package jp.co.blueflag.shisaquick.jws.cellrenderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import jp.co.blueflag.shisaquick.jws.common.CheckboxBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * �`�F�b�N�{�b�N�X�Z�������_���[
 *  : �`�F�b�N�{�b�N�X�̃Z�������_���[��ݒ肷��
 *
 */
public class CheckBoxCellRenderer extends CheckboxBase implements TableCellRenderer {
	
	private CheckboxBase chkRend;				//�`�F�b�N�{�b�N�X
	private Color color = new Color(0, 0, 0);	//�F�w��
	
	/**
	 * �`�F�b�N�{�b�N�X�Z���G�f�B�^�[�@�R���X�g���N�^
	 * @param  base : �����_���p�R���|�[�l���g
	 */
    public CheckBoxCellRenderer(CheckboxBase base) {
    	chkRend = base;
    	color = chkRend.getBackground();
    }
    
    /**
	 * �iTableCellRenderer�j����
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		//�t�H�[�J�X���ɔw�i�F�ύX
		if(hasFocus){
			chkRend.setBackground(JwsConstManager.TABLE_SELECTED_COLOR);
		}else{
			//�I�����ɔw�i�F�ύX
			if(isSelected){
				chkRend.setBackground(JwsConstManager.TABLE_GYO_SELECTED_COLOR);
			}else{
				chkRend.setBackground(color);
			}
		}
		
		//�e�[�u���ݒ�l��null�łȂ��ꍇ
		if(value != null){
			
			//�e�[�u���ݒ�l��true�̏ꍇ
			if(value.toString().equals("true")){
				
				//�`�F�b�N�{�b�N�X��on
				chkRend.setSelected(true);
				
			//�e�[�u���ݒ�l��true�ȊO�̏ꍇ
			}else if(value.toString().equals("1")){
				
				//�`�F�b�N�{�b�N�X��on
				chkRend.setSelected(true);
				
			}else{
				
				//�`�F�b�N�{�b�N�X��off
				chkRend.setSelected(false);
				
			}
			
		//�e�[�u���ݒ�l��null�̏ꍇ
		}else{
			
			//�`�F�b�N�{�b�N�X��off
			chkRend.setSelected(false);
			
		}
		
		//�`�F�b�N�{�b�N�X�ԋp
		return chkRend;
	}
}