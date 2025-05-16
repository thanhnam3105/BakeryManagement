package jp.co.blueflag.shisaquick.srv.manager;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.ResponsData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionBase;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 
 * リクエストコントロール
 *  : 受信したXML機能IDごとに業務ロジックを管理する。
 * @author TT.furuta
 * @since  2009/03/25
 */
public class RequestContoroler {

	//レスポンスデータ
	private ResponsData responseData = null;
	//機能リクエストデータ
	private RequestResponsKindBean reqKinoData = null;
	//機能レスポンスデータ
	private RequestResponsKindBean respKinoData = null;
	//ユーザー情報管理
	private UserInfoData userInfoData = null;
	
	/**
	 * コンストラクタ
	 *  : リクエストコントロール用コンストラクタ
	 */
	public RequestContoroler() {
		
		//インスタンス変数の初期化
		responseData = null;
		reqKinoData = null;
		respKinoData = null;
		userInfoData = null;
	}
	
	/**
	 * メインコントローラー
	 *  : 各ロジック管理コントローラー
	 * @param  requestData ：リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @param constManager : 設定情報
	 * @return responseData：レスポンスデータ
	 */
	public ResponsData mainContorol(
			RequestData requestData
			,UserInfoData _userInfoData
			) {

		//ユーザー情報退避
		userInfoData = _userInfoData;
		
		ClassPathResource cls_rsr = null;
		XmlBeanFactory factory = null;
		DataCheckManager dcManager = null;
		LogicManager lgManager = null;
		
		try {
						
			//レスポンスデータのインスタンス生成
			responseData = new ResponsData(requestData.getID());
			
			//DI設定ファイル読出
			cls_rsr = new ClassPathResource(ConstManager.APPCONTEXT_NM_MANAGER);
			factory = new XmlBeanFactory(cls_rsr);

			for (int i=0;i < requestData.GetKindCnt();i++){
			
				//①：requestDataより機能リクエスト（RequestResponsBean）を抽出
				reqKinoData = (RequestResponsKindBean) requestData.getItemList().get(i);
				
				//機能IDがUSERINFO以外の場合のみ以下実行
				if (!reqKinoData.getID().equals("USERINFO")){
						
					//②：データチェックを行う
					dcManager = (DataCheckManager)factory.getBean("DI_DataCheckManager");
					
					try {
						//機能リクエスト（RequestResponsBean）を引数にして、DataCheckMahagerｸﾗｽDataCheckﾒｿｯﾄを呼び出す。
						dcManager.DataCheck(reqKinoData, userInfoData);
						
					} catch (ExceptionWaning e) {
						//何もしない
						e = null;
						
					}
					
					//③：業務ロジックを実行する
					lgManager = (LogicManager)factory.getBean("DI_LogicManager");
					
					//機能リクエスト（RequestResponsBean）を引数にして、LogicManagerｸﾗｽcallLogicメソットを呼び出す。
					respKinoData = lgManager.callLogic(reqKinoData, userInfoData);
					
					//戻り値をメンバresponseDataのAddKindﾒｿｯﾄで追加する
					responseData.AddKind(respKinoData);
				}
			}
			
		} catch (Exception e) {
			
			//⑤：エクセプションの処理を行う
			responseData = exceptionProc(e);
			e = null;
			
		} finally {
			//クラスの破棄
			cls_rsr = null;
			factory = null;
			dcManager = null;
			lgManager = null;
			
		}
		//④：処理結果を返却する
		return responseData;

	}
	
	/**
	 * ｴｸｾﾌﾟｼｮﾝﾚｽﾎﾟﾝｽ保持変換
	 *  : 異常情報の抽出・転機を行う。
	 * @param ex : 例外クラス
	 */
	private ResponsData exceptionProc(Exception ex) {
		
		//※ココでキャッチされるExceptionは下記２種のみ
		//１．インプットチェック/セッションチェックで明示的に発生させたエラー
		//２．AjaxServletで発生した、予期せぬ障害

		ExceptionManager eManager = null;
		
		try{
			//Exceptionの種類を判定
			ExceptionBase e = null;
			if ( ex.getClass().equals(ExceptionSystem.class) ) {
				e = (ExceptionBase) ex;
	
			} else if ( ex.getClass().equals(ExceptionUser.class) ) {
				e = (ExceptionBase) ex;
	
			} else if ( ex.getClass().equals(ExceptionWaning.class) ) {
				e = (ExceptionBase) ex;
	
			} else {
				//２．予期せぬ障害を、ExceptionSystemに変換する
				eManager = new ExceptionManager(this.getClass().getName());
				e = eManager.cnvException(ex, "RequestContorolerで、システム例外が発生しました。");
				
			}
			
			//①：例外クラスを基にレスポンス保持を生成し、メンバrespDataに格納する
			RequestResponsKindBean KindBean = new RequestResponsKindBean();
			//機能IDを設定する
			KindBean.setID(reqKinoData.getID());
			
			//属性情報を設定する。
			
			//成功可否
			KindBean.addFieldVale("table", "rec", "flg_return", "false");
			//メッセージ 
			KindBean.addFieldVale("table", "rec", "msg_error", e.getUserMsg());
			//処理名称 
			KindBean.addFieldVale("table", "rec", "nm_class", e.getClassName());
			//メッセージ番号 
			KindBean.addFieldVale("table", "rec", "no_errmsg", e.getMsgNumber());
			//エラーコード 
			KindBean.addFieldVale("table", "rec", "cd_error", e.getSystemErrorCD());
			//システムメッセージ 
			KindBean.addFieldVale("table", "rec", "msg_system", e.getSystemErrorMsg());
			//デバックモード
			KindBean.addFieldVale("table", "rec", "debuglevel", ConstManager.getConstValue(Category.設定, "CONST_DEBUG_LEVEL"));
			
			//行追加
			responseData.AddKind(KindBean);

		} catch (Exception exc){
			//失敗時返却結果を初期化
			responseData = null;
			
		}finally{
			//クラスの破棄
			eManager = null;
			
		}
		return responseData;
	}

}
