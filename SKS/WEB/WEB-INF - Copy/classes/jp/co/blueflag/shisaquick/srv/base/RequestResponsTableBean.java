package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * ���N�G�X�g/���X�|���XTable
 *  : ���N�G�X�g/���X�|���X�̃f�[�^�ێ��ŁA�ŏ��P�ʂ̃f�[�^�Ǘ����s��
 * @author TT.isono
 * @since  2009/03/24
 */
public class RequestResponsTableBean extends RequestResponsBean{
	
	/**
	 * �R���X�g���N�^
	 */
	public RequestResponsTableBean() {
		//���׽�̃R���X�g���N�^
		super();
		
	}
	/**
	 * ���[�N���X���擾
	 * @param itemNo�F���[�C���f�b�N�X
	 * @return ���[RequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(int itemNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item �擾
			if (GetItem(itemNo) == null){
				
			}else{
				ret = (RequestResponsRowBean) GetItem(itemNo);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̎擾�Ɏ��s���܂����BitemNo=" + Integer.toString(itemNo));
		}finally{
			
		}
		return ret;
	}
	/**
	 * ���[�N���X���擾
	 * @param itemName�F���[��
	 * @return ���[RequestResponsRowBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean getRowItem(String itemName) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsRowBean ret = null;
		
		try{
			//item �擾
			if (GetItemNo(itemName) == -1){
				
			}else{
				ret = (RequestResponsRowBean) getRowItem(GetItemNo(itemName));
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̎擾�Ɏ��s���܂����BitemNo=" + itemName);
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * ���[��޼ު�Ă��Z�b�g����
	 * @param itemNo�F���[�C���f�b�N�X
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem( int itemNo, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item ���Z�b�g����
			SetItem(itemRow, itemNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̃Z�b�g�Ɏ��s���܂����BitemNo=" + Integer.toString(itemNo));
			
		}finally{
			
		}
		
	}
	/**
	 * ���[��޼ު�Ă��Z�b�g����
	 * @param itemRow�F���[��޼ު��
	 * @param itemName�F���[��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setRowItem(String itemName, RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item ���Z�b�g����
			SetItem(itemRow, GetItemNo(itemName));
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n ���[�̃Z�b�g�Ɏ��s���܂����BitemNo=" + itemName);
			
		}finally{
			
		}
		
	}
	/**
	 * ���[�̒ǉ�
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(RequestResponsRowBean itemRow)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item ��ǉ�����
			AddItem(itemRow);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * ���[�̒ǉ�
	 * @param RowID�F���[ID
	 * @param itemRow�F���[��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsRowBean addRowItem(String RowID)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsRowBean ret = new RequestResponsRowBean();
		
		try{
			//item ��ǉ�����
			AddItem(ret);
			ret.setID(RowID);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * ���[�̒ǉ�
	 * @param itemRow�F���[��޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addRowItem(RequestResponsRowBean itemRow,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item ��ǉ�����
			AddItem(itemRow,ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * ���[��ID���擾����
	 * @param itemNo�F���[�̃C���f�b�N�X
	 * @return�@���[�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getRowID(int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getRowItem(itemNo).getID();
			
		}catch(Exception e){
			this.em.ThrowException(e, "���[�̒l�A�擾�Ɏ��s���܂����BitemNo" + Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}

	//-----------------------------------------------------------------------------

	/**
	 * �t�B�[���h�N���X���擾
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int RowNo, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(RowNo).getFieldItem(FieldNo);
			
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
	 * @param RowID�F���[��
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String RowID, int FieldNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(RowID).getFieldItem(FieldNo);
			
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
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[����
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int RowNo, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(RowNo).getFieldItem(FieldName);
			
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
	 * @param RowID�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[���h�C���f�b�N�X
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String RowID, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			ret = getRowItem(RowID).getFieldItem(FieldName);
			
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
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int RowNo, int FieldNo ,RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item ���Z�b�g����
			getRowItem(RowNo).setFieldItem(FieldNo, itemField);
			
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
	 * @param RowID�F���[ID
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String RowID, int FieldNo, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item ���Z�b�g����
			getRowItem(RowID).setFieldItem(FieldNo, itemField);
			
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
	 * @param RowNo�F���[No
	 * @param FieldName�F�t�B�[���h��
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int RowNo, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item ���Z�b�g����
			getRowItem(RowNo).setFieldItem(FieldName, itemField);
			
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
	 * @param RowID�F���[ID
	 * @param FieldName�F�t�B�[���h��
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String RowID, String FieldName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item ���Z�b�g����
			getRowItem(RowID).setFieldItem(FieldName, itemField);
			
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
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int RowNo, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null;
			//item ��ǉ�����
			RowBean = getRowItem(RowNo);
			if(RowBean == null){
				RowBean = addRowItem("rec");
				
			}
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă̒ǉ�
	 * @param RowID�F���[ID
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(String RowID, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null;
			//item ��ǉ�����
			RowBean = getRowItem(RowID);
			if(RowBean == null){
				RowBean = addRowItem("rec");
				
			}
			RowBean.addFieldItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă̒ǉ�
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(int RowNo, RequestResponsFieldBean itemField,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null;
			//item ��ǉ�����
			RowBean = getRowItem(RowNo);
			if(RowBean == null){
				RowBean = addRowItem("rec");
				
			}
			RowBean.addFieldItem(itemField, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă̒ǉ�
	 * @param RowID�F���[ID
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(String RowID, RequestResponsFieldBean itemField,int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null;
			//item ��ǉ�����
			RowBean = getRowItem(RowID);
			if(RowBean == null){
				RowBean = addRowItem("rec");
				
			}
			RowBean.addFieldItem(itemField, ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldNo�F�t�B�[���h�̃C���f�b�N�X
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int RowNo , int FieldNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(RowNo, FieldNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemNo"
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param RowID�F���[ID
	 * @param FiledNo�F�t�B�[���h�̃C���f�b�N�X
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String RowID , int FiledNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(RowID, FiledNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemNo"
					+ Integer.toString(FiledNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FiledName�F�t�B�[���h��
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int RowNo, String FiledName) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(RowNo, FiledName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemName=" + FiledName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param RowID�F���[ID
	 * @param FieldName�F�t�B�[���h��
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String RowID, String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(RowID, FieldName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemName=" + FieldName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param FieldValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int RowNo, int FieldNo, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			getFieldItem(RowNo, FieldNo).setValue(FieldValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo="
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param RowID�F���[ID
	 * @param FieldNo�F�t�B�[���h�C���f�b�N�X
	 * @param FieldValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String RowID, int FieldNo, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			getFieldItem(RowID, FieldNo).setValue(FieldValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo=" 
					+ Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldName�F�t�B�[���h��
	 * @param FieldValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(int RowNo, String FieldName, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			getFieldItem(RowNo, FieldName).setValue(FieldValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param RowID�F���[ID
	 * @param FieldName�F�t�B�[���h��
	 * @param FieldValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String RowID, String FieldName, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			getFieldItem(RowID, FieldName).setValue(FieldValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l��ǉ�����
	 * @param RowNo�F���[�C���f�b�N�X
	 * @param FieldValue�F�t�B�[���h�̒l
	 * @param FieldName�F�t�B�[���h��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(int RowNo, String FieldName, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null; 
			
			RowBean = getRowItem(RowNo);
			if(RowBean == null){
				RowBean = addRowItem("rec");
			}
			RowBean.addFieldItem(new RequestResponsFieldBean(FieldName,FieldValue));
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�ǉ��Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l��ǉ�����
	 * @param RowID�F���[ID
	 * @param FieldName�F�t�B�[���h��
	 * @param FieldValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(String RowID, String FieldName, String FieldValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			RequestResponsRowBean RowBean = null; 
			
			RowBean = getRowItem(RowID);
			if(RowBean == null){
				RowBean = addRowItem(RowID);
			}
			RowBean.addFieldItem(new RequestResponsFieldBean(FieldName,FieldValue));
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�ǉ��Ɏ��s���܂����BitemNo=" + FieldName);
			
		}finally{
			
		}
		
	}
	
}
