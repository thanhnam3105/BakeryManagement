package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【KPX@1502111_No.5】
 *  分析値入力：配合リンクＤＢ処理
 *  機能FGEN2250
 *
 * @author TT.Kitazawa
 * @since 2016/06/07
 */
public class FGEN2250_Logic extends LogicBaseJExcel {

	/**
	 * コンストラクタ
	 */
	public FGEN2250_Logic() {
		//スーパークラスのコンストラクタ呼び出し
		super();

	}
	/**
	 * 分析値入力：配合リンクＤＢ処理
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

		StringBuffer strSql = new StringBuffer();

		//レスポンスデータ（機能）
		RequestResponsKindBean resKind = null;

		try {

			// レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// トランザクション開始
			super.createExecDB();

			execDB.BeginTran();

			strSql = createSQL(reqData);

			if (strSql != null) {
				// コミット
				execDB.Commit();
			}

			//機能IDの設定
			String strKinoId = reqData.getID();

			resKind.setID(strKinoId);

            // テーブル名の設定
            String strTableNm = reqData.getTableID(0);

            resKind.addTableItem(strTableNm);

            // レスポンスデータの形成
            this.storageData(resKind.getTableItem(strTableNm));

		} catch (Exception e) {
			if (execDB != null) {
				// ロールバック
				execDB.Rollback();
			}

			this.em.ThrowException(e, "");

		} finally {
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
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @return strSql：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createSQL(
            RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL格納用
		StringBuffer strSql = null;

		try {

			//処理区分
			String strKbn      = reqData.getFieldVale("table", 0, "kbn_shori");
			//原料新規登録：原料コード採番前の時、処理を抜ける
			if (strKbn.equals("0")) {
				return null;
			}

            //会社コード
			String strKaishaCd  = reqData.getFieldVale("table", 0, "cd_kaisha");
			//原料コード
			String strGenryoCd  = reqData.getFieldVale("table", 0, "cd_genryo");
			//部署コード
			String strBushoCd   = reqData.getFieldVale("table", 0, "cd_busho");
			if (strBushoCd.equals("")) {
				strBushoCd = ConstManager.getConstValue(Category.設定, "SHINKIGENRYO_BUSHOCD");
			}
			//試作CD-社員CD
			String strShainCd   = reqData.getFieldVale("table", 0, "cd_shain");
			//試作CD-年
			String strNen       = reqData.getFieldVale("table", 0, "nen");
			//試作CD-追番
			String strNo_oi     = reqData.getFieldVale("table", 0, "no_oi");
			//試作SEQ
			String strSeqShisaku  = reqData.getFieldVale("table", 0, "seq_shisaku");
			//枝番号
			String strNoEda     = reqData.getFieldVale("table", 0, "no_eda");

            strSql = new StringBuffer();
			// SQL文の作成
			strSql.append(" SELECT cd_shain  ");
			strSql.append("       ,nen  ");
			strSql.append("       ,no_oi  ");
			strSql.append("       ,seq_shisaku  ");
			strSql.append("       ,no_eda  ");
			strSql.append(" FROM  tr_haigo_link  ");
			strSql.append(" WHERE  cd_kaisha = " + strKaishaCd );
			strSql.append("   AND  cd_genryo = '" + strGenryoCd + "'");
			strSql.append("   AND  cd_busho = " + strBushoCd );

			super.createSearchDB();

			List<?> lstRecset = searchDB.dbSearch(strSql.toString());

			if(lstRecset.size() > 0) {
				strSql = new StringBuffer();

				// 削除SQL文の作成
				strSql.append(" DELETE  ");
				strSql.append(" FROM  tr_haigo_link  ");
				strSql.append(" WHERE  cd_kaisha = " + strKaishaCd );
				strSql.append("   AND  cd_genryo = '" + strGenryoCd + "'");
				strSql.append("   AND  cd_busho = " + strBushoCd );

				execDB.execSQL(strSql.toString());
			 }

			// 試作CDが空（試作SEQ=""） の時削除
//			if (!strSeqShisaku.equals("") ) {
			// 処理区分：削除（=3）以外の時
			if (!strKbn.equals("3") ) {
				strSql = new StringBuffer();

				// 登録SQL文の作成
				strSql.append("INSERT INTO tr_haigo_link (  ");
				strSql.append("  cd_kaisha,  ");
				strSql.append("  cd_genryo,  ");
				strSql.append("  cd_busho,  ");
				strSql.append("  cd_shain,  ");
				strSql.append("  nen,  ");
				strSql.append("  no_oi,  ");
				strSql.append("  seq_shisaku,  ");
				strSql.append("  no_eda,  ");
				strSql.append("  id_toroku,  ");
				strSql.append("  dt_toroku,  ");
				strSql.append("  id_koshin,  ");
				strSql.append("  dt_koshin  ");
				strSql.append(")  ");
				strSql.append("VALUES  ");
				strSql.append("(  ");
				strSql.append(      strKaishaCd  + ",  " );
				strSql.append("  '" + strGenryoCd  + "',  ");
				strSql.append(      strBushoCd  + ",  " );
				strSql.append(      strShainCd  + ",  " );
				strSql.append(      strNen  + ",  " );
				strSql.append(      strNo_oi  + ",  " );
				strSql.append(      strSeqShisaku  + ",  " );
				strSql.append(      strNoEda  + ",  " );

				strSql.append("  " + userInfoData.getId_user() + ",  ");
				strSql.append("  GETDATE(),  ");
				strSql.append("  " + userInfoData.getId_user() + ",  ");
				strSql.append("  GETDATE()   ");
				strSql.append(")  ");

				execDB.execSQL(strSql.toString());

            }

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : レスポンスデータへ格納する。
	 * @param resTable : レスポンスデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
            // 処理結果の格納
            resTable.addFieldVale(0, "flg_return", "true");
            resTable.addFieldVale(0, "msg_error", "");
            resTable.addFieldVale(0, "no_errmsg", "");
            resTable.addFieldVale(0, "nm_class", "");
            resTable.addFieldVale(0, "cd_error", "");
            resTable.addFieldVale(0, "msg_system", "");


		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}
}
