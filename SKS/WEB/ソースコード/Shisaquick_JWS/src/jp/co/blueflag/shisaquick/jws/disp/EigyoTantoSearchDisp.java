package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.panel.AnalysisPanel;
import jp.co.blueflag.shisaquick.jws.panel.EigyoTantoSerchPanel;

/**
 * 
 * 営業担当検索サブ画面
 *  : 営業担当検索の操作を行う
 * 
 * @author TT.Nishigawa
 * @since 2011/2/10
 */
public class EigyoTantoSearchDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private EigyoTantoSerchPanel EigyoTantoSerchPanel;

	private ExceptionBase ex;

	/**
	 * コンストラクタ 
	 * @param strOutput : 画面タイトル
	 */
	public EigyoTantoSearchDisp(String strOutput) throws ExceptionBase {
		//１．スーパークラスのコンストラクタを定義（画面タイトル設定）
		super(JwsConstManager.JWS_TITLE + "　"+strOutput);
		
		try {
			//２．画面の位置、サイズを指定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.setSize(920, 530);
			this.setSize(936, 542);
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.setLocationRelativeTo(null);

			//３．試作分析データ確認パネルインスタンスを生成
			this.EigyoTantoSerchPanel = new EigyoTantoSerchPanel(strOutput);
			//終了ボタンのイベント設定
			(this.EigyoTantoSerchPanel.getButton())[2].addActionListener(getActionEvent());
			(this.EigyoTantoSerchPanel.getButton())[2].setActionCommand("shuryo");
			this.getContentPane().add(this.EigyoTantoSerchPanel);

	        //フォーカス順設定用オブジェクトの格納
	        this.setFocusControl(this.EigyoTantoSerchPanel.getSetFocusComponent());
			
			//４．画面を表示状態にする
			this.setVisible(false);
			
		} catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("営業担当検索サブ画面のコンストラクタが失敗しました。");
			this.ex.setStrErrShori("EigyoTantoSerchPanel");
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
						ex.setStrErrmsg("営業担当検索サブ画面　アクションイベント処理が失敗しました");
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
			this.ex.setStrErrmsg("営業担当検索サブ画面　アクションイベント処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
		
		return listener;
	}
	
	//パネルセッター&ゲッター
	public EigyoTantoSerchPanel getEigyoTantoSerchPanel() {
		return EigyoTantoSerchPanel;
	}
	public void setEigyoTantoSerchPanel(EigyoTantoSerchPanel EigyoTantoSerchPanel) {
		this.EigyoTantoSerchPanel = EigyoTantoSerchPanel;
	}
	
	
}