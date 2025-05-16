package jp.co.blueflag.shisaquick.srv.manager;

import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
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
 * データチェック管理
 *  : 機能IDごとの　各データチェックを管理する。
 *
 */
public class DataCheckManager extends ObjectBase{

	//データチェック
	private DataCheck targetDataCheck = null;
	//ユーザー情報管理
	private UserInfoData userInfoData = null;
	
	/**
	 * データチェック管理用コンストラクタ : インスタンスを生成する
	 */
	public DataCheckManager() {
		//基底クラスのコンストラクタ
		super();

		//インスタンス変数の初期化
		targetDataCheck = null;
		userInfoData = null;
		
	}
	
	/**
	 * データチェック管理
	 *  : 機能IDごとの　各データチェック処理を管理する。
	 * @param requestData : 機能IDリクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void DataCheck(
			RequestResponsKindBean requestData
			,UserInfoData _userInfoData
		)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = _userInfoData;
	
		ClassPathResource cls_rsr = null;
		XmlBeanFactory factory = null;
		String strKinoID = null;
		
		try {
			
			//①：機能IDの取得
			strKinoID = requestData.getID();
			
			//②定数管理（ConstManager）ｸﾗｽより、”APPCONTEXT_NM_DATACHECK”の値を取得
			cls_rsr = new ClassPathResource(ConstManager.APPCONTEXT_NM_DATACHECK);
			factory = new XmlBeanFactory(cls_rsr);

			//③：②取得値でコンテナを生成
			targetDataCheck = (DataCheck)factory.getBean(strKinoID);
			
			//④：リクエストデータ保持を引数にして、メンバtargetDataCheckのexecDataCheckメソッドを呼び出す
			targetDataCheck.execDataCheck(requestData, userInfoData);

		//コンテナが存在しなかった場合
		} catch (NoSuchBeanDefinitionException e){
			//何もしない
			
		//インスタンスが存在しない場合Exceptionをスローする
		} catch (CannotLoadBeanClassException e){
			
			//警告例外をThrowする。
			em.ThrowException(ExceptionKind.警告Exception, "W000300", strKinoID, "", "");

			
		} catch (Exception e){
		
			em.ThrowException(e, "データチェック管理に失敗しました。");
			
		} finally {
			//クラスの破棄
			targetDataCheck = null;
			userInfoData = null;
			cls_rsr = null;
			factory = null;
			targetDataCheck = null;
			
		}

	}

}
