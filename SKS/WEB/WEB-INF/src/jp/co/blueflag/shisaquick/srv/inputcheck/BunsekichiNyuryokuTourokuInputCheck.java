package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 分析値入力更新処理インプットチェック : 試作データ画面の試作表出力時に各項目の入力値チェックを行う。
 *
 * @author itou
 * @since 2009/04/06
 */
public class BunsekichiNyuryokuTourokuInputCheck extends InputCheck {

	/**
	 * コンストラクタ : 分析値入力更新処理インプットチェックコンストラクタ
	 */
	public BunsekichiNyuryokuTourokuInputCheck() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}

	/**
	 * インプットチェック管理 : 入力チェックの管理を行う。
	 *
	 * @param requestData
	 *            : リクエストデータ
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
			// USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

			// 機能ID：SA380のインプットチェック（更新内容チェック）を行う。
			kanriValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 更新内容チェック : 登録、更新内容の入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kanriValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			if (checkData.GetValueStr("SA380", 0, 0, "kbn_shori").equals("0")) {
				// 処理区分＝登録の場合、登録値チェックを行う。
				insertValueCheck(checkData);
			} else {
				// 処理区分＝更新の場合、更新値チェックを行う。
				updateValueCheck(checkData);
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 登録値チェック : 登録内容の入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// 会社コードの必須入力チェック（スーパークラスの必須チェック）を行う。
			// ※引数1：会社コード 				メッセージパラメータ："会社"　メッセージコード：E000207
			super.hissuCodeCheck(checkData.GetValueStr("SA380", 0, 0, "cd_kaisha"), "会社");

			// 登録、更新共通部分入力チェック。
			// （酢酸,食塩,総酸,油含有率,表示案,添加物,栄養計算食品番号1～5,割合1～5およびメモの入力チェック。）
			comInsertUpdateValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 更新値チェック : 更新内容の入力値チェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void updateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			// 登録、更新共通部分入力チェック。
			// （酢酸,食塩,総酸,油含有率,表示案,添加物,栄養計算食品番号1～5,割合1～5およびメモの入力チェック。）
			comInsertUpdateValueCheck(checkData);

//			// 廃止がチェックされている場合（0:使用可能、1:廃止）、確定コードの必須入力チェック（スーパークラスの必須チェック）を行う。
//			// ※引数1：確定コード メッセージパラメータ："確定コード"　メッセージコード：E000200
//			if (checkData.GetValueStr("SA400", 0, 0, "kbn_haishi").equals("1")) {
//				super.hissuInputCheck(checkData.GetValueStr("SA380", 0, 0, "cd_kakutei"), "確定コード");
//			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * 登録値更新値共通部分チェック :
	 * 酢酸,食塩,総酸,油含有率,表示案,添加物,栄養計算食品番号1～5,割合1～5およびメモの入力チェックは登録、更新共通。
	 * @param checkData : リクエストデータ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void comInsertUpdateValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// 原料名の必須入力チェック（スーパークラスの必須チェック）を行う。
		// ※引数1：原料名 		メッセージパラメータ："原料名"　メッセージコード：E000200
		super.hissuInputCheck(checkData.GetValueStr("SA380", 0, 0, "nm_genryo"), "原料名");
		// 原料名の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
		// ※引数1：原料名、引数3：60、引数4：30 		メッセージパラメータ："原料名"　メッセージコード：E000201　引数3：60　引数4：30
		super.sizeHalfFullLengthCheck(checkData.GetValueStr("SA380", 0, 0, "nm_genryo"), "原料名", 60, 30);
		String strRituSakusan = checkData.GetValueStr("SA380", 0, 0, "ritu_sakusan");
		if (!(strRituSakusan.equals("") || strRituSakusan == null)) {
			// 酢酸の数字範囲チェック（スーパークラスの数字範囲チェック）を行う。
			// ※引数1：酢酸、引数2：0、引数3：999.99 	 メッセージパラメータ："酢酸（%）"　メッセージコード：E000203　引数4：0、引数5：999.99
			super.rangeNumCheck(Double.parseDouble(strRituSakusan), 0, 999.99, "酢酸（%）");
			// 酢酸の小数桁数チェック（スーパークラスの小数桁数チェック）を行う。
			// ※引数1：酢酸、引数2：2	 メッセージパラメータ："酢酸（%）"　メッセージコード：E000204　引数4：3、引数5：2
			super.shousuRangeCheck(Double.parseDouble(strRituSakusan), 3, 2, "酢酸（%）");
		}
		String strRituShokuen = checkData.GetValueStr("SA380", 0, 0, "ritu_shokuen");
		if (!(strRituShokuen.equals("") || strRituShokuen == null)) {
			// 食塩の数字範囲チェック（スーパークラスの数字範囲チェック）を行う。
			// ※引数1：食塩、引数2：0、引数3：999.99 	 メッセージパラメータ："食塩（%）"　メッセージコード：E000203　引数4：0、引数5：999.99
			super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr("SA380", 0, 0, "ritu_shokuen")), 0, 999.99, "食塩（%）");
			// 食塩の小数桁数チェック（スーパークラスの小数桁数チェック）を行う。
			// ※引数1：食塩、引数2：2 メッセージパラメータ："食塩（%）"　メッセージコード：E000204　引数4：3、引数5：2
			super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr("SA380", 0, 0, "ritu_shokuen")), 3, 2, "食塩（%）");
		}
		String strRituSousan = checkData.GetValueStr("SA380", 0, 0, "ritu_sousan");
		if (!(strRituSousan.equals("") || strRituSousan == null)) {
			// 総酸の数字範囲チェック（スーパークラスの数字範囲チェック）を行う。
			// ※引数1：総酸、引数2：0、引数3：999.99 	 メッセージパラメータ："総酸（%）"　メッセージコード：E000203　引数4：0、引数5：999.99
			super.rangeNumCheck(Double.parseDouble(strRituSousan), 0, 999.99, "総酸（%）");
			// 総酸の小数桁数チェック（スーパークラスの小数桁数チェック）を行う。
			// ※引数1：総酸、引数2：2 メッセージパラメータ："総酸（%）"　メッセージコード：E000204　引数4：3、引数5：2
			super.shousuRangeCheck(Double.parseDouble(strRituSousan), 3, 2, "総酸（%）");
		}
		String strRituAbura = checkData.GetValueStr("SA380", 0, 0, "ritu_abura");
		if (!(strRituAbura.equals("") || strRituAbura == null)) {
			// 油含有率の数字範囲チェック（スーパークラスの数字範囲チェック）を行う。
			// ※引数1：油含有率、引数2：0、引数3：999.99 	 メッセージパラメータ："油含有率（%）"　メッセージコード：E000203　引数4：0、引数5：999.99
			super.rangeNumCheck(Double.parseDouble(strRituAbura), 0, 999.99, "油含有率");
			// 油含有率の小数桁数チェック（スーパークラスの小数桁数チェック）を行う。
			// ※引数1：油含有率、引数2：2 メッセージパラメータ："油含有率"　メッセージコード：E000204　引数4：3、引数5：2
			super.shousuRangeCheck(Double.parseDouble(strRituAbura), 3, 2, "油含有率");
		}
// ADD start 20121005 QP@20505 No.24
		String strRituMsg = checkData.GetValueStr("SA380", 0, 0, "ritu_msg");
		if (!(strRituMsg.equals("") || strRituMsg == null)) {
			super.rangeNumCheck(Double.parseDouble(strRituMsg), 0, 999.99, "ＭＳＧ");
			super.shousuRangeCheck(Double.parseDouble(strRituMsg), 3, 2, "ＭＳＧ");
		}
// ADD end 20121005 QP@20505 No.24
		String strHyojian = checkData.GetValueStr("SA380", 0, 0, "hyojian");
		if (!(strHyojian.equals("") || strHyojian == null)) {
			// 表示案の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：表示案、引数2：200 	 メッセージパラメータ："表示案"　メッセージコード：E000212　引数4：200、引数5：空文字
			super.sizeCheckLen(strHyojian,"表示案", 200);
		}
		String strTenkabutu = checkData.GetValueStr("SA380", 0, 0, "tenkabutu");
		if (!(strTenkabutu.equals("") || strTenkabutu == null)) {
			// 添加物の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：添加物、引数2：200 	 メッセージパラメータ："添加物"　メッセージコード：E000212　引数4：200、引数5：空文字
			super.sizeCheckLen(strTenkabutu, "添加物", 200);
		}
		String strEiyoNo1 = checkData.GetValueStr("SA380", 0, 0, "no_eiyo1");
		String strEiyoNo2 = checkData.GetValueStr("SA380", 0, 0, "no_eiyo2");
		String strEiyoNo3 = checkData.GetValueStr("SA380", 0, 0, "no_eiyo3");
		String strEiyoNo4 = checkData.GetValueStr("SA380", 0, 0, "no_eiyo4");
		String strEiyoNo5 = checkData.GetValueStr("SA380", 0, 0, "no_eiyo5");
		if (!(strEiyoNo1.equals("") || strEiyoNo1 == null)) {
			// 栄養計算食品番号1～5の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
			// ※引数1：栄養計算食品番号、引数2：10 メッセージパラメータ："栄養計算食品番号"　メッセージコード：不明
			super.sizeCheckLen(strEiyoNo1, "栄養計算食品番号１", 10);
		}
		if (!(strEiyoNo2.equals("") || strEiyoNo2 == null)) {
			super.sizeCheckLen(strEiyoNo2, "栄養計算食品番号２", 10);
		}
		if (!(strEiyoNo3.equals("") || strEiyoNo3 == null)) {
			super.sizeCheckLen(strEiyoNo3, "栄養計算食品番号３", 10);
		}
		if (!(strEiyoNo4.equals("") || strEiyoNo4 == null)) {
			super.sizeCheckLen(strEiyoNo4, "栄養計算食品番号４", 10);
		}
		if (!(strEiyoNo5.equals("") || strEiyoNo5 == null)) {
			super.sizeCheckLen(strEiyoNo5, "栄養計算食品番号５", 10);
		}
		// 割合1～5の入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
		// ※引数1：割合、引数2：10 メッセージパラメータ："割合（%）"　メッセージコード：不明
		String strWariai1 = checkData.GetValueStr("SA380", 0, 0, "wariai1");
		String strWariai2 = checkData.GetValueStr("SA380", 0, 0, "wariai2");
		String strWariai3 = checkData.GetValueStr("SA380", 0, 0, "wariai3");
		String strWariai4 = checkData.GetValueStr("SA380", 0, 0, "wariai4");
		String strWariai5 = checkData.GetValueStr("SA380", 0, 0, "wariai5");
//		if (!(strWariai1.equals("") || strWariai1 == null)) {
//			super.sizeCheckLen(strWariai1, "割合１（%）", 10);
//		}
//		if (!(strWariai2.equals("") || strWariai2 == null)) {
//			super.sizeCheckLen(strWariai2, "割合２（%）", 10);
//		}
//		if (!(strWariai3.equals("") || strWariai3 == null)) {
//			super.sizeCheckLen(strWariai3, "割合３（%）", 10);
//		}
//		if (!(strWariai4.equals("") || strWariai4 == null)) {
//			super.sizeCheckLen(strWariai4, "割合４（%）", 10);
//		}
//		if (!(strWariai5.equals("") || strWariai5 == null)) {
//			super.sizeCheckLen(strWariai5, "割合５（%）", 10);
//		}
		if(toString(strWariai1) != ""){
			//数値チェック
			numberCheck(strWariai1,"割合1");
			//数値範囲チェック
			rangeNumCheck(
					toDouble(strWariai1)
					, 0.01
					, 999.99
					, "割合1");
			//少数桁数
			shousuRangeCheck(toDouble(strWariai1),2,"割合1");

		}
		if(toString(strWariai2) != ""){
			//数値チェック
			numberCheck(strWariai2,"割合2");
			//数値範囲チェック
			rangeNumCheck(
					toDouble(strWariai2)
					, 0.01
					, 999.99
					, "割合2");
			//少数桁数
			shousuRangeCheck(toDouble(strWariai2),2,"割合2");

		}
		if(toString(strWariai3) != ""){
			//数値チェック
			numberCheck(strWariai3,"割合3");
			//数値範囲チェック
			rangeNumCheck(
					toDouble(strWariai3)
					, 0.01
					, 999.99
					, "割合3");
			//少数桁数
			shousuRangeCheck(toDouble(strWariai3),2,"割合3");

		}
		if(toString(strWariai4) != ""){
			//数値チェック
			numberCheck(strWariai4,"割合4");
			//数値範囲チェック
			rangeNumCheck(
					toDouble(strWariai4)
					, 0.01
					, 999.99
					, "割合4");
			//少数桁数
			shousuRangeCheck(toDouble(strWariai4),2,"割合4");

		}
		if(toString(strWariai5) != ""){
			//数値チェック
			numberCheck(strWariai5,"割合5");
			//数値範囲チェック
			rangeNumCheck(
					toDouble(strWariai5)
					, 0.01
					, 999.99
					, "割合5");
			//少数桁数
			shousuRangeCheck(toDouble(strWariai5),2,"割合5");

		}

		// メモの入力桁数チェック（スーパークラスの入力桁数チェック）を行う。
		// ※引数1：メモ、引数2：200 メッセージパラメータ："メモ"　メッセージコード：E000212　引数4：200、引数5：空文字
		String strMemo = checkData.GetValueStr("SA380", 0, 0, "memo");
		if (!(strMemo.equals("") || strMemo == null)) {
			super.sizeCheckLen(strMemo, "メモ", 200);
		}
	}

}