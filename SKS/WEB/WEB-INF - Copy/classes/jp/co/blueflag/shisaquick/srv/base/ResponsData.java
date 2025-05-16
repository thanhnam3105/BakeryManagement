package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * ���X�|���X�f�[�^�ێ�
 *  : XMLID���Ƃ̊e���X�|���X�f�[�^�ێ��N���X��
 * @author TT.furuta
 * @since  2009/03/24
 */
public class ResponsData extends RequestData{

	private String strCurrentKindID;		//���쒆�̋@�\ID
	private String strCurrentTableName;	//���쒆�̃e�[�u����
	private int 	iCurrentKindIdx;		//���쒆�̋@�\ID�C���f�b�N�X
	private int 	iCurrentTableIdx;		//���쒆�̃e�[�u�����C���f�b�N�X 
	
	/**
	 * �R���X�g���N�^
	 *  : ���X�|���X�f�[�^�ێ��R���X�g���N�^
	 * @param XmlID Xml��ID
	 */
	public ResponsData(String XmlID) throws Exception {
		super(null);
		//XMLID��ݒ肷��B
		super.setID(XmlID);
		
		iCurrentKindIdx  = 0;
		iCurrentTableIdx = 0;
	}
	
	/**
	 * �s�ǉ�
	 *  : �@�\ID/�e�[�u��/�s��ǉ�����
	 * @param requestResponsRow : �ǉ��s
	 * @return �ǉ��s�̃C���f�b�N�X
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int addRow(RequestResponsRowBean requestResponsRow) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//�ǉ��s�C���f�b�N�X
		int iAddIdx = 0;
		
		try {
			//�����o�ϐ��FstrCurrentKindID/strCurrentTableName�����ݒ�̏ꍇ���삵�Ȃ�
			if (null != strCurrentKindID && null != strCurrentTableName){
			
				//�@�\Bean�擾
				RequestResponsKindBean respKinoBean  =(RequestResponsKindBean) super.getItemList().get(iCurrentKindIdx);
				//�e�[�u��Bean�擾
				RequestResponsTableBean respTableBean =(RequestResponsTableBean) respKinoBean.getItemList().get(iCurrentTableIdx);
								
				//�e�[�u��Bean�ɍs�f�[�^�ݒ�
				respTableBean.getItemList().add(requestResponsRow);
				
				//�C���f�b�N�X�ݒ�
				iAddIdx = respTableBean.GetCnt() -1;
			}
		
		} catch (Exception e){
			
			em.ThrowException(e, "�s�ǉ��Ɏ��s���܂����B");
			
		} finally {
			
		}

		return iAddIdx;
	}
	/**
	 * �@�\�������ʒǉ�
	 *  : �@�\ID���̏������ʂ�ǉ�����
	 * @param benKaind : �@�\��������
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void AddKind(RequestResponsKindBean benKaind) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			
			//�@�F�@�\�������ʂ�ǉ�����
			super.getItemList().add(benKaind);
			
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\�������ʒǉ��Ɏ��s���܂����B");
			
		} finally {
			
		}
	}
	/**
	 * �@�\�������ʒǉ�
	 *  : �@�\ID���̏������ʂ�ǉ�����
	 * @param benKaind : �@�\��������
	 * @param addIndex : �ǉ��ʒuIndex
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void AddKind(RequestResponsBean benKaind,int addIndex)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			
			//�@�F�@�\�������ʂ�ǉ�����
			super.getItemList().add(addIndex, benKaind);
			
		} catch (Exception e) {
			
			em.ThrowException(e, "�@�\�������ʒǉ��Ɏ��s���܂����B");
			
		} finally {
			
		}
	}

	/**
	 * �J�����g�@�\ID
	 *  : ���쒆�̋@�\ID���m��
	 * @param strKindID : �@�\ID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void CurrentKindID(String strKindID) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		boolean blFlg = false;
		
		try {
			
			//�@�\ID�����[�v
			for (int i=0; i < super.GetKindCnt(); i++) {
				
				//�@�\ID����v�����ꍇ
				if (strKindID.equals(super.GetKindID(i))){
					//�@�\ID�ޔ�
					this.strCurrentKindID = strKindID;
					//�@�\�C���f�b�N�X�ޔ�
					this.iCurrentKindIdx = i;
					//����t���O�Ftrue
					blFlg = true;
				}
			}
			
			//�@�\ID�����݂��Ȃ������ꍇ
			if (!blFlg){
				
				RequestResponsKindBean reqRespBean = new RequestResponsKindBean();
				
				//�@�\ID�ݒ�
				reqRespBean.setID(strKindID);
				
				//�@�\ID��V���ɋ@�\ID���X�g�i���N���XArray�j�ɒǉ�
				super.getItemList().add(reqRespBean);
				//�@�\ID�ޔ�
				this.strCurrentKindID = strKindID;
				//�@�\�C���f�b�N�X�ޔ�
//				this.iCurrentKindIdx = 0;
				this.iCurrentKindIdx = super.GetKindCnt()-1;
			}
			
		} catch (Exception e) {
			
			em.ThrowException(e, "�J�����g�@�\ID�̐ݒ�Ɏ��s���܂����B");
			
		} finally {
			
		}
		
	}
	
	/**
	 * �J�����g�e�[�u����
	 *  : ���쒆�̃e�[�u�������m��
	 * @param TableName : �e�[�u����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void CurrentTableName(String strTableName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			
			boolean blFlg		= false;

			//�J�����g�@�\ID�����ݒ�̏ꍇ�������Ȃ�
			if (null != strCurrentKindID){
				
				//�@�\ID�ɕR�t���e�[�u�������[�v
				for (int i=0;i<super.GetTableCnt(iCurrentKindIdx);i++){
					
					//�擾�e�[�u�����̂���v�����ꍇ
					if (strTableName.equals(super.GetTableName(iCurrentKindIdx, i))){
						
						//�e�[�u������ޔ�
						this.strCurrentTableName = strTableName;
						//�e�[�u�����C���f�b�N�X�ޔ�
						this.iCurrentTableIdx = i;
						//����t���O�Ftrue
						blFlg = true;

					}
				}
				
				//�e�[�u���������݂��Ȃ������ꍇ
				if (!blFlg){
					
					RequestResponsTableBean reqRespBean = new RequestResponsTableBean();
					
					//�e�[�u�����ݒ�
					reqRespBean.setID(strTableName);
					//�@�\IDBean
					RequestResponsKindBean reqRespKino = (RequestResponsKindBean) GetItem(iCurrentKindIdx);
						//((RequestResponsBean) super.getItemList().get(iCurrentKindIdx));
					
					//�e�[�u������V���ɋ@�\ID���̃e�[�u�����X�g�i���N���XArray�j�ɒǉ�
//					reqRespKino.getItemList().add(reqRespBean);
					reqRespKino.addTableItem(reqRespBean);
					
					//�e�[�u������ޔ�
					this.strCurrentTableName = strTableName;
					
					//�e�[�u�����C���f�b�N�X�ޔ�
					this.iCurrentTableIdx = 0;
				}

			}
			
		} catch (Exception e) {
			
			em.ThrowException(e, "�J�����g�e�[�u�����̐ݒ�Ɏ��s���܂����B");
			
		} finally {
			
		}
		this.strCurrentTableName = strTableName;
	}
}
