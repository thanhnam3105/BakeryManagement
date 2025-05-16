package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import jp.co.blueflag.shisaquick.jws.base.CostMaterialData;
import jp.co.blueflag.shisaquick.jws.base.ShisanData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextFieldCellEditor;
import jp.co.blueflag.shisaquick.jws.cellrenderer.CheckBoxCellRenderer;
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
import jp.co.blueflag.shisaquick.jws.label.ItemIndicationLabel;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.DownloadPathData;
import jp.co.blueflag.shisaquick.jws.manager.UrlConnection;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/*****************************************************************************************
 * 
 *   原価試算(試作表⑤)パネルクラス
 *   @author TT katayama
 *   
 *****************************************************************************************/
public class Trial5Panel extends PanelBase {
	
	private static final long serialVersionUID = 1L;

	//定数値
	private static final String CLICK_SHISAN_RIREKI = "click_shisanRireki";

	//データ&通信オブジェクト
	private XmlConnection xcon;
	private XmlData xmlJW840;						//試算確定サンプルNo検索(初期表示)
	private XmlData xmlJW850;						//試算確定履歴参照
	private XmlData xmlJW860;						//原価試算登録
	private XmlData xmlJ010;							//試算履歴用自動採番
	private XmlData xmlJW830;						//原価試算表出力

	//画面内コンポーネント
	private TableBase table;							//テーブル
	private ComboBase cmbShisanKakutei;			//試算確定サンプルNOコンボボックス
	private ButtonBase btnShisanHyo;				//原価試算表出力ボタン
	private ButtonBase btnShisanToroku;			//原価試算登録ボタン
	private ButtonBase btnShisanRireki;			//試算履歴参照ボタン

	//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
	//スクロールバー
	private JScrollPane scroll;
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------
	
	//エラー操作
	private ExceptionBase ex;
		
