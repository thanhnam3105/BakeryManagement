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
 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA331O�v�ɐݒ肷��B
 * @author hisahori
 * @since  2014/10/10
 */
public class GentyoLiteralDataKanriLogic extends LogicBase{

	/**
	 * ���e�����f�[�^�Ǘ��R���X�g���N�^
	 */
	public GentyoLiteralDataKanriLogic() {
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
			// �\���l
			String strSortNo = requestData.getFieldVale(0, 0, "no_sort");
			// ���l
			String strBiko = requestData.getFieldVale(0, 0, "biko");
			// �ҏW��
			String strFlgEdit = requestData.getFieldVale(0, 0, "flg_edit");
			// ��񃊃e�����R�[�h
			String strLiteral2ndCd = requestData.getFieldVale(0, 0, "cd_2nd_literal");
			// ��񃊃e������
			String strLiteral2ndNm = requestData.getFieldVale(0, 0, "nm_2nd_literal");
			// ��񃊃e�����g�p�t���O�l
			String strFlg2ndEdit = requestData.getFieldVale(0, 0, "flg_2ndedit");
			// ��Q���e�����\���l
			String str2ndSortNo = requestData.getFieldVale(0, 0, "no_2ndsort");

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
			strSql.append(" ,cd_2nd_literal");
			strSql.append(" ,nm_2nd_literal");
			strSql.append(" ,no_2nd_sort");
			strSql.append(" ,flg_2ndedit");
			strSql.append(" ) VALUES ( '");
			strSql.append(strCategoryCd + "'");

			// �̔ԏ���
			String strNoSeq = "";
			StringBuffer strSelSql = new StringBuffer();
			StringBuffer strInsSql = new StringBuffer();
			StringBuffer strUpdSql = new StringBuffer();
			List<?> lstRecset = null;

			try {
				// �g�����U�N�V�����J�n
				super.createSearchDB();
				searchDB.BeginTran();

				//��Q���e�����������͂���Ă��Ȃ��ꍇ�A�i��P�j���e�����̓o�^
				if ( strLiteral2ndNm.equals("") ){
					/*** ���e�����R�[�h start **************************************/
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

					strSql.append(" ,'" + ("000" + strLiteralCd).substring(("000" + strLiteralCd).length() - 3) + "'");
					/*** ���e�����R�[�h end **************************************/

				//��Q���e�����������͂���Ă���ꍇ�A��Q���e�����̓o�^
				}else{
					/*** ��Q���e�����R�[�h�̎擾 start **************************************/
					// �̔ԏ���
					int intNoSeq = 0;

					// ���e�����R�[�h�̍̔�SQL�쐬
					strSelSql.append("SELECT top 1 cd_2nd_literal");
					strSelSql.append(" FROM ma_literal ");
					strSelSql.append(" WHERE cd_category = '" + strCategoryCd + "'");
					strSelSql.append(" AND cd_literal = '" + strLiteralCd + "'");
					strSelSql.append(" ORDER BY cd_2nd_literal desc ");
					//SQL�����s
					lstRecset = searchDB.dbSearch(strSelSql.toString());
					if (lstRecset.size() > 0) {
						// �̔Ԍ��ʂ�ޔ�
						Object items = (Object) lstRecset.get(0);
						if (items.toString().equals("")){
							intNoSeq = 0;
						}else{
							intNoSeq = Integer.parseInt(items.toString()); //001 �� 1
						}
						strLiteral2ndCd = String.valueOf(intNoSeq + 1);  //�{1���ĕ������
					} else {
						strLiteral2ndCd = "1";
					}

					strSql.append(" ,'" + strLiteralCd + "'");  //���e�����R�[�h
					/*** ��Q���e�����R�[�h�̎擾 end **************************************/


					// ��Q���e�������g�p�J�n����܂ŁA�g���Ă����i��P�j���e�����p�̃��R�[�h���폜
					strUpdSql.append("DELETE ma_literal");
					strUpdSql.append(" WHERE cd_category = '");
					strUpdSql.append(strCategoryCd + "'");
					strUpdSql.append(" AND cd_literal = '");
					strUpdSql.append(strLiteralCd + "'");
					strUpdSql.append(" AND cd_2nd_literal = ''"); //��Q���e�������󔒂̂��̂��Ώ�

					//SQL�����s
					super.createExecDB();
					execDB.setSession(searchDB.getSession());
					execDB.execSQL(strUpdSql.toString());

					//�R�~�b�g
					searchDB.Commit();
				}
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

			/*** ���e�������ȉ� **************************************/
			strSql.append(" ,'" + strLiteralNm + "'");  //���e������
			strSql.append(" ,NULL");  // �����ޒ��B���p�J�e�S���}�X�^�ł͕ҏW���Ȃ�����
			strSql.append(" ,NULL");  // �����ޒ��B���p�J�e�S���}�X�^�ł͕ҏW���Ȃ�����
			strSql.append(" ," + strSortNo);
			if ( !strBiko.equals("") ) {
				strSql.append(" ,'" + strBiko + "'");  // ���l
			} else {
				strSql.append(" ,NULL");
			}
			strSql.append(" ," + strFlgEdit);  // �ҏW�t���O
			strSql.append(" ,NULL");  // �����ޒ��B���p�J�e�S���}�X�^�ł͕ҏW���Ȃ�����
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");
			strSql.append(" ," + userInfoData.getId_user());
			strSql.append(" ,GETDATE()");

			//��Q���e��������
			if ( !strLiteral2ndNm.equals("") ){
				//���͂���Ă��Ȃ��ꍇ�A�i��P�j���e�����̓o�^
				strSql.append(" ,'" + ("000" + strLiteral2ndCd).substring(("000" + strLiteral2ndCd).length() - 3) + "'");  //��Q���e�����R�[�h
				strSql.append(" ,'" + strLiteral2ndNm + "'");  //��Q���e������
				strSql.append(" ,'" + str2ndSortNo + "'");  //��Q���e�����\����
				strSql.append(" ,'1'");  //��Q���e�����g�p�t���O
			}else{
				//���͂���Ă���ꍇ�A��Q���e�����̓o�^
				strSql.append(" ,''");  //��Q���e�����R�[�h
				strSql.append(" ,NULL");  //��Q���e������
				strSql.append(" ,NULL");  //��Q���e�����\����
				strSql.append(" ,NULL");  //��Q���e�����g�p�t���O
			}

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
			// �\���l
			String strSortNo = requestData.getFieldVale(0, 0, "no_sort");
			// ���l
			String strBiko = requestData.getFieldVale(0, 0, "biko");
			// �ҏW��
			String strFlgEdit = requestData.getFieldVale(0, 0, "flg_edit");
			// ��񃊃e�����R�[�h
			String strLiteral2ndCd = requestData.getFieldVale(0, 0, "cd_2nd_literal");
			// ��񃊃e������
			String strLiteral2ndNm = requestData.getFieldVale(0, 0, "nm_2nd_literal");
			// ��Q�\���l
			String str2ndSortNo = requestData.getFieldVale(0, 0, "no_2ndsort");
			// ��񃊃e�����g�p�t���O�l
			String strFlg2ndEdit = requestData.getFieldVale(0, 0, "flg_2ndedit");

			//SQL���̍쐬
			strSql.append("UPDATE ma_literal SET");
			if ( strLiteral2ndNm.equals("") ){
				//��Q���e�����������͂���Ă��Ȃ���΁A�i��P�j���e�����̍X�V
				strSql.append("  nm_literal = '");
				strSql.append(strLiteralNm + "'");
				strSql.append(" ,no_sort = ");
				strSql.append(strSortNo);
				strSql.append(" ,biko = ");
				if ( !strBiko.equals("") ) {
					strSql.append("'"+ strBiko + "'");
				} else {
					strSql.append("NULL");
				}
				strSql.append(" ,flg_edit = ");
				strSql.append(strFlgEdit);
			}else{
				//��Q���e�����������͂���Ă���΁A��Q���e�����̍X�V
				strSql.append(" cd_2nd_literal = '");
				strSql.append(strLiteral2ndCd + "'");
				strSql.append(" ,nm_2nd_literal = '");
				strSql.append(strLiteral2ndNm + "'");
				strSql.append(" ,no_2nd_sort = ");
				strSql.append(str2ndSortNo);
				strSql.append(" ,flg_2ndedit = ");
				strSql.append(strFlg2ndEdit);
			}
			strSql.append(" ,id_koshin = ");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_category = '");
			strSql.append(strCategoryCd+ "'");
			strSql.append(" AND cd_literal = '");
			strSql.append(strLiteralCd+ "'");
			if ( !strLiteral2ndNm.equals("") ){
				strSql.append(" AND cd_2nd_literal = '");
				strSql.append(strLiteral2ndCd+ "'");
			}

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
			// ���e������
			String strLiteralNm = requestData.getFieldVale(0, 0, "nm_literal");
			// ��񃊃e�����R�[�h
			String strLiteral2ndCd = requestData.getFieldVale(0, 0, "cd_2nd_literal");
			// ��񃊃e������
			String strLiteral2ndNm = requestData.getFieldVale(0, 0, "nm_2nd_literal");

			//SQL���̍쐬
			if ( strLiteral2ndNm.equals("") ){
				//��Q���e�����������͂���Ă��Ȃ���΁A�i��P�j���e�����̍폜�B��Q���e�����g�p�ŁA�����s�����Ă��S�č폜
				strSql.append("DELETE");
				strSql.append(" FROM ma_literal");
				strSql.append(" WHERE cd_category = '");
				strSql.append(strCategoryCd + "'");
				strSql.append(" AND cd_literal = '");
				strSql.append(strLiteralCd + "'");
			}else{
				//��Q���e�����������͂���Ă���΁A��Q���e�����̍폜
				strSql.append("DELETE");
				strSql.append(" FROM ma_literal");
				strSql.append(" WHERE cd_category = '");
				strSql.append(strCategoryCd + "'");
				strSql.append(" AND cd_literal = '");
				strSql.append(strLiteralCd + "'");
				strSql.append(" AND cd_2nd_literal = '");
				strSql.append(strLiteral2ndCd + "'");
			}

			StringBuffer strSelSql = new StringBuffer();
			StringBuffer strInsSql = new StringBuffer();
			List<?> lstRecset = null;

			try {
				// �g�����U�N�V�����J�n
				super.createSearchDB();
				searchDB.BeginTran();

				// �Ώۃf�[�^�������R�[�h���邩
				strSelSql.append("SELECT cd_literal");
				strSelSql.append(" FROM ma_literal ");
				strSelSql.append(" WHERE cd_category = '");
				strSelSql.append(strCategoryCd + "'");
				strSelSql.append(" AND cd_literal = '");
				strSelSql.append(strLiteralCd + "'");

				lstRecset = searchDB.dbSearch(strSelSql.toString());

				if ( strLiteral2ndNm.equals("") ){
					// ��P���e���������Ȃ��ꍇ�͋C�ɂ��Ȃ�
				}else{
					// ��Q���e�����̍Ō�̈���폜�����ꍇ�A�i��P�j���e�����p�̃��R�[�h���쐬
					if (lstRecset.size() <= 1) {
						strInsSql.append("INSERT INTO ma_literal (");
						strInsSql.append(" cd_category");
						strInsSql.append(" ,cd_literal");
						strInsSql.append(" ,nm_literal");
						strInsSql.append(" ,value1");
						strInsSql.append(" ,value2");
						strInsSql.append(" ,no_sort");
						strInsSql.append(" ,biko");
						strInsSql.append(" ,flg_edit");
						strInsSql.append(" ,cd_group");
						strInsSql.append(" ,id_toroku");
						strInsSql.append(" ,dt_toroku");
						strInsSql.append(" ,id_koshin");
						strInsSql.append(" ,dt_koshin");
						strInsSql.append(" ,cd_2nd_literal");
						strInsSql.append(" ,nm_2nd_literal");
						strInsSql.append(" ,no_2nd_sort");
						strInsSql.append(" ,flg_2ndedit");
						strInsSql.append(" )");
						strInsSql.append(" SELECT");
						strInsSql.append(" cd_category");
						strInsSql.append(" ,cd_literal");
						strInsSql.append(" ,nm_literal");
						strInsSql.append(" ,NULL");
						strInsSql.append(" ,NULL");
						strInsSql.append(" ,no_sort");
						strInsSql.append(" ,biko");
						strInsSql.append(" ,flg_edit");
						strInsSql.append(" ,cd_group");
						strInsSql.append(" ,id_toroku");
						strInsSql.append(" ,dt_toroku");
						strInsSql.append(" ,id_koshin");
						strInsSql.append(" ,dt_koshin");
						strInsSql.append(" ,''"); // �L�[�Ȃ̂�NULL�ł͂Ȃ���
						strInsSql.append(" ,NULL");
						strInsSql.append(" ,NULL");
						strInsSql.append(" ,NULL");
						strInsSql.append(" FROM ma_literal ");
						strInsSql.append(" WHERE cd_category = '");
						strInsSql.append(strCategoryCd + "'");
						strInsSql.append(" AND cd_literal = '");
						strInsSql.append(strLiteralCd + "'");
						strInsSql.append(" AND cd_2nd_literal = '");
						strInsSql.append(strLiteral2ndCd + "'");

						//SQL�����s
						super.createExecDB();
						execDB.setSession(searchDB.getSession());
						execDB.execSQL(strInsSql.toString());
						//�R�~�b�g
						searchDB.Commit();
					}

				}

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
				strInsSql = null;
			}
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
