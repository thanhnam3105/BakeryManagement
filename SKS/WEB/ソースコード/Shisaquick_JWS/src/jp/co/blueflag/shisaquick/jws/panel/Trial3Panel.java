package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.CostMaterialData;
import jp.co.blueflag.shisaquick.jws.base.EigyoTantoData;
import jp.co.blueflag.shisaquick.jws.base.KaishaData;
import jp.co.blueflag.shisaquick.jws.base.LiteralData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.InputComboBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.common.TextAreaBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.disp.EigyoTantoSearchDisp;
import jp.co.blueflag.shisaquick.jws.label.ItemIndicationLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/**
 * 
 * 基本情報(試作表③)パネルクラス
 * 
 */
public class Trial3Panel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private Color Yellow = JwsConstManager.SHISAKU_ITEM_COLOR2;
	
	private ComboBase cmbKaisha;			//担当会社コンボボックス
	private ComboBase cmbKojo;				//担当工場コンボボックス
	private ComboBase cmbShosu;			//小数指定コンボボックス
	private TextboxBase txtGroup;
	private TextboxBase txtTeam;
	private TextboxBase txtIrisu;
	private InputComboBase cmbYoryo;
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.26
//	private TextboxBase txtBaikibou;
	private NumelicTextbox txtBaikibou;
//mod end --------------------------------------------------------------------------------------
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
	private ComboBase cmbtani;
