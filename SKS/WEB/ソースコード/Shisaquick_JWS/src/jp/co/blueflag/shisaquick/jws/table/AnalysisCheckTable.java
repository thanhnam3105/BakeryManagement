package jp.co.blueflag.shisaquick.jws.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;

/**
 * 
 * 試作分析データ確認テーブルクラス
 *  : 試作分析データ確認テーブルコントロールを設定する
 * 
 * @author TT.katayama
 * @since 2009/04/04
 *
 */
public class AnalysisCheckTable {

	private ExceptionBase ex = null;
	
	private TableBase headerTable;			//ヘッダーテーブル
	private TableBase mainTable;				//メインテーブル
	private ScrollBase scroll;					//スクロールパネル
	
	/**
	 * コンストラクタ(行・列 指定)
	 * @param intRow : 行数
	 * @param intCol : 列数
	 * @param columnNm : 列名格納配列
	 * @param columnWidth : 列幅格納配列
	 * @throws ExceptionBase 
	 */
	public AnalysisCheckTable( int intRow, int intCol, Object[] columnNm, int[] columnWidth ) throws ExceptionBase{
		
		//テーブルヘッダー色
		Color table_color = JwsConstManager.SHISAKU_ITEM_COLOR;
		
		try {
			//テーブルのインスタンス生成
			this.mainTable = new TableBase( intRow, intCol ){
				private static final long serialVersionUID = 1L;
				/**
				 * セル編集不可
				 */
				public boolean isCellEditable(int row, int column) {
				    return false;
				}
			};
			this.headerTable = new TableBase( 1, intCol ) {
				private static final long serialVersionUID = 1L;
				/**
				 * セル編集不可
				 */
				public boolean isCellEditable(int row, int column) {
				    return false;
				}
			};
			
			
			this.mainTable.setCellSelectionEnabled( false );
			this.headerTable.setCellSelectionEnabled( false );
			this.headerTable.setEnabled(false);
			//自動リサイズをOFFに設定
			this.mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			this.headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//行選択の設定
			this.mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.mainTable.setRowSelectionAllowed(true);
			
			//列名の設定
			this.setColumnName(columnNm);
			//列幅の設定
			this.setColumnWidth(this.mainTable, columnWidth);
			this.setColumnWidth(this.headerTable, columnWidth);
			
			this.headerTable.setRowHeight(35);
			
			//ヘッダーテーブルの色設定
			this.headerTable.setBackground(table_color);
			
			//スクロールパネルのインスタンス生成
			this.scroll = new ScrollBase( this.mainTable ) {
				private static final long serialVersionUID = 1L;
				//ヘッダーを消去
				public void setColumnHeaderView(Component view) {} 
			};
			
			//スクロールパネルの設定
			this.scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			this.scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			this.scroll.setBackground(Color.WHITE);
			this.scroll.setBorder(new LineBorder(Color.BLACK, 1));
			this.scroll.setBackground(Color.WHITE);
			
			//ビューポートの設定
			JViewport headerViewport = new JViewport();
			headerViewport.setView(this.headerTable);
			headerViewport.setPreferredSize(this.headerTable.getPreferredSize());
			this.scroll.setColumnHeader(headerViewport);
			
		} catch( Exception e ) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作分析データ確認テーブル初期化処理が失敗しました。");
			this.ex.setStrErrShori("AnalysisCheckTable");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.toString());
			throw ex;
		} finally {
		}
	}
	
	/**
	 * 列名の初期化
	 * @param columnNm : 列名格納配列
	 */
	private void setColumnName(Object[] columnNm) {
		for (int i=0; i<columnNm.length; i++ ) {
			this.headerTable.setValueAt(columnNm[i], 0, i);
		}
	}
	
	/**
	 * 列の横幅の初期化
	 * @param table : 対象テーブル
	 * @param columnWidth : 列幅格納配列
	 */
	private void setColumnWidth(TableBase table, int[] columnWidth) {		
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column;
		
		//列幅を設定していく
		for ( int i=0; i<table.getColumnCount(); i++ ) {
			column = columnModel.getColumn(i);
			column.setPreferredWidth(columnWidth[i]);
			table.setRowHeight(17);
			table.setFont(new Font("Default", Font.PLAIN, 13));
		}
	}
	
	/**
	 * スクロールパネル　ゲッター
	 * @return Scrollパネル 
	 */
	public ScrollBase getScroll() {
		return this.scroll;
	}
	
	/**
	 * メインテーブル ゲッター
	 * @return メインテーブル
	 */
	public TableBase getMainTable() {
		return this.mainTable;
	}
	
	/**
	 * サブテーブル ゲッター
	 * @return サブテーブル
	 */
	public TableBase getHeaderTable() {
		return this.headerTable;
	}
	
}