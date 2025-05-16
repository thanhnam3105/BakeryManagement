package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.List;

//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL
//import jp.co.blueflag.shisaquick.srv.base.LogicBaseExcel;
//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL
import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * 【SA460】 試作表を生成する
 * 
 * @author k-katayama
 * @since 2009/05/20
 *
 */
public class ShisakuHyoLogic extends LogicBaseJExcel {

	/**
	 * コンストラクタ
	 */
	public ShisakuHyoLogic() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
	}

	/**
	 * 試作表を生成する
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
		//検索結果
		List<?> lstRecset1 = null;		//配合データ用検索データ
		List<?> lstRecset2 = null;		//試作データ用検索データ
		String strChuijiko = "";			//製造工程/注意事項データ
		//エクセルファイルパス
		String DownLoadPath = "";
		
		try {
			
			//出力対象データが存在しない時,Exceptionをthrowする
			if ( reqData.getCntRow(0) == 0 ) {
				this.em.ThrowException(ExceptionKind.警告Exception,"W000403", "", "", "");
				
			}
			
			//DB検索
			super.createSearchDB();
			lstRecset1 = getHaigoData(reqData);
			lstRecset2 = getShisakuData(reqData);
			strChuijiko = getChuijikoData(reqData, lstRecset2);

			//Excelファイル生成 : 試作表（横バージョン）
			DownLoadPath = makeExcelFile1(
					lstRecset1, 
					lstRecset2, 
					strChuijiko,
					reqData		
			);

			//Excelファイル生成 : 試作表（縦バージョン）
			DownLoadPath = DownLoadPath + ":::" + makeExcelFile2(
					lstRecset1, 
					lstRecset2, 
					strChuijiko,
					reqData
					);
			
			//レスポンスデータ生成
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "試作表の生成に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset1);
			removeList(lstRecset2);
			if (searchDB != null) {
				//DBセッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
			
			//変数の削除
			DownLoadPath = null;
			strChuijiko = null;

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
			ret.setID("SA460");
			
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
	 * 試作表(横バージョン)を生成する
	 * @param lstHaigoRecset : 配合データ検索データリスト
	 * @param lstShisakuRecset : 試作データ検索データリスト
	 * @param strChuijiko 
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(
			List<?> lstHaigoRecset, 
			List<?> lstShisakuRecset, 
			String strChuijiko,
			RequestResponsKindBean reqData		
	) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		Object[] items = null;
		
		//値格納用
		int intShisakuRetuSu = 0;				//試作列数
		int intShosuKeta = 0;						//小数桁数
		Double dblRyo = 0.0;						//配合量
		Double dblGoukeiJuryo = 0.0;			//配合量 合計
		
		//工程 合計量
		int intKoteiCount = 0;
		Object[] aryKoteiGoukeiRyo = null;
		List<Double> lstKoteiGoukei = new ArrayList<Double>();

		//合計仕上がり重量
		List<Object> lstShiagariJuryo = new ArrayList<Object>();				
						
		try {
// ADD start 20121019 QP@20505 No.24
			String ptSokuteichi = ""; // 測定値項目の表示パターン
			ptSokuteichi = reqData.getFieldVale(0, 0, "pattern_kotei");
// ADD end 20121019 QP@20505 No.24
			
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("試作表(横)");
			
			
			//ダウンロード用のEXCELを生成する
			
			// ① : 画面基本情報をダウンロード用EXCELに設定
			
			// 検索結果取得
			items = (Object[]) lstHaigoRecset.get(0);
			
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD Start JEXCEL対応の為
			//エクセルファイルをダウンロードフォルダに生成する
			ret = super.ExcelOutput(
			makeShisakuNo(
					reqData.getFieldVale(0, 0, "cd_shain")
					,reqData.getFieldVale(0, 0, "nen")
					,reqData.getFieldVale(0, 0, "no_oi")
					));
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD End
			
			// Excelに値をセット		
			
			//共通項目
			ExcelSetValueShisakuhin(items);		
			//総合メモ
			super.ExcelSetValue("総合メモ", toString(items[7]));
			
			
			//試作列数の取得
// 2010.11.04 Mod Arai Start  QP@00412_シサクイック改良 №.1------------------
			intShisakuRetuSu = Integer.parseInt(items[34].toString());
//			intShisakuRetuSu = Integer.parseInt(items[24].toString());
// 2010.11.04 Mod Arai End ---------------------------------------------------
						
			//試作列数分オブジェクトを用意する
			aryKoteiGoukeiRyo = new Object[intShisakuRetuSu];

			//工程合計量格納用配列、単価格納用配列の初期化
			for ( int i=0; i<intShisakuRetuSu; i++ ) {
				aryKoteiGoukeiRyo[i] = new ArrayList<Double>();
				
			}			

			// ② : 配合データをダウンロード用EXCELに設定
			for ( int i = 0; i < lstHaigoRecset.size(); i++ ) {
				
				// 検索結果取得
				items = (Object[]) lstHaigoRecset.get(i);
				
				try{

					//小数桁数
					if ( !toString(items[9]).isEmpty() ) {
						intShosuKeta = Integer.parseInt(toString(items[9]));
						
					} 

					// Excelに値をセットする
					
					//共通項目
					this.ExcelSetValueHaigo(items);		
					
					//量(列数分)
					for ( int j=0; j<intShisakuRetuSu; j++ ) {
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
//						super.ExcelSetValue("量" + (j+1), toRoundString(toDouble(items[j + 14]), intShosuKeta) );
						super.ExcelSetValue("量" + (j+1), ChkDblVal(toRoundString(toDouble(items[j + 14]), intShosuKeta)) );
//mod end --------------------------------------------------------------------------------------
						
					}

					//工程行の場合
					if ( toString(items[10]).equals("---") ) {

						//工程合計量リストの初期化
						for ( int j=0; j<aryKoteiGoukeiRyo.length; j++ ) {
							lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
							lstKoteiGoukei.add(0.0);
							aryKoteiGoukeiRyo[j] = lstKoteiGoukei;
							
						}
						//カウントを進める
						intKoteiCount++;
						
					} else {
						
						//各項目の工程合計量及び、単価を算出する
						for ( int j=0; j<aryKoteiGoukeiRyo.length; j++ ) {

							//配合量1～10を変数に格納する
							dblRyo = toDouble(items[j + 14]);

							//各工程の合計量(最大10列)を求める
							lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
							//合計値算出
							SetKoteiGoukeiRyo(lstKoteiGoukei, intKoteiCount, dblRyo);		
							aryKoteiGoukeiRyo[j] = lstKoteiGoukei;

						}			
												
					}
					
				} catch (ExceptionWaning e) {
					// 最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;
					
				} finally {
					items = null;
					
				}
				
			}
						
			//③ : 項目名を表示
			items = (Object[]) lstShisakuRecset.get(0);
// ADD start 20121019 QP@20505 No.24
			if (ptSokuteichi.equals("1")){
				// 工程パータンが１液or２液の場合
				ExcelSetValueTitle_p1and2(items);
			} else {
// ADD end 20121019 QP@20505 No.24
				ExcelSetValueTitle(items);
// ADD start 20121019 QP@20505 No.24
			}
// ADD end 20121019 QP@20505 No.24
			
			//④ : 試作データをダウンロード用EXCELに設定
			for ( int i = 0; i < lstShisakuRecset.size(); i++ ) {
				
				//検索結果取得
				items = (Object[]) lstShisakuRecset.get(i);
				
				try{
					//Excelに値をセットする
					
					//共通項目
// ADD start 20121019 QP@20505 No.24
					if (ptSokuteichi.equals("1")){
						// 工程パータンが１液or２液の場合
						this.ExcelSetValueShisaku_p1and2(items, i+1);
					} else {
// ADD end 20121019 QP@20505 No.24
						this.ExcelSetValueShisaku(items, i+1);
// ADD start 20121019 QP@20505 No.24
					}
// ADD end 20121019 QP@20505 No.24
					//コメント内容
					super.ExcelSetValue("コメント内容NO", toString(items[2]));
					super.ExcelSetValue("コメント内容NO", "");
					super.ExcelSetValue("コメント内容", "【作成メモ】\n" + toString(items[4]) + "\n\n【評価】\n" + toString(items[5]));
					super.ExcelSetValue("コメント内容", "");

					//合計仕上がり重量を設定する
					lstShiagariJuryo.add(items[19]);
					
				} catch (ExceptionWaning e) {
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;
					
				} finally {
					items = null;			
					
				}
				
			}
			
			//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL Start
			//⑤ : 製造工程/注意事項を設定
//			super.ExcelSetValue("製造工程/注意事項", strChuijiko);
			//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL End
			
			//各項目の工程合計量及び、単価を算出する
			for ( int j=0; j<intShisakuRetuSu; j++ ) {
				
				//⑥ : 工程合計量・合計重量を設定
				lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
				dblGoukeiJuryo = ExcelSetValueKotei(lstKoteiGoukei, intShosuKeta, j);
				
				//⑦ : 仕上げ重量又は、合計重量をExcelに設定
				
				try {
						
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
//					//仕上がり重量の存在チェック
//					if ( toString(lstShiagariJuryo.get(j)).isEmpty() ) {
//						//仕上がり重量の存在しない場合、合計重量を表示
//						super.ExcelSetValue("合計重量", toString(toRoundString(dblGoukeiJuryo,intShosuKeta)));
//						
//					} else {
//						//仕上がり重量の存在する場合、仕上げ重量を表示
//						super.ExcelSetValue("合計重量", toRoundString(toDouble(lstShiagariJuryo.get(j)),intShosuKeta) );
//						
//					}
					//合計重量の表示
					super.ExcelSetValue("合計重量", ChkDblVal(toRoundString(dblGoukeiJuryo,intShosuKeta)) );
					//仕上り重量の表示
					super.ExcelSetValue("合計仕上重量", ChkDblVal(toRoundString(toDouble(lstShiagariJuryo.get(j)),intShosuKeta)) );
//mod end --------------------------------------------------------------------------------------
										
				} catch (ExceptionWaning e) {
					//最大行数を超えると、ExceptionWaningがThrowされる
					//処理を終了する
					
				} finally {
					
				}
				
			}
			
			//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL Start JEXCEL対応の為
			//エクセルファイルをダウンロードフォルダに生成する
//			ret = super.ExcelOutput();
//			ret = super.ExcelOutput(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
//					);
			//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL End
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD Start JEXCEL対応の為
			super.ExcelWrite();
			super.close();
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD End
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			//リストの破棄
			removeList(lstShiagariJuryo);
			removeList(lstKoteiGoukei);

			//変数の削除
			items = null;
			dblRyo = null;			
			intKoteiCount = 0;
			lstKoteiGoukei = null;
			dblGoukeiJuryo = null;
			lstShiagariJuryo = null;
			aryKoteiGoukeiRyo = null;

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
	/**
	 * 試作表(縦バージョン)を生成する
	 * @param lstHaigoRecset : 配合データ検索データリスト
	 * @param lstShisakuRecset : 試作データ検索データリスト
	 * @param strChuijiko 
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile2(
			List<?> lstHaigoRecset, 
			List<?> lstShisakuRecset, 
			String strChuijiko,
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";
		Object[] items = null;
		
		//値格納用
		int intShisakuRetuSu = 0;				//試作列数
		int intShosuKeta = 0;						//小数桁数
		Double dblRyo = 0.0;						//配合量
		Double dblGoukeiJuryo = 0.0;			//配合量 合計
		
		//工程 合計量
		int intKoteiCount = 0;
		Object[] aryKoteiGoukeiRyo = null;
		List<Double> lstKoteiGoukei = new ArrayList<Double>();

		//合計仕上がり重量
		List<Object> lstShiagariJuryo = new ArrayList<Object>();				
						
		try {
			
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("試作表(縦)");
			
			
			//ダウンロード用のEXCELを生成する
			
// ADD start 20121019 QP@20505 No.24
			String ptSokuteichi = ""; // 測定値項目の表示パターン
			ptSokuteichi = reqData.getFieldVale(0, 0, "pattern_kotei");
// ADD end 20121019 QP@20505 No.24
			
			// ① : 画面基本情報をダウンロード用EXCELに設定
			
			// 検索結果取得
			items = (Object[]) lstHaigoRecset.get(0);

			//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL JEXCEL対応の為
			ret = super.ExcelOutput(
					makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							)
					);
			//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL
			
			// Excelに値をセット		
			
			//共通項目
			ExcelSetValueShisakuhin(items);	
						
			//試作列数の取得
// 2010.11.04 Mod Arai Start  QP@00412_シサクイック改良 №.1------------------
			intShisakuRetuSu = Integer.parseInt(items[34].toString());
//			intShisakuRetuSu = Integer.parseInt(items[24].toString());
// 2010.11.04 Mod Arai End ---------------------------------------------------
						
			//試作列数分オブジェクトを用意する
			aryKoteiGoukeiRyo = new Object[intShisakuRetuSu];

			//工程合計量格納用配列、単価格納用配列の初期化
			for ( int i=0; i<intShisakuRetuSu; i++ ) {
				aryKoteiGoukeiRyo[i] = new ArrayList<Double>();
				
			}
			

			// ② : 配合データをダウンロード用EXCELに設定
			for ( int i = 0; i < lstHaigoRecset.size(); i++ ) {
				
				// 検索結果取得
				items = (Object[]) lstHaigoRecset.get(i);
				
				try{

					//小数桁数
					if ( !toString(items[9]).isEmpty() ) {
						intShosuKeta = Integer.parseInt(toString(items[9]));
						
					} 

					// Excelに値をセットする
					
					//共通項目
					this.ExcelSetValueHaigo(items);
					
					//量(列数分)
					for ( int j=0; j<intShisakuRetuSu; j++ ) {
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
//						super.ExcelSetValue("量" + (j+1), toRoundString(toDouble(items[j + 14]), intShosuKeta) );
						super.ExcelSetValue("量" + (j+1), ChkDblVal(toRoundString(toDouble(items[j + 14]), intShosuKeta)) );
//mod end --------------------------------------------------------------------------------------
						
					}

					//工程行の場合
					if ( toString(items[10]).equals("---") ) {

						//工程合計量リストの初期化
						for ( int j=0; j<aryKoteiGoukeiRyo.length; j++ ) {
							lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
							lstKoteiGoukei.add(0.0);
							aryKoteiGoukeiRyo[j] = lstKoteiGoukei;
							
						}
						//カウントを進める
						intKoteiCount++;
						
					} else {
						
						//各項目の工程合計量及び、単価を算出する
						for ( int j=0; j<aryKoteiGoukeiRyo.length; j++ ) {

							//配合量1～10を変数に格納する
							dblRyo = toDouble(items[j + 14]);

							//各工程の合計量(最大10列)を求める
							lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
							//合計値算出
							SetKoteiGoukeiRyo(lstKoteiGoukei, intKoteiCount, dblRyo);		
							aryKoteiGoukeiRyo[j] = lstKoteiGoukei;

						}			
												
					}
					
				} catch (ExceptionWaning e) {
					// 最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;
					
				} finally {
					items = null;
					
				}
				
			}

			//③ : 項目名を表示
			items = (Object[]) lstShisakuRecset.get(0);
// ADD start 20121019 QP@20505 No.24
			if (ptSokuteichi.equals("1")){
				// 工程パータンが１液or２液の場合
				ExcelSetValueTitle_p1and2(items);
			} else {
// ADD end 20121019 QP@20505 No.24
				ExcelSetValueTitle(items);
// ADD start 20121019 QP@20505 No.24
			}
// ADD end 20121019 QP@20505 No.24
							
			//④ : 試作データをダウンロード用EXCELに設定
			for ( int i = 0; i < lstShisakuRecset.size(); i++ ) {
				
				//検索結果取得
				items = (Object[]) lstShisakuRecset.get(i);
				
				try{
					//Excelに値をセットする
					
					//共通項目
// ADD start 20121019 QP@20505 No.24
					if (ptSokuteichi.equals("1")){
						// 工程パータンが１液or２液の場合
						this.ExcelSetValueShisaku_p1and2(items, i+1);
					} else {
// ADD end 20121019 QP@20505 No.24
						this.ExcelSetValueShisaku(items, i+1);
// ADD start 20121019 QP@20505 No.24
					}
// ADD end 20121019 QP@20505 No.24
					//合計仕上がり重量を設定する
					lstShiagariJuryo.add(items[19]);
				} catch (ExceptionWaning e) {
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;
					
				} finally {
					items = null;			
					
				}
				
			}

			//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL Start
			//⑤ : 製造工程/注意事項を設定
//			super.ExcelSetValue("製造工程/注意事項", strChuijiko);
			//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL End

			//各項目の工程合計量及び、単価を算出する
			for ( int j=0; j<intShisakuRetuSu; j++ ) {
				
				//⑥ : 工程合計量・合計重量を設定
				lstKoteiGoukei = (ArrayList<Double>)aryKoteiGoukeiRyo[j];
				dblGoukeiJuryo = ExcelSetValueKotei(lstKoteiGoukei, intShosuKeta, j);
				
				//⑦ : 仕上げ重量又は、合計重量をExcelに設定
				
				try {

//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
//					//仕上がり重量の存在チェック
//					if ( toString(lstShiagariJuryo.get(j)).isEmpty() ) {
//						//仕上がり重量の存在しない場合、合計重量を表示
//						super.ExcelSetValue("合計重量", toString(toRoundString(dblGoukeiJuryo,intShosuKeta)));
//						
//					} else {
//						//仕上がり重量の存在する場合、仕上げ重量を表示
//						super.ExcelSetValue("合計重量", toRoundString(toDouble(lstShiagariJuryo.get(j)),intShosuKeta) );
//						
//					}
					//合計重量の表示
					super.ExcelSetValue( "合計重量", ChkDblVal(toRoundString(dblGoukeiJuryo,intShosuKeta)) );
					//仕上り重量の表示
					super.ExcelSetValue( "合計仕上重量", ChkDblVal(toRoundString(toDouble(lstShiagariJuryo.get(j)),intShosuKeta)) );
//mod end --------------------------------------------------------------------------------------
					
				} catch (ExceptionWaning e) {
					//最大行数を超えると、ExceptionWaningがThrowされる
					//処理を終了する
					
				} finally {
					
				}
				
			}
					
			//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL Start JEXCEL対応の為
			//エクセルファイルをダウンロードフォルダに生成する
//			ret = super.ExcelOutput();
//			ret = super.ExcelOutput(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
//					);
			//【QP@20505】No3 2012/10/09 TT H.SHIMA DEL End
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD Start JEXCEL対応の為
			super.ExcelWrite();
			super.close();
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD End
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			//リストの破棄
			removeList(lstShiagariJuryo);
			removeList(lstKoteiGoukei);

			//変数の削除
			items = null;
			dblRyo = null;			
			intKoteiCount = 0;
			lstKoteiGoukei = null;
			dblGoukeiJuryo = null;
			lstShiagariJuryo = null;
			aryKoteiGoukeiRyo = null;

		}
		return ret;
		
	}
	
	/**
	 * 試作品テーブルデータをExcelに設定
	 *  : 共通項目の値を設定する
	 * @param lstShisakuRecset
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void ExcelSetValueShisakuhin(Object[] items)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//Excelに値を設定
			super.ExcelSetValue("試作コード", toString(items[0]));
			super.ExcelSetValue("依頼番号", toString(items[1]));
			super.ExcelSetValue("品名", toString(items[2]));
			super.ExcelSetValue("所属", toString(items[3]));
			super.ExcelSetValue("選択工場", toString(items[4]));
			super.ExcelSetValue("試作者", toString(items[5]));
			super.ExcelSetValue("発行日", toString(items[6]));
			super.ExcelSetValue("試作メモ", toString(items[8]));

		} catch(Exception e) {
			em.ThrowException(e, "");
			 
		} finally {
			
		}
		
	}
	
	/**
	 * 配合データをExcelに設定
	 *  : 共通項目の値を設定する
	 * @param lstShisakuRecset
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void ExcelSetValueHaigo(Object[] items)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//Excelに値を設定
			super.ExcelSetValue("原料コード", toString(items[10]));
			super.ExcelSetValue("原料名", toString(items[11]));
			
//mod start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
//			super.ExcelSetValue("単価", toRoundString(toDouble(items[12]), 2) );
//			super.ExcelSetValue("歩留", toRoundString(toDouble(items[13]), 2) );
			//単価・歩留の設定(工程行の場合は、空白を設定する)
			if ( toString(items[10]).equals("---") ) {
				super.ExcelSetValue("単価", "" );
				super.ExcelSetValue("歩留", "" );
			} else {
				super.ExcelSetValue("単価", toRoundString(toDouble(items[12]), 2) );		
				super.ExcelSetValue("歩留", toRoundString(toDouble(items[13]), 2) );		
			}
//mod end --------------------------------------------------------------------------------------

		} catch(Exception e) {
			em.ThrowException(e, "");
			 
		} finally {
			
		}
		
	}
	
	/**
	 * 試作データをExcelに設定
	 *  : 共通項目の値を設定する
	 * @param items : 試作データ
	 * @param columnNo : 列番号
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
		private void ExcelSetValueShisaku(Object[] items, int columnNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strLinkNmVal = "値" + columnNo;
		String strLinkNmTitle = "タイトル";
		
		try {
			//Excelに値を設定
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD Start
			if(columnNo <= 3 ){
				super.ExcelSetValue("製品_サンプルNo" + columnNo, "サンプル:" + toString(items[2]));
				super.ExcelSetValue("製品_工程版No" + columnNo, "工程版:" + toString(items[18]));
				super.ExcelSetValue("製品_注意事項" + columnNo, "" + toString(items[50]));
			}
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD End
			
			super.ExcelSetValue("日付", toString(items[1]));
			super.ExcelSetValue("NO", toString(items[2]));
			super.ExcelSetValue("メモ", toString(items[3]));
			
			//フラグが1の場合、表示
			if ( toString(items[28]).equals("1") ) {
				//総酸
				super.ExcelSetValue(strLinkNmVal, toString(items[6]));
				
			}
			if ( toString(items[29]).equals("1") ) {
				//食塩
				super.ExcelSetValue(strLinkNmVal, toString(items[7]));
				
			}
			if ( toString(items[30]).equals("1") ) {
				//水相中酸度
				super.ExcelSetValue(strLinkNmVal, toString(items[8]));
				
			}
			if ( toString(items[31]).equals("1") ) {
				//水相中食塩
				super.ExcelSetValue(strLinkNmVal, toString(items[9]));
				
			}
			if ( toString(items[32]).equals("1") ) {
				//水相中酢酸
				super.ExcelSetValue(strLinkNmVal, toString(items[10]));
				
			}
			if ( toString(items[33]).equals("1") ) {
				//糖度
				super.ExcelSetValue(strLinkNmVal, toString(items[11]));
				
			}
			if ( toString(items[34]).equals("1") ) {
				//粘度
				super.ExcelSetValue(strLinkNmVal, toString(items[12]));
				
			}
			if ( toString(items[35]).equals("1") ) {
				//温度
				super.ExcelSetValue(strLinkNmVal, toString(items[13]));
				
			}
			if ( toString(items[36]).equals("1") ) {
				//pH
				super.ExcelSetValue(strLinkNmVal, toString(items[14]));
				
			}
			if ( toString(items[37]).equals("1") ) {
				//総酸（分析）
				super.ExcelSetValue(strLinkNmVal, toString(items[15]));
				
			}
			if ( toString(items[38]).equals("1") ) {
				//食塩（分析）
				super.ExcelSetValue(strLinkNmVal, toString(items[16]));
				
			}
			if ( toString(items[39]).equals("1") ) {
				//比重
				super.ExcelSetValue(strLinkNmVal, toString(items[17]));
				
			}
			
			//水相比重
			if ( toString(items[48]).equals("1") ) {
				//水相比重
				super.ExcelSetValue(strLinkNmVal, toString(items[49]));
				
			}
// MDO start 20121019 QP@20505 No.24
//			if ( toString(items[40]).equals("1") ) {
//				// フリー水分活性
//				super.ExcelSetValue(strLinkNmVal, toString(items[20]));
//				
//			}
//			if ( toString(items[41]).equals("1") ) {
//				//フリーアルコール
//				super.ExcelSetValue(strLinkNmVal, toString(items[21]));
//				
//			}
			if ( toString(items[55]).equals("1") ) {
				//フリー水分活性タイトル&フリー水分活性
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[51]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[52]));
			}
			if ( toString(items[56]).equals("1") ) {
				//フリー水分活性タイトル&フリー水分活性
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[53]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[54]));
			}
// MOD end 20121019 QP@20505 No.24

			if ( toString(items[42]).equals("1") ) {
				//フリータイトル&フリー内容 ①
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[22]));
					
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[23]));
				
			}
			if ( toString(items[43]).equals("1") ) {
				//フリータイトル&フリー内容 ②
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[24]));
				
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[25]));
				
			}
			if ( toString(items[44]).equals("1") ) {
				//フリータイトル&フリー内容 ③
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[26]));
					
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[27]));
				
			}
			
			//原価(kg)
			super.ExcelSetValue("原価Kg", toDouble(items[45]));
			
//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add Start --------------------------
			//原価(個)
			super.ExcelSetValue("原価個", toDouble(items[47]));
//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add End --------------------------

		} catch(Exception e) {
			em.ThrowException(e, "");
			 
		} finally {
			strLinkNmVal = null;
			strLinkNmTitle = null;
			
		}
		
	}

// ADD start 20121019 QP@20505 No.24
	/**
	 * 試作データをExcelに設定
	 *  : 共通項目の値を設定する
	 * @param items : 試作データ
	 * @param columnNo : 列番号
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void ExcelSetValueShisaku_p1and2(Object[] items, int columnNo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strLinkNmVal = "値" + columnNo;
		String strLinkNmTitle = "タイトル";
		
		try {
			//Excelに値を設定
	//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD Start
			if(columnNo <= 3 ){
				super.ExcelSetValue("製品_サンプルNo" + columnNo, "サンプル:" + toString(items[2]));
				super.ExcelSetValue("製品_工程版No" + columnNo, "工程版:" + toString(items[18]));
				super.ExcelSetValue("製品_注意事項" + columnNo, "" + toString(items[50]));
			}
	//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD End
			super.ExcelSetValue("日付", toString(items[1]));
			super.ExcelSetValue("NO", toString(items[2]));
			super.ExcelSetValue("メモ", toString(items[3]));
			
			//フラグが1の場合、表示
			if ( toString(items[28]).equals("1") ) {
				//総酸
				super.ExcelSetValue(strLinkNmVal, toString(items[6]));
			}
			if ( toString(items[29]).equals("1") ) {
				//食塩
				super.ExcelSetValue(strLinkNmVal, toString(items[7]));
			}
			if ( toString(items[30]).equals("1") ) {
				//水相中酸度
				super.ExcelSetValue(strLinkNmVal, toString(items[8]));
			}
			if ( toString(items[31]).equals("1") ) {
				//水相中食塩
				super.ExcelSetValue(strLinkNmVal, toString(items[9]));
			}
			if ( toString(items[32]).equals("1") ) {
				//水相中酢酸
				super.ExcelSetValue(strLinkNmVal, toString(items[10]));
			}
			if ( toString(items[69]).equals("1") ) {
				//実効酢酸濃度
				super.ExcelSetValue(strLinkNmVal, toString(items[57]));
			}
			if ( toString(items[70]).equals("1") ) {
				//水相中MSG
				super.ExcelSetValue(strLinkNmVal, toString(items[58]));
			}
			if ( toString(items[36]).equals("1") ) {
				//pH
				super.ExcelSetValue(strLinkNmVal, toString(items[14]));
			}
			if ( toString(items[39]).equals("1") ) {
				//製品比重
				super.ExcelSetValue(strLinkNmVal, toString(items[17]));
			}
			if ( toString(items[48]).equals("1") ) {
				//水相比重
				super.ExcelSetValue(strLinkNmVal, toString(items[49]));
			}
			if ( toString(items[71]).equals("1") ) {
				//フリー粘度タイトル&フリー粘度
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[59]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[60]));
			}
			if ( toString(items[71]).equals("1") ) { // 粘度とフラグチェックボックス共通
				//フリー温度タイトル&フリー温度
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[61]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[62]));
			}
			if ( toString(items[42]).equals("1") ) {
				//フリータイトル&フリー内容 ①
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[22]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[23]));
			}
			if ( toString(items[43]).equals("1") ) {
				//フリータイトル&フリー内容 ②
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[24]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[25]));
			}
			if ( toString(items[44]).equals("1") ) {
				//フリータイトル&フリー内容 ③
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[26]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[27]));
			}
			if ( toString(items[73]).equals("1") ) {
				//フリータイトル&フリー内容 ④
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[63]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[64]));
			}
			if ( toString(items[74]).equals("1") ) {
				//フリータイトル&フリー内容 ⑤
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[65]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[66]));
			}
			if ( toString(items[75]).equals("1") ) {
				//フリータイトル&フリー内容 ⑥
				if ( columnNo == 1 ) {
					super.ExcelSetValue(strLinkNmTitle, toString(items[67]));
				}
				super.ExcelSetValue(strLinkNmVal, toString(items[68]));
			}
			//原価(kg)
			super.ExcelSetValue("原価Kg", toDouble(items[45]));
			//原価(個)
			super.ExcelSetValue("原価個", toDouble(items[47]));
		} catch(Exception e) {
			em.ThrowException(e, "");
		} finally {
			strLinkNmVal = null;
			strLinkNmTitle = null;
		}
	}
// ADD end 20121019 QP@20505 No.24

	/**
	 * 工程合計量をExcelに設定
	 * @param lstKoteiGoukei : 工程合計量リスト
	 * @param intShosuKeta : 小数桁数
	 * @param intColumn : 列数
	 * @return 合計重量
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private Double ExcelSetValueKotei(List<Double> lstKoteiGoukei, int intShosuKeta, int intColumn)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Double ret = 0.0;
		
		try {

			for(int i=0; i<lstKoteiGoukei.size(); i++) {
				
				//合計重量を求める
				ret += lstKoteiGoukei.get(i);
				
				//工程合計量タイトルをExcelに値をセット				
				if ( intColumn == 0 ) {
					if ( i==0 ) {
						super.ExcelSetValue("工程タイトル", "１工程(g)");
						
					} else {
						super.ExcelSetValue("工程タイトル", Integer.toString(i+1).toUpperCase() + "工程(g)");
						
					}
					
				}

				//工程合計量をExcelに値をセット
				if ( lstKoteiGoukei.get(i) != 0.0 ) {
					super.ExcelSetValue("工程合計量" + (intColumn+1), toRoundString(toDouble(lstKoteiGoukei.get(i)),intShosuKeta) );
					
				} else {
					super.ExcelSetValue("工程合計量" + (intColumn+1), "");
					
				}
								
			}

		} catch (ExceptionWaning e) {
			//最大行数を超えると、ExceptionWaningがThrowされる
			//処理を終了する
			
		} catch(Exception e) {
			this.em.ThrowException(e, "工程合計量出力処理に失敗しました。");
			
		} finally {
			
		}
		return ret;
		
	}
	
	/**
	 * 項目名をExcelに設定
	 *  : 合計重量～比重までの項目名を一覧に表示
	 * @param items : 試作データ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void ExcelSetValueTitle(Object[] items)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strLinkNmTitle = "タイトル";
		
		try {

			//Excelに表示
			super.ExcelSetValue("合計重量タイトル", "合計重量(g)");
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
			super.ExcelSetValue("合計仕上重量タイトル", "合計仕上重量(g)");
//add end --------------------------------------------------------------------------------------	
			super.ExcelSetValue("原価Kgタイトル", "原料費(Kg)");
			
//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add Start --------------------------
			super.ExcelSetValue("原価個タイトル", "原料費(1個)");
//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add End --------------------------
			
			//フラグが1の場合、表示
			if ( toString(items[28]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "総酸");
				
			}
			if ( toString(items[29]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "食塩");
				
			}
			if ( toString(items[30]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "水相中酸度");
				
			}
			if ( toString(items[31]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "水相中食塩");
				
			}
			if ( toString(items[32]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "水相中酢酸");
				
			}
			if ( toString(items[33]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "糖度");
				
			}
			if ( toString(items[34]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "粘度");
				
			}
			if ( toString(items[35]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "温度");
				
			}
			if ( toString(items[36]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "pH");
				
			}
			if ( toString(items[37]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "総酸（分析）");
				
			}
			if ( toString(items[38]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "食塩（分析）");
				
			}
			if ( toString(items[39]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "製品比重");
				
			}
			if ( toString(items[48]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "水相比重");
				
			}
// DEL start 20121019 QP@20505 No.24
//				if ( toString(items[40]).equals("1") ) {
//					super.ExcelSetValue(strLinkNmTitle, "水分活性");
//					
//				}
//				if ( toString(items[41]).equals("1") ) {
//					super.ExcelSetValue(strLinkNmTitle, "アルコール");
//					
//				}
// DEL end 20121019 QP@20505 No.24

		} catch(Exception e) {
			this.em.ThrowException(e, "項目名設定処理に失敗しました。");
			
		} finally {
			
		}
	}
	
// ADD start 20121019 QP@20505 No.24
	/**
	 * 項目名をExcelに設定（工程パターン１液or２液の場合）
	 *  : 合計重量～比重までの項目名を一覧に表示
	 * @param items : 試作データ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void ExcelSetValueTitle_p1and2(Object[] items)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strLinkNmTitle = "タイトル";
		
		try {
			//Excelに表示
			super.ExcelSetValue("合計重量タイトル", "合計重量(g)");
			super.ExcelSetValue("合計仕上重量タイトル", "合計仕上重量(g)");
			super.ExcelSetValue("原価Kgタイトル", "原料費(Kg)");
			super.ExcelSetValue("原価個タイトル", "原料費(1個)");

			//フラグが1の場合、表示
			if ( toString(items[28]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "総酸");
			}
			if ( toString(items[29]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "食塩");
			}
			if ( toString(items[30]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "水相中酸度");
			}
			if ( toString(items[31]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "水相中食塩");
			}
			if ( toString(items[32]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "水相中酢酸");
			}
			if ( toString(items[69]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "実効酢酸濃度");
			}
			if ( toString(items[70]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "水相中ＭＳＧ");
			}
			if ( toString(items[36]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "pH");
			}
			if ( toString(items[39]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "製品比重");
			}
			if ( toString(items[48]).equals("1") ) {
				super.ExcelSetValue(strLinkNmTitle, "水相比重");
			}
		} catch(Exception e) {
			this.em.ThrowException(e, "項目名設定処理に失敗しました。");
		} finally {
		}
	}
// ADD end 20121019 QP@20505 No.24
	
	/**
	 * 対象の配合データを検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getHaigoData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> lstHaigoData = null;
		List<?> lstShisakuListData = null;
		//出力データ
		List<?> ret = null;
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//① :　配合データの検索
			
			//SQL文の作成
			strSql = MakeHaigoSQLBuf(KindBean);
			
			//SQLを実行
			lstHaigoData = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstHaigoData.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
				
			}

			//② :　試作リストデータの検索
			
			//SQL文の作成
			strSql = MakeShisakuListSQLBuf(KindBean);
			
			//SQLを実行
			lstShisakuListData = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (lstShisakuListData.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
				
			}

			//③ 配合データと試作リストデータを合わせて、出力用リストを作成する
			ret = CreateHaigoShisakuListData(lstHaigoData, lstShisakuListData);
			
		} catch (Exception e) {
			em.ThrowException(e, "試作表出力、配合データ検索に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstHaigoData);
			removeList(lstShisakuListData);
			
			//変数の削除
			strSql = null;

		}
		return ret;
		
	}
	/**
	 * 対象の試作データを検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getShisakuData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//検索結果
		List<?> ret = null;
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//① 試作データ検索処理

			//SQL文の作成
			strSql = MakeShisakuSQLBuf(KindBean);
			
			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());
			
			//検索結果がない場合
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "試作表出力、試作データ検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;

		}
		return ret;
		
	}

	/**
	 * 注意事項取得処理
	 * @param KindBean : リクエストデータ（機能）
	 * @param lstShisakuData : 試作データ検索データ
	 * @return　検索結果
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private String getChuijikoData(RequestResponsKindBean reqData, List<?> lstShisakuData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//注意事項
		String ret = null;		
		//検索結果
		List<?> lstRecset = null;
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();
						
		try {
			
			//① : 製造工程データ検索用SQLを取得する
			strSql = MakeSeizokoteiSQLBuf(reqData);
			
			//③ : 取得した検索用SQLを実行する
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//⑤ : 検索結果がある場合、最新の注意事項を取得する（注意事項NOの最大値を判定）
			if (lstRecset.size() != 0){
				
				int intMaxChuiNo = 0;

				for ( int j=0; j<lstRecset.size(); j++ ) {
					//製造工程検索データの取得
					Object[] items = (Object[])lstRecset.get(j);
					
					//注意事項NOの最大値を判定し、最大値の注意事項を取得する
					if ( !toString(items[0]).isEmpty() ) {
					
						if ( intMaxChuiNo < Integer.parseInt(toString(items[0]))  ) {
							//注意事項NOを設定
							intMaxChuiNo = Integer.parseInt(toString(items[0]));		
							//注意事項を設定
							ret = toString(items[1]);											
							
						}
					
					}
					
				}
				
			}
								
		} catch (Exception e) {
			em.ThrowException(e, "注意事項取得処理に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			
			//変数の削除
			strSql = null;

		}
		return ret;
		
	}	
	
	/**
	 * リクエストデータより、配合データ検索SQLを生成する
	 *  : 試作品テーブル、配合テーブルを検索し、配合データを生成する。
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 配合データ検索SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeHaigoSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strCd_kotei = "";
		String strSeq_kotei = "";

		try {

			//リクエストパラメータより、試作コードを取得する
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL文の作成
			ret.append(" SELECT DISTINCT ");
			ret.append("  RIGHT('0000000000'+CONVERT(varchar,T110.cd_shain),10)  ");
			ret.append("   +'-'+ RIGHT('00'+CONVERT(varchar,T110.nen),2)  ");
			ret.append("   +'-'+ RIGHT('000'+CONVERT(varchar,T110.no_oi),3) AS 試作コード  ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T110.no_irai),'') AS 依頼番号 ");
			ret.append("  ,T110.nm_hin AS 品名 ");
			ret.append("  ,ISNULL(T110.memo,'') AS メモ ");
			ret.append("  ,M104.nm_kaisha AS 所属 ");
			ret.append("  ,M104.nm_busho AS 選択工場 ");
			ret.append("  ,M101_shain.nm_user AS 試作者 ");
			ret.append("  ,CONVERT(VARCHAR,GETDATE(),111) + ' ' ");
			ret.append("    + CONVERT(VARCHAR,DATEPART(hour,GETDATE())) + ':' ");
			ret.append("    + CONVERT(VARCHAR,DATEPART(minute,GETDATE()))  AS 発行日 ");
			ret.append("  ,T120.cd_kotei AS cd_kotei ");
			ret.append("  ,T120.seq_kotei AS seq_kotei ");
			ret.append("  ,ISNULL(T120.nm_kotei,'') AS 工程名 ");
			ret.append("  ,ISNULL(T120.zoku_kotei,'') AS 工程属性 ");
			ret.append("  ,ISNULL(T120.cd_genryo,'') AS 原料コード ");
			ret.append("  ,ISNULL(T120.nm_genryo,'') AS 原料名 ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.tanka),'') AS 単価 ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.budomari),'') AS 歩留 ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.ritu_abura),'') AS 油含有率 ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.ritu_sakusan),'') AS 酢酸 ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.ritu_shokuen),'') AS 食塩 ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.ritu_sousan),'') AS 総酸 ");
			ret.append("  ,ISNULL(M302.value1,0) AS 小数桁数 ");
			ret.append("  ,ISNULL(T110.memo_shisaku,'') AS 試作メモ ");
			ret.append("  ,ISNULL(T120.color,'') AS color ");
			ret.append("  ,T120.sort_kotei AS sort_kotei ");
			ret.append("  ,T120.sort_genryo AS sort_genryo ");
			ret.append(" FROM tr_shisakuhin T110 ");
			ret.append("  LEFT JOIN tr_haigo T120 ");
			ret.append("   ON  T120.cd_shain = T110.cd_shain ");
			ret.append("   AND T120.nen = T110.nen ");
			ret.append("   AND T120.no_oi = T110.no_oi ");
			ret.append("  LEFT JOIN ma_busho M104 ");
			ret.append("   ON  M104.cd_kaisha = T110.cd_kaisha ");
			ret.append("   AND M104.cd_busho = T110.cd_kojo ");
			ret.append("  LEFT JOIN ma_user M101_shain ");
			ret.append("   ON  M101_shain.id_user = T110.cd_shain ");
			ret.append("  LEFT JOIN ma_literal M302 ");
			ret.append("   ON  M302.cd_category = 'K_shosu' ");
			ret.append("   AND  M302.cd_literal = T110.keta_shosu ");
			ret.append(" WHERE  ");
			
			//試作コードを検索条件に設定
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);
			
			//工程CD・工程SEQを検索条件に設定
			ret.append(" AND ( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//リクエストパラメータより、工程CD・工程SEQを取得する
				strCd_kotei = reqData.getFieldVale(0, i, "cd_kotei");
				strSeq_kotei = reqData.getFieldVale(0, i, "seq_kotei");
				
				//条件を追加
				if ( i != 0 ) {
					ret.append("  OR ");
				}
				ret.append("   (T120.cd_kotei=" + strCd_kotei);
				ret.append("    AND T120.seq_kotei=" + strSeq_kotei + ")");
				
			}
			ret.append(" ) ");

			//取得順
			ret.append(" ORDER BY T120.sort_kotei, T120.sort_genryo ");

			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作表、配合データ検索SQLの生成に失敗しました。");
			
		} finally {
			//変数の削除
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strCd_kotei = null;
			strSeq_kotei = null;
	
		}
		return ret;
		
	}
		
	/**
	 * リクエストデータより、試作リストデータ検索SQLを生成する
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 試作データ検索SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeShisakuListSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";
		String strCd_kotei = "";
		String strSeq_kotei = "";

		try {

			//リクエストパラメータより、試作コードを取得する
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL文の作成
			ret.append(" SELECT T132.seq_shisaku AS seq_shisaku ");
			ret.append("    ,T132.cd_kotei AS cd_kotei ");
			ret.append("    ,T132.seq_kotei AS seq_kotei ");
			ret.append("    ,ISNULL(CONVERT(VARCHAR,T132.quantity),'') AS 量 ");
			ret.append("    ,ISNULL(T132.color,'') AS color ");
			ret.append("  FROM tr_shisakuhin T110 ");
			ret.append("    LEFT JOIN tr_haigo T120 ");
			ret.append("     ON  T120.cd_shain = T110.cd_shain ");
			ret.append("     AND T120.nen = T110.nen ");
			ret.append("     AND T120.no_oi = T110.no_oi ");
			ret.append("    LEFT JOIN tr_shisaku T131 ");
			ret.append("     ON  T131.cd_shain = T110.cd_shain ");
			ret.append("     AND T131.nen = T110.nen ");
			ret.append("     AND T131.no_oi = T110.no_oi ");
			ret.append("    LEFT JOIN tr_shisaku_list T132 ");
			ret.append("     ON  T132.cd_shain = T110.cd_shain ");
			ret.append("     AND T132.nen = T110.nen ");
			ret.append("     AND T132.no_oi = T110.no_oi ");
			ret.append("     AND T132.seq_shisaku = T131.seq_shisaku ");
			ret.append("     AND T132.cd_kotei = T120.cd_kotei ");
			ret.append("     AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" WHERE  ");

			//試作コードを検索条件に設定
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);

			//試作SEQ
			ret.append("  AND T132.seq_shisaku IN( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//リクエストパラメータより、試作SEQを取得する
				strSeq_shisaku = reqData.getFieldVale(0, i, "seq_shisaku");
				
				//SQL文に追加
				if ( i != 0 ) {
					ret.append(",");
					
				}
				ret.append(strSeq_shisaku);
				
			}
			ret.append("  ) ");
			
			//工程CD・工程SEQを検索条件に設定
			ret.append(" AND ( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//リクエストパラメータより、工程CD・工程SEQを取得する
				strCd_kotei = reqData.getFieldVale(0, i, "cd_kotei");
				strSeq_kotei = reqData.getFieldVale(0, i, "seq_kotei");
				
				//条件を追加
				if ( i != 0 ) {
					ret.append("  OR ");
					
				}
				ret.append("   (T132.cd_kotei=" + strCd_kotei);
				ret.append("    AND T132.seq_kotei=" + strSeq_kotei + ")");
				
			}
			ret.append(" ) ");
			
			//取得順
			ret.append(" ORDER BY T131.sort_shisaku, T120.sort_kotei, T120.sort_genryo ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作表、試作リストデータ検索SQLの生成に失敗しました。");
			
		} finally {
			//変数の削除
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strSeq_shisaku = null;
			strCd_kotei = null;
			strSeq_kotei = null;
	
		}
		return ret;
		
	}
	
	/**
	 * リクエストデータより、試作データ検索SQLを生成する
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 試作データ検索SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeShisakuSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {
			//リクエストパラメータより、試作コードを取得する
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			
			//SQL文の作成
			ret.append(" SELECT  ");
			ret.append("    T131.seq_shisaku AS seq_shisaku "); // 0
			ret.append("   ,ISNULL(CONVERT(VARCHAR,T131.dt_shisaku,111),'') AS 試作日 "); // 1
			ret.append("   ,ISNULL(T131.nm_sample,'') AS サンプルNO "); // 2
			ret.append("   ,ISNULL(T131.memo,'') AS メモ "); // 3
//			ret.append("   ,CASE WHEN T131.flg_memo=0 ");
//			ret.append("     THEN ISNULL(T131.memo_sakusei,'') ");
//			ret.append("     ELSE '' END AS 作成メモ ");
			ret.append("   ,ISNULL(T131.memo_sakusei,'') AS 作成メモ "); // 4
//			ret.append("   ,CASE WHEN T131.flg_hyoka=0 ");
//			ret.append("     THEN ISNULL(T131.hyoka,'') ");
//			ret.append("     ELSE '' END AS 評価 ");
			ret.append("   ,ISNULL(T131.hyoka,'') AS 評価 "); // 5
			ret.append("   ,CASE WHEN T131.flg_sousan=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.ritu_sousan),'') ");
			ret.append("     ELSE '' END AS 総酸 "); // 6
			ret.append("   ,CASE WHEN T131.flg_shokuen=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.ritu_shokuen),'') ");
			ret.append("     ELSE '' END AS 食塩 "); // 7
			ret.append("   ,CASE WHEN T131.flg_sando_suiso=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.sando_suiso),'') ");
			ret.append("     ELSE '' END AS 水相中酸度 "); // 8
			ret.append("   ,CASE WHEN T131.flg_shokuen_suiso=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.shokuen_suiso),'') ");
			ret.append("     ELSE '' END AS 水相中食塩 "); // 9
			ret.append("   ,CASE WHEN T131.flg_sakusan_suiso=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.sakusan_suiso),'') ");
			ret.append("     ELSE '' END AS 水相中酢酸 "); // 10
			ret.append("   ,CASE WHEN T131.flg_toudo=1 ");
			ret.append("     THEN ISNULL(T131.toudo,'') ");
			ret.append("     ELSE '' END AS 糖度 "); // 11
			ret.append("   ,CASE WHEN T131.flg_nendo=1 ");
			ret.append("     THEN ISNULL(T131.nendo,'') ");
			ret.append("     ELSE '' END AS 粘度 "); // 12
			ret.append("   ,CASE WHEN T131.flg_ondo=1 ");
			ret.append("     THEN ISNULL(T131.ondo,'') ");
			ret.append("     ELSE '' END AS 温度 "); // 13
			ret.append("   ,CASE WHEN T131.flg_ph=1 ");
			ret.append("     THEN ISNULL(T131.ph,'') ");
			ret.append("     ELSE '' END AS PH "); // 14
			ret.append("   ,CASE WHEN T131.flg_sousan_bunseki=1 ");
			ret.append("     THEN ISNULL(T131.ritu_sousan_bunseki,'') ");
			ret.append("     ELSE '' END AS 総酸分析 "); // 15
			ret.append("   ,CASE WHEN T131.flg_shokuen_bunseki=1 ");
			ret.append("     THEN ISNULL(T131.ritu_shokuen_bunseki,'') ");
			ret.append("     ELSE '' END AS 食塩分析 "); // 16
			ret.append("   ,CASE WHEN T131.flg_hiju=1 ");
			ret.append("     THEN ISNULL(T131.hiju,'') ");
			ret.append("     ELSE '' END AS 比重 "); // 17
			ret.append("   ,ISNULL(T131.no_chui,'') AS 注意事項No "); // 18
			ret.append("   ,ISNULL(CONVERT(VARCHAR,T131.juryo_shiagari_g),'') AS 合計仕上がり重量 "); // 19
			//水分活性 ～ フリー③
			ret.append("  ,CASE WHEN T131.flg_suibun_kasei=1 THEN ISNULL(T131.suibun_kasei,'') ELSE '' END AS 水分活性 "); // 20
			ret.append("  ,CASE WHEN T131.flg_alcohol=1 THEN ISNULL(T131.alcohol,'') ELSE '' END AS アルコール "); // 21
			ret.append("  ,CASE WHEN T131.flg_free1=1 THEN ISNULL(T131.free_title1,'') ELSE '' END AS フリータイトル1 "); // 22
			ret.append("  ,CASE WHEN T131.flg_free1=1 THEN ISNULL(T131.free_value1,'') ELSE '' END AS フリー内容1 "); // 23
			ret.append("  ,CASE WHEN T131.flg_free2=1 THEN ISNULL(T131.free_title2,'') ELSE '' END AS フリータイトル2 "); // 24
			ret.append("  ,CASE WHEN T131.flg_free2=1 THEN ISNULL(T131.free_value2,'') ELSE '' END AS フリー内容2 "); // 25
			ret.append("  ,CASE WHEN T131.flg_free3=1 THEN ISNULL(T131.free_title3,'') ELSE '' END AS フリータイトル3 "); // 26
			ret.append("  ,CASE WHEN T131.flg_free3=1 THEN ISNULL(T131.free_value3,'') ELSE '' END AS フリー内容3 "); // 27
			//フラグ取得 総酸～アルコール
			ret.append("  ,ISNULL(T131.flg_sousan,0) AS flg_sousan "); // 28
			ret.append("  ,ISNULL(T131.flg_shokuen,0) AS flg_shokuen "); // 29
			ret.append("  ,ISNULL(T131.flg_sando_suiso,0) AS flg_sando_suiso "); // 30
			ret.append("  ,ISNULL(T131.flg_shokuen_suiso,0) AS flg_shokuen_suiso "); // 31
			ret.append("  ,ISNULL(T131.flg_sakusan_suiso,0) AS flg_sakusan_suiso "); // 32
			ret.append("  ,ISNULL(T131.flg_toudo,0) AS flg_toudo "); // 33
			ret.append("  ,ISNULL(T131.flg_nendo,0) AS flg_nendo "); // 34
			ret.append("  ,ISNULL(T131.flg_ondo,0) AS flg_ondo "); // 35
			ret.append("  ,ISNULL(T131.flg_ph,0) AS flg_ph "); // 36
			ret.append("  ,ISNULL(T131.flg_sousan_bunseki,0) AS flg_sousan_bunseki "); // 37
			ret.append("  ,ISNULL(T131.flg_shokuen_bunseki,0) AS flg_shokuen_bunseki "); // 38
			ret.append("  ,ISNULL(T131.flg_hiju,0) AS flg_hiju "); // 39
			ret.append("  ,ISNULL(T131.flg_suibun_kasei,0) AS flg_suibun_kasei "); // 40
			ret.append("  ,ISNULL(T131.flg_alcohol,0) AS flg_alcohol "); // 41

			ret.append("  ,ISNULL(T131.flg_free1,0) AS flg_free1 "); // 42
			ret.append("  ,ISNULL(T131.flg_free2,0) AS flg_free2 "); // 43
			ret.append("  ,ISNULL(T131.flg_free3,0) AS flg_free3 "); // 44
			//原価（kg） : 原価原料テーブル.原料費
			ret.append("  ,T141.genryohi AS genka "); // 45
			//試作順
			ret.append("   ,T131.sort_shisaku AS sort_shisaku "); // 46
	//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add Start --------------------------
			//原価（個） : 原価原料テーブル.原料費（1本）
			ret.append("  ,T141.genryohi1 AS genka1 "); // 47
			
			ret.append("  ,ISNULL(T131.flg_hiju_sui,0) AS flg_hiju_sui "); // 48
			ret.append("  ,CASE WHEN T131.flg_hiju_sui=1 ");
			ret.append("     THEN ISNULL(T131.hiju_sui,'') ");
			ret.append("     ELSE '' END AS 水相比重 "); // 49
	//2011/05/11 QP@10181_No.21 TT T.Nishigawa Add End --------------------------

			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD Start
			ret.append(",    ISNULL(T133.chuijiko, '') AS 注意事項 "); // 50
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD End
//ADD start 20121019 QP@20505 No.24
			ret.append("  ,CASE WHEN T131.flg_freeSuibunKasei=1 THEN ISNULL(T131.freetitle_suibun_kasei,'') ELSE '' END AS フリー水分活性タイトル "); // 51
			ret.append("  ,CASE WHEN T131.flg_freeSuibunKasei=1 THEN ISNULL(T131.free_suibun_kasei,'') ELSE '' END AS フリー水分活性 "); // 52
			ret.append("  ,CASE WHEN T131.flg_freeAlchol=1 THEN ISNULL(T131.freetitle_alcohol,'') ELSE '' END AS フリーアルコールタイトル "); // 53
			ret.append("  ,CASE WHEN T131.flg_freeAlchol=1 THEN ISNULL(T131.free_alcohol,'') ELSE '' END AS フリーアルコール "); // 54
			// DEL start 20130226 QP@20505 検収後の修正
//			ret.append("  ,ISNULL(T131.flg_suibun_kasei,0) AS flg_freeSuibunKasei ");
//			ret.append("  ,ISNULL(T131.flg_alcohol,0) AS flg_freeAlchol ");
			// DEL end 20130226 QP@20505 検収後の修正
			// ADD start 20130226 QP@20505 検収後の修正
			ret.append("  ,ISNULL(T131.flg_freeSuibunKasei,0) AS flg_freeSuibunKasei "); // 55
			ret.append("  ,ISNULL(T131.flg_freeAlchol,0) AS flg_freeAlchol "); // 56
			// ADD end 20130226 QP@20505 検収後の修正
			ret.append("   ,CASE WHEN T131.flg_jikkoSakusanNodo=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.jikkoSakusanNodo),'') ");
			ret.append("     ELSE '' END AS 実効酢酸濃度 "); // 57
			ret.append("   ,CASE WHEN T131.flg_msg_suiso=1 ");
			ret.append("     THEN ISNULL(CONVERT(VARCHAR,T131.msg_suiso),'') ");
			ret.append("     ELSE '' END AS 水相中ＭＳＧ "); // 58			
			ret.append("  ,CASE WHEN T131.flg_freeNendo=1 THEN ISNULL(T131.freetitle_nendo,'') ELSE '' END AS フリー粘度タイトル "); // 59
			ret.append("  ,CASE WHEN T131.flg_freeNendo=1 THEN ISNULL(T131.free_nendo,'') ELSE '' END AS フリー粘度 "); // 60
			ret.append("  ,CASE WHEN T131.flg_freeNendo=1 THEN ISNULL(T131.freetitle_ondo,'') ELSE '' END AS フリー温度タイトル "); // 61 ※フラグは粘度と共通
			ret.append("  ,CASE WHEN T131.flg_freeNendo=1 THEN ISNULL(T131.free_ondo,'') ELSE '' END AS フリー温度 "); // 62
			ret.append("  ,CASE WHEN T131.flg_free4=1 THEN ISNULL(T131.free_title4,'') ELSE '' END AS フリータイトル4 "); // 63
			ret.append("  ,CASE WHEN T131.flg_free4=1 THEN ISNULL(T131.free_value4,'') ELSE '' END AS フリー内容4 "); // 64
			ret.append("  ,CASE WHEN T131.flg_free5=1 THEN ISNULL(T131.free_title5,'') ELSE '' END AS フリータイトル5 "); // 65
			ret.append("  ,CASE WHEN T131.flg_free5=1 THEN ISNULL(T131.free_value5,'') ELSE '' END AS フリー内容5 "); // 66
			ret.append("  ,CASE WHEN T131.flg_free6=1 THEN ISNULL(T131.free_title6,'') ELSE '' END AS フリータイトル6 "); // 67
			ret.append("  ,CASE WHEN T131.flg_free6=1 THEN ISNULL(T131.free_value6,'') ELSE '' END AS フリー内容6 "); // 68
			
			ret.append("  ,ISNULL(T131.flg_jikkoSakusanNodo,0) AS flg_jikkoSakusanNodo "); // 69
			ret.append("  ,ISNULL(T131.flg_msg_suiso,0) AS flg_msg_suiso "); // 70
			ret.append("  ,ISNULL(T131.flg_freeNendo,0) AS flg_freeNendo "); // 71
			ret.append("  ,ISNULL(T131.flg_freeOndo,0) AS flg_freeOndo "); // 72
			ret.append("  ,ISNULL(T131.flg_free4,0) AS flg_free4 "); // 73
			ret.append("  ,ISNULL(T131.flg_free5,0) AS flg_free5 "); // 74
			ret.append("  ,ISNULL(T131.flg_free6,0) AS flg_free6 "); // 75
//ADD end 20121019 QP@20505 No.24
			ret.append(" FROM tr_shisakuhin T110 ");
			ret.append("  LEFT JOIN tr_shisaku T131 ");
			ret.append("   ON  T131.cd_shain = T110.cd_shain ");
			ret.append("   AND T131.nen = T110.nen ");
			ret.append("   AND T131.no_oi = T110.no_oi ");			
			
			//原価原料テーブル
			ret.append(" LEFT JOIN tr_genryo T141 ");
			ret.append("  ON  T141.cd_shain = T131.cd_shain ");
			ret.append("  AND T141.nen = T131.nen ");
			ret.append("  AND T141.no_oi = T131.no_oi ");
			ret.append("  AND T141.seq_shisaku = T131.seq_shisaku ");
			
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD Start
			ret.append(" LEFT JOIN tr_cyuui T133 ");
			ret.append("  ON T133.cd_shain = T131.cd_shain ");
			ret.append("  AND T133.nen = T131.nen");
			ret.append("  AND T133.no_oi = T131.no_oi");
			ret.append("  AND T133.no_chui = T131.no_chui");
			//【QP@20505】No3 2012/10/09 TT H.SHIMA ADD End
			
			ret.append(" WHERE  ");

			//試作コードを検索条件に設定
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);

			//試作SEQ
			ret.append("  AND T131.seq_shisaku IN( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//リクエストパラメータより、試作SEQを取得する
				strSeq_shisaku = reqData.getFieldVale(0, i, "seq_shisaku");
				
				//SQL文に追加
				if ( i != 0 ) {
					ret.append(",");
					
				}
				ret.append(strSeq_shisaku);
				
			}
			ret.append("  ) ");
			
			//取得順
			ret.append(" ORDER BY T131.sort_shisaku ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "試作表、試作データ検索SQLの生成に失敗しました。");
			
		} finally {
			//変数の削除
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strSeq_shisaku = null;
	
		}
		return ret;
		
	}

	/**
	 * リクエストデータより、製造工程データ検索SQLを生成する
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 製造工程データ検索SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSeizokoteiSQLBuf(RequestResponsKindBean reqData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {

			//リクエストパラメータより、試作コードを取得する
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL文の作成
			ret.append(" SELECT ");
			ret.append("   ISNULL(CONVERT(VARCHAR,T133.no_chui),'') AS 注意NO ");
			ret.append("  ,ISNULL(T133.chuijiko,'') AS 注意事項 ");
			ret.append(" FROM tr_shisaku T131 ");
			ret.append(" LEFT JOIN tr_cyuui T133 ");
			ret.append("   ON  T133.cd_shain = T131.cd_shain ");
			ret.append("   AND T133.nen = T131.nen ");
			ret.append("   AND T133.no_oi = T131.no_oi ");
			ret.append("   AND T133.no_chui = T131.no_chui ");
			ret.append(" WHERE  ");

			//試作コードを検索条件に設定
			ret.append("  T131.cd_shain=" + strCd_shain);
			ret.append("  AND T131.nen=" + strNen);
			ret.append("  AND T131.no_oi=" + strNo_oi);

			//試作SEQ
			ret.append("  AND T131.seq_shisaku IN( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//リクエストパラメータより、試作SEQを取得する
				strSeq_shisaku = reqData.getFieldVale(0, i, "seq_shisaku");
				
				//SQL文に追加
				if ( i != 0 ) {
					ret.append(",");
					
				}
				ret.append(strSeq_shisaku);
				
			}
			ret.append("  ) ");
			
			//取得順
			ret.append(" ORDER BY T131.sort_shisaku ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "製造工程データ検索QLの生成に失敗しました。");
			
		} finally {
			//変数の削除
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strSeq_shisaku = null;
	
		}
		return ret;

	}
		
	
	/**
	 * 配合・試作リストデータの作成
	 * @param lstHaigoData : 配合データ
	 * @param lstShisakuListData : 試作リストデータ
	 * @return 出力データ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> CreateHaigoShisakuListData(List<?> lstHaigoData, List<?> lstShisakuListData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		List<Object[]> ret = new ArrayList<Object[]>();
		
		//設定用オブジェクト
		Object[] add_items = null;
		Object[] haigo_items = null;
		Object[] shisakuList_items = null;
		//工程CD・工程SEQ
		String strCd_kotei = null;
		String strSeq_kotei = null;
		
		//退避用工程CD
		String strCd_kotei_taihi = "";
		//工程CD設定用カウント
		int intKoteiCount = 0;		
		//試作リスト設定用カウント
		int intShisakuListCount = 0;
		
		//要素数
// 2010.11.04 Mod Arai Start  QP@00412_シサクイック改良 №.1------------------
		int intRecCount = 35;
//		int intRecCount = 25;
// 2010.11.04 Mod Arai End ---------------------------------------------------
		//試作列数
		int intShisakuRetuCount = 0;
		
		try {
			
			//配合テーブルデータと試作リストテーブルデータを合わせる
			
			//配合データリストの設定
			for (int i = 0; i < lstHaigoData.size(); i++ ) {
				
				//配合データ 検索結果取得
				haigo_items = (Object[]) lstHaigoData.get(i);
				
				//追加用オブジェクト生成
				add_items = new Object[intRecCount];
				
				//基本情報の設定
				add_items[0] = haigo_items[0].toString();			//試作コード
				add_items[1] = haigo_items[1].toString();			//依頼番号
				add_items[2] = haigo_items[2].toString();			//品名
				add_items[3] = haigo_items[4].toString();			//会社名
				add_items[4] = haigo_items[5].toString();			//工場名
				add_items[5] = haigo_items[6].toString();			//試作者
				add_items[6] = haigo_items[7].toString();			//発行日
				add_items[7] = haigo_items[3].toString();			//総合メモ
				add_items[8] = haigo_items[21].toString();		//試作メモ
				
				//配合データ検索結果の設定
				
				// 工程CD・工程SEQの取得
				strCd_kotei = haigo_items[8].toString();			//工程CD
				strSeq_kotei = haigo_items[9].toString();			//工程SEQ
				
				// 工程行の設定
				if ( !strCd_kotei.equals(strCd_kotei_taihi) ) {
					//工程CDを退避
					strCd_kotei_taihi = strCd_kotei; 
										
					//工程CD設定用カウントを進める
					intKoteiCount++;
					
					//工程CDと退避用工程CDが異なる場合、「工程属性 工程名」を設定
					add_items[10] = "---";
					add_items[11] = intKoteiCount + "工程◆" + haigo_items[10].toString() + "◆";
					for ( int j = 12; j < intRecCount; j++ ) {
						//配合データ
						add_items[j] = "";
						
					}

					//リスト一行追加
					ret.add(add_items);	
					
				}
				
				//追加用オブジェクト再生成
				add_items = new Object[intRecCount];
				
				//基本情報の設定
				add_items[0] = haigo_items[0].toString();			//試作コード
				add_items[1] = haigo_items[1].toString();			//依頼番号
				add_items[2] = haigo_items[2].toString();			//品名
				add_items[3] = haigo_items[3].toString();			//会社名
				add_items[4] = haigo_items[4].toString();			//工場名
				add_items[5] = haigo_items[5].toString();			//試作者
				add_items[6] = haigo_items[6].toString();			//発行日
				add_items[7] = haigo_items[7].toString();			//総合メモ
				add_items[8] = haigo_items[21].toString();		//試作メモ
				add_items[9] = haigo_items[20].toString();		//小数桁数
				
				//配合データの設定
				add_items[10] = haigo_items[12].toString();		//原料コード 
				add_items[11] = haigo_items[13].toString();		//原料名
				add_items[12] = haigo_items[14].toString();		//単価
				add_items[13] = haigo_items[15].toString();		//歩留
				
				//試作リストより量を取得(10項目のみ)
				intShisakuListCount = 0;
// 2010.11.04 Mod Arai Start  QP@00412_シサクイック改良 №.1------------------
				for (int j = 0; intShisakuListCount < 20; j++ ) {
//				for (int j = 0; intShisakuListCount < 10; j++ ) {
// 2010.11.04 Mod Arai End ---------------------------------------------------
					
					//試作リスト.量を設定
					if ( j < lstShisakuListData.size() ) {
						
						//試作リストデータ　検索結果取得
						shisakuList_items = (Object[]) lstShisakuListData.get(j);
						
						//配合データと試作リストデータの工程CD・工程SEQが等しい
						if ( strCd_kotei.equals(shisakuList_items[1].toString()) && strSeq_kotei.equals(shisakuList_items[2].toString()) ) {
						
							//量を設定
							add_items[intShisakuListCount + 14] = shisakuList_items[3].toString();

							//カウントを進める
							intShisakuListCount++;
							
							//試作列数をカウント(原料１行目は、カウントを行う)
							if ( ret.size() < 2 ) {
								intShisakuRetuCount++;
							
							}
							
						}
												
					} else {
						
						//量を設定
						add_items[intShisakuListCount + 14] = "";
						
						//カウントを進める
						intShisakuListCount++;
						
					}
															
				}
					
				//リスト一行追加
				ret.add(add_items);	
				
			}

			//全ての配合データに試作列を設定
			for ( int i=0; i<ret.size(); i++ ) {
				
				//配合データ取得
				Object[] items = (Object[])ret.get(i);
				
				//試作列を設定
				items[intRecCount-1] = intShisakuRetuCount;
				ret.set(i, items);
				
			}			
			
		} catch(Exception e) {
			this.em.ThrowException(e, "試作表、配合・試作リストデータの作成処理に失敗しました。");
			
		} finally {
			//変数の削除
			add_items = null;
			haigo_items = null;
			shisakuList_items = null;
			strCd_kotei = null;
			strSeq_kotei = null;
			strCd_kotei_taihi = null;
			intKoteiCount = 0;
			intShisakuListCount = 0;
			intRecCount = 0;
			
		}
		return ret;
		
	}
	
	/**
	 * 工程合計量算出処理
	 * @param lstKoteiGoukei : 工程合計量リスト
	 * @param dblRyo : 配合量
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void SetKoteiGoukeiRyo(List<Double> lstKoteiGoukei, int intIndex, Double dblRyo)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			//工程合計量算出
			lstKoteiGoukei.set(intIndex-1, lstKoteiGoukei.get(intIndex-1) + dblRyo);
			
		} catch(Exception e) {
			this.em.ThrowException(e, "工程合計量算出処理に失敗しました。");
			
		} finally {
			
		}
		
	}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
	/**
	 * Double値変換処理
	 * @param chkVal : 対象の値
	 * @return 0とNULL = 空白
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String ChkDblVal(String chkVal)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRet = "";
		
		try {
			//Doubleに変換して0の場合、空白を返す
			if (toDouble(chkVal) == 0) {
				strRet = "";				
			} else {
				strRet = toString(chkVal);				
			}

		} catch(Exception e) {
			this.em.ThrowException(e, "Double値変換処理に失敗しました。");
		} finally {			
		}
		
		return strRet;
	}
//add end --------------------------------------------------------------------------------------
	
}
