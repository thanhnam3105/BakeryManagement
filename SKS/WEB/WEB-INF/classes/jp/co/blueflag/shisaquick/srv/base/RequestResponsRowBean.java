package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * ���N�G�X�g/���X�|���XRows
 *  : ���N�G�X�g/���X�|���X�̍s���Ǘ�����
 * @author TT.furuta
 * @since  2009/03/24
 */
public class RequestResponsRowBean extends RequestResponsBean {
	
	/**
	 * �R���X�g���N�^
	 */
	public RequestResponsRowBean() {
		//���̃R���X�g���N�^
		super();
		
	}
	/**
	 * �t�B�[���h�N���X���擾
	 * @param itemNo�F�t�B�[���h�C���f�b�N�X
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(int itemNo) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			if(GetItem(itemNo) == null){
				ret=null;				
			}else{
				ret = (RequestResponsFieldBean) GetItem(itemNo);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̎擾�Ɏ��s���܂����BitemNo=" + Integer.toString(itemNo));

		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�N���X���擾
	 * @param itemName�F�t�B�[���h��
	 * @return �t�B�[���hRequestResponsFieldBean
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean getFieldItem(String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsFieldBean ret = null;
		
		try{
			//item �擾
			if (getFieldItem(GetItemNo(itemName)) == null){

			}else{
				ret = (RequestResponsFieldBean) getFieldItem(GetItemNo(itemName));
				
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̎擾�Ɏ��s���܂����BitemNo=" + itemName);
			
		}finally{
			
		}
		return ret;

	}
	/**
	 * �t�B�[���h��޼ު�Ă��Z�b�g����
	 * @param itemNo�F�t�B�[���h�C���f�b�N�X
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(int FieldNo, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item ���Z�b�g����
			SetItem(itemField, FieldNo);
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̃Z�b�g�Ɏ��s���܂����BitemNo=" + Integer.toString(FieldNo));
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h��޼ު�Ă��Z�b�g����
	 * @param itemName�F�t�B�[���h��
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldItem(String itemName, RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//item ���Z�b�g����
			SetItem(itemField, GetItemNo(itemName));
			
		}catch(Exception e){
			this.em.ThrowException(e, "/n �t�B�[���h�̃Z�b�g�Ɏ��s���܂����BitemNo=" + itemName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒ǉ�
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(RequestResponsFieldBean itemField)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item ��ǉ�����
			AddItem(itemField);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒ǉ�
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsFieldBean addFieldItem(String FieldName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsFieldBean ret = new RequestResponsFieldBean(FieldName,"");
		
		try{
			//item ��ǉ�����
			AddItem(ret);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒ǉ�
	 * @param itemField�F�t�B�[���h��޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldItem(RequestResponsFieldBean itemField, int ix)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//item ��ǉ�����
			AddItem(itemField,ix);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param itemNo�F�t�B�[���h�̃C���f�b�N�X
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(int itemNo)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(itemNo).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemNo" + Integer.toString(itemNo));
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���擾����
	 * @param itemName�F�t�B�[���h��
	 * @return�@�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public String getFieldVale(String itemName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try{
			//�l���擾
			ret = getFieldItem(itemName).getValue();
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�擾�Ɏ��s���܂����BitemName=" + itemName);
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param itemValue�F�t�B�[���h�̒l
	 * @param itemNo�F�t�B�[���h�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale( int itemNo, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			
			getFieldItem(itemNo).setValue(itemValue);
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo=" + Integer.toString(itemNo));
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l���Z�b�g����
	 * @param itemName�F�t�B�[���h��
	 * @param itemValue�F�t�B�[���h�̒l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void setFieldVale(String itemName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l���Z�b�g
			if(getFieldItem(itemName) == null){
				addFieldItem(new RequestResponsFieldBean(itemName,itemValue));
			}else{
				getFieldItem(itemName).setValue(itemValue);
			}
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�Z�b�g�Ɏ��s���܂����BitemNo=" + itemName);
			
		}finally{
			
		}
		
	}
	/**
	 * �t�B�[���h�̒l��ǉ�����
	 * @param itemValue�F�t�B�[���h�̒l
	 * @param itemName�F�t�B�[���h��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void addFieldVale(String itemName, String itemValue)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			//�l��ǉ�
			addFieldItem(new RequestResponsFieldBean(itemName,itemValue));
			
		}catch(Exception e){
			this.em.ThrowException(e, "�t�B�[���h�̒l�A�ǉ��Ɏ��s���܂����BitemNo=" + itemName);
			
		}finally{
			
		}
		
	}
	
}
