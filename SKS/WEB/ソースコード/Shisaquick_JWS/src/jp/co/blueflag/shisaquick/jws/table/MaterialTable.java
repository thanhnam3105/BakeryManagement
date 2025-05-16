package jp.co.blueflag.shisaquick.jws.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import jp.co.blueflag.shisaquick.jws.base.BushoData;
import jp.co.blueflag.shisaquick.jws.base.MaterialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.cellrenderer.MiddleCellRenderer;
import jp.co.blueflag.shisaquick.jws.cellrenderer.TextFieldCellRenderer;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.ScrollBase;
import jp.co.blueflag.shisaquick.jws.common.TableBase;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;
import jp.co.blueflag.shisaquick.jws.textbox.NumelicTextbox;

/**
 * 
 * 原料一覧テーブルクラス
 *  : 原料一覧テーブルコントロールを設定する
 * 
 * @author TT.katayama
 * @since 2009/04/03
 *
 */
public class MaterialTable {

	private ExceptionBase ex = null;
	
	private TableBase headerTable;			//ヘッダーテーブル
	private TableBase mainTable;				//メインテーブル
	private ScrollBase scroll;					//スクロールパネル
	
	private BushoData BushoData = new BushoData();

	//退避用フィールド
	private int intTaihiKaishaCd = 0;
	private int intTaihiBushoCd = 0;
	private String strTaihiGenryoCd = "";
	private int intTaihiRowId = 0;
	
	private ArrayList lstKaishaCd = null;
	private ArrayList lstBushoCd = null;
	private XmlData xmlJW620;						//ＸＭＬデータ保持(JW620)
	
	//ヘッダー幅
	private static final int HEADER_HEIGHT = 48;
	//ヘッダーフォントサイズ
	private static final int HEADER_FONT_SIZE = 12;
	//行高さ
	private static final int COLUMN_HEIGHT = 16;
	//テーブルフォントサイズ
	private static final int COLUMN_FONT_SIZE = 12;
		
