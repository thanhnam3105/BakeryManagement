package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;

import javax.swing.*;

/**
 * 
 * 画面操作 : 画面の基底クラス
 *  フォーカスの制御についてはSwingの機能を用いて行う　
 *  （フォーカスルール設定＋キー指定）
 *
 */
public class FrameBase extends JFrame  {

	private static final long serialVersionUID = 1L;	//デフォルトシリアルID
	
	private ExceptionBase ex;						//エラー操作
	
	/**
	 * 画面操作コンストラクタ : インスタンスを生成
	 * @param title : 画面タイトル
	 */
	public FrameBase(String title) {
		//スーパークラスのコンストラクタ呼び出し
		super(title);
		
		this.ex = null;
	}

	/**
	 * フォーカスコントロール
	 *  : Enterキー制御処理とフォーカス順の設定を行う
	 * @param aryFocusComp : フォーカスに設定する画面コンポーネント.
	 *  aryFocusComp[a][b] :  [a : 画面行], [b : 画面列]
	 */
	public void setFocusControl(final JComponent[][] aryFocusComp) {
		JComponent setComp = null;
		JComponent enterComp = null;
		final ArrayList lstComp = new ArrayList();
		
		//多次元配列を1次元配列に変換し、
		//ENTER制御を設定
		for ( int i = 0; i < aryFocusComp.length; i++ ) {
			for ( int j= 0; j < aryFocusComp[i].length; j++ ) {
				setComp =aryFocusComp[i][j];
				
				if ( i < aryFocusComp.length - 1 ) {
					enterComp = aryFocusComp[i + 1][0];
				} else {
					enterComp = aryFocusComp[0][0];
				}
				
				try {
					this.setEnterFocusControl(setComp, enterComp);
				} catch (ExceptionBase e) {
					e.getStackTrace();
				}
				
				lstComp.add(setComp);
			}
		}
		
		//コンポーネントが送られていない場合、強制終了
		if ( lstComp.size() == 0 ) {
			return;
		}
		
		//フォーカス順設定
		this.setFocusTraversalPolicy(new FocusTraversalPolicy() {			
			//初期フォーカス場所
			public Component getFirstComponent(Container focusCycleRoot) {
				return (Component) lstComp.get(0);
			}
			//最終フォーカス場所
			public Component getLastComponent(Container focusCycleRoot) {
				return (Component) lstComp.get(lstComp.size()-1);
			}
			//次のフォーカス移動場所
			public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
				int index = lstComp.indexOf(aComponent);
				return (Component) lstComp.get((index + 1) % lstComp.size());
			}
			//前のフォーカス移動場所
			public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
				int index = lstComp.indexOf(aComponent);
				return (Component) lstComp.get((index - 1 + lstComp.size()) % lstComp.size());
			}
			//デフォルトComponent
			public Component getDefaultComponent(Container focusCycleRoot) {
				return (Component) lstComp.get(0);
			}
			
		} );
		
	}

	/***
	 * Enter押下時フォーカスコントロール
	 *  : Enter押下時フォーカス制御処理を行う
	 * @param setBaseComp : 対象コンポーネントのBaseクラス(TableBase等)
	 * @param enterComp : Enter時のフォーカス移動先コンポーネント
	 */
	public void setEnterFocusControl(JComponent setBaseComp, JComponent enterComp) throws ExceptionBase {
		try { 
			//FocusControlインターフェースのメソッドを使用
			FocusControl focusCtrl = (FocusControl)setBaseComp;
			focusCtrl.setEnterFocusControl(enterComp);
		} catch ( Exception e ) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("FrameBaseのフォーカス制御処理が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
		} finally {
			
		}
	}

}
