package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �����f�[�^�Ǘ�DB���� : �����f�[�^���Ǘ��i�o�^�E�X�V�E�폜�j����SQL���쐬����B
 * M102_Kengen�@�E�@M103_kinou�e�[�u���f�[�^�̊Ǘ����s���B �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA340O�v�ɐݒ肷��B
 * 
 * @author itou
 * @since 2009/04/16
 */
public class KengenDataKanriLogic extends LogicBase {

	/**
	 * �����f�[�^�Ǘ��R���X�g���N�^
	 */
	public KengenDataKanriLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �����f�[�^����Ǘ� �F �����f�[�^�̑���Ǘ����s���B
	 * 
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
		StringBuffer strSql3 = new StringBuffer();

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			String strShotriKbn = null;

			// �����敪�̎擾
			strShotriKbn = reqData.getFieldVale("ma_kengen", 0, "kbn_shori");

			
			// �@�\���N�G�X�g�f�[�^���A�����敪�𒊏o���A�e�������\�b�h���ďo���B
			if (strShotriKbn.equals("1")) { // �����敪�F�o�^
				
				// ����CD
				String strKengenCd = reqData.getFieldVale("ma_kengen", 0, "cd_kengen");
				// ����CD�̒l���Ȃ��ꍇ�A�̔ԏ���������B
				if (strKengenCd.equals("") || strKengenCd == null) {
					strKengenCd = saibanKengenCd();
				}
				// �g�����U�N�V�����J�n
				super.createExecDB();
				execDB.BeginTran();
				// �����f�[�^�o�^SQL�쐬�������ďo���B
				strSql = kengenKanriInsertSQL(reqData, strKengenCd);
				execDB.execSQL(strSql.toString()); // SQL�����s

				// �@�\�}�X�^�o�^�pSQL�쐬�������ďo���B
				int intRecCount = reqData.getCntRow("ma_kinou");
				for (int i = 0; i < intRecCount; i++) {
					strSql2 = tantouKanriInsertSQL(reqData, i, strKengenCd);
					execDB.execSQL(strSql2.toString()); // SQL�����s
				}
			} else if (strShotriKbn.equals("2")) { // �����敪�F�X�V

				// �g�����U�N�V�����J�n
				super.createExecDB();
				execDB.BeginTran();
				// �����f�[�^�X�VSQL�쐬�������ďo���B
				strSql = kengenKanriUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s

				// �@�\�}�X�^�폜SQL�쐬�������ďo���B
				strSql2 = tantouKanriDeleteSQL(reqData);
				execDB.execSQL(strSql2.toString()); // SQL�����s
				// �@�\�}�X�^�o�^SQL�쐬�������ďo���B
				int intRecCount = reqData.getCntRow("ma_kinou");
				for (int i = 0; i < intRecCount; i++) {
					strSql3 = tantouKanriInsertSQL(reqData, i, reqData.getFieldVale("ma_kengen", 0, "cd_kengen"));
					execDB.execSQL(strSql3.toString()); // SQL�����s
				}

			} else if (strShotriKbn.equals("3")) { // �����敪�F�폜

				// �g�����U�N�V�����J�n
				super.createExecDB();
				execDB.BeginTran();
				// �S���҃f�[�^�폜SQL�쐬�������ďo���B
				strSql = kengenKanriDeleteSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQL�����s

				// �����S���҃f�[�^�폜SQL�쐬�������ďo���B
				strSql2 = tantouKanriDeleteSQL(reqData);
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
			storageGroupDataKanri(resKind.getTableItem(0));

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
	 * �����R�[�h�̔ԏ���
	 * @return String
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String saibanKengenCd() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
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
			strSelSql.append(" WHERE key1 = '�����R�[�h'");
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
				strInsSql.append(" WHERE key1 = '�����R�[�h'");
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
				strInsSql.append("  '�����R�[�h'");
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
	 * �����f�[�^�o�^SQL�쐬 : �����f�[�^�o�^�p�@SQL���쐬���A�e�f�[�^��DB�ɓo�^����B
	 * 
	 * @param reqData �F ���N�G�X�g�f�[�^
	 * @param strSql �F ��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer kengenKanriInsertSQL(RequestResponsKindBean requestData, String strKengenCd)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// �V�K���s�̔Ԃ��ꂽ�A�`�[���R�[�h�o�^�pSQL���쐬����B
		StringBuffer strSql = new StringBuffer();
		try {
			// ������
			String strKengenNm = requestData.getFieldVale("ma_kengen", 0, "nm_kengen");
			
			// �����f�[�^�o�^�pSQL�쐬
			strSql.append("INSERT INTO ma_kengen (");
			strSql.append(" cd_kengen");
			strSql.append(" ,nm_kengen");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ) VALUES ( ");
			strSql.append(strKengenCd);
			strSql.append(" ,'" + strKengenNm + "'");
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
	 * �����f�[�^�X�VSQL�쐬 : �����f�[�^�X�V�p�@SQL���쐬���A�e�f�[�^��DB�ɍX�V����B
	 * 
	 * @param reqData �F ���N�G�X�g�f�[�^
	 * @param strSql �F ��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer kengenKanriUpdateSQL(RequestResponsKindBean requestData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {
			// ����CD
			String strKengenCd = requestData.getFieldVale("ma_kengen", 0, "cd_kengen");
			// ������
			String strKengenNm = requestData.getFieldVale("ma_kengen", 0, "nm_kengen");

			// SQL���̍쐬
			strSql.append("UPDATE ma_kengen SET");
			strSql.append(" nm_kengen = '");
			strSql.append(strKengenNm + "'");
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_kengen = ");
			strSql.append(strKengenCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �����f�[�^�폜SQL�쐬 : �����f�[�^�폜�p�@SQL���쐬���A�e�f�[�^��DB�ɍ폜����B
	 * 
	 * @param reqData �F ���N�G�X�g�f�[�^
	 * @param strSql �F ��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer kengenKanriDeleteSQL(RequestResponsKindBean requestData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSql = new StringBuffer();
		try {
			// ����CD
			String strKengenCd = requestData.getFieldVale("ma_kengen", 0, "cd_kengen");
			
			// SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_kengen");
			strSql.append(" WHERE cd_kengen = ");
			strSql.append(strKengenCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �@�\�}�X�^�o�^SQL�쐬 : �@�\�f�[�^�o�^�p�@SQL���쐬���A�e�f�[�^��DB�ɓo�^����B
	 * 
	 * @param reqData �F ���N�G�X�g�f�[�^
	 * @param strKengenCd �F �����R�[�h
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantouKanriInsertSQL(RequestResponsKindBean reqData, int i, String strKengenCd)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {
			// ���ID
			String strGamenId = reqData.getFieldVale("ma_kinou", i, "id_gamen");
			// �@�\ID
			String strKinoNm = reqData.getFieldVale("ma_kinou", i, "nm_kino");
			// �ް�ID
			String strKinoId = reqData.getFieldVale("ma_kinou", i, "id_kino");
			// ����
			String strDataId = reqData.getFieldVale("ma_kinou", i, "id_data");
			// ���l
			String strBiko = reqData.getFieldVale("ma_kinou", i, "biko");
			// �@�\�}�X�^�o�^�pSQL�쐬
			strSql.append("INSERT INTO ma_kinou (");
			strSql.append(" cd_kengen");
			strSql.append(" ,id_gamen");
			strSql.append(" ,id_kino");
			strSql.append(" ,id_data");
			strSql.append(" ,nm_kino");
			strSql.append(" ,biko");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ) VALUES ( ");
			strSql.append(strKengenCd);
			strSql.append(" ," + strGamenId);
			strSql.append(" ," + strKinoId);
			strSql.append(" ," + strDataId);
			strSql.append(" ,'" + strKinoNm + "'");
			if ( !strBiko.equals("") ) {
				strSql.append(" ,'" + strBiko + "'");
			} else {
				strSql.append(" ,NULL");
			}
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
	 * �@�\�}�X�^�폜SQL�쐬 : �@�\�}�X�^�폜�p�@SQL���쐬���A�e�f�[�^��DB�ɍ폜����B
	 * 
	 * @param reqData �F ���N�G�X�g�f�[�^
	 * @param strKengenCd �F �����R�[�h
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer tantouKanriDeleteSQL(RequestResponsKindBean requestData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSql = new StringBuffer();
		try {
			// ����CD
			String strKengenCd = requestData.getFieldVale("ma_kengen", 0, "cd_kengen");
			// SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_kinou");
			strSql.append(" WHERE cd_kengen = ");
			strSql.append(strKengenCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * �Ǘ����ʃp�����[�^�[�i�[ : �S���҃f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * 
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGroupDataKanri(RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

			// �������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}