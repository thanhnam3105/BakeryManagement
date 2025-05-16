package jp.co.blueflag.shisaquick.jws.common;

import java.io.Serializable;

/**
 * 
 * 共通エラー操作
 *  : 例外処理の基本動作を行う
 *
 */
public class ExceptionBase extends Exception implements Serializable  {

	private String strErrmsg;			//エラーメッセージ
	private String strErrShori;			//エラー処理名
	private String strMsgNo;			//メッセージNo
	private String strErrCd;			    //エラーコード
	private String strSystemMsg;		//システムメッセージ

	/**
	 * デフォルトコンストラクタ
	 */
	public ExceptionBase() {
		//スーパークラスのコンストラクタを呼び出し
		super();

		this.strErrmsg = "";
		this.strErrShori = "";
		this.strMsgNo = "";
		this.strErrCd = "";
		this.strSystemMsg = "";
	}


	/**
	 * コンストラクタ
	 * @param e : Exceptionクラス
	 * @param strSetErrmsg : エラーメッセージ
	 * @param stSetrErrShori : エラー処理名
	 * @param strSetMsgNo : メッセージNo
	 * @param strSetErrCd : エラーコード
	 * @param strSetSystemMsg : システムメッセージ
	 */
	public ExceptionBase(Exception e, String strSetErrmsg, String stSetrErrShori, String strSetMsgNo, String strSetErrCd, String strSetSystemMsg) {
		//スーパークラスのコンストラクタを呼び出し
		super(e);

		this.strErrmsg = strSetErrmsg;
		this.strErrShori = stSetrErrShori;
		this.strMsgNo = strSetMsgNo;
		this.strErrCd = strSetErrCd;
		this.strSystemMsg = strSetSystemMsg;
	}

	/**
	 * エラーメッセージ ゲッター
	 * @return strErrmsg : エラーメッセージの値を返す
	 */
	public String getStrErrmsg() {
		return strErrmsg;
	}

	/**
	 * エラーメッセージ セッター
	 * @param _strErrmsg : エラーメッセージの値を格納する
	 */
	public void setStrErrmsg(String _strErrmsg) {
		this.strErrmsg = _strErrmsg;
	}

	/**
	 * エラー処理名 ゲッター
	 * @return strErrShori : エラー処理名の値を返す
	 */
	public String getStrErrShori() {
		return strErrShori;
	}

	/**
	 * エラー処理名 セッター
	 * @param _strErrShori : エラー処理名の値を格納する
	 */
	public void setStrErrShori(String _strErrShori) {
		this.strErrShori = _strErrShori;
	}

	/**
	 * メッセージNo ゲッター
	 * @return strMsgNo : メッセージNoの値を返す
	 */
	public String getStrMsgNo() {
		return strMsgNo;
	}

	/**
	 * メッセージNo セッター
	 * @param _strMsgNo : メッセージNoの値を格納する
	 */
	public void setStrMsgNo(String _strMsgNo) {
		this.strMsgNo = _strMsgNo;
	}

	/**
	 * エラーコード ゲッター
	 * @return strErrCd : エラーコードの値を返す
	 */
	public String getStrErrCd() {
		return strErrCd;
	}

	/**
	 * エラーコード セッター
	 * @param _strErrCd : エラーコードの値を格納する
	 */
	public void setStrErrCd(String _strErrCd) {
		this.strErrCd = _strErrCd;
	}

	/**
	 * システムメッセージ ゲッター
	 * @return strSystemMsg : システムメッセージの値を返す
	 */
	public String getStrSystemMsg() {
		return strSystemMsg;
	}

	/**
	 * システムメッセージ セッター
	 * @param _strSystemMsg : システムメッセージの値を格納する
	 */
	public void setStrSystemMsg(String _strSystemMsg) {
		this.strSystemMsg = _strSystemMsg;
	}
	
}
