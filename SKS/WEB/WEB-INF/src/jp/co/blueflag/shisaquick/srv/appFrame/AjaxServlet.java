package jp.co.blueflag.shisaquick.srv.appFrame;

import java.io.PrintWriter;

import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;

import jp.co.blueflag.shisaquick.srv.base.ChangeXML;
import jp.co.blueflag.shisaquick.srv.base.CreateXML;
import jp.co.blueflag.shisaquick.srv.base.HttpServletBase;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.ResponsData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionBase;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.manager.DataCheckManager;
import jp.co.blueflag.shisaquick.srv.manager.InputCheckManager;
import jp.co.blueflag.shisaquick.srv.manager.LogicManager;
import jp.co.blueflag.shisaquick.srv.manager.RequestContoroler;
import jp.co.blueflag.shisaquick.srv.manager.UserInfoManager;


/**
 * 
 * AjaxServlet
 *  : XML通信、操作、セッション管理、インプットチェックを管理する。
 * 
 * @author TT.furuta
 * @since  2009/03/23
 */
public class AjaxServlet extends HttpServletBase implements SingleThreadModel {

	//返却用XMLオブジェクト
	private Document document = null;
	//リクエストデータ保持クラス
	private RequestData reqData = null;
	//レスポンスデータ保持クラス
	private ResponsData respData = null;
	//ユーザー情報管理
	private UserInfoData userInfoData = null;
	
	/**
	 * HttpPost送受信
	 *  : POST通信の送受信を行う。
	 * @param req : サーブレットリクエスト
	 * @param resp : サーブレットレスポンス
	 */
	public void doPost(HttpServletRequest _req, HttpServletResponse _res) {

		//クラス変数の初期化　
		reqData = null;
		respData = null;
		document = null;
		userInfoData = null;
		
		ClassPathResource cls_rsr = null;
		XmlBeanFactory factory = null;
		ChangeXML changeXML = null;
		UserInfoManager userInfoManager = null;
		InputCheckManager inputCheckManager = null;
		RequestContoroler requestContoroler = null;
		
		try{
			
			//基底クラス（HttpServletBase）の初期処理を行う
			super.initServlet(_req, _res);

			//DI設定ファイル読み込み
			cls_rsr = new ClassPathResource(ConstManager.APPCONTEXT_NM_MANAGER);
			factory = new XmlBeanFactory(cls_rsr);

			//④サーブレットリクエストを基に、RequestDataを生成する
			//	XML情報抽出処理呼出
			changeXML = (ChangeXML)factory.getBean("DI_ChangeXML");
			reqData = changeXML.createRequestData(req);

			//⑤リクエストセッション情報用い、ユーザー情報管理呼出処理を呼出す。
			//	ユーザー情報管理呼出（セッションの確認、ユーザー情報の取得を行う）
			userInfoManager = (UserInfoManager)factory.getBean("DI_UserInfoManager");
			userInfoData = userInfoManager.userInfoManager(req, reqData);

			try{
				//⑥インプットチェック管理を呼出し、各インプットチェックを行う。
				//	インプットチェック管理呼出
				inputCheckManager = (InputCheckManager)factory.getBean("DI_InputCheckManager");
				inputCheckManager.inputCheckmanager(reqData, userInfoData);

			} catch (ExceptionWaning e) {
				//何もしない
				e = null;
			}

			//⑦業務ロジックを処理する
			//	リクエストコントローラー呼出
			requestContoroler = (RequestContoroler)factory.getBean("DI_RequestContoroler");
			respData = requestContoroler.mainContorol(reqData, userInfoData);

		} catch (Exception e){
			//⑨例外処理（例外を処理結果に変換する）
			respData = ExceptionProc(e);
			e = null;
						
		}finally{

			//⑩XMLID毎の処理結果を付加する
			AddResult();

			//⑪XMLID毎のUSERINFOを付加する
			AddUserInfo();

			//⑧メンバrespDataよりXMLｵﾌﾞｼﾞｪｸﾄを生成し、レスポンス通信を行う
			ResponsProc(res, respData, factory);	
			
			//○ローカルクラスの破棄
			cls_rsr = null;
			factory = null;
			changeXML = null;
			userInfoManager = null;
			inputCheckManager = null;
			requestContoroler = null;

			//○インスタンスクラスの破棄
			//リクエストデータBeanの開放
			reqData.RemoveList();
			reqData = null;
			//レスポンスデータBeanの開放
			respData.RemoveList();
			respData = null;
			//エクセプションマネージャ
			em = null;
			//smlドキュメント
			document = null;
			//ユーザーインフォデータ
			userInfoData.removeList();
			userInfoData = null;
			
		}
		//ガベージの実行
		//System.gc();
	
	}
	
