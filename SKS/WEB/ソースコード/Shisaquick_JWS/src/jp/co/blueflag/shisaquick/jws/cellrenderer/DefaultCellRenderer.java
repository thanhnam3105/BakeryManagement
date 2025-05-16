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
 * デフォルトセルレンダラー
 *  : デフォルトのセルレンダラーを設定する
 *
 */
public class DefaultCellRenderer extends DefaultTableCellRenderer{
	
	private JComponent cmpRend = new TextboxBase();	//テキストフィールド（インスタンス生成用）
	private Color color = new Color(0, 0, 0);		//色指定
	
	/**
	 * デフォルトセルエディター　コンストラクタ
	 * @param  base : レンダラ用コンポーネント
	 */
    public DefaultCellRenderer(JComponent base) {
    	super();
    	cmpRend = base;
    	color = cmpRend.getBackground();
    }
    
    /**
	 * （TableCellRenderer）実装
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		
		this.setText((value!=null)?value.toString():"");
		this.setFont(cmpRend.getFont());
		
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
}