package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JRadioButton;
import javax.swing.table.TableCellEditor;

import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;
import jp.co.blueflag.shisaquick.jws.label.HeaderLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.label.LevelLabel;
import jp.co.blueflag.shisaquick.jws.manager.MessageCtrl;
import jp.co.blueflag.shisaquick.jws.table.PrototypeListTable;

/*************************************************************************************
 * 
 * 【A05-07】 試作列追加パネル操作用のクラス
 * 
 * @author TT.katayama
 * @since 2009/04/03
 * 
 **********************************************************************************/
public class PrototypeListPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private DispTitleLabel dispTitleLabel;				//画面タイトルラベル
	private HeaderLabel headerLabel;					//レベル表示ラベル
	private LevelLabel levelLabel;							//ヘッダ表示ラベル
	
	private ItemLabel[] itemLabel;							//項目ラベル
	private JRadioButton[] radioButton;					//ラジオボタン
	private PrototypeListTable prototypeListTable;	//試作列テーブル
	private ButtonBase[] button;							//ボタン
	private ButtonGroup copyCheck = new ButtonGroup();
	
	private MessageCtrl messageCtrl;					//メッセージ操作
	private ExceptionBase ex;								//エラー操作
	
	/**********************************************************************************
	 * 
	 * コンストラクタ
	 * 
	 **********************************************************************************/
	public PrototypeListPanel(String strOutput) throws ExceptionBase {
		//スーパークラスのコンストラクタを呼び出す
		super();

		try {
			//１．パネルの設定
			this.setPanel();
			
			//２．コントロールの配置
			this.addControl(strOutput);
			
		} catch(Exception e) {
			e.printStackTrace();
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作列追加パネル処理が失敗しました。");
			this.ex.setStrErrShori("PrototypeListPanel");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
			
		}
		
	}

	/**********************************************************************************
	 * 
	 * パネル設定
	 * 
	 **********************************************************************************/
	private void setPanel() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
	}

	/**********************************************************************************
	 * 
	 * コントロール配置
	 * @param strTitle : 画面タイトル
	 * @throws ExceptionBase 
	 * 
	 **********************************************************************************/
	public void addControl(String strTitle) throws ExceptionBase {
		int i, x, y, width, height;
		int dispWidth = 620;
		int defLabelWidth = 60;
		int defRadioWidth = 60;
		int defButtonWidth = 80;
		int defButtonHeight = 38;
		int defHeight = 15;
	
		//-------------------------- タイトルラベル設定 -----------------------------------
		this.dispTitleLabel = new DispTitleLabel();
		this.dispTitleLabel.setText(strTitle);
		this.add(this.dispTitleLabel);
		
		//2010.09.09 sakai change --->
		////------------------- 項目ラベルの設定[0:計算, 1:全工程] ----------------------------
		//------------------- 項目ラベルの設定[0:全工程, 1:計算] ----------------------------
		//2010.09.09 sakai change <---
		this.itemLabel = new ItemLabel[2];
		x = 5;
		y = 30;
		width = defLabelWidth;
		height = defHeight;
		//2010.09.09 sakai change --->
		//this.itemLabel[0] = new ItemLabel();
		//this.itemLabel[0].setText("計算");
		//this.itemLabel[0].setBounds(x,y,width,height);
		////全工程
		//x += defLabelWidth + defRadioWidth + 5;
		//this.itemLabel[1] = new ItemLabel();
		//this.itemLabel[1].setText("全工程");
		//this.itemLabel[1].setBounds(x,y,width,height);
		//全工程
		this.itemLabel[0] = new ItemLabel();
		this.itemLabel[0].setText("全工程");
		this.itemLabel[0].setBounds(x,y,width,height);
		//計算
		x += defLabelWidth + defRadioWidth + 5;
		this.itemLabel[1] = new ItemLabel();
		this.itemLabel[1].setText("計算");
		this.itemLabel[1].setBounds(x,y,width,height);
		//2010.09.09 sakai change <---
		//パネルに追加
		for ( i=0; i<this.itemLabel.length; i++ ) {
			this.add(this.itemLabel[i]);
		}
				
		//2010.09.09 sakai change --->
		////-------------------- ラジオボタンの設定[0:計算, 1:全工程] --------------------------
		//-------------------- ラジオボタンの設定[0:全工程, 1:計算] --------------------------
		//2010.09.09 sakai change <---
		this.radioButton = new JRadioButton[2];
		x = this.itemLabel[0].getX() + defLabelWidth;
		y = this.itemLabel[0].getY();
		width = defRadioWidth;
		height = defHeight;
		//2010.09.09 sakai change --->
		////計算
		//this.radioButton[0] = new JRadioButton();
		//this.radioButton[0].setBounds(x,y,width,height);
		//this.radioButton[0].setSelected(true);
		//this.radioButton[0].addActionListener(this.getActionEvent());
		//this.radioButton[0].setActionCommand("keisan");
		//copyCheck.add(this.radioButton[0]);
		////全工程
		//x = this.itemLabel[1].getX() + defLabelWidth;
		//y = this.itemLabel[1].getY();
		//this.radioButton[1] = new JRadioButton();
		//this.radioButton[1].setBounds(x,y,width,height);
		//this.radioButton[1].addActionListener(this.getActionEvent());
		//this.radioButton[1].setActionCommand("allKotei");
		//copyCheck.add(this.radioButton[1]);
		//全工程
		this.radioButton[0] = new JRadioButton();
		this.radioButton[0].setBounds(x,y,width,height);
		this.radioButton[0].setSelected(true);
		this.radioButton[0].addActionListener(this.getActionEvent());
		this.radioButton[0].setActionCommand("allKotei");
		copyCheck.add(this.radioButton[0]);
		//計算
		x = this.itemLabel[1].getX() + defLabelWidth;
		y = this.itemLabel[1].getY();
		this.radioButton[1] = new JRadioButton();
		this.radioButton[1].setBounds(x,y,width,height);
		this.radioButton[1].addActionListener(this.getActionEvent());
		this.radioButton[1].setActionCommand("keisan");
		copyCheck.add(this.radioButton[1]);
		//2010.09.09 sakai change <---
		//パネルに追加
		for ( i=0; i<this.radioButton.length; i++ ) {
			this.radioButton[i].setBackground(Color.WHITE);
			this.add(this.radioButton[i]);
		}
		
		//---------------------------- テーブルの設定 ------------------------------------
		this.addTable();
		
		//----------------- ボタンの設定 [0:列追加, 1:採用, 2:キャンセル] ----------------------
		this.button = new ButtonBase[3];
		width = defButtonWidth;
		height = defButtonHeight;
		//列追加
		x = dispWidth - width - width - 120;
		y = this.prototypeListTable.getLeftHeaderTable().getY() + this.prototypeListTable.getLeftHeaderTable().getHeight() + 30;
		this.button[0] = new ButtonBase();
		this.button[0].setFont(new Font("Default", Font.PLAIN, 11));
		this.button[0].setBounds(x, y, width, height);
		this.button[0].setText("列追加");
		this.button[0].addActionListener(this.getActionEvent());
		this.button[0].setActionCommand("addRetu");
		//採用
		x = dispWidth - width - 120;
		this.button[1] = new ButtonBase();
		this.button[1].setFont(new Font("Default", Font.PLAIN, 11));
		this.button[1].setBounds(x, y, width, height);
		this.button[1].setText("採用");
		//キャンセル
		x = dispWidth - 120;
		this.button[2] = new ButtonBase();
		this.button[2].setFont(new Font("Default", Font.PLAIN, 11));
		this.button[2].setBounds(x, y, width + 20, height);
		this.button[2].setText("キャンセル");
		//パネルに追加
		for ( i=0; i<this.button.length; i++ ) {
			this.add(this.button[i]);
		}
	}
	
	/**********************************************************************************
	 * 
	 * パネルへテーブル配置
	 * @throws ExceptionBase 
	 * 
	 **********************************************************************************/
	public void addTable() throws ExceptionBase { 
		
		//----------------------------- 初期設定 ----------------------------------------
		//各種変数宣言
		int i;
		int x,y, width,height;
		int hoseiValue = 17;
		//テーブルのインスタンス生成
		this.prototypeListTable = new PrototypeListTable();
		
		
		//--------------------- 左側ヘッダーテーブルのサイズ ------------------------------
		x = 5;
		y = this.itemLabel[0].getY() + this.itemLabel[0].getHeight() + 5;
		width = 100;
		height = 230 - 17;
		this.prototypeListTable.getLeftHeaderTable().setBounds(x,y, width,height);

		
		//------------------------ ヘッダーテーブルのサイズ -------------------------------
		x = 104;
		y = this.itemLabel[0].getY() + this.itemLabel[0].getHeight() + 5;
		width = 500;
		height = 20;
		this.prototypeListTable.getScroll()[0].setBounds(x, y, width - hoseiValue, height);
		
		//--------------------- コンボボックステーブルのサイズ -----------------------------
		y += height - 2;
		height = 20;
		this.prototypeListTable.getScroll()[1].setBounds(x, y, width - hoseiValue, height);
		
		//------------------------- メインテーブルのサイズ --------------------------------
		y += height - 1;
		height = 193;
		this.prototypeListTable.getScroll()[2].setBounds(x, y, width, height);
		
		//----------------------------- パネルに追加 -------------------------------------
		for ( i=0; i<this.prototypeListTable.getScroll().length; i++ ) {
			this.add(this.prototypeListTable.getScroll()[i]);
		}
		this.add(this.prototypeListTable.getLeftHeaderTable());
		
	}
	
	/************************************************************************************
	 * 
	 *   ActionListenerイベント
	 *    : 試作列コピー画面でのボタン押下時の処理をキャッチする
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	private ActionListener getActionEvent(){
		return (
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						try {
							//イベント名取得
							String event_name = e.getActionCommand();
							
							//------------------------- 列追加 ------------------------------------
							if ( event_name == "addRetu") {
								
								//選択元テーブルを未選択に設定
								TableBase mainTable = prototypeListTable.getMainTable();
								TableCellEditor tce = mainTable.getCellEditor();
								if(tce != null){
									mainTable.getCellEditor().stopCellEditing();
								}
								
								//工程数チェック
								int dataRows = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
								int tableRows = prototypeListTable.getMaxKotei();
								boolean chkAddRetu = true;
								if(dataRows != tableRows){
									chkAddRetu = false;
								}
								
								//試作列数チェック
								int dataCols = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0).size();
								MiddleCellEditor mce = (MiddleCellEditor)prototypeListTable.getComboTable().getCellEditor(0, 0);
								DefaultCellEditor dce = (DefaultCellEditor)mce.getTableCellEditor(0);
								ComboBase cb = (ComboBase)dce.getComponent();
								int tableCols = cb.getItemCount();
								if(dataCols != tableCols){
									chkAddRetu = false;
								}
								
								//列追加処理
								//試作列が20列未満の場合に追加
								int maxcol = prototypeListTable.getHeaderTable().getColumnCount();
								
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
								int chkCol = 0;
								if(radioButton[0].isSelected()){
									chkCol = 10;
								}
								else{
									chkCol = 20;
								}
//add end ----------------------------------------------------------------------------------------
								
								if(maxcol < chkCol){
									if(chkAddRetu){
										//計算選択時
										//2010.09.09 sakai change --->
										//if(radioButton[0].isSelected()){
										if(radioButton[1].isSelected()){
										//2010.09.09 sakai change --->
											prototypeListTable.addHeaderTableCol();
											prototypeListTable.addComboTableCol();
											prototypeListTable.addMainTableCol();
										//全工程選択時
										}else{
											prototypeListTable.addHeaderTableCol();
											prototypeListTable.addComboTableCol();
											prototypeListTable.addMainTableCol_all();
										}
									}else{
										//エラー設定
										ex = new ExceptionBase();
										ex.setStrErrCd("");
										ex.setStrErrmsg("試作表の情報が変更されています。「試作列コピー」ボタンを押下し、再度設定して下さい。");
										ex.setStrErrShori(this.getClass().getName());
										ex.setStrMsgNo("");
										ex.setStrSystemMsg("");
										//メッセージ表示
										DataCtrl.getInstance().PrintMessage(ex);
									}
								}
								//試作列が20列以上の場合はメッセージ表示
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString("列追加は" + chkCol + "列までです。");
								}
								
							}
							
							//-------------------------- 計算 -------------------------------------
							if ( event_name == "keisan") {
								//テーブル初期化
								prototypeListTable.initMainTable();
								//列追加
								int col_count = prototypeListTable.getHeaderTable().getColumnCount();
								for(int i=0; i<col_count; i++){
									prototypeListTable.addMainTableCol();
								}
							}
							
							//------------------------- 全工程 ------------------------------------
							if ( event_name == "allKotei") {
								//テーブル初期化
								prototypeListTable.initMainTable();
								//列追加
								int col_count = prototypeListTable.getHeaderTable().getColumnCount();
								for(int i=0; i<col_count; i++){
									prototypeListTable.addMainTableCol_all();
								}
							}
							
						}catch(ExceptionBase be){
							//メッセージ表示
							DataCtrl.getInstance().PrintMessage(be);
							
						}catch (Exception ec) {
							ec.printStackTrace();
							//エラー設定
							ex = new ExceptionBase();
							ex.setStrErrCd("");
							ex.setStrErrmsg("試作列コピー画面でのボタン押下処理が失敗しました。");
							ex.setStrErrShori(this.getClass().getName());
							ex.setStrMsgNo("");
							ex.setStrSystemMsg(ec.getMessage());
							//メッセージ表示
							DataCtrl.getInstance().PrintMessage(ex);
							
						} finally {
							
						}
					}
				}
			);
	}
	
	/**********************************************************************************
	 * 
	 *  ボタン配列セッター&ゲッター
	 *  @return
	 * 
	 **********************************************************************************/
	public ButtonBase[] getButton() {
		return button;
	}
	public void setButton(ButtonBase[] button) {
		this.button = button;
	}
	
	/**********************************************************************************
	 * 
	 *  ラジオボタン配列セッター&ゲッター
	 *  @return
	 * 
	 **********************************************************************************/
	public JRadioButton[] getRadioButton() {
		return radioButton;
	}
	public void setRadioButton(JRadioButton[] radioButton) {
		this.radioButton = radioButton;
	}
	
	/**********************************************************************************
	 * 
	 *  テーブルセッター&ゲッター
	 *  @return
	 * 
	 **********************************************************************************/
	public PrototypeListTable getPrototypeListTable() {
		return prototypeListTable;
	}
	public void setPrototypeListTable(PrototypeListTable prototypeListTable) {
		this.prototypeListTable = prototypeListTable;
	}
}