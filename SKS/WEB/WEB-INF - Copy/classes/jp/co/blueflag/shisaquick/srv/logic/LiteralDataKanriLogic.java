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
 * ���e�����f�[�^�Ǘ�DB�����F���e�����f�[�^���Ǘ��i�o�^�E�X�V�E�폜�j����SQL���쐬����B
 * M302_��iteral�e�[�u���f�[�^�̊Ǘ����s���B
 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA330O�v�ɐݒ肷��B
 * @author itou
 * @since  2009/04/15
 */
public class LiteralDataKanriLogic extends LogicBase{
	
	/**
	 * ���e�����f�[�^�Ǘ��R���X�g���N�^
	 */
	public LiteralDataKanriLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���e�����f�[�^����Ǘ��F���e�����f�[�^�̑���Ǘ����s���B
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
				//�����敪�F�o�^�@���e�����f�[�^�o�^SQL�쐬�������ďo���B
				strSql = literalKanriInsertSQL(reqData);
			} else if (strShotriKbn.equals("2")) {
				//�����敪�F�X�V�@���e�����f�[�^�X�VSQL�쐬�������ďo���B
				strSql = literalKanriUpdateSQL(reqData, strSql);
			} else if (strShotriKbn.equals("3")) {
				//�����敪�F�폜�@���e�����f�[�^�폜SQL�쐬�������ďo���B
				strSql = literalKanriDeleteSQL(reqData, strSql);
			}
			
			//�g�����U�N�V�����J�n
			super.createExecDB();
			execDB.BeginTran();

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
			storageLiteralDataKanri(resKind.getTableItem(0));
			
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
		}
		return resKind;
	}
	
	/**
	 * ���e�����f�[�^�o�^SQL�쐬 : ���e�����f�[�^�o�^�p�@SQL���쐬���A�e�f�[�^��DB�ɓo�^����B
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
	private StringBuffer literalKanriInsertSQL(RequestResponsKindBean requestData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		try {
			
			// �J�e�S���R�[�h
			String strCategoryCd = requestData.getFieldVale(0, 0, "cd_category");
			// ���e�����R�[�h
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");
			// ���e������
			String strLiteralNm = requestData.getFieldVale(0, 0, "nm_literal");
			// ���e�����l1
			String strValue1 = requestData.getFieldVale(0, 0, "value1");
			// ���e�����l2
			String strValue2 = requestData.getFieldVale(0, 0, "value2");
			// �\���l
			String strSortNo = requestData.getFieldVale(0, 0, "no_sort");
			// ���l
			String strBiko = requestData.getFieldVale(0, 0, "biko");
			// �ҏW��
			String strFlgEdit = requestData.getFieldVale(0, 0, "flg_edit");
			// �O���[�v
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");

			// ���e�����f�[�^�o�^SQL�쐬
			strSql.append("INSERT INTO ma_literal (");
			strSql.append(" cd_category");
			strSql.append(" ,cd_literal");
			strSql.append(" ,nm_literal");
			strSql.append(" ,value1");
			strSql.append(" ,value2");
			strSql.append(" ,no_sort");
			strSql.append(" ,biko");
			strSql.append(" ,flg_edit");
			strSql.append(" ,cd_group");
			strSql.append(" ,id_toroku");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,id_koshin");
			strSql.append(" ,dt_koshin");
			strSql.append(" ) VALUES ( '");
			strSql.append(strCategoryCd + "'");
			if (!(strLiteralCd.equals("") || strLiteralCd == null)) {
				// �擾�������e�����R�[�h��p���A���e�����f�[�^�o�^�pSQL���쐬����B
				strSql.append(" ,'" + strLiteralCd + "'");
			} else {
				// �V�K���s�̔Ԃ��ꂽ�A���e�����R�[�h�o�^�pSQL���쐬����B
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
					strSelSql.append(" WHERE key1 = '���e�����R�[�h'");
					strSelSql.append(" AND key2 = '");
					strSelSql.append(strCategoryCd + "'");
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
						strInsSql.append(" WHERE key1 = '���e�����R�[�h'");
						strInsSql.append(" AND key2 = '");
						strInsSql.append(strCategoryCd + "'");

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
						strInsSql.append("  '���e�����R�[�h'");
						strInsSql.append(" ,'" + strCategoryCd + "'");
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
					
					strLiteralCd = strNoSeq;
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
				strSql.append(" ,'" + ("000" + strLiteralCd).substring(("000" + strLiteralCd).length() - 3) + "'");
			}
			strSql.append(" ,'" + strLiteralNm + "'");
			if ( !strValue1.equals("") ) {
				strSql.append(" ," + strValue1);
			} else {
				strSql.append(" ,NULL");
			}
			if ( !strValue2.equals("") ) {
				strSql.append(" ," + strValue2);
			} else {
				strSql.append(" ,NULL");
			}
			strSql.append(" ," + strSortNo);
			if ( !strBiko.equals("") ) {
				strSql.append(" ,'" + strBiko + "'");
			} else {
				strSql.append(" ,NULL");
			}
			strSql.append(" ," + strFlgEdit);
			if ( !strGroupCd.equals("") ) {
				strSql.append(" ," + strGroupCd);
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
	 * ���e�����f�[�^�X�VSQL�쐬
	 *  : ���e�����f�[�^�X�V�p�@SQL���쐬���A�e�f�[�^��DB�ɍX�V����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer literalKanriUpdateSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// �J�e�S���R�[�h
			String strCategoryCd = requestData.getFieldVale(0, 0, "cd_category");
			// ���e�����R�[�h
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");
			// ���e������
			String strLiteralNm = requestData.getFieldVale(0, 0, "nm_literal");
			// ���e�����l1
			String strValue1 = requestData.getFieldVale(0, 0, "value1");
			// ���e�����l2
			String strValue2 = requestData.getFieldVale(0, 0, "value2");
			// �\���l
			String strSortNo = requestData.getFieldVale(0, 0, "no_sort");
			// ���l
			String strBiko = requestData.getFieldVale(0, 0, "biko");
			// �ҏW��
			String strFlgEdit = requestData.getFieldVale(0, 0, "flg_edit");
			// �O���[�v
			String strGroupCd = requestData.getFieldVale(0, 0, "cd_group");

			//SQL���̍쐬
			strSql.append("UPDATE ma_literal SET");
			strSql.append("  nm_literal = '");
			strSql.append(strLiteralNm + "'");
			strSql.append(" ,value1 = ");
			if ( !strValue1.equals("") ) {
				strSql.append(strValue1);
			} else {
				strSql.append("NULL");
			}
			strSql.append(" ,value2 = ");
			if ( !strValue2.equals("") ) {
				strSql.append(strValue2);
			} else {
				strSql.append("NULL");
			}
			strSql.append(" ,no_sort = ");
			strSql.append(strSortNo);
			strSql.append(" ,biko = ");
			if ( !strBiko.equals("") ) {
				strSql.append("'"+strBiko + "'");
			} else {
				strSql.append("NULL");
			}
			strSql.append(" ,flg_edit = ");
			strSql.append(strFlgEdit);
			strSql.append(" ,cd_group = ");
			if ( !strGroupCd.equals("") ) {
				strSql.append(strGroupCd);
			} else {
				strSql.append("NULL");
			}
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd+ "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd+ "'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * ���e�����f�[�^�폜SQL�쐬
	 *  : ���e�����f�[�^�폜�p�@SQL���쐬���A�e�f�[�^��DB�ɍ폜����B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer literalKanriDeleteSQL(RequestResponsKindBean requestData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			// �J�e�S���R�[�h
			String strCategoryCd = requestData.getFieldVale(0, 0, "cd_category");
			// ���e�����R�[�h
			String strLiteralCd = requestData.getFieldVale(0, 0, "cd_literal");

			//SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd + "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd);
			strSql.append("'");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : ���e�����f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageLiteralDataKanri(RequestResponsTableBean resTable) 
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
