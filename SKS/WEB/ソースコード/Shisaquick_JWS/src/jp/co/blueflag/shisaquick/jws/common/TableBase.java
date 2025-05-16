package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * テーブル操作
 *  : アプリ内テーブルの基本動作を行う
 *
 */
public class TableBase extends JTable implements FocusControl {

	private static final long serialVersionUID = 1L;	//デフォルトシリアルID
	
	//エラー操作
	private ExceptionBase ex;
	//直接入力メンバ
	boolean directInput = true;
	
	/**
	 * コンストラクタ
	 */
	public TableBase(){
		//スーパークラスのコンストラクタを呼び出す
		super();
		
		this.ex = null;
		
	}
	
	/**
	 * コンストラクタ(テーブルモデル 指定)
	 * @param DefaultTableModel : テーブルモデル
	 */
	public TableBase(DefaultTableModel DefaultTableModel) {
		super(DefaultTableModel);
		
		this.ex = null;
		
	}
	
	/**
	 * コンストラクタ(TableModel 指定)
	 * @param tablemodel : 行数
	 */
	public TableBase(int intRow, int intCol) {
		super(intRow, intCol);
		
		this.ex = null;
		
	}
	
	/**
	 * 直接入力指定
	 * @param tablemodel : 行数
	 */
	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
		//直接入力の場合
		if(directInput){
			 boolean retValue = super.processKeyBinding(ks, e, condition, pressed);
			    //if(!check.isSelected()) return retValue;
			 
			 try{
				 
				 if(KeyStroke.getKeyStroke('\t').equals(ks) || KeyStroke.getKeyStroke('\n').equals(ks)) {
				      
					 return retValue;
				    	
				 }
				    
				 if(getInputContext().isCompositionEnabled() && !isEditing() &&
						 !pressed && !ks.isOnKeyRelease()) {
				    	
					 int selectedRow = getSelectedRow();
					 int selectedColumn = getSelectedColumn();
					 if(selectedRow!=-1 && selectedColumn!=-1 && !editCellAt(selectedRow, selectedColumn)) {
						 return retValue;
					 }
				      
				 }
				 
			 }catch(Exception ex){
				 
				 
			 }
			 
			 return retValue;
			
		//直接入力でない場合
		}else{
			return super.processKeyBinding(ks, e, condition, pressed);
		}
	}

	/**
	 * Enter押下時フォーカスコントロール
	 * @param enterComp : Enter時のフォーカス移動先JComponent
	 */
	public void setEnterFocusControl(final JComponent enterComp) throws ExceptionBase {
		
		try {
			this.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {

					//最終行・最終列
					boolean isLastRowSelect = ( isRowSelected(getRowCount() - 1));
					boolean isLastColumnSelect = ( isColumnSelected(getColumnCount() - 1));
					
					//KEY 押下時の処理
					if ( e.getKeyCode() == KeyEvent.VK_ENTER  ) {			//ENTERキー
						//最終行にフォーカス時
						if ( isLastRowSelect ) {
							enterComp.requestFocus();	//対象のJComponentへフォーカスを設定
							clearSelection();		//テーブルを未選択に設定
						}
					}
				}
			});
		} catch ( Exception e ) {			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("TableBaseのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
	/**
	 * TAB押下時フォーカスコントロール
	 * @param enterComp : TAB時のフォーカス移動先JComponent
	 */
	public void setTabFocusControl(final JComponent enterComp) throws ExceptionBase {
		
		try {
			this.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
	
					//最終行・最終列
					boolean isLastRowSelect = ( isRowSelected(getRowCount() - 1));
					boolean isLastColumnSelect = ( isColumnSelected(getColumnCount() - 1));
					
					if ( e.getKeyCode() == KeyEvent.VK_TAB ) {		//TABキー
						//最終行・最終列にフォーカス時
						if ( isLastRowSelect && isLastColumnSelect ) {
							transferFocus();		//システムに設定されている次のフィールドにフォーカスを設定
							clearSelection();		//テーブルを未選択に設定
						}
					}
				}
			});
		} catch ( Exception e ) {			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("TableBaseのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}
	
	//テーブル操作メソッド（行削除）
	public void tableDeleteRow(int row){
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		model.removeRow( row );
	}
	//テーブル操作メソッド（行追加）
	public void tableInsertRow(int row){
		this.clearSelection();
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		model.insertRow( row, new Vector() );
	}
	//テーブル操作メソッド（行移動）
	public void tableMoveRow(int oldRow,int newRow){
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		model.moveRow( oldRow, oldRow, newRow );
	}
	//テーブル操作メソッド（列削除）
	public void tableDeleteColumn(int columnIndex){
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		/* ヘッダ情報格納 */
		Vector columnIdentifiers = new Vector();
		for (int i = 0, n = this.getColumnCount(); i < n; i++) {
			columnIdentifiers.add(columnModel.getColumn(i).getHeaderValue());
		}
		/* カラムを削除 */
		columnIdentifiers.remove(columnIndex);
		TableColumnModel cm = getColumnModel();
		this.removeColumn(cm.getColumn(columnIndex));
		/* dataVector 配列の作成 */
		Object[][] dataVector = new Object[this.getRowCount()][this.getColumnCount()];
		for (int i = 0, n = dataVector.length; i < n; i++) {
			for (int j = 0, m = dataVector[0].length; j < m; j++) {
				dataVector[i][j] = this.getValueAt(i, j);
			}
		}
		/* 新しいデータをセット */
		model.setDataVector(dataVector, columnIdentifiers.toArray());
	}
	//テーブル操作メソッド（列追加）
	public void tableInsertColumn(int columnIndex){
		int rowCount = this.getRowCount();
		int colCount = this.getColumnCount();
		/* 列追加し、指定位置に移動 */
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		model.addColumn(new Vector());
		tableMoveColumn(colCount,columnIndex);
	}
	
	//テーブル操作メソッド（列移動）
	public void tableMoveColumn(int oldCol,int newCol){
		DefaultTableModel model = (DefaultTableModel)this.getModel();
		/* ヘッダ情報格納 */
		Vector columnIdentifiers = new Vector();
		for (int i = 0, n = this.getColumnCount(); i < n; i++) {
			columnIdentifiers.add(columnModel.getColumn(i).getHeaderValue());
		}
		/* カラムを移動 */
		columnIdentifiers.set(oldCol, columnIdentifiers.get(newCol));
		columnIdentifiers.set(newCol, columnIdentifiers.get(oldCol));
		this.moveColumn( oldCol, newCol );
		/* dataVector 配列の作成 */
		Object[][] dataVector = new Object[this.getRowCount()][this.getColumnCount()];
		for (int i = 0, n = dataVector.length; i < n; i++) {
			for (int j = 0, m = dataVector[0].length; j < m; j++) {
				dataVector[i][j] = this.getValueAt(i, j);
			}
		}
		/* 新しいデータをセット */
		model.setDataVector(dataVector, columnIdentifiers.toArray());
	}
	
	/**
	 * 直接入力メンバ　ゲッター
	 * @return
	 */
	public boolean isDirectInput() {
		return directInput;
	}
	/**
	 * 直接入力メンバ　セッター
	 * @return
	 */
	public void setDirectInput(boolean directInput) {
		this.directInput = directInput;
	}
	
	
	  
}
