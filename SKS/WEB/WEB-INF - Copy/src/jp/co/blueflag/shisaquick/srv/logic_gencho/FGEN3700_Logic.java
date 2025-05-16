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
 * 資材情報更新処理
 *  機能ID：FGEN3360　
 *
 * @author t2nakamura
 * @since  2016/10/28
 */
public class FGEN3700_Logic extends LogicBase {

	/**
	 * 資材情報更新コンストラクタ
	 * : インスタンス生成
	 */
	public FGEN3700_Logic(){
		super();
	}

	/**
	 * 資材手配情報更新 管理操作
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

		//レスポンスデータクラス
		RequestResponsKindBean resKind = null;

		try {
			//レスポンスデータ（機能）生成
			resKind = new RequestResponsKindBean();

			// トランザクション開始
			super.createExecDB();
			execDB.BeginTran();

			//DBコネクション
			createSearchDB();

			// 更新処理
			updateSQL(reqData);

			// コミット
			execDB.Commit();

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// レスポンスデータの格納
			storageData(resKind.getTableItem(0));


		} catch (Exception e) {
			// ロールバック
			execDB.Rollback();
			this.em.ThrowException(e, "資材情報更新DB処理に失敗しました。");

		} finally {
			//セッションのクローズ
			if (execDB != null) {
				execDB.Close();
				execDB = null;
			}
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}
		}
		return resKind;
	}

	/**
	 * 更新処理
	 *  : 資材手配情報の更新を行う
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */

	//==========================================================================================
	//仮テープルに保存
	// SQL作成