	/************************************************************************************
	 * 
	 * 原価試算(試作表⑤)パネルクラス　コンストラクタ
	 *   @throws ExceptionBase 
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	public Trial5Panel() throws ExceptionBase {
		
		super();
		
		try {
			//画面表示
			this.panelDisp();
			
			//試算確定サンプルNo検索処理
		
			//コンボボックスが初期化されていない場合、初期検索処理を行う
			if ( DataCtrl.getInstance().getShisanRirekiKanriData().getAryShisanKakuteiData().size() == 0 ) {
				
				//検索処理
				this.con_JW840();	
				
			}
			
			//試算確定サンプルNoコンボボックスを設定
			this.setShisanKakuteiCmb();
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ コンストラクタ処理が失敗しました");
			ex.setStrErrmsg("原価試算 コンストラクタ処理が失敗しました");
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
	 * 原価試算(試作表⑤)パネルクラス　画面表示
	 *   @throws ExceptionBase 
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	private void panelDisp() throws ExceptionBase{

		try {
			this.setLayout(null);
			this.setBackground(Color.WHITE);
			
			//試作データを取得
			Object[] aryTrialData = (DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0).toArray());
			
			//コンポーネント初期化処理
			this.initComponent(aryTrialData);
			
			//テーブル初期化処理
			this.initTable(aryTrialData);
			
			//2011/06/07 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			//原価試算の横スクロールバーの最大値を取得
			int hGenkaBarMax = this.getScroll().getHorizontalScrollBar().getMaximum();
			
			//原価試算の横スクロールバーの位置を設定
			this.getScroll().getHorizontalScrollBar().setValue(hGenkaBarMax);
			//2011/06/07 QP@10181_No.41 TT T.Satoh Add End ---------------------------

		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 画面表示処理が失敗しました");
			ex.setStrErrmsg("原価試算 画面表示処理が失敗しました");
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
	 * コンポーネント初期化処理（テーブルを除く）
	 *   @param aryTrialData : 試作データ
	 *   @throws ExceptionBase 
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	public void initComponent(Object[] aryTrialData) throws ExceptionBase {
		
		try {  
			
			int hll_x, hlr_x;				//ヘッダーX座標
			int hll_w, hlr_w;				//ヘッダー幅
			int hlr_h = 16;				//左側ヘッダー高さ
			
			hll_x = 15;						//左側ヘッダーX座標
			hll_w = 80;						//左側ヘッダー幅
			hlr_x = hll_x + hll_w - 1;	//右側ヘッダーX座標
			hlr_w = 180;					//右側ヘッダー幅
			
			//計算項目用文字列 : (計算)　と表示する
			String strKeisan = " (" + JwsConstManager.JWS_MARK_0005 + ")";
									
			//--------------------　①　----------------------------
			
			//項目ラベル設定（左側:①）
			ItemLabel hl = new ItemLabel();
			hl.setBorder(new LineBorder(Color.BLACK, 1));
			hl.setHorizontalAlignment(SwingConstants.CENTER);
			hl.setBounds(hll_x, 25, hll_w, 116);
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//hl.setText("①");
			hl.setText("配合表");
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			this.add(hl);
			
			//項目ラベル設定（サンプルNO）
			ItemLabel hlSampleNo = new ItemLabel();
			hlSampleNo.setBorder(new LineBorder(Color.BLACK, 1));
			hlSampleNo.setHorizontalAlignment(SwingConstants.LEFT);
			hlSampleNo.setBounds(hlr_x, 25, hlr_w, hlr_h + 10);
			hlSampleNo.setText(" サンプルNO");
			this.add(hlSampleNo);
			
			//項目ラベル設定（印刷FG）
			ItemLabel hlPrintFg = new ItemLabel();
			hlPrintFg.setBorder(new LineBorder(Color.BLACK, 1));
			hlPrintFg.setHorizontalAlignment(SwingConstants.LEFT);
			hlPrintFg.setBounds(hlr_x, 50, hlr_w, hlr_h);
			hlPrintFg.setText(" 印刷FG");
			this.add(hlPrintFg);
			
			//項目ラベル設定（原価試算依頼FG）
			ItemLabel genkaIraiFg = new ItemLabel();
			genkaIraiFg.setBorder(new LineBorder(Color.BLACK, 1));
			genkaIraiFg.setHorizontalAlignment(SwingConstants.LEFT);
			genkaIraiFg.setBounds(hlr_x, 65, hlr_w, hlr_h);
			genkaIraiFg.setText(" 原価試算依頼");
			this.add(genkaIraiFg);
			
			//項目ラベル設定（）
			ItemLabel hlJutenSuiso = new ItemLabel();
			hlJutenSuiso.setBorder(new LineBorder(Color.BLACK, 1));
			hlJutenSuiso.setHorizontalAlignment(SwingConstants.LEFT);
			hlJutenSuiso.setBounds(hlr_x, 80, hlr_w, hlr_h);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlJutenSuiso.setText(" 充填量水相(g)" + strKeisan);
			hlJutenSuiso.setText(" 充填量水相(g)" + strKeisan);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlJutenSuiso);
			
			//項目ラベル設定（充填量油相(g))
			ItemLabel hlJutenYuso = new ItemLabel();
			hlJutenYuso.setBorder(new LineBorder(Color.BLACK, 1));
			hlJutenYuso.setHorizontalAlignment(SwingConstants.LEFT);
			hlJutenYuso.setBounds(hlr_x, 95, hlr_w, hlr_h);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlJutenYuso.setText(" 充填量油相(g)" + strKeisan);
			hlJutenYuso.setText(" 充填量油相(g)" + strKeisan);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlJutenYuso);
			
			//項目ラベル設定（空白）
			ItemLabel hlKuhaku = new ItemLabel();
			hlKuhaku.setBorder(new LineBorder(Color.BLACK, 1));
			hlKuhaku.setHorizontalAlignment(SwingConstants.LEFT);
			hlKuhaku.setBounds(hlr_x, 110, hlr_w, hlr_h);
			hlKuhaku.setText("");
			this.add(hlKuhaku);
			
			//【計算項目】 項目ラベル設定（合計量(１本：g)(容量×比重)）
			ItemLabel hlGoukei = new ItemLabel();
			hlGoukei.setBorder(new LineBorder(Color.BLACK, 1));
			hlGoukei.setHorizontalAlignment(SwingConstants.LEFT);
			hlGoukei.setBounds(hlr_x, 125, hlr_w, hlr_h);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlGoukei.setText(" 合計量(１本：g)(容量×比重)");
			hlGoukei.setText(" 合計量(１本：g)(容量×比重)");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlGoukei); 
			
			//【計算項目】 項目ラベル設定（原料費(kg)）
			ItemLabel hlGenryohiKg = new ItemLabel();
			hlGenryohiKg.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenryohiKg.setHorizontalAlignment(SwingConstants.LEFT);
			hlGenryohiKg.setBounds(hlr_x, 140, hlr_w, hlr_h);
			hlGenryohiKg.setText(" 原料費(kg)");
			this.add(hlGenryohiKg);

			//-----------------------------------------------------

			//【計算項目】 項目ラベル設定（原料費(1本当)）
			ItemLabel hlGenryohi1Hon = new ItemLabel();
			hlGenryohi1Hon.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenryohi1Hon.setHorizontalAlignment(SwingConstants.LEFT);
			hlGenryohi1Hon.setBounds(hlr_x, 155, hlr_w, hlr_h);
			hlGenryohi1Hon.setText(" 原料費(1本当)");
			this.add(hlGenryohi1Hon);

			//--------------------　②　----------------------------
			
			//項目ラベル設定（左側:②）
			hl = new ItemLabel();
			hl.setBorder(new LineBorder(Color.BLACK, 1));
			hl.setHorizontalAlignment(SwingConstants.CENTER);
			hl.setBounds(hll_x, 170, hll_w, hlr_h);
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//hl.setText("②");
			hl.setText("特性値");
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			this.add(hl);
			
			//項目ラベル設定（比重）
			ItemLabel hlHiju = new ItemLabel();
			hlHiju.setBorder(new LineBorder(Color.BLACK, 1));
			hlHiju.setHorizontalAlignment(SwingConstants.LEFT);
			hlHiju.setBounds(hlr_x, 170, hlr_w, hlr_h);
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlHiju.setText(" 比重");
			hlHiju.setText(" 比重");
			//2011/05/06 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			this.add(hlHiju);

			//--------------------　③　----------------------------
			
			//項目ラベル設定（左側:③）
			hl = new ItemLabel();
			hl.setBorder(new LineBorder(Color.BLACK, 1));
			hl.setHorizontalAlignment(SwingConstants.CENTER);
			hl.setBounds(hll_x, 185, hll_w, 31);
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//hl.setText("③");
			hl.setText("基本情報");
			//2011/05/26 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			this.add(hl);
			
			//項目ラベル設定（容量）
			ItemLabel hlYoryo = new ItemLabel();
			hlYoryo.setBorder(new LineBorder(Color.BLACK, 1));
			hlYoryo.setHorizontalAlignment(SwingConstants.LEFT);
			hlYoryo.setBounds(hlr_x, 185, hlr_w, hlr_h);
			hlYoryo.setText(" 容量");
			this.add(hlYoryo);
			
			//項目ラベル設定（入り数）
			ItemLabel hlIrisu = new ItemLabel();
			hlIrisu.setBorder(new LineBorder(Color.BLACK, 1));
			hlIrisu.setHorizontalAlignment(SwingConstants.LEFT);
			hlIrisu.setBounds(hlr_x, 200, hlr_w, hlr_h);
			hlIrisu.setText(" 入り数");
			this.add(hlIrisu);

			//-----------------------------------------------------

			//項目ラベル設定（有効歩留(%)）
			ItemLabel hlYukoBudomari = new ItemLabel();
			hlYukoBudomari.setBorder(new LineBorder(Color.BLACK, 1));
			hlYukoBudomari.setHorizontalAlignment(SwingConstants.LEFT);
			hlYukoBudomari.setBounds(hlr_x, 215, hlr_w, hlr_h);
			hlYukoBudomari.setText(" 有効歩留(%)" + strKeisan);
			this.add(hlYukoBudomari);
			
			//項目ラベル設定（空白）
			hlKuhaku = new ItemLabel();
			hlKuhaku.setBorder(new LineBorder(Color.BLACK, 1));
			hlKuhaku.setHorizontalAlignment(SwingConstants.LEFT);
			hlKuhaku.setBounds(hlr_x, 230, hlr_w, hlr_h);
			hlKuhaku.setText("");
			this.add(hlKuhaku);
			
			//【計算項目】 項目ラベル設定（レベル量(g内容量×入数)）
			ItemLabel hlLebelRyo1 = new ItemLabel();
			hlLebelRyo1.setBorder(new LineBorder(Color.BLACK, 1));
			hlLebelRyo1.setHorizontalAlignment(SwingConstants.LEFT);
			hlLebelRyo1.setBounds(hlr_x, 245, hlr_w, hlr_h);
			hlLebelRyo1.setText(" レベル量(g内容量×入数)");
			this.add(hlLebelRyo1);

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.24
//			//【計算項目】 項目ラベル設定（比重加算量(gレベル量×比重)）
			//【計算項目】 項目ラベル設定（比重加算量(g平均充填量×比重)）
			ItemLabel hlLebelRyo2 = new ItemLabel();
			hlLebelRyo2.setBorder(new LineBorder(Color.BLACK, 1));
			hlLebelRyo2.setHorizontalAlignment(SwingConstants.LEFT);
			hlLebelRyo2.setBounds(hlr_x, 260, hlr_w, hlr_h);
//			hlLebelRyo2.setText(" 比重加算量(gレベル量×比重)");
			hlLebelRyo2.setText(" 比重加算量(g平均充填量×比重)");
			this.add(hlLebelRyo2);
//mod end --------------------------------------------------------------------------------------
			
			//項目ラベル設定（平均充填量(g)）
			ItemLabel hlLebelRyo3 = new ItemLabel();
			hlLebelRyo3.setBorder(new LineBorder(Color.BLACK, 1));
			hlLebelRyo3.setHorizontalAlignment(SwingConstants.LEFT);
			hlLebelRyo3.setBounds(hlr_x, 275, hlr_w, hlr_h);
			hlLebelRyo3.setText(" 平均充填量(g)" + strKeisan);
			this.add(hlLebelRyo3);

			//--------------------　1c/s当たりの計算　---------------

			//項目ラベル設定（左側:1c/s当たりの計算）
			ItemLabel hlKeisan1CS = new ItemLabel();
			hlKeisan1CS.setBorder(new LineBorder(Color.BLACK, 1));
			hlKeisan1CS.setFont(new Font("Default", Font.PLAIN, 9));
			hlKeisan1CS.setHorizontalAlignment(SwingConstants.CENTER);
			//2011/06/07 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//hlKeisan1CS.setBounds(hll_x, 290, hll_w, hlr_h);
			hlKeisan1CS.setBounds(hll_x, 290, hll_w, hlr_h * 4);
			//2011/06/07 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			hlKeisan1CS.setText("1c/s当たりの計算");
			this.add(hlKeisan1CS);
			
			//【計算項目】 項目ラベル設定（原料費）
			ItemLabel hlGenryohi = new ItemLabel();
			hlGenryohi.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenryohi.setHorizontalAlignment(SwingConstants.LEFT);
			hlGenryohi.setBounds(hlr_x, 290, hlr_w, hlr_h);
			hlGenryohi.setText(" 原料費");
			this.add(hlGenryohi);

			//--------------------　④　----------------------------

			//項目ラベル設定（左側:④）
			hl = new ItemLabel();
			hl.setBorder(new LineBorder(Color.BLACK, 1));
			hl.setHorizontalAlignment(SwingConstants.CENTER);
			hl.setBounds(hll_x, 305, hll_w, hlr_h);
			
//2011/05/26 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//hl.setText("④");
			hl.setText("");
//2011/05/26 QP@10181_No.26 TT T.Satoh Change End ---------------------------

			this.add(hl);
			//項目ラベル設定（材料費）
			ItemLabel hlZairyohi = new ItemLabel();
			hlZairyohi.setBorder(new LineBorder(Color.BLACK, 1));
			hlZairyohi.setHorizontalAlignment(SwingConstants.LEFT);
			hlZairyohi.setBounds(hlr_x, 305, hlr_w, hlr_h);
			hlZairyohi.setText(" 材料費" + strKeisan);
			this.add(hlZairyohi);

			//-----------------------------------------------------

			//項目ラベル設定（固定費）
			ItemLabel hlKeihi = new ItemLabel();
			hlKeihi.setBorder(new LineBorder(Color.BLACK, 1));
			hlKeihi.setHorizontalAlignment(SwingConstants.LEFT);
			hlKeihi.setBounds(hlr_x, 320, hlr_w, hlr_h);
			hlKeihi.setText(" 固定費" + strKeisan);
			this.add(hlKeihi);
			
			//【計算項目】 項目ラベル設定（原価計/cs）
			ItemLabel hlGenkakeiCs = new ItemLabel();
			hlGenkakeiCs.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenkakeiCs.setHorizontalAlignment(SwingConstants.LEFT);
			hlGenkakeiCs.setBounds(hlr_x, 335, hlr_w, hlr_h);
			hlGenkakeiCs.setText(" 原価計/cs");
			this.add(hlGenkakeiCs);

			//--------------------　1個当たりの計算　----------------

			//項目ラベル設定（左側:1個当たりの計算）
			ItemLabel hlKeisan1Ko = new ItemLabel();
			hlKeisan1Ko.setBorder(new LineBorder(Color.BLACK, 1));
			hlKeisan1Ko.setFont(new Font("Default", Font.PLAIN, 9));
			hlKeisan1Ko.setHorizontalAlignment(SwingConstants.CENTER);
			hlKeisan1Ko.setBounds(hll_x, 350, hll_w, hlr_h);
			hlKeisan1Ko.setText("1個当たりの計算");
			this.add(hlKeisan1Ko);
			
			//【計算項目】 項目ラベル設定（原価計/個）
			ItemLabel hlGenkakeiKo = new ItemLabel();
			hlGenkakeiKo.setBorder(new LineBorder(Color.BLACK, 1));
			hlGenkakeiKo.setHorizontalAlignment(SwingConstants.LEFT);
			hlGenkakeiKo.setBounds(hlr_x, 350, hlr_w, hlr_h);
			hlGenkakeiKo.setText(" 原価計/個");
			this.add(hlGenkakeiKo);

			//-----------------------------------------------------

			//項目ラベル設定（売価）
			ItemLabel hlBaika = new ItemLabel();
			hlBaika.setBorder(new LineBorder(Color.BLACK, 1));
			hlBaika.setHorizontalAlignment(SwingConstants.LEFT);
			hlBaika.setBounds(hlr_x, 365, hlr_w, hlr_h);
			hlBaika.setText(" 売価");
			this.add(hlBaika);
			
			//【計算項目】 項目ラベル設定（粗利(%)）
			ItemLabel hlArari = new ItemLabel();
			hlArari.setBorder(new LineBorder(Color.BLACK, 1));
			hlArari.setHorizontalAlignment(SwingConstants.LEFT);
			hlArari.setBounds(hlr_x, 380, hlr_w, hlr_h);
			hlArari.setText(" 粗利(%)");
			this.add(hlArari);
			
//2011/04/20 QP@10181_No.67 TT Nishigawa Change Start ---------------------------
			//【計算項目】 項目ラベル設定（依頼キャンセル）
			ItemLabel hlCancel = new ItemLabel();
			hlCancel.setBorder(new LineBorder(Color.BLACK, 1));
			hlCancel.setHorizontalAlignment(SwingConstants.LEFT);
			hlCancel.setBounds(hlr_x, 395, hlr_w, hlr_h);
			hlCancel.setText(" 依頼キャンセル");
			this.add(hlCancel);
//2011/04/20 QP@10181_No.67 TT Nishigawa Change End ---------------------------
			

			//-----------------------------------------------------

			//項目ラベル設定（備考）
			ItemLabel hlBikou = new ItemLabel();
			hlBikou.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
			hlBikou.setBorder(new LineBorder(Color.BLACK, 1));
			hlBikou.setBounds(hlr_x, 435+15, 15, 16);
			this.add(hlBikou);
			
			ItemIndicationLabel ilBikou = new ItemIndicationLabel();
			ilBikou.setBounds(hlBikou.getX() + 16, hlBikou.getY(), 120, 16);
			ilBikou.setText(" の項目は編集可能");
			this.add(ilBikou);

			//--------------------　試算確定サンプルNO　--------------------------

			//項目ラベル設定（粗利(%)）
			ItemLabel lblKakuteiSampleNo = new ItemLabel();
			lblKakuteiSampleNo.setBackground(Color.white);
			lblKakuteiSampleNo.setBorder(new LineBorder(Color.BLACK, 0));
			lblKakuteiSampleNo.setHorizontalAlignment(SwingConstants.CENTER);
			lblKakuteiSampleNo.setBounds(273, 419+15, 120, hlr_h);
			lblKakuteiSampleNo.setText(" 試算確定サンプルNo");
			this.add(lblKakuteiSampleNo);
			
			//コンボボックス設定（試算確定サンプルNO）
			cmbShisanKakutei = new ComboBase();
			cmbShisanKakutei.setBounds(273, 435+15, 120, 20);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0131, DataCtrl.getInstance().getParamData().getStrMode())){
				cmbShisanKakutei.setEnabled(false);
			}
			this.add(cmbShisanKakutei);

			//--------------------　ボタン　--------------------------

			//ボタン設定（原価試算登録）
			btnShisanToroku = new ButtonBase();
			btnShisanToroku.setBounds(cmbShisanKakutei.getX() + 120, 435+15, 80, 20);
			btnShisanToroku.setHorizontalAlignment(SwingConstants.CENTER);
			btnShisanToroku.setMargin(new Insets(0, 0, 0, 0));
			btnShisanToroku.setText("原価試算登録");
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0150, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisanToroku.setEnabled(false);
			}
			this.add(btnShisanToroku);
			
			//ボタン設定(試算履歴参照)
			btnShisanRireki = new ButtonBase();
			btnShisanRireki.setBounds(btnShisanToroku.getX() + 80, 435+15, 80, 20);
			btnShisanRireki.setHorizontalAlignment(SwingConstants.CENTER);
			btnShisanRireki.setMargin(new Insets(0, 0, 0, 0));
			btnShisanRireki.setText("試算履歴参照");
			btnShisanRireki.addActionListener(this.getActionEvent());
			btnShisanRireki.setActionCommand(CLICK_SHISAN_RIREKI);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0132, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisanRireki.setEnabled(false);
			}
			this.add(btnShisanRireki);
			
			//ボタン設定（原価試算表印刷）
			btnShisanHyo = new ButtonBase();
			btnShisanHyo.setBounds(1024 - 140, 435+15, 100, 20);
			btnShisanHyo.setHorizontalAlignment(SwingConstants.CENTER);
			btnShisanHyo.setMargin(new Insets(0, 0, 0, 0));
			btnShisanHyo.setText("原価試算表印刷");
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0133, DataCtrl.getInstance().getParamData().getStrMode())){
				btnShisanHyo.setEnabled(false);
			}
			this.add(btnShisanHyo);
			
			//------------------------------------------------------
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ コンポーネント初期化処理が失敗しました");
			ex.setStrErrmsg("原価試算 コンポーネント初期化処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}	
		
	}

	/************************************************************************************
	 * 
	 * テーブル初期化処理
	 *   @param aryTrialData : 試作データ
	 *   @throws ExceptionBase 
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	private void initTable(Object[] aryTrialData) throws ExceptionBase {
		
		//取得対象データ : 試作テーブル
		TrialData trialData = null;
		
		//取得対象データ : 原価原料テーブル
		CostMaterialData costMaterialData = null;
		//取得対象データ : 原価原料
		ArrayList aryCostMaterialData = null;
			
		try {
			//行・列数
			
//2011/04/20 QP@10181_No.67 TT Nishigawa Change Start -------------------------
//			int intRowCount = 24;
			int intRowCount = 25;
//2011/04/20 QP@10181_No.67 TT Nishigawa Change End ---------------------------
			
			int intColumnCount = aryTrialData.length;
			
			//テーブル生成
			//2011/04/20 QP@10181_No.41 TT T.Satoh Change Start -------------------------
			//JScrollPane scroll;
			//2011/04/20 QP@10181_No.41 TT T.Satoh Change End ---------------------------
			table = new TableBase(intRowCount,intColumnCount){

				private static final long serialVersionUID = 1L;
				
				//--------------------　原価原料データ更新　----------------------------
				public void editingStopped( ChangeEvent e ){
					try{
						super.editingStopped( e );
						
						//編集行列番号取得
						int row = getSelectedRow();
						int column = getSelectedColumn();
						if( row>=0 && column>=0 ){
							
							//キー項目取得
							MiddleCellEditor mceSeq = (MiddleCellEditor)this.getCellEditor(0, column);
							DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
							TextboxBase chkSeq = (TextboxBase)dceSeq.getComponent();
							int intSeq  = Integer.parseInt(chkSeq.getPk1());

							//--------------------　モード編集　----------------------------
					    	if(row == 1){
								//印刷FG
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0149, DataCtrl.getInstance().getParamData().getStrMode())){

									//原価原料データの更新
					    			String insert = (getValueAt( row, column ).toString()=="true")?"1":"0";
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaPrintFg(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid());
						    		
						    		//setValueAt(insert, row, column);
						    								    		
								}
								
					    	} 
					    	
					    	else if(row == 2){
								//原価試算依頼FG
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0151, DataCtrl.getInstance().getParamData().getStrMode())){

									//原価原料データの更新
					    			String insert = (getValueAt( row, column ).toString()=="true")?"1":"0";
					    			
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuGenkaIraiFg(
						    				intSeq,
						    				Integer.parseInt(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid());
						    		
						    		//setValueAt(insert, row, column);
						    								    		
								}
								
					    	} 
					    	
					    	else if (row == 3) {
								//充填量水相
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0134, DataCtrl.getInstance().getParamData().getStrMode())){

									//原価原料データの更新
					    			String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0134);

						    		//画面表示値の更新
						    		updDispValues(intSeq, column);
						    		
								}
								
					    	}
					    	
					    	else if (row == 4) {
								//充填量油相
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0135, DataCtrl.getInstance().getParamData().getStrMode())){

									//原価原料データの更新
					    			String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0135);

						    		//画面表示値の更新
						    		updDispValues(intSeq, column);
						    		
								}
								
					    	}
					    	
					    	else if (row == 12) {
								//有効歩留
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0127, DataCtrl.getInstance().getParamData().getStrMode())){

									//JTable内部の編集値を取得
					    			String insert = getValueAt( row, column ).toString();
					    			
					    			
					    			// QP@10181_No.30 -----------------------------------------------start
						    		//自身の列数を取得
						    		int intColomCnt = getColumnCount();
						    		
						    		//試作SEQの取得
						    		MiddleCellEditor mceShisakuSeq = (MiddleCellEditor)this.getCellEditor(0, column);
									DefaultCellEditor dceShisakuSeq = (DefaultCellEditor)mceShisakuSeq.getTableCellEditor(0);
									TextboxBase chkShisakuSeq = (TextboxBase)dceShisakuSeq.getComponent();
									int intShisakuSeq  = Integer.parseInt(chkShisakuSeq.getPk1());
						    		
						    		//データ内の有効歩留取得
						    		ArrayList genkaData = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(intShisakuSeq);
						    		CostMaterialData costMaterialData = (CostMaterialData)genkaData.get(0);
						    		String strYukoBudomari = costMaterialData.getStrYukoBudomari();
						    		
						    		//有効歩留（入力値）取得
						    		double dblInsert = -1;
						    		
						    		//有効歩留（入力値）がNULLの場合
						    		if(insert == null){
						    			
						    		}
						    		//有効歩留（入力値）がNULLでない場合
						    		else{
						    			if(insert.length() == 0){
						    				
						    			}
						    			else{
							    			try{
						    					dblInsert = Double.parseDouble(insert);
						    				}catch(Exception ee){
						    					dblInsert = 0.00;
						    				}
						    			}
						    		}
						    		
						    		
						    		//有効歩留（現在値）取得
						    		double dblBudomari = -1;
						    		
						    		//有効歩留（現在値）がNULLの場合
						    		if(strYukoBudomari == null){
						    			
						    		}
						    		//有効歩留（現在値）がNULLでない場合
						    		else{
						    			if(strYukoBudomari.length() == 0){
						    				
						    			}
						    			else{
						    				dblBudomari = Double.parseDouble(strYukoBudomari);
						    			}
						    		}
						    		
						    		//列数が１より大きい場合 且つ　入力値が現在の設定値と等しくない場合
							    	if( intColomCnt > 1 && dblInsert != dblBudomari ){
						    			
						    			//ダイアログコンポーネント設定
										JOptionPane jp = new JOptionPane();
										
										//確認ダイアログ表示
										int option = jp.showConfirmDialog(
												jp.getRootPane(),
												"有効歩留を他の列にも反映しますか？"
												, "確認メッセージ"
												,JOptionPane.YES_NO_OPTION
												,JOptionPane.PLAIN_MESSAGE
											);
										
										//「はい」押下
									    if (option == JOptionPane.YES_OPTION){
									    	
									    	//列数分ループ
									    	for( int i = 0; i < intColomCnt; i++ ){
									    		
									    		//試作SEQの取得
									    		MiddleCellEditor mceLoopSeq = (MiddleCellEditor)this.getCellEditor(0, i);
												DefaultCellEditor dceLoopSeq = (DefaultCellEditor)mceLoopSeq.getTableCellEditor(0);
												TextboxBase chkLoopSeq = (TextboxBase)dceLoopSeq.getComponent();
												int intLoopSeq  = Integer.parseInt(chkLoopSeq.getPk1());
									    		
									    		//原価原料データの更新
									    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
									    				intLoopSeq,
									    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
									    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
									    				JwsConstManager.JWS_COMPONENT_0127);

									    		//画面表示値の更新
									    		updDispValues(intLoopSeq, i);
									    		
									    	}
									    	
									    }
									    //「いいえ」押下
									    else if (option == JOptionPane.NO_OPTION){
									    	
									    	
									    }
						    		}
							    	// QP@10181_No.30 -----------------------------------------------end
							    	
					    			
					    			//原価原料データの更新
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0127);

						    		//画面表示値の更新
						    		updDispValues(intSeq, column);
						    		
								}
								
					    	}
					    	
					    	else if (row == 16) {
								//平均充填量
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0128, DataCtrl.getInstance().getParamData().getStrMode())){

									
									//JTable内部の編集値を取得
					    			String insert = getValueAt( row, column ).toString();
					    			
					    			
					    			// QP@10181_No.30 -----------------------------------------------start
						    		//自身の列数を取得
						    		int intColomCnt = getColumnCount();
						    		
						    		//試作SEQの取得
						    		MiddleCellEditor mceShisakuSeq = (MiddleCellEditor)this.getCellEditor(0, column);
									DefaultCellEditor dceShisakuSeq = (DefaultCellEditor)mceShisakuSeq.getTableCellEditor(0);
									TextboxBase chkShisakuSeq = (TextboxBase)dceShisakuSeq.getComponent();
									int intShisakuSeq  = Integer.parseInt(chkShisakuSeq.getPk1());
						    		
						    		//データ内の有効歩留取得
						    		ArrayList genkaData = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(intShisakuSeq);
						    		CostMaterialData costMaterialData = (CostMaterialData)genkaData.get(0);
						    		String strZyutenAve = costMaterialData.getStrZyutenAve();
						    		
						    		//平均充填量（入力値）の取得
						    		double dblInsert = -1;
						    		
						    		//平均充填量（入力値）がNULLの場合
						    		if(insert == null){
						    			
						    		}
						    		//平均充填量（入力値）がNULLでない場合
						    		else{
						    			if(insert.length() == 0){
						    				
						    			}
						    			else{
						    				try{
						    					dblInsert = Double.parseDouble(insert);
						    				}catch(Exception ee){
						    					dblInsert = 0.00;
						    				}
						    			}
						    		}
						    		
						    		//平均充填量（現在値）の取得
						    		double dblZyutenAve = -1;
						    		
						    		//平均充填量（現在値）がNULLの場合
						    		if(strZyutenAve == null){
						    			
						    		}
						    		//平均充填量（現在値）がNULLでない場合
						    		else{
						    			if(strZyutenAve.length() == 0){
						    				
						    			}
						    			else{
						    				dblZyutenAve = Double.parseDouble(strZyutenAve);
						    			}
						    		}
						    		
						    		//列数が１より大きい場合 且つ　入力値が現在の設定値と等しくない場合
							    	if( intColomCnt > 1 && dblInsert != dblZyutenAve ){
						    			
						    			//ダイアログコンポーネント設定
										JOptionPane jp = new JOptionPane();
										
										//確認ダイアログ表示
										int option = jp.showConfirmDialog(
												jp.getRootPane(),
												"平均充填量を他の列にも反映しますか？"
												, "確認メッセージ"
												,JOptionPane.YES_NO_OPTION
												,JOptionPane.PLAIN_MESSAGE
											);
										
										//「はい」押下
									    if (option == JOptionPane.YES_OPTION){
									    	
									    	//列数分ループ
									    	for( int i = 0; i < intColomCnt; i++ ){
									    		
									    		//試作SEQの取得
									    		MiddleCellEditor mceLoopSeq = (MiddleCellEditor)this.getCellEditor(0, i);
												DefaultCellEditor dceLoopSeq = (DefaultCellEditor)mceLoopSeq.getTableCellEditor(0);
												TextboxBase chkLoopSeq = (TextboxBase)dceLoopSeq.getComponent();
												int intLoopSeq  = Integer.parseInt(chkLoopSeq.getPk1());
									    		
												//原価原料データの更新
								    			DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
								    					intLoopSeq,
									    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
									    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
									    				JwsConstManager.JWS_COMPONENT_0128);

									    		//画面表示値の更新
									    		updDispValues(intLoopSeq, i);
									    		
									    	}
									    	
									    }
									    //「いいえ」押下
									    else if (option == JOptionPane.NO_OPTION){
									    	
									    	
									    }
						    		}
							    	// QP@10181_No.30 -----------------------------------------------end
					    			
						    		
					    			//原価原料データの更新
					    			DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0128);

						    		//画面表示値の更新
						    		updDispValues(intSeq, column);
						    		
								}
								
					    	}
					    	
					    	else if (row == 18) {
								//材料費
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0145, DataCtrl.getInstance().getParamData().getStrMode())){

									//原価原料データの更新
					    			String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0145);

						    		//画面表示値の更新
						    		updDispValues(intSeq, column);
						    		
								}
								
					    	}
					    	
					    	else if (row == 19) {
								//固定費
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0129, DataCtrl.getInstance().getParamData().getStrMode())){

									//原価原料データの更新
					    			String insert = getValueAt( row, column ).toString();
						    		DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
						    				JwsConstManager.JWS_COMPONENT_0129);

						    		//画面表示値の更新
						    		updDispValues(intSeq, column);
						    		
								}
																
					    	}
					    	
//2011/04/12 QP@10181_No.67 TT Nishigawa Change Start -------------------------
					    	else if(row == 24){
								//キャンセルFG
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0151, DataCtrl.getInstance().getParamData().getStrMode())){

									//原価原料データの更新
					    			String insert = (getValueAt( row, column ).toString()=="true")?"1":"0";
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuCancelIraiFg(
						    				intSeq,
						    				Integer.parseInt(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid());
								}
					    	} 
//2011/04/12 QP@10181_No.67 TT Nishigawa Change End -------------------------
					    	
						}
					}catch(ExceptionBase be){
						DataCtrl.getInstance().PrintMessage(be);
						
					}catch(Exception ex){
						//エラー設定
						ExceptionBase e1 = new ExceptionBase();
						ex.printStackTrace();
						e1.setStrErrCd("");
						//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
						//e1.setStrErrmsg("試作表⑤ イベント処理が失敗しました");
						e1.setStrErrmsg("原価試算 イベント処理が失敗しました");
						//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
						e1.setStrErrShori(this.getClass().getName());
						e1.setStrMsgNo("");
						e1.setStrSystemMsg(ex.getMessage());
						DataCtrl.getInstance().PrintMessage(e1);
												
					}finally{
						//テスト表示
//						DataCtrl.getInstance().getTrialTblData().dispCostMaterialData();
//						DataCtrl.getInstance().getTrialTblData().dispTrial();
						
					}
					
				}
				
			};
			table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setCellSelectionEnabled(true);
			
			//テーブルサイズ設定
			table.setRowHeight(15);
			table.setRowHeight(0,25);
			
			//スクロールパネル設定
			scroll = new JScrollPane( table ) {
				private static final long serialVersionUID = 1L;
				
				//ヘッダーを消去
				public void setColumnHeaderView(Component view) {} 
				
			};
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setBorder(new LineBorder(Color.BLACK, 1));
			scroll.setBounds(273, 25, 721, 403);
			scroll.setBackground(Color.WHITE);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			scroll.getHorizontalScrollBar().setMaximum(200*intColumnCount);
			scroll.getVerticalScrollBar().setMaximum(1000);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add End ---------------------------
			this.add(scroll,BorderLayout.CENTER);
			
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

				//------------------------ テーブルデータの取得  ------------------------

				//試作列データ取得（表示順）
				int no=0;
				for(int i=0; i<columnModel.getColumnCount(); i++){
					TrialData chkHyji = (TrialData)aryTrialData[i];
					if((chkHyji.getIntHyojiNo()-1) == j){
						no=i;
					}
				}
				trialData = (TrialData)aryTrialData[no];
				
				//原価原料テーブルデータの更新・追加 
				// (データが存在しない場合は、新規データを生成)
				DataCtrl.getInstance().getTrialTblData().AddGenkaGenryoData(trialData.getIntShisakuSeq());
				
				//原価原料を取得
				aryCostMaterialData = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(trialData.getIntShisakuSeq());
				costMaterialData = (CostMaterialData)aryCostMaterialData.get(0);
				 
				//--------------------------------- サンプルNO --------------------------
				//コンポーネント生成
				TextboxBase sampleNoTB = new TextboxBase();
				sampleNoTB.setEditable(false);
				sampleNoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
				sampleNoTB.setPk1(Integer.toString(trialData.getIntShisakuSeq()));
				//セルエディタ&レンダラ生成
				TextFieldCellEditor sampleNoTCE = new TextFieldCellEditor(sampleNoTB);
				TextFieldCellRenderer sampleNoTCR = new TextFieldCellRenderer(sampleNoTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(0, sampleNoTCE);
				MiddleCellRenderer.add(0, sampleNoTCR);
				//データ設定
				table.setValueAt( trialData.getStrSampleNo(), 0, j);

				//--------------------------------- 印刷FG -------------------------------
				//コンポーネント生成
				CheckboxBase insatuFg = new CheckboxBase();
				insatuFg.setHorizontalAlignment(CheckboxBase.CENTER);
				//データ設定
				if(costMaterialData.getIntinsatu() == 1){
					//insatuFg.setSelected(true);
					//データ設定
					table.setValueAt( "true", 1, j);
					
				}
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0149, DataCtrl.getInstance().getParamData().getStrMode())){
					insatuFg.setEnabled(false);
					
				}
				insatuFg.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//エディター＆レンダラー生成
				DefaultCellEditor insatuFlgTCE = new DefaultCellEditor(insatuFg);
				CheckBoxCellRenderer insatuFlgTCR = new CheckBoxCellRenderer(insatuFg);
				//中間エディター＆レンダラーへ登録
				MiddleCellEditor.addEditorAt(1, insatuFlgTCE);
				MiddleCellRenderer.add(1, insatuFlgTCR);
				
				
				//------------------------------- 原価試算依頼Fg ------------------------
				//コンポーネント生成
				CheckboxBase genkaIraiFg = new CheckboxBase();
				genkaIraiFg.setHorizontalAlignment(CheckboxBase.CENTER);
				//データ設定
				if(trialData.getFlg_shisanIrai() == 1){
					//insatuFg.setSelected(true);
					//データ設定
					table.setValueAt( "true", 2, j);
					
					//既に依頼されてあるデータの場合
					if(trialData.getFlg_init() == 1){
						//編集不可
						genkaIraiFg.setEnabled(false);
					}
					
				}
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0151, DataCtrl.getInstance().getParamData().getStrMode())){
					genkaIraiFg.setEnabled(false);
					
				}
				genkaIraiFg.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//エディター＆レンダラー生成
				DefaultCellEditor genkaIraiFgTCE = new DefaultCellEditor(genkaIraiFg);
				CheckBoxCellRenderer genkaIraiFgTCR = new CheckBoxCellRenderer(genkaIraiFg);
				//中間エディター＆レンダラーへ登録
				MiddleCellEditor.addEditorAt(2, genkaIraiFgTCE);
				MiddleCellRenderer.add(2, genkaIraiFgTCR);
				
				
				//--------------------------------- 充填量水相(g) ------------------------
				//コンポーネント生成
				NumelicTextbox jutenSuisoTB = new NumelicTextbox();
				jutenSuisoTB.setEnabled(true);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0134, DataCtrl.getInstance().getParamData().getStrMode())){
					jutenSuisoTB.setEditable(false);
					
				}
				jutenSuisoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
				int intShisakuSeq = trialData.getIntShisakuSeq();
				//列キー項目取得
				boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);
				//編集可能の場合：既存処理
				if(chk){
					
				}
				//編集可能の場合：既存処理
				else{
					jutenSuisoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					jutenSuisoTB.setEditable(false);
				}
//add end   -------------------------------------------------------------------------------
				
				
				
				//--------------------------------- 充填量油相(g) ------------------------
				//コンポーネント生成
				NumelicTextbox jutenYusoTB = new NumelicTextbox();
				jutenYusoTB.setEnabled(true);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0135, DataCtrl.getInstance().getParamData().getStrMode())){
					jutenYusoTB.setEditable(false);
					
				}
				jutenYusoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
				intShisakuSeq = trialData.getIntShisakuSeq();
				//列キー項目取得
				chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);
				//編集可能の場合：既存処理
				if(chk){
					
				}
				//編集可能の場合：既存処理
				else{
					jutenYusoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					jutenYusoTB.setEditable(false);
				}
//add end   -------------------------------------------------------------------------------
				
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------
				
				//参照モードの場合（充填量水相）
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0134, DataCtrl.getInstance().getParamData().getStrMode())){
					jutenSuisoTB.setEditable(false);
				}
				//参照モードの場合（充填量油相）
				else if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0135, DataCtrl.getInstance().getParamData().getStrMode())){
					jutenYusoTB.setEditable(false);
				}
				//参照モードでない場合
				else{
					
					//列キー項目取得
					intShisakuSeq = trialData.getIntShisakuSeq();
					chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);
					
					//編集可能の場合：既存処理
					if(chk){
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
							
							//「充填量水相」は編集不可
							jutenSuisoTB.setEditable(false);
							//「充填量油相」は編集不可
							jutenYusoTB.setEditable(false);
							
							jutenSuisoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							jutenYusoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							
						}
						//工程パターンが「空白」でない場合
						else{
							
							//工程パターンのValue1取得
							String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);
							//工程パターンが「調味料１液タイプ」の場合-------------------------------------------------------------
							if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
								
								//「充填量水相」は編集不可（充填量計算）
								jutenSuisoTB.setEditable(false);
								//「充填量油相」は編集不可
								jutenYusoTB.setEditable(false);
								
								jutenSuisoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								jutenYusoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
							//工程パターンが「調味料２液タイプ」の場合-------------------------------------------------------------
							else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
								
								//「充填量水相」は編集不可（計算）
								jutenSuisoTB.setEditable(false);
								//「充填量油相」は編集不可（計算）
								jutenYusoTB.setEditable(false);
								
								jutenSuisoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
								jutenYusoTB.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
							}
							//工程パターンが「その他・加食タイプ」の場合-------------------------------------------------------------
							else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
								
								//「充填量水相」は編集可
								jutenSuisoTB.setEditable(true);
								//「充填量油相」は編集可
								jutenYusoTB.setEditable(true);
								
								jutenSuisoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
								jutenYusoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
							}
						}
					}
				}
				
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End   -------------------------
				
				//セルエディタ&レンダラ生成
				TextFieldCellEditor jutenSuisoTCE = new TextFieldCellEditor(jutenSuisoTB);
				TextFieldCellRenderer jutenSuisoTCR = new TextFieldCellRenderer(jutenSuisoTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(3, jutenSuisoTCE);
				MiddleCellRenderer.add(3, jutenSuisoTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrZyutenSui(), 3, j);
				
				//セルエディタ&レンダラ生成
				TextFieldCellEditor jutenYusoTCE = new TextFieldCellEditor(jutenYusoTB);
				TextFieldCellRenderer jutenYusoTCR = new TextFieldCellRenderer(jutenYusoTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(4, jutenYusoTCE);
				MiddleCellRenderer.add(4, jutenYusoTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrZyutenYu(), 4, j);
				
				
				//--------------------------------- 空白 ---------------------------------
				//コンポーネント生成
				NumelicTextbox kuhakuTB = new NumelicTextbox();
				kuhakuTB.setEnabled(false);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor kuhakuTCE = new TextFieldCellEditor(kuhakuTB);
				TextFieldCellRenderer kuhakuTCR = new TextFieldCellRenderer(kuhakuTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(5, kuhakuTCE);
				MiddleCellRenderer.add(5, kuhakuTCR);
				
				//--------------------------------- 合計量(１本：g)(容量×比重) ------------
				//コンポーネント生成
				NumelicTextbox goukeiryoTB = new NumelicTextbox();
				goukeiryoTB.setEnabled(false);
				goukeiryoTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor goukeiryoTCE = new TextFieldCellEditor(goukeiryoTB);
				TextFieldCellRenderer goukeiryoTCR = new TextFieldCellRenderer(goukeiryoTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(6, goukeiryoTCE);
				MiddleCellRenderer.add(6, goukeiryoTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrGokei(), 6, j);
				
				//--------------------------------- 原料費(kg) ---------------------------
				//コンポーネント生成
				NumelicTextbox genryohiKgTB = new NumelicTextbox();
				genryohiKgTB.setEnabled(false);
				genryohiKgTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor genryohiKgTCE = new TextFieldCellEditor(genryohiKgTB);
				TextFieldCellRenderer genryohiKgTCR = new TextFieldCellRenderer(genryohiKgTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(7, genryohiKgTCE);
				MiddleCellRenderer.add(7, genryohiKgTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrGenryohi(), 7, j);
				
				//--------------------------------- 原料費(1本当) ------------------------
				//コンポーネント生成
				NumelicTextbox genryohi1HonTB = new NumelicTextbox();
				genryohi1HonTB.setEnabled(false);
				genryohi1HonTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor genryohi1HonTCE = new TextFieldCellEditor(genryohi1HonTB);
				TextFieldCellRenderer genryohi1HonTCR = new TextFieldCellRenderer(genryohi1HonTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(8, genryohi1HonTCE);
				MiddleCellRenderer.add(8, genryohi1HonTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrGenryohiTan(), 8, j);
				
				//--------------------------------- 比重 ---------------------------------
				//コンポーネント生成
				NumelicTextbox hijuTB = new NumelicTextbox();
				hijuTB.setEnabled(false);
				hijuTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor hijuTCE = new TextFieldCellEditor(hijuTB);
				TextFieldCellRenderer hijuTCR = new TextFieldCellRenderer(hijuTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(9, hijuTCE);
				MiddleCellRenderer.add(9, hijuTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrHizyu(), 9, j);
				
				//--------------------------------- 容量 ---------------------------------
				//コンポーネント生成
				NumelicTextbox yoryoTB = new NumelicTextbox();
				yoryoTB.setEnabled(false);
				yoryoTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor yoryoTCE = new TextFieldCellEditor(yoryoTB);
				TextFieldCellRenderer yoryoTCR = new TextFieldCellRenderer(yoryoTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(10, yoryoTCE);
				MiddleCellRenderer.add(10, yoryoTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrYoryo(), 10, j);
				
				//--------------------------------- 入り数 -------------------------------
				//コンポーネント生成
				NumelicTextbox irisuTB = new NumelicTextbox();
				irisuTB.setEnabled(false);
				irisuTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor irisuTCE = new TextFieldCellEditor(irisuTB);
				TextFieldCellRenderer irisuTCR = new TextFieldCellRenderer(irisuTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(11, irisuTCE);
				MiddleCellRenderer.add(11, irisuTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrIrisu(), 11, j);
				
				//--------------------------------- 有効歩留(%) --------------------------
				//コンポーネント生成
				NumelicTextbox yukoBudomariTB = new NumelicTextbox();
				yukoBudomariTB.setEnabled(true);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0127, DataCtrl.getInstance().getParamData().getStrMode())){
					yukoBudomariTB.setEditable(false);
					
				}
				yukoBudomariTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor yukoBudomariTCE = new TextFieldCellEditor(yukoBudomariTB);
				TextFieldCellRenderer yukoBudomariTCR = new TextFieldCellRenderer(yukoBudomariTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(12, yukoBudomariTCE);
				MiddleCellRenderer.add(12, yukoBudomariTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrYukoBudomari(), 12, j);
				
				//--------------------------------- 空白 ---------------------------------
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(13, kuhakuTCE);
				MiddleCellRenderer.add(13, kuhakuTCR);
				
				//--------------------------------- レベル量(g内容量×入数) --------------
				//コンポーネント生成
				NumelicTextbox lebelRyo1TB = new NumelicTextbox();
				lebelRyo1TB.setEnabled(false);
				lebelRyo1TB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor lebelRyo1TCE = new TextFieldCellEditor(lebelRyo1TB);
				TextFieldCellRenderer lebelRyo1TCR = new TextFieldCellRenderer(lebelRyo1TB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(14, lebelRyo1TCE);
				MiddleCellRenderer.add(14, lebelRyo1TCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrLevel(), 14, j);
				
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.24
//				//--------------------------------- 比重加算量(gレベル量×比重) ------------
				//--------------------------------- 比重加算量(g平均充填量×比重) ------------
//mod end --------------------------------------------------------------------------------------
				//コンポーネント生成
				NumelicTextbox lebelRyo2TB = new NumelicTextbox();
				lebelRyo2TB.setEnabled(false);
				lebelRyo2TB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor lebelRyo2TCE = new TextFieldCellEditor(lebelRyo2TB);
				TextFieldCellRenderer lebelRyo2TCR = new TextFieldCellRenderer(lebelRyo2TB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(15, lebelRyo2TCE);
				MiddleCellRenderer.add(15, lebelRyo2TCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrHizyuBudomari(), 15, j);
				
				//--------------------------------- 平均充填量(g) ------------------------
				//コンポーネント生成
				NumelicTextbox heikinJutenryoTB = new NumelicTextbox();
				heikinJutenryoTB.setEnabled(true);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0128, DataCtrl.getInstance().getParamData().getStrMode())){
					heikinJutenryoTB.setEditable(false);
					
				}
				heikinJutenryoTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor heikinJutenryoTCE = new TextFieldCellEditor(heikinJutenryoTB);
				TextFieldCellRenderer heikinJutenryoTCR = new TextFieldCellRenderer(heikinJutenryoTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(16, heikinJutenryoTCE);
				MiddleCellRenderer.add(16, heikinJutenryoTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrZyutenAve(), 16, j);
				
				//--------------------------------- 原料費 (1c/s当たり) -------------------------------
				//コンポーネント生成
				NumelicTextbox genryohiTB = new NumelicTextbox();
				genryohiTB.setEnabled(false);
				genryohiTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor genryohiTCE = new TextFieldCellEditor(genryohiTB);
				TextFieldCellRenderer genryohiTCR = new TextFieldCellRenderer(genryohiTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(17, genryohiTCE);
				MiddleCellRenderer.add(17, genryohiTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrGenryohiCs(), 17, j);
				
				//--------------------------------- 材料費 -------------------------------
				//コンポーネント生成
				NumelicTextbox zairyohiTB = new NumelicTextbox();
				zairyohiTB.setEnabled(true);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0145, DataCtrl.getInstance().getParamData().getStrMode())){
					zairyohiTB.setEditable(false);
					
				}
				zairyohiTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor zairyohiTCE = new TextFieldCellEditor(zairyohiTB);
				TextFieldCellRenderer zairyohiTCR = new TextFieldCellRenderer(zairyohiTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(18, zairyohiTCE);
				MiddleCellRenderer.add(18, zairyohiTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrZairyohiCs(), 18, j);
				
				//--------------------------------- 固定費 ---------------------------------
				//コンポーネント生成
				NumelicTextbox keihiTB = new NumelicTextbox();
				keihiTB.setEnabled(true);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0129, DataCtrl.getInstance().getParamData().getStrMode())){
					keihiTB.setEditable(false);
					
				}
				keihiTB.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor keihiTCE = new TextFieldCellEditor(keihiTB);
				TextFieldCellRenderer keihiTCR = new TextFieldCellRenderer(keihiTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(19, keihiTCE);
				MiddleCellRenderer.add(19, keihiTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrKeihiCs(), 19, j);
				
				//--------------------------------- 原価計/cs ----------------------------
				//コンポーネント生成
				NumelicTextbox genkakeiCsTB = new NumelicTextbox();
				genkakeiCsTB.setEnabled(false);
				genkakeiCsTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor genkakeiCsTCE = new TextFieldCellEditor(genkakeiCsTB);
				TextFieldCellRenderer genkakeiCsTCR = new TextFieldCellRenderer(genkakeiCsTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(20, genkakeiCsTCE);
				MiddleCellRenderer.add(20, genkakeiCsTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrGenkakeiCs(), 20, j);
				
				//--------------------------------- 原価計/個 ----------------------------
				//コンポーネント生成
				NumelicTextbox genkakeiKoTB = new NumelicTextbox();
				genkakeiKoTB.setEnabled(false);
				genkakeiKoTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor genkakeiKoTCE = new TextFieldCellEditor(genkakeiKoTB);
				TextFieldCellRenderer genkakeiKoTCR = new TextFieldCellRenderer(genkakeiKoTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(21, genkakeiKoTCE);
				MiddleCellRenderer.add(21, genkakeiKoTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrGenkakeiTan(), 21, j);
				
				//--------------------------------- 売価 ---------------------------------
				//コンポーネント生成
				NumelicTextbox baikaTB = new NumelicTextbox();
				baikaTB.setEnabled(false);
				baikaTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor baikaTCE = new TextFieldCellEditor(baikaTB);
				TextFieldCellRenderer baikaTCR = new TextFieldCellRenderer(baikaTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(22, baikaTCE);
				MiddleCellRenderer.add(22, baikaTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrGenkakeiBai(), 22, j);
				
				//--------------------------------- 粗利(%) ------------------------------
				//コンポーネント生成
				HankakuTextbox arariTB = new HankakuTextbox();
				arariTB.setHorizontalAlignment(SwingConstants.RIGHT);
				arariTB.setEnabled(false);
				arariTB.setBackground(Color.WHITE);
				//セルエディタ&レンダラ生成
				TextFieldCellEditor arariTCE = new TextFieldCellEditor(arariTB);
				TextFieldCellRenderer arariTCR = new TextFieldCellRenderer(arariTB);
				//中間エディタ&レンダラへ登録
				MiddleCellEditor.addEditorAt(23, arariTCE);
				MiddleCellRenderer.add(23, arariTCR);
				//データ設定
				table.setValueAt( costMaterialData.getStrGenkakeiRi(), 23, j);
				
//2011/05/27 QP@10181_No.67 TT Nishigawa Change Start -------------------------
				
				//--------------------------------- 依頼キャンセル ------------------------
				//コンポーネント生成
				CheckboxBase cancelFg = new CheckboxBase();
				cancelFg.setHorizontalAlignment(CheckboxBase.CENTER);
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0151, DataCtrl.getInstance().getParamData().getStrMode())){
					//編集不可
					cancelFg.setEnabled(false);
				}
				else{
					//既に依頼されてあるデータの場合
					if(trialData.getFlg_init() == 1){
						//編集可
						cancelFg.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR2);
					}
					//依頼されていない場合
					else{
						//編集不可
						cancelFg.setEnabled(false);
					}
				}
				//エディター＆レンダラー生成
				DefaultCellEditor cancelFlgTCE = new DefaultCellEditor(cancelFg);
				CheckBoxCellRenderer cancelFlgTCR = new CheckBoxCellRenderer(cancelFg);
				//中間エディター＆レンダラーへ登録
				MiddleCellEditor.addEditorAt(24, cancelFlgTCE);
				MiddleCellRenderer.add(24, cancelFlgTCR);
				
//2011/04/27 QP@10181_No.67 TT Nishigawa Change End   -------------------------
				
				//------------------------------- テーブルカラムへ設定  ---------------------------
				column.setCellEditor(MiddleCellEditor);
				column.setCellRenderer(MiddleCellRenderer);
				
			}

		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ テーブル初期化処理処理が失敗しました");
			ex.setStrErrmsg("原価試算 テーブル初期化処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			//変数の削除
			trialData = null;
			costMaterialData = null;
			aryCostMaterialData = null;
			
		}		
		
	}
	
	/************************************************************************************
	 * 
	 *  試算確定サンプルNoコンボボックス設定
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private void setShisanKakuteiCmb() throws ExceptionBase {

		try {
			
			//試算確定データを取得
			ArrayList aryShisanKakutei = DataCtrl.getInstance().getShisanRirekiKanriData().getAryShisanKakuteiData();
			
			//コンボボックス初期化
			cmbShisanKakutei.removeAllItems();
			
			//コンボボックスにサンプルNoを設定
			for ( int i =0; i<aryShisanKakutei.size(); i++ ) {				
				ShisanData shisanData = (ShisanData)aryShisanKakutei.get(i);
				
				if ( shisanData.getStrSampleNo() != null ) {
					cmbShisanKakutei.addItem(shisanData.getStrSampleNo());
					
				} else {
					cmbShisanKakutei.addItem("");
					
				}
				
			}
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 試算確定サンプルNoコンボボックス設定処理が失敗しました");
			ex.setStrErrmsg("原価試算 試算確定サンプルNoコンボボックス設定処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 *  試算確定サンプルNoコンボボックス設定
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	public void updDispValues(int intSeq, int column) throws ExceptionBase {

		try {
			
			//試算データの取得
			CostMaterialData genkaData = (CostMaterialData)DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(intSeq).get(0);

			//3 : 充填量水相
			table.setValueAt(genkaData.getStrZyutenSui(), 3, column);

			//4 : 充填量油相
			table.setValueAt(genkaData.getStrZyutenYu(), 4, column);

			//6 : 合計量
			table.setValueAt(genkaData.getStrGokei(), 6, column);
									
			//7 : 原料費(kg)
			table.setValueAt(genkaData.getStrGenryohi(), 7, column);
			
			//8 : 原料費(１本当)
			table.setValueAt(genkaData.getStrGenryohiTan(), 8, column);

			//9 : 比重
			table.setValueAt(genkaData.getStrHizyu(), 9, column);

			//10 : 容量
			table.setValueAt(genkaData.getStrYoryo(), 10, column);

			//11 : 入数
			table.setValueAt(genkaData.getStrIrisu(), 11, column);

			//12 : 有効歩留
			table.setValueAt(genkaData.getStrYukoBudomari(), 12, column);
			
			//14 : レベル量
			table.setValueAt(genkaData.getStrLevel(), 14, column);
			
			//15 : 比重加算量
			table.setValueAt(genkaData.getStrHizyuBudomari(), 15, column);
			
			//16 : 平均充填量
			table.setValueAt(genkaData.getStrZyutenAve(), 16, column);
			
			//17 : 原料費/cs
			table.setValueAt(genkaData.getStrGenryohiCs(), 17, column);
			
			//18 : 材料費
			table.setValueAt(genkaData.getStrZairyohiCs(), 18, column);

			//19 : 固定費
			table.setValueAt(genkaData.getStrKeihiCs(), 19, column);
						
			//20 : 原価計/cs
			table.setValueAt(genkaData.getStrGenkakeiCs(), 20, column);
			
			//21 : 原価系/個
			table.setValueAt(genkaData.getStrGenkakeiTan(), 21, column);

			//22 : 売価
			table.setValueAt(genkaData.getStrGenkakeiBai(), 22, column);
			
			//23 : 粗利/個
			table.setValueAt(genkaData.getStrGenkakeiRi(), 23, column);			
			
		} catch (Exception e) {
			e.printStackTrace();
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 試算確定サンプルNoコンボボックス設定処理が失敗しました");
			ex.setStrErrmsg("原価試算 試算確定サンプルNoコンボボックス設定処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 *  イベント処理の取得
	 *   @return ActionListenerクラス
	 *   @author TT katayama
	 *   
	 ************************************************************************************/
	private ActionListener getActionEvent() {
		
		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				String eventNm = e.getActionCommand();
				
				try {
					//試算履歴参照ボタン押下時処理
					if ( eventNm.equals(CLICK_SHISAN_RIREKI) ) {					
						
						//JW850 : 試算履歴参照
						con_JW850();
						
					}
					
				} catch (ExceptionBase eb) {
					DataCtrl.getInstance().PrintMessage(eb);
					
				} catch (Exception ec) {
					//エラー設定
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
					//ex.setStrErrmsg("原価試算⑤ イベント処理が失敗しました");
					ex.setStrErrmsg("原価試算 イベント処理が失敗しました");
					//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
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
	 *  JW840 試算確定サンプルNo検索処理
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private void con_JW840() throws ExceptionBase {
		
		try {

			//------------------------------ 送信パラメータ格納  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//------------------------------ 送信XMLデータ作成  ------------------------
			xmlJW840 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root追加  ---------------------------------
			xmlJW840.AddXmlTag("","JW840");
			arySetTag.clear();

			//------------------------------ 機能ID追加（USEERINFO）  -------------------
			xmlJW840.AddXmlTag("JW840", "USERINFO");
			//　テーブルタグ追加
			xmlJW840.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW840.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------------ 機能ID追加（SA830）  -------------------
			xmlJW840.AddXmlTag("JW840", "SA830");
			//　テーブルタグ追加
			xmlJW840.AddXmlTag("SA830", "table");
			//　レコード追加
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});
			xmlJW840.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("JW840送信XML===============================================================");
//			xmlJW840.dispXml();
			xcon = new XmlConnection(xmlJW840);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJW840 = xcon.getXdocRes();

//			System.out.println("JW840受信XML===============================================================");
//			xmlJW840.dispXml();
//			System.out.println();

			//---------------------------- Resultデータ設定(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW840);
			
			// Resultデータ.処理結果がtrueの場合、ExceptionBaseをThrowする
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
				//検索が失敗した場合
//				throw new ExceptionBase();
				
			} else {
				//検索が成功した場合

				//------------------------------ データ設定(SA830) --------------------------------
				DataCtrl.getInstance().getShisanRirekiKanriData().setShisanKakuteiNoData(this.xmlJW840);
				
			}

			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 初期表示処理が失敗しました");
			ex.setStrErrmsg("原価試算 初期表示処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 *  JW850 試算履歴参照ボタン 押下時処理
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private void con_JW850() throws ExceptionBase {
		
		try {

			//------------------------------ 送信パラメータ格納  ------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//------------------------------ 送信XMLデータ作成  ------------------------
			xmlJW850 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root追加  ---------------------------------
			xmlJW850.AddXmlTag("","JW850");
			arySetTag.clear();

			//------------------------------ 機能ID追加（USEERINFO）  -------------------
			xmlJW850.AddXmlTag("JW850", "USERINFO");
			//　テーブルタグ追加
			xmlJW850.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW850.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------------ 機能ID追加（SA840）  -------------------
			xmlJW850.AddXmlTag("JW850", "SA840");
			//　テーブルタグ追加
			xmlJW850.AddXmlTag("SA840", "table");
			//　レコード追加
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});;		
			xmlJW850.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("\nJW850送信XML===============================================================");
//			xmlJW850.dispXml();
			xcon = new XmlConnection(xmlJW850);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJW850 = xcon.getXdocRes();
//			System.out.println("\nJW850受信XML===============================================================");
//			xmlJW850.dispXml();

			//---------------------------- Resultデータ設定(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW850);
			
			// Resultデータ.処理結果がtrueの場合、ExceptionBaseをThrowする
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {	
				throw new ExceptionBase();
			} else {
				//検索結果が存在した場合

				//------------------------------ データ設定(SA840) --------------------------------
				DataCtrl.getInstance().getShisanRirekiKanriData().setShisanRirekiData(this.xmlJW850);
			
				//試算履歴確認メッセージを表示
				DataCtrl.getInstance().getMessageCtrl().PrintMessageShisanRirekiSansyo();
				
			}
				
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 試算履歴参照ボタン押下時処理が失敗しました");
			ex.setStrErrmsg("原価試算 試算履歴参照ボタン押下時処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 *   試算履歴自動採番　XML通信処理（J010）
	 *    :  試算履歴自動採番処理XMLデータ通信（J010）を行う
	 *   @return 新規発行コード
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private String conJ010() throws ExceptionBase{
		
		String ret = "";
		
		try{
			
			//--------------------------- 送信パラメータ格納  ---------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
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
			arySetTag.add(new String[]{"kbn_shori", "shisan_rireki"});
			xmlJ010.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();
			
			//----------------------------------- XML送信  ----------------------------------
			//System.out.println("\nJ010送信XML===============================================================");
			//xmlJ010.dispXml();
			xcon = new XmlConnection(xmlJ010);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJ010 = xcon.getXdocRes();
			//System.out.println("\nJ010受信XML===============================================================");
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
							ret = recVal;
							
						}
						
					}
					
				}
				
			}else{
				ExceptionBase ex  = new ExceptionBase();
				throw ex;
				
			}
			
		}catch(ExceptionBase eb){
			throw eb;
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 試算履歴自動採番処理が失敗しました");
			ex.setStrErrmsg("原価試算 試算履歴自動採番処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
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
	 *  JW860 原価試算登録ボタン 押下時処理
	 *   @param strRirekiNo : 履歴順
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private void con_JW860(String strRirekiNo) throws ExceptionBase {
			
		try {
						
			//------------------------------ 送信パラメータ格納  ------------------------
			
			//ユーザID
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
			//画面.試算確定サンプルNo.コンボボックスより試作SEQとサンプルNoを取得する
			ShisanData shisanKakutei = DataCtrl.getInstance().getShisanRirekiKanriData().SearchShisanKakuteiData(this.cmbShisanKakutei.getSelectedIndex());
			String strShisakuSeq = Integer.toString(shisanKakutei.getIntShisakuSeq());
			
			//サンプルNO
			String strSampleNo = "";
			if ( shisanKakutei.getStrSampleNo() != null ) {
				strSampleNo = shisanKakutei.getStrSampleNo();
				
			}
						
			//------------------------------ 送信XMLデータ作成  ------------------------
			xmlJW860 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root追加  ---------------------------------
			xmlJW860.AddXmlTag("","JW860");
			arySetTag.clear();

			//------------------------------ 機能ID追加（USEERINFO）  -------------------
			xmlJW860.AddXmlTag("JW860", "USERINFO");
			//　テーブルタグ追加
			xmlJW860.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW860.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------------ 機能ID追加（SA820）  -------------------
			//試算確定履歴登録処理
			xmlJW860.AddXmlTag("JW860", "SA820");
			//　テーブルタグ追加
			xmlJW860.AddXmlTag("SA820", "table");
			//　レコード追加
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});
			arySetTag.add(new String[]{"seq_shisaku", strShisakuSeq});			//試作SEQ
			arySetTag.add(new String[]{"nm_sample", strSampleNo});				//サンプルNo
			arySetTag.add(new String[]{"sort_rireki", strRirekiNo});				//履歴順
			
			xmlJW860.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();
			
			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("\nJW860送信XML===============================================================");
//			xmlJW860.dispXml();
			xcon = new XmlConnection(xmlJW860);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJW860 = xcon.getXdocRes();
//			System.out.println("\nJW860受信XML===============================================================");
//			xmlJW860.dispXml();

			//---------------------------- Resultデータ設定(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW860);
			
			// Resultデータ.処理結果がtrueの場合、ExceptionBaseをThrowする
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {	
				throw new ExceptionBase();
				
			} else {
				//エラーではない場合
				
				//確認メッセージを表示する
				DataCtrl.getInstance().getMessageCtrl().PrintMessageString("正常に原価試算登録処理が完了しました。");
				
				//最終試算確定データを設定する
				DataCtrl.getInstance().getShisanRirekiKanriData().SetLastShisanData(Integer.parseInt(strShisakuSeq));
				
			}

		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 原価試算登録ボタン押下時処理が失敗しました");
			ex.setStrErrmsg("原価試算 原価試算登録ボタン押下時処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
				
	}

	/************************************************************************************
	 * 
	 *  JW830 原価試算表印刷ボタン 押下時処理
	 *   @param intKoteiVal : 選択されている工程のリテラル値1(不正ではない値)
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	private void con_JW830(int intKoteiVal) throws ExceptionBase {
		
		//試作SEQカウント
		int intShisakuSeqCnt = 0;
		//試作SEQ 格納用配列
		String[] aryShisakuSeq = null;
		//仕上がり合計重量 格納用配列
		String[] aryShiagariJuryo = null;
		//試作テーブルデータリスト
		ArrayList aryCostMaterialData = null;
		
		try {
			
			int i;
			
			//------------------------------ 送信パラメータ格納  ------------------------
			
			//ユーザID
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();
			
			//印刷Fgによる試作データ設定(最大3項目)
			
			//試作SEQカウント 初期化
			intShisakuSeqCnt = 0;
			//試作SEQ 格納用配列 生成
			aryShisakuSeq = new String[3];
			//仕上がり合計重量 格納用配列 生成
			aryShiagariJuryo = new String[3];
			//試作テーブルデータリスト 取得
			aryCostMaterialData = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);

			//配列の初期化
			aryShisakuSeq[0] = "";
			aryShisakuSeq[1] = "";
			aryShisakuSeq[2] = "";
			aryShiagariJuryo[0] = "";
			aryShiagariJuryo[1] = "";
			aryShiagariJuryo[2] = "";
			
			//試作データを設定する
			for ( i=0; i<aryCostMaterialData.size(); i++ ) {
				
				//原価原料テーブルデータ取得
				CostMaterialData costMaterialData = (CostMaterialData)aryCostMaterialData.get(i);
				
				//印刷Fgチェック
				if ( costMaterialData.getIntinsatu() == 1 ) {
					
					//試作SEQカウントを進める
					intShisakuSeqCnt++;
					if ( intShisakuSeqCnt > 3 ) {
						//設定できる試作SEQは３つまで
						break;
					}
					
					
					//試作SEQを設定
					aryShisakuSeq[intShisakuSeqCnt-1] = Integer.toString(costMaterialData.getIntShisakuSeq());
					
					//試作テーブルより、仕上がり合計重量を設定
//					TrialData trialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(costMaterialData.getIntShisakuSeq()).get(0);
//					if ( trialData.getDciShiagari() != null ) {
//						aryShiagariJuryo[intShisakuSeqCnt-1] = trialData.getDciShiagari().toString();
//						
//					}
				}
				
			}
			
			
			
				//ソートSEQ配列
			    String[] setArySeq = new String[3];
			    setArySeq[0] = "";
			    setArySeq[1] = "";
			    setArySeq[2] = "";
			    
			    //試作列データ取得
				ArrayList aryTrialData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
				
				//格納カウント
				int m=0;
				
				//試作列数分ループ
				for(int l=1; l <= aryTrialData.size(); l++){
					
					//印刷試作列数分ループ
					for(int n=0; n < aryShisakuSeq.length; n++){
						
						//印刷試作SEQがNULLでない場合
						if(aryShisakuSeq[n] != null && aryShisakuSeq[n].length() > 0){
							
							//印刷試作SEQ取得
							int intSeq = Integer.parseInt(aryShisakuSeq[n]);
							
							//印刷試作列データ取得
							TrialData trialData1 = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intSeq).get(0);
							
							//印刷試作ソート順取得
							int intSort = trialData1.getIntHyojiNo();
							
							//列数カウントと印刷試作ソートが等しい場合
							if(l == intSort ){
								
								//ソートSEQ配列へ格納
								setArySeq[m] = Integer.toString(intSeq);
								
								if ( trialData1.getDciShiagari() != null ) {
									aryShiagariJuryo[m] = trialData1.getDciShiagari().toString();
									
								}
								
								//格納カウント+1
								m++;
							}
							
						}else{
							
							
						}
					}
				}
				
				//テスト表示
//				for(int o=0; o<setArySeq.length; o++){
//					System.out.println("試作SEQ"+setArySeq[o]);
//					System.out.println("仕上がり"+aryShiagariJuryo[o]);
//				}
				
			
			
			
			
			
			
			
			
			//------------------------------ 送信XMLデータ作成  ------------------------
			xmlJW830 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//------------------------------ Root追加  ---------------------------------
			xmlJW830.AddXmlTag("","JW830");
			arySetTag.clear();

			//------------------------------ 機能ID追加（USEERINFO）  -------------------
			xmlJW830.AddXmlTag("JW830", "USERINFO");
			//　テーブルタグ追加
			xmlJW830.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW830.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------------ 機能ID追加（SA800）  -------------------
			xmlJW830.AddXmlTag("JW830", "SA800");
			//　テーブルタグ追加
			xmlJW830.AddXmlTag("SA800", "table");
			//　レコード追加
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});
			//試作SEQ
			for ( i=0; i<3; i++ ) {
				
				System.out.println(setArySeq[i]);
				
				arySetTag.add(new String[]{"seq_shisaku" + (i+1), setArySeq[i]});
				arySetTag.add(new String[]{"juryo_shiagari" + (i+1), aryShiagariJuryo[i]});
				
			}
			//工程値
			arySetTag.add(new String[]{"kotei_value", Integer.toString(intKoteiVal)});
			
			xmlJW830.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------------ 機能ID追加（SA870）  -------------------
			xmlJW830.AddXmlTag("JW830", "SA870");
			//　テーブルタグ追加
			xmlJW830.AddXmlTag("SA870", "table");
			//　レコード追加
			arySetTag.add(new String[]{"cd_shain", DataCtrl.getInstance().getParamData().getStrSisaku_user()});
			arySetTag.add(new String[]{"nen", DataCtrl.getInstance().getParamData().getStrSisaku_nen()});
			arySetTag.add(new String[]{"no_oi", DataCtrl.getInstance().getParamData().getStrSisaku_oi()});
			//試作SEQ
			for ( i=0; i<3; i++ ) {
				arySetTag.add(new String[]{"seq_shisaku" + (i+1), setArySeq[i]});
				
			}
			
			xmlJW830.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("\nJW830送信XML===============================================================");
//			xmlJW830.dispXml();
			xcon = new XmlConnection(xmlJW830);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();
			
			//----------------------------------- XML受信  ----------------------------------
			xmlJW830 = xcon.getXdocRes();
//			System.out.println("\nJW830受信XML===============================================================");
//			xmlJW830.dispXml();

			//---------------------------- Resultデータ設定(RESULT)  -----------------------------
			DataCtrl.getInstance().getResultData().setResultData(xmlJW830);
			
			// Resultデータ.処理結果がtrueの場合、ExceptionBaseをThrowする
			if ( !DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {	
				throw new ExceptionBase();
			}

			//------------------------------ データ設定(SA800) --------------------------------
			//ダウンロードパスクラス
			DownloadPathData downloadPathData = new DownloadPathData();
			downloadPathData.setDownloadPathData(xmlJW830, "SA800");
			
			//URLコネクションクラス
			UrlConnection urlConnection = new UrlConnection();
			
			//ダウンロードパスを送り、ファイルダウンロード画面で開く
			urlConnection.urlFileDownLoad( downloadPathData.getStrDownloadPath());
			
			//------------------------------ データ設定(原価試算No) --------------------------------
			for ( i=0; i<3; i++ ) {
				
				//試作SEQが格納されている場合、原価試算Noを設定する
				if ( !aryShisakuSeq[i].toString().equals("") ) {
					
					//試作SEQを取得
					int intShisakuSeq =Integer.parseInt(aryShisakuSeq[i]); 
					
					//原価試算Noを設定する(1～3)
					this.setGenkaShisanNo( intShisakuSeq, i + 1);
					
				}			
				
			}

		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 原価試算表印刷ボタン押下時処理が失敗しました");
			ex.setStrErrmsg("原価試算 原価試算表印刷ボタン押下時処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			//変数の削除
			intShisakuSeqCnt = 0;
			aryShisakuSeq = null;
			aryCostMaterialData = null;
			
		}
		
	}

	/************************************************************************************
	 * 
	 *  試算確定サンプルNoコンボボックス　更新処理
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	public void updShisanSampleNo() throws ExceptionBase {
		
		try {
			
			//検索処理
			this.con_JW840();
			
			//コンボボックス初期化処理
			this.setShisanKakuteiCmb();
			
			//使用可に設定
			this.cmbShisanKakutei.setEnabled(true);
			this.btnShisanToroku.setEnabled(true);
			this.btnShisanRireki.setEnabled(true);
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 試算確定サンプルNoコンボボックス更新処理が失敗しました");
			ex.setStrErrmsg("原価試算 試算確定サンプルNoコンボボックス更新処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 * 試算確定サンプルNoコンボボックス選択処理
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	public void selectCmbShisanKakutei(int intSelectIndex) throws ExceptionBase {
		
		try {
			this.cmbShisanKakutei.setSelectedIndex(intSelectIndex);
		
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 試算確定サンプルNoコンボボックス選択処理が失敗しました");
			ex.setStrErrmsg("原価試算 試算確定サンプルNoコンボボックス選択処理が失敗しました");
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
	 *  原価試算画面クリア処理
	 *   : 試算確定サンプルNoコンボボックス、原価試算登録ボタン、
	 *   試算履歴参照ボタンを使用不可に設定する
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	public void clearGenkaShisan() throws ExceptionBase {

		try {

			//試算確定サンプルNoコンボボックスの値をクリア
			this.cmbShisanKakutei.removeAllItems();
			
			//試算確定サンプルNoコンボボックスを使用不可に設定
			this.cmbShisanKakutei.setEnabled(false);

			//原価試算登録ボタンを使用不可に設定
			this.btnShisanToroku.setEnabled(false);
			
			//試算履歴参照ボタンを使用不可に設定
			this.btnShisanRireki.setEnabled(false);
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 試算確定サンプルNoコンボボックス更新処理が失敗しました");
			ex.setStrErrmsg("原価試算 試算確定サンプルNoコンボボックス更新処理が失敗しました");
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
	 *  原価試算登録処理
	 *   @author TT katayama
	 *   @throws ExceptionBase 
	 *   
	 ************************************************************************************/
	public void shisanToroku() throws ExceptionBase {
		
		try {
		
			//J010 自動採番処理 [試算履歴順 取得]
			String strRirekiNo = conJ010();
			
			//JW860 : 原価試算登録
			con_JW860(strRirekiNo);
		
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 原価試算登録処理が失敗しました");
			ex.setStrErrmsg("原価試算 原価試算登録処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
	/************************************************************************************
	 * 
	 * 原価試算表　印刷FGチェック
	 * @param strName : 帳票名 
	 * @param intChkCount : 指定件数
	 * @return true : 印刷可, false : 印刷不可
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	public boolean chkGenkaInsatuFlg(String strName, int intChkCount) throws ExceptionBase {

		boolean ret = true;
		int intCount = 0;
		
		try {
			
			//試作列データを取得し、試作テーブルデータを取得する
			ArrayList aryShisakuRetu = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
			for ( int i=0; i<aryShisakuRetu.size(); i++ ) {
				CostMaterialData costMaterialData = (CostMaterialData)aryShisakuRetu.get(i);
				
				//印刷Flgがチェックされているならば、カウントを進める。
				if ( costMaterialData.getIntinsatu() == 1 ) {
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
			//ex.setStrErrmsg("原価試算⑤ 印刷FGチェック処理が失敗しました");
			ex.setStrErrmsg("原価試算 印刷FGチェック処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;
			
		} finally {
			
		}
		return ret;
	
	}
	
	//2011/05/02 QP@10181_No.73 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 * 
	 * 原価試算表　原価試算依頼FGチェック
	 * @return true : 依頼済み, false : 依頼していない
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	public boolean chkGenkaIraiFlg() throws ExceptionBase {
		
		boolean ret = true;
		int intCount = 0;
		
		//取得対象データ : 試作テーブル
		TrialData trialData = null;
		
		try {
			//試作データを取得
			Object[] aryTrialData = (DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0).toArray());
			
			//テーブルカラムモデル取得
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)table.getColumnModel();
			
			//列数分ループ
			for(int j = 0;j < columnModel.getColumnCount();j++){
				//試作列データ取得（表示順）
				int no=0;
				for(int i=0; i<columnModel.getColumnCount(); i++){
					TrialData chkHyji = (TrialData)aryTrialData[i];
					if((chkHyji.getIntHyojiNo()-1) == j){
						no=i;
					}
				}
				trialData = (TrialData)aryTrialData[no];
				
				//試作列データを取得
				ArrayList aryShisakuRetu = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);
				
				//試作テーブルデータを取得
				for ( int i=0; i<aryShisakuRetu.size(); i++ ) {
					//原価試算依頼Flgがチェックされているならば、カウントを進める。
					if ( trialData.getFlg_shisanIrai() == 1 ) {
						intCount++;
					}
				}
				
				//原価試算依頼FGが選択されていない場合
				if ( intCount == 0 ) {
					ret = false;
				}
			}
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原価試算 原価試算依頼FGチェック処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getStackTrace().toString());
			throw ex;
		} finally {
		}
		return ret;
	}
	//2011/05/02 QP@10181_No.73 TT T.Satoh Add End ---------------------------
	
	/************************************************************************************
	 * 
	 * 原価試算表　出力処理
	 * @param intKoteiVal : 選択されている工程のリテラル値1(不正ではない値)
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	public void outputGenkaShisanHyo(int intKoteiVal) throws ExceptionBase {

		try {

			//原価試算表出力
			con_JW830(intKoteiVal);
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 原価試算表出力処理が失敗しました");
			ex.setStrErrmsg("原価試算 原価試算表出力処理が失敗しました");
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
	 * 原価試算No設定処理
	 * @param intShisakuSeq : 試作SEQ 
	 * @param intGenkaShisan : 原価試算No
	 * @throws ExceptionBase 
	 * 
	 ************************************************************************************/
	private void setGenkaShisanNo(int intShisakuSeq, int intGenkaShisan) throws ExceptionBase {
		
		try {
			
			//試作テーブルデータを取得
			TrialData trialData = (TrialData)(DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intShisakuSeq).get(0));
			
			trialData.setIntGenkaShisan(intGenkaShisan);
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 原価試算No設定処理が失敗しました");
			ex.setStrErrmsg("原価試算 原価試算No設定処理が失敗しました");
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
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//ex.setStrErrmsg("原価試算⑤ 原価試算更新処理が失敗しました");
			ex.setStrErrmsg("原価試算 原価試算更新処理が失敗しました");
			//2011/04/13 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(ec.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
		
	/************************************************************************************
	 * 
	 * 試算確定サンプルNoコンボボックス　ゲッタ
	 * @return 試算確定サンプルNoコンボボックス
	 * 
	 ************************************************************************************/
	public ComboBase getCmbShisanKakutei() {
		return this.cmbShisanKakutei;
		
	}

	/************************************************************************************
	 * 
	 * 原価試算表印刷ボタン　ゲッタ
	 * @return 原価試算表印刷ボタン
	 * 
	 ************************************************************************************/
	public ButtonBase getBtnShisanHyo() {
		return this.btnShisanHyo;
		
	}
	
	/************************************************************************************
	 * 
	 * 原価試算登録ボタン　ゲッタ
	 * @return 原価試算登録ボタン
	 * 
	 ************************************************************************************/
	public ButtonBase getBtnShisanToroku() {
		return this.btnShisanToroku;
		
	}
	
	public TableBase getTable() {
		return table;
	}

	public void setTable(TableBase table) {
		this.table = table;
	}
	
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 * 
	 *   scrollゲッター
	 *   @author TT satoh
	 *   @return scroll :　原価試算のスクロールバー
	 *   
	 ************************************************************************************/
	public JScrollPane getScroll() {
		return scroll;
	}
	
	/************************************************************************************
	 * 
	 *   scrollセッター
	 *   @author TT satoh
	 *   @param _scroll : 原価試算のスクロールバー
	 *   
	 ************************************************************************************/
	public void setScroll(JScrollPane _scroll) {
		scroll = _scroll;
	}
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------
	
}

