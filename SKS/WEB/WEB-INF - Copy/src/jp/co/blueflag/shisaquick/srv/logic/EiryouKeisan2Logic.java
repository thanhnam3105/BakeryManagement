package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 栄養計算書（五訂増補）　機能ID：SA430
 * @author isono
 * @since  2009/05/25
 */
public class EiryouKeisan2Logic extends LogicBaseExcel {

	/**
	 * コンストラクタ
	 */
	public EiryouKeisan2Logic() {
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
		//検索データ
		List<?> lstRecset = null;
		//エクセルファイルパス
		String DownLoadPath = "";

		try {
			//DB検索
			super.createSearchDB();
			lstRecset = getData(reqData);

			//Excelファイル生成
			DownLoadPath = makeExcelFile(lstRecset,reqData);

			//レスポンスデータ生成
			ret = CreateRespons(DownLoadPath);

		} catch (Exception e) {
			em.ThrowException(e, "栄養計算書（五訂増補）の生成に失敗しました。");

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
			ret.setID("SA440");

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
			em.ThrowException(e, "栄養計算書（五訂増補）");

		} finally {

		}
		return ret;

	}
	/**
	 * サンプル説明書を生成する
	 * @param lstRecset : 検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile(List<?> lstRecset,RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//EXCELテンプレートを読み込む
			//MOD 2013/06/18 ogawa start        Settingシートの削除を非表示に変更
			//修正前のソース
			//super.ExcelReadTemplate("調味料（五訂増補）");
			//修正後のソース
			super.ExcelReadTemplate("調味料（五訂増補）", false);
			//MOD 2013/06/18 ogawa  end

			//ダウンロード用のEXCELを生成する
			for (int i = 0; i < lstRecset.size(); i++) {

				//検索結果の1行分を取り出す
				Object[] items = (Object[]) lstRecset.get(i);

				try{
					//Excelに値をセットする
					//試作名
					super.ExcelSetValue("試作名", toString(items[0]));
					//製法番号
					super.ExcelSetValue("製法番号", toString(items[1]));

					//小数桁数の取得
					int intShosuKeta = 0;
					if ( !toString(items[12]).isEmpty() ) {
						intShosuKeta = Integer.parseInt(toString(items[12]));

					}

					//食品番号1・量1の値をセット
					if ( !toString(items[2]).isEmpty() ) {

						//配合量がある場合
						if(toDouble( toString( items[3])) > 0){
							//食品番号
							super.ExcelSetValue("食品番号", toString(items[2]));
							//使用量
							super.ExcelSetValue("使用量",
									toRoundString( toDouble( toString( items[3])), intShosuKeta ) );
						}
					}
					//食品番号2・量2の値をセット
					if ( !toString(items[4]).isEmpty() ) {

						//配合量がある場合
						if(toDouble( toString( items[5])) > 0){
							//食品番号
							super.ExcelSetValue("食品番号", toString(items[4]));
							//使用量
							super.ExcelSetValue("使用量",
									toRoundString( toDouble( toString( items[5])), intShosuKeta ) );
						}
					}
					//食品番号3・量3の値をセット
					if ( !toString(items[6]).isEmpty() ) {

						//配合量がある場合
						if(toDouble( toString( items[7])) > 0){
							//食品番号
							super.ExcelSetValue("食品番号", toString(items[6]));
							//使用量
							super.ExcelSetValue("使用量",
									toRoundString( toDouble( toString( items[7])), intShosuKeta ) );
						}
					}
					//食品番号4・量4の値をセット
					if ( !toString(items[8]).isEmpty() ) {

						//配合量がある場合
						if(toDouble( toString( items[9])) > 0){
							//食品番号
							super.ExcelSetValue("食品番号", toString(items[8]));
							//使用量
							super.ExcelSetValue("使用量",
									toRoundString( toDouble( toString( items[9])), intShosuKeta ) );
						}
					}
					//食品番号5・量5の値をセット
					if ( !toString(items[10]).isEmpty() ) {

						//配合量がある場合
						if(toDouble( toString( items[11])) > 0){
							//食品番号
							super.ExcelSetValue("食品番号", toString(items[10]));
							//使用量
							super.ExcelSetValue("使用量",
									toRoundString( toDouble( toString( items[11])), intShosuKeta ) );
						}
					}

				}catch(ExceptionWaning e){
					//最大行数を超えると、ExceptionWaningがThrowされるのでループを終了する。
					break;

				}finally{

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
			em.ThrowException(e, "栄養計算書（五訂増補）、excelの生成に失敗しました。");

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
	 * 対象の試作品データを検索する
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
			em.ThrowException(e, "栄養計算書（五訂増補）、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;
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
	private StringBuffer MakeSQLBuf(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try {

			String strCd_shain = "";
			String strNen = "";
			String strNo_oi = "";
			String strSeq_shisaku = "";

			//試作品CD　社員コード
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//試作品No　年
			strNen = reqData.getFieldVale(0, 0, "nen");
			//試作品No　追番
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//試作No（サンプル番号）
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku");

			//SQL文の作成
			ret.append(" SELECT ");
			ret.append("    T110.nm_hin AS 品名 ");
			ret.append("  , ISNULL(T131_seiho.no_seiho,'') AS 製法番号 ");
			ret.append("  , M401.no_eiyo1 AS 食品番号1 ");
			ret.append("  , CASE ISNUMERIC(ISNULL(M401.wariai1, '0')) WHEN 0 THEN 0 ");
			ret.append("     ELSE ISNULL(CONVERT(DECIMAL(5,2),M401.wariai1), 0) ");
			ret.append("     END * 0.01 * ISNULL(T132.quantity, 0) AS 量1 ");
			ret.append("  , M401.no_eiyo2 AS 食品番号2 ");
			ret.append("  , CASE ISNUMERIC(ISNULL(M401.wariai2, '0')) WHEN 0 THEN 0 ");
			ret.append("     ELSE ISNULL(CONVERT(DECIMAL(5,2),M401.wariai2), 0) ");
			ret.append("     END * 0.01 * ISNULL(T132.quantity, 0) AS 量2 ");
			ret.append("  , M401.no_eiyo3 AS 食品番号3 ");
			ret.append("  , CASE ISNUMERIC(ISNULL(M401.wariai3, '0')) WHEN 0 THEN 0 ");
			ret.append("     ELSE ISNULL(CONVERT(DECIMAL(5,2),M401.wariai3), 0) ");
			ret.append("     END * 0.01 * ISNULL(T132.quantity, 0) AS 量3 ");
			ret.append("  , M401.no_eiyo4 AS 食品番号4 ");
			ret.append("  , CASE ISNUMERIC(ISNULL(M401.wariai4, '0')) WHEN 0 THEN 0 ");
			ret.append("     ELSE ISNULL(CONVERT(DECIMAL(5,2),M401.wariai4), 0) ");
			ret.append("     END * 0.01 * ISNULL(T132.quantity, 0) AS 量4 ");
			ret.append("  , M401.no_eiyo5 AS 食品番号5 ");
			ret.append("  , CASE ISNUMERIC(ISNULL(M401.wariai5, '0')) WHEN 0 THEN 0 ");
			ret.append("     ELSE ISNULL(CONVERT(DECIMAL(5,2),M401.wariai5), 0) ");
			ret.append("     END * 0.01 * ISNULL(T132.quantity, 0) AS 量5 ");
			ret.append("  , ISNULL(M302.value1,0) AS keta_shosu ");
			ret.append("  , M401.cd_genryo AS cd_genryo ");
			ret.append("  FROM tr_shisakuhin AS T110 ");
			ret.append("  LEFT JOIN tr_shisaku AS T131 ");
			ret.append("   ON T110.cd_shain = T131.cd_shain ");
			ret.append("   AND T110.nen = T131.nen ");
			ret.append("   AND T110.no_oi = T131.no_oi ");
			ret.append("  LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append("   ON T110.cd_shain = T132.cd_shain ");
			ret.append("   AND T110.nen = T132.nen ");
			ret.append("   AND T110.no_oi = T132.no_oi ");
			ret.append("   AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append("  LEFT JOIN tr_haigo AS T120 ");
			ret.append("   ON T110.cd_shain = T120.cd_shain ");
			ret.append("   AND T110.nen = T120.nen ");
			ret.append("   AND T110.no_oi = T120.no_oi ");
			ret.append("   AND T132.cd_kotei = T120.cd_kotei ");
			ret.append("   AND T132.seq_kotei = T120.seq_kotei ");
			ret.append("  LEFT JOIN ma_genryo AS M401 ");
			ret.append("   ON T120.cd_genryo = M401.cd_genryo ");
			ret.append("   AND T120.cd_kaisha = M401.cd_kaisha ");
			ret.append("  LEFT JOIN ( ");
			ret.append("   SELECT ");
			ret.append("     T131s.cd_shain AS cd_shain ");
			ret.append("   , T131s.nen AS nen ");
			ret.append("   , T131s.no_oi AS no_oi ");
			ret.append("   , T131s.seq_shisaku AS seq_shisaku ");
			ret.append("   , T131s.no_seiho1 AS no_seiho ");
			ret.append("   FROM tr_shisaku AS T131s ");
			ret.append("  ) T131_seiho ");
			ret.append("   ON T110.cd_shain = T131_seiho.cd_shain ");
			ret.append("   AND T110.nen = T131_seiho.nen ");
			ret.append("   AND T110.no_oi = T131_seiho.no_oi ");
//			ret.append("   AND ISNULL(T131_seiho.no_seiho,'') <> '' ");
			ret.append("   AND T110.seq_shisaku = T131_seiho.seq_shisaku");
			ret.append("  LEFT JOIN ma_literal M302 ");
			ret.append("   ON M302.cd_category = 'K_shosu' ");
			ret.append("   AND M302.cd_literal = T110.keta_shosu ");
			ret.append(" WHERE ");
			ret.append("  T110.cd_shain = " + strCd_shain);
			ret.append("  AND T110.nen = " + strNen);
			ret.append("  AND T110.no_oi = " + strNo_oi);
			ret.append("  AND T131.seq_shisaku = " + strSeq_shisaku);
			ret.append("  AND NOT ( ISNULL(M401.no_eiyo1,'') = '' ");
			ret.append("   AND ISNULL(M401.no_eiyo2,'') = '' ");
			ret.append("   AND ISNULL(M401.no_eiyo3,'') = '' ");
			ret.append("   AND ISNULL(M401.no_eiyo4,'') = '' ");
			ret.append("   AND ISNULL(M401.no_eiyo5,'') = '' ) ");
//			ret.append("  ORDER BY M401.cd_genryo ");
			ret.append("  ORDER BY T120.sort_kotei, T120.sort_genryo ");

		} catch (Exception e) {
			this.em.ThrowException(e, "栄養計算書（五訂増補）、検索SQLの生成に失敗しました。");

		} finally {

		}
		return ret;

	}

}
