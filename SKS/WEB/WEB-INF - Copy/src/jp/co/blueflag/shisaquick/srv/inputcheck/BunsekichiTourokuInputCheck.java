package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 *
 * 【JW820】 分析値入力画面リクエスト登録 インプットチェック
 *
 */
public class BunsekichiTourokuInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 *  : 試作データ画面 初期表示時インプットチェック用コンストラクタ
	 */
	public BunsekichiTourokuInputCheck() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}

	/**
	 * インプットチェック管理
	 *  : 各データチェック処理を管理する。
	 * @param requestData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 */
	public void execInputCheck(
			RequestData checkData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		super.userInfoData = _userInfoData;

		try {
			//USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

			//SA380のインプットチェックを行う。
			kanriValueCheck(checkData);

// 20160607  KPX@1502111_No.5 ADD start
			// 機能ID：FGEN2250のインプットチェックを行う。
			renkeiValueCheck(checkData);
// 20160607  KPX@1502111_No.5 ADD end


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 更新内容チェック
	 *  : SA380のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kanriValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//
			if ( checkData.GetValueStr("SA380", 0, 0, "kbn_shori").equals("0") ) {

				//処理区分＝登録の場合、登録値チェックを行う。
				this.insertValueCheck(checkData);

			} else if ( checkData.GetValueStr("SA380", 0, 0, "kbn_shori").equals("1") )  {

				//処理区分＝更新の場合、更新値チェックを行う。
				this.updateValueCheck(checkData);

			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 登録値 インプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA380";

			///
			///必須項目の入力チェック
			///
		    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_kaisha"),"会社コード");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_genryo"),"原料コード");
	    	super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "nm_genryo"),"原料名");

		    ///
			///入力桁数チェック
		    ///
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"nm_genryo"),"原料名",60);
	    	super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"nm_genryo"),"原料名",60,30);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"hyojian"),"表示案",200);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"tenkabutu"),"添加物",200);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"memo"),"メモ",200);
