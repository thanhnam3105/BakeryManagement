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
import jp.co.blueflag.shisaquick.srv.logic.LiteralDataSearchLogic;

/**
 * 原価試算表（原価試算用）を生成する
 * @author isono
 * @since  2009/11/06
 */
public class FGEN0050_Logic extends LogicBaseJExcel {

	/**
	 * コンストラクタ
	 */
	public FGEN0050_Logic() {
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
		List<?> lstRecset_shizai = null;
		//エクセルファイルパス
		String DownLoadPath = "";

		try {
			//DB検索
			super.createSearchDB();
			lstRecset = getData(reqData);
			lstRecset_shizai = getData_shizai(reqData);

			// 20160524  KPX@1600766 ADD start
			// ユーザの部署：研究所の時、製造会社の単価開示権限取得
			int flgTanka = getTankaHyouji_kengen(reqData, userInfoData);
			// 20160524  KPX@1600766 ADD end

			//工程値
			String strKoteiValue = getSyurui(reqData);

			// 20160524  KPX@1600766 MOD start
			//単価開示権限により単価・歩留等（非表示項目）を0に変更
			//工程値により、「調味料パターン」か「その他調味料以外パターン」かを判定する
			if ( strKoteiValue.equals("1") ) {
				//調味料Excelファイル生成
//				DownLoadPath = makeExcelFile1(lstRecset, lstRecset_shizai, reqData);
				DownLoadPath = makeExcelFile1(lstRecset, lstRecset_shizai, reqData, flgTanka);

			} else if ( strKoteiValue.equals("2") ) {
				//その他Excelファイル生成
//				DownLoadPath = makeExcelFile2(lstRecset, lstRecset_shizai, reqData);
				DownLoadPath = makeExcelFile2(lstRecset, lstRecset_shizai, reqData, flgTanka);

			}
			// 20160524  KPX@1600766 MOD end

			//レスポンスデータ生成
			ret = CreateRespons(DownLoadPath);

		} catch (Exception e) {
			em.ThrowException(e, "原価試算表の生成に失敗しました。");

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
			ret.setID("FGEN0050");

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
	// 20160524  KPX@1600766 MOD start
	/**
	 * 調味料_原価試算表(EXCEL)を生成する
	 * @param lstRecset : 検索データリスト
	 * @param lstRecset_Shizai : 資材データリスト
	 * @param flgTanka: 単価開示権限 （9：単価開示（全て） 1：単価開示許可   0：単価開示不可）
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(

			List<?> lstRecset
			, List<?> lstRecset_Shizai
			, RequestResponsKindBean reqData
			, int flgTanka
	// 20160524  KPX@1600766 MOD end
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";
		String strShisakuSeq1 = "";
		String strShisakuSeq2 = "";
		String strShisakuSeq3 = "";
		int intChuijiko = 0;

		//【QP@00342】
		String strSuiZyuten1 = "";
		String strSuiZyuten2 = "";
		String strSuiZyuten3 = "";
		String strYuZyuten1 = "";
		String strYuZyuten2 = "";
		String strYuZyuten3 = "";

		try {
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("調味料原価試算表");

			// ADD 2015/06/17 E.kitazawa【QP@40812】  --- start
			//選択サンプル数を取得（テーブルBeanの件数）
			int reqCnt = ((RequestResponsTableBean)reqData.GetItem(0)).GetCnt();
			// ADD 2015/06/17 E.kitazawa【QP@40812】  --- end

			//試作SEQを取得
			try{
				strShisakuSeq1 = toString( reqData.getFieldVale(0, 0, "seq_shisaku"));

				//【QP@00342】
				strSuiZyuten1 = toString( reqData.getFieldVale(0, 0, "zyuten_sui"));
				strYuZyuten1 = toString( reqData.getFieldVale(0, 0, "zyuten_yu"));

			}catch(Exception e){

			}
			try{
				// MOD 2015/06/17 E.kitazawa【QP@40812】  --- start
				// リクエストデータが存在するときのみ値を取得する
				if (reqCnt > 1) {
					strShisakuSeq2 = toString( reqData.getFieldVale(0, 1, "seq_shisaku"));

					//【QP@00342】
					strSuiZyuten2 = toString( reqData.getFieldVale(0, 1, "zyuten_sui"));
					strYuZyuten2 = toString( reqData.getFieldVale(0, 1, "zyuten_yu"));

				}

			}catch(Exception e){

			}
			try{
				if (reqCnt > 2) {
					strShisakuSeq3 = toString( reqData.getFieldVale(0, 2, "seq_shisaku"));

					//【QP@00342】
					strSuiZyuten3 = toString( reqData.getFieldVale(0, 2, "zyuten_sui"));
					strYuZyuten3 = toString( reqData.getFieldVale(0, 2, "zyuten_yu"));
				}
				// MOD 2015/06/17 E.kitazawa【QP@40812】  --- end

			}catch(Exception e){

			}
			//ダウンロード用のEXCELを生成する
			for (int i = 0; i < lstRecset.size(); i++) {


				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset.get(i);

				// 20160524  KPX@1600766 ADD start
				//単価開示権限により 0設定
				items = setObj(items, flgTanka);
				// 20160524  KPX@1600766 ADD end


				//ADD 2012.4.30　【H24年度対応】JEXCEL対応の為追加
				if(i==0){
					//EXCELテンプレートを読み込む
					ret = super.ExcelOutput_genka(
							makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							),
							this.replaceSymbol(toString(items[2])));
				}

				try{
					//Excelに値をセットする

					//品名
					super.ExcelSetValue("品名", toString(items[2]));
					//依頼番号
					super.ExcelSetValue("依頼番号", toString(items[3]));
					//日付
					super.ExcelSetValue("日付", toString(items[4]));
					//工場コード	（生産本部）5
					//super.ExcelSetValue("工場コード", toDouble(items[5]));		//DEL 2012.4.30　【H24年度対応】コード出力から名称出力に変更
					//ADD&UPD 2012.4.30　【H24年度対応】履歴から最終アクセス社を担当者として編集。研究所、営業、生産管理部
					//研究所
					super.ExcelSetValue("研究所", toString(items[50]));
					//営業
					super.ExcelSetValue("営業", toString(items[51]));
					//生産管理部
					super.ExcelSetValue("生産管理部", toString(items[52]));

					//ADD 2012.4.30　【H24年度対応】原価試算メモ、原価試算メモ(営業用)
					super.ExcelSetValue("原価試算メモ", toString(items[44]));
					super.ExcelSetValue("原価試算メモ(営業用)", toString(items[45]));

					//試作SEQごとに分岐
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//サンプルNo1
						super.ExcelSetValue("サンプルNo1", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版1編集
						super.ExcelSetValue("製品_サンプルNo1","サンプル:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No1","工程版:" + toString(items[39]));
							super.ExcelSetValue("製品_注意事項1",toString(items[33]));
						}

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//サンプルNo2
						super.ExcelSetValue("サンプルNo2", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版2編集
						super.ExcelSetValue("製品_サンプルNo2","サンプル:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No2","工程版:" + toString(items[39]));
							super.ExcelSetValue("製品_注意事項2",toString(items[33]));
						}

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//サンプルNo3
						super.ExcelSetValue("サンプルNo3", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版3編集
						super.ExcelSetValue("製品_サンプルNo3","サンプル:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No3","工程版:" + toString(items[39]));
							super.ExcelSetValue("製品_注意事項3",toString(items[33]));
						}

					}

					//工程が「殺菌調味液」の場合
					if (toString(items[1]).equals("1")) {
						//***** [殺菌調味液] *****

						if ( toString(items[38]).equals(strShisakuSeq1) ) {
							//新規原料（生産本部）9
							super.ExcelSetValue("工程1_新規原料", toString(items[9]));
							//品コード
							super.ExcelSetValue("工程1_品コード", toString(items[10]));
							//品名（生産本部）11
							super.ExcelSetValue("工程1_品名", toString(items[11]));
							//単価（生産本部）12
							super.ExcelSetValue("工程1_単価", toDouble(items[12]));
							//歩留（生産本部）13
							super.ExcelSetValue("工程1_歩留", (toDouble(items[13]) / 100));

						}

						//試作SEQごとに分岐
						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//配合1
							super.ExcelSetValue("工程1_配合1", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq2) ){
							//配合2
							super.ExcelSetValue("工程1_配合2", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq3) ){
							//配合3
							super.ExcelSetValue("工程1_配合3", toDouble(items[14]));

						}

					//工程が「水相」の場合
					} else if (toString(items[1]).equals("2")) {
						//***** [水相] *****

						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//新規原料（生産本部）
							super.ExcelSetValue("工程2_新規原料", toString(items[9]));
							//品コード
							super.ExcelSetValue("工程2_品コード", toString(items[10]));
							//品名（生産本部）
							super.ExcelSetValue("工程2_品名", toString(items[11]));
							//単価（生産本部）
							super.ExcelSetValue("工程2_単価", toDouble(items[12]));
							//歩留（生産本部）
							super.ExcelSetValue("工程2_歩留", (toDouble(items[13]) / 100));

						}

						//試作SEQごとに分岐
						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//配合1
							super.ExcelSetValue("工程2_配合1", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq2) ){
							//配合2
							super.ExcelSetValue("工程2_配合2", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq3) ){
							//配合3
							super.ExcelSetValue("工程2_配合3", toDouble(items[14]));

						}

					//工程が「油相」の場合
					} else if (toString(items[1]).equals("3")) {
						//***** [油相] *****

						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//新規原料（生産本部）
							super.ExcelSetValue("工程3_新規原料", toString(items[9]));
							//品コード
							super.ExcelSetValue("工程3_品コード", toString(items[10]));
							//品名（生産本部）
							super.ExcelSetValue("工程3_品名", toString(items[11]));
							//単価（生産本部）
							super.ExcelSetValue("工程3_単価", toDouble(items[12]));
							//歩留（生産本部）
							super.ExcelSetValue("工程3_歩留", (toDouble(items[13]) / 100));

						}

						//試作SEQごとに分岐
						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//配合1
							super.ExcelSetValue("工程3_配合1", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq2) ){
							//配合2
							super.ExcelSetValue("工程3_配合2", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq3) ){
							//配合3
							super.ExcelSetValue("工程3_配合3", toDouble(items[14]));

						}

					}

					//原料情報
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//充填量水相1
						super.ExcelSetValue("水相1", toDouble(strSuiZyuten1));
						//充填量油相1
						super.ExcelSetValue("油相1", toDouble(strYuZyuten1));
						//比重1
						super.ExcelSetValue("比重1", toDouble(items[17]));

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//充填量水相2
						super.ExcelSetValue("水相2", toDouble(strSuiZyuten2));
						//充填量油相2
						super.ExcelSetValue("油相2", toDouble(strYuZyuten2));
						//比重2
						super.ExcelSetValue("比重2", toDouble(items[17]));

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//充填量水相3
						super.ExcelSetValue("水相3", toDouble(strSuiZyuten3));
						//充填量油相3
						super.ExcelSetValue("油相3", toDouble(strYuZyuten3));
						//比重3
						super.ExcelSetValue("比重3", toDouble(items[17]));

					}

					//ADD 2012.4.30　【H24年度対応】工場名(部署名)追加
					super.ExcelSetValue("工場", toString(items[43]));
					//製造方法
					super.ExcelSetValue("製造方法", toString(items[18]));
					//充填方法
					super.ExcelSetValue("充填方法", toString(items[19]));
					//殺菌方法
					super.ExcelSetValue("殺菌方法", toString(items[20]));
					//取扱温度
					super.ExcelSetValue("取扱温度", toString(items[21]));
					//容器・包材
					super.ExcelSetValue("容器・包材", toString(items[22]));
					//荷姿（生産本部）23
					super.ExcelSetValue("荷姿", toString(items[23]));
					//内容量
					super.ExcelSetValue("内容量", toString(items[24]));
					//入り数（生産本部）25
					super.ExcelSetValue("入り数", toString(items[25]));
					//賞味期間
					super.ExcelSetValue("賞味期間", toString(items[26]));
					// MOD 2013/11/14 okano【QP@30154】start
//						//製造ロット
//						super.ExcelSetValue("製造ロット", toString(items[53]));
//						//発売時期（生産本部）27
//						super.ExcelSetValue("発売時期", toString(items[27]));
//						//想定物量（生産本部）28
//						// MOD 2013/9/6 okano【QP@30151】No.30 start
//	//						super.ExcelSetValue("想定物量", toString(items[28]));
//						super.ExcelSetValue("想定物量", toDouble(items[28]));
//						super.ExcelSetValue("想定物量単位", toString(items[29])+toString(items[30]));
//						// MOD 2013/9/6 okano【QP@30151】No.30 end
//						//売価（生産本部）29
//						super.ExcelSetValue("売価", toString(items[31]));
//						//希望原価（生産本部）30
//						super.ExcelSetValue("希望原価", toString(items[32]));
//
//
//						//ADD 2012.4.30　【H24年度対応】販売期間  販売期間選択(通年orスポット)+販売期間+販売期間単位
//						super.ExcelSetValue("販売期間", toString(items[47]) + toString(items[46]) + toString(items[48]));
//						//ADD 2012.4.30　【H24年度対応】原価単位をそれぞれ原価、売価向けに設定。
//						super.ExcelSetValue("原価単位", toString(items[49]));
//						super.ExcelSetValue("売価単位", toString(items[49]));
					//最終試算用
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//製造ロット
						super.ExcelSetValue("製造ロット1", toString(items[53]));
						//発売時期（生産本部）27
						super.ExcelSetValue("発売時期1", toString(items[27]));
						//想定物量（生産本部）28
						super.ExcelSetValue("想定物量1", toDouble(items[28]));
						super.ExcelSetValue("想定物量単位1", toString(items[29])+toString(items[30]));
						//売価（生産本部）29
						super.ExcelSetValue("売価1", toString(items[31]));
						//希望原価（生産本部）30
						super.ExcelSetValue("希望原価1", toString(items[32]));
						//販売期間  販売期間選択(通年orスポット)+販売期間+販売期間単位
						super.ExcelSetValue("販売期間1", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//原価単位をそれぞれ原価、売価向けに設定。
						super.ExcelSetValue("原価単位1", toString(items[49]));
						super.ExcelSetValue("売価単位1", toString(items[49]));

						//ADD 2014/04/04 shima 【QP@30154】start
						super.ExcelSetValue("試算日1", toString(items[71]));
						//ADD 2014/04/04 shima 【QP@30154】end

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//製造ロット
						super.ExcelSetValue("製造ロット2", toString(items[53]));
						//発売時期（生産本部）27
						super.ExcelSetValue("発売時期2", toString(items[27]));
						//想定物量（生産本部）28
						super.ExcelSetValue("想定物量2", toDouble(items[28]));
						super.ExcelSetValue("想定物量単位2", toString(items[29])+toString(items[30]));
						//売価（生産本部）29
						super.ExcelSetValue("売価2", toString(items[31]));
						//希望原価（生産本部）30
						super.ExcelSetValue("希望原価2", toString(items[32]));
						//販売期間  販売期間選択(通年orスポット)+販売期間+販売期間単位
						super.ExcelSetValue("販売期間2", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//原価単位をそれぞれ原価、売価向けに設定。
						super.ExcelSetValue("原価単位2", toString(items[49]));
						super.ExcelSetValue("売価単位2", toString(items[49]));

						//ADD 2014/04/04 shima 【QP@30154】start
						super.ExcelSetValue("試算日2", toString(items[71]));
						//ADD 2014/04/04 shima 【QP@30154】end

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//製造ロット
						super.ExcelSetValue("製造ロット3", toString(items[53]));
						//発売時期（生産本部）27
						super.ExcelSetValue("発売時期3", toString(items[27]));
						//想定物量（生産本部）28
						super.ExcelSetValue("想定物量3", toDouble(items[28]));
						super.ExcelSetValue("想定物量単位3", toString(items[29])+toString(items[30]));
						//売価（生産本部）29
						super.ExcelSetValue("売価3", toString(items[31]));
						//希望原価（生産本部）30
						super.ExcelSetValue("希望原価3", toString(items[32]));
						//販売期間  販売期間選択(通年orスポット)+販売期間+販売期間単位
						super.ExcelSetValue("販売期間3", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//原価単位をそれぞれ原価、売価向けに設定。
						super.ExcelSetValue("原価単位3", toString(items[49]));
						super.ExcelSetValue("売価単位3", toString(items[49]));

						//ADD 2014/04/04 shima 【QP@30154】start
						super.ExcelSetValue("試算日3", toString(items[71]));
						//ADD 2014/04/04 shima 【QP@30154】end

					}
					// MOD 2013/11/14 okano【QP@30154】end

					//DEL 2012.4.30　【H24年度対応】製品情報の変更
//					//最新の注意事項を設定する
//					if ( !toString(items[37]).isEmpty() ) {
//
//						if ( intChuijiko < Integer.parseInt(toString(items[37])) ){
//							intChuijiko = Integer.parseInt(toString(items[37]));
//
//							//製品情報
//							super.ExcelSetValue("製品情報", toString(toString(items[31])));
//
//						}
//
//					}

					//最終試算用
					if ( toString(items[38]).equals(strShisakuSeq1) ){
//2009/11/27　isono　課題管理：119
						//平均充填量1（生産本部）32
//						super.ExcelSetValue("平均充填量1", toDouble(items[32]));
						super.ExcelSetValue("平均充填量1", toDouble(items[34])*1000);
						//有効歩留1（生産本部）33
						super.ExcelSetValue("有効歩留1", (toDouble(items[35]) / 100));
						//固定費1（生産本部）34
						super.ExcelSetValue("固定費1", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
						//固定項目チェックされているサンプル列の場合は、ＤＢ値を計算セルに上書き
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("レベル量(ｇ)1", toDouble(items[59])*1000);
							super.ExcelSetValue("比重加算量（ｇ）1", toDouble(items[60])*1000);
							super.ExcelSetValue("原料費/ケース1", toDouble(items[61]));
							super.ExcelSetValue("材料費/ケース1", toDouble(items[62]));
							super.ExcelSetValue("原価計/ケース1", toDouble(items[63]));
							super.ExcelSetValue("原価計/個1", toDouble(items[64]));
							super.ExcelSetValue("原料費/ＫＧ1", toDouble(items[65]));
							super.ExcelSetValue("材料費/ＫＧ1", toDouble(items[66]));
							super.ExcelSetValue("固定費/ＫＧ1", toDouble(items[70]));
							super.ExcelSetValue("原価計/ＫＧ1", toDouble(items[67]));
						//ADD 2014/04/22 start【QP@30154】追加
							super.ExcelSetValue("希望特約1", toDouble(items[68]));
							super.ExcelSetValue("粗利(％)1", toDouble(items[69]));
						//ADD 2014/04/22 end 【QP@30154】追加
							//ADD 2014/05/05 start 【QP@30154】追加
							super.ExcelSetValue("項目固定チェック1", "↓項目固定サンプルNO");
							//ADD 2014/05/05 end 【QP@30154】追加
						}
						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
//2009/11/27　isono　課題管理：119
						//平均充填量2（生産本部）
//						super.ExcelSetValue("平均充填量2", toDouble(items[32]));
						super.ExcelSetValue("平均充填量2", toDouble(items[34])*1000);
						//有効歩留2（生産本部）
						super.ExcelSetValue("有効歩留2", (toDouble(items[35]) / 100));
						//固定費2（生産本部）
						super.ExcelSetValue("固定費2", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
						//固定項目チェックされているサンプル列の場合は、ＤＢ値を計算セルに上書き
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("レベル量(ｇ)2", toDouble(items[59])*1000);
							super.ExcelSetValue("比重加算量（ｇ）2", toDouble(items[60])*1000);
							super.ExcelSetValue("原料費/ケース2", toDouble(items[61]));
							super.ExcelSetValue("材料費/ケース2", toDouble(items[62]));
							super.ExcelSetValue("原価計/ケース2", toDouble(items[63]));
							super.ExcelSetValue("原価計/個2", toDouble(items[64]));
							super.ExcelSetValue("原料費/ＫＧ2", toDouble(items[65]));
							super.ExcelSetValue("材料費/ＫＧ2", toDouble(items[66]));
							super.ExcelSetValue("固定費/ＫＧ2", toDouble(items[70]));
							super.ExcelSetValue("原価計/ＫＧ2", toDouble(items[67]));
						//ADD 2014/04/22 start【QP@30154】追加
							super.ExcelSetValue("希望特約2", toDouble(items[68]));
							super.ExcelSetValue("粗利(％)2", toDouble(items[69]));
						//ADD 2014/04/22 end 【QP@30154】追加
							//ADD 2014/05/05 start 【QP@30154】追加
							super.ExcelSetValue("項目固定チェック2", "↓項目固定サンプルNO");
							//ADD 2014/05/05 end 【QP@30154】追加
						}
						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
//2009/11/27　isono　課題管理：119
						//平均充填量3（生産本部）
//						super.ExcelSetValue("平均充填量3", toDouble(items[32]));
						super.ExcelSetValue("平均充填量3", toDouble(items[34])*1000);
						//有効歩留3（生産本部）
						super.ExcelSetValue("有効歩留3", (toDouble(items[35]) / 100));
						//固定費3（生産本部）
						super.ExcelSetValue("固定費3", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
						//固定項目チェックされているサンプル列の場合は、ＤＢ値を計算セルに上書き
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("レベル量(ｇ)3", toDouble(items[59])*1000);
							super.ExcelSetValue("比重加算量（ｇ）3", toDouble(items[60])*1000);
							super.ExcelSetValue("原料費/ケース3", toDouble(items[61]));
							super.ExcelSetValue("材料費/ケース3", toDouble(items[62]));
							super.ExcelSetValue("原価計/ケース3", toDouble(items[63]));
							super.ExcelSetValue("原価計/個3", toDouble(items[64]));
							super.ExcelSetValue("原料費/ＫＧ3", toDouble(items[65]));
							super.ExcelSetValue("材料費/ＫＧ3", toDouble(items[66]));
							super.ExcelSetValue("固定費/ＫＧ3", toDouble(items[70]));
							super.ExcelSetValue("原価計/ＫＧ3", toDouble(items[67]));
						//ADD 2014/04/22 start【QP@30154】追加
							super.ExcelSetValue("希望特約3", toDouble(items[68]));
							super.ExcelSetValue("粗利(％)3", toDouble(items[69]));
						//ADD 2014/04/22 end 【QP@30154】追加
							//ADD 2014/05/05 start 【QP@30154】追加
							super.ExcelSetValue("項目固定チェック3", "↓項目固定サンプルNO");
							//ADD 2014/05/05 end 【QP@30154】追加
						}
						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

					}

				}catch(ExceptionWaning e){
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;

				}finally{

				}

			}

			//資材情報をセットする
			for (int i = 0; i < lstRecset_Shizai.size(); i++) {

				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset_Shizai.get(i);

				// 20160524  KPX@1600766 ADD start
				//単価開示（全て）以外の時、単価・歩留を隠す
				if (flgTanka < 9) {
					items[9] = 0;
					items[10] = 0;
					items[11] = 0;
				}
				// 20160524  KPX@1600766 ADD end

				try{
					//Excelに値をセットする

					//資材_対象資材（工場記号）
					super.ExcelSetValue("資材_対象資材（工場記号）", toString(items[12]));
					//資材_資材コード
					super.ExcelSetValue("資材_資材コード", toString(items[7]));
					//資材_資材名
					super.ExcelSetValue("資材_資材名", toString(items[8]));
					//資材_単価
					super.ExcelSetValue("資材_単価", (toDouble(items[9])));
					//資材_歩留
					super.ExcelSetValue("資材_歩留", toDouble(items[10]) / 100);
					//資材_使用量
					super.ExcelSetValue("資材_使用量", toDouble(items[11]));

				}catch(ExceptionWaning e){
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;

				}finally{

				}

			}

			//DEL 2012.4.30　【H24年度対応】JEXCEL対応の為移動
			//エクセルファイルをダウンロードフォルダに生成する
//			ret = super.ExcelOutput_genka(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
//					,hinNm
//					);

			//ADD 2012.4.30　【H24年度対応】JEXCEL対応の為追加
			super.ExcelWrite();
			super.close();

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			//変数の削除
			strShisakuSeq1 = null;
			strShisakuSeq2 = null;
			strShisakuSeq3 = null;
			intChuijiko = 0;

		}
		return ret;

	}
	/**
	 * ファイル名用の試作番号を生成する
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

		return ret;

	}

	// 20160524  KPX@1600766 MOD start
	/**
	 * その他_原価試算表(EXCEL)を生成する
	 * @param lstRecset : 検索データリスト
	 * @param lstRecset_Shizai : 資材データリスト
	 * @param flgTanka: 単価開示権限 （9：単価開示（全て） 1：単価開示許可   0：単価開示不可）
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile2(

			List<?> lstRecset
			,List<?> lstRecset_Shizai
			, RequestResponsKindBean reqData
			, int flgTanka
	// 20160524  KPX@1600766 MOD end
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";
		String strShisakuSeq1 = "";
		String strShisakuSeq2 = "";
		String strShisakuSeq3 = "";
		String hinNm = "";
		int intChuijiko = 0;

		try {
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("その他原価試算表");

			// ADD 2015/06/17 E.kitazawa【QP@40812】  --- start
			//選択サンプル数を取得（テーブルBeanの件数）
			int reqCnt = ((RequestResponsTableBean)reqData.GetItem(0)).GetCnt();
			// ADD 2015/06/17 E.kitazawa【QP@40812】  --- start


			//試作SEQを取得
			try{
				strShisakuSeq1 = toString( reqData.getFieldVale(0, 0, "seq_shisaku"));

			}catch(Exception e){
			}
			try{
				// MOD 2015/06/17 E.kitazawa【QP@40812】  --- start
				// リクエストデータが存在するときのみ値を取得する
				if (reqCnt > 1) {
					strShisakuSeq2 = toString( reqData.getFieldVale(0, 1, "seq_shisaku"));
				}

			}catch(Exception e){
			}
			try{
				if (reqCnt > 2) {
					strShisakuSeq3 = toString( reqData.getFieldVale(0, 2, "seq_shisaku"));
				}
				// MOD 2015/06/17 E.kitazawa【QP@40812】  --- end

			}catch(Exception e){
			}

			//ダウンロード用のEXCELを生成する
			for (int i = 0; i < lstRecset.size(); i++) {

				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset.get(i);

				// 20160524  KPX@1600766 ADD start
				//単価開示権限により 0設定
				items = setObj(items, flgTanka);
				// 20160524  KPX@1600766 ADD end

				//ADD 2012.4.30　【H24年度対応】JEXCEL対応の為追加
				if(i==0){
					ret = super.ExcelOutput_genka(
							makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							),
							this.replaceSymbol(toString(items[2])));	//品名
				}

				try{
					//Excelに値をセットする

					//品名
					super.ExcelSetValue("品名", toString(items[2]));
					hinNm = toString(items[2]);
					//依頼番号
					super.ExcelSetValue("依頼番号", toString(items[3]));
					//日付
					super.ExcelSetValue("日付", toString(items[4]));
					//工場コード（生産本部）
					//super.ExcelSetValue("工場コード", toDouble(items[5]));		//DEL 2012.4.30　【H24年度対応】コード出力から名称出力に変更
					//ADD&UPD 2012.4.30　【H24年度対応】履歴から最終アクセス社を担当者として編集。研究所、営業、生産管理部
					//研究所
					super.ExcelSetValue("研究所", toString(items[50]));
					//営業
					super.ExcelSetValue("営業", toString(items[51]));
					//生産管理部
					super.ExcelSetValue("生産管理部", toString(items[52]));

					//ADD 2012.4.30　【H24年度対応】原価試算メモ、原価試算メモ(営業用)
					super.ExcelSetValue("原価試算メモ", toString(items[44]));
					super.ExcelSetValue("原価試算メモ(営業用)", toString(items[45]));

					//試作SEQごとに分岐
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//サンプルNo1
						super.ExcelSetValue("サンプルNo1", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版1編集
						super.ExcelSetValue("製品_サンプルNo1","サンプル:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No1","工程版:" + toString(items[39]));
							super.ExcelSetValue("製品_注意事項1",toString(items[33]));
						}

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//サンプルNo2
						super.ExcelSetValue("サンプルNo2", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版2編集
						super.ExcelSetValue("製品_サンプルNo2","サンプル:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No2","工程版:" + toString(items[39]));
							super.ExcelSetValue("製品_注意事項2",toString(items[33]));
						}

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//サンプルNo3
						super.ExcelSetValue("サンプルNo3", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版3編集
						super.ExcelSetValue("製品_サンプルNo3","サンプル:" + toString(items[8]));
						if ( !toString(items[39]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No3","工程版:" + toString(items[39]));
							super.ExcelSetValue("製品_注意事項3",toString(items[33]));
						}

					}

					//工程が「殺菌調味液」の場合
					if (toString(items[1]).equals("1")) {
						//***** [殺菌調味液] *****

						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//新規原料（生産本部）9
							super.ExcelSetValue("工程1_新規原料", toString(items[9]));
							//品コード
							super.ExcelSetValue("工程1_品コード", toString(items[10]));
							//品名
							super.ExcelSetValue("工程1_品名", toString(items[11]));
							//単価
							super.ExcelSetValue("工程1_単価", toDouble(items[12]));
							//歩留
							super.ExcelSetValue("工程1_歩留", (toDouble(items[13]) / 100));

						}

						//試作SEQごとに分岐
						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//配合1
							super.ExcelSetValue("工程1_配合1", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq2) ){
							//配合2
							super.ExcelSetValue("工程1_配合2", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq3) ){
							//配合3
							super.ExcelSetValue("工程1_配合3", toDouble(items[14]));

						}

					//工程が「水相」の場合
					} else if (toString(items[1]).equals("2")) {
						//***** [水相] *****

						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//新規原料
							super.ExcelSetValue("工程2_新規原料", toString(items[9]));
							//品コード
							super.ExcelSetValue("工程2_品コード", toString(items[10]));
							//品名
							super.ExcelSetValue("工程2_品名", toString(items[11]));
							//単価
							super.ExcelSetValue("工程2_単価", toDouble(items[12]));
							//歩留
							super.ExcelSetValue("工程2_歩留", (toDouble(items[13]) / 100));

						}

						//試作SEQごとに分岐
						if ( toString(items[38]).equals(strShisakuSeq1) ){
							//配合1
							super.ExcelSetValue("工程2_配合1", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq2) ){
							//配合2
							super.ExcelSetValue("工程2_配合2", toDouble(items[14]));

						} else if ( toString(items[38]).equals(strShisakuSeq3) ){
							//配合3
							super.ExcelSetValue("工程2_配合3", toDouble(items[14]));

						}

					}

					//原料情報
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//仕上がり合計重量1
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("仕上がり合計重量1", toDouble(items[37]));

						}

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//仕上がり合計重量2
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("仕上がり合計重量2", toDouble(items[37]));

						}

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//仕上がり合計重量3
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("仕上がり合計重量3", toDouble(items[37]));

						}

					}

					//ADD 2012.4.30　【H24年度対応】工場名(部署名)追加
					super.ExcelSetValue("工場", toString(items[43]));
					//製造方法
					super.ExcelSetValue("製造方法", toString(items[18]));
					//充填方法
					super.ExcelSetValue("充填方法", toString(items[19]));
					//殺菌方法
					super.ExcelSetValue("殺菌方法", toString(items[20]));
					//取扱温度
					super.ExcelSetValue("取扱温度", toString(items[21]));
					//容器・包材
					super.ExcelSetValue("容器・包材", toString(items[22]));
					//荷姿
					super.ExcelSetValue("荷姿", toString(items[23]));
					//内容量
					super.ExcelSetValue("内容量", toString(items[24]));
					//入り数
					super.ExcelSetValue("入り数", toString(items[25]));
					//賞味期間
					super.ExcelSetValue("賞味期間", toString(items[26]));
					// MOD 2013/11/14 okano【QP@30154】start
//						//製造ロット
//						super.ExcelSetValue("製造ロット", toString(items[53]));
//						//発売時期
//						super.ExcelSetValue("発売時期", toString(items[27]));
//						//想定物量
//						// MOD 2013/9/6 okano【QP@30151】No.30 start
//	//						super.ExcelSetValue("想定物量", toString(items[28]));
//						super.ExcelSetValue("想定物量", toDouble(items[28]));
//						super.ExcelSetValue("想定物量単位", toString(items[29])+toString(items[30]));
//						// MOD 2013/9/6 okano【QP@30151】No.30 end
//						//売価
//						super.ExcelSetValue("売価", toString(items[31]));
//						//希望原価
//						super.ExcelSetValue("希望原価", toString(items[32]));
//
//						//ADD 2012.4.30　【H24年度対応】販売期間  販売期間選択(通年orスポット)+販売期間+販売期間単位
//						super.ExcelSetValue("販売期間", toString(items[47]) + toString(items[46]) + toString(items[48]));
//						//ADD 2012.4.30　【H24年度対応】原価単位をそれぞれ原価、売価向けに設定。
//						super.ExcelSetValue("原価単位", toString(items[49]));
//						super.ExcelSetValue("売価単位", toString(items[49]));
					if ( toString(items[38]).equals(strShisakuSeq1) ){
						//製造ロット
						super.ExcelSetValue("製造ロット1", toString(items[53]));
						//発売時期
						super.ExcelSetValue("発売時期1", toString(items[27]));
						//想定物量
						super.ExcelSetValue("想定物量1", toDouble(items[28]));
						super.ExcelSetValue("想定物量単位1", toString(items[29])+toString(items[30]));
						//売価
						super.ExcelSetValue("売価1", toString(items[31]));
						//希望原価
						super.ExcelSetValue("希望原価1", toString(items[32]));

						//販売期間  販売期間選択(通年orスポット)+販売期間+販売期間単位
						super.ExcelSetValue("販売期間1", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//原価単位をそれぞれ原価、売価向けに設定。
						super.ExcelSetValue("原価単位1", toString(items[49]));
						super.ExcelSetValue("売価単位1", toString(items[49]));

						//ADD 2014/04/04 shima 【QP@30154】start
						super.ExcelSetValue("試算日1", toString(items[71]));
						//ADD 2014/04/04 shima 【QP@30154】end

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
						//製造ロット
						super.ExcelSetValue("製造ロット2", toString(items[53]));
						//発売時期
						super.ExcelSetValue("発売時期2", toString(items[27]));
						super.ExcelSetValue("想定物量2", toDouble(items[28]));
						super.ExcelSetValue("想定物量単位2", toString(items[29])+toString(items[30]));
						//売価
						super.ExcelSetValue("売価2", toString(items[31]));
						//希望原価
						super.ExcelSetValue("希望原価2", toString(items[32]));

						//販売期間  販売期間選択(通年orスポット)+販売期間+販売期間単位
						super.ExcelSetValue("販売期間2", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//原価単位をそれぞれ原価、売価向けに設定。
						super.ExcelSetValue("原価単位2", toString(items[49]));
						super.ExcelSetValue("売価単位2", toString(items[49]));

						//ADD 2014/04/04 shima 【QP@30154】start
						super.ExcelSetValue("試算日2", toString(items[71]));
						//ADD 2014/04/04 shima 【QP@30154】end

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
						//製造ロット
						super.ExcelSetValue("製造ロット3", toString(items[53]));
						//発売時期
						super.ExcelSetValue("発売時期3", toString(items[27]));
						super.ExcelSetValue("想定物量3", toDouble(items[28]));
						super.ExcelSetValue("想定物量単位3", toString(items[29])+toString(items[30]));
						//売価
						super.ExcelSetValue("売価3", toString(items[31]));
						//希望原価
						super.ExcelSetValue("希望原価3", toString(items[32]));

						//販売期間  販売期間選択(通年orスポット)+販売期間+販売期間単位
						super.ExcelSetValue("販売期間3", toString(items[47]) + toString(items[46]) + toString(items[48]));
						//原価単位をそれぞれ原価、売価向けに設定。
						super.ExcelSetValue("原価単位3", toString(items[49]));
						super.ExcelSetValue("売価単位3", toString(items[49]));

						//ADD 2014/04/04 shima 【QP@30154】start
						super.ExcelSetValue("試算日3", toString(items[71]));
						//ADD 2014/04/04 shima 【QP@30154】end

					}
					// MOD 2013/11/14 okano【QP@30154】end

					//DEL 2012.4.30　【H24年度対応】製品情報の変更
					//最新の注意事項を設定する
//					if ( !toString(items[37]).isEmpty() ) {
//
//						if ( intChuijiko < Integer.parseInt(toString(items[37])) ){
//							intChuijiko = Integer.parseInt(toString(items[37]));
//
//							//製品情報
//							super.ExcelSetValue("製品情報", toString(toString(items[31])));
//
//						}
//
//					}

					//最終試算用
					if ( toString(items[38]).equals(strShisakuSeq1) ){
//2009/11/27　isono　課題管理：119
						//平均充填量1（生産本部）32
//						super.ExcelSetValue("平均充填量1", toDouble(items[32]));
						super.ExcelSetValue("平均充填量1", toDouble(items[34])*1000);
						//有効歩留1（生産本部）33
						super.ExcelSetValue("有効歩留1", (toDouble(items[35]) / 100));
						//固定費1（生産本部）34
						super.ExcelSetValue("固定費1", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
						//固定項目チェックされているサンプル列の場合は、ＤＢ値を計算セルに上書き
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("レベル量(g)1", toDouble(items[59])*1000);
							super.ExcelSetValue("原料費/ケース1", toDouble(items[61]));
							super.ExcelSetValue("材料費/ケース1", toDouble(items[62]));
							super.ExcelSetValue("原価計/ケース1", toDouble(items[63]));
							super.ExcelSetValue("原価計/個1", toDouble(items[64]));
							super.ExcelSetValue("原料費/ＫＧ1", toDouble(items[65]));
							super.ExcelSetValue("材料費/ＫＧ1", toDouble(items[66]));
							super.ExcelSetValue("固定費/ＫＧ1", toDouble(items[70]));
							super.ExcelSetValue("原価計/ＫＧ1", toDouble(items[67]));
						//ADD 2014/04/22 start【QP@30154】追加
							super.ExcelSetValue("希望特約1", toDouble(items[68]));
							super.ExcelSetValue("粗利1", toDouble(items[69]));
						//ADD 2014/04/22 end 【QP@30154】追加
							//ADD 2014/05/05 start 【QP@30154】追加
							super.ExcelSetValue("項目固定チェック1", "↓項目固定サンプルNO");
							//ADD 2014/05/05 end 【QP@30154】追加
						}
						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

					} else if ( toString(items[38]).equals(strShisakuSeq2) ){
//2009/11/27　isono　課題管理：119
						//平均充填量2（生産本部）
//						super.ExcelSetValue("平均充填量2", toDouble(items[32]));
						super.ExcelSetValue("平均充填量2", toDouble(items[34])*1000);
						//有効歩留2（生産本部）
						super.ExcelSetValue("有効歩留2", (toDouble(items[35]) / 100));
						//固定費2（生産本部）
						super.ExcelSetValue("固定費2", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
						//固定項目チェックされているサンプル列の場合は、ＤＢ値を計算セルに上書き
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("レベル量(g)2", toDouble(items[59])*1000);
							super.ExcelSetValue("原料費/ケース2", toDouble(items[61]));
							super.ExcelSetValue("材料費/ケース2", toDouble(items[62]));
							super.ExcelSetValue("原価計/ケース2", toDouble(items[63]));
							super.ExcelSetValue("原価計/個2", toDouble(items[64]));
							super.ExcelSetValue("原料費/ＫＧ2", toDouble(items[65]));
							super.ExcelSetValue("材料費/ＫＧ2", toDouble(items[66]));
							super.ExcelSetValue("固定費/ＫＧ2", toDouble(items[70]));
							super.ExcelSetValue("原価計/ＫＧ2", toDouble(items[67]));
						//ADD 2014/04/22 start【QP@30154】追加
							super.ExcelSetValue("希望特約2", toDouble(items[68]));
							super.ExcelSetValue("粗利2", toDouble(items[69]));
						//ADD 2014/04/22 end 【QP@30154】追加
							//ADD 2014/05/05 start 【QP@30154】追加
							super.ExcelSetValue("項目固定チェック2", "↓項目固定サンプルNO");
							//ADD 2014/05/05 end 【QP@30154】追加
						}
						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

					} else if ( toString(items[38]).equals(strShisakuSeq3) ){
//2009/11/27　isono　課題管理：119
						//平均充填量3（生産本部）
//						super.ExcelSetValue("平均充填量3", toDouble(items[32]));
						super.ExcelSetValue("平均充填量3", toDouble(items[34])*1000);
						//有効歩留3（生産本部）
						super.ExcelSetValue("有効歩留3", (toDouble(items[35]) / 100));
						//固定費3（生産本部）
						super.ExcelSetValue("固定費3", toDouble(items[36]));

						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
						//固定項目チェックされているサンプル列の場合は、ＤＢ値を計算セルに上書き
						if(toString(items[54]).equals("1")){
							super.ExcelSetValue("レベル量(g)3", toDouble(items[59])*1000);
							super.ExcelSetValue("原料費/ケース3", toDouble(items[61]));
							super.ExcelSetValue("材料費/ケース3", toDouble(items[62]));
							super.ExcelSetValue("原価計/ケース3", toDouble(items[63]));
							super.ExcelSetValue("原価計/個3", toDouble(items[64]));
							super.ExcelSetValue("原料費/ＫＧ3", toDouble(items[65]));
							super.ExcelSetValue("材料費/ＫＧ3", toDouble(items[66]));
							super.ExcelSetValue("固定費/ＫＧ3", toDouble(items[70]));
							super.ExcelSetValue("原価計/ＫＧ3", toDouble(items[67]));
						//ADD 2014/04/22 start【QP@30154】追加
							super.ExcelSetValue("希望特約3", toDouble(items[68]));
							super.ExcelSetValue("粗利3", toDouble(items[69]));
						//ADD 2014/04/22 end 【QP@30154】追加
							//ADD 2014/05/05 start 【QP@30154】追加
							super.ExcelSetValue("項目固定チェック3", "↓項目固定サンプルNO");
							//ADD 2014/05/05 end 【QP@30154】追加
						}
						//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

					}

				}catch(ExceptionWaning e){
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;

				}finally{

				}

			}

			//資材情報をセットする

			for (int i = 0; i < lstRecset_Shizai.size(); i++) {

				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset_Shizai.get(i);

				// 20160524  KPX@1600766 ADD start
				//単価開示（全て）以外の時、単価・歩留を隠す
				if (flgTanka < 9) {
					items[9] = 0;
					items[10] = 0;
					items[11] = 0;
				}
				// 20160524  KPX@1600766 ADD end

				try{
					//Excelに値をセットする

					//資材_対象資材（工場記号）
					super.ExcelSetValue("資材_対象資材（工場記号）", toString(items[12]));
					//資材_資材コード
					super.ExcelSetValue("資材_資材コード", toString(items[7]));
					//資材_資材名
					super.ExcelSetValue("資材_資材名", toString(items[8]));
					//資材_単価
					super.ExcelSetValue("資材_単価", (toDouble(items[9])));
					//資材_歩留
					super.ExcelSetValue("資材_歩留", toDouble(items[10]) / 100);
					//資材_使用量
					super.ExcelSetValue("資材_使用量", toDouble(items[11]));

				}catch(ExceptionWaning e){
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;

				}finally{

				}

			}

			//DEL 2012.4.30　【H24年度対応】JEXCEL対応の為移動
//			//エクセルファイルをダウンロードフォルダに生成する
//			ret = super.ExcelOutput_genka(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
//					,hinNm
//					);

			//ADD 2012.4.30　【H24年度対応】JEXCEL対応の為追加
			super.ExcelWrite();
			super.close();

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			//変数の削除
			strShisakuSeq1 = null;
			strShisakuSeq2 = null;
			strShisakuSeq3 = null;
			intChuijiko = 0;

		}
		return ret;

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
			em.ThrowException(e, "原価試算表、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;

		}
		return ret;

	}
	/**
	 * 帳票種類判定
	 * @param KindBean : リクエストデータ（機能）
	 * @return String  : 帳票種類（1：調味料　2：調味料以外）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private String getSyurui(

			RequestResponsKindBean KindBean
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> listRisult = null;
		//SQL　StringBuffer
		StringBuffer strSQL = new StringBuffer();

		String ret = "";

		try {
			//SQL文の作成
			strSQL.append(" SELECT ");
			strSQL.append("  T120.cd_shain ");
			strSQL.append(" ,T120.nen ");
			strSQL.append(" ,T120.no_oi ");
			strSQL.append(" ,MAX(M302.value1) AS SYURUI ");
			strSQL.append(" FROM ");
			strSQL.append("           tr_haigo AS T120 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kote'  ");
			strSQL.append(" AND M302.cd_literal = T120.zoku_kotei ");
			strSQL.append(" WHERE ");
			strSQL.append("     T120.cd_shain = " + KindBean.getFieldVale("table", "rec", "cd_shain") + " ");
			strSQL.append(" AND T120.nen      = " + KindBean.getFieldVale("table", "rec", "nen") + " ");
			strSQL.append(" AND T120.no_oi    = " + KindBean.getFieldVale("table", "rec", "no_oi") + " ");
			strSQL.append(" GROUP BY  ");
			strSQL.append("  T120.cd_shain ");
			strSQL.append(" ,T120.nen ");
			strSQL.append(" ,T120.no_oi ");

			try{
				//SQLを実行
				listRisult = searchDB.dbSearch(strSQL.toString());

			}catch(ExceptionWaning ew){

			}
			if (listRisult == null){

			}else{
				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < listRisult.size(); i++) {

					//検索結果の1行分を取り出す
					Object[] items = (Object[]) listRisult.get(i);

					ret = toString(items[3], "1");

				}

			}

		} catch (Exception e) {
			em.ThrowException(e, "原価資材情報、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

		}
		return ret;

	}
	/**
	 * 対象の原価試算データ（資材）を検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData_shizai(

			RequestResponsKindBean KindBean
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> ret = null;
		//SQL　StringBuffer
		StringBuffer strSQL = new StringBuffer();

		try {
			//SQL文の作成
			strSQL.append(" SELECT ");
			strSQL.append("  T340.cd_shain ");		//0
			strSQL.append(" ,T340.nen ");			//1
			strSQL.append(" ,T340.no_oi ");			//2
			strSQL.append(" ,T340.seq_shizai ");	//3
			strSQL.append(" ,T340.no_sort ");		//4
			strSQL.append(" ,T340.cd_kaisha ");		//5
			strSQL.append(" ,T340.cd_busho  ");		//6
			strSQL.append(" ,T340.cd_shizai ");		//7
			strSQL.append(" ,T340.nm_shizai ");		//8
			strSQL.append(" ,T340.tanka ");			//9
			strSQL.append(" ,T340.budomari ");		//10
			strSQL.append(" ,T340.cs_siyou ");		//11
			strSQL.append(" ,M302.nm_literal ");	//12
//			strSQL.append(" ,ISNULL(T340.tanka, 0)/(ISNULL(T340.budomari, 0)/100)*ISNULL(T340.cs_siyou,0) AS KINGAKU ");//13
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shizai AS T340 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kigo_kojyo'  ");
			strSQL.append(" AND M302.cd_literal = CONVERT(varchar, T340.cd_kaisha) + '-' + CONVERT(varchar, T340.cd_busho) ");
			strSQL.append(" WHERE ");
			strSQL.append("     T340.cd_shain = " + KindBean.getFieldVale("table", "rec", "cd_shain") + " ");
			strSQL.append(" AND T340.nen      = " + KindBean.getFieldVale("table", "rec", "nen") + " ");
			strSQL.append(" AND T340.no_oi    = " + KindBean.getFieldVale("table", "rec", "no_oi") + " ");

			//【QP@00342】
			strSQL.append(" AND T340.no_eda    = " + KindBean.getFieldVale("table", "rec", "no_eda") + " ");

			strSQL.append(" ORDER BY ");
			strSQL.append("  T340.seq_shizai ");

			try{
				//SQLを実行
				ret = searchDB.dbSearch(strSQL.toString());

			}catch(ExceptionWaning ew){

			}

		} catch (Exception e) {
			em.ThrowException(e, "原価資材情報、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

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

			//【QP@00342】
			//試作品No　枝番		no_eda
			strNo_eda = reqData.getFieldVale(0, 0, "no_eda");

			// ADD 2015/06/17 E.kitazawa【QP@40812】  --- start
			//選択サンプル数を取得（テーブルBeanの件数）
			int reqCnt = ((RequestResponsTableBean)reqData.GetItem(0)).GetCnt();
			// ADD 2015/06/17 E.kitazawa【QP@40812】  --- end


			//SQL文の作成
			ret.append(" SELECT ");
			ret.append("   ISNULL(CONVERT(VARCHAR,M302K.value1),'') AS kotei1 ");	//0
			ret.append("  ,M302K.value2 AS kotei2 ");								//1

			//【H24年度対応】2012/05/08 TT H.SHIMA mod Start
//			ret.append("  ,T110.nm_hin AS hinmei ");
			ret.append("  ,CASE WHEN T310.nm_edaShisaku IS NOT NULL ");
			ret.append("  	THEN ");
			ret.append("  	CASE RTRIM(T310.nm_edaShisaku) WHEN '' ");
			ret.append("  	THEN T110.nm_hin ");
			ret.append("  	ELSE T110.nm_hin + ' 【' + T310.nm_edaShisaku + '】' ");
			ret.append("  	END ");
			ret.append("  	ELSE T110.nm_hin END AS hinmei ");						//2
			//【H24年度対応】2012/05/08 TT H.SHIMA mod end

			ret.append("  ,CONVERT(VARCHAR,T110.no_irai) AS no_irai ");				//3
			ret.append(" ,CONVERT(VARCHAR,YEAR(GETDATE())) + '年' ");
			ret.append("   + CONVERT(VARCHAR,MONTH(GETDATE())) + '月' ");
			ret.append("   + CONVERT(VARCHAR,DAY(GETDATE())) + '日' AS dt_hizuke ");//4
			ret.append("  ,T310.cd_kojo AS cd_kojo ");								//5● 工場コード
			ret.append("  ,M101.nm_user AS kenkyusho ");							//6


			//【QP@00342】
			//ret.append("  ,T110.cd_eigyo AS eigyo ");								//7
			ret.append("  ,M110.nm_user AS eigyo ");



			ret.append("  ,T131.nm_sample AS nm_sample ");							//8サンプルNo
//2009/11/27 isono UPD START 課題管理：123の対応
//			ret.append("  ,CASE ISNULL(LEFT(T120.cd_genryo,1),'') WHEN '' THEN ''  ");
//			ret.append("    WHEN 'N' THEN '☆' ELSE '' END AS new_genryo ");			//9●
			ret.append(" ,CASE ");
			ret.append("       WHEN LEFT(ISNULL(T120.cd_genryo, ' '), 1) = 'N'  THEN 'Ｎ' ");
			ret.append("       WHEN LEFT(ISNULL(T320.nm_genryo, ' '), 1) = '☆' THEN '☆' ");
			ret.append("       WHEN LEFT(ISNULL(T320.nm_genryo, ' '), 1) = '★' THEN '★' ");
			ret.append("       ELSE '' ");
			ret.append(" END AS new_genryo ");						//9●
//2009/11/27 isono UPD END   課題管理：123の対応
			ret.append("  ,T120.cd_genryo AS cd_genryo ");			//10
			ret.append("  ,T320.nm_genryo AS nm_genryo ");			//11●
			ret.append("  ,ISNULL(T320.tanka_ins,T320.tanka_ma) AS tanka ");//12●
			ret.append("  ,ISNULL(T320.budomari_ins,T320.budomar_ma) AS budomari ");//13●
			ret.append("  ,T132.quantity AS haigoryo ");			//14
			ret.append("  ,T141.zyusui AS zyuten_suiso ");			//15
			ret.append("  ,T141.zyuabura AS zyuten_yuso ");			//16
			ret.append("  ,T131.hiju AS hiju ");					//17
			ret.append("  ,M3021.nm_literal AS nm_seizohoho ");		//18
			ret.append("  ,M3022.nm_literal AS nm_juten ");			//19
			ret.append("  ,T110.hoho_sakin AS hoho_sakin ");		//20
			ret.append("  ,M3023.nm_literal AS nm_ondo ");			//21
			ret.append("  ,T110.youki AS youki ");					//22
			ret.append("  ,T310.cd_nisugata AS nm_nisugata ");		//23●
			ret.append("  ,T310.yoryo AS yoryo ");					//24
			ret.append("  ,T310.su_iri AS su_iri ");				//25●
			// CHG 2019/09/17 BRC.ida start
			//ret.append("  ,T110.shomikikan AS shomikikan ");		//26
			ret.append("  ,CASE WHEN T110.shomikikan IS NULL THEN NULL ");
			ret.append("   ELSE T110.shomikikan + M3024.nm_literal END AS shomikikan ");//26
			// CHG 2019/09/17 BRC.ida start			

			// MOD 2013/7/2 shima【QP@30151】No.37 start
//			ret.append("  ,T310.dt_hatubai AS dt_hatubai ");		//27●
//			ret.append("  ,T310.buturyo AS buturyo ");				//28●
//			ret.append("  ,T310.baika AS baika ");					//29●
//			ret.append("  ,T310.genka AS kibogenka ");				//30●
			ret.append("  ,T313_kihon_sub.dt_hatubai AS dt_hatubai ");//27●
			// MOD 2013/9/6 okano【QP@30151】No.30 start
//				ret.append("  ,T313_kihon_sub.buturyo AS buturyo ");
			ret.append("  ,T313_kihon_sub.buturyo_suti AS buturyo_s ");//28●
			ret.append("  ,M302_Buturyo_U.nm_literal AS buturyo_u ");//29●
			ret.append("  ,M302_Buturyo_K.nm_literal AS buturyo_k ");//30●
			// MOD 2013/9/6 okano【QP@30151】No.30 end
			ret.append("  ,T313_kihon_sub.baika AS baika ");		//31●
			ret.append("  ,T313_kihon_sub.genka AS kibogenka ");	//32●
			// MOD 2013/7/2 shima【QP@30151】No.37 end

			ret.append("  ,T133.chuijiko AS chuijiko ");			//33 製品情報
			ret.append("  ,T331.heikinjuten AS heikinzyu ");		//34●
			ret.append("  ,T331.budomari AS yukobudomari ");		//35●
			ret.append("  ,T331.cs_kotei AS koteihi ");				//36●
			ret.append("  ,T131.juryo_shiagari_g AS shiagarijuryo ");//37
			ret.append("  ,T131.seq_shisaku AS seq_shisaku ");		//38
			ret.append("  ,T131.no_chui AS no_chui ");				//39工程版(注意事項№)
			ret.append("  ,T120.sort_kotei AS sort_kotei ");		//40
			ret.append("  ,T120.sort_genryo AS sort_genryo ");		//41
			ret.append("  ,T131.no_shisan AS no_shisan ");			//42

			//ADD 2012.4.30　【H24年度対応】工場名(部署名、原価試算メモ、試作営業メモ追加、販売期間
			ret.append("  ,M104.nm_busho  AS nm_busho ");			//43工場名(部署名)
			ret.append("  ,T311.memo AS memo ");					//44原価試算メモ
			ret.append("  ,T312.memo_eigyo AS memo_eigyo ");		//45原価試算メモ(営業用)

			// MOD 2013/7/2 shima【QP@30151】No.37 start
//			ret.append("  ,T310.kikan_hanbai_suti AS kikan_hanbai_suti ");	//44販売期間
			ret.append("  ,T313_kihon_sub.kikan_hanbai_suti AS kikan_hanbai_suti ");//46販売期間
			// MOD 2013/7/2 shima【QP@30151】No.37 end

			//ADD 2012.4.30　【H24年度対応】販売期間、販売課期間(通年orスポット)、販売期間(単位)、原価単位
			ret.append("  ,M302_Hanbai_T.nm_literal AS kikan_hanbai_sen ");	//47販売期間選択(通年orスポット)
			ret.append("  ,M302_Hanbai_K.nm_literal AS kikan_hanbai_tani ");//48販売期間単位
			ret.append("  ,M302_TNI_G.nm_literal AS cd_genka_tani ");		//49原価単位

			//ADD 2012.4.30　【H24年度対応】ステータス変更履歴より、研究所、営業、生産管理部のおのおの最終更新担当者名を取得
			ret.append("  ,M101_nm_kenkyu.nm_user AS nm_kenkyu ");	//50研究所　最終アクセス担当者名
			ret.append("  ,M101_nm_eigyo.nm_user AS nm_eigyo ");	//51営業　最終アクセス担当者名
			ret.append("  ,M101_nm_seisan.nm_user AS nm_seisan ");	//52生産管理部　最終アクセス担当者名

			// MOD 2013/7/2 shima【QP@30151】No.37 start
//			ret.append("  ,T310.lot AS lot ");	                    //   51
			ret.append("  ,T313_kihon_sub.lot AS lot ");			//   53
			// MOD 2013/7/2 shima【QP@30151】No.37 end

			//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
			ret.append(" ,T331.fg_koumokuchk ");	//54 項目固定チェック
			ret.append(" ,T332.zyusui ");			//55 充填量水相（ｇ）
			ret.append(" ,T332.zyuabura ");			//56 充填量油相（ｇ）
			ret.append(" ,T332.gokei ");			//57 合計量（ｇ）
			ret.append(" ,T332.hiju ");				//58 比重
			ret.append(" ,T332.reberu ");			//59 レベル量（㎏）
			ret.append(" ,T332.hijukasan ");		//60 比重加算量（㎏）
			ret.append(" ,T332.cs_genryo ");		//61 原料費（円）/ケース
			ret.append(" ,T332.cs_zairyohi ");		//62 材料費（円）/ケース
			ret.append(" ,T332.cs_genka ");			//63 原価計（円）/ケース
			ret.append(" ,T332.ko_genka ");			//64 原価計（円）/個
			ret.append(" ,T332.kg_genryo ");		//65 原料費（円）/㎏
			ret.append(" ,T332.kg_zairyohi ");		//66 材料費（円）/㎏
			ret.append(" ,T332.kg_genka ");			//67 原価計（円）/㎏
			ret.append(" ,T332.baika ");			//68 売価
			ret.append(" ,T332.arari ");			//69 粗利（％）
			ret.append(" ,T331.kg_kotei ");			//70 固定費/ｋｇ
			//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

			// ADD 2014/04/04 shima 【QP@30154】start
			ret.append(" ,CONVERT(VARCHAR,T331.dt_shisan,111) dt_shisan ");	//71 試算日
			// ADD 2014/04/04 shima 【QP@30154】end

			ret.append(" FROM tr_shisakuhin T110 ");
			ret.append(" INNER JOIN tr_haigo T120 ");
			ret.append("  ON T110.cd_shain = T120.cd_shain ");
			ret.append("  AND T110.nen = T120.nen ");
			ret.append("  AND T110.no_oi = T120.no_oi ");
			ret.append(" INNER JOIN tr_shisaku T131 ");
			ret.append("  ON T110.cd_shain = T131.cd_shain ");
			ret.append("  AND T110.nen = T131.nen ");
			ret.append("  AND T110.no_oi = T131.no_oi ");
			ret.append(" INNER JOIN tr_shisaku_list T132 ");
			ret.append("  ON T120.cd_shain = T132.cd_shain ");
			ret.append("  AND T120.nen = T132.nen ");
			ret.append("  AND T120.no_oi = T132.no_oi ");
			ret.append("  AND T120.cd_kotei = T132.cd_kotei ");
			ret.append("  AND T120.seq_kotei = T132.seq_kotei ");
			ret.append("  AND T131.seq_shisaku = T132.seq_shisaku ");
			//製造方法名取得
			ret.append(" LEFT JOIN ma_literal M3021 ");
			ret.append("  ON M3021.cd_category = 'K_seizohoho' ");
			ret.append("  AND M3021.cd_literal = T110.cd_hoho ");
			//充填方法名取得
			ret.append(" LEFT JOIN ma_literal M3022 ");
			ret.append("  ON M3022.cd_category = 'K_jyutenhoho' ");
			ret.append("  AND M3022.cd_literal = T110.cd_juten ");
			//取扱温度名取得
			ret.append(" LEFT JOIN ma_literal M3023 ");
			ret.append("  ON M3023.cd_category = 'K_toriatukaiondo' ");
			ret.append("  AND M3023.cd_literal = T110.cd_ondo ");
			// ADD 2019/09/17 BRC.ida start
			//賞味期間単価取得
			ret.append(" LEFT JOIN ma_literal AS M3024 ");
			ret.append("  ON M3024.cd_category = '23' ");
			ret.append("  AND T110.shomikikan_tani = M3024.cd_literal ");
			// ADD 2019/09/17 BRC.ida end
			//荷姿名取得
			ret.append(" LEFT JOIN ma_user AS M101 ");
			ret.append("  ON id_user =" + strCd_shain);
			ret.append(" LEFT JOIN ma_literal AS M302K ");
			ret.append("  ON M302K.cd_category = 'K_kote' ");
			ret.append("  AND M302K.cd_literal = T120.zoku_kotei ");
			ret.append(" LEFT JOIN tr_genryo AS T141 ");
			ret.append("  ON T141.cd_shain = T131.cd_shain ");
			ret.append("  AND T141.nen = T131.nen ");
			ret.append("  AND T141.no_oi = T131.no_oi ");
			ret.append("  AND T141.seq_shisaku = T131.seq_shisaku ");
			ret.append(" LEFT JOIN tr_cyuui AS T133 ");
			ret.append("  ON T133.cd_shain = T131.cd_shain ");
			ret.append("  AND T133.nen = T131.nen ");
			ret.append("  AND T133.no_oi = T131.no_oi ");
			ret.append("  AND T133.no_chui = T131.no_chui ");
			//試算　試作品（T310）
			ret.append(" INNER JOIN tr_shisan_shisakuhin T310 ");
			ret.append("  ON T110.cd_shain = T310.cd_shain ");
			ret.append("  AND T110.nen = T310.nen ");
			ret.append("  AND T110.no_oi = T310.no_oi ");

			// ADD 2013/7/2 shima【QP@30151】No.37 start
			ret.append(" INNER JOIN ( ");
			// MOD 2013/11/14 okano 【QP@30154】start
//				ret.append("  SELECT TOP(1) T313_s.cd_shain ");
			ret.append("  SELECT T313_s.cd_shain ");
			// MOD 2013/11/14 okano 【QP@30154】end
			ret.append("   ,T313_s.nen ");
			ret.append("   ,T313_s.no_oi ");
			// ADD 2013/11/14 okano 【QP@30154】start
			ret.append("   ,T313_s.seq_shisaku ");
			// ADD 2013/11/14 okano 【QP@30154】end
			ret.append("   ,T313_s.lot ");
			// MOD 2013/9/6 okano【QP@30151】No.30 start
//				ret.append("   ,T313_s.buturyo ");
			ret.append("   ,T313_s.buturyo_suti ");
			ret.append("   ,T313_s.buturyo_seihin ");
			ret.append("   ,T313_s.buturyo_kikan ");
			// MOD 2013/9/6 okano【QP@30151】No.30 end
			ret.append("   ,T313_s.genka ");
			ret.append("   ,T313_s.baika ");
			ret.append("   ,T313_s.dt_hatubai ");
			ret.append("   ,T313_s.kikan_hanbai_suti ");
			ret.append("   ,T313_s.kikan_hanbai_sen ");
			ret.append("   ,T313_s.kikan_hanbai_tani ");
			ret.append("   ,T313_s.cd_genka_tani ");
			ret.append("  FROM ");
			ret.append("   tr_shisan_kihonjoho T313_s ");
			ret.append("  LEFT JOIN tr_shisaku T131_s ");
			ret.append("   ON T313_s.cd_shain = T131_s.cd_shain ");
			ret.append("   AND T313_s.nen = T131_s.nen ");
			ret.append("   AND T313_s.no_oi = T131_s.no_oi ");
			ret.append("   AND T313_s.seq_shisaku = T131_s.seq_shisaku ");
			ret.append("  WHERE ");
			ret.append("   T313_s.cd_shain = " + strCd_shain );
			ret.append("   AND T313_s.nen = " + strNen );
			ret.append("   AND T313_s.no_oi = " + strNo_oi );
			ret.append("   AND T313_s.no_eda = " + strNo_eda );
			//試作SEQ
			ret.append("  AND T313_s.seq_shisaku IN ( ");

			// MOD 2015/06/17 E.kitazawa【QP@40812】  --- start
//			for ( int i=0; i<3; i++ ) {
			for ( int i=0; i<reqCnt; i++ ) {
			// MOD 2015/06/17 E.kitazawa【QP@40812】  --- end

				//試作SEQの取得
				try{
					strShisakuSeq = toString(reqData.getFieldVale(0, i, "seq_shisaku"));

				}catch(Exception e){
					strShisakuSeq = "";

				}

				//試作SEQが空ではない場合、条件式を追加する
				if ( !strShisakuSeq.isEmpty() ) {

					if ( i != 0 ) {
						ret.append(" , ");

					}
					ret.append(strShisakuSeq);

				}

			}
			ret.append(" ) ");

			// DEL 2013/11/14 okano 【QP@30154】start
//				ret.append("  ORDER BY T131_s.sort_shisaku DESC ");
			// DEL 2013/11/14 okano 【QP@30154】end
			ret.append("  ) AS T313_kihon_sub ");
			// MOD 2013/11/14 okano 【QP@30154】start
//				ret.append("  ON T110.cd_shain = T313_kihon_sub.cd_shain ");
//				ret.append("  AND T110.nen = T313_kihon_sub.nen ");
//				ret.append("  AND T110.no_oi = T313_kihon_sub.no_oi ");
			ret.append("  ON T131.cd_shain = T313_kihon_sub.cd_shain ");
			ret.append("  AND T131.nen = T313_kihon_sub.nen ");
			ret.append("  AND T131.no_oi = T313_kihon_sub.no_oi ");
			ret.append("  AND T131.seq_shisaku = T313_kihon_sub.seq_shisaku ");
			// MOD 2013/11/14 okano 【QP@30154】end
			// ADD 2013/7/2 shima【QP@30151】No.37 end

			//試算　試作（T331）
			ret.append(" INNER JOIN tr_shisan_shisaku T331 ");
			ret.append("  ON T131.cd_shain = T331.cd_shain ");
			ret.append("  AND T131.nen = T331.nen ");
			ret.append("  AND T131.no_oi = T331.no_oi ");
			// ADD 2015/06/17 E.kitazawa【QP@40812】  --- start
			ret.append("  AND T310.no_eda = T331.no_eda ");
			// ADD 2015/06/17 E.kitazawa【QP@40812】  --- end
			ret.append("  AND T131.seq_shisaku = T331.seq_shisaku ");

			//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
			//試算　試作（固定チェック）（T332）
			ret.append(" LEFT JOIN tr_shisan_shisaku_kotei T332 ");
			ret.append(" ON T131.cd_shain = T332.cd_shain ");
			ret.append(" AND T131.nen = T332.nen ");
			ret.append(" AND T131.no_oi = T332.no_oi ");
			// ADD 2015/06/17 E.kitazawa【QP@40812】  --- start
			ret.append(" AND T310.no_eda = T332.no_eda ");
			// ADD 2015/06/17 E.kitazawa【QP@40812】  --- end
			ret.append(" AND T131.seq_shisaku = T332.seq_shisaku ");
			//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

			//試算　配合（T320）
			ret.append(" INNER JOIN tr_shisan_haigo T320 ");
			ret.append("  ON T120.cd_shain = T320.cd_shain ");
			ret.append("  AND T120.nen = T320.nen ");
			ret.append("  AND T120.no_oi = T320.no_oi ");
			ret.append("  AND T120.cd_kotei = T320.cd_kotei ");
			ret.append("  AND T120.seq_kotei = T320.seq_kotei ");

			//【QP@00342】
			ret.append(" LEFT JOIN ma_user AS M110 ");
			ret.append("  ON T110.cd_eigyo = M110.id_user ");

			//ADD 2012.4.30　【H24年度対応】工場名(部署名)、原価試算メモ、原価試算メモ(営業用)追加
			ret.append(" LEFT JOIN ma_busho AS M104 ");
			ret.append("  ON T310.cd_kaisha = M104.cd_kaisha ");
			ret.append("  AND T310.cd_kojo = M104.cd_busho ");
			ret.append(" LEFT JOIN tr_shisan_memo AS T311 ");
			ret.append("  ON T310.cd_shain = T311.cd_shain ");
			ret.append("  AND T310.nen = T311.nen ");
			ret.append("  AND T310.no_oi = T311.no_oi ");
			ret.append(" LEFT JOIN tr_shisan_memo_eigyo AS T312 ");
			ret.append("  ON T310.cd_shain = T312.cd_shain ");
			ret.append("  AND T310.nen = T312.nen ");
			ret.append("  AND T310.no_oi = T312.no_oi ");

			//ADD 2012.4.30　【H24年度対応】販売課期間(通年orスポット)、販売課期間(単位)、原価単位
			// MOD 2013/7/2 shima【QP@30151】No.37 start
			ret.append("  LEFT JOIN ma_literal AS M302_Hanbai_T ON ");
			ret.append("   'hanbai_kikan_sentaku' = M302_Hanbai_T.cd_category ");
//			ret.append("   AND T310.kikan_hanbai_sen = M302_Hanbai_T.cd_literal ");
			ret.append("   AND T313_kihon_sub.kikan_hanbai_sen = M302_Hanbai_T.cd_literal ");
			ret.append("  LEFT JOIN ma_literal AS M302_Hanbai_K ON ");
			ret.append("   'hanbai_kikan_tani' = M302_Hanbai_K.cd_category ");
//			ret.append("   AND T310.kikan_hanbai_tani = M302_Hanbai_K.cd_literal ");
			ret.append("   AND T313_kihon_sub.kikan_hanbai_tani = M302_Hanbai_K.cd_literal ");

			ret.append("  LEFT JOIN ma_literal AS M302_TNI_G ON ");
			ret.append("   'K_tani_genka' = M302_TNI_G.cd_category ");
//			ret.append("   AND T310.cd_genka_tani = M302_TNI_G.cd_literal ");
			ret.append("   AND T313_kihon_sub.cd_genka_tani = M302_TNI_G.cd_literal ");
			// MOD 2013/7/2 shima【QP@30151】No.37 end

			// ADD 2013/9/6 okano【QP@30151】No.30 start
			// 想定物量(製品単位)、想定物量(期間単位)
			ret.append("  LEFT JOIN ma_literal AS M302_Buturyo_U ON ");
			ret.append("   'sotei_buturyo_seihin' = M302_Buturyo_U.cd_category ");
			ret.append("   AND T313_kihon_sub.buturyo_seihin = M302_Buturyo_U.cd_literal ");
			ret.append("  LEFT JOIN ma_literal AS M302_Buturyo_K ON ");
			ret.append("   'sotei_buturyo_kikan' = M302_Buturyo_K.cd_category ");
			ret.append("   AND T313_kihon_sub.buturyo_kikan = M302_Buturyo_K.cd_literal ");
			// ADD 2013/9/6 okano【QP@30151】No.30 end

			//ADD 2012.4.30　【H24年度対応】ステータス変更履歴より、研究所、営業、生産管理部のおのおの最終更新担当者名を取得
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



			//試作SEQ1～3の配合量の取得
			// MOD 2015/06/17 E.kitazawa【QP@40812】  --- start
//			for ( int i=0; i<3; i++ ) {
			for ( int i=0; i<reqCnt; i++ ) {
			// MOD 2015/06/17 E.kitazawa【QP@40812】  --- end

				//試作SEQの取得	seq_shisaku
				try{
					strShisakuSeq = toString(reqData.getFieldVale(0, i, "seq_shisaku"));

				}catch(Exception e){
					strShisakuSeq = "";

				}

				//試作SEQが空ではない場合、配合量を取得する
				if ( !strShisakuSeq.isEmpty() ) {

					ret.append(" LEFT JOIN ( ");
					ret.append("  SELECT  ");
					ret.append("     T132s.cd_shain AS cd_shain ");
					ret.append("    ,T132s.nen AS nen ");
					ret.append("    ,T132s.no_oi AS no_oi ");
					ret.append("    ,T132s.cd_kotei AS cd_kotei ");
					ret.append("    ,T132s.seq_kotei AS seq_kotei ");
					ret.append("    ,T132s.quantity AS quantity ");
					ret.append("   FROM tr_shisaku_list T132s ");
					ret.append("   WHERE T132s.seq_shisaku = " + strShisakuSeq);
					ret.append(" ) T132_" + (i+1) );
					ret.append(" ON T120.cd_shain = T132_" + (i+1) + ".cd_shain ");
					ret.append(" AND T120.nen = T132_" + (i+1) + ".nen ");
					ret.append(" AND T120.no_oi = T132_" + (i+1) + ".no_oi ");
					ret.append(" AND T120.cd_kotei = T132_" + (i+1) + ".cd_kotei ");
					ret.append(" AND T120.seq_kotei = T132_" + (i+1) + ".seq_kotei ");

				}

			}

			//検索条件
			//【QP@00342】
//			ret.append(" WHERE T110.cd_shain = " + strCd_shain);
//			ret.append("  AND T110.nen = " + strNen);
//			ret.append("  AND T110.no_oi = " + strNo_oi);
			//絞り込み（試算　試作品）
			ret.append(" WHERE ");
			ret.append(" T310.cd_shain = " + strCd_shain);
			ret.append(" AND T310.nen = " + strNen);
			ret.append(" AND T310.no_oi = " + strNo_oi);
			ret.append(" AND T310.no_eda = " + strNo_eda);
			//絞り込み（試算　配合）
			ret.append(" AND T320.cd_shain = " + strCd_shain);
			ret.append(" AND T320.nen = " + strNen);
			ret.append(" AND T320.no_oi = " + strNo_oi);
			ret.append(" AND T320.no_eda = " + strNo_eda);
			//絞り込み（試算　試作）
			ret.append(" AND T331.cd_shain = " + strCd_shain);
			ret.append(" AND T331.nen = " + strNen);
			ret.append(" AND T331.no_oi = " + strNo_oi);
			ret.append(" AND T331.no_eda = " + strNo_eda);

			//試作SEQ
			ret.append("  AND T131.seq_shisaku IN ( ");

			// MOD 2015/06/17 E.kitazawa【QP@40812】  --- start
//			for ( int i=0; i<3; i++ ) {
			for ( int i=0; i<reqCnt; i++ ) {
			// MOD 2015/06/17 E.kitazawa【QP@40812】  --- end

				//試作SEQの取得
				try{
					strShisakuSeq = toString(reqData.getFieldVale(0, i, "seq_shisaku"));

				}catch(Exception e){
					strShisakuSeq = "";

				}

				//試作SEQが空ではない場合、条件式を追加する
				if ( !strShisakuSeq.isEmpty() ) {

					if ( i != 0 ) {
						ret.append(" , ");

					}
					ret.append(strShisakuSeq);

				}

			}
			ret.append(" ) ");

			//試作SEQから取得した、最大3つの配合量が、全て「0 or NULL」ではないかをチェック
			ret.append("  AND NOT ( ");
			// MOD 2015/06/17 E.kitazawa【QP@40812】  --- start
//			for ( int i=0; i<3; i++ ) {
			for ( int i=0; i<reqCnt; i++ ) {
			// MOD 2015/06/17 E.kitazawa【QP@40812】  --- end

				//試作SEQの取得
				try{
					strShisakuSeq = toString(reqData.getFieldVale(0, i, "seq_shisaku"));

				}catch(Exception e){
					strShisakuSeq = "";

				}

				//試作SEQが空ではない場合、条件式を追加する
				if ( !strShisakuSeq.isEmpty() ) {

					if ( i != 0 ) {
						ret.append(" AND ");

					}
					ret.append(" ISNULL(T132_" + (i+1) + ".quantity,0) = 0 ");

				}

			}
			ret.append(" ) ");

			ret.append("  AND M302K.value1 IN ( 1,2 ) ");
			ret.append("  AND M302K.value2 IN ( 1,2,3 ) ");
			ret.append("  ORDER BY ");
			ret.append("   T120.sort_kotei ");
			ret.append("   ,T120.sort_genryo ");
			ret.append("   ,T131.no_shisan ");

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算表、検索SQLの生成に失敗しました。");

		} finally {

		}
		return ret;

	}

// 20160524  KPX@1600766 ADD start
	/**
	 * 単価開示権限取得
	 * @param reqData : リクエストデータ（機能）
	 * @return int  : 単価開示権限 （9：単価開示（全て） 1：単価開示許可   0：単価開示不可）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int getTankaHyouji_kengen(RequestResponsKindBean reqData
			,UserInfoData _userInfoData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		int ret = 0;			//単価開示不可

		//ユーザー情報退避
		userInfoData = _userInfoData;

		//ユーザの部署フラグ取得
		FGEN2130_Logic flgBushoSearch = null;
		//検索結果
		RequestResponsKindBean resKind = null;

		//リテラルマスタ検索クラス
		LiteralDataSearchLogic clsLiteralSearch = null;
		//リテラルマスタリクエスト
		RequestResponsKindBean reqLiteral = null;
		//リテラルマスタ検索レスポンス
		RequestResponsKindBean resLiteral = null;

		//ユーザ部署フラグ（研究所）
		String flg_kenkyu = "";
		//単価開示フラグ（リテラルマスタ）
		String value1 = "";

		try {
			//ユーザの部署フラグ取得
			flgBushoSearch = new FGEN2130_Logic();
			resKind = flgBushoSearch.ExecLogic(reqData, userInfoData);
			if (resKind != null) {
				flg_kenkyu = toString(resKind.getFieldVale(0, 0, "flg_kenkyu"));
			}

		} catch (Exception e) {

		} finally {
			//変数の削除
			resKind = null;
		}

		if (flg_kenkyu.equals("")) {
			//研究所以外は単価開示制御はしない
			ret = 9;
		} else {
			try {
				//リクエストインスタンス
				reqLiteral = new RequestResponsKindBean();
				//リテラルマスタ検索リクエスト生成
				reqLiteral.addFieldVale("table", "rec", "cd_category", "K_tanka_hyoujigaisha");
				//会社コード
				reqLiteral.addFieldVale("table", "rec", "cd_literal", toString(reqData.getFieldVale(0, 0, "cd_kaisya")));

				//リテラルマスタ検索：単価開示権限
				clsLiteralSearch = new LiteralDataSearchLogic();
				resLiteral = clsLiteralSearch.ExecLogic(reqLiteral, userInfoData);

				if (resLiteral != null) {
					value1 = toString(resLiteral.getFieldVale(0, 0, "value1"));
				}

				if (value1.equals("1") || value1.equals("9")) {
					ret = Integer.parseInt(value1);		//単価開示許可、単価開示（全て）
				}

			} catch (Exception e) {
				//該当データ無しの時は開示不可
				ret = 0;

			} finally {
				//変数の削除
				reqLiteral = null;
				resLiteral = null;
			}
		}
		return ret;
	}

	/**
	 * 原価試算データを単価開示編集
	 * @param objItems : 原価試算データ1行分
	 * @param flg      : 単価開示権限 （9：単価開示（全て） 1：単価開示許可   0：単価開示不可）
	 * @return Object[]  : 原価試算データ
	 */
	private Object[] setObj(Object[] objItems ,int flg) {

		//単価開示（全て）の時、そのまま返す
		if (flg == 9)	return objItems;

		//非表示項目を0に置き換え
		//固定費
		objItems[36] = 0;
		//材料費/ケース
		objItems[62] = 0;
		//材料費/ＫＧ
		objItems[66] = 0;
		//固定費/ＫＧ
		objItems[70] = 0;

		//単価開示不可の場合
		if (flg == 0) {
			//原料単価
			objItems[12] = 0;
			//原料歩留
			objItems[13] = 0;

			//有効歩留
			objItems[35] = 0;
			//レベル量(ｇ)
			objItems[59] = 0;
			//平均充填量
			objItems[34] = 0;
			//比重加算量（ｇ）
			objItems[60] = 0;
			//原料費/ケース
			objItems[61] = 0;
			//原料費/ＫＧ
			objItems[65] = 0;
		}

		return objItems;
	}
// 20160524  KPX@1600766 ADD end

	/**
	 * 半角記号を全角に変換
	 * @param str : 変換対象文字列
	 * @return String  : 変換後文字列
	 */
	private String replaceSymbol(String str) {
		str = str.replace("%", "％");
		str = str.replace("=", "＝");
		str = str.replace("?", "？");
		str = str.replace("&", "＆");
		str = str.replace("#", "＃");

		return str;
	}
}