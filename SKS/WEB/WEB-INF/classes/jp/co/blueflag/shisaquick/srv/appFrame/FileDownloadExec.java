/* 
 * ダウンロードファイル削除用通信クラス
 * 通信情報より取得したファイルパスのファイルを削除
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.base.HttpServletBase;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 
 * Class名　: ファイルダウンロード処理
 * 処理概要 : ファイルダウンロードを行う　
 *   
 * @since   2009/05/13
 * @author  jinbo
 * @version 1.0
 */
public class FileDownloadExec extends HttpServletBase {
	
	/**
	 * HttpGet送受信
	 *  : GET通信の送受信を行う。
	 * @param req : サーブレットリクエスト
	 * @param resp : サーブレットレスポンス
	 */
	public void doGet(HttpServletRequest HttpReq, HttpServletResponse HttpRes) {
	
		//基底クラス（HttpServletBase）の初期処理を行う
		super.initServlet(HttpReq, HttpRes);
		
		String strFileNames = "";
		String strDownloadFileNames = "";
		
		//リクエスト情報より対象ファイルパスを取得
		if (req.getParameter("FName")!= null){
			strFileNames = req.getParameter("FName").toString();
			
			String url;
			try {
				url = URLEncoder.encode(req.getParameter("FName").toString() , "ASCII");
				
				System.out.println(url);
			} catch (UnsupportedEncodingException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
		    String val = req.getParameter("FName");
		    strDownloadFileNames = val;
		    try {
		    	byte[] byteData = val.getBytes("ISO_8859_1");
		      	val = new String(byteData, "Shift_JIS");
		      	strFileNames = val;
		    }catch(UnsupportedEncodingException e){
		    	System.out.println(e);
		    }
			
		}
		
        // ダウンロード
		try {
			// ヘッダの設定
			HttpRes.setContentType("application/octet-stream");
			HttpRes.setHeader("Content-Disposition", "attachment; filename=\"" + strDownloadFileNames + "\"");

			// ダウンロードファイルの出力
	        ServletOutputStream out = HttpRes.getOutputStream();
	        System.out.println(new File(".").getAbsolutePath());
	        FileInputStream in = new FileInputStream(ConstManager.getRootAppPath() + ConstManager.getConstValue(Category.設定, "DOWNLOAD_FOLDER") + "\\" + strFileNames);
	        byte[] buf = new byte[4096];
	        int size;
	        while ((size = in.read(buf)) != -1) {
	            out.write(buf, 0, size);
	        }
	        in.close();
	        out.close();
	        in = null;
	        out = null;
		} catch (Exception e) {
	    	System.out.println(e);
		} finally {

		}

	}

}
