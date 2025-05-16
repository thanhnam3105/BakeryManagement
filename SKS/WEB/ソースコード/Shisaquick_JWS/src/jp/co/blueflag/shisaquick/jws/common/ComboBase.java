package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * 
 * コンボボックス操作
 *  : アプリ内コンボボックスの基本動作を行う
 *
 */
public class ComboBase extends JComboBox implements FocusControl {

	private static final long serialVersionUID = 1L;	//デフォルトシリアルID
	
	private ExceptionBase ex;						//エラー操作
	private   String pk1;						//キー１
	private   String pk2;						//キー２
	private   String pk3;						//キー３
	private   String pk4;						//キー４
	
	private String[] cmbData;						//コンボボックス生成用配列
	
	/**
	 * コンストラクタ
	 */
	public ComboBase(){
		//スーパークラスのコンストラクタを呼び出し
		super();
		
		this.cmbData = null;
		
		this.ex = null;
		this.setBackground(Color.WHITE);
		this.setFont(new Font("Default", Font.PLAIN, 12));

	}
	
	/**
	 * コンストラクタ
	 */
	public ComboBase(String[] str){
		//スーパークラスのコンストラクタを呼び出し
		super(str);
		
		this.cmbData = null;
		
		this.ex = null;
		this.setBackground(Color.WHITE);
		this.setFont(new Font("Default", Font.PLAIN, 11));
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
			this.ex.setStrErrmsg("ComboBaseのフォーカス制御処理が失敗しました");
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
	public String[] getCmbData(){
		return cmbData;
	}
	/**
	 * コンボボックス生成用配列 セッター
	 * @param _cmbData : コンボボックス生成用配列の値を格納する
	 */
	public void setCmbData( String[] _cmbData ){
		this.cmbData = _cmbData;
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
