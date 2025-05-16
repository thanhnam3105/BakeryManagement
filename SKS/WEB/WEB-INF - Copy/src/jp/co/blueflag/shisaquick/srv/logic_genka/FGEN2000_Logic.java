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
 * 【QP@00342】
 * ステータス履歴　検索処理
 *  : ステータス履歴画面の情報を取得する。
 *  機能ID：FGEN2000
 *
 * @author Nishigawa
 * @since  2011/01/24
 */
public class FGEN2000_Logic extends LogicBase{

	/**
	 * ステータス履歴　検索処理
	 * : インスタンス生成
	 */
	public FGEN2000_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * ステータス履歴　検索処理
	 *  : ステータス履歴画面の情報を取得する。
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

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//レスポンスデータの形成
			this.genkaKihonSetting(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除

		}
		return resKind;

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting（レスポンスデータの形成）
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * レスポンスデータの形成
	 * @param resKind : レスポンスデータ
	 * @param reqData : リクエストデータ
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

		//レコード値格納リスト
		List<?> lstRecset = null;

		//レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {

			//テーブル名
			String strTblNm = "kihon";

			//①データ取得SQL作成
			strSqlBuf = this.createGenkaKihonSQL(reqData);

			//②共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//検索結果がない場合
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000407", strSqlBuf.toString(), "", "");
			}

			//③レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			//④追加したテーブルへレコード格納
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "ステータス履歴　データ検索処理が失敗しました。");

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


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL（SQL文生成）
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaKihonSQL(

			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL格納用
		StringBuffer strSql = new StringBuffer();

		try {
			//リクエストからデータ抽出
			String strCdShain = toString(reqData.getFieldVale(0, 0, "cd_shain"));
			String strNen = toString(reqData.getFieldVale(0, 0, "nen"));
			String strNoOi = toString(reqData.getFieldVale(0, 0, "no_oi"));
			String strNoEda = toString(reqData.getFieldVale(0, 0, "no_eda"));
			String strNoPage = reqData.getFieldVale(0, 0, "no_page");

			//リクエストのキー項目のいずれかが""の場合
			if(strCdShain.equals("") || strNen.equals("") || strNoOi.equals("") || strNoEda.equals("")){
				//userInfoDataから取得
				strCdShain = toString(userInfoData.getMovement_condition().get(0));
				strNen = toString(userInfoData.getMovement_condition().get(1));
				strNoOi = toString(userInfoData.getMovement_condition().get(2));
				strNoEda = toString(userInfoData.getMovement_condition().get(3));
			}

			//コンストファイルからデータ取得
			String strListRowMax =
				ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			//SQL文の作成
			strSql.append(" SELECT  ");
			strSql.append("  tbl.no_row  "); 				// 0　行番号
			strSql.append(" ,tbl.cd_shain  ");			// 1　試作No_社員CD
			strSql.append(" ,tbl.nen   ");					// 2　試作No_年
			strSql.append(" ,tbl.no_oi    ");				// 3　試作No_追番
			strSql.append(" ,tbl.no_eda   ");				// 4　試作No_枝番
			strSql.append(" ,tbl.dt_henkou_ymd  ");	// 5　変更日時（年月日）
			strSql.append(" ,tbl.dt_henkou_hmd  ");	// 6　変更日時（時分秒）
			strSql.append(" ,tbl.nm_user  ");			// 7　ユーザ名
			strSql.append(" ,tbl.nm_category  ");		// 8　カテゴリ名
			strSql.append(" ,tbl.nm_ｌiteral  ");			// 9　リテラル名
			strSql.append(" ,tbl.st_kenkyu  ");			// 10　研究所ステータス
			strSql.append(" ,tbl.st_seisan  ");			// 11　生産管理部ステータス
			strSql.append(" ,tbl.st_gensizai  ");			// 12　原資材調達部ステータス
			strSql.append(" ,tbl.st_kojo  ");				// 13　工場ステータス
			strSql.append(" ,tbl.st_eigyo  ");			// 14　営業ステータス
			strSql.append(" ,cnttbl.max_row  ");		// 15　全件数
			strSql.append(" ,tbl.cd_zikko_li  ");		// 16　リテラル番号 ADD 【H24年度対応】 2012/04/16 青木
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
			strSql.append(" 	,l1.nm_ｌiteral  ");
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
			//strSql.append(" 	 AND T1.cd_zikko_li <> 0");  //仮保存以外 //DEL 【H24年度対応】 2012/04/16 青木
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
			strSql.append(" 	    ,l1.nm_ｌiteral  ");
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
			//strSql.append(" 	     AND T1.cd_zikko_li <> 0");  //仮保存以外 //DEL 【H24年度対応】 2012/04/16 青木
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
	//                                                                  storageData（パラメーター格納）
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * パラメーター格納
	 *  : ステータス履歴画面へのレスポンスデータへ格納する。
	 * @param lstGenkaHeader : 検索結果情報リスト
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

			//コンストファイルからデータ取得
			String strListRowMax =
				ConstManager.getConstValue(ConstManager.Category.設定,"LIST_ROW_MAX");

			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//結果をレスポンスデータに格納
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
				resTable.addFieldVale(i, "cd_zikko_li", toString(items[16],"")); //ADD 【H24年度対応】 2012/04/16 青木
				resTable.addFieldVale(i, "roop_cnt", Integer.toString(lstGenkaHeader.size()));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
