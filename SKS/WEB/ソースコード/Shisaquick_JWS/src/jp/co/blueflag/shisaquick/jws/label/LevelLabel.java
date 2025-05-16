package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * レベル項目ラベル設定用のクラス
 * 
 */
public class LevelLabel extends LabelBase {
	
	private ExceptionBase ex;
	private int red = 0xEE;
	private int green = 0x00;
	private int blue = 0x00;
	
	/**
	 *  レベル項目ラベル コンストラクタ 
	 */
	public LevelLabel() throws ExceptionBase{
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		try {
			//ラベルのサイズ設定
			this.setText("レベル１");
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setForeground( Color.white );
			this.setBackground(JwsConstManager.SHISAKU_LEVEL_COLOR);
			this.setOpaque( true );
			
		} catch ( Exception e ) {			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("レベル項目ラベルのコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
}