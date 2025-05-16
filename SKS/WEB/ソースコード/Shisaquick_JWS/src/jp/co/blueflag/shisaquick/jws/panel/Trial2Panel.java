package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import jp.co.blueflag.shisaquick.jws.base.MixedData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextAreaCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextFieldCellEditor;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextAreaCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.CheckboxBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.ItemIndicationLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;

/**
 *
 * 特性値(試作表②)パネルクラス
 *
 */
public class Trial2Panel extends PanelBase {
	private static final long serialVersionUID = 1L;

	//一括チェック/解除　 ActionCommand値
	private final String IKKATU_CHK = "ikatuChkClick";
	private final String BUNSEKI_CHK = "bunsekiChkClick";

	//QP@20505 2012/11/01 ADD Start
	private TextboxBase txtFreeSuibunKassei;	//水分活性
	private TextboxBase txtFreeAlchol;			//アルコール
	private TextboxBase txtNendo;				//粘度
	private TextboxBase txtOndo;				//温度
	private TextboxBase txtFree1;				//
	private TextboxBase txtFree2;				//
	private TextboxBase txtFree3;				//
	private TextboxBase txtFree4;				//
	private TextboxBase txtFree5;				//
	private TextboxBase txtFree6;				//
	//QP@20505 2012/11/01 ADD End
	
	//チェックボックス
	private CheckboxBase chkAuto;		//自動計算
// ADD start 20121017 QP@20505 No.11
	private ButtonBase btnBunsekiMstData;		//分析マスタの最新状態に更新
// ADD end 20121017 QP@20505 No.11
	private CheckboxBase chkSo_sho;		//総数
	private CheckboxBase chkSui;		//水相中
	private CheckboxBase chkTodo;		//糖度
	private CheckboxBase chkOndo;		//温度
	private CheckboxBase chkPh;			//PH
	private CheckboxBase chkBun;		//分析
	private CheckboxBase chkHiju;		//比重
	private CheckboxBase chkKasei;		//水分活性
	private CheckboxBase chkAruko;		//アルコール
	private CheckboxBase chkFree1;		//フリータイトル①
	private CheckboxBase chkFree2;		//フリータイトル②
	private CheckboxBase chkFree3;		//フリータイトル③
	private CheckboxBase chkAll;		//一括チェックor解除
// ADD start 20120928 QP@20505 No.24
	private CheckboxBase chkFreeSuibunKassei;		//水分活性フリー
	private CheckboxBase chkFreeAlchol;				//アルコール フリー
	private CheckboxBase chkJikkoSakusanNodo;		//実効酢酸濃度
	private CheckboxBase chkSuisoMSG;		//水相中MSG
	private CheckboxBase chkFreeNendo;		//粘度フリー
	private CheckboxBase chkFreeOndo;				//温度 フリー
	private CheckboxBase chkFree4;		//フリータイトル４
	private CheckboxBase chkFree5;		//フリータイトル５
	private CheckboxBase chkFree6;		//フリータイトル６
// ADD end 20120928 QP@20505 No.24

	//テーブル
	private TableBase table;

	//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
	//スクロールバー
	private JScrollPane scroll;
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	private CheckboxBase chkHiju_sui;		//水相比重
	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

	private ExceptionBase ex;

	/**
	 *
	 * 特性値(試作表②)パネルクラス　コンストラクタ
	 * @throws ExceptionBase
	 *
	 */
	public Trial2Panel() throws ExceptionBase {
		super();

		try {
			//画面表示
			this.panelDisp();
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表②パネルクラス　コンストラクタ処理が失敗しました");
			ex.setStrErrmsg("特性値パネルクラス　コンストラクタ処理が失敗しました");
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
	 * 特性値(試作表②)パネルクラス　画面表示
	 * @throws ExceptionBase
	 *
	 */
	private void panelDisp() throws ExceptionBase{

		this.setLayout(null);
		this.setBackground(Color.WHITE);

		//試作データを取得
		Object[] aryTrialData = (DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0).toArray());

		//コンポーネント初期化処理
		this.initComponent(aryTrialData);

		//テーブル初期化処理
		this.initTable(aryTrialData);

		//2011/06/07 QP@10181_No.41 TT T.Satoh Add Start -------------------------
		//特性値の横スクロールバーの最大値を取得
		int hTokuseiBarMax = this.getScroll().getHorizontalScrollBar().getMaximum();

		//特性値の横スクロールバーの位置を設定
		this.getScroll().getHorizontalScrollBar().setValue(hTokuseiBarMax);
		//2011/06/07 QP@10181_No.41 TT T.Satoh Add End ---------------------------

	}


	/**
	 * コンポーネント初期化処理（テーブルを除く）
	 * @param aryTrialData : 試作データ
	 * @throws ExceptionBase
	 */
	private void initComponent(Object[] aryTrialData) throws ExceptionBase {
		TrialData trialData = (TrialData)aryTrialData[0];

		//計算項目用文字列 : (計算)　と表示する
		String strKeisan = "(" + JwsConstManager.JWS_MARK_0005 + ")";

		try {
			//分析値を自動計算
			chkAuto = new CheckboxBase();
			chkAuto.setBackground(Color.WHITE);
			chkAuto.setBounds(11, 5, 25, 16);
			//試作テーブル.自動計算Flg
			if ( trialData.getIntZidoKei() == 1 ) {
				chkAuto.setSelected(true);
			} else {
				chkAuto.setSelected(false);
			}
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0055, DataCtrl.getInstance().getParamData().getStrMode())){
				chkAuto.setEnabled(false);
			}else{
				chkAuto.addFocusListener(new FocusCheck("自動計算"));
			}
			this.add(chkAuto);

				ItemIndicationLabel iiAuto = new ItemIndicationLabel();
				iiAuto.setBounds(35, 5, 150, 15);
				iiAuto.setText("分析値を毎回自動計算");
				this.add(iiAuto);

//QP@20505 No.24 2012/09/11 TT H.SHIMA ADD Start
			// 工程パターン取得
			String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
			String ptValue = "";
			if(ptKotei == null || ptKotei.length() == 0){
			}else{
				ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
			}
//QP@20505 No.24 2012/09/11 TT H.SHIMA ADD End

			//項目ラベル設定（TOP）
			ItemLabel hlTop = new ItemLabel();
			hlTop.setBorder(new LineBorder(Color.BLACK, 1));
			hlTop.setHorizontalAlignment(SwingConstants.CENTER);
			hlTop.setBounds(15, 25, 180, 18);
			this.add(hlTop);

//QP@20505 No.24 2012/09/11 TT H.SHIMA DEL Start
//			//項目ラベル設定（計算結果表示グループ）
//			ItemLabel hlKeisan = new ItemLabel();
//			hlKeisan.setBorder(new LineBorder(Color.BLACK, 1));
//			hlKeisan.setHorizontalAlignment(SwingConstants.CENTER);
//			hlKeisan.setBounds(15, 42, 15, 86);
//			hlKeisan.setText("<html>計<br> 算<br> 結<br> 果");
//			this.add(hlKeisan);
//QP@20505 No.24 2012/09/11 TT H.SHIMA DEL End

			//項目ラベル設定（総酸）
			ItemLabel hlSosan = new ItemLabel();
			hlSosan.setBorder(new LineBorder(Color.BLACK, 1));
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//			hlSosan.setBounds(45, 42, 150, 18);
			hlSosan.setBounds(31, 42, 164, 18);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			hlSosan.setText(" 総酸（％）");
			this.add(hlSosan);

			//項目ラベル設定（食塩）
			ItemLabel hlShoku = new ItemLabel();
			hlShoku.setBorder(new LineBorder(Color.BLACK, 1));
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//			hlShoku.setBounds(45, 59, 150, 18);
			hlShoku.setBounds(31, 59, 164, 18);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			hlShoku.setText(" 食塩（％）");
			this.add(hlShoku);

			//総酸・食塩
			chkSo_sho = new CheckboxBase();
			chkSo_sho.setBorderPainted(true);
			chkSo_sho.setBorder(new LineBorder(Color.BLACK, 1));
			chkSo_sho.setBackground( JwsConstManager.SHISAKU_ITEM_COLOR );
			chkSo_sho.setHorizontalAlignment(JCheckBox.CENTER);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//				chkSo_sho.setBounds(29, 42, 17, 35);
			chkSo_sho.setBounds(15, 42, 17, 35);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			//試作テーブル flg
			if ( trialData.getIntSosanFlg() == 1 ) {
				chkSo_sho.setSelected(true);
			} else if (trialData.getIntShokuenFlg() == 1) {
				chkSo_sho.setSelected(true);
			} else {
				chkSo_sho.setSelected(false);
			}
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0056, DataCtrl.getInstance().getParamData().getStrMode())){
				chkSo_sho.setEnabled(false);
			}else{
				chkSo_sho.addFocusListener(new FocusCheck("総酸・食塩"));
			}
			this.add(chkSo_sho);

			//項目ラベル設定（水相中酸度）
			ItemLabel hlSuiSando = new ItemLabel();
			hlSuiSando.setBorder(new LineBorder(Color.BLACK, 1));
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//			hlSuiSando.setBounds(45, 76, 150, 18);
			hlSuiSando.setBounds(31, 76, 164, 18);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			hlSuiSando.setText(" 水相中酸度（％）");
			this.add(hlSuiSando);

			//項目ラベル設定（水相中食塩）
			ItemLabel hlSuiShoku = new ItemLabel();
			hlSuiShoku.setBorder(new LineBorder(Color.BLACK, 1));
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//			hlSuiShoku.setBounds(45, 93, 150, 18);
			hlSuiShoku.setBounds(31, 93, 164, 18);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			hlSuiShoku.setText(" 水相中食塩（％）");
			this.add(hlSuiShoku);

			//項目ラベル設定（水相中酢酸）
			ItemLabel hlSuiSaku = new ItemLabel();
			hlSuiSaku.setBorder(new LineBorder(Color.BLACK, 1));
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//			hlSuiSaku.setBounds(45, 110, 150, 18);
			hlSuiSaku.setBounds(31, 110, 164, 18);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			hlSuiSaku.setText(" 水相中酢酸（％）");
			this.add(hlSuiSaku);

			//水相中酸度, 水相中食塩, 水相中酢酸
			chkSui = new CheckboxBase();
			chkSui.setBorderPainted(true);
			chkSui.setBorder(new LineBorder(Color.BLACK, 1));
			chkSui.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
			chkSui.setHorizontalAlignment(JCheckBox.CENTER);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
//				chkSui.setBounds(29, 76, 17, 52);
			chkSui.setBounds(15, 76, 17, 52);
//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
			//試作テーブル flg
			if ( trialData.getIntSuiSandoFlg() == 1 ) {
				chkSui.setSelected(true);
			} else if ( trialData.getIntSuiShokuenFlg() == 1 ) {
				chkSui.setSelected(true);
			} else if ( trialData.getIntSuiSakusanFlg() == 1 ) {
				chkSui.setSelected(true);
			} else {
				chkSui.setSelected(false);
			}
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0057, DataCtrl.getInstance().getParamData().getStrMode())){
				chkSui.setEnabled(false);
			}else{
				chkSui.addFocusListener(new FocusCheck("水相中酸度・食塩・酢酸"));
			}
			this.add(chkSui);

