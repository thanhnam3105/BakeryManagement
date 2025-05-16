package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 *
 * JWS-試作テーブル検索ＤＢ処理の実装
 *  : JWS-試作テーブル検索処理のＤＢに対する業務ロジックの実装
 *
 * @author TT.k-katayama
 * @since  2009/03/30
 */
public class ShisakuTblSearchLogic extends LogicBase {

	//検索条件用パラメータ
	private String strShainCd;						//試作CD-社員CD
	private String strNen;							//試作CD-年
	private String strTuiban;						//試作CD-追番

	/**
	 * コンストラクタ
	 *  : 試作テーブル検索用コンストラクタ
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	public ShisakuTblSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 試作テーブル検索ロジック管理
	 * @param reqData : リクエストデータ
	 * @param RequestResponsBean：レスポンスデータ
	 * @param userInfoData : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		this.userInfoData = _userInfoData;

		this.strShainCd = "";
		this.strNen = "";
		this.strTuiban = "";

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//属性値の取得 : SA480
			this.strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			this.strNen = reqData.getFieldVale(0, 0, "nen");
			this.strTuiban = reqData.getFieldVale(0, 0, "no_oi");

			// 機能IDの設定
			resKind.setID(reqData.getID());

			//①：試作品データ パラメーター格納を呼出す。
			this.shisakuhinDataSearch(resKind);

			//②：配合データ パラメーター格納を呼出す。
			this.haigoDataSearch(resKind);

			//③：試作データ パラメーター格納を呼出す。
			this.shisakuDataSearch(resKind);

			//④：試作リストデータ パラメーター格納を呼出す。
			this.shisakuListDataSearch(resKind);

			//⑤：製造工程データ パラメーター格納を呼出す。
			this.seizoKouteiDataSearch(resKind);

			//⑥：原価資材データ パラメーター格納を呼出す。
			this.genkaShizaiDataSearch(resKind);

			//⑦：原価原料データ パラメーター格納を呼出す。
			this.genkaGenryoDataSearch(resKind);

		} catch (Exception e) {
			this.em.ThrowException(e, "試作テーブル検索処理が失敗しました。");

		} finally {
			//開放処理
			this.userInfoData = null;
			this.strShainCd = null;
			this.strNen = null;
			this.strTuiban = null;

		}
		return resKind;

	}

	/**
	 * 試作品データ取得SQL作成
	 * @return 検索用SQL文(StringBuffer)
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private StringBuffer shisakuhinDataCreateSQL() throws Exception {
		StringBuffer strRetSQL = new StringBuffer();

		//検索用SQLの記述
		strRetSQL.append(" SELECT ");
		strRetSQL.append(" T110.cd_shain AS cd_shain  ");
		strRetSQL.append(" ,T110.nen AS nen  ");
		strRetSQL.append(" ,T110.no_oi AS no_oi  ");

		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.no_irai),'') AS no_irai  ");
		strRetSQL.append(" ,ISNULL(T110.nm_hin,'') AS nm_hin  ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.cd_kaisha),'') AS cd_kaisha  ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.cd_kojo),'') AS cd_kojo  ");
		strRetSQL.append(" ,ISNULL(T110.cd_shubetu,'') AS cd_shubetu  ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.no_shubetu),'') AS no_shubetu  ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.cd_group),'') AS cd_group  ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.cd_team),'') AS cd_team  ");

		strRetSQL.append(" ,ISNULL(T110.cd_ikatu,'') AS cd_ikatu  ");
		strRetSQL.append(" ,ISNULL(T110.cd_genre,'') AS cd_genre  ");
		strRetSQL.append(" ,ISNULL(T110.cd_user,'') AS cd_user  ");
		strRetSQL.append(" ,ISNULL(T110.tokuchogenryo,'') AS tokuchogenryo  ");
		strRetSQL.append(" ,ISNULL(T110.youto,'') AS youto  ");
		strRetSQL.append(" ,ISNULL(T110.cd_kakaku,'') AS cd_kakaku  ");

		//【QP@00342】
		strRetSQL.append(" ,ISNULL(T110.cd_eigyo,NULL) AS cd_eigyo  ");

		strRetSQL.append(" ,ISNULL(T110.cd_hoho,'') AS cd_hoho  ");
		strRetSQL.append(" ,ISNULL(T110.cd_juten,'') AS cd_juten  ");
		strRetSQL.append(" ,ISNULL(T110.hoho_sakin,'') AS hoho_sakin  ");
		strRetSQL.append(" ,ISNULL(T110.youki,'') AS youki  ");
		strRetSQL.append(" ,ISNULL(T110.yoryo,'') AS yoryo  ");
		strRetSQL.append(" ,ISNULL(T110.cd_tani,'') AS cd_tani  ");
		strRetSQL.append(" ,ISNULL(T110.su_iri,'') AS su_iri  ");
		strRetSQL.append(" ,ISNULL(T110.cd_ondo,'') AS cd_ondo  ");
		strRetSQL.append(" ,ISNULL(T110.shomikikan,'') AS shomikikan  ");
		strRetSQL.append(" ,ISNULL(T110.genka,'') AS genka  ");
		strRetSQL.append(" ,ISNULL(T110.baika,'') AS baika  ");
		strRetSQL.append(" ,ISNULL(T110.buturyo,'') AS buturyo  ");
		strRetSQL.append(" ,ISNULL(T110.dt_hatubai,'') AS dt_hatubai  ");
		strRetSQL.append(" ,ISNULL(T110.uriage_k,'') AS uriage_k  ");
		strRetSQL.append(" ,ISNULL(T110.rieki_k,'') AS rieki_k  ");
		strRetSQL.append(" ,ISNULL(T110.uriage_h,'') AS uriage_h  ");
		strRetSQL.append(" ,ISNULL(T110.rieki_h,'') AS rieki_h  ");
		strRetSQL.append(" ,ISNULL(T110.cd_nisugata,'') AS cd_nisugata  ");
		strRetSQL.append(" ,ISNULL(T110.memo,'') AS memo  ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.keta_shosu),'') AS keta_shosu  ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.kbn_haishi),'') AS kbn_haishi  ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.kbn_haita),'') AS kbn_haita  ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.seq_shisaku),'') AS seq_shisaku  ");

		strRetSQL.append(" ,T110.id_toroku AS id_toroku  ");
		strRetSQL.append(" ,CONVERT(VARCHAR,T110.dt_toroku,111) AS dt_toroku  ");
		strRetSQL.append(" ,T110.id_koshin AS id_koshin  ");

		//UPD 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため年月日字分秒を返却
//		strRetSQL.append(" ,CONVERT(VARCHAR,T110.dt_koshin,111) AS dt_koshin  ");
		strRetSQL.append(" ,CONVERT(VARCHAR,T110.dt_koshin,20) AS dt_koshin  ");

		// グループ名, チーム名
		strRetSQL.append(" ,ISNULL(M105.nm_group,'') AS nm_group, ISNULL(M106.nm_team,'') AS nm_team ");
		// 登録者名, 更新者名
		strRetSQL.append(" ,ISNULL(M101_t.nm_user,'') AS nm_toroku, ISNULL(M101_u.nm_user,'') AS nm_koshin ");
		//試作メモ
		strRetSQL.append(" ,ISNULL(T110.memo_shisaku,'') AS memo_shisaku  ");
		//注意事項表示
		strRetSQL.append(" ,ISNULL(T110.flg_chui,'') AS flg_chui  ");


//【シサクイック（原価）要望】排他制御　TT.Nishigawa 2010/5/11 START -----------------------------------
		strRetSQL.append(" ,M101_haita.nm_user AS nm_user_haita  ");
		strRetSQL.append(" ,M104_haita.nm_kaisha AS nm_kaisha_haita  ");
		strRetSQL.append(" ,M104_haita.nm_busho AS nm_busho_haita  ");
//【シサクイック（原価）要望】排他制御　TT.Nishigawa 2010/5/11 END -------------------------------------

		//【QP@00342】
		strRetSQL.append(" ,M101_e.nm_user AS nm_eigyo ");

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
		strRetSQL.append(" ,ISNULL(T110.pt_kotei,'') AS pt_kotei ");
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
		
		//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
		strRetSQL.append(" ,T110.flg_secret ");
		//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
		// ADD 2013/10/22 QP@30154 okano start
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T110.cd_hanseki),'') AS cd_hanseki  ");
		// ADD 2013/10/22 QP@30154 okano end
		
		//試作品テーブル
		strRetSQL.append(" FROM tr_shisakuhin AS T110 ");
		//グループマスタ
		strRetSQL.append(" LEFT JOIN ma_group M105 ");
		strRetSQL.append("   ON M105.cd_group = T110.cd_group ");
		//チームマスタ
		strRetSQL.append(" LEFT JOIN ma_team M106 ");
		strRetSQL.append("   ON M106.cd_team = T110.cd_team ");
		strRetSQL.append("   AND M106.cd_group = T110.cd_group ");
		//ユーザマスタ
		strRetSQL.append(" LEFT JOIN ma_user M101_t ");
		strRetSQL.append("   ON M101_t.id_user = T110.id_toroku ");
		strRetSQL.append(" LEFT JOIN ma_user M101_u ");
		strRetSQL.append("   ON M101_u.id_user = T110.id_koshin ");

//【シサクイック（原価）要望】排他制御　TT.Nishigawa 2010/5/11 START -----------------------------------
		strRetSQL.append(" LEFT JOIN ma_user M101_haita ");
		strRetSQL.append("   ON M101_haita.id_user = T110.kbn_haita ");
		strRetSQL.append(" LEFT JOIN ma_busho M104_haita ");
		strRetSQL.append("   ON M101_haita.cd_kaisha = M104_haita.cd_kaisha ");
		strRetSQL.append("   AND M101_haita.cd_busho = M104_haita.cd_busho ");
//【シサクイック（原価）要望】排他制御　TT.Nishigawa 2010/5/11 END -------------------------------------

		//【QP@00342】
		strRetSQL.append(" LEFT JOIN ma_user M101_e ");
		strRetSQL.append(" ON M101_e.id_user = T110.cd_eigyo ");

		//検索条件
		strRetSQL.append(" WHERE T110.cd_shain=" + this.strShainCd);
		strRetSQL.append(" AND T110.nen=" + this.strNen);
		strRetSQL.append(" AND T110.no_oi=" + this.strTuiban);

		return strRetSQL;

	}
	/**
	 * 試作品データ パラメーター格納
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void shisakuhinDataSearch(RequestResponsKindBean resKind)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		List<?> lstRecset = null;							//レコード値格納リスト
		StringBuffer strSqlBuf = null;						//検索SQL文格納

		try {
			String strTblNm = "tr_shisakuhin";				//テーブル名

			//①：リクエストデータより、試作品テーブル検索条件を抽出し、試作品テーブルデータ情報を取得するSQLを作成する。
			strSqlBuf = this.shisakuhinDataCreateSQL();

			//②：共通クラス　データベース検索を用い、試作品テーブルデータを取得する。
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//③：試作テーブルデータパラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			resKind.addTableItem(strTblNm);
			this.storageShisakuhinData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "試作品データ検索処理が失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;

			}
			//変数の削除
			strSqlBuf = null;

		}

	}

	/**
	 * 配合データ取得SQL作成
	 * @return 検索用SQL文(StringBuffer)
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private StringBuffer haigoDataCreateSQL() throws Exception {

		StringBuffer strRetSQL = new StringBuffer();

		//検索用SQLの記述
		strRetSQL.append(" SELECT ");
		strRetSQL.append("  T120.cd_shain AS cd_shain ");
		strRetSQL.append(" ,T120.nen AS nen ");
		strRetSQL.append(" ,T120.no_oi AS no_oi ");
		strRetSQL.append(" ,T120.cd_kotei AS cd_kotei ");
		strRetSQL.append(" ,T120.seq_kotei AS seq_kotei ");

		strRetSQL.append(" ,ISNULL(T120.nm_kotei,'') AS nm_kotei ");
		strRetSQL.append(" ,ISNULL(T120.zoku_kotei,'') AS zoku_kotei ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T120.sort_kotei),'') AS sort_kotei ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T120.sort_genryo),'') AS sort_genryo ");
		strRetSQL.append(" ,ISNULL(T120.cd_genryo,'') AS cd_genryo ");
		strRetSQL.append(" ,ISNULL(T120.cd_kaisha,'') AS cd_kaisha ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T120.cd_busho),'') AS cd_busho ");
		strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR(60),T120.nm_genryo),'') AS nm_genryo ");

		//単価
		strRetSQL.append(" ,CASE IsNull(CAST(T120.tanka AS varchar),'') WHEN ''");
		strRetSQL.append(" 	THEN CAST('' AS varchar)");
		strRetSQL.append(" 	ELSE LTRIM(STR(T120.tanka,12,2))");
		strRetSQL.append(" END AS tanka");

		//歩留
		strRetSQL.append(" ,CASE IsNull(CAST(T120.budomari AS varchar),'') WHEN ''");
		strRetSQL.append(" 	THEN CAST('' AS varchar)");
		strRetSQL.append(" 	ELSE LTRIM(STR(T120.budomari,6,2))");
		strRetSQL.append(" END AS budomari");

		//油含有率
		strRetSQL.append(" ,CASE IsNull(CAST(T120.ritu_abura AS varchar),'') WHEN ''");
		strRetSQL.append(" 	THEN CAST('' AS varchar)");
		strRetSQL.append(" 	ELSE LTRIM(STR(T120.ritu_abura,6,2))");
		strRetSQL.append(" END AS ritu_abura");

		//酢酸
		strRetSQL.append(" ,CASE IsNull(CAST(T120.ritu_sakusan AS varchar),'') WHEN ''");
		strRetSQL.append(" 	THEN CAST('' AS varchar)");
		strRetSQL.append(" 	ELSE LTRIM(STR(T120.ritu_sakusan,6,2))");
		strRetSQL.append(" END AS ritu_sakusan");

		//食塩
		strRetSQL.append(" ,CASE IsNull(CAST(T120.ritu_shokuen AS varchar),'') WHEN ''");
		strRetSQL.append(" 	THEN CAST('' AS varchar)");
		strRetSQL.append(" 	ELSE LTRIM(STR(T120.ritu_shokuen,6,2))");
		strRetSQL.append(" END AS ritu_shokuen");

		//総酸
		strRetSQL.append(" ,CASE IsNull(CAST(T120.ritu_sousan AS varchar),'') WHEN ''");
		strRetSQL.append(" 	THEN CAST('' AS varchar)");
		strRetSQL.append(" 	ELSE LTRIM(STR(T120.ritu_sousan,6,2))");
		strRetSQL.append(" END AS ritu_sousan");

		strRetSQL.append(" ,ISNULL(T120.color,'') AS color ");

		strRetSQL.append(" ,T120.id_toroku AS id_toroku ");
		strRetSQL.append(" ,CONVERT(VARCHAR,T120.dt_toroku,111) AS dt_toroku ");
		strRetSQL.append(" ,T120.id_koshin AS id_koshin ");
		strRetSQL.append(" ,CONVERT(VARCHAR,T120.dt_koshin,111) AS dt_koshin ");

		//登録者名, 更新者名
		strRetSQL.append(" ,ISNULL(M101_t.nm_user,'') AS nm_toroku ");
		strRetSQL.append(" ,ISNULL(M101_u.nm_user,'') AS nm_koshin ");

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
		//マスタ歩留値
		strRetSQL.append(" ,genryokojo.budomari AS ma_budomari ");
//add end   -------------------------------------------------------------------------------
// ADD start 20121002 QP@20505 No.24
		//ＭＳＧ（グルタミン酸Ｎａ）
		strRetSQL.append(" ,CASE IsNull(CAST(T120.ritu_msg AS varchar),'') WHEN ''");
		strRetSQL.append(" 	THEN CAST('' AS varchar)");
		strRetSQL.append(" 	ELSE LTRIM(STR(T120.ritu_msg,6,2))");
		strRetSQL.append(" END AS ritu_msg");
// ADD end 20121002 QP@20505 No.24

		//配合テーブル
		strRetSQL.append(" FROM tr_haigo AS T120 ");

		//ユーザマスタ
		strRetSQL.append("  LEFT JOIN ma_user M101_t ");
		strRetSQL.append("   ON M101_t.id_user = T120.id_toroku ");
		strRetSQL.append("  LEFT JOIN ma_user M101_u ");
		strRetSQL.append("   ON M101_u.id_user = T120.id_koshin ");

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
		//原料工場マスタ
		strRetSQL.append(" LEFT JOIN ma_genryokojo AS genryokojo ");
		strRetSQL.append(" ON T120.cd_kaisha = genryokojo.cd_kaisha ");
		strRetSQL.append(" AND T120.cd_busho = genryokojo.cd_busho ");
		strRetSQL.append(" AND T120.cd_genryo = genryokojo.cd_genryo ");
//add end   -------------------------------------------------------------------------------

		//検索条件
		strRetSQL.append(" WHERE T120.cd_shain=" + this.strShainCd);
		strRetSQL.append("  AND T120.nen=" + this.strNen);
		strRetSQL.append("  AND T120.no_oi=" + this.strTuiban);

		//表示順
		strRetSQL.append(" ORDER BY T120.sort_kotei , T120.sort_genryo ");

		return strRetSQL;

	}
	/**
	 * 配合データ パラメーター格納
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void haigoDataSearch(RequestResponsKindBean resKind)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		List<?> lstRecset = null;							//レコード値格納リスト
		StringBuffer strSqlBuf = null;						//検索SQL文格納

		try {
			String strTblNm = "tr_haigo";					//テーブル名

			//①：リクエストデータより、配合テーブル検索条件を抽出し、配合テーブルデータ情報を取得するSQLを作成する。
			strSqlBuf = this.haigoDataCreateSQL();

			//②：共通クラス　データベース検索を用い、配合テーブルデータを取得する。
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//③：試作テーブルデータパラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			resKind.addTableItem(strTblNm);
			this.storageHaigoData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "配合データ検索処理が失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;

			}

			//変数の削除
			strSqlBuf = null;

		}

	}

	/**
	 * 試作データ取得SQL作成
	 * @return 検索用SQL文(StringBuffer)
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private StringBuffer shisakuDataCreateSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strRetSQL = null;

		try {
			strRetSQL = new StringBuffer();

			//検索用SQLの記述
			strRetSQL.append(" SELECT  ");
			strRetSQL.append("   T131.cd_shain AS cd_shain  ");
			strRetSQL.append("  ,T131.nen AS nen  ");
			strRetSQL.append("  ,T131.no_oi AS no_oi  ");

			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.seq_shisaku),'') AS seq_shisaku  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.sort_shisaku),'') AS sort_shisaku  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.no_chui),'') AS no_chui  ");

			strRetSQL.append("  ,ISNULL(T131.nm_sample,'') AS nm_sample  ");
			strRetSQL.append("  ,ISNULL(T131.memo,'') AS memo  ");
			//strRetSQL.append("  ,ISNULL(T131.memo_shisaku,'') AS memo_shisaku  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_print),'') AS flg_print  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_auto),'') AS flg_auto  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.no_shisan),'') AS no_shisan  ");
			strRetSQL.append("  ,ISNULL(T131.no_seiho1,'') AS no_seiho1  ");
			strRetSQL.append("  ,ISNULL(T131.no_seiho2,'') AS no_seiho2  ");
			strRetSQL.append("  ,ISNULL(T131.no_seiho3,'') AS no_seiho3  ");
			strRetSQL.append("  ,ISNULL(T131.no_seiho4,'') AS no_seiho4  ");
			strRetSQL.append("  ,ISNULL(T131.no_seiho5,'') AS no_seiho5  ");

			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.ritu_sousan),'') AS ritu_sousan  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_sousan),'') AS flg_sousan  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.ritu_shokuen),'') AS ritu_shokuen  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_shokuen),'') AS flg_shokuen  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.sando_suiso),'') AS sando_suiso  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_sando_suiso),'') AS flg_sando_suiso  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.shokuen_suiso),'') AS shokuen_suiso  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_shokuen_suiso),'') AS flg_shokuen_suiso  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.sakusan_suiso),'') AS sakusan_suiso  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_sakusan_suiso),'') AS flg_sakusan_suiso  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.toudo),'') AS toudo  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_toudo),'') AS flg_toudo  ");
			strRetSQL.append("  ,ISNULL(T131.nendo,'') AS nendo  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_nendo),'') AS flg_nendo  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.ondo),'') AS ondo  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_ondo),'') AS flg_ondo  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.ph),'') AS ph  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_ph),'') AS flg_ph  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.ritu_sousan_bunseki),'') AS ritu_sousan_bunseki  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_sousan_bunseki),'') AS flg_sousan_bunseki  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.ritu_shokuen_bunseki),'') AS ritu_shokuen_bunseki  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_shokuen_bunseki),'') AS flg_shokuen_bunseki  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.hiju),'') AS hiju  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_hiju),'') AS flg_hiju  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.suibun_kasei),'') AS suibun_kasei  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_suibun_kasei),'') AS flg_suibun_kasei  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.alcohol),'') AS alcohol  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_alcohol),'') AS flg_alcohol  ");
			strRetSQL.append("  ,ISNULL(T131.memo_sakusei,'') AS memo_sakusei  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_memo),'') AS  flg_memo  ");
			strRetSQL.append("  ,ISNULL(T131.hyoka,'') AS hyoka  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_hyoka),'') AS flg_hyoka  ");
			strRetSQL.append("  ,ISNULL(T131.free_title1,'') AS free_title1  ");
			strRetSQL.append("  ,ISNULL(T131.free_value1,'') AS free_value1  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_free1),'') AS  flg_free1  ");
			strRetSQL.append("  ,ISNULL(T131.free_title2,'') AS free_title2  ");
			strRetSQL.append("  ,ISNULL(T131.free_value2,'') AS free_value2  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_free2),'') AS  flg_free2  ");
			strRetSQL.append("  ,ISNULL(T131.free_title3,'') AS free_title3  ");
			strRetSQL.append("  ,ISNULL(T131.free_value3,'') AS free_value3  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_free3),'') AS  flg_free3  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.dt_shisaku,111),'') AS dt_shisaku  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.juryo_shiagari_g),'') AS juryo_shiagari_g  ");
			strRetSQL.append("  ,T131.id_toroku AS id_toroku  ");
			strRetSQL.append("  ,CONVERT(VARCHAR,T131.dt_toroku,111) AS dt_toroku  ");
			strRetSQL.append("  ,T131.id_koshin AS id_koshin  ");
			strRetSQL.append("  ,CONVERT(VARCHAR,T131.dt_koshin,111) AS dt_koshin  ");
			// 登録者名, 更新者名
			strRetSQL.append(" ,ISNULL(M101_t.nm_user,'') AS nm_toroku, ISNULL(M101_u.nm_user,'') AS nm_koshin ");
//2009/10/25 TT.A.ISONO ADD START [原価試算：試算依頼flg取得]
			// 試算依頼flg
			strRetSQL.append(" ,ISNULL(T131.flg_shisanIrai,'') AS flg_shisanIrai ");
//2009/10/25 TT.A.ISONO ADD END   [原価試算：試算依頼flg取得]
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
			strRetSQL.append(" ,ISNULL(T131.siki_keisan,'') AS siki_keisan ");
//add end   ---------------------------------------------------------------------------
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			strRetSQL.append(" ,T131.hiju_sui AS hiju_sui ");
			strRetSQL.append(" ,T131.flg_hiju_sui AS flg_hiju_sui ");
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20120927 QP@20505 No.24
			strRetSQL.append("        ,ISNULL(T131.freetitle_nendo,'') AS freetitle_nendo  ");
			strRetSQL.append("        ,ISNULL(T131.free_nendo,'') AS free_nendo  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.flg_freeNendo),'') AS flg_freeNendo  ");
			strRetSQL.append("        ,ISNULL(T131.freetitle_ondo,'') AS freetitle_ondo  ");
			strRetSQL.append("        ,ISNULL(T131.free_ondo,'') AS free_ondo  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.flg_freeOndo),'') AS flg_freeOndo  ");
			strRetSQL.append("        ,ISNULL(T131.freetitle_suibun_kasei,'') AS freetitle_suibun_kasei  ");
			strRetSQL.append("        ,ISNULL(T131.free_suibun_kasei,'') AS free_suibun_kasei  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.flg_freeSuibunKasei),'') AS flg_freeSuibunKasei  ");
			strRetSQL.append("        ,ISNULL(T131.freetitle_alcohol,'') AS freetitle_alcohol  ");
			strRetSQL.append("        ,ISNULL(T131.free_alcohol,'') AS free_alcohol  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.flg_freeAlchol),'') AS flg_freeAlchol  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.jikkoSakusanNodo),'') AS jikkoSakusanNodo  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.flg_jikkoSakusanNodo),'') AS flg_jikkoSakusanNodo  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.msg_suiso),'') AS msg_suiso  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.flg_msg_suiso),'') AS flg_msg_suiso  ");
			strRetSQL.append("        ,ISNULL(T131.free_title4,'') AS free_title4  ");
			strRetSQL.append("        ,ISNULL(T131.free_value4,'') AS free_value4  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.flg_free4),'') AS flg_free4  ");
			strRetSQL.append("        ,ISNULL(T131.free_title5,'') AS free_title5  ");
			strRetSQL.append("        ,ISNULL(T131.free_value5,'') AS free_value5  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.flg_free5),'') AS flg_free5  ");
			strRetSQL.append("        ,ISNULL(T131.free_title6,'') AS free_title6  ");
			strRetSQL.append("        ,ISNULL(T131.free_value6,'') AS free_value6  ");
			strRetSQL.append("        ,ISNULL(CONVERT(VARCHAR,T131.flg_free6),'') AS flg_free6  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.ritu_msg),'') AS ritu_msg  ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,T131.flg_msg),'') AS flg_msg  ");
// ADD end 20120927 QP@20505 No.24
			//試作データテーブル
						strRetSQL.append(" FROM tr_shisaku AS T131 ");

			//ユーザマスタ
			strRetSQL.append(" LEFT JOIN ma_user M101_t ");
			strRetSQL.append("   ON M101_t.id_user = T131.id_toroku ");
			strRetSQL.append(" LEFT JOIN ma_user M101_u ");
			strRetSQL.append("   ON M101_u.id_user = T131.id_koshin ");

			//検索条件
			strRetSQL.append(" WHERE T131.cd_shain=" + this.strShainCd);
			strRetSQL.append(" AND T131.nen=" + this.strNen);
			strRetSQL.append(" AND T131.no_oi=" + this.strTuiban);

			//表示順
			strRetSQL.append("ORDER BY ISNULL(T131.sort_shisaku,0)  ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		return strRetSQL;

	}
	/**
	 * 試作データ パラメーター格納
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void shisakuDataSearch(RequestResponsKindBean resKind)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		List<?> lstRecset = null;							//レコード値格納リスト
		StringBuffer strSqlBuf = null;						//検索SQL文格納

		try {
			String strTblNm = "tr_shisaku";				//テーブル名

			//①：リクエストデータより、試作テーブル検索条件を抽出し、試作テーブルデータ情報を取得するSQLを作成する。
			strSqlBuf = this.shisakuDataCreateSQL();

			//②：共通クラス　データベース検索を用い、試作テーブルデータを取得する。
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//③：試作テーブルデータパラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			resKind.addTableItem(strTblNm);
			this.storageShisakuData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ検索処理が失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;

			}
			//変数の削除
			strSqlBuf = null;

		}

	}

	/**
	 * 試作リストデータ取得SQL作成
	 * @return 検索用SQL文(StringBuffer)
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private StringBuffer shisakuListDataCreateSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strRetSQL = null;

		try {
			strRetSQL = new StringBuffer();

			//検索用SQLの記述
			strRetSQL.append(" SELECT ");
			strRetSQL.append("  T131.cd_shain AS cd_shain ");
			strRetSQL.append(" ,T131.nen AS nen ");
			strRetSQL.append(" ,T131.no_oi AS no_oi ");

			strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T131.seq_shisaku),'') AS seq_shisaku ");
			strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T132.cd_kotei),'') AS cd_kotei ");
			strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T132.seq_kotei),'') AS seq_kotei ");

			//量 (試作品テーブル.小数桁数で桁数判定)
			strRetSQL.append(" ,CASE IsNull(CAST(T132.quantity AS varchar),'') WHEN ''");
			strRetSQL.append(" 	THEN CAST('' AS varchar)");
			strRetSQL.append(" 	ELSE ");
			strRetSQL.append(" 		LTRIM(STR(T132.quantity,");
			strRetSQL.append(" 				  10,");

			strRetSQL.append("		CAST((select	value1");
			strRetSQL.append("		from	ma_literal");
			strRetSQL.append("		where	cd_category = 'K_shosu'");
			strRetSQL.append("		AND cd_literal =(SELECT t110.keta_shosu");
			strRetSQL.append("						 FROM tr_shisakuhin AS t110");
			strRetSQL.append("						 WHERE t110.cd_shain = " + this.strShainCd);
			strRetSQL.append("						 AND t110.nen = " + this.strNen);
			 strRetSQL.append("						 AND t110.no_oi = " + this.strTuiban + ")");
			 strRetSQL.append("		) AS int ) ))");



//			strRetSQL.append(" 				  CAST((SELECT t110.keta_shosu");
//			strRetSQL.append(" 				  		FROM tr_shisakuhin AS t110");
//			strRetSQL.append(" 				  		WHERE t110.cd_shain = " + this.strShainCd);
//			strRetSQL.append(" 				  		AND t110.nen = " + this.strNen);
//			strRetSQL.append(" 				  		AND t110.no_oi = " + this.strTuiban + ") AS int");
//			strRetSQL.append(" 			  )");
//			strRetSQL.append(" 		))");



			strRetSQL.append(" END AS quantity");

			strRetSQL.append(" ,ISNULL(T132.color,'') AS color ");

			strRetSQL.append(" ,T132.id_toroku AS id_toroku ");
			strRetSQL.append(" ,CONVERT(VARCHAR,T132.dt_toroku,111) AS dt_toroku ");
			strRetSQL.append(" ,T132.id_koshin AS id_koshin ");
			strRetSQL.append(" ,CONVERT(VARCHAR,T132.dt_koshin,111) AS dt_koshin ");

			strRetSQL.append(" ,ISNULL(M101_t.nm_user,'') AS nm_toroku ");
			strRetSQL.append(" ,ISNULL(M101_u.nm_user,'') AS nm_koshin ");

// ADD start 20120914 QP@20505 No.1
			//工程仕上重量
			strRetSQL.append(" ,CASE IsNull(CAST(T132.juryo_shiagari_seq AS varchar),'') WHEN ''");
			strRetSQL.append(" 	THEN CAST('' AS varchar)");
			strRetSQL.append(" 	ELSE ");
			strRetSQL.append(" 		LTRIM(STR(T132.juryo_shiagari_seq,");
			strRetSQL.append(" 				  10,");
			strRetSQL.append("		4 ))");
			strRetSQL.append(" END AS juryo_shiagari_seq");
// ADD end 20120914 QP@20505 No.1

			//試作テーブル
			strRetSQL.append(" FROM tr_shisaku AS T131 ");
			//配合テーブル
			strRetSQL.append(" LEFT JOIN tr_haigo AS T120 ");
			strRetSQL.append(" ON T120.cd_shain = T131.cd_shain ");
			strRetSQL.append(" AND T120.nen = T131.nen ");
			strRetSQL.append(" AND T120.no_oi = T131.no_oi ");
			//試作リストテーブル
			strRetSQL.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			strRetSQL.append(" ON T132.cd_shain = T131.cd_shain ");
			strRetSQL.append(" AND T132.nen = T131.nen ");
			strRetSQL.append(" AND T132.no_oi = T131.no_oi ");
			strRetSQL.append(" AND T132.seq_shisaku = T131.seq_shisaku ");
			strRetSQL.append(" AND T132.cd_kotei = T120.cd_kotei ");
			strRetSQL.append(" AND T132.seq_kotei = T120.seq_kotei ");
			//ユーザマスタ
			strRetSQL.append(" LEFT JOIN ma_user M101_t ");
			strRetSQL.append(" ON M101_t.id_user = T132.id_toroku ");
			strRetSQL.append(" LEFT JOIN ma_user M101_u ");
			strRetSQL.append(" ON M101_u.id_user = T132.id_koshin ");

			//検索条件
			strRetSQL.append(" WHERE T131.cd_shain=" + this.strShainCd);
			strRetSQL.append(" AND T131.nen=" + this.strNen);
			strRetSQL.append(" AND T131.no_oi=" + this.strTuiban);

			//表示順
			strRetSQL.append(" ORDER BY  ");
			strRetSQL.append("   ISNULL(T131.sort_shisaku,0) ");
			strRetSQL.append("  ,ISNULL(T120.sort_kotei,0) ");
			strRetSQL.append("  ,ISNULL(T120.sort_genryo,0) ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		return strRetSQL;

	}
	/**
	 * 試作リストデータ パラメーター格納
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void shisakuListDataSearch(RequestResponsKindBean resKind)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		List<?> lstRecset = null;						//レコード値格納リスト
		StringBuffer strSqlBuf = null;					//検索SQL文格納

		try {
			String strTblNm = "tr_shisaku_list";		//テーブル名

			//①：リクエストデータより、試作リストテーブル検索条件を抽出し、試作リストテーブルデータ情報を取得するSQLを作成する。
			strSqlBuf = this.shisakuListDataCreateSQL();

			//②：共通クラス　データベース検索を用い、試作リストテーブルデータを取得する。
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//③：試作リストテーブルデータパラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			resKind.addTableItem(strTblNm);
			this.storageShisakuListData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "試作リストデータ検索処理が失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;

			}
			//変数の削除
			strSqlBuf = null;

		}

	}

	/**
	 * 製造工程データ取得SQL作成
	 * @return 検索用SQL文(StringBuffer)
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private StringBuffer seizoKouteiDataCreateSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strRetSQL = null;

		try {
			strRetSQL = new StringBuffer();

			//検索用SQLの記述
			strRetSQL.append(" SELECT ");
			strRetSQL.append(" T133.cd_shain AS cd_shain ");
			strRetSQL.append(" ,T133.nen AS nen ");
			strRetSQL.append(" ,T133.no_oi AS no_oi ");

			//strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T133.seq_shisaku),'') AS seq_shisaku ");
			strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T133.no_chui),'') AS no_chui ");
			strRetSQL.append(" ,ISNULL(T133.chuijiko,'') AS chuijiko ");

			strRetSQL.append(" ,T133.id_toroku AS id_toroku ");
			strRetSQL.append(" ,CONVERT(VARCHAR,T133.dt_toroku,111) AS dt_toroku ");
			strRetSQL.append(" ,T133.id_koshin AS id_koshin ");
			strRetSQL.append(" ,CONVERT(VARCHAR,T133.dt_koshin,111) AS dt_koshin ");

			// 登録者名, 更新者名
			strRetSQL.append(" ,ISNULL(M101_t.nm_user,'') AS nm_toroku, ISNULL(M101_u.nm_user,'') AS nm_koshin ");

			//試作リストデータテーブル
			strRetSQL.append(" FROM tr_cyuui AS T133 ");
			//ユーザマスタ
			strRetSQL.append(" LEFT JOIN ma_user M101_t ");
			strRetSQL.append("   ON M101_t.id_user = T133.id_toroku ");
			strRetSQL.append(" LEFT JOIN ma_user M101_u ");
			strRetSQL.append("   ON M101_u.id_user = T133.id_koshin ");

			//検索条件
			strRetSQL.append(" WHERE T133.cd_shain=" + this.strShainCd);
			strRetSQL.append(" AND T133.nen=" + this.strNen);
			strRetSQL.append(" AND T133.no_oi=" + this.strTuiban);

			//表示順
			strRetSQL.append(" ORDER BY ISNULL(T133.no_chui,0) ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

		return strRetSQL;
	}
	/**
	 * 製造工程データ パラメーター格納
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void seizoKouteiDataSearch(RequestResponsKindBean resKind)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		List<?> lstRecset = null;						//レコード値格納リスト
		StringBuffer strSqlBuf = null;					//検索SQL文格納

		try {
			String strTblNm = "tr_cyuui";				//テーブル名

			//①：リクエストデータより、製造工程テーブル検索条件を抽出し、製造工程テーブルデータ情報を取得するSQLを作成する。
			strSqlBuf = this.seizoKouteiDataCreateSQL();

			//②：共通クラス　データベース検索を用い、製造工程テーブルデータを取得する。
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//③：製造工程テーブルデータパラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			resKind.addTableItem(strTblNm);
			this.storageKouteiData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "製造工程データ検索処理が失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションの解放
				this.searchDB.Close();
				searchDB = null;

			}

			//変数の削除
			strSqlBuf = null;

		}

	}

	/**
	 * 原価資材データ取得SQL作成
	 * @return 検索用SQL文(StringBuffer)
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private StringBuffer genkaShizaiDataCreateSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strRetSQL = null;

		try {
			strRetSQL = new StringBuffer();

			//検索用SQLの記述
			strRetSQL.append(" SELECT ");
			strRetSQL.append(" T140.cd_shain AS cd_shain ");
			strRetSQL.append(" ,T140.nen AS nen ");
			strRetSQL.append(" ,T140.no_oi AS no_oi ");

			strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T140.seq_shizai),'') AS seq_shizai ");
			strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T140.no_sort),'') AS no_sort ");
			strRetSQL.append(" ,ISNULL(T140.cd_shizai,'') AS cd_shizai ");
			strRetSQL.append(" ,ISNULL(T140.nm_shizai,'') AS nm_shizai ");
			strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T140.tanka),'') AS tanka ");
			strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T140.budomari),'') AS budomari ");

			strRetSQL.append(" ,T140.id_toroku AS id_toroku ");
			strRetSQL.append(" ,CONVERT(VARCHAR,T140.dt_toroku,111) AS dt_toroku ");
			strRetSQL.append(" ,T140.id_koshin AS id_koshin ");
			strRetSQL.append(" ,CONVERT(VARCHAR,T140.dt_koshin,111) AS dt_koshin ");

			// 登録者名, 更新者名
			strRetSQL.append(" ,ISNULL(M101_t.nm_user,'') AS nm_toroku, ISNULL(M101_u.nm_user,'') AS nm_koshin ");

			//試作リストデータテーブル
			strRetSQL.append(" FROM tr_shizai AS T140 ");
			//ユーザマスタ
			strRetSQL.append(" LEFT JOIN ma_user M101_t ");
			strRetSQL.append("   ON M101_t.id_user = T140.id_toroku ");
			strRetSQL.append(" LEFT JOIN ma_user M101_u ");
			strRetSQL.append("   ON M101_u.id_user = T140.id_koshin ");

			//検索条件
			strRetSQL.append(" WHERE T140.cd_shain=" + this.strShainCd);
			strRetSQL.append(" AND T140.nen=" + this.strNen);
			strRetSQL.append(" AND T140.no_oi=" + this.strTuiban);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		return strRetSQL;

	}

	/**
	 * 原価資材データ パラメーター格納
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void genkaShizaiDataSearch(RequestResponsKindBean resKind)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSqlBuf = null;					//検索SQL文格納
		String strTblNm = "tr_shizai";					//テーブル名
		List<?> lstRecset = null;						//レコード値格納リスト

		try {

			//①：リクエストデータより、原価資材テーブル検索条件を抽出し、原価資材テーブルデータ情報を取得するSQLを作成する。
			strSqlBuf = this.genkaShizaiDataCreateSQL();

			//②：共通クラス　データベース検索を用い、原価資材テーブルデータを取得する。
//			super.createSearchDB();
//			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			lstRecset = new java.util.ArrayList();

			//③：原価資材テーブルデータパラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			resKind.addTableItem(strTblNm);
			this.storageShizaiData(lstRecset, resKind.getTableItem(strTblNm));

			//セッションの解放
//			this.searchDB.Close();

		} catch (Exception e) {
			this.em.ThrowException(e, "原価資材データ検索処理が失敗しました。");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			//変数の削除
			strTblNm = null;
			strSqlBuf = null;
		}

	}

	/**
	 * 原価原料データ取得SQL作成
	 * @return 検索用SQL文(StringBuffer)
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/06/10
	 */
	private StringBuffer genkaGenryoDataCreateSQL()
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strRetSQL = null;

