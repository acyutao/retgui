/**
 * 
 */
package org.acca.retgui.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.acca.retgui.config.ApplicationContextHolder;
import org.acca.retgui.config.ConfigHelper;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.service.BaseFileParser;
import org.acca.retgui.service.FileTypeParseFactory;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author yutao
 * 
 */
public class RetProcess {

	private int readQueueLength = 20;

	private int writerCount = 5;

	private int batchSize = 100;

	private ThreadGroup threadGroup = new ThreadGroup("RET_PROCESS");

	private ExecutorService executor;

	private String fileName;

	private BlockingQueue readerQueue = new ArrayBlockingQueue(readQueueLength);

	private Boolean readerCompleteFlag = false;

	private FileTypeParseFactory facotry;
	
	private BaseFileParser parser;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 文件处理
	 * 
	 * @param fileName
	 */
	public void execute(final String fileName) {

		// 初始化
		this.fileName = fileName;

		// 创建解析器
		XMLConfiguration configuration = ConfigHelper.getInstance();
		String path = configuration.getString("uploadFilePath");
		facotry = new FileTypeParseFactory(path, fileName);
		parser = this.facotry.getFileParserInstance();

		// 准备线程池
		executor = Executors.newScheduledThreadPool(this.getWriterCount() + 1,
				new SimpleThreadFactory());

		// 添加一个读
		Future reader = executor.submit(new Callable() {

			@Override
			public Object call() throws Exception {
				// 读取交易，放入队列.
				List<Transaction> list = new ArrayList();
				Transaction tran = parser.readATransaction();
				while (tran != null) {
					list.add(tran);

					if (list.size() == batchSize) {
						readerQueue.put(list);
						list = new ArrayList();
					}
					
					 tran = parser.readATransaction();
				}

				if (!list.isEmpty()) {
					readerQueue.put(list);
				}

				readerCompleteFlag = true;

				return null;
			}

		});

		List<Future> writers = new ArrayList();
		for (int i = 0; i < this.writerCount; i++) {
			writers.add(executor.submit(new Callable() {

				@Override
				public Object call() throws Exception {

					// TODO 存入mongodb
					MongoTemplate mongoTemplate = (MongoTemplate) ApplicationContextHolder
							.getApplicationContext().getBean("mongoTemplate");

					while (!readerCompleteFlag) {
						try {
							List<Transaction> list = (List<Transaction>) readerQueue
									.poll(1, TimeUnit.SECONDS);
							if (list != null) {
								mongoTemplate.insert(list, fileName);
							}

						} catch (InterruptedException e) {
							log.debug("{} : {}, Reader Queue is empty.",
									Thread.currentThread(), fileName);
						} catch (Exception e) {
							// 执行错误
							log.debug("Processor failed.", e);
						}
					}

					return null;
				}

			}));
		}

		try {
			reader.get();
			log.info("Reader Complete. {}", fileName);
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}

		for (Future f : writers) {
			try {
				f.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		log.info("Writer Complete. {}", fileName);

	}

	public int getReadQueueLength() {
		return readQueueLength;
	}

	public void setReadQueueLength(int readQueueLength) {
		this.readQueueLength = readQueueLength;
	}

	public int getWriterCount() {
		return writerCount;
	}

	public void setWriterCount(int writerCount) {
		this.writerCount = writerCount;
	}

	/**
	 * Simple ThreadFactory.
	 * 
	 * 
	 * @version Seurat v1.0
	 * @author Yu Tao, 2012-7-3
	 */
	class SimpleThreadFactory implements ThreadFactory {
		int count;

		public Thread newThread(Runnable r) {

			return new Thread(threadGroup, r, fileName + "-" + count++);
		}
	}

}