// ADD start 20120927 QP@20505 No.24
			int y_count = 0;
			if(ptValue.equals("") || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
				// --------  工程パターン「その他・加食」または、未選択の場合  ----------------------------------------------------------------
// ADD start 20120927 QP@20505 No.24
				//項目ラベル設定（測定値入力グループ）
				ItemLabel hlSokutei = new ItemLabel();
	//QP@20505 No.24 2012/09/11 TT H.SHIMA DEL Start
	//			hlSokutei.setBorder(new LineBorder(Color.BLACK, 1));
	//			hlSokutei.setHorizontalAlignment(SwingConstants.CENTER);
	//			hlSokutei.setVerticalAlignment(SwingConstants.CENTER);
	//			hlSokutei.setBounds(15, 127, 15, 222);
	//			hlSokutei.setText("<html>測<br>定<br>値<br>入<br>力");
	//			this.add(hlSokutei);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA DEL End

				//項目ラベル設定（糖度）
				ItemLabel hlTodo = new ItemLabel();
				hlTodo.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlTodo.setBounds(45, 127, 150, 18);
				hlTodo.setBounds(31, 127, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlTodo.setText(" 糖度");
				this.add(hlTodo);

					//糖度
					chkTodo = new CheckboxBase();
					chkTodo.setBorderPainted(true);
					chkTodo.setBorder(new LineBorder(Color.BLACK, 1));
					chkTodo.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkTodo.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkTodo.setBounds(29, 127, 17, 18);
					chkTodo.setBounds(15, 127, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//試作テーブル flg
					if ( trialData.getIntToudoFlg() == 1 ) {
						chkTodo.setSelected(true);
					} else {
						chkTodo.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0058, DataCtrl.getInstance().getParamData().getStrMode())){
						chkTodo.setEnabled(false);
					}else{
						chkTodo.addFocusListener(new FocusCheck("糖度"));
					}
					this.add(chkTodo);

				//項目ラベル設定（粘度）
				ItemLabel hlNendo = new ItemLabel();
				hlNendo.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlNendo.setBounds(45, 144, 150, 18);
				hlNendo.setBounds(31, 144, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlNendo.setText(" 粘度");
				this.add(hlNendo);

				//項目ラベル設定（温度）
				ItemLabel hlOndo = new ItemLabel();
				hlOndo.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlOndo.setBounds(45, 161, 150, 18);
				hlOndo.setBounds(31, 161, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlOndo.setText(" 温度（℃）");
				this.add(hlOndo);

					//粘度, 温度
					chkOndo = new CheckboxBase();
					chkOndo.setBorderPainted(true);
					chkOndo.setBorder(new LineBorder(Color.BLACK, 1));
					chkOndo.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkOndo.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkOndo.setBounds(29, 144, 17, 35);
					chkOndo.setBounds(15, 144, 17, 35);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//試作テーブル flg
					if ( trialData.getIntNendoFlg() == 1 ) {
						chkOndo.setSelected(true);
					} else if ( trialData.getIntOndoFlg() == 1 ) {
						chkOndo.setSelected(true);
					} else {
						chkOndo.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0059, DataCtrl.getInstance().getParamData().getStrMode())){
						chkOndo.setEnabled(false);
					}else{
						chkOndo.addFocusListener(new FocusCheck("粘度・温度"));
					}
					this.add(chkOndo);

				//項目ラベル設定（pH）
				ItemLabel hlPh = new ItemLabel();
				hlPh.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlPh.setBounds(45, 178, 150, 18);
				hlPh.setBounds(31, 178, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlSokutei.setVerticalAlignment(SwingConstants.CENTER);
				hlPh.setText(" pH");
				this.add(hlPh);

					//PH
					chkPh = new CheckboxBase();
					chkPh.setBorderPainted(true);
					chkPh.setBorder(new LineBorder(Color.BLACK, 1));
					chkPh.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkPh.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkPh.setBounds(29, 178, 17, 18);
					chkPh.setBounds(15, 178, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//試作テーブル flg
					if ( trialData.getIntPhFlg() == 1 ) {
						chkPh.setSelected(true);
					} else {
						chkPh.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0060, DataCtrl.getInstance().getParamData().getStrMode())){
						chkPh.setEnabled(false);
					}else{
						chkPh.addFocusListener(new FocusCheck("PH"));
					}
					this.add(chkPh);

				//項目ラベル設定（総酸（％）：分析）
				ItemLabel hlBun_sosan = new ItemLabel();
				hlBun_sosan.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlBun_sosan.setBounds(45, 195, 150, 18);
				hlBun_sosan.setBounds(31, 195, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlBun_sosan.setVerticalAlignment(SwingConstants.CENTER);
				hlBun_sosan.setText(" 総酸（％）：分析");
				this.add(hlBun_sosan);

				//項目ラベル設定（食塩（％）：分析）
				ItemLabel hlBun_shoku = new ItemLabel();
				hlBun_shoku.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlBun_shoku.setBounds(45, 212, 150, 18);
				hlBun_shoku.setBounds(31, 212, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlBun_shoku.setVerticalAlignment(SwingConstants.CENTER);
				hlBun_shoku.setText(" 食塩（％）：分析");
				this.add(hlBun_shoku);

					//総酸分析, 食塩分析
					chkBun = new CheckboxBase();
					chkBun.setBorderPainted(true);
					chkBun.setBorder(new LineBorder(Color.BLACK, 1));
					chkBun.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkBun.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkBun.setBounds(29, 195, 17, 35);
					chkBun.setBounds(15, 195, 17, 35);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//試作テーブル flg
					if ( trialData.getIntSosanBunsekiFlg() == 1 ) {
						chkBun.setSelected(true);
					} else if ( trialData.getIntShokuenBunsekiFlg() == 1 ) {
						chkBun.setSelected(true);
					} else {
						chkBun.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0061, DataCtrl.getInstance().getParamData().getStrMode())){
						chkBun.setEnabled(false);
					}else{
						chkBun.addFocusListener(new FocusCheck("総酸分析・食塩分析"));
					}
					this.add(chkBun);

				//項目ラベル設定（比重）
				ItemLabel hlHiju = new ItemLabel();
				hlHiju.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			hlHiju.setBounds(45, 229, 150, 18);
				hlHiju.setBounds(31, 229, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				hlHiju.setVerticalAlignment(SwingConstants.CENTER);
	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
				hlHiju.setText(" 製品比重（ｇなら１）" + strKeisan);
	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
				this.add(hlHiju);

					//比重
					chkHiju = new CheckboxBase();
					chkHiju.setBorderPainted(true);
					chkHiju.setBorder(new LineBorder(Color.BLACK, 1));
					chkHiju.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkHiju.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkHiju.setBounds(29, 229, 17, 18);
					chkHiju.setBounds(15, 229, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//試作テーブル flg
					if ( trialData.getIntHizyuFlg() == 1 ) {
						chkHiju.setSelected(true);
					} else {
						chkHiju.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0062, DataCtrl.getInstance().getParamData().getStrMode())){
						chkHiju.setEnabled(false);
					}else{
						chkHiju.addFocusListener(new FocusCheck("比重"));
					}
					this.add(chkHiju);


	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
// MOD start 20120928 QP@20505 No.24
//					int y_count = 229 + 17;
					y_count = 229 + 17;
// MOD end 20120928 QP@20505 No.24

					//項目ラベル設定（比重）
					ItemLabel hlHiju_sui = new ItemLabel();
					hlHiju_sui.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				hlHiju_sui.setBounds(45, y_count, 150, 18);
					hlHiju_sui.setBounds(31, y_count, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					hlHiju_sui.setVerticalAlignment(SwingConstants.CENTER);
					hlHiju_sui.setText(" 水相比重");
					this.add(hlHiju_sui);

						//比重
						chkHiju_sui = new CheckboxBase();
						chkHiju_sui.setBorderPainted(true);
						chkHiju_sui.setBorder(new LineBorder(Color.BLACK, 1));
						chkHiju_sui.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
						chkHiju_sui.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//					chkHiju_sui.setBounds(29, y_count, 17, 18);
						chkHiju_sui.setBounds(15, y_count, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
						//試作テーブル flg
						if ( trialData.getIntHiju_sui_fg() == 1 ) {
							chkHiju_sui.setSelected(true);
						} else {
							chkHiju_sui.setSelected(false);
						}
						//モード編集
						if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
								JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
							chkHiju_sui.setEnabled(false);
						}else{
							chkHiju_sui.addFocusListener(new FocusCheck("水相比重"));
						}
						this.add(chkHiju_sui);
	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

// MOD start 20121025 QP@20505 No.24
//				//項目ラベル設定（水分活性）
//				y_count = y_count + 17;
//				ItemLabel hlKasei = new ItemLabel();
//				hlKasei.setBorder(new LineBorder(Color.BLACK, 1));
//				hlKasei.setBounds(31, y_count, 164, 18);
//				hlKasei.setVerticalAlignment(SwingConstants.CENTER);
//				hlKasei.setText(" 水分活性");
//				this.add(hlKasei);
				//テキスト設定（水分活性フリー）
				y_count = y_count + 17;
				txtFreeSuibunKassei = new TextboxBase();
				txtFreeSuibunKassei.setBackground(Color.WHITE);
				txtFreeSuibunKassei.setBorder(new LineBorder(Color.BLACK, 1));
				txtFreeSuibunKassei.setBounds(31, y_count, 164, 18);
				//試作テーブル
				if (trialData.getStrFreeTitleSuibunKasei() == null){
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 課題No,11
	//				txtFreeSuibunKassei.setText("水分活性");
//					txtFreeSuibunKassei.setText("");
	// MOD end 20121128 QP@20505 課題No,11
				txtFreeSuibunKassei.setText("水分活性");
// MOD end 20130215 QP@20505
				}else{
					txtFreeSuibunKassei.setText(trialData.getStrFreeTitleSuibunKasei());
				}
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0160, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFreeSuibunKassei.setEditable(false);
				}else{
					txtFreeSuibunKassei.addFocusListener(new FocusCheck("水分活性フリータイトル"));
				}
				this.add(txtFreeSuibunKassei);
// MOD start 20121025 QP@20505 No.24

					//水分活性
// MOD start 20121025 QP@20505 No.24
//					chkKasei = new CheckboxBase();
//					chkKasei.setBorderPainted(true);
//					chkKasei.setBorder(new LineBorder(Color.BLACK, 1));
//					chkKasei.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
//					chkKasei.setHorizontalAlignment(JCheckBox.CENTER);
//					chkKasei.setBounds(29, y_count, 17, 18);
//					//試作テーブル flg
//					if ( trialData.getIntSuibunFlg() == 1 ) {
//						chkKasei.setSelected(true);
//					} else {
//						chkKasei.setSelected(false);
//					}
//					//モード編集
//					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
//							JwsConstManager.JWS_COMPONENT_0063, DataCtrl.getInstance().getParamData().getStrMode())){
//						chkKasei.setEnabled(false);
//					}else{
//						chkKasei.addFocusListener(new FocusCheck("水分活性フリー出力"));
//					}
//					this.add(chkKasei);
					chkFreeSuibunKassei = new CheckboxBase();
					chkFreeSuibunKassei.setBorderPainted(true);
					chkFreeSuibunKassei.setBorder(new LineBorder(Color.BLACK, 1));
					chkFreeSuibunKassei.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFreeSuibunKassei.setHorizontalAlignment(JCheckBox.CENTER);
					chkFreeSuibunKassei.setBounds(15, y_count, 17, 18);
					if ( trialData.getIntFreeSuibunKaseiFlg() == 1 ) {
						chkFreeSuibunKassei.setSelected(true);
					} else {
						chkFreeSuibunKassei.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0063, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFreeSuibunKassei.setEnabled(false);
					}else{
						chkFreeSuibunKassei.addFocusListener(new FocusCheck("水分活性フリー出力"));
					}
					this.add(chkFreeSuibunKassei);
// MOD end 20121025 QP@20505 No.24


// MOD start 20121025 QP@20505 No.24
//				//項目ラベル設定（アルコール）
//				y_count = y_count + 17;
//				ItemLabel hlAruko = new ItemLabel();
//				hlAruko.setBorder(new LineBorder(Color.BLACK, 1));
//				hlAruko.setBounds(45, y_count, 150, 18);
//				hlAruko.setVerticalAlignment(SwingConstants.CENTER);
//				hlAruko.setText(" アルコール");
//				this.add(hlAruko);
				//テキスト設定（アルコール フリー）
				y_count = y_count + 17;
				txtFreeAlchol = new TextboxBase();
				txtFreeAlchol.setBackground(Color.WHITE);
				txtFreeAlchol.setBorder(new LineBorder(Color.BLACK, 1));
				txtFreeAlchol.setBounds(31, y_count, 164, 18);
				//試作テーブル
				if (trialData.getStrFreeTitleAlchol() == null){
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 課題No,11
	//				txtFreeAlchol.setText("アルコール");
//					txtFreeAlchol.setText("");
	// MOD end 20121128 QP@20505 課題No,11
					txtFreeAlchol.setText("アルコール");
// MOD end 20130215 QP@20505
				}else{
					txtFreeAlchol.setText(trialData.getStrFreeTitleAlchol());
				}
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0163, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFreeAlchol.setEditable(false);
				}else{
					txtFreeAlchol.addFocusListener(new FocusCheck("アルコールフリータイトル"));
				}
				this.add(txtFreeAlchol);
// MOD end 20121025 QP@20505 No.24

					//アルコール
// MOD start 20121025 QP@20505 No.24
//					chkAruko = new CheckboxBase();
//					chkAruko.setBorderPainted(true);
//					chkAruko.setBorder(new LineBorder(Color.BLACK, 1));
//					chkAruko.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
//					chkAruko.setHorizontalAlignment(JCheckBox.CENTER);
//					chkAruko.setBounds(29, y_count, 17, 18);
//					//試作テーブル flg
//					if ( trialData.getIntArukoruFlg() == 1 ) {
//						chkAruko.setSelected(true);
//					} else {
//						chkAruko.setSelected(false);
//					}
//					//モード編集
//					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
//							JwsConstManager.JWS_COMPONENT_0064, DataCtrl.getInstance().getParamData().getStrMode())){
//						chkAruko.setEnabled(false);
//					}else{
//						chkAruko.addFocusListener(new FocusCheck("アルコール"));
//					}
//					this.add(chkAruko);
					chkFreeAlchol = new CheckboxBase();
					chkFreeAlchol.setBorderPainted(true);
					chkFreeAlchol.setBorder(new LineBorder(Color.BLACK, 1));
					chkFreeAlchol.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFreeAlchol.setHorizontalAlignment(JCheckBox.CENTER);
					chkFreeAlchol.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntFreeAlcholFlg() == 1 ) {
						chkFreeAlchol.setSelected(true);
					} else {
						chkFreeAlchol.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0064, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFreeAlchol.setEnabled(false);
					}else{
						chkFreeAlchol.addFocusListener(new FocusCheck("アルコールフリー出力"));
					}
					this.add(chkFreeAlchol);
// MOD start 20121025 QP@20505 No.24


				//テキスト設定（フリータイトル①）
				y_count = y_count + 17;
				txtFree1 = new TextboxBase();
				txtFree1.setBackground(Color.WHITE);
				txtFree1.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			txtFree1.setBounds(45, y_count, 150, 18);
				txtFree1.setBounds(31, y_count, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				//試作テーブル　フリータイトル
				txtFree1.setText(trialData.getStrFreeTitle1());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0069, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree1.setEditable(false);
				}else{
					txtFree1.addFocusListener(new FocusCheck("フリータイトル1"));
				}
				this.add(txtFree1);

					//フリータイトル①
					chkFree1 = new CheckboxBase();
					chkFree1.setBorderPainted(true);
					chkFree1.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree1.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree1.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkFree1.setBounds(29, y_count, 17, 18);
					chkFree1.setBounds(15, y_count, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//試作テーブル flg
					if ( trialData.getIntFreeFlg() == 1 ) {
						chkFree1.setSelected(true);
					} else {
						chkFree1.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0065, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree1.setEnabled(false);
					}else{
						chkFree1.addFocusListener(new FocusCheck("フリータイトル1Fg"));
					}
					this.add(chkFree1);

				//テキスト設定（フリータイトル②）
				y_count = y_count + 17;
				txtFree2 = new TextboxBase();
				txtFree2.setBackground(Color.WHITE);
				txtFree2.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			txtFree2.setBounds(45, y_count, 150, 18);
				txtFree2.setBounds(31, y_count, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				//試作テーブル　フリータイトル
				txtFree2.setText(trialData.getStrFreeTitle2());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0070, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree2.setEditable(false);
				}else{
					txtFree2.addFocusListener(new FocusCheck("フリータイトル2"));
				}
				this.add(txtFree2);

					//フリータイトル②
					chkFree2 = new CheckboxBase();
					chkFree2.setBorderPainted(true);
					chkFree2.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree2.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree2.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkFree2.setBounds(29, y_count, 17, 18);
					chkFree2.setBounds(15, y_count, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//試作テーブル flg
					if ( trialData.getIntFreeFl2() == 1 ) {
						chkFree2.setSelected(true);
					} else {
						chkFree2.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0066, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree2.setEnabled(false);
					}else{
						chkFree2.addFocusListener(new FocusCheck("フリータイトル2Fg"));
					}
					this.add(chkFree2);

				//テキスト設定（フリータイトル③）
				y_count = y_count + 17;
				txtFree3 = new TextboxBase();
				txtFree3.setBackground(Color.WHITE);
				txtFree3.setBorder(new LineBorder(Color.BLACK, 1));
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//			txtFree3.setBounds(45, y_count, 150, 18);
				txtFree3.setBounds(31, y_count, 164, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
				//試作テーブル　フリータイトル
				txtFree3.setText(trialData.getStrFreeTitle3());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0071, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree3.setEditable(false);
				}else{
					txtFree3.addFocusListener(new FocusCheck("フリータイトル3"));
				}
				this.add(txtFree3);

					//フリータイトル③
					chkFree3 = new CheckboxBase();
					chkFree3.setBorderPainted(true);
					chkFree3.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree3.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree3.setHorizontalAlignment(JCheckBox.CENTER);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD Start
	//				chkFree3.setBounds(29, y_count, 17, 18);
					chkFree3.setBounds(15, y_count, 17, 18);
	//QP@20505 No.24 2012/09/11 TT H.SHIMA MOD End
					//試作テーブル flg
					if ( trialData.getIntFreeFl3() == 1 ) {
						chkFree3.setSelected(true);
					} else {
						chkFree3.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0067, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree3.setEnabled(false);
					}else{
						chkFree3.addFocusListener(new FocusCheck("フリータイトル3Fg"));
					}
					this.add(chkFree3);
// ADD start 20120927 QP@20505 No.24
			}else{
				// --------  工程パターン「１液」「２液」の場合  -----------------------------------------------------------------------------
				y_count = 110;

				//項目ラベル設定（測定値入力グループ）
				ItemLabel hlSokutei = new ItemLabel();

				//項目ラベル設定（実効酢酸濃度）
				y_count = y_count + 17;
				ItemLabel hlJSN = new ItemLabel();
				hlJSN.setBorder(new LineBorder(Color.BLACK, 1));
				hlJSN.setBounds(31, y_count, 164, 18);
				hlJSN.setText(" 実効酢酸濃度（％）");
				this.add(hlJSN);

					chkJikkoSakusanNodo = new CheckboxBase();
					chkJikkoSakusanNodo.setBorderPainted(true);
					chkJikkoSakusanNodo.setBorder(new LineBorder(Color.BLACK, 1));
					chkJikkoSakusanNodo.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkJikkoSakusanNodo.setHorizontalAlignment(JCheckBox.CENTER);
					chkJikkoSakusanNodo.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntJikkoSakusanNodoFlg() == 1 ) {
						chkJikkoSakusanNodo.setSelected(true);
					} else {
						chkJikkoSakusanNodo.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0167, DataCtrl.getInstance().getParamData().getStrMode())){
						chkJikkoSakusanNodo.setEnabled(false);
					}else{
						chkJikkoSakusanNodo.addFocusListener(new FocusCheck("実効酢酸濃度"));
					}
					this.add(chkJikkoSakusanNodo);

				//項目ラベル設定（水相中ＭＳＧ）
				y_count = y_count + 17;
				ItemLabel hlSuiMSG = new ItemLabel();
				hlSuiMSG.setBorder(new LineBorder(Color.BLACK, 1));
				hlSuiMSG.setBounds(31, y_count, 164, 18);
				hlSuiMSG.setText(" 水相中ＭＳＧ（％）");
				this.add(hlSuiMSG);

					chkSuisoMSG = new CheckboxBase();
					chkSuisoMSG.setBorderPainted(true);
					chkSuisoMSG.setBorder(new LineBorder(Color.BLACK, 1));
					chkSuisoMSG.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkSuisoMSG.setHorizontalAlignment(JCheckBox.CENTER);
					chkSuisoMSG.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntSuisoMSGFlg() == 1 ) {
						chkSuisoMSG.setSelected(true);
					} else {
						chkSuisoMSG.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0169, DataCtrl.getInstance().getParamData().getStrMode())){
						chkSuisoMSG.setEnabled(false);
					}else{
						chkSuisoMSG.addFocusListener(new FocusCheck("水相中ＭＳＧ"));
					}
					this.add(chkSuisoMSG);

				//項目ラベル設定（ｐＨ）
				y_count = y_count + 17;
				ItemLabel hlPh = new ItemLabel();
				hlPh.setBorder(new LineBorder(Color.BLACK, 1));
				hlPh.setBounds(31, y_count, 164, 18);
				hlPh.setText(" pH");
				this.add(hlPh);

					chkPh = new CheckboxBase();
					chkPh.setBorderPainted(true);
					chkPh.setBorder(new LineBorder(Color.BLACK, 1));
					chkPh.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkPh.setHorizontalAlignment(JCheckBox.CENTER);
					chkPh.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntPhFlg() == 1 ) {
						chkPh.setSelected(true);
					} else {
						chkPh.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0060, DataCtrl.getInstance().getParamData().getStrMode())){
						chkPh.setEnabled(false);
					}else{
						chkPh.addFocusListener(new FocusCheck("PH"));
					}
					this.add(chkPh);

				//項目ラベル設定（製品比重）
				y_count = y_count + 17;
				ItemLabel hlHiju = new ItemLabel();
				hlHiju.setBorder(new LineBorder(Color.BLACK, 1));
				hlHiju.setBounds(31, y_count, 164, 18);
				hlHiju.setVerticalAlignment(SwingConstants.CENTER);
				hlHiju.setText(" 製品比重（ｇなら１）" + strKeisan);
				this.add(hlHiju);

					chkHiju = new CheckboxBase();
					chkHiju.setBorderPainted(true);
					chkHiju.setBorder(new LineBorder(Color.BLACK, 1));
					chkHiju.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkHiju.setHorizontalAlignment(JCheckBox.CENTER);
					chkHiju.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntHizyuFlg() == 1 ) {
						chkHiju.setSelected(true);
					} else {
						chkHiju.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0062, DataCtrl.getInstance().getParamData().getStrMode())){
						chkHiju.setEnabled(false);
					}else{
						chkHiju.addFocusListener(new FocusCheck("比重"));
					}
					this.add(chkHiju);

				//項目ラベル設定（水相比重）
				y_count = y_count + 17;
				ItemLabel hlHiju_sui = new ItemLabel();
				hlHiju_sui.setBorder(new LineBorder(Color.BLACK, 1));
				hlHiju_sui.setBounds(31, y_count, 164, 18);
				hlHiju_sui.setVerticalAlignment(SwingConstants.CENTER);
				hlHiju_sui.setText(" 水相比重");
				this.add(hlHiju_sui);

					chkHiju_sui = new CheckboxBase();
					chkHiju_sui.setBorderPainted(true);
					chkHiju_sui.setBorder(new LineBorder(Color.BLACK, 1));
					chkHiju_sui.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkHiju_sui.setHorizontalAlignment(JCheckBox.CENTER);
					chkHiju_sui.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntHiju_sui_fg() == 1 ) {
						chkHiju_sui.setSelected(true);
					} else {
						chkHiju_sui.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
						chkHiju_sui.setEnabled(false);
					}else{
						chkHiju_sui.addFocusListener(new FocusCheck("水相比重"));
					}
					this.add(chkHiju_sui);

				//項目ラベル設定（粘度 フリータイトル）
				y_count = y_count + 17;
				txtNendo = new TextboxBase();
				txtNendo.setBackground(Color.WHITE);
				txtNendo.setBorder(new LineBorder(Color.BLACK, 1));
				txtNendo.setBounds(31, y_count, 164, 18);
				if (trialData.getStrFreeTitleNendo() == null){
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 課題No.11
	//				txtNendo.setText("粘度");
//					txtNendo.setText("");
	// MOD end 20121128 QP@20505 課題No.11
				txtNendo.setText("粘度");
// MOD end 20130215 QP@20505
				}else{
					txtNendo.setText(trialData.getStrFreeTitleNendo());
				}
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0170, DataCtrl.getInstance().getParamData().getStrMode())){
					txtNendo.setEditable(false);
				}else{
					txtNendo.addFocusListener(new FocusCheck("粘度フリータイトル"));
				}
				this.add(txtNendo);

				//項目ラベル設定（温度 フリータイトル）
				y_count = y_count + 17;
				txtOndo = new TextboxBase();
				txtOndo.setBackground(Color.WHITE);
				txtOndo.setBorder(new LineBorder(Color.BLACK, 1));
				txtOndo.setBounds(31, y_count, 164, 18);
				if (trialData.getStrFreeTitleOndo() == null){
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 課題No.11
	//				txtOndo.setText("温度（℃）");
//					txtOndo.setText("");
	// MOD end 20121128 QP@20505 課題No.11
				txtOndo.setText("温度（℃）");
// MOD end 20130215 QP@20505
				}else{
					txtOndo.setText(trialData.getStrFreeTitleOndo());
				}
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0173, DataCtrl.getInstance().getParamData().getStrMode())){
					txtOndo.setEditable(false);
				}else{
					txtOndo.addFocusListener(new FocusCheck("温度フリータイトル"));
				}
				this.add(txtOndo);

					chkFreeNendo = new CheckboxBase();
					chkFreeNendo.setBorderPainted(true);
					chkFreeNendo.setBorder(new LineBorder(Color.BLACK, 1));
					chkFreeNendo.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFreeNendo.setHorizontalAlignment(JCheckBox.CENTER);
					chkFreeNendo.setBounds(15, y_count - 17, 17, 35);
					//試作テーブル flg
					if ( trialData.getIntFreeNendoFlg() == 1 ) {
						chkFreeNendo.setSelected(true);
					} else if ( trialData.getIntFreeOndoFlg() == 1 ) {
						chkFreeNendo.setSelected(true);
					} else {
						chkFreeNendo.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0172, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFreeNendo.setEnabled(false);
					}else{
						chkFreeNendo.addFocusListener(new FocusCheck("粘度フリー出力"));
					}
					this.add(chkFreeNendo);

				//テキスト設定（フリータイトル①）
				y_count = y_count + 17;
				txtFree1 = new TextboxBase();
				txtFree1.setBackground(Color.WHITE);
				txtFree1.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree1.setBounds(31, y_count, 164, 18);
				//試作テーブル　フリータイトル
				txtFree1.setText(trialData.getStrFreeTitle1());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0069, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree1.setEditable(false);
				}else{
					txtFree1.addFocusListener(new FocusCheck("フリータイトル1"));
				}
				this.add(txtFree1);

					//フリータイトル①
					chkFree1 = new CheckboxBase();
					chkFree1.setBorderPainted(true);
					chkFree1.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree1.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree1.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree1.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntFreeFlg() == 1 ) {
						chkFree1.setSelected(true);
					} else {
						chkFree1.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0065, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree1.setEnabled(false);
					}else{
						chkFree1.addFocusListener(new FocusCheck("フリータイトル1Fg"));
					}
					this.add(chkFree1);

				//テキスト設定（フリータイトル②）
				y_count = y_count + 17;
				txtFree2 = new TextboxBase();
				txtFree2.setBackground(Color.WHITE);
				txtFree2.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree2.setBounds(31, y_count, 164, 18);
				//試作テーブル　フリータイトル
				txtFree2.setText(trialData.getStrFreeTitle2());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0070, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree2.setEditable(false);
				}else{
					txtFree2.addFocusListener(new FocusCheck("フリータイトル2"));
				}
				this.add(txtFree2);

					//フリータイトル②
					chkFree2 = new CheckboxBase();
					chkFree2.setBorderPainted(true);
					chkFree2.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree2.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree2.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree2.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntFreeFl2() == 1 ) {
						chkFree2.setSelected(true);
					} else {
						chkFree2.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0066, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree2.setEnabled(false);
					}else{
						chkFree2.addFocusListener(new FocusCheck("フリータイトル2Fg"));
					}
					this.add(chkFree2);

				//テキスト設定（フリータイトル③）
				y_count = y_count + 17;
				txtFree3 = new TextboxBase();
				txtFree3.setBackground(Color.WHITE);
				txtFree3.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree3.setBounds(31, y_count, 164, 18);
				//試作テーブル　フリータイトル
				txtFree3.setText(trialData.getStrFreeTitle3());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0071, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree3.setEditable(false);
				}else{
					txtFree3.addFocusListener(new FocusCheck("フリータイトル3"));
				}
				this.add(txtFree3);

					//フリータイトル③
					chkFree3 = new CheckboxBase();
					chkFree3.setBorderPainted(true);
					chkFree3.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree3.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree3.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree3.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntFreeFl3() == 1 ) {
						chkFree3.setSelected(true);
					} else {
						chkFree3.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0067, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree3.setEnabled(false);
					}else{
						chkFree3.addFocusListener(new FocusCheck("フリータイトル3Fg"));
					}
					this.add(chkFree3);

				//テキスト設定（フリータイトル④）
				y_count = y_count + 17;
				txtFree4 = new TextboxBase();
				txtFree4.setBackground(Color.WHITE);
				txtFree4.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree4.setBounds(31, y_count, 164, 18);
				//試作テーブル　フリータイトル
				txtFree4.setText(trialData.getStrFreeTitle4());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0176, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree4.setEditable(false);
				}else{
					txtFree4.addFocusListener(new FocusCheck("フリータイトル4"));
				}
				this.add(txtFree4);

					//フリータイトル④
					chkFree4 = new CheckboxBase();
					chkFree4.setBorderPainted(true);
					chkFree4.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree4.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree4.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree4.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntFreeFlg4() == 1 ) {
						chkFree4.setSelected(true);
					} else {
						chkFree4.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0178, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree4.setEnabled(false);
					}else{
						chkFree4.addFocusListener(new FocusCheck("フリータイトル4Fg"));
					}
					this.add(chkFree4);

				//テキスト設定（フリータイトル⑤）
				y_count = y_count + 17;
				txtFree5 = new TextboxBase();
				txtFree5.setBackground(Color.WHITE);
				txtFree5.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree5.setBounds(31, y_count, 164, 18);
				//試作テーブル　フリータイトル
				txtFree5.setText(trialData.getStrFreeTitle5());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0179, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree5.setEditable(false);
				}else{
					txtFree5.addFocusListener(new FocusCheck("フリータイトル5"));
				}
				this.add(txtFree5);

					//フリータイトル⑤
					chkFree5 = new CheckboxBase();
					chkFree5.setBorderPainted(true);
					chkFree5.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree5.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree5.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree5.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntFreeFlg5() == 1 ) {
						chkFree5.setSelected(true);
					} else {
						chkFree5.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0181, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree5.setEnabled(false);
					}else{
						chkFree5.addFocusListener(new FocusCheck("フリータイトル5Fg"));
					}
					this.add(chkFree5);

				//テキスト設定（フリータイトル⑥）
				y_count = y_count + 17;
				txtFree6 = new TextboxBase();
				txtFree6.setBackground(Color.WHITE);
				txtFree6.setBorder(new LineBorder(Color.BLACK, 1));
				txtFree6.setBounds(31, y_count, 164, 18);
				//試作テーブル　フリータイトル
				txtFree6.setText(trialData.getStrFreeTitle6());
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0182, DataCtrl.getInstance().getParamData().getStrMode())){
					txtFree6.setEditable(false);
				}else{
					txtFree6.addFocusListener(new FocusCheck("フリータイトル6"));
				}
				this.add(txtFree6);

					//フリータイトル⑥
					chkFree6 = new CheckboxBase();
					chkFree6.setBorderPainted(true);
					chkFree6.setBorder(new LineBorder(Color.BLACK, 1));
					chkFree6.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
					chkFree6.setHorizontalAlignment(JCheckBox.CENTER);
					chkFree6.setBounds(15, y_count, 17, 18);
					//試作テーブル flg
					if ( trialData.getIntFreeFlg6() == 1 ) {
						chkFree6.setSelected(true);
					} else {
						chkFree6.setSelected(false);
					}
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0184, DataCtrl.getInstance().getParamData().getStrMode())){
						chkFree6.setEnabled(false);
					}else{
						chkFree6.addFocusListener(new FocusCheck("フリータイトル6Fg"));
					}
					this.add(chkFree6);
			}

			y_count = 331;