	/**
	 * ﾚｽﾎﾟﾝｽ処理
	 *  : ﾚｽﾎﾟﾝｽ保持ｸﾗｽをXML変換しﾚｽﾎﾟﾝｽ送信する
	 * @param ex : 例外クラス
	 */
	private void ResponsProc(HttpServletResponse resp, ResponsData respData, XmlBeanFactory factory){
	
		PrintWriter out = null;
		
		//書込みオブジェクト生成
		try {
			
			//①：エンコードを行う。（text/xml; charset=UTF-8）
			resp.setContentType(ConstManager.SRV_XML_RESP_ENCODE);
			
			out = resp.getWriter();
			
			//②：メンバrespDataよりXMLｵﾌﾞｼﾞｪｸﾄを生成し、レスポンス通信を行う
			CreateXML createXML = (CreateXML)factory.getBean("DI_CreateXML");
			
			//XML情報生成処理呼出
			document = createXML.writeXMLInfo(respData);
			
			//ログ　コンソールにレスポンスXMLを表示する
			if (!ConstManager.getConstValue(Category.設定, "CONST_DEBUG_LEVEL").equals("0")){

				TransformerFactory tfactory2 = TransformerFactory.newInstance(); 
		        Transformer transforme2 = tfactory2.newTransformer(); 
		        transforme2.setOutputProperty(OutputKeys.ENCODING, "Shift_JIS");
		        transforme2.transform(new DOMSource(document), new StreamResult(System.out)); 
				
			}
			
			//③：TransformerFactoryを使用し、上記②で生成したレスポンスXMLを返却
	        TransformerFactory tfactory = TransformerFactory.newInstance();
	        Transformer transformer = tfactory.newTransformer();
	        transformer.transform(new DOMSource(document), new StreamResult(out));

		} catch (Exception e) {
			//④：XML生成が不可能な場合、空で返す。
			out.println("");
		} finally {
			out.close();
			out = null;
		}
	}

