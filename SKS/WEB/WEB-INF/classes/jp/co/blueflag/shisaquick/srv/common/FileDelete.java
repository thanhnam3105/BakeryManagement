/* 
 * ファイル物理削除クラス
 * ファイルパスに存在する対象ファイルを削除
 */
package jp.co.blueflag.shisaquick.srv.common;

import java.io.File;

/**
 * 
 * Class名　: ファイル削除
 * 処理概要 : ダウンロードされた対象ファイルをサーバーより削除する。
 *
 * @since   2009/03/16
 * @author  TT.Furuta
 * @version 1.0
 */
public class FileDelete {

	private ExceptionManager em;		//Exception管理クラス
	
	/**
	 * コンストラクタ
	 *  : ファイル削除用コンストラクタ
	 */
	public FileDelete() {
		//クラス名を引数として、ExceptionManagerのインスタンスを生成する。
		this.em = new ExceptionManager(this.getClass().getName());
		
	}
	
	/**
	 * ファイル削除
	 *  : サーバーに存在する対象ファイルを削除する。
	 *  
	 * @param  strFilePath
	 * @return なし
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void fileDeleteLogic(String strFilePath) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {
			//ファイルObj作成
			File file = new File(strFilePath);
			
			//ファイル削除
			file.delete();
			
		} catch( Exception ex ) {
			this.em.ThrowException(ex, "");
		} finally {
			
		}
		
	}
}
