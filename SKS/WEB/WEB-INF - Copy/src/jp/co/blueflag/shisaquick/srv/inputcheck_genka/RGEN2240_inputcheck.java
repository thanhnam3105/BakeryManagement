package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 原価試算（営業）　印刷ｲﾝﾌﾟｯﾄﾁｪｯｸ :
 * 		印刷のｲﾝﾌﾟｯﾄﾁｪｯｸ
 *
 * @author TT.Kitazawa
 * @since 2015/03/03
 */
public class RGEN2240_inputcheck extends InputCheck {

	/**
	 * コンストラクタ : 原価試算（営業）　印刷ｲﾝﾌﾟｯﾄﾁｪｯｸコンストラクタ
	 */
	public RGEN2240_inputcheck() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  execInputCheck（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * インプットチェック管理 : 各データチェック処理を管理する。
	 *
	 * @param requestData
	 *            : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execInputCheck(
			RequestData checkData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		super.userInfoData = _userInfoData;

		try {
			// USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);


			//FGEN2240のインプットチェックを行う。
			insertValueCheck(checkData);



		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                           insertValueCheck（ｲﾝﾌﾟｯﾄﾁｪｯｸ開始開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 印刷項目チェック
	 *  : FGEN2240のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//テーブル：[table]のインプットチェック
			this.tableInsertValueCheck(checkData);



		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                              TableValueCheck（各ﾃｰﾌﾞﾙｲﾝﾌﾟｯﾄﾁｪｯｸ）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * テーブル：[table]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void tableInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN2240";
		String strTableNm = "table";

		try {
			//列が選択されているかを確認
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   試作SEQ
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//必須チェック
					super.hissuCodeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku"), "試作列", "E000404");

			}

			//７列以上選択されていないかを確認
			if(checkData.GetRecCnt(strKinoNm, strTableNm) > 6){
				// 必須入力不正をスローする。
				em.ThrowException(ExceptionKind.一般Exception, "E000218", "6", "", "");
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;

		}
	}

}
