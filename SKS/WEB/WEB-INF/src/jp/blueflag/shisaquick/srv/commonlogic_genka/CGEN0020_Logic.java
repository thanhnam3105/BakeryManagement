package jp.co.blueflag.shisaquick.srv.commonlogic_genka;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 計算項目算出
 * @author isono
 * @create 2009/10/26
 */
public class CGEN0020_Logic extends LogicBase {

	//対象データ特定情報
	//試作No-社員ID
	BigDecimal cd_shisaku_syainID = new BigDecimal(-1);
	//試作No-年
	int cd_shisaku_nen = -1;
	//試作No-追番
	int cd_shisaku_oi = -1;

	//【QP@00342】
	//試作No-枝番
	int cd_shisaku_eda = -1;

	//工程グループ（1：調味液パターン　2：その他調味液以外バターン　3：計算不能）
	int flgKoteGroup = -1;
	//希望原価単位
	String strNm_tani_kibogenka = "";

	// DBより取得する項目
	//【計算諸原　DB取得項目】容量
	double douYouryo = -1;
	//【計算諸原　DB取得項目】配合(㎏)
	double douHaigo = -1;
	//【計算諸原　DB取得項目】合計仕上重量(㎏)
	double douGokeShiagari = -1;
	//【計算諸原　DB取得項目】充填量水相(ｇ)
	double douZyutenSui = -1;
	//【計算諸原　DB取得項目】充填量油相(ｇ)
	double douZyutenYu = -1;
	//【計算諸原　DB取得項目】比重
	double douHizyu = -1;

	// 引数より取得する項目
	//【計算諸原　引数項目　】計算項目（固定費/ケースor固定費/kg）
	int intRagio_kesu_kg = -1;
	//【計算諸原　引数項目　】入り数
	double douIrisu = -1;
	//【計算諸原　引数項目　】希望原価
	double douKiboGenka = -1;
	//【計算諸原　引数項目　】希望原価単位CD
	int intKiboGenkaTani = -1;
	//【計算諸原　引数項目　】希望売価
	double douKiboBaika = -1;
	//【計算諸原　引数項目　】単価(円/㎏)
	double douTanka_gen = -1;
	//【計算諸原　引数項目　】歩留(％)
	double douBudomari_Gen = -1;
	//【計算諸原　引数項目　】有効歩留(％)
	double douYukoBudomari = -1;
	//【計算諸原　引数項目　】平均充填量(ｇ)
	double douHeikinZyuten = -1;
	//【計算諸原　引数項目　】単価(円)  資材
	double douTanka_Shizai = -1;
	//【計算諸原　引数項目　】歩留(％)  資材
	double douBudomari_Shizai = -1;
	//【計算諸原　引数項目　】使用量/ｹｰｽ  資材
	double douShiyouryo = -1;

	// 以下の項目は、メソッドにて取得
	//【計算諸原　計算項目　】レベル量(ｇ)
	double douLevelRyo = -1;
	//【計算諸原　計算項目　】材料費(円)/ケース
	//【計算諸原　計算項目　】原料費(円)/ケース
	double douGenryohiKesu = -1;
	//【計算諸原　計算項目　】原価計(円)/ケース
	double douGenkaKei_Kesu = -1;
	//【計算諸原　引数項目　】固定費(円)/ケース
	double douKotehi_Kesu = -1;
	//【計算諸原　計算項目　】原価計(円)/個
	double douGenkaKei_Ko = -1;
	//【計算諸原　計算項目　】原価計(円)/kg
	double douGenkaKei_Kg = -1;
	//【計算諸原　引数項目　】固定費(円)/kg
	double douKotehi_Kg = -1;
	// ADD 2013/11/1 QP@30154 okano start
	//【計算諸原　引数項目　】利益(円)/ケース
	double douRieki_Kesu = -1;
	//【計算諸原　引数項目　】利益(円)/kg
	double douRieki_Kg = -1;
	// ADD 2013/11/1 QP@30154 okano end
	//【計算諸原　計算項目　】金額計  資材
	double douKingakuKei_Shizai = -1;
	//【計算諸原　計算項目　】金額    資材
	//【　　　　　計算項目　】金額(円)
	//【　　　　　計算項目　】比重加算量(ｇ)
	double douHizykasanRyo = -1;
	//【　　　　　計算項目　】原料費(円)/kg
	double douGenryohiKg = -1;
	//【　　　　　計算項目　】材料費(円)/kg
	double douZairyohi_Kg = -1;
	//【　　　　　計算項目　】売価
	//【　　　　　計算項目　】粗利(％)
	double douArari = -1;

	//試作情報
	List<?> listShisaku = null;
	//ｻﾝﾌﾟﾙ情報
	List<?> listSanpuru = null;
	//原料情報
	List<?> listGenryo = null;

	//水相合計単価
	double gokeTanka_sui = -1;
	//水相合計重量
	double gokeJyuryo_sui = -1;
	//油相合計単価
	double gokeTanka_yu = -1;
	//油相合計重量
	double gokeJyuryo_yu = -1;

	//【QP@00342】
	//容量（元版）
	double yoryo_moto = -1;

