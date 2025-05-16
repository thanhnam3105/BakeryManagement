package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * 
 * テキストエリア基本クラス
 *
 */
public class TextAreaBase extends JTextArea implements FocusControl {

	private static final long serialVersionUID = 1L;	//デフォルトシリアルID
	
	private ExceptionBase ex;						//エラー操作
	
	/**
	 * テキストエリア　コンストラクタ
	 */
	public TextAreaBase() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		this.setFont(new Font("Default", Font.PLAIN, 12));
		this.ex = new ExceptionBase();
	}
	
	/**
	 * TAB押下時フォーカスコントロール
	 */
	public void setTABFocusControl() throws ExceptionBase {
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				//KEY 押下時の処理
				if ( e.getKeyCode() == KeyEvent.VK_TAB ) {
					transferFocus();
					e.consume();
				}
			}
		});
	}
	
	/**
	 * 編集可否時の色指定
	 */
	public void setEnabled(boolean b) {
		if(b){
			this.setBackground(Color.WHITE);
		}else{
			this.setBackground(Color.LIGHT_GRAY);
		}
		super.setEnabled(b);
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
			this.ex.setStrErrmsg("TextAreaBaseのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
}
