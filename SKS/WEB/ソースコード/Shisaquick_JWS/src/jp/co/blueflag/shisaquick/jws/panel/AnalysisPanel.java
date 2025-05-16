package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.button.LinkButton;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.disp.AnalysisInputSubDisp;
import jp.co.blueflag.shisaquick.jws.label.*;
import jp.co.blueflag.shisaquick.jws.manager.*;
import jp.co.blueflag.shisaquick.jws.table.AnalysisCheckTable;
import jp.co.blueflag.shisaquick.jws.textbox.*;

/************************************************************************************
 * 
 * 【A05-04】 試作分析データ確認パネル操作用のクラス
 * 
 * @author TT.katayama
 * @since 2009/04/04
 * 
 ************************************************************************************/
public class AnalysisPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private KaishaData KaishaData = new KaishaData();
	private BushoData BushoData = new BushoData();
	
	private DispTitleLabel dispTitleLabel;						//画面タイトルラベル
	private HeaderLabel headerLabel;							//ヘッダ表示ラベル
	private LevelLabel levelLabel;									//レベル表示ラベル
	private ItemLabel[] itemLabel;									//項目ラベル
	private CheckboxBase[] genryoCheckbox;					//原料チェックボックス
	private HankakuTextbox genryoCdTextbox;				//原料コードテキストボックス（半角）
	private TextboxBase genryoNmTextbox;					//原料名称テキストボックス
	private ComboBase kaishaComb;								//会社コンボボックス
	private ComboBase bushoComb;								//部署コンボボックス
	private ButtonBase[] button;									//ボタン
	private AnalysisCheckTable analysisCheckTable;		//試作分析データ確認テーブル
	private LinkButton[] linkBtn;									//リンクボタン
	private ItemLabel dataLabel;									//データ数表示ラベル
	private LinkButton linkPrevBtn;								//前項目リンクボタン
	private LinkButton linkNextBtn;								//次項目リンクボタン
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	private JRadioButton[] shiyoFlgBtn;					//ラジオボタン
	private ButtonGroup copyCheck = new ButtonGroup();
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
	//ラジオボタン　ゲッタ＆セッタ
	public JRadioButton[] getShiyoFlgBtn() {
		return shiyoFlgBtn;
	}
	public void setShiyoFlgBtn(JRadioButton[] shiyoFlgBtn) {
		this.shiyoFlgBtn = shiyoFlgBtn;
	}
//add end --------------------------------------------------------------------------------------
	
	private AnalysisInputSubDisp analysisInputSubDisp;	//分析値入力画面
	
	private int intLinkMaxNum = 0;								//リンク最大数
	
	private XmlConnection xmlConnection;						//ＸＭＬ通信
	private MessageCtrl messageCtrl;							//メッセージ操作
	private ExceptionBase ex;										//エラー操作
	private XmlData xmlData;										//ＸＭＬデータ保持
	
	private XmlData xmlJW610;
	private XmlData xmlJW620;
	private XmlData xmlJW710;
	private XmlData xmlJW720;
	private XmlData xmlJW730;
	private XmlData xmlJW740;
	
	int selectMaxNum = 0;
	int selectPage = 1;

	int selRow=0;

	private boolean isSelectKaishaCmb = false;	//会社コンボボックス選択処理フラグ
	//会社コンボボックス選択時のイベント名
	private final String KAISHA_COMB_SELECT = "kaishaCombSelect";
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	//工場コンボボックス選択時のイベント名
	private final String BUSHO_COMB_SELECT = "bushoCombSelect";
//add end --------------------------------------------------------------------------------------
	//リンクボタン押下時のイベント名
	private final String LINK_BTN_CLICK = "linkBtnClick";
	//「前へ」リンクボタン押下時のイベント名
	private final String LINK_PREV_BTN_CLICK = "linkPrevBtnClick";
	//「次へ」リンクボタン押下時のイベント名
	private final String LINK_NEXT_BTN_CLICK = "linkNextBtnClick";
	
	private MaterialMstData bunsekiMaterialMst =new MaterialMstData();
	private MaterialMstData henkouMaterialMst =new MaterialMstData();
	
	//検索ボタン押下ﾌﾗｸﾞ
	private boolean selectFg = false;

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.6
	private final String KOJO_ZENKOJO_TATE = "全工場_縦";		//空白の場合の文言
