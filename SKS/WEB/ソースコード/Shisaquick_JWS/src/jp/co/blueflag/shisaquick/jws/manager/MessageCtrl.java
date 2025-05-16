package jp.co.blueflag.shisaquick.jws.manager;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JScrollBar;

import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.base.*;

/**
 * 
 * メッセージ操作
 *  : システム内にて扱うメッセージの操作を行う
 *  
 *  @author TT.katayama
 *  @since 2009/04/22
 *
 */
public class MessageCtrl extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	//OK（了解）ボタンクリック時のコマンド名称
	private final String OK_CLICK = "okClick";
	
	private PanelBase msgPanel;			//メッセージ配置パネル
	private LabelBase msgLabel;			//メッセージラベル
	
	private TabBase msgTab;				//メッセージタブ
	private ScrollBase[] tabScroll;		//タブ用スクロールパネル
	private TextAreaBase[] tabText;		//タブ用テキスト

	private ScrollBase msgScroll;			//メッセージスクロール
	private TextAreaBase msgText;		//メッセージテキスト
	private ButtonBase okButton;			//了解ボタン

	//2012/02/22 TT H.SHIMA Java6対応 start
//	private int dispWidth = 400;			//画面幅
	private int dispWidth = 412;
//	private int dispHeight = 400;			//画面高さ
	private int dispHeight = 412;
	//2012/02/22 TT H.SHIMA Java6対応 end
	private final int btnWidth = 80;		//ボタン幅
	private final int btnHeight = 40;		//ボタン高さ
	
	private String DebugLevel = "";
	
	private ExceptionBase ex;
	
	/**
	 * コンストラクタ
	 */
	public MessageCtrl() {
		//スーパークラスのコンストラクタ呼び出し
		super("メッセージボックス");
		
		try{
			final int MSG_COUNT = 3;
			
			///
			/// 画面の位置、サイズを指定
			///
			this.setSize(dispWidth, dispHeight);
			this.setLocationRelativeTo(null);
					
			///
			/// メッセージパネルの生成
			///
			this.msgPanel = new PanelBase();
			this.msgPanel.setLayout(null);
			this.msgPanel.setBounds(0, 0, dispWidth, dispHeight);

			///
			/// 了解ボタンの生成
			///
			this.okButton = new ButtonBase();
			this.msgPanel.setBounds(0, 0, dispWidth, dispHeight);
			//位置・サイズの設定
			this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight * 2),btnWidth,btnHeight);
			//表示テキストを設定
			this.okButton.setText("了解");	
			//クリックイベントを設定
			this.okButton.addActionListener(this.getActionEvent());
			this.okButton.setActionCommand(this.OK_CLICK);
			//パネルに追加
			this.msgPanel.add(this.okButton);

			///
			/// ラベルの初期化
			///
			this.msgLabel = new LabelBase();
			this.msgLabel.setBounds(5,10,dispWidth - 15,20);
			this.msgLabel.setText("");
			this.msgPanel.add(this.msgLabel);
			
			///
			/// メッセージタブの生成
			///
			this.msgTab = new TabBase();
			//位置・サイズの設定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.msgTab.setBounds(5,50,dispWidth - 15,this.okButton.getY() - 60);
			this.msgTab.setBounds(5,50,dispWidth - 27,this.okButton.getY() - 60);
			//2012/02/22 TT H.SHIMA Java6対応 end

			///
			/// タブ用 スクロールパネル、テキストの生成
			///
			this.tabScroll = new ScrollBase[MSG_COUNT];
			this.tabText = new TextAreaBase[MSG_COUNT];
			for( int i=0; i<MSG_COUNT; i++ ) {
				//テキストの生成
				this.tabText[i] = new TextAreaBase();
				this.tabText[i].setLineWrap(true);
				this.tabText[i].setEditable(false);
				this.tabText[i].setText("");
				this.tabText[i].setBackground(Color.WHITE);
				//スクロールパネルの生成
				this.tabScroll[i] = new ScrollBase(this.tabText[i]);
			}
			
			///
			/// スクロールパネルをタブに設定
			///
			this.msgTab.addTab("カテゴリ", this.tabScroll[0]);
			this.msgTab.addTab("エラーメッセージ", this.tabScroll[1]);
			this.msgTab.addTab("システムエラーメッセージ", this.tabScroll[2]);
			this.msgTab.setVisible(false);
			//パネルに追加
			this.msgPanel.add(this.msgTab);
			
			///
			/// メッセージ用 テキストの生成
			///
			this.msgText = new TextAreaBase();
			this.msgText.setLineWrap(true);
			this.msgText.setEditable(false);
			this.msgText.setText("");
			
			///
			/// メッセージ用 スクロールパネルの生成
			///
			this.msgScroll = new ScrollBase(this.msgText);
			this.msgScroll.setBounds(this.msgTab.getBounds());
			this.msgScroll.setVerticalScrollBarPolicy(ScrollBase.VERTICAL_SCROLLBAR_AS_NEEDED);
			this.msgScroll.setHorizontalScrollBarPolicy(ScrollBase.HORIZONTAL_SCROLLBAR_NEVER);
			this.msgScroll.setVisible(false);
			this.msgPanel.add(this.msgScroll);
			
			///
			/// フレームにパネルを設定
			///
			Container contentPane = this.getContentPane();
			contentPane.add (this.msgPanel);
			
			// フレームを非表示に設定
			this.setVisible(false);
						
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("メッセージ操作のコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			
			this.PrintErrMessage(ex);
		}

		
	}
	
	/**
	 * Resultメッセージ表示
	 *  : 設定されているメッセージをダイアログにて表示する
	 */
	public void PrintMessage(ResultData resultData) {
		//画面を非表示
		this.setVisible(false);
		
		//画面位置、サイズの再設定
		//2012/02/22 TT H.SHIMA Java6対応 start
//		this.dispWidth = 400;
		this.dispWidth = 412;
//		this.dispHeight = 400;
		this.dispHeight = 412;
		//2012/02/22 TT H.SHIMA Java6対応 end
		this.setSize(dispWidth, dispHeight);
		this.setLocationRelativeTo(null);

		//メッセージラベルの設定
		this.msgLabel.setText("実行結果");
		
		//デバッグレベル取得
		DebugLevel = resultData.getStrDebuglevel();
		
		//ボタンの位置・サイズの設定
		//2012/02/22 TT H.SHIMA Java6対応 start
//		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 30),btnWidth,btnHeight);
		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 42),btnWidth,btnHeight);
		//2012/02/22 TT H.SHIMA Java6対応 end
		
		//エラーメッセージ
		StringBuffer strErrMsg = new StringBuffer();
		strErrMsg.append(resultData.getStrErrorMsg().replaceAll("\\\\n", "\n"));
		
		//デバッグレベルが「0」の場合
		if(DebugLevel.equals("0")){
			
			//メッセージ表示
			PrintMessageString(strErrMsg.toString());
			
		}else{
			
			//非表示
			msgScroll.setVisible(false);
			msgTab.setVisible(false);
			
			msgTab.setSelectedIndex(1);
			
			//各テキストにメッセージを設定
			StringBuffer strCategory = new StringBuffer();
			
			//システムエラーメッセージ
			StringBuffer strSystemErr = new StringBuffer();
			strSystemErr.append(resultData.getStrSystemMsg().replaceAll("\\\\n", "\n"));
			
			strCategory.append("処理名称 : " + resultData.getStrClassNm());
			strCategory.append("\nエラーコード : " + resultData.getStrErrorCd());
			strCategory.append("\nエラーメッセージ番号 : " + resultData.getStrErrmsgNo());
			this.tabText[0].setText(strCategory.toString());
			this.tabText[1].setText(strErrMsg.toString());
			this.tabText[2].setText(strSystemErr.toString());
			
			//タブの位置・サイズの設定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.msgTab.setBounds(5,30,dispWidth - 15,this.okButton.getY() - 35);
			this.msgTab.setBounds(5,30,dispWidth - 27,this.okButton.getY() - 35);
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.msgTab.setVisible(true);
			
		}
		
		//画面を表示
		this.setVisible(true);
	}
	
	/**
	 * 共通エラーメッセージ表示
	 *  : BaseExceptionクラスよりエラー内容を取得し、メッセージをダイアログにて表示する
	 * @param printBexception : 共通エラークラス
	 */
	public void PrintErrMessage(ExceptionBase printBexception) {
		//画面を非表示
		this.setVisible(false);
		
		//画面位置、サイズの再設定
		//2012/02/22 TT H.SHIMA Java6対応 start
//		this.dispWidth = 400;
		this.dispWidth = 412;
//		this.dispHeight = 400;
		this.dispHeight = 412;
		//2012/02/22 TT H.SHIMA Java6対応 end
		this.setSize(dispWidth, dispHeight);
		this.setLocationRelativeTo(null);

		//メッセージラベルの設定
		this.msgLabel.setText("実行結果");

		//ボタンの位置・サイズの設定
		//2012/02/22 TT H.SHIMA Java6対応 start
//		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 30),btnWidth,btnHeight);
		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 42),btnWidth,btnHeight);
		//2012/02/22 TT H.SHIMA Java6対応 end
		
		//デバッグレベルが「0」の場合
		if(DebugLevel.equals("0") || DebugLevel.equals("")){
			
			//メッセージ表示
			PrintMessageString(printBexception.getStrErrmsg());
			
		}else{
			
			//非表示
			msgScroll.setVisible(false);
			msgTab.setVisible(false);
			
			msgTab.setSelectedIndex(1);
			
			//各テキストにメッセージを設定
			StringBuffer strCategory = new StringBuffer();
			strCategory.append("処理名称 : " + printBexception.getStrErrShori());
			strCategory.append("\nエラーコード : " + printBexception.getStrErrCd());
			strCategory.append("\nエラーメッセージ番号 : " + printBexception.getStrMsgNo());
			this.tabText[0].setText(strCategory.toString());
			this.tabText[1].setText(printBexception.getStrErrmsg());				//エラーメッセージ
			this.tabText[2].setText(printBexception.getStrSystemMsg());		//システムエラーメッセージ
			
			//タブの位置・サイズの設定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.msgTab.setBounds(5,30,dispWidth - 15,this.okButton.getY() - 35);
			this.msgTab.setBounds(5,30,dispWidth - 27,this.okButton.getY() - 35);
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.msgTab.setVisible(true);
			
		}
		
		//画面を表示
		this.setVisible(true);
	}
	
	/**
	 * 指定文字列表示メッセージボックス
	 *  : 指定文字列をメッセージボックスで表示する
	 */
	public void PrintMessageString(String strMessage) {
		//画面を非表示
		this.setVisible(false);
		
		//画面位置、サイズの再設定
		//2012/02/22 TT H.SHIMA Java6対応 start
//		this.dispWidth = 400;
		this.dispWidth = 412;
//		this.dispHeight = 200;
		this.dispHeight = 212;
		//2012/02/22 TT H.SHIMA Java6対応 start
		this.setSize(dispWidth, dispHeight);
		this.setLocationRelativeTo(null);

		//メッセージラベルの設定
		this.msgLabel.setText("実行結果");

		//ボタンの位置・サイズの設定
		//2012/02/22 TT H.SHIMA Java6対応 start
//		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 30),btnWidth,btnHeight);
		this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 42),btnWidth,btnHeight);
		//2012/02/22 TT H.SHIMA Java6対応 end
		
		//非表示
		msgScroll.setVisible(false);
		msgTab.setVisible(false);
		
		//テキストを表示
		this.msgText.setText(strMessage);
		this.msgText.setBackground(this.getBackground());
		
		//スクロールの位置・サイズの設定
		//2012/02/22 TT H.SHIMA Java6対応 start
