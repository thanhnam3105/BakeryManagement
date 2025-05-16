package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 原価試算　更新ｲﾝﾌﾟｯﾄﾁｪｯｸ :
 * 		原価試算、更新情報のｲﾝﾌﾟｯﾄﾁｪｯｸ
 *
 * @author Nishigawa
 * @since 2009/10/28
 */
public class RGEN0041_inputcheck extends InputCheck {

	//【QP@00342】
	String kenkyu = "";
	String seikan = "";
	String gentyo = "";
	String kojo = "";
	String eigyo = "";
	String setting = "";
	String ragio_kesu_kg = "";

	/**
	 * コンストラクタ : 原価試算、資材情報表示ﾍｯﾀﾞｰｲﾝﾌﾟｯﾄﾁｪｯｸコンストラクタ
	 */
	public RGEN0041_inputcheck() {
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

			//【QP@00342】リクエストデータから部署フラグ取得
			kenkyu = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "busho_kenkyu"));
			seikan = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "busho_seikan"));
			gentyo = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "busho_gentyo"));
			kojo = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "busho_kojo"));
			eigyo = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "busho_eigyo"));

			//更新方法取得
			setting = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "setting"));

			//計算方法選択
			ragio_kesu_kg = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "ragio_kesu_kg"));

			//FGEN0040のインプットチェックを行う。
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
	 * 登録項目チェック
	 *  : FGEN0040のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//テーブル：[kihon]のインプットチェック
			this.kihonInsertValueCheck(checkData);

			//テーブル：[genryo]のインプットチェック
			this.genryoInsertValueCheck(checkData);

			//テーブル：[keisan]のインプットチェック
			this.keisanInsertValueCheck(checkData);

			//テーブル：[shizai]のインプットチェック
			this.shizaiInsertValueCheck(checkData);

			// ADD 2013/7/2 shima【QP@30151】No.37 start
			//テーブル：[kihonsub]のインプットチェック
			this.kihonsubInsertValueCheck(checkData);
			// ADD 2013/7/2 shima【QP@30151】No.37 end

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
	 * テーブル：[kihon]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kihonInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "kihon";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {



					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   担当会社
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					//必須チェック
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kaisya"),"製造会社");


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   担当工場
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					//必須チェック
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kojyo"),"製造工場");


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   入り数
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "irisu") ).equals("") ){

						//数値チェック
						super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "irisu"), "", ","), "入り数");

						//桁数チェック（60桁以内）
						super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "irisu"), "", ","),"入り数",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   荷姿
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "nisugata") ).equals("") ){

						//桁数チェック（26桁以内）
						// 【QP@10713】20111111 HISAHORI MOD START
						//super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nisugata"),"荷姿",26);
						super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nisugata"),"荷姿",26,13);
						// 【QP@10713】20111111 HISAHORI MOD END
					}

					// DEL 2013/7/2 shima【QP@30151】No.37 start
