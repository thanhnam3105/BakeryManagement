package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseCSV;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * リテラルデータCSVを生成する
 * @author jinbo
 * @since  2009/05/13
 */
public class LiteralCSVCreateLogic extends LogicBaseCSV {
	
	/**
	 * コンストラクタ
	 */
	public LiteralCSVCreateLogic() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
	}
	
	/**
	 * リテラルデータCSVを生成する
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
			strFilePath = CSVOutput("リテラルマスタ", lstRecset);

			//レスポンスデータ生成
			ret = storageLiteralDataCSV(strFilePath);
			
		} catch (Exception e) {
			em.ThrowException(e, "リテラルデータCSVの生成に失敗しました。");

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
	private RequestResponsKindBean storageLiteralDataCSV(String DownLoadPath) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsKindBean ret = null;
		
		try {
			//レスポンスを生成する
			ret = new RequestResponsKindBean();
			//機能IDを設置する
			ret.setID("SA320");
			
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
	 * 対象のリテラルデータを検索する
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
			strSql = literalDataCSVCreateSQL(KindBean);
			
			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (ret.size() == 1){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "リテラルデータ、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;
		}
		return ret;
		
	}
	
	/**
	 * リクエストデータより、リテラルデータ検索SQLを生成する
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : リテラルデータ検索SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer literalDataCSVCreateSQL(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();

		try {

			String strCd_Category = "";
			String dataId = null;

			//カテゴリコード
			strCd_Category = reqData.getFieldVale(0, 0, "cd_category");
	
			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("65")){
					//リテラルマスタＣＳＶ画面のデータIDを設定
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL文の作成	
			ret.append(" SELECT ");
			ret.append("  cd_category ");
			ret.append(" , nm_category ");
			ret.append(" , cd_literal ");
			ret.append(" , nm_ｌiteral ");
			ret.append(" , value1 ");
			ret.append(" , value2 ");
			ret.append(" , no_sort ");
			ret.append(" , biko ");
			ret.append(" , flg_edit ");
			ret.append(" , cd_group ");
			ret.append(" , nm_group ");
			ret.append(" FROM (");
			ret.append(" SELECT ");
			ret.append("  0 AS kbn ");
			ret.append(" , 'カテゴリコード' AS cd_category ");
			ret.append(" , 'カテゴリ名' AS nm_category ");
			ret.append(" , 'リテラルコード' AS cd_literal ");
			ret.append(" , 'リテラル名' AS nm_ｌiteral ");
			ret.append(" , 'リテラル値1' AS value1 ");
			ret.append(" , 'リテラル値2' AS value2 ");
			ret.append(" , '表示順' AS no_sort ");
			ret.append(" , '備考' AS biko ");
			ret.append(" , '編集Flg' AS flg_edit ");
			ret.append(" , 'グループコード' AS cd_group ");
			ret.append(" , 'グループ名' AS nm_group ");
			ret.append(" UNION ");
			ret.append(" SELECT ");
			ret.append("  1 AS kbn ");
			ret.append(" , '\"' + REPLACE(A.cd_category,'\"','\"\"') + '\"' AS cd_category ");
			ret.append(" , '\"' + REPLACE(A.nm_category,'\"','\"\"') + '\"' AS nm_category ");
			ret.append(" , '\"' + REPLACE(B.cd_literal,'\"','\"\"') + '\"' AS cd_literal ");
			ret.append(" , '\"' + REPLACE(B.nm_ｌiteral,'\"','\"\"') + '\"' AS nm_ｌiteral ");
			ret.append(" , ISNULL(CONVERT(varchar,B.value1),'') AS value1 ");
			ret.append(" , ISNULL(CONVERT(varchar,B.value2),'') AS value2 ");
			ret.append(" , ISNULL(CONVERT(varchar,B.no_sort),'') AS no_sort ");
			ret.append(" , '\"' + REPLACE(ISNULL(CONVERT(varchar,B.biko),''),'\"','\"\"') + '\"' AS biko ");
			ret.append(" , ISNULL(CONVERT(varchar,B.flg_edit),'') AS flg_edit ");
			ret.append(" , ISNULL(CONVERT(varchar,B.cd_group),'') AS cd_group ");
			ret.append(" , '\"' + REPLACE(ISNULL(CONVERT(varchar,C.nm_group),''),'\"','\"\"') + '\"' AS nm_group ");
			ret.append(" FROM ");
			ret.append(" ma_category AS A ");
			ret.append(" INNER JOIN ma_literal AS B ");
			ret.append(" ON A.cd_category = B.cd_category ");
			ret.append(" LEFT JOIN ma_group AS C ");
			ret.append(" ON B.cd_group = C.cd_group ");
			ret.append(" WHERE ");
			ret.append("     A.cd_category = '" + strCd_Category + "' ");
			
			//検索条件権限設定
			//同一グループ
			if(dataId.equals("1")) {
				ret.append(" AND (B.cd_group IS NULL");
				ret.append(" OR B.cd_group = ");
				ret.append(userInfoData.getCd_group());
				ret.append(" ) ");
			}

			ret.append(" ) tbl ");
			ret.append(" ORDER BY ");
			ret.append("     tbl.kbn, tbl.cd_literal ");
	
		} catch (Exception e) {
			this.em.ThrowException(e, "リテラルデータCSV、検索SQLの生成に失敗しました。");
			
		} finally {
	
		}
		return ret;
		
	}

}