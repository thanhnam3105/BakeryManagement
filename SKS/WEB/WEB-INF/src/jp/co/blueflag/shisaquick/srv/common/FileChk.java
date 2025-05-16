/*
 * ファイルチェッククラス
 * ファイルパスに存在する対象ファイルの存在チェック
 */
package jp.co.blueflag.shisaquick.srv.common;

import java.io.File;

/**
 *
 * Class名　: ファイルチェック
 * 処理概要 : 対象ファイルの存在チェック（サーバー内）
 *
 * @since   2014/08/27
 * @author  E.Kitazawa
 * @version 1.0
 */
public class FileChk {

	private ExceptionManager em;		//Exception管理クラス

	/**
	 * コンストラクタ
	 *  : ファイルコピー用コンストラクタ
	 */
	public FileChk() {
		//クラス名を引数として、ExceptionManagerのインスタンスを生成する。
		this.em = new ExceptionManager(this.getClass().getName());

	}

	/**
	 * ファイルチェック
	 *  : 対象ファイルがサーバーに存在するかチェックする。
	 *
	 * @param  strFilePath
	 * @return int    ファイルが存在する（通常ファイル）：1
	 *                ファイルが存在する（フォルダー）：2
	 *                ファイルが存在する（その他）：9
	 *                ファイルが存在しない時：-1
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public int fileChkLogic(String strFilePath) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int retint = -1;			// 戻り値の宣言;

		try {
			if (strFilePath.equals("")) {
				return retint;
			}

			//ファイルObj作成
			File file = new File(strFilePath);

			// ファイル存在チェック（ファイルもしくはディレクトリ）
			if (file.exists()) {
				// ファイルが存在する時、true を返す
				retint = 9;

				if (file.isFile()) {
					retint = 1;
				} else if (file.isDirectory()) {
					retint = 2;
				}
			}

		} catch( Exception ex ) {
			this.em.ThrowException(ex, "");
		} finally {

		}
		return retint;
	}

	/**
	 * ディレクトリ内ファイルチェック
	 *  : 対象ディレクトリ内にファイルが存在するかチェックする。
	 *
	 * @param  strDir
	 * @return String[] 対象ディレクトリ内のファイル一覧
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public String[] fileListChkLogic(String strDir) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String[] retFile = null;			// 戻り値の宣言;

		try {
			if (strDir.equals("")) {
				return retFile;
			}

			//ファイルObj作成
			File dir = new File(strDir);

			// フォルダー内のファイル一覧
			File[] files1 = dir.listFiles();

			if (files1 != null && files1.length > 0) {
				// フォルダー内にファイル存在する時、ファイルリストを返す
				retFile = new String[files1.length];
				for (int i=0; i<files1.length; i++) {
					retFile[i] = files1[i].getName();
				}
			}

		} catch( Exception ex ) {
			this.em.ThrowException(ex, "");
		} finally {

		}
		return retFile;
	}

}
