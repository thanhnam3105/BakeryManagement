package jp.co.blueflag.shisaquick.jws.common;

import javax.swing.JComponent;

/**
 * 
 * フォーカスコントロール用インターフェース
 *
 */
public interface FocusControl {

	/**
	 * Enter押下時フォーカスコントロール
	 * @param enterComp : Enter時のフォーカス移動先JComponent
	 */
	public void setEnterFocusControl(final JComponent enterComp) throws ExceptionBase;
}
