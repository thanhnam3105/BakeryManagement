package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * 原価試算項目固定値登録
 *  : 営業の確認完了済みデータについては、原価試算画面の項目固定値を登録。※データ補正のため、未登録データのみ対象
 *
 * @author TT.hisahori
 * @since  2014/09/19
 *
 */

public class FGEN2021_Logic extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public FGEN2021_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//              ExecLogic（処理開始）
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

			StringBuffer strSQL = new StringBuffer();
			List<?> lstRecset = null;

			//リクエストデータより試作コード取得
			String strReqShainCd = reqData.getFieldVale("kihon", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("kihon", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("kihon", 0, "no_oi");
			String strReqEda = reqData.getFieldVale("kihon", 0, "no_eda");

//			//選択ステータス取得
//			String setting = reqData.getFieldVale("kihon", 0, "setting");
//			//所属部署フラグ
//			String kojo = reqData.getFieldVale("kihon", 0, "busho_kojo");
//			//現在ステータス
//			String st_kojo = reqData.getFieldVale("kihon", 0, "st_kojo");

			//正規表現：数値確認用
			Pattern pattern = Pattern.compile("^[-]?[0-9]*[.]?[0-9]+");

			//更新用SQL作成
			for ( int i=0; i<reqData.getCntRow("keisan"); i++ ) {
				lstRecset = null;

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

				//SELECT文作成
				strSQL = new StringBuffer();
				strSQL.append(" SELECT cd_shain FROM");
				strSQL.append("    tr_shisan_shisaku_kotei");
				strSQL.append(" WHERE");
				strSQL.append("    cd_shain=" + strReqShainCd);
				strSQL.append(" AND nen=" + strReqNen);
				strSQL.append(" AND no_oi=" + strReqOiNo);
				strSQL.append(" AND seq_shisaku=" + strReqSeq);
				strSQL.append(" AND no_eda=" + strReqEda);

				//共通クラス　データベース管理を用い、SQLを実行
				lstRecset = this.searchDB.dbSearch(strSQL.toString());

				if( lstRecset.isEmpty() == false ){
					break;
				}

				//項目固定チェック対象の項目値を登録
				strSQL = null;
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

				//項目工程チェックをオン
				strSQL = null;
				strSQL = new StringBuffer();
				strSQL.append(" UPDATE tr_shisan_shisaku ");
				strSQL.append(" SET fg_koumokuchk = 1 ");
				strSQL.append(" WHERE ");
				strSQL.append("     cd_shain=" + strReqShainCd);
				strSQL.append(" AND nen=" + strReqNen);
				strSQL.append(" AND no_oi=" + strReqOiNo);
				strSQL.append(" AND seq_shisaku=" + strReqSeq);
				strSQL.append(" AND no_eda=" + strReqEda);

				this.execDB.execSQL(strSQL.toString());

			}
			// コミット
			execDB.Commit();

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			resKind.getTableItem(strTableNm);

			//処理結果の格納
			resKind.getTableItem(strTableNm).addFieldVale(0, "flg_return", "true");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_error", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "no_errmsg", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "nm_class", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "cd_error", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_system", "");
			resKind.getTableItem(strTableNm).addFieldVale(0, "msg_cd", "0");

		} catch (Exception e) {
			// ロールバック
			execDB.Rollback();
			this.em.ThrowException(e, "原価試算情報 管理操作処理が失敗しました。");

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
}
