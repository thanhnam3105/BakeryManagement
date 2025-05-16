package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseCSV;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 原料データCSVを生成する
 * @author jinbo
 * @since  2009/05/13
 */
public class GenryoDataCSVLogic extends LogicBaseCSV {
	
	/**
	 * コンストラクタ
	 */
	public GenryoDataCSVLogic() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
	}
	
	/**
	 * 原料データCSVを生成する
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

		//レスポンスデータ（機能）
		RequestResponsKindBean ret = null;
		//検索データ
		List<?> lstRecset = null;
		//CSVファイル出力先パス
		String strFilePath = "";
		
		try {
			//DB検索
			super.createSearchDB();
			lstRecset = getData(reqData);
			
			//CSVファイル生成
			strFilePath = CSVOutput("原料マスタ", lstRecset);
			
			//レスポンスデータ生成
			ret = storageGenryoDataCSV(strFilePath);
			
		} catch (Exception e) {
			em.ThrowException(e, "原料データCSVの生成に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//DBセッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
		}
		return ret;

	}
	
	/**
	 * レスポンスデータを生成する
	 * @param DownLoadPath : ファイルパス生成ファイル格納先(ダウンロードパラメータ)
	 * @return RequestResponsKindBean : レスポンスデータ（機能）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private RequestResponsKindBean storageGenryoDataCSV(String DownLoadPath) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsKindBean ret = null;
		
		try {
			//レスポンスを生成する
			ret = new RequestResponsKindBean();
			//機能IDを設置する
			ret.setID("SA360");
			
			//ファイルパス	生成ファイル格納先
			ret.addFieldVale(0, 0, "URLValue", DownLoadPath);
			//処理結果①	成功可否
			ret.addFieldVale(0, 0, "flg_return", "true");
			//処理結果②	メッセージ
			ret.addFieldVale(0, 0, "msg_error", "");
			//処理結果③	処理名称
			ret.addFieldVale(0, 0, "no_errmsg", "");
			//処理結果⑥	メッセージ番号
			ret.addFieldVale(0, 0, "nm_class", "");
			//処理結果④	エラーコード
			ret.addFieldVale(0, 0, "cd_error", "");
			//処理結果⑤	システムメッセージ
			ret.addFieldVale(0, 0, "msg_system", "");
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}

		/**
	 * 対象の原料データを検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//検索結果
		List<?> ret = null;
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//SQL文の作成
			strSql = genryoDataCSVCreateSQL(KindBean);
			
			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (ret.size() == 1){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "原料データ、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;
		}
		return ret;
		
	}
	
	/**
	 * リクエストデータより、原料データ検索SQLを生成する
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 原料データ検索SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer genryoDataCSVCreateSQL(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();

		try {

			String strJokenKbn1 = "";
			String strJokenKbn2 = "";
			String strCd_Genryo = "";
			String strName_Genryo = "";
			String strCd_Kaisha = "";
			String strCd_Kojo = "";
			String dataId = null;
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			String strShiyoFlg = "";
//add end --------------------------------------------------------------------------------------
			
			//対象原料（新規原料）の取得
			strJokenKbn1 = reqData.getFieldVale(0, 0, "kbn_joken1");
			//対象原料（既存原料）の取得
			strJokenKbn2 = reqData.getFieldVale(0, 0, "kbn_joken2");
			//原料コードの取得
			strCd_Genryo = reqData.getFieldVale(0, 0, "cd_genryo");
			//原料名の取得
			strName_Genryo = reqData.getFieldVale(0, 0, "nm_genryo");
			//会社コード
			strCd_Kaisha = reqData.getFieldVale(0, 0, "cd_kaisha");
			//工場コード
			strCd_Kojo = reqData.getFieldVale(0, 0, "cd_kojo");
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			//使用実績フラグ
			strShiyoFlg = reqData.getFieldVale(0, 0, "flg_shiyo");
//add end --------------------------------------------------------------------------------------

			String strSqlTanto = "SELECT Shin.cd_kaisha FROM tr_shisakuhin Shin JOIN ma_tantokaisya Tanto ON Shin.cd_kaisha = Tanto.cd_tantokaisha AND Tanto.id_user = " + userInfoData.getId_user();
	
			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("25")){
					//原料マスタＣＳＶ画面のデータIDを設定
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL文の作成	
			ret.append(" SELECT ");
			ret.append("  cd_genryo ");
			ret.append(" , nm_genryo ");
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			ret.append(" , flg_shiyo ");
			ret.append(" , flg_mishiyo ");
//add end --------------------------------------------------------------------------------------
			ret.append(" , ritu_sakusan ");
			ret.append(" , ritu_shokuen ");
			ret.append(" , ritu_sousan ");
			ret.append(" , ritu_abura ");
			ret.append(" , hyojian ");
			ret.append(" , tenkabutu ");
			ret.append(" , memo ");
			ret.append(" , no_eiyo1 ");
			ret.append(" , wariai1 ");
			ret.append(" , no_eiyo2 ");
			ret.append(" , wariai2 ");
			ret.append(" , no_eiyo3 ");
			ret.append(" , wariai3 ");
			ret.append(" , no_eiyo4 ");
			ret.append(" , wariai4 ");
			ret.append(" , no_eiyo5 ");
			ret.append(" , wariai5 ");
			ret.append(" , dt_konyu ");
			ret.append(" , cd_kakutei ");
			ret.append(" , nm_haishi ");
			ret.append(" FROM (");
			ret.append(" SELECT ");
			ret.append("  0 AS kbn ");
			ret.append(" , '原料コード' AS cd_genryo ");
			ret.append(" , '原料名' AS nm_genryo ");
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			ret.append(" , '"+ ConstManager.getConstValue(Category.設定, "NM_SHIYO_JISEKI") +"' AS flg_shiyo ");
			ret.append(" , '未使用' AS flg_mishiyo ");
//add end --------------------------------------------------------------------------------------
			ret.append(" , '酢酸(%)' AS ritu_sakusan ");
			ret.append(" , '食塩(%)' AS ritu_shokuen ");
			ret.append(" , '総酸(%)' AS ritu_sousan ");
			ret.append(" , '油含有率(%)' AS ritu_abura ");
			ret.append(" , '表示案' AS hyojian ");
			ret.append(" , '添加物' AS tenkabutu ");
			ret.append(" , 'メモ' AS memo ");
			ret.append(" , '栄養計算食品番号1' AS no_eiyo1 ");
			ret.append(" , '割合1(%)' AS wariai1 ");
			ret.append(" , '栄養計算食品番号2' AS no_eiyo2 ");
			ret.append(" , '割合2(%)' AS wariai2 ");
			ret.append(" , '栄養計算食品番号3' AS no_eiyo3 ");
			ret.append(" , '割合3(%)' AS wariai3 ");
			ret.append(" , '栄養計算食品番号4' AS no_eiyo4 ");
			ret.append(" , '割合4(%)' AS wariai4 ");
			ret.append(" , '栄養計算食品番号5' AS no_eiyo5 ");
			ret.append(" , '割合5(%)' AS wariai5 ");
			ret.append(" , '最終購入日' AS dt_konyu ");
			ret.append(" , '確定コード' AS cd_kakutei ");
			ret.append(" , '廃止区' AS nm_haishi ");
			ret.append(" UNION ");

			ret.append("SELECT");
			ret.append("  1 AS kbn ");
//			ret.append(" ,A.cd_kaisha");
			ret.append(" ,'\"' + REPLACE(A.cd_genryo,'\"','\"\"') + '\"' AS cd_genryo");
			ret.append(" ,'\"' + REPLACE(A.nm_genryo,'\"','\"\"') + '\"' AS nm_genryo");
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			ret.append(" ,CASE LEFT(A.cd_genryo,1) WHEN 'N' THEN '-' ELSE " 
							+ "(CASE ISNULL(A.flg_shiyo,0) WHEN 0 THEN '×' WHEN 1 THEN '○' END) END flg_shiyo");
			ret.append(" ,CASE LEFT(A.cd_genryo,1) WHEN 'N' THEN '' ELSE " 
							+ "(CASE ISNULL(A.flg_mishiyo,0) WHEN 0 THEN '' WHEN 1 THEN '○' END) END flg_mishiyo");
//add end --------------------------------------------------------------------------------------
			ret.append(" ,ISNULL(CONVERT(varchar,B.ritu_sakusan),'') as ritu_sakusan");
			ret.append(" ,ISNULL(CONVERT(varchar,B.ritu_shokuen),'') as ritu_shokuen");
			ret.append(" ,ISNULL(CONVERT(varchar,B.ritu_sousan),'') as ritu_sousan");
			ret.append(" ,ISNULL(CONVERT(varchar,B.ritu_abura),'') as ritu_abura");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.hyojian,''),'\"','\"\"') + '\"' as hyojian");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.tenkabutu,''),'\"','\"\"') + '\"' as tenkabutu");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.memo,''),'\"','\"\"') + '\"' as memo");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.no_eiyo1,''),'\"','\"\"') + '\"' as no_eiyo1");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.wariai1,''),'\"','\"\"') + '\"' as wariai1");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.no_eiyo2,''),'\"','\"\"') + '\"' as no_eiyo2");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.wariai2,''),'\"','\"\"') + '\"' as wariai2");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.no_eiyo3,''),'\"','\"\"') + '\"' as no_eiyo3");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.wariai3,''),'\"','\"\"') + '\"' as wariai3");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.no_eiyo4,''),'\"','\"\"') + '\"' as no_eiyo4");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.wariai4,''),'\"','\"\"') + '\"' as wariai4");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.no_eiyo5,''),'\"','\"\"') + '\"' as no_eiyo5");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.wariai5,''),'\"','\"\"') + '\"' as wariai5");
			ret.append(" ,ISNULL(convert(varchar(10),A.dt_konyu,111),'') as dt_konyu");
			ret.append(" ,ISNULL(convert(varchar,B.cd_kakutei),'') as cd_kakutei");
			ret.append(" ,CASE B.kbn_haishi WHEN 0 THEN '使用可能' WHEN 1 THEN '廃止' END as nm_haishi");
//			ret.append(" FROM ma_genryokojo A");

			//工場が選択されている場合
			if (!strCd_Kojo.equals("")) {
				ret.append(" FROM ma_genryokojo A");
			} else {
				ret.append(" FROM (");
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
				ret.append(" SELECT MG1.cd_kaisha, MG1.cd_genryo, MG1.cd_busho, MG2.nm_genryo, '' as dt_konyu, MG2.flg_shiyo, MG2.flg_mishiyo");
//add end --------------------------------------------------------------------------------------
				ret.append(" FROM (");
				ret.append(" SELECT cd_kaisha, cd_genryo, MIN(cd_busho) as cd_busho");
				ret.append(" FROM ma_genryokojo");
				ret.append(" GROUP BY cd_kaisha, cd_genryo");
				ret.append(" ) as MG1");
				ret.append(" INNER JOIN ma_genryokojo MG2");
				ret.append(" ON MG1.cd_kaisha = MG2.cd_kaisha");
				ret.append(" AND MG1.cd_busho = MG2.cd_busho");
				ret.append(" AND MG1.cd_genryo = MG2.cd_genryo");
				ret.append(" ) A");
			}
			
			ret.append("      LEFT JOIN ma_genryo B");
			ret.append("      ON A.cd_kaisha = B.cd_kaisha");
			ret.append("      AND A.cd_genryo = B.cd_genryo");
			ret.append(" WHERE A.cd_kaisha = ");
			ret.append(strCd_Kaisha);
//			ret.append(" AND A.cd_busho IN (");
//			ret.append(strCd_Kojo);
//			ret.append("," + ConstManager.getConstValue(ConstManager.Category.設定, "SHINKIGENRYO_BUSHOCD") + ")");

			//工場が選択されている場合
			if (!strCd_Kojo.equals("")) {
				ret.append(" AND A.cd_busho IN (");
				ret.append(strCd_Kojo);
				ret.append("," + ConstManager.getConstValue(ConstManager.Category.設定, "SHINKIGENRYO_BUSHOCD") + ")");
			}
			
			//原料コードが入力されている場合
			if (!strCd_Genryo.equals("")) {
				ret.append(" AND B.cd_genryo = '");
				ret.append(strCd_Genryo);
				ret.append("'");
			}
			
			//原料名が入力されている場合
			if (!strName_Genryo.equals("")) {
				ret.append(" AND A.nm_genryo LIKE '%");
				ret.append(strName_Genryo);
				ret.append("%'");
			}

			//新規原料が検索対象の場合
			if (!strJokenKbn1.equals("") && strJokenKbn2.equals("")) {
				ret.append(" AND SUBSTRING(B.cd_genryo,1,1) > '9'");
			
			//既存原料が検索対象の場合
			}else if (strJokenKbn1.equals("") && !strJokenKbn2.equals("")) {
				ret.append(" AND SUBSTRING(B.cd_genryo,1,1) <= '9'");
			}
			
			//検索条件権限設定
			//担当会社
			if(dataId.equals("1")) {
				ret.append(" AND A.cd_kaisha in ( ");
				ret.append(strSqlTanto + " ) ");

			//自工場分
			} else if (dataId.equals("2")) { 
				ret.append(" AND A.cd_busho = ");
				ret.append(userInfoData.getCd_busho());
			}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			//使用実績
			if (strShiyoFlg.equals("1")) {
				ret.append(" AND A.flg_shiyo = 1");
			}
//add end --------------------------------------------------------------------------------------
			
			ret.append(" ) tbl ");
			ret.append(" ORDER BY ");
			ret.append("     tbl.kbn, tbl.cd_genryo ");
	
		} catch (Exception e) {
			this.em.ThrowException(e, "原料データCSV、検索SQLの生成に失敗しました。");
			
		} finally {
	
		}
		return ret;
		
	}

}
