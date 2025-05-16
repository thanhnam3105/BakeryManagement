package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 栄養計算書（五訂）　機能ID：SA430
 * @author isono
 * @since  2009/05/25
 */
public class EiryouKeisan1Logic extends LogicBaseExcel {
	
	/**
	 * コンストラクタ
	 */
	public EiryouKeisan1Logic() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
	}
	/**
	 * サンプル説明書を生成する
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
		//エクセルファイルパス
		String DownLoadPath = "";
		
		try {
			//DB検索
			super.createSearchDB();
			lstRecset = getData(reqData);

			//Excelファイル生成
			DownLoadPath = makeExcelFile(lstRecset);

			//レスポンスデータ生成
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "栄養計算書（五訂）の生成に失敗しました。");

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
	private RequestResponsKindBean CreateRespons(String DownLoadPath) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsKindBean ret = null;
		
		try {
			//レスポンスを生成する
			ret = new RequestResponsKindBean();
			//機能IDを設置する
			ret.setID("SA430");
			
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
			em.ThrowException(e, "栄養計算書（五訂）");

		} finally {

		}
		return ret;
		
	}
	/**
	 * サンプル説明書を生成する
	 * @param lstRecset : 検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile(List<?> lstRecset) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		
		try {
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("調味料（五訂）");
			
			//ダウンロード用のEXCELを生成する
			for (int i = 0; i < lstRecset.size(); i++) {
				
				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset.get(i);

				try{
					//Excelに値をセットする
					//試作名
					super.ExcelSetValue("試作名", toString(items[0]));
					//製法番号
					super.ExcelSetValue("製法番号", toString(items[1]));
					//食品番号
					super.ExcelSetValue("食品番号", toString(items[2])); 
					//使用量
					super.ExcelSetValue("使用量", 
							toRound( toDouble(toString(items[3])) * 1000, 3));
					
				}catch(ExceptionWaning e){
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;
					
				}finally{
					
				}
				
			}
			//エクセルファイルをダウンロードフォルダに生成する
			ret = super.ExcelOutput();
			
		} catch (Exception e) {
			em.ThrowException(e, "栄養計算書（五訂）、excelの生成に失敗しました。");

		} finally {

		}
		return ret;
		
	}
	/**
	 * 対象の試作品データを検索する
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
			strSql = MakeSQLBuf(KindBean);
			
			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "栄養計算書（五訂）、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;
		}
		return ret;
		
	}
	/**
	 * リクエストデータより、試作データ検索SQLを生成する
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 試作データ検索SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();

		try {

			String strCd_shain = "";
			String strNen = "";
			String strNo_oi = "";
			String strSeq_shisaku = "";
			
			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//試作No（サンプル番号）
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku");
	
			//SQL文の作成	
			ret.append(" SELECT ");
			ret.append(" T110.nm_hin AS NM_HIN ");
			ret.append(" ,ISNULL(T131.no_seiho1, '') AS NO_SEIHO ");
			ret.append(" ,ANS.no_eiyo AS EIYO ");
			ret.append(" ,SUM(ANS.量) AS RYO ");
			ret.append(" FROM ");
			ret.append(" ( ");
			ret.append(" SELECT ");
			ret.append("   T131.cd_shain ");
			ret.append(" , T131.nen ");
			ret.append(" , T131.no_oi ");
			ret.append(" , T131.seq_shisaku ");
			ret.append(" , T120.cd_genryo ");
			ret.append(" , T120.cd_kaisha ");
			ret.append(" , T401.no_eiyo1 AS no_eiyo ");
			ret.append(" , T401.wariai1 AS wariai ");
			ret.append(" , T132.quantity ");
			ret.append(" , CASE ISNULL(T401.wariai1, 0) ");
			ret.append(" WHEN '' THEN 0 ELSE ISNULL(T401.wariai1, 0) ");
			ret.append(" END * 0.01 * ISNULL(T132.quantity, 0) AS 量 ");
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append(" ON T131.cd_shain = T132.cd_shain ");
			ret.append(" AND T131.nen = T132.nen ");
			ret.append(" AND T131.no_oi = T132.no_oi ");
			ret.append(" AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append(" ON T132.cd_shain = T120.cd_shain ");
			ret.append(" AND T132.nen = T120.nen ");
			ret.append(" AND T132.no_oi = T120.no_oi ");
			ret.append(" AND T132.cd_kotei = T120.cd_kotei ");
			ret.append(" AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS T401 ");
			ret.append(" ON T120.cd_genryo = T401.cd_genryo ");
			ret.append(" AND T120.cd_kaisha = T401.cd_kaisha ");
			ret.append(" WHERE  ");
			ret.append("     T131.cd_shain = " + strCd_shain + " ");
			ret.append(" AND T131.nen = " + strNen + " ");
			ret.append(" AND T131.no_oi = " + strNo_oi + " ");
			ret.append(" AND T131.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(T401.no_eiyo1, '') != ''  ");
			ret.append(" UNION ALL ");
			ret.append(" SELECT ");
			ret.append("   T131.cd_shain ");
			ret.append(" , T131.nen ");
			ret.append(" , T131.no_oi ");
			ret.append(" , T131.seq_shisaku ");
			ret.append(" , T120.cd_genryo ");
			ret.append(" , T120.cd_kaisha ");
			ret.append(" , T401.no_eiyo2 AS no_eiyo  ");
			ret.append(" , T401.wariai2 AS wariai ");
			ret.append(" , T132.quantity ");
			ret.append(" , CASE ISNULL(T401.wariai2, 0) ");
			ret.append(" WHEN '' THEN 0 ELSE ISNULL(T401.wariai2, 0) ");
			ret.append(" END * 0.01 * ISNULL(T132.quantity, 0) AS 量 ");
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append(" ON T131.cd_shain = T132.cd_shain ");
			ret.append(" AND T131.nen = T132.nen ");
			ret.append(" AND T131.no_oi = T132.no_oi ");
			ret.append(" AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append(" ON T132.cd_shain = T120.cd_shain ");
			ret.append(" AND T132.nen = T120.nen ");
			ret.append(" AND T132.no_oi = T120.no_oi ");
			ret.append(" AND T132.cd_kotei = T120.cd_kotei ");
			ret.append(" AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS T401 ");
			ret.append(" ON T120.cd_genryo = T401.cd_genryo ");
			ret.append(" AND T120.cd_kaisha = T401.cd_kaisha ");
			ret.append(" WHERE  ");
			ret.append("     T131.cd_shain = " + strCd_shain + " ");
			ret.append(" AND T131.nen = " + strNen + " ");
			ret.append(" AND T131.no_oi = " + strNo_oi + " ");
			ret.append(" AND T131.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(T401.no_eiyo1, '') != ''  ");
			ret.append(" UNION ALL ");
			ret.append(" SELECT ");
			ret.append("   T131.cd_shain ");
			ret.append(" , T131.nen ");
			ret.append(" , T131.no_oi ");
			ret.append(" , T131.seq_shisaku ");
			ret.append(" , T120.cd_genryo ");
			ret.append(" , T120.cd_kaisha ");
			ret.append(" , T401.no_eiyo3 AS no_eiyo  ");
			ret.append(" , T401.wariai3 AS wariai ");
			ret.append(" , T132.quantity ");
			ret.append(" , CASE ISNULL(T401.wariai3, 0) ");
			ret.append(" WHEN '' THEN 0 ELSE ISNULL(T401.wariai3, 0) ");
			ret.append(" END * 0.01 * ISNULL(T132.quantity, 0) AS 量 ");
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append(" ON T131.cd_shain = T132.cd_shain ");
			ret.append(" AND T131.nen = T132.nen ");
			ret.append(" AND T131.no_oi = T132.no_oi ");
			ret.append(" AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append(" ON T132.cd_shain = T120.cd_shain ");
			ret.append(" AND T132.nen = T120.nen ");
			ret.append(" AND T132.no_oi = T120.no_oi ");
			ret.append(" AND T132.cd_kotei = T120.cd_kotei ");
			ret.append(" AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS T401 ");
			ret.append(" ON T120.cd_genryo = T401.cd_genryo ");
			ret.append(" AND T120.cd_kaisha = T401.cd_kaisha ");
			ret.append(" WHERE  ");
			ret.append("     T131.cd_shain = " + strCd_shain + " ");
			ret.append(" AND T131.nen = " + strNen + " ");
			ret.append(" AND T131.no_oi = " + strNo_oi + " ");
			ret.append(" AND T131.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(T401.no_eiyo1, '') != ''  ");
			ret.append(" UNION ALL ");
			ret.append(" SELECT ");
			ret.append("   T131.cd_shain ");
			ret.append(" , T131.nen ");
			ret.append(" , T131.no_oi ");
			ret.append(" , T131.seq_shisaku ");
			ret.append(" , T120.cd_genryo ");
			ret.append(" , T120.cd_kaisha ");
			ret.append(" , T401.no_eiyo4 AS no_eiyo  ");
			ret.append(" , T401.wariai4 AS wariai ");
			ret.append(" , T132.quantity ");
			ret.append(" , CASE ISNULL(T401.wariai4, 0) ");
			ret.append(" WHEN '' THEN 0 ELSE ISNULL(T401.wariai4, 0) ");
			ret.append(" END * 0.01 * ISNULL(T132.quantity, 0) AS 量 ");
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append(" ON T131.cd_shain = T132.cd_shain ");
			ret.append(" AND T131.nen = T132.nen ");
			ret.append(" AND T131.no_oi = T132.no_oi ");
			ret.append(" AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append(" ON T132.cd_shain = T120.cd_shain ");
			ret.append(" AND T132.nen = T120.nen ");
			ret.append(" AND T132.no_oi = T120.no_oi ");
			ret.append(" AND T132.cd_kotei = T120.cd_kotei ");
			ret.append(" AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS T401 ");
			ret.append(" ON T120.cd_genryo = T401.cd_genryo ");
			ret.append(" AND T120.cd_kaisha = T401.cd_kaisha ");
			ret.append(" WHERE  ");
			ret.append("     T131.cd_shain = " + strCd_shain + " ");
			ret.append(" AND T131.nen = " + strNen + " ");
			ret.append(" AND T131.no_oi = " + strNo_oi + " ");
			ret.append(" AND T131.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(T401.no_eiyo1, '') != ''  ");
			ret.append(" UNION ALL ");
			ret.append(" SELECT ");
			ret.append("   T131.cd_shain ");
			ret.append(" , T131.nen ");
			ret.append(" , T131.no_oi ");
			ret.append(" , T131.seq_shisaku ");
			ret.append(" , T120.cd_genryo ");
			ret.append(" , T120.cd_kaisha ");
			ret.append(" , T401.no_eiyo5 AS no_eiyo  ");
			ret.append(" , T401.wariai5 AS wariai ");
			ret.append(" , T132.quantity ");
			ret.append(" , CASE ISNULL(T401.wariai5, 0) ");
			ret.append(" WHEN '' THEN 0 ELSE ISNULL(T401.wariai5, 0) ");
			ret.append(" END * 0.01 * ISNULL(T132.quantity, 0) AS 量 ");
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append(" ON T131.cd_shain = T132.cd_shain ");
			ret.append(" AND T131.nen = T132.nen ");
			ret.append(" AND T131.no_oi = T132.no_oi ");
			ret.append(" AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append(" ON T132.cd_shain = T120.cd_shain ");
			ret.append(" AND T132.nen = T120.nen ");
			ret.append(" AND T132.no_oi = T120.no_oi ");
			ret.append(" AND T132.cd_kotei = T120.cd_kotei ");
			ret.append(" AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS T401 ");
			ret.append(" ON T120.cd_genryo = T401.cd_genryo ");
			ret.append(" AND T120.cd_kaisha = T401.cd_kaisha ");
			ret.append(" WHERE  ");
			ret.append("     T131.cd_shain = " + strCd_shain + " ");
			ret.append(" AND T131.nen = " + strNen + " ");
			ret.append(" AND T131.no_oi = " + strNo_oi + " ");
			ret.append(" AND T131.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(T401.no_eiyo1, '') != ''  ");
			ret.append(" ) AS ANS ");
			ret.append(" LEFT JOIN tr_shisakuhin AS T110 ");
			ret.append(" ON ANS.cd_shain = T110.cd_shain ");
			ret.append(" AND ANS.nen = T110.nen ");
			ret.append(" AND ANS.no_oi = T110.no_oi ");
			ret.append(" LEFT JOIN tr_shisaku AS T131 ");
			ret.append(" ON T110.cd_shain = T131.cd_shain ");
			ret.append(" AND T110.nen = T131.nen ");
			ret.append(" AND T110.no_oi = T131.no_oi ");
			ret.append(" AND T110.seq_shisaku = T131.seq_shisaku ");
			ret.append(" WHERE ");
			ret.append("  ANS.量 > 0 ");
			ret.append(" GROUP BY  ");
			ret.append("  T110.nm_hin ");
			ret.append(" ,T131.no_seiho1 ");
			ret.append(" ,ANS.no_eiyo ");
	
		} catch (Exception e) {
			this.em.ThrowException(e, "栄養計算書（五訂）、検索SQLの生成に失敗しました。");
			
		} finally {
	
		}
		return ret;
		
	}

}
