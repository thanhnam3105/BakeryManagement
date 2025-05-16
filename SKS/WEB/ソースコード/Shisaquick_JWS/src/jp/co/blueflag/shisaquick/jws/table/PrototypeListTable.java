package jp.co.blueflag.shisaquick.jws.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.celleditor.ComboBoxCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextFieldCellEditor;
import jp.co.blueflag.shisaquick.jws.cellrenderer.ComboBoxCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.LabelBase;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/************************************************************************************
 * 
 * 試作列追加テーブルクラス
 *  : 試作列追加テーブルコントロールを設定する
 * 
 * @author TT.katayama
 * @since 2009/04/05
 *
 ************************************************************************************/
public class PrototypeListTable{
	//---------------------------- 各テーブル ---------------------------------------
	//左側ヘッダーテーブル
	private TableBase leftHeaderTable;
	//右側ヘッダーテーブル
	private TableBase headerTable;
	//右側コンボボックステーブル
	private TableBase comboTable;
	//右側メインテーブル
	private TableBase mainTable;
	
	//------------------------- スクロールパネル -------------------------------------
	private ScrollBase[] scroll;	
	
	//------------------------ エディタ&レンダラ配列 -----------------------------------
	//右側ヘッダーテーブル用配列
	private ArrayList headerTableCellEditor = new ArrayList();	
	private ArrayList headerTableCellRenderer = new ArrayList();
	//右側コンボボックステーブル用配列
	private ArrayList comboTableCellEditor = new ArrayList();	
	private ArrayList comboTableCellRenderer = new ArrayList();
	//右側メインテーブル用配列
	private ArrayList mainTableCellEditor = new ArrayList();	
	private ArrayList mainTableCellRenderer = new ArrayList();
	
	//---------------------------- 最大工程順 ----------------------------------------
	private int maxKotei = 0;
	
	//---------------------------- 例外クラス -----------------------------------------
	private ExceptionBase ex = null;
	
