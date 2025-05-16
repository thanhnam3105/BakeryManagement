package jp.co.blueflag.shisaquick.srv.logic;

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
 * [S3-53 SA580] 試作コード入力検索ＤＢ処理
 * @author TT.katayama
 * @since 2009/04/08
 *
 */
public class InputGenryoSearchLogic extends LogicBase {
	
	/**
	 * コンストラクタ
	 */
	public InputGenryoSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 試作コード入力データ取得処理
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

		//リクエスト
		String strReqKaishaCd = "";
		String strReqBushoCd = "";
		String strReqGenryoCd = "";

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//①：リクエストデータより、試作コード入力検索条件を抽出し、試作コード入力検索データ情報を取得するSQLを作成する。

			//機能リクエストデータより取得
			strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strReqBushoCd = reqData.getFieldVale(0, 0, "cd_busho");
			strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			
			//SQL文の作成
			strSql = createSQL(strReqKaishaCd,strReqBushoCd,strReqGenryoCd);
			
			//②：データベース検索を用い、試作原料データを取得する。
			super.createSearchDB();
			lstRecset = this.searchDB.dbSearch(strSql.toString());
			
			//③：試作原料データパラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			
			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//レスポンスデータの形成
			storageInputGenryoData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作コード入力データ取得処理が失敗しました。");
			
		} finally {
			//リストの破棄
			removeList(lstRecset);
		
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			
			//変数の削除
			strSql = null;

		}
		return resKind;

	}
	
	/**
	 * 検索用SQL作成
	 * @param strKaishaCd : 会社コード　
	 * @param strBushoCd : 部署コード
	 * @param strGenryoCd : 原料コード
	 * @return 作成したSQL
	 */
	private StringBuffer createSQL(String strKaishaCd, String strBushoCd, String strGenryoCd) {
		StringBuffer strRetSql = new StringBuffer();;
		
		//①：試作コード入力情報を取得するためのSQLを作成
		strRetSql.append(" SELECT  ");
		strRetSql.append("   M401.cd_genryo AS cd_genryo ");
		strRetSql.append("  ,M402.nm_genryo AS nm_genryo ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS budomari ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
		strRetSql.append("  ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
		strRetSql.append(" FROM ma_genryo M401 ");
		strRetSql.append("  LEFT JOIN ma_genryokojo M402 ");
		strRetSql.append("   ON  M402.cd_kaisha = M401.cd_kaisha ");
		strRetSql.append("   AND M402.cd_genryo = M401.cd_genryo ");
		strRetSql.append(" WHERE M401.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M402.cd_busho =" + strBushoCd);
		strRetSql.append("  AND M401.cd_genryo = '" + strGenryoCd + "' ");
		
		//②:作成したSQLを返却
		return strRetSql;
	}
	
	/**
	 * 試作コード入力データパラメーター格納
	 * @param lstGenryoData : 検索結果情報リスト
	 */
	private void storageInputGenryoData(List<?> lstGenryoData, RequestResponsTableBean resTable)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//①：引数　検索結果情報リストに保持している各パラメーターをレスポンスデータへ格納する。
		try {
			
			for (int i = 0; i < lstGenryoData.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenryoData.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale("rec" + i, "cd_genryo", items[0].toString());
				resTable.addFieldVale("rec" + i, "nm_genryo", items[1].toString());
				resTable.addFieldVale("rec" + i, "tanka", items[2].toString());
				resTable.addFieldVale("rec" + i, "budomari", items[3].toString());
				resTable.addFieldVale("rec" + i, "ritu_abura", items[4].toString());
				resTable.addFieldVale("rec" + i, "ritu_sakusan", items[5].toString());
				resTable.addFieldVale("rec" + i, "ritu_shokuen", items[6].toString());
				resTable.addFieldVale("rec" + i, "ritu_sousan", items[7].toString());
				
			}

			if (lstGenryoData.size() == 0) {

				//処理結果の格納
				resTable.addFieldVale("rec0", "flg_return", "true");
				resTable.addFieldVale("rec0", "msg_error", "");
				resTable.addFieldVale("rec0", "no_errmsg", "");
				resTable.addFieldVale("rec0", "nm_class", "");
				resTable.addFieldVale("rec0", "cd_error", "");
				resTable.addFieldVale("rec0", "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale("rec0", "cd_genryo", "");
				resTable.addFieldVale("rec0", "nm_genryo", "");
				resTable.addFieldVale("rec0", "tanka", "");
				resTable.addFieldVale("rec0", "budomari", "");
				resTable.addFieldVale("rec0", "ritu_abura", "");
				resTable.addFieldVale("rec0", "ritu_sakusan", "");
				resTable.addFieldVale("rec0", "ritu_shokuen", "");
				resTable.addFieldVale("rec0", "ritu_sousan", "");
				
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "試作コード入力データパラメーター格納処理が失敗しました。");

		} finally {

		}
		
	}
	
}
