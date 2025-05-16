package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.CostMaterialData;
import jp.co.blueflag.shisaquick.jws.base.HansekiData;
import jp.co.blueflag.shisaquick.jws.base.KaishaData;
import jp.co.blueflag.shisaquick.jws.base.LiteralData;
import jp.co.blueflag.shisaquick.jws.base.ManufacturingData;
import jp.co.blueflag.shisaquick.jws.base.MixedData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeListData;
import jp.co.blueflag.shisaquick.jws.base.ShisanData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.cellrenderer.ComboBoxCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.CheckboxBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;
import jp.co.blueflag.shisaquick.jws.label.HaihunLabel;
import jp.co.blueflag.shisaquick.jws.label.HeaderLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemIndicationLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.label.LevelLabel;
import jp.co.blueflag.shisaquick.jws.manager.DownloadPathData;
import jp.co.blueflag.shisaquick.jws.manager.UrlConnection;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.tab.TrialTab;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/************************************************************************************************
 *
 * 試作データ画面パネルクラス
 * @author TT nishigawa
 *
 ***********************************************************************************************/
public class TrialDispPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	//エラー操作
	private ExceptionBase ex;

	//項目コンポーネント
	private DispTitleLabel DispTitleLabel;
	private LevelLabel LevelLabel;
	private HaihunLabel HaihunLabel;
	private HeaderLabel HeaderLabel;

	//データ&通信オブジェクト
	private XmlConnection xcon;
	private XmlData xmlJW010;
	private XmlData xmlJW020;
	private XmlData xmlJW030;
	private XmlData xmlJW040;
	private XmlData xmlJW050;
	private XmlData xmlJW060;
	private XmlData xmlJW070;
	private XmlData xmlJ010;
	private XmlData xmlJW120;		//試作表出力
	private XmlData xmlJW130;		//サンプル説明書出力
	private XmlData xmlJW740;		//栄養計算書出力
	private XmlData xmlJWuser;
	private PrototypeData PrototypeData;
	private TrialData TrialData = null;
	private ArrayList aryTrialData = new ArrayList();

	//試作&製法No
	private String seiho_user = null;
	private String seiho_shubetu = null;
	private String seiho_nen = null;
	private String seiho_num = null;
	private String seiho_Shisan = null;
	private String seiho_kakutei = null;

	//コンポーネント設定
	private String strTitle = "試作データ画面";
	private int red = 0xff;
	private int green = 0xff;
	private int blue = 0xff;

	//画面内コンポーネント
	private TrialTab tb;
	private TextboxBase txtShisakuUser;
	private TextboxBase txtShisakuNen;
	private TextboxBase txtShisakuOi;
	private TextboxBase txtIrai;
	private ComboBase cmbShubetuNo;
	private TextboxBase txtHinnm;
	private TextboxBase txtSeihoUser;
	private TextboxBase txtSeihoShu;
	private TextboxBase txtSeihoNen;
	private TextboxBase txtSeihoOi;
	private ItemIndicationLabel ilTorokuHi;
	private ItemIndicationLabel ilKosinHi;
	private ItemIndicationLabel ilTorokuSha;
	private ItemIndicationLabel iiShisanHi;
	private ItemIndicationLabel ilKosinSha;
	private ItemIndicationLabel iiKakuteiHi;
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
	private ItemIndicationLabel ilSecret;
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
	private CheckboxBase chkHaisi;
	private ButtonBase btnToroku;
	private ButtonBase btnShuryo;
	private ButtonBase btnTcopy;
	private ButtonBase btnZcopy;
	private ButtonBase btnCtrlZ;
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
	private ButtonBase btnSecret;
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	private ComboBase cmbKoteiPtn;
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20121009 QP@20505 No.24
	JTabbedPane jtab;
// ADD end 201210093 QP@20505 No.24

	/*******************************************************************************************
	 *
	 * 試作データ画面パネル コンストラクタ
	 * @throws ExceptionBase
	 * @author TT nishigawa
	 *
	 ******************************************************************************************/
	public TrialDispPanel() throws ExceptionBase {
		//スーパークラスのコンストラクタ呼び出し
		super();

		try {
			//モード取得
			String mode = DataCtrl.getInstance().getParamData().getStrMode();

			//ユーザ情報取得
			this.conUserInfo();

			//データ設定（JW010）
			this.conJW010();

			//パネル画面の設定
			this.panelDisp();

			//データの設定
			this.setPanelData();

			//タブの初期選択
			if(mode.equals(JwsConstManager.JWS_MODE_0002)){
				tb.setSelectedIndex(2);
			}

			//2011/06/07 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//配合表の試作明細テーブルの横スクロールバーの最大値を取得
			int hHaigoBarMax = tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().getMaximum();

			//配合表の試作明細テーブルの横スクロールバーの位置を設定
			tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().setValue(hHaigoBarMax);
			//2011/06/07 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//タブ選択イベント追加
			tb.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {

					//原価試算画面 フォーカス解除
			    	TableBase GenkaTable = tb.getTrial5Panel().getTable();
			    	TableCellEditor GenkaTableEditor = GenkaTable.getCellEditor();
					if(GenkaTableEditor != null){
						GenkaTable.getCellEditor().stopCellEditing();
					}
					GenkaTable.clearSelection();

					try{
// MOD start 20121009 QP@20505 No.24
//						JTabbedPane jtab = (JTabbedPane)e.getSource();
						jtab = (JTabbedPane)e.getSource();
// MOD end 20121009 QP@20505 No.24
					    int sindex = jtab.getSelectedIndex();

				    	//特性値(試作表②)画面 原価試算更新処理
				    	tb.getTrial2Panel().updGenkaShisan();
				    	//基本情報(試作表③)画面 原価試算更新処理
				    	tb.getTrial3Panel().updGenkaShisan();
				    	//原価試算画面 原価試算更新処理
				    	tb.getTrial5Panel().updGenkaShisan();

					    if(sindex == 0){

					    	//原価(kg)計算結果を画面.原価(kg)に表示する
					    	tb.getTrial1Panel().getTrial1().DispGenryohi();

					    	//2011/06/07 QP@10181_No.41 TT T.Satoh Add Start -------------------------
							//配合表の試作明細テーブルの横スクロールバーの最大値を取得
							int hHaigoBarMax = tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().getMaximum();

							//配合表の試作明細テーブルの横スクロールバーの位置を設定
							tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().setValue(hHaigoBarMax);
							//2011/06/07 QP@10181_No.41 TT T.Satoh Add End ---------------------------

					    }if(sindex == 1){

					    	//テーブル情報取得
							TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
							TableBase ListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
							TableBase ListHeader = tb.getTrial1Panel().getTrial1().getListHeader();

							//試作ヘッダーの選択情報をクリア
							TableCellEditor shEditor = ListHeader.getCellEditor();
							if(shEditor != null){
								ListHeader.getCellEditor().stopCellEditing();
							}
							ListHeader.clearSelection();

							//配合明細の選択情報をクリア
							TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
							if(hmEditor != null){
								HaigoMeisai.getCellEditor().stopCellEditing();
							}
							HaigoMeisai.clearSelection();

							//試作明細の選択情報をクリア
							TableCellEditor lmEditor = ListMeisai.getCellEditor();
							if(lmEditor != null){
								ListMeisai.getCellEditor().stopCellEditing();
							}
							ListMeisai.clearSelection();

							//--------------------------- 特性値(試作表②)パネル設定 -----------------------------------
							//自動計算
							Trial2Panel tp2 = new Trial2Panel();
// ADD start 20121017 QP@20505 No.11
							CheckboxBase chkAuto = tp2.getChkAuto();
							chkAuto.addActionListener(getActionEvent());
							chkAuto.setActionCommand("autoClick");
							ButtonBase btnBunsekiMstData = tp2.getCmdBunsekiMstData();
							btnBunsekiMstData.addActionListener(getActionEvent());
							btnBunsekiMstData.setActionCommand("click_getBunsekiMstData");
// ADD end 20121017 QP@20505 No.11

							//再表示
							tb.setTrial2Panel(tp2);
							jtab.setComponentAt(1, tp2);

					    }if(sindex == 2){

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
					    	tb.getTrial1Panel().getTrial1().AutoCopyKeisan();
//add end   -------------------------------------------------------------------------------

					    }if(sindex == 3){
					    	//原価試算(試作表⑤)画面処理

					    	//テーブル情報取得
							TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
							TableBase ListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
							TableBase ListHeader = tb.getTrial1Panel().getTrial1().getListHeader();

							//試作ヘッダーの選択情報をクリア
							TableCellEditor shEditor = ListHeader.getCellEditor();
							if(shEditor != null){
								ListHeader.getCellEditor().stopCellEditing();
							}
							ListHeader.clearSelection();
							//配合明細の選択情報をクリア
							TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
							if(hmEditor != null){
								HaigoMeisai.getCellEditor().stopCellEditing();
							}
							HaigoMeisai.clearSelection();
							//試作明細の選択情報をクリア
							TableCellEditor lmEditor = ListMeisai.getCellEditor();
							if(lmEditor != null){
								ListMeisai.getCellEditor().stopCellEditing();
							}
							ListMeisai.clearSelection();

							//--------------------------- 原価試算(試作表⑤)パネル設定 -----------------------------------

							//原価試算(試作表⑤)再表示
							Trial5Panel tp5 = new Trial5Panel();

							//原価試算(試作表⑤)画面のコンボボックス値を設定
							tp5.selectCmbShisanKakutei(tb.getTrial5Panel().getCmbShisanKakutei().getSelectedIndex());

							//原価試算表印刷ボタンイベント処理 再設定
							tp5.getBtnShisanHyo().addActionListener(getActionEvent());
							tp5.getBtnShisanHyo().setActionCommand("shisanHyo");

							//原価試算登録ボタンイベント処理 再設定
							tp5.getBtnShisanToroku().addActionListener(getActionEvent());
							tp5.getBtnShisanToroku().setActionCommand("shisanToroku");

							//再表示
							tb.setTrial5Panel(tp5);
							jtab.setComponentAt(3, tp5);

							//工程チェック
							int intKoteiChk = DataCtrl.getInstance().getTrialTblData().CheckKotei();

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
//							if ( intKoteiChk == 0 || intKoteiChk == -1 || intKoteiChk > 2 ) {
//								//不正時
//
//								//警告メッセージ表示
//								tb.transferFocus();
//								String strMessage = JwsConstManager.JWS_ERROR_0023;
//								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);
//
//							}

							//再表示した場合
							if(JwsConstManager.JWS_FLG_DISP){
								JwsConstManager.JWS_FLG_DISP = false;
							}
							//再表示でない場合
							else{
								if ( intKoteiChk == 0 || intKoteiChk == -1 || intKoteiChk > 2 ) {
									//不正時

									//警告メッセージ表示
									tb.transferFocus();
									String strMessage = JwsConstManager.JWS_ERROR_0023;
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);

								}
							}
//mod end   -------------------------------------------------------------------------------

					    }

					}catch(Exception ex){

					}finally{

					}
				}
			});

		} catch (ExceptionBase e) {
			e.printStackTrace();
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作データ画面パネル コンストラクタ処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/*******************************************************************************************
	 *
	 * 試作データ画面設定
	 * @throws ExceptionBase
	 * @author TT nishigawa
	 *
	 ******************************************************************************************/
	public void setPanelData() throws ExceptionBase{
		try{
			//試作品データ取得
			PrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();

			//試作品データ内に製法試作が登録されている場合
			if(PrototypeData.getIntSeihoShisaku() > 0){

				//試作列データ検索
				aryTrialData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(PrototypeData.getIntSeihoShisaku());

				//試作列データが存在する場合
				if(!aryTrialData.isEmpty()){

					//試作列データ取得
					TrialData = new TrialData();
					TrialData = (TrialData)aryTrialData.get(0);

					//製法No取得（製法No-1）
					String seiho = TrialData.getStrSeihoNo1();
					String[] seiho_sp = seiho.split("-");

					if ( seiho_sp.length == 4 ) {
						seiho_user = seiho_sp[0];
						seiho_shubetu = seiho_sp[1];
						seiho_nen = seiho_sp[2];
						seiho_num = seiho_sp[3];
					}
				}
			//試作品データ内に製法試作が登録されていない場合
			}else{
				seiho_user = null;
				seiho_shubetu = null;
				seiho_nen = null;
				seiho_num = null;
			}

			//タブ設定
			String strKohinHi = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrKosinhi();
			tb.setTrialPane();
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setStrKosinhi(strKohinHi);

			//試作コード
			txtShisakuUser.setText(checkNull(PrototypeData.getDciShisakuUser()));
			txtShisakuNen.setText(checkNull(PrototypeData.getDciShisakuYear()));
			txtShisakuOi.setText(checkNull(PrototypeData.getDciShisakuNum()));

			//依頼番号
			txtIrai.setText(checkNull(PrototypeData.getStrIrai()));

			//種別No
			setLiteralCmb(
					cmbShubetuNo
					,DataCtrl.getInstance().getLiteralDataShubetuNo()
					, ""
					, 1);
			cmbShubetuNo.setSelectedItem(PrototypeData.getStrShubetuNo());
			//txtShubetuNo.setText(checkNull(PrototypeData.getStrShubetuNo()));

			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
			if(PrototypeData.getStrSecret() != null){
				ilSecret.setText("ON");
				ilSecret.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR3);
			}else{
				ilSecret.setText("OFF");
				ilSecret.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR4);
			}
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

			//品名
			txtHinnm.setText(checkNull(PrototypeData.getStrHinnm()));

			//製法NO
			txtSeihoUser.setText(checkNull(seiho_user));
			txtSeihoShu.setText(checkNull(seiho_shubetu));
			txtSeihoNen.setText(checkNull(seiho_nen));
			txtSeihoOi.setText(checkNull(seiho_num));

			//登録日
			ilTorokuHi.setText(checkNull(PrototypeData.getStrTorokuhi()));

			//更新日
			//UPD 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御のため、サーバサイドの更新日を編集
//			ilKosinHi.setText(checkNull(PrototypeData.getStrKosinhi()));
			String strKoshin = checkNull(PrototypeData.getStrKosinhi());
			if(strKoshin.length() >= 10 ){
				strKoshin = strKoshin.substring(0,10);
				strKoshin = strKoshin.replace('-', '/');
			}
			ilKosinHi.setText(strKoshin);

			//登録者
			ilTorokuSha.setText(checkNull(PrototypeData.getStrKosinNm()));

			//試算日
			iiShisanHi.setText(checkNull(seiho_Shisan));

			//更新者
			ilKosinSha.setText(checkNull(PrototypeData.getStrTorokuNm()));

			//試算確定日
			iiKakuteiHi.setText(checkNull(seiho_kakutei));

			//廃止
			if(PrototypeData.getIntHaisi() > 0){
				chkHaisi.setSelected(true);
			}

			//--------------------------- 基本情報(試作表③)パネル設定 -----------------------------------
			//担当会社（モード編集）
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0102, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial3Panel().getCmbKaisha().addFocusListener(new FocusCheck("担当会社"));
			}

			//担当工場（モード編集）
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0103, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial3Panel().getCmbKojo().addFocusListener(new FocusCheck("担当工場"));
			}

			//小数指定
			boolean edit_fg = true;
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0101, DataCtrl.getInstance().getParamData().getStrMode())){
				//tb.getTrial3Panel().getCmbShosu().addFocusListener(new FocusCheck("小数指定"));

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
				//試作列配列取得
				ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
				//試作列ループ
				for(int i=0; i<aryRetu.size(); i++){
					//試作列データ取得
					TrialData TrialData = (TrialData)aryRetu.get(i);
					int intShisakuSeq = TrialData.getIntShisakuSeq();
					//列キー項目取得
					boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);
					//編集可能の場合：既存処理
					if(chk){
						tb.getTrial3Panel().getCmbShosu().addFocusListener(new FocusCheck("小数指定"));
					}
					//編集不可の場合：既存処理
					else{
						edit_fg = false;

						//小数指定編集不可
						tb.getTrial3Panel().getCmbShosu().setEnabled(false);
						tb.getTrial3Panel().getCmbShosu().setBackground(JwsConstManager.JWS_DISABLE_COLOR);
						//容量編集不可
						tb.getTrial3Panel().getCmbYoryo().setEnabled(false);
						tb.getTrial3Panel().getCmbYoryo().setBackground(JwsConstManager.JWS_DISABLE_COLOR);
						tb.getTrial3Panel().getCmbYoryo().getEditor().getEditorComponent().setBackground(JwsConstManager.JWS_DISABLE_COLOR);
						//容量単位編集不可
						tb.getTrial3Panel().getCmbtani().setEnabled(false);
						tb.getTrial3Panel().getCmbtani().setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					}
				}
//add end   -------------------------------------------------------------------------------

			}

//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add Start -------------------------
			//工程パターン
			if(edit_fg){

				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0153, DataCtrl.getInstance().getParamData().getStrMode())){
					cmbKoteiPtn.setBackground(Color.lightGray);
					cmbKoteiPtn.setEnabled(false);
				}
				else{
					//工程パターン編集可
					cmbKoteiPtn.setBackground(Color.white);
					cmbKoteiPtn.setEnabled(true);
				}
			}
			else{
				//工程パターン編集不可
				cmbKoteiPtn.setBackground(Color.lightGray);
				cmbKoteiPtn.setEnabled(false);
			}
//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add Start -------------------------


//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add Start -------------------------
			//容量単位
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0110, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial3Panel().getCmbtani().addFocusListener(new FocusCheck("容量単位"));
			}
//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add End --------------------------

			//--------------------------- 配合表(試作表①)パネル 帳票出力 設定 -----------------------------------
			//サンプル説明書
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0049, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial1Panel().getBtnSample().addActionListener(this.getActionEvent());
				tb.getTrial1Panel().getBtnSample().setActionCommand("sampleSetumei");
			}

			//試作表
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0048, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial1Panel().getBtnShisakuHyo().addActionListener(this.getActionEvent());
				tb.getTrial1Panel().getBtnShisakuHyo().setActionCommand("shisakuHyo");
			}

			//栄養計算書
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0126, DataCtrl.getInstance().getParamData().getStrMode())){
				tb.getTrial1Panel().getBtnEiyoKeisan().addActionListener(this.getActionEvent());
				tb.getTrial1Panel().getBtnEiyoKeisan().setActionCommand("eiyoKeisan");
			}

			//--------------------------- 試算日・試算確定 設定 -----------------------------------

			//最終試算確定を試算データより取得
			ShisanData shisanData = DataCtrl.getInstance().getShisanRirekiKanriData().SearchLastShisanData();

			//最終試算確定した試算日とサンプルNoを、画面項目の試算日と試算確定に設定
			iiShisanHi.setText(shisanData.getStrShisanHi());
			if ( shisanData.getStrSampleNo() != null ) {
				iiKakuteiHi.setText(shisanData.getStrSampleNo());

			} else {
				iiKakuteiHi.setText("");

			}

//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add Start -------------------------
			//工程パターン
			int listCount = cmbKoteiPtn.getActionListeners().length;
			for(int i=0; i<listCount; i++){
				cmbKoteiPtn.removeActionListener(cmbKoteiPtn.getActionListeners()[0]);
			}
			//cmbKoteiPtn.removeActionListener(cmbKoteiPtn.getActionListeners());

			//工程パターン
			setLiteralCmb(
					cmbKoteiPtn
					,DataCtrl.getInstance().getLiteralDataKoteiPtn()
					, PrototypeData.getStrPt_kotei()
					, 0);
			//アクションリスナー追加
			cmbKoteiPtn.addActionListener(getActionEvent());
			cmbKoteiPtn.setActionCommand("工程パターン");

			//setGenkaShisanMark();
//2011/05/11 QP@10181_No.42_49_73 TT Nishigawa Add Start -------------------------

		}catch(Exception e){
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作データ画面設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}
	}

	/*******************************************************************************************
	 *
	 * 試作データ画面パネル表示
	 * @throws ExceptionBase
	 * @author TT nishigawa
	 *
	 ******************************************************************************************/
	private void panelDisp() throws ExceptionBase{
		try{
			//------------------------------ 描画モード設定  -------------------------------------
			UIManager.setLookAndFeel(JwsConstManager.UI_CLASS_NAME);

			//-------------------------------- パネル設定   --------------------------------------
			this.setLayout(null);
			this.setBackground(new Color(red,green,blue));

			//------------------------------ タイトルラベル設定  -----------------------------------
			DispTitleLabel = new DispTitleLabel();
			DispTitleLabel.setText(strTitle);
			this.add(DispTitleLabel);

			//------------------------------ JWSバージョン設定  ----------------------------------
			ItemLabel VerLabel = new ItemLabel();
			VerLabel.setBounds(160, 5, 50, 15);
			VerLabel.setBackground(Color.white);
			VerLabel.setText(JwsConstManager.JWS_VERSION);
			this.add(VerLabel);

			//------------------------------- レベルラベル設定  -----------------------------------
			LevelLabel = new LevelLabel();
			LevelLabel.setBounds(963, 5, 50, 15);
			this.add(LevelLabel);

			//------------------------------- ヘッダラベル設定   -----------------------------------
			HeaderLabel = new HeaderLabel();
			HeaderLabel.setBounds(215, 5, 745, 15);
			this.add(HeaderLabel);

			//-------------------------- 項目ラベル設定（試作コード）  -------------------------------
			ItemLabel hlShisaku = new ItemLabel();
			hlShisaku.setBounds(5, 25, 60, 19);
			hlShisaku.setText("試作ｺｰﾄﾞ");
			this.add(hlShisaku);

			//試作コード（ユーザID）
			txtShisakuUser = new TextboxBase();
			txtShisakuUser.setBounds(65, 25, 80, 20);

			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0001, DataCtrl.getInstance().getParamData().getStrMode())){
				txtShisakuUser.setBounds(65, 25, 80, 19);
				txtShisakuUser.setBackground(Color.lightGray);
				txtShisakuUser.setEditable(false);
			}
			this.add(txtShisakuUser);


			//試作コード（ハイフン）
			HaihunLabel = new HaihunLabel();
			HaihunLabel.setBounds(145, 25, 15, 19);
			this.add(HaihunLabel);

			//試作コード（年）
			txtShisakuNen = new TextboxBase();
			txtShisakuNen.setBounds(160, 25, 30, 20);
			txtShisakuNen.setEditable(false);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0002, DataCtrl.getInstance().getParamData().getStrMode())){
				txtShisakuNen.setBounds(160, 25, 30, 19);
				txtShisakuNen.setBackground(Color.lightGray);
				txtShisakuNen.setEditable(false);
			}
			this.add(txtShisakuNen);

			//試作コード（ハイフン）
			HaihunLabel = new HaihunLabel();
			HaihunLabel.setBounds(190, 25, 15, 20);
			this.add(HaihunLabel);

			//試作コード（追番）
			txtShisakuOi = new TextboxBase();
			txtShisakuOi.setBounds(205, 25, 30, 20);
			txtShisakuOi.setEditable(false);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0003, DataCtrl.getInstance().getParamData().getStrMode())){
				txtShisakuOi.setBounds(205, 25, 30, 19);
				txtShisakuOi.setBackground(Color.lightGray);
				txtShisakuOi.setEditable(false);
			}
			this.add(txtShisakuOi);

			//-------------------------- 項目ラベル設定（依頼番号）  -------------------------------
			ItemLabel hlIrai = new ItemLabel();
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
			//hlIrai.setBounds(270, 25, 60, 19);
			//hlIrai.setText("依頼番号");
			hlIrai.setBounds(254, 25, 76, 19);
			hlIrai.setText("依頼番号IR@");
//mod start --------------------------------------------------------------------------------------
			this.add(hlIrai);

			//依頼番号
			txtIrai = new TextboxBase();
			txtIrai.setBounds(330, 25, 110, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0004, DataCtrl.getInstance().getParamData().getStrMode())){
				txtIrai.setBounds(330, 25, 110, 19);
				txtIrai.setBackground(Color.lightGray);
				txtIrai.setEditable(false);
			}else{
				txtIrai.addFocusListener(new FocusCheck("依頼番号"));
			}
			this.add(txtIrai);

			//----------------------------------- ボタン  ---------------------------------------
			//登録ボタン
			btnToroku = new ButtonBase("登録");
			btnToroku.addActionListener(this.getActionEvent());
			btnToroku.setActionCommand("toroku");
			btnToroku.setBounds(445, 25, 80, 38);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0011, DataCtrl.getInstance().getParamData().getStrMode())){
				btnToroku.setEnabled(false);
			}
			this.add(btnToroku);

			//終了ボタン
			btnShuryo = new ButtonBase("終了");
			btnShuryo.addActionListener(this.getActionEvent());
			btnShuryo.setActionCommand("shuryo");
			btnShuryo.setBounds(525, 25, 80, 38);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0012, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShuryo.setEnabled(false);
			}
			this.add(btnShuryo);

			//特徴コピーボタン
			btnTcopy = new ButtonBase("特徴ｺﾋﾟｰ");
			btnTcopy.addActionListener(this.getActionEvent());
			btnTcopy.setActionCommand("copy_tokutyo");
			btnTcopy.setBounds(445, 63, 80, 38);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0013, DataCtrl.getInstance().getParamData().getStrMode())){
				btnTcopy.setEnabled(false);
			}
			this.add(btnTcopy);

			//全コピーボタン
			btnZcopy = new ButtonBase("全ｺﾋﾟｰ");
			btnZcopy.addActionListener(this.getActionEvent());
			btnZcopy.setActionCommand("copy_all");
			btnZcopy.setBounds(525, 63, 80, 38);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0014, DataCtrl.getInstance().getParamData().getStrMode())){
				btnZcopy.setEnabled(false);
			}
			this.add(btnZcopy);

			//--------------------------- 項目ラベル設定（登録日）  --------------------------------
			ItemLabel hlTorokuHi = new ItemLabel();
			hlTorokuHi.setBounds(610, 25, 50, 15);
			hlTorokuHi.setText("登録日");
			this.add(hlTorokuHi);
			//登録日
			ilTorokuHi = new ItemIndicationLabel();
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD Start
//			ilTorokuHi.setBounds(660, 25, 145, 15);
			ilTorokuHi.setBounds(660, 25, 100, 15);
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD End
			this.add(ilTorokuHi);

			//-------------------------- 項目ラベル設定（種別番号）  -------------------------------
			ItemLabel hlShubetu = new ItemLabel();
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD Start
//			hlShubetu.setBounds(807, 25, 60, 35);
			hlShubetu.setBounds(762, 25, 60, 35);
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD End
			hlShubetu.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
			hlShubetu.setText("種別番号");
			this.add(hlShubetu);
			//種別番号
			cmbShubetuNo = new ComboBase();

			//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD Start
//			cmbShubetuNo.setBounds(867, 25, 70, 35);
			cmbShubetuNo.setBounds(822, 25, 70, 35);
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD End
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0015, DataCtrl.getInstance().getParamData().getStrMode())){
				//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD Start
//				cmbShubetuNo.setBounds(867, 25, 70, 35);
				cmbShubetuNo.setBounds(822, 25, 70, 35);
				//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD End
				cmbShubetuNo.setBackground(Color.lightGray);
				cmbShubetuNo.setEnabled(false);
			}else{
				cmbShubetuNo.addFocusListener(new FocusCheck("種別番号"));
			}
			this.add(cmbShubetuNo);

			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
			//--------------------------- シークレットラベル設定   ---------------------------------
			ItemLabel hlSecret = new ItemLabel();
			hlSecret.setBounds(897, 25, 75, 15);
			hlSecret.setText("シークレット");
			this.add(hlSecret);
			ilSecret = new ItemIndicationLabel();
			ilSecret.setBounds(972, 25, 44, 15);
			this.add(ilSecret);
			btnSecret = new ButtonBase("シークレット設定");
			btnSecret.setMargin(new Insets(0, 0, 0, 0));
			btnSecret.addActionListener(this.getActionEvent());
			btnSecret.setActionCommand("secret");
			btnSecret.setBounds(897, 40, 119, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0190, DataCtrl.getInstance().getParamData().getStrMode())){
				btnSecret.setEnabled(false);
			}
			this.add(btnSecret);
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

			//--------------------------- 項目ラベル設定（更新日）  --------------------------------
			ItemLabel hlKosinHi = new ItemLabel();
			hlKosinHi.setBounds(610, 45, 50, 15);
			hlKosinHi.setText("更新日");
			this.add(hlKosinHi);
			//更新日
			ilKosinHi = new ItemIndicationLabel();
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD Start
//			ilKosinHi.setBounds(660, 45, 145, 15);
			ilKosinHi.setBounds(660, 45, 100, 15);
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD End
			this.add(ilKosinHi);

			//--------------------------- 項目ラベル設定（登録者）  --------------------------------
			ItemLabel hlTorokuSha = new ItemLabel();
			hlTorokuSha.setBounds(610, 65, 50, 15);
			hlTorokuSha.setText("登録者");
			this.add(hlTorokuSha);
			//登録者
			ilTorokuSha = new ItemIndicationLabel();
			ilTorokuSha.setBounds(660, 65, 145, 15);
			this.add(ilTorokuSha);

			//--------------------------- 項目ラベル設定（試算日）  --------------------------------
			ItemLabel hlShisanHi = new ItemLabel();
			hlShisanHi.setBounds(807, 65, 60, 15);
			hlShisanHi.setText("試算日");
			this.add(hlShisanHi);
			//試算日
			iiShisanHi = new ItemIndicationLabel();
			iiShisanHi.setBounds(867, 65, 145, 15);
			this.add(iiShisanHi);

			//--------------------------- 項目ラベル設定（更新者）  --------------------------------
			ItemLabel hlKosinSha = new ItemLabel();
			hlKosinSha.setBounds(610, 85, 50, 15);
			hlKosinSha.setText("更新者");
			this.add(hlKosinSha);
			//更新者
			ilKosinSha = new ItemIndicationLabel();
			ilKosinSha.setBounds(660, 85, 145, 15);
			this.add(ilKosinSha);

			//-------------------------- 項目ラベル設定（試算確定）  -------------------------------
			ItemLabel hlKakuteiHi = new ItemLabel();
			hlKakuteiHi.setBounds(807, 85, 60, 15);
			hlKakuteiHi.setText("試算確定");
			this.add(hlKakuteiHi);
			//試算確定
			iiKakuteiHi = new ItemIndicationLabel();
			iiKakuteiHi.setBounds(867, 85, 145, 15);
			this.add(iiKakuteiHi);

			//---------------------------- 項目ラベル設定（品名）    --------------------------------
			ItemLabel hlHin = new ItemLabel();
			hlHin.setBounds(5, 48, 60, 30);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlHin.setText("品名");
			hlHin.setText("品名");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlHin);
			//品名
			txtHinnm = new TextboxBase();
			txtHinnm.setBounds(65, 48, 375, 31);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0005, DataCtrl.getInstance().getParamData().getStrMode())){
				txtHinnm.setBounds(65, 48, 375, 30);
				txtHinnm.setBackground(Color.lightGray);
				txtHinnm.setEditable(false);
			}else{
				txtHinnm.addFocusListener(new FocusCheck("品名"));
			}
			this.add(txtHinnm);

			//--------------------------- 項目ラベル設定（製法No） --------------------------------
			ItemLabel hlSeiho = new ItemLabel();
			hlSeiho.setBounds(5, 82, 60, 19);
			hlSeiho.setText("製法No");
			this.add(hlSeiho);

			//製法No（ユーザID）
			txtSeihoUser = new TextboxBase();
			txtSeihoUser.setBounds(65, 82, 50, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0006, DataCtrl.getInstance().getParamData().getStrMode())){
				txtSeihoUser.setBounds(65, 82, 50, 19);
				txtSeihoUser.setBackground(Color.lightGray);
				txtSeihoUser.setEditable(false);
			}
			this.add(txtSeihoUser);

			//ハイフン
			HaihunLabel = new HaihunLabel();
			HaihunLabel.setBounds(115, 82, 15, 19);
			this.add(HaihunLabel);

			//製法No（製法種別）
			txtSeihoShu = new TextboxBase();
			txtSeihoShu.setBounds(130, 82, 30, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0007, DataCtrl.getInstance().getParamData().getStrMode())){
				txtSeihoShu.setBounds(130, 82, 30, 19);
				txtSeihoShu.setBackground(Color.lightGray);
				txtSeihoShu.setEditable(false);
			}
			this.add(txtSeihoShu);

			//ハイフン
			HaihunLabel = new HaihunLabel();
			HaihunLabel.setBounds(160, 82, 15, 19);
			this.add(HaihunLabel);

			//製法No（年）
			txtSeihoNen = new TextboxBase();
			txtSeihoNen.setBounds(175, 82, 30, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0008, DataCtrl.getInstance().getParamData().getStrMode())){
				txtSeihoNen.setBounds(175, 82, 30, 19);
				txtSeihoNen.setBackground(Color.lightGray);
				txtSeihoNen.setEnabled(false);
			}
			this.add(txtSeihoNen);

			//ハイフン
			HaihunLabel = new HaihunLabel();
			HaihunLabel.setBounds(205, 82, 15, 19);
			this.add(HaihunLabel);

			//製法No（追番）
			txtSeihoOi = new TextboxBase();
			txtSeihoOi.setBackground(Color.lightGray);
			txtSeihoOi.setBounds(220, 82, 40, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0009, DataCtrl.getInstance().getParamData().getStrMode())){
				txtSeihoOi.setBounds(220, 82, 40, 19);
				txtSeihoOi.setBackground(Color.lightGray);
				txtSeihoOi.setEnabled(false);
			}
			this.add(txtSeihoOi);

			//---------------------------- 項目ラベル設定（廃止） ---------------------------------
			ItemLabel hlhaisi = new ItemLabel();
			hlhaisi.setBounds(270, 82, 60, 19);
			hlhaisi.setText("廃止");
			this.add(hlhaisi);
			//廃止
			chkHaisi = new CheckboxBase();
			chkHaisi.setBackground(Color.WHITE);
			chkHaisi.setBounds(330, 82, 30, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0010, DataCtrl.getInstance().getParamData().getStrMode())){
				chkHaisi.setEnabled(false);
			}else{
				chkHaisi.addFocusListener(new FocusCheck("廃止"));
			}
			this.add(chkHaisi);

			//---------------------------- 原料費計算備考ラベル -------------------------------
			ItemLabel hlGenBikouText = new ItemLabel();
			hlGenBikouText.setBackground(Color.white);
			hlGenBikouText.setBounds(545, 109, 190, 15);
			hlGenBikouText.setText("（" + JwsConstManager.JWS_MARK_0005 + "）は原料費の計算に必要です");
			this.add(hlGenBikouText);


			//------------------------------- F2備考ラベル表示 --------------------------------
			ItemLabel hlBikou = new ItemLabel();
			hlBikou.setBackground(JwsConstManager.SHISAKU_F2_COLOR);
			hlBikou.setBorder(new LineBorder(Color.BLACK, 1));
			hlBikou.setBounds(750, 109, 15, 15);
			this.add(hlBikou);

			ItemLabel hlBikouText = new ItemLabel();
			hlBikouText.setBackground(Color.white);
			hlBikouText.setBounds(770, 109, 250, 15);
			hlBikouText.setText("の項目はF2ボタン押下後に編集して下さい");
			this.add(hlBikouText);

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			ItemLabel hlKoteiPtn = new ItemLabel();
			hlKoteiPtn.setBounds(270, 106, 110, 19);
			hlKoteiPtn.setText("工程パターン");
			this.add(hlKoteiPtn);
			//工程パターン
			cmbKoteiPtn = new ComboBase();
			cmbKoteiPtn.setBounds(380, 106, 150, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0153, DataCtrl.getInstance().getParamData().getStrMode())){
				cmbKoteiPtn.setBackground(Color.lightGray);
				cmbKoteiPtn.setEnabled(false);
			}
			else{
				//フォーカスリスナー設定
//				cmbKoteiPtn.addActionListener(getActionEvent());
//				cmbKoteiPtn.setActionCommand("工程パターン");
				//cmbKoteiPtn.addFocusListener(new FocusCheck("工程パターン"));
			}
			this.add(cmbKoteiPtn);
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

			//--------------------------------- タブ表示 ---------------------------------------
			tb = new TrialTab();
//MOD 2013/6/25 ogawa【QP@30151】No.9 start
//修正前のソース
//			tb.setBounds(5, 105, 1010, 610);
//修正後のソース
			tb.setBounds(5, 105, 1010, 629);
//MOD 2013/6/25 ogawa【QP@30151】No.9 end
			this.add(tb);

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			e.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作データ画面パネル コンストラクタ処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   ActionListenerイベント
	 *    : 配合表(試作表①)画面でのボタン押下時の処理をキャッチする
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

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
						tb.getTrial1Panel().getTrial1().AutoCopyKeisan();
