package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;

/**
 * 
 * リクエスト/レスポンスField
 *  : 項目名/項目値を保持する
 *
 */
public class RequestResponsFieldBean extends RequestResponsRow {
	
	/**
	 * コンストラクタ : インスタンス生成  
	 */
	public RequestResponsFieldBean() {
		//基底ｸﾗｽのコンストラクタ
		super("", "");

	}
	/**
	 * コンストラクタ : インスタンス生成  
	 * @param name : 項目名
	 * @param value : 項目値
	 */
	public RequestResponsFieldBean(String strFeldNm, String strFeldValue) {
		//基底ｸﾗｽのコンストラクタ
		super(strFeldNm, strFeldValue);
		//クラス名を引数として、ExceptionManagerのインスタンスを生成する。
		this.em = new ExceptionManager(this.getClass().getName());
	}

}