//add end   -------------------------------------------------------------------------------
	
	//【QP@00342】
	TextboxBase txtEigyo;
	
	//会社コンボボックス ActionCommand値
	private final String kaishaCommand = "kaishaCmb_click";
	
	private int l_labelx = 15;						//まとめラベルX位置（開始点）
	private int l_labelw = 20;						//まとめラベル幅
	private int labelx = l_labelx + l_labelw - 1;	//ラベルX位置
	private int labely = 5;							//ラベルY位置（開始点）
	private int labelw = 170;						//ラベル幅
	private int labelh = 20;							//ラベル高さ
	private int mg_hgt = labelh - 1;				//ラベル間隔
	private int ctrlx  = labelx + labelw;			//コントロールX位置
	private int ctrlw  = 275;						//コントロール幅
	private int ctrlh  = labelh + 1;					//コントロール高さ
	private int sogo_labelx = labelx + labelw + ctrlw + 10;	//総合メモラベルX位置
	private int sogo_labely = labely;				//総合メモラベルY位置
	private int sogo_labelw = 500;				//総合メモラベル幅
	private int sogo_labelh = labelh;				//総合メモラベル高さ
	private int sogo_ctrlh = 306;					//総合メモコントロール高さ
	private int bikouw = 15;
	private int bikouh = 15;
	private int tani = 50;
		
	private PrototypeData PrototypeData;

	//【QP@00342】
	private EigyoTantoSearchDisp et = new EigyoTantoSearchDisp("担当営業検索");
	
	private XmlConnection xcon;
	private XmlData xmlJW620;
	private ExceptionBase ex;
	
	/**
	 * 
	 * 基本情報(試作表③)パネルクラス　コンストラクタ
	 * @throws ExceptionBase 
	 * 
	 */
	public Trial3Panel() throws ExceptionBase {
		super();

		try {
			//試作品データ保持クラスの生成
			PrototypeData = new PrototypeData();
			PrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
	
			//データ設定（JW020）
			this.conJW620(Integer.toString(PrototypeData.getIntKaishacd()));
			
			//【QP@00342】
			et.setVisible(false);
			
			//画面表示
			this.panelDisp();
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表③パネルクラス　コンストラクタ処理が失敗しました");
			ex.setStrErrmsg("基本情報パネルクラス　コンストラクタ処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;
		} finally {
		}		
	}
	
	/**
	 * 
	 * 基本情報(試作表③)パネルクラス　画面表示
	 * @throws ExceptionBase 
	 * 
	 */
	private void panelDisp() throws ExceptionBase{
		String strLiteralCd = "";

		//計算項目用文字列 : (計算)　と表示する
		String strKeisan = " (" + JwsConstManager.JWS_MARK_0005 + ")";
								
		try {
			this.setLayout(null);
			this.setBackground(Color.WHITE);
			
			//項目ラベル設定（試作表グループ）
			ItemLabel hlShisaku = new ItemLabel();
			hlShisaku.setBorder(new LineBorder(Color.BLACK, 1));
			hlShisaku.setHorizontalAlignment(SwingConstants.CENTER);
			hlShisaku.setBounds(l_labelx, labely, l_labelw, (labelh*8)-7);
			hlShisaku.setText("<html>試<br>作<br>表");
			this.add(hlShisaku);
			
			//所属グループ
			ItemLabel hlGroup = new ItemLabel();
			hlGroup.setBorder(new LineBorder(Color.BLACK, 1));
			hlGroup.setBounds(labelx, labely, labelw, labelh);
			hlGroup.setText("<html><font color=\"red\">必須</font>　所属グループ");
			this.add(hlGroup);
			
				//所属グループ用テキストボックス
				txtGroup = new TextboxBase();
				txtGroup.setBackground(Color.WHITE);
				txtGroup.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtGroup.setText(PrototypeData.getStrGroupNm());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0092, DataCtrl.getInstance().getParamData().getStrMode())){
					txtGroup.setBackground(Color.lightGray);
					txtGroup.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtGroup.setEditable(false);
				}else{
					txtGroup.addFocusListener(new FocusCheck("所属グループ"));
				}
				this.add(txtGroup);
			
			//所属チーム
			labely = labely + mg_hgt;
			ItemLabel hlTeam = new ItemLabel();
			hlTeam.setBorder(new LineBorder(Color.BLACK, 1));
			hlTeam.setBounds(labelx, labely, labelw, labelh);
			hlTeam.setText("<html><font color=\"red\">必須</font>　所属チーム");
			this.add(hlTeam);
	
				//所属チーム用テキストボックス
				txtTeam = new TextboxBase();
				txtTeam.setBackground(Color.WHITE);
				txtTeam.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtTeam.setText(PrototypeData.getStrTeamNm());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0093, DataCtrl.getInstance().getParamData().getStrMode())){
					txtTeam.setBackground(Color.lightGray);
					txtTeam.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtTeam.setEditable(false);
				}else{
					txtTeam.addFocusListener(new FocusCheck("所属チーム"));
				}
				this.add(txtTeam);
			
			//一括表示
			labely = labely + mg_hgt;
			ItemLabel hlIkatu = new ItemLabel();
			hlIkatu.setBorder(new LineBorder(Color.BLACK, 1));
			hlIkatu.setBounds(labelx, labely, labelw, labelh);
			hlIkatu.setText("　　　一括表示");
			this.add(hlIkatu);
			
				//一括表示用コンボボックス生成
				ComboBase cmbIkatu = new ComboBase();
				cmbIkatu.setBackground(Color.WHITE);
				cmbIkatu.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrIkatu();
				setLiteralCmb(cmbIkatu, DataCtrl.getInstance().getLiteralDataIkatu(), strLiteralCd, 0);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0094, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbIkatu.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbIkatu.setEnabled(false);
				}else{
					cmbIkatu.addFocusListener(new FocusCheck("一括表示"));
				}
				//パネルに追加
				this.add(cmbIkatu);
			
			//ジャンル
			labely = labely + mg_hgt;
			ItemLabel hlZyanru = new ItemLabel();
			hlZyanru.setBorder(new LineBorder(Color.BLACK, 1));
			hlZyanru.setBounds(labelx, labely, labelw, labelh);
			hlZyanru.setText("　　　ジャンル");
			this.add(hlZyanru);
	
				//ジャンル用コンボボックス生成
				ComboBase cmbZyanru = new ComboBase();
				cmbZyanru.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrZyanru();
				setLiteralCmb(cmbZyanru, DataCtrl.getInstance().getLiteralDataZyanru(), strLiteralCd, 0);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0095, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbZyanru.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbZyanru.setEnabled(false);
				}else{
					cmbZyanru.addFocusListener(new FocusCheck("ジャンル"));
				}
				//パネルに追加
				this.add(cmbZyanru);
	
			//ユーザ
			labely = labely + mg_hgt;
			ItemLabel hlUser = new ItemLabel();
			hlUser.setBorder(new LineBorder(Color.BLACK, 1));
			hlUser.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlUser.setText("　　　ユーザ");
			hlUser.setText("<html><font color=\"red\">必須</font>　ユーザ");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlUser);
	
				//ユーザ用コンボボックス生成
				ComboBase cmbUser = new ComboBase();
				cmbUser.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrUsercd();
				setLiteralCmb(cmbUser, DataCtrl.getInstance().getLiteralDataUser(), strLiteralCd, 0);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0096, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbUser.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbUser.setEnabled(false);
				}else{
					cmbUser.addFocusListener(new FocusCheck("ユーザ"));
				}
				//パネルに追加
				this.add(cmbUser);
			
			//特徴原料
			labely = labely + mg_hgt;
			ItemLabel hlTokutyo = new ItemLabel();
			hlTokutyo.setBorder(new LineBorder(Color.BLACK, 1));
			hlTokutyo.setBounds(labelx, labely, labelw, labelh);
			hlTokutyo.setText("　　　特徴原料");
			this.add(hlTokutyo);
	
				//特徴原料用コンボボックス生成
				InputComboBase cmbTokutyo = new InputComboBase();
				cmbTokutyo.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrTokutyo();
				setLiteralCmb(cmbTokutyo, DataCtrl.getInstance().getLiteralDataTokutyo(), strLiteralCd, 1);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0097, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbTokutyo.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbTokutyo.setEditable(false);
					cmbTokutyo.setEnabled(false);
					
				}else{
					cmbTokutyo.getEditor().getEditorComponent().addFocusListener(new FocusCheck("特徴原料"));
				}
				//パネルに追加
				this.add(cmbTokutyo);
			
			//用途
			labely = labely + mg_hgt;
			ItemLabel hlYoto = new ItemLabel();
			hlYoto.setBorder(new LineBorder(Color.BLACK, 1));
			hlYoto.setBounds(labelx, labely, labelw, labelh);
			hlYoto.setText("　　　用途");
			this.add(hlYoto);
	
				//用途用コンボボックス生成
				InputComboBase cmbYoto = new InputComboBase();
				cmbYoto.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrYoto();
				setLiteralCmb(cmbYoto, DataCtrl.getInstance().getLiteralDataYoto(), strLiteralCd, 1);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0098, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbYoto.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbYoto.setEditable(false);
					cmbYoto.setEnabled(false);
				}else{
					cmbYoto.getEditor().getEditorComponent().addFocusListener(new FocusCheck("用途"));
				}
				//パネルに追加
				this.add(cmbYoto);
			
			//価格帯
			labely = labely + mg_hgt;
			ItemLabel hlKakaku = new ItemLabel();
			hlKakaku.setBorder(new LineBorder(Color.BLACK, 1));
			hlKakaku.setBounds(labelx, labely, labelw, labelh);
			hlKakaku.setText("　　　価格帯");
			this.add(hlKakaku);
	
				//価格帯用コンボボックス生成
				ComboBase cmbKakaku = new ComboBase();
				cmbKakaku.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrKakaku();
				setLiteralCmb(cmbKakaku, DataCtrl.getInstance().getLiteralDataKakaku(), strLiteralCd, 0);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0099, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbKakaku.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbKakaku.setEnabled(false);
				}else{
					cmbKakaku.addFocusListener(new FocusCheck("価格帯"));
				}
				//パネルに追加
				this.add(cmbKakaku);
			
			//項目ラベル設定（オプション）
			labely = labely + mg_hgt;
			ItemLabel hlOption = new ItemLabel();
			hlOption.setBorder(new LineBorder(Color.BLACK, 1));
			hlOption.setHorizontalAlignment(SwingConstants.CENTER);
			hlOption.setBounds(l_labelx, labely, l_labelw, (labelh*2)-1);
			this.add(hlOption);
			
			//種別
			ItemLabel hlShubetu = new ItemLabel();
			hlShubetu.setBorder(new LineBorder(Color.BLACK, 1));
			hlShubetu.setBounds(labelx, labely, labelw, labelh);
			hlShubetu.setText("<html><font color=\"red\">必須</font>　種別");
			this.add(hlShubetu);
			
				//種別用コンボボックス生成
				ComboBase cmbShubetu = new ComboBase();
				cmbShubetu.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrShubetu();
				setLiteralCmb(cmbShubetu, DataCtrl.getInstance().getLiteralDataShubetu(), strLiteralCd, 0);		//必須
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0100, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbShubetu.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbShubetu.setEnabled(false);
				}else{
					cmbShubetu.addFocusListener(new FocusCheck("種別"));
				}
				//パネルに追加
				this.add(cmbShubetu);
			
			//小数指定
			labely = labely + mg_hgt;
			ItemLabel hlShosu = new ItemLabel();
			hlShosu.setBorder(new LineBorder(Color.BLACK, 1));
			hlShosu.setBounds(labelx, labely, labelw, labelh);
			hlShosu.setText("　　　小数指定");
			this.add(hlShosu);
			
				//小数指定用コンボボックス生成
				cmbShosu = new ComboBase();
				cmbShosu.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd =PrototypeData.getStrShosu();
				if(strLiteralCd != null && strLiteralCd.length()>0){
					for(int i=strLiteralCd.length(); i<3; i++){
						strLiteralCd = "0" + strLiteralCd;
					}
				}
				setLiteralCmb(cmbShosu, DataCtrl.getInstance().getLiteralDataShosu(), strLiteralCd, 0);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0101, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbShosu.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbShosu.setEnabled(false);
				}
				//パネルに追加
				this.add(cmbShosu);
			
			//項目ラベル設定（原価試算表）
			labely = labely + mg_hgt;
			ItemLabel hlGenkahyo = new ItemLabel();
			hlGenkahyo.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenkahyo.setHorizontalAlignment(SwingConstants.CENTER);
			hlGenkahyo.setBounds(l_labelx, labely, l_labelw, (labelh*20)-19);
			hlGenkahyo.setText("<html>原<br>価<br>試<br>算<br>表");
			this.add(hlGenkahyo);
			
			//担当会社
			ItemLabel hlKaisha = new ItemLabel();
			hlKaisha.setBorder(new LineBorder(Color.BLACK, 1));
			hlKaisha.setBounds(labelx, labely, labelw, labelh);
			hlKaisha.setText("<html><font color=\"red\">必須</font>　工場　担当会社");
			this.add(hlKaisha);
	
				//担当会社用コンボボックス生成
				cmbKaisha = new ComboBase();
				cmbKaisha.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				setKaishaCmb(cmbKaisha);
				//イベントクラスの設定
				cmbKaisha.addActionListener(this.getActionEvent());
				cmbKaisha.setActionCommand(kaishaCommand);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0102, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbKaisha.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbKaisha.setEnabled(false);
				}
				//パネルに追加
				this.add(cmbKaisha);
			
			//担当工場
			labely = labely + mg_hgt;
			ItemLabel hlKojo = new ItemLabel();
			hlKojo.setBorder(new LineBorder(Color.BLACK, 1));
			hlKojo.setBounds(labelx, labely, labelw, labelh);
			hlKojo.setText("<html><font color=\"red\">必須</font>　工場　担当工場");
			this.add(hlKojo);
	
				//担当工場用コンボボックス生成
				cmbKojo = new ComboBase();
				cmbKojo.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				setBushoCmb(cmbKojo);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0103, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbKojo.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbKojo.setEnabled(false);
				}
				//パネルに追加
				this.add(cmbKojo);
			
			//担当営業
			//【QP@00342】
			labely = labely + mg_hgt;
			ItemLabel hlEigyo = new ItemLabel();
			hlEigyo.setBorder(new LineBorder(Color.BLACK, 1));
			hlEigyo.setBounds(labelx, labely, labelw-50, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlEigyo.setText("　　　担当営業");
			hlEigyo.setText("<html><font color=\"red\">必須</font>　担当営業");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlEigyo);
			
			//担当営業検索ボタン
			ButtonBase btnSearchEigyo = new ButtonBase();
			btnSearchEigyo.setBounds(labelx+119, labely, 51, labelh);
			btnSearchEigyo.setHorizontalAlignment(SwingConstants.CENTER);
			btnSearchEigyo.addActionListener(this.getActionEvent());
			btnSearchEigyo.setActionCommand("btnSerchEigyo");
			btnSearchEigyo.setMargin(new Insets(0, 0, 0, 0));
			btnSearchEigyo.setText("検索");
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0133, DataCtrl.getInstance().getParamData().getStrMode())){
				btnSearchEigyo.setEnabled(false);
			}
			this.add(btnSearchEigyo);
			
			//担当営業用テキストボックス生成
			txtEigyo = new TextboxBase();
			//txtEigyo.setBackground(Yellow);
			txtEigyo.setBounds(ctrlx, labely, ctrlw, ctrlh);
			txtEigyo.setText(PrototypeData.getStrNmEigyoTanto());
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0104, DataCtrl.getInstance().getParamData().getStrMode())){
				txtEigyo.setBackground(Color.lightGray);
				txtEigyo.setBounds(ctrlx, labely, ctrlw-1, ctrlh-2);
				txtEigyo.setEditable(false);
			}else{
				txtEigyo.setEditable(false);
				//txtEigyo.addFocusListener(new FocusCheck("担当営業"));
			}
			this.add(txtEigyo);
			
