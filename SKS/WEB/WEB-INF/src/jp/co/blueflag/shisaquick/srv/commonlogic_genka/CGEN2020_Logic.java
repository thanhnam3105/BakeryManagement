package jp.co.blueflag.shisaquick.srv.commonlogic_genka;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * 原価試算情報更新
 *  : 原価試算画面の情報を登録
 *
 * @author TT.Y.Nishigawa
 * @since  2009/10/28
 *
 */
public class CGEN2020_Logic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public CGEN2020_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic（処理開始）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 原価試算情報 管理操作
	 * @param reqData : 機能リクエストデータ
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
//ADD 2013/06/28 ogawa【QP@30151】No.38 Start
		String msgcd = "";
//ADD 2013/06/28 ogawa【QP@30151】No.38 end

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();


			// トランザクション開始
			super.createExecDB();
			execDB.BeginTran();

			try {
				//【QP@00342】
				//権限取得
				ArrayList<?> aryGamen = userInfoData.getId_gamen();
				ArrayList<?> aryKino = userInfoData.getId_kino();
				// ADD 2013/11/5 QP@30154 okano start
				ArrayList<?> aryData = userInfoData.getId_data();
				String DataID = null;
				// ADD 2013/11/5 QP@30154 okano end

				//権限ループ（原価試算画面の権限を探索）
				for(int i=0; i<aryGamen.size(); i++){
					//画面ID取得
					String gamenId = toString(aryGamen.get(i));

					//原価試算画面ID
					if(aryGamen.get(i).equals("170")){

						//機能ID取得
						String kinouID = toString(aryKino.get(i));
						// ADD 2013/11/5 QP@30154 okano start
						DataID = toString(aryData.get(i));
						// ADD 2013/11/5 QP@30154 okano end

						//編集の場合
						if(kinouID.equals("20")){
							//原価試算登録[kihon]情報の更新処理を行う
							this.torokuKihonUpdateSQL(reqData);

							//原価試算登録[memo]情報の更新処理を行う
							this.torokuMemoUpdateSQL(reqData);

							//原価試算登録[genryo]情報の更新処理を行う
							this.torokuGenryoUpdateSQL(reqData);

							//原価試算登録[keisan]情報の更新処理を行う
							this.torokuKeisanUpdateSQL(reqData);

							//原価試算登録[shizai]情報の削除、登録処理を行う
							this.torokuShizaiDeleteSQL(reqData);
							this.torokuShizaiInsertSQL(reqData);

							// ADD 2013/7/2 shima【QP@30151】No.37 start
							//原価試算登録[kihonsub]情報の更新処理を行う
							this.torokuKihonSubUpdateSQL(reqData);
							// ADD 2013/7/2 shima【QP@30151】No.37 end
						}
						//閲覧の場合
						else if(kinouID.equals("70")){
							//原価試算登録[memo]情報の更新処理を行う
							this.torokuMemoUpdateSQL(reqData);
						}
					}
				}

				//ステータス、ステータス履歴更新
//MOD 2013/06/28 ogawa【QP@30151】No.38 Start
//修正前ソース
//				statusUpdateExec(reqData);
//修正後ソース
				// MOD 2013/11/5 QP@30154 okano start
//					msgcd = statusUpdateExec(reqData);
				msgcd = statusUpdateExec(reqData, DataID);
				// MOD 2013/11/5 QP@30154 okano end
//MOD 2013/06/28 ogawa【QP@30151】No.38 end

				// コミット
				execDB.Commit();

			} catch(Exception e) {
				// ロールバック
				execDB.Rollback();
				this.em.ThrowException(e, "");

			} finally {

			}

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//正常終了時、管理結果パラメーター格納メソッドを呼出し、レスポンスデータを形成する。
//MOD 2013/06/28 ogawa【QP@30151】No.38 Start
//修正前ソース
//			this.storageGenkaTorokuDataKanri(resKind.getTableItem(strTableNm));
//修正後ソース
			this.storageGenkaTorokuDataKanri(resKind.getTableItem(strTableNm), msgcd);
