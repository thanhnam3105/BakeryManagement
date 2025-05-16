/*
 * ダウンロードファイル削除用通信クラス
 * 通信情報より取得したファイルパスのファイルを削除
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.FileChk;
import jp.co.blueflag.shisaquick.srv.common.FileDelete;

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

		ExceptionManager em = null;

		String strFileNames = "";
	    String strFilePath = "";

		//【QP@40404】add start TT E.Kitazawa 2014/10/28
	    String strMsg = "";
		//【QP@40404】add end TT E.Kitazawa 2014/10/28

		try{
			em = new ExceptionManager(this.getClass().getName());

			strFileNames = req.getParameter("FName").toString();

			String url;
			try {
				url = URLEncoder.encode(req.getParameter("FName").toString() , "ASCII");

				System.out.println(url);
			} catch (UnsupportedEncodingException e) {
				printPage(resp, "UnsupportedEncodingException", strMsg);
				e.printStackTrace();
			}

			//リクエスト情報より対象ファイルパスを取得
		    String val = req.getParameter("FName");
		    try {
		    	byte[] byteData = val.getBytes("ISO_8859_1");
//2010/01/13　UPDATE　Start　isono　MiclosoftのS-JIS拡張文字対応
//		    	val = new String(byteData, "Shift_JIS");
		      	val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);
//2010/01/13　UPDATE　End　　isono　MiclosoftのS-JIS拡張文字対応
		    	strFileNames = val;
		    }catch(UnsupportedEncodingException e){
				printPage(resp, "ファイルパスの取得に失敗", strMsg);
				em.cnvException(e, "ファイルパスの取得に失敗しました。");
//		    	System.out.println(e);
		    }

			//【QP@40404】add start TT E.Kitazawa 2014/09/18
			//リクエスト情報よりサーバーパスを取得
			String strDLPath = "";

			if (req.getParameter("DLPath")!= null){

			    val = req.getParameter("DLPath");
			    strDLPath = val;
			    try {
			    	byte[] byteData = val.getBytes("ISO_8859_1");
			    	//val = new String(byteData, "Shift_JIS");
			      	val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);
			      	strDLPath = val;

			      	}catch(UnsupportedEncodingException e){
					printPage(resp, "DLPathの取得に失敗", strMsg);
			    	System.out.println(e);
			    }
			}
			//【QP@40404】add end TT E.Kitazawa 2014/09/18

			//【QP@40404】mod start TT E.Kitazawa 2014/09/18
			strFilePath = ConstManager.getRootAppPath();

			//削除ファイルのルートURLを生成
			if (strDLPath.equals("")) {
				// フォルダー指定がない時はDefault
				strFilePath += ConstManager.getConstValue(Category.設定, "DOWNLOAD_FOLDER");
				strFilePath += "\\";
	        } else {
	        	// サーバーパス（const値）
	        	// strDLPath にはサブフォルダーが付加されている
	        	// DELETE処理ではconst値のみ（strFileNames にサブフォルダーを付加）
				strFilePath += ConstManager.getConstValue(Category.設定, strDLPath);
				strFilePath += "\\";
	        }

			// strFileNames：フォルダーも削除する時、サブフォルダー名まで指定の場合がある
			//【QP@40404】mod end TT E.Kitazawa 2014/09/18
			//ファイル別に分解
			String[] listFName = strFileNames.split(":::");

			FileDelete fileDel = new FileDelete();

			//ファイル削除ロジックを呼出す
			try {
				for (int i = 0; i < listFName.length; i++) {
					//【QP@40404】mod start TT E.Kitazawa 2014/09/18
					// listFName[i]がフォルダーか？ 存在するファイルの時だけ削除実行
					if (chkFileExists(strFilePath + listFName[i]) == 1) {
						fileDel.fileDeleteLogic(strFilePath + listFName[i]);
						strMsg += "ファイル削除：" + strFilePath + listFName[i] + "<br/>";
					}

					// 追加仕様：サブフォルダー指定の場合、サブフォルダーを削除
					if (!strDLPath.equals("")) {
						// フォルダー内にファイルが残っている場合、サブフォルダー内ファイルを削除
						// listFName[i] にサブフォルダーも含む
						strMsg += dirDelete(strFilePath, listFName[i], resp);
					}
					//【QP@40404】mod end TT E.Kitazawa 2014/09/18
				}
			} catch (Exception e) {
				printPage(resp, "1.ファイルの削除に失敗", strMsg);
				em.cnvException(e, "ファイルの削除に失敗しました。");
//				e.printStackTrace();
			} finally {
				em = null;
				fileDel = null;
				// strDLPath にサブフォルダーが設定されている時だけメッセージ出力
				if (!strDLPath.equals("")) {
					printPage(resp, "＜削除処理終了＞", strMsg);
				}
			}

		}catch(Exception e){
			printPage(resp, "2.ファイルの削除に失敗", strMsg);
			e.printStackTrace();
		}finally{

		}

	}


	//【QP@40404】add start TT E.Kitazawa 2014/09/18
	/**
	 * フォルダー削除
	 * @param   argServerFPath： サーバーパス
	 *          argDelFile    ： サブフォルダーを含む削除ファイル名、又はサブフォルダー名のみ
	 * @return  strRet        ： 削除結果（削除ファイル名、フォルダー名）
	 *
	 */
	private String dirDelete(String argServerFPath, String argDelFile, HttpServletResponse res){

		String[] delFile = null;			// サブフォルダー、ファイル名を分解
		String strFolder = "";				// 削除フォルダー名
		String strRet = "";					// 戻り値（削除結果を返す）

		//ファイル削除オブジェクトの生成
		FileDelete fileDel = new FileDelete();

		//ファイルチェックオブジェクトの生成
		FileChk fileChk = new FileChk();

		if (argServerFPath.equals("")) {
			return strRet;
		}
		if (argDelFile.equals("")) {
			return strRet;
		}

		// サブフォルダーとファイルを分解
		delFile = argDelFile.split("\\\\");
		strFolder = argServerFPath + delFile[0];

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());

			try {
				// フォルダー内一覧を取得する
				String[] fileLst = fileChk.fileListChkLogic(strFolder);
				// フォルダー内ファイルをチェック
				if (fileLst == null) {
					// フォルダー内が空の時、フォルダー削除
					fileDel.fileDeleteLogic(strFolder);
					strRet += "フォルダー削除--" + strFolder + "<br/>";
				} else {
					strRet += "フォルダー内ファイル数：" + fileLst.length + "<br/>";

					for (int i=0; i<fileLst.length; i++) {
						// フォルダー内ファイルを削除
						fileDel.fileDeleteLogic(strFolder + "\\" + fileLst[i]);
						strRet += "ファイル削除：" + strFolder + "\\" + fileLst[i] + "<br/>";
					}
					// フォルダー削除
					fileDel.fileDeleteLogic(strFolder);
					strRet += "フォルダー削除：" + strFolder + "<br/>";
				}

			} catch (Exception e) {
				strRet += "フォルダー削除処理に失敗しました。";
				printPage(res, "dirDelete:", strRet);

				em.cnvException(e, "フォルダー削除処理に失敗しました。");
				System.out.println(e);
			} finally {
				em = null;
				fileDel = null;
			}

		}catch(Exception e){

		}finally{

		}

		return strRet;

	}

	/**
	 * サーバーファイルチェック
	 * @param argServerFPath サーバーファイルパス
	 * @return int    ファイルが存在する（通常ファイル）：1
	 *                ファイルが存在する（フォルダー）：2
	 *                ファイルが存在する（その他）：9
	 *                ファイルが存在しない時：-1
	 *
	 */
	private int chkFileExists(String argServerFPath){

		int retInt = -1;			// 戻り値の設定

		if (argServerFPath.equals("")) {
			return retInt;
		}

		//ファイルチェックオブジェクトの生成
		FileChk fileChk = new FileChk();

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());


			//ファイルチェックロジックを呼出す
			try {

				// ファイルの存在チェック
				retInt = fileChk.fileChkLogic(argServerFPath);

			} catch (Exception e) {
				em.cnvException(e, "ファイル存在チェックに失敗しました。");
				System.out.println(e);
			} finally {
				em = null;
				fileChk = null;
			}


		}catch(Exception e){

		}finally{

		}

		return retInt;

	}


	/**
	 * 削除ファイル確認を出力する
	 * @param res   サーブレットレスポンス
	 *        strHeder メッセージヘッダー
	 *        strMsg   メッセージ
	 */
	private void printPage(HttpServletResponse res, String strHeder, String strMsg){


		try{
			//ContentType を設定
			res.setContentType(ConstManager.SRV_HTML_CHARSET);

			//出力用 PrintWriter を取得
			PrintWriter out = res.getWriter();

			//HTML 出力
			out.println("<html>");
			out.println("    <head>");
			out.println("        <title>削除確認</title>");
			out.println("        <script>");
			out.println("        <!--");
			out.println("            function kakunin() {");
			out.println("                var frm = document.frm00; ");
//			out.println("                alert();");
//			                             画面を閉じる
//			out.println("                this.window.close();");
			out.println("            }");
			out.println("        //-->");
			out.println("        </script>");
			out.println("    </head>");
			out.println("    <body onload=\"kakunin()\" style=\"margin:10px;\">");
			out.println("    <form name=\"frm00\" method=\"POST\" >");
			out.println(   strHeder  + "<br/><br/>");
			out.println(   strMsg);
			out.println("      <input type=\"hidden\" name=\"msg\" value =\"" + strMsg + "\">");
			out.println("    </form>");
			out.println("    </form>");
			out.println("    </body>");
			out.println("</html>");

		}catch(Exception e){

		}finally{
		}
	}

	//【QP@40404】add end TT E.Kitazawa 2014/09/18

}
