package jp.co.blueflag.shisaquick.jws.common;

import java.awt.event.KeyEvent;

import javax.swing.*;


/**
 * 
 * タブ操作の規定クラス
 *
 */
public class TabBase extends JTabbedPane implements FocusControl {

	private static final long serialVersionUID = 1L;	//デフォルトシリアルID
	
	private ExceptionBase ex;						//エラー操作
	
	/**
	 * タブ操作　コンストラクタ
	 */
	public TabBase() {
		 //スーパークラスのコンストラクタ呼び出し
		 super();
			
		this.ex = null;
	}
	
	/**
	 * Enter押下時フォーカスコントロール
	 * @param enterComp : Enter時のフォーカス移動先JComponent
	 */
	public void setEnterFocusControl(final JComponent enterComp) throws ExceptionBase {
		try {
			this.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					//KEY 押下時の処理
					if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
						enterComp.requestFocus();
					}
				}
			});
		} catch ( Exception e ) {			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("TabBaseのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
}
