/* 
 * ダウンロードファイル削除用通信クラス
 * 通信情報より取得したファイルパスのファイルを削除
 */
package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.base.HttpServletBase;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;

/**
 * 
 * Class名　: ファイルダウンロード通信
 * 処理概要 : ファイルダウンロード用のURL通信を行い、対象画面を開く　
 *           （正常時：ダウンロード画面　・　異常時：エラー画面）
 *   
 * @since   2009/03/16
 * @author  TT.Furuta
 * @version 1.0
 */
public class FileDownLoadServlet extends HttpServletBase {
	
	/**
	 * HttpGet送受信
	 *  : GET通信の送受信を行う。
	 * @param req : サーブレットリクエスト
	 * @param resp : サーブレットレスポンス
	 */
	public void doGet(HttpServletRequest HttpReq, HttpServletResponse HttpRes) {

		//http://localhost:8080/Shisaquick_SRV/FileDownLoadServlet?FName=a:::b
		//a:::b　=　ダウンロード対象、ファイルa　ファイルb
		BufferedReader br;
		
		//基底クラス（HttpServletBase）の初期処理を行う
		super.initServlet(HttpReq, HttpRes);
		
		String strFileNames = "";
		
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
		    try {
		    	byte[] byteData = val.getBytes("ISO_8859_1");
		    	val = new String(byteData, "Shift_JIS");
		    	strFileNames = val;
		    }catch(UnsupportedEncodingException e){
		    	System.out.println(e);
		    }
			
		}
		
		//ダウンロードページの出力
		CreatePage(strFileNames);
	}

	private void CreatePage(String FNames){
		
		ArrayList<String> listFUrl = null;
		
		//ダウンロードURL編集
		listFUrl = MakeFileURL(FNames);
		
		//ダウンロードページ出力
		printPage(listFUrl);
		
		listFUrl = null;
		
	}
	/**
	 * ファイル名をダウンロードURLに変換する
	 * @param FNames : ファイル名
	 * @return ArrayList<String> : URLのリスト
	 */
	private ArrayList<String> MakeFileURL(String FNames){

		ArrayList<String> ret = null;
		String[] listFName = null;
		
		try{
			
			ret = new ArrayList<String>();
			
			//ファイル別に分解
			listFName = FNames.split(":::");
			//ファイル数分URLを生成
			for(int i = 0; i < listFName.length; i++){
				//ダウンロードURLを格納
				ret.add(listFName[i]);
			}
			
		}catch(Exception e){
			
		}finally{
			listFName = null;
			
		}
		return ret;
		
	}
	/**
	 * ダウンロードページを出力する
	 * @param listFUrl ダウンロード用のURLリスト
	 */
	private void printPage(ArrayList<String> listFUrl){

		StringBuffer strFName = new StringBuffer();

		try{
			//ContentType を設定
			res.setContentType("text/html; charset=Shift_JIS");

			//出力用 PrintWriter を取得
			PrintWriter out = res.getWriter();
			    
			String[] strRootPath;
			strRootPath = ConstManager.getRootURL().split("/");

			//HTML 出力 
			out.println("<html>");
			out.println("    <head>");
			out.println("        <title>ダウンロード画面</title>");
			out.println("        <script>");
			out.println("        <!--");
			out.println("            function funFileDelete() {");
			out.println("                document.frm00.action='/" + strRootPath[strRootPath.length-1] + "/FileDeleteServlet';");
			out.println("                document.frm00.submit();");
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
			out.println("    <body onUnload=\"funFileDelete()\">");   
			out.println("    <form name=\"frm00\" method=\"POST\" >");  
			out.println("        <center>");
			out.println("            <br><br>");
			out.println("                <table>");
			out.println("                    <tr>");
			out.println("                        <td align=\"center\" class=\"title\" width =\"220\">ファイルダウンロード</td>");
			out.println("                    </tr>");
			out.println("                </table>");
			out.println("            <br><br><br>");
			out.println("ダウンロードボタンをクリックしてファイルを保存して下さい。");
			out.println("            <br><br><br><br>");
			out.println("            <table>");
			
			//ファイル数リンクを作る
			for (int i = 0; i < listFUrl.size(); i++){
				out.println("<tr><td>");
				out.println(listFUrl.get(i));
				out.println("</td><td>");
//				out.println("<input type=\"button\" value=\"ダウンロード\" onclick=\"javascript:location.href='/Shisaquick_SRV/FileDownloadExec?FName=" + listFUrl.get(i).split("/")[listFUrl.get(i).split("/").length-1] + "';\">");
				out.println("<input type=\"button\" value=\"ダウンロード\" onclick=\"javascript:location.href='/" + strRootPath[strRootPath.length-1] + "/FileDownloadExec?FName=" + listFUrl.get(i) + "';\">");
				out.println("</td></tr>");

				if (i != 0) {
					//区切り文字を付加する
					strFName.append(":::");
				}
//				strFName.append(listFUrl.get(i).split("/")[listFUrl.get(i).split("/").length-1]);
				strFName.append(listFUrl.get(i));
			}

			out.println("            </table>");
			out.println("        </center>");
			out.println("        <input type=\"hidden\" name=\"FName\" id=\"FName\" value=\"" + strFName + "\">");
			out.println("    </form>");   
			out.println("    </body>");
			out.println("</html>");
			
		}catch(Exception e){
			
		}finally{
			//変数の削除
			strFName = null;
		}
		
	}
	
}