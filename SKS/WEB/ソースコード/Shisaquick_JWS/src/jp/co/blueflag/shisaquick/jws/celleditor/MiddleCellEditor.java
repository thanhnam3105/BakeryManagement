package jp.co.blueflag.shisaquick.jws.celleditor;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

import jp.co.blueflag.shisaquick.jws.common.TableBase;

/**
 * 
 * 中間セルエディター
 *  : 行毎のエディタを設定する
 *
 */
public class MiddleCellEditor implements TableCellEditor {
  protected ArrayList editors;
  protected TableCellEditor editor, defaultEditor;
  private JTable table;

  /**
   * コンストラクタ
   * @param table
   */ 
  public MiddleCellEditor(TableBase table) {
    this.table = table;
    editors = new ArrayList();
    defaultEditor = new GenericEditor();
  }
  
  /**
   * @param row    table row
   * @param editor table cell editor
   */
  public void addEditorAt(int row, TableCellEditor editor) {
    editors.add(row, editor);
  }
  
  public void setEditorAt(int row, TableCellEditor editor) {
	editors.set(row, editor);
  }
  
  public void removeEditorAt(int row) {
	editors.remove(row);
  }
  
  public Component getTableCellEditorComponent(JTable table,
      Object value, boolean isSelected, int row, int column) {
    return editor.getTableCellEditorComponent(table,
             value, isSelected, row, column);
  }

  public Object getCellEditorValue() {
    return editor.getCellEditorValue();
  }
  
  public boolean stopCellEditing() {
    return editor.stopCellEditing();
  }
  
  public void cancelCellEditing() {
    editor.cancelCellEditing();
  }
  
  public void addCellEditorListener(CellEditorListener l) {
    editor.addCellEditorListener(l);
  }
  
  public void removeCellEditorListener(CellEditorListener l) {
    editor.removeCellEditorListener(l);
  }
  
  public boolean shouldSelectCell(EventObject anEvent) {
	  if(anEvent instanceof MouseEvent){
		  selectMouseEditor((MouseEvent)anEvent);
	  }else{
		  selectListEditor(anEvent);
	  }
	  return editor.shouldSelectCell(anEvent);
  }
  
  public boolean isCellEditable(EventObject anEvent) {
	  if(anEvent instanceof MouseEvent){
		  selectMouseEditor((MouseEvent)anEvent);
	  }else{
		  selectListEditor(anEvent);
	  }
	  return editor.isCellEditable(anEvent);
  }
  
  //マウスイベントより選択コンポーネント取得
  protected void selectMouseEditor(MouseEvent e) {
	  int count = e.getClickCount();
	  int row;
	  if (e == null || count <= 0) {
		  row = -1;
	  } else {
		  row = table.rowAtPoint(e.getPoint());
	  }
	  if(row >= 0){
		  editor = (TableCellEditor)editors.get(row);
		  if (editor == null) {
			  editor = defaultEditor;
		  }
	  }
  }
  //リストイベントより選択コンポーネント取得
  protected void selectListEditor(EventObject e) {
	  int row;
	  if (e == null) {
		  row = table.getSelectionModel().getAnchorSelectionIndex();
	  } else {
		  row = table.getSelectedRow();
	  }
	  editor = (TableCellEditor)editors.get(row);
	  if (editor == null) {
		  editor = defaultEditor;
	  }
  }
  
  public TableCellEditor getTableCellEditor(int row) {
		return (TableCellEditor)editors.get(row);
  }
  
  /**
   * Default Editors
   */
  static class GenericEditor extends DefaultCellEditor {

	Class[] argTypes = new Class[]{String.class};
	java.lang.reflect.Constructor constructor;
	Object value;

	public GenericEditor() { super(new JTextField()); }

	public boolean stopCellEditing() {
	    String s = (String)super.getCellEditorValue();
	    if ("".equals(s)) {
		if (constructor.getDeclaringClass() == String.class) {
		    value = s;
		}
		super.stopCellEditing();
	    }

	    try {
		value = constructor.newInstance(new Object[]{s});
	    }
	    catch (Exception e) {
		((JComponent)getComponent()).setBorder(new LineBorder(Color.red));
		return false;
	    }
	    return super.stopCellEditing();
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
						 boolean isSelected,
						 int row, int column) {
	    this.value = null;
	    ((JComponent)getComponent()).setBorder(new LineBorder(Color.black));
	    try {
		Class type = table.getColumnClass(column);
		if (type == Object.class) {
		    type = String.class;
		}
		constructor = type.getConstructor(argTypes);
	    }
	    catch (Exception e) {
		return null;
	    }
	    return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}

	public Object getCellEditorValue() {
	    return value;
	}
  }
}
  

