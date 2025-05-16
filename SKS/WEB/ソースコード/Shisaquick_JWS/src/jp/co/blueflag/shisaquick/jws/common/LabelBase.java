package jp.co.blueflag.shisaquick.jws.common;

import javax.swing.*;

/**
 * 
 * ラベルの基本クラス
 *
 */
public class LabelBase extends JLabel {

	private static final long serialVersionUID = 1L;	//デフォルトシリアルID
	
//	private ExceptionBase eb;						//エラー操作
	
	/**
	 * デフォルトコンストラクタ
	 */
	public LabelBase() {
		 //スーパークラスのコンストラクタ呼び出し
		 super();
	}

	/**
	 * コンストラクタ(テキスト指定)
	 * @param text : テキスト
	 */
	public LabelBase(String text) {
		 //スーパークラスのコンストラクタ呼び出し
		 super(text);

	}
}
