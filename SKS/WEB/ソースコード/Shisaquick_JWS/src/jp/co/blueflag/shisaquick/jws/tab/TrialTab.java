package jp.co.blueflag.shisaquick.jws.tab;

import java.awt.Color;
import java.awt.Font;

import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.TabBase;
import jp.co.blueflag.shisaquick.jws.panel.Trial1Panel;
import jp.co.blueflag.shisaquick.jws.panel.Trial2Panel;
import jp.co.blueflag.shisaquick.jws.panel.Trial3Panel;
import jp.co.blueflag.shisaquick.jws.panel.Trial5Panel;

/****************************************************************************************
 * 
 *    タブ操作の規定クラス
 *   @author TT nishigawa
 *   
 ****************************************************************************************/
public class TrialTab extends TabBase{
	private static final long serialVersionUID = 1L;
	
	private Trial1Panel Trial1Panel;
	private Trial2Panel Trial2Panel;
	private Trial3Panel Trial3Panel;

	private Trial5Panel Trial5Panel;
	
	private ExceptionBase ex;		//エラーハンドラ
	
	/************************************************************************************
	 * 
	 *   コンストラクタ
	 *    : 試作表タブを生成する
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	public TrialTab() throws ExceptionBase {
		super();
		
		try {
			this.setBackground(Color.WHITE);
			this.setFont(new Font("Default", Font.PLAIN, 12));
			Trial1Panel = new Trial1Panel();
			Trial2Panel = new Trial2Panel();
			Trial3Panel = new Trial3Panel();

			Trial5Panel = new Trial5Panel();
			
		} catch (Exception e) {
			e.printStackTrace();
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("タブ操作処理が失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.toString());
			throw ex;
			
		} finally {
			
		}
	}
	
	
	/************************************************************************************
	 * 
	 *   タブパネル設定
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	public void setTrialPane(){
		try{
			//再表示
			Trial1Panel.init();
			Trial2Panel = new Trial2Panel();
			Trial3Panel = new Trial3Panel();
			
			this.removeAll();
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//this.addTab("試作表①",  Trial1Panel);
			this.addTab("配合表",  Trial1Panel);
			//this.addTab("試作表②",  Trial2Panel);
			this.addTab("特性値",  Trial2Panel);
			//this.addTab("試作表③",  Trial3Panel);
			this.addTab("基本情報",  Trial3Panel);

			//this.addTab("原価試算＜原料＞⑤",  Trial5Panel);
			this.addTab("原価試算",  Trial5Panel);
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			
		}catch(Exception e){
			
		}
	}
	
	/**
	 * @return trial1Panel
	 */
	public Trial1Panel getTrial1Panel() {
		return Trial1Panel;
	}
	
	/**
	 * @return trial2Panel
	 */
	public Trial2Panel getTrial2Panel() {
		return Trial2Panel;
	}
	/**
	 * @param trial2Panel セットする trial2Panel
	 */
	public void setTrial2Panel(Trial2Panel trial2Panel) {
		Trial2Panel = trial2Panel;
	}

	/**
	 * @return trial3Panel
	 */
	public Trial3Panel getTrial3Panel() {
		return Trial3Panel;
	}

	/**
	 * @return trial5Panel
	 */
	public Trial5Panel getTrial5Panel() {
		return Trial5Panel;
	}
	/**
	 * @param trial5Panel セットする trial5Panel
	 */
	public void setTrial5Panel(Trial5Panel trial5Panel) {
		Trial5Panel = trial5Panel;
	}
	
}