package jp.co.blueflag.shisaquick.srv.logic;


import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic.JWSLiteralSearchLogic;

/**
 * QP@10181_No.42_49_72
 * 【SA910】 JWSリテラル検索（製品比重）ＤＢ処理の実装
 * 
 * @author TT.Nishigawa
 * @since 2011/05/30
 *
 */
public class SA910 extends LogicBase {

	private JWSLiteralSearchLogic literalSearch = null;		//JWS用リテラル検索処理クラス
	
	/**
	 * コンストラクタ
	 */
	public SA910() {
		//基底クラスのコンストラクタ
		super();

		//JWS用リテラル検索処理クラス
		this.literalSearch = new JWSLiteralSearchLogic(); 
	}

	/**
	 * JWSリテラル検索（製品比重）ロジック管理
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

		RequestResponsKindBean resBean = null;
		
		try {
			//カテゴリコードの設定
			String strCd_Categori = "K_seihin_hiju";
			//検索処理を行い、結果を格納する。
			resBean = this.literalSearch.ExecLogic(reqData,strCd_Categori,userInfoData);
		} catch (Exception e) {
			this.em.ThrowException(e, "JWSリテラル検索（製品比重）ロジック管理処理に失敗しました。");
		} finally {
		}
		return resBean;
	}
}