//add end --------------------------------------------------------------------------------------
	
	//2011/05/26 QP@10181_No.5 TT T.Satoh Add Start -------------------------
	//イベント名取得用
	private String event_name = "";
	//2011/05/26 QP@10181_No.5 TT T.Satoh Add End ---------------------------
	
	/************************************************************************************
	 * 
	 * コンストラクタ
	 * @param strOutput : 画面タイトル
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public AnalysisPanel(String strOutput) throws ExceptionBase {
		//スーパークラスのコンストラクタを呼び出す
		super();

		try {
			//パネルの設定
			this.setPanel();
			
			//分析値入力画面クラスのインスタンス生成
			ArrayList aList = DataCtrl.getInstance().getUserMstData().getAryAuthData();

			for (int i = 0; i < aList.size(); i++) {		
				String[] items = (String[]) aList.get(i);	
				
				//試作分析データ確認画面の使用権限があるかチェックする	
				if (items[0].toString().equals("130") || items[0].toString().equals("140")) {	
					//分析値入力画面クラスのインスタンス生成
					this.analysisInputSubDisp = new AnalysisInputSubDisp("分析値入力画面");
					this.analysisInputSubDisp.getAnalysisInputPanel().getButton()[0].addActionListener(this.getActionEvent());
					this.analysisInputSubDisp.getAnalysisInputPanel().getButton()[0].setActionCommand("toroku");
					break;
				}	
			}		
			
			//コントロール配置
			this.addControl(strOutput);
			
		}catch(ExceptionBase eb){
			
			throw eb;
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作分析データ確認パネルのコンストラクタが失敗しました。");
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
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
//			int dispWidth = 900;
			int dispWidth = 990;
			int defRadioWidth = 30;
//add end --------------------------------------------------------------------------------------
			
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

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			///
			/// 項目ラベルの設定
			/// [0:新規原料, 1:既存原料, 2:原料コード, 3:原料名称, 4:会場, 5:工場]
			/// [6, 7 : データチェック等の説明]
			/// [8, 9 :使用実績フラグ, 全件ラベル]
			///
//			this.itemLabel = new ItemLabel[8];
			this.itemLabel = new ItemLabel[10];
//mod end --------------------------------------------------------------------------------------
			//0 : 新規原料
			x = 5;
			y = 30;
			this.itemLabel[0] = new ItemLabel();
			this.itemLabel[0].setText("新規原料");
			this.itemLabel[0].setBounds(x,y,defLabelWidth,defHeight);
			//1 : 既存原料
			x = this.itemLabel[0].getX() + defLabelWidth + 40;
			this.itemLabel[1] = new ItemLabel();
			this.itemLabel[1].setText("既存原料");
			this.itemLabel[1].setBounds(x,y,defLabelWidth,defHeight);
			
			//2 : 原料コード
			x = this.itemLabel[1].getX();
			y = this.itemLabel[1].getY() + defHeight + 10;
			this.itemLabel[2] = new ItemLabel();
			this.itemLabel[2].setText("原料コード");
			this.itemLabel[2].setBounds(x,y,defLabelWidth,defHeight);
			//3 : 原料名称
			x = this.itemLabel[2].getX();
			y = this.itemLabel[2].getY() + defHeight + 5;
			this.itemLabel[3] = new ItemLabel();
			this.itemLabel[3].setText("原料名称");
			this.itemLabel[3].setBounds(x,y,defLabelWidth,defHeight);

			//4 : ※データチェックと確認チェックは別に行って下さい
			width = 200;
			x = this.itemLabel[2].getX() + defLabelWidth + defTextWidth + 5;
			y = this.itemLabel[2].getY();
			this.itemLabel[4] = new ItemLabel();
//			this.itemLabel[4].setText("<html>※データチェックと確認チェックは<br>&nbsp;&nbsp;&nbsp;&nbsp;別に行って下さい</html>");
			this.itemLabel[4].setText("<html>※データチェックと確認チェックは<br>&nbsp;&nbsp;別に行って下さい</html>");
			this.itemLabel[4].setBackground(Color.WHITE);
			this.itemLabel[4].setBounds(x,y,width+56,defHeight + defHeight);
			//5 : ※データ変換すると確認項目は消えます
			x = this.itemLabel[4].getX();
			y = this.itemLabel[4].getY() + this.itemLabel[4].getHeight() + 5;
			this.itemLabel[5] = new ItemLabel();
			this.itemLabel[5].setText("※データ変換すると確認項目は消えます");
			this.itemLabel[5].setBackground(Color.WHITE);
			this.itemLabel[5].setBounds(x,y,width+56,defHeight);

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			//8 : 使用実績フラグ
			x = this.itemLabel[4].getX() + this.itemLabel[4].getWidth();
			y = this.itemLabel[4].getY();
			this.itemLabel[8] = new ItemLabel();
			this.itemLabel[8].setText(JwsConstManager.JWS_NM_SHIYO);
			this.itemLabel[8].setBounds(x,y,defLabelWidth,defHeight);
			//9 : 全件フラグ
			x = this.itemLabel[8].getX();
			y = this.itemLabel[8].getY() + this.itemLabel[8].getHeight() + 5;
			this.itemLabel[9] = new ItemLabel();
			this.itemLabel[9].setText("全件");
			this.itemLabel[9].setBounds(x,y,defLabelWidth,defHeight);
			
			//6 : 会社
//			x = this.itemLabel[4].getX() + this.itemLabel[4].getWidth();
//			y = this.itemLabel[4].getY();
			x = this.itemLabel[8].getX() + this.itemLabel[8].getWidth() + defRadioWidth;
			y = this.itemLabel[8].getY();
//mod end --------------------------------------------------------------------------------------
			this.itemLabel[6] = new ItemLabel();
			this.itemLabel[6].setText("会社");
			this.itemLabel[6].setBounds(x,y,defLabelWidth,defHeight);
			//7 : 工場
			x = this.itemLabel[6].getX();
			y = this.itemLabel[6].getY() + this.itemLabel[6].getHeight() + 5;
			this.itemLabel[7] = new ItemLabel();
			this.itemLabel[7].setText("工場");
			this.itemLabel[7].setBounds(x,y,defLabelWidth,defHeight);
			
			//項目ラベルをパネルに追加
			for ( i=0; i<this.itemLabel.length; i++ ) {
				this.add(this.itemLabel[i]);
			}
			
			///
			/// 原料チェックボックス
			/// [0:新規原料, 1:既存原料]
			///
			this.genryoCheckbox = new CheckboxBase[2];
			//0:新規原料
			x = this.itemLabel[0].getX();
			y = this.itemLabel[0].getY();
			width = this.itemLabel[0].getWidth();
			this.genryoCheckbox[0] = new CheckboxBase();
			this.genryoCheckbox[0].setBounds(x+width, y, 20,defHeight);
			//1:既存原料
			x = this.itemLabel[1].getX();
			y = this.itemLabel[1].getY();
			width = this.itemLabel[1].getWidth();
			this.genryoCheckbox[1] = new CheckboxBase();
			this.genryoCheckbox[1].setBounds(x+width, y, 20,defHeight);
			//原料チェックボックスをパネルに追加
			for ( i=0; i<this.genryoCheckbox.length; i++ ) {
				this.genryoCheckbox[i].setBackground(Color.white);
				this.add(this.genryoCheckbox[i]);
			}
			
			///
			///原料コードテキストボックス（半角）
			///
			x = this.itemLabel[2].getX();
			y = this.itemLabel[2].getY();
			width = this.itemLabel[2].getWidth();
			this.genryoCdTextbox = new HankakuTextbox();
			this.genryoCdTextbox.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.genryoCdTextbox);
			
			///
			///原料名称テキストボックス
			///
			x = this.itemLabel[3].getX();
			y = this.itemLabel[3].getY();
			width = this.itemLabel[3].getWidth();
			this.genryoNmTextbox = new TextboxBase();
			this.genryoNmTextbox.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.genryoNmTextbox);

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			///
			/// ラジオボタンの設定[0:使用実績, 1:全件]
			/// 
			this.shiyoFlgBtn = new JRadioButton[2];
			x = this.itemLabel[8].getX() + defLabelWidth;
			y = this.itemLabel[8].getY();
			width = defRadioWidth;
			height = defHeight;
			//使用実績
			this.shiyoFlgBtn[0] = new JRadioButton();
			this.shiyoFlgBtn[0].setBounds(x,y,width,height);
			this.shiyoFlgBtn[0].setSelected(true);
			this.shiyoFlgBtn[0].addActionListener(this.getActionEvent());
			this.shiyoFlgBtn[0].setActionCommand("sanKagetu");
			copyCheck.add(this.shiyoFlgBtn[0]);
			//全件
			x = this.itemLabel[9].getX() + defLabelWidth;
			y = this.itemLabel[9].getY();
			this.shiyoFlgBtn[1] = new JRadioButton();
			this.shiyoFlgBtn[1].setBounds(x,y,width,height);
			this.shiyoFlgBtn[1].addActionListener(this.getActionEvent());
			this.shiyoFlgBtn[1].setActionCommand("zenKen");
			copyCheck.add(this.shiyoFlgBtn[1]);
			//パネルに追加
			for ( i=0; i<this.shiyoFlgBtn.length; i++ ) {
				this.shiyoFlgBtn[i].setBackground(Color.WHITE);
				this.add(this.shiyoFlgBtn[i]);
			}
			//使用実績フラグ・未使用フラグの初期設定
			this.shiyoFlgBtn[0].setEnabled(true);
			this.shiyoFlgBtn[1].setEnabled(true);
//mod end --------------------------------------------------------------------------------------
			
			///
			///会社コンボボックス
			///
			x = this.itemLabel[6].getX();
			y = this.itemLabel[6].getY();
			width = this.itemLabel[6].getWidth();
			this.kaishaComb = new ComboBase();
			this.kaishaComb.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.kaishaComb);

			///
			///部署コンボボックス
			///
			x = this.itemLabel[7].getX();
			y = this.itemLabel[7].getY();
			width = this.itemLabel[7].getWidth();
			this.bushoComb = new ComboBase();
			this.bushoComb.setBounds(x+width, y, defTextWidth,defHeight);
			this.add(this.bushoComb);
			
			///
			/// ボタン
			/// [0:検索, 1:詳細, 2:新規原料, 3:削除, 4:終了]
			///
			this.button = new ButtonBase[5];		//TODO 6 -> 5
			//0:検索
			x = 5;
			width = 80;
			height = 38;
			y = this.itemLabel[1].getY() + defHeight + 10;
			this.button[0] = new ButtonBase();
			this.button[0].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[0].setBounds(x, y, width, height);
			this.button[0].setText("検索");
			this.button[0].addActionListener(this.getActionEvent());
			this.button[0].setActionCommand("kensaku");
			
			//1:詳細
			y = 450;
			this.button[1] = new ButtonBase();
			this.button[1].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[1].setBounds(x, y, width, height);
			this.button[1].setText("詳細");
			this.button[1].addActionListener(this.getActionEvent());
			this.button[1].setActionCommand("shosai_btn_click");
			//権限チェック
			ArrayList Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
			boolean shosaiChk = false;
			for(int j=0;j<Auth.size();j++){
				String[] strDispAuth = (String[])Auth.get(j);
				if(strDispAuth[0].equals("140")){
					//閲覧権限の場合
					if(strDispAuth[1].equals("10")){
						shosaiChk = true;
					}
					//編集権限の場合
					else if(strDispAuth[1].equals("20")){
						shosaiChk = true;
					}
					//編集（全て）権限の場合
					else if(strDispAuth[1].equals("40")){
						shosaiChk = true;
					}
				}
			}
			if(!shosaiChk){
				this.button[1].setEnabled(false);
			}
			
			
			//2:新規原料
			x += width;
			this.button[2] = new ButtonBase();
			this.button[2].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[2].setBounds(x, y, width, height);
			this.button[2].setText("新規原料");
			this.button[2].addActionListener(this.getActionEvent());
			this.button[2].setActionCommand("shinkigenryo_btn_click");
			//権限チェック
			Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
			boolean sinkiChk = false;
			for(int j=0;j<Auth.size();j++){
				String[] strDispAuth = (String[])Auth.get(j);
				if(strDispAuth[0].equals("130")){
					//閲覧権限の場合
					if(strDispAuth[1].equals("20")){
						sinkiChk = true;
					}
				}
			}
			if(!sinkiChk){
				this.button[2].setEnabled(false);
			}
			
			
			//3:削除
			x += width;
			this.button[3] = new ButtonBase();
			this.button[3].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[3].setBounds(x, y, width, height);
			this.button[3].setText("削除");
			this.button[3].addActionListener(this.getActionEvent());
			this.button[3].setActionCommand("sakuzyo");
			//権限チェック
			Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
			boolean delChk = false;
			for(int j=0;j<Auth.size();j++){
				String[] strDispAuth = (String[])Auth.get(j);
				if(strDispAuth[0].equals("160")){
					//編集権限の場合
					if(strDispAuth[1].equals("20")){
						delChk = true;
					}
				}
			}
			if(!delChk){
				this.button[3].setEnabled(false);
			}
			
			//TODO 4:Excel出力
//			width = 100;
//			x = dispWidth - 100 - width;
//			this.button[4] = new ButtonBase();
//			this.button[4].setFont(new Font("Default", Font.PLAIN, 11));
//			this.button[4].setBounds(x, y, width, height);
//			this.button[4].setText("栄養計算書");
//			this.button[4].addActionListener(this.getActionEvent());
//			this.button[4].setActionCommand("outputExcel");
			//5:終了
			width = 80;
			x = dispWidth - 100;
			this.button[4] = new ButtonBase();
			this.button[4].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[4].setBounds(x, y, width, height);
			this.button[4].setText("終了");
			this.button[4].addActionListener(this.getActionEvent());
			this.button[4].setActionCommand("shuryo");
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
			this.dataLabel.setBounds(0, 400, 880, 20);
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
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
//			this.linkBtn[11].setBounds(685,this.linkBtn[9].getY(),80,this.linkBtn[9].getHeight());
			this.linkBtn[11].setBounds(this.linkBtn[9].getX() + this.linkBtn[9].getWidth() + this.linkBtn[9].getWidth()
					,this.linkBtn[9].getY(),80,this.linkBtn[9].getHeight());
//mod end --------------------------------------------------------------------------------------
			
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
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			//工場コンボボックスにイベントを設定
			this.bushoComb.addActionListener(this.getActionEvent());
			this.bushoComb.setActionCommand(this.BUSHO_COMB_SELECT);
//add end --------------------------------------------------------------------------------------
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
			this.ex.setStrErrmsg("試作分析データ確認パネルのコントロール配置処理が失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.5
	/**
	 * フォーカス順設定用コンポーネント郡　ゲッター
	 * @return フォーカス順設定用コンポーネント郡 
	 */
	public JComponent[][] getSetFocusComponent() {
		JComponent[][] comp = new JComponent[][] {
				{ this.genryoNmTextbox, this.genryoCdTextbox },	//名前, コード
				this.shiyoFlgBtn,									//使用実績・全件ラジオボタン
				{ this.kaishaComb, this.bushoComb },				//会社コンボ, 工場コンボ
				this.genryoCheckbox,								//チェックボックス
				this.button,										//ボタン
				{ this.linkPrevBtn },								//前へリンクボタン 
				this.linkBtn,										//リンクボタン
				{ this.linkNextBtn }								//次へリンクボタン
		};
		return comp;
	}
