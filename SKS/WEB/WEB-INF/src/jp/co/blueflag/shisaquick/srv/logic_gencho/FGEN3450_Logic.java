package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yQP@40404�z
 *  ���ގ�z���́F���ރe�[�u������
 *  �@�\ID�FFGEN3450
 *
 * @author E.Kitazawa
 * @since  2014/09/11
 */
public class FGEN3450_Logic extends LogicBase{

	/**
	 * ���ގ�z���́F���ރe�[�u������
	 * : �C���X�^���X����
	 */
	public FGEN3450_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * ���ގ�z���́F���ރe�[�u������
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

			// �������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", lstRecset.toString(), "", "");
			}
			}

			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm), reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "���ރe�[�u���������������s���܂����B");

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

		try {
			// �f�U�C���X�y�[�X�A�b�v���[�h��̉�ʍă��[�h�p
			String strMovement_condition = toString(userInfoData.getMovement_condition());
			String strCd_seihin_tmp = "";
			String strCd_sisakuNo_tmp = "";
			String strCd_shain_tmp = "";
			String strNen_tmp = "";
			String strNo_oi_tmp    = "";
			String strNo_eda_tmp    = "";

			if (strMovement_condition.length() > 6) {
				strCd_seihin_tmp    = toString(userInfoData.getMovement_condition().get(0));
				strCd_sisakuNo_tmp    = toString(userInfoData.getMovement_condition().get(1));
				strCd_shain_tmp    = toString(userInfoData.getMovement_condition().get(2));
				strNen_tmp = toString(userInfoData.getMovement_condition().get(3));
				strNo_oi_tmp = toString(userInfoData.getMovement_condition().get(4));
				strNo_eda_tmp    = toString(userInfoData.getMovement_condition().get(5));
			}

			// ����No.
			String strCd_shain = reqData.getFieldVale("table", 0, "cd_shain");
			String strNen      = reqData.getFieldVale("table", 0, "nen");
			String strNo_oi    = reqData.getFieldVale("table", 0, "no_oi");
			String strNo_eda   = reqData.getFieldVale("table", 0, "no_eda");

			long lngCd_shain = 0;
			int intNen = 0;
			int intNo_oi = 0;
			int intNo_eda = 0;

			// �����L�[�ɋ󔒂͋����Ȃ�
			if (strCd_shain.equals("") || strNen.equals("") || strNo_oi.equals("") || strNo_eda.equals("")) {
				// �A�b�v���[�h��̉�ʍă��[�h���A�O��̌����L�[�ōĎ擾
				if (!strCd_sisakuNo_tmp.equals("")) {
					strCd_shain = strCd_shain_tmp;
					strNen      = strNen_tmp;
					strNo_oi    = strNo_oi_tmp;
					strNo_eda    = strNo_eda_tmp;
				} else {
					// ���j���[����̃��[�h���͋�
					return null;
				}
//				em.ThrowException(ExceptionKind.���Exception,"E000001","����No","","");
			}

			// ���l �ɕϊ�
			lngCd_shain =  Long.parseLong(strCd_shain);
			intNen      =  Integer.parseInt(strNen);
			intNo_oi    =  Integer.parseInt(strNo_oi);
			intNo_eda   =  Integer.parseInt(strNo_eda);

			// SQL���̍쐬
			strSql.append(" SELECT T340.cd_shain  ");
			strSql.append("       ,T340.nen  ");
			strSql.append("       ,T340.no_oi  ");
			strSql.append("       ,T340.no_eda  ");
			strSql.append("       ,T340.seq_shizai  ");
			strSql.append("       ,T340.cd_kaisha  ");
			strSql.append("       ,T340.cd_busho  ");
			strSql.append("       ,T340.cd_shizai  ");
			strSql.append("       ,T340.nm_shizai  ");
			strSql.append("       ,T340.tanka  ");
			strSql.append("       ,T340.cd_shizai_new  ");
			strSql.append("       ,T340.nm_shizai_new  ");
			strSql.append("       ,T340.cd_seizokojo  ");
			strSql.append("       ,T340.cd_shokuba  ");
			strSql.append("       ,T340.cd_line  ");
			strSql.append("       ,T340.cd_seihin  ");
			strSql.append("       ,T340.cd_taisyoshizai  ");
			strSql.append("       ,T340.cd_hattyusaki  ");
			strSql.append("       ,T340.chk_kanryo  ");
			strSql.append("       ,M101.nm_user AS nm_koshin  ");
			strSql.append("       ,CONVERT(VARCHAR,T340.dt_koshin,111) AS dt_koshin  ");
//			strSql.append("       ,CONVERT(VARCHAR,T340.dt_koshin,120) AS dt_koshin  ");
			// ���ގ�z�e�[�u���̍X�V���Ǝ�z�X�e�[�^�X���擾����
			strSql.append("       ,T401.dt_koshin AS dt_tehai_koshin  ");
			strSql.append("       ,T401.flg_tehai_status  ");
			//������̃f�[�^�擾 �yKPX@1602367�zMay Thu 2016/09/20 add start
			strSql.append("       ,ISNULL(M102.nm_user,'') AS nm_hattyu  ");
			strSql.append("       ,CONVERT(VARCHAR,T340.dt_hattyu,111) AS dt_hattyu  ");
//			strSql.append("       ,CONVERT(VARCHAR,T340.dt_hattyu,120) AS dt_hattyu  ");
			//������̃f�[�^�擾 �yKPX@1602367�zMay Thu 2016/09/20 add end
			//�ԉ��t�@�C�����ƃp�X�擾 �yKPX@1602367�zMay Thu 2016/09/28 add start
			strSql.append("       ,T340.nm_file_henshita  ");
			strSql.append("       ,T340.file_path_henshita  ");
			//�ԉ��t�@�C�����ƃp�X�擾 �yKPX@1602367�zMay Thu 2016/09/28 add end
			//������̃f�[�^�擾 �yKPX@1602367�zMay Thu 2016/09/28 add end
			strSql.append(" FROM  tr_shisan_shizai T340 ");
			strSql.append(" LEFT JOIN  tr_shizai_tehai T401 ");
			strSql.append(" ON   ( T340.cd_shain   = T401.cd_shain ");
			strSql.append("   AND  T340.nen        = T401.nen");
			strSql.append("   AND  T340.no_oi      = T401.no_oi");
			strSql.append("   AND  T340.no_eda     = T401.no_eda ");
			strSql.append("   AND  T340.seq_shizai = T401.seq_shizai) ");
			strSql.append("   LEFT JOIN ma_user M101 ");
			strSql.append("   ON   M101.id_user = T340.id_koshin ");
			strSql.append("   LEFT JOIN ma_user M102 ");
			strSql.append("   ON   T340.cd_hattyu = M102.id_user ");
			strSql.append(" WHERE T340.cd_shain = "+ lngCd_shain );
			strSql.append("   AND T340.nen = "     + intNen );
			strSql.append("   AND T340.no_oi = "   + intNo_oi );
			strSql.append("   AND T340.no_eda = "  + intNo_eda );
			//������̃f�[�^�擾 �yKPX@1602367�zMay Thu 2016/11/01 update start
			//strSql.append(" ORDER BY  T340.no_sort");
			strSql.append(" ORDER BY ");
			strSql.append("   T340.dt_hattyu, ");
			strSql.append("   case T401.flg_tehai_status when 2 then 1 else 2 end, ");
			strSql.append("   case T401.flg_tehai_status when 3 then 1 else 2 end, ");
			strSql.append("   case T401.flg_tehai_status when 1 then 1 else 2 end ");
			//������̃f�[�^�擾 �yKPX@1602367�zMay Thu 2016/11/01 update end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstSizaiData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(

			  List<?> lstSizaiData
			, RequestResponsTableBean resTable
			, RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//���j���[����̃��[�h����lstMstData�� null�̈�
			if ((lstSizaiData== null) || (lstSizaiData.size() == 0)) {
				// �f�[�^���擾�ł��Ȃ����F�G���[�̂��Ȃ��ׁijs�̃��b�Z�[�W��Ԃ������j
				resTable.addFieldVale(0, "flg_return", "");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");
				resTable.addFieldVale(0, "sisakuNo", "");
			} else {
			    String strMovement_condition = toString(userInfoData.getMovement_condition());
 		        String sisakuNo   = reqData.getFieldVale("table", 0, "sisakuNo");
 		        if(sisakuNo == null || sisakuNo.equals("")) {
 		            // �f�U�C���X�y�[�X�A�b�v���[�h��̉�ʍă��[�h�p
 		            sisakuNo    = toString(userInfoData.getMovement_condition().get(1));
 		        }
			for (int i = 0; i < lstSizaiData.size(); i++) {

				// �������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				resTable.addFieldVale(i, "sisakuNo", sisakuNo);

				Object[] items = (Object[]) lstSizaiData.get(i);

				// ���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_shain", toString(items[0],""));
				resTable.addFieldVale(i, "nen", toString(items[1],""));
				resTable.addFieldVale(i, "no_oi", toString(items[2],""));
				resTable.addFieldVale(i, "no_eda", toString(items[3],""));
				resTable.addFieldVale(i, "seq_shizai", toString(items[4],""));
				resTable.addFieldVale(i, "cd_kaisha", toString(items[5],""));
				resTable.addFieldVale(i, "cd_busho", toString(items[6],""));
				resTable.addFieldVale(i, "cd_shizai", toString(items[7],""));
				resTable.addFieldVale(i, "nm_shizai", toString(items[8],""));
				resTable.addFieldVale(i, "tanka", toString(items[9],""));
				resTable.addFieldVale(i, "cd_shizai_new", toString(items[10],""));
				resTable.addFieldVale(i, "nm_shizai_new", toString(items[11],""));
				resTable.addFieldVale(i, "cd_seizokojo", toString(items[12],""));
				resTable.addFieldVale(i, "cd_shokuba", toString(items[13],""));
				resTable.addFieldVale(i, "cd_line", toString(items[14],""));
				resTable.addFieldVale(i, "cd_seihin", toString(items[15],""));
				resTable.addFieldVale(i, "cd_taisyoshizai", toString(items[16],""));
				resTable.addFieldVale(i, "cd_hattyusaki", toString(items[17],""));
				resTable.addFieldVale(i, "chk_kanryo", toString(items[18],""));
				resTable.addFieldVale(i, "nm_koshin", toString(items[19],""));
				resTable.addFieldVale(i, "dt_koshin", toString(items[20],""));
				// ���ގ�z�e�[�u���̍X�V���Ǝ�z�X�e�[�^�X
				resTable.addFieldVale(i, "dt_tehai_koshin", toString(items[21],""));
				resTable.addFieldVale(i, "flg_tehai_status", toString(items[22],""));
				//������̃f�[�^�擾 �yKPX@1602367�zMay Thu 2016/09/20 add start
				resTable.addFieldVale(i, "nm_hattyu", toString(items[23],""));
				resTable.addFieldVale(i, "dt_hattyu", toString(items[24],""));
				resTable.addFieldVale(i, "nm_file_henshita", toString(items[25],""));
				resTable.addFieldVale(i, "file_path_henshita", toString(items[26],""));
				//������̃f�[�^�擾 �yKPX@1602367�zMay Thu 2016/09/20 add end
			}
            }
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
