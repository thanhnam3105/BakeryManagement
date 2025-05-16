package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * �`�[���f�[�^���Ǘ��i�o�^�E�X�V�E�폜�j����SQL���쐬����B
 * M106_team�e�[�u������A�f�[�^�̒��o���s���B
 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA090O�v�ɐݒ肷��B
 * 
 * @author itou
 * @since 2009/04/16
 */
public class TeamDataKanriLogic extends LogicBase {

	/**
	 * �`�[���f�[�^�Ǘ��R���X�g���N�^ �F �`�[���f�[�^�̑���Ǘ����s���B
	 */
	public TeamDataKanriLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �`�[���f�[�^����Ǘ� �F �`�[���f�[�^�̑���Ǘ����s���B
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

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			String strShotriKbn = null;

			// �����敪�̎擾
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");

			// SQL���̍쐬
			if (strShotriKbn.equals("1")) {
				// �����敪�F�o�^�@�`�[���f�[�^�o�^SQL�쐬�������ďo���B
				strSql = teamKanriInsertSQL(reqData);
			} else if (strShotriKbn.equals("2")) {
				// �����敪�F�X�V�@�`�[���f�[�^�X�VSQL�쐬�������ďo���B
				strSql = teamKanriUpdateSQL(reqData, strSql);
			} else if (strShotriKbn.equals("3")) {
				// �����敪�F�폜�@�`�[���f�[�^�폜SQL�쐬�������ďo���B
				strSql = teamKanriDeleteSQL(reqData, strSql);
			}

			// �g�����U�N�V�����J�n
			super.createExecDB();
			execDB.BeginTran();

			// SQL�����s
			execDB.execSQL(strSql.toString());

			// �R�~�b�g
			execDB.Commit();

			// �@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// �e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// ���X�|���X�f�[�^�̌`��
			storageLiteralDataKanri(resKind.getTableItem(strTableNm));

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
		}
		return resKind;
	}

	/**
	 * �`�[���f�[�^�o�^SQL�쐬 : �`�[���f�[�^�o�^�p�@SQL���쐬���A�e�f�[�^��DB�ɓo�^����B
	 * 
	 * @param reqData
	 *            �F���N�G�X�g�f�[�^
	 * @param strSql
	 *            �F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer teamKanriInsertSQL(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {

			// �O���[�vCD
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");
			// �`�[��CD
			String strTeamCd = requestData.getFieldVale(0, 0, "cd_team");
			// �`�[����
			String strTeamNm = requestData.getFieldVale(0, 0, "nm_team");

			// ���e�����f�[�^�o�^SQL�쐬
			strSql.append("INSERT INTO ma_team (");
			strSql.append(" cd_group");
			strSql.append(" ,cd_team");
			strSql.append(" ,nm_team");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ) VALUES ( '");
			strSql.append(strGroupCd + "'");
			if (!(strTeamCd.equals("") || strTeamCd == null)) {
				// �擾�����`�[���R�[�h��p���A�`�[���f�[�^�o�^�pSQL���쐬����B
				strSql.append(" ,'" + strTeamCd + "'");
			} else {
				// �V�K���s�̔Ԃ��ꂽ�A�`�[���R�[�h�o�^�pSQL���쐬����B
				/*** �̔ԏ��� *******start **************************************/
				String strNoSeq = "";

				StringBuffer strSelSql = new StringBuffer();
				StringBuffer strInsSql = new StringBuffer();
				List<?> lstRecset = null;
				try {
					// �g�����U�N�V�����J�n
					super.createSearchDB();
					searchDB.BeginTran();
					// ���e�����R�[�h�̍̔�SQL�쐬
					strSelSql.append("SELECT (no_seq + 1) as no_seq");
					strSelSql.append(" FROM ma_saiban WITH(UPDLOCK, ROWLOCK)");
					strSelSql.append(" WHERE key1 = '�`�[���R�[�h'");
					strSelSql.append(" AND key2 = '");
					strSelSql.append(strGroupCd + "'");
					// SQL�����s
					lstRecset = searchDB.dbSearch(strSelSql.toString());
					if (lstRecset.size() > 0) {
						// �̔Ԍ��ʂ�ޔ�
						Object items = (Object) lstRecset.get(0);
						strNoSeq = items.toString();

						// ���R�[�h���X�V
						strInsSql.append("UPDATE ma_saiban SET");
						strInsSql.append("  no_seq = ");
						strInsSql.append(strNoSeq);
						strInsSql.append(" ,id_koshin = ");
						strInsSql.append(userInfoData.getId_user());
						strInsSql.append(" ,dt_koshin = GETDATE()");
						strInsSql.append(" WHERE key1 = '�`�[���R�[�h'");
						strInsSql.append(" AND key2 = '");
						strInsSql.append(strGroupCd + "'");

					} else {
						strNoSeq = "1";

						// ���R�[�h��ǉ�
						strInsSql.append("INSERT INTO ma_saiban (");
						strInsSql.append("  key1");
						strInsSql.append(" ,key2");
						strInsSql.append(" ,no_seq");
						strInsSql.append(" ,id_toroku");
						strInsSql.append(" ,dt_toroku");
						strInsSql.append(" ,id_koshin");
						strInsSql.append(" ,dt_koshin");
						strInsSql.append(" ) VALUES (");
						strInsSql.append("  '�`�[���R�[�h'");
						strInsSql.append(" ,'" + strGroupCd + "'");
						strInsSql.append(" ," + strNoSeq);
						strInsSql.append(" ," + userInfoData.getId_user());
						strInsSql.append(" ,GETDATE()");
						strInsSql.append(" ," + userInfoData.getId_user());
						strInsSql.append(" ,GETDATE()");
						strInsSql.append(")");
					}
					//SQL�����s
					super.createExecDB();
					execDB.setSession(searchDB.getSession());
					execDB.execSQL(strInsSql.toString());
					
					//�R�~�b�g
					searchDB.Commit();

					strTeamCd = strNoSeq;
				} catch (Exception e) {
					if (searchDB != null) {
						// ���[���o�b�N
						searchDB.Rollback();
					}

					this.em.ThrowException(e, "");
				} finally {
					//���X�g�̔j��
					removeList(lstRecset);
					if (searchDB != null) {
						// �Z�b�V�����̃N���[�Y
						searchDB.Close();
						searchDB = null;
					}

					//�ϐ��̍폜
					strSelSql = null;
					strInsSql = null;
				}
				/*** �̔ԏ��� *******end **************************************/
				strSql.append(" ," + strTeamCd );
			}
			strSql.append(" ,'" + strTeamNm + "'");
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");
			strSql.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �`�[���f�[�^�X�VSQL�쐬 : �`�[���f�[�^�X�V�p�@SQL���쐬���A�e�f�[�^��DB�ɍX�V����B
	 * 
	 * @param reqData
	 *            �F���N�G�X�g�f�[�^
	 * @param strSql
	 *            �F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer teamKanriUpdateSQL(
			RequestResponsKindBean requestData, StringBuffer strSql)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// �O���[�vCD
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");
			// �`�[��CD
			String strTeamCd = requestData.getFieldVale(0, 0, "cd_team");
			// �`�[����
			String strTeamNm = requestData.getFieldVale(0, 0, "nm_team");
			
			// SQL���̍쐬
			strSql.append("UPDATE ma_team SET");
			strSql.append(" nm_team = '");
			strSql.append(strTeamNm + "'");
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);
			strSql.append(" AND cd_team = ");
			strSql.append(strTeamCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �`�[���f�[�^�폜SQL�쐬 : �`�[���f�[�^�폜�p�@SQL���쐬���A�e�f�[�^��DB�ɍ폜����B
	 * 
	 * @param reqData
	 *            �F���N�G�X�g�f�[�^
	 * @param strSql
	 *            �F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer teamKanriDeleteSQL(
			RequestResponsKindBean requestData, StringBuffer strSql)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// �O���[�vCD
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");
			// �`�[��CD
			String strTeamCd = requestData.getFieldVale(0, 0, "cd_team");

			// SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_team");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);
			strSql.append(" AND cd_team = ");
			strSql.append(strTeamCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �Ǘ����ʃp�����[�^�[�i�[ : �`�[���f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * 
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageLiteralDataKanri(RequestResponsTableBean resTable) 
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

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