//			labely = labely + mg_hgt;
//			ItemLabel hlEigyo = new ItemLabel();
//			hlEigyo.setBorder(new LineBorder(Color.BLACK, 1));
//			hlEigyo.setBounds(labelx, labely, labelw, labelh);
//			hlEigyo.setText("　　　担当営業");
//			this.add(hlEigyo);
//			
//				//担当営業用コンボボックス生成
//				InputComboBase cmbEigyo = new InputComboBase();
//				cmbEigyo.setBounds(ctrlx, labely, ctrlw, ctrlh);
//				//コンボボックスに値を設定
//				strLiteralCd = PrototypeData.getStrTantoEigyo();
//				setLiteralCmb(cmbEigyo, DataCtrl.getInstance().getLiteralDataTanto(), strLiteralCd, 1);
//				//モード編集
//				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
//						JwsConstManager.JWS_COMPONENT_0104, DataCtrl.getInstance().getParamData().getStrMode())){
//					cmbEigyo.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
//					cmbEigyo.setEditable(false);
//					cmbEigyo.setEnabled(false);
//				}else{
//					cmbEigyo.getEditor().getEditorComponent().addFocusListener(new FocusCheck("担当営業"));
//				}
//				//パネルに追加
//				this.add(cmbEigyo);
			
			
			//製造方法
			labely = labely + mg_hgt;
			ItemLabel hlSeizo = new ItemLabel();
			hlSeizo.setBorder(new LineBorder(Color.BLACK, 1));
			hlSeizo.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlSeizo.setText("　　　製造方法");
			hlSeizo.setText("<html><font color=\"red\">必須</font>　製造方法");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlSeizo);
			
				//製造方法用コンボボックス生成
				ComboBase cmbSeizo = new ComboBase();
				cmbSeizo.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrSeizocd();
				setLiteralCmb(cmbSeizo, DataCtrl.getInstance().getLiteralDataSeizo(), strLiteralCd, 0);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0105, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbSeizo.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbSeizo.setEnabled(false);
				}else{
					cmbSeizo.addFocusListener(new FocusCheck("製造方法"));
				}
				//パネルに追加
				this.add(cmbSeizo);
			
			//充填方法
			labely = labely + mg_hgt;
			ItemLabel hlZyuten = new ItemLabel();
			hlZyuten.setBorder(new LineBorder(Color.BLACK, 1));
			hlZyuten.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlZyuten.setText("　　　充填方法");
			hlZyuten.setText("<html><font color=\"red\">必須</font>　充填方法");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlZyuten);
			
				//充填方法用コンボボックス生成
				ComboBase cmbZyuten = new ComboBase();
				cmbZyuten.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrZyutencd();
				setLiteralCmb(cmbZyuten, DataCtrl.getInstance().getLiteralDataZyuten(), strLiteralCd, 0);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0106, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbZyuten.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbZyuten.setEnabled(false);
				}else{
					cmbZyuten.addFocusListener(new FocusCheck("充填方法"));
				}
				//パネルに追加
				this.add(cmbZyuten);
			
			//殺菌方法
			labely = labely + mg_hgt;
			ItemLabel hlSakin = new ItemLabel();
			hlSakin.setBorder(new LineBorder(Color.BLACK, 1));
			hlSakin.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlSakin.setText("　　　殺菌方法");
			hlSakin.setText("<html><font color=\"red\">必須</font>　殺菌方法");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlSakin);
			
				//殺菌方法用コンボボックス生成
				InputComboBase cmbSakin = new InputComboBase();
				cmbSakin.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrSakin();
				setLiteralCmb(cmbSakin, DataCtrl.getInstance().getLiteralDataSakin(), strLiteralCd, 1);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0107, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbSakin.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbSakin.setEditable(false);
					cmbSakin.setEnabled(false);
				}else{
					cmbSakin.getEditor().getEditorComponent().addFocusListener(new FocusCheck("殺菌方法"));
				}		
				//パネルに追加
				this.add(cmbSakin);
			
			//容器・包材
			labely = labely + mg_hgt;
			ItemLabel hlYoki = new ItemLabel();
			hlYoki.setBorder(new LineBorder(Color.BLACK, 1));
			hlYoki.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlYoki.setText("　　　容器・包材");
			hlYoki.setText("<html><font color=\"red\">必須</font>　容器・包材");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlYoki);
			
				//容器・包材用コンボボックス生成
				InputComboBase cmbYoki = new InputComboBase();
				cmbYoki.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrYokihozai();
				setLiteralCmb(cmbYoki, DataCtrl.getInstance().getLiteralDataYoki(), strLiteralCd, 1);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0108, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbYoki.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbYoki.setEditable(false);
					cmbYoki.setEnabled(false);
				}else{
					cmbYoki.getEditor().getEditorComponent().addFocusListener(new FocusCheck("容器包材"));
				}
				//パネルに追加
				this.add(cmbYoki);
			
			//容量
			labely = labely + mg_hgt;
			ItemLabel hlYoryo = new ItemLabel();
			hlYoryo.setBorder(new LineBorder(Color.BLACK, 1));
			hlYoryo.setBounds(labelx, labely, labelw, labelh);
			hlYoryo.setText("<html><font color=\"red\">必須</font>　容量（数値入力）" + strKeisan);
			this.add(hlYoryo);
			
				//容量用コンボボックス生成
				cmbYoryo = new InputComboBase();
				cmbYoryo.setBounds(ctrlx, labely, ctrlw-tani+2, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrYoryo();
				setLiteralCmb(cmbYoryo, DataCtrl.getInstance().getLiteralDataYoryo(), strLiteralCd, 1);		//必須
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0109, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbYoryo.setEditable(false);
					cmbYoryo.setEnabled(false);
				}else{
					cmbYoryo.getEditor().getEditorComponent().addFocusListener(new FocusCheck("容量"));
				}
				//パネルに追加
				this.add(cmbYoryo);
				
				//単位用コンボボックス生成
				cmbtani = new ComboBase();
				cmbtani.setBounds(ctrlx+ctrlw-tani, labely, tani, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrTani();
				setLiteralCmbYoryo(cmbtani, DataCtrl.getInstance().getLiteralDataTani(), strLiteralCd, 0);			//必須
				//cmbtani.addFocusListener(new FocusCheck("容量単位"));
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0110, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbtani.setEnabled(false);
				}else{
					//cmbtani.addFocusListener(new FocusCheck("容量単位"));
				}
				//パネルに追加
				this.add(cmbtani);
			
			//入り数
			labely = labely + mg_hgt;
			ItemLabel hlIrisu = new ItemLabel();
			hlIrisu.setBorder(new LineBorder(Color.BLACK, 1));
			hlIrisu.setBounds(labelx, labely, labelw, labelh);
			hlIrisu.setText("<html><font color=\"red\">必須</font>　入り数" + strKeisan);
			this.add(hlIrisu);
	
				//入り数用テキストボックス生成
				txtIrisu = new TextboxBase();
				txtIrisu.setBackground(Yellow);
				txtIrisu.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtIrisu.setText(PrototypeData.getStrIrisu());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0111, DataCtrl.getInstance().getParamData().getStrMode())){
					txtIrisu.setBackground(Color.lightGray);
					txtIrisu.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtIrisu.setEditable(false);
				}else{
					txtIrisu.addFocusListener(new FocusCheck("入り数"));
				}
				this.add(txtIrisu);
	
			//荷姿
			labely = labely + mg_hgt;
			ItemLabel hlNisugata = new ItemLabel();
			hlNisugata.setBorder(new LineBorder(Color.BLACK, 1));
			hlNisugata.setBounds(labelx, labely, labelw, labelh);
			hlNisugata.setText("　　　荷姿");
			this.add(hlNisugata);
			
				//荷姿用コンボボックス生成
				InputComboBase cmbNisugata = new InputComboBase();
				cmbNisugata.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrNishugata();
				setLiteralCmb(cmbNisugata, DataCtrl.getInstance().getLiteralDataNisugata(), strLiteralCd, 1);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0112, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbNisugata.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbNisugata.setEditable(false);
					cmbNisugata.setEnabled(false);
				}else{
					cmbNisugata.getEditor().getEditorComponent().addFocusListener(new FocusCheck("荷姿"));
				}
				//パネルに追加
				this.add(cmbNisugata);
			
			//取扱温度
			labely = labely + mg_hgt;
			ItemLabel hlTori = new ItemLabel();
			hlTori.setBorder(new LineBorder(Color.BLACK, 1));
			hlTori.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlTori.setText("　　　取扱温度");
			hlTori.setText("<html><font color=\"red\">必須</font>　取扱温度");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlTori);
			
				//取扱温度用コンボボックス生成
				ComboBase cmbTori = new ComboBase();
				cmbTori.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrOndo();
				setLiteralCmb(cmbTori, DataCtrl.getInstance().getLiteralDataOndo(), strLiteralCd, 0);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0113, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbTori.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbTori.setEnabled(false);
				}else{
					cmbTori.addFocusListener(new FocusCheck("取扱温度"));
				}
				//パネルに追加
				this.add(cmbTori);
			
			//賞味期間
			labely = labely + mg_hgt;
			ItemLabel hlShomi = new ItemLabel();
			hlShomi.setBorder(new LineBorder(Color.BLACK, 1));
			hlShomi.setBounds(labelx, labely, labelw, labelh);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlShomi.setText("　　　賞味期間");
			hlShomi.setText("<html><font color=\"red\">必須</font>　賞味期間");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlShomi);
			
				//賞味期間用コンボボックス生成
				InputComboBase cmbShomi = new InputComboBase();
				cmbShomi.setBounds(ctrlx, labely, ctrlw, ctrlh);
				//コンボボックスに値を設定
				strLiteralCd = PrototypeData.getStrShomi();
				setLiteralCmb(cmbShomi, DataCtrl.getInstance().getLiteralDataShomi(), strLiteralCd, 1);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0114, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbShomi.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					cmbShomi.setEditable(false);
					cmbShomi.setEnabled(false);
				}else{
					cmbShomi.getEditor().getEditorComponent().addFocusListener(new FocusCheck("賞味期間"));
				}
				//パネルに追加
				this.add(cmbShomi);
			
			//原価希望
			labely = labely + mg_hgt;
			ItemLabel hlGenkibou = new ItemLabel();
			hlGenkibou.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenkibou.setBounds(labelx, labely, labelw, labelh);
			hlGenkibou.setText("　　　原価希望");
			this.add(hlGenkibou);
			
				//原価希望用テキストボックス生成
				TextboxBase txtGenkibou = new TextboxBase();
				txtGenkibou.setBackground(Yellow);
				txtGenkibou.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtGenkibou.setText(PrototypeData.getStrGenka());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0115, DataCtrl.getInstance().getParamData().getStrMode())){
					txtGenkibou.setBackground(Color.lightGray);
					txtGenkibou.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtGenkibou.setEditable(false);
				}else{
					txtGenkibou.addFocusListener(new FocusCheck("原価希望"));
				}
				this.add(txtGenkibou);
			
			//売価希望
			labely = labely + mg_hgt;
			ItemLabel hlBaikibou = new ItemLabel();
			hlBaikibou.setBorder(new LineBorder(Color.BLACK, 1));
			hlBaikibou.setBounds(labelx, labely, labelw, labelh);
			hlBaikibou.setText("　　　売価希望" + strKeisan);
			this.add(hlBaikibou);
			
				//売価希望用テキストボックス生成
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.26
//				txtBaikibou = new TextboxBase();
				txtBaikibou = new NumelicTextbox();								//数値のみ入力可
