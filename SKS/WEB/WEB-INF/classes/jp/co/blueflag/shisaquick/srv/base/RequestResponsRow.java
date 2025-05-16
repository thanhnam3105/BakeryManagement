package jp.co.blueflag.shisaquick.srv.base;

/**
 * 
 * リクエスト/レスポンス項目
 *  : 項目名/項目値を保持する
 *
 */
public class RequestResponsRow extends ObjectBase {

	protected String strName;		//項目名
	protected String strValue;		//項目値
	
	/**
	 * コンストラクタ : インスタンス生成  
	 * @param name : 項目名
	 * @param value : 項目値
	 */
	public RequestResponsRow(String strFeldNm, String strFeldValue) {
		super();
		
		this.strName = strFeldNm;
		this.strValue = strFeldValue;
	}

	/**
	 * 項目名 ゲッター
	 * @return name : 項目名の値を返す
	 */
	public String getName() {
		return strName;
	}
	/**
	 * 項目名 セッター
	 * @param _name : 項目名の値を格納する
	 */
	public void setName(String _strName) {
		strName = _strName;
	}

	/**
	 * 項目値 ゲッター
	 * @return value : 項目値の値を返す
	 */
	public String getValue() {
		return strValue;
	}
	/**
	 * 項目値 セッター
	 * @param _value : 項目値の値を格納する
	 */
	public void setValue(String _strValue) {
		strValue = _strValue;
	}
	
	
}
