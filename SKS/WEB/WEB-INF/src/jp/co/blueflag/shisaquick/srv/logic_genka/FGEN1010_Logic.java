package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 会社コンボボックス検索
 * @author Nishigawa
 * @create 2009/11/05
 */
public class FGEN1010_Logic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public FGEN1010_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = _userInfoData;
		//検索バッファ
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;
		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();
			resKind.setID("FGEN1010");

			String kaishaCd = "";
			String DataId = "";

			//会社コードの取得
			kaishaCd = reqData.getFieldVale(0, 0, "cd_kaisya");
			DataId = userInfoData.getID_data("170");
			DataId = userInfoData.getID_data("170");

			// SQL文の作成
			strSql.append("SELECT DISTINCT cd_kaisha,nm_kaisha");
			strSql.append(" FROM ma_busho");

			//【QP@00342】工場フラグが１のものだけ表示
			strSql.append(" WHERE	flg_kojo = 1 ");

			// 2015/07/08 ADD ログインユーザと同じ会社を取得 Start
			// ログインユーザーと同じ会社のものだけ取得
			strSql.append(" AND cd_kaisha = " + userInfoData.getCd_kaisha());
			// 2015/07/08 ADD ログインユーザと同じ会社を取得 End

			strSql.append(" ORDER BY cd_kaisha");

			// SQLを実行
			super.createSearchDB();
			try{
				lstRecset = searchDB.dbSearch(strSql.toString());

			}catch(ExceptionWaning e){

			}

			// レスポンスデータの形成
			storageKaisyaCmb(lstRecset, resKind);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			//セッションのクローズ
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}
			//変数の削除
			strSql = null;
		}
		return resKind;
	}

	private void storageKaisyaCmb(

			List<?> lstGroupCmb
			, RequestResponsKindBean resData
			)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {

			if (lstGroupCmb == null){

				//処理結果①	flg_return
				resData.addFieldVale("kaisya", "rec" + "0", "flg_return", "true");
				//処理結果②	msg_error
				resData.addFieldVale("kaisya", "rec" + "0", "msg_error", "該当する会社がありません");
				//処理結果③	nm_class
				resData.addFieldVale("kaisya", "rec" + "0", "nm_class", "");
				//処理結果⑥	no_errmsg
				resData.addFieldVale("kaisya", "rec" + "0", "no_errmsg", "");
				//処理結果④	cd_error
				resData.addFieldVale("kaisya", "rec" + "0", "cd_error", "");
				//処理結果⑤	msg_system
				resData.addFieldVale("kaisya", "rec" + "0", "msg_system", "");
				//会社CD	cd_kaisya
				resData.addFieldVale("kaisya", "rec" + "0", "cd_kaisya", "");
				//会社名	nm_kaisya
				resData.addFieldVale("kaisya", "rec" + "0", "nm_kaisya", "");

			}else{

				for (int i = 0; i < lstGroupCmb.size(); i++) {

					//処理結果の格納
					Object[] items = (Object[]) lstGroupCmb.get(i);

					//処理結果①	flg_return
					resData.addFieldVale("kaisya", "rec" + toString(i), "flg_return", "true");
					//処理結果②	msg_error
					resData.addFieldVale("kaisya", "rec" + toString(i), "msg_error", "");
					//処理結果③	nm_class
					resData.addFieldVale("kaisya", "rec" + toString(i), "nm_class", "");
					//処理結果⑥	no_errmsg
					resData.addFieldVale("kaisya", "rec" + toString(i), "no_errmsg", "");
					//処理結果④	cd_error
					resData.addFieldVale("kaisya", "rec" + toString(i), "cd_error", "");
					//処理結果⑤	msg_system
					resData.addFieldVale("kaisya", "rec" + toString(i), "msg_system", "");
					//会社CD	cd_kaisya
					resData.addFieldVale("kaisya", "rec" + toString(i), "cd_kaisya", toString(items[0]));
					//会社名	nm_kaisya
					resData.addFieldVale("kaisya", "rec" + toString(i), "nm_kaisya", toString(items[1]));

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
