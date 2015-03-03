/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acca.retgui.domainmodel.BaseVO;

/**
 * 
 * RetTransaction.
 * 
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-12
 */
public class RetTransaction extends BaseVO {

	private final String[] filterRcids = { "IT0S" };
	private final String CNJ = "CNJ";

	private RetRecord recIt02;

	private List<RetRecord> recIt02CNJs = new ArrayList();
	private List<RetRecord> recIt03s;

	private List<RetRecord> recIt0Ns; // for dish22
	private List<RetRecord> recIt0Ts; // for dish22
	private List<RetRecord> recIt0TCNJs; // for dish22

	private RetRecord recIt04;
	private List<RetRecord> recIt05s;
	private List<RetRecord> recIt06s;
	private List<RetRecord> recIt06CNJs;

	private Map<RetRecord, Long> it06TktSeq;
	private Map<RetRecord, Long> it0GTktSeq;
	private Map<RetRecord, Long> it0TTktSeq;
	private List<RetRecord> recIt0Gs;
	private List<RetRecord> recIt0GCNJs;

	private List<RetRecord> recIt07s;
	private List<RetRecord> recIt08s;
	private List<RetRecord> recIt0Ys;

	private RetRecord recIt09;
	private RetRecord recIt0A;
	private RetRecord recIt0B;
	private List<RetRecord> recIt0Cs;
	private List<RetRecord> recIt0Ds;
	private RetRecord recIt0E;

	private String trnn;
	private String trnc;
	private String form;
	private String cjcp;
	private String dais;
	private String agentCode;
	private String airCode;
	private String docNum;
	private String tdnr;

	private String wbsi = "N";

	private List<NoSuchRcidException> noSuchRcidExceptions;

	private List<RetRecord> sequentialRecords = new ArrayList();

	// private List<RetErrorVO> errors = new ArrayList();

	private boolean existIT0Y = false;

	// attribute validate
	// private AttributeValidator validator;

	private Map<String, Long> identifierCount = new HashMap();

	private List<String> cnjDocNums = new ArrayList();

	/**
	 * RetTransaction.
	 * 
	 * @param transRecords
	 *            List<String>
	 * @param retVersion
	 *            RetVersion
	 */
	public RetTransaction(List<String> transRecords, RetVersion retVersion) {

		String recordId;

		for (int i = 0; i < transRecords.size(); i++) {
			RetRecord retRecord = new RetRecord(transRecords.get(i), retVersion);

			retRecord.setSequenceInRet(sequentialRecords.size());
			// sequential records
			sequentialRecords.add(retRecord);

			if (retRecord.isErrorRetRecord()) {
				if (noSuchRcidExceptions == null) {
					noSuchRcidExceptions = new ArrayList<NoSuchRcidException>();
				}
				noSuchRcidExceptions.add(retRecord.getNoSuchRcidException());
				continue;
			}

			recordId = retRecord.getRecordId();

			if (isFilterByRecordId(recordId)) {
				continue;
			}

			assembleIt02IfExist(retRecord);
			assembleIt03IfExist(retRecord);
			assembleIt04IfExist(retRecord);
			assembleIt05IfExist(retRecord);
			assembleIt06IfExist(retRecord);
			assembleIt07IfExist(retRecord);
			assembleIt08IfExist(retRecord);
			assembleIt09IfExist(retRecord);
			assembleIt0AIfExist(retRecord);
			assembleIt0BIfExist(retRecord);
			assembleIt0CIfExist(retRecord);
			assembleIt0DIfExist(retRecord);
			assembleIt0EIfExist(retRecord);
			assembleIt0GIfExist(retRecord);
			assembleIt0YIfExist(retRecord);
			assembleIt0NIfExist(retRecord);
			assembleIt0TIfExist(retRecord);
			setCountForIdentifier(retRecord);
		}
		// from above result ： it06TktSeq and it0GTktSeq
		for (RetRecord r : sequentialRecords) {
			if ("IT06".equalsIgnoreCase(r.getRecordId())) {
				long tktSeq = it06TktSeq.get(r);
				r.setTicketSequence(tktSeq);
			} else if ("IT0G".equalsIgnoreCase(r.getRecordId())) {
				long tktSeq = it0GTktSeq.get(r);
				r.setTicketSequence(tktSeq);
			}
		}
		setCountForCNJs();

		if (recIt02 != null) {
			trnc = recIt02.getElement(DishRetConst.TRNC);
			trnn = recIt02.getElement(DishRetConst.TRNN);
			form = recIt02.getElement(DishRetConst.FORM);
			dais = recIt02.getElement(DishRetConst.DAIS);
			agentCode = recIt02.getElement(DishRetConst.AGTN).substring(0, 7);
			tdnr = recIt02.getElement(DishRetConst.TDNR);
			airCode = tdnr.substring(0, 3);
			docNum = tdnr.substring(3).trim();
		}

		if (recIt02CNJs.size() > 0) {
			for (RetRecord r : recIt02CNJs) {
				String cnjTdnr = r.getElement(DishRetConst.TDNR);
				String docNum = cnjTdnr.substring(3).trim();

				cnjDocNums.add(docNum);
			}
		}
		
		putElements();

	}

