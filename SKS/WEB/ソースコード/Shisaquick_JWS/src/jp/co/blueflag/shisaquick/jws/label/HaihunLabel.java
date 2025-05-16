package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * ハイフン表示ラベル設定用のクラス
 * 
 */
public class HaihunLabel extends LabelBase {
	
	private ExceptionBase ex;
	private int width = 50;
	private int height = 15;
	
	/**
	 * ハイフン表示ラベル コンストラクタ 
	 */
	public HaihunLabel() throws ExceptionBase {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		try {
			//ラベルのサイズ設定
			this.setText("-");
			this.setHorizontalAlignment(this.CENTER);
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setBackground(Color.WHITE);
			this.setOpaque( true );
			
		} catch ( Exception e ) {			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("ハイフン表示ラベルのコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}
	
}