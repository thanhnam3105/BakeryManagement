package jp.co.blueflag.shisaquick.jws.cellrenderer;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * 
 * 中間セルレンダラー
 *  : 行毎のレンダラを設定する
 *
 */
public class MiddleCellRenderer implements TableCellRenderer {
  protected ArrayList renderers;
  protected TableCellRenderer renderer, defaultRenderer;

  /**
   * コンストラクタ
   */ 
  public MiddleCellRenderer() {
    renderers = new ArrayList();
    defaultRenderer = new DefaultTableCellRenderer();
  }
  
  public void add(int row, TableCellRenderer renderer) {
    renderers.add(row,renderer);
  }
  
  public void set(int row, TableCellRenderer renderer) {
	renderers.set(row,renderer);
  }
  
  public void remove(int row) {
	renderers.remove(row);
  }
  
  public Component getTableCellRendererComponent(JTable table,
      Object value, boolean isSelected, boolean hasFocus,
                                      int row, int column) {
    renderer = (TableCellRenderer)renderers.get(row);
    if (renderer == null) {
      renderer = defaultRenderer;
    }
    return renderer.getTableCellRendererComponent(table,
             value, isSelected, hasFocus, row, column);
  }
  
  public TableCellRenderer getTableCellRenderer(int row) {
		return (TableCellRenderer)renderers.get(row);
  }
  
}
  

