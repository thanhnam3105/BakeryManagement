package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * �S���Ҍ����i�c�Ɓj�F�S���ҁi�c�Ɓj��񌟍�DB����
 * @author Y.Nishigawa
 * @since  2011/01/28
 */
public class FGEN2110_Logic extends LogicBase{

	/**
	 * �S���Ҍ����F�S���ҏ�񌟍�DB����
	 * : �C���X�^���X����
	 */
	public FGEN2110_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �S���Ҍ����i�c�Ɓj�F�S���ҁi�c�Ɓj���擾SQL�쐬
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
		List<?> lstRecset = null;
		// ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 start
//		List<?> MemberlstRecset = null;
		// ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 end

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 start
			//SQL���̍쐬
			strSql = getTantouMemberLstCreateSQL(reqData, strSql);

			//�����o�[���X�g�擾SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			HashMap<String, ArrayList<String>> mapMember = setTantoushaAry(lstRecset);
			// ADD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 end

			//SQL���̍쐬
			// MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 start
//			strSql = genryoData2CreateSQL(reqData, strSql);
			strSql = getTantouLstCreateSQL(reqData, strSql);
			// MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 end

			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);


			//���X�|���X�f�[�^�̌`��
			// MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 start
//			storageTantoushaData(lstRecset, resKind.getTableItem(strTableNm));
			storageTantoushaData(lstRecset, mapMember, resKind.getTableItem(strTableNm));
			// MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 end




		} catch (Exception e) {
			this.em.ThrowException(e, "�S���ҁi�c�Ɓj�f�[�^���������Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
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
	 * �S���ҁi�c�Ɓj���擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	// MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 start
//	private StringBuffer genryoData2CreateSQL(RequestResponsKindBean reqData, StringBuffer strSql)
	private StringBuffer getTantouLstCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	// MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 end

		StringBuffer strAllSql = new StringBuffer();
		StringBuffer strWhere = new StringBuffer();

		try {
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			String strUserId = null;
			String strUserName = null;
			String strKaishaCd = null;
			String strBushoCd = null;
			String strPageNo = null;
			String strKbnShori = null;

			//�c�Ɓi��ʁj�����R�[�h�擾
			String strEigyoIppan =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_IPPAN");
			//�c�Ɓi�{�������j�����R�[�h�擾
			String strEigyoHonbu =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_HONBU");
			//�c�Ɓi�V�X�e���Ǘ��ҁj�����R�[�h�擾
			String strEigyoSystem =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_SYSTEM");
			//�c�Ɓi���o�^���[�U�j�����R�[�h�擾
			String strEigyoKari =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_KARI");
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
			String strEigyoPass =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_PASS");
			String strEigyoCash =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_CASH");
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 end

			//���[�UID�̎擾
			strUserId = toString(reqData.getFieldVale(0, 0, "id_user"));
			//���[�U���̎擾
			strUserName = toString(reqData.getFieldVale(0, 0, "nm_user"));
			//��ЃR�[�h�̎擾
			strKaishaCd = toString(reqData.getFieldVale(0, 0, "cd_kaisha"));
			//�����R�[�h�̎擾
			strBushoCd = toString(reqData.getFieldVale(0, 0, "cd_busho"));
			//�I���y�[�WNo�̎擾
			strPageNo = toString(reqData.getFieldVale(0, 0, "no_page"));
			//�����敪�̎擾
			strKbnShori = toString(reqData.getFieldVale(0, 0, "kbn_shori"));

			//�@�\ID�擾�p
			String strUKinoId = "";
			String strUDataId = "";

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("200")){
					//�@�\ID��ݒ�
					strUKinoId = userInfoData.getId_kino().get(i).toString();
					//�f�[�^ID��ݒ�
					strUDataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//���[�UID�����͂���Ă���ꍇ
			if (!strUserId.equals("")) {
				strWhere.append(" AND M101.id_user = '");
				strWhere.append(strUserId + "'");
			}
			//���[�U�������͂���Ă���ꍇ
			if (!strUserName.equals("")) {
				strWhere.append(" AND M101.nm_user LIKE '%");
				strWhere.append(strUserName + "%'");
			}
			//��Ђ����͂���Ă���ꍇ
			if (!strKaishaCd.equals("")) {
				strWhere.append(" AND M101.cd_kaisha = ");
				strWhere.append(strKaishaCd);
			}
			//���������͂���Ă���ꍇ
			if (!strBushoCd.equals("")) {
				strWhere.append("AND  M101.cd_busho = ");
				strWhere.append(strBushoCd);
			}

			//�S���Ҍ������[�h�@���@�������u�����̂݁v�̏ꍇ
			if(strUKinoId.equals("100") && strUDataId.equals("1") && strKbnShori.equals("1")){
				strWhere.append(" AND M101.id_user = ");
				strWhere.append(userInfoData.getId_user());
			}
			//�S���Ҍ������[�h�@���@�������u�S�Ẳc�ƒS���ҁi��ʂ̂݁j�v�̏ꍇ
			else if(strUKinoId.equals("101") && strUDataId.equals("1") && strKbnShori.equals("1")){
				strWhere.append(" AND M101.cd_kengen = ");
				strWhere.append(strEigyoIppan);
			}
			//�S���Ҍ������[�h�@���@�������u�S�Ẳc�ƒS���ҁi��ʁA�{�������j�v�̏ꍇ
			else if(strUKinoId.equals("102") && strUDataId.equals("1") && strKbnShori.equals("1")){
				strWhere.append(" AND ( M101.cd_kengen = ");
				strWhere.append(strEigyoIppan);
				strWhere.append(" OR M101.cd_kengen = ");
				strWhere.append(strEigyoHonbu + " )");
				// ADD 2013/11/7 QP@30154 okano start
				strWhere.append(" AND M101.cd_kaisha = ");
				strWhere.append(userInfoData.getCd_kaisha());
				// ADD 2013/11/7 QP@30154 okano end
			}
			//�S���Ҍ������[�h�@���@�������u�S�Ẳc�ƒS���ҁi��ʁA�{�������A�V�X�e���Ǘ��ҁj�v�̏ꍇ
			else if(strUKinoId.equals("103") && strUDataId.equals("1") && strKbnShori.equals("1")){
				strWhere.append(" AND ( M101.cd_kengen = ");
				strWhere.append(strEigyoIppan);
				strWhere.append(" OR M101.cd_kengen = ");
				strWhere.append(strEigyoHonbu);
				strWhere.append(" OR M101.cd_kengen = ");
				strWhere.append(strEigyoSystem + " )");
			}

			//SQL���̍쐬
			strAllSql.append(" SELECT ");
			strAllSql.append("  tbl.id_user ");
			strAllSql.append(" ,tbl.nm_user ");
			strAllSql.append(" ,tbl.cd_kengen ");
			strAllSql.append(" ,ISNULL(tbl.nm_kengen ");
			strAllSql.append(" 	,'') as nm_kengen ");
			strAllSql.append(" ,tbl.cd_kaisha ");
			strAllSql.append(" ,ISNULL(tbl.nm_kaisha ");
			strAllSql.append(" 	,'') as nm_kaisha ");
			strAllSql.append(" ,tbl.cd_busho ");
			strAllSql.append(" ,ISNULL(tbl.nm_busho ");
			strAllSql.append(" 	,'') as nm_busho ");
			strAllSql.append("	," + strListRowMax + " AS list_max_row");
			strAllSql.append(" ,cnttbl.max_row ");
			strAllSql.append(" ,tbl.id_josi ");
			strAllSql.append(" ,tbl.nm_josi ");
			strAllSql.append(" FROM ( ");
			strAllSql.append(" 	SELECT ");
			strAllSql.append(" 	 datatbl.PageNO ");
			strAllSql.append(" 	,datatbl.id_user ");
			strAllSql.append(" 	,datatbl.nm_user ");
			strAllSql.append(" 	,datatbl.cd_kengen ");
			strAllSql.append(" 	,datatbl.nm_kengen ");
			strAllSql.append(" 	,datatbl.cd_kaisha ");
			strAllSql.append(" 	,datatbl.nm_kaisha ");
			strAllSql.append(" 	,datatbl.cd_busho ");
			strAllSql.append(" 	,datatbl.nm_busho ");
			strAllSql.append(" 	,datatbl.id_josi ");
			strAllSql.append(" 	,datatbl.nm_josi ");
			strAllSql.append(" 	FROM ( ");
			strAllSql.append(" 		SELECT ");
			strAllSql.append(" 		 Convert(int ");
			strAllSql.append(" 			,(ROW_NUMBER() OVER ( ");
			strAllSql.append(" 					ORDER BY M101.id_user)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strAllSql.append(" 		,RIGHT('0000000000' + CONVERT(varchar ");
			strAllSql.append(" 				,M101.id_user) ");
			strAllSql.append(" 			,10) as id_user ");
			strAllSql.append(" 		,M101.nm_user ");
			strAllSql.append(" 		,M101.cd_kengen ");
			strAllSql.append(" 		,M102.nm_kengen ");
			strAllSql.append(" 		,M101.cd_kaisha ");
			strAllSql.append(" 		,M1041.nm_kaisha ");
			strAllSql.append(" 		,M101.cd_busho ");
			strAllSql.append(" 		,M1042.nm_busho ");
			strAllSql.append(" 		,M101.id_josi ");
			strAllSql.append(" 		,M101_2.nm_user AS nm_josi ");
			strAllSql.append(" 		FROM ma_user M101 ");
			strAllSql.append(" 		LEFT JOIN ( ");
			strAllSql.append(" 			SELECT ");
			strAllSql.append(" 			 cd_kaisha ");
			strAllSql.append(" 			, nm_kaisha ");
			strAllSql.append(" 			FROM ma_busho ");
			strAllSql.append(" 			GROUP BY cd_kaisha ");
			strAllSql.append(" 			, nm_kaisha) M1041 ");
			strAllSql.append(" 		ON M1041.cd_kaisha = M101.cd_kaisha ");
			strAllSql.append(" 		LEFT JOIN ma_busho M1042 ");
			strAllSql.append(" 		ON M1042.cd_kaisha = M101.cd_kaisha ");
			strAllSql.append(" 		AND M1042.cd_busho = M101.cd_busho ");
			strAllSql.append(" 		LEFT JOIN ma_kengen M102 ");
			strAllSql.append(" 		ON M101.cd_kengen = M102.cd_kengen ");
			strAllSql.append(" 		LEFT JOIN ma_user M101_2 ");
			strAllSql.append(" 		ON M101_2.id_user = M101.id_josi ");
			strAllSql.append(" 		WHERE M1042.flg_eigyo = 1 ");
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoKari);
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoPass);
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoCash);
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
			strAllSql.append(strWhere);
			strAllSql.append(" 	  ) AS datatbl ");
			strAllSql.append(" 	) AS tbl ");
			strAllSql.append(" ,( ");
			strAllSql.append(" 	SELECT ");
			strAllSql.append(" 	 COUNT(*) as max_row ");
			strAllSql.append(" 	FROM ( ");
			strAllSql.append(" 		SELECT ");
			strAllSql.append(" 		 Convert(int ");
			strAllSql.append(" 			,(ROW_NUMBER() OVER ( ");
			strAllSql.append(" 					ORDER BY M101.id_user)-1)/" + strListRowMax + "  + 1) AS PageNO ");
			strAllSql.append(" 		,RIGHT('0000000000' + CONVERT(varchar ");
			strAllSql.append(" 				,M101.id_user) ");
			strAllSql.append(" 			,10) as id_user ");
			strAllSql.append(" 		,M101.nm_user ");
			strAllSql.append(" 		,M101.cd_kengen ");
			strAllSql.append(" 		,M102.nm_kengen ");
			strAllSql.append(" 		,M101.cd_kaisha ");
			strAllSql.append(" 		,M1041.nm_kaisha ");
			strAllSql.append(" 		,M101.cd_busho ");
			strAllSql.append(" 		,M1042.nm_busho ");
			strAllSql.append(" 		,M101.id_josi ");
			strAllSql.append(" 		,M101_2.nm_user AS nm_josi ");
			strAllSql.append(" 		FROM ma_user M101 ");
			strAllSql.append(" 		LEFT JOIN ( ");
			strAllSql.append(" 			SELECT ");
			strAllSql.append(" 			 cd_kaisha ");
			strAllSql.append(" 			, nm_kaisha ");
			strAllSql.append(" 			FROM ma_busho ");
			strAllSql.append(" 			GROUP BY cd_kaisha ");
			strAllSql.append(" 			, nm_kaisha) M1041 ");
			strAllSql.append(" 		ON M1041.cd_kaisha = M101.cd_kaisha ");
			strAllSql.append(" 		LEFT JOIN ma_busho M1042 ");
			strAllSql.append(" 		ON M1042.cd_kaisha = M101.cd_kaisha ");
			strAllSql.append(" 		AND M1042.cd_busho = M101.cd_busho ");
			strAllSql.append(" 		LEFT JOIN ma_kengen M102 ");
			strAllSql.append(" 		ON M101.cd_kengen = M102.cd_kengen ");
			strAllSql.append(" 		LEFT JOIN ma_user M101_2 ");
			strAllSql.append(" 		ON M101_2.id_user = M101.id_josi ");
			strAllSql.append(" 		WHERE M1042.flg_eigyo = 1 ");
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoKari);
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 start
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoPass);
			strAllSql.append(" 			AND M101.cd_kengen <> " + strEigyoCash);
			// ADD 2013/9/25 okano�yQP@30151�zNo.28 end
			strAllSql.append(strWhere);
			strAllSql.append(" 	) AS CT ) AS cnttbl ");
			strAllSql.append("	WHERE tbl.PageNO = " + strPageNo);
			strAllSql.append(" ORDER BY tbl.id_user ");

			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���ҁi�c�Ɓj�f�[�^���������Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜
			strAllSql = null;
			strWhere = null;
		}
		return strSql;
	}

	/**
	 * �S���ҁi�c�Ɓj�����F�S���ҏ��p�����[�^�[�i�[
	 * @param lstTantouShaData : �������ʏ�񃊃X�g
	 * @param MapMember : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	// MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 start
//	private void storageTantoushaData(List<?> lstRecData, RequestResponsTableBean resTable)
	private void storageTantoushaData(List<?> lstRecData, HashMap<String, ArrayList<String>> MapMember, RequestResponsTableBean resTable)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		// MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 start

		ArrayList<String> lstMember = null;
		String strMember = "";

		try {
			//�c�Ɓi�{�������j�����R�[�h�擾
			String strEigyoHonbu =
				ConstManager.getConstValue(ConstManager.Category.�ݒ�,"EIGYO_KENGEN_HONBU");

			for (int i = 0; i < lstRecData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstRecData.get(i);

				resTable.addFieldVale(i, "no_row", Integer.toString(i+1));
				resTable.addFieldVale(i, "id_user", toString(items[0]));
				resTable.addFieldVale(i, "nm_user", toString(items[1]));
				resTable.addFieldVale(i, "cd_kaisha", toString(items[4]));
				resTable.addFieldVale(i, "nm_kaisha", toString(items[5]));
				resTable.addFieldVale(i, "cd_busho", toString(items[6]));
				resTable.addFieldVale(i, "nm_busho", toString(items[7]));
				if(strEigyoHonbu.equals(toString(items[2]))){
					resTable.addFieldVale(i, "kbn_honbu", "����");
				}
				else{
					resTable.addFieldVale(i, "kbn_honbu", "�Ȃ�");
				}
				// MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 start
//				resTable.addFieldVale(i, "id_josi", toString(items[10]));
//				resTable.addFieldVale(i, "nm_josi", toString(items[11]));

				lstMember = MapMember.get(toString(items[0]));
				strMember = "";
				if (lstMember != null) {
					for (Iterator<String> it = lstMember.iterator(); it.hasNext();) {
						// ���L�����o�[���J���}�ŋ�؂�
						if (strMember != "") {
							strMember += ", ";
						}
						strMember += it.next();
					}
				}
			    resTable.addFieldVale(i, "nm_member", strMember);
				// MOD 2015/05/15 TT.Kitazawa�yQP@40812�zNo.19 end

			    resTable.addFieldVale(i, "list_max_row", toString(items[8]));
				resTable.addFieldVale(i, "max_row", toString(items[9]));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���ҁi�c�Ɓj�f�[�^���������Ɏ��s���܂����B");

		} finally {

		}

	}


	/**
	 * �S���ҁi�c�Ɓj���擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer getTantouMemberLstCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strAllSql = new StringBuffer();

		try {

			//SQL���̍쐬
			strAllSql.append(" SELECT ");
			strAllSql.append("  RIGHT('0000000000' + CONVERT(varchar ");
			strAllSql.append("  	,MEMBER.id_user) ");
			strAllSql.append("  	,10) as id_user ");
			strAllSql.append(" ,id_member ");
			strAllSql.append(" ,nm_user ");
			strAllSql.append(" FROM ma_member as MEMBER ");
			strAllSql.append(" 		LEFT JOIN  ma_user as M101  ");
			strAllSql.append(" 		ON MEMBER.id_member = M101.id_user ");
			strAllSql.append(" ORDER BY MEMBER.id_user ");
			strAllSql.append(" ,no_sort ");

			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "�S���ҁi�c�Ɓj�f�[�^���������Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜
			strAllSql = null;
		}
		return strSql;
	}

	/**
	 * �S���ҁi�c�Ɓj�����F�S���ҋ��L�����o�[�����[�U�[����MAP�Ɋi�[
	 * @param lstTantouShaData : �������ʏ�񃊃X�g
	 * @return HashMap         �F���[�U���̋��L�����o�[���X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private HashMap<String, ArrayList<String>> setTantoushaAry(List<?> lstRecData) {

		// ��r�p���[�U�R�[�h
		String svUser = "";

		// �߂�l�̐錾
		HashMap<String, ArrayList<String>> mapMember = new HashMap<String, ArrayList<String>>();
		// ���[�U�̋��L�����o�[���X�g
		ArrayList<String>  strAry = new ArrayList<String>();

		for (int i = 0; i < lstRecData.size(); i++) {
			Object[] items = (Object[]) lstRecData.get(i);

			if (svUser.equals(toString(items[0]))) {
				// ���L�����o�[�̉��Z
				strAry.add(toString(items[2]));
			} else {
				// ���[�U���ς������MAP�ɕۑ�����
				if (!svUser.equals("")) {
					mapMember.put(svUser, strAry);
				}
				// ���L�����o�[���X�g�̒u������
				svUser = toString(items[0]);
				strAry = new ArrayList<String>();
				strAry.add(toString(items[2]));
			}
		}

		// �Ō�̋��L�����o�[���X�g��ۑ�
		if (strAry.size() > 0) {
			mapMember.put(svUser, strAry);
		}

		// �i�[����MAP��Ԃ�
		return mapMember;

	}


}
