package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * ���[�U�[�F��SQL�쐬
 *  : ���[�U�[�F�؏�����������B
 * @author furuta
 * @since  2009/03/29
 */
public class UserNinshoLogic extends LogicBase{
	
	/**
	 * ���[�U�[�F�؃��W�b�N�p�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public UserNinshoLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���[�U�[�F��SQL�쐬 
	 * : ���[�U�[�F�؂��s���ׂ�SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
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

		RequestResponsTableBean reqTableBean = null;
		RequestResponsRowBean reqRecBean = null;
		StringBuffer strSQL = new StringBuffer();
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�e�[�u��Bean�擾
			reqTableBean = reqData.getTableItem(0);
			//�sBean�擾
			reqRecBean = reqTableBean.getRowItem(0);
			
			String strId_User   = "";
			String strKbn_Login = "";
			String strPassword  = "";
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
			boolean flg_Eigyo = false;
			boolean flg_Sign = false;
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
			
			//�s�f�[�^��胊�N�G�X�g���擾
			//���[�U�[ID�ݒ�
			strId_User = reqRecBean.GetItemValue("id_user");
			//���O�C���敪�ݒ�
			strKbn_Login = reqRecBean.GetItemValue("kbn_login");
			//�p�X���[�h�ݒ�
			strPassword = reqRecBean.GetItemValue("password");
					
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
			
			strSQL = new StringBuffer();
			strSQL.append("SELECT COUNT(*) FROM ma_user ");
			strSQL.append(" LEFT JOIN ma_busho ");
			strSQL.append(" ON ma_busho.cd_busho = ma_user.cd_busho ");
			strSQL.append(" AND ma_busho.cd_kaisha = ma_user.cd_kaisha ");
			strSQL.append(" WHERE id_user = ");
			strSQL.append(strId_User);
			strSQL.append(" AND flg_eigyo = 1 ");
			
			createSearchDB();
			lstRecset = searchDB.dbSearch(strSQL.toString());

			if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
				strSQL = new StringBuffer();
				strSQL.append("SELECT COUNT(*) FROM ma_user_new ");
				strSQL.append(" LEFT JOIN ma_busho ");
				strSQL.append(" ON ma_busho.cd_busho = ma_user_new.cd_busho ");
				strSQL.append(" AND ma_busho.cd_kaisha = ma_user_new.cd_kaisha ");
				strSQL.append(" WHERE id_user = ");
				strSQL.append(strId_User);
				strSQL.append(" AND flg_eigyo = 1 ");
				
				createSearchDB();
				lstRecset = searchDB.dbSearch(strSQL.toString());
				
				if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
					flg_Eigyo = false;
				} else {
					flg_Eigyo = true;
				}
			} else {
				flg_Eigyo = true;
			}
			strSQL = new StringBuffer();
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
			
			// MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//				//�V���O���T�C���I���pSQL�쐬
//				if (strKbn_Login.equals("1")){
//					
//					strSQL.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
//					strSQL.append(strId_User);
//					
//				//���O�C���pSQL�쐬
//				} else if (strKbn_Login.equals("2")){
//					
//					strSQL.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
//					strSQL.append(strId_User);
//					strSQL.append(" AND password = '");
//					strSQL.append(strPassword + "' COLLATE Japanese_CS_AS");
//				}
//				
//				createSearchDB();
//				lstRecset = searchDB.dbSearch(strSQL.toString());
			//�V���O���T�C���I���pSQL�쐬
			if (strKbn_Login.equals("1")){
				
				strSQL.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
				strSQL.append(strId_User);
				
				createSearchDB();
				lstRecset = searchDB.dbSearch(strSQL.toString());
				
				if (Integer.parseInt(lstRecset.get(0).toString()) == 0){
					
					strSQL = new StringBuffer();
					strSQL.append("SELECT COUNT(*) FROM ma_user_new WHERE id_user = ");
					strSQL.append(strId_User);
					
					createSearchDB();
					lstRecset = searchDB.dbSearch(strSQL.toString());
					
					flg_Sign = true;
					
				}
				
			//���O�C���pSQL�쐬
			} else if (strKbn_Login.equals("2")){
				
				strSQL.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
				strSQL.append(strId_User);
				strSQL.append(" AND password = '");
				strSQL.append(strPassword + "' COLLATE Japanese_CS_AS");
				
				createSearchDB();
				lstRecset = searchDB.dbSearch(strSQL.toString());
			}
			// MOD 2013/9/25 okano�yQP@30151�zNo.28 end
							
			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());
			//�e�[�u�����̐ݒ�
			resKind.addTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID());

			//�F�،��ʊi�[�p�����[�^�[�ďo
			// MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//				storageUserNinsho(lstRecset, resKind.getTableItem(0));
			storageUserNinsho(lstRecset, resKind.getTableItem(0), flg_Eigyo, flg_Sign);
			// MOD 2013/9/25 okano�yQP@30151�zNo.28 end
			
		} catch (Exception e) {
			
			em.ThrowException(e,"���[�U�[�F�؂Ɏ��s���܂����B");
			
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSQL = null;
			//�N���X�̔j��
			reqTableBean = null;
			reqRecBean = null;
		}
		
		return resKind;
	}

	/**
	 * �F�،��ʃp�����[�^�[�i�[ 
	 * : ���[�U�[�F�؂̔F�،��ʏ����@�\���X�|���X�f�[�^�֊i�[����B
	 * @param lstNinshoKekka�F�F�،���
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	// MOD 2013/9/25 okano�yQP@30151�zNo.28 start
//		private void storageUserNinsho(List<?> lstNinshoKekka, RequestResponsTableBean resTable) 
	private void storageUserNinsho(List<?> lstNinshoKekka, RequestResponsTableBean resTable, boolean flg_Eigyo, boolean flg_Sign) 
	// MOD 2013/9/25 okano�yQP@30151�zNo.28 end
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
			if(flg_Eigyo){
				resTable.addFieldVale(0, "flg_eigyo", "true");
			} else {
				resTable.addFieldVale(0, "flg_eigyo", "false");
			}
			if(flg_Sign){
				resTable.addFieldVale(0, "flg_sign", "true");
			} else {
				resTable.addFieldVale(0, "flg_sign", "false");
			}
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 end

			//���[�U�[��񂪑��݂��Ȃ��ꍇ
			if (Integer.parseInt(lstNinshoKekka.get(0).toString()) == 0){

				//�x����O��Throw����B
				em.ThrowException(ExceptionKind.���Exception, "W000401", "", "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e,"���[�U�[�F�؂Ɏ��s���܂����B");
			
		} finally {

		}

	}

}
