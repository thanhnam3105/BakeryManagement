package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

public class ShisakuSrvSearchInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 *  : 試作データ一覧画面 検索ボタン押下時インプットチェック用コンストラクタ
	 */
	public ShisakuSrvSearchInputCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理
	 *  : 各データチェック処理を管理する。
	 * @param requestData : リクエストデータ
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

			//SA200のインプットチェックを行う。
			shisakuListSearchCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 試作データ一覧検索条件インプットチェック
	 *  : SA200のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakuListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//試作Noの取得
			String strShisakuNo = checkData.GetValueStr("SA200", 0, 0, "no_shisaku");
			
		    //試作Noが入力されている場合
			if (strShisakuNo != null && !strShisakuNo.equals("")) {
				
				String[] strShisaku = strShisakuNo.split("-",-1);
				
				//2009/08/04 DEL 課題№295の対応
//				//試作Noフォーマットチェックを行う。
//				arrayCheck(strShisakuNo, 3, "試作No");

				//2009/08/04 UPD START 課題№295の対応
//				if (!(strShisaku[0].toString().length() <= 10 && strShisaku[0].toString().length() >= 1 && 
//						strShisaku[1].toString().length() <= 2 && strShisaku[1].toString().length() >= 1 && 
//						strShisaku[2].toString().length() <= 3 && strShisaku[2].toString().length() >= 1)) {
//					//エラーをスローする。
//					em.ThrowException(ExceptionKind.一般Exception, "E000206", "試作No", "", "");
//				}
				//社員CDのみの場合
				if (strShisaku.length == 1){
					//社員ＣＤが10桁以上の場合はエラー
					if ((strShisaku[0].toString().length() > 10)) {
						// 入力桁数不正をスローする。
						em.ThrowException(ExceptionKind.一般Exception,"E000212","試作CD-社員CD","10","");
					}
				}
				//社員CDと年のみの場合
				if (strShisaku.length == 2){
					//社員ＣＤが10桁以上の場合はエラー
					if ((strShisaku[0].toString().length() > 10)) {
						// 入力桁数不正をスローする。
						em.ThrowException(ExceptionKind.一般Exception,"E000212","試作CD-社員CD","10","");
					}
					//年が2桁以上の場合はエラー
					if ((strShisaku[1].toString().length() > 2)) {
						// 入力桁数不正をスローする。
						em.ThrowException(ExceptionKind.一般Exception,"E000212","試作CD-年","2","");
					}
				}
				//社員CDと年と追番の場合
				if (strShisaku.length == 3){
					//社員ＣＤが10桁以上の場合はエラー
					if ((strShisaku[0].toString().length() > 10)) {
						// 入力桁数不正をスローする。
						em.ThrowException(ExceptionKind.一般Exception,"E000212","試作CD-社員CD","10","");
					}
					//年が２桁以上の場合はエラー
					if ((strShisaku[1].toString().length() > 2)) {
						// 入力桁数不正をスローする。
						em.ThrowException(ExceptionKind.一般Exception,"E000212","試作CD-年","2","");
					}
					//追番が３桁以上の場合はエラー
					if ((strShisaku[2].toString().length() > 3)) {
						// 入力桁数不正をスローする。
						em.ThrowException(ExceptionKind.一般Exception,"E000212","試作CD-追番","3","");
					}
				}
				//4つ以上の要素がある場合はエラー
				if (strShisaku.length >= 4){
					//検索条件不正をスロー
					em.ThrowException(ExceptionKind.一般Exception,"E000010","試作No","","");
				}
				//2009/08/04 UPD END
			}
			
			//製法Noも検索対象とする場合
			if (checkData.GetValueStr("SA200", 0, 0, "kbn_joken1").equals("1")) {
				//製法Noの取得
				String strSeihoNo = checkData.GetValueStr("SA200", 0, 0, "no_seiho");
				
			    //製法Noが入力されている場合
				if (strSeihoNo != null && !strSeihoNo.equals("")) {
					
					String[] strSeiho = strSeihoNo.split("-",-1);
	
					//製法Noフォーマットチェックを行う。
//					arrayCheck(strSeihoNo, 4, "製法No");
				    
//					if (!(strSeiho[0].toString().length() == 4 && 
//							strSeiho[1].toString().length() == 3 && 
//							strSeiho[2].toString().length() == 2 && 
//							strSeiho[3].toString().length() == 4)) {
//						//エラーをスローする。
//						em.ThrowException(ExceptionKind.一般Exception, "E000206", "製法No", "", "");
//					}
					//会社コードのみの場合
					if (strSeiho.length == 1){
						//会社コードが4桁以上の
						if ((strSeiho[0].toString().length() > 4)) {
							// 入力桁数不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception,"E000212","製法No-会社CD","4","");
						}
					}
					//会社コードと種別の場合
					if (strSeiho.length == 2){
						//会社コードが4桁以上の場合
						if ((strSeiho[0].toString().length() > 4)) {
							// 入力桁数不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception,"E000212","製法No-会社CD","4","");
						}
						//種別が3桁以上の場合
						if ((strSeiho[1].toString().length() > 3)) {
							// 入力桁数不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception,"E000212","製法No-種別","3","");
						}
					}
					//会社コードと種別と年の場合
					if (strSeiho.length == 3){
						//会社コードが4桁以上の場合
						if ((strSeiho[0].toString().length() > 4)) {
							// 入力桁数不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception,"E000212","製法No-会社CD","4","");
						}
						//種別が3桁以上の場合
						if ((strSeiho[1].toString().length() > 3)) {
							// 入力桁数不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception,"E000212","製法No-種別","3","");
						}
						//年が2桁以上の場合
						if ((strSeiho[2].toString().length() > 2)) {
							// 入力桁数不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception,"E000212","製法No-年","2","");
						}
					}
					//会社コードと種別と年と追番の場合
					if (strSeiho.length == 4){
						//会社コードが4桁以上の場合
						if ((strSeiho[0].toString().length() > 4)) {
							// 入力桁数不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception,"E000212","製法No-会社CD","4","");
						}
						//種別が3桁以上の場合
						if ((strSeiho[1].toString().length() > 3)) {
							// 入力桁数不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception,"E000212","製法No-種別","3","");
						}
						//年が2桁以上の場合
						if ((strSeiho[2].toString().length() > 2)) {
							// 入力桁数不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception,"E000212","製法No-年","2","");
						}
						//追番が4桁以上の場合
						if ((strSeiho[3].toString().length() > 4)) {
							// 入力桁数不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception,"E000212","製法No-追番","4","");
						}
					}
					//5つ以上の要素がある場合はエラー
					if (strSeiho.length >= 5){
						//検索条件不正をスロー
						em.ThrowException(ExceptionKind.一般Exception,"E000010","製法No","","");
					}
				}
			}

			//試作名の入力桁数チェックを行う
			super.sizeCheckLen(checkData.GetValueStr("SA200", 0, 0, "nm_shisaku"), "試作名", 100);
			//製品の用途の入力桁数チェックを行う
			super.sizeCheckLen(checkData.GetValueStr("SA200", 0, 0, "cd_youto"), "製品の用途", 60);
			//特徴原料の入力桁数チェックを行う
			super.sizeCheckLen(checkData.GetValueStr("SA200", 0, 0, "cd_genryo"), "特徴原料", 60);
			//キーワードの入力桁数チェックを行う
			super.sizeCheckLen(checkData.GetValueStr("SA200", 0, 0, "keyword"), "キーワード", checkData.GetValueStr("SA200", 0, 0, "keyword").length());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
