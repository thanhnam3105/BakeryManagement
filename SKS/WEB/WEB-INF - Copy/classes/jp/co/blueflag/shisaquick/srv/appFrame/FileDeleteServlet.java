/* 
 * ダウンロードファイル削除用通信クラス
 * 通信情報より取得したファイルパスのファイルを削除
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.FileDelete;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 
 * Class名　: ダウンロードファイル削除通信
 * 処理概要 : ダウンロードファイル削除用のURL通信を行い、対象ファイルの削除を行う。
 *
 * @since   2009/03/16
 * @author  TT.Furuta
 * @version 1.0
 */
public class FileDeleteServlet extends HttpServlet {
	
	/**
	 * HttpPost送受信
	 *  : POST通信の送受信を行う。
	 * @param req : サーブレットリクエスト
	 * @param resp : サーブレットレスポンス
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {

		String strFileNames = "";
	    String strFilePath = "";

		try{
			//リクエスト情報より対象ファイルパスを取得
		    String val = req.getParameter("FName");
		    try {
		    	byte[] byteData = val.getBytes("ISO_8859_1");
		    	val = new String(byteData, "Shift_JIS");
		    	strFileNames = val;
		    }catch(UnsupportedEncodingException e){
		    	System.out.println(e);
		    }

			//ダウンロードのルートURLを生成
			strFilePath = ConstManager.getRootAppPath();
			strFilePath += ConstManager.getConstValue(Category.設定, "DOWNLOAD_FOLDER");
			strFilePath += "\\";
			
			//ファイル別に分解
			String[] listFName = strFileNames.split(":::");
	    
			FileDelete fileDel = new FileDelete();

			//ファイル削除ロジックを呼出す
			try {
				for (int i = 0; i < listFName.length; i++) {
					fileDel.fileDeleteLogic(strFilePath + listFName[i]);				
				}
			} catch (ExceptionSystem e) {
				e.printStackTrace();
			} catch (ExceptionUser e) {
				e.printStackTrace();
			} catch (ExceptionWaning e) {
				e.printStackTrace();
			}
		
		}catch(Exception e){

		}finally{
			
		}

	}
}
