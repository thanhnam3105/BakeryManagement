package jp.co.blueflag.shisaquick.jws.button;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import jp.co.blueflag.shisaquick.jws.common.ButtonBase;

/**
 * 
 * リンクボタン
 * 
 * @author TT.katayama
 * @since 2009/04/03
 *
 */
public class LinkButton extends ButtonBase {
	private static final long serialVersionUID = 1L;
	
	/**
	 * コンストラクタ
	 * @param strText : テキスト
	 */
	public LinkButton(String strText) {
		//スーパークラスのコンストラクタを呼び出す
		super();
		
		//ボタン名をセット
		this.setText(strText);
		//Fontを設定
		this.setFont(new Font("Default", Font.PLAIN, 14));
		//枠線を消去
		this.setBorderPainted(false);
		//背景を消去
		this.setContentAreaFilled(false);
		
		//フォーカス制御処理の設定
		this.addFocusListener(this.getFocusEvent());
	}
	
	/**
	 * コンストラクタ(座標指定)
	 * @param strText : テキスト
	 * @param x : X座標
	 * @param y : Y座標
	 */
	public LinkButton(String strText, int x, int y) {
		//スーパークラスのコンストラクタを呼び出す
		super();
		
		//ボタン名をセット
		this.setText(strText);
		//サイズ設定
		this.setBounds(x,y, 50,50);
		//Fontを設定
		this.setFont(new Font("Default", Font.PLAIN, 14));
		//枠線を消去
		this.setBorderPainted(false);
		//背景を消去
		this.setContentAreaFilled(false);
		
		//フォーカス制御処理の設定
		this.addFocusListener(this.getFocusEvent());
	}
	
	/**
	 * FocusListener取得メソッド
	 * @return FocusListener
	 */
	private FocusListener getFocusEvent() {
		return new FocusListener() {
			/**
			 * フォーカス時
			 */
			public void focusGained(FocusEvent e) {
				if ( !isEnabled() ) {
					transferFocus();
				}
			}
			/**
			 * ロストフォーカス時
			 */
			public void focusLost(FocusEvent e) {}
		};
	}
}
