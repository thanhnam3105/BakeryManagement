package jp.co.blueflag.shisaquick.jws.celleditor;

import java.awt.Color;
import java.awt.Component;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * コンボボックスセルエディター
 *  : コンボボックスのセルエディターを設定する
 *
 */
public class ComboBoxCellEditor extends DefaultCellEditor {

	private Border noFocusBorder = new EmptyBorder(0, 0, 0, 0);				//ボーダークラス
	
	/**
	 * コンボボックスセルエディター　コンストラクタ
	 * @param  base : エディタ用コンポーネント
	 */
	public ComboBoxCellEditor(ComboBase base) {
		super(base);
		((JComponent)this.getComponent()).setBorder(noFocusBorder);
	}
	
	/**
	 * （DefaultCellEditor）オーバーライド
	 */
	public boolean isCellEditable(EventObject anEvent) {
		((JComponent)this.getComponent()).setBackground(JwsConstManager.TABLE_SELECTED_COLOR);
		return delegate.isCellEditable(anEvent);
	}

}