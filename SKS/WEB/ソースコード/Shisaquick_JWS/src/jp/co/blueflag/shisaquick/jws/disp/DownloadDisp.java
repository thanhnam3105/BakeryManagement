package jp.co.blueflag.shisaquick.jws.disp;

import java.awt.Color;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import jp.co.blueflag.shisaquick.jws.base.SubFrame;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;
import jp.co.blueflag.shisaquick.jws.panel.ColorPanel;


/**
 * 
 * 起動時ダウンロード用待ち画面
 * 
 * @author TT.nishigawa
 * @since 2009/06/24
 */
public class DownloadDisp  extends SubFrame {

	private static final long serialVersionUID = 1L;
	
	private PanelBase pb;						//画面パネル
	private DispTitleLabel dispTitleLabel;	//画面タイトルラベル
	private JLabel gifLabel;						//画像表示用ラベル
	private ExceptionBase ex;					//エラー操作クラス

	/**
	 * コンストラクタ 
	 * @throws ExceptionBase 
	 */
	public DownloadDisp() throws ExceptionBase {
		//スーパークラスのコンストラクタを定義（画面タイトル設定）
		super(JwsConstManager.JWS_TITLE + "　アプリケーションのダウンロード");
		
		try {
			//画面の位置、サイズを指定
			//2012/02/22 TT H.SHIMA Java6対応 start
//			this.setSize(1000, 720);
			this.setSize(1012,729);
			//2012/02/22 TT H.SHIMA Java6対応 end
			this.setLocationRelativeTo(null);
			this.setBackground(Color.WHITE);
			
			//パネルインスタンスを生成
			this.pb = new PanelBase();
			pb.setLayout(null);
			pb.setBackground(Color.WHITE);
			
			//コントロール配置
			setControl();
			this.getContentPane().add(this.pb);
			
			//画面を非表示状態にする
			this.setVisible(false);

		} catch (Exception e) {
			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("起動時ダウンロード用待ち画面のコンストラクタが失敗しました。");
			this.ex.setStrErrShori(this.getClass().toString());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			
			throw this.ex;
			
		} finally {
		}
		
	}
	
	/**
	 * コントロール配置
	 * @throws ExceptionBase 
	 */
	public void setControl() throws ExceptionBase{
		
		try {
			
			//コントロール設定
			
			//画面タイトルラベル設定
			this.dispTitleLabel = new DispTitleLabel();
			this.dispTitleLabel.setText("試作データを取得中です。しばらくお待ちください。");
			this.dispTitleLabel.setBounds(0, 290, 1000, 50);
			this.dispTitleLabel.setHorizontalAlignment(JLabel.CENTER);
			
			//画像表示ラベル設定
			ClassLoader classLoader = this.getClass().getClassLoader();
			URL resUrl = classLoader.getResource("images/bar.gif");
			ImageIcon icon = new ImageIcon( resUrl );
			this.gifLabel = new JLabel(icon);
			this.gifLabel.setBounds(0, 380, 1000, 50);
			this.gifLabel.setHorizontalAlignment(JLabel.CENTER);
			
			//パネルへ追加
			this.pb.add(this.dispTitleLabel);
			this.pb.add(this.gifLabel);

		} catch (ExceptionBase eb) {
			
			throw eb;
			
		} catch (Exception e) {
			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("起動時ダウンロード用待ち画面のコンストラクタが失敗しました。");
			this.ex.setStrErrShori(this.getClass().toString());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
		}
		
	}
	
}