//		this.msgScroll.setBounds(5,30,dispWidth - 15,this.okButton.getY() - 35);
		this.msgScroll.setBounds(5,30,dispWidth - 27,this.okButton.getY() - 35);
		//2012/02/22 TT H.SHIMA Java6対応 end
		this.msgScroll.setVisible(true);
				
		//画面を表示
		this.setVisible(true);
		
	}
	
	/**
	 * 分析原料確認データ表示メッセージボックス
	 *  : 分析原料確認データをメッセージボックスで表示する
	 * @throws ExceptionBase 
	 */
	public void PrintMessageGenryoCheck() throws ExceptionBase {
		
		try {
			//画面を非表示
			this.setVisible(false);
			
			//画面位置、サイズの再設定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.dispWidth = 400;
			this.dispWidth = 412;
//			this.dispHeight = 400;
			this.dispHeight = 412;
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.setSize(dispWidth, dispHeight);
			this.setLocationRelativeTo(null);
	
			//メッセージラベルの設定
			this.msgLabel.setText("分析原料確認データ確認");
	
			//ボタンの位置・サイズの設定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 30),btnWidth,btnHeight);
			this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 42),btnWidth,btnHeight);
			//2012/02/22 TT H.SHIMA Java6対応 end
			
			//非表示
			msgScroll.setVisible(false);
			msgTab.setVisible(false);
			
			//変更データ有無
			boolean hrnkouFg = false;
			
			//分析原料変更確認結果 表示用文字列
			StringBuffer strMessage = new StringBuffer();
			
			//原料データ
			ArrayList aryMateChkData = DataCtrl.getInstance().getMaterialMstData().getAryMateChkData();
			
			//配合データ
			ArrayList aryHaigoData = new ArrayList(DataCtrl.getInstance().getTrialTblData().SearchHaigoData(0));
			
			
			//変更されている原料データの値を取得
			for ( int i=0; i<aryMateChkData.size(); i++ ) {
				
				MaterialData mateData = (MaterialData)aryMateChkData.get(i);
				
				//配合データのキー項目と一致する値を設定
				for ( int j=0; j<aryHaigoData.size(); j++ ) {
				
					MixedData mixData = (MixedData)aryHaigoData.get(j); 
					
					String strNotGenryoMsg = "原料は存在しません"; 
					
					//コメント行の場合は処理しない
					if(ChkNull(mixData.getStrGenryoCd()).equals("999999")){
						
						
					}else{
						
						//同一のｷｰ項目の場合
						if ( mateData.getStrGenryocd().equals(mixData.getStrGenryoCd()) 
								&& mateData.getIntKaishacd() == mixData.getIntKaishaCd()
								&& mateData.getIntBushocd() == mixData.getIntBushoCd()) {
							
							//分析値の内容確認
							//分析値内容に変更がない場合
							if(mateData.getStrGenryonm().equals(mixData.getStrGenryoNm())
									&& ChkNull(mateData.getDciGanyu()).equals(ChkNull(mixData.getDciGanyuritu()))
									&& ChkNull(mateData.getDciSakusan()).equals(ChkNull(mixData.getDciSakusan()))
									&& ChkNull(mateData.getDciShokuen()).equals(ChkNull(mixData.getDciShokuen()))
									&& ChkNull(mateData.getDciSousan()).equals(ChkNull(mixData.getDciSosan()))
									//ADD start 20121031 QP@20505
									&& ChkNull(mateData.getDciMsg()).equals(ChkNull(mixData.getDciMsg()))
									//ADD end 20121031 QP@20505
								){
								
								//何もしない								
								
							//分析値内容に変更がある場合
							}else{
								
								hrnkouFg = true;
								
								//改行（2項目以上の）
								if ( strMessage.length() != 0 ) strMessage.append("\n");
								
								//原料が存在する場合
								if ( !mateData.getStrGenryonm().equals(strNotGenryoMsg) ) {
									
									strMessage.append("---------------------------------------------------------");
									strMessage.append("\n工程順：" + mixData.getIntKoteiNo());
									strMessage.append("\n原料コード：" + ChkNull(mateData.getStrGenryocd()) );
									strMessage.append("\n会社名：" + ChkNull(mateData.getStrKaishanm()) );
									strMessage.append("\n部署名：" + ChkNull(mateData.getStrBushonm()) );
									strMessage.append("\n---------------------------------------------------------");
									strMessage.append("\n■原料名：" + ChkTouroku(mixData.getStrGenryoNm()) + "　→　" + ChkTouroku(mateData.getStrGenryonm()) );
									strMessage.append("\n■油含有率：" + ChkTouroku(mixData.getDciGanyuritu()) + "　→　" + ChkTouroku(mateData.getDciGanyu()) );
									strMessage.append("\n■酢酸：" + ChkTouroku(mixData.getDciSakusan()) + "　→　" + ChkTouroku(mateData.getDciSakusan()) );
									strMessage.append("\n■食塩：" + ChkTouroku(mixData.getDciShokuen()) + "　→　" + ChkTouroku(mateData.getDciShokuen()) );
									strMessage.append("\n■総酸：" + ChkTouroku(mixData.getDciSosan()) + "　→　" + ChkTouroku(mateData.getDciSousan()) );
									//ADD start 20121031 QP@20505
									strMessage.append("\n■MSG：" + ChkTouroku(mixData.getDciMsg()) + "　→　" + ChkTouroku(mateData.getDciMsg()) );
									//ADD end 20121031 QP@20505
									
									strMessage.append("\n---------------------------------------------------------");
									
								//原料が存在しない場合
								} else {
									
									strMessage.append("---------------------------------------------------------");
									strMessage.append("\n工程順：" + mixData.getIntKoteiNo());
									strMessage.append("\n原料コード：" + ChkNull(mateData.getStrGenryocd()) );
									strMessage.append("\n会社名：" + ChkNull(mateData.getStrKaishanm()) );
									strMessage.append("\n部署名：" + ChkNull(mateData.getStrBushonm()) );
									strMessage.append("\n---------------------------------------------------------");
									strMessage.append("\n■原料名：" + ChkTouroku(mixData.getStrGenryoNm()) + "　→　" + ChkTouroku(strNotGenryoMsg));
									strMessage.append("\n■油含有率：" + ChkTouroku(mixData.getDciGanyuritu()) + "　→　" + ChkTouroku(strNotGenryoMsg));
									strMessage.append("\n■酢酸：" + ChkTouroku(mixData.getDciSakusan()) + "　→　" + ChkTouroku(strNotGenryoMsg));
									strMessage.append("\n■食塩：" + ChkTouroku(mixData.getDciShokuen()) + "　→　" + ChkTouroku(strNotGenryoMsg));
									strMessage.append("\n■総酸：" + ChkTouroku(mixData.getDciSosan()) + "　→　" + ChkTouroku(strNotGenryoMsg));
									//ADD start 20121031 QP@20505
									strMessage.append("\n■MSG：" + ChkTouroku(mixData.getDciMsg()) + "　→　" + ChkTouroku(mateData.getDciMsg()) );
									//ADD end 20121031 QP@20505
									
									strMessage.append("\n---------------------------------------------------------");
									
								}
								
								//処理が行われたデータを初期化する(多重比較を阻止するため)
								aryHaigoData.set(j, new MixedData());
								
							}
						}
					}
				}
			}
			
			
			//変更されている原料データがある場合
			if(hrnkouFg){
				
				
			//変更されている原料データがない場合
			}else{
				
				strMessage.append("変更されている原料はありません");
				
			}
			
			//メッセージを設定
			this.msgText.setText(strMessage.toString());
			this.msgText.setBackground(Color.WHITE);
			
			//スクロールの位置・サイズの設定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.msgScroll.setBounds(5,30,dispWidth - 15,this.okButton.getY() - 35);
			this.msgScroll.setBounds(5,30,dispWidth - 27,this.okButton.getY() - 35);
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.msgScroll.setVisible(true);
			
			//画面を表示
			this.setVisible(true);
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			//エラー設定
			ExceptionBase ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("分析原料確認データ表示メッセージボックス処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/**
	 * 試算履歴参照データ表示メッセージボックス
	 *  : 試算履歴参照データをメッセージボックスで表示する
	 * @throws ExceptionBase 
	 */
	public void PrintMessageShisanRirekiSansyo() throws ExceptionBase {
		try {
			//画面を非表示
			this.setVisible(false);
			
			//画面位置、サイズの再設定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.dispWidth = 400;
			this.dispWidth = 412;
//			this.dispHeight = 400;
			this.dispHeight = 412;
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.setSize(dispWidth, dispHeight);
			this.setLocationRelativeTo(null);
	
			//メッセージラベルの設定
			this.msgLabel.setText("試算履歴参照");
	
			//ボタンの位置・サイズの設定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 30),btnWidth,btnHeight);
			this.okButton.setBounds((dispWidth/2) - (btnWidth/2),dispHeight - (btnHeight + 42),btnWidth,btnHeight);
			//2012/02/22 TT H.SHIMA Java6対応 end
			
			//非表示
			msgScroll.setVisible(false);
			msgTab.setVisible(false);
			
			//表示用文字列
			StringBuffer strMessage = new StringBuffer();
			
			//データの表示
			strMessage.append("---------------------------------------------------------");
			strMessage.append("\n試算日     \t ： サンプルNO");
			strMessage.append("\n---------------------------------------------------------");
			
			//参照データを表示する
			ArrayList aryShisanRireki = DataCtrl.getInstance().getShisanRirekiKanriData().getAryShisanRirekiData();
			for ( int i=0; i<aryShisanRireki.size(); i++ ) {
				ShisanData shisanRirekiData = (ShisanData)aryShisanRireki.get(i);  

				strMessage.append("\n");
				String strShisanHi = shisanRirekiData.getStrShisanHi();
				String strSampleNo = shisanRirekiData.getStrSampleNo();
				strMessage.append(strShisanHi + "\t ： " + ChkTouroku(strSampleNo));
				
			}
			
			strMessage.append("\n---------------------------------------------------------");

			this.msgText.setText(strMessage.toString());
			this.msgText.setBackground(Color.WHITE);
			
			//スクロールの位置・サイズの設定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.msgScroll.setBounds(5,30,dispWidth - 15,this.okButton.getY() - 35);
			this.msgScroll.setBounds(5,30,dispWidth - 27,this.okButton.getY() - 35);
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.msgScroll.setVisible(true);
			
			//画面を表示
			this.setVisible(true);
			
		} catch (Exception e) {
			//エラー設定
			ExceptionBase ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試算履歴参照データ表示メッセージボックス処理が失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}
		
	/**
	 * Actionイベント取得
	 * @return ActionListener
	 */
	private ActionListener getActionEvent() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//OK(了解)ボタンクリック時
				if ( e.getActionCommand().equals(OK_CLICK) ) {
					msgText.setBackground(Color.WHITE);
					Exit();
				}
			}
		};
	}
	
	/**
	 * Nullチェック
	 *  : Nullの場合は、空を返す
	 * @param strValue : チェックする値
	 * @return チェック後の値
	 */
	private String ChkNull(Object strValue) {
		String ret = "";
		
		if ( strValue != null ) {
			ret = strValue.toString();
			
		}	
		return ret;
		
	}

	/**
	 * 未登録チェック(String)
	 *  : Nullの場合は、空を返す
	 * @param strValue : チェックする値
	 * @return チェック後の値
	 */
	private String ChkTouroku(String strValue) {
		String ret = "未登録";
		
		if ( strValue != null ) {
			ret = strValue;
			
		}	
		return ret;
		
	}
	
	/**
	 * 未登録チェック(BigDecimal)
	 *  : Nullの場合は、空を返す
	 * @param strValue : チェックする値
	 * @return チェック後の値
	 */
	private String ChkTouroku(BigDecimal dciValue) {
		String ret = "未登録";
		
		if ( dciValue != null ) {
			ret = dciValue.toString();
			
		}	
		return ret;
		
	}

	/**
	 * デバッグレベルゲッター＆セッター
	 */
	public String getDebugLevel() {
		return DebugLevel;
	}

	public void setDebugLevel(String debugLevel) {
		DebugLevel = debugLevel;
	}
	
	
}
