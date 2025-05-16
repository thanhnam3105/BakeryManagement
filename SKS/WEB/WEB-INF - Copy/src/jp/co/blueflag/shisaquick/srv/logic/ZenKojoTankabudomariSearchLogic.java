package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;
/**
 *
 * �S�H��P�������F�S�H��P��������񌟍�DB����
 *  : �S�H��P�������F�S�H��P������������������B
 * @author jinbo
 * @since  2009/05/19
 */
public class ZenKojoTankabudomariSearchLogic extends LogicBase{

	private int intRecCnt = 0;			    		//�H�ꌏ��

	/**
	 * �S�H��P�������F�S�H��P��������񌟍�DB����
	 * : �C���X�^���X����
	 */
	public ZenKojoTankabudomariSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();
	}

	private int flgSelectGenryoShizai = 0;	//�����E���ޑI���t���O

	/**
	 * ���[�U�[���b�Z�[�W �Q�b�^�[
	 * @return userMsg : ���[�U�[���b�Z�[�W�̒l��Ԃ�
	 */
	public int getflgSelect() {
		return flgSelectGenryoShizai;
	}
	/**
	 * ���[�U�[���b�Z�[�W �Z�b�^�[
	 * @param _userMsg : ���[�U�[���b�Z�[�W�̒l���i�[����
	 */
	public void setflgSelect(int _flgSelect) {
		flgSelectGenryoShizai = _flgSelect;
	}

	/**
	 * �S�H��P�������F�S�H��P���������擾SQL�쐬
	 *  : �S�H��P�����������擾����SQL���쐬�B
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

		RequestResponsKindBean resKind = null;

		try {
			String strGenryoKbn = null;

			//�Ώۃf�[�^�敪�̎擾
			strGenryoKbn = reqData.getFieldVale(0, 0, "kbn_data");

			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

// 20160930  KPX@1600766 DEL start
// 20160513  KPX@1600766 ADD start
			//���쌴���f�[�^�̉�Ђ̒P���J���������擾����i���e�����}�X�^�j
//			boolean blnKengen = getTankaHyouji_kengen(reqData, userInfoData);
// 20160513  KPX@1600766 ADD end
// 20160930  KPX@1600766 DEL end

			//SQL���̍쐬
			if (strGenryoKbn.equals("0")) {

				// 20160930  KPX@1600766 ADD start
				//���쌴���f�[�^�̉�Ђ̒P���J���������擾����i���e�����}�X�^�j
				boolean blnKengen = getGenryoTankaHyouji_kengen(reqData, userInfoData);
				// 20160930  KPX@1600766 ADD end

				setflgSelect(0);
				// 20160513  KPX@1600766 MOD start
//				strSql = genryoDataCreateSQL(reqData, strSql);
				strSql = genryoDataCreateSQL(reqData, strSql, blnKengen);
				// 20160513  KPX@1600766 MOD end
			} else {

				// 20160930  KPX@1600766 ADD Start
				//���쎑�ރf�[�^�̉�Ђ̒P���J���������擾����i���e�����}�X�^�j
				boolean blnKengen = getShizaiTankaHyouji_kengen(reqData, userInfoData);
				// 20160930  KPX@1600766 ADD end

				setflgSelect(1);
				// 20160810  KPX@1600766 MOD start
//				strSql = shizaiDataCreateSQL(reqData, strSql);
				strSql = shizaiDataCreateSQL(reqData, strSql, blnKengen);
				// 20160810  KPX@1600766 MOD end

			}

			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 1){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageGenryouData(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "�S�H��P�������f�[�^���������Ɏ��s���܂����B");
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

// 20160513  KPX@1600766 MOD start
	/**
	 * �S�H��P�������i�����j���擾SQL�쐬
	 *  : �S�H��P�������i�����j�����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @param kengen  �F�P���J�������itrue�F���@false�F�J���s�j
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
//	private StringBuffer genryoDataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql)
	private StringBuffer genryoDataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql, boolean kengen)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
// 20160513  KPX@1600766 MOD end

		StringBuffer strAllSql = new StringBuffer();

		try {
			String strKaishaCd = null;
			String strCd = null;
			String strName = null;
			String strTaishoKbn = null;
			String strPageNo = null;
			String strDataId = null;

			//�ő�\���s���̎擾
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			//��ЃR�[�h�̎擾
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//�R�[�h�̎擾
			strCd = reqData.getFieldVale(0, 0, "cd_genryo");
			//���O�̎擾
			strName = reqData.getFieldVale(0, 0, "nm_genryo");
			//�o�͍��ڂ̎擾
			strTaishoKbn = reqData.getFieldVale(0, 0, "item_output");
			//�I���y�[�WNo�̎擾
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("50")){
					//�f�[�^ID��ݒ�
					strDataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//�H�ꌏ���̎擾
			getKojoCount(strKaishaCd, strDataId);

			//SQL���̍쐬
			strAllSql.append("SELECT *");
			strAllSql.append(" FROM (");
			strAllSql.append(" SELECT");
			strAllSql.append("  MBT.no_row");
			strAllSql.append(" ,MBT.cd_genryo");
			strAllSql.append(" ,MBT.nm_genryo");
			for (int i = 1; i <= intRecCnt; i++) {
				strAllSql.append(" ,ISNULL(CONVERT(VARCHAR,MAX(CASE WHEN MBT.Rank = " + i + " THEN MBT.disp_val end)),'') as disp_val" + i);
			}
			strAllSql.append(" ,MBT.list_max_row");
			strAllSql.append(" ,MBT.max_row ");
			strAllSql.append(" ,MBT.cnt_kojo ");
			strAllSql.append(" FROM (");

			strAllSql.append(" SELECT");
			strAllSql.append("  0 as no_row");
			strAllSql.append(" ,'' as cd_genryo");
			strAllSql.append(" ,'' as nm_genryo");
			strAllSql.append(" ,MB.cd_kaisha");
		    strAllSql.append(" ,RIGHT('00' + CONVERT(VARCHAR,MB.cd_busho),2) + ':' + MB.nm_busho as disp_val");
			strAllSql.append(" ,0 as list_max_row");
			strAllSql.append(" ,0 as max_row ");
			strAllSql.append(" ," + intRecCnt + " as cnt_kojo ");
			strAllSql.append(" ,Row_Number() over(partition by MB.cd_kaisha order by MB.cd_kaisha) as Rank");
			strAllSql.append(" FROM ma_busho MB");
			strAllSql.append(" WHERE MB.cd_kaisha = ");
			strAllSql.append(strKaishaCd);

			//���H��̏ꍇ
			if (strDataId.equals("2")) {
				strAllSql.append(" AND MB.cd_busho = ");
				strAllSql.append(userInfoData.getCd_busho());
			}

			strAllSql.append(" ) as MBT");
			strAllSql.append(" GROUP BY MBT.no_row, MBT.cd_genryo ,MBT.nm_genryo, MBT.list_max_row, MBT.max_row, MBT.cnt_kojo");
			strAllSql.append(" UNION");

			strAllSql.append(" SELECT");
			strAllSql.append("  tbl3.no_row");
			strAllSql.append(" ,tbl3.cd_genryo");
			strAllSql.append(" ,tbl3.nm_genryo");
			for (int i = 1; i <= intRecCnt; i++) {
			    strAllSql.append(" ,CASE WHEN tbl3.disp_val" + i + " = '' THEN ISNULL(CONVERT(VARCHAR,tbl3.disp_val" + i + "),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(10,2),tbl3.disp_val" + i + ")) END as disp_val" + i);
			}
			strAllSql.append(" ,tbl3.list_max_row");
			strAllSql.append(" ,cnttbl.max_row ");
			strAllSql.append(" ," + intRecCnt + " as cnt_kojo ");
			strAllSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY tbl2.cd_genryo)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY tbl2.cd_genryo)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY tbl2.cd_genryo)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append(" ,tbl2.cd_genryo");
			strSql.append(" ,tbl2.nm_genryo");
			for (int i = 1; i <= intRecCnt; i++) {
			    strSql.append(" ,tbl2.disp_val" + i);
			}
			strSql.append(" ," + strListRowMax + " AS list_max_row");
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  tbl.cd_genryo");
			strSql.append(" ,tbl.nm_genryo");
			for (int i = 1; i <= intRecCnt; i++) {
			    strSql.append(" ,ISNULL(CONVERT(VARCHAR,MAX(CASE WHEN tbl.Rank = " + i + " THEN tbl.disp_val end)),'') as disp_val" + i);
			}
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  LIST.cd_busho");
//			strSql.append(" ,RIGHT('000000' + LIST.cd_genryo,6) as cd_genryo");
			strSql.append(" ,LIST.cd_genryo");
			strSql.append(" ,LIST.nm_genryo");
			strSql.append(" ,LIST.disp_val");
//�ۑ�Ǘ�222�̑Ή��@Isono�@2009/8/3
//			strSql.append(" ,Row_Number() over(partition by LIST.cd_genryo order by LIST.cd_genryo) as Rank");
			strSql.append(" ,Row_Number() over(partition by LIST.cd_genryo order by LIST.cd_genryo,LIST.cd_busho) as Rank");
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  M402.cd_busho");
			strSql.append(" ,M402.cd_genryo");
			strSql.append(" ,M402.nm_genryo");

// 20160513  KPX@1600766 MOD start
			//�P�����Ώۂ̏ꍇ
//			if (strTaishoKbn.equals("0")) {
//				strSql.append(" ,M402.tanka as disp_val");
			//�������Ώۂ̏ꍇ
//			} else {
//				strSql.append(" ,M402.budomari as disp_val");
//			}
			if (kengen == false) {
				//�P���E�������J���s�̏ꍇ�A�󔒂��Z�b�g
				strSql.append(" , '' as disp_val");
			} else if (strTaishoKbn.equals("0")) {
				//�P�����Ώۂ̏ꍇ
				strSql.append(" ,M402.tanka as disp_val");
			} else {
				//�������Ώۂ̏ꍇ
				strSql.append(" ,M402.budomari as disp_val");
			}
// 20160513  KPX@1600766 MOD end

			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append(" c.*, d.tanka, d.budomari");
			strSql.append(" FROM (");
			strSql.append(" SELECT DISTINCT");
			strSql.append(" a.cd_kaisha, a.cd_genryo, a.nm_genryo, b.cd_busho, b.nm_busho");
//			strSql.append(" FROM ma_genryokojo a, ma_busho b");

			//���H��̏ꍇ
			if (strDataId.equals("2")) {
				strSql.append(" FROM ma_genryokojo a");
			} else {
				strSql.append(" FROM (");
				strSql.append(" SELECT MG1.cd_kaisha, MG1.cd_genryo, MG1.cd_busho, MG2.nm_genryo");
				strSql.append(" FROM (");
				strSql.append(" SELECT cd_kaisha, cd_genryo, MIN(cd_busho) as cd_busho");
				strSql.append(" FROM ma_genryokojo");
				strSql.append(" GROUP BY cd_kaisha, cd_genryo");
				strSql.append(" ) as MG1");
				strSql.append(" INNER JOIN ma_genryokojo MG2");
				strSql.append(" ON MG1.cd_kaisha = MG2.cd_kaisha");
				strSql.append(" AND MG1.cd_busho = MG2.cd_busho");
				strSql.append(" AND MG1.cd_genryo = MG2.cd_genryo");
				strSql.append(" ) a");
			}

			strSql.append(" , ma_busho b");
			strSql.append(" WHERE a.cd_kaisha = ");
			strSql.append(strKaishaCd);

			//���H��̏ꍇ
			if (strDataId.equals("2")) {
				strSql.append(" AND a.cd_busho = ");
				strSql.append(userInfoData.getCd_busho());
			}

			strSql.append(" AND a.cd_kaisha = b.cd_kaisha");
			strSql.append(" ) as c");
			strSql.append(" LEFT JOIN ma_genryokojo d");
			strSql.append(" ON c.cd_kaisha = d.cd_kaisha ");
			strSql.append(" AND c.cd_busho = d.cd_busho");
			strSql.append(" AND c.cd_genryo = d.cd_genryo ");
			strSql.append(" ) as M402");
			strSql.append(" WHERE M402.cd_kaisha = ");
			strSql.append(strKaishaCd);

			//�����R�[�h�����͂���Ă���ꍇ
			if (!strCd.equals("")) {
//				strSql.append(" AND M402.cd_genryo = '");
//				strSql.append(strCd);
//				strSql.append("'");
				strSql.append(" AND M402.cd_genryo LIKE '%");
				strSql.append(strCd);
				strSql.append("%'");
			}

			//�����������͂���Ă���ꍇ
			if (!strName.equals("")) {
				strSql.append(" AND M402.nm_genryo LIKE '%");
				strSql.append(strName);
				strSql.append("%'");
			}

			strSql.append(" ) AS LIST");
			strSql.append(" ) AS tbl");
			strSql.append(" GROUP BY tbl.cd_genryo,tbl.nm_genryo");
			strSql.append(" ) AS tbl2");

			strAllSql.append(strSql);
			strAllSql.append(" ) AS tbl3");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");

			strAllSql.append(" WHERE tbl3.PageNO = ");
			strAllSql.append(strPageNo);
			strAllSql.append(" ) as T");
			strAllSql.append(" ORDER BY T.no_row");

			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "�S�H��P�������i�����j�f�[�^���������Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜
			strAllSql = null;
		}
		return strSql;
	}

	/**
	 * �S�H��P�������i���ށj���擾SQL�쐬
	 *  : �S�H��P�������i���ށj�����擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @param kengen�F�P���J�������itrue�F���@false�F�J���s�j
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer shizaiDataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql, Boolean kengen)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strAllSql = new StringBuffer();

		try {
			String strKaishaCd = null;
			String strCd = null;
			String strName = null;
			String strTaishoKbn = null;
			String strPageNo = null;
			String strDataId = null;

			//�ő�\���s���̎擾
			String strListRowMax = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX");

			//��ЃR�[�h�̎擾
			strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//�R�[�h�̎擾
			strCd = reqData.getFieldVale(0, 0, "cd_genryo");
			//���O�̎擾
			strName = reqData.getFieldVale(0, 0, "nm_genryo");
			//�o�͍��ڂ̎擾
			strTaishoKbn = reqData.getFieldVale(0, 0, "item_output");
			//�I���y�[�WNo�̎擾
			strPageNo = reqData.getFieldVale(0, 0, "no_page");

			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("50")){
					//�f�[�^ID��ݒ�
					strDataId = userInfoData.getId_data().get(i).toString();
				}
			}

			//�H�ꌏ���̎擾
			getKojoCount(strKaishaCd, strDataId);

			//SQL���̍쐬
			strAllSql.append("SELECT *");
			strAllSql.append(" FROM (");
			strAllSql.append(" SELECT");
			strAllSql.append("  MBT.no_row");
			strAllSql.append(" ,MBT.cd_shizai");
			strAllSql.append(" ,MBT.nm_shizai");
			for (int i = 1; i <= intRecCnt; i++) {
				strAllSql.append(" ,ISNULL(CONVERT(VARCHAR,MAX(CASE WHEN MBT.Rank = " + i + " THEN MBT.disp_val end)),'') as disp_val" + i);
			}
			strAllSql.append(" ,MBT.list_max_row");
			strAllSql.append(" ,MBT.max_row ");
			strAllSql.append(" ,MBT.cnt_kojo ");
			strAllSql.append(" FROM (");

			strAllSql.append(" SELECT");
			strAllSql.append("  0 as no_row");
			//2012/06/15 TT H.SHIMA MOD Start
//			strAllSql.append(" ,'' as cd_shizai");
			strAllSql.append(" ,0 as cd_shizai");
			//2012/06/15 TT H.SHIMA MOD End
			strAllSql.append(" ,'' as nm_shizai");
			strAllSql.append(" ,MB.cd_kaisha");
		    strAllSql.append(" ,RIGHT('00' + CONVERT(VARCHAR,MB.cd_busho),2) + ':' + MB.nm_busho as disp_val");
			strAllSql.append(" ,0 as list_max_row");
			strAllSql.append(" ,0 as max_row ");
			strAllSql.append(" ," + intRecCnt + " as cnt_kojo ");
			strAllSql.append(" ,Row_Number() over(partition by MB.cd_kaisha order by MB.cd_kaisha) as Rank");
			strAllSql.append(" FROM ma_busho MB");
			strAllSql.append(" WHERE MB.cd_kaisha = ");
			strAllSql.append(strKaishaCd);

			//���H��̏ꍇ
			if (strDataId.equals("2")) {
				strAllSql.append(" AND MB.cd_busho = ");
				strAllSql.append(userInfoData.getCd_busho());
			}

			strAllSql.append(" ) as MBT");
			strAllSql.append(" GROUP BY MBT.no_row, MBT.cd_shizai ,MBT.nm_shizai, MBT.list_max_row, MBT.max_row, MBT.cnt_kojo");
			strAllSql.append(" UNION");

			strAllSql.append(" SELECT");
			strAllSql.append("  tbl3.no_row");
			strAllSql.append(" ,tbl3.cd_shizai");
			strAllSql.append(" ,tbl3.nm_shizai");
			for (int i = 1; i <= intRecCnt; i++) {
			    strAllSql.append(" ,CASE WHEN tbl3.disp_val" + i + " = '' THEN ISNULL(CONVERT(VARCHAR,tbl3.disp_val" + i + "),'') ELSE CONVERT(VARCHAR,CONVERT(DECIMAL(10,2),tbl3.disp_val" + i + ")) END as disp_val" + i);
			}
			strAllSql.append(" ,tbl3.list_max_row");
			strAllSql.append(" ,cnttbl.max_row ");
			strAllSql.append(" ," + intRecCnt + " as cnt_kojo ");
			strAllSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("	(CASE WHEN ROW_NUMBER() OVER (ORDER BY tbl2.cd_shizai)%" + strListRowMax + " = 0 THEN " + strListRowMax + " ELSE ROW_NUMBER() OVER (ORDER BY tbl2.cd_shizai)%" + strListRowMax + " END) AS no_row ");
			strSql.append("	,Convert(int,(ROW_NUMBER() OVER (ORDER BY tbl2.cd_shizai)-1)/" + strListRowMax + " + 1) AS PageNO ");
			strSql.append(" ,tbl2.cd_shizai");
			strSql.append(" ,tbl2.nm_shizai");
			for (int i = 1; i <= intRecCnt; i++) {
			    strSql.append(" ,tbl2.disp_val" + i);
			}
			strSql.append(" ," + strListRowMax + " AS list_max_row");
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  tbl.cd_shizai");
			strSql.append(" ,tbl.nm_shizai");
			for (int i = 1; i <= intRecCnt; i++) {
			    strSql.append(" ,ISNULL(CONVERT(VARCHAR,MAX(CASE WHEN tbl.Rank = " + i + " THEN tbl.disp_val end)),'') as disp_val" + i);
			}
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  LIST.cd_busho");
			strSql.append(" ,LIST.cd_shizai");
			strSql.append(" ,LIST.nm_shizai");
			strSql.append(" ,LIST.disp_val");
//�ۑ�Ǘ�222�̑Ή��@Isono�@2009/8/3
//			strSql.append(" ,Row_Number() over(partition by LIST.cd_shizai order by LIST.cd_shizai) as Rank");
			strSql.append(" ,Row_Number() over(partition by LIST.cd_shizai order by LIST.cd_shizai,LIST.cd_busho) as Rank");
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append("  M501.cd_busho");
			strSql.append(" ,M501.cd_shizai");
			strSql.append(" ,M501.nm_shizai");
			// 20160810  KPX@1600766 MOD start
//			//�P�����Ώۂ̏ꍇ
//			if (strTaishoKbn.equals("0")) {
//				strSql.append(" ,M501.tanka as disp_val");
//			//�������Ώۂ̏ꍇ
//			} else {
//				strSql.append(" ,M501.budomari as disp_val");
//			}
			if (kengen == false) {
				//�P���E�������J���s�̏ꍇ�A�󔒂��Z�b�g
				strSql.append(" , '' as disp_val");
			} else if (strTaishoKbn.equals("0")) {
				//�P�����Ώۂ̏ꍇ
				strSql.append(" ,M501.tanka as disp_val");
			} else {
				//�������Ώۂ̏ꍇ
				strSql.append(" ,M501.budomari as disp_val");
			}
			// 20160810  KPX@1600766 MOD end
			strSql.append(" FROM (");

			strSql.append(" SELECT");
			strSql.append(" c.*, d.tanka, d.budomari");
			strSql.append(" FROM (");
			strSql.append(" SELECT DISTINCT");
			strSql.append(" a.cd_kaisha, a.cd_shizai, a.nm_shizai, b.cd_busho, b.nm_busho");
//			strSql.append(" FROM ma_shizai a, ma_busho b");

			//���H��̏ꍇ
			if (strDataId.equals("2")) {
				strSql.append(" FROM ma_shizai a");
			} else {
				strSql.append(" FROM (");
				strSql.append(" SELECT MG1.cd_kaisha, MG1.cd_shizai, MG1.cd_busho, MG2.nm_shizai");
				strSql.append(" FROM (");
				strSql.append(" SELECT cd_kaisha, cd_shizai, MIN(cd_busho) as cd_busho");
				strSql.append(" FROM ma_shizai");
				strSql.append(" GROUP BY cd_kaisha, cd_shizai");
				strSql.append(" ) as MG1");
				strSql.append(" INNER JOIN ma_shizai MG2");
				strSql.append(" ON MG1.cd_kaisha = MG2.cd_kaisha");
				strSql.append(" AND MG1.cd_busho = MG2.cd_busho");
				strSql.append(" AND MG1.cd_shizai = MG2.cd_shizai");
				strSql.append(" ) a");
			}

			strSql.append(" , ma_busho b");
			strSql.append(" WHERE a.cd_kaisha = ");
			strSql.append(strKaishaCd);

			//���H��̏ꍇ
			if (strDataId.equals("2")) {
				strSql.append(" AND a.cd_busho = ");
				strSql.append(userInfoData.getCd_busho());
			}

			strSql.append(" AND a.cd_kaisha = b.cd_kaisha");
			strSql.append(" ) as c");
			strSql.append(" LEFT JOIN ma_shizai d");
			strSql.append(" ON c.cd_kaisha = d.cd_kaisha ");
			strSql.append(" AND c.cd_busho = d.cd_busho");
			strSql.append(" AND c.cd_shizai = d.cd_shizai ");
			strSql.append(" ) as M501");
			strSql.append(" WHERE M501.cd_kaisha = ");
			strSql.append(strKaishaCd);

			//���ރR�[�h�����͂���Ă���ꍇ
			if (!strCd.equals("")) {
//				strSql.append(" AND M501.cd_shizai = '");
//				strSql.append(strCd);
//				strSql.append("'");
				strSql.append(" AND M501.cd_shizai LIKE '%");
				strSql.append(strCd);
				strSql.append("%'");
			}

			//���ޖ������͂���Ă���ꍇ
			if (!strName.equals("")) {
				strSql.append(" AND M501.nm_shizai LIKE '%");
				strSql.append(strName);
				strSql.append("%'");
			}

			strSql.append(" ) AS LIST");
			strSql.append(" ) AS tbl");
			strSql.append(" GROUP BY tbl.cd_shizai,tbl.nm_shizai");
			strSql.append(" ) AS tbl2");

			strAllSql.append(strSql);
			strAllSql.append(" ) AS tbl3");
			strAllSql.append(",(SELECT COUNT(*) as max_row FROM (" + strSql + ") AS CT ) AS cnttbl ");

			strAllSql.append(" WHERE tbl3.PageNO = ");
			strAllSql.append(strPageNo);
			strAllSql.append(" ) as T");
			strAllSql.append(" ORDER BY T.no_row");

			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "�S�H��P�������i���ށj�f�[�^���������Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜
			strAllSql = null;
		}
		return strSql;
	}

	/**
	 * �H�ꌏ���擾
	 *  : ��Ђɏ�������H��̌������擾����B
	 * @param strKaishaCd�F��ЃR�[�h
	 * @param strDataId�F�����f�[�^ID
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void getKojoCount(String strKaishaCd, String strDataId)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		try {
			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append(" cd_busho ");
			strSql.append(" FROM ma_busho");
			strSql.append(" WHERE cd_kaisha = ");
			strSql.append(strKaishaCd);

			//���H��̏ꍇ
			if (strDataId.equals("2")) {
				strSql.append(" AND cd_busho = ");
				strSql.append(userInfoData.getCd_busho());
			}

			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//���ʂ̑ޔ�
			intRecCnt = lstRecset.size();

		} catch (Exception e) {
			this.em.ThrowException(e, "�S�H��P�������F�H�ꌏ���擾�����Ɏ��s���܂����B");
		} finally {
			removeList(lstRecset);
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}
			//�ϐ��̍폜
			strSql = null;
		}
	}

	/**
	 * �S�H��P�������F�S�H��P���������p�����[�^�[�i�[
	 *  : �S�H��P�������������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenryouData : �������ʏ�񃊃X�g
	 * @param resTable : ���X�|���X�e�[�u�����
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenryouData(List<?> lstGenryouData, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstGenryouData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstGenryouData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "no_row", items[0].toString());
//MOD start 20120713 hisahori
//				resTable.addFieldVale(i, "cd_genryo", items[1].toString());
				if (getflgSelect() == 0){
					// �����̏ꍇ
					resTable.addFieldVale(i, "cd_genryo", items[1].toString());
				} else {
					// ���ނ̏ꍇ
// 20160513  KPX@1600766 MOD start
//					if (items[1].toString() == ""){
					if (items[1].toString().equals("")){
// 20160513  KPX@1600766 MOD start
						resTable.addFieldVale(i, "cd_genryo", "");
					} else { // 6���[������
						resTable.addFieldVale(i, "cd_genryo", String.format("%06d",Integer.parseInt(items[1].toString())));
					}
				}
//MOD end 20120713 hisahori
				resTable.addFieldVale(i, "nm_genryo", items[2].toString());
				for (int j = 1; j <= intRecCnt; j++) {
					resTable.addFieldVale(i, "disp_val" + j, items[j+2].toString());
				}
				resTable.addFieldVale(i, "list_max_row", items[intRecCnt+3].toString());
				resTable.addFieldVale(i, "max_row", items[intRecCnt+4].toString());
				resTable.addFieldVale(i, "cnt_kojo", items[intRecCnt+5].toString());

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�S�H��P�������f�[�^���������Ɏ��s���܂����B");
		} finally {

		}
	}

// 20160513  KPX@1600766 ADD start
	/**
	 * �����P���J�������擾
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return boolean  : �P���J������ �itrue�F�P���J������  false�F�P���J���s�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
// 20160930  KPX@1600766 MOD start
//	private boolean getTankaHyouji_kengen(RequestResponsKindBean reqData
	private boolean getGenryoTankaHyouji_kengen(RequestResponsKindBean reqData
			,UserInfoData _userInfoData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
// 20160930  KPX@1600766 MOD end

		//��������
		boolean ret = false;			//�P���J���s��

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���e�����}�X�^�����N���X
		LiteralDataSearchLogic clsLiteralSearch = null;
		//���e�����}�X�^���N�G�X�g
		RequestResponsKindBean reqLiteral = null;
		//���e�����}�X�^�������X�|���X
		RequestResponsKindBean resLiteral = null;

		String value1 = "";

		try {
			//���N�G�X�g�C���X�^���X
			reqLiteral = new RequestResponsKindBean();
			//���e�����}�X�^�������N�G�X�g����
			reqLiteral.addFieldVale("table", "rec", "cd_category", "K_tanka_hyoujigaisha");
			//��ЃR�[�h
			reqLiteral.addFieldVale("table", "rec", "cd_literal", reqData.getFieldVale(0, 0, "cd_kaisha"));

			//���e�����}�X�^�����F�P���J������
			clsLiteralSearch = new LiteralDataSearchLogic();
			resLiteral = clsLiteralSearch.ExecLogic(reqLiteral, userInfoData);

			if (resLiteral != null) {
				value1 = toString(resLiteral.getFieldVale(0, 0, "value1"));
			}

			if (value1.equals("1") || value1.equals("9")) {
				ret = true;		//�P���J������
			}

		} catch (Exception e) {
			//�Y���f�[�^�����̎��͊J���s��
			ret = false;

		} finally {

		}
		return ret;
	}
// 20160513  KPX@1600766 ADD end


// 20160930  KPX@1600766 ADD start
	/**
	 * ���ޒP���J�������擾
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return boolean  : �P���J������ �itrue�F�P���J������  false�F�P���J���s�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private boolean getShizaiTankaHyouji_kengen(RequestResponsKindBean reqData
			,UserInfoData _userInfoData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		boolean ret = false;			//�P���J���s��

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���e�����}�X�^�����N���X
		LiteralDataSearchLogic clsLiteralSearch = null;
		//���e�����}�X�^���N�G�X�g
		RequestResponsKindBean reqLiteral = null;
		//���e�����}�X�^�������X�|���X
		RequestResponsKindBean resLiteral = null;

		String value1 = "";

		try {
			//���N�G�X�g�C���X�^���X
			reqLiteral = new RequestResponsKindBean();
			//���e�����}�X�^�������N�G�X�g����
			reqLiteral.addFieldVale("table", "rec", "cd_category", "K_tanka_hyoujigaisha");
			//��ЃR�[�h
			reqLiteral.addFieldVale("table", "rec", "cd_literal", reqData.getFieldVale(0, 0, "cd_kaisha"));

			//���e�����}�X�^�����F�P���J������
			clsLiteralSearch = new LiteralDataSearchLogic();
			resLiteral = clsLiteralSearch.ExecLogic(reqLiteral, userInfoData);

			if (resLiteral != null) {
				value1 = toString(resLiteral.getFieldVale(0, 0, "value1"));
			}

			if (value1.equals("9")) {
				ret = true;		//�P���J������
			}

		} catch (Exception e) {
			//�Y���f�[�^�����̎��͊J���s��
			ret = false;

		} finally {

		}
		return ret;
	}
// 20160930  KPX@1600766 ADD end
}
