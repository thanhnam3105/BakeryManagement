package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

public class RGEN2150_inputcheck extends InputCheck {

	/**
	 * 【QP@00342】コンストラクタ
	 *  : 原価試算データ一覧画面 検索ボタン押下時インプットチェック用コンストラクタ
	 */
	public RGEN2150_inputcheck() {
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

			//FGEN2150のインプットチェックを行う。
			shisakuListSearchCheck(checkData);
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 原価試算データ一覧検索条件インプットチェック
	 *  : FGEN2150のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakuListSearchCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//試作Noの取得
			String strShisakuNo = checkData.GetValueStr("FGEN2150", 0, 0, "no_shisaku");
			
		    //試作Noが入力されている場合
			if (strShisakuNo != null && !strShisakuNo.equals("")) {
				
				String[] strShisaku = strShisakuNo.split("-",-1);
				
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
				//社員CDと年と追番の場合
				if (strShisaku.length == 4){
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
					//枝番が３桁以上の場合はエラー
					if ((strShisaku[3].toString().length() > 3)) {
						// 入力桁数不正をスローする。
						em.ThrowException(ExceptionKind.一般Exception,"E000212","試作CD-枝番","3","");
					}
				}
				//5つ以上の要素がある場合はエラー
				if (strShisaku.length >= 5){
					//検索条件不正をスロー
					em.ThrowException(ExceptionKind.一般Exception,"E000010","試作No","","");
				}
			}

			//試作名の入力桁数チェックを行う
			super.sizeCheckLen(checkData.GetValueStr("FGEN2150", 0, 0, "nm_shisaku"), "試作名", 100);
			
			//試算期日Fromの日付チェックを行う
			String from = toString(checkData.GetValueStr("FGEN2150", 0, 0, "hi_kizitu_from"));
			if(from.equals("")){
				
			}
			else{
				super.dateCheck(from, "試算期日（開始日）");
			}
			
			//試算期日Toの日付チェックを行う
			String to = toString(checkData.GetValueStr("FGEN2150", 0, 0, "hi_kizitu_to"));
			if(to.equals("")){
				
			}
			else{
				super.dateCheck(to, "試算期日（終了日）");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
