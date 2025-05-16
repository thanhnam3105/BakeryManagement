package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * �@�S���҃f�[�^�Ǘ�DB�F�S���҃f�[�^���Ǘ��i�o�^�E�X�V�E�폜�j����SQL���쐬����B
 * M101_user�@�E�@M107_tantokaisya�e�[�u������A�f�[�^�̒��o���s���B
 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA270O�v�ɐݒ肷��B																
 * 
 * @author itou
 * @since 2009/04/13
 */
public class TantoushaDataKanriLogic extends LogicBase {

	/**
	 * �S���҃f�[�^�Ǘ��R���X�g���N�^ �F �C���X�^���X����
	 */
	public TantoushaDataKanriLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �S���҃f�[�^����Ǘ� �F �S���҃f�[�^�̑���Ǘ����s���B
	 * 
	 * @param reqData
	 *            : ���N�G�X�g�f�[�^
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

		StringBuffer strSql = new StringBuffer();
		StringBuffer strSql2 = new StringBuffer();
		StringBuffer strSql3 = new StringBuffer();

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			String strShotriKbn = null;

			// �g�����U�N�V�����J�n
			super.createExecDB();
			execDB.BeginTran();

			// �����敪�̎擾
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");

			// �@�\���N�G�X�g�f�[�^���A�����敪�𒊏o���A�e�������\�b�h���ďo���B
			if (strShotriKbn.equals("1")) { // �����敪�F�o�^

				// �S���҃f�[�^�o�^SQL�쐬�������ďo���B
				strSql = tantoushaKanriInsertSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s

				if (!reqData.getFieldVale("ma_tantokaisya", 0, "cd_tantokaisha").equals("")) {
					// �����S���҃f�[�^�o�^SQL�쐬�������ďo���B
					String strUserId = reqData.getFieldVale("ma_tantokaisya", 0, "id_user");
					int intRecCount = reqData.getCntRow("ma_tantokaisya");
					for (int i = 0; i < intRecCount; i++) {
						strSql2 = seizouTantoushaKanriInsertSQL(strUserId,reqData.getFieldVale("ma_tantokaisya", i, "cd_tantokaisha"));
						execDB.execSQL(strSql2.toString()); // SQL�����s
					}
				}
			} else if (strShotriKbn.equals("2")) { // �����敪�F�X�V
				
				//ADD 2013/9/25 okano�yQP@30151�zNo.28 start
				String strUKinoId = userInfoData.getId_kino().get(0).toString();
				if(strUKinoId.equals("23")){
					String strPassId = reqData.getFieldVale( 0, 0, "id_user");
					StringBuffer strSqlPass = new StringBuffer();
					strSqlPass.append("SELECT password FROM ma_user ");
					strSqlPass.append("WHERE id_user=");
					strSqlPass.append(strPassId);
					
					createSearchDB();
					List<?> lstRecset = searchDB.dbSearch(strSqlPass.toString());
					
					if(lstRecset.get(0).toString().equals(reqData.getFieldVale( 0, 0, "password"))){
						em.ThrowException(ExceptionKind.���Exception, "E000228", "�p�X���[�h", "", "");
					}
					//���X�g�̔j��
					removeList(lstRecset);
					if (searchDB != null) {
						//�Z�b�V�����̃N���[�Y
						searchDB.Close();
						searchDB = null;
					}
					strUKinoId = null;
					strPassId = null;
					strSqlPass = null;
				}
				//ADD 2013/9/25 okano�yQP@30151�zNo.28 end
				
				// �S���҃f�[�^�X�VSQL�쐬�������ďo���B
				strSql = tantoushaKanriUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s
				
				// �����S���҃f�[�^�폜SQL�쐬�������ďo���B
				strSql2 = seizouTantoushaKanriDeleteSQL(reqData);
				execDB.execSQL(strSql2.toString()); // SQL�����s
				if (!reqData.getFieldVale("ma_tantokaisya", 0, "cd_tantokaisha").equals("")) {
					// �����S���҃f�[�^�o�^SQL�쐬�������ďo���B
					String strUserId = reqData.getFieldVale("ma_tantokaisya", 0, "id_user");
					int intRecCount = reqData.getCntRow("ma_tantokaisya");
					for (int i = 0; i < intRecCount; i++) {	
						strSql3 = seizouTantoushaKanriInsertSQL(strUserId,reqData.getFieldVale("ma_tantokaisya", i, "cd_tantokaisha"));
						execDB.execSQL(strSql3.toString()); // SQL�����s
					}
				}
				
			} else if (strShotriKbn.equals("3")) { // �����敪�F�폜
				
				// �S���҃f�[�^�폜SQL�쐬�������ďo���B
				strSql = tantoushaKanriDeleteSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s
				
				// �����S���҃f�[�^�폜SQL�쐬�������ďo���B
				strSql2 = seizouTantoushaKanriDeleteSQL(reqData);
				execDB.execSQL(strSql2.toString()); // SQL�����s
				
			}

			// �R�~�b�g
			execDB.Commit();

			// �@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// �e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// ���X�|���X�f�[�^�̌`��
			storageGroupDataKanri(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			if (execDB != null) {
				// ���[���o�b�N
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");
		} finally {
			if (execDB != null) {
				// �Z�b�V�����̃N���[�Y
				execDB.Close();
				execDB = null;
			}
			
			//�ϐ��̍폜
			strSql = null;
			strSql2 = null;
			strSql3 = null;
		}
		return resKind;
	}

	/**
	 * �S���҃f�[�^�o�^SQL�쐬
	 *  : �S���҃f�[�^�o�^�p�@SQL���쐬���A�e�f�[�^��DB�ɓo�^����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer tantoushaKanriInsertSQL(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {
			
			// ���[�UID
			String strUserId = "";
			// �p�X���[�h
			String strPassword = "";
			// ����
			String strKengenCd = "";
			// ����
			String strNmUser = "";
			// �������
			String strKaishaCd = "";
			// ��������
			String strBushoCd = "";
			// �����O���[�v
			String strGroupCd = "";
			// �����`�[��
			String strTeamCd = "";
			// ��E
			String strYakushokuCd = "";
			
			strUserId = requestData.getFieldVale("ma_user", 0, "id_user");
			strPassword = requestData.getFieldVale("ma_user", 0, "password");
			strKengenCd = requestData.getFieldVale("ma_user", 0, "cd_kengen");
			strNmUser = requestData.getFieldVale("ma_user", 0, "nm_user");
			strKaishaCd = requestData.getFieldVale("ma_user", 0, "cd_kaisha");
			strBushoCd = requestData.getFieldVale("ma_user", 0, "cd_busho");
			strGroupCd = requestData.getFieldVale("ma_user", 0, "cd_group");
			strTeamCd = requestData.getFieldVale("ma_user", 0, "cd_team");
			strYakushokuCd = requestData.getFieldVale("ma_user", 0, "cd_yakushoku");
			
			// SQL���̍쐬
			//���R�[�h��ǉ�
			strInsSql.append("INSERT INTO ma_user (");
			strInsSql.append("  id_user");
			strInsSql.append(" ,password");
			strInsSql.append(" ,cd_kengen");
			strInsSql.append(" ,nm_user");
			strInsSql.append(" ,cd_kaisha");
			strInsSql.append(" ,cd_busho");
			strInsSql.append(" ,cd_group");
			strInsSql.append(" ,cd_team");
			strInsSql.append(" ,cd_yakushoku");
			strInsSql.append(" ,id_toroku");
			strInsSql.append(" ,dt_toroku");
			strInsSql.append(" ,id_koshin");
			strInsSql.append(" ,dt_koshin");
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 start
			strInsSql.append(" ,dt_password");
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 end
			strInsSql.append(" ) VALUES (");
			strInsSql.append(strUserId);
			strInsSql.append(" ,'" + strPassword + "'");
			strInsSql.append(" ," + strKengenCd);
			strInsSql.append(" ,'" + strNmUser + "'");
			strInsSql.append(" ," + strKaishaCd);
			strInsSql.append(" ," + strBushoCd);
			strInsSql.append(" ," + strGroupCd);
			strInsSql.append(" ," + strTeamCd);
			strInsSql.append(" ,'" + strYakushokuCd + "'");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 start
			strInsSql.append(" ,GETDATE()");
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 end
			strInsSql.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * �S���҃f�[�^�X�VSQL�쐬
	 *  : �S���҃f�[�^�X�V�p�@SQL���쐬���A�e�f�[�^��DB�ɍX�V����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer tantoushaKanriUpdateSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {
			// ���[�UID
			String strUserId = "";
			// �p�X���[�h
			String strPassword = "";
			// ����
			String strKengenCd = "";
			// ����
			String strNmUser = "";
			// �������
			String strKaishaCd = "";
			// ��������
			String strBushoCd = "";
			// �����O���[�v
			String strGroupCd = "";
			// �����`�[��
			String strTeamCd = "";
			// ��E
			String strYakushokuCd = "";

			strUserId = requestData.getFieldVale("ma_user", 0, "id_user");
			strPassword = requestData.getFieldVale("ma_user", 0, "password");
			strKengenCd = requestData.getFieldVale("ma_user", 0, "cd_kengen");
			strNmUser = requestData.getFieldVale("ma_user", 0, "nm_user");
			strKaishaCd = requestData.getFieldVale("ma_user", 0, "cd_kaisha");
			strBushoCd = requestData.getFieldVale("ma_user", 0, "cd_busho");
			strGroupCd = requestData.getFieldVale("ma_user", 0, "cd_group");
			strTeamCd = requestData.getFieldVale("ma_user", 0, "cd_team");
			strYakushokuCd = requestData.getFieldVale("ma_user", 0, "cd_yakushoku");

			//SQL���̍쐬
			strSql.append("UPDATE ma_user SET");
			strSql.append(" password = '");
			strSql.append(strPassword + "'");
			strSql.append(" ,cd_kengen = ");
			strSql.append(strKengenCd);
			strSql.append(" ,nm_user = '");
			strSql.append(strNmUser + "'");
			strSql.append(" ,cd_kaisha = ");
			strSql.append(strKaishaCd);
			strSql.append(" ,cd_busho = ");
			strSql.append(strBushoCd);
			strSql.append(" ,cd_group = ");
			strSql.append(strGroupCd);
			strSql.append(" ,cd_team = ");
			strSql.append(strTeamCd);
			strSql.append(" ,cd_yakushoku = '");
			strSql.append(strYakushokuCd + "'");
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			//MOD 2016/7/14 shima start
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 start
			strSql.append(" ,dt_password = GETDATE() ");
//			strSql.append(" ,dt_password = CASE ");
//			strSql.append(" WHEN password = '");
//			strSql.append(strPassword);
//			strSql.append("' THEN dt_password ");
//			strSql.append(" ELSE GETDATE() END");
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 end
			//MOD 2016/7/14 shima end
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �S���҃f�[�^�폜SQL�쐬
	 *  : �S���҃f�[�^�폜�p�@SQL���쐬���A�e�f�[�^��DB�ɍ폜����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer tantoushaKanriDeleteSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {
			// ���[�UID
			String strUserId = "";
			
			strUserId = requestData.getFieldVale("ma_user", 0, "id_user");
			
			//SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	/**
	 * �����S���҃f�[�^�o�^SQL�쐬
	 *  : �����S���҃f�[�^�o�^�p�@SQL���쐬���A�e�f�[�^��DB�ɓo�^����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer seizouTantoushaKanriInsertSQL(String strUserId,String strTantokaishaCd) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strInsSql = new StringBuffer();
		try {
			
			// SQL���̍쐬
			//���R�[�h��ǉ�
			strInsSql.append("INSERT INTO ma_tantokaisya (");
			strInsSql.append("  id_user");
			strInsSql.append(" ,cd_tantokaisha");
			strInsSql.append(" ,id_toroku");
			strInsSql.append(" ,dt_toroku");
			strInsSql.append(" ,id_koshin");
			strInsSql.append(" ,dt_koshin");
			strInsSql.append(" ) VALUES (");
			strInsSql.append(strUserId);
			strInsSql.append(" ," + strTantokaishaCd);
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * �����S���҃f�[�^�폜SQL�쐬
	 *  : �����S���҃f�[�^�폜�p�@SQL���쐬���A�e�f�[�^��DB�ɍ폜����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer seizouTantoushaKanriDeleteSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {
			// ���[�UID
			String strUserId = "";
			
			strUserId = requestData.getFieldVale("ma_tantokaisya", 0, "id_user");

			//SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_tantokaisya");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : �S���҃f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGroupDataKanri(RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}

	}
	
}
