
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Main extends HttpServlet {

	/**
	 * HttpGet送受信
	 *  : GET通信の送受信を行う。
	 * @param req : サーブレットリクエスト
	 * @param resp : サーブレットレスポンス
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		PrintWriter pw;
		
		resp.setContentType("text/html; charset=Shift_JIS");
		
		try {
			pw = resp.getWriter();
			pw.println("シサクイック_SRV");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
	}

	/**
	 * HttpPost送受信
	 *  : POST通信の送受信を行う。
	 * @param req : サーブレットリクエスト
	 * @param resp : サーブレットレスポンス
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

	}
}
