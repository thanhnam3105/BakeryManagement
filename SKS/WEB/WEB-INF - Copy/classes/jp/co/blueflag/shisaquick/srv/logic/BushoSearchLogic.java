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
 * 部署検索（ﾄﾞﾛｯﾌﾟﾀﾞｳﾝ用）DB処理
 *  : ﾄﾞﾛｯﾌﾟﾀﾞｳﾝ用　部署検索データ抽出SQLを作成する。
 * M104_bushoテーブルから、データの抽出を行う。
 * 
 * @author TT.katayama
 * @since 2009/04/08
 */
public class BushoSearchLogic extends LogicBase {

	/**
	 * 部署検索（ﾄﾞﾛｯﾌﾟﾀﾞｳﾝ用）用コンストラクタ : インスタンス生成
	 */
	public BushoSearchLogic() {
		//基底クラスのコンストラクタ
		super();
		
	}

	/**
	 * コンボ用：部署情報取得SQL作成
	 *  M104_bushoテーブルから、データの抽出を行う。
	 * 
	 * @param reqKind : 機能リクエストデータ
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
		String strReqKaishaCd = "";
		//ユーザ情報
		String strUDataId = "";
		String strUKinoId = "";

		List<?> lstRecset = null;
		StringBuffer strSql = new StringBuffer();

		RequestResponsKindBean resKind = null;
		
		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//権限データIDの取得

			//①：機能リクエストデータクラスを用い、リテラルマスタ情報を取得するSQLを作成する。
			
			//機能リクエストデータよりユーザIDと画面IDを取得
			strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strReqGamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//ユーザ情報のユーザIDを取得
			strReqUserId = userInfoData.getId_user();
			
			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strReqGamenId)){
					//機能IDを設定
					strUKinoId = userInfoData.getId_kino().get(i).toString(); 
					//データIDを設定
					strUDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
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
					} else if ( strUDataId.equals("4") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("20") ) {
				//原料分析情報（JSP）
				
				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("2") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {		//編集（削除のみ）
					if ( strUDataId.equals("2") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("50") ) {		//編集（削除＋CSV出力）
					if ( strUDataId.equals("2") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("30") ) {
				//分析値入力（新規）（JSP）
				
				//権限による分岐
				if ( strUKinoId.equals("30") ) {					//新規
					if ( strUDataId.equals("1") ) {
						//担当会社
						
						
					} else if ( strUDataId.equals("2") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("40") ) {
				//分析値入力（詳細）（JSP）
				
				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//担当会社
						
						
					} else if ( strUDataId.equals("2") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {		//編集
					if ( strUDataId.equals("1") ) {
						//担当会社
						
						
					} else if ( strUDataId.equals("2") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("50") ) {
				//全工場単価歩留画面（JSP）
				
				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）
						
					} else if ( strUDataId.equals("2") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("60") ) {
				//各リテラルマスタ（JSP）
				
				//権限による分岐
				if ( strUKinoId.equals("20") ) {					//編集（更新のみ）
					if ( strUDataId.equals("1") ) {
						//同一グループ
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("50") ) {		//編集（更新＋CSV出力）
					if ( strUDataId.equals("1") ) {
						//同一グループ
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else  if ( strUKinoId.equals("40") ) {		//編集（システム管理者）
					if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("70") ) {
				//グループマスタ（JSP）
				
				//権限による分岐
				if ( strUKinoId.equals("20") ) {					//編集
					if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("80") ) {
				//権限マスタ（JSP）
				
				//権限による分岐
				if ( strUKinoId.equals("20") ) {					//編集
					if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("90") ) {
				//担当者マスタ（JSP）
				
				//会社コードが未入力の場合（ユーザIDのロストフォーカス時）
				if (strReqKaishaCd.equals("")) {
					//ユーザIDの再取得
					strReqUserId = reqData.getFieldVale(0, 0, "id_user");
				}

				//権限による分岐
				if ( strUKinoId.equals("21") ) {					//編集（氏名ﾊﾟｽﾜｰﾄﾞのみ）
					 if ( strUDataId.equals("1") ) {
						//自分のみ
						strSql = this.createSQLMyBushoTantoMst(strReqUserId, strReqKaishaCd);
						 
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBushoTantoMst(strReqUserId, strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {		//編集（全て）
					if ( strUDataId.equals("1") ) {
						//自分のみ
						strSql = this.createSQLMyBushoTantoMst(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBushoTantoMst(strReqUserId, strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("100") ) {
				//試作データ画面（詳細）(JWS)
				
				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）			
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("2") ) {
						//同一グループ且つ担当会社				
						strSql = this.createSQLAllBusho(strReqKaishaCd);
								
					} else if ( strUDataId.equals("3") ) {
						//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("4") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {			//編集
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）		
						strSql = this.createSQLAllBusho(strReqKaishaCd);
										
					} else if ( strUDataId.equals("2") ) {
						//同一グループ且つ担当会社					
						strSql = this.createSQLAllBusho(strReqKaishaCd);
							
					} else if ( strUDataId.equals("3") ) {
						//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("4") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("110") ) {	
				//試作データ画面（新規）(JWS)

				//権限による分岐
				if ( strUKinoId.equals("30") ) {			//新規
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）			
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("2") ) {
						//同一グループ且つ担当会社						
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("3") ) {
						//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("4") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("120") ) {
				//試作データ画面（製法支援コピー）(JWS)

				//権限による分岐
				if ( strUKinoId.equals("20") ) {			//編集（新規）
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）			
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("2") ) {
						//同一グループ且つ担当会社						
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("3") ) {
						//同一グループ且つ担当会社且つ（本人+ﾁｰﾑﾘｰﾀﾞｰ以上）
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("4") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("130") ) {	
				//分析値入力（新規）（JWS）
				
				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）			
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {		//編集（更新）
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）		
						strSql = this.createSQLAllBusho(strReqKaishaCd);
										
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("140") ) {
				//分析値入力（詳細）（JWS）
				
				//権限による分岐
				if ( strUKinoId.equals("20") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）			
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("150") ) {	
				//原料一覧
				
				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）				
						strSql = this.createSQLAllBusho(strReqKaishaCd);
								
					} else if ( strUDataId.equals("2") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("160") ) {
				//原料情報分析（JWS）
				
				//権限による分岐
				if ( strUKinoId.equals("10") ) {					//閲覧
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）			
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("2") ) {
						//自部署
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {		//編集（削除）
					if ( strUDataId.equals("1") ) {
						//同一グループ且つ担当会社且つ（本人+上司）				
						strSql = this.createSQLAllBusho(strReqKaishaCd);
								
					} else if ( strUDataId.equals("2") ) {
						//自部署分
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//全て
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//対象の機能IDではない場合
					this.em.ThrowException(ExceptionManager.ExceptionKind.警告Exception, "W000400", strKinoId, "", "");
				}
			} 
			
			//②：データベース検索を用い、部署コンボボックスデータを取得する。
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//③：コンボ用：部署パラメーター格納メソッドを呼出し、機能レスポンスデータを形成する。
			
			// 機能IDの設定
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			// テーブル名の設定
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの形成
			storageKaishaCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "コンボ用：部署情報取得SQL作成処理が失敗しました。");
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
		
		//④：機能レスポンスデータを返却する。
		return resKind;
	}
	
	/**
	 * 権限「全て」の部署データ取得用SQL作成
	 * @param strKaishaCd : 会社コード
	 * @return 作成SQL
	 */
	private StringBuffer createSQLAllBusho(String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL文の作成
		strRetSql.append("SELECT distinct cd_busho, nm_busho");
		strRetSql.append(" FROM ma_busho");
		strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);
		strRetSql.append(" ORDER BY cd_busho");
		
