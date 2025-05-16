package jp.co.blueflag.shisaquick.jws.table;

//------------------------------ 基本機能　Listインポート  -----------------------------------
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
//----------------------------------- AWT　インポート  --------------------------------------
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
//---------------------------------- Swing　インポート  -------------------------------------
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;

//--------------------------------- シサクイック　インポート  -----------------------------------
import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.CostMaterialData;
import jp.co.blueflag.shisaquick.jws.base.KaishaData;
import jp.co.blueflag.shisaquick.jws.base.LiteralData;
import jp.co.blueflag.shisaquick.jws.base.ManufacturingData;
import jp.co.blueflag.shisaquick.jws.base.MaterialData;
import jp.co.blueflag.shisaquick.jws.base.MixedData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeData;
import jp.co.blueflag.shisaquick.jws.base.PrototypeListData;
import jp.co.blueflag.shisaquick.jws.base.TrialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.celleditor.ComboBoxCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.MiddleCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextAreaCellEditor;
import jp.co.blueflag.shisaquick.jws.celleditor.TextFieldCellEditor;
import jp.co.blueflag.shisaquick.jws.cellrenderer.CheckBoxCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.ComboBoxCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextAreaCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.label.ItemLabel;
import jp.co.blueflag.shisaquick.jws.manager.MaterialMstData;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.panel.ManufacturingPanel;
import jp.co.blueflag.shisaquick.jws.textbox.HankakuTextbox;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;
import jp.co.blueflag.shisaquick.jws.data.*;
import jp.co.blueflag.shisaquick.jws.disp.AnalysinSubDisp;
import jp.co.blueflag.shisaquick.jws.disp.ColorSubDisp;
import jp.co.blueflag.shisaquick.jws.disp.MaterialSubDisp;
import jp.co.blueflag.shisaquick.jws.disp.MemoSubDisp;

/************************************************************************************
 *
 *   配合表(試作表①)テーブルクラス
 *   @author TT nishigawa
 *
 ************************************************************************************/
public class Trial1Table extends PanelBase{

	//------------------------------- データ  ------------------------------------------
	ArrayList aryTrialData;
	PrototypeData PrototypeData;
	ArrayList aryHaigou;
	ArrayList aryShisakuList;

	//------------------------------ エラー管理  ----------------------------------------
	private ExceptionBase ex;

	//-------------------------------- 画面  ------------------------------------------
	private ColorSubDisp colorSubDisp = null;
	private AnalysinSubDisp analysinSubDisp = null;
	private MaterialSubDisp materialSubDisp_Copy = null;
	//【QP@20505】No5 2012/10/12 TT H.SHIMA ADD Start
	private MemoSubDisp memoSubDisp = null;
	//【QP@20505】No5 2012/10/12 TT H.SHIMA ADD End

	//----------------------------- ボタングループ ---------------------------------------
	//ButtonGroup KoteiCheck = new ButtonGroup();
	//ButtonGroup ShisakuCheck = new ButtonGroup();

	//-------------------------------- ヘッダー  ----------------------------------------
	private PanelBase HaigoHeader;
	private TableBase ListHeader;

	//-------------------------------- 明細  ------------------------------------------
	private TableBase HaigoMeisai;
	private TableBase ListMeisai;

	//----------------------------- スクロールバー  ---------------------------------------
	private ScrollBase scrollTop;
	private ScrollBase scrollLeft;
	private ScrollBase scrollMain;

	//---------------------- 試作ヘッダーテーブルセルエディター  -------------------------------
	ArrayList aryHeaderShisakuCellEditor;

	//---------------------- 試作ヘッダーテーブルセルレンダラー  -------------------------------
	ArrayList aryHeaderShisakuCellRenderer;

	//----------------------- 配合明細テーブルセルエディター  --------------------------------
	MiddleCellEditor HaigoMeisaiCellEditor0;		//1列目
	MiddleCellEditor HaigoMeisaiCellEditor1;		//2列目
	MiddleCellEditor HaigoMeisaiCellEditor2;		//3列目
	MiddleCellEditor HaigoMeisaiCellEditor3;		//4列目
	MiddleCellEditor HaigoMeisaiCellEditor4;		//5列目
	MiddleCellEditor HaigoMeisaiCellEditor5;		//6列目
	MiddleCellEditor HaigoMeisaiCellEditor6;		//7列目
	MiddleCellEditor HaigoMeisaiCellEditor7;		//8列目

	//---------------------- 配合明細テーブルセルレンダラー  ---------------------------------
	MiddleCellRenderer HaigoMeisaiCellRenderer0;	//1列目
	MiddleCellRenderer HaigoMeisaiCellRenderer1;	//2列目
	MiddleCellRenderer HaigoMeisaiCellRenderer2;	//3列目
	MiddleCellRenderer HaigoMeisaiCellRenderer3;	//4列目
	MiddleCellRenderer HaigoMeisaiCellRenderer4;	//5列目
	MiddleCellRenderer HaigoMeisaiCellRenderer5;	//6列目
	MiddleCellRenderer HaigoMeisaiCellRenderer6;	//7列目
	MiddleCellRenderer HaigoMeisaiCellRenderer7;	//8列目

	//---------------------- 試作明細テーブルセルエディター  ----------------------------------
	ArrayList aryListShisakuCellEditor;

	//---------------------- 試作明細テーブルセルレンダラー  ----------------------------------
	ArrayList aryListShisakuCellRenderer;

	//------------------------------- ライン  --------------------------------------------
	private LineBorder line = new LineBorder(Color.BLACK, 1);

	//-------------------------------- 色  ---------------------------------------------
	private Color clBlue = JwsConstManager.SHISAKU_ITEM_COLOR;
	private Color Yellow = JwsConstManager.SHISAKU_ITEM_COLOR2;

	//------------------------------ 選択配列  ------------------------------------------
	ArrayList aryGenryoCheck = new ArrayList();
	int[] gCheck = new int[2]; //原料選択（[0]=行、[1]=列）
	int[] kCheck = new int[2]; //工程選択（[0]=行、[1]=列）

	//------------------------------ 処理フラグ  -----------------------------------------
	boolean blnGenryoCopy = false;

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
	//編集列
	private int editCol = -1;
	//セル編集フラグ
	private boolean editFg = false;
	public boolean isEditFg() {
		return editFg;
	}
	public void setEditFg(boolean editFg) {
		this.editFg = editFg;
	}
//add end   -------------------------------------------------------------------------------

//2011/05/11 QP@10181_No.12 TT Nishigawa Add Start -----------------------
	private CheckboxBase allCheck;
//2011/05/11 QP@10181_No.12 TT Nishigawa Add End -------------------------


	/************************************************************************************
	 *
	 *   コンストラクタ
	 *    : 配合表(試作表①)テーブルを生成する
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public Trial1Table(){
		super();
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		ex = new ExceptionBase();

		try{

			//試作ヘッダーテーブルセルエディター
			aryHeaderShisakuCellEditor = new ArrayList();

			//試作ヘッダーテーブルセルレンダラー
			aryHeaderShisakuCellRenderer = new ArrayList();

			//試作明細テーブルセルエディター
			aryListShisakuCellEditor = new ArrayList();

			//試作明細テーブルセルレンダラー
			aryListShisakuCellRenderer = new ArrayList();

			//色指定画面生成
			colorSubDisp = new ColorSubDisp("色指定画面");

			//分析データ確認画面
			analysinSubDisp = new AnalysinSubDisp("試作分析データ確認画面");

			//【QP@20505】No5 2012/10/12 TT H.SHIMA ADD Start
			//メモ入力画面
			memoSubDisp = new MemoSubDisp("メモ入力画面");
			//【QP@20505】No5 2012/10/12 TT H.SHIMA ADD End


			//原料一覧画面（原料コピー時）
			ArrayList aList = DataCtrl.getInstance().getUserMstData().getAryAuthData();

			//画面インスタンス生成
			for (int i = 0; i < aList.size(); i++) {

				//権限取得
				String[] items = (String[]) aList.get(i);

				//原料一覧画面の使用権限があるかチェックする
				if (items[0].toString().equals("150")) {

					//画面生成
					materialSubDisp_Copy = new MaterialSubDisp("原料一覧画面");
					break;
				}
			}

			//データ取得
			try{

				//試作品データ取得
				PrototypeData = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();

				//試作列データ取得（全件）
				aryTrialData = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);

				//配合データ取得（全件）
				aryHaigou = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

				//試作リストデータ取得（全件）
				aryShisakuList = DataCtrl.getInstance().getTrialTblData().getAryShisakuList();

			}catch(Exception e){

				e.printStackTrace();

			}

			//配合ヘッダー生成
			HaigoHeader = (PanelBase)DispHaigoHeader();
			this.add(HaigoHeader);

			//試作列ヘッダー生成
			ListHeader = (TableBase)DispListHeader();
			scrollTop = new ScrollBase(ListHeader) {
				private static final long serialVersionUID = 1L;

				//ヘッダーを消去
				public void setColumnHeaderView(Component view) {}
			};

			//配合明細生成
			HaigoMeisai = (TableBase)DispHaigoMeisai();
			scrollLeft = new ScrollBase(HaigoMeisai) {
				private static final long serialVersionUID = 1L;

				//ヘッダーを消去
				public void setColumnHeaderView(Component view) {}
			};

			//試作列明細生成
			ListMeisai = (TableBase)DispListMeisai();
			scrollMain = new ScrollBase(ListMeisai) {
				private static final long serialVersionUID = 1L;

				//ヘッダーを消去
				public void setColumnHeaderView(Component view) {}
			};

			//スクロールバー設定（試作ヘッダーテーブル）
			scrollTop.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollTop.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			scrollTop.setBounds(596, 0, 378, 100);
			scrollTop.setBorder(new LineBorder(Color.BLACK, 1));
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			scrollTop.getHorizontalScrollBar().setMaximum(ListHeader.getColumnCount() * 200);
			scrollTop.getVerticalScrollBar().setMaximum(1000);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//スクロールバー設定（配合明細テーブル）
			scrollLeft.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollLeft.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			scrollLeft.setBounds(0, 99, 597, 409);
			scrollLeft.setBorder(new LineBorder(Color.BLACK, 1));
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			scrollLeft.getHorizontalScrollBar().setMaximum(1000);
			scrollLeft.getVerticalScrollBar().setMaximum(HaigoMeisai.getRowCount() * 100);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//スクロールバー設定（試作明細テーブル）
			scrollMain.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollMain.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollMain.setBounds(596, 99, 395, 426);
			scrollMain.setBorder(new LineBorder(Color.BLACK, 1));
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add Start -------------------------
			scrollMain.getHorizontalScrollBar().setMaximum(ListMeisai.getColumnCount() * 200);
			scrollMain.getVerticalScrollBar().setMaximum(ListMeisai.getRowCount() * 100);
			//2011/04/21 QP@10181_No.41 TT T.Satoh Add End ---------------------------

			//スクロールバーの動きを同期させる
			final JScrollBar barTop = this.scrollTop.getHorizontalScrollBar();
			final JScrollBar barLeft = this.scrollLeft.getVerticalScrollBar();
			final JScrollBar barMain_yoko = this.scrollMain.getHorizontalScrollBar();
			final JScrollBar barMain_tate = this.scrollMain.getVerticalScrollBar();

			barMain_yoko.addAdjustmentListener(new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					barTop.setValue(e.getValue());
				}
			});
			barTop.addAdjustmentListener(new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					barMain_yoko.setValue(e.getValue());
				}
			});
			barMain_tate.addAdjustmentListener(new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					barLeft.setValue(e.getValue());
				}
			});
			barLeft.addAdjustmentListener(new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent e) {
					barMain_tate.setValue(e.getValue());
				}
			});

			//配合明細、試作リストテーブルをマウスホイールでも同期させる
			HaigoMeisai.addMouseWheelListener(new MouseWheelListener (){
				public void mouseWheelMoved(MouseWheelEvent e) {

					//スクロールバー情報取得
					JScrollBar JScrollBar = scrollMain.getVerticalScrollBar();
					int IncScroll = 0;
					int value = JScrollBar.getValue();

					//上方向
					if(0<e.getWheelRotation()){
						IncScroll = JScrollBar.getUnitIncrement(+1);
						JScrollBar.setValue(value + IncScroll);

					//下方向
					}else{
						IncScroll = JScrollBar.getUnitIncrement(-1);
						JScrollBar.setValue(value - IncScroll);
					}
				}
			});

			//フォーカス設定
			this.ListHeaderFocusControl();
			this.HaigoMeisaiFocusControl();
			this.ListMeisaiFocusControl();

			//パネルに追加
			this.add(scrollTop);
			this.add(scrollLeft);
			this.add(scrollMain);

			//原料費計算
			DispGenryohi();

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/************************************************************************************
	 *
	 *   配合ヘッダー生成
	 *    : 配合ヘッダー生成の初期処理を行う
	 *   @author TT nishigawa
	 *   @return JComponent : 配合ヘッダーコンポーネント
	 *
	 ************************************************************************************/
	private JComponent DispHaigoHeader(){
		try{

			//パネル初期設定
			HaigoHeader = new PanelBase();
			HaigoHeader.setLayout(null);
			HaigoHeader.setBackground(Color.LIGHT_GRAY);
			HaigoHeader.setBorder(new LineBorder(Color.BLACK, 1));
			HaigoHeader.setBounds(0, 0, 597, 100);

			//コンポーネント配置
			//工程ラベル
			ItemLabel hlKotei = new ItemLabel();
			hlKotei.setBorder(line);
			hlKotei.setHorizontalAlignment(SwingConstants.CENTER);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlKotei.setText("工程");
			hlKotei.setText("工程");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			hlKotei.setBounds(0, 0, 30, 35);
			HaigoHeader.add(hlKotei);

			//原料CDラベル
			ItemLabel hlGenryoCd = new ItemLabel();
			hlGenryoCd.setBorder(line);
			hlGenryoCd.setHorizontalAlignment(SwingConstants.CENTER);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlGenryoCd.setText("原料CD");
			hlGenryoCd.setText("原料CD");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			hlGenryoCd.setBounds(29, 0, 101, 35);
			HaigoHeader.add(hlGenryoCd);

			//原料名ラベル
			ItemLabel hlGenryoNm = new ItemLabel();
			hlGenryoNm.setBorder(line);
			hlGenryoNm.setHorizontalAlignment(SwingConstants.CENTER);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlGenryoNm.setText("原料名");
			hlGenryoNm.setText("原料名");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			hlGenryoNm.setBounds(129, 0, 285, 35);
			HaigoHeader.add(hlGenryoNm);

			//単価ラベル
			ItemLabel hlTanka = new ItemLabel();
			hlTanka.setBorder(line);
			hlTanka.setHorizontalAlignment(SwingConstants.CENTER);
			hlTanka.setText("<html>単価<br>  (" + JwsConstManager.JWS_MARK_0005 + ")");
			hlTanka.setBounds(413, 0, 82, 35);
			HaigoHeader.add(hlTanka);

			//歩留ラベル
			ItemLabel hlBudomari = new ItemLabel();
			hlBudomari.setBorder(line);
			hlBudomari.setHorizontalAlignment(SwingConstants.CENTER);
			hlBudomari.setText("<html>歩留<br>(" + JwsConstManager.JWS_MARK_0005 + ")");
			hlBudomari.setBounds(494, 0, 51, 35);
			HaigoHeader.add(hlBudomari);

			//油含有率ラベル
			ItemLabel hlAbura = new ItemLabel();
			hlAbura.setBorder(line);
			hlAbura.setHorizontalAlignment(SwingConstants.CENTER);
			hlAbura.setText("油含有率");
			hlAbura.setBounds(544, 0, 53, 35);
			HaigoHeader.add(hlAbura);

			//日付ラベル
			ItemLabel hlHiduke = new ItemLabel();
			hlHiduke.setBackground(Color.lightGray);
			hlHiduke.setHorizontalAlignment(SwingConstants.RIGHT);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlHiduke.setText("日付");
			hlHiduke.setText("日付");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			hlHiduke.setBounds(503, 35, 91, 13);
			HaigoHeader.add(hlHiduke);

			//ｻﾝﾌﾟﾙNOラベル
			ItemLabel hlSample = new ItemLabel();
			hlSample.setBackground(Color.lightGray);
			hlSample.setHorizontalAlignment(SwingConstants.RIGHT);
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change Start -------------------------
			//hlSample.setText("ｻﾝﾌﾟﾙNO");
			hlSample.setText("ｻﾝﾌﾟﾙNO");
			//2011/05/13 QP@10181_No.71 TT T.Satoh Change End ---------------------------
			hlSample.setBounds(503, 49, 91, 13);
			HaigoHeader.add(hlSample);

			//メモラベル
			ItemLabel hlMemo = new ItemLabel();
			hlMemo.setBackground(Color.lightGray);
			hlMemo.setHorizontalAlignment(SwingConstants.RIGHT);
			hlMemo.setText("メモ");
			hlMemo.setBounds(503, 65, 91, 13);
			HaigoHeader.add(hlMemo);

			//印刷FGラベル
			ItemLabel hlInsatu = new ItemLabel();
			hlInsatu.setBackground(Color.lightGray);
			hlInsatu.setHorizontalAlignment(SwingConstants.RIGHT);

//2011/05/13 QP@10181_No.12 TT Nishigawa Change Start -------------------------
			//hlInsatu.setText("印刷FG");
			hlInsatu.setText("（全コピー時の列指定）印刷FG");

			//ALLチェックボックス設定
			allCheck = new CheckboxBase();
			allCheck.setBackground(Color.lightGray);
			allCheck.setBounds(578, 86, 17, 12);
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0021, DataCtrl.getInstance().getParamData().getStrMode())){
				allCheck.setEnabled(false);
			}
			else{
				allCheck.addActionListener(new selectAllCheck());
			}
			this.add(allCheck);
//2011/05/13 QP@10181_No.12 TT Nishigawa Change end   -------------------------

			hlInsatu.setBounds(376, 86, 200, 12);
			HaigoHeader.add(hlInsatu);

		}catch(Exception e){

		}finally{

		}

		return HaigoHeader;
	}

