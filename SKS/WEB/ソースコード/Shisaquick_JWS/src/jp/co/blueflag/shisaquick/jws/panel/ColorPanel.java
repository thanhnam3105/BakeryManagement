package jp.co.blueflag.shisaquick.jws.panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import jp.co.blueflag.shisaquick.jws.common.ButtonBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.PanelBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.label.DispTitleLabel;

/**
 * 
 * 【A05-06】 色指定パネル操作用のクラス
 * 
 * @author TT.katayama
 * @since 2009/04/05
 */
public class ColorPanel extends PanelBase {
	private static final long serialVersionUID = 1L;

	private DispTitleLabel dispTitleLabel;		//画面タイトルラベル
//	private HeaderLabel headerLabel;				//ヘッダ表示ラベル
//	private LevelLabel levelLabel;					//レベル表示ラベル

	private JColorChooser colorChooser;		//色選択クラス
//	private Color selectColor;						//色情報格納クラス
	private ButtonBase closeBtn;					//閉じるボタン
	private ButtonBase colorClearBtn;			//色解除ボタン
	
//	private MessageCtrl messageCtrl;				//メッセージ操作
	private ExceptionBase ex;						//エラー操作

	//選択色解除ボタンコマンド文字列
	private final static String BTN_COLOR_CANCEL = "colorCancelBtnClick";
	
	/**
	 * コンストラクタ
	 * @param strOutput : 画面タイトル
	 * @throws ExceptionBase 
	 */
	public ColorPanel(String strOutput) throws ExceptionBase {
		//スーパークラスのコンストラクタを呼び出す
		super();

		try {
			//１．パネルの設定
			this.setPanel();
			
			//２．コントロールの配置
			this.addControl(strOutput);
			
			//３．初期処理を実行
			this.init();
	
		} catch(ExceptionBase e) {
			throw e;
			
		} catch(Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("色指定パネルのコンストラクタが失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
			
		} finally {
		}
		
	}

	/**
	 * パネル設定
	 */
	private void setPanel() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
	}

	/**
	 * コントロール配置
	 * @param strTitle : 画面タイトル
	 * @throws ExceptionBase 
	 */
	private void addControl(String strTitle) throws ExceptionBase {
		int x, y, width, height;
		int dispWidth = 290;
	
		try {
			///
			/// タイトルラベル設定
			///
			this.dispTitleLabel = new DispTitleLabel();
			this.dispTitleLabel.setText(strTitle);
			this.add(this.dispTitleLabel);
			
			///
			/// カラー選択
			///
			x = 5;
			y = 30;
			width = 273;
			height = 120;
			this.colorChooser = new JColorChooser();
			AbstractColorChooserPanel colorPanel = this.colorChooser.getChooserPanels()[0];
			colorPanel.setBounds(x,y,width,height);
			colorPanel.setBackground(Color.WHITE);
			this.add(colorPanel);
	
			///
			/// キャンセルボタン
			///
			x = dispWidth - 110;
			y = colorPanel.getY() + height;
			width = 100;
			height = 38;
			this.closeBtn = new ButtonBase();
			this.closeBtn.setFont(new Font("Default", Font.PLAIN, 11));
			this.closeBtn.setBounds(x,y,width,height);
			this.closeBtn.setText("キャンセル");
			this.add(this.closeBtn);
			
			///
			/// 色選択解除ボタン
			///
			x = x - width;
			this.colorClearBtn = new ButtonBase();
			this.colorClearBtn.setFont(new Font("Default", Font.PLAIN, 11));
			this.colorClearBtn.setBounds(x,y,width,height);
			this.colorClearBtn.setText("選択色解除");
			this.add(this.colorClearBtn);
		
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("色指定パネルのコントロール配置処理が失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
		} finally {
		}
	}
  
	/**
	 * 初期化処理
	 * @throws ExceptionBase 
	 */
	private void init() throws ExceptionBase {
		
		try {
			//選択色解除ボタン Actionコマンド設定
			this.colorClearBtn.addActionListener(this.getActionEvent());
			this.colorClearBtn.setActionCommand(BTN_COLOR_CANCEL);
			
		} catch (ExceptionBase e) {
			throw e;
		} catch (Exception e) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("色指定パネルの初期化処理が失敗しました。");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw this.ex;
		} finally {
		}		
	}

	/**
	 * キャンセルボタン　イベント設定処理
	 * @param listener : ActionListener
	 * @param actionCommand : コマンド名称
	 */
	public void setEndEvent(ActionListener listener, String actionCommand) {
		this.closeBtn.addActionListener(listener);
		this.closeBtn.setActionCommand(actionCommand);
	}
	
	/**
	 * ActionListener取得メソッド
	 *  : ボタン押下時及びコンボボックス選択時のイベントを設定する
	 * @return ActionListener 
	 * @throws ExceptionBase
	 */
	private ActionListener getActionEvent() throws ExceptionBase {
		ActionListener listener = null;
		listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String event_name = e.getActionCommand();
					if (event_name.equals(BTN_COLOR_CANCEL)) {
						//選択色解除押下時
						colorChooser.setColor(new Color(-1));
						colorChooser.setColor(new Color(-2));
					}
				}catch(Exception ec){
					//エラー設定
					ex = new ExceptionBase();
					ex.setStrErrCd("");
					ex.setStrErrmsg("色指定パネルのキャンセルボタン処理が失敗しました。");
					ex.setStrErrShori(this.getClass().getName());
					ex.setStrMsgNo("");
					ex.setStrSystemMsg(ec.getMessage());
					DataCtrl.getInstance().getMessageCtrl().PrintErrMessage(ex);
					
				}finally{
					
				}
			}
		};
		return listener;
	}	

	/**
	 * JColorChooser取得メソッド
	 *  : JColorChooserクラスに、選択色情報が格納されている。
	 * @return JColorChooser
	 */
	public JColorChooser getColorChooser() {
		return colorChooser;
	}

	/**
	 * JColorChooser設定メソッド
	 * @param colorChooser
	 */
	public void setColorChooser(JColorChooser colorChooser) {
		this.colorChooser = colorChooser;
	}
}