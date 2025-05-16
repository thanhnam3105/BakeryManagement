package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 原価試算（営業）データ処理インプットチェック 
 * 
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class RGEN2180_inputcheck extends InputCheck {

	/**
	 * コンストラクタ : 原価試算（営業）データインプットチェックコンストラクタ
	 */
	public RGEN2180_inputcheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理 : 入力チェックの管理を行う。
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
			//　USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);
			
			//　設定ステータス取得
			String st_setting = toString(checkData.GetValueStr("FGEN2160", 0, 0, "st_setting"));

			//　FGEN2160のインプットチェック（共通）を行う。
			FGEN2160Check_kyotu(checkData);
			
			// 試算依頼、確認完了、採用有無確定（採用有り）の場合
			if( st_setting.equals("1") || st_setting.equals("2") || st_setting.equals("3") ){
				
				//ステータス変更時のインプットチェックを行う
				FGEN2160Check_StatusHenkou(checkData);
				
			}
			
			// 採用有無確定（採用有り）の場合
			if( st_setting.equals("3") ){
				
				//採用ｻﾝﾌﾟﾙNo取得
				String no_saiyou = toString(checkData.GetValueStr("FGEN2160", 0, 0, "no_saiyou"));
				super.hissuInputCheck(no_saiyou,"採用ｻﾝﾌﾟﾙNo");
				
			}
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 原価試算（営業）データ　共通インプットチェック
	 *  : FGEN2160のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void FGEN2160Check_kyotu(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//リクエストから枝番取得
			String eda = toString(checkData.GetValueStr("FGEN2160", 0, 0, "no_eda"));
			
			//試算期日
			String dt_kizitu = toString(checkData.GetValueStr("FGEN2160", 0, 0, "dt_kizitu"));
			if(dt_kizitu.equals("")){
				
			}
			else{
				//試算期日が日付で入力されているか
				super.dateCheck(dt_kizitu, "試算期日");
			}
			
			
			//容量
			String yoryo = toString(checkData.GetValueStr("FGEN2160", 0, 0, "yoryo"), "", ",");
			//元版の場合
			if(eda.equals("000")){
				
			}
			//枝版の場合
			else{
				if(yoryo.equals("")){
					
				}
				else{
					//容量が数値で入力されているか（枝版の場合のみ）
					super.numberCheck(yoryo, "容量（数値入力）");
					
					//容量が60桁以内で入力されているか（枝版の場合のみ）
					super.sizeCheckLen(yoryo,"容量（数値入力）",60);
				}
			}
			
			//入り数
			String su_iri = toString(checkData.GetValueStr("FGEN2160", 0, 0, "su_iri"), "", ",");
			if(su_iri.equals("")){
				
			}
			else{
				//入り数が数値で入力されているか
				super.numberCheck(su_iri, "入り数（数値入力）");
				
				//入り数が60桁以内で入力されているか
				super.sizeCheckLen(su_iri,"入り数（数値入力）",60);
			}
			
			//荷姿
			String nisugata = toString(checkData.GetValueStr("FGEN2160", 0, 0, "nisugata"));
			if(nisugata.equals("")){
				
			}
			else{
				//荷姿が26桁以内で入力されているか
				// 【QP@10713】201111114 HISAHORI MOD START 
				//super.sizeCheckLen(nisugata,"荷姿",26);
				super.sizeHalfFullLengthCheck(nisugata,"荷姿",26,13);
			}
			
			// MOD 2013/7/2 shima【QP@30151】No.37 start
			//サンプル毎の基本情報
			for ( int i=0; i<checkData.GetRecCnt("FGEN2160", "kihonsub"); i++ ) {
			
				//希望原価
//				String genka = toString(checkData.GetValueStr("FGEN2160", 0, 0, "genka"), "", ",");
				String genka = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "genka"), "", ",");
				if(genka.equals("")){
					
				}
				else{
					//希望原価が数値で入力されているか
					super.numberCheck(genka, "希望原価（数値入力）");	
					
					//希望原価が60桁以内で入力されているか
					super.sizeCheckLen(genka,"希望原価（数値入力）",60);
				}
				
				//希望売価
//				String baika = toString(checkData.GetValueStr("FGEN2160", 0, 0, "baika"), "", ",");
				String baika = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "baika"), "", ",");
				if(baika.equals("")){
					
				}
				else{
					//希望売価が数値で入力されているか
					//【QP@10713】20111110 TT H.SHIMA 文言変更
					//super.numberCheck(baika, "希望売価（数値入力）");	
					super.numberCheck(baika, "希望特約（数値入力）");
					
					//希望売価が60桁以内で入力されているか
					//【QP@10713】20111110 TT H.SHIMA 文言変更
					//super.sizeCheckLen(baika,"希望売価（数値入力）",60);	
					super.sizeCheckLen(baika,"希望特約（数値入力）",60);
				}
				
				//想定物量
