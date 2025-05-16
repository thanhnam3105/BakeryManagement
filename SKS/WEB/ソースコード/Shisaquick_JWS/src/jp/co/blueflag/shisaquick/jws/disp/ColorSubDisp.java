package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.panel.ColorPanel;

/**
 * 
 * 【A02-05】 色指定サブ画面
 *  : 色指定サブ画面の操作を行う
 * 
 * @author TT.katayama
 * @since 2009/04/05
 */
public class ColorSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private ColorPanel colorPanel;			//色指定パネルクラス
	private ExceptionBase ex;					//エラー操作クラス

	//終了ボタン押下時のイベント名
	private final String END_BTN_CLICK = "endBtnClick";

	/**
	 * コンストラクタ 
	 * @param strOutput : 画面タイトル
	 * @throws ExceptionBase 
	 */
	public ColorSubDisp(String strOutput) throws ExceptionBase {
		//１．スーパークラスのコンストラクタを定義（画面タイトル設定）
		super(JwsConstManager.JWS_TITLE + "　"+strOutput);

		try {
			//２．画面の位置、サイズを指定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.setSize(290, 220);
			this.setSize(302,229);
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.setLocationRelativeTo(null);
			
			//３．色指定パネルインスタンスを生成
			this.colorPanel = new ColorPanel(strOutput); 
			this.colorPanel.setEndEvent(this.getActionEvent(), this.END_BTN_CLICK);
			this.getContentPane().add(this.colorPanel);
			
			//４．画面を非表示状態にする
			this.setVisible(false);

		} catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("色指定サブ画面のコンストラクタが失敗しました。");
			this.ex.setStrErrShori("ColorSubDisp");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
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
//							colorPanel.getColorChooser().setColor(new Color(-1));
							//終了処理を行う
							Exit();
						}
					} catch (Exception ec) {
						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("色指定サブ画面(Frame)の終了処理が失敗しました");
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
			this.ex.setStrErrmsg("色指定サブ画面(Frame)　アクションイベント処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
		return listener;
	}
	
	public ColorPanel getColorPanel() {
		return colorPanel;
	}

	public void setColorPanel(ColorPanel colorPanel) {
		this.colorPanel = colorPanel;
	}
	
}