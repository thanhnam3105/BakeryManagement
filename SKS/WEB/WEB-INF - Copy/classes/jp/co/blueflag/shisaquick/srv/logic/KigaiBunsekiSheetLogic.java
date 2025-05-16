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
 * 危害分析シートを生成する
 * @author k-katayama
 * @since  2009/05/21
 */
public class KigaiBunsekiSheetLogic extends LogicBaseExcel {
	
	/**
	 * コンストラクタ
	 */
	public KigaiBunsekiSheetLogic() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
	}
	/**
	 * 危害分析シートを生成する
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
//			super.createSearchDB();
//			lstRecset = getData(reqData);

			//Excelファイル生成
//			DownLoadPath = makeExcelFile(lstRecset);

			//レスポンスデータ生成
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "危害分析シートの生成に失敗しました。");

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
			ret.setID("SA810");
			
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
	 * 危害分析シート(EXCEL)を生成する
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
			super.ExcelReadTemplate("危害分析シート");
			
			//ダウンロード用のEXCELを生成する
			for (int i = 0; i < lstRecset.size(); i++) {
				
				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset.get(i);

				try{
					//Excelに値をセットする
					//品コード
					super.ExcelSetValue("品コード", toString(items[4]));
					
				}catch(ExceptionWaning e){
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;
					
				}finally{
					
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
	 * 対象のデータを検索する
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
			em.ThrowException(e, "危害分析シート、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;
		}
		return ret;
		
	}
	/**
	 * リクエストデータより、検索SQLを生成する
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 検索SQL
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
			
			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL文の作成	
			ret.append(" SELECT ");
	
		} catch (Exception e) {
			this.em.ThrowException(e, "危害分析シート、検索SQLの生成に失敗しました。");			
		} finally {	
		}
		return ret;
		
	}

}