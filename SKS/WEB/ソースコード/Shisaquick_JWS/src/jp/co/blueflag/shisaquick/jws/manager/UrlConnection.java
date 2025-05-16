package jp.co.blueflag.shisaquick.jws.manager;

import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * URL通信
 *  : URLを設定し、ブラウザを起動する
 *
 */
public class UrlConnection {
	
	private String TARGET_URL = "";
	private ExceptionBase ex;

	/**
	 * コンストラクタ
	 */
	public UrlConnection(){
		
	}
	
	/**
	 * コンストラクタ
	 *  : ブラウザを起動し、引数のURLよりページを表示
	 * @param strUrl : 接続先アドレス
	 */
	public void urlFileDownLoad(String strUrl) throws ExceptionBase{
		
		this.TARGET_URL = strUrl;
		
		try {
			String strServletURL = DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax();
			strServletURL = strServletURL.replaceAll("AjaxServlet", "FileDownLoadServlet") + "?FName=" + this.TARGET_URL;
			
			Runtime.getRuntime().exec(new String[]{"C:\\Program Files\\Internet Explorer\\iexplore.exe",strServletURL});
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("ファイルダウンロードに失敗しました。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}
		
	}
	
}
