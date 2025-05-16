package jp.co.blueflag.shisaquick.jws.base;

import javax.swing.JFrame;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * メイン画面基盤クラス
 *
 */
public class MainFrame extends FrameBase {

	private static final long serialVersionUID = 1L;

	/**
	 * メイン画面基盤 コンストラクタ 
	 * @param title : タイトル
	 */
	public MainFrame(String title) {
		//スーパークラスのコンストラクタ呼び出し
		super(title);

		//画面を閉じると、プログラムを終了する
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	/**
	 * 終了処理
	 *  : プログラムを終了する
	 */
	public void Exit() {
		System.exit(0);
	}

}