		try {
			strRetSQL = new StringBuffer();

			//検索用SQLの記述
			strRetSQL.append(" SELECT ");
			strRetSQL.append("  T141.cd_shain AS cd_shain ");
			strRetSQL.append(" ,T141.nen AS nen ");
			strRetSQL.append(" ,T141.no_oi AS no_oi ");
			strRetSQL.append(" ,T141.seq_shisaku AS seq_shisaku ");
			strRetSQL.append(" ,ISNULL(CONVERT(VARCHAR,T141.flg_print),'') AS flg_print ");
			strRetSQL.append(" ,ISNULL(T141.zyusui,'') AS zyusui ");
			strRetSQL.append(" ,ISNULL(T141.zyuabura,'') AS zyuabura ");
			strRetSQL.append(" ,ISNULL(T141.gokei,'') AS gokei ");
			strRetSQL.append(" ,ISNULL(T141.genryohi,'') AS genryohi ");
			strRetSQL.append(" ,ISNULL(T141.genryohi1,'') AS genryohi1 ");
			strRetSQL.append(" ,ISNULL(T141.hiju,'') AS hiju ");
			strRetSQL.append(" ,ISNULL(T141.yoryo,'') AS yoryo ");
			strRetSQL.append(" ,ISNULL(T141.irisu,'') AS irisu ");
			strRetSQL.append(" ,ISNULL(T141.yukobudomari,'') AS yukobudomari ");
			strRetSQL.append(" ,ISNULL(T141.reberu,'') AS reberu ");
			strRetSQL.append(" ,ISNULL(T141.hizyubudomari,'') AS hizyubudomari ");
			strRetSQL.append(" ,ISNULL(T141.heikinzyu,'') AS heikinzyu ");
			strRetSQL.append(" ,ISNULL(T141.cs_genryo,'') AS cs_genryo ");
			strRetSQL.append(" ,ISNULL(T141.cs_zairyohi,'') AS cs_zairyohi ");
			strRetSQL.append(" ,ISNULL(T141.cs_keihi,'') AS cs_keihi ");
			strRetSQL.append(" ,ISNULL(T141.cs_genka,'') AS cs_genka ");
			strRetSQL.append(" ,ISNULL(T141.ko_genka,'') AS ko_genka ");
			strRetSQL.append(" ,ISNULL(T141.ko_baika,'') AS ko_baika ");
			strRetSQL.append(" ,ISNULL(T141.ko_riritu,'') AS ko_riritu ");
			strRetSQL.append(" ,T141.id_toroku AS id_toroku");
			strRetSQL.append(" ,CONVERT(VARCHAR,T141.dt_toroku,111) AS dt_toroku");
			strRetSQL.append(" ,T141.id_koshin AS id_koshin");
			strRetSQL.append(" ,CONVERT(VARCHAR,T141.dt_koshin,111) AS dt_koshin");
			// 登録者名, 更新者名
			strRetSQL.append(" ,ISNULL(M101_t.nm_user,'') AS nm_toroku, ISNULL(M101_u.nm_user,'') AS nm_koshin ");

			//原価原料テーブル
			strRetSQL.append(" FROM tr_genryo AS T141 ");
			//ユーザマスタ(登録者・更新者)
			strRetSQL.append(" LEFT JOIN ma_user M101_t ");
			strRetSQL.append("   ON M101_t.id_user = T141.id_toroku ");
			strRetSQL.append(" LEFT JOIN ma_user M101_u ");
			strRetSQL.append("   ON M101_u.id_user = T141.id_koshin ");

			//検索条件
			strRetSQL.append(" WHERE T141.cd_shain=" + this.strShainCd);
			strRetSQL.append(" AND T141.nen=" + this.strNen);
			strRetSQL.append(" AND T141.no_oi=" + this.strTuiban);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		return strRetSQL;

	}
	/**
	 * 原価原料データ パラメーター格納
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.k-katayama
	 * @since  2009/06/10
	 */
	private void genkaGenryoDataSearch(RequestResponsKindBean resKind)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSqlBuf = null;			//検索SQL文格納
		String strTblNm = "tr_genryo";			//テーブル名
		List<?> lstRecset = null;					//レコード値格納リスト

