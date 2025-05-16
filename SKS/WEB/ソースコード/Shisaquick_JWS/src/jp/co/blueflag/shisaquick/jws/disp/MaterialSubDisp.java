package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.panel.MaterialPanel;

/**
 * 
 * 【A02-03】 原料一覧サブ画面
 *  : 原料一覧サブ画面の操作を行う
 * 
 * @author k-katayama
 * @since 2009/04/03
 */
public class MaterialSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private MaterialPanel materialPanel;			//原料一覧パネルクラス

	private ExceptionBase ex;						//エラー操作クラス

	//終了ボタン押下時のイベント名
	private final String END_BTN_CLICK = "endBtnClick";

	/**
	 * コンストラクタ 
	 * @param strOutput : 画面タイトル
	 * @throws ExceptionBase 
	 */
	public MaterialSubDisp(String strOutput) throws ExceptionBase {
		//１．スーパークラスのコンストラクタを定義（画面タイトル設定）
		super(JwsConstManager.JWS_TITLE + "　"+strOutput);

		try {
			//２．画面の位置、サイズを指定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.setSize(900, 460);
			this.setSize(912, 469);
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.setLocationRelativeTo(null);
		
			//３．原料一覧パネルインスタンスを生成
			this.materialPanel = new MaterialPanel(strOutput);
			this.materialPanel.setEndEvent(this.getActionEvent(), this.END_BTN_CLICK);
			this.getContentPane().add(this.materialPanel);

			//４．画面を表示状態にする
			this.setVisible(false);

	        //フォーカス順設定用オブジェクトの格納
	        this.setFocusControl(this.materialPanel.getSetFocusComponent());
	        			
			//Windowイベントを設定
			this.addWindowListener(this.getWindowEvent());
			
		} catch (ExceptionBase e) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原料一覧サブ画面のコンストラクタが失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.toString());
			throw ex;
			
		} finally {
		}
		
	}

	/**
	 * ActionListener取得メソッド
	 *  : ボタン押下時及びコンボボックス選択時のイベントを設定する
	 * @return ActionListener 
	 * @throws ExceptionBase
	 */
	public ActionListener getActionEvent() throws ExceptionBase {
		ActionListener listener = null;
		try{
			listener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String event_name = e.getActionCommand();
						if ( event_name.equals(END_BTN_CLICK)) {
							//終了ボタン押下時
							materialPanel.clickEndBtn();
							
							//終了処理を行う
							Exit();
						}
					} catch (ExceptionBase eb) {
						DataCtrl.getInstance().PrintMessage(eb);
						
					} catch (Exception ec) {
						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("原料一覧サブ画面(Frame)の終了処理が失敗しました");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
						
					} finally {
					}
				}
			};

		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原料一覧サブ画面(Frame)　アクションイベント処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		
		return listener;
	}
	
	/**
	 * Windowイベント取得
	 * @return WindowListener
	 * @throws ExceptionBase
	 */
	public WindowListener getWindowEvent() throws ExceptionBase {
		WindowListener listener = null;
		try {
			listener = new WindowListener() {
				/**
				 * 画面終了イベント
				 */
				public void windowClosing(WindowEvent e) {
					try {
						materialPanel.clickEndBtn();
					} catch (ExceptionBase eb) {
						DataCtrl.getInstance().PrintMessage(eb);
					} catch (Exception ec) {
						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("原料一覧サブ画面(Frame)　画面終了イベント（Windowイベント）処理が失敗しました");
						ex.setStrErrShori(this.getClass().getName());
						ex.setStrMsgNo("");
						ex.setStrSystemMsg(ec.getMessage());
						DataCtrl.getInstance().PrintMessage(ex);
					} finally {
					}
				}	
				
				public void windowClosed(WindowEvent e) {}
				public void windowActivated(WindowEvent e) {}
				public void windowDeactivated(WindowEvent e) {}
				public void windowDeiconified(WindowEvent e) {}
				public void windowIconified(WindowEvent e) {}
				public void windowOpened(WindowEvent e) {}
			};

		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原料一覧サブ画面(Frame)　Windowイベント処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
		}
		return listener;
	}
	
	//パネルセッター&ゲッター
	public MaterialPanel getMaterialPanel() {
		return materialPanel;
	}
	public void setMaterialPanel(MaterialPanel materialPanel) {
		this.materialPanel = materialPanel;
	}
	
}