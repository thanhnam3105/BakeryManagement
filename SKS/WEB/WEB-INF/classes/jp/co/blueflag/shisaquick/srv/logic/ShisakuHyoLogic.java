package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * 【SA460】 試作表を生成する
 * 
 * @author k-katayama
 * @since 2009/05/20
 *
 */
public class ShisakuHyoLogic extends LogicBaseExcel {

	/**
	 * コンストラクタ
	 */
	public ShisakuHyoLogic() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
	}

	/**
	 * 試作表を生成する
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
		//配合データ用検索データ
		List<?> lstHaigoRecset = null;
		//試作データ用検索データ
		List<?> lstShisakuRecset = null;
		//エクセルファイルパス
		String DownLoadPath = "";
		
		try {
			//DB検索 : 配合データの検索
			super.createSearchDB();
			lstHaigoRecset = getHaigoData(reqData);
			
			//DB検索 : 試作データの検索
			lstShisakuRecset = getShisakuData(reqData);
						
			//Excelファイル生成
			DownLoadPath = makeExcelFile(lstHaigoRecset, lstShisakuRecset);
			
			//レスポンスデータ生成
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "試作表の生成に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstHaigoRecset);
			removeList(lstShisakuRecset);
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
			ret.setID("SA460");
			
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
	 * 試作表を生成する
	 * @param lstHaigoRecset : 配合データ検索データリスト
	 * @param lstShisakuRecset : 試作データ検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile(List<?> lstHaigoRecset, List<?> lstShisakuRecset) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		int i;
		
		try {		
			
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("試作表");

			//ダウンロード用のEXCELを生成する
			
			// ① : 画面基本情報をダウンロード用EXCELに設定
			
			// 検索結果取得
			Object[] items = (Object[]) lstHaigoRecset.get(0);
			
			// Excelに値をセット
			super.ExcelSetValue("試作コード", toString(items[0]));
			super.ExcelSetValue("依頼番号", toString(items[1]));
			super.ExcelSetValue("品名", toString(items[2]));
			super.ExcelSetValue("所属", toString(items[3]));
			super.ExcelSetValue("選択工場", toString(items[4]));
			super.ExcelSetValue("試作者", toString(items[5]));
			super.ExcelSetValue("発行日", toString(items[6]));
			super.ExcelSetValue("総合メモ", toString(items[7]));

			// ② : 配合データをダウンロード用EXCELに設定
			for ( i = 0; i < lstHaigoRecset.size(); i++ ) {
				
				// 検索結果取得
				items = (Object[]) lstHaigoRecset.get(i);
				
				try{
					// Excelに値をセットする
					super.ExcelSetValue("原料コード", toString(items[8]));
					super.ExcelSetValue("原料名", toString(items[9]));
					super.ExcelSetValue("単価", toString(items[10]));
					super.ExcelSetValue("歩留", toString(items[11]));
					super.ExcelSetValue("量1", toString(items[12]));
					super.ExcelSetValue("量2", toString(items[13]));
					super.ExcelSetValue("量3", toString(items[14]));
					super.ExcelSetValue("量4", toString(items[15]));
					super.ExcelSetValue("量5", toString(items[16]));
					super.ExcelSetValue("量6", toString(items[17]));
					super.ExcelSetValue("量7", toString(items[18]));
					super.ExcelSetValue("量8", toString(items[19]));
					super.ExcelSetValue("量9", toString(items[20]));
					super.ExcelSetValue("量10", toString(items[21]));
					
				} catch (ExceptionWaning e) {
					// 最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;
					
				} finally {
					items = null;			
					
				}
				
			}
			
			//② : 試作データをダウンロード用EXCELに設定

			//検索結果取得
			items = (Object[]) lstShisakuRecset.get(0);
			
			//注意事項・試作メモ
			super.ExcelSetValue("試作メモ", toString(items[4]));
			super.ExcelSetValue("製造工程/注意事項", toString(items[7]));
			
			for ( i = 0; i < lstShisakuRecset.size(); i++ ) {
				
				//検索結果取得
				items = (Object[]) lstShisakuRecset.get(i);
				
				try{
					//Excelに値をセットする
					super.ExcelSetValue("日付", toString(items[1]));
					super.ExcelSetValue("NO", toString(items[2]));
					super.ExcelSetValue("メモ", toString(items[3]));
					super.ExcelSetValue("コメント内容NO", toString(items[2]));
					super.ExcelSetValue("コメント内容", toString(items[5]) + "\n\n" + toString(items[6]));
					
				} catch (ExceptionWaning e) {
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;
					
				} finally {
					items = null;			
					
				}
				
			}
			//エクセルファイルをダウンロードフォルダに生成する
			ret = super.ExcelOutput();
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}

	/**
	 * 対象の配合データを検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getHaigoData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> lstHaigoData = null;
		List<?> lstShisakuListData = null;
		//出力データ
		List<?> ret = null;
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//① :　配合データの検索
			
			//SQL文の作成
			strSql = MakeHaigoSQLBuf(KindBean);
			
			//SQLを実行
			lstHaigoData = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstHaigoData.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
				
			}

			//② :　試作リストデータの検索
			
			//SQL文の作成
			strSql = MakeShisakuListSQLBuf(KindBean);
			
			//SQLを実行
			lstShisakuListData = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstShisakuListData.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
				
			}

			//③ 配合データと試作リストデータを合わせて、出力用リストを作成する
			ret = CreateHaigoShisakuListData(lstHaigoData, lstShisakuListData);
			
		} catch (Exception e) {
			em.ThrowException(e, "試作表出力、配合データ検索に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstHaigoData);
			removeList(lstShisakuListData);
			
			//変数の削除
			strSql = null;

		}
		return ret;
		
	}
	/**
	 * 対象の試作データを検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getShisakuData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//検索結果
		List<?> ret = null;
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//① 試作データ検索処理
			
			//SQL文の作成
			strSql = MakeShisakuSQLBuf(KindBean);
			
			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "試作表出力、試作データ検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;

		}
		return ret;
		
	}
	/**
	 * リクエストデータより、配合データ検索SQLを生成する
	 *  : 試作品テーブル、配合テーブルを検索し、配合データを生成する。
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 配合データ検索SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeHaigoSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strCd_kotei = "";
		String strSeq_kotei = "";

		try {

			//リクエストパラメータより、試作コードを取得する
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL文の作成
			ret.append(" SELECT DISTINCT ");
			ret.append("  RIGHT('0000000000'+CONVERT(varchar,T110.cd_shain),10)  ");
			ret.append("   +'-'+ RIGHT('00'+CONVERT(varchar,T110.nen),2)  ");
			ret.append("   +'-'+ RIGHT('000'+CONVERT(varchar,T110.no_oi),3) AS cd_shisaku  ");
			ret.append("  ,CONVERT(VARCHAR,T110.no_irai) AS no_irai ");
			ret.append("  ,T110.nm_hin AS nm_hin ");
			ret.append("  ,ISNULL(T110.memo,'') AS sogo_memo ");
			ret.append("  ,M104.nm_kaisha AS nm_kaisha ");
			ret.append("  ,M104.nm_busho AS nm_kojo ");
			ret.append("  ,M101_shain.nm_user AS nm_shain ");
			ret.append("  ,CONVERT(VARCHAR,GETDATE(),111) + ' ' ");
			ret.append("    + CONVERT(VARCHAR,DATEPART(hour,GETDATE())) + ':' ");
			ret.append("    + CONVERT(VARCHAR,DATEPART(minute,GETDATE()))  AS dt_hakkou ");
			ret.append("  ,T120.cd_kotei AS cd_kotei ");
			ret.append("  ,T120.seq_kotei AS seq_kotei ");
			ret.append("  ,ISNULL(T120.nm_kotei,'') AS nm_kotei ");
			ret.append("  ,ISNULL(T120.zoku_kotei,'') AS zoku_kotei ");
			ret.append("  ,ISNULL(T120.cd_genryo,'') AS cd_genryo ");
			ret.append("  ,ISNULL(T120.nm_genryo,'') AS nm_genryo ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.tanka),'') AS tanka ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.budomari),'') AS budomari ");
			ret.append("  ,ISNULL(T120.color,'') AS color ");
			ret.append("  ,T120.sort_kotei AS sort_kotei ");
			ret.append("  ,T120.sort_genryo AS sort_genryo ");
			ret.append(" FROM tr_shisakuhin T110 ");
			ret.append("  LEFT JOIN tr_haigo T120 ");
			ret.append("   ON  T120.cd_shain = T110.cd_shain ");
			ret.append("   AND T120.nen = T110.nen ");
			ret.append("   AND T120.no_oi = T110.no_oi ");
			ret.append("  LEFT JOIN ma_busho M104 ");
			ret.append("   ON  M104.cd_kaisha = T110.cd_kaisha ");
			ret.append("   AND M104.cd_busho = T110.cd_kojo ");
			ret.append("  LEFT JOIN ma_user M101_shain ");
			ret.append("   ON  M101_shain.id_user = T110.cd_shain ");
			ret.append(" WHERE  ");
			
			//試作コードを検索条件に設定
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);
			
			//工程CD・工程SEQを検索条件に設定
			ret.append(" AND ( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//リクエストパラメータより、工程CD・工程SEQを取得する
				strCd_kotei = reqData.getFieldVale(0, i, "cd_kotei");
				strSeq_kotei = reqData.getFieldVale(0, i, "seq_kotei");
				
				//条件を追加
				if ( i != 0 ) {
					ret.append("  OR ");
				}
				ret.append("   (T120.cd_kotei=" + strCd_kotei);
				ret.append("    AND T120.seq_kotei=" + strSeq_kotei + ")");
				
			}
			ret.append(" ) ");

			//取得順
			ret.append(" ORDER BY T120.sort_kotei, T120.sort_genryo ");

			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作表、配合データ検索SQLの生成に失敗しました。");
			
		} finally {
			//変数の削除
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strCd_kotei = null;
			strSeq_kotei = null;
	
		}
		return ret;
		
	}
	/**
	 * リクエストデータより、試作リストデータ検索SQLを生成する
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 試作データ検索SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeShisakuListSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";
		String strCd_kotei = "";
		String strSeq_kotei = "";

		try {

			//リクエストパラメータより、試作コードを取得する
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL文の作成
			ret.append(" SELECT T132.seq_shisaku AS seq_shisaku ");
			ret.append("    ,T132.cd_kotei AS cd_kotei ");
			ret.append("    ,T132.seq_kotei AS seq_kotei ");
			ret.append("    ,ISNULL(CONVERT(VARCHAR,T132.quantity),'') AS quantity ");
			ret.append("    ,ISNULL(T132.color,'') AS color ");
			ret.append("  FROM tr_shisakuhin T110 ");
			ret.append("    LEFT JOIN tr_haigo T120 ");
			ret.append("     ON  T120.cd_shain = T110.cd_shain ");
			ret.append("     AND T120.nen = T110.nen ");
			ret.append("     AND T120.no_oi = T110.no_oi ");
			ret.append("    LEFT JOIN tr_shisaku T131 ");
			ret.append("     ON  T131.cd_shain = T110.cd_shain ");
			ret.append("     AND T131.nen = T110.nen ");
			ret.append("     AND T131.no_oi = T110.no_oi ");
			ret.append("    LEFT JOIN tr_shisaku_list T132 ");
			ret.append("     ON  T132.cd_shain = T110.cd_shain ");
			ret.append("     AND T132.nen = T110.nen ");
			ret.append("     AND T132.no_oi = T110.no_oi ");
			ret.append("     AND T132.seq_shisaku = T131.seq_shisaku ");
			ret.append("     AND T132.cd_kotei = T120.cd_kotei ");
			ret.append("     AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" WHERE  ");

			//試作コードを検索条件に設定
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);

			//試作SEQ
			ret.append("  AND T132.seq_shisaku IN( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//リクエストパラメータより、試作SEQを取得する
				strSeq_shisaku = reqData.getFieldVale(0, i, "seq_shisaku");
				
				//SQL文に追加
				if ( i != 0 ) {
					ret.append(",");
					
				}
				ret.append(strSeq_shisaku);
				
			}
			ret.append("  ) ");
			
			//工程CD・工程SEQを検索条件に設定
			ret.append(" AND ( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//リクエストパラメータより、工程CD・工程SEQを取得する
				strCd_kotei = reqData.getFieldVale(0, i, "cd_kotei");
				strSeq_kotei = reqData.getFieldVale(0, i, "seq_kotei");
				
				//条件を追加
				if ( i != 0 ) {
					ret.append("  OR ");
					
				}
				ret.append("   (T132.cd_kotei=" + strCd_kotei);
				ret.append("    AND T132.seq_kotei=" + strSeq_kotei + ")");
				
			}
			ret.append(" ) ");
			
			//取得順
			ret.append(" ORDER BY T131.sort_shisaku, T120.sort_kotei, T120.sort_genryo ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作表、試作リストデータ検索SQLの生成に失敗しました。");
			
		} finally {
			//変数の削除
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strSeq_shisaku = null;
			strCd_kotei = null;
			strSeq_kotei = null;
	
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
	private StringBuffer MakeShisakuSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {
			//リクエストパラメータより、試作コードを取得する
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			
			//SQL文の作成
			ret.append(" SELECT  ");
			ret.append("    T131.seq_shisaku AS seq_shisaku ");
			ret.append("   ,ISNULL(CONVERT(VARCHAR,T131.dt_shisaku,111),'') AS dt_shisaku ");
			ret.append("   ,ISNULL(T131.nm_sample,'') AS nm_sample ");
			ret.append("   ,ISNULL(T131.memo,'') AS memo ");
			ret.append("   ,ISNULL(T131.memo_shisaku,'') AS memo_shisaku ");
			
			ret.append("   ,CASE WHEN T131.flg_memo=1 ");
			ret.append("     THEN ISNULL(T131.memo_sakusei,'') ");
			ret.append("     ELSE '' END AS memo_sakusei ");

			ret.append("   ,CASE WHEN T131.flg_hyoka=1 ");
			ret.append("     THEN ISNULL(T131.hyoka,'') ");
			ret.append("    ELSE '' END AS hyoka ");
			
			ret.append("   ,ISNULL(T133.chuijiko,'') AS chuijiko ");
			ret.append("   ,T131.sort_shisaku AS sort_shisaku ");
			ret.append(" FROM tr_shisakuhin T110 ");
			ret.append("  LEFT JOIN tr_shisaku T131 ");
			ret.append("   ON  T131.cd_shain = T110.cd_shain ");
			ret.append("   AND T131.nen = T110.nen ");
			ret.append("   AND T131.no_oi = T110.no_oi ");
			ret.append("  LEFT JOIN tr_cyuui T133 ");
			ret.append("   ON  T133.cd_shain = T110.cd_shain ");
			ret.append("   AND T133.nen = T110.nen ");
			ret.append("   AND T133.no_oi = T110.no_oi ");
			ret.append("   AND T133.no_chui = T131.no_chui ");
			ret.append(" WHERE  ");

			//試作コードを検索条件に設定
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);

			//試作SEQ
			ret.append("  AND T131.seq_shisaku IN( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//リクエストパラメータより、試作SEQを取得する
				strSeq_shisaku = reqData.getFieldVale(0, i, "seq_shisaku");
				
				//SQL文に追加
				if ( i != 0 ) {
					ret.append(",");
					
				}
				ret.append(strSeq_shisaku);
				
			}
			ret.append("  ) ");
			
			//取得順
			ret.append(" ORDER BY T131.sort_shisaku ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作表、試作データ検索SQLの生成に失敗しました。");
			
		} finally {
			//変数の削除
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strSeq_shisaku = null;
	
		}
		return ret;
		
	}
	
	/**
	 * 配合・試作リストデータの作成
	 * @param lstHaigoData : 配合データ
	 * @param lstShisakuListData : 試作リストデータ
	 * @return 出力データ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> CreateHaigoShisakuListData(List<?> lstHaigoData, List<?> lstShisakuListData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		List<Object[]> ret = new ArrayList<Object[]>();
		
		//設定用オブジェクト
		Object[] add_items = null;
		Object[] haigo_items = null;
		Object[] shisakuList_items = null;
		//工程CD・工程SEQ
		String strCd_kotei = null;
		String strSeq_kotei = null;
		
		//退避用工程CD
		String strCd_kotei_taihi = "";
		//試作リスト設定用カウント
		int intShisakuListCount = 0;
		
		try {
			//配合データリストの設定
			for (int i = 0; i < lstHaigoData.size(); i++ ) {
				
				//配合データ 検索結果取得
				haigo_items = (Object[]) lstHaigoData.get(i);
				
				//追加用オブジェクト生成
				add_items = new Object[22];
				
				//基本情報の設定
				add_items[0] = haigo_items[0].toString();			//試作コード
				add_items[1] = haigo_items[1].toString();			//依頼番号
				add_items[2] = haigo_items[2].toString();			//品名
				add_items[3] = haigo_items[4].toString();			//会社名
				add_items[4] = haigo_items[5].toString();			//工場名
				add_items[5] = haigo_items[6].toString();			//試作者
				add_items[6] = haigo_items[7].toString();			//発行日
				add_items[7] = haigo_items[3].toString();			//総合メモ
				
				//配合データ検索結果の設定
				
				// 工程CD・工程SEQの取得
				strCd_kotei = haigo_items[8].toString();			//工程CD
				strSeq_kotei = haigo_items[9].toString();			//工程SEQ
				
				// 工程行の設定
				if ( !strCd_kotei.equals(strCd_kotei_taihi) ) {
					//工程CDを退避
					strCd_kotei_taihi = strCd_kotei; 
										
					//工程CDと退避用工程CDが異なる場合、「工程属性 工程名」を設定
					add_items[8] = "";
					add_items[9] = haigo_items[11].toString() + " " + haigo_items[10].toString();
					for ( int j = 10; j < add_items.length; j++ ) {
						add_items[j] = "";
						
					}
					
					//リスト一行追加
					ret.add(add_items);	
					
				}
				
				//追加用オブジェクト再生成
				add_items = new Object[22];
				
				//基本情報の設定
				add_items[0] = haigo_items[0].toString();			//試作コード
				add_items[1] = haigo_items[1].toString();			//依頼番号
				add_items[2] = haigo_items[2].toString();			//品名
				add_items[3] = haigo_items[3].toString();			//会社名
				add_items[4] = haigo_items[4].toString();			//工場名
				add_items[5] = haigo_items[5].toString();			//試作者
				add_items[6] = haigo_items[6].toString();			//発行日
				add_items[7] = haigo_items[7].toString();			//総合メモ
				
				//配合データの設定
				add_items[8] = haigo_items[12].toString();		//原料コード 
				add_items[9] = haigo_items[13].toString();		//原料名
				add_items[10] = haigo_items[14].toString();		//単価
				add_items[11] = haigo_items[15].toString();		//歩留

				//試作リストより量を取得(10項目のみ)
				intShisakuListCount = 0;
				for (int j = 0; intShisakuListCount < 10; j++ ) {
					
					//試作リスト.量を設定
					if ( j < lstShisakuListData.size() ) {
						
						//試作リストデータ　検索結果取得
						shisakuList_items = (Object[]) lstShisakuListData.get(j);
						
						//配合データと試作リストデータの工程CD・工程SEQが等しい
						if ( strCd_kotei.equals(shisakuList_items[1].toString()) && strSeq_kotei.equals(shisakuList_items[2].toString()) ) {
						
							//量を設定
							add_items[intShisakuListCount + 12] = shisakuList_items[3].toString();

							//カウントを進める
							intShisakuListCount++;
							
						}
												
					} else {
						
						//量を設定
						add_items[intShisakuListCount + 12] = "";
						
						//カウントを進める
						intShisakuListCount++;
						
					}
															
				}
				
				//リスト一行追加
				ret.add(add_items);	
				
			}
			
		} catch(Exception e) {
			this.em.ThrowException(e, "試作表、配合・試作リストデータの作成処理に失敗しました。");
			
		} finally {
			//変数の削除
			add_items = null;
			haigo_items = null;
			shisakuList_items = null;
			strCd_kotei = null;
			strSeq_kotei = null;
			strCd_kotei_taihi = null;
			intShisakuListCount = 0;
			
		}
		
		return ret;
	}

	
}