//add end   -------------------------------------------------------------------------------

					    //-------------------- 登録ボタン クリック時の処理  ------------------------------
						if ( event_name == "toroku") {

							if(cancelMsg()){
								//登録処理
								toroku();
							}
						}
						//-------------------- 終了ボタン クリック時の処理  ------------------------------
						else if ( event_name == "shuryo") {

							//終了処理
							shuryo();


						}
						//-------------- 試作コピー（特徴コピー）ボタン クリック時の処理  ----------------------
						else if ( event_name == "copy_tokutyo") {

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
							//現在のモード取得
							String modo_now = DataCtrl.getInstance().getParamData().getStrMode();
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end

							int sel = tb.getSelectedIndex();

							//試作コピー通信
							conJW020();

							//特徴コピー
							DataCtrl.getInstance().getTrialTblData().CopyShisakuhin();
							DataCtrl.getInstance().getParamData().setStrSisaku("0-0-0");

							//再表示
							setPanelData();
							tb.setSelectedIndex(sel);

							//モードを新規に変更
							DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0002);

							//コピーボタン使用不可
							btnTcopy.setEnabled(false);
							btnZcopy.setEnabled(false);

							//廃止チェックボックス
							chkHaisi.setSelected(false);

							//原価試算画面をクリアする
							tb.getTrial5Panel().clearGenkaShisan();
							//原価試算画面の原価依頼チェックボックスを編集可設定
							setGenkaIrai_true();

							//試算日・試算確定をクリアする
							iiShisanHi.setText("");
							iiKakuteiHi.setText("");

//2010/05/12　シサクイック（原価）要望【案件No9】排他情報の表示　TT.NISHIGAWA　START
							//排他情報クリア
							DataCtrl.getInstance().getUserMstData().setStrHaitaKaishanm("");
							DataCtrl.getInstance().getUserMstData().setStrHaitaBushonm("");
							DataCtrl.getInstance().getUserMstData().setStrHaitaShimei("");
							HeaderLabel.setText(HeaderLabel.getHeaderUserData());
//2010/05/12　シサクイック（原価）要望【案件No9】排他情報の表示　TT.NISHIGAWA　END


//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
							//工程パターンの編集不可解除
							cmbKoteiPtn.setBackground(Color.white);
							cmbKoteiPtn.setEnabled(true);
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------


//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
							//ボタン押下前のモードが試作コピーモードだった場合
							if(modo_now.equals(JwsConstManager.JWS_MODE_0004)){

								//依頼番号を編集可
								txtIrai.setEditable(true);
								txtIrai.setBackground(Color.white);
								txtIrai.addFocusListener(new FocusCheck("依頼番号"));

								//品名を編集可
								txtHinnm.setEditable(true);
								txtHinnm.setBackground(Color.white);
								txtHinnm.addFocusListener(new FocusCheck("品名"));

								//廃止を編集可
								chkHaisi.setEnabled(true);
								chkHaisi.addFocusListener(new FocusCheck("廃止"));

								//登録ボタンを編集可
								btnToroku.setEnabled(true);

								//種別番号を編集可
								cmbShubetuNo.setEnabled(true);
								cmbShubetuNo.setBackground(Color.white);
								cmbShubetuNo.addFocusListener(new FocusCheck("種別番号"));

								//工程パターンにリスナー追加
//								cmbKoteiPtn.addActionListener(getActionEvent());
//								cmbKoteiPtn.setActionCommand("工程パターン");
								//cmbKoteiPtn.addFocusListener(new FocusCheck("工程パターン"));

								//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
								//シークレットボタンを編集可
								btnSecret.setEnabled(true);
								//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

								//再表示
								setPanelData();
								tb.setSelectedIndex(sel);

							}
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end


						}
						//--------------- 試作コピー（全コピー）ボタン クリック時の処理  -----------------------
						else if ( event_name == "copy_all") {

//2011/05/19　【QP@10181_No.12】全コピー列指定　TT.NISHIGAWA　start
							//選択サンプル列保持
							String CopyRetu = "";

							//試作列ヘッダテーブル取得
							TableBase ListHeaderChk = tb.getTrial1Panel().getTrial1().getListHeader();

							//試作列ループ
							int msg_count = 1;
							for(int i=0; i<ListHeaderChk.getColumnCount(); i++){

								//コンポーネント取得
								MiddleCellEditor mcShisaku = (MiddleCellEditor)ListHeaderChk.getCellEditor(0, i);
								DefaultCellEditor tcShisaku = (DefaultCellEditor)mcShisaku.getTableCellEditor(0);
								CheckboxBase chkShisaku = (CheckboxBase)tcShisaku.getComponent();
								int chk_seq = Integer.parseInt(chkShisaku.getPk1());
								TrialData trialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(chk_seq).get(0);

								//印刷Fgにチェックが入っている場合
								if(trialData.getIntInsatuFlg() == 1){
									//サンプルNo設定
									String sampleNo = (String)ListHeaderChk.getValueAt(3, i);
									if(sampleNo == null || sampleNo.length() == 0){
										sampleNo = "サンプル名称無し";
									}

									//サンプルが15列以上の場合はループアウト
									msg_count++;
									if(msg_count > 5){
										//i=ListHeaderChk.getColumnCount();
										msg_count = 1;
										CopyRetu = CopyRetu + " , " + "【" + sampleNo + "】" + "\n";
									}
									else{
										if(i == (ListHeaderChk.getColumnCount()-1)){
											if(msg_count > 2){
												CopyRetu = CopyRetu + " , " + "【" + sampleNo + "】";
											}
											else{
												CopyRetu = CopyRetu + "【" + sampleNo + "】";
											}
										}
										else{
											if(msg_count > 2){
												CopyRetu = CopyRetu + " , " + "【" + sampleNo + "】";
											}
											else{
												CopyRetu = CopyRetu + "【" + sampleNo + "】";
											}

										}
									}
								}
							}

							//確認ダイアログ表示（サンプル選択がない場合）
							if(CopyRetu.length() == 0){
								//メッセージ表示
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0025);
								//処理中断
								return;
							}

							//確認ダイアログ表示（全コピー実行前）
							JOptionPane jp = new JOptionPane();
							int option = jp.showConfirmDialog(
									jp.getRootPane(),
									JwsConstManager.JWS_ERROR_0026 + "\n" + CopyRetu
									, "確認メッセージ"
									,JOptionPane.YES_NO_OPTION
									,JOptionPane.PLAIN_MESSAGE
								);

							//「はい」押下
						    if (option == JOptionPane.YES_OPTION){
						    	//処理続行

						    }
						    //「いいえ」押下
						    else if (option == JOptionPane.NO_OPTION){
						    	//処理中断
						    	return;
						    }
//2011/05/19　【QP@10181_No.12】全コピー列指定　TT.NISHIGAWA　end


//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
							//現在のモード取得
							String modo_now = DataCtrl.getInstance().getParamData().getStrMode();
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end


							//試作コピー通信
							conJW020();

							//全コピー
							DataCtrl.getInstance().getTrialTblData().CopyAllShisakuhin();
							DataCtrl.getInstance().getParamData().setStrSisaku("0-0-0");

							//表示変更
							setCopyData();

							//モードを新規に変更
							DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0002);

							//コピーボタン使用不可
							btnTcopy.setEnabled(false);
							btnZcopy.setEnabled(false);

							//廃止チェックボックス
							chkHaisi.setSelected(false);

							//原価試算画面をクリアする
							tb.getTrial5Panel().clearGenkaShisan();
							//原価試算画面の原価依頼チェックボックスを編集可設定
							setGenkaIrai_true();

//2010/05/12　シサクイック（原価）要望【案件No9】排他情報の表示　TT.NISHIGAWA　START
							//排他情報クリア
							DataCtrl.getInstance().getUserMstData().setStrHaitaKaishanm("");
							DataCtrl.getInstance().getUserMstData().setStrHaitaBushonm("");
							DataCtrl.getInstance().getUserMstData().setStrHaitaShimei("");
							HeaderLabel.setText(HeaderLabel.getHeaderUserData());
//2010/05/12　シサクイック（原価）要望【案件No9】排他情報の表示　TT.NISHIGAWA　END


							//配合データ、試作データの配列整理
							DataCtrl.getInstance().getTrialTblData().sortAryHaigo();
							DataCtrl.getInstance().getTrialTblData().sortAryShisaku();
							DataCtrl.getInstance().getTrialTblData().sortAryShisakuList();


//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
							//ボタン押下前のモードが試作コピーモードだった場合
							if(modo_now.equals(JwsConstManager.JWS_MODE_0004)){

								//依頼番号を編集可
								txtIrai.setEditable(true);
								txtIrai.setBackground(Color.white);
								txtIrai.addFocusListener(new FocusCheck("依頼番号"));

								//品名を編集可
								txtHinnm.setEditable(true);
								txtHinnm.setBackground(Color.white);
								txtHinnm.addFocusListener(new FocusCheck("品名"));

								//廃止を編集可
								chkHaisi.setEnabled(true);
								chkHaisi.addFocusListener(new FocusCheck("廃止"));

								//登録ボタンを編集可
								btnToroku.setEnabled(true);

								//種別番号を編集可
								cmbShubetuNo.setEnabled(true);
								cmbShubetuNo.setBackground(Color.white);
								cmbShubetuNo.addFocusListener(new FocusCheck("種別番号"));

								//工程パターンにリスナー追加
//								cmbKoteiPtn.addActionListener(getActionEvent());
//								cmbKoteiPtn.setActionCommand("工程パターン");
								//cmbKoteiPtn.addFocusListener(new FocusCheck("工程パターン"));

								//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
								//シークレットボタンを編集可
								btnSecret.setEnabled(true);
								//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

								//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
								//配合表の試作明細テーブルの縦スクロールバーの現在位置を取得
								int vHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().getValue();

								//配合表の試作明細テーブルの横スクロールバーの現在位置を取得
								int hHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().getValue();

								//特性値の横スクロールバーの現在位置を取得
								int hTokuseiBarVal = tb.getTrial2Panel().getScroll().getHorizontalScrollBar().getValue();

								//原価試算の横スクロールバーの現在位置を取得
								int hGenkaBarVal = tb.getTrial5Panel().getScroll().getHorizontalScrollBar().getValue();
								//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

								//タブパネルの再表示
						    	int sel = tb.getSelectedIndex();
						    	setPanelData();
						    	tb.setSelectedIndex(sel);

								//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
								//配合表の試作明細テーブルの縦スクロールバーの位置を設定
								tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().setValue(vHaigoBarVal);

								//配合表の試作明細テーブルの横スクロールバーの位置を設定
								tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().setValue(hHaigoBarVal);

								//特性値の横スクロールバーの位置を設定
								tb.getTrial2Panel().getScroll().getHorizontalScrollBar().setValue(hTokuseiBarVal);

								//原価試算の横スクロールバーの位置を設定
								tb.getTrial5Panel().getScroll().getHorizontalScrollBar().setValue(hGenkaBarVal);
								//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------
							}
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end


//2011/05/19　【QP@10181_No.12】全コピー列指定　TT.NISHIGAWA　start
							//試作列ヘッダテーブル取得
							TableBase ListHeader = tb.getTrial1Panel().getTrial1().getListHeader();

							//試作列ループ
							for(int i=0; i<ListHeader.getColumnCount(); i++){

								//コンポーネント取得
								MiddleCellEditor mcShisaku = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
								DefaultCellEditor tcShisaku = (DefaultCellEditor)mcShisaku.getTableCellEditor(0);
								CheckboxBase chkShisaku = (CheckboxBase)tcShisaku.getComponent();
								int chk_seq = Integer.parseInt(chkShisaku.getPk1());
								TrialData trialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(chk_seq).get(0);

								//印刷Fgにチェックが入っている場合
								if(trialData.getIntInsatuFlg() == 1){

								}
								//印刷Fgにチェックが入っていない場合
								else{
									//列削除
									tb.getTrial1Panel().HaigoDelShisakuCol(i);
									i --;
								}
							}

							//印刷FGクリア処理
							for(int i=0; i<ListHeader.getColumnCount(); i++){

								//キー項目取得
								MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
								DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
								CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
								int intSeq  = Integer.parseInt(chkSeq.getPk1());

								//データ設定
					    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPrintFg(
					    				intSeq,
					    				0,
					    				DataCtrl.getInstance().getUserMstData().getDciUserid());

					    		//表示値設定
					    		ListHeader.setValueAt("false", 5, i);

							}

							//ALLチェックフラグクリア
							tb.getTrial1Panel().getTrial1().getAllCheck().setSelected(false);

							//工程パターンの編集不可解除
							cmbKoteiPtn.setBackground(Color.white);
							cmbKoteiPtn.setEnabled(true);

//2011/05/19　【QP@10181_No.12】全コピー列指定　TT.NISHIGAWA　end

							//再表示
					    	int sel = tb.getSelectedIndex();
					    	setPanelData();
					    	tb.setSelectedIndex(sel);

						}

						//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
						//------------------------------- シークレット切替 --------------------------------
						else if(event_name.equals("secret")){
							// 【KPX1500671】 ADD start
							//データ変更フラグＯＮ
					    	DataCtrl.getInstance().getTrialTblData().setHenkouFg(true);
							// 【KPX1500671】 ADD end

							if(PrototypeData.getStrSecret() != null){
								PrototypeData.setStrSecret(null);
								ilSecret.setText("OFF");
								ilSecret.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR4);
							}else{
								PrototypeData.setStrSecret("1");
								ilSecret.setText("ON");
								ilSecret.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR3);
							}
						}
						//【QP@20505_No.38】2012/10/17 TT H.SHIMA MOD End

						//------------------------------- 自動計算 --------------------------------------
						else if(event_name.equals("autoClick")){

							//計算処理
							AutoKeisan();

						}

// ADD start 20121017 QP@20505 No.11
						//------------------------------- 分析マスタの最新状態に更新ボタン処理 --------------------------------------
						else if(event_name.equals("click_getBunsekiMstData")){

							//確認ダイアログ表示（全コピー実行前）
							JOptionPane jp = new JOptionPane();
							int option = jp.showConfirmDialog(
									jp.getRootPane(),
									JwsConstManager.JWS_ERROR_0029
									, "確認メッセージ"
									,JOptionPane.YES_NO_OPTION
									,JOptionPane.PLAIN_MESSAGE
								);

							//「はい」押下
						    if (option == JOptionPane.YES_OPTION){
						    	//処理続行

						    }
						    //「いいえ」押下
						    else if (option == JOptionPane.NO_OPTION){
						    	//処理中断
						    	return;
						    }

							//原料分析マスタ値取得
							ArrayList aryBunsekiData = conJW220();

							//表示情報（配合明細）
							TableBase dispHaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
							//最大工程数
							int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

							//現在の配合データ取得
							ArrayList koshinHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

							for(int k=0; k<dispHaigoMeisai.getRowCount()-maxKotei-9; k++){
								//コンポーネント取得
								MiddleCellEditor selectMc = (MiddleCellEditor)dispHaigoMeisai.getCellEditor(k, 2);
								DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(k);

								//原料行の場合に処理
								if(selectDc.getComponent() instanceof CheckboxBase){

									for(int n=0; n<aryBunsekiData.size(); n++){
										//マスタから取得した原料分析データ
										MixedData md = (MixedData)aryBunsekiData.get(n);
										if ( dispHaigoMeisai.getValueAt(k, 3) == null || dispHaigoMeisai.getValueAt(k, 3).equals("")) {
											// 原料CDが入力されていない（空白）の場合

										} else {
											if (dispHaigoMeisai.getValueAt(k, 3).equals(md.getStrGenryoCd())){
												//油含有率　画面に表示
												dispHaigoMeisai.setValueAt(md.getDciGanyuritu(), k, 7);
											}
										}
									}
								}
							}

							int koteiCd = 0;
							int koteiSeq = 0;
							String genryoCd = "";
							for(int m=0; m<koshinHaigo.size(); m++){
								MixedData MixedData = (MixedData)koshinHaigo.get(m);

								genryoCd = MixedData.getStrGenryoCd();

								for(int n=0; n<aryBunsekiData.size(); n++){

									//マスタから取得した原料分析データ
									MixedData md = (MixedData)aryBunsekiData.get(n);

									if(genryoCd == null || genryoCd.length() < 0){
									}
									else if (genryoCd.equals(md.getStrGenryoCd())){
										koteiCd = MixedData.getIntKoteiCd();
										koteiSeq = MixedData.getIntKoteiSeq();

										//油含有率
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoYuganyuryo(
												koteiCd,
												koteiSeq,
												md.getDciGanyuritu(),
												DataCtrl.getInstance().getUserMstData().getDciUserid());
										//酢酸
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSakusan(
												koteiCd,
												koteiSeq,
												md.getDciSakusan(),
												DataCtrl.getInstance().getUserMstData().getDciUserid());
										//食塩
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSyokuen(
												koteiCd,
												koteiSeq,
												md.getDciShokuen(),
												DataCtrl.getInstance().getUserMstData().getDciUserid());
										//総酸
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSousan(
												koteiCd,
												koteiSeq,
												md.getDciSosan(),
												DataCtrl.getInstance().getUserMstData().getDciUserid());
										//ＭＳＧ
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMsg(
												koteiCd,
												koteiSeq,
												md.getDciMsg(),
												DataCtrl.getInstance().getUserMstData().getDciUserid());
									}
								}
							}

							//自動計算のチェックボックスがオフなら一時的にオン（マスタ値で更新するため必ず計算処理をさせる）
							int flgChkOff = 0;
							CheckboxBase chkAuto = tb.getTrial2Panel().getChkAuto();
							if (! chkAuto.isSelected()){
								flgChkOff = 1;
								chkAuto.setSelected(true);
							}

							//計算処理
							AutoKeisan();

							if (flgChkOff == 1){
								chkAuto.setSelected(false);
							}
						}
// ADD end 20121017 QP@20505 No.11

						//------------------------------- サンプル説明書出力 --------------------------------------
						else if(event_name.equals("sampleSetumei")) {

							//印刷Flgチェックを行う
							if ( chkInsatuFlg("サンプル説明書", 4) ) {

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.2
								//サンプルNo同一チェック
								String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();

								//同一サンプルNoがない場合
								if(chk==null){

									if(cancelMsg()){
										//自動保存処理
										JidouHozon(2);

										//サンプル説明書を出力する
										conJW130();
									}

								}
								//同一サンプルNoがある場合
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
								}
//mod end -------------------------------------------------------------------------------
							}

						}
						//------------------------------- 試作表出力 --------------------------------------
						else if(event_name.equals("shisakuHyo")) {

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
							//印刷Flgチェックを行う
							//if ( chkInsatuFlg("試作表", 10) ) {
							if ( chkInsatuFlg("試作表", 20) ) {

	//mod start -------------------------------------------------------------------------------
	//QP@00412_シサクイック改良 No.2
								//サンプルNo同一チェック
								String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
								//同一サンプルNoがない場合
								if(chk==null){

									if(cancelMsg()){
										//自動保存処理
										JidouHozon(1);

										//試作表出力処理
										conJW120();
									}

								}
								//同一サンプルNoがある場合
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
								}
	//mod end -------------------------------------------------------------------------------
							}
//mod end -------------------------------------------------------------------------------

						}
						//------------------------------- 栄養計算書出力 --------------------------------------
						else if(event_name.equals("eiyoKeisan")) {

							//試作列が選択されているか
							if ( DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getShisakuSeq() != 0 ) {

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.2
								//サンプルNo同一チェック
								String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
								//同一サンプルNoがない場合
								if(chk==null){

									int seq = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getShisakuSeq();

									if(cancelMsg()){
										//自動保存処理
										JidouHozon(3);

										//栄養計算書出力処理
										conJW740(seq);
									}

								}
								//同一サンプルNoがある場合
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
								}
							} else {
								//メッセージ表示
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString("試作列を選択して下さい。");

							}
//mod end -------------------------------------------------------------------------------

						}

						//------------------------------- 原価試算表 --------------------------------------
						else if(event_name.equals("shisanHyo")) {

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.2
							//サンプルNo同一チェック
							String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
							//同一サンプルNoがない場合
							if(chk==null){

								//工程チェック
								int intKoteiChk = DataCtrl.getInstance().getTrialTblData().CheckKotei();

								//配合表(試作表①)画面の工程にて、選択されている工程のチェックを行う
								if ( intKoteiChk == 1 || intKoteiChk == 2 ) {
									//調味液 or その他調味液以外の場合

									//印刷Flgチェックを行う
									if ( tb.getTrial5Panel().chkGenkaInsatuFlg("原価試算表", 3) ) {

										if(cancelMsg()){
											//自動保存処理
											JidouHozon(4);

											//原価試算表出力処理
											tb.getTrial5Panel().outputGenkaShisanHyo(intKoteiChk);

											//原価試算マーク設定処理
											setGenkaShisanMark();
										}

									}

								} else if ( intKoteiChk == 0 ) {
									//未選択時

									//警告メッセージ表示
									String strMessage = JwsConstManager.JWS_ERROR_0022;
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);

								} else {
									//調味液 or その他調味液以外ではない場合

									//警告メッセージ表示
									String strMessage = JwsConstManager.JWS_ERROR_0023;
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);

								}

							}
							//同一サンプルNoがある場合
							else{
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
							}
