package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN0020_Logic;

/**
 * 原価試算　再計算（FID：FGEN0030）の実装
 * @author isono
 *
 */
public class FGEN0030_Logic extends LogicBase {

	/**
	 * 原価試算　再計算（FID：FGEN0030）の実装
	 * : インスタンス生成
	 */
	public FGEN0030_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 原価試算　再計算（FID：FGEN0030）
	 *  : 再計算の実装
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
		//再計算クラス
		CGEN0020_Logic clsCGEN0020_Logic = null;
		//レスポンス
		RequestResponsKindBean ret = null;
		
		try {

			//再計算クラス生成
			clsCGEN0020_Logic = new CGEN0020_Logic();
			ret = clsCGEN0020_Logic.ExecLogic(reqData, userInfoData);
			
			//ファンクションID設定
			ret.setID("FGEN0030");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算　再計算に失敗しました。");
		} finally {

		}
		return ret;
		
	}
	
}
