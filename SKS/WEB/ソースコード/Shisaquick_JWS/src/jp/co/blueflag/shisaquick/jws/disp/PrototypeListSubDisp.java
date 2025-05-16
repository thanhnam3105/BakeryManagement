package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.panel.PrototypeListPanel;

/**
 * 
 * 【A02-06】 試作列追加サブ画面
 *  : 試作列追加サブ画面の操作を行う
 * 
 * @author TT.katayama
 * @since 2009/04/03
 */
public class PrototypeListSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private PrototypeListPanel prototypeListPanel;	//試作列追加パネルクラス
	private ExceptionBase ex;								//エラー操作クラス
	private String strTitle;

	/**
	 * コンストラクタ 
	 * @param strOutput : 画面タイトル
	 * @throws ExceptionBase 
	 */
	public PrototypeListSubDisp(String strOutput) throws ExceptionBase {
		//１．スーパークラスのコンストラクタを定義（画面タイトル設定）
		super(JwsConstManager.JWS_TITLE + "　"+strOutput);
		strTitle = strOutput;
		
		try {
			//画面の位置、サイズを指定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.setSize(620, 370);
			this.setSize(632,379);
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.setLocationRelativeTo(null);
			
			//試作列追加パネルインスタンスを生成
			this.prototypeListPanel = new PrototypeListPanel(strTitle); 
			this.getContentPane().add(this.prototypeListPanel);
			
			//画面を表示状態にする
			this.setVisible(false);
			

		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作列追加サブ画面のコンストラクタが失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
			
		}
	}
	
	
	public void initPanel() throws ExceptionBase {
		try {
			//試作列追加パネルインスタンスを生成
			this.remove(prototypeListPanel);
			this.prototypeListPanel = new PrototypeListPanel(strTitle); 
			this.getContentPane().add(this.prototypeListPanel);
			
		}catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作列追加サブ画面の初期化処理が失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
			
		}
	}
	
	//パネルゲッター＆セッター
	public PrototypeListPanel getPrototypeListPanel() {
		return prototypeListPanel;
	}

	public void setPrototypeListPanel(PrototypeListPanel prototypeListPanel) {
		this.prototypeListPanel = prototypeListPanel;
	}
	
	
}