//2011/05/13 QP@10181_No.12 TT Nishigawa Change Start -------------------------
	/************************************************************************************
	 *
	 *   ALLチェックボックス選択イベントクラス
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class selectAllCheck implements ActionListener{

		public void actionPerformed(ActionEvent e){
			try{
				//--------------------- 試作SEQ&注意事項No取得 --------------------------
				CheckboxBase cb = (CheckboxBase)e.getSource();

				//ALLチェックボックスがチェックされている場合
				if(cb.isSelected()){
					//試作列ループ
					for(int i=0; i<ListHeader.getColumnCount(); i++){

						//キー項目取得
						MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
						DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
						CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
						int intSeq  = Integer.parseInt(chkSeq.getPk1());

		    			//データ設定
			    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPrintFg(
			    				intSeq,
			    				1,
			    				DataCtrl.getInstance().getUserMstData().getDciUserid());

			    		//表示値設定
			    		ListHeader.setValueAt("true", 5, i);

					}
				}
				//ALLチェックボックスがチェックされていない場合
				else{
					//試作列ループ
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
				}
			}
			catch(Exception ex){
				ex.printStackTrace();

			}
			finally{

			}
		}
	}
//2011/05/13 QP@10181_No.12 TT Nishigawa Change end -------------------------


	/************************************************************************************
	 *
	 *   試作列ヘッダー生成
	 *    : 試作列ヘッダー生成の初期処理を行う
	 *   @author TT nishigawa
	 *   @return JComponent :試作列ヘッダーコンポーネント
	 *
	 ************************************************************************************/
	private JComponent DispListHeader(){
		try{
			//テーブル初期設定
			int ListHeaderCol = aryTrialData.size();
			int ListHeaderRow = 6;
			ListHeader = new TableBase(ListHeaderRow,ListHeaderCol){

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
				//セル選択時
				public void columnSelectionChanged(ListSelectionEvent e){
					super.columnSelectionChanged(e);
					try{
						//再計算処理
						if(!e.getValueIsAdjusting()){
							AutoCopyKeisan();
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{

					}
				}
//add end   -------------------------------------------------------------------------------


				//--------------------　試作列データ更新　----------------------------
				public void editingStopped( ChangeEvent e ){
					try{
						//セル編集終了処理
						super.editingStopped( e );

						//編集行列番号取得
						int row = getSelectedRow();
						int column = getSelectedColumn();
						if( row>=0 && column>=0 ){

							//キー項目取得
							MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, column);
							DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
							CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
							int intSeq  = Integer.parseInt(chkSeq.getPk1());

							//試作列選択
							if(row == 0){
								//列ループ
								for(int i=0; i<ListHeader.getColumnCount(); i++){
									//選択列は処理しない
									if(i == column){

									}
									//選択列でない場合
									else{
										//選択チェックボックスを初期化
										MiddleCellEditor mceChk = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
										DefaultCellEditor dceChk = (DefaultCellEditor)mceChk.getTableCellEditor(0);
										CheckboxBase chkChk = (CheckboxBase)dceChk.getComponent();
										chkChk.setSelected(false);
									}
								}
							}

					    	//注意事項No
					    	if(row == 1){

					    		//モード編集
								if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0017, DataCtrl.getInstance().getParamData().getStrMode())){

									//表示値取得
									String insert = getValueAt( row, column ).toString();

									//データ設定
						    		DataCtrl.getInstance().getTrialTblData().UpdChuiJikouNo(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
								}
					    	}
					    	//試作日付
					    	if(row == 2){

					    		//モード編集
					    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0018, DataCtrl.getInstance().getParamData().getStrMode())){

					    			//表示値取得
					    			String insert = getValueAt( row, column ).toString();

					    			//データ設定
						    		DataCtrl.getInstance().getTrialTblData().UpdShisaukRetuDate(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
					    		}
					    	}
					    	//サンプルNo
					    	if(row == 3){

					    		//モード編集
					    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0019, DataCtrl.getInstance().getParamData().getStrMode())){

					    			//表示値取得
					    			String insert = getValueAt( row, column ).toString();

					    			//データ設定
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSampleNo(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.2
						    		//サンプルNo同一チェック
									String chk = DataCtrl.getInstance().getTrialTblData().checkDistSampleNo(intSeq);
									//同一サンプルNoがない場合
									if(chk==null){

									}
									//同一サンプルNoがある場合
									else{
										DataCtrl.getInstance().getMessageCtrl().PrintMessageString(JwsConstManager.JWS_ERROR_0048 + chk);
									}
//mod end --------------------------------------------------------------------------------

					    		}
					    	}
					    	//メモ
					    	if(row == 4){

					    		//モード編集
					    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0020, DataCtrl.getInstance().getParamData().getStrMode())){

					    			//表示値取得
					    			String insert = getValueAt( row, column ).toString();

					    			//データ設定
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuMemo(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid()
						    			);
					    		}
					    	}
					    	//印刷Flg
					    	if(row == 5){

					    		//モード編集
					    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0021, DataCtrl.getInstance().getParamData().getStrMode())){

					    			//表示値取得
					    			String insert = (getValueAt( row, column ).toString()=="true")?"1":"0";

					    			//データ設定
						    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuPrintFg(
						    				intSeq,
						    				DataCtrl.getInstance().getTrialTblData().checkNullInt(insert),
						    				DataCtrl.getInstance().getUserMstData().getDciUserid());
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

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
			ListHeader.addMouseListener(new java.awt.event.MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					//再計算処理
					try{
						AutoCopyKeisan();
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			});
//add end ---------------------------------------------------------------------------------

			ListHeader.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			ListHeader.setRowHeight(13);
			ListHeader.setAutoResizeMode(ListHeader.AUTO_RESIZE_OFF);
			ListHeader.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			ListHeader.setCellSelectionEnabled(true);

			//テーブル選択
			ListHeader.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e){
					if(e.getValueIsAdjusting()){

						//試作ヘッダーの選択情報をクリア
						TableCellEditor lmEditor = ListMeisai.getCellEditor();
						if(lmEditor != null){
							ListMeisai.getCellEditor().stopCellEditing();
						}
						ListMeisai.clearSelection();

						//配合明細の選択情報をクリア
						TableCellEditor hmEditor = HaigoMeisai.getCellEditor();
						if(hmEditor != null){
							HaigoMeisai.getCellEditor().stopCellEditing();
						}
						HaigoMeisai.clearSelection();
					}
				}
			});

			//テーブル生成
			for(int i=0; i<ListHeaderCol; i++){
				//--------------------　試作列データ取得　----------------------------
				TrialData TrialData = ((TrialData)aryTrialData.get(i)); //試作列データ

				//------------------------- 試作列選択 ----------------------------

				//------------------------- 注意事項No ---------------------------
				//データ取得
				String tyuiNum = checkNull(TrialData.getStrTyuiNo());
				ListHeader.setRowHeight(1,21);
				//値設定
				ListHeader.setValueAt(tyuiNum, 1, i);

				//------------------------- 日付 ---------------------------------
				//データ取得
				String Hiduke = TrialData.getStrShisakuHi();
				//値設定
				ListHeader.setValueAt(Hiduke, 2, i);

				//------------------------- ｻﾝﾌﾟﾙNo ------------------------------
				//データ取得
				String Sample = TrialData.getStrSampleNo();
				//値設定
				ListHeader.setValueAt(Sample, 3, i);

				//------------------------- メモ -----------------------------------
				//データ取得
				String memo = TrialData.getStrMemo();
				//値設定
				ListHeader.setValueAt( memo, 4, i );

				//------------------------- 印刷FG --------------------------------
				//データ取得
				int Insatu = TrialData.getIntInsatuFlg();
				//値設定
				ListHeader.setValueAt((Insatu == 1) ? "true" : "false", 5, i);

				//------------------ Jtableへ中間エディター＆レンダラーを登録  ----------------------
				addRetuShisakuColER(i,Integer.toString(TrialData.getIntShisakuSeq()));
			}
			setHeaderShisakuER();

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}

		return ListHeader;
	}

	/************************************************************************************
	 *
	 *   原料コード入力検索
	 *    : 入力された原料コードにて原料データを検索
	 *   @author TT nishigawa
	 *   @param kaishaCd : 会社コード
	 *   @param bushoCd  : 部署コード
	 *   @param kaishaCd : 原料コード
	 *   @return MaterialData : 検索原料データ
	 *
	 ************************************************************************************/
	public MaterialData conJW110(int kaishaCd,int bushoCd,String GenryoCd) throws ExceptionBase{
		MaterialMstData mmd = new MaterialMstData();
		MaterialData ret = null;
		try{
			//新規原料チェック
			if(GenryoCd.indexOf("N") != -1){
				bushoCd = 0;
			}

			XmlData xmlJW110 = new XmlData();

			//----------------------------- 送信パラメータ格納  -------------------------------
			String strUser = DataCtrl.getInstance().getParamData().getDciUser().toString();

			//----------------------------- 送信XMLデータ作成  ------------------------------
			xmlJW110 = new XmlData();
			ArrayList arySetTag = new ArrayList();

			//--------------------------------- Root追加  ---------------------------------
			xmlJW110.AddXmlTag("","JW110");
			arySetTag.clear();

			//--------------------------- 機能ID追加（USEERINFO）  --------------------------
			xmlJW110.AddXmlTag("JW110", "USERINFO");
			//　テーブルタグ追加
			xmlJW110.AddXmlTag("USERINFO", "table");
			//　レコード追加
			arySetTag.add(new String[]{"kbn_shori", "3"});
			arySetTag.add(new String[]{"id_user",strUser});
			xmlJW110.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//------------------------- 機能ID追加（原料コード検索）  --------------------------
			xmlJW110.AddXmlTag("JW110", "SA580");
			//　テーブルタグ追加
			xmlJW110.AddXmlTag("SA580", "table");
			//　レコード追加
			arySetTag.add(new String[]{"cd_kaisha", checkNull(kaishaCd)});
			arySetTag.add(new String[]{"cd_busho" , checkNull(bushoCd)});
			arySetTag.add(new String[]{"cd_genryo", checkNull(GenryoCd)});
			xmlJW110.AddXmlTag("table", "rec", arySetTag);
			//配列初期化
			arySetTag.clear();

			//----------------------------------- XML送信  ----------------------------------
//			System.out.println("JW110送信XML===============================================================");
//			xmlJW110.dispXml();
			XmlConnection xcon = new XmlConnection(xmlJW110);
			xcon.setStrAddress(DataCtrl.getInstance().getJnlpConnect().getStrAddressAjax());
			xcon.XmlSend();

			//----------------------------------- XML受信  ----------------------------------
			xmlJW110 = xcon.getXdocRes();
			System.out.println();
//			System.out.println("JW110受信XML===============================================================");
//			xmlJW110.dispXml();

			//テストXMLデータ
			//xmlJW110 = new XmlData(new File("src/main/JW110.xml"));

			//--------------------------------- 各種データ設定  -------------------------------
			//Resultデータ
			DataCtrl.getInstance().getResultData().setResultData(xmlJW110);
			if(DataCtrl.getInstance().getResultData().getStrReturnFlg().equals("true")){

				//原料データ設定
				mmd.setMaterialData(xmlJW110,"SA580");

				//原料データ取得
				MaterialData selMmd = (MaterialData)mmd.getAryMateData().get(0);

				//検索結果が存在する場合　且つ　廃止原料でない場合
				if(selMmd.getStrGenryocd() != null && selMmd.getIntHaisicd() == 0){

					//返却値に原料データ格納
					ret = selMmd;

				}

			}else{

				//通信エラー
				ExceptionBase ExceptionBase = new ExceptionBase();
				throw ExceptionBase;

			}

		}catch(ExceptionBase be){
			be.printStackTrace();
			throw be;

		}catch(Exception e){
			e.printStackTrace();

		}finally{


		}
		return ret;
	}

	/************************************************************************************
	 *
	 *   配合明細生成
	 *    : 配合明細生成の初期処理を行う
	 *   @author TT nishigawa
	 *   @return JComponent :配合明細コンポーネント
	 *
	 ************************************************************************************/
	private JComponent DispHaigoMeisai(){
		try{
			int kotei_num = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			int row = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei() + aryHaigou.size();
			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int keisan = kotei_num+8;
//			int keisan = kotei_num+9;
			int keisan = (kotei_num*2)+9;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			// MOD end 20120914 QP@20505 No.1

			//テーブル初期設定
			HaigoMeisai = new TableBase((row+keisan),8){

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
				//セル選択時
				public void columnSelectionChanged(ListSelectionEvent e){
					super.columnSelectionChanged(e);
					try{
						//再計算処理
						if(!e.getValueIsAdjusting()){
							AutoCopyKeisan();
						}
					}catch(Exception ex){
						ex.printStackTrace();
					}finally{

					}
				}
//add end   -------------------------------------------------------------------------------

				//セルの編集終了時
				public void editingStopped( ChangeEvent e ){

					//------------------------ 配合データ更新 ------------------------------
					try{

						super.editingStopped( e );

						//2011/05/12 QP@10181_No.73 TT T.Satoh Add Start -------------------------
						//会社コード保持用
						int hojiKaisyaCd = 0;
						//2011/05/12 QP@10181_No.73 TT T.Satoh Add End ---------------------------

						//編集行列番号取得
						int row = getSelectedRow();
						int column = getSelectedColumn();
						if( row>=0 && column>=0 ){

							//---------------- キー項目取得  -------------------------------
							int intKoteiCd  = -1;
							int intKoteiSeq = -1;
							int intGenryoFg;
							MiddleCellEditor mceKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
							DefaultCellEditor dceKey = (DefaultCellEditor)mceKey.getTableCellEditor(row);

							//原料行の場合
							if(dceKey.getComponent() instanceof CheckboxBase){

								//キー項目取得
								CheckboxBase chkKey = (CheckboxBase)dceKey.getComponent();
								intKoteiCd  = Integer.parseInt(chkKey.getPk1());
								intKoteiSeq = Integer.parseInt(chkKey.getPk2());
								intGenryoFg = 0;

							//工程行の場合
							}else{

								//コンポーネント取得
								MiddleCellEditor mceKeyKotei = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 0);
								DefaultCellEditor dceKeyKotei = (DefaultCellEditor)mceKeyKotei.getTableCellEditor(row);

								//コンポーネントがチェックボックスの場合
								if(dceKeyKotei.getComponent() instanceof CheckboxBase){

									//キー項目取得
									CheckboxBase chkKey = (CheckboxBase)dceKeyKotei.getComponent();
									intKoteiCd  = Integer.parseInt(chkKey.getPk1());
									intKoteiSeq = Integer.parseInt(chkKey.getPk2());
									intGenryoFg = 1;

								//計算行の場合
								}else{
									intGenryoFg = 2;
								}
							}

					    	//------------ 原料CD or 工程属性  ----------------------------
							if(column == 3){

								//原料CD
								if(intGenryoFg == 0){


//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
									//工程キー項目取得
									int intShisakuSeq = 0;
									boolean hanshu_chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

									//編集可能の場合：既存処理
									if(hanshu_chk){

									}
									//編集不可の場合：処理しない
									else{
										return;
									}
//add end   -------------------------------------------------------------------------------

									//モード編集
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0025, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//表示値取得
						    			String insert = getValueAt( row, column ).toString();

										//入力桁数取得
										int keta = DataCtrl.getInstance().getTrialTblData().getKaishaGenryo();

						    			//前0埋め処理(原料コードが入力されている場合 且つ 原料コード入力時)
						    			if(insert != null &&insert.length() > 0 && !blnGenryoCopy){

						    				//新規原料でない場合
						    				if(insert.matches("^[0-9.]+$")){

						    					//入力文字数取得
						    					int m = insert.length();

						    					//0埋めループ
						    					for(int l=m; l < keta; l++){
						    						insert = "0"+insert;
								    			}
						    				}
						    			}
						    			setValueAt(insert,row,column);

						    			//自工場原料取得用
						    			MaterialData md3 = new MaterialData();

						    			//原料検索処理
										ArrayList aryHaigo = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(intKoteiCd);
										MixedData selMixed = new MixedData();
										for(int i=0;i<aryHaigo.size();i++){
											MixedData MixedData = (MixedData)aryHaigo.get(i);
											if(MixedData.getIntKoteiSeq() == intKoteiSeq){
												selMixed = MixedData;
											}
										}

										//③画面指定会社、工場コード取得
					    				int protoKaisha = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntKaishacd();
					    				int protoBusho = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntKojoco();

						    			//原料コードコピーor入力の判断
						    			if(blnGenryoCopy){

						    				//原料コードコピー時

						    				//コピーされた原料が自工場の場合
						    				if(selMixed.getIntKaishaCd() == protoKaisha
						    						&& selMixed.getIntBushoCd() == protoBusho){

						    					//処理無し
						    					md3 = null;

						    				//コピーされた原料が新規原料の場合
						    				}else if(selMixed.getIntKaishaCd() == protoKaisha
						    						&& selMixed.getIntBushoCd() == 0){

						    					//処理無し
						    					md3 = null;

						    				//コピーされた原料が他会社の場合
						    				}else if(selMixed.getIntKaishaCd() != protoKaisha){

						    					//処理無し
						    					md3 = null;

						    				}else{


//add start--------------------------------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.8
						    					//自工場原料検索
							    				//md3 = conJW110(protoKaisha,protoBusho,insert);

						    					//研究所の場合は検索を行わない
							    				if(protoBusho == Integer.parseInt(JwsConstManager.JWS_CD_DAIHYO_KOJO)
							    						&& protoKaisha == Integer.parseInt(JwsConstManager.JWS_CD_DAIHYO_KAISHA) ){
							    					md3 = null;
							    				}
							    				else{
							    					md3 = conJW110(protoKaisha,protoBusho,insert);
							    				}
//add end---------------------------------------------------------------------------------------------------------------

						    				}

						    			}else{

						    				//原料コード入力時

						    				//基本情報(試作表③)指定担当会社を配合データに設定
											DataCtrl.getInstance().getTrialTblData().UpdHaigoKaishaCd(
													intKoteiCd,
													intKoteiSeq,
													protoKaisha,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//新規原料の場合
											if(!insert.matches("^[0-9.]+$")){
												protoBusho = 0;
											}

											//基本情報(試作表③)指定担当工場を配合データに設定
											DataCtrl.getInstance().getTrialTblData().UpdHaigoKojoCd(
													intKoteiCd,
													intKoteiSeq,
													protoBusho,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);
						    			}

										//コミット（原料コード）
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoCd(
												intKoteiCd,
												intKoteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//入力原料コード検索
										int kaishaCd = selMixed.getIntKaishaCd();
										int bushoCd = selMixed.getIntBushoCd();

										//コメント行判定
										boolean comFlg = DataCtrl.getInstance().getTrialTblData().commentChk(insert, keta);

										//原料コードが空でない場合 AND コメント行でない場合に検索
										if(insert != null && insert.length() > 0 && !comFlg){

											//検索
											MaterialData md = conJW110(kaishaCd,bushoCd,insert);

											//処理実行Fg
											boolean exe = true;

											//検索結果が見つかった場合
											if(md != null){

												//原料コピーの場合
												if(blnGenryoCopy){

													//自工場原料が存在しない場合
													if(md3 == null){

														//処理無し

													//自工場原料が存在する場合
													}else{

														//確認ダイアログ表示
														int option = JOptionPane.showConfirmDialog(
																this,
																"自工場に原料が存在します。自工場の原料を使用しますか？"
																, "確認メッセージ"
																,JOptionPane.YES_NO_OPTION
																,JOptionPane.PLAIN_MESSAGE
															);

														//「はい」押下
													    if (option == JOptionPane.YES_OPTION){

													    	//自工場原料設定
													    	md = md3;

													    	//配合データの会社コードに③指定会社コード設定
													    	DataCtrl.getInstance().getTrialTblData().UpdHaigoKaishaCd(
																	intKoteiCd,
																	intKoteiSeq,
																	protoKaisha,
																	DataCtrl.getInstance().getUserMstData().getDciUserid()
																);

													    	//配合データの部署コードに③指定部署コード設定
													    	DataCtrl.getInstance().getTrialTblData().UpdHaigoKojoCd(
																	intKoteiCd,
																	intKoteiSeq,
																	protoBusho,
																	DataCtrl.getInstance().getUserMstData().getDciUserid()
																);

													    //「いいえ」押下
													    }else if (option == JOptionPane.NO_OPTION){

													    	//何もしない

													    }

													}

													//コピーフラグを初期化
													blnGenryoCopy = false;

												}

												//処理実行
												if(exe){

													//コミット（原料名称）
													//原料名取得
													String GenryoNm = md.getStrGenryonm();

													//試作品データ取得
													PrototypeData selPrototype = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData();

													//試作品-指定会社、部署コード取得
													int hinKaishaCd = selPrototype.getIntKaishacd();
													int hinBushoCd  = selPrototype.getIntKojoco();

													//検索原料-会社コード、部署コード取得
													int haigoKaishaCd = selMixed.getIntKaishaCd();
													int haigoBushoCd = selMixed.getIntBushoCd();

													//2011/05/12 QP@10181_No.73 TT T.Satoh Add Start -------------------------
													//会社コード保持
													hojiKaisyaCd = haigoKaishaCd;
													//2011/05/12 QP@10181_No.73 TT T.Satoh Add End ---------------------------

													//会社・工場記号設定
													if(hinKaishaCd == haigoKaishaCd){
														if(hinBushoCd != haigoBushoCd){
															if(haigoBushoCd > 0){

																//☆＋原料名を設定
																GenryoNm = JwsConstManager.JWS_MARK_0002 + delMark(GenryoNm);

															}
														}
													}else{

															//★＋原料名を設定
															GenryoNm = JwsConstManager.JWS_MARK_0001 + delMark(GenryoNm);

													}

//add start--------------------------------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.8
													if(hinBushoCd == Integer.parseInt(JwsConstManager.JWS_CD_DAIHYO_KOJO)
															&& hinKaishaCd == Integer.parseInt(JwsConstManager.JWS_CD_DAIHYO_KAISHA) ){
														//配合データの部署コードに③指定部署コード設定
												    	DataCtrl.getInstance().getTrialTblData().UpdHaigoKojoCd(
																intKoteiCd,
																intKoteiSeq,
																md.getIntBushocd(),
																DataCtrl.getInstance().getUserMstData().getDciUserid()
															);
													}
//add end---------------------------------------------------------------------------------------------------------------


													//表示値挿入
													setValueAt( GenryoNm, row, 4 );

													//データ挿入
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
															intKoteiCd,
															intKoteiSeq,
															GenryoNm,
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

													//コミット（単価）
													setValueAt( md.getDciTanka(), row, 5 );
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoTanka(
															intKoteiCd,
															intKoteiSeq,
															md.getDciTanka(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

													//コミット（歩留）
													setValueAt( md.getDciBudomari(), row, 6 );
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoBudomari(
															intKoteiCd,
															intKoteiSeq,
															md.getDciBudomari(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMaBudomari(
															intKoteiCd,
															intKoteiSeq,
															md.getMa_dciBudomari()
														);
//add end   -------------------------------------------------------------------------------


													//コミット（油含有率）
													setValueAt( md.getDciGanyu(), row, 7 );
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoYuganyuryo(
															intKoteiCd,
															intKoteiSeq,
															md.getDciGanyu(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

													//コミット（酢酸）
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSakusan(
															intKoteiCd,
															intKoteiSeq,
															md.getDciSakusan(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

													//コミット（食塩）
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSyokuen(
															intKoteiCd,
															intKoteiSeq,
															md.getDciShokuen(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

													//コミット（総酸）
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSousan(
															intKoteiCd,
															intKoteiSeq,
															md.getDciSousan(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);
								// ADD start 20121002 QP@20505 No.24
													//コミット（ＭＳＧ）
													DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMsg(
															intKoteiCd,
															intKoteiSeq,
															md.getDciMsg(),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);
								// ADD end 20121002 QP@20505 No.24
												}

											//検索結果がない場合
											}else{

												String GenryoNm = "";

												//原料名が空でない場合
												if(selMixed.getStrGenryoNm() != null){

													//★＋原料名　設定
													if(delMark(selMixed.getStrGenryoNm()) == null){
														GenryoNm = JwsConstManager.JWS_MARK_0001;
													}
													else{
														GenryoNm = JwsConstManager.JWS_MARK_0001 + delMark(selMixed.getStrGenryoNm());
													}

												}else{

													//★　設定
													GenryoNm = JwsConstManager.JWS_MARK_0001;

												}

												//★　を表示
												setValueAt( GenryoNm, row, 4 );

												//データ挿入
												DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
														intKoteiCd,
														intKoteiSeq,
														GenryoNm,
														DataCtrl.getInstance().getUserMstData().getDciUserid()
													);

												//権限データ取得
												ArrayList aList = DataCtrl.getInstance().getUserMstData().getAryAuthData();

												//画面の使用権限確認Fg
												boolean kengen = false;

												//画面インスタンス生成
												for (int i = 0; i < aList.size(); i++) {

													//画面ID取得
													String[] items = (String[]) aList.get(i);

													//原料一覧画面の使用権限があるかチェックする
													if (items[0].toString().equals("150")) {

														kengen = true;

														//会社コードチェック
														KaishaData KaishaData = materialSubDisp_Copy.getMaterialPanel().getKaishaData();
														ArrayList aryKaishaCd = KaishaData.getArtKaishaCd();
														ArrayList aryKaishaNm = KaishaData.getAryKaishaNm();
														boolean chk = false;
														for(int k=0; k<aryKaishaCd.size(); k++){
															String cd = (String)aryKaishaCd.get(k);

															//原料一覧権限内に同会社コードが存在する場合のみ
															if(Integer.parseInt(cd) == kaishaCd){
																chk = true;

																//会社名取得
																String kaishaNm = (String)aryKaishaNm.get(k);

																//画面初期化
																materialSubDisp_Copy.getMaterialPanel().clearDisp(false);

																//検索項目設定
																materialSubDisp_Copy.getMaterialPanel().getCodeTextbox().setText(insert);
																materialSubDisp_Copy.getMaterialPanel().getKaishaComb().setSelectedItem(kaishaNm);

																//画面非表示
																materialSubDisp_Copy.setVisible(false);

																//検索処理
																try{
																	materialSubDisp_Copy.getMaterialPanel().clickSearchBtn("1", true);
																	materialSubDisp_Copy.setVisible(true);
																}catch(ExceptionBase eb){
																	//DataCtrl.getInstance().PrintMessage(eb);
																}
															}
														}
														//自権限で扱えない会社コードの場合
														if(!chk){
															DataCtrl.getInstance().getMessageCtrl().PrintMessageString("扱えない会社コードが設定されています。権限の再設定をして下さい。");
														}
													}
												}
												//原料一覧の閲覧権限がない場合
												if(!kengen){
													DataCtrl.getInstance().getMessageCtrl().PrintMessageString("原料一覧画面の閲覧権限がありません。");
												}
											}
										}

										//原料コードがコメント行の場合に初期化
										else if(insert != null && insert.length() > 0 && comFlg){

											//--------------------------- 配合データ初期化 -----------------------------

											//原料名
											setValueAt( null, row, 4 );
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//コミット（単価）
											setValueAt( null, row, 5 );
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoTanka(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//コミット（歩留）
											setValueAt( null, row, 6 );
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoBudomari(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//コミット（油含有率）
											setValueAt( null, row, 7 );
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoYuganyuryo(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//コミット（酢酸）
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSakusan(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//コミット（食塩）
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSyokuen(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);

											//コミット（総酸）
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoSousan(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);
// ADD start 20121002 QP@20505 No.24
											//コミット（ＭＳＧ）
											DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMsg(
													intKoteiCd,
													intKoteiSeq,
													null,
													DataCtrl.getInstance().getUserMstData().getDciUserid()
												);
// ADD end 20121002 QP@20505 No.24

											//--------------------------- 量データ初期化 -----------------------------
											for(int i=0; i<ListHeader.getColumnCount(); i++){

												//キーコンポーネント取得
												MiddleCellEditor mcHeader = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
												DefaultCellEditor dcHeader = (DefaultCellEditor)mcHeader.getTableCellEditor(0);
												CheckboxBase cbHeader = (CheckboxBase)dcHeader.getComponent();

												//試作SEQ取得
												int sisakuSeq = Integer.parseInt(cbHeader.getPk1());

												//量データ初期化
												ListMeisai.setValueAt(null, row, i);
												DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
														sisakuSeq,
														intKoteiCd,
														intKoteiSeq,
														null
													);

											}

										}

						    		}

						    		//工程合計計算
									for(int col=0; col<ListMeisai.getColumnCount(); col++){
										koteiSum(col);
									}

									//自動計算
									AutoKeisan();

						    		//原料費計算
									DispGenryohi();

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.30
									//新規原料orコメント行orNULL
									boolean sinki_chk = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoCdSinki(intKoteiCd, intKoteiSeq);

									//モード編集可否取得
									boolean modeChk = DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0025, DataCtrl.getInstance().getParamData().getStrMode());

									//原料名取得
									String genryoNm = (String)HaigoMeisai.getValueAt(row, column);

									//新規原料orコメント行orNULLの場合　且つ　モード編集可能な場合　：　編集可能
									if(sinki_chk && modeChk){
										int color = Integer.parseInt(DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoColor(intKoteiCd, intKoteiSeq));

										//コンポーネント取得（原料名）
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(true);
										((TextboxBase)dceGenryoNm.getComponent()).setEditable(true);
										TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer4.getTableCellRenderer(row);
										tfcrGenryoNm.setColor(new Color(color));

										//コンポーネント取得（単価）
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(true);
										((TextboxBase)dceTanka.getComponent()).setEditable(true);
										TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);
										tfcrTanka.setColor(new Color(color));

									}
									//2011/05/12 QP@10181_No.73 TT T.Satoh Add Start -------------------------
									//モード編集不可の場合
									else if (!modeChk) {
										//コンポーネント取得（原料名）:編集不可能
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(false);

										//コンポーネント取得（単価）:編集不可能
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(false);
									}
									//基本情報の会社コードがキューピーではない場合
									else if(DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntKaishacd() != Integer.parseInt(JwsConstManager.JWS_CD_KEWPIE)){
										//コンポーネント取得（原料名）:編集不可能
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(false);
										TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer4.getTableCellRenderer(row);
										tfcrGenryoNm.setColor(JwsConstManager.JWS_DISABLE_COLOR);

										//コンポーネント取得（単価）:編集可能
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(true);
										((TextboxBase)dceTanka.getComponent()).setEditable(true);
										TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);
										tfcrTanka.setColor(Color.WHITE);
									}

									//★原料の場合
									else if(genryoNm != null && genryoNm.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001)){
										//コンポーネント取得（原料名）:編集不可能
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(false);
										TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer4.getTableCellRenderer(row);
										tfcrGenryoNm.setColor(JwsConstManager.JWS_DISABLE_COLOR);

										//コンポーネント取得（単価）:編集可能
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(true);
										((TextboxBase)dceTanka.getComponent()).setEditable(true);
										TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);
										tfcrTanka.setColor(Color.WHITE);
									}

									//原料の会社コードがキューピーではない場合
									else if (hojiKaisyaCd != Integer.parseInt(JwsConstManager.JWS_CD_KEWPIE)) {
										//コンポーネント取得（原料名）:編集不可能
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(false);
										TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer4.getTableCellRenderer(row);
										tfcrGenryoNm.setColor(JwsConstManager.JWS_DISABLE_COLOR);

										//コンポーネント取得（単価）:編集可能
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(true);
										((TextboxBase)dceTanka.getComponent()).setEditable(true);
										TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);
										tfcrTanka.setColor(Color.WHITE);
									}
									//2011/05/12 QP@10181_No.73 TT T.Satoh Add End ---------------------------
									//既存原料の場合：編集不可
									else{
										//コンポーネント取得（原料名）
										DefaultCellEditor dceGenryoNm = (DefaultCellEditor)HaigoMeisaiCellEditor4.getTableCellEditor(row);
										((TextboxBase)dceGenryoNm.getComponent()).setEnabled(false);
										TextFieldCellRenderer tfcrGenryoNm =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer4.getTableCellRenderer(row);
										tfcrGenryoNm.setColor(JwsConstManager.JWS_DISABLE_COLOR);

										//コンポーネント取得（単価）
										DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);
										((TextboxBase)dceTanka.getComponent()).setEnabled(false);
										TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);
										tfcrTanka.setColor(JwsConstManager.JWS_DISABLE_COLOR);
									}
//add end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
									//コンポーネント取得（歩留）
									boolean chkMaBudomari = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoMaBudomari(intKoteiCd, intKoteiSeq);
									if(chkMaBudomari){
										DefaultCellEditor dceBudomari = (DefaultCellEditor)HaigoMeisaiCellEditor6.getTableCellEditor(row);
										((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.PLAIN, 12));
										((TextboxBase)dceBudomari.getComponent()).setForeground(Color.black);
										TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer6.getTableCellRenderer(row);
										tfcrBudomari.setFont(new Font("Default", Font.PLAIN, 12));
										tfcrBudomari.setForeground(Color.black);
									}
									else{
										DefaultCellEditor dceBudomari = (DefaultCellEditor)HaigoMeisaiCellEditor6.getTableCellEditor(row);
										((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.BOLD, 12));
										((TextboxBase)dceBudomari.getComponent()).setForeground(Color.red);
										TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer6.getTableCellRenderer(row);
										tfcrBudomari.setFont(new Font("Default", Font.BOLD, 12));
										tfcrBudomari.setForeground(Color.red);
									}
//add end   -------------------------------------------------------------------------------

								//工程属性
								}else if(intGenryoFg == 1){

									//モード編集
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0024, DataCtrl.getInstance().getParamData().getStrMode())){

						    			String insert = null;

						    			//コンポーネント取得
										MiddleCellEditor mcZokusei = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, column);
										DefaultCellEditor dceZokusei = (DefaultCellEditor)mcZokusei.getTableCellEditor(row);
										ComboBase cb = (ComboBase)dceZokusei.getComponent();
										int selectId = cb.getSelectedIndex();

										//挿入値取得
										if(selectId > 0){

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start

											//insert = DataCtrl.getInstance().getLiteralDataZokusei().selectLiteralCd(selectId-1);

											//工程パターン取得
											String ptKotei = PrototypeData.getStrPt_kotei();

											//工程パターンが「空白」の場合
											if(ptKotei == null || ptKotei.length() == 0){
												insert = "";
											}
											//工程パターンが「空白」でない場合
											else{
												//工程パターンのValue1取得
												String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

												//工程パターンが「調味料１液タイプ」の場合
												if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
													insert = DataCtrl.getInstance().getLiteralDataKotei_tyomi1().selectLiteralCd(selectId-1);
												}
												//工程パターンが「調味料２液タイプ」の場合
												else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
													insert = DataCtrl.getInstance().getLiteralDataKotei_tyomi2().selectLiteralCd(selectId-1);
												}
												//工程パターンが「その他・加食タイプ」の場合
												else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
													insert = DataCtrl.getInstance().getLiteralDataKotei_sonota().selectLiteralCd(selectId-1);
												}
											}

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

										}

										//データ挿入
										DataCtrl.getInstance().getTrialTblData().UpdHaigoZokusei(
												intKoteiCd,
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//工程チェック
										int intKoteiChk = DataCtrl.getInstance().getTrialTblData().CheckKotei();

										//工程が混在している場合、警告メッセージを表示
										if ( intKoteiChk == -1 ) {
											//不正時

											//警告メッセージ表示
											String strMessage = JwsConstManager.JWS_ERROR_0021;
											DataCtrl.getInstance().getMessageCtrl().PrintMessageString(strMessage);

										}

										//原料費計算
						    			DispGenryohi();

						    		}
								}

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

								//製品比重、充填量、水相充填量、油相充填量　計算処理
								ZidouKeisan2();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

							}

					    	//------------- 原料名  or 工程名  -----------------------------
							if(column == 4){

								//原料名
								if(intGenryoFg == 0){

									//モード編集
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0027, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//表示値取得
						    			String insert = getValueAt( row, column ).toString();

										//データ挿入
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoMei(
												intKoteiCd,
												intKoteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);
						    		}

								//工程名
								}else if(intGenryoFg == 1){
									//モード編集
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0026, DataCtrl.getInstance().getParamData().getStrMode())){

						    			String insert = getValueAt( row, column ).toString();
										DataCtrl.getInstance().getTrialTblData().UpdHaigoKouteimei(
												intKoteiCd,
												DataCtrl.getInstance().getTrialTblData().checkNullString(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);
						    		}
								}
							}

					    	//------------------ 単価  -----------------------------------
							if(column == 5){
								if(intGenryoFg == 0){
									//モード編集
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0028, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//テーブル設定値取得
						    			String insert = getValueAt( row, column ).toString();

						    			//小数桁数洗い替え
						    			insert = ShosuArai_haigo(insert);

						    			//表示値設定
						    			setValueAt( insert, row, column );

										//データ設定
						    			DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoTanka(
												intKoteiCd,
												intKoteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

						    			//原料費計算
						    			DispGenryohi();

						    		}
								}
							}

							//------------------ 歩留  -----------------------------------
							if(column == 6){
								if(intGenryoFg == 0){
									//モード編集
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0029, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//テーブル設定値取得
										String insert = getValueAt( row, column ).toString();

										//小数桁数洗い替え
						    			insert = ShosuArai_haigo(insert);

						    			//表示値設定
						    			setValueAt( insert, row, column );

										//データ設定
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoBudomari(
												intKoteiCd,
												intKoteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//原料費計算
						    			DispGenryohi();

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
						    			//歩留を比較
						    			boolean chk_3 = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoMaBudomari(intKoteiCd, intKoteiSeq);

										//歩留がマスタと同一の場合：既存処理
										if(chk_3){
											DefaultCellEditor dceBudomari = (DefaultCellEditor)HaigoMeisaiCellEditor6.getTableCellEditor(row);
											((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.PLAIN, 12));
											((TextboxBase)dceBudomari.getComponent()).setForeground(Color.black);
											TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer6.getTableCellRenderer(row);
											tfcrBudomari.setFont(new Font("Default", Font.PLAIN, 12));
											tfcrBudomari.setForeground(Color.black);
										}
										//歩留がマスタと相違の場合：コンポーネントの操作不可
										else{
											DefaultCellEditor dceBudomari = (DefaultCellEditor)HaigoMeisaiCellEditor6.getTableCellEditor(row);
											((TextboxBase)dceBudomari.getComponent()).setFont(new Font("Default", Font.BOLD, 12));
											((TextboxBase)dceBudomari.getComponent()).setForeground(Color.red);
											TextFieldCellRenderer tfcrBudomari =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer6.getTableCellRenderer(row);
											tfcrBudomari.setFont(new Font("Default", Font.BOLD, 12));
											tfcrBudomari.setForeground(Color.red);
										}
//add end   -------------------------------------------------------------------------------

						    		}
								}
							}

							//---------------- 油含有率  ----------------------------------
							if(column == 7){
								if(intGenryoFg == 0){
									//モード編集
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0030, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//テーブル設定値取得
						    			String insert = getValueAt( row, column ).toString();

						    			//小数桁数洗い替え
						    			insert = ShosuArai_haigo(insert);

						    			//表示値設定
						    			setValueAt( insert, row, column );

										//データ設定
										DataCtrl.getInstance().getTrialTblData().UpdHaigoGenryoYuganyuryo(
												intKoteiCd,
												intKoteiSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);
						    		}
								}
							}

							//自動計算
							AutoKeisan();

						}
					}catch(ExceptionBase be){

					}catch(Exception ex){
						ex.printStackTrace();

					}finally{
						//テスト表示
						//DataCtrl.getInstance().getTrialTblData().dispHaigo();
					}
				}
			};

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
			HaigoMeisai.addMouseListener(new java.awt.event.MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					//再計算処理
					try{
						AutoCopyKeisan();
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			});
//add end ---------------------------------------------------------------------------------

			HaigoMeisai.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			HaigoMeisai.setRowHeight(17);
			HaigoMeisai.setAutoResizeMode(HaigoMeisai.AUTO_RESIZE_OFF);
			//HaigoMeisai.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			HaigoMeisai.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			//HaigoMeisai.setCellSelectionEnabled(true);
			HaigoMeisai.addKeyListener(new CopyGenryo());


			//テーブル選択
			HaigoMeisai.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e){
					if(e.getValueIsAdjusting()){

						//試作ヘッダーの選択情報をクリア
						TableCellEditor hmEditor = ListHeader.getCellEditor();
						if(hmEditor != null){
							ListHeader.getCellEditor().stopCellEditing();
						}
						ListHeader.clearSelection();

						//リスト明細の選択情報をクリア
						TableCellEditor lmEditor = ListMeisai.getCellEditor();
						if(lmEditor != null){
							ListMeisai.getCellEditor().stopCellEditing();
						}
						ListMeisai.clearSelection();
					}
				}
			});

			//---------------------------------- 列設定 ---------------------------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)HaigoMeisai.getColumnModel();
			TableColumn column = null;
			for (int i = 0 ; i < columnModel.getColumnCount() ; i++){
				column = columnModel.getColumn(i);

				//工程選択枠サイズ指定
				if(i == 0){
					column.setMinWidth(15);
					column.setMaxWidth(15);
				}
				//工程順枠サイズ指定
				else if(i == 1){
					column.setMinWidth(15);
					column.setMaxWidth(15);
				}
				//原料選択枠サイズ指定
				else if(i == 2){
					column.setMinWidth(16);
					column.setMaxWidth(16);
				}
				//原料CD（工程属性）枠サイズ指定
				else if(i == 3){
					column.setMinWidth(84);
					column.setMaxWidth(84);
				}
				//原料名枠サイズ指定
				else if(i == 4){
					column.setMinWidth(285);
					column.setMaxWidth(285);
				}
				//単価枠サイズ指定
				else if(i == 5){
					column.setMinWidth(80);
					column.setMaxWidth(80);
				}
				//歩留枠サイズ指定
				else if(i == 6){
					column.setMinWidth(50);
					column.setMaxWidth(50);
				}
				//油含有率枠サイズ指定
				else if(i == 7){
					column.setMinWidth(51);
					column.setMaxWidth(51);
				}
	        }

			//------------------------------ 配合データ表示 ---------------------------------------
			//配合明細テーブル　中間エディター生成（列数分）
			HaigoMeisaiCellEditor0 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor1 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor2 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor3 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor4 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor5 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor6 = new MiddleCellEditor(HaigoMeisai);
			HaigoMeisaiCellEditor7 = new MiddleCellEditor(HaigoMeisai);

			//配合明細テーブル　中間レンダラー生成（列数分）
			HaigoMeisaiCellRenderer0 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer1 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer2 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer3 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer4 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer5 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer6 = new MiddleCellRenderer();
			HaigoMeisaiCellRenderer7 = new MiddleCellRenderer();

			//テーブル生成
			int Output = 0;
			int bkKoteiCd = 0;

			//配合データ設定
			for(int i=0;i<aryHaigou.size();i++){
				MixedData mixedData = (MixedData)aryHaigou.get(i);

				//主キーの取得
				String pk_KoteiCd  = Integer.toString(mixedData.getIntKoteiCd());
				String pk_KoteiSeq = Integer.toString(mixedData.getIntKoteiSeq());

				//工程情報表示
				if(mixedData.getIntKoteiNo() != bkKoteiCd){
					HaigoMeisai.setRowHeight(Output, 17);

					//---------------------- 工程選択  ---------------------------


					//----------------------- 工程順  ----------------------------
					//データ取得
					String KoteiNo = Integer.toString(mixedData.getIntKoteiNo());
					HaigoMeisai.setValueAt(KoteiNo, Output, 1);
					bkKoteiCd = mixedData.getIntKoteiNo();

					//---------------------- 工程属性 ----------------------------
					String KoteiZoku = checkNull(mixedData.getStrKouteiZokusei());

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
					//HaigoMeisai.setValueAt(DataCtrl.getInstance().getLiteralDataZokusei().selectLiteralNm(KoteiZoku), Output, 3);

					//工程パターン取得
					String ptKotei = PrototypeData.getStrPt_kotei();

					//工程パターンが「空白」の場合
					if(ptKotei == null || ptKotei.length() == 0){

					}
					//工程パターンが「空白」でない場合
					else{
						//工程パターンのValue1取得
						String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

						//工程パターンが「調味料１液タイプ」の場合
						if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
							HaigoMeisai.setValueAt(DataCtrl.getInstance().getLiteralDataKotei_tyomi1().selectLiteralNm(KoteiZoku), Output, 3);
						}
						//工程パターンが「調味料２液タイプ」の場合
						else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
							HaigoMeisai.setValueAt(DataCtrl.getInstance().getLiteralDataKotei_tyomi2().selectLiteralNm(KoteiZoku), Output, 3);
						}
						//工程パターンが「その他・加食タイプ」の場合
						else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
							HaigoMeisai.setValueAt(DataCtrl.getInstance().getLiteralDataKotei_sonota().selectLiteralNm(KoteiZoku), Output, 3);
						}
					}
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

					//------------------------ 工程名  ----------------------------
					String KoteiNm = mixedData.getStrKouteiNm();
					HaigoMeisai.setValueAt(KoteiNm, Output, 4);

					this.addHaigoMeisaiRowER(Output, 1, pk_KoteiCd, pk_KoteiSeq,-1);
					Output++;
				}
				//---------------------- 工程順（原料）  -----------------------
				String KoteiNo = Integer.toString(mixedData.getIntKoteiNo());
				HaigoMeisai.setValueAt(KoteiNo, Output, 1);

				//----------------------- 原料選択  --------------------------


				//------------------------ 原料CD  --------------------------
				String GenryoCd = mixedData.getStrGenryoCd();
				HaigoMeisai.setValueAt(GenryoCd, Output, 3);

				//------------------------ 原料名  ---------------------------
				String GenryoNm = mixedData.getStrGenryoNm();
				HaigoMeisai.setValueAt(GenryoNm, Output, 4);

				//------------------------- 単価  ----------------------------
				String Tanka;
				//値がNULLでない場合
				if(mixedData.getDciTanka() != null){
					Tanka = mixedData.getDciTanka().toString();
				//値がNULLの場合
				}else{
					Tanka = null;
				}
				HaigoMeisai.setValueAt(Tanka, Output, 5);

				//------------------------- 歩留  ----------------------------
				String Budomari;
				//値がNULLでない場合
				if(mixedData.getDciBudomari() != null){
					Budomari = mixedData.getDciBudomari().toString();
				//値がNULLの場合
				}else{
					Budomari = null;
				}
				HaigoMeisai.setValueAt(Budomari, Output, 6);

				//------------------------ 油含有率  -------------------------
				String Abura;
				//値がNULLでない場合
				if(mixedData.getDciGanyuritu() != null){
					Abura = mixedData.getDciGanyuritu().toString();
				//値がNULLの場合
				}else{
					Abura = null;
				}
				HaigoMeisai.setValueAt(Abura, Output, 7);

				this.addHaigoMeisaiRowER(Output, 0, pk_KoteiCd, pk_KoteiSeq,Integer.parseInt(mixedData.getStrIro()));
				Output++;
			}

			//---------------------- 計算データ表示 ---------------------------
			//中間エディター＆レンダラー生成
			MiddleCellEditor SHeaderCellEditor = new MiddleCellEditor(HaigoMeisai);
			MiddleCellRenderer SHeaderCellRenderer = new MiddleCellRenderer();

			//コンポーネント生成
			TextboxBase tbKeisan = new TextboxBase();
			tbKeisan.setEditable(false);
			tbKeisan.setBackground(Color.WHITE);

			//エディター＆レンダラー生成
			TextFieldCellEditor editKaraRetu = new TextFieldCellEditor(tbKeisan);
			TextFieldCellRenderer rendKaraRetu = new TextFieldCellRenderer(tbKeisan);

			//中間エディター＆レンダラーへ登録
			for(int i = Output; i < HaigoMeisai.getRowCount(); i++){
				//1列目
				HaigoMeisaiCellEditor0.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer0.add(i, rendKaraRetu);
				//2列目
				HaigoMeisaiCellEditor1.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer1.add(i, rendKaraRetu);
				//3列目
				HaigoMeisaiCellEditor2.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer2.add(i, rendKaraRetu);
				//4列目
				HaigoMeisaiCellEditor3.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer3.add(i, rendKaraRetu);
				//5列目
				HaigoMeisaiCellEditor4.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer4.add(i, rendKaraRetu);
				//6列目
				HaigoMeisaiCellEditor5.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer5.add(i, rendKaraRetu);
				//7列目
				HaigoMeisaiCellEditor6.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer6.add(i, rendKaraRetu);
				//8列目
				HaigoMeisaiCellEditor7.addEditorAt(i, editKaraRetu);
				HaigoMeisaiCellRenderer7.add(i, rendKaraRetu);
			}

			//各工程合計
			for(int i=0; i<kotei_num; i++){
				//x工程（ｇ）項目
				HaigoMeisai.setValueAt((i+1) + "工程（ｇ）", Output, 4);
				Output++;
			}

			//合計重量（ｇ）項目
			HaigoMeisai.setValueAt("合計重量（ｇ）", Output, 4);
			Output++;

			// ADD start 20120914 QP@20505 No.1
			//工程仕上重量（ｇ）項目
			for(int i=0;i<kotei_num;i++){
				HaigoMeisai.setValueAt((i+1) + "工程仕上重量（ｇ）", Output, 4);
				Output++;
			}
			// ADD end 20120914 QP@20505 No.1

			//合計仕上重量（ｇ）項目
			HaigoMeisai.setValueAt("合計仕上重量（ｇ）（"+JwsConstManager.JWS_MARK_0005 + "）", Output, 4);
			Output++;

			//原価（ｋｇ）項目
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//HaigoMeisai.setValueAt("原価（ｋｇ）", Output, 4);
			HaigoMeisai.setValueAt("原料費（ｋｇ）", Output, 4);
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			Output++;

			//2011/06/13 QP@10181_No.29 TT T.Satoh Add Start -------------------------
			HaigoMeisai.setValueAt("原料費（１個）", Output, 4);
			Output++;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Add End ---------------------------

			//総酸（％）項目
			HaigoMeisai.setValueAt("総酸（％）", Output, 4);
			Output++;

			//食塩（％）項目
			HaigoMeisai.setValueAt("食塩（％）", Output, 4);
			Output++;

