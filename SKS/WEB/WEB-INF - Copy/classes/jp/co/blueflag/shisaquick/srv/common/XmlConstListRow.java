package jp.co.blueflag.shisaquick.srv.common;

/**
 * 
 * 定数XML格納Row
 *  : コンストXMLを読み込みリストの行
 *
 */
public class XmlConstListRow {

	private String Code;			//コンストのコード
	private String Value;			//コンストの値
	
	/**
	 * コンストラクタ
	 * @param strCode : コード
	 * @param strValue : 値
	 */
	public XmlConstListRow(String strCode, String strValue) {
		//① インスタンスを生成する
		this.Code = "";
		this.Value = "";
		
		//② 値の格納
		this.Code = strCode;
		this.Value = strValue;
	}
	
	/**
	 * コンストのコード ゲッター
	 * @return code : コンストのコードの値を返す
	 */
	public String getCode() {
		return this.Code;
	}
	/**
	 * コンストのコード セッター
	 * @param _code : コンストのコードの値を格納する
	 */
	public void setCode(String _code) {
		this.Code = _code;
	}
	
	/**
	 * コンスト値 ゲッター
	 * @return value : コンスト値の値を返す
	 */
	public String getValue() {
		return this.Value;
	}
	/**
	 * コンスト値 セッター
	 * @param _value : コンスト値の値を格納する
	 */
	public void setValue(String _value) {
		this.Value = _value;
	}
	
}