//				String sotei_buturyo = toString(checkData.GetValueStr("FGEN2160", 0, 0, "sotei_buturyo"));
				// MOD 2013/12/4 okano【QP@30154】start
//					String sotei_buturyo = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo"), "", ",");
				String sotei_buturyo = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo"));
				// MOD 2013/12/4 okano【QP@30154】end
				if(sotei_buturyo.equals("")){
					
				}
				else{
					//想定物量が60桁以内で入力されているか
					// MOD 2013/9/6 okano【QP@30151】No.30 start
//						super.sizeCheckLen(sotei_buturyo,"想定物量",60);
					super.sizeCheckLen(sotei_buturyo,"想定物量（備考）",60);
					// MOD 2013/9/6 okano【QP@30151】No.30 end
				}
				
				// ADD 2013/9/6 okano【QP@30151】No.30 start
				String sotei_buturyo_s = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo_s"), "", ",");
				if(sotei_buturyo_s.equals("")){
					
				}
				else{
					//想定物量が数値で入力されているか
					super.numberCheck(sotei_buturyo_s, "想定物量（数値）");
					//想定物量が9桁以内で入力されているか
					super.sizeCheckLen(sotei_buturyo_s,"想定物量（数値）",9);
				}
				// ADD 2013/9/6 okano【QP@30151】No.30 end
				
				// ADD 2013/7/19 shima【QP@30151】No.37 start
				String dt_hatubai = toString(checkData.GetValueStr("FGEN2160", "kihonsub", 0, "dt_hatubai"));
				if(dt_hatubai.equals("")){
					
				}else{
					//初回納品時期が60桁以内で入力されているか
					super.sizeCheckLen(dt_hatubai,"初回納品時期",60);
				// ADD 2013/7/19 shima【QP@30151】No.37 end
				//発売時期が「YYYY年MM月」、「YYYY年M月」形式で入力されているか
	//			String dt_hatubai = toString(checkData.GetValueStr("FGEN2160", 0, 0, "dt_hatubai"));
	//			if(dt_hatubai.equals("")){
	//				
	//			}else{
	//				try{
	//					//年月取得
	//					String nen = dt_hatubai.split("年")[0].toString();
	//					String tuki = dt_hatubai.split("年")[1].toString();
	//					int chk_nen = Integer.parseInt(nen);
	//					int chk_tuki = Integer.parseInt(tuki.split("月")[0].toString());
	//					
	//					//年チェック
	//					if(chk_nen >= 2000 && chk_nen <= 9999){
	//						
	//					}
	//					else{
	//						// 必須入力不正をスローする。
	//						em.ThrowException(ExceptionKind.一般Exception, "E000315", "3", "", "");
	//					}
	//					//月チェック
	//					if(chk_tuki >= 1 && chk_tuki <=12){
	//						
	//					}
	//					else{
	//						// 必須入力不正をスローする。
	//						em.ThrowException(ExceptionKind.一般Exception, "E000315", "3", "", "");
	//					}
	//					
	//				}
	//				catch(Exception e){
	//					// 必須入力不正をスローする。
	//					em.ThrowException(ExceptionKind.一般Exception, "E000315", "3", "", "");
	//				}
				}
				
				
				
				//計画売上
//				String keikakuuriage = toString(checkData.GetValueStr("FGEN2160", 0, 0, "keikakuuriage"));
				String keikakuuriage = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "keikakuuriage"), "", ",");
				if(keikakuuriage.equals("")){
					
				}
				else{
					//計画売上が60桁以内で入力されているか
					super.sizeCheckLen(keikakuuriage,"計画売上",60);
				}
				
				//計画利益
//				String keikakurieki = toString(checkData.GetValueStr("FGEN2160", 0, 0, "keikakurieki"));
				String keikakurieki = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "keikakurieki"), "", ",");
				if(keikakurieki.equals("")){
					
				}
				else{
					//計画利益が60桁以内で入力されているか
					super.sizeCheckLen(keikakurieki,"計画利益",60);
				}
				
				//販売後売上が60桁以内で入力されているか
//				String kuhaku_1 = toString(checkData.GetValueStr("FGEN2160", 0, 0, "kuhaku_1"));
				String kuhaku_1 = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "kuhaku_1"), "", ",");
				if(kuhaku_1.equals("")){
					
				}
				else{
					super.sizeCheckLen(kuhaku_1,"空白欄1",60);
				}
				
				//販売後利益が60桁以内で入力されているか
//				String kuhaku_2 = toString(checkData.GetValueStr("FGEN2160", 0, 0, "kuhaku_2"));
				String kuhaku_2 = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "kuhaku_2"), "", ",");
				if(kuhaku_2.equals("")){
					
				}
				else{
					super.sizeCheckLen(kuhaku_2,"空白欄2",60);
				}
				
				//販売期間（数値）