	/**
	 * コンストラクタ(行・列 指定)
	 * @throws ExceptionBase 
	 */
	public MaterialTable() throws ExceptionBase{
		
		try {
			//キユーピー部署データ取得
			conJW620(JwsConstManager.JWS_CD_KEWPIE);
			
			//テーブルのインスタンス生成
			this.mainTable = new TableBase(0,1) {
				private static final long serialVersionUID = 1L;
				/**
				 * セル編集不可
				 */
				public boolean isCellEditable(int row, int column) {
				    return false;
				    
				}
				
			};
			this.headerTable = new TableBase(1,10) {
				private static final long serialVersionUID = 1L;
				/**
				 * セル編集不可
				 */
				public boolean isCellEditable(int row, int column) {
				    return false;
				    
				}
				
			};
			
			//
			this.mainTable.setCellSelectionEnabled( false );
			this.headerTable.setCellSelectionEnabled( false );
			//自動リサイズをOFFに設定
			this.mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			this.headerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			this.headerTable.setEnabled(false);

			//行選択の設定
			this.mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.mainTable.setRowSelectionAllowed(true);
			this.mainTable.setSelectionBackground(JwsConstManager.TABLE_SELECTED_COLOR);
			
			//フォーカス設定
			this.mainTable.setTabFocusControl(null);
			
			//ヘッダーテーブルの色設定
			this.headerTable.setBackground(JwsConstManager.SHISAKU_ITEM_COLOR);
			
			//スクロールパネルのインスタンス生成
			this.scroll = new ScrollBase( this.mainTable ) {
				private static final long serialVersionUID = 1L;

				//ヘッダーを消去
				public void setColumnHeaderView(Component view) {} 
				
			};

			//スクロールパネルの設定
			this.scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			this.scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			this.scroll.setBackground(Color.WHITE);
			this.scroll.setBorder(new LineBorder(Color.BLACK, 1));
			this.scroll.setBackground(Color.WHITE);
			
			//ビューポートの設定
			JViewport headerViewport = new JViewport();
			headerViewport.setView(this.headerTable);
			headerViewport.setPreferredSize( new Dimension(this.headerTable.getPreferredSize().width, HEADER_HEIGHT));
			headerViewport.setSize(this.scroll.getWidth(), 32);
			this.scroll.setColumnHeader(headerViewport);
			
		} catch( Exception e ) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原料一覧テーブル初期化処理が失敗しました。");
			this.ex.setStrErrShori("MaterialTable");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * テーブルのクリア
	 */
	public void clearTable() {
		//行項目の全削除
		this.deleteRowAll(this.headerTable);
		this.deleteRowAll(this.mainTable);
		
		//列項目の全削除
		this.deleteColumnAll(this.headerTable);
		this.deleteColumnAll(this.mainTable);

		//会社コード・部署コードリストの開放
		this.lstKaishaCd = null;
		this.lstBushoCd = null;
		
	}
	
	/**
	 * 列項目の設定(全工場ではない場合)
	 */
	public void initTableNotZenkojo() {
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
		//int intColumnCount = 13;				//列数
		int intColumnCount = 14;				//列数
		Object[] objColumnNm = new Object[intColumnCount];		//列名
		int[] intColumnWidth = new int[intColumnCount];				//列幅

		////列名の設定
		//objColumnNm[0] = "";
		//objColumnNm[1] = setHtmlHeaderCenter("原料<br>コード");
		//objColumnNm[2] = "原料名";
		//objColumnNm[3] = "工場名";
		//objColumnNm[4] = "単価";
		//objColumnNm[5] = "歩留１";
		//objColumnNm[6] = setHtmlHeaderCenter("酢酸<br>（%）");
		//objColumnNm[7] = setHtmlHeaderCenter("食塩<br>（%）");
		//objColumnNm[8] = setHtmlHeaderCenter("総酸<br>（%）");
		//objColumnNm[9] = setHtmlHeaderCenter("油含有率<br>（%）");
		//objColumnNm[10] = "メモ";
		//objColumnNm[11] = "廃止";
		//objColumnNm[12] = setHtmlHeaderCenter("確定<br>コード");

		//列幅の設定
		//intColumnWidth[0] = 30;		//連番
		//intColumnWidth[1] = 80;	//原料コード
		//intColumnWidth[2] = 250;	//原料名
		//intColumnWidth[3] = 100;	//原料名
		//intColumnWidth[4] = 80;	//単価
		//intColumnWidth[5] = 50;	//歩留１
		//intColumnWidth[6] = 48;	//酢酸
		//intColumnWidth[7] = 48;	//食塩
		//intColumnWidth[8] = 48;	//総酸
		//intColumnWidth[9] = 58;	//油含有率
		//intColumnWidth[10] = 200;	//メモ
		//intColumnWidth[11] = 55;	//廃止
		//intColumnWidth[12] = 50;	//確定コード

		//列名の設定
		objColumnNm[0] = setHtmlHeaderCenter("原料<br>コード");
		objColumnNm[1] = "原料名";
		objColumnNm[2] = "工場名";
		objColumnNm[3] = "単価";
		// 使用実績
		// レイアウトは２文字のみ表示可のため、先頭２文字のみ表示させるようにする
		String strShiyoJiseki = JwsConstManager.JWS_NM_SHIYO;
		String strOutShiyoJiseki = strShiyoJiseki.substring(0, 1);
		strOutShiyoJiseki += "<br>" + strShiyoJiseki.substring(1, 2);
		objColumnNm[4] = setHtmlHeaderCenter(strOutShiyoJiseki);
		objColumnNm[5] = setHtmlHeaderCenter("未<br>使");
		objColumnNm[6] = "歩留１";
		objColumnNm[7] = setHtmlHeaderCenter("酢酸<br>（%）");
		objColumnNm[8] = setHtmlHeaderCenter("食塩<br>（%）");
		objColumnNm[9] = setHtmlHeaderCenter("総酸<br>（%）");
		objColumnNm[10] = setHtmlHeaderCenter("油含有率<br>（%）");
		objColumnNm[11] = "メモ";
		objColumnNm[12] = "廃止";
		objColumnNm[13] = setHtmlHeaderCenter("確定<br>コード");

		//列幅の設定
		intColumnWidth[0] = 80;	//原料コード
		intColumnWidth[1] = 250;	//原料名
		intColumnWidth[2] = 100;	//原料名
		intColumnWidth[3] = 80;	//単価
		intColumnWidth[4] = 20;		//三ヶ月
		intColumnWidth[5] = 20;		//未使用フラグ
		intColumnWidth[6] = 50;	//歩留１
		intColumnWidth[7] = 48;	//酢酸
		intColumnWidth[8] = 48;	//食塩
		intColumnWidth[9] = 48;	//総酸
		intColumnWidth[10] = 58;	//油含有率
		intColumnWidth[11] = 200;	//メモ
		intColumnWidth[12] = 55;	//廃止
		intColumnWidth[13] = 50;	//確定コード
//add end --------------------------------------------------------------------------------------
		
		this.initTable(intColumnCount, intColumnWidth, objColumnNm);
		
		//会社コード・部署コードの初期化
		if ( this.lstKaishaCd == null ) {
			this.lstKaishaCd = new ArrayList();
		}
		if ( this.lstBushoCd == null ) {
			this.lstBushoCd = new ArrayList();
		}
		this.lstKaishaCd.clear();
		this.lstBushoCd.clear();
		
	}
	
	/**
	 * 列項目の設定(全工場の場合)
	 */
	public void initTableZenkojo() {
		int i;
		String bushoNm = "";
		ArrayList objColumnNm = new ArrayList();			//列名
		
		//部署マスタ内の項目数
		int intBushoCount = BushoData.getAryBushoNm().size();
		
		//列名の設定
		int j =0;
		objColumnNm.add("");
		objColumnNm.add(setHtmlHeaderCenter("原料<br>コード"));
		objColumnNm.add("原料名");
		
		//部署名分の列項目(単価)を設定
		for( i=0; i<intBushoCount; i++ ) {
			bushoNm = BushoData.getAryBushoNm().get(i).toString();
			objColumnNm.add(setHtmlHeaderTable(bushoNm.replaceAll("工場", ""),60,48));
//			objColumnNm.add(setHtmlHeader("<span valign='top'>" + bushoNm.replaceAll("工場", "") + ""));
		}
		//部署名分の列項目(歩留)を設定
		for( i=0; i<intBushoCount; i++ ) {
			bushoNm = BushoData.getAryBushoNm().get(i).toString();
			objColumnNm.add(setHtmlHeaderTable(bushoNm.replaceAll("工場", "") + "歩留1",50,48));
		}
		objColumnNm.add(setHtmlHeaderCenter("酢酸<br>（%）"));
		objColumnNm.add(setHtmlHeaderCenter("食塩<br>（%）"));
		objColumnNm.add(setHtmlHeaderCenter("総酸<br>（%）"));
		objColumnNm.add(setHtmlHeaderCenter("油含有率<br>（%）"));
		objColumnNm.add("メモ");
		objColumnNm.add("廃止");
		objColumnNm.add(setHtmlHeaderCenter("確定<br>コード"));

		//列幅の設定
		int[] intColumnWidth = new int[objColumnNm.size()];		//列幅
		intColumnWidth[0] = 30;		//連番
		intColumnWidth[1] = 80;		//原料コード
		intColumnWidth[2] = 250;		//原料名
		for( i=0; i<intBushoCount; i++ ) {
			intColumnWidth[3+i] = 80;							//単価
			intColumnWidth[3+i+intBushoCount] = 50;		//歩留
		}
		j=3+intBushoCount+intBushoCount;
		intColumnWidth[j] = 48;		//酢酸
		j=4+intBushoCount+intBushoCount;
		intColumnWidth[j] = 48;		//食塩
		j=5+intBushoCount+intBushoCount;
		intColumnWidth[j] = 48;		//総酸
		j=6+intBushoCount+intBushoCount;
		intColumnWidth[j] = 58;		//油含有率
		j=7+intBushoCount+intBushoCount;
		intColumnWidth[j] = 200;		//メモ
		j=8+intBushoCount+intBushoCount;
		intColumnWidth[j] = 55;		//廃止
		j=9+intBushoCount+intBushoCount;
		intColumnWidth[j] = 50;		//確定コード
		
		//テーブルを初期化
		this.initTable(objColumnNm.size(), intColumnWidth, objColumnNm.toArray());
		
		this.strTaihiGenryoCd = null;
		this.intTaihiBushoCd = 0;
		this.intTaihiKaishaCd = 0;
		this.intTaihiRowId = 0;

		//会社コード・部署コードの初期化
		if ( this.lstKaishaCd == null ) {
			this.lstKaishaCd = new ArrayList();
		}
		if ( this.lstBushoCd == null ) {
			this.lstBushoCd = new ArrayList();
		}
		this.lstKaishaCd.clear();
		this.lstBushoCd.clear();
		
	}
	
	/**
	 * HTMLヘッダー項目設定
	 * @param strParam : 項目値
	 * @return 設定後項目値
	 */
	private String setHtmlHeader(String strParam) {
		
		return "<html>" + strParam + "</html>";
		
	}

	/**
	 * HTMLヘッダー項目設定(中央揃え)
	 * @param strParam : 項目値
	 * @return 設定後項目値
	 */
	private String setHtmlHeaderCenter(String strParam) {
		
		return this.setHtmlHeader("<center>" + strParam + "</center>");
		
	}

	/**
	 * HTMLヘッダー項目設定(テーブル)
	 * @param strParam : 項目値
	 * @return 設定後項目値
	 */
	private String setHtmlHeaderTable(String strParam, int intWidth, int intHeight) {
		
		return this.setHtmlHeader("<table width='" + intWidth + "' height='" + intHeight + "'><tr><td>" + strParam + "</td></tr></table>");
		
	}

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	/**
	 * 原料一覧 行追加処理(全工場ではない時)
	 * @param intRowId : 行番号
	 * @param materialData : 原料データ保持クラス
	 * @param listener : イベント設定クラス
	 */
	//public void insertMainTableNotZenkojo(int intRowId, MaterialData materialData, MiddleCellRenderer md,MiddleCellRenderer mdn) {
	public void insertMainTableNotZenkojo(int intRowId, MaterialData materialData, MiddleCellRenderer md,MiddleCellRenderer mdn,MiddleCellRenderer mdc) {
		int j=0;
		int intGenryocdNstartFlg = 0;

		this.mainTable.tableInsertRow(intRowId);
		//this.mainTable.setValueAt("" + (intRowId+1), intRowId, j++);	//連番
		
		this.mainTable.setValueAt(materialData.getStrGenryocd(), intRowId, j++);		//原料コード
		//原料コード先頭１文字Nチェック
		if(!materialData.getStrGenryocd().equals("")) {
			if(materialData.getStrGenryocd().charAt(0) == 'N'){
				intGenryocdNstartFlg = 1;
			}
		}
		
		this.mainTable.setValueAt(materialData.getStrGenryonm(), intRowId, j++);		//原料名
		this.mainTable.setValueAt(materialData.getStrBushonm(), intRowId, j++);		//工場名
		this.mainTable.setValueAt(materialData.getDciTanka(), intRowId, j++);			//単価
		//使用実績フラグ
		String strSankazetuNm = "";
		if(intGenryocdNstartFlg == 1 ) {
			//先頭N文字の場合
			strSankazetuNm = "-";
			
		} else if (materialData.getIntShiyoFlg() == 1) {
			//使用実績フラグ=1
			strSankazetuNm = "○";
			
		} else {
			//使用実績フラグ=0
			strSankazetuNm = "×";
			
		}
		this.mainTable.setValueAt(strSankazetuNm, intRowId, j++);
		
		//未使用フラグ
		String strMishiyouNm = "";
		if(intGenryocdNstartFlg == 1 ) {
			//先頭N文字の場合
			strMishiyouNm = "";
			
		} else if (materialData.getIntMishiyoFlg() == 1) {
			//使用実績フラグ=1
			strMishiyouNm = "○";
			
		} else {
			//使用実績フラグ=0
			strMishiyouNm = "";
			
		}
		this.mainTable.setValueAt(strMishiyouNm, intRowId, j++);
//add end --------------------------------------------------------------------------------------

		this.mainTable.setValueAt(materialData.getDciBudomari(), intRowId, j++);		//歩留
		this.mainTable.setValueAt(materialData.getDciSakusan(), intRowId, j++);		//酢酸
		this.mainTable.setValueAt(materialData.getDciShokuen(), intRowId, j++);		//食塩
		this.mainTable.setValueAt(materialData.getDciSousan(), intRowId, j++);			//総酸
		this.mainTable.setValueAt(materialData.getDciGanyu(), intRowId, j++);			//油含有率
		this.mainTable.setValueAt(materialData.getStrMemo(), intRowId, j++);			//メモ
		String strHaishiNm = (materialData.getIntHaisicd() == 1)?"廃止":"使用可能";
		this.mainTable.setValueAt(strHaishiNm, intRowId, j++);								//廃止
		this.mainTable.setValueAt(materialData.getStrkakuteicd(), intRowId, j++);		//確定コード
		
		this.lstKaishaCd.add(new Integer(materialData.getIntKaishacd()));				//会社コード
		this.lstBushoCd.add(new Integer(materialData.getIntBushocd()));;					//部署コード
		
		//レンダラ設定（通常）
		TextboxBase comp = new TextboxBase();
		comp.setFont(new Font("Default", Font.PLAIN, COLUMN_FONT_SIZE));
		if(materialData.getIntHaisicd() == 1){
			comp.setBackground(Color.LIGHT_GRAY);
			
		}else{
			comp.setBackground(Color.WHITE);
			
		}
		md.add(intRowId, new TextFieldCellRenderer(comp));
		
		//レンダラ設定（数値）
		NumelicTextbox ncomp = new NumelicTextbox();
		ncomp.setFont(new Font("Default", Font.PLAIN, COLUMN_FONT_SIZE));
		if(materialData.getIntHaisicd() == 1){
			ncomp.setBackground(Color.LIGHT_GRAY);
			
		}else{
			ncomp.setBackground(Color.WHITE);
			
		}
		mdn.add(intRowId, new TextFieldCellRenderer(ncomp));

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
		////レンダラ設定（通常）
		TextboxBase compc = new TextboxBase();
		compc.setFont(new Font("Default", Font.PLAIN, 14));
		compc.setHorizontalAlignment(SwingConstants.CENTER);
		if(materialData.getIntHaisicd() == 1){
			compc.setBackground(Color.LIGHT_GRAY);	
		}else{
			compc.setBackground(Color.WHITE);
		}
		mdc.add(intRowId, new TextFieldCellRenderer(compc));
//add end --------------------------------------------------------------------------------------
	}
		
	/**
	 * 原料一覧 行追加処理(全工場時)
	 * @param intRowId : 行番号
	 * @param materialData : 原料データ保持クラス
	 * @param listener : イベント設定クラス
	 */
	public void insertMainTableZenkojo(MaterialData materialData, MiddleCellRenderer md,MiddleCellRenderer mdn) {
		
		//部署マスタ内の項目数
		int intBushoCount = BushoData.getAryBushoNm().size();


		//検索結果と保持していた会社コード・部署コードを比較し、同一でないのならば、行を追加
		if ( materialData.getIntKaishacd() != this.intTaihiKaishaCd || materialData.getIntBushocd() != this.intTaihiBushoCd
				|| !materialData.getStrGenryocd().equals(this.strTaihiGenryoCd) ) {
			//テーブル行数設定
			intTaihiRowId = this.mainTable.getRowCount();
			//テーブル行追加
			this.mainTable.tableInsertRow(intTaihiRowId);
			//値の設定
			this.mainTable.setValueAt("" + (intTaihiRowId+1), intTaihiRowId, 0);							//連番
			this.mainTable.setValueAt(materialData.getStrGenryocd(), intTaihiRowId, 1);			//原料コード
			this.mainTable.setValueAt(materialData.getStrGenryonm(), intTaihiRowId, 2);			//原料名
			this.mainTable.setValueAt(materialData.getDciSakusan(), intTaihiRowId, 3 + intBushoCount + intBushoCount);		//酢酸
			this.mainTable.setValueAt(materialData.getDciShokuen(), intTaihiRowId, 4 + intBushoCount + intBushoCount);		//食塩
			this.mainTable.setValueAt(materialData.getDciSousan(), intTaihiRowId, 5 + intBushoCount + intBushoCount);		//総酸
			this.mainTable.setValueAt(materialData.getDciGanyu(), intTaihiRowId, 6 + intBushoCount + intBushoCount);			//油含有率
			this.mainTable.setValueAt(materialData.getStrMemo(), intTaihiRowId, 7 + intBushoCount + intBushoCount);			//メモ
			String strHaishiNm = (materialData.getIntHaisicd() == 1)?"廃止":"使用可能";
			this.mainTable.setValueAt(strHaishiNm, intTaihiRowId, 8 + intBushoCount + intBushoCount);							//廃止
			this.mainTable.setValueAt(materialData.getStrkakuteicd(), intTaihiRowId, 9 + intBushoCount + intBushoCount);		//確定コード
			
			//コードを退避
			this.intTaihiKaishaCd = materialData.getIntKaishacd();
			this.intTaihiBushoCd = materialData.getIntBushocd();
			this.strTaihiGenryoCd = materialData.getStrGenryocd();

			this.lstKaishaCd.add(new Integer(materialData.getIntKaishacd()));				//会社コード
			this.lstBushoCd.add(new Integer(materialData.getIntBushocd()));;					//部署コード
			
			//レンダラ設定
			TextboxBase comp = new TextboxBase();
			comp.setFont(new Font("Default", Font.PLAIN, COLUMN_FONT_SIZE));
			if(materialData.getIntHaisicd() == 1){
				comp.setBackground(Color.LIGHT_GRAY);
			}else{
				comp.setBackground(Color.WHITE);
			}
			TextFieldCellRenderer rendComp = new TextFieldCellRenderer(comp);
			md.add(intTaihiRowId, rendComp);
			
			//レンダラ設定（数値）
			NumelicTextbox ncomp = new NumelicTextbox();
			ncomp.setFont(new Font("Default", Font.PLAIN, COLUMN_FONT_SIZE));
			if(materialData.getIntHaisicd() == 1){
				ncomp.setBackground(Color.LIGHT_GRAY);
				
			}else{
				ncomp.setBackground(Color.WHITE);
				
			}
			mdn.add(intTaihiRowId, new TextFieldCellRenderer(ncomp));
			
		}

		//全部署分の単価及び歩留を設定
		for ( int i=0; i<intBushoCount; i++ ) {
			String cmbBushoNm = BushoData.getAryBushoNm().get(i).toString(); 
			if ( cmbBushoNm.equals(materialData.getStrBushonm()) ) {
				this.mainTable.setValueAt(materialData.getDciTanka(), intTaihiRowId, i + 3 );							//単価
				this.mainTable.setValueAt(materialData.getDciBudomari(), intTaihiRowId, i + 3 + intBushoCount );	//歩留

				break;
			}
		}

	}

	/**
	 * 全行項目削除処理
	 */
	public void deleteRowAll(TableBase table) {
		if ( table.getRowCount() > 0 ) {
			for ( int i=table.getRowCount()-1; i>-1; i-- ) {
				table.tableDeleteRow(i);
			}
		}
	}

	/**
	 * 全列項目削除処理
	 * @param table
	 */
	private void deleteColumnAll(TableBase table) {
		if ( table.getColumnCount() > 0 ) {
			for( int i=table.getColumnCount()-1; i>-1; i--  ) {
				table.tableDeleteColumn(i);
			}
		}
	}
	
	/**
	 * 列名の初期化
	 * @param columnNm : 列名格納配列
	 */
	private void setColumnName(Object[] columnNm) {
		for (int i=0; i<columnNm.length; i++ ) {
			this.headerTable.setValueAt(columnNm[i], 0, i);
		}
	}
	
	/**
	 * 列のサイズの初期化
	 * @param table : 対象テーブル
	 * @param columnWidth : 列幅格納配列
	 * @param rowHeight : 列高さ格納配列
	 */
	private void setColumnSize(TableBase table, int[] columnWidth, int rowHeight) {		
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column;
		
		//列幅を設定していく
		for ( int i=0; i<table.getColumnCount(); i++ ) {
			//中央揃え設定用セルレンダラー
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();
			render.setHorizontalAlignment(JLabel.CENTER);
			
			//カラムの設定
			column = columnModel.getColumn(i);
			column.setCellRenderer(render);
			column.setPreferredWidth(columnWidth[i]);
			table.setRowHeight(rowHeight);
			table.setFont(new Font("Default", Font.PLAIN, HEADER_FONT_SIZE));		//ヘッダーのフォント
			
		}
		
	}
	
	/**
	 * テーブル初期化処理
	 * @param intColumnCount
	 * @param table
	 */
	private void initTable(int intColumnCount, int[] intColumnWidth, Object[] objColumnNm) {
		
		//テーブルのクリア
		this.clearTable();

		//列を追加
		for ( int i=0; i<intColumnCount; i++ ) {
			this.headerTable.tableInsertColumn(i);
			this.mainTable.tableInsertColumn(i);
		}
		
		//ヘッダー行を追加
		this.headerTable.tableInsertRow(0);

		//列名の設定
		this.setColumnName(objColumnNm);
		//列幅の設定
		this.setColumnSize(this.mainTable, intColumnWidth, COLUMN_HEIGHT);
		this.setColumnSize(this.headerTable, intColumnWidth, HEADER_HEIGHT);
		
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
			e.printStackTrace();
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
	 * テーブル内の指定会社コードを取得する
	 * @param index
	 * @return 指定会社コード
	 */
	public String getTableKaishaCd(int index) {
		return this.lstKaishaCd.get(index).toString();
	}
	
	/**
	 * テーブル内の指定部署コードを取得する
	 * @param index
	 * @return 指定部署コード
	 */
	public String getTableBushoCd(int index) {
		return this.lstBushoCd.get(index).toString();
	}
	
	/**
	 * スクロールパネル　ゲッター
	 * @return Scrollパネル 
	 */
	public ScrollBase getScroll() {
		return this.scroll;
	}
	
	/**
	 * メインテーブル ゲッター
	 * @return メインテーブル
	 */
	public TableBase getMainTable() {
		return this.mainTable;
	}
	
	/**
	 * サブテーブル ゲッター
	 * @return サブテーブル
	 */
	public TableBase getHeaderTable() {
		return this.headerTable;
	}
	
}