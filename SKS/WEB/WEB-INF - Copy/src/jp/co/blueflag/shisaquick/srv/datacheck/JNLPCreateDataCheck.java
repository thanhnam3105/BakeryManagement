package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.jnlp.ShisakuNoKengenCheck;
import jp.co.blueflag.shisaquick.srv.jnlp.ShisakuSecretCheck;

/**
 * 
 * JNLP作成処理DBチェック
 *  : JNLPファイルの作成時DBチェック
 * 
 * @author TT.jinbo
 * @since 2009/08/03
 *
 */
public class JNLPCreateDataCheck extends DataCheck {

	private ShisakuNoKengenCheck shisakuCheck = null;		//試作No権限チェッククラス
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
	private ShisakuSecretCheck shisakuSecretCheck = null;	//試作Noシークレットチェッククラス
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
	
	/**
	 * コンストラクタ
	 */
	public JNLPCreateDataCheck() {
		//基底クラスのコンストラクタ
		super();

		//試作No権限チェッククラス
		shisakuCheck = new ShisakuNoKengenCheck(); 
		//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
		//試作Noシークレットチェッククラス
		shisakuSecretCheck = new ShisakuSecretCheck();
		//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
	}

	/**
	 * JNLP作成処理DBチェック管理
	 * @param reqData : 機能リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData,
			UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//ユーザー情報退避
		userInfoData = _userInfoData;

		try {
			String strShisakuNo = "";
			String strMode = "";
			
			strShisakuNo = reqData.getFieldVale(0, 0, "no_shisaku");
			strMode = reqData.getFieldVale(0, 0, "mode");
			
			//新規モードでの起動以外の場合
			if (!strMode.equals("110")) {
				//試作Noの権限チェックを行う。
				if (shisakuCheck.execDataCheck(reqData, userInfoData) == false) {
					em.ThrowException(ExceptionKind.一般Exception,"E000308", strShisakuNo, "", "");
				}
				//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
				//編集モード or 製法コピーモードでの起動の場合
				else if(strMode.equals("100") || strMode.equals("120")){
					//試作Noのシークレットチェックを行う。
					if (shisakuSecretCheck.execDataCheck(reqData,_userInfoData) == false) {
						em.ThrowException(ExceptionKind.一般Exception,"E000334", strShisakuNo, "", "");
					}
				}
				//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "JNLP作成DBチェック処理に失敗しました。");
			
		} finally {

		}

	}

}