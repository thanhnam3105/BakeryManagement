package jp.co.blueflag.shisaquick.jws.cellrenderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * �f�t�H���g�Z�������_���[
 *  : �f�t�H���g�̃Z�������_���[��ݒ肷��
 *
 */
public class DefaultCellRenderer extends DefaultTableCellRenderer{
	
	private JComponent cmpRend = new TextboxBase();	//�e�L�X�g�t�B�[���h�i�C���X�^���X�����p�j
	private Color color = new Color(0, 0, 0);		//�F�w��
	
	/**
	 * �f�t�H���g�Z���G�f�B�^�[�@�R���X�g���N�^
	 * @param  base : �����_���p�R���|�[�l���g
	 */
    public DefaultCellRenderer(JComponent base) {
    	super();
    	cmpRend = base;
    	color = cmpRend.getBackground();
    }
    
    /**
	 * �iTableCellRenderer�j����
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		
		this.setText((value!=null)?value.toString():"");
		this.setFont(cmpRend.getFont());
		
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
}