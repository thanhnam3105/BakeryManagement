package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * �yQP@00342�z
 * �S���҃}�X�^�����e�i�c�Ɓj�@���O�C��ID�擾�i�V���O���T�C���I�����j
 *  : ���݃X�e�[�^�X�����擾����B
 *  �@�\ID�FFGEN2050
 *  
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2120_Logic extends LogicBase{
	
	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@���O�C��ID�擾�i�V���O���T�C���I�����j
	 * : �C���X�^���X����
	 */
	public FGEN2120_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * �S���҃}�X�^�����e�i�c�Ɓj�@���O�C��ID�擾�i�V���O���T�C���I�����j
	 *  : ���O�C��ID�i�V���O���T�C���I�����j�����擾����B
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean ExecLogic(
			
			 RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;
		
		try {
			
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			//���X�|���X�f�[�^�̌`��
			this.genkaKihonSetting(resKind, reqData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
		
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			
		}
		return resKind;
		
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting�i���X�|���X�f�[�^�̌`���j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * ���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void genkaKihonSetting(
			
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData 
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;	
		
		try {
			//�e�[�u����
			String strTblNm = "table";	
			
			//���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			
			//�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihon(resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃}�X�^�����e�i�c�Ɓj�@���O�C��ID�擾�i�V���O���T�C���I�����j���������s���܂����B");
			
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			
			if (searchDB != null) {
				//�Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;
				
			}
		}
	}
	
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData�i�p�����[�^�[�i�[�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenkaKihon( RequestResponsTableBean resTable ) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//���[�UID�擾
			String id_user = toString(userInfoData.getMovement_condition().get(0));
			
			//�����f�[�^ID�擾
			String strUKinoId = "";
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				// MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//					if (userInfoData.getId_gamen().get(i).toString().equals("200")){
				if (userInfoData.getId_gamen().get(i).toString().equals("200") || userInfoData.getId_gamen().get(i).toString().equals("90")){
				// MOD 2013/9/25 okano�yQP@30151�zNo.28 end
					//�@�\ID��ݒ�
					strUKinoId = userInfoData.getId_kino().get(i).toString(); 
				}
			}
			
			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			//���ʂ����X�|���X�f�[�^�Ɋi�[
			resTable.addFieldVale(0, "id_user", id_user);
			
			//���o�^���[�U�敪�ݒ�
			// MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//				if(strUKinoId.equals("101")){
			if(strUKinoId.equals("101") || strUKinoId.equals("22") || strUKinoId.equals("104") || strUKinoId.equals("23") || strUKinoId.equals("105") || strUKinoId.equals("24") ){
			// MOD 2013/9/25 okano�yQP@30151�zNo.28 end
				resTable.addFieldVale(0, "kbn_kari", "1");
			}
			else{
				resTable.addFieldVale(0, "kbn_kari", "0");
			}
			
			

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
