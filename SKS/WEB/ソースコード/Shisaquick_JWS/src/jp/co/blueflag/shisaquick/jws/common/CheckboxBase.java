package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * 
 * チェックボックス操作
 *  : アプリ内チェックボックスの基本動作を行う
 *
 */
public class CheckboxBase extends JCheckBox implements FocusControl {

	private static final long serialVersionUID = 1L;	//デフォルトシリアルID
	
	private   ExceptionBase ex;					//エラー操作
	private   String pk1;						//キー１
	private   String pk2;						//キー２
	private   String pk3;						//キー３
	private   String pk4;						//キー４
	private   String pk5;						//キー５
	
	/**
	 * コンストラクタ
	 */
	public CheckboxBase(){
		//スーパークラスの呼び出し
		super();
		
		this.ex = null;
		this.setFont(new Font("Default", Font.PLAIN, 12));
		this.setIcon(new MyCheckBoxIcon2());
		this.setHorizontalAlignment(JCheckBox.CENTER);
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
		}  catch ( Exception e ) {			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("CheckboxBaseのフォーカス制御処理が失敗しました");
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
	/**
	 * キー５ゲッター＆セッター
	 */
	public String getPk5() {
		return pk5;
	}
	public void setPk5(String pk5) {
		this.pk5 = pk5;
	}
	
	/**
	 * 専用アイコンクラス
	 */
	public class MyCheckBoxIcon2 implements Icon {  
		
		private final Icon orgIcon = UIManager.getIcon("CheckBox.icon"); 
		  
		public void paintIcon(Component c, Graphics g, int x, int y) {  
			  
			orgIcon.paintIcon(c, g, x, y);    
			AbstractButton b = (AbstractButton)c;    
			ButtonModel model = b.getModel();    
			g.setColor(Color.WHITE);    
			g.fillRect(x+2,y+2,getIconWidth()-4,getIconHeight()-4);    
			if(model.isSelected()) {
				g.setColor(Color.BLACK);      
				g.drawLine(x+9, y+3, x+9, y+3);      
				g.drawLine(x+8, y+4, x+9, y+4);      
				g.drawLine(x+7, y+5, x+9, y+5);      
				g.drawLine(x+6, y+6, x+8, y+6);      
				g.drawLine(x+3, y+7, x+7, y+7);      
				g.drawLine(x+4, y+8, x+6, y+8);      
				g.drawLine(x+5, y+9, x+5, y+9);      
				g.drawLine(x+3, y+5, x+3, y+5);      
				g.drawLine(x+3, y+6, x+4, y+6);    
			}  
		}
		  
		public int getIconWidth() {    
			return orgIcon.getIconWidth();  
		}
		
		public int getIconHeight() {    
			return orgIcon.getIconHeight();  
		}
	}
	
}
