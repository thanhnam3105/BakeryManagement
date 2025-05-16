package jp.co.blueflag.shisaquick.srv.logic_gencho;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * �yQP@40404�z
 *  �f�U�C���X�y�[�X���F�s�폜
 *  �@�\ID�FFGEN3270
 *
 * @author E.Kitazawa
 * @since  2014/09/17
 */
public class FGEN3270_Logic extends LogicBase{

	/**
	 * �f�U�C���X�y�[�X���F�s�폜
	 * : �C���X�^���X����
	 */
	public FGEN3270_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �f�U�C���X�y�[�X���F�s�폜
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
	 * �f�[�^�X�VSQL�쐬
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
		StringBuffer strSql = new StringBuffer();
		StringBuffer strWhere = new StringBuffer();

		try {
			//���[�U���̉�ЃR�[�h���擾
			String strKaisha = userInfoData.getCd_kaisha();
			// ��������
			String strCdSeizokojo = reqData.getFieldVale("table", 0, "cd_seizokojo");
			String strCdShokuba   = reqData.getFieldVale("table", 0, "cd_shokuba");
			String strCdLine      = reqData.getFieldVale("table", 0, "cd_line");
			// �J�e�S���R�[�h��"_" �Ō���
			String cd_syurui      = reqData.getFieldVale("table", 0, "cd_syurui");
			String strTmp[]       = cd_syurui.split("_");
			String cd_literal     = strTmp[0];
			String cd_2nd_literal = strTmp.length > 1 ?  strTmp[1] : "";

			// ��ЁA�H��A�E��A���C���܂ł̌�������
			strWhere.append(" WHERE ");
			strWhere.append("  cd_kaisha = '" + strKaisha + "'  AND  ");
			strWhere.append("  cd_seizokojo = '" + strCdSeizokojo + "'  AND  ");
			strWhere.append("  cd_shokuba = '" +  strCdShokuba + "'  AND  ");
			strWhere.append("  cd_line = '" +  strCdLine + "' ");

			// SQL���̍쐬
			strSql.append(" SELECT nm_syurui  ");
			strSql.append("       ,nm_file  ");
			strSql.append(" FROM  tr_shisan_designspace  ");
			strSql.append( strWhere + " AND ");
			strSql.append("  cd_literal = '" +  cd_literal + "'  AND  ");
			strSql.append("  cd_2nd_literal = '" +  cd_2nd_literal + "' ");

			super.createSearchDB();

			List<?> lstRecset = searchDB.dbSearch(strSql.toString());

			if(lstRecset.size() > 0) {
				// �X�V SQL���̍쐬�F
				// �H��A�E��ALine �܂ł̃f�[�^�̍X�V�ҁA�X�V���t��UPDATE
				strSql = new StringBuffer();

				strSql.append(" UPDATE  ");
				strSql.append("  tr_shisan_designspace  ");
				strSql.append("  SET dt_koshin = getdate() ");
				strSql.append("    , id_koshin = " + userInfoData.getId_user() );
				strSql.append( strWhere );


				execDB.execSQL(strSql.toString());

				strSql = new StringBuffer();

				// �폜SQL���̍쐬
				strSql.append(" DELETE  ");
				strSql.append(" FROM  tr_shisan_designspace  ");
				strSql.append( strWhere + " AND ");
				strSql.append("  cd_literal = '" +  cd_literal + "'  AND  ");
				strSql.append("  cd_2nd_literal = '" +  cd_2nd_literal + "' ");

				execDB.execSQL(strSql.toString());

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
