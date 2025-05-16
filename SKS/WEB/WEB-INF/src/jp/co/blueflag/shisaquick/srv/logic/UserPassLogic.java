package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 *
 * パスワード確認SQL作成
 *  : パスワードに不正がないか確認する。
 * @author okano
 * @since  2013/09/18
 */
public class UserPassLogic extends LogicBase{

	/**
	 * パスワード確認ロジック用コンストラクタ
	 * : インスタンス生成
	 */
	public UserPassLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * パスワード確認SQL作成
	 * : パスワード不正チェックを行う為のSQLを作成。
	 * @param reqData：リクエストデータ
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

		RequestResponsTableBean reqTableBean = null;
		RequestResponsRowBean reqRecBean = null;
		StringBuffer strSQL = new StringBuffer();
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//テーブルBean取得
			reqTableBean = reqData.getTableItem(0);
			//行Bean取得
			reqRecBean = reqTableBean.getRowItem(0);

			String strId_User   = "";
			String strKbn_Login = "";
			String strPassword  = "";

			//行データよりリクエスト情報取得
			//ユーザーID設定
			strId_User = reqRecBean.GetItemValue("id_user");
			//ログイン区分設定
			strKbn_Login = reqRecBean.GetItemValue("kbn_login");
			//パスワード設定
			//シングルサインオン用パスワード取得SQL作成
			if (strKbn_Login.equals("1")){

				strSQL = new StringBuffer();
				strSQL.append("SELECT password FROM ma_user WHERE id_user = ");
				strSQL.append(strId_User);

				createSearchDB();
				lstRecset = searchDB.dbSearch(strSQL.toString());

				//Listをobjectへ変換
				Object[] objs = lstRecset.toArray(new String[lstRecset.size()]);

				//ObjectをStringへ変換
				strPassword = objs[0].toString();

			//ログイン用パスワード取得
			} else if (strKbn_Login.equals("2")){

				strPassword = reqRecBean.GetItemValue("password");
			}

			//機能IDの設定
			resKind.setID(reqData.getID());
			//テーブル名の設定
			resKind.addTableItem(((RequestResponsTableBean) reqData.GetItem(0)).getID());

			strSQL = new StringBuffer();
			strSQL.append("SELECT COUNT(*) FROM ma_user WHERE id_user = ");
			strSQL.append(strId_User);
			strSQL.append(" AND dt_password > DATEADD(MONTH,-6,GETDATE()) ");

			createSearchDB();
			lstRecset = searchDB.dbSearch(strSQL.toString());

			// MOD 2014/12/05 TT.Kitazawa 処理結果の格納を追加 start
//			checkUserPass(lstRecset);
			checkUserPass(lstRecset, resKind.getTableItem(0));
			// MOD 2014/12/05 TT.Kitazawa end

			sizeCheckPass(strPassword, 6);
			strCheckPass(strPassword);



		} catch (Exception e) {

			em.ThrowException(e,"ユーザー認証に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSQL = null;
			//クラスの破棄
			reqTableBean = null;
			reqRecBean = null;
		}

		return resKind;
	}

	// MOD 2014/12/05 TT.Kitazawa 処理結果の格納を追加 start
	/**
	 * 認証結果パラメーター格納
	 * : ユーザー認証の認証結果情報を機能レスポンスデータへ格納する。
	 * @param lstNinshoKekka：認証結果
	 * @param resTable：レスポンスデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
//	private void checkUserPass(List<?> lstNinshoKekka)
	private void checkUserPass(List<?> lstNinshoKekka, RequestResponsTableBean resTable)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			// MOD 2014/12/05 TT.Kitazawa end

			if (Integer.parseInt(lstNinshoKekka.get(0).toString()) == 0){

				//警告例外をThrowする。
				em.ThrowException(ExceptionKind.一般Exception, "E000225", "パスワード", "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e,"ユーザー認証に失敗しました。");

		} finally {

		}

	}

	protected void sizeCheckPass(String strChkPrm, int iMinLen) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			int chkLen = strChkPrm.length();
			// ①:最小桁数を用い、入力桁数チェックを行う。
			if (iMinLen > chkLen) {

				// 入力桁数不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception,"E000226","パスワード",Integer.toString(iMinLen),"");
			}

		} catch (Exception e) {
			em.ThrowException(e, "ユーザー認証に失敗しました。");

		} finally {

		}
	}

	protected void strCheckPass(String strChkPrm) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			//空白スペースチェック
			if(strChkPrm.indexOf(" ") != -1){

				em.ThrowException(ExceptionKind.一般Exception, "E000227", "パスワード", "", "");
			}

			//半角英数字混在チェック
			if(!strChkPrm.matches("^[0-9]+[0-9a-zA-Z]*[a-zA-Z]+[0-9a-zA-Z]*$|^[a-zA-Z]+[0-9a-zA-Z]*[0-9]+[0-9a-zA-Z]*$")){

				em.ThrowException(ExceptionKind.一般Exception, "E000227", "パスワード", "", "");
			}

		} catch (Exception e) {
			em.ThrowException(e, "ユーザー認証に失敗しました。");

		} finally {

		}
	}

}