		return strRetSql;
	}
	
	/**
	 * 権限「自部署分」の部署データ取得用SQL作成
	 * @param strUserId : ユーザID
	 * @param strKaishaCd : 会社コード
	 * @return 作成SQL
	 */
	private StringBuffer createSQLMyBusho(String strUserId, String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();
		
		String strBushoCd = userInfoData.getCd_busho();

		// SQL文の作成
		strRetSql.append(" SELECT distinct ");
		strRetSql.append(" M101.cd_busho AS cd_busho,  ");
		strRetSql.append(" ISNULL(M104.nm_busho,'') AS nm_busho ");
		strRetSql.append(" FROM ma_user M101 ");
		strRetSql.append("  LEFT JOIN ma_busho M104 ");
		strRetSql.append("   ON M104.cd_busho = M101.cd_busho ");
		strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M101.cd_busho =" + strBushoCd);
		strRetSql.append("  AND M101.id_user =" + strUserId);
		
		return strRetSql;
	}
	
	/**
	 * 担当者マスタ専用：権限「全て」の部署データ取得用SQL作成
	 * @param strKaishaCd : 会社コード
	 * @return 作成SQL
	 */
	private StringBuffer createSQLAllBushoTantoMst(String strUserId, String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL文の作成
		strRetSql.append("SELECT distinct cd_busho, nm_busho");
		strRetSql.append(" FROM ma_busho");
		if (strKaishaCd.equals("")) {
			strRetSql.append(" WHERE cd_kaisha = (SELECT cd_kaisha FROM ma_user WHERE id_user = " + strUserId + ")");
		} else {
			strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);
		}
		strRetSql.append(" ORDER BY cd_busho");
		
		return strRetSql;
	}
	
	/**
	 * 担当者マスタ専用：権限「自分のみ」の部署データ取得用SQL作成
	 * @param strUserId : ユーザID
	 * @param strKaishaCd : 会社コード
	 * @return 作成SQL
	 */
	private StringBuffer createSQLMyBushoTantoMst(String strUserId, String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();
		
		String strBushoCd = userInfoData.getCd_busho();

		// SQL文の作成
		strRetSql.append(" SELECT distinct ");
		strRetSql.append(" M101.cd_busho AS cd_busho,  ");
		strRetSql.append(" ISNULL(M104.nm_busho,'') AS nm_busho ");
		strRetSql.append(" FROM ma_user M101 ");
		strRetSql.append("  LEFT JOIN ma_busho M104 ");
		strRetSql.append("   ON M104.cd_kaisha = M101.cd_kaisha ");
		strRetSql.append("   AND M104.cd_busho = " + strBushoCd);
		if (strKaishaCd.equals("")) {
			strRetSql.append(" WHERE M101.id_user =" + strUserId);
		} else {
			strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
			strRetSql.append("  AND M101.cd_busho =" + strBushoCd);
			strRetSql.append("  AND M101.id_user =" + strUserId);
		}
		
		return strRetSql;
	}
	
	/**
	 * コンボ用：部署パラメーター格納
	 *  : 部署コンボボックス情報をレスポンスデータへ格納する。
	 * 取得情報を、レスポンスデータ保持「機能ID：SA290O」に設定する。
	 * 
	 * @param lstGroupCmb : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageKaishaCmb(List<?> lstGroupCmb, RequestResponsTableBean resTable)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {
			
			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//処理結果の格納
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGroupCmb.get(i);

				//結果をレスポンスデータに格納
				resTable.addFieldVale(i, "cd_busho", items[0].toString());
				resTable.addFieldVale(i, "nm_busho", items[1].toString());
				
			}

			if (lstGroupCmb.size() == 0) {

				//処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//結果をレスポンスデータに格納
				resTable.addFieldVale(0, "cd_busho", "");
				resTable.addFieldVale(0, "nm_busho", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "コンボ用：部署パラメーター格納処理が失敗しました。");

		} finally {

		}

	}
	
}
