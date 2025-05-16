package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 原価試算表を生成する
 * @author jinbo
 * @since  2009/05/21
 */
public class GenkaShisanHyoLogic extends LogicBaseJExcel {
	
	/**
	 * コンストラクタ
	 */
	public GenkaShisanHyoLogic() {
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
			//DB検索
			super.createSearchDB();
			lstRecset = getData(reqData);

			//工程値
			String strKoteiValue = toString( reqData.getFieldVale(0, 0, "kotei_value"));
			
			//工程値により、「調味料パターン」か「その他調味料以外パターン」かを判定する
			if ( strKoteiValue.equals("1") ) {
				//調味料Excelファイル生成
				DownLoadPath = makeExcelFile1(lstRecset, reqData);
				
			} else if ( strKoteiValue.equals("2") ) {
				//その他Excelファイル生成
				DownLoadPath = makeExcelFile2(lstRecset, reqData);
				
			}

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
			ret.setID("SA800");
			
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
	 * 調味料_原価試算表(EXCEL)を生成する
	 * @param lstRecset : 検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(List<?> lstRecset, RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		String strShisakuSeq1 = "";
		String strShisakuSeq2 = "";
		String strShisakuSeq3 = "";
		int intChuijiko = 0;
						
		try {
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("調味料原価試算表");
			
			//試作SEQを取得
			strShisakuSeq1 = toString( reqData.getFieldVale(0, 0, "seq_shisaku1"));
			strShisakuSeq2 = toString( reqData.getFieldVale(0, 0, "seq_shisaku2"));
			strShisakuSeq3 = toString( reqData.getFieldVale(0, 0, "seq_shisaku3"));
			
			//ダウンロード用のEXCELを生成する
			for (int i = 0; i < lstRecset.size(); i++) {
				
				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset.get(i);

				//ADD 2012.4.30　【H24年度対応】JEXCEL対応の為追加
				if(i==0){
					//EXCELテンプレートを読み込む
					ret = super.ExcelOutput(
							makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							));
				}

				try{
					//Excelに値をセットする
					
					//品名
					super.ExcelSetValue("品名", toString(items[2]));
					//依頼番号
					super.ExcelSetValue("依頼番号", toString(items[3]));
					//日付
					super.ExcelSetValue("日付", toString(items[4]));
					//工場コード
//					super.ExcelSetValue("工場コード", toDouble(items[5]));				//DEL 2012.4.30　【H24年度対応】コード出力から名称出力に変更
					//研究所
					super.ExcelSetValue("研究所", toString(items[6]));
					//営業
					super.ExcelSetValue("営業", toString(items[7]));					

					//試作SEQごとに分岐
					if ( toString(items[36]).equals(strShisakuSeq1) ){
						//サンプルNo1
						super.ExcelSetValue("サンプルNo1", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版1編集
						super.ExcelSetValue("製品_サンプルNo1","サンプル:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No1","工程版:" + toString(items[37]));
							super.ExcelSetValue("製品_注意事項1",toString(items[31]));
						}
						
					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
						//サンプルNo2
						super.ExcelSetValue("サンプルNo2", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版2編集
						super.ExcelSetValue("製品_サンプルNo2","サンプル:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No2","工程版:" + toString(items[37]));
							super.ExcelSetValue("製品_注意事項2",toString(items[31]));
						}

					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
						//サンプルNo3
						super.ExcelSetValue("サンプルNo3", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版3編集
						super.ExcelSetValue("製品_サンプルNo3","サンプル:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No3","工程版:" + toString(items[37]));
							super.ExcelSetValue("製品_注意事項3",toString(items[31]));
						}

					}

					//工程が「殺菌調味液」の場合
					if (toString(items[1]).equals("1")) {
						//***** [殺菌調味液] *****

						if ( toString(items[36]).equals(strShisakuSeq1) ) {
							//新規原料
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
						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//配合1
							super.ExcelSetValue("工程1_配合1", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq2) ){
							//配合2
							super.ExcelSetValue("工程1_配合2", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq3) ){
							//配合3
							super.ExcelSetValue("工程1_配合3", toDouble(items[14]));
							
						}

					//工程が「水相」の場合
					} else if (toString(items[1]).equals("2")) {
						//***** [水相] *****

						if ( toString(items[36]).equals(strShisakuSeq1) ){
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
						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//配合1
							super.ExcelSetValue("工程2_配合1", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq2) ){
							//配合2
							super.ExcelSetValue("工程2_配合2", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq3) ){
							//配合3
							super.ExcelSetValue("工程2_配合3", toDouble(items[14]));
							
						}

					//工程が「油相」の場合
					} else if (toString(items[1]).equals("3")) {
						//***** [油相] *****

						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//新規原料
							super.ExcelSetValue("工程3_新規原料", toString(items[9]));
							//品コード
							super.ExcelSetValue("工程3_品コード", toString(items[10]));
							//品名
							super.ExcelSetValue("工程3_品名", toString(items[11]));
							//単価
							super.ExcelSetValue("工程3_単価", toDouble(items[12]));
							//歩留
							super.ExcelSetValue("工程3_歩留", (toDouble(items[13]) / 100));
							
						}

						//試作SEQごとに分岐
						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//配合1
							super.ExcelSetValue("工程3_配合1", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq2) ){
							//配合2
							super.ExcelSetValue("工程3_配合2", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq3) ){
							//配合3
							super.ExcelSetValue("工程3_配合3", toDouble(items[14]));
							
						}
						
					} 
					
					//原料情報
					if ( toString(items[36]).equals(strShisakuSeq1) ){
						//充填量水相1
						super.ExcelSetValue("水相1", toDouble(items[15]));
						//充填量油相1
						super.ExcelSetValue("油相1", toDouble(items[16]));
						//比重1
						super.ExcelSetValue("比重1", toDouble(items[17]));
						
					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
						//充填量水相2
						super.ExcelSetValue("水相2", toDouble(items[15]));
						//充填量油相2
						super.ExcelSetValue("油相2", toDouble(items[16]));
						//比重2
						super.ExcelSetValue("比重2", toDouble(items[17]));
						
					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
						//充填量水相3
						super.ExcelSetValue("水相3", toDouble(items[15]));
						//充填量油相3
						super.ExcelSetValue("油相3", toDouble(items[16]));
						//比重3
						super.ExcelSetValue("比重3", toDouble(items[17]));
						
					}

					//ADD 2012.4.30　【H24年度対応】工場名(部署名)追加
					super.ExcelSetValue("工場", toString(items[41]));
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

					//発売時期
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
					//super.ExcelSetValue("発売時期", toString(items[27]));
					super.ExcelSetValue("発売時期1", toString(items[27]));
					super.ExcelSetValue("発売時期2", toString(items[27]));
					super.ExcelSetValue("発売時期3", toString(items[27]));
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
					
					//想定物量
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
					//super.ExcelSetValue("想定物量", toString(items[28]));
					super.ExcelSetValue("想定物量1", toString(items[28]));
					super.ExcelSetValue("想定物量2", toString(items[28]));
					super.ExcelSetValue("想定物量3", toString(items[28]));
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
					
					//売価
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
					//super.ExcelSetValue("売価", toString(items[29]));
					super.ExcelSetValue("売価1", toString(items[29]));
					super.ExcelSetValue("売価2", toString(items[29]));
					super.ExcelSetValue("売価3", toString(items[29]));
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
					
					//希望原価
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
					//super.ExcelSetValue("希望原価", toString(items[30]));
					super.ExcelSetValue("希望原価1", toString(items[30]));
					super.ExcelSetValue("希望原価2", toString(items[30]));
					super.ExcelSetValue("希望原価3", toString(items[30]));
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

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

//					//最終試算用
//					if ( toString(items[36]).equals(strShisakuSeq1) ){
//						//平均充填量1
//						super.ExcelSetValue("平均充填量1", toDouble(items[32]));
//						//有効歩留1
//						super.ExcelSetValue("有効歩留1", (toDouble(items[33]) / 100));
//						//固定費1
//						super.ExcelSetValue("固定費1", toDouble(items[34]));
//						
//					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
//						//平均充填量2
//						super.ExcelSetValue("平均充填量2", toDouble(items[32]));
//						//有効歩留2
//						super.ExcelSetValue("有効歩留2", (toDouble(items[33]) / 100));
//						//固定費2
//						super.ExcelSetValue("固定費2", toDouble(items[34]));
//						
//					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
//						//平均充填量3
//						super.ExcelSetValue("平均充填量3", toDouble(items[32]));
//						//有効歩留3
//						super.ExcelSetValue("有効歩留3", (toDouble(items[33]) / 100));
//						//固定費3
//						super.ExcelSetValue("固定費3", toDouble(items[34]));
//						
//					}
					
				}catch(ExceptionWaning e){
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;
					
				}finally{
					
				}
				
			}
			
