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
 *  �f�U�C���X�y�[�X���F�ꗗ����
 *  �@�\ID�FFGEN3260
 *
 * @author E.Kitazawa
 * @since  2014/09/17
 */
public class FGEN3260_Logic extends LogicBase{

	/**
	 * �f�U�C���X�y�[�X���ꗗ����
	 * : �C���X�^���X����
	 */
	public FGEN3260_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �f�U�C���X�y�[�X���ꗗ����
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

		RequestResponsKindBean resKind = null;

		try {

			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//���X�|���X�f�[�^�̌`��
			this.setData(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜

		}
		return resKind;

	}

	/**
	 * ���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author E.Kitazawa
	 * @since  2014/09/11
	 */
	private void setData(

			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;

		// ���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {
			// �e�[�u����
			String strTblNm = "table";

			//�f�[�^�擾SQL�쐬
			strSqlBuf = this.createSQL(reqData);

			// null�̎��i���j���[����̃��[�h���j�͌������������s���Ȃ�
			if (strSqlBuf != null) {

				// ���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
				super.createSearchDB();
				lstRecset = searchDB.dbSearch(strSqlBuf.toString());

				// �������ʂ��Ȃ��ꍇ�A�����ŃG���[�Ƃ��Ȃ�
				if (lstRecset.size() == 0){
//					em.ThrowException(ExceptionKind.�x��Exception,"W000401", lstRecset.toString(), "", "");
				}

			}
			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "�f�U�C���X�y�[�X��񌟍����������s���܂����B");

		} finally {
			// ���X�g�̔j��
			removeList(lstRecset);

			if (searchDB != null) {
				// �Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;

			}

			// �ϐ��̍폜
			strSqlBuf = null;

		}

	}

	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return StringBuffer�F��������SQL
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
			// �f�U�C���X�y�[�X�A�b�v���[�h��̉�ʍă��[�h�p
			String strMovement_condition = toString(userInfoData.getMovement_condition());

			String strkeyKojo = "";
			String strkeyShokuba = "";
			String strkeyLine    = "";

			if (strMovement_condition.length() > 3) {
				strkeyKojo    = toString(userInfoData.getMovement_condition().get(1));
				strkeyShokuba = toString(userInfoData.getMovement_condition().get(2));
				strkeyLine    = toString(userInfoData.getMovement_condition().get(3));
			}

			//���[�U���̉�ЃR�[�h���擾
			String strKaisha = userInfoData.getCd_kaisha();

			// �����H��
			String strCdSeizokojo = reqData.getFieldVale("table", 0, "cd_seizokojo");
			// �E��
			String strCdShokuba   = reqData.getFieldVale("table", 0, "cd_shokuba");
			// �������C��
			String strCdLine      = reqData.getFieldVale("table", 0, "cd_line");

			// ��ށF�J�e�S���R�[�h��"_" �Ō���
			String cd_syurui      = reqData.getFieldVale("table", 0, "cd_syurui");
			String cd_literal     = "";
			String cd_2nd_literal = "";
			String strTmp[]       = null;

			// �����L�[�ɂ��ׂċ󔒂͋����Ȃ�
			if (strCdSeizokojo.equals("") && strCdShokuba.equals("") && strCdLine.equals("") && cd_syurui.equals("")) {
				// �A�b�v���[�h��̉�ʍă��[�h���A�O��̌����L�[�ōĎ擾
				if (!strkeyKojo.equals("")) {
					strCdSeizokojo = strkeyKojo;
					strCdShokuba   = strkeyShokuba;
					strCdLine      = strkeyLine;
				} else {
					// ���j���[����̃��[�h���͋�
					return null;

				}
			}

			// ���������F���
			strWhere.append(" T402.cd_kaisha = " + strKaisha );

			// ���������F�ݒ�l
			if (!strCdSeizokojo.equals("")) {
				strWhere.append(" AND  T402.cd_seizokojo = '" + strCdSeizokojo + "'");
			}
			if(!strCdShokuba.equals("")){
				strWhere.append(" AND  T402.cd_shokuba = '" + strCdShokuba + "'");
			}
			if(!strCdLine.equals("")){
				strWhere.append(" AND  T402.cd_line = '" + strCdLine + "'");
			}
			if(!cd_syurui.equals("")){
				strTmp         = cd_syurui.split("_");
				cd_literal     = strTmp[0];
				cd_2nd_literal = strTmp.length > 1 ?  strTmp[1] : "";

				strWhere.append(" AND  T402.cd_literal = '" + cd_literal + "'");
				strWhere.append(" AND  T402.cd_2nd_literal = '" + cd_2nd_literal + "'");
			}

			// SQL���̍쐬
			strSql.append(" SELECT  ");
			strSql.append("    T402.cd_seizokojo  ");
			strSql.append("   ,T402.cd_shokuba  ");
			strSql.append("   ,T402.cd_line  ");
			strSql.append("   ,T402.cd_literal  ");
			strSql.append("   ,T402.cd_2nd_literal  ");
			strSql.append("   ,T402.nm_syurui  ");
			strSql.append("   ,T402.nm_file  ");
			strSql.append("   ,M101.nm_user AS nm_koshin  ");
			strSql.append("   ,CONVERT(VARCHAR,T402.dt_koshin,120) AS dt_koshin  ");
			strSql.append("   ,LST.nm_busho   ");
			strSql.append("   ,LST.nm_shokuba   ");
			strSql.append("   ,LST.nm_line   ");
			strSql.append(" FROM  tr_shisan_designspace  AS T402 ");
			strSql.append(" LEFT JOIN  ");
			strSql.append("  (SELECT M405.cd_kaisha ");
			strSql.append("        , M405.cd_seizokojo ");
			strSql.append("        , M405.cd_shokuba ");
			strSql.append("        , M405.cd_line ");
			strSql.append("        , M104.nm_busho ");
			strSql.append("        , M405.nm_line ");
			strSql.append("        , M404.nm_shokuba ");
			strSql.append("   FROM ma_line M405 ");
			strSql.append("   LEFT JOIN ma_shokuba M404 ");
			strSql.append("   ON   (M405.cd_kaisha = M404.cd_kaisha ");
			strSql.append("    AND  M405.cd_seizokojo = M404.cd_seizokojo ");
			strSql.append("    AND  M405.cd_shokuba = M404.cd_shokuba) ");
			strSql.append("   LEFT JOIN ma_busho M104 ");
			strSql.append("   ON  (M404.cd_kaisha = M104.cd_kaisha ");
			strSql.append("    AND  M404.cd_seizokojo = M104.cd_busho) ");
			strSql.append("   WHERE M104.cd_kaisha = " + strKaisha );
			strSql.append("   GROUP BY M405.cd_kaisha ");
			strSql.append("          , M405.cd_seizokojo   ");
			strSql.append("          , M405.cd_shokuba   ");
			strSql.append("          , M405.cd_line  ");
			strSql.append("          , M104.nm_busho ");
			strSql.append("          ,M405.nm_line ");
			strSql.append("          ,M404.nm_shokuba)   AS LST ");
			strSql.append("   ON (T402.cd_kaisha = LST.cd_kaisha ");
			strSql.append("    AND T402.cd_seizokojo = LST.cd_seizokojo ");
			strSql.append("    AND T402.cd_shokuba = LST.cd_shokuba ");
			strSql.append("    AND T402.cd_line = LST.cd_line) ");
			strSql.append("   LEFT JOIN ma_user M101 ");
			strSql.append("   ON   M101.id_user = T402.id_koshin ");

			strSql.append(" WHERE  " + strWhere);
			// ��ޖ��̏�
			strSql.append(" ORDER BY   ");
			strSql.append("    T402.cd_seizokojo  ");
			strSql.append("   ,T402.cd_shokuba  ");
			strSql.append("   ,T402.cd_line  ");
			strSql.append("   ,T402.nm_syurui  ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstMstData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(

			  List<?> lstMstData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//���j���[����̃��[�h����lstMstData�� null�̈�
			if ((lstMstData== null) || (lstMstData.size() == 0)) {
				// �f�[�^���擾�ł��Ȃ����F�G���[�̂��Ȃ��ׁijs�̃��b�Z�[�W��Ԃ������j
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

			} else {

				for (int i = 0; i < lstMstData.size(); i++) {

					// �������ʂ̊i�[
					resTable.addFieldVale(i, "flg_return", "true");
					resTable.addFieldVale(i, "msg_error", "");
					resTable.addFieldVale(i, "no_errmsg", "");
					resTable.addFieldVale(i, "nm_class", "");
					resTable.addFieldVale(i, "cd_error", "");
					resTable.addFieldVale(i, "msg_system", "");

					Object[] items = (Object[]) lstMstData.get(i);

					// ���ʂ����X�|���X�f�[�^�Ɋi�[
					resTable.addFieldVale(i, "cd_seizokojo", toString(items[0],""));
					resTable.addFieldVale(i, "cd_shokuba", toString(items[1],""));
					resTable.addFieldVale(i, "cd_line", toString(items[2],""));
					resTable.addFieldVale(i, "cd_literal", toString(items[3],""));
					resTable.addFieldVale(i, "cd_2nd_literal", toString(items[4],""));
					resTable.addFieldVale(i, "nm_syurui", toString(items[5],""));
					resTable.addFieldVale(i, "nm_file", toString(items[6],""));
					resTable.addFieldVale(i, "nm_koshin", toString(items[7],""));
					resTable.addFieldVale(i, "dt_koshin", toString(items[8],""));
					resTable.addFieldVale(i, "nm_seizokojo", toString(items[9],""));
					resTable.addFieldVale(i, "nm_shokuba", toString(items[10],""));
					resTable.addFieldVale(i, "nm_line", toString(items[11],""));
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