//MOD 2013/06/28 ogawa【QP@30151】No.38 end


		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算情報 管理操作処理が失敗しました。");

		} finally {
			if (execDB != null) {
				execDB.Close();				//セッションのクローズ
				execDB = null;
			}
		}
		return resKind;
	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  ExecDB（DB更新）
	//
	//--------------------------------------------------------------------------------------------------------------
	/**【QP@00342】
	 * ステータス更新（ステータス、ステータス履歴テーブル）
	 * @param reqData：リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
//MOD 2013/06/28 ogawa【QP@30151】No.38 Start
//修正前ソース
//	private void statusUpdateExec(
//修正後ソース
	private String statusUpdateExec(
//MOD 2013/06/28 ogawa【QP@30151】No.38 end
				RequestResponsKindBean requestData
				// ADD 2013/11/5 QP@30154 okano start
				,String DataID
				// ADD 2013/11/5 QP@30154 okano end
			) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

//ADD 2013/06/28 ogawa【QP@30151】No.38 Start
		String MsgCd = "";
//ADD 2013/06/28 ogawa【QP@30151】No.38 end

		StringBuffer strInsSql = new StringBuffer();
		try {

			//試作NO取得
			String cd_shain = toString( requestData.getFieldVale("kihon", 0, "cd_shain"));
			String nen = toString( requestData.getFieldVale("kihon", 0, "nen"));
			String no_oi = toString( requestData.getFieldVale("kihon", 0, "no_oi"));
			String no_eda = toString( requestData.getFieldVale("kihon", 0, "no_eda"));

			//選択ステータス取得
			String setting = requestData.getFieldVale("kihon", 0, "setting");

			//現在ステータス取得
			String st_kenkyu = requestData.getFieldVale("kihon", 0, "st_kenkyu");
			String st_seikan = requestData.getFieldVale("kihon", 0, "st_seikan");
			String st_gentyo = requestData.getFieldVale("kihon", 0, "st_gentyo");
			String st_kojo = requestData.getFieldVale("kihon", 0, "st_kojo");
			String st_eigyo = requestData.getFieldVale("kihon", 0, "st_eigyo");

			//所属部署フラグ取得
			String kenkyu = requestData.getFieldVale("kihon", 0, "busho_kenkyu");
			String seikan = requestData.getFieldVale("kihon", 0, "busho_seikan");
			String gentyo = requestData.getFieldVale("kihon", 0, "busho_gentyo");
			String kojo = requestData.getFieldVale("kihon", 0, "busho_kojo");
			String eigyo = requestData.getFieldVale("kihon", 0, "busho_eigyo");

			//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
			// ステータス履歴Insertパラメータ作成用配列
			// ユーザ情報 （会社、部署、ユーザID）
			String aryUsrInf[] = {userInfoData.getCd_kaisha(), userInfoData.getCd_busho(), userInfoData.getId_user()};
			// 試作No （社員、年、追番、枝番）
			String aryShisakuNo[] = {cd_shain, nen, no_oi, no_eda};
			// 設定ステータス（初期値：現在のステータス）
			String aryStatus[] = {st_kenkyu, st_seikan, st_gentyo, st_kojo, st_eigyo};
			//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

			//研究所------------------------------------------------------------------------
			if(kenkyu.equals("1")){
				//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
				// 工場変更
				if(setting.equals("9")){
					// 研究所：仮保存（工場変更）
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kenkyu", "9");
					this.execDB.execSQL(strInsSql.toString());
				} else {
				// settingが "0"以外の時も「仮保存」で履歴を追加する（ステータス設定でﾗｼﾞｵﾎﾞﾀﾝがないので、setting=undefined）
				//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

				//仮保存
				//if(setting.equals("0")){
					//ステータステーブル更新
					//更新しない

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_kenkyu'");
//					strInsSql.append("            ,'0' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 研究所：仮保存
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kenkyu", "0");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
			}

			//生産管理部----------------------------------------------------------------------
			else if(seikan.equals("1")){
				//仮保存
				if(setting.equals("0")){
					//ステータステーブル更新
					//更新しない

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_seisan'");
//					strInsSql.append("            ,'0' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 生産管理部：仮保存
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "0");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());

				//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
				// 工場変更
				} else if(setting.equals("9")){
					// 生産管理部：仮保存（工場変更）
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "9");
					this.execDB.execSQL(strInsSql.toString());
				//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

				}
				//試算依頼
				else if(setting.equals("1")){

					//ステータステーブル更新
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_seisan = 2");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_seisan'");
//					strInsSql.append("            ,'1' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ,2 ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 生産管理部：試算依頼
					aryStatus[1] = "2";		//生管
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "1");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());

//ADD 2013/06/28 ogawa【QP@30151】No.38 Start
					MsgCd = "RGEN0042";
//ADD 2013/06/28 ogawa【QP@30151】No.38 end
				}
				//確認完了
				else if(setting.equals("2")){
					// ADD 2013/11/5 QP@30154 okano start
					// 画面権限（170）、DataId==1 （グループ（生管））⇒ 原調、工場のｽﾃｰﾀｽ を「完了」に
					if(DataID.equals("1")){
						//ステータステーブル更新
						strInsSql.append(" UPDATE tr_shisan_status ");
						strInsSql.append("    SET  ");
						strInsSql.append("        st_seisan   = 3");
						strInsSql.append("       ,st_gensizai = 2");
						strInsSql.append("       ,st_kojo     = 2");
						strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
						strInsSql.append("       ,dt_koshin = GETDATE()");
						strInsSql.append("  WHERE ");
						strInsSql.append(" 	  cd_shain =  " + cd_shain);
						strInsSql.append("       AND nen =  " + nen);
						strInsSql.append("       AND no_oi =  " + no_oi);
						strInsSql.append("       AND no_eda =  " + no_eda);
						this.execDB.execSQL(strInsSql.toString());


						strInsSql = null;
						strInsSql = new StringBuffer();

						//ステータス履歴追加
						//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//						strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//						strInsSql.append("            (cd_shain ");
//						strInsSql.append("            ,nen ");
//						strInsSql.append("            ,no_oi ");
//						strInsSql.append("            ,no_eda ");
//						strInsSql.append("            ,dt_henkou ");
//						strInsSql.append("            ,cd_kaisha ");
//						strInsSql.append("            ,cd_busho ");
//						strInsSql.append("            ,id_henkou ");
//						strInsSql.append("            ,cd_zikko_ca ");
//						strInsSql.append("            ,cd_zikko_li ");
//						strInsSql.append("            ,st_kenkyu ");
//						strInsSql.append("            ,st_seisan ");
//						strInsSql.append("            ,st_gensizai ");
//						strInsSql.append("            ,st_kojo ");
//						strInsSql.append("            ,st_eigyo ");
//						strInsSql.append("            ,id_toroku ");
//						strInsSql.append("            ,dt_toroku) ");
//						strInsSql.append("      VALUES ");
//						strInsSql.append("            (" + cd_shain + " ");
//						strInsSql.append("            ," + nen + " ");
//						strInsSql.append("            ," + no_oi + " ");
//						strInsSql.append("            ," + no_eda +" ");
//						strInsSql.append("            ,GETDATE() ");
//						strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//						strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,'wk_seisan'");
//						strInsSql.append("            ,'2' ");
//						strInsSql.append("            ," + st_kenkyu + " ");
//						strInsSql.append("            ,3 ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ," + st_eigyo + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,GETDATE() )");
						//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

						//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
						// 生産管理部：確認完了
						aryStatus[1] = "3";		//生管
						aryStatus[2] = "2";		//原調
						aryStatus[3] = "2";		//工場
						strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "2");
						//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

						this.execDB.execSQL(strInsSql.toString());
					} else {
					// ADD 2013/11/5 QP@30154 okano end
					//ステータステーブル更新
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_seisan = 3");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_seisan'");
//					strInsSql.append("            ,'2' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ,3 ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 生産管理部：確認完了
					aryStatus[1] = "3";		//生管
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "2");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());
					// ADD 2013/11/5 QP@30154 okano start
					}
					// ADD 2013/11/5 QP@30154 okano end

					//20160607  KPX@1502111_No7 ADD start
					strInsSql = null;
					strInsSql = new StringBuffer();
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
					this.execDB.execSQL(strInsSql.toString());
					//20160607  KPX@1502111_No7 ADD end

//ADD 2013/06/28 ogawa【QP@30151】No.38 Start
					MsgCd = "RGEN0043";
//ADD 2013/06/28 ogawa【QP@30151】No.38 end

				}
				//確認取消し
				else if(setting.equals("3")){
					//ステータステーブル更新
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_seisan = 2");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_seisan'");
//					strInsSql.append("            ,'3' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ,2 ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 生産管理部：確認取消し
					aryStatus[1] = "2";		//生管
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_seisan", "3");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());
				}
			}

			//原資材調達部---------------------------------------------------------------------
			else if(gentyo.equals("1")){
				//仮保存
				if(setting.equals("0")){
					//ステータステーブル更新
					//更新しない

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_genshizai'");
//					strInsSql.append("            ,'0' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 原資材調達部：仮保存
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_genshizai", "0");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
				//確認完了
				else if(setting.equals("2")){
					// ADD 2013/11/5 QP@30154 okano start
					if(DataID.equals("2")){
						//ステータステーブル更新
						strInsSql.append(" UPDATE tr_shisan_status ");
						strInsSql.append("    SET  ");
						strInsSql.append("        st_gensizai = 2");
						strInsSql.append("       ,st_kojo     = 2");
						strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
						strInsSql.append("       ,dt_koshin = GETDATE()");
						strInsSql.append("  WHERE ");
						strInsSql.append(" 	  cd_shain =  " + cd_shain);
						strInsSql.append("       AND nen =  " + nen);
						strInsSql.append("       AND no_oi =  " + no_oi);
						strInsSql.append("       AND no_eda =  " + no_eda);
						this.execDB.execSQL(strInsSql.toString());


						strInsSql = null;
						strInsSql = new StringBuffer();

						//ステータス履歴追加
						//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//						strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//						strInsSql.append("            (cd_shain ");
//						strInsSql.append("            ,nen ");
//						strInsSql.append("            ,no_oi ");
//						strInsSql.append("            ,no_eda ");
//						strInsSql.append("            ,dt_henkou ");
//						strInsSql.append("            ,cd_kaisha ");
//						strInsSql.append("            ,cd_busho ");
//						strInsSql.append("            ,id_henkou ");
//						strInsSql.append("            ,cd_zikko_ca ");
//						strInsSql.append("            ,cd_zikko_li ");
//						strInsSql.append("            ,st_kenkyu ");
//						strInsSql.append("            ,st_seisan ");
//						strInsSql.append("            ,st_gensizai ");
//						strInsSql.append("            ,st_kojo ");
//						strInsSql.append("            ,st_eigyo ");
//						strInsSql.append("            ,id_toroku ");
//						strInsSql.append("            ,dt_toroku) ");
//						strInsSql.append("      VALUES ");
//						strInsSql.append("            (" + cd_shain + " ");
//						strInsSql.append("            ," + nen + " ");
//						strInsSql.append("            ," + no_oi + " ");
//						strInsSql.append("            ," + no_eda +" ");
//						strInsSql.append("            ,GETDATE() ");
//						strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//						strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,'wk_genshizai'");
//						strInsSql.append("            ,'1' ");
//						strInsSql.append("            ," + st_kenkyu + " ");
//						strInsSql.append("            ," + st_seikan + " ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ," + st_eigyo + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,GETDATE() )");
						//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

						//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
						// 原資材調達部：確認完了
						aryStatus[2] = "2";		//原調
						aryStatus[3] = "2";		//工場
						strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_genshizai", "1");
						//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

						this.execDB.execSQL(strInsSql.toString());
					} else {
					// ADD 2013/11/5 QP@30154 okano end
					//ステータステーブル更新
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_gensizai = 2");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_genshizai'");
//					strInsSql.append("            ,'1' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ,2 ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 原資材調達部：確認完了
					aryStatus[2] = "2";		//原調
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_genshizai", "1");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());
					// ADD 2013/11/5 QP@30154 okano start
					}
					// ADD 2013/11/5 QP@30154 okano end

//ADD 2013/06/28 ogawa【QP@30151】No.38 Start
					MsgCd = "RGEN0045";
//ADD 2013/06/28 ogawa【QP@30151】No.38 end

				}
				//確認取消し
				else if(setting.equals("3")){
					//ステータステーブル更新
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_gensizai = 1");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_genshizai'");
//					strInsSql.append("            ,'2' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ,1 ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 原資材調達部：確認取消し
					aryStatus[3] = "1";		//原調
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_genshizai", "2");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
			}

			//工場-------------------------------------------------------------------------
			else if(kojo.equals("1")){
				//仮保存
				if(setting.equals("0")){
					//ステータステーブル更新
					//更新しない

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_kojo'");
//					strInsSql.append("            ,'0' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 工場：仮保存
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kojo", "0");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
				//確認完了
				else if(setting.equals("2")){
					// ADD 2013/11/5 QP@30154 okano start
					if(DataID.equals("2")){
						//ステータステーブル更新
						strInsSql.append(" UPDATE tr_shisan_status ");
						strInsSql.append("    SET  ");
						strInsSql.append("        st_gensizai = 2");
						strInsSql.append("       ,st_kojo     = 2");
						strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
						strInsSql.append("       ,dt_koshin = GETDATE()");
						strInsSql.append("  WHERE ");
						strInsSql.append(" 	  cd_shain =  " + cd_shain);
						strInsSql.append("       AND nen =  " + nen);
						strInsSql.append("       AND no_oi =  " + no_oi);
						strInsSql.append("       AND no_eda =  " + no_eda);
						this.execDB.execSQL(strInsSql.toString());


						strInsSql = null;
						strInsSql = new StringBuffer();

						//ステータス履歴追加
						//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//						strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//						strInsSql.append("            (cd_shain ");
//						strInsSql.append("            ,nen ");
//						strInsSql.append("            ,no_oi ");
//						strInsSql.append("            ,no_eda ");
//						strInsSql.append("            ,dt_henkou ");
//						strInsSql.append("            ,cd_kaisha ");
//						strInsSql.append("            ,cd_busho ");
//						strInsSql.append("            ,id_henkou ");
//						strInsSql.append("            ,cd_zikko_ca ");
//						strInsSql.append("            ,cd_zikko_li ");
//						strInsSql.append("            ,st_kenkyu ");
//						strInsSql.append("            ,st_seisan ");
//						strInsSql.append("            ,st_gensizai ");
//						strInsSql.append("            ,st_kojo ");
//						strInsSql.append("            ,st_eigyo ");
//						strInsSql.append("            ,id_toroku ");
//						strInsSql.append("            ,dt_toroku) ");
//						strInsSql.append("      VALUES ");
//						strInsSql.append("            (" + cd_shain + " ");
//						strInsSql.append("            ," + nen + " ");
//						strInsSql.append("            ," + no_oi + " ");
//						strInsSql.append("            ," + no_eda +" ");
//						strInsSql.append("            ,GETDATE() ");
//						strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//						strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,'wk_kojo'");
//						strInsSql.append("            ,'1' ");
//						strInsSql.append("            ," + st_kenkyu + " ");
//						strInsSql.append("            ," + st_seikan + " ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ,2 ");
//						strInsSql.append("            ," + st_eigyo + " ");
//						strInsSql.append("            ," + userInfoData.getId_user() + " ");
//						strInsSql.append("            ,GETDATE() )");
						//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

						//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
						// 工場：確認完了
						aryStatus[2] = "2";		// 原調
						aryStatus[3] = "2";		// 工場
						strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kojo", "1");
						//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

						this.execDB.execSQL(strInsSql.toString());
					} else {
					// ADD 2013/11/5 QP@30154 okano end
					//ステータステーブル更新
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_kojo = 2");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_kojo'");
//					strInsSql.append("            ,'1' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ,2 ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 工場：確認完了
					aryStatus[3] = "2";		//工場
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kojo", "1");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());
					// ADD 2013/11/5 QP@30154 okano start
					}
					// ADD 2013/11/5 QP@30154 okano end

//ADD 2013/06/28 ogawa【QP@30151】No.38 Start
					MsgCd = "RGEN0044";
//ADD 2013/06/28 ogawa【QP@30151】No.38 end

				}
				//確認取消し
				else if(setting.equals("3")){
					//ステータステーブル更新
					strInsSql.append(" UPDATE tr_shisan_status ");
					strInsSql.append("    SET  ");
					strInsSql.append("        st_kojo = 1");
					strInsSql.append("       ,id_koshin = " + userInfoData.getId_user());
					strInsSql.append("       ,dt_koshin = GETDATE()");
					strInsSql.append("  WHERE ");
					strInsSql.append(" 	  cd_shain =  " + cd_shain);
					strInsSql.append("       AND nen =  " + nen);
					strInsSql.append("       AND no_oi =  " + no_oi);
					strInsSql.append("       AND no_eda =  " + no_eda);
					this.execDB.execSQL(strInsSql.toString());


					strInsSql = null;
					strInsSql = new StringBuffer();

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_kojo'");
//					strInsSql.append("            ,'2' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ,1 ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 工場：確認取消し
					aryStatus[3] = "1";		//工場
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_kojo", "2");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
			}

			//営業-------------------------------------------------------------------------
			else if(eigyo.equals("1")){
				//仮保存
				if(setting.equals("0")){
					//ステータステーブル更新
					//更新しない

					//ステータス履歴追加
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
//					strInsSql.append(" INSERT INTO tr_shisan_status_rireki ");
//					strInsSql.append("            (cd_shain ");
//					strInsSql.append("            ,nen ");
//					strInsSql.append("            ,no_oi ");
//					strInsSql.append("            ,no_eda ");
//					strInsSql.append("            ,dt_henkou ");
//					strInsSql.append("            ,cd_kaisha ");
//					strInsSql.append("            ,cd_busho ");
//					strInsSql.append("            ,id_henkou ");
//					strInsSql.append("            ,cd_zikko_ca ");
//					strInsSql.append("            ,cd_zikko_li ");
//					strInsSql.append("            ,st_kenkyu ");
//					strInsSql.append("            ,st_seisan ");
//					strInsSql.append("            ,st_gensizai ");
//					strInsSql.append("            ,st_kojo ");
//					strInsSql.append("            ,st_eigyo ");
//					strInsSql.append("            ,id_toroku ");
//					strInsSql.append("            ,dt_toroku) ");
//					strInsSql.append("      VALUES ");
//					strInsSql.append("            (" + cd_shain + " ");
//					strInsSql.append("            ," + nen + " ");
//					strInsSql.append("            ," + no_oi + " ");
//					strInsSql.append("            ," + no_eda +" ");
//					strInsSql.append("            ,GETDATE() ");
//					strInsSql.append("            ," + userInfoData.getCd_kaisha() + " ");
//					strInsSql.append("            ," + userInfoData.getCd_busho() + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,'wk_eigyo'");
//					strInsSql.append("            ,'0' ");
//					strInsSql.append("            ," + st_kenkyu + " ");
//					strInsSql.append("            ," + st_seikan + " ");
//					strInsSql.append("            ," + st_gentyo + " ");
//					strInsSql.append("            ," + st_kojo + " ");
//					strInsSql.append("            ," + st_eigyo + " ");
//					strInsSql.append("            ," + userInfoData.getId_user() + " ");
//					strInsSql.append("            ,GETDATE() )");
					//DEL 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
					// 営業：仮保存
					strInsSql = MakeStatusRirekiSQLBuf(aryUsrInf, aryShisakuNo, aryStatus, "wk_eigyo", "0");
					//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

					this.execDB.execSQL(strInsSql.toString());

				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strInsSql = null;

		}

//ADD 2013/06/28 ogawa【QP@30151】No.38 Start
		return MsgCd;
//ADD 2013/06/28 ogawa【QP@30151】No.38 end
	}

//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
	/**【QP@40812】
	 * リクエストデータからの設定値より、ステータス履歴INSERT SQLを生成する
	 * @param argUserInfo   : ユーザ情報  [0]cd_kaisha [1]cd_busho [2]id_user
	 * @param argSisakuNo   : 試作No      [0]cd_shain  [1]nen [2]no_oi [3]no_eda
	 * @param argStatus     : ステータス  [0]st_kenkyu [1]st_seikan [2]st_gentyo [3]st_kojo [4]st_eigyo
	 * @param argZikkoCa    : 作業種別（部署）
	 * @param argZikkoLi    : 作業詳細業務（リテラルコード）
	 * @return StringBuffer : InsertSQL
	 */
	private StringBuffer MakeStatusRirekiSQLBuf(String[] argUserInfo, String[] argSisakuNo, String[] argStatus
			, String argZikkoCa, String argZikkoLi) {
		// 戻り値の宣言
		StringBuffer ret = new StringBuffer();

		//ステータス履歴追加SQL 作成
		ret.append(" INSERT INTO tr_shisan_status_rireki ");
		ret.append("            (cd_shain ");
		ret.append("            ,nen ");
		ret.append("            ,no_oi ");
		ret.append("            ,no_eda ");
		ret.append("            ,dt_henkou ");
		ret.append("            ,cd_kaisha ");
		ret.append("            ,cd_busho ");
		ret.append("            ,id_henkou ");
		ret.append("            ,cd_zikko_ca ");
		ret.append("            ,cd_zikko_li ");
		ret.append("            ,st_kenkyu ");
		ret.append("            ,st_seisan ");
		ret.append("            ,st_gensizai ");
		ret.append("            ,st_kojo ");
		ret.append("            ,st_eigyo ");
		ret.append("            ,id_toroku ");
		ret.append("            ,dt_toroku) ");
		ret.append("      VALUES ");
		ret.append("            (" + argSisakuNo[0] + " ");
		ret.append("            ," + argSisakuNo[1] + " ");
		ret.append("            ," + argSisakuNo[2] + " ");
		ret.append("            ," + argSisakuNo[3] +" ");
		ret.append("            ,GETDATE() ");
		ret.append("            ," + argUserInfo[0] + " ");
		ret.append("            ," + argUserInfo[1] + " ");
		ret.append("            ," + argUserInfo[2] + " ");
		ret.append("            ,'" + argZikkoCa + "'");
		ret.append("            ,'" + argZikkoLi + "'");
		ret.append("            ," + argStatus[0] + " ");
		ret.append("            ," + argStatus[1] + " ");
		ret.append("            ," + argStatus[2] + " ");
		ret.append("            ," + argStatus[3] + " ");
		ret.append("            ," + argStatus[4] + " ");
		ret.append("            ," + argUserInfo[2] + " ");
		ret.append("            ,GETDATE() )");

		return ret;
	}