//				txtBaikibou.setBackground(Yellow);
				txtBaikibou.setHorizontalAlignment(SwingConstants.LEFT);		//左寄せに設定
//mod end --------------------------------------------------------------------------------------
				txtBaikibou.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtBaikibou.setText(PrototypeData.getStrBaika());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0116, DataCtrl.getInstance().getParamData().getStrMode())){
					txtBaikibou.setBackground(Color.lightGray);
					txtBaikibou.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtBaikibou.setEditable(false);
				}else{
					txtBaikibou.addFocusListener(new FocusCheck("売価希望"));
				}
				this.add(txtBaikibou);
			
			//想定物量
			labely = labely + mg_hgt;
			ItemLabel hlSotei = new ItemLabel();
			hlSotei.setBorder(new LineBorder(Color.BLACK, 1));
			hlSotei.setBounds(labelx, labely, labelw, labelh);
			hlSotei.setText("　　　想定物量");
			this.add(hlSotei);
	
				//想定物量用テキストボックス生成
				TextboxBase txtSotei = new TextboxBase();
				txtSotei.setBackground(Yellow);
				txtSotei.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtSotei.setText(PrototypeData.getStrSotei());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0117, DataCtrl.getInstance().getParamData().getStrMode())){
					txtSotei.setBackground(Color.lightGray);
					txtSotei.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtSotei.setEditable(false);
				}else{
					txtSotei.addFocusListener(new FocusCheck("想定物量"));
				}
				this.add(txtSotei);
			
			//販売時期
			labely = labely + mg_hgt;
			ItemLabel hlHanbai = new ItemLabel();
			hlHanbai.setBorder(new LineBorder(Color.BLACK, 1));
			hlHanbai.setBounds(labelx, labely, labelw, labelh);
			hlHanbai.setText("　　　販売時期");
			this.add(hlHanbai);
	
				//販売時期用テキストボックス生成
				TextboxBase txtHanbai = new TextboxBase();
				txtHanbai.setBackground(Yellow);
				txtHanbai.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtHanbai.setText(PrototypeData.getStrHatubai());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0118, DataCtrl.getInstance().getParamData().getStrMode())){
					txtHanbai.setBackground(Color.lightGray);
					txtHanbai.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtHanbai.setEditable(false);
				}else{
					txtHanbai.addFocusListener(new FocusCheck("販売時期"));
				}
				this.add(txtHanbai);
			
			//計画売上
			labely = labely + mg_hgt;
			ItemLabel hlKeikakuUri = new ItemLabel();
			hlKeikakuUri.setBorder(new LineBorder(Color.BLACK, 1));
			hlKeikakuUri.setBounds(labelx, labely, labelw, labelh);
			hlKeikakuUri.setText("　　　計画売上");
			this.add(hlKeikakuUri);
	
				//計画売上用テキストボックス生成
				TextboxBase txtKeikakuUri = new TextboxBase();
				txtKeikakuUri.setBackground(Yellow);
				txtKeikakuUri.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtKeikakuUri.setText(PrototypeData.getStrKeikakuUri());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0119, DataCtrl.getInstance().getParamData().getStrMode())){
					txtKeikakuUri.setBackground(Color.lightGray);
					txtKeikakuUri.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtKeikakuUri.setEditable(false);
				}else{
					txtKeikakuUri.addFocusListener(new FocusCheck("計画売上"));
				}
				this.add(txtKeikakuUri);
			
			//計画利益
			labely = labely + mg_hgt;
			ItemLabel hlKeikakuRie = new ItemLabel();
			hlKeikakuRie.setBorder(new LineBorder(Color.BLACK, 1));
			hlKeikakuRie.setBounds(labelx, labely, labelw, labelh);
			hlKeikakuRie.setText("　　　計画利益");
			this.add(hlKeikakuRie);
	
				//計画利益用テキストボックス生成
				TextboxBase txtKeikakuRie = new TextboxBase();
				txtKeikakuRie.setBackground(Yellow);
				txtKeikakuRie.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtKeikakuRie.setText(PrototypeData.getStrKeikakuRie());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0120, DataCtrl.getInstance().getParamData().getStrMode())){
					txtKeikakuRie.setBackground(Color.lightGray);
					txtKeikakuRie.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtKeikakuRie.setEditable(false);
				}else{
					txtKeikakuRie.addFocusListener(new FocusCheck("計画利益"));
				}
				this.add(txtKeikakuRie);
			
			//販売後売上
			labely = labely + mg_hgt;
			ItemLabel hlHanbaiUri = new ItemLabel();
			hlHanbaiUri.setBorder(new LineBorder(Color.BLACK, 1));
			hlHanbaiUri.setBounds(labelx, labely, labelw, labelh);
			hlHanbaiUri.setText("　　　販売後売上");
			this.add(hlHanbaiUri);
	
				//販売後売上用テキストボックス生成
				TextboxBase txtHanbaiUri = new TextboxBase();
				txtHanbaiUri.setBackground(Yellow);
				txtHanbaiUri.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtHanbaiUri.setText(PrototypeData.getStrHanbaigoUri());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0121, DataCtrl.getInstance().getParamData().getStrMode())){
					txtHanbaiUri.setBackground(Color.lightGray);
					txtHanbaiUri.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtHanbaiUri.setEditable(false);
				}else{
					txtHanbaiUri.addFocusListener(new FocusCheck("販売後売上"));
				}
				this.add(txtHanbaiUri);
			
			//販売後利益
			labely = labely + mg_hgt;
			ItemLabel hlHanbaiRie = new ItemLabel();
			hlHanbaiRie.setBorder(new LineBorder(Color.BLACK, 1));
			hlHanbaiRie.setBounds(labelx, labely, labelw, labelh);
			hlHanbaiRie.setText("　　　販売後利益");
			this.add(hlHanbaiRie);
	
				//販売後利益用テキストボックス生成
				TextboxBase txtHanbaiRie = new TextboxBase();
				txtHanbaiRie.setBackground(Yellow);
				txtHanbaiRie.setBounds(ctrlx, labely, ctrlw, ctrlh);
				txtHanbaiRie.setText(PrototypeData.getStrHanbaigoRie());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0122, DataCtrl.getInstance().getParamData().getStrMode())){
					txtHanbaiRie.setBackground(Color.lightGray);
					txtHanbaiRie.setBounds(ctrlx, labely, ctrlw-1, ctrlh);
					txtHanbaiRie.setEditable(false);
				}else{
					txtHanbaiRie.addFocusListener(new FocusCheck("販売後利益"));
				}
				this.add(txtHanbaiRie);
			
			//項目ラベル設定（総合メモ）
			ItemLabel hlSogo = new ItemLabel();
			hlSogo.setBorder(new LineBorder(Color.BLACK, 1));
			hlSogo.setBounds(sogo_labelx, sogo_labely, sogo_labelw, sogo_labelh);
			hlSogo.setText("総合メモ");
			this.add(hlSogo);
			
				//総合メモ用テキストエリア生成
				sogo_labely = sogo_labely + mg_hgt;
				TextAreaBase txaSogo = new TextAreaBase();
				txaSogo.setTABFocusControl();
				txaSogo.setText(PrototypeData.getStrSogo());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0123, DataCtrl.getInstance().getParamData().getStrMode())){
					txaSogo.setBackground(Color.lightGray);
					txaSogo.setEditable(false);
				}else{
					txaSogo.addFocusListener(new FocusCheck("総合メモ"));
				}
				ScrollBase scSogo = new ScrollBase(txaSogo);
				scSogo.setBounds(sogo_labelx, sogo_labely, sogo_labelw+1, sogo_ctrlh);
				this.add(scSogo);
				
			//半角入力（容量）
				sogo_labely = sogo_labely + sogo_ctrlh;
				ItemIndicationLabel ilHankaku1 = new ItemIndicationLabel();
				ilHankaku1.setBounds(sogo_labelx, sogo_labely, 150, bikouh);
				ilHankaku1.setText("半角入力");
				this.add(ilHankaku1);
			
			//半角入力（入り数）
				sogo_labely = sogo_labely + mg_hgt;
				ItemIndicationLabel ilHankaku2 = new ItemIndicationLabel();
				ilHankaku2.setBounds(sogo_labelx, sogo_labely, 150, bikouh);
				ilHankaku2.setText("半角入力");
				this.add(ilHankaku2);
				
			//項目ラベル設定（備考）
			sogo_labely = sogo_labely + mg_hgt;
			ItemLabel hlBikou = new ItemLabel();
			hlBikou.setBackground(Yellow);
			hlBikou.setBorder(new LineBorder(Color.BLACK, 1));
			hlBikou.setBounds(sogo_labelx, sogo_labely, bikouw, bikouh);
			this.add(hlBikou);
				
				ItemIndicationLabel ilBikou = new ItemIndicationLabel();
				ilBikou.setBounds(sogo_labelx+bikouw-1, sogo_labely, 150, bikouh);
				ilBikou.setText(" の項目は編集可能");
				this.add(ilBikou);
		
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作データ画面 試作表③ 初期化処理が失敗しました");
			ex.setStrErrmsg("試作データ画面 基本情報 初期化処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}		
		
	}
	
	/************************************************************************************
	 * 
	 *  データ挿入クラス（FocusListener）
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	class FocusCheck implements FocusListener {
		String komoku = "";
		
		/****** コンストラクタ ******/
		public FocusCheck(String strKomoku){
			komoku = strKomoku;
		}
		
		/***** ロストフォーカス *****/
		public void focusLost( FocusEvent e ){
			try{
				//コンポーネント取得
				JComponent jc = (JComponent)e.getSource();
				if(jc != null){
					
					//所属グループ
					if(komoku == "所属グループ"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdGroupCd(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//所属チーム
					if(komoku == "所属チーム"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdTeamCd(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//一括表示
					if(komoku == "一括表示"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						//挿入値取得
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataIkatu().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdIkkatsuHyouji(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//ジャンル
					if(komoku == "ジャンル"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						//挿入値取得
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataZyanru().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdJanru(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//ユーザ
					if(komoku == "ユーザ"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						//挿入値取得
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataUser().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdUser(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//特徴原料
					if(komoku == "特徴原料"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdGenryo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//用途
					if(komoku == "用途"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdYouto(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//価格帯
					if(komoku == "価格帯"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataKakaku().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdKakakutai(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//種別
					if(komoku == "種別"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataShubetu().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdHinsyubetu(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//少数指定
					//試作データ画面内にて定義
					
					//担当会社
					//試作データ画面内にて定義
					
					//担当工場
					//試作データ画面内にて定義
					
					//【QP@00342】
//					//担当営業
//					if(komoku == "担当営業"){
//						String insert = ((JTextField)jc).getText();
//						DataCtrl.getInstance().getTrialTblData().UpdTantoEigyo(
//								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
//								DataCtrl.getInstance().getUserMstData().getDciUserid()
//							);
//					}
					
					//製造方法
					if(komoku == "製造方法"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataSeizo().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdSeizoHouho(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//充填方法
					if(komoku == "充填方法"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataZyuten().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdJutenHouho(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//殺菌方法
					if(komoku == "殺菌方法"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdSakkinHouho(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//容器包材
					if(komoku == "容器包材"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdYoukiHouzai(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//容量
					if(komoku == "容量"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdYouryo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						
						

			    		//原価原料データ　容量 更新
						ArrayList aryCostMaterial = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
						for ( int i=0; i<aryCostMaterial.size(); i++ ) {
							CostMaterialData costMaterial = (CostMaterialData)aryCostMaterial.get(i);

				    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0140
				    			);
				    		
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
				    		//充填量を計算
							String keisan = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(costMaterial.getIntShisakuSeq());
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
									costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);
				    		
				    		//水相充填量を計算
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(costMaterial.getIntShisakuSeq());
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
									costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);
							
							//油相充填量を計算
							String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(costMaterial.getIntShisakuSeq());
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
									costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan2),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
							
						}
					}
					
					//容量単位
					if(komoku == "容量単位"){
						
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
//						String insert = null;
//						int selectId = ((ComboBase)jc).getSelectedIndex();
//						if(selectId > 0){
//							insert = DataCtrl.getInstance().getLiteralDataTani().selectLiteralCd(selectId-1);
//						}
//						DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
//								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
//								DataCtrl.getInstance().getUserMstData().getDciUserid()
//							);
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
						
					}
					
					//入り数
					if(komoku == "入り数"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdIriSu(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

			    		//原価原料データ　入数 更新
						ArrayList aryCostMaterial = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
						for ( int i=0; i<aryCostMaterial.size(); i++ ) {
							CostMaterialData costMaterial = (CostMaterialData)aryCostMaterial.get(i);
							
				    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0141
				    			);
						}
						
					}
					
					//荷姿
					if(komoku == "荷姿"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdNisugata(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//取扱温度
					if(komoku == "取扱温度"){
						String insert = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert = DataCtrl.getInstance().getLiteralDataOndo().selectLiteralCd(selectId-1);
						}
						DataCtrl.getInstance().getTrialTblData().UpdOndo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//賞味期間
					if(komoku == "賞味期間"){
						String insert = ((JTextField)jc).getText();
						DataCtrl.getInstance().getTrialTblData().UpdSyoumikigen(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//原価希望
					if(komoku == "原価希望"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdGenkaKibo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//売価希望
					if(komoku == "売価希望"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdBaikaKibo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

			    		//原価原料データ　売価 更新
						ArrayList aryCostMaterial = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
						for ( int i=0; i<aryCostMaterial.size(); i++ ) {
							CostMaterialData costMaterial = (CostMaterialData)aryCostMaterial.get(i);

				    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				costMaterial.getIntShisakuSeq(),
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0130
				    			);
						}
						
					}
					
					//想定物量
					if(komoku == "想定物量"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdSouteiButuryo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//販売時期
					if(komoku == "販売時期"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdHanbaiJiki(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//計画売上
					if(komoku == "計画売上"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdKeikakuUriage(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//計画利益
					if(komoku == "計画利益"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdKeikakuRieki(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//販売後売上
					if(komoku == "販売後売上"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdHanbaigoUriage(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//販売後利益
					if(komoku == "販売後利益"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdHanbaigoRieki(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					
					//総合メモ
					if(komoku == "総合メモ"){
						String insert = checkNull(((JTextArea)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdSogoMemo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
				}
			}catch(ExceptionBase eb){
				
			}catch(Exception ex){
				ex.printStackTrace();
				
			}finally{
				//テスト表示
				//DataCtrl.getInstance().getTrialTblData().dispPrototype();
			}
	    }
		public void focusGained( FocusEvent e ){
	    }
	}

	/**
	 * 【JW620】 送信XMLデータ作成
	 * @param strKaishaCd : 会社コード
	 */
	private void conJW620(String strKaishaCd) throws ExceptionBase{
		try{
			
			//　送信パラメータ格納
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();
//			if(strGamenId.equals(JwsConstManager.JWS_MODE_0000)){
//				strGamenId = JwsConstManager.JWS_MODE_0001;
//			}
			
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
//			xmlJW620.dispXml();
			xcon = new XmlConnection(xmlJW620);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			//　XML受信
			xmlJW620 = xcon.getXdocRes();
//			xmlJW620.dispXml();
			
			//　テストXMLデータ
			//xmlJW620 = new XmlData(new File("src/main/JW620.xml"));
			
			//部署データ
			DataCtrl.getInstance().getBushoData().setBushoData(xmlJW620);
			//DataCtrl.getInstance().getBushoData().dispBushoData();

		}catch(ExceptionBase ex){
			throw ex;
		}catch(Exception e){
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("送信XMLデータ作成処理が失敗しました");
			ex.setStrErrShori("Trial3Panel:conJW620");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}

	/**
	 * リテラルデータをコンボボックスへ設定
	 * @param comb : 設定対象コンボボックス
	 * @param literalData : 設定対象リテラルデータ
	 * @param strLiteralCd : 表示対象リテラルコード
	 * @param intType : 表示用リテラルコードのタイプ
	 *  (0:コード, 1:実値)
	 * @throws ExceptionBase 
	 */
	private void setLiteralCmb(JComboBox comb, LiteralData literalData, String strLiteralCd, int intType) throws ExceptionBase {
		int i;		
		String literalCd = "";
		String literalNm = "";
		Object viewLiteralNm = "";

		try {
			//コンボボックスの全項目の削除
			comb.removeAllItems();
	
			//タイプが0か1の場合、空項目を追加する
			comb.addItem("");
			
			//表示順に沿ってコンボボックスに値を追加
			for ( i=0; i<literalData.getAryLiteralCd().size(); i++ ) {
				//リテラルコード
				literalCd = literalData.getAryLiteralCd().get(i).toString();
				//リテラル名
				literalNm = literalData.getAryLiteralNm().get(i).toString(); 
				
				//コンボボックスへ追加
				comb.addItem(literalNm);
				
				//表示項目の検出
				if ( intType == 0 ) {
					//コード時
					if ( literalCd.equals(strLiteralCd) ) {
						viewLiteralNm = literalNm; 
					}
				} else {
					//実値時
					if ( literalNm.equals(strLiteralCd) ) {
						viewLiteralNm = strLiteralCd;	
					}
				}
			}
			
			//表示項目の設定
			if ( viewLiteralNm != "" ) {
				//表示項目が存在する場合
				comb.setSelectedItem(viewLiteralNm);
			} else {
				if ( intType == 0 ) {
					//コードの場合、空項目を表示
					comb.setSelectedIndex(0);
				} else {
					//実値の場合、実値を項目に追加し、表示する
					if(strLiteralCd != null && strLiteralCd.length() > 0){
						comb.addItem(strLiteralCd);
						comb.setSelectedItem(strLiteralCd);
					}
				}
			}

		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("リテラルデータコンボボックス設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
	
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	/**
	 * リテラルデータをコンボボックスへ設定（容量単位コンボ専用）
	 * @param comb : 設定対象コンボボックス
	 * @param literalData : 設定対象リテラルデータ
	 * @param strLiteralCd : 表示対象リテラルコード
	 * @param intType : 表示用リテラルコードのタイプ
	 *  (0:コード, 1:実値)
	 * @throws ExceptionBase 
	 */
	public void setLiteralCmbYoryo(JComboBox comb, LiteralData literalData, String strLiteralCd, int intType) throws ExceptionBase {
		int i;		
		String literalCd = "";
		String literalNm = "";
		String literalValue1 = "";
		Object viewLiteralNm = "";

		String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
		
		try {
			//コンボボックスの全項目の削除
			comb.removeAllItems();
	
			//タイプが0か1の場合、空項目を追加する
			comb.addItem("");
			
			
			//工程パターンが「空白」の場合
			if(ptKotei == null || ptKotei.length() == 0){
				
			}
			//工程パターンが「空白」でない場合
			else{
				//工程パターンのValue1取得
				String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
				
				//表示順に沿ってコンボボックスに値を追加
				for ( i=0; i<literalData.getAryLiteralCd().size(); i++ ) {
					
					//Value1
					literalValue1 = literalData.getAryValue1().get(i).toString();
					if(literalValue1.equals("0")){
						
					}
					else{
						//工程パターンが「調味料１液タイプ」の場合
						if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
							
							if( literalValue1.equals("1") || literalValue1.equals("2") ){
								
								//リテラルコード
								literalCd = literalData.getAryLiteralCd().get(i).toString();
								//リテラル名
								literalNm = literalData.getAryLiteralNm().get(i).toString(); 
								
								//コンボボックスへ追加
								comb.addItem(literalNm);
								
								//表示項目の検出
								if ( intType == 0 ) {
									//コード時
									if ( literalCd.equals(strLiteralCd) ) {
										viewLiteralNm = literalNm; 
									}
								}
							}
						}
						//工程パターンが「調味料２液タイプ」の場合
						else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
							
							//QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -Start
							//if( literalValue1.equals("1") ){
							if( literalValue1.equals("1") || literalValue1.equals("2") ){
							//QP@20505 No.2 2012/09/05 TT H.SHIMA MOD -End
								
								//リテラルコード
								literalCd = literalData.getAryLiteralCd().get(i).toString();
								//リテラル名
								literalNm = literalData.getAryLiteralNm().get(i).toString(); 
								
								//コンボボックスへ追加
								comb.addItem(literalNm);
								
								//表示項目の検出
								if ( intType == 0 ) {
									//コード時
									if ( literalCd.equals(strLiteralCd) ) {
										viewLiteralNm = literalNm; 
									}
								}
							}
						}
						//工程パターンが「その他・加食タイプ」の場合
						else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
							
							if( literalValue1.equals("2") ){
								
								//リテラルコード
								literalCd = literalData.getAryLiteralCd().get(i).toString();
								//リテラル名
								literalNm = literalData.getAryLiteralNm().get(i).toString(); 
								
								//コンボボックスへ追加
								comb.addItem(literalNm);
								
								//表示項目の検出
								if ( intType == 0 ) {
									//コード時
									if ( literalCd.equals(strLiteralCd) ) {
										viewLiteralNm = literalNm; 
									}
								}
							}
						}
					}
				}
			}
			
			//表示項目の設定
			if ( viewLiteralNm != "" ) {
				//表示項目が存在する場合
				comb.setSelectedItem(viewLiteralNm);
			} else {
				if ( intType == 0 ) {
					//コードの場合、空項目を表示
					comb.setSelectedIndex(0);
				}
			}

		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("リテラルデータコンボボックス設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

	/**
	 * 会社データをコンボボックスへ設定
	 * @param comb : 設定対象コンボボックス
	 * @throws ExceptionBase 
	 */
	private void setKaishaCmb(JComboBox comb) throws ExceptionBase {
		int i;
		
		try {
			//会社データ
			KaishaData kaishaData = DataCtrl.getInstance().getKaishaData();
			//試作品.会社コードの取得
			String protKaishaCd = Integer.toString(PrototypeData.getIntKaishacd());
			
			String kaishaCd = "";
			String kaishaNm = "";
			Object viewKaishaNm = "";
			
			//コンボボックスの全項目の削除
			comb.removeAllItems();
			
			//表示順に沿ってコンボボックスに値を追加
			for ( i=0; i<kaishaData.getArtKaishaCd().size(); i++ ) {
				//会社コード
				kaishaCd = kaishaData.getArtKaishaCd().get(i).toString();
				//会社名
				kaishaNm = kaishaData.getAryKaishaNm().get(i).toString(); 
				
				//コンボボックスへ追加
				comb.addItem(kaishaNm);
				
				//表示項目の検出
				if ( kaishaCd.equals(protKaishaCd) ) {
					viewKaishaNm = kaishaNm; 
				}
			}
			
			//対象項目の表示
			if ( viewKaishaNm != "" ) {
				comb.setSelectedItem(viewKaishaNm);
			} else {
				comb.setSelectedIndex(0);
			}
		
		}catch(Exception e){
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("送信XMLデータ作成処理が失敗しました");
			ex.setStrErrShori("Trial3Panel:conJW620");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}


	/**
	 * 部署データをコンボボックスへ設定
	 * @param comb : 設定対象コンボボックス
	 * @param strBushoCd : 表示対象コードコード
	 * @throws ExceptionBase 
	 */
	private void setBushoCmb(JComboBox comb) throws ExceptionBase {
		int i;
		
		try {
			//部署データ
			BushoData bushoData = DataCtrl.getInstance().getBushoData();
			//試作品.工場コードの取得
			String protBushoCd = Integer.toString(PrototypeData.getIntKojoco());
			
			String bushoCd = "";
			String bushoNm = "";
			Object viewBushoNm = "";
			
			//コンボボックスの全項目の削除
			comb.removeAllItems();
			
			//表示順に沿ってコンボボックスに値を追加
			for ( i=0; i<bushoData.getArtBushoCd().size(); i++ ) {
				//会社コード
				bushoCd = bushoData.getArtBushoCd().get(i).toString();
				//会社名
				bushoNm = bushoData.getAryBushoNm().get(i).toString(); 
				
				//コンボボックスへ追加
				comb.addItem(bushoNm);
				
				//表示項目の検出
				if ( bushoCd.equals(protBushoCd) ) {
					viewBushoNm = bushoNm; 
				}
			}
			
			//対象項目の表示
			if ( viewBushoNm != "" ) {
				comb.setSelectedItem(viewBushoNm);
			} else {
				comb.setSelectedIndex(0);
			}

		}catch(Exception e){
			e.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("送信XMLデータ作成処理が失敗しました");
			ex.setStrErrShori("Trial3Panel:conJW620");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
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
			//会社データ
			KaishaData kaishaData = DataCtrl.getInstance().getKaishaData();
			
			//表示順に沿ってコンボボックスに値を追加
			for ( i=0; i<kaishaData.getArtKaishaCd().size(); i++ ) {
				//会社コード
				kaishaCd = kaishaData.getArtKaishaCd().get(i).toString();
				//会社名
				kaishaNm = kaishaData.getAryKaishaNm().get(i).toString(); 
	
				//選択会社コードの検出
				if ( kaishaNm.equals(cmbKaisha.getSelectedItem().toString()) ) {
					intRetKaishaCd = kaishaCd;
				}
			}

		}catch(Exception e){
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("送信XMLデータ作成処理が失敗しました");
			ex.setStrErrShori("Trial3Panel:conJW620");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
		
		return intRetKaishaCd;
	}
	
	/**
	 * イベント処理の取得
	 * @return ActionListenerクラス
	 */
	private ActionListener getActionEvent() {
		return new ActionListener() {
			//イベント処理
			public void actionPerformed(ActionEvent e) {
				String eventNm = e.getActionCommand();
				
				try {
					//会社コンボボックス ActionEvent
					if ( eventNm == kaishaCommand ) {
						//会社コードの取得
						String selKaishaCd = getSelectKaishaCd();
						//JW620 : 部署データの再取得
						try {
							conJW620(selKaishaCd);
						} catch (ExceptionBase ex) {
							ex.printStackTrace();
						}
						//工場コンボボックスの再設定
						setBushoCmb(cmbKojo);
					}
					
					//【QP@00342】
					//営業担当検索 ActionEvent
					if ( eventNm == "btnSerchEigyo" ) {
						
						//試作分析データ確認サブ画面　再表示
						et.setVisible(true);
						et.getEigyoTantoSerchPanel().init();
						
						//アクションイベント追加
						(et.getEigyoTantoSerchPanel().getButton())[1].addActionListener(getActionEvent());
						(et.getEigyoTantoSerchPanel().getButton())[1].setActionCommand("sentaku_btn_click");
					}
					//【QP@00342】
					//営業担当検索 サブ画面内の選択ボタンクリック
					if ( eventNm == "sentaku_btn_click") {
						
						//営業担当検索テーブル取得
						TableBase tb = et.getEigyoTantoSerchPanel().getEigyoTantoSearchTable().getMainTable();
						
						//選択されている場合
						if(tb.getSelectedRow() >= 0){
							
							//選択行取得
							int selected = tb.getSelectedRow();
							
							//選択されている担当営業データ取得
							EigyoTantoData EigyoTantoData = (EigyoTantoData)et.getEigyoTantoSerchPanel().getEigyoTantoAry().get(selected);
							
							//担当営業挿入
							DataCtrl.getInstance().getTrialTblData().UpdTantoEigyo(
									DataCtrl.getInstance().getTrialTblData().checkNullString(EigyoTantoData.getId_user()),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							
							//担当営業名表示
							txtEigyo.setText(EigyoTantoData.getNm_user());
							
							//担当営業検索画面を非表示
							et.setVisible(false);
							
						}
					}

				} catch (ExceptionBase eb) {
					DataCtrl.getInstance().PrintMessage(eb);
				} catch (Exception ec) {
					//エラー設定
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
					//ex.setStrErrmsg("試作表③ イベント処理が失敗しました");
					ex.setStrErrmsg("基本情報 イベント処理が失敗しました");
					//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg(ec.getMessage());
					DataCtrl.getInstance().PrintMessage(ex);
				} finally {
				}
			}
		};
	}
	
	/************************************************************************************
	 * 
	 * 原価試算更新処理
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public void updGenkaShisan() throws ExceptionBase {

		String strYoryo = "";
		String strIrisu = "";
		String strBaika = "";
		
		try {

			//試作品データ　更新
			//容量
			strYoryo = this.cmbYoryo.getEditor().getItem().toString();
			DataCtrl.getInstance().getTrialTblData().UpdYouryo(
					DataCtrl.getInstance().getTrialTblData().checkNullString(strYoryo),
					DataCtrl.getInstance().getUserMstData().getDciUserid()
				);

			//入り数
			strIrisu = this.txtIrisu.getText();
			DataCtrl.getInstance().getTrialTblData().UpdIriSu(
					DataCtrl.getInstance().getTrialTblData().checkNullString(strIrisu),
					DataCtrl.getInstance().getUserMstData().getDciUserid()
				);
			
			//売価
			strBaika = this.txtBaikibou.getText();
			DataCtrl.getInstance().getTrialTblData().UpdBaikaKibo(
					DataCtrl.getInstance().getTrialTblData().checkNullString(strBaika),
					DataCtrl.getInstance().getUserMstData().getDciUserid()
				);

    		//原価原料データ　更新
			ArrayList aryCostMaterial = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
			
			for ( int i=0; i<aryCostMaterial.size(); i++ ) {
				CostMaterialData costMaterial = (CostMaterialData)aryCostMaterial.get(i);
			
				//容量
	    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
	    				costMaterial.getIntShisakuSeq(),
	    				DataCtrl.getInstance().getTrialTblData().checkNullString(strYoryo),
	    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
	    				JwsConstManager.JWS_COMPONENT_0140
	    			);
				
				//入り数
	    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
	    				costMaterial.getIntShisakuSeq(),
	    				DataCtrl.getInstance().getTrialTblData().checkNullString(strIrisu),
	    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
	    				JwsConstManager.JWS_COMPONENT_0141
	    			);
	    		
	    		//売価
	    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
	    				costMaterial.getIntShisakuSeq(),
	    				DataCtrl.getInstance().getTrialTblData().checkNullString(strBaika),
	    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
	    				JwsConstManager.JWS_COMPONENT_0130
	    			);
	    		
			}
			
		} catch (Exception ec) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表③ 原価試算更新処理が失敗しました");
			ex.setStrErrmsg("基本情報 原価試算更新処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;
			
		} finally {
			//変数の削除
			strYoryo = null;
			strIrisu = null;
			strBaika = null;
			
		}
		
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
//	private String checkNull(int val){
//		String ret = "";
//		if(val >= 0){
//			ret = Integer.toString(val);
//		}
//		return ret;
//	}
	
	/************************************************************************************
	 * 
	 * テキストグループゲッター&セッター
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public TextboxBase getTxtGroup() {
		return txtGroup;
	}
	public TextboxBase getTxtTeam() {
		return txtTeam;
	}
	
	/************************************************************************************
	 * 
	 * 担当会社コンボボックスゲッター
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public ComboBase getCmbKaisha() {
		return cmbKaisha;
	}
	
	/************************************************************************************
	 * 
	 * 担当工場コンボボックスゲッター
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public ComboBase getCmbKojo() {
		return cmbKojo;
	}
	
	/************************************************************************************
	 * 
	 * 小数指定コンボボックスゲッター
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public ComboBase getCmbShosu() {
		return cmbShosu;
	}
	
	/************************************************************************************
	 * 
	 * 小数指定コンボボックスセッター
	 * @throws ExceptionBase
	 * 
	 ************************************************************************************/
	public void setCmbShosu(ComboBase cmbShosu) {
		this.cmbShosu = cmbShosu;
	}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
	public InputComboBase getCmbYoryo() {
		return cmbYoryo;
	}
	public void setCmbYoryo(InputComboBase cmbYoryo) {
		this.cmbYoryo = cmbYoryo;
	}
	public ComboBase getCmbtani() {
		return cmbtani;
	}
	public void setCmbtani(ComboBase cmbtani) {
		this.cmbtani = cmbtani;
	}
//add end   -------------------------------------------------------------------------------

	
	
}