//20160819  KPX@1502111 MOD start
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"栄養計算食品番号1",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"栄養計算食品番号2",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"栄養計算食品番号3",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"栄養計算食品番号4",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"栄養計算食品番号5",10);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"栄養計算食品番号1",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"栄養計算食品番号2",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"栄養計算食品番号3",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"栄養計算食品番号4",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"栄養計算食品番号5",10,5);
//20160819  KPX@1502111 MOD end
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"割合1",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"割合2",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"割合3",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"割合4",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"割合5",10);
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai1")) != ""){
				//数値チェック
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"割合1");
				//数値範囲チェック
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai1"))
						, 0.01
						, 999.99
						, "割合1");
				//少数桁数
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai1"))
						,2
						,"割合1");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai2")) != ""){
				//数値チェック
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"割合2");
				//数値範囲チェック
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai2"))
						, 0.01
						, 999.99
						, "割合2");
				//少数桁数
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai2"))
						,2
						,"割合2");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai3")) != ""){
				//数値チェック
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"割合3");
				//数値範囲チェック
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai3"))
						, 0.01
						, 999.99
						, "割合3");
				//少数桁数
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai3"))
						,2
						,"割合3");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai4")) != ""){
				//数値チェック
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"割合4");
				//数値範囲チェック
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai4"))
						, 0.01
						, 999.99
						, "割合4");
				//少数桁数
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai4"))
						,2
						,"割合4");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai5")) != ""){
				//数値チェック
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"割合5");
				//数値範囲チェック
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai5"))
						, 0.01
						, 999.99
						, "割合5");
				//少数桁数
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai5"))
						,2
						,"割合5");

			}

			///
			/// 数値項目については、数字範囲チェック
			///
		    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan")),0,999.99,"酢酸（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen")),0,999.99,"食塩（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan")),0,999.99,"総酸（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura")),0,999.99,"油含有率（%）");
			}
			// ADD start 20121005 QP@20505 No.24
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg")),0,999.99,"ＭＳＧ（%）");
			}
			// ADD end 20121005 QP@20505 No.24

			///
			/// 小数点以下が入力される項目については、小数桁数チェック
			///
		    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan")),2,"酢酸（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen")),2,"食塩（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan")),2,"総酸（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura")),2,"油含有率（%）");
			}
			// ADD start 20121005 QP@20505 No.24
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg")),2,"ＭＳＧ（%）");
			}
			// ADD end 20121005 QP@20505 No.24

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
	}

	/**
	 * 更新値インプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void updateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA380";

			///
			///必須項目の入力チェック
			///
		    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_kaisha"),"会社コード");
		    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_genryo"),"原料コード");
	    	super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "nm_genryo"),"原料名");
//		    if ( checkData.GetValueStr(strKinoNm, 0, 0, "kbn_haishi").equals("1") ) {
//			    //廃止がチェックされている場合
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_kakutei"),"確定コード");
//		    }

		    ///
			///入力桁数チェック
		    ///
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"nm_genryo"),"原料名",60);
	    	super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"nm_genryo"),"原料名",60,30);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"hyojian"),"表示案",200);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"tenkabutu"),"添加物",200);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"memo"),"メモ",200);
//20160819  KPX@1502111 MOD start
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"栄養計算食品番号1",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"栄養計算食品番号2",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"栄養計算食品番号3",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"栄養計算食品番号4",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"栄養計算食品番号5",10);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"栄養計算食品番号1",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"栄養計算食品番号2",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"栄養計算食品番号3",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"栄養計算食品番号4",10,5);
			super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"栄養計算食品番号5",10,5);
//20160819  KPX@1502111 MOD end
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"割合1",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"割合2",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"割合3",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"割合4",10);
//			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"割合5",10);
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai1")) != ""){
				//数値チェック
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"割合1");
				//数値範囲チェック
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai1"))
						, 0.01
						, 999.99
						, "割合1");
				//少数桁数
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai1"))
						,2
						,"割合1");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai2")) != ""){
				//数値チェック
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"割合2");
				//数値範囲チェック
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai2"))
						, 0.01
						, 999.99
						, "割合2");
				//少数桁数
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai2"))
						,2
						,"割合2");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai3")) != ""){
				//数値チェック
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"割合3");
				//数値範囲チェック
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai3"))
						, 0.01
						, 999.99
						, "割合3");
				//少数桁数
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai3"))
						,2
						,"割合3");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai4")) != ""){
				//数値チェック
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"割合4");
				//数値範囲チェック
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai4"))
						, 0.01
						, 999.99
						, "割合4");
				//少数桁数
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai4"))
						,2
						,"割合4");

			}
			if(toString(checkData.GetValueStr(strKinoNm,0,0,"wariai5")) != ""){
				//数値チェック
				numberCheck(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"割合5");
				//数値範囲チェック
				rangeNumCheck(
						toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai5"))
						, 0.01
						, 999.99
						, "割合5");
				//少数桁数
				shousuRangeCheck(toDouble(checkData.GetValueStr(strKinoNm,0,0,"wariai5"))
						,2
						,"割合5");

			}

			///
			/// 数値項目については、数字範囲チェック
			///
		    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan")),0,999.99,"酢酸（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen")),0,999.99,"食塩（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan")),0,999.99,"総酸（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura")),0,999.99,"油含有率（%）");
			}
			// ADD start 20121005 QP@20505 No.24
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg").isEmpty()) ) {
				super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg")),0,999.99,"ＭＳＧ（%）");
			}
			// ADD end 20121005 QP@20505 No.24

			///
			/// 小数点以下が入力される項目については、小数桁数チェック
			///
		    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sakusan")),2,"酢酸（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_shokuen")),2,"食塩（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_sousan")),2,"総酸（%）");
			}
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_abura")),2,"油含有率（%）");
			}
			// ADD start 20121005 QP@20505 No.24
			if ( !(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg").isEmpty()) ) {
				super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm,0,0,"ritu_msg")),2,"ＭＳＧ（%）");
			}
			// ADD end 20121005 QP@20505 No.24

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}


// 20160607  KPX@1502111_No.5 ADD start
	/**
	 * 配合リンク更新内容チェック : 登録、更新内容の入力値チェックを行う。
	 *  : 機能ID：FGEN2250のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void renkeiValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {


		String strKinoNm = "FGEN2250";

		try {

			// 機能ID設定時のみチェックする
			if (checkData.GetItemNo(strKinoNm) > 0) {

				// 処理区分
				String strKbn = checkData.GetValueStr(strKinoNm, 0, 0, "kbn_shori");
				if (strKbn.equals("3")) {
					//処理モード：削除の場合
					// 試作No全てが空であること「入力内容が不正です。」
					// 試作Ｎｏ-社員CDの未入力チェック（スーパークラスのチェック）を行う。
					if (checkData.GetValueStr(strKinoNm, 0, 0, "cd_shain").length() > 0) {
						em.ThrowException(ExceptionKind.一般Exception, "E000206", "試作Ｎｏ", "", "");
					}

					// 試作Ｎｏ-年の未入力チェック（スーパークラスのチェック）を行う。
					if (checkData.GetValueStr(strKinoNm, 0, 0, "nen").length() > 0) {
						em.ThrowException(ExceptionKind.一般Exception, "E000206", "試作Ｎｏ", "", "");
					}

					// 試作Ｎｏ-追番の未入力チェック（スーパークラスのチェック）を行う。
					if (checkData.GetValueStr(strKinoNm, 0, 0, "no_oi").length() > 0) {
						em.ThrowException(ExceptionKind.一般Exception, "E000206", "試作Ｎｏ", "", "");
					}

					// 枝番号の未入力チェック（スーパークラスのチェック）を行う。
					if (checkData.GetValueStr(strKinoNm, 0, 0, "no_eda").length() > 0) {
						em.ThrowException(ExceptionKind.一般Exception, "E000206", "試作Ｎｏ", "", "");
					}

					// 試作SEQの未入力チェック（スーパークラスのチェック）を行う。
					if (checkData.GetValueStr(strKinoNm, 0, 0, "seq_shisaku").length() > 0) {
						em.ThrowException(ExceptionKind.一般Exception, "E000206", "サンプルＮｏ", "", "");
					}

				} else {
					//処理区分：登録・更新の場合

					// 試作Ｎｏ-社員CDの必須入力チェック（スーパークラスの必須チェック）を行う。
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_shain"),"試作Ｎｏ_社員コード");
					// 試作Ｎｏ-社員CDの入力チェック（スーパークラスのチェック）を行う。
					super.numberCheck(checkData.GetValueStr(strKinoNm, 0, 0, "cd_shain"),"試作Ｎｏ_社員コード");
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, 0, 0, "cd_shain"),"試作Ｎｏ_社員コード", 10);


					// 試作Ｎｏ-年の必須入力チェック（スーパークラスの必須チェック）を行う。
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "nen"),"試作Ｎｏ_年)");
					// 試作Ｎｏ-年の入力チェック（スーパークラスのチェック）を行う。
					super.numberCheck(checkData.GetValueStr(strKinoNm, 0, 0, "nen"),"試作Ｎｏ_年");
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, 0, 0, "nen"),"試作Ｎｏ_年", 2);

					// 試作Ｎｏ-追番の必須入力チェック（スーパークラスの必須チェック）を行う。
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "no_oi"),"試作Ｎｏ_追番");
					// 試作Ｎｏ-追番の入力チェック（スーパークラスのチェック）を行う。
					super.numberCheck(checkData.GetValueStr(strKinoNm, 0, 0, "no_oi"),"試作Ｎｏ_追番");
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, 0, 0, "no_oi"),"試作Ｎｏ_追番", 3);

					// 枝番号の必須入力チェック（スーパークラスの必須チェック）を行う。
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, 0, 0, "no_eda"),"試作Ｎｏ_枝番");
					// 枝番号の入力チェック（スーパークラスのチェック）を行う。
					super.numberCheck(checkData.GetValueStr(strKinoNm, 0, 0, "no_eda"),"試作Ｎｏ_枝番");
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, 0, 0, "no_eda"),"試作Ｎｏ_枝番", 3);

					// 試作SEQの必須入力チェック（スーパークラスの必須チェック）を行う。
					super.hissuCodeCheck(checkData.GetValueStr(strKinoNm, 0, 0, "seq_shisaku"),"サンプルＮｏ");
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}
// 20160607  KPX@1502111_No.5 ADD end

}