//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

	/**【QP@00342】
	 * 原価試算登録[memo]情報の更新処理
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuMemoUpdateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//リクエストデータより試作コード取得
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//【QP@00342】
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");

			//【QP@00342】
			//原価試算メモの更新
			StringBuffer strSQL_memo = new StringBuffer();
			strSQL_memo.append(" UPDATE tr_shisan_memo ");
			strSQL_memo.append("    SET memo = '" + toString( reqData.getFieldVale( "kihon", 0, "memo_genkashisan"),"") + "'");
			strSQL_memo.append("       ,id_koshin = " + userInfoData.getId_user());
			strSQL_memo.append("       ,dt_koshin = GETDATE() ");
			strSQL_memo.append("  WHERE  ");
			strSQL_memo.append("	cd_shain = "    + strReqShainCd);
			strSQL_memo.append("    AND nen = "   + strReqNen);
			strSQL_memo.append("    AND no_oi = " + strReqOiNo);
			this.execDB.execSQL(strSQL_memo.toString());

			//原価試算メモ（営業連絡用）の更新
			StringBuffer strSQL_memo_eigyo = new StringBuffer();
			strSQL_memo_eigyo.append(" UPDATE tr_shisan_memo_eigyo ");
			strSQL_memo_eigyo.append("    SET memo_eigyo = '" + toString( reqData.getFieldVale( "kihon", 0, "memo_genkashisan_eigyo"),"") + "'");
			strSQL_memo_eigyo.append("       ,id_koshin = " + userInfoData.getId_user());
			strSQL_memo_eigyo.append("       ,dt_koshin = GETDATE() ");
			strSQL_memo_eigyo.append("  WHERE  ");
			strSQL_memo_eigyo.append("	cd_shain = "    + strReqShainCd);
			strSQL_memo_eigyo.append("    AND nen = "   + strReqNen);
			strSQL_memo_eigyo.append("    AND no_oi = " + strReqOiNo);
			this.execDB.execSQL(strSQL_memo_eigyo.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算登録[memo]情報の更新SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

		}
	}


	/**
	 * 原価試算登録[kihon]情報の更新処理
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuKihonUpdateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQLSearch = new StringBuffer();
		List<?> lstRecset = null;

		try {

			//リクエストデータより試作コード取得
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//【QP@00342】
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");

			//【QP@00342】
//			//--------------------- 更新箇所の検索（生産管理、原資材調達、工場） ---------------------
//			String id_seisanKanri = "";  		//生産管理更新者
//			String dt_seisanKanri = "";   	//生産管理更新日
//			String id_genshizai = "";			//原資材調達更新者
//			String dt_genshizai = "";      	//原資材調達更新日
//			String id_kojo = "";				//工場更新者
//			String dt_kojo = "";				//工場更新日
//
//			//検索SQL文生成
//			strSQLSearch = new StringBuffer();
//			strSQLSearch.append(" SELECT DISTINCT cd_category ");
//			strSQLSearch.append(" FROM ma_literal ");
//			strSQLSearch.append(" WHERE ");
//			strSQLSearch.append("  (cd_category = 'K_Busho_Seisankanri' ");
//			strSQLSearch.append("   AND value1 = '" + userInfoData.getCd_kaisha() + "' ");
//			strSQLSearch.append("   AND value2 = '" + userInfoData.getCd_busho() + "') ");
//			strSQLSearch.append(" OR ");
//			strSQLSearch.append(" (cd_category = 'K_Busho_Genshizai' ");
//			strSQLSearch.append("  AND value1 = '" + userInfoData.getCd_kaisha() + "' ");
//			strSQLSearch.append("  AND value2 = '" + userInfoData.getCd_busho() + "') ");
//			strSQLSearch.append(" OR ");
//			strSQLSearch.append(" (cd_category = 'K_Busho_Kojyo' ");
//			strSQLSearch.append("  AND value1 = '" + userInfoData.getCd_kaisha() + "' ");
//			strSQLSearch.append("  AND value2 = '" + userInfoData.getCd_busho() + "') ");
//
//			//検索実行
//			createSearchDB();
//			lstRecset = this.searchDB.dbSearch(strSQLSearch.toString());
//
//			//検索結果にて各更新日、更新者設定
//			for( int i=0; i<lstRecset.size(); i++ ){
//
//				//生産管理の場合
//				if( toString(lstRecset.get(i)).equals("K_Busho_Seisankanri") ){
//					id_seisanKanri = userInfoData.getId_user();
//					dt_seisanKanri = "GETDATE()";
//				}
//
//				//原資材調達の場合
//				if( toString(lstRecset.get(i)).equals("K_Busho_Genshizai") ){
//					id_genshizai = userInfoData.getId_user();
//					dt_genshizai = "GETDATE()";
//				}
//
//				//工場の場合
//				if( toString(lstRecset.get(i)).equals("K_Busho_Kojyo") ){
//					id_kojo = userInfoData.getId_user();
//					dt_kojo = "GETDATE()";
//				}
//
//			}
//
//			//-------------------------------- サンプルNO確定更新者 -------------------------------
//			String sam_id_koshin = "";
//
//			//検索用SQL生成
//			strSQLSearch = new StringBuffer();
//			strSQLSearch.append(" SELECT saiyo_sample ");
//			strSQLSearch.append(" FROM tr_shisan_shisakuhin ");
//			strSQLSearch.append(" WHERE  ");
//			strSQLSearch.append(" 	cd_shain = " + strReqShainCd );
//			strSQLSearch.append(" 	AND nen = " + strReqNen );
//			strSQLSearch.append(" 	AND no_oi = " + strReqOiNo );
//			//【QP@00342】
//			strSQLSearch.append(" 	AND no_eda = " + strReqEdaNo );
//
//			//検索実行
//			createSearchDB();
//			lstRecset = this.searchDB.dbSearch(strSQLSearch.toString());
//
//			//サンプルNOの変更がない場合
//			if( toString(lstRecset.get(0)).equals(toString( reqData.getFieldVale( "kihon", 0, "no_sanpuru"))) ){
//
//			}
//			//サンプルNOの変更がある場合
//			else{
//				sam_id_koshin = userInfoData.getId_user();
//
//			}



			//更新用SQL作成
			//【QP@00342】
//			strSQL.append(" UPDATE tr_shisan_shisakuhin");
//			strSQL.append("   SET ");
//			strSQL.append("        saiyo_sample = " + toString( reqData.getFieldVale( "kihon", 0, "no_sanpuru"),"null"));
//			//strSQL.append("       ,cd_kaisha = "      + toString( reqData.getFieldVale( "kihon", 0, "cd_kaisya"),"null"));
//			//strSQL.append("       ,cd_kojo = "         + toString( reqData.getFieldVale( "kihon", 0, "cd_kojyo"),"null"));
//			strSQL.append("       ,su_iri = '"           + toString( reqData.getFieldVale( "kihon", 0, "irisu"),"",",") + "'");
//			strSQL.append("       ,cd_nisugata = '"   + toString( reqData.getFieldVale( "kihon", 0, "nisugata"),"") + "'");
//			strSQL.append("       ,genka = '"          + toString( reqData.getFieldVale( "kihon", 0, "kibo_genka"),"",",") + "'");
//			strSQL.append("       ,cd_genka_tani = '" + toString (reqData.getFieldVale( "kihon", 0, "kibo_genka_cd_tani"),"") + "'");
//			strSQL.append("       ,baika = '"           + toString( reqData.getFieldVale( "kihon", 0, "kibo_baika"),"",",") + "'");
//			strSQL.append("       ,buturyo = '"         + toString( reqData.getFieldVale( "kihon", 0, "butu_sotei"),"") + "'");
//			strSQL.append("       ,dt_hatubai = '"     + toString( reqData.getFieldVale( "kihon", 0, "ziki_hanbai"),"") + "'");
//			strSQL.append("       ,uriage_k = '"        + toString( reqData.getFieldVale( "kihon", 0, "keikaku_uriage"),"") + "'");
//			strSQL.append("       ,rieki_k = '"          + toString( reqData.getFieldVale( "kihon", 0, "keikaku_rieki"),"") + "'");
//			strSQL.append("       ,uriage_h = '"        + toString( reqData.getFieldVale( "kihon", 0, "hanbaigo_uriage"),"") + "'");
//			strSQL.append("       ,rieki_h = '"          + toString( reqData.getFieldVale( "kihon", 0, "hanbaigo_rieki"),"") + "'");
//			strSQL.append("       ,lot = '"                + toString( reqData.getFieldVale( "kihon", 0, "seizo_roto"),"") + "'");
//			strSQL.append("       ,memo = '"           + toString( reqData.getFieldVale( "kihon", 0, "memo_genkashisan"),"") + "'");
//			strSQL.append("      ,fg_keisan = "        + toString( reqData.getFieldVale( "kihon", 0, "ragio_kesu_kg"),"null"));
//			strSQL.append("      ,id_koshin = " + userInfoData.getId_user());
//			strSQL.append("     ,dt_koshin = GETDATE()");
//
//			if(!id_seisanKanri.equals("")){
//				strSQL.append("     ,sei_id_koshin = " + toString( id_seisanKanri , "null" ) );
//				strSQL.append("     ,sei_dt_koshin = " + toString( dt_seisanKanri , "null" ) );
//			}
//			if(!id_genshizai.equals("")){
//				strSQL.append("     ,gen_id_koshin = " + toString( id_genshizai , "null" ) );
//				strSQL.append("     ,gen_dt_koshin = " + toString( dt_genshizai , "null" ) );
//			}
//			if(!id_kojo.equals("")){
//				strSQL.append("     ,kojo_id_koshin = " + toString( id_kojo , "null" ) );
//				strSQL.append("     ,kojo_dt_koshin = " + toString( dt_kojo , "null" ) );
//			}
//
//			if(!toString( sam_id_koshin ).equals("")){
//				strSQL.append("     ,sam_dt_koshin = GETDATE()");
//				strSQL.append("     ,sam_id_koshin = " + sam_id_koshin );
//			}
			strSQL.append(" UPDATE tr_shisan_shisakuhin");
			strSQL.append("   SET ");
			strSQL.append("       lot = '"                + toString( reqData.getFieldVale( "kihon", 0, "seizo_roto"),"") + "'");
			strSQL.append("      ,fg_keisan = "        + toString( reqData.getFieldVale( "kihon", 0, "ragio_kesu_kg"),"null"));
			strSQL.append("      ,id_koshin = " + userInfoData.getId_user());
			strSQL.append("     ,dt_koshin = GETDATE()");


			strSQL.append(" WHERE");
			strSQL.append("	cd_shain = "    + strReqShainCd);
			strSQL.append("    AND nen = "   + strReqNen);
			strSQL.append("    AND no_oi = " + strReqOiNo);

			//【QP@00342】
			strSQL.append(" 	AND no_eda = " + strReqEda );


			//共通クラス　データベース管理を用い、試作リストデータの削除を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算登録[kihon]情報の更新SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;
			strSQLSearch = null;

			//セッションのクローズ
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}

		}
	}



	/**
	 * 原価試算登録[genryo]情報の更新処理
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuGenryoUpdateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQLSearch = new StringBuffer();
		List<?> lstRecset = null;

		try {

			//DBコネクション
			createSearchDB();

			//リクエストデータより試作コード取得
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//【QP@00342】
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");


			//更新処理
			for ( int i=0; i<reqData.getCntRow("genryo"); i++ ) {

				//カンマ削除
				String genka = toString(reqData.getFieldVale("genryo", i, "tanka"),"",",");
				String budomari = toString(reqData.getFieldVale("genryo", i, "budomari"),"",",");


				//------------------------ 単価比較　検索処理 -------------------------
				strSQLSearch = new StringBuffer();

				//単価　入力値・マスタ値比較
				strSQLSearch.append(" select ");
				strSQLSearch.append("  Count(tanka_ma) AS CNT ");
				strSQLSearch.append(" from ");
				strSQLSearch.append("  tr_shisan_haigo ");
				strSQLSearch.append(" where ");
				strSQLSearch.append("  cd_shain = " + strReqShainCd );
				strSQLSearch.append("  AND nen = " + strReqNen );
				strSQLSearch.append("  AND no_oi = " + strReqOiNo );

				//【QP@00342】
				strSQLSearch.append("  AND no_eda = " + strReqEda );

				strSQLSearch.append("  AND cd_kotei = " + reqData.getFieldVale("genryo", i, "cd_kotei") );
				strSQLSearch.append("  AND seq_kotei = " + reqData.getFieldVale("genryo", i, "seq_kotei") );
				strSQLSearch.append("  AND tanka_ma = " + toString(genka,"null") );

				lstRecset = this.searchDB.dbSearch(strSQLSearch.toString());

				//マスタ値と同じ場合
				if( toString(lstRecset.get(0)).equals("1") ){
					genka = "";
				}


				//------------------------ 歩留比較　検索処理 -------------------------
				strSQLSearch = new StringBuffer();

				//歩留　入力値・マスタ値比較
				strSQLSearch.append(" select ");
				strSQLSearch.append("  Count(budomar_ma) AS CNT ");
				strSQLSearch.append(" from ");
				strSQLSearch.append("  tr_shisan_haigo ");
				strSQLSearch.append(" where ");
				strSQLSearch.append("  cd_shain = " + strReqShainCd );
				strSQLSearch.append("  AND nen = " + strReqNen );
				strSQLSearch.append("  AND no_oi = " + strReqOiNo );

				//【QP@00342】
				strSQLSearch.append("  AND no_eda = " + strReqEda );

				strSQLSearch.append("  AND cd_kotei = " + reqData.getFieldVale("genryo", i, "cd_kotei") );
				strSQLSearch.append("  AND seq_kotei = " + reqData.getFieldVale("genryo", i, "seq_kotei") );
				strSQLSearch.append("  AND budomar_ma = " + toString(budomari,"null") );

				lstRecset = this.searchDB.dbSearch(strSQLSearch.toString());

				//マスタ値と同じ場合
				if( toString(lstRecset.get(0)).equals("1") ){
					budomari = "";
				}


				//----------------------------- 更新処理 ----------------------------
				strSQL = new StringBuffer();

				strSQL.append(" UPDATE tr_shisan_haigo");
				strSQL.append("   SET ");
				strSQL.append("      tanka_ins = " + toString(genka,"null"));
				strSQL.append("      ,budomari_ins = " + toString(budomari,"null"));
				strSQL.append("      ,id_koshin = " + userInfoData.getId_user());
				strSQL.append("      ,dt_koshin = GETDATE()");
				strSQL.append(" WHERE");
				strSQL.append("	cd_shain = "    + strReqShainCd);
				strSQL.append("   AND nen = "   + strReqNen);
				strSQL.append("   AND no_oi = " + strReqOiNo);

				//【QP@00342】
				strSQL.append("  AND no_eda = " + strReqEda );

				strSQL.append("   AND cd_kotei = " + reqData.getFieldVale("genryo", i, "cd_kotei"));
				strSQL.append("   AND seq_kotei = " + reqData.getFieldVale("genryo", i, "seq_kotei"));

				//共通クラス　データベース管理を用い、試作リストデータの削除を行う。
				this.execDB.execSQL(strSQL.toString());
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算登録[genryo]情報の更新SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;
			strSQLSearch = null;

			//セッションのクローズ
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}

		}
	}

	/**
	 * 原価試算登録[keisan]情報の更新処理
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuKeisanUpdateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//リクエストデータより試作コード取得
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//【QP@00342】
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");

			//選択ステータス取得
			String setting = reqData.getFieldVale("kihon", 0, "setting");
			//所属部署フラグ
			String kojo = reqData.getFieldVale("kihon", 0, "busho_kojo");
			//現在ステータス
			String st_kojo = reqData.getFieldVale("kihon", 0, "st_kojo");

			//更新用SQL作成
			for ( int i=0; i<reqData.getCntRow("keisan"); i++ ) {

				//【QP@00342】試算中止
				String fg_chusi = toString(reqData.getFieldVale("keisan", i, "fg_chusi"));

				//試算日のNULL設定
				String shisanHi = toString(reqData.getFieldVale("keisan", i, "shisan_date"),"null");

				//【H24年度対応】No.11 Start
				if(kojo.equals("1")){
					if(setting.equals("2")||st_kojo.equals("2")){
						// MOD start 20120614 hisahori 試算日が空白のサンプルのみ日付代入or更新
						//shisanHi = "CONVERT(varchar, GETDATE(),111)";
						if(shisanHi.equals("null")){
							shisanHi = "CONVERT(varchar, GETDATE(),111)";
						}else{
							shisanHi = "'" + shisanHi + "'";
						}
						// MOD end 20120614 hisahori
					}else{
						if(shisanHi.equals("null")){
							//何もしない
						}else{
							//SQL用にシングルクォーテーション追加
							shisanHi = "'" + shisanHi + "'";
						}
					}
				}else{
					if(shisanHi.equals("null")){
						//何もしない
					}else{
						//SQL用にシングルクォーテーション追加
						shisanHi = "'" + shisanHi + "'";
					}
				}
				//【H24年度対応】No.11 End


				//カンマ削除
				String yuuko_budomari = toString(reqData.getFieldVale("keisan", i, "yuuko_budomari"),"",",");
				String heikinjyutenryo = toString(reqData.getFieldVale("keisan", i, "heikinjyutenryo"),"",",");
				String kesu_kotehi = toString(reqData.getFieldVale("keisan", i, "kesu_kotehi"),"",",");
				String kg_kotehi = toString(reqData.getFieldVale("keisan", i, "kg_kotehi"),"",",");
				// ADD 2013/11/1 QP@30154 okano start
				String kesu_rieki = toString(reqData.getFieldVale("keisan", i, "kesu_rieki"),"",",");
				String kg_rieki = toString(reqData.getFieldVale("keisan", i, "kg_rieki"),"",",");
				// ADD 2013/11/1 QP@30154 okano end
//ADD 2013/07/11 ogawa 【QP@30151】No.13 start    //項目固定チェック
				String fg_koumokuchk = toString(reqData.getFieldVale("keisan", i, "fg_koumokuchk"),"",",");
//ADD 2013/07/11 ogawa 【QP@30151】No.13 start    //項目固定チェック

				strSQL = new StringBuffer();

				strSQL.append("UPDATE tr_shisan_shisaku");
				strSQL.append("   SET ");
				strSQL.append("      budomari = " + toString(yuuko_budomari,"null"));
				strSQL.append("      ,heikinjuten = " + toString(heikinjyutenryo,"null"));
				strSQL.append("      ,cs_kotei = " + toString(kesu_kotehi,"null"));
				strSQL.append("      ,kg_kotei = " + toString(kg_kotehi,"null"));
				// ADD 2013/11/1 QP@30154 okano start
				strSQL.append("      ,cs_rieki = " + toString(kesu_rieki,"null"));
				strSQL.append("      ,kg_rieki = " + toString(kg_rieki,"null"));
				// ADD 2013/11/1 QP@30154 okano end
				strSQL.append("      ,id_koshin = " + userInfoData.getId_user());
				strSQL.append("      ,dt_koshin = GETDATE()");

				//【QP@00342】試算中止
				if(fg_chusi.equals("1")){
					//試算中止の場合に試算中止フラグを更新
					strSQL.append("      ,fg_chusi = " + fg_chusi);
				}
				else{
					//試算中止でない場合に試算日付を更新
					strSQL.append("      ,dt_shisan = " + shisanHi);
				}
//ADD 2013/07/11 ogawa 【QP@30151】No.13 start    //項目固定チェック
				if(fg_koumokuchk.equals("1")){
					strSQL.append("      ,fg_koumokuchk = 1 ");
				}else{
					strSQL.append("      ,fg_koumokuchk = 0 ");
				}
//ADD 2013/07/11 ogawa 【QP@30151】No.13 start    //項目固定チェック

				strSQL.append(" WHERE");
				strSQL.append("	cd_shain = "    + strReqShainCd);
				strSQL.append("   AND nen = "   + strReqNen);
				strSQL.append("   AND no_oi = " + strReqOiNo);

				//【QP@00342】
				strSQL.append("  AND no_eda = " + strReqEda );

				strSQL.append("   AND seq_shisaku = " + reqData.getFieldVale("keisan", i, "seq_shisaku"));

				//共通クラス　データベース管理を用い、試作リストデータの削除を行う。
				this.execDB.execSQL(strSQL.toString());
			}

//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start    //項目固定表示用のDB更新（DELETE→INSERT）
			//更新用SQL作成(固定項目のON,OFFに関わらずDB更新)
			for ( int i=0; i<reqData.getCntRow("keisan"); i++ ) {

				//正規表現：数値確認用
				Pattern pattern = Pattern.compile("^[-]?[0-9]*[.]?[0-9]+");

				//リクエストデータより試作コード取得
				strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
				strReqNen = reqData.getFieldVale("kihon", 0, "nen");
				strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
				strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");
				String strReqSeq = reqData.getFieldVale("keisan", i, "seq_shisaku");

				//計算固定項目取得
				String strZyusui = toString(reqData.getFieldVale("keisan", i, "zyusui"),"",",");
				String strZyuabura = toString(reqData.getFieldVale("keisan", i, "zyuabura"),"",",");
				String strGokei = toString(reqData.getFieldVale("keisan", i, "gokei"),"",",");
				String strHiju = toString(reqData.getFieldVale("keisan", i, "hiju"),"",",");
				String strReberu = toString(reqData.getFieldVale("keisan", i, "reberu"),"",",");
				String strHijukasan = toString(reqData.getFieldVale("keisan", i, "hijukasan"),"",",");
				String strCsgenryo = toString(reqData.getFieldVale("keisan", i, "cs_genryo"),"",",");
				String strCszairyohi = toString(reqData.getFieldVale("keisan", i, "cs_zairyohi"),"",",");
				String strCsgenka = toString(reqData.getFieldVale("keisan", i, "cs_genka"),"",",");
				String strKogenka = toString(reqData.getFieldVale("keisan", i, "ko_genka"),"",",");
				String strKggenryo = toString(reqData.getFieldVale("keisan", i, "kg_genryo"),"",",");
				String strKgzairyohi = toString(reqData.getFieldVale("keisan", i, "kg_zairyohi"),"",",");
				String strKggenka = toString(reqData.getFieldVale("keisan", i, "kg_genka"),"",",");
				String strBaika = toString(reqData.getFieldVale("keisan", i, "baika"),"");
				String strArari = toString(reqData.getFieldVale("keisan", i, "arari"),"",",");

				//粗利の単位（％）削除
				//※売価の単位は項目固定時のものを保存する為、ここでの削除はしない
				Matcher matcher = pattern.matcher(strArari);
				if (matcher.find()) {
					strArari = matcher.group();
				} else {
					strArari = "";
				}

				//DELETE文作成
				strSQL = new StringBuffer();
				strSQL.append(" DELETE FROM");
				strSQL.append("    tr_shisan_shisaku_kotei");
				strSQL.append(" WHERE");
				strSQL.append("    cd_shain=" + strReqShainCd);
				strSQL.append(" AND nen=" + strReqNen);
				strSQL.append(" AND no_oi=" + strReqOiNo);
				strSQL.append(" AND seq_shisaku=" + strReqSeq);
				strSQL.append(" AND no_eda=" + strReqEda);

				//共通クラス　データベース管理を用い、SQLを実行
				this.execDB.execSQL(strSQL.toString());

				//INSERT文作成
				strSQL = new StringBuffer();
				strSQL.append(" INSERT INTO tr_shisan_shisaku_kotei ");
				strSQL.append("            ( cd_shain ");
				strSQL.append("            , nen ");
				strSQL.append("            , no_oi ");
				strSQL.append("            , seq_shisaku ");
				strSQL.append("            , no_eda ");
				strSQL.append("            , zyusui ");
				strSQL.append("            , zyuabura ");
				strSQL.append("            , gokei ");
				strSQL.append("            , hiju ");
				strSQL.append("            , reberu ");
				strSQL.append("            , hijukasan ");
				strSQL.append("            , cs_genryo ");
				strSQL.append("            , cs_zairyohi ");
				strSQL.append("            , cs_genka ");
				strSQL.append("            , ko_genka ");
				strSQL.append("            , kg_genryo ");
				strSQL.append("            , kg_zairyohi ");
				strSQL.append("            , kg_genka ");
				strSQL.append("            , baika ");
				strSQL.append("            , arari ");
				strSQL.append("            , id_toroku ");
				strSQL.append("            , dt_toroku ");
				strSQL.append("            , id_koshin ");
				strSQL.append("            , dt_koshin )");
				strSQL.append("      VALUES");
				strSQL.append("            (" + strReqShainCd);
				strSQL.append("            ," + strReqNen);
				strSQL.append("            ," + strReqOiNo);
				strSQL.append("            ," + strReqSeq);
				strSQL.append("            ," + strReqEda);
				strSQL.append("            ,'" + strZyusui + "'");
				strSQL.append("            ,'" + strZyuabura + "'");
				strSQL.append("            ,'" + strGokei + "'");
				strSQL.append("            ,'" + strHiju + "'");
				strSQL.append("            ,'" + strReberu + "'");
				strSQL.append("            ,'" + strHijukasan + "'");
				strSQL.append("            ,'" + strCsgenryo + "'");
				strSQL.append("            ,'" + strCszairyohi + "'");
				strSQL.append("            ,'" + strCsgenka + "'");
				strSQL.append("            ,'" + strKogenka + "'");
				strSQL.append("            ,'" + strKggenryo + "'");
				strSQL.append("            ,'" + strKgzairyohi + "'");
				strSQL.append("            ,'" + strKggenka + "'");
				strSQL.append("            ,'" + strBaika + "'");
				strSQL.append("            ,'" + strArari + "'");
				strSQL.append("            ," + userInfoData.getId_user());
				strSQL.append("            ,GETDATE()");
				strSQL.append("            ," + userInfoData.getId_user());
				strSQL.append("            ,GETDATE())");

				//共通クラス　データベース管理を用い、SQLを実行
				this.execDB.execSQL(strSQL.toString());
			}
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end    //項目固定表示用のDB更新（DELETE→INSERT）

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算登録[keisan]情報の更新SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

		}
	}

	/**
	 * 原価試算登録[shizai]情報の削除処理
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuShizaiDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//リクエストデータより試作コード取得
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//【QP@00342】
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");


			//削除用SQL作成
			strSQL.append(" DELETE FROM tr_shisan_shizai");
			strSQL.append(" WHERE");
			strSQL.append("	cd_shain = "    + strReqShainCd);
			strSQL.append("   AND nen = "   + strReqNen);
			strSQL.append("   AND no_oi = " + strReqOiNo);

			//【QP@00342】
			strSQL.append("   AND no_eda = " + strReqEda);


			//共通クラス　データベース管理を用い、試作リストデータの削除を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算登録[shizai]情報の削除SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

		}
	}

	/**
	 * 原価試算登録[shizai]情報の登録処理
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuShizaiInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//リクエストデータより試作コード取得
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//【QP@00342】
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");


			//登録用SQL作成
			for ( int i=0; i<reqData.getCntRow("shizai"); i++ ) {

				strSQL = new StringBuffer();

				//資材SEQがない場合
				if(toString(reqData.getFieldVale("shizai", i, "seq_shizai")).equals("")){
					//何もしない

				}else{

					//カンマ削除
					String tanka = toString(reqData.getFieldVale("shizai", i, "tanka"),"",",");
					String budomari = toString(reqData.getFieldVale("shizai", i, "budomari"),"",",");
					String shiyouryo = toString(reqData.getFieldVale("shizai", i, "shiyouryo"),"",",");

					strSQL.append("INSERT INTO tr_shisan_shizai");
					strSQL.append("   (cd_shain");
					strSQL.append("   ,nen");
					strSQL.append("   ,no_oi");
					strSQL.append("   ,no_eda");
					strSQL.append("   ,seq_shizai");
					strSQL.append("   ,no_sort");
					strSQL.append("   ,cd_kaisha");
					strSQL.append("   ,cd_busho");
					strSQL.append("   ,cd_shizai");
					strSQL.append("   ,nm_shizai");
					strSQL.append("   ,tanka");
					strSQL.append("   ,budomari");
					strSQL.append("  ,cs_siyou");
					strSQL.append("   ,id_toroku");
					strSQL.append("   ,dt_toroku");
					strSQL.append("   ,id_koshin");
					strSQL.append("  ,dt_koshin)");
					strSQL.append(" VALUES");
					strSQL.append("   (" + strReqShainCd );
					strSQL.append("   ," + strReqNen );
					strSQL.append("   ," + strReqOiNo);

					//【QP@00342】
					strSQL.append("   ," + strReqEda);

					strSQL.append("   ," + reqData.getFieldVale("shizai", i, "seq_shizai"));
					strSQL.append("   ," + reqData.getFieldVale("shizai", i, "seq_shizai"));
					strSQL.append("   ," + toString(reqData.getFieldVale("shizai", i, "cd_kaisya"),"null", ","));
					strSQL.append("   ," + toString(reqData.getFieldVale("shizai", i, "cd_kojyo"),"null", ","));
					strSQL.append("   ," + toString(reqData.getFieldVale("shizai", i, "cd_shizai"),"null", ","));
					strSQL.append("   ,'" + toString(reqData.getFieldVale("shizai", i, "nm_shizai"),"") + "'");
					strSQL.append("   ," + toString(tanka,"null", ","));
					strSQL.append("   ," + toString(budomari,"null", ","));
					strSQL.append("   ," + toString(shiyouryo,"null", ","));

					//登録者ID、登録日
					if(toString(reqData.getFieldVale("shizai", i, "id_toroku")).equals("")){
						strSQL.append("   ," + userInfoData.getId_user());
						strSQL.append("   ,GETDATE()");

					}else{
						strSQL.append("   ,'" + toString(reqData.getFieldVale("shizai", i, "id_toroku")) + "'");
						strSQL.append("   ,'" + toString(reqData.getFieldVale("shizai", i, "dt_toroku")) + "'");

					}

					strSQL.append("   ," + userInfoData.getId_user());
					strSQL.append("   ,GETDATE() )");

					//共通クラス　データベース管理を用い、試作リストデータの削除を行う。
					this.execDB.execSQL(strSQL.toString());
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算登録[shizai]情報の登録SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

		}
	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData（パラメーター格納）
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * 管理結果パラメーター格納
	 *  : 試作テーブルデータの管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
//MOD 2013/06/28 ogawa【QP@30151】No.38 Start
//修正前ソース
//	private void storageGenkaTorokuDataKanri(RequestResponsTableBean resTable)
//修正後ソース
	private void storageGenkaTorokuDataKanri(RequestResponsTableBean resTable, String msgcd)
//MOD 2013/06/28 ogawa【QP@30151】No.38 end
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//①：レスポンスデータを形成する。

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
//ADD 2013/06/28 ogawa 【QP@30151】No.38 start
			resTable.addFieldVale(0, "msg_cd", msgcd);
//ADD 2013/06/28 ogawa 【QP@30151】No.38 end

		} catch (Exception e) {
			this.em.ThrowException(e, "管理結果パラメーター格納処理が失敗しました。");

		} finally {

		}
	}



	// ADD 2013/7/2 shima【QP@30151】No.37 start
	/**
	 * 原価試算登録[kihonsub]情報の更新処理
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void torokuKihonSubUpdateSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {
			//リクエストデータより試作コード取得
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			//【QP@00342】
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");

			//更新処理
			for ( int i=0; i<reqData.getCntRow("kihonsub"); i++ ) {

				strSQL.append(" UPDATE tr_shisan_kihonjoho");
				strSQL.append("   SET ");
				strSQL.append("       lot = '"      + toString( reqData.getFieldVale( "kihonsub", i, "seizo_roto"),"") + "'");
				strSQL.append("      ,id_koshin = " + userInfoData.getId_user());
				strSQL.append("     ,dt_koshin = GETDATE()");

				strSQL.append(" WHERE ");
				strSQL.append("       cd_shain = " + strReqShainCd);
				strSQL.append("   AND nen = "   + strReqNen);
				strSQL.append("   AND no_oi = " + strReqOiNo);
				strSQL.append("   AND no_eda = " + strReqEda );
				strSQL.append("   AND seq_shisaku = " + reqData.getFieldVale("kihonsub", i, "seq_shisaku"));

				//共通クラス　データベース管理を用い、試作リストデータの削除を行う。
				this.execDB.execSQL(strSQL.toString());

			}
		} catch (Exception e) {
			this.em.ThrowException(e, "原価試算登録[kihonsub]情報の更新SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

			//セッションのクローズ
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}

		}
	}
	// ADD 2013/7/2 shima【QP@30151】No.37 end

}