
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Main extends HttpServlet {

	/**
	 * HttpGet����M
	 *  : GET�ʐM�̑���M���s���B
	 * @param req : �T�[�u���b�g���N�G�X�g
	 * @param resp : �T�[�u���b�g���X�|���X
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		PrintWriter pw;
		
		resp.setContentType("text/html; charset=Shift_JIS");
		
		try {
			pw = resp.getWriter();
			pw.println("�V�T�N�C�b�N_SRV");
		} catch (IOException e) {
			// TODO �����������ꂽ catch �u���b�N
			e.printStackTrace();
		}
		
	}

	/**
	 * HttpPost����M
	 *  : POST�ʐM�̑���M���s���B
	 * @param req : �T�[�u���b�g���N�G�X�g
	 * @param resp : �T�[�u���b�g���X�|���X
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

	}
}
