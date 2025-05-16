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
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * �e�L�X�g�t�B�[���h�@�Z�������_���[
 *  : �e�L�X�g�t�B�[���h�̃Z�������_���[��ݒ肷��
 *
 */
public class TextFieldCellRenderer extends TextboxBase implements TableCellRenderer {
	
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1); 
	private TextboxBase cmpRend;	//�e�L�X�g�t�B�[���h�i�C���X�^���X�����p�j
	private Color color = new Color(0, 0, 0);		//�F�w��
	
	/**
	 * �e�L�X�g�t�B�[���h�Z�������_���[�@�R���X�g���N�^
	 * @param  base : �����_���p�R���|�[�l���g
	 */
    public TextFieldCellRenderer(TextboxBase base) {
    	super();
    	cmpRend = base;
    	cmpRend.setBorder(noFocusBorder);
    	color = cmpRend.getBackground();
    	
    }
    
    /**
	 * �iTableCellRenderer�j����
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		cmpRend.setText((value!=null)?value.toString():"");
		cmpRend.setFont(cmpRend.getFont());
		
		//�t�H�[�J�X���ɔw�i�F�ύX
		if(hasFocus){
			cmpRend.setBackground(JwsConstManager.TABLE_SELECTED_COLOR);
			
		}else{
			//�I�����ɔw�i�F�ύX
			if(isSelected){
				cmpRend.setBackground(JwsConstManager.TABLE_GYO_SELECTED_COLOR);
				
			}else{
				cmpRend.setBackground(color);
				
			}
		}
		
		
		
		return cmpRend;
	}
	
	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}