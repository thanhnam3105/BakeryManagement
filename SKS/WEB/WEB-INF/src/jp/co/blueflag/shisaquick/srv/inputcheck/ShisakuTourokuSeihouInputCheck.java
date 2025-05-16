package jp.co.blueflag.shisaquick.srv.inputcheck;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * 【JW050】 試作データ画面リクエスト登録（製法コピー） インプットチェック
 *
 */
public class ShisakuTourokuSeihouInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 *  : 試作データ画面 初期表示時インプットチェック用コンストラクタ
	 */
	public ShisakuTourokuSeihouInputCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理
	 *  : 各データチェック処理を管理する。
	 * @param checkData : リクエストデータ
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
			//USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

			//SA500のインプットチェックを行う。
			SeihoNoInsertValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 製法№登録項目チェック
	 *  : SA500のインプットチェックを行う。
	 * @param checkData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void SeihoNoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//① 試作CD－社員CDの必須入力チェック（スーパークラスの必須チェック）を行う。
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "cd_shain"),"試作コード（社員コード）");
		    //② 試作CD－年の必須入力チェック（スーパークラスの必須チェック）を行う。
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "nen"),"試作コード（年）");
		    //③ 試作CD－追番の必須入力チェック（スーパークラスの必須チェック）を行う。
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "no_oi"),"試作コード（追番）");
		    //④ 試作SEQの必須入力チェック（スーパークラスの必須チェック）を行う。
		    super.hissuCodeCheck(checkData.GetValueStr("SA500", 0, 0, "seq_shisaku"),"試作列");
		    //⑤ 種別番号の必須入力チェック（スーパークラスの必須チェック）を行う。
		    super.hissuInputCheck(checkData.GetValueStr("SA500", 0, 0, "no_shubetu"),"種別番号");
			super.sizeCheckLen(checkData.GetValueStr("SA500", 0, 0, "no_shubetu"),"種別番号",2);
		    //⑥ 配合データの入力チェック
		    haigoInsertValueCheck(checkData);
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	
	/**
	 * 配合テーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void haigoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA500";
			String strTableNm = "tr_haigo";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				String strAddMsg = "";

				///
				/// 必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");					//試作コード-年
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");				//試作コード-追番

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kotei"),"試作データ画面 配合表 工程CD");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_kotei"),"試作データ画面 配合表 工程SEQ");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_genryo"),"試作データ画面 配合表 原料コード");
			    
				//  [試作表①]追加メッセージの編集
				strAddMsg = "試作データ画面 配合表 [sort_kotei=" + checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_kotei") + "] :";

//2009/08/03 DEL 課題№285の対応
//				///
//				/// 新規原料チェック
//				///
//				super.checkExistString(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_genryo"),"N","試作データ画面 試作表① 原料コード");
			
				
				///
				/// 入力桁数チェック
				///
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_kotei"),strAddMsg+"工程名",60);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_kotei"),strAddMsg+"工程名",60,30);
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"原料名称",60);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"原料名称",60,30);

				///
				/// 数値項目については、数字範囲チェック
				///
			    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
			    if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.rangeNumCheck(
							new BigDecimal(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka")))
							, new BigDecimal("0")
							, new BigDecimal("99999999.99")
							, strAddMsg+"単価");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),0,999.99,strAddMsg+"歩留");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),0,999.99,strAddMsg+"油含有率");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),0,999.99,strAddMsg+"酢酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),0,999.99,strAddMsg+"食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),0,999.99,strAddMsg+"総酸");
				}

				///
				/// 小数点以下が入力される項目については、小数桁数チェック
				///
			    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"),2,strAddMsg+"単価");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),2,strAddMsg+"歩留");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),2,strAddMsg+"油含有率");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),2,strAddMsg+"酢酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),2,strAddMsg+"食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),2,strAddMsg+"総酸");
				}
				
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