//			// ADD start 20121002 QP@20505 No.24
//			//ＭＳＧ（％）項目
//			HaigoMeisai.setValueAt("ＭＳＧ（％）", Output, 4);
//			Output++;
//			// ADD end 20121002 QP@20505 No.24

			//水相中総酸項目
			HaigoMeisai.setValueAt("水相中酸度", Output, 4);
			Output++;

			//水相中食塩項目
			HaigoMeisai.setValueAt("水相中食塩", Output, 4);
			Output++;

			//水相中酢酸項目
			HaigoMeisai.setValueAt("水相中酢酸", Output, 4);
			//Output++;


			//------------------ Jtableへ中間エディター＆レンダラーを登録  ----------------------
			this.setHaigoMeisaiER();
		}catch(Exception e){
			e.printStackTrace();

		}finally{

		}

		return HaigoMeisai;
	}

	/************************************************************************************
	 *
	 *   試作列明細生成
	 *    : 試作列明細生成の初期処理を行う
	 *   @author TT nishigawa
	 *   @return JComponent :試作列明細コンポーネント
	 *
	 ************************************************************************************/
	private JComponent DispListMeisai(){
		try{
			//初期設定
			int kotei_num = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			int row = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei() + aryHaigou.size();

			// ADD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int keisan = kotei_num+8;
			//int keisan = kotei_num+9;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			int keisan = (kotei_num * 2) + 9;
			// ADD end 20120914 QP@20505 No.1

			int col = aryTrialData.size();

			//テーブル初期設定
			ListMeisai = new TableBase((row+keisan),col){


//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
				//セル選択時
				public void columnSelectionChanged(ListSelectionEvent e){
					super.columnSelectionChanged(e);
					try{
						//再計算処理
						if(!e.getValueIsAdjusting()){

							if(this.getSelectedColumn() > -1){
								AutoCopyKeisan();
								editCol = this.getSelectedColumn();
							}

							//選択箇所の調整
							int row = HaigoMeisai.getSelectedRow();
							if(row > -1){
								HaigoMeisai.clearSelection();
								HaigoMeisai.setRowSelectionInterval(row, row);
							}
						}
					}catch(Exception ex){
						ex.printStackTrace();

					}finally{

					}
				}
//add end   -------------------------------------------------------------------------------


				//セルの編集終了時
				public void editingStopped( ChangeEvent e ){
					//------------------------ 配合データ更新 ------------------------------
					try{
						super.editingStopped( e );
						//編集行列番号取得
						int row = getSelectedRow();
						int column = getSelectedColumn();
						if( row>=0 && column>=0 ){
							//---------------- キー項目取得  -------------------------------
							int intShisakuSeq = -1;
							int intKoteiCd    = -1;
							int intKoteiSeq   = -1;
							int intGenryoFg;

							//試作SEQ　取得
							MiddleCellEditor mceHeaderKey = (MiddleCellEditor)ListHeader.getCellEditor(0, column);
							DefaultCellEditor dceHeaderKey = (DefaultCellEditor)mceHeaderKey.getTableCellEditor(0);
							CheckboxBase chkHeaderKey = (CheckboxBase)dceHeaderKey.getComponent();
							intShisakuSeq  = Integer.parseInt(chkHeaderKey.getPk1());

							//工程CD & 工程SEQ　取得
							MiddleCellEditor mceHaigoKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
							DefaultCellEditor dceHaigoKey = (DefaultCellEditor)mceHaigoKey.getTableCellEditor(row);
							//工程行でない場合
							if(dceHaigoKey.getComponent() instanceof CheckboxBase){
								CheckboxBase chkHaigoKey = (CheckboxBase)dceHaigoKey.getComponent();
								intKoteiCd  = Integer.parseInt(chkHaigoKey.getPk1());
								intKoteiSeq = Integer.parseInt(chkHaigoKey.getPk2());

								//-------------------- 量  -----------------------------------
								//モード編集
					    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
										JwsConstManager.JWS_COMPONENT_0031, DataCtrl.getInstance().getParamData().getStrMode())){

					    			//表示値取得
					    			String insert = (String)getValueAt( row, column );

					    			//小数洗替
					    			if(insert != null && insert.length() > 0){

					    				//洗替処理
					    				insert = ShosuArai(insert);

						    			//テーブル挿入
						    			setValueAt(insert, row, column);
					    			}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
					    			//入力前の値を取得
					    			String beforeInsert = DataCtrl.getInstance().getTrialTblData().searchShisakuListRyo(
											intShisakuSeq,
											intKoteiCd,
											intKoteiSeq);

					    			//入力前と入力後の値を比較
					    			if(beforeInsert.equals(insert)){
					    				//等しい場合→変更無し
					    				//何もしない
					    			}
					    			else{
					    				//等しくない場合→変更有り
					    				//セル編集フラグをON（再計算対象）
						    			editFg = true;
					    			}
//add end   -------------------------------------------------------------------------------

					    			//データ挿入
									DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
											intShisakuSeq,
											intKoteiCd,
											intKoteiSeq,
											DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert)
										);

									//工程合計計算
									koteiSum(column);

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

					    			//製品比重、充填量、水相充填量、油相充填量　計算処理
									ZidouKeisan2();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

									//自動計算
									AutoKeisan();

									//原料費計算
					    			DispGenryohi();
					    		}
							}else{
								//------------------ 仕上重量  --------------------------------
								// ADD start 20120914 QP@20505 No.1
								int koutei_Cnt = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
								// ADD end 20120914 QP@20505 No.1
								//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
					    		//if(row == this.getRowCount()-7){
						    	if(row == this.getRowCount()-8){
						    	//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
					    			//モード編集
						    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
											JwsConstManager.JWS_COMPONENT_0124, DataCtrl.getInstance().getParamData().getStrMode())){

						    			//表示値取得
						    			String insert = (String)getValueAt( row, column );

						    			//小数洗替
						    			if(insert != null && insert.length() > 0){

						    				//洗替処理
						    				insert = ShosuArai4(insert);

							    			//テーブル挿入
							    			setValueAt(insert, row, column);
						    			}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
						    			//入力前の値を取得
						    			String beforeInsert =
						    				DataCtrl.getInstance().getTrialTblData().searchShisakuRetuSiagari(intShisakuSeq);

						    			//入力前と入力後の値を比較
						    			if(beforeInsert.equals(insert)){
						    				//等しい場合→変更無し
						    				//何もしない
						    			}
						    			else{
						    				//等しくない場合→変更有り
						    				//セル編集フラグをON（再計算対象）
							    			editFg = true;
						    			}
//add end   -------------------------------------------------------------------------------

						    			//データ挿入
										DataCtrl.getInstance().getTrialTblData().UpdShiagariRetuDate(
												intShisakuSeq,
												DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
												DataCtrl.getInstance().getUserMstData().getDciUserid()
											);

										//原料費計算
						    			DispGenryohi();

						    		}
					    		}
						    	// ADD start 20120914 QP@20505 No.1
						    	//工程仕上重量
						    	else if(row < this.getRowCount()-8 && row >= this.getRowCount()-8-koutei_Cnt){
						    		//表示値取得
					    			String insert = (String)getValueAt( row, column );
					    			int gokeiShiagari = this.getRowCount()-8;
					    			int kouteiNum = row - (gokeiShiagari-koutei_Cnt) + 1;
					    			int intKoteiCode = DataCtrl.getInstance().getTrialTblData().getSerchKoteiCode(intShisakuSeq ,kouteiNum);

				    				//洗替処理
				    				insert = ShosuArai4(insert);

					    			//テーブル挿入
					    			setValueAt(insert, row, column);

					    			//データ挿入
									DataCtrl.getInstance().getTrialTblData().UpdKouteiShiagari(
											intShisakuSeq,
											intKoteiCode,
											DataCtrl.getInstance().getTrialTblData().checkNullDecimal(insert),
											DataCtrl.getInstance().getUserMstData().getDciUserid()
										);

						    		//合計仕上重量計算
					    			ShiagariZyuryoKeisan(column);

					    			String shiagariGokeiIns = checkNull(getValueAt(gokeiShiagari, column));

					    			//データ挿入
									DataCtrl.getInstance().getTrialTblData().UpdShiagariRetuDate(
											intShisakuSeq,
											DataCtrl.getInstance().getTrialTblData().checkNullDecimal(shiagariGokeiIns),
											DataCtrl.getInstance().getUserMstData().getDciUserid()
										);
									// ADD start 20121005 QP@20505
									//原料費計算
					    			DispGenryohi();
									// ADD end 20121005 QP@20505
					    		}
						    	// ADD end 20120914 QP@20505 No.1
							}
						}
					}catch(ExceptionBase be){

					}catch(Exception ex){
						ex.printStackTrace();

					}finally{
						//テスト表示
						//DataCtrl.getInstance().getTrialTblData().dispProtoList();
						//DataCtrl.getInstance().getTrialTblData().dispTrial();
					}
				}
			};

			ListMeisai.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			ListMeisai.setRowHeight(17);
			ListMeisai.setAutoResizeMode(ListMeisai.AUTO_RESIZE_OFF);
			ListMeisai.setCellSelectionEnabled(true);
			ListMeisai.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

			//キーイベント設定（モードチェック）
			//モード編集
			if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0125, DataCtrl.getInstance().getParamData().getStrMode())){
				ListMeisai.addKeyListener(new CopyCell());
			}

			//テーブル選択
			ListMeisai.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				//他テーブルへのフォーカス移動時
				public void valueChanged(ListSelectionEvent e){

					int intSelectRow = ListMeisai.getSelectedRow();
					int intSelectCol = ListMeisai.getSelectedColumn();

					if(intSelectRow > -1 && intSelectCol > -1){
						//配合行選択
						HaigoMeisai.setRowSelectionInterval(intSelectRow, (intSelectRow+ListMeisai.getSelectedRows().length-1));
					}else{
						//HaigoMeisai.clearSelection();
					}

					if(e.getValueIsAdjusting()){


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
//						HaigoMeisai.clearSelection();
					}
				}
			});

			//セルエディター＆レンダラー生成
			for(int i=0;i<col;i++){
				this.addListShisakuColumnER(i);
			}

			//データ生成
			int ListCount = 0;
			for(int i=0;i<col;i++){
				//各工程合計計算
				ArrayList koteiGokei = new ArrayList();
				BigDecimal koteiKeisan = null;
				TrialData TrialData = ((TrialData)aryTrialData.get(i)); //試作列データ取得
				int Output = 0;
				int bkKoteiCd = 0;

				// ADD start 20120914 QP@20505 No.1
				ArrayList koteiShiagari = new ArrayList();
				// ADD end 20120914 QP@20505 No.1

				//--------------------- 試作リストデータ表示  --------------------------------
				for(int j=0;j<aryHaigou.size();j++){

					MixedData mixedData = (MixedData)aryHaigou.get(j);
					PrototypeListData PrototypeListData = (PrototypeListData)aryShisakuList.get(ListCount);
					//主キー取得
					String pk_ShisakuSeq = Integer.toString(PrototypeListData.getIntShisakuSeq());
					String pk_KoteiCd = Integer.toString(PrototypeListData.getIntKoteiCd());
					String pk_KoteiSeq = Integer.toString(PrototypeListData.getIntKoteiSeq());


					//-------------------- 工程情報表示  ----------------------------------
					if(mixedData.getIntKoteiNo() != bkKoteiCd){
						//工程計算配列へ追加
						if(koteiKeisan != null){
							koteiGokei.add(koteiKeisan);
						}
						//計算情報初期化
						koteiKeisan = new BigDecimal("0");
						//テーブル設定
						ListMeisai.setRowHeight(Output, 17);
						this.addListShisakuRowER(i, Output, 1);
						bkKoteiCd = mixedData.getIntKoteiNo();
						Output++;

						// ADD start 20120914 QP@20505 No.1
						//値がNULLでない場合
						if(((PrototypeListData)aryShisakuList.get(ListCount)).getDciKouteiShiagari() != null){
							koteiShiagari.add(((PrototypeListData)aryShisakuList.get(ListCount)).getDciKouteiShiagari().toString());
						}else{
							koteiShiagari.add("");
						}
						// ADD end 20120914 QP@20505 No.1

					}
					//--------------------- 量データ表示  ----------------------------------
					String ryo;
					//値がNULLでない場合
					if(((PrototypeListData)aryShisakuList.get(ListCount)).getDciRyo() != null){
						ryo = ((PrototypeListData)aryShisakuList.get(ListCount)).getDciRyo().toString();
						//工程合計加算
						koteiKeisan = koteiKeisan.add(new BigDecimal(ryo));
					//値がNULLの場合
					}else{
						ryo = null;
					}
					ListMeisai.setValueAt(ryo, Output, i);
					//エディタ&レンダラ設定
					this.addListShisakuRowER(i, Output, 0);
					//カウント
					ListCount++;
					Output++;
				}
				koteiGokei.add(koteiKeisan);

				//------------------------- 計算列表示  ----------------------------------
				//工程合計
				BigDecimal allGokei = new BigDecimal("0");
				for(int k=0; k<kotei_num; k++){
					//各工程合計値取得
					BigDecimal koteiAns = (BigDecimal)koteiGokei.get(k);
					//koteiAns = koteiAns.multiply(new BigDecimal("1000"));
					//x工程（ｇ）項目
					ListMeisai.setValueAt(koteiAns, Output, i);
					//エディタ&レンダラ設定
					this.addListShisakuRowER(i, Output, 1);
					Output++;
					//工程合計加算
					allGokei = allGokei.add(koteiAns);
				}

				//合計重量（ｇ）項目
				ListMeisai.setValueAt(allGokei, Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				//工程仕上重量
				for(int l=0; l<kotei_num; l++){
					//x工程仕上重量（ｇ）項目
					ListMeisai.setValueAt(koteiShiagari.get(l), Output, i);
					//エディタ&レンダラ設定
					this.addListShisakuRowER(i, Output, 0);
					Output++;
				}


				//合計仕上重量（ｇ）項目
				ListMeisai.setValueAt(TrialData.getDciShiagari(), Output, i);
				this.addListShisakuRowER(i, Output, 0);
				Output++;

				//原価（ｋｇ）項目
				ListMeisai.setValueAt(null, Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				//2011/06/13 QP@10181_No.29 TT T.Satoh Add Start -------------------------
				//原料費（１個）
				ListMeisai.setValueAt(null, Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;
				//2011/06/13 QP@10181_No.29 TT T.Satoh Add End ---------------------------

				//総酸（％）項目
				ListMeisai.setValueAt(TrialData.getDciSosan(), Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				//食塩（％）項目
				ListMeisai.setValueAt(TrialData.getDciShokuen(), Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				// ADD start 20121002 QP@20505 No.24
//				//ＭＳＧ（％）項目
//				ListMeisai.setValueAt(TrialData.getDciMsg(), Output, i);
//				this.addListShisakuRowER(i, Output, 1);
//				Output++;
				// ADD end 20121002 QP@20505 No.24

				//水相中酸度項目
				ListMeisai.setValueAt(TrialData.getDciSuiSando(), Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				//水相中食塩項目
				ListMeisai.setValueAt(TrialData.getDciSuiShokuen(), Output, i);
				this.addListShisakuRowER(i, Output, 1);
				Output++;

				//水相中酢酸項目
				ListMeisai.setValueAt(TrialData.getDciSuiSakusan(), Output, i);
				this.addListShisakuRowER(i, Output, 1);
				//Output++;

			}
			//Jtableへ中間エディター＆レンダラーを登録
			this.setListShisakuER();

			//------------------------------ 色変更  ------------------------------------
			int Count = 0;
			for(int i=0;i<col;i++){
				//試作列データ取得
				TrialData TrialData = ((TrialData)aryTrialData.get(i));
				int Output = 0;
				//配合データ数ループ
				for(int j=0;j<aryHaigou.size();j++){
					MixedData mixedData = (MixedData)aryHaigou.get(j);
					PrototypeListData PrototypeListData = (PrototypeListData)aryShisakuList.get(Count);

					//工程キー項目取得
					MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(Output, 2);
					DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(Output);
					if(tc.getComponent() instanceof CheckboxBase){

					}else{
						Output++;
					}

//					if(mixedData.getIntGenryoNo() == 1){
//						Output++;
//					}



					//セル色設定
					this.addListShisakuRowER_color(Output, i, Integer.parseInt(PrototypeListData.getStrIro()));

					//カウント
					Count++;
					Output++;
				}
			}

		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}

		return ListMeisai;
	}

	/************************************************************************************
	 *
	 *   試作明細　小数指定洗替
	 *    : リスト内項目の小数部を指定小数分のみ表示
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public String ShosuArai(String strChk){
		String ret = null;

		try{
				if(strChk != null && strChk.length()>0){
					//小数値を取得
					String cd = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShosu();
	    			int val1 = 0;
	    			if(cd != null && cd.length()>0){
	    				val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(cd));
	    			}

	    			//フォーマット設定
	    			String format = "0";
	    			for(int k=0; k<val1; k++){
	    				if(k == 0){
	    					format = format + ".";
	    				}
	    				format = format + "0";
	    			}
	    			DecimalFormat decimalFormat = new DecimalFormat( format );
	    			BigDecimal bigDecimal = new BigDecimal(strChk);
	    			ret = decimalFormat.format( bigDecimal.setScale( val1, BigDecimal.ROUND_DOWN ) );
				}


		}catch(Exception e){
			e.printStackTrace();

			ret = null;


		}finally{

		}

		return ret;
	}

	/************************************************************************************
	 *
	 *   試作明細　小数指定洗替
	 *    : リスト内項目の小数部を指定小数分のみ表示
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public String ShosuArai4(String strChk){
		String ret = null;

		try{
				if(strChk != null && strChk.length()>0){
					//小数値を取得
					String cd = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShosu();
	    			int val1 = 4;


	    			//フォーマット設定
	    			String format = "0";
	    			for(int k=0; k<val1; k++){
	    				if(k == 0){
	    					format = format + ".";
	    				}
	    				format = format + "0";
	    			}
	    			DecimalFormat decimalFormat = new DecimalFormat( format );
	    			BigDecimal bigDecimal = new BigDecimal(strChk);
	    			ret = decimalFormat.format( bigDecimal.setScale( val1, BigDecimal.ROUND_DOWN ) );
				}


		}catch(Exception e){
			e.printStackTrace();

			ret = null;


		}finally{

		}

		return ret;
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.19
	/************************************************************************************
	 *
	 *   試作明細　小数指定洗替
	 *    : リスト内項目の小数部を指定小数分四捨五入して表示
	 *   @author TT k-katayama
	 *
	 ************************************************************************************/
	public String ShosuAraiHulfUp(String strChk){
		String ret = null;

		try{
				if(strChk != null && strChk.length()>0){
					//小数値を取得
					String cd = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrShosu();
	    			int val1 = 0;
	    			if(cd != null && cd.length()>0){
	    				val1 = DataCtrl.getInstance().getLiteralDataShosu().selectLiteralVal1(Integer.parseInt(cd));
	    			}

	    			//フォーマット設定
	    			String format = "0";
	    			for(int k=0; k<val1; k++){
	    				if(k == 0){
	    					format = format + ".";
	    				}
	    				format = format + "0";
	    			}
	    			DecimalFormat decimalFormat = new DecimalFormat( format );
	    			BigDecimal bigDecimal = new BigDecimal(strChk);
	    			ret = decimalFormat.format( bigDecimal.setScale( val1, BigDecimal.ROUND_HALF_UP ) );
				}


		}catch(Exception e){
			e.printStackTrace();

			ret = null;


		}finally{

		}

		return ret;
	}
//add end --------------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
	/************************************************************************************
	 *
	 *   試作明細　小数指定洗替_桁数指定
	 *    : リスト内項目の小数部を指定小数分四捨五入して表示
	 *   @author TT k-katayama
	 *
	 ************************************************************************************/
	public String ShosuAraiHulfUp_keta(String strChk,String keta){
		String ret = null;

		try{
				if(strChk != null && strChk.length()>0){
					//小数値を取得
	    			int val1 = 0;
	    			if(keta != null && keta.length()>0){
	    				val1 = Integer.parseInt(keta);
	    			}

	    			//フォーマット設定
	    			String format = "0";
	    			for(int k=0; k<val1; k++){
	    				if(k == 0){
	    					format = format + ".";
	    				}
	    				format = format + "0";
	    			}
	    			DecimalFormat decimalFormat = new DecimalFormat( format );
	    			BigDecimal bigDecimal = new BigDecimal(strChk);
	    			ret = decimalFormat.format( bigDecimal.setScale( val1, BigDecimal.ROUND_HALF_UP ) );
				}


		}catch(Exception e){
			e.printStackTrace();

			ret = null;


		}finally{

		}

		return ret;
	}
//add end   -------------------------------------------------------------------------------

	/************************************************************************************
	 *
	 *   配合明細　小数指定洗替
	 *    : リスト内項目の小数部を指定小数分のみ表示
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public String ShosuArai_haigo(String strChk){
		String ret = null;

		try{
				if(strChk != null && strChk.length()>0){
					//小数値を取得
	    			int val1 = 2;

	    			//フォーマット設定
	    			String format = "0";
	    			for(int k=0; k<val1; k++){
	    				if(k == 0){
	    					format = format + ".";
	    				}
	    				format = format + "0";
	    			}
	    			DecimalFormat decimalFormat = new DecimalFormat( format );
	    			BigDecimal bigDecimal = new BigDecimal(strChk);
	    			ret = decimalFormat.format( bigDecimal.setScale( val1, BigDecimal.ROUND_DOWN ) );
				}


		}catch(Exception e){
			e.printStackTrace();

			ret = null;


		}finally{

		}

		return ret;
	}


	/************************************************************************************
	 *
	 *   原料費（ｋｇ）計算
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void DispGenryohi() throws ExceptionBase{
		try{

			//------------------------------- 原料費（ｋｇ）計算 --------------------------------

			//原価原料データ取得（全件）
			ArrayList aryGenka = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(0);

			//原価（Kg）計算
			for(int i=0; i<aryGenka.size(); i++){

				//原価原料データ取得（1件）
				CostMaterialData CostMaterialData = (CostMaterialData)aryGenka.get(i);

				//原価（Kg）計算実行
				DataCtrl.getInstance().getTrialTblData().UpdGenkaGenryoMath(CostMaterialData);

			}


			//------------------------------- 原料費（ｋｇ）表示 --------------------------------

			//原料費　行番号取得
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int row = ListMeisai.getRowCount()-6;
			int row = ListMeisai.getRowCount()-7;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

			//試作列数分ループ
			for(int j=0; j<ListHeader.getColumnCount(); j++){

				//試作SEQ取得
				MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, j);
				DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
				CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
				int shisakuSeq  = Integer.parseInt(chkSeq.getPk1());

				//原価原料データ取得（計算済データ）
    			ArrayList aryKeisanGenka = DataCtrl.getInstance().getTrialTblData().SearchGenkaGenryoData(shisakuSeq);

    			//原料費（ｋｇ）取得
    			CostMaterialData keisanCostMaterialData = (CostMaterialData)aryKeisanGenka.get(0);
    			String genryohi = keisanCostMaterialData.getStrGenryohi();

    			//2011/06/13 QP@10181_No.29 TT T.Satoh Add Start -------------------------
    			//原料費（１個）取得[原料費(1本当)と同じ計算なので流用]
    			String genryohitan = keisanCostMaterialData.getStrGenryohiTan();
    			//2011/06/13 QP@10181_No.29 TT T.Satoh Add End ---------------------------

				//表示値設定
    			ListMeisai.setValueAt(genryohi, row, j);
    			//2011/06/13 QP@10181_No.29 TT T.Satoh Add Start -------------------------
    			ListMeisai.setValueAt(genryohitan, row+1, j);
    			//2011/06/13 QP@10181_No.29 TT T.Satoh Add End ---------------------------

			}

		}catch(ExceptionBase eb){

			throw eb;

		}catch(Exception e){

			e.printStackTrace();

			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			//2011/04/22 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//this.ex.setStrErrmsg("試作①原料費（ｋg）表示に失敗しました");
			this.ex.setStrErrmsg("配合表原料費（ｋg）表示に失敗しました");
			//2011/04/22 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   各列工程合計計算
	 *    : 各列の工程合計値を計算し、テーブルへ設定する
	 *   @author TT nishigawa
	 *   @param sol : 計算行
	 *
	 ************************************************************************************/
	public void koteiSum(int col){
		try{
			//-------------------------------- 初期処理 ----------------------------------
			//各工程合計計算
			ArrayList koteiGokei = new ArrayList();
			BigDecimal koteiKeisan = null;
			//配合データ取得
			ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
			//最大工程順取得
			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
			//計算対象列数取得
			// MOD start 20120914 QP@20505 No.1
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
			//int keisanRow = ListMeisai.getRowCount()-maxKotei-8;
//			int keisanRow = ListMeisai.getRowCount()-maxKotei-9;
			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
			int keisanRow = ListMeisai.getRowCount() - ( maxKotei * 2 ) - 9;
			// ADD end 20120914 QP@20505 No.1

			//合計挿入開始行
			int Output = 0;

			//-------------------------------- 計算処理 ----------------------------------
			//試作明細の行数ループ
			for(int i=0; i<keisanRow; i++){
				MiddleCellEditor mceHaigo = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor dceHaigo = (DefaultCellEditor)mceHaigo.getTableCellEditor(i);

				//原料行
				if(dceHaigo.getComponent() instanceof CheckboxBase){
					//量データ取得
					String ryo = (String)ListMeisai.getValueAt(i, col);
					//NULLでない場合に処理
					if(ryo != null && ryo.length() > 0){
						koteiKeisan = koteiKeisan.add(new BigDecimal(ryo));
					}
				//工程行
				}else{
					//工程計算配列へ追加
					if(koteiKeisan != null){
						koteiGokei.add(koteiKeisan);
					}
					//計算情報初期化
					koteiKeisan = new BigDecimal("0");
				}

				Output++;
			}
			koteiGokei.add(koteiKeisan);

			//----------------------------- 各工程合計表示 -------------------------------
			BigDecimal allGokei = new BigDecimal("0");
			for(int k=0; k<maxKotei; k++){
				//各工程合計値取得
				BigDecimal koteiAns = (BigDecimal)koteiGokei.get(k);
				//koteiAns = koteiAns.multiply(new BigDecimal("1000"));
				//x工程（ｇ）項目
				ListMeisai.setValueAt(koteiAns, Output, col);
				Output++;
				//工程合計加算
				allGokei = allGokei.add(koteiAns);
			}
			//合計重量（ｇ）項目
			ListMeisai.setValueAt(allGokei, Output, col);
			Output++;

		}catch(Exception e){
			e.printStackTrace();

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作ヘッダテーブル　試作列エディタ＆レンダラ列追加
	 *    : 試作ヘッダテーブルへセルエディタ＆レンダラを追加する
	 *   @author TT nishigawa
	 *   @param row : 追加行
	 *   @param koteiCd  : 試作SEQ
	 *
	 ************************************************************************************/
	public void addRetuShisakuColER(int col,String sisakuSeq){
		try{
			//試作列データ取得
			ArrayList aryRetu =
				DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(Integer.parseInt(sisakuSeq));
			TrialData TrialData = (TrialData)aryRetu.get(0);

			//---------------------- 追加用中間エディタ＆レンダラ生成  --------------------------
			MiddleCellEditor MiddleCellEditor = new MiddleCellEditor(ListHeader);
			MiddleCellRenderer MiddleCellRenderer = new MiddleCellRenderer();
			int seq = TrialData.getIntShisakuSeq(); //SEQ

			//----------------------------------- 1行目  ----------------------------------
			//製法マーク設定
			String mark = "";
			int seihoseq = PrototypeData.getIntSeihoShisaku();
			if(seq == seihoseq){
				mark = JwsConstManager.JWS_MARK_0003+" ";
			}

			//原価試算マーク設定
			int genka = TrialData.getFlg_init();
			if(genka > 0){
				mark = mark + JwsConstManager.JWS_MARK_0004;
			}

			//コンポーネント生成
			CheckboxBase listSelect = new CheckboxBase();
			listSelect.setHorizontalAlignment(listSelect.LEFT);
			listSelect.setText(mark);
			listSelect.setBackground(clBlue);

			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0016, DataCtrl.getInstance().getParamData().getStrMode())){
				listSelect.setEnabled(false);
			}

			//チェック時のイベント追加
			listSelect.addActionListener(new CheckShisaku());
			listSelect.setActionCommand("shisakuCheck");

			//コンポーネントへキー項目追加
			listSelect.setPk1(sisakuSeq);

			//ボタングループへ追加
			//ShisakuCheck.add(listSelect);

			//エディター＆レンダラー生成
			DefaultCellEditor editor0 = new DefaultCellEditor(listSelect);
			CheckBoxCellRenderer renderer0 = new CheckBoxCellRenderer(listSelect);

			//中間エディタ＆レンダラへ登録
			MiddleCellEditor.addEditorAt(0, editor0);
			MiddleCellRenderer.add(0, renderer0);

			//----------------------------------- 2行目  ----------------------------------
			ArrayList SeizoKotei =
				DataCtrl.getInstance().getTrialTblData().SearchSeizoKouteiData(0); //製造工程データ
			int tyuiNum = 0;
			if(TrialData.getStrTyuiNo() != null){
				tyuiNum = Integer.parseInt(TrialData.getStrTyuiNo());
			}
			String setTyuiNum = "";

			//コンポーネント生成
			ComboBase comboSelect = new ComboBase();
			comboSelect.setPk1(sisakuSeq);
			comboSelect.addItem("");

			//注意事項No追加
			for(int j=0;j<SeizoKotei.size();j++){
				ManufacturingData ManufacturingData = (ManufacturingData)SeizoKotei.get(j);
				String seizoNum = Integer.toString(ManufacturingData.getIntTyuiNo());
				comboSelect.addItem(seizoNum);
			}

			//注意事項No選択
			if(tyuiNum > 0){
				setTyuiNum = Integer.toString(tyuiNum);
				comboSelect.setSelectedItem(setTyuiNum);
			}

			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0017, DataCtrl.getInstance().getParamData().getStrMode())){
				comboSelect.setEnabled(false);

			}else{
				comboSelect.addActionListener(new selectTyuui());

			}

			//エディター＆レンダラー生成
			ComboBoxCellEditor editor1 = new ComboBoxCellEditor(comboSelect);
			ComboBoxCellRenderer renderer1 = new ComboBoxCellRenderer(comboSelect);

			//中間エディタ＆レンダラへ登録
			MiddleCellEditor.addEditorAt(1, editor1);
			MiddleCellRenderer.add(1, renderer1);

			//----------------------------------- 3行目  ----------------------------------
			//コンポーネント生成
			HankakuTextbox tbHiduke = new HankakuTextbox();
			tbHiduke.setBackground(Yellow);

			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0018, DataCtrl.getInstance().getParamData().getStrMode())){
				tbHiduke.setEditable(false);
			}

			//エディター＆レンダラー生成
			TextFieldCellEditor editor2 = new TextFieldCellEditor(tbHiduke);
			TextFieldCellRenderer renderer2 = new TextFieldCellRenderer(tbHiduke);

			//中間エディター＆レンダラーへ登録
			MiddleCellEditor.addEditorAt(2, editor2);
			MiddleCellRenderer.add(2, renderer2);

			//----------------------------------- 4行目  ----------------------------------
			//コンポーネント生成
			TextboxBase tbSample = new TextboxBase();
			tbSample.setBackground(Yellow);

			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0019, DataCtrl.getInstance().getParamData().getStrMode())){
				tbSample.setEditable(false);
			}

			//エディター＆レンダラー生成
			TextFieldCellEditor editor3 = new TextFieldCellEditor(tbSample);
			TextFieldCellRenderer renderer3 = new TextFieldCellRenderer(tbSample);

			//中間エディター＆レンダラーへ登録
			MiddleCellEditor.addEditorAt(3, editor3);
			MiddleCellRenderer.add(3, renderer3);

			//----------------------------------- 5行目  ----------------------------------
			//エディター＆レンダラー生成
			TextAreaCellEditor editor4 = new TextAreaCellEditor(ListHeader);
			editor4.getTextArea().setBackground(Yellow);

			//【QP@20505】No5 2012/10/12 TT H.SHIMA ADD Start
			// リスナーの設定
			editor4.getTextArea().addMouseListener(new memoPanelDisp(ListHeader));
			//【QP@20505】No5 2012/10/12 TT H.SHIMA ADD Start
			
			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0020, DataCtrl.getInstance().getParamData().getStrMode())){
				editor4.getTextArea().setEditable(false);
			}
			TextAreaCellRenderer renderer4 = new TextAreaCellRenderer();
			renderer4.setColor(JwsConstManager.SHISAKU_F2_COLOR);
			//DefaultCellRenderer renderer4 = new DefaultCellRenderer(editor4.getTextArea());

			//中間エディター＆レンダラーへ登録
			MiddleCellEditor.addEditorAt(4, editor4);
			MiddleCellRenderer.add(4, renderer4);

			//----------------------------------- 6行目  ----------------------------------
			int Insatu = TrialData.getIntInsatuFlg();

			//コンポーネント生成
			CheckboxBase InsatuFg = new CheckboxBase();
			InsatuFg.setBackground(Yellow);
			InsatuFg.setHorizontalAlignment(listSelect.CENTER);
			if(Insatu == 1){
				InsatuFg.setSelected(true);
			}

			//モード編集
			if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0021, DataCtrl.getInstance().getParamData().getStrMode())){
				InsatuFg.setEnabled(false);
			}

			//エディター＆レンダラー生成
			DefaultCellEditor editor5 = new DefaultCellEditor(InsatuFg);
			CheckBoxCellRenderer renderer5 = new CheckBoxCellRenderer(InsatuFg);

			//中間エディター＆レンダラーへ登録
			MiddleCellEditor.addEditorAt(5, editor5);
			MiddleCellRenderer.add(5, renderer5);

			//試作ヘッダーエディタ＆レンダラ配列へ追加
			aryHeaderShisakuCellEditor.add(col,MiddleCellEditor);
			aryHeaderShisakuCellRenderer.add(col, MiddleCellRenderer);

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作ヘッダーテーブル　エディタ＆レンダラ列削除
	 *    : 試作ヘッダーテーブルよりセルエディタ＆レンダラ列を削除する
	 *   @author TT nishigawa
	 *   @param col : 削除列数
	 *
	 ************************************************************************************/
	public void removeHeaderShisakuColER(int col){
		try{
			//エディタ＆レンダラ削除
			aryHeaderShisakuCellEditor.remove(col);
			aryHeaderShisakuCellRenderer.remove(col);
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作ヘッダーテーブル　エディタ＆レンダラ列入替
	 *    : 試作ヘッダーテーブルよりセルエディタ＆レンダラ列を入替する
	 *   @author TT nishigawa
	 *   @param  col_moto : 入替元列数
	 *   @param  col_saki : 入替先列数
	 *
	 ************************************************************************************/
	public void changeHeaderShisakuColER(int col_moto, int col_saki){
		try{
			//入替元エディタ＆レンダラ列取得
			MiddleCellEditor mc_moto = (MiddleCellEditor)aryHeaderShisakuCellEditor.get(col_moto);
			MiddleCellRenderer mr_moto = (MiddleCellRenderer)aryHeaderShisakuCellRenderer.get(col_moto);

			//入替先エディタ＆レンダラ列取得
			MiddleCellEditor mc_saki = (MiddleCellEditor)aryHeaderShisakuCellEditor.get(col_saki);
			MiddleCellRenderer mr_saki = (MiddleCellRenderer)aryHeaderShisakuCellRenderer.get(col_saki);

			//エディタ列入替
			aryHeaderShisakuCellEditor.set(col_moto, mc_saki);
			aryHeaderShisakuCellEditor.set(col_saki, mc_moto);

			//レンダラ列入替
			aryHeaderShisakuCellRenderer.set(col_moto, mr_saki);
			aryHeaderShisakuCellRenderer.set(col_saki, mr_moto);

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作ヘッダーテーブル　エディタ＆レンダラ設定
	 *    : 試作ヘッダーテーブルへセルエディタ＆レンダラを設定する
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void setHeaderShisakuER(){
		try{
			//テーブルカラム取得
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)ListHeader.getColumnModel();
			TableColumn column = null;

			for(int i = 0; i<ListHeader.getColumnCount(); i++){

				//エディタ＆レンダラ配列より取得
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryHeaderShisakuCellEditor.get(i);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryHeaderShisakuCellRenderer.get(i);

				//テーブルカラムへ設定
				column = ListHeader.getColumnModel().getColumn(i);
				column.setCellEditor(MiddleCellEditor);
				column.setCellRenderer(MiddleCellRenderer);
			}

			//注意事項No行の高さ設定
			ListHeader.setRowHeight(1,21);

			//ｻﾝﾌﾟﾙNo行の高さ設定
			ListHeader.setRowHeight(3, 17);

			//メモ行の高さ設定
			ListHeader.setRowHeight(4, 22);

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   配合明細テーブル　原料エディタ＆レンダラ行追加
	 *    : 配合明細テーブルへ原料行のセルエディタ＆レンダラを追加する
	 *   @author TT nishigawa
	 *   @param row : 追加行
	 *   @param flg : 0=原料行 or 1=工程行　or 2=計算行
	 *   @param koteiCd  : 工程CD
	 *   @param koteiSeq : 工程SEQ
	 *
	 ************************************************************************************/
	public void addHaigoMeisaiRowER(int row,int flg,String koteiCd,String koteiSeq,int color){
		try{
			//--------------- 工程選択エディター＆レンダラー生成  -------------------------
			JComponent KoteiSel;
			TableCellEditor editKoteiSel;
			TableCellRenderer rendKoteiSel;

			//原料行
			if(flg == 0){
				KoteiSel = new TextboxBase();
				((TextboxBase)KoteiSel).setEditable(false);
				KoteiSel.setBackground(Color.WHITE);
				editKoteiSel = new TextFieldCellEditor((TextboxBase)KoteiSel);
				rendKoteiSel = new TextFieldCellRenderer((TextboxBase)KoteiSel);

			//工程行
			}else if(flg == 1){

				//工程情報取得
				ArrayList aryHaigoData =
					DataCtrl.getInstance().getTrialTblData().SearchHaigoData(Integer.parseInt(koteiCd));
				MixedData MixedData = new MixedData();
				String koteiNo = "";
				String koteiNm = "";
				String koteiZoku = "";

				//試作テーブルデータ保持クラスよりデータ検索
				for(int i=0; i<aryHaigoData.size(); i++){
					MixedData = (MixedData)aryHaigoData.get(i);

					//引数と同工程CDの工程順、工程名、工程属性を取得
					if(MixedData.getIntKoteiSeq() == Integer.parseInt(koteiSeq)){
						koteiNo = Integer.toString(MixedData.getIntKoteiNo());
						koteiNm = MixedData.getStrKouteiNm();
						koteiZoku = MixedData.getStrKouteiZokusei();
					}
				}

				//コンポーネント生成
				KoteiSel = new CheckboxBase();
				KoteiSel.setPreferredSize(new Dimension(13, 13));
				KoteiSel.setBackground(Color.WHITE);
				KoteiSel.setBorder(line);

				//チェック時のイベント追加
				((CheckboxBase)KoteiSel).addActionListener(new CheckKotei());
				((CheckboxBase)KoteiSel).setActionCommand("koteiCheck");

				//コンポーネントにキーを設定
				((CheckboxBase)KoteiSel).setPk1(koteiCd);
				((CheckboxBase)KoteiSel).setPk2(koteiSeq);
				((CheckboxBase)KoteiSel).setPk3(koteiNo);
				((CheckboxBase)KoteiSel).setPk4(koteiNm);
				((CheckboxBase)KoteiSel).setPk5(koteiZoku);

				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0022, DataCtrl.getInstance().getParamData().getStrMode())){
					((CheckboxBase)KoteiSel).setEnabled(false);
				}

				//ボタングループへ追加
				//KoteiCheck.add((CheckboxBase)KoteiSel);
				editKoteiSel = new DefaultCellEditor((CheckboxBase)KoteiSel);
				rendKoteiSel = new CheckBoxCellRenderer((CheckboxBase)KoteiSel);

			}else{

				//コンポーネント生成
				KoteiSel = new TextboxBase();
				((TextboxBase)KoteiSel).setEditable(false);
				KoteiSel.setBackground(Color.WHITE);

				//エディター＆レンダラー生成
				editKoteiSel = new TextFieldCellEditor((TextboxBase)KoteiSel);
				rendKoteiSel = new TextFieldCellRenderer((TextboxBase)KoteiSel);
			}

			//中間セルエディター＆レンダラーへ登録
			HaigoMeisaiCellEditor0.addEditorAt(row, editKoteiSel);
			HaigoMeisaiCellRenderer0.add(row, rendKoteiSel);

			//----------------- 工程順エディター＆レンダラー生成  ---------------------------
			TextboxBase KoteiNo = new TextboxBase();
			KoteiNo.setEditable(false);
			KoteiNo.setBackground(Color.WHITE);
			TextFieldCellEditor editKoteiNo = new TextFieldCellEditor(KoteiNo);
			TextFieldCellRenderer rendKoteiNo = new TextFieldCellRenderer(KoteiNo);

			//中間セルエディター＆レンダラーへ登録
			HaigoMeisaiCellEditor1.addEditorAt(row, editKoteiNo);
			HaigoMeisaiCellRenderer1.add(row, rendKoteiNo);

			//----------------- 原料選択エディター＆レンダラー生成  -------------------------
			JComponent GenryoSel;
			TableCellEditor editGenryoSel;
			TableCellRenderer rendGenryoSel;

			//原料行
			if(flg == 0){
				GenryoSel = new CheckboxBase();
				GenryoSel.setBackground(Color.WHITE);

				//チェック時のイベント追加
				((CheckboxBase)GenryoSel).addActionListener(new CheckGenryo());
				((CheckboxBase)GenryoSel).setActionCommand("genryoCheck");

				//コンポーネントにキーを設定
				((CheckboxBase)GenryoSel).setPk1(koteiCd);
				((CheckboxBase)GenryoSel).setPk2(koteiSeq);

				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0023, DataCtrl.getInstance().getParamData().getStrMode())){
					((CheckboxBase)GenryoSel).setEnabled(false);
				}
				editGenryoSel = new DefaultCellEditor((CheckboxBase)GenryoSel);
				rendGenryoSel = new CheckBoxCellRenderer((CheckboxBase)GenryoSel);

			//工程行
			}else{
				GenryoSel = new TextboxBase();
				GenryoSel.setBackground(Color.WHITE);
				((TextboxBase)GenryoSel).setEditable(false);
				GenryoSel.setBackground(Color.WHITE);
				editGenryoSel = new TextFieldCellEditor((TextboxBase)GenryoSel);
				rendGenryoSel = new TextFieldCellRenderer((TextboxBase)GenryoSel);
			}

			//中間セルエディター＆レンダラーへ登録
			HaigoMeisaiCellEditor2.addEditorAt(row, editGenryoSel);
			HaigoMeisaiCellRenderer2.add(row, rendGenryoSel);

			//------------------- 原料CDエディター＆レンダラー生成  -------------------------
			JComponent GenryoCD;
			TableCellEditor editGenryoCD;
			TableCellRenderer rendGenryoCD;

			//原料行
			if(flg == 0){
				GenryoCD = new HankakuTextbox(){

					/**
					 * テキストボックスのフォルトモデルの作成
					 * @return 半角文字用Document
					 */
				    protected Document createDefaultModel() {
				    	 //文字制限付きＤｏｃｕｍｅｎｔ指定
					     return new HankakuDocumentGenryo();
				    }
				};
				GenryoCD.setBackground(Color.WHITE);

				//桁数取得
				int keta = getKeta();

				//桁指定
				((HankakuTextbox)GenryoCD).setIntMaxLength(keta);

				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0025, DataCtrl.getInstance().getParamData().getStrMode())){
					((HankakuTextbox)GenryoCD).setEditable(false);

				}else{
					GenryoCD.addMouseListener(new analysisDisp());
					//GenryoCD.addKeyListener(new CopyGenryo());
				}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
				//工程キー項目取得
				int intShisakuSeq = 0;
				int intKoteiCd = Integer.parseInt(koteiCd);
				int intKoteiSeq = Integer.parseInt(koteiSeq);
				boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

				//編集可能の場合：既存処理
				if(chk){
					//モード編集
					if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0025, DataCtrl.getInstance().getParamData().getStrMode())){
						((HankakuTextbox)GenryoCD).setEditable(true);
					}
				}
				//編集不可の場合：コンポーネントの操作不可
				else{
					((HankakuTextbox)GenryoCD).setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					((HankakuTextbox)GenryoCD).setEditable(false);
				}
