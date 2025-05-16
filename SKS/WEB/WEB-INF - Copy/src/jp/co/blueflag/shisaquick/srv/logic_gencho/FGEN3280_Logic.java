package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yQP@40404�z
 *  �f�U�C���X�y�[�X���F�o�^����
 *  �@�\ID�FFGEN3280
 *
 * @author E.Kitazawa
 * @since  2014/09/17
 */
public class FGEN3280_Logic extends LogicBase{

	/**
	 * �f�U�C���X�y�[�X���o�^
	 * : �C���X�^���X����
	 */
	public FGEN3280_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �f�U�C���X�y�[�X���o�^
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

		RequestResponsKindBean resKind = null;

		try {

			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();


			// �g�����U�N�V�����J�n
			super.createExecDB();

			execDB.BeginTran();

			strSql = createSQL(reqData);

			// �R�~�b�g
			execDB.Commit();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();

			resKind.setID(strKinoId);

            // �e�[�u�����̐ݒ�
            String strTableNm = reqData.getTableID(0);

            resKind.addTableItem(strTableNm);

            // ���X�|���X�f�[�^�̌`��
            this.storageData(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			if (execDB != null) {
				// ���[���o�b�N
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");

		} finally {
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSql = null;

		}
		return resKind;

	}

	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL�i�[�p
		StringBuffer strSql = null;

		try {
			//���[�U���̉�ЃR�[�h���擾
			String strKaisha = userInfoData.getCd_kaisha();
			// ��������
			String strCdSeizokojo  = reqData.getFieldVale("table", 0, "cd_seizokojo");
			String strCdShokuba    = reqData.getFieldVale("table", 0, "cd_shokuba");
			String strCdLine       = reqData.getFieldVale("table", 0, "cd_line");

			// �o�^���i":::"�ŕ����j
			String strCdSyurui     = reqData.getFieldVale("table", 0, "cd_syurui");
			String strNmSyurui     = reqData.getFieldVale("table", 0, "nm_syurui");
			String strNmfile       = reqData.getFieldVale("table", 0, "nm_file");

			String[] lstCdSyurui   = null;
			String[] lstNmSyurui   = null;
			String[] lstNmFile     = null;

            // �������[�h���擾����
            String strMode = reqData.getFieldVale("table", 0, "syoriMode");

            strSql = new StringBuffer();

			// SQL���̍쐬
            strSql.append(" SELECT nm_syurui  ");
            strSql.append("       ,nm_file  ");
            strSql.append(" FROM  tr_shisan_designspace  ");
            strSql.append(" WHERE ");
            strSql.append("  cd_kaisha = " + strKaisha + "  AND  ");
            strSql.append("  cd_seizokojo = '" + strCdSeizokojo + "'  AND  ");
            strSql.append("  cd_shokuba = '" +  strCdShokuba + "'  AND  ");
            strSql.append("  cd_line = '" +  strCdLine + "'");

            super.createSearchDB();

            List<?> lstRecset = searchDB.dbSearch(strSql.toString());

            if(lstRecset.size() > 0) {
            	strSql = new StringBuffer();

    			// �폜SQL���̍쐬
                strSql.append(" DELETE  ");
                strSql.append(" FROM  tr_shisan_designspace  ");
                strSql.append(" WHERE ");
                strSql.append("  cd_kaisha = " + strKaisha + "  AND  ");
                strSql.append("  cd_seizokojo = '" + strCdSeizokojo + "'  AND  ");
                strSql.append("  cd_shokuba = '" +  strCdShokuba + "'  AND  ");
                strSql.append("  cd_line = '" +  strCdLine + "'");

                execDB.execSQL(strSql.toString());
            }

            if (strMode.equals("ADD")) {
            	// �o�^���𕪊�
            	lstCdSyurui = strCdSyurui.split(":::");
            	lstNmSyurui = strNmSyurui.split(":::");
            	lstNmFile = strNmfile.split(":::");

            	for (int i = 0; i < lstNmSyurui.length; i++) {
            		if (!lstNmSyurui[i].equals("")) {

            			strSql = new StringBuffer();
            			String cd_syurui[] = null;
            			String cd_literal = "";
            			String cd_2nd_literal = "";
            			String nmFile = "";
            			// �G���[��h�����߂̃`�F�b�N�i��ނƃt�@�C�����̐��͓����j
            			if (i < lstCdSyurui.length) {
            				cd_syurui = lstCdSyurui[i].split("_");
            				cd_literal = cd_syurui[0];
            				cd_2nd_literal = cd_syurui.length > 1 ?  cd_syurui[1] : "";
            			}
            			if (i < lstNmFile.length)  nmFile = lstNmFile[i];

                        strSql.append("INSERT INTO tr_shisan_designspace (  ");
                        strSql.append("  cd_kaisha,  ");
                        strSql.append("  cd_seizokojo,  ");
                        strSql.append("  cd_shokuba,  ");
                        strSql.append("  cd_line,  ");
                        strSql.append("  no_hyoji,  ");
                        strSql.append("  cd_literal,  ");
                        strSql.append("  cd_2nd_literal,  ");
                        strSql.append("  nm_syurui,  ");
                        strSql.append("  nm_file,  ");
                        strSql.append("  id_toroku,  ");
                        strSql.append("  dt_toroku,  ");
                        strSql.append("  id_koshin,  ");
                        strSql.append("  dt_koshin  ");
                        strSql.append(")  ");
                        strSql.append("VALUES  ");
                        strSql.append("(  ");
                        strSql.append(      strKaisha  + ",  " );
                        strSql.append("  '" + strCdSeizokojo  + "',  ");
                        strSql.append("  '" + strCdShokuba + "',  ");
                        strSql.append("  '"  + strCdLine  + "',   ");
                        strSql.append("  '"  + i  + "',   ");
                        strSql.append("  '" + cd_literal + "',  ");
                        strSql.append("  '" + cd_2nd_literal + "',  ");
                        strSql.append("  '" + lstNmSyurui[i] + "',  ");
                        strSql.append("  '"  + nmFile  + "',   ");

                        strSql.append("  " + userInfoData.getId_user() + ",  ");
                        strSql.append("  GETDATE(),  ");
                        strSql.append("  " + userInfoData.getId_user() + ",  ");
                        strSql.append("  GETDATE()   ");
                        strSql.append(")  ");

                        execDB.execSQL(strSql.toString());
            		}
            	}
            }

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
            // �������ʂ̊i�[
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
