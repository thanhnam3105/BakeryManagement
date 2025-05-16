package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;

import jp.co.blueflag.shisaquick.jws.base.ManufacturingData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.CheckboxBase;
import jp.co.blueflag.shisaquick.jws.common.ComboBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TextAreaBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.MessageCtrl;
import jp.co.blueflag.shisaquick.jws.table.Trial1Table;

/************************************************************************************
 *
 * 【A05-08】 製造工程パネル操作用のクラス
 *
 * @author TT.katayama
 * @since 2009/04/06
 *
 ************************************************************************************/
public class ManufacturingPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private ComboBase combo;					//コンボボックス操作
	//2011/04/19 QP@10181_No.31 TT T.Satoh Change Start -------------------------
	//private ItemLabel itemLabel;					//ラベル操作
	private ItemLabel[] itemLabel;					//ラベル操作
	//2011/04/19 QP@10181_No.31 TT T.Satoh Change End ---------------------------
	private CheckboxBase checkbox;			//チェックボックス操作
	private TextAreaBase memoText;			//メモテキスト
	private ScrollBase memoScroll;				//メモテキスト用スクロール
	private ButtonBase[] button;					//ボタン

	private MessageCtrl messageCtrl;			//メッセージ操作
	private ExceptionBase ex;						//エラー操作

	private int ShisakuSeq = 0;
	private int TyuuiNo = 0;

	private boolean tyuuiFg = false;			//注意事項表示フラグ
	private boolean memoFg = false;			//試作メモ表示フラグ

	/**
	 * コンストラクタ
	 */
	public ManufacturingPanel() throws ExceptionBase {
		//スーパークラスのコンストラクタを呼び出す
		super();

		try {
			//パネルの設定
			this.setPanel();

		} catch(Exception e) {
			e.printStackTrace();
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("製造工程パネルのコンストラクタが失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
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
		this.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
	}

	/************************************************************************************
	 *
	 * 初期化処理
	 *
	 ************************************************************************************/
	public void init() {
		ShisakuSeq = 0;
		TyuuiNo = 0;
		memoText.setText(null);

		//注意事項or試作メモ選択
		int sentaku = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntChuiFg();

		//選択無し
		if(sentaku == 0){

			tyuuiFg = false;
			memoFg = false;
			combo.setSelectedIndex(0);
			checkbox.setSelected(false);

		//注意事項表示
		}else if(sentaku == 1){

			tyuuiFg = true;
			memoFg = false;
			combo.setSelectedIndex(0);
			checkbox.setSelected(true);

		//試作メモ表示
		}else if(sentaku == 2){

			tyuuiFg = false;
			memoFg = true;
			combo.setSelectedIndex(1);
			checkbox.setSelected(true);

		}
	}

	/************************************************************************************
	 *
	 * コントロール配置
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	public void addControl() throws ExceptionBase{
		try{
			//初期化
			this.removeAll();

			int x, y, width, height;
			int dispWidth = 320;

			///
			/// コンボボックス
			///
			x = 5;
			y = 5;
			width = dispWidth - 20;
			height = 20;
			this.combo = new ComboBase();
			this.combo.addItem("製造工程/注意事項");
			this.combo.addItem("試作メモ");
			this.combo.addItem("製法No");
			this.combo.addActionListener(new selectCombo());
			this.combo.setBounds(x,y,width,height);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0050, DataCtrl.getInstance().getParamData().getStrMode())){
				combo.setBackground(Color.lightGray);
				combo.setEditable(false);
			}
			this.add(this.combo);

			///
			/// メモテキストエリア
			///
			this.memoText = new TextAreaBase();
			//座標・サイズ設定
			x = 0;
			y += height + 5;
			width = dispWidth - 8;
			height = 420;
			//テキスト折り返し可に設定
			this.memoText.setLineWrap(true);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0051, DataCtrl.getInstance().getParamData().getStrMode())){
				memoText.setBackground(Color.lightGray);
				memoText.setEditable(false);
			}
			//スクロールパネル生成
			this.memoScroll = new ScrollBase(this.memoText);
			this.memoScroll.setBounds(x,y,width,height);
			this.memoScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			//パネルに追加
			this.add(this.memoScroll);

			///
			/// ラベル(常に表示)
			///
			x = dispWidth - 80;
			y += height + 5;
			width = 50;
			height = 15;
			//2011/04/19 QP@10181_No.31 TT T.Satoh Change Start -------------------------
			//this.itemLabel = new ItemLabel();
			this.itemLabel = new ItemLabel[2];
			this.itemLabel[0] = new ItemLabel();
			//this.itemLabel.setText("常に表示");
			this.itemLabel[0].setText("常に表示");
			//this.itemLabel.setBounds(x,y,width,height);
			this.itemLabel[0].setBounds(x,y,width,height);
			//this.add(this.itemLabel);
			this.add(this.itemLabel[0]);
			//2011/04/19 QP@10181_No.31 TT T.Satoh Change End ---------------------------

			///
			/// 常に表示チェックボックス
			///
			x += width;
			width = 50;
			this.checkbox = new CheckboxBase();
			this.checkbox.setBounds(x,y,20,height);
			this.checkbox.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
			this.checkbox.addActionListener(new hyojiCheck());
			this.checkbox.setActionCommand("hyoji");
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0052, DataCtrl.getInstance().getParamData().getStrMode())){
				checkbox.setEnabled(false);
			}
			this.add(this.checkbox);

			//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
			///
			/// ラベル(工程版：)
			///
			x = 0;
			y += height + 5;
			width = 120;
			height = 15;
			this.itemLabel[1] = new ItemLabel();
			if (TyuuiNo == 0) {
				this.itemLabel[1].setText("<html>工程版：<font color=\"red\"><b>未選択です</font>");
			} else {
				this.itemLabel[1].setText("工程版：" + TyuuiNo);
			}
			this.itemLabel[1].setBounds(x,y,width,height);
			this.add(this.itemLabel[1]);
			//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------

			///
			/// ボタン
			/// [0:新規, 1:更新]
			///
			this.button = new ButtonBase[2];
			//新規
			width = 80;
			x = dispWidth - width - 100;
			//2011/04/19 QP@10181_No.31 TT T.Satoh Change Start -------------------------
			//y += height + 5;
			//2011/04/19 QP@10181_No.31 TT T.Satoh Change End ---------------------------
			height = 38;
			this.button[0] = new ButtonBase();
			this.button[0].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[0].setBounds(x,y,width,height);
			this.button[0].setText("新規");
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0053, DataCtrl.getInstance().getParamData().getStrMode())){
				button[0].setEnabled(false);
			}
			//更新
			x = dispWidth - 100;
			this.button[1] = new ButtonBase();
			this.button[1].setFont(new Font("Default", Font.PLAIN, 11));
			this.button[1].setBounds(x,y,width,height);
			this.button[1].setText("更新");
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0054, DataCtrl.getInstance().getParamData().getStrMode())){
				button[1].setEnabled(false);
			}
			//ボタンをパネルに追加
			for ( int i=0; i<this.button.length; i++ ) {
				this.add(this.button[i]);
			}

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception ec){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("製造工程パネルのコントロール配置処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 * 製造工程/注意事項表示
	 *
	 ************************************************************************************/
	public void dispSeizo() throws ExceptionBase{
		try{
			//製造工程/注意事項データ取得
			String seizo = "";
			if(TyuuiNo > 0){
				ArrayList tyuuiData = DataCtrl.getInstance().getTrialTblData().SearchSeizoKouteiData(TyuuiNo);
				ManufacturingData mfData = (ManufacturingData)tyuuiData.get(0);
				seizo = mfData.getStrTyuiNaiyo();
			}
			//表示
			memoText.setText(seizo);

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception ec){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("製造工程パネルの製造工程/注意事項表示処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 * 試作メモ表示
	 *
	 ************************************************************************************/
	public void dispMemo() throws ExceptionBase{
		try{
			String sisakuMemo = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShisakuMemo();

			//表示
			memoText.setText(sisakuMemo);

		}catch(Exception ec){
			ec.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("製造工程パネルの試作メモ表示が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 * 製法No表示
	 *
	 ************************************************************************************/
	public void dispSeiho() throws ExceptionBase{
		try{
			String strOut = "";
			if(ShisakuSeq > 0){
				//製法No取得
				ArrayList seihoData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(ShisakuSeq);
				TrialData trData = (TrialData)((DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(ShisakuSeq)).get(0));
				String seiho1 = trData.getStrSeihoNo1();
				String seiho2 = trData.getStrSeihoNo2();
				String seiho3 = trData.getStrSeihoNo3();
				String seiho4 = trData.getStrSeihoNo4();
				String seiho5 = trData.getStrSeihoNo5();
				//表示データ作成
				StringBuffer strMessage = new StringBuffer();
				strMessage.append((seiho1 == null)?"":seiho1);
				strMessage.append("\n"+((seiho2 == null)?"":seiho2));
				strMessage.append("\n"+((seiho3 == null)?"":seiho3));
				strMessage.append("\n"+((seiho4 == null)?"":seiho4));
				strMessage.append("\n"+((seiho5 == null)?"":seiho5));
				strOut = strMessage.toString();
			}
			//表示
			memoText.setText(strOut);

		}catch(ExceptionBase eb){
			throw eb;

		}catch(Exception ec){
			ec.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("製造工程パネルの製法No表示処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   常に表示チェックイベントクラス : 常に表示チェックボックス押下時の処理
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class hyojiCheck implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				String event_name = e.getActionCommand();

				// 【KPX1500671】ADD start
				// データ変更フラグON
				DataCtrl.getInstance().getTrialTblData().setHenkouFg(true);
				// 【KPX1500671】ADD end
				
				//常に表示チェックボックス押下
				if ( event_name == "hyoji") {

					//コンボボックス選択インデックス取得
					int selectCombo = combo.getSelectedIndex();

					//製造工程/注意事項　選択
					if(selectCombo == 0){

						//チェック
						if(checkbox.isSelected()){
							//製造工程/注意事項　表示FGをON
							tyuuiFg = true;

							//試作メモ　表示FGをOFF
							memoFg = false;

							//データ挿入
							DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setIntChuiFg(1);

						//チェック解除時
						}else{
							//製造工程/注意事項　表示FGをOFF
							tyuuiFg = false;

							//データ挿入
							DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setIntChuiFg(0);
						}

					//試作メモ　選択
					}else if(selectCombo == 1){

						//チェック
						if(checkbox.isSelected()){
							//製造工程/注意事項　表示FGをOFF
							tyuuiFg = false;

							//試作メモ　表示FGをON
							memoFg = true;

							//データ挿入
							DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setIntChuiFg(2);

						//チェック解除時
						}else{
							//試作メモ　表示FGをOFF
							memoFg = false;

							//データ挿入
							DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().setIntChuiFg(0);
						}


					//製法No　選択
					}else{


					}


				}
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{

			}
		}
	}


	/************************************************************************************
	 *
	 *   製造コンボ選択イベントクラス
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class selectCombo implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				//-------------------------- 製造工程表示処理  ----------------------------
				//製造工程の選択コンボボックスの選択値を取得
				ComboBase cb = (ComboBase)e.getSource();
				int selectCombo = cb.getSelectedIndex();

				//製造工程/注意事項の場合
				if(selectCombo == 0){

					//製造工程/注意事項表示
					dispSeizo();

					//常に表示チェックボックス設定
					checkbox.setEnabled(true);
					if(tyuuiFg){
						checkbox.setSelected(true);
					}else{
						checkbox.setSelected(false);
					}

					//テキストエリアの使用可否
					memoText.setBackground(Color.WHITE);
					memoText.setEnabled(true);

					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0051, DataCtrl.getInstance().getParamData().getStrMode())){
						memoText.setBackground(Color.lightGray);
						memoText.setEnabled(false);
					}

					//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
					//工程版表示
					itemLabel[1].setVisible(true);
					//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------

					//新規ボタンの使用可否
					button[0].setEnabled(true);

					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0053, DataCtrl.getInstance().getParamData().getStrMode())){
						button[0].setEnabled(false);
					}

					//更新ボタンの使用可否
					button[1].setEnabled(true);

					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0054, DataCtrl.getInstance().getParamData().getStrMode())){
						button[1].setEnabled(false);
					}
				}

				//試作メモの場合
				if(selectCombo == 1){

					//試作メモ表示
					dispMemo();

					//常に表示チェックボックス設定
					checkbox.setEnabled(true);
					if(memoFg){
						checkbox.setSelected(true);
					}else{
						checkbox.setSelected(false);
					}

					//テキストエリアの使用可否
					memoText.setBackground(Color.WHITE);
					memoText.setEnabled(true);

					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0051, DataCtrl.getInstance().getParamData().getStrMode())){
						memoText.setBackground(Color.lightGray);
						memoText.setEnabled(false);
					}

					//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
					//工程版非表示
					itemLabel[1].setVisible(false);
					//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------

					//新規ボタンの使用可否
					button[0].setEnabled(false);

					//更新ボタンの使用可否
					button[1].setEnabled(true);

					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0054, DataCtrl.getInstance().getParamData().getStrMode())){
						button[1].setEnabled(false);
					}
				}

				//製法Noの場合
				if(selectCombo == 2){
					//製法No表示
					dispSeiho();

					//常に表示チェックボックス設定
					checkbox.setSelected(false);
					checkbox.setEnabled(false);

					//メモテキストエリア
					memoText.setBackground(Color.lightGray);

					memoText.setEnabled(false);

					//2012/02/22 TT H.SHIMA Java6対応 start
					memoText.setBackground(Color.white);
					memoText.setDisabledTextColor(Color.BLACK);
					//2012/02/22 TT H.SHIMA Java6対応 end

					//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
					//工程版非表示
					itemLabel[1].setVisible(false);
					//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------

					//ボタンの使用可否
					button[0].setEnabled(false);
					button[1].setEnabled(false);
				}

			}catch(Exception ec){
				ec.printStackTrace();
				//エラー設定
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				ex.setStrErrmsg("製造工程パネルの製造コンボ選択処理が失敗しました");
				ex.setStrErrShori(this.getClass().getName());
				ex.setStrMsgNo("");
				ex.setStrSystemMsg(ec.getMessage());
				//メッセージ表示
				DataCtrl.getInstance().PrintMessage(ex);

			}finally{

			}
		}
	}

	/************************************************************************************
	 *
	 * 試作SEQセッター&ゲッター
	 *
	 ************************************************************************************/
	public int getShisakuSeq() {
		return ShisakuSeq;
	}
	public void setShisakuSeq(int shisakuSeq) {
		ShisakuSeq = shisakuSeq;
	}

	/************************************************************************************
	 *
	 * 注意事項Noセッター&ゲッター
	 *
	 ************************************************************************************/
	public int getTyuuiNo() {
		return TyuuiNo;
	}
	public void setTyuuiNo(int tyuuiNo) {
		TyuuiNo = tyuuiNo;
	}

	/************************************************************************************
	 *
	 * 常に表示チェックボックスセッター&ゲッター
	 *
	 ************************************************************************************/
	public CheckboxBase getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(CheckboxBase checkbox) {
		this.checkbox = checkbox;
	}

	/************************************************************************************
	 *
	 * 選択コンボボックスボックスセッター&ゲッター
	 *
	 ************************************************************************************/
	public ComboBase getCombo() {
		return combo;
	}
	public void setCombo(ComboBase combo) {
		this.combo = combo;
	}

	//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 *
	 * ラベル配列セッター&ゲッター
	 *
	 ************************************************************************************/
	public ItemLabel[] getLabel() {
		return itemLabel;
	}
	public void setLabel(ItemLabel[] itemLabel) {
		this.itemLabel = itemLabel;
	}
	//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------

	/************************************************************************************
	 *
	 * ボタン配列セッター&ゲッター
	 *
	 ************************************************************************************/
	public ButtonBase[] getButton() {
		return button;
	}
	public void setButton(ButtonBase[] button) {
		this.button = button;
	}

	/************************************************************************************
	 *
	 * テキストエリアセッター&ゲッター
	 *
	 ************************************************************************************/
	public TextAreaBase getMemoText() {
		return memoText;
	}

	public void setMemoText(TextAreaBase memoText) {
		this.memoText = memoText;
	}

	/************************************************************************************
	 *
	 * 製造工程/注意事項　表示FGゲッター
	 *
	 ************************************************************************************/
	public boolean isTyuuiFg() {
		return tyuuiFg;
	}

	/************************************************************************************
	 *
	 * 試作メモ　表示FGゲッター
	 *
	 ************************************************************************************/
	public boolean isMemoFg() {
		return memoFg;
	}


}