//add end   -------------------------------------------------------------------------------

				editGenryoCD = new TextFieldCellEditor((HankakuTextbox)GenryoCD);
				rendGenryoCD = new TextFieldCellRenderer((HankakuTextbox)GenryoCD);

			//工程行
			}else if(flg == 1){

				GenryoCD = new ComboBase();
				GenryoCD.setFont(new Font("Default", Font.PLAIN, 11));
				GenryoCD.setBackground(Color.WHITE);

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
				//setLiteralCmb((ComboBase)GenryoCD, DataCtrl.getInstance().getLiteralDataZokusei(), "", 0);

				//工程パターン取得
				String ptKotei = PrototypeData.getStrPt_kotei();

				//工程パターンが「空白」の場合
				if(ptKotei == null || ptKotei.length() == 0){
					//選択値に空行のみ
					((ComboBase) GenryoCD).removeAllItems();
					((ComboBase) GenryoCD).addItem("");
				}
				//工程パターンが「空白」でない場合
				else{
					//工程パターンのValue1取得
					String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

					//工程パターンが「調味料１液タイプ」の場合
					if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){
						//選択値を設定
						setLiteralCmb((ComboBase)GenryoCD, DataCtrl.getInstance().getLiteralDataKotei_tyomi1(), "", 0);

					}
					//工程パターンが「調味料２液タイプ」の場合
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){
						//選択値を設定
						setLiteralCmb((ComboBase)GenryoCD, DataCtrl.getInstance().getLiteralDataKotei_tyomi2(), "", 0);

					}
					//工程パターンが「その他・加食タイプ」の場合
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){
						//選択値を設定
						setLiteralCmb((ComboBase)GenryoCD, DataCtrl.getInstance().getLiteralDataKotei_sonota(), "", 0);

					}
				}


//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0024, DataCtrl.getInstance().getParamData().getStrMode())){
					((ComboBase)GenryoCD).setEnabled(false);
				}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
				//工程キー項目取得
				int intShisakuSeq = 0;
				int intKoteiCd = Integer.parseInt(koteiCd);
				int intKoteiSeq = 0;
				boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);

				//編集可能の場合：既存処理
				if(chk){
					//モード編集
					if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0024, DataCtrl.getInstance().getParamData().getStrMode())){
						((ComboBase)GenryoCD).setEnabled(true);
					}
				}
				//編集不可の場合：コンポーネントの操作不可
				else{
					((ComboBase)GenryoCD).setEnabled(false);
				}
//add end   -------------------------------------------------------------------------------

				editGenryoCD = new ComboBoxCellEditor((ComboBase)GenryoCD);
				rendGenryoCD = new ComboBoxCellRenderer((ComboBase)GenryoCD);

			//計算行
			}else{

				//コンポーネント生成
				GenryoCD = new TextboxBase();
				((TextboxBase)GenryoCD).setEditable(false);
				GenryoCD.setBackground(Color.WHITE);

				//エディター＆レンダラー生成
				editGenryoCD = new TextFieldCellEditor((TextboxBase)GenryoCD);
				rendGenryoCD = new TextFieldCellRenderer((TextboxBase)GenryoCD);
			}
			//中間セルエディター＆レンダラーへ登録
			HaigoMeisaiCellEditor3.addEditorAt(row, editGenryoCD);
			HaigoMeisaiCellRenderer3.add(row, rendGenryoCD);

			//------------------ 原料名エディター＆レンダラー生成  -------------------------
			JComponent GenryoNm;
			TextFieldCellEditor editGenryoNm;
			TextFieldCellRenderer rendGenryoNm;

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
			//工程キー項目取得
			int intShisakuSeq = 0;
			int intKoteiCd = Integer.parseInt(koteiCd);
			int intKoteiSeq = Integer.parseInt(koteiSeq);
			boolean chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, intKoteiCd, intKoteiSeq);
