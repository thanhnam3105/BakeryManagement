/*
 * ファイル物理コピークラス
 * ファイルパスに存在する対象ファイルの存在チェック、ファイルコピー
 */
package jp.co.blueflag.shisaquick.srv.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * Class名　: ファイル操作
 * 処理概要 : サーバー内ディレクトリ作成、ファイルコピー
 *
 * @since   2014/08/27
 * @author  E.Kitazawa
 * @version 1.0
 */
public class FileCreate {

	private ExceptionManager em;		//Exception管理クラス

	/**
	 * コンストラクタ
	 *  : ファイルコピー用コンストラクタ
	 */
	public FileCreate() {
		//クラス名を引数として、ExceptionManagerのインスタンスを生成する。
		this.em = new ExceptionManager(this.getClass().getName());

	}

	/**
	 * ホルダー作成
	 *  : サーバーにホルダーが存在しない時ホルダーを作成する。
	 *
	 * @param  strDir
	 * @return boolean  作成に成功
	 *                  作成失敗
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public boolean createDirLogic(String strDir) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		boolean retBool = false;			// 戻り値の宣言;

		try {
			if (strDir.equals("")) {
				return retBool;
			}

			//ファイルObj作成
			File file = new File(strDir);

			// ファイル存在チェック（ファイルもしくはディレクトリ）
			if (!file.exists()) {
				// ファイルが存在しない時、ディレクトリ作成
				file.mkdir();
				retBool = true;
			}

		} catch( Exception ex ) {
			this.em.ThrowException(ex, "");
		} finally {

		}
		return retBool;
	}


	/**
	 * ファイルコピー
	 *  : ファイルコピーを実行する。
	 *
	 * @param  inFpath		サーバーファイル名（入力側）
	 * 	       outFpath		サーバーファイル名（出力側）
	 * @return なし
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void fileCopyLogic(String inFpath, String outFpath) throws ExceptionSystem, ExceptionUser, ExceptionWaning {


        try {
            //Fileオブジェクトを生成する
            FileInputStream fis = new FileInputStream(inFpath);
            FileOutputStream fos = new FileOutputStream(outFpath);

            //入力ファイルをそのまま出力ファイルに書き出す
            byte buf[] = new byte[256];
            int len;
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }

            //終了処理
            fos.flush();
            fos.close();
            fis.close();

        	fis = null;
        	fos = null;

            System.out.println("コピーが完了しました。");

        } catch (IOException ex) {
            //例外時処理
            System.out.println("コピーに失敗しました。");
            ex.printStackTrace();

        } finally {

		}

	}

}
