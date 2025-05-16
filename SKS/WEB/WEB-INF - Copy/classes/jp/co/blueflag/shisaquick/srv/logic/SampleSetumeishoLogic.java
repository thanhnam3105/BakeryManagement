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
 * サンプル説明書を生成する
 * @author isono
 * @since  2009/04/09
 */
public class SampleSetumeishoLogic extends LogicBaseExcel {
	
	/**
	 * コンストラクタ
	 */
	public SampleSetumeishoLogic() {
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
		List<?> lstRecset1 = null;
		List<?> lstRecset2 = null;
		List<?> lstRecset3 = null;
		//エクセルファイルパス
		String DownLoadPath = "";
		
		try {
			//DB検索
			super.createSearchDB();
			lstRecset1 = getData1(reqData);
			lstRecset2 = getData2(reqData);
			lstRecset3 = getData3(reqData);

			//Excelファイル生成
			DownLoadPath = makeExcelFile(lstRecset1,lstRecset2,lstRecset3);

			//レスポンスデータ生成
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書の生成に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset1);
			removeList(lstRecset2);
			removeList(lstRecset3);
			
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
			ret.setID("SA450");
			
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
	 * サンプル説明書を生成する
	 * @param lstRecset : 検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile(List<?> lstRecset1, List<?> lstRecset2, List<?> lstRecset3) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		
		try {
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("サンプル説明書");

			//サンプル一つ目の出力
			if(lstRecset1 != null){
				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset1.size(); i++) {
					
					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset1.get(i);

					try{
						//Excelに値をセットする
						//品名
						super.ExcelSetValue("品名1", toString(items[12]));
						//サンプル番号
						super.ExcelSetValue("ＳＥＱ1", toString(items[3]));
						//品コード
						super.ExcelSetValue("品コード1", toString(items[4]));
						//原料名
						super.ExcelSetValue("原料名1", toString(items[5]));
						//配合
						super.ExcelSetValue("配合1", toString(items[6]));
						//歩留
						super.ExcelSetValue("歩留1", toString(items[7]));
						//表示案情報
						super.ExcelSetValue("表示案情報1", toString(items[8]));
						//添加物情報
						super.ExcelSetValue("添加物情報1", toString(items[9]));
						//栄養計算用　食品番号
						super.ExcelSetValue("栄養計算用　食品番号1", toString(items[10]));
						//メモ
						super.ExcelSetValue("メモ1", toString(items[11]));
						
					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;
						
					}finally{
						
					}
					
				}

			}

			//サンプル２つ目の出力
			if(lstRecset1 != null){
				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset2.size(); i++) {
					
					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset2.get(i);

					try{
						//Excelに値をセットする
						//品名
						super.ExcelSetValue("品名2", toString(items[12]));
						//サンプル番号
						super.ExcelSetValue("ＳＥＱ2", toString(items[3]));
						//品コード
						super.ExcelSetValue("品コード2", toString(items[4]));
						//原料名
						super.ExcelSetValue("原料名2", toString(items[5]));
						//配合
						super.ExcelSetValue("配合2", toString(items[6]));
						//歩留
						super.ExcelSetValue("歩留2", toString(items[7]));
						//表示案情報
						super.ExcelSetValue("表示案情報2", toString(items[8]));
						//添加物情報
						super.ExcelSetValue("添加物情報2", toString(items[9]));
						//栄養計算用　食品番号
						super.ExcelSetValue("栄養計算用　食品番号2", toString(items[10]));
						//メモ
						super.ExcelSetValue("メモ2", toString(items[11]));
						
					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;
						
					}finally{
						
					}
					
				}

			}

