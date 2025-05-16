package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.button.LinkButton;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.*;
import jp.co.blueflag.shisaquick.jws.manager.*;
import jp.co.blueflag.shisaquick.jws.table.EigyoTantoSearchTable;
import jp.co.blueflag.shisaquick.jws.textbox.*;

/************************************************************************************
 * 
 * 【QP@00342】
 * 営業担当検索サブ画面 パネル操作用のクラス
 * 
 * @author TT.Nishigawa
 * @since 2011/2/15
 * 
 ************************************************************************************/
public class EigyoTantoSerchPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private KaishaData KaishaData = new KaishaData();
	private BushoData BushoData = new BushoData();
	
	private DispTitleLabel dispTitleLabel;							//画面タイトルラベル
	private HeaderLabel headerLabel;								//ヘッダ表示ラベル
	private LevelLabel levelLabel;										//レベル表示ラベル
	private ItemLabel[] itemLabel;										//項目ラベル
	private TextboxBase TantoNmTextbox;							//担当者名テキストボックス
	private ComboBase kaishaComb;									//所属会社コンボボックス
	private ComboBase bushoComb;									//所属部署コンボボックス
	private ButtonBase[] button;										//ボタン
	private EigyoTantoSearchTable EigyoTantoSearchTable;	//試作分析データ確認テーブル
	private LinkButton[] linkBtn;										//リンクボタン
	private ItemLabel dataLabel;										//データ数表示ラベル
	private LinkButton linkPrevBtn;									//前項目リンクボタン
	private LinkButton linkNextBtn;									//次項目リンクボタン
	
	//会社コンボボックス　ゲッタ＆セッタ
	public ComboBase getKaishaComb() {
		return kaishaComb;
	}
	public void setKaishaComb(ComboBase kaishaComb) {
		this.kaishaComb = kaishaComb;
	}
	//部署コンボボックス　ゲッタ＆セッタ
	public ComboBase getBushoComb() {
		return bushoComb;
	}
	public void setBushoComb(ComboBase bushoComb) {
		this.bushoComb = bushoComb;
	}
	//会社データ　ゲッタ＆セッタ
	public KaishaData getKaishaData() {
		return KaishaData;
	}
	public void setKaishaData(KaishaData kaishaData) {
		KaishaData = kaishaData;
	}
	//部署データ　ゲッタ＆セッタ
	public BushoData getBushoData() {
		return BushoData;
	}
	public void setBushoData(BushoData bushoData) {
		BushoData = bushoData;
	}
	
	private int intLinkMaxNum = 0;								//リンク最大数
	
	private XmlConnection xmlConnection;						//ＸＭＬ通信
	private MessageCtrl messageCtrl;							//メッセージ操作
	private ExceptionBase ex;										//エラー操作
	private XmlData xmlData;										//ＸＭＬデータ保持
	
	private XmlData xmlRGEN2040;
	private XmlData xmlRGEN2060;
	private XmlData xmlJW900;
	
	int selectMaxNum = 0;
	int selectPage = 1;

	int selRow=0;

	private boolean isSelectKaishaCmb = false;	//会社コンボボックス選択処理フラグ
	//会社コンボボックス選択時のイベント名
	private final String KAISHA_COMB_SELECT = "kaishaCombSelect";
	//工場コンボボックス選択時のイベント名
	private final String BUSHO_COMB_SELECT = "bushoCombSelect";
	//リンクボタン押下時のイベント名
	private final String LINK_BTN_CLICK = "linkBtnClick";
	//「前へ」リンクボタン押下時のイベント名
	private final String LINK_PREV_BTN_CLICK = "linkPrevBtnClick";
	//「次へ」リンクボタン押下時のイベント名
	private final String LINK_NEXT_BTN_CLICK = "linkNextBtnClick";
	
	private ArrayList EigyoTantoAry = new ArrayList();
	
	//検索ボタン押下ﾌﾗｸﾞ
	private boolean selectFg = false;

	/************************************************************************************
	 * 
	 * コンストラクタ
	 * @param strOutput : 画面タイトル
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public EigyoTantoSerchPanel(String strOutput) throws ExceptionBase {
		//スーパークラスのコンストラクタを呼び出す
		super();

		try {
			//パネルの設定
			this.setPanel();
			
			//コントロール配置
			this.addControl(strOutput);
			
		}catch(ExceptionBase eb){
			
			throw eb;
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("営業担当検索サブ画面パネルのコンストラクタが失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
	}

	/************************************************************************************
	 * 
	 * パネル設定
	 * 
	 ************************************************************************************/
	private void setPanel() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
	}
	
	/************************************************************************************
	 * 
	 * コントロール配置
	 * @param strTitle : 画面タイトル
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void addControl(String strTitle) throws ExceptionBase {
		try{
			int i;
			int x, y, width, height;
			int defLabelWidth = 60;
			int defTextWidth = 200;
			int defHeight = 18;
			int dispWidth = 920;
			
			///
			/// タイトルラベル設定
			///
			this.dispTitleLabel = new DispTitleLabel();
			this.dispTitleLabel.setText(strTitle);
			this.add(this.dispTitleLabel);
			
			///
			/// レベルラベル設定
			///
			this.levelLabel = new LevelLabel();
			this.levelLabel.setBounds(dispWidth - 65, 5, 50, 15);
			this.add(this.levelLabel);
			
			///
			/// ヘッダラベル設定
			///
			this.headerLabel = new HeaderLabel();
			this.headerLabel.setBounds(10, 5, dispWidth - 80, 15);
			this.add(this.headerLabel);

			///
			/// 項目ラベルの設定
			/// [0:担当者名, 1:所属会社, 2:所属工場]
			///
			this.itemLabel = new ItemLabel[3];
			
			x = 5;
			y = 30;
			
			//0 : 担当者名
			this.itemLabel[0] = new ItemLabel();
			this.itemLabel[0].setText("担当者名");
			this.itemLabel[0].setBounds(x,y,defLabelWidth,defHeight);

			//1 : 所属会社
			x = this.itemLabel[0].getX();
			y = this.itemLabel[0].getY() + defHeight + 5;
			this.itemLabel[1] = new ItemLabel();
			this.itemLabel[1].setText("所属会社");
			this.itemLabel[1].setBounds(x,y,defLabelWidth,defHeight);
			
			//2 : 所属部署
			x = this.itemLabel[1].getX();
			y = this.itemLabel[1].getY() + this.itemLabel[1].getHeight() + 5;
			this.itemLabel[2] = new ItemLabel();
			this.itemLabel[2].setText("所属部署");
			this.itemLabel[2].setBounds(x,y,defLabelWidth,defHeight);
			
			//項目ラベルをパネルに追加
			for ( i=0; i<this.itemLabel.length; i++ ) {
				this.add(this.itemLabel[i]);
			}
			
			///
			///担当者名テキストボックス
			///
			x = this.itemLabel[0].getX();
			y = this.itemLabel[0].getY();
			width = this.itemLabel[0].getWidth();
			this.TantoNmTextbox = new TextboxBase();
			this.TantoNmTextbox.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.TantoNmTextbox);

			
			///
			///所属会社コンボボックス
			///
			x = this.itemLabel[1].getX();
			y = this.itemLabel[1].getY();
			width = this.itemLabel[1].getWidth();
			this.kaishaComb = new ComboBase();
			this.kaishaComb.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.kaishaComb);

			///
			///所属工場コンボボックス
			///
			x = this.itemLabel[2].getX();
			y = this.itemLabel[2].getY();
			width = this.itemLabel[2].getWidth();
			this.bushoComb = new ComboBase();
			this.bushoComb.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.bushoComb);
			
			///
			/// ボタン
			/// [0:検索, 1:選択, 2:終了]
			///
			this.button = new ButtonBase[3];	
			
			//0:検索
			x = dispWidth - 200;
			width = 80;
			height = 38;
			y = this.itemLabel[1].getY() + defHeight-10;
			this.button[0] = new ButtonBase();
			this.button[0].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[0].setBounds(x, y, width, height);
			this.button[0].setText("検索");
			this.button[0].addActionListener(this.getActionEvent());
			this.button[0].setActionCommand("kensaku");
			
			//1:選択
			y = this.itemLabel[1].getY() + defHeight-10;
			x = dispWidth - 100;
			this.button[1] = new ButtonBase();
			this.button[1].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[1].setBounds(x, y, width, height);
			this.button[1].setText("選択");
			
			//2:終了
			width = 80;
			y = 450;
			x = dispWidth - 100;
			this.button[2] = new ButtonBase();
			this.button[2].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[2].setBounds(x, y, width, height);
			this.button[2].setText("終了");
			//ボタンをパネルに追加
			for ( i=0; i<this.button.length; i++ ) {
				this.add(this.button[i]);
			}

			//テーブル配置
			this.addTable();
			
			
			///
			/// データ数表示ラベルの設定
			///
			this.dataLabel = new ItemLabel();
			this.dataLabel.setBackground(Color.WHITE);
			this.dataLabel.setFont(new Font("Default", Font.PLAIN, 13));
			this.dataLabel.setText("");
			this.dataLabel.setHorizontalAlignment(JLabel.CENTER);
			this.dataLabel.setBounds(0, 400, 800, 20);
			this.add(this.dataLabel);
			
			
			///
			/// リンクボタンの設定
			///
			this.linkBtn = new LinkButton[12];
			for ( i=0; i<this.linkBtn.length-2; i++ ) {
				String strText = "" + (i + 1);
				//this.linkBtn[i] = new LinkButton(strText,120 + (i*35),410);
				this.linkBtn[i] = new LinkButton(strText,dispWidth/2 - 110 + ((i-2)*35),405);
			}
			this.linkBtn[10] = new LinkButton("最初へ");
			this.linkBtn[10].setBounds(150,this.linkBtn[0].getY(),80,this.linkBtn[0].getHeight());
			
			this.linkBtn[11] = new LinkButton("最後へ");
			
			this.linkBtn[11].setBounds(this.linkBtn[9].getX() + this.linkBtn[9].getWidth() + this.linkBtn[9].getWidth()
					,this.linkBtn[9].getY(),80,this.linkBtn[9].getHeight());
			
			//リンクボタンをパネルに追加
			for ( i=0; i<this.linkBtn.length; i++ ) {
				this.add(this.linkBtn[i]);
			}
			
			///
			/// 「次へ」・「前へ」リンクボタンの設定
			///
			//リンクボタンにテキストと表示座標を設定
			this.linkPrevBtn = new LinkButton("<<", this.linkBtn[0].getX() - this.linkBtn[0].getWidth(), this.linkBtn[0].getY());
			this.linkNextBtn = new LinkButton(">>", this.linkBtn[9].getX() + this.linkBtn[9].getWidth(), this.linkBtn[9].getY());
			this.linkPrevBtn.setEnabled(false);
			this.linkNextBtn.setEnabled(false);
			//リンクボタンをパネルに追加
			this.add(this.linkPrevBtn);
			this.add(this.linkNextBtn);

			//会社コンボボックスにイベントを設定
			this.kaishaComb.addActionListener(this.getActionEvent());
			this.kaishaComb.setActionCommand(this.KAISHA_COMB_SELECT);
			
			//工場コンボボックスにイベントを設定
			this.bushoComb.addActionListener(this.getActionEvent());
			this.bushoComb.setActionCommand(this.BUSHO_COMB_SELECT);
			
			//リンクボタンにイベントを設定
			for (i=0; i<this.linkBtn.length; i++ ){
				this.linkBtn[i].addActionListener(this.getActionEvent());
				this.linkBtn[i].setActionCommand(this.LINK_BTN_CLICK);
				
			}
			//「次へ」・「前へ」リンクボタンをイベントを設定
			this.linkPrevBtn.addActionListener(this.getActionEvent());
			this.linkPrevBtn.setActionCommand(this.LINK_PREV_BTN_CLICK);
			this.linkNextBtn.addActionListener(this.getActionEvent());
			this.linkNextBtn.setActionCommand(this.LINK_NEXT_BTN_CLICK);
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("営業担当検索サブ画面パネルのコントロール配置処理が失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}

	/**
	 * フォーカス順設定用コンポーネント郡　ゲッター
	 * @return フォーカス順設定用コンポーネント郡 
	 */
	public JComponent[][] getSetFocusComponent() {
		JComponent[][] comp = new JComponent[][] {
				{ this.TantoNmTextbox },	//名前
				{ this.kaishaComb, this.bushoComb },				//会社コンボ, 工場コンボ
				this.button,										//ボタン
				{ this.linkPrevBtn },								//前へリンクボタン 
				this.linkBtn,										//リンクボタン
				{ this.linkNextBtn }								//次へリンクボタン
		};
		return comp;
	}
	
	
	/************************************************************************************
	 * 
	 * 営業担当検索サブ画面テーブル初期化処理
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void addTable() throws ExceptionBase {
		try{
			int intRow = 0;			//行数
			int intCol = 6;		//列数
			Object[] columnNm = new Object[intCol];		//列名
			int[] columnWidth = new int[intCol];				//列幅

			//列名の設定
			columnNm[0]  = "No";
			columnNm[1]  = "担当者名";
			columnNm[2]  = "所属会社";
			columnNm[3]  = "所属部署";
			columnNm[4]  = "本部権限";
			columnNm[5]  = "担当上司";

			//列幅の設定
			columnWidth[0] = 20;		//行No
			columnWidth[1] = 200;	//担当者名
			columnWidth[2] = 200;	//所属会社
			columnWidth[3] = 200;	//所属部署
			columnWidth[4] = 70;		//本部権限
			columnWidth[5] = 200;	//担当上司
			
			//テーブルのインスタンス生成
			this.EigyoTantoSearchTable = new EigyoTantoSearchTable(intRow, intCol, columnNm, columnWidth);
			this.EigyoTantoSearchTable.getScroll().setBounds(5, 120, 910, 275);
			this.add(this.EigyoTantoSearchTable.getScroll());
			
			//マウスイベント設定
			this.EigyoTantoSearchTable.getMainTable().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					//選択行の行番号を取得します
					int ii = EigyoTantoSearchTable.getMainTable().getSelectedRow();
					selRow = ii;
				}
			});
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("営業担当検索サブ画面パネルのテーブル初期化処理が失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/************************************************************************************
	 * 
	 * 初期化処理
	 * 
	 ************************************************************************************/
	public void init() throws ExceptionBase{
		try{
			selectFg = false;
			selectPage = 1;
			
			//担当者名テキストボックス
			TantoNmTextbox.setText(null);
			
			//------------------------------- テーブル初期化 ---------------------------------
			dispClear();
			
			//------------------------ 会社コンボボックスの初期設定 ----------------------------
			this.isSelectKaishaCmb = false;
			conJW610();
			this.setKaishaCmb();
			
			//------------------------ 部署コンボボックスの初期設定 ----------------------------
			this.isSelectKaishaCmb = true;
			this.selectKaishaComb();

			//---------------------------- リンクボタンの初期設定 ------------------------------
			for(int i=0; i<this.linkBtn.length-2; i++) {
				this.linkBtn[i].setEnabled(false);
			}
			//「次へ」・「前へ」リンクボタンの初期設定
			this.linkNextBtn.setEnabled(false);
			this.linkPrevBtn.setEnabled(false);
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("営業担当検索サブ画面パネルのテーブル初期化処理が失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/************************************************************************************
	 * 
	 *   テーブル表示
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	public void dispTable() throws ExceptionBase{
		try{
			//テーブル初期化
			dispClear();
			//初期設定
			int max_row=0;
			int row_num=0;
			TableBase mt = EigyoTantoSearchTable.getMainTable();
			
			//レンダラ生成（文字列）
			MiddleCellRenderer md = new MiddleCellRenderer();
			
			//レンダラ生成（数値）
			MiddleCellRenderer nmd = new MiddleCellRenderer();

			for(int i=0; i<EigyoTantoAry.size(); i++){
				
				//データ挿入
				EigyoTantoData EigyoTantoData = (EigyoTantoData)EigyoTantoAry.get(i);
				
				//行追加
				mt.tableInsertRow(i);
				
				//レンダラ設定（文字列）
				TextboxBase comp = new TextboxBase();
				comp.setBackground(Color.WHITE);
				
				//レンダラ設定（数値）
				NumelicTextbox ncomp = new NumelicTextbox();
				ncomp.setBackground(Color.WHITE);
								
				//リスト件数の格納
				max_row = EigyoTantoData.getIntMaxRow();
				row_num = EigyoTantoData.getIntListRowMax();
				selectMaxNum = max_row;
				
				//データ設定
				//No
				mt.setValueAt(EigyoTantoData.getNo_gyo(), i, 0);
				//担当者名
				mt.setValueAt(EigyoTantoData.getNm_user(), i, 1);
				//所属会社
				mt.setValueAt(EigyoTantoData.getNm_kaisha(), i, 2);
				//所属部署
				mt.setValueAt(EigyoTantoData.getNm_busho(), i, 3);
				//本部権限
				mt.setValueAt(EigyoTantoData.getHonbukengen(), i, 4);
				//担当上司
				mt.setValueAt(EigyoTantoData.getNm_josi(), i, 5);
				

				//文字列レンダラ設定
				TextFieldCellRenderer rendComp = new TextFieldCellRenderer(comp);
				md.add(i, rendComp);
				
				//数値レンダラ設定
				TextFieldCellRenderer nrendComp = new TextFieldCellRenderer(ncomp);
				nmd.add(i, nrendComp);
			}
			
			//レンダラ設定
			//テーブルカラム取得
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)mt.getColumnModel();
			TableColumn column = null;
			for(int i = 0; i<mt.getColumnCount(); i++){
				
				//テーブルカラムへ設定
				column = mt.getColumnModel().getColumn(i);

				//行No
				if(i == 0){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}
				//担当者名
				else if(i == 1){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}
				//所属会社
				else if(i == 2){
					//文字列レンダラ設定
					column.setCellRenderer(md);			
				}
				//所属部署
				else if(i == 3){
					//文字列レンダラ設定
					column.setCellRenderer(md);			
				}
				//本部権限
				else if(i == 4){
					//文字列レンダラ設定
					column.setCellRenderer(md);				
				}
				//担当上司
				else if(i == 5){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}
			}
			initLink(max_row, row_num);
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("営業担当検索サブ画面パネルのテーブル表示処理が失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
		
	}
	
	/************************************************************************************
	 * 
	 *   リンクボタン初期化処理
	 *   
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	private void initLink(int intMaxRow, int intListRowMax) {

		//リンクボタンの初期設定
		for(int i=0; i<this.linkBtn.length; i++) {
			this.linkBtn[i].setEnabled(false);
		}
		this.linkPrevBtn.setEnabled(false);
		this.linkNextBtn.setEnabled(false);
		
		//最大値が0でない場合
		if ( intMaxRow != 0 ) {
			if ( (intMaxRow%intListRowMax != 0) ) {
				intLinkMaxNum = (intMaxRow/intListRowMax)+1;
			} else {
				intLinkMaxNum = (intMaxRow/intListRowMax);
			}

			for( int i=0; i<intLinkMaxNum; i++ ) {
				if ( i < 10 ) {
					if ( Integer.parseInt(this.linkBtn[i].getText()) <= intLinkMaxNum ) {
						this.linkBtn[i].setEnabled(true);
						
					}
				}
			}
			this.linkBtn[10].setEnabled(true);
			this.linkBtn[11].setEnabled(true);
			
			//検索結果の件数が表示最大件数より、上回っている場合
			if ( intMaxRow > (intListRowMax*10) ) {	
				//[次へ]・[前へ]リンクボタンを使用可に設定
				this.linkPrevBtn.setEnabled(true);
				this.linkNextBtn.setEnabled(true);
			}
		}
	}
	
	/************************************************************************************
	 * 
	 *   検索処理用　XML通信処理（JW900）
	 *    : 検索処理XMLデータ通信（JW900）を行う
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	public void conJW900(int page) throws ExceptionBase{
		try{
			
			//----------------------------- 送信パラメータ格納  -------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strKaishaCd = this.getSelectKaishaCd();
			String strBushoCd = this.getSelectKojoCd();
			
			//----------------------------- 送信XMLデータ作成  ------------------------------
			xmlJW900 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//--------------------------------- Root追加  ---------------------------------
			xmlJW900.AddXmlTag("","JW900");
			arySetTag.clear();
			
			//--------------------------- 機能ID追加（USEERINFO）  --------------------------
			xmlJW900.AddXmlTag("JW900", "USERINFO");
			//　テーブルタグ追加
			xmlJW900.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW900.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();
			
			//------------------------ 機能ID追加（担当者検索）  ----------------------------
			xmlJW900.AddXmlTag("JW900", "FGEN2110");
			//　テーブルタグ追加
			xmlJW900.AddXmlTag("FGEN2110", "table");
			
			//　レコード追加
			String[] id_user = new String[]{"id_user", ""};
			String[] nm_genryo = new String[]{"nm_user", checkNull(TantoNmTextbox.getText())};
			String[] cd_kaisha = new String[]{"cd_kaisha",strKaishaCd};
			String[] cd_busho = new String[]{"cd_busho", strBushoCd};
			String[] num_selectRow = new String[]{"no_page", Integer.toString(page)};
			String[] kbn_shori = new String[]{"kbn_shori", "1"};

			arySetTag.add(id_user);
			arySetTag.add(nm_genryo);
			arySetTag.add(cd_kaisha);
			arySetTag.add(cd_busho);
			arySetTag.add(num_selectRow);
			arySetTag.add(kbn_shori);
			
			xmlJW900.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("\nJW720送信XML===============================================================");
//			xmlJW900.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJW900);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJW900 = xcon.getXdocRes();
//			System.out.println("\nJW720受信XML===============================================================");
//			xmlJW900.dispXml();
//			xmlJW720.dispXml();
			
			//テストXMLデータ
			//xmlJW720 = new XmlData(new File("src/main/JW720.xml"));
			
			//------------------------------- Resultデータチェック -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW900);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				
				//画面情報クリア
				dispClear();
				
				//データ数表示ラベルクリア
				this.dataLabel.setText("");
				
				throw new ExceptionBase();
				
			}
			else{
				XmlData xdtData = xmlJW900;
				
				EigyoTantoAry = new ArrayList();
				 
				 try{
					//　XMLデータ格納
					String strKinoId = "FGEN2110";
					
					//全体配列取得
					ArrayList mateData = xdtData.GetAryTag(strKinoId);
					
					//機能配列取得
					ArrayList kinoData = (ArrayList)mateData.get(0);
					
					//テーブル配列取得
					ArrayList tableData = (ArrayList)kinoData.get(1);

					//レコード取得
					for(int i=1; i<tableData.size(); i++){
					//　１レコード取得
					ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
					
						//営業担当者データ生成
						EigyoTantoData EigyoTantoData = new EigyoTantoData();
						
						//営業担当者データへ格納
						for(int j=0; j<recData.size(); j++){
							
							//　項目名取得
							String recNm = ((String[])recData.get(j))[1];
							//　項目値取得
							String recVal = ((String[])recData.get(j))[2];
							
							/*****************原料データへ値セット*********************/
							// 行No
							if(recNm == "no_row"){
								EigyoTantoData.setNo_gyo(recVal);
							}
							// ユーザーID
							if(recNm == "id_user"){
								EigyoTantoData.setId_user(recVal);
							}
							// ユーザー名称
							if(recNm == "nm_user"){
								EigyoTantoData.setNm_user(recVal);
							}
							// 会社CD
							if(recNm == "cd_kaisha"){
								EigyoTantoData.setCd_kaisha(recVal);
							}
							// 会社名
							if(recNm == "nm_kaisha"){
								EigyoTantoData.setNm_kaisha(recVal);
							}
							// 部署CD
							if(recNm == "cd_busho"){
								EigyoTantoData.setCd_busho(recVal);
							}
							// 部署名称
							if(recNm == "nm_busho"){
								EigyoTantoData.setNm_busho(recVal);
							}
							// 本部権限
							if(recNm == "kbn_honbu"){
								EigyoTantoData.setHonbukengen(recVal);
							}
							// 上司ID
							if(recNm == "id_josi"){
								EigyoTantoData.setId_josi(recVal);
							}
							// 上司名
							if(recNm == "nm_josi"){
								EigyoTantoData.setNm_josi(recVal);
							}
							// 総件数
							if(recNm == "max_row"){
								EigyoTantoData.setIntMaxRow(checkNullInt(recVal));
							}
							// 頁内表示件数
							if(recNm == "list_max_row"){
								EigyoTantoData.setIntListRowMax(checkNullInt(recVal));
							}
						}
						//　営業担当配列へ追加
						this.EigyoTantoAry.add(EigyoTantoData);
					}
				}catch(Exception e){
					 
				}
				
				//データ数表示設定
				//リスト表示件数及び最大件数を取得
				EigyoTantoData EigyoTantoData = (EigyoTantoData)EigyoTantoAry.get(0);
				int intListRowMax = EigyoTantoData.getIntListRowMax();
				int intMaxRow = EigyoTantoData.getIntMaxRow();
				
				//最大値が0でない場合
				if ( intMaxRow != 0 ) {
					if ( (intMaxRow%intListRowMax != 0) ) {
						intLinkMaxNum = (intMaxRow/intListRowMax)+1;
					} else {
						intLinkMaxNum = (intMaxRow/intListRowMax);
					}
				}
				//データ数の表示
				this.dataLabel.setText("<html>データ数　：　" + intMaxRow + " 件です(" + intListRowMax + "件毎に表示しています)　　　<b>"+selectPage+"／"+intLinkMaxNum+" ページ<b></html>");
			}
			
			
		}catch(ExceptionBase ex){
			throw ex;
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの原料検索通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
		
	/**
	 * 空文字チェック（Int）
	 */
	public int checkNullInt(String val){
		int ret = -1;
		try{
			//値が空文字でない場合
			if(!val.equals("")){
				ret = Integer.parseInt(val);
			}
		}catch(Exception e){
			
		}finally{
			
		}
		return ret;
	}
	
	/**********************************************************************************
	 * 
	 * ActionListenerイベント
	 * @return
	 * 
	 **********************************************************************************/
	private ActionListener getActionEvent() {
		return (
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						//イベント名取得
						String event_name = e.getActionCommand();
						
						//--------------------- 選択 ボタン クリック時の処理 -------------------------
						if(event_name == "kensaku"){
						
							selectPage = 1;
							
							//検索処理（JW900）
							conJW900(selectPage);
							dispTable();
							selectFg = true;

							//リンク初期化
							for ( int i=0; i<10; i++) {
								linkBtn[i].setText("" + (i+1));
								//リンク使用可否再設定
								if ( (i+1) <= intLinkMaxNum ) {
									linkBtn[i].setEnabled(true);
									
								} else {
									linkBtn[i].setEnabled(false);
									
								}
								
							}
							
						
						}
						//---------------------- 会社コンボボックス選択時 --------------------------
						else if ( event_name.equals(KAISHA_COMB_SELECT)) {
							selectKaishaComb();
							
						}
						//---------------------- 工場コンボボックス選択時 --------------------------
						else if ( event_name.equals(BUSHO_COMB_SELECT)) {
							selectBushoComb();
							
						}
						//------------------------- リンクボタン押下時 -----------------------------
						else if ( event_name.equals(LINK_BTN_CLICK) ) {
							
							ButtonBase link = (ButtonBase)e.getSource();
							String strPage = link.getText();
							//データ挿入
							EigyoTantoData EigyoTantoData = (EigyoTantoData)EigyoTantoAry.get(0);
							int intRowNum = EigyoTantoData.getIntListRowMax();
							
							
							//検索ボタン押下
							if(selectFg){
								if(strPage.equals("最初へ")){
									selectPage = 1;
									conJW900(selectPage);
									dispTable();
									
									//リンク初期化
									for ( int i=0; i<10; i++) {
										linkBtn[i].setText("" + (i+1));
										//リンク使用可否再設定
										if ( (i+1) <= intLinkMaxNum ) {
											linkBtn[i].setEnabled(true);
											
										} else {
											linkBtn[i].setEnabled(false);
											
										}
										
									}

								}else if(strPage.equals("最後へ")){
									selectPage = intLinkMaxNum;
									conJW900(selectPage);
									dispTable();
									
									//最終ページまで進める
									for ( int i=0; i<(selectMaxNum / (intRowNum*10)); i++ ) {
										clickLinkNextBtn(linkNextBtn);
										
									}
									
								}else{
									selectPage = Integer.parseInt(strPage);
									conJW900(selectPage);
									dispTable();
									
								}
							}
						}
						//------------------------- <<（前へ）リンクボタン押下時 -----------------------------
						else if ( event_name.equals(LINK_PREV_BTN_CLICK) ) {
							clickLinkPrevBtn(e.getSource());
							
						}
						//------------------------- >>（次へ）リンクボタン押下時 -----------------------------
						else if ( event_name.equals(LINK_NEXT_BTN_CLICK) ) {
							clickLinkNextBtn(e.getSource());
							
						}
						
						
					}catch(ExceptionBase eb){
						DataCtrl.getInstance().PrintMessage(eb);
						
					}catch(Exception ec){
						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("営業担当検索サブ画面パネルのActionListenerイベントが失敗しました");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
						
					}finally{
						
					}
				}

			}
		);
	}
	
	/************************************************************************************
	 * 
	 * 会社コンボボックス設定処理
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void setKaishaCmb() throws ExceptionBase {
		int i;
		
		try {
			String kaishaNm = "";
			
			//コンボボックスの全項目の削除
			this.kaishaComb.removeAllItems();
	
			//表示順に沿ってコンボボックスに値を追加
			for ( i=0; i<KaishaData.getArtKaishaCd().size(); i++ ) {
				//会社名
				kaishaNm = KaishaData.getAryKaishaNm().get(i).toString(); 
				
				//コンボボックスへ追加
				this.kaishaComb.addItem(kaishaNm);
			}
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの会社コンボボックス設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
	}

	/************************************************************************************
	 * 
	 * 部署コンボボックス設定処理
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void setKojoCmb() throws ExceptionBase {
		int i;
		try {
			String bushoNm = "";
			
			//コンボボックスの全項目の削除
			this.bushoComb.removeAllItems();
			
			//表示順に沿ってコンボボックスに値を追加
			this.bushoComb.addItem("");
			for ( i=0; i<BushoData.getArtBushoCd().size(); i++ ) {
				//会社名
				bushoNm = BushoData.getAryBushoNm().get(i).toString(); 
				
				//コンボボックスへ追加
				this.bushoComb.addItem(bushoNm);
			}
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの部署コンボボックス設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
	}

	/************************************************************************************
	 * 
	 * 選択会社コードの取得
	 * @return 選択会社コード 
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private String getSelectKaishaCd() throws ExceptionBase {
		int i;
		String intRetKaishaCd = "";
		String kaishaCd = "";
		String kaishaNm = "";
		try {
			
			//表示順に沿ってコンボボックスに値を追加
			for ( i=0; i<KaishaData.getArtKaishaCd().size(); i++ ) {
				//会社コード
				kaishaCd = KaishaData.getArtKaishaCd().get(i).toString();
				//会社名
				kaishaNm = KaishaData.getAryKaishaNm().get(i).toString(); 
	
				//選択会社コードの検出
				if ( kaishaNm.equals(this.kaishaComb.getSelectedItem().toString()) ) {
					intRetKaishaCd = kaishaCd;
				}
			}
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの選択会社コード取得処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
		return intRetKaishaCd;
	}
	
	/************************************************************************************
	 * 
	 * 【JW610】 会社コンボボックス値取得 送信XMLデータ作成
	 * 
	 ************************************************************************************/
	private void conJW610() throws ExceptionBase{
		try{
			
			//　送信パラメータ格納
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
			//　送信XMLデータ作成
			xmlRGEN2040 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//　Root追加
			xmlRGEN2040.AddXmlTag("","RGEN2040");
			arySetTag.clear();
			
			//　機能ID追加
			xmlRGEN2040.AddXmlTag("RGEN2040", "USERINFO");
			//　テーブルタグ追加
			xmlRGEN2040.AddXmlTag("USERINFO", "table");	
			//　レコード追加
			String[] kbn_shori = {"kbn_shori", "3"};
			String[] id_user = {"id_user",strUser};
			arySetTag.add(kbn_shori);
			arySetTag.add(id_user);
			xmlRGEN2040.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();

			//　【部署検索】 機能ID追加
			xmlRGEN2040.AddXmlTag("RGEN2040", "FGEN2090");
			//　テーブルタグ追加
			xmlRGEN2040.AddXmlTag("FGEN2090", "table");
			String[] nulldata = {"data",""};
			arySetTag.add(nulldata);
			xmlRGEN2040.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
//			xmlJW610.dispXml();
			//　XML送信
			XmlConnection xmlConnection = new XmlConnection(xmlRGEN2040);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//　XML受信
			xmlRGEN2040 = xmlConnection.getXdocRes();
//			xmlJW610.dispXml();
			
			//　テストXMLデータ
			//xmlJW610 = new XmlData(new File("src/main/JW610.xml"));
			
			// 会社データ
			KaishaData.setKaishaData_Eigyo(xmlRGEN2040);

			// Resultデータ
			DataCtrl.getInstance().getResultData().setResultData(xmlRGEN2040);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
			}
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの会社コンボボックス値取得通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
	
	/************************************************************************************
	 * 
	 * 会社コンボボックス選択時処理
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void selectKaishaComb() throws ExceptionBase {
		try {
			if ( this.isSelectKaishaCmb ) {		//多重検索を防ぐ
				
				this.conJW620(this.getSelectKaishaCd());
				
				this.setKojoCmb();
			}
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの会社コンボボックス選択時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}

	/************************************************************************************
	 * 
	 * 工場コンボボックス選択時処理
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void selectBushoComb() throws ExceptionBase {
		try {
			if ( this.bushoComb.isValid() ) {		//多重検索を防ぐ
				
			}
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの工場コンボボックス選択時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}
	
	/************************************************************************************
	 * 
	 * 選択工場コードの取得
	 * @return 選択工場コード 
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private String getSelectKojoCd() throws ExceptionBase {
		int i;
		String strRetKojoCd = "";
		String bushoCd = "";
		String bushoNm = "";
		try {
			//工場コンボボックスの選択値を取得
			String kojoCombItem = this.bushoComb.getSelectedItem().toString();
			
			//工場が全工場ではない場合、選択されている部署名にひ部署コードを取得
			for ( i=0; i<BushoData.getArtBushoCd().size(); i++ ) {
				//会社コード
				bushoCd = BushoData.getArtBushoCd().get(i).toString();
				//会社名
				bushoNm = BushoData.getAryBushoNm().get(i).toString(); 
		
				//選択会社コードの検出
				if ( bushoNm.equals(kojoCombItem) ) {
					strRetKojoCd = bushoCd;
				}
			}
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの選択工場コード取得処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		return strRetKojoCd;
	}
		
	/************************************************************************************
	 * 
	 * 【RGEN2060】 会社コンボボックス検索時 送信XMLデータ作成
	 * @param strKaishaCd : 会社コード
	 * 
	 ************************************************************************************/
	private void conJW620(String strKaishaCd) throws ExceptionBase{
		try{
			
			//　送信パラメータ格納
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
			//　送信XMLデータ作成
			xmlRGEN2060 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//　Root追加
			xmlRGEN2060.AddXmlTag("","RGEN2060");
			arySetTag.clear();
			
			//　機能ID追加
			xmlRGEN2060.AddXmlTag("RGEN2060", "USERINFO");
			//　テーブルタグ追加
			xmlRGEN2060.AddXmlTag("USERINFO", "table");	
			//　レコード追加
			String[] kbn_shori = {"kbn_shori", "3"};
			String[] id_user = {"id_user",strUser};
			arySetTag.add(kbn_shori);
			arySetTag.add(id_user);
			xmlRGEN2060.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();

			//　【部署検索】 機能ID追加
			xmlRGEN2060.AddXmlTag("RGEN2060", "FGEN2070");

			//　テーブルタグ追加
			xmlRGEN2060.AddXmlTag("FGEN2070", "table");
			//　レコード追加
			String[] cd_kaisha = new String[]{"cd_kaisha",strKaishaCd};
						
			String[] eigyo_kengen = new String[]{"eigyo_kengen","0"};
						
			arySetTag.add(cd_kaisha);
						
			arySetTag.add(eigyo_kengen);
						
			xmlRGEN2060.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
//			xmlJW620.dispXml();
			//　XML送信
			XmlConnection xmlConnection = new XmlConnection(xmlRGEN2060);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//　XML受信
			xmlRGEN2060 = xmlConnection.getXdocRes();
//			xmlJW620.dispXml();
			
			//部署データ
			BushoData.setBushoData_Eigyo(xmlRGEN2060);

			// Resultデータ
			DataCtrl.getInstance().getResultData().setResultData(xmlRGEN2060);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
				
			}
			
		}catch(ExceptionBase e){
			throw e;
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの会社コンボボックス検索通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}

	/************************************************************************************
	 * 
	 * NULLチェック処理（オブジェクト）
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	private String checkNull(Object val){
		String ret = "NULL";
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
		String ret = "NULL";
		if(val >= 0){
			ret = Integer.toString(val);
		}
		return ret;
	}
	
	/************************************************************************************
	 * 
	 * 画面初期化処理
	 * 
	 ************************************************************************************/
	public void dispClear() throws ExceptionBase{
		try{
			//テーブルクリア処理
			if ( EigyoTantoSearchTable.getMainTable().getRowCount() > 0 ) {
				for ( int i=EigyoTantoSearchTable.getMainTable().getRowCount()-1; i>-1; i-- ) {
					EigyoTantoSearchTable.getMainTable().tableDeleteRow(i);
				}
			}
			//リンクボタン
			for(int i=0; i<linkBtn.length-2; i++){
				linkBtn[i].setEnabled(false);
			}
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの画面初期化処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}
	
	/************************************************************************************
	 * 
	 * ボタンゲッター
	 * 
	 ************************************************************************************/
	public ButtonBase[] getButton() {
		return button;
	}

	/************************************************************************************
	 * 
	 * 「次へ」リンクボタン押下時処理
	 * @param source
	 * @throws ExceptionBase
	 *  
	 ************************************************************************************/
	private void clickLinkNextBtn(Object source) throws ExceptionBase {

		try {
			
			//リンク最大表示値を取得
			int chkValue = Integer.parseInt(this.linkBtn[9].getText());
			
			//リンク最大表示値が検索結果最大値を超えていないか
			if ( chkValue < this.intLinkMaxNum ) {

				//リンク最小表示値を取得
				int intValue = Integer.parseInt(this.linkBtn[0].getText());
				
				//リンク表示値を増やす
				for ( int i=0; i<10; i++ ) {
					
					//リンク表示値再設定
					this.linkBtn[i].setText(Integer.toString(intValue + 10 + i));
					
					//リンク使用可否再設定
					if ( (intValue + 10 + i) <= intLinkMaxNum ) {
						this.linkBtn[i].setEnabled(true);
						
					} else {
						this.linkBtn[i].setEnabled(false);
						
					}
					
				}
				
			}
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの<<リンクボタン押下時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 * 「前へ」リンクボタン押下時処理
	 * @param source
	 * @throws ExceptionBase
	 *  
	 ************************************************************************************/
	private void clickLinkPrevBtn(Object source) throws ExceptionBase {

		try {

			//リンク最小表示値
			int chkValue = Integer.parseInt(this.linkBtn[0].getText());
			
			//リンク最小表示値が初期値ではない場合
			if ( chkValue != 1 ) {				
				
				//リンク表示値を１０前に戻す
				for ( int i=0; i<10; i++ ) {
					
					//リンク表示値再設定
					this.linkBtn[i].setText(Integer.toString(chkValue - 10 + i));
					
					//リンク使用可否再設定
					if ( (chkValue - 10 + i) <= intLinkMaxNum ) {
						this.linkBtn[i].setEnabled(true);
						
					} else {
						this.linkBtn[i].setEnabled(false);
						
					}
					
				}
				
			}
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("営業担当検索サブ画面パネルの>>リンクボタン押下時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
	
	public ArrayList getEigyoTantoAry() {
		return EigyoTantoAry;
	}
	public void setEigyoTantoAry(ArrayList eigyoTantoAry) {
		EigyoTantoAry = eigyoTantoAry;
	}
	public EigyoTantoSearchTable getEigyoTantoSearchTable() {
		return EigyoTantoSearchTable;
	}
	public void setEigyoTantoSearchTable(EigyoTantoSearchTable eigyoTantoSearchTable) {
		EigyoTantoSearchTable = eigyoTantoSearchTable;
	}
	
}