//add end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.30
			boolean chk_2 = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoCdSinki(intKoteiCd, intKoteiSeq);
//add end   -------------------------------------------------------------------------------
			//2011/06/02 QP@10181_No.73 TT T.Satoh Add Start -------------------------
			//会社コードがキュピーか判定
			boolean kaishaCd_QP = DataCtrl.getInstance().getTrialTblData().searchHaigouKaishaCd(intKoteiCd, intKoteiSeq);
			//2011/06/02 QP@10181_No.73 TT T.Satoh Add End ---------------------------

			//原料行
			if(flg == 0){
				GenryoNm = new TextboxBase();

				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0027, DataCtrl.getInstance().getParamData().getStrMode())){
					((TextboxBase)GenryoNm).setEditable(false);

				}else{

					//原料行の場合：色変更イベント追加
					((TextboxBase)GenryoNm).addMouseListener(new colorChange(HaigoMeisai,0));
				}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.30,No.33,No.34
				//編集可能の場合：既存処理
				if(chk && chk_2){

				}
				//編集不可の場合：コンポーネントの操作不可
				else{
					((TextboxBase)GenryoNm).setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					((TextboxBase)GenryoNm).setEditable(false);
				}
//add end   -------------------------------------------------------------------------------

				editGenryoNm = new TextFieldCellEditor((TextboxBase)GenryoNm);
				rendGenryoNm = new TextFieldCellRenderer((TextboxBase)GenryoNm);

			//工程行
			}else if(flg == 1){
				GenryoNm = new TextboxBase();

				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0026, DataCtrl.getInstance().getParamData().getStrMode())){
					((TextboxBase)GenryoNm).setEditable(false);
				}
				editGenryoNm = new TextFieldCellEditor((TextboxBase)GenryoNm);
				rendGenryoNm = new TextFieldCellRenderer((TextboxBase)GenryoNm);

			//計算行
			}else{
				//コンポーネント生成
				GenryoNm = new TextboxBase();
				((TextboxBase)GenryoNm).setEditable(false);
				GenryoNm.setBackground(Color.WHITE);

				//エディター＆レンダラー生成
				editGenryoNm = new TextFieldCellEditor((TextboxBase)GenryoNm);
				rendGenryoNm = new TextFieldCellRenderer((TextboxBase)GenryoNm);
			}

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.30,No.33,No.34
			//色設定
			//rendGenryoNm.setColor(new Color(color));

			//編集可能の場合：既存処理
			if(chk && chk_2){
				rendGenryoNm.setColor(new Color(color));
			}
			//編集不可の場合：色変更不可
			else{

			}
//mod end   -------------------------------------------------------------------------------


			//中間セルエディター＆レンダラーへ登録
			HaigoMeisaiCellEditor4.addEditorAt(row, editGenryoNm);
			HaigoMeisaiCellRenderer4.add(row, rendGenryoNm);

			//-------------------- 単価エディター＆レンダラー生成  --------------------------
			JComponent Tanka;

			//原料行
			if(flg == 0){

				Tanka = new NumelicTextbox();

				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0028, DataCtrl.getInstance().getParamData().getStrMode())){
					((NumelicTextbox)Tanka).setEditable(false);
				}else{
					Tanka.addMouseListener(new colorChange(HaigoMeisai,0));
				}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No30,.33,34
				//編集可能の場合：既存処理
				if(chk && chk_2){
					((TextboxBase)Tanka).setEditable(true);
				}
				//編集不可の場合：コンポーネントの操作不可
				else{
					((TextboxBase)Tanka).setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					((TextboxBase)Tanka).setEditable(false);
				}
//add end   -------------------------------------------------------------------------------

				//2011/05/10 QP@10181_No.73 TT T.Satoh Add Start -------------------------
				//配合量が入力されていない場合
				if(chk){

					//会社CDがキューピーの場合
					if (kaishaCd_QP) {

					}
					//会社CDがキューピーでない場合
					else{
						//単価を編集可能にする
						((TextboxBase)Tanka).setBackground(Color.WHITE);
						((TextboxBase)Tanka).setEditable(true);
					}
				}

				//モード編集
				if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0028, DataCtrl.getInstance().getParamData().getStrMode())){
				}
				else{
					((NumelicTextbox)Tanka).setEditable(false);
				}
				//2011/05/17 QP@10181_No.73 TT T.Satoh Add End ---------------------------

			//工程行
			}else{
				//TextField（入力不可）コンポーネント生成
				Tanka = new TextboxBase();
				((TextboxBase)Tanka).setEditable(false);
				Tanka.setBackground(Color.WHITE);
			}

			TextFieldCellEditor editTanka = new TextFieldCellEditor((TextboxBase)Tanka);
			TextFieldCellRenderer rendTanka = new TextFieldCellRenderer((TextboxBase)Tanka);

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.30,No.33,No.34
			//色設定
			//rendTanka.setColor(new Color(color));

			//編集可能の場合：既存処理
			if(chk && chk_2){
				rendTanka.setColor(new Color(color));
			}
			//編集不可の場合：色変更不可
			else{

			}
//mod end   -------------------------------------------------------------------------------

			//中間セルエディター＆レンダラーへ登録
			HaigoMeisaiCellEditor5.addEditorAt(row, editTanka);
			HaigoMeisaiCellRenderer5.add(row, rendTanka);

			//--------------------- 歩留エディター＆レンダラー生成  --------------------------
			JComponent Budomari;
			//原料行
			if(flg == 0){
				Budomari = new NumelicTextbox();
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0029, DataCtrl.getInstance().getParamData().getStrMode())){
					((NumelicTextbox)Budomari).setEditable(false);
				}else{
					Budomari.addMouseListener(new colorChange(HaigoMeisai,0));
				}
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,No.34
				//編集可能の場合：既存処理
				if(chk){
					((NumelicTextbox)Budomari).setEditable(true);
				}
				//編集不可の場合：コンポーネントの操作不可
				else{
					((NumelicTextbox)Budomari).setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					((NumelicTextbox)Budomari).setEditable(false);
				}
//add end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
				boolean chk_3 = DataCtrl.getInstance().getTrialTblData().searchHaigouGenryoMaBudomari(intKoteiCd, intKoteiSeq);
				//歩留がマスタと同一の場合：既存処理
				if(chk_3){

				}
				//歩留がマスタと相違の場合：コンポーネントの操作不可
				else{
					((NumelicTextbox)Budomari).setFont(new Font("Default", Font.BOLD, 12));
					((NumelicTextbox)Budomari).setForeground(Color.red);
				}
//add end   -------------------------------------------------------------------------------

				//2011/05/17 QP@10181_No.73 TT T.Satoh Add Start -------------------------
				//モード編集
				if (!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0029, DataCtrl.getInstance().getParamData().getStrMode())) {
					//歩留を入力不可能にする
					((NumelicTextbox)Budomari).setEditable(false);
				}
				//2011/05/17 QP@10181_No.73 TT T.Satoh Add End ---------------------------

			//工程行
			}else{
				//TextField（入力不可）コンポーネント生成
				Budomari = new TextboxBase();
				((TextboxBase)Budomari).setEditable(false);
				Budomari.setBackground(Color.WHITE);
			}
			TextFieldCellEditor editBudomari = new TextFieldCellEditor((TextboxBase)Budomari);
			TextFieldCellRenderer rendBudomari = new TextFieldCellRenderer((TextboxBase)Budomari);

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,No.34
			//色設定
			//rendBudomari.setColor(new Color(color));

			//編集可能の場合：既存処理
			if(chk){
				rendBudomari.setColor(new Color(color));
			}
			//編集不可の場合：色変更不可
			else{

			}
//mod end   -------------------------------------------------------------------------------

			//中間セルエディター＆レンダラーへ登録
			HaigoMeisaiCellEditor6.addEditorAt(row, editBudomari);
			HaigoMeisaiCellRenderer6.add(row, rendBudomari);

			//------------------- 油含有率エディター＆レンダラー生成  -------------------------
			JComponent Abura;
			//原料行
			if(flg == 0){
				Abura = new NumelicTextbox();
				//モード編集
				if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0030, DataCtrl.getInstance().getParamData().getStrMode())){
					((NumelicTextbox)Abura).setEditable(false);
				}else{
					Abura.addMouseListener(new colorChange(HaigoMeisai,0));
				}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,No.34
				//編集可能の場合：既存処理
				if(chk){
					((NumelicTextbox)Abura).setEditable(true);
				}
				//編集不可の場合：コンポーネントの操作不可
				else{
					((NumelicTextbox)Abura).setBackground(JwsConstManager.JWS_DISABLE_COLOR);
					((NumelicTextbox)Abura).setEditable(false);
				}
//add end   -------------------------------------------------------------------------------

				//2011/05/17 QP@10181_No.73 TT T.Satoh Add Start -------------------------
				//モード編集
				if (!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
						JwsConstManager.JWS_COMPONENT_0030, DataCtrl.getInstance().getParamData().getStrMode())) {
					//油含有率を入力不可能にする
					((NumelicTextbox)Abura).setEditable(false);
				}
				//2011/05/17 QP@10181_No.73 TT T.Satoh Add End ---------------------------

			//工程行
			}else{
				//TextField（入力不可）コンポーネント生成
				Abura = new TextboxBase();
				((TextboxBase)Abura).setEditable(false);
				Abura.setBackground(Color.WHITE);
			}
			TextFieldCellEditor editAbura = new TextFieldCellEditor((TextboxBase)Abura);
			TextFieldCellRenderer rendAbura = new TextFieldCellRenderer((TextboxBase)Abura);
//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,No.34
			//色設定
			//rendAbura.setColor(new Color(color));

			//編集可能の場合：既存処理
			if(chk){
				rendAbura.setColor(new Color(color));
			}
			//編集不可の場合：色変更不可
			else{

			}
