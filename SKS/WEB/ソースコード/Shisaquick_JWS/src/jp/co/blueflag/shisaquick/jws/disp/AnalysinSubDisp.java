package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.panel.AnalysisPanel;

/**
 * 
 * 【A02-02】 試作分析データ確認サブ画面
 *  : 試作分析データ確認サブ画面の操作を行う
 * 
 * @author TT.katayama
 * @since 2009/04/03
 */
public class AnalysinSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private AnalysisPanel analysisPanel;

	private ExceptionBase ex;

	/**
	 * コンストラクタ 
	 * @param strOutput : 画面タイトル
	 */
	public AnalysinSubDisp(String strOutput) throws ExceptionBase {
		//１．スーパークラスのコンストラクタを定義（画面タイトル設定）
		super(JwsConstManager.JWS_TITLE + "　"+strOutput);
		
		try {
			//２．画面の位置、サイズを指定
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
//			this.setSize(900, 530);
//			this.setSize(990, 530);
//Java6対応
			this.setSize(1002,599);
//mod end --------------------------------------------------------------------------------------
			this.setLocationRelativeTo(null);

			//３．試作分析データ確認パネルインスタンスを生成
			this.analysisPanel = new AnalysisPanel(strOutput);
			//TODO 終了ボタンのイベント設定
			(this.analysisPanel.getButton())[4].addActionListener(getActionEvent());
			(this.analysisPanel.getButton())[4].setActionCommand("shuryo");
			this.getContentPane().add(this.analysisPanel);

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.5
	        //フォーカス順設定用オブジェクトの格納
	        this.setFocusControl(this.analysisPanel.getSetFocusComponent());
//add end --------------------------------------------------------------------------------------
			
			//４．画面を表示状態にする
			this.setVisible(false);
			
		} catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試作分析データ確認画面のコンストラクタが失敗しました。");
			this.ex.setStrErrShori("AnalysinSubDisp");
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
						if ( event_name.equals("shuryo")) {
							//終了処理を行う
							Exit();
						}
					} catch (Exception ec) {
						//エラー設定
						ex = new ExceptionBase();
						ex.setStrErrCd("");
						ex.setStrErrmsg("試作分析データ確認画面(Frame)　アクションイベント処理が失敗しました");
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
	
	//パネルセッター&ゲッター
	public AnalysisPanel getAnalysisPanel() {
		return analysisPanel;
	}
	public void setAnalysisPanel(AnalysisPanel analysisPanel) {
		this.analysisPanel = analysisPanel;
	}
	
	
}