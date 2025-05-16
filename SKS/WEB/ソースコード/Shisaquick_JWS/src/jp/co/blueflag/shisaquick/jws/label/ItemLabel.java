package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * 項目ラベル設定用のクラス
 * 
 */
public class ItemLabel extends LabelBase {
	
	private ExceptionBase ex;
	
	/**
	 * 項目ラベル コンストラクタ 
	 */
	public ItemLabel() throws ExceptionBase{
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		try {
			//ラベルのサイズ設定
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setVerticalAlignment(SwingConstants.CENTER);
			this.setBackground( JwsConstManager.SHISAKU_ITEM_COLOR );
			this.setOpaque( true );
			
		} catch ( Exception e ) {	
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("項目ラベルのコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
}