		try {

			//①：リクエストデータより、原価原料テーブル検索条件を抽出し、原価原料テーブルデータ情報を取得するSQLを作成する。
			strSqlBuf = this.genkaGenryoDataCreateSQL();

			//②：共通クラス　データベース検索を用い、原価原料テーブルデータを取得する。
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//③：原価原料テーブルデータパラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			resKind.addTableItem(strTblNm);
			this.storageGenryoData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "原価原料データ検索処理が失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);

			//変数の削除
			strTblNm = null;
			strSqlBuf = null;

		}

	}

	/**
	 * 試作品テーブルデータ パラメーター格納
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @param strTableNm : 対象テーブル
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void storageShisakuhinData(List<?> lstTantouShaData, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			for (int i = 0; i < lstTantouShaData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");

				Object[] items = (Object[]) lstTantouShaData.get(i);

				//検索結果を格納
				resTable.addFieldVale("rec" + i, "cd_shain", items[0].toString());
				resTable.addFieldVale("rec" + i, "nen", items[1].toString());
				resTable.addFieldVale("rec" + i, "no_oi", items[2].toString());
				resTable.addFieldVale("rec" + i, "no_irai", items[3].toString());
				resTable.addFieldVale("rec" + i, "nm_hin", items[4].toString());
				resTable.addFieldVale("rec" + i, "cd_kaisha", items[5].toString());
				resTable.addFieldVale("rec" + i, "cd_kojo", items[6].toString());
				resTable.addFieldVale("rec" + i, "cd_shubetu", items[7].toString());
				resTable.addFieldVale("rec" + i, "no_shubetu", items[8].toString());
				resTable.addFieldVale("rec" + i, "cd_group", items[9].toString());
				resTable.addFieldVale("rec" + i, "cd_team", items[10].toString());
				resTable.addFieldVale("rec" + i, "cd_ikatu", items[11].toString());
				resTable.addFieldVale("rec" + i, "cd_genre", items[12].toString());
				resTable.addFieldVale("rec" + i, "cd_user", items[13].toString());
				resTable.addFieldVale("rec" + i, "tokuchogenryo", items[14].toString());
				resTable.addFieldVale("rec" + i, "youto", items[15].toString());
				resTable.addFieldVale("rec" + i, "cd_kakaku", items[16].toString());

				//【QP@00342】
				resTable.addFieldVale("rec" + i, "cd_eigyo", toString(items[17]));

				resTable.addFieldVale("rec" + i, "cd_hoho", items[18].toString());
				resTable.addFieldVale("rec" + i, "cd_juten", items[19].toString());
				resTable.addFieldVale("rec" + i, "hoho_sakin", items[20].toString());
				resTable.addFieldVale("rec" + i, "youki", items[21].toString());
				resTable.addFieldVale("rec" + i, "yoryo", items[22].toString());
				resTable.addFieldVale("rec" + i, "cd_tani", items[23].toString());
				resTable.addFieldVale("rec" + i, "su_iri", items[24].toString());
				resTable.addFieldVale("rec" + i, "cd_ondo", items[25].toString());
				resTable.addFieldVale("rec" + i, "shomikikan", items[26].toString());
				resTable.addFieldVale("rec" + i, "genka", items[27].toString());
				resTable.addFieldVale("rec" + i, "baika", items[28].toString());
				resTable.addFieldVale("rec" + i, "buturyo", items[29].toString());
				resTable.addFieldVale("rec" + i, "dt_hatubai", items[30].toString());
				resTable.addFieldVale("rec" + i, "uriage_k", items[31].toString());
				resTable.addFieldVale("rec" + i, "rieki_k", items[32].toString());
				resTable.addFieldVale("rec" + i, "uriage_h", items[33].toString());
				resTable.addFieldVale("rec" + i, "rieki_h", items[34].toString());
				resTable.addFieldVale("rec" + i, "cd_nisugata", items[35].toString());
				resTable.addFieldVale("rec" + i, "memo", items[36].toString());
				resTable.addFieldVale("rec" + i, "keta_shosu", items[37].toString());
				resTable.addFieldVale("rec" + i, "kbn_haishi", items[38].toString());
				resTable.addFieldVale("rec" + i, "kbn_haita", items[39].toString());
				resTable.addFieldVale("rec" + i, "seq_shisaku", items[40].toString());
				resTable.addFieldVale("rec" + i, "id_toroku", items[41].toString());
				resTable.addFieldVale("rec" + i, "dt_toroku", items[42].toString());
				resTable.addFieldVale("rec" + i, "id_koshin", items[43].toString());
				resTable.addFieldVale("rec" + i, "dt_koshin", items[44].toString());
				resTable.addFieldVale("rec" + i, "nm_group", items[45].toString());
				resTable.addFieldVale("rec" + i, "nm_team", items[46].toString());
				resTable.addFieldVale("rec" + i, "nm_koshin", items[47].toString());
				resTable.addFieldVale("rec" + i, "nm_toroku", items[48].toString());
				resTable.addFieldVale("rec" + i, "memo_shisaku", items[49].toString());
				resTable.addFieldVale("rec" + i, "flg_chui", items[50].toString());
//【シサクイック（原価）要望】排他制御　TT.Nishigawa 2010/2/16 START -----------------------------------
				resTable.addFieldVale("rec" + i, "nm_user_haita", toString(items[51]));
				resTable.addFieldVale("rec" + i, "nm_kaisha_haita", toString(items[52]));
				resTable.addFieldVale("rec" + i, "nm_busho_haita", toString(items[53]));
//【シサクイック（原価）要望】排他制御　TT.Nishigawa 2010/2/16 END -------------------------------------


				//【QP@00342】
				resTable.addFieldVale("rec" + i, "nm_eigyo_tanto", toString(items[54]));


//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.8
				resTable.addFieldVale("rec" + i, "cd_daihyo_kojo", ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KOJO"));
				resTable.addFieldVale("rec" + i, "cd_daihyo_kaisha", ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KAISHA"));
//add end----------------------------------------------------------------------------------------

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
				resTable.addFieldVale("rec" + i, "nm_shiyo", ConstManager.getConstValue(Category.設定, "NM_SHIYO_JISEKI"));
//add end----------------------------------------------------------------------------------------


//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
				resTable.addFieldVale("rec" + i, "pt_kotei", toString(items[55]));
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
				
				//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
				resTable.addFieldVale("rec" + i, "flg_secret", toString(items[56]));
				//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
				//ADD 2013/10/22 QP@30154 okano start
				resTable.addFieldVale("rec" + i, "cd_hanseki", toString(items[57]));
				//ADD 2013/10/22 QP@30154 okano end

			}

//add start --------------------------------------------------------------------------------------

			if (lstTantouShaData.size() == 0) {
				//処理結果の格納
//QP@00412_シサクイック改良 No.8
				resTable.addFieldVale("rec0", "cd_daihyo_kojo", ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KOJO"));
				resTable.addFieldVale("rec0", "cd_daihyo_kaisha", ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KAISHA"));
//QP@00412_シサクイック改良 No.4
				//使用実績名
				resTable.addFieldVale("rec0", "nm_shiyo", ConstManager.getConstValue(Category.設定, "NM_SHIYO_JISEKI"));

			}
//add end----------------------------------------------------------------------------------------

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	/**
	 * 配合テーブルデータ パラメーター格納
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @param strTableNm : 対象テーブル
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void storageHaigoData(List<?> lstTantouShaData, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			for (int i = 0; i < lstTantouShaData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");

				Object[] items = (Object[]) lstTantouShaData.get(i);

				//検索結果を格納
				resTable.addFieldVale("rec" + i, "cd_shain", items[0].toString());
				resTable.addFieldVale("rec" + i, "nen", items[1].toString());
				resTable.addFieldVale("rec" + i, "no_oi", items[2].toString());
				resTable.addFieldVale("rec" + i, "cd_kotei", items[3].toString());
				resTable.addFieldVale("rec" + i, "seq_kotei", items[4].toString());
				resTable.addFieldVale("rec" + i, "nm_kotei", items[5].toString());
				resTable.addFieldVale("rec" + i, "zoku_kotei", items[6].toString());
				resTable.addFieldVale("rec" + i, "sort_kotei", items[7].toString());
				resTable.addFieldVale("rec" + i, "sort_genryo", items[8].toString());
				resTable.addFieldVale("rec" + i, "cd_genryo", items[9].toString());
				resTable.addFieldVale("rec" + i, "cd_kaisha", items[10].toString());
				resTable.addFieldVale("rec" + i, "cd_busho", items[11].toString());
				resTable.addFieldVale("rec" + i, "nm_genryo", items[12].toString());
				resTable.addFieldVale("rec" + i, "tanka", items[13].toString());
				resTable.addFieldVale("rec" + i, "budomari", items[14].toString());
				resTable.addFieldVale("rec" + i, "ritu_abura", items[15].toString());
				resTable.addFieldVale("rec" + i, "ritu_sakusan", items[16].toString());
				resTable.addFieldVale("rec" + i, "ritu_shokuen", items[17].toString());
				resTable.addFieldVale("rec" + i, "ritu_sousan", items[18].toString());
				resTable.addFieldVale("rec" + i, "color", items[19].toString());
				resTable.addFieldVale("rec" + i, "id_toroku", items[20].toString());
				resTable.addFieldVale("rec" + i, "dt_toroku", items[21].toString());
				resTable.addFieldVale("rec" + i, "id_koshin", items[22].toString());
				resTable.addFieldVale("rec" + i, "dt_koshin", items[23].toString());
				resTable.addFieldVale("rec" + i, "nm_koshin", items[24].toString());
				resTable.addFieldVale("rec" + i, "nm_toroku", items[25].toString());

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
				//マスタ歩留値
				resTable.addFieldVale("rec" + i, "ma_budomari", toString(items[26]));
//add end   -------------------------------------------------------------------------------
// ADD start 20121002 QP@20505 No.24
				resTable.addFieldVale("rec" + i, "ritu_msg", items[27].toString());
// ADD end 20121002 QP@20505 No.24
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	/**
	 * 試作テーブルデータ パラメーター格納
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @param strTableNm : 対象テーブル
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void storageShisakuData(List<?> lstTantouShaData, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			for (int i = 0; i < lstTantouShaData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");

				Object[] items = (Object[]) lstTantouShaData.get(i);

				//検索結果を格納
				resTable.addFieldVale("rec" + i, "cd_shain", items[0].toString());
				resTable.addFieldVale("rec" + i, "nen", items[1].toString());
				resTable.addFieldVale("rec" + i, "no_oi", items[2].toString());
				resTable.addFieldVale("rec" + i, "seq_shisaku", items[3].toString());
				resTable.addFieldVale("rec" + i, "sort_shisaku", items[4].toString());
				resTable.addFieldVale("rec" + i, "no_chui", items[5].toString());
				resTable.addFieldVale("rec" + i, "nm_sample", items[6].toString());
				resTable.addFieldVale("rec" + i, "memo", items[7].toString());
				//resTable.addFieldVale("rec" + i, "memo_shisaku", items[8].toString());
				resTable.addFieldVale("rec" + i, "flg_print", items[8].toString());
				resTable.addFieldVale("rec" + i, "flg_auto", items[9].toString());
				resTable.addFieldVale("rec" + i, "no_shisan", items[10].toString());
				resTable.addFieldVale("rec" + i, "no_seiho1", items[11].toString());
				resTable.addFieldVale("rec" + i, "no_seiho2", items[12].toString());
				resTable.addFieldVale("rec" + i, "no_seiho3", items[13].toString());
				resTable.addFieldVale("rec" + i, "no_seiho4", items[14].toString());
				resTable.addFieldVale("rec" + i, "no_seiho5", items[15].toString());
				resTable.addFieldVale("rec" + i, "ritu_sousan", items[16].toString());
				resTable.addFieldVale("rec" + i, "flg_sousan", items[17].toString());
				resTable.addFieldVale("rec" + i, "ritu_shokuen", items[18].toString());
				resTable.addFieldVale("rec" + i, "flg_shokuen", items[19].toString());
				resTable.addFieldVale("rec" + i, "sando_suiso", items[20].toString());
				resTable.addFieldVale("rec" + i, "flg_sando_suiso", items[21].toString());
				resTable.addFieldVale("rec" + i, "shokuen_suiso", items[22].toString());
				resTable.addFieldVale("rec" + i, "flg_shokuen_suiso", items[23].toString());
				resTable.addFieldVale("rec" + i, "sakusan_suiso", items[24].toString());
				resTable.addFieldVale("rec" + i, "flg_sakusan_suiso", items[25].toString());
				resTable.addFieldVale("rec" + i, "toudo", items[26].toString());
				resTable.addFieldVale("rec" + i, "flg_toudo", items[27].toString());
				resTable.addFieldVale("rec" + i, "nendo", items[28].toString());
				resTable.addFieldVale("rec" + i, "flg_nendo", items[29].toString());
				resTable.addFieldVale("rec" + i, "ondo", items[30].toString());
				resTable.addFieldVale("rec" + i, "flg_ondo", items[31].toString());
				resTable.addFieldVale("rec" + i, "ph", items[32].toString());
				resTable.addFieldVale("rec" + i, "flg_ph", items[33].toString());
				resTable.addFieldVale("rec" + i, "ritu_sousan_bunseki", items[34].toString());
				resTable.addFieldVale("rec" + i, "flg_sousan_bunseki", items[35].toString());
				resTable.addFieldVale("rec" + i, "ritu_shokuen_bunseki", items[36].toString());
				resTable.addFieldVale("rec" + i, "flg_shokuen_bunseki", items[37].toString());
				resTable.addFieldVale("rec" + i, "hiju", items[38].toString());
				resTable.addFieldVale("rec" + i, "flg_hiju", items[39].toString());
				resTable.addFieldVale("rec" + i, "suibun_kasei", items[40].toString());
				resTable.addFieldVale("rec" + i, "flg_suibun_kasei", items[41].toString());
				resTable.addFieldVale("rec" + i, "alcohol", items[42].toString());
				resTable.addFieldVale("rec" + i, "flg_alcohol", items[43].toString());
				resTable.addFieldVale("rec" + i, "memo_sakusei", items[44].toString());
				resTable.addFieldVale("rec" + i, "flg_memo", items[45].toString());
				resTable.addFieldVale("rec" + i, "hyoka", items[46].toString());
				resTable.addFieldVale("rec" + i, "flg_hyoka", items[47].toString());
				resTable.addFieldVale("rec" + i, "free_title1", items[48].toString());
				resTable.addFieldVale("rec" + i, "free_value1", items[49].toString());
				resTable.addFieldVale("rec" + i, "flg_free1", items[50].toString());
				resTable.addFieldVale("rec" + i, "free_title2", items[51].toString());
				resTable.addFieldVale("rec" + i, "free_value2", items[52].toString());
				resTable.addFieldVale("rec" + i, "flg_free2", items[53].toString());
				resTable.addFieldVale("rec" + i, "free_title3", items[54].toString());
				resTable.addFieldVale("rec" + i, "free_value3", items[55].toString());
				resTable.addFieldVale("rec" + i, "flg_free3", items[56].toString());
				resTable.addFieldVale("rec" + i, "dt_shisaku", items[57].toString());
				resTable.addFieldVale("rec" + i, "juryo_shiagari_g", items[58].toString());
				resTable.addFieldVale("rec" + i, "id_toroku", items[59].toString());
				resTable.addFieldVale("rec" + i, "dt_toroku", items[60].toString());
				resTable.addFieldVale("rec" + i, "id_koshin", items[61].toString());
				resTable.addFieldVale("rec" + i, "dt_koshin", items[62].toString());
				resTable.addFieldVale("rec" + i, "nm_koshin", items[63].toString());
				resTable.addFieldVale("rec" + i, "nm_toroku", items[64].toString());
//2009/10/25 TT.A.ISONO ADD START [原価試算：試算依頼flgを返却ﾊﾟﾗﾒｰﾀに付加]
				resTable.addFieldVale("rec" + i, "flg_shisanIrai", items[65].toString());
//2009/10/25 TT.A.ISONO ADD END   [原価試算：試算依頼flgを返却ﾊﾟﾗﾒｰﾀに付加]
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
				resTable.addFieldVale("rec" + i, "siki_keisan", items[66].toString());
//add end   -------------------------------------------------------------------------------
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
				resTable.addFieldVale("rec" + i, "hiju_sui", toString(items[67]));
				resTable.addFieldVale("rec" + i, "flg_hiju_sui", toString(items[68]));
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20120927 QP@20505 No.24
				resTable.addFieldVale("rec" + i, "freetitle_nendo", items[69].toString());
				resTable.addFieldVale("rec" + i, "free_nendo", items[70].toString());
				resTable.addFieldVale("rec" + i, "flg_freeNendo", items[71].toString());
				resTable.addFieldVale("rec" + i, "freetitle_ondo", items[72].toString());
				resTable.addFieldVale("rec" + i, "free_ondo", items[73].toString());
				resTable.addFieldVale("rec" + i, "flg_freeOndo", items[74].toString());
				resTable.addFieldVale("rec" + i, "freetitle_suibun_kasei", items[75].toString());
				resTable.addFieldVale("rec" + i, "free_suibun_kasei", items[76].toString());
				resTable.addFieldVale("rec" + i, "flg_freeSuibunKasei", items[77].toString());
				resTable.addFieldVale("rec" + i, "freetitle_alcohol", items[78].toString());
				resTable.addFieldVale("rec" + i, "free_alcohol", items[79].toString());
				resTable.addFieldVale("rec" + i, "flg_freeAlchol", items[80].toString());
				resTable.addFieldVale("rec" + i, "jikkoSakusanNodo", items[81].toString());
				resTable.addFieldVale("rec" + i, "flg_jikkoSakusanNodo", items[82].toString());
				resTable.addFieldVale("rec" + i, "msg_suiso", items[83].toString());
				resTable.addFieldVale("rec" + i, "flg_msg_suiso", items[84].toString());
				resTable.addFieldVale("rec" + i, "free_title4", items[85].toString());
				resTable.addFieldVale("rec" + i, "free_value4", items[86].toString());
				resTable.addFieldVale("rec" + i, "flg_free4", items[87].toString());
				resTable.addFieldVale("rec" + i, "free_title5", items[88].toString());
				resTable.addFieldVale("rec" + i, "free_value5", items[89].toString());
				resTable.addFieldVale("rec" + i, "flg_free5", items[90].toString());
				resTable.addFieldVale("rec" + i, "free_title6", items[91].toString());
				resTable.addFieldVale("rec" + i, "free_value6", items[92].toString());
				resTable.addFieldVale("rec" + i, "flg_free6", items[93].toString());
				resTable.addFieldVale("rec" + i, "ritu_msg", items[94].toString());
				resTable.addFieldVale("rec" + i, "flg_msg", items[95].toString());
// ADD end 20120927 QP@20505 No.24
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	/**
	 * 試作リストテーブルデータ パラメーター格納
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @param strTableNm : 対象テーブル
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void storageShisakuListData(List<?> lstTantouShaData, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			for (int i = 0; i < lstTantouShaData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");

				Object[] items = (Object[]) lstTantouShaData.get(i);

				//検索結果を格納
				resTable.addFieldVale("rec" + i, "cd_shain", items[0].toString());
				resTable.addFieldVale("rec" + i, "nen", items[1].toString());
				resTable.addFieldVale("rec" + i, "no_oi", items[2].toString());
				resTable.addFieldVale("rec" + i, "seq_shisaku", items[3].toString());
				resTable.addFieldVale("rec" + i, "cd_kotei", items[4].toString());
				resTable.addFieldVale("rec" + i, "seq_kotei", items[5].toString());
// ADD start 20120914 QP@20505 No.1
				resTable.addFieldVale("rec" + i, "juryo_shiagari_seq", items[14].toString());
// ADD end 20120914 QP@20505 No.1;
				resTable.addFieldVale("rec" + i, "quantity", items[6].toString());
				resTable.addFieldVale("rec" + i, "color", items[7].toString());
				resTable.addFieldVale("rec" + i, "id_toroku", items[8].toString());
				resTable.addFieldVale("rec" + i, "dt_toroku", items[9].toString());
				resTable.addFieldVale("rec" + i, "id_koshin", items[10].toString());
				resTable.addFieldVale("rec" + i, "dt_koshin", items[11].toString());
				resTable.addFieldVale("rec" + i, "nm_koshin", items[12].toString());
				resTable.addFieldVale("rec" + i, "nm_toroku", items[13].toString());

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	/**
	 * 製造工程テーブルデータ パラメーター格納
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @param strTableNm : 対象テーブル
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void storageKouteiData(List<?> lstTantouShaData, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			for (int i = 0; i < lstTantouShaData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");

				Object[] items = (Object[]) lstTantouShaData.get(i);

				//検索結果を格納
				resTable.addFieldVale("rec" + i, "cd_shain", items[0].toString());
				resTable.addFieldVale("rec" + i, "nen", items[1].toString());
				resTable.addFieldVale("rec" + i, "no_oi", items[2].toString());
				//resTable.addFieldVale("rec" + i, "seq_shisaku", items[3].toString());
				resTable.addFieldVale("rec" + i, "no_chui", items[3].toString());
				resTable.addFieldVale("rec" + i, "chuijiko", items[4].toString());
				resTable.addFieldVale("rec" + i, "id_toroku", items[5].toString());
				resTable.addFieldVale("rec" + i, "dt_toroku", items[6].toString());
				resTable.addFieldVale("rec" + i, "id_koshin", items[7].toString());
				resTable.addFieldVale("rec" + i, "dt_koshin", items[8].toString());
				resTable.addFieldVale("rec" + i, "nm_koshin", items[9].toString());
				resTable.addFieldVale("rec" + i, "nm_toroku", items[10].toString());

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	/**
	 * 原価資材テーブルデータ パラメーター格納
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @param strTableNm : 対象テーブル
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void storageShizaiData(List<?> lstTantouShaData, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			for (int i = 0; i < lstTantouShaData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");

				Object[] items = (Object[]) lstTantouShaData.get(i);

				//検索結果を格納
				resTable.addFieldVale("rec" + i, "cd_shain", items[0].toString());
				resTable.addFieldVale("rec" + i, "nen", items[1].toString());
				resTable.addFieldVale("rec" + i, "no_oi", items[2].toString());
				resTable.addFieldVale("rec" + i, "seq_shizai", items[3].toString());
				resTable.addFieldVale("rec" + i, "no_sort", items[4].toString());
				resTable.addFieldVale("rec" + i, "cd_shizai", items[5].toString());
				resTable.addFieldVale("rec" + i, "nm_shizai", items[6].toString());
				resTable.addFieldVale("rec" + i, "tanka", items[7].toString());
				resTable.addFieldVale("rec" + i, "budomari", items[8].toString());
				resTable.addFieldVale("rec" + i, "id_toroku", items[9].toString());
				resTable.addFieldVale("rec" + i, "dt_toroku", items[10].toString());
				resTable.addFieldVale("rec" + i, "id_koshin", items[11].toString());
				resTable.addFieldVale("rec" + i, "dt_koshin", items[12].toString());
				resTable.addFieldVale("rec" + i, "nm_koshin", items[13].toString());
				resTable.addFieldVale("rec" + i, "nm_toroku", items[14].toString());

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	/**
	 * 原価原料テーブルデータ パラメーター格納
	 * @param lstTantouShaData : 検索結果情報リスト
	 * @param strTableNm : 対象テーブル
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 *
	 * @author TT.k-katayama
	 * @since  2009/03/30
	 */
	private void storageGenryoData(List<?> lstTantouShaData, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			for (int i = 0; i < lstTantouShaData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");

				Object[] items = (Object[]) lstTantouShaData.get(i);

				//検索結果を格納
				resTable.addFieldVale("rec" + i, "cd_shain", items[0].toString());
				resTable.addFieldVale("rec" + i, "nen", items[1].toString());
				resTable.addFieldVale("rec" + i, "no_oi", items[2].toString());
				resTable.addFieldVale("rec" + i, "seq_shisaku", items[3].toString());
				resTable.addFieldVale("rec" + i, "flg_print", items[4].toString());
				resTable.addFieldVale("rec" + i, "zyusui", items[5].toString());
				resTable.addFieldVale("rec" + i, "zyuabura", items[6].toString());
				resTable.addFieldVale("rec" + i, "gokei", items[7].toString());
				resTable.addFieldVale("rec" + i, "genryohi", items[8].toString());
				resTable.addFieldVale("rec" + i, "genryohi1", items[9].toString());
				resTable.addFieldVale("rec" + i, "hiju", items[10].toString());
				resTable.addFieldVale("rec" + i, "yoryo", items[11].toString());
				resTable.addFieldVale("rec" + i, "irisu", items[12].toString());
				resTable.addFieldVale("rec" + i, "yukobudomari", items[13].toString());
				resTable.addFieldVale("rec" + i, "reberu", items[14].toString());
				resTable.addFieldVale("rec" + i, "hizyubudomari", items[15].toString());
				resTable.addFieldVale("rec" + i, "heikinzyu", items[16].toString());
				resTable.addFieldVale("rec" + i, "cs_genryo", items[17].toString());
				resTable.addFieldVale("rec" + i, "cs_zairyohi", items[18].toString());
				resTable.addFieldVale("rec" + i, "cs_keihi", items[19].toString());
				resTable.addFieldVale("rec" + i, "cs_genka", items[20].toString());
				resTable.addFieldVale("rec" + i, "ko_genka", items[21].toString());
				resTable.addFieldVale("rec" + i, "ko_baika", items[22].toString());
				resTable.addFieldVale("rec" + i, "ko_riritu", items[23].toString());
				resTable.addFieldVale("rec" + i, "id_toroku", items[24].toString());
				resTable.addFieldVale("rec" + i, "dt_toroku", items[25].toString());
				resTable.addFieldVale("rec" + i, "id_koshin", items[26].toString());
				resTable.addFieldVale("rec" + i, "dt_koshin", items[27].toString());
				resTable.addFieldVale("rec" + i, "nm_toroku", items[28].toString());
				resTable.addFieldVale("rec" + i, "nm_koshin", items[29].toString());

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
