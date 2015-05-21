/**
 * 
 */
package org.acca.retgui.execution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.acca.retgui.config.ApplicationContextHolder;
import org.acca.retgui.config.ConfigHelper;
import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.RetFileUtil;
import org.acca.retgui.domainmodel.RetTransaction;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.service.BaseFileParser;
import org.acca.retgui.service.FileTypeParseFactory;
import org.acca.retgui.utils.LongUtil;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.DBCollection;

/**
 * @author yutao
 * 
 */
public class RetProcess {

	private int readQueueLength = 20;

	private int writerCount = 5;

	private int readCount = 5;

	private int batchSize = 100;

	private ThreadGroup threadGroup = new ThreadGroup("RET_PROCESS");

	private ExecutorService executor;

	private String fileName;

	private BlockingQueue readerQueue = new ArrayBlockingQueue(readQueueLength);

	private Boolean readerCompleteFlag = false;

	private FileTypeParseFactory facotry;

	private BaseFileParser parser;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	public static final String SIZE_CATEGORY = "size.category";
	public static final String CATEGORY_CAPACITY = "category.capacity";

	/**
	 * 文件处理
	 * 
	 * @param fileName
	 */
	public FileType execute(final String fileName) {

		// 初始化
		this.fileName = fileName;

		// 创建解析器
		XMLConfiguration configuration = ConfigHelper.getInstance();
		String path = configuration.getString("uploadFilePath");
		// configuration.getKeys(prefix);
		facotry = new FileTypeParseFactory(path, fileName);
		parser = this.facotry.getFileParserInstance();
		RetFileUtil retFileUtil = new RetFileUtil(path, fileName);
		String fileCategory = getFileSizeType(configuration,
				retFileUtil.getTotalTrans());
		Iterator<String> countGroup = configuration.getKeys(fileCategory
				+ "Count");
		for (; countGroup.hasNext();) {
			String strKey = (String) countGroup.next();
			writerCount = configuration.getInt(strKey, 5);
		}

		final MongoTemplate mongoTemplate = (MongoTemplate) ApplicationContextHolder
				.getApplicationContext().getBean("mongoTemplate");
		DBCollection dbcollection = mongoTemplate.createCollection(fileName);
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

					int i = 0;
					int interval = 0;
					while (!(readerCompleteFlag&&readerQueue.isEmpty())) {
						try {
//							interval = 1000*(1+i/100);
//							if(i>200){
//							interval=interval+1000;
//							}
//							i++;
//							Thread.sleep(interval);
							List<RetTransaction> list = (List<RetTransaction>) readerQueue
									.poll(1, TimeUnit.SECONDS);
							if (list != null) {
								mongoTemplate.insert(list, fileName);
							}

						} catch (InterruptedException e) {
							log.info("{0} : {1}, Reader Queue is empty.",
									Thread.currentThread(), fileName);
						} catch (Exception e) {
							// 执行错误
							log.info("Processor failed.", e);
						}
					}

					return null;
				}

			}));
		}

		try {
			reader.get();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		log.info("Reader Complete. {}", fileName);

		for (Future f : writers) {
			try {
				f.get();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// } catch (InterruptedException | ExecutionException e) {
			// e.printStackTrace();
			// }
		}

		dbcollection.createIndex("tdnr");
		dbcollection.createIndex("tnrr");
		log.info("Writer Complete. {}", fileName);
		return parser.parseFileType();
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

	public static String getFileSizeType(XMLConfiguration configuration,
			Long total) {
		String[] categorys = configuration
				.getStringArray(ConfigHelper.SIZE_CATEGORY);
		String[] sizes = configuration
				.getStringArray(ConfigHelper.CATEGORY_CAPACITY);

		try {
			for (int i = 0; i < sizes.length; i++) {
				String str = sizes[i];

				Long number = LongUtil.defaultValueOf(str);

				if (total <= number) {
					return categorys[i];
				}
			}

			return categorys[categorys.length - 1];

		} catch (Exception e) {
			return "";
		}

	}

}
