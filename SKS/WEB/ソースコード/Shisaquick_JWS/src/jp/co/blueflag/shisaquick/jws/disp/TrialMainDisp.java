package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.Container;

import javax.swing.JFrame;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.panel.TrialDispPanel;

/**
 * 
 * 試作データ画面クラス
 * 
 */
public class TrialMainDisp extends MainFrame {
	
	private ExceptionBase ex;
	private TrialDispPanel tp;
	
	/**
	 * 試作データ画面 コンストラクタ 
	 * @param title : タイトル
	 * @throws ExceptionBase 
	 */
	public TrialMainDisp(String title) throws ExceptionBase {
		//スーパークラスのコンストラクタ呼び出し
		super(JwsConstManager.JWS_TITLE + "　"+title);
		
		try {
			
			//画面サイズ、位置の設定
			//2012/02/22 TT H.SHIMA Java6対応 start
			//this.setSize(1024, 750);
			this.setSize(1036,759);
			//2012/02/22 TT H.SHIMA Java6対応 end
			//this.setLocationRelativeTo(null);
			
			//パネルの設定
			tp = new TrialDispPanel();
			Container contentPane = this.getContentPane();
			contentPane.add (tp);
			
			
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作データ画面（Frame）のコンストラクタが失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}
	
	public TrialDispPanel getTp() {
		return tp;
	}

	public void setTp(TrialDispPanel tp) {
		this.tp = tp;
	}
	
	
}