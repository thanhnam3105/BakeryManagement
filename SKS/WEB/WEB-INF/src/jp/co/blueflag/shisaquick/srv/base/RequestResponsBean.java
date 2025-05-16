package jp.co.blueflag.shisaquick.srv.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * ���N�G�X�g/���X�|���XBean
 *  : ���N�G�X�g/���X�|���X�̃f�[�^�ێ��ŁA�ŏ��P�ʂ̃f�[�^�Ǘ����s��
 * @author TT.furuta
 * @since  2009/03/24
 */
public class RequestResponsBean extends ObjectBase {

	//ID
	protected String strID;				
	//�p�r�ɉ����ARequestResponsBean���́ARequestResponsRow���i�[
	protected ArrayList<Object> arrItemList;
	
	/**
	 * �R���X�g���N�^
	 */
	public RequestResponsBean() {
		super();
		
		//�C���X�^���X����
		arrItemList = new ArrayList<Object>();
	}
	
	/**
	 * ���X�g�̃A�C�e�������擾
	 * @return ItemList�̊i�[��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetCnt() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		int ret = 0;
		
		try {
			ret = arrItemList.size();
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		
		//�����oarrItemList�̃A�C�e������Ԃ�
		return ret;
	}
	
	/**
	 * ���X�g�̃A�C�e�������擾
	 * @param itemNo : �A�C�e��No
	 * @return �w�肳�ꂽItemList�̃N���X��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetItemClassName(int itemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String ret = "";
		try {
			ret = arrItemList.get(itemNo).getClass().getName();
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		//�A�C�e�����X�g�̃N���X����Ԃ�
		return ret;
	}
	
	/**
	 * ���ږ��擾
	 * @param itemNo : �A�C�e��No
	 * @return ���ږ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetItemName(int itemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strReturnNm = null;
		
		try {
			//Item�ԍ����擾�����Ώۂ�RequestResponsRow�̏ꍇ
			if (GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRow")){
				//RequestResponsRow�Ɋi�[����Ă��鍀�ږ����擾
				strReturnNm = ((RequestResponsRow)arrItemList.get(itemNo)).getName();
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
		
		return strReturnNm;
	}
	
	/**
	 * ���ڐݒ�l�擾
	 * @param itemNo : �A�C�e��No
	 * @return ���ڐݒ�l
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetItemValue(int itemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strReturnVal = null;

		try {
			
			if (GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsBean")){
				//RequestResponsBean
				//�A�C�e�����X�g���ID�擾
				strReturnVal = ((RequestResponsBean)arrItemList.get(itemNo)).getID();
				
			}else if(GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRow")){
				//RequestResponsRow
				//�A�C�e�����X�g��荀�ڒl���擾
				strReturnVal = ((RequestResponsRow)arrItemList.get(itemNo)).getValue().toString();

			}else if(GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean")){
				//RequestResponsKindBean
				//�A�C�e�����X�g���ID�擾
				strReturnVal = ((RequestResponsKindBean)arrItemList.get(itemNo)).getID();

			}else if(GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean")){
				//RequestResponsTablBean
				//�A�C�e�����X�g���ID�擾
				strReturnVal = ((RequestResponsTableBean)arrItemList.get(itemNo)).getID();

			}else if(GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean")){
				//RequestResponsRowBean
				//�A�C�e�����X�g���ID�擾
				strReturnVal = ((RequestResponsRowBean)arrItemList.get(itemNo)).getID();

			}else if(GetItemClassName(itemNo).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsFieldBean")){
				//RequestResponsFieldBean
				//�A�C�e�����X�g��荀�ڒl���擾
				strReturnVal = ((RequestResponsFieldBean)arrItemList.get(itemNo)).getValue().toString();

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "���ڂ̒l�擾�Ɏ��s���܂����BitemNo=" + Integer.toString(itemNo));

		} finally {
			
		}
		return strReturnVal;

	}

	/**
	 * ���ڐݒ�l�擾
	 * @param itemName : �A�C�e����
	 * @return ���ڐݒ�l
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public String GetItemValue(String itemName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		return GetItemValue(GetItemNo(itemName));
		
	}
	
	/**
	 * �A�C�e���擾
	 * @param itemNo : �A�C�e��No
	 * @return �I�u�W�F�N�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public Object GetItem(int itemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		Object ret = null;
		
		try {
			ret = arrItemList.get(itemNo);
			
		} catch(IndexOutOfBoundsException ex){
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			
		}
		//���X�g�̃A�C�e���ԍ����w�肵�ā@�I�u�W�F�N�g��Ԃ�
		return ret;
		
	}
	/**
	 * ���X�g�A�C�e�����Z�b�g����
	 * @param obj�F���X�g�A�C�e��
	 * @param itemNo�F���X�g�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void SetItem(Object obj, int itemNo) throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			arrItemList.set(itemNo, obj);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			
		}

	}
	/**
	 * �A�C�e�����X�g�ɵ�޼ު�Ă�ǉ�
	 * @param obj�F��޼ު��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void AddItem(Object obj) throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			arrItemList.add(obj);

		} catch (Exception e) {
			this.em.ThrowException(e, "��޼ު�Ă̒ǉ��Ɏ��s���܂����B");

		} finally {
			
		}
		
	}
	/**
	 * �A�C�e�����X�g�ɵ�޼ު�Ă�ǉ�
	 * @param obj�F��޼ު��
	 * @param ix�F�}���ʒu�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void AddItem(Object obj,int ix) throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			arrItemList.add(ix, obj);

		} catch (Exception e) {
			this.em.ThrowException(e, "��޼ު�Ă̒ǉ��Ɏ��s���܂����B");

		} finally {
			
		}
		
	}
	
	/**
	 * �A�C�e��No
	 * @param itemName : ID/���ږ�
	 * @return �A�C�e���ԍ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int GetItemNo(String itemName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int iReturnNo = -1;
		String strId = "";

		try {
			//Item���X�g�����[�v
			for (int i =0;i < arrItemList.size();i++){
				
				if (GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsBean")){
					//RequestResponsBean
					//�A�C�e�����X�g���ID�擾
					strId = ((RequestResponsBean)arrItemList.get(i)).getID();
					
				}else if(GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRow")){
					//RequestResponsRow
					//�A�C�e�����X�g��荀�ږ����擾
					strId = ((RequestResponsRow)arrItemList.get(i)).getName();

				}else if(GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean")){
					//RequestResponsKindBean
					//�A�C�e�����X�g���ID�擾
					strId = ((RequestResponsKindBean)arrItemList.get(i)).getID();

				}else if(GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean")){
					//RequestResponsTablBean
					//�A�C�e�����X�g���ID�擾
					strId = ((RequestResponsTableBean)arrItemList.get(i)).getID();

				}else if(GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean")){
					//RequestResponsRowBean
					//�A�C�e�����X�g���ID�擾
					strId = ((RequestResponsRowBean)arrItemList.get(i)).getID();

				}else if(GetItemClassName(i).equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsFieldBean")){
					//RequestResponsFieldBean
					//�A�C�e�����X�g��荀�ږ����擾
					strId = ((RequestResponsFieldBean)arrItemList.get(i)).getName();

				}
				//�A�C�e���ԍ���ID����v�����ꍇ�A�ΏۃA�C�e���ԍ��ݒ�
				if (itemName.equals(strId)){
					iReturnNo = i;
					break;
				}

			}
		} catch (Exception e) {

			this.em.ThrowException(e, "�A�C�e���ԍ��̎擾�Ɏ��s���܂����BitemName=" + itemName);

		} finally {
			
		}
		return iReturnNo;

	}


	/**
	 * ID �Q�b�^�[
	 * @return ID : ID�̒l��Ԃ�
	 */
	public String getID() {
		
		return strID;
	}
	/**
	 * ID �Z�b�^�[
	 * @param _ID : ID�̒l���i�[����
	 */
	public void setID(String _strID) {
		this.strID = _strID;
		
	}

	/**
	 * �A�C�e�����X�g �Q�b�^�[
	 * @return itemList : �A�C�e�����X�g�̒l��Ԃ�
	 */
	public ArrayList<Object> getItemList() {
		return arrItemList;
	}
	/**
	 * �A�C�e�����X�g �Z�b�^�[
	 * @param _itemList : �A�C�e�����X�g�̒l���i�[����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void setItemList(ArrayList<Object> _itemList) {
		arrItemList = _itemList;

	}
	/**
	 * �f�[�^���X�g���J������iNull�Z�b�g�j
	 */
	public void RemoveList(){
		
		RemoveList(arrItemList);
		
	}

	/**
	 * RequestResponsBean�̃A�C�e�����X�g���N���A����
	 * @param ItemList
	 */
	private void RemoveList(ArrayList<Object> ItemList){
		
		if (ItemList == null){
			return;
		}
		
		for(int ix = ItemList.size()-1; ix < -1; ix-- ){

			if(ItemList.get(ix).getClass().getName().equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsRow")){
				//RequestResponsRow
			}else if(ItemList.get(ix).getClass().getName().equals("jp.co.blueflag.shisaquick.srv.base.RequestResponsFieldBean")){
				//RequestResponsFieldBean
			}else{
				RemoveList(((RequestResponsBean)ItemList.get(ix)).getItemList());
			}
			ItemList.remove(ix);
			
		}
		//���X�g�N���A
		ItemList = null;
		
	}
}
