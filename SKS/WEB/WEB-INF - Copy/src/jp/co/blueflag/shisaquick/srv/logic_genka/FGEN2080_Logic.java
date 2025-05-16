package jp.co.blueflag.shisaquick.srv.logic_genka;

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
 * �S���ҁi�c�Ɓj�f�[�^�Ǘ�DB�F�S���҃f�[�^���Ǘ��i�o�^�E�X�V�E�폜�j����SQL���쐬����B
 * M101_user�e�[�u������A�f�[�^�̒��o���s���B
 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FFGEN2080O�v�ɐݒ肷��B
 *
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class FGEN2080_Logic extends LogicBase {

	/**
	 * �S���҃f�[�^�i�c�Ɓj�Ǘ��R���X�g���N�^ �F �C���X�^���X����
	 */
	public FGEN2080_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �S���҃f�[�^�i�c�Ɓj����Ǘ� �F �S���҃f�[�^�i�c�Ɓj�̑���Ǘ����s���B
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
//		StringBuffer strSql3 = new StringBuffer();

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
			// �����敪�F�o�^
			if (strShotriKbn.equals("1")) {

				// �S���҃f�[�^�o�^SQL�쐬�������ďo���B
				strSql = tantoushaKanriInsertSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s

				// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 start
				// �e�[�u��Bean�擾
				RequestResponsTableBean reqTableBean = (RequestResponsTableBean)reqData.GetItem(0);
				// �f�[�^���������L�����o�[��o�^
				for (int i = 0; i < reqTableBean.GetCnt(); i++) {
					// ���L�����o�[�o�^SQL�쐬����
					strSql = tantoushaMemberInsertSQL(reqTableBean, i);
					// ���L�����o�[���Ȃ����͏����s�v
					if (strSql != null) {
						execDB.execSQL(strSql.toString()); // SQL�����s
					}
				}
				// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 end

			}
			// �����敪�F�X�V
			else if (strShotriKbn.equals("2")) {

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
					strSqlPass = null;
				}
				//ADD 2013/9/25 okano�yQP@30151�zNo.28 end

				// �S���҃f�[�^�X�VSQL�쐬�������ďo���B
				strSql = tantoushaKanriUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s

				// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 start
				// ���L�����o�[�폜�i�폜�S���� = id_user�j
				strSql = tantoushaMemberDeleteSQL(reqData, false);
				execDB.execSQL(strSql.toString()); // SQL�����s

				//�e�[�u��Bean�擾
				RequestResponsTableBean reqTableBean = (RequestResponsTableBean)reqData.GetItem(0);
				// �f�[�^���������L�����o�[��o�^
				for (int i = 0; i < reqTableBean.GetCnt(); i++) {
					// ���L�����o�[�o�^SQL�쐬����
					strSql = tantoushaMemberInsertSQL(reqTableBean, i);
					// ���L�����o�[���Ȃ����͏����s�v
					if (strSql != null) {
						execDB.execSQL(strSql.toString()); // SQL�����s
					}
				}
				// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 end

			}
			// �����敪�F�폜
			else if (strShotriKbn.equals("3")) {

				// �S���҃f�[�^�폜SQL�쐬�������ďo���B
				strSql = tantoushaKanriDeleteSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s

				// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 start
				// ���L�����o�[�폜�i�폜�S���� = id_user OR id_member�j
				strSql = tantoushaMemberDeleteSQL(reqData, true);
				execDB.execSQL(strSql.toString()); // SQL�����s
				// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 end

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

			// MOD 2013/9/25 okano�yQP@30151�zNo.28 end
//			em.ThrowException(ExceptionKind.���Exception, "E000329", "", "", "");
			this.em.ThrowException(e, "");
			// MOD 2013/9/25 okano�yQP@30151�zNo.28 end

			//this.em.ThrowException(e, "�S���҃f�[�^�i�c�Ɓj�̓o�^�E�X�V�E�폜���������s���܂����B");
		} finally {
			if (execDB != null) {
				// �Z�b�V�����̃N���[�Y
				execDB.Close();
				execDB = null;
			}

			//�ϐ��̍폜
			strSql = null;
			strSql2 = null;
//			strSql3 = null;
		}
		return resKind;
	}

	/**
	 * �S���҃f�[�^�i�c�Ɓj�o�^SQL�쐬
	 *  : �S���҃f�[�^�i�c�Ɓj�o�^�p�@SQL���쐬���A�e�f�[�^��DB�ɓo�^����B
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
			// ��E
			String strJosiCd = "";

			strUserId = requestData.getFieldVale("table", 0, "id_user");
			strPassword = requestData.getFieldVale("table", 0, "password");
			strKengenCd = requestData.getFieldVale("table", 0, "cd_kengen");
			strNmUser = requestData.getFieldVale("table", 0, "nm_user");
			strKaishaCd = requestData.getFieldVale("table", 0, "cd_kaisha");
			strBushoCd = requestData.getFieldVale("table", 0, "cd_busho");
			strJosiCd = requestData.getFieldVale("table", 0, "id_josi");

			if(strJosiCd.equals("")){
				strJosiCd = "NULL";
			}

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
			strInsSql.append(" ,id_josi");
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 start
			strInsSql.append(" ,dt_password");
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 end
			strInsSql.append(" ) VALUES (");
			strInsSql.append("  " + strUserId);
			strInsSql.append(" ,'" + strPassword + "'");
			strInsSql.append(" ," + strKengenCd);
			strInsSql.append(" ,'" + strNmUser + "'");
			strInsSql.append(" ," + strKaishaCd);
			strInsSql.append(" ," + strBushoCd);
			strInsSql.append(" ,NULL");
			strInsSql.append(" ,NULL");
			strInsSql.append(" ,NULL");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(" ," + strJosiCd);
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 start
			strInsSql.append(" ,GETDATE()");
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 end
			strInsSql.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^�i�c�Ɓj�o�^SQL�쐬���������s���܂����B");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * �S���҃f�[�^�i�c�Ɓj�X�VSQL�쐬
	 *  : �S���҃f�[�^�i�c�Ɓj�X�V�p�@SQL���쐬���A�e�f�[�^��DB�ɍX�V����B
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
		//  2015/03/03 DEL start TT.Kitazawa�yQP@40812�zNo.19
//			// ��E
//			String strJosiCd = "";
		//  2015/03/03 DEL end TT.Kitazawa�yQP@40812�zNo.19

			strUserId = requestData.getFieldVale("table", 0, "id_user");
			strPassword = requestData.getFieldVale("table", 0, "password");
			strKengenCd = requestData.getFieldVale("table", 0, "cd_kengen");
			strNmUser = requestData.getFieldVale("table", 0, "nm_user");
			strKaishaCd = requestData.getFieldVale("table", 0, "cd_kaisha");
			strBushoCd = requestData.getFieldVale("table", 0, "cd_busho");
		//  2015/03/03 DEL start TT.Kitazawa�yQP@40812�zNo.19
//			strJosiCd = requestData.getFieldVale("table", 0, "id_josi");
//
//			if(strJosiCd.equals("")){
//				strJosiCd = "NULL";
//			}
		//  2015/03/03 DEL end TT.Kitazawa�yQP@40812�zNo.19

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
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			//  2015/03/03 DEL start TT.Kitazawa�yQP@40812�zNo.19
//			strSql.append(" ,id_josi = ");
//			strSql.append(strJosiCd);
			//  2015/03/03 DEL end TT.Kitazawa�yQP@40812�zNo.19
			//MOD 2016/7/14 shima start
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 start
			strSql.append(" ,dt_password = GETDATE() ");
//			strSql.append(" ,dt_password = CASE ");
//			strSql.append(" WHEN password = '");
//			strSql.append(strPassword);
//			strSql.append("' THEN dt_password ");
//			strSql.append(" ELSE GETDATE() END");
			//ADD 2013/10/2 okano�yQP@30151�zNo.28 end
			//MOD 2016/7/14 shima start
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^�i�c�Ɓj�X�VSQL�쐬���������s���܂����B");
		} finally {

		}
		return strSql;
	}

	/**
	 * �S���҃f�[�^�폜�i�c�ƁjSQL�쐬
	 *  : �S���҃f�[�^�i�c�Ɓj�폜�p�@SQL���쐬���A�e�f�[�^��DB�ɍ폜����B
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

			strUserId = requestData.getFieldVale("table", 0, "id_user");

			//SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_user");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���҃f�[�^�폜�i�c�ƁjSQL�쐬���������s���܂����B");
		} finally {

		}
		return strSql;
	}


	/**
	 * �����S���҃f�[�^�i�c�Ɓj�폜SQL�쐬
	 *  : �����S���҃f�[�^�i�c�Ɓj�폜�p�@SQL���쐬���A�e�f�[�^��DB�ɍ폜����B
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

			strUserId = requestData.getFieldVale("table", 0, "id_user");

			//SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_tantokaisya");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);

		} catch (Exception e) {
			this.em.ThrowException(e, "�����S���҃f�[�^�i�c�Ɓj�폜SQL�쐬���������s���܂����B");
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

	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 start
	/**
	 * �S���ҁi�c�Ɓj���L�����o�[�폜SQL�쐬
	 *  : �S���ҁi�c�Ɓj���L�����o�[�폜�p�@SQL���쐬����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param blMember�Ftrue ���[�U�폜�Afalse �X�V��
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantoushaMemberDeleteSQL(RequestResponsKindBean requestData, Boolean blMember)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();
		try {
			// ���[�UID
			String strUserId = "";

			strUserId = requestData.getFieldVale("table", 0, "id_user");

			//SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_member");
			strSql.append(" WHERE id_user = ");
			strSql.append(strUserId);
			if (blMember) {
				strSql.append(" OR id_member = ");
				strSql.append(strUserId);
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���ҁi�c�Ɓj���L�����o�[�폜SQL�쐬���������s���܂����B");
		} finally {

		}
		return strSql;
	}

	/**
	 * �S���ҁi�c�Ɓj���L�����o�[�o�^SQL�쐬
	 *  : �S���ҁi�c�Ɓj���L�����o�[�o�^�p�@SQL���쐬����B
	 * @param reqData�F���N�G�X�g�f�[�^ RequestResponsTableBean
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantoushaMemberInsertSQL(RequestResponsTableBean reqTableData, int cnt)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strInsSql = new StringBuffer();
		try {
			// ���[�UID�i�P���ڂ݂̂ɐݒ�j
			String strUserId = reqTableData.getFieldVale( 0, "id_user");
			// ���L�����o�[
			String strMemberId = reqTableData.getFieldVale( cnt, "id_member");
			if (strMemberId.equals("")) {
				// �o�^�s�v
				return null;
			}

			//SQL���̍쐬
			strInsSql.append("INSERT INTO ma_member (");
			strInsSql.append("  id_user");
			strInsSql.append(" ,id_member");
			strInsSql.append(" ,no_sort");
			strInsSql.append(" ,id_toroku");
			strInsSql.append(" ,dt_toroku");
			strInsSql.append(" ,id_koshin");
			strInsSql.append(" ,dt_koshin");
			strInsSql.append(" ) VALUES (");
			strInsSql.append("  " + strUserId );
			strInsSql.append(" ," + strMemberId );
			strInsSql.append(" ," + (cnt+1));
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(" ," + userInfoData.getId_user());
			strInsSql.append(" ,GETDATE()");
			strInsSql.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���ҁi�c�Ɓj���L�����o�[�o�^SQL�쐬���������s���܂����B");
		} finally {

		}
		return strInsSql;
	}
	// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.19 end

}

