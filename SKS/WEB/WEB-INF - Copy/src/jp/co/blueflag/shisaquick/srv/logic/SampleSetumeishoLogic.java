package jp.co.blueflag.shisaquick.srv.logic;

import java.math.BigDecimal;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * サンプル説明書を生成する
 * @author isono
 * @since  2009/04/09
 */
public class SampleSetumeishoLogic extends LogicBaseExcel {

	/**
	 * コンストラクタ
	 */
	public SampleSetumeishoLogic() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}
	/**
	 * サンプル説明書を生成する
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
		List<?> item = null;
		//検索データ
		List<?> lstRecset1 = null;
		List<?> lstRecset2 = null;
		List<?> lstRecset3 = null;
		List<?> lstRecset4 = null;
		//エクセルファイルパス
		String DownLoadPath = "";
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();

		try {

			super.createSearchDB();

			//試作順にて試作SEQを検索し、リクエスとデータに格納
			//SQL文の作成
			strSql = MakeSQLSeq(reqData,_userInfoData);
			//SQLを実行
			item = searchDB.dbSearch(strSql.toString());
			//検索結果がない場合
			if (item.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");

			}

			for( int i = 0; i < item.size(); i++ ){

				//処理結果の格納
				Object[] items = (Object[]) item.get(i);

				//試作SEQ取得
				String seq = toString(items[0]);
				//リクエストデータに試作SEQ格納
				reqData.setFieldVale(0, 0, "seq_shisaku" + (i+1), seq);

			}

			//DB検索(調味料版)
			lstRecset1 = getData1(reqData);
			lstRecset2 = getData2(reqData);
			lstRecset3 = getData3(reqData);
			lstRecset4 = getData4(reqData);

			//Excelファイル生成(加食版)
			DownLoadPath = makeExcelFile1(lstRecset1,lstRecset2,lstRecset3,lstRecset4,reqData);

			//Excelファイル生成(調味料版)
			DownLoadPath = DownLoadPath + ":::" + makeExcelFile2(
					lstRecset1,
					lstRecset2,
					lstRecset3,
					lstRecset4,
					reqData
					);

			//レスポンスデータ生成
			ret = CreateRespons(DownLoadPath);

		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書の生成に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset1);
			removeList(lstRecset2);
			removeList(lstRecset3);
			removeList(lstRecset4);

			if (searchDB != null) {
				//DBセッションのクローズ
				searchDB.Close();
				searchDB = null;

			}

		}
		return ret;

	}

	/**
	 * リクエストデータより、試作SEQ検索SQLを生成する
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @return String : sql
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLSeq(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try {

			//SQL文生成
			ret.append(" select TOP 3 seq_shisaku,sort_shisaku  ");
			ret.append(" from tr_shisaku ");
			ret.append(" where  ");
			ret.append("   cd_shain = " + reqData.getFieldVale(0, 0, "cd_shain") );
			ret.append("   AND nen = " +  reqData.getFieldVale(0, 0, "nen") );
			ret.append("   AND no_oi = " + reqData.getFieldVale(0, 0, "no_oi") );
			ret.append("   AND flg_print = 1 ");
			ret.append(" order by sort_shisaku ");

		} catch (Exception e) {
			this.em.ThrowException(e, "サンプル説明書、検索SQLの生成に失敗しました。");

		} finally {

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
			ret.setID("SA450");

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
	 * サンプル説明書を生成する
	 * @param lstRecset1 : 検索データリスト(seq_shisaku1)
	 * @param lstRecset2 : 検索データリスト(seq_shisaku2)
	 * @param lstRecset3 : 検索データリスト(seq_shisaku3)
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(
			List<?> lstRecset1,
			List<?> lstRecset2,
			List<?> lstRecset3,
			List<?> lstRecset4,
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//EXCELテンプレートを読み込む
//			super.ExcelReadTemplate("サンプル説明書（調味料版）");
			//MOD 2013/06/18 ogawa start        Settingシートの削除を非表示に変更
			//修正前のソース
			//super.ExcelReadTemplate("サンプル説明書_合算");
			//修正後のソース
			super.ExcelReadTemplate("サンプル説明書_合算", false);
			//MOD 2013/06/18 ogawa  end

			//サンプル一つ目の出力
			if(lstRecset1 != null){

				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset1.size(); i++) {

					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset1.get(i);

					try{

						//Excelに値をセットする
						ExcelSetValueMain(1, items);

					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;

					}finally{

					}

				}

			}

			//サンプル２つ目の出力
			if(lstRecset2 != null){

				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset2.size(); i++) {

					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset2.get(i);

					try{

						//Excelに値をセットする
						ExcelSetValueMain(2, items);

					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;

					}finally{

					}

				}

			}

			//サンプル３つ目の出力
			if(lstRecset3 != null){

				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset3.size(); i++) {

					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset3.get(i);

					try{

						//Excelに値をセットする
						ExcelSetValueMain(3, items);

					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;

					}finally{

					}

				}

			}

			//サンプル４つ目の出力
			if(lstRecset4 != null){

				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset4.size(); i++) {

					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset4.get(i);

					try{

						//Excelに値をセットする
						ExcelSetValueMain(4, items);

					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;

					}finally{

					}

				}

			}

			//エクセルファイルをダウンロードフォルダに生成する
//			ret = super.ExcelOutput();
			ret = super.ExcelOutput(
					makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							)
					);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

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
	 * サンプル説明書(加食)を生成する
	 * @param lstRecset1 : 検索データリスト(seq_shisaku1)
	 * @param lstRecset2 : 検索データリスト(seq_shisaku2)
	 * @param lstRecset3 : 検索データリスト(seq_shisaku3)
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile2(
			List<?> lstRecset1,
			List<?> lstRecset2,
			List<?> lstRecset3,
			List<?> lstRecset4,
			RequestResponsKindBean reqData
			)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//EXCELテンプレートを読み込む
//			super.ExcelReadTemplate("サンプル説明書(加食版)");
			//MOD 2013/06/18 ogawa start        Settingシートの削除を非表示に変更
			//修正前のソース
			//super.ExcelReadTemplate("サンプル説明書");
			//修正後のソース
			super.ExcelReadTemplate("サンプル説明書", false);
			//MOD 2013/06/18 ogawa  end

			//サンプル一つ目の出力
			if(lstRecset1 != null){

				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset1.size(); i++) {

					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset1.get(i);

					try{

						//Excelに値をセットする
						ExcelSetValueMain(1, items);

					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;

					}finally{

					}

				}

			}

			//サンプル２つ目の出力
			if(lstRecset2 != null){

				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset2.size(); i++) {

					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset2.get(i);

					try{

						//Excelに値をセットする
						ExcelSetValueMain(2, items);

					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;

					}finally{

					}

				}

			}

			//サンプル３つ目の出力
			if(lstRecset3 != null){

				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset3.size(); i++) {

					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset3.get(i);

					try{

						//Excelに値をセットする
						ExcelSetValueMain(3, items);

					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;

					}finally{

					}

				}

			}

			//【QP@10181_No.21】 start
			//サンプル４つ目の出力
			if(lstRecset4 != null){

				//ダウンロード用のEXCELを生成する
				for (int i = 0; i < lstRecset4.size(); i++) {

					//検索結果の1行分を取り出す
					Object[] items = (Object[]) lstRecset4.get(i);

					try{

						//Excelに値をセットする
						ExcelSetValueMain(4, items);

					}catch(ExceptionWaning e){
						//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
						break;

					}finally{

					}

				}

			}
			//【QP@10181_No.21】 end

			//エクセルファイルをダウンロードフォルダに生成する
//			ret = super.ExcelOutput();
			ret = super.ExcelOutput(
					makeShisakuNo(
							reqData.getFieldVale(0, 0, "cd_shain")
							,reqData.getFieldVale(0, 0, "nen")
							,reqData.getFieldVale(0, 0, "no_oi")
							)
					);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}

	/**
	 * 共通Excel出力項目を設定する
	 * @param intGenryoIdx : 原料番号
	 * @param items : 検索結果
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void ExcelSetValueMain(int intGenryoIdx, Object[] items) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int intKetasu = 0;

		try {

			//小数桁数を取得する
			if ( !toString(items[17]).isEmpty() ) {
				intKetasu = Integer.parseInt(items[17].toString());

			}

			//Excelに値をセットする
			//品名
			super.ExcelSetValue("品名" + intGenryoIdx, toString(items[16]));
			//サンプル番号
			super.ExcelSetValue("ＳＥＱ" + intGenryoIdx, toString(items[20]));
			//品コード
			super.ExcelSetValue("品コード" + intGenryoIdx, toString(items[4]));
			//原料名
			super.ExcelSetValue("原料名" + intGenryoIdx, toString(items[5]));
			//配合
			super.ExcelSetValue("配合" + intGenryoIdx, toString(SetShosuKeta(items[6],intKetasu)));
			//歩留
			super.ExcelSetValue("歩留" + intGenryoIdx, toDouble(SetShosuKeta(items[7],2)));
			//表示案情報
			super.ExcelSetValue("表示案情報" + intGenryoIdx, toString(items[8]));
			//添加物情報
			super.ExcelSetValue("添加物情報" + intGenryoIdx, toString(items[9]));
			//栄養計算用　食品番号
			super.ExcelSetValue("栄養計算用　食品番号" + intGenryoIdx, SetEiyoNo(items));
			//メモ
			super.ExcelSetValue("メモ" + intGenryoIdx, toString(items[15]));
			//合計重量
			super.ExcelSetValue("合計重量タイトル" + intGenryoIdx, "合計重量(g)");
			super.ExcelSetValue("合計重量" + intGenryoIdx, toString(SetShosuKeta(items[19],intKetasu)));

			//【QP@10181_No.21】 start
			//保存方法
			super.ExcelSetValue("保存方法" + intGenryoIdx, toString(items[21]));
			//生産工場
			super.ExcelSetValue("生産工場" + intGenryoIdx, toString(items[22]));
			//賞味期間
			super.ExcelSetValue("賞味期間" + intGenryoIdx, toString(items[23]));
			//容器
			super.ExcelSetValue("容器" + intGenryoIdx, toString(items[24]));
			//容量
			super.ExcelSetValue("容量" + intGenryoIdx, toString(items[25]));
			//製法番号
			super.ExcelSetValue("製法番号" + intGenryoIdx, toString(items[26]));
			//一括表示の名称
			super.ExcelSetValue("一括表示の名称" + intGenryoIdx, toString(items[27]));
			//試作品コード
			super.ExcelSetValue("試作品コード" + intGenryoIdx, toString(items[28]));
			//作成日
			super.ExcelSetValue("作成日" + intGenryoIdx, toString(items[31]));
			//グループ
			super.ExcelSetValue("グループ" + intGenryoIdx, toString(items[29]));
			//名前
			super.ExcelSetValue("名前" + intGenryoIdx, toString(items[30]));
			//サンプル説明書 グループ
			super.ExcelSetValue("グループ", toString(items[29]));
			//サンプル説明書 グループ
			super.ExcelSetValue("名前", toString(items[30]));
			//サンプル説明書 作成日
			super.ExcelSetValue("作成日", toString(items[31]));
			//【QP@10181_No.21】 end

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			intKetasu = 0;

		}

	}

	/**
	 * 試作品データ(サンプル１つ目)を検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData1(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {

			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//試作No（サンプル番号）
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku1");

			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);

			}

		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書サンプル１、DB検索に失敗しました。");

		} finally {

		}
		return ret;

	}
	/**
	 * 試作品データ(サンプル２つ目)を検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData2(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {

			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//試作No（サンプル番号）
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku2");

			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);

			}

		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書サンプル２、DB検索に失敗しました。");

		} finally {

		}
		return ret;

	}
	/**
	 * 試作品データ(サンプル３つ目)を検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData3(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {

			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//試作No（サンプル番号）
			strSeq_shisaku = toString(reqData.getFieldVale(0, 0, "seq_shisaku3"));

			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);

			}

		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書サンプル３、DB検索に失敗しました。");

		} finally {

		}
		return ret;

	}
	/**
	 * 試作品データ(サンプル４つ目)を検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData4(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {

			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//試作No（サンプル番号）
			strSeq_shisaku = toString(reqData.getFieldVale(0, 0, "seq_shisaku4"));

			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);

			}

		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書サンプル４、DB検索に失敗しました。");

		} finally {

		}
		return ret;

	}
	/**
	 * 対象の試作品データを検索する
	 * @param strCd_shain : 試作社員cd
	 * @param strNen : 試作年
	 * @param strNo_oi : 追番
	 * @return list : 検索結果
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> getData(String strCd_shain, String strNen,String strNo_oi,String strSeq_shisaku)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> ret = null;
		//SQL　StringBuffer
		StringBuffer strSql = new StringBuffer();

		try {
			//SQL文の作成
			strSql = MakeSQLBuf(strCd_shain,strNen,strNo_oi,strSeq_shisaku);

			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());

			//検索結果がない場合
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e, "サンプル説明書、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;

		}
		return ret;

	}
	/**
	 * リクエストデータより、試作データ検索SQLを生成する
	 * @param strCd_shain : 試作社員cd
	 * @param strNen : 試作年
	 * @param strNo_oi : 追番
	 * @param strSeq_shisaku : 試作No（サンプル番号）
	 * @return String : sql
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLBuf(String strCd_shain,	String strNen,String strNo_oi, String strSeq_shisaku)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try {

			//SQL文の作成
			ret.append(" SELECT ");
			ret.append("  A.cd_shain AS 試作番号_社員CD ");
			ret.append(" , A.nen AS 試作番号_年 ");
			ret.append(" , A.no_oi AS 試作番号_追版 ");
			ret.append(" , A.seq_shisaku AS 試作SEQ ");
			ret.append(" , D.cd_genryo AS 品コード ");
			ret.append(" , D.nm_genryo AS 原料名 ");
			ret.append(" , CONVERT(VARCHAR,C.quantity) AS 配合 ");
			ret.append(" , CONVERT(VARCHAR,D.budomari) AS 歩留 ");
			ret.append(" , E.hyojian AS 表示案情報 ");
			ret.append(" , E.tenkabutu AS 添加物情報 ");
			ret.append(" , E.no_eiyo1 AS 栄養計算用食品番号1 ");
			ret.append(" , E.no_eiyo2 AS 栄養計算用食品番号2 ");
			ret.append(" , E.no_eiyo3 AS 栄養計算用食品番号3 ");
			ret.append(" , E.no_eiyo4 AS 栄養計算用食品番号4 ");
			ret.append(" , E.no_eiyo5 AS 栄養計算用食品番号5 ");
			ret.append(" , E.memo AS メモ ");
			ret.append(" , B.nm_hin AS 品名 ");
			ret.append(" , ISNULL(G.value1,0) AS 小数桁数 ");
			ret.append(" , A.juryo_shiagari_g AS 仕上がり量 ");
			ret.append(" , F.sum_quantity AS 合計重量 ");
			ret.append(" , RIGHT('0000000000'+CONVERT(varchar,A.cd_shain),10)  ");
			//ret.append(" , RIGHT('00000'+CONVERT(varchar,A.cd_shain),5)  ");
			ret.append("   +'-'+ RIGHT('00'+CONVERT(varchar,A.nen),2)  ");
			ret.append("   +'-'+ RIGHT('000'+CONVERT(varchar,A.no_oi),3) ");
			ret.append("   +'  '+  A.nm_sample AS サンプルNo ");

			//【QP@10181_No.21】 start
			ret.append(" ,G2.nm_literal AS 保存方法 ");
			ret.append(" ,B1.nm_busho AS 生産工場 ");
			ret.append(" ,B.shomikikan AS 賞味期間 ");
			ret.append(" ,B.youki AS 容器 ");
			ret.append(" ,B.yoryo+G3.nm_literal AS 容量 ");
			ret.append(" ,TS.no_seiho1 AS 製法番号 ");
			ret.append(" ,G4.nm_literal AS 一括表示の名称 ");
			ret.append(" ,RIGHT('0000000000'+CONVERT(varchar ");
			ret.append(" 		,A.cd_shain) ");
			ret.append(" 	,10) +'-'+ RIGHT('00'+CONVERT(varchar ");
			ret.append(" 		,A.nen) ");
			ret.append(" 	,2) +'-'+ RIGHT('000'+CONVERT(varchar ");
			ret.append(" 		,A.no_oi) ");
			ret.append(" 	,3) AS 試作品コード ");
			ret.append(" ,MG.nm_team AS グループ ");
			ret.append(" ,MU.nm_user AS 名前 ");
			ret.append(" ,B.dt_toroku AS 作成日 ");
			//【QP@10181_No.21】 end

			ret.append(" FROM  ");
			ret.append(" tr_shisaku AS A ");
			ret.append(" LEFT JOIN tr_shisakuhin AS B ");
			ret.append(" ON A.cd_shain = B.cd_shain ");
			ret.append(" AND A.nen = B.nen ");
			ret.append(" AND A.no_oi = B.no_oi ");
			ret.append(" LEFT JOIN tr_shisaku_list AS C ");
			ret.append(" ON A.cd_shain = C.cd_shain ");
			ret.append(" AND A.nen = C.nen ");
			ret.append(" AND A.no_oi = C.no_oi ");
			ret.append(" AND A.seq_shisaku = C.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS D ");
			ret.append(" ON C.cd_shain = D.cd_shain ");
			ret.append(" AND C.nen = D.nen ");
			ret.append(" AND C.no_oi = D.no_oi ");
			ret.append(" AND C.cd_kotei = D.cd_kotei ");
			ret.append(" AND C.seq_kotei = D.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS E ");
			ret.append(" ON D.cd_kaisha = E.cd_kaisha ");
			ret.append(" AND D.cd_genryo = E.cd_genryo ");
			//合計重量
			ret.append(" LEFT JOIN (  ");
			ret.append("  SELECT ");
			ret.append("    SUM(Fs.quantity) AS sum_quantity  ");
			ret.append("    ,Fs.cd_shain AS cd_shain ");
			ret.append("    ,Fs.nen AS nen ");
			ret.append("    ,Fs.no_oi AS no_oi ");
			ret.append("    ,Fs.seq_shisaku AS seq_shisaku ");
			ret.append("  FROM tr_shisaku_list Fs ");
			ret.append("  GROUP BY Fs.cd_shain, Fs.nen, Fs.no_oi, Fs.seq_shisaku ) F ");
			ret.append("  ON A.cd_shain = F.cd_shain ");
			ret.append("  AND A.nen = F.nen ");
			ret.append("  AND A.no_oi = F.no_oi ");
			ret.append("  AND A.seq_shisaku = F.seq_shisaku ");
			//小数桁数(リテラル値)
			ret.append("  LEFT JOIN ma_literal G ");
			ret.append("   ON  G.cd_category = 'K_shosu' ");
			ret.append("   AND  G.cd_literal = B.keta_shosu ");

			//【QP@10181_No.21】 start
			ret.append("  LEFT JOIN ma_literal G2 ");
			ret.append("  ON G2.cd_category = 'K_toriatukaiondo' ");
			ret.append("  AND G2.cd_literal = B.cd_ondo ");
			ret.append("  LEFT JOIN ma_busho B1 ");
			ret.append("  ON B1.cd_kaisha = B.cd_kaisha ");
			ret.append("  AND B1.cd_busho = B.cd_kojo ");
			ret.append("  LEFT JOIN ma_literal G3 ");
			ret.append("  ON G3.cd_category = 'K_tani' ");
			ret.append("  AND G3.cd_literal = B.cd_tani ");
			ret.append("  LEFT JOIN tr_shisaku AS TS ");
			ret.append("  ON B.cd_shain = TS.cd_shain ");
			ret.append("  AND B.nen = TS.nen ");
			ret.append("  AND B.no_oi = TS.no_oi ");
			ret.append("  AND B.seq_shisaku = TS.seq_shisaku ");
			ret.append("  LEFT JOIN ma_literal G4 ");
			ret.append("  ON G4.cd_category = 'K_ikatuhyouzi' ");
			ret.append("  AND G4.cd_literal = B.cd_ikatu ");
			ret.append("  LEFT JOIN ma_team MG ");
			ret.append("  ON MG.cd_group = B.cd_group ");
			ret.append("  AND MG.cd_team = B.cd_team ");
			ret.append("  LEFT JOIN ma_user MU ");
			ret.append("  ON MU.id_user = B.cd_shain ");
			//【QP@10181_No.21】 end

			ret.append(" WHERE  ");
			ret.append("     A.cd_shain = " + strCd_shain + " ");
			ret.append(" AND A.nen = " + strNen + " ");
			ret.append(" AND A.no_oi = " + strNo_oi + " ");
			ret.append(" AND A.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(C.quantity,0) > 0 ");
			//表示順
			ret.append(" ORDER BY D.sort_kotei, D.sort_genryo ");

		} catch (Exception e) {
			this.em.ThrowException(e, "サンプル説明書、検索SQLの生成に失敗しました。");

		} finally {

		}
		return ret;

	}

	/**
	 * Excel出力用の栄養計算食品番号の設定
	 * @param items : 検索結果
	 * @return Excelに設定する栄養計算食品番号の文字列
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private String SetEiyoNo(Object[] items) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String ret = "";

		try{

			//５回繰り返す
			for ( int i=0; i<5; i++ ) {

				//栄養計算食品番号がNULLまたは、空文字ではない場合
				if ( !toString(items[i+10]).isEmpty() ) {

					//文字列が空ではない場合、カンマを挿入
					if ( !ret.isEmpty() ) {
						ret += ",";

					}

					//栄養計算食品番号を文字列に追加
					ret += toString(items[i+10]);

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "Excel出力用栄養計算食品番号設定処理に失敗しました。");

		} finally {

		}
		return ret;

	}

	/**
	 * 小数桁数合わせ処理
	 * @param Value : 対象値
	 * @param intKetasu : 指定小数桁数
	 * @return  桁合わせ編集後文字列
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected Object SetShosuKeta(Object Value, int intKetasu)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		Object ret = null;

		try {

			//対象値がNULLまたは空文字では無い場合
			if ( !toString(Value).isEmpty() ) {

				//値をDecimalに設定
				BigDecimal dciValue = new BigDecimal(toDouble(Value));

				//値を指定小数桁数に合わせる
				ret = dciValue.setScale(intKetasu, BigDecimal.ROUND_HALF_EVEN).toString();

			}

		} catch(Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}

}