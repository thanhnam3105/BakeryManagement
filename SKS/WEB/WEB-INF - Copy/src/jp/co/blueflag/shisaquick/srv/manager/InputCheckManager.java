package jp.co.blueflag.shisaquick.srv.manager;

import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.ObjectBase;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * インプットチェック管理
 *  : XMLIDごとの　各インプットチェックを管理する。
 * @author TT.furuta
 * @since  2009/03/25
 */
public class InputCheckManager extends ObjectBase{

	//インプットチェッククラス
	private InputCheck inputCheck = null;
	//ユーザー情報管理
	private UserInfoData userInfoData = null;
	
	/**
	 * コンストラクタ
	 *  : インプットチェック管理用コンストラクタ
	 */
	public InputCheckManager() {
		//基底クラスのコンストラクタ
		super();
		
		//インスタンス変数の初期化
		inputCheck = null;
		userInfoData = null;
		
	}
	
	/**
	 * インプットチェック管理
	 *  : XMLIDごとの　各インプットチェック処理を管理する。
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void inputCheckmanager(
			RequestData reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//ユーザー情報退避
		userInfoData = _userInfoData;
		
		ClassPathResource cls_rsr = null;
		XmlBeanFactory factory = null;
		String strXmlID = null;
		
		try {
			
			//①：XMLID取得
			strXmlID = reqData.getID();
			
			//②定数管理（ConstManager）ｸﾗｽより、”APPCONTEXT_NM_INPUTCHECK”の値を取得
			cls_rsr = new ClassPathResource(ConstManager.APPCONTEXT_NM_INPUTCHECK);
			factory = new XmlBeanFactory(cls_rsr);

			//③：②取得値でコンテナを生成
			inputCheck = (InputCheck)factory.getBean(strXmlID);
						
			//④：リクエストデータ保持を引数にして、メンバIpCheckのexecInputCheckメソッドを呼び出す
			inputCheck.execInputCheck(reqData, userInfoData);
		
		//インスタンスが存在しない場合Exceptionをスローする
		} catch (CannotLoadBeanClassException e){
			//警告例外をThrowする。
			em.ThrowException(ExceptionKind.警告Exception, "W000200", strXmlID, "", "");

		}catch(NoSuchBeanDefinitionException e){
			//警告例外をThrowする。
			em.ThrowException(ExceptionKind.警告Exception, "W000200", strXmlID, "", "");
			
		} catch (Exception e){
		
			em.ThrowException(e, "インプットチェック管理に失敗しました。");
			
		} finally {

			//クラスの破棄
			inputCheck = null;
			userInfoData = null;
			cls_rsr = null;
			factory = null;
			inputCheck = null;
			
		}
		
	}

}