	private void setCountForCNJs() {
		// special process IT02 IT06 IT0G.
		if (this.recIt02 != null) {
			identifierCount.put(DishRetConst.IT02, 1L);
		}

		if (this.recIt06s != null && this.recIt06s.size() > 0) {
			identifierCount.put(DishRetConst.IT06,
					Long.valueOf(this.recIt06s.size()));
		}

		if (this.recIt0Gs != null && this.recIt0Gs.size() > 0) {
			identifierCount.put(DishRetConst.IT0G,
					Long.valueOf(this.recIt0Gs.size()));
		}

		if (this.recIt02CNJs != null && this.recIt02CNJs.size() > 0) {
			identifierCount.put(DishRetConst.IT02 + CNJ,
					Long.valueOf(this.recIt02CNJs.size()));
		}

		if (this.recIt06CNJs != null && this.recIt06CNJs.size() > 0) {
			identifierCount.put(DishRetConst.IT06 + CNJ,
					Long.valueOf(this.recIt06CNJs.size()));
		}

		if (this.recIt0GCNJs != null && this.recIt0GCNJs.size() > 0) {
			identifierCount.put(DishRetConst.IT0G + CNJ,
					Long.valueOf(this.recIt0GCNJs.size()));
		}
	}

	private void setCountForIdentifier(RetRecord record) {
		String identifier = record.getRecordId();

		boolean filter = DishRetConst.IT02.equals(identifier)
				|| DishRetConst.IT06.equals(identifier)
				|| DishRetConst.IT0G.equals(identifier);

		if (!filter) {
			if (identifierCount.get(identifier) == null) {
				identifierCount.put(identifier, 1L);
			} else {
				Long count = identifierCount.get(identifier);
				identifierCount.put(identifier, count + 1);
			}
		}
	}

	// /**
	// * Use AttributeValidator Validate all Element in RetRecord.Default 203.
	// */
	// public void attributeValidate() {
	// attributeValidate("203");
	// }

	// /**
	// * Use AttributeValidator Validate all Element in RetRecord.
	// * @param revn String
	// */
	// public void attributeValidate(String revn) {
	//
	// if (validator == null) {
	//
	// if("220".equals(revn)){
	// validator = new Attribute220Validator();
	// }else{
	// validator = new AttributeValidator();
	// }
	//
	// validator.initAttributeValidators();
	// }
	//
	// validator.setRetTransaction(this);
	//
	// for (RetRecord r : sequentialRecords) {
	//
	// // change RetRecord for validate.
	// validator.setRetRecord(r);
	//
	// // Dish description
	// RecordIdentifier identifier = r.getIdentifier();
	//
	// // loop and validate all element in Identifier.
	// for (RecordElement e : identifier.getElements()) {
	// String value = r.getElement(e.getName());
	// validator.attributeValidate(value, e);
	// }
	//
	// }
	// }

	public void putElements() {
		for (RetRecord r : sequentialRecords) {

			// change RetRecord for validate.

			// Dish description
			RecordIdentifier identifier = r.getIdentifier();

			// loop and validate all element in Identifier.
			for (RecordElement e : identifier.getElements()) {
				r.getElement(e.getName());
			}

		}

	}

