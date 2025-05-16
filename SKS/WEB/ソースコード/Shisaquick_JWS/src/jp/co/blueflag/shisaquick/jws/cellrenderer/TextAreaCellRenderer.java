package jp.co.blueflag.shisaquick.jws.cellrenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.TextAreaBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * �e�L�X�g�t�B�[���h�@�Z�������_���[
 *  : �e�L�X�g�t�B�[���h�̃Z�������_���[��ݒ肷��
 *
 */
public class TextAreaCellRenderer extends TextAreaBase implements TableCellRenderer {
	
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1); 
	private TextAreaBase cmpRend;
	private Color color = Color.white;
	
	/**
	 * �e�L�X�g�t�B�[���h�Z�������_���[�@�R���X�g���N�^
	 * @param  base : �����_���p�R���|�[�l���g
	 */
    public TextAreaCellRenderer() {
    	super();
    	this.setLineWrap(true);
    	this.setBorder(noFocusBorder);
    	
    }
    
    /**
	 * �iTableCellRenderer�j����
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		this.setText((value!=null)?value.toString():"");
		
		//�t�H�[�J�X���ɔw�i�F�ύX
		if(hasFocus){
			this.setBackground(JwsConstManager.TABLE_SELECTED_COLOR);
			
		}else{
			//�I�����ɔw�i�F�ύX
			if(isSelected){
				this.setBackground(JwsConstManager.TABLE_GYO_SELECTED_COLOR);
				
			}else{
				this.setBackground(color);
				
			}
		}
		
		return this;
	}
	
	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
