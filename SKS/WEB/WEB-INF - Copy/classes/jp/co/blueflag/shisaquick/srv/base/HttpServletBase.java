package jp.co.blueflag.shisaquick.srv.base;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

public class HttpServletBase extends HttpServlet {
	
	//リクエスト
	protected HttpServletRequest req = null;
	//レスポンス
	protected HttpServletResponse res = null;
	//エクセプションマネージャー
	protected ExceptionManager em = null;
	
	/**
	 * HttpServlet通信基底クラス、初期化処理
	 * @param _req　:　Httpリクエスト
	 * @param _res　：　Httpレスポンス
	 */
	protected void initServlet(HttpServletRequest _req, HttpServletResponse _res){
		
		//エクセプションマネージャの生成
		this.em = new ExceptionManager(this.getClass().getName());
		//リクエストの退避
		req = _req;
		//レスポンスの退避
		res = _res;
		//コンスト情報の取得
		//ルートURL
		setRootURL();
		//ルートAPPパス
		setRootAppPath();
		
		//れクエストカウント
		ReqCount();
		
	}
	/**
	 * URLのルートを取得する
	 * @return String URLのルート
	 */
	private void setRootURL(){
		
		StringBuffer sbRequestURL = req.getRequestURL (); 
		String sServletPath = req.getServletPath (); 
		int index = sbRequestURL.lastIndexOf (sServletPath); 
		ConstManager.setRootURL(sbRequestURL.substring (0, index+1)); 
	
	}
	/**
	 * Tomcatのルートパスを取得する
	 * @return String ルートパス
	 */
	private void setRootAppPath(){

		ServletContext sc = getServletContext();
		ConstManager.setRootAppPath(sc.getRealPath("/"));
	
	}
	/**
	 * アクセスカウント数の表示
	 */
	private void ReqCount(){
		
		try{
			if (ConstManager.getConstValue(Category.設定, "CONST_DEBUG_LEVEL") != "0"){
				ConstManager.ReqCount++;
				System.out.print("●Req Count =[" + Long.toString(ConstManager.ReqCount) + "] \n");
				
			}
			
		}catch(Exception e){
			
		}
		
	}

}
