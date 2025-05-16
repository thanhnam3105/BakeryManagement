package jp.co.blueflag.shisaquick.jws.disp;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.panel.ManufacturingPanel;
import jp.co.blueflag.shisaquick.jws.panel.PrototypeListPanel;

/**
 * 
 * 【A02-07】 製造工程サブ画面
 *  : 製造工程サブ画面の操作を行う
 * 
 * @author TT.katayama
 * @since 2009/04/03
 */
public class ManufacturingSubDisp extends SubFrame {
	private static final long serialVersionUID = 1L;
	
	private ManufacturingPanel manufacturingPanel;	//製造工程パネルクラス
	private ExceptionBase ex;								//エラー操作クラス

	/**
	 * コンストラクタ 
	 * @param strOutput : 画面タイトル
	 * @throws ExceptionBase 
	 */
	public ManufacturingSubDisp(String strOutput) throws ExceptionBase {
		//１．スーパークラスのコンストラクタを定義（画面タイトル設定）
		super(JwsConstManager.JWS_TITLE + "　"+strOutput);

		try {
			//２．画面の位置、サイズを指定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.setSize(320, 550);
			this.setSize(332, 559);
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.setLocationRelativeTo(null);
			
			//３．製造工程パネルインスタンスを生成
			this.manufacturingPanel = new ManufacturingPanel(); 
			this.getContentPane().add(this.manufacturingPanel);
			
			//４．画面を表示状態にする
			this.setVisible(false);

		} catch (ExceptionBase eb) {
			throw eb;
			
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("製造工程画面のコンストラクタが失敗しました。");
			this.ex.setStrErrShori("ManufacturingSubDisp");
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
			
		}
	}
	
	/**
	 * 製造工程パネルセッター&ゲッター
	 */
	public ManufacturingPanel getManufacturingPanel() {
		return manufacturingPanel;
	}
	public void setManufacturingPanel(ManufacturingPanel manufacturingPanel) {
		this.manufacturingPanel = manufacturingPanel;
	}
	
}