// ADD end 20120927 QP@20505 No.24

			//項目ラベル設定（作成メモ）
			y_count = y_count + 17;
			ItemLabel hlSakusei = new ItemLabel();
			hlSakusei.setBorder(new LineBorder(Color.BLACK, 1));
			hlSakusei.setBounds(15, y_count, 180, 91);
			hlSakusei.setVerticalAlignment(SwingConstants.CENTER);
			hlSakusei.setText(" 作成メモ");
			this.add(hlSakusei);

			//項目ラベル設定（評価）
			y_count = y_count + 90;
			ItemLabel hlHyoka = new ItemLabel();
			hlHyoka.setBorder(new LineBorder(Color.BLACK, 1));
			hlHyoka.setBounds(15, y_count, 180, 91);
			hlHyoka.setVerticalAlignment(SwingConstants.CENTER);
			hlHyoka.setText(" 評価");
			this.add(hlHyoka);

			//一括チェック
			chkAll = new CheckboxBase();
			chkAll.setBackground(Color.WHITE);
			chkAll.setBounds(11, 555, 25, 16);
			chkAll.setSelected(false);
			chkAll.addActionListener(this.getActionEvent());
			chkAll.setActionCommand(this.IKKATU_CHK);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0068, DataCtrl.getInstance().getParamData().getStrMode())){
				chkAll.setEnabled(false);
			}else{
				chkAll.addFocusListener(new FocusCheck("一括チェック"));
			}
			this.add(chkAll);

				ItemIndicationLabel iiAll = new ItemIndicationLabel();
				iiAll.setBounds(35, 555, 150, 15);
				iiAll.setText("一括チェック／解除");
				this.add(iiAll);

			//分析値の変更確認
			ButtonBase btnHankou = new ButtonBase("分析値の変更確認");
			btnHankou.setBounds(194, 555, 180, 20);
			btnHankou.addActionListener(this.getActionEvent());
			btnHankou.setActionCommand(this.BUNSEKI_CHK);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0091, DataCtrl.getInstance().getParamData().getStrMode())){
				btnHankou.setEnabled(false);
			}
			this.add(btnHankou);

