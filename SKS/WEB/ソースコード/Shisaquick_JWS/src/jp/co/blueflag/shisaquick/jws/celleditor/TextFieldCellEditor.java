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
 * �e�L�X�g�t�B�[���h�Z���G�f�B�^�[
 *  : �e�L�X�g�t�B�[���h�̃Z���G�f�B�^�[��ݒ肷��
 *
 */
public class TextFieldCellEditor extends DefaultCellEditor {
	
	private static final long serialVersionUID = 1L;
	
	//���ړ��̓����o
	boolean directInput = true;
	
	/**
	 * �e�L�X�g�t�B�[���h�Z���G�f�B�^�[�@�R���X�g���N�^
	 * @param  TextBase : �G�f�B�^�p�R���|�[�l���g
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
		
		//���ړ��͂̏ꍇ�i�I����Ԃɂ���j
		if(directInput){
			((JTextField)editorComponent).selectAll();
		}else{
			
		}
		
		return editorComponent;
	}
	
	/**
	 * ���ړ��̓����o�@�Q�b�^�[
	 * @return
	 */
	public boolean isDirectInput() {
		return directInput;
	}
	/**
	 * ���ړ��̓����o�@�Z�b�^�[
	 * @return
	 */
	public void setDirectInput(boolean directInput) {
		this.directInput = directInput;
	}
	
}