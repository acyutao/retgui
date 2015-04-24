package org.acca.retgui.domainmodel;

import java.util.ArrayList;
import java.util.List;

public abstract  class Transaction {
	

	protected List<Record> sequentialRecords = new ArrayList();


	public List<Record> getSequentialRecords() {
		return sequentialRecords;
	}
	
}
