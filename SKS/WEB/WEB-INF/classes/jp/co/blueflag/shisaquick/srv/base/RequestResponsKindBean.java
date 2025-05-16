package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * ���N�G�X�g/���X�|���XKaind (�@�\ID)
 *  : ���N�G�X�g/���X�|���X�̃f�[�^�ێ��ŁA�ŏ��P�ʂ̃f�[�^�Ǘ����s��
 * @author TT.isono
 * @since  2009/03/24
 */
public class RequestResponsKindBean  extends RequestResponsBean {

	/**
	 * �R���X�g���N�^
	 */
	public RequestResponsKindBean() {
		//���׽�̃R���X�g���N�^
		super();
		
	}
	/**
	 * �e�[�u���N���X���擾
	 * @param itemNo�F�e�[�u���C���f�b�N�X
	 * @return �e�[�u��RequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsTableBean getTableItem(int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsTableBean ret = null;
		
		try{
			//item �擾
			ret = (RequestResponsTableBean) GetItem(itemNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �e�[�u���̎擾�Ɏ��s���܂����BitemNo=" + Integer.toString(itemNo));
		}finally{
			
		}
		return ret;
	}
	/**
	 * �e�[�u���N���X���擾
	 * @param itemName�F�e�[�u����
	 * @return �e�[�u��RequestResponsTableBean
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public RequestResponsTableBean getTableItem(String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsTableBean ret = null;
		
		try{
			//item �擾
			ret = (RequestResponsTableBean) getTableItem(GetItemNo(itemName));
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �e�[�u���̎擾�Ɏ��s���܂����BitemNo=" + itemName);
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * �e�[�u����޼ު�Ă��Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param itemTable�F�e�[�u����޼ު��
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public void setTableItem( int TableNo, RequestResponsTableBean itemTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item ���Z�b�g����
			SetItem(itemTable, TableNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �e�[�u���̃Z�b�g�Ɏ��s���܂����BitemNo="
					+ Integer.toString(TableNo));
			
		}finally{
			
		}
		
	}
	/**
	 * �e�[�u����޼ު�Ă��Z�b�g����
	 * @param TableName�F�e�[�u����
	 * @param itemTable�F�e�[�u����޼ު��
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public void setTableItem(String TableName, RequestResponsTableBean itemTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item ���Z�b�g����
			SetItem(itemTable, GetItemNo(TableName));
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �e�[�u���̃Z�b�g�Ɏ��s���܂����BitemNo=" + TableName);
			
		}finally{
			
		}
		
	}
	/**
	 * �e�[�u���̒ǉ�
	 * @param itemTable�F�e�[�u����޼ު��
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public void addTableItem(RequestResponsTableBean itemTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item ��ǉ�����
			AddItem(itemTable);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�e�[�u���̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �e�[�u���̒ǉ�
	 * @param TableID�F�e�[�u��ID
	 * @return �e�[�u����޼ު��
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public RequestResponsTableBean addTableItem(String TableID)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsTableBean ret = new RequestResponsTableBean();
		
		try{
			//item ��ǉ�����
			AddItem(ret);
			ret.setID(TableID);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�e�[�u���̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �e�[�u���̒ǉ�
	 * @param itemTable�F�e�[�u����޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public void addTableItem(RequestResponsTableBean itemTable,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item ��ǉ�����
			AddItem(itemTable,ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�e�[�u���̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �e�[�u����ID���擾����
	 * @param itemNo�F�e�[�u���̃C���f�b�N�X
	 * @return�@�e�[�u���̒l
	 * @Throws ExceptionSystem
	 * @Throws ExceptionUser
	 * @Throws ExceptionWaning
	 */
	public String getTableID(int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getTableItem(itemNo).getID();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�e�[�u���̒l�A�擾�Ɏ��s���܂����BitemNo" + Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}

	//-----------------------------------------------------------------------------

	/**
	 * ���[�N���X���擾
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @return ���[RequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(int TableNo, int RowNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item �擾
			ret = getTableItem(TableNo).getRowItem(RowNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̎擾�Ɏ��s���܂����BitemNo=" 
					+ Integer.toString(RowNo));
		}finally{
			
		}
		return ret;
	}
	/**
	 * ���[�N���X���擾
	 * @param TableID�F�e�[�u��ID
	 * @param RowNo�F���[�C���f�b�N�X
	 * @return ���[RequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(String TableID, int RowNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item �擾
			ret = getTableItem(TableID).getRowItem(RowNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̎擾�Ɏ��s���܂����BitemNo=" 
					+ Integer.toString(RowNo));
		}finally{
			
		}
		return ret;
	}
	/**
	 * ���[�N���X���擾
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowName�F���[��
	 * @return ���[RequestResponsRowBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(int TableNo, String RowName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item �擾
			ret = getTableItem(TableNo).getRowItem(RowName);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̎擾�Ɏ��s���܂����BitemNo=" + RowName);
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * ���[�N���X���擾
	 * @param TableID�F�e�[�u��ID
	 * @param RowName�F���[��
	 * @return ���[RequestResponsRowBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(String TableID, String RowName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item �擾
			ret = getTableItem(TableID).getRowItem(RowName);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̎擾�Ɏ��s���܂����BitemNo=" + RowName);
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * ���[��޼ު�Ă��Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem(int TableNo, int RowNo, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getTableItem(TableNo).setRowItem(RowNo, itemRow);
				
			}catch(Exception e){
				addRowItem(TableNo, itemRow);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̃Z�b�g�Ɏ��s���܂����BitemNo=" + Integer.toString(RowNo));
			
		}finally{
			
		}
		
	}
	/**
	 * ���[��޼ު�Ă��Z�b�g����
	 * @param TableID�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem(String TableID, int RowNo, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getTableItem(TableID).setRowItem(RowNo, itemRow);
				
			}catch(Exception e){
				addRowItem(TableID, itemRow);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̃Z�b�g�Ɏ��s���܂����BitemNo="
					+ Integer.toString(RowNo));
			
		}finally{
			
		}
		
	}
	/**
	 * ���[��޼ު�Ă��Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[��
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem(int TableNo, String RowID, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getTableItem(TableNo).setRowItem(RowID, itemRow);
				
			}catch(Exception e){
				addRowItem(TableNo, itemRow);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̃Z�b�g�Ɏ��s���܂����BitemNo=" + RowID);
			
		}finally{
			
		}
		
	}
	/**
	 * ���[��޼ު�Ă��Z�b�g����
	 * @param TableID�F�e�[�u��ID
	 * @param RowID�F���[��
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem(String TableID, String RowID, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getTableItem(TableID).setRowItem(RowID, itemRow);
				
			}catch(Exception e){
				addRowItem(TableID, itemRow);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̃Z�b�g�Ɏ��s���܂����BitemNo=" + RowID);
			
		}finally{
			
		}
		
	}
	/**
	 * ���[�̒ǉ�
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(int TableNo, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item ��ǉ�����
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			TableBean.addRowItem(itemRow);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * ���[�̒ǉ�
	 * @param TableID�F�e�[�u��ID
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(String TableID, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item ��ǉ�����
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			TableBean.addRowItem(itemRow);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * ���[�̒ǉ�
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean addRowItem(int TableNo, String RowID)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsRowBean RowBean = null; 
		
		try{
			//item ��ǉ�����
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.addRowItem(RowID);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		return RowBean;
		
	}
	/**
	 * ���[�̒ǉ�
	 * @param TableID�F�e�[�u��ID
	 * @param RowID�F���[ID
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean addRowItem(String TableID, String RowID)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsRowBean RowBean = null; 
		
		try{
			//item ��ǉ�����
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.addRowItem(RowID);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		return RowBean;
		
	}
	/**
	 * ���[�̒ǉ�
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param itemRow�F���[��޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(int TableNo, RequestResponsRowBean itemRow,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item ��ǉ�����
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("rec");
			}
			TableBean.addRowItem(itemRow, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * ���[�̒ǉ�
	 * @param TableID�F�e�[�u��ID
	 * @param itemRow�F���[��޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(String TableID, RequestResponsRowBean itemRow,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item ��ǉ�����
			RequestResponsTableBean TableBean = null;
			
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			TableBean.addRowItem(itemRow, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * ���[��ID���擾����
	 * @param TableNo�F�e�[�u���̃C���f�b�N�X
	 * @param RowNo�F���[�̃C���f�b�N�X
	 * @return�@���[�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getRowID(int TableNo, int RowNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getTableItem(TableNo).getRowID(RowNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒l�A�擾�Ɏ��s���܂����BitemNo"
					+ Integer.toString(RowNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * ���[��ID���擾����
	 * @param TableID�F�e�[�u��ID
	 * @param RowNo�F���[�̃C���f�b�N�X
	 * @return�@���[�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getRowID(String TableID, int RowNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getTableItem(TableID).getRowID(RowNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒l�A�擾�Ɏ��s���܂����BitemNo"
					+ Integer.toString(RowNo));
			
		}finally{
			
		}
		return ret;
		
	}

	//-----------------------------------------------------------------------------

	/**
	 * �t�B�[���h�N���X���擾
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int TableNo, int RowNo, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(TableNo, RowNo).getFieldItem(FieldNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̎擾�Ɏ��s���܂����B"
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "FieldNo="
					+ Integer.toString(FieldNo)
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * �t�B�[���h�N���X���擾
	 * @param TableID�F�e�[�u��ID
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String TableID, int RowNo, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(TableID, RowNo).getFieldItem(FieldNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̎擾�Ɏ��s���܂����B"
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "FieldNo="
					+ Integer.toString(FieldNo)
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * �t�B�[���h�N���X���擾
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int TableNo, String RowID, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(TableNo, RowID).getFieldItem(FieldNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̎擾�Ɏ��s���܂����B"
					+ "RowID="
					+ RowID
					+ "FieldNo="
					+ Integer.toString(FieldNo)
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * �t�B�[���h�N���X���擾
	 * @param TableID�F�e�[�u��ID
	 * @param RowID�F���[ID
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String TableID, String RowID, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(TableID, RowID).getFieldItem(FieldNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̎擾�Ɏ��s���܂����B"
					+ "RowID="
					+ RowID
					+ "FieldNo="
					+ Integer.toString(FieldNo)
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * �t�B�[���h�N���X���擾
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[����
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int TableNo, int RowNo, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(TableNo, RowNo).getFieldItem(FieldName);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̎擾�Ɏ��s���܂����B"
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "FieldNo="
					+ FieldName
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * �t�B�[���h�N���X���擾
	 * @param TableID�F�e�[�u��ID
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[����
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String TableID, int RowNo, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(TableID, RowNo).getFieldItem(FieldName);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̎擾�Ɏ��s���܂����B"
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "FieldNo="
					+ FieldName
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * �t�B�[���h�N���X���擾
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[���h�C���f�b�N�X
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int TableNo, String RowID, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(TableNo, RowID).getFieldItem(FieldName);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̎擾�Ɏ��s���܂����B"
					+ "RowID="
					+ RowID
					+ "FieldName="
					+ FieldName
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * �t�B�[���h�N���X���擾
	 * @param TableID�F�e�[�u��ID
	 * @param RowID�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[���h�C���f�b�N�X
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String TableID, String RowID, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(TableID, RowID).getFieldItem(FieldName);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̎擾�Ɏ��s���܂����B"
					+ "RowID="
					+ RowID
					+ "FieldName="
					+ FieldName
					);
		}finally{
			
		}
		return ret;
	}
	/**
	 * �t�B�[���h��޼ު�Ă��Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int TableNo, int RowNo, int FieldNo ,RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getRowItem(TableNo, RowNo).setFieldItem(FieldNo, itemField);
				
			}catch(Exception e){
				addFieldItem(TableNo, RowNo, itemField);
				
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̃Z�b�g�Ɏ��s���܂����B"					
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "itemNo="
					+ Integer.toString(FieldNo)
					);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă��Z�b�g����
	 * @param TableID�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String TableID, int RowNo, int FieldNo ,RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getRowItem(TableID, RowNo).setFieldItem(FieldNo, itemField);
				
			}catch(Exception e){
				addFieldItem(TableID, RowNo, itemField);
				
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̃Z�b�g�Ɏ��s���܂����B"					
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "itemNo="
					+ Integer.toString(FieldNo)
					);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă��Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int TableNo, String RowID, int FieldNo ,RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getRowItem(TableNo, RowID).setFieldItem(FieldNo, itemField);
				
			}catch(Exception e){
				addFieldItem(TableNo, RowID, itemField);
				
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̃Z�b�g�Ɏ��s���܂����B"					
					+ "RowNo="
					+ RowID
					+ "itemNo="
					+ Integer.toString(FieldNo)
					);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă��Z�b�g����
	 * @param TableID�F�e�[�u��ID
	 * @param RowID�F���[ID
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String TableID, String RowID, int FieldNo ,RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getRowItem(TableID, RowID).setFieldItem(FieldNo, itemField);
				
			}catch(Exception e){
				addFieldItem(TableID, RowID, itemField);
				
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̃Z�b�g�Ɏ��s���܂����B"					
					+ "RowNo="
					+ RowID
					+ "itemNo="
					+ Integer.toString(FieldNo)
					);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă��Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[���h��
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int TableNo, int RowNo, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getRowItem(TableNo, RowNo).setFieldItem(FieldName,itemField);
				
			}catch(Exception e){
				addFieldItem(TableNo, RowNo, itemField);
				
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̃Z�b�g�Ɏ��s���܂����B"					
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "itemName="
					+ FieldName
					);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă��Z�b�g����
	 * @param TableID�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[���h��
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String TableID, int RowNo, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getRowItem(TableID, RowNo).setFieldItem(FieldName, itemField);
				
			}catch(Exception e){
				addFieldItem(TableID, RowNo, itemField);
				
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̃Z�b�g�Ɏ��s���܂����B"					
					+ "RowNo="
					+ Integer.toString(RowNo)
					+ "itemName="
					+ FieldName
					);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă��Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param FieldName�F�t�B�[���h��
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int TableNo, String RowID, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getRowItem(TableNo, RowID).setFieldItem(FieldName, itemField);
				
			}catch(Exception e){
				addFieldItem(TableNo, RowID, itemField);
				
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̃Z�b�g�Ɏ��s���܂����B"					
					+ "RowID="
					+ RowID
					+ "itemName="
					+ FieldName
					);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă��Z�b�g����
	 * @param TableID�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param FieldName�F�t�B�[���h��
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String TableID, String RowID, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			try{
				//item ���Z�b�g����
				getRowItem(TableID, RowID).setFieldItem(FieldName, itemField);
				
			}catch(Exception e){
				addFieldItem(TableID, RowID, itemField);
				
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̃Z�b�g�Ɏ��s���܂����B"					
					+ "RowID="
					+ RowID
					+ "itemName="
					+ FieldName
					);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă̒ǉ�
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int TableNo, int RowNo, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item ��ǉ�����
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă̒ǉ�
	 * @param TableID�F�e�[�u��ID
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(String TableID, int RowNo, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item ��ǉ�����
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă̒ǉ�
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int TableNo, String RowID, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item ��ǉ�����
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowID);
			if (RowBean == null){
				RowBean = TableBean.addRowItem(RowID);
			}
			
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă̒ǉ�
	 * @param TableID�F�e�[�u��ID
	 * @param RowID�F���[ID
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(String TableID, String RowID, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item ��ǉ�����
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.getRowItem(RowID);
			if (RowBean == null){
				RowBean = TableBean.addRowItem(RowID);
			}
			
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă̒ǉ�
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int TableNo, int RowNo, RequestResponsFieldBean itemField,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item ��ǉ�����
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			
			RowBean.addFieldItem(itemField, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă̒ǉ�
	 * @param TableID�F�e�[�u��ID
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(String TableID, int RowNo, RequestResponsFieldBean itemField,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item ��ǉ�����
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			RowBean.addFieldItem(itemField, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă̒ǉ�
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int TableNo, String RowID, RequestResponsFieldBean itemField,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;

			//item ��ǉ�����
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowID);
			if (RowBean == null){
				RowBean = TableBean.addRowItem(RowID);
			}
			
			RowBean.addFieldItem(itemField, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemNo�F�t�B�[���h�̃C���f�b�N�X
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int TableNo, int RowNo , int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(TableNo, RowNo, itemNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemNo" + Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param TableID�F�e�[�u��ID
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemNo�F�t�B�[���h�̃C���f�b�N�X
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String TableID, int RowNo , int itemNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(TableID, RowNo, itemNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemNo" 
					+ Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param itemNo�F�t�B�[���h�̃C���f�b�N�X
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int TableNo, String RowID , int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(TableNo, RowID, itemNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemNo" 
					+ Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param TableID�F�e�[�u��ID
	 * @param RowID�F���[ID
	 * @param itemNo�F�t�B�[���h�̃C���f�b�N�X
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String TableID, String RowID , int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(TableID, RowID, itemNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemNo" 
					+ Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemName�F�t�B�[���h��
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int TableNo, int RowNo, String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(TableNo, RowNo, itemName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemName=" 
					+ itemName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param TableID�F�e�[�uID
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemName�F�t�B�[���h��
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String TableID, int RowNo, String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(TableID, RowNo, itemName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemName=" + itemName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param itemName�F�t�B�[���h��
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int TableNo, String RowID, String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(TableNo, RowID, itemName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemName=" + itemName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param TableID�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param itemName�F�t�B�[���h��
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String TableID, String RowID, String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(TableID, RowID, itemName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemName=" + itemName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int TableNo, int RowNo, int FieldNo, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			getFieldItem(TableNo, RowNo, FieldNo).setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo="
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param TableID�F�e�[�u��ID
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String TableID, int RowNo, int FieldNo, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			getFieldItem(TableID, RowNo, FieldNo).setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo="
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int TableNo, String RowID, int FieldNo, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			getFieldItem(TableNo, RowID, FieldNo).setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo="
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param TableID�F�e�[�u��ID
	 * @param RowID�F���[ID
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String TableID, String RowID, int FieldNo, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			getFieldItem(TableID, RowID, FieldNo).setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo=" 
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[���h��
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int TableNo, int RowNo, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			try{
				//�l���Z�b�g
				getFieldItem(TableNo, RowNo, FieldName).setValue(itemValue);
				
			}catch(Exception e){
				addFieldVale(TableNo, RowNo, FieldName, itemValue);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param TableID�F�e�[�u��ID
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[���h��
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String TableID, int RowNo, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			try{
				//�l���Z�b�g
				getFieldItem(TableID, RowNo, FieldName).setValue(itemValue);
				
			}catch(Exception e){
				addFieldVale(TableID, RowNo, FieldName, itemValue);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param FieldName�F�t�B�[���h��
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int TableNo, String RowID, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			try{
				getTableItem(TableNo).getRowItem(RowID).getFieldItem(FieldName).setValue(itemValue);
				
			}catch(Exception e){
				addFieldVale(TableNo, RowID, FieldName, itemValue);		
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param TableID�F�e�[�u��ID
	 * @param RowID�F���[ID
	 * @param FieldName�F�t�B�[���h��
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String TableID, String RowID, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			try{
				getTableItem(TableID).getRowItem(RowID).getFieldItem(FieldName).setValue(itemValue);
				
			}catch(Exception e){
				addFieldVale(TableID, RowID, FieldName, itemValue);		
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l��ǉ�����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[���h��
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(int TableNo, int RowNo, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;
			RequestResponsFieldBean FieldBean = null;

			//�l���Z�b�g
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			FieldBean = RowBean.getFieldItem(FieldName);
			if (FieldBean == null){
				FieldBean = RowBean.addFieldItem(FieldName);
			}
			
			FieldBean.setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�ǉ��Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l��ǉ�����
	 * @param TableID�F�e�[�u��ID
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[���h��
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(String TableID, int RowNo, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;
			RequestResponsFieldBean FieldBean = null;

			//�l���Z�b�g
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.getRowItem(RowNo);
			if (RowBean == null){
				RowBean = TableBean.addRowItem("rec");
			}
			FieldBean = RowBean.getFieldItem(FieldName);
			if (FieldBean == null){
				FieldBean = RowBean.addFieldItem(FieldName);
			}
			FieldBean.setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�ǉ��Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l��ǉ�����
	 * @param TableNo�F�e�[�u���C���f�b�N�X
	 * @param RowID�F���[ID
	 * @param FieldName�F�t�B�[���h��
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(int TableNo, String RowID, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l��ǉ�
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;
			RequestResponsFieldBean FieldBean = null;

			//�l���Z�b�g
			TableBean = getTableItem(TableNo);
			if (TableBean == null){
				TableBean = addTableItem("table");
			}
			RowBean = TableBean.getRowItem(RowID);
			if (RowBean == null){
				RowBean = TableBean.addRowItem(RowID);
			}
			FieldBean = RowBean.getFieldItem(FieldName);
			if (FieldBean == null){
				FieldBean = RowBean.addFieldItem(FieldName);
			}
			FieldBean.setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�ǉ��Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l��ǉ�����
	 * @param TableID�F�e�[�u��ID
	 * @param RowID�F���[ID
	 * @param FieldName�F�t�B�[���h��
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(String TableID, String RowID, String FieldName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l��ǉ�
			RequestResponsTableBean TableBean = null;
			RequestResponsRowBean RowBean = null;
			RequestResponsFieldBean FieldBean = null;

			//�l���Z�b�g
			TableBean = getTableItem(TableID);
			if (TableBean == null){
				TableBean = addTableItem(TableID);
			}
			RowBean = TableBean.getRowItem(RowID);
			if (RowBean == null){
				RowBean = TableBean.addRowItem(RowID);
			}
			FieldBean = RowBean.getFieldItem(FieldName);
			if (FieldBean == null){
				FieldBean = RowBean.addFieldItem(FieldName);
			}
			FieldBean.setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�ǉ��Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	public int getCntTable() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntRow(int TableNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getTableItem(TableNo).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntRow(String TableID) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getTableItem(TableID).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntField(int TableNo, int RowNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getRowItem(TableNo, RowNo).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntField(String TableID, int RowNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getRowItem(TableID, RowNo).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntField(int TableNo, String RowID) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getRowItem(TableNo, RowID).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	public int getCntField(String TableID, String RowID) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;
		
		try{
			ret = getRowItem(TableID, RowID).GetCnt();
			
		}catch(Exception e){
			this.em.ThrowException(e, "");
			
		}finally{

		}
		return ret;
	
	}
	
	
}
