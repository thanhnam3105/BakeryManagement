package jp.co.blueflag.shisaquick.srv.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 *
 * セッションチェック
 *  : セッション情報の存在可否チェックを行う。
 * @author TT.furuta
 * @since  2009/03/24
 */
public class SessionCheck {

	private HttpSession session = null;		//セッションクラス
	private ExceptionManager em = null;		//Exception管理クラス

	/**
	 * コンストラクタ
	 *  : セッションチェックコンストラクタ
	 * @param session : セッション
	 */
	public SessionCheck(HttpSession session) {
		//インスタンス変数の初期化
//		session = null;
		em = null;

		//クラス名を引数として、ExceptionManagerのインスタンスを生成する。
		this.em = new ExceptionManager(this.getClass().getName());
		//セッション格納
		this.session = session;

	}

	/**
	 * セッション情報チェック
	 *  : セッション情報の存在可否をチェックする。
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void sessionInfoCheck()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning  {

		ArrayList<Object> arrSessionData = null;

		try{

			arrSessionData = new ArrayList<Object>();

			//ユーザーＩＤ
			arrSessionData.add(session.getAttribute("id_user"));
			//ユーザー名
			arrSessionData.add(session.getAttribute("nm_user"));
			//会社コード
			arrSessionData.add(session.getAttribute("cd_kaisha"));
			//会社名
			arrSessionData.add(session.getAttribute("nm_kaisha"));
			//部署コード
			arrSessionData.add(session.getAttribute("cd_busho"));
			//部署名
			arrSessionData.add(session.getAttribute("nm_busho"));
			//グループコード
			arrSessionData.add(session.getAttribute("cd_group"));
			//グループ名
			arrSessionData.add(session.getAttribute("nm_group"));
			//チームコード
			arrSessionData.add(session.getAttribute("cd_team"));
			//チーム名
			arrSessionData.add(session.getAttribute("nm_team"));
			//役職コード
			arrSessionData.add(session.getAttribute("cd_literal"));
			//役職名
			arrSessionData.add(session.getAttribute("nm_literal"));

			//リストサイズ数ループしてチェック
			for (int i=0;i<arrSessionData.size();i++){

				//セッション情報が取得出来ているか判定
//				if (arrSessionData.get(i).toString().equals(null)){
				if (arrSessionData.get(i) == null){
					//得出来ていない場合

					//一般Exceptionを生成する
					em.ThrowException(ExceptionKind.一般Exception, "E000101", "", "", "");

				}

			}

		} catch (Exception e) {
			//例外処理
			em.ThrowException(e, "セッション情報チェックに失敗しました。");

		} finally {
			removeList(arrSessionData);

		}

	}
	/**
	 * リストObjectの破棄
	 * @param listObj : 破棄対象のリスト
	 */
	private void removeList(Object Obj){

		List<?> tgList = null;
		try{
			tgList = (List<?>)Obj;

			for(int ix = 0; ix < tgList.size(); ix++){
				removeList(tgList.get(ix));
				tgList.remove(ix);

			}

		}catch(Exception e){

		} finally {
			Obj = null;
			tgList = null;
		}

	}

}