	/**
	 * コンストラクタ
	 */
	public CGEN0020_Logic() {
		//基底クラスのコンストラクタ
		super();

	}
	/**
	 * 計算項目の算出を行う
	 * @param reqData	：リクエストデータ
	 * @param userInfo	：ユーザー情報
	 * @return RequestResponsKindBean	：計算結果
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsKindBean ExecLogic(

			RequestResponsKindBean reqData
			,UserInfoData          userInfo
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = userInfo;
		//レスポンスデータ
		RequestResponsKindBean ret = null;


		try{

			//試作No退避
			cd_shisaku_syainID = toDecimal(reqData.getFieldVale("kihon", "rec", "cd_shain"),"-1");
			cd_shisaku_nen = toInteger(reqData.getFieldVale("kihon", "rec", "nen"),-1);
			cd_shisaku_oi = toInteger(reqData.getFieldVale("kihon", "rec", "no_oi"),-1);

			//【QP@00342】
			cd_shisaku_eda = toInteger(reqData.getFieldVale("kihon", "rec", "no_eda"),-1);

			//DB取得項目の取得
			//計算諸原
			getDBKomoku();

			// DEL 2013/7/2 shima【QP@30151】No.37 start
//			//希望原価の単位
//			strNm_tani_kibogenka = seachNmTaniKibogenka(
//					toString(reqData.getFieldVale("kihon", "rec", "kibo_genka_tani")));
			// DEL 2013/7/2 shima【QP@30151】No.37 end

			//レスポンスのインスタンス
			ret = new RequestResponsKindBean();
			//計算項目算出
			calcuExec(reqData, ret);

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価の再計算に失敗しました。");

		}finally{
			//ローカル変数の開放
			removeList(listShisaku);
			removeList(listSanpuru);
			removeList(listGenryo);
		}

		return ret;

	}
	/**
	 * 計算項目、算出の実装
	 * @param reqData		：リクエストデータ
	 * @param ret			：計算結果データ
	 * @param listShisaku	：検索結果（試作情報）
	 * @param listSanpuru	：検索結果（ｻﾝﾌﾟﾙ情報）
	 * @param listGenryo	：検索結果（原料情報）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuExec(

			RequestResponsKindBean reqData
			, RequestResponsKindBean resData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//計算結果データクラス生成
		resData.setID("CGEN0020");

		try{
			//基本生成
			resData.addFieldVale("kihon", "rec", "flg_return", "true");
			resData.addFieldVale("kihon", "rec", "msg_error", "");
			resData.addFieldVale("kihon", "rec", "nm_class", "");
			resData.addFieldVale("kihon", "rec", "no_errmsg", "");
			resData.addFieldVale("kihon", "rec", "cd_error", "");
			resData.addFieldVale("kihon", "rec", "msg_system", "");

			if (toString(reqData.getFieldVale("kihon", "rec", "mode")).equals("1")){
				//原料金額の算出
				calcuKingakuGenryo(reqData, resData);

				//資材金額の算出
				calcuKingakuShizai(reqData, resData, listShisaku, listGenryo);

				//原価部の算出
				calcuGenka(reqData, resData);

			}else{
				//資材金額の算出
				calcuKingakuShizai(reqData, resData, listShisaku, listGenryo);

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　計算項目、算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 計算項目を算出しレスポンスデータを生成する。
	 * @param reqData		：リクエストデータ
	 * @param resData		：レスポンスデータ（結果が格納されます）
	 * @param listShisaku	：DB試作情報
	 * @param listSanpuru	：DBサンプル情報
	 * @param listGenryo	：DB原料情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenka(

			RequestResponsKindBean reqData
			, RequestResponsKindBean resData
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int i = 0;
		int ix = 0;
		Object[] items = null;
		String errMsg = "";

		try{

			items = (Object[]) listShisaku.get(0);
			//容量
			douYouryo = toDouble(items[3], -1);

			for (i =0; i < reqData.getCntRow("keisan"); i++){

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				String strKoteiChk = reqData.getFieldVale("keisan", i, "fg_koumokuchk");
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				//DB情報の特定（サンプル情報）
				ix = seachSanpuruIndex(
						reqData.getFieldVale("keisan", i, "seq_shisaku")
						);
				items = (Object[]) listSanpuru.get(ix);

				//レコード処理結果RESULT追加
				resData.addFieldVale("keisan", "rec" + toString(i), "flg_return", "true");
				resData.addFieldVale("keisan", "rec" + toString(i), "msg_error", "");
				resData.addFieldVale("keisan", "rec" + toString(i), "nm_class", "");
				resData.addFieldVale("keisan", "rec" + toString(i), "no_errmsg", "");
				resData.addFieldVale("keisan", "rec" + toString(i), "cd_error", "");
				resData.addFieldVale("keisan", "rec" + toString(i), "msg_system", "");

				// ----------------------------------------------------------------------+
				// 試作SEQ		seq_shisaku
				// ----------------------------------------------------------------------+
				resData.addFieldVale("keisan", "rec" + toString(i), "seq_shisaku"
						, reqData.getFieldVale("keisan", i, "seq_shisaku"));
				// ----------------------------------------------------------------------+
				// 試算日		shisan_date
				// ----------------------------------------------------------------------+

				//リクエストに試算日が存在するかの確認
//2010/03/02 UPDATE ISONO START [バグ対応　検索ﾚｺｰﾄﾞ名（正：rec誤：rec1）が誤っているためshisan_dateが取得できない。ﾚｺｰﾄﾞをｲﾝﾃﾞｯｸｽ取得に変更]
//				int index = reqData.getItemNo("keisan" , "rec" + toString(i) , "shisan_date");
				int index = reqData.getItemNo("keisan" , i , "shisan_date");
//2010/03/02 UPDATE ISONO END   [バグ対応　検索ﾚｺｰﾄﾞ名（正：rec誤：rec1）が誤っているためshisan_dateが取得できない。ﾚｺｰﾄﾞをｲﾝﾃﾞｯｸｽ取得に変更]

				if( index > -1 ){

					//リクエストに試算日がある場合は優先して返却する
					resData.addFieldVale("keisan", "rec" + toString(i), "shisan_date"
							, toString(reqData.getFieldVale("keisan", i, "shisan_date")));

				}
				else{

					//リクエストに試算日がない場合はDB検索値を返却する
					resData.addFieldVale("keisan", "rec" + toString(i), "shisan_date"
							, toString(items[10]));

				}

//				try{
//					//リクエストに試算日がある場合は優先して返却する
//					resData.addFieldVale("keisan", "rec" + toString(i), "shisan_date"
//							, toString(reqData.getFieldVale("keisan", i, "shisan_date")));
//				}catch(Exception e){
//					//リクエストに試算日がない場合はDB検索値を返却する
//					resData.addFieldVale("keisan", "rec" + toString(i), "shisan_date"
//							, toString(items[10]));
//
//				}

				// ----------------------------------------------------------------------+
				// ｻﾝﾌﾟﾙNo（名称）	nm_sanpuru
				// ----------------------------------------------------------------------+
				resData.addFieldVale("keisan", "rec" + toString(i), "nm_sanpuru"
						, toString(items[9]));
				// ----------------------------------------------------------------------+
				// 充填量水相（ｇ）	jyuuten_suiso
				// ----------------------------------------------------------------------+
				douZyutenSui = toDouble(items[5], -1);

				//【QP@00342】元版
				if(cd_shisaku_eda == 0){

				}
				//枝版
				else{
					//元版の水相充填量/元版の内容量×枝版の内容量
					douZyutenSui = douZyutenSui / yoryo_moto * douYouryo;
				}

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					douZyutenSui = toDouble(reqData.getFieldVale("keisan", i, "zyusui"), -1);
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				if(douZyutenSui > -1){
					resData.addFieldVale("keisan", "rec" + toString(i), "jyuuten_suiso"
							, toString(douZyutenSui, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "jyuuten_suiso"
							, "");

				}

				// ----------------------------------------------------------------------+
				// 充填量油相（ｇ）	jyuuten_yuso
				// ----------------------------------------------------------------------+
				douZyutenYu = toDouble(items[6], -1);

				//【QP@00342】元版
				if(cd_shisaku_eda == 0){

				}
				//枝版
				else{
					//元版の充填量油相/元版の内容量×枝版の内容量
					douZyutenYu = douZyutenYu / yoryo_moto * douYouryo;
				}

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					douZyutenYu = toDouble(reqData.getFieldVale("keisan", i, "zyuabura"), -1);
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				if (douZyutenYu > -1){
					resData.addFieldVale("keisan", "rec" + toString(i), "jyuuten_yuso"
							, toString(douZyutenYu, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "jyuuten_yuso"
							, "");

				}

				// ----------------------------------------------------------------------+
				// 合計量		gookezyuryo
				// ----------------------------------------------------------------------+
				//【QP@00342】
//				resData.addFieldVale("keisan", "rec" + toString(i), "gookezyuryo"
//						, toString(items[11]));
				//元版
				if(cd_shisaku_eda == 0){
					resData.addFieldVale("keisan", "rec" + toString(i), "gookezyuryo"
							, toString(items[11]));
				}
				//枝版
				else{
					//充填量水相（ｇ） + 充填量油相（ｇ）
					double goukei = douZyutenSui + douZyutenYu;
					resData.addFieldVale("keisan", "rec" + toString(i), "gookezyuryo"
							, toString(goukei, 2, 2, true, ""));

				}

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "gookezyuryo"
							, toString(reqData.getFieldVale("keisan", i, "gokei"),""));
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				// ----------------------------------------------------------------------+
				// 比重			hizyu
				// ----------------------------------------------------------------------+
				resData.addFieldVale("keisan", "rec" + toString(i), "hizyu"
						, toString(items[7]));

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					resData.addFieldVale("keisan", "rec" + toString(i), "hizyu"
							, reqData.getFieldVale("keisan", i, "hiju"));
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				// ----------------------------------------------------------------------+
				// 有効歩留（％）	yuuko_budomari
				// ----------------------------------------------------------------------+
				douYukoBudomari = toDouble(reqData.getFieldVale("keisan", i, "yuuko_budomari"), -1);
				if(douYukoBudomari > -1){
					resData.addFieldVale(
							"keisan", "rec" + toString(i)
							, "yuuko_budomari"
							, toString(
									douYukoBudomari
									, 2
									, 1
									, false
									, "")
							);

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "yuuko_budomari"
							, "");

				}
				// ----------------------------------------------------------------------+
				// レベル量（ｇ）	reberuryo	?
				// ----------------------------------------------------------------------+
				calcuReberuryo(
						douYouryo
						, toDouble(reqData.getFieldVale("kihon", "rec", "irisu"), -1)
						);

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					douLevelRyo=toDouble(reqData.getFieldVale("keisan", i, "reberu"), -1);
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				if (douLevelRyo > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "reberuryo"
							, toString(douLevelRyo, 6, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "reberuryo"
							, "");

				}

				// ----------------------------------------------------------------------+
				// 平均充填量（ｇ）	heikinjyutenryo
				// ----------------------------------------------------------------------+
				douHeikinZyuten = toDouble(reqData.getFieldVale("keisan", i, "heikinjyutenryo"), -1);
				if(douHeikinZyuten > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "heikinjyutenryo"
							, toString(
									douHeikinZyuten
									, 6
									, 2
									, true
									, "")
							);

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "heikinjyutenryo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 比重加算量（ｇ）	hizyukasanryo
				// ----------------------------------------------------------------------+
				calcuHizyukasanryo(
						douYouryo
						, toDouble(reqData.getFieldVale("kihon", "rec", "irisu"), -1)
						, toDouble(items[7], -1)
						);

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					douHizykasanRyo=toDouble(reqData.getFieldVale("keisan", i, "hijukasan"), -1);
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				if (douHizykasanRyo > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "hizyukasanryo"
							, toString(douHizykasanRyo, 6, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "douHizykasanRyo"
							, "");

				}

				// ----------------------------------------------------------------------+
				// 原料費/ケース	kesu_genryohi
				// ----------------------------------------------------------------------+
				calcuGenryohiKesu(
						reqData
						, reqData.getFieldVale("keisan", i, "seq_shisaku")
						);

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					douGenryohiKesu=toDouble(reqData.getFieldVale("keisan", i, "cs_genryo"), -1);
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				if (douGenryohiKesu > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_genryohi"
							, toString(douGenryohiKesu, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_genryohi"
							, "");

				}

				// ----------------------------------------------------------------------+
				// 材料費/ケース	kesu_zairyohi
				// ----------------------------------------------------------------------+
				if (douKingakuKei_Shizai > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_zairyohi"
							, toString(douKingakuKei_Shizai, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_zairyohi"
							, "");

				}

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_zairyohi"
							, toString(toDouble(reqData.getFieldVale("keisan", i, "cs_zairyohi"),-1), 2, 2, true, ""));
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				// ----------------------------------------------------------------------+
				// 固定費/ケース	kesu_kotehi
				// ----------------------------------------------------------------------+
				//MOD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg").equals("1")
						|| strKoteiChk.equals("1")){
					//ラジオボタンが固定/ｹｰｽが選択されている場合
					//入力値を設定
					douKotehi_Kesu = toDouble(reqData.getFieldVale("keisan", i, "kesu_kotehi"));

				}else{
					//ラジオボタンが固定/Kgが選択されている場合
					//計算値を設定
					calcuKoteihi_Kesu(reqData, i);

				}
				//MOD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
				if (douKotehi_Kesu > 0){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_kotehi"
							, toString(douKotehi_Kesu, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_kotehi"
							, "");

				}

				// ADD 2013/11/1 QP@30154 okano start
				// ----------------------------------------------------------------------+
				// 利益/ケース	kesu_rieki
				// ----------------------------------------------------------------------+
				//MOD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg").equals("1")
						|| strKoteiChk.equals("1")){
					//ラジオボタンが固定/ｹｰｽが選択されている場合
					//入力値を設定
					douRieki_Kesu = toDouble(reqData.getFieldVale("keisan", i, "kesu_rieki"));

				}else{
					//ラジオボタンが固定/Kgが選択されている場合
					//計算値を設定
					calcuRieki_Kesu(reqData, i);

				}
				//MOD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
				if (douRieki_Kesu > 0){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_rieki"
							, toString(douRieki_Kesu, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_rieki"
							, "");
					// ADD 2013/12/4 QP@30154 okano start
					douRieki_Kesu = 0;
					// ADD 2013/12/4 QP@30154 okano end

				}
				// ADD 2013/11/1 QP@30154 okano end

				// ----------------------------------------------------------------------+
				// 原価計/ケース	kesu_genkake
				// ----------------------------------------------------------------------+
				calcuGenkaKei_Kesu();

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					douGenkaKei_Kesu=toDouble(reqData.getFieldVale("keisan", i, "cs_genka"), -1);
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				if (douGenkaKei_Kesu > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_genkake"
							, toString(douGenkaKei_Kesu, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_genkake"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 原価計/個		ko_genkake
				// ----------------------------------------------------------------------+
				calcuGenkaKei_ko();

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					douGenkaKei_Ko=toDouble(reqData.getFieldVale("keisan", i, "ko_genka"), -1);
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				if (douGenkaKei_Ko > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "ko_genkake"
							, toString(douGenkaKei_Ko, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_genkake"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 原料費/KG		kg_genryohi
				// ----------------------------------------------------------------------+
				calcuGenryohiKg();

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					douGenryohiKg=toDouble(reqData.getFieldVale("keisan", i, "kg_genryo"), -1);
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				if (douGenryohiKg > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kg_genryohi"
							, toString(douGenryohiKg, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kg_genryohi"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 材料費/KG		kg_zairyohi
				// ----------------------------------------------------------------------+
				calcuZairyohiKg();

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					douZairyohi_Kg=toDouble(reqData.getFieldVale("keisan", i, "kg_zairyohi"), -1);
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				if (douZairyohi_Kg > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kg_zairyohi"
							, toString(douZairyohi_Kg, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kg_zairyohi"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 固定費/KG		kg_kotehi
				// ----------------------------------------------------------------------+
				//MOD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg").equals("2")
						|| strKoteiChk.equals("1")){
					//ラジオボタンが固定/kgが選択されている場合
					//入力値を設定
					douKotehi_Kg = toDouble(reqData.getFieldVale("keisan", i, "kg_kotehi"));

				}else{
					//ラジオボタンが固定/ｹｰｽが選択されている場合
					//計算値を設定
					calcuKoteihi_Kg(reqData, i);

				}
				//MOD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
				if (douKotehi_Kg > 0){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kg_kotehi"
							, toString(douKotehi_Kg, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kg_kotehi"
							, "");

				}

				// ADD 2013/11/1 QP@30154 okano start
				// ----------------------------------------------------------------------+
				// 利益/KG		kg_rieki
				// ----------------------------------------------------------------------+
				//MOD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg").equals("2")
						|| strKoteiChk.equals("1")){
					//ラジオボタンが固定/kgが選択されている場合
					//入力値を設定
					douRieki_Kg = toDouble(reqData.getFieldVale("keisan", i, "kg_rieki"));

				}else{
					//ラジオボタンが固定/ｹｰｽが選択されている場合
					//計算値を設定
					calcuRieki_Kg(reqData, i);

				}
				//MOD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
				if (douRieki_Kg > 0){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kg_rieki"
							, toString(douRieki_Kg, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kg_rieki"
							, "");
					// ADD 2013/12/4 QP@30154 okano start
					douRieki_Kg = 0;
					// ADD 2013/12/4 QP@30154 okano end

				}
				// ADD 2013/11/1 QP@30154 okano end
				// ----------------------------------------------------------------------+
				// 原価計/KG		kg_genkake
				// ----------------------------------------------------------------------+
				calcuGenkaKei_Kg();

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					douGenkaKei_Kg=toDouble(reqData.getFieldVale("keisan", i, "kg_genka"), -1);
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				if (douGenkaKei_Kg > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kg_genkake"
							, toString(douGenkaKei_Kg, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kg_genkake"
							, "");

				}

				// ----------------------------------------------------------------------+
				// 売価			baika
				// ----------------------------------------------------------------------+

				// ADD 2013/7/2 shima【QP@30151】No.37 start
				//希望原価の単位
				strNm_tani_kibogenka = seachNmTaniKibogenka(
						toString(reqData.getFieldVale("kihonsub",i, "kibo_genka_tani")));
				// ADD 2013/7/2 shima【QP@30151】No.37 end
				// MOD 2013/7/2 shima【QP@30151】No.37 start
//				douKiboBaika = toDouble(reqData.getFieldVale("kihon", "rec", "kibo_baika"), -1);
				douKiboBaika = toDouble(reqData.getFieldVale("kihonsub", i, "kibo_baika"), -1);
				// MOD 2013/7/2 shima【QP@30151】No.37 end

				if (douKiboBaika > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "baika"
							, toString(douKiboBaika, 2, 2, true, "") + strNm_tani_kibogenka);

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "baika"
							, "");

				}

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				//単位も一緒に保持している為、このタイミングで設定する
				if(strKoteiChk.equals("1")){
					resData.addFieldVale("keisan", "rec" + toString(i), "baika"
							, reqData.getFieldVale("keisan", i, "baika"));
				}
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				// ----------------------------------------------------------------------+
				// 粗利（％）		arari
				// ----------------------------------------------------------------------+
//				if(reqData.getFieldVale("kihon", "rec", "kibo_genka_tani").equals("001")){
				if(reqData.getFieldVale("kihonsub", i, "kibo_genka_tani").equals("001")){
					//原価希望単位が　個　の場合
					calcuArari_Ko();

				}else{
					//原価希望単位が　kg　の場合
					calcuArari_Kg();

				}

//				if (douArari > -1){
//					resData.addFieldVale(
//							"keisan"
//							, "rec" + toString(i)
//							, "arari"
//							, toString(douArari, 2, 2, true, ""));
//
//				}else{
//					resData.addFieldVale("keisan", "rec" + toString(i), "arari"
//							, "");

//				}

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//固定項目がチェックされている場合は、計算値ではなく画面値 or DB値を設定する
				if(strKoteiChk.equals("1")){
					//正規表現：数値確認用
					String strArari = reqData.getFieldVale("keisan", i, "arari");
					Pattern pattern = Pattern.compile("^[-]?[0-9,]*[.]?[0-9]+");
					Matcher matcher = pattern.matcher(strArari);
					if (matcher.find()) {
						strArari = matcher.group();
					} else {
						strArari = "";
					}
					douArari=toDouble(strArari, -1);
				}

				resData.addFieldVale(
						"keisan"
						, "rec" + toString(i)
						, "arari"
						, toString(douArari, 2, 2, true, ""));
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

				// ----------------------------------------------------------------------+
				//　レベル量・平均充填量の比較
				// ----------------------------------------------------------------------+
				//【QP@00342】試算中止の場合はチェック対象外
				if(toString(reqData.getFieldVale("keisan", i, "fg_chusi")).equals("")){
					if( douLevelRyo > douHeikinZyuten && douHeikinZyuten > -1 ){
						errMsg += "【"+(i+1)+"列目】";
					}
				}
				else{

				}

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.38
				// ----------------------------------------------------------------------+
				// 製造工程版			seizokotei_han
				// ----------------------------------------------------------------------+
				resData.addFieldVale("keisan", "rec" + toString(i), "seizokotei_han"
							, reqData.getFieldVale("keisan", i, "seizokotei_han"));
				// ----------------------------------------------------------------------+
				// 製造工程			seizokotei_shosai
				// ----------------------------------------------------------------------+
				resData.addFieldVale("keisan", "rec" + toString(i), "seizokotei_shosai"
							, reqData.getFieldVale("keisan", i, "seizokotei_shosai"));

//mod end --------------------------------------------------------------------------------


				//【QP@00342】試算中止
				resData.addFieldVale("keisan", "rec" + toString(i), "fg_chusi"
						, reqData.getFieldVale("keisan", i, "fg_chusi"));

//ADD 2013/07/9 ogawa 【QP@30151】No.13 start
				// ----------------------------------------------------------------------+
				// 項目固定チェック	fg_koumokuchk
				// ----------------------------------------------------------------------+

				resData.addFieldVale("keisan", "rec" + toString(i), "fg_koumokuchk"
						, reqData.getFieldVale("keisan", i, "fg_koumokuchk"));
//ADD 2013/07/09 ogawa 【QP@30151】No.13 end
			}
			//レスポンス基本の追加
			// ----------------------------------------------------------------------+
			//　サンプル数（列）
			// ----------------------------------------------------------------------+
			resData.addFieldVale("kihon", "rec", "cnt_sanpuru"
					, toString(reqData.getCntRow("keisan")));
			// ----------------------------------------------------------------------+
			//　製造工程
			// ----------------------------------------------------------------------+
			resData.addFieldVale("kihon", "rec", "koute"
					, toString(seachSeizoKote(reqData)));
			// ----------------------------------------------------------------------+
			//　計算項目（固定費/ケースor固定費/kg）
			// ----------------------------------------------------------------------+
			resData.addFieldVale("kihon", "rec", "ragio_kesu_kg"
					, toString(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg")));



			// ----------------------------------------------------------------------+
			//　レベル量・平均充填量の比較
			// ----------------------------------------------------------------------+
			if( toString(errMsg).equals("") ){

			}else{
				resData.addFieldVale("kihon", "rec", "err_msg"
						, errMsg + "平均充填量はレベル量以上の値を入力して下さい。");
			}



		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原価の算出に失敗しました。");

		}finally{
			//ローカル変数の開放
			items = null;

		}

	}
	/**
	 * 粗利（％）/kg　の算出
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuArari_Kg(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　（1－原価計（円）/Kg÷希望売価）×100
		//+　
		//+　【計算諸原　計算項目　】原価計(円)/kg	douGenkaKei_Kg
		//+　【計算諸原　引数項目　】希望売価			douKiboBaika
		//+　
		//+--------------------------------------------+

		//【　　　　　計算項目　】粗利(％)
		douArari = -1;

		try{

			//計算実施判定
			if (
					douGenkaKei_Kg > -1
					&&	douKiboBaika > -1
					){

				//計算
				if (douKiboBaika > 0){
					douArari = ( 1 - douGenkaKei_Kg / douKiboBaika ) * 100;

				}else{
					douArari = 0;

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　粗利（％）/kgの算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 粗利（％）/個　の算出
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuArari_Ko(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　（1－原価計（円）/個÷希望売価）×100
		//+　
		//+　【計算諸原　計算項目　】原価計(円)/個	douGenkaKei_Ko
		//+　【計算諸原　引数項目　】希望売価			douKiboBaika
		//+　
		//+--------------------------------------------+

		//【　　　　　計算項目　】粗利(％)
		douArari = -1;

		try{

			//計算実施判定
			if (
					douGenkaKei_Ko > -1
					&&	douKiboBaika > -1
					){

				//計算
				if (douKiboBaika > 0){
					douArari = ( 1 - douGenkaKei_Ko / douKiboBaika ) * 100;

				}else{
					douArari = 0;

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　粗利（％）/個の算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 原価計/Kgの算出
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenkaKei_Kg(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　【2009/11/27　課題管理：119　変更】原価計/ケース÷レベル量(g)×1000
		//+　原価計/ケース÷レベル量(g)
		//+　
		//+　【計算諸原　計算項目　】原価計(円)/ケース	douGenkaKei_Kesu
		//+　【計算諸原　計算項目　】レベル量(ｇ)		douLevelRyo
		//+　
		//+--------------------------------------------+

		//【計算諸原　計算項目　】原価計(円)/kg
		douGenkaKei_Kg = -1;

		try{

			//計算実施判定
			// MOD 2013/12/7 QP@30154 okano start
//				if (
//						douGenkaKei_Kesu > -1
//						&&	douLevelRyo > -1
//						){
//
//					//計算
//					if(douLevelRyo > 0){
//	//					douGenkaKei_Kg = douGenkaKei_Kesu / douLevelRyo * 1000;
//						douGenkaKei_Kg = douGenkaKei_Kesu / douLevelRyo;
//
//					}else{
//						douGenkaKei_Kg = 0;
//
//					}
//
//				}
			if (
					douGenryohiKg > -1
					&&	douZairyohi_Kg > -1
					&&	douKotehi_Kg > -1
					){
						douGenkaKei_Kg = douGenryohiKg + douZairyohi_Kg + douKotehi_Kg + douRieki_Kg;

			}
			// MOD 2013/12/7 QP@30154 okano end

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原価計/Kgの算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 固定費/Kgの算出
	 * @param reqData	：リクエストデータ
	 * @param seq		：列インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuKoteihi_Kg(

			RequestResponsKindBean reqData
			, int seq
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　【2009/11/27　課題管理：119　変更】固定費/ケース÷レベル量(g)×1000
		//+　固定費/ケース÷レベル量(g)
		//+　
		//+　【計算諸原　引数項目　】固定費(円)/ケース	douKotehi_Kesu
		//+　【計算諸原　計算項目　】レベル量(ｇ)		douLevelRyo
		//+　
		//+--------------------------------------------+

		//【計算諸原　引数項目　】固定費(円)/kg
		douKotehi_Kg = -1;

		try{

			//計算諸原の収集
			//固定費(円)/kg
			douKotehi_Kesu = toDouble(reqData.getFieldVale("keisan", seq, "kesu_kotehi"), -1);

			//計算実施判定
			if (
					douKotehi_Kesu > -1
				&&	douLevelRyo > -1
					){

				if (	douKotehi_Kesu > 0
						&&	douLevelRyo > 0
					){

//					douKotehi_Kg = douKotehi_Kesu / douLevelRyo * 1000;
					douKotehi_Kg = douKotehi_Kesu / douLevelRyo;

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　固定費/Kgの算出に失敗しました。");

		}finally{

		}

	}
	// ADD 2013/11/1 QP@30154 okano start
	/**
	 * 利益/Kgの算出
	 * @param reqData	：リクエストデータ
	 * @param seq		：列インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuRieki_Kg(

			RequestResponsKindBean reqData
			, int seq
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　【2009/11/27　課題管理：119　変更】利益/ケース÷レベル量(g)×1000
		//+　利益/ケース÷レベル量(g)
		//+　
		//+　【計算諸原　引数項目　】利益(円)/ケース	douKotehi_Kesu
		//+　【計算諸原　計算項目　】レベル量(ｇ)		douLevelRyo
		//+　
		//+--------------------------------------------+

		//【計算諸原　引数項目　】利益(円)/kg
		douRieki_Kg = -1;

		try{

			//計算諸原の収集
			//利益(円)/kg
			douRieki_Kesu = toDouble(reqData.getFieldVale("keisan", seq, "kesu_rieki"), -1);

			//計算実施判定
			if (
					douRieki_Kesu > -1
				&&	douLevelRyo > -1
					){

				if (	douRieki_Kesu > 0
						&&	douLevelRyo > 0
					){

					douRieki_Kg = douRieki_Kesu / douLevelRyo;

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　利益/Kgの算出に失敗しました。");

		}finally{

		}

	}
	// ADD 2013/11/1 QP@30154 okano end
	/**
	 * 材料費/KGの算出
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuZairyohiKg(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　【2009/11/27　課題管理：119　変更】材料費/ケース÷レベル量(g)×1000
		//+　材料費/ケース÷レベル量(g)
		//+　
		//+　【計算諸原　計算項目　】金額計  資材	douKingakuKei_Shizai
		//+　【計算諸原　計算項目　】レベル量(ｇ)	douLevelRyo
		//+　
		//+--------------------------------------------+

		//【　　　　　計算項目　】材料費(円)/kg
		douZairyohi_Kg = -1;

		try{

			//計算実施判定
			if (
					douKingakuKei_Shizai > -1
					&&	douLevelRyo > -1
					){

				if(douLevelRyo > 0){
//					douZairyohi_Kg = douKingakuKei_Shizai / douLevelRyo * 1000;
					douZairyohi_Kg = douKingakuKei_Shizai / douLevelRyo;

				}else{
					douZairyohi_Kg = 0;

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　材料費/KGの算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 原料費/KGの算出
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenryohiKg(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　【2009/11/27　課題管理：119　変更】原料費/ケース÷レベル量(g)×1000
		//+　原料費/ケース÷レベル量(g)
		//+　
		//+　【計算諸原　計算項目　】原料費(円)/ケース	douGenryohiKesu
		//+　【計算諸原　計算項目　】レベル量(ｇ)		douLevelRyo
		//+　
		//+--------------------------------------------+

		//【　　　　　計算項目　】原料費(円)/kg
		douGenryohiKg = -1;

		try{

			//計算実施判定
			if (
					douGenryohiKesu > -1
					&&	douLevelRyo > -1
					){

				if (douLevelRyo > 0){
//					douGenryohiKg = douGenryohiKesu / douLevelRyo * 1000;
					// MOD 2013/8/2 okano【QP@30151】No.34 start
//						douGenryohiKg = douGenryohiKesu / douLevelRyo;
					douGenryohiKg = douGenryohiKesu / ( douLevelRyo * douHizyu );
					// MOD 2013/8/2 okano【QP@30151】No.34 end

				}else{
					douGenryohiKg = 0;

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原料費/KGの算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 原価計/個の算出
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenkaKei_ko(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　原価計/ケース÷入数
		//+　
		//+　【計算諸原　計算項目　】原価計(円)/ケース	douGenkaKei_Kesu
		//+　【計算諸原　引数項目　】入り数			douIrisu
		//+　
		//+--------------------------------------------+

		//【計算諸原　計算項目　】原価計(円)/個
		douGenkaKei_Ko = -1;

		try{

			//計算実施判定
			if (
					douGenkaKei_Kesu > -1
					&&	douIrisu > -1
					){

				if (douIrisu > 0){
					douGenkaKei_Ko = douGenkaKei_Kesu / douIrisu;

				}else{
					douGenkaKei_Ko = 0;

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原価計/個の算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 原価計/ケースの算出
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenkaKei_Kesu(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　原料費/ケース+材料費/ケース+固定費/ケース
		//+　
		//+　【計算諸原　引数項目　】固定費(円)/ケース	douKotehi_Kesu
		//+　【計算諸原　計算項目　】金額計  資材		douKingakuKei_Shizai
		//+　【計算諸原　計算項目　】原料費(円)/ケース	douGenryohiKesu
		//+　
		//+--------------------------------------------+
		//【計算諸原　計算項目　】原価計(円)/ケース
		douGenkaKei_Kesu = -1;

		try{

			//計算実施判定
			if (
					douKotehi_Kesu > -1
					&&	douKingakuKei_Shizai > -1
					&&	douGenryohiKesu > -1
					){

				// MOD 2013/11/7 QP@30154 okano start
//					douGenkaKei_Kesu = douGenryohiKesu + douKingakuKei_Shizai + douKotehi_Kesu;
				douGenkaKei_Kesu = douGenryohiKesu + douKingakuKei_Shizai + douKotehi_Kesu + douRieki_Kesu;
				// MOD 2013/11/7 QP@30154 okano end
			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原価計/ケースの算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 固定費/ケースの算出
	 * @param reqData	：リクエストデータ
	 * @param seq		：列インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuKoteihi_Kesu(

			RequestResponsKindBean reqData
			, int seq
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　【2009/11/27　課題管理：119　変更】固定費/㎏×レベル量（ｇ）/1000
		//+　固定費/㎏×レベル量（ｇ）
		//+　
		//+　【計算諸原　引数項目　】固定費(円)/kg	douKotehi_Kg
		//+　【計算諸原　計算項目　】レベル量(ｇ)		douLevelRyo
		//+　
		//+--------------------------------------------+

		//【計算諸原　引数項目　】固定費(円)/ケース
		douKotehi_Kesu = -1;

		try{

			//計算諸原の収集
			//固定費(円)/kg
			douKotehi_Kg = toDouble(reqData.getFieldVale("keisan", seq, "kg_kotehi"), -1);

			//計算実施判定
			if (
					douKotehi_Kg > -1
				&&	douLevelRyo > -1
					){

				if (
						douKotehi_Kg > 0
					&&	douLevelRyo > 0
						){

//					douKotehi_Kesu = douKotehi_Kg * douLevelRyo / 1000;
					douKotehi_Kesu = douKotehi_Kg * douLevelRyo;

				}


			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　固定費/ケースの算出に失敗しました。");

		}finally{

		}

	}
	// ADD 2013/11/1 QP@30154 okano start
	/**
	 * 利益/ケースの算出
	 * @param reqData	：リクエストデータ
	 * @param seq		：列インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuRieki_Kesu(

			RequestResponsKindBean reqData
			, int seq
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+　
		//+　【2009/11/27　課題管理：119　変更】利益/㎏×レベル量（ｇ）/1000
		//+　利益/㎏×レベル量（ｇ）
		//+　
		//+　【計算諸原　引数項目　】利益(円)/kg	douRieki_Kg
		//+　【計算諸原　計算項目　】レベル量(ｇ)		douLevelRyo
		//+　
		//+--------------------------------------------+

		//【計算諸原　引数項目　】利益(円)/ケース
		douRieki_Kesu = -1;

		try{

			//計算諸原の収集
			//利益(円)/kg
			douRieki_Kg = toDouble(reqData.getFieldVale("keisan", seq, "kg_rieki"), -1);

			//計算実施判定
			if (
					douRieki_Kg > -1
				&&	douLevelRyo > -1
					){

				if (
						douRieki_Kg > 0
					&&	douLevelRyo > 0
						){

					douRieki_Kesu = douRieki_Kg * douLevelRyo;

				}


			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　利益/ケースの算出に失敗しました。");

		}finally{

		}

	}
	// ADD 2013/11/1 QP@30154 okano end
	/**
	 * 原料費（円）/ケースの算出
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenryohiKesu(

			RequestResponsKindBean reqData
			, String seq_shisaku
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//		●調味料の場合
		//       リテラルマスタの該当工程のVALUE1が"1"の工程
		//
		//		●その他調味料以外の場合
		//       リテラルマスタの該当工程のVALUE1が"2"の工程
		//
		//      ※混在している場合は計算できない。
		//
		//+--------------------------------------------+

		douGenryohiKesu = -1;

		try{

			//工程の種類判定
			if (flgKoteGroup == 1){
				//調味液パターン
				douGenryohiKesu = calcuGenryohiKesu_Cyoumi(reqData, seq_shisaku);

			}else if (flgKoteGroup == 2){
				//その他調味液以外バターン
				douGenryohiKesu = calcuGenryohiKesu_Sonota(reqData, seq_shisaku);

			}else{
				//工程の種類が混在しているので、計算できない
				//Skip!

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原料費（円）/ケースの算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 原料費（円）/ケース(調味液以外)の算出
	 * @param reqData		：リクエストデータ
	 * @param listSanpuru	：DBサンプル情報
	 * @param listGenryo	：DB原料情報
	 * @param seq_shisaku	：試作SEQ
	 * @return double		：算出結果
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private double calcuGenryohiKesu_Sonota(

			RequestResponsKindBean reqData
			, String seq_shisaku
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//
		//		●その他調味料以外の場合
		//		　【2009/11/27　課題管理：119　変更】「金額計」÷合計仕上重量（ｇ）×平均充填量（ｇ）÷1000÷(有効歩留（％）÷100)
		//		　「金額計」÷合計仕上重量（ｇ）×平均充填量（ｇ）÷(有効歩留（％）÷100)
		//		　　
		//		　「金額計」
		//		　　「配合:⑤」×「単価:③」÷ (「歩留:④」÷100)の計
		//
		// 【計算諸原　DB取得項目】合計仕上重量(㎏)douGokeShiagari
		// 【計算諸原　引数項目　】平均充填量(ｇ)	douHeikinZyuten
		// 【計算諸原　引数項目　】有効歩留(％)	douYukoBudomari
		//
		//+--------------------------------------------+

		//金額計
		double gokeKingaku = -1;

		Object[] items = null;
		double ret = -1;

		try{

			//計算諸原の収集

			//合計仕上り重量（サンプル4）
			int ix = seachSanpuruIndex(seq_shisaku);

			items = (Object[]) listSanpuru.get(ix);

			douGokeShiagari = toDouble(items[4], -1);

			//金額計の集計
			gokeKingaku = calcuGenryohiKesu_Sonota_Syuke(
					reqData
					, seq_shisaku);

			//諸原のチェック
			if (
					douGokeShiagari > -1
				&&	douHeikinZyuten > -1
				&&	douYukoBudomari > -1
				&&	gokeKingaku > -1

			){

				//計算
				if(douGokeShiagari > 0){
//					ret = gokeKingaku / douGokeShiagari * douHeikinZyuten / 1000
//					/ (douYukoBudomari / 100);
					ret = gokeKingaku / douGokeShiagari * douHeikinZyuten
					/ (douYukoBudomari / 100);

				}else{
					ret = 0;

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原料費（円）/ケース(調味液以外)の算出に失敗しました。");

		}finally{
			//ローカル変数の開放
			items = null;

		}
		return ret;

	}
	/**
	 * 金額計の集計
	 * @param reqData		：リクエストデータ
	 * @param listGenryo	：DB原料情報
	 * @param seq_shisaku	：試作SEQ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private double calcuGenryohiKesu_Sonota_Syuke(

			RequestResponsKindBean reqData
			, String seq_shisaku
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//
		//		　「金額計」
		//		　　配合×単価÷(歩留÷100)の計
		//
		// 【計算諸原　DB取得項目】配合(㎏)		douHaigo
		// 【計算諸原　引数項目　】単価(円/㎏)	douTanka_gen
		// 【計算諸原　引数項目　】歩留(％)		douBudomari_Gen
		//
		//+--------------------------------------------+

		Object[] items = null;

		//金額計
		double gokeKingaku = 0;

		try{

			//原料DB情報特定

			for (int i =0; i < reqData.getCntRow("genryo"); i++){

				int ix = seachGenryoIndex(
						seq_shisaku
						, reqData.getFieldVale("genryo", i, "cd_kotei")
						, reqData.getFieldVale("genryo", i, "seq_kotei")
						);

				items = (Object[]) listGenryo.get(ix);

				//集計

				//配合量
				douHaigo = toDouble(items[8], -1);
				//単価
				douTanka_gen = toDouble(reqData.getFieldVale("genryo", i, "tanka"), -1);
				//歩留り
				douBudomari_Gen = toDouble(reqData.getFieldVale("genryo", i, "budomari"), -1);

				//金額計
				if ( douHaigo > 0 && douTanka_gen > 0 && douBudomari_Gen > 0 ){
					gokeKingaku += douHaigo * douTanka_gen / (douBudomari_Gen / 100);

				}else{

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原料費（円）/ケース(調味液パターン)、合計単価，合計重量の算出に失敗しました。");

		}finally{
			//ローカル変数の開放
			items = null;

		}
		return gokeKingaku;

	}
	/**
	 * 原料費（円）/ケース(調味液パターン)の算出
	 * @param reqData		：リクエストデータ
	 * @param listGenryo	：DB原料情報
	 * @param seq_shisaku	：試作SEQ
	 * @return double		：算出結果
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private double calcuGenryohiKesu_Cyoumi(

			RequestResponsKindBean reqData
			, String seq_shisaku
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//
		//		●調味料の場合
		//		　(　(「水相合計単価」÷「水相合計重量」×充填量水相（ｇ）÷1000）
		//		　＋（「油相合計単価」÷「油相合計重量」×充填量油相（ｇ）÷1000) )
		//		　×平均充填量（ｇ）÷レベル量(g)÷(有効歩留÷100)×入数
		//
		//		　※水相とは、殺菌調味液工程と水相工程を指す
		//       リテラルマスタの該当工程のVALUE2が"1"と"2"の工程
		//		　　水相合計単価
		//		　　　配合×単価÷(歩留÷100)の計
		//		　　水相合計重量
		//		　　　配合の計
		//
		//		　※油相とは、油相工程を指す
		//       リテラルマスタの該当工程のVALUE2が"3"の工程
		//		　　油相合計単価
		//		　　　配合×単価÷(歩留÷100)の計
		//		　　油相合計重量
		//		　　　配合の計
		//
		// 【計算諸原　DB取得項目】充填量水相(ｇ)	douZyutenSui
		// 【計算諸原　DB取得項目】充填量油相(ｇ)	douZyutenYu
		// 【計算諸原　引数項目　】平均充填量(ｇ)	douHeikinZyuten
		// 【計算諸原　引数項目　】有効歩留(％)	douYukoBudomari
		// 【計算諸原　引数項目　】入数			douIrisu
		// 【計算諸原　計算項目　】レベル量(ｇ)		douLevelRyo
		//
		//+--------------------------------------------+

		double ret = -1;

		try{

			//計算諸原の収集

			//入数
			douIrisu = toDouble(reqData.getFieldVale("kihon", "rec", "irisu"), -1);

			//水相合計単価・水相合計重量・油相合計単価・油相合計重量の集計
			calcuGenryohiKesu_Cyoumi_Syuke(
					reqData
					, seq_shisaku);

			//諸原のチェック
			if (
					douZyutenSui > -1	//？10.22	ＯＫ
				&&	douZyutenYu > -1	//？12.3		ＯＫ
				&&	douHeikinZyuten > -1//？52.825	ＯＫ
				&&	douLevelRyo > -1	//？1000.0	ＯＫ
				&&	douYukoBudomari > -1//？83.25	ＯＫ
				&&	douIrisu > -1		//？10.0		ＯＫ
				&&	gokeTanka_sui > -1	//？10923.546660882188	ＯＫ
				&&	gokeJyuryo_sui > -1	//？107.25	ＯＫ
				&&	gokeTanka_yu > -1	//？461.12	ＯＫ
				&&	gokeJyuryo_yu > -1	//？1.5399999999999998	ＯＫ

			){

				//計算
				double a = 0;
				double b = 0;
				if (gokeTanka_sui > 0
				&& gokeJyuryo_sui > 0
				&& douZyutenSui > 0){

					a = (gokeTanka_sui / gokeJyuryo_sui * douZyutenSui / 1000);
					//？1.040919784374974
				}
				if (gokeTanka_yu > 0
				&& gokeJyuryo_yu > 0
				&& douZyutenYu > 0){

					b = (gokeTanka_yu / gokeJyuryo_yu * douZyutenYu / 1000);
					//？	3.682971428571429
				}
				ret = (a + b) * douHeikinZyuten / douLevelRyo / (douYukoBudomari / 100) * douIrisu;
//				ret = ((gokeTanka_sui / gokeJyuryo_sui * douZyutenSui / 1000)
//				+ (gokeTanka_yu / gokeJyuryo_yu * douZyutenYu / 1000))
//				* douHeikinZyuten / douLevelRyo / (douYukoBudomari / 100) * douIrisu;
					//？2.997472111998723
			}
//			else{
//				ret = 0;
//			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原料費（円）/ケース(調味液パターン)の算出に失敗しました。");

		}finally{

		}
		return ret;

	}
	/**
	 * 合計単価，合計重量を集計する。
	 * @param reqData			：リクエストデータ
	 * @param listGenryo		：DB原料情報
	 * @param seq_shisaku		：試作SEQ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenryohiKesu_Cyoumi_Syuke(

			RequestResponsKindBean reqData
			, String seq_shisaku
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//
		//		　※水相とは、殺菌調味液工程と水相工程を指す
		//       リテラルマスタの該当工程のVALUE2が"1"と"2"の工程
		//		　　水相合計単価
		//		　　　配合×単価÷(歩留÷100)の計
		//		　　水相合計重量
		//		　　　配合の計
		//
		//		　※油相とは、油相工程を指す
		//       リテラルマスタの該当工程のVALUE2が"3"の工程
		//		　　油相合計単価
		//		　　　配合×単価÷(歩留÷100)の計
		//		　　油相合計重量
		//		　　　配合の計
		//
		// 【計算諸原　DB取得項目】配合(㎏)		douHaigo
		// 【計算諸原　引数項目　】単価(円/㎏)	douTanka_gen
		// 【計算諸原　引数項目　】歩留(％)		douBudomari_Gen
		//
		//+--------------------------------------------+

		Object[] items = null;

		//水相合計単価
		gokeTanka_sui = 0;
		//水相合計重量
		gokeJyuryo_sui = 0;
		//油相合計単価
		gokeTanka_yu = 0;
		//油相合計重量
		gokeJyuryo_yu = 0;

		try{

			//原料DB情報特定

			for (int i =0; i < reqData.getCntRow("genryo"); i++){

				int ix = seachGenryoIndex(
						seq_shisaku
						, reqData.getFieldVale("genryo", i, "cd_kotei")
						, reqData.getFieldVale("genryo", i, "seq_kotei")
						);

				items = (Object[]) listGenryo.get(ix);

				//集計

				//配合量
				douHaigo = toDouble(items[8], -1);
				//単価
				douTanka_gen = toDouble(reqData.getFieldVale("genryo", i, "tanka"), -1);
				//歩留り
				douBudomari_Gen = toDouble(reqData.getFieldVale("genryo", i, "budomari"), -1);

				if (toInteger(items[11]) == 1 || toInteger(items[11]) == 2){
					//水相集計


					if (douHaigo > 0){

						//合計単価
						if (douBudomari_Gen > 0){
							gokeTanka_sui += douHaigo * douTanka_gen / (douBudomari_Gen / 100);

						}else{

						}
						//合計重量
						gokeJyuryo_sui += douHaigo;

					}

				}else{
					//油相集計

					if (douHaigo > 0){


						//合計単価
						if (douBudomari_Gen > 0){
							gokeTanka_yu += douHaigo * douTanka_gen / (douBudomari_Gen / 100);

						}else{

						}
						//合計重量
						gokeJyuryo_yu += douHaigo;

					}

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原料費（円）/ケース(調味液パターン)、合計単価，合計重量の算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 比重加算量（ｇ）の計算
	 * @param yory	：容量
	 * @param irisu	：入数
	 * @param irisu	：比重
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuHizyukasanryo(

			double yory
			, double irisu
			, double hizyu
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+ 計算式
		//+
		//+ 2009/12/?? KG似合わせる　容量×入数×比重
		//+ 2009/12/17　算出方法をｼｻｸｲｯｸに合わせる　（容量×入数÷1000）×比重
		//+ 平均充填量×比重
		//+
		//+ 【計算諸原　DB取得項目】容量
		//+ 【計算諸原　引数項目　】入り数
		//+ 【計算諸原　DB取得項目】比重
		//+--------------------------------------------+
		douYouryo = yory;
		douIrisu = irisu;
		douHizyu = hizyu;

		//【　　　　　計算項目　】比重加算量(ｇ)
		douHizykasanRyo = -1;

		try{
			if (douHeikinZyuten > -1
			&&	douHizyu > -1
					){
				//計算
//				douHizykasanRyo = (douYouryo * douIrisu ) * douHizyu;
//				douHizykasanRyo = (douYouryo * douIrisu / 1000 ) * douHizyu;
				douHizykasanRyo = douHeikinZyuten * douHizyu;


			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　レベル量の算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * レベル量(ｇ)の計算
	 * @param yory	：容量
	 * @param irisu	：入数
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuReberuryo(

			double yory
			, double irisu
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+ 計算式
		//+
		//+ 【2009/11/27　課題管理：119　変更】容量×入数
		//+ 容量×入数÷1000
		//+
		//+ 【計算諸原　DB取得項目】容量
		//+ 【計算諸原　引数項目　】入り数
		//+--------------------------------------------+
		douYouryo = yory;
		douIrisu = irisu;

		//【計算諸原　計算項目　】レベル量(ｇ)
		douLevelRyo = -1;

		try{
			if (douYouryo > -1
			&&	douIrisu > -1
			){
//				douLevelRyo = douYouryo * douIrisu;
				douLevelRyo = douYouryo * douIrisu / 1000;

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　レベル量の算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 資材金額を計算する
	 * @param reqData		：リクエストデータ
	 * @param resData		：計算結果（レスポンス）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuKingakuShizai(

			RequestResponsKindBean reqData
			, RequestResponsKindBean resData
			, List<?> listShisaku
			, List<?> listGenryo

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+ 計算式
		//+
		//+ 単価÷（歩留（％）÷100）×使用量/ｹｰｽ
		//+
		//+ 【計算諸原　引数項目　】単価(円)		資材
		//+ 【計算諸原　引数項目　】歩留(％)		資材
		//+ 【計算諸原　引数項目　】使用量/ｹｰｽ 	資材
		//+--------------------------------------------+
		douTanka_Shizai = -1;
		douBudomari_Shizai = -1;
		douShiyouryo = -1;

		double douResult = 0;
		int i = -1;
		//金額合計
		douKingakuKei_Shizai = 0;
		String kigo = "";
		int keta = -1;

		try{
			reqData.getCntRow("shizai");

		}catch(Exception e){
			return;
		}

		try{

			for (i = 0; i < reqData.getCntRow("shizai"); i++){

				//工場記号/資材コード桁数を検索
				Object[] items = seachKojyoKigo(
						reqData.getFieldVale("shizai", i, "cd_kaisya")
						, reqData.getFieldVale("shizai", i, "cd_kojyo")
						);

				kigo = "";
				keta = -1;

				if(items == null){

				}else{
					//工場記号
					kigo = toString(items[0], "");
					//原料/資材コード桁数
					keta = toInteger(items[1], -1);

				}
				if (keta > -1){

				}else{
					keta = 6;
				}

				//計算諸原の収集
				//使用量/ｹｰｽ
				 douShiyouryo = toDouble(reqData.getFieldVale("shizai", i, "shiyouryo"), -1);
				//単価
				 douTanka_Shizai = toDouble(reqData.getFieldVale("shizai", i, "tanka"), -1);
				//歩留り
				 douBudomari_Shizai = toDouble(reqData.getFieldVale("shizai", i, "budomari"), -1);

				 douResult = -1;

				if(douShiyouryo > -1
				&& douTanka_Shizai > -1
				&& douBudomari_Shizai > -1
				){
					//計算
					if(douBudomari_Shizai > 0){
						douResult = douTanka_Shizai / (douBudomari_Shizai / 100) * douShiyouryo;

					}else{
						douResult = 0;

					}

					//金額合計　加算
					douKingakuKei_Shizai += douResult;

				}

				//レコード処理結果RESULT追加
				resData.addFieldVale("shizai", "rec" + toString(i), "flg_return", "true");
				resData.addFieldVale("shizai", "rec" + toString(i), "msg_error", "");
				resData.addFieldVale("shizai", "rec" + toString(i), "nm_class", "");
				resData.addFieldVale("shizai", "rec" + toString(i), "no_errmsg", "");
				resData.addFieldVale("shizai", "rec" + toString(i), "cd_error", "");
				resData.addFieldVale("shizai", "rec" + toString(i), "msg_system", "");
				// ----------------------------------------------------------------------+
				// 資材SEQ
				// ----------------------------------------------------------------------+
				resData.addFieldVale("shizai", "rec" + toString(i), "seq_shizai"
						, reqData.getFieldVale("shizai", i, "seq_shizai"));
				// ----------------------------------------------------------------------+
				// 会社CD
				// ----------------------------------------------------------------------+
				resData.addFieldVale("shizai", "rec" + toString(i), "cd_kaisya"
						, reqData.getFieldVale("shizai", i, "cd_kaisya"));
				// ----------------------------------------------------------------------+
				// 工場CD
				// ----------------------------------------------------------------------+
				resData.addFieldVale("shizai", "rec" + toString(i), "cd_kojyo"
						, reqData.getFieldVale("shizai", i, "cd_kojyo"));
				// ----------------------------------------------------------------------+
				// 工場記号
				// ----------------------------------------------------------------------+
				resData.addFieldVale("shizai", "rec" + toString(i), "kigo_kojyo"
						, kigo);
				// ----------------------------------------------------------------------+
				// 資材CD
				// ----------------------------------------------------------------------+
				if(toDouble(reqData.getFieldVale("shizai", i, "cd_shizai"), -1) > -1){
					resData.addFieldVale(
							"shizai"
							, "rec" + toString(i)
							, "cd_shizai"
							, getRight("0000000000" + reqData.getFieldVale("shizai", i, "cd_shizai"), keta)
							);

				}else{
					resData.addFieldVale("shizai", "rec" + toString(i), "cd_shizai"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 資材名
				// ----------------------------------------------------------------------+
				resData.addFieldVale("shizai", "rec" + toString(i), "nm_shizai"
						, reqData.getFieldVale("shizai", i, "nm_shizai"));
				// ----------------------------------------------------------------------+
				// 単価
				// ----------------------------------------------------------------------+
				if (douTanka_Shizai > -1){
					resData.addFieldVale(
							"shizai"
							, "rec" + toString(i)
							, "tanka"
							, toString(douTanka_Shizai, 2, 2, true, ""));

				}else{
					resData.addFieldVale("shizai", "rec" + toString(i), "tanka"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 歩留（％）
				// ----------------------------------------------------------------------+
				if (douBudomari_Shizai > -1){
					resData.addFieldVale(
							"shizai"
							, "rec" + toString(i)
							, "budomari"
							, toString(douBudomari_Shizai, 2, 2, true, ""));

				}else{
					resData.addFieldVale("shizai", "rec" + toString(i), "budomari"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 使用量/ｹｰｽ
				// ----------------------------------------------------------------------+
				if (douShiyouryo > -1){
					resData.addFieldVale(
							"shizai"
							, "rec" + toString(i)
							, "shiyouryo"
							, toString(douShiyouryo, 6, 2, true, ""));

				}else{
					resData.addFieldVale("shizai", "rec" + toString(i), "shiyouryo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 金額
				// ----------------------------------------------------------------------+
				if (douResult > -1){
					resData.addFieldVale(
							"shizai"
							, "rec" + toString(i)
							, "kei_kingaku"
							, toString(douResult, 2, 2, true, ""));

				}else{
					resData.addFieldVale("shizai", "rec" + toString(i), "kei_kingaku"
							, "");

				}

				// ----------------------------------------------------------------------+
				// 登録者ID
				// ----------------------------------------------------------------------+
				//リクエストに試算日が存在するかの確認
				int index = reqData.getItemNo("shizai", i, "id_toroku");

				if( index > -1 ){

					resData.addFieldVale("shizai", "rec" + toString(i), "id_toroku"
							, reqData.getFieldVale("shizai", i, "id_toroku"));

				}
				else{


				}

				// ----------------------------------------------------------------------+
				// 登録日付
				// ----------------------------------------------------------------------+
				//リクエストに試算日が存在するかの確認
				int index2 = reqData.getItemNo("shizai", i, "dt_toroku");

				if( index2 > -1 ){

					resData.addFieldVale("shizai", "rec" + toString(i), "dt_toroku"
							, reqData.getFieldVale("shizai", i, "dt_toroku"));

				}
				else{


				}

//				try{
//					// ----------------------------------------------------------------------+
//					// 登録者ID
//					// ----------------------------------------------------------------------+
//					resData.addFieldVale("shizai", "rec" + toString(i), "id_toroku"
//							, reqData.getFieldVale("shizai", i, "id_toroku"));
//					// ----------------------------------------------------------------------+
//					// 登録日付
//					// ----------------------------------------------------------------------+
//					resData.addFieldVale("shizai", "rec" + toString(i), "dt_toroku"
//							, reqData.getFieldVale("shizai", i, "dt_toroku"));
//				}catch(Exception e){
//
//				}



			}

			// ----------------------------------------------------------------------+
			// 金額合計
			// ----------------------------------------------------------------------+
			if (douKingakuKei_Shizai > -1){
				resData.addFieldVale(
						"kihon"
						, "rec"
						, "goke_shizai"
						, toString(douKingakuKei_Shizai, 2, 2, true, ""));

			}else{
				resData.addFieldVale("kihon", "rec", "goke_shizai"
						, "");

			}
			// ----------------------------------------------------------------------+
			// 資材Max行数
			// ----------------------------------------------------------------------+
			resData.addFieldVale("kihon", "rec", "cnt_shizai"
					, toString(
							i
							+ toInteger(ConstManager.getConstValue(Category.設定, "GENK_SHIZAI_LIST_ADD_CNT")))
							);


		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　資材金額の算出に失敗しました。");

		}finally{

		}

	}
	/**
	 * 原料金額を計算する
	 * @param reqData		：リクエストデータ
	 * @param resData		：計算結果（レスポンス）
	 * @param listShisaku	：検索結果（試作情報）
	 * @param listGenryo	：検索結果（原料情報）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuKingakuGenryo(

			RequestResponsKindBean reqData
			, RequestResponsKindBean resData

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+ 計算式
		//+
		//+ 単価÷（歩留÷100）×配合
		//+
		//+ 【計算諸原　引数項目　】単価(円/㎏)　原料
		//+ 【計算諸原　引数項目　】歩留(％)　　原料
		//+ 【計算諸原　DB取得項目】配合(㎏)
		//+--------------------------------------------+
		douHaigo = -1;
		douTanka_gen = -1;
		douBudomari_Gen = -1;

		int keta_shosu = 0;
		double douResult = 0;
		Object[] items = null;

		//工程CD比較バッファ
		int intCd_kotei = -1;
		//recの添え字
		int cntRec = 0;
		//工程グループ（1：調味液パターン　2：その他調味液以外バターン　3：計算不能）
		flgKoteGroup = -1;

		//配合工程計
		ArrayList<Double> lstGoukeJyuryo_kote = null;
		//配合合計
		double GoukeJyuryo_total = 0;

		int ii = 0;
		int i = 0;
		int iy = 0;

		try{

			//準備　配合小数指定　退避
			items = (Object[]) listShisaku.get(0);
			keta_shosu = toInteger(items[4], 0);

			for (ii = 0; ii < reqData.getCntRow("keisan"); ii++){
				//サンプル毎の処理

				//レスポンス基本（kihon）生成
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "flg_return", "true");
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "msg_error", "");
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "nm_class", "");
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "no_errmsg", "");
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "cd_error", "");
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "msg_system", "");

				//レスポンスﾍｯﾀﾞｰ（heder）生成
				//DBサンプル情報取得
				iy = seachSanpuruIndex(reqData.getFieldVale("keisan", ii, "seq_shisaku"));
				items = (Object[]) listSanpuru.get(iy);

				resData.addFieldVale("shisaku" + toString(ii)
						, "heder", "seq_shisaku", toString(items[3],"　"));
				resData.addFieldVale("shisaku" + toString(ii)
						, "heder", "shisakuDate", toString(items[8],"　"));
				resData.addFieldVale("shisaku" + toString(ii)
						, "heder", "nm_sample", toString(items[9],"　"));

				//サンプル毎の初期化
				GoukeJyuryo_total = 0;
				cntRec = -1;
				intCd_kotei = -1;
				removeList(lstGoukeJyuryo_kote);
				lstGoukeJyuryo_kote = new ArrayList<Double>();

				for (i =0; i < reqData.getCntRow("genryo"); i++){
					//原料毎(haigo＋行SEQ)の処理

					int ix = seachGenryoIndex(
							  reqData.getFieldVale("keisan", ii, "seq_shisaku")
							, reqData.getFieldVale("genryo", i, "cd_kotei")
							, reqData.getFieldVale("genryo", i, "seq_kotei")
							);

					items = (Object[]) listGenryo.get(ix);

					//工程グループの特定（1：調味液パターン　2：その他調味液以外バターン　3：計算不能）
					if (flgKoteGroup == -1){
						flgKoteGroup = toInteger(items[10]);

					}else if(flgKoteGroup == toInteger(items[10])){

					}else{
						flgKoteGroup = 3;

					}

					//工程表示行(空白行)の追加

					if (intCd_kotei == toInteger(items[9])){

					}else{
						cntRec += 1;
						//配合
						resData.addFieldVale("shisaku" + toString(ii), "haigo" + toString(cntRec)
								, "haigo", "");
						//金額
						resData.addFieldVale("shisaku" + toString(ii), "haigo" + toString(cntRec)
								, "kingaku", "");
						//工程CDの退避
						intCd_kotei = toInteger(items[9]);

						//工程計の初期化
						lstGoukeJyuryo_kote.add(toDouble(0));

						//変更連絡　工程（空白）　2009/11/12追加
						if(ii == 0){
							resData.addFieldVale("henkou", "rec" + toString(cntRec)
									, "henkourenraku", "");

						}

					}

					//配合量
					douHaigo = toDouble(items[8], -1);
					//単価
					douTanka_gen = toDouble(reqData.getFieldVale("genryo", i, "tanka"), -1);
					//歩留り
					douBudomari_Gen = toDouble(reqData.getFieldVale("genryo", i, "budomari"), -1);

					douResult = -1;

					if(douHaigo > -1
					&& douTanka_gen > -1
					&& douBudomari_Gen > -1
					){
						//計算（金額）
						if(douBudomari_Gen > 0){
							douResult = douTanka_gen / (douBudomari_Gen / 100) * douHaigo;

						}else{
							douResult = 0;

						}
					}


					//配合　工程計
					lstGoukeJyuryo_kote.set(lstGoukeJyuryo_kote.size()-1,
					lstGoukeJyuryo_kote.get(lstGoukeJyuryo_kote.size()-1) + toDouble(items[8], 0));
					//配合　合計
					GoukeJyuryo_total += toDouble(items[8], 0);

					//計算結果データ（レスポンス）生成
					cntRec += 1;
					// ----------------------------------------------------------------------+
					// 配合
					// ----------------------------------------------------------------------+
					if (douHaigo > -1){
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "haigo"
								, toString(douHaigo, keta_shosu, 2, true, ""));

					}else{
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "haigo"
								, "");

					}
					// ----------------------------------------------------------------------+
					// 金額
					// ----------------------------------------------------------------------+
					if (douResult > -1){
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "kingaku"
								, toString(douResult, 2, 2, true, ""));

					}else{
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "kingaku"
								, "");

					}

					//【シサクイックH24年度対応】No46 2012/04/20 ADD Start
					//原料工程
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "sort"
							, toString(items[7]));
					//【シサクイックH24年度対応】No46 2012/04/20 ADD End

					//変更連絡　2009/11/12追加
					if(ii == 0){
						//マスタの値と比較する。
						if (douTanka_gen != toDouble(items[12], -1)
							||	douBudomari_Gen != toDouble(items[13], -1)){
							resData.addFieldVale("henkou", "rec" + toString(cntRec)
									, "henkourenraku", "◎");

						}else{
							resData.addFieldVale("henkou", "rec" + toString(cntRec)
									, "henkourenraku", "");

						}

					}


				}

				//工程計行追加

				for (int ix = 0 ; ix < lstGoukeJyuryo_kote.size() ; ix++){

					cntRec += 1;

					// ----------------------------------------------------------------------+
					// 工程計行　配合
					// ----------------------------------------------------------------------+
					if (lstGoukeJyuryo_kote.get(ix) > -1){
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "haigo"
								, toString(lstGoukeJyuryo_kote.get(ix), keta_shosu, 2, true, ""));

					}else{
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "haigo"
								, "");

					}
					// ----------------------------------------------------------------------+
					// 工程計行　金額
					// ----------------------------------------------------------------------+
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "kingaku"
							, "");

				}

				//合計行追加

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// 合計行　配合
				// ----------------------------------------------------------------------+
				if (GoukeJyuryo_total > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(GoukeJyuryo_total, keta_shosu, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 合計行　金額
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");



				//DBサンプル情報取得
				iy = seachSanpuruIndex(reqData.getFieldVale("keisan", ii, "seq_shisaku"));
				items = (Object[]) listSanpuru.get(iy);

				//合計仕上り重量

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// 合計仕上り重量　配合
				// ----------------------------------------------------------------------+
				if (toDouble(items[4], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[4], -1), 4, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 合計仕上り重量　金額
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");

				//総酸

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// 総酸　配合
				// ----------------------------------------------------------------------+
				if (toDouble(items[12], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[12], -1), 2, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 総酸　金額
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");

				//食塩

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// 食塩　配合
				// ----------------------------------------------------------------------+
				if (toDouble(items[13], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[13], -1), 2, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 食塩　金額
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");

				//水相中酸度

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// 水相中酸度　配合
				// ----------------------------------------------------------------------+
				if (toDouble(items[14], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[14], -1), 2, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 水相中酸度　金額
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");

				//水相中食塩

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// 水相中食塩　配合
				// ----------------------------------------------------------------------+
				if (toDouble(items[15], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[15], -1), 2, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 水相中食塩　金額
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");

				//水相中酢酸

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// 水相中酢酸　配合
				// ----------------------------------------------------------------------+
				if (toDouble(items[16], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[16], -1), 2, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// 水相中酢酸　金額
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");


			}
			//レスポンス基本の追加
			// ----------------------------------------------------------------------+
			//　原料数（行）
			// ----------------------------------------------------------------------+
			resData.addFieldVale("kihon", "rec", "cnt_genryo"
					, toString(cntRec + 1));

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　原料金額の算出に失敗しました。");

		}finally{
			//ローカル変数の開放
			items = null;
			removeList(lstGoukeJyuryo_kote);

		}

	}
	/**
	 * サンプル情報（listSanpuru）より、条件に合致するインデックスを検出する
	 * @param seq_shisaku	：試作SEQ
	 * @param listGenryo	：検索結果（サンプル情報）
	 * @return int	：インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachSanpuruIndex(

			  String seq_shisaku
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;

		try{

			for (int i = 0; i < listSanpuru.size(); i++){

				Object[] items = (Object[]) listSanpuru.get(i);

				//配合をマッチング
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(seq_shisaku) == toInteger(items[3])
						){
					//該当した場合、終了
					ret = i;
					break;

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * 原料情報（listGenryo）より、条件に合致するインデックスを検出する
	 * @param seq_shisaku	：試作SEQ
	 * @param cd_kotei		：工程CD
	 * @param seq_kotei		：工程SEQ
	 * @param listGenryo	：検索結果（原料情報）
	 * @return int	：インデックス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachGenryoIndex(

			  String seq_shisaku
			, String cd_kotei
			, String seq_kotei
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;

		try{

			for (int i = 0; i < listGenryo.size(); i++){

				Object[] items = (Object[]) listGenryo.get(i);

				//配合をマッチング
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(seq_shisaku) == toInteger(items[3])
					&&	toInteger(cd_kotei) == toInteger(items[4])
					&&	toInteger(seq_kotei) == toInteger(items[5])
						){
					//該当した場合、終了
					ret = i;
					break;

				}

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * 希望原価単位を取得する
	 * @param seq_shisaku	：単位コード
	 * @return string :希望原価単位
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String seachNmTaniKibogenka(

			    String cdTani
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQLバッファ
		StringBuffer strSQL = new StringBuffer();
		//結果バッファ
		List<?> listResult = null;
		//希望原価単位
		String ret = "";

		try{

			//DBコネクション
			createSearchDB();

			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("     nm_literal ");
			strSQL.append(" ,   cd_literal ");
			strSQL.append(" FROM ");
			strSQL.append("     ma_literal ");
			strSQL.append(" WHERE ");
			strSQL.append("     cd_category = 'K_tani_genka' ");
			strSQL.append(" AND cd_literal  = '" + toString(cdTani) + "' ");
			//DB検索
			listResult = this.searchDB.dbSearch(strSQL.toString());

			try{
				//希望原価単位
				if ( listResult.size() >= 0 ) {

					for (int i = 0; i < listResult.size(); i++) {

						Object[] items = (Object[]) listResult.get(i);

						ret = toString(items[0]);

					}

				}

			}catch(Exception e){

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "希望原価、単位の取得 に失敗しました。\nSQL:"
					+ strSQL.toString());

		}finally{
			//DBコネクション開放
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}
			//ローカル変数の開放
			strSQL = null;
			removeList(listResult);

		}
		return ret;

	}
	/**
	 * 作業工程を取得する
	 * @param reqData	：リクエストデータ
	 * @return String	：検索結果
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String seachSeizoKote(

		    RequestResponsKindBean reqData
		)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQLバッファ
		StringBuffer strSQL = new StringBuffer();
		//結果バッファ
		List<?> listResult = null;
		String strWhere = "null";
		//作業工程
		String ret = "";


		try{

			//DBコネクション
			createSearchDB();

			//条件収集
			for (int i = 0 ; i < reqData.getCntRow("keisan"); i++){
				strWhere += "," + toString(reqData.getFieldVale("keisan", i, "seq_shisaku"));
			}

			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  T133.chuijiko ");
			strSQL.append(" ,T131.cd_shain ");
			strSQL.append(" FROM ");
			strSQL.append("    (SELECT ");
			strSQL.append("      cd_shain ");
			strSQL.append("     ,nen ");
			strSQL.append("     ,no_oi ");
			strSQL.append("     ,MAX(no_chui) AS no_chui ");
			strSQL.append("     FROM ");
			strSQL.append("     tr_shisaku ");
			strSQL.append("     WHERE ");
			strSQL.append("         cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append("     AND nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append("     AND no_oi    = " + toString(cd_shisaku_oi) + " ");
			strSQL.append("     AND seq_shisaku IN (" + strWhere + ") ");
			strSQL.append("     GROUP BY ");
			strSQL.append("      cd_shain ");
			strSQL.append("     ,nen ");
			strSQL.append("     ,no_oi ");
			strSQL.append("     ) AS T131 ");
			strSQL.append(" LEFT JOIN tr_cyuui AS T133 ");
			strSQL.append(" ON  T131.cd_shain = T133.cd_shain ");
			strSQL.append(" AND T131.nen      = T133.nen ");
			strSQL.append(" AND T131.no_oi    = T133.no_oi ");
			strSQL.append(" AND T131.no_chui  = T133.no_chui ");
			//DB検索
			listResult = this.searchDB.dbSearch(strSQL.toString());

			try{
				//作業工程取得
				if ( listResult.size() >= 0 ) {

					for (int i = 0; i < listResult.size(); i++) {

						Object[] items = (Object[])listResult.get(i);

						ret = toString(items[0]);

					}

				}

			}catch(Exception e){

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "作業工程の取得 に失敗しました。\nSQL:"
					+ strSQL.toString());

		}finally{
			//DBコネクション開放
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}
			//ローカル変数の開放
			strSQL = null;
			removeList(listResult);

		}
		return ret;

	}
	/**
	 * 工場記号を取得する
	 * @param seq_shisaku	：試作SEQ
	 * @param listGenryo	：検索結果（原料情報）
	 * @return String		：工場記号/コード桁数
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private Object[] seachKojyoKigo(

			    String cdKaisya
			  , String cdKojyo
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQLバッファ
		StringBuffer strSQL = new StringBuffer();
		//結果バッファ
		List<?> listResult = null;

		Object[] ret = null;

		try{

			//DBコネクション
			createSearchDB();

			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  M302.nm_literal ");
			strSQL.append(" ,M104.keta_genryo ");
			strSQL.append(" FROM ");
			strSQL.append("           ma_busho   AS M104 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kigo_kojyo'  ");
			strSQL.append(" AND M302.cd_literal = CONVERT(varchar, M104.cd_kaisha) + '-' + CONVERT(varchar, M104.cd_busho) ");
			strSQL.append(" WHERE ");
			strSQL.append("     M104.cd_kaisha =" + toString(cdKaisya,"null") + " ");
			strSQL.append(" AND M104.cd_busho  =" + toString(cdKojyo,"null") + " ");
			//DB検索
			listResult = this.searchDB.dbSearch(strSQL.toString());

			try{
				//工場記号取得
				if ( listResult.size() >= 0 ) {

					for (int i = 0; i < listResult.size(); i++) {

						ret = (Object[]) listResult.get(i);

					}

				}

			}catch(Exception e){

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "工場記号の取得 に失敗しました。\nSQL:"
					+ strSQL.toString());

		}finally{
			//DBコネクション開放
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}
			//ローカル変数の開放
			strSQL = null;
			removeList(listResult);

		}
		return ret;

	}
	/**
	 * DBより取得する計算諸原の検索
	 * @param listShisaku	：検索結果（試作情報）
	 * @param listSanpuru	：検索結果（ｻﾝﾌﾟﾙ情報）
	 * @param listGenryo	：検索結果（原料情報）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void getDBKomoku()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+　memo　--------------------------------------+
		//+ DBより取得する計算諸原
		//+ 試作毎
		//+ 【計算諸原　DB取得項目】容量
		//+ ｻﾝﾌﾟﾙ毎
		//+ 【計算諸原　DB取得項目】合計仕上重量(㎏)
		//+ 【計算諸原　DB取得項目】充填量水相(ｇ)
		//+ 【計算諸原　DB取得項目】充填量油相(ｇ)
		//+ 【計算諸原　DB取得項目】比重
		//+ 原料毎
		//+ 【計算諸原　DB取得項目】配合(㎏)
		//+--------------------------------------------+

		//SQLバッファ
		StringBuffer strSQL = new StringBuffer();

		try{

			//DBコネクション
			createSearchDB();

			//試作情報検索

			//SQL生成
//			strSQL.append(" SELECT ");
//			strSQL.append("  cd_shain ");	//0
//			strSQL.append(" ,nen ");		//1
//			strSQL.append(" ,no_oi ");		//2
//			strSQL.append(" ,yoryo ");		//3
//			strSQL.append(" ,keta_shosu ");	//4
//			strSQL.append(" FROM ");
//			strSQL.append("     tr_shisakuhin ");
//			strSQL.append(" WHERE ");
//			strSQL.append("     cd_shain = " + toString(cd_shisaku_syainID) + " ");
//			strSQL.append(" AND nen      = " + toString(cd_shisaku_nen) + " ");
//			strSQL.append(" AND no_oi    = " + toString(cd_shisaku_oi) + " ");

			//【QP@00342】
			strSQL.append(" SELECT ");
			strSQL.append(" 	 T310.cd_shain AS cd_shain "); 		//0
			strSQL.append(" 	,T310.nen AS nen "); 						//1
			strSQL.append(" 	,T310.no_oi AS no_oi "); 					//2
			strSQL.append(" 	,T310.yoryo AS yoryo "); 				//3
			strSQL.append(" 	,T110.keta_shosu AS keta_shosu "); 	//4
			strSQL.append(" FROM ");
			strSQL.append(" 	tr_shisakuhin AS T110 ");
			strSQL.append(" 	LEFT JOIN tr_shisan_shisakuhin AS T310 ");
			strSQL.append(" 	ON T110.cd_shain = T310.cd_shain ");
			strSQL.append(" 	AND T110.nen = T310.nen ");
			strSQL.append(" 	AND T110.no_oi = T310.no_oi ");
			strSQL.append(" WHERE ");
			strSQL.append("     T310.cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T310.nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T310.no_oi    = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND T310.no_eda    = " + toString(cd_shisaku_eda) + " ");

			//DB検索
			listShisaku = this.searchDB.dbSearch(strSQL.toString());

			//ｻﾝﾌﾟﾙ情報検索

			strSQL = null;
			strSQL = new StringBuffer();

			//【シサクイックH24対応】No43 Start
			//【QP@00342】元版
			if(cd_shisaku_eda == 0){
				//SQL生成
				strSQL.append(" SELECT ");
				strSQL.append("  T131.cd_shain ");			//0
				strSQL.append(" ,T131.nen ");				//1
				strSQL.append(" ,T131.no_oi ");				//2
				strSQL.append(" ,T131.seq_shisaku ");		//3
				strSQL.append(" ,T131.juryo_shiagari_g ");	//4
				strSQL.append(" ,T141.zyusui ");			//5
				strSQL.append(" ,T141.zyuabura ");			//6
				strSQL.append(" ,T141.hiju ");				//7

				//課題管理台帳　No116　：　試作日付→登録日に変更　TT.Nishigawa START -----------------------------------
				//strSQL.append(" ,CONVERT(VARCHAR,T131.dt_shisaku,111) AS A ");	//8
				strSQL.append(" ,CONVERT(VARCHAR,T331.dt_toroku,111) AS A ");	//8
				//課題管理台帳　No116　：　試作日付→登録日に変更　TT.Nishigawa END     -----------------------------------

				strSQL.append(" ,T131.nm_sample ");			//9
				strSQL.append(" ,CONVERT(VARCHAR,T331.dt_shisan,111) AS B ");	//10
				strSQL.append(" ,T141.gokei ");				//11
				strSQL.append(" ,T131.ritu_sousan ");		//12
				strSQL.append(" ,T131.ritu_shokuen ");		//13
				strSQL.append(" ,T131.sando_suiso ");		//14
				strSQL.append(" ,T131.shokuen_suiso ");		//15
				strSQL.append(" ,T131.sakusan_suiso ");		//16
				strSQL.append(" FROM ");
				strSQL.append("           tr_shisaku AS T131 ");
				strSQL.append(" LEFT JOIN tr_genryo AS T141 ");
				strSQL.append(" ON  T131.cd_shain    = T141.cd_shain ");
				strSQL.append(" AND T131.nen         = T141.nen ");
				strSQL.append(" AND T131.no_oi       = T141.no_oi ");
				strSQL.append(" AND T131.seq_shisaku = T141.seq_shisaku ");
				strSQL.append(" LEFT JOIN tr_shisan_shisaku AS T331 ");
				strSQL.append(" ON  T131.cd_shain    = T331.cd_shain ");
				strSQL.append(" AND T131.nen         = T331.nen ");
				strSQL.append(" AND T131.no_oi       = T331.no_oi ");
				strSQL.append(" AND T131.seq_shisaku = T331.seq_shisaku ");

	//			strSQL.append(" WHERE ");
	//			strSQL.append("     T131.cd_shain = " + toString(cd_shisaku_syainID) + " ");
	//			strSQL.append(" AND T131.nen      = " + toString(cd_shisaku_nen) + " ");
	//			strSQL.append(" AND T131.no_oi    = " + toString(cd_shisaku_oi) + " ");
				//【QP@00342】
				strSQL.append(" WHERE ");
				strSQL.append("     T331.cd_shain = " + toString(cd_shisaku_syainID) + " ");
				strSQL.append(" AND T331.nen      = " + toString(cd_shisaku_nen) + " ");
				strSQL.append(" AND T331.no_oi    = " + toString(cd_shisaku_oi) + " ");
				strSQL.append(" AND T331.no_eda    = " + toString(cd_shisaku_eda) + " ");
			}
			//枝版
			else{
				//SQL生成
				strSQL.append(" SELECT ");
				strSQL.append("  T131.cd_shain ");			//0
				strSQL.append(" ,T131.nen ");				//1
				strSQL.append(" ,T131.no_oi ");				//2
				strSQL.append(" ,T131.seq_shisaku ");		//3
				strSQL.append(" ,T131.juryo_shiagari_g ");	//4
				strSQL.append(" ,T141.zyusui ");			//5
				strSQL.append(" ,T141.zyuabura ");			//6
				strSQL.append(" ,T141.hiju ");				//7
				strSQL.append(" ,CONVERT(VARCHAR,T_moto.dt_toroku,111) AS A ");	//8
				strSQL.append(" ,T131.nm_sample ");			//9
				strSQL.append(" ,CONVERT(VARCHAR,T331.dt_shisan,111) AS B ");	//10
				strSQL.append(" ,T141.gokei ");				//11
				strSQL.append(" ,T131.ritu_sousan ");		//12
				strSQL.append(" ,T131.ritu_shokuen ");		//13
				strSQL.append(" ,T131.sando_suiso ");		//14
				strSQL.append(" ,T131.shokuen_suiso ");		//15
				strSQL.append(" ,T131.sakusan_suiso ");		//16
				strSQL.append(" FROM ");
				strSQL.append("           tr_shisaku AS T131 ");
				strSQL.append(" LEFT JOIN tr_genryo AS T141 ");
				strSQL.append(" ON  T131.cd_shain    = T141.cd_shain ");
				strSQL.append(" AND T131.nen         = T141.nen ");
				strSQL.append(" AND T131.no_oi       = T141.no_oi ");
				strSQL.append(" AND T131.seq_shisaku = T141.seq_shisaku ");
				strSQL.append(" LEFT JOIN tr_shisan_shisaku AS T331 ");
				strSQL.append(" ON  T131.cd_shain    = T331.cd_shain ");
				strSQL.append(" AND T131.nen         = T331.nen ");
				strSQL.append(" AND T131.no_oi       = T331.no_oi ");
				strSQL.append(" AND T131.seq_shisaku = T331.seq_shisaku ");
				strSQL.append(" LEFT join ( select ");
				strSQL.append(" 	seq_shisaku,");
				strSQL.append(" 	dt_toroku ");
				strSQL.append(" 	FROM ");
				strSQL.append(" 	tr_shisan_shisaku");
				strSQL.append(" 	WHERE ");
				strSQL.append(" 	cd_shain = " + toString(cd_shisaku_syainID) + " ");
				strSQL.append(" 	AND nen = " + toString(cd_shisaku_nen) + " ");
				strSQL.append(" 	AND no_oi = " + toString(cd_shisaku_oi) + " ");
				strSQL.append(" 	AND no_eda = 0 ");
				strSQL.append(" ) AS T_moto");
				strSQL.append(" ON T131.seq_shisaku = T_moto.seq_shisaku  ");
				strSQL.append(" WHERE ");
				strSQL.append("     T331.cd_shain = " + toString(cd_shisaku_syainID) + " ");
				strSQL.append(" AND T331.nen      = " + toString(cd_shisaku_nen) + " ");
				strSQL.append(" AND T331.no_oi    = " + toString(cd_shisaku_oi) + " ");
				strSQL.append(" AND T331.no_eda    = " + toString(cd_shisaku_eda) + " ");
			}
			//【シサクイックH24対応】No43 End


			//DB検索
			listSanpuru = this.searchDB.dbSearch(strSQL.toString());

			//原料情報

			strSQL = null;
			strSQL = new StringBuffer();

			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  T120.cd_shain ");		//0
			strSQL.append(" ,T120.nen ");			//1
			strSQL.append(" ,T120.no_oi ");			//2
			strSQL.append(" ,T132.seq_shisaku ");	//3
			strSQL.append(" ,T120.cd_kotei ");		//4
			strSQL.append(" ,T120.seq_kotei ");		//5
			strSQL.append(" ,T120.sort_kotei ");	//6
			strSQL.append(" ,T120.sort_genryo ");	//7
			strSQL.append(" ,T132.quantity ");		//8
			strSQL.append(" ,T120.cd_kotei ");		//9
			strSQL.append(" ,M302.value1 ");		//10
			strSQL.append(" ,M302.value2 ");		//11
			strSQL.append(" ,T320.tanka_ma ");		//12
			strSQL.append(" ,T320.budomar_ma ");	//13
			strSQL.append(" FROM ");
			strSQL.append("           tr_haigo AS T120 ");
			strSQL.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			strSQL.append(" ON  T120.cd_shain  = T132.cd_shain ");
			strSQL.append(" AND T120.nen       = T132.nen ");
			strSQL.append(" AND T120.no_oi     = T132.no_oi ");
			strSQL.append(" AND T120.cd_kotei  = T132.cd_kotei ");
			strSQL.append(" AND T120.seq_kotei = T132.seq_kotei ");

			strSQL.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			strSQL.append(" ON  T120.cd_shain  = T320.cd_shain ");
			strSQL.append(" AND T120.nen       = T320.nen ");
			strSQL.append(" AND T120.no_oi     = T320.no_oi ");
			strSQL.append(" AND T120.cd_kotei  = T320.cd_kotei ");
			strSQL.append(" AND T120.seq_kotei = T320.seq_kotei ");

			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  'K_kote'        = M302.cd_category ");
			strSQL.append(" AND T120.zoku_kotei = M302.cd_literal ");


//			strSQL.append(" WHERE ");
//			strSQL.append("     T120.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
//			strSQL.append(" AND T120.nen       = " + toString(cd_shisaku_nen) + " ");
//			strSQL.append(" AND T120.no_oi     = " + toString(cd_shisaku_oi) + " ");
			//【QP@00342】
			strSQL.append(" WHERE ");
			strSQL.append("     T320.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T320.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T320.no_oi     = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND T320.no_eda     = " + toString(cd_shisaku_eda) + " ");

			strSQL.append(" ORDER BY ");
			strSQL.append("  T120.cd_shain ");
			strSQL.append(" ,T120.nen ");
			strSQL.append(" ,T120.no_oi ");
			strSQL.append(" ,T132.seq_shisaku ");
			strSQL.append(" ,T120.sort_kotei ");
			strSQL.append(" ,T120.sort_genryo ");
			//DB検索
			listGenryo = this.searchDB.dbSearch(strSQL.toString());


			//【QP@00342】元版の容量取得
			strSQL = null;
			strSQL = new StringBuffer();
			strSQL.append(" SELECT ");
			strSQL.append(" 	yoryo AS yoryo ");
			strSQL.append(" FROM ");
			strSQL.append(" 	tr_shisan_shisakuhin");
			strSQL.append(" WHERE ");
			strSQL.append("     cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND no_oi    = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND no_eda    = 0 ");
			//DB検索
			List<?> yoryo_list = this.searchDB.dbSearch(strSQL.toString());
			Object items = (Object) yoryo_list.get(0);
			yoryo_moto = toDouble(items,-1);


		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "計算項目算出　DBより取得する計算諸原の検索に失敗しました。 \nSQL:"
					+ strSQL.toString());

		}finally{
			//DBコネクション開放
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}
			//ローカル変数の開放
			strSQL = null;

		}

	}

}