			//サンプル３つ目の出力
			if(lstRecset1 != null){
				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset3.size(); i++) {
					
					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset3.get(i);

					try{
						//Excelに値をセットする
						//品名
						super.ExcelSetValue("品名3", toString(items[12]));
						//サンプル番号
						super.ExcelSetValue("ＳＥＱ3", toString(items[3]));
						//品コード
						super.ExcelSetValue("品コード3", toString(items[4]));
						//原料名
						super.ExcelSetValue("原料名3", toString(items[5]));
						//配合
						super.ExcelSetValue("配合3", toString(items[6]));
						//歩留
						super.ExcelSetValue("歩留3", toString(items[7]));
						//表示案情報
						super.ExcelSetValue("表示案情報3", toString(items[8]));
						//添加物情報
						super.ExcelSetValue("添加物情報3", toString(items[9]));
						//栄養計算用　食品番号
						super.ExcelSetValue("栄養計算用　食品番号3", toString(items[10]));
						//メモ
						super.ExcelSetValue("メモ3", toString(items[11]));
						
					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;
						
					}finally{
						
					}
					
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
	 * 試作品データ(サンプル１つ目)を検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData1(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//検索結果
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";
		
		try {

			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//試作No（サンプル番号）
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku1");
			
			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書サンプル１、DB検索に失敗しました。");

		} finally {

		}
		return ret;
		
	}
	/**
	 * 試作品データ(サンプル２つ目)を検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData2(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//検索結果
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";
		
		try {

			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//試作No（サンプル番号）
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku2");
			
			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書サンプル２、DB検索に失敗しました。");

		} finally {

		}
		return ret;
		
	}
	/**
	 * 試作品データ(サンプル３つ目)を検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData3(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//検索結果
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";
		
		try {

			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//試作No（サンプル番号）
			strSeq_shisaku = toString(reqData.getFieldVale(0, 0, "seq_shisaku3"));
			
			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書サンプル３、DB検索に失敗しました。");

		} finally {

		}
		return ret;
		
	}
	/**
	 * 対象の試作品データを検索する
	 * @param strCd_shain : 試作社員cd
	 * @param strNen : 試作年
	 * @param strNo_oi : 追番
	 * @param strSeq_shisaku : 試作No（サンプル番号）
	 * @return list : 検索結果
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> getData(String strCd_shain, String strNen,String strNo_oi,String strSeq_shisaku) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//検索結果
		List<?> ret = null;
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//SQL文の作成
			strSql = MakeSQLBuf(strCd_shain,strNen,strNo_oi,strSeq_shisaku);
			
			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;
		}
		return ret;
		
	}
	/**
	 * リクエストデータより、試作データ検索SQLを生成する
	 * @param strCd_shain : 試作社員cd
	 * @param strNen : 試作年
	 * @param strNo_oi : 追番
	 * @param strSeq_shisaku : 試作No（サンプル番号）
	 * @return String : sql
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLBuf(String strCd_shain,	String strNen,String strNo_oi, String strSeq_shisaku) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();

		try {

			//SQL文の作成	
			ret.append(" SELECT ");
			ret.append("  A.cd_shain AS 試作番号_社員CD ");
			ret.append(" , A.nen AS 試作番号_年 ");
			ret.append(" , A.no_oi AS 試作番号_追版 ");
			ret.append(" , A.seq_shisaku AS 試作SEQ ");
			ret.append(" , D.cd_genryo AS 品コード ");
			ret.append(" , D.nm_genryo AS 原料名 ");
			ret.append(" , C.quantity AS 配合 ");
			ret.append(" , D.budomari AS 歩留 ");
			ret.append(" , E.hyojian AS 表示案情報 ");
			ret.append(" , E.tenkabutu AS 添加物情報 ");
			ret.append(" , E.no_eiyo1 ");
			ret.append(" + ',' +  E.no_eiyo2  ");
			ret.append(" + ',' +  E.no_eiyo3  ");
			ret.append(" + ',' +  E.no_eiyo4  ");
			ret.append(" + ',' +  E.no_eiyo5 AS 栄養計算用食品番号 ");
			ret.append(" , E.memo AS メモ ");
			ret.append(" , B.nm_hin AS 品名 ");
			ret.append(" FROM  ");
			ret.append(" tr_shisaku AS A ");
			ret.append(" LEFT JOIN tr_shisakuhin AS B ");
			ret.append(" ON A.cd_shain = B.cd_shain ");
			ret.append(" AND A.nen = B.nen ");
			ret.append(" AND A.no_oi = B.no_oi ");
			ret.append(" LEFT JOIN tr_shisaku_list AS C ");
			ret.append(" ON A.cd_shain = C.cd_shain ");
			ret.append(" AND A.nen = C.nen ");
			ret.append(" AND A.no_oi = C.no_oi ");
			ret.append(" AND A.seq_shisaku = C.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS D ");
			ret.append(" ON C.cd_shain = D.cd_shain ");
			ret.append(" AND C.nen = D.nen ");
			ret.append(" AND C.no_oi = D.no_oi ");
			ret.append(" AND C.cd_kotei = D.cd_kotei ");
			ret.append(" AND C.seq_kotei = D.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS E ");
			ret.append(" ON B.cd_kaisha = E.cd_kaisha ");
			ret.append(" AND D.cd_genryo = E.cd_genryo ");
			ret.append(" WHERE  ");
			ret.append("     A.cd_shain = " + strCd_shain + " ");
			ret.append(" AND A.nen = " + strNen + " ");
			ret.append(" AND A.no_oi = " + strNo_oi + " ");
			ret.append(" AND A.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(C.quantity,0) > 0 ");
	
		} catch (Exception e) {
			this.em.ThrowException(e, "サンプル説明書、検索SQLの生成に失敗しました。");
			
		} finally {
	
		}
		return ret;
		
	}

}