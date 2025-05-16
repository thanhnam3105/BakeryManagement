package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;

import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.KaishaData;
import jp.co.blueflag.shisaquick.jws.base.MaterialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.button.LinkButton;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;
import jp.co.blueflag.shisaquick.jws.label.HeaderLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.label.LevelLabel;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.table.MaterialTable;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;

/**
 * 
 * 【A05-05】 原料一覧パネル操作用のクラス
 * 
 * @author k-katayama
 * @since 2009/04/03
 */
public class MaterialPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private KaishaData KaishaData = new KaishaData();
	private BushoData BushoData = new BushoData();
	
	private DispTitleLabel dispTitleLabel;			//画面タイトルラベル
	private HeaderLabel headerLabel;				//ヘッダ表示ラベル
	private LevelLabel levelLabel;						//レベル表示ラベル
	private ItemLabel[] itemLabel;						//項目ラベル[0:コード, 1:会社, 2:名前, 3:工場]
	private HankakuTextbox codeTextbox;			//コードテキストボックス
	private TextboxBase nameTextbox;				//名前テキストボックス
	private ComboBase kaishaComb;					//会社コンボボックス
	private ComboBase kojoComb;					//工場コンボボックス
	private ButtonBase searchBtn;					//検索ボタン
	private ButtonBase endBtn;						//終了ボタン
	private MaterialTable materialTable;				//原料一覧テーブル
	private LinkButton[] linkBtn;						//リンクボタン
	private ItemLabel dataLabel;						//データ数表示ラベル
	private LinkButton linkPrevBtn;					//前項目リンクボタン
	private LinkButton linkNextBtn;					//次項目リンクボタン
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	private JRadioButton[] radioButton;					//ラジオボタン
	private ButtonGroup copyCheck = new ButtonGroup();
//add end --------------------------------------------------------------------------------------

	private XmlData xmlJW610;						//ＸＭＬデータ保持(JW610)
	private XmlData xmlJW620;						//ＸＭＬデータ保持(JW620)
	private XmlData xmlJW630;						//ＸＭＬデータ保持(JW630)
	private ExceptionBase ex;							//エラー操作
	
	//検索ボタン押下時のイベント名
	private final String SEARCH_BTN_CLICK = "searchBtnClick";
	//会社コンボボックス選択時のイベント名
	private final String KAISHA_COMB_SELECT = "kaishaCombSelect";
	//リンクボタン押下時のイベント名
	private final String LINK_BTN_CLICK = "linkBtnClick";
	//「前へ」リンクボタン押下時のイベント名
	private final String LINK_PREV_BTN_CLICK = "linkPrevBtnClick";
	//「次へ」リンクボタン押下時のイベント名
	private final String LINK_NEXT_BTN_CLICK = "linkNextBtnClick";
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	//工場コンボボックス選択時のイベント名
	private final String KOUJYOU_COMB_SELECT = "KoujyouCombSelect";
//add end --------------------------------------------------------------------------------------
	
	private boolean isSelectKaishaCmb = false;	//会社コンボボックス選択処理フラグ
	private int intLinkMaxNum = 0;					//リンク最大数

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.6
	private final String KOJO_ZENKOJO = "zenkojo";			//全工場の場合
	private final String KOJO_ZENKOJO_YOKO = "全工場_横";		//全工場の場合の文言
	private final String KOJO_ZENKOJO_TATE = "全工場_縦";		//空白の場合の文言
