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
 * �O���[�v�}�X�^�����e�F�O���[�v���o�^�A�X�V�A�폜DB����
 *  : �O���[�v�}�X�^�����e�F�O���[�v����o�^�A�X�V�A�폜����B
 * @author jinbo
 * @since  2009/04/09
 */
public class GroupDataKanriLogic extends LogicBase{
	
	/**
	 * �O���[�v�}�X�^�����e�F�O���[�v���o�^�A�X�V�A�폜DB�����p�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public GroupDataKanriLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �O���[�v�}�X�^�����e�F�O���[�v���X�VSQL�쐬
	 *  : �O���[�v�����X�V����SQL���쐬�B
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

		StringBuffer strSql = new StringBuffer();
		StringBuffer strSql2 = new StringBuffer();

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			String strShotriKbn = null;

			//�����敪�̎擾
			strShotriKbn = reqData.getFieldVale(0, 0, "kbn_shori");

			//SQL���̍쐬
			if (strShotriKbn.equals("1")) {
				// �O���[�v�R�[�h�̎擾
				String strGroupCd = reqData.getFieldVale(0, 0, "cd_group");
				// �O���[�v�R�[�h�̒l���Ȃ��ꍇ�A�̔ԏ���������B
				if (strGroupCd.equals("") || strGroupCd == null) {
					strGroupCd = saibanGroupCd();
				}
				// �g�����U�N�V�����J�n
				super.createExecDB();
				execDB.BeginTran();
				//�o�^
				strSql = groupKanriInsertSQL(reqData, strGroupCd);
			} else if (strShotriKbn.equals("2")) {
				// �g�����U�N�V�����J�n
				super.createExecDB();
				execDB.BeginTran();
				//�X�V
				strSql = groupKanriUpdateSQL(reqData);
			} else if (strShotriKbn.equals("3")) {
				// �g�����U�N�V�����J�n
				super.createExecDB();
				execDB.BeginTran();
				// �O���[�v�폜
				strSql = groupKanriDeleteSQL(reqData);
				// �`�[���폜�Ǝ��s
				strSql2 = teamDataKanriDeleteSQL(reqData);
				execDB.execSQL(strSql2.toString());
			}
			
			//SQL�����s
			execDB.execSQL(strSql.toString());
			
			//�R�~�b�g
			execDB.Commit();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageGroupDataKanri(resKind.getTableItem(0));
			
		} catch (Exception e) {
			if (execDB != null) {
				//���[���o�b�N
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");
		} finally {
			if (execDB != null) {
				//�Z�b�V�����̃N���[�Y
				execDB.Close();
				execDB = null;
			}
			
			//�ϐ��̍폜
			strSql = null;
			strSql2 = null;

		}
		return resKind;
		
	}
	
	/**
	 * �O���[�v�R�[�h�̔ԏ���
	 * @return String
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String saibanGroupCd() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		/*** �̔ԏ��� *******start **************************************/
		String strNoSeq = "";

		StringBuffer strSelSql = new StringBuffer();
		StringBuffer strInsSql = new StringBuffer();
		List<?> lstRecset = null;

		try {
			// �g�����U�N�V�����J�n
			super.createSearchDB();
			searchDB.BeginTran();
			// �O���[�v�R�[�h�̍̔�SQL�쐬
			strSelSql.append("SELECT (no_seq + 1) as no_seq");
			strSelSql.append(" FROM ma_saiban WITH(UPDLOCK, ROWLOCK)");
			strSelSql.append(" WHERE key1 = '�O���[�v�R�[�h'");
			strSelSql.append(" AND key2 = ''");
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
				strInsSql.append(" WHERE key1 = '�O���[�v�R�[�h'");
				strInsSql.append(" AND KEY2 = ''");

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
				strInsSql.append("  '�O���[�v�R�[�h'");
				strInsSql.append(" ,''");
				strInsSql.append(" ," + strNoSeq);
				strInsSql.append(" ," + userInfoData.getId_user());
				strInsSql.append(" ,GETDATE()");
				strInsSql.append(" ," + userInfoData.getId_user());
				strInsSql.append(" ,GETDATE()");
				strInsSql.append(")");
			}
			// SQL�����s
			super.createExecDB();
			execDB.setSession(searchDB.getSession());
			execDB.execSQL(strInsSql.toString());

			// �R�~�b�g
			searchDB.Commit();

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
		return strNoSeq;
	}
	
	/**
	 * �O���[�v�o�^SQL�쐬
	 *  : �O���[�v��o�^����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer groupKanriInsertSQL(RequestResponsKindBean requestData, String strGroupCd)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {
			// �O���[�v���̎擾
			String strGroupName = requestData.getFieldVale(0, 0, "nm_group");

			// SQL���̍쐬
			strSql.append("INSERT INTO ma_group (");
			strSql.append(" cd_group");
			strSql.append(" ,nm_group");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ) VALUES (");
			strSql.append(" " + strGroupCd);
			strSql.append(" ,'" + strGroupName + "'");
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
	 * �O���[�v�X�VSQL�쐬
	 *  : �O���[�v���X�V����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer groupKanriUpdateSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {
			String strGroupCd = null;
			String strGroupName = null;

			//�O���[�v�R�[�h�̎擾
			strGroupCd = requestData.getFieldVale(0, 0, "cd_group");
			//�O���[�v���̎擾
			strGroupName = requestData.getFieldVale(0, 0, "nm_group");

			//SQL���̍쐬
			strSql.append("UPDATE ma_group SET");
			strSql.append(" nm_group = '");
			strSql.append(strGroupName + "'");
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �O���[�v�폜SQL�쐬
	 *  : �O���[�v���폜����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer groupKanriDeleteSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {
			String strGroupCd = null;

			//�O���[�v�R�[�h�̎擾
			strGroupCd = requestData.getFieldVale(0, 0, "cd_group");

			//SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_group");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * �`�[���폜SQL�쐬
	 *  : �`�[�����폜����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer teamDataKanriDeleteSQL(RequestResponsKindBean requestData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();
		try {

			// �O���[�vCD
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");

			// SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_team");
			strSql.append(" WHERE cd_group = ");
			strSql.append(strGroupCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	/**
	 * �O���[�v�}�X�^�����e�F�O���[�v�p�����[�^�[�i�[
	 *  : �O���[�v�X�V���ʏ������X�|���X�f�[�^�֊i�[����B
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
