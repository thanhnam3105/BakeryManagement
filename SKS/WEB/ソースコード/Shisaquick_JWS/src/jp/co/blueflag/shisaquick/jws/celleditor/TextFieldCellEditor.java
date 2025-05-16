package jp.co.blueflag.shisaquick.jws.celleditor;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import jp.co.blueflag.shisaquick.jws.common.TextboxBase;

/**
 * 
 * テキストフィールドセルエディター
 *  : テキストフィールドのセルエディターを設定する
 *
 */
public class TextFieldCellEditor extends DefaultCellEditor {
	
	private static final long serialVersionUID = 1L;
	
	//直接入力メンバ
	boolean directInput = true;
	
	/**
	 * テキストフィールドセルエディター　コンストラクタ
	 * @param  TextBase : エディタ用コンポーネント
	 */
	public TextFieldCellEditor(TextboxBase TextBase) {
		super(TextBase);
		((JComponent)this.getComponent()).setBorder(new EmptyBorder(1, 1, 1, 1));
		
		((JTextComponent)this.getComponent()).addFocusListener(new FocusAdapter() {
			  public void focusGained(FocusEvent e) {
			    ((JTextComponent)e.getSource()).selectAll();
			  }
		});
		
	}
	
	public boolean isCellEditable(EventObject anEvent) {
	    if (anEvent instanceof MouseEvent) {
	    	return ((MouseEvent)anEvent).getClickCount() >= 2;
	    }
	    return true;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
			 boolean isSelected,
			 int row, int column) {
		
		delegate.setValue(value);
		
		//直接入力の場合（選択状態にする）
		if(directInput){
			((JTextField)editorComponent).selectAll();
		}else{
			
		}
		
		return editorComponent;
	}
	
	/**
	 * 直接入力メンバ　ゲッター
	 * @return
	 */
	public boolean isDirectInput() {
		return directInput;
	}
	/**
	 * 直接入力メンバ　セッター
	 * @return
	 */
	public void setDirectInput(boolean directInput) {
		this.directInput = directInput;
	}
	
}