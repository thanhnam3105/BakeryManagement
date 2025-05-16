package jp.co.blueflag.shisaquick.jws.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.common.DataBase;

/**
 * 
 * クリップボードデータ保持
 *  : コピーしたデータ管理する
 *
 */
public class ClipboardData extends DataBase {

	private String strClipboad;			//原料コードのコピー＆ペーストに必要なデータの格納
	private ArrayList aryClipboad;		//試作明細のコピー＆ペーストに必要なデータの格納
	
	/**
	 * コンストラクタ
	 */
	public ClipboardData(){
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		this.strClipboad = null;
		this.aryClipboad = new ArrayList();
	}

	/**
	 * クリップボード用文字 ゲッター
	 * @return strClipboad : クリップボード用文字の値を返す
	 */
	public String getStrClipboad() {
		return strClipboad;
	}
	/**
	 * クリップボード用文字 セッター
	 * @param strClipboad : クリップボード用文字の値を格納する
	 */
	public void setStrClipboad(String _strClipboad) {
		this.strClipboad = _strClipboad;
	}
	
	/**
	 * クリップボード用配列 ゲッター
	 * @return aryClipboad : クリップボード用配列の値を返す
	 */
	public ArrayList getAryClipboad() {
		return aryClipboad;
	}
	/**
	 * クリップボード用配列 セッター
	 * @param aryClipboad : クリップボード用配列の値を格納する
	 */
	public void setAryClipboad(ArrayList aryClipboad) {
		this.aryClipboad = aryClipboad;
	}
	
}