/*
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   希望原価
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka") ).equals("") ){

						super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka"), "", ","), "希望原価");

						//桁数チェック（60桁以内）
						super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka"), "", ","),"希望原価",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   原価単位
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					//必須チェック（原価希望が入力されている場合のみ）
					if( toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka") ).equals( "" ) ){

						//何もしない

					}
					else{

						//必須チェック
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka_cd_tani"),"希望原価単位");

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   売価希望
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika") ).equals("") ){

						//数値チェック
						// 【QP@10713】20111110 TT H.SHIMA 文言変更
						//super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","), "希望売価");
						super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","), "希望特約");

						//桁数チェック（60桁以内）
						// 【QP@10713】20111110 TT H.SHIMA 文言変更
						//super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","),"希望売価",60);
						super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","),"希望特約",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   想定物量
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "butu_sotei") ).equals("") ){

						//桁数チェック（60桁以内）
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "butu_sotei"),"想定物量",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   販売時期
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "ziki_hanbai") ).equals("") ){

						//桁数チェック（60桁以内）
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "ziki_hanbai"),"販売時期",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   計画売上
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_uriage") ).equals("") ){

						//桁数チェック（60桁以内）
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_uriage"),"計画売上",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   計画利益
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_rieki") ).equals("") ){

						//桁数チェック（60桁以内）
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_rieki"),"計画利益",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   販売後売上
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_uriage") ).equals("") ){

						//桁数チェック（60桁以内）
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_uriage"),"販売後売上",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   販売後利益
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_rieki") ).equals("") ){

						//桁数チェック（60桁以内）
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_rieki"),"販売後利益",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   製造ロット
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//【QP@00342】部署が工場の場合は製造ロット必須
					//【QP@10713】生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
					//if(kojo.equals("1") && setting.equals("2")){
					if( ( kojo.equals("1") || seikan.equals("1") ) && setting.equals("2")){
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto"),"製造ロット");
					}

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto") ).equals("") ){

						//桁数チェック（60桁以内）
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto"),"製造ロット",60);

					}
*/
					// DEL 2013/7/2 shima【QP@30151】No.37 end


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   原価試算メモ
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan") ).equals("") ){

//MOD 2013/07/18 ogawa 【QP@30151】No.33 start
//修正前ソース
						//桁数チェック（500桁以内）
//						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan"),"原価試算メモ", 500);
//修正後ソース
						//桁数チェック（2000桁以内）
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan"),"原価試算メモ", 2000);
//MOD 2013/07/18 ogawa 【QP@30151】No.33 end

					}

					//【QP@00342】
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   原価試算メモ（営業連絡用）
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan_eigyo") ).equals("") ){

//MOD 2013/07/18 ogawa 【QP@30151】No.33 start
//修正前ソース
						//桁数チェック（500桁以内）
