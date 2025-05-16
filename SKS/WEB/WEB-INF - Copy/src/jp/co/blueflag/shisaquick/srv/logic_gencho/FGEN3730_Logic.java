package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 【KPX@1602367】
 *  資材手配添付テーブル（一覧）
 *  : 資材手配添付情報を取得する。
 *  機能ID：FGEN3730
 *
 * @author BRC t2nakamura
 * @since  2016/11/02
 */
public class FGEN3730_Logic extends LogicBase{

	/**
	 * 資材手配依頼書出力取得処理
	 * : インスタンス生成
	 */
	public FGEN3730_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * ベース単価取得
	 *  : ベース単価情報を取得する。
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

		RequestResponsKindBean resKind = null;

		try {

			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//レスポンスデータの形成
			this.setData(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}

			//変数の削除

		}
		return resKind;

	}

	/**
	 * レスポンスデータの形成
	 * @param resKind : レスポンスデータ
	 * @param reqData : リクエストデータ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void setData(
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// レコード値格納リスト
		List<?> lstRecset = null;

		// レコード値格納リスト
		StringBuffer strSqlBuf = null;

		try {

			// テーブル名
			String strTblNm = "xmlFGEN3730";

			// データ取得SQL作成
			strSqlBuf = this.createSQL(reqData);

			// 共通クラス　データベース検索を用いてSQL実行
			super.createSearchDB();
			lstRecset = searchDB.dbSearch_notError(strSqlBuf.toString());

//			// 検索結果がない場合
//			if (lstRecset.size() == 0){
//				em.ThrowException(ExceptionKind.警告Exception,"W000401", lstRecset.toString(), "", "");
//			}

			// レスポンスデータにテーブル追加
			resKind.addTableItem(strTblNm);

			// 追加したテーブルへレコード格納
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "資材手配依頼出力 データ検索処理が失敗しました。");

		} finally {

			// リストの破棄
			removeList(lstRecset);

			if (searchDB != null) {
				// セッションの解放
				this.searchDB.Close();
				searchDB = null;
			}

			// 変数の削除
			strSqlBuf = null;
		}
	}

	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
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
		StringBuffer strSql = new StringBuffer();
		String allkey = getALLKey(reqData);
		try {
			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){

				//
				String strKbn = reqData.getFieldVale(0, 0, "kbn");
				// 社員コード
				String strCdShain = reqData.getFieldVale(0, 0, "cd_shain");
				// 年コード
				String strNen = reqData.getFieldVale(0, 0, "nen");
				// 追番
				String strNooi = reqData.getFieldVale(0, 0, "no_oi");
				// seq資材
				String strSeqShizai = reqData.getFieldVale(0, 0, "seq_shizai");
				// 枝番
				String strNoeda = reqData.getFieldVale(0, 0, "no_eda");
				// 製品コード
				String strCdShohin = toString(reqData.getFieldVale(0, 0, "cd_shohin"));
				String strCdShohinFormat = get0SupressCode(strCdShohin);


				int intKbn = Integer.parseInt(strKbn);
				int intCdShain = Integer.parseInt(strCdShain);
				int intNen = Integer.parseInt(strNen);
				int intNooi = Integer.parseInt(strNooi);
				int intSeqShizai = Integer.parseInt(strSeqShizai);
				int intNoeda = Integer.parseInt(strNoeda);



				// SQL文の作成
				strSql.append("SELECT ");			// 版
				strSql.append("    han");
				strSql.append("    ,cd_ｌiteral_1");// 発注先コード
				strSql.append("    ,cd_2nd_literal_1");// 担当者コード１
				strSql.append("    ,cd_ｌiteral_2");	// 発注先コード２
				strSql.append("    ,cd_2nd_literal_2");		// 担当者コード２
				strSql.append("    ,naiyo ");// 内容
				strSql.append("    ,cd_shohin ");// 製品コード
				strSql.append("    ,nm_shohin ");// 製品名
				strSql.append("    ,nisugata ");// 荷姿
				strSql.append("    ,cd_taisyoshizai ");// 対象資材コード
				strSql.append("    ,nounyusaki ");// 納入先
				strSql.append("    ,cd_shizai ");// 旧資材コード
				strSql.append("    ,cd_shizai_new ");// 新資材コード
				strSql.append("    ,shiyohenko ");// 仕様変更
				strSql.append("    ,sekkei1 ");// 設計１
				strSql.append("    ,sekkei2 ");// 設計２
				strSql.append("    ,sekkei3 ");// 設計３
				strSql.append("    ,zaishitsu ");// 材質
				strSql.append("    ,biko_tehai ");// 備考
				strSql.append("    ,printcolor ");// プリントカラー
				strSql.append("    ,no_color ");//
				strSql.append("    ,henkounaiyoushosai");// 変更内容詳細
				strSql.append("    ,nouki");			// 納期
				strSql.append("    ,suryo");		// 数量
				strSql.append("    ,old_sizaizaiko");	// 旧資材在庫
				strSql.append("    ,rakuhan");		// 落版
				strSql.append("    ,hyoji_naiyo");	// 表示内容
				strSql.append("    ,hyoji_nisugata");	// 表示荷姿
				strSql.append("    ,hyoji_nounyusaki");	// 表示納入先
				strSql.append("    ,hyoji_cd_shizai");	// 表示旧資材
				strSql.append("    ,hyoji_cd_shizai_new");// 表示新資材コード
				strSql.append("    ,cd_tmp_group_key");
				strSql.append(" FROM ");
				strSql.append("    tr_shizai_tehai_temp  ");
				strSql.append(" WHERE ");
				strSql.append(" cd_shain =" + intCdShain); //
				strSql.append(" AND nen =" + intNen);//
				strSql.append(" AND no_oi =" + intNooi);//
				strSql.append(" AND seq_shizai =" + intSeqShizai);//
				strSql.append(" AND no_eda =" + intNoeda);//
				strSql.append(" AND kbn_shizai="+ intKbn);//
				strSql.append(" AND cd_shohin =" + strCdShohinFormat);
				strSql.append(" AND cd_tmp_group_key='" + allkey +"'");//


			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * パラメーター格納
	 *  : ステータス履歴画面へのレスポンスデータへ格納する。
	 * @param lstCostData : 検索結果情報リスト
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstCostData
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {


				// 処理結果の格納
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "flg_return_success", "false");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				if (lstCostData.size() != 0) {

					Object[] items = (Object[]) lstCostData.get(0);
					resTable.addFieldVale(0, "flg_return_success", "true");

					// 結果をレスポンスデータに格納
					resTable.addFieldVale(0, "han", toString(items[0]));					// 版
					resTable.addFieldVale(0, "cd_literal_1", toString(items[1]));		// 発注先コード１
					resTable.addFieldVale(0, "cd_2nd_literal_1", toString(items[2]));	// 担当者コード１
					resTable.addFieldVale(0, "cd_literal_2", toString(items[3]));		// 発注先コード２
					resTable.addFieldVale(0, "cd_2nd_literal_2", toString(items[4]));	// 担当者コード２
					resTable.addFieldVale(0, "naiyo", toString(items[5]));				// 内容
					resTable.addFieldVale(0, "cd_shohin", toString(items[6]));			// 製品コード
					resTable.addFieldVale(0, "nm_shohin", toString(items[7]));			// 製品名
					resTable.addFieldVale(0, "nisugata", toString(items[8]));				// 荷姿
					resTable.addFieldVale(0, "cd_taisyoshizai", toString(items[9]));				// 対象資材コード
					resTable.addFieldVale(0, "nounyusaki", toString(items[10]));				// 納入先
					resTable.addFieldVale(0, "cd_shizai", toString(items[11]));				// 旧資材コード
					resTable.addFieldVale(0, "cd_shizai_new", toString(items[12]));				// 新資材コード
					resTable.addFieldVale(0, "shiyohenko", toString(items[13]));				// 仕様変更
					resTable.addFieldVale(0, "sekkei1", toString(items[14]));				// 設計１
					resTable.addFieldVale(0, "sekkei2", toString(items[15]));				// 設計２
					resTable.addFieldVale(0, "sekkei3", toString(items[16]));				// 設計３
					resTable.addFieldVale(0, "zaishitsu", toString(items[17]));				// 材質
					resTable.addFieldVale(0, "biko_tehai", toString(items[18]));				// 備考
					resTable.addFieldVale(0, "printcolor", toString(items[19]));				// 印刷色
					resTable.addFieldVale(0, "no_color", toString(items[20]));				// 色番号
					resTable.addFieldVale(0, "henkounaiyoushosai", toString(items[21]));				// 変更内容詳細
					resTable.addFieldVale(0, "nouki", toString(items[22]));				// 納期
					resTable.addFieldVale(0, "suryo", toString(items[23]));				// 数量
					resTable.addFieldVale(0, "old_sizaizaiko", toString(items[24]));				// 旧資材在庫
					resTable.addFieldVale(0, "rakuhan", toString(items[25]));				// 落版
					resTable.addFieldVale(0, "hyoji_naiyo", toString(items[26]));				// 表示内容
					resTable.addFieldVale(0, "hyoji_nisugata", toString(items[27]));				// 表示荷姿
					resTable.addFieldVale(0, "hyoji_nounyusaki", toString(items[28]));			// 表示納入先
					resTable.addFieldVale(0, "hyoji_cd_shizai", toString(items[29]));			// 表示旧資材コード
					resTable.addFieldVale(0, "hyoji_cd_shizai_new", toString(items[30]));			// 表示新資材コード
					resTable.addFieldVale(0, "cd_tmp_group_key", toString(items[31]));			// テンプキー
				}


		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}
	/**
	 * tempKEY作成
	 * @param reqData
	 * @return
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String getALLKey(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String allkey = "";
		for(int j = 0;j < reqData.getCntRow(reqData.getTableID(0)); j++){
			//リクエストデータより試作コード取得
			// 社員コード
			String strReqShain   = toString(reqData.getFieldVale(0, j, "cd_shain"));
			// 年
			String strReqNen     = toString(reqData.getFieldVale(0, j, "nen"));
			// 追番
			String strReqNoOi    = toString(reqData.getFieldVale(0, j, "no_oi"));
			// seq番号
			String strSeqShizai    = toString(reqData.getFieldVale(0, j, "seq_shizai"));
			// 枝番
			String strReqNoEda   = toString(reqData.getFieldVale(0, j, "no_eda"));
			// 製品コード
			String strCdShohin = toString(reqData.getFieldVale(0, j, "cd_shohin"));

			int intShainCd = Integer.parseInt(strReqShain);
			String shainCd = String.valueOf(intShainCd);

			int intNen = Integer.parseInt(strReqNen);
			String nen = String.valueOf(intNen);

			int intNoOi = Integer.parseInt(strReqNoOi);
			String noOi =String.valueOf(intNoOi);

			int intSeqShizai = Integer.parseInt(strSeqShizai);
			String seqShizai = String.valueOf(intSeqShizai);

			int intNoEda = Integer.parseInt(strReqNoEda);
			String noEda = String.valueOf(intNoEda);

			String tempkey = shainCd + "_" + nen + "_" +  noOi
					 + "_" + seqShizai + "_" + noEda + "_" + strCdShohin + "_" ;
			allkey += tempkey;
		}
		return allkey;
	}
	/**
	 * 0サプレスする
	 * @param strCdShohin
	 * @return
	 */
	public String  get0SupressCode(String strCdShohin) {
		int intCdShohin = 0;
		if (!strCdShohin.equals("")) {
			 intCdShohin = Integer.parseInt(strCdShohin);
		}
		return String.valueOf(intCdShohin);
	}

}
