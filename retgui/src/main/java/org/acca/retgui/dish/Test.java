package org.acca.retgui.dish;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

public class Test {

	/**
	 * @param args
	 * @throws JAXBException
	 */
	public static void main(String[] args) throws JAXBException {
		// TODO Auto-generated method stub
		// InputStream is = Thread.currentThread().getContextClassLoader()
		// .getResourceAsStream("dish220.xml");
		// DishRet ret = JAXBUtils.readObjectFromXml(DishRet.class, is);
		loopTransaction();

	}

	private static List<RetTransaction> loopTransaction() {
		// Read File.
		RetFileUtil fileUtil = new RetFileUtil("D:\\temp\\upload\\test.R");
		RetVersion version = RetVersion.getInstance("203");
		RetHelper helper = new RetHelper(version, fileUtil);

		long i = 1;

		List<RetTransaction> retTransList = new ArrayList<RetTransaction>();

		RetTransaction retTrans = helper.fetchTransRecords();

		while (retTrans != null) {

			retTransList.add(retTrans);
			retTrans = helper.fetchTransRecords();
		}

		return retTransList;

	}

}