//mod end --------------------------------------------------------------------------------------
	
	/************************************************************************************
	 * 
	 * 試作表分析テーブル初期化処理
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void addTable() throws ExceptionBase {
		try{
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			int intRow = 0;			//行数
//			int intCol = 21;				//列数
//			int intCol = 22;		//列数
			int intCol = 23;		//列数
			Object[] columnNm = new Object[intCol];		//列名
			int[] columnWidth = new int[intCol];				//列幅

			//列名の設定
/*			columnNm[0]  = "";
			columnNm[1]  = "原料CD";
			columnNm[2]  = "原料名";
			columnNm[3]  = "酢酸(%)";
			columnNm[4]  = "食塩(%)";
			columnNm[5]  = "総酸(%)";
			columnNm[6]  = "油含(%)";
			columnNm[7]  = "表示案";
			columnNm[8]  = "添加物";
			columnNm[9]  = "メモ";
			columnNm[10] = "<html>栄養計算<br>食品番号1</html>";
			columnNm[11] = "割合1(%)";
			columnNm[12] = "<html>栄養計算<br>食品番号2</html>";
			columnNm[13] = "割合2(%)";
			columnNm[14] = "<html>栄養計算<br>食品番号3</html>";
			columnNm[15] = "割合3(%)";
			columnNm[16] = "<html>栄養計算<br>食品番号4</html>";
			columnNm[17] = "割合4(%)";
			columnNm[18] = "<html>栄養計算<br>食品番号5</html>";
			columnNm[19] = "割合5(%)";
			columnNm[20] = "最終使用日";*/
			columnNm[0]  = "原料CD";
			columnNm[1]  = "原料名";
			
			// 使用実績フラグ(2文字表示)
			String strShiyoFlg = JwsConstManager.JWS_NM_SHIYO;
			strShiyoFlg = "<html>" + strShiyoFlg.substring(0,1) + "<br>" + strShiyoFlg.substring(1,2) + "</html>";
			columnNm[2]  = strShiyoFlg;					//使用実績		追加
			columnNm[3]  = "<html>未<br>使</html>";		//未使用		追加
			
			columnNm[4]  = "酢酸(%)";
			columnNm[5]  = "食塩(%)";
			columnNm[6]  = "総酸(%)";
			//【QP@20505_No11】2012/10/19 TT H.SHIMA MOD Start
//			columnNm[7]  = "油含(%)";
//			columnNm[8]  = "表示案";
//			columnNm[9]  = "添加物";
//			columnNm[10]  = "メモ";
//			columnNm[11] = "<html>栄養計算<br>食品番号1</html>";
//			columnNm[12] = "割合1(%)";
//			columnNm[13] = "<html>栄養計算<br>食品番号2</html>";
//			columnNm[14] = "割合2(%)";
//			columnNm[15] = "<html>栄養計算<br>食品番号3</html>";
//			columnNm[16] = "割合3(%)";
//			columnNm[17] = "<html>栄養計算<br>食品番号4</html>";
//			columnNm[18] = "割合4(%)";
//			columnNm[19] = "<html>栄養計算<br>食品番号5</html>";
//			columnNm[20] = "割合5(%)";
//			columnNm[21] = "最終使用日";
			columnNm[7]  = "MSG(%)";
			columnNm[8]  = "油含(%)";
			columnNm[9]  = "表示案";
			columnNm[10]  = "添加物";
			columnNm[11]  = "メモ";
			columnNm[12] = "<html>栄養計算<br>食品番号1</html>";
			columnNm[13] = "割合1(%)";
			columnNm[14] = "<html>栄養計算<br>食品番号2</html>";
			columnNm[15] = "割合2(%)";
			columnNm[16] = "<html>栄養計算<br>食品番号3</html>";
			columnNm[17] = "割合3(%)";
			columnNm[18] = "<html>栄養計算<br>食品番号4</html>";
			columnNm[19] = "割合4(%)";
			columnNm[20] = "<html>栄養計算<br>食品番号5</html>";
			columnNm[21] = "割合5(%)";
			columnNm[22] = "最終使用日";
			//【QP@20505_No11】2012/10/19 TT H.SHIMA MOD End

			//列幅の設定
/*			columnWidth[0]  = 30;		//連番
			columnWidth[1]  = 100;	//原料CD
			columnWidth[2]  = 250;	//原料名
			columnWidth[3]  = 60;		//酢酸(%)
			columnWidth[4]  = 60;		//食塩(%)
			columnWidth[5]  = 60;		//総酸(%)
			columnWidth[6]  = 60;		//油含(%)
			columnWidth[7]  = 200;	//表示案
			columnWidth[8]  = 200;	//添加物
			columnWidth[9]  = 200;	//メモ
			columnWidth[10] = 65;	//栄養計算食品番号1
			columnWidth[11] = 65;	//割合1(%)
			columnWidth[12] = 65;	//栄養計算食品番号2
			columnWidth[13] = 65;	//割合2(%)
			columnWidth[14] = 65;	//栄養計算食品番号3
			columnWidth[15] = 65;	//割合3(%)
			columnWidth[16] = 65;	//栄養計算食品番号4
			columnWidth[17] = 65;	//割合4(%)
			columnWidth[18] = 65;	//栄養計算食品番号5
			columnWidth[19] = 65;	//割合5(%)
			columnWidth[20] = 120;	//最終購入日*/
			columnWidth[0] = 100;	//原料CD
			columnWidth[1] = 250;	//原料名
			columnWidth[2] = 20;		//使用実績
			columnWidth[3] = 20;		//未使用
			columnWidth[4] = 60;		//酢酸(%)
			columnWidth[5] = 60;		//食塩(%)
			columnWidth[6] = 60;		//総酸(%)
			//【QP@20505_No11】2012/10/19 TT H.SHIMA MOD Start
//			columnWidth[7] = 60;		//油含(%)
//			columnWidth[8] = 200;	//表示案
//			columnWidth[9] = 200;	//添加物
//			columnWidth[10] = 200;	//メモ
//			columnWidth[11] = 65;		//栄養計算食品番号1
//			columnWidth[12] = 65;		//割合1(%)
//			columnWidth[13] = 65;		//栄養計算食品番号2
//			columnWidth[14] = 65;		//割合2(%)
//			columnWidth[15] = 65;		//栄養計算食品番号3
//			columnWidth[16] = 65;		//割合3(%)
//			columnWidth[17] = 65;		//栄養計算食品番号4
//			columnWidth[18] = 65;		//割合4(%)
//			columnWidth[19] = 65;		//栄養計算食品番号5
//			columnWidth[20] = 65;		//割合5(%)
//			columnWidth[21] = 120;	//最終購入日
			columnWidth[7] = 60;		//MSG
			columnWidth[8] = 60;		//油含(%)
			columnWidth[9] = 200;	//表示案
			columnWidth[10] = 200;	//添加物
			columnWidth[11] = 200;	//メモ
			columnWidth[12] = 65;		//栄養計算食品番号1
			columnWidth[13] = 65;		//割合1(%)
			columnWidth[14] = 65;		//栄養計算食品番号2
			columnWidth[15] = 65;		//割合2(%)
			columnWidth[16] = 65;		//栄養計算食品番号3
			columnWidth[17] = 65;		//割合3(%)
			columnWidth[18] = 65;		//栄養計算食品番号4
			columnWidth[19] = 65;		//割合4(%)
			columnWidth[20] = 65;		//栄養計算食品番号5
			columnWidth[21] = 65;		//割合5(%)
			columnWidth[22] = 120;	//最終購入日
			//【QP@20505_No11】2012/10/19 TT H.SHIMA MOD End
			
			//テーブルのインスタンス生成
			this.analysisCheckTable = new AnalysisCheckTable(intRow, intCol, columnNm, columnWidth);
			//テーブル用Scrollパネルの設定
//			this.analysisCheckTable.getScroll().setBounds(5, 120, 880, 275);
			this.analysisCheckTable.getScroll().setBounds(5, 120, 970, 275);
			this.add(this.analysisCheckTable.getScroll());
//mod end --------------------------------------------------------------------------------------
			
			//マウスイベント設定
			this.analysisCheckTable.getMainTable().addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					//選択行の行番号を取得します
					int ii = analysisCheckTable.getMainTable().getSelectedRow();
					MaterialData mt = (MaterialData)(bunsekiMaterialMst.getAryMateData().get(ii));
					bunsekiMaterialMst.setSelMate(mt);
					selRow = ii;
				}
			});
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作分析データ確認パネルのテーブル初期化処理が失敗しました。");
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
			
			//------------------------------ 検索項目の初期化 -------------------------------
			//原料チェックボックス
			genryoCheckbox[0].setSelected(false);
			genryoCheckbox[1].setSelected(false);
			//原料コードテキストボックス（半角）
			genryoCdTextbox.setText(null);
			//原料名称テキストボックス
			genryoNmTextbox.setText(null);
			
			//------------------------------- テーブル初期化 ---------------------------------
			dispClear();
			
			//------------------------ 会社コンボボックスの初期設定 ----------------------------
			this.isSelectKaishaCmb = false;
			conJW610();
			this.setKaishaCmb();
			
			//------------------------ 部署コンボボックスの初期設定 ----------------------------
			this.isSelectKaishaCmb = true;
			this.selectKaishaComb();

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			//------------------------ 使用実績・未使用の初期設定 ----------------------------
			//使用実績フラグ・未使用フラグの初期設定
			//this.shiyoFlgBtn[1].setSelected(true);
