package jp.co.blueflag.shisaquick.jws.common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * パネル操作 : 部分表示枠の基本クラス
 *
 */
public class PanelBase extends JPanel implements FocusControl {

	private static final long serialVersionUID = 1L;	//デフォルトシリアルID
	
	private ExceptionBase ex;						//エラー操作
	
	 /**
	  * パネル操作　コンストラクタ
	  */
	 public PanelBase(){
		 //スーパークラスのコンストラクタ呼び出し
		 super();
		 this.ex = new ExceptionBase();
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
			this.ex.setStrErrmsg("PanelBaseのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}	

}
