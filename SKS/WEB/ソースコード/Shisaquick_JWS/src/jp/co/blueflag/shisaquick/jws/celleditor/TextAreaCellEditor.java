package jp.co.blueflag.shisaquick.jws.celleditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.peer.ComponentPeer;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;

import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TextAreaBase;

/**
 * 
 * �e�L�X�g�G���A�Z���G�f�B�^�[
 *  : �e�L�X�g�G���A�̃Z���G�f�B�^�[��ݒ肷��
 *
 */
public class TextAreaCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	private Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);	//�{�[�_�[
	private TextAreaBase textArea = new TextAreaBase();			//�e�L�X�g�G���A
	private ScrollBase scroll = new ScrollBase(textArea);		//�X�N���[��
	private boolean editMode = true;
	
	/**
	 * �R���{�{�b�N�X�Z���G�f�B�^�[�@�R���X�g���N�^
	 */
	public TextAreaCellEditor(JTable table) {
		
		//�����ݒ�
		textArea.setBorder(noFocusBorder);
		textArea.setLineWrap(true);
		scroll.setBorder(noFocusBorder);
		scroll.setHorizontalScrollBarPolicy(ScrollBase.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollBase.VERTICAL_SCROLLBAR_NEVER);
		
		//�e�[�u���̃A�N�V�����}�b�v���擾
		ActionMap am = table.getActionMap();
		
		//�A�N�V�����擾
		final Action oldStartEditingAction = am.get("startEditing");
		
		//�V�K�A�N�V�����ݒ�iF2�{�^���������̕ҏW���j
		am.put("startEditing", new AbstractAction() {
			
			public void actionPerformed(ActionEvent e) {
				
				//���A�N�V�����C�x���g���s
				oldStartEditingAction.actionPerformed(e);
				
				//�e�L�X�g�G���A�Ƀt�H�[�J�X
				textArea.requestFocusInWindow();
				
			}
			
		});
		
	}

	/**
	 * �igetCellEditorValue�j����
	 */
	public Object getCellEditorValue() {
		return textArea.getText();
	}
	
	/**
	 * �iisCellEditable�j�I�[�o���C�h
	 */
	public boolean isCellEditable(EventObject anEvent) {
		
		textArea.setBackground(JwsConstManager.TABLE_SELECTED_COLOR);
		
		if (anEvent instanceof MouseEvent) {
			return ((MouseEvent) anEvent).getClickCount() >= 2;
		}
		
		return true;
	}

	/**
	 * �ishouldSelectCell�j�I�[�o���C�h
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	/**
	 * �istopCellEditing�j�I�[�o���C�h
	 */
	public boolean stopCellEditing() {
		fireEditingStopped();
		return true;
	}

	/**
	 * �icancelCellEditing�j�I�[�o���C�h
	 */
	public void cancelCellEditing() {
		fireEditingCanceled();
	}

	/**
	 * �igetTableCellEditorComponent�j����
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		
		// �e�[�u���̒l���e�L�X�g�G���A�֔��f
		textArea.setText((value != null) ? value.toString() : "");
		
		// ���ړ��͎��ɕҏW���[�h�ɂ���
		//TextAreaFocus(table, row, column);
		
		// �e�L�X�g�G���A����S�I��
		textArea.selectAll();
		
		// �X�N���[���p�l���R���|�[�l���g��ԋp
		return scroll;
	}
	
	/**
	 * �e�L�X�g�G���A�t�H�[�J�X
	 */
	public void TextAreaFocus(JTable table, int row, int column){
		
		//���ړ��͎��ɕҏW���[�h�ɂ���
		if(editMode){
			
			//�ҏW���[�h��ҏW�s�ɐݒ�
			//��1�x�����ҏW���[�h���ɐݒ肷��
			//�@��L�ݒ肪�Ȃ��ꍇ�AeditCellAt���\�b�h�ɂ�薳�����[�v�Ɋׂ�
			editMode = false;
			
			//�����I�ɃG�f�B�b�g���[�h
			table.editCellAt(row, column);
			
			//�e�L�X�g�G���A�Ƀt�H�[�J�X
			textArea.requestFocusInWindow();
		}
		
		//�ҏW�J�E���g��ҏW�ɐݒ�
		editMode = true;
	}
	
	/**
	 * �e�L�X�g�G���A �Q�b�^�[
	 * @return TextAreaBase : �e�L�X�g�G���A��Ԃ�
	 */
	public TextAreaBase getTextArea() {
		return textArea;
	}
	
	/**
	 * �e�L�X�g�G���A �Z�b�^�[
	 * @param textArea : �e�L�X�g�G���A��ݒ�
	 */
	public void setTextArea(TextAreaBase textArea) {
		this.textArea = textArea;
	}
}
