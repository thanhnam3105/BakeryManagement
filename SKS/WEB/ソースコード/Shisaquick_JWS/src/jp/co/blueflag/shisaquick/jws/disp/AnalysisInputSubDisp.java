package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.panel.AnalysisInputPanel;

/**
 * 
 * 【A02-03】 分析値入力画面
 *  : 分析値入力画面の操作を行う
 * 
 * @author TT.katayama
 * @since 2009/04/03
 */
public class AnalysisInputSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private AnalysisInputPanel analysisInputPanel;

	private ExceptionBase ex;

	/**
	 * コンストラクタ 
	 * @param strOutput : 画面タイトル
	 */
	public AnalysisInputSubDisp(String strOutput) throws ExceptionBase {
		//１．スーパークラスのコンストラクタを定義（画面タイトル設定）
		super(JwsConstManager.JWS_TITLE + "　"+strOutput);
		
		try {
			//２．画面の位置、サイズを指定
// MOD start 20121126 QP@20505 課題No.10 MSG追加のため
//			//2012/02/22 TT H.SHIMA Java6対応 start
////			this.setSize(500, 550);
//			this.setSize(512, 559);
//			//2012/02/22 TT H.SHIMA Java6対応 end
			this.setSize(512, 585);
// MOD end 20121126 QP@20505 課題No.10 MSG追加のため
			this.setLocationRelativeTo(null);

			//３．分析値入力パネルインスタンスを生成
			this.analysisInputPanel = new AnalysisInputPanel(strOutput); 
			this.getContentPane().add(this.analysisInputPanel);
			
			//終了イベント追加
			analysisInputPanel.getButton()[1].addActionListener(getActionEvent());
			analysisInputPanel.getButton()[1].setActionCommand("shuryo");
			
			//４．画面を表示状態にする
			this.setVisible(false);

		} catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("分析値入力画面のコンストラクタが失敗しました。");
			this.ex.setStrErrShori("AnalysisInputSubDisp");
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
						if ( event_name.equals("shuryo")) {
							//終了処理を行う
							Exit();
						}
					} catch (Exception ec) {
						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("試作分析データ確認画面(Frame)　終了処理が失敗しました");
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
			this.ex.setStrErrmsg("試作分析データ確認画面(Frame)　アクションイベント処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
		
		return listener;
	}
	
	/**
	 * パネルゲッター
	 */
	public AnalysisInputPanel getAnalysisInputPanel() {
		return analysisInputPanel;
	}
	
}