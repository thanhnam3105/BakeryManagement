package jp.co.blueflag.shisaquick.jws.common;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

/**
 * 
 * Jnlp接続クラス
 * 　：　jnlpの情報を取得する
 * @author k-katayama
 *
 */
public class JnlpConnect {
	
	private String strAddressAjax;		//Ajax通信用アドレス
	private ExceptionBase ex;				//エラーハンドラ

	/**
	 * コンストラクタ
	 */
	public JnlpConnect() {
		this.ex = null;
		this.strAddressAjax = "";
	}

	/**
	 * Ajax通信用アドレス設定
	 *  : Jnlpのcodebase値より、Ajax通信用のアドレスを取得する 
	 * @throws ExceptionBase 
	 * @throws UnavailableServiceException 
	 */
	public void setAddressAjax() throws ExceptionBase, UnavailableServiceException {
		this.strAddressAjax = "";
		
		try {
			//codebase値を取得する
			BasicService basicService = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
			String strCodeBase = basicService.getCodeBase().toString();
			//アドレスに設定
			if ( !strCodeBase.equals("") ) {
				this.strAddressAjax = strCodeBase.substring(0, strCodeBase.length()-4) + "AjaxServlet";
			}
		} catch (UnavailableServiceException e) {
			//JNLPから接続していない場合は、処理を行わない
			this.strAddressAjax = "";
			throw e;
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("Ajax通信用アドレス取得処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
		}
		
	}
	
	/**
	 * Ajax通信用アドレス ゲッター
	 * @return Ajax通信用アドレスを返す
	 */
	public String getStrAddressAjax() {
		return this.strAddressAjax;
	}	
	/**
	 * Ajax通信用アドレス セッター
	 * @param Ajax通信用アドレスを取得
	 */
	public void setStrAddressAjax(String _strAddressAjax) {
		this.strAddressAjax = _strAddressAjax;
	}
	
}