//				String hanabai_s = toString(checkData.GetValueStr("FGEN2160", 0, 0, "hanabai_s"), "", ",");
				String hanabai_s = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "hanabai_s"), "", ",");
				if(hanabai_s.equals("")){
					
				}
				else{
					//希望売価が数値で入力されているか
					super.numberCheck(hanabai_s, "販売期間（数値）");
					
					//希望売価が9桁以内で入力されているか
					super.sizeCheckLen(hanabai_s,"販売期間（数値）",9);
				}
			}
			// MOD 2013/7/2 shima【QP@30151】No.37 end
			
			//原価試算メモ（営業連絡用）が500桁以内で入力されているか
			String memo_eigyo = toString(checkData.GetValueStr("FGEN2160", 0, 0, "memo_eigyo"));
			if(memo_eigyo.equals("")){
				
			}
			else{
//MOD 2013/07/18 ogawa 【QP@30151】No.33 start
//修正前ソース
//				super.sizeCheckLen(memo_eigyo,"原価試算メモ（営業連絡用）",500);
//修正後ソース
				super.sizeCheckLen(memo_eigyo,"原価試算メモ（営業連絡用）",2000);
//MOD 2013/07/18 ogawa 【QP@30151】No.33 end
			}
			

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	
	/**
	 * 原価試算（営業）データ　ステータス変更時インプットチェック
	 *  : FGEN2160のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void FGEN2160Check_StatusHenkou(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//試算期日
			String dt_kizitu = toString(checkData.GetValueStr("FGEN2160", 0, 0, "dt_kizitu"));
			super.hissuInputCheck(dt_kizitu,"試算期日");
			
			//容量
			String yoryo = toString(checkData.GetValueStr("FGEN2160", 0, 0, "yoryo"), "", ",");
			super.hissuInputCheck(yoryo,"容量");
			
			//入り数
			String su_iri = toString(checkData.GetValueStr("FGEN2160", 0, 0, "su_iri"), "", ",");
			super.hissuInputCheck(su_iri,"入り数");
			
			//荷姿
			String nisugata = toString(checkData.GetValueStr("FGEN2160", 0, 0, "nisugata"));
			super.hissuInputCheck(nisugata,"荷姿");
			
			// MOD 2013/7/2 shima【QP@30151】No.37 start
			for ( int i=0; i<checkData.GetRecCnt("FGEN2160", "kihonsub"); i++ ) {
				
				//試算中止
				String fg_chusi = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "chusi"), "", ",");
				
				//試算中止
				if( fg_chusi.equals("1")){
					//必須チェックを行わない
					
				}else{
					//希望原価
					String genka = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "genka"), "", ",");
					super.hissuInputCheck(genka,"希望原価");
					
					//希望原価（単位）
					String genka_tani = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "genka_tani"), "", ",");
					super.hissuInputCheck(genka_tani,"希望原価（単位）");
					
					//希望売価
					String baika = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "baika"), "", ",");
					//super.hissuInputCheck(baika,"希望売価");	
					//【QP@10713】20111110 TT H.SHIMA 文言変更
					super.hissuInputCheck(baika,"希望特約");
					
					//想定物量
					// MOD 2013/9/6 okano【QP@30151】No.30 start
//						String sotei_buturyo = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo"));
//						super.hissuInputCheck(sotei_buturyo,"想定物量");
					String sotei_buturyo_s = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo_s"));
					super.hissuInputCheck(sotei_buturyo_s,"想定物量（数値）");
					
					String sotei_buturyo_u = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo_u"));
					super.hissuInputCheck(sotei_buturyo_u,"想定物量（単位）");
					
					String sotei_buturyo_k = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo_k"));
					super.hissuInputCheck(sotei_buturyo_k,"想定物量（期間）");
					// MOD 2013/9/6 okano【QP@30151】No.30 end
					
					//発売時期
					String dt_hatubai = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "dt_hatubai"));
					super.hissuInputCheck(dt_hatubai,"発売時期");
					
					//販売期間（通年orスポット）
					String hanbai_t = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "hanbai_t"));
					super.hissuInputCheck(hanbai_t,"販売期間（通年 or スポット）");
					
					//販売期間（ヶ月）
					if( hanbai_t.equals("2") ){
						String hanabai_s = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "hanabai_s"));
						super.hissuInputCheck(hanabai_s,"販売期間（数値）");
						
						String hanabai_k = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "hanabai_k"));
						super.hissuInputCheck(hanabai_k,"販売期間（ヶ月 or 日間）");
					}
					
				}
			
			}
			// MOD 2013/7/2 shima【QP@30151】No.37 end
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
