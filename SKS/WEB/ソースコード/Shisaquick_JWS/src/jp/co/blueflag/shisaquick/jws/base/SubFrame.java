package jp.co.blueflag.shisaquick.jws.base;

import javax.swing.JFrame;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * サブ画面基底クラス
 *
 */
public class SubFrame extends FrameBase {

	private static final long serialVersionUID = 1L;

	/**
	 * サブ画面基底　コンストラクタ
	 * @param title : タイトル
	 */
	public SubFrame(String title) {
		//スーパークラスのコンストラクタ呼び出し
		super(title);
		
	}

	/**
	 * 終了処理
	 *  : 画面を非表示に設定する
	 */
	public void Exit() {
		this.setVisible(false);
	}
}