//mod end -------------------------------------------------------------------------------

						}
						//------------------------------- 原価試算登録 --------------------------------------
						else if(event_name.equals("shisanToroku")) {

							//原価試算登録処理
							tb.getTrial5Panel().shisanToroku();


							//試算日・試算確定 設定

							//最終試算確定を試算データより取得
							ShisanData shisanData = DataCtrl.getInstance().getShisanRirekiKanriData().SearchLastShisanData();

							//最終試算確定した試算日とサンプルNoを、画面項目の試算日と試算確定に設定
							iiShisanHi.setText(shisanData.getStrShisanHi());
							if ( shisanData.getStrSampleNo() != null ) {

								iiKakuteiHi.setText(shisanData.getStrSampleNo());

							} else {

								iiKakuteiHi.setText("");

							}

						}


						//-------------------------- 工程パターン  -----------------------
						else if(event_name.equals("工程パターン")){

							//現在の設定値取得
							String kotei_ptn = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
							String kotei_ptn_value = "";
							if(kotei_ptn == null){
								kotei_ptn = "";
							}
							else{
								kotei_ptn_value = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(kotei_ptn);
							}

							System.out.println("現在値：" + kotei_ptn_value);

							//選択値取得
							String insert = null;
							int selectId = ((ComboBase)e.getSource()).getSelectedIndex();
							if(selectId > 0){
								insert = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralCd(selectId-1);
							}
							if(insert == null){
								insert = "";
							}

							System.out.println("選択値：" + insert);

							//確認メッセージ表示フラグ
							boolean confirmMsg = false;

							//設定値と選択値が等しい場合
							if(insert.equals(kotei_ptn)){
								//何もしない

							}
							//設定値と選択値が等しくない場合
							else{
// DEL start 20121026 QP@20505 No.24
//								//水相比重の入力確認
//								ArrayList aryShisakuRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
//								Iterator ite = aryShisakuRetu.iterator();
//
//								//リスト件数分ループ
//								while(ite.hasNext()){
//									//試作列データオブジェクト取得
//									TrialData trialData = (TrialData)ite.next();
//
//									//水相比重に入力がない場合
//									if(trialData.getStrHiju_sui() == null || trialData.getStrHiju_sui().length()==0){
//
//									}
//									//水相比重に入力がある場合
//									else{
//										//確認メッセージ表示フラグをオン
//										confirmMsg = true;
//									}
//								}
// DEL end 20121026 QP@20505 No.24
// ADD start 20121003 QP@20505 No.24
								//水相比重の入力確認
								boolean flgSuisoHiju = false;
								ArrayList aryShisakuRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
								Iterator ite = aryShisakuRetu.iterator();

								while(ite.hasNext()){
									//試作列データオブジェクト取得
									TrialData trialData = (TrialData)ite.next();

									if(trialData.getStrHiju_sui() == null || trialData.getStrHiju_sui().length()==0){
										//水相比重に入力がない場合
									}else{
										flgSuisoHiju = true;	// 水相比重メッセージ表示フラグ
									}
								}
								ite = null;
								//製品比重の入力確認
								boolean flgSeihinHiju = false;
								ite = aryShisakuRetu.iterator();

								while(ite.hasNext()){
									//試作列データオブジェクト取得
									TrialData trialData = (TrialData)ite.next();

									if(trialData.getStrHizyu() == null || trialData.getStrHizyu().length()==0){
										//製品比重に入力がない場合
									}else{
										flgSeihinHiju = true;	//製品比重メッセージ表示フラグ
									}
								}

								int flgValueClear = 0;
								//容量単位取得
								String yoryoTani = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrTani();
								//容量単位からValue1取得
								String taniValue1 = "";
								if(yoryoTani == null || yoryoTani.length() == 0){
								}
								else{
									taniValue1 =  DataCtrl.getInstance().getLiteralDataTani().selectLiteralVal1(yoryoTani);
								}
								//特性値画面項目が切り替わるかどうか判定
								if (insert.equals("001") || insert.equals("002")){
									if (kotei_ptn.equals("003") || kotei_ptn.equals("")){
										flgValueClear = 1; // 1液 or 2液 になる場合
									}
								}else{
									if (kotei_ptn.equals("001") || kotei_ptn.equals("002")){
										flgValueClear = 2; // その他・加食 or 空白 になる場合
									}
								}
								//工程パターン、容量単位 の選択値によって表示メッセージが異なる
								confirmMsg = true;
								String strMessage = "";
								if (kotei_ptn.equals("001")){
									if (taniValue1.equals("1")){
										if (insert.equals("003") || insert.equals("")){
											// 1液(ml) → その他・加食 or 空白
											strMessage = JwsConstManager.JWS_CONFIRM_0013;
										}else{
											// 1液(ml) → 2液
											strMessage = JwsConstManager.JWS_CONFIRM_0012;
											if ( ! flgSeihinHiju){
												confirmMsg = false;                       // 製品比重が空白ならメッセージ表示しない
											}
										}
									}else if (taniValue1.equals("2")){
										if (insert.equals("003") || insert.equals("")){
											// 1液(g) → その他・加食 or 空白
											strMessage = JwsConstManager.JWS_CONFIRM_0014;
										}
									}else{
										if (insert.equals("003") || insert.equals("")){
											// 1液(単位空白) → その他・加食 or 空白
											strMessage = JwsConstManager.JWS_CONFIRM_0014;
										}
									}
								}else if (kotei_ptn.equals("002")){
									if (taniValue1.equals("1")){
										if (insert.equals("003") || insert.equals("")){
											// 2液(ml) → その他・加食 or 空白
											strMessage = JwsConstManager.JWS_CONFIRM_0015;
										}else{
											// 2液(ml) → 1液
											strMessage = JwsConstManager.JWS_CONFIRM_0011;
											if ( ! flgSuisoHiju){
												confirmMsg = false;                        // 水相比重が空白ならメッセージ表示しない
											}
										}
									}else if (taniValue1.equals("2")){
										if (insert.equals("003") || insert.equals("")){
											// 2液(g) → その他・加食 or 空白
											strMessage = JwsConstManager.JWS_CONFIRM_0014;
										}
									}else{
										// 2液(単位空白) → その他・加食 or 空白
										strMessage = JwsConstManager.JWS_CONFIRM_0014;
									}
								}else{
									if (insert.equals("001") || insert.equals("002")){
										// その他・加食 or 空白 (ml・g・単位空白) → 1液 or 2液
										strMessage = JwsConstManager.JWS_CONFIRM_0016;
									}
								}

								if (strMessage.equals("")){
									// Yes_No確認メッセージ表示なし

								}else{
									//Yes_No確認メッセージ表示
									if (confirmMsg){
										JOptionPane jp = new JOptionPane();
										int option = JOptionPane.showConfirmDialog(
												jp.getRootPane(),
												strMessage
												, "確認メッセージ"
												,JOptionPane.YES_NO_OPTION
												,JOptionPane.PLAIN_MESSAGE
											);
									    if (option == JOptionPane.YES_OPTION){
									    	 //「はい」押下、処理続行
									    }else{ // 「いいえ」押下
									    	//コンボボックスを選択前の状態に戻す
									    	String selectNm = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralNm(kotei_ptn);
									    	((ComboBase)e.getSource()).setSelectedItem(selectNm);
									    	//処理中断
									    	return;
									    }
									}
								}
// ADD end 20121003 QP@20505 No.24
// DEL start 20121026 QP@20505 No.24
//								//確認メッセージ表示
//								if(confirmMsg){
//
//									//確認ダイアログ表示
//									JOptionPane jp = new JOptionPane();
//									int option = JOptionPane.showConfirmDialog(
//											jp.getRootPane(),
//											JwsConstManager.JWS_CONFIRM_0011
//											, "確認メッセージ"
//											,JOptionPane.YES_NO_OPTION
//											,JOptionPane.PLAIN_MESSAGE
//										);
//
//									//「はい」押下
//								    if (option == JOptionPane.YES_OPTION){
//
//								    	//処理続行
//
//								    }
//								    //「いいえ」押下
//								    else {
//
//								    	//コンボボックスを選択前の状態に戻す
//								    	String selectNm = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralNm(kotei_ptn);
//								    	((ComboBase)e.getSource()).setSelectedItem(selectNm);
//
//								    	//処理中断
//								    	return;
//
//								    }
//								}
// DEL end 20121026 QP@20505 No.24
								//工程パターン変更時に確認メッセージ
								if(kotei_ptn_value.equals("")){
									//前選択が空白の場合はメッセージなし
								}
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_CONFIRM_0010);
								}

								//工程パターン変更
								DataCtrl.getInstance().getTrialTblData().UpdKoteiPtn(
										DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
										DataCtrl.getInstance().getUserMstData().getDciUserid()
									);
								int selecttab = tb.getSelectedIndex();
								if (selecttab == 1){
//							    	tb.setSelectedIndex(2);
//							    	tb.setSelectedIndex(1);
									//自動計算
									Trial2Panel tp2 = new Trial2Panel();
// ADD start 20121017 QP@20505
									CheckboxBase chkAuto = tp2.getChkAuto();
									chkAuto.addActionListener(getActionEvent());
									chkAuto.setActionCommand("autoClick");
									//自動計算
									ButtonBase btnBunsekiMstData = tp2.getCmdBunsekiMstData();
									btnBunsekiMstData.addActionListener(getActionEvent());
									btnBunsekiMstData.setActionCommand("click_getBunsekiMstData");
// ADD end 20121017 QP@20505
									//再表示
									tb.setTrial2Panel(tp2);
									jtab.setComponentAt(1, tp2);
								}
								//工程パターン取得
								String ptKotei = PrototypeData.getStrPt_kotei();

						    	//配合明細テーブル取得
								TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();

								//配合明細テーブル　行ループ
								for(int i=0; i<HaigoMeisai.getRowCount(); i++){

									//コンポーネント取得
									MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 3);
									DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

									//工程行の場合
									if(selectDc.getComponent() instanceof ComboBase){

										//キー項目取得
										MiddleCellEditor selectMcKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
										DefaultCellEditor selectDcKey = (DefaultCellEditor)selectMcKey.getTableCellEditor(i);
										CheckboxBase cb = (CheckboxBase)selectDcKey.getComponent();
										int koteiCd = Integer.parseInt(cb.getPk1());

										//データ挿入
										DataCtrl.getInstance().getTrialTblData().UpdHaigoZokusei(
												koteiCd,
												DataCtrl.getInstance().getTrialTblData().checkNullString(""),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//工程属性コンボボックス取得
										ComboBase cbKotei = (ComboBase)selectDc.getComponent();
										MiddleCellRenderer selectMr =  (MiddleCellRenderer)HaigoMeisai.getCellRenderer(i, 3);
										ComboBoxCellRenderer selectCr =  (ComboBoxCellRenderer)selectMr.getTableCellRenderer(i);

										//工程パターンが「空白」の場合
										if(ptKotei == null || ptKotei.length() == 0){
											//セルエディタに空行のみ
											cbKotei.removeAllItems();
											cbKotei.addItem("");
											selectCr.removeAllItems();
											selectCr.addItem("");
										}
										//工程パターンが「空白」でない場合
										else{
											//工程パターンのValue1取得
											String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

											//工程パターンが「調味料１液タイプ」の場合
											if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
												//選択値を設定
												setLiteralCmb(cbKotei, DataCtrl.getInstance().getLiteralDataKotei_tyomi1(), "", 0);
												setLiteralCmb(selectCr, DataCtrl.getInstance().getLiteralDataKotei_tyomi1(), "", 0);
											}
											//工程パターンが「調味料２液タイプ」の場合
											else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
												//選択値を設定
												setLiteralCmb(cbKotei, DataCtrl.getInstance().getLiteralDataKotei_tyomi2(), "", 0);
												setLiteralCmb(selectCr, DataCtrl.getInstance().getLiteralDataKotei_tyomi2(), "", 0);
											}
											//工程パターンが「その他・加食タイプ」の場合
											else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
												//選択値を設定
												setLiteralCmb(cbKotei, DataCtrl.getInstance().getLiteralDataKotei_sonota(), "", 0);
												setLiteralCmb(selectCr, DataCtrl.getInstance().getLiteralDataKotei_sonota(), "", 0);
											}
										}
										HaigoMeisai.setValueAt("", i, 3);
									}
								}

								//基本情報の容量単位を設定
								ComboBase cbTani = tb.getTrial3Panel().getCmbtani();
								tb.getTrial3Panel().setLiteralCmbYoryo(cbTani, DataCtrl.getInstance().getLiteralDataTani(), "", 0);

								//工程パターンが「空白」の場合
								if(ptKotei == null || ptKotei.length() == 0){

									//データ設定
									DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
											"",
											DataCtrl.getInstance().getUserMstData().getDciUserid()
										);

									//合計仕上がり重量初期化
									clearSiagariZyuryo(kotei_ptn_value);
								}
								//工程パターンが「空白」でない場合
								else{
									//工程パターンのValue1取得
									String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

									//工程パターンが「調味料１液タイプ」の場合
									if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){

										//データ設定
										DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
												"",
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//合計仕上がり重量初期化
										clearSiagariZyuryo(kotei_ptn_value);
									}
									//工程パターンが「調味料２液タイプ」の場合
									else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){

										cbTani.setSelectedIndex(1);

										//データ設定
										String selectNm = (String) cbTani.getSelectedItem();
										insert = DataCtrl.getInstance().getLiteralDataTani().selectLiteralCd(selectNm);
										DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//合計仕上がり重量初期化
										clearSiagariZyuryo(kotei_ptn_value);
									}
									//工程パターンが「その他・加食タイプ」の場合
									else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){

										cbTani.setSelectedIndex(1);

										//データ設定
										String selectNm = (String) cbTani.getSelectedItem();
										insert = DataCtrl.getInstance().getLiteralDataTani().selectLiteralCd(selectNm);
										DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);
									}
								}

								//特性値、原価試算テーブル設定処理
								setTp2_5TableHiju(flgValueClear);
							}
						}

					} catch (ExceptionBase eb) {

						//メッセージ表示
						DataCtrl.getInstance().PrintMessage(eb);

					} catch (Exception ec) {

						ec.printStackTrace();

						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("試作データ画面 ボタン押下処理が失敗しました");
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

	/************************************************************************************
	 *
	 *   終了処理
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void shuryo() throws ExceptionBase{
		try{

			//処理モード取得
			String mode = DataCtrl.getInstance().getParamData().getStrMode();

			//終了実行フラグ
			boolean blnEnd = true;

			//データ変更チェック
			if(DataCtrl.getInstance().getTrialTblData().getHenkouFg()){

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
//				//参照モードの場合 or 製法コピーモードの場合
//				if(mode.equals(JwsConstManager.JWS_MODE_0000) || mode.equals(JwsConstManager.JWS_MODE_0003)){

				//参照モードの場合 or 製法コピーモードの場合 or 試作コピーモードの場合
				if(mode.equals(JwsConstManager.JWS_MODE_0000)
						|| mode.equals(JwsConstManager.JWS_MODE_0003)
						|| mode.equals(JwsConstManager.JWS_MODE_0004) ){

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end

					//終了実行OK
			    	blnEnd = true;

				}
				//詳細、新規モードの場合
				else{

					//ダイアログコンポーネント設定
					JOptionPane jp = new JOptionPane();

					//確認ダイアログ表示
					int option = jp.showConfirmDialog(
							jp.getRootPane(),
							"内容が変更されています。保存しますか？"
							, "確認メッセージ"
							,JOptionPane.YES_NO_OPTION
							,JOptionPane.PLAIN_MESSAGE
						);

					//「はい」押下
				    if (option == JOptionPane.YES_OPTION){

				    	//終了実行NG
				    	blnEnd = false;

				    	//登録処理
				    	toroku();

				    //「いいえ」押下
				    }else if (option == JOptionPane.NO_OPTION){

				    	//終了実行OK
				    	blnEnd = true;

				    }

				}



			}

			//終了実行OK
			if(blnEnd){

				//詳細 or 製法コピーの場合
				if(mode.equals(JwsConstManager.JWS_MODE_0001) || mode.equals(JwsConstManager.JWS_MODE_0003)){

					//終了処理
					conJW060();

				}

				//画面終了
				System.exit(0);

			}
			//終了事項NG
			else{

				//何もしない

			}

		}catch(ExceptionBase eb) {

			throw eb;

		}catch(Exception ec){

			ec.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("終了処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());

		}finally{


		}
	}


	/************************************************************************************
	 *
	 *   登録処理
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void toroku() throws ExceptionBase{
		try{
			String mode = DataCtrl.getInstance().getParamData().getStrMode();

			//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//配合表の試作明細テーブルの縦スクロールバーの現在位置を取得
			int vHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().getValue();

			//配合表の試作明細テーブルの横スクロールバーの現在位置を取得
			int hHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().getValue();

			//特性値の横スクロールバーの現在位置を取得
			int hTokuseiBarVal = tb.getTrial2Panel().getScroll().getHorizontalScrollBar().getValue();

			//原価試算の横スクロールバーの現在位置を取得
			int hGenkaBarVal = tb.getTrial5Panel().getScroll().getHorizontalScrollBar().getValue();
			//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//詳細
			if(mode.equals(JwsConstManager.JWS_MODE_0001)){

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.2
				//サンプルNo同一チェック
				String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
				//同一サンプルNoがない場合
				if(chk==null){
					//登録（編集）
					conJW040(0);
					//データ変更フラグ初期化
			    	DataCtrl.getInstance().getTrialTblData().setHenkouFg(false);
				}
				//同一サンプルNoがある場合
				else{
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
				}
//mod end --------------------------------------------------------------------------------

			//新規
			}else if(mode.equals(JwsConstManager.JWS_MODE_0002)){

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.2
				//サンプルNo同一チェック
				String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
				//同一サンプルNoがない場合
				if(chk==null){
					//登録（新規）
					conJW030(0);

					//データ設定
					txtShisakuUser.setText(checkNull(PrototypeData.getDciShisakuUser()));
					txtShisakuNen.setText(checkNull(PrototypeData.getDciShisakuYear()));
					txtShisakuOi.setText(checkNull(PrototypeData.getDciShisakuNum()));

					//コピーボタン使用可
					btnTcopy.setEnabled(true);
					btnZcopy.setEnabled(true);

	//2010/05/12　シサクイック（原価）要望【案件No9】排他情報の表示　TT.NISHIGAWA　START
					//排他情報設定
					DataCtrl.getInstance().getUserMstData().setStrHaitaKaishanm(
							DataCtrl.getInstance().getUserMstData().getStrKaishanm());
					DataCtrl.getInstance().getUserMstData().setStrHaitaBushonm(
							DataCtrl.getInstance().getUserMstData().getStrBushonm());
					DataCtrl.getInstance().getUserMstData().setStrHaitaShimei(
							DataCtrl.getInstance().getUserMstData().getStrUsernm());
					HeaderLabel.setText(HeaderLabel.getHeaderUserData());
	//2010/05/12　シサクイック（原価）要望【案件No9】排他情報の表示　TT.NISHIGAWA　END

					//データ変更フラグ初期化
			    	DataCtrl.getInstance().getTrialTblData().setHenkouFg(false);
				}
				//同一サンプルNoがある場合
				else{
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
				}
//mod end --------------------------------------------------------------------------------

			//製法コピー
			}else if(mode.equals(JwsConstManager.JWS_MODE_0003)){

				//試作SEQ取得
				String strSeq = null;
				int col;

				TableBase ListHeader = tb.getTrial1Panel().getTrial1().getListHeader();

				for(int i=0; i<ListHeader.getColumnCount(); i++){
					MiddleCellEditor mcShisaku = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
					DefaultCellEditor tcShisaku = (DefaultCellEditor)mcShisaku.getTableCellEditor(0);
					CheckboxBase chkShisaku = (CheckboxBase)tcShisaku.getComponent();
					if(chkShisaku.isSelected()){
						strSeq = chkShisaku.getPk1();
						col = i;
					}
				}
				//登録（製法コピー）
				if(strSeq != null){

					conJW050(strSeq);

				}else{

					DataCtrl.getInstance().getMessageCtrl().PrintMessageString("試作列を選択して下さい。");

				}
				//データ変更フラグ初期化
		    	DataCtrl.getInstance().getTrialTblData().setHenkouFg(false);
			}

			//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//配合表の試作明細テーブルの縦スクロールバーの位置を設定
			tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().setValue(vHaigoBarVal);

			//配合表の試作明細テーブルの横スクロールバーの位置を設定
			tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().setValue(hHaigoBarVal);

			//特性値の横スクロールバーの位置を設定
			tb.getTrial2Panel().getScroll().getHorizontalScrollBar().setValue(hTokuseiBarVal);

			//原価試算の横スクロールバーの位置を設定
			tb.getTrial5Panel().getScroll().getHorizontalScrollBar().setValue(hGenkaBarVal);
			//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

		}catch(ExceptionBase eb) {

			throw eb;

		}catch(Exception ec){

			ec.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("登録処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());

		}finally{


		}
	}



	/************************************************************************************
	 *
	 *   自動計算
	 *    :  自動計算を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void AutoKeisan() throws ExceptionBase{
		try{

			//コンポーネント取得
			CheckboxBase chkAuto = tb.getTrial2Panel().getChkAuto();
			TableBase table = tb.getTrial2Panel().getTable();
			TableBase ListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
			TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
// ADD start 20121029 QP@20505 No.11
			//工程パターン取得
			String ptKotei = PrototypeData.getStrPt_kotei();
			String ptValue = "";
			if(ptKotei == null || ptKotei.length() == 0){
			}else{
				ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
			}
// ADD end 20121029 QP@20505 No.11

			//自動計算がチェックされている場合のみ処理
			if(chkAuto.isSelected()){

				//配合データ取得
				ArrayList HaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

				//最大工程数取得
				int koteiNum = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

				//試作列数分ループ
				for(int i=0; i<ListMeisai.getColumnCount(); i++){
					//少数指定取得
					//リテラルデータ取得
					ArrayList aryLiteralCd = DataCtrl.getInstance().getLiteralDataShosu().getAryLiteralCd();
					ArrayList aryLiteralNm = DataCtrl.getInstance().getLiteralDataShosu().getAryLiteralNm();
					//選択データ取得
					String SelShosu = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShosu();
					int shosu = 0;
					//選択リテラルコードの名称取得
					for(int k=0; k<aryLiteralCd.size(); k++){
						//コードと名称取得
						String strLiteralCd = (String)aryLiteralCd.get(k);
						String strLiteralNm = (String)aryLiteralNm.get(k);
						if(SelShosu != null && SelShosu.length() > 0){
							if(SelShosu.equals(strLiteralCd)){
								try{
									//小数指定取得
									shosu = Integer.parseInt(strLiteralNm);
								}catch(Exception e){
									//例外時は0を挿入
									shosu = 0;
								}

							}

						}
					}


					//試作SEQ取得
					MiddleCellEditor mceSeq = (MiddleCellEditor)table.getCellEditor(0, i);
					DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
					TextboxBase chkSeq = (TextboxBase)dceSeq.getComponent();
					int intSeq  = Integer.parseInt(chkSeq.getPk1());

					//配合表(試作表①)行数
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
					//int maxRow = ListMeisai.getRowCount()-7;
					int maxRow = ListMeisai.getRowCount()-8;
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------


					//------------------------ 計算必要項目 -----------------------------------
					//①合計重量(g)
					BigDecimal goleiZyuryo = new BigDecimal("0.00");

// MOD start 20121009 QP@20505 No.24
//					Object objZyuryo = tb.getTrial1Panel().getTrial1().getListMeisai().getValueAt(maxRow-1, i);
					Object objZyuryo = tb.getTrial1Panel().getTrial1().getListMeisai().getValueAt(maxRow - koteiNum - 1, i);
// MOD end 20121009 QP@20505 No.24

					if(objZyuryo != null){
						if(objZyuryo.toString().length()>0){
							goleiZyuryo = new BigDecimal(objZyuryo.toString());
							//goleiZyuryo = goleiZyuryo.divide(new BigDecimal("1000"), BigDecimal.ROUND_HALF_UP);
						}
					}
					//②油含有合計量
					BigDecimal goleiGanyu = new BigDecimal("0.00");
					//③酢酸合計量
					BigDecimal goleiSakusan = new BigDecimal("0.00");
					//④食塩合計量
					BigDecimal goleiShokuen = new BigDecimal("0.00");
					//⑤総酸合計量
					BigDecimal goleiSosan = new BigDecimal("0.00");
// ADD start 20121002 QP@20505 No.24
					//ＭＳＧ合計量
					BigDecimal goleiMsg = new BigDecimal("0.00");
// ADD end 20121002 QP@20505 No.24


					//----------------------- 配合～食塩量取得 ---------------------------------
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
					//for(int j=0; j<HaigoMeisai.getRowCount()-koteiNum-8;j++){
					for(int j=0; j<HaigoMeisai.getRowCount()-koteiNum-9;j++){
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
						//キーコンポーネント取得
						MiddleCellEditor mceHaigo = (MiddleCellEditor)HaigoMeisai.getCellEditor(j, 2);
						DefaultCellEditor dceHaigo = (DefaultCellEditor)mceHaigo.getTableCellEditor(j);

						//原料行の場合
						if(dceHaigo.getComponent() instanceof CheckboxBase){

							//キー項目取得
							CheckboxBase cbKotei = (CheckboxBase)dceHaigo.getComponent();
							int koteiCd = Integer.parseInt(cbKotei.getPk1());
							int koteiSeq = Integer.parseInt(cbKotei.getPk2());

							//配合量取得
							String ryoVal = (String)ListMeisai.getValueAt(j, i);
							BigDecimal bdRyo = new BigDecimal("0.0000");
							if(ryoVal != null && ryoVal.length() > 0){
								bdRyo = new BigDecimal(ryoVal);
							}


							//配合～食塩量を計算＋加算
							for(int k=0; k<HaigoData.size(); k++){

								//配合データ取得
								MixedData selMixedData = (MixedData)HaigoData.get(k);
								if(selMixedData.getIntKoteiCd() == koteiCd && selMixedData.getIntKoteiSeq() == koteiSeq){

									//②油含有合計量加算
									if(selMixedData.getDciGanyuritu() != null){
										goleiGanyu = goleiGanyu.add(bdRyo.multiply(selMixedData.getDciGanyuritu().multiply(new BigDecimal("0.01"))));
									}
									//③酢酸合計量加算
									if(selMixedData.getDciSakusan() != null){
										goleiSakusan = goleiSakusan.add(bdRyo.multiply(selMixedData.getDciSakusan()));
									}
									//④食塩合計量加算
									if(selMixedData.getDciShokuen() != null){
										goleiShokuen = goleiShokuen.add(bdRyo.multiply(selMixedData.getDciShokuen()));
									}
									//⑤総酸合計量加算
									if(selMixedData.getDciSosan() != null){
										goleiSosan = goleiSosan.add(bdRyo.multiply(selMixedData.getDciSosan()));
									}
// ADD start 20121002 QP@20505 No.24
									//ＭＳＧ合計量加算
									if(selMixedData.getDciMsg() != null){
										goleiMsg = goleiMsg.add(bdRyo.multiply(selMixedData.getDciMsg()));
									}
// ADD end 20121002 QP@20505 No.24
								}
							}
						}
					}

					//---------------------------------- 原価行 -------------------------------
					maxRow+=2;

					//------------------------------- 総酸計算処理 -----------------------------
					//③総酸合計量/①合計重量
					BigDecimal sosan = new BigDecimal("0.00");
					if(goleiSosan.intValue() > 0 && goleiZyuryo.intValue() > 0){
						sosan = goleiSosan.divide(goleiZyuryo, 2, BigDecimal.ROUND_HALF_UP);
					}

					//データ挿入
					table.setValueAt(sosan, 1, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousan(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(sosan.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//配合表(試作表①)へ挿入
					ListMeisai.setValueAt(sosan, maxRow+=1, i);


					//------------------------------ 食塩計算処理 -------------------------------
					//④食塩合計量/①合計重量
					BigDecimal shokuen = new BigDecimal("0.00");
					if(goleiShokuen.intValue() > 0 && goleiZyuryo.intValue() > 0){
						shokuen = goleiShokuen.divide(goleiZyuryo, 2, BigDecimal.ROUND_HALF_UP);
					}

					//データ挿入
					table.setValueAt(shokuen, 2, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuen(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(shokuen.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//配合表(試作表①)へ挿入
					ListMeisai.setValueAt(shokuen, maxRow+=1, i);

// ADD start 20121002 QP@20505 No.24
					//------------------------------ ＭＳＧ計算処理 -------------------------------
					//④ＭＳＧ合計量/①合計重量
					BigDecimal msg = new BigDecimal("0.00");
					if(goleiMsg.intValue() > 0 && goleiZyuryo.intValue() > 0){
						msg = goleiMsg.divide(goleiZyuryo, 2, BigDecimal.ROUND_HALF_UP);
					}

					//データ挿入
//					table.setValueAt(msg, 2, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuMsg(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(msg.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
// ADD end 20121002 QP@20505 No.24

					//--------------------------- 水相中酸度計算処理-----------------------------
					//⑤総酸合計量/（①合計量ー②油含有合計量）
					BigDecimal sui_sando = new BigDecimal("0.00");
					if(goleiSosan.intValue() > 0 && (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0){
						sui_sando = goleiSosan.divide((goleiZyuryo.subtract(goleiGanyu)), 2, BigDecimal.ROUND_HALF_UP);
					}

					//データ挿入
					table.setValueAt(sui_sando, 3, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSando(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(sui_sando.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//配合表(試作表①)へ挿入
					ListMeisai.setValueAt(sui_sando, maxRow+=1, i);


					//--------------------------- 水相中食塩計算処理-----------------------------
					//④食塩合計量/（①合計量ー②油含有合計量）
					BigDecimal sui_shokuen = new BigDecimal("0.00");
					if(goleiShokuen.intValue() > 0 && (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0){
						sui_shokuen = goleiShokuen.divide((goleiZyuryo.subtract(goleiGanyu)), 2, BigDecimal.ROUND_HALF_UP);
					}

					//データ挿入
					table.setValueAt(sui_shokuen, 4, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSyokuen(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(sui_shokuen.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//配合表(試作表①)へ挿入
					ListMeisai.setValueAt(sui_shokuen, maxRow+=1, i);


					//-------------------------- 水相中酢酸計算処理------------------------------
					//③酢酸合計量/（①合計量ー②油含有合計量）
					BigDecimal sui_sakusan = new BigDecimal("0.00");
					if(goleiSakusan.intValue() > 0 && (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0){
						sui_sakusan = goleiSakusan.divide((goleiZyuryo.subtract(goleiGanyu)), 2, BigDecimal.ROUND_HALF_UP);
					}

					//データ挿入
					table.setValueAt(sui_sakusan, 5, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoSakusan(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(sui_sakusan.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//配合表(試作表①)へ挿入
					ListMeisai.setValueAt(sui_sakusan, maxRow+=1, i);

// ADD start 20121003 QP@20505 No.24
					//--------------------------- 水相中ＭＳＧ計算処理-----------------------------
					//ＭＳＧ合計量/（①合計量ー②油含有合計量）
					BigDecimal sui_msg = new BigDecimal("0.00");
					if(goleiMsg.intValue() > 0 && (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0){
						sui_msg = goleiMsg.divide((goleiZyuryo.subtract(goleiGanyu)), 2, BigDecimal.ROUND_HALF_UP);
					}
					//データ挿入
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuisoMSG(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(sui_msg.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
//					//配合表へ挿入
					if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1) || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
						table.setValueAt(sui_msg, 7, i);
					}

					//----------------------------- 実効酢酸濃度（％）-----------------------------
					//水相中酢酸－水相中ＭＳＧ×(0.5791×ｐＨ－1.9104)/ 187.13×60
					double dblJsnKoteiValue1 = checkNumericDouble(DataCtrl.getInstance().getLiteralDataJikkoSakusanNodo().getAryBiko().get(0));
					double dblJsnKoteiValue2 = checkNumericDouble(DataCtrl.getInstance().getLiteralDataJikkoSakusanNodo().getAryBiko().get(1));
					double dblJsnKoteiValue3 = checkNumericDouble(DataCtrl.getInstance().getLiteralDataJikkoSakusanNodo().getAryBiko().get(2));
					double dblJsnKoteiValue4 = checkNumericDouble(DataCtrl.getInstance().getLiteralDataJikkoSakusanNodo().getAryBiko().get(3));
					String strPh = "";
					BigDecimal ph = new BigDecimal("0.00");
					BigDecimal dci_sui_sakusan = new BigDecimal(sui_sakusan.toString());
					BigDecimal kotei1 = new BigDecimal(String.valueOf(dblJsnKoteiValue1)); // 0.5791
					BigDecimal kotei2 = new BigDecimal(String.valueOf(dblJsnKoteiValue2)); // 1.9104
					BigDecimal kotei3 = new BigDecimal(String.valueOf(dblJsnKoteiValue3)); // 187.13
					BigDecimal kotei4 = new BigDecimal(String.valueOf(dblJsnKoteiValue4)); // 60
					ArrayList aryTrialData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
					int cntList = aryTrialData.size();
					TrialData TrialData = new TrialData();
					for (int k=0; k<cntList; k++){
						TrialData = ((TrialData)aryTrialData.get(k)); //試作列データ
						if (TrialData.getIntShisakuSeq() == intSeq){
							strPh = TrialData.getStrPh();
						}
					}
					if (strPh == null){
					}else{
						if (strPh.length() > 0){
							//2013/04/01 MOD Start
//							ph = new BigDecimal(strPh);
							ph = new BigDecimal(strPh.replaceAll(" ","").replaceAll("　",""));
							//2013/04/01 MOD End
						}
					}
					// 計算
					BigDecimal jsn = new BigDecimal("0.00");
					jsn = ph.multiply(kotei1); // 0.5791×ph
					jsn = jsn.subtract(kotei2); // jsn－1.9104
					if (sui_msg.doubleValue() > 0 && jsn.doubleValue() > 0){
						jsn = jsn.multiply(sui_msg); // jsn×水相中MSG
						jsn = jsn.divide(kotei3, 4, BigDecimal.ROUND_HALF_UP); // jsn÷187.13
						jsn = jsn.multiply(kotei4); // jsn×60
						jsn = dci_sui_sakusan.subtract(jsn); // 水相中酢酸－jsn
						if (jsn.doubleValue() > 0){
							jsn = jsn.setScale(2, BigDecimal.ROUND_HALF_UP);
						}else{
							// 最後の減算でマイナス値になる場合
							jsn = new BigDecimal("0.00");
						}
					}else{
						// 割り算する前に0値があった場合
						jsn = new BigDecimal("0.00");
					}

					//データ挿入
					if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1) || ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
						table.setValueAt(jsn, 6, i);
					}
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuJikkoSakusanNodo(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(jsn.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
// ADD end 20121003 QP@20505 No.24
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作データ画面パネルの自動計算処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}
// ADD start 20121003 QP@20505 No.24
	/************************************************************************************
	 * 数値チェック（Double）
	 ************************************************************************************/
	public double checkNumericDouble(Object val){
		double ret = 0.0;
		//値が空文字でない場合
		if(val != null){
			ret = Double.parseDouble(val.toString());
		}
		return ret;
	}
// ADD end 20121003 QP@20505 No.24
	/************************************************************************************
	 *
	 *   全コピー
	 *    :  全コピーを行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void setCopyData() throws ExceptionBase{
		try{
			//------------------------ 試作データ画面初期化  -------------------------------
			//試作コード
			txtShisakuUser.setText(checkNull(PrototypeData.getDciShisakuUser()));
			txtShisakuNen.setText(checkNull(PrototypeData.getDciShisakuYear()));
			txtShisakuOi.setText(checkNull(PrototypeData.getDciShisakuNum()));
			//製法No
			txtSeihoUser.setText(checkNull(null));
			txtSeihoShu.setText(checkNull(null));
			txtSeihoNen.setText(checkNull(null));
			txtSeihoOi.setText(checkNull(null));
			//登録日
			ilTorokuHi.setText(checkNull(PrototypeData.getStrTorokuhi()));
			//更新日

			//UPD 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御のため、サーバサイドの更新日を編集
//			ilKosinHi.setText(checkNull(PrototypeData.getStrKosinhi()));
			String strKoshin = checkNull(PrototypeData.getStrKosinhi());
			if(strKoshin.length() >= 10 ){
				strKoshin = strKoshin.substring(0,10);
				strKoshin = strKoshin.replace('-', '/');
			}
			ilKosinHi.setText(strKoshin);

			//登録者
			ilTorokuSha.setText(checkNull(PrototypeData.getStrKosinNm()));
			//更新者
			ilKosinSha.setText(checkNull(PrototypeData.getStrTorokuNm()));
			//試算日
			iiShisanHi.setText(checkNull(seiho_Shisan));
			//試算確定日
			iiKakuteiHi.setText(checkNull(seiho_kakutei));

			//--------------------------- 配合表(試作表①)初期化  --------------------------------
			//試作ヘッダ-製法&試算マーク
			TableBase ListHeader = tb.getTrial1Panel().getTrial1().getListHeader();
			for(int i=0; i<ListHeader.getColumnCount(); i++){
				MiddleCellEditor mc = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
				DefaultCellEditor dc = (DefaultCellEditor)mc.getTableCellEditor(0);
				CheckboxBase cb = (CheckboxBase)dc.getComponent();
				cb.setText("");
			}
			tb.getTrial1Panel().getTrial1().setHeaderShisakuER();

			//--------------------------- 基本情報(試作表③)初期化  --------------------------------
			//所属グループ
			tb.getTrial3Panel().getTxtGroup().setText(PrototypeData.getStrGroupNm());
			//所属チーム
			tb.getTrial3Panel().getTxtTeam().setText(PrototypeData.getStrTeamNm());





//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.30,No.33,No.34
			//編集可否フラグ初期化
			DataCtrl.getInstance().getTrialTblData().setShisakuListHenshuOkFg_init();


			//配合表(試作表①)初期化--------------------------------------------------------------------------------------------------
			//配合明細テーブル取得
			TableBase tbHaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();

			//配合明細行数ループ
			for(int i=0; i<tbHaigoMeisai.getRowCount(); i++){

				//2列目のオブジェクト取得
				MiddleCellEditor mcKotei = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor tcKotei = (DefaultCellEditor)mcKotei.getTableCellEditor(i);

				//工程行だった場合
				if(((JComponent)tcKotei.getComponent()) instanceof CheckboxBase){

					//工程属性を使用可能--------------------------------------------------------------
					//エディタ設定
					MiddleCellEditor mceKoteiZokusei = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 3);
					DefaultCellEditor dceKoteiZokusei = (DefaultCellEditor)mceKoteiZokusei.getTableCellEditor(i);
					((ComboBase)dceKoteiZokusei.getComponent()).setEnabled(true);
					//レンダラ設定
					MiddleCellRenderer mcrKoteiZokusei =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 3);
					ComboBoxCellRenderer tfcrmcrKoteiZokusei =  (ComboBoxCellRenderer)mcrKoteiZokusei.getTableCellRenderer(i);
					tfcrmcrKoteiZokusei.setColor(Color.white);

					//試作リスト取得
					TableBase tbListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
					for(int j=0; j<tbListMeisai.getColumnCount(); j++){
						//配合量を使用可能--------------------------------------------------------------
						//レンダラ設定
						MiddleCellRenderer mcrRyo =  (MiddleCellRenderer)tbListMeisai.getCellRenderer(i, j);
						TextFieldCellRenderer tfcrRyo =  (TextFieldCellRenderer)mcrRyo.getTableCellRenderer(i);
						tfcrRyo.setColor(Color.white);
					}
				}
				//工程行以外
				else{

					//3列目のオブジェクト取得
					MiddleCellEditor mcGenryo = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 2);
					DefaultCellEditor tcGenryo = (DefaultCellEditor)mcGenryo.getTableCellEditor(i);

					//原料行だった場合
					if(((JComponent)tcGenryo.getComponent()) instanceof CheckboxBase){

						//工程キー項目取得
						CheckboxBase chkGenryo = (CheckboxBase)tcGenryo.getComponent();
						int koteiCd = Integer.parseInt(chkGenryo.getPk1());
						int koteiSeq = Integer.parseInt(chkGenryo.getPk2());

						//セル色取得
						int Iro = Integer.parseInt(DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoColor(koteiCd,koteiSeq));

						//新規原料orコメント行orNULL
						boolean sinki_chk = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoCdSinki(koteiCd, koteiSeq);

						//原料コードを使用可能-------------------------------------------------------------
						//エディタ設定
						MiddleCellEditor mceGenryoCd = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 3);
						DefaultCellEditor dceGenryoCd = (DefaultCellEditor)mceGenryoCd.getTableCellEditor(i);
						((HankakuTextbox)dceGenryoCd.getComponent()).setBackground(Color.white);
						((HankakuTextbox)dceGenryoCd.getComponent()).setEditable(true);
						//レンダラ設定
						MiddleCellRenderer mcrGenryoCd =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 3);
						TextFieldCellRenderer tfcrGenryoCd =  (TextFieldCellRenderer)mcrGenryoCd.getTableCellRenderer(i);
						tfcrGenryoCd.setColor(Color.white);

						//原料名を使用可能-------------------------------------------------------------
						//新規原料orコメント行orNULLの場合：編集可能
						if(sinki_chk){
							//エディタ設定
							MiddleCellEditor mceGenryoNm = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 4);
							DefaultCellEditor dceGenryoNm = (DefaultCellEditor)mceGenryoNm.getTableCellEditor(i);
							((TextboxBase)dceGenryoNm.getComponent()).setBackground(Color.white);
							((TextboxBase)dceGenryoNm.getComponent()).setEditable(true);
							//レンダラ設定
							MiddleCellRenderer mcrGenryoNm =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 4);
							TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)mcrGenryoNm.getTableCellRenderer(i);
							tfcrGenryoNm.setColor(new Color(Iro));
						}
						//新規原料orコメント行orNULLの場合：処理しない
						else{

						}

						//単価を使用可能-------------------------------------------------------------
						//新規原料orコメント行orNULLの場合：編集可能
						if(sinki_chk){
							//エディタ設定
							MiddleCellEditor mceTanka = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 5);
							DefaultCellEditor dceTanka = (DefaultCellEditor)mceTanka.getTableCellEditor(i);
							((TextboxBase)dceTanka.getComponent()).setBackground(Color.white);
							((TextboxBase)dceTanka.getComponent()).setEditable(true);
							//レンダラ設定
							MiddleCellRenderer mcrTanka =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 5);
							TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)mcrTanka.getTableCellRenderer(i);
							tfcrTanka.setColor(new Color(Iro));
						}
						//新規原料orコメント行orNULLの場合：処理しない
						else{

						}

						//歩留を使用可能-------------------------------------------------------------
						//エディタ設定
						MiddleCellEditor mceBudomari = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 6);
						DefaultCellEditor dceBudomari = (DefaultCellEditor)mceBudomari.getTableCellEditor(i);
						((TextboxBase)dceBudomari.getComponent()).setBackground(Color.white);
						((TextboxBase)dceBudomari.getComponent()).setEditable(true);
						//レンダラ設定
						MiddleCellRenderer mcrBudomari =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 6);
						TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)mcrBudomari.getTableCellRenderer(i);
						tfcrBudomari.setColor(new Color(Iro));

						//歩留を使用可能-------------------------------------------------------------
						//エディタ設定
						MiddleCellEditor mceAbura = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(i, 7);
						DefaultCellEditor dceAbura = (DefaultCellEditor)mceAbura.getTableCellEditor(i);
						((TextboxBase)dceAbura.getComponent()).setBackground(Color.white);
						((TextboxBase)dceAbura.getComponent()).setEditable(true);
						//レンダラ設定
						MiddleCellRenderer mcrAbura =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(i, 7);
						TextFieldCellRenderer tfcrAbura =  (TextFieldCellRenderer)mcrAbura.getTableCellRenderer(i);
						tfcrAbura.setColor(new Color(Iro));

						//試作リスト取得
						TableBase tbListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
						for(int j=0; j<tbListMeisai.getColumnCount(); j++){

							//試作ＳＥＱ取得
							TableBase tbListRetu = tb.getTrial1Panel().getTrial1().getListHeader();
							MiddleCellEditor mceChk = (MiddleCellEditor)tbListRetu.getCellEditor(0, j);
							DefaultCellEditor dceChk = (DefaultCellEditor)mceChk.getTableCellEditor(0);
							int intShisakuSeq = Integer.parseInt( ((CheckboxBase)dceChk.getComponent()).getPk1() );

							//色取得
							int ryoIro = Integer.parseInt(DataCtrl.getInstance().getTrialTblData().searchShisakuListColor(intShisakuSeq, koteiCd, koteiSeq));

							//配合量を使用可能---------------------------------------------------------
							//エディタ設定
							MiddleCellEditor mceRyo = (MiddleCellEditor)tbListMeisai.getCellEditor(i, j);
							DefaultCellEditor dceRyo = (DefaultCellEditor)mceRyo.getTableCellEditor(i);
							((NumelicTextbox)dceRyo.getComponent()).setBackground(Color.white);
							((NumelicTextbox)dceRyo.getComponent()).setEnabled(true);
							//レンダラ設定
							MiddleCellRenderer mcrRyo =  (MiddleCellRenderer)tbListMeisai.getCellRenderer(i, j);
							TextFieldCellRenderer tfcrRyo =  (TextFieldCellRenderer)mcrRyo.getTableCellRenderer(i);
							tfcrRyo.setColor(new Color(ryoIro));
						}
					}
					//原料行以外
					else{
						//試作リスト取得
						TableBase tbListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
						//int gokeiShiagariGyo = tbHaigoMeisai.getRowCount()-7;
						int gokeiShiagariGyo = tbHaigoMeisai.getRowCount()-8;
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
						for(int j=0; j<tbListMeisai.getColumnCount(); j++){

							if( i == gokeiShiagariGyo ){
								//合計仕上重量を使用可能---------------------------------------------------
								//エディタ設定
								MiddleCellEditor mceRyo = (MiddleCellEditor)tbListMeisai.getCellEditor(i, j);
								DefaultCellEditor dceRyo = (DefaultCellEditor)mceRyo.getTableCellEditor(i);
								((NumelicTextbox)dceRyo.getComponent()).setBackground(Color.white);
								((NumelicTextbox)dceRyo.getComponent()).setEnabled(true);
								//レンダラ設定
								MiddleCellRenderer mcrRyo =  (MiddleCellRenderer)tbListMeisai.getCellRenderer(i, j);
								TextFieldCellRenderer tfcrRyo =  (TextFieldCellRenderer)mcrRyo.getTableCellRenderer(i);
								tfcrRyo.setColor(Color.white);
							}
							else{
								//配合量を使用可能---------------------------------------------------------
								//レンダラ設定
								MiddleCellRenderer mcrRyo =  (MiddleCellRenderer)tbListMeisai.getCellRenderer(i, j);
								TextFieldCellRenderer tfcrRyo =  (TextFieldCellRenderer)mcrRyo.getTableCellRenderer(i);
								tfcrRyo.setColor(Color.white);
							}
						}
					}
				}
			}

			//フォーカス処理（上記処理を即時反映する為）
			int row = 0;
			int col = 0;
			//配合明細
			tbHaigoMeisai.clearSelection();
			row = tbHaigoMeisai.getRowCount()-1;
			col = tbHaigoMeisai.getColumnCount()-1;
			tbHaigoMeisai.setRowSelectionInterval(0, 0);
			tbHaigoMeisai.setColumnSelectionInterval(0, 0);
			tbHaigoMeisai.setRowSelectionInterval(row, row);
			tbHaigoMeisai.setColumnSelectionInterval(col, col);
			//試作リスト明細
			TableBase tbListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
			tbListMeisai.clearSelection();
			row = tbListMeisai.getRowCount()-1;
			col = tbListMeisai.getColumnCount()-1;
			tbListMeisai.setRowSelectionInterval(0, 0);
			tbListMeisai.setColumnSelectionInterval(0, 0);
			tbListMeisai.setRowSelectionInterval(row, row);
			tbListMeisai.setColumnSelectionInterval(col, col);
			//クリア
			tbHaigoMeisai.clearSelection();
			tbListMeisai.clearSelection();


			//基本情報(試作表③)初期化--------------------------------------------------------------------------------------------------
			//小数指定編集不可
			tb.getTrial3Panel().getCmbShosu().setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
			tb.getTrial3Panel().getCmbShosu().setEnabled(true);
			//容量編集不可
			tb.getTrial3Panel().getCmbYoryo().setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
			tb.getTrial3Panel().getCmbYoryo().getEditor().getEditorComponent().setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
			tb.getTrial3Panel().getCmbYoryo().setEnabled(true);
			//容量単位編集不可
			tb.getTrial3Panel().getCmbtani().setBackground(Color.white);
			tb.getTrial3Panel().getCmbtani().setEnabled(true);


			//原価試算(試作表⑤)初期化--------------------------------------------------------------------------------------------------
			TableBase table5 = tb.getTrial5Panel().getTable();
			for(int i=0; i<table5.getColumnCount(); i++){
				//充填量水相を使用可能--------------------------------------------------------
				//エディタ設定
				MiddleCellEditor mceSuiso = (MiddleCellEditor)table5.getCellEditor(3, i);
				DefaultCellEditor dceSuiso = (DefaultCellEditor)mceSuiso.getTableCellEditor(3);
				((NumelicTextbox)dceSuiso.getComponent()).setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				((NumelicTextbox)dceSuiso.getComponent()).setEditable(true);
				//レンダラ設定
				MiddleCellRenderer mcrSuiso =  (MiddleCellRenderer)table5.getCellRenderer(3, i);
				TextFieldCellRenderer tfcrSuiso =  (TextFieldCellRenderer)mcrSuiso.getTableCellRenderer(3);
				tfcrSuiso.setColor(JwsConstManager.SHISAKU_ITEM_COLOR2);

				//充填量油相を使用可能--------------------------------------------------------
				//エディタ設定
				MiddleCellEditor mceYuso = (MiddleCellEditor)table5.getCellEditor(4, i);
				DefaultCellEditor dceYuso = (DefaultCellEditor)mceYuso.getTableCellEditor(4);
				((NumelicTextbox)dceYuso.getComponent()).setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				((NumelicTextbox)dceYuso.getComponent()).setEditable(true);
				//レンダラ設定
				MiddleCellRenderer mcrYuso =  (MiddleCellRenderer)table5.getCellRenderer(4, i);
				TextFieldCellRenderer tfcrYuso =  (TextFieldCellRenderer)mcrYuso.getTableCellRenderer(4);
				tfcrYuso.setColor(JwsConstManager.SHISAKU_ITEM_COLOR2);
			}
//add end   -------------------------------------------------------------------------------

		}catch(Exception e){
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("全コピーが失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   特徴コピー
	 *    :  特徴コピーを行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void Copy_Tokutyo() throws ExceptionBase{
		try{
			//試作データ初期化
			DataCtrl.getInstance().getTrialTblData().setTraialData(1);

			//試作コード初期化
			//試作CD-社員CD
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuUser(null);
			//試作CD-年
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuYear(null);
			//試作CD-追番
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuNum(null);


		}catch(Exception e){

			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("特徴コピーが失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *  製法コピー処理　XML通信処理（JW050）
	 *    :  製法コピー処理XMLデータ通信（JW050）を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW050(String strSeq) throws ExceptionBase{
		try{
			//--------------------------- 送信パラメータ格納  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strShubetuNm = DataCtrl.getInstance().getLiteralDataShubetu().selectLiteralNm(
												DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShubetu());


			//--------------------------- 送信XMLデータ作成  ---------------------------------
			xmlJW050 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root追加  ------------------------------------
			xmlJW050.AddXmlTag("","JW050");
			arySetTag.clear();

			//------------------------- 機能ID追加（USERINFO）  ------------------------------
			xmlJW050.AddXmlTag("JW050", "USERINFO");

			//----------------------------- テーブルタグ追加  --------------------------------
			xmlJW050.AddXmlTag("USERINFO", "table");

			//------------------------------ レコード追加  -----------------------------------
			//処理区分
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//ユーザID
			arySetTag.add(new String[]{"id_user",strUser});
			//XMLへレコード追加
			xmlJW050.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//--------------------------- 機能ID追加（製法No登録）  --------------------------
			xmlJW050.AddXmlTag("JW050", "SA500");
			//　テーブルタグ追加
			xmlJW050.AddXmlTag("SA500", "table");
			//　レコード追加
			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
			arySetTag.add(new String[]{"nen", strShisaku_nen});
			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
			arySetTag.add(new String[]{"seq_shisaku", strSeq});
			arySetTag.add(new String[]{"no_shubetu", checkNull(DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShubetuNo())});
			arySetTag.add(new String[]{"cd_shubetu", checkNull(strShubetuNm)});

			//コメントコード
			int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();
			String commentCd = commentSet(keta);
			arySetTag.add(new String[]{"cd_comment", commentCd});


			xmlJW050.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------- T120 配合テーブル(tr_haigo) -------------------------
			xmlJW050.AddXmlTag("SA500", "tr_haigo");

			//-------------------------------- レコード追加  ---------------------------------
			//配合データ取得
			ArrayList addHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for(int i=0; i<addHaigo.size(); i++){
				MixedData MixedData = (MixedData)addHaigo.get(i);
				//試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",checkNull(MixedData.getDciShisakuUser())});
				//試作CD-年
				arySetTag.add(new String[]{"nen",checkNull(MixedData.getDciShisakuYear())});
				//試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(MixedData.getDciShisakuNum())});
				//工程CD
				arySetTag.add(new String[]{"cd_kotei",checkNull(MixedData.getIntKoteiCd())});
				//工程SEQ
				arySetTag.add(new String[]{"seq_kotei",checkNull(MixedData.getIntKoteiSeq())});
				//工程名
				arySetTag.add(new String[]{"nm_kotei",checkNull(MixedData.getStrKouteiNm())});
				//工程属性
				arySetTag.add(new String[]{"zoku_kotei",checkNull(MixedData.getStrKouteiZokusei())});
				//工程順
				arySetTag.add(new String[]{"sort_kotei",checkNull(MixedData.getIntKoteiNo())});
				//原料順
				arySetTag.add(new String[]{"sort_genryo",checkNull(MixedData.getIntGenryoNo())});
				//原料CD
				arySetTag.add(new String[]{"cd_genryo",checkNull(MixedData.getStrGenryoCd())});
				//会社CD
				arySetTag.add(new String[]{"cd_kaisha",checkNull(MixedData.getIntKaishaCd())});
				//部署CD
				arySetTag.add(new String[]{"cd_busho",checkNull(MixedData.getIntBushoCd())});
				//原料名称
				arySetTag.add(new String[]{"nm_genryo",checkNull(MixedData.getStrGenryoNm())});
				//単価
				arySetTag.add(new String[]{"tanka",checkNull(MixedData.getDciTanka())});
				//歩留
				arySetTag.add(new String[]{"budomari",checkNull(MixedData.getDciBudomari())});
				//油含有率
				arySetTag.add(new String[]{"ritu_abura",checkNull(MixedData.getDciGanyuritu())});
				//酢酸
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(MixedData.getDciSakusan())});
				//食塩
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(MixedData.getDciShokuen())});
				//総酸
				arySetTag.add(new String[]{"ritu_sousan",checkNull(MixedData.getDciSosan())});
// ADD start 20121002 QP@20505 No.24
				//ＭＳＧ
				arySetTag.add(new String[]{"ritu_msg",checkNull(MixedData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24
				//色
				arySetTag.add(new String[]{"color",checkNull(MixedData.getStrIro())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(MixedData.getDciTorokuId())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",checkNull(MixedData.getStrTorokuHi())});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(MixedData.getDciKosinId())});
				//更新日付
				arySetTag.add(new String[]{"dt_koshin",checkNull(MixedData.getStrKosinHi())});

				//XMLへレコード追加
				xmlJW050.AddXmlTag("tr_haigo", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//配列初期化
			arySetTag.clear();

			//--------------------------------- XML送信 ------------------------------------
			System.out.println("JW050送信XML===============================================================");
			xmlJW050.dispXml();

			xcon = new XmlConnection(xmlJW050);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//--------------------------------- XML受信 ------------------------------------
			xmlJW050 = xcon.getXdocRes();

//			System.out.println();
//			System.out.println("JW050受信XML===============================================================");
//			xmlJW050.dispXml();
//			System.out.println();

			//------------------------------- Resultチェック ----------------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW050);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {

				//---------------------------- 製法記号更新 ----------------------------------
				TableBase lh = tb.getTrial1Panel().getTrial1().getListHeader();
				for(int i=0; i<lh.getColumnCount(); i++){
					//コンポーネント取得
					MiddleCellEditor selectMc = (MiddleCellEditor)lh.getCellEditor(0, i);
					DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(0);
					CheckboxBase cb = (CheckboxBase)selectDc.getComponent();
					String Mark = cb.getText();
					int sisakuSeq = Integer.parseInt(cb.getPk1());
					//記号削除
					if(Mark != null && Mark.length() > 0){
						Mark = Mark.replace(JwsConstManager.JWS_MARK_0003.toCharArray()[0], ' ');
						Mark = Mark.trim();
					}
					if(sisakuSeq == Integer.parseInt(strSeq)){
						cb.setText(JwsConstManager.JWS_MARK_0003 + " " + Mark);
					}else{
						cb.setText(Mark);
					}
				}

				//-------------------------- 製法No更新 ----------------------------------
				//機能IDの設定
				String strKinoId = "SA500";

				//全体配列取得
				ArrayList aryData = xmlJW050.GetAryTag(strKinoId);

				//機能配列取得
				ArrayList kinoData = (ArrayList)aryData.get(0);

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

						//　製法No1
						if ( recNm == "no_seiho1" ) {
							//画面表示
							txtSeihoUser.setText(recVal.split("-")[0]);
							txtSeihoShu.setText(recVal.split("-")[1]);
							txtSeihoNen.setText(recVal.split("-")[2]);
							txtSeihoOi.setText(recVal.split("-")[3]);

							//データ挿入
							DataCtrl.getInstance().getTrialTblData().UpdRetuSeiho1(
									Integer.parseInt(strSeq),
									recVal,
									DataCtrl.getInstance().getUserMstData().getDciUserid());
						}
						//　製法No2
						if ( recNm == "no_seiho2" ) {
							//データ挿入
							DataCtrl.getInstance().getTrialTblData().UpdRetuSeiho2(
									Integer.parseInt(strSeq),
									recVal,
									DataCtrl.getInstance().getUserMstData().getDciUserid());
						}

						//　製法No3
						if ( recNm == "no_seiho3" ) {
							//データ挿入
							DataCtrl.getInstance().getTrialTblData().UpdRetuSeiho3(
									Integer.parseInt(strSeq),
									recVal,
									DataCtrl.getInstance().getUserMstData().getDciUserid());
						}
						//　製法No4
						if ( recNm == "no_seiho4" ) {
							//データ挿入
							DataCtrl.getInstance().getTrialTblData().UpdRetuSeiho4(
									Integer.parseInt(strSeq),
									recVal,
									DataCtrl.getInstance().getUserMstData().getDciUserid());
						}
						//　製法No5
						if ( recNm == "no_seiho5" ) {
							//データ挿入
							DataCtrl.getInstance().getTrialTblData().UpdRetuSeiho5(
									Integer.parseInt(strSeq),
									recVal,
									DataCtrl.getInstance().getUserMstData().getDciUserid());
						}
					}
				}

				//製造工程画面
				ManufacturingPanel mp = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();
				//製法Noが選択されている場合
				if(mp.getCombo().getSelectedIndex() == 2){
					mp.dispSeiho();
				}

				//再表示
				tb.getTrial1Panel().getTrial1().setHeaderShisakuER();
				DataCtrl.getInstance().getMessageCtrl().PrintMessageString("正常に製法コピー処理が完了しました。");

			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}
		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("製法コピーが失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *  ユーザ情報取得処理　XML通信処理（JW020）
	 *    :  ユーザ情報取得
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conUserInfo() throws ExceptionBase{
		try{
			//--------------------------- 送信パラメータ格納  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//--------------------------- 送信XMLデータ作成  ---------------------------------
			xmlJWuser = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root追加  ------------------------------------
			xmlJWuser.AddXmlTag("","JW020");
			arySetTag.clear();

			//------------------------- 機能ID追加（USERINFO）  ------------------------------
			xmlJWuser.AddXmlTag("JW020", "USERINFO");

			//----------------------------- テーブルタグ追加  ---------------------------------
			xmlJWuser.AddXmlTag("USERINFO", "table");

			//------------------------------ レコード追加  -----------------------------------
			//処理区分
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//ユーザID
			arySetTag.add(new String[]{"id_user",strUser});
			//XMLへレコード追加
			xmlJWuser.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//--------------------------- 機能ID追加（排他制御）  -----------------------------
			xmlJWuser.AddXmlTag("JW020", "SA420");
			//　テーブルタグ追加
			xmlJWuser.AddXmlTag("SA420", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kubun_ziko", "0"});
			arySetTag.add(new String[]{"kubun_haita", "0"});
			arySetTag.add(new String[]{"id_user", strUser});
			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
			arySetTag.add(new String[]{"nen", strShisaku_nen});
			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
			xmlJWuser.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//--------------------------------- XML送信 ------------------------------------
			//System.out.println("JW020送信XML===============================================================");
			//xmlJWuser.dispXml();
			xcon = new XmlConnection(xmlJWuser);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//--------------------------------- XML受信 ------------------------------------
			xmlJWuser = xcon.getXdocRes();
			//System.out.println();
			//System.out.println("JW020受信XML===============================================================");
			//xmlJW020.dispXml();
			//System.out.println();

			//Resultチェック
			DataCtrl.getInstance().getResultData().setResultData(xmlJWuser);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {

			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}

			//---------------------------- ユーザマスタデータ設定  -----------------------------
			DataCtrl.getInstance().getUserMstData().setUserData(xmlJWuser);

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("ユーザデータ取得に失敗しました。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}


	/************************************************************************************
	 *
	 *  試作コピー処理（排他制御）　XML通信処理（JW020）
	 *    :  排他制御処理XMLデータ通信（JW020）を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW020() throws ExceptionBase{
		try{
			//--------------------------- 送信パラメータ格納  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//--------------------------- 送信XMLデータ作成  ---------------------------------
			xmlJW020 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root追加  ------------------------------------
			xmlJW020.AddXmlTag("","JW020");
			arySetTag.clear();

			//------------------------- 機能ID追加（USERINFO）  ------------------------------
			xmlJW020.AddXmlTag("JW020", "USERINFO");

			//----------------------------- テーブルタグ追加  ---------------------------------
			xmlJW020.AddXmlTag("USERINFO", "table");

			//------------------------------ レコード追加  -----------------------------------
			//処理区分
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//ユーザID
			arySetTag.add(new String[]{"id_user",strUser});
			//XMLへレコード追加
			xmlJW020.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//--------------------------- 機能ID追加（排他制御）  -----------------------------
			xmlJW020.AddXmlTag("JW020", "SA420");
			//　テーブルタグ追加
			xmlJW020.AddXmlTag("SA420", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kubun_ziko", "1"});
			arySetTag.add(new String[]{"kubun_haita", "0"});
			arySetTag.add(new String[]{"id_user", strUser});
			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
			arySetTag.add(new String[]{"nen", strShisaku_nen});
			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
			xmlJW020.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//--------------------------------- XML送信 ------------------------------------
			//System.out.println("JW020送信XML===============================================================");
			//xmlJW020.dispXml();
			xcon = new XmlConnection(xmlJW020);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//--------------------------------- XML受信 ------------------------------------
			xmlJW020 = xcon.getXdocRes();
			//System.out.println();
			//System.out.println("JW020受信XML===============================================================");
			//xmlJW020.dispXml();
			//System.out.println();

			//Resultチェック
			DataCtrl.getInstance().getResultData().setResultData(xmlJW020);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {

			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}
		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作データの排他制御に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   終了処理　XML通信処理（JW060）
	 *    :  終了処理XMLデータ通信（JW060）を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW060() throws ExceptionBase{
		try{
			//--------------------------- 送信パラメータ格納  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//--------------------------- 送信XMLデータ作成  ---------------------------------
			xmlJW060 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root追加  ------------------------------------
			xmlJW060.AddXmlTag("","JW060");
			arySetTag.clear();

			//------------------------- 機能ID追加（USERINFO）  ------------------------------
			xmlJW060.AddXmlTag("JW060", "USERINFO");

			//----------------------------- テーブルタグ追加  ---------------------------------
			xmlJW060.AddXmlTag("USERINFO", "table");

			//------------------------------ レコード追加  -----------------------------------
			//処理区分
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//ユーザID
			arySetTag.add(new String[]{"id_user",strUser});
			//XMLへレコード追加
			xmlJW060.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//--------------------------- 機能ID追加（排他制御）  -----------------------------
			xmlJW060.AddXmlTag("JW060", "SA420");
			//　テーブルタグ追加
			xmlJW060.AddXmlTag("SA420", "table");

			//　レコード追加
			arySetTag.add(new String[]{"kubun_ziko", "1"});
			arySetTag.add(new String[]{"kubun_haita", "0"});
			arySetTag.add(new String[]{"id_user", strUser});
			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
			arySetTag.add(new String[]{"nen", strShisaku_nen});
			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
			xmlJW060.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//　XML送信
			//System.out.println("送信XML===============================================================");
			//xmlJW060.dispXml();

			xcon = new XmlConnection(xmlJW060);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			//　XML受信
			xmlJW060 = xcon.getXdocRes();

			//System.out.println();
			//System.out.println("受信XML===============================================================");
			//xmlJW060.dispXml();
			//System.out.println();

			//Resultチェック
			DataCtrl.getInstance().getResultData().setResultData(xmlJW060);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {

			}else{
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;
			}

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("終了処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   新規登録処理　XML通信処理（JW030）
	 *    :  新規登録処理XMLデータ通信（JW030）を行う
	 *   @param intChkMsg : 登録時メッセージの指定
	 *    [0:登録, 1:自動保存(試作表), 2:自動保存(サンプル説明書), 3:自動保存(栄養計算書)]
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW030(int intChkMsg) throws ExceptionBase{
		try{


			//DataCtrl.getInstance().getMessageCtrl().PrintMessageString("正常に試作登録処理が完了しました。");

			//--------------------------- 送信パラメータ格納  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();



			String nen = new SimpleDateFormat("yy").format(new Date());


			//--------------------------- 送信XMLデータ作成  ---------------------------------
			xmlJW030 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root追加  ------------------------------------
			xmlJW030.AddXmlTag("","JW030");
			arySetTag.clear();

			//------------------------- 機能ID追加（USERINFO）  ------------------------------
			xmlJW030.AddXmlTag("JW030", "USERINFO");

			//----------------------------- テーブルタグ追加  ---------------------------------
			xmlJW030.AddXmlTag("USERINFO", "table");

			//------------------------------ レコード追加  -----------------------------------
			//処理区分
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//ユーザID
			arySetTag.add(new String[]{"id_user",strUser});
			//XMLへレコード追加
			xmlJW030.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------ 機能ID追加（試作情報登録）  ---------------------------
			xmlJW030.AddXmlTag("JW030", "SA490");

			//--------------------- T110 試作品テーブル(tr_shisakuhin) ----------------------
			xmlJW030.AddXmlTag("SA490", "tr_shisakuhin");

			//-------------------------------- レコード追加  ---------------------------------

			//試作品データ取得
			PrototypeData addPrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
			//試作CD-社員CD
			arySetTag.add(new String[]{"cd_shain", strUser});
			//試作CD-年
			arySetTag.add(new String[]{"nen", nen});
			//試作CD-追番
			arySetTag.add(new String[]{"no_oi", checkNull(addPrototypeData.getDciShisakuNum())});
			//依頼番号
			arySetTag.add(new String[]{"no_irai", checkNull(addPrototypeData.getStrIrai())});
			//品名
			arySetTag.add(new String[]{"nm_hin", checkNull(addPrototypeData.getStrHinnm())});

			//指定工場-会社CD
			if(addPrototypeData.getIntKaishacd() <= 0){ //会社コードが0以下の場合は空
				arySetTag.add(new String[]{"cd_kaisha", ""});
			}else{
				arySetTag.add(new String[]{"cd_kaisha", checkNull(addPrototypeData.getIntKaishacd())});
			}
			//指定工場-工場CD
			if(addPrototypeData.getIntKojoco() <= 0){ //工場コードが0以下の場合は空
				arySetTag.add(new String[]{"cd_kojo", ""});
			}else{
				arySetTag.add(new String[]{"cd_kojo", checkNull(addPrototypeData.getIntKojoco())});
			}

			//種別CD
			arySetTag.add(new String[]{"cd_shubetu", checkNull(addPrototypeData.getStrShubetu())});
			//種別No
			arySetTag.add(new String[]{"no_shubetu", checkNull(addPrototypeData.getStrShubetuNo())});
			//グループCD
			arySetTag.add(new String[]{"cd_group", checkNull(addPrototypeData.getIntGroupcd())});
			//チームCD
			arySetTag.add(new String[]{"cd_team", checkNull(addPrototypeData.getIntTeamcd())});
			//一括表示CD
			arySetTag.add(new String[]{"cd_ikatu", checkNull(addPrototypeData.getStrIkatu())});
			//ジャンルCD
			arySetTag.add(new String[]{"cd_genre", checkNull(addPrototypeData.getStrZyanru())});
			//ユーザCD
			arySetTag.add(new String[]{"cd_user", checkNull(addPrototypeData.getStrUsercd())});
			//特徴原料
			arySetTag.add(new String[]{"tokuchogenryo", checkNull(addPrototypeData.getStrTokutyo())});
			//用途
			arySetTag.add(new String[]{"youto", checkNull(addPrototypeData.getStrYoto())});
			//価格帯CD
			arySetTag.add(new String[]{"cd_kakaku", checkNull(addPrototypeData.getStrKakaku())});
			//担当営業CD
			arySetTag.add(new String[]{"cd_eigyo", checkNull(addPrototypeData.getStrTantoEigyo())});
			//製造方法CD
			arySetTag.add(new String[]{"cd_hoho", checkNull(addPrototypeData.getStrSeizocd())});
			//充填方法CD
			arySetTag.add(new String[]{"cd_juten", checkNull(addPrototypeData.getStrZyutencd())});
			//殺菌方法
			arySetTag.add(new String[]{"hoho_sakin", checkNull(addPrototypeData.getStrSakin())});
			//容器・包材
			arySetTag.add(new String[]{"youki", checkNull(addPrototypeData.getStrYokihozai())});
			//容量
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//seisuCheck(checkNull(addPrototypeData.getStrYoryo()), "試作データ画面 試作表③ 容量", 0);
			seisuCheck(checkNull(addPrototypeData.getStrYoryo()), "試作データ画面 基本情報 容量", 0);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"yoryo", checkNull(addPrototypeData.getStrYoryo())});

			//容量単位CD
			arySetTag.add(new String[]{"cd_tani", checkNull(addPrototypeData.getStrTani())});

			//入り数
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//seisuCheck(checkNull(addPrototypeData.getStrIrisu()), "試作データ画面 試作表③ 入り数", 0);
			seisuCheck(checkNull(addPrototypeData.getStrIrisu()), "試作データ画面 基本情報 入り数", 0);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"su_iri", checkNull(addPrototypeData.getStrIrisu())});

			//取扱温度CD
			arySetTag.add(new String[]{"cd_ondo", checkNull(addPrototypeData.getStrOndo())});
			//賞味期間
			arySetTag.add(new String[]{"shomikikan", checkNull(addPrototypeData.getStrShomi())});

			//原価
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ShosuCheck(checkNull(addPrototypeData.getStrGenka()), 2, "試作データ画面 試作表③ 原価希望", 0);
			ShosuCheck(checkNull(addPrototypeData.getStrGenka()), 2, "試作データ画面 基本情報 原価希望", 0);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"genka", checkNull(addPrototypeData.getStrGenka())});

			//売価
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ShosuCheck(checkNull(addPrototypeData.getStrBaika()), 2, "試作データ画面 試作表③ 売価希望", 0);
			ShosuCheck(checkNull(addPrototypeData.getStrBaika()), 2, "試作データ画面 基本情報 売価希望", 0);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"baika", checkNull(addPrototypeData.getStrBaika())});

			//想定物量
			arySetTag.add(new String[]{"buturyo", checkNull(addPrototypeData.getStrSotei())});
			//発売時期
			arySetTag.add(new String[]{"dt_hatubai", checkNull(addPrototypeData.getStrHatubai())});
			//計画売上
			arySetTag.add(new String[]{"uriage_k", checkNull(addPrototypeData.getStrKeikakuUri())});
			//計画利益
			arySetTag.add(new String[]{"rieki_k", checkNull(addPrototypeData.getStrKeikakuRie())});
			//販売後売上
			arySetTag.add(new String[]{"uriage_h", checkNull(addPrototypeData.getStrHanbaigoUri())});
			//販売後利益
			arySetTag.add(new String[]{"rieki_h", checkNull(addPrototypeData.getStrHanbaigoRie())});
			//荷姿CD
			arySetTag.add(new String[]{"cd_nisugata", checkNull(addPrototypeData.getStrNishugata())});
			//総合ﾒﾓ
			arySetTag.add(new String[]{"memo", checkNull(addPrototypeData.getStrSogo())});
			//小数指定
			arySetTag.add(new String[]{"keta_shosu", checkNull(addPrototypeData.getStrShosu())});

			//小数指定値１
			String cd = addPrototypeData.getStrShosu();
			int val1 = 0;
			if(cd != null && cd.length()>0){
				val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(cd));
			}
			arySetTag.add(new String[]{"keta_shosu_val1", Integer.toString(val1)});

			//廃止区
			arySetTag.add(new String[]{"kbn_haishi", checkNull(addPrototypeData.getIntHaisi())});
			//排他
			arySetTag.add(new String[]{"kbn_haita", checkNull(addPrototypeData.getDciHaita())});
			//製法試作
			arySetTag.add(new String[]{"seq_shisaku", checkNull(addPrototypeData.getIntSeihoShisaku())});
			//試作メモ
			arySetTag.add(new String[]{"memo_shisaku", checkNull(addPrototypeData.getStrShisakuMemo())});
			//注意事項表示
			arySetTag.add(new String[]{"flg_chui", checkNull(addPrototypeData.getIntChuiFg())});
			//登録者ID
			arySetTag.add(new String[]{"id_toroku", checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
			//登録日付
			arySetTag.add(new String[]{"dt_toroku", DataCtrl.getInstance().getTrialTblData().getSysDate()});
			//更新者ID
			arySetTag.add(new String[]{"id_koshin", checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});

			//更新日付
			//UPD 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため新規登録を指示するため日付に何もいれない。
//			arySetTag.add(new String[]{"dt_koshin", DataCtrl.getInstance().getTrialTblData().getSysDate()});
			arySetTag.add(new String[]{"dt_koshin", checkNull(addPrototypeData.getStrKosinhi())});

//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
			//工程パターン
			arySetTag.add(new String[]{"pt_kotei", checkNull(addPrototypeData.getStrPt_kotei())});
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------

			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
			arySetTag.add(new String[]{"flg_secret", checkNull(addPrototypeData.getStrSecret())});
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
//ADD 2013/06/19 ogawa 【QP@30151】No.9 start
			//販責会社CD
			if(addPrototypeData.getIntHansekicd() <= 0){ //販責会社コードが0以下の場合は空
				arySetTag.add(new String[]{"cd_hanseki", ""});
			}else{
				arySetTag.add(new String[]{"cd_hanseki", checkNull(addPrototypeData.getIntHansekicd())});
			}
//ADD 2013/06/19 ogawa 【QP@30151】No.9 end

			//XMLへレコード追加
			xmlJW030.AddXmlTag("tr_shisakuhin", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------- T120 配合テーブル(tr_haigo) -------------------------
			xmlJW030.AddXmlTag("SA490", "tr_haigo");

			//-------------------------------- レコード追加  ---------------------------------
			//配合データ取得
			ArrayList addHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for(int i=0; i<addHaigo.size(); i++){
				MixedData MixedData = (MixedData)addHaigo.get(i);
				//試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",strUser});
				//試作CD-年
				arySetTag.add(new String[]{"nen",nen});
				//試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(MixedData.getDciShisakuNum())});
				//工程CD
				arySetTag.add(new String[]{"cd_kotei",checkNull(MixedData.getIntKoteiCd())});
				//工程SEQ
				arySetTag.add(new String[]{"seq_kotei",checkNull(MixedData.getIntKoteiSeq())});
				//工程名
				arySetTag.add(new String[]{"nm_kotei",checkNull(MixedData.getStrKouteiNm())});
				//工程属性
				arySetTag.add(new String[]{"zoku_kotei",checkNull(MixedData.getStrKouteiZokusei())});
				//工程順
				arySetTag.add(new String[]{"sort_kotei",checkNull(MixedData.getIntKoteiNo())});
				//原料順
				arySetTag.add(new String[]{"sort_genryo",checkNull(MixedData.getIntGenryoNo())});
				//原料CD
				arySetTag.add(new String[]{"cd_genryo",checkNull(MixedData.getStrGenryoCd())});
				//会社CD
				arySetTag.add(new String[]{"cd_kaisha",checkNull(MixedData.getIntKaishaCd())});
				//部署CD
				arySetTag.add(new String[]{"cd_busho",checkNull(MixedData.getIntBushoCd())});
				//原料名称
				arySetTag.add(new String[]{"nm_genryo",checkNull(MixedData.getStrGenryoNm())});
				//単価
				arySetTag.add(new String[]{"tanka",checkNull(MixedData.getDciTanka())});
				//歩留
				arySetTag.add(new String[]{"budomari",checkNull(MixedData.getDciBudomari())});
				//油含有率
				arySetTag.add(new String[]{"ritu_abura",checkNull(MixedData.getDciGanyuritu())});
				//酢酸
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(MixedData.getDciSakusan())});
				//食塩
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(MixedData.getDciShokuen())});
				//総酸
				arySetTag.add(new String[]{"ritu_sousan",checkNull(MixedData.getDciSosan())});
// ADD start 20121002 QP@20505 No.24
				//ＭＳＧ
				arySetTag.add(new String[]{"ritu_msg",checkNull(MixedData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24
				//色
				arySetTag.add(new String[]{"color",checkNull(MixedData.getStrIro())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});

				//更新日付
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XMLへレコード追加
				xmlJW030.AddXmlTag("tr_haigo", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//----------------------- T131 試作テーブル(tr_shisaku) -------------------------
			xmlJW030.AddXmlTag("SA490", "tr_shisaku");

			//-------------------------------- レコード追加  ---------------------------------
			//試作列データ取得
			ArrayList addRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			for(int i=0; i<addRetu.size(); i++){
				TrialData TrialData = (TrialData)addRetu.get(i);
				//試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",strUser});
				//試作CD-年
				arySetTag.add(new String[]{"nen",nen});
				//試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(TrialData.getDciShisakuNum())});
				//試作SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(TrialData.getIntShisakuSeq())});
				//試作表示順
				arySetTag.add(new String[]{"sort_shisaku",checkNull(TrialData.getIntHyojiNo())});
				//注意事項NO
				arySetTag.add(new String[]{"no_chui",checkNull(TrialData.getStrTyuiNo())});
				//サンプルNO（名称）
				arySetTag.add(new String[]{"nm_sample",checkNull(TrialData.getStrSampleNo())});
				//メモ
				arySetTag.add(new String[]{"memo",checkNull(TrialData.getStrMemo())});
				//印刷Flg
				arySetTag.add(new String[]{"flg_print",checkNull(TrialData.getIntInsatuFlg())});
				//自動計算Flg
				arySetTag.add(new String[]{"flg_auto",checkNull(TrialData.getIntZidoKei())});
				//原価試算No
				arySetTag.add(new String[]{"no_shisan",checkNull(TrialData.getIntGenkaShisan())});
				//製法No-1
				arySetTag.add(new String[]{"no_seiho1",checkNull(TrialData.getStrSeihoNo1())});
				//製法No-2
				arySetTag.add(new String[]{"no_seiho2",checkNull(TrialData.getStrSeihoNo2())});
				//製法No-3
				arySetTag.add(new String[]{"no_seiho3",checkNull(TrialData.getStrSeihoNo3())});
				//製法No-4
				arySetTag.add(new String[]{"no_seiho4",checkNull(TrialData.getStrSeihoNo4())});
				//製法No-5
				arySetTag.add(new String[]{"no_seiho5",checkNull(TrialData.getStrSeihoNo5())});
				//総酸
				arySetTag.add(new String[]{"ritu_sousan",checkNull(TrialData.getDciSosan())});
				//総酸-出力Flg
				arySetTag.add(new String[]{"flg_sousan",checkNull(TrialData.getIntSosanFlg())});
				//食塩
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(TrialData.getDciShokuen())});
				//食塩-出力Flg
				arySetTag.add(new String[]{"flg_shokuen",checkNull(TrialData.getIntShokuenFlg())});
// ADD start 20121002 QP@20505 No.24
				//ＭＳＧ
				arySetTag.add(new String[]{"ritu_msg",checkNull(TrialData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24
				//水相中酸度
				arySetTag.add(new String[]{"sando_suiso",checkNull(TrialData.getDciSuiSando())});
				//水相中酸度-出力Flg
				arySetTag.add(new String[]{"flg_sando_suiso",checkNull(TrialData.getIntSuiSandoFlg())});
				//水相中食塩
				arySetTag.add(new String[]{"shokuen_suiso",checkNull(TrialData.getDciSuiShokuen())});
				//水相中食塩-出力Flg
				arySetTag.add(new String[]{"flg_shokuen_suiso",checkNull(TrialData.getIntSuiShokuenFlg())});
				//水相中酢酸
				arySetTag.add(new String[]{"sakusan_suiso",checkNull(TrialData.getDciSuiSakusan())});
				//水相中酢酸-出力Flg
				arySetTag.add(new String[]{"flg_sakusan_suiso",checkNull(TrialData.getIntSuiSandoFlg())});
				//糖度
				arySetTag.add(new String[]{"toudo",checkNull(TrialData.getStrToudo())});
				//糖度-出力Flg
				arySetTag.add(new String[]{"flg_toudo",checkNull(TrialData.getIntToudoFlg())});
				//粘度
				arySetTag.add(new String[]{"nendo",checkNull(TrialData.getStrNendo())});
				//粘度-出力Flg
				arySetTag.add(new String[]{"flg_nendo",checkNull(TrialData.getIntNendoFlg())});
				//温度
				arySetTag.add(new String[]{"ondo",checkNull(TrialData.getStrOndo())});
				//温度-出力Flg
				arySetTag.add(new String[]{"flg_ondo",checkNull(TrialData.getIntOndoFlg())});
				//PH
				arySetTag.add(new String[]{"ph",checkNull(TrialData.getStrPh())});
				//PH - 出力Flg
				arySetTag.add(new String[]{"flg_ph",checkNull(TrialData.getIntPhFlg())});
				//総酸：分析
				arySetTag.add(new String[]{"ritu_sousan_bunseki",checkNull(TrialData.getStrSosanBunseki())});
				//総酸：分析-出力Flg
				arySetTag.add(new String[]{"flg_sousan_bunseki",checkNull(TrialData.getIntSosanBunsekiFlg())});
				//食塩：分析
				arySetTag.add(new String[]{"ritu_shokuen_bunseki",checkNull(TrialData.getStrShokuenBunseki())});
				//食塩：分析-出力Flg
				arySetTag.add(new String[]{"flg_shokuen_bunseki",checkNull(TrialData.getIntShokuenBunsekiFlg())});
				//比重
				arySetTag.add(new String[]{"hiju",checkNull(TrialData.getStrHizyu())});
				//比重-出力Flg
				arySetTag.add(new String[]{"flg_hiju",checkNull(TrialData.getIntHizyuFlg())});
				//水分活性
				arySetTag.add(new String[]{"suibun_kasei",checkNull(TrialData.getStrSuibun())});
				//水分活性-出力Flg
				arySetTag.add(new String[]{"flg_suibun_kasei",checkNull(TrialData.getIntSuibunFlg())});
				//アルコール
				arySetTag.add(new String[]{"alcohol",checkNull(TrialData.getStrArukoru())});
				//アルコール-出力Flg
				arySetTag.add(new String[]{"flg_alcohol",checkNull(TrialData.getIntArukoruFlg())});
				//作成メモ
				arySetTag.add(new String[]{"memo_sakusei",checkNull(TrialData.getStrSakuseiMemo())});
				//作成メモ-出力Flg
				arySetTag.add(new String[]{"flg_memo",checkNull(TrialData.getIntSakuseiMemoFlg())});
				//評価
				arySetTag.add(new String[]{"hyoka",checkNull(TrialData.getStrHyoka())});
				//評価-出力Flg
				arySetTag.add(new String[]{"flg_hyoka",checkNull(TrialData.getIntHyokaFlg())});
				//フリー①タイトル
				arySetTag.add(new String[]{"free_title1",checkNull(TrialData.getStrFreeTitle1())});
				//フリー①内容
				arySetTag.add(new String[]{"free_value1",checkNull(TrialData.getStrFreeNaiyo1())});
				//フリー①-出力Flg
				arySetTag.add(new String[]{"flg_free1",checkNull(TrialData.getIntFreeFlg())});
				//フリー②タイトル
				arySetTag.add(new String[]{"free_title2",checkNull(TrialData.getStrFreeTitle2())});
				//フリー②内容
				arySetTag.add(new String[]{"free_value2",checkNull(TrialData.getStrFreeNaiyo2())});
				//フリー②-出力Flg
				arySetTag.add(new String[]{"flg_free2",checkNull(TrialData.getIntFreeFl2())});
				//フリー③タイトル
				arySetTag.add(new String[]{"free_title3",checkNull(TrialData.getStrFreeTitle3())});
				//フリー③内容
				arySetTag.add(new String[]{"free_value3",checkNull(TrialData.getStrFreeNaiyo3())});
				//フリー③-出力Flg
				arySetTag.add(new String[]{"flg_free3",checkNull(TrialData.getIntFreeFl3())});
				//試作日付
				arySetTag.add(new String[]{"dt_shisaku",checkNull(TrialData.getStrShisakuHi())});
				//仕上重量
				arySetTag.add(new String[]{"juryo_shiagari_g",checkNull(TrialData.getDciShiagari())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//更新日付
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//原価依頼フラグ
				arySetTag.add(new String[]{"flg_shisanIrai",Integer.toString(TrialData.getFlg_shisanIrai())});
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
				//計算式
				arySetTag.add(new String[]{"siki_keisan",checkNull(TrialData.getStrKeisanSiki())});
//add end   -------------------------------------------------------------------------------
//2011/04/12 QP@10181_No.67 TT Nishigawa Change Start -------------------------
				//キャンセルFG
				arySetTag.add(new String[]{"flg_cancel",checkNull(TrialData.getFlg_cancel())});
//2011/04/12 QP@10181_No.67 TT Nishigawa Change Start -------------------------
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
				//水相比重
				arySetTag.add(new String[]{"hiju_sui",checkNull(TrialData.getStrHiju_sui())});
				//水相比重-出力Flg
				arySetTag.add(new String[]{"flg_hiju_sui",checkNull(TrialData.getIntHiju_sui_fg())});
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------
// ADD start 20121003 QP@20505 No.24
				// --------------- 工程パターン その他・加食 -----------------
				//フリー水分活性 タイトル
				arySetTag.add(new String[]{"freetitle_suibun_kasei",checkNull(TrialData.getStrFreeTitleSuibunKasei())});
				//フリー水分活性 内容
				arySetTag.add(new String[]{"free_suibun_kasei",checkNull(TrialData.getStrFreeSuibunKasei())});
				//フリー水分活性 - 出力Flg
				arySetTag.add(new String[]{"flg_freeSuibunKasei",checkNull(TrialData.getIntFreeSuibunKaseiFlg())});
				//フリーアルコール タイトル
				arySetTag.add(new String[]{"freetitle_alcohol",checkNull(TrialData.getStrFreeTitleAlchol())});
				//フリーアルコール 内容
				arySetTag.add(new String[]{"free_alcohol",checkNull(TrialData.getStrFreeAlchol())});
				//フリーアルコール - 出力Flg
				arySetTag.add(new String[]{"flg_freeAlchol",checkNull(TrialData.getIntFreeAlcholFlg())});
				// --------------- 工程パターン 1液・2液 -----------------
				//実効酢酸濃度
				arySetTag.add(new String[]{"jikkoSakusanNodo",checkNull(TrialData.getDciJikkoSakusanNodo())});
				//実効酢酸濃度 - 出力flg
				arySetTag.add(new String[]{"flg_jikkoSakusanNodo",checkNull(TrialData.getIntJikkoSakusanNodoFlg())});
				//水相中ＭＳＧ
				arySetTag.add(new String[]{"msg_suiso",checkNull(TrialData.getDciSuisoMSG())});
				//水相中ＭＳＧ - 出力flg
				arySetTag.add(new String[]{"flg_msg_suiso",checkNull(TrialData.getIntSuisoMSGFlg())});
				//フリー粘度 タイトル
				arySetTag.add(new String[]{"freetitle_nendo",checkNull(TrialData.getStrFreeTitleNendo())});
				//フリー粘度 内容
				arySetTag.add(new String[]{"free_nendo",checkNull(TrialData.getStrFreeNendo())});
				//フリー粘度 - 出力Flg
				arySetTag.add(new String[]{"flg_freeNendo",checkNull(TrialData.getIntFreeNendoFlg())});
				//フリー温度 タイトル
				arySetTag.add(new String[]{"freetitle_ondo",checkNull(TrialData.getStrFreeTitleOndo())});
				//フリー温度 内容
				arySetTag.add(new String[]{"free_ondo",checkNull(TrialData.getStrFreeOndo())});
				//フリー温度 - 出力Flg
				arySetTag.add(new String[]{"flg_freeOndo",checkNull(TrialData.getIntFreeOndoFlg())});
				//フリー④タイトル
				arySetTag.add(new String[]{"free_title4",checkNull(TrialData.getStrFreeTitle4())});
				//フリー④内容
				arySetTag.add(new String[]{"free_value4",checkNull(TrialData.getStrFreeNaiyo4())});
				//フリー④-出力Flg
				arySetTag.add(new String[]{"flg_free4",checkNull(TrialData.getIntFreeFlg4())});
				//フリー⑤タイトル
				arySetTag.add(new String[]{"free_title5",checkNull(TrialData.getStrFreeTitle5())});
				//フリー⑤内容
				arySetTag.add(new String[]{"free_value5",checkNull(TrialData.getStrFreeNaiyo5())});
				//フリー⑤-出力Flg
				arySetTag.add(new String[]{"flg_free5",checkNull(TrialData.getIntFreeFlg5())});
				//フリー⑥タイトル
				arySetTag.add(new String[]{"free_title6",checkNull(TrialData.getStrFreeTitle6())});
				//フリー⑥内容
				arySetTag.add(new String[]{"free_value6",checkNull(TrialData.getStrFreeNaiyo6())});
				//フリー⑥-出力Flg
				arySetTag.add(new String[]{"flg_free6",checkNull(TrialData.getIntFreeFlg6())});
// ADD end 20121003 QP@20505 No.24

				//XMLへレコード追加
				xmlJW030.AddXmlTag("tr_shisaku", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//------------------- T132 試作リストテーブル(tr_shisaku_list) --------------------
			xmlJW030.AddXmlTag("SA490", "tr_shisaku_list");

			//-------------------------------- レコード追加  ---------------------------------
			//配合データ取得
			ArrayList addList = DataCtrl.getInstance().getTrialTblData().getAryShisakuList();
			for(int i=0; i<addList.size(); i++){
				PrototypeListData PrototypeListData = (PrototypeListData)addList.get(i);
				//試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",strUser});
				//試作CD-年
				arySetTag.add(new String[]{"nen",nen});
				//試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(PrototypeListData.getDciShisakuNum())});
				//試作SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(PrototypeListData.getIntShisakuSeq())});
				//工程CD
				arySetTag.add(new String[]{"cd_kotei",checkNull(PrototypeListData.getIntKoteiCd())});
				//工程SEQ
				arySetTag.add(new String[]{"seq_kotei",checkNull(PrototypeListData.getIntKoteiSeq())});
				//量
				arySetTag.add(new String[]{"quantity",checkNull(PrototypeListData.getDciRyo())});
				//色
				arySetTag.add(new String[]{"color",checkNull(PrototypeListData.getStrIro())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//更新日付
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});
// ADD start 20121009 QP@20505 No.24
				//工程仕上重量
				arySetTag.add(new String[]{"juryo_shiagari_seq",checkNull(PrototypeListData.getDciKouteiShiagari())});
// ADD end 20121009 QP@20505 No.24
				//XMLへレコード追加
				xmlJW030.AddXmlTag("tr_shisaku_list", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//---------------------- T133 製造工程テーブル(tr_cyuui) ------------------------
			xmlJW030.AddXmlTag("SA490", "tr_cyuui");

			//-------------------------------- レコード追加  ---------------------------------
			//配合データ取得
			ArrayList addCyuui = DataCtrl.getInstance().getTrialTblData().SearchSeizoKouteiData(0);
			for(int i=0; i<addCyuui.size(); i++){
				ManufacturingData ManufacturingData = (ManufacturingData)addCyuui.get(i);
				//試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",strUser});
				//試作CD-年
				arySetTag.add(new String[]{"nen",nen});
				//試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(ManufacturingData.getDciShisakuNum())});
				//注意事項NO
				arySetTag.add(new String[]{"no_chui",checkNull(ManufacturingData.getIntTyuiNo())});
				//注意事項
				arySetTag.add(new String[]{"chuijiko",checkNull(ManufacturingData.getStrTyuiNaiyo())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//更新日付
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XMLへレコード追加
				xmlJW030.AddXmlTag("tr_cyuui", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//---------------------- T141 原価原料テーブル(tr_genryo) ------------------------
			xmlJW030.AddXmlTag("SA490", "tr_genryo");

			//-------------------------------- レコード追加  ---------------------------------
			//原価原料データ取得
			ArrayList addGenka = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
			for(int i=0; i<addGenka.size(); i++){
				CostMaterialData costMaterialData = (CostMaterialData)addGenka.get(i);

				//　試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",strUser});
				//　試作CD-年
				arySetTag.add(new String[]{"nen",nen});
				//　試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(costMaterialData.getDciShisakuNum())});
				//　試作SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(costMaterialData.getIntShisakuSeq())});
				//　印刷flg
				arySetTag.add(new String[]{"flg_print",checkNull(costMaterialData.getIntinsatu())});
				//　重点量水相
				arySetTag.add(new String[]{"zyusui",checkNull(costMaterialData.getStrZyutenSui())});
				//　重点量油相
				arySetTag.add(new String[]{"zyuabura",checkNull(costMaterialData.getStrZyutenYu())});
				//　合計量
				arySetTag.add(new String[]{"gokei",checkNull(costMaterialData.getStrGokei())});
				//　原料費
				arySetTag.add(new String[]{"genryohi",checkNull(costMaterialData.getStrGenryohi())});
				//　原料費（1本）
				arySetTag.add(new String[]{"genryohi1",checkNull(costMaterialData.getStrGenryohiTan())});
				//　比重
				arySetTag.add(new String[]{"hiju",checkNull(costMaterialData.getStrHizyu())});
				//　容量
				arySetTag.add(new String[]{"yoryo",checkNull(costMaterialData.getStrYoryo())});
				//　入数
				arySetTag.add(new String[]{"irisu",checkNull(costMaterialData.getStrIrisu())});
				//　有効歩留
				arySetTag.add(new String[]{"yukobudomari",checkNull(costMaterialData.getStrYukoBudomari())});
				//　レベル量
				arySetTag.add(new String[]{"reberu",checkNull(costMaterialData.getStrLevel())});
				//　比重歩留
				arySetTag.add(new String[]{"hizyubudomari",checkNull(costMaterialData.getStrHizyuBudomari())});
				//　平均充填量
				arySetTag.add(new String[]{"heikinzyu",checkNull(costMaterialData.getStrZyutenAve())});
				//　1C/S原料費
				arySetTag.add(new String[]{"cs_genryo",checkNull(costMaterialData.getStrGenryohiCs())});
				//　1C/S材料費
				arySetTag.add(new String[]{"cs_zairyohi",checkNull(costMaterialData.getStrZairyohiCs())});
				//　1C/S経費
				arySetTag.add(new String[]{"cs_keihi",checkNull(costMaterialData.getStrKeihiCs())});
				//　1C/S原価計
				arySetTag.add(new String[]{"cs_genka",checkNull(costMaterialData.getStrGenkakeiCs())});
				//　1個原価計
				arySetTag.add(new String[]{"ko_genka",checkNull(costMaterialData.getStrGenkakeiTan())});
				//　1個売価
				arySetTag.add(new String[]{"ko_baika",checkNull(costMaterialData.getStrGenkakeiBai())});
				//　1個粗利率
				arySetTag.add(new String[]{"ko_riritu",checkNull(costMaterialData.getStrGenkakeiRi())});
//				//　登録者ID
//				arySetTag.add(new String[]{"id_toroku",checkNull(costMaterialData.getDciTorokuId())});
//				//　登録日付
//				arySetTag.add(new String[]{"dt_toroku",checkNull(costMaterialData.getStrTorokuHi())});
//				//　更新者ID
//				arySetTag.add(new String[]{"id_koshin",checkNull(costMaterialData.getDciKosinId())});
//				//　更新日付
//				arySetTag.add(new String[]{"dt_koshin",checkNull(costMaterialData.getStrKosinHi())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//更新日付
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XMLへレコード追加
				xmlJW030.AddXmlTag("tr_genryo", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();

			}


			//2009/09/30 TT.NISHIGAWA DEL START [排他制御は登録ロジック内にて実行]
			//--------------------------- 機能ID追加（排他制御）  -----------------------------
//			xmlJW030.AddXmlTag("JW030", "SA420");
//			//　テーブルタグ追加
//			xmlJW030.AddXmlTag("SA420", "table");
//			//　レコード追加
//			arySetTag.add(new String[]{"kubun_ziko", "1"});
//			arySetTag.add(new String[]{"kubun_haita", "1"});
//			arySetTag.add(new String[]{"id_user", strUser});
//			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
//			arySetTag.add(new String[]{"nen", strShisaku_nen});
//			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
//			xmlJW030.AddXmlTag("table", "rec", arySetTag);
//			//配列初期化
//			arySetTag.clear();
			//2009/09/30 TT.NISHIGAWA DEL END   [排他制御は登録ロジック内にて実行]

			//　XML送信
			//System.out.println("JW030送信XML===============================================================");
			//xmlJW030.dispXml();

			xcon = new XmlConnection(xmlJW030);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			//　XML受信
			xmlJW030 = xcon.getXdocRes();

//			System.out.println();
//			System.out.println("JW030受信XML===============================================================");
//			xmlJW030.dispXml();
//			System.out.println();

			//Resultチェック
			DataCtrl.getInstance().getResultData().setResultData(xmlJW030);



			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
				//機能IDの設定
				String strKinoId = "SA490";

				//全体配列取得
				ArrayList userData = xmlJW030.GetAryTag(strKinoId);

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
							//パラメータデータへ格納
							DataCtrl.getInstance().getParamData().setStrSisaku(recVal);

							//各種テーブルデータへ格納
							String shisakuCd = recVal.split("-")[0];
							String shisakuNen = recVal.split("-")[1];
							String shisakuOi = recVal.split("-")[2];
							this.setTableData(shisakuCd, shisakuNen, shisakuOi);

						}
					}
				}
			}






			if ( intChkMsg == 0 ) {
				//登録ボタン処理時

				if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
					//モードの変更
					DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0001);
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString("正常に試作登録処理が完了しました。");
				}else{
					ExceptionBase ExceptionBase  = new ExceptionBase();
					throw ExceptionBase;
				}

			} else {
				//自動保存処理時

				if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
					//モードの変更
					DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0001);

				} else {

					//エラー発生時

					//エラーメッセージを帳票用に変更
					String strErrMsg = new String(DataCtrl.getInstance().getResultData().getStrErrorMsg());

					//試作表
					if ( intChkMsg == 1 ) {
						strErrMsg = strErrMsg.replaceFirst("登録", "試作表 自動保存");

					//サンプル説明書
					} else if ( intChkMsg == 2 ) {
						strErrMsg = strErrMsg.replaceFirst("登録", "サンプル説明書 自動保存");

					//栄養計算書
					} else if ( intChkMsg == 3 ) {
						strErrMsg = strErrMsg.replaceFirst("登録", "栄養計算書 自動保存");

					//原価試算表
					} else if ( intChkMsg == 4 ) {
						strErrMsg = strErrMsg.replaceFirst("登録", "原価試算表 自動保存");

					}

					//Resultのエラーメッセージを変更
					DataCtrl.getInstance().getResultData().setStrErrorMsg(strErrMsg);

					//Exceptionをthrowする
					ExceptionBase ExceptionBase  = new ExceptionBase();
					throw ExceptionBase;

				}

			}

			//原価試算画面　試算確定サンプルNoコンボボックス 更新
			tb.getTrial5Panel().updShisanSampleNo();

			//原価試算FG編集不可設定
			DataCtrl.getInstance().getTrialTblData().setShisakuRetuFlg_initCtrl();
			setGenkaIrai_false();

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
			//登録後の編集フラグ設定＋再表示
			dispHenshuOkFg();
//add end   -------------------------------------------------------------------------------


		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){

			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("新規登録処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}


	/************************************************************************************
	 *
	 *   試作コード自動採番　XML通信処理（J010）
	 *    :  試作コード自動採番処理XMLデータ通信（J010）を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJ010() throws ExceptionBase{
		try{
			//--------------------------- 送信パラメータ格納  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

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
			arySetTag.add(new String[]{"kbn_shori", "cd_shisaku"});
			xmlJ010.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//----------------------------------- XML送信  ----------------------------------
			//System.out.println("J010送信XML===============================================================");
			//xmlJ010.dispXml();

			xcon = new XmlConnection(xmlJ010);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML受信  ----------------------------------
			xmlJ010 = xcon.getXdocRes();

			//System.out.println();
			//System.out.println("J010受信XML===============================================================");
			//xmlJ010.dispXml();

			//--------------------------------- 試作コード取得  -------------------------------
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
							//パラメータデータへ格納
							DataCtrl.getInstance().getParamData().setStrSisaku(recVal);

							//各種テーブルデータへ格納
							String shisakuCd = recVal.split("-")[0];
							String shisakuNen = recVal.split("-")[1];
							String shisakuOi = recVal.split("-")[2];
							this.setTableData(shisakuCd, shisakuNen, shisakuOi);

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
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作コードの自動採番処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   各データへ主キーを設定する
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void setTableData(String shisakuCd, String shisakuNen, String shisakuOi) throws ExceptionBase{
		try{
			/**********************************************************
			 *　T110格納
			 *********************************************************/
			//試作CD-社員CD
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuUser(new BigDecimal(shisakuCd));
			//試作CD-年
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuYear(new BigDecimal(shisakuNen));
			//試作CD-追番
			DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setDciShisakuNum(new BigDecimal(shisakuOi));

			/**********************************************************
			 *　T120格納
			 *********************************************************/
			ArrayList aryHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for(int i=0; i<aryHaigo.size(); i++){
				//試作CD-社員CD
				((MixedData)aryHaigo.get(i)).setDciShisakuUser(new BigDecimal(shisakuCd));
				//試作CD-年
				((MixedData)aryHaigo.get(i)).setDciShisakuYear(new BigDecimal(shisakuNen));
				//試作CD-追番
				((MixedData)aryHaigo.get(i)).setDciShisakuNum(new BigDecimal(shisakuOi));
			}

			/**********************************************************
			 *　T131格納
			 *********************************************************/
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			for(int i=0; i<aryRetu.size(); i++){
				//試作CD-社員CD
				((TrialData)aryRetu.get(i)).setDciShisakuUser(new BigDecimal(shisakuCd));
				//試作CD-年
				((TrialData)aryRetu.get(i)).setDciShisakuYear(new BigDecimal(shisakuNen));
				//試作CD-追番
				((TrialData)aryRetu.get(i)).setDciShisakuNum(new BigDecimal(shisakuOi));
			}


			/**********************************************************
			 *　T132格納
			 *********************************************************/
			ArrayList aryList = DataCtrl.getInstance().getTrialTblData().getAryShisakuList();
			for(int i=0; i<aryList.size(); i++){
				//試作CD-社員CD
				((PrototypeListData)aryList.get(i)).setDciShisakuUser(new BigDecimal(shisakuCd));
				//試作CD-年
				((PrototypeListData)aryList.get(i)).setDciShisakuYear(new BigDecimal(shisakuNen));
				//試作CD-追番
				((PrototypeListData)aryList.get(i)).setDciShisakuNum(new BigDecimal(shisakuOi));
			}

			/**********************************************************
			 *　T133格納
			 *********************************************************/
			ArrayList arySeizo = DataCtrl.getInstance().getTrialTblData().SearchSeizoKouteiData(0);
			for(int i=0; i<arySeizo.size(); i++){
				//試作CD-社員CD
				((ManufacturingData)arySeizo.get(i)).setDciShisakuUser(new BigDecimal(shisakuCd));
				//試作CD-年
				((ManufacturingData)arySeizo.get(i)).setDciShisakuYear(new BigDecimal(shisakuNen));
				//試作CD-追番
				((ManufacturingData)arySeizo.get(i)).setDciShisakuNum(new BigDecimal(shisakuOi));
			}

			/**********************************************************
			 *　T140格納
			 *********************************************************/


			/**********************************************************
			 *　T141格納
			 *********************************************************/
			//原価原料データ取得
			ArrayList aryGenka = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
			for(int i=0; i<aryGenka.size(); i++){
				CostMaterialData costMaterialData = (CostMaterialData)aryGenka.get(i);

				//試作CD-社員CD
				costMaterialData.setDciShisakuUser(new BigDecimal(shisakuCd));
				//試作CD-年
				costMaterialData.setDciShisakuYear(new BigDecimal(shisakuNen));
				//試作CD-追番
				costMaterialData.setDciShisakuNum(new BigDecimal(shisakuOi));

			}

		}catch(Exception e){
			e.printStackTrace();

			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("各データのキー設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{
			//テスト表示
			//DataCtrl.getInstance().getTrialTblData().dispPrototype();

		}
	}

	/************************************************************************************
	 *
	 *   登録処理用　XML通信処理（JW040）
	 *    : 初期処理XMLデータ通信（JW040）を行う
	 *   @param intChkMsg : 登録時メッセージの指定
	 *    [0:登録, 1:自動保存(試作表), 2:自動保存(サンプル説明書), 3:自動保存(栄養計算書)]
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW040(int intChkMsg) throws ExceptionBase{
		try{

			//DataCtrl.getInstance().getMessageCtrl().PrintMessageString("正常に試作登録処理が完了しました。");

			//--------------------------- 送信パラメータ格納  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();
			String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//--------------------------- 送信XMLデータ作成  ---------------------------------
			xmlJW040 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root追加  ------------------------------------
			xmlJW040.AddXmlTag("","JW040");
			arySetTag.clear();

			//------------------------- 機能ID追加（USERINFO）  ------------------------------
			xmlJW040.AddXmlTag("JW040", "USERINFO");

			//----------------------------- テーブルタグ追加  ---------------------------------
			xmlJW040.AddXmlTag("USERINFO", "table");

			//------------------------------ レコード追加  -----------------------------------
			//処理区分
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//ユーザID
			arySetTag.add(new String[]{"id_user",strUser});
			//XMLへレコード追加
			xmlJW040.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------ 機能ID追加（試作情報登録）  ---------------------------
			xmlJW040.AddXmlTag("JW040", "SA490");

			//--------------------- T110 試作品テーブル(tr_shisakuhin) ----------------------
			xmlJW040.AddXmlTag("SA490", "tr_shisakuhin");

			//-------------------------------- レコード追加  ---------------------------------
			//試作品データ取得
			PrototypeData addPrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
			//試作CD-社員CD
			arySetTag.add(new String[]{"cd_shain", checkNull(addPrototypeData.getDciShisakuUser())});
			//試作CD-年
			arySetTag.add(new String[]{"nen", checkNull(addPrototypeData.getDciShisakuYear())});
			//試作CD-追番
			arySetTag.add(new String[]{"no_oi", checkNull(addPrototypeData.getDciShisakuNum())});
			//依頼番号
			arySetTag.add(new String[]{"no_irai", checkNull(addPrototypeData.getStrIrai())});
			//品名
			arySetTag.add(new String[]{"nm_hin", checkNull(addPrototypeData.getStrHinnm())});

			//指定工場-会社CD
			if(addPrototypeData.getIntKaishacd() <= 0){ //会社コードが0以下の場合は空
				arySetTag.add(new String[]{"cd_kaisha", ""});
			}else{
				arySetTag.add(new String[]{"cd_kaisha", checkNull(addPrototypeData.getIntKaishacd())});
			}
			//指定工場-工場CD
			if(addPrototypeData.getIntKojoco() <= 0){ //工場コードが0以下の場合は空
				arySetTag.add(new String[]{"cd_kojo", ""});
			}else{
				arySetTag.add(new String[]{"cd_kojo", checkNull(addPrototypeData.getIntKojoco())});
			}

			//種別CD
			arySetTag.add(new String[]{"cd_shubetu", checkNull(addPrototypeData.getStrShubetu())});
			//種別No
			arySetTag.add(new String[]{"no_shubetu", checkNull(addPrototypeData.getStrShubetuNo())});
			//グループCD
			arySetTag.add(new String[]{"cd_group", checkNull(addPrototypeData.getIntGroupcd())});
			//チームCD
			arySetTag.add(new String[]{"cd_team", checkNull(addPrototypeData.getIntTeamcd())});
			//一括表示CD
			arySetTag.add(new String[]{"cd_ikatu", checkNull(addPrototypeData.getStrIkatu())});
			//ジャンルCD
			arySetTag.add(new String[]{"cd_genre", checkNull(addPrototypeData.getStrZyanru())});
			//ユーザCD
			arySetTag.add(new String[]{"cd_user", checkNull(addPrototypeData.getStrUsercd())});
			//特徴原料
			arySetTag.add(new String[]{"tokuchogenryo", checkNull(addPrototypeData.getStrTokutyo())});
			//用途
			arySetTag.add(new String[]{"youto", checkNull(addPrototypeData.getStrYoto())});
			//価格帯CD
			arySetTag.add(new String[]{"cd_kakaku", checkNull(addPrototypeData.getStrKakaku())});
			//担当営業CD
			arySetTag.add(new String[]{"cd_eigyo", checkNull(addPrototypeData.getStrTantoEigyo())});
			//製造方法CD
			arySetTag.add(new String[]{"cd_hoho", checkNull(addPrototypeData.getStrSeizocd())});
			//充填方法CD
			arySetTag.add(new String[]{"cd_juten", checkNull(addPrototypeData.getStrZyutencd())});
			//殺菌方法
			arySetTag.add(new String[]{"hoho_sakin", checkNull(addPrototypeData.getStrSakin())});
			//容器・包材
			arySetTag.add(new String[]{"youki", checkNull(addPrototypeData.getStrYokihozai())});
			//容量
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//seisuCheck(checkNull(addPrototypeData.getStrYoryo()), "試作データ画面 試作表③ 容量", 1);
			seisuCheck(checkNull(addPrototypeData.getStrYoryo()), "試作データ画面 基本情報 容量", 1);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"yoryo", checkNull(addPrototypeData.getStrYoryo())});

			//容量単位CD
			arySetTag.add(new String[]{"cd_tani", checkNull(addPrototypeData.getStrTani())});

			//入り数
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//seisuCheck(checkNull(addPrototypeData.getStrIrisu()), "試作データ画面 試作表③ 入り数", 1);
			seisuCheck(checkNull(addPrototypeData.getStrIrisu()), "試作データ画面 基本情報 入り数", 1);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"su_iri", checkNull(addPrototypeData.getStrIrisu())});

			//取扱温度CD
			arySetTag.add(new String[]{"cd_ondo", checkNull(addPrototypeData.getStrOndo())});
			//賞味期間
			arySetTag.add(new String[]{"shomikikan", checkNull(addPrototypeData.getStrShomi())});

			//原価
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ShosuCheck(checkNull(addPrototypeData.getStrGenka()), 2, "試作データ画面 試作表③ 原価希望", 1);
			ShosuCheck(checkNull(addPrototypeData.getStrGenka()), 2, "試作データ画面 基本情報 原価希望", 1);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"genka", checkNull(addPrototypeData.getStrGenka())});

			//売価
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ShosuCheck(checkNull(addPrototypeData.getStrBaika()), 2, "試作データ画面 試作表③ 売価希望", 1);
			ShosuCheck(checkNull(addPrototypeData.getStrBaika()), 2, "試作データ画面 基本情報 売価希望", 1);
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			arySetTag.add(new String[]{"baika", checkNull(addPrototypeData.getStrBaika())});

			//想定物量
			arySetTag.add(new String[]{"buturyo", checkNull(addPrototypeData.getStrSotei())});
			//発売時期
			arySetTag.add(new String[]{"dt_hatubai", checkNull(addPrototypeData.getStrHatubai())});
			//計画売上
			arySetTag.add(new String[]{"uriage_k", checkNull(addPrototypeData.getStrKeikakuUri())});
			//計画利益
			arySetTag.add(new String[]{"rieki_k", checkNull(addPrototypeData.getStrKeikakuRie())});
			//販売後売上
			arySetTag.add(new String[]{"uriage_h", checkNull(addPrototypeData.getStrHanbaigoUri())});
			//販売後利益
			arySetTag.add(new String[]{"rieki_h", checkNull(addPrototypeData.getStrHanbaigoRie())});
			//荷姿CD
			arySetTag.add(new String[]{"cd_nisugata", checkNull(addPrototypeData.getStrNishugata())});
			//総合ﾒﾓ
			arySetTag.add(new String[]{"memo", checkNull(addPrototypeData.getStrSogo())});
			//小数指定
			arySetTag.add(new String[]{"keta_shosu", checkNull(addPrototypeData.getStrShosu())});

			//小数指定値１
			String cd = addPrototypeData.getStrShosu();
			int val1 = 0;
			if(cd != null && cd.length()>0){
				val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(cd));
			}
			arySetTag.add(new String[]{"keta_shosu_val1", Integer.toString(val1)});


			//廃止区
			arySetTag.add(new String[]{"kbn_haishi", checkNull(addPrototypeData.getIntHaisi())});
			//排他
			arySetTag.add(new String[]{"kbn_haita", checkNull(addPrototypeData.getDciHaita())});
			//製法試作
			arySetTag.add(new String[]{"seq_shisaku", checkNull(addPrototypeData.getIntSeihoShisaku())});
			//試作メモ
			arySetTag.add(new String[]{"memo_shisaku", checkNull(addPrototypeData.getStrShisakuMemo())});
			//注意事項表示
			arySetTag.add(new String[]{"flg_chui", checkNull(addPrototypeData.getIntChuiFg())});
			//登録者ID
			arySetTag.add(new String[]{"id_toroku", checkNull(addPrototypeData.getDciTorokuid())});
			//登録日付
			arySetTag.add(new String[]{"dt_toroku", checkNull(addPrototypeData.getStrTorokuhi())});
			//更新者ID
			arySetTag.add(new String[]{"id_koshin", checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});

			//更新日付
			//UPD 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
//			arySetTag.add(new String[]{"dt_koshin", DataCtrl.getInstance().getTrialTblData().getSysDate()});
			arySetTag.add(new String[]{"dt_koshin", checkNull(addPrototypeData.getStrKosinhi())});


//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
			//工程パターン
			arySetTag.add(new String[]{"pt_kotei", checkNull(addPrototypeData.getStrPt_kotei())});
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------

			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
			arySetTag.add(new String[]{"flg_secret", checkNull(addPrototypeData.getStrSecret())});
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
//ADD 2013/06/19 ogawa 【QP@30151】No.9 start
			//販責会社CD
			if(addPrototypeData.getIntHansekicd() <= 0){ //販責会社コードが0以下の場合は空
				arySetTag.add(new String[]{"cd_hanseki", ""});
			}else{
				arySetTag.add(new String[]{"cd_hanseki", checkNull(addPrototypeData.getIntHansekicd())});
			}
//ADD 2013/06/19 ogawa 【QP@30151】No.9 start

			//XMLへレコード追加
			xmlJW040.AddXmlTag("tr_shisakuhin", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------- T120 配合テーブル(tr_haigo) -------------------------
			xmlJW040.AddXmlTag("SA490", "tr_haigo");

			//-------------------------------- レコード追加  ---------------------------------
			//配合データ取得
			ArrayList addHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for(int i=0; i<addHaigo.size(); i++){
				MixedData MixedData = (MixedData)addHaigo.get(i);
				//試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",checkNull(MixedData.getDciShisakuUser())});
				//試作CD-年
				arySetTag.add(new String[]{"nen",checkNull(MixedData.getDciShisakuYear())});
				//試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(MixedData.getDciShisakuNum())});
				//工程CD
				arySetTag.add(new String[]{"cd_kotei",checkNull(MixedData.getIntKoteiCd())});
				//工程SEQ
				arySetTag.add(new String[]{"seq_kotei",checkNull(MixedData.getIntKoteiSeq())});
				//工程名
				arySetTag.add(new String[]{"nm_kotei",checkNull(MixedData.getStrKouteiNm())});
				//工程属性
				arySetTag.add(new String[]{"zoku_kotei",checkNull(MixedData.getStrKouteiZokusei())});
				//工程順
				arySetTag.add(new String[]{"sort_kotei",checkNull(MixedData.getIntKoteiNo())});
				//原料順
				arySetTag.add(new String[]{"sort_genryo",checkNull(MixedData.getIntGenryoNo())});
				//原料CD
				arySetTag.add(new String[]{"cd_genryo",checkNull(MixedData.getStrGenryoCd())});
				//会社CD
				arySetTag.add(new String[]{"cd_kaisha",checkNull(MixedData.getIntKaishaCd())});
				//部署CD
				arySetTag.add(new String[]{"cd_busho",checkNull(MixedData.getIntBushoCd())});
				//原料名称
				arySetTag.add(new String[]{"nm_genryo",checkNull(MixedData.getStrGenryoNm())});
				//単価
				arySetTag.add(new String[]{"tanka",checkNull(MixedData.getDciTanka())});
				//歩留
				arySetTag.add(new String[]{"budomari",checkNull(MixedData.getDciBudomari())});
				//油含有率
				arySetTag.add(new String[]{"ritu_abura",checkNull(MixedData.getDciGanyuritu())});
				//酢酸
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(MixedData.getDciSakusan())});
				//食塩
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(MixedData.getDciShokuen())});
// ADD start 20121002 QP@20505 No.24
				//ＭＳＧ
				arySetTag.add(new String[]{"ritu_msg",checkNull(MixedData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24
				//総酸
				arySetTag.add(new String[]{"ritu_sousan",checkNull(MixedData.getDciSosan())});
				//色
				arySetTag.add(new String[]{"color",checkNull(MixedData.getStrIro())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(MixedData.getDciTorokuId())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",checkNull(MixedData.getStrTorokuHi())});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//更新日付
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XMLへレコード追加
				xmlJW040.AddXmlTag("tr_haigo", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//----------------------- T131 試作テーブル(tr_shisaku) -------------------------
			xmlJW040.AddXmlTag("SA490", "tr_shisaku");

			//-------------------------------- レコード追加  ---------------------------------
			//試作列データ取得
			ArrayList addRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			for(int i=0; i<addRetu.size(); i++){
				TrialData TrialData = (TrialData)addRetu.get(i);
				//試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",checkNull(TrialData.getDciShisakuUser())});
				//試作CD-年
				arySetTag.add(new String[]{"nen",checkNull(TrialData.getDciShisakuYear())});
				//試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(TrialData.getDciShisakuNum())});
				//試作SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(TrialData.getIntShisakuSeq())});
				//試作表示順
				arySetTag.add(new String[]{"sort_shisaku",checkNull(TrialData.getIntHyojiNo())});
				//注意事項NO
				arySetTag.add(new String[]{"no_chui",checkNull(TrialData.getStrTyuiNo())});
				//サンプルNO（名称）
				arySetTag.add(new String[]{"nm_sample",checkNull(TrialData.getStrSampleNo())});
				//メモ
				arySetTag.add(new String[]{"memo",checkNull(TrialData.getStrMemo())});
				//印刷Flg
				arySetTag.add(new String[]{"flg_print",checkNull(TrialData.getIntInsatuFlg())});
				//自動計算Flg
				arySetTag.add(new String[]{"flg_auto",checkNull(TrialData.getIntZidoKei())});
				//原価試算No
				arySetTag.add(new String[]{"no_shisan",checkNull(TrialData.getIntGenkaShisan())});
				//製法No-1
				arySetTag.add(new String[]{"no_seiho1",checkNull(TrialData.getStrSeihoNo1())});
				//製法No-2
				arySetTag.add(new String[]{"no_seiho2",checkNull(TrialData.getStrSeihoNo2())});
				//製法No-3
				arySetTag.add(new String[]{"no_seiho3",checkNull(TrialData.getStrSeihoNo3())});
				//製法No-4
				arySetTag.add(new String[]{"no_seiho4",checkNull(TrialData.getStrSeihoNo4())});
				//製法No-5
				arySetTag.add(new String[]{"no_seiho5",checkNull(TrialData.getStrSeihoNo5())});
				//総酸
				arySetTag.add(new String[]{"ritu_sousan",checkNull(TrialData.getDciSosan())});
				//総酸-出力Flg
				arySetTag.add(new String[]{"flg_sousan",checkNull(TrialData.getIntSosanFlg())});
				//食塩
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(TrialData.getDciShokuen())});
				//食塩-出力Flg
				arySetTag.add(new String[]{"flg_shokuen",checkNull(TrialData.getIntShokuenFlg())});
// ADD start 20121002 QP@20505 No.24
				//ＭＳＧ
				arySetTag.add(new String[]{"ritu_msg",checkNull(TrialData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24
				//水相中酸度
				arySetTag.add(new String[]{"sando_suiso",checkNull(TrialData.getDciSuiSando())});
				//水相中酸度-出力Flg
				arySetTag.add(new String[]{"flg_sando_suiso",checkNull(TrialData.getIntSuiSandoFlg())});
				//水相中食塩
				arySetTag.add(new String[]{"shokuen_suiso",checkNull(TrialData.getDciSuiShokuen())});
				//水相中食塩-出力Flg
				arySetTag.add(new String[]{"flg_shokuen_suiso",checkNull(TrialData.getIntSuiShokuenFlg())});
				//水相中酢酸
				arySetTag.add(new String[]{"sakusan_suiso",checkNull(TrialData.getDciSuiSakusan())});
				//水相中酢酸-出力Flg
				arySetTag.add(new String[]{"flg_sakusan_suiso",checkNull(TrialData.getIntSuiSandoFlg())});
				//糖度
				arySetTag.add(new String[]{"toudo",checkNull(TrialData.getStrToudo())});
				//糖度-出力Flg
				arySetTag.add(new String[]{"flg_toudo",checkNull(TrialData.getIntToudoFlg())});
				//粘度
				arySetTag.add(new String[]{"nendo",checkNull(TrialData.getStrNendo())});
				//粘度-出力Flg
				arySetTag.add(new String[]{"flg_nendo",checkNull(TrialData.getIntNendoFlg())});
				//温度
				arySetTag.add(new String[]{"ondo",checkNull(TrialData.getStrOndo())});
				//温度-出力Flg
				arySetTag.add(new String[]{"flg_ondo",checkNull(TrialData.getIntOndoFlg())});
				//PH
				arySetTag.add(new String[]{"ph",checkNull(TrialData.getStrPh())});
				//PH - 出力Flg
				arySetTag.add(new String[]{"flg_ph",checkNull(TrialData.getIntPhFlg())});
				//総酸：分析
				arySetTag.add(new String[]{"ritu_sousan_bunseki",checkNull(TrialData.getStrSosanBunseki())});
				//総酸：分析-出力Flg
				arySetTag.add(new String[]{"flg_sousan_bunseki",checkNull(TrialData.getIntSosanBunsekiFlg())});
				//食塩：分析
				arySetTag.add(new String[]{"ritu_shokuen_bunseki",checkNull(TrialData.getStrShokuenBunseki())});
				//食塩：分析-出力Flg
				arySetTag.add(new String[]{"flg_shokuen_bunseki",checkNull(TrialData.getIntShokuenBunsekiFlg())});
				//比重
				arySetTag.add(new String[]{"hiju",checkNull(TrialData.getStrHizyu())});
				//比重-出力Flg
				arySetTag.add(new String[]{"flg_hiju",checkNull(TrialData.getIntHizyuFlg())});
				//水分活性
				arySetTag.add(new String[]{"suibun_kasei",checkNull(TrialData.getStrSuibun())});
				//水分活性-出力Flg
				arySetTag.add(new String[]{"flg_suibun_kasei",checkNull(TrialData.getIntSuibunFlg())});
				//アルコール
				arySetTag.add(new String[]{"alcohol",checkNull(TrialData.getStrArukoru())});
				//アルコール-出力Flg
				arySetTag.add(new String[]{"flg_alcohol",checkNull(TrialData.getIntArukoruFlg())});
				//作成メモ
				arySetTag.add(new String[]{"memo_sakusei",checkNull(TrialData.getStrSakuseiMemo())});
				//作成メモ-出力Flg
				arySetTag.add(new String[]{"flg_memo",checkNull(TrialData.getIntSakuseiMemoFlg())});
				//評価
				arySetTag.add(new String[]{"hyoka",checkNull(TrialData.getStrHyoka())});
				//評価-出力Flg
				arySetTag.add(new String[]{"flg_hyoka",checkNull(TrialData.getIntHyokaFlg())});
				//フリー①タイトル
				arySetTag.add(new String[]{"free_title1",checkNull(TrialData.getStrFreeTitle1())});
				//フリー①内容
				arySetTag.add(new String[]{"free_value1",checkNull(TrialData.getStrFreeNaiyo1())});
				//フリー①-出力Flg
				arySetTag.add(new String[]{"flg_free1",checkNull(TrialData.getIntFreeFlg())});
				//フリー②タイトル
				arySetTag.add(new String[]{"free_title2",checkNull(TrialData.getStrFreeTitle2())});
				//フリー②内容
				arySetTag.add(new String[]{"free_value2",checkNull(TrialData.getStrFreeNaiyo2())});
				//フリー②-出力Flg
				arySetTag.add(new String[]{"flg_free2",checkNull(TrialData.getIntFreeFl2())});
				//フリー③タイトル
				arySetTag.add(new String[]{"free_title3",checkNull(TrialData.getStrFreeTitle3())});
				//フリー③内容
				arySetTag.add(new String[]{"free_value3",checkNull(TrialData.getStrFreeNaiyo3())});
				//フリー③-出力Flg
				arySetTag.add(new String[]{"flg_free3",checkNull(TrialData.getIntFreeFl3())});
				//試作日付
				arySetTag.add(new String[]{"dt_shisaku",checkNull(TrialData.getStrShisakuHi())});
				//仕上重量
				arySetTag.add(new String[]{"juryo_shiagari_g",checkNull(TrialData.getDciShiagari())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(TrialData.getDciTorokuId())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",checkNull(TrialData.getStrTorokuHi())});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//更新日付
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});
				//原価依頼フラグ
				arySetTag.add(new String[]{"flg_shisanIrai",Integer.toString(TrialData.getFlg_shisanIrai())});
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
				//計算式
				arySetTag.add(new String[]{"siki_keisan",checkNull(TrialData.getStrKeisanSiki())});
//add end   -------------------------------------------------------------------------------
//2011/04/12 QP@10181_No.67 TT Nishigawa Change Start -------------------------
				//キャンセルFG
				arySetTag.add(new String[]{"flg_cancel",checkNull(TrialData.getFlg_cancel())});
//2011/04/12 QP@10181_No.67 TT Nishigawa Change Start -------------------------
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
				//水相比重
				arySetTag.add(new String[]{"hiju_sui",checkNull(TrialData.getStrHiju_sui())});
				//水相比重-出力Flg
				arySetTag.add(new String[]{"flg_hiju_sui",checkNull(TrialData.getIntHiju_sui_fg())});
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------
// ADD start 20121003 QP@20505 No.24
				//フリー粘度 タイトル
				arySetTag.add(new String[]{"freetitle_nendo",checkNull(TrialData.getStrFreeTitleNendo())});
				//フリー粘度 内容
				arySetTag.add(new String[]{"free_nendo",checkNull(TrialData.getStrFreeNendo())});
				//フリー粘度 -出力Flg
				arySetTag.add(new String[]{"flg_freeNendo",checkNull(TrialData.getIntFreeNendoFlg())});
				//フリー温度 タイトル
				arySetTag.add(new String[]{"freetitle_ondo",checkNull(TrialData.getStrFreeTitleOndo())});
				//フリー温度 内容
				arySetTag.add(new String[]{"free_ondo",checkNull(TrialData.getStrFreeOndo())});
				//フリー温度 -出力Flg
				arySetTag.add(new String[]{"flg_freeOndo",checkNull(TrialData.getIntFreeOndoFlg())});
				//フリー水分活性 タイトル
				arySetTag.add(new String[]{"freetitle_suibun_kasei",checkNull(TrialData.getStrFreeTitleSuibunKasei())});
				//フリー水分活性 内容
				arySetTag.add(new String[]{"free_suibun_kasei",checkNull(TrialData.getStrFreeSuibunKasei())});
				//フリー水分活性 -出力Flg
				arySetTag.add(new String[]{"flg_freeSuibunKasei",checkNull(TrialData.getIntFreeSuibunKaseiFlg())});
				//フリーアルコール タイトル
				arySetTag.add(new String[]{"freetitle_alcohol",checkNull(TrialData.getStrFreeTitleAlchol())});
				//フリーアルコール 内容
				arySetTag.add(new String[]{"free_alcohol",checkNull(TrialData.getStrFreeAlchol())});
				//フリーアルコール -出力Flg
				arySetTag.add(new String[]{"flg_freeAlchol",checkNull(TrialData.getIntFreeAlcholFlg())});
				//実効酢酸濃度
				arySetTag.add(new String[]{"jikkoSakusanNodo",checkNull(TrialData.getDciJikkoSakusanNodo())});
				//実効酢酸濃度 -出力Flg
				arySetTag.add(new String[]{"flg_jikkoSakusanNodo",checkNull(TrialData.getIntJikkoSakusanNodoFlg())});
				//水相中ＭＳＧ
				arySetTag.add(new String[]{"msg_suiso",checkNull(TrialData.getDciSuisoMSG())});
				//水相中ＭＳＧ-出力Flg
				arySetTag.add(new String[]{"flg_msg_suiso",checkNull(TrialData.getIntSuisoMSGFlg())});
				//フリー④タイトル
				arySetTag.add(new String[]{"free_title4",checkNull(TrialData.getStrFreeTitle4())});
				//フリー④内容
				arySetTag.add(new String[]{"free_value4",checkNull(TrialData.getStrFreeNaiyo4())});
				//フリー④-出力Flg
				arySetTag.add(new String[]{"flg_free4",checkNull(TrialData.getIntFreeFlg4())});
				//フリー⑤タイトル
				arySetTag.add(new String[]{"free_title5",checkNull(TrialData.getStrFreeTitle5())});
				//フリー⑤内容
				arySetTag.add(new String[]{"free_value5",checkNull(TrialData.getStrFreeNaiyo5())});
				//フリー⑤-出力Flg
				arySetTag.add(new String[]{"flg_free5",checkNull(TrialData.getIntFreeFlg5())});
				//フリー⑥タイトル
				arySetTag.add(new String[]{"free_title6",checkNull(TrialData.getStrFreeTitle6())});
				//フリー⑥内容
				arySetTag.add(new String[]{"free_value6",checkNull(TrialData.getStrFreeNaiyo6())});
				//フリー⑥-出力Flg
				arySetTag.add(new String[]{"flg_free6",checkNull(TrialData.getIntFreeFlg6())});
// ADD end 20121003 QP@20505 No.24

				//XMLへレコード追加
				xmlJW040.AddXmlTag("tr_shisaku", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//------------------- T132 試作リストテーブル(tr_shisaku_list) --------------------
			xmlJW040.AddXmlTag("SA490", "tr_shisaku_list");

			//-------------------------------- レコード追加  ---------------------------------
			//配合データ取得
			ArrayList addList = DataCtrl.getInstance().getTrialTblData().getAryShisakuList();
			for(int i=0; i<addList.size(); i++){
				PrototypeListData PrototypeListData = (PrototypeListData)addList.get(i);
				//試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",checkNull(PrototypeListData.getDciShisakuUser())});
				//試作CD-年
				arySetTag.add(new String[]{"nen",checkNull(PrototypeListData.getDciShisakuYear())});
				//試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(PrototypeListData.getDciShisakuNum())});
				//試作SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(PrototypeListData.getIntShisakuSeq())});
				//工程CD
				arySetTag.add(new String[]{"cd_kotei",checkNull(PrototypeListData.getIntKoteiCd())});
				//工程SEQ
				arySetTag.add(new String[]{"seq_kotei",checkNull(PrototypeListData.getIntKoteiSeq())});
				//量
				arySetTag.add(new String[]{"quantity",checkNull(PrototypeListData.getDciRyo())});
				//色
				arySetTag.add(new String[]{"color",checkNull(PrototypeListData.getStrIro())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(PrototypeListData.getDciTorokuId())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",checkNull(PrototypeListData.getStrTorokuHi())});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//更新日付
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});
// ADD start 20121009 QP@20505 No.24
				//工程仕上重量
				arySetTag.add(new String[]{"juryo_shiagari_seq",checkNull(PrototypeListData.getDciKouteiShiagari())});
// ADD end 20121009 QP@20505 No.24
				//XMLへレコード追加
				xmlJW040.AddXmlTag("tr_shisaku_list", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//---------------------- T133 製造工程テーブル(tr_cyuui) ------------------------
			xmlJW040.AddXmlTag("SA490", "tr_cyuui");

			//-------------------------------- レコード追加  ---------------------------------
			//配合データ取得
			ArrayList addCyuui = DataCtrl.getInstance().getTrialTblData().SearchSeizoKouteiData(0);
			for(int i=0; i<addCyuui.size(); i++){
				ManufacturingData ManufacturingData = (ManufacturingData)addCyuui.get(i);
				//試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",checkNull(ManufacturingData.getDciShisakuUser())});
				//試作CD-年
				arySetTag.add(new String[]{"nen",checkNull(ManufacturingData.getDciShisakuYear())});
				//試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(ManufacturingData.getDciShisakuNum())});
				//注意事項NO
				arySetTag.add(new String[]{"no_chui",checkNull(ManufacturingData.getIntTyuiNo())});
				//注意事項
				arySetTag.add(new String[]{"chuijiko",checkNull(ManufacturingData.getStrTyuiNaiyo())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(ManufacturingData.getDciTorokuId())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",checkNull(ManufacturingData.getStrTorokuHi())});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//更新日付
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XMLへレコード追加
				xmlJW040.AddXmlTag("tr_cyuui", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//---------------------- T141 原価原料テーブル(tr_genryo) ------------------------
			xmlJW040.AddXmlTag("SA490", "tr_genryo");

			//-------------------------------- レコード追加  ---------------------------------
			//原価原料データ取得
			ArrayList addGenka = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
			for(int i=0; i<addGenka.size(); i++){
				CostMaterialData costMaterialData = (CostMaterialData)addGenka.get(i);

				//　試作CD-社員CD
				arySetTag.add(new String[]{"cd_shain",checkNull(costMaterialData.getDciShisakuUser())});
				//　試作CD-年
				arySetTag.add(new String[]{"nen",checkNull(costMaterialData.getDciShisakuYear())});
				//　試作CD-追番
				arySetTag.add(new String[]{"no_oi",checkNull(costMaterialData.getDciShisakuNum())});
				//　試作SEQ
				arySetTag.add(new String[]{"seq_shisaku",checkNull(costMaterialData.getIntShisakuSeq())});
				//　印刷flg
				arySetTag.add(new String[]{"flg_print",checkNull(costMaterialData.getIntinsatu())});
				//　重点量水相
				arySetTag.add(new String[]{"zyusui",checkNull(costMaterialData.getStrZyutenSui())});
				//　重点量油相
				arySetTag.add(new String[]{"zyuabura",checkNull(costMaterialData.getStrZyutenYu())});
				//　合計量
				arySetTag.add(new String[]{"gokei",checkNull(costMaterialData.getStrGokei())});
				//　原料費
				arySetTag.add(new String[]{"genryohi",checkNull(costMaterialData.getStrGenryohi())});
				//　原料費（1本）
				arySetTag.add(new String[]{"genryohi1",checkNull(costMaterialData.getStrGenryohiTan())});
				//　比重
				arySetTag.add(new String[]{"hiju",checkNull(costMaterialData.getStrHizyu())});
				//　容量
				arySetTag.add(new String[]{"yoryo",checkNull(costMaterialData.getStrYoryo())});
				//　入数
				arySetTag.add(new String[]{"irisu",checkNull(costMaterialData.getStrIrisu())});
				//　有効歩留
				arySetTag.add(new String[]{"yukobudomari",checkNull(costMaterialData.getStrYukoBudomari())});
				//　レベル量
				arySetTag.add(new String[]{"reberu",checkNull(costMaterialData.getStrLevel())});
				//　比重歩留
				arySetTag.add(new String[]{"hizyubudomari",checkNull(costMaterialData.getStrHizyuBudomari())});
				//　平均充填量
				arySetTag.add(new String[]{"heikinzyu",checkNull(costMaterialData.getStrZyutenAve())});
				//　1C/S原料費
				arySetTag.add(new String[]{"cs_genryo",checkNull(costMaterialData.getStrGenryohiCs())});
				//　1C/S材料費
				arySetTag.add(new String[]{"cs_zairyohi",checkNull(costMaterialData.getStrZairyohiCs())});
				//　1C/S経費
				arySetTag.add(new String[]{"cs_keihi",checkNull(costMaterialData.getStrKeihiCs())});
				//　1C/S原価計
				arySetTag.add(new String[]{"cs_genka",checkNull(costMaterialData.getStrGenkakeiCs())});
				//　1個原価計
				arySetTag.add(new String[]{"ko_genka",checkNull(costMaterialData.getStrGenkakeiTan())});
				//　1個売価
				arySetTag.add(new String[]{"ko_baika",checkNull(costMaterialData.getStrGenkakeiBai())});
				//　1個粗利率
				arySetTag.add(new String[]{"ko_riritu",checkNull(costMaterialData.getStrGenkakeiRi())});
//				//　登録者ID
//				arySetTag.add(new String[]{"id_toroku",checkNull(costMaterialData.getDciTorokuId())});
//				//　登録日付
//				arySetTag.add(new String[]{"dt_toroku",checkNull(costMaterialData.getStrTorokuHi())});
//				//　更新者ID
//				arySetTag.add(new String[]{"id_koshin",checkNull(costMaterialData.getDciKosinId())});
//				//　更新日付
//				arySetTag.add(new String[]{"dt_koshin",checkNull(costMaterialData.getStrKosinHi())});
				//登録者ID
				arySetTag.add(new String[]{"id_toroku",checkNull(costMaterialData.getDciTorokuId())});
				//登録日付
				arySetTag.add(new String[]{"dt_toroku",checkNull(costMaterialData.getStrTorokuHi())});
				//更新者ID
				arySetTag.add(new String[]{"id_koshin",checkNull(DataCtrl.getInstance().getUserMstData().getDciUserid())});
				//更新日付
				arySetTag.add(new String[]{"dt_koshin",DataCtrl.getInstance().getTrialTblData().getSysDate()});

				//XMLへレコード追加
				xmlJW040.AddXmlTag("tr_genryo", "rec", arySetTag);

				//配列初期化
				arySetTag.clear();
			}

			//　XML送信
//			System.out.println("JW040送信XML===============================================================");
//			xmlJW040.dispXml();

			xcon = new XmlConnection(xmlJW040);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//　XML受信
			xmlJW040 = xcon.getXdocRes();

//			System.out.println();
//			System.out.println("JW040受信XML===============================================================");
//			xmlJW040.dispXml();
//			System.out.println();

			//------------------------------- Resultデータチェック -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW040);

			if ( intChkMsg == 0 ) {
				//登録ボタン処理時

				if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
					//モードの変更
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString("正常に試作登録処理が完了しました。");
				}else{
					ExceptionBase ExceptionBase  = new ExceptionBase();
					throw ExceptionBase;
				}

			} else {
				//自動保存処理時

				if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")) {
					//モードの変更

				}else{
					//エラー発生時

					//エラーメッセージを帳票用に変更
					String strErrMsg = new String(DataCtrl.getInstance().getResultData().getStrErrorMsg());

					//試作表
					if ( intChkMsg == 1 ) {
						strErrMsg = strErrMsg.replaceFirst("登録", "試作表 自動保存");

					//サンプル説明書
					} else if ( intChkMsg == 2 ) {
						strErrMsg = strErrMsg.replaceFirst("登録", "サンプル説明書 自動保存");

					//栄養計算書
					} else if ( intChkMsg == 3 ) {
						strErrMsg = strErrMsg.replaceFirst("登録", "栄養計算書 自動保存");

					//原価試算表
					} else if ( intChkMsg == 4 ) {
						strErrMsg = strErrMsg.replaceFirst("登録", "原価試算表 自動保存");

					}

					//Resultのエラーメッセージを変更
					DataCtrl.getInstance().getResultData().setStrErrorMsg(strErrMsg);

					//Exceptionをthrowする
					ExceptionBase ExceptionBase  = new ExceptionBase();
					throw ExceptionBase;

				}

			}

			//原価試算画面　試算確定サンプルNoコンボボックス 更新
			tb.getTrial5Panel().updShisanSampleNo();

			//原価試算FG編集不可設定
			DataCtrl.getInstance().getTrialTblData().setShisakuRetuFlg_initCtrl();
			setGenkaIrai_false();

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
			//登録後の編集フラグ設定＋再表示
			dispHenshuOkFg();
//add end   -------------------------------------------------------------------------------

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("編集登録処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   初期処理用　XML通信処理（JW010）
	 *    : 初期処理XMLデータ通信（JW010）を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void conJW010() throws ExceptionBase{
		try{
			//----------------------------- 送信パラメータ格納  -------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strShisaku_user = DataCtrl.getInstance().getParamData().getStrSisaku_user();
			String strShisaku_nen = DataCtrl.getInstance().getParamData().getStrSisaku_nen();
			String strShisaku_oi = DataCtrl.getInstance().getParamData().getStrSisaku_oi();

//【QP@10181_No.11】
//試作コピーモード編集パターン追加 start ----------------------------------------------------
			//String strGamenId = DataCtrl.getInstance().getParamData().getStrMode();

			//画面ID初期化
			String strGamenId = "";
			//起動モードが試作コピーの場合
			if(DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0004)){
				//画面IDに「詳細」を設定
				strGamenId = JwsConstManager.JWS_MODE_0001;
			}
			//起動モードが試作コピーでない場合
			else{
				//現在の起動モードを設定
				strGamenId = DataCtrl.getInstance().getParamData().getStrMode();
			}
//試作コピーモード編集パターン追加 end ----------------------------------------------------


			//----------------------------- 送信XMLデータ作成  ------------------------------
			xmlJW010 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//--------------------------------- Root追加  ---------------------------------
			xmlJW010.AddXmlTag("","JW010");
			arySetTag.clear();

			//--------------------------- 機能ID追加（USEERINFO）  --------------------------
			xmlJW010.AddXmlTag("JW010", "USERINFO");
			//　テーブルタグ追加
			xmlJW010.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW010.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------- 機能ID追加（JWSリテラル検索）  -------------------------
			this.addLiteralDataXml(strUser, strGamenId);

			//--------------------------- 機能ID追加（会社検索）  ----------------------------
			xmlJW010.AddXmlTag("JW010", "SA140");
			//　テーブルタグ追加
			xmlJW010.AddXmlTag("SA140", "table");
			//　レコード追加
			arySetTag.add(new String[]{"id_user",strUser});
			arySetTag.add(new String[]{"id_gamen", strGamenId});

			xmlJW010.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//--------------------------- 機能ID追加（排他制御）  -----------------------------
			xmlJW010.AddXmlTag("JW010", "SA420");
			//　テーブルタグ追加
			xmlJW010.AddXmlTag("SA420", "table");
			//レコード追加

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
//			//新規モードの場合 or 参照モードの場合
//			if(DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0002)
//					|| DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0000) ){

			//新規モードの場合 or 参照モードの場合 or 試作コピーモードの場合
			if(DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0002)
					|| DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0000)
					|| DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0004) ){

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end

				arySetTag.add(new String[]{"kubun_ziko", "0"});
				arySetTag.add(new String[]{"kubun_haita", "1"});
				arySetTag.add(new String[]{"id_user", strUser});
				arySetTag.add(new String[]{"cd_shain", strShisaku_user});
				arySetTag.add(new String[]{"nen", strShisaku_nen});
				arySetTag.add(new String[]{"no_oi", strShisaku_oi});
				xmlJW010.AddXmlTag("table", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}else{
				arySetTag.add(new String[]{"kubun_ziko", "1"});
				arySetTag.add(new String[]{"kubun_haita", "1"});
				arySetTag.add(new String[]{"id_user", strUser});
				arySetTag.add(new String[]{"cd_shain", strShisaku_user});
				arySetTag.add(new String[]{"nen", strShisaku_nen});
				arySetTag.add(new String[]{"no_oi", strShisaku_oi});
				xmlJW010.AddXmlTag("table", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//------------------------- 機能ID追加（試作データ検索）  ---------------------------
			xmlJW010.AddXmlTag("JW010", "SA480");
			//　テーブルタグ追加
			xmlJW010.AddXmlTag("SA480", "table");
			//　レコード追加
			arySetTag.add(new String[]{"cd_shain", strShisaku_user});
			arySetTag.add(new String[]{"nen", strShisaku_nen});
			arySetTag.add(new String[]{"no_oi", strShisaku_oi});
			xmlJW010.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------ 機能ID追加（製造担当会社検索）  -------------------------
			xmlJW010.AddXmlTag("JW010", "SA210");
			//　テーブルタグ追加
			xmlJW010.AddXmlTag("SA210", "table");
			//　レコード追加
			arySetTag.add(new String[]{"id_user", strUser});
			xmlJW010.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();


			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("JW010送信XML===============================================================");
//			xmlJW010.dispXml();
			xcon = new XmlConnection(xmlJW010);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML受信  ----------------------------------
			xmlJW010 = xcon.getXdocRes();

//			System.out.println();
//			System.out.println("JW010受信XML===============================================================");
//			xmlJW010.dispXml();
//			System.out.println();

			//テストXMLデータ
			//xmlJW010 = new XmlData(new File("src/main/JW010.xml"));

			//------------------------------- Resusltデータ設定 -------------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW010);

			//------------------------------- リテラルデータ設定  ------------------------------
			this.setLiteralData();

//ADD 2013/06/21 ogawa 【QP@30151】No.9 start
			//-------------------------------- 販責会社データ設定  --------------------------------
			DataCtrl.getInstance().getHansekiData().setHansekiData(xmlJW010);
//ADD 2013/06/21 ogawa 【QP@30151】No.9 end
			//-------------------------------- 会社データ設定  --------------------------------
			DataCtrl.getInstance().getKaishaData().setKaishaData(xmlJW010);

			//---------------------------- ユーザマスタデータ設定  -----------------------------
			DataCtrl.getInstance().getUserMstData().setSeizoData(xmlJW010);

			//2010/02/25 NAKAMURA ADD START----------------------------------------------
			//---------------------------- 排他ユーザデータ設定  -----------------------------
			DataCtrl.getInstance().getUserMstData().setHaitaUserData(xmlJW010);
			//2010/02/25 NAKAMURA ADD END------------------------------------------------

			//---------------------------------- 排他チェック ----------------------------------
			if(!DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0000)){
				//機能IDの設定
				String strKinoId = "SA420";

				//全体配列取得
				ArrayList aryData = xmlJW010.GetAryTag(strKinoId);

				//機能配列取得
				ArrayList kinoData = (ArrayList)aryData.get(0);

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
						if ( recNm == "kekka_haita" ) {
							//排他処理失敗時
							if(recVal.equals("false")){

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
								//表示メッセージ設定
								String sanshoMsg = "";

								//開かれた時のモードを確認：詳細の場合
								if(DataCtrl.getInstance().getParamData().getStrMode().equals(JwsConstManager.JWS_MODE_0001)){
									//試作コピーモードに設定
									DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0004);
									sanshoMsg = "下記ユーザが編集中です。試作コピーモードで起動します。";
								}
								else{
									//参照モードに設定
									DataCtrl.getInstance().getParamData().setStrMode(JwsConstManager.JWS_MODE_0000);
									sanshoMsg = "下記ユーザが編集中です。参照モードで起動します。";
								}

								//2010/05/19　シサクイック（原価）要望【案件No9】排他情報の表示　TT.NISHIGAWA　START
								//DataCtrl.getInstance().getMessageCtrl().PrintMessageString("他ユーザが編集中です。参照モードで起動します。");
								sanshoMsg = sanshoMsg + "\n";
								sanshoMsg = sanshoMsg + "\n会社：" + DataCtrl.getInstance().getUserMstData().getStrHaitaKaishanm();
								sanshoMsg = sanshoMsg + "\n部署：" + DataCtrl.getInstance().getUserMstData().getStrHaitaBushonm();
								sanshoMsg = sanshoMsg + "\n氏名：" + DataCtrl.getInstance().getUserMstData().getStrHaitaShimei();
								//メッセージ表示
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(sanshoMsg);
								//2010/05/19　シサクイック（原価）要望【案件No9】排他情報の表示　TT.NISHIGAWA　END
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end
							}
						}
					}
				}
			}


			//---------------------------- 試作テーブルデータ設定  -----------------------------
			//モード取得
			String mode = DataCtrl.getInstance().getParamData().getStrMode();

			//【QP@10181_No.11】
			//試作コピーモード編集パターン追加 -----start

			//モード：参照or詳細or製法コピーの場合
			//if(mode.equals(JwsConstManager.JWS_MODE_0000) || mode.equals(JwsConstManager.JWS_MODE_0001) || mode.equals(JwsConstManager.JWS_MODE_0003)){
			//	DataCtrl.getInstance().getTrialTblData().setTraialData(xmlJW010);
			if(mode.equals(JwsConstManager.JWS_MODE_0000) || mode.equals(JwsConstManager.JWS_MODE_0001) || mode.equals(JwsConstManager.JWS_MODE_0003) || mode.equals(JwsConstManager.JWS_MODE_0004)){
				DataCtrl.getInstance().getTrialTblData().setTraialData(xmlJW010);

			//試作コピーモード編集パターン追加 -----end

			//モード：新規の場合
			}else{
				DataCtrl.getInstance().getTrialTblData().setTraialData(0);
			}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4,8
			//コンスト値設定
			DataCtrl.getInstance().getTrialTblData().setConstData(xmlJW010);
//add end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
			//編集可能フラグ設定（初期表示時）
			DataCtrl.getInstance().getTrialTblData().setShisakuListHenshuOkFg();
//add end   -------------------------------------------------------------------------------


		}catch(ExceptionBase ex){
			ex.printStackTrace();
			throw ex;

		}catch(Exception e){
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("初期表示のデータ取得に失敗しました");
			ex.setStrErrShori(this.getClass().toString());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

			//DataCtrl.getInstance().getTrialTblData().dispTrial();
		}

	}

	/************************************************************************************
	 *
	 * リテラルデータを送信用XMLに格納
	 * @param strUserId : ユーザID
	 * @param strGamenId : 画面ID
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void addLiteralDataXml(String strUserId, String strGamenId) throws ExceptionBase {
		String[] id_user = new String[2];
		String[] id_gamen = new String[2];
		ArrayList arySetTag = new ArrayList();

		//　【SA600～SA780 : JWSリテラル検索】 機能ID追加
		for ( int i=0; i<19; i++ ) {

			String strKinoId = "SA" + (600+(i*10));
			xmlJW010.AddXmlTag("JW010", strKinoId);

			//　テーブルタグ追加
			xmlJW010.AddXmlTag(strKinoId, "table");

			//　レコード追加
			id_user = new String[]{"id_user", strUserId};
			id_gamen = new String[]{"id_gamen",strGamenId};
			arySetTag.add(id_user);
			arySetTag.add(id_gamen);
			xmlJW010.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();
		}

		//　【SA850 : JWSリテラル検索】 機能ID追加
		xmlJW010.AddXmlTag("JW010", "SA850");

		//　テーブルタグ追加
		xmlJW010.AddXmlTag("SA850", "table");

		//　レコード追加
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start

		//　【SA900 : JWSリテラル検索（工程パターン）】 機能ID追加
		xmlJW010.AddXmlTag("JW010", "SA900");
		//　テーブルタグ追加
		xmlJW010.AddXmlTag("SA900", "table");
		//　レコード追加
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();


		//　【SA910 : JWSリテラル検索（製品比重）】 機能ID追加
		xmlJW010.AddXmlTag("JW010", "SA910");
		//　テーブルタグ追加
		xmlJW010.AddXmlTag("SA910", "table");
		//　レコード追加
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();

		//　【SA920 : JWSリテラル検索（油相比重）】 機能ID追加
		xmlJW010.AddXmlTag("JW010", "SA920");
		//　テーブルタグ追加
		xmlJW010.AddXmlTag("SA920", "table");
		//　レコード追加
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();

		//　【SA930 : JWSリテラル検索（調味料１液タイプ　工程属性）】 機能ID追加
		xmlJW010.AddXmlTag("JW010", "SA930");
		//　テーブルタグ追加
		xmlJW010.AddXmlTag("SA930", "table");
		//　レコード追加
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();

		//　【SA940 : JWSリテラル検索（調味料２液タイプ　工程属性）】 機能ID追加
		xmlJW010.AddXmlTag("JW010", "SA940");
		//　テーブルタグ追加
		xmlJW010.AddXmlTag("SA940", "table");
		//　レコード追加
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();

		//　【SA950 : JWSリテラル検索（その他・加食タイプ　工程属性）】 機能ID追加
		xmlJW010.AddXmlTag("JW010", "SA950");
		//　テーブルタグ追加
		xmlJW010.AddXmlTag("SA950", "table");
		//　レコード追加
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20121003 QP@20505 No.24
		//　【SA960 : JWSリテラル検索（実効酢酸濃度）】 機能ID追加
		xmlJW010.AddXmlTag("JW010", "SA960");
		//　テーブルタグ追加
		xmlJW010.AddXmlTag("SA960", "table");
		//　レコード追加
		id_user = new String[]{"id_user", strUserId};
		id_gamen = new String[]{"id_gamen",strGamenId};
		arySetTag.add(id_user);
		arySetTag.add(id_gamen);
		xmlJW010.AddXmlTag("table", "rec", arySetTag);
		arySetTag.clear();
// ADD end 20121003 QP@20505 No.24

	}

	/************************************************************************************
	 *
	 * リテラルデータ保持処理
	 *  : リテラルデータをDataCtrl内フィールドに格納する
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void setLiteralData() throws ExceptionBase {
		//　SA600 : リテラルデータ(工程属性)
		DataCtrl.getInstance().getLiteralDataZokusei().setLiteralData(xmlJW010);
		//　SA610 : リテラルデータ(一括表示)
		DataCtrl.getInstance().getLiteralDataIkatu().setLiteralData(xmlJW010);
		//　SA620 : リテラルデータ(ジャンル)
		DataCtrl.getInstance().getLiteralDataZyanru().setLiteralData(xmlJW010);
		//　SA630 : リテラルデータ(ユーザ)
		DataCtrl.getInstance().getLiteralDataUser().setLiteralData(xmlJW010);
		//　SA640 : リテラルデータ(特徴原料)
		DataCtrl.getInstance().getLiteralDataTokutyo().setLiteralData(xmlJW010);
		//　SA650 : リテラルデータ(用途)
		DataCtrl.getInstance().getLiteralDataYoto().setLiteralData(xmlJW010);
		//　SA660 : リテラルデータ(価格帯)
		DataCtrl.getInstance().getLiteralDataKakaku().setLiteralData(xmlJW010);
		//　SA670 : リテラルデータ(種別)
		DataCtrl.getInstance().getLiteralDataShubetu().setLiteralData(xmlJW010);
		//　SA680 : リテラルデータ(小数指定)
		DataCtrl.getInstance().getLiteralDataShosu().setLiteralData(xmlJW010);
		//　SA690 : リテラルデータ(担当営業)
		DataCtrl.getInstance().getLiteralDataTanto().setLiteralData(xmlJW010);
		//　SA700 : リテラルデータ(製造方法)
		DataCtrl.getInstance().getLiteralDataSeizo().setLiteralData(xmlJW010);
		//　SA710 : リテラルデータ(充填方法)
		DataCtrl.getInstance().getLiteralDataZyuten().setLiteralData(xmlJW010);
		//　SA720 : リテラルデータ(殺菌方法)
		DataCtrl.getInstance().getLiteralDataSakin().setLiteralData(xmlJW010);
		//　SA730 : リテラルデータ(容器包材)
		DataCtrl.getInstance().getLiteralDataYoki().setLiteralData(xmlJW010);
		//　SA740 : リテラルデータ(容量)
		DataCtrl.getInstance().getLiteralDataYoryo().setLiteralData(xmlJW010);
		//　SA750 : リテラルデータ(単位)
		DataCtrl.getInstance().getLiteralDataTani().setLiteralData(xmlJW010);
		//　SA760 : リテラルデータ(荷姿)
		DataCtrl.getInstance().getLiteralDataNisugata().setLiteralData(xmlJW010);
		//　SA770 : リテラルデータ(取扱温度)
		DataCtrl.getInstance().getLiteralDataOndo().setLiteralData(xmlJW010);
		//　SA780 : リテラルデータ(賞味期間)
		DataCtrl.getInstance().getLiteralDataShomi().setLiteralData(xmlJW010);
		//　SA850 : リテラルデータ(賞味期間)
		DataCtrl.getInstance().getLiteralDataShubetuNo().setLiteralData(xmlJW010);

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
		//　SA900 : リテラルデータ(工程パターン)
		DataCtrl.getInstance().getLiteralDataKoteiPtn().setLiteralData(xmlJW010);

		//　SA910 : リテラルデータ(製品比重)
		DataCtrl.getInstance().getLiteralDataSeihinHiju().setLiteralData(xmlJW010);

		//　SA920 : リテラルデータ(油相比重)
		DataCtrl.getInstance().getLiteralDataYusoHiju().setLiteralData(xmlJW010);

		//　SA930 : リテラルデータ(調味料１液タイプ)
		DataCtrl.getInstance().getLiteralDataKotei_tyomi1().setLiteralData(xmlJW010);

		//　SA940 : リテラルデータ(調味料２液タイプ)
		DataCtrl.getInstance().getLiteralDataKotei_tyomi2().setLiteralData(xmlJW010);

		//　SA940 : リテラルデータ(その他・加食タイプ)
		DataCtrl.getInstance().getLiteralDataKotei_sonota().setLiteralData(xmlJW010);
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20121003 QP@20505 No.24
		//　SA960 : リテラルデータ(実効酢酸濃度)
		DataCtrl.getInstance().getLiteralDataJikkoSakusanNodo().setLiteralData(xmlJW010);
// ADD end 20121003 QP@20505 No.24
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

					//-------------------------- 依頼番号  ---------------------------
					if(komoku == "依頼番号"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdIraiNo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//---------------------------- 品名  -----------------------------
					if(komoku == "品名"){
						String insert = checkNull(((JTextField)jc).getText());
						DataCtrl.getInstance().getTrialTblData().UpdHinmei(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//---------------------------- 廃止  -----------------------------
					if(komoku == "廃止"){
						String insert = (((JCheckBox)jc).isSelected())? "1" : "0";
						DataCtrl.getInstance().getTrialTblData().UpdHaishi(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

					//-------------------------- 種別番号  ---------------------------
					if(komoku == "種別番号"){
						String insert = checkNull(((JComboBox)jc).getSelectedItem());
						DataCtrl.getInstance().getTrialTblData().UpdSyubetuNo(
								DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}


//ADD 2013/06/19 ogawa 【QP@30151】No.9 start
					//------------------- 基本情報(試作表③)販責会社 ---------------------
					if(komoku == "販責会社"){
						//---------------------- 販責会社データ ----------------------------
						String selHanseki = (String)tb.getTrial3Panel().getCmbHanseki().getSelectedItem();

						String ins=null;

						//販責会社データコード検索
						HansekiData hansekiData = DataCtrl.getInstance().getHansekiData();

						for ( int i=0; i<hansekiData.getArtKaishaCd().size(); i++ ) {
							//会社コード
							String hansekiCd = hansekiData.getArtKaishaCd().get(i).toString();
							//会社名
							String hansekiNm = hansekiData.getAryKaishaNm().get(i).toString();

							//選択販責会社コードの検出
							if ( hansekiNm.equals(checkNull(selHanseki))) {
								ins = hansekiCd;
							}
						}
						//データ挿入
						DataCtrl.getInstance().getTrialTblData().UpdHanseki(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(ins),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);
					}

//ADD 2013/06/19 ogawa 【QP@30151】No.9 end
					//------------------- 基本情報(試作表③)担当会社or工場 ---------------------
					if(komoku == "担当会社" || komoku == "担当工場"){

						//---------------------- 会社データ ----------------------------
						String selKaisha = (String)tb.getTrial3Panel().getCmbKaisha().getSelectedItem();

						String insert=null;

						//会社データコード検索
						KaishaData kaishaData = DataCtrl.getInstance().getKaishaData();

						for ( int i=0; i<kaishaData.getArtKaishaCd().size(); i++ ) {
							//会社コード
							String kaishaCd = kaishaData.getArtKaishaCd().get(i).toString();
							//会社名
							String kaishaNm = kaishaData.getAryKaishaNm().get(i).toString();
							//原料桁
							String keta_genryo = kaishaData.getAryKaishaGenryo().get(i).toString();

							//選択会社コードの検出
							if ( kaishaNm.equals(checkNull(selKaisha))) {
								insert = kaishaCd;

								//原料桁洗替
								genryoCdArai(keta_genryo);

							}
						}
						//データ挿入
						DataCtrl.getInstance().getTrialTblData().UpdTantoKaisha(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

						//---------------------- 部署データ -----------------------------
						String selKojo = (String)tb.getTrial3Panel().getCmbKojo().getSelectedItem();
						String kojo_insert = null;
						BushoData bushoData = DataCtrl.getInstance().getBushoData();
						//部署データコード検索
						for (int i=0; i<bushoData.getArtBushoCd().size(); i++ ) {
							//部署コード
							String bushoCd = bushoData.getArtBushoCd().get(i).toString();
							//部署名
							String bushoNm = bushoData.getAryBushoNm().get(i).toString();

							//選択部署コードの検出
							if ( bushoNm.equals(checkNull(selKojo))) {
								kojo_insert = bushoCd;
							}
						}
						//データ挿入
						DataCtrl.getInstance().getTrialTblData().UpdTantoKojo(
								DataCtrl.getInstance().getTrialTblData().checkNullInt(kojo_insert),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

						//原料名洗替
						if(selKaisha != null && selKaisha.length() > 0){

							if(selKojo != null && selKojo.length() > 0){

								genryoNmArai();

							}

						}


					}
					//------------------------ 小数指定 -----------------------------
					if(komoku == "小数指定"){

						//初期処理
						TableBase tblListHeader  = tb.getTrial1Panel().getTrial1().getListHeader();
						TableBase tblListHaigo  = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
						TableBase tblListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
						ArrayList aryHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
						int max = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();


						//現在設定されている小数リテラルコード取得
						PrototypeData PrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
						String shosucd = PrototypeData.getStrShosu();

						//現在設定されている小数桁数取得
						int moto_val1 = 0;
						if(shosucd != null){
							moto_val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(shosucd));
						}


						//基本情報(試作表③)指定の小数リテラルコード取得
						String insert_shosu = null;
						int selectId = ((ComboBase)jc).getSelectedIndex();
						if(selectId > 0){
							insert_shosu = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralCd(selectId-1);
						}

						//基本情報(試作表③)指定の小数桁数取得
						int kosin_val1 = 0;
						if(insert_shosu != null){
							kosin_val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(insert_shosu));
						}

						//実行許可フラグ
						boolean blnExe = true;

						//指定小数が現在設定されている小数値より小さい場合
						if(kosin_val1 < moto_val1){

							//ダイアログコンポーネント設定
							JOptionPane jp = new JOptionPane();

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.19
							//確認ダイアログ表示
							int option = jp.showConfirmDialog(
									jp.getRootPane(),
//									"小数"+kosin_val1+"桁より大きい小数部は切り捨てられます。よろしいですか？"
									"小数"+kosin_val1+"桁より大きい小数部は四捨五入されます。よろしいですか？"
									, "確認メッセージ"
									,JOptionPane.YES_NO_OPTION
									,JOptionPane.PLAIN_MESSAGE
								);
//mod end --------------------------------------------------------------------------------------

							//「はい」押下
						    if (option == JOptionPane.YES_OPTION){

						    	//実行する
						    	blnExe = true;

						    //「いいえ」押下
						    }else if (option == JOptionPane.NO_OPTION){

						    	//実行しない
						    	blnExe = false;
						    }
						}

						//-------------------------------- 実行する場合 --------------------------------------
						if(blnExe){

							//小数点挿入
							DataCtrl.getInstance().getTrialTblData().UpdSyousuShitei(
									DataCtrl.getInstance().getTrialTblData().checkNullString(insert_shosu),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);

							//配合明細行数分ループ
							//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
							//for(int l=0; l<tblListHaigo.getRowCount()-max-8; l++){
							for(int l=0; l<tblListHaigo.getRowCount()-max-9; l++){
							//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

								//コンポーネント取得
								MiddleCellEditor selectMc = (MiddleCellEditor)tblListHaigo.getCellEditor(l, 2);
								DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(l);

								//原料行の場合に処理
								if(selectDc.getComponent() instanceof CheckboxBase){

									//キー項目取得
									CheckboxBase CheckboxBase = (CheckboxBase)selectDc.getComponent();
									int koteiCd = Integer.parseInt(CheckboxBase.getPk1());
									int koteiSeq = Integer.parseInt(CheckboxBase.getPk2());

									//試作ヘッダー列数分ループ
									for(int m=0; m<tblListHeader.getColumnCount(); m++){

										//コンポーネント取得
										MiddleCellEditor selectMch = (MiddleCellEditor)tblListHeader.getCellEditor(0, m);
										DefaultCellEditor selectDch = (DefaultCellEditor)selectMch.getTableCellEditor(0);

										//キー項目取得
										CheckboxBase CheckboxBaseh = (CheckboxBase)selectDch.getComponent();
										int ShisakuCd = Integer.parseInt(CheckboxBaseh.getPk1());

										//洗替処理
										String insert_arai = (String)tblListMeisai.getValueAt(l, m);

										//小数洗替
						    			if(insert_arai != null && insert_arai.length() > 0){

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.19
						    				//洗替処理
//						    				insert_arai = tb.getTrial1Panel().getTrial1().ShosuArai(insert_arai);
						    				insert_arai = tb.getTrial1Panel().getTrial1().ShosuAraiHulfUp(insert_arai);
//mod end --------------------------------------------------------------------------------------

							    			//テーブル挿入
						    				tblListMeisai.setValueAt(insert_arai, l, m);
						    			}

						    			//データ挿入
										DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
												ShisakuCd,
												koteiCd,
												koteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert_arai)
											);

										//工程合計計算
										tb.getTrial1Panel().getTrial1().koteiSum(m);

										//自動計算
										AutoKeisan();
									}
								}
							}
						}
						//------------------------------- 実行しない場合 --------------------------------------
						else{

							//試作③画面の小数選択値を元に戻す

							//リテラルコード先頭0埋め処理
							int strLng = shosucd.length();
							for(int i=strLng; i<3; i++){
								shosucd = "0" + shosucd;
							}

							//リテラル名取得
							String litNm = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralNm(shosucd);

							//コンボボックス選択
							((ComboBase)jc).setSelectedItem(litNm);

						}
					}

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------
					if(komoku == "容量単位"){
						//変更後取得
						String insert = null;
						String selectNm = (String) ((ComboBase)jc).getSelectedItem();
						insert = DataCtrl.getInstance().getLiteralDataTani().selectLiteralCd(selectNm);

						//変更前取得
						String  insert_moto = PrototypeData.getStrTani();

						//変更前と変更後が同じ場合
						if(insert.equals(insert_moto)){

							//処理なし

						}
						//変更前と変更後が異なる場合
						else{

							//変更後データ設定
							DataCtrl.getInstance().getTrialTblData().UpdYouryoTani(
									DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);

							//特性値、原価試算テーブル設定処理
							setTp2_5TableHiju(0);
						}
					}
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End --------------------------


				}
			}catch(ExceptionBase eb){
				DataCtrl.getInstance().PrintMessage(eb);

			}catch(Exception ex){
				ex.printStackTrace();

			}finally{
				//テスト表示
				//DataCtrl.getInstance().getTrialTblData().dispPrototype();
				//DataCtrl.getInstance().getTrialTblData().dispHaigo();
				//DataCtrl.getInstance().getTrialTblData().dispProtoList();

			}
	    }

		public void focusGained( FocusEvent e ){
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
			try{
				tb.getTrial1Panel().getTrial1().AutoCopyKeisan();

			}catch(ExceptionBase eb){
				DataCtrl.getInstance().PrintMessage(eb);

			}catch(Exception ex){
				ex.printStackTrace();

			}finally{

			}
//add end   -------------------------------------------------------------------------------
	    }
	}

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------
	/************************************************************************************
	 *
	 *  特性値、原価試算テーブル設定処理
	 *    :  製品比重、水相比重、水相充填量、油相充填量を設定
	 *   @author TT nishigawa
	 *   @flgColumnChenge 工程パターンによる表示項目変更フラグ
	 *                   （0:項目変更時の処理をしない  1:その他・加食⇒1液・2液  2:1液・2液⇒その他・加食）
	 *
	 ************************************************************************************/
	public void setTp2_5TableHiju(int flgColumnChenge) throws ExceptionBase{
		try{

			//現在選択中のタブ取得
			int sel = tb.getSelectedIndex();

			//工程パターン取得
			String ptKotei = PrototypeData.getStrPt_kotei();

			//容量単位取得
			String yoryoTani = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrTani();

			//容量単位からValue1取得
			String taniValue1 = "";
			if(yoryoTani == null || yoryoTani.length() == 0){

			}
			else{
				taniValue1 =  DataCtrl.getInstance().getLiteralDataTani().selectLiteralVal1(yoryoTani);
			}

			//を設定（テーブル）
			TableBase tp1Table = tb.getTrial1Panel().getTrial1().getListHeader();
			//特性値の製品比重、水相比重を設定（テーブル）
			TableBase tp2Table = tb.getTrial2Panel().getTable();
			//原価試算テーブル
			TableBase tp5Table = tb.getTrial5Panel().getTable();

			for(int i=0; i<tp1Table.getColumnCount(); i++){

				//キー項目
//				MiddleCellEditor mceSeq = (MiddleCellEditor)tp2Table.getCellEditor(0, i);
//				DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
//				TextboxBase chkSeq = (TextboxBase)dceSeq.getComponent();
//				int intSeq  = Integer.parseInt(chkSeq.getPk1());
				MiddleCellEditor mceSeq = (MiddleCellEditor)tp1Table.getCellEditor(0, i);
				DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
				CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
				int intSeq  = Integer.parseInt(chkSeq.getPk1());

				//工程パターンが「空白」の場合
				if(ptKotei == null || ptKotei.length() == 0){

					//製品比重　編集不可（初期値：空白）
					if(sel == 1){
						setSeihinHijuEnabled(i,false,"");
					}
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//水相比重　編集不可（初期値：空白）
					if(sel == 1){
						setSuisoHijuEnabled(i,false,"");
					}
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//「充填量水相」は編集不可
					if(sel == 3){
						setSuisoJutenEnabled(i,false,"");
					}
					DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
		    				JwsConstManager.JWS_COMPONENT_0134);

					//「充填量油相」は編集不可
					if(sel == 3){
						setYusoJutenEnabled(i,false,"");
					}
					DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
		    				JwsConstManager.JWS_COMPONENT_0135);

				}
				//工程パターンが「空白」でない場合
				else{

					//工程パターンのValue1取得
					String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

					//工程パターンが「調味料１液タイプ」の場合-------------------------------------------------------------
					if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){

						//容量が「ml」の場合
						if(taniValue1.equals("1")){

							//製品比重　編集可（初期値：空白）
							if(sel == 1){
								setSeihinHijuEnabled(i,true,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可（充填量計算）
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
							if(sel == 3){
								setSuisoJutenEnabled(i,false,keisan1);
							}
							tp5Table.setValueAt( keisan1, 3, i);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可
							if(sel == 3){
								setYusoJutenEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
						//容量が「g」の場合
						else if(taniValue1.equals("2")){

							//製品比重　編集不可（初期値：1）
							if(sel == 1){
								setSeihinHijuEnabled(i,false,"1");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可（充填量計算）
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
							if(sel == 3){
								setSuisoJutenEnabled(i,false,keisan1);
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可
							if(sel == 3){
								setYusoJutenEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
						//容量が「空白」の場合（ml,g以外の場合）
						else{

							//製品比重　編集不可（初期値：空白）
							if(sel == 1){
								setSeihinHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可
							if(sel == 3){
								setSuisoJutenEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可
							if(sel == 3){
								setYusoJutenEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
					}
					//工程パターンが「調味料２液タイプ」の場合-------------------------------------------------------------
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){

						//容量が「ml」の場合
						if(taniValue1.equals("1")){

							//製品比重　編集不可（製品比重　編集不可（下記自動計算））
							String keisan = DataCtrl.getInstance().getTrialTblData().KeisanSeihinHiju(intSeq);
							if(sel == 1){
								setSeihinHijuEnabled(i,false,keisan);
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集可（空白）
							if(sel == 1){
								setSuisoHijuEnabled(i,true,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可（計算）
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
							if(sel == 3){
								setSuisoJutenEnabled(i,false,keisan1);
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可（計算）
							String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
							if(sel == 3){
								setYusoJutenEnabled(i,false,keisan2);
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan2),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}

						//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -Start
						//容量が「g」の場合
						else if(taniValue1.equals("2")){

							//製品比重　編集不可（初期値：1）
							if(sel == 1){
								setSeihinHijuEnabled(i,false,"1");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可（充填量水相計算）
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
							if(sel == 3){
								setSuisoJutenEnabled(i,false,keisan1);
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可（充填量油相計算）
							String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
							if(sel == 3){
								setYusoJutenEnabled(i,false,keisan2);
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan2),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
						//QP@20505 No.2 2012/09/05 TT H.SHIMA ADD -End

						//容量が「空白」の場合（ml以外の場合）
						else{

							//製品比重　編集不可（初期値：空白）
							if(sel == 1){
								setSeihinHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可
							if(sel == 3){
								setSuisoJutenEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可
							if(sel == 3){
								setYusoJutenEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}

					}

					//工程パターンが「その他・加食タイプ」の場合-------------------------------------------------------------
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){

						//「充填量水相」は編集可
						if(sel == 3){
							setSuisoJutenEnabled(i,true,"");
						}
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0134);

						//「充填量油相」は編集可
						if(sel == 3){
							setYusoJutenEnabled(i,true,"");
						}
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0135);

						//容量が「g」の場合
						if(taniValue1.equals("2")){

							//製品比重　編集不可（初期値：1）
							if(sel == 1){
								setSeihinHijuEnabled(i,false,"1");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

						}
						//容量が「空白」の場合（g以外の場合）
						else{

							//製品比重　編集不可（初期値：空白）
							if(sel == 1){
								// MOD start 20121003 QP@20505
//								setSeihinHijuEnabled(i,false,"1");
								setSeihinHijuEnabled(i,false,"");
								// MOD start 20121003 QP@20505
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							if(sel == 1){
								setSuisoHijuEnabled(i,false,"");
							}
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);
						}
					}
// ADD start 20121003 QP@20505 No.24
					// 各サンプルごとに画面項目クリア（デフォルト値を設定）
					if (flgColumnChenge == 1){
						// 工程パターン その他・加食or空白 ⇒ 1液・2液 に変更された場合
						// 糖度
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuToudo(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// 粘度
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuNendo(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// 温度
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuOndo(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// 総酸－分析
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanBunseki(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// 食塩－分析
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenBunseki(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// 水分活性フリー内容
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeSuibunKasei(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// アルコールフリー内容
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeAlchol(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// フリー1
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_1(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// フリー2
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_2(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// フリー3
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_3(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);

					}else if (flgColumnChenge == 2){
						// 工程パターン 1液・2液 ⇒ その他・加食or空白 に変更された場合
						// 粘度フリー
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNendo(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// 温度フリー
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeOndo(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// フリー1
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_1(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// フリー2
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_2(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// フリー3
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_3(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// フリー4
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_4(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// フリー5
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_5(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
						// フリー6
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNaiyou_6(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);
					}
// ADD end 20121003 QP@20505 No.24
				}
// ADD start 20121003 QP@20505 No.24
				// 画面項目のクリア（デフォルト値を設定）
				// ADD start 20130226 QP@20505 検収後の修正
				if (flgColumnChenge == 0){
					// 工程パターン 空白 ⇔ その他・加食 に変更された場合
					// 水分活性フリータイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_SuibunKasei(
		    				"水分活性",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// アルコールフリータイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Alchol(
		    				"アルコール",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
				}else
				// ADD end 20130226 QP@20505 検収後の修正
				if (flgColumnChenge == 1){
					// 水分活性フリータイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_SuibunKasei(
// MOD start 20130225 QP@20505 検収後の修正
//// MOD start 20121128 QP@20505 課題No.11
////		    				"水分活性",
//		    				"",
//// MOD end 20121128 QP@20505 課題No.11
		    				"水分活性",
// MOD end 20130225 QP@20505 検収後の修正
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// アルコールフリータイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Alchol(
// MOD start 20130225 QP@20505 検収後の修正
//// MOD start 20121128 QP@20505 課題No.11
////		    				"アルコール",
//		    				"",
//// MOD end 20121128 QP@20505 課題No.11
		    				"アルコール",
// MOD end 20130225 QP@20505 検収後の修正
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					// ADD start 20130226 QP@20505 検収後の修正
					// 粘度フリータイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Nendo(
		    				"粘度",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// 温度フリータイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Ondo(
		    				"温度（℃）",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// ADD end 20130226 QP@20505 検収後の修正

					// 糖度－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuToudoFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// 粘度・温度－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuNendoFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuOndoFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// 総酸－分析－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSousanBunsekiFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// 食塩－分析－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuenBunsekiFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// 水分活性フリー－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeSuibunKaseiFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// アルコールフリー－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeAlcholFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー1タイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_1(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー1－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_1(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー2タイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_2(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー2－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_2(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー3タイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_3(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー3－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_3(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					if(sel == 1){
						tb.getTrial2Panel().afterOtherTypeDispClear();
					}

				}else if (flgColumnChenge == 2){

					// 粘度フリータイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Nendo(
// MOD start 20130225 QP@20505 検収後の修正
//// MOD start 20121128 QP@20505 課題No.11
////		    				"粘度",
//		    				"",
//// MOD end 20121128 QP@20505 課題No.11
		    				"粘度",
// MOD end 20130225 QP@20505 検収後の修正
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// 温度フリータイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Ondo(
// MOD start 20130225 QP@20505 検収後の修正
//// MOD start 20121128 QP@20505 課題No.11
////		    				"温度",
//		    				"",
//// MOD end 20121128 QP@20505 課題No.11
		    				"温度（℃）",
// MOD end 20130225 QP@20505 検収後の修正
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// 粘度フリー・温度フリー－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeNendoFg(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// ADD start 20130226 QP@20505 検収後の修正
					// 水分活性フリータイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_SuibunKasei(
		    				"水分活性",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// アルコールフリータイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_Alchol(
		    				"アルコール",
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// ADD end 20130226 QP@20505 検収後の修正
					// フリー1タイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_1(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー1－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_1(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー2タイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_2(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー2－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_2(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー3タイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_3(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー3－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_3(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー4タイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_4(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー4－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_4(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー5タイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_5(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー5－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_5(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー6タイトル
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeTitle_6(
							DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);
					// フリー6－出力Flg
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuFreeFg_6(
		    				0,
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					if(sel == 1){
						tb.getTrial2Panel().afterEkiTypeDispClear();
					}
				}
// ADD end 20121003 QP@20505 No.24

				//⑤の表示値設定
				if(sel == 3){
					tb.getTrial5Panel().updDispValues(intSeq, i);
				}

			}
		}
		catch(Exception e){
			e.printStackTrace();

		}
	}
	private void setSeihinHijuEnabled(int col,boolean enabled,String val){
		try{
// ADD start 20121003 QP@20505 No.24
			//工程パターン取得
			String ptKotei = PrototypeData.getStrPt_kotei();
			String taniValue = "0";
			int numRow = 12;
			if(ptKotei == null || ptKotei.length() == 0){
				taniValue = "0";
			}
			else{
				taniValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
			}
			if (taniValue.equals(JwsConstManager.JWS_KOTEITYPE_1) || taniValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
				numRow = 9;
			}
// ADD end 20121003 QP@20505 No.24
			//特性値の製品比重、水相比重を設定（テーブル）
			TableBase tp2Table = tb.getTrial2Panel().getTable();

			//製品比重コンポーネント取得
// MOD start 20121003 QP@20505 No.24
//			MiddleCellEditor tp2TableMC_h = (MiddleCellEditor)tp2Table.getCellEditor(12, col);
//			DefaultCellEditor tp2TableDC_h = (DefaultCellEditor)tp2TableMC_h.getTableCellEditor(12);
			MiddleCellEditor tp2TableMC_h = (MiddleCellEditor)tp2Table.getCellEditor(numRow, col);
			DefaultCellEditor tp2TableDC_h = (DefaultCellEditor)tp2TableMC_h.getTableCellEditor(numRow);
// MOD end 20121003 QP@20505 No.24
			TextboxBase tp2TableTB_h = (TextboxBase)tp2TableDC_h.getComponent();

			//製品比重
			tp2TableTB_h.setEditable(enabled);
// MOD start 20121003 QP@20505 No.24
//			tp2Table.setValueAt( val, 12, col);
			tp2Table.setValueAt( val, numRow, col);
// MOD end 20121003 QP@20505 No.24

			//背景色変更
// MOD start 20121003 QP@20505 No.24
//			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp2Table.getCellRenderer(12, col);
//			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(12);
			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp2Table.getCellRenderer(numRow, col);
			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(numRow);
// MOD end 20121003 QP@20505 No.24
			if(enabled){
				tp2TableTB_h.setBackground(Color.white);
				selectCer.setColor(Color.white);
			}
			else{
				tp2TableTB_h.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
				selectCer.setColor(JwsConstManager.JWS_DISABLE_COLOR);
			}

		}catch(Exception e){

		}
	}
	private void setSuisoHijuEnabled(int col,boolean enabled,String val){
		try{
// ADD start 20121003 QP@20505 No.24
			//工程パターン取得
			String ptKotei = PrototypeData.getStrPt_kotei();
			String taniValue = "0";
			int numRow = 13;
			if(ptKotei == null || ptKotei.length() == 0){
				taniValue = "0";
			}
			else{
				taniValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
			}
			if (taniValue.equals(JwsConstManager.JWS_KOTEITYPE_1) || taniValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
				numRow = 10;
			}
// ADD end 20121003 QP@20505 No.24

			//特性値の製品比重、水相比重を設定（テーブル）
			TableBase tp2Table = tb.getTrial2Panel().getTable();

			//水相比重コンポーネント取得
// MOD start 20121003 QP@20505 No.24
//			MiddleCellEditor tp2TableMC_s = (MiddleCellEditor)tp2Table.getCellEditor(13, col);
//			DefaultCellEditor tp2TableDC_s = (DefaultCellEditor)tp2TableMC_s.getTableCellEditor(13);
			MiddleCellEditor tp2TableMC_s = (MiddleCellEditor)tp2Table.getCellEditor(numRow, col);
			DefaultCellEditor tp2TableDC_s = (DefaultCellEditor)tp2TableMC_s.getTableCellEditor(numRow);
// MOD end 20121003 QP@20505 No.24
			TextboxBase tp2TableTB_s = (TextboxBase)tp2TableDC_s.getComponent();

			//水相比重
			tp2TableTB_s.setEditable(enabled);
// MOD start 20121003 QP@20505 No.24
//			tp2Table.setValueAt( val, 13, col);
			tp2Table.setValueAt( val, numRow, col);
// MOD end 20121003 QP@20505 No.24

			//背景色変更
// MOD start 20121003 QP@20505 No.24
//			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp2Table.getCellRenderer(13, col);
//			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(13);
			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp2Table.getCellRenderer(numRow, col);
			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(numRow);
// MOD end 20121003 QP@20505 No.24
			if(enabled){
				tp2TableTB_s.setBackground(Color.white);
				selectCer.setColor(Color.white);
			}
			else{
				tp2TableTB_s.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
				selectCer.setColor(JwsConstManager.JWS_DISABLE_COLOR);
			}

		}catch(Exception e){

		}
	}
	private void setSuisoJutenEnabled(int col,boolean enabled,String val){
		try{
			//原価試算テーブル
			TableBase tp5Table = tb.getTrial5Panel().getTable();

			//水相充填量コンポーネント取得
			MiddleCellEditor tp5TableMC_s = (MiddleCellEditor)tp5Table.getCellEditor(3, col);
			DefaultCellEditor tp5TableDC_s = (DefaultCellEditor)tp5TableMC_s.getTableCellEditor(3);
			TextboxBase tp5TableTB_s = (TextboxBase)tp5TableDC_s.getComponent();

			//充填量水相
			tp5TableTB_s.setEditable(enabled);
			tp5Table.setValueAt( val, 3, col);

			//背景色変更
			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp5Table.getCellRenderer(3, col);
			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(3);
			if(enabled){
				tp5TableTB_s.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				selectCer.setColor(JwsConstManager.SHISAKU_ITEM_COLOR2);
			}
			else{
				tp5TableTB_s.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
				selectCer.setColor(JwsConstManager.JWS_DISABLE_COLOR);
			}

		}catch(Exception e){

		}
	}
	private void setYusoJutenEnabled(int col,boolean enabled,String val){
		try{
			//原価試算テーブル
			TableBase tp5Table = tb.getTrial5Panel().getTable();

			//油相充填量コンポーネント取得
			MiddleCellEditor tp5TableMC_y = (MiddleCellEditor)tp5Table.getCellEditor(4, col);
			DefaultCellEditor tp5TableDC_y = (DefaultCellEditor)tp5TableMC_y.getTableCellEditor(4);
			TextboxBase tp5TableTB_y = (TextboxBase)tp5TableDC_y.getComponent();

			//充填量油相
			tp5TableTB_y.setEditable(enabled);
			tp5Table.setValueAt( val, 4, col);

			//背景色変更
			MiddleCellRenderer selectMr = (MiddleCellRenderer)tp5Table.getCellRenderer(4, col);
			TextFieldCellRenderer selectCer = (TextFieldCellRenderer)selectMr.getTableCellRenderer(4);
			if(enabled){
				tp5TableTB_y.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				selectCer.setColor(JwsConstManager.SHISAKU_ITEM_COLOR2);
			}
			else{
				tp5TableTB_y.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
				selectCer.setColor(JwsConstManager.JWS_DISABLE_COLOR);
			}

		}catch(Exception e){

		}
	}
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

	/************************************************************************************
	 *
	 *  原料桁（原料コード）洗い換え
	 *    :  会社指定の桁数にて原料コードの洗替を行う
	 *   @author TT nishigawa
	 *   @param 指定桁数
	 *
	 ************************************************************************************/
	public void genryoCdArai(String keta) throws ExceptionBase{
		try{

			//指定桁数
			String setLen = JwsConstManager.JWS_KETA_GENRYO;

			//配合明細テーブル取得
			TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();

			//配合明細テーブル　行ループ
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){

				//コンポーネント取得
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 3);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

				//原料コードの場合
				if(selectDc.getComponent() instanceof HankakuTextbox){


					//キー項目取得
					MiddleCellEditor selectMcKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
					DefaultCellEditor selectDcKey = (DefaultCellEditor)selectMcKey.getTableCellEditor(i);
					CheckboxBase cb = (CheckboxBase)selectDcKey.getComponent();
					int koteiCd = Integer.parseInt(cb.getPk1());
					int koteiSeq = Integer.parseInt(cb.getPk2());

					//原料コード取得
					String strCd = (String)HaigoMeisai.getValueAt(i, 3);

					//設定原料コード初期化
					String setCd = null;

					//コンポーネント取得
					HankakuTextbox Comp = (HankakuTextbox)selectDc.getComponent();


					//指定桁数がNULLの場合
					if(keta == null){



					}
					//指定桁数がNULL以外の場合
					else{

						setLen = keta;

					}

					//洗替前の原料コード桁数取得
					int keta_moto = 0;
					if(strCd != null){
						keta_moto = strCd.length();
					}

					//洗替後の原料コード桁数が小さくなる場合
					if(keta_moto > Integer.parseInt(setLen)){

						//コードにNullを設定
						setCd = null;

					}
					//洗替後の原料コード桁数が等しいか、大きくなる場合
					else{

						//コードに洗替前の値を設定
						setCd = strCd;

					}

					//コメント行の判定
					boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(strCd, Comp.getIntMaxLength());

					//コメント行の場合
					if(comFlg){

						//コメント行作成
						setCd = commentSet(Integer.parseInt(setLen));

					}

					//桁数設定
					Comp.setIntMaxLength(Integer.parseInt(setLen));

					//テーブル値に洗替後のコードを設定
					HaigoMeisai.setValueAt(setCd, i, 3);

					//配合データ：原料コードに洗替後のコードを設定
					DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoCd(
							koteiCd,
							koteiSeq,
							setCd,
							DataCtrl.getInstance().getUserMstData().getDciUserid()
						);


				}

			}

		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *  コメント行作成
	 *    :  指定文字列、指定桁数にてコメント行を作成する（指定桁数全てに9を挿入）
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public String commentSet(int intKeta){

		//戻り値初期化
		String ret = "";

		try{

			//指定桁数ループ
			for(int i=0; i<intKeta; i++){

				//文字「9」を加算
				ret = ret + "9";

			}


		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}

		//返却
		return ret;
	}


	/************************************************************************************
	 *
	 *  原料情報、会社/工場（部署）洗い換え
	 *    :  原料情報、会社/工場（部署）洗い換えを行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private void genryoNmArai() throws ExceptionBase{
		try{
			//------------------- 配合表(試作表①)原料名洗替 ------------------------
			TableBase HaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();

			//配合データ取得
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

			//最大工程順取得
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

			//洗い替え通信処理
			ArrayList aryHaigoArai = conJW070();

			//表示処理
			TableBase dispHaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//for(int k=0; k<dispHaigoMeisai.getRowCount()-maxKotei-8; k++){
			for(int k=0; k<dispHaigoMeisai.getRowCount()-maxKotei-9; k++){
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

				//コンポーネント取得
				MiddleCellEditor selectMc = (MiddleCellEditor)dispHaigoMeisai.getCellEditor(k, 2);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(k);

				//原料行の場合に処理
				if(selectDc.getComponent() instanceof CheckboxBase){
					CheckboxBase CheckboxBase = (CheckboxBase)selectDc.getComponent();

					//キー項目取得
					int koteiCd = Integer.parseInt(CheckboxBase.getPk1());
					int koteiSeq = Integer.parseInt(CheckboxBase.getPk2());

					//通信取得データループ
					for(int l=0; l<aryHaigoArai.size(); l++){

						//原料データ取得
						MixedData md = (MixedData)aryHaigoArai.get(l);

						if(md.getIntGenryoNo() == k){

							//原料名称取得
							String insertNm = md.getStrGenryoNm();

							//2011/04/28 QP@10181_No.5 TT T.Satoh Add Start -------------------------
							//原料コード取得
							String insertCd = md.getStrGenryoCd();
							//2011/04/28 QP@10181_No.5 TT T.Satoh Add End ---------------------------

							if(insertNm != null && insertNm.length() > 0){

								//表示用原料名
								String strDispGenryo = insertNm;

								//コメント行の判定
								int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();
								boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(md.getStrGenryoCd(), keta);

								//原料コードがコメント行の場合
								if(insertNm != null && insertNm.length()>0){

									if(md.getStrGenryoCd() != null && comFlg){

										//「★☆」記号がある場合
										if(strDispGenryo.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001) ||
												strDispGenryo.substring(0, 1).equals(JwsConstManager.JWS_MARK_0002)){

											//星記号削除
											strDispGenryo = strDispGenryo.substring(1);
											if(strDispGenryo.length() == 0){
												strDispGenryo = null;
											}
										}
									}
								}

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
								//編集可否チェック
								boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(0, koteiCd, koteiSeq);

								//編集可能の場合：既存処理
								if(chk){
									//2011/04/28 QP@10181_No.5 TT T.Satoh Add Start -------------------------
									//原料コードがNULLではない場合
									if (insertCd != null) {
										//原料コードの1文字目が「N」ではない場合
										if (!insertCd.substring(0, 1).equals("N")) {
									//2011/04/28 QP@10181_No.5 TT T.Satoh Add End ---------------------------
											//表示値設定
											dispHaigoMeisai.setValueAt(strDispGenryo, k, 4);

											//原料名
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
													koteiCd,
													koteiSeq,
													strDispGenryo,
													DataCtrl.getInstance().getUserMstData().getDciUserid());
									//2011/04/28 QP@10181_No.5 TT T.Satoh Add Start -------------------------
										}
										else{
											//新規原料で★の場合
											if(strDispGenryo.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001)){
												//表示値設定
												dispHaigoMeisai.setValueAt(strDispGenryo, k, 4);

												//原料名
												DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
														koteiCd,
														koteiSeq,
														strDispGenryo,
														DataCtrl.getInstance().getUserMstData().getDciUserid());
											}
											else{
												//設定前の原料名取得
												String genryoNm_moto = DataCtrl.getInstance().getTrialTblData().SearchHaigoGenryoMei(koteiCd,koteiSeq);

												//設定前の原料名がNULLでない場合
												if(genryoNm_moto != null){

													//「★」記号がある場合
													if(genryoNm_moto.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001)){
														//星記号削除
														genryoNm_moto = genryoNm_moto.substring(1);
														if(genryoNm_moto.length() == 0){
															genryoNm_moto = null;
														}

														//表示値設定
														dispHaigoMeisai.setValueAt(genryoNm_moto, k, 4);

														//原料名
														DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
																koteiCd,
																koteiSeq,
																genryoNm_moto,
																DataCtrl.getInstance().getUserMstData().getDciUserid());
													}
												}
											}
										}
									}
									//2011/04/28 QP@10181_No.5 TT T.Satoh Add End ---------------------------

									//会社CD
									DataCtrl.getInstance().getTrialTblData().UpdHaigoKaishaCd(
											koteiCd,
											koteiSeq,
											md.getIntKaishaCd(),
											DataCtrl.getInstance().getUserMstData().getDciUserid());

									//部署CD（工場CD）
									DataCtrl.getInstance().getTrialTblData().UpdHaigoKojoCd(
											koteiCd,
											koteiSeq,
											md.getIntBushoCd(),
											DataCtrl.getInstance().getUserMstData().getDciUserid());

									//2011/04/26 QP@10181_No.73 TT T.Satoh Add Start -------------------------
									//単価
//									//表示値設定
//									dispHaigoMeisai.setValueAt(md.getDciTanka(), k, 5);
//									//テーブル設定
//									DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoTanka(
//											koteiCd,
//											koteiSeq,
//											md.getDciTanka(),
//											DataCtrl.getInstance().getUserMstData().getDciUserid());

									//表示値設定
									dispHaigoMeisai.setValueAt(md.getDciTanka(), k, 5);

									//テーブル設定
									DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoTanka(
											koteiCd,
											koteiSeq,
											md.getDciTanka(),
											DataCtrl.getInstance().getUserMstData().getDciUserid());

									//エディタ設定
									TableBase tbHaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
									MiddleCellEditor mceTanka = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(k, 5);
									DefaultCellEditor dceTanka = (DefaultCellEditor)mceTanka.getTableCellEditor(k);
									TextboxBase tbbTanka = (TextboxBase)dceTanka.getComponent();

									//レンダラ設定
									MiddleCellRenderer mcrTanka =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(k, 5);
									TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)mcrTanka.getTableCellRenderer(k);

									//新規原料orコメント行orNULLの場合
									boolean sinki_chk = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoCdSinki(koteiCd, koteiSeq);
									if(sinki_chk){

										//編集可
										tbbTanka.setBackground(Color.WHITE);
										tbbTanka.setEditable(true);
										tbbTanka.setEnabled(true);
										tfcrTanka.setColor(Color.WHITE);
									}
									//新規原料orコメント行orNULLでない場合
									else{
										//基本情報の会社がキユーピーの場合
										if(DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntKaishacd() ==
											Integer.parseInt(JwsConstManager.JWS_CD_KEWPIE)){

											//原料の会社がキユーピーの場合
											if(md.getIntKaishaCd() == Integer.parseInt(JwsConstManager.JWS_CD_KEWPIE)){

												//★原料の場合
												if(strDispGenryo != null && strDispGenryo.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001)){

													//編集可
													tbbTanka.setBackground(Color.WHITE);
													tbbTanka.setEditable(true);
													tbbTanka.setEnabled(true);
													tfcrTanka.setColor(Color.WHITE);
												}
												//★原料でない場合
												else{

													//編集不可
													tbbTanka.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
													tbbTanka.setEditable(false);
													tbbTanka.setEnabled(false);
													tfcrTanka.setColor(JwsConstManager.JWS_DISABLE_COLOR);
												}
											}
											//原料の会社がキユーピーでない場合
											else{

												//編集可
												tbbTanka.setBackground(Color.WHITE);
												tbbTanka.setEditable(true);
												tbbTanka.setEnabled(true);
												tfcrTanka.setColor(Color.WHITE);
											}
										}
										//基本情報の会社がキユーピーでない場合
										else{

											//編集可
											tbbTanka.setBackground(Color.WHITE);
											tbbTanka.setEditable(true);
											tbbTanka.setEnabled(true);
											tfcrTanka.setColor(Color.WHITE);

										}
									}
									//2011/04/26 QP@10181_No.73 TT T.Satoh Add End -------------------------

									//歩留
									//表示値設定
									dispHaigoMeisai.setValueAt(md.getDciBudomari(), k, 6);
									//テーブル設定
									DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoBudomari(
											koteiCd,
											koteiSeq,
											md.getDciBudomari(),
											DataCtrl.getInstance().getUserMstData().getDciUserid());

									//油含有率
									//表示値設定
									dispHaigoMeisai.setValueAt(md.getDciGanyuritu(), k, 7);
									//テーブル設定
									DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoYuganyuryo(
											koteiCd,
											koteiSeq,
											md.getDciGanyuritu(),
											DataCtrl.getInstance().getUserMstData().getDciUserid());
								}
								//編集不可の場合：処理しない
								else{

								}
								//酢酸
								DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSakusan(
										koteiCd,
										koteiSeq,
										md.getDciSakusan(),
										DataCtrl.getInstance().getUserMstData().getDciUserid());

								//食塩
								DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSyokuen(
										koteiCd,
										koteiSeq,
										md.getDciShokuen(),
										DataCtrl.getInstance().getUserMstData().getDciUserid());

								//総酸
								DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSousan(
										koteiCd,
										koteiSeq,
										md.getDciSosan(),
										DataCtrl.getInstance().getUserMstData().getDciUserid());

// ADD start 20121002 QP@20505 No.24
								//ＭＳＧ
								DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMsg(
										koteiCd,
										koteiSeq,
										md.getDciMsg(),
										DataCtrl.getInstance().getUserMstData().getDciUserid());
// ADD end 20121002 QP@20505 No.24
//mod end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31

								//他工場に原料がある場合
								if(md.getDciMaBudomari() == null){

								}
								//自工場に原料がある場合
								else{
									//マスタ歩留設定
									DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMaBudomari(
											koteiCd,
											koteiSeq,
											md.getDciMaBudomari());
								}



								boolean fg = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoMaBudomari(koteiCd, koteiSeq);

								//エディタ設定
								TableBase tbHaigoMeisai = tb.getTrial1Panel().getTrial1().getHaigoMeisai();
								MiddleCellEditor mceBudomari = (MiddleCellEditor)tbHaigoMeisai.getCellEditor(k, 6);
								DefaultCellEditor dceBudomari = (DefaultCellEditor)mceBudomari.getTableCellEditor(k);
								//2011/05/31 QP@10181_No.66 TT T.Satoh Change Start -------------------------
								//依頼済みの場合
								if(!DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(0, koteiCd, koteiSeq)){
									//色を変えない
								}
								else if(fg){
								//if(fg){
								//2011/05/31 QP@10181_No.66 TT T.Satoh Change End ---------------------------
									((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.PLAIN, 12));
									((TextboxBase)dceBudomari.getComponent()).setForeground(Color.black);
								}
								else{
									((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.BOLD, 12));
									((TextboxBase)dceBudomari.getComponent()).setForeground(Color.red);
								}

								//レンダラ設定
								MiddleCellRenderer mcrBudomari =  (MiddleCellRenderer)tbHaigoMeisai.getCellRenderer(k, 6);
								TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)mcrBudomari.getTableCellRenderer(k);
								if(fg){
									tfcrBudomari.setFont(new Font("Default", Font.PLAIN, 12));
									tfcrBudomari.setForeground(Color.black);
								}
								else{
									tfcrBudomari.setFont(new Font("Default", Font.BOLD, 12));
									tfcrBudomari.setForeground(Color.red);
								}
//add end   -------------------------------------------------------------------------------

							}
						}
					}
				}
			}
		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料情報の会社/工場（部署）洗替処理に失敗しました");
			ex.setStrErrShori(this.getClass().toString());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}

	}

	/************************************************************************************
	 *
	 *  原料情報、会社/工場（部署）洗い換え　XML通信処理（JW070）
	 *    :  原料情報、会社/工場（部署）洗い換え処理XMLデータ通信（JW070）を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	private ArrayList conJW070() throws ExceptionBase{
		ArrayList ret = new ArrayList();

		try{

			//--------------------------- 送信パラメータ格納  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//配合データ取得
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

			//--------------------------- 送信XMLデータ作成  ---------------------------------
			xmlJW070 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root追加  ------------------------------------
			xmlJW070.AddXmlTag("","JW070");
			arySetTag.clear();

			//------------------------- 機能ID追加（USERINFO）  -----------------------------
			xmlJW070.AddXmlTag("JW070", "USERINFO");

			//----------------------------- テーブルタグ追加  --------------------------------
			xmlJW070.AddXmlTag("USERINFO", "table");

			//------------------------------ レコード追加  -----------------------------------
			//処理区分
			arySetTag.add(new String[]{"kbn_shori", "3"});
			//ユーザID
			arySetTag.add(new String[]{"id_user",strUser});
			//XMLへレコード追加
			xmlJW070.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//---------------------- 原料情報、会社/工場（部署）洗い換え  ----------------------
			xmlJW070.AddXmlTag("JW070", "SA810");
			//　テーブルタグ追加
			xmlJW070.AddXmlTag("SA810", "table");


			//-------------------------------- レコード追加  ---------------------------------
			//試作品データ取得
			PrototypeData selPrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
			//配合データ取得
			ArrayList addHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

			for(int i=0; i<addHaigo.size(); i++){
				MixedData MixedData = (MixedData)addHaigo.get(i);
				//会社CD(洗い換え先の会社CD)
				arySetTag.add(new String[]{"cd_new_kaisha",checkNull(selPrototypeData.getIntKaishacd())});
				//工場CD(洗い換え先の工場CD)
				arySetTag.add(new String[]{"cd_new_busho",checkNull(selPrototypeData.getIntKojoco())});
				//原料順
				arySetTag.add(new String[]{"sort_genryo",checkNull(MixedData.getIntGenryoNo())});
				//原料CD
				arySetTag.add(new String[]{"cd_genryo",checkNull(MixedData.getStrGenryoCd())});
				//会社CD
				arySetTag.add(new String[]{"cd_kaisha",checkNull(MixedData.getIntKaishaCd())});
				//部署CD（工場CD）
				arySetTag.add(new String[]{"cd_busho",checkNull(MixedData.getIntBushoCd())});
				//原料名称
				String insertNm = "";

				//原料桁数取得
				int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();

				//コメント行の判定
				boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(
												MixedData.getStrGenryoCd(),
												keta
											);

				//コメント行の場合
				if(comFlg){

					insertNm = MixedData.getStrGenryoNm();

				//原料行の場合
				}else{

					insertNm = tb.getTrial1Panel().getTrial1().delMark(MixedData.getStrGenryoNm());

				}
				arySetTag.add(new String[]{"nm_genryo",checkNull(insertNm)});
				//単価
				arySetTag.add(new String[]{"tanka",checkNull(MixedData.getDciTanka())});
				//歩留
				arySetTag.add(new String[]{"budomari",checkNull(MixedData.getDciBudomari())});
				//油含有率
				arySetTag.add(new String[]{"ritu_abura",checkNull(MixedData.getDciGanyuritu())});
				//酢酸
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(MixedData.getDciSakusan())});
				//食塩
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(MixedData.getDciShokuen())});
				//総酸
				arySetTag.add(new String[]{"ritu_sousan",checkNull(MixedData.getDciSosan())});
// ADD start 20121002 QP@20505 No.24
				//ＭＳＧ
				arySetTag.add(new String[]{"ritu_msg",checkNull(MixedData.getDciMsg())});
// ADD end 20121002 QP@20505 No.24

				//XMLへレコード追加
				xmlJW070.AddXmlTag("table", "rec", arySetTag);
				//配列初期化
				arySetTag.clear();
			}

			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("xmlJW070送信XML===============================================================");
//			xmlJW070.dispXml();
			xcon = new XmlConnection(xmlJW070);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML受信  ----------------------------------
			xmlJW070 = xcon.getXdocRes();
//			System.out.println();
//			System.out.println("xmlJW070受信XML===============================================================");
//			xmlJW070.dispXml();

			//---------------------------------- Resultチェック  -------------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW070);
			if (DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("false")) {
				ExceptionBase ExceptionBase  = new ExceptionBase();
				throw ExceptionBase;

			}else{
				/**********************************************************
				 *　T120格納
				 *********************************************************/
				//全体配列取得
				ArrayList t120 = xmlJW070.GetAryTag("SA810");

				//機能配列取得
				ArrayList kinoData = (ArrayList)t120.get(0);

				//テーブル配列取得
				ArrayList tableT120 = (ArrayList)kinoData.get(1);

				//　レコード取得
				for(int i=1; i<tableT120.size(); i++){

					//　１レコード取得
					ArrayList recData = ((ArrayList)((ArrayList)tableT120.get(i)).get(0));
					//　配合データ初期化
					MixedData midtHaigou = new MixedData();

					//　データへ格納
					for(int j=0; j<recData.size(); j++){

						//　項目名取得
						String recNm = ((String[])recData.get(j))[1];
						//　項目値取得
						String recVal = ((String[])recData.get(j))[2];

						/*****************配合データへ値セット*********************/
						//  原料順
						if(recNm == "sort_genryo"){
							midtHaigou.setIntGenryoNo(DataCtrl.getInstance().getTrialTblData().checkNullInt(recVal));

						//　原料CD
						}if(recNm == "cd_genryo"){
							midtHaigou.setStrGenryoCd(DataCtrl.getInstance().getTrialTblData().checkNullString(recVal));

						//　会社CD
						}if(recNm == "cd_kaisha"){
							midtHaigou.setIntKaishaCd(DataCtrl.getInstance().getTrialTblData().checkNullInt(recVal));

						//　部署CD（工場CD）
						}if(recNm == "cd_busho"){
							midtHaigou.setIntBushoCd(DataCtrl.getInstance().getTrialTblData().checkNullInt(recVal));

						//　原料名称
						}if(recNm == "nm_genryo"){
							midtHaigou.setStrGenryoNm(DataCtrl.getInstance().getTrialTblData().checkNullString(recVal));

						//　単価
						}if(recNm == "tanka"){
							midtHaigou.setDciTanka(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						//　歩留
						}if(recNm == "budomari"){
							midtHaigou.setDciBudomari(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						//　油含有率
						}if(recNm == "ritu_abura"){
							midtHaigou.setDciGanyuritu(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						//　酢酸
						}if(recNm == "ritu_sakusan"){
							midtHaigou.setDciSakusan(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						//　食塩
						}if(recNm == "ritu_shokuen"){
							midtHaigou.setDciShokuen(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						//　総酸
						}if(recNm == "ritu_sousan"){
							midtHaigou.setDciSosan(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						}

						if(recNm == "ma_budomari"){
							midtHaigou.setDciMaBudomari(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));

						}
// ADD start 20121002 QP@20505 No.24
						//　ＭＳＧ
						if(recNm == "ritu_msg"){
							midtHaigou.setDciMsg(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						}
// ADD end 20121002 QP@20505 No.24
					}
					//　配合データ配列へ追加
					ret.add(midtHaigou);
					recData.clear();
				}
			}

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg(" 原料情報の会社/工場（部署）洗替処理に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		}finally{

		}

		return ret;
	}

// ADD start 20121017 QP@20505 No.11
	/**
	 * 【JW220】 原料分析マスタ値取得 送信XMLデータ作成
	 * @author 2012/10/17 T.Hisahori
	 */
	private ArrayList conJW220() throws ExceptionBase{
		ArrayList ret = new ArrayList();
		int i;
		try{
			//--------------------------- 送信パラメータ格納  -----------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			String strXmlId = "JW220";
			String strAjaxUrl = DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax();

			//--------------------------- 送信XMLデータ作成  -----------------------------------
			XmlData xmlJW220 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------- Root追加  --------------------------------------
			xmlJW220.AddXmlTag("",strXmlId);
			arySetTag.clear();

			//-------------------------- 機能ID追加（USERINFO）  -------------------------------
			xmlJW220.AddXmlTag(strXmlId, "USERINFO");
			//　テーブルタグ追加
			xmlJW220.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW220.AddXmlTag("table", "rec", arySetTag);
			arySetTag.clear();

			//------------------------ 機能ID追加（分析値変更確認）  ----------------------------
			xmlJW220.AddXmlTag(strXmlId, "SA591");
			//　テーブルタグ追加
			xmlJW220.AddXmlTag("SA591", "table");

			//配合データ
			ArrayList aryHaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			for ( i=0; i<aryHaigoData.size(); i++  ) {
				MixedData mixedData = (MixedData)aryHaigoData.get(i);
				//レスポンスデータの設定
				arySetTag.add(new String[]{"cd_kaisha",checkNull(mixedData.getIntKaishaCd())});		//会社コード(複数)
				arySetTag.add(new String[]{"cd_genryo",checkNull(mixedData.getStrGenryoCd())});		//原料コード(複数)
				arySetTag.add(new String[]{"ritu_abura",checkNull(mixedData.getDciGanyuritu())});	//油含有率(複数)
				arySetTag.add(new String[]{"ritu_sakusan",checkNull(mixedData.getDciSakusan())});	//酢酸(複数)
				arySetTag.add(new String[]{"ritu_shokuen",checkNull(mixedData.getDciShokuen())});	//食塩(複数)
				arySetTag.add(new String[]{"ritu_sousan",checkNull(mixedData.getDciSosan())});		//総酸(複数)
				arySetTag.add(new String[]{"ritu_msg",checkNull(mixedData.getDciMsg())});		//総酸(複数)
				xmlJW220.AddXmlTag("table", "rec", arySetTag);
				arySetTag.clear();
			}

			//---------------------------------- XML送信  ------------------------------------
			//xmlJW210.dispXml();

			XmlData xmlJW = xmlJW220;
			XmlConnection xmlConnection = new XmlConnection(xmlJW220);
			xmlConnection.setStrAddress(strAjaxUrl);
			xmlConnection.XmlSend();

			//---------------------------------- XML受信  ------------------------------------
			xmlJW220 = xmlConnection.getXdocRes();

			//--------------------------------- Resultデータ  ----------------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW220);
			if ( DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				//検索結果が存在した場合
				/**********************************************************
				 *　T120格納
				 *********************************************************/
				//全体配列取得
				ArrayList t120 = xmlJW220.GetAryTag("SA591");

				//機能配列取得
				ArrayList kinoData = (ArrayList)t120.get(0);

				//テーブル配列取得
				ArrayList tableT120 = (ArrayList)kinoData.get(1);

				//　レコード取得
				for(int p=1; p<tableT120.size(); p++){

					//　１レコード取得
					ArrayList recData = ((ArrayList)((ArrayList)tableT120.get(p)).get(0));
					//　配合データ初期化
					MixedData midtHaigou = new MixedData();

					//　データへ格納
					for(int j=0; j<recData.size(); j++){

						//　項目名取得
						String recNm = ((String[])recData.get(j))[1];
						//　項目値取得
						String recVal = ((String[])recData.get(j))[2];

						/*****************配合データへ値セット*********************/
						//　原料CD
						 if(recNm == "cd_genryo"){
							midtHaigou.setStrGenryoCd(DataCtrl.getInstance().getTrialTblData().checkNullString(recVal));
						//　会社CD
						}if(recNm == "cd_kaisha"){
							midtHaigou.setIntKaishaCd(DataCtrl.getInstance().getTrialTblData().checkNullInt(recVal));
						//　原料名称
						}if(recNm == "nm_genryo"){
							midtHaigou.setStrGenryoNm(DataCtrl.getInstance().getTrialTblData().checkNullString(recVal));
						//　油含有率
						}if(recNm == "ritu_abura"){
							midtHaigou.setDciGanyuritu(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						//　酢酸
						}if(recNm == "ritu_sakusan"){
							midtHaigou.setDciSakusan(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						//　食塩
						}if(recNm == "ritu_shokuen"){
							midtHaigou.setDciShokuen(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						//　総酸
						}if(recNm == "ritu_sousan"){
							midtHaigou.setDciSosan(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						//　ＭＳＧ
						}if(recNm == "ritu_msg"){
							midtHaigou.setDciMsg(DataCtrl.getInstance().getTrialTblData().checkNullDecimal(recVal));
						}
					}
					//　配合データ配列へ追加
					ret.add(midtHaigou);
					recData.clear();
				}
			} else {
				//検索結果が存在しない場合
			}
		}catch(ExceptionBase eb){
			throw eb;
		}catch(Exception e){
			e.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg(" 分析マスタの最新データ取得処理に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;
		}finally{
		}
		return ret;
	}
// ADD end 20121017 QP@20505 No.11

	/************************************************************************************
	 *
	 * 自動保存処理
	 *  : 帳票出力ボタンが押下された時、呼び出される。
	 *   自動で、登録処理を行う
	 *   @param intChkMsg : 登録時メッセージの指定
	 *    [0:登録, 1:自動保存(試作表), 2:自動保存(サンプル説明書), 3:自動保存(栄養計算書)]
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void JidouHozon(int intChkMsg) throws ExceptionBase {

		try {

			//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//配合表の試作明細テーブルの縦スクロールバーの現在位置を取得
			int vHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().getValue();

			//配合表の試作明細テーブルの横スクロールバーの現在位置を取得
			int hHaigoBarVal = tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().getValue();

			//特性値の横スクロールバーの現在位置を取得
			int hTokuseiBarVal = tb.getTrial2Panel().getScroll().getHorizontalScrollBar().getValue();

			//原価試算の横スクロールバーの現在位置を取得
			int hGenkaBarVal = tb.getTrial5Panel().getScroll().getHorizontalScrollBar().getValue();
			//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//モードの取得
			String mode = DataCtrl.getInstance().getParamData().getStrMode();

			//詳細
			if(mode.equals(JwsConstManager.JWS_MODE_0001)){
				//登録（編集）
				conJW040(intChkMsg);

			//新規
			}else if(mode.equals(JwsConstManager.JWS_MODE_0002)){
				//試作コード自動採番
				//conJ010();
				//登録（新規）
				conJW030(intChkMsg);
				//データ設定
				txtShisakuUser.setText(checkNull(PrototypeData.getDciShisakuUser()));
				txtShisakuNen.setText(checkNull(PrototypeData.getDciShisakuYear()));
				txtShisakuOi.setText(checkNull(PrototypeData.getDciShisakuNum()));

				//コピーボタン使用可
				btnTcopy.setEnabled(true);
				btnZcopy.setEnabled(true);
			}

			//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//配合表の試作明細テーブルの縦スクロールバーの位置を設定
			tb.getTrial1Panel().getTrial1().getScrollMain().getVerticalScrollBar().setValue(vHaigoBarVal);

			//配合表の試作明細テーブルの横スクロールバーの位置を設定
			tb.getTrial1Panel().getTrial1().getScrollMain().getHorizontalScrollBar().setValue(hHaigoBarVal);

			//特性値の横スクロールバーの位置を設定
			tb.getTrial2Panel().getScroll().getHorizontalScrollBar().setValue(hTokuseiBarVal);

			//原価試算の横スクロールバーの位置を設定
			tb.getTrial5Panel().getScroll().getHorizontalScrollBar().setValue(hGenkaBarVal);
			//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表① 印刷FGチェック処理が失敗しました");
			ex.setStrErrmsg("配合表 印刷FGチェック処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		}finally{

		}

	}

	/************************************************************************************
	 *
	 * 印刷FGチェック
	 * @param strName : 帳票名
	 * @param intChkCount : 指定件数
	 * @return true : 印刷可, false : 印刷不可
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	private boolean chkInsatuFlg(String strName, int intChkCount) throws ExceptionBase {

		boolean ret = true;
		int intCount = 0;

		try {

			//試作列データを取得し、試作テーブルデータを取得する
			ArrayList aryShisakuRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			for ( int i=0; i<aryShisakuRetu.size(); i++ ) {
				TrialData trialData = (TrialData)aryShisakuRetu.get(i);

				//印刷Flgがチェックされているならば、カウントを進める。
				if ( trialData.getIntInsatuFlg() == 1 ) {
					intCount++;

				}

				//カウントが指定件数を超えた場合
				if ( intCount > intChkCount ) {
					//メッセージを表示し、処理を中断する
					String strMessage = strName + "に出力できるのは" + intChkCount + "列までです";
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);
					ret = false;
					break;

				}

			}

			//印刷FGが選択されていない場合
			if ( intCount == 0 ) {
				//メッセージを表示し、処理を中断する
				String strMessage = "印刷FGを選択して下さい。";
				DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);
				ret = false;

			}

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表① 印刷FGチェック処理が失敗しました");
			ex.setStrErrmsg("配合表 印刷FGチェック処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {

		}
		return ret;

	}

	/************************************************************************************
	 *
	 *   試作表出力
	 *   @author TT katayama
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void conJW120() throws ExceptionBase {

		try {

			//------------------------------ 送信パラメータ格納  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//------------------------------ 送信XMLデータ作成  ------------------------
			xmlJW120 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root追加  ---------------------------------
			xmlJW120.AddXmlTag("","JW120");
			arySetTag.clear();

			//------------------------------ 機能ID追加（USEERINFO）  -------------------
			xmlJW120.AddXmlTag("JW120", "USERINFO");
			//　テーブルタグ追加
			xmlJW120.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW120.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------------ 機能ID追加（SA460）  ------------------------
			xmlJW120.AddXmlTag("JW120", "SA460");
			//　テーブルタグ追加
			xmlJW120.AddXmlTag("SA460", "table");

			//試作リストデータより取得
			Object[] aryShisakuList = DataCtrl.getInstance().getTrialTblData().getAryShisakuList().toArray();

			//注意事項
			String strChuijiko = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getMemoText().getText();
			//常に表示
			boolean isTuneniChk = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getCheckbox().isSelected();

			//　レコード追加
			for ( int i=0; i<aryShisakuList.length; i++ ) {

				//試作リストデータより取得
				PrototypeListData prototypeListData = (PrototypeListData)aryShisakuList[i];

				//印刷Fgの取得
				int intPrintFg = ((TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(prototypeListData.getIntShisakuSeq()).get(0)).getIntInsatuFlg();

				//印刷Fｇにチェックがされている場合、データを追加する
				if ( intPrintFg == 1 ) {

					//データの追加
					arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
					arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
					arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});
					arySetTag.add(new String[]{"seq_shisaku", Integer.toString(prototypeListData.getIntShisakuSeq())});
					arySetTag.add(new String[]{"cd_kotei", Integer.toString(prototypeListData.getIntKoteiCd())});
					arySetTag.add(new String[]{"seq_kotei", Integer.toString(prototypeListData.getIntKoteiSeq())});

					//注意事項「常に表示」にチェックが入っている場合
					if ( isTuneniChk ) {
						arySetTag.add(new String[]{"chuijiko", strChuijiko});

					} else {
						arySetTag.add(new String[]{"chuijiko", ""});

					}
// ADD start 20121019 QP@20505 No.24
					// 特性値項目の切り分け（工程パターンによって表示項目が切り替わるため）
					//現在の設定値取得
					String kotei_ptn = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();
					if(kotei_ptn == null){
						kotei_ptn = "";
					}
					String flgPattern = "0";
					if (kotei_ptn.equals("001") || kotei_ptn.equals("002")) {
						//工程パターンが１液or２液の場合
						flgPattern = "1";
					}
					arySetTag.add(new String[]{"pattern_kotei", flgPattern});
// ADD end 20121019 QP@20505 No.24

					xmlJW120.AddXmlTag("table", "rec", arySetTag);

					//配列初期化
					arySetTag.clear();

				}

			}

			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("JW120送信XML===============================================================");
//			xmlJW120.dispXml();
			xcon = new XmlConnection(xmlJW120);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML受信  ----------------------------------
			xmlJW120 = xcon.getXdocRes();
			//System.out.println();
			//System.out.println("JW120受信XML===============================================================");
			//xmlJW120.dispXml();
			//System.out.println();

			//---------------------------- Resultデータ設定(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW120);

			// Resultデータ.処理結果がtrueの場合、ExceptionBaseをThrowする
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
			}

			//------------------------------ データ設定(SA460) --------------------------------
			//ダウンロードパスクラス
			DownloadPathData downloadPathData = new DownloadPathData();
			downloadPathData.setDownloadPathData(xmlJW120, "SA460");

			//URLコネクションクラス
			UrlConnection urlConnection = new UrlConnection();

			//ダウンロードパスを送り、ファイルダウンロード画面で開く
			urlConnection.urlFileDownLoad( downloadPathData.getStrDownloadPath());

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表① 試作表出力処理が失敗しました");
			ex.setStrErrmsg("配合表 試作表出力処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {

		}

	}

	/************************************************************************************
	 *
	 *   サンプル説明書出力
	 *   @author TT katayama
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void conJW130() throws ExceptionBase {

		try {

			//------------------------------ 送信パラメータ格納  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//------------------------------ 送信XMLデータ作成  ------------------------
			xmlJW130 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root追加  ---------------------------------
			xmlJW130.AddXmlTag("","JW130");
			arySetTag.clear();

			//------------------------------ 機能ID追加（USEERINFO）  -------------------
			xmlJW130.AddXmlTag("JW130", "USERINFO");
			//　テーブルタグ追加
			xmlJW130.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW130.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------------ 機能ID追加（SA450）  ------------------------
			xmlJW130.AddXmlTag("JW130", "SA450");
			//　テーブルタグ追加
			xmlJW130.AddXmlTag("SA450", "table");
			//　レコード追加 : 試作テーブルのデータを設定
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});

			//試作列よりデータを取得
			int intShisakuSeqCount = 0;
			ArrayList aryTrialData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);

			//試作SEQ格納変数
			String[] aryShisakuSeq = new String[]{"","","",""};

			for ( int i=0; i<aryTrialData.size(); i++ ) {

				//試作テーブルデータの取得
				TrialData trialData = (TrialData)aryTrialData.get(i);

				//印刷Fgによる判定
				if ( trialData.getIntInsatuFlg() == 1 ) {

					//試作SEQ取得
					String strShisakuSeq = Integer.toString(trialData.getIntShisakuSeq());

					//試作SEQを格納
					aryShisakuSeq[intShisakuSeqCount] = strShisakuSeq;

					intShisakuSeqCount++;

				}

			}

			//試作SEQ設定
			arySetTag.add(new String[]{"seq_shisaku1", aryShisakuSeq[0]});
			arySetTag.add(new String[]{"seq_shisaku2", aryShisakuSeq[1]});
			arySetTag.add(new String[]{"seq_shisaku3", aryShisakuSeq[2]});
			arySetTag.add(new String[]{"seq_shisaku4", aryShisakuSeq[3]});

			//table設定
			xmlJW130.AddXmlTag("table", "rec", arySetTag);

			//配列初期化
			arySetTag.clear();

			//----------------------------------- XML送信  ----------------------------------
			System.out.println("JW130送信XML===============================================================");
			xmlJW130.dispXml();
			xcon = new XmlConnection(xmlJW130);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML受信  ----------------------------------
			xmlJW130 = xcon.getXdocRes();
//			System.out.println();
//			System.out.println("JW130受信XML===============================================================");
//			xmlJW130.dispXml();
//			System.out.println();

			//---------------------------- Resultデータ設定(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW130);

			// Resultデータ.処理結果がtrueの場合、ExceptionBaseをThrowする
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				throw new ExceptionBase();
			}

			//------------------------------ データ設定(SA450) --------------------------------
			//ダウンロードパスクラス
			DownloadPathData downloadPathData = new DownloadPathData();
			downloadPathData.setDownloadPathData(xmlJW130, "SA450");

			//URLコネクションクラス
			UrlConnection urlConnection = new UrlConnection();

			//ダウンロードパスを送り、ファイルダウンロード画面で開く
			urlConnection.urlFileDownLoad( downloadPathData.getStrDownloadPath());

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表① サンプル説明書出力処理が失敗しました");
			ex.setStrErrmsg("配合表 サンプル説明書出力処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {


		}

	}

	/************************************************************************************
	 *
	 * 【JW740】 栄養計算書出力 送信XMLデータ作成
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	private void conJW740(int seq) throws ExceptionBase {

		try {

			//------------------------------ 送信パラメータ格納  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
//			String strShisakuSeq = Integer.toString(DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().getShisakuSeq());
			String strShisakuSeq = Integer.toString(seq);

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
		int sortNo = 0;
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

	/**
	 * 原価試算マーク設定処理
	 * @throws ExceptionBase
	 */
	private void setGenkaShisanMark() throws ExceptionBase {

		try {

			//原価原料データの取得
			ArrayList aryCostMaterial = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);

			for ( int i=0; i<aryCostMaterial.size(); i++ ) {

				//原価原料データ
				CostMaterialData costMaterialData = (CostMaterialData)aryCostMaterial.get(i);

				//試作SEQの取得
				int intSeq = costMaterialData.getIntShisakuSeq();

				//試作テーブルの取得
				TrialData trialData = (TrialData)(DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intSeq).get(0));

				//原価試算Noの取得
				int intGenkaShisanNo = trialData.getFlg_init();

				//---------------------------- 原価試算マーク更新 ----------------------------------
				TableBase lh = tb.getTrial1Panel().getTrial1().getListHeader();
				for(int j=0; j<lh.getColumnCount(); j++){

					//コンポーネント取得
					MiddleCellEditor selectMc = (MiddleCellEditor)lh.getCellEditor(0, j);
					DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(0);
					CheckboxBase cb = (CheckboxBase)selectDc.getComponent();
					String Mark = cb.getText();
					int sisakuSeq = Integer.parseInt(cb.getPk1());

					//記号削除
					if(Mark != null && Mark.length() > 0){
						Mark = Mark.replace(JwsConstManager.JWS_MARK_0004.toCharArray()[0], ' ');
						Mark = Mark.trim();
					}

					if(sisakuSeq == costMaterialData.getIntShisakuSeq()){

						//原価試算Noが0より大きい場合、マークを設定する
						if ( intGenkaShisanNo > 0 ) {
							cb.setText(Mark + " " + JwsConstManager.JWS_MARK_0004);

						}

					}

				}

				//再表示
				tb.getTrial1Panel().getTrial1().setHeaderShisakuER();

			}

		}catch(ExceptionBase e) {
			throw e;

		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原価試算マーク設定処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{

		}

	}

	/**
	 * 比重チェック
	 * @throws ExceptionBase
	 */
	public boolean chkHiju() throws ExceptionBase{

		boolean ret = true;

		try{

			//試作品情報取得
			PrototypeData PrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();

			//容量単位が「ml」の場合
			String tani = PrototypeData.getStrTani();
			if(tani != null && tani.equals(JwsConstManager.JWS_CD_TANI)){

				//試作列データ配列取得
				ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);

				//比重未入力チェック
				for(int i=0; i<aryRetu.size(); i++){

					//試作列データ取得
					TrialData TrialData = (TrialData)aryRetu.get(i);

					//比重取得
					String hiju = TrialData.getStrHizyu();

					//未入力の場合
					if(hiju == null){

						ret = false;

					}
				}
			}
		}
		catch(Exception e){

			e.printStackTrace();

		}
		finally{

		}

		return ret;

	}


	/**
	 * 整数チェック
	 * @param strData : チェックデータ
	 * @param komokuNm : 項目名
	 * @param flg : 0=新規,1=編集
	 * @throws ExceptionBase
	 */
	public void seisuCheck(String strData, String komokuNm, int flg) throws ExceptionBase{

			//比重の値が数値の場合
			try{

				//BigDecimal型に変換（数値チェック）
				BigDecimal chkDeci = new BigDecimal(strData);

				try{
					long chkInt = Long.parseLong(strData);

				}catch(Exception e){

					//エラーメッセージ生成
					String errMsg;

					//新規登録の場合
					if(flg == 0){
						errMsg = "【試作データ画面】 登録（新規）が、失敗しました。\n";
					}
					//編集登録の場合
					else{
						errMsg = "【試作データ画面】 登録（編集）が、失敗しました。\n";
					}

					errMsg += komokuNm+"は整数で入力して下さい。";

					//エラー設定
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					ex.setStrErrmsg(errMsg);
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg("");

					throw ex;
				}

			}
			//桁数チェックエラーの場合
			catch(ExceptionBase eb){

				throw eb;

			}
			//比重の値が数値でない場合
			catch(Exception e){

				//エラーメッセージ生成
				String errMsg;

				//新規登録の場合
				if(flg == 0){
					errMsg = "【試作データ画面】 登録（新規）が、失敗しました。\n";
				}
				//編集登録の場合
				else{
					errMsg = "【試作データ画面】 登録（編集）が、失敗しました。\n";
				}

				errMsg += komokuNm+"は整数で入力して下さい。";

				//エラー設定
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				ex.setStrErrmsg(errMsg);
				ex.setStrErrShori(this.getClass().getName());
				ex.setStrMsgNo("");
				ex.setStrSystemMsg("");

				throw ex;



			}

	}

	/**
	 * 小数桁数チェック
	 * @param strData : チェックデータ
	 * @param iKetasu : 指定桁数
	 * @param komokuNm : 項目名
	 * @param flg : 0=新規,1=編集
	 * @throws ExceptionBase
	 */
	public void ShosuCheck(String strData, int iKetasu, String komokuNm, int flg) throws ExceptionBase{

		//値が数値の場合
		try{

			//BigDecimal型に変換（数値チェック）
			BigDecimal chkDeci = new BigDecimal(strData);

			//小数点「.」が存在する場合にチェック
			if(strData.indexOf(".") > 0){

				//桁数チェック
				int iSize = strData.substring(strData.indexOf(".") + 1).length();

				if (iSize != 0 && iKetasu < iSize){

					//エラーメッセージ生成
					String errMsg;

					//新規登録の場合
					if(flg == 0){
						errMsg = "【試作データ画面】 登録（新規）が、失敗しました。\n";
					}
					//編集登録の場合
					else{
						errMsg = "【試作データ画面】 登録（編集）が、失敗しました。\n";
					}

					errMsg += komokuNm+"は小数部"+iKetasu+"桁以内で入力して下さい。";

					//エラー設定
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					ex.setStrErrmsg(errMsg);
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg("");

					throw ex;
				}

			}

		}
		//桁数チェックエラーの場合
		catch(ExceptionBase eb){

			throw eb;

		}
		//比重の値が数値でない場合
		catch(Exception e){

			//何もしない

		}
	}

	/**
	 * 原価試算(試作表⑤)　原価依頼チェックボックス編集不可操作
	 * @throws ExceptionBase
	 */
	private void setGenkaIrai_false() {

		//原価試算(試作表⑤)テーブル取得
		TableBase GenkaTable = tb.getTrial5Panel().getTable();

		//原価試算(試作表⑤)テーブル　列ループ
		for(int i=0; i<GenkaTable.getColumnCount(); i++){

			//コンポーネント取得
			MiddleCellEditor selectMc = (MiddleCellEditor)GenkaTable.getCellEditor(2, i);
			DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(2);

			//原料コードの場合
			if(selectDc.getComponent() instanceof CheckboxBase){

				//依頼チェックボックス取得
				CheckboxBase CheckIrai = (CheckboxBase)selectDc.getComponent();

				//依頼した列の依頼チェックボックスは編集不可
				if(CheckIrai.isSelected()){
					CheckIrai.setEnabled(false);
				}
			}
		}
	}

	/**
	 * 原価試算(試作表⑤)　原価依頼チェックボックス編集可操作
	 * @throws ExceptionBase
	 */
	private void setGenkaIrai_true() {

		//原価試算(試作表⑤)テーブル取得
		TableBase GenkaTable = tb.getTrial5Panel().getTable();

		//原価試算(試作表⑤)テーブル　列ループ
		for(int i=0; i<GenkaTable.getColumnCount(); i++){

			//コンポーネント取得
			MiddleCellEditor selectMc = (MiddleCellEditor)GenkaTable.getCellEditor(2, i);
			DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(2);

			//原料コードの場合
			if(selectDc.getComponent() instanceof CheckboxBase){

				//依頼チェックボックス取得
				CheckboxBase CheckIrai = (CheckboxBase)selectDc.getComponent();

				//依頼チェックボックス編集可
				CheckIrai.setEnabled(true);
				GenkaTable.setValueAt("false", 2, i);
			}
		}
	}


	public ButtonBase getBtnShuryo() {
		return btnShuryo;
	}

	public void setBtnShuryo(ButtonBase btnShuryo) {
		this.btnShuryo = btnShuryo;
	}

	//add start -------------------------------------------------------------------------------
	//QP@00412_シサクイック改良 No.33,34
	public void dispHenshuOkFg() throws ExceptionBase {

		JwsConstManager.JWS_FLG_DISP = true;

		//値が数値の場合
		try{
			//データ設定（JW010）
			this.conJW010();

			//編集可能フラグ設定（登録時）
			DataCtrl.getInstance().getTrialTblData().setShisakuListHenshuOkFg();

	    	//再表示
	    	int sel = tb.getSelectedIndex();
	    	setPanelData();
	    	tb.setSelectedIndex(sel);

	    	//変更フラグ初期化
	    	DataCtrl.getInstance().getTrialTblData().setHenkouFg(false);
		}catch(ExceptionBase e) {
			throw e;

		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("登録時の再表示処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{

		}
	}
	//add end   -------------------------------------------------------------------------------

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------
	/**
	 * 工程パターン変更時の合計仕上重量初期化
	 * @throws ExceptionBase
	 */
	private void clearSiagariZyuryo(String kotei_ptn_value){
		try{
			//合計仕上がり重量初期化
			if(kotei_ptn_value.equals(JwsConstManager.JWS_KOTEITYPE_3)){

				//試作リスト取得
				TableBase tbListMeisai = tb.getTrial1Panel().getTrial1().getListMeisai();
				TableBase tbListHeader = tb.getTrial1Panel().getTrial1().getListHeader();
				int gokeiShiagariGyo = tb.getTrial1Panel().getTrial1().getHaigoMeisai().getRowCount()-8;
				for(int i=0; i<tbListHeader.getColumnCount(); i++){

					//試作SEQ取得
					MiddleCellEditor selectMcKey = (MiddleCellEditor)tbListHeader.getCellEditor(0, i);
					DefaultCellEditor selectDcKey = (DefaultCellEditor)selectMcKey.getTableCellEditor(0);
					CheckboxBase cb = (CheckboxBase)selectDcKey.getComponent();
					int shisakuSeq = Integer.parseInt(cb.getPk1());

					//表示値を初期化
					tbListMeisai.setValueAt("", gokeiShiagariGyo, i);

					//データ初期化
					TrialData trialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(shisakuSeq).get(0);
					trialData.setDciShiagari(null);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

	/**
	 * キャンセル依頼確認メッセージ
	 * @throws ExceptionBase
	 */
	private boolean cancelMsg(){
		boolean ret = false;
		try{
			//キャンセル列保持
			String strCancelNo = "";

			//試作列データ取得
			ArrayList addRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			for(int i=0; i<addRetu.size(); i++){

				//試作データ取得
				TrialData TrialData = (TrialData)addRetu.get(i);

				//キャンセルFg
				String cancelFg = checkNull(TrialData.getFlg_cancel());

				//キャンセルFgが１の場合
				if(cancelFg.equals("1")){
					strCancelNo = strCancelNo + "\n" + TrialData.getStrSampleNo();
				}
			}

			//1件もない場合
			if(strCancelNo.equals("")){
				ret = true;
			}
			//1件以上ある場合
			else{
				//ダイアログコンポーネント設定
				JOptionPane jp = new JOptionPane();
				//確認ダイアログ表示
				int option = jp.showConfirmDialog(
						jp.getRootPane(),
						"下記サンプルNoの原価試算依頼を取り消します。よろしいですか？" + strCancelNo
						, "確認メッセージ"
						,JOptionPane.YES_NO_OPTION
						,JOptionPane.PLAIN_MESSAGE
					);

				//「はい」押下
			    if (option == JOptionPane.YES_OPTION){
			    	ret = true;

			    //「いいえ」押下
			    }else if (option == JOptionPane.NO_OPTION){
			    	ret = false;
			    }
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}

}
