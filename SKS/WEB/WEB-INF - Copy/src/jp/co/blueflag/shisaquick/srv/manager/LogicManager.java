package jp.co.blueflag.shisaquick.srv.manager;

import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.ObjectBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * ロジック管理
 *  : 機能IDごとの　各業務ロジックを管理する。
 * @author TT.furuta
 * @since  2009/03/25
 */
public class LogicManager extends ObjectBase{
	
	//レスポンスデータ
	private RequestResponsKindBean responsData = null;
	//対象ロジッククラス
	private LogicBase targetLogic = null;
	//ユーザー情報管理
	private UserInfoData userInfoData = null;

	/**
	 * ロジック管理用コンストラクタ 
	 * : インスタンス生成
	 */
	public LogicManager() {
		//基底クラスのコンストラクタ
		super();

		//インスタンス変数の初期化
		responsData = null;
		targetLogic = null;
		userInfoData = null;
		
	}
	
	/**
	 * ロジック呼出
	 * ：各業務ロジックを呼出す。
	 * @param requestData  : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @return responsData：レスポンスデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean callLogic(
			RequestResponsKindBean requestData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		ClassPathResource cls_rsr = null;
		XmlBeanFactory factory = null;
		String strKinoID = null;

		//ユーザー情報退避
		userInfoData = _userInfoData;
		
		try {
			//①：機能ID取得
			strKinoID = requestData.getID();
			
			//②：定数管理（ConstManager）ｸﾗｽより、”APPCONTEXT_NM_LOGIC”の値を取得
			cls_rsr = new ClassPathResource(ConstManager.APPCONTEXT_NM_LOGIC);
			factory = new XmlBeanFactory(cls_rsr);

			//③：②取得値でコンテナを生成
			targetLogic = (LogicBase)factory.getBean(strKinoID);
						
			//④：リクエストデータを引数にして、メンバtargetLogicのexecLogicメソッドを呼び出す
			responsData = targetLogic.ExecLogic(requestData, userInfoData);
			
		//インスタンスが存在しない場合Exceptionをスローする
		} catch (CannotLoadBeanClassException e){
			//警告例外をThrowする。
			em.ThrowException(ExceptionKind.警告Exception, "W000400", strKinoID, "", "");

		} catch (Exception e){
			em.ThrowException(e, "ロジック管理に失敗しました。");
			
		} finally {
			
			//クラスの破棄
			targetLogic = null;
			userInfoData = null;
			cls_rsr = null;
			factory = null;
			targetLogic = null;
			
		}
		return responsData;

	}

}