			//DEL 2012.4.30　【H24年度対応】JEXCEL対応の為移動
//			//エクセルファイルをダウンロードフォルダに生成する
////			ret = super.ExcelOutput();
//			ret = super.ExcelOutput(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
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
	/**
	 * その他_原価試算表(EXCEL)を生成する
	 * @param lstRecset : 検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile2(List<?> lstRecset, RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		String strShisakuSeq1 = "";
		String strShisakuSeq2 = "";
		String strShisakuSeq3 = "";
		int intChuijiko = 0;
		
		try {
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("その他原価試算表");

			//試作SEQを取得
			strShisakuSeq1 = toString( reqData.getFieldVale(0, 0, "seq_shisaku1"));
			strShisakuSeq2 = toString( reqData.getFieldVale(0, 0, "seq_shisaku2"));
			strShisakuSeq3 = toString( reqData.getFieldVale(0, 0, "seq_shisaku3"));

			//ダウンロード用のEXCELを生成する
			for (int i = 0; i < lstRecset.size(); i++) {
				
				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset.get(i);

				//ADD 2012.4.30　【H24年度対応】JEXCEL対応の為追加
				if(i==0){
					//EXCELテンプレートを読み込む
					ret = super.ExcelOutput(
							makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							));
				}

				try{
					//Excelに値をセットする
					
					//品名
					super.ExcelSetValue("品名", toString(items[2]));
					//依頼番号
					super.ExcelSetValue("依頼番号", toString(items[3]));
					//日付
					super.ExcelSetValue("日付", toString(items[4]));
					//工場コード
//					super.ExcelSetValue("工場コード", toDouble(items[5]));					//DEL 2012.4.30　【H24年度対応】コード出力から名称出力に変更
					//研究所
					super.ExcelSetValue("研究所", toString(items[6]));
					//営業
					super.ExcelSetValue("営業", toString(items[7]));
					
					//試作SEQごとに分岐
					if ( toString(items[36]).equals(strShisakuSeq1) ){
						//サンプルNo1
						super.ExcelSetValue("サンプルNo1", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版1編集
						super.ExcelSetValue("製品_サンプルNo1","サンプル:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No1","工程版:" + toString(items[37]));
							super.ExcelSetValue("製品_注意事項1",toString(items[31]));
						}
						
					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
						//サンプルNo2
						super.ExcelSetValue("サンプルNo2", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版2編集
						super.ExcelSetValue("製品_サンプルNo2","サンプル:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No2","工程版:" + toString(items[37]));
							super.ExcelSetValue("製品_注意事項2",toString(items[31]));
						}

					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
						//サンプルNo3
						super.ExcelSetValue("サンプルNo3", toString(items[8]));

						//ADD 2012.4.30　【H24年度対応】製品情報欄に工程版3編集
						super.ExcelSetValue("製品_サンプルNo3","サンプル:" + toString(items[8]));
						if ( !toString(items[37]).isEmpty() ) {
							super.ExcelSetValue("製品_工程版No3","工程版:" + toString(items[37]));
							super.ExcelSetValue("製品_注意事項3",toString(items[31]));
						}

					}

					//工程が「殺菌調味液」の場合
					if (toString(items[1]).equals("1")) {
						//***** [殺菌調味液] *****

						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//新規原料
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
						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//配合1
							super.ExcelSetValue("工程1_配合1", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq2) ){
							//配合2
							super.ExcelSetValue("工程1_配合2", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq3) ){
							//配合3
							super.ExcelSetValue("工程1_配合3", toDouble(items[14]));
							
						}

					//工程が「水相」の場合
					} else if (toString(items[1]).equals("2")) {
						//***** [水相] *****

						if ( toString(items[36]).equals(strShisakuSeq1) ){
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
						if ( toString(items[36]).equals(strShisakuSeq1) ){
							//配合1
							super.ExcelSetValue("工程2_配合1", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq2) ){
							//配合2
							super.ExcelSetValue("工程2_配合2", toDouble(items[14]));
							
						} else if ( toString(items[36]).equals(strShisakuSeq3) ){
							//配合3
							super.ExcelSetValue("工程2_配合3", toDouble(items[14]));
							
						}

					}
					
					//原料情報
					if ( toString(items[36]).equals(strShisakuSeq1) ){
						//仕上がり合計重量1
						if ( !toString(items[35]).isEmpty() ) {
							super.ExcelSetValue("仕上がり合計重量1", toDouble(items[35]));
							
						}
						
					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
						//仕上がり合計重量2
						if ( !toString(items[35]).isEmpty() ) {
							super.ExcelSetValue("仕上がり合計重量2", toDouble(items[35]));
							
						}
						
					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
						//仕上がり合計重量3
						if ( !toString(items[35]).isEmpty() ) {
							super.ExcelSetValue("仕上がり合計重量3", toDouble(items[35]));
						
						}
						
					}

					//ADD 2012.4.30　【H24年度対応】工場名(部署名)追加
					super.ExcelSetValue("工場", toString(items[41]));
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
					
					//発売時期
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
					//super.ExcelSetValue("発売時期", toString(items[27]));
					super.ExcelSetValue("発売時期1", toString(items[27]));
					super.ExcelSetValue("発売時期2", toString(items[27]));
					super.ExcelSetValue("発売時期3", toString(items[27]));
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
					
					//想定物量
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
					//super.ExcelSetValue("想定物量", toString(items[28]));
					super.ExcelSetValue("想定物量1", toString(items[28]));
					super.ExcelSetValue("想定物量2", toString(items[28]));
					super.ExcelSetValue("想定物量3", toString(items[28]));
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
					
					//売価
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
					//super.ExcelSetValue("売価", toString(items[29]));
					super.ExcelSetValue("売価1", toString(items[29]));
					super.ExcelSetValue("売価2", toString(items[29]));
					super.ExcelSetValue("売価3", toString(items[29]));
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
					
					//希望原価
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
					//super.ExcelSetValue("希望原価", toString(items[30]));
					super.ExcelSetValue("希望原価1", toString(items[30]));
					super.ExcelSetValue("希望原価2", toString(items[30]));
					super.ExcelSetValue("希望原価3", toString(items[30]));
					//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

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
					
//					//最終試算用
//					if ( toString(items[36]).equals(strShisakuSeq1) ){
//						//平均充填量1
//						super.ExcelSetValue("平均充填量1", toDouble(items[32]));
//						//有効歩留1
//						super.ExcelSetValue("有効歩留1", (toDouble(items[33]) / 100));
//						//固定費1
//						super.ExcelSetValue("固定費1", toDouble(items[34]));
//						
//					} else if ( toString(items[36]).equals(strShisakuSeq2) ){
//						//平均充填量2
//						super.ExcelSetValue("平均充填量2", toDouble(items[32]));
//						//有効歩留2
//						super.ExcelSetValue("有効歩留2", (toDouble(items[33]) / 100));
//						//固定費2
//						super.ExcelSetValue("固定費2", toDouble(items[34]));
//						
//					} else if ( toString(items[36]).equals(strShisakuSeq3) ){
//						//平均充填量3
//						super.ExcelSetValue("平均充填量3", toDouble(items[32]));
//						//有効歩留3
//						super.ExcelSetValue("有効歩留3", (toDouble(items[33]) / 100));
//						//固定費3
//						super.ExcelSetValue("固定費3", toDouble(items[34]));
//						
//					}
					
				}catch(ExceptionWaning e){
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;
					
				}finally{
					
				}
				
			}
			
			//DEL 2012.4.30　【H24年度対応】JEXCEL対応の為移動
//			//エクセルファイルをダウンロードフォルダに生成する
////			ret = super.ExcelOutput();
//			ret = super.ExcelOutput(
//					makeShisakuNo(
//							reqData.getFieldVale(0, 0, "cd_shain")
//							,reqData.getFieldVale(0, 0, "nen")
//							,reqData.getFieldVale(0, 0, "no_oi")
//							)
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
			String strShisakuSeq = "";
			
			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL文の作成	
			ret.append(" SELECT ");
			ret.append("   ISNULL(CONVERT(VARCHAR,M302K.value1),'') AS kotei1 ");
			ret.append("  ,M302K.value2 AS kotei2 ");
			ret.append("  ,T110.nm_hin AS hinmei ");
			ret.append("  ,CONVERT(VARCHAR,T110.no_irai) AS no_irai ");
			ret.append(" ,CONVERT(VARCHAR,YEAR(GETDATE())) + '年' ");
			ret.append("   + CONVERT(VARCHAR,MONTH(GETDATE())) + '月' ");
			ret.append("   + CONVERT(VARCHAR,DAY(GETDATE())) + '日' AS dt_hizuke ");
			ret.append("  ,T110.cd_kojo AS cd_kojo ");
			ret.append("  ,M101.nm_user AS kenkyusho ");	//6.研究所名
			ret.append("  ,T110.cd_eigyo AS eigyo ");		//7.営業コード
			ret.append("  ,T131.nm_sample AS nm_sample ");
			ret.append("  ,CASE ISNULL(LEFT(T120.cd_genryo,1),'') WHEN '' THEN ''  ");
			ret.append("    WHEN 'N' THEN '☆' ELSE '' END AS new_genryo ");
			ret.append("  ,T120.cd_genryo AS cd_genryo ");
			ret.append("  ,T120.nm_genryo AS nm_genryo ");
			ret.append("  ,T120.tanka AS tanka ");
			ret.append("  ,T120.budomari AS budomari ");
			ret.append("  ,T132.quantity AS haigoryo ");
			ret.append("  ,T141.zyusui AS zyuten_suiso ");
			ret.append("  ,T141.zyuabura AS zyuten_yuso ");
			ret.append("  ,T131.hiju AS hiju ");
			ret.append("  ,M3021.nm_literal AS nm_seizohoho ");
			ret.append("  ,M3022.nm_literal AS nm_juten ");
			ret.append("  ,T110.hoho_sakin AS hoho_sakin ");
			ret.append("  ,M3023.nm_literal AS nm_ondo ");
			ret.append("  ,T110.youki AS youki ");
			ret.append("  ,T110.cd_nisugata AS nm_nisugata ");
			ret.append("  ,T110.yoryo AS yoryo ");
			ret.append("  ,T110.su_iri AS su_iri ");
			ret.append("  ,T110.shomikikan AS shomikikan ");
			ret.append("  ,T110.dt_hatubai AS dt_hatubai ");
			ret.append("  ,T110.buturyo AS buturyo ");
			ret.append("  ,T110.baika AS baika ");
			ret.append("  ,T110.genka AS kibogenka ");
			ret.append("  ,T133.chuijiko AS chuijiko ");				//31注意事項
			ret.append("  ,T141.heikinzyu AS heikinzyu ");
			ret.append("  ,T141.yukobudomari AS yukobudomari ");
			ret.append("  ,T141.cs_keihi AS koteihi ");
			ret.append("  ,T131.juryo_shiagari_g AS shiagarijuryo ");
			ret.append("  ,T131.seq_shisaku AS seq_shisaku ");
			ret.append("  ,T131.no_chui AS no_chui ");
			ret.append("  ,T120.sort_kotei AS sort_kotei ");
			ret.append("  ,T120.sort_genryo AS sort_genryo ");
			ret.append("  ,T131.no_shisan AS no_shisan ");

			//ADD 2012.4.30　【H24年度対応】工場名
			ret.append("  ,M104.nm_busho  AS nm_busho ");			//41工場名(部署名)
			
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

			//ADD 2012.4.30　【H24年度対応】工場名
			ret.append(" LEFT JOIN ma_busho AS M104 ");
			ret.append("  ON T110.cd_kaisha = M104.cd_kaisha ");
			ret.append("  AND T110.cd_kojo = M104.cd_busho ");

			//試作SEQ1～3の配合量の取得
			for ( int i=0; i<3; i++ ) {
				
				//試作SEQの取得
				strShisakuSeq = toString(reqData.getFieldVale(0, 0, "seq_shisaku" + (i+1)));

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
			ret.append(" WHERE T110.cd_shain = " + strCd_shain);
			ret.append("  AND T110.nen = " + strNen);
			ret.append("  AND T110.no_oi = " + strNo_oi);
			
			//試作SEQ
			ret.append("  AND T131.seq_shisaku IN ( ");
			for ( int i=0; i<3; i++ ) {
				
				//試作SEQの取得
				strShisakuSeq = toString(reqData.getFieldVale(0, 0, "seq_shisaku" + (i+1)));

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
			for ( int i=0; i<3; i++ ) {
				
				//試作SEQの取得
				strShisakuSeq = toString(reqData.getFieldVale(0, 0, "seq_shisaku" + (i+1)));

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
	
	
}