	/**
	 * ｴｸｾﾌﾟｼｮﾝﾚｽﾎﾟﾝｽ保持変換
	 *  : 異常情報の抽出・転機を行う。
	 * @param ex : 例外クラス
	 */
	private ResponsData ExceptionProc(Exception ex){
		
		//※ココでキャッチされるExceptionは下記２種のみ
		//１．インプットチェック/セッションチェックで明示的に発生させたエラー
		//２．AjaxServletで発生した、予期せぬ障害

		ExceptionBase ExBase = null;

		try{
			//Exceptionの種類を判定
			if ( ex.getClass().equals(ExceptionSystem.class) ) {
				ExBase = (ExceptionBase) ex;
	
			} else if ( ex.getClass().equals(ExceptionUser.class) ) {
				ExBase = (ExceptionBase) ex;
	
			} else if ( ex.getClass().equals(ExceptionWaning.class) ) {
				ExBase = (ExceptionBase) ex;
	
			} else {
				//２．AjaxServletで発生した、予期せぬ障害を、ExceptionSystemに変換する
				ExBase = em.cnvException(ex, "AjaxServletで、システム例外が発生しました。");
				
			}
			
			//responsの生成
			//Exceptionクラスの内容を、Responceデータに置き換える
			
			//XMLIDを取得
			String XmlID = "";
			if (null == reqData){
				XmlID = "Non XmlID";
	
			}else{
				XmlID = reqData.getID();
			}
			//レスポンス生成（機能ID層）
			RequestResponsKindBean respBean = new RequestResponsKindBean();
			respBean.setID("AjaxServlet");
			//成功可否
			respBean.addFieldVale(0, 0, "flg_return", "false");
			//メッセージ 
			respBean.addFieldVale(0, 0, "msg_error", ExBase.getUserMsg());
			//処理名称 
			respBean.addFieldVale(0, 0, "nm_class", ExBase.getClassName());
			//メッセージ番号 
			respBean.addFieldVale(0, 0, "no_errmsg", ExBase.getMsgNumber());
			//エラーコード 
			respBean.addFieldVale(0, 0, "cd_error", ExBase.getSystemErrorCD());
			//システムメッセージ 
			respBean.addFieldVale(0, 0, "msg_system", ExBase.getSystemErrorMsg());
			//デバックモード
			respBean.addFieldVale(0, 0, "debuglevel", 
					ConstManager.getConstValue(ConstManager.Category.設定, "CONST_DEBUG_LEVEL"));
	
			//レスポンスの生成
			if (null == respData){
				respData = new ResponsData(null);
				respData.setID(XmlID);
			}
			respData.AddItem(respBean);

		} catch (Exception exc){
			//失敗時返却結果を初期化
			respData = null;
		}
		//エクセプションクラスの破棄
		ExBase = null;

		return respData;
		
	}
	/**
	 * ⑩XMLID毎の処理結果を付加する
	 */
	private void AddResult(){

		String strMsg = "";
//ADD 2013/06/28 ogawa【QP@30151】No.38 Start
		String msg = "";
		String name;
//ADD 2013/06/28 ogawa【QP@30151】No.38 end
		RequestResponsKindBean ResultBean = null;

		try{
			
			//1)処理結果を判定する
			RequestResponsKindBean respNG = ResultCheck(); 

			//3)結果Beanに格納する
			
			//機能ID
			ResultBean = new RequestResponsKindBean(); 
			ResultBean.setID("RESULT");

			if (respNG == null){
				//成功の場合

				//処理判定
				ResultBean.addFieldVale(0, 0, "flg_return", "true");
				//メッセージ 
				try{
//MOD 2013/06/28 ogawa【QP@30151】No.38 Start
//修正前ソース
//					strMsg = ConstManager.getConstValue(Category.メッセージ, "S"+respData.getID());
//修正後ソース
					for (int i=0;i < respData.GetKindCnt();i++){
						
						//①：respDataよりレスポンス（ResponsData）を抽出
						RequestResponsKindBean respKinoData = (RequestResponsKindBean)respData.getItemList().get(i);
						
						for (int j=0;j < respKinoData.GetCnt();j++){
							RequestResponsTableBean respTblData = respKinoData.getTableItem(j);
							for (int k=0;k < respTblData.GetCnt();k++){
								RequestResponsRowBean rowdata = respTblData.getRowItem(k);
								for( int l=0; l < rowdata.GetCnt(); l++){
									
									name = rowdata.getFieldItem(l).getName();
									if(name.equals("msg_cd")){
										msg = rowdata.getFieldItem(l).getValue();
										if((msg != null) && (msg.length() != 0)){
											strMsg = ConstManager.getConstValue(Category.メッセージ, "S"+msg);
											break;
										}
									}
								}
							}
						}
						
					}

					if((msg == null) || (msg.length() == 0)){
						strMsg = ConstManager.getConstValue(Category.メッセージ, "S"+respData.getID());
					}
//MOD 2013/06/28 ogawa【QP@30151】No.38 end
				}catch(ExceptionWaning e){

				}
				ResultBean.addFieldVale(0, 0, "msg_error", strMsg);
				//処理名称 
				ResultBean.addFieldVale(0, 0, "nm_class", "");
				//メッセージ番号 
				ResultBean.addFieldVale(0, 0, "no_errmsg", "");
				//エラーコード 
				ResultBean.addFieldVale(0, 0, "cd_error", "");
				//システムメッセージ 
				ResultBean.addFieldVale(0, 0, "msg_system", "");
				//デバックモード
				ResultBean.addFieldVale(0, 0, "debuglevel",
						ConstManager.getConstValue(Category.設定, "CONST_DEBUG_LEVEL"));
				
			}else{
				//失敗の場合

				//処理判定
				ResultBean.addFieldVale(0, 0, "flg_return", "false");
				//メッセージ 
				try{
					strMsg = ConstManager.getConstValue(Category.メッセージ, "E"+respData.getID())
									+ "\n"
									+ respNG.getFieldVale(0, 0, "msg_error");
				}catch(ExceptionWaning e){

				}
				ResultBean.addFieldVale(0, 0, "msg_error", strMsg);
				//処理名称 
				ResultBean.addFieldVale(0, 0, "nm_class",  
						respNG.getFieldVale(0, 0, "nm_class"));
				//メッセージ番号 
				ResultBean.addFieldVale(0, 0, "no_errmsg",  
						respNG.getFieldVale(0, 0, "no_errmsg"));
				//エラーコード 
				ResultBean.addFieldVale(0, 0, "cd_error",  
						respNG.getFieldVale(0, 0, "cd_error"));
				//システムメッセージ 
				ResultBean.addFieldVale(0, 0, "msg_system",  
						respNG.getFieldVale(0, 0, "msg_system"));
				//デバックモード
				ResultBean.addFieldVale(0, 0, "debuglevel",  
						ConstManager.getConstValue(Category.設定, "CONST_DEBUG_LEVEL"));
			}
			//4)結果Bean(ResponsData)に追加する
			respData.AddKind(ResultBean,0);
			
		}catch(Exception e){

		}finally{
			ResultBean = null;
		}
	}

