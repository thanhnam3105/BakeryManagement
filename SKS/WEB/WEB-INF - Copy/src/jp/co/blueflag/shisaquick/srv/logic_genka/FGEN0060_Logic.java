package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN0010_Logic;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN2020_Logic;

/**
 * 会社/工場洗い替え（FGEN0060）の実装
 * @author isono
 * @cleate 2009/11/02
 */
public class FGEN0060_Logic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public FGEN0060_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	
	/**
	 * 原価試算情報 会社、工場洗い替え
	 * 
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
		//原価試算 洗い替えクラス
		CGEN0010_Logic clsCGEN0010_Logic = null;
		//原価試算 登録管理クラス
		CGEN2020_Logic clsCGEN2020_Logic = null;
		
		try {
			
			//原価試算 登録管理クラスインスタンス生成
			clsCGEN2020_Logic = new CGEN2020_Logic();
			//原価試算 登録管理クラス実行
			resKind = clsCGEN2020_Logic.ExecLogic(reqData, userInfoData);

			//会社、工場洗い替えクラスインスタンス生成
			clsCGEN0010_Logic = new CGEN0010_Logic();
			//会社、工場洗い替えクラス実行 【QP@00342】引数に枝番追加
			clsCGEN0010_Logic.ExecLogic(
					  toDecimal(reqData.getFieldVale("kihon", "rec", "cd_shain"))
					, toInteger(reqData.getFieldVale("kihon", "rec", "nen"))
					, toInteger(reqData.getFieldVale("kihon", "rec", "no_oi"))
					, toInteger(reqData.getFieldVale("kihon", "rec", "new_cd_kaisya"))
					, toInteger(reqData.getFieldVale("kihon", "rec", "new_cd_kojyo"))
					, toInteger(reqData.getFieldVale("kihon", "rec", "no_eda"))
					, userInfoData);

		} catch (Exception e) {
			em.ThrowException(e, "原価試算情報 会社、工場洗い替えが失敗しました。");
			
		} finally {

		}
		return resKind;

	}
	
}