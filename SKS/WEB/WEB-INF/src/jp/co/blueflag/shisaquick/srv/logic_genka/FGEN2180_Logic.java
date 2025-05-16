package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * 枝番作成
 * 元版データを基に枝版データを作成する。
 *
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class FGEN2180_Logic extends LogicBase {

	String no_eda = "";

	/**
	 * 原価試算（営業）管理コンストラクタ ： インスタンス生成
	 */
	public FGEN2180_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 枝番作成
	 *
	 * @param reqData
	 *            : リクエストデータ
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
		userInfoData = _userInfoData;
		List<?> lstRecset = null;

		StringBuffer strSql = new StringBuffer();

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			String cd_shain = toString( reqData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( reqData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( reqData.getFieldVale("kihon", 0, "no_oi"));

			//枝番最大値取得
			strSql.append(" SELECT ");
			strSql.append(" 	MAX(no_eda)+1 AS no_eda ");
			strSql.append(" FROM ");
			strSql.append(" 	tr_shisan_shisakuhin  ");
			strSql.append(" WHERE ");
			strSql.append(" 	cd_shain = " + cd_shain);
			strSql.append(" 	AND nen = " + nen);
			strSql.append(" 	AND no_oi = " + no_oi);
			createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			Object item = (Object)lstRecset.get(0);
			no_eda = toString(item);

			// トランザクション開始
			super.createExecDB();
			execDB.BeginTran();

			// 試算　試作品テーブル作成
			strSql = insertSQL_sisan_sisakuhin(reqData,no_eda);
			execDB.execSQL(strSql.toString()); // SQLを実行

			// 試算　配合テーブル作成
			strSql = insertSQL_sisan_haigo(reqData,no_eda);
			execDB.execSQL(strSql.toString()); // SQLを実行

			// 試算　試作テーブル作成
			strSql = insertSQL_sisan_shisaku(reqData,no_eda);
			execDB.execSQL(strSql.toString()); // SQLを実行


//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
			// 試算　試作（固定項目）テーブル作成
			strSql = insertSQL_sisan_shisaku_kotei(reqData,no_eda);
			execDB.execSQL(strSql.toString()); // SQLを実行
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end


			// 試算　資材テーブル作成
			//strSql = insertSQL_sisan_sizai(reqData,no_eda);
			//execDB.execSQL(strSql.toString()); // SQLを実行

			// 試算　ステータステーブル作成
			strSql = insertSQL_sisan_status(reqData,no_eda);
			execDB.execSQL(strSql.toString()); // SQLを実行

			// 試算　ステータス履歴テーブル作成
			strSql = insertSQL_sisan_rireki(reqData,no_eda);
			execDB.execSQL(strSql.toString()); // SQLを実行

			// ADD 2013/7/2 shima【QP@30151】No.37 start
			// 試算　試作情報テーブル作成
			strSql = insertSQL_sisan_kihonjoho(reqData,no_eda);
			execDB.execSQL(strSql.toString()); // SQLを実行
			// ADD 2013/7/2 shima【QP@30151】No.37 end

			// コミット
			execDB.Commit();

			// 機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
			storageGroupDataKanri(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			if (execDB != null) {
				// ロールバック
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");
		} finally {
			if (execDB != null) {
				// セッションのクローズ
				execDB.Close();
				execDB = null;
			}

			//変数の削除

			strSql = null;
		}
		return resKind;
	}

	/**
	 * 試算　試作品テーブル作成SQL
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer insertSQL_sisan_sisakuhin(
			RequestResponsKindBean requestData
			,String no_eda
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			//リクエストからデータ取得
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String shurui = toString( requestData.getFieldVale("kihon", 0, "shurui"));
// ADD start 20120410 hisahori
			String nmEdaShisaku = toString( requestData.getFieldVale("kihon", 0, "eda_nm_shisaku"));
// ADD end 20120410 hisahori
			// ADD start 2015/03/03 【QP@40812】No.3
			String no_eda_org = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			// ADD end 2015/03/03 【QP@40812】No.3

			// SQL文の作成
			strInsSql.append(" INSERT INTO tr_shisan_shisakuhin ");
			// ADD start 2020/01/15 【S200720】原価試算システム（枝番作成）
			strInsSql.append(" 	( ");
			strInsSql.append(" 	   cd_shain ");
			strInsSql.append(" 	  ,nen ");
			strInsSql.append(" 	  ,no_oi ");
			strInsSql.append(" 	  ,no_eda ");
			strInsSql.append(" 	  ,cd_kaisha ");
			strInsSql.append(" 	  ,cd_kojo ");
			strInsSql.append(" 	  ,su_iri ");
			strInsSql.append(" 	  ,genka ");
			strInsSql.append(" 	  ,baika ");
			strInsSql.append(" 	  ,cd_genka_tani ");
			strInsSql.append(" 	  ,buturyo ");
			strInsSql.append(" 	  ,dt_hatubai ");
			strInsSql.append(" 	  ,uriage_k ");
			strInsSql.append(" 	  ,rieki_k ");
			strInsSql.append(" 	  ,uriage_h ");
			strInsSql.append(" 	  ,rieki_h ");
			strInsSql.append(" 	  ,cd_nisugata ");
			strInsSql.append(" 	  ,lot ");
			strInsSql.append(" 	  ,saiyo_sample ");
			strInsSql.append(" 	  ,ken_id_koshin ");
			strInsSql.append(" 	  ,ken_dt_koshin ");
			strInsSql.append(" 	  ,sei_id_koshin ");
			strInsSql.append(" 	  ,sei_dt_koshin ");
			strInsSql.append(" 	  ,gen_id_koshin ");
			strInsSql.append(" 	  ,gen_dt_koshin ");
			strInsSql.append(" 	  ,kojo_id_koshin ");
			strInsSql.append(" 	  ,kojo_dt_koshin ");
			strInsSql.append(" 	  ,sam_dt_koshin ");
			strInsSql.append(" 	  ,fg_keisan ");
			strInsSql.append(" 	  ,id_toroku ");
			strInsSql.append(" 	  ,dt_toroku ");
			strInsSql.append(" 	  ,id_koshin ");
			strInsSql.append(" 	  ,dt_koshin ");
			strInsSql.append(" 	  ,sam_id_koshin ");
			strInsSql.append(" 	  ,haita_id_user ");
			strInsSql.append(" 	  ,kikan_hanbai_sen ");
			strInsSql.append(" 	  ,kikan_hanbai_tani ");
			strInsSql.append(" 	  ,shurui_eda ");
			strInsSql.append(" 	  ,yoryo ");
			strInsSql.append(" 	  ,dt_kizitu ");
			strInsSql.append(" 	  ,su_irai ");
			strInsSql.append(" 	  ,kikan_hanbai_suti ");
			strInsSql.append(" 	  ,nm_edaShisaku ");
			strInsSql.append(" 	  ,cd_hanseki ");
			strInsSql.append(" 	) ");
			// ADD end 2020/01/15 【S200720】原価試算システム（枝番作成）
			strInsSql.append(" 	SELECT ");
			strInsSql.append(" 	   cd_shain ");
			strInsSql.append(" 	  ,nen ");
			strInsSql.append(" 	  ,no_oi ");
			strInsSql.append(" 	  ," + no_eda);
			strInsSql.append(" 	  ,cd_kaisha ");
			strInsSql.append(" 	  ,cd_kojo ");
			strInsSql.append(" 	  ,su_iri ");
			strInsSql.append(" 	  ,genka ");
			strInsSql.append(" 	  ,baika ");
			strInsSql.append(" 	  ,cd_genka_tani ");
			strInsSql.append(" 	  ,buturyo ");
			strInsSql.append(" 	  ,dt_hatubai ");
			strInsSql.append(" 	  ,uriage_k ");
			strInsSql.append(" 	  ,rieki_k ");
			strInsSql.append(" 	  ,uriage_h ");
			strInsSql.append(" 	  ,rieki_h ");
			strInsSql.append(" 	  ,cd_nisugata ");
			strInsSql.append(" 	  ,NULL "); //製造ロット
			strInsSql.append(" 	  ,NULL "); //採用ｻﾝﾌﾟﾙNo
			strInsSql.append(" 	  ,ken_id_koshin ");
			strInsSql.append(" 	  ,ken_dt_koshin ");
			strInsSql.append(" 	  ,sei_id_koshin ");
			strInsSql.append(" 	  ,sei_dt_koshin ");
			strInsSql.append(" 	  ,gen_id_koshin ");
			strInsSql.append(" 	  ,gen_dt_koshin ");
			strInsSql.append(" 	  ,kojo_id_koshin ");
			strInsSql.append(" 	  ,kojo_dt_koshin ");
			strInsSql.append(" 	  ,sam_dt_koshin ");
			strInsSql.append(" 	  ,fg_keisan ");
			strInsSql.append(" 	  ," + userInfoData.getId_user()); //登録者ID
			strInsSql.append(" 	  ,GETDATE()"); //登録日
			strInsSql.append(" 	  ," + userInfoData.getId_user()); //更新者ID
			strInsSql.append(" 	  ,GETDATE()"); //更新日
			strInsSql.append(" 	  ,sam_id_koshin ");
			strInsSql.append(" 	  ,haita_id_user ");
			strInsSql.append(" 	  ,kikan_hanbai_sen ");
			strInsSql.append(" 	  ,kikan_hanbai_tani ");
			strInsSql.append(" 	  ,'" + shurui + "'"); //枝番種類
			strInsSql.append(" 	  ,yoryo ");
			strInsSql.append(" 	  ,NULL "); //試算期日
			strInsSql.append(" 	  ,1 "); //依頼回数
			strInsSql.append(" 	  ,kikan_hanbai_suti "); //販売期間（数値）
// ADD start 20120410 hisahori
			strInsSql.append(" 	  ,'" + nmEdaShisaku + "' "); //枝番試作名
// ADD end 20120410 hisahori
			// ADD 2013/10/22 QP@30154 okano start
			strInsSql.append(" 	  ,cd_hanseki ");	//販責会社CD
			// ADD 2013/10/22 QP@30154 okano end

			strInsSql.append(" FROM ");
			strInsSql.append(" 	 tr_shisan_shisakuhin ");
			strInsSql.append(" WHERE ");
			strInsSql.append(" 	 cd_shain = " + cd_shain);
			strInsSql.append(" 	 AND nen = " + nen);
			strInsSql.append(" 	 AND no_oi = " + no_oi);
			// MOD start 2015/03/03 【QP@40812】No.3
//			strInsSql.append(" 	 AND no_eda = 0 ");
			strInsSql.append("   AND no_eda = " + no_eda_org);
			// MOD end 2015/03/03 【QP@40812】No.3

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * 試算　配合テーブル作成SQL
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer insertSQL_sisan_haigo(
			RequestResponsKindBean requestData
			,String no_eda
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			//リクエストからデータ取得
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			// ADD start 2015/03/03 【QP@40812】No.3
			String no_eda_org = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			// ADD end 2015/03/03 【QP@40812】No.3

			// SQL文の作成
			strInsSql.append(" INSERT INTO tr_shisan_haigo ");
			strInsSql.append("  SELECT ");
			strInsSql.append("    cd_shain ");
			strInsSql.append("   ,nen ");
			strInsSql.append("   ,no_oi ");
			strInsSql.append("   ,cd_kotei ");
			strInsSql.append("   ,seq_kotei ");
			strInsSql.append("   ," + no_eda);
			strInsSql.append("   ,nm_genryo ");
			strInsSql.append("   ,tanka_ins ");
			strInsSql.append("   ,budomari_ins ");
			strInsSql.append("   ,tanka_ma ");
			strInsSql.append("   ,budomar_ma ");
			strInsSql.append("   ," + userInfoData.getId_user()); //登録者ID
			strInsSql.append("   ,GETDATE()"); //登録日
			strInsSql.append("   ,NULL"); //更新者ID
			strInsSql.append("   ,NULL"); //更新日
			strInsSql.append("   ,cd_kaisha_tanka ");
			strInsSql.append("   ,cd_kojo_tanka ");
			strInsSql.append("   ,cd_kaisha_budomari ");
			strInsSql.append("   ,cd_kojo_budomari ");
			strInsSql.append("  FROM ");
			strInsSql.append("  tr_shisan_haigo ");
			strInsSql.append("  WHERE ");
			strInsSql.append("  cd_shain = " + cd_shain);
			strInsSql.append("  AND nen = " + nen);
			strInsSql.append("  AND no_oi = " + no_oi);
			// MOD start 2015/03/03 【QP@40812】No.3
//			strInsSql.append("  AND no_eda = 0 ");
			strInsSql.append("   AND no_eda = " + no_eda_org);
			// MOD end 2015/03/03 【QP@40812】No.3

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * 試算　試作テーブル作成SQL
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer insertSQL_sisan_shisaku(
			RequestResponsKindBean requestData
			,String no_eda
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			//リクエストからデータ取得
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			// ADD start 2015/03/03 【QP@40812】No.3
			String no_eda_org = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			// ADD end 2015/03/03 【QP@40812】No.3

			// SQL文の作成
			strInsSql.append(" INSERT INTO tr_shisan_shisaku ");
			// ADD start 2020/01/15 【S200720】原価試算システム（枝番作成）
			strInsSql.append("  ( ");
			strInsSql.append("    cd_shain ");
			strInsSql.append("   ,nen ");
			strInsSql.append("   ,no_oi ");
			strInsSql.append("   ,seq_shisaku ");
			strInsSql.append("   ,no_eda ");
			strInsSql.append("   ,juryo_shiagari_g ");
			strInsSql.append("   ,dt_shisan ");
			strInsSql.append("   ,budomari ");
			strInsSql.append("   ,heikinjuten ");
			strInsSql.append("   ,cs_kotei ");
			strInsSql.append("   ,kg_kotei ");
			strInsSql.append("   ,id_toroku ");
			strInsSql.append("   ,dt_toroku ");
			strInsSql.append("   ,id_koshin ");
			strInsSql.append("   ,dt_koshin ");
			strInsSql.append("   ,fg_chusi ");
			strInsSql.append("   ,fg_koumokuchk ");
			strInsSql.append("   ,cs_rieki ");
			strInsSql.append("   ,kg_rieki ");
			strInsSql.append("  ) ");
			// ADD end 2020/01/15 【S200720】原価試算システム（枝番作成）
			strInsSql.append("  SELECT ");
			strInsSql.append("    cd_shain ");
			strInsSql.append("   ,nen ");
			strInsSql.append("   ,no_oi ");
			strInsSql.append("   ,seq_shisaku ");
			strInsSql.append("   ," + no_eda);
			strInsSql.append("   ,juryo_shiagari_g ");
			strInsSql.append("   ,NULL "); //試算日
			strInsSql.append("   ,NULL "); //有効歩留
			strInsSql.append("   ,NULL "); //平均充填量
			strInsSql.append("   ,NULL "); //固定費（円）/ケース
			strInsSql.append("   ,NULL "); //固定費（円）/㎏
			strInsSql.append("   ," + userInfoData.getId_user()); //登録者ID
			strInsSql.append("   ,GETDATE()"); //登録日
			strInsSql.append("   ,NULL"); //更新者ID
			strInsSql.append("   ,NULL"); //更新日
			strInsSql.append("   ,fg_chusi");
//ADD 2013/07/12 ogawa 【QP@30151】No.13 start
			strInsSql.append("   ,fg_koumokuchk"); //項目固定チェック
//ADD 2013/07/12 ogawa 【QP@30151】No.13 end
			//ADD 2013/10/22 QP@30154 okano start
			strInsSql.append("   ,NULL ");
			strInsSql.append("   ,NULL ");
			//ADD 2013/10/22 QP@30154 okano end
			strInsSql.append("  FROM ");
			strInsSql.append("  tr_shisan_shisaku ");
			strInsSql.append("  WHERE ");
			strInsSql.append("  cd_shain = " + cd_shain);
			strInsSql.append("  AND nen = " + nen);
			strInsSql.append("  AND no_oi = " + no_oi);
			// MOD start 2015/03/03 【QP@40812】No.3
//			strInsSql.append("  AND no_eda = 0");
			strInsSql.append("   AND no_eda = " + no_eda_org);
			// MOD end 2015/03/03 【QP@40812】No.3


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/* ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
	 * 試算　試作テーブル作成SQL
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer insertSQL_sisan_shisaku_kotei(
			RequestResponsKindBean requestData
			,String no_eda
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			//リクエストからデータ取得
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			// ADD start 2015/03/03 【QP@40812】No.3
			String no_eda_org = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			// ADD end 2015/03/03 【QP@40812】No.3

			// SQL文の作成
			strInsSql.append(" INSERT INTO tr_shisan_shisaku_kotei");
			strInsSql.append(" SELECT ");
			strInsSql.append("      cd_shain");
			strInsSql.append("     ,nen");
			strInsSql.append("     ,no_oi");
			strInsSql.append("     ,seq_shisaku");
			strInsSql.append("     ," + no_eda);
			strInsSql.append("     ,zyusui");
			strInsSql.append("     ,zyuabura");
			strInsSql.append("     ,gokei");
			strInsSql.append("     ,hiju");
			strInsSql.append("     ,reberu");
			strInsSql.append("     ,hijukasan");
			strInsSql.append("     ,cs_genryo");
			strInsSql.append("     ,cs_zairyohi");
			strInsSql.append("     ,cs_genka");
			strInsSql.append("     ,ko_genka");
			strInsSql.append("     ,kg_genryo");
			strInsSql.append("     ,kg_zairyohi");
			strInsSql.append("     ,kg_genka");
			strInsSql.append("     ,baika");
			strInsSql.append("     ,arari");
			strInsSql.append("     ," + userInfoData.getId_user());
			strInsSql.append("     ,GETDATE()");
			strInsSql.append("     ," + userInfoData.getId_user());
			strInsSql.append("     ,GETDATE()");
			strInsSql.append(" FROM");
			strInsSql.append("     tr_shisan_shisaku_kotei");
			strInsSql.append(" WHERE");
			strInsSql.append("     cd_shain=" + cd_shain);
			strInsSql.append(" and nen=" + nen);
			strInsSql.append(" and no_oi="+ no_oi);
			// MOD start 2015/03/03 【QP@40812】No.3
//			strInsSql.append(" and no_eda=0");
			strInsSql.append("   AND no_eda = " + no_eda_org);
			// MOD end 2015/03/03 【QP@40812】No.3

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * 試算　資材テーブル作成SQL
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer insertSQL_sisan_sizai(
			RequestResponsKindBean requestData
			,String no_eda
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			//リクエストからデータ取得
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			// ADD start 2015/03/03 【QP@40812】No.3
			String no_eda_org = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			// ADD end 2015/03/03 【QP@40812】No.3

			// SQL文の作成
			strInsSql.append(" INSERT INTO tr_shisan_shizai ");
			strInsSql.append("  SELECT ");
			strInsSql.append("    cd_shain ");
			strInsSql.append("   ,nen ");
			strInsSql.append("   ,no_oi ");
			strInsSql.append("   ,seq_shizai ");
			strInsSql.append("   ," + no_eda);
			strInsSql.append("   ,no_sort ");
			strInsSql.append("   ,cd_kaisha ");
			strInsSql.append("   ,cd_busho  ");
			strInsSql.append("   ,cd_shizai ");
			strInsSql.append("   ,nm_shizai ");
			strInsSql.append("   ,tanka ");
			strInsSql.append("   ,budomari ");
			strInsSql.append("   ,cs_siyou ");
			strInsSql.append("   ," + userInfoData.getId_user());
			strInsSql.append("   ,GETDATE()");
			strInsSql.append("   ,NULL");
			strInsSql.append("   ,NULL");
			strInsSql.append("  FROM ");
			strInsSql.append("  tr_shisan_shizai ");
			strInsSql.append("  WHERE ");
			strInsSql.append("  cd_shain = " + cd_shain);
			strInsSql.append("  AND nen = " + nen);
			strInsSql.append("  AND no_oi = " + no_oi);
			// MOD start 2015/03/03 【QP@40812】No.3
//			strInsSql.append("  AND no_eda = 0 ");
			strInsSql.append("   AND no_eda = " + no_eda_org);
			// MOD end 2015/03/03 【QP@40812】No.3

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * 試算　ステータステーブル作成SQL
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer insertSQL_sisan_status(
			RequestResponsKindBean requestData
			,String no_eda
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			//リクエストからデータ取得
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			// ADD start 2015/03/03 【QP@40812】No.3
			String no_eda_org = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			// ADD end 2015/03/03 【QP@40812】No.3

			// SQL文の作成
			strInsSql.append(" INSERT INTO tr_shisan_status ");
			strInsSql.append("  SELECT ");
			strInsSql.append("    cd_shain ");
			strInsSql.append("   ,nen ");
			strInsSql.append("   ,no_oi ");
			strInsSql.append("   ," + no_eda);
			strInsSql.append("   ,2 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ," + userInfoData.getId_user());
			strInsSql.append("   ,GETDATE()");
			strInsSql.append("   ,NULL ");
			strInsSql.append("   ,NULL ");
			strInsSql.append("  FROM ");
			strInsSql.append("   tr_shisan_shisakuhin ");
			strInsSql.append("  WHERE ");
			strInsSql.append("   cd_shain = " + cd_shain);
			strInsSql.append("   AND nen = " + nen);
			strInsSql.append("   AND no_oi = " + no_oi);
			// MOD start 2015/03/03 【QP@40812】No.3
//			strInsSql.append("   AND no_eda = 0");
			strInsSql.append("   AND no_eda = " + no_eda_org);
			// MOD end 2015/03/03 【QP@40812】No.3

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * 試算　ステータス履歴作成SQL
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer insertSQL_sisan_rireki(
			RequestResponsKindBean requestData
			,String no_eda
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			//リクエストからデータ取得
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));

			// MOD start 2015/03/03 【QP@40812】No.9
			// SQL文の作成
/*			strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
			strInsSql.append("  SELECT ");
			strInsSql.append("    cd_shain ");
			strInsSql.append("   ,nen ");
			strInsSql.append("   ,no_oi ");
			strInsSql.append("   ," + no_eda);
			strInsSql.append("   ,GETDATE()");
			strInsSql.append("   ," + userInfoData.getCd_kaisha());
			strInsSql.append("   ," + userInfoData.getCd_busho());
			strInsSql.append("   ," + userInfoData.getId_user());
			strInsSql.append("   ,'wk_kenkyu' ");
			strInsSql.append("   ,'1' ");
			strInsSql.append("   ,2 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ," + userInfoData.getId_user());
			strInsSql.append("   ,GETDATE()");
			strInsSql.append("  FROM ");
			strInsSql.append("   tr_shisan_shisakuhin");
			strInsSql.append("  WHERE ");
			strInsSql.append("   cd_shain = " + cd_shain);
			strInsSql.append("   AND nen = " + nen);
			strInsSql.append("   AND no_oi = " + no_oi);
			strInsSql.append("   AND no_eda = 0");
*/

			// 元版の研究所作業者を登録（一件目のデータより作業者をコピー）
			strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
			strInsSql.append("  SELECT TOP 1");
			strInsSql.append("    cd_shain ");
			strInsSql.append("   ,nen ");
			strInsSql.append("   ,no_oi ");
			strInsSql.append("   ," + no_eda);
			strInsSql.append("   ,GETDATE()");
			strInsSql.append("   ,cd_kaisha");
			strInsSql.append("   ,cd_busho");
			strInsSql.append("   ,id_henkou");
			strInsSql.append("   ,'wk_kenkyu' ");
			strInsSql.append("   ,'1' ");
			strInsSql.append("   ,2 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ," + userInfoData.getId_user());
			strInsSql.append("   ,GETDATE()");
			strInsSql.append("  FROM ");
			strInsSql.append("   tr_shisan_status_rireki");
			strInsSql.append("  WHERE ");
			strInsSql.append("   cd_shain = " + cd_shain);
			strInsSql.append("   AND nen = " + nen);
			strInsSql.append("   AND no_oi = " + no_oi);
			strInsSql.append("   AND no_eda = 0");
			strInsSql.append("   order by dt_henkou");
			// 枝番=0 の履歴が無かった時の対応
			strInsSql.append(" IF @@ROWCOUNT = 0    ");
			strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
			strInsSql.append("   (cd_shain ");
			strInsSql.append("   ,nen ");
			strInsSql.append("   ,no_oi ");
			strInsSql.append("   ,no_eda ");
			strInsSql.append("   ,dt_henkou");
			strInsSql.append("   ,cd_kaisha");
			strInsSql.append("   ,cd_busho");
			strInsSql.append("   ,id_henkou");
			strInsSql.append("   ,cd_zikko_ca ");
			strInsSql.append("   ,cd_zikko_li ");
			strInsSql.append("   ,st_kenkyu ");
			strInsSql.append("   ,st_seisan ");
			strInsSql.append("   ,st_gensizai ");
			strInsSql.append("   ,st_kojo ");
			strInsSql.append("   ,st_eigyo ");
			strInsSql.append("   ,id_toroku");
			strInsSql.append("   ,dt_toroku )");
			strInsSql.append(" VALUES ( ");
			strInsSql.append("    " + cd_shain );
			strInsSql.append("   ," + nen );
			strInsSql.append("   ," + no_oi );
			strInsSql.append("   ," + no_eda);
			strInsSql.append("   ,GETDATE()");
			strInsSql.append("   ," + userInfoData.getCd_kaisha());
			strInsSql.append("   ," + userInfoData.getCd_busho());
			strInsSql.append("   ," + userInfoData.getId_user());
			strInsSql.append("   ,'wk_kenkyu' ");
			strInsSql.append("   ,'1' ");
			strInsSql.append("   ,2 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ,1 ");
			strInsSql.append("   ," + userInfoData.getId_user());
			strInsSql.append("   ,GETDATE()");
			strInsSql.append(" )    ");
			// MOD end 2015/03/03 【QP@40812】No.9

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	// ADD 2013/7/2 shima【QP@30151】No.37 start
	/**
	 * 試算　試算基本情報作成SQL
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer insertSQL_sisan_kihonjoho(
			RequestResponsKindBean requestData
			,String no_eda
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			//リクエストからデータ取得
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			// ADD start 2015/03/03 【QP@40812】No.3
			String no_eda_org = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			// ADD end 2015/03/03 【QP@40812】No.3

			// SQL文の作成
			strInsSql.append(" INSERT INTO tr_shisan_kihonjoho ");
			strInsSql.append("  SELECT ");
			strInsSql.append("    cd_shain ");
			strInsSql.append("   ,nen ");
			strInsSql.append("   ,no_oi ");
			strInsSql.append("   ,seq_shisaku ");
			strInsSql.append("   ," + no_eda);
			strInsSql.append("   ,genka ");
			strInsSql.append("   ,baika ");
			strInsSql.append("   ,cd_genka_tani ");
			strInsSql.append("   ,buturyo ");
			strInsSql.append("   ,dt_hatubai ");
			strInsSql.append("   ,kikan_hanbai_sen ");
			strInsSql.append("   ,kikan_hanbai_suti ");
			strInsSql.append("   ,kikan_hanbai_tani ");
			strInsSql.append("   ,uriage_k ");
			strInsSql.append("   ,rieki_k ");
			strInsSql.append("   ,NULL ");		//製造ロット
			strInsSql.append("   ,uriage_h ");
			strInsSql.append("   ,rieki_h ");
			strInsSql.append("   ," + userInfoData.getId_user());
			strInsSql.append("   ,GETDATE()");
			strInsSql.append("   ,NULL ");
			strInsSql.append("   ,NULL ");
			// ADD 2013/9/6 okano【QP@30151】No.30 start
			strInsSql.append("   ,buturyo_suti ");
			strInsSql.append("   ,buturyo_seihin ");
			strInsSql.append("   ,buturyo_kikan ");
			// ADD 2013/9/6 okano【QP@30151】No.30 end
			strInsSql.append("  FROM ");
			strInsSql.append("   tr_shisan_kihonjoho");
			strInsSql.append("  WHERE ");
			strInsSql.append("   cd_shain = " + cd_shain);
			strInsSql.append("   AND nen = " + nen);
			strInsSql.append("   AND no_oi = " + no_oi);
			// MOD start 2015/03/03 【QP@40812】No.3
//			strInsSql.append("   AND no_eda = 0");
			strInsSql.append("   AND no_eda = " + no_eda_org);
			// MOD end 2015/03/03 【QP@40812】No.3

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}
	// ADD 2013/7/2 shima【QP@30151】No.37 end


	/**
	 * 管理結果パラメーター格納
	 *  : 担当者データの管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGroupDataKanri(RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			resTable.addFieldVale(0, "no_eda", no_eda);


		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}


