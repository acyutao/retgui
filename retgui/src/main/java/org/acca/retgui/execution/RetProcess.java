/**
 * 
 */
package org.acca.retgui.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import org.acca.retgui.config.ConfigHelper;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.service.FileTypeParseFactory;
import org.apache.commons.configuration.XMLConfiguration;

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

		// 准备线程池
		executor = Executors.newScheduledThreadPool(this.getWriterCount() + 1,
				new SimpleThreadFactory());

		// 添加一个读
		Future reader = executor.submit(new Callable() {

			@Override
			public Object call() throws Exception {
				// 读取交易，放入队列.
				List<Transaction> list = new ArrayList();
				Transaction tran = RetProcess.this.facotry
						.getFileParserInstance().readATransaction();
				while (tran != null) {
					list.add(tran);

					if (list.size() == batchSize) {
						readerQueue.put(list);
						list = new ArrayList();
					}
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
			writers.add(executor.submit(new Callable(){

				@Override
				public Object call() throws Exception {
					
					// TODO 存入mongodb
					
					
					return null;
				}
				
			}));
		}

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
