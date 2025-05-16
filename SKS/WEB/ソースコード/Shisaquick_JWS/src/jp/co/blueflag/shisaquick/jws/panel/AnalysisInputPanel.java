package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import jp.co.blueflag.shisaquick.jws.base.KaishaData;
import jp.co.blueflag.shisaquick.jws.base.MaterialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.CheckboxBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TextAreaBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;
import jp.co.blueflag.shisaquick.jws.label.HeaderLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.label.LevelLabel;
import jp.co.blueflag.shisaquick.jws.manager.MessageCtrl;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/**
 * 
 * 【A05-04】 分析値入力パネル操作用のクラス
 * 
 * @author k-katayama
 * @since 2009/04/03
 */
public class AnalysisInputPanel extends PanelBase {
	private static final long serialVersionUID = 1L;
	
	private KaishaData KaishaData = new KaishaData();
	
	private DispTitleLabel dispTitleLabel;				//画面タイトルラベル
	private HeaderLabel headerLabel;					//ヘッダ表示ラベル
	private LevelLabel levelLabel;							//レベル表示ラベル
	
	private ItemLabel[] itemLabelLeft;					//左舷項目ラベル
	private ItemLabel[] itemLabelOther;					//その他項目ラベル
	
	private ComboBase kaishaComb;						// 会社コンボボックス
	private HankakuTextbox genryoCdTextbox;		// 原料コードテキストボックス(半角)
	private TextboxBase genryoNmTextbox;			// 原料名テキストボックス
	
	private TextboxBase[] numTextbox;				// テキストボックス(数値)
	
	private TextAreaBase hyojianTextarea;				// 表示案テキストエリア
	private ScrollBase hyojianScroll;						// 表示案スクロールパネル
	private TextAreaBase tenkabutuTextarea;			// 添加物テキストエリア
	private ScrollBase tenkabutuScroll;					// 添加物スクロールパネル
	
	private TextboxBase[] eiyouTextbox;			// 栄養計算食品番号割合テキストボックス
	
	private TextAreaBase memoTextarea;				// メモテキストエリア
	private ScrollBase memoScroll;						// メモスクロールパネル
	private HankakuTextbox nyuryokuhiTextbox;		// 入力日テキストボックス(半角)
	private TextboxBase nyuryokushaTextbox;		// 入力者テキストボックス
	
	private CheckboxBase kakuninCheckbox;			// 確認チェックボックス
	private HankakuTextbox kakuninhiTextbox;		// 確認日テキストボックス(半角)
	private TextboxBase kakuninTextbox;				// 確認者テキストボックス
	private CheckboxBase haishiCheckbox;			// 廃止チェックボックス
	private HankakuTextbox kakuteiCdTextbox;		// 確定コードテキストボックス(半角)
	
	private ButtonBase[] button;							// ボタン
	
	private XmlData xmlData;								//ＸＭＬデータ保持
	private XmlConnection xmlConnection;				//ＸＭＬ通信
	private MessageCtrl messageCtrl;					//メッセージ操作
	private ExceptionBase ex;								//エラー操作
	private String strGamenId;
	
	private MaterialData selData = new MaterialData();
	boolean sinkiFg = false;
	
	//確認、更新、登録者ID
	BigDecimal torokuId;
	BigDecimal kakuninId;
	BigDecimal koshinId;
	String koshinNm;
	
	//XMLデータ
	private XmlData xmlJW610;
	private XmlData xmlJW820;
	private XmlData xmlJ010;
	
	//確認チェック
	boolean kakuninFg = false;

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
	//編集前原料情報
	private String strGenryoNmOld = "";		//原料名
	private String strSakusanOld = "";			//酢酸
	private String strShokuenOld = "";			//食塩
	private String strSousanOld = "";			//総酸
	private String strGanyuOld = "";			//油含有率
// ADD start 20121122 QP@20505 課題No.10
	private String strMsgOld = "";			//MSG
// ADD end 20121122 QP@20505 課題No.10
	private String strHyojiOld = "";			//表示案
	private String strTankaOld = "";			//添加物
	private String strMemoOld = "";			//メモ
	private String[] strEiyonoOld = new String[5];		//栄養計算
	private String[] strWariaiOld = new String[5];		//割合
	private String strKakuteiCdOld = "";		//確定コード
	private boolean blnCheckKakunin = false;	//確認データフラグ
	//確認データフラグ　セッタ＆ゲッタ
	public boolean isBlnCheckKakunin() {
		return blnCheckKakunin;
	}
	public void setBlnCheckKakunin(boolean blnCheckKakunin) {
		this.blnCheckKakunin = blnCheckKakunin;
	}
	//確認チェックボックス　セッタ＆ゲッタ
	public CheckboxBase getKakuninCheckbox() {
		return kakuninCheckbox;
	}
	public void setKakuninCheckbox(CheckboxBase kakuninCheckbox) {
		this.kakuninCheckbox = kakuninCheckbox;
	}
	
//add end --------------------------------------------------------------------------------------
		