// ADD start 20121016 QP@20505 No.11
			//分析値の変更確認
			btnBunsekiMstData = new ButtonBase("分析マスタの最新情報に更新");
			btnBunsekiMstData.setBounds(394, 555, 180, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0155, DataCtrl.getInstance().getParamData().getStrMode())){
				btnBunsekiMstData.setEnabled(false);
			}
			this.add(btnBunsekiMstData);
// ADD end 20121016 QP@20505 No.11

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表② コンポーネント初期化処理が失敗しました");
			ex.setStrErrmsg("特性値 コンポーネント初期化処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * テーブル初期化処理
	 * @param aryTrialData : 試作データ
	 * @throws ExceptionBase
	 */
	private void initTable(Object[] aryTrialData) throws ExceptionBase {
		TrialData trialData;
		try {
			//行・列数
			int intRowCount = 21;
			int intColumnCount = aryTrialData.length;

			//テーブル生成
			//2011/04/20 QP@10181_No.41 TT T.Satoh Change Start -------------------------
			//JScrollPane scroll;
			//2011/04/20 QP@10181_No.41 TT T.Satoh Change End ---------------------------
			table = new TableBase(intRowCount,intColumnCount){
				//--------------------　試作列データ更新　----------------------------
				public void editingStopped( ChangeEvent e ){
					try{
						super.editingStopped( e );

		// ADD start 20120927 QP@20505 No.24 工程パターン別表示形式
						//工程パターン取得
						String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
						String ptValue = "";
						if(ptKotei == null || ptKotei.length() <= 0){
						}else{
							//工程パターンが空白ではない場合、Value1取得
							ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
						}
		// ADD end 20120927 QP@20505 No.24 工程パターン別表示形式

						//編集行列番号取得
						int row = getSelectedRow();
						int column = getSelectedColumn();
						if( row>=0 && column>=0 ){
							//キー項目取得
							MiddleCellEditor mceSeq = (MiddleCellEditor)this.getCellEditor(0, column);
							DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
							TextboxBase chkSeq = (TextboxBase)dceSeq.getComponent();
							int intSeq  = Integer.parseInt(chkSeq.getPk1());
							//総酸
					    	if(row == 1){
					    		//モード編集
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0072, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousan(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//食塩
					    	if(row == 2){
					    		//モード編集
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0073, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuen(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//水相中酸度
					    	if(row == 3){
					    		//モード編集
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0074, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSando(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//水相中食塩
					    	if(row == 4){
					    		//モード編集
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0075, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSyokuen(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//水相中酢酸
					    	if(row == 5){
					    		//モード編集
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0076, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSakusan(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}

// ADD start 20120927 QP@20505 No.24
							if(ptValue.equals("") || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
								// 工程パターン「その他・加食」または、未選択の場合
// ADD start 20120927 QP@20505 No.24
						    	//糖度
						    	if(row == 6){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0077, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuToudo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//粘度
						    	if(row == 7){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0078, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuNendo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//温度
						    	if(row == 8){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0079, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuOndo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//PH
						    	if(row == 9){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0080, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPh(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//総酸分析
						    	if(row == 10){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0081, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanBunseki(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//食塩分析
						    	if(row == 11){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0082, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenBunseki(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//比重
						    	if(row == 12){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0083, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);

							    		//原価原料データ　比重 更新
							    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0139
							    			);

	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
							    		//充填量を計算
										String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
										DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0134);
	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　End

									}
						    	}

	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
						    	//水相比重
						    	if(row == 13){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);

							    		//製品比重を計算
							    		String keisan = DataCtrl.getInstance().getTrialTblData().KeisanSeihinHiju(intSeq);
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
							    		//表示値設定
							    		this.setValueAt(keisan, 12, column);

							    		//水相充填量を計算
										String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
										DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0134);

										//油相充填量を計算
										String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
										DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan2),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0135);
									}
						    	}
	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start

						    	//水分活性
						    	if(row == 14){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0084, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeSuibunKasei(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//アルコール
						    	if(row == 15){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0085, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeAlchol(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//フリー内容1
						    	if(row == 16){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0086, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_1(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//フリー内容2
						    	if(row == 17){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0087, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_2(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//フリー内容3
						    	if(row == 18){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0088, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_3(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
// ADD start 20120928 QP@20505 No.24
							}else{
								// ---------  工程パターン １液・２液の場合  --------------------------------------------------------------
						    	//実効酢酸濃度
						    	if(row == 6){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0166, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJikkoSakusanNodo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//水相中ＭＳＧ
						    	if(row == 7){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0168, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoMSG(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//PH
						    	if(row == 8){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0080, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPh(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
							    		//QP@20505 2012/10/25 No24 Start
							    		//自動計算がチェックされている場合のみ処理
							    		if(getChkAuto().isSelected()){
							    			//実効酢酸濃度
							    			String jikkoSakusan = DataCtrl.getInstance().getTrialTblData().KeisanJikkoSakusanNodo(intSeq).toString();
							    			DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJikkoSakusanNodo(
							    					intSeq,
							    					DataCtrl.getInstance().getTrialTblData().checkNullDecimal(jikkoSakusan),
							    					DataCtrl.getInstance().getUserMstData().getDciUserid()
							    				);
							    			//表示値設定
							    			this.setValueAt(jikkoSakusan, 6, column);
							    			
							    		}
							    		//QP@20505 2012/10/25 No24 End
									}
						    	}
						    	//比重
						    	if(row == 9){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0083, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);

							    		//原価原料データ　比重 更新
							    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0139
							    			);
							    		//充填量を計算
										String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
										DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0134);
									}
						    	}
						    	//水相比重
						    	if(row == 10){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);

							    		//製品比重を計算
							    		String keisan = DataCtrl.getInstance().getTrialTblData().KeisanSeihinHiju(intSeq);
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
							    		//表示値設定
				// MOD start 20121010 QP@20505 No.24
				//			    		this.setValueAt(keisan, 12, column);
							    		this.setValueAt(keisan, 9, column);
				// MOD start 20121010 QP@20505 No.24

							    		//水相充填量を計算
										String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
										DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0134);

										//油相充填量を計算
										String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
										DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan2),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
							    				JwsConstManager.JWS_COMPONENT_0135);
									}
						    	}
						    	//粘度 フリー
						    	if(row == 11){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0171, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNendo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//温度 フリー
						    	if(row == 12){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0174, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeOndo(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//フリー内容1
						    	if(row == 13){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0086, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_1(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//フリー内容2
						    	if(row == 14){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0087, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_2(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//フリー内容3
						    	if(row == 15){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0088, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_3(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//フリー内容4
						    	if(row == 16){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0177, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_4(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//フリー内容5
						    	if(row == 17){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0180, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_5(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
						    	//フリー内容6
						    	if(row == 18){
						    		//モード編集
									if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0183, DataCtrl.getInstance().getParamData().getStrMode())){
										String insert = getValueAt( row, column ).toString();
							    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_6(
							    				intSeq,
							    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
							    				DataCtrl.getInstance().getUserMstData().getDciUserid()
							    			);
									}
						    	}
							}
// ADD end 20120928 QP@20505 No.24

					    	//作成メモ
					    	if(row == 19){
					    		//モード編集
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0089, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSakuseiMemo(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//評価
					    	if(row == 20){
					    		//モード編集
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0090, DataCtrl.getInstance().getParamData().getStrMode())){
									String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHyouka(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
						}
					}catch(ExceptionBase be){
					}catch(Exception ex){

					}finally{
						//テスト表示
						//DataCtrl.getInstance().getTrialTblData().dispTrial();
					}
				}
			};
			table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//table.setCellSelectionEnabled(true);

			//テーブルサイズ設定
			table.setRowHeight(17);
			table.setRowHeight(19, 90);
			table.setRowHeight(20, 90);

			//スクロールパネル設定
			scroll = new JScrollPane( table ) {
				private static final long serialVersionUID = 1L;
				//ヘッダーを消去
				public void setColumnHeaderView(Component view) {}
			};
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setBackground(Color.WHITE);
			scroll.setBorder(new LineBorder(Color.BLACK, 1));
			scroll.setBounds(194, 25, 800, 523 + 1);
			scroll.setBackground(Color.WHITE);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			scroll.getHorizontalScrollBar().setMaximum(200*intColumnCount);
			scroll.getVerticalScrollBar().setMaximum(1000);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add End ---------------------------
			this.add(scroll,BorderLayout.CENTER);

// ADD start 20120927 QP@20505 No.24 工程パターン別表示形式
			//工程パターン取得
			String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
			String ptValue = "";
			if(ptKotei == null || ptKotei.length() == 0){
			}else{
				//工程パターンが空白ではない場合、Value1取得
				ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
			}
// ADD end 20120927 QP@20505 No.24 工程パターン別表示形式

			//------------------------ テーブルデータ&セルエディタ・レンダラ挿入  ------------------------
			//テーブルカラムモデル取得
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
			TableColumn column = null;
			//列数分ループ
			for(int j = 0;j < columnModel.getColumnCount();j++){
				//列の幅設定
				column = columnModel.getColumn(j);
	            column.setPreferredWidth(150);

				//追加用中間エディタ＆レンダラ生成
				MiddleCellEditor MiddleCellEditor = new MiddleCellEditor(table);
				MiddleCellRenderer MiddleCellRenderer = new MiddleCellRenderer();

				//試作列データ取得（表示順）
				int no=0;
				for(int i=0; i<columnModel.getColumnCount(); i++){
					TrialData chkHyji = (TrialData)aryTrialData[i];
					if((chkHyji.getIntHyojiNo()-1) == j){
						no=i;
					}
				}
				trialData = (TrialData)aryTrialData[no];

				//------------------------------- ヘッダ項目  -----------------------------------
				//コンポーネント生成
				TextboxBase headerTB = new TextboxBase();
				headerTB.setEditable(false);
				headerTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
				headerTB.setPk1(Integer.toString(trialData.getIntShisakuSeq()));
				//セルエディタ&レンダラ生成
				TextFieldCellEditor headerTCE = new TextFieldCellEditor(headerTB);
				TextFieldCellRenderer headerTCR = new TextFieldCellRenderer(headerTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(0, headerTCE);
				MiddleCellRenderer.add(0, headerTCR);
				//データ設定
				table.setValueAt( trialData.getStrSampleNo(), 0, j);

				//--------------------------------- 総酸  ------------------------------------
				//コンポーネント生成
				TextboxBase sosanTB = new TextboxBase();
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0072, DataCtrl.getInstance().getParamData().getStrMode())){
					sosanTB.setBackground(Color.lightGray);
					sosanTB.setEditable(false);
				}
				//セルエディタ&レンダラ生成
				TextFieldCellEditor sosanTCE = new TextFieldCellEditor(sosanTB);
				TextFieldCellRenderer sosanTCR = new TextFieldCellRenderer(sosanTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(1, sosanTCE);
				MiddleCellRenderer.add(1, sosanTCR);
				//データ設定
				table.setValueAt( trialData.getDciSosan(), 1, j);

				//--------------------------------- 食塩  ------------------------------------
				//コンポーネント生成
				TextboxBase shokuenTB = new TextboxBase();
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0073, DataCtrl.getInstance().getParamData().getStrMode())){
					shokuenTB.setBackground(Color.lightGray);
					shokuenTB.setEditable(false);
				}
				//セルエディタ&レンダラ生成
				TextFieldCellEditor shokuenTCE = new TextFieldCellEditor(shokuenTB);
				TextFieldCellRenderer shokuenTCR = new TextFieldCellRenderer(shokuenTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(2, shokuenTCE);
				MiddleCellRenderer.add(2, shokuenTCR);
				//データ設定
				table.setValueAt( trialData.getDciShokuen(), 2, j);

				//------------------------------- 水相中酸度   --------------------------------
				//コンポーネント生成
				TextboxBase suisandoTB = new TextboxBase();
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0074, DataCtrl.getInstance().getParamData().getStrMode())){
					suisandoTB.setBackground(Color.lightGray);
					suisandoTB.setEditable(false);
				}
				//セルエディタ&レンダラ生成
				TextFieldCellEditor suisandoTCE = new TextFieldCellEditor(suisandoTB);
				TextFieldCellRenderer suisandoTCR = new TextFieldCellRenderer(suisandoTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(3, suisandoTCE);
				MiddleCellRenderer.add(3, suisandoTCR);
				//データ設定
				table.setValueAt( trialData.getDciSuiSando(), 3, j);

				//------------------------------- 水相中食塩   --------------------------------
				//コンポーネント生成
				TextboxBase suishokuenTB = new TextboxBase();
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0075, DataCtrl.getInstance().getParamData().getStrMode())){
					suishokuenTB.setBackground(Color.lightGray);
					suishokuenTB.setEditable(false);
				}
				//セルエディタ&レンダラ生成
				TextFieldCellEditor suishokuenTCE = new TextFieldCellEditor(suishokuenTB);
				TextFieldCellRenderer suishokuenTCR = new TextFieldCellRenderer(suishokuenTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(4, suishokuenTCE);
				MiddleCellRenderer.add(4, suishokuenTCR);
				//データ設定
				table.setValueAt( trialData.getDciSuiShokuen(), 4, j);

				//------------------------------- 水相中酢酸   --------------------------------
				//コンポーネント生成
				TextboxBase suisakusanTB = new TextboxBase();
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0076, DataCtrl.getInstance().getParamData().getStrMode())){
					suisakusanTB.setBackground(Color.lightGray);
					suisakusanTB.setEditable(false);
				}
				//セルエディタ&レンダラ生成
				TextFieldCellEditor suisakusanTCE = new TextFieldCellEditor(suisakusanTB);
				TextFieldCellRenderer suisakusanTCR = new TextFieldCellRenderer(suisakusanTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(5, suisakusanTCE);
				MiddleCellRenderer.add(5, suisakusanTCR);
				//データ設定
				table.setValueAt( trialData.getDciSuiSakusan(), 5, j);

// ADD start 20120927 QP@20505 No.24
				if(ptValue.equals("") || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
					// 工程パターン「その他・加食」または、未選択の場合
//ADD start 20120927 QP@20505 No.24
					//---------------------------------- 糖度  -----------------------------------
					//コンポーネント生成
					TextboxBase todoTB = new TextboxBase();
					todoTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0077, DataCtrl.getInstance().getParamData().getStrMode())){
						todoTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor todoTCE = new TextFieldCellEditor(todoTB);
					TextFieldCellRenderer todoTCR = new TextFieldCellRenderer(todoTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(6, todoTCE);
					MiddleCellRenderer.add(6, todoTCR);
					//データ設定
					table.setValueAt( trialData.getStrToudo(), 6, j);

					//---------------------------------- 粘度  -----------------------------------
					//コンポーネント生成
					TextboxBase nendoTB = new TextboxBase();
					nendoTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0078, DataCtrl.getInstance().getParamData().getStrMode())){
						nendoTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor nendoTCE = new TextFieldCellEditor(nendoTB);
					TextFieldCellRenderer nendoTCR = new TextFieldCellRenderer(nendoTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(7, nendoTCE);
					MiddleCellRenderer.add(7, nendoTCR);
					//データ設定
					table.setValueAt( trialData.getStrNendo(), 7, j);

					//---------------------------------- 温度  -----------------------------------
					//コンポーネント生成
					TextboxBase ondoTB = new TextboxBase();
					ondoTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0079, DataCtrl.getInstance().getParamData().getStrMode())){
						ondoTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor ondoTCE = new TextFieldCellEditor(ondoTB);
					TextFieldCellRenderer ondoTCR = new TextFieldCellRenderer(ondoTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(8, ondoTCE);
					MiddleCellRenderer.add(8, ondoTCR);
					//データ設定
					table.setValueAt( trialData.getStrOndo(), 8, j);

					//----------------------------------- PH ------------------------------------
					//コンポーネント生成
					TextboxBase phTB = new TextboxBase();
					phTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0080, DataCtrl.getInstance().getParamData().getStrMode())){
						phTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor phTCE = new TextFieldCellEditor(phTB);
					TextFieldCellRenderer phTCR = new TextFieldCellRenderer(phTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(9, phTCE);
					MiddleCellRenderer.add(9, phTCR);
					//データ設定
					table.setValueAt( trialData.getStrPh(), 9, j);

					//--------------------------------- 総酸分析   --------------------------------
					//コンポーネント生成
					TextboxBase bunsosanTB = new TextboxBase();
					bunsosanTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0081, DataCtrl.getInstance().getParamData().getStrMode())){
						bunsosanTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor bunsosanTCE = new TextFieldCellEditor(bunsosanTB);
					TextFieldCellRenderer bunsosanTCR = new TextFieldCellRenderer(bunsosanTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(10, bunsosanTCE);
					MiddleCellRenderer.add(10, bunsosanTCR);
					//データ設定
					table.setValueAt( trialData.getStrSosanBunseki(), 10, j);

					//--------------------------------- 食塩分析   --------------------------------
					//コンポーネント生成
					TextboxBase bunshokuenTB = new TextboxBase();
					bunshokuenTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0082, DataCtrl.getInstance().getParamData().getStrMode())){
						bunshokuenTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor bunshokuenTCE = new TextFieldCellEditor(bunshokuenTB);
					TextFieldCellRenderer bunshokuenTCR = new TextFieldCellRenderer(bunshokuenTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(11, bunshokuenTCE);
					MiddleCellRenderer.add(11, bunshokuenTCR);
					//データ設定
					table.setValueAt( trialData.getStrShokuenBunseki(), 11, j);

					//----------------------------------- 比重   ----------------------------------
					//コンポーネント生成
					TextboxBase hijuTB = new TextboxBase();
					hijuTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0083, DataCtrl.getInstance().getParamData().getStrMode())){
						hijuTB.setEditable(false);
					}


	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
					//----------------------------------- 水相比重   ----------------------------------
					//コンポーネント生成
					TextboxBase hijuTB_sui = new TextboxBase();
					hijuTB_sui.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
						hijuTB_sui.setEditable(false);
					}

					//----------------------------------- 製品比重・水相比重設定   ----------------------------------
					//参照モードでない場合
					if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){

						//容量単位取得
						String yoryoTani = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrTani();

						//容量単位からValue1取得
						String taniValue1 = "";
						if(yoryoTani == null || yoryoTani.length() == 0){

						}
						else{
							taniValue1 =  DataCtrl.getInstance().getLiteralDataTani().selectLiteralVal1(yoryoTani);
						}
	// DEL start 20120928 QP@20505 No.24
	//					//工程パターン取得
	//					String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
	// DEL end 20120928 QP@20505 No.24
						//工程パターンが「空白」の場合-------------------------------------------------------------------------
	// MOD start 20120928 QP@20505 No.24
	//					if(ptKotei == null || ptKotei.length() == 0){
						if(ptValue.equals("")){
	// MOD end 20120928 QP@20505 No.24
							//製品比重　編集不可
							hijuTB.setEditable(false);
							//水相比重　編集不可
							hijuTB_sui.setEditable(false);

							hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
						}
						//工程パターンが「空白」でない場合
						else{
// DEL start 20120928 QP@20505 No.24
	//						//工程パターンのValue1取得
	//						String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
//							//工程パターンが「調味料１液タイプ」の場合-------------------------------------------------------------
//							if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
//
//								//容量が「ml」の場合
//								if(taniValue1.equals("1")){
//									//製品比重　編集可
//									hijuTB.setEditable(true);
//									//水相比重　編集不可
//									hijuTB_sui.setEditable(false);
//
//									hijuTB.setBackground(Color.white);
//									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//								}
//								//容量が「g」の場合
//								else if(taniValue1.equals("2")){
//									//製品比重　編集不可
//									hijuTB.setEditable(false);
//									//水相比重　編集不可
//									hijuTB_sui.setEditable(false);
//
//									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//								}
//								//容量が「空白」の場合
//								else{
//									//製品比重　編集不可
//									hijuTB.setEditable(false);
//									//水相比重　編集不可
//									hijuTB_sui.setEditable(false);
//
//									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//								}
//							}
//							//工程パターンが「調味料２液タイプ」の場合-------------------------------------------------------------
//							else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
//
//								//容量が「ml」の場合
//								if(taniValue1.equals("1")){
//									//製品比重　編集不可
//									hijuTB.setEditable(false);
//									//水相比重　編集可
//									hijuTB_sui.setEditable(true);
//
//									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//									hijuTB_sui.setBackground(Color.white);
//								}
//								//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -Start
//								//容量が「g」の場合
//								else if(taniValue1.equals("2")){
//									//製品比重　編集不可
//									hijuTB.setEditable(false);
//									//水相比重　編集不可
//									hijuTB_sui.setEditable(false);
//
//									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//								}
//								//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -End
//								//容量が「空白」の場合（ml以外の場合）
//								else{
//									//製品比重　編集不可
//									hijuTB.setEditable(false);
//									//水相比重　編集不可
//									hijuTB_sui.setEditable(false);
//
//									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
//								}
//
//							}
// DEL end 20120928 QP@20505 No.24
							//工程パターンが「その他・加食タイプ」の場合-------------------------------------------------------------
// MOD start 20120928 QP@20505 No.24
//							else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
							if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
// MOD end 20120928 QP@20505 No.24
								//容量が「g」の場合
								if(taniValue1.equals("2")){
									//製品比重　編集不可
									hijuTB.setEditable(false);
									//水相比重　編集不可
									hijuTB_sui.setEditable(false);

									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								}
								//容量が「空白」の場合（g以外の場合）
								else{
									//製品比重　編集不可
									hijuTB.setEditable(false);
									//水相比重　編集不可
									hijuTB_sui.setEditable(false);

									hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
									hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								}
							}
						}
					}
	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

					//セルエディタ&レンダラ生成
					TextFieldCellEditor hijuTCE = new TextFieldCellEditor(hijuTB);
					TextFieldCellRenderer hijuTCR = new TextFieldCellRenderer(hijuTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(12, hijuTCE);
					MiddleCellRenderer.add(12, hijuTCR);
					//データ設定
					table.setValueAt( trialData.getStrHizyu(), 12, j);

					//セルエディタ&レンダラ生成
					TextFieldCellEditor hijuTCE_sui = new TextFieldCellEditor(hijuTB_sui);
					TextFieldCellRenderer hijuTCR_sui = new TextFieldCellRenderer(hijuTB_sui);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(13, hijuTCE_sui);
					MiddleCellRenderer.add(13, hijuTCR_sui);

					//データ設定
					table.setValueAt( trialData.getStrHiju_sui(), 13, j);

					//--------------------------------- 水分活性   --------------------------------
					//コンポーネント生成
					TextboxBase suikaseiTB = new TextboxBase();
					suikaseiTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0084, DataCtrl.getInstance().getParamData().getStrMode())){
						suikaseiTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor suikaseiTCE = new TextFieldCellEditor(suikaseiTB);
					TextFieldCellRenderer suikaseiTCR = new TextFieldCellRenderer(suikaseiTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(14, suikaseiTCE);
					MiddleCellRenderer.add(14, suikaseiTCR);
					//データ設定
	// MOD start 20120928 QP@20505 No.24
	//				table.setValueAt( trialData.getStrSuibun(), 14, j);
					table.setValueAt( trialData.getStrFreeSuibunKasei(), 14, j);
	// MOD end 20120928 QP@20505 No.24

					//--------------------------------- アルコール  ---------------------------------
					//コンポーネント生成
					TextboxBase arukoTB = new TextboxBase();
					arukoTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0085, DataCtrl.getInstance().getParamData().getStrMode())){
						arukoTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor arukoTCE = new TextFieldCellEditor(arukoTB);
					TextFieldCellRenderer arukoTCR = new TextFieldCellRenderer(arukoTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(15, arukoTCE);
					MiddleCellRenderer.add(15, arukoTCR);
					//データ設定
	// MOD start 20120928 QP@20505 No.24
	//				table.setValueAt( trialData.getStrArukoru(), 15, j);
					table.setValueAt( trialData.getStrFreeAlchol(), 15, j);
	// MOD end 20120928 QP@20505 No.24

					//--------------------------------- フリー内容① ------------------------------
					//コンポーネント生成
					TextboxBase free1TB = new TextboxBase();
					free1TB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0086, DataCtrl.getInstance().getParamData().getStrMode())){
						free1TB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor free1TCE = new TextFieldCellEditor(free1TB);
					TextFieldCellRenderer free1TCR = new TextFieldCellRenderer(free1TB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(16, free1TCE);
					MiddleCellRenderer.add(16, free1TCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeNaiyo1(), 16, j);

					//--------------------------------- フリー内容② ------------------------------
					//コンポーネント生成
					TextboxBase free2TB = new TextboxBase();
					free2TB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0087, DataCtrl.getInstance().getParamData().getStrMode())){
						free2TB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor free2TCE = new TextFieldCellEditor(free2TB);
					TextFieldCellRenderer free2TCR = new TextFieldCellRenderer(free2TB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(17, free2TCE);
					MiddleCellRenderer.add(17, free2TCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeNaiyo2(), 17, j);

					//--------------------------------- フリー内容③ ------------------------------
					//コンポーネント生成
					TextboxBase free3TB = new TextboxBase();
					free3TB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0088, DataCtrl.getInstance().getParamData().getStrMode())){
						free3TB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor free3TCE = new TextFieldCellEditor(free3TB);
					TextFieldCellRenderer free3TCR = new TextFieldCellRenderer(free3TB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(18, free3TCE);
					MiddleCellRenderer.add(18, free3TCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeNaiyo3(), 18, j);
// ADD start 20120928 QP@20505 No.24
				}else{
					// ----------  工程パターン １液・２液 の場合  ----------------------------------------------------------------------
					//---------------------------------- 実効酢酸濃度  -----------------------------------
					//コンポーネント生成
					TextboxBase JsnTB = new TextboxBase();
					JsnTB.setBackground(Color.WHITE);
//					//モード編集
//					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
//							JwsConstManager.JWS_COMPONENT_0166, DataCtrl.getInstance().getParamData().getStrMode())){
//						JsnTB.setEditable(false);
//					}
					JsnTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					JsnTB.setEditable(false);

					//セルエディタ&レンダラ生成
					TextFieldCellEditor JsnTCE = new TextFieldCellEditor(JsnTB);
					TextFieldCellRenderer JsnTCR = new TextFieldCellRenderer(JsnTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(6, JsnTCE);
					MiddleCellRenderer.add(6, JsnTCR);
					//データ設定
					table.setValueAt( trialData.getDciJikkoSakusanNodo(), 6, j);
					//---------------------------------- 水相中ＭＳＧ  -----------------------------------
					//コンポーネント生成
					TextboxBase suiMsgTB = new TextboxBase();
					suiMsgTB.setBackground(Color.WHITE);
//					//モード編集
//					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
//							JwsConstManager.JWS_COMPONENT_0168, DataCtrl.getInstance().getParamData().getStrMode())){
//						suiMsgTB.setEditable(false);
//					}
					suiMsgTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					suiMsgTB.setEditable(false);

					//セルエディタ&レンダラ生成
					TextFieldCellEditor suiMsgTCE = new TextFieldCellEditor(suiMsgTB);
					TextFieldCellRenderer suiMsgTCR = new TextFieldCellRenderer(suiMsgTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(7, suiMsgTCE);
					MiddleCellRenderer.add(7, suiMsgTCR);
					//データ設定
					table.setValueAt( trialData.getDciSuisoMSG(), 7, j);
					//----------------------------------- PH ------------------------------------
					//コンポーネント生成
					TextboxBase phTB = new TextboxBase();
					phTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0080, DataCtrl.getInstance().getParamData().getStrMode())){
						phTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor phTCE = new TextFieldCellEditor(phTB);
					TextFieldCellRenderer phTCR = new TextFieldCellRenderer(phTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(8, phTCE);
					MiddleCellRenderer.add(8, phTCR);
					//データ設定
					table.setValueAt( trialData.getStrPh(), 8, j);
					//----------------------------------- 比重   ----------------------------------
					//コンポーネント生成
					TextboxBase hijuTB = new TextboxBase();
					hijuTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0083, DataCtrl.getInstance().getParamData().getStrMode())){
						hijuTB.setEditable(false);
					}
					//----------------------------------- 水相比重   ----------------------------------
					//コンポーネント生成
					TextboxBase hijuTB_sui = new TextboxBase();
					hijuTB_sui.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){
						hijuTB_sui.setEditable(false);
					}
					//----------------------------------- 製品比重・水相比重設定   ----------------------------------
					//参照モードでない場合
					if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0154, DataCtrl.getInstance().getParamData().getStrMode())){

						//容量単位取得
						String yoryoTani = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrTani();
						//容量単位からValue1取得
						String taniValue1 = "";
						if(yoryoTani == null || yoryoTani.length() == 0){
						}
						else{
							taniValue1 =  DataCtrl.getInstance().getLiteralDataTani().selectLiteralVal1(yoryoTani);
						}

						//工程パターンが「調味料１液タイプ」の場合-------------------------------------------------------------
						if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
							//容量が「ml」の場合
							if(taniValue1.equals("1")){
								//製品比重　編集可
								hijuTB.setEditable(true);
								//水相比重　編集不可
								hijuTB_sui.setEditable(false);

								hijuTB.setBackground(Color.white);
								hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
							//容量が「g」の場合
							else if(taniValue1.equals("2")){
								//製品比重　編集不可
								hijuTB.setEditable(false);
								//水相比重　編集不可
								hijuTB_sui.setEditable(false);

								hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
							//容量が「空白」の場合
							else{
								//製品比重　編集不可
								hijuTB.setEditable(false);
								//水相比重　編集不可
								hijuTB_sui.setEditable(false);

								hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
						}
						//工程パターンが「調味料２液タイプ」の場合-------------------------------------------------------------
						else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
							//容量が「ml」の場合
							if(taniValue1.equals("1")){
								//製品比重　編集不可
								hijuTB.setEditable(false);
								//水相比重　編集可
								hijuTB_sui.setEditable(true);

								hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								hijuTB_sui.setBackground(Color.white);
							}
							//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -Start
							//容量が「g」の場合
							else if(taniValue1.equals("2")){
								//製品比重　編集不可
								hijuTB.setEditable(false);
								//水相比重　編集不可
								hijuTB_sui.setEditable(false);

								hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
							//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -End
							//容量が「空白」の場合（ml以外の場合）
							else{
								//製品比重　編集不可
								hijuTB.setEditable(false);
								//水相比重　編集不可
								hijuTB_sui.setEditable(false);

								hijuTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								hijuTB_sui.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
						}
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor hijuTCE = new TextFieldCellEditor(hijuTB);
					TextFieldCellRenderer hijuTCR = new TextFieldCellRenderer(hijuTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(9, hijuTCE);
					MiddleCellRenderer.add(9, hijuTCR);
					//データ設定
					table.setValueAt( trialData.getStrHizyu(), 9, j);

					//セルエディタ&レンダラ生成
					TextFieldCellEditor hijuTCE_sui = new TextFieldCellEditor(hijuTB_sui);
					TextFieldCellRenderer hijuTCR_sui = new TextFieldCellRenderer(hijuTB_sui);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(10, hijuTCE_sui);
					MiddleCellRenderer.add(10, hijuTCR_sui);

					//データ設定
					table.setValueAt( trialData.getStrHiju_sui(), 10, j);
					//---------------------------------- 粘度 フリー  -----------------------------------
					//コンポーネント生成
					TextboxBase nendoTB = new TextboxBase();
					nendoTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0171, DataCtrl.getInstance().getParamData().getStrMode())){
						nendoTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor nendoTCE = new TextFieldCellEditor(nendoTB);
					TextFieldCellRenderer nendoTCR = new TextFieldCellRenderer(nendoTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(11, nendoTCE);
					MiddleCellRenderer.add(11, nendoTCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeNendo(), 11, j);
					//---------------------------------- 温度 フリー  -----------------------------------
					//コンポーネント生成
					TextboxBase ondoTB = new TextboxBase();
					ondoTB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0174, DataCtrl.getInstance().getParamData().getStrMode())){
						ondoTB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor ondoTCE = new TextFieldCellEditor(ondoTB);
					TextFieldCellRenderer ondoTCR = new TextFieldCellRenderer(ondoTB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(12, ondoTCE);
					MiddleCellRenderer.add(12, ondoTCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeOndo(), 12, j);
					//--------------------------------- フリー内容① ------------------------------
					//コンポーネント生成
					TextboxBase free1TB = new TextboxBase();
					free1TB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0086, DataCtrl.getInstance().getParamData().getStrMode())){
						free1TB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor free1TCE = new TextFieldCellEditor(free1TB);
					TextFieldCellRenderer free1TCR = new TextFieldCellRenderer(free1TB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(13, free1TCE);
					MiddleCellRenderer.add(13, free1TCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeNaiyo1(), 13, j);
					//--------------------------------- フリー内容② ------------------------------
					//コンポーネント生成
					TextboxBase free2TB = new TextboxBase();
					free2TB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0087, DataCtrl.getInstance().getParamData().getStrMode())){
						free2TB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor free2TCE = new TextFieldCellEditor(free2TB);
					TextFieldCellRenderer free2TCR = new TextFieldCellRenderer(free2TB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(14, free2TCE);
					MiddleCellRenderer.add(14, free2TCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeNaiyo2(), 14, j);
					//--------------------------------- フリー内容③ ------------------------------
					//コンポーネント生成
					TextboxBase free3TB = new TextboxBase();
					free3TB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0088, DataCtrl.getInstance().getParamData().getStrMode())){
						free3TB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor free3TCE = new TextFieldCellEditor(free3TB);
					TextFieldCellRenderer free3TCR = new TextFieldCellRenderer(free3TB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(15, free3TCE);
					MiddleCellRenderer.add(15, free3TCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeNaiyo3(), 15, j);
					//--------------------------------- フリー内容④ ------------------------------
					//コンポーネント生成
					TextboxBase free4TB = new TextboxBase();
					free4TB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0177, DataCtrl.getInstance().getParamData().getStrMode())){
						free4TB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor free4TCE = new TextFieldCellEditor(free4TB);
					TextFieldCellRenderer free4TCR = new TextFieldCellRenderer(free4TB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(16, free4TCE);
					MiddleCellRenderer.add(16, free4TCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeNaiyo4(), 16, j);
					//--------------------------------- フリー内容⑤ ------------------------------
					//コンポーネント生成
					TextboxBase free5TB = new TextboxBase();
					free5TB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0180, DataCtrl.getInstance().getParamData().getStrMode())){
						free5TB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor free5TCE = new TextFieldCellEditor(free5TB);
					TextFieldCellRenderer free5TCR = new TextFieldCellRenderer(free5TB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(17, free5TCE);
					MiddleCellRenderer.add(17, free5TCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeNaiyo5(), 17, j);
					//--------------------------------- フリー内容⑥ ------------------------------
					//コンポーネント生成
					TextboxBase free6TB = new TextboxBase();
					free6TB.setBackground(Color.WHITE);
					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0183, DataCtrl.getInstance().getParamData().getStrMode())){
						free6TB.setEditable(false);
					}
					//セルエディタ&レンダラ生成
					TextFieldCellEditor free6TCE = new TextFieldCellEditor(free6TB);
					TextFieldCellRenderer free6TCR = new TextFieldCellRenderer(free6TB);
					//中間エディタ&レンダラへ登録
					MiddleCellEditor.addEditorAt(18, free6TCE);
					MiddleCellRenderer.add(18, free6TCR);
					//データ設定
					table.setValueAt( trialData.getStrFreeNaiyo6(), 18, j);
				}
// ADD end 20120928 QP@20505 No.24

				//----------------------------------- 作成メモ  -------------------------------
				//セルエディタ&レンダラ生成
				TextAreaCellEditor memoTCE = new TextAreaCellEditor(table);
				memoTCE.getTextArea().setBackground(Color.WHITE);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0089, DataCtrl.getInstance().getParamData().getStrMode())){
					memoTCE.getTextArea().setEditable(false);
				}
				TextAreaCellRenderer memoTCR = new TextAreaCellRenderer();
				memoTCR.setColor(JwsConstManager.SHISAKU_F2_COLOR);
				//DefaultCellRenderer memoTCR = new DefaultCellRenderer(memoTCE.getTextArea());
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(19, memoTCE);
				MiddleCellRenderer.add(19, memoTCR);
				//データ設定
				table.setValueAt( trialData.getStrSakuseiMemo(), 19, j);

				//------------------------------------- 評価  --------------------------------
				//セルエディタ&レンダラ生成
				TextAreaCellEditor hyokaTCE = new TextAreaCellEditor(table);
				hyokaTCE.getTextArea().setBackground(Color.WHITE);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0090, DataCtrl.getInstance().getParamData().getStrMode())){
					hyokaTCE.getTextArea().setEditable(false);
				}
				TextAreaCellRenderer hyokaTCR = new TextAreaCellRenderer();
				hyokaTCR.setColor(JwsConstManager.SHISAKU_F2_COLOR);
				//DefaultCellRenderer hyokaTCR = new DefaultCellRenderer(hyokaTCE.getTextArea());
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(20, hyokaTCE);
				MiddleCellRenderer.add(20, hyokaTCR);
				//データ設定
				table.setValueAt( trialData.getStrHyoka(), 20, j);

				//------------------------------- テーブルカラムへ設定  ---------------------------
				column.setCellEditor(MiddleCellEditor);
				column.setCellRenderer(MiddleCellRenderer);
			}
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表② テーブル初期化処理処理が失敗しました");
			ex.setStrErrmsg("特性値 テーブル初期化処理処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
		}
	}

	/**
	 * 【JW210】 分析値マスタ変更確認 送信XMLデータ作成
	 */
	private void conJW210() throws ExceptionBase{
		int i;
		try{
			//--------------------------- 送信パラメータ格納  -----------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strXmlId = "JW210";
			String strAjaxUrl = DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax();

			//--------------------------- 送信XMLデータ作成  -----------------------------------
			XmlData xmlJW210 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root追加  --------------------------------------
			xmlJW210.AddXmlTag("",strXmlId);
			arySetTag.clear();

			//-------------------------- 機能ID追加（USERINFO）  -------------------------------
			xmlJW210.AddXmlTag(strXmlId, "USERINFO");
			//　テーブルタグ追加
			xmlJW210.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW210.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();

			//------------------------ 機能ID追加（分析値変更確認）  ----------------------------
			xmlJW210.AddXmlTag(strXmlId, "SA590");
			//　テーブルタグ追加
			xmlJW210.AddXmlTag("SA590", "table");

			//配合データ
			ArrayList aryHaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for ( i=0; i<aryHaigoData.size(); i++  ) {
				MixedData mixedData = (MixedData)aryHaigoData.get(i);
				//レスポンスデータの設定
				arySetTag.add(new String[]{"cd_kaisha",checkNull(mixedData.getIntKaishaCd())});		//会社コード(複数)
				arySetTag.add(new String[]{"cd_busho",checkNull(mixedData.getIntBushoCd())});		//部署コード(複数)
				arySetTag.add(new String[]{"cd_genryo",checkNull(mixedData.getStrGenryoCd())});		//原料コード(複数)
				arySetTag.add(new String[]{"nm_genryo",checkNull(mixedData.getStrGenryoNm())});		//原料名(複数)
				arySetTag.add(new String[]{"tanka",checkNull(mixedData.getDciTanka())});			//単価(複数)
				arySetTag.add(new String[]{"budomari",checkNull(mixedData.getDciBudomari())});		//歩留(複数)
				arySetTag.add(new String[]{"ritu_abura",checkNull(mixedData.getDciGanyuritu())});	//油含有率(複数)
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(mixedData.getDciSakusan())});	//酢酸(複数)
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(mixedData.getDciShokuen())});	//食塩(複数)
				arySetTag.add(new String[]{"ritu_sousan",checkNull(mixedData.getDciSosan())});		//総酸(複数)
				//ADD start 20121031 QP@20505
				arySetTag.add(new String[]{"ritu_msg",checkNull(mixedData.getDciMsg())});			//MSG
				//ADD end 20121031 QP@20505
				
				xmlJW210.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();
			}

			//---------------------------------- XML送信  ------------------------------------
			//xmlJW210.dispXml();

			XmlData xmlJW = xmlJW210;
			XmlConnection xmlConnection = new XmlConnection(xmlJW210);
			xmlConnection.setStrAddress(strAjaxUrl);
			xmlConnection.XmlSend();

			//---------------------------------- XML受信  ------------------------------------
			xmlJW210 = xmlConnection.getXdocRes();

			//xmlJW210.dispXml();
			//　テストXMLデータ
			//xmlJW210 = new XmlData(new File("src/main/JW210.xml"));
			//xmlJW.dispXml();

			// 分析原料確認データに格納
			DataCtrl.getInstance().getMaterialMstData().setMaterialChkData(xmlJW210);

			//--------------------------------- Resultデータ  ----------------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW210);
			if ( DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				//検索結果が存在した場合
				DataCtrl.getInstance().getMessageCtrl().PrintMessageGenryoCheck();
			} else {
				//検索結果が存在しない場合
				throw new Exception();
			}

		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("  送信XMLデータ作成処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
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
					//---------------------------- 自動計算  -----------------------------
					if(komoku == "自動計算"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//自動計算
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJidouKeisanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//---------------------------- 総酸・食塩  ----------------------------
					if(komoku == "総酸・食塩"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//総酸
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//食塩
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//----------------------- 水相中酸度・食塩・酢酸 -----------------------
					if(komoku == "水相中酸度・食塩・酢酸"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//水相中酸度
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSandoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//水相中食塩
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSyokuenFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//水相中酢酸
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSakusanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//------------------------------- 糖度 ------------------------------
					if(komoku == "糖度"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//糖度
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuToudoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//---------------------------- 粘度・温度  ----------------------------
					if(komoku == "粘度・温度"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//粘度
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuNendoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//温度
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuOndoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//-------------------------------- PH  -----------------------------
					if(komoku == "PH"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//PH
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPhFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//------------------------- 総酸分析・食塩分析  ------------------------
					if(komoku == "総酸分析・食塩分析"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//総酸分析
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanBunsekiFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//食塩分析
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenBunsekiFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//------------------------------- 比重 ------------------------------
					if(komoku == "比重"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//比重
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHijuFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
					//------------------------------- 比重 ---------------------------
					if(komoku == "水相比重"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//比重
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHijuFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End -------------------------

					//----------------------------- 水分活性 フリー タイトル  ------------------------
					if(komoku == "水分活性フリー"){
						String insert = (((TextboxBase)jc).getText());
						//水分活性
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuibunKaseiFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- アルコール フリー タイトル -----------------------------
					if(komoku == "アルコールフリー"){
						String insert = (((TextboxBase)jc).getText());
						//アルコール
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuArukoruFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//---------------------------- フリータイトル1 --------------------------
					if(komoku == "フリータイトル1"){
						String insert = (((TextboxBase)jc).getText());
						//フリータイトル1
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_1(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- フリータイトル1Fg  ------------------------
					if(komoku == "フリータイトル1Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//フリータイトル1Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_1(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//---------------------------- フリータイトル2  -------------------------
					if(komoku == "フリータイトル2"){
						String insert = (((TextboxBase)jc).getText());
						//フリータイトル2
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_2(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- フリー2Fg  ------------------------
					if(komoku == "フリータイトル2Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//フリータイトル2Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_2(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//---------------------------- フリータイトル3  -------------------------
					if(komoku == "フリータイトル3"){
						String insert = (((TextboxBase)jc).getText());
						//フリータイトル3
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_3(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- フリータイトル3Fg  ------------------------
					if(komoku == "フリータイトル3Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//フリータイトル3Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_3(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
// ADD start 20121001 QP@20505 No.1
					//----------------------------- 水分活性フリータイトル  ------------------------
					if(komoku == "水分活性フリータイトル"){
						String insert = (((TextboxBase)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_SuibunKasei(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- 水分活性フリーFlg  ------------------------
					if(komoku == "水分活性フリー出力"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeSuibunKaseiFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- アルコールフリータイトル -----------------------------
					if(komoku == "アルコールフリータイトル"){
						String insert = (((TextboxBase)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Alchol(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- アルコールフリーFlg -----------------------------
					if(komoku == "アルコールフリー出力"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeAlcholFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- 実効酢酸濃度 ------------------------
					if(komoku == "実効酢酸濃度"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJikkoSakusanNodoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- 水相中ＭＳＧ ------------------------
					if(komoku == "水相中ＭＳＧ"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoMSGFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- 粘度フリータイトル ------------------------
					if(komoku == "粘度フリータイトル"){
						String insert = (((TextboxBase)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Nendo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- 粘度フリー Flg ------------------------
					if(komoku == "粘度フリー出力"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNendoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeOndoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//----------------------------- 温度フリータイトル ------------------------
					if(komoku == "温度フリータイトル"){
						String insert = (((TextboxBase)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Ondo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
//					//----------------------------- 温度フリー Flg ------------------------
//					if(komoku == "温度フリー出力"){
//						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
//						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeOndoFg(
//								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
//								DataCtrl.getInstance().getUserMstData().getDciUserid()
//							);
//					}
					//---------------------------- フリータイトル4 --------------------------
					if(komoku == "フリータイトル4"){
						String insert = (((TextboxBase)jc).getText());
						//フリータイトル4
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_4(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- フリータイトル4Fg  ------------------------
					if(komoku == "フリータイトル4Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//フリータイトル4Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_4(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//---------------------------- フリータイトル5  -------------------------
					if(komoku == "フリータイトル5"){
						String insert = (((TextboxBase)jc).getText());
						//フリータイトル5
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_5(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- フリータイトル5Fg  ------------------------
					if(komoku == "フリータイトル5Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//フリータイトル5Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_5(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//---------------------------- フリータイトル6  -------------------------
					if(komoku == "フリータイトル6"){
						String insert = (((TextboxBase)jc).getText());
						//フリータイトル6
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_6(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
					//--------------------------- フリータイトル6Fg  ------------------------
					if(komoku == "フリータイトル6Fg"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//フリータイトル6Fg
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_6(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}
// ADD end 20121001 QP@20505 No.1

					//---------------------------- 一括チェック  ----------------------------
					if(komoku == "一括チェック"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						//自動計算
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJidouKeisanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//総酸
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//食塩
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//水相中酸度
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSandoFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//水相中食塩
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSyokuenFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
						//水相中酢酸
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSakusanFg(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
// ADD start 20121002 QP@20505 No.24
						//工程パターン取得
						String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
						String ptValue = "";
						if(ptKotei == null || ptKotei.length() == 0){
						}else{
							//工程パターンが空白ではない場合、Value1取得
							ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
						}
						if(ptValue.equals("") || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
							// 工程パターン「その他・加食」または、未選択の場合
// ADD end 20121002 QP@20505 No.24
							//糖度
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuToudoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//粘度
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuNendoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//温度
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuOndoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//PH
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPhFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//総酸分析
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanBunsekiFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//食塩分析
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenBunsekiFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//比重
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHijuFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);

	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
							//水相比重
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHijuFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
	//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

	//2012/11/01 QP@20505 MOD Start
							//水分活性
//							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuibunKaseiFg(
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeSuibunKaseiFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//アルコール
//							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuArukoruFg(
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeAlcholFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
	//2012/11/01 QP@20505 MOD Start
							//フリータイトル1Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_1(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//フリータイトル2Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_2(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//フリータイトル3Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_3(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
// ADD start 20121002 QP@20505 No.24
						}else{
							// 工程パターン1液・2液の場合
							//実効酢酸濃度
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJikkoSakusanNodoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//水相中ＭＳＧ
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoMSGFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//PH
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPhFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//比重
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHijuFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//水相比重
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHijuFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//粘度フリー
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNendoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//温度フリー
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeOndoFg(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//フリータイトル1Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_1(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//フリータイトル2Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_2(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//フリータイトル3Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_3(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//フリータイトル4Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_4(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//フリータイトル5Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_5(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
							//フリータイトル6Fg
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_6(
									DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);
						}
// ADD end 20121002 QP@20505 No.24
					}
				}
			}catch(ExceptionBase eb){

			}catch(Exception ex){

			}finally{
				//テスト表示
				//DataCtrl.getInstance().getTrialTblData().dispTrial();
			}
	    }
		public void focusGained( FocusEvent e ){
	    }
	}


	/**
	 * チェック切り替え
	 * @param blnChk : チェック値
	 * @param flgKoteiP : 工程パターン別の切り替えフラグ（1:1液・2液、0:その他・加食）
	 */
	private void setCheckboxSelectedAll(boolean blnChk, int flgKoteiP) {
//		chkAuto.setSelected(blnChk);
		chkSo_sho.setSelected(blnChk);
		chkSui.setSelected(blnChk);
// ADD start 20121002 QP@20505 No.24
		if (flgKoteiP == 0){
// ADD end 20121002 QP@20505 No.24
			chkTodo.setSelected(blnChk);
			chkOndo.setSelected(blnChk);
			chkPh.setSelected(blnChk);
			chkBun.setSelected(blnChk);
			chkHiju.setSelected(blnChk);
	// MOD start 20121002 QP@20505 No.24 項目名フリーに変更
	//		chkKasei.setSelected(blnChk);
	//		chkAruko.setSelected(blnChk);
			chkFreeSuibunKassei.setSelected(blnChk);
			chkFreeAlchol.setSelected(blnChk);
	// MOD start 20121002 QP@20505 No.24
			chkFree1.setSelected(blnChk);
			chkFree2.setSelected(blnChk);
			chkFree3.setSelected(blnChk);
	//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
			chkHiju_sui.setSelected(blnChk);
	//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------
// ADD start 20121002 QP@20505 No.24
		}else{
			chkJikkoSakusanNodo.setSelected(blnChk);
			chkSuisoMSG.setSelected(blnChk);
			chkFreeNendo.setSelected(blnChk);
			//chkFreeOndo.setSelected(blnChk);
			chkHiju.setSelected(blnChk);
			chkHiju_sui.setSelected(blnChk);
			chkPh.setSelected(blnChk);
			chkFree1.setSelected(blnChk);
			chkFree2.setSelected(blnChk);
			chkFree3.setSelected(blnChk);
			chkFree4.setSelected(blnChk);
			chkFree5.setSelected(blnChk);
			chkFree6.setSelected(blnChk);
		}
// ADD end 20121002 QP@20505 No.24
	}

	/**
	 * イベント処理の取得
	 * @return ActionListenerクラス
	 */
	private ActionListener getActionEvent() {
		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String eventNm = e.getActionCommand();

				try {
// ADD start 20121002 QP@20505 No.24
					//工程パターン取得
					int flgKoteiP = 0;
					String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
					String ptValue = "";
					if(ptKotei == null || ptKotei.length() == 0){
					}else{
						//工程パターンが空白ではない場合、Value1取得
						ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
					}
					if(ptValue.equals("") || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
						// 工程パターン「その他・加食」または、未選択の場合
						flgKoteiP = 0;
					}else{
						flgKoteiP = 1;
					}
// ADD end 20121002 QP@20505 No.24
					//---------- 一括チェック/解除チェックボックス ActionEvent -----------------------
					if ( eventNm.equals(IKKATU_CHK) ) {
						if ( chkAll.isSelected() ) {
							//全チェック
							setCheckboxSelectedAll(true, flgKoteiP);
						} else {
							//全解除
							setCheckboxSelectedAll(false, flgKoteiP);
						}

					//----------------------- 分析値マスタ変更確認 ------------------------------
					} else if ( eventNm.equals(BUNSEKI_CHK) ) {
						//分析値マスタ変更確認
						conJW210();

					}

				} catch (ExceptionBase eb) {

					DataCtrl.getInstance().PrintMessage(eb);

				} catch (Exception ec) {

					ec.printStackTrace();
					//エラー設定
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
					//ex.setStrErrmsg("試作表② イベント処理が失敗しました");
					ex.setStrErrmsg("特性値 イベント処理が失敗しました");
					//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg(ec.getMessage());
					DataCtrl.getInstance().PrintMessage(ex);

				} finally {

					//DataCtrl.getInstance().getTrialTblData().dispTrial();

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

		try {

			//試作品データ　更新
			table.editingStopped(new ChangeEvent(new Object()));

		} catch (Exception ec) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表② 原価試算更新処理が失敗しました");
			ex.setStrErrmsg("特性値 原価試算更新処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;

		} finally {

		}

	}
	
	//QP@20505 2012/11/01 ADD Start
	/**
	 *  画面クリア処理
	 *    空白、その他加食タイプ→1液2液タイプへと工程パターン切替時の画面初期化処理
	 */
	public void afterEkiTypeDispClear(){

// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 課題No.11
	//	txtFreeSuibunKassei.setText("水分活性");//水分活性
//		txtFreeSuibunKassei.setText("");//水分活性
	// MOD end 20121128 QP@20505 課題No.11
		txtFreeSuibunKassei.setText("水分活性");
// MOD end 20130215 QP@20505
		this.add(txtFreeSuibunKassei);
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 課題No.11
	//	txtFreeAlchol.setText("アルコール");	//アルコール
//		txtFreeAlchol.setText("");	//アルコール
	// MOD end 20121128 QP@20505 課題No.11
		txtFreeAlchol.setText("アルコール");
// MOD end 20130215 QP@20505
		this.add(txtFreeAlchol);
		txtFree1.setText("");					//フリータイトル１
		this.add(txtFree1);
		txtFree2.setText("");					//フリータイトル２
		this.add(txtFree2);
		txtFree3.setText("");					//フリータイトル３
		this.add(txtFree3);
		
		chkFreeSuibunKassei.setSelected(false);	//フリー水分活性チェック
		chkFreeAlchol.setSelected(false);		//フリーアルコールチェック
		chkFree1.setSelected(false);			//フリー１チェック
		chkFree2.setSelected(false);			//フリー２チェック
		chkFree3.setSelected(false);			//フリー３チェック
		
		for(int i = 0; i < table.getColumnCount(); i++){
			table.setValueAt("" ,16 ,i );		//フリー１値
			table.setValueAt("" ,17 ,i );		//フリー２値
			table.setValueAt("" ,18 ,i );		//フリー３値
		}
		
	}
	
	/**
	 *  画面クリア処理
	 *    1液2液タイプ→空白、その他加食タイプへと工程パターン切替時の画面初期化処理
	 */
	public void afterOtherTypeDispClear(){
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 課題No.11
	//	txtNendo.setText("粘度");
//		txtNendo.setText("");
	// MOD end 20121128 QP@20505 課題No.11
		txtNendo.setText("粘度");
// MOD end 20130215 QP@20505
		this.add(txtNendo);
// MOD start 20130215 QP@20505
	// MOD start 20121128 QP@20505 課題No.11
	//	txtOndo.setText("温度（℃）");
//		txtOndo.setText("");
	// MOD end 20121128 QP@20505 課題No.11
		txtOndo.setText("温度（℃）");
// MOD end 20130215 QP@20505
		this.add(txtOndo);
		
		txtFree1.setText("");					//フリータイトル１
		this.add(txtFree1);
		txtFree2.setText("");					//フリータイトル２
		this.add(txtFree2);
		txtFree3.setText("");					//フリータイトル３
		this.add(txtFree3);
		txtFree4.setText("");					//フリータイトル４
		this.add(txtFree4);
		txtFree5.setText("");					//フリータイトル５
		this.add(txtFree5);
		txtFree6.setText("");					//フリータイトル６
		this.add(txtFree6);
		
		chkFreeNendo.setSelected(false);		//フリー粘度温度チェック
		chkFree1.setSelected(false);			//フリー１チェック
		chkFree2.setSelected(false);			//フリー２チェック
		chkFree3.setSelected(false);			//フリー３チェック
		chkFree4.setSelected(false);			//フリー４チェック
		chkFree5.setSelected(false);			//フリー５チェック
		chkFree6.setSelected(false);			//フリー６チェック
		
		for(int i = 0; i < table.getColumnCount(); i++){
			table.setValueAt("" ,13 ,i );		//フリー１値
			table.setValueAt("" ,14 ,i );		//フリー２値
			table.setValueAt("" ,15 ,i );		//フリー３値
			table.setValueAt("" ,16 ,i );		//フリー４値
			table.setValueAt("" ,17 ,i );		//フリー５値
			table.setValueAt("" ,18 ,i );		//フリー６値
		}
	}
	//QP@20505 2012/11/01 ADD End

	/**
	 * NULLチェック処理（オブジェクト）
	 * @throws ExceptionBase
	 */
	private String checkNull(Object val){
		String ret = "NULL";
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
		String ret = "NULL";
		if(val >= 0){
			ret = Integer.toString(val);
		}
		return ret;
	}

	/**
	 * @return chkAuto
	 */
	public CheckboxBase getChkAuto() {
		return chkAuto;
	}
	
// ADD start 20121017 QP@20505 No.11
	/**
	 * @return chkAuto
	 */
	public ButtonBase getCmdBunsekiMstData() {
		return btnBunsekiMstData;
	}
// ADD end 20121017 QP@20505 No.11

	/**
	 * @return table
	 */
	public TableBase getTable() {
		return table;
	}

	//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 *
	 *   scrollゲッター
	 *   @author TT satoh
	 *   @return scroll :　特性値のスクロールバー
	 *
	 ************************************************************************************/
	public JScrollPane getScroll() {
		return scroll;
	}

	/************************************************************************************
	 *
	 *   scrollセッター
	 *   @author TT satoh
	 *   @param _scroll : 特性値のスクロールバー
	 *
	 ************************************************************************************/
	public void setScroll(JScrollPane _scroll) {
		scroll = _scroll;
	}
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

}

