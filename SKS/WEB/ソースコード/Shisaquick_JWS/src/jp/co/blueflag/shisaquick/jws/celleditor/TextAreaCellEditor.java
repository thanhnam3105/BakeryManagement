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
 * テキストエリアセルエディター
 *  : テキストエリアのセルエディターを設定する
 *
 */
public class TextAreaCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	private Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);	//ボーダー
	private TextAreaBase textArea = new TextAreaBase();			//テキストエリア
	private ScrollBase scroll = new ScrollBase(textArea);		//スクロール
	private boolean editMode = true;
	
	/**
	 * コンボボックスセルエディター　コンストラクタ
	 */
	public TextAreaCellEditor(JTable table) {
		
		//初期設定
		textArea.setBorder(noFocusBorder);
		textArea.setLineWrap(true);
		scroll.setBorder(noFocusBorder);
		scroll.setHorizontalScrollBarPolicy(ScrollBase.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(ScrollBase.VERTICAL_SCROLLBAR_NEVER);
		
		//テーブルのアクションマップを取得
		ActionMap am = table.getActionMap();
		
		//アクション取得
		final Action oldStartEditingAction = am.get("startEditing");
		
		//新規アクション設定（F2ボタン押下時の編集時）
		am.put("startEditing", new AbstractAction() {
			
			public void actionPerformed(ActionEvent e) {
				
				//旧アクションイベント実行
				oldStartEditingAction.actionPerformed(e);
				
				//テキストエリアにフォーカス
				textArea.requestFocusInWindow();
				
			}
			
		});
		
	}

	/**
	 * （getCellEditorValue）実装
	 */
	public Object getCellEditorValue() {
		return textArea.getText();
	}
	
	/**
	 * （isCellEditable）オーバライド
	 */
	public boolean isCellEditable(EventObject anEvent) {
		
		textArea.setBackground(JwsConstManager.TABLE_SELECTED_COLOR);
		
		if (anEvent instanceof MouseEvent) {
			return ((MouseEvent) anEvent).getClickCount() >= 2;
		}
		
		return true;
	}

	/**
	 * （shouldSelectCell）オーバライド
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	/**
	 * （stopCellEditing）オーバライド
	 */
	public boolean stopCellEditing() {
		fireEditingStopped();
		return true;
	}

	/**
	 * （cancelCellEditing）オーバライド
	 */
	public void cancelCellEditing() {
		fireEditingCanceled();
	}

	/**
	 * （getTableCellEditorComponent）実装
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		
		// テーブルの値をテキストエリアへ反映
		textArea.setText((value != null) ? value.toString() : "");
		
		// 直接入力時に編集モードにする
		//TextAreaFocus(table, row, column);
		
		// テキストエリア内を全選択
		textArea.selectAll();
		
		// スクロールパネルコンポーネントを返却
		return scroll;
	}
	
	/**
	 * テキストエリアフォーカス
	 */
	public void TextAreaFocus(JTable table, int row, int column){
		
		//直接入力時に編集モードにする
		if(editMode){
			
			//編集モードを編集不可に設定
			//※1度だけ編集モードを可に設定する
			//　上記設定がない場合、editCellAtメソッドにより無限ループに陥る
			editMode = false;
			
			//強制的にエディットモード
			table.editCellAt(row, column);
			
			//テキストエリアにフォーカス
			textArea.requestFocusInWindow();
		}
		
		//編集カウントを編集可に設定
		editMode = true;
	}
	
	/**
	 * テキストエリア ゲッター
	 * @return TextAreaBase : テキストエリアを返す
	 */
	public TextAreaBase getTextArea() {
		return textArea;
	}
	
	/**
	 * テキストエリア セッター
	 * @param textArea : テキストエリアを設定
	 */
	public void setTextArea(TextAreaBase textArea) {
		this.textArea = textArea;
	}
}