	/**********************************************************************************
	 * 
	 * コンストラクタ
	 * @param intRow : 行数
	 * @param intCol : 列数
	 * @throws ExceptionBase 
	 * 
	 **********************************************************************************/
	public PrototypeListTable() throws ExceptionBase {
		
		try {
			//配合データ取得
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			//最大工程順取得
			this.maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			
			//テーブル 初期化
			this.initTable();
			
			//スクロールパネルの初期設定
			this.initScroll();
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作列追加テーブル初期化処理が失敗しました。");
			this.ex.setStrErrShori("PrototypeListTable");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
	}
	
	/**********************************************************************************
	 * 
	 * テーブル 初期化
	 * 
	 **********************************************************************************/
	public void initTable() throws ExceptionBase{
		try{
			//-------------------- 左側ヘッダーテーブルの初期化 ------------------------------
			this.leftHeaderTable = new TableBase(3,1){
				private static final long serialVersionUID = 1L;
				
				//セル編集不可設定
				public boolean isCellEditable(int row, int column) {
				    return false;
				}
			};
			//左側ヘッダーテーブル単一セル選択不可
			this.leftHeaderTable.setCellSelectionEnabled( false );
			//左側ヘッダーテーブル枠線
			this.leftHeaderTable.setBorder(new LineBorder(Color.BLACK, 1));
			//左側ヘッダーテーブル背景色
			this.leftHeaderTable.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
			//2010.09.09 sakai add --->
			//フォントサイズの設定
			this.leftHeaderTable.setFont(new Font("Default", Font.PLAIN, 13));
			//2010.09.09 sakai add <---
			//左側ヘッダーテーブル値
			this.leftHeaderTable.setValueAt("計算列数", 0, 0);
			this.leftHeaderTable.setValueAt("サンプルNo", 1, 0);
			this.leftHeaderTable.setValueAt("工程", 2, 0);
			//左側ヘッダーテーブル縦幅
			this.leftHeaderTable.setRowHeight( 0 , 20 );
			this.leftHeaderTable.setRowHeight( 1 , 18 );
			this.leftHeaderTable.setRowHeight( 2 , 200 );
			
			
			//------------------ （1行目）ヘッダーテーブルの初期化 -----------------------------
			this.headerTable = new TableBase(1,0);
			//右側ヘッダーテーブル単一セル選択不可
			this.headerTable.setCellSelectionEnabled( false );
			//右側ヘッダーテーブル自動サイズ設定不可
			this.headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//右側ヘッダーテーブル縦幅
			this.headerTable.setRowHeight(20);
			//列追加
			this.addHeaderTableCol();
			
			
			//----------------- （2行目）試作列選択テーブルの初期化 ----------------------------
			this.comboTable = new TableBase(1,0);
			//右側コンボボックステーブル単一セル選択可
			this.comboTable.setCellSelectionEnabled( true );
			//右側コンボボックステーブル自動サイズ設定不可
			this.comboTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//右側コンボボックステーブル縦幅
			this.comboTable.setRowHeight(20);
			//列追加
			this.addComboTableCol();
			
			
			//------------------- （3行目）メインテーブルの初期化 -------------------------------
			this.mainTable = new TableBase(0,0);
			//右側メインテーブル単一セル選択可
			this.mainTable.setCellSelectionEnabled( true );
			//右側メインテーブル自動サイズ設定不可
			this.mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//列追加
			//2010.09.15 sakai change --->
			//this.addMainTableCol();
			this.addMainTableCol_all();
			//2010.09.15 sakai change <---
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作列計算テーブルの初期化に失敗しました。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}
	
	/**********************************************************************************
	 * 
	 * 右側テーブル(1行目)・タイトルテーブル列追加
	 * 
	 **********************************************************************************/
	public void addHeaderTableCol() {
		try{
			//----------------------------- 初期設定 -------------------------------------
			//最大列数取得
			int col = this.headerTable.getColumnCount();
			
			//-------------------------- テーブル列追加 ----------------------------------
			this.headerTable.tableInsertColumn(col);
			this.headerTable.setValueAt(Integer.toString(col+1), 0, col);
			
			//------------------------ エディタ&レンダラ生成 -------------------------------
			//中間エディタ&レンダラ生成
			MiddleCellEditor mce = new MiddleCellEditor(this.headerTable);
			MiddleCellRenderer mcr = new MiddleCellRenderer();
			
			//コンポーネント生成
			TextboxBase tb = new TextboxBase();
			tb.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
			tb.setEditable(false);
			
			//エディタ&レンダラ生成
			TextFieldCellEditor tce = new TextFieldCellEditor(tb);
			TextFieldCellRenderer tcr = new TextFieldCellRenderer(tb);
			//中間エディタ&レンダラ登録
			mce.addEditorAt(0, tce);
			mcr.add(0, tcr);
			//エディタ&レンダラ配列へ追加
			headerTableCellEditor.add(mce);
			headerTableCellRenderer.add(mcr);
			
			//------------------- テーブルへ中間エディタ&レンダラ設定 ------------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.headerTable.getColumnModel();
			TableColumn column = null;
			for(int i=0; i<this.headerTable.getColumnCount(); i++){
				//エディタ&レンダラ設定
				column = this.headerTable.getColumnModel().getColumn(i);
				column.setCellEditor((MiddleCellEditor)headerTableCellEditor.get(i));
				column.setCellRenderer((MiddleCellRenderer)headerTableCellRenderer.get(i));
				//幅設定
				column.setMinWidth(200);
				column.setMaxWidth(200);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			
		}
	}

	/**********************************************************************************
	 * 
	 * 右側テーブル(2行目)・コンボボックステーブル列追加
	 * 
	 **********************************************************************************/
	public void addComboTableCol() {
		try{
			//----------------------------- 初期設定 -------------------------------------
			//最大列数取得
			int col = this.comboTable.getColumnCount();
			//データ取得
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			String setSample = ((TrialData)retuData.get(0)).getStrSampleNo();
			
			//-------------------------- テーブル列追加 ----------------------------------
			//ｻﾝﾌﾟﾙNo
			this.comboTable.tableInsertColumn(col);
			//計算式
			this.comboTable.tableInsertColumn(col+1);
			this.comboTable.setValueAt("+", 0, col+1);
			
			
			//------------------- ｻﾝﾌﾟﾙNo選択用　エディタ&レンダラ生成 ---------------------
			//中間エディタ&レンダラ生成
			MiddleCellEditor mceSample = new MiddleCellEditor(this.comboTable);
			MiddleCellRenderer mcrSample = new MiddleCellRenderer();
			
			//コンポーネント生成
			ComboBase cbSample = new ComboBase();
			//データ設定
			int selCount = 0;
			for(int j=1; j<=retuData.size(); j++){
				for(int i=0; i<retuData.size(); i++){
					TrialData selTrialData = (TrialData)retuData.get(i);
					//表示順にて表示
					if(j == selTrialData.getIntHyojiNo()){
						//2010.09.09 sakai change --->
						//cbSample.addItem(selTrialData.getIntHyojiNo()+"："+checkNull(selTrialData.getStrSampleNo()));
						cbSample.addItem(checkNull(selTrialData.getStrSampleNo()));
						//2010.09.09 sakai change <---
						
						//初期設定用サンプルNo取得（表示順が最初のサンプルNo）
						if(selCount == 0){
							setSample = checkNull(selTrialData.getStrSampleNo());
							selCount ++;
						}
					}
				}
			}
			//サンプル名設定
			this.comboTable.setValueAt(setSample, 0, col);
			
			//エディタ&レンダラ生成
			ComboBoxCellEditor cceSample = new ComboBoxCellEditor(cbSample);
			ComboBoxCellRenderer ccrSample = new ComboBoxCellRenderer(cbSample);
			//中間エディタ&レンダラ登録
			mceSample.addEditorAt(0, cceSample);
			mcrSample.add(0, ccrSample);
			//エディタ&レンダラ配列へ追加
			comboTableCellEditor.add(mceSample);
			comboTableCellRenderer.add(mcrSample);
			
			//-------------------- 計算式選択用　エディタ&レンダラ生成 ----------------------
			//中間エディタ&レンダラ生成
			MiddleCellEditor mceKeisan = new MiddleCellEditor(this.comboTable);
			MiddleCellRenderer mcrKeisan = new MiddleCellRenderer();
			
			//コンポーネント生成
			ComboBase cbKeisan = new ComboBase();
			//2010.09.09 sakai add --->
			//フォントサイズの設定
			cbKeisan.setFont(new Font("Default", Font.PLAIN, 14));
			//2010.09.09 sakai add <---
			
			//データ設定
			cbKeisan.addItem("+");
			cbKeisan.addItem("-");
			
			//エディタ&レンダラ生成
			ComboBoxCellEditor cceKeisan = new ComboBoxCellEditor(cbKeisan);
			ComboBoxCellRenderer ccrKeisan = new ComboBoxCellRenderer(cbKeisan);
			//中間エディタ&レンダラ登録
			mceKeisan.addEditorAt(0, cceKeisan);
			mcrKeisan.add(0, ccrKeisan);
			//エディタ&レンダラ配列へ追加
			comboTableCellEditor.add(mceKeisan);
			comboTableCellRenderer.add(mcrKeisan);
			
			
			//------------------- テーブルへ中間エディタ&レンダラ設定 ------------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.comboTable.getColumnModel();
			TableColumn column = null;
			for(int i=0; i<this.comboTable.getColumnCount(); i++){
				//エディタ&レンダラ設定
				column = this.comboTable.getColumnModel().getColumn(i);
				column.setCellEditor((MiddleCellEditor)comboTableCellEditor.get(i));
				column.setCellRenderer((MiddleCellRenderer)comboTableCellRenderer.get(i));
				//幅設定
				int retuFlg = i % 2;
				if(retuFlg == 0){
					column.setMinWidth(140);
					column.setMaxWidth(140);
				}else{
					column.setMinWidth(60);
					column.setMaxWidth(60);
				}
			}
			
		}catch(ExceptionBase eb){
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			
		}
		
	}
	
	/**********************************************************************************
	 * 
	 * 右側テーブル(3行目) ・メインテーブル列追加（計算）
	 * 
	 **********************************************************************************/
	public void addMainTableCol() throws ExceptionBase{
		try{
			//----------------------------- 初期設定 -------------------------------------
			//最大列数取得
			int col = this.mainTable.getColumnCount();
			
			//-------------------------- テーブル列追加 ----------------------------------
			this.mainTable.tableInsertColumn(col);
			this.mainTable.tableInsertColumn(col+1);
			this.mainTable.tableInsertColumn(col+2);
			
			//-------------------------- テーブル行追加 ----------------------------------
			//工程順分ループ
			for(int i=0; i<maxKotei; i++){
				if(this.mainTable.getRowCount() < maxKotei){
					this.mainTable.tableInsertRow(i);
				}
				//初期値設定
				this.mainTable.setValueAt(Integer.toString(i+1), i, col);
				this.mainTable.setValueAt("×", i, col+1);
				this.mainTable.setValueAt("0.0", i, col+2);
			}
			
			
			//------------------- 工程順　エディタ&レンダラ生成 -----------------------------
			//中間エディタ&レンダラ生成
			MiddleCellEditor mceKotei = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrKotei = new MiddleCellRenderer();
			
			//行数分処理
			for(int i=0; i<maxKotei; i++){
				//コンポーネント生成
				TextboxBase tb = new TextboxBase();
				tb.setBackground(Color.lightGray);
				tb.setEditable(false);
				
				//エディタ&レンダラ生成
				TextFieldCellEditor tce = new TextFieldCellEditor(tb);
				TextFieldCellRenderer tcr = new TextFieldCellRenderer(tb);
				//中間エディタ&レンダラ登録
				mceKotei.addEditorAt(i, tce);
				mcrKotei.add(i, tcr);
			}
			//エディタ&レンダラ配列へ追加
			mainTableCellEditor.add(mceKotei);
			mainTableCellRenderer.add(mcrKotei);
			
			
			//------------------- 計算式　エディタ&レンダラ生成 -----------------------------
			//中間エディタ&レンダラ生成
			MiddleCellEditor mceKeisan = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrKeisan = new MiddleCellRenderer();
			
			//行数分処理
			for(int i=0; i<maxKotei; i++){
				//コンポーネント生成
				ComboBase cbKeisan = new ComboBase();
				//2010.09.09 sakai add --->
				//フォントサイズの設定
				cbKeisan.setFont(new Font("Default", Font.PLAIN, 14));
				//2010.09.09 sakai add <---
				//データ設定
				cbKeisan.addItem("×");
				cbKeisan.addItem("÷");
				//エディタ&レンダラ生成
				ComboBoxCellEditor cceKeisan = new ComboBoxCellEditor(cbKeisan);
				ComboBoxCellRenderer ccrKeisan = new ComboBoxCellRenderer(cbKeisan);
				//中間エディタ&レンダラ登録
				mceKeisan.addEditorAt(i, cceKeisan);
				mcrKeisan.add(i, ccrKeisan);
			}
			//エディタ&レンダラ配列へ追加
			mainTableCellEditor.add(mceKeisan);
			mainTableCellRenderer.add(mcrKeisan);
			
			
			//------------------- 計算値　エディタ&レンダラ生成 ------------------------------
			//中間エディタ&レンダラ生成
			MiddleCellEditor mceValue = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrValue = new MiddleCellRenderer();
			
			//行数分処理
			for(int i=0; i<maxKotei; i++){
				//コンポーネント生成
				NumelicTextbox tb = new NumelicTextbox();
				
				//エディタ&レンダラ生成
				TextFieldCellEditor tce = new TextFieldCellEditor(tb);
				TextFieldCellRenderer tcr = new TextFieldCellRenderer(tb);
				//中間エディタ&レンダラ登録
				mceValue.addEditorAt(i, tce);
				mcrValue.add(i, tcr);
			}
			//エディタ&レンダラ配列へ追加
			mainTableCellEditor.add(mceValue);
			mainTableCellRenderer.add(mcrValue);
			
			
			//------------------- テーブルへ中間エディタ&レンダラ設定 ------------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.mainTable.getColumnModel();
			TableColumn column = null;
			for(int i=0; i<this.mainTable.getColumnCount(); i++){
				//エディタ&レンダラ設定
				column = this.mainTable.getColumnModel().getColumn(i);
				column.setCellEditor((MiddleCellEditor)mainTableCellEditor.get(i));
				column.setCellRenderer((MiddleCellRenderer)mainTableCellRenderer.get(i));
				//幅設定
				int retuFlg = i % 3;
				if(retuFlg == 0){
					column.setMinWidth(100);
					column.setMaxWidth(100);
				}else if(retuFlg == 1){
					column.setMinWidth(40);
					column.setMaxWidth(40);
				}else{
					column.setMinWidth(60);
					column.setMaxWidth(60);
				}
			}
			//2010.09.09 sakai add --->
			this.mainTable.setRowHeight(17);
			//2010.09.09 sakai add <---
			
		}catch(Exception e){
			e.printStackTrace();
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("計算テーブルの列追加に失敗しました。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/**********************************************************************************
	 * 
	 * 右側テーブル(3行目) ・メインテーブル列追加（全工程）
	 * 
	 **********************************************************************************/
	public void addMainTableCol_all() throws ExceptionBase{
		try{
			//----------------------------- 初期設定 -------------------------------------
			//最大列数取得
			int col = this.mainTable.getColumnCount();
			//最大工程順取得
			int maxKotei = 1;
			
			//-------------------------- テーブル列追加 ----------------------------------
			this.mainTable.tableInsertColumn(col);
			this.mainTable.tableInsertColumn(col+1);
			this.mainTable.tableInsertColumn(col+2);
			
			//-------------------------- テーブル行追加 ----------------------------------
			if(this.mainTable.getRowCount() < maxKotei){
				this.mainTable.tableInsertRow(0);
			}
			this.mainTable.setValueAt("全工程", 0, col);
			this.mainTable.setValueAt("×", 0, col+1);
			this.mainTable.setValueAt("0.0", 0, col+2);
			
			//------------------- 工程順　エディタ&レンダラ生成 -----------------------------
			//中間エディタ&レンダラ生成
			MiddleCellEditor mceKotei = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrKotei = new MiddleCellRenderer();
			
			//コンポーネント生成
			TextboxBase tb = new TextboxBase();
			tb.setBackground(Color.lightGray);
			tb.setEditable(false);
				
			//エディタ&レンダラ生成
			TextFieldCellEditor tce = new TextFieldCellEditor(tb);
			TextFieldCellRenderer tcr = new TextFieldCellRenderer(tb);

			//中間エディタ&レンダラ登録
			mceKotei.addEditorAt(0, tce);
			mcrKotei.add(0, tcr);
			
			//エディタ&レンダラ配列へ追加
			mainTableCellEditor.add(mceKotei);
			mainTableCellRenderer.add(mcrKotei);
			
			
			//------------------- 計算式　エディタ&レンダラ生成 -----------------------------
			//中間エディタ&レンダラ生成
			MiddleCellEditor mceKeisan = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrKeisan = new MiddleCellRenderer();
			
			//コンポーネント生成
			ComboBase cbKeisan = new ComboBase();
			
			//2010.09.09 sakai add --->
			//フォントサイズの設定
			cbKeisan.setFont(new Font("Default", Font.PLAIN, 14));
			//2010.09.09 sakai add <---

			//データ設定
			cbKeisan.addItem("×");
			cbKeisan.addItem("÷");
			
			//エディタ&レンダラ生成
			ComboBoxCellEditor cceKeisan = new ComboBoxCellEditor(cbKeisan);
			ComboBoxCellRenderer ccrKeisan = new ComboBoxCellRenderer(cbKeisan);
			
			//中間エディタ&レンダラ登録
			mceKeisan.addEditorAt(0, cceKeisan);
			mcrKeisan.add(0, ccrKeisan);
			
			//エディタ&レンダラ配列へ追加
			mainTableCellEditor.add(mceKeisan);
			mainTableCellRenderer.add(mcrKeisan);
			
			
			//------------------- 計算値　エディタ&レンダラ生成 ------------------------------
			//中間エディタ&レンダラ生成
			MiddleCellEditor mceValue = new MiddleCellEditor(this.mainTable);
			MiddleCellRenderer mcrValue = new MiddleCellRenderer();
			
			//コンポーネント生成
			NumelicTextbox tb2 = new NumelicTextbox();
			
			//エディタ&レンダラ生成
			TextFieldCellEditor tce2 = new TextFieldCellEditor(tb2);
			TextFieldCellRenderer tcr2 = new TextFieldCellRenderer(tb2);
			
			//中間エディタ&レンダラ登録
			mceValue.addEditorAt(0, tce2);
			mcrValue.add(0, tcr2);
			
			//エディタ&レンダラ配列へ追加
			mainTableCellEditor.add(mceValue);
			mainTableCellRenderer.add(mcrValue);
			
			
			//------------------- テーブルへ中間エディタ&レンダラ設定 ------------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)this.mainTable.getColumnModel();
			TableColumn column = null;
			for(int i=0; i<this.mainTable.getColumnCount(); i++){
				//エディタ&レンダラ設定
				column = this.mainTable.getColumnModel().getColumn(i);
				column.setCellEditor((MiddleCellEditor)mainTableCellEditor.get(i));
				column.setCellRenderer((MiddleCellRenderer)mainTableCellRenderer.get(i));
				//幅設定
				int retuFlg = i % 3;
				if(retuFlg == 0){
					column.setMinWidth(100);
					column.setMaxWidth(100);
				}else if(retuFlg == 1){
					column.setMinWidth(40);
					column.setMaxWidth(40);
				}else{
					column.setMinWidth(60);
					column.setMaxWidth(60);
				}
			}
			//2010.09.09 sakai add --->
			this.mainTable.setRowHeight(17);
			//2010.09.09 sakai add <---
			
		}catch(Exception e){
			e.printStackTrace();
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("計算テーブルの初期化に失敗しました。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/**********************************************************************************
	 * 
	 * 右側テーブル(3行目) ・メインテーブル初期化
	 * 
	 **********************************************************************************/
	public void initMainTable() throws ExceptionBase{
		try{
			int col_count = this.mainTable.getColumnCount();
			int row_count = this.mainTable.getRowCount();
			//テーブル初期化
			for(int i=0; i<col_count; i++){
				this.mainTable.tableDeleteColumn(0);
			}
			for(int i=0; i<row_count; i++){
				this.mainTable.tableDeleteRow(0);
			}
			
			//レンダラ&エディタ配列初期化
			mainTableCellEditor.clear();
			mainTableCellRenderer.clear();
			
		}catch(Exception e){
			e.printStackTrace();
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("計算テーブルの初期化に失敗しました。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}

	/**********************************************************************************
	 * 
	 * スクロールパネル 初期設定
	 * 
	 **********************************************************************************/
	private void initScroll() {

		//スクロールパネルのインスタンス生成[0:ヘッダーテーブル, 1:コンボボックステーブル, 2:メインテーブル]
		this.scroll = new ScrollBase[3];
		//ヘッダーテーブル
		this.scroll[0] = new ScrollBase( this.headerTable ) {
			private static final long serialVersionUID = 1L;
			//ヘッダーを消去
			public void setColumnHeaderView(Component view) {} 
		};
		//コンボボックステーブル
		this.scroll[1] = new ScrollBase( this.comboTable ) {
			private static final long serialVersionUID = 1L;
			//ヘッダーを消去
			public void setColumnHeaderView(Component view) {} 
		};
		//メインテーブル
		this.scroll[2] = new ScrollBase( this.mainTable ) {
			private static final long serialVersionUID = 1L;
			//ヘッダーを消去
			public void setColumnHeaderView(Component view) {} 
		};

		
		//スクロールパネルの初期設定
		//ヘッダーテーブル
		this.scroll[0].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.scroll[0].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scroll[0].setBackground(Color.WHITE);
		this.scroll[0].setBorder(new LineBorder(Color.BLACK, 1));
		this.scroll[0].setBackground(Color.WHITE);
		//コンボボックステーブル
		this.scroll[1].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.scroll[1].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scroll[1].setBackground(Color.WHITE);
		this.scroll[1].setBorder(new LineBorder(Color.BLACK, 1));
		this.scroll[1].setBackground(Color.WHITE);
		//メインテーブル
		this.scroll[2].setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.scroll[2].setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scroll[2].setBackground(Color.WHITE);
		this.scroll[2].setBorder(new LineBorder(Color.BLACK, 1));		
		this.scroll[2].setBackground(Color.WHITE);

		
		//スクロールバーの動きを同期させる
		final JScrollBar bar1 = this.scroll[0].getHorizontalScrollBar();
		final JScrollBar bar2 = this.scroll[1].getHorizontalScrollBar();
		final JScrollBar bar3 = this.scroll[2].getHorizontalScrollBar();
		bar1.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				bar2.setValue(e.getValue());
				bar3.setValue(e.getValue());
			}
		});
		bar2.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				bar1.setValue(e.getValue());
				bar3.setValue(e.getValue());
			}
		});
		bar3.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				bar1.setValue(e.getValue());
				bar2.setValue(e.getValue());
			}
		});
		
	}

	/************************************************************************************
	 * 
	 * NULLチェック処理（オブジェクト）
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	private String checkNull(Object val){
		String ret = "";
		if(val != null){
			ret = val.toString();
		}
		return ret;
	}
	
	/************************************************************************************
	 * 
	 * NULLチェック処理（数値）
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	private String checkNull(int val){
		String ret = "";
		if(val >= 0){
			ret = Integer.toString(val);
		}
		return ret;
	}
	
	/**********************************************************************************
	 * 
	 * 最大工程順　ゲッター
	 * @return Scrollパネル 
	 * 
	 **********************************************************************************/
	public int getMaxKotei() {
		return maxKotei;
	}
	
	/**********************************************************************************
	 * 
	 * スクロールパネル　ゲッター
	 * @return Scrollパネル 
	 * 
	 **********************************************************************************/
	public ScrollBase[] getScroll() {
		return this.scroll;
	}
	
	/**********************************************************************************
	 * 
	 * メインテーブル ゲッター
	 * @return メインテーブル
	 * 
	 **********************************************************************************/
	public TableBase getMainTable() {
		return this.mainTable;
	}
	
	/**********************************************************************************
	 * 
	 * コンボボックステーブル ゲッター
	 * @return コンボボックステーブル
	 * 
	 **********************************************************************************/
	public TableBase getComboTable() {
		return this.comboTable;
	}
	
	/**********************************************************************************
	 * 
	 * ヘッダーテーブル ゲッター
	 * @return ヘッダーテーブル
	 * 
	 **********************************************************************************/
	public TableBase getHeaderTable() {
		return this.headerTable;
	}
	
	/**********************************************************************************
	 * 
	 * 左舷ヘッダーテーブル ゲッター
	 * @return 左舷ヘッダーテーブル
	 * 
	 **********************************************************************************/
	public TableBase getLeftHeaderTable() {
		return this.leftHeaderTable;
	}
	
	
}