//add end --------------------------------------------------------------------------------------
	
	
	/**
	 * コンストラクタ
	 * @param strOutput : 画面タイトル
	 * @throws ExceptionBase
	 */
	public MaterialPanel(String strOutput) throws ExceptionBase {
		//スーパークラスのコンストラクタを呼び出す
		super();

		try {
			//１．パネルの設定
			this.setPanel();
			
			//２．コントロールの配置
			
			//コントロール配置
			this.addControl(strOutput);	

			//３．初期処理を実行
			this.init();
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch(Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原料一覧パネルコンストラクタが失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.toString());
			throw ex;
			
		} finally {
			
		}
		
	}
	
	/**
	 * パネル設定
	 */
	private void setPanel() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
	}
	
	/**
	 * コントロール配置
	 * @param strTitle : 画面タイトル
	 * @throws ExceptionBase 
	 */
	private void addControl(String strTitle) throws ExceptionBase {
		int i;
		int x,y, width,height;
		
		int defLabelWidth = 60;
		int defTextWidth = 200;
		int defHeight = 18;
		int dispWidth = 900;
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
		int defRadioWidth = 60;
//add end --------------------------------------------------------------------------------------

		try {
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
			/// 項目ラベルの設定[0:コード, 1:名前, 2:会社, 3:工場]
			///
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			//this.itemLabel = new ItemLabel[4];
			this.itemLabel = new ItemLabel[6];
			//0 : コード
			x = 5;
			y = 30;
			this.itemLabel[0] = new ItemLabel();
			this.itemLabel[0].setText("コード");
			this.itemLabel[0].setBounds(x,y,defLabelWidth,defHeight);
			//1 : 名前
			x = this.itemLabel[0].getX();
			y = this.itemLabel[0].getY() + defHeight + 5;
			this.itemLabel[1] = new ItemLabel();
			this.itemLabel[1].setText("名前");
			this.itemLabel[1].setBounds(x,y,defLabelWidth,defHeight);
			//2 : 会社
			x = this.itemLabel[0].getX() + defLabelWidth + defTextWidth + 5;
			y = this.itemLabel[0].getY();
			this.itemLabel[2] = new ItemLabel();
			this.itemLabel[2].setText("会社");
			this.itemLabel[2].setBounds(x,y,defLabelWidth,defHeight);
			//3 : 工場
			x = this.itemLabel[2].getX();
			y = this.itemLabel[2].getY() + defHeight + 5;
			this.itemLabel[3] = new ItemLabel();
			this.itemLabel[3].setText("工場");
			this.itemLabel[3].setBounds(x,y,defLabelWidth,defHeight);
			//4 : 使用実績(ラベル値はサーブレットのConst_Setting.xmlより取得)
			x = this.itemLabel[2].getX() + defLabelWidth + defTextWidth + 5;
			y = this.itemLabel[2].getY();
			this.itemLabel[4] = new ItemLabel();
			this.itemLabel[4].setText(JwsConstManager.JWS_NM_SHIYO);
			this.itemLabel[4].setBounds(x,y,defLabelWidth,defHeight);
			//5 : 全件
			x = this.itemLabel[4].getX();
			y = this.itemLabel[4].getY() + defHeight + 5;
			this.itemLabel[5] = new ItemLabel();
			this.itemLabel[5].setText("全件");
			this.itemLabel[5].setBounds(x,y,defLabelWidth,defHeight);
			
			//項目ラベルをパネルに追加
			for ( i=0; i<this.itemLabel.length; i++ ) {
				this.add(this.itemLabel[i]);
			}
			
			///
			/// コードテキストボックス(半角)の設定
			///
			x = this.itemLabel[0].getX() + defLabelWidth;
			y = this.itemLabel[0].getY();
			this.codeTextbox = new HankakuTextbox();
			this.codeTextbox.setBounds(x,y,defTextWidth,defHeight);
			this.add(this.codeTextbox);
			
			///
			/// 名前テキストボックスの設定
			///
			x = this.itemLabel[1].getX() + defLabelWidth;
			y = this.itemLabel[1].getY();
			this.nameTextbox = new TextboxBase();
			this.nameTextbox.setBounds(x,y,defTextWidth,defHeight);
			this.add(this.nameTextbox);
			
			///
			/// 会社コンボボックスの設定
			///
			x = this.itemLabel[2].getX() + defLabelWidth;
			y = this.itemLabel[2].getY();
			this.kaishaComb = new ComboBase();
			this.kaishaComb.setBounds(x,y,defTextWidth,defHeight);
			this.add(this.kaishaComb);
			
			///
			/// 工場コンボボックスの設定
			///
			x = this.itemLabel[3].getX() + defLabelWidth;
			y = this.itemLabel[3].getY();
			this.kojoComb = new ComboBase();
			this.kojoComb.setBounds(x,y,defTextWidth,defHeight);
			this.add(this.kojoComb);

			///
			/// ラジオボタンの設定[0:3ヶ月, 1:全件]
			///
			this.radioButton = new JRadioButton[2];
			x = this.itemLabel[4].getX() + defLabelWidth;
			y = this.itemLabel[4].getY();
			width = defRadioWidth;
			height = defHeight;
			//3ヶ月
			this.radioButton[0] = new JRadioButton();
			this.radioButton[0].setBounds(x,y,width,height);
			this.radioButton[0].addActionListener(this.getActionEvent());
			this.radioButton[0].setActionCommand("sanKagetu");
			this.radioButton[0].setSelected(true);
			copyCheck.add(this.radioButton[0]);
			//全件
			x = this.itemLabel[5].getX() + defLabelWidth;
			y = this.itemLabel[5].getY();
			this.radioButton[1] = new JRadioButton();
			this.radioButton[1].setBounds(x,y,width,height);
			this.radioButton[1].addActionListener(this.getActionEvent());
			this.radioButton[1].setActionCommand("zenKen");
			//this.radioButton[1].setSelected(true);
			copyCheck.add(this.radioButton[1]);
			//パネルに追加
			for ( i=0; i<this.radioButton.length; i++ ) {
				this.radioButton[i].setBackground(Color.WHITE);
				this.add(this.radioButton[i]);
			}
//mod end --------------------------------------------------------------------------------------

			///
			/// 検索ボタンの設定
			///
			width = 80;
			height = 38;
//2010.09.09 sakai change --->
			//x = this.kaishaComb.getX() + defTextWidth + 5;
			//y = this.kaishaComb.getY();
			x = this.radioButton[0].getX() + defRadioWidth + 5;
			y = this.radioButton[0].getY();
//2010.09.09 sakai change <---
			this.searchBtn = new ButtonBase();
			this.searchBtn.setFont(new Font("Default", Font.PLAIN, 11));
			this.searchBtn.setBounds(x, y, width, height+2);
			this.searchBtn.setText("検索");
			this.add(this.searchBtn);
			
			///
			/// 終了ボタンの設定
			///
			x = x + width + 5;
			this.endBtn = new ButtonBase();
			this.endBtn.setFont(new Font("Default", Font.PLAIN, 11));
			this.endBtn.setBounds(x, y, width, height+2);
			this.endBtn.setText("終了");
			this.add(this.endBtn);
	
			///
			/// テーブル配置
			///
			this.addTable();
			
			///
			/// データ数表示
			///
			this.dataLabel = new ItemLabel();
			this.dataLabel.setBackground(Color.WHITE);
			this.dataLabel.setFont(new Font("Default", Font.PLAIN, 13));
			this.dataLabel.setText("");
			this.dataLabel.setHorizontalAlignment(JLabel.CENTER);
			this.dataLabel.setBounds(0, 380, 880, 20);
			this.add(this.dataLabel);
			
			///
			/// リンクボタンの設定
			///
			y  = 390;
			this.linkBtn = new LinkButton[12];
			this.linkBtn[0] = new LinkButton("最初へ");
			this.linkBtn[1] = new LinkButton("最後へ");
			for ( i=2; i<this.linkBtn.length; i++ ) {
				String strText = "" + (i-1);
//				this.linkBtn[i] = new LinkButton(strText,120 + ((i-2)*35),y);
				this.linkBtn[i] = new LinkButton(strText,dispWidth/2 - 175 + ((i-2)*35),y);
// ADD start 20120706 hisahori
				this.linkBtn[i].setBounds(this.linkBtn[i].getLocation().x, this.linkBtn[i].getLocation().y, 60, this.linkBtn[i].getHeight());
// ADD end 20120706 hisahori
			}
			this.linkBtn[0].setBounds(150,y,80,this.linkBtn[2].getHeight());
			this.linkBtn[1].setBounds(685,y,80,this.linkBtn[2].getHeight());		
			//リンクボタンをパネルに追加
			for ( i=0; i<this.linkBtn.length; i++ ) {
				this.add(this.linkBtn[i]);
			}
			
			///
			/// 「次へ」・「前へ」リンクボタンの設定
			///
			//リンクボタンにテキストと表示座標を設定
			this.linkPrevBtn = new LinkButton("<<", this.linkBtn[2].getX() - this.linkBtn[2].getWidth(), y);
			this.linkNextBtn = new LinkButton(">>", this.linkBtn[11].getX() + this.linkBtn[11].getWidth(), y);
			this.linkPrevBtn.setEnabled(false);
			this.linkNextBtn.setEnabled(false);
			//リンクボタンをパネルに追加
			this.add(this.linkPrevBtn);
			this.add(this.linkNextBtn);
			
				
			//②:各コンポーネントのイベントを設定する
			
			//原料テーブルにキーイベントを設定する
			materialTable.getMainTable().addKeyListener(getKeyEvent());
	
			//検索ボタンにイベントを設定
			this.searchBtn.addActionListener(this.getActionEvent());
			this.searchBtn.setActionCommand(this.SEARCH_BTN_CLICK);
			//会社コンボボックスにイベントを設定
			this.kaishaComb.addActionListener(this.getActionEvent());
			this.kaishaComb.setActionCommand(this.KAISHA_COMB_SELECT);
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			//工場コンボボックスにイベントを設定
			this.kojoComb.addActionListener(this.getActionEvent());
			this.kojoComb.setActionCommand(this.KOUJYOU_COMB_SELECT);
			
//add end --------------------------------------------------------------------------------------

			//リンクボタンにイベントを設定
			for ( i=0; i<this.linkBtn.length; i++ ) {
				this.linkBtn[i].addActionListener(this.getActionEvent());
				this.linkBtn[i].setActionCommand(this.LINK_BTN_CLICK);
			}
			//「次へ」・「前へ」リンクボタンをイベントを設定
			this.linkPrevBtn.addActionListener(this.getActionEvent());
			this.linkPrevBtn.setActionCommand(this.LINK_PREV_BTN_CLICK);
			this.linkNextBtn.addActionListener(this.getActionEvent());
			this.linkNextBtn.setActionCommand(this.LINK_NEXT_BTN_CLICK);

		} catch(ExceptionBase eb){
			throw eb;
			
		}catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルのコントロール配置が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}
	
	/**
	 * 原料一覧テーブル配置処理
	 * @throws ExceptionBase 
	 */
	private void addTable() throws ExceptionBase {
		
		try {
			//テーブルのインスタンス生成
			this.materialTable = new MaterialTable();
			
			//テーブル用Scrollパネルの設定
			this.materialTable.getScroll().setBounds(5, 80, 880, 290);
			this.add(this.materialTable.getScroll());

		} catch(ExceptionBase eb){
			throw eb;
			
		}catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルのテーブル配置処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * 初期化処理
	 */
	private void init() throws ExceptionBase {
		//①:【JW620】を実行し、部署コンボボックスコントロールに設定する
		try {
			//会社コンボボックスの初期設定
			this.isSelectKaishaCmb = false;
			this.conJW610();
			this.setKaishaCmb();
			//部署コンボボックスの初期設定
			this.isSelectKaishaCmb = true;
			this.selectKaishaComb();
	
			//リンクボタンの初期設定
			for(int i=0; i<this.linkBtn.length; i++) {
				this.linkBtn[i].setEnabled(false);
			}
			//「次へ」・「前へ」リンクボタンの初期設定
			this.linkNextBtn.setEnabled(false);
			this.linkPrevBtn.setEnabled(false);

		}catch(ExceptionBase eb){
			throw eb;
			
		}catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの初期化処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * 画面クリア処理
	 */
	public void clearDisp(boolean kensakuFg) throws ExceptionBase{

		try{
			//検索失敗時
			if(kensakuFg){
				//テーブル初期化
				materialTable.clearTable();
				
				//リンクボタンの初期設定
				for(int i=0; i<this.linkBtn.length; i++) {
					this.linkBtn[i].setEnabled(false);
				}
				//「次へ」・「前へ」リンクボタンの初期設定
				this.linkNextBtn.setEnabled(false);
				this.linkPrevBtn.setEnabled(false);
				
				//データ数表示初期化
				this.dataLabel.setText("");
				
			//画面終了時
			}else{
				//テーブル初期化
				materialTable.clearTable();

				//リンクボタンの初期設定
				for(int i=0; i<this.linkBtn.length; i++) {
					this.linkBtn[i].setEnabled(false);
				}
				//「次へ」・「前へ」リンクボタンの初期設定
				this.linkNextBtn.setEnabled(false);
				this.linkPrevBtn.setEnabled(false);
				
				//テキストボックスの初期化
				this.codeTextbox.setText("");
				this.nameTextbox.setText("");
				this.kaishaComb.setSelectedIndex(0);
				this.kojoComb.setSelectedIndex(0);
				
				//データ数表示初期化
				this.dataLabel.setText("");
				
				
			}
			
			
			
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの画面クリア処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
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
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.5
//				{ this.codeTextbox, this.kaishaComb },		//1:コード, 2:会社
//				{ this.nameTextbox, this.kojoComb },			//3;名前, 4:工場
				{ this.nameTextbox, this.codeTextbox },		//名前, コード
				{ this.kaishaComb, this.kojoComb },			//会社, 工場
				this.radioButton,								//使用実績・全件ラジオボタン
//mod end --------------------------------------------------------------------------------------
				{ this.searchBtn, this.endBtn },				//5:検索, 6:終了
				{ this.materialTable.getMainTable() },			//7:原料リスト
				{ this.linkPrevBtn },								//9:前へリンクボタン 
				this.linkBtn,											//8～:リンクボタン
				{ this.linkNextBtn }								//10:次へリンクボタン
		};
		return comp;
	}
	
	/**
	 * 終了ボタン押下時イベント設定
	 * @param actionListener
	 * @throws ExceptionBase 
	 */
	public void setEndEvent(ActionListener actionListener, String actionCommand) throws ExceptionBase {
		try {
			//終了ボタンにイベントを設定
			this.endBtn.addActionListener(actionListener);
			this.endBtn.setActionCommand(actionCommand);
			
		}catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの終了ボタンイベントが失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
	}
	
	/**
	 * 終了ボタン押下時処理
	 * @throws ExceptionBase 
	 * @throws ExceptionBase 
	 */
	public void clickEndBtn() throws ExceptionBase {
		try {
			//画面クリア処理
			clearDisp(false);
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの終了ボタン押下時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}
	
	
	/**
	 * 検索ボタン押下時処理
	 * @throws ExceptionBase 
	 */
	public void clickSearchBtn(String strPageNum, boolean isSearch) throws ExceptionBase {
		try {
	
			//検索処理を行う
			this.conJW630(strPageNum);
			
			//検索結果が空の場合、テーブルをクリア
			if ( DataCtrl.getInstance().getMaterialMstData().getAryMateData().size() == 0
					|| ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(0)).getIntKaishacd() == 0 ) {
				clearDisp(true);		//画面クリア処理
				return;
			}

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.6
			//全工場チェック
//			boolean isZenkojo = this.kojoComb.getSelectedItem().equals("全工場");
			boolean isZenkojo = this.getSelectKojoCd().equals(KOJO_ZENKOJO);
//mod end --------------------------------------------------------------------------------------
			
			//テーブル初期化
			if ( isZenkojo ) {
				//キューピー且つ全工場の場合
				this.materialTable.initTableZenkojo();
				
				//レンダラ生成(通常)
				MiddleCellRenderer md = new MiddleCellRenderer();
				//レンダラ生成（数値）
				MiddleCellRenderer mdn = new MiddleCellRenderer();
	
				//テーブルに項目を追加
				for ( int i=0; i<DataCtrl.getInstance().getMaterialMstData().getAryMateData().size(); i++ ) {
					materialTable.insertMainTableZenkojo(((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(i)),md,mdn);	
				}
				
				//レンダラ設定
				//テーブルカラム取得
				DefaultTableColumnModel columnModel = (DefaultTableColumnModel)materialTable.getMainTable().getColumnModel();
				TableColumn column = null;
				for(int i = 0; i<materialTable.getMainTable().getColumnCount(); i++){
					//テーブルカラムへ設定
					column = materialTable.getMainTable().getColumnModel().getColumn(i);
					
					//単価or歩留列の場合は右寄せ
					if(2 < i && i < materialTable.getMainTable().getColumnCount()-3){
						column.setCellRenderer(mdn);
						
					//単価or歩留列以外は通常のもの
					}else{
						column.setCellRenderer(md);
					}
				}
				
			} else {
				//全工場ではない場合
				this.materialTable.initTableNotZenkojo();
				
				//レンダラ生成(通常)
				MiddleCellRenderer md = new MiddleCellRenderer();
				//レンダラ生成（数値）
				MiddleCellRenderer mdn = new MiddleCellRenderer();
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
				//レンダラ生成(通常)（中央揃え）
				MiddleCellRenderer mdc = new MiddleCellRenderer();
//add end --------------------------------------------------------------------------------------
				
				//テーブルに項目を追加
				for ( int i=0; i<DataCtrl.getInstance().getMaterialMstData().getAryMateData().size(); i++ ) {
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
					//materialTable.insertMainTableNotZenkojo(i, ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(i)),md,mdn);	
					materialTable.insertMainTableNotZenkojo(i, ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(i)),md,mdn,mdc);	
//mod start --------------------------------------------------------------------------------------
				}
				
				//レンダラ設定
				//テーブルカラム取得
				DefaultTableColumnModel columnModel = (DefaultTableColumnModel)materialTable.getMainTable().getColumnModel();
				TableColumn column = null;
				for(int i = 0; i<materialTable.getMainTable().getColumnCount(); i++){
					//テーブルカラムへ設定
					column = materialTable.getMainTable().getColumnModel().getColumn(i);
					//単価or歩留列の場合は右寄せ
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
					//if(i >= 4 && i <= 9){
					if((i == 3) || (i >= 6 && i <= 10)){
//mod start --------------------------------------------------------------------------------------
						column.setCellRenderer(mdn);
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
					//三ヶ月は中央揃えにする
					}else if(i == 4 || i == 5){
						
						column.setCellRenderer(mdc);
//mod start --------------------------------------------------------------------------------------
					//単価or歩留列以外は通常のもの
					}else{
						column.setCellRenderer(md);
					}
				}
			}
			
			//リスト表示件数及び最大件数を取得
			int intListRowMax = ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(0)).getIntListRowMax();
			int intMaxRow = ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(0)).getIntMaxRow();
			
			//最大値が0でない場合
			if ( intMaxRow != 0 ) {
				if ( (intMaxRow%intListRowMax != 0) ) {
					intLinkMaxNum = (intMaxRow/intListRowMax)+1;
				} else {
					intLinkMaxNum = (intMaxRow/intListRowMax);
				}
			}
			
			//データ数の表示
			//System.out.println(strPageNum);
			this.dataLabel.setText("<html>データ数　：　" + intMaxRow + " 件です(" + intListRowMax + "件毎に表示しています)　　　<b>"+Integer.parseInt(strPageNum)+"／"+intLinkMaxNum+" ページ<b></html>");
			
			if ( isSearch ) {
				//リンクボタンの初期設定
				for(int i=0; i<this.linkBtn.length; i++) {
					if ( i > 1 ) {
						this.linkBtn[i].setText("" + (i-1));
					}
					this.linkBtn[i].setEnabled(false);
				}
				this.linkPrevBtn.setEnabled(false);
				this.linkNextBtn.setEnabled(false);
				
				
				this.linkBtn[0].setEnabled(true);
				this.linkBtn[1].setEnabled(true);
				for( int i=0; i<intLinkMaxNum; i++ ) {
					if ( i < this.linkBtn.length-2 ) { 
						this.linkBtn[i+2].setEnabled(true);
					}
				}
				
				//検索結果の件数が表示最大件数より、上回っている場合
				//if ( this.intLinkMaxNum > intListRowMax ) {
				if ( intMaxRow > (intListRowMax*10) ) {
					//[次へ]・[前へ]リンクボタンを使用可に設定
					this.linkPrevBtn.setEnabled(true);
					this.linkNextBtn.setEnabled(true);
				}
			}

		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの検索ボタン押下時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}		
	}
	
	/**
	 * 会社コンボボックス選択時処理
	 * @throws ExceptionBase 
	 */
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
			ex.setStrErrmsg("原料一覧パネルの会社コンボボックス選択時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	/**
	 * 工場コンボボックス選択時処理
	 * @throws ExceptionBase 
	 */
	private void selectKojoComb() throws ExceptionBase {
		try {
			if ( this.kojoComb.isValid() ) {		//多重検索を防ぐ
				this.setShiyoFlg();
			}
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの工場コンボボックス選択時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}
//add end --------------------------------------------------------------------------------------

	/**
	 * リンクボタン押下時処理
	 * @throws ExceptionBase 
	 */
	private void clickLinkBtn(Object objSource) throws ExceptionBase {
		
		try {

			//リスト表示件数及び最大件数を取得
			int intListRowMax = ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(0)).getIntListRowMax();
			int intMaxRow = ((MaterialData)DataCtrl.getInstance().getMaterialMstData().getAryMateData().get(0)).getIntMaxRow();
			int intLinkCnt = intMaxRow / (intListRowMax*10);
			
			if ( objSource.equals(this.linkBtn[0]) ) {
				//最初へ
				this.clickSearchBtn("1", false);
				
				//最初まで戻る
				for ( int i=0; i<intLinkCnt; i++ ) {
					this.clickLinkPrevBtn(objSource);
					
				}
				
			} else if ( objSource.equals(this.linkBtn[1]) ) {
				//最後へ
				this.clickSearchBtn("" + intLinkMaxNum, false);
				
					//最後まで進む
				for ( int i=0; i<intLinkCnt; i++ ) {
					this.clickLinkNextBtn(objSource);
					
				}
				
			} else {
				//その他
				for ( int i=0; i<this.linkBtn.length; i++ ) {
					
					if ( objSource.equals(this.linkBtn[i+2]) ) {
						
						if ( this.linkBtn[i+2].isEnabled() ) {
							//対象リンク番号へ
							this.clickSearchBtn(this.linkBtn[i+2].getText(), false);
							break;
						}
						
					}
					
				}
				
			}
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルのリンクボタン押下時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
	}

	/**
	 * 「次へ」リンクボタン押下時処理
	 * @param source
	 * @throws ExceptionBase 
	 */
	private void clickLinkNextBtn(Object source) throws ExceptionBase {

		try {
			
			//リンク最大表示値を取得
			int chkValue = Integer.parseInt(this.linkBtn[11].getText());
			
			//リンク最大表示値が検索結果最大値を超えていないか
			if ( chkValue < this.intLinkMaxNum ) {

				//リンク最小表示値を取得
				int intValue = Integer.parseInt(this.linkBtn[2].getText());
				
				//リンク表示値を増やす
				for ( int i=0; i<10; i++ ) {
					
					//リンク表示値再設定
					this.linkBtn[i + 2].setText(Integer.toString(intValue + 10 + i));
					
					//リンク使用可否再設定
					if ( (intValue + 10 + i) <= intLinkMaxNum ) {
						this.linkBtn[i + 2].setEnabled(true);
						
					} else {
						this.linkBtn[i + 2].setEnabled(false);
						
					}
					
				}
				
			}
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの<<リンクボタン押下時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * 「前へ」リンクボタン押下時処理
	 * @param source
	 * @throws ExceptionBase 
	 */
	private void clickLinkPrevBtn(Object source) throws ExceptionBase {

		try {

			//リンク最小表示値
			int chkValue = Integer.parseInt(this.linkBtn[2].getText());
			
			//リンク最小表示値が初期値ではない場合
			if ( chkValue != 1 ) {				
				
				//リンク表示値を１０前に戻す
				for ( int i=0; i<10; i++ ) {
					
					//リンク表示値再設定
					this.linkBtn[i + 2].setText(Integer.toString(chkValue - 10 + i));
					
					//リンク使用可否再設定
					if ( (chkValue - 10 + i) <= intLinkMaxNum ) {
						this.linkBtn[i + 2].setEnabled(true);
						
					} else {
						this.linkBtn[i + 2].setEnabled(false);
						
					}
					
				}
				
			}
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの>>リンクボタン押下時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * 会社コンボボックス設定処理
	 * @throws ExceptionBase 
	 */
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
		}catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの会社コンボボックス設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
		
	}

	/**
	 * 部署コンボボックス設定処理
	 * @throws ExceptionBase 
	 */
	private void setKojoCmb() throws ExceptionBase {
		int i;
		try {
			
			String bushoNm = "";
			
			//コンボボックスの全項目の削除
			this.kojoComb.removeAllItems();
			
//			//権限により設定
//			ArrayList Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
//			for(int j=0; j<Auth.size(); j++){
//				String[] strAuth = (String[])Auth.get(j);
//				//画面IDチェック
//				if(strAuth[0].equals("150")){
//					//データIDチェック（全ての場合）
//					if(strAuth[2].equals("9")){
//						//空項目を追加
//						this.kojoComb.addItem("");
//					}
//				}
//			}

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.6
			//空項目を追加
//			this.kojoComb.addItem("");
			this.kojoComb.addItem(KOJO_ZENKOJO_TATE);

			//会社コンボボックスが「キユーピー」を選択している場合、全工場を項目に追加
			if ( this.kaishaComb.getSelectedItem().equals("キユーピー") ) {
//				this.kojoComb.addItem("全工場");
				this.kojoComb.addItem(KOJO_ZENKOJO_YOKO);
			}
//mod end --------------------------------------------------------------------------------------
			
			//表示順に沿ってコンボボックスに値を追加
			for ( i=0; i<BushoData.getArtBushoCd().size(); i++ ) {
				//会社名
				bushoNm = BushoData.getAryBushoNm().get(i).toString(); 
				
				//コンボボックスへ追加
				this.kojoComb.addItem(bushoNm);
			}
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの部署コンボボックス設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * 選択会社コードの取得
	 * @return 選択会社コード 
	 * @throws ExceptionBase 
	 */
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
			ex.setStrErrmsg("原料一覧パネルの選択会社コード取得処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
		
		return intRetKaishaCd;
	}

	/**
	 * 選択工場コードの取得
	 * @return 選択工場コード 
	 * @throws ExceptionBase 
	 */
	private String getSelectKojoCd() throws ExceptionBase {
		int i;
		String strRetKojoCd = "";
		String bushoCd = "";
		String bushoNm = "";
		try {
			//工場コンボボックスの選択値を取得
			String kojoCombItem = this.kojoComb.getSelectedItem().toString();
			

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.6
//			if ( !kojoCombItem.equals("全工場") ) {
			if ( !kojoCombItem.equals(KOJO_ZENKOJO_YOKO) ) {
//mod end --------------------------------------------------------------------------------------
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
			} else {
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.6
				//工場が全工場の場合
//				strRetKojoCd = "zenkojo";
				strRetKojoCd = KOJO_ZENKOJO;
//mod end --------------------------------------------------------------------------------------
			}
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの選択会社コード取得処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
		
		return strRetKojoCd;
	}
	
	/**
	 * 【JW610】 会社コンボボックス値取得 送信XMLデータ作成
	 */
	private void conJW610() throws ExceptionBase{
		try{
			
			//　送信パラメータ格納
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGamenId = "150";
			
			//　送信XMLデータ作成
			xmlJW610 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//　Root追加
			xmlJW610.AddXmlTag("","JW610");
			arySetTag.clear();
			
			//　機能ID追加
			xmlJW610.AddXmlTag("JW610", "USERINFO");
				//　テーブルタグ追加
				xmlJW610.AddXmlTag("USERINFO", "table");	
				//　レコード追加
				String[] kbn_shori = {"kbn_shori", "3"};
				String[] id_user = {"id_user",strUser};
				arySetTag.add(kbn_shori);
				arySetTag.add(id_user);
				xmlJW610.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();

			//　【部署検索】 機能ID追加
			xmlJW610.AddXmlTag("JW610", "SA140");

				//　テーブルタグ追加
				xmlJW610.AddXmlTag("SA140", "table");
				//　レコード追加
				id_user = new String[]{"id_user",strUser};
				String[] id_gamen = new String[]{"id_gamen", strGamenId};
				arySetTag.add(id_user);
				arySetTag.add(id_gamen);
				xmlJW610.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();
			
//			xmlJW610.dispXml();
			//　XML送信
			XmlConnection xmlConnection = new XmlConnection(xmlJW610);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//　XML受信
			xmlJW610 = xmlConnection.getXdocRes();
//			xmlJW610.dispXml();
			
			//　テストXMLデータ
			//xmlJW610 = new XmlData(new File("src/main/JW610.xml"));

			// 会社データ
			KaishaData.setKaishaData(xmlJW610);

			// Resultデータ
			DataCtrl.getInstance().getResultData().setResultData(xmlJW610);
			// Resultデータ.処理結果がtrueの場合、ExceptionBaseをThrowする
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				clearDisp(false);		//画面クリア処理
				throw new ExceptionBase();
			}
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの会社コンボボックス値取得通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
		
	/**
	 * 【JW620】 会社コンボボックス検索時 送信XMLデータ作成
	 * @param strKaishaCd : 会社コード
	 */
	private void conJW620(String strKaishaCd) throws ExceptionBase{
		try{
			
			//　送信パラメータ格納
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGamenId = "150";
			
			//　送信XMLデータ作成
			xmlJW620 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//　Root追加
			xmlJW620.AddXmlTag("","JW620");
			arySetTag.clear();
			
			//　機能ID追加
			xmlJW620.AddXmlTag("JW620", "USERINFO");
				//　テーブルタグ追加
				xmlJW620.AddXmlTag("USERINFO", "table");
				//　レコード追加
				String[] kbn_shori = {"kbn_shori", "3"};
				String[] id_user = {"id_user",strUser};
				arySetTag.add(kbn_shori);
				arySetTag.add(id_user);
				xmlJW620.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();

			//　【部署検索】 機能ID追加
			xmlJW620.AddXmlTag("JW620", "SA290");

				//　テーブルタグ追加
				xmlJW620.AddXmlTag("SA290", "table");
				//　レコード追加
				String[] cd_kaisha = new String[]{"cd_kaisha",strKaishaCd};
				id_user = new String[]{"id_user",strUser};
				String[] id_gamen = new String[]{"id_gamen", strGamenId};
				arySetTag.add(cd_kaisha);
				arySetTag.add(id_user);
				arySetTag.add(id_gamen);
				xmlJW620.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();
			
//			xmlJW620.dispXml();
			//　XML送信
			XmlConnection xmlConnection = new XmlConnection(xmlJW620);
//			xmlConnection.setStrAddress("http://localhost:8080/Shisaquick_SRV/AjaxServlet");
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//　XML受信
			xmlJW620 = xmlConnection.getXdocRes();
//			xmlJW620.dispXml();
			
			//　テストXMLデータ
			//xmlJW620 = new XmlData(new File("src/main/JW620.xml"));

			//部署データ
			BushoData.setBushoData(xmlJW620);

			// Resultデータ
			DataCtrl.getInstance().getResultData().setResultData(xmlJW620);
			// Resultデータ.処理結果がtrueの場合、ExceptionBaseをThrowする
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
			}
			
		}catch(ExceptionBase e){
			throw e;
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの会社コンボボックス検索通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
	
	/**
	 * 【JW630】 原料一覧検索処理 送信XMLデータ作成
	 * @param strKaishaCd : 会社コード
	 */
	private void conJW630(String strSelectRow) throws ExceptionBase{

		try {
			//　送信パラメータ格納
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGenryoCd = this.codeTextbox.getText();
			String strGenryoNm = this.nameTextbox.getText();
			String strKaishaCd = this.getSelectKaishaCd();
			String strBushoCd = this.getSelectKojoCd();
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			// 使用実績ラジオボタンが選択フラグ設定
			String strShiyoFlg = "";
			//ラジオボタンが選択 且つ 部署コードが全工場、未選択ではない場合
			if (this.radioButton[0].isSelected() 
					&& !strBushoCd.equals(KOJO_ZENKOJO) 
					&& !strBushoCd.equals("") ) {
				strShiyoFlg = "1";		//選択されている
			} else {
				strShiyoFlg = "0";		//選択されていない
			}
//add end --------------------------------------------------------------------------------------
			
			//　送信XMLデータ作成
			xmlJW630 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//　Root追加
			xmlJW630.AddXmlTag("","JW630");
			arySetTag.clear();
			
			//　機能ID追加
			xmlJW630.AddXmlTag("JW630", "USERINFO");
				//　テーブルタグ追加
				xmlJW630.AddXmlTag("USERINFO", "table");	
				//　レコード追加
				String[] kbn_shori = {"kbn_shori", "3"};
				String[] id_user = {"id_user",strUser};
				arySetTag.add(kbn_shori);
				arySetTag.add(id_user);
				xmlJW630.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();

			//　【分析原料検索】 機能ID追加
			xmlJW630.AddXmlTag("JW630", "SA510");

				//　テーブルタグ追加
				xmlJW630.AddXmlTag("SA510", "table");
				//　レコード追加
				String[] taisho_genryo1 = new String[]{"taisho_genryo1","true"};
				String[] taisho_genryo2 = new String[]{"taisho_genryo2","true"};
				String[] cd_genryo = new String[]{"cd_genryo",strGenryoCd};
				String[] nm_genryo = new String[]{"nm_genryo",strGenryoNm};
				String[] cd_kaisha;
				if(strKaishaCd.equals("0")){
					cd_kaisha = new String[]{"cd_kaisha",""};
				}else{
					cd_kaisha = new String[]{"cd_kaisha",strKaishaCd};
				}
				String[] cd_busho = new String[]{"cd_busho",strBushoCd};
				String[] num_selectRow = new String[]{"num_selectRow",strSelectRow};
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
				String[] flg_shiyo = new String[]{"flg_shiyo", strShiyoFlg};
//add end --------------------------------------------------------------------------------------
				
				arySetTag.add(taisho_genryo1);
				arySetTag.add(taisho_genryo2);
				arySetTag.add(cd_genryo);
				arySetTag.add(nm_genryo);
				arySetTag.add(cd_kaisha);
				arySetTag.add(cd_busho);
				arySetTag.add(num_selectRow);
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
				arySetTag.add(flg_shiyo);
//add end --------------------------------------------------------------------------------------
				
				xmlJW630.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();
	
			//　XML送信
			XmlConnection xmlConnection = new XmlConnection(xmlJW630);
			
			//xmlJW630.dispXml();
			
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//　XML受信
			xmlJW630 = xmlConnection.getXdocRes();

			//xmlJW630.dispXml();

			//　テストXMLデータ
			//xmlJW630 = new XmlData(new File("src/main/JW630.xml"));

//			//原料データ
//			DataCtrl.getInstance().getMaterialMstData().setMaterialData(xmlJW630,"SA510");
//			DataCtrl.getInstance().getMaterialMstData().DispMateData();

			// Resultデータ
			DataCtrl.getInstance().getResultData().setResultData(xmlJW630);
//			DataCtrl.getInstance().getResultData().dispData();
			
			// Resultデータ.処理結果がtrueの場合、ExceptionBaseをThrowする
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {	
				throw new ExceptionBase();
			} else {
				//原料データ
				DataCtrl.getInstance().getMaterialMstData().setMaterialData(xmlJW630,"SA510");
			}

		}catch(ExceptionBase e){
			throw e;
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料一覧パネルの原料一覧検索通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
	
	/**
	 * KeyAdapter取得メソッド
	 *  : キー押下時イベントを設定する
	 * @return KeyAdapterクラス
	 * @throws ExceptionBase 
	 */
	private KeyAdapter getKeyEvent() throws ExceptionBase {
		KeyAdapter keyAdapter = null;
		try {
			keyAdapter =  new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					try {
						//F2キー押下時
						if ( e.getKeyCode() == KeyEvent.VK_F1 ) {
							//選択されている行番号を取得
							int selectRow = materialTable.getMainTable().getSelectedRow();
							//原料テーブルより、対象行の原料コードを取得
							String genryoCd = "";
							String kaishaCd = "";
							String bushoCd = "";
							try {
								
								if ( selectRow >= 0 ) {
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
//									genryoCd = materialTable.getMainTable().getValueAt(selectRow, 1).toString();
									if (!getSelectKojoCd().equals(KOJO_ZENKOJO)) {
										//全工場ではない場合
										genryoCd = materialTable.getMainTable().getValueAt(selectRow, 0).toString();										
									} else {
										//全工場の場合
										genryoCd = materialTable.getMainTable().getValueAt(selectRow, 1).toString();										
									}
//mod end --------------------------------------------------------------------------------------
									kaishaCd = materialTable.getTableKaishaCd(selectRow);
									bushoCd = materialTable.getTableBushoCd(selectRow);
								}
							} catch(NullPointerException ne) {
								//リストの値がNULLの場合
								genryoCd = "";
							} finally {
							}
							//取得した原料コードをクリップボード格納クラスに設定
							DataCtrl.getInstance().getClipboardData().setStrClipboad(genryoCd + "\n" + kaishaCd + "\n" + bushoCd);
						}
					} catch (Exception ec) {
						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("原料一覧パネルのキー押下時イベント処理が失敗しました");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
					} finally {
					}
				}			
			};
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原料一覧パネルのキーイベント処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
		return keyAdapter;
	}
	
	
	/**
	 * ActionListener取得メソッド
	 *  : ボタン押下時及びコンボボックス選択時のイベントを設定する
	 * @return ActionListener 
	 * @throws ExceptionBase
	 */
	private ActionListener getActionEvent() throws ExceptionBase {
		ActionListener listener = null;
		try{
			listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String event_name = e.getActionCommand();
						if ( event_name.equals(SEARCH_BTN_CLICK)) {
							
							//検索ボタン押下時							
							//クリップボードの値をクリア
							DataCtrl.getInstance().getClipboardData().setStrClipboad("");
							//検索処理
							clickSearchBtn("1", true);
							
						} else if ( event_name.equals(KAISHA_COMB_SELECT)) {
							
							//会社コンボボックス選択時
							selectKaishaComb();
							
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
							radioButton[1].setSelected(true);
							radioButton[0].setEnabled(false);
//add end --------------------------------------------------------------------------------------
							
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
						} else if ( event_name.equals(KOUJYOU_COMB_SELECT)) {
							//工場コンボボックス選択時
							selectKojoComb();
//add end --------------------------------------------------------------------------------------
							
						} else if ( event_name.equals(LINK_BTN_CLICK) ) {
							
							//リンクボタン押下時
							clickLinkBtn( e.getSource() );
							
						} else if ( event_name.equals(LINK_PREV_BTN_CLICK) ) {
							
							//[前へ]リンクボタン押下時
							clickLinkPrevBtn( e.getSource() );
							
						} else if ( event_name.equals(LINK_NEXT_BTN_CLICK) ) {
							
							//[次へ]リンクボタン押下時
							clickLinkNextBtn( e.getSource() );
							
						}
					} catch (ExceptionBase eb) {
						
						DataCtrl.getInstance().PrintMessage(eb);
						
						//画面クリア処理
						try{
							clearDisp(true);
							
						}catch(ExceptionBase exb){
							
							DataCtrl.getInstance().PrintMessage(exb);
							
						}catch(Exception ec){
							//エラー設定
							ex = new ExceptionBase();
							ex.setStrErrCd("");
							ex.setStrErrmsg("原料一覧パネルの画面クリア処理が失敗しました");
							ex.setStrErrShori(this.getClass().getName());
							ex.setStrMsgNo("");
							ex.setStrSystemMsg(ec.getMessage());
							
							DataCtrl.getInstance().PrintMessage(eb);
							
						}finally{
							
						}
						
						
					} catch (Exception ec) {
						
						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("原料一覧パネルのアクションイベント処理が失敗しました");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
						
					} finally {
					}
				}

			};
			
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原料一覧パネルのアクションイベント処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
		
		return listener;
	}
	
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	/**
	 * 使用実績フラグ初期設定
	 * @throws ExceptionBase 
	 */
	public void setShiyoFlg() throws ExceptionBase {
		try {
			//空白かつ全工場_横の場合は、全件のみとする
			if ( this.kojoComb.getSelectedItem().equals(KOJO_ZENKOJO_TATE) 
					|| this.getSelectKojoCd().equals(KOJO_ZENKOJO)
					|| this.kojoComb.getSelectedItem().equals("新規登録原料")  ) {
				this.radioButton[1].setSelected(true);
				this.radioButton[0].setEnabled(false);
			}
			else {
				this.radioButton[0].setEnabled(true);
				this.radioButton[0].setSelected(true);
			}
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("使用実績フラグ初期設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}
//add end --------------------------------------------------------------------------------------

	
	/**
	 * 原料コードテキストボックスセッター＆ゲッター
	 */
	public HankakuTextbox getCodeTextbox() {
		return codeTextbox;
	}
	public void setCodeTextbox(HankakuTextbox codeTextbox) {
		this.codeTextbox = codeTextbox;
	}
	
	/**
	 * 会社選択コンボボックスセッター＆ゲッター
	 */
	public ComboBase getKaishaComb() {
		return kaishaComb;
	}
	public void setKaishaComb(ComboBase kaishaComb) {
		this.kaishaComb = kaishaComb;
	}
	
	/**
	 * 部署選択コンボボックスゲッター
	 */
	public ComboBase getKojoComb() {
		return kojoComb;
	}
	
	/**
	 * 部署データセッター＆ゲッター
	 */
	public BushoData getBushoData() {
		return BushoData;
	}
	public void setBushoData(BushoData bushoData) {
		BushoData = bushoData;
	}
	
	/**
	 * 会社データセッター＆ゲッター
	 */
	public KaishaData getKaishaData() {
		return KaishaData;
	}
	public void setKaishaData(KaishaData kaishaData) {
		KaishaData = kaishaData;
	}
	
}