	/**
	 * コンストラクタ
	 * @param strOutput : 画面タイトル
	 * @throws ExceptionBase
	 */
	public AnalysisInputPanel(String strOutput) throws ExceptionBase {
		//スーパークラスのコンストラクタを呼び出す
		super();

		try {
			//１．パネルの設定
			this.setPanel();
			
			//２．コントロールの配置
			this.addControlLabel(strOutput);
			this.addControl();
			
		}catch(ExceptionBase eb){
			
			throw eb;
			
		}catch(Exception e) {
			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("分析値入力パネルのコンストラクタが失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
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
	 * コントロールラベル配置
	 * @param strTitle : 画面タイトル
	 */
	private void addControlLabel(String strTitle) throws ExceptionBase{
		try{
			int i;
			int x, y, width, height;
			
			int midWidth = 100;
			int defHeight = 18;
			int dispWidht = 500;
			
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
			this.levelLabel.setBounds(dispWidht - 65, 5, 50, 15);
			this.add(this.levelLabel);
			
			///
			/// ヘッダラベル設定
			///
			this.headerLabel = new HeaderLabel();
			this.headerLabel.setBounds(10, 22, dispWidht - 17, 15);
			this.add(this.headerLabel);
			
			///
			/// 左舷項目ラベルの設定
			/// [0:会社, 1:原料コード, 2:原料名, 3:酢酸(%), 4:食塩(%), 5:総酸(%) 6:油含有率(%),
			///  7:表示案, 8:添加物, 9: 栄養計算食品番号割合
			///  10:メモ, 11:入力日, 12:確認日, 13:廃止区 ]
			///
// MOD start 20121122 QP@20505 課題No.10
//			this.itemLabelLeft = new ItemLabel[14];
			this.itemLabelLeft = new ItemLabel[15];
// MOD end 20121122 QP@20505 課題No.10
			String[] strSetText = new String[this.itemLabelLeft.length];
			strSetText[0] = "会社";
			strSetText[1] = "原料コード";
			strSetText[2] = "原料名";
			strSetText[3] = "酢酸(%)";
			strSetText[4] = "食塩(%)";
			strSetText[5] = "総酸(%)";
			strSetText[6] = "油含有率(%)";
// ADD start 20121122 QP@20505 課題No.10
			strSetText[14] = "ＭＳＧ(%)";
// ADD end 20121122 QP@20505 課題No.10
			strSetText[7] = "表示案";
			strSetText[8] = "添加物";
			strSetText[9] = "<html>栄養計算食品番号<br>割合(%)</html>";
			strSetText[10] = "メモ";
			strSetText[11] = "入力日";
			strSetText[12] = "確認日";
			strSetText[13] = "廃止区";
			
			//設定処理
			x = 5;
			y = 40;
			width = midWidth;
// MOD start 20121122 QP@20505 課題No.10 ループの途中に項目MSGを追加するため
//			for ( i=0; i<this.itemLabelLeft.length; i++ ) {
			for ( i=0; i<this.itemLabelLeft.length - 1; i++ ) {
// MOD end 20121122 QP@20505 課題No.10
				height = defHeight;

				//座標調整
				if ( i == 9 ) {
					//栄養計算食品番号割合の場合
					y += 25;
				}
				//サイズ調整
				if ( i > 6 && i < 12 ) {
					//表示案 ～ 入力日の場合
					height += height;
				}
	 			
				//初期化
				this.itemLabelLeft[i] = new ItemLabel();
				this.itemLabelLeft[i].setText(strSetText[i]);
				this.itemLabelLeft[i].setBounds(x,y,width,height);
				//次項目ラベルの座標設定
				x = this.itemLabelLeft[i].getX();
				y = this.itemLabelLeft[i].getY() + height + 5;
				//上揃えに設定
				this.itemLabelLeft[i].setVerticalAlignment(ItemLabel.TOP);
				//パネルに追加
				this.add(this.itemLabelLeft[i]);

// ADD start 20121122 QP@20505 課題No.10 ループの途中(油含有率の前）に項目MSGを追加するため
				if ( i == 5) {
					//初期化
					this.itemLabelLeft[14] = new ItemLabel();
					this.itemLabelLeft[14].setText(strSetText[14]);
					this.itemLabelLeft[14].setBounds(x,y,width,height);
					//次項目ラベルの座標設定
					x = this.itemLabelLeft[14].getX();
					y = this.itemLabelLeft[14].getY() + height + 5;
					//上揃えに設定
					this.itemLabelLeft[14].setVerticalAlignment(ItemLabel.TOP);
					//パネルに追加
					this.add(this.itemLabelLeft[14]);
				}
// ADD end 20121122 QP@20505 課題No.10
			}
			
			///
			/// その他項目ラベルの設定
			/// [0～4: 栄養計算食品番号割合 列番号]
			/// [5,入力者, 6:確認(チェックボックス), 7:確認者, 8:廃止(チェックボックス), 9:確定コード]
			///
// MOD start 20121122 QP@20505 課題No.10
			this.itemLabelOther = new ItemLabel[10];
//			this.itemLabelOther = new ItemLabel[11];
// MOD end 20121122 QP@20505 課題No.10
			height = defHeight;
			
			// 0～4:栄養計算食品番号割合 列番号
			x = this.itemLabelLeft[9].getX() + this.itemLabelLeft[9].getWidth() + 10;
			y = this.itemLabelLeft[9].getY() - 20;
			width = midWidth - 30;
			for ( i = 0; i < 5; i++ ) {
				this.itemLabelOther[i] = new ItemLabel();
				this.itemLabelOther[i].setText(""+ (i+1));
				this.itemLabelOther[i].setBounds(x,y,width,height);
				this.itemLabelOther[i].setBackground(Color.WHITE);
				x = this.itemLabelOther[i].getX() + this.itemLabelOther[i].getWidth();
			}
//// ADD start 20121122 QP@20505 課題No.10
//			// 14： ＭＳＧ
//			i = 5;
//			this.itemLabelOther[10] = new ItemLabel();
//			this.itemLabelOther[10].setText(""+ (i+1));
//			this.itemLabelOther[10].setBounds(x,y,width,height);
//			this.itemLabelOther[10].setBackground(Color.WHITE);
//			x = this.itemLabelOther[10].getX() + this.itemLabelOther[10].getWidth();
//// ADD end 20121122 QP@20505 課題No.10
			
			//入力者
			x = dispWidht / 2;
			y = this.itemLabelLeft[11].getY();		//入力日
			width = midWidth;
			this.itemLabelOther[5] = new ItemLabel();
			this.itemLabelOther[5].setText("入力者");
			this.itemLabelOther[5].setBounds(x-40,y,width,height);
			
			//確認(チェックボックス)
			x = this.itemLabelLeft[11].getX() + this.itemLabelLeft[11].getWidth() + 10;
			y = this.itemLabelLeft[11].getY() + 17;	//入力日
			width = 50;
			this.itemLabelOther[6] = new ItemLabel();
			this.itemLabelOther[6].setText("確認");
			this.itemLabelOther[6].setBounds(x,y+2,width,height-2);
			this.itemLabelOther[6].setBackground(Color.WHITE);
			
			//確認者
			x = dispWidht / 2;
			y = this.itemLabelLeft[12].getY();		//確認日
			width = midWidth;
			this.itemLabelOther[7] = new ItemLabel();
			this.itemLabelOther[7].setText("確認者");
			this.itemLabelOther[7].setBounds(x-40,y,width,height);
			
			//廃止(チェックボックス)
			x = this.itemLabelLeft[13].getX() + this.itemLabelLeft[13].getWidth() + 10;
			y = this.itemLabelLeft[13].getY() + 2;	//廃止区
			width = 50;
			this.itemLabelOther[8] = new ItemLabel();
			this.itemLabelOther[8].setText("廃止");
			this.itemLabelOther[8].setBounds(x,y,width,height-2);
			this.itemLabelOther[8].setBackground(Color.WHITE);
			
			//確定コード
			x = dispWidht / 2;
			y = this.itemLabelLeft[13].getY();		//廃止区
			width = midWidth;
			this.itemLabelOther[9] = new ItemLabel();
			this.itemLabelOther[9].setText("確定コード");
			this.itemLabelOther[9].setBounds(x-40,y,width,height);
			
			//その他項目ラベルをパネルに追加
			for ( i=0; i<this.itemLabelOther.length; i++ ) {
				if ( i != 6 && i != 8 ) {
					//上揃えに設定
					this.itemLabelOther[i].setVerticalAlignment(ItemLabel.TOP);
				}
				//追加
				this.add(this.itemLabelOther[i]);
			}
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("分析値入力パネルのコントロールラベル配置処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
		
	}
	
	/**
	 * コントロール配置
	 */
	private void addControl() throws ExceptionBase{
		try{
			int i, j;
			int x, y, width, height;
			
			int midWidth = 100;
			int largeWidth = 380;
			int dispWidht = 500;
			
			///
			/// 会社コンボボックスの設定
			///
			this.kaishaComb = new ComboBase();
			//座標・サイズ設定
			j = 0;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = midWidth;
			height = this.itemLabelLeft[j].getHeight();
			this.kaishaComb.setBounds(x,y,width+100,height);
			//パネルに追加
			this.add(this.kaishaComb);
			
			/// 
			/// 原料コードテキストボックス(半角)の設定
			///
			this.genryoCdTextbox = new HankakuTextbox();
			//座標・サイズ設定
			j = 1;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = midWidth;
			height = this.itemLabelLeft[j].getHeight();
			this.genryoCdTextbox.setBounds(x,y,width,height);
			//パネルに追加
			this.add(this.genryoCdTextbox);
			
			///
			/// 原料名テキストボックスの設定
			///
			this.genryoNmTextbox = new TextboxBase();
			//座標・サイズ設定
			j = 2;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = largeWidth;
			height = this.itemLabelLeft[j].getHeight();
			this.genryoNmTextbox.setBounds(x,y,width,height);
			//パネルに追加
			this.add(this.genryoNmTextbox);
			
			///
			/// テキストボックス(数値)の設定
			/// [0:酢酸(%), 1:食塩(%), 2:総酸(%), 3:油含有率(%)]
			///
// MOD start 20121122 QP@20505 課題No.10
//			this.numTextbox = new NumelicTextbox[4];
			this.numTextbox = new NumelicTextbox[5];
// MOD end 20121122 QP@20505 課題No.10
			//初期化
			j = 3;
			for ( i = 0; i < this.numTextbox.length; i++ ) {
				this.numTextbox[i] = new NumelicTextbox();
				
// ADD start 20121122 QP@20505 課題No.10 項目MSG(%)をループの途中に追加するため
				if (i == 3) {
					// ＭＳＧ(%)
					int m = 14;
					
					//座標・サイズ設定
					x = this.itemLabelLeft[m].getX() + this.itemLabelLeft[m].getWidth();
					y = this.itemLabelLeft[m].getY();
					width = midWidth;
					height = this.itemLabelLeft[m].getHeight();
					this.numTextbox[i].setBounds(x,y,width,height);
					//パネルに追加
					this.add(this.numTextbox[i]);
					
					j -= 1;
				} else {
// ADD end 20121122 QP@20505 課題No.10
					//座標・サイズ設定
					x = this.itemLabelLeft[j + i].getX() + this.itemLabelLeft[j + i].getWidth();
					y = this.itemLabelLeft[j + i].getY();
					width = midWidth;
					height = this.itemLabelLeft[j + i].getHeight();
					this.numTextbox[i].setBounds(x,y,width,height);
					//パネルに追加
					this.add(this.numTextbox[i]);
// ADD start 20121122 QP@20505 課題No.10
				}
// ADD end 20121122 QP@20505 課題No.10
			}


			///
			/// 表示案テキストエリアの設定
			///
			this.hyojianTextarea = new TextAreaBase();
			hyojianTextarea.setTABFocusControl();
			//座標・サイズ設定
			j = 7;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = largeWidth;
			height = this.itemLabelLeft[j].getHeight();
//			this.hyojianTextarea.setBounds(x,y,width,height);
			//テキスト折り返し可に設定
			this.hyojianTextarea.setLineWrap(true);
			//スクロールパネル生成
			this.hyojianScroll = new ScrollBase(this.hyojianTextarea);
			this.hyojianScroll.setBounds(x,y,width,height);
			//パネルに追加
			this.add(this.hyojianScroll);
			
			///
			/// 添加物テキストエリアの設定
			///
			this.tenkabutuTextarea = new TextAreaBase();
			tenkabutuTextarea.setTABFocusControl();
			//座標・サイズ設定
			j = 8;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = largeWidth;
			height = this.itemLabelLeft[j].getHeight();
//			this.tenkabutuTextarea.setBounds(x,y,width,height);
			//テキスト折り返し可に設定
			this.tenkabutuTextarea.setLineWrap(true);
			//スクロールパネル生成
			this.tenkabutuScroll = new ScrollBase(this.tenkabutuTextarea);
			this.tenkabutuScroll.setBounds(x,y,width,height);
			//パネルに追加
			this.add(this.tenkabutuScroll);
					
			///
			/// 栄養計算食品番号割合(%)テキストボックスの設定
			/// [0～9:テキストボックス(数値)]
			///
			this.eiyouTextbox = new TextboxBase[10];
			//サイズ設定
			width = this.itemLabelOther[0].getWidth();
			height = this.itemLabelLeft[0].getHeight();
			//初期化
			j = 9;
			for ( i = 0; i < this.eiyouTextbox.length; i++ ) {
				this.eiyouTextbox[i] = new TextboxBase();
				
				//X座標
				if ( i == 0 || i == 5 ) {
					x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
				} else {
					x += width;
				}
				//X座標
				if ( i < 5 ) {
					y = this.itemLabelLeft[j].getY();
				} else {
					y = this.itemLabelLeft[j].getY() + this.itemLabelLeft[0].getHeight();
				}
				//座標・サイズ設定
				this.eiyouTextbox[i].setBounds(x,y,width,height);
				//パネルに追加
				this.add(this.eiyouTextbox[i]);
			}
			
			///
			/// メモテキストエリアの設定
			///
			this.memoTextarea = new TextAreaBase();
			memoTextarea.setTABFocusControl();
			//座標・サイズ設定
			j = 10;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = largeWidth;
			height = this.itemLabelLeft[j].getHeight();
//			this.memoTextarea.setBounds(x,y,width,height);
			//テキスト折り返し可に設定
			this.memoTextarea.setLineWrap(true);
			//スクロールパネル生成
			this.memoScroll = new ScrollBase(this.memoTextarea);
			this.memoScroll.setBounds(x,y,width,height);
			//パネルに追加
			this.add(this.memoScroll);

			///
			/// 入力日テキストボックス(半角)の設定
			///
			this.nyuryokuhiTextbox = new HankakuTextbox();
			//座標・サイズ設定
			j = 11;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = midWidth;
			height = this.itemLabelLeft[0].getHeight();
			this.nyuryokuhiTextbox.setBounds(x,y,width,height);
			//パネルに追加
			this.add(this.nyuryokuhiTextbox);

			///
			/// 入力者テキストボックスの設定
			///
			this.nyuryokushaTextbox = new TextboxBase();
			//座標・サイズ設定
			j = 5;
			x = this.itemLabelOther[j].getX() + this.itemLabelOther[j].getWidth();
			y = this.itemLabelOther[j].getY();
			width = midWidth;
			height = this.itemLabelOther[j].getHeight();
			this.nyuryokushaTextbox.setBounds(x,y,width+74,height);
			//パネルに追加
			this.add(this.nyuryokushaTextbox);

			///
			/// 確認チェックボックスの設定
			///
			this.kakuninCheckbox = new CheckboxBase();
			//座標・サイズ設定
			j = 6;
			x = this.itemLabelOther[j].getX() + this.itemLabelOther[j].getWidth();
			y = this.itemLabelOther[j].getY() + 2;
			width = 20;
			height = this.itemLabelOther[j].getHeight();
			this.kakuninCheckbox.setBounds(x,y,width,height);
			//背景 : 白
			this.kakuninCheckbox.setBackground(Color.WHITE);
			this.kakuninCheckbox.addActionListener(this.getActionEvent());
			this.kakuninCheckbox.setActionCommand("kakunin");
			//パネルに追加
			this.add(this.kakuninCheckbox);

			///
			/// 確認日テキストボックス(半角)の設定
			///
			this.kakuninhiTextbox = new HankakuTextbox();
			//座標・サイズ設定
			j = 12;
			x = this.itemLabelLeft[j].getX() + this.itemLabelLeft[j].getWidth();
			y = this.itemLabelLeft[j].getY();
			width = midWidth;
			height = this.itemLabelLeft[j].getHeight();
			this.kakuninhiTextbox.setBounds(x,y,width,height);
			//パネルに追加
			this.add(this.kakuninhiTextbox);

			///
			/// 確認者テキストボックスの設定
			///
			this.kakuninTextbox = new TextboxBase();
			//座標・サイズ設定
			j = 7;
			x = this.itemLabelOther[j].getX() + this.itemLabelOther[j].getWidth();
			y = this.itemLabelOther[j].getY();
			width = midWidth;
			height = this.itemLabelOther[j].getHeight();
			this.kakuninTextbox.setBounds(x,y,width+74,height);
			//パネルに追加
			this.add(this.kakuninTextbox);
			
			///
			/// 廃止チェックボックスの設定
			///
			this.haishiCheckbox = new CheckboxBase();
			//座標・サイズ設定
			j = 8;
			x = this.itemLabelOther[j].getX() + this.itemLabelOther[j].getWidth();
			y = this.itemLabelOther[j].getY() + 2;
			width = 20;
			height = this.itemLabelOther[j].getHeight();
			this.haishiCheckbox.setBounds(x,y,width,height);
			//背景 : 白
			this.haishiCheckbox.setBackground(Color.WHITE);
			//パネルに追加
			this.add(this.haishiCheckbox);

			///
			/// 確定コードテキストボックス(半角)の設定
			///
			this.kakuteiCdTextbox = new HankakuTextbox();
			//座標・サイズ設定
			j = 9;
			x = this.itemLabelOther[j].getX() + this.itemLabelOther[j].getWidth();
			y = this.itemLabelOther[j].getY();
			width = midWidth;
			height = this.itemLabelOther[j].getHeight();
			this.kakuteiCdTextbox.setBounds(x,y,width+74,height);
			//パネルに追加
			this.add(this.kakuteiCdTextbox);

			///
			/// ボタン
			/// [0:登録, 1:終了]
			///
			this.button = new ButtonBase[2];
			//0:登録
			width = 80;
			height = 38;
			x = dispWidht - 100 - width;
// MOD start 20121122 QP@20505 課題No.10
//			y = 480;
			y = 500;
// MOD end 20121122 QP@20505 課題No.10
			this.button[0] = new ButtonBase();
			this.button[0].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[0].setBounds(x, y, width, height);
			this.button[0].setText("登録");
			//1:終了
			x = dispWidht - 100;
			this.button[1] = new ButtonBase();
			this.button[1].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[1].setBounds(x, y, width, height);
			this.button[1].setText("終了");
			//ボタンをパネルに追加
			for ( i=0; i<this.button.length; i++ ) {
				this.add(this.button[i]);
			}
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("分析値入力パネルのコントロール配置処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
		
	}

	/**
	 * 初期化処理
	 * @param mode : 0=新規 or 1=詳細
	 * @param md : 選択原料データ
	 */
	public void init(int mode,MaterialData md) throws ExceptionBase{
		try{
			//--------------------------- 編集指定（新規） ----------------------------
			if(mode == 0){
				strGamenId = "130";
				sinkiFg = true;
				
				
				//会社コンボボックスの初期設定
				conJW610();
				this.setKaishaCmb();
				
				//会社コンボボックス
				try{
					//自会社を選択
					kaishaComb.setSelectedItem(DataCtrl.getInstance().getUserMstData().getStrKaishanm());
					
				}catch(Exception e){
					//コンボボックス内の最初のものを選択
					kaishaComb.setSelectedIndex(0);
					
				}
				
				// 原料コードテキストボックス(半角)
				genryoCdTextbox.setText(null);
				// 原料名テキストボックス
				genryoNmTextbox.setText(null);
				
				// テキストボックス(数値)
				// [0:酢酸(%), 1:食塩(%), 2:総酸(%), 3:ＭＳＧ(%) 4:油含有率(%) ]
				numTextbox[0].setText(null);
				numTextbox[1].setText(null);
				numTextbox[2].setText(null);
				numTextbox[3].setText(null);
// ADD start 20121122 QP@20505 課題No.10
				numTextbox[4].setText(null);
// ADD end 20121122 QP@20505 課題No.10
				
				// 表示案テキストエリア
				hyojianTextarea.setText(null);
				// 添加物テキストエリア
				tenkabutuTextarea.setText(null);
				
				// 栄養計算食品番号割合テキストボックス
				eiyouTextbox[0].setText(null);
				eiyouTextbox[1].setText(null);
				eiyouTextbox[2].setText(null);
				eiyouTextbox[3].setText(null);
				eiyouTextbox[4].setText(null);
				eiyouTextbox[5].setText(null);
				eiyouTextbox[6].setText(null);
				eiyouTextbox[7].setText(null);
				eiyouTextbox[8].setText(null);
				eiyouTextbox[9].setText(null);
				
				// メモテキストエリア
				memoTextarea.setText(null);
				// 入力日テキストボックス(半角)
				nyuryokuhiTextbox.setText(null);
				// 入力者テキストボックス
				nyuryokushaTextbox.setText(null);
				
				// 確認チェックボックス
				kakuninCheckbox.setSelected(false);
				// 確認日テキストボックス(半角)
				kakuninhiTextbox.setText(null);
				// 確認者テキストボックス
				kakuninTextbox.setText(null);
				// 廃止チェックボックス
				haishiCheckbox.setEnabled(false);
				// 確定コードテキストボックス(半角)
				kakuteiCdTextbox.setText(null);
				
				//ID格納
				torokuId = DataCtrl.getInstance().getUserMstData().getDciUserid();
				kakuninId = null;
				
				//会社コンボボックス
				kaishaComb.setEnabled(true);
				// 原料コードテキストボックス(半角)
				genryoCdTextbox.setEnabled(false);
				// 原料名テキストボックス
				genryoNmTextbox.setEnabled(true);
				
				// テキストボックス(数値)
				numTextbox[0].setEnabled(true);
				numTextbox[1].setEnabled(true);
				numTextbox[2].setEnabled(true);
				numTextbox[3].setEnabled(true);
// ADD start 20121122 QP@20505 課題No.10
				numTextbox[4].setEnabled(true);
// ADD end 20121122 QP@20505 課題No.10
				
				// 表示案テキストエリア
				hyojianTextarea.setEnabled(false);
				// 添加物テキストエリア
				tenkabutuTextarea.setEnabled(false);
				
				// 栄養計算食品番号割合テキストボックス
				eiyouTextbox[0].setEnabled(false);
				eiyouTextbox[1].setEnabled(false);
				eiyouTextbox[2].setEnabled(false);
				eiyouTextbox[3].setEnabled(false);
				eiyouTextbox[4].setEnabled(false);
				eiyouTextbox[5].setEnabled(false);
				eiyouTextbox[6].setEnabled(false);
				eiyouTextbox[7].setEnabled(false);
				eiyouTextbox[8].setEnabled(false);
				eiyouTextbox[9].setEnabled(false);
				
				// メモテキストエリア
				memoTextarea.setEnabled(true);
				// 入力日テキストボックス(半角)
				nyuryokuhiTextbox.setText(getSysDate());
				nyuryokuhiTextbox.setEnabled(false);
				// 入力者テキストボックス
				nyuryokushaTextbox.setText(DataCtrl.getInstance().getUserMstData().getStrUsernm());
				nyuryokushaTextbox.setEnabled(false);
				
				// 確認チェックボックス
				kakuninCheckbox.setEnabled(false);
				// 確認日テキストボックス(半角)
				kakuninhiTextbox.setEnabled(false);
				// 確認者テキストボックス
				kakuninTextbox.setEnabled(false);
				// 廃止チェックボックス
				haishiCheckbox.setEnabled(false);
				haishiCheckbox.setSelected(false);
				// 確定コードテキストボックス(半角)
				kakuteiCdTextbox.setEnabled(false);
				// ボタン
				button[0].setEnabled(true);
				button[1].setEnabled(true);
			
			//--------------------------- 編集指定（詳細） ----------------------------
			}else{
				strGamenId = "140";
				sinkiFg = false;
				//ID格納
				torokuId = md.getDciTorokuId();
				kakuninId = md.getDciKakunincd();
				
				koshinId = md.getDciKosinId();
				koshinNm = md.getStrKosinNm();
				
				
				//会社コンボボックスの初期設定
				conJW610();
				this.setKaishaCmb();
				
				//-------------------------- データ設定 -------------------------------
				//会社コンボボックス
				kaishaComb.setSelectedItem(getSelectKaishaNm(md.getIntKaishacd()));
				// 原料コードテキストボックス(半角)
				genryoCdTextbox.setText(md.getStrGenryocd());
				// 原料名テキストボックス
				genryoNmTextbox.setText(md.getStrGenryonm());
				
				// テキストボックス(数値)
				// [0:酢酸(%), 1:食塩(%), 2:総酸(%), 3:MSG 4:油含有率(%)]
				if(md.getDciSakusan() != null){
					numTextbox[0].setText(md.getDciSakusan().toString());
				}else{
					numTextbox[0].setText(null);
				}
				if(md.getDciShokuen() != null){
					numTextbox[1].setText(md.getDciShokuen().toString());
				}else{
					numTextbox[1].setText(null);
				}
				if(md.getDciSousan() != null){
					numTextbox[2].setText(md.getDciSousan().toString());
				}else{
					numTextbox[2].setText(null);
				}
// MOD start 20121122 QP@20505 課題No.10
//				if(md.getDciGanyu() != null){
//					numTextbox[3].setText(md.getDciGanyu().toString());
//				}else{
//					numTextbox[3].setText(null);
//				}
				if(md.getDciMsg() != null){
					numTextbox[3].setText(md.getDciMsg().toString());
				}else{
					numTextbox[3].setText(null);
				}
				if(md.getDciGanyu() != null){
					numTextbox[4].setText(md.getDciGanyu().toString());
				}else{
					numTextbox[4].setText(null);
				}
// MOD end 20121122 QP@20505 課題No.10
				
				
				// 表示案テキストエリア
				hyojianTextarea.setText(md.getStrHyoji());
				// 添加物テキストエリア
				tenkabutuTextarea.setText(md.getStrTenka());
				
				// 栄養計算食品番号割合テキストボックス
				eiyouTextbox[0].setText(md.getStrEiyono1());
				eiyouTextbox[1].setText(md.getStrEiyono2());
				eiyouTextbox[2].setText(md.getStrEiyono3());
				eiyouTextbox[3].setText(md.getStrEiyono4());
				eiyouTextbox[4].setText(md.getStrEiyono5());
				eiyouTextbox[5].setText(md.getStrWariai1());
				eiyouTextbox[6].setText(md.getStrWariai2());
				eiyouTextbox[7].setText(md.getStrWariai3());
				eiyouTextbox[8].setText(md.getStrWariai4());
				eiyouTextbox[9].setText(md.getStrWariai5());
				
				// メモテキストエリア
				memoTextarea.setText(md.getStrMemo());
				// 入力日テキストボックス(半角)
				nyuryokuhiTextbox.setText(md.getStrKosinDt());
				// 入力者テキストボックス
				nyuryokushaTextbox.setText(md.getStrKosinNm());
				
				// 確認チェックボックス
				kakuninCheckbox.setSelected((md.getDciKakunincd()!=null)?true:false);
				
				// 確認されたデータかどうか
				if(md.getDciKakunincd()!=null){
					kakuninFg = true;
				}else{
					kakuninFg = false;
				}
				
				// 確認日テキストボックス(半角)
				kakuninhiTextbox.setText(md.getStrKakuninhi());
				// 確認者テキストボックス
				kakuninTextbox.setText(md.getStrKakuninnm());
				// 廃止チェックボックス
				haishiCheckbox.setSelected((md.getIntHaisicd()==1)?true:false);
				// 確定コードテキストボックス(半角)
				kakuteiCdTextbox.setText(md.getStrkakuteicd());
				
				//権限チェック
				ArrayList Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
				boolean delChk = false;
				for(int j=0;j<Auth.size();j++){
					String[] strDispAuth = (String[])Auth.get(j);
					if(strDispAuth[0].equals("140")){
						//閲覧権限の場合
						if(strDispAuth[1].equals("10")){
							//会社コンボボックス
							kaishaComb.setEnabled(false);
							// 原料コードテキストボックス(半角)
							genryoCdTextbox.setEnabled(false);
							// 原料名テキストボックス
							genryoNmTextbox.setEnabled(false);	
							
							// テキストボックス(数値)
							numTextbox[0].setEnabled(false);
							numTextbox[1].setEnabled(false);
							numTextbox[2].setEnabled(false);
							numTextbox[3].setEnabled(false);
// ADD start 20121122 QP@20505 課題No.10
							numTextbox[4].setEnabled(false);
// ADD end 20121122 QP@20505 課題No.10
							
							// 表示案テキストエリア
							hyojianTextarea.setEnabled(false);
							// 添加物テキストエリア
							tenkabutuTextarea.setEnabled(false);
							
							// 栄養計算食品番号割合テキストボックス
							eiyouTextbox[0].setEnabled(false);
							eiyouTextbox[1].setEnabled(false);
							eiyouTextbox[2].setEnabled(false);
							eiyouTextbox[3].setEnabled(false);
							eiyouTextbox[4].setEnabled(false);
							eiyouTextbox[5].setEnabled(false);
							eiyouTextbox[6].setEnabled(false);
							eiyouTextbox[7].setEnabled(false);
							eiyouTextbox[8].setEnabled(false);
							eiyouTextbox[9].setEnabled(false);
							
							// メモテキストエリア
							memoTextarea.setEnabled(false);
							// 入力日テキストボックス(半角)
							nyuryokuhiTextbox.setEnabled(false);
							// 入力者テキストボックス
							nyuryokushaTextbox.setEnabled(false);
							
							// 確認チェックボックス
							kakuninCheckbox.setEnabled(false);
							// 確認日テキストボックス(半角)
							kakuninhiTextbox.setEnabled(false);
							// 確認者テキストボックス
							kakuninTextbox.setEnabled(false);
							// 廃止チェックボックス
							haishiCheckbox.setEnabled(false);
							// 確定コードテキストボックス(半角)
							kakuteiCdTextbox.setEnabled(false);
							// ボタン
							button[0].setEnabled(false);
							button[1].setEnabled(true);
							
						}
						
						//編集（新規）権限の場合
						else if(strDispAuth[1].equals("20")){
							//新規原料
							if(md.getStrGenryocd().indexOf("N") != -1){
								//会社コンボボックス
								kaishaComb.setEnabled(false);
								// 原料コードテキストボックス(半角)
								genryoCdTextbox.setEnabled(false);
								// 原料名テキストボックス
								genryoNmTextbox.setEnabled(true);	
								
								// テキストボックス(数値)
								numTextbox[0].setEnabled(true);
								numTextbox[1].setEnabled(true);
								numTextbox[2].setEnabled(true);
								numTextbox[3].setEnabled(true);
// ADD start 20121122 QP@20505 課題No.10
								numTextbox[4].setEnabled(true);
// ADD end 20121122 QP@20505 課題No.10
								
								// 表示案テキストエリア
								hyojianTextarea.setEnabled(true);
								// 添加物テキストエリア
								tenkabutuTextarea.setEnabled(true);
								
								// 栄養計算食品番号割合テキストボックス
								eiyouTextbox[0].setEnabled(true);
								eiyouTextbox[1].setEnabled(true);
								eiyouTextbox[2].setEnabled(true);
								eiyouTextbox[3].setEnabled(true);
								eiyouTextbox[4].setEnabled(true);
								eiyouTextbox[5].setEnabled(true);
								eiyouTextbox[6].setEnabled(true);
								eiyouTextbox[7].setEnabled(true);
								eiyouTextbox[8].setEnabled(true);
								eiyouTextbox[9].setEnabled(true);
								
								// メモテキストエリア
								memoTextarea.setEnabled(true);
								// 入力日テキストボックス(半角)
								nyuryokuhiTextbox.setEnabled(false);
								// 入力者テキストボックス
								nyuryokushaTextbox.setEnabled(false);
								
								// 確認チェックボックス
								kakuninCheckbox.setEnabled(true);
								// 確認日テキストボックス(半角)
								kakuninhiTextbox.setEnabled(false);
								// 確認者テキストボックス
								kakuninTextbox.setEnabled(false);
								// 廃止チェックボックス
								haishiCheckbox.setEnabled(true);
								// 確定コードテキストボックス(半角)
								kakuteiCdTextbox.setEnabled(true);
								// ボタン
								button[0].setEnabled(true);
								button[1].setEnabled(true);
								
							//既存原料
							}else{
								//会社コンボボックス
								kaishaComb.setEnabled(false);
								// 原料コードテキストボックス(半角)
								genryoCdTextbox.setEnabled(false);
								// 原料名テキストボックス
								genryoNmTextbox.setEnabled(false);	
								
								// テキストボックス(数値)
								numTextbox[0].setEnabled(false);
								numTextbox[1].setEnabled(false);
								numTextbox[2].setEnabled(false);
								numTextbox[3].setEnabled(false);
// ADD start 20121122 QP@20505 課題No.10
								numTextbox[4].setEnabled(false);
// ADD end 20121122 QP@20505 課題No.10
								
								// 表示案テキストエリア
								hyojianTextarea.setEnabled(false);
								// 添加物テキストエリア
								tenkabutuTextarea.setEnabled(false);
								
								// 栄養計算食品番号割合テキストボックス
								eiyouTextbox[0].setEnabled(false);
								eiyouTextbox[1].setEnabled(false);
								eiyouTextbox[2].setEnabled(false);
								eiyouTextbox[3].setEnabled(false);
								eiyouTextbox[4].setEnabled(false);
								eiyouTextbox[5].setEnabled(false);
								eiyouTextbox[6].setEnabled(false);
								eiyouTextbox[7].setEnabled(false);
								eiyouTextbox[8].setEnabled(false);
								eiyouTextbox[9].setEnabled(false);
								
								// メモテキストエリア
								memoTextarea.setEnabled(false);
								// 入力日テキストボックス(半角)
								nyuryokuhiTextbox.setEnabled(false);
								// 入力者テキストボックス
								nyuryokushaTextbox.setEnabled(false);
								
								// 確認チェックボックス
								kakuninCheckbox.setEnabled(false);
								// 確認日テキストボックス(半角)
								kakuninhiTextbox.setEnabled(false);
								// 確認者テキストボックス
								kakuninTextbox.setEnabled(false);
								// 廃止チェックボックス
								haishiCheckbox.setEnabled(false);
								// 確定コードテキストボックス(半角)
								kakuteiCdTextbox.setEnabled(false);
								// ボタン
								button[0].setEnabled(false);
								button[1].setEnabled(true);
							}
						}
						//編集（全て）権限の場合
						else if(strDispAuth[1].equals("40")){
							//新規原料
							if(md.getStrGenryocd().indexOf("N") != -1){
								//会社コンボボックス
								kaishaComb.setEnabled(false);
								// 原料コードテキストボックス(半角)
								genryoCdTextbox.setEnabled(false);
								// 原料名テキストボックス
								genryoNmTextbox.setEnabled(true);	
								
								// テキストボックス(数値)
								numTextbox[0].setEnabled(true);
								numTextbox[1].setEnabled(true);
								numTextbox[2].setEnabled(true);
								numTextbox[3].setEnabled(true);
// ADD start 20121122 QP@20505 課題No.10
								numTextbox[4].setEnabled(true);
// ADD end 20121122 QP@20505 課題No.10
								
								// 表示案テキストエリア
								hyojianTextarea.setEnabled(true);
								// 添加物テキストエリア
								tenkabutuTextarea.setEnabled(true);
								
								// 栄養計算食品番号割合テキストボックス
								eiyouTextbox[0].setEnabled(true);
								eiyouTextbox[1].setEnabled(true);
								eiyouTextbox[2].setEnabled(true);
								eiyouTextbox[3].setEnabled(true);
								eiyouTextbox[4].setEnabled(true);
								eiyouTextbox[5].setEnabled(true);
								eiyouTextbox[6].setEnabled(true);
								eiyouTextbox[7].setEnabled(true);
								eiyouTextbox[8].setEnabled(true);
								eiyouTextbox[9].setEnabled(true);
								
								// メモテキストエリア
								memoTextarea.setEnabled(true);
								// 入力日テキストボックス(半角)
								nyuryokuhiTextbox.setEnabled(false);
								// 入力者テキストボックス
								nyuryokushaTextbox.setEnabled(false);
								
								// 確認チェックボックス
								kakuninCheckbox.setEnabled(true);
								// 確認日テキストボックス(半角)
								kakuninhiTextbox.setEnabled(false);
								// 確認者テキストボックス
								kakuninTextbox.setEnabled(false);
								// 廃止チェックボックス
								haishiCheckbox.setEnabled(true);
								// 確定コードテキストボックス(半角)
								kakuteiCdTextbox.setEnabled(true);
								// ボタン
								button[0].setEnabled(true);
								button[1].setEnabled(true);
							}else{
								//会社コンボボックス
								kaishaComb.setEnabled(false);
								// 原料コードテキストボックス(半角)
								genryoCdTextbox.setEnabled(false);
								// 原料名テキストボックス
								genryoNmTextbox.setEnabled(false);	
								
								// テキストボックス(数値)
								numTextbox[0].setEnabled(true);
								numTextbox[1].setEnabled(true);
								numTextbox[2].setEnabled(true);
								numTextbox[3].setEnabled(true);
// ADD start 20121122 QP@20505 課題No.10
								numTextbox[4].setEnabled(true);
// ADD end 20121122 QP@20505 課題No.10
								
								// 表示案テキストエリア
								hyojianTextarea.setEnabled(true);
								// 添加物テキストエリア
								tenkabutuTextarea.setEnabled(true);
								
								// 栄養計算食品番号割合テキストボックス
								eiyouTextbox[0].setEnabled(true);
								eiyouTextbox[1].setEnabled(true);
								eiyouTextbox[2].setEnabled(true);
								eiyouTextbox[3].setEnabled(true);
								eiyouTextbox[4].setEnabled(true);
								eiyouTextbox[5].setEnabled(true);
								eiyouTextbox[6].setEnabled(true);
								eiyouTextbox[7].setEnabled(true);
								eiyouTextbox[8].setEnabled(true);
								eiyouTextbox[9].setEnabled(true);
								
								// メモテキストエリア
								memoTextarea.setEnabled(true);
								// 入力日テキストボックス(半角)
								nyuryokuhiTextbox.setEnabled(false);
								// 入力者テキストボックス
								nyuryokushaTextbox.setEnabled(false);
								
								// 確認チェックボックス
								kakuninCheckbox.setEnabled(true);
								// 確認日テキストボックス(半角)
								kakuninhiTextbox.setEnabled(false);
								// 確認者テキストボックス
								kakuninTextbox.setEnabled(false);
								// 廃止チェックボックス
								haishiCheckbox.setEnabled(false);
								// 確定コードテキストボックス(半角)
								kakuteiCdTextbox.setEnabled(false);
								// ボタン
								button[0].setEnabled(true);
								button[1].setEnabled(true);
							}
						}
					}
				}
				
				// ボタン（廃止の場合は操作不可）
				if(md.getIntHaisicd() == 1){
					button[0].setEnabled(false);
				}
				
			}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
			//分析情報を退避
			bunsekiDataTaihi();
//add end --------------------------------------------------------------------------------------
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("分析値入力パネルの初期化処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
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
						String event_name = e.getActionCommand();
						
						//--------------- 確認 チェックボックス クリック時の処理 -----------------------
						if ( event_name == "kakunin") {
							if(kakuninCheckbox.isSelected()){
								// 確認日テキストボックス(半角)
								kakuninhiTextbox.setText(getSysDate());
								// 確認者テキストボックス
								kakuninTextbox.setText(DataCtrl.getInstance().getUserMstData().getStrUsernm());
								//ID格納
								kakuninId = DataCtrl.getInstance().getUserMstData().getDciUserid();
								
							}else{
								// 確認日テキストボックス(半角)
								kakuninhiTextbox.setText(null);
								// 確認者テキストボックス
								kakuninTextbox.setText(null);
								//ID格納
								kakuninId = null;
							}
						}
						
					}catch(Exception ec){
						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("分析値入力パネルのActionListenerイベントが失敗しました");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
						
					}
				}
			}
		);
	}
	
	/************************************************************************************
	 * 
	 * 登録処理
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	public void toroku_bunseki() throws ExceptionBase{
		try{
			//登録（JW820）
			if(sinkiFg){
				//自動採番（J010）
//				String newCd = conJ010();
//				if(newCd != null){
//					//0埋め処理
//					for(int i=newCd.length(); i<5; i++){
//						newCd = "0"+newCd;
//					}
//					newCd = "N" + newCd;			
//				}

				//新規
				String genryo_cd = conJW820("new","0");
				genryoCdTextbox.setText(genryo_cd);
				
				//登録ボタン編集不可
				this.button[0].setEnabled(false);

			}else{
//del start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
//				//確認されているデータの場合
//				if(kakuninFg){
//					
//					//確認内容を初期化
//					kakuninCheckbox.setSelected(false);
//					kakuninhiTextbox.setText(null);
//					kakuninTextbox.setText(null);
//					
//					//確認チェックフラグをfalse
//					kakuninFg = false;
//				
//				}
//				//確認されていないデータの場合
//				else{
//					
//					//確認チェックボックスがチェックされている場合
//					if(kakuninCheckbox.isSelected()){
//						
//						//確認チェックフラグをtrue
//						kakuninFg = true;
//						
//					}
//					//確認チェックボックスがチェックされていない場合
//					else{
//						
//						//何もしない
//						
//					}
//					
//				}
//del end --------------------------------------------------------------------------------------
				
				//編集
				conJW820(genryoCdTextbox.getText(),"1");
				
				if (kakuninCheckbox.isSelected()){
					// 確認日テキストボックス(半角)
					kakuninhiTextbox.setText(DataCtrl.getInstance().getTrialTblData().getSysDate());
					// 確認者テキストボックス
					kakuninTextbox.setText(DataCtrl.getInstance().getUserMstData().getStrUsernm());
				}
				else{
					// 入力日テキストボックス(半角)
					nyuryokuhiTextbox.setText(DataCtrl.getInstance().getTrialTblData().getSysDate());
					// 入力者テキストボックス
					nyuryokushaTextbox.setText(DataCtrl.getInstance().getUserMstData().getStrUsernm());
				}

				
			}

		}catch(ExceptionBase eb){
			throw eb;
		}catch(Exception ec){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("分析値入力パネルの登録処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;
		}
		
	}
	
	/************************************************************************************
	 * 
	 * 選択会社名の取得
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private String getSelectKaishaNm(int cd) throws ExceptionBase {
		int i;
		String ret = null;
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
				if ( kaishaCd.equals(Integer.toString(cd)) ) {
					ret = kaishaNm;
				}
			}
		}catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("分析値入力パネルの会社名取得処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
		return ret;
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
			ex.setStrErrmsg("分析値入力パネルの選択会社コード取得処理が失敗しました");
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
	 *   試作コード自動採番　XML通信処理（J010）
	 *    :  試作コード自動採番処理XMLデータ通信（J010）を行う
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	private String conJ010() throws ExceptionBase{
		String ret = null;
		try{
			//--------------------------- 送信パラメータ格納  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			
			//--------------------------- 送信XMLデータ作成  ---------------------------------
			xmlJ010 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//------------------------------- Root追加  ------------------------------------
			xmlJ010.AddXmlTag("","J010");
			arySetTag.clear();
			
			//------------------------- 機能ID追加（USERINFO）  ------------------------------
			xmlJ010.AddXmlTag("J010", "USERINFO");
			
			//----------------------------- テーブルタグ追加  ---------------------------------
			xmlJ010.AddXmlTag("USERINFO", "table");
			
			//------------------------------ レコード追加  -----------------------------------
			//処理区分
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//ユーザID
			arySetTag.add(new String[]{"id_user",strUser});
			//XMLへレコード追加
			xmlJ010.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();
			
			//--------------------------- 機能ID追加（自動採番）  -----------------------------
			xmlJ010.AddXmlTag("J010", "SA410");
			//　テーブルタグ追加
			xmlJ010.AddXmlTag("SA410", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "cd_genryo"});
			xmlJ010.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();
			
			//----------------------------------- XML送信  ----------------------------------
			//System.out.println("J010送信XML===============================================================");
			//xmlJ010.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJ010);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJ010 = xcon.getXdocRes();
			//System.out.println();
			//System.out.println("J010受信XML===============================================================");
			//xmlJ010.dispXml();
			
			//　テストXMLデータ
			//xmlJ010 = new XmlData(new File("src/main/J010.xml"));
			
			//--------------------------------- 原料コード取得  -------------------------------
			//Resultチェック
			DataCtrl.getInstance().getResultData().setResultData(xmlJ010);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
				//機能IDの設定
				String strKinoId = "SA410";
				
				//全体配列取得
				ArrayList userData = xmlJ010.GetAryTag(strKinoId);
				
				//機能配列取得
				ArrayList kinoData = (ArrayList)userData.get(0);

				//テーブル配列取得
				ArrayList tableData = (ArrayList)kinoData.get(1);

				//レコード取得
				for(int i=1; i<tableData.size(); i++){
					//　１レコード取得
					ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
					for(int j=0; j<recData.size(); j++){
						//　項目名取得
						String recNm = ((String[])recData.get(j))[1];
						//　項目値取得
						String recVal = ((String[])recData.get(j))[2];
						//　試作コード
						if ( recNm == "new_code" ) {
							ret = recVal;
						}
					}
				}
			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("分析値入力パネルの試作コード自動採番通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
		return ret;
	}
	
	/************************************************************************************
	 * 
	 *   登録処理用　XML通信処理（JW820）
	 *    : 登録処理XMLデータ通信（JW820）を行う
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	private String conJW820(String genryoCd,String kbn) throws ExceptionBase{
		
		String genryo_cd = "";
		
		try{
			//----------------------------- 送信パラメータ格納  -----------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
			//----------------------------- 送信XMLデータ作成  -----------------------------
			xmlJW820 = new XmlData();
			ArrayList arySetTag = new ArrayList();
			
			//--------------------------------- Root追加  ----------------------------------
			xmlJW820.AddXmlTag("","JW820");
			
			//--------------------------- 機能ID追加（USEERINFO）  --------------------------
			xmlJW820.AddXmlTag("JW820", "USERINFO");
			//　テーブルタグ追加
			xmlJW820.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW820.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();
			
			//--------------------------- 機能ID追加（分析原料登録）  -------------------------
			xmlJW820.AddXmlTag("JW820", "SA380");
			//　テーブルタグ追加
			xmlJW820.AddXmlTag("SA380", "table");
			
			arySetTag.add(new String[]{"cd_kaisha",checkNull(getSelectKaishaCd())});					//会社コード
			arySetTag.add(new String[]{"cd_genryo",checkNull(genryoCd)});				//原料コード
			arySetTag.add(new String[]{"nm_genryo",checkNull(genryoNmTextbox.getText())});				//原料名
			arySetTag.add(new String[]{"ritu_sakusan",checkNull(numTextbox[0].getText())});				//酢酸
			arySetTag.add(new String[]{"ritu_shokuen",checkNull(numTextbox[1].getText())});				//食塩
			arySetTag.add(new String[]{"ritu_sousan",checkNull(numTextbox[2].getText())});				//総酸
// MOD start 20121122 QP@20505 課題No.10
//			arySetTag.add(new String[]{"ritu_abura",checkNull(numTextbox[3].getText())});				//油含有率
			arySetTag.add(new String[]{"ritu_msg",checkNull(numTextbox[3].getText())});				//ＭＳＧ
			arySetTag.add(new String[]{"ritu_abura",checkNull(numTextbox[4].getText())});				//油含有率
// MDO end 20121122 QP@20505 課題No.10
			arySetTag.add(new String[]{"hyojian",checkNull(hyojianTextarea.getText())});					//表示案
			arySetTag.add(new String[]{"tenkabutu",checkNull(tenkabutuTextarea.getText())});				//添加物
			arySetTag.add(new String[]{"memo",checkNull(memoTextarea.getText())});						//メモ
			arySetTag.add(new String[]{"no_eiyo1",checkNull(eiyouTextbox[0].getText())});					//栄養計算1
			arySetTag.add(new String[]{"no_eiyo2",checkNull(eiyouTextbox[1].getText())});					//栄養計算2
			arySetTag.add(new String[]{"no_eiyo3",checkNull(eiyouTextbox[2].getText())});					//栄養計算3
			arySetTag.add(new String[]{"no_eiyo4",checkNull(eiyouTextbox[3].getText())});					//栄養計算4
			arySetTag.add(new String[]{"no_eiyo5",checkNull(eiyouTextbox[4].getText())});					//栄養計算5
			arySetTag.add(new String[]{"wariai1",checkNull(eiyouTextbox[5].getText())});					//割合1
			arySetTag.add(new String[]{"wariai2",checkNull(eiyouTextbox[6].getText())});					//割合2
			arySetTag.add(new String[]{"wariai3",checkNull(eiyouTextbox[7].getText())});					//割合3
			arySetTag.add(new String[]{"wariai4",checkNull(eiyouTextbox[8].getText())});					//割合4
			arySetTag.add(new String[]{"wariai5",checkNull(eiyouTextbox[9].getText())});					//割合5
			arySetTag.add(new String[]{"kbn_haishi",checkNull( (haishiCheckbox.isSelected())?1:0 )});	//廃止
			arySetTag.add(new String[]{"cd_kakutei",checkNull(kakuteiCdTextbox.getText())});			//確定コード

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
//			//確認チェックボックスがチェックされている場合
//			if(kakuninCheckbox.isSelected()){
//				
//				arySetTag.add(new String[]{"id_kakunin",checkNull(kakuninId)});								//確認者ID
//				arySetTag.add(new String[]{"dt_kakunin",checkNull(kakuninhiTextbox.getText())});		//確認日
//			}
//			//確認チェックボックスがチェックされていない場合
//			else{
//				
//				arySetTag.add(new String[]{"id_kakunin",""});		//確認者ID
//				arySetTag.add(new String[]{"dt_kakunin",""});		//確認日
//			}
			
			//確認情報退避
			boolean blnKakuninCheck = kakuninCheckbox.isSelected();
			String strKakuninDt = kakuninhiTextbox.getText();
			String strKakuninId = kakuninTextbox.getText();
			
			int fg = koshinJohoCheck();
			int henkou_fg = this.bunsekiDataHensyuCheck();
			
			if (henkou_fg == 1) {
				arySetTag.add(new String[]{"fg_henkou","true"});
			}
			else{
				arySetTag.add(new String[]{"fg_henkou","false"});
			}

			if (fg == 1) {
				//確認情報にユーザ情報を格納(ここでは判定するために1を格納)
				arySetTag.add(new String[]{"id_kakunin","1"});		//確認者ID
				arySetTag.add(new String[]{"dt_kakunin","1"});		//確認日

				arySetTag.add(new String[]{"id_toroku",checkNull(koshinId)});									//登録者ID
				arySetTag.add(new String[]{"dt_toroku",checkNull(koshinNm)});		//登録日
				
				//確認チェックフラグをtrue
				kakuninFg = true;
				
			} else if(fg == 0) {
				//確認情報にNULLを格納
				arySetTag.add(new String[]{"id_kakunin",""});		//確認者ID
				arySetTag.add(new String[]{"dt_kakunin",""});		//確認日
				
				arySetTag.add(new String[]{"id_toroku",checkNull(torokuId)});	//登録者ID
				arySetTag.add(new String[]{"dt_toroku",checkNull(nyuryokuhiTextbox.getText())});	//登録日

				//確認内容を初期化
				kakuninCheckbox.setSelected(false);
				kakuninhiTextbox.setText(null);
				kakuninTextbox.setText(null);

				//確認チェックフラグをfalse
				kakuninFg = false;
			} else{
				return genryoCd;
			}
//mod end --------------------------------------------------------------------------------------
			
			//arySetTag.add(new String[]{"id_toroku",checkNull(torokuId)});									//登録者ID
			//arySetTag.add(new String[]{"dt_toroku",checkNull(nyuryokuhiTextbox.getText())});		//登録日
			arySetTag.add(new String[]{"kbn_shori",checkNull(kbn)});										//処理区分
			
			xmlJW820.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
			
			//----------------------------------- XML送信  ----------------------------------
			System.out.println("JW820送信XML===============================================================");
			xmlJW820.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJW820);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJW820 = xcon.getXdocRes();
			//System.out.println();
			//System.out.println("JW820受信XML===============================================================");
			//xmlJW820.dispXml();
			
			//テストXMLデータ
			//xmlJW820 = new XmlData(new File("src/main/JW820.xml"));
			
			//------------------------------- Resultデータチェック -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW820);
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
				//確認内容を初期化
				kakuninCheckbox.setSelected(blnKakuninCheck);
				kakuninhiTextbox.setText(strKakuninDt);
				kakuninTextbox.setText(strKakuninId);
//add end --------------------------------------------------------------------------------------				
				throw new ExceptionBase();
			}else{
				if(haishiCheckbox.isSelected()){
					button[0].setEnabled(false);
				}
				
				
				//採番原料コード取得
				//機能IDの設定
				String strKinoId = "SA380";
				
				//全体配列取得
				ArrayList userData = xmlJW820.GetAryTag(strKinoId);
				
				//機能配列取得
				ArrayList kinoData = (ArrayList)userData.get(0);

				//テーブル配列取得
				ArrayList tableData = (ArrayList)kinoData.get(1);

				//レコード取得
				for(int i=1; i<tableData.size(); i++){
					//　１レコード取得
					ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
					for(int j=0; j<recData.size(); j++){
						//　項目名取得
						String recNm = ((String[])recData.get(j))[1];
						//　項目値取得
						String recVal = ((String[])recData.get(j))[2];
						//　原料コード
						if ( recNm == "cd_genryo" ) {
							
							genryo_cd = recVal;
							
						}
					}
				}
				
				DataCtrl.getInstance().getMessageCtrl().PrintMessageString("正常に登録処理が完了しました。");

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
				//登録した分析情報を退避
				this.bunsekiDataTaihi();
				
				if (fg == 1) {
					blnCheckKakunin = true;
					if (henkou_fg == 1) {
						// 入力日テキストボックス(半角)
						nyuryokuhiTextbox.setText(DataCtrl.getInstance().getTrialTblData().getSysDate());
						// 入力者テキストボックス
						nyuryokushaTextbox.setText(DataCtrl.getInstance().getUserMstData().getStrUsernm());
					}
				}
				else if(fg == 0){
					blnCheckKakunin = false;
				}
//add end --------------------------------------------------------------------------------------
			}

		}catch(ExceptionBase ex){
			throw ex;
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("分析値入力パネルの登録通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
		}
		
		return genryo_cd;
		
	}
	
	/**
	 * 【JW610】 会社コンボボックス値取得 送信XMLデータ作成
	 */
	private void conJW610() throws ExceptionBase{
		try{
			
			//　送信パラメータ格納
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
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
			
			xmlJW610.dispXml();
			//　XML送信
			XmlConnection xmlConnection = new XmlConnection(xmlJW610);
			xmlConnection.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xmlConnection.XmlSend();
			//　XML受信
			xmlJW610 = xmlConnection.getXdocRes();
			xmlJW610.dispXml();
			
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
			ex.setStrErrmsg("分析値入力パネルの会社コンボボックス値取得通信処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
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
			ex.setStrErrmsg("分析値入力パネルの会社コンボボックス設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.13
	/**
	 * 分析情報編集退避
	 * @throws ExceptionBase
	 */
	private void bunsekiDataTaihi() throws ExceptionBase {
		
		try {
			//画面情報
			strGenryoNmOld = genryoNmTextbox.getText();						//原料名
			strSakusanOld = numTextbox[0].getText();						//酢酸
			strShokuenOld = numTextbox[1].getText();						//食塩
			strSousanOld = numTextbox[2].getText();							//総酸
// MOD start 20121122 QP@20505 課題No.10
//			strGanyuOld = numTextbox[3].getText();							//油含有率
			strMsgOld = numTextbox[3].getText();							//MSG
			strGanyuOld = numTextbox[4].getText();							//油含有率
// MOD end 20121122 QP@20505 課題No.10
			strHyojiOld = hyojianTextarea.getText();						//表示案
			strTankaOld = tenkabutuTextarea.getText();						//添加物
			strMemoOld = memoTextarea.getText();							//メモ
			strEiyonoOld[0] = eiyouTextbox[0].getText();					//栄養計算1
			strEiyonoOld[1] = eiyouTextbox[1].getText();					//栄養計算2
			strEiyonoOld[2] = eiyouTextbox[2].getText();					//栄養計算3
			strEiyonoOld[3] = eiyouTextbox[3].getText();					//栄養計算4
			strEiyonoOld[4] = eiyouTextbox[4].getText();					//栄養計算5
			strWariaiOld[0] = eiyouTextbox[5].getText();					//割合1
			strWariaiOld[1] = eiyouTextbox[6].getText();					//割合2
			strWariaiOld[2] = eiyouTextbox[7].getText();					//割合3
			strWariaiOld[3] = eiyouTextbox[8].getText();					//割合4
			strWariaiOld[4] = eiyouTextbox[9].getText();					//割合5
			strKakuteiCdOld = kakuteiCdTextbox.getText();					//確定コード

		}catch(Exception ec){	
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("分析情報編集チェック処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			
		}finally{
			
		}
		
	}
	
	/**
	 * 分析情報編集チェック
	 * @return 1:編集時, 0:未編集時
	 * @throws ExceptionBase
	 */
	private int bunsekiDataHensyuCheck() throws ExceptionBase {
		
		try {
			//現在の画面情報
			String strGenryoNmNew = genryoNmTextbox.getText();					//原料名
			String strSakusanNew = numTextbox[0].getText();						//酢酸
			String strShokuenNew = numTextbox[1].getText();						//食塩
			String strSousanNew = numTextbox[2].getText();						//総酸
// MDO start 20121122 QP@20505 課題No.10
//			String strGanyuNew = numTextbox[3].getText();						//油含有率
			String strMsgNew = numTextbox[3].getText();						//ＭＳＧ
			String strGanyuNew = numTextbox[4].getText();						//油含有率
// MOD end 20121122 QP@20505 課題No.10
			String strHyojiNew = hyojianTextarea.getText();						//表示案
			String strTankaNew = tenkabutuTextarea.getText();					//添加物
			String strMemoNew = memoTextarea.getText();							//メモ
			String strEiyono1New = eiyouTextbox[0].getText();					//栄養計算1
			String strEiyono2New = eiyouTextbox[1].getText();					//栄養計算2
			String strEiyono3New = eiyouTextbox[2].getText();					//栄養計算3
			String strEiyono4New = eiyouTextbox[3].getText();					//栄養計算4
			String strEiyono5New = eiyouTextbox[4].getText();					//栄養計算5
			String strWariai1New = eiyouTextbox[5].getText();					//割合1
			String strWariai2New = eiyouTextbox[6].getText();					//割合2
			String strWariai3New = eiyouTextbox[7].getText();					//割合3
			String strWariai4New = eiyouTextbox[8].getText();					//割合4
			String strWariai5New = eiyouTextbox[9].getText();					//割合5
			String strKakuteiCdNew = kakuteiCdTextbox.getText();				//確定コード

			//編集チェック : 編集がある場合、1を返却して処理終了
			if (!strGenryoNmNew.equals(strGenryoNmOld)) {
				return 1;
			} else if (!strSakusanNew.equals(strSakusanOld)) {
				return 1;
			} else if (!strShokuenNew.equals(strShokuenOld)) {
				return 1;
			} else if (!strSousanNew.equals(strSousanOld)) {
				return 1;
			} else if (!strGanyuNew.equals(strGanyuOld)) {
				return 1;
// ADD start 20121122 QP@20505 課題No.10
			} else if (!strMsgNew.equals(strMsgOld)) {
				return 1;
// ADD end 20121122 QP@20505 課題No.10
			} else if (!strHyojiNew.equals(strHyojiOld)) {
				return 1;
			} else if (!strTankaNew.equals(strTankaOld)) {
				return 1;
			} else if (!strMemoNew.equals(strMemoOld)) {
				return 1;
			} else if (!strEiyono1New.equals(strEiyonoOld[0])) {
				return 1;
			} else if (!strEiyono2New.equals(strEiyonoOld[1])) {
				return 1;
			} else if (!strEiyono3New.equals(strEiyonoOld[2])) {
				return 1;
			} else if (!strEiyono4New.equals(strEiyonoOld[3])) {
				return 1;
			} else if (!strEiyono5New.equals(strEiyonoOld[4])) {
				return 1;
			} else if (!strWariai1New.equals(strWariaiOld[0])) {
				return 1;
			} else if (!strWariai2New.equals(strWariaiOld[1])) {
				return 1;
			} else if (!strWariai3New.equals(strWariaiOld[2])) {
				return 1;
			} else if (!strWariai4New.equals(strWariaiOld[3])) {
				return 1;
			} else if (!strWariai5New.equals(strWariaiOld[4])) {
				return 1;
			} else if (!strKakuteiCdNew.equals(strKakuteiCdOld)) {
				return 1;
			}
			
		}catch(Exception ec){	
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("分析情報編集チェック処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			
		}finally{
			
		}
		
		return 0;
		
	}
	
	/**
	 * 更新情報確認
	 * @return 0:NULL格納, 1:確認情報にユーザ情報を格納
	 * @throws ExceptionBase
	 */
	private int koshinJohoCheck() throws ExceptionBase {
		int retFlg = 0;

		try {
			//確認チェック
			if (blnCheckKakunin) {
				
				if(kakuninCheckbox.isSelected()){
					//分析データ編集チェック
					if (this.bunsekiDataHensyuCheck() == 1) {
						//分析データが編集されている場合
						
						//ダイアログコンポーネント設定
						JOptionPane jp = new JOptionPane();
										
						//確認ダイアログ表示
						int option = JOptionPane.showConfirmDialog(
								jp.getRootPane(),
								"確認済みデータのため確認者をクリアして保存しますがよろしいでしょうか？"
								, "確認メッセージ"
								,JOptionPane.YES_NO_OPTION
								,JOptionPane.PLAIN_MESSAGE
							);
						
						//YES・NO判定
					    if (option == JOptionPane.YES_OPTION){
					    	//「はい」押下
					    	retFlg = 0;				    	
					    } else {
					    	//「いいえ」押下
					    	retFlg = 2;				    	
					    }
					} else {
						//分析データが未編集の場合
				    	retFlg = 1;
					}
				}
				else{
					retFlg = 0;	
				}
			} else {
				if(kakuninCheckbox.isSelected()){
					retFlg = 1;
				}
				else{
					retFlg = 0;	
				}
			}
	
		}catch(Exception ec){	
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("更新情報確認処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			
		}finally{
			
		}
		
		return retFlg;
		
	}
//add end --------------------------------------------------------------------------------------
	
	/**
	 * ボタン ゲッター
	 */
	public ButtonBase[] getButton() {
		return button;
	}
	
	/**
	 * システム日付 ゲッター
	 * @return システム日付の値を返す
	 */
	public String getSysDate(){
		return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
	}
	
	/**
	 * NULLチェック処理（オブジェクト）
	 * @throws ExceptionBase
	 */
	private String checkNull(Object val){
		String ret = "";
		if(val != null){
			ret = val.toString();
		}
		return ret;
	}
	
	/**
	 * NULLチェック処理（数値）
	 * @throws ExceptionBase
	 */
	private String checkNull(int val){
		String ret = "";
		if(val >= 0){
			ret = Integer.toString(val);
		}
		return ret;
	}

}