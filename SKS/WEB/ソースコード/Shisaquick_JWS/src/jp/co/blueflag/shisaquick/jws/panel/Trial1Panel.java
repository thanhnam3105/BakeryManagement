package jp.co.blueflag.shisaquick.jws.panel;

//------------------------------ 基本機能　Listインポート  -----------------------------------
import java.math.BigDecimal;
import java.util.ArrayList;
//----------------------------------- AWT　インポート  --------------------------------------
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//---------------------------------- Swing　インポート  -------------------------------------
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
//--------------------------------- シサクイック　インポート  -----------------------------------
import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.CostMaterialData;
import jp.co.blueflag.shisaquick.jws.base.ManufacturingData;
import jp.co.blueflag.shisaquick.jws.base.MixedData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeListData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextFieldCellEditor;
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
import jp.co.blueflag.shisaquick.jws.disp.*;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.table.PrototypeListTable;
import jp.co.blueflag.shisaquick.jws.table.Trial1Table;

/*****************************************************************************************
 *
 *   配合表(試作表①)クラス
 *   @author TT nishigawa
 *
 *****************************************************************************************/
public class Trial1Panel extends PanelBase {

	//--------------------------------- 画面メンバ  ----------------------------------------
	private MaterialSubDisp materialSubDisp = null;
	private PrototypeListSubDisp prototypeListSubDisp = null;

	//-------------------------------- テーブルメンバ  ---------------------------------------
	private TableBase table = new TableBase();
	private Trial1Table Trial1;

	private ButtonBase btnSample;			//サンプル説明書ボタン
	private ButtonBase btnShisakuHyo;		//試作表ボタン
	private ButtonBase btnEiyoKeisan;		//栄養計算書ボタン

	//-------------------------------- データ&通信オブジェクト  -------------------------------
	private XmlConnection xcon;
	private XmlData xmlJW120;				//試作表出力
	private XmlData xmlJW130;				//サンプル説明書出力

	//--------------------------------- エラー管理  ----------------------------------------
	private ExceptionBase ex;


