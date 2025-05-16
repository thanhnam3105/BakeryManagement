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
 * テキストフィールド　セルレンダラー
 *  : テキストフィールドのセルレンダラーを設定する
 *
 */
public class TextFieldCellRenderer extends TextboxBase implements TableCellRenderer {
	
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1); 
	private TextboxBase cmpRend;	//テキストフィールド（インスタンス生成用）
	private Color color = new Color(0, 0, 0);		//色指定
	
	/**
	 * テキストフィールドセルレンダラー　コンストラクタ
	 * @param  base : レンダラ用コンポーネント
	 */
    public TextFieldCellRenderer(TextboxBase base) {
    	super();
    	cmpRend = base;
    	cmpRend.setBorder(noFocusBorder);
    	color = cmpRend.getBackground();
    	
    }
    
    /**
	 * （TableCellRenderer）実装
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		cmpRend.setText((value!=null)?value.toString():"");
		cmpRend.setFont(cmpRend.getFont());
		
		//フォーカス時に背景色変更
		if(hasFocus){
			cmpRend.setBackground(JwsConstManager.TABLE_SELECTED_COLOR);
			
		}else{
			//選択時に背景色変更
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