package org.acca.retgui.domainmodel;

import java.util.ArrayList;
import java.util.List;

import org.acca.retgui.dish.NoSuchRcidException;

public abstract  class Transaction {
	
	protected List<NoSuchRcidException> noSuchRcidExceptions;

	protected List<Record> sequentialRecords = new ArrayList();



	public List<NoSuchRcidException> getNoSuchRcidExceptions() {
		return noSuchRcidExceptions;
	}

	public List<Record> getSequentialRecords() {
		return sequentialRecords;
	}
	
}
