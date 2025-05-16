package jp.co.blueflag.shisaquick.srv.common;

/**
 * 
 * 共通例外処理クラス
 *  : 例外発生時の情報を格納。
 *    例外クラスの基底クラス。
 *  
 * @author TT.k-katayama
 * @since  2009/03/25
 *
 */
public class ExceptionBase extends Exception {
	private static final long serialVersionUID = 1L;		//デフォルトシリアルバージョンID
	
	private String UserMsg = "";			//ユーザーメッセージ
	private String ClassName = "";			//例外発生クラス名
	private String MsgNumber = "";			//メッセージ番号
	private String SystemErrorCD = "";		//システムエラーコード
	private String SystemErrorMsg = "";	//システムエラーメッセージ
	
	/**
	 * コンストラクタ
	 *  : 共通例外処理用コンストラクタ
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public ExceptionBase() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
	}

	/**
	 * ユーザーメッセージ ゲッター
	 * @return userMsg : ユーザーメッセージの値を返す
	 */
	public String getUserMsg() {
		return UserMsg;
	}
	/**
	 * ユーザーメッセージ セッター
	 * @param _userMsg : ユーザーメッセージの値を格納する
	 */
	public void setUserMsg(String _userMsg) {
		UserMsg = _userMsg;
	}

	/**
	 * 例外発生クラス名 ゲッター
	 * @return className : 例外発生クラス名の値を返す
	 */
	public String getClassName() {
		return ClassName;
	}
	/**
	 * 例外発生クラス名 セッター
	 * @param _className : 例外発生クラス名の値を格納する
	 */
	public void setClassName(String _className) {
		ClassName = _className;
	}

	/**
	 * メッセージ番号 ゲッター
	 * @return msgNumber : メッセージ番号の値を返す
	 */
	public String getMsgNumber() {
		return MsgNumber;
	}
	/**
	 * メッセージ番号 セッター
	 * @param _msgNumber : メッセージ番号の値を格納する
	 */
	public void setMsgNumber(String _msgNumber) {
		MsgNumber = _msgNumber;
	}
	
	/**
	 * システムエラーコード ゲッター
	 * @return systemErrorCD : システムエラーコードの値を返す
	 */
	public String getSystemErrorCD() {
		return SystemErrorCD;
	}
	/**
	 * システムエラーコード セッター
	 * @param _systemErrorCD : システムエラーコードの値を格納する
	 */
	public void setSystemErrorCD(String _systemErrorCD) {
		SystemErrorCD = _systemErrorCD;
	}

	/**
	 * システムエラーメッセージ ゲッター
	 * @return systemErrorMsg : システムエラーメッセージの値を返す
	 */
	public String getSystemErrorMsg() {
		return SystemErrorMsg;
	}
	/**
	 * システムエラーメッセージ セッター
	 * @param _systemErrorMsg : システムエラーメッセージの値を格納する
	 */
	public void setSystemErrorMsg(String _systemErrorMsg) {
		SystemErrorMsg = _systemErrorMsg;
	}
	
}
