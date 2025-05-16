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
 * チェックボックスセルレンダラー
 *  : チェックボックスのセルレンダラーを設定する
 *
 */
public class CheckBoxCellRenderer extends CheckboxBase implements TableCellRenderer {
	
	private CheckboxBase chkRend;				//チェックボックス
	private Color color = new Color(0, 0, 0);	//色指定
	
	/**
	 * チェックボックスセルエディター　コンストラクタ
	 * @param  base : レンダラ用コンポーネント
	 */
    public CheckBoxCellRenderer(CheckboxBase base) {
    	chkRend = base;
    	color = chkRend.getBackground();
    }
    
    /**
	 * （TableCellRenderer）実装
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		//フォーカス時に背景色変更
		if(hasFocus){
			chkRend.setBackground(JwsConstManager.TABLE_SELECTED_COLOR);
		}else{
			//選択時に背景色変更
			if(isSelected){
				chkRend.setBackground(JwsConstManager.TABLE_GYO_SELECTED_COLOR);
			}else{
				chkRend.setBackground(color);
			}
		}
		
		//テーブル設定値がnullでない場合
		if(value != null){
			
			//テーブル設定値がtrueの場合
			if(value.toString().equals("true")){
				
				//チェックボックスをon
				chkRend.setSelected(true);
				
			//テーブル設定値がtrue以外の場合
			}else if(value.toString().equals("1")){
				
				//チェックボックスをon
				chkRend.setSelected(true);
				
			}else{
				
				//チェックボックスをoff
				chkRend.setSelected(false);
				
			}
			
		//テーブル設定値がnullの場合
		}else{
			
			//チェックボックスをoff
			chkRend.setSelected(false);
			
		}
		
		//チェックボックス返却
		return chkRend;
	}
}