package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseCSV;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 類似品検索データCSVを生成する
 * @author Nishigawa
 * @since  2010/2/15
 */
public class FGEN1050_Logic extends LogicBaseCSV {
	
	/**
	 * コンストラクタ
	 */
	public FGEN1050_Logic() {
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
			
			//リクエストデータを規定のCSVフォーマットに変換
			lstRecset = makeCsvFile(reqData);
			
			//CSVファイル生成
			strFilePath = CSVOutput("ruizi", lstRecset);

			//レスポンスデータ生成
			ret = CreateRespons(strFilePath);
			
		} catch (Exception e) {
			em.ThrowException(e, "カテゴリデータCSVの生成に失敗しました。");

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
			ret.setID("FGEN1050");
			
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
	 * 規定のCSVフォーマットに変換
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 変換後リスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> makeCsvFile(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//変換後リスト
		List<?> ret = null;
		
		try {
			
			List<Object> aryAll = new ArrayList<Object>();
			
			//製品検索タイトル挿入
			Object[] arySeihinTitle = new Object[1];
			arySeihinTitle[0] = "製品検索";
			aryAll.add(arySeihinTitle);
			
			//製品検索条件挿入
			Object[] arySeihinZyoken = new Object[4];
			arySeihinZyoken[0] = (reqData.getFieldVale("seihin_joken", 0, "joken_nm_kaisha"));
			arySeihinZyoken[1] = (reqData.getFieldVale("seihin_joken", 0, "joken_nm_kojo"));
			arySeihinZyoken[2] = (reqData.getFieldVale("seihin_joken", 0, "joken_cd_seihin"));
			arySeihinZyoken[3] = (reqData.getFieldVale("seihin_joken", 0, "joken_nm_seihin"));
			aryAll.add(arySeihinZyoken);
			
			//2010/02/16[表示項目名行の追加] NAKAMURA ADD START-----------
			Object[] arySeihinKomoku = new Object[3];
			arySeihinKomoku[0] = "工場";
			arySeihinKomoku[1] = "製品コード";
			arySeihinKomoku[2] = "製品名";
			aryAll.add(arySeihinKomoku);
			//2010/02/16[表示項目名行の追加] NAKAMURA ADD END-------------
			
			//製品一覧挿入
			for(int i=0; i<reqData.getCntRow("seihin"); i++){
				Object[] arySeihinList = new Object[3];
				arySeihinList[0] = (reqData.getFieldVale("seihin", i, "nm_kojo"));
				arySeihinList[1] = (reqData.getFieldVale("seihin", i, "cd_seihin"));
				arySeihinList[2] = (reqData.getFieldVale("seihin", i, "nm_seihin"));
				aryAll.add(arySeihinList);
			}
			
			//資材検索タイトル挿入
			Object[] aryShizaiTitle = new Object[1];
			aryShizaiTitle[0] = ("資材検索");
			aryAll.add(aryShizaiTitle);
			
			//資材検索条件挿入
			Object[] aryShizaiZyoken = new Object[4];
			aryShizaiZyoken[0] = (reqData.getFieldVale("shizai_joken", 0, "joken_nm_kaisha"));
			aryShizaiZyoken[1] = (reqData.getFieldVale("shizai_joken", 0, "joken_nm_kojo"));
			aryShizaiZyoken[2] = (reqData.getFieldVale("shizai_joken", 0, "joken_cd_shizai"));
			aryShizaiZyoken[3] = (reqData.getFieldVale("shizai_joken", 0, "joken_nm_shizai"));
			aryAll.add(aryShizaiZyoken);
			
			//2010/02/16[表示項目名行の追加] NAKAMURA ADD START-----------
			Object[] aryShizaiSentakuKomoku = new Object[7];
			aryShizaiSentakuKomoku[0] = "工場";
			aryShizaiSentakuKomoku[1] = "資材コード";
			aryShizaiSentakuKomoku[2] = "資材名";
			aryShizaiSentakuKomoku[3] = "単価";
			aryShizaiSentakuKomoku[4] = "歩留（％）";
			aryShizaiSentakuKomoku[5] = "使用量／ケース";
			aryShizaiSentakuKomoku[6] = "製品コード";
			aryAll.add(aryShizaiSentakuKomoku);
			//2010/02/16[表示項目名行の追加] NAKAMURA ADD END-------------
			
			//資材一覧挿入
			for(int i=0; i<reqData.getCntRow("shizai"); i++){
				Object[] aryShizaiList = new Object[7];
				aryShizaiList[0] = (reqData.getFieldVale("shizai", i, "nm_kojo"));
				aryShizaiList[1] = (reqData.getFieldVale("shizai", i, "cd_shizai"));
				aryShizaiList[2] = (reqData.getFieldVale("shizai", i, "nm_shizai"));
				aryShizaiList[3] = (reqData.getFieldVale("shizai", i, "tanka"));
				aryShizaiList[4] = (reqData.getFieldVale("shizai", i, "budomari"));
				aryShizaiList[5] = (reqData.getFieldVale("shizai", i, "siyoryo"));
				aryShizaiList[6] = (reqData.getFieldVale("shizai", i, "cd_seihin"));
				aryAll.add(aryShizaiList);
			}
			
			//選択資材タイトル挿入
			Object[] arySentakuTitle = new Object[1];
			arySentakuTitle[0] = ("選択資材");
			aryAll.add(arySentakuTitle);
			
			//2010/02/16[表示項目名行の追加] NAKAMURA ADD START-----------
			aryAll.add(aryShizaiSentakuKomoku);
			//2010/02/16[表示項目名行の追加] NAKAMURA ADD END-------------
			
			//選択一覧挿入
			for(int i=0; i<reqData.getCntRow("sentaku"); i++){
				Object[] arySentakuList = new Object[7];
				arySentakuList[0] = (reqData.getFieldVale("sentaku", i, "nm_kojo"));
				arySentakuList[1] = (reqData.getFieldVale("sentaku", i, "cd_shizai"));
				arySentakuList[2] = (reqData.getFieldVale("sentaku", i, "nm_shizai"));
				arySentakuList[3] = (reqData.getFieldVale("sentaku", i, "tanka"));
				arySentakuList[4] = (reqData.getFieldVale("sentaku", i, "budomari"));
				arySentakuList[5] = (reqData.getFieldVale("sentaku", i, "siyoryo"));
				arySentakuList[6] = (reqData.getFieldVale("sentaku", i, "cd_seihin"));
				aryAll.add(arySentakuList);
			}
			
			//返却値に設定
			ret = aryAll;
			
		} catch (Exception e) {
			em.ThrowException(e, "類似品検索データ、CSVフォーマット変換に失敗しました。");

		} finally {
			
		}
		return ret;
		
	}

}