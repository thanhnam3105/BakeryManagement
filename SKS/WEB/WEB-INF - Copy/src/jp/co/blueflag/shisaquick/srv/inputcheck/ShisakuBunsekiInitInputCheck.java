package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 【JW710】 試作表分析データ確認画面リクエスト初期処理 インプットチェック
 *
 */
public class ShisakuBunsekiInitInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 *  : 試作データ画面 初期表示時インプットチェック用コンストラクタ
	 */
	public ShisakuBunsekiInitInputCheck() {
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

			//SA590のインプットチェックを行う。
//			henkouSearchKeyCheck(checkData);

			//SA570のインプットチェックを行う。
			shisakulistSearchKeyCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 分析値変更検索条件値チェック
	 *  : SA590のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void henkouSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			for( int i=0; i<checkData.GetRecCnt("SA590", 0); i++ ){
				//会社コードの必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "cd_kaisha"),"会社コード");
				//部署コードの必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "cd_busho"),"部署コード");
				//原料コードの必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "cd_genryo"),"原料コード");
				//原料名の必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "nm_genryo"),"原料名");
			    //原料名の入力桁数チェック
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr("SA590", 0, i, "nm_genryo"),"原料名",60,30);
				//単価の必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "tanka"),"単価");
				//歩留の必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "budomari"),"歩留");
				//油含有率の必須入力チェック	
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "ritu_abura"),"油含有率");
				//酢酸の必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "ritu_sakusan"),"酢酸");
				//食塩の必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "ritu_shokuen"),"食塩");
				//総酸の必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA590", 0, i, "ritu_sousan"),"総酸");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 分析値変更検索条件値チェック
	 *  : SA570のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakulistSearchKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			for( int i=0; i<checkData.GetRecCnt("SA570", 0); i++ ){
				//会社コードの必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA570", 0, i, "cd_kaisha"),"会社コード");
				//部署コードの必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA570", 0, i, "cd_busho"),"部署コード");
				//原料コードの必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA570", 0, i, "cd_genryo"),"原料コード");
				//選択ページ番号の必須入力チェック
			    super.hissuInputCheck(checkData.GetValueStr("SA570", 0, i, "num_selectRow"),"選択ページ番号");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

}
