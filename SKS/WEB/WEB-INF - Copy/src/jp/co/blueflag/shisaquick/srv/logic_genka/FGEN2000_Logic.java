package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.ArrayList;
import java.util.List;

/**
 * �yQP@00342�z
 * �X�e�[�^�X�����@��������
 *  : �X�e�[�^�X������ʂ̏����擾����B
 *  �@�\ID�FFGEN2000
 *
 * @author Nishigawa
 * @since  2011/01/24
 */
public class FGEN2000_Logic extends LogicBase{

	/**
	 * �X�e�[�^�X�����@��������
	 * : �C���X�^���X����
	 */
	public FGEN2000_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �X�e�[�^�X�����@��������
	 *  : �X�e�[�^�X������ʂ̏����擾����B
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

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;

		try {

			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//���X�|���X�f�[�^�̌`��
			this.genkaKihonSetting(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);

			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜

		}
		return resKind;

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting�i���X�|���X�f�[�^�̌`���j
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * ���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void genkaKihonSetting(

			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;

		//���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {

			//�e�[�u����
			String strTblNm = "kihon";

			//�@�f�[�^�擾SQL�쐬
			strSqlBuf = this.createGenkaKihonSQL(reqData);

			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000407", strSqlBuf.toString(), "", "");
			}

			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "�X�e�[�^�X�����@�f�[�^�������������s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);

			if (searchDB != null) {
				//�Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;

			}

			//�ϐ��̍폜
			strSqlBuf = null;

		}

	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL�iSQL�������j
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaKihonSQL(

			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {
			//���N�G�X�g����f�[�^���o
			String strCdShain = toString(reqData.getFieldVale(0, 0, "cd_shain"));
			String strNen = toString(reqData.getFieldVale(0, 0, "nen"));
			String strNoOi = toString(reqData.getFieldVale(0, 0, "no_oi"));
			String strNoEda = toString(reqData.getFieldVale(0, 0, "no_eda"));
			String strNoPage = reqData.getFieldVale(0, 0, "no_page");

			//���N�G�X�g�̃L�[���ڂ̂����ꂩ��""�̏ꍇ
			if(strCdShain.equals("") || strNen.equals("") || strNoOi.equals("") || strNoEda.equals("")){
				//userInfoData����擾
				strCdShain = toString(userInfoData.getMovement_condition().get(0));
				strNen = toString(userInfoData.getMovement_condition().get(1));
				strNoOi = toString(userInfoData.getMovement_condition().get(2));
				strNoEda = toString(userInfoData.getMovement_condition().get(3));
			}

			//�R���X�g�t�@�C������f�[�^�擾
			String strListRowMax =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			//SQL���̍쐬
			strSql.append(" SELECT  ");
			strSql.append("  tbl.no_row  "); 				// 0�@�s�ԍ�
			strSql.append(" ,tbl.cd_shain  ");			// 1�@����No_�Ј�CD
			strSql.append(" ,tbl.nen   ");					// 2�@����No_�N
			strSql.append(" ,tbl.no_oi    ");				// 3�@����No_�ǔ�
			strSql.append(" ,tbl.no_eda   ");				// 4�@����No_�}��
			strSql.append(" ,tbl.dt_henkou_ymd  ");	// 5�@�ύX�����i�N�����j
			strSql.append(" ,tbl.dt_henkou_hmd  ");	// 6�@�ύX�����i�����b�j
			strSql.append(" ,tbl.nm_user  ");			// 7�@���[�U��
			strSql.append(" ,tbl.nm_category  ");		// 8�@�J�e�S����
			strSql.append(" ,tbl.nm_��iteral  ");			// 9�@���e������
			strSql.append(" ,tbl.st_kenkyu  ");			// 10�@�������X�e�[�^�X
			strSql.append(" ,tbl.st_seisan  ");			// 11�@���Y�Ǘ����X�e�[�^�X
			strSql.append(" ,tbl.st_gensizai  ");			// 12�@�����ޒ��B���X�e�[�^�X
			strSql.append(" ,tbl.st_kojo  ");				// 13�@�H��X�e�[�^�X
			strSql.append(" ,tbl.st_eigyo  ");			// 14�@�c�ƃX�e�[�^�X
			strSql.append(" ,cnttbl.max_row  ");		// 15�@�S����
			strSql.append(" ,tbl.cd_zikko_li  ");		// 16�@���e�����ԍ� ADD �yH24�N�x�Ή��z 2012/04/16 ��
			strSql.append(" FROM (  ");
			strSql.append(" 	SELECT  ");
			strSql.append(" 	 (CASE  ");
			strSql.append(" 		WHEN ROW_NUMBER() OVER (  ");
			strSql.append(" 			ORDER BY T1.cd_shain  ");
			strSql.append(" 			, T1.nen  ");
			strSql.append(" 			, T1.no_oi  ");
			strSql.append(" 			, T1.no_eda)%" + strListRowMax + " = 0 THEN " + strListRowMax);
			strSql.append(" 		ELSE ROW_NUMBER() OVER (  ");
			strSql.append(" 			ORDER BY T1.cd_shain  ");
			strSql.append(" 			, T1.nen  ");
			strSql.append(" 			, T1.no_oi  ");
			strSql.append(" 			, T1.no_eda)%" + strListRowMax);
			strSql.append(" 		END) AS no_row  ");
			strSql.append(" 	,Convert(int  ");
			strSql.append(" 		,(ROW_NUMBER() OVER (  ");
			strSql.append(" 				ORDER BY T1.cd_shain  ");
			strSql.append(" 				, T1.nen  ");
			strSql.append(" 				, T1.no_oi  ");
			strSql.append(" 				, T1.no_eda)-1)/" + strListRowMax + " + 1) AS PageNO  ");
			strSql.append(" 	,RIGHT('0000000000' + CONVERT(varchar,T1.cd_shain),10) AS cd_shain  ");
			strSql.append(" 	,RIGHT('00' + CONVERT(varchar,T1.nen),2) AS nen   ");
			strSql.append(" 	,RIGHT('000' + CONVERT(varchar,T1.no_oi),3) AS no_oi    ");
			strSql.append(" 	,RIGHT('000' + CONVERT(varchar,T1.no_eda),3) AS no_eda   ");
			strSql.append("     ,CONVERT(VARCHAR(10),T1.dt_henkou,111) AS dt_henkou_ymd  ");
			strSql.append("     ,CONVERT(VARCHAR(8),T1.dt_henkou,108) AS dt_henkou_hmd  ");
			strSql.append("     ,m1.nm_user  ");
			strSql.append(" 	,c1.nm_category  ");
			strSql.append(" 	,l1.nm_��iteral  ");
			strSql.append("     ,T1.st_kenkyu  ");
			strSql.append("     ,T1.st_seisan  ");
			strSql.append("     ,T1.st_gensizai  ");
			strSql.append("     ,T1.st_kojo  ");
			strSql.append("     ,T1.st_eigyo  ");
			strSql.append("     ,T1.cd_zikko_li  ");
			strSql.append(" 	FROM  ");
			strSql.append(" 	 tr_shisan_status_rireki T1  ");
			strSql.append(" 	 left join ma_user as m1  ");
			strSql.append(" 		on t1.id_henkou = m1.id_user  ");
			strSql.append("  	 left join ma_category as c1  ");
			strSql.append(" 		on t1.cd_zikko_ca = c1.cd_category  ");
			strSql.append(" 	 left join ma_literal as l1  ");
			strSql.append(" 		on t1.cd_zikko_ca = l1.cd_category  ");
			strSql.append(" 		and t1.cd_zikko_li = l1.cd_literal  ");
			strSql.append(" 	WHERE  ");
			strSql.append(" 	 T1.cd_shain = " + strCdShain);
			strSql.append(" 	 AND T1.nen =  " + strNen);
			strSql.append(" 	 AND T1.no_oi = " + strNoOi);
			strSql.append(" 	 AND T1.no_eda = " + strNoEda);
			//strSql.append(" 	 AND T1.cd_zikko_li <> 0");  //���ۑ��ȊO //DEL �yH24�N�x�Ή��z 2012/04/16 ��
			strSql.append(" ) AS tbl  ");
			strSql.append(" ,(  ");
			strSql.append(" 	SELECT  ");
			strSql.append(" 	 COUNT(*) as max_row  ");
			strSql.append(" 	FROM (  ");
			strSql.append(" 		SELECT  ");
			strSql.append(" 		 (CASE  ");
			strSql.append(" 			WHEN ROW_NUMBER() OVER (  ");
			strSql.append(" 				ORDER BY T1.cd_shain  ");
			strSql.append(" 				, T1.nen  ");
			strSql.append(" 				, T1.no_oi  ");
			strSql.append(" 				, T1.no_eda)%" + strListRowMax + " = 0 THEN " + strListRowMax);
			strSql.append(" 			ELSE ROW_NUMBER() OVER (  ");
			strSql.append(" 				ORDER BY T1.cd_shain  ");
			strSql.append(" 				, T1.nen  ");
			strSql.append(" 				, T1.no_oi  ");
			strSql.append(" 				, T1.no_eda)%" +strListRowMax);
			strSql.append(" 			END) AS no_row  ");
			strSql.append(" 		,Convert(int  ");
			strSql.append(" 			,(ROW_NUMBER() OVER (  ");
			strSql.append(" 					ORDER BY T1.cd_shain  ");
			strSql.append(" 					, T1.nen  ");
			strSql.append(" 					, T1.no_oi  ");
			strSql.append(" 					, T1.no_eda)-1)/" + strListRowMax + " + 1) AS PageNO  ");
			strSql.append(" 	    ,RIGHT('0000000000' + CONVERT(varchar,T1.cd_shain),10) AS cd_shain  ");
			strSql.append(" 	    ,RIGHT('00' + CONVERT(varchar,T1.nen),2) AS nen   ");
			strSql.append(" 	    ,RIGHT('000' + CONVERT(varchar,T1.no_oi),3) AS no_oi    ");
			strSql.append(" 	    ,RIGHT('000' + CONVERT(varchar,T1.no_eda),3) AS no_eda   ");
			strSql.append(" 		,CONVERT(VARCHAR(10),t1.dt_henkou,111) AS dt_henkou_ymd  ");
			strSql.append("         ,CONVERT(VARCHAR(8),t1.dt_henkou,108) AS dt_henkou_hmd  ");
			strSql.append("   	    ,m1.nm_user  ");
			strSql.append(" 	    ,c1.nm_category  ");
			strSql.append(" 	    ,l1.nm_��iteral  ");
			strSql.append("         ,T1.st_kenkyu  ");
			strSql.append("         ,T1.st_seisan  ");
			strSql.append("         ,T1.st_gensizai  ");
			strSql.append("         ,T1.st_kojo  ");
			strSql.append("         ,T1.st_eigyo  ");
			strSql.append("         ,T1.cd_zikko_li  ");
			strSql.append(" 		FROM  ");
			strSql.append(" 	     tr_shisan_status_rireki T1  ");
			strSql.append(" 	     left join ma_user as m1  ");
			strSql.append(" 			on t1.id_henkou = m1.id_user  ");
			strSql.append("  		 left join ma_category as c1  ");
			strSql.append(" 			on t1.cd_zikko_ca = c1.cd_category  ");
			strSql.append(" 		 left join ma_literal as l1  ");
			strSql.append(" 			on t1.cd_zikko_ca = l1.cd_category  ");
			strSql.append(" 			and t1.cd_zikko_li = l1.cd_literal  ");
			strSql.append(" 		WHERE  ");
			strSql.append(" 	     T1.cd_shain = " + strCdShain);
			strSql.append(" 	     AND T1.nen =  " + strNen);
			strSql.append(" 	     AND T1.no_oi = " + strNoOi);
			strSql.append(" 	     AND T1.no_eda = " + strNoEda);
			//strSql.append(" 	     AND T1.cd_zikko_li <> 0");  //���ۑ��ȊO //DEL �yH24�N�x�Ή��z 2012/04/16 ��
			strSql.append(" 		) AS CT ) AS cnttbl  ");
			strSql.append(" WHERE tbl.PageNO = " +strNoPage);
			strSql.append(" ORDER BY ");
			strSql.append(" tbl.cd_shain ");
			strSql.append(" ,tbl.nen ");
			strSql.append(" ,tbl.no_oi ");
			strSql.append(" ,tbl.no_eda ");
			strSql.append(" ,tbl.dt_henkou_ymd ");
			strSql.append(" ,tbl.dt_henkou_hmd ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData�i�p�����[�^�[�i�[�j
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �p�����[�^�[�i�[
	 *  : �X�e�[�^�X������ʂւ̃��X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenkaKihon(

			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//�R���X�g�t�@�C������f�[�^�擾
			String strListRowMax =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "no_row", toString(items[0],""));
				resTable.addFieldVale(i, "cd_shain", toString(items[1],""));
				resTable.addFieldVale(i, "nen", toString(items[2],""));
				resTable.addFieldVale(i, "no_oi", toString(items[3],""));
				resTable.addFieldVale(i, "no_eda", toString(items[4],""));
				resTable.addFieldVale(i, "dt_henkou_ymd", toString(items[5],""));
				resTable.addFieldVale(i, "dt_henkou_hms", toString(items[6],""));
				resTable.addFieldVale(i, "nm_henkou", toString(items[7],""));
				resTable.addFieldVale(i, "nm_zikko_ca", toString(items[8],""));
				resTable.addFieldVale(i, "nm_zikko_li", toString(items[9],""));
				resTable.addFieldVale(i, "st_kenkyu", toString(items[10],""));
				resTable.addFieldVale(i, "st_seisan", toString(items[11],""));
				resTable.addFieldVale(i, "st_gensizai", toString(items[12],""));
				resTable.addFieldVale(i, "st_kojo", toString(items[13],""));
				resTable.addFieldVale(i, "st_eigyo", toString(items[14],""));
				resTable.addFieldVale(i, "list_max_row", toString(strListRowMax,""));
				resTable.addFieldVale(i, "max_row", toString(items[15],""));
				resTable.addFieldVale(i, "cd_zikko_li", toString(items[16],"")); //ADD �yH24�N�x�Ή��z 2012/04/16 ��
				resTable.addFieldVale(i, "roop_cnt", Integer.toString(lstGenkaHeader.size()));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
