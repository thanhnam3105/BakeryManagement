/*
 * アップロードファイル用通信クラス
 * 通信情報より取得したファイルパスのファイルを渡す
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.base.HttpServletBase;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.FileChk;
import jp.co.blueflag.shisaquick.srv.common.FileCreate;
import jp.co.blueflag.shisaquick.srv.common.FileDelete;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
*
* Class名　: アップロード通信
* 処理概要 : ファイルアップロード用のURL通信を行い、対象画面を開く　
*           （正常時：アップロード画面　・　異常時：エラー画面）
*
* @since   2014/08/26
* @author  E.Kitazawa
* @version 1.0
*/
@SuppressWarnings("serial")
public class FileUpLoadExec extends HttpServletBase {

	/**
	 * HttpPost送受信
	 *  : POST通信の送受信を行う。
	 * @param req : サーブレットリクエスト
	 * @param resp : サーブレットレスポンス
	 */
	// ファイルアップロードする時は、doPostメソッドを使う。
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


//		response.setContentType("text/html; charset=Shift_JIS");
		response.setContentType(ConstManager.SRV_HTML_CHARSET);
		PrintWriter out = response.getWriter();

		// 遷移元JSP
		String strRef = request.getHeader("Referer");

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());

			// enctype="multipart/form-data" の時、getParameterでデータ取得ができない
			//          Enumeration names = request.getParameterNames();

			// アップロードファイルを受け取る準備
			// ディスク領域を利用するアイテムファクトリーを作成
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// ServletFileUploadを作成
			ServletFileUpload sfu = new ServletFileUpload(factory);

			// アップロード先のパス
			String upPath = "";
			String upFileNm = "";

			//-- リクエスト情報 --//
			// アップロード先 const定義名 --- strServerConst
			String strServerConst = "";

			// アップロードファイルのカウント（サブフォルダーと同じ数）
			int intIndex = 0;
			// サブフォルダー名 --- strSubFolder
			String[] listSubFolder = null;
			String strSubFolder = "";

			// アップロードするフィールド名 --- strFieldNm(split)
			String[] listFieldNm = null;
			// フィールド名
			String fieldname = "";

			// 削除するファイル名 --- strFilePath
			String[] listDeleteFname = null;
			String strDeleteFname = "";
			String[] listTmp = null;

			// ファイル名のみの抽出用
			String filename = "";

			// リクエストをファイルアイテムのリストに変換
			List<FileItem> list = sfu.parseRequest(request);

			// リクエスト情報を先に取得する
			Iterator<FileItem> iterator = list.iterator();

			try {
				while(iterator.hasNext()){
					// コレクションから1つ1つFileItemクラスのオブジェクトを取り出す
					FileItem item = (FileItem)iterator.next();

					// 取り出したFileItemに対する処理
					// フォーム内のコントロールのデータを取得する
					if (item.isFormField()){
						// フォーム内のコントロールのデータ(text値)を取得する
						// name（コントロール名）
						String name = item.getFieldName();

						if  (name.equals("strServerConst")) {
							// アップロード先の cost値
							// strServerConst の value値を取得
							strServerConst = item.getString();

						} else if  (name.equals("strSubFolder")) {
							// サブフォルダー名（アップロードファイル毎に指定）
							// strSubFolder の value値を取得し分解（エンコード）
							listSubFolder = encodeStr(item.getString()).split(":::");

						} else if  (name.equals("strFieldNm")) {
							// アップロードファイルのフィールド名（アップロードファイルのチェック用）
							// strFieldNm の value値を取得し分解
							listFieldNm = item.getString().split(":::");

						} else if  (name.equals("strFilePath")) {
							// 削除ファイルパス（サブフォルダーを含む）
							// delFname の value値を取得（エンコード）
							listDeleteFname = encodeStr(item.getString()).split(":::");

						}
					}
				}

				// アップロード先のパス取得
				if (!strServerConst.equals("")) {
					// const 値を取得
					upPath = ConstManager.getConstValue(Category.設定, strServerConst);
					// アップロード先のサーバーパスに階層がある時
					String[] strPath = upPath.split("/");
					String tmpPath = "";
					for (int i=0; i<strPath.length; i++) {
						if (i > 0) tmpPath += "/";
						tmpPath += strPath[i];
						// サーバーパスを取得
						upPath = getServletContext().getRealPath(tmpPath);
						// アップロード先フォルダーの存在チェック
						if (!chkFileExists(upPath, out)) {
							// フォルダーが存在しない時、ディレクトリ作成
							createDir(upPath, out);
						}
					}
				}

				// 削除ファイル名が指定されている時：ファイル削除
				if (listDeleteFname != null) {
					// リクエスト情報を先に取得する
					for (int i=0; i < listDeleteFname.length; i++) {
						strDeleteFname = listDeleteFname[i];
						if (!strDeleteFname.equals("")) {
							// strDeleteFname は サブフォルダー名を含む
							// ファイル名だけを取り出す
							filename = (new File(strDeleteFname)).getName();

							// アップロード先のファイルパス
							listTmp = strDeleteFname.split("\\\\");
							strSubFolder = "";
							// 削除ファイル名にサブフォルダーが指定されている時
							if (listTmp.length > 1) {
								strSubFolder = "\\" + listTmp[0];
							}
							upFileNm = upPath + "\\" + strDeleteFname;

							// ファイルの存在チェック後、削除
							if (fileDelete(upFileNm, upPath + strSubFolder, out)) {
								System.out.println("ファイルを削除しました：" + upFileNm);
							}
						}
					}

				}

				// ファイル情報を取得する
				iterator = list.iterator();
				// 完了メッセージ
				String strmsg = "";

				while(iterator.hasNext()){
					// コレクションから1つ1つFileItemクラスのオブジェクトを取り出す
					FileItem item = (FileItem)iterator.next();

					// 取り出したFileItemに対する処理
					// フォーム内の他のコントロールのデータは除外する
					if (!item.isFormField()){
						// アップロードされたファイルのみ対象の処理
						//					  String filename = encodeStr(item.getName());		// ファイルパスのエンコード
						// ファイルパス
						filename = item.getName();
						// フィールド名
						fieldname = item.getFieldName();
						// フィールド名で対象ファイルかどうかチェックする
						if (!chkFieldNm(listFieldNm, fieldname)) {
							continue;
						}

						// アップロード対象ファイルの場合
						if ((filename != null) && (!filename.equals(""))){
							// ファイル名だけを取り出す
							filename = (new File(filename)).getName();

							// アップロード対象のフィールド順にlstSubFolder が設定されている
							// アップロード先のパス：サブフォルダーを追加
							if ((listSubFolder != null) && (listSubFolder.length > intIndex)) {
								if (!listSubFolder[intIndex].equals("")) {
									strSubFolder = "\\" + listSubFolder[intIndex];
									upFileNm = upPath + strSubFolder + "\\" + filename;
								}
								intIndex++;
							} else {
								upFileNm = upPath + "\\" + filename;
							}

							// サブフォルダーの存在チェック（深さ：一つ）
							if (!chkFileExists(upPath + strSubFolder, out)) {
								// サブフォルダーが存在しない時、ディレクトリ作成
								createDir(upPath + strSubFolder, out);
							}

							// 存在チェック
							if (chkFileExists(upFileNm, out)) {
								System.out.println("ファイルが存在します：" + upFileNm);
								// ファイルが存在する時、無条件にファイル削除
								fileDelete(upFileNm, "", out);
							}

							// アップロード実行
							item.write(new File(upFileNm));
							// アップロードファイル名を追加
							strmsg += "\n" +item.getName();
						}
					}
				}

				// 完了メッセージ表示（遷移元JSPのURLを渡す）
				printHeadPage(out, strRef);
				if (strmsg.equals("")) {
					strmsg +=  "\nアップロードファイルがありません。";
				} else {
					strmsg +=  "\nをアップロードしました";
				}
				String strln ="<input type=\"hidden\" name=\"msg\" value =\"" + strmsg + "\"> ";

				out.println(strln);
				out.println("</form>");
				out.println("</body>");
				out.println("</html>");

			}catch (FileUploadException e) {
				printErrorPage(out, "ファイルアップロード処理に失敗しました。", upFileNm);
				em.cnvException(e, "ファイルアップロード処理に失敗しました。");
			} catch (Exception e) {
				printErrorPage(out, "ファイルアップロード処理に失敗しました。", upFileNm);
				em.cnvException(e, "ファイルアップロード処理に失敗しました。");
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			out = null;
		}
	}



	/**
	 * 文字列のエンコード
	 * @param argStr アップロード先ファイル名
	 * @return  エンコード後のストリング
	 *
	 */
	private String encodeStr(String argStr){

		String retStr = "";

		//リクエスト情報より対象ファイル名を取得
		if (argStr != null){

			String val = argStr;
			retStr = val;
			try {
				byte[] byteData = val.getBytes("ISO_8859_1");
				val = new String(byteData, ConstManager.SRV_URL_REQ_ENCODE);
				retStr = val;
			}catch(UnsupportedEncodingException e){
				System.out.println(e);
			}

		}

		return retStr;

	}


	/**
	 * アップロードするフィールド名のチェック
	 * @param   argLstField アップロード対象のフィールド名配列
	 *          argFieldNm  チェックするフィールド名
	 * @return  true ：アップロードを実行する
	 *          false：アップロードを実行しない
	 *
	 */
	private boolean chkFieldNm(String[] argLstField, String argFieldNm){

		boolean retBool = false;			// 戻り値の設定

		if ((argLstField != null)  && (!argFieldNm.equals(""))){
			//ファイル数分URLを生成
			for(int i = 0; i < argLstField.length; i++){
				// 対象フィールドの場合、trueを返す
				if (argLstField[i].equals(argFieldNm)) {
					retBool = true;
					break;
				}
			}
		}

		return retBool;
	}


	/**
	 * サーバーファイルチェック
	 * @param argServerFPath アップロード先ファイル名
	 *
	 * @return  true ：ファイルが存在する
	 *          false：ファイルが存在しない
	 *
	 */
	private boolean chkFileExists(String argServerFPath, PrintWriter out){

		boolean retBool = false;			// 戻り値の設定

		if (argServerFPath.equals("")) {
			return retBool;
		}

		//ファイルチェックオブジェクトの生成
		FileChk fileChk = new FileChk();

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());


			//ファイルコピーロジックを呼出す
			try {

				// ファイルの存在チェック
				if (fileChk.fileChkLogic(argServerFPath) > 0) {
//					System.out.println("ファイルがすでに存在します：" + argServerFPath);
					retBool = true;
				}

			} catch (Exception e) {
				printErrorPage(out, "ファイル存在チェックに失敗しました。", argServerFPath);
				em.cnvException(e, "ファイル存在チェックに失敗しました。");
				System.out.println(e);
			} finally {
				em = null;
				fileChk = null;
			}


		}catch(Exception e){

		}finally{

		}

		return retBool;

	}

	/**
	 * ファイルコピー（サーバー間）
	 * @param argInFPath  コピー元ファイルパス
	 *        argOutFPath コピー先ファイルパス
	 * @return  true ：ファイルが存在する
	 *          false：ファイルが存在しない
	 *
	 */
	private boolean fileCopy(String argInFPath, String argOutFPath, PrintWriter out){

		boolean retBool = false;			// 戻り値の設定

		//ファイルチェックオブジェクトの生成
		FileChk fileChk = new FileChk();
		//ファイルシステムオブジェクトの生成
		FileCreate fileCreate = new FileCreate();

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());


			//ファイルコピーロジックを呼出す
			try {

				// ファイルの存在チェック
				if (fileChk.fileChkLogic(argOutFPath) > 0) {
					System.out.println("ファイルがすでに存在します：" + argOutFPath);
					retBool = true;
				}

				// ファイルの存在チェック
				fileCreate.fileCopyLogic(argInFPath, argOutFPath);

			} catch (Exception e) {
				printErrorPage(out, "ファイルコピー処理に失敗しました。", "");
				em.cnvException(e, "ファイルコピー処理に失敗しました。");
				System.out.println(e);
			} finally {
				em = null;
				fileChk = null;
			}


		}catch(Exception e){

		}finally{

		}

		return retBool;

	}

	/**
	 *ファイル削除
	 * @param   argServerFPath： 削除ファイル名（フルパス）
	 *          argFolder  ： フォルダー名
	 * @return  true ：ファイルが存在する
	 *          false：ファイルが存在しない
	 *
	 */
	private boolean fileDelete(String argServerFPath, String argFolder, PrintWriter out){

		boolean retBool = false;			// 戻り値の設定

		//ファイル削除オブジェクトの生成
		FileDelete fileDel = new FileDelete();

		//ファイルチェックオブジェクトの生成
		FileChk fileChk = new FileChk();

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());

			//ファイル削除ロジックを呼出す
			try {
				// ファイルが存在する時
				if (chkFileExists(argServerFPath, out)) {
					// ファイル削除の実行
					fileDel.fileDeleteLogic(argServerFPath);

					// フォルダー内ファイルをチェック
					if (!argFolder.equals("")) {
						// フォルダー内が空の時
						if (fileChk.fileListChkLogic(argFolder) == null) {
							// フォルダー削除
							fileDel.fileDeleteLogic(argFolder);
						}
					}

					retBool = true;
				}

			} catch (Exception e) {
				printErrorPage(out, "ファイル削除処理に失敗しました。", argServerFPath);
				em.cnvException(e, "ファイル削除処理に失敗しました。");
				System.out.println(e);
			} finally {
				em = null;
				fileDel = null;
			}

		}catch(Exception e){

		}finally{

		}

		return retBool;

	}

	/**
	 * アップロード先フォルダ作成
	 * @param argServerFPath フォルダ名
	 * @return
	 *
	 */
	private void createDir(String argServerFPath, PrintWriter out){

		//ファイルシステムオブジェクトの生成
		FileCreate fileCreate = new FileCreate();

		try {
			ExceptionManager em = new ExceptionManager(this.getClass().getName());

			//ファイルチェックロジックを呼出す
			try {
				// ディレクトリ作成処理
				fileCreate.createDirLogic(argServerFPath);

			} catch (Exception e) {
				printErrorPage(out, "ディレクトリ作成処理に失敗しました。", argServerFPath);
				em.cnvException(e, "ディレクトリ作成処理に失敗しました。");
				System.out.println(e);
			} finally {
				em = null;
				fileCreate = null;
			}


		}catch(Exception e){

		}finally{

		}

	}

	/**
	 * アップロード完了画面ヘッダー部を出力
	 * @param  out    PrintWriter
	 *         strUrl 遷移元JSPのURL
	 *
	 */
	private void printHeadPage(PrintWriter out, String strUrl){

		try{

			// 遷移元URLに戻る
			String url = "window.open(\"" + strUrl + "\"";
			url += " ,\"_self\",\"menubar=no,resizable=yes\"); ";

			//HTML 出力
			out.println("<html>");
			out.println("    <head>");
			out.println("        <title>アップロード画面</title>");
			out.println("        <script>");
			out.println("        <!--");
			out.println("            function kakunin() {");
			out.println("                var frm = document.frm00; ");
			out.println("                var msg = \"FileUpload ：\"; ");
			out.println("                msg += frm.msg.value; ");

//			out.println("                alert(msg);");          // 	メッセージを表示しない
			//                           遷移元JSPをオープン
			out.println( url );
//			                             画面を閉じる（window名がない時）
//			out.println("                this.window.close();");
			out.println("            }");
			out.println("        //-->");
			out.println("        </script>");
			out.println("<style type=\"text/css\">");
			out.println(".title {");
			out.println("    height:22px;");
			out.println("    font-size:16px;");
			out.println("    font-weight:bold;");
			out.println("    background-color=#8380F5;");
			out.println("    color=#FFFFFF;");
			out.println("    text-align=center;");
			out.println("}");
			out.println("</style>");
			out.println("    </head>");
			out.println("    <body onload=\"kakunin()\">");
			out.println("    <form name=\"frm00\" method=\"POST\" enctype=\"multipart/form-data\"  >");

		}catch(Exception e){

		}finally{
		}

	}

	/**
	 * アップロード処理エラーメッセージを出力
	 * @param out    PrintWriter
	 *        strHeder メッセージヘッダー
	 *        strMsg   メッセージ
	 */
	private void printErrorPage(PrintWriter out, String strHeder, String strMsg){


		try{
			String msg = strHeder;
			msg += "\n処理をやり直してください。";


			//HTML 出力
			out.println("<html>");
			out.println("    <head>");
			out.println("        <title>アップロード処理</title>");
			out.println("        <script>");
			out.println("        <!--");
			out.println("            function kakunin() {");
			out.println("                var frm = document.frm00; ");
			out.println("                var msg = \"\"; ");
			out.println("                msg += frm.msg.value; ");
			out.println("                alert(msg);");
//			out.println("                alert();");
//			                             画面を閉じる（window名がない時）
			out.println("                this.window.close();");
			out.println("            }");
			out.println("        //-->");
			out.println("        </script>");
			out.println("    </head>");
			out.println("    <body onload=\"kakunin()\" style=\"margin:10px;\">");
			out.println("    <form name=\"frm00\" method=\"POST\" >");
			out.println("<br/><br/>");
			out.println(   strMsg);
			out.println("      <input type=\"hidden\" name=\"msg\" value =\"" + msg + "\">");
			out.println("    </form>");
			out.println("    </form>");
			out.println("    </body>");
			out.println("</html>");

		}catch(Exception e){

		}finally{
		}
	}

}
