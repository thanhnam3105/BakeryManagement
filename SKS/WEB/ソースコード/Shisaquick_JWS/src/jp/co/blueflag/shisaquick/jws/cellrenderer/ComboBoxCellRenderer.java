package jp.co.blueflag.shisaquick.jws.cellrenderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.table.TableCellRenderer;

import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * �R���{�{�b�N�X�Z�������_���[
 *  : �R���{�{�b�N�X�̃Z�������_���[��ݒ肷��
 *
 */
public class ComboBoxCellRenderer extends ComboBase implements TableCellRenderer {
	
    private DefaultComboBoxModel model;		//�R���{���f��
    private Color color = new Color(0,0,0);	//�F�w��
    
    /**
	 * �R���{�{�b�N�X�Z���G�f�B�^�[�@�R���X�g���N�^
	 * @param  base : �����_���p�R���|�[�l���g
	 */
    public ComboBoxCellRenderer(ComboBase base) {
        super();
        color = base.getBackground();
        this.setFont(base.getFont());
        this.initialize(base.getModel());
    }
    
    /**
	 * �R���{�{�b�N�X�Z���G�f�B�^�[�@��������
	 * @param  listModel : ���X�g���f��
	 */
    private void initialize(ListModel listModel) {
        Object[] items = new Object[listModel.getSize()];
        for ( int i = 0; i < items.length; i++ ) {
            items[i] = listModel.getElementAt(i);
        }
        this.model = new DefaultComboBoxModel(items);
        this.setModel(this.model);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
    
    /**
	 * �iTableCellRenderer�j����
	 */
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
    	
        int index = this.model.getIndexOf(value);
        if ( index != -1 ) {
            this.setSelectedIndex(index);
        } else {
            this.setSelectedIndex(0);
        }
        
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