	/************************************************************************************
	 *
	 *   配合表(試作表①)コンストラクタ
	 *    : 配合表(試作表①)画面の初期化処理
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public Trial1Panel() throws ExceptionBase {
		super();
		this.setLayout(null);
		this.setBackground(Color.WHITE);

		try {
			//画面インスタンス生成
			ArrayList aList = DataCtrl.getInstance().getUserMstData().getAryAuthData();
			for (int i = 0; i < aList.size(); i++) {
				String[] items = (String[]) aList.get(i);

				//原料一覧画面の使用権限があるかチェックする
				if (items[0].toString().equals("150")) {
					materialSubDisp = new MaterialSubDisp("原料一覧画面");
					break;
				}
			}

			prototypeListSubDisp = new PrototypeListSubDisp("試作列追加画面");

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作データ画面 試作表① 初期化処理が失敗しました");
			ex.setStrErrmsg("試作データ画面 配合表 初期化処理が失敗しました");
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
	 *   画面初期化処理
	 *    : 配合表(試作表①)画面の初期化処理を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void init() throws ExceptionBase{
		try{
			this.removeAll();

			//製造工程ボタン
			ButtonBase btnSeizoKotei = new ButtonBase();
			btnSeizoKotei.setBounds(980, 10, 17, 99);
			btnSeizoKotei.addActionListener(this.getActionEvent());
			btnSeizoKotei.setHorizontalAlignment(SwingConstants.CENTER);
			btnSeizoKotei.setMargin(new Insets(0, 0, 0, 0));
			btnSeizoKotei.setText("<html>製造工程");
			btnSeizoKotei.setActionCommand("btnSeizoKotei");

			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0032, DataCtrl.getInstance().getParamData().getStrMode())){
				btnSeizoKotei.setEnabled(false);
			}
			this.add(btnSeizoKotei);

			//配合表(試作表①)テーブル
			Trial1 = new Trial1Table();
			Trial1.setBounds(5,10,1010,526);
			this.add(Trial1);

			Runtime.getRuntime().gc();

			//テーブル操作ボタン（上段）
			//原料一覧
			ButtonBase btnGenryoList = new ButtonBase("原料一覧");
			btnGenryoList.addActionListener(this.getActionEvent());
			btnGenryoList.setActionCommand("btnGenryoList");
			btnGenryoList.setMargin(new Insets(0, 0, 0, 0));
			btnGenryoList.setBounds(5, 540, 80, 20);
			//権限チェック
			ArrayList Auth = DataCtrl.getInstance().getUserMstData().getAryAuthData();
			boolean mateChk = false;
			for(int i=0;i<Auth.size();i++){
				String[] strDispAuth = (String[])Auth.get(i);
				if(strDispAuth[0].equals("150")){
					//閲覧権限の場合
					if(strDispAuth[1].equals("10")){
						mateChk = true;
					}
				}
			}
			//モード編集
			if(mateChk){
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0033, DataCtrl.getInstance().getParamData().getStrMode())){
					btnGenryoList.setEnabled(false);
				}
			}else{
				btnGenryoList.setEnabled(false);
			}
			this.add(btnGenryoList);

			//工程挿入
			ButtonBase btnKoteiIns = new ButtonBase("工程挿入");
			btnKoteiIns.addActionListener(this.getActionEvent());
			btnKoteiIns.setActionCommand("btnKoteiIns");
			btnKoteiIns.setMargin(new Insets(0, 0, 0, 0));
			btnKoteiIns.setBounds(100, 540, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0035, DataCtrl.getInstance().getParamData().getStrMode())){
				btnKoteiIns.setEnabled(false);
			}
			this.add(btnKoteiIns);

			//工程移動(↑)
			ButtonBase btnKoteiMove_Up = new ButtonBase("工程移動(↑)");
			btnKoteiMove_Up.setMargin(new Insets(0, 0, 0, 0));
			btnKoteiMove_Up.addActionListener(this.getActionEvent());
			btnKoteiMove_Up.setActionCommand("btnKoteiMove_Up");
			btnKoteiMove_Up.setBounds(180, 540, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0036, DataCtrl.getInstance().getParamData().getStrMode())){
				btnKoteiMove_Up.setEnabled(false);
			}
			this.add(btnKoteiMove_Up);

			//工程移動(↓)
			ButtonBase btnKoteiMove_Down = new ButtonBase("工程移動(↓)");
			btnKoteiMove_Down.setMargin(new Insets(0, 0, 0, 0));
			btnKoteiMove_Down.addActionListener(this.getActionEvent());
			btnKoteiMove_Down.setActionCommand("btnKoteiMove_Down");
			btnKoteiMove_Down.setBounds(260, 540, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0037, DataCtrl.getInstance().getParamData().getStrMode())){
				btnKoteiMove_Down.setEnabled(false);
			}
			this.add(btnKoteiMove_Down);

			//工程削除
			ButtonBase btnKoteiDel = new ButtonBase("工程削除");
			btnKoteiDel.setMargin(new Insets(0, 0, 0, 0));
			btnKoteiDel.addActionListener(this.getActionEvent());
			btnKoteiDel.setActionCommand("btnKoteiDel");
			btnKoteiDel.setBounds(340, 540, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0038, DataCtrl.getInstance().getParamData().getStrMode())){
				btnKoteiDel.setEnabled(false);
			}
			this.add(btnKoteiDel);

			//試作列追加
			ButtonBase btnShisakuIns = new ButtonBase("試作列追加");
			btnShisakuIns.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuIns.addActionListener(this.getActionEvent());
			btnShisakuIns.setActionCommand("btnShisakuIns");
			btnShisakuIns.setBounds(435, 540, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0043, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuIns.setEnabled(false);
			}
			this.add(btnShisakuIns);

			//試作列削除
			ButtonBase btnShisakuDel = new ButtonBase("試作列削除");
			btnShisakuDel.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuDel.addActionListener(this.getActionEvent());
			btnShisakuDel.setActionCommand("btnShisakuDel");
			btnShisakuDel.setBounds(515, 540, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0044, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuDel.setEnabled(false);
			}
			this.add(btnShisakuDel);

			//試作列ｺﾋﾟｰ
			ButtonBase btnShisakuCopy = new ButtonBase("試作列ｺﾋﾟｰ");
			btnShisakuCopy.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuCopy.addActionListener(this.getActionEvent());
			btnShisakuCopy.setActionCommand("btnShisakuCopy");
			btnShisakuCopy.setBounds(595, 540, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0045, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuCopy.setEnabled(false);
			}
			this.add(btnShisakuCopy);

			//ｻﾝﾌﾟﾙ説明書
			btnSample = new ButtonBase("ｻﾝﾌﾟﾙ説明書");
			btnSample.setMargin(new Insets(0, 0, 0, 0));
			btnSample.setBounds(690, 540, 80, 20);
//			btnSample.addActionListener(this.getActionEvent());
//			btnSample.setActionCommand("sampleSetumei");
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0049, DataCtrl.getInstance().getParamData().getStrMode())){
				btnSample.setEnabled(false);
			}
			this.add(btnSample);

			//テーブル操作ボタン（下段）
			ButtonBase btnGenryoBunseki = new ButtonBase("原料分析");
			btnGenryoBunseki.setBounds(5, 560, 80, 20);
			btnGenryoBunseki.addActionListener(this.getActionEvent());
			btnGenryoBunseki.setActionCommand("btnGenryoBunseki");
			//権限チェック
			boolean bunsekiChk = false;
			for(int i=0;i<Auth.size();i++){
				String[] strDispAuth = (String[])Auth.get(i);
				if(strDispAuth[0].equals("160")){
					//閲覧権限の場合
					if(strDispAuth[1].equals("10")){
						bunsekiChk = true;
					}else if(strDispAuth[1].equals("20")){
						bunsekiChk = true;
					}
				}
			}
			if(bunsekiChk){
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0034, DataCtrl.getInstance().getParamData().getStrMode())){
					btnGenryoBunseki.setEnabled(false);
				}
			}else{
				btnGenryoBunseki.setEnabled(false);
			}
			this.add(btnGenryoBunseki);

			//原料挿入
			ButtonBase btnGenryoIns = new ButtonBase("原料挿入");
			btnGenryoIns.setMargin(new Insets(0, 0, 0, 0));
			btnGenryoIns.addActionListener(this.getActionEvent());
			btnGenryoIns.setActionCommand("btnGenryoIns");
			btnGenryoIns.setBounds(100, 560, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0039, DataCtrl.getInstance().getParamData().getStrMode())){
				btnGenryoIns.setEnabled(false);
			}
			this.add(btnGenryoIns);

			//原料移動(↑)
			ButtonBase btnGenryoMove_Up = new ButtonBase("原料移動(↑)");
			btnGenryoMove_Up.setMargin(new Insets(0, 0, 0, 0));
			btnGenryoMove_Up.addActionListener(this.getActionEvent());
			btnGenryoMove_Up.setActionCommand("btnGenryoMove_Up");
			btnGenryoMove_Up.setBounds(180, 560, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0040, DataCtrl.getInstance().getParamData().getStrMode())){
				btnGenryoMove_Up.setEnabled(false);
			}
			this.add(btnGenryoMove_Up);

			//原料移動(↓)
			ButtonBase btnGenryoMove_Down = new ButtonBase("原料移動(↓)");
			btnGenryoMove_Down.setMargin(new Insets(0, 0, 0, 0));
			btnGenryoMove_Down.addActionListener(this.getActionEvent());
			btnGenryoMove_Down.setActionCommand("btnGenryoMove_Down");
			btnGenryoMove_Down.setBounds(260, 560, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0041, DataCtrl.getInstance().getParamData().getStrMode())){
				btnGenryoMove_Down.setEnabled(false);
			}
			this.add(btnGenryoMove_Down);

			//原料削除
			ButtonBase btnGenryoDel = new ButtonBase("原料削除");
			btnGenryoDel.setMargin(new Insets(0, 0, 0, 0));
			btnGenryoDel.addActionListener(this.getActionEvent());
			btnGenryoDel.setActionCommand("btnGenryoDel");
			btnGenryoDel.setBounds(340, 560, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0042, DataCtrl.getInstance().getParamData().getStrMode())){
				btnGenryoDel.setEnabled(false);
			}
			this.add(btnGenryoDel);

			//試作列移動(←)
			ButtonBase btnShisakuMove_Left = new ButtonBase("試作列移動(←)");
			btnShisakuMove_Left.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuMove_Left.addActionListener(this.getActionEvent());
			btnShisakuMove_Left.setActionCommand("btnShisakuMove_Left");
			btnShisakuMove_Left.setBounds(435, 560, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0046, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuMove_Left.setEnabled(false);
			}
			this.add(btnShisakuMove_Left);

			//試作列移動(→)
			ButtonBase btnShisakuMove_Right = new ButtonBase("試作列移動(→)");
			btnShisakuMove_Right.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuMove_Right.addActionListener(this.getActionEvent());
			btnShisakuMove_Right.setActionCommand("btnShisakuMove_Right");
			btnShisakuMove_Right.setBounds(515, 560, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0047, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuMove_Right.setEnabled(false);
			}
			this.add(btnShisakuMove_Right);

			//試作表出力
			btnShisakuHyo = new ButtonBase("試作表出力");
			btnShisakuHyo.setMargin(new Insets(0, 0, 0, 0));
			btnShisakuHyo.setBounds(595, 560, 80, 20);
//			btnShisakuHyo.addActionListener(this.getActionEvent());
//			btnShisakuHyo.setActionCommand("shisakuHyo");
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0048, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisakuHyo.setEnabled(false);
			}
			this.add(btnShisakuHyo);

			//栄養計算書出力
			btnEiyoKeisan = new ButtonBase("栄養計算書");
			btnEiyoKeisan.setMargin(new Insets(0, 0, 0, 0));
			btnEiyoKeisan.setBounds(690, 560, 80, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0126, DataCtrl.getInstance().getParamData().getStrMode())){
				btnEiyoKeisan.setEnabled(false);
			}
			this.add(btnEiyoKeisan);


			//製造工程画面設定
			DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().addControl();
			DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel().init();

			//----------------------------- 製造工程枠イベント追加 -------------------------------
			//製造工程パネル取得
			ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();
			//製造工程新規ボタン
			pb.getButton()[0].addActionListener(this.getActionEvent());
			pb.getButton()[0].setActionCommand("sinki");

			//製造工程更新ボタン
			pb.getButton()[1].addActionListener(this.getActionEvent());
			pb.getButton()[1].setActionCommand("kosin");

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作データ画面 試作表① 初期化処理が失敗しました");
			ex.setStrErrmsg("試作データ画面 配合表 初期化処理が失敗しました");
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
						Trial1.AutoCopyKeisan();
//add end   -------------------------------------------------------------------------------

						//------------------------ 選択工程＆原料設定  ------------------------------
						TableBase selectHaigoMeisai = Trial1.getHaigoMeisai();
						ArrayList aryGenryoCheck = new ArrayList();
						//工程選択初期化
						Trial1.setKCheck(new int[]{-1,-1});
						for(int i=0; i<selectHaigoMeisai.getRowCount(); i++){
							//工程選択設定
							MiddleCellEditor mcKotei = (MiddleCellEditor)selectHaigoMeisai.getCellEditor(i, 0);
							DefaultCellEditor tcKotei = (DefaultCellEditor)mcKotei.getTableCellEditor(i);
							if(((JComponent)tcKotei.getComponent()) instanceof CheckboxBase){
								CheckboxBase chkKotei = (CheckboxBase)tcKotei.getComponent();
								if(chkKotei.isSelected()){
									//行・列番号取得
									int[] kcheck = new int[2];
									kcheck[0] = i;    //行番号
									kcheck[1] = 0; //列番号
									//選択行列設定
									Trial1.setKCheck(kcheck);
								}
							}
							//原料選択設定
							MiddleCellEditor mcGenryo = (MiddleCellEditor)selectHaigoMeisai.getCellEditor(i, 2);
							DefaultCellEditor tcGenryo = (DefaultCellEditor)mcGenryo.getTableCellEditor(i);
							if(((JComponent)tcGenryo.getComponent()) instanceof CheckboxBase){
								CheckboxBase chkGenryo = (CheckboxBase)tcGenryo.getComponent();
								if(chkGenryo.isSelected()){
									//行・列番号取得
									int[] gcheck = new int[2];
									gcheck[0] = i;    //行番号
									gcheck[1] = 2; //列番号
									//選択行列設定
									aryGenryoCheck.add(gcheck);
								}
							}
						}
						Trial1.setAryGenryoCheck(aryGenryoCheck);

						//-------------------------- 選択試作列設定  --------------------------------
						TableBase ListHeader = Trial1.getListHeader();
						int selectCol = -1;
						for(int i=0; i<ListHeader.getColumnCount(); i++){
							//コンポーネント取得
							MiddleCellEditor mcShisaku = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
							DefaultCellEditor tcShisaku = (DefaultCellEditor)mcShisaku.getTableCellEditor(0);
							CheckboxBase chkShisaku = (CheckboxBase)tcShisaku.getComponent();
							if(chkShisaku.isSelected()){
								//列番号取得
								selectCol = i;
							}
						}
						
						// 【KPX1500671】 ADD start
						// 原料一覧・原料分析・製造工程ボタンイベントは変更フラグ変更を除外する
						if ( (event_name != "btnGenryoBunseki") &&  (event_name != "btnGenryoList") && (event_name != "btnSeizoKotei") 
								&& (event_name != "sinki") && (event_name != "kosin")) {
							//データ変更フラグＯＮ
							DataCtrl.getInstance().getTrialTblData().setHenkouFg(true);
						}
						// 【KPX1500671】 ADD end

					    //----------- 試作分析データ確認サブ画面 ボタン クリック時の処理  ---------------------
						if ( event_name == "btnGenryoBunseki") {
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.5
//							Trial1.getAnalysinSubDisp().setVisible(true);
//							Trial1.getAnalysinSubDisp().getAnalysisPanel().init();

							//試作分析データ確認サブ画面　再表示
							AnalysinSubDisp as = new AnalysinSubDisp("試作分析データ確認画面");
							Trial1.getAnalysinSubDisp().dispose();
							Trial1.setAnalysinSubDisp(as);
							Trial1.getAnalysinSubDisp().setVisible(true);
							Trial1.getAnalysinSubDisp().getAnalysisPanel().init();

							//試作品データ 会社＆部署コード取得
							PrototypeData pd = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();
							String strSelKaisha = Integer.toString(pd.getIntKaishacd());
							String strSelBusho = Integer.toString(pd.getIntKojoco());

							//会社＆部署コンボボックス取得
							AnalysisPanel ap = Trial1.getAnalysinSubDisp().getAnalysisPanel();
							ComboBase cmbKaisha = ap.getKaishaComb();
							ComboBase cmbKojo = ap.getBushoComb();

							//試作分析データ確認サブ画面内の会社データ取得
							ArrayList aryKaishaCd = ap.getKaishaData().getArtKaishaCd();
							ArrayList aryKaishaNm = ap.getKaishaData().getAryKaishaNm();

							//会社データ数分ループ
							for(int ki=0; ki<aryKaishaCd.size(); ki++){

								//会社コード、会社名取得
								String strkaishaCd = (String)aryKaishaCd.get(ki);
								String strkaishaNm = (String)aryKaishaNm.get(ki);

								//同一の会社コードが存在する場合
								if(strSelKaisha.equals(strkaishaCd)){

									//会社コンボボックス選択
									cmbKaisha.setSelectedItem(strkaishaNm);

									//部署データ取得
									BushoData bd = ap.getBushoData();

									//原料一覧画面内の部署データ取得
									ArrayList aryBushoCd = ap.getBushoData().getArtBushoCd();
									ArrayList aryBushoNm = ap.getBushoData().getAryBushoNm();

									//部署データ数分ループ
									for(int kj=0; kj<aryBushoCd.size(); kj++){

										//部署コード、部署名取得
										String strBushoCd = (String)aryBushoCd.get(kj);
										String strBushoNm = (String)aryBushoNm.get(kj);

										//同一の部署コードが存在する場合
										if(strSelBusho.equals(strBushoCd)){

											//部署コンボボックス選択
											cmbKojo.setSelectedItem(strBushoNm);

											//ループ処理終了
											break;
										}

									}

									//ループ処理終了
									break;
								}
							}

							if(strSelKaisha.equals(JwsConstManager.JWS_CD_DAIHYO_KAISHA)
									&& strSelBusho.equals(JwsConstManager.JWS_CD_DAIHYO_KOJO)){
								ap.getShiyoFlgBtn()[0].setEnabled(false);
								ap.getShiyoFlgBtn()[1].setEnabled(true);
								ap.getShiyoFlgBtn()[1].setSelected(true);
							}
							else{
								ap.getShiyoFlgBtn()[0].setEnabled(true);
								ap.getShiyoFlgBtn()[1].setEnabled(true);
								ap.getShiyoFlgBtn()[0].setSelected(true);
							}
//mod end --------------------------------------------------------------------------------------
						}

						//--------------- 原料一覧サブ画面 ボタン クリック時の処理  -------------------------
						else if ( event_name == "btnGenryoList") {

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.5
							materialSubDisp.dispose();
							materialSubDisp = new MaterialSubDisp("原料一覧画面");
//mod end --------------------------------------------------------------------------------------

							//試作品データ取得
							PrototypeData pd = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();

							//会社＆部署コード取得
							String strSelKaisha = Integer.toString(pd.getIntKaishacd());
							String strSelBusho = Integer.toString(pd.getIntKojoco());

							//原料一覧パネル取得
							MaterialPanel mp = materialSubDisp.getMaterialPanel();

							//会社＆部署コンボボックス取得
							ComboBase cmbKaisha = mp.getKaishaComb();
							ComboBase cmbKojo = mp.getKojoComb();


							//原料一覧画面内の会社データ取得
							ArrayList aryKaishaCd = mp.getKaishaData().getArtKaishaCd();
							ArrayList aryKaishaNm = mp.getKaishaData().getAryKaishaNm();

							//会社データ数分ループ
							for(int ki=0; ki<aryKaishaCd.size(); ki++){

								//会社コード、会社名取得
								String strkaishaCd = (String)aryKaishaCd.get(ki);
								String strkaishaNm = (String)aryKaishaNm.get(ki);

								//同一の会社コードが存在する場合
								if(strSelKaisha.equals(strkaishaCd)){

									//会社コンボボックス選択
									cmbKaisha.setSelectedItem(strkaishaNm);

									//部署データ取得
									BushoData bd = mp.getBushoData();

									//原料一覧画面内の部署データ取得
									ArrayList aryBushoCd = mp.getBushoData().getArtBushoCd();
									ArrayList aryBushoNm = mp.getBushoData().getAryBushoNm();

									//部署データ数分ループ
									for(int kj=0; kj<aryBushoCd.size(); kj++){

										//部署コード、部署名取得
										String strBushoCd = (String)aryBushoCd.get(kj);
										String strBushoNm = (String)aryBushoNm.get(kj);

										//同一の部署コードが存在する場合
										if(strSelBusho.equals(strBushoCd)){

											//部署コンボボックス選択
											cmbKojo.setSelectedItem(strBushoNm);

											//ループ処理終了
											break;
										}

									}

									//ループ処理終了
									break;
								}
							}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
							//工場選択時処理を行う
							mp.setShiyoFlg();
//add end --------------------------------------------------------------------------------------

							//画面表示
							materialSubDisp.setVisible(true);
						}
						//------------------ 試作列コピーボタン クリック時の処理  ---------------------------
						else if ( event_name == "btnShisakuCopy") {

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.2
							//サンプルNo同一チェック
							String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo_ALL();
							//同一サンプルNoがない場合
							if(chk==null){
								//初期化
								prototypeListSubDisp.initPanel();
								//採用ボタンイベント追加
								(prototypeListSubDisp.getPrototypeListPanel().getButton())[1].addActionListener(this);
								(prototypeListSubDisp.getPrototypeListPanel().getButton())[1].setActionCommand("saiyou");
								//キャンセルボタンイベント追加
								(prototypeListSubDisp.getPrototypeListPanel().getButton())[2].addActionListener(this);
								(prototypeListSubDisp.getPrototypeListPanel().getButton())[2].setActionCommand("cansel");
								//画面表示
								prototypeListSubDisp.setVisible(true);
							}
							//同一サンプルNoがある場合
							else{
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
							}
//mod end --------------------------------------------------------------------------------

						}
						//--------------- 製造工程サブ画面用ボタン クリック時の処理  -----------------------
						else if ( event_name == "btnSeizoKotei") {

							//製造工程/注意事項パネル取得
							ManufacturingPanel mp = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();

							//「常に表示」項目が「製造工程/注意事項」の場合
							if(mp.isTyuuiFg()){

								//選択コンボボックスを設定
								mp.getCombo().setSelectedIndex(0);

								//テキストエリア設定
								mp.dispSeizo();

								//常に表示チェックボックス設定
								mp.getCheckbox().setSelected(true);

							}
							//「常に表示」項目が「試作メモ」の場合
							else if(mp.isMemoFg()){

								//選択コンボボックスを設定
								mp.getCombo().setSelectedIndex(1);

								//テキストエリア設定
								mp.dispMemo();

								//常に表示チェックボックス設定
								mp.getCheckbox().setSelected(true);

							}
							//「常に表示」項目がセットされていない場合
							else{

								//選択コンボボックスを設定
								mp.getCombo().setSelectedIndex(0);

								//テキストエリア設定
								mp.dispSeizo();

								//常に表示チェックボックス設定
								mp.getCheckbox().setSelected(false);

							}

							//画面表示
							DataCtrl.getInstance().getManufacturingSubDisp().setVisible(true);
						}
						//-------------------- 工程挿入ボタン クリック時の処理  ---------------------------
						else if ( event_name == "btnKoteiIns") {
							//選択行番号取得
							int[] kCheck = Trial1.getKCheck();
							int row = kCheck[0];
							//工程行挿入
							if(row >= 0){
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
								//原料行＋工程行の最大行チェック
								if (CheckMaxKoteiGenryoRow(2)) {
									//工程行挿入
									HaigoInsKoteiRow(row);

									//原料費計算
									Trial1.DispGenryohi();

								}
//mod end --------------------------------------------------------------------------------------

							}
						}
						//------------------- 工程移動（↑）ボタン クリック時の処理  -------------------------
						else if ( event_name == "btnKoteiMove_Up") {
							//選択行番号取得
							int[] kCheck = Trial1.getKCheck();
							int row = kCheck[0];
							//工程行移動（↑）
							if(row >= 0){
								HaigoMoveKoteiRow(row,1);

								//原料費計算
								Trial1.DispGenryohi();
							}
						}
						//------------------- 工程移動（↓）ボタン クリック時の処理  -------------------------
						else if ( event_name == "btnKoteiMove_Down") {
							//選択行番号取得
							int[] kCheck = Trial1.getKCheck();
							int row = kCheck[0];
							//工程行移動（↓）
							if(row >= 0){
								HaigoMoveKoteiRow(row,0);

								//原料費計算
								Trial1.DispGenryohi();
							}
						}
						//---------------------- 工程削除ボタン クリック時の処理  -------------------------
						else if ( event_name == "btnKoteiDel") {
							//選択行番号取得
							int[] kCheck = Trial1.getKCheck();
							int row = kCheck[0];
							//工程行削除
							if(row >= 0){

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
								//テーブル取得
								TableBase HaigoMeisai = Trial1.getHaigoMeisai();

								//コンポ－ネント取得
								MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 0);
								DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
								CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();

								//工程キー項目取得
								int intShisakuSeq = 0;
								int intKoteiCd = Integer.parseInt(CheckboxBase.getPk1());
								int intKoteiSeq = 0;
								boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

								//編集可能の場合：既存処理
								if(chk){

								}
								//編集不可の場合：エラーメッセージを表示
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0040);
									return;
								}
//mod end   -------------------------------------------------------------------------------

								//ダイアログコンポーネント設定
								JOptionPane jp = new JOptionPane();

								//確認ダイアログ表示
								int option = jp.showConfirmDialog(
										jp.getRootPane(),
										"工程行の削除を行います。よろしいですか？"
										, "確認メッセージ"
										,JOptionPane.YES_NO_OPTION
										,JOptionPane.PLAIN_MESSAGE
									);

								//「はい」押下
							    if (option == JOptionPane.YES_OPTION){

							    	//工程削除
							    	HaigoDelKoteiRow(row);

									//原料費計算
									Trial1.DispGenryohi();

	//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

									//製品比重、充填量、水相充填量、油相充填量　計算処理
									Trial1.ZidouKeisan2();

	//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

							    //「いいえ」押下
							    }else if (option == JOptionPane.NO_OPTION){

							    	//何もしない

							    }

							}
						}

						//---------------------- 原料挿入ボタン クリック時の処理  -------------------------
						else if ( event_name == "btnGenryoIns") {

							ArrayList AryGenryoCheck = Trial1.getAryGenryoCheck();
							int count = AryGenryoCheck.size();
							String pk_KoteiCd;
							String pk_KoteiSeq;
							int maxRow = 0;

							//選択されている原料がある場合
							if(count > 0){
								//テーブル取得
								TableBase HaigoMeisai = Trial1.getHaigoMeisai();
								TableBase ListMeisai = Trial1.getListMeisai();
								//最大行数取得
								for(int i=0;i<count;i++){
									int[] sel = (int[])AryGenryoCheck.get(i);
									if(maxRow < sel[0]){
										maxRow = sel[0];
									}
								}
								//工程キー項目取得
								MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(maxRow, 2);
								DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(maxRow);
								CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
								pk_KoteiCd = CheckboxBase.getPk1();
								pk_KoteiSeq = CheckboxBase.getPk2();

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
								//原料行＋工程行の最大行チェック
								if (CheckMaxKoteiGenryoRow(1)) {
									//原料行挿入
									HaigoInsertRow(maxRow,pk_KoteiCd,pk_KoteiSeq);

									//原料費計算
									Trial1.DispGenryohi();

								}
//mod end --------------------------------------------------------------------------------------

							}
						}

						//--------------------- 原料移動（↑）ボタン クリック時の処理  -----------------------
						else if ( event_name == "btnGenryoMove_Up") {
							HaigoMoveRow(-1);

							//原料費計算
							Trial1.DispGenryohi();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

							//製品比重、充填量、水相充填量、油相充填量　計算処理
							Trial1.ZidouKeisan2();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------
						}

						//--------------------- 原料移動（↓）ボタン クリック時の処理  -----------------------
						else if ( event_name == "btnGenryoMove_Down") {
							HaigoMoveRow(1);

							//原料費計算
							Trial1.DispGenryohi();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

							//製品比重、充填量、水相充填量、油相充填量　計算処理
							Trial1.ZidouKeisan2();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------
						}

						//----------------------- 原料削除ボタン クリック時の処理  ------------------------
						else if ( event_name == "btnGenryoDel") {

							//削除用データ取得
							ArrayList AryGenryoCheck = Trial1.getAryGenryoCheck();
							int count = AryGenryoCheck.size();
							String pk_KoteiCd;
							String pk_KoteiSeq;

							//原料選択されている場合
							if(count > 0){


								//選択順配列（行番号の小さいもの順）
								ArrayList sort = new ArrayList();
								for(int i=0; i<count; i++){

									//選択行番号取得
									int row = ((int[])AryGenryoCheck.get(i))[0];

									//ソート（2回目以降）
									if(sort.size() > 0){

										//sort配列内を1件毎に比較
										int index = sort.size();

										for(int j=0; j<sort.size(); j++){
											int tag = Integer.parseInt((String)sort.get(j));

											//行番号の数が小さい場合
											if(row < tag){
												index = j;
												j = sort.size();
											}
										}
										sort.add(index, Integer.toString(row));
									//ソート（初回）
									}else{
										sort.add(Integer.toString(row));
									}
								}

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
								//テーブル取得
								TableBase chkHaigoMeisai = Trial1.getHaigoMeisai();

								//選択されている原料を削除
								for(int i=0; i<sort.size(); i++){

									//工程キー項目取得
									int row = Integer.parseInt((String)sort.get(i));
									MiddleCellEditor mc = (MiddleCellEditor)chkHaigoMeisai.getCellEditor(row, 2);
									DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
									CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
									int intShisakuSeq = 0;
									int intKoteiCd = Integer.parseInt(CheckboxBase.getPk1());
									int intKoteiSeq = Integer.parseInt(CheckboxBase.getPk2());

									boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

									//編集可能の場合：既存処理
									if(chk){

									}
									//編集不可の場合：エラーメッセージを表示
									else{
										DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0042);
										return;
									}
								}
//mod end   -------------------------------------------------------------------------------

								//ダイアログコンポーネント設定
								JOptionPane jp = new JOptionPane();

								//確認ダイアログ表示
								int option = jp.showConfirmDialog(
										jp.getRootPane(),
										"原料行の削除を行います。よろしいですか？"
										, "確認メッセージ"
										,JOptionPane.YES_NO_OPTION
										,JOptionPane.PLAIN_MESSAGE
									);

								//「はい」押下
							    if (option == JOptionPane.YES_OPTION){

							    	//テーブル取得
									TableBase HaigoMeisai = Trial1.getHaigoMeisai();
									TableBase ListMeisai = Trial1.getListMeisai();

									//選択されている原料を削除
									for(int i=0; i<sort.size(); i++){

										//工程キー項目取得
										int row = Integer.parseInt((String)sort.get(i));
										MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
										DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
										CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
										pk_KoteiCd = CheckboxBase.getPk1();
										pk_KoteiSeq = CheckboxBase.getPk2();

										//原料行削除
										HaigoDeleteRow(row,pk_KoteiCd,pk_KoteiSeq);

										//ソート配列再設定
										for(int j=0; j<sort.size(); j++){
											if(i < j){
												int set = Integer.parseInt((String)sort.get(j));
												sort.set(j, Integer.toString(set-1));
											}
										}
									}

									//選択配列を初期化
									AryGenryoCheck.clear();

									//空工程へ原料行を挿入
									for(int i=0; i<HaigoMeisai.getRowCount(); i++){

										//コンポーネント取得
										MiddleCellEditor mc_check = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
										DefaultCellEditor tc_check = (DefaultCellEditor)mc_check.getTableCellEditor(i);

										//工程行の場合
										if(((JComponent)tc_check.getComponent()) instanceof CheckboxBase){

											//キー項目を取得
											CheckboxBase chkSelKotei = (CheckboxBase)tc_check.getComponent();
											String selKoteiCd = chkSelKotei.getPk1();

											//配合データ検索
											ArrayList aryChkKotei =
												DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(selKoteiCd));

											//工程データ内に原料がない場合
											if(aryChkKotei.size() == 0){

												//空原料行挿入
												HaigoInsertRow(i, selKoteiCd, "1");

											}
										}
									}

									//配合データ原料順設定
									for(int i=0; i<HaigoMeisai.getRowCount(); i++){

										//コンポーネント取得（原料選択チェックボックス）
										MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
										DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

										//原料選択チェックボックスの場合
										if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){

											//コンポーネントより対象工程CD、工程SEQ取得
											CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
											String count_KoteiCd = selectCb.getPk1();
											String count_KoteiSeq = selectCb.getPk2();

											//原料順設定
											DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
													count_KoteiCd, count_KoteiSeq, i);
										}
									}

									//原料費計算
									Trial1.DispGenryohi();

		//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

									//製品比重、充填量、水相充填量、油相充填量　計算処理
									Trial1.ZidouKeisan2();

		//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

							    //「いいえ」押下
							    }else if (option == JOptionPane.NO_OPTION){

							    	//何もしない

							    }
							}
						}

						//---------------------- 試作列追加ボタン クリック時の処理  -----------------------
						else if ( event_name == "btnShisakuIns") {
							if(selectCol >= 0){
								HaigoInsShisakuCol(selectCol);
							}
						}

						//---------------------- 試作列削除ボタン クリック時の処理  -----------------------
						else if ( event_name == "btnShisakuDel") {
							if(selectCol >= 0){


//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
								//試作SEQ取得
								int intShisakuSeq = 0;
								MiddleCellEditor deleteMc = (MiddleCellEditor)ListHeader.getCellEditor(0, selectCol);
								DefaultCellEditor deleteDc = (DefaultCellEditor)deleteMc.getTableCellEditor(0);
								CheckboxBase CheckboxBase = (CheckboxBase)deleteDc.getComponent();
								intShisakuSeq = Integer.parseInt(CheckboxBase.getPk1());

								//列キー項目取得
								boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);

								//編集可能の場合：既存処理
								if(chk){

								}
								//編集不可の場合：処理無し
								else{
									DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0043);
									return;
								}
//add end   -------------------------------------------------------------------------------

								//ダイアログコンポーネント設定
								JOptionPane jp = new JOptionPane();

								//確認ダイアログ表示
								int option = jp.showConfirmDialog(
										jp.getRootPane(),
										"試作列の削除を行います。よろしいですか？"
										, "確認メッセージ"
										,JOptionPane.YES_NO_OPTION
										,JOptionPane.PLAIN_MESSAGE
									);

								//「はい」押下
							    if (option == JOptionPane.YES_OPTION){

	//add start -------------------------------------------------------------------------------
	//QP@00412_シサクイック改良 No.7
							    	//列削除時の確認メッセージ
							    	if(Trial1.AutoCopyKeisanCheck(selectCol)){

							    	}
							    	else{
							    		//確認ダイアログ表示
										int option2 = jp.showConfirmDialog(
												jp.getRootPane(),
												JwsConstManager.JWS_ERROR_0047
												, "確認メッセージ"
												,JOptionPane.YES_NO_OPTION
												,JOptionPane.PLAIN_MESSAGE
											);

										//「はい」押下
									    if (option2 == JOptionPane.YES_OPTION){
									    	//処理続行
									    }
									    //「いいえ」押下
									    else{
									    	//処理終了
									    	return;
									    }
							    	}
	//add end   -------------------------------------------------------------------------------

							    	//試作列削除
							    	HaigoDelShisakuCol(selectCol);

							    //「いいえ」押下
							    }else if (option == JOptionPane.NO_OPTION){

							    	//何もしない

							    }
							}
						}

						//-------------------- 試作列移動（←）ボタン クリック時の処理  ----------------------
						else if ( event_name == "btnShisakuMove_Left") {
							if(selectCol >= 0){
								moveRetuShisakuCol(selectCol, 0);
							}
						}

						//-------------------- 試作列移動（→）ボタン クリック時の処理  ----------------------
						else if ( event_name == "btnShisakuMove_Right") {
							if(selectCol >= 0){
								moveRetuShisakuCol(selectCol, 1);
							}
						}

						//--------------------  製造工程新規 ボタン クリック時の処理  -----------------------
						if ( event_name == "sinki") {

							//製造工程パネル取得
							ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();

							//2011/06/02 QP@10181_No.31 TT T.Satoh Add Start -------------------------
							//ダイアログコンポーネント設定
							JOptionPane jp = new JOptionPane();

							//選択可能なボタン名を設定
							Object[] options = { "OK" };

							//選択されている項目がない場合
							if(pb.getMemoText().getText().equals("")){
								//確認ダイアログ表示
								int option = jp.showOptionDialog(
										jp.getRootPane(),
										"「製造工程/注意事項」が未入力です。"
										, "確認メッセージ"
										,JOptionPane.DEFAULT_OPTION
										,JOptionPane.PLAIN_MESSAGE
										,null
										,options
										,options[0]);
							}
							//2011/06/02 QP@10181_No.31 TT T.Satoh Add End ---------------------------

							//選択されている項目がある場合
							if( !pb.getMemoText().getText().equals("")){

								//製造工程データ追加
								ManufacturingData md =
									DataCtrl.getInstance().getTrialTblData().AddSeizoKouteiData(
										pb.getShisakuSeq(),
										pb.getTyuuiNo(),
										DataCtrl.getInstance().getTrialTblData().checkNullString(pb.getMemoText().getText()),
										DataCtrl.getInstance().getUserMstData().getDciUserid()
									);

								//2011/05/31 QP@10181_No.31 TT T.Satoh Add Start -------------------------
								//確認ダイアログ表示
								int option = jp.showOptionDialog(
										jp.getRootPane(),
										"新規作成されました。"
										, "確認メッセージ"
										,JOptionPane.DEFAULT_OPTION
										,JOptionPane.PLAIN_MESSAGE
										,null
										,options
										,options[0]);

								//工程版表示
								pb.getLabel()[1].setText("工程版：" + md.getIntTyuiNo());
								//2011/05/31 QP@10181_No.31 TT T.Satoh Add End -------------------------

								//選択データ設定
								pb.setTyuuiNo(md.getIntTyuiNo());

								//コンボボックス追加
								for(int i=0; i<Trial1.getListHeader().getColumnCount(); i++){

									//コンポーネント取得
									MiddleCellEditor selectMc = (MiddleCellEditor)Trial1.getListHeader().getCellEditor(1, i);
									DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(1);
									ComboBase cb = (ComboBase)selectDc.getComponent();

									//選択index取得
									int selectIndex = cb.getSelectedIndex();

									//セルエディタ選択値変更
									cb.addItem(Integer.toString(md.getIntTyuiNo()));
									//2011/05/31 QP@10181_No.31 TT T.Satoh Change Start -------------------------
									//cb.setSelectedItem(Integer.toString(md.getIntTyuiNo()));
									//2011/05/31 QP@10181_No.31 TT T.Satoh Change End ---------------------------

									//セルレンダラ選択値変更
									MiddleCellRenderer selectMr = (MiddleCellRenderer)Trial1.getListHeader().getCellRenderer(1, i);
									ComboBoxCellRenderer selectCer = (ComboBoxCellRenderer)selectMr.getTableCellRenderer(1);
									selectCer.addItem(Integer.toString(md.getIntTyuiNo()));
									selectCer.setSelectedIndex(selectIndex);
								}
							}

							//テスト表示
							//DataCtrl.getInstance().getTrialTblData().dispManufacturingData();
						}
						//--------------------- 製造工程更新 ボタン クリック時の処理  -----------------------
						if ( event_name == "kosin") {

					    	//2011/05/26 QP@10181_No.31 TT T.Satoh Add Start -------------------------
							//確認ダイアログのボタン押下イベント取得用
							int option = 0;

							//ダイアログコンポーネント設定
							JOptionPane jp = new JOptionPane();

							//選択可能なボタン名を設定
							Object[] options = { "OK" };
							//2011/05/26 QP@10181_No.31 TT T.Satoh Add End ---------------------------

							//製造工程パネル取得
							ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();
							int select = pb.getCombo().getSelectedIndex();

							//製造工程&注意事項更新
							if(select == 0){

								//選択されている項目がある場合
								if( pb.getTyuuiNo()>0 ){

									//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
									//確認ダイアログ表示
									option = jp.showOptionDialog(
											jp.getRootPane(),
											"更新されました。"
											, "確認メッセージ"
											,JOptionPane.DEFAULT_OPTION
											,JOptionPane.PLAIN_MESSAGE
											,null
											,options
											,options[0]);
									//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------

									//製造工程データ更新
									DataCtrl.getInstance().getTrialTblData().UpdSeizoKouteiData(
											pb.getTyuuiNo(),
											DataCtrl.getInstance().getTrialTblData().checkNullString(pb.getMemoText().getText()),
											DataCtrl.getInstance().getUserMstData().getDciUserid()
										);
								}
								//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
								//選択されている項目がない場合
								else {
									//確認ダイアログ表示
									option = jp.showOptionDialog(
											jp.getRootPane(),
											"工程版を選択して下さい。"
											, "確認メッセージ"
											,JOptionPane.DEFAULT_OPTION
											,JOptionPane.PLAIN_MESSAGE
											,null
											,options
											,options[0]);
								}
								//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------
							}else if(select == 1){

								//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
								//確認ダイアログ表示
								option = jp.showOptionDialog(
										jp.getRootPane(),
										"更新されました。"
										, "確認メッセージ"
										,JOptionPane.DEFAULT_OPTION
										,JOptionPane.PLAIN_MESSAGE
										,null
										,options
										,options[0]);
								//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------

								// 【KPX1500671】 ADD start
								//試作メモ更新
								String strBefMemo = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShisakuMemo();
								String strAftMemo = pb.getMemoText().getText();
								if(null != strBefMemo && !strBefMemo.equals(strAftMemo)){
									//データ変更フラグＯＮ
									DataCtrl.getInstance().getTrialTblData().setHenkouFg(true);
								}
								// 【KPX1500671】 ADD end
								
								DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setStrShisakuMemo(pb.getMemoText().getText());

							}
						}
						//----------------- 試作コピー画面　採用ボタンクリック時の処理 -----------------------
						if ( event_name == "saiyou") {
							ShisakuSaiyou(selectCol);

							//原料費計算
							Trial1.DispGenryohi();
						}
						//-------------- 試作コピー画面　キャンセルボタンクリック時の処理 ---------------------
						if(event_name == "cansel"){
							prototypeListSubDisp.setVisible(false);
						}

					} catch (ExceptionBase eb) {
						//メッセージ表示
						DataCtrl.getInstance().PrintMessage(eb);

					} catch (Exception ec) {
						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
						//ex.setStrErrmsg("試作データ画面 試作表① ボタン押下処理が失敗しました");
						ex.setStrErrmsg("試作データ画面 配合表 ボタン押下処理が失敗しました");
						//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
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
	 *   試作コピー画面　採用処理
	 *   @author TT nishigawa
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void ShisakuSaiyou(int col) throws ExceptionBase{
		try{

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.35
			//完成系の計算式格納用（sampleNo）
			String strSampleNo = "";
			//完成系の計算式格納用（計算式）
			String strSampleKeisan = "";
			//計算中に使用した試作列１列分の計算式（sampleNo）
			String strKeisanShiki = "";
			//計算中に使用した試作列１列分の計算式（計算式）
			String strKeisanShiki_keisan = "";
//add end   -------------------------------------------------------------------------------

			//------------------------------ 初期処理 --------------------------------------
			//試作コピーパネル取得
			PrototypeListPanel pp = prototypeListSubDisp.getPrototypeListPanel();

			//試作コピーテーブル取得
			PrototypeListTable pt = pp.getPrototypeListTable();

			//選択解除
			TableBase mainTable = pt.getMainTable();
			TableCellEditor tce = mainTable.getCellEditor();
			if(tce != null){
				mainTable.getCellEditor().stopCellEditing();
			}

			//処理フラグ
			boolean chkAddRetu = true;

			//---------------------------- 工程数チェック ------------------------------------
			int dataRows = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			int tableRows = pt.getMaxKotei();
			if(dataRows != tableRows){
				chkAddRetu = false;
			}

			//--------------------------- 試作列数チェック -----------------------------------
			int dataCols = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0).size();
			MiddleCellEditor mce = (MiddleCellEditor)pt.getComboTable().getCellEditor(0, 0);
			DefaultCellEditor dce = (DefaultCellEditor)mce.getTableCellEditor(0);
			ComboBase cb = (ComboBase)dce.getComponent();
			int tableCols = cb.getItemCount();
			if(dataCols != tableCols){
				chkAddRetu = false;
			}

			//試作列が10列未満の場合に追加
			if(pp.getRadioButton()[0].isSelected()){
				int maxcol = pt.getHeaderTable().getColumnCount();
				int chkCol = 10;
				if(maxcol > chkCol){
					DataCtrl.getInstance().getMessageCtrl().PrintMessageString("列追加は" + chkCol + "列までです。");
					return;
				}
			}

			//-------------------------- 試作列選択チェック ----------------------------------
			if(col >= 0){

				//試作列計算
				if(chkAddRetu){

					//テーブル列追加
					HaigoInsShisakuCol(col);

					//対象列
					int insCol = col+1;

					//計算列
					int keisanCol = pt.getHeaderTable().getColumnCount();

					//工程順
					int koteiNo = -1;

					//少数指定取得
					//リテラルデータ取得
					ArrayList aryLiteralCd = DataCtrl.getInstance().getLiteralDataShosu().getAryLiteralCd();
					ArrayList aryLiteralVal = DataCtrl.getInstance().getLiteralDataShosu().getAryValue1();

					//選択データ取得
					String SelShosu = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShosu();
					int shosu = 0;

					//選択リテラルコードの名称取得
					for(int k=0; k<aryLiteralCd.size(); k++){

						//リテラルコードと値１　取得
						String strLiteralCd = (String)aryLiteralCd.get(k);
						String strLiteralVal = (String)aryLiteralVal.get(k);

						//選択している小数指定のリテラル値１　取得
						if(SelShosu != null && SelShosu.length() > 0){
							if(Integer.parseInt(SelShosu) == Integer.parseInt(strLiteralCd)){
								try{
									//小数指定取得
									shosu = Integer.parseInt(strLiteralVal);

								}catch(Exception e){
									//例外時は0を挿入
									shosu = 0;

								}
							}
						}
					}

					//配合行ループ
					TableBase HaigoMeisai = Trial1.getHaigoMeisai();
					for(int i=0; i<HaigoMeisai.getRowCount(); i++){

						//コンポーネント取得
						MiddleCellEditor mceHaigo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
						DefaultCellEditor dceHaigo = (DefaultCellEditor)mceHaigo.getTableCellEditor(i);

						//原料行：計算
						if(dceHaigo.getComponent() instanceof CheckboxBase){


							//コメント行の場合
							String Cd = (String)HaigoMeisai.getValueAt(i, 3);
							int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();
							if(DataCtrl.getInstance().getTrialTblData().commentChk(Cd, keta)){

							}
							//コメント行でない場合
							else{

								//コンポーネント取得
								CheckboxBase cbHaigo = (CheckboxBase)dceHaigo.getComponent();

								//キー項目取得
								int koteiCd = Integer.parseInt(cbHaigo.getPk1());
								int koteiSeq = Integer.parseInt(cbHaigo.getPk2());

								//計算列ループ
								TableBase ListHeader = Trial1.getListHeader();
								int combCount = 0;
								int mainCount = 0;
								String Ans = null;
								boolean nullFg = false;

								//列数分ループ
								for(int j=0; j<keisanCol; j++){

									//試作コピー画面　試作選択コンボボックス取得
									MiddleCellEditor mceKeisan = (MiddleCellEditor)pt.getComboTable().getCellEditor(0, combCount);
									DefaultCellEditor dceKeisan = (DefaultCellEditor)mceKeisan.getTableCellEditor(0);
									ComboBase cbKeisan = (ComboBase)dceKeisan.getComponent();

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.35
									//選択試作順取得（選択文字を「：（コロン）」にて切り取る）
									//int ShisakuNo = Integer.parseInt(cbKeisan.getSelectedItem().toString().split("：")[0]);
									int ShisakuNo = cbKeisan.getSelectedIndex() + 1;

									//計算式中の使用試作列のSampleNoの取得
									try{
										strKeisanShiki = cbKeisan.getSelectedItem().toString();
									}catch(Exception e){
										strKeisanShiki = "";
									}
									if(JwsConstManager.JWS_COPY_0002){
										//コピー先計算フラグがTrueの場合Sample名を括弧でくくる
										strKeisanShiki = JwsConstManager.JWS_COPY_0003 + strKeisanShiki + JwsConstManager.JWS_COPY_0004;
									}
//add end   -------------------------------------------------------------------------------

									//試作SEQ取得（試作Noより処理列を特定し、その列の試作SEQを取得）
									if(ShisakuNo > insCol){
										ShisakuNo += 1;
									}
									MiddleCellEditor mceHeader = (MiddleCellEditor)ListHeader.getCellEditor(0, ShisakuNo-1);
									DefaultCellEditor dceHeader = (DefaultCellEditor)mceHeader.getTableCellEditor(0);
									CheckboxBase cbHeader = (CheckboxBase)dceHeader.getComponent();

									//キー項目取得（試作SEQ）
									int shisakuSeq = Integer.parseInt(cbHeader.getPk1());

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
									if(JwsConstManager.JWS_COPY_0002){
										String keisan = DataCtrl.getInstance().getTrialTblData().SearchShisakuKeisanSiki(shisakuSeq);
										//計算式がない場合
										if(keisan == null || keisan.length() == 0){
											//試作SEQを括弧でくくる
											strKeisanShiki_keisan = JwsConstManager.JWS_COPY_0003 + JwsConstManager.JWS_COPY_0005
																			+ Integer.toString(shisakuSeq) + JwsConstManager.JWS_COPY_0004;
										}else{
											//計算式を括弧でくくる
											strKeisanShiki_keisan = JwsConstManager.JWS_COPY_0003 + keisan + JwsConstManager.JWS_COPY_0004;
										}
									}
//add end   -------------------------------------------------------------------------------

									//リスト値取得
									TableBase ListMeisai = Trial1.getListMeisai();
									String listValue = (String)ListMeisai.getValueAt(i, ShisakuNo-1);

									//Nullチェック
									String strSiki = "";
									String strAtai = "";
									String strRetuSiki ="";

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
									String strSiki_keisan = "";
//add end   -------------------------------------------------------------------------------

									//数値データでない場合は「0」を挿入
									try{

										new BigDecimal(listValue);

									}catch(Exception e){

										listValue = "0";

									}

									//BigDecimal型に変換
									BigDecimal deciValue = new BigDecimal(listValue);

									//工程計算式取得
									MiddleCellEditor mceSiki = (MiddleCellEditor)pt.getMainTable().getCellEditor(koteiNo, mainCount+1);
									DefaultCellEditor dceSiki = (DefaultCellEditor)mceSiki.getTableCellEditor(koteiNo);
									ComboBase cbSiki = (ComboBase)dceSiki.getComponent();
									strSiki = (String)cbSiki.getSelectedItem();

									//工程計算値取得
									strAtai = (String)pt.getMainTable().getValueAt(koteiNo, mainCount+2);

									//計算
									if(strAtai != null && strAtai.length()>0){
										//計算値をBigDecimal型に変換
										BigDecimal deciAtai = new BigDecimal(strAtai);

										//掛け算
										if(strSiki.equals("×")){
											deciValue = deciValue.multiply(deciAtai);

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.35
											strSiki = "*";
											strSiki_keisan = "*";
//add end   -------------------------------------------------------------------------------

										//割り算
										}else if(strSiki.equals("÷")){
											deciValue = deciValue.divide(deciAtai, BigDecimal.ROUND_HALF_UP);

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.35
											strSiki = "/";
											strSiki_keisan = "/";
//add end   -------------------------------------------------------------------------------

										}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.35
										//選択計算演算子と選択試作列のSampleNo連結
										strKeisanShiki = strKeisanShiki + strSiki + deciAtai;
										//選択計算演算子と選択試作列の計算式連結
										strKeisanShiki_keisan = strKeisanShiki_keisan + strSiki_keisan + deciAtai;
//add end   -------------------------------------------------------------------------------

									}else{
										Ans = null;
										nullFg = true;
									}

									//初回
									if(j == 0){
										//文字列データへ格納
										//deciValue.setScale(scale);
										Ans = deciValue.toString();
										//テスト表示
										//System.out.println(koteiCd + "," + koteiSeq+"," + shisakuSeq+","+listValue+strSiki+strAtai+"="+Ans);

									//2回目以降
									}else{
										//計算対象をBigDecimal型に変換
										if(Ans != null && Ans.length() > 0){
											BigDecimal keisanValue = new BigDecimal(Ans);

											//試作列計算式取得
											MiddleCellEditor mceRetuSiki = (MiddleCellEditor)pt.getComboTable().getCellEditor(0, combCount-1);
											DefaultCellEditor dceRetuSiki = (DefaultCellEditor)mceRetuSiki.getTableCellEditor(0);
											ComboBase cbRetuSiki = (ComboBase)dceRetuSiki.getComponent();
											strRetuSiki = (String)cbRetuSiki.getSelectedItem();

											//足し算
											if(strRetuSiki.equals("+")){
												keisanValue = keisanValue.add(deciValue);
											//引き算
											}else if(strRetuSiki.equals("-")){
												keisanValue = keisanValue.subtract(deciValue);
											}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.35
											//選択計算演算子と計算式の連結（サンプルNo）
											strKeisanShiki = strSampleNo + strRetuSiki + strKeisanShiki;
											//選択計算演算子と計算式の連結（計算式）
											strKeisanShiki_keisan = strSampleKeisan + strRetuSiki + strKeisanShiki_keisan;
//add end   -------------------------------------------------------------------------------

											//テスト表示
											//System.out.println(koteiCd + "," + koteiSeq+"," + shisakuSeq+","+Ans+strRetuSiki+"("+listValue+strSiki+strAtai+"="+deciValue.toString()+")="+keisanValue.toString());

											//文字列データへ格納
											Ans = keisanValue.toString();
										}
									}

									//コンボテーブル列カウント
									combCount += 2;
									//メインテーブル列カウント
									mainCount+=3;

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.35
									//計算式の格納（サンプルNo）
									strSampleNo = strKeisanShiki;
									//計算式の格納（計算式）
									strSampleKeisan = strKeisanShiki_keisan;
//add end   -------------------------------------------------------------------------------

								}

								//計算結果をテーブル表示
								TableBase ListMeisai = Trial1.getListMeisai();
								ListMeisai.setValueAt(Trial1.ShosuArai(Ans), i, insCol);
								//ListMeisai.setValueAt(Ans, i, insCol);

								//計算結果をデータ挿入
								//試作SEQ取得
								MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, insCol);
								DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
								CheckboxBase cbSeq = (CheckboxBase)dceSeq.getComponent();
								int listSeq = Integer.parseInt(cbSeq.getPk1());
								//データ挿入
								DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
										listSeq,
										koteiCd,
										koteiSeq,
										DataCtrl.getInstance().getTrialTblData().checkNullDecimal(Trial1.ShosuArai(Ans))
									);
							}

						//工程行
						}else{
							//計算が選択されている場合
							if(pp.getRadioButton()[1].isSelected()){
								koteiNo++;
							//全工程が選択されている場合
							}else{
								koteiNo = 0;
							}
						}
					}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.35
					//全工程が選択されている場合
					if(pp.getRadioButton()[0].isSelected()){

						//作成した試作列にSampleNoの設定 ---------------------------
						//表示値設定
						TableBase ListHeader = Trial1.getListHeader();

						//キー項目取得
						MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, insCol);
						DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
						CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
						int intSeq  = Integer.parseInt(chkSeq.getPk1());

						//値の設定
						ListHeader.setValueAt(strSampleNo, 3, insCol);

						//データ挿入
						DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSampleNo(
								intSeq,
								strSampleNo,
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

						//作成した試作データに計算式を設定 ---------------------------
						//データ挿入
						DataCtrl.getInstance().getTrialTblData().UpdShisakuKeisanSiki(
								intSeq,
								strSampleKeisan
							);

	//add start ----------------------------------------------------------------------------
	//QP@00412_シサクイック改良 No.23
						//試作明細テーブル取得
						TableBase listMeisai = this.getTrial1().getListMeisai();

						//最大工程順取得
						int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

						//合計仕上重量行
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
						//int keisanRow = listMeisai.getRowCount()-7;
						int keisanRow = listMeisai.getRowCount()-8;
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------


						//配合データ配列取得
						ArrayList aryShisaku = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intSeq);

						//サンプルNO取得
						String sampleKeisan = ((TrialData)aryShisaku.get(0)).getStrKeisanSiki();

						//計算式変換
						String keisanSiki = DataCtrl.getInstance().getTrialTblData().changeKeisanLogic( sampleKeisan , 0 );
						String keisan = DataCtrl.getInstance().getTrialTblData().getKeisanShisakuSeqSiagari( keisanSiki);


						//QP@20505 2012/10/26 No1 Add start
						int koteiShiagariRow = keisanRow - maxKotei;

						for(int i = 0; i < maxKotei; i++){
							//2013/04/01 MOD Start
//							int intKoteiCode = DataCtrl.getInstance().getTrialTblData().getSerchKoteiCode(insCol, i + 1);
							int intKoteiCode = DataCtrl.getInstance().getTrialTblData().getSerchKoteiCode(intSeq, i + 1);
							//2013/04/01 MOD End

							String koteiKeisanSiki = DataCtrl.getInstance().getTrialTblData().changeKeisanLogic( sampleKeisan , 0 );

							//計算式取得
							String keisana = DataCtrl.getInstance().getTrialTblData().getKeisanShisakuSeqKoteiSiagari( koteiKeisanSiki ,i + 1);

							//計算実行
							String strKekka = DataCtrl.getInstance().getTrialTblData().execKeisan(keisana);

							//小数洗替（工程仕上重量）
							if(strKekka != null && strKekka.length() > 0){
								//洗替処理
								strKekka = this.getTrial1().ShosuAraiHulfUp_keta(strKekka,"4");
							}

							//値の設定
							listMeisai.setValueAt(strKekka, koteiShiagariRow + i, insCol);

							DataCtrl.getInstance().getTrialTblData().UpdKouteiShiagari(
									intSeq,
									intKoteiCode,
									DataCtrl.getInstance().getTrialTblData().checkNullDecimal(strKekka),
									DataCtrl.getInstance().getUserMstData().getDciUserid()
								);

						}
						//QP@20505 2012/10/26 No1 Add End

						//計算実行
						String strKekka = DataCtrl.getInstance().getTrialTblData().execKeisan(keisan);

						//小数洗替（合計仕上重量）
						if(strKekka != null && strKekka.length() > 0){
							//洗替処理
							strKekka = this.getTrial1().ShosuAraiHulfUp_keta(strKekka,"4");
						}

						//値の設定
						listMeisai.setValueAt(strKekka, keisanRow, insCol);

						//データ挿入
						DataCtrl.getInstance().getTrialTblData().UpdShiagariRetuDate(
								intSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullDecimal(strKekka),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

	//add end   ---------------------------------------------------------------------------

					}
//add end   -------------------------------------------------------------------------------

					//工程合計計算
					Trial1.koteiSum(col+1);

					//自動計算
					Trial1.AutoKeisan();

					//非表示処理
					prototypeListSubDisp.setVisible(false);

				}else{
					//エラー設定
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					ex.setStrErrmsg("試作表の情報が変更されています。「試作列コピー」ボタンを押下し、再度設定して下さい。");
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg("");
					throw ex;

				}

			}else{
				//エラー設定
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
				//ex.setStrErrmsg("試作列が選択されていません。試作表①より試作列を選択して下さい。");
				ex.setStrErrmsg("試作列が選択されていません。配合表より試作列を選択して下さい。");
				//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
				ex.setStrErrShori(this.getClass().getName());
				ex.setStrMsgNo("");
				ex.setStrSystemMsg("");
				throw ex;

			}

		}catch(ExceptionBase eb){
			throw eb;

		}catch(ArithmeticException ae){
			//追加列を削除
			HaigoDelShisakuCol(col+1);

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("0除算はできません。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ae.getMessage());
			throw ex;

		}catch(Exception e){

			e.printStackTrace();

			//追加列を削除
			HaigoDelShisakuCol(col+1);

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試作コピー　採用処理が失敗しました。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg("");
			throw ex;

		}finally{
			//テスト表示
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		}
	}

	/************************************************************************************
	 *
	 *   原料行挿入
	 *    : 配合明細、試作リストテーブルへ行を挿入する
	 *   @author TT nishigawa
	 *   @parm   maxRow      : 追加行
	 *   @parm   pk_KoteiCd  : 工程CD
	 *   @parm   pk_KoteiSeq : 工程SEQ
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoInsertRow(int maxRow,String pk_KoteiCd,String pk_KoteiSeq) throws ExceptionBase{
		try{
			//テーブル取得
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();
			TableBase ListMeisai = Trial1.getListMeisai();

			//挿入行
			int moveRow = maxRow+1;

			//配合データ＆試作リストデータ追加
			ArrayList chkHaigo =
				DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(pk_KoteiCd));
			MixedData addMixedData = new MixedData();


			if(chkHaigo.size() == 0){

				//コンポーネント取得（原料選択チェックボックス）
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(maxRow, 0);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(maxRow);
				CheckboxBase CheckboxBase = (CheckboxBase)selectDc.getComponent();

				addMixedData = DataCtrl.getInstance().getTrialTblData().AddHaigoGenryo(
						pk_KoteiCd,
						pk_KoteiSeq,
						Integer.parseInt(CheckboxBase.getPk3()),
						CheckboxBase.getPk4(),
						CheckboxBase.getPk5()
					);

			}else{

				addMixedData = DataCtrl.getInstance().getTrialTblData().AddHaigoGenryo(pk_KoteiCd);
			}

			pk_KoteiCd = Integer.toString(addMixedData.getIntKoteiCd());
			pk_KoteiSeq = Integer.toString(addMixedData.getIntKoteiSeq());

			//配合明細の選択情報をクリア
			HaigoMeisai.clearSelection();
			TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
			if(hmEditor != null){
				HaigoMeisai.getCellEditor().stopCellEditing();
			}

			//配合明細へ行追加
			HaigoMeisai.tableInsertRow(moveRow);
			Trial1.addHaigoMeisaiRowER(moveRow, 0, pk_KoteiCd, pk_KoteiSeq,Integer.parseInt(addMixedData.getStrIro()));
			Trial1.setHaigoMeisaiER();

			//配合テーブルへ工程順表示
			HaigoMeisai.setValueAt(Integer.toString(addMixedData.getIntKoteiNo()), moveRow, 1);

			//リスト明細の選択情報をクリア
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}

			//リスト明細へ行追加
			ListMeisai.tableInsertRow(moveRow);
			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				Trial1.addListShisakuRowER(i, moveRow, 0);
			}


			Trial1.setListShisakuER();

			//配合データ原料順設定
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){

				//コンポーネント取得（原料選択チェックボックス）
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

				//原料選択チェックボックスの場合
				if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){

					//コンポーネントより対象工程CD、工程SEQ取得
					CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
					String count_KoteiCd = selectCb.getPk1();
					String count_KoteiSeq = selectCb.getPk2();

					//原料順設定
					DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
							count_KoteiCd, count_KoteiSeq, i);
				}
			}

			//工程合計計算
			for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
				Trial1.koteiSum(col);
			}
			//自動計算
			Trial1.AutoKeisan();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作データ画面 試作表① 原料行挿入処理が失敗しました");
			ex.setStrErrmsg("試作データ画面 配合表 原料行挿入処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			//テスト表示
			//DataCtrl.getInstance().getTrialTblData().dispHaigo();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		}
	}

	/************************************************************************************
	 *
	 *   原料行移動
	 *    : 配合明細、試作リストテーブルの行を移動する
	 *   @author TT nishigawa
	 *   @param move : 移動数（1 or -1）
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoMoveRow(int move) throws ExceptionBase{
		try{
			//配合データ取得
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			//最大工程順取得
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

			ArrayList AryGenryoCheck = Trial1.getAryGenryoCheck();
			int count = AryGenryoCheck.size();
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int keisanRow = maxKotei+8;
			int keisanRow = maxKotei+9;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			String pk_KoteiCd_moto = "";
			String pk_KoteiSeq_moto = "";
			String pk_KoteiCd_saki = "";
			String pk_KoteiSeq_saki = "";

			//選択されている原料がある場合
			if(count > 0){
				//テーブル取得
				TableBase HaigoMeisai = Trial1.getHaigoMeisai();
				TableBase ListMeisai = Trial1.getListMeisai();
				//選択順配列（行番号の小さいもの順）
				ArrayList sort = new ArrayList();
				for(int i=0; i<count; i++){
					//選択行番号取得
					int row = ((int[])AryGenryoCheck.get(i))[0];
					//ソート（2回目以降）
					if(sort.size() > 0){
						//sort配列内を1件毎に比較
						int index = sort.size();
						for(int j=0; j<sort.size(); j++){
							int tag = Integer.parseInt((String)sort.get(j));
							//行番号の数が小さい場合
							if(row < tag){
								index = j;
								j = sort.size();
							}
						}
						sort.add(index, Integer.toString(row));
					//ソート（初回）
					}else{
						sort.add(Integer.toString(row));
					}
				}

				//配合、試作リスト行移動
				int move_count;
				if(move < 0){

					//行番号の小さいものより移動
					move_count = 0;

				}else{

					//行番号の大きいものより移動
					move_count = sort.size()-1;
				}

				for(int i=0; i<sort.size();i++){

					int row = Integer.parseInt((String)sort.get(move_count));
					int row_move = row+move;
					int row_bef = -1;

					//移動先の行番号取得
					if(move < 0){

						if(move_count > 0){

							row_bef = Integer.parseInt((String)sort.get(move_count-1));
						}

					}else{

						if(move_count < sort.size()-1){
							row_bef = Integer.parseInt((String)sort.get(move_count+1));
						}

					}

					//配合明細の選択情報をクリア
					HaigoMeisai.clearSelection();
					TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
					if(hmEditor != null){
						HaigoMeisai.getCellEditor().stopCellEditing();
					}

					//リスト明細の選択情報をクリア
					ListMeisai.clearSelection();
					TableCellEditor lmEditor = ListMeisai.getCellEditor();
					if(lmEditor != null){
						ListMeisai.getCellEditor().stopCellEditing();
					}

					//移動範囲の限界値を設定
					int move_limit;

					if(move < 0){

						//先頭行の設定
						move_limit = 1;
					}else{

						//最終行の設定
						move_limit = HaigoMeisai.getRowCount()-keisanRow-1;
					}
					//移動先が先頭・最終行でない場合、且つ移動先が選択行番号ではない場合
					if(row != move_limit && row_bef != row_move){

						//キー項目取得移動元
						MiddleCellEditor mc_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
						DefaultCellEditor tc_moto = (DefaultCellEditor)mc_moto.getTableCellEditor(row);
						CheckboxBase cb_moto = (CheckboxBase)tc_moto.getComponent();
						pk_KoteiCd_moto  = cb_moto.getPk1();
						pk_KoteiSeq_moto = cb_moto.getPk2();

						//工程順変更確認
						MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_move, 0);
						DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row_move);

						boolean kotei_move_flg = false; //工程変更Flg
						if(((JComponent)tc.getComponent()) instanceof CheckboxBase){
							kotei_move_flg = true;

							//キー項目取得移動先（他工程内の場合）
							MiddleCellEditor mc_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_move+move, 2);
							DefaultCellEditor tc_saki = (DefaultCellEditor)mc_saki.getTableCellEditor(row_move+move);
							CheckboxBase cb_saki = (CheckboxBase)tc_saki.getComponent();
							pk_KoteiCd_saki  = cb_saki.getPk1();
							pk_KoteiSeq_saki = cb_saki.getPk2();

						}else{

							//キー項目取得移動先（同工程内の場合）
							MiddleCellEditor mc_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_move, 2);
							DefaultCellEditor tc_saki = (DefaultCellEditor)mc_saki.getTableCellEditor(row_move);
							CheckboxBase cb_saki = (CheckboxBase)tc_saki.getComponent();
							pk_KoteiCd_saki  = cb_saki.getPk1();
							pk_KoteiSeq_saki = cb_saki.getPk2();

						}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
						//工程を跨る場合
						if(kotei_move_flg){
							//検索キー取得
							int intShisakuSeq = 0;
							int intKoteiCd  = Integer.parseInt(cb_moto.getPk1());
							int intKoteiSeq = Integer.parseInt(cb_moto.getPk2());
							boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

							//編集可能の場合：既存処理
							if(chk){

							}
							//編集不可の場合：既存処理
							else{
								DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0041);
								break;
							}
						}
//add end   -------------------------------------------------------------------------------

						//データ移動
						MixedData Haigou_saki = DataCtrl.getInstance().getTrialTblData().MoveHaigoGenryo(pk_KoteiCd_moto, pk_KoteiSeq_moto,
								pk_KoteiCd_saki, pk_KoteiSeq_saki, move);

						//配合データ移動(画面)
						HaigoMeisai.tableMoveRow(row,row_move);

						//試作リストデータ移動(画面)
						ListMeisai.tableMoveRow(row,row_move);

						//工程順変更
						if(kotei_move_flg){

							int koteiNo = Integer.parseInt((String)HaigoMeisai.getValueAt(row_move, 1))+move;
							HaigoMeisai.setValueAt(Integer.toString(koteiNo), row_move, 1);

							//工程行の高さ調整
							HaigoMeisai.setRowHeight(row, 17);
							HaigoMeisai.setRowHeight(row_move, 17);
							ListMeisai.setRowHeight(row, 17);
							ListMeisai.setRowHeight(row_move, 17);

						}

						//配合エディタ＆レンダラ移動
						Trial1.changeHaigoMeisaiRowER(row, row_move);
						Trial1.setHaigoMeisaiER();
						MiddleCellEditor mc_haigo = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_move, 2);
						DefaultCellEditor dc_haigo = (DefaultCellEditor)mc_haigo.getTableCellEditor(row_move);
						CheckboxBase cb_haigo = (CheckboxBase)dc_haigo.getComponent();
						cb_haigo.setPk1(Integer.toString(Haigou_saki.getIntKoteiCd()));
						cb_haigo.setPk2(Integer.toString(Haigou_saki.getIntKoteiSeq()));

						//試作リストエディタ＆レンダラ移動
						Trial1.changeListShisakuRowER(row, row_move);
						Trial1.setListShisakuER();

						//原料行が工程行を跨いだ場合
						if(kotei_move_flg){

							//コンポーネント取得
							MiddleCellEditor mc_check = (MiddleCellEditor)HaigoMeisai.getCellEditor(row-move, 2);
							DefaultCellEditor tc_check = (DefaultCellEditor)mc_check.getTableCellEditor(row-move);

							//工程内に原料行がない場合
							if(((JComponent)tc_check.getComponent()) instanceof CheckboxBase){

								//移動した行の行数を配列に設定
								sort.set(move_count,Integer.toString(row_move));

							}else{

								//挿入する行数の取得
								int ins_move;
								if(move < 0){
									ins_move = row;
								}else{
									ins_move = row-move;
								}

								//原料行挿入
								HaigoInsertRow(ins_move, pk_KoteiCd_moto, "1");


								//ソート配列再設定
								for(int j=0; j<sort.size(); j++){
									if(move < 0){

										//移動した行の行数を配列に設定
										if(move_count == j){
											int setRow = Integer.parseInt((String)sort.get(j));
											sort.set(j,Integer.toString(row_move));
										}

										//移動した行より大きい行数を配列に設定
										if(move_count < j){
											int setRow = Integer.parseInt((String)sort.get(j));
											sort.set(j,Integer.toString(setRow-move));
										}

									}else{

										//移動した行の行数を配列に設定
										if(move_count == j){
											int setRow = Integer.parseInt((String)sort.get(j));
											sort.set(j,Integer.toString(row_move+move));
										}

										//移動した行より大きい行数を配列に設定
										if(move_count < j){
											int setRow = Integer.parseInt((String)sort.get(j));
											sort.set(j,Integer.toString(setRow+move));
										}
									}
								}
							}
						}else{
							//移動した行の行数を配列に設定
							sort.set(move_count,Integer.toString(row_move));
						}
					}
					//ループインデックス：カウント
					if(move < 0){
						move_count++; //昇順
					}else{
						move_count--; //降順
					}
				}

				//選択原料配列を再設定
				for(int i=0; i<sort.size(); i++){
					AryGenryoCheck.set(i, new int[]{Integer.parseInt((String)sort.get(i)), 2});
				}

				//配合データ原料順設定
				for(int i=0; i<HaigoMeisai.getRowCount(); i++){

					//コンポーネント取得（原料選択チェックボックス）
					MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
					DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

					//原料選択チェックボックスの場合
					if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){

						//コンポーネントより対象工程CD、工程SEQ取得
						CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
						String count_KoteiCd = selectCb.getPk1();
						String count_KoteiSeq = selectCb.getPk2();

						//原料順設定
						DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
								count_KoteiCd, count_KoteiSeq, i);
					}
				}

				//工程合計計算
				for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
					Trial1.koteiSum(col);
				}

				//テスト表示
				//DataCtrl.getInstance().getTrialTblData().dispHaigo();
			}

		} catch (ExceptionBase e) {

			throw e;

		} catch (Exception e) {

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作データ画面 試作表① 原料行移動処理が失敗しました");
			ex.setStrErrmsg("試作データ画面 配合表 原料行移動処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		}

	}

	/************************************************************************************
	 *
	 *   原料行削除
	 *    : 配合明細、試作リストテーブルの行を削除する
	 *   @author TT nishigawa
	 *   @param row : 削除行番号
	 *   @param pk_KoteiCd : 工程CD
	 *   @param pk_KoteiSeq : 工程SEQ
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoDeleteRow(int row, String pk_KoteiCd, String pk_KoteiSeq) throws ExceptionBase{
		try{
			//------------------------- 表示項目削除  ------------------------------------
			//テーブル取得
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();
			TableBase ListMeisai = Trial1.getListMeisai();

			//テーブル表示値削除
			HaigoMeisai.tableDeleteRow(row);
			ListMeisai.tableDeleteRow(row);

			//エディタ＆レンダラ削除
			Trial1.delHaigoMeisaiRowER(row);
			Trial1.setHaigoMeisaiER();
			Trial1.delListShisakuRowER(row);
			Trial1.setListShisakuER();

			//---------------------------- データ削除  -------------------------------------
			DataCtrl.getInstance().getTrialTblData().DelHaigoGenryo(pk_KoteiCd, pk_KoteiSeq);

			//工程合計計算
			for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
				Trial1.koteiSum(col);
			}
			//自動計算
			Trial1.AutoKeisan();

		}catch (ExceptionBase e) {
			throw e;

		}catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作データ画面 試作表① 原料行削除処理が失敗しました");
			ex.setStrErrmsg("試作データ画面 配合表 原料行削除処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			//テスト表示
//			DataCtrl.getInstance().getTrialTblData().dispHaigo();
//			DataCtrl.getInstance().getTrialTblData().dispProtoList();
		}
	}

	/************************************************************************************
	 *
	 *   工程行挿入
	 *    : 配合明細、試作リストテーブルの工程行（紐付く原料行）を挿入する
	 *   @author TT nishigawa
	 *   @param row : 挿入行番号
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoInsKoteiRow(int row) throws ExceptionBase{
		try{
			//---------------------------- 初期処理  --------------------------------------
			//テーブル取得
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();
			TableBase ListMeisai = Trial1.getListMeisai();

			//配合データ取得
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

			//コンポ－ネント取得
			MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 0);
			DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
			CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
			//工程キー項目取得
			String pk_KoteiCd = CheckboxBase.getPk1();
			String pk_KoteiSeq = CheckboxBase.getPk2();

			//配合データ数取得
			ArrayList ins_kotei =
				DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(pk_KoteiCd));

			//挿入行設定
			int insert_row = row+ins_kotei.size()+1;

			//--------------------------- データ挿入  ---------------------------------------
			//工程追加
			MixedData addMixedData = DataCtrl.getInstance().getTrialTblData().AddHaigoKoutei();
			String addPk_koteiCd = Integer.toString(addMixedData.getIntKoteiCd());
			String addPk_koteiSeq = Integer.toString(addMixedData.getIntKoteiSeq());

			//--------------------------- 表示値挿入 --------------------------------------
			//配合明細の選択情報をクリア
			HaigoMeisai.clearSelection();
			TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
			if(hmEditor != null){
				HaigoMeisai.getCellEditor().stopCellEditing();
			}
			//リスト明細の選択情報をクリア
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}

			//配合明細テーブルへ工程行挿入
			HaigoMeisai.tableInsertRow(insert_row);
			Trial1.addHaigoMeisaiRowER(insert_row, 1, addPk_koteiCd, addPk_koteiSeq, -1);
			Trial1.setHaigoMeisaiER();
			//試作明細テーブルへ工程行挿入
			ListMeisai.tableInsertRow(insert_row);

			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				Trial1.addListShisakuRowER(i, insert_row, 1);
			}

			Trial1.setListShisakuER();
			//行の高さを設定
			HaigoMeisai.setRowHeight(insert_row, 17);
			ListMeisai.setRowHeight(insert_row, 17);

			//配合明細テーブルへ原料行挿入
			HaigoMeisai.tableInsertRow(insert_row+1);
			Trial1.addHaigoMeisaiRowER(insert_row+1, 0, addPk_koteiCd, addPk_koteiSeq,Integer.parseInt(addMixedData.getStrIro()));
			Trial1.setHaigoMeisaiER();
			//試作明細テーブルへ原料行挿入
			ListMeisai.tableInsertRow(insert_row+1);

			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				Trial1.addListShisakuRowER(i, insert_row+1, 0);
			}

			Trial1.setListShisakuER();


			//------------------------------ 計算列追加 --------------------------------------
			//最大工程順取得
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

			//計算対象列数取得
			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int keisanRow = ListMeisai.getRowCount()-8;
			//int keisanRow = ListMeisai.getRowCount()-9;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			int keisanRow = ListMeisai.getRowCount()-maxKotei-9;
			// MOD end 20120914 QP@20505 No.1

			//配合明細テーブルへ計算行挿入
			HaigoMeisai.tableInsertRow(keisanRow);
			Trial1.addHaigoMeisaiRowER(keisanRow, 2, addPk_koteiCd, addPk_koteiSeq, -1);
			Trial1.setHaigoMeisaiER();
			//試作明細テーブルへ計算行挿入
			ListMeisai.tableInsertRow(keisanRow);

			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				Trial1.addListShisakuRowER(i, keisanRow, 1);
			}

			Trial1.setListShisakuER();

			//項目表示
			Trial1.getHaigoMeisai().setValueAt((maxKotei+1)+"工程（ｇ）", keisanRow, 4);

			// ADD start 20120914 QP@20505 No.1
			//工程仕上り
			int koteiSiagariRow = ListMeisai.getRowCount()-8;
			int gokeiJuuryoRow = koteiSiagariRow - maxKotei;
			int koteiShiagariIns = gokeiJuuryoRow + Integer.parseInt(CheckboxBase.getPk3());

			//配合明細テーブルへ工程仕上行挿入
			HaigoMeisai.tableInsertRow(koteiSiagariRow);
			Trial1.addHaigoMeisaiRowER(koteiSiagariRow, 2, addPk_koteiCd, addPk_koteiSeq, -1);
			Trial1.setHaigoMeisaiER();
			//試作明細テーブルへ工程仕上行挿入
			ListMeisai.tableInsertRow(koteiShiagariIns);

			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				Trial1.addListShisakuRowER(i, koteiSiagariRow, 0);
			}

			Trial1.setListShisakuER();

			//項目表示
			Trial1.getHaigoMeisai().setValueAt((maxKotei+1)+"工程仕上重量（ｇ）", koteiSiagariRow, 4);
			// ADD end 20120914 QP@20505 No.1

			//---------------------- 工程＆原料順再設定  -----------------------------------
			int koteiNo = 0;

			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-8-1; i++){
			//for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-9-1; i++){
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			for(int i=0; i<HaigoMeisai.getRowCount()-(maxKotei*2)-10-1; i++){
			// MOD end 20120914 QP@20505 No.1

				MiddleCellEditor mcKoteiNo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor tcKoteiNo = (DefaultCellEditor)mcKoteiNo.getTableCellEditor(i);
				if(((JComponent)tcKoteiNo.getComponent()) instanceof CheckboxBase){
					koteiNo++;
					//コンポーネントにキー項目を設定
					CheckboxBase setKotei = (CheckboxBase)tcKoteiNo.getComponent();
					setKotei.setPk3(Integer.toString(koteiNo));
					setKotei.setPk4("");
					setKotei.setPk5("");
				}
				//テーブル内表示値設定
				HaigoMeisai.setValueAt(Integer.toString(koteiNo), i, 1);

				//配合データ工程＆原料順設定
				//コンポーネント取得（原料選択チェックボックス）
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

				//原料選択チェックボックスの場合
				if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){

					//コンポーネントより対象工程CD、工程SEQ取得
					CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
					String count_KoteiCd = selectCb.getPk1();
					String count_KoteiSeq = selectCb.getPk2();

					//原料順設定
					DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
							count_KoteiCd, count_KoteiSeq, koteiNo, i);
				}
			}
			//最大工程順設定
			DataCtrl.getInstance().getTrialTblData().setIntMaxKotei(koteiNo);

			//工程合計計算
			for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
				Trial1.koteiSum(col);
			}
			//自動計算
			Trial1.AutoKeisan();

			//テスト表示
			//DataCtrl.getInstance().getTrialTblData().dispHaigo();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作データ画面 試作表① 工程行挿入処理が失敗しました");
			ex.setStrErrmsg("試作データ画面 配合表 工程行挿入処理が失敗しました");
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
	 *   工程行削除
	 *    : 配合明細、試作リストテーブルの工程行（紐付く原料行）を削除する
	 *   @author TT nishigawa
	 *   @param row : 削除行番号
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoDelKoteiRow(int row) throws ExceptionBase{
		try{
			//---------------------------- 初期処理  --------------------------------------
			//テーブル取得
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();
			TableBase ListMeisai = Trial1.getListMeisai();

			//配合データ取得
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei()-1;

			// ADD start 20120914 QP@20505 No.1
			int beforeKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			// ADD end 20120914 QP@20505 No.1

			//コンポ－ネント取得
			MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 0);
			DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
			CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
			//工程キー項目取得
			String pk_KoteiCd = CheckboxBase.getPk1();
			String pk_KoteiSeq = CheckboxBase.getPk2();

			// ADD start 20120914 QP@20505 No.1
			String pk_KoteiSelect = CheckboxBase.getPk3();
			// ADD end 20120914 QP@20505 No.1

			//配合データ数取得
			ArrayList del_kotei =
				DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(pk_KoteiCd));

			//削除行設定
			int delete_start = row;
			int delete_end = delete_start+del_kotei.size();

			//--------------------------- 表示値削除  -------------------------------------
			//配合明細の選択情報をクリア
			HaigoMeisai.clearSelection();
			TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
			if(hmEditor != null){
				HaigoMeisai.getCellEditor().stopCellEditing();
			}
			//リスト明細の選択情報をクリア
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}

			//原料行を1行毎に削除
			for(int i=delete_start; i<=delete_end; i++){
				//テーブル表示値削除
				HaigoMeisai.tableDeleteRow(row);
				ListMeisai.tableDeleteRow(row);
				//エディタ＆レンダラ削除
				Trial1.delHaigoMeisaiRowER(row);
				Trial1.delListShisakuRowER(row);
				Trial1.setHaigoMeisaiER();
				Trial1.setListShisakuER();
			}

			//---------------------------- データ削除  -------------------------------------
			DataCtrl.getInstance().getTrialTblData().DelHaigoKoutei(Integer.parseInt(pk_KoteiCd));
			//工程行データが0件の場合
			if(DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0).size() == 0){
				//工程データ追加
				MixedData addMixedData = DataCtrl.getInstance().getTrialTblData().AddHaigoKoutei();
				String addPk_koteiCd = Integer.toString(addMixedData.getIntKoteiCd());
				String addPk_koteiSeq = Integer.toString(addMixedData.getIntKoteiSeq());
				int insert_row = 0;
				maxKotei = 1;

				//配合明細テーブルへ工程行挿入
				HaigoMeisai.tableInsertRow(insert_row);
				Trial1.addHaigoMeisaiRowER(insert_row, 1, addPk_koteiCd, "1", -1);
				Trial1.setHaigoMeisaiER();
				//試作明細テーブルへ工程行挿入
				ListMeisai.tableInsertRow(insert_row);

				for(int i=0; i<ListMeisai.getColumnCount(); i++){
					Trial1.addListShisakuRowER(i, insert_row, 1);
				}

				Trial1.setListShisakuER();
				//行の高さを設定
				HaigoMeisai.setRowHeight(insert_row, 17);
				ListMeisai.setRowHeight(insert_row, 17);

				//配合明細テーブルへ原料行挿入
				HaigoMeisai.tableInsertRow(insert_row+1);
				Trial1.addHaigoMeisaiRowER(insert_row+1, 0, addPk_koteiCd, addPk_koteiSeq,Integer.parseInt(addMixedData.getStrIro()));
				Trial1.setHaigoMeisaiER();
				//試作明細テーブルへ原料行挿入
				ListMeisai.tableInsertRow(insert_row+1);

				for(int i=0; i<ListMeisai.getColumnCount(); i++){
					Trial1.addListShisakuRowER(i, insert_row+1, 0);
				}

				Trial1.setListShisakuER();
			}else{
				//---------------------------- 計算列削除 ------------------------------------
				// MOD start 20120914 QP@20505 No.1
				//計算対象列数取得
				//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
				//int keisanRow = ListMeisai.getRowCount()-8-1;
//				int keisanRow = ListMeisai.getRowCount()-9-1;
				//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
				int keisanRow = ListMeisai.getRowCount()-beforeKotei-9-1;
				int siagariRow = ListMeisai.getRowCount()-9-1-beforeKotei + Integer.parseInt(pk_KoteiSelect);
				// MOD end 20120914 QP@20505 No.1

				//テーブル表示値削除
				HaigoMeisai.tableDeleteRow(keisanRow);
				ListMeisai.tableDeleteRow(keisanRow);
				//エディタ＆レンダラ削除
				Trial1.delHaigoMeisaiRowER(keisanRow);
				Trial1.delListShisakuRowER(keisanRow);
				Trial1.setHaigoMeisaiER();
				Trial1.setListShisakuER();

				// ADD start 20120914 QP@20505 No.1
				//テーブル表示値削除
				HaigoMeisai.tableDeleteRow(siagariRow);
				ListMeisai.tableDeleteRow(siagariRow);
				//エディタ＆レンダラ削除
				Trial1.delHaigoMeisaiRowER(siagariRow);
				Trial1.delListShisakuRowER(siagariRow);
				Trial1.setHaigoMeisaiER();
				Trial1.setListShisakuER();

				for(int i = 0;i <= ListMeisai.getRowCount()-9 - siagariRow;i++){
					Trial1.getHaigoMeisai().setValueAt(i+ Integer.parseInt(pk_KoteiSelect) + "工程仕上重量（ｇ）", siagariRow + i, 4);
				}
				// ADD end 20120914 QP@20505 No.1

			}

			//------------------------ 工程＆原料順再設定  --------------------------------
			int koteiNo = 0;
			//System.out.println(maxKotei);

			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-8; i++){
			//for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-9; i++){
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			for(int i=0; i<HaigoMeisai.getRowCount()-(maxKotei*2)-9; i++){
			// MOD end 20120914 QP@20505 No.1

				MiddleCellEditor mcKoteiNo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor tcKoteiNo = (DefaultCellEditor)mcKoteiNo.getTableCellEditor(i);
				if(((JComponent)tcKoteiNo.getComponent()) instanceof CheckboxBase){
					koteiNo++;
					//コンポーネントにキー項目を設定
					CheckboxBase setKotei = (CheckboxBase)tcKoteiNo.getComponent();
					setKotei.setPk3(Integer.toString(koteiNo));
					setKotei.setPk4("");
					setKotei.setPk5("");
				}
				//テーブル内表示値設定
				HaigoMeisai.setValueAt(Integer.toString(koteiNo), i, 1);

				//配合データ工程＆原料順設定
				//コンポーネント取得（原料選択チェックボックス）
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

				//原料選択チェックボックスの場合
				if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){

					//コンポーネントより対象工程CD、工程SEQ取得
					CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
					String count_KoteiCd = selectCb.getPk1();
					String count_KoteiSeq = selectCb.getPk2();

					//原料順設定
					DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
							count_KoteiCd, count_KoteiSeq, koteiNo, i);
				}
			}
			//最大工程順設定
			DataCtrl.getInstance().getTrialTblData().setIntMaxKotei(koteiNo);

			//工程合計計算
			for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
				Trial1.koteiSum(col);
			}
			//自動計算
			Trial1.AutoKeisan();

			//テスト表示
//			DataCtrl.getInstance().getTrialTblData().dispHaigo();
//			DataCtrl.getInstance().getTrialTblData().dispProtoList();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作データ画面 試作表① 工程行削除処理が失敗しました");
			ex.setStrErrmsg("試作データ画面 配合表 工程行削除処理が失敗しました");
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
	 *   工程行移動
	 *    : 配合明細、試作リストテーブルの工程行（紐付く原料行）を移動する
	 *   @author TT nishigawa
	 *   @param row  : 移動元の工程行番号
	 *   @param hoko : 移動方向（0=下 or 1=上）
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoMoveKoteiRow(int row,int hoko) throws ExceptionBase{
		try{
			//-------------------------- 移動元行設定  ------------------------------------
			//テーブル取得
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();
			TableBase ListMeisai = Trial1.getListMeisai();

			//コンポ－ネント取得
			MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 0);
			DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(row);
			CheckboxBase CheckboxBase = (CheckboxBase)tc.getComponent();
			//工程キー項目取得
			String pk_KoteiCd = CheckboxBase.getPk1();
			String pk_KoteiSeq = CheckboxBase.getPk2();
			// ADD start 20120914 QP@20505 No.1
			String pk_KoteiSelect = CheckboxBase.getPk3();
			// ADD end 20120914 QP@20505 No.1

			//配合データ数取得
			ArrayList arymoto_kotei =
				DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(pk_KoteiCd));

			//移動元行設定
			int move_moto = row;
			int move_size = arymoto_kotei.size();

			//--------------------------- 移動先行設定  -----------------------------------
			String koteiCd_saki = "";
			int move_saki = 0;
			int move_saki_size = 0;

			// ADD start 20120914 QP@20505 No.1
			int koteiShiagari_saki = 0;
			// ADD end 20120914 QP@20505 No.1

			boolean move_flg = true;
			//---------------- 上移動の場合  ----------------
			if(hoko == 1){
				move_moto = move_moto + move_size;
				//先頭行でない場合に上方向移動処理を開始
				if(row > 0){
					//移動先行の検索
					for(int i=row-1; i>=0; i--){
						MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
						DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
						//1列目に工程選択チェックボックスがある場合、最終行でない
						if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
							CheckboxBase up_chkBox = (CheckboxBase)DefaultCellEditor.getComponent();
							koteiCd_saki = up_chkBox.getPk1();
							move_saki = i;

							// ADD start 20120914 QP@20505 No.1
							koteiShiagari_saki = Integer.parseInt(up_chkBox.getPk3());
							// ADD end 20120914 QP@20505 No.1

							i = 0; //ループアウト
						}
					}
					//移動先工程の配合データ数取得
					ArrayList saki_koteiData =
						DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(koteiCd_saki));
				}else{
					move_flg = false;
				}
			//---------------- 下移動の場合  ----------------
			}else{
				move_moto = row;
				//移動先行の検索処理
				for(int i=row+1; i<HaigoMeisai.getRowCount(); i++){
					MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
					DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
					//1列目に工程選択チェックボックスがある場合、最終行でない
					if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
						CheckboxBase down_chkBox = (CheckboxBase)DefaultCellEditor.getComponent();
						koteiCd_saki = down_chkBox.getPk1();
						move_saki = i;

						// ADD start 20120914 QP@20505 No.1
						koteiShiagari_saki = Integer.parseInt(down_chkBox.getPk3());
						// ADD end 20120914 QP@20505 No.1

						i = HaigoMeisai.getRowCount(); //ループアウト
					}
				}
				//最終行でない場合に下方向移動処理を開始
				if(move_saki > 0){
					//移動先工程の配合データ数取得
					ArrayList saki_koteiData =
						DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(koteiCd_saki));
					//移動先行の設定
					move_saki = move_saki + saki_koteiData.size();
				}else{
					move_flg = false;
				}
			}
			//--------------------------- 表示値移動  -------------------------------------
			if(move_flg){
				//配合明細の選択情報をクリア
				HaigoMeisai.clearSelection();
				TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
				if(hmEditor != null){
					HaigoMeisai.getCellEditor().stopCellEditing();
				}
				//リスト明細の選択情報をクリア
				ListMeisai.clearSelection();
				TableCellEditor lmEditor = ListMeisai.getCellEditor();
				if(lmEditor != null){
					ListMeisai.getCellEditor().stopCellEditing();
				}
				//データ移動(画面)
				for(int i=0; i<=move_size; i++){
					//配合データ移動(画面)
					HaigoMeisai.tableMoveRow(move_moto,move_saki);
					Trial1.moveHaigoMeisaiRowER(move_moto, move_saki);
					Trial1.setHaigoMeisaiER();
					//試作リストデータ移動(画面)
					ListMeisai.tableMoveRow(move_moto,move_saki);
					Trial1.moveListShisakuRowER(move_moto, move_saki);
					Trial1.setListShisakuER();
				}

				// ADD start 20120914 QP@20505 No.1
				int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
				int ListRow = ListMeisai.getRowCount();
				int shiagari_moto = ListRow - 9 - maxKotei + Integer.parseInt(pk_KoteiSelect);
				int shiagari_saki = ListRow - 9 - maxKotei + koteiShiagari_saki;
				ListMeisai.tableMoveRow(shiagari_moto, shiagari_saki);
				Trial1.moveListShisakuRowER(shiagari_moto, shiagari_saki);
				Trial1.setListShisakuER();
				// ADD end 20120914 QP@20505 No.1

				//工程行の高さ設定
				for(int i=0; i<HaigoMeisai.getRowCount(); i++){
					MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
					DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
					//1列目に工程選択チェックボックスがある場合
					if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
						HaigoMeisai.setRowHeight(i, 17);
						ListMeisai.setRowHeight(i, 17);
					}else{
						HaigoMeisai.setRowHeight(i, 17);
						ListMeisai.setRowHeight(i, 17);
					}
				}
			}
			//----------------------- 工程＆原料順再設定  ----------------------------------
			//配合データ取得
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			//最大工程順取得
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			int koteiNo = 0;

			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-8; i++){
//			for(int i=0; i<HaigoMeisai.getRowCount()-maxKotei-9; i++){
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			for(int i = 0;i < HaigoMeisai.getRowCount() - (maxKotei * 2) - 9;i++){
			// ADD end 20120914 QP@20505 No.1

				MiddleCellEditor mcKoteiNo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor tcKoteiNo = (DefaultCellEditor)mcKoteiNo.getTableCellEditor(i);
				if(((JComponent)tcKoteiNo.getComponent()) instanceof CheckboxBase){
					koteiNo++;
					//コンポーネントにキー項目を設定
					CheckboxBase setKotei = (CheckboxBase)tcKoteiNo.getComponent();
					setKotei.setPk3(Integer.toString(koteiNo));
					setKotei.setPk4("");
					setKotei.setPk5("");
				}
				//テーブル内表示値設定
				HaigoMeisai.setValueAt(Integer.toString(koteiNo), i, 1);

				//配合データ工程＆原料順設定
				//コンポーネント取得（原料選択チェックボックス）
				MiddleCellEditor selectMc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor selectDc = (DefaultCellEditor)selectMc.getTableCellEditor(i);

				//原料選択チェックボックスの場合
				if(((JComponent)selectDc.getComponent()) instanceof CheckboxBase){
					//コンポーネントより対象工程CD、工程SEQ取得
					CheckboxBase selectCb = (CheckboxBase)selectDc.getComponent();
					String count_KoteiCd = selectCb.getPk1();
					String count_KoteiSeq = selectCb.getPk2();
					//原料順設定
					DataCtrl.getInstance().getTrialTblData().NoHaigoGenryo(
							count_KoteiCd, count_KoteiSeq, koteiNo, i);
				}
			}

			//工程合計計算
			for(int col=0; col<Trial1.getListMeisai().getColumnCount(); col++){
				Trial1.koteiSum(col);
			}


			//テスト表示
//			DataCtrl.getInstance().getTrialTblData().dispHaigo();
//			DataCtrl.getInstance().getTrialTblData().dispProtoList();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表①パネルクラス　工程行移動処理が失敗しました");
			ex.setStrErrmsg("配合表パネルクラス　工程行移動処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {
			//テスト表示
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		}

	}

	/************************************************************************************
	 *
	 *   試作列挿入
	 *    : 試作列テーブルの試作列を挿入する
	 *   @author TT nishigawa
	 *   @param  col  : 追加列番号
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoInsShisakuCol(int col) throws ExceptionBase{
		try{
			//---------------------------- テーブル取得  ------------------------------------
			TableBase ListHeader = Trial1.getListHeader();
			TableBase ListMeisai = Trial1.getListMeisai();
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();

			//----------------------------- データ挿入  -------------------------------------
			TrialData TrialData = DataCtrl.getInstance().getTrialTblData().AddShisakuRetu();

			//原価原料データの挿入
			DataCtrl.getInstance().getTrialTblData().AddGenkaGenryoData(TrialData.getIntShisakuSeq());

//2011/05/12 QP@10181_No.30 TT Nishigawa Change Start -------------------------

			setTp2_5TableHiju_tp1(TrialData.getIntShisakuSeq());

			//サンプル列ヘッダテーブル取得
			TableBase tbListHeader = this.getTrial1().getListHeader();

			//試作SEQの取得_先頭列
    		MiddleCellEditor mceShisakuSeq_sen = (MiddleCellEditor)tbListHeader.getCellEditor(0, 0);
			DefaultCellEditor dceShisakuSeq_sen = (DefaultCellEditor)mceShisakuSeq_sen.getTableCellEditor(0);
			CheckboxBase chkShisakuSeq_sen = (CheckboxBase)dceShisakuSeq_sen.getComponent();
			int intShisakuSeq_sen = Integer.parseInt(chkShisakuSeq_sen.getPk1());

			//先頭列の有効歩留、平均充填量取得
    		ArrayList genkaData_sen = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(intShisakuSeq_sen);
    		CostMaterialData costMaterialData_sen = (CostMaterialData)genkaData_sen.get(0);
    		String strBudomari_sen = costMaterialData_sen.getStrYukoBudomari();
    		String strZyutenAve_sen = costMaterialData_sen.getStrZyutenAve();

    		//追加列の有効歩留、平均充填量に先頭列のものを設定
    		ArrayList genkaData_ins = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(TrialData.getIntShisakuSeq());
    		CostMaterialData costMaterialData_ins = (CostMaterialData)genkaData_ins.get(0);
    		costMaterialData_ins.setStrYukoBudomari(strBudomari_sen);
    		costMaterialData_ins.setStrZyutenAve(strZyutenAve_sen);

//2011/05/12 QP@10181_No.30 TT Nishigawa Change end   -------------------------


			//---------------------------- 画面内挿入  -------------------------------------
			//試作ヘッダーの選択情報をクリア
			ListHeader.clearSelection();
			TableCellEditor hmEditor = ListHeader.getCellEditor();
			if(hmEditor != null){
				ListHeader.getCellEditor().stopCellEditing();
			}
			//リスト明細の選択情報をクリア
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}
			col++;
			ListHeader.tableInsertColumn(col);
			ListMeisai.tableInsertColumn(col);

			//------------------------- エディタ＆レンダラ挿入  ---------------------------------
			Trial1.addRetuShisakuColER(col, Integer.toString(TrialData.getIntShisakuSeq()));
			Trial1.addListShisakuColER(col);
			Trial1.setHeaderShisakuER();
			Trial1.setListShisakuER();

			//-------------------------- 工程行の高さ設定  ----------------------------------
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
				//1列目に工程選択チェックボックスがある場合
				if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
					ListMeisai.setRowHeight(i, 17);
				}else{
					ListMeisai.setRowHeight(i, 17);
				}
			}

			//---------------------------- 試作順の設定  -----------------------------------
			setShisakuColNo();

			//工程合計計算
			Trial1.koteiSum(col);
			//自動計算
			Trial1.AutoKeisan();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			e.printStackTrace();

			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表①パネルクラス　試作列挿入処理が失敗しました");
			ex.setStrErrmsg("配合表パネルクラス　試作列挿入処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName() + ":HaigoInsShisakuCol");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {
			//テスト表示
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();

		}

	}

	/************************************************************************************
	 *
	 *   試作列削除
	 *    : 試作列テーブルの試作列を削除する
	 *   @author TT nishigawa
	 *   @param  col  : 削除列番号
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void HaigoDelShisakuCol(int col) throws ExceptionBase{
		try{
			//---------------------------- テーブル取得  ------------------------------------
			TableBase ListHeader = Trial1.getListHeader();
			TableBase ListMeisai = Trial1.getListMeisai();
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();

			//----------------------------- データ削除  -------------------------------------
			//試作SEQ取得
			int delSeq = 0;
			MiddleCellEditor deleteMc = (MiddleCellEditor)ListHeader.getCellEditor(0, col);
			DefaultCellEditor deleteDc = (DefaultCellEditor)deleteMc.getTableCellEditor(0);
			CheckboxBase CheckboxBase = (CheckboxBase)deleteDc.getComponent();
			delSeq = Integer.parseInt(CheckboxBase.getPk1());
			DataCtrl.getInstance().getTrialTblData().DelShsiakuRetu(delSeq);

			//---------------------------- 画面内削除  -------------------------------------
			//試作ヘッダーの選択情報をクリア
			ListHeader.clearSelection();
			TableCellEditor hmEditor = ListHeader.getCellEditor();
			if(hmEditor != null){
				ListHeader.getCellEditor().stopCellEditing();
			}
			//リスト明細の選択情報をクリア
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}
			ListHeader.tableDeleteColumn(col);
			ListMeisai.tableDeleteColumn(col);

			//------------------------- エディタ＆レンダラ削除  ---------------------------------
			Trial1.removeHeaderShisakuColER(col);
			Trial1.removeListShisakuColER(col);
			Trial1.setHeaderShisakuER();
			Trial1.setListShisakuER();

			//---------------------- 試作列が全て削除された場合  ------------------------------
			if(ListHeader.getColumnCount() == 0){
				//新規に試作列を挿入
				TrialData TrialData = DataCtrl.getInstance().getTrialTblData().AddShisakuRetu();
				//画面内挿入
				ListHeader.tableInsertColumn(col);
				ListMeisai.tableInsertColumn(col);
				//エディタ＆レンダラ挿入
				Trial1.addRetuShisakuColER(col, Integer.toString(TrialData.getIntShisakuSeq()));
				Trial1.addListShisakuColER(col);
				Trial1.setHeaderShisakuER();
				Trial1.setListShisakuER();
			}

			//---------------------------- 試作順の設定  -----------------------------------
			setShisakuColNo();

			//-------------------------- 工程行の高さ設定  ----------------------------------
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
				//1列目に工程選択チェックボックスがある場合
				if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
					ListMeisai.setRowHeight(i, 17);
				}else{
					ListMeisai.setRowHeight(i, 17);
				}
			}

			//-------------------------- 注意事項No削除  ------------------------------------
			//製造工程パネル取得
			ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();
			//削除SEQと現在表示されている製造工程内の試作SEQが等しい場合
			if(delSeq == pb.getShisakuSeq()){
				//製造工程枠初期化
				pb.init();
			}

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表①パネルクラス　試作列削除処理が失敗しました");
			ex.setStrErrmsg("配合表パネルクラス　試作列削除処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {
			//テスト表示
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
			//DataCtrl.getInstance().getTrialTblData().dispProtoList();
			//DataCtrl.getInstance().getTrialTblData().dispManufacturingData();

		}

	}

	/************************************************************************************
	 *
	 *   試作列移動
	 *    : 試作列テーブルの試作列を移動する
	 *   @author TT nishigawa
	 *   @param  col_moto : 移動元列番号
	 *   @param  hoko     : 移動方向（0=左 or 1=右）
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void moveRetuShisakuCol(int col, int hoko) throws ExceptionBase{
		try{
			//---------------------------- テーブル取得  ------------------------------------
			TableBase ListHeader = Trial1.getListHeader();
			TableBase ListMeisai = Trial1.getListMeisai();
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();

			//---------------------------- 画面内移動  -------------------------------------
			//試作ヘッダーの選択情報をクリア
			ListHeader.clearSelection();
			TableCellEditor hmEditor = ListHeader.getCellEditor();
			if(hmEditor != null){
				ListHeader.getCellEditor().stopCellEditing();
			}
			//リスト明細の選択情報をクリア
			ListMeisai.clearSelection();
			TableCellEditor lmEditor = ListMeisai.getCellEditor();
			if(lmEditor != null){
				ListMeisai.getCellEditor().stopCellEditing();
			}
			//左移動
			if(hoko == 0){
				if(col > 0){
					ListHeader.tableMoveColumn( col, col-1 );
					ListMeisai.tableMoveColumn( col, col-1 );
				}
			//右移動
			}else{
				if(col < ListHeader.getColumnCount()-1){
					ListHeader.tableMoveColumn( col, col+1 );
					ListMeisai.tableMoveColumn( col, col+1 );
				}
			}

			//------------------------- エディタ＆レンダラ移動  ---------------------------------
			//左移動
			if(hoko == 0){
				if(col > 0){
					Trial1.changeHeaderShisakuColER(col, col-1);
					Trial1.changeListShisakuColER(col, col-1);
					Trial1.setHeaderShisakuER();
					Trial1.setListShisakuER();
				}
			//右移動
			}else{
				if(col < ListHeader.getColumnCount()-1){
					Trial1.changeHeaderShisakuColER(col, col+1);
					Trial1.changeListShisakuColER(col, col+1);
					Trial1.setHeaderShisakuER();
					Trial1.setListShisakuER();
				}
			}

			//---------------------------- 試作順の設定  -----------------------------------
			setShisakuColNo();

			//-------------------------- 工程行の高さ設定  ----------------------------------
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(i);
				//1列目に工程選択チェックボックスがある場合
				if(((JComponent)DefaultCellEditor.getComponent()) instanceof CheckboxBase){
					ListMeisai.setRowHeight(i, 17);
				}else{
					ListMeisai.setRowHeight(i, 17);
				}
			}

			//テスト表示
//			DataCtrl.getInstance().getTrialTblData().dispTrial();
//			DataCtrl.getInstance().getTrialTblData().dispProtoList();
//			DataCtrl.getInstance().getTrialTblData().dispManufacturingData();

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表①パネルクラス　試作列移動処理が失敗しました");
			ex.setStrErrmsg("配合表パネルクラス　試作列移動処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   試作列順設定
	 *    : 試作列の表示順を設定する
	 *   @author TT nishigawa
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void setShisakuColNo() throws ExceptionBase{
		try{
			//---------------------------- テーブル取得  ------------------------------------
			TableBase ListHeader = Trial1.getListHeader();

			//---------------------------- 試作順の設定  -----------------------------------
			int shisaku_no = 1;
			for(int i=0; i<ListHeader.getColumnCount(); i++){
				//コンポーネント取得
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
				DefaultCellEditor DefaultCellEditor = (DefaultCellEditor)MiddleCellEditor.getTableCellEditor(0);
				CheckboxBase CheckboxBase = (CheckboxBase)DefaultCellEditor.getComponent();
				//試作SEQ取得
				int noSeq = Integer.parseInt(CheckboxBase.getPk1());
				//試作順設定
				DataCtrl.getInstance().getTrialTblData().SetRetuNo(noSeq, shisaku_no);
				//試作Noカウント
				shisaku_no++;
			}

		} catch (ExceptionBase e) {
			throw e;

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表①パネルクラス　試作列順設定処理が失敗しました");
			ex.setStrErrmsg("配合表パネルクラス　試作列順設定処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		}finally{

		}

	}

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
	/************************************************************************************
	 *
	 *   最大行数チェック
	 *    : 最大行数（工程行＋原料行）のチェックを行う
	 *   @param row : 追加行数（工程追加=2行, 原料追加=1行）
	 *   @author TT k-katayama
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	private boolean CheckMaxKoteiGenryoRow(int insRow) throws ExceptionBase {
		boolean blnRet = true;

		try{
			int maxRow = 0;
			TableBase HaigoMeisai = Trial1.getHaigoMeisai();

			//最大行の取得
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){
				//工程選択設定
				MiddleCellEditor mcKotei = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
				DefaultCellEditor tcKotei = (DefaultCellEditor)mcKotei.getTableCellEditor(i);
				if(((JComponent)tcKotei.getComponent()) instanceof CheckboxBase){
					maxRow = i;

				}

				//原料選択設定
				MiddleCellEditor mcGenryo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor tcGenryo = (DefaultCellEditor)mcGenryo.getTableCellEditor(i);
				if(((JComponent)tcGenryo.getComponent()) instanceof CheckboxBase){
					maxRow = i;
				}
			}

			//最大行が150を超える場合、エラー処理を行う
			if (maxRow + insRow + 1 > 150) {
				//エラー設定
				String strMessage = JwsConstManager.JWS_ERROR_0024;
				DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);
				blnRet = false;
			}

		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("試作表①パネルクラス　最大行数チェック処理が失敗しました");
			ex.setStrErrmsg("配合表パネルクラス　最大行数チェック処理が失敗しました");
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;

		}finally{

		}

		return blnRet;
	}
//mod end --------------------------------------------------------------------------------------

	/************************************************************************************
	 *
	 *   配合表(試作表①)テーブル　ゲッタ&セッタ
	 *   @author TT nishigawa
	 *   @throws ExceptionBase
	 *
	 ************************************************************************************/
	public Trial1Table getTrial1() {
		return Trial1;
	}
	public void setTrial1(Trial1Table trial1) {
		Trial1 = trial1;
	}