//						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan_eigyo"),"原価試算メモ（営業連絡用）", 500);
//修正後ソース
						//桁数チェック（2000桁以内）
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan_eigyo"),"原価試算メモ（営業連絡用）", 2000);
//MOD 2013/07/18 ogawa 【QP@30151】No.33 end

					}


			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;

		}
	}

	/**
	 * テーブル：[genryo]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void genryoInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "genryo";
		String strAddMsg = "";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

			    //追加メッセージの編集
				strAddMsg = "【原料情報】 [ 行数：" + checkData.GetValueStr(strKinoNm, strTableNm, i, "no_gyo") + "]";

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   単価
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//【QP@00342】部署が工場、原資材調達部の場合
				//【QP@10713】生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
				//if(kojo.equals("1") || gentyo.equals("1")){
				if(kojo.equals("1") || gentyo.equals("1") || seikan.equals("1")){
					if(setting.equals("2")){
						//必須チェック
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), strAddMsg + "単価");
					}
					else{

					}
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka") ).equals("") ){
					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ","), strAddMsg + "単価");

					//***** MOD【H24年度対応】20120424 hagiwara S **********
					//範囲チェック（0.0 ～999999.99）
					//super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ",") ), 0, 999999.99, strAddMsg + "単価");

					//範囲チェック（0.01 ～999999.99）
					if(Double.parseDouble(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ",")) != 0){
						super.rangeNumCheck(Double.parseDouble(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ",") ), 0.01, 999999.99, strAddMsg + "単価");
					}
					//***** MOD【H24年度対応】20120424 hagiwara E **********
				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   歩留
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//【QP@00342】部署が工場、原資材調達部の場合
				//【QP@10713】生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
				//if(kojo.equals("1") || gentyo.equals("1")){
				if(kojo.equals("1") || gentyo.equals("1") || seikan.equals("1")){
					if(setting.equals("2")){
						//必須チェック
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), strAddMsg + "歩留");
					}
					else{

					}

				}
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari") ).equals("") ){
					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ","), strAddMsg + "歩留");

					//***** MOD【H24年度対応】20120424 hagiwara S **********
					//範囲チェック（0.0 ～999.99）
					//super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ",") ), 0, 999.99, strAddMsg + "歩留");

					//範囲チェック（0.01 ～999.99）
					if(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ",") ) != 0){
						super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ",") ), 0.01, 999.99, strAddMsg + "歩留");
					}
					//***** MOD【H24年度対応】20120424 hagiwara E **********
				}




			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;

		}

	}

	/**
	 * テーブル：[keisan]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void keisanInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "keisan";
		String strAddMsg = "";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				//追加メッセージの編集
				strAddMsg = "【試算項目】 [ 列数：" + ( i + 1 ) + "]";

				//【QP@00342】試算中止
				String fg_chusi = toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "fg_chusi"));

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   試算日付
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//【H24年度対応】No.11 Start

				//【QP@00342】試算中止
//				if(fg_chusi.equals("1")){
//
//				}
//				else{
					//部署が工場の場合
					//【QP@10713】2011/11/02 TT H.SHIMA -MOD	生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
					//if(kojo.equals("1") && setting.equals("2")){
//					if((kojo.equals("1") || seikan.equals("1")) && setting.equals("2")){
//						//必須チェック
//						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "shisan_date"), strAddMsg + "試算日");
//					}
//
//					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "shisan_date") ).equals("") ){
//
//						//日付チェック
//						super.dateCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "shisan_date"), strAddMsg + "試算日");
//
//					}
//				}

				//【H24年度対応】No.11 End

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   有効歩留
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//【QP@00342】部署が工場の場合
				//【QP@10713】2011/11/02 TT H.SHIMA -MOD	生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
				//if(kojo.equals("1")){
				if(kojo.equals("1") || seikan.equals("1")){
					//【QP@00342】試算中止
					if(fg_chusi.equals("1")){

					}
					else{
						if(setting.equals("2")){
							//必須チェック
							super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yuuko_budomari"), strAddMsg + "有効歩留");
						}
						else{

						}
					}
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "yuuko_budomari") ).equals("") ){

					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "yuuko_budomari"), "", ","), strAddMsg + "有効歩留");

					//範囲チェック（0.0 ～999.99）
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "yuuko_budomari"), "", ",") ), 0, 999.99, strAddMsg + "有効歩留");

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   平均充填量
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//【QP@00342】部署が工場の場合
				//【QP@10713】2011/11/02 TT H.SHIMA -MOD	生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
				//if(kojo.equals("1")){
				if(kojo.equals("1") || seikan.equals("1")){
					//【QP@00342】試算中止
					if(fg_chusi.equals("1")){

					}
					else{
						if(setting.equals("2")){
							//必須チェック
							super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinjyutenryo"), strAddMsg + "平均充填量");
						}
						else{

						}
					}
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinjyutenryo") ).equals("") ){

					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinjyutenryo"), "", ","), strAddMsg + "平均充填量");

					//範囲チェック（0.0～999999.999999）
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinjyutenryo"), "", ",") ), 0, 999999.999999, strAddMsg + "平均充填量");

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   固定費/ケース
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//【QP@00342】部署が工場の場合
				//【QP@10713】2011/11/02 TT H.SHIMA -MOD	生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
				//if(kojo.equals("1")){
				if(kojo.equals("1") || seikan.equals("1")){
					//【QP@00342】試算中止
					if(fg_chusi.equals("1")){

					}
					else{
						if(setting.equals("2") && ragio_kesu_kg.equals("1")){
							//必須チェック
							super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_kotehi"), strAddMsg + "固定費/ケース");
						}
						else{

						}
					}
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_kotehi") ).equals("") ){

					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_kotehi"), "", ","), strAddMsg + "固定費/ケース");

					//範囲チェック（0.0 ～999999.99）
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_kotehi"), "", ",") ), 0, 999999.99, strAddMsg + "固定費/ケース");

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   固定費/kg
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//【QP@00342】部署が工場の場合
				//【QP@10713】2011/11/02 TT H.SHIMA -MOD	生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
				//if(kojo.equals("1")){
				if(kojo.equals("1") || seikan.equals("1")){
					//【QP@00342】試算中止
					if(fg_chusi.equals("1")){

					}
					else{
						if(setting.equals("2") && ragio_kesu_kg.equals("2")){
							//必須チェック
							super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_kotehi"), strAddMsg + "固定費/kg");
						}
						else{

						}
					}
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_kotehi") ).equals("") ){

					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_kotehi"), "", ","), strAddMsg + "固定費/kg");

					//範囲チェック（0.0 ～999999.99）
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_kotehi"), "", ",") ), 0, 999999.99, strAddMsg + "固定費/kg");

				}
                // ADD 2013/11/1 QP@30154 okano start
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   利益/ケース
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				// DEL 2013/12/6 QP@30154 okano start
//					if(kojo.equals("1") || seikan.equals("1")){
//						//試算中止
//						if(fg_chusi.equals("1")){
//	
//						}
//						else{
//							if(setting.equals("2") && ragio_kesu_kg.equals("1")){
//								//必須チェック
//								super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_rieki"), strAddMsg + "利益/ケース");
//							}
//							else{
//	
//							}
//						}
//					}
				// DEL 2013/12/6 QP@30154 okano end

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_rieki") ).equals("") ){

					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_rieki"), "", ","), strAddMsg + "利益/ケース");

					//範囲チェック（0.0 ～999999.99）
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_rieki"), "", ",") ), 0, 999999.99, strAddMsg + "利益/ケース");

				}

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   利益/kg
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				// DEL 2013/12/6 QP@30154 okano start
//					if(kojo.equals("1") || seikan.equals("1")){
//						//試算中止
//						if(fg_chusi.equals("1")){
//	
//						}
//						else{
//							if(setting.equals("2") && ragio_kesu_kg.equals("2")){
//								//必須チェック
//								super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_rieki"), strAddMsg + "利益/kg");
//							}
//							else{
//	
//							}
//						}
//					}
				// DEL 2013/12/6 QP@30154 okano end

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_rieki") ).equals("") ){

					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_rieki"), "", ","), strAddMsg + "利益/kg");

					//範囲チェック（0.0 ～999999.99）
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_rieki"), "", ",") ), 0, 999999.99, strAddMsg + "利益/kg");

				}
                // ADD 2013/11/1 QP@30154 okano end

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;

		}

	}

	/**
	 * テーブル：[shizai]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shizaiInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "shizai";
		String strAddMsg = "";

		try {


			//資材数のチェック
			if( checkData.GetRecCnt(strKinoNm, strTableNm) > 15 ){

				//不正をスローする。
		    	em.ThrowException(
		    			ExceptionKind.一般Exception,
		    			"E000403",
		    			"",
		    			"",
		    			"");
			}

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				//追加メッセージの編集
				strAddMsg = "【資材情報】 [ 行数：" + ( i + 1 ) + "]";

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   資材コード
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shizai") ).equals("") ){

					//桁数チェック（13桁以内）
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shizai"), strAddMsg + "資材コード", 13);

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   資材名
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//【QP@00342】部署が工場の場合
				//【QP@10713】生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
				//if(kojo.equals("1") && setting.equals("2")){
				if((kojo.equals("1") || seikan.equals("1")) && setting.equals("2")){
					//必須チェック
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_shizai"), strAddMsg + "資材名");
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_shizai") ).equals("") ){

//2010/02/15　T.T.Isono　UPDATE　START　資材名を36桁以内に変更
					//桁数チェック（60桁以内）
//					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_shizai"), strAddMsg + "資材名", 60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_shizai"), strAddMsg + "資材名", 36);
//2010/02/15　T.T.Isono　UPDATE　END　　資材名を36桁以内に変更

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   資材単価
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//【QP@00342】部署が工場の場合
				//【QP@10713】生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
				//if(kojo.equals("1") && setting.equals("2")){
				if((kojo.equals("1") || seikan.equals("1")) && setting.equals("2")){
					//必須チェック
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), strAddMsg + "資材単価");
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka") ).equals("") ){

					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ","), strAddMsg + "資材単価");

					//範囲チェック（0.0 ～999999.99）
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ",") ), 0, 999999.99, strAddMsg + "資材単価");

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   資材歩留
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//【QP@00342】部署が工場の場合
				//【QP@10713】生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
				//if(kojo.equals("1") && setting.equals("2")){
				if((kojo.equals("1") || seikan.equals("1")) && setting.equals("2")){
					//必須チェック
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), strAddMsg + "資材歩留");
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari") ).equals("") ){

					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ","), strAddMsg + "資材歩留");

					//範囲チェック（0.0 ～999.99）
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ",") ), 0, 999.99, strAddMsg + "資材歩留");

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   使用量/ケース
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//【QP@00342】部署が工場の場合
				//【QP@10713】生産権限でステータスが「確認完了」時には工場権限入力必須項目の処理も行う
				//if(kojo.equals("1") && setting.equals("2")){
				if((kojo.equals("1") || seikan.equals("1")) && setting.equals("2")){
					//必須チェック
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "shiyouryo"), strAddMsg + "使用量/ケース");
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "shiyouryo") ).equals("") ){

					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "shiyouryo"), "", ","), strAddMsg + "使用量/ケース");

					//範囲チェック（0.0～9999.999999）
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "shiyouryo"), "", ",") ), 0, 9999.999999, strAddMsg + "使用量/ケース");

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;

		}

	}

	// ADD 2013/7/2 shima【QP@30151】No.37 start
	/**
	 * テーブル：[kihonsub]のインプットチェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kihonsubInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strKinoNm = "FGEN0040";
		String strTableNm = "kihonsub";
		
		try {
			
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
				//試算中止
				String fg_chusi = toString(checkData.GetValueStr(strKinoNm, "keisan", i, "fg_chusi"));
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   希望原価
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka") ).equals("") ){
				
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka"), "", ","), "希望原価");
				
					//桁数チェック（60桁以内）
					super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka"), "", ","),"希望原価",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   原価単位
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				//試算中止
				if(fg_chusi.equals("1")){

				}
				else{
					//必須チェック（原価希望が入力されている場合のみ）
					if( toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka") ).equals( "" ) ){
					
						//何もしない
					
					}
					else{
					
						//必須チェック
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka_cd_tani"),"希望原価単位");
					
					}
				}
					
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   売価希望
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika") ).equals("") ){
				
					//数値チェック
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","), "希望特約");
				
					//桁数チェック（60桁以内）
					super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","),"希望特約",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   想定物量
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "butu_sotei") ).equals("") ){
				
					//桁数チェック（60桁以内）
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "butu_sotei"),"想定物量",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   販売時期
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "ziki_hanbai") ).equals("") ){
				
					//桁数チェック（60桁以内）
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "ziki_hanbai"),"販売時期",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   計画売上
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_uriage") ).equals("") ){
				
					//桁数チェック（60桁以内）
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_uriage"),"計画売上",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   計画利益
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_rieki") ).equals("") ){
				
					//桁数チェック（60桁以内）
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_rieki"),"計画利益",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   販売後売上
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_uriage") ).equals("") ){
				
					//桁数チェック（60桁以内）
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_uriage"),"販売後売上",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   販売後利益
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_rieki") ).equals("") ){
				
					//桁数チェック（60桁以内）
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_rieki"),"販売後利益",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   製造ロット
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if( ( kojo.equals("1") || seikan.equals("1") ) && setting.equals("2")){
					//試算中止
					if(fg_chusi.equals("1")){

					}
					else{
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto"),"製造ロット");
					}
				}
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto") ).equals("") ){
				
					//桁数チェック（60桁以内）
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto"),"製造ロット",60);
				
				}
					
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
		
		}
	}
	// ADD 2013/7/2 shima【QP@30151】No.37 end
}
