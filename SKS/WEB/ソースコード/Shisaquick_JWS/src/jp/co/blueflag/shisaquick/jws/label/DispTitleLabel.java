package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * 画面タイトルラベル設定用のクラス
 * 
 */
public class DispTitleLabel extends LabelBase{
	
	private ExceptionBase ex;
	
	private int width = 150;
	private int height = 15;
	
	/**
	 * 画面タイトルラベル コンストラクタ 
	 */
	public DispTitleLabel() throws ExceptionBase{
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		try{
			
			//ラベルのサイズ設定
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setBounds(5, 5, width, height);
			this.setForeground( Color.white );
			this.setBackground( JwsConstManager.SHISAKU_TITLE_COLOR);
			this.setOpaque( true );
			
		}catch(Exception e){
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("画面タイトルラベルのコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}
	
}