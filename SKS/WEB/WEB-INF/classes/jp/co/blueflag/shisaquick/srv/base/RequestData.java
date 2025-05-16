package jp.co.blueflag.shisaquick.srv.base;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * 
 * ���N�G�X�g�f�[�^�ێ�
 *  : XMLID���Ƃ̊e���N�G�X�g�f�[�^�ێ��N���X��
 *
 */
public class RequestData extends RequestResponsBean {
	
	/**
	 * �R���X�g���N�^
	 * @param inXML  : XML�̃I�u�W�F�N�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestData(Document inXML) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
	
		super();
		
		try{
			if (null != inXML){
				//XML�f�[�^�ݒ�ďo
				CreateXMLData(inXML);
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "XML�f�[�^�ݒ�ďo�Ɏ��s���܂����B");

		} finally {
			
		}
		
	}

	/**
	 * XML�f�[�^�ݒ�
	 * @param inXML : XML�I�u�W�F�N�g 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void CreateXMLData(Document inXML) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			
			inXML.getDocumentElement().getNodeName();
			
			//XMLID�ݒ�
			super.setID(inXML.getDocumentElement().getNodeName());
			
			//�q�m�[�h���擾
			int iChildLength = inXML.getDocumentElement().getChildNodes().getLength();
		
			//�q�m�[�h�����[�v
			for(int i=0;i<iChildLength;i++){
				//�q�m�[�hItem�擾
				Node strChildItem = inXML.getDocumentElement().getChildNodes().item(i);
	
				//�@�\ID�f�[�^�ݒ�ďo
				CreateKinoData(strChildItem);
			}
			
		} catch (Exception e){
			
			em.ThrowException(e, "XMLID�f�[�^�ݒ�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	
	/**
	 * �@�\ID�f�[�^�ݒ�
	 * @param strChildItem : ���m�[�hItem 
	 * @param reqRespBean  : XMLIDBean
	 * @return �e�[�u����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void CreateKinoData(Node strChildItem) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
			
		try {
			//���s�ȊO�̏ꍇ�v�f�ǉ�
	    	if(strChildItem.getNodeName() != "#text"){
	    		
	    		//���N�G�X�g���X�|���XBean����
	    		RequestResponsKindBean reqBean = new RequestResponsKindBean();
	    		//ID�ݒ�
	    		reqBean.setID(strChildItem.getNodeName());
	    		
	    		//�����ǉ�
	    		super.getItemList().add(reqBean);
	    		
	    		for (int i=0; i < strChildItem.getChildNodes().getLength();i++){
	    			//�q�m�[�hItem�擾
	    			Node strChildItem2 = strChildItem.getChildNodes().item(i);
					//�e�[�u���f�[�^�ݒ�ďo
	    			CreateTableData(strChildItem2, reqBean);
	
	    		}
	    	}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�f�[�^�ݒ�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}

	/**
	 * �e�[�u���f�[�^�ݒ�
	 * @param strChildItem : ���m�[�hItem 
	 * @param reqRespBean  : �e�[�u���C���f�b�N�X
	 * @return �e�[�u����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void CreateTableData(Node strChildItem, RequestResponsKindBean requestResponsBean) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//���s�ȊO�̏ꍇ�v�f�ǉ�
	    	if(strChildItem.getNodeName() != "#text"){
	    		
	    		//���N�G�X�g���X�|���XBean����
	    		RequestResponsTableBean reqBean = new RequestResponsTableBean();
	    		//ID�ݒ�
	    		reqBean.setID(strChildItem.getNodeName());
	    		
	    		//�����ǉ�
	    		requestResponsBean.getItemList().add(reqBean);
	    		
	    		for (int i=0; i < strChildItem.getChildNodes().getLength();i++){
	    			//�q�m�[�hItem�擾
	    			Node strChildItem2 = strChildItem.getChildNodes().item(i);
					//�s�f�[�^�ݒ�ďo
	    			CreateRowData(strChildItem2, reqBean);
	
	    		}
	    	}
		} catch (Exception e) {
			
			em.ThrowException(e, "�e�[�u���f�[�^�ݒ�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}

	/**
	 * �s�f�[�^�ݒ�
	 * @param strChildItem : ���m�[�hItem 
	 * @param reqRespBean  : �e�[�u���C���f�b�N�X
	 * @return �e�[�u����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void CreateRowData(Node strChildItem, RequestResponsTableBean requestResponsBean) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			
			//���s�ȊO�̏ꍇ�v�f�ǉ�
	    	if(strChildItem.getNodeName() != "#text"){
	    		
	    		//���N�G�X�g���X�|���XBean����
	    		RequestResponsRowBean reqBean = new RequestResponsRowBean();
	    		//ID�ݒ�
	    		reqBean.setID(strChildItem.getNodeName());
	    		
	    		//�����ǉ�
	    		requestResponsBean.getItemList().add(reqBean);
	    		
	            for (int i=0; i < strChildItem.getAttributes().getLength(); i++) {
	                Node attr = strChildItem.getAttributes().item(i);  // �����m�[�h

	                //���s�R�[�h�ɒu��������
	                attr.setNodeValue(attr.getNodeValue().replaceAll("\\\\n", "\n"));
	                
	                RequestResponsRow reqRow = new RequestResponsFieldBean(attr.getNodeName(), attr.getNodeValue());
	                	                
	                //���ڐݒ�
	                reqBean.getItemList().add(reqRow);
	            }
	
	    	}
		} catch (Exception e) {
			
			em.ThrowException(e, "�s�f�[�^�ݒ�Ɏ��s���܂����B");
			
		} finally {
			
		}
	}

	/**
	 * XmlID�擾
	 * @return : XmlID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetXMLID() {
		return super.getID();
	}
	
	/**
	 * �@�\ID�擾 
	 * @param KindItemNo : �A�C�e���ԍ� 
	 * @return �@�\ID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetKindID(int KindItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strKinoId = null;
		
		try {
		
			//�v�f����ȊO�̏ꍇ
			if (null != super.getItemList().get(KindItemNo)){
				
				//�q�m�[�h�@�\Bean�擾	
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
				
				//�@�\ID�擾
				strKinoId = reqKinoBean.getID();
			}

		} catch (Exception e) {
			em.ThrowException(e, "�@�\ID�̎擾�Ɏ��s���܂����B");
		} finally {
			
		}		
		return strKinoId;
	}
	
	/**
	 * �@�\ID�C���f�b�N�X�擾 
	 * @param KindId : �@�\ID 
	 * @return �@�\ID�C���f�b�N�X
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetKindIDIndex(String KindId) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnKinoIdIndex = -1;
		
		try {
			//�@�\ID���̎擾
			int iKinoIdCnt = GetKindCnt();
			
			//�@�\ID�̃C���f�b�N�X������
			for (int i = 0; i < iKinoIdCnt; i++){
				//�����l�̋@�\ID�Ɠ����ꍇ
				if (KindId == GetKindID(i)) {
					//�@�\ID�C���f�b�N�X�擾
					iRtnKinoIdIndex = i;
					break;
				}
			}

		} catch (Exception e) {
			em.ThrowException(e, "�@�\ID�C���f�b�N�X�̎擾�Ɏ��s���܂����B");
		} finally {
			
		}		
		return iRtnKinoIdIndex;
	}

	/**
	 * �e�[�u�����擾
	 * @param KindItemNo : �@�\ID�C���f�b�N�X 
	 * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @return �e�[�u����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetTableName(int KindItemNo, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strTableNm = null;
		
		try {
			//�v�f����ȊO�̏ꍇ
			if (null != super.getItemList().get(KindItemNo)){
				
				//�q�m�[�h�@�\Bean�擾
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
				
				//�v�f����ȊO�̏ꍇ
				if (null != reqKinoBean.getItemList().get(TableItemNo)){
	
					//�q�m�[�h�e�[�u��Bean�擾
					RequestResponsBean reqTableBean = (RequestResponsBean) reqKinoBean.getItemList().get(TableItemNo);
					
					//�e�[�u�����擾
					strTableNm = reqTableBean.getID();
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�e�[�u�����̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return strTableNm;
	}
	
	/**
	 * �e�[�u�����擾
	 * @param KindId : �@�\ID
	 * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @return �e�[�u����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetTableName(String KindId, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strTableNm = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u�����擾�̎擾
				strTableNm = GetTableName(iKindItemNo, TableItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�e�[�u�����̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return strTableNm;
	}

	/**
	 * �e�[�u���C���f�b�N�X�擾 
	 * @param KindItemNo : �@�\ID�C���f�b�N�X
	 * @param TableNm : �e�[�u����
	 * @return �e�[�u���C���f�b�N�X
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetTableIndex(int KindItemNo, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnTableIndex = -1;
		
		try {
			//�e�[�u�����̎擾
			int iTableCnt = GetTableCnt(KindItemNo);
			
			//�e�[�u�����̃C���f�b�N�X������
			for (int i = 0; i < iTableCnt; i++){
				//�����l�̃e�[�u�����Ɠ����ꍇ
				if (TableNm == GetTableName(KindItemNo, i)) {
					//�e�[�u���C���f�b�N�X�擾
					iRtnTableIndex = i;
					break;
				}
			}

		} catch (Exception e) {
			em.ThrowException(e, "�e�[�u���C���f�b�N�X�̎擾�Ɏ��s���܂����B");
		} finally {
			
		}		
		return iRtnTableIndex;
	}

	/**
	 * �e�[�u���C���f�b�N�X�擾 
	 * @param KindId : �@�\ID
	 * @param TableNm : �e�[�u����
	 * @return �e�[�u���C���f�b�N�X
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetTableIndex(String KindId, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnTableIndex = -1;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���C���f�b�N�X�̎擾
				iRtnTableIndex = GetTableIndex(iKindItemNo, TableNm);
			}

		} catch (Exception e) {
			em.ThrowException(e, "�e�[�u���C���f�b�N�X�̎擾�Ɏ��s���܂����B");
		} finally {
			
		}		
		return iRtnTableIndex;
	}

	/**
	 * �@�\ID�̍��ږ��擾
	 * @param KindItemNo : �@�\ID�C���f�b�N�X
	 * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @param ValueNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueName(int KindItemNo, int TableItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValueNm = null;
		
		try {
			//�v�f����ȊO�̏ꍇ
			if (null != super.getItemList().get(KindItemNo)){
				
				//�q�m�[�h�@�\Bean�擾
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
				
				//�v�f����ȊO�̏ꍇ
				if (null != reqKinoBean.getItemList().get(TableItemNo)){
	
					//�q�m�[�h�e�[�u��Bean�擾
					RequestResponsBean reqTableBean = (RequestResponsBean) reqKinoBean.getItemList().get(TableItemNo);
	
					//�v�f����ȊO�̏ꍇ
					if (null != reqTableBean.getItemList().get(0)){
	
						//�q�m�[�h�sBean�擾
						RequestResponsBean reqRowBean = (RequestResponsBean) reqTableBean.getItemList().get(0);
	
						if (null != reqRowBean.getItemList().get(ValueNo)){
	
							//�q�m�[�h����Bean�擾
							RequestResponsRow reqRow = (RequestResponsRow) reqRowBean.getItemList().get(ValueNo);
	
							//���ږ��擾
							strValueNm = reqRow.getName();
						}
					}
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ږ��擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValueNm;
	
	}
	
	/**
	 * �@�\ID�̍��ږ��擾
	 * @param KindId : �@�\ID
	 * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @param ValueNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueName(String KindId, int TableItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValueNm = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�@�\ID�̍��ږ����擾
				strValueNm = GetValueName(iKindItemNo, TableItemNo, ValueNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ږ��擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValueNm;
	
	}

	/**
	 * �@�\ID�̍��ږ��擾
	 * @param KindId : �@�\ID
	 * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @param ValueNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueName(String KindId, String TableNm, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValueNm = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//�@�\ID�̍��ږ����擾
					strValueNm = GetValueName(iKindItemNo, iTableItemNo, ValueNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ږ��擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValueNm;
	
	}

	/**
	 * �@�\ID�̍��ږ��擾
	 * @param KindItemNo : �@�\ID�C���f�b�N�X
	 * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @param ValueNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueName(int KindItemNo, String TableNm, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValueNm = null;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//�@�\ID�̍��ږ����擾
				strValueNm = GetValueName(KindItemNo, iTableItemNo, ValueNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ږ��擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValueNm;
	
	}

	/**
	 * �@�\ID�̍��ڃC���f�b�N�X�擾
	 * @param KindItemNo : �@�\ID�C���f�b�N�X
	 * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @param ValueNm : �@�\ID�̍��ږ�
	 * @return ���ڃC���f�b�N�X
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueIndex(int KindItemNo, int TableItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValueIndex = -1;
		
		try {
			//���ڐ��̎擾
			int iValueCnt = GetValueCnt(KindItemNo, TableItemNo);
			
			//���ږ��̃C���f�b�N�X������
			for (int i = 0; i < iValueCnt; i++){
				//�����l�̍��ږ��Ɠ����ꍇ
				if (ValueNm == GetValueName(KindItemNo, TableItemNo, i)) {
					//���ڃC���f�b�N�X�擾
					iRtnValueIndex = i;
					break;
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڃC���f�b�N�X�擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnValueIndex;
	
	}

	/**
	 * �@�\ID�̍��ڃC���f�b�N�X�擾
	 * @param KindId : �@�\ID
	 * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @param ValueNm : �@�\ID�̍��ږ�
	 * @return ���ڃC���f�b�N�X
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueIndex(String KindId, int TableItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValueIndex = -1;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�@�\ID�̍��ڃC���f�b�N�X���擾�̎擾
				iRtnValueIndex = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڃC���f�b�N�X�擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnValueIndex;
	
	}

	/**
	 * �@�\ID�̍��ڃC���f�b�N�X�擾
	 * @param KindId : �@�\ID
	 * @param TableNm : �e�[�u����
	 * @param ValueNm : �@�\ID�̍��ږ�
	 * @return ���ڃC���f�b�N�X
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueIndex(String KindId, String TableNm, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValueIndex = -1;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//�@�\ID�̍��ڃC���f�b�N�X���擾�̎擾
					iRtnValueIndex = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڃC���f�b�N�X�擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnValueIndex;
	
	}

	/**
	 * �@�\ID�̍��ڃC���f�b�N�X�擾
	 * @param KindItemNo : �@�\ID�C���f�b�N�X
	 * @param TableNm : �e�[�u����
	 * @param ValueNm : �@�\ID�̍��ږ�
	 * @return ���ڃC���f�b�N�X
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueIndex(int KindItemNo, String TableNm, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValueIndex = -1;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//�@�\ID�̍��ڃC���f�b�N�X���擾�̎擾
				iRtnValueIndex = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڃC���f�b�N�X�擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnValueIndex;
	
	}

	/**
	 * �@�\ID���擾
	 * @return �@�\ID�̐� 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetKindCnt() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		int ret = 0;
		try {
			
			if (null != super.getItemList()){
				ret = super.getItemList().size();
			}
			 
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		//�@�\ID���ԋp
		return ret;
	}
	
	/**
	 * �e�[�u�����擾
	 * @param KindItemNo : �@�\ID�C���f�b�N�X
	 * @return �e�[�u����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetTableCnt(int KindItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnTableCount = 0;
		
		try {
			//�v�f����ȊO�̏ꍇ
			if (null != super.getItemList().get(KindItemNo)){
				//�e�[�u�����ݒ�
				iRtnTableCount = ((RequestResponsBean) super.getItemList().get(KindItemNo)).getItemList().size(); 
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�e�[�u�����擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnTableCount;
	}
	
	/**
	 * �e�[�u�����擾
	 * @param KindId : �@�\ID
	 * @return �e�[�u����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetTableCnt(String KindId) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnTableCount = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u�������擾
				iRtnTableCount = GetTableCnt(iKindItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�e�[�u�����擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnTableCount;
	}
	
  /**
    * ���R�[�h���̎擾
    * @param KindItemo : �@�\ID�C���f�b�N�X
    * @param TableItemNo : �e�[�u���C���f�b�N�X
    * @return ���R�[�h��
	* @throws ExceptionWaning 
	* @throws ExceptionUser 
	* @throws ExceptionSystem 
    */
	public int GetRecCnt(int KindItemNo, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnRecCount = 0;
		try {
			//�v�f����ȊO�̏ꍇ
			if (null != super.getItemList().get(KindItemNo)){
				
				//�q�m�[�h�@�\Bean�擾
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
	
				if (null != reqKinoBean.getItemList().get(TableItemNo)){
					//���R�[�h���ݒ�
					iRtnRecCount = ((RequestResponsBean) reqKinoBean.getItemList().get(TableItemNo)).getItemList().size(); 				
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "���R�[�h���̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnRecCount;
	}
	
	/**
	 * ���R�[�h���̎擾
	 * @param KindId : �@�\ID
	 * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @return ���R�[�h��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetRecCnt(String KindId, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnRecCount = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//���R�[�h�����擾
				iRtnRecCount = GetRecCnt(iKindItemNo, TableItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "���R�[�h���̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnRecCount;
	}
	
	/**
	 * ���R�[�h���̎擾
	 * @param KindId : �@�\ID
	 * @param TableNm : �e�[�u����
	 * @return ���R�[�h��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetRecCnt(String KindId, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnRecCount = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//���R�[�h�����擾
					iRtnRecCount = GetRecCnt(iKindItemNo, iTableItemNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "���R�[�h���̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnRecCount;
	}
	
	/**
	 * ���R�[�h���̎擾
	 * @param KindItemNo : �@�\ID�C���f�b�N�X
	 * @param TableNm : �e�[�u����
	 * @return ���R�[�h��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetRecCnt(int KindItemNo, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnRecCount = 0;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//���R�[�h�����擾
				iRtnRecCount = GetRecCnt(KindItemNo, iTableItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "���R�[�h���̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnRecCount;
	}
	
	/**
	 * ���ڐ�
     * @param KindItemo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @return ���ڐ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueCnt(int KindItemNo, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValCount = 0;
		
		try {
			//�v�f����ȊO�̏ꍇ
			if (null != super.getItemList().get(KindItemNo)){
				
				//�q�m�[�h�@�\Bean�擾
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
	
				if (null != reqKinoBean.getItemList().get(TableItemNo)){
					
					//�q�m�[�h�e�[�u��Bean�擾
					RequestResponsBean reqTableBean = (RequestResponsBean) reqKinoBean.getItemList().get(TableItemNo);
					
					if (null != reqTableBean.getItemList().get(0)){
						
						//���ڐ��ݒ�
						iRtnValCount = ((RequestResponsBean) reqTableBean.getItemList().get(0)).getItemList().size();
					}
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "���ڐ��̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnValCount;
	}
	
	/**
	 * ���ڐ�
     * @param KindItemo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
	 * @return ���ڐ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueCnt(String KindId, int TableItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValCount = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//���ڐ����擾
				iRtnValCount = GetValueCnt(iKindItemNo, TableItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "���ڐ��̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnValCount;
	}
	
	/**
	 * ���ڐ�
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
	 * @return ���ڐ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueCnt(String KindId, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValCount = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//���ڐ����擾
					iRtnValCount = GetValueCnt(iKindItemNo, iTableItemNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "���ڐ��̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnValCount;
	}
	
	/**
	 * ���ڐ�
     * @param KindItemo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u����
	 * @return ���ڐ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueCnt(int KindItemNo, String TableNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValCount = 0;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//���ڐ����擾
				iRtnValCount = GetValueCnt(KindItemNo, iTableItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "���ڐ��̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return iRtnValCount;
	}
	
	/**
	 * �@�\ID�̍��ڒl
     * @param KindItemo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//�v�f����ȊO�̏ꍇ
			if (null != super.getItemList().get(KindItemNo)){
				
				//�q�m�[�h�@�\Bean�擾
				RequestResponsBean reqKinoBean = (RequestResponsBean) super.getItemList().get(KindItemNo);
				
				//�v�f����ȊO�̏ꍇ
				if (null != reqKinoBean.getItemList().get(TableItemNo)){
	
					//�q�m�[�h�e�[�u��Bean�擾
					RequestResponsBean reqTableBean = (RequestResponsBean) reqKinoBean.getItemList().get(TableItemNo);
	
					//�v�f����ȊO�̏ꍇ
					if (null != reqTableBean.getItemList().get(RecItemNo)){
	
						//�q�m�[�h�sBean�擾
						RequestResponsBean reqRowBean = (RequestResponsBean) reqTableBean.getItemList().get(RecItemNo);
	
						if (null != reqRowBean.getItemList().get(ValueNo)){
	
							//�q�m�[�h����Bean�擾
							RequestResponsRow reqRow = (RequestResponsRow) reqRowBean.getItemList().get(ValueNo);
	
							//���ڒl�擾
							strValue = reqRow.getValue();
						}
					}
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl�̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValue;
	}
	
	/**
	 * �@�\ID�̍��ڒl
     * @param KindId : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�@�\ID�̍��ڒl���擾
				strValue = GetValue(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl�̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * �@�\ID�̍��ڒl
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl���擾
					strValue = GetValue(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl�̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * �@�\ID�̍��ڒl
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//���ڂ̃C���f�b�N�X���擾
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
					if (iValueItemNo != -1) {
						//�@�\ID�̍��ڒl���擾
						strValue = GetValue(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl�̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * �@�\ID�̍��ڒl
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl���擾
					strValue = GetValue(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl�̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * �@�\ID�̍��ڒl
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//���ڂ̃C���f�b�N�X���擾
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iValueItemNo != -1) {
				//�@�\ID�̍��ڒl���擾
				strValue = GetValue(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl�̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * �@�\ID�̍��ڒl
     * @param KindId : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl���擾
					strValue = GetValue(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl�̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * �@�\ID�̍��ڒl
     * @param KindId : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValue(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strValue = null;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//�@�\ID�̍��ڒl���擾
				strValue = GetValue(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}

		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl�̎擾�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return strValue;
	}

	/**
	 * �@�\ID�̍��ڒl(int)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//�擾���ڒl��NULL�ȊO�̏ꍇ
			if (null != GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo)){
				
				//���ڒl��int�^�ɂĐݒ�
				iRtnValue = Integer.parseInt(GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo));
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(int)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * �@�\ID�̍��ڒl(int)
     * @param KindId : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�@�\ID�̍��ڒl(int)���擾
				iRtnValue = GetValueInt(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(int)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * �@�\ID�̍��ڒl(int)
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl(int)���擾
					iRtnValue = GetValueInt(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(int)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * �@�\ID�̍��ڒl(int)
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//���ڂ̃C���f�b�N�X���擾
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
					if (iValueItemNo != -1) {
						//�@�\ID�̍��ڒl(int)���擾
						iRtnValue = GetValueInt(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(int)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * �@�\ID�̍��ڒl(int)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl(int)���擾
					iRtnValue = GetValueInt(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(int)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * �@�\ID�̍��ڒl(int)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//���ڂ̃C���f�b�N�X���擾
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iValueItemNo != -1) {
				//�@�\ID�̍��ڒl(int)���擾
				iRtnValue = GetValueInt(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(int)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * �@�\ID�̍��ڒl(int)
     * @param String : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl(int)���擾
					iRtnValue = GetValueInt(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(int)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * �@�\ID�̍��ڒl(int)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u��
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(int)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetValueInt(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iRtnValue = 0;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl(int)���擾
					iRtnValue = GetValueInt(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(int)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return iRtnValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Decimal)
     * @param KindItemo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
	
		BigDecimal dciValue = null;
	
		try {
			//�擾���ڒl��NULL�ȊO�̏ꍇ
			if (null != GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo)){
				
				//���ڒl��BigDecimal�^�ɂĐݒ�
				dciValue = new BigDecimal(GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo));
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Decimal)�Ɏ��s���܂����B");
			
		} finally {
			
		}		

		return dciValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Decimal)
     * @param KindId : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�@�\ID�̍��ڒl(Decimal)���擾
				dciValue = GetValueDec(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Decimal)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Decimal)
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl(Decimal)���擾
					dciValue = GetValueDec(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Decimal)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Decimal)
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//���ڂ̃C���f�b�N�X���擾
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
					if (iValueItemNo != -1) {
						//�@�\ID�̍��ڒl(Decimal)���擾
						dciValue = GetValueDec(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Decimal)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Decimal)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl(Decimal)���擾
					dciValue = GetValueDec(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Decimal)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Decimal)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//���ڂ̃C���f�b�N�X���擾
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iValueItemNo != -1) {
				//�@�\ID�̍��ڒl(Decimal)���擾
				dciValue = GetValueDec(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Decimal)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Decimal)
     * @param String : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl(Decimal)���擾
					dciValue = GetValueDec(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Decimal)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Decimal)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u��
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Decimal)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public BigDecimal GetValueDec(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		BigDecimal dciValue = null;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl(Decimal)���擾
					dciValue = GetValueDec(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Decimal)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dciValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Double)
     * @param KindItemo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//�擾���ڒl��NULL�ȊO�̏ꍇ
			if (null != GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo)){
				
				//���ڒl��double�^�ɂĐݒ�
				dblValue = Double.parseDouble(GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo));
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Double)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dblValue;

	}

	/**
	 * �@�\ID�̍��ڒl(Double)
     * @param KindId : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�@�\ID�̍��ڒl(Double)���擾
				dblValue = GetValueDub(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Double)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Double)
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl(Double)���擾
					dblValue = GetValueDub(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Double)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Double)
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//���ڂ̃C���f�b�N�X���擾
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
					if (iValueItemNo != -1) {
						//�@�\ID�̍��ڒl(Double)���擾
						dblValue = GetValueDub(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Double)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Double)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl(Double)���擾
					dblValue = GetValueDub(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Double)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Double)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//���ڂ̃C���f�b�N�X���擾
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iValueItemNo != -1) {
				//�@�\ID�̍��ڒl(Double)���擾
				dblValue = GetValueDub(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Double)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Double)
     * @param String : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl(Double)���擾
					dblValue = GetValueDub(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Double)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Double)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u��
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Double)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public double GetValueDub(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		double dblValue = 0;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl(Double)���擾
					dblValue = GetValueDub(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Double)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dblValue;
	}

	/**
	 * �@�\ID�̍��ڒl(String)
     * @param KindItemo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String ret = "";
		
		try {
			ret = GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		
		//���ڒl��String�ɂĕԋp
		return ret;
	}

	/**
	 * �@�\ID�̍��ڒl(String)
     * @param KindId : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�@�\ID�̍��ڒl(String)���擾
				ret = GetValueStr(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(String)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * �@�\ID�̍��ڒl(String)
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl(String)���擾
					ret = GetValueStr(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(String)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * �@�\ID�̍��ڒl(String)
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//���ڂ̃C���f�b�N�X���擾
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
					if (iValueItemNo != -1) {
						//�@�\ID�̍��ڒl(String)���擾
						ret = GetValueStr(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(String)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * �@�\ID�̍��ڒl(String)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl(String)���擾
					ret = GetValueStr(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(String)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * �@�\ID�̍��ڒl(String)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//���ڂ̃C���f�b�N�X���擾
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iValueItemNo != -1) {
				//�@�\ID�̍��ڒl(String)���擾
				ret = GetValueStr(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(String)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * �@�\ID�̍��ڒl(String)
     * @param String : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl(String)���擾
					ret = GetValueStr(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(String)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * �@�\ID�̍��ڒl(String)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u��
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(String)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetValueStr(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String ret = "";
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl(String)���擾
					ret = GetValueStr(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(String)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return ret;
	}

	/**
	 * �@�\ID�̍��ڒl(Date)
     * @param KindItemo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(int KindItemNo, int TableItemNo, int RecItemNo, int ValueNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//�擾���ڒl��NULL�ȊO�̏ꍇ
			if (null != GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo)){
	
				//���ڒl��date�^�ɂĐݒ�
				DateFormat foramt = new SimpleDateFormat("yyyy/MM/dd"); 
				dateValue = foramt.parse(GetValue(KindItemNo, TableItemNo, RecItemNo, ValueNo));
			}
		} catch (Exception e) {
			em.ThrowException(e, "���t�^�ւ̕ϊ��Ɏ��s���܂����B");
		} 

		return dateValue;
	}
	
	/**
	 * �@�\ID�̍��ڒl(Date)
     * @param KindId : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(String KindId, int TableItemNo, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�@�\ID�̍��ڒl(Date)���擾
				dateValue = GetValueDate(iKindItemNo, TableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Date)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Date)
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(String KindId, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl(Date)���擾
					dateValue = GetValueDate(iKindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Date)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Date)
     * @param KindId : �@�\ID
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(String KindId, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//�e�[�u���̃C���f�b�N�X���擾
				int iTableItemNo = GetTableIndex(iKindItemNo, TableNm);
			
				//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iTableItemNo != -1) {
					//���ڂ̃C���f�b�N�X���擾
					int iValueItemNo = GetValueIndex(iKindItemNo, iTableItemNo, ValueNm);
				
					//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
					if (iValueItemNo != -1) {
						//�@�\ID�̍��ڒl(Date)���擾
						dateValue = GetValueDate(iKindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
					}
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Date)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Date)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u����
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(int KindItemNo, String TableNm, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(KindItemNo, iTableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl(Date)���擾
					dateValue = GetValueDate(KindItemNo, iTableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Date)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Date)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(int KindItemNo, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//���ڂ̃C���f�b�N�X���擾
			int iValueItemNo = GetValueIndex(KindItemNo, TableItemNo, ValueNm);
		
			//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iValueItemNo != -1) {
				//�@�\ID�̍��ڒl(Date)���擾
				dateValue = GetValueDate(KindItemNo, TableItemNo, RecItemNo, iValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Date)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Date)
     * @param String : �@�\ID
     * @param TableItemNo : �e�[�u���C���f�b�N�X
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueNm : ���ږ�
	 * @return �@�\ID�̍��ڒl(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(String KindId, int TableItemNo, int RecItemNo, String ValueNm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//�@�\ID�̃C���f�b�N�X���擾
			int iKindItemNo = GetKindIDIndex(KindId);
		
			//�@�\ID�C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iKindItemNo != -1) {
				//���ڂ̃C���f�b�N�X���擾
				int iValueItemNo = GetValueIndex(iKindItemNo, TableItemNo, ValueNm);
			
				//���ڃC���f�b�N�X������Ɏ擾�ł����ꍇ
				if (iValueItemNo != -1) {
					//�@�\ID�̍��ڒl(Date)���擾
					dateValue = GetValueDate(iKindItemNo, TableItemNo, RecItemNo, iValueItemNo);
				}
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Date)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dateValue;
	}

	/**
	 * �@�\ID�̍��ڒl(Date)
     * @param KindItemNo : �@�\ID�C���f�b�N�X
     * @param TableNm : �e�[�u��
     * @param RecItemNo : ���R�[�h�C���f�b�N�X
     * @param ValueItemNo : ���ڃC���f�b�N�X
	 * @return �@�\ID�̍��ڒl(Date)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Date GetValueDate(int KindItemNo, String TableNm, int RecItemNo, int ValueItemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Date dateValue = null;
		
		try {
			//�e�[�u���̃C���f�b�N�X���擾
			int iTableItemNo = GetTableIndex(KindItemNo, TableNm);
		
			//�e�[�u���C���f�b�N�X������Ɏ擾�ł����ꍇ
			if (iTableItemNo != -1) {
					//�@�\ID�̍��ڒl(Date)���擾
					dateValue = GetValueDate(KindItemNo, iTableItemNo, RecItemNo, ValueItemNo);
			}
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\ID�̍��ڒl(Date)�Ɏ��s���܂����B");
			
		} finally {
			
		}		
		
		return dateValue;
	}

}
