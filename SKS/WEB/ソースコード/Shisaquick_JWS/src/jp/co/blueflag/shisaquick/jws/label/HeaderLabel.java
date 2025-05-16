package jp.co.blueflag.shisaquick.jws.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.SwingConstants;

import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * ヘッダー項目ラベル設定用のクラス
 * 
 */
public class HeaderLabel extends LabelBase {
	
	private ExceptionBase ex;
	
	private String strkaisha = "所属会社：";
	private String strbusho = "所属部署：";
	private String strtanto = "担当者：";
	//2010/02/25 NAKAMURA ADD START---------
	private String strhaitakaisha = "会社：";
	private String strhaitabusho = "";
	private String strhaitashimei = "";
	//2010/02/25 NAKAMURA ADD END-----------
	private String header = "";
	
	/**
	 * 画面タイトルラベル コンストラクタ 
	 */
	public HeaderLabel() throws ExceptionBase{
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		try {
			//ユーザデータの設定
			header = this.getHeaderUserData();

			//ラベルのサイズ設定
			this.setText(header);
			this.setHorizontalAlignment(this.RIGHT);
			this.setFont(new Font("Default", Font.PLAIN, 12));
			this.setBackground( Color.WHITE );
			this.setOpaque( true );
			
		}catch ( Exception e ) {			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("ヘッダー項目ラベルのコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
//2010/05/12　シサクイック（原価）要望【案件No9】排他情報の表示　TT.NISHIGAWA　START	
	public String getHeaderUserData() throws ExceptionBase{
		
		String ret = "";
		
		try {
			//データ初期化
			strkaisha = "所属会社：";
			strbusho = "所属部署：";
			strtanto = "担当者：";
			strhaitakaisha = "会社：";
			strhaitabusho = "";
			strhaitashimei = "";
			
			//ユーザデータの設定
			strkaisha = strkaisha + DataCtrl.getInstance().getUserMstData().getStrKaishanm();
			strbusho = strbusho + DataCtrl.getInstance().getUserMstData().getStrBushonm();
			strtanto = strtanto + DataCtrl.getInstance().getUserMstData().getStrUsernm();
			//2010/02/25 NAKAMURA UPDATE START--------------------------------------------
			//header = strkaisha + "　" + strbusho + "　" + strtanto;
			strhaitakaisha = strhaitakaisha + DataCtrl.getInstance().getUserMstData().getStrHaitaKaishanm();
			strhaitabusho = DataCtrl.getInstance().getUserMstData().getStrHaitaBushonm();
			strhaitashimei = DataCtrl.getInstance().getUserMstData().getStrHaitaShimei();
			if(strhaitabusho.equals("")){
				strhaitabusho = "使用中：---/";
			}else{
				strhaitabusho = "使用中：" + strhaitabusho + "/";
			}
			if(strhaitashimei.equals("")){
				strhaitashimei = "---";
			}
			
			ret = strhaitabusho + strhaitashimei+ "　" + strkaisha + "　" + strbusho + "　" + strtanto;
			
			//header = strhaitakaisha + "　" + strhaitabusho + "　" + strhaitashimei + "　" + strkaisha + "　" + strbusho + "　" + strtanto;
			//2010/02/25 NAKAMURA UPDATE END----------------------------------------------
			
			this.setText("");
			//this.setText(header);

		}catch ( Exception e ) {			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("ヘッダー項目ラベルのユーザ設定が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
		return ret;
		
	}
//2010/05/12　シサクイック（原価）要望【案件No9】排他情報の表示　TT.NISHIGAWA　END
	
}