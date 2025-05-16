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
 * コンボボックスセルレンダラー
 *  : コンボボックスのセルレンダラーを設定する
 *
 */
public class ComboBoxCellRenderer extends ComboBase implements TableCellRenderer {
	
    private DefaultComboBoxModel model;		//コンボモデル
    private Color color = new Color(0,0,0);	//色指定
    
    /**
	 * コンボボックスセルエディター　コンストラクタ
	 * @param  base : レンダラ用コンポーネント
	 */
    public ComboBoxCellRenderer(ComboBase base) {
        super();
        color = base.getBackground();
        this.setFont(base.getFont());
        this.initialize(base.getModel());
    }
    
    /**
	 * コンボボックスセルエディター　初期処理
	 * @param  listModel : リストモデル
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
	 * （TableCellRenderer）実装
	 */
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
    	
        int index = this.model.getIndexOf(value);
        if ( index != -1 ) {
            this.setSelectedIndex(index);
        } else {
            this.setSelectedIndex(0);
        }
        
      //フォーカス時に背景色変更
		if(hasFocus){
			this.setBackground(JwsConstManager.TABLE_SELECTED_COLOR);
		}else{
			//選択時に背景色変更
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