package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * 原価試算（営業）データを管理（登録・更新）するSQLを作成する。
 * 取得情報を、レスポンスデータ保持「機能ID：FGEN2160O」に設定する。
 *
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class FGEN2160_Logic extends LogicBase {

	/**
	 * 原価試算（営業）管理コンストラクタ ： インスタンス生成
	 */
	public FGEN2160_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 原価試算（営業）データ操作管理 ： 原価試算（営業）データの操作管理を行う。
	 *
	 * @param reqData
	 *            : リクエストデータ
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

		StringBuffer strSql = new StringBuffer();

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			String st_setting = null;
//ADD 2013/06/28 ogawa 【QP@30151】No.38 start
			String dsp_data = null;
//ADD 2013/06/28 ogawa 【QP@30151】No.38 end

			// トランザクション開始
			super.createExecDB();
			execDB.BeginTran();

			// ステータス設定の取得
			st_setting = reqData.getFieldVale(0, 0, "st_setting");

			// 共通DB更新（試算　試作品）
			strSql = statusKyotuUpdateSQL_sisan_sisakuhin(reqData);
			execDB.execSQL(strSql.toString()); // SQLを実行

			// ADD 2013/7/2 shima【QP@30151】No.37 start
			// サンプル毎DB更新(試作　基本情報)
			this.statusKihonSubUpdateSQL_sisan_kihonjoho(reqData);
			// ADD 2013/7/2 shima【QP@30151】No.37 end

			// 共通DB更新（試算メモ（営業連絡））
			strSql = statusKyotuUpdateSQL_eigyo_memo(reqData);
			execDB.execSQL(strSql.toString()); // SQLを実行

			//営業の現在ステータス取得
			int st_eigyo = Integer.parseInt(reqData.getFieldVale(0, 0, "st_eigyo"));

			// ステータス設定：仮保存
			if (st_setting.equals("0")) {

				//ステータス履歴挿入
				strSql = statusRirekiInsertSQL(reqData , st_eigyo , "0");
				execDB.execSQL(strSql.toString()); // SQLを実行
				//ADD 2015/07/28 TT.Kitazawa【QP@40812】No.6 start
				dsp_data = "RGEN2161";
				//ADD 2015/07/28 TT.Kitazawa【QP@40812】No.6 start
			}
			// ステータス設定：試算依頼
			else if (st_setting.equals("1")) {
				//ステータステーブル更新
				strSql = statusUpdateSQL(reqData , 2);
				execDB.execSQL(strSql.toString()); // SQLを実行

				//ステータス履歴挿入
				strSql = statusRirekiInsertSQL(reqData , 2 , "1");
				execDB.execSQL(strSql.toString()); // SQLを実行

//ADD 2013/06/28 ogawa 【QP@30151】No.38 start
				dsp_data = "RGEN2162";
//ADD 2013/06/28 ogawa 【QP@30151】No.38 end
			}
			// ステータス設定：確認完了
			else if (st_setting.equals("2")) {
				//ステータステーブル更新
				strSql = statusUpdateSQL(reqData , 3);
				execDB.execSQL(strSql.toString()); // SQLを実行

				//ステータス履歴挿入
				strSql = statusRirekiInsertSQL(reqData , 3 , "2");
				execDB.execSQL(strSql.toString()); // SQLを実行

//ADD 2013/06/28 ogawa 【QP@30151】No.38 start
				dsp_data = "RGEN2163";
//ADD 2013/06/28 ogawa 【QP@30151】No.38 end

//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				strSql = koteikomokuUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

			}
			// ステータス設定：採用有無確定（採用有り）
			else if (st_setting.equals("3")) {
				//ステータステーブル更新
				strSql = statusUpdateSQL(reqData , 4);
				execDB.execSQL(strSql.toString()); // SQLを実行

				//ステータス履歴挿入
				if(st_eigyo == 2){
					//試算依頼からの採用有無確定の場合は、「確認完了」ステータスを履歴に追加
					strSql = statusRirekiInsertSQL(reqData , 3 , "2");
					execDB.execSQL(strSql.toString()); // SQLを実行
				}
				Thread.sleep(1100);
				strSql = statusRirekiInsertSQL(reqData , 4 , "3");
				execDB.execSQL(strSql.toString()); // SQLを実行

				//採用ｻﾝﾌﾟﾙNo更新
				String saiyou = toString(reqData.getFieldVale(0, 0, "no_saiyou"));
				strSql = saiyouSampleUpdateSQL(reqData, saiyou);
				execDB.execSQL(strSql.toString()); // SQLを実行

//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				strSql = koteikomokuUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

			}
			// ステータス設定：採用有無確定（採用無し）
			else if (st_setting.equals("4")) {
				//ステータステーブル更新
				strSql = statusUpdateSQL(reqData , 4);
				execDB.execSQL(strSql.toString()); // SQLを実行

				//ステータス履歴挿入
				if(st_eigyo == 2){
					//試算依頼からの採用有無確定の場合は、「確認完了」ステータスを履歴に追加
					strSql = statusRirekiInsertSQL(reqData , 3 , "2");
					execDB.execSQL(strSql.toString()); // SQLを実行
				}
				Thread.sleep(1100);
				strSql = statusRirekiInsertSQL(reqData , 4 , "4");
				execDB.execSQL(strSql.toString()); // SQLを実行

				//採用ｻﾝﾌﾟﾙNo更新
				strSql = saiyouSampleUpdateSQL(reqData, "-1");
				execDB.execSQL(strSql.toString()); // SQLを実行

//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				strSql = koteikomokuUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
			}
			// ステータス設定：試算途中での不採用決定
			else if (st_setting.equals("5")) {
				//ステータステーブル更新
				strSql = statusUpdateSQL(reqData , 4);
				execDB.execSQL(strSql.toString()); // SQLを実行

				//ステータス履歴挿入
				strSql = statusRirekiInsertSQL(reqData , 4 , "5");
				execDB.execSQL(strSql.toString()); // SQLを実行

				//採用ｻﾝﾌﾟﾙNo更新
				strSql = saiyouSampleUpdateSQL(reqData, "-1");
				execDB.execSQL(strSql.toString()); // SQLを実行

//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				strSql = koteikomokuUpdateSQL(reqData);
				execDB.execSQL(strSql.toString()); // SQLを実行
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
			}

			// コミット
			execDB.Commit();

			// 機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
//MOD 2013/06/28 ogawa 【QP@30151】No.38 start
//修正前ソース
//			storageGroupDataKanri(resKind.getTableItem(strTableNm));
//修正後ソース
			storageGroupDataKanri(resKind.getTableItem(strTableNm), dsp_data);
//MOD 2013/06/28 ogawa 【QP@30151】No.38 end

		} catch (Exception e) {
			if (execDB != null) {
				// ロールバック
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");
		} finally {
			if (execDB != null) {
				// セッションのクローズ
				execDB.Close();
				execDB = null;
			}

			//変数の削除
			strSql = null;
		}
		return resKind;
	}

	/**
	 * 全ステータス共通SQL作成（試算　試作品テーブル）
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer statusKyotuUpdateSQL_sisan_sisakuhin(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			String dt_kizitu = toString( requestData.getFieldVale("kihon", 0, "dt_kizitu"));
			String yoryo = toString( requestData.getFieldVale("kihon", 0, "yoryo"), "", ",");
			String su_iri = toString( requestData.getFieldVale("kihon", 0, "su_iri"), "", ",");
			String nisugata = toString( requestData.getFieldVale("kihon", 0, "nisugata"));
			// DEL 2013/7/2 shima【QP@30151】No.37 start
//			String genka = toString( requestData.getFieldVale("kihon", 0, "genka"), "", ",");
//			String genka_tani = toString( requestData.getFieldVale("kihon", 0, "genka_tani"));
//			String baika = toString( requestData.getFieldVale("kihon", 0, "baika"), "", ",");
//			String sotei_buturyo = toString( requestData.getFieldVale("kihon", 0, "sotei_buturyo"));
//			String dt_hatubai = toString( requestData.getFieldVale("kihon", 0, "dt_hatubai"));
//			String hanbai_t = toString( requestData.getFieldVale("kihon", 0, "hanbai_t"));
//			String hanbai_s = toString( requestData.getFieldVale("kihon", 0, "hanabai_s"));
//			String hanabai_k = toString( requestData.getFieldVale("kihon", 0, "hanabai_k"));
//			String keikakuuriage = toString( requestData.getFieldVale("kihon", 0, "keikakuuriage"));
//			String keikakurieki = toString( requestData.getFieldVale("kihon", 0, "keikakurieki"));
//			String kuhaku_1 = toString( requestData.getFieldVale("kihon", 0, "kuhaku_1"));
//			String kuhaku_2 = toString( requestData.getFieldVale("kihon", 0, "kuhaku_2"));
			// DEL 2013/7/2 shima【QP@30151】No.37 end

			if(dt_kizitu.equals("")){
				dt_kizitu = "NULL";
			}
			else{
				dt_kizitu = "'" + dt_kizitu + "'";
			}

			// DEL 2013/7/2 shima【QP@30151】No.37 start
//			if(hanbai_s.equals("")){
//				hanbai_s = "NULL";
//			}
			// DEL 2013/7/2 shima【QP@30151】No.37 end

			// SQL文の作成
			strInsSql.append(" UPDATE tr_shisan_shisakuhin ");
			strInsSql.append("    SET  ");
			strInsSql.append("        dt_kizitu = " + dt_kizitu + " ");
			strInsSql.append("       ,yoryo = '" + yoryo + "' ");
			strInsSql.append("       ,su_iri = '" + su_iri + "' ");
			strInsSql.append("       ,cd_nisugata = '" + nisugata + "' ");
			// DEL 2013/7/2 shima【QP@30151】No.37 start
//			strInsSql.append("       ,genka = '" + genka + "' ");
//			strInsSql.append("       ,baika = '" + baika + "' ");
//			strInsSql.append("       ,cd_genka_tani = '" + genka_tani + "' ");
//			strInsSql.append("       ,buturyo = '" + sotei_buturyo + "' ");
//			strInsSql.append("       ,dt_hatubai = '" + dt_hatubai + "' ");
//			strInsSql.append("       ,kikan_hanbai_sen = '" + hanbai_t + "' ");
//			strInsSql.append("       ,kikan_hanbai_suti = " + hanbai_s + " ");
//			strInsSql.append("       ,kikan_hanbai_tani = '" + hanabai_k + "' ");
//			strInsSql.append("       ,uriage_k = '" + keikakuuriage + "' ");
//			strInsSql.append("       ,rieki_k = '" + keikakurieki + "' ");
//			strInsSql.append("       ,uriage_h = '" + kuhaku_1 + "' ");
//			strInsSql.append("       ,rieki_h = '" + kuhaku_2 + "' ");
			// DEL 2013/7/2 shima【QP@30151】No.37 end
			strInsSql.append("       ,id_toroku =  " + userInfoData.getId_user());
			strInsSql.append("       ,dt_toroku = GETDATE() ");
			strInsSql.append("  WHERE  ");
			strInsSql.append(" 	  cd_shain =  " + cd_shain);
			strInsSql.append("       AND nen =  " + nen);
			strInsSql.append("       AND no_oi =  " + no_oi);
			strInsSql.append("       AND no_eda =  " + no_eda);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	// ADD 2013/7/2 shima【QP@30151】No.37 start
	/**
	 * 全ステータス共通SQL作成（試算　基本情報サブテーブル）
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void statusKihonSubUpdateSQL_sisan_kihonjoho(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));

			for(int i = 0;i < requestData.getCntRow("kihonsub"); i++){

				String genka = toString( requestData.getFieldVale("kihonsub", i, "genka"), "", ",");
				String genka_tani = toString( requestData.getFieldVale("kihonsub", i, "genka_tani"));
				String baika = toString( requestData.getFieldVale("kihonsub", i, "baika"), "", ",");
				// ADD 2013/9/6 okano【QP@30151】No.30 start
				// MOD 2013/12/4 okano【QP@30154】start
//					String sotei_buturyo_s = toString( requestData.getFieldVale("kihonsub", i, "sotei_buturyo_s"));
				String sotei_buturyo_s = toString( requestData.getFieldVale("kihonsub", i, "sotei_buturyo_s"), "", ",");
				// MOD 2013/12/4 okano【QP@30154】end
				String sotei_buturyo_u = toString( requestData.getFieldVale("kihonsub", i, "sotei_buturyo_u"));
				String sotei_buturyo_k = toString( requestData.getFieldVale("kihonsub", i, "sotei_buturyo_k"));
				// ADD 2013/9/6 okano【QP@30151】No.30 end
				String sotei_buturyo = toString( requestData.getFieldVale("kihonsub", i, "sotei_buturyo"));
				String dt_hatubai = toString( requestData.getFieldVale("kihonsub", i, "dt_hatubai"));
				String hanbai_t = toString( requestData.getFieldVale("kihonsub", i, "hanbai_t"));
				String hanbai_s = toString( requestData.getFieldVale("kihonsub", i, "hanabai_s"));
				String hanabai_k = toString( requestData.getFieldVale("kihonsub", i, "hanabai_k"));
				String keikakuuriage = toString( requestData.getFieldVale("kihonsub", i, "keikakuuriage"));
				String keikakurieki = toString( requestData.getFieldVale("kihonsub", i, "keikakurieki"));
				String kuhaku_1 = toString( requestData.getFieldVale("kihonsub", i, "kuhaku_1"));
				String kuhaku_2 = toString( requestData.getFieldVale("kihonsub", i, "kuhaku_2"));


				if(hanbai_s.equals("")){
					hanbai_s = "NULL";
				}

				// ADD 2013/9/6 okano【QP@30151】No.30 start
				if(sotei_buturyo_s.equals("")){
					sotei_buturyo_s = "NULL";
				}
				// ADD 2013/9/6 okano【QP@30151】No.30 end

				// SQL文の作成
				strInsSql.append(" UPDATE tr_shisan_kihonjoho ");
				strInsSql.append("    SET  ");
				strInsSql.append("       genka = '" + genka + "' ");
				strInsSql.append("       ,baika = '" + baika + "' ");
				strInsSql.append("       ,cd_genka_tani = '" + genka_tani + "' ");
				// ADD 2013/9/6 okano【QP@30151】No.30 start
				strInsSql.append("       ,buturyo_suti = " + sotei_buturyo_s + " ");
				strInsSql.append("       ,buturyo_seihin = '" + sotei_buturyo_u + "' ");
				strInsSql.append("       ,buturyo_kikan = '" + sotei_buturyo_k + "' ");
				// ADD 2013/9/6 okano【QP@30151】No.30 end
				strInsSql.append("       ,buturyo = '" + sotei_buturyo + "' ");
				strInsSql.append("       ,dt_hatubai = '" + dt_hatubai + "' ");
				strInsSql.append("       ,kikan_hanbai_sen = '" + hanbai_t + "' ");
				strInsSql.append("       ,kikan_hanbai_suti = " + hanbai_s + " ");
				strInsSql.append("       ,kikan_hanbai_tani = '" + hanabai_k + "' ");
				strInsSql.append("       ,uriage_k = '" + keikakuuriage + "' ");
				strInsSql.append("       ,rieki_k = '" + keikakurieki + "' ");
				strInsSql.append("       ,uriage_h = '" + kuhaku_1 + "' ");
				strInsSql.append("       ,rieki_h = '" + kuhaku_2 + "' ");
				strInsSql.append("       ,id_koshin =  " + userInfoData.getId_user());
				strInsSql.append("       ,dt_koshin = GETDATE() ");
				strInsSql.append("  WHERE  ");
				strInsSql.append(" 	  cd_shain =  " + cd_shain);
				strInsSql.append("       AND nen =  " + nen);
				strInsSql.append("       AND no_oi =  " + no_oi);
				strInsSql.append("       AND no_eda =  " + no_eda);
				strInsSql.append("       AND seq_shisaku = " + toString( requestData.getFieldVale("kihonsub", i, "seq_shisaku")));

				this.execDB.execSQL(strInsSql.toString());
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算登録[kihonsub]情報の更新SQL作成処理が失敗しました。");
		} finally {
			//変数の削除
			strInsSql = null;

			//セッションのクローズ
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}

		}
	}
	// ADD 2013/7/2 shima【QP@30151】No.37 end

	/**
	 * 全ステータス共通SQL作成（試算メモ（営業連絡用）テーブル）
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer statusKyotuUpdateSQL_eigyo_memo(
			RequestResponsKindBean requestData) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String memo_eigyo = toString( requestData.getFieldVale("kihon", 0, "memo_eigyo"));

			// SQL文の作成
			strInsSql.append(" UPDATE tr_shisan_memo_eigyo ");
			strInsSql.append("    SET  ");
			strInsSql.append("       memo_eigyo = '" + memo_eigyo + "' ");
			strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
			strInsSql.append("       ,dt_koshin = GETDATE()");
			strInsSql.append("  WHERE ");
			strInsSql.append(" 	  cd_shain = " + cd_shain);
			strInsSql.append("       AND nen = " + nen);
			strInsSql.append("       AND no_oi = " + no_oi);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * ステータス更新SQL作成（ステータステーブル）
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer statusUpdateSQL(
				RequestResponsKindBean requestData
				,int Status_Eigyo
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));

			// SQL文の作成
			strInsSql.append(" UPDATE tr_shisan_status ");
			strInsSql.append("    SET  ");
			strInsSql.append("        st_eigyo = " + Status_Eigyo);
			strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
			strInsSql.append("       ,dt_koshin = GETDATE()");
			strInsSql.append("  WHERE ");
			strInsSql.append(" 	  cd_shain =  " + cd_shain);
			strInsSql.append("       AND nen =  " + nen);
			strInsSql.append("       AND no_oi =  " + no_oi);
			strInsSql.append("       AND no_eda =  " + no_eda);


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * ステータス履歴更新SQL作成（ステータス履歴テーブル）
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer statusRirekiInsertSQL(
				RequestResponsKindBean requestData
				,int Status_Eigyo
				,String cd_literal
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			//リクエストデータ取得
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));
			String st_kenkyu = toString( requestData.getFieldVale("kihon", 0, "st_kenkyu"));
			String st_seisan = toString( requestData.getFieldVale("kihon", 0, "st_seikan"));
			String st_gensizai = toString( requestData.getFieldVale("kihon", 0, "st_gentyo"));
			String st_kojo = toString( requestData.getFieldVale("kihon", 0, "st_kojo"));


			// SQL文の作成
			strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
			strInsSql.append("            (cd_shain ");
			strInsSql.append("            ,nen ");
			strInsSql.append("            ,no_oi ");
			strInsSql.append("            ,no_eda ");
			strInsSql.append("            ,dt_henkou ");
			strInsSql.append("            ,cd_kaisha ");
			strInsSql.append("            ,cd_busho ");
			strInsSql.append("            ,id_henkou ");
			strInsSql.append("            ,cd_zikko_ca ");
			strInsSql.append("            ,cd_zikko_li ");
			strInsSql.append("            ,st_kenkyu ");
			strInsSql.append("            ,st_seisan ");
			strInsSql.append("            ,st_gensizai ");
			strInsSql.append("            ,st_kojo ");
			strInsSql.append("            ,st_eigyo ");
			strInsSql.append("            ,id_toroku ");
			strInsSql.append("            ,dt_toroku) ");
			strInsSql.append("      VALUES ");
			strInsSql.append("            (" + cd_shain + " ");
			strInsSql.append("            ," + nen + " ");
			strInsSql.append("            ," + no_oi + " ");
			strInsSql.append("            ," + no_eda +" ");
			strInsSql.append("            ,GETDATE() ");
			strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
			strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
			strInsSql.append("            ," + userInfoData.getId_user() + " ");
			strInsSql.append("            ,'wk_eigyo'");
			strInsSql.append("            ,'" + cd_literal +"' ");
			strInsSql.append("            ," + st_kenkyu + " ");
			strInsSql.append("            ," + st_seisan + " ");
			strInsSql.append("            ," + st_gensizai + " ");
			strInsSql.append("            ," + st_kojo + " ");
			strInsSql.append("            ," + Status_Eigyo + " ");
			strInsSql.append("            ," + userInfoData.getId_user() + " ");
			strInsSql.append("            ,GETDATE() )");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * 採用ｻﾝﾌﾟﾙNo更新SQL作成
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer saiyouSampleUpdateSQL(
				RequestResponsKindBean requestData
				,String No_Saiyou
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));

			// SQL文の作成
			strInsSql.append(" UPDATE tr_shisan_shisakuhin ");
			strInsSql.append("    SET  ");
			strInsSql.append("       saiyo_sample = " + No_Saiyou + " ");
			strInsSql.append("       ,id_koshin = " + userInfoData.getId_user() + " ");
			strInsSql.append("       ,dt_koshin = GETDATE() ");
			strInsSql.append("  WHERE  ");
			strInsSql.append(" 	  cd_shain =  " + cd_shain);
			strInsSql.append("       AND nen =  " + nen);
			strInsSql.append("       AND no_oi =  " + no_oi);
			strInsSql.append("       AND no_eda =  " + no_eda);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

	/**
	 * 管理結果パラメーター格納
	 *  : 担当者データの管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
//MOD 2013/06/28 ogawa 【QP@30151】No.38 start
//修正前ソース
//	private void storageGroupDataKanri(RequestResponsTableBean resTableo)
//修正後ソース
	private void storageGroupDataKanri(RequestResponsTableBean resTable, String msgno)
//MOD 2013/06/28 ogawa 【QP@30151】No.38 end
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
//ADD 2013/06/28 ogawa 【QP@30151】No.38 start
			resTable.addFieldVale(0, "msg_cd", msgno);
//ADD 2013/06/28 ogawa 【QP@30151】No.38 end

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	/**
	 * ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11
	 * 固定項目一括更新SQL（ステータステーブル）
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer koteikomokuUpdateSQL(
				RequestResponsKindBean requestData
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		StringBuffer strInsSql = new StringBuffer();
		try {

			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));

			// SQL文の作成
			strInsSql.append(" UPDATE tr_shisan_shisaku ");
			strInsSql.append("    SET  ");
			strInsSql.append("        fg_koumokuchk = 1");
			strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
			strInsSql.append("       ,dt_koshin = GETDATE()");
			strInsSql.append("  WHERE ");
			strInsSql.append(" 	  cd_shain =  " + cd_shain);
			strInsSql.append("       AND nen =  " + nen);
			strInsSql.append("       AND no_oi =  " + no_oi);
			strInsSql.append("       AND no_eda =  " + no_eda);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strInsSql;
	}

}