//			if (this.bushoComb.getSelectedItem().equals(KOJO_ZENKOJO_TATE)) {
//				this.shiyoFlgBtn[0].setEnabled(false);	
//			} else {
//				this.shiyoFlgBtn[0].setEnabled(true);
//			}
//add end --------------------------------------------------------------------------------------
			
			//---------------------------- リンクボタンの初期設定 ------------------------------
			for(int i=0; i<this.linkBtn.length-2; i++) {
				this.linkBtn[i].setEnabled(false);
			}
			//「次へ」・「前へ」リンクボタンの初期設定
			this.linkNextBtn.setEnabled(false);
			this.linkPrevBtn.setEnabled(false);
			
			//------------------------ 試作表分析データ検索（JW710） --------------------------
			conJW710(1);
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
			
			//-------------------------------- テーブル表示 ----------------------------------
			dispTable();
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作分析データ確認パネルのテーブル初期化処理が失敗しました。");
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
			TableBase mt = analysisCheckTable.getMainTable();
			ArrayList aryMateData = bunsekiMaterialMst.getAryMateData();
			ArrayList aryHenkouData = henkouMaterialMst.getAryMateData();
			//2011/06/01 QP@10181_No.5 TT T.Satoh Add Start -------------------------
			//並べ替えた配合データテーブル格納用配列用意
			ArrayList arySortHaigoData = new ArrayList();
			
			//ボタン押下時
			if (event_name.equals("kensaku")
					|| event_name.equals("shosai_btn_click")
					|| event_name.equals("shinkigenryo_btn_click")
					|| event_name.equals(KAISHA_COMB_SELECT)
					|| event_name.equals(LINK_BTN_CLICK)
					|| event_name.equals("sakuzyo")
					|| event_name.equals("toroku")
					|| event_name.equals(LINK_PREV_BTN_CLICK)
					|| event_name.equals(LINK_NEXT_BTN_CLICK)) {
				
			}
			//ボタン押下時以外の場合(初期表示)
			else {
				//配合表画面の配合データ配列取得
				ArrayList aryHaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
				
				//原料マスタから取得した配列をループ
				for(int i=0; i<aryMateData.size(); i++){
					//原料マスタから1行取得
					MaterialData genryoMasterDt = (MaterialData)aryMateData.get(i);
					
					//配合表画面の配合データ配列をループ
					for(int j=0; j<aryHaigoData.size(); j++){
						//配合表画面の配合データを1行取得
						MixedData mxHaigoDt = (MixedData)aryHaigoData.get(j);
						
						//原料コードが一致する場合
						if (genryoMasterDt.getStrGenryocd().equals(mxHaigoDt.getStrGenryoCd())) {
							//配合表画面の配合データを1行追加
							arySortHaigoData.add(mxHaigoDt);
							
							//配合表画面の配合データ配列のループを抜ける
							break;
						}
						//原料コードがNULLの場合
						else if (genryoMasterDt.getStrGenryocd().equals("NULL")
								&& mxHaigoDt.getStrGenryoCd() == null) {
							//配合表画面の配合データを1行追加
							arySortHaigoData.add(mxHaigoDt);
							
							//配合表画面の配合データ配列のループを抜ける
							break;
						}
					}
				}
			}
			//2011/06/01 QP@10181_No.5 TT T.Satoh Add End ---------------------------
			
			//レンダラ生成（文字列）
			MiddleCellRenderer md = new MiddleCellRenderer();
			
			//レンダラ生成（数値）
			MiddleCellRenderer nmd = new MiddleCellRenderer();

			for(int i=0; i<aryMateData.size(); i++){
				
				//データ挿入
				MaterialData selMaterialData = (MaterialData)aryMateData.get(i);
				
				//2011/05/26 QP@10181_No.5 TT T.Satoh Add Start -------------------------
				MixedData mxSortHaigoData = null;
				
				//ボタン押下時
				if (event_name.equals("kensaku")
						|| event_name.equals("shosai_btn_click")
						|| event_name.equals("shinkigenryo_btn_click")
						|| event_name.equals(KAISHA_COMB_SELECT)
						|| event_name.equals(LINK_BTN_CLICK)
						|| event_name.equals("sakuzyo")
						|| event_name.equals("toroku")
						|| event_name.equals(LINK_PREV_BTN_CLICK)
						|| event_name.equals(LINK_NEXT_BTN_CLICK)) {
					
				}
				//ボタン押下時以外の場合(初期表示)
				else {
					//並べ替えた配合表画面の配合データを1行取得
					mxSortHaigoData = (MixedData)arySortHaigoData.get(i);
				}
				//2011/05/26 QP@10181_No.5 TT T.Satoh Add End ---------------------------
				
//				//コメント行は原料名変更
//				if(checkNull(selMaterialData.getStrGenryocd()).equals("999999")){
//					selMaterialData.setStrGenryonm("コメント行です");
//					//selMaterialData.setIntHaisicd(1);
//				}
				
				//行追加
				mt.tableInsertRow(i);
				
				//レンダラ設定（文字列）
				TextboxBase comp = new TextboxBase();
				comp.setBackground(Color.WHITE);
				
				//レンダラ設定（数値）
				NumelicTextbox ncomp = new NumelicTextbox();
				ncomp.setBackground(Color.WHITE);
								
				//データチェック
//				for(int j=0; j<aryHenkouData.size(); j++){
//					MaterialData selHenkouData = (MaterialData)aryHenkouData.get(j);
//					//変更されているデータがある場合
//					if((selMaterialData.getIntKaishacd() == selHenkouData.getIntKaishacd())
//							&& (selMaterialData.getIntBushocd() == selHenkouData.getIntBushocd())
//							&& (selMaterialData.getStrGenryocd().equals(selHenkouData.getStrGenryocd()))){
//						//comp.setBackground(Color.ORANGE);
//					}
//				}
				
				//リスト件数の格納
				max_row = selMaterialData.getIntMaxRow();
				row_num = selMaterialData.getIntListRowMax();
				selectMaxNum = max_row;

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
/*				//No
				mt.setValueAt(Integer.toString(i+1), i, 0);
				//原料CD
				mt.setValueAt(selMaterialData.getStrGenryocd(), i, 1);
				//原料名
				mt.setValueAt(selMaterialData.getStrGenryonm(), i, 2);
				//酢酸(%)
				mt.setValueAt(selMaterialData.getDciSakusan(), i, 3);
				//食塩(%)
				mt.setValueAt(selMaterialData.getDciShokuen(), i, 4);
				//総酸(%)
				mt.setValueAt(selMaterialData.getDciSousan(), i, 5);
				//油含(%)
				mt.setValueAt(selMaterialData.getDciGanyu(), i, 6);
				//表示案
				mt.setValueAt(selMaterialData.getStrHyoji(), i, 7);
				//添加物
				mt.setValueAt(selMaterialData.getStrTenka(), i, 8);
				//メモ
				mt.setValueAt(selMaterialData.getStrMemo(), i, 9);
				//栄養計算食品番号1
				mt.setValueAt(selMaterialData.getStrEiyono1(), i, 10);
				//割合1(%)
				mt.setValueAt(selMaterialData.getStrWariai1(), i, 11);
				//栄養計算食品番号2
				mt.setValueAt(selMaterialData.getStrEiyono2(), i, 12);
				//割合2(%)
				mt.setValueAt(selMaterialData.getStrWariai2(), i, 13);
				//栄養計算食品番号3
				mt.setValueAt(selMaterialData.getStrEiyono3(), i, 14);
				//割合3(%)
				mt.setValueAt(selMaterialData.getStrWariai3(), i, 15);
				//栄養計算食品番号4
				mt.setValueAt(selMaterialData.getStrEiyono4(), i, 16);
				//割合4(%)
				mt.setValueAt(selMaterialData.getStrWariai4(), i, 17);
				//栄養計算食品番号5
				mt.setValueAt(selMaterialData.getStrEiyono5(), i, 18);
				//割合5(%)
				mt.setValueAt(selMaterialData.getStrWariai5(), i, 19);
				//最終購入日
				mt.setValueAt(selMaterialData.getStrKonyu(), i, 20);*/
				//2011/06/01 QP@10181_No.5 TT T.Satoh Change Start -------------------------
				//原料CD
				//mt.setValueAt(selMaterialData.getStrGenryocd(), i, 0);
				//原料名
				//mt.setValueAt(selMaterialData.getStrGenryonm(), i, 1);
				
				//原料CDがNULLの場合
				if (selMaterialData.getStrGenryocd().equals("NULL")) {
					//原料CDに空白を表示
					mt.setValueAt("", i, 0);
				}
				//原料CDがある場合
				else {
					//原料CD表示
					mt.setValueAt(selMaterialData.getStrGenryocd(), i, 0);
				}
				
				//ボタン押下時
				if (event_name.equals("kensaku")
						|| event_name.equals("shosai_btn_click")
						|| event_name.equals("shinkigenryo_btn_click")
						|| event_name.equals(KAISHA_COMB_SELECT)
						|| event_name.equals(LINK_BTN_CLICK)
						|| event_name.equals("sakuzyo")
						|| event_name.equals("toroku")
						|| event_name.equals(LINK_PREV_BTN_CLICK)
						|| event_name.equals(LINK_NEXT_BTN_CLICK)) {
					//原料名表示
					mt.setValueAt(selMaterialData.getStrGenryonm(), i, 1);
				}
				//ボタン押下時以外の場合(初期表示)
				else {
					//原料名表示
					mt.setValueAt(mxSortHaigoData.getStrGenryoNm(), i, 1);
				}
				//2011/06/01 QP@10181_No.5 TT T.Satoh Change End ---------------------------
				
				// 原料コード先頭N文字チェック　（Nの場合、true）
				boolean isGenryoCdN = false;
				if (selMaterialData.getStrGenryocd().substring(0,1).equals("N")) {
					isGenryoCdN = true;
				} else {
					isGenryoCdN = false;
				}
				
				//使用実績
				String strShiyoFlg = "";
				if (isGenryoCdN) {
					//原料コード先頭N文字の場合
					strShiyoFlg = "  -";
				} else if (selMaterialData.getIntShiyoFlg() == 1) {
					//使用実績フラグ＝1
					strShiyoFlg = " ○";
				} else {
					//使用実績フラグ≠1
					strShiyoFlg = "  X";				
				}
				mt.setValueAt(strShiyoFlg, i, 2);
				
				//未使用
				String strMiShiyoFlg = "";
				if (isGenryoCdN) {
					//原料コード先頭N文字の場合
					strMiShiyoFlg = "";
				} else if (selMaterialData.getIntMishiyoFlg() == 1) {
					//未使用フラグ＝1
					strMiShiyoFlg = " ○";
				} else {
					//未使用フラグ≠1
					strMiShiyoFlg = "";	
				}
				mt.setValueAt(strMiShiyoFlg, i, 3);
				
				//酢酸(%)
				mt.setValueAt(selMaterialData.getDciSakusan(), i, 4);
				//食塩(%)
				mt.setValueAt(selMaterialData.getDciShokuen(), i, 5);
				//総酸(%)
				mt.setValueAt(selMaterialData.getDciSousan(), i, 6);
				//【QP@20505_No11】2012/10/19 TT H.SHIMA ADD Start
				mt.setValueAt(selMaterialData.getDciMsg(), i, 7);
				//【QP@20505_No11】2012/10/19 TT H.SHIMA ADD End
				//【QP@20505_No11】2012/10/19 TT H.SHIMA MOD Start
				//油含(%)
				//2011/06/01 QP@10181_No.5 TT T.Satoh Change Start -------------------------
				//mt.setValueAt(selMaterialData.getDciGanyu(), i, 7);
				
				//ボタン押下時
				if (event_name.equals("kensaku")
						|| event_name.equals("shosai_btn_click")
						|| event_name.equals("shinkigenryo_btn_click")
						|| event_name.equals(KAISHA_COMB_SELECT)
						|| event_name.equals(LINK_BTN_CLICK)
						|| event_name.equals("sakuzyo")
						|| event_name.equals("toroku")
						|| event_name.equals(LINK_PREV_BTN_CLICK)
						|| event_name.equals(LINK_NEXT_BTN_CLICK)) {
					//油含(%)表示
//					mt.setValueAt(selMaterialData.getDciGanyu(), i, 7);
					mt.setValueAt(selMaterialData.getDciGanyu(), i, 8);
				}
				//ボタン押下時以外の場合(初期表示)
				else {
					//油含(%)表示
//					mt.setValueAt(mxSortHaigoData.getDciGanyuritu(), i, 7);
					mt.setValueAt(mxSortHaigoData.getDciGanyuritu(), i, 8);
				}
				//2011/06/01 QP@10181_No.5 TT T.Satoh Change End ---------------------------
//				//表示案
//				mt.setValueAt(selMaterialData.getStrHyoji(), i, 8);
//				//添加物
//				mt.setValueAt(selMaterialData.getStrTenka(), i, 9);
//				//メモ
//				mt.setValueAt(selMaterialData.getStrMemo(), i, 10);
//				//栄養計算食品番号1
//				mt.setValueAt(selMaterialData.getStrEiyono1(), i, 11);
//				//割合1(%)
//				mt.setValueAt(selMaterialData.getStrWariai1(), i, 12);
//				//栄養計算食品番号2
//				mt.setValueAt(selMaterialData.getStrEiyono2(), i, 13);
//				//割合2(%)
//				mt.setValueAt(selMaterialData.getStrWariai2(), i, 14);
//				//栄養計算食品番号3
//				mt.setValueAt(selMaterialData.getStrEiyono3(), i, 15);
//				//割合3(%)
//				mt.setValueAt(selMaterialData.getStrWariai3(), i, 16);
//				//栄養計算食品番号4
//				mt.setValueAt(selMaterialData.getStrEiyono4(), i, 17);
//				//割合4(%)
//				mt.setValueAt(selMaterialData.getStrWariai4(), i, 18);
//				//栄養計算食品番号5
//				mt.setValueAt(selMaterialData.getStrEiyono5(), i, 19);
//				//割合5(%)
//				mt.setValueAt(selMaterialData.getStrWariai5(), i, 20);
//				//最終購入日
//				mt.setValueAt(selMaterialData.getStrKonyu(), i, 21);
				//表示案
				mt.setValueAt(selMaterialData.getStrHyoji(), i, 9);
				//添加物
				mt.setValueAt(selMaterialData.getStrTenka(), i, 10);
				//メモ
				mt.setValueAt(selMaterialData.getStrMemo(), i, 11);
				//栄養計算食品番号1
				mt.setValueAt(selMaterialData.getStrEiyono1(), i, 12);
				//割合1(%)
				mt.setValueAt(selMaterialData.getStrWariai1(), i, 13);
				//栄養計算食品番号2
				mt.setValueAt(selMaterialData.getStrEiyono2(), i, 14);
				//割合2(%)
				mt.setValueAt(selMaterialData.getStrWariai2(), i, 15);
				//栄養計算食品番号3
				mt.setValueAt(selMaterialData.getStrEiyono3(), i, 16);
				//割合3(%)
				mt.setValueAt(selMaterialData.getStrWariai3(), i, 17);
				//栄養計算食品番号4
				mt.setValueAt(selMaterialData.getStrEiyono4(), i, 18);
				//割合4(%)
				mt.setValueAt(selMaterialData.getStrWariai4(), i, 19);
				//栄養計算食品番号5
				mt.setValueAt(selMaterialData.getStrEiyono5(), i, 20);
				//割合5(%)
				mt.setValueAt(selMaterialData.getStrWariai5(), i, 21);
				//最終購入日
				mt.setValueAt(selMaterialData.getStrKonyu(), i, 22);
				//【QP@20505_No11】2012/10/19 TT H.SHIMA MOD End
//mod end --------------------------------------------------------------------------------------
				
				if(selMaterialData.getIntHaisicd() == 1){
					comp.setBackground(Color.LIGHT_GRAY);
					ncomp.setBackground(Color.LIGHT_GRAY);
				}
				
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

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
/*				//No
				if(i == 0){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}
				//原料CD
				else if(i == 1){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}
				//原料名
				else if(i == 2){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}
				//酢酸(%)
				else if(i == 3){
					//数値レンダラ設定
					column.setCellRenderer(nmd);				
				}
				//食塩(%)
				else if(i == 4){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}
				//総酸(%)
				else if(i == 5){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}				
				//油含(%)
				else if(i == 6){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}			
				//表示案
				else if(i == 7){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}		
				//添加物
				else if(i == 8){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}	
				//メモ
				else if(i == 9){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}				
				//栄養計算食品番号1
				else if(i == 10){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}				
				//割合1(%)
				else if(i == 11){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}				
				//栄養計算食品番号2
				else if(i == 12){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}		
				//割合2(%)
				else if(i == 13){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}			
				//栄養計算食品番号3
				else if(i == 14){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}				
				//割合3(%)
				else if(i == 15){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}				
				//栄養計算食品番号4
				else if(i == 16){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}				
				//割合4(%)
				else if(i == 17){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}	
				//栄養計算食品番号5
				else if(i == 18){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}			
				//割合5(%)
				else if(i == 19){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}			
				//最終購入日
				else if(i == 20){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}*/
				//原料CD
				if(i == 0){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}
				//原料名
				else if(i == 1){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}
				//使用実績
				else if(i == 2){
					//文字列レンダラ設定
					column.setCellRenderer(md);			
				}
				//未使用
				else if(i == 3){
					//文字列レンダラ設定
					column.setCellRenderer(md);			
				}
				//酢酸(%)
				else if(i == 4){
					//数値レンダラ設定
					column.setCellRenderer(nmd);				
				}
				//食塩(%)
				else if(i == 5){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}
				//総酸(%)
				else if(i == 6){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}				
				//【QP@20505_No11】2012/10/19 TT H.SHIMA MOD Start
//				//油含(%)
//				else if(i == 7){
//					//数値レンダラ設定
//					column.setCellRenderer(nmd);
//				}			
//				//表示案
//				else if(i == 8){
//					//文字列レンダラ設定
//					column.setCellRenderer(md);
//				}		
//				//添加物
//				else if(i == 9){
//					//文字列レンダラ設定
//					column.setCellRenderer(md);
//				}	
//				//メモ
//				else if(i == 10){
//					//文字列レンダラ設定
//					column.setCellRenderer(md);
//				}				
//				//栄養計算食品番号1
//				else if(i == 11){
//					//文字列レンダラ設定
//					column.setCellRenderer(md);
//				}				
//				//割合1(%)
//				else if(i == 12){
//					//数値レンダラ設定
//					column.setCellRenderer(nmd);
//				}				
//				//栄養計算食品番号2
//				else if(i == 13){
//					//文字列レンダラ設定
//					column.setCellRenderer(md);
//				}		
//				//割合2(%)
//				else if(i == 14){
//					//数値レンダラ設定
//					column.setCellRenderer(nmd);
//				}			
//				//栄養計算食品番号3
//				else if(i == 15){
//					//文字列レンダラ設定
//					column.setCellRenderer(md);
//				}				
//				//割合3(%)
//				else if(i == 16){
//					//数値レンダラ設定
//					column.setCellRenderer(nmd);
//				}				
//				//栄養計算食品番号4
//				else if(i == 17){
//					//文字列レンダラ設定
//					column.setCellRenderer(md);
//				}				
//				//割合4(%)
//				else if(i == 18){
//					//数値レンダラ設定
//					column.setCellRenderer(nmd);
//				}	
//				//栄養計算食品番号5
//				else if(i == 19){
//					//文字列レンダラ設定
//					column.setCellRenderer(md);
//				}			
//				//割合5(%)
//				else if(i == 20){
//					//数値レンダラ設定
//					column.setCellRenderer(nmd);
//				}			
//				//最終購入日
//				else if(i == 21){
//					//文字列レンダラ設定
//					column.setCellRenderer(md);
//				}
				//MSG(%)
				else if(i == 7){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}			
				//油含(%)
				else if(i == 8){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}			
				//表示案
				else if(i == 9){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}		
				//添加物
				else if(i == 10){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}	
				//メモ
				else if(i == 11){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}				
				//栄養計算食品番号1
				else if(i == 12){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}				
				//割合1(%)
				else if(i == 13){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}				
				//栄養計算食品番号2
				else if(i == 14){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}		
				//割合2(%)
				else if(i == 15){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}			
				//栄養計算食品番号3
				else if(i == 16){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}				
				//割合3(%)
				else if(i == 17){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}				
				//栄養計算食品番号4
				else if(i == 18){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}				
				//割合4(%)
				else if(i == 19){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}	
				//栄養計算食品番号5
				else if(i == 20){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}			
				//割合5(%)
				else if(i == 21){
					//数値レンダラ設定
					column.setCellRenderer(nmd);
				}			
				//最終購入日
				else if(i == 22){
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}
				//【QP@20505_No11】2012/10/19 TT H.SHIMA MOD End
//mod end --------------------------------------------------------------------------------------
				//上記以外
				else{
					//文字列レンダラ設定
					column.setCellRenderer(md);
				}
			}
			
			//リンクボタン設定