//mod end   -------------------------------------------------------------------------------

			//中間セルエディター＆レンダラーへ登録
			HaigoMeisaiCellEditor7.addEditorAt(row, editAbura);
			HaigoMeisaiCellRenderer7.add(row, rendAbura);

		}catch(ExceptionBase eb){
			eb.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}


	/************************************************************************************
	 *
	 *   配合明細テーブル　原料エディタ＆レンダラ行移動
	 *    : 配合明細テーブルへ原料行のセルエディタ＆レンダラの移動を行う
	 *   @author TT nishigawa
	 *   @param row_moto : 移動元　行番号
	 *   @param row_saki : 移動先　行番号
	 *
	 ************************************************************************************/
	public int getKeta(){

		//桁数初期化
		String keta = JwsConstManager.JWS_KETA_GENRYO;

		try{

			//指定会社取得
			int selKaisha = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getIntKaishacd();

			//会社データ全件取得
			KaishaData kaishaData = DataCtrl.getInstance().getKaishaData();

			for ( int i=0; i<kaishaData.getArtKaishaCd().size(); i++ ) {

				//会社コード取得
				int kaishaCd = Integer.parseInt(kaishaData.getArtKaishaCd().get(i).toString());

				//原料桁
				String keta_genryo = kaishaData.getAryKaishaGenryo().get(i).toString();

				//選択会社コードの検出
				if ( kaishaCd == selKaisha ) {

					//桁数取得
					keta = keta_genryo;

				}
			}

		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}

		return Integer.parseInt(keta);

	}

	/************************************************************************************
	 *
	 *   配合明細テーブル　原料エディタ＆レンダラ行移動
	 *    : 配合明細テーブルへ原料行のセルエディタ＆レンダラの移動を行う
	 *   @author TT nishigawa
	 *   @param row_moto : 移動元　行番号
	 *   @param row_saki : 移動先　行番号
	 *
	 ************************************************************************************/
	public void moveHaigoMeisaiRowER(int row_moto,int row_saki){
		try{
			//-------------------- 入替元エディタ＆レンダラ取得  ----------------------------------
			//1列目
			MiddleCellEditor mc_0_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,0);
			TableCellEditor dc_0_moto = mc_0_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_0_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 0);
			TableCellRenderer cc_0_moto = mr_0_moto.getTableCellRenderer(row_moto);
			mc_0_moto.removeEditorAt(row_moto); //中間エディタより削除
			mr_0_moto.remove(row_moto); 		//中間レンダラより削除
			//2列目
			MiddleCellEditor mc_1_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,1);
			TableCellEditor dc_1_moto = mc_1_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_1_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 1);
			TableCellRenderer cc_1_moto = mr_1_moto.getTableCellRenderer(row_moto);
			mc_1_moto.removeEditorAt(row_moto); //中間エディタより削除
			mr_1_moto.remove(row_moto); 		//中間レンダラより削除
			//3列目
			MiddleCellEditor mc_2_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,2);
			TableCellEditor dc_2_moto = mc_2_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_2_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 2);
			TableCellRenderer cc_2_moto = mr_2_moto.getTableCellRenderer(row_moto);
			mc_2_moto.removeEditorAt(row_moto); //中間エディタより削除
			mr_2_moto.remove(row_moto); 		//中間レンダラより削除
			//4列目
			MiddleCellEditor mc_3_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,3);
			TableCellEditor dc_3_moto = mc_3_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_3_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 3);
			TableCellRenderer cc_3_moto = mr_3_moto.getTableCellRenderer(row_moto);
			mc_3_moto.removeEditorAt(row_moto); //中間エディタより削除
			mr_3_moto.remove(row_moto); 		//中間レンダラより削除
			//5列目
			MiddleCellEditor mc_4_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,4);
			TableCellEditor dc_4_moto = mc_4_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_4_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 4);
			TableCellRenderer cc_4_moto = mr_4_moto.getTableCellRenderer(row_moto);
			mc_4_moto.removeEditorAt(row_moto); //中間エディタより削除
			mr_4_moto.remove(row_moto); 		//中間レンダラより削除
			//6列目
			MiddleCellEditor mc_5_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,5);
			TableCellEditor dc_5_moto = mc_5_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_5_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 5);
			TableCellRenderer cc_5_moto = mr_5_moto.getTableCellRenderer(row_moto);
			mc_5_moto.removeEditorAt(row_moto); //中間エディタより削除
			mr_5_moto.remove(row_moto); 		//中間レンダラより削除
			//7列目
			MiddleCellEditor mc_6_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,6);
			TableCellEditor dc_6_moto = mc_6_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_6_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 6);
			TableCellRenderer cc_6_moto = mr_6_moto.getTableCellRenderer(row_moto);
			mc_6_moto.removeEditorAt(row_moto); //中間エディタより削除
			mr_6_moto.remove(row_moto); 		//中間レンダラより削除
			//8列目
			MiddleCellEditor mc_7_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,7);
			TableCellEditor dc_7_moto = mc_7_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_7_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 7);
			TableCellRenderer cc_7_moto = mr_7_moto.getTableCellRenderer(row_moto);
			mc_7_moto.removeEditorAt(row_moto); //中間エディタより削除
			mr_7_moto.remove(row_moto); 		//中間レンダラより削除

			//------------------------- エディタ＆レンダラ移動  ----------------------------------
			//1列目
			mc_0_moto.addEditorAt(row_saki, dc_0_moto);
			mr_0_moto.add(row_saki, cc_0_moto);
			//2列目
			mc_1_moto.addEditorAt(row_saki, dc_1_moto);
			mr_1_moto.add(row_saki, cc_1_moto);
			//3列目
			mc_2_moto.addEditorAt(row_saki, dc_2_moto);
			mr_2_moto.add(row_saki, cc_2_moto);
			//4列目
			mc_3_moto.addEditorAt(row_saki, dc_3_moto);
			mr_3_moto.add(row_saki, cc_3_moto);
			//5列目
			mc_4_moto.addEditorAt(row_saki, dc_4_moto);
			mr_4_moto.add(row_saki, cc_4_moto);
			//6列目
			mc_5_moto.addEditorAt(row_saki, dc_5_moto);
			mr_5_moto.add(row_saki, cc_5_moto);
			//7列目
			mc_6_moto.addEditorAt(row_saki, dc_6_moto);
			mr_6_moto.add(row_saki, cc_6_moto);
			//8列目
			mc_7_moto.addEditorAt(row_saki, dc_7_moto);
			mr_7_moto.add(row_saki, cc_7_moto);
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   配合明細テーブル　原料エディタ＆レンダラ行入替
	 *    : 配合明細テーブルへ原料行のセルエディタ＆レンダラを入替する
	 *   @author TT nishigawa
	 *   @param row_moto : 入替元　行番号
	 *   @param row_saki : 入替先　行番号
	 *
	 ************************************************************************************/
	public void changeHaigoMeisaiRowER(int row_moto,int row_saki){
		try{
			//-------------------- 入替元エディタ＆レンダラ取得  ----------------------------------
			//1列目
			MiddleCellEditor mc_0_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,0);
			TableCellEditor dc_0_moto = mc_0_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_0_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 0);
			TableCellRenderer cc_0_moto = mr_0_moto.getTableCellRenderer(row_moto);
			//2列目
			MiddleCellEditor mc_1_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,1);
			TableCellEditor dc_1_moto = mc_1_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_1_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 1);
			TableCellRenderer cc_1_moto = mr_1_moto.getTableCellRenderer(row_moto);
			//3列目
			MiddleCellEditor mc_2_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,2);
			TableCellEditor dc_2_moto = mc_2_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_2_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 2);
			TableCellRenderer cc_2_moto = mr_2_moto.getTableCellRenderer(row_moto);
			//4列目
			MiddleCellEditor mc_3_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,3);
			TableCellEditor dc_3_moto = mc_3_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_3_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 3);
			TableCellRenderer cc_3_moto = mr_3_moto.getTableCellRenderer(row_moto);
			//5列目
			MiddleCellEditor mc_4_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,4);
			TableCellEditor dc_4_moto = mc_4_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_4_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 4);
			TableCellRenderer cc_4_moto = mr_4_moto.getTableCellRenderer(row_moto);
			//6列目
			MiddleCellEditor mc_5_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,5);
			TableCellEditor dc_5_moto = mc_5_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_5_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 5);
			TableCellRenderer cc_5_moto = mr_5_moto.getTableCellRenderer(row_moto);
			//7列目
			MiddleCellEditor mc_6_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,6);
			TableCellEditor dc_6_moto = mc_6_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_6_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 6);
			TableCellRenderer cc_6_moto = mr_6_moto.getTableCellRenderer(row_moto);
			//8列目
			MiddleCellEditor mc_7_moto = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_moto,7);
			TableCellEditor dc_7_moto = mc_7_moto.getTableCellEditor(row_moto);
			MiddleCellRenderer mr_7_moto = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_moto, 7);
			TableCellRenderer cc_7_moto = mr_7_moto.getTableCellRenderer(row_moto);

			//-------------------- 入替先エディタ＆レンダラ取得  -----------------------------------
			//1列目
			MiddleCellEditor mc_0_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,0);
			TableCellEditor dc_0_saki = mc_0_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_0_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 0);
			TableCellRenderer cc_0_saki = mr_0_moto.getTableCellRenderer(row_saki);
			//2列目
			MiddleCellEditor mc_1_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,1);
			TableCellEditor dc_1_saki = mc_1_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_1_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 1);
			TableCellRenderer cc_1_saki = mr_1_moto.getTableCellRenderer(row_saki);
			//3列目
			MiddleCellEditor mc_2_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,2);
			TableCellEditor dc_2_saki = mc_2_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_2_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 2);
			TableCellRenderer cc_2_saki = mr_2_moto.getTableCellRenderer(row_saki);
			//4列目
			MiddleCellEditor mc_3_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,3);
			TableCellEditor dc_3_saki = mc_3_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_3_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 3);
			TableCellRenderer cc_3_saki = mr_3_moto.getTableCellRenderer(row_saki);
			//5列目
			MiddleCellEditor mc_4_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,4);
			TableCellEditor dc_4_saki = mc_4_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_4_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 4);
			TableCellRenderer cc_4_saki = mr_4_moto.getTableCellRenderer(row_saki);
			//6列目
			MiddleCellEditor mc_5_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,5);
			TableCellEditor dc_5_saki = mc_5_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_5_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 5);
			TableCellRenderer cc_5_saki = mr_5_moto.getTableCellRenderer(row_saki);
			//7列目
			MiddleCellEditor mc_6_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,6);
			TableCellEditor dc_6_saki = mc_6_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_6_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 6);
			TableCellRenderer cc_6_saki = mr_6_moto.getTableCellRenderer(row_saki);
			//8列目
			MiddleCellEditor mc_7_saki = (MiddleCellEditor)HaigoMeisai.getCellEditor(row_saki,7);
			TableCellEditor dc_7_saki = mc_7_saki.getTableCellEditor(row_saki);
			MiddleCellRenderer mr_7_saki = (MiddleCellRenderer)HaigoMeisai.getCellRenderer(row_saki, 7);
			TableCellRenderer cc_7_saki = mr_7_moto.getTableCellRenderer(row_saki);

			//----------------------- エディタ＆レンダラ入替  --------------------------------------
			//1列目
			HaigoMeisaiCellEditor0.setEditorAt(row_saki, dc_0_moto);
			HaigoMeisaiCellEditor0.setEditorAt(row_moto, dc_0_saki);
			HaigoMeisaiCellRenderer0.set(row_saki, cc_0_moto);
			HaigoMeisaiCellRenderer0.set(row_moto, cc_0_saki);
			//2列目
			HaigoMeisaiCellEditor1.setEditorAt(row_saki, dc_1_moto);
			HaigoMeisaiCellEditor1.setEditorAt(row_moto, dc_1_saki);
			HaigoMeisaiCellRenderer1.set(row_saki, cc_1_moto);
			HaigoMeisaiCellRenderer1.set(row_moto, cc_1_saki);
			//3列目
			HaigoMeisaiCellEditor2.setEditorAt(row_saki, dc_2_moto);
			HaigoMeisaiCellEditor2.setEditorAt(row_moto, dc_2_saki);
			HaigoMeisaiCellRenderer2.set(row_saki, cc_2_moto);
			HaigoMeisaiCellRenderer2.set(row_moto, cc_2_saki);
			//4列目
			HaigoMeisaiCellEditor3.setEditorAt(row_saki, dc_3_moto);
			HaigoMeisaiCellEditor3.setEditorAt(row_moto, dc_3_saki);
			HaigoMeisaiCellRenderer3.set(row_saki, cc_3_moto);
			HaigoMeisaiCellRenderer3.set(row_moto, cc_3_saki);
			//5列目
			HaigoMeisaiCellEditor4.setEditorAt(row_saki, dc_4_moto);
			HaigoMeisaiCellEditor4.setEditorAt(row_moto, dc_4_saki);
			HaigoMeisaiCellRenderer4.set(row_saki, cc_4_moto);
			HaigoMeisaiCellRenderer4.set(row_moto, cc_4_saki);
			//6列目
			HaigoMeisaiCellEditor5.setEditorAt(row_saki, dc_5_moto);
			HaigoMeisaiCellEditor5.setEditorAt(row_moto, dc_5_saki);
			HaigoMeisaiCellRenderer5.set(row_saki, cc_5_moto);
			HaigoMeisaiCellRenderer5.set(row_moto, cc_5_saki);
			//7列目
			HaigoMeisaiCellEditor6.setEditorAt(row_saki, dc_6_moto);
			HaigoMeisaiCellEditor6.setEditorAt(row_moto, dc_6_saki);
			HaigoMeisaiCellRenderer6.set(row_saki, cc_6_moto);
			HaigoMeisaiCellRenderer6.set(row_moto, cc_6_saki);
			//8列目
			HaigoMeisaiCellEditor7.setEditorAt(row_saki, dc_7_moto);
			HaigoMeisaiCellEditor7.setEditorAt(row_moto, dc_7_saki);
			HaigoMeisaiCellRenderer7.set(row_saki, cc_7_moto);
			HaigoMeisaiCellRenderer7.set(row_moto, cc_7_saki);
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   配合明細テーブル　原料エディタ＆レンダラ行削除
	 *    : 配合明細テーブルへ原料行のセルエディタ＆レンダラを削除する
	 *   @author TT nishigawa
	 *   @param row : 削除行
	 *
	 ************************************************************************************/
	public void delHaigoMeisaiRowER(int row){
		try{
			//--------------- 削除行エディター＆レンダラー削除 -------------------------
			//1列目
			HaigoMeisaiCellEditor0.removeEditorAt(row);
			HaigoMeisaiCellRenderer0.remove(row);
			//2列目
			HaigoMeisaiCellEditor1.removeEditorAt(row);
			HaigoMeisaiCellRenderer1.remove(row);
			//3列目
			HaigoMeisaiCellEditor2.removeEditorAt(row);
			HaigoMeisaiCellRenderer2.remove(row);
			//4列目
			HaigoMeisaiCellEditor3.removeEditorAt(row);
			HaigoMeisaiCellRenderer3.remove(row);
			//5列目
			HaigoMeisaiCellEditor4.removeEditorAt(row);
			HaigoMeisaiCellRenderer4.remove(row);
			//6列目
			HaigoMeisaiCellEditor5.removeEditorAt(row);
			HaigoMeisaiCellRenderer5.remove(row);
			//7列目
			HaigoMeisaiCellEditor6.removeEditorAt(row);
			HaigoMeisaiCellRenderer6.remove(row);
			//8列目
			HaigoMeisaiCellEditor7.removeEditorAt(row);
			HaigoMeisaiCellRenderer7.remove(row);
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   配合明細テーブル　エディタ＆レンダラ設定
	 *    : 配合明細テーブルへセルエディタ＆レンダラを設定する
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void setHaigoMeisaiER(){
		try{
			//------------------ Jtableへ中間エディター＆レンダラーを登録  ----------------------
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)HaigoMeisai.getColumnModel();
			TableColumn column = null;

			//工程選択
			column = HaigoMeisai.getColumnModel().getColumn(0);
			column.setCellEditor(HaigoMeisaiCellEditor0);
			column.setCellRenderer(HaigoMeisaiCellRenderer0);

			//工程順
			column = HaigoMeisai.getColumnModel().getColumn(1);
			column.setCellEditor(HaigoMeisaiCellEditor1);
			column.setCellRenderer(HaigoMeisaiCellRenderer1);

			//原料選択
			column = HaigoMeisai.getColumnModel().getColumn(2);
			column.setCellEditor(HaigoMeisaiCellEditor2);
			column.setCellRenderer(HaigoMeisaiCellRenderer2);

			//原料CD
			column = HaigoMeisai.getColumnModel().getColumn(3);
			column.setCellEditor(HaigoMeisaiCellEditor3);
			column.setCellRenderer(HaigoMeisaiCellRenderer3);

			//原料名
			column = HaigoMeisai.getColumnModel().getColumn(4);
			column.setCellEditor(HaigoMeisaiCellEditor4);
			column.setCellRenderer(HaigoMeisaiCellRenderer4);

			//単価
			column = HaigoMeisai.getColumnModel().getColumn(5);
			column.setCellEditor(HaigoMeisaiCellEditor5);
			column.setCellRenderer(HaigoMeisaiCellRenderer5);

			//歩留
			column = HaigoMeisai.getColumnModel().getColumn(6);
			column.setCellEditor(HaigoMeisaiCellEditor6);
			column.setCellRenderer(HaigoMeisaiCellRenderer6);

			//油含有率
			column = HaigoMeisai.getColumnModel().getColumn(7);
			column.setCellEditor(HaigoMeisaiCellEditor7);
			column.setCellRenderer(HaigoMeisaiCellRenderer7);

		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　量エディタ＆レンダラ色設定
	 *    : 試作明細テーブルへ量行のセルエディタ＆レンダラを追加する
	 *   @author TT nishigawa
	 *   @param row : 追加行
	 *   @param flg : 原料行or空行
	 *   @param pk_KoteiCd  : 工程CD
	 *   @param pk_KoteiSeq : 工程SEQ
	 *
	 ************************************************************************************/
	public void addListShisakuRowER_color(int row, int col, int color){
		try{
				//レンダラ取得
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(col);
				TextFieldCellRenderer dr = (TextFieldCellRenderer)MiddleCellRenderer.getTableCellRenderer(row);

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34

				//色変更
				//dr.setColor(new Color(color));

				//試作SEQ取得
				int intShisakuSeq = 0;
				MiddleCellEditor deleteMc = (MiddleCellEditor)ListHeader.getCellEditor(0, col);
				DefaultCellEditor deleteDc = (DefaultCellEditor)deleteMc.getTableCellEditor(0);
				CheckboxBase CheckboxBase = (CheckboxBase)deleteDc.getComponent();
				intShisakuSeq = Integer.parseInt(CheckboxBase.getPk1());

				//列キー項目取得
				boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);

				//編集可能の場合：既存処理
				if(chk){
					//色変更
					dr.setColor(new Color(color));
				}
				//編集不可の場合：処理無し
				else{

				}
//add end   -------------------------------------------------------------------------------


		}catch(Exception e){
			e.printStackTrace();

		}finally{
			//テスト表示
			//System.out.println(row+" , "+col+" , "+color);
		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　量エディタ＆レンダラ行追加
	 *    : 試作明細テーブルへ量行のセルエディタ＆レンダラを追加する
	 *   @author TT nishigawa
	 *   @param row : 追加行
	 *   @param flg : 原料行or空行
	 *   @param pk_KoteiCd  : 工程CD
	 *   @param pk_KoteiSeq : 工程SEQ
	 *
	 ************************************************************************************/
	public void addListShisakuRowER(int col, int row, int flg){
		try{
			//量エディタ＆レンダラ行追加
//			for(int i=0; i<ListMeisai.getColumnCount(); i++){

				//エディタ＆レンダラ配列より中間エディター＆レンダラー取得
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryListShisakuCellEditor.get(col);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(col);

				//コンポーネント生成
				TextboxBase comp;
				if(flg == 0){

					//TextField（数値）コンポーネント生成
					comp = new NumelicTextbox();

					//モード編集
					if(!DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0031, DataCtrl.getInstance().getParamData().getStrMode())){
						((NumelicTextbox)comp).setEditable(false);
					}else{
						//2012/10/22 QP@20505_No.1 TT S.SHIMA MOD Start
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
						//if(row < ListMeisai.getRowCount()-8-1){
						//if(row < ListMeisai.getRowCount()-9-1){
						if(row < ListMeisai.getRowCount()-10-1){
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
						//2012/10/22 QP@20505_No.1 TT S.SHIMA MOD End
							comp.addMouseListener(new colorChange(ListMeisai,1));
						}
					}

				}else{

					//TextField（入力不可）コンポーネント生成
					comp = new NumelicTextbox();
					comp.setEditable(false);
					comp.setBackground(Color.WHITE);
				}

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34

				//色変更
				//rendComp.setColor(new Color(-1));

				//試作SEQ取得
				int intShisakuSeq = 0;
				MiddleCellEditor deleteMc = (MiddleCellEditor)ListHeader.getCellEditor(0, col);
				DefaultCellEditor deleteDc = (DefaultCellEditor)deleteMc.getTableCellEditor(0);
				CheckboxBase CheckboxBase = (CheckboxBase)deleteDc.getComponent();
				intShisakuSeq = Integer.parseInt(CheckboxBase.getPk1());

				//列キー項目取得
				boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);

				//編集可能の場合：既存処理
				if(chk){
					comp.setBackground(Color.WHITE);
				}
				//編集不可の場合：コンポーネントの操作不可
				else{
					comp.setEnabled(false);
					comp.setBackground(JwsConstManager.JWS_DISABLE_COLOR);
				}
//add end   -------------------------------------------------------------------------------

				//エディター＆レンダラー生成
				TextFieldCellEditor editComp = new TextFieldCellEditor(comp);
				TextFieldCellRenderer rendComp = new TextFieldCellRenderer(comp);

				//中間エディター＆レンダラーへ登録
				MiddleCellEditor.addEditorAt(row, editComp);
				MiddleCellRenderer.add(row, rendComp);
				//エディタ＆レンダラ配列へ設定
				aryListShisakuCellEditor.set(col, MiddleCellEditor);
				aryListShisakuCellRenderer.set(col, MiddleCellRenderer);
//			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　エディタ＆レンダラ列削除
	 *    : 試作明細テーブルのセルエディタ＆レンダラ列を削除する
	 *   @author TT nishigawa
	 *   @param  col : 削除列
	 *
	 ************************************************************************************/
	public void removeListShisakuColER(int col){
		try{
			//エディタ＆レンダラ列削除
			aryListShisakuCellEditor.remove(col);
			aryListShisakuCellRenderer.remove(col);
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　エディタ＆レンダラ列入替
	 *    : 試作明細テーブルのセルエディタ＆レンダラ列を入替する
	 *   @author TT nishigawa
	 *   @param  col_moto : 入替元列
	 *   @param  col_saki : 入替先列
	 *
	 ************************************************************************************/
	public void changeListShisakuColER(int col_moto, int col_saki){
		try{
			//入替元エディタ＆レンダラ列取得
			MiddleCellEditor mc_moto = (MiddleCellEditor)aryListShisakuCellEditor.get(col_moto);
			MiddleCellRenderer mr_moto = (MiddleCellRenderer)aryListShisakuCellRenderer.get(col_moto);

			//入替先エディタ＆レンダラ列取得
			MiddleCellEditor mc_saki = (MiddleCellEditor)aryListShisakuCellEditor.get(col_saki);
			MiddleCellRenderer mr_saki = (MiddleCellRenderer)aryListShisakuCellRenderer.get(col_saki);

			//エディタ列入替
			aryListShisakuCellEditor.set(col_moto, mc_saki);
			aryListShisakuCellEditor.set(col_saki, mc_moto);

			//レンダラ列入替
			aryListShisakuCellRenderer.set(col_moto, mr_saki);
			aryListShisakuCellRenderer.set(col_saki, mr_moto);

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　エディタ＆レンダラ列追加
	 *    : 試作明細テーブルへ列のセルエディタ＆レンダラを追加する
	 *   @author TT nishigawa
	 *   @param col : 追加列
	 *
	 ************************************************************************************/
	public void addListShisakuColER(int col){
		try{
			//中間エディター＆レンダラー生成
			MiddleCellEditor MiddleCellEditor = new MiddleCellEditor(ListMeisai);
			MiddleCellRenderer MiddleCellRenderer = new MiddleCellRenderer();

			//量エディタ＆レンダラ列追加
			for(int i=0; i<HaigoMeisai.getRowCount(); i++){

				//工程行かどうかの判断を行う
				int flg = 0;

				MiddleCellEditor chkKotei_mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 2);
				DefaultCellEditor chkKotei_tc = (DefaultCellEditor)chkKotei_mc.getTableCellEditor(i);

				if(((JComponent)chkKotei_tc.getComponent()) instanceof CheckboxBase){
					//原料行
					flg = 0;
				}else{
					// ADD start 20120914 QP@20505 No.1
					int kotei_Cnt = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
					// ADD end 20120914 QP@20505 No.1

					//工程行
					flg = 1;

					//合計仕上重量行の場合
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
					//if(i == HaigoMeisai.getRowCount() - 7){
					if(i == HaigoMeisai.getRowCount() - 8){
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
						flg = 0;
					}
					// ADD start 20120914 QP@20505 No.1
					//工程仕上重量行の場合
					else if(i < HaigoMeisai.getRowCount()-8 && i >= HaigoMeisai.getRowCount()-8-kotei_Cnt){
						flg = 0;
					}
					// ADD end 20120914 QP@20505 No.1
				}

				//コンポーネント生成
				TextboxBase comp;

				//原料行の場合
				if(flg == 0){

					//TextField（数値）コンポーネント生成
					comp = new NumelicTextbox();
					comp.addMouseListener(new colorChange(ListMeisai,1));

				}
				//工程行の場合
				else{

					//TextField（入力不可）コンポーネント生成
					comp = new NumelicTextbox();
					comp.setEditable(false);
					comp.setBackground(Color.WHITE);

				}
				//エディター＆レンダラー生成
				TextFieldCellEditor editComp = new TextFieldCellEditor(comp);
				TextFieldCellRenderer rendComp = new TextFieldCellRenderer(comp);
				//中間エディター＆レンダラーへ登録
				MiddleCellEditor.addEditorAt(i, editComp);
				MiddleCellRenderer.add(i, rendComp);
			}

			//エディタ＆レンダラ配列へ追加
			aryListShisakuCellEditor.add(col, MiddleCellEditor);
			aryListShisakuCellRenderer.add(col, MiddleCellRenderer);

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　量エディタ＆レンダラ行移動
	 *    : 試作明細テーブルへ量行のセルエディタ＆レンダラを移動する
	 *   @author TT nishigawa
	 *   @param row_moto : 移動元　行番号
	 *   @param row_saki : 移動先　行番号
	 *
	 ************************************************************************************/
	public void moveListShisakuRowER(int row_moto,int row_saki){
		//-------------------- エディタ＆レンダラ移動設定  ----------------------------------
		try{
			//列数ループ
			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				//エディタ＆レンダラ配列より中間エディター＆レンダラー取得
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryListShisakuCellEditor.get(i);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(i);

				//移動元エディタ＆レンダラ取得
				MiddleCellEditor mc_moto = (MiddleCellEditor)ListMeisai.getCellEditor(row_moto,i);
				TableCellEditor dc_moto = mc_moto.getTableCellEditor(row_moto);
				MiddleCellRenderer mr_moto = (MiddleCellRenderer)ListMeisai.getCellRenderer(row_moto, i);
				TableCellRenderer cc_moto = mr_moto.getTableCellRenderer(row_moto);

				//中間エディター＆レンダラーより移動元を削除
				MiddleCellEditor.removeEditorAt(row_moto);
				MiddleCellRenderer.remove(row_moto);

				//中間エディター＆レンダラーへ登録
				MiddleCellEditor.addEditorAt(row_saki, dc_moto);
				MiddleCellRenderer.add(row_saki, cc_moto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　量エディタ＆レンダラ行入替
	 *    : 試作明細テーブルへ量行のセルエディタ＆レンダラを入替する
	 *   @author TT nishigawa
	 *   @param row_moto : 入替元　行番号
	 *   @param row_saki : 入替先　行番号
	 *
	 ************************************************************************************/
	public void changeListShisakuRowER(int row_moto,int row_saki){
		//-------------------- エディタ＆レンダラ入替設定  ----------------------------------
		try{
			//列数ループ
			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				//エディタ＆レンダラ配列より中間エディター＆レンダラー取得
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryListShisakuCellEditor.get(i);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(i);

				//入替元エディタ＆レンダラ取得
				MiddleCellEditor mc_moto = (MiddleCellEditor)ListMeisai.getCellEditor(row_moto,i);
				TableCellEditor dc_moto = mc_moto.getTableCellEditor(row_moto);
				MiddleCellRenderer mr_moto = (MiddleCellRenderer)ListMeisai.getCellRenderer(row_moto, i);
				TableCellRenderer cc_moto = mr_moto.getTableCellRenderer(row_moto);

				//入替先エディタ＆レンダラ取得
				MiddleCellEditor mc_saki = (MiddleCellEditor)ListMeisai.getCellEditor(row_saki,i);
				TableCellEditor dc_saki = mc_saki.getTableCellEditor(row_saki);
				MiddleCellRenderer mr_saki = (MiddleCellRenderer)ListMeisai.getCellRenderer(row_saki, i);
				TableCellRenderer cc_saki = mr_saki.getTableCellRenderer(row_saki);

				//中間エディター＆レンダラーへ登録
				MiddleCellEditor.setEditorAt(row_moto, dc_saki);
				MiddleCellEditor.setEditorAt(row_saki, dc_moto);
				MiddleCellRenderer.set(row_moto, cc_saki);
				MiddleCellRenderer.set(row_saki, cc_moto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　量エディタ＆レンダラ行削除
	 *    : 試作明細テーブル行のセルエディタ＆レンダラを削除する
	 *   @author TT nishigawa
	 *   @param row : 移動元　行番号
	 *
	 ************************************************************************************/
	public void delListShisakuRowER(int row){
		//-------------------- エディタ＆レンダラ削除設定  ----------------------------------
		try{
			//列数ループ
			for(int i=0; i<ListMeisai.getColumnCount(); i++){
				//エディタ＆レンダラ配列より中間エディター＆レンダラー取得
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryListShisakuCellEditor.get(i);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(i);

				//エディタ＆レンダラ削除
				MiddleCellEditor.removeEditorAt(row);
				MiddleCellRenderer.remove(row);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　量エディタ＆レンダラ列追加
	 *    : 試作明細テーブルへ量列のセルエディタ＆レンダラを追加する
	 *   @author TT nishigawa
	 *   @param Column : 追加列
	 *
	 ************************************************************************************/
	public void addListShisakuColumnER(int Column){
		try{
			//エディタ＆レンダラ配列へ追加
			aryListShisakuCellEditor.add(Column, new MiddleCellEditor(ListMeisai));
			aryListShisakuCellRenderer.add(Column, new MiddleCellRenderer());
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　エディタ＆レンダラ設定
	 *    : 試作明細テーブルへセルエディタ＆レンダラを設定する
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void setListShisakuER(){
		try{
			//テーブルカラム取得
			DefaultTableColumnModel columnModel = (DefaultTableColumnModel)ListMeisai.getColumnModel();
			TableColumn column = null;
			for(int i = 0; i<ListMeisai.getColumnCount(); i++){
				//エディタ＆レンダラ配列より取得
				MiddleCellEditor MiddleCellEditor = (MiddleCellEditor)aryListShisakuCellEditor.get(i);
				MiddleCellRenderer MiddleCellRenderer = (MiddleCellRenderer)aryListShisakuCellRenderer.get(i);
				//テーブルカラムへ設定
				column = ListMeisai.getColumnModel().getColumn(i);
				column.setCellEditor(MiddleCellEditor);
				column.setCellRenderer(MiddleCellRenderer);
			}
		}catch(Exception e){
			e.printStackTrace();

		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   原料チェックイベントクラス : 原料チェックボックス押下時の処理（原料選択）
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class CheckGenryo implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				String event_name = e.getActionCommand();
				//原料チェックボックス押下
				if ( event_name == "genryoCheck") {
					//コンポーネント取得
					CheckboxBase CheckboxBase = (CheckboxBase)e.getSource();
					String pk_KoteiCd = CheckboxBase.getPk1();
					String pk_KoteiSeq = CheckboxBase.getPk2();

					//テスト表示
//					System.out.println("工程CD：" + pk_KoteiCd);
//					System.out.println("工程SEQ：" + pk_KoteiSeq);

					//選択原料データ追加
					DataCtrl.getInstance().getTrialTblData().SelectHaigoGenryo(
							Integer.parseInt(pk_KoteiCd), Integer.parseInt(pk_KoteiSeq));
				}
			}catch(ExceptionBase eb){


			}catch(Exception ex){
				ex.printStackTrace();

			}finally{

			}
		}
	}

	/************************************************************************************
	 *
	 *   工程チェックイベントクラス : 工程チェックボックス押下時の処理（工程選択）
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class CheckKotei implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				String event_name = e.getActionCommand();
				//工程チェックボックス押下
				if ( event_name == "koteiCheck") {

					//コンポーネント取得
					CheckboxBase CheckboxBase = (CheckboxBase)e.getSource();
					String pk_KoteiCd = CheckboxBase.getPk1();
					String pk_KoteiSeq = CheckboxBase.getPk2();
					String pk_KoteiNo = CheckboxBase.getPk3();
					String pk_KoteiNm = CheckboxBase.getPk4();
					String pk_KoteiZoku = CheckboxBase.getPk5();

					//テスト表示
//					System.out.println("工程CD：" + pk_KoteiCd);
//					System.out.println("工程SEQ：" + pk_KoteiSeq);
//					System.out.println("工程順：" + pk_KoteiNo);
//					System.out.println("工程名：" + pk_KoteiNm);
//					System.out.println("工程属性：" + pk_KoteiZoku);


					//チェックボックスが選択された場合
					if(CheckboxBase.isSelected()){

						//他チェックボックスを解除
						for(int i=0; i<HaigoMeisai.getRowCount(); i++){

							//コンポーネント取得
							MiddleCellEditor mc = (MiddleCellEditor)HaigoMeisai.getCellEditor(i, 0);
							DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(i);

							//工程チェックボックスの場合
							if(tc.getComponent() instanceof CheckboxBase){

								//チェックボックスコンポーネント取得
								CheckboxBase getCheck = (CheckboxBase)tc.getComponent();

								//工程CD取得
								String get_KoteiCd = getCheck.getPk1();

								//チェックされたものと同一の工程CDの場合
								if(pk_KoteiCd.equals(get_KoteiCd)){

								}
								//チェックされたものと異なる試作SEQの場合
								else{

									//チェックを解除
									HaigoMeisai.setValueAt(null, i, 0);

								}

							}
						}

					//チェックボックスが選択解除された場合
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
	 *   試作選択チェックイベントクラス : 試作選択チェックボックス押下時の処理
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class CheckShisaku implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				String event_name = e.getActionCommand();
				//工程チェックボックス押下
				if ( event_name == "shisakuCheck") {

					//コンポーネント取得
					CheckboxBase CheckboxBase = (CheckboxBase)e.getSource();

					//キー項目取得
					String tyuuiNo = (String)ListHeader.getValueAt(1, ListHeader.getSelectedColumn());
					String pk_ShisakuSeq = CheckboxBase.getPk1();

					//チェックボックスが選択された場合
					if(CheckboxBase.isSelected()){

						//他チェックボックスを解除
						for(int i=0; i<ListHeader.getColumnCount(); i++){

							//コンポーネント取得
							MiddleCellEditor mc = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
							DefaultCellEditor tc = (DefaultCellEditor)mc.getTableCellEditor(0);
							CheckboxBase getCheck = (CheckboxBase)tc.getComponent();

							//試作SEQ取得
							String selectSeq = getCheck.getPk1();

							//チェックされたものと同一の試作SEQの場合
							if(pk_ShisakuSeq.equals(selectSeq)){

							}
							//チェックされたものと異なる試作SEQの場合
							else{

								//チェックを解除
								ListHeader.setValueAt(null, 0, i);

							}

						}

					//チェックボックスが選択解除された場合
					}else{


					}

					//-------------------------- 製造工程表示処理  ----------------------------
					//製造工程パネル取得
					ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();
					CheckboxBase seizoCb = pb.getCheckbox();


					//チェックボックスが選択された場合
					if(CheckboxBase.isSelected()){

						//試作SEQセット
						pb.setShisakuSeq(Integer.parseInt(pk_ShisakuSeq));

						//注意事項Noがnullでない場合
						if(tyuuiNo != null){

							//注意事項Noが選択されている場合
							if(!tyuuiNo.equals("")){
								//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
								pb.getLabel()[1].setText("工程版：" + tyuuiNo);
								//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------
								pb.setTyuuiNo(Integer.parseInt(tyuuiNo));

							//注意事項Noが選択されていない場合
							}else{

								//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
								//画面表示
								pb.getLabel()[1].setText("<html>工程版：<font color=\"red\"><b>未選択です</font>");
								//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------
								pb.setTyuuiNo(0);

							}

						//注意事項Noがnullの場合
						}else{

							pb.setTyuuiNo(0);

						}

					//チェックボックスが選択解除された場合
					}else{

						//試作SEQ、注意事項Noセット
						pb.setShisakuSeq(0);
						pb.setTyuuiNo(0);

						//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
						//画面表示
						pb.getLabel()[1].setText("<html>工程版：<font color=\"red\"><b>未選択です</font>");
						//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------
					}


					//製造工程の選択コンボボックスの選択値を取得
					int selectCombo = pb.getCombo().getSelectedIndex();

					//製造工程/注意事項の場合
					if(selectCombo == 0){
						pb.dispSeizo();
					}

					//試作メモの場合
					if(selectCombo == 1){
						pb.dispMemo();
					}

					//製法Noの場合
					if(selectCombo == 2){
						pb.dispSeiho();
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
	 *   注意事項No選択イベントクラス : 注意事項No選択時の処理
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class selectTyuui implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				//--------------------- 試作SEQ&注意事項No取得 --------------------------
				ComboBase cb = (ComboBase)e.getSource();
				String strNo = (String)cb.getSelectedItem();
				int intSEQ = Integer.parseInt(cb.getPk1());

				//-------------------------- 選択状態チェック  -----------------------------
				//製造工程パネル取得
				ManufacturingPanel pb = DataCtrl.getInstance().getManufacturingSubDisp().getManufacturingPanel();

				//試作が選択状態にある場合に処理
				if(pb.getShisakuSeq() == intSEQ){

					//注意事項No設定
					if(strNo != null && strNo.length() > 0){
						//2011/04/19 QP@10181_No.31 TT T.Satoh Add Start -------------------------
						pb.getLabel()[1].setText("工程版：" + strNo);
						//2011/04/19 QP@10181_No.31 TT T.Satoh Add End ---------------------------
						pb.setTyuuiNo(Integer.parseInt(strNo));
					}else{
						//2011/05/27 QP@10181_No.31 TT T.Satoh Add Start -------------------------
						pb.getLabel()[1].setText("<html>工程版：<font color=\"red\"><b>未選択です</font>");
						//2011/05/27 QP@10181_No.31 TT T.Satoh Add End ---------------------------
						pb.setTyuuiNo(0);
					}

					//-------------------- 製造工程表示処理  ----------------------------
					//製造工程の選択コンボボックスの選択値を取得
					int selectCombo = pb.getCombo().getSelectedIndex();

					//製造工程/注意事項の場合
					if(selectCombo == 0){
						pb.dispSeizo();
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
	 *   試作列ヘッダーテーブル　フォーカス処理
	 *    : 試作列ヘッダーテーブルのフォーカス処理を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void ListHeaderFocusControl() throws ExceptionBase {
		ExceptionBase ex = new ExceptionBase();
		try {
			ListHeader.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {

					//最終行・最終列
					boolean isLastRowSelect = ( ListHeader.isRowSelected(ListHeader.getRowCount() - 1));
					boolean isLastColumnSelect = ( ListHeader.isColumnSelected(ListHeader.getColumnCount() - 1));
					int RowSelect = ListHeader.getSelectedRow();
					int ColumnSelect = ListHeader.getSelectedColumn();

					//下方向移動
					if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {			//ENTERキー
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {
							//最初行にフォーカス時
							if ( RowSelect == 0 && ColumnSelect > 0 ) {

								//選択元テーブルを未選択に設定
								TableCellEditor tce = ListHeader.getCellEditor();
								if(tce != null){
									ListHeader.getCellEditor().stopCellEditing();
								}
								ListHeader.clearSelection();

								//選択先テーブルへフォーカス
								int row = ListMeisai.getRowCount()-1;
								ListMeisai.requestFocus();
								ListMeisai.setRowSelectionInterval(row, row);
								ListMeisai.setColumnSelectionInterval(ColumnSelect-1, ColumnSelect-1);

								//配合行選択
								HaigoMeisai.setRowSelectionInterval(row, row);

								e.consume();
							}
							//最初行・列にフォーカス時
							else if(RowSelect == 0 && ColumnSelect == 0){

								//選択元テーブルを未選択に設定
								TableCellEditor tce = ListHeader.getCellEditor();
								if(tce != null){
									ListHeader.getCellEditor().stopCellEditing();
								}
								ListHeader.clearSelection();

								//選択先テーブルへフォーカス
								int row = HaigoMeisai.getRowCount()-1;
								int col = HaigoMeisai.getColumnCount()-1;
								HaigoMeisai.requestFocus();
								HaigoMeisai.setRowSelectionInterval(row, row);
								HaigoMeisai.setColumnSelectionInterval(col, col);

								e.consume();
							}
						}else{
							//最終行にフォーカス時
							if ( isLastRowSelect ) {

								//選択元テーブルを未選択に設定
								TableCellEditor tce = ListHeader.getCellEditor();
								if(tce != null){
									ListHeader.getCellEditor().stopCellEditing();
								}
								ListHeader.clearSelection();

								//選択先テーブルへフォーカス
								ListMeisai.requestFocus();
								ListMeisai.setRowSelectionInterval(0, 0);
								ListMeisai.setColumnSelectionInterval(ColumnSelect, ColumnSelect);

								//配合行選択
								HaigoMeisai.setRowSelectionInterval(0, 0);

								e.consume();
							}
						}
					}
					//下方向移動
					if ( e.getKeyCode() == KeyEvent.VK_DOWN ) {			//ENTERキー

						//最終行にフォーカス時
						if ( isLastRowSelect ) {

							//選択元テーブルを未選択に設定
							TableCellEditor tce = ListHeader.getCellEditor();
							if(tce != null){
								ListHeader.getCellEditor().stopCellEditing();
							}
							ListHeader.clearSelection();

							//選択先テーブルへフォーカス
							ListMeisai.requestFocus();
							ListMeisai.setRowSelectionInterval(0, 0);
							ListMeisai.setColumnSelectionInterval(ColumnSelect, ColumnSelect);

							//配合行選択
							HaigoMeisai.setRowSelectionInterval(0, 0);

							e.consume();
						}
					}

					//右方向移動
					else if ( e.getKeyCode() == KeyEvent.VK_TAB) {		//TABキー
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {

						}else{
							//最終列にフォーカス時
							if ( isLastRowSelect && isLastColumnSelect ) {
								//選択元テーブルを未選択に設定
								TableCellEditor tce = ListHeader.getCellEditor();
								if(tce != null){
									ListHeader.getCellEditor().stopCellEditing();
								}
								ListHeader.clearSelection();
								//選択先テーブルへフォーカス
								HaigoMeisai.requestFocus();
								HaigoMeisai.setRowSelectionInterval(0, 0);
								HaigoMeisai.setColumnSelectionInterval(0, 0);
								e.consume();
							}
						}
					}
				}
			});
		} catch ( Exception e ) {
			e.printStackTrace();
			//エラー設定
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作列ヘッダーのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori("Trial1Table:ListHeaderFocusControl");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   配合明細テーブル　フォーカス処理
	 *    : 配合明細テーブルのフォーカス処理を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void HaigoMeisaiFocusControl() throws ExceptionBase {
		ExceptionBase ex = new ExceptionBase();
		try {
			HaigoMeisai.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {

					//最終行・最終列
					boolean isLastRowSelect = ( HaigoMeisai.isRowSelected(HaigoMeisai.getRowCount() - 1));
					boolean isLastColumnSelect = ( HaigoMeisai.isColumnSelected(HaigoMeisai.getColumnCount() - 1));
					int RowSelect = HaigoMeisai.getSelectedRow();
					int ColumnSelect = HaigoMeisai.getSelectedColumn();
					//下方向移動
					if ( e.getKeyCode() == KeyEvent.VK_ENTER) {			//ENTERキー
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {
							//最終行にフォーカス時
							if ( RowSelect == 0 && ColumnSelect == 0 ) {
								e.consume();
							}
						}else{
							//最終行にフォーカス時
							if ( isLastRowSelect && isLastColumnSelect ) {
								//選択元テーブルを未選択に設定
								TableCellEditor tce = HaigoMeisai.getCellEditor();
								if(tce != null){
									HaigoMeisai.getCellEditor().stopCellEditing();
								}
								HaigoMeisai.clearSelection();

								//選択先テーブルへフォーカス
								ListHeader.requestFocus();
								ListHeader.setRowSelectionInterval(0, 0);
								ListHeader.setColumnSelectionInterval(0, 0);
								e.consume();
							}
						}
					}
					//右方向移動
					else if ( e.getKeyCode() == KeyEvent.VK_TAB ) {		//TABキー
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {
							//最初列にフォーカス時
							if ( ColumnSelect == 0 && RowSelect > 0 ) {
								//選択元テーブルを未選択に設定
								TableCellEditor tce = HaigoMeisai.getCellEditor();
								if(tce != null){
									HaigoMeisai.getCellEditor().stopCellEditing();
								}
								HaigoMeisai.clearSelection();
								//選択先テーブルへフォーカス
								int col = ListMeisai.getColumnCount()-1;
								ListMeisai.requestFocus();
								ListMeisai.setRowSelectionInterval(RowSelect-1, RowSelect-1);
								ListMeisai.setColumnSelectionInterval(col, col);
								e.consume();
							}
							//最初列・行にフォーカス時
							else if ( ColumnSelect == 0 && RowSelect == 0 ) {
								e.consume();
							}

						}else{
							//最終列にフォーカス時
							if ( isLastColumnSelect ) {
								//選択元テーブルを未選択に設定
								TableCellEditor tce = HaigoMeisai.getCellEditor();
								if(tce != null){
									HaigoMeisai.getCellEditor().stopCellEditing();
								}
								//HaigoMeisai.clearSelection();
								//選択先テーブルへフォーカス
								ListMeisai.requestFocus();
								ListMeisai.setRowSelectionInterval(RowSelect, RowSelect);
								ListMeisai.setColumnSelectionInterval(0, 0);
								e.consume();
							}
						}
					}
					//右方向移動
					else if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {

						//最終列にフォーカス時
						if ( isLastColumnSelect ) {
							//選択元テーブルを未選択に設定
							TableCellEditor tce = HaigoMeisai.getCellEditor();
							if(tce != null){
								HaigoMeisai.getCellEditor().stopCellEditing();
							}
							//HaigoMeisai.clearSelection();

							//選択先テーブルへフォーカス
							ListMeisai.requestFocus();
							ListMeisai.setRowSelectionInterval(RowSelect, RowSelect);
							ListMeisai.setColumnSelectionInterval(0, 0);
							e.consume();
						}
					}
				}
			});
		} catch ( Exception e ) {
			e.printStackTrace();
			//エラー設定
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("HaigoMeisaiのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori("Trial1Table:HaigoMeisaiFocusControl");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   試作明細テーブル　フォーカス処理
	 *    : 試作明細テーブルのフォーカス処理を行う
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void ListMeisaiFocusControl() throws ExceptionBase {
		ExceptionBase ex = new ExceptionBase();
		try {
			ListMeisai.addKeyListener(new KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {

					//最終行・最終列
					boolean isLastRowSelect = ( ListMeisai.isRowSelected(ListMeisai.getRowCount() - 1));
					boolean isLastColumnSelect = ( ListMeisai.isColumnSelected(ListMeisai.getColumnCount() - 1));
					int RowSelect = ListMeisai.getSelectedRow();
					int ColumnSelect = ListMeisai.getSelectedColumn();

					//下方向移動(Enter)
					if ( e.getKeyCode() == KeyEvent.VK_ENTER) {
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {
							//最初行にフォーカス時
							if ( RowSelect == 0 ) {

								//配合行選択解除
								HaigoMeisai.clearSelection();

								//選択元テーブルを未選択に設定
								TableCellEditor tce = ListMeisai.getCellEditor();
								if(tce != null){
									ListMeisai.getCellEditor().stopCellEditing();
								}
								ListMeisai.clearSelection();

								//選択先テーブルへフォーカス
								int row = ListHeader.getRowCount()-1;
								ListHeader.requestFocus();
								ListHeader.setRowSelectionInterval(row, row);
								ListHeader.setColumnSelectionInterval(ColumnSelect, ColumnSelect);
								e.consume();
							}
						}else{

							//最終行にフォーカス時
							if ( isLastRowSelect && !isLastColumnSelect) {

								//配合行選択解除
								HaigoMeisai.clearSelection();

								//選択元テーブルを未選択に設定
								TableCellEditor tce = ListMeisai.getCellEditor();
								if(tce != null){
									ListMeisai.getCellEditor().stopCellEditing();
								}
								ListMeisai.clearSelection();

								//選択先テーブルへフォーカス
								ListHeader.requestFocus();
								ListHeader.setRowSelectionInterval(0, 0);
								ListHeader.setColumnSelectionInterval(ColumnSelect+1, ColumnSelect+1);
								e.consume();
							}
							//最終行・列にフォーカス時
							else if ( isLastRowSelect && isLastColumnSelect ) {
								e.consume();
							}
						}
					}

					//上方向移動
					if ( e.getKeyCode() == KeyEvent.VK_UP) {

						//最初行にフォーカス時
						if ( RowSelect == 0 ) {

							//配合行選択解除
							HaigoMeisai.clearSelection();

							//選択元テーブルを未選択に設定
							TableCellEditor tce = ListMeisai.getCellEditor();
							if(tce != null){
								ListMeisai.getCellEditor().stopCellEditing();
							}
							ListMeisai.clearSelection();

							//選択先テーブルへフォーカス
							int row = ListHeader.getRowCount()-1;
							ListHeader.requestFocus();
							ListHeader.setRowSelectionInterval(row, row);
							ListHeader.setColumnSelectionInterval(ColumnSelect, ColumnSelect);
							e.consume();
						}
					}

					//右方向移動（Tab）
					else if ( e.getKeyCode() == KeyEvent.VK_TAB ) {
						if (e.getModifiers() == KeyEvent.SHIFT_MASK) {

							//最初列にフォーカス時
							if ( ColumnSelect == 0 ) {

								//選択元テーブルを未選択に設定
								TableCellEditor tce = ListMeisai.getCellEditor();
								if(tce != null){
									ListMeisai.getCellEditor().stopCellEditing();
								}
								ListMeisai.clearSelection();

								//選択先テーブルへフォーカス
								HaigoMeisai.requestFocus();
								HaigoMeisai.setRowSelectionInterval(RowSelect, RowSelect);
								int col = HaigoMeisai.getColumnCount() - 1;
								HaigoMeisai.setColumnSelectionInterval(col, col);
								e.consume();
							}
	                    } else {

	                    	//最終列にフォーカス時
							if ( isLastColumnSelect && !isLastRowSelect ) {

								//選択元テーブルを未選択に設定
								TableCellEditor tce = ListMeisai.getCellEditor();
								if(tce != null){
									ListMeisai.getCellEditor().stopCellEditing();
								}
								ListMeisai.clearSelection();

								//選択先テーブルへフォーカス
								HaigoMeisai.requestFocus();
								HaigoMeisai.setRowSelectionInterval(RowSelect+1, RowSelect+1);
								HaigoMeisai.setColumnSelectionInterval(0, 0);

								//選択元テーブルを未選択に設定
								ListMeisai.clearSelection();
								e.consume();
							}
							//最終行・列にフォーカス時
							else if ( isLastRowSelect && isLastColumnSelect ) {
								e.consume();
							}
	                    }
					}

					//左方向移動(left)
					else if ( e.getKeyCode() == KeyEvent.VK_LEFT) {

						//最終列にフォーカス時
						if ( ColumnSelect == 0 ) {

							//選択元テーブルを未選択に設定
							TableCellEditor tce = ListMeisai.getCellEditor();
							if(tce != null){
								ListMeisai.getCellEditor().stopCellEditing();
							}
							ListMeisai.clearSelection();

							//選択先テーブルへフォーカス
							HaigoMeisai.requestFocus();
							HaigoMeisai.setRowSelectionInterval(RowSelect, RowSelect);
							int col = HaigoMeisai.getColumnCount() - 1;
							HaigoMeisai.setColumnSelectionInterval(col, col);
							e.consume();
						}
					}
				}
			});
		} catch ( Exception e ) {

			e.printStackTrace();

			//エラー設定
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("HaigoMeisaiのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori("Trial1Table:HaigoMeisaiFocusControl");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/************************************************************************************
	 *
	 *   リテラルデータをコンボボックスへ設定
	 *   @author TT katayama
	 *   @param comb : 設定対象コンボボックス
	 *   @param literalData : 設定対象リテラルデータ
	 *   @param strLiteralCd : 表示対象リテラルコード
	 *   @param intType : 表示用リテラルコードのタイプ(0:コード, 1:実値)
	 *
	 ************************************************************************************/
	private void setLiteralCmb(JComboBox comb, LiteralData literalData, String strLiteralCd, int intType) {
		try{
			int i;
			String literalCd = "";
			String literalNm = "";
			Object viewLiteralNm = "";

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
					comb.addItem(strLiteralCd);
					comb.setSelectedItem(strLiteralCd);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

	/************************************************************************************
	 *
	 *   色指定サブ画面　起動&色変更イベントクラス
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class colorChange extends MouseAdapter{

		//*********************************************************************************
		//  メンバ格納
		//*********************************************************************************
		TableBase TableBase;
		colorChangeEv ccev;
		int tableFlg = 0; //配合明細 or 試作明細(0=配合明細,1=試作明細)

		//*********************************************************************************
		//  コンストラクタ
		//*********************************************************************************
		public colorChange(TableBase tb,int tbFg){
			TableBase = tb;
			tableFlg = tbFg;
			//チェンジリスナー
			ccev = new colorChangeEv(TableBase);
		}

		//*********************************************************************************
		//  マウスクリック
		//*********************************************************************************
		public void mouseClicked(final MouseEvent me) {

			//ダブルクリック
			if(me.getClickCount()==2) {

				//リスナーを削除
				colorSubDisp.getColorPanel().getColorChooser().getSelectionModel().removeChangeListener(ccev);

				//リスナーを登録
				colorSubDisp.getColorPanel().getColorChooser().getSelectionModel().addChangeListener(ccev);

				//色指定サブ画面 ボタン クリック時の処理
				colorSubDisp.setVisible(true);
			}
		}

		//*********************************************************************************
		//  色選択クラス
		//*********************************************************************************
		class colorChangeEv implements ChangeListener{

			//------------------------------------------------------------------------------
			//  メンバ格納
			//------------------------------------------------------------------------------
			TableBase TableBase;

			//------------------------------------------------------------------------------
			//  コンストラクタ
			//------------------------------------------------------------------------------
			public colorChangeEv(TableBase tb){
				TableBase = tb;
			}

			//------------------------------------------------------------------------------
			//  色選択時
			//------------------------------------------------------------------------------
			public void stateChanged(ChangeEvent e) {
				try{
					//色情報を取得
					Color selectColor = colorSubDisp.getColorPanel().getColorChooser().getColor();

					System.out.println("色指定："+selectColor.getRGB());

					//テーブル情報取得
					int row = TableBase.getSelectedRow();
					int col = TableBase.getSelectedColumn();

					//キー項目取得
					int intShisakuSeq = -1;
					int intKoteiCd    = -1;
					int intKoteiSeq   = -1;

					//工程CD & 工程SEQ　取得
					MiddleCellEditor mceHaigoKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
					DefaultCellEditor dceHaigoKey = (DefaultCellEditor)mceHaigoKey.getTableCellEditor(row);
					CheckboxBase chkHaigoKey = (CheckboxBase)dceHaigoKey.getComponent();
					intKoteiCd  = Integer.parseInt(chkHaigoKey.getPk1());
					intKoteiSeq = Integer.parseInt(chkHaigoKey.getPk2());

					//----------------------------- 配合明細の場合 -------------------------------
					if(tableFlg == 0){
						for(int i=4; i<TableBase.getColumnCount(); i++){

							//レンダラ取得
							MiddleCellRenderer mr = (MiddleCellRenderer)TableBase.getCellRenderer(row, i);
							TextFieldCellRenderer dr = (TextFieldCellRenderer)mr.getTableCellRenderer(row);

							//色変更
							dr.setColor(new Color(selectColor.getRGB()));
						}

						//データ挿入
						DataCtrl.getInstance().getTrialTblData().UpdHaigouGenryoColor(
								intKoteiCd,
								intKoteiSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(Integer.toString(selectColor.getRGB())),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

					//----------------------------- 試作明細の場合 -------------------------------
					}else{

						//レンダラ取得
						MiddleCellRenderer mr = (MiddleCellRenderer)TableBase.getCellRenderer(row, col);
						TextFieldCellRenderer dr = (TextFieldCellRenderer)mr.getTableCellRenderer(row);

						//色変更
						dr.setColor(new Color(selectColor.getRGB()));

						//試作SEQ　取得
						MiddleCellEditor mceHeaderKey = (MiddleCellEditor)ListHeader.getCellEditor(0, col);
						DefaultCellEditor dceHeaderKey = (DefaultCellEditor)mceHeaderKey.getTableCellEditor(0);
						CheckboxBase chkHeaderKey = (CheckboxBase)dceHeaderKey.getComponent();
						intShisakuSeq  = Integer.parseInt(chkHeaderKey.getPk1());

						//データ挿入
						DataCtrl.getInstance().getTrialTblData().UpdShisakuListColor(
								intShisakuSeq,
								intKoteiCd,
								intKoteiSeq,
								DataCtrl.getInstance().getTrialTblData().checkNullString(Integer.toString(selectColor.getRGB())),
								DataCtrl.getInstance().getUserMstData().getDciUserid()
							);

					}

					//選択情報をクリア
//					TableCellEditor hmEditor = TableBase.getCellEditor();
//					if(hmEditor != null){
//						hmEditor.stopCellEditing();
//					}
					TableBase.clearSelection();

					//選択情報の再設定
					TableBase.requestFocus();
					TableBase.setRowSelectionInterval(row, row);
					TableBase.setColumnSelectionInterval(col, col);

					//画面を非表示
					colorSubDisp.setVisible(false);

					//リスナーを削除
					//colorSubDisp.getColorPanel().getColorChooser().getSelectionModel().removeChangeListener(this);

					colorSubDisp = new ColorSubDisp("色指定画面");

					//テスト表示
					//System.out.println(row);

				}catch(Exception ex){

				}finally{
					//テスト表示
					//DataCtrl.getInstance().getTrialTblData().dispHaigo();
					//DataCtrl.getInstance().getTrialTblData().dispProtoList();
				}

			}
		}
	}

	/************************************************************************************
	 *
	 *   試作分析データ確認サブ画面　起動イベントクラス
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class analysisDisp extends MouseAdapter{
		public void mouseClicked(final MouseEvent me) {
			if(me.getClickCount()==2) {
				try{
					//試作分析データ確認サブ画面 ボタン クリック時の処理
					analysinSubDisp.setVisible(true);
					analysinSubDisp.getAnalysisPanel().init();

				}catch(ExceptionBase eb){

					DataCtrl.getInstance().getMessageCtrl().PrintErrMessage(eb);

				}catch(Exception e){

					e.printStackTrace();

				}

			}
		}
	}

	/************************************************************************************
	 *
	 *   原料コードコピークラス
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class CopyGenryo extends KeyAdapter {
		//キー押下時
		public void keyPressed( KeyEvent e ){
			try{

				int keyCode = e.getKeyCode();

				//クリップボードデータ取得
				if(keyCode == KeyEvent.VK_F3){

					//コピーデータ取得
					String cpData = DataCtrl.getInstance().getClipboardData().getStrClipboad();

					if(cpData != null && cpData.length() > 0){

						String genryoCd = cpData.split("\n")[0];
						String kaishaCd = cpData.split("\n")[1];
						String kojoCd = cpData.split("\n")[2];

						//配合データ取得
						ArrayList ary = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);
						int haigoNum = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

						//選択行取得
						int selRow = HaigoMeisai.getSelectedRow();
						int selCol = HaigoMeisai.getSelectedColumn();

						//編集可能行　且つ　原料コードのみ処理
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
						//if(selRow < HaigoMeisai.getRowCount()-haigoNum-8 && selCol == 3){
						if(selRow < HaigoMeisai.getRowCount()-haigoNum-9 && selCol == 3){
						//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

							//キーコンポーネント取得
							MiddleCellEditor mce2 = (MiddleCellEditor)HaigoMeisai.getCellEditor(selRow, 2);
							DefaultCellEditor tfce2 = (DefaultCellEditor)mce2.getTableCellEditor(selRow);

							//原料行の場合
							if(tfce2.getComponent() instanceof CheckboxBase){

								//原料コードコピーフラグをon
								blnGenryoCopy = true;

								//主キー取得
								CheckboxBase cb = (CheckboxBase)tfce2.getComponent();
								int koteiCd  = Integer.parseInt(cb.getPk1());
								int koteiSeq = Integer.parseInt(cb.getPk2());

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
								//工程キー項目取得
								int intShisakuSeq = 0;
								boolean hanshu_chk = DataCtrl.getInstance().getTrialTblData().checkListHenshuOkFg(intShisakuSeq, koteiCd, koteiSeq);

								//編集可能の場合：既存処理
								if(hanshu_chk){

								}
								//編集不可の場合：処理しない
								else{
									blnGenryoCopy = false;
									return;
								}
//add end   -------------------------------------------------------------------------------


								//------------------------------- 表示値変更 -----------------------------------
								HaigoMeisai.setValueAt(genryoCd, selRow, 3);

								//------------------------------- データ挿入 ------------------------------------
								//会社挿入
								DataCtrl.getInstance().getTrialTblData().UpdHaigoKaishaCd(
										koteiCd,
										koteiSeq,
										DataCtrl.getInstance().getTrialTblData().checkNullInt(kaishaCd),
										DataCtrl.getInstance().getUserMstData().getDciUserid()
									);
								//工場挿入
								DataCtrl.getInstance().getTrialTblData().UpdHaigoKojoCd(
										koteiCd,
										koteiSeq,
										DataCtrl.getInstance().getTrialTblData().checkNullInt(kojoCd),
										DataCtrl.getInstance().getUserMstData().getDciUserid()
									);
							}
						}
					}
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				//テスト表示
				//DataCtrl.getInstance().getTrialTblData().dispHaigo();

			}

		}
	}

	/************************************************************************************
	 *
	 *   テーブルコピークラス
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	class CopyCell extends KeyAdapter {
		public void keyPressed( KeyEvent e ){
			try{

				int keyCode = e.getKeyCode();

				//配合データ取得
				ArrayList retuData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

				//最大工程順取得
				int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
				//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
				//int maxRow = ListMeisai.getRowCount()-maxKotei-8;
				int maxRow = ListMeisai.getRowCount()-maxKotei-9;
				//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

				//-------------------------- テーブルセルデータコピー  -------------------------------
				if(keyCode == KeyEvent.VK_F1){
					//選択セル行列取得
					int[] rows = ListMeisai.getSelectedRows();
					int[] columns = ListMeisai.getSelectedColumns();

					//コピー配列初期化
					ArrayList aryCopy = new ArrayList();

					//選択行ループ
					for(int i=0; i<rows.length; i++){

						//行配列初期化
						ArrayList aryRows = new ArrayList();

						//選択列ループ
						for(int j=0; j<columns.length; j++){

							//行列番号取得
							int row = rows[i];
							int col = columns[j];

							//値取得
							if(row < maxRow){
								Object value = ListMeisai.getValueAt(row, col);

								//行配列へ追加
								aryRows.add(value);
							}
						}

						//コピー配列へ追加
						aryCopy.add(aryRows);
					}

					//クリップボードへ保存
					DataCtrl.getInstance().getClipboardData().setAryClipboad(aryCopy);

					//処理終了
					e.consume();
				}

				//------------------------ テーブルセルデータペースト  -------------------------------
				if(keyCode == KeyEvent.VK_F3){

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
					//確認メッセージ
					boolean kakuninFg = false;
					//再計算
					boolean exeCopyFg = false;
					//ﾍﾟｰｽﾄ先試作SEQ保存用
					ArrayList aryPstSeq = new ArrayList();
//add end   -------------------------------------------------------------------------------

					//クリップボードより取得
					ArrayList aryCopy = DataCtrl.getInstance().getClipboardData().getAryClipboad();

					//選択セル行列取得
					int row = ListMeisai.getSelectedRows()[0];
					int col = ListMeisai.getSelectedColumns()[0];

					//最大テーブル行列取得
					int row_max = ListMeisai.getRowCount();
					int col_max = ListMeisai.getColumnCount();

					int sel_max_row = -1;
					int sel_max_col = -1;

					//テーブルへデータ設定
					for(int i=0; i<aryCopy.size(); i++){

						//列データ取得
						ArrayList selCols = (ArrayList)aryCopy.get(i);
						for(int j=0; j<selCols.size(); j++){

							//テーブル行列を指定
							int set_row = row + i;
							int set_col = col + j;

								//値の取得
								String value = null;
								if(selCols.get(j) != null){
									value = selCols.get(j).toString();
								}

								//値設定
								if(set_row < row_max && set_col < col_max){

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
								//試作SEQ取得
								int intShisakuSeq = 0;
								MiddleCellEditor deleteMc = (MiddleCellEditor)ListHeader.getCellEditor(0, set_col);
								DefaultCellEditor deleteDc = (DefaultCellEditor)deleteMc.getTableCellEditor(0);
								CheckboxBase CheckboxBase = (CheckboxBase)deleteDc.getComponent();
								intShisakuSeq = Integer.parseInt(CheckboxBase.getPk1());

								//列キー項目取得
								boolean chk = DataCtrl.getInstance().getTrialTblData().checkShisakuIraiKakuteiFg(intShisakuSeq);

								//編集可能の場合：既存処理
								if(chk){

	//add start -------------------------------------------------------------------------------
	//QP@00412_シサクイック改良 No.7
									if(kakuninFg){

									}
									else{

										//再計算の影響範囲確認
						    			if(AutoCopyKeisanCheck(ListMeisai.getSelectedColumn())){

						    			}
						    			else{
						    				//ダイアログコンポーネント設定
											JOptionPane jp = new JOptionPane();

							    			//確認ダイアログ表示
											int option = jp.showConfirmDialog(
													jp.getRootPane(),
													JwsConstManager.JWS_ERROR_0046
													, "確認メッセージ"
													,JOptionPane.YES_NO_OPTION
													,JOptionPane.PLAIN_MESSAGE
												);

											//「はい」押下
										    if (option == JOptionPane.YES_OPTION){
										    	exeCopyFg = true;

										    //「いいえ」押下
										    }else if (option == JOptionPane.NO_OPTION){
										    	exeCopyFg = false;
										    }
										    kakuninFg = true;
						    			}
									}
	//add end   -------------------------------------------------------------------------------



									//キー項目表示（配合データ）
									int pk_koteiCd = 0;
									int pk_koteiSeq = 0;
									MiddleCellEditor mc_haigo = (MiddleCellEditor)HaigoMeisai.getCellEditor(set_row, 2);
									DefaultCellEditor tc_haigo = (DefaultCellEditor)mc_haigo.getTableCellEditor(set_row);
									if(((JComponent)tc_haigo.getComponent()) instanceof CheckboxBase){
										CheckboxBase chk_haigo = (CheckboxBase)tc_haigo.getComponent();
										pk_koteiCd = Integer.parseInt(chk_haigo.getPk1());
										pk_koteiSeq = Integer.parseInt(chk_haigo.getPk2());
									}

									//キー項目取得（試作列データ）
									int pk_sisakuSeq = 0;
									MiddleCellEditor mc_shisaku = (MiddleCellEditor)ListHeader.getCellEditor(0, set_col);
									DefaultCellEditor tc_shisaku = (DefaultCellEditor)mc_shisaku.getTableCellEditor(0);
									CheckboxBase chk_shisaku = (CheckboxBase)tc_shisaku.getComponent();
									pk_sisakuSeq = Integer.parseInt(chk_shisaku.getPk1());

    //add start -------------------------------------------------------------------------------
    //QP@00412_シサクイック改良 No.7
									//ﾍﾟｰｽﾄ先の試作SEQ保存
									aryPstSeq.add(Integer.toString(pk_sisakuSeq));
    //add end   -------------------------------------------------------------------------------

									//工程行or計算行の場合はペーストしない
									if(pk_koteiCd > 0){

										//テーブル表示値設定
										ListMeisai.setValueAt(value, set_row, set_col);

										//試作リストデータ設定
										if(value == null || value.length() == 0){
											DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
													pk_sisakuSeq, pk_koteiCd, pk_koteiSeq, null);
										}else{
											DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
													pk_sisakuSeq, pk_koteiCd, pk_koteiSeq, new BigDecimal(value));
										}

	//add start -------------------------------------------------------------------------------
	//QP@00412_シサクイック改良 No.7
										if(exeCopyFg){
											//コピー先計算フラグが可に設定されている場合　且つ　実行可能な場合
								    		if( JwsConstManager.JWS_COPY_0002 ){

								    			//「括弧開き」指定
					    						String strKakkoHiraki = JwsConstManager.JWS_COPY_0003;

					    						//「括弧閉じ」指定
					    						String strKakkoTozi = JwsConstManager.JWS_COPY_0004;

								    			//配合データ配列取得
								    			ArrayList aryShisaku = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(pk_sisakuSeq);

								    			//サンプルNO取得
//								    			String SampleNo = ((TrialData)aryShisaku.get(0)).getStrSampleNo();
//								    			String chkSampleNo = strKakkoHiraki + SampleNo + strKakkoTozi;

								    			//サンプルSEQ取得
								    			String SampleSeq = JwsConstManager.JWS_COPY_0005 + Integer.toString((((TrialData)aryShisaku.get(0)).getIntShisakuSeq()));

								    			//サンプルNO取得
								    			String SampleKeisan = ((TrialData)aryShisaku.get(0)).getStrKeisanSiki();
								    			if(SampleKeisan == null || SampleKeisan.length() ==0){
								    				SampleKeisan = SampleSeq;
								    			}

								    			//String SampleSeq = ((TrialData)aryShisaku.get(0))
								    			String chkSampleNo = strKakkoHiraki + SampleKeisan + strKakkoTozi;

								    			//試作列ループ
								    			for(int k=0; k<ListHeader.getColumnCount(); k++){

								    				//自身の列でない場合
								    				if( k != set_col ){

								    					//試作SEQ取得
							    						MiddleCellEditor mceHeaderKey1 = (MiddleCellEditor)ListHeader.getCellEditor(0, k);
														DefaultCellEditor dceHeaderKey1 = (DefaultCellEditor)mceHeaderKey1.getTableCellEditor(0);
														CheckboxBase chkHeaderKey1 = (CheckboxBase)dceHeaderKey1.getComponent();
														int roopShisakuSeq  = Integer.parseInt(chkHeaderKey1.getPk1());

//								    					//サンプルNo取得
//								    					String roopSampleNo =
//								    						DataCtrl.getInstance().getTrialTblData().toString(ListHeader.getValueAt(3, k), "");
								    					//計算式取得
								    					String roopSampleNo =
								    						DataCtrl.getInstance().getTrialTblData().SearchShisakuKeisanSiki(roopShisakuSeq);

								    					//自列のサンプルNoが含まれる場合
								    					if(roopSampleNo.indexOf(chkSampleNo) >= 0){

															//原価試算依頼されているデータは処理しない
															TrialData chkTrialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(roopShisakuSeq).get(0);
															if(chkTrialData.getFlg_init() == 1){

															}else{

																//コピー先計算実行Fg
																boolean blnExec = true;

																//ﾍﾟｰｽﾄ先の試作かどうか判断
//																for(int l=0; l<aryPstSeq.size(); l++){
//
//																	//試作SEQ取得
//																	String seq = (String) aryPstSeq.get(l);
//
//																	//実行不可（ﾍﾟｰｽﾄ先を優先）
//																	if(seq.equals(Integer.toString(roopShisakuSeq))){
//																		blnExec = false;
//																	}
//
//																}

																//実行可能な場合（ﾍﾟｰｽﾄ先の試作列でない場合）
																if(blnExec){

																	String strKekka = "";

																	if(value == null || value.length() == 0){

																	}
																	else{
																		//自身のサンプルNoが設定されてある部分を配合量とおきかえる
																		//String repSampleNo = "\\\\" + strKakkoHiraki + SampleNo + "\\\\" + strKakkoTozi;
																		String repSampleNo = strKakkoHiraki + SampleKeisan + strKakkoTozi;
																		//roopSampleNo = roopSampleNo.replaceAll( repSampleNo , value);
																		roopSampleNo = roopSampleNo.replaceAll( "\\Q" + chkSampleNo + "\\E" , value);

																		//計算式変換
																		String keisanSiki = DataCtrl.getInstance().getTrialTblData().changeKeisanLogic( roopSampleNo , 0 );

																		//配合量設定
																		String keisan =
																			DataCtrl.getInstance().getTrialTblData().getKeisanShisakuSeq( keisanSiki , pk_koteiCd , pk_koteiSeq );

											    						//計算実行
																		strKekka = DataCtrl.getInstance().getTrialTblData().execKeisan(keisan);

																		//小数洗替
														    			if(strKekka != null && strKekka.length() > 0){

														    				//洗替処理
														    				strKekka = ShosuAraiHulfUp(strKekka);

														    			}
																	}

													    			//テーブル挿入
																	ListMeisai.setValueAt(strKekka, set_row, k);

													    			//データ挿入
																	DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
																			roopShisakuSeq,
																			pk_koteiCd,
																			pk_koteiSeq,
																			DataCtrl.getInstance().getTrialTblData().checkNullDecimal(strKekka)
																		);

																	//工程合計の算出
																	koteiSum(k);

																}
									    					}
														}
								    				}
								    			}
								    		}
										}
	//add end   -------------------------------------------------------------------------------
									}
								}
								//編集不可の場合：処理無し
								else{

								}
//mod end   -------------------------------------------------------------------------------

								//最終ペースト値の取得
								if(sel_max_row < set_row){
									if(set_row < maxRow){
										sel_max_row = set_row;
									}else{
										sel_max_row = maxRow-1;
									}
								}
								if(sel_max_col < set_col){
									sel_max_col = set_col;
								}
							}
						}
					}

					//選択色の設定
					if(sel_max_row > -1 && sel_max_col > -1){
						ListMeisai.changeSelection(sel_max_row, sel_max_col, false, true);
					}

					//工程合計計算
					if(aryCopy.size() > 0){
						ArrayList selCols = (ArrayList)aryCopy.get(0);
						for(int j=0; j<selCols.size(); j++){

							//テーブル行列を指定
							int set_col = col + j;
							if(set_col < ListMeisai.getColumnCount()){
								koteiSum(set_col);
							}
						}
						//自動計算
						AutoKeisan();
					}

					//原料費計算
					DispGenryohi();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------

					//製品比重、充填量、水相充填量、油相充填量　計算処理
					ZidouKeisan2();

//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

					//処理終了
					e.consume();
				}
			}catch(Exception ex){
				ex.printStackTrace();

			}finally{
				//DataCtrl.getInstance().getTrialTblData().dispProtoList();

			}
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
			ArrayList aryRetu = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(0);
			TrialData td = (TrialData)aryRetu.get(0);

			//自動計算がチェックされている場合のみ処理
			if(td.getIntZidoKei() == 1){

				//配合データ取得
				ArrayList HaigoData = DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0);

				//最大工程数取得
				int koteiNum = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();

				//試作列数分ループ
				for(int i=0; i<ListHeader.getColumnCount(); i++){

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
					MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
					DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
					CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
					int intSeq  = Integer.parseInt(chkSeq.getPk1());

					// MOD start 20120914 QP@20505 No.1
					//配合表(試作表①)行数
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
					//int maxRow = ListMeisai.getRowCount()-7;
//					int maxRow = ListMeisai.getRowCount()-8;
					//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------
					int kotei_num = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
					int maxRow = ListMeisai.getRowCount()-8-kotei_num;
					// MOD end 20120914 QP@20505 No.1

					//------------------------ 計算必要項目 -----------------------------------
					//①合計重量(g)
					BigDecimal goleiZyuryo = new BigDecimal("0.00");
					Object objZyuryo = ListMeisai.getValueAt(maxRow-1, i);
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
					//2011/05/17 QP@10181_No.71 TT T.Satoh Add Start -------------------------
					//maxRow+=1;
// MOD start 20121017 QP@20505 No.1
					//maxRow+=2;
					maxRow+=2 + koteiNum;
// MOD start 20121017 QP@20505 No.1
					//2011/05/17 QP@10181_No.71 TT T.Satoh Add End --------------------------

					//------------------------------- 総酸計算処理 -----------------------------
					//③総酸合計量/①合計重量
					BigDecimal sosan = new BigDecimal("0.00");
					if(goleiSosan.intValue() > 0 && goleiZyuryo.intValue() > 0){
						sosan = goleiSosan.divide(goleiZyuryo, 2, BigDecimal.ROUND_HALF_UP);
					}


					//データ挿入
					//table.setValueAt(sosan, 1, i);
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
					//table.setValueAt(shokuen, 2, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuSyokuen(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(shokuen.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

					//配合表(試作表①)へ挿入
					ListMeisai.setValueAt(shokuen, maxRow+=1, i);

// ADD start 20121002 QP@20505 No.24
					//------------------------------ ＭＳＧ計算処理 -------------------------------
					//ＭＳＧ合計量/①合計重量
					BigDecimal msg = new BigDecimal("0.00");
					if(goleiMsg.intValue() > 0 && goleiZyuryo.intValue() > 0){
						msg = goleiMsg.divide(goleiZyuryo, 2, BigDecimal.ROUND_HALF_UP);
					}
					//データ挿入
					//table.setValueAt(shokuen, 2, i);
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuMsg(
		    				intSeq,
		    				DataCtrl.getInstance().getTrialTblData().checkNullDecimal(msg.toString()),
		    				DataCtrl.getInstance().getUserMstData().getDciUserid()
		    			);

//					//配合表(試作表①)へ挿入
//					ListMeisai.setValueAt(msg, maxRow+=1, i);
// ADD end 20121002 QP@20505 No.24

					//--------------------------- 水相中酸度計算処理-----------------------------
					//⑤総酸合計量/（①合計量ー②油含有合計量）
					BigDecimal sui_sando = new BigDecimal("0.00");
					if(goleiSosan.intValue() > 0 && (goleiZyuryo.subtract(goleiGanyu)).intValue() > 0){
						sui_sando = goleiSosan.divide((goleiZyuryo.subtract(goleiGanyu)), 2, BigDecimal.ROUND_HALF_UP);
					}

					//データ挿入
					//table.setValueAt(sui_sando, 3, i);
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
					//table.setValueAt(sui_shokuen, 4, i);
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
					//table.setValueAt(sui_sakusan, 5, i);
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
					//配合表へ挿入
//					ListMeisai.setValueAt(sui_msg, maxRow+=1, i);

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

	/************************************************************************************
	 *
	 *   仕上重量計算
	 *    :  仕上重量の計算を行う
	 *   @author TT shima
	 *
	 ************************************************************************************/
	public void ShiagariZyuryoKeisan(int column){
		try{
			
			//モード編集
    		if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
					JwsConstManager.JWS_COMPONENT_0055, DataCtrl.getInstance().getParamData().getStrMode())){
    			
				int gokeiShiagari = ListMeisai.getRowCount()-8;
				int koteiNum = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
				int koteiSum = gokeiShiagari - koteiNum;
				
				BigDecimal shiagariGokei = null;
				
				for(int j = 0; j < koteiNum ;j++){
					
					//量データ取得
					String ryo = (String)ListMeisai.getValueAt(koteiSum+j, column);
					
					if(ryo != null && ryo.length() > 0){
					
						if(shiagariGokei instanceof BigDecimal){
						}else{
							shiagariGokei = new BigDecimal("0");
						}
						
						//工程合計加算
						shiagariGokei = shiagariGokei.add(new BigDecimal(ryo));
					}
				}
				
				//合計を出力
				ListMeisai.setValueAt(shiagariGokei, gokeiShiagari, column);
			
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	/************************************************************************************
	 *
	 * 星記号削除
	 * @throws ExceptionBase
	 *
	 ************************************************************************************/
	public String delMark(String strVal){

		String ret = strVal;

		try{

			if(ret != null && ret.length() > 0){

				//星記号削除
				if(ret.substring(0, 1).equals(JwsConstManager.JWS_MARK_0001) ||
						ret.substring(0, 1).equals(JwsConstManager.JWS_MARK_0002)){
					ret = ret.substring(1);
					if(ret.length() == 0){
						ret = null;
					}
				}
			}

		}catch(Exception e){

			e.printStackTrace();

		}finally{

		}

		return ret;
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
	 *   kCheckゲッター
	 *   @author TT nishigawa
	 *   @return int[] : 選択工程配列
	 *
	 ************************************************************************************/
	public int[] getKCheck() {
		return kCheck;
	}

	/************************************************************************************
	 *
	 *   kCheckセッター
	 *   @author TT nishigawa
	 *   @param check : 選択工程配列
	 *
	 ************************************************************************************/
	public void setKCheck(int[] check) {
		kCheck = check;
	}

	/************************************************************************************
	 *
	 *   aryGenryoCheckゲッター
	 *   @author TT nishigawa
	 *   @return ArrayList : 原料選択配列
	 *
	 ************************************************************************************/
	public ArrayList getAryGenryoCheck() {
		return aryGenryoCheck;
	}

	/************************************************************************************
	 *
	 *   aryGenryoCheckセッター
	 *   @author TT nishigawa
	 *   @param aryGenryoCheck : 原料選択配列
	 *
	 ************************************************************************************/
	public void setAryGenryoCheck(ArrayList aryGenryoCheck) {
		this.aryGenryoCheck = aryGenryoCheck;
	}

	/************************************************************************************
	 *
	 *   ListHeaderゲッター
	 *   @author TT nishigawa
	 *   @return TableBase : 試作列ヘッダーテーブル
	 *
	 ************************************************************************************/
	public TableBase getListHeader() {
		return ListHeader;
	}

	/************************************************************************************
	 *
	 *   ListHeaderセッター
	 *   @author TT nishigawa
	 *   @param listHeader : 試作列ヘッダーテーブル
	 *
	 ************************************************************************************/
	public void setListHeader(TableBase listHeader) {
		ListHeader = listHeader;
	}

	/************************************************************************************
	 *
	 *   HaigoMeisaiゲッター
	 *   @author TT nishigawa
	 *   @return TableBase : 配合明細テーブル
	 *
	 ************************************************************************************/
	public TableBase getHaigoMeisai() {
		return HaigoMeisai;
	}

	/************************************************************************************
	 *
	 *   HaigoMeisaiセッター
	 *   @author TT nishigawa
	 *   @param haigoMeisai : 配合明細テーブル
	 *
	 ************************************************************************************/
	public void setHaigoMeisai(TableBase haigoMeisai) {
		HaigoMeisai = haigoMeisai;
	}

	/************************************************************************************
	 *
	 *   AnalysinSubDispゲッター
	 *   @author TT nishigawa
	 *   @return AnalysinSubDisp : 原料分析値確認画面
	 *
	 ************************************************************************************/
	public AnalysinSubDisp getAnalysinSubDisp() {
		return analysinSubDisp;
	}

	/************************************************************************************
	 *
	 *   AnalysinSubDispセッター
	 *   @author TT nishigawa
	 *   @param AnalysinSubDisp : 原料分析値確認画面
	 *
	 ************************************************************************************/
	public void setAnalysinSubDisp(AnalysinSubDisp analysinSubDisp) {
		this.analysinSubDisp = analysinSubDisp;
	}

	/************************************************************************************
	 *
	 *   ListMeisaiゲッター
	 *   @author TT nishigawa
	 *   @return TableBase : 試作明細テーブル
	 *
	 ************************************************************************************/
	public TableBase getListMeisai() {
		return ListMeisai;
	}

	/************************************************************************************
	 *
	 *   ListMeisaiセッター
	 *   @author TT nishigawa
	 *   @param listMeisai : 試作明細テーブル
	 *
	 ************************************************************************************/
	public void setListMeisai(TableBase listMeisai) {
		ListMeisai = listMeisai;
	}

	//2011/04/20 QP@10181_No.41 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 *
	 *   scrollMainゲッター
	 *   @author TT satoh
	 *   @return scrollMain :　試作明細テーブルのスクロールバー
	 *
	 ************************************************************************************/
	public ScrollBase getScrollMain() {
		return scrollMain;
	}

	/************************************************************************************
	 *
	 *   scrollMainセッター
	 *   @author TT satoh
	 *   @param _scrollMain : 試作明細テーブルのスクロールバー
	 *
	 ************************************************************************************/
	public void setScrollMain(ScrollBase _scrollMain) {
		scrollMain = _scrollMain;
	}
	//2011/04/20 QP@10181_No.41 TT T.Satoh Add End ---------------------------


//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
	/************************************************************************************
	 *
	 *   コピーしたセルの再計算
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public void AutoCopyKeisan() throws ExceptionBase{
		try{
			if(editFg && editCol > -1){
				//コピー先計算フラグが可に設定されている場合
	    		if( JwsConstManager.JWS_COPY_0002){

	    			//再計算の影響範囲確認
	    			if(AutoCopyKeisanCheck(editCol)){

	    			}
	    			else{
//	    				//ダイアログコンポーネント設定
						JOptionPane jp = new JOptionPane();

		    			//確認ダイアログ表示
						int option = jp.showConfirmDialog(
								jp.getRootPane(),
								JwsConstManager.JWS_ERROR_0045
								, "確認メッセージ"
								,JOptionPane.YES_NO_OPTION
								,JOptionPane.PLAIN_MESSAGE
							);

						//「はい」押下
					    if (option == JOptionPane.YES_OPTION){

					    //「いいえ」押下
					    }else if (option == JOptionPane.NO_OPTION){
					    	//セル編集フラグを初期化
			    			editFg = false;
					    	return;
					    }
	    			}

	    			//最大工程順取得
//	    			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
	    			//合計仕上重量
	    			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
	    			//int keisanRow = ListMeisai.getRowCount()-7;
	    			int keisanRow = ListMeisai.getRowCount()-8;
	    			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

					for(int j=0 ;j<ListMeisai.getRowCount(); j++){

						//行・列　取得
		    			int row = j;
						int column = editCol;

						//---------------- キー項目取得  -------------------------------
						int intShisakuSeq = -1;
						int intKoteiCd    = -1;
						int intKoteiSeq   = -1;

						//試作SEQ　取得
						MiddleCellEditor mceHeaderKey = (MiddleCellEditor)ListHeader.getCellEditor(0, column);
						DefaultCellEditor dceHeaderKey = (DefaultCellEditor)mceHeaderKey.getTableCellEditor(0);
						CheckboxBase chkHeaderKey = (CheckboxBase)dceHeaderKey.getComponent();
						intShisakuSeq  = Integer.parseInt(chkHeaderKey.getPk1());

						//工程CD & 工程SEQ　取得
						MiddleCellEditor mceHaigoKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
						DefaultCellEditor dceHaigoKey = (DefaultCellEditor)mceHaigoKey.getTableCellEditor(row);

						//工程行でない場合 or 合計仕上重量行
						if(dceHaigoKey.getComponent() instanceof CheckboxBase || j == keisanRow){

							//「括弧開き」指定
    						String strKakkoHiraki = JwsConstManager.JWS_COPY_0003;

    						//「括弧閉じ」指定
    						String strKakkoTozi = JwsConstManager.JWS_COPY_0004;

			    			//配合データ配列取得
			    			ArrayList aryShisaku = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intShisakuSeq);

			    			//サンプルSEQ取得
			    			String SampleSeq = JwsConstManager.JWS_COPY_0005 + Integer.toString((((TrialData)aryShisaku.get(0)).getIntShisakuSeq()));

			    			//サンプルNO取得
			    			String SampleKeisan = ((TrialData)aryShisaku.get(0)).getStrKeisanSiki();
			    			if(SampleKeisan == null || SampleKeisan.length() ==0){
			    				SampleKeisan = SampleSeq;
			    			}

			    			//String SampleSeq = ((TrialData)aryShisaku.get(0))
			    			String chkSampleNo = strKakkoHiraki + SampleKeisan + strKakkoTozi;




			    			//表示値取得
			    			String insert = "";
			    			try{
		    					insert = ListMeisai.getValueAt( row, column ).toString();
		    				}catch(Exception e){

		    				}

			    			//試作列ループ
			    			for(int i=0; i<ListHeader.getColumnCount(); i++){

			    				//自身の列でない場合
			    				if( i != column ){

			    					//試作SEQ取得
		    						MiddleCellEditor mceHeaderKey1 = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
									DefaultCellEditor dceHeaderKey1 = (DefaultCellEditor)mceHeaderKey1.getTableCellEditor(0);
									CheckboxBase chkHeaderKey1 = (CheckboxBase)dceHeaderKey1.getComponent();
									int roopShisakuSeq  = Integer.parseInt(chkHeaderKey1.getPk1());

			    					//計算式取得
			    					String roopSampleKeisanSiki =
			    						DataCtrl.getInstance().getTrialTblData().SearchShisakuKeisanSiki(roopShisakuSeq);

			    					//計算式がNULLの場合
			    					if(roopSampleKeisanSiki == null){

			    					}
			    					//計算式がNULLでない場合
			    					else{
			    						//自列のサンプルNoが含まれる場合
				    					if(roopSampleKeisanSiki.indexOf(chkSampleNo) >= 0){

											//原価試算依頼されているデータは処理しない
											TrialData chkTrialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(roopShisakuSeq).get(0);
											if(chkTrialData.getFlg_init() == 1){

											}
											else{
												//自身のサンプルNoが設定されてある部分を配合量とおきかえる
												String repSampleNo = strKakkoHiraki + SampleKeisan + strKakkoTozi;
												//String repSampleNo = JwsConstManager.JWS_COPY_0005 + SampleNo;
												roopSampleKeisanSiki = roopSampleKeisanSiki.replaceAll( "\\Q" + chkSampleNo + "\\E" , insert);

												//計算式変換
												String keisanSiki = DataCtrl.getInstance().getTrialTblData().changeKeisanLogic( roopSampleKeisanSiki , 0 );

//												//String keisanSiki = roopSampleNo;
//												//自身のサンプルNoが設定されてある部分を配合量とおきかえる
//												String repSampleNo = SampleSeq;
//												roopSampleKeisanSiki = roopSampleKeisanSiki.replaceAll( repSampleNo , insert);
//												//計算式変換
//												String keisanSiki = roopSampleKeisanSiki;

												//合計仕上重量計算
												if(j == keisanRow){
													String keisan =
														DataCtrl.getInstance().getTrialTblData().getKeisanShisakuSeqSiagari( keisanSiki);

													//計算実行
													String strKekka = DataCtrl.getInstance().getTrialTblData().execKeisan(keisan);

													//小数洗替（合計仕上重量）
									    			if(strKekka != null && strKekka.length() > 0){

									    				//洗替処理
									    				strKekka = ShosuAraiHulfUp_keta(strKekka,"4");

									    			}

													//テーブル挿入
													ListMeisai.setValueAt(strKekka, row, i);

													//データ挿入
													DataCtrl.getInstance().getTrialTblData().UpdShiagariRetuDate(
															roopShisakuSeq,
															DataCtrl.getInstance().getTrialTblData().checkNullDecimal(strKekka),
															DataCtrl.getInstance().getUserMstData().getDciUserid()
														);

												}
												//配合量計算
												else{

													//キー項目取得
													CheckboxBase chkHaigoKey = (CheckboxBase)dceHaigoKey.getComponent();
													intKoteiCd  = Integer.parseInt(chkHaigoKey.getPk1());
													intKoteiSeq = Integer.parseInt(chkHaigoKey.getPk2());

													//配合量設定
													String keisan =
														DataCtrl.getInstance().getTrialTblData().getKeisanShisakuSeq( keisanSiki , intKoteiCd , intKoteiSeq );

						    						//計算実行
													String strKekka = DataCtrl.getInstance().getTrialTblData().execKeisan(keisan);

													//小数洗替
									    			if(strKekka != null && strKekka.length() > 0){

									    				//洗替処理
									    				strKekka = ShosuAraiHulfUp(strKekka);

									    			}

									    			//テーブル挿入
													ListMeisai.setValueAt(strKekka, row, i);

									    			//データ挿入
													DataCtrl.getInstance().getTrialTblData().UpdShisakuListRyo(
															roopShisakuSeq,
															intKoteiCd,
															intKoteiSeq,
															DataCtrl.getInstance().getTrialTblData().checkNullDecimal(strKekka)
														);

						    						//工程合計計算
													koteiSum(i);
												}
											}
				    					}
			    					}

			    				}
			    			}
						}
					}
					//自動計算
					AutoKeisan();

					//原料費計算
	    			DispGenryohi();

	    			//セル編集フラグを初期化
	    			editFg = false;
	    		}
			}
		}catch(ExceptionBase be){

		}catch(Exception ex){
			ex.printStackTrace();

		}finally{
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
		}
	}

	/************************************************************************************
	 *
	 *   自身の列が他列の計算で扱われているかの確認
	 *   @param int　：　列指定
	 *   @return boolean　：　true 影響しない　false 影響する
	 *   @author TT nishigawa
	 *
	 ************************************************************************************/
	public boolean AutoCopyKeisanCheck(int col) throws ExceptionBase{

		boolean ret = true;

		try{
				//コピー先計算フラグが可に設定されている場合
	    		if( JwsConstManager.JWS_COPY_0002){

	    			//最大工程順取得
	    			int maxKotei = DataCtrl.getInstance().getTrialTblData().getIntMaxKotei();
	    			//合計仕上重量
	    			//2011/06/13 QP@10181_No.29 TT T.Satoh Change Start -------------------------
	    			//int keisanRow = ListMeisai.getRowCount()-maxKotei-6;
	    			int keisanRow = ListMeisai.getRowCount()-maxKotei-7;
	    			//2011/06/13 QP@10181_No.29 TT T.Satoh Change End ---------------------------

					for(int j=0 ;j<ListMeisai.getRowCount(); j++){

						//行・列　取得
		    			int row = j;
						int column = col;

						//---------------- キー項目取得  -------------------------------
						int intShisakuSeq = -1;

						//試作SEQ　取得
						MiddleCellEditor mceHeaderKey = (MiddleCellEditor)ListHeader.getCellEditor(0, column);
						DefaultCellEditor dceHeaderKey = (DefaultCellEditor)mceHeaderKey.getTableCellEditor(0);
						CheckboxBase chkHeaderKey = (CheckboxBase)dceHeaderKey.getComponent();
						intShisakuSeq  = Integer.parseInt(chkHeaderKey.getPk1());

						//工程CD & 工程SEQ　取得
						MiddleCellEditor mceHaigoKey = (MiddleCellEditor)HaigoMeisai.getCellEditor(row, 2);
						DefaultCellEditor dceHaigoKey = (DefaultCellEditor)mceHaigoKey.getTableCellEditor(row);

						//工程行でない場合 or 合計仕上重量行
						if(dceHaigoKey.getComponent() instanceof CheckboxBase || j == keisanRow){

							//「括弧開き」指定
    						String strKakkoHiraki = JwsConstManager.JWS_COPY_0003;

    						//「括弧閉じ」指定
    						String strKakkoTozi = JwsConstManager.JWS_COPY_0004;

			    			//配合データ配列取得
			    			ArrayList aryShisaku = DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(intShisakuSeq);

			    			//サンプルSEQ取得
			    			String SampleSeq = JwsConstManager.JWS_COPY_0005 + Integer.toString((((TrialData)aryShisaku.get(0)).getIntShisakuSeq()));

			    			//サンプルNO取得
			    			String SampleKeisan = ((TrialData)aryShisaku.get(0)).getStrKeisanSiki();
			    			if(SampleKeisan == null || SampleKeisan.length() ==0){
			    				SampleKeisan = SampleSeq;
			    			}

			    			//String SampleSeq = ((TrialData)aryShisaku.get(0))
			    			String chkSampleNo = strKakkoHiraki + SampleKeisan + strKakkoTozi;

			    			//試作列ループ
			    			for(int i=0; i<ListHeader.getColumnCount(); i++){

			    				//自身の列でない場合
			    				if( i != column ){

			    					//試作SEQ取得
		    						MiddleCellEditor mceHeaderKey1 = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
									DefaultCellEditor dceHeaderKey1 = (DefaultCellEditor)mceHeaderKey1.getTableCellEditor(0);
									CheckboxBase chkHeaderKey1 = (CheckboxBase)dceHeaderKey1.getComponent();
									int roopShisakuSeq  = Integer.parseInt(chkHeaderKey1.getPk1());

			    					//計算式取得
			    					String roopSampleKeisanSiki =
			    						DataCtrl.getInstance().getTrialTblData().SearchShisakuKeisanSiki(roopShisakuSeq);

			    					//計算式がNULLの場合
			    					if(roopSampleKeisanSiki == null){

			    					}
			    					//計算式がNULLでない場合
			    					else{
			    						//自列のサンプルNoが含まれる場合
				    					if(roopSampleKeisanSiki.indexOf(chkSampleNo) >= 0){

											//原価試算依頼されているデータは処理しない
											TrialData chkTrialData = (TrialData)DataCtrl.getInstance().getTrialTblData().SearchShisakuRetuData(roopShisakuSeq).get(0);
											if(chkTrialData.getFlg_init() == 1){

											}
											else{
												ret = false;
												break;
											}
				    					}
			    					}
			    				}
			    			}
						}
					}
			}
		}catch(ExceptionBase be){

		}catch(Exception ex){
			ex.printStackTrace();

		}finally{
			//DataCtrl.getInstance().getTrialTblData().dispTrial();
		}

		return ret;
	}
//add end   -------------------------------------------------------------------------------

//2011/04/26 QP@10181_No.73 TT T.Satoh Add Start -------------------------
	/************************************************************************************
	 *
	 *   指定した行の単価の編集可不可を設定する
	 *   @param int　：　行指定
	 *   @param boolean　：　true 編集可能　false 編集不可能
	 *   @author TT T.Satoh
	 *
	 ************************************************************************************/
	public void changeTankaHenshuOK(int row, boolean henshu) {
		DefaultCellEditor dceTanka = (DefaultCellEditor)HaigoMeisaiCellEditor5.getTableCellEditor(row);

		if (henshu) {
			((TextboxBase)dceTanka.getComponent()).setEnabled(true);
			((TextboxBase)dceTanka.getComponent()).setEditable(true);
		} else {
			((TextboxBase)dceTanka.getComponent()).setEnabled(false);
			((TextboxBase)dceTanka.getComponent()).setEditable(false);
		}

		TextFieldCellRenderer tfcrTanka =  (TextFieldCellRenderer)HaigoMeisaiCellRenderer5.getTableCellRenderer(row);

		if (henshu) {
			tfcrTanka.setColor(Color.WHITE);
		} else {
			tfcrTanka.setColor(JwsConstManager.JWS_DISABLE_COLOR);
		}
	}
//2011/04/26 QP@10181_No.73 TT T.Satoh Add End ---------------------------

//2011/04/26 QP@10181_No.12 TT Nishigawa Add Start ---------------------------
	public CheckboxBase getAllCheck() {
		return allCheck;
	}
	public void setAllCheck(CheckboxBase allCheck) {
		this.allCheck = allCheck;
	}
//2011/04/26 QP@10181_No.12 TT Nishigawa Add End ---------------------------


//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add Start -------------------------
	/************************************************************************************
	 *
	 *   製品比重、充填量、水相充填量、油相充填量　計算処理
	 *   @param なし
	 *   @author TT T.Nishigawa
	 *
	 ************************************************************************************/
	public void ZidouKeisan2(){

		try{

			//工程パターン取得
			String ptKotei = DataCtrl.getInstance().getTrialTblData().SearchShisakuhinData().getStrPt_kotei();

			//製品比重、充填量、水相充填量、油相充填量　計算処理
			for(int i=0; i<ListHeader.getColumnCount();i++){

				//コンポーネント取得
				MiddleCellEditor mc2 = (MiddleCellEditor)ListHeader.getCellEditor(0, i);
				DefaultCellEditor tc2 = (DefaultCellEditor)mc2.getTableCellEditor(0);
				CheckboxBase getCheck = (CheckboxBase)tc2.getComponent();
				int intSeq = Integer.parseInt(getCheck.getPk1());

	    		//工程パターンが「空白」の場合
	    		if(ptKotei == null || ptKotei.length() == 0){

	    		}
	    		//工程パターンが「空白」でない場合
	    		else{

	    			//工程パターンのValue1取得
					String ptValue = DataCtrl.getInstance().getLiteralDataKoteiPtn().selectLiteralVal1(ptKotei);

					//工程パターンが「調味料１液タイプ」の場合-------------------------------------------------------------
					if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_1)){

						//充填量を計算
						String keisan1 = DataCtrl.getInstance().getTrialTblData().KeisanZyutenType1(intSeq);
						DataCtrl.getInstance().getTrialTblData().UpdGenkaValue(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan1),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid(),
			    				JwsConstManager.JWS_COMPONENT_0134);

					}
					//工程パターンが「調味料２液タイプ」の場合-------------------------------------------------------------
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_2)){

						//製品比重を計算
			    		String keisan = DataCtrl.getInstance().getTrialTblData().KeisanSeihinHiju(intSeq);
			    		DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuHiju(
			    				intSeq,
			    				DataCtrl.getInstance().getTrialTblData().checkNullString(keisan),
			    				DataCtrl.getInstance().getUserMstData().getDciUserid()
			    			);

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
					//工程パターンが「その他・加食タイプ」の場合-------------------------------------------------------------
					else if(ptValue.equals(JwsConstManager.JWS_KOTEITYPE_3)){

					}
	    		}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
//2011/05/11 QP@10181_No.42_49_72 TT T.Nishigawa Add End -------------------------

	
	/*******************************************************************************
	 *
	 *   メモ入力画面
	 *   @author shima.hs
	 *
	 *******************************************************************************/
	class memoPanelDisp extends MouseAdapter {
		
		//*************************************
		//  メンバ格納
		//*************************************
		TableBase TableBase;
		String string = "";
		memoChangeEvent mce;
		
		//*************************************
		//  コンストラクタ
		//*************************************
		public memoPanelDisp(TableBase tb){
			TableBase = tb;
			mce = new memoChangeEvent(TableBase);
		}
		
		//*************************************
		//  マウスクリック
		//*************************************
		public void mouseClicked(final MouseEvent me) {
			
			int column = TableBase.getSelectedColumn();
			int row = 4;
			
			//ダブルクリック
			if(me.getClickCount()==2){
				try{
					if(DataCtrl.getInstance().getModeCtrl().checkModeCtrl(
							JwsConstManager.JWS_COMPONENT_0020,DataCtrl.getInstance().getParamData().getStrMode())){
						
						//編集中の処理を確定させる
						TableBase.editingStopped(null);
						
						//メモ画面のテキストに既入力テキストを設定
						String insert=checkNull(TableBase.getValueAt(row,column));
						memoSubDisp.getMemoInputPanel().getMemoTextBox().setText(insert);
						
						memoSubDisp.getMemoInputPanel().setColumn(column);
						
						//リスナーを削除
						memoSubDisp.getMemoInputPanel().getOKButton().removeActionListener(mce);
						//リスナーを追加
						memoSubDisp.getMemoInputPanel().getOKButton().addActionListener(mce);
						
						//メモ画面表示
						memoSubDisp.setVisible(true);
					}
					//編集不可の場合
					else{
					}
				}catch(ExceptionBase e){
					e.printStackTrace();
				}
			}
		}
		
		//*************************************
		//  メモ反映イベントリスナー
		//*************************************
		class memoChangeEvent implements ActionListener{
			
			//メンバ
			TableBase TableBase;
			
			//コンストラクタ
			public memoChangeEvent(TableBase tb){
				TableBase = tb;
			}
			
			public void actionPerformed(ActionEvent actionevent) {
				
				//メモ画面のテキストエリア内の文字列取得
				String resultStr = memoSubDisp.getMemoInputPanel().getMemoTextBox().getText().toString();
				
				//テーブル情報
				int col = memoSubDisp.getMemoInputPanel().getColumn();
				int row = 4;
				
				//キー項目取得
				MiddleCellEditor mceSeq = (MiddleCellEditor)ListHeader.getCellEditor(0, col);
				DefaultCellEditor dceSeq = (DefaultCellEditor)mceSeq.getTableCellEditor(0);
				CheckboxBase chkSeq = (CheckboxBase)dceSeq.getComponent();
				int intSeq  = Integer.parseInt(chkSeq.getPk1());
				
				
				//選択列のメモ欄に設定
				TableBase.setValueAt(resultStr ,row ,col );
				
				//データ設定
	    		try{
					DataCtrl.getInstance().getTrialTblData().UpdShisakuRetuMemo(
							intSeq,
							DataCtrl.getInstance().getTrialTblData().checkNullString(resultStr),
							DataCtrl.getInstance().getUserMstData().getDciUserid()
						);
				}catch(ExceptionBase e){
					e.printStackTrace();
				}
				
				//画面を非表示
				memoSubDisp.setVisible(false);
			}
			
		}
	}
}