	/**
	 * ⑩ 1)処理結果を判定する
	 */
	private RequestResponsKindBean ResultCheck(){

		RequestResponsKindBean ret = null;

		try{
			//機能ID毎の処理結果を判定し、失敗（false）が会ったらBeanを返却する。
	        for (int ix=0;ix < respData.GetCnt();ix++){
	        	//不成功の機能IDを探し出す
	        	if( respData.GetValue(ix, 0, 0, "flg_return")=="false"){
	        		ret =  (RequestResponsKindBean) respData.GetItem(ix);
	        	}
	        }

		}catch(Exception e){

		}finally{

		}
		return ret;
	}
	
	/**
	 * USERINFOXMLオブジェクト生成
	 * @param document : XMLドキュメント
	 * @param elmXML : XMLIDエレメント
	 * @return ユーザー基本情報のエレメント
	 */
	private void AddUserInfo(){
		
		RequestResponsKindBean UserInfoBean = null;
		
		try{
			//1)結果Beanに格納する

			//機能ID
			UserInfoBean = new RequestResponsKindBean(); 
			UserInfoBean.setID("USERINFO");

			//①：ユーザー基本情報データ保持クラスより、データを取得し、XMLドキュメントに設定する。
	        if (null == userInfoData.getId_data()){

        		//権限（画面ID,機能ID,データID）が無い場合
        		
	        	//処理結果①
	        	UserInfoBean.addFieldVale(0, 0, "flg_return", "ture");
	        	//処理結果②
	        	UserInfoBean.addFieldVale(0, 0, "msg_error", "");
	        	//処理結果③
	        	UserInfoBean.addFieldVale(0, 0, "nm_class", "");
	        	//処理結果④
	        	UserInfoBean.addFieldVale(0, 0, "no_errmsg", "");
	        	//処理結果⑤
	        	UserInfoBean.addFieldVale(0, 0, "cd_error", "");
	        	//処理結果⑥
	        	UserInfoBean.addFieldVale(0, 0, "msg_system", "");
	        	//システムバージョン設定
	        	UserInfoBean.addFieldVale(0, 0, "systemversion", "");
	        	//ユーザーID設定
	        	UserInfoBean.addFieldVale(0, 0, "id_user", userInfoData.getId_user());
	        	//ユーザー名称設定
	        	UserInfoBean.addFieldVale(0, 0, "nm_user", userInfoData.getNm_user());
	        	//会社コード設定
	        	UserInfoBean.addFieldVale(0, 0, "cd_kaisha", userInfoData.getCd_kaisha());
	        	//会社名設定
	        	UserInfoBean.addFieldVale(0, 0, "nm_kaisha", userInfoData.getNm_kaisha());
	        	//部署コード設定
	        	UserInfoBean.addFieldVale(0, 0, "cd_busho", userInfoData.getCd_busho());
	        	//部署名設定
	        	UserInfoBean.addFieldVale(0, 0, "nm_busho", userInfoData.getNm_busho());
	        	//グループCD設定
	        	UserInfoBean.addFieldVale(0, 0, "cd_group", userInfoData.getCd_group());
	        	//グループ名称設定
	        	UserInfoBean.addFieldVale(0, 0, "nm_group", userInfoData.getNm_group());
	        	//チームCD設定
	        	UserInfoBean.addFieldVale(0, 0, "cd_team", userInfoData.getCd_team());
	        	//チーム名称設定
	        	UserInfoBean.addFieldVale(0, 0, "nm_team", userInfoData.getNm_team());
	        	//リテラルCD設定
	        	UserInfoBean.addFieldVale(0, 0, "cd_literal", userInfoData.getCd_literal());
	        	//リテラル名称設定
	        	UserInfoBean.addFieldVale(0, 0, "nm_literal", userInfoData.getNm_literal());
	        	//画面ID設定
	        	UserInfoBean.addFieldVale(0, 0, "id_gamen", "");
	        	//機能ID設定
	        	UserInfoBean.addFieldVale(0, 0, "id_kino", "");
	        	//データID設定
	        	UserInfoBean.addFieldVale(0, 0, "id_data", "");
	        	//デバッグレベル設定
	        	UserInfoBean.addFieldVale(0, 0, "debuglevel",
	        			ConstManager.getConstValue(Category.設定, "CONST_DEBUG_LEVEL"));
	        	
	        } else {

	        	for (int i=0;i<userInfoData.getId_data().size();i++){

	        		//権限（画面ID,機能ID,データID）が有る場合
	        		
		        	//処理結果①
		        	UserInfoBean.addFieldVale(0, i, "flg_return", "ture");
		        	//処理結果②
		        	UserInfoBean.addFieldVale(0, i, "msg_error", "");
		        	//処理結果③
		        	UserInfoBean.addFieldVale(0, i, "nm_class", "");
		        	//処理結果④
		        	UserInfoBean.addFieldVale(0, i, "no_errmsg", "");
		        	//処理結果⑤
		        	UserInfoBean.addFieldVale(0, i, "cd_error", "");
		        	//処理結果⑥
		        	UserInfoBean.addFieldVale(0, i, "msg_system", "");
		        	//システムバージョン設定
		        	UserInfoBean.addFieldVale(0, i, "systemversion", userInfoData.getSystemversion());
		        	//ユーザーID設定
		        	UserInfoBean.addFieldVale(0, i, "id_user", userInfoData.getId_user());
		        	//ユーザー名称設定
		        	UserInfoBean.addFieldVale(0, i, "nm_user", userInfoData.getNm_user());
		        	//会社コード設定
		        	UserInfoBean.addFieldVale(0, i, "cd_kaisha", userInfoData.getCd_kaisha());
		        	//会社名設定
		        	UserInfoBean.addFieldVale(0, i, "nm_kaisha", userInfoData.getNm_kaisha());
		        	//部署コード設定
		        	UserInfoBean.addFieldVale(0, i, "cd_busho", userInfoData.getCd_busho());
		        	//部署名設定
		        	UserInfoBean.addFieldVale(0, i, "nm_busho", userInfoData.getNm_busho());
		        	//グループCD設定
		        	UserInfoBean.addFieldVale(0, i, "cd_group", userInfoData.getCd_group());
		        	//グループ名称設定
		        	UserInfoBean.addFieldVale(0, i, "nm_group", userInfoData.getNm_group());
		        	//チームCD設定
		        	UserInfoBean.addFieldVale(0, i, "cd_team", userInfoData.getCd_team());
		        	//チーム名称設定
		        	UserInfoBean.addFieldVale(0, i, "nm_team", userInfoData.getNm_team());
		        	//リテラルCD設定
		        	UserInfoBean.addFieldVale(0, i, "cd_literal", userInfoData.getCd_literal());
		        	//リテラル名称設定
		        	UserInfoBean.addFieldVale(0, i, "nm_literal", userInfoData.getNm_literal());
		        	//画面ID設定
		        	UserInfoBean.addFieldVale(0, i, "id_gamen", userInfoData.getId_gamen().get(i).toString());
		        	//機能ID設定
		        	UserInfoBean.addFieldVale(0, i, "id_kino", userInfoData.getId_kino().get(i).toString());
		        	//データID設定
		        	UserInfoBean.addFieldVale(0, i, "id_data", userInfoData.getId_data().get(i).toString());
		        	//デバッグレベル設定
		        	UserInfoBean.addFieldVale(0, i, "debuglevel", 
		        			ConstManager.getConstValue(Category.設定, "CONST_DEBUG_LEVEL"));
		    
		        }
	        	
	        }
	        //レスポンスデータに追加
	        respData.AddKind(UserInfoBean,1);
        
		}catch(Exception e){

		}finally{
			UserInfoBean = null;
		}
	}

}
