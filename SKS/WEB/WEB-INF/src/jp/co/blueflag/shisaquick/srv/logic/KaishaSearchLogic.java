package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 *
 * 会社検索（ﾄﾞﾛｯﾌﾟﾀﾞｳﾝ用）DB処理 : ﾄﾞﾛｯﾌﾟﾀﾞｳﾝ用　会社検索データ抽出SQLを作成する。
 * M104_bushoテーブルから、データの抽出を行う。
 *
 * @author itou
 * @since 2009/04/02
 * @author TT.katayama
 * @since 2009/04/07
 */
public class KaishaSearchLogic extends LogicBase {

	/**
	 * 会社検索（ﾄﾞﾛｯﾌﾟﾀﾞｳﾝ用）用コンストラクタ : インスタンス生成
	 */
	public KaishaSearchLogic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * コンボ用：会社情報取得SQL作成 M104_bushoテーブルから、データの抽出を行う。
	 *
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

		//リクエストデータ
		String strKinoId = "";
		String strTableNm = "";
		String strReqUserId = "";
		String strReqGamenId = "";
		//ユーザ情報
		String strUDataId = "";
		String strUKinoId = "";

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//権限データIDの取得

			//①：機能リクエストデータクラスを用い、リテラルマスタ情報を取得するSQLを作成する。

			//機能リクエストデータよりユーザIDと画面IDを取得
			strReqUserId = reqData.getFieldVale(0, 0, "id_user");
			strReqGamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//ユーザ情報のユーザIDを取得
			if ( strReqUserId.equals("") ) {
				strReqUserId = userInfoData.getId_user();
			}

			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strReqGamenId)){
					//機能IDを設定
					strUKinoId = userInfoData.getId_kino().get(i).toString();
					//データIDを設定
					strUDataId = userInfoData.getId_data().get(i).toString();
				}
			}

			// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
			//仮ユーザが存在しない
			if (strReqUserId.equals("")) {
				this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "E000205", "", "", "");
			}
			// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end

			// SQL文の作成
