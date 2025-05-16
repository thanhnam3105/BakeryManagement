package jp.co.blueflag.shisaquick.srv.manager;

import javax.servlet.http.*;

import jp.co.blueflag.shisaquick.srv.base.ObjectBase;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * ユーザーセッション管理
 *  : ユーザーセッション情報の管理を行う。
 * @author TT.furuta
 * @since  2009/03/24
 */
public class UserInfoManager extends ObjectBase{

	//処理区分
	private String strShoriKbn = "";
	//ユーザーID
	//処理区分：1　JSPログイン及びJWS以外の場合
	//処理区分：2　JSPログインの場合	
	//処理区分：3　JWSの場合
	private String strUserId = "";
	
	/**
	 * ユーザーセッション管理コンストラクタ
	 */
	public UserInfoManager() {
		//基底クラスのコンストラクタ
		super();
		
		//インスタンス変数の初期化
		strShoriKbn = "";
		strUserId = "";
	
	}
	
	/**
	 * ユーザー情報参照
	 * @param  HttpServletRequest　：　HttpRequest
	 * @param  requestData ：　リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public UserInfoData userInfoManager(
			HttpServletRequest HttpRequest
			, RequestData requestData
			) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//ユーザー情報管理
		UserInfoData userInfo = null;
		//
		HttpSession session = null;
		
		try{
			//リクエストデータより処理区分、ユーザーIDの取得
			getKaind(requestData);
			
			//②：処理区分より、セッション情報を管理する。
			if (strShoriKbn.equals("1")){
				//処理区分：1　JSPログイン及びJWS以外の場合
				//	セッションチェッククラスを呼出す。
				//	ユーザー情報参照を呼出す。

				//セッションチェッククラスを呼出す。
				session = SessionCheck(HttpRequest);
				
				//ユーザー情報をUserInfoDada
				userInfo = new UserInfoData(session);
				userInfo.CreatUserInfo(strShoriKbn, session.getAttribute("id_user").toString());
				
			}else if (strShoriKbn.equals("2")){
				//処理区分：2　JSPログインの場合	
				//	セッション情報初期化クラスを呼出す。
				//	ユーザー情報参照を呼出す。
				
				//セッション情報初期化クラスを呼出す。
				session = SessionRemove(HttpRequest);
				
				if (!strUserId.equals("")){
					//ユーザー情報をUserInfoDada　
					userInfo = new UserInfoData(session);
					userInfo.CreatUserInfo(strShoriKbn, strUserId);
				}
				
			}else if (strShoriKbn.equals("3")){
				//処理区分：3　JWSの場合
				//	ユーザー情報をDBより参照
				
				if (!strUserId.equals("")){
					//ユーザー情報をUserInfoDada　
					userInfo = new UserInfoData(null);
					userInfo.CreatUserInfo(strShoriKbn, strUserId);
				}
				
			}
			
			
			
			try{
				
				//JSP起動条件をセッションに保存する
				if (!toString(requestData.GetValue("USERINFO", 0, 0, "MOVEMENT_CONDITION")).equals("")){
					session.setAttribute("MOVEMENT_CONDITION"
							, toString(requestData.GetValue("USERINFO", 0, 0, "MOVEMENT_CONDITION")));
					
				}
				//JSP起動条件をセッションよりメンバに退避する。
				userInfo.setMovement_condition(StringToList(
						toString(session.getAttribute("MOVEMENT_CONDITION")), ":::"));
				
			}catch(Exception e){
			
			}
			
			
		} catch (Exception e){
			em.ThrowException(e, "ユーザーセッション管理に失敗しました。");

		}
		return userInfo;
		
	}
	/**
	 * セッションの有無をチェックする
	 * @param HttpRequest:ｈｔｔｐリクエスト
	 * @return　HttpSession
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private HttpSession SessionCheck(HttpServletRequest HttpRequest) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		HttpSession ret = null;
		
		try{
			//セッションを取得する
			ret = HttpRequest.getSession(false);

			//セッション情報が取得出来ているか判定
			if (ret == null){
				//取得出来ていない場合

				//一般Exceptionを生成する
				em.ThrowException(ExceptionKind.一般Exception, "E000101", "", "", "");

			} else {
				try {
					//セッションにユーザID属性が存在しない場合
					String strUserId = ret.getAttribute("id_user").toString();
					
				} catch (NullPointerException e) {
					//セッションがタイムアウトしている
					//一般Exceptionを生成する
					em.ThrowException(ExceptionKind.一般Exception, "E000101", "", "", "");
				}
			}

		}catch(Exception e){
			em.ThrowException(e, "ユーザーセッション管理に失敗しました。");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * セッションを破棄して新たなセッションを開始する。
	 * @param HttpRequest : ｈｔｔｐリクエスト
	 * @return HttpSession : 新たに開始されたセッション
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private HttpSession SessionRemove(HttpServletRequest HttpRequest) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		HttpSession ret = null;

		try{
			//セッションの破棄
			if (HttpRequest.getSession(false) != null){
				HttpRequest.getSession(false).invalidate();
				
			}
			//新たなセッションを開始
			ret = HttpRequest.getSession();
			
		}catch(Exception e){
			em.ThrowException(e, "Httpセッションの開始に失敗しました。");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * リクエストデータより処理区分、ユーザーIDの取得
	 * @param requestData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void getKaind(RequestData requestData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try{
			//①：リクエストデータより、機能ID「USERINFO」のデータを抽出し、処理区分　・　ユーザーIDを取得する。
			for(int i=0; i<requestData.getItemList().size(); i++){

				//機能IDの対象インデックス取得
				if (requestData.GetKindID(i).equals("USERINFO")){
					
					//項目数ループ
					for (int j=0;j<requestData.GetValueCnt(i, 0);j++){

						if ("kbn_shori".equals(requestData.GetValueName(i,0,j))){
							//処理区分格納
							strShoriKbn = requestData.GetValue(i,0,0,j);

						} else if ("id_user".equals(requestData.GetValueName(i,0,j))){
							//ユーザーID格納
							strUserId = requestData.GetValue(i,0,0,j);

						}
					}
				}
			}
			
		}catch(Exception e){
			em.ThrowException(e, "ユーザーセッション管理、処理区分、ユーザーIDの取得に失敗しました。");
			
		}finally{
			
		}
		
	}
	
}
