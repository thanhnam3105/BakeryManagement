package jp.co.blueflag.shisaquick.srv.logic;

import java.util.Calendar;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.SearchBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UpdateBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.base.BaseDao.DBCategory;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionBase;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 *
 * 試作データ画面 登録(製法コピー)処理
 * @author k-katayama
 * @since  2009/05/21
 */
public class SeihoNoTourokuLogic extends LogicBase{

	private SearchBaseDao searchDB_Seiho = null;
	private UpdateBaseDao execDB_Seiho = null;
	private UpdateBaseDao execDB_Log = null;

	/**
	 * コンボ用：カテゴリ情報検索DB処理用コンストラクタ
	 * : インスタンス生成
	 */
	public SeihoNoTourokuLogic() {
		//基底クラスのコンストラクタ
		super();

		//インスタンス変数の初期化
		searchDB_Seiho = null;
		execDB_Seiho = null;
	}

	/**
	 * 試作データ画面 登録(製法コピー)処理
	 * @param reqData : リクエストデータ
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

		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//ログ情報のクリア
			deleteLogInfo(reqData);

			//***** シサクイックデータ（種別、配合）を更新 **************
			shisakuUpdate(reqData);

			//***** データを取得 ***************
			//試作データの取得
			lstRecset = serchShisakuInfo(reqData, lstRecset);

			//工場マスタの取得
			Object[] itemsKojo = serchKojoMst(reqData, lstRecset);

			//製法番号の取得
			String strSeihoNo = serchSeihoNo(reqData);

			//***** 製法支援データを登録 ***************
			insertSeiho(reqData, lstRecset, itemsKojo, strSeihoNo);

			//***** シサクイックデータを更新 ***************
			insertShisaku(reqData, lstRecset, strSeihoNo);

			//リストの破棄
			removeList(lstRecset);

			//***** 製法No1～5を取得 ***************
			lstRecset = getSelectSeihoNo(reqData, lstRecset);

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//正常終了時、管理結果パラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			storageSeihoNo(resKind.getTableItem(0), lstRecset);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//リストの破棄
			removeList(lstRecset);
		}

		return resKind;
	}

	/**
	 * シサクイックデータ更新処理
	 * @param reqData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuUpdate(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		ExceptionBase excBase = null;

		StringBuffer strSQL_Ins = new StringBuffer();
		StringBuffer strSQL_Upd = new StringBuffer();
		StringBuffer strSQL_Del = new StringBuffer();
		String strTableName = "";

		Object[] items = {"","","","","","","","","","","","","","","",""};

		try {
			if (execDB != null) {
				//セッションのクローズ
				execDB.Close();
				execDB = null;
			}

			//データベース検索を用い、試作データを取得する
			super.createExecDB();
			this.execDB.BeginTran();

			//配合テーブルの削除
			strSQL_Del = shisakuHaigoDeleteSQL(reqData, strSQL_Del);

			//SQLの実行
			strTableName = "tr_haigo(delete)";
			execDB.execSQL(strSQL_Del.toString());

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, "", strTableName, "");

			strTableName = "tr_haigo(insert)";
			for (int i = 0; i < reqData.getCntRow("tr_haigo"); i++) {
				//配合テーブルの登録
				strSQL_Ins = shisakuHaigoInsertSQL(reqData.getTableItem("tr_haigo"), i);

				//SQLの実行
				execDB.execSQL(strSQL_Ins.toString());

				//ログ情報の設定
				items[6] = reqData.getFieldVale("tr_haigo", i, "cd_kotei").toString();
				items[9] = reqData.getFieldVale("tr_haigo", i, "cd_genryo").toString();

				//シサクイックシステムへのログ出力
				insertLogInfo(reqData, items, "", strTableName, "");
			}

			//20160809 製法コピーシークレット保存 ADD Start
			//試作テーブル.種別、シークレットの更新
//			strSQL_Upd = shisakuhinSyubetunoUpdateSQL(reqData, strSQL_Upd);
			strSQL_Upd = shisakuhinSyubetunoSecretUpdateSQL(reqData, strSQL_Upd);
			//20160809 製法コピーシークレット保存 ADD End

			//SQLの実行
			strTableName = "tr_shisakuhin";
			execDB.execSQL(strSQL_Upd.toString());

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, "", strTableName, "");

			//コミット
			execDB.Commit();

		} catch (Exception e) {
			//ロールバック
			execDB.Rollback();

			excBase = this.em.cnvException(e, "");

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, "", strTableName, excBase.getSystemErrorMsg());

			//更新処理失敗
			this.em.ThrowException(e, "配合、種別データ更新処理が失敗しました。");

		} finally {
			if (execDB != null) {
				//セッションのクローズ
				execDB.Close();
				execDB = null;
			}

			//変数の削除
			strSQL_Ins = null;
			strSQL_Upd = null;
			strSQL_Del = null;
		}
	}

	/**
	 * 試作品データ種別番号、シークレット更新SQL作成
	 * @param reqData : リクエストデータ
	 * @param strSql : 更新SQL
	 * @return strSql：更新SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer shisakuhinSyubetunoSecretUpdateSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//①：リクエストデータを用い、試作品データ更新用SQLを作成する。
			strSql.append(" UPDATE tr_shisakuhin");
			strSql.append(" SET no_shubetu = " + reqData.getFieldVale("table", 0, "no_shubetu"));
			//20160809 製法コピーシークレット保存 ADD Start
			strSql.append("  , flg_secret = " + toString(reqData.getFieldVale("table", 0, "flg_secret"), "NULL"));
			//20160809 製法コピーシークレット保存 ADD End
			strSql.append(" WHERE cd_shain = " + reqData.getFieldVale("table", 0, "cd_shain"));
			strSql.append("  AND nen = " + reqData.getFieldVale("table", 0, "nen"));
			strSql.append("  AND no_oi = " + reqData.getFieldVale("table", 0, "no_oi"));

		} catch (Exception e) {
			this.em.ThrowException(e, "試作品データ種別番号更新SQL作成処理が失敗しました。");

		} finally {
		}

		return strSql;
	}

	/**
	 * 配合データ削除SQL作成
	 * @param reqData : リクエストデータ
	 * @param strSql : 削除SQL
	 * @return strSql：削除SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer shisakuHaigoDeleteSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//①：リクエストデータを用い、試作品データ更新用SQLを作成する。
			strSql.append(" DELETE");
			strSql.append(" FROM tr_haigo");
			strSql.append(" WHERE cd_shain = " + reqData.getFieldVale("table", 0, "cd_shain"));
			strSql.append("  AND nen = " + reqData.getFieldVale("table", 0, "nen"));
			strSql.append("  AND no_oi = " + reqData.getFieldVale("table", 0, "no_oi"));

		} catch (Exception e) {
			this.em.ThrowException(e, "配合データ削除SQL作成処理が失敗しました。");

		} finally {
		}

		return strSql;
	}

	/**
	 * 配合データ登録SQL作成
	 * @param reqData : リクエストデータ
	 * @param i : 対象行
	 * @return strSql：登録SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer shisakuHaigoInsertSQL(
			RequestResponsTableBean reqData,
			int i)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();

		try {
			//①：リクエストデータを用い、試作品データ更新用SQLを作成する。
			strSql.append(" INSERT INTO tr_haigo (");
			strSql.append("   cd_shain");
			strSql.append("  ,nen");
			strSql.append("  ,no_oi");
			strSql.append("  ,cd_kotei");
			strSql.append("  ,seq_kotei");
			strSql.append("  ,nm_kotei");
			strSql.append("  ,zoku_kotei");
			strSql.append("  ,sort_kotei");
			strSql.append("  ,sort_genryo");
			strSql.append("  ,cd_genryo");
			strSql.append("  ,cd_kaisha");
			strSql.append("  ,cd_busho");
			strSql.append("  ,nm_genryo");
			strSql.append("  ,tanka");
			strSql.append("  ,budomari");
			strSql.append("  ,ritu_abura");
			strSql.append("  ,ritu_sakusan");
			strSql.append("  ,ritu_shokuen");
			strSql.append("  ,ritu_sousan");
			strSql.append("  ,color");
			strSql.append("  ,id_toroku");
			strSql.append("  ,dt_toroku");
			strSql.append("  ,id_koshin");
			strSql.append("  ,dt_koshin");
			strSql.append(" ) VALUES (");
			strSql.append(reqData.getFieldVale(i, "cd_shain"));
			strSql.append("  ," + reqData.getFieldVale(i, "nen"));
			strSql.append("  ," + reqData.getFieldVale(i, "no_oi"));
			strSql.append("  ," + reqData.getFieldVale(i, "cd_kotei"));
			strSql.append("  ," + reqData.getFieldVale(i, "seq_kotei"));
			strSql.append("  ,'" + reqData.getFieldVale(i, "nm_kotei") + "'");
			strSql.append("  ,'" + reqData.getFieldVale(i, "zoku_kotei") + "'");
			strSql.append("  ," + reqData.getFieldVale(i, "sort_kotei"));
			strSql.append("  ," + reqData.getFieldVale(i, "sort_genryo"));
			strSql.append("  ,'" + reqData.getFieldVale(i, "cd_genryo") + "'");
			strSql.append("  ," + reqData.getFieldVale(i, "cd_kaisha"));
			strSql.append("  ," + reqData.getFieldVale(i, "cd_busho"));
			strSql.append("  ,'" + reqData.getFieldVale(i, "nm_genryo") + "'");
			if (!reqData.getFieldVale(i, "tanka").equals("")) {
				strSql.append("  ," + reqData.getFieldVale(i, "tanka"));
			} else {
				strSql.append("  ,NULL");
			}
			if (!reqData.getFieldVale(i, "budomari").equals("")) {
				strSql.append("  ," + reqData.getFieldVale(i, "budomari"));
			} else {
				strSql.append("  ,NULL");
			}
			if (!reqData.getFieldVale(i, "ritu_abura").equals("")) {
				strSql.append("  ," + reqData.getFieldVale(i, "ritu_abura"));
			} else {
				strSql.append("  ,NULL");
			}
			if (!reqData.getFieldVale(i, "ritu_sakusan").equals("")) {
				strSql.append("  ," + reqData.getFieldVale(i, "ritu_sakusan"));
			} else {
				strSql.append("  ,NULL");
			}
			if (!reqData.getFieldVale(i, "ritu_shokuen").equals("")) {
				strSql.append("  ," + reqData.getFieldVale(i, "ritu_shokuen"));
			} else {
				strSql.append("  ,NULL");
			}
			if (!reqData.getFieldVale(i, "ritu_sousan").equals("")) {
				strSql.append("  ," + reqData.getFieldVale(i, "ritu_sousan"));
			} else {
				strSql.append("  ,NULL");
			}
			strSql.append("  ,'" + reqData.getFieldVale(i, "color") + "'");
			strSql.append("  ," + reqData.getFieldVale(i, "id_toroku"));
			strSql.append("  ,'" + reqData.getFieldVale(i, "dt_toroku") + "'");
			strSql.append("  ," + reqData.getFieldVale(i, "id_koshin"));
			strSql.append("  ,'" + reqData.getFieldVale(i, "dt_koshin") + "'");
			strSql.append(" )");

		} catch (Exception e) {
			this.em.ThrowException(e, "配合データ登録SQL作成処理が失敗しました。");

		} finally {
		}

		return strSql;
	}

	/**
	 * 試作データ取得処理
	 * @param reqData : リクエストデータ
	 * @param lstRecset : 検索結果格納リスト
	 * @return 検索結果格納リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private List<?> serchShisakuInfo(
			RequestResponsKindBean reqData,
			List<?> lstRecset
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();

		try{
			//試作データ取得処理用SQL文の作成を行う
			strSql = createSearchSQL(reqData, strSql);

			//データベース検索を用い、試作データを取得する
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//対象データが存在しない場合
			if (lstRecset.size() == 0) {
				em.ThrowException(ExceptionKind.警告Exception,"W000403", "", "", "");
			}

		}catch(Exception e){
			//検索処理失敗
			this.em.ThrowException(e, "");
		} finally {
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;
		}

		return lstRecset;
	}

	/**
	 * 配合コード取得処理
	 * @return 検索結果格納リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private String serchHaigoCd()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecsetNo = null;

		String strHaigoCd = "";

		try{
			Object itemsNo = null;;

			//セッションの設定
			searchDB_Seiho = new SearchBaseDao(DBCategory.DB2);

			//配合コード取得処理用SQL文の作成を行う
			strSql = createGetHaigoCdSQL(strSql);

			//データベース検索を用い、配合コードを取得する
			lstRecsetNo = searchDB_Seiho.dbSearch(strSql.toString());
			itemsNo = (Object) lstRecsetNo.get(0);
			strHaigoCd = itemsNo.toString();

			//配合コードが不正な場合（200000番台以外はエラー）
			if (Integer.parseInt(strHaigoCd.toString()) < 200000 || Integer.parseInt(strHaigoCd.toString()) > 299999) {
				em.ThrowException(ExceptionKind.警告Exception,"W000406", "", "", "");
			}

		}catch(Exception e){
			//検索処理失敗
			this.em.ThrowException(e, "");
		} finally {
			//リストの廃棄
			removeList(lstRecsetNo);
			if (searchDB_Seiho != null) {
				//セッションのクローズ
				searchDB_Seiho.Close();
				searchDB_Seiho = null;
			}

			//変数の削除
			strSql = null;
		}

		return strHaigoCd;
	}

	/**
	 * 製法番号取得処理
	 * @param reqData : リクエストデータ
	 * @return 検索結果格納リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private String serchSeihoNo(
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecsetNo = null;

		String strSeihoNo = "";

		try{
			Object itemsNo[] = null;

			//セッションの設定
			searchDB_Seiho = new SearchBaseDao(DBCategory.DB2);

			//製法番号取得処理用SQL文の作成を行う
			//strSql = createGetSeihoNoSQL(reqData, strSql, items[13].toString());
			strSql = createGetSeihoNoSQL(reqData, strSql, reqData.getFieldVale("table", 0, "cd_shubetu")+reqData.getFieldVale("table", 0, "no_shubetu"));

			//データベース検索を用い、製法番号を取得する
			lstRecsetNo = searchDB_Seiho.dbSearch(strSql.toString());
			itemsNo = (Object[]) lstRecsetNo.get(0);

			//製法番号の追番が不正な場合
			if (Integer.parseInt(itemsNo[1].toString()) >= 10000 || Integer.parseInt(itemsNo[1].toString()) < 1) {
				em.ThrowException(ExceptionKind.警告Exception,"W000405", "", "", "");
			} else {
				strSeihoNo = itemsNo[0].toString();
			}

		}catch(Exception e){
			//検索処理失敗
			this.em.ThrowException(e, "");
		} finally {
			//リストの廃棄
			removeList(lstRecsetNo);
			if (searchDB_Seiho != null) {
				//セッションのクローズ
				searchDB_Seiho.Close();
				searchDB_Seiho = null;
			}

			//変数の削除
			strSql = null;
		}

		return strSeihoNo;
	}

	/**
	 * 工場マスタ取得処理
	 * @param reqData : リクエストデータ
	 * @param lstRecset : 検索結果格納リスト
	 * @return 検索結果格納リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private Object[] serchKojoMst(
			RequestResponsKindBean reqData,
			List<?> lstRecset
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecsetKojo = null;

		Object[] itemsKojo = null;

		try{
			Object[] items = (Object[]) lstRecset.get(0);

			//セッションの設定
			searchDB_Seiho = new SearchBaseDao(DBCategory.DB2);

			//工場マスタ取得処理用SQL文の作成を行う
			strSql = createKojoSQL(reqData, strSql, items);

			//データベース検索を用い、工場マスタを取得する
			lstRecsetKojo = searchDB_Seiho.dbSearch(strSql.toString());
			//対象データが存在しない場合
			if (lstRecsetKojo.size() == 0) {
				em.ThrowException(ExceptionKind.警告Exception,"W000404", "", "", "");
			}
			itemsKojo = (Object[]) lstRecsetKojo.get(0);

		}catch(Exception e){
			//検索処理失敗
			this.em.ThrowException(e, "");
		} finally {
			//リストの廃棄
			removeList(lstRecsetKojo);
			if (searchDB_Seiho != null) {
				//セッションのクローズ
				searchDB_Seiho.Close();
				searchDB_Seiho = null;
			}

			//変数の削除
			strSql = null;
		}

		return itemsKojo;
	}

	/**
	 * 製法支援への登録処理
	 * @param reqData : リクエストデータ
	 * @param lstRecset : 検索結果格納リスト
	 * @return 検索結果格納リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertSeiho(
			RequestResponsKindBean reqData,
			List<?> lstRecset,
			Object[] itemsKojo,
			String strSeihoNo
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		ExceptionBase excBase = null;

		StringBuffer strSql_Ins1 = new StringBuffer();	//配合ヘッダ登録用
		StringBuffer strSql_Ins2 = new StringBuffer();	//配合明細登録用
		StringBuffer strSql_Ins3 = new StringBuffer();	//製法マスタ登録用
		StringBuffer strSql_Ins4 = new StringBuffer();	//製法伝送マスタ登録用
		StringBuffer strSql_Ins5 = new StringBuffer();	//製造ラインマスタ登録用

		String strTableName = "";
		Object[] items = null;

		try{
			//トランザクションを開始する
			execDB_Seiho = new UpdateBaseDao(DBCategory.DB2);
			this.execDB_Seiho.BeginTran();

			//***** 製法支援システムへのデータ登録 ***************
			//製法マスタ登録処理用SQL文の作成を行う
			items = (Object[]) lstRecset.get(0);
			strSql_Ins3 = createSeihoSQL(reqData, strSql_Ins3, items, strSeihoNo);

			//製法マスタ登録用SQL実行
			strTableName = "ma_seiho";
			execDB_Seiho.execSQL(strSql_Ins3.toString());

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//製法伝送マスタ登録処理用SQL文の作成を行う
			items = (Object[]) lstRecset.get(0);
			strSql_Ins4 = createSeihoDensoSQL(reqData, strSql_Ins4, items, strSeihoNo);

			//製法伝送マスタ登録用SQL実行
			strTableName = "ma_seiho_denso";
			execDB_Seiho.execSQL(strSql_Ins4.toString());

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//配合コードの取得
			String strHaigoCd = serchHaigoCd();

			//配合ヘッダ登録処理用SQL文の作成を行う
			items = (Object[]) lstRecset.get(0);
			strSql_Ins1 = createHaigoHeaderSQL(reqData, strSql_Ins1, items, itemsKojo, strHaigoCd, strSeihoNo);

			//配合ヘッダ登録用SQL実行
			strTableName = "ma_haigo_header";
			execDB_Seiho.execSQL(strSql_Ins1.toString());

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//原料データ分、明細データを登録する
			for (int i = 0; i < lstRecset.size(); i++) {
				//配合明細登録処理用SQL文の作成を行う
				items = (Object[]) lstRecset.get(i);
				strSql_Ins2 = createHaigoMeisaiSQL(reqData, items, strHaigoCd);

				//配合明細登録用SQL実行
				strTableName = "ma_haigo_meisai";
				execDB_Seiho.execSQL(strSql_Ins2.toString());

				//シサクイックシステムへのログ出力
				insertLogInfo(reqData, items, strSeihoNo, strTableName, "");
			}

			//製造ラインマスタ登録処理用SQL文の作成を行う
			items = (Object[]) lstRecset.get(0);
			strSql_Ins5 = createSeizoLineSQL(reqData, strSql_Ins5, items, itemsKojo, strHaigoCd);

			//製造ラインマスタ登録用SQL実行
			strTableName = "ma_seizo_line";
			execDB_Seiho.execSQL(strSql_Ins5.toString());

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//コミット処理を実行する
			execDB_Seiho.Commit();

		}catch(Exception e){
			//ロールバック処理を実行する
			execDB_Seiho.Rollback();

			excBase = this.em.cnvException(e, "");

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, strSeihoNo, strTableName, excBase.getSystemErrorMsg());


			//課題管理台帳No306 TT西川　2009/09/29-------------------------------- START

			//登録処理失敗
			//this.em.ThrowException(e, "製法支援への登録処理が失敗しました。");

			this.em.ThrowException(ExceptionKind.一般Exception,"E000309", "", "", "");

			//-------------------------------------------------------------------- END

		} finally {
			if (execDB_Seiho != null) {
				//セッションのクローズ
				execDB_Seiho.Close();
				execDB_Seiho = null;
			}

			//変数の削除
			strSql_Ins1 = null;
			strSql_Ins2 = null;
			strSql_Ins3 = null;
			strSql_Ins4 = null;
			strSql_Ins5 = null;
		}

	}

	/**
	 * シサクイックへの登録処理
	 * @param reqData : リクエストデータ
	 * @param lstRecset : 検索結果格納リスト
	 * @return 検索結果格納リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertShisaku(
			RequestResponsKindBean reqData,
			List<?> lstRecset,
			String strSeihoNo
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		ExceptionBase excBase = null;

		StringBuffer strSql_Upd1 = new StringBuffer();	//試作テーブル更新用
		StringBuffer strSql_Upd2 = new StringBuffer();	//試作品テーブル更新用

		String strTableName = "";
		Object[] items = null;

		try{
			//トランザクションを開始する
			super.createExecDB();
			this.execDB.BeginTran();

			items = (Object[]) lstRecset.get(0);

			//試作テーブル更新処理用SQL文の作成を行う
			strSql_Upd1 = createShisakuUpdateSQL(reqData, strSql_Upd1, strSeihoNo);

			//試作テーブル更新用SQL実行
			strTableName = "tr_shisaku";
			this.execDB.execSQL(strSql_Upd1.toString());

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//試作品テーブル更新処理用SQL文の作成を行う
			strSql_Upd2 = createShisakuhinUpdateSQL(reqData, strSql_Upd2);

			//試作品テーブル更新用SQL実行
			strTableName = "tr_shisakuhin";
			this.execDB.execSQL(strSql_Upd2.toString());

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//コミット処理を実行する
			this.execDB.Commit();

		}catch(Exception e){
			//ロールバック処理を実行する
			this.execDB.Rollback();

			excBase = this.em.cnvException(e, "");

			//シサクイックシステムへのログ出力
			insertLogInfo(reqData, items, strSeihoNo, strTableName, excBase.getSystemErrorMsg());

			//更新処理失敗
			this.em.ThrowException(e, "製法Noの更新処理が失敗しました。");

		} finally {
			if (execDB != null) {
				//セッションのクローズ
				execDB.Close();
				execDB = null;
			}

			//変数の削除
			strSql_Upd1 = null;
			strSql_Upd2 = null;
		}

	}

	/**
	 * 試作データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSearchSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String strShainCd = "";
		String strNen = "";
		String strOiNo = "";
		String strShisakuSeq = "";
		String strCommentCd = "";		//2009/08/04 ADD 課題№284-1の対応

		try {
			//パラメータの取得
			strShainCd = reqData.getFieldVale("table", 0, "cd_shain");
			strNen = reqData.getFieldVale("table", 0, "nen");
			strOiNo = reqData.getFieldVale("table", 0, "no_oi");
			strShisakuSeq = reqData.getFieldVale("table", 0, "seq_shisaku");
			//コメントコード取得	//2009/08/04 ADD 課題№284-1の対応
			strCommentCd = reqData.getFieldVale("table", 0, "cd_comment");

			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  TBL.nm_hin");
			strSql.append(" ,TBL.cd_kaisha");
			strSql.append(" ,TBL.cd_kojo");
			strSql.append(" ,TBL.id_koshin");
//			strSql.append(" ,TBL.dt_toroku");
//			strSql.append(" ,TBL.dt_koshin");
			strSql.append(" ,GETDATE()");
			strSql.append(" ,GETDATE()");
			strSql.append(" ,TBL.sort_kotei");
			strSql.append(" ,TBL.nm_kotei");
			strSql.append(" ,TBL.no_sort");
			strSql.append(" ,TBL.cd_genryo");
			strSql.append(" ,TBL.nm_genryo");
			strSql.append(" ,TBL.quantity ");
			strSql.append(" ,TBL.juryo_shiagari_g");
			strSql.append(" ,TBL.cd_shubetu");
			strSql.append(" ,TBL.nm_sample");
			strSql.append(" ,GTBL.quantity_g");
			strSql.append(" FROM (");
			strSql.append(" SELECT");
			strSql.append("  T110.nm_hin");
			strSql.append(" ,T110.cd_kaisha");
			strSql.append(" ,T110.cd_kojo");
			strSql.append(" ,T110.id_koshin");
			strSql.append(" ,T110.dt_toroku");
			strSql.append(" ,T110.dt_koshin");
			strSql.append(" ,T120.sort_kotei");
			strSql.append(" ,T120.cd_kotei");
			strSql.append(" ,T120.nm_kotei");
			strSql.append(" ,1 as no_sort");
			strSql.append(" ,'' as cd_genryo");
			strSql.append(" ,'' as nm_genryo");
			strSql.append(" ,NULL as quantity ");
			strSql.append(" ,T131.juryo_shiagari_g");
			strSql.append(" ,T110.cd_shubetu + RIGHT('00' + CONVERT(VARCHAR,T110.no_shubetu),2) as cd_shubetu");
			//strSql.append(" ,'A' + RIGHT('00' + CONVERT(VARCHAR,T110.no_shubetu),2) as cd_shubetu");
			strSql.append(" ,T131.nm_sample");
			strSql.append(" FROM");
			strSql.append("  tr_shisakuhin T110");
			strSql.append("  INNER JOIN tr_haigo T120");
			strSql.append("  ON T110.cd_shain  = T120.cd_shain ");
			strSql.append("  AND T110.nen = T120.nen");
			strSql.append("  AND T110.no_oi = T120.no_oi");
			strSql.append("  INNER JOIN tr_shisaku T131");
			strSql.append("  ON T110.cd_shain  = T131.cd_shain ");
			strSql.append("  AND T110.nen = T131.nen");
			strSql.append("  AND T110.no_oi = T131.no_oi");
			strSql.append("  AND T131.seq_shisaku = " + strShisakuSeq);
			strSql.append(" WHERE T110.cd_shain = " + strShainCd);
			strSql.append(" AND T110.nen = " + strNen);
			strSql.append(" AND T110.no_oi = " + strOiNo);
			strSql.append(" UNION");
			strSql.append(" SELECT");
			strSql.append("  T110.nm_hin");
			strSql.append(" ,T110.cd_kaisha");
			strSql.append(" ,T110.cd_kojo");
			strSql.append(" ,T110.id_koshin");
			strSql.append(" ,T110.dt_toroku");
			strSql.append(" ,T110.dt_koshin");
			strSql.append(" ,T120.sort_kotei");
			strSql.append(" ,T120.cd_kotei");
			strSql.append(" ,T120.nm_kotei");
			strSql.append(" ,Row_Number() over(partition by T120.cd_kotei order by T120.cd_kotei,T120.sort_genryo)+1 as no_sort");
			strSql.append(" ,T120.cd_genryo");
			strSql.append(" ,T120.nm_genryo");
			strSql.append(" ,T132.quantity ");
			strSql.append(" ,T131.juryo_shiagari_g");
			strSql.append(" ,T110.cd_shubetu + RIGHT('00' + CONVERT(VARCHAR,T110.no_shubetu),2) as cd_shubetu");
			strSql.append(" ,T131.nm_sample");
			strSql.append(" FROM");
			strSql.append("  tr_shisakuhin T110");
			strSql.append("  INNER JOIN tr_haigo T120");
			strSql.append("  ON T110.cd_shain  = T120.cd_shain ");
			strSql.append("  AND T110.nen = T120.nen");
			strSql.append("  AND T110.no_oi = T120.no_oi");
			strSql.append("  INNER JOIN tr_shisaku T131");
			strSql.append("  ON T110.cd_shain  = T131.cd_shain ");
			strSql.append("  AND T110.nen = T131.nen");
			strSql.append("  AND T110.no_oi = T131.no_oi");
			strSql.append("  AND T131.seq_shisaku = " + strShisakuSeq);
			strSql.append("  INNER JOIN tr_shisaku_list T132");
			strSql.append("  ON T120.cd_shain  = T132.cd_shain ");
			strSql.append("  AND T120.nen = T132.nen");
			strSql.append("  AND T120.no_oi = T132.no_oi");
			strSql.append("  AND T120.cd_kotei = T132.cd_kotei");
			strSql.append("  AND T120.seq_kotei = T132.seq_kotei");
			strSql.append("  AND T132.seq_shisaku = " + strShisakuSeq);
			strSql.append(" WHERE T110.cd_shain = " + strShainCd);
			strSql.append(" AND T110.nen = " + strNen);
			strSql.append(" AND T110.no_oi = " + strOiNo);
			strSql.append(" AND (ISNULL(T132.quantity, 0) <> 0");			//2009/08/03 ADD 課題№284-1の対応
			strSql.append(" OR T120.cd_genryo = '" + strCommentCd + "')");	//2009/08/04 ADD 課題№284-1の対応
			strSql.append(" ) TBL");
			strSql.append(" ,(SELECT SUM(ISNULL(quantity,0)) as quantity_g");
			strSql.append("   FROM tr_shisaku_list");
			strSql.append("   WHERE cd_shain = " + strShainCd);
			strSql.append("   AND nen = " + strNen);
			strSql.append("   AND no_oi = " + strOiNo);
			strSql.append("   AND seq_shisaku = " + strShisakuSeq);
			strSql.append("   GROUP BY");
			strSql.append("    cd_shain ");
			strSql.append("   ,nen ");
			strSql.append("   ,no_oi ");
			strSql.append(" ) GTBL");
			strSql.append(" ORDER BY");
			strSql.append(" TBL.sort_kotei, TBL.no_sort");

		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ取得SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 試作データ更新SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：更新SQL
	 * @param strSeihoNo：製法番号
	 * @return strSql：更新SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createShisakuUpdateSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql,
			String strSeihoNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String strShainCd = "";
		String strNen = "";
		String strOiNo = "";
		String strShisakuSeq = "";

		try {
			//パラメータの取得
			strShainCd = reqData.getFieldVale("table", 0, "cd_shain");
			strNen = reqData.getFieldVale("table", 0, "nen");
			strOiNo = reqData.getFieldVale("table", 0, "no_oi");
			strShisakuSeq = reqData.getFieldVale("table", 0, "seq_shisaku");

			//SQL文の作成
			strSql.append("UPDATE tr_shisaku SET");
			strSql.append("  no_seiho1 = '" + strSeihoNo + "'");
			strSql.append(" ,no_seiho2 = no_seiho1");
			strSql.append(" ,no_seiho3 = no_seiho2");
			strSql.append(" ,no_seiho4 = no_seiho3");
			strSql.append(" ,no_seiho5 = no_seiho4");
			strSql.append(" ,id_koshin = " + userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_shain = " + strShainCd);
			strSql.append(" AND nen = " + strNen);
			strSql.append(" AND no_oi = " + strOiNo);
			strSql.append(" AND seq_shisaku = " + strShisakuSeq);

		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ更新SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 試作品データ更新SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：更新SQL
	 * @return strSql：更新SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createShisakuhinUpdateSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String strShainCd = "";
		String strNen = "";
		String strOiNo = "";
		String strShisakuSeq = "";

		try {
			//パラメータの取得
			strShainCd = reqData.getFieldVale("table", 0, "cd_shain");
			strNen = reqData.getFieldVale("table", 0, "nen");
			strOiNo = reqData.getFieldVale("table", 0, "no_oi");
			strShisakuSeq = reqData.getFieldVale("table", 0, "seq_shisaku");

			//SQL文の作成
			strSql.append("UPDATE tr_shisakuhin SET");
			strSql.append("  seq_shisaku = " + strShisakuSeq);
			strSql.append(" ,id_koshin = " + userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_shain = " + strShainCd);
			strSql.append(" AND nen = " + strNen);
			strSql.append(" AND no_oi = " + strOiNo);

		} catch (Exception e) {
			this.em.ThrowException(e, "試作品データ更新SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 製法No1～5取得処理
	 * @param reqData：リクエストデータ
	 * @param lstRecset：検索結果リスト
	 * @return lstRecset：検索結果リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private List<?> getSelectSeihoNo(
			RequestResponsKindBean reqData,
			List<?> lstRecset)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();

		String strShainCd = "";
		String strNen = "";
		String strOiNo = "";
		String strShisakuSeq = "";

		try {
			//パラメータの取得
			strShainCd = reqData.getFieldVale("table", 0, "cd_shain");
			strNen = reqData.getFieldVale("table", 0, "nen");
			strOiNo = reqData.getFieldVale("table", 0, "no_oi");
			strShisakuSeq = reqData.getFieldVale("table", 0, "seq_shisaku");

			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  ISNULL(no_seiho1,'') as no_seiho1");
			strSql.append(" ,ISNULL(no_seiho2,'') as no_seiho2");
			strSql.append(" ,ISNULL(no_seiho3,'') as no_seiho3");
			strSql.append(" ,ISNULL(no_seiho4,'') as no_seiho4");
			strSql.append(" ,ISNULL(no_seiho5,'') as no_seiho5");
			strSql.append(" FROM");
			strSql.append("  tr_shisaku");
			strSql.append(" WHERE cd_shain = " + strShainCd);
			strSql.append(" AND nen = " + strNen);
			strSql.append(" AND no_oi = " + strOiNo);
			strSql.append(" AND seq_shisaku = " + strShisakuSeq);

			//データベース検索を用い、製法No1～5を取得する
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "製法No1～5取得処理が失敗しました。");
		} finally {
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;
		}
		return lstRecset;
	}

	/**
	 * ログ情報削除処理
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void deleteLogInfo(
			RequestResponsKindBean reqData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();

		try {
			//トランザクションの開始
			execDB_Log = new UpdateBaseDao(DBCategory.DB1);
			execDB_Log.BeginTran();

			//SQL文の作成
			strSql.append("DELETE");
			strSql.append(" FROM wk_log");
			strSql.append(" WHERE id_user = ");
			strSql.append(userInfoData.getId_user());

			//SQLの実行
			execDB_Log.execSQL(strSql.toString());

			//コミット
			execDB_Log.Commit();

		} catch (Exception e) {
			//ロールバック
			execDB_Log.Rollback();

			this.em.ThrowException(e, "ログ情報削除処理が失敗しました。");
		} finally {
			if (execDB_Log != null) {
				//セッションのクローズ
				execDB_Log.Close();
				execDB_Log = null;
			}

			//変数の削除
			strSql = null;
		}
	}

	/**
	 * ログ情報登録処理
	 * @param reqData：リクエストデータ
	 * @param strSeihoNo：製法番号
	 * @param strTableName：テーブル名
	 * @param strGenryoCd：原料コード
	 * @param strErrMsg：エラーメッセージ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertLogInfo(
			RequestResponsKindBean reqData,
			Object[] items,
			String strSeihoNo,
			String strTableName,
			String strErrMsg)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();

		String strShainCd = "";
		String strNen = "";
		String strOiNo = "";
		String strShisakuSeq = "";

		try {
			//パラメータの取得
			strShainCd = reqData.getFieldVale("table", 0, "cd_shain");
			strNen = reqData.getFieldVale("table", 0, "nen");
			strOiNo = reqData.getFieldVale("table", 0, "no_oi");
			strShisakuSeq = reqData.getFieldVale("table", 0, "seq_shisaku");

			//トランザクションの開始
			execDB_Log = new UpdateBaseDao(DBCategory.DB1);
			execDB_Log.BeginTran();

			//SQL文の作成
			strSql.append("INSERT INTO wk_log (");
			strSql.append("  id_user");
			strSql.append(" ,dt_system");
			strSql.append(" ,cd_shain");
			strSql.append(" ,nen");
			strSql.append(" ,no_oi");
			strSql.append(" ,seq_shisaku");
			strSql.append(" ,nm_sample");
			strSql.append(" ,no_seiho");
			strSql.append(" ,nm_table");
			strSql.append(" ,cd_kotei");
			strSql.append(" ,cd_genryo");
			strSql.append(" ,err_msg");
			strSql.append(" ) VALUES (");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,GETDATE()");
			strSql.append(" ," + strShainCd);
			strSql.append(" ," + strNen);
			strSql.append(" ," + strOiNo);
			strSql.append(" ," + strShisakuSeq);
			strSql.append(" ,'" + toString(items[14]) + "'");
			strSql.append(" ,'" + strSeihoNo + "'");
			strSql.append(" ,'" + strTableName + "'");
			if (strTableName.equals("ma_haigo_meisai")) {
				strSql.append(" ,'" + items[6].toString() + "'");
				strSql.append(" ,'" + items[9].toString() + "'");
			} else if (strTableName.equals("tr_haigo(insert)")) {
				strSql.append(" ,'" + items[6].toString() + "'");
				strSql.append(" ,'" + items[9].toString() + "'");
			} else {
				strSql.append(" ,''");
				strSql.append(" ,''");
			}
			strSql.append(" ,'" + strErrMsg + "'");
			strSql.append(" )");

			//SQLの実行
			execDB_Log.execSQL(strSql.toString());

			//コミット
			execDB_Log.Commit();

		} catch (Exception e) {
			//ロールバック
			execDB_Log.Rollback();

			this.em.ThrowException(e, "ログ情報登録処理が失敗しました。");
		} finally {
			if (execDB_Log != null) {
				//セッションのクローズ
				execDB_Log.Close();
				execDB_Log = null;
			}

			//変数の削除
			strSql = null;
		}
	}

	/**
	 * 配合コード取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGetHaigoCdSQL(StringBuffer strSql)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  ISNULL(MAX(cd_haigo)+1,200000) as cd_haigo");
			strSql.append(" FROM");
			strSql.append("  ma_haigo_header");

		} catch (Exception e) {
			this.em.ThrowException(e, "配合コード取得SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 製法番号取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @param strShubetuCd：種別コード
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGetSeihoNoSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql,
			String strShubetuCd)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String strNen = "";

		try {
			// 2011.01.12 mod Arai Start ------------------------------------->
			// 製法番号の年は、サーバー日付の年を使用
			//パラメータの取得
			//strNen = reqData.getFieldVale("table", 0, "nen");
			Calendar calendar = Calendar.getInstance();
			strNen = String.format("%1$ty", calendar);
			// 2011.01.12 mod Arai End --------------------------------------->

			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  RIGHT('0000' + CONVERT(VARCHAR," + userInfoData.getCd_kaisha() + "),4) + '-' + ");
			strSql.append("  '" + strShubetuCd + "-' + ");
			strSql.append("  RIGHT('00' + CONVERT(VARCHAR," + strNen + "),2)+ '-' + ");
			strSql.append("  RIGHT('0000' + CONVERT(VARCHAR,ISNULL(CONVERT(int,substring(MAX(no_seiho),13,4)),0)+1),4) as no_seiho");
			strSql.append(" ,ISNULL(CONVERT(int,substring(MAX(no_seiho),13,4)),0)+1 as no_oi");
			strSql.append(" FROM");
			strSql.append("  ma_seiho");
			strSql.append(" WHERE substring(no_seiho,1,4) = RIGHT('0000' + CONVERT(VARCHAR," + userInfoData.getCd_kaisha() + "),4)");
			strSql.append(" AND substring(no_seiho,6,3) = '" + strShubetuCd + "'");
			strSql.append(" AND substring(no_seiho,10,2) = RIGHT('00' + CONVERT(VARCHAR,'" + strNen + "'),2)");

		} catch (Exception e) {
			this.em.ThrowException(e, "製法番号取得SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 工場マスタ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @param items：試作データの検索結果
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createKojoSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql,
			Object[] items)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  budomari");
			strSql.append(" ,qty_kihon");
			strSql.append(" ,ritsu_kihon");
			strSql.append(" ,cd_setsubi");
			strSql.append(" ,qty_max");

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.11
			//strSql.append(" ,hijyu");
			strSql.append(" ,1 AS hijyu");
//mod end ---------------------------------------------------------------------------------
			strSql.append(" ,cd_line");
			strSql.append(" FROM");
			strSql.append("  ma_kojyo");
			strSql.append(" WHERE cd_kaisha = " + items[1].toString());
			strSql.append(" AND cd_kojyo = " + items[2].toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "工場マスタ取得SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 配合ヘッダ登録SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：登録SQL
	 * @param items：試作データの検索結果
	 * @param itemsKojo：工場マスタの検索結果
	 * @param strHaigoCd：配合コード取得結果
	 * @param strSeihoNo：製法番号
	 * @return strSql：登録SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createHaigoHeaderSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql,
			Object[] items,
			Object[] itemsKojo,
			String strHaigoCd,
			String strSeihoNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//SQL文の作成
			strSql.append("INSERT INTO ma_haigo_header (");
			strSql.append("  cd_haigo");
			strSql.append(" ,nm_haigo");
			strSql.append(" ,nm_haigo_r");
			strSql.append(" ,cd_kaisha_daihyo");
			strSql.append(" ,cd_kojyo_daihyo");
			strSql.append(" ,kbn_hin");
			strSql.append(" ,cd_bunrui");
			strSql.append(" ,budomari");
			strSql.append(" ,qty_kihon");
			strSql.append(" ,ritsu_kihon");
			strSql.append(" ,cd_setsubi");
			strSql.append(" ,flg_gasan");
			strSql.append(" ,qty_max");
			strSql.append(" ,qty_haigo_kei");
			strSql.append(" ,biko");
			strSql.append(" ,no_seiho");
			strSql.append(" ,cd_kaisha");
			strSql.append(" ,kbn_vw");
			strSql.append(" ,hijyu");
			strSql.append(" ,flg_mishiyo");
			strSql.append(" ,kbn_haishi");
			strSql.append(" ,kbn_shiagari");
			strSql.append(" ,status");
			strSql.append(" ,cd_seiho_bunrui");
			strSql.append(" ,no_seiho_sanko");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,cd_toroku_kaisha");
			strSql.append(" ,cd_toroku");
			strSql.append(" ,dt_henko");
			strSql.append(" ,cd_koshin_kaisha");
			strSql.append(" ,cd_koshin");
			strSql.append(" ,cd_haigo_sanko");
			strSql.append(" ,cd_dcp_aoh");
			strSql.append(" ,cd_mxt_aoh");
			strSql.append(" ,kbn_cnv_aoh");
			strSql.append(" ) VALUES (");
			strSql.append("  " + strHaigoCd);

			if (items[0].toString().getBytes().length > 60) {
				//*** 品名のバイト数が60バイトを超えている場合 ***
				String strHinName = "";

				//先頭から60バイトを設定する
				for (int i = 0; i < 60; i++) {
					if (strHinName.getBytes().length < 60) {
						//品名変数のバイト数が59バイトで次の文字が2バイトの場合
						if (strHinName.getBytes().length == 59 &&
							items[0].toString().substring(i, i+1).getBytes().length == 2) {
							//変数に格納すると61バイトになるので、変数に格納せずにFor文を抜ける
							break;
						}

						//品名変数へ1文字格納する
						strHinName = strHinName + items[0].toString().substring(i, i+1);
					} else {
						//For文を抜ける
						break;
					}
				}
				strSql.append(" ,'" + strHinName + "'");

			} else {
				//*** 品名のバイト数が60バイト以下の場合 ***
				//登録されている品名を設定する
				strSql.append(" ,'" + items[0].toString() + "'");
			}

			strSql.append(" ,NULL");
			strSql.append(" ," + items[1].toString());
			strSql.append(" ," + items[2].toString());
			strSql.append(" ,3");
			strSql.append(" ,NULL");
			strSql.append(" ," + itemsKojo[0].toString());//工場マスタ：budomari
			strSql.append(" ," + itemsKojo[1].toString());//工場マスタ：qty_kihon
			strSql.append(" ," + itemsKojo[2].toString());//工場マスタ：ritsu_kihon
			strSql.append(" ," + itemsKojo[3].toString());//工場マスタ：cd_setsubi
			strSql.append(" ,0");
			strSql.append(" ," + itemsKojo[4].toString());//工場マスタ：qty_max
			if (items[12] != null) {
				//仕上がり重量が入力されている場合
				strSql.append(" ," + items[12].toString());//仕上がり重量
			} else {
				//仕上がり重量が入力されていない場合
				strSql.append(" ," + items[15].toString());//配合の合計重量
			}
			strSql.append(" ,NULL");
			strSql.append(" ,'" + strSeihoNo + "'");
			strSql.append(" ," + userInfoData.getCd_kaisha());
			strSql.append(" ,'04'");
			strSql.append(" ," + itemsKojo[5].toString());//工場マスタ：hijyu
			strSql.append(" ,0");
			strSql.append(" ,0");
			if (items[12] != null) {
				//仕上がり重量が入力されている場合
				strSql.append(" ,1");
			} else {
				//仕上がり重量が入力されていない場合
				strSql.append(" ,0");
			}
			strSql.append(" ,0");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,'" + items[4].toString() + "'");
			strSql.append(" ," + userInfoData.getCd_kaisha());
//			strSql.append(" ,'" + userInfoData.getId_user() + "'");
			strSql.append(" ,right('0000000000' + '" + userInfoData.getId_user() + "',10)");
			strSql.append(" ,'" + items[5].toString() + "'");
			strSql.append(" ," + userInfoData.getCd_kaisha());
//			strSql.append(" ,'" + userInfoData.getId_user() + "'");
			strSql.append(" ,right('0000000000' + '" + userInfoData.getId_user() + "',10)");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" )");


		} catch (Exception e) {
			this.em.ThrowException(e, "配合ヘッダ登録SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 配合明細登録SQL作成
	 * @param reqData：リクエストデータ
	 * @param items：試作データの検索結果
	 * @param strHaigoCd：配合コード取得結果
	 * @return strSql：登録SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createHaigoMeisaiSQL(
			RequestResponsKindBean reqData,
			Object[] items,
			String strHaigoCd)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();

		try {

			//コメントコード取得
			String commentCd = reqData.getFieldVale("table", 0, "cd_comment");
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.11
			//製法DB.ma_genryoを検索　結果取得（キー項目は配合テーブル.cd_hin）
			String strHinCd = toString(items[9]);
			String strkaishaCd = toString(items[1]);
			String strkojoCd = toString(items[2]);

			Object[] itemsGenryo = null;

			if (!items[8].toString().equals("1")) {
				//工程ではない場合
				itemsGenryo = serchSeihoGenryo(strHinCd,strkaishaCd,strkojoCd);

			}
			//対象原料が存在した場合、品名・荷姿・歩留を取得
			//String strHinNm = items[10].toString();		//配合テーブル.原料名
			String strHinNm = "NULL";		//配合テーブル.原料名
			String strNisugata = "NULL";				//配合テーブル.荷姿
			String strBudomari = "NULL";				//配合テーブル.歩留
			if (itemsGenryo != null && itemsGenryo.length != 0) {
				//品コードマッチ
				//strHinNm = itemsGenryo[1].toString();		//ma_genryo.原料名
				strHinNm = toString(itemsGenryo[1],"NULL");		//ma_genryo.原料名
				//strNisugata = itemsGenryo[3].toString();	//ma_genryo.荷姿
				strNisugata = toString(itemsGenryo[3],"NULL");	//ma_genryo.荷姿
				//strBudomari = itemsGenryo[4].toString();	//ma_genryo.歩留
				strBudomari = toString(itemsGenryo[4],"NULL");	//ma_genryo.歩留

			// 20160905 ADD KPX@1600766 Start
			} else if (!strHinCd.equals(commentCd)){
				// 品コードアンマッチ、且つコメントコードでない場合
				strBudomari = "100";							//ma_genryo.歩留
			}
			// 20160905 ADD KPX@1600766 End
//add end --------------------------------------------------------------------------------------

			//SQL文の作成
			strSql.append("INSERT INTO ma_haigo_meisai (");
			strSql.append("  cd_haigo");
			strSql.append(" ,no_kotei");
			strSql.append(" ,no_tonyu");
			strSql.append(" ,cd_hin");
			strSql.append(" ,flg_shitei");
			strSql.append(" ,kbn_hin");
			strSql.append(" ,kbn_shikakari");
			strSql.append(" ,nm_hin");
			strSql.append(" ,cd_mark");
			strSql.append(" ,qty_haigo");
			strSql.append(" ,qty_nisugata");
			strSql.append(" ,gosa");
			strSql.append(" ,budomari");
			strSql.append(" ,kbn_bunkatsu");
			strSql.append(" ) VALUES (");
			strSql.append("  " + strHaigoCd);
			strSql.append(" ," + items[6].toString());
			strSql.append(" ," + items[8].toString()); //工程No
			//行Noが"1"の場合
			if (items[8].toString().equals("1")) {
				//工程
				strSql.append(" ,999999");

				//strSql.append(" ," + commentCd);

				strSql.append(" ,0");
				strSql.append(" ,9");
				strSql.append(" ,NULL");
				strSql.append(" ,'" + items[7].toString() + "'");
				strSql.append(" ,18");
				strSql.append(" ,NULL");
				strSql.append(" ,NULL");
				strSql.append(" ,NULL");
				strSql.append(" ,NULL");
			} else {
//				//原料
//				strSql.append(" ," + items[9].toString());
//				strSql.append(" ,0");
//				strSql.append(" ,1");
//				strSql.append(" ,0");
//				strSql.append(" ,'" + items[10].toString() + "'");
//				strSql.append(" ,0");
//				if (items[11] != null) {
//					strSql.append(" ," + items[11].toString());
//				} else {
//					strSql.append(" ,NULL");
//				}
//				strSql.append(" ,NULL");
//				strSql.append(" ,NULL");
//				strSql.append(" ,100");

				//if (items[9].toString().equals("999999")) {
				if (items[9].toString().equals(commentCd)) {
					//コメント
					//strSql.append(" ," + items[9].toString());
					strSql.append(" ,999999");
					strSql.append(" ,0");
					strSql.append(" ,1");
					strSql.append(" ,0");
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.11
//					strSql.append(" ,'items[10].toString()'");
					strSql.append(" ,'" + strHinNm + "'");	//原料名
					strSql.append(" ,0");
					strSql.append(" ,NULL");
//					strSql.append(" ,NULL");
					strSql.append(" ," + strNisugata);		//荷姿
					strSql.append(" ,NULL");
//					strSql.append(" ,NULL");
					strSql.append(" ," + strBudomari);		//歩留
				} else {
					//原料
					strSql.append(" ," + items[9].toString());
					strSql.append(" ,0");
					strSql.append(" ,1");
					strSql.append(" ,0");
//					strSql.append(" ,'items[10].toString()'");
					strSql.append(" ,'" + strHinNm + "'");	//原料名
					strSql.append(" ,0");
					if (items[11] != null) {
						strSql.append(" ," + items[11].toString());
					} else {
						strSql.append(" ,NULL");
					}
//					strSql.append(" ,NULL");
					strSql.append(" ," + strNisugata);		//荷姿
					strSql.append(" ,NULL");
					// 20160905 MOD KPX@1600766 Start
//					strSql.append(" ,100");
					strSql.append(" ," + strBudomari);		//歩留
					// 20160905 MOD KPX@1600766 End

//mod end --------------------------------------------------------------------------------------
				}
			}
			strSql.append(" ,0");
			strSql.append(" )");


		} catch (Exception e) {
			this.em.ThrowException(e, "配合明細登録SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 製法マスタ登録SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：登録SQL
	 * @param items：試作データの検索結果
	 * @param strSeihoNo：製法番号
	 * @return strSql：登録SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSeihoSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql,
			Object[] items,
			String strSeihoNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//SQL文の作成
			strSql.append("INSERT INTO ma_seiho (");
			strSql.append("  no_seiho");
			strSql.append(" ,nm_seiho");
			strSql.append(" ,dt_seiho_sakusei");
			strSql.append(" ,nm_seiho_sakusei_1");
			strSql.append(" ,nm_seiho_sakusei_2");
			strSql.append(" ,nm_seiho_bunsho_before");
			strSql.append(" ,cd_shinsei_tanto_kaisha");
			strSql.append(" ,cd_shinsei_tanto");
			strSql.append(" ,nm_seiho_sekinin");
			strSql.append(" ,dt_seiho_shinsei");
			strSql.append(" ) VALUES (");
			strSql.append("  '" + strSeihoNo + "'");

			if (items[0].toString().getBytes().length > 60) {
				//*** 品名のバイト数が60バイトを超えている場合 ***
				String strHinName = "";

				//先頭から60バイトを設定する
				for (int i = 0; i < 60; i++) {
					if (strHinName.getBytes().length < 60) {
						//品名変数のバイト数が59バイトで次の文字が2バイトの場合
						if (strHinName.getBytes().length == 59 &&
							items[0].toString().substring(i, i+1).getBytes().length == 2) {
							//変数に格納すると61バイトになるので、変数に格納せずにFor文を抜ける
							break;
						}

						//品名変数へ1文字格納する
						strHinName = strHinName + items[0].toString().substring(i, i+1);
					} else {
						//For文を抜ける
						break;
					}
				}
				strSql.append(" ,'" + strHinName + "'");

			} else {
				//*** 品名のバイト数が60バイト以下の場合 ***
				//登録されている品名を設定する
				strSql.append(" ,'" + items[0].toString() + "'");
			}

			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" )");

		} catch (Exception e) {
			this.em.ThrowException(e, "製法マスタ登録SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 製法伝送マスタ登録SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：登録SQL
	 * @param items：試作データの検索結果
	 * @param strSeihoNo：製法番号
	 * @return strSql：登録SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSeihoDensoSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql,
			Object[] items,
			String strSeihoNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//SQL文の作成
			strSql.append("INSERT INTO ma_seiho_denso (");
			strSql.append("  no_seiho");
			strSql.append(" ,cd_kaisha");
			strSql.append(" ,cd_kojyo");
			strSql.append(" ,flg_denso_taisho");
			strSql.append(" ,flg_denso_jyotai");
			strSql.append(" ,dt_denso_toroku");
			strSql.append(" ,dt_denso_kanryo");
			strSql.append(" ,flg_daihyo_kojyo");
			strSql.append(" ,cd_denso_tanto_kaisha");
			strSql.append(" ,cd_denso_tanto");
			strSql.append(" ,biko");
			strSql.append(" ) VALUES (");
			strSql.append("  '" + strSeihoNo + "'");
			//strSql.append(" ," + userInfoData.getCd_kaisha());
			strSql.append(" ," + items[1].toString());
			strSql.append(" ," + items[2].toString());
			strSql.append(" ,0");
			strSql.append(" ,0");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,1");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" )");


		} catch (Exception e) {
			this.em.ThrowException(e, "製法伝送マスタ登録SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 製造ラインマスタ登録SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：登録SQL
	 * @param items：試作データの検索結果
	 * @param itemsKojo：工場マスタの検索結果
	 * @param strHaigoCd：配合コード取得結果
	 * @return strSql：登録SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSeizoLineSQL(
			RequestResponsKindBean reqData,
			StringBuffer strSql,
			Object[] items,
			Object[] itemsKojo,
			String strHaigoCd)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//SQL文の作成
			strSql.append("INSERT INTO ma_seizo_line (");
			strSql.append("  cd_haigo");
			strSql.append(" ,no_yusen");
			strSql.append(" ,cd_line");
			strSql.append(" ,dt_henko");
			strSql.append(" ,cd_koshin_kaisha");
			strSql.append(" ,cd_koshin");
			strSql.append(" ) VALUES (");
			strSql.append("  " + strHaigoCd);
			strSql.append(" ,1");
			strSql.append(" ," + itemsKojo[6].toString());//工場マスタ：cd_line
			strSql.append(" ,GETDATE()");
			strSql.append(" ," + userInfoData.getCd_kaisha());
//			strSql.append(" ,'" + items[3].toString() + "'");
			strSql.append(" ,right('0000000000' + '" + userInfoData.getId_user() + "',10)");
			strSql.append(" )");


		} catch (Exception e) {
			this.em.ThrowException(e, "製造ラインマスタ登録SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : 情報をレスポンスデータへ格納する。
	 * @param resTable : レスポンステーブル情報
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageSeihoNo(
			RequestResponsTableBean resTable,
			List<?> lstRecset)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

			if (lstRecset.size() != 0) {
				Object[] items = (Object[]) lstRecset.get(0);

				//製法No1～5を設定
				resTable.addFieldVale(0, "no_seiho1", items[0].toString());
				resTable.addFieldVale(0, "no_seiho2", items[1].toString());
				resTable.addFieldVale(0, "no_seiho3", items[2].toString());
				resTable.addFieldVale(0, "no_seiho4", items[3].toString());
				resTable.addFieldVale(0, "no_seiho5", items[4].toString());
			} else {
				//データが存在しない場合は空白を設定する
				resTable.addFieldVale(0, "no_seiho1", "");
				resTable.addFieldVale(0, "no_seiho2", "");
				resTable.addFieldVale(0, "no_seiho3", "");
				resTable.addFieldVale(0, "no_seiho4", "");
				resTable.addFieldVale(0, "no_seiho5", "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "パラメーター格納処理が失敗しました。");

		} finally {

		}
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.11
	/**
	 * 製法.原料取得SQL作成
	 * @param strHinCd：品コード
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGetSeihoGenryoSQL(String strHinCd,String strkaishaCd,String strkojoCd)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer strSql = new StringBuffer();

		try {
			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  cd_hin");			//品コード
			strSql.append(" ,nm_hin");			//品名
			strSql.append(" ,hijyu");			//比重
			strSql.append(" ,qty");				//荷姿
			strSql.append(" ,budomari");		//歩留
			strSql.append(" FROM ma_genryo");
			strSql.append(" WHERE");
			strSql.append("  cd_hin = " + strHinCd);
			strSql.append("  and cd_kaisha = " + strkaishaCd);
			strSql.append("  and cd_kojyo = " + strkojoCd);

		} catch (Exception e) {
			this.em.ThrowException(e, "製法番号取得SQL作成処理が失敗しました。");
		} finally {
		}
		return strSql;
	}

	/**
	 * 製法.原料取得処理
	 * @param reqData : リクエストデータ
	 * @return 検索結果格納リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private Object[] serchSeihoGenryo(String strHinCd,String strkaishaCd,String strkojoCd)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecsetNo = null;

		Object[] itemsSeihoGenryo = null;

		try{
			//品コード存在した場合
			if (!strHinCd.equals("")) {
				//セッションの設定
				searchDB_Seiho = new SearchBaseDao(DBCategory.DB2);

				//SQL文の作成を行う
				strSql = createGetSeihoGenryoSQL(strHinCd,strkaishaCd,strkojoCd);

				//データベース検索を用い、製法DB.原料情報を取得する
				lstRecsetNo = searchDB_Seiho.dbSearch(strSql.toString());
				if (lstRecsetNo.size() != 0) {
					itemsSeihoGenryo = (Object[])lstRecsetNo.get(0);
				}
			}

		}catch(Exception e){
			//検索処理失敗
			this.em.ThrowException(e, "");
		} finally {
			//リストの廃棄
			removeList(lstRecsetNo);
			if (searchDB_Seiho != null) {
				//セッションのクローズ
				searchDB_Seiho.Close();
				searchDB_Seiho = null;
			}

			//変数の削除
			strSql = null;
		}

		return itemsSeihoGenryo;
	}
//add end --------------------------------------------------------------------------------------

}
