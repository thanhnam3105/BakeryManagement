package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN2020_Logic;

/**
 * 
 * 原価試算情報登録
 *  : 原価試算画面の登録
 *  
 * @author TT.Y.Nishigawa
 * @since  2009/10/28
 *
 */
public class FGEN0040_Logic extends LogicBase {
	
	/**
	 * コンストラクタ
	 */
	public FGEN0040_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始） 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * 原価試算情報 管理操作
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
		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;
		//原価試算 登録管理クラス
		CGEN2020_Logic clsCGEN2020_Logic = null;
		
		try {
			//原価試算 登録管理クラスインスタンス生成
			clsCGEN2020_Logic = new CGEN2020_Logic();
			//原価試算 登録管理クラス実行
			resKind = clsCGEN2020_Logic.ExecLogic(reqData, userInfoData);

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算情報 管理操作処理が失敗しました。");
			
		} finally {
			if (execDB != null) {
				execDB.Close();				//セッションのクローズ
				execDB = null;
			}
		}
		return resKind;
	}
	
}	