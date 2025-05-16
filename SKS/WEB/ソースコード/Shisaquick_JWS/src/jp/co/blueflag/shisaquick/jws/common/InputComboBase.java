package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.*;

/**
 * 
 * コンボボックス（入力可）操作
 *  : アプリ内コンボボックス（入力可）の基本動作を行う
 *
 */
public class InputComboBase extends JComboBox implements FocusControl {

	private static final long serialVersionUID = 1L;	//デフォルトシリアルID
	
	private ExceptionBase ex;						//エラー操作
	
	private String[] cmbData;						//コンボボックス生成用配列
	private String strText;							//テキスト
	
	private Color Yellow = JwsConstManager.SHISAKU_ITEM_COLOR2;
	
	/**
	 * コンストラクタ
	 */
	public InputComboBase(){
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		this.cmbData = null;
		this.strText = "";
		this.ex = new ExceptionBase();
		this.setEditable(true);
		this.setBackground(Yellow);
		this.getEditor().getEditorComponent().setBackground(Yellow);
		this.setFont(new Font("Default", Font.PLAIN, 12));
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
			this.ex.setStrErrmsg("InputComboBaseのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
	
	/**
	 * コンボボックス生成用配列 ゲッター
	 * @return cmbData : コンボボックス生成用配列の値を返す
	 */
	public String[] getCmbData() {
		return cmbData;
	}
	/**
	 * コンボボックス生成用配列 セッター
	 * @param cmbData : コンボボックス生成用配列の値を格納する
	 */
	public void setCmbData(String[] _cmbData) {
		this.cmbData = _cmbData;
	}

	/**
	 * テキスト ゲッター
	 * @return strText : テキストの値を返す
	 */
	public String getStrText() {
		return strText;
	}
	/**
	 * テキスト セッター
	 * @param strText : テキストの値を格納する
	 */
	public void setStrText(String _strText) {
		this.strText = _strText;
	}

}