//			int index=0;
//			for(int i=max_row; i>0; i-=row_num){
//				linkBtn[index].setEnabled(true);
//				index++;
//			}
//			intLinkMaxNum = index;
			initLink(max_row, row_num);
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作分析データ確認パネルのテーブル表示処理が失敗しました。");
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
		//TODO

		//リンクボタンの初期設定
		for(int i=0; i<this.linkBtn.length; i++) {
//			if ( i < 10 ) {
//				this.linkBtn[i].setText("" + (i+1));	
//			}
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
			//if ( this.intLinkMaxNum > intListRowMax ) {
			if ( intMaxRow > (intListRowMax*10) ) {	
				//[次へ]・[前へ]リンクボタンを使用可に設定
				this.linkPrevBtn.setEnabled(true);
				this.linkNextBtn.setEnabled(true);
			}
		}
	}
	
	/************************************************************************************
	 * 
	 *   初期処理用　XML通信処理（JW710）
	 *    : 初期処理XMLデータ通信（JW710）を行う
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	private void conJW710(int page) throws ExceptionBase{
		try{
			//----------------------------- 送信パラメータ格納  -------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			//配合データ
			ArrayList aryHaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			
			//----------------------------- 送信XMLデータ作成  ------------------------------
			xmlJW710 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//--------------------------------- Root追加  ---------------------------------
			xmlJW710.AddXmlTag("","JW710");
			arySetTag.clear();
			
			//--------------------------- 機能ID追加（USEERINFO）  --------------------------
			xmlJW710.AddXmlTag("JW710", "USERINFO");
			//　テーブルタグ追加
			xmlJW710.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW710.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();
			
			//------------------------ 機能ID追加（分析値変更確認）  ----------------------------
//			xmlJW710.AddXmlTag("JW710", "SA590");
//			//　テーブルタグ追加
//			xmlJW710.AddXmlTag("SA590", "table");
//			
//			for (int i=0; i<aryHaigoData.size(); i++  ) {
//				MixedData mixedData = (MixedData)aryHaigoData.get(i);
//				//レスポンスデータの設定
//				arySetTag.add(new String[]{"cd_kaisha",checkNull(mixedData.getIntKaishaCd())});		//会社コード(複数)
//				arySetTag.add(new String[]{"cd_busho",checkNull(mixedData.getIntBushoCd())});		//部署コード(複数)
//				arySetTag.add(new String[]{"cd_genryo",checkNull(mixedData.getStrGenryoCd())});		//原料コード(複数)
//				arySetTag.add(new String[]{"nm_genryo",checkNull(mixedData.getStrGenryoNm())});		//原料名(複数)
//				arySetTag.add(new String[]{"tanka",checkNull(mixedData.getDciTanka())});			//単価(複数)
//				arySetTag.add(new String[]{"budomari",checkNull(mixedData.getDciBudomari())});		//歩留(複数)
//				arySetTag.add(new String[]{"ritu_abura",checkNull(mixedData.getDciGanyuritu())});	//油含有率(複数)
//				arySetTag.add(new String[]{"ritu_sakusan",checkNull(mixedData.getDciSakusan())});	//酢酸(複数)
//				arySetTag.add(new String[]{"ritu_shokuen",checkNull(mixedData.getDciShokuen())});	//食塩(複数)
//				arySetTag.add(new String[]{"ritu_sousan",checkNull(mixedData.getDciSosan())});		//総酸(複数)
//				xmlJW710.AddXmlTag("table", "rec", arySetTag);
//				arySetTag.clear();
//			}
			
			//------------------------ 機能ID追加（試作表原料検索）  ----------------------------
			xmlJW710.AddXmlTag("JW710", "SA570");
			//　テーブルタグ追加
			xmlJW710.AddXmlTag("SA570", "table");
			
			//配合データループ
			for (int i=0; i<aryHaigoData.size(); i++  ) {
				
				//配合データ取得
				MixedData mixedData = (MixedData)aryHaigoData.get(i);
				
				//原料桁数取得
				int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();
				
				//コメント行判定
				boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(mixedData.getStrGenryoCd(), keta);
				
				//コメント行の場合
				if( comFlg ){
					
				}else{
					
					//レスポンスデータの設定
					arySetTag.add(new String[]{"cd_kaisha",checkNull(mixedData.getIntKaishaCd())});		//会社コード(複数)
					
					//mod start --------------------------------------------------------------------------------------
					//QP@00412_シサクイック改良 No.4
					//arySetTag.add(new String[]{"cd_busho",checkNull(mixedData.getIntBushoCd())});		//部署コード(複数)
					PrototypeData pt = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
					arySetTag.add(new String[]{"cd_busho",Integer.toString(pt.getIntKojoco())});		//部署コード(複数)
					//mod end --------------------------------------------------------------------------------------
					
					arySetTag.add(new String[]{"cd_genryo",checkNull(mixedData.getStrGenryoCd())});		//原料コード(複数)
					arySetTag.add(new String[]{"num_selectRow",Integer.toString(page)});		//選択ページ番号(複数)
					arySetTag.add(new String[]{"gyo_no",Integer.toString(mixedData.getIntGenryoNo())});		//選択ページ番号(複数)
					xmlJW710.AddXmlTag("table", "rec", arySetTag);
					arySetTag.clear();
					
				}
				
			}
			
			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("\nJW710送信XML===============================================================");
//			xmlJW710.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJW710);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJW710 = xcon.getXdocRes();
//			System.out.println("\nJW710受信XML===============================================================");
//			xmlJW710.dispXml();
			
			//テストXMLデータ
			//xmlJW710 = new XmlData(new File("src/main/JW710.xml"));
			
			//------------------------------- Resultデータチェック -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW710);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				
				//画面情報クリア
				dispClear();
				
				//データ数表示ラベルクリア
				this.dataLabel.setText("");
				
				throw new ExceptionBase();
				
			}else{
				//--------------------------------- 変更情報格納 --------------------------------
//				henkouMaterialMst = new MaterialMstData();
//				henkouMaterialMst.setMaterialData(xmlJW710, "SA590");
				//henkouMaterialMst.DispMateData();
				
				//------------------------------- 原料分析データ格納 -----------------------------
				bunsekiMaterialMst.setMaterialData(xmlJW710,"SA570");
				//bunsekiMaterialMst.DispMateData();
				
				//データ数表示設定
				//リスト表示件数及び最大件数を取得
				int intListRowMax = ((MaterialData)bunsekiMaterialMst.getAryMateData().get(0)).getIntListRowMax();
				int intMaxRow = ((MaterialData)bunsekiMaterialMst.getAryMateData().get(0)).getIntMaxRow();
				
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
			ex.setStrErrmsg("試作分析データ確認パネルの初期処理通信処理が失敗しました。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
	
	/************************************************************************************
	 * 
	 *   検索処理用　XML通信処理（JW720）
	 *    : 検索処理XMLデータ通信（JW720）を行う
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	public void conJW720(int page) throws ExceptionBase{
		try{
			
			//----------------------------- 送信パラメータ格納  -------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strKaishaCd = this.getSelectKaishaCd();
			String strBushoCd = this.getSelectKojoCd();
			
			//----------------------------- 送信XMLデータ作成  ------------------------------
			xmlJW720 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//--------------------------------- Root追加  ---------------------------------
			xmlJW720.AddXmlTag("","JW720");
			arySetTag.clear();
			
			//--------------------------- 機能ID追加（USEERINFO）  --------------------------
			xmlJW720.AddXmlTag("JW720", "USERINFO");
			//　テーブルタグ追加
			xmlJW720.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW720.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();
			
			//------------------------ 機能ID追加（分析値変更確認）  ----------------------------
//			xmlJW720.AddXmlTag("JW720", "SA590");
//			//　テーブルタグ追加
//			xmlJW720.AddXmlTag("SA590", "table");
//			
//			//配合データ
//			ArrayList aryHaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
//			for (int i=0; i<aryHaigoData.size(); i++  ) {
//				MixedData mixedData = (MixedData)aryHaigoData.get(i);
//				//レスポンスデータの設定
//				arySetTag.add(new String[]{"cd_kaisha",checkNull(mixedData.getIntKaishaCd())});		//会社コード(複数)
//				arySetTag.add(new String[]{"cd_busho",checkNull(mixedData.getIntBushoCd())});		//部署コード(複数)
//				arySetTag.add(new String[]{"cd_genryo",checkNull(mixedData.getStrGenryoCd())});		//原料コード(複数)
//				arySetTag.add(new String[]{"nm_genryo",checkNull(mixedData.getStrGenryoNm())});		//原料名(複数)
//				arySetTag.add(new String[]{"tanka",checkNull(mixedData.getDciTanka())});			//単価(複数)
//				arySetTag.add(new String[]{"budomari",checkNull(mixedData.getDciBudomari())});		//歩留(複数)
//				arySetTag.add(new String[]{"ritu_abura",checkNull(mixedData.getDciGanyuritu())});	//油含有率(複数)
//				arySetTag.add(new String[]{"ritu_sakusan",checkNull(mixedData.getDciSakusan())});	//酢酸(複数)
//				arySetTag.add(new String[]{"ritu_shokuen",checkNull(mixedData.getDciShokuen())});	//食塩(複数)
//				arySetTag.add(new String[]{"ritu_sousan",checkNull(mixedData.getDciSosan())});		//総酸(複数)
//				xmlJW720.AddXmlTag("table", "rec", arySetTag);
//				arySetTag.clear();
//			}

			//------------------------ 機能ID追加（分析原料検索）  ----------------------------
			xmlJW720.AddXmlTag("JW720", "SA860");
			//　テーブルタグ追加
			xmlJW720.AddXmlTag("SA860", "table");
			
			//　レコード追加
			String[] taisho_genryo1 = new String[]{"taisho_genryo1", (genryoCheckbox[0].isSelected()?"true":"") };
			String[] taisho_genryo2 = new String[]{"taisho_genryo2", (genryoCheckbox[1].isSelected()?"true":"") };
			String[] cd_genryo = new String[]{"cd_genryo", checkNull(genryoCdTextbox.getText())};
			String[] nm_genryo = new String[]{"nm_genryo", checkNull(genryoNmTextbox.getText())};
			String[] cd_kaisha;
			if(strKaishaCd.equals("0")){
				cd_kaisha = new String[]{"cd_kaisha",""};
			}else{
				cd_kaisha = new String[]{"cd_kaisha",strKaishaCd};
			}
			String[] cd_busho = new String[]{"cd_busho", strBushoCd};
			String[] num_selectRow = new String[]{"num_selectRow", Integer.toString(page)};

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
			// 使用実績ボタン選択　かつ　工場コンボボックス＝空白の場合、使用実績フラグに"1"を設定
			String strShiyoFlg = "";
			if (this.shiyoFlgBtn[0].isSelected() 
					&& !this.getSelectKojoCd().equals(KOJO_ZENKOJO_TATE)) {
				strShiyoFlg = "1";
			} else {
				strShiyoFlg = "0";
			}
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
			
			xmlJW720.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("\nJW720送信XML===============================================================");
//			xmlJW720.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJW720);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJW720 = xcon.getXdocRes();
//			System.out.println("\nJW720受信XML===============================================================");
//			xmlJW720.dispXml();
			
			//テストXMLデータ
			//xmlJW720 = new XmlData(new File("src/main/JW720.xml"));
			
			//------------------------------- Resultデータチェック -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW720);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				
				//画面情報クリア
				dispClear();
				
				//データ数表示ラベルクリア
				this.dataLabel.setText("");
				
				throw new ExceptionBase();
				
			}else{
				//--------------------------------- 変更情報格納 --------------------------------
//				henkouMaterialMst.setMaterialData(xmlJW720, "SA590");
				//henkouMaterialMst.DispMateData();
				
				//------------------------------- 原料分析データ格納 -----------------------------
				bunsekiMaterialMst.setMaterialData(xmlJW720,"SA860");
				//bunsekiMaterialMst.DispMateData();
				
				//データ数表示設定
				//リスト表示件数及び最大件数を取得
				int intListRowMax = ((MaterialData)bunsekiMaterialMst.getAryMateData().get(0)).getIntListRowMax();
				int intMaxRow = ((MaterialData)bunsekiMaterialMst.getAryMateData().get(0)).getIntMaxRow();
				
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
			ex.setStrErrmsg("試作分析データ確認パネルの原料検索通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}
	
	/************************************************************************************
	 * 
	 *   削除処理用　XML通信処理（JW730）
	 *    : 削除処理XMLデータ通信（JW730）を行う
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	private void conJW730() throws ExceptionBase{
		try{
			MaterialData selmt = bunsekiMaterialMst.getSelMate();
			
			//原料桁数取得
			int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();
			
			//コメント行判定
			boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(selmt.getStrGenryocd(), keta);
			
			//原料コードがNULL 且つ コメント行でない場合
			if(selmt != null && !comFlg){
				//----------------------------- 送信パラメータ格納  -------------------------------
				String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
				
				//----------------------------- 送信XMLデータ作成  ------------------------------
				xmlJW730 = new XmlData();
				ArrayList arySetTag = new ArrayList();
				
				//--------------------------------- Root追加  ---------------------------------
				xmlJW730.AddXmlTag("","JW730");
				arySetTag.clear();
				
				//--------------------------- 機能ID追加（USEERINFO）  --------------------------
				xmlJW730.AddXmlTag("JW730", "USERINFO");
				//　テーブルタグ追加
				xmlJW730.AddXmlTag("USERINFO", "table");
				//　レコード追加
				arySetTag.add(new String[]{"kbn_shori", "3"});
				arySetTag.add(new String[]{"id_user",strUser});
				xmlJW730.AddXmlTag("table", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
				
				//--------------------------- 機能ID追加（原料データ管理）  --------------------------
				xmlJW730.AddXmlTag("JW730", "SA370");
				//　テーブルタグ追加
				xmlJW730.AddXmlTag("SA370", "table");
				//　レコード追加
				arySetTag.add(new String[]{"cd_kaisha", checkNull(selmt.getIntKaishacd())});
				arySetTag.add(new String[]{"cd_genryo",checkNull(selmt.getStrGenryocd())});
				arySetTag.add(new String[]{"flg_haishi",checkNull(selmt.getIntHaisicd())});
				xmlJW730.AddXmlTag("table", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
				
				//----------------------------------- XML送信  ----------------------------------
//				System.out.println("\nJW730送信XML===============================================================");
//				xmlJW730.dispXml();
				XmlConnection xcon = new XmlConnection(xmlJW730);
				xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
				xcon.XmlSend();
				
				//----------------------------------- XML受信  ----------------------------------
				xmlJW730 = xcon.getXdocRes();
//				System.out.println("\nJW730受信XML===============================================================");
//				xmlJW730.dispXml();
				
				//------------------------------- Resultデータチェック -----------------------------
				DataCtrl.getInstance().getResultData().setResultData(xmlJW730);
				if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
					throw new ExceptionBase();
				}else{
					//テーブルより削除
					analysisCheckTable.getMainTable().tableDeleteRow(selRow);
					bunsekiMaterialMst.getAryMateData().remove(selRow);
					bunsekiMaterialMst.setSelMate(null);
					
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString("正常に削除処理が完了しました。");
				}
			}
		}catch(ExceptionBase ex){
			throw ex;
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作分析データ確認パネルの削除通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
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
						//2011/05/26 QP@10181_No.5 TT T.Satoh Change Start -------------------------
						//String event_name = e.getActionCommand();
						event_name = e.getActionCommand();
						//2011/05/26 QP@10181_No.5 TT T.Satoh Change End ---------------------------
						
						//--------------------- 詳細 ボタン クリック時の処理 -------------------------
						if ( event_name == "shosai_btn_click") {
							if(analysisCheckTable.getMainTable().getSelectedRow() >= 0){
								MaterialData selmt = bunsekiMaterialMst.getSelMate();
								MaterialData md = bunsekiMaterialMst.getSelMate();
								analysisInputSubDisp.getAnalysisInputPanel().init(1,md);
								
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
								//確認チェック（確認されているデータか否か）
								if (analysisInputSubDisp.getAnalysisInputPanel().getKakuninCheckbox().isSelected()) {
									analysisInputSubDisp.getAnalysisInputPanel().setBlnCheckKakunin(true);
								}
								else{
									analysisInputSubDisp.getAnalysisInputPanel().setBlnCheckKakunin(false);
								}
//add end ----------------------------------------------------------------------------------------
								
								analysisInputSubDisp.setVisible(true);
								
							}else{
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString("原料を選択して下さい。");
							}
							
						//------------------ 新規原料 ボタン クリック時の処理 ------------------------
						} else if ( event_name == "shinkigenryo_btn_click") {
							analysisInputSubDisp.getAnalysisInputPanel().init(0,null);
							
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
							//確認チェック（確認されているデータか否か）
							if (analysisInputSubDisp.getAnalysisInputPanel().getKakuninCheckbox().isSelected()) {
								analysisInputSubDisp.getAnalysisInputPanel().setBlnCheckKakunin(true);
							}
							else{
								analysisInputSubDisp.getAnalysisInputPanel().setBlnCheckKakunin(false);
							}
//add end ----------------------------------------------------------------------------------------
							
							analysisInputSubDisp.setVisible(true);
							
						//--------------------- 検索 ボタン クリック時の処理 -------------------------
						} else if(event_name == "kensaku"){
							selectPage = 1;
							
							//検索処理（JW720）
							conJW720(selectPage);
							dispTable();
							selectFg = true;

							//リンク初期化
							for ( int i=0; i<10; i++) {
								linkBtn[i].setText("" + (i+1));
// ADD start 20120706 hisahori
								linkBtn[i].setBounds(linkBtn[i].getLocation().x,linkBtn[i].getLocation().y,60,linkBtn[i].getHeight());
// ADD start 20120706 hisahori
								//リンク使用可否再設定
								if ( (i+1) <= intLinkMaxNum ) {
									linkBtn[i].setEnabled(true);
									
								} else {
									linkBtn[i].setEnabled(false);
									
								}
								
							}
							
						//---------------------- 会社コンボボックス選択時 --------------------------
						} else if ( event_name.equals(KAISHA_COMB_SELECT)) {
							selectKaishaComb();
							
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
							shiyoFlgBtn[1].setSelected(true);
							shiyoFlgBtn[0].setEnabled(false);
//add end --------------------------------------------------------------------------------------

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
						//---------------------- 工場コンボボックス選択時 --------------------------
						} else if ( event_name.equals(BUSHO_COMB_SELECT)) {
							selectBushoComb();
//add end --------------------------------------------------------------------------------------
							
						//------------------------- リンクボタン押下時 -----------------------------
						} else if ( event_name.equals(LINK_BTN_CLICK) ) {
							ButtonBase link = (ButtonBase)e.getSource();
							String strPage = link.getText();
							//データ挿入
							ArrayList aryMateData = bunsekiMaterialMst.getAryMateData();
							MaterialData selMaterialData = (MaterialData)aryMateData.get(0);
							int intRowNum = selMaterialData.getIntListRowMax();
							
							
							//検索ボタン押下
							if(selectFg){
								if(strPage.equals("最初へ")){
									selectPage = 1;
									conJW720(selectPage);
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
									conJW720(selectPage);
									dispTable();
									
									//最終ページまで進める
									for ( int i=0; i<(selectMaxNum / (intRowNum*10)); i++ ) {
										clickLinkNextBtn(linkNextBtn);
										
									}
									
								}else{
									selectPage = Integer.parseInt(strPage);
									conJW720(selectPage);
									dispTable();
//									intLinkMaxNum = Integer.parseInt(strPage);
								}
							//初期表示
							}else{
								if(strPage.equals("最初へ")){
									selectPage = 1;
									conJW710(selectPage);
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
									conJW710(selectPage);
									dispTable();

									//最終ページまで進める
									for ( int i=0; i<(selectMaxNum / (intRowNum*10)); i++ ) {
										clickLinkNextBtn(linkNextBtn);
										
									}
									
								}else{
									selectPage = Integer.parseInt(strPage);
									conJW710(selectPage);
									dispTable();
//									intLinkMaxNum = Integer.parseInt(strPage);
									
								}
								
							}
						
						//----------------------------- 削除処理 ---------------------------------
						}else if(event_name.equals("sakuzyo")){
							//原料削除
							if(analysisCheckTable.getMainTable().getSelectedRow() >= 0){
								
								//ダイアログコンポーネント設定
								JOptionPane jp = new JOptionPane();
								
								//確認ダイアログ表示
								int option = jp.showConfirmDialog(
										jp.getRootPane(),
										"原料の削除を行います。よろしいですか？"
										, "確認メッセージ"
										,JOptionPane.YES_NO_OPTION
										,JOptionPane.PLAIN_MESSAGE
									);
								
								//「はい」押下
							    if (option == JOptionPane.YES_OPTION){
							    	
							    	//削除処理実行
							    	conJW730();
									
									//検索処理
									int row = analysisCheckTable.getMainTable().getSelectedRow();
// ADD start 20120706 hisahori
									int rowcount = analysisCheckTable.getMainTable().getRowCount();
									if(intLinkMaxNum <= 1 && rowcount <= 0){
										//行カウントが０件なら再検索しない
									}else{
										 if(intLinkMaxNum >= 1 && rowcount <= 0){
												selectPage = selectPage - 1;
										 }
// ADD end 20120706 hisahori
										//（JW720）
										if(selectFg){
											conJW720(selectPage);
											dispTable();
											if(row > -1){
												analysisCheckTable.getMainTable().setRowSelectionInterval(row, row);
												MaterialData mt = (MaterialData)(bunsekiMaterialMst.getAryMateData().get(row));
												bunsekiMaterialMst.setSelMate(mt);
											}
											
										//（JW710）
										}else{
											conJW710(selectPage);
											dispTable();
											if(row > -1){
												analysisCheckTable.getMainTable().setRowSelectionInterval(row, row);
												MaterialData mt = (MaterialData)(bunsekiMaterialMst.getAryMateData().get(row));
												bunsekiMaterialMst.setSelMate(mt);
											}
										}
// ADD start 20120706 hisahori
									}
// ADD end 20120706 hisahori							    	
							    //「いいえ」押下
							    }else if (option == JOptionPane.NO_OPTION){
							    	
							    	//何もしない
							    	
							    }
								
							}else{
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString("原料を選択して下さい。");
							}
							
						//TODO
//						//----------------------------- 栄養計算書出力処理 ---------------------------------
//						}else if(event_name.equals("outputExcel")){
//							if ( DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getShisakuSeq() != 0 ) {
//								//栄養計算書出力処理
//								conJW740();	
//								
//							} else {
//								//メッセージ表示
//								DataCtrl.getInstance().getMessageCtrl().PrintMessageString("試作列が選択されていません。");
//								
//							}
//							
						//------------------------- 登録 ボタン クリック時の処理 --------------------------
						}
						else if ( event_name == "toroku") {
							analysisInputSubDisp.getAnalysisInputPanel().toroku_bunseki();
							
							
							
							//検索処理
							int row = analysisCheckTable.getMainTable().getSelectedRow();
							//検索ボタンが押下されている場合（JW720）
							if(selectFg){
								conJW720(selectPage);
								dispTable();
								if(row > -1){
									analysisCheckTable.getMainTable().setRowSelectionInterval(row, row);
									MaterialData mt = (MaterialData)(bunsekiMaterialMst.getAryMateData().get(row));
									bunsekiMaterialMst.setSelMate(mt);
								}
								
							//初期表示検索の場合（JW710）
							}else{
								conJW710(selectPage);
								dispTable();
								if(row > -1){
									analysisCheckTable.getMainTable().setRowSelectionInterval(row, row);
									MaterialData mt = (MaterialData)(bunsekiMaterialMst.getAryMateData().get(row));
									bunsekiMaterialMst.setSelMate(mt);
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
						ex.setStrErrmsg("試作分析データ確認パネルのActionListenerイベントが失敗しました");
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
			ex.setStrErrmsg("試作分析データ確認パネルの会社コンボボックス設定処理が失敗しました");
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
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.6
//			this.bushoComb.addItem("");
			this.bushoComb.addItem(KOJO_ZENKOJO_TATE);
//mod end --------------------------------------------------------------------------------------
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
			ex.setStrErrmsg("試作分析データ確認パネルの部署コンボボックス設定処理が失敗しました");
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
			ex.setStrErrmsg("試作分析データ確認パネルの選択会社コード取得処理が失敗しました");
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
			String strGamenId = "160";
			
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
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
			}
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作分析データ確認パネルの会社コンボボックス値取得通信処理が失敗しました");
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
			ex.setStrErrmsg("試作分析データ確認パネルの会社コンボボックス選択時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	/************************************************************************************
	 * 
	 * 工場コンボボックス選択時処理
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void selectBushoComb() throws ExceptionBase {
		try {
			if ( this.bushoComb.isValid() ) {		//多重検索を防ぐ
				//空白の場合は、全件のみとする
				if ( this.bushoComb.getSelectedItem().equals(this.KOJO_ZENKOJO_TATE) 
						|| bushoComb.getSelectedItem().equals("新規登録原料") ) {
					this.shiyoFlgBtn[1].setSelected(true);
					this.shiyoFlgBtn[0].setEnabled(false);
				}
				else {
					this.shiyoFlgBtn[0].setEnabled(true);
					this.shiyoFlgBtn[0].setSelected(true);
				}
			}
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作分析データ確認パネルの工場コンボボックス選択時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}	
	}
//add end --------------------------------------------------------------------------------------
	
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
			ex.setStrErrmsg("試作分析データ確認パネルの選択工場コード取得処理が失敗しました");
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
	 * 【JW620】 会社コンボボックス検索時 送信XMLデータ作成
	 * @param strKaishaCd : 会社コード
	 * 
	 ************************************************************************************/
	private void conJW620(String strKaishaCd) throws ExceptionBase{
		try{
			
			//　送信パラメータ格納
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGamenId = "160";
			
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
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//　XML受信
			xmlJW620 = xmlConnection.getXdocRes();
//			xmlJW620.dispXml();
			
			//部署データ
			BushoData.setBushoData(xmlJW620);

			// Resultデータ
			DataCtrl.getInstance().getResultData().setResultData(xmlJW620);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
				
			}
			
		}catch(ExceptionBase e){
			throw e;
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作分析データ確認パネルの会社コンボボックス検索通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
	}

	/************************************************************************************
	 * 
	 * 【JW740】 栄養計算書出力 送信XMLデータ作成
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void conJW740() throws ExceptionBase {
		
		try {
			
			//------------------------------ 送信パラメータ格納  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisakuSeq = Integer.toString(DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getShisakuSeq());

			//------------------------------ 送信XMLデータ作成  ------------------------
			xmlJW740 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root追加  ---------------------------------
			xmlJW740.AddXmlTag("","JW740");
			arySetTag.clear();

			//------------------------------ 機能ID追加（USEERINFO）  -------------------
			xmlJW740.AddXmlTag("JW740", "USERINFO");
			//　テーブルタグ追加
			xmlJW740.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW740.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------------ 機能ID追加（SA430）  ------------------------
			xmlJW740.AddXmlTag("JW740", "SA430");
			//　テーブルタグ追加
			xmlJW740.AddXmlTag("SA430", "table");
			//　レコード追加
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});	
			arySetTag.add(new String[]{"seq_shisaku", strShisakuSeq});	
			xmlJW740.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------------ 機能ID追加（SA440）  ------------------------
			xmlJW740.AddXmlTag("JW740", "SA440");
			//　テーブルタグ追加
			xmlJW740.AddXmlTag("SA440", "table");
			//　レコード追加 : 試作テーブルのデータを設定
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});	
			arySetTag.add(new String[]{"seq_shisaku", strShisakuSeq});	
			xmlJW740.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("JW740送信XML===============================================================");
//			xmlJW740.dispXml();
			XmlConnection xmlConnection = new XmlConnection(xmlJW740);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJW740 = xmlConnection.getXdocRes();
//			System.out.println();
//			System.out.println("JW740受信XML===============================================================");
//			xmlJW740.dispXml();

			//---------------------------- Resultデータ設定(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW740);
			
			// Resultデータ.処理結果がtrueの場合、ExceptionBaseをThrowする
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {	
				throw new ExceptionBase();
				
			}

			//------------------------------ データ設定(SA430/SA440) --------------------------------
			
			//ダウンロードパスクラス取得(SA430)
			DownloadPathData downloadPathData1 = new DownloadPathData();
			downloadPathData1.setDownloadPathData(xmlJW740, "SA430");

			//ダウンロードパスクラス取得(SA440)
			DownloadPathData downloadPathData2 = new DownloadPathData();
			downloadPathData2.setDownloadPathData(xmlJW740, "SA440");
			
			//URLコネクションクラス
			UrlConnection urlConnection = new UrlConnection();
			
			//ダウンロードパスを送り、ファイルダウンロード画面で開く
			urlConnection.urlFileDownLoad( downloadPathData1.getStrDownloadPath() + ":::" + downloadPathData2.getStrDownloadPath());
						
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作分析 栄養計算書出力処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace()[0].toString());
			throw ex;
			
		} finally {
			
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
			if ( analysisCheckTable.getMainTable().getRowCount() > 0 ) {
				for ( int i=analysisCheckTable.getMainTable().getRowCount()-1; i>-1; i-- ) {
					analysisCheckTable.getMainTable().tableDeleteRow(i);
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
			ex.setStrErrmsg("試作分析データ確認パネルの画面初期化処理が失敗しました");
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
			ex.setStrErrmsg("原料一覧パネルの<<リンクボタン押下時処理が失敗しました");
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
			ex.setStrErrmsg("原料一覧パネルの>>リンクボタン押下時処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
}