//			strSql.append("SELECT distinct cd_kaisha, nm_kaisha");
//			strSql.append(" FROM ma_busho");
//			strSql.append(" ORDER BY cd_kaisha");

			//画面ID、機能ID、データIDによる処理の制御
			if ( strReqGamenId.equals("10") ) {
				//試作データ一覧（JSP）

				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
					} else if ( strUDataId.equals("2") ) {
						//同一グループ且つ担当会社
					} else if ( strUDataId.equals("3") ) {
						//担当会社
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("4") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("20") || strReqGamenId.equals("25") ) {
				//原料分析情報（JSP）

				if (strUKinoId.equals("")) {
					//権限データID取得
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("25")){
							//機能IDを設定
							strUKinoId = userInfoData.getId_kino().get(i).toString();
							//データIDを設定
							strUDataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}

				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//担当会社
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
					    // MOD 2013/11/6 QP@30154 okano start
//							strSql = this.createSQLAllKaisha();
						strSql = this.createSQLMyKaisha(strReqUserId);
					    // MOD 2013/11/6 QP@30154 okano end

					}
				} else if ( strUKinoId.equals("20") ) {		//編集（削除のみ）
					if ( strUDataId.equals("1") ) {
						//担当会社
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
					    // MOD 2013/11/6 QP@30154 okano start
//							strSql = this.createSQLAllKaisha();
						strSql = this.createSQLMyKaisha(strReqUserId);
					    // MOD 2013/11/6 QP@30154 okano end

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410" ,strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("30") ) {
				//分析値入力（新規）（JSP）

				//権限による分岐
				if ( strUKinoId.equals("30") ) {					//新規
					if ( strUDataId.equals("1") ) {
						//担当会社
					    // MOD 2013/11/6 QP@30154 okano start
//							strSql = this.createSQLTantoKaisha(strReqUserId);
						strSql = this.createSQLAllKaisha();
					    // MOD 2013/11/6 QP@30154 okano end

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("40") ) {
				//分析値入力（詳細）（JSP）

				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//担当会社
					    // MOD 2013/11/6 QP@30154 okano start
//							strSql = this.createSQLTantoKaisha(strReqUserId);
						strSql = this.createSQLMyKaisha(strReqUserId);
					    // MOD 2013/11/6 QP@30154 okano end

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else if ( strUKinoId.equals("20") ) {		//編集(新規原料のみ)
					if ( strUDataId.equals("1") ) {
						//担当会社
					    // MOD 2013/11/6 QP@30154 okano start
//							strSql = this.createSQLTantoKaisha(strReqUserId);
						strSql = this.createSQLMyKaisha(strReqUserId);
					    // MOD 2013/11/6 QP@30154 okano end

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else if ( strUKinoId.equals("40") ) {		//編集(全て)
					if ( strUDataId.equals("1") ) {
						//担当会社
					    // MOD 2013/11/6 QP@30154 okano start
//							strSql = this.createSQLTantoKaisha(strReqUserId);
						strSql = this.createSQLMyKaisha(strReqUserId);
					    // MOD 2013/11/6 QP@30154 okano end

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
// 20160513  KPX@1600766 ADD start
				} else if ( strUKinoId.equals("50") ) {		//編集(システム管理者：廃止操作許可)
					if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();
					}
// 20160513  KPX@1600766 ADD end
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("50") ) {
				//全工場単価歩留画面（JSP）

				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
					    // MOD 2013/11/6 QP@30154 okano start
//							strSql = this.createSQLAllKaisha();
						strSql = this.createSQLMyKaisha(strReqUserId);
					    // MOD 2013/11/6 QP@30154 okano end

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("60") || strReqGamenId.equals("65") ) {
				//各リテラルマスタ（JSP）

				if (strUKinoId.equals("")) {
					//権限データID取得
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("65")){
							//機能IDを設定
							strUKinoId = userInfoData.getId_kino().get(i).toString();
							//データIDを設定
							strUDataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}

				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//CSV出力（一般）
					if ( strUDataId.equals("1") ) {
						//同一グループ
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else  if ( strUKinoId.equals("20") ) {					//編集（一般）
					if ( strUDataId.equals("1") ) {
						//同一グループ
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else  if ( strUKinoId.equals("50") ) {		//編集（システム管理者）
					if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("70") ) {
				//グループマスタ（JSP）

				//権限による分岐
				if ( strUKinoId.equals("20") ) {					//編集
					if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("80") ) {
				//権限マスタ（JSP）

				//権限による分岐
				if ( strUKinoId.equals("20") ) {					//編集
					if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("90") ) {
				//担当者マスタ（JSP）

				//権限による分岐
				if ( strUKinoId.equals("21") ) {					//編集（氏名ﾊﾟｽﾜｰﾄﾞのみ）
					 if ( strUDataId.equals("1") ) {
						//自分のみ
						strSql = this.createSQLMyKaisha(strReqUserId);

					// ADD 2013/10/24 QP@30154 okano start
					} else if ( strUDataId.equals("2") ) {
						//所属会社のみ
						strSql = this.createSQLMyKaisha(strReqUserId);

					// ADD 2013/10/24 QP@30154 okano end
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else if ( strUKinoId.equals("20") ) {		//編集（全て）
					if ( strUDataId.equals("1") ) {
						//自分のみ
						strSql = this.createSQLMyKaisha(strReqUserId);

					// ADD 2013/10/24 QP@30154 okano start
					} else if ( strUDataId.equals("2") ) {
						//所属会社のみ
						strSql = this.createSQLMyKaisha(strReqUserId);

					// ADD 2013/10/24 QP@30154 okano end
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				// ADD 2013/9/25 okano【QP@30151】No.28 start
				} else if ( strUKinoId.equals("22") || strUKinoId.equals("23") || strUKinoId.equals("24") ) {		//編集（仮登録ユーザ）
						//全て
						strSql = this.createSQLAllKaisha();
				// ADD 2013/9/25 okano【QP@30151】No.28 end
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("000") ) {
				//試作データ画面（閲覧）(JWS)

				//全て
				strSql = this.createSQLAllKaisha();

			} else if ( strReqGamenId.equals("100") ) {
				//試作データ画面（詳細）(JWS)

				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
//					if ( strUDataId.equals("1") ) {
//						//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
//						strSql = this.createSQLTantoKaisha(strReqUserId);
//
//					} else if ( strUDataId.equals("2") ) {
//						//同一グループ且つ担当会社
//						strSql = this.createSQLTantoKaisha(strReqUserId);
//
//					} else if ( strUDataId.equals("3") ) {
//						//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
//						strSql = this.createSQLTantoKaisha(strReqUserId);
//
//					} else if ( strUDataId.equals("4") ) {
//						//自工場分(自会社)
//						strSql = this.createSQLMyKaisha(strReqUserId);
//
//					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

//					}
				} else if ( strUKinoId.equals("20") ) {			//編集

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
//					if ( strUDataId.equals("1") ) {
					if ( strUDataId.equals("1") || strUDataId.equals("5") ) {
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end
						//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//同一グループ且つ担当会社
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("3") ) {
						//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("4") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("110") ) {
				//試作データ画面（新規）(JWS)

				//権限による分岐
				if ( strUKinoId.equals("30") ) {			//新規
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//同一グループ且つ担当会社
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("3") ) {
						//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("4") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("120") ) {
				//試作データ画面（製法支援コピー）(JWS)

				//権限による分岐
				if ( strUKinoId.equals("20") ) {			//編集（新規）
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//同一グループ且つ担当会社
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("3") ) {
						//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("4") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("130") ) {
				//分析値入力（新規）（JWS）

				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else if ( strUKinoId.equals("20") ) {		//編集（更新）
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("140") ) {
				//分析値入力（詳細）（JWS）

				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				}

				else if ( strUKinoId.equals("20") ) {					//編集（更新・新規原料のみ）
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				}

				else if ( strUKinoId.equals("40") ) {					//編集（更新・全て）
					if ( strUDataId.equals("1") ) {
						//担当会社
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				}
// 20160513  KPX@1600766 ADD start
				else if ( strUKinoId.equals("50") ) {		//編集(システム管理者：廃止操作許可)
					if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();
					}
				}
//20160513  KPX@1600766 ADD end

				else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}

			} else if ( strReqGamenId.equals("150") ) {
				//原料一覧

				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end
				}
			} else if ( strReqGamenId.equals("160") ) {
				//原料情報分析（JWS）

				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				} else if ( strUKinoId.equals("20") ) {		//編集（削除）
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//自工場分
						strSql = this.createSQLMyKaisha(strReqUserId);

					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllKaisha();

					}
				}
				else {
					//対象の機能IDではない場合
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
//					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
					// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end

				}
			}

			//【QP@00342】原価試算一覧
			else if ( strReqGamenId.equals("180") ) {

				//閲覧
				if ( strUKinoId.equals("80") ) {
					//自工場分のみ
					if ( strUDataId.equals("2") ) {
						strSql = this.createSQLMyKaisha(strReqUserId);

					}
					//自工場分のみ　以外
					else {
						strSql = this.createSQLAllKaisha_KojoFg();

					}
				}
				// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.32 start
				else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000410", strReqGamenId, strUKinoId, "");
				}
				// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.32 end

			}

			// SQLを実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			// 機能IDの設定
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// テーブル名の設定
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
			storageKaishaCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除
			strSql = null;
		}

		return resKind;
	}

	/**
	 * 権限「全て」の会社データ取得用SQL作成
	 * @return 作成SQL
	 */
	private StringBuffer createSQLAllKaisha() {
		StringBuffer strRetSql = new StringBuffer();

		// SQL文の作成
		strRetSql.append("SELECT distinct cd_kaisha, nm_kaisha, keta_genryo");
		strRetSql.append(" FROM ma_busho");
		strRetSql.append(" ORDER BY cd_kaisha");

		return strRetSql;
	}

	/**【QP@00342】
	 * 権限「全て」の会社データ取得用SQL作成（工場フラグが１のもの）
	 * @return 作成SQL
	 */
	private StringBuffer createSQLAllKaisha_KojoFg() {
		StringBuffer strRetSql = new StringBuffer();

		// SQL文の作成
		strRetSql.append("SELECT distinct cd_kaisha, nm_kaisha, keta_genryo");
		strRetSql.append(" FROM ma_busho");

		//【QP@00342】工場フラグが１のものだけ表示
		strRetSql.append(" WHERE	flg_kojo = 1 ");

		strRetSql.append(" ORDER BY cd_kaisha");

		return strRetSql;
	}

	/**
	 * 権限「自会社分」の会社データ取得用SQL作成
	 * @param strUserId : ユーザID
	 * @return 作成SQL
	 */
	private StringBuffer createSQLMyKaisha(String strUserId) {
		StringBuffer strRetSql = new StringBuffer();

		String strKaishaCd = userInfoData.getCd_kaisha();

		// SQL文の作成
		strRetSql.append(" SELECT distinct ");
		strRetSql.append(" M101.cd_kaisha AS cd_kaisha,  ");
		strRetSql.append(" ISNULL(M104.nm_kaisha,'') AS nm_kaisha, ");
		strRetSql.append(" ISNULL(M104.keta_genryo,'') ");
		strRetSql.append(" FROM ma_user M101 ");
		strRetSql.append("  LEFT JOIN ma_busho M104 ");
		strRetSql.append("   ON M104.cd_kaisha = M101.cd_kaisha ");
		strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M101.id_user =" + strUserId);

		return strRetSql;
	}

	/**
	 * 権限「担当会社」の会社データ取得用SQL作成
	 * @param strUserId : ユーザID
	 * @return 作成SQL
	 */
	private StringBuffer createSQLTantoKaisha(String strUserId) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL文の作成
		strRetSql.append(" SELECT distinct  ");
		strRetSql.append(" ISNULL(M107.cd_tantokaisha,'') AS cd_tantokaisha, ");
		strRetSql.append(" ISNULL(M104.nm_kaisha,'') AS nm_kaisha, ");
		strRetSql.append(" ISNULL(M104.keta_genryo,'') ");
		strRetSql.append(" FROM ma_user M101 ");
		strRetSql.append("  LEFT JOIN ma_tantokaisya M107 ");
		strRetSql.append("   ON M107.id_user = M101.id_user ");
		strRetSql.append("  LEFT JOIN ma_busho M104 ");
		strRetSql.append("   ON M104.cd_kaisha = M107.cd_tantokaisha ");
		strRetSql.append(" WHERE M101.id_user =" + strUserId);

		return strRetSql;
	}

	/**
	 * コンボ用：会社パラメーター格納 : 会社コンボボックス情報をレスポンスデータへ格納する。
	 * 取得情報を、レスポンスデータ保持「機能ID：SA140O」に設定する。
	 *
	 * @param lstGroupCmb
	 *            : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageKaishaCmb(List<?> lstGroupCmb, RequestResponsTableBean resTable)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {

			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");

				Object[] items = (Object[]) lstGroupCmb.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale("rec" + i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale("rec" + i, "nm_kaisha", items[1].toString());
				resTable.addFieldVale("rec" + i, "keta_genryo", items[2].toString());

			}

			if (lstGroupCmb.size() == 0) {

				//処理結果の格納
				resTable.addFieldVale("rec0", "flg_return", "true");
				resTable.addFieldVale("rec0", "msg_error", "");
				resTable.addFieldVale("rec0", "no_errmsg", "");
				resTable.addFieldVale("rec0", "nm_class", "");
				resTable.addFieldVale("rec0", "cd_error", "");
				resTable.addFieldVale("rec0", "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale("rec0", "cd_kaisha", "");
				resTable.addFieldVale("rec0", "nm_kaisha", "");
				resTable.addFieldVale("rec0", "keta_genryo", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