	//=========================================================================================
	private void updateSQL(
			RequestResponsKindBean reqData)
					throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL;
		List<?> lstRecset;
		List<?> lstRecset2;
		List<?> lstRecset3;
		String allkey = "";
		try {
			allkey = getALLKey(reqData);

			for(int i = 0;i < reqData.getCntRow(reqData.getTableID(0)); i++){


				//リクエストデータより試作コード取得
				// 社員コード
				String strReqShain   = toString(reqData.getFieldVale(0, i, "insert_cd_shain"));

				// 年
				String strReqNen     = toString(reqData.getFieldVale(0, i, "insert_nen"));
				// 追番
				String strReqNoOi    = toString(reqData.getFieldVale(0, i, "insert_no_oi"));
				// seq番号
				String strSeqShizai    = toString(reqData.getFieldVale(0, i, "insert_seq_shizai"));
				// 枝番
				String strReqNoEda   = toString(reqData.getFieldVale(0, i, "insert_no_eda"));
				// 区分
				String strKbnShizai = toString(reqData.getFieldVale(0, 0, "insert_kbn_shizai"));
				// 版
				String strHan = toString(reqData.getFieldVale(0, i, "insert_han"));
				// 発注先1
				String strCdLiteral1   = toString(reqData.getFieldVale(0, 0, "insert_cd_literal1"));
				int intCdLiteral1 = 0;
				if (!strCdLiteral1.equals("")) {
					intCdLiteral1 = Integer.parseInt(strCdLiteral1);
				}
				// 担当者1
				String strCd2ndLiteral1  = toString(reqData.getFieldVale(0, 0, "insert_cd_2nd_literal1"));
				// 発注先２
				String strCdLiteral2 = toString(reqData.getFieldVale(0, 0, "insert_cd_liiteral2"));
				int intCdLiteral2 = 0;
				if (!strCdLiteral2.equals("")) {
					intCdLiteral2 = Integer.parseInt(strCdLiteral2);
				}
				// 担当者２
				String strCd2ndLiteral2 = toString(reqData.getFieldVale(0, 0,"insert_cd_2nd_literal2"));
				// 内容
				String strNaiyo = toString(reqData.getFieldVale(0, i, "insert_naiyo"));
				// 製品コード
				String strCdShohin = toString(reqData.getFieldVale(0, i, "insert_cd_shohin"));
				String strCdShohinFormat = get0SupressCode(strCdShohin);
				// 製品名
				String strNmShohin = toString(reqData.getFieldVale(0, i, "insert_nm_shohin"));
				// 荷姿
				String strNisugata = toString(reqData.getFieldVale(0, i, "insert_nisugata"));
				// 対象資材
				String strTaisyoshizai = toString(reqData.getFieldVale(0, 0, "insert_cd_taisyoshizai"));
				// 納入債
				String strNounyudaki = toString(reqData.getFieldVale(0, i, "insert_nounyusaki"));
				// 旧資材コード
				String strCdShizai = toString(reqData.getFieldVale(0, i, "insert_cd_shizai"));
				// 新資材コード1
				String strCdShizaiNew = toString(reqData.getFieldVale(0, i, "insert_cd_shizai_new"));
				// 仕様変更
				String strShiyohenko = toString(reqData.getFieldVale(0, i, "insert_shiyohenko"));
				// 設計１
				String strSekkei1    = toString(reqData.getFieldVale(0, 0, "sekkei1"));
				// 設計２
				String strSekkei2    = toString(reqData.getFieldVale(0, 0, "sekkei2"));
				// 設計３
				String strSekkei3    = toString(reqData.getFieldVale(0, 0, "sekkei3"));
				// 材質
				String strZaishitsu  = toString(reqData.getFieldVale(0, 0, "zaishitsu"));
				// 備考
				String strBiko = toString(reqData.getFieldVale(0, 0, "biko"));
				// 印刷色
				String strPrintColor = toString(reqData.getFieldVale(0, 0, "printcolor"));
				// 色番号
				String strNoColor    = toString(reqData.getFieldVale(0, 0, "no_color"));
				// 変更内容詳細
				String strHenkounaiyou = toString(reqData.getFieldVale(0, 0, "henkounaiyoushosai"));
				// 納期
				String strNouki = toString(reqData.getFieldVale(0, 0, "nouki"));
				// 数量
				String strSuryo = toString(reqData.getFieldVale(0, 0, "suryo"));
				// 旧資材在庫
				String strOldShizaizaiko = toString(reqData.getFieldVale(0, 0, "old_sizaizaiko"));
				// 落版
				String strRakuhan = toString(reqData.getFieldVale(0, 0, "rakuhan"));
				// 表示内容
				String strHyojiNaiyo = toString(reqData.getFieldVale(0, 0, "hyoji_naiyo"));
				// 表示荷姿
				String strHyojiNisugata = toString(reqData.getFieldVale(0, 0, "hyoji_nisugata"));
				// 表示納入先
				String strHyojiNounyusaki = toString(reqData.getFieldVale(0, 0, "hyoji_nounyusaki"));
				// 表示旧資材コード
				String strHyojiCdShizai = toString(reqData.getFieldVale(0, 0, "hyoji_cd_shizai"));
				// 表示新資材コード
				String strHyojiCdShizaiNew = toString(reqData.getFieldVale(0, 0, "hyoji_cd_shizai_new"));

				// 版代支払日
				String strHandaiPayDay = toString(reqData.getFieldVale(0, i, "insert_dt_han_payday"));
				// 版代
				String strHandai = toString(reqData.getFieldVale(0, i, "insert_file_han_pay"));
				// 青焼ファイル名
				String strAoyakiFileName = toString(reqData.getFieldVale(0, i, "insert_nm_file_aoyaki"));
				// 青焼ファイルパス
				String strAoyakiFilePath = toString(reqData.getFieldVale(0, i, "insert_file_path_aoyaki"));
				// 数値に変換
				int shainCd = Integer.parseInt(strReqShain);
				int nen = Integer.parseInt(strReqNen);
				int noOi = Integer.parseInt(strReqNoOi);
				int seqShizai = Integer.parseInt(strSeqShizai);
				int noEda = Integer.parseInt(strReqNoEda);

				StringBuffer strInsertSQL1 = new StringBuffer();

				strInsertSQL1.append(" SELECT ");
				strInsertSQL1.append(" cd_shain");
				strInsertSQL1.append(" FROM ");
				strInsertSQL1.append("    tr_shizai_tehai_temp ");
				strInsertSQL1.append(" WHERE ");
				strInsertSQL1.append("        cd_shain = " + shainCd);
				strInsertSQL1.append("    AND nen = " + nen);
				strInsertSQL1.append("    AND no_oi = " + noOi);
				strInsertSQL1.append("    AND no_eda = " + noEda);
				strInsertSQL1.append("    AND seq_shizai = " + seqShizai);
				strInsertSQL1.append("    AND kbn_shizai = "+ strKbnShizai);
				strInsertSQL1.append("    AND cd_tmp_group_key = '"+ allkey + "'");

				//共通クラス　データベース管理を用い、SQLを実行
				lstRecset = this.searchDB.dbSearch_notError(strInsertSQL1.toString());

				StringBuffer strInsertSQL2 = new StringBuffer();
				if (lstRecset.size() != 0) {

					//SELECT文作成

					strInsertSQL2.append(" DELETE ");
					strInsertSQL2.append(" FROM ");
					strInsertSQL2.append("    tr_shizai_tehai_temp ");
					strInsertSQL2.append("   WHERE");
					strInsertSQL2.append("        cd_shain = " + shainCd);
					strInsertSQL2.append("    AND nen = " + nen);
					strInsertSQL2.append("    AND no_oi = " + noOi);
					strInsertSQL2.append("    AND no_eda = " + noEda);
					strInsertSQL2.append("    AND seq_shizai = " + seqShizai);
					strInsertSQL2.append("    AND kbn_shizai = "+ strKbnShizai);
					strInsertSQL2.append("    AND cd_tmp_group_key = '"+ allkey + "'");
					//共通クラス　データベース管理を用い、SQLを実行
					execDB.execSQL(strInsertSQL2.toString());
				}
				StringBuffer strInsertSQL = new StringBuffer();

				strInsertSQL.append("INSERT INTO tr_shizai_tehai_temp (");
				strInsertSQL.append(" cd_tmp_group_key");
				strInsertSQL.append(", cd_shain");
				strInsertSQL.append(", nen");
				strInsertSQL.append(", no_oi");
				strInsertSQL.append(", seq_shizai");
				strInsertSQL.append(", no_eda");
				strInsertSQL.append(", kbn_shizai");
				strInsertSQL.append(", han");
				strInsertSQL.append(", cd_literal_1");
				strInsertSQL.append(", cd_2nd_literal_1");
				strInsertSQL.append(", cd_literal_2");
				strInsertSQL.append(", cd_2nd_literal_2");
				strInsertSQL.append(", naiyo");
				strInsertSQL.append(", cd_shohin");
				strInsertSQL.append(", nm_shohin");
				strInsertSQL.append(", nisugata");
				strInsertSQL.append(", cd_taisyoshizai");
				strInsertSQL.append(", nounyusaki");
				strInsertSQL.append(", cd_shizai");
				strInsertSQL.append(", cd_shizai_new");
				strInsertSQL.append(", shiyohenko");
				strInsertSQL.append(", sekkei1");
				strInsertSQL.append(", sekkei2");
				strInsertSQL.append(", sekkei3");
				strInsertSQL.append(", zaishitsu");
				strInsertSQL.append(", biko_tehai");
				strInsertSQL.append(", printcolor");
				strInsertSQL.append(", no_color");
				strInsertSQL.append(", henkounaiyoushosai");
				strInsertSQL.append(", nouki");
				strInsertSQL.append(", suryo");
				strInsertSQL.append(", old_sizaizaiko");
				strInsertSQL.append(", rakuhan");
				strInsertSQL.append(", hyoji_naiyo");
				strInsertSQL.append(", hyoji_nisugata");
				strInsertSQL.append(", hyoji_nounyusaki");
				strInsertSQL.append(", hyoji_cd_shizai");
				strInsertSQL.append(", hyoji_cd_shizai_new");
				strInsertSQL.append(", dt_han_payday");	// 版代支払日
				strInsertSQL.append(", han_pay");	// 版代
				strInsertSQL.append(", nm_file_aoyaki");	// 青焼きファイル名
				strInsertSQL.append(", file_path_aoyaki");	// 青焼きファイルパス
				strInsertSQL.append(", id_toroku");
				strInsertSQL.append(", dt_toroku");
				strInsertSQL.append(", id_koshin");
				strInsertSQL.append(", dt_koshin");
				strInsertSQL.append(", flg_tehai_status");
				strInsertSQL.append(" ) VALUES ( ");
				strInsertSQL.append(  "'" + allkey + "'");				// key
				strInsertSQL.append("  ," +  strReqShain );			// 社員コード
				strInsertSQL.append("  ," + strReqNen);					// 年
				strInsertSQL.append("  ," + strReqNoOi);				// 追番
				strInsertSQL.append("  ," + strSeqShizai);				// seq番号
				strInsertSQL.append("  ," + strReqNoEda);				// 枝番
				strInsertSQL.append("  ," + strKbnShizai);				// 区分
				strInsertSQL.append("  ," + strHan);					// 版
				if (!strCd2ndLiteral1.equals("")) {
					strInsertSQL.append("  ,CONVERT(int, " + intCdLiteral1 + ")");		// 発注先1
				} else {
					strInsertSQL.append("  ,'" + strCdLiteral1 + "'");		// 発注先1
				}
				strInsertSQL.append("  ,'" + strCd2ndLiteral1 + "'");	// 担当者１
				if (!strCd2ndLiteral2.equals("")) {
					strInsertSQL.append("  ,CONVERT(int, " + intCdLiteral2 + ")");		// 発注先２
				} else {
					strInsertSQL.append("  ,'" + strCdLiteral2 + "'");		// 発注先２
				}
				strInsertSQL.append("  ,'" + strCd2ndLiteral2 + "'");	// 担当者２
				strInsertSQL.append("  ,'" + strNaiyo + "'");			// 内容
				strInsertSQL.append("  ,'" + strCdShohinFormat + "'");		// 製品コード
				strInsertSQL.append("  ,'" + strNmShohin + "'");		// 製品名
				strInsertSQL.append("  ,'" + strNisugata + "'");		// 荷姿
				strInsertSQL.append("  ,'" + strTaisyoshizai + "'");	// 対象資材
				strInsertSQL.append("  ,'" + strNounyudaki + "'");		// 納入先
				strInsertSQL.append("  ,'" + strCdShizai + "'");		// 旧資材コード
				strInsertSQL.append("  ,'" + strCdShizaiNew + "'");		// 新資材コード
				strInsertSQL.append("  ," + strShiyohenko);				// 仕様変更
				strInsertSQL.append("  ,'" + strSekkei1 + "'");			// 設計１
				strInsertSQL.append("  ,'" + strSekkei2 + "'");			// 設計２
				strInsertSQL.append("  ,'" + strSekkei3 + "'");			// 設計３
				strInsertSQL.append("  ,'" + strZaishitsu + "'");		// 材質
				strInsertSQL.append("  ,'" + strBiko + "'");			// 備考
				strInsertSQL.append("  ,'" + strPrintColor + "'");		// 印刷色
				strInsertSQL.append("  ,'" + strNoColor + "'");			// 色番号
				strInsertSQL.append("  ,'" + strHenkounaiyou + "'");	// 変更内容詳細
				strInsertSQL.append("  ,'" + strNouki + "'");			// 納期
				strInsertSQL.append("  ,'" + strSuryo + "'");			// 数量
				strInsertSQL.append("  ,'" + strOldShizaizaiko +"'");	// 旧資材在庫
				strInsertSQL.append("  ,'" + strRakuhan + "'");			// 落版
				// 表示されている項目
				strInsertSQL.append("  ,'" + strHyojiNaiyo + "'");		// 表示内容
				strInsertSQL.append("  ,'" + strHyojiNisugata + "'");	// 表示荷姿
				strInsertSQL.append("  ,'" + strHyojiNounyusaki + "'");	// 表示納入先
				strInsertSQL.append(",  '" + strHyojiCdShizai + "'");	// 表示旧資材コード
				strInsertSQL.append("  ,'" + strHyojiCdShizaiNew + "'");// 表示資材コード
				//追加
				strInsertSQL.append("  ,NULL");// 版代支払日
				strInsertSQL.append("  ,NULL");// 版代
				strInsertSQL.append("  ,NULL");// 青焼きファイル名
				strInsertSQL.append("  ,NULL");// 青焼きファイルパス
//				strInsertSQL.append("  ,'" + strHandaiPayDay + "'");// 版代支払日
//				strInsertSQL.append("  ,'" + strHandai + "'");// 版代
//				strInsertSQL.append("  ,'" + strAoyakiFileName + "'");// 青焼きファイル名
//				strInsertSQL.append("  ,'" + strAoyakiFilePath + "'");// 青焼きファイルパス
				strInsertSQL.append("  ," + userInfoData.getId_user());	// 登録者ID
				strInsertSQL.append("  ,  GETDATE()");					// 登録日付
				strInsertSQL.append("  ," + userInfoData.getId_user());	// 更新者ID
				strInsertSQL.append("  , + GETDATE()");					// 更新日付
				strInsertSQL.append("  , 2)");							//ステータス
				execDB.execSQL(strInsertSQL.toString());				// SQL実行

				// 資材手配テーブルインサート

				//SELECT文作成
				strSQL = new StringBuffer();
				strSQL.append(" SELECT ");
				strSQL.append("   cd_shain ");
				strSQL.append(" FROM ");
				strSQL.append("    tr_shizai_tehai ");
				strSQL.append(" WHERE ");
				strSQL.append("        cd_shain = " + shainCd);
				strSQL.append("    AND nen = " + nen);
				strSQL.append("    AND no_oi = " + noOi);
				strSQL.append("    AND no_eda = " + noEda);
				strSQL.append("    AND seq_shizai = " + seqShizai);
//				//2016.11.23 発注した対象の区分を変更するのはまずいので追加（発注完了時は資材手配のデータは更新されずデータは更新されない。
//				strSQL.append("    AND flg_tehai_status < 3 ");

				//共通クラス　データベース管理を用い、SQLを実行
				lstRecset2 = this.searchDB.dbSearch_notError(strSQL.toString());



				// 資産資材テーブルにデータが存在する場合は更新する
				if(lstRecset2.size() != 0){
					//資材テーブルの更新は、資材手配済みのステータスが資材手配済み（３）でないことが前提
					//ＩＦ分を見直した。
					StringBuffer strShisan = new StringBuffer();

					// 資産資材テーブル更新
					strShisan.append("SELECT ");
					strShisan.append("  cd_shain");
					strShisan.append(" FROM  tr_shisan_shizai");
					strShisan.append(" WHERE ");
					strShisan.append("        cd_shain = " + shainCd);
					strShisan.append("    AND nen = " + nen);
					strShisan.append("    AND no_oi = " + noOi);
					strShisan.append("    AND no_eda = " + noEda);
					strShisan.append("    AND seq_shizai = " + seqShizai);
					strShisan.append("    AND cd_seihin = " + strCdShohinFormat);

					//資産資材にデータ有無確認の、SQLを実行
					lstRecset3 = this.searchDB.dbSearch_notError(strShisan.toString());

					if (lstRecset3.size() != 0 ) {

						// ステータスの更新
						// SQL作成
						strSQL = new StringBuffer();
						strSQL.append(" UPDATE ");
						strSQL.append("   tr_shizai_tehai ");
						strSQL.append(" SET ");
						strSQL.append("   flg_tehai_status = 2 ");
						strSQL.append(" WHERE ");
						strSQL.append("        cd_shain = " + shainCd);
						strSQL.append("    AND nen = " + nen);
						strSQL.append("    AND no_oi = " + noOi);
						strSQL.append("    AND no_eda = " + noEda);
						strSQL.append("    AND seq_shizai = " + seqShizai);
						strSQL.append("    AND flg_tehai_status < 3 ");

						//共通クラス　データベース管理を用い、SQLを実行
						this.execDB.execSQL(strSQL.toString());

						// SQL作成
						strSQL = new StringBuffer();
						strSQL.append(" UPDATE ");
						strSQL.append("   tr_shizai_tehai ");
						strSQL.append(" SET ");
						strSQL.append("   naiyo   = '" + strHyojiNaiyo + "',");				// 内容（内容の入力は表示用内容とする（2016.11.23））
						strSQL.append("   nm_shohin   = '" + strNmShohin + "',");			// 製品名
						strSQL.append("   nounyusaki   = '" + strNounyudaki + "',");		// 納入先
						String strCdShizaiFormat = get0SupressShizaiCd(strCdShizai);
						strSQL.append("   cd_shizai   = '" + strCdShizaiFormat + "',");			// 旧資材コード
						String strCdShizaiNewFormat = get0SupressShizaiCd(strCdShizaiNew);
						strSQL.append("   cd_shizai_new   = '" + strCdShizaiNewFormat + "',");	// 新資材コード
						strSQL.append("   sekkei1 = '" + strSekkei1 + "', ");				// 設計１
						strSQL.append("   sekkei2 = '" + strSekkei2 + "', ");				// 設計２
						strSQL.append("   sekkei3 = '" + strSekkei3 + "', ");				// 設計３
						strSQL.append("   zaishitsu = '" + strZaishitsu + "', ");			// 材質
						strSQL.append("   biko_tehai = '" + strBiko + "', ");					// 備考
						strSQL.append("   printcolor = '" + strPrintColor + "', ");			// 印刷色
						strSQL.append("   no_color = '" + strNoColor + "', ");				// 色番号
						strSQL.append("   henkounaiyoushosai = '" + strHenkounaiyou + "', ");	// 変更内容詳細
						strSQL.append("   nouki = '" + strNouki + "', ");						// 納期
						strSQL.append("   id_koshin = '" + userInfoData.getId_user() + "', ");//更新ユーザ（追加）
						strSQL.append("   rakuhan = '" + strRakuhan+ "', ");//落版追加
						strSQL.append("   old_sizaizaiko = '" + strOldShizaizaiko + "', ");//旧資材
						strSQL.append("   suryo='" + strSuryo + "' ");						// 数量
						strSQL.append(" WHERE ");
						strSQL.append("        cd_shain = " + shainCd);
						strSQL.append("    AND nen = " + nen);
						strSQL.append("    AND no_oi = " + noOi);
						strSQL.append("    AND no_eda = " + noEda);
						strSQL.append("    AND seq_shizai = " + seqShizai);
						strSQL.append("    AND cd_shohin  = '" + strCdShohinFormat + "'");
//						//2016.11.23 発注した対象の区分を変更するのはまずいので追加（発注完了時は資材手配のデータは更新されずデータは更新されない。
//						strSQL.append("    AND flg_tehai_status < 3 ");
						//共通クラス　データベース管理を用い、SQLを実行
						this.execDB.execSQL(strSQL.toString());



						// ステータスの更新
						// SQL作成
						strShisan = new StringBuffer();
						strShisan.append(" UPDATE ");
						strShisan.append("   tr_shisan_shizai ");
						strShisan.append(" SET ");
						strShisan.append("   cd_hattyu = " + userInfoData.getId_user() + ", ");
						strShisan.append("  cd_hattyusaki = '" +  strCdLiteral1 + "', ");//発注先コード
						strShisan.append("  cd_taisyoshizai = '" +  strTaisyoshizai + "',");//対象資材コード
						strShisan.append("   id_koshin = '" + userInfoData.getId_user() + "', ");
						strShisan.append("   dt_koshin = GETDATE() ");
						strShisan.append(" WHERE ");
						strShisan.append("        cd_shain = " + shainCd);
						strShisan.append("    AND nen = " + nen);
						strShisan.append("    AND no_oi = " + noOi);
						strShisan.append("    AND no_eda = " + noEda);
						strShisan.append("    AND seq_shizai = " + seqShizai);
						strShisan.append("    AND cd_seihin = " + strCdShohinFormat);

						this.execDB.execSQL(strShisan.toString());
					}
				}
				// クリア
				strSQL = null;
			}

		} catch (Exception e) {

			em.ThrowException(e, "資材情報更新DB処理に失敗しました。");

		} finally {

		}
	}
	/**
	 * テンポラリー用キー作成
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
			String strReqShain   = toString(reqData.getFieldVale(0, j, "insert_cd_shain"));
			// 年
			String strReqNen     = toString(reqData.getFieldVale(0, j, "insert_nen"));
			// 追番
			String strReqNoOi    = toString(reqData.getFieldVale(0, j, "insert_no_oi"));
			// seq番号
			String strSeqShizai    = toString(reqData.getFieldVale(0, j, "insert_seq_shizai"));
			// 枝番
			String strReqNoEda   = toString(reqData.getFieldVale(0, j, "insert_no_eda"));
			// 製品コード
			String strCdShohin = toString(reqData.getFieldVale(0, j, "insert_cd_shohin"));

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
     * パラメーター格納
     *  : 資材手配依頼書出力画面へのレスポンスデータへ格納する。
     * @param lstGenkaHeader : 検索結果情報リスト
     * @throws ExceptionWaning
     * @throws ExceptionUser
     * @throws ExceptionSystem
     */
	private void storageData(RequestResponsTableBean resTable) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			resTable.addFieldVale(0, "msg_cd", "0");

		} catch (Exception e) {
			this.em.ThrowException(e, "資材情報更新DB処理に失敗しました。");

		} finally {

		}
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

	/**
	 * 0サプレスする
	 * @param str
	 * @return convertShizai
	 */
	private String get0SupressShizaiCd(String str) {
		int intCdShizai = 0;
		if (!str.equals("")) {
			 intCdShizai = Integer.parseInt(str);
		}
		return String.valueOf(intCdShizai);
	}

}