	/************************************************************************************
	 *
	 *   サンプル説明書ボタン　ゲッタ
	 *   @author TT katayama
	 *
	 ************************************************************************************/
	public ButtonBase getBtnSample() {
		return this.btnSample;
	}

	/************************************************************************************
	 *
	 *   試作表出力ボタン　ゲッタ
	 *   @author TT katayama
	 *
	 ************************************************************************************/
	public ButtonBase getBtnShisakuHyo() {
		return this.btnShisakuHyo;
	}

	/************************************************************************************
	 *
	 *   栄養計算書ボタン　ゲッタ
	 *   @author TT katayama
	 *
	 ************************************************************************************/
	public ButtonBase getBtnEiyoKeisan() {
		return this.btnEiyoKeisan;
	}

	/************************************************************************************
	 *
	 *  特性値、原価試算テーブル設定処理
	 *    :  製品比重、水相比重、水相充填量、油相充填量を設定
	 *   @author TT nishigawa
	 *   @param なし
	 *
	 ************************************************************************************/
	public void setTp2_5TableHiju_tp1(int intSeq){
		try{
			//工程パターン取得
			String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();

			//容量単位取得
			String yoryoTani = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrTani();

			//容量単位からValue1取得
			String taniValue1 = "";
			if(yoryoTani == null || yoryoTani.length() == 0){

			}
			else{
				taniValue1 =  DataCtrl.getInstance().getLiteralDataTani().selectLiteralVal1(yoryoTani);
			}

				//工程パターンが「空白」の場合
				if(ptKotei == null || ptKotei.length() == 0){

					//製品比重　編集不可（初期値：空白）
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//水相比重　編集不可（初期値：空白）
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//「充填量水相」は編集不可
					DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
		    				JwsConstManager.JWS_COMPONENT_0134);

					//「充填量油相」は編集不可
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
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可（充填量計算）
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
						//容量が「g」の場合
						else if(taniValue1.equals("2")){

							//製品比重　編集不可（初期値：1）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可（充填量計算）
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0135);

						}
						//容量が「空白」の場合（ml,g以外の場合）
						else{

							//製品比重　編集不可（初期値：空白）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可
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
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集可（空白）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可（計算）
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可（計算）
							String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
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
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可（充填量水相計算）
							String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanSuisoZyuten(intSeq);
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可（充填量油相計算）
							String keisan2 = DataCtrl.getInstance().getTrialTblData().KeisanYusoZyuten(intSeq);
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
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//「充填量水相」は編集不可
							DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
				    				JwsConstManager.JWS_COMPONENT_0134);

							//「充填量油相」は編集不可
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
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0134);

						//「充填量油相」は編集可
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0135);

						//容量が「g」の場合
						if(taniValue1.equals("2")){

							//製品比重　編集不可（初期値：1）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString("1"),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

						}
						//容量が「空白」の場合（g以外の場合）
						else{

							//製品比重　編集不可（初期値：空白）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);

							//水相比重　編集不可（初期値：空白）
							DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSuiHiju(
				    				intSeq,
				    				DataCtrl.getInstance().getTrialTblData().checkNullString(null),
				    				DataCtrl.getInstance().getUserMstData().getDciUserid()
				    			);
						}
					}
				}
		}
		catch(Exception e){
			e.printStackTrace();

		}
	}
}
