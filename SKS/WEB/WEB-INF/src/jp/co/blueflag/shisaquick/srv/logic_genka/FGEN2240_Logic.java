package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 原価試算表（営業）を生成する
 * @author TT.Kitazawa
 * @since 2015/03/03
 */
public class FGEN2240_Logic extends LogicBaseJExcel {

	/**
	 * コンストラクタ
	 */
	public FGEN2240_Logic() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}
	/**
	 * 原価試算表を生成する
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = _userInfoData;

		//レスポンスデータ（機能）
		RequestResponsKindBean ret = null;
		//検索データ
		List<?> lstRecset = null;
		//エクセルファイルパス
		String DownLoadPath = "";

		try {
			//inputCheck でチェック済
			if (toString( reqData.getFieldVale(0, 0, "seq_shisaku")).equals("")) {
				em.ThrowException(ExceptionKind.一般Exception, "E000207", "試作列", "", "");
			}

			//DB検索
			super.createSearchDB();
			lstRecset = getData(reqData);

			//原価試算表（営業）Excelファイル生成
			DownLoadPath = makeExcelFile(lstRecset,  reqData);



			//レスポンスデータ生成
			ret = CreateRespons(DownLoadPath);

		} catch (Exception e) {
			em.ThrowException(e, "原価試算表（営業）の生成に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//DBセッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
		}
		return ret;

	}

	/**
	 * レスポンスデータを生成する
	 * @param DownLoadPath : ファイルパス生成ファイル格納先(ダウンロードパラメータ)
	 * @return RequestResponsKindBean : レスポンスデータ（機能）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private RequestResponsKindBean CreateRespons(String DownLoadPath)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsKindBean ret = null;

		try {
			//レスポンスを生成する
			ret = new RequestResponsKindBean();
			//機能IDを設置する
			ret.setID("FGEN2240");

			//ファイルパス	生成ファイル格納先
			ret.addFieldVale(0, 0, "URLValue", DownLoadPath);
			//処理結果①	成功可否
			ret.addFieldVale(0, 0, "flg_return", "true");
			//処理結果②	メッセージ
			ret.addFieldVale(0, 0, "msg_error", "");
			//処理結果③	処理名称
			ret.addFieldVale(0, 0, "no_errmsg", "");
			//処理結果⑥	メッセージ番号
			ret.addFieldVale(0, 0, "nm_class", "");
			//処理結果④	エラーコード
			ret.addFieldVale(0, 0, "cd_error", "");
			//処理結果⑤	システムメッセージ
			ret.addFieldVale(0, 0, "msg_system", "");

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	/**
	 * 原価試算表（営業）(EXCEL)を生成する
	 * @param lstRecset : 検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile(

			List<?> lstRecset
			, RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// 戻り値：ダウンロード用のパス
		String ret = "";
		// MAXサンプル列
		int intMax = 6;
		// サンプル列ごとの試作SEQ
		String[] strArraySeq = new String[intMax];
		// サンプル列ごとの試算項目
		//  [0]:原価計（円）／ケース, [1]:原価計（円）／個, [2]: 原価計（円）／Kg, [3]:売価, [4]:粗利（％）
		String[][] strArraySisan = new String[intMax][5];

		try {
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("原価試算表（営業）");

			//選択サンプル数を取得（テーブルBeanの件数）
			int reqCnt = ((RequestResponsTableBean)reqData.GetItem(0)).GetCnt();

			try{
				// サンプル列ごとの値を取得
				for (int i = 0; i < reqCnt; i++) {
					//試作SEQを取得
					strArraySeq[i] = toString(reqData.getFieldVale(0, i, "seq_shisaku"));
					//試算項目を取得:画面表示の値なのでString toString必要？
					// 原価計（円）／ケース
					strArraySisan[i][0] = toString(reqData.getFieldVale(0, i, "genkakei"));
					// 原価計（円）／個
					strArraySisan[i][1] = toString(reqData.getFieldVale(0, i, "genkakeiKo"));
					// 原価計（円）／Kg
					strArraySisan[i][2] = toString(reqData.getFieldVale(0, i, "kg_genkake"));
					// 売価
					strArraySisan[i][3] = toString(reqData.getFieldVale(0, i, "baika"));
					// 粗利（％）
					strArraySisan[i][4] = toString(reqData.getFieldVale(0, i, "arari"));
				}

			}catch(Exception e){

			}

			//ダウンロード用のEXCELを生成する
			for (int i = 0; i < lstRecset.size(); i++) {
				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset.get(i);

				if(i==0){
					//EXCELテンプレートを読み込む
					// ファイル名：試作コード + "（営業）" + 品名 + 日付
					ret = super.ExcelOutput_genka(
							makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							),
							toString(items[0]));
				}

				try{
					//Excelに値をセットする

					//品名
					super.ExcelSetValue("品名", toString(items[0]));
					//依頼番号
					super.ExcelSetValue("依頼番号", toString(items[1]));
					//日付
					super.ExcelSetValue("日付", toString(items[2]));
					//試算期日
					super.ExcelSetValue("試算期日", toString(items[8]));
					//採用サンプル
					super.ExcelSetValue("採用サンプル", toString(items[52]));
					//枝番種類
					super.ExcelSetValue("枝番種類", toString(items[39]));
					//研究所
					super.ExcelSetValue("研究所", toString(items[18]));
					//営業
					super.ExcelSetValue("営業", toString(items[19]));
					//生産管理部
					super.ExcelSetValue("生産管理部", toString(items[20]));
					//工場
					super.ExcelSetValue("工場", toString(items[21]));

					//原価試算メモ(営業用)
					super.ExcelSetValue("原価試算メモ(営業用)", toString(items[17]));
					//販責会社
					super.ExcelSetValue("販責会社", toString(items[7]));
					//製造会社
					super.ExcelSetValue("製造会社", toString(items[4]));
					//工場名(部署名)
					super.ExcelSetValue("製造工場", toString(items[5]));
					//工場名(部署名)
					super.ExcelSetValue("担当営業", toString(items[10]));

					//容器・包材
					super.ExcelSetValue("容器・包材", toString(items[11]));
					//内容量
					super.ExcelSetValue("内容量", toString(items[12]));
					//入り数
					super.ExcelSetValue("入り数", toString(items[13]));
					//荷姿
					super.ExcelSetValue("荷姿", toString(items[14]));
					//取扱温度
					super.ExcelSetValue("取扱温度", toString(items[15]));
					//賞味期間
					super.ExcelSetValue("賞味期間", toString(items[16]));


					//試作SEQごとに分岐（最終試算用）
					if ( toString(items[40]).equals(strArraySeq[0]) ){

						// サンプル列：１列目のデータセット
						setExcelValue(items, "1", strArraySisan[0]);

					} else if ( toString(items[40]).equals(strArraySeq[1]) ){

						// サンプル列：２列目のデータセット
						setExcelValue(items, "2", strArraySisan[1]);

					} else if ( toString(items[40]).equals(strArraySeq[2]) ){

						// サンプル列：３列目のデータセット
						setExcelValue(items, "3", strArraySisan[2]);

					} else if ( toString(items[40]).equals(strArraySeq[3]) ){

						// サンプル列：４列目のデータセット
						setExcelValue(items, "4", strArraySisan[3]);

					} else if ( toString(items[40]).equals(strArraySeq[4]) ){

						// サンプル列：５列目のデータセット
						setExcelValue(items, "5", strArraySisan[4]);

					} else if ( toString(items[40]).equals(strArraySeq[5]) ){

						// サンプル列：６列目のデータセット
						setExcelValue(items, "6", strArraySisan[5]);

					}

				}catch(ExceptionWaning e){
					break;

				}finally{

				}

			}

			super.ExcelWrite();
			super.close();

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			//変数の削除
			strArraySeq = null;

		}
		return ret;

	}

	/**
	 * ファイル名用の試作番号を生成する（「営業」付加）
	 * @param strCd_shain　：　試作No社員
	 * @param strNen　：　試作No年
	 * @param strNo_oi　：　試作No追い番
	 * @return　String　：　試作No
	 */
	private String makeShisakuNo(String strCd_shain, String strNen, String strNo_oi){

		String ret = "";

		ret += getRight(("0000000000" + strCd_shain),10);
		ret += "-";
		ret += getRight(("0000000000" + strNen),2);
		ret += "-";
		ret += getRight(("0000000000" + strNo_oi),3);
		//営業
		ret += "（営業）";

		return ret;

	}

	/**
	 * サンプル列のデータをExcelにセットする
	 * @param items  ： 検索結果の1行分データ
	 * @param cellNo ： 列番号
	 * @param argSisan： 試算項目の値
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void setExcelValue(Object[] items, String cellNo, String[] argSisan)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String strSetNm = "";

		//サンプルNo
		strSetNm = "サンプルNo" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[22]));
		strSetNm = "サンプル" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[22]));

		//希望原価
		strSetNm = "希望原価" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[23]));
		//売価
		strSetNm = "希望特約" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[24]));
		//原価単位をそれぞれ原価、売価向けに設定。
		strSetNm = "原価単位" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[25]));
		strSetNm = "売価単位" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[25]));
		//販売期間  販売期間選択(通年orスポット)+販売期間+販売期間単位
		strSetNm = "販売期間" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[27]) + toString(items[26]) + toString(items[28]));
		//想定物量
		strSetNm = "想定物量" + cellNo;
		super.ExcelSetValue(strSetNm, toDouble(items[29]));
		strSetNm = "想定物量単位" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[30])+toString(items[31]));
		//想定物量備考
		strSetNm = "想定物量備考" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[32]));
		//発売時期
		strSetNm = "発売時期" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[33]));

		//計画売上
		strSetNm = "計画売上" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[34]));
		//計画利益
		strSetNm = "計画利益" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[35]));
		//販売後売上
		strSetNm = "販売後売上" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[36]));
		//販売後売上
		strSetNm = "販売後利益" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[37]));

		//製造ロット
		strSetNm = "製造ロット" + cellNo;
		super.ExcelSetValue(strSetNm, toString(items[38]));

		//試算中止のサンプル列
		if(toString(items[43]).equals("1")){
			strSetNm = "試算日" + cellNo;
			super.ExcelSetValue(strSetNm, "試算中止");

		} else if(toString(items[42]).equals("1")){
			//固定項目チェックされているサンプル列
			strSetNm = "項目固定チェック" + cellNo;
			super.ExcelSetValue(strSetNm, "↓項目固定サンプルNO");

			//試算日：固定項目チェックONの時表示
			strSetNm = "試算日" + cellNo;
			super.ExcelSetValue(strSetNm, toString(items[44]));
		}

		// 試算日が空白の時は【計算項目】を表示しない
		//   画面の値を取得しているので、そのまま表示していい
		// 原価計（円）／ケース
		strSetNm = "原価計/ケース" + cellNo;
		super.ExcelSetValue(strSetNm, argSisan[0]);
		// 原価計（円）／個
		strSetNm = "原価計/個" + cellNo;
		super.ExcelSetValue(strSetNm, argSisan[1]);
		// 原価計（円）／Kg
		strSetNm = "原価計/ＫＧ" + cellNo;
		super.ExcelSetValue(strSetNm, argSisan[2]);
		// 売価
		strSetNm = "売価" + cellNo;
		super.ExcelSetValue(strSetNm, argSisan[3]);
		// 粗利（％）
		strSetNm = "粗利" + cellNo;
		super.ExcelSetValue(strSetNm, argSisan[4]);

/*
 * // 試算日が空白の時は【計算項目】非表示 －－ＤＢの値は使用しない
			if((items[44] != null) && (!items[44].equals(""))) {
				//試算項目：nullの時は表示しない
				if ((items[45] != null) && (!items[45].equals(""))) {
					strSetNm = "原価計/ケース" + cellNo;
					super.ExcelSetValue(strSetNm, toDouble(items[45]));
				}
				if ((items[46] != null) && (!items[46].equals(""))) {
					strSetNm = "原価計/個" + cellNo;
					super.ExcelSetValue(strSetNm, toDouble(items[46]));
				}
				if ((items[47] != null) && (!items[47].equals(""))) {
					strSetNm = "原価計/ＫＧ" + cellNo;
					super.ExcelSetValue(strSetNm, toDouble(items[47]));
				}
				// 試算項目（tr_shisan_shisaku_kotei） の売価は単位付の為、文字列
				if ((items[48] != null) && (!items[48].equals(""))) {
					strSetNm = "売価" + cellNo;
					super.ExcelSetValue(strSetNm, toString(items[48]));
				}
				// 画面：計算前の時、-1 がセットされている（ここではnull）
				if ((items[49] != null) && (!items[49].equals(""))) {
					strSetNm = "粗利" + cellNo;
					super.ExcelSetValue(strSetNm, toDouble(items[49]));
					strSetNm = "粗利％" + cellNo;
					super.ExcelSetValue(strSetNm, "％");
				}
			}
*/
	}

	/**
	 * 対象の原価試算データを検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData(RequestResponsKindBean KindBean)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> ret = null;
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();

		try {
			//SQL文の作成
			strSql = MakeSQLBuf(KindBean);

			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());

			//検索結果がない場合
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e, "原価試算表（営業）、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;

		}
		return ret;

	}

	/**
	 * リクエストデータより、検索SQLを生成する
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 検索SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLBuf(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try {

			String strCd_shain = "";
			String strNen = "";
			String strNo_oi = "";
			String strNo_eda = "";
			String strShisakuSeq = "";

			//試作品CD　社員コード	cd_shain
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年		nen
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番		no_oi
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//試作品No　枝番		no_eda
			strNo_eda = reqData.getFieldVale(0, 0, "no_eda");

			//選択サンプル数を取得（テーブルBeanの件数）
			int reqCnt = ((RequestResponsTableBean)reqData.GetItem(0)).GetCnt();

			//SQL文の作成
			ret.append(" SELECT ");
			ret.append("   CASE WHEN T310.nm_edaShisaku IS NOT NULL ");
			ret.append("   THEN ");
			ret.append("   CASE RTRIM(T310.nm_edaShisaku) WHEN '' ");
			ret.append("   THEN T110.nm_hin ");
			ret.append("   ELSE T110.nm_hin + ' 【' + T310.nm_edaShisaku + '】' ");
			ret.append("   END ");
			ret.append("   ELSE T110.nm_hin END AS hinmei ");						//0 品名

			ret.append("  ,CONVERT(VARCHAR,T110.no_irai) AS no_irai ");				//1 依頼番号
			ret.append("  ,CONVERT(VARCHAR,YEAR(GETDATE())) + '年' ");
			ret.append("   + CONVERT(VARCHAR,MONTH(GETDATE())) + '月' ");
			ret.append("   + CONVERT(VARCHAR,DAY(GETDATE())) + '日' AS dt_hizuke ");//2
			ret.append("  ,T310.cd_kojo AS cd_kojo ");								//3 工場コード
			ret.append("  ,M104.nm_kaisha  AS nm_kaisha ");							//4 製造会社名
			ret.append("  ,M104.nm_busho  AS nm_busho ");							//5 工場名(部署名)
			ret.append("  ,T310.cd_hanseki AS cd_hanseki ");						//6 販責会社コード
			ret.append("  ,M104_hanseki.nm_kaisha  AS nm_hanseki ");				//7 販責会社名

//			ret.append("  ,T310.dt_kizitu ");										//8 試算期日
			ret.append("  ,CONVERT(VARCHAR,YEAR(T310.dt_kizitu)) + '年' ");
			ret.append("   + CONVERT(VARCHAR,MONTH(T310.dt_kizitu)) + '月' ");
			ret.append("   + CONVERT(VARCHAR,DAY(T310.dt_kizitu)) + '日' AS dt_kizitu ");//8 試算期日

			ret.append("  ,M101.nm_user AS kenkyusho ");							//9
			ret.append("  ,M110.nm_user AS eigyo ");								//10 担当営業

			ret.append("  ,T110.youki AS youki ");								//11 容器・包材
			ret.append("  ,T310.yoryo AS yoryo ");								//12 容量
			ret.append("  ,T310.su_iri AS su_iri ");							//13 入数
			ret.append("  ,T310.cd_nisugata AS nm_nisugata ");					//14 荷姿
			ret.append("  ,M3023.nm_literal AS nm_ondo ");						//15 取扱温度
			// CHG 2019/09/17 BRC.ida start
			//ret.append("  ,T110.shomikikan AS shomikikan ");					//16 賞味期間
			ret.append(" ,CASE WHEN T110.shomikikan IS NULL THEN NULL ");
			ret.append("  ELSE T110.shomikikan + M302_TNI_SYUMI.nm_literal END AS shomikikan ");//16 賞味期間
			// CHG 2019/09/17 BRC.ida start			
			ret.append("  ,T312.memo_eigyo AS memo_eigyo ");					//17 原価試算メモ(営業用)

			//ステータス変更履歴より、研究所、営業、生産管理部のおのおの最終更新担当者名を取得
			ret.append("  ,M101_nm_kenkyu.nm_user AS nm_kenkyu ");				//18 研究所　最終アクセス担当者名
			ret.append("  ,M101_nm_eigyo.nm_user AS nm_eigyo ");				//19 営業　最終アクセス担当者名
			ret.append("  ,M101_nm_seisan.nm_user AS nm_seisan ");				//20 生産管理部　最終アクセス担当者名
			ret.append("  ,M101_nm_kojo.nm_user AS nm_kojo ");					//21 工場　最終アクセス担当者名

			ret.append("  ,T131.nm_sample AS nm_sample ");						//22 サンプルNo
			// 【基本情報：tr_shisan_kihonjoho】
			ret.append("  ,T313_kihon_sub.genka AS kibogenka ");				//23 希望原価
			ret.append("  ,T313_kihon_sub.baika AS kibobaika ");				//24 希望特約
			ret.append("  ,M302_TNI_G.nm_literal AS cd_genka_tani ");			//25 原価単位

			ret.append("  ,T313_kihon_sub.kikan_hanbai_suti AS kikan_hanbai_suti ");//26 販売期間
			ret.append("  ,M302_Hanbai_T.nm_literal AS kikan_hanbai_sen ");			//27 販売期間選択(通年orスポット)
			ret.append("  ,M302_Hanbai_K.nm_literal AS kikan_hanbai_tani ");		//28 販売期間単位

			ret.append("  ,T313_kihon_sub.buturyo_suti AS buturyo_s ");			//29 想定物量
			ret.append("  ,M302_Buturyo_U.nm_literal AS buturyo_u ");			//30 想定物量
			ret.append("  ,M302_Buturyo_K.nm_literal AS buturyo_k ");			//31 想定物量
			ret.append("  ,T313_kihon_sub.buturyo AS buturyo_biko ");			//32 想定物量備考
			ret.append("  ,T313_kihon_sub.dt_hatubai AS dt_hatubai ");			//33 発売期間

			ret.append("  ,T313_kihon_sub.uriage_k AS uriage_k ");				//34 計画売上／年
			ret.append("  ,T313_kihon_sub.rieki_k AS rieki_k ");				//35 計画利益／年
			ret.append("  ,T313_kihon_sub.uriage_h AS uriage_h ");				//36
			ret.append("  ,T313_kihon_sub.rieki_h AS rieki_h ");				//37
			ret.append("  ,T313_kihon_sub.lot AS lot ");						//38 製造ロット

			ret.append("  ,M302_Eda_S.nm_ｌiteral AS nm_shurui ");				//39 枝番種類
			ret.append("  ,T131.seq_shisaku AS seq_shisaku ");					//40 試作SEQ
			ret.append("  ,T131.no_shisan AS no_shisan ");						//41
			ret.append("  ,T331.fg_koumokuchk ");								//42 固定項目チェック
			ret.append("  ,T331.fg_chusi ");									//43 試算中止フラグ
			ret.append("  ,CONVERT(VARCHAR,T331.dt_shisan,111) dt_shisan ");	//44 試算日

			// 【試算項目：tr_shisan_shisaku_kotei】
			ret.append("  ,T332.cs_genka ");									//45 原価計（円）/ケース
			ret.append("  ,T332.ko_genka ");									//46 原価計（円）/個
			ret.append("  ,T332.kg_genka ");									//47 原価計（円）/㎏
			ret.append("  ,T332.baika ");										//48 売価
			ret.append("  ,T332.arari ");										//49 粗利（％）

			ret.append("  ,T131.sort_shisaku ");								//50 ソート
			ret.append("  ,T310.saiyo_sample ");								//51 採用サンプル(seq_shisaku)
			//試作 --- 採用サンプル
			ret.append("  ,CASE WHEN T310.saiyo_sample < 0  ");
			ret.append("   THEN  '' ");
			ret.append("   ELSE ( ");
			ret.append("    SELECT nm_sample  ");
			ret.append("     FROM tr_shisaku  ");
			ret.append("    WHERE cd_shain = " + strCd_shain);
			ret.append("    AND nen = " + strNen );
			ret.append("    AND no_oi = " + strNo_oi );
			ret.append("    AND seq_shisaku = T310.saiyo_sample )  ");
			ret.append("   END AS nm_saiyo ");									//52 採用サンプル

			//試作品（T110）
			ret.append(" FROM tr_shisakuhin T110 ");

			//サンプルNo（T131） seq_shisaku
			ret.append(" INNER JOIN tr_shisaku T131 ");
			ret.append("  ON T110.cd_shain = T131.cd_shain ");
			ret.append("  AND T110.nen = T131.nen ");
			ret.append("  AND T110.no_oi = T131.no_oi ");
			//取扱温度名取得
			ret.append(" LEFT JOIN ma_literal M3023 ");
			ret.append("  ON M3023.cd_category = 'K_toriatukaiondo' ");
			ret.append("  AND M3023.cd_literal = T110.cd_ondo ");
			//研究所：試作依頼
			ret.append(" LEFT JOIN ma_user AS M101 ");
			ret.append("  ON id_user =" + strCd_shain);

			//試算　試作品（T310）--- 荷姿・容量・入数 （seq_shisaku なし）
			ret.append(" INNER JOIN tr_shisan_shisakuhin T310 ");
			ret.append("  ON T110.cd_shain = T310.cd_shain ");
			ret.append("  AND T110.nen = T310.nen ");
			ret.append("  AND T110.no_oi = T310.no_oi ");

			//試算　基本情報（T313_kihon_sub）
			ret.append(" LEFT JOIN tr_shisan_kihonjoho T313_kihon_sub ");
			ret.append("  ON T310.cd_shain = T313_kihon_sub.cd_shain ");
			ret.append("  AND T310.nen = T313_kihon_sub.nen ");
			ret.append("  AND T310.no_oi = T313_kihon_sub.no_oi ");
			ret.append("  AND T310.no_eda = T313_kihon_sub.no_eda ");
			ret.append("  AND T131.seq_shisaku = T313_kihon_sub.seq_shisaku ");

			//試算　試作（T331）
			ret.append(" LEFT JOIN tr_shisan_shisaku T331 ");
			ret.append("  ON T310.cd_shain = T331.cd_shain ");
			ret.append("  AND T310.nen = T331.nen ");
			ret.append("  AND T310.no_oi = T331.no_oi ");
			ret.append("  AND T310.no_eda = T331.no_eda ");
			ret.append("  AND T131.seq_shisaku = T331.seq_shisaku ");

			//試算　試作（T332） 固定チェック
			ret.append(" LEFT JOIN tr_shisan_shisaku_kotei T332 ");
			ret.append("  ON T310.cd_shain = T332.cd_shain ");
			ret.append("  AND T310.nen = T332.nen ");
			ret.append("  AND T310.no_oi = T332.no_oi ");
			ret.append("  AND T310.no_eda = T332.no_eda ");
			ret.append("  AND T131.seq_shisaku = T332.seq_shisaku ");

			//担当営業者名
			ret.append(" LEFT JOIN ma_user AS M110 ");
			ret.append("  ON T110.cd_eigyo = M110.id_user ");

			//製造会社・部署名
			ret.append(" LEFT JOIN ma_busho AS M104 ");
			ret.append("  ON T310.cd_kaisha = M104.cd_kaisha ");
			ret.append("  AND T310.cd_kojo = M104.cd_busho ");

			//販責会社名
			ret.append(" LEFT JOIN (  ");
			ret.append("  SELECT DISTINCT cd_kaisha  ");
			ret.append("   , nm_kaisha  ");
			ret.append("  FROM ma_busho ) AS M104_hanseki ");
			ret.append("  ON T310.cd_hanseki = M104_hanseki.cd_kaisha ");

			//原価試算メモ（営業連絡用）
			ret.append(" LEFT JOIN tr_shisan_memo_eigyo AS T312 ");
			ret.append("  ON T310.cd_shain = T312.cd_shain ");
			ret.append("  AND T310.nen = T312.nen ");
			ret.append("  AND T310.no_oi = T312.no_oi ");

			//販売期間
			ret.append("  LEFT JOIN ma_literal AS M302_Hanbai_T ON ");
			ret.append("   'hanbai_kikan_sentaku' = M302_Hanbai_T.cd_category ");
			ret.append("   AND T313_kihon_sub.kikan_hanbai_sen = M302_Hanbai_T.cd_literal ");
			ret.append("  LEFT JOIN ma_literal AS M302_Hanbai_K ON ");
			ret.append("   'hanbai_kikan_tani' = M302_Hanbai_K.cd_category ");
			ret.append("   AND T313_kihon_sub.kikan_hanbai_tani = M302_Hanbai_K.cd_literal ");

			//原価単位
			ret.append("  LEFT JOIN ma_literal AS M302_TNI_G ON ");
			ret.append("   'K_tani_genka' = M302_TNI_G.cd_category ");
			ret.append("   AND T313_kihon_sub.cd_genka_tani = M302_TNI_G.cd_literal ");

			// ADD 2019/09/17 BRC.ida start
			//賞味期間単価
			ret.append("  LEFT JOIN ma_literal AS M302_TNI_SYUMI ON ");
			ret.append("   M302_TNI_SYUMI.cd_category = '23' ");
			ret.append("   AND T110.shomikikan_tani = M302_TNI_SYUMI.cd_literal ");
			// ADD 2019/09/17 BRC.ida end

			// 想定物量(製品単位)、想定物量(期間単位)
			ret.append("  LEFT JOIN ma_literal AS M302_Buturyo_U ON ");
			ret.append("   'sotei_buturyo_seihin' = M302_Buturyo_U.cd_category ");
			ret.append("   AND T313_kihon_sub.buturyo_seihin = M302_Buturyo_U.cd_literal ");
			ret.append("  LEFT JOIN ma_literal AS M302_Buturyo_K ON ");
			ret.append("   'sotei_buturyo_kikan' = M302_Buturyo_K.cd_category ");
			ret.append("   AND T313_kihon_sub.buturyo_kikan = M302_Buturyo_K.cd_literal ");

			//枝番種類
			ret.append("  LEFT JOIN ma_literal AS M302_Eda_S ON ");
			ret.append("   'shurui_eda' = M302_Eda_S.cd_category ");
			ret.append("   AND T310.shurui_eda = M302_Eda_S.cd_literal ");

			//ステータス変更履歴より、研究所、営業、生産管理部、工場のおのおの最終更新担当者名を取得
			ret.append(" LEFT JOIN ( ");
			ret.append(" SELECT ");
			ret.append("  TOP(1) T442.dt_henkou AS dt_henkou ");
			ret.append("  ,M101.nm_user AS nm_user ");
			ret.append("  ,T442.cd_shain AS cd_shain ");
			ret.append("  ,T442.nen AS nen ");
			ret.append("  ,T442.no_oi AS no_oi ");
			ret.append("  ,T442.no_eda AS no_eda ");
			ret.append(" FROM ");
			ret.append("  tr_shisan_status_rireki AS T442 ");
			ret.append("  INNER JOIN ma_user AS M101 ON T442.id_henkou = M101.id_user ");
			ret.append(" WHERE ");
			ret.append("  T442.cd_zikko_ca = 'wk_kenkyu' ");
			ret.append("  AND T442.cd_shain = " + strCd_shain);
			ret.append("  AND T442.nen = " + strNen);
			ret.append("  AND T442.no_oi = " + strNo_oi);
			ret.append("  AND T442.no_eda = " + strNo_eda);
			ret.append(" ORDER BY T442.dt_henkou DESC ");
			ret.append(" ) AS M101_nm_kenkyu ON ");
			ret.append("  T310.cd_shain = M101_nm_kenkyu.cd_shain ");
			ret.append("  AND T310.nen = M101_nm_kenkyu.nen ");
			ret.append("  AND T310.no_oi = M101_nm_kenkyu.no_oi ");
			ret.append("  AND T310.no_eda = M101_nm_kenkyu.no_eda ");

			ret.append(" LEFT JOIN ( ");
			ret.append(" SELECT ");
			ret.append("  TOP(1) T442.dt_henkou AS dt_henkou ");
			ret.append("  ,M101.nm_user AS nm_user ");
			ret.append("  ,T442.cd_shain AS cd_shain ");
			ret.append("  ,T442.nen AS nen ");
			ret.append("  ,T442.no_oi AS no_oi ");
			ret.append("  ,T442.no_eda AS no_eda ");
			ret.append(" FROM ");
			ret.append("  tr_shisan_status_rireki AS T442 ");
			ret.append("  INNER JOIN ma_user AS M101 ON T442.id_henkou = M101.id_user ");
			ret.append(" WHERE ");
			ret.append("  T442.cd_zikko_ca = 'wk_eigyo' ");
			ret.append("  AND T442.cd_shain = " + strCd_shain);
			ret.append("  AND T442.nen = " + strNen);
			ret.append("  AND T442.no_oi = " + strNo_oi);
			ret.append("  AND T442.no_eda = " + strNo_eda);
			ret.append(" ORDER BY T442.dt_henkou DESC ");
			ret.append(" ) AS M101_nm_eigyo ON ");
			ret.append("  T310.cd_shain = M101_nm_eigyo.cd_shain ");
			ret.append("  AND T310.nen = M101_nm_eigyo.nen ");
			ret.append("  AND T310.no_oi = M101_nm_eigyo.no_oi ");
			ret.append("  AND T310.no_eda = M101_nm_eigyo.no_eda ");

			ret.append(" LEFT JOIN ( ");
			ret.append(" SELECT ");
			ret.append("  TOP(1) T442.dt_henkou AS dt_henkou ");
			ret.append("  ,M101.nm_user AS nm_user ");
			ret.append("  ,T442.cd_shain AS cd_shain ");
			ret.append("  ,T442.nen AS nen ");
			ret.append("  ,T442.no_oi AS no_oi ");
			ret.append("  ,T442.no_eda AS no_eda ");
			ret.append(" FROM ");
			ret.append("  tr_shisan_status_rireki AS T442 ");
			ret.append("  INNER JOIN ma_user AS M101 ON T442.id_henkou = M101.id_user ");
			ret.append(" WHERE ");
			ret.append("  T442.cd_zikko_ca = 'wk_seisan' ");
			ret.append("  AND T442.cd_shain = " + strCd_shain);
			ret.append("  AND T442.nen = " + strNen);
			ret.append("  AND T442.no_oi = " + strNo_oi);
			ret.append("  AND T442.no_eda = " + strNo_eda);
			ret.append(" ORDER BY T442.dt_henkou DESC ");
			ret.append(" ) AS M101_nm_seisan ON ");
			ret.append("  T310.cd_shain = M101_nm_seisan.cd_shain ");
			ret.append("  AND T310.nen = M101_nm_seisan.nen ");
			ret.append("  AND T310.no_oi = M101_nm_seisan.no_oi ");
			ret.append("  AND T310.no_eda = M101_nm_seisan.no_eda ");

			ret.append(" LEFT JOIN ( ");
			ret.append(" SELECT ");
			ret.append("  TOP(1) T442.dt_henkou AS dt_henkou ");
			ret.append("  ,M101.nm_user AS nm_user ");
			ret.append("  ,T442.cd_shain AS cd_shain ");
			ret.append("  ,T442.nen AS nen ");
			ret.append("  ,T442.no_oi AS no_oi ");
			ret.append("  ,T442.no_eda AS no_eda ");
			ret.append(" FROM ");
			ret.append("  tr_shisan_status_rireki AS T442 ");
			ret.append("  INNER JOIN ma_user AS M101 ON T442.id_henkou = M101.id_user ");
			ret.append(" WHERE ");
			ret.append("  T442.cd_zikko_ca = 'wk_kojo' ");
			ret.append("  AND T442.cd_shain = " + strCd_shain);
			ret.append("  AND T442.nen = " + strNen);
			ret.append("  AND T442.no_oi = " + strNo_oi);
			ret.append("  AND T442.no_eda = " + strNo_eda);
			ret.append(" ORDER BY T442.dt_henkou DESC ");
			ret.append(" ) AS M101_nm_kojo ON ");
			ret.append("  T310.cd_shain = M101_nm_kojo.cd_shain ");
			ret.append("  AND T310.nen = M101_nm_kojo.nen ");
			ret.append("  AND T310.no_oi = M101_nm_kojo.no_oi ");
			ret.append("  AND T310.no_eda = M101_nm_kojo.no_eda ");


			//検索条件
			//絞り込み（試算　試作品）
			ret.append(" WHERE ");
			ret.append("  T310.cd_shain = " + strCd_shain);
			ret.append("  AND T310.nen = " + strNen);
			ret.append("  AND T310.no_oi = " + strNo_oi);
			ret.append("  AND T310.no_eda = " + strNo_eda);

			//試作SEQ
			ret.append("  AND T131.seq_shisaku IN ( ");
			for ( int i=0; i<reqCnt; i++ ) {
				//試作SEQの取得
				try{
					strShisakuSeq = toString(reqData.getFieldVale(0, i, "seq_shisaku"));
					if ( i != 0 ) {
						ret.append(" , ");
					}
					ret.append(strShisakuSeq);

				}catch(Exception e){
					strShisakuSeq = "";
				}
			}
			ret.append(" ) ");

			ret.append("  ORDER BY ");
			ret.append("   T131.sort_shisaku ");

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算表（営業）、検索SQLの生成に失敗しました。");

		} finally {

		}
		return ret;

	}

}