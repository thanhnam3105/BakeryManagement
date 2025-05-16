package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * 
 * テキストボックス操作
 *  ： アプリ内テキストボックスの基本動作を行う
 */
public class TextboxBase extends JTextField implements FocusControl {

	private static final long serialVersionUID = 1L;	//デフォルトシリアルID
	
	private ExceptionBase ex;						//エラー操作
	private   String pk1;						//キー１
	private   String pk2;						//キー２
	private   String pk3;						//キー３
	private   String pk4;						//キー４
	
	/**
	 * コンストラクタ
	 */
	public TextboxBase(){
		//スーパークラスのコンストラクタの呼び出し
		super();
		
		this.ex = new ExceptionBase();
		this.setFont(new Font("Default", Font.PLAIN, 12));
		this.setDisabledTextColor(Color.BLACK);
	}
	
	/**
	 * コンストラクタ
	 */
	public TextboxBase(String s){
		//スーパークラスのコンストラクタの呼び出し
		super(s);
		
		this.ex = new ExceptionBase();
		this.setFont(new Font("Default", Font.PLAIN, 11));
		this.setDisabledTextColor(Color.BLACK);
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
			this.ex.setStrErrmsg("TextboxBaseのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}
	
	/**
	 * キー１ゲッター＆セッター
	 */
	public String getPk1() {
		return pk1;
	}
	public void setPk1(String pk1) {
		this.pk1 = pk1;
	}
	/**
	 * キー２ゲッター＆セッター
	 */
	public String getPk2() {
		return pk2;
	}
	public void setPk2(String pk2) {
		this.pk2 = pk2;
	}
	/**
	 * キー３ゲッター＆セッター
	 */
	public String getPk3() {
		return pk3;
	}
	public void setPk3(String pk3) {
		this.pk3 = pk3;
	}
	/**
	 * キー４ゲッター＆セッター
	 */
	public String getPk4() {
		return pk4;
	}
	public void setPk4(String pk4) {
		this.pk4 = pk4;
	}
	
}
