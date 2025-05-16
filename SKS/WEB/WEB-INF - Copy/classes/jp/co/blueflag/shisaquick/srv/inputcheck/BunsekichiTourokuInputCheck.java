package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

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
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"栄養計算食品番号1",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"栄養計算食品番号2",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"栄養計算食品番号3",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"栄養計算食品番号4",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"栄養計算食品番号5",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"割合1",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"割合2",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"割合3",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"割合4",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"割合5",10);

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
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo1"),"栄養計算食品番号1",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo2"),"栄養計算食品番号2",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo3"),"栄養計算食品番号3",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo4"),"栄養計算食品番号4",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"no_eiyo5"),"栄養計算食品番号5",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai1"),"割合1",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai2"),"割合2",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai3"),"割合3",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai4"),"割合4",10);
			super.sizeCheckLen(checkData.GetValueStr(strKinoNm,0,0,"wariai5"),"割合5",10);

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
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
