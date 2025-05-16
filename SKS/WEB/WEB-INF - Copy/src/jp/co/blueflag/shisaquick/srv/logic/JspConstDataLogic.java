package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 
 * コンストデータ取得
 * 	: QP@00412_シサクイック改良 No.4
 * @author TT.katayama
 * @since  2010/10/07
 */
public class JspConstDataLogic extends LogicBase{

	/**
	 * コンストデータ取得コンストラクタ 
	 * : インスタンス生成
	 */
	public JspConstDataLogic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * コンストデータ取得処理ロジック管理
	 * @param reqData : 機能リクエストデータ
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

		String strKinoId = "";
		String strTableNm = "";

		List<?> lstRecset = null;										//検索結果格納用リスト

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		
		try {
			
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// 機能IDの設定
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			// テーブル名の設定
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//検索結果をレスポンスデータに格納
			storageGenryoData(resKind.getTableItem(0));
						
		} catch (Exception e) {
			this.em.ThrowException(e, "コンストデータ取得処理に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

		}
		return resKind;

	}
	
	/**
	 * コンストデータ取得パラメーター格納
	 *  : コンストデータ情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenryoData(RequestResponsTableBean resTable) 
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//①：引数　検索結果情報リストに保持している各パラメーターをレスポンスデータへ格納する。
		try {

			//処理結果の格納
			resTable.addFieldVale("rec" + 0, "flg_return", "true");
			resTable.addFieldVale("rec" + 0, "msg_error", "");
			resTable.addFieldVale("rec" + 0, "no_errmsg", "");
			resTable.addFieldVale("rec" + 0, "nm_class", "");
			resTable.addFieldVale("rec" + 0, "cd_error", "");
			resTable.addFieldVale("rec" + 0, "msg_system", "");
			
			//結果をレスポンスデータに格納
			resTable.addFieldVale("rec" + 0, "nm_shiyo", ConstManager.getConstValue(Category.設定, "NM_SHIYO_JISEKI"));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "コンストデータ取得処理に失敗しました。");
			
		} finally {

		}

	}

}