	/**
	 * According Ticket sequence, find CNJ Ticket`s TI06s. Begin with ticket
	 * sequence 2.
	 * 
	 * @param seq
	 *            long
	 * @return List<RetRecord>
	 */
	public List<RetRecord> getIt06CNJsByIt02Seq(long seq) {
		List<RetRecord> it06CNJs = new ArrayList();
		if (recIt06CNJs != null) {
			for (RetRecord it06CNJ : recIt06CNJs) {
				long tktSeq = it06TktSeq.get(it06CNJ);
				if (tktSeq == seq) {
					it06CNJs.add(it06CNJ);
				}
			}
		}
		return it06CNJs;
	}

	/**
	 * According Ticket sequence, find CNJ Ticket`s TI0Gs. Begin with ticket
	 * sequence 2.
	 * 
	 * @param seq
	 *            long
	 * @return List<RetRecord>
	 */
	public List<RetRecord> getIt0GCNJsByIt02Seq(long seq) {
		List<RetRecord> it0GCNJs = new ArrayList();
		for (RetRecord it0GCNJ : recIt0GCNJs) {
			long tktSeq = it0GTktSeq.get(it0GCNJ);
			if (tktSeq == seq) {
				it0GCNJs.add(it0GCNJ);
			}
		}
		return it0GCNJs;
	}

	/**
	 * According Ticket sequence, find CNJ Ticket`s TI0Ts. Begin with ticket
	 * sequence 2.
	 * 
	 * @param seq
	 *            long
	 * @return List<RetRecord>
	 */
	public List<RetRecord> getIt0TCNJsByIt02Seq(long seq) {
		List<RetRecord> it0TCNJs = new ArrayList();
		if (recIt0TCNJs == null) {
			return null;
		}

		for (RetRecord it0TCNJ : recIt0TCNJs) {
			long tktSeq = it0TTktSeq.get(it0TCNJ);
			if (tktSeq == seq) {
				it0TCNJs.add(it0TCNJ);
			}
		}
		return it0TCNJs;
	}

	private void assembleIt0GIfExist(RetRecord retRecord) {
		if ("It0G".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt0Gs == null) {
				recIt0Gs = new ArrayList<RetRecord>();
				recIt0GCNJs = new ArrayList<RetRecord>();
				it0GTktSeq = new HashMap();
			}
			if (recIt02CNJs.size() == 0) {
				recIt0Gs.add(retRecord);
				it0GTktSeq.put(retRecord, 1L);
			} else {
				recIt0GCNJs.add(retRecord);
				it0GTktSeq.put(retRecord, recIt02CNJs.size() + 1L);
			}

			// record sequence.
			retRecord.setSequence(recIt0Gs.size() + recIt0GCNJs.size());
		}
	}

	private void assembleIt0EIfExist(RetRecord retRecord) {

		if ("It0E".equalsIgnoreCase(retRecord.getRecordId())) {
			recIt0E = retRecord;
		}
	}

	private void assembleIt0DIfExist(RetRecord retRecord) {

		if ("It0D".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt0Ds == null) {
				recIt0Ds = new ArrayList<RetRecord>();
			}

			recIt0Ds.add(retRecord);
		}
	}

	private void assembleIt0CIfExist(RetRecord retRecord) {
		if ("It0C".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt0Cs == null) {
				recIt0Cs = new ArrayList<RetRecord>();
			}

			recIt0Cs.add(retRecord);
		}
	}

	private void assembleIt0BIfExist(RetRecord retRecord) {
		if ("It0B".equalsIgnoreCase(retRecord.getRecordId())) {
			recIt0B = retRecord;
		}
	}

	private void assembleIt0AIfExist(RetRecord retRecord) {
		if ("It0A".equalsIgnoreCase(retRecord.getRecordId())) {
			recIt0A = retRecord;
		}
	}

	private void assembleIt02IfExist(RetRecord retRecord) {

		if ("IT02".equalsIgnoreCase(retRecord.getRecordId())) {

			if (recIt02 == null) {
				recIt02 = retRecord;
				retRecord.setSequence(1);
			} else {
				recIt02CNJs.add(retRecord);
				// sequence begin from 1
				retRecord.setSequence(recIt02CNJs.size() + 1);
				// ticket sequence begin from 1,cnj ticket squence from 2
				retRecord.setTicketSequence(recIt02CNJs.size() + 1);
			}
		}
	}

	private void assembleIt03IfExist(RetRecord retRecord) {

		if ("IT03".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt03s == null) {
				recIt03s = new ArrayList<RetRecord>();
			}
			recIt03s.add(retRecord);
		}
	}

	private void assembleIt04IfExist(RetRecord retRecord) {
		if ("IT04".equalsIgnoreCase(retRecord.getRecordId())) {
			recIt04 = retRecord;
		}
	}

	private void assembleIt05IfExist(RetRecord retRecord) {

		if ("IT05".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt05s == null) {
				recIt05s = new ArrayList<RetRecord>();
			}

			// record RetRecord sequence.
			retRecord.setSequence(recIt05s.size() + 1);

			recIt05s.add(retRecord);
		}
	}

	private void assembleIt06IfExist(RetRecord retRecord) {

		if ("IT06".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt06s == null) {
				recIt06s = new ArrayList<RetRecord>();
				recIt06CNJs = new ArrayList<RetRecord>();
				it06TktSeq = new HashMap();
			}

			if (recIt02CNJs.size() == 0) {
				recIt06s.add(retRecord);
				it06TktSeq.put(retRecord, 1L);
			} else {
				recIt06CNJs.add(retRecord);
				it06TktSeq.put(retRecord, recIt02CNJs.size() + 1L);
			}

			// record sequence.
			retRecord.setSequence(recIt06s.size() + recIt06CNJs.size());
		}
	}

	private void assembleIt07IfExist(RetRecord retRecord) {

		if ("IT07".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt07s == null) {
				recIt07s = new ArrayList<RetRecord>();
			}

			retRecord.setSequence(recIt07s.size() + 1);
			recIt07s.add(retRecord);

		}
	}

	private void assembleIt08IfExist(RetRecord retRecord) {

		if ("IT08".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt08s == null) {
				recIt08s = new ArrayList<RetRecord>();
			}

			recIt08s.add(retRecord);

			// record sequence.
			retRecord.setSequence(recIt08s.size());
		}
	}

	private void assembleIt0YIfExist(RetRecord retRecord) {

		if ("IT0Y".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt0Ys == null) {
				recIt0Ys = new ArrayList<RetRecord>();
			}

			recIt0Ys.add(retRecord);

			// record sequence.
			retRecord.setSequence(recIt0Ys.size());
		}
	}

	private void assembleIt09IfExist(RetRecord retRecord) {

		if ("It09".equalsIgnoreCase(retRecord.getRecordId())) {
			recIt09 = retRecord;
		}

	}

	/**
	 * IT0Ts(not IT0TCNJs) belong to the IT02(not IT02CNJ,PRI TICKET) when IT0Ts
	 * after this IT02 before next IT02. IT0Ts(IT0TCNJs) belong to the
	 * IT02(IT02CNJ,CNJ TICKET) when IT0Ts after this IT02 before next IT02.
	 * 
	 * @param retRecord
	 *            RetRecord
	 */
	private void assembleIt0TIfExist(RetRecord retRecord) {
		if ("IT0T".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt0Ts == null) {
				recIt0Ts = new ArrayList<RetRecord>();
				recIt0TCNJs = new ArrayList<RetRecord>();
				it0TTktSeq = new HashMap();
			}
			if (recIt02CNJs.size() == 0) {
				recIt0Ts.add(retRecord);
				it0TTktSeq.put(retRecord, 1L);
			} else {
				recIt0TCNJs.add(retRecord);
				it0TTktSeq.put(retRecord, recIt02CNJs.size() + 1L);
			}

			// record sequence.
			retRecord.setSequence(recIt0Ts.size() + recIt0TCNJs.size());
		}

	}

	private void assembleIt0NIfExist(RetRecord retRecord) {
		if ("IT0N".equalsIgnoreCase(retRecord.getRecordId())) {
			if (recIt0Ns == null) {
				recIt0Ns = new ArrayList<RetRecord>();
			}

			recIt0Ns.add(retRecord);

			// record sequence.
			retRecord.setSequence(recIt0Ns.size());
		}

	}

	/**
	 * record Id: IT02, IT05...
	 * 
	 * @param recordId
	 *            String
	 * @return boolean
	 */
	protected boolean isFilterByRecordId(String recordId) {

		for (String filterRcid : filterRcids) {
			if (filterRcid.equalsIgnoreCase(recordId)) {

				if (DishRetConst.IT0Y.equals(recordId)) {
					this.existIT0Y = true;
				}

				return true;
			}

		}
		return false;
	}

	public List<RetRecord> getRecIt03s() {
		return recIt03s;
	}

	public void setRecIt03s(List<RetRecord> recIt03s) {
		this.recIt03s = recIt03s;
	}

	public RetRecord getRecIt04() {
		return recIt04;
	}

	public void setRecIt04(RetRecord recIt04) {
		this.recIt04 = recIt04;
	}

	public List<RetRecord> getRecIt05s() {
		return recIt05s;
	}

	public void setRecIt05s(List<RetRecord> recIt05s) {
		this.recIt05s = recIt05s;
	}

	public List<RetRecord> getRecIt06s() {
		return recIt06s;
	}

	public void setRecIt06s(List<RetRecord> recIt06s) {
		this.recIt06s = recIt06s;
	}

	public List<RetRecord> getRecIt06CNJs() {
		return recIt06CNJs;
	}

	public void setRecIt06CNJs(List<RetRecord> recIt06CNJs) {
		this.recIt06CNJs = recIt06CNJs;
	}

	public List<RetRecord> getRecIt07s() {
		return recIt07s;
	}

	public void setRecIt07s(List<RetRecord> recIt07s) {
		this.recIt07s = recIt07s;
	}

	public List<RetRecord> getRecIt08s() {
		return recIt08s;
	}

	public void setRecIt08s(List<RetRecord> recIt08s) {
		this.recIt08s = recIt08s;
	}

	public RetRecord getRecIt09() {
		return recIt09;
	}

	public void setRecIt09(RetRecord recIt09) {
		this.recIt09 = recIt09;
	}

	public List<RetRecord> getRecIt0Cs() {
		return recIt0Cs;
	}

	public void setRecIt0Cs(List<RetRecord> recIt0Cs) {
		this.recIt0Cs = recIt0Cs;
	}

	public RetRecord getRecIt0E() {
		return recIt0E;
	}

	public void setRecIt0E(RetRecord recIt0E) {
		this.recIt0E = recIt0E;
	}

	public String[] getFilterRcids() {
		return filterRcids;
	}

	public String getTrnc() {
		return trnc;
	}

	public String getForm() {
		return form;
	}

	public void setRecIt0A(RetRecord recIt0A) {
		this.recIt0A = recIt0A;
	}

	public RetRecord getRecIt0A() {
		return recIt0A;
	}

	public void setRecIt0B(RetRecord recIt0B) {
		this.recIt0B = recIt0B;
	}

	public RetRecord getRecIt0B() {
		return recIt0B;
	}

	public void setRecIt0Ds(List<RetRecord> recIt0Ds) {
		this.recIt0Ds = recIt0Ds;
	}

	public List<RetRecord> getRecIt0Ds() {
		return recIt0Ds;
	}

	public void setRecIt0Gs(List<RetRecord> recIt0Gs) {
		this.recIt0Gs = recIt0Gs;
	}

	public List<RetRecord> getRecIt0Gs() {
		return recIt0Gs;
	}

	public List<RetRecord> getRecIt02CNJs() {
		return recIt02CNJs;
	}

	public void setRecIt02CNJs(List<RetRecord> recIt02CNJs) {
		this.recIt02CNJs = recIt02CNJs;
	}

	public void setTrnn(String trnn) {
		this.trnn = trnn;
	}

	public String getTrnn() {
		return trnn;
	}

	public void setCjcp(String cjcp) {
		this.cjcp = cjcp;
	}

	public String getCjcp() {
		return cjcp;
	}

	public void setRecIt0GCNJs(List<RetRecord> recIt0GCNJs) {
		this.recIt0GCNJs = recIt0GCNJs;
	}

	public List<RetRecord> getRecIt0GCNJs() {
		return recIt0GCNJs;
	}

	public RetRecord getRecIt02() {
		return recIt02;
	}

	public void setRecIt02(RetRecord recIt02) {
		this.recIt02 = recIt02;
	}

	public List<NoSuchRcidException> getNoSuchRcidExceptions() {
		return noSuchRcidExceptions;
	}

	public List<RetRecord> getSequentialRecords() {
		return sequentialRecords;
	}

	public String getWbsi() {
		return wbsi;
	}

	public void setWbsi(String wbsi) {
		this.wbsi = wbsi;
	}

	public void setTrnc(String trnc) {
		this.trnc = trnc;
	}

	public void setForm(String form) {
		this.form = form;
	}

	// public List<RetErrorVO> getErrors() {
	// return errors;
	// }
	//
	// public void setErrors(List<RetErrorVO> errors) {
	// this.errors = errors;
	// }
	//
	// public AttributeValidator getValidator() {
	// return validator;
	// }
	//
	// public void setValidator(AttributeValidator validator) {
	// this.validator = validator;
	// }

	public String getDais() {
		return dais;
	}

	public void setDais(String dais) {
		this.dais = dais;
	}

	// /**
	// * Generate RetTicketVOs.
	// *
	// * @return List<RetTicketVO>
	// */
	// public List<RetTicketVO> getTicketVO() {
	// List<RetTicketVO> list = new ArrayList();
	//
	// RetTicketVO vo = parseVO(this.recIt02);
	//
	// if (vo != null) {
	// list.add(vo);
	// }
	//
	// if (this.recIt02CNJs != null) {
	// for (RetRecord ret : this.recIt02CNJs) {
	// vo = parseVO(ret);
	// list.add(vo);
	// }
	// }
	//
	// return list;
	// }
	//
	// private RetTicketVO parseVO(RetRecord it02) {
	//
	// if (it02 == null) {
	// return null;
	// }
	//
	// RetTicketVO vo = new RetTicketVO();
	// vo.setTrnc(trnc);
	// vo.setDocNum(this.recIt02.getElement(DishRetConst.TDNR).substring(3,
	// 15));
	//
	// return vo;
	// }

	public Map<String, Long> getIdentifierCount() {
		return identifierCount;
	}

	public void setIdentifierCount(Map<String, Long> identifierCount) {
		this.identifierCount = identifierCount;
	}

	public boolean isExistIT0Y() {
		return existIT0Y;
	}

	public void setExistIT0Y(boolean existIT0Y) {
		this.existIT0Y = existIT0Y;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAirCode() {
		return airCode;
	}

	public void setAirCode(String airCode) {
		this.airCode = airCode;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getTdnr() {
		return tdnr;
	}

	public void setTdnr(String tdnr) {
		this.tdnr = tdnr;
	}

	public List<RetRecord> getRecIt0Ys() {
		return recIt0Ys;
	}

	public void setRecIt0Ys(List<RetRecord> recIt0Ys) {
		this.recIt0Ys = recIt0Ys;
	}

	public List<String> getCnjDocNums() {
		return cnjDocNums;
	}

	public List<RetRecord> getRecIt0Ns() {
		return recIt0Ns;
	}

	public void setRecIt0Ns(List<RetRecord> recIt0Ns) {
		this.recIt0Ns = recIt0Ns;
	}

	public List<RetRecord> getRecIt0Ts() {
		return recIt0Ts;
	}

	public void setRecIt0Ts(List<RetRecord> recIt0Ts) {
		this.recIt0Ts = recIt0Ts;
	}

	public List<RetRecord> getRecIt0TCNJs() {
		return recIt0TCNJs;
	}

	public void setRecIt0TCNJs(List<RetRecord> recIt0TCNJs) {
		this.recIt0TCNJs = recIt0TCNJs;
	}

}
