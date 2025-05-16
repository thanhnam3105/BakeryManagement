package jp.co.blueflag.shisaquick.srv.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN2010_Logic;

/**
 *
 * JWS-試作テーブル管理DB処理
 *  : JWS-試作テーブル管理DB処理のＤＢに対する業務ロジックの実装
 *
 * @author TT.k-katayama
 * @since  2009/04/15
 *
 */
public class ShisakuTblKanriDataChekck extends LogicBase {

	/**
	 * コンストラクタ
	 */
	public ShisakuTblKanriDataChekck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 試作テーブル管理操作
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

			//①:トランザクションを開始する
			//DB更新の生成
			super.createExecDB();
			//DB検索の生成
			super.createSearchDB();
			//トランザクション開始
			this.searchDB.BeginTran();
			//トランザクションをexecDBと共有する。
			execDB.setSession(searchDB.getSession());


			//【QP@00342】今回追加の試作SEQのみ取得
			boolean flgCopy = false;
			BigDecimal  cd_shain = new BigDecimal("0");
			int     nen = 0;
			int     no_oi = 0;
			int     seq_shisaku=0;
			ArrayList<Integer> list_seq_shisaku = new ArrayList();
			StringBuffer strSQL = null;
			List<?> lstSearchAry = null;

			//2012/08/02
			String strTableName = "tr_shisaku_list";
			String strSeq_shisaku = "";//ブレークキー:試作SEQ
			int intRecIndex = 0;//取得レコードインデックス

			String strFlg_shisanIrai = "";//試作依頼フラグ(1で依頼済み)
			String strFlg_cancel = "";//試作依頼キャンセル
			String strCd_genryo = "";
			String strCd_kaisha = "";
			String strNm_genryo = "";
			// 20160913 ADD KPX@1600766 Start
			String nm_sample = "";
			// 20160913 ADD KPX@1600766 End

			try {

				// 20160915 MOD KPX@1600766 Start
				// 20160420 ADD KPX@1600766 Start
//				String errMsg = ""; // エラー原料情報
				StringBuffer errMsg = new StringBuffer(""); // エラー原料情報
				// 20160420 ADD KPX@1600766 End
				// 20160915 MOD KPX@1600766 End

				//2012/08/02 Add
				for(int i = 0 ; i < reqData.getCntRow("tr_shisaku_list") ; i++){

					String strShisakuSeq = reqData.getFieldVale(strTableName, i, "seq_shisaku");
					String strKouteiCd = reqData.getFieldVale(strTableName, i, "cd_kotei");
					String strKouteiSeq = reqData.getFieldVale(strTableName, i, "seq_kotei");

					if( !strSeq_shisaku.equals(strShisakuSeq)){

						intRecIndex = getTableIndex(reqData,"tr_shisaku","seq_shisaku",strShisakuSeq);
						strFlg_shisanIrai = reqData.getFieldVale("tr_shisaku",intRecIndex,"flg_shisanIrai");
						strFlg_cancel = reqData.getFieldVale("tr_shisaku",intRecIndex,"flg_cancel");

					}

					// 20160913 ADD KPX@1600766 Start
					nm_sample = reqData.getFieldVale("tr_shisaku",intRecIndex,"nm_sample");
					// 20160913 ADD KPX@1600766 End

					//※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
					String strRyo = reqData.getFieldVale(strTableName, i, "quantity");
					if ( !(strRyo.isEmpty()) ) {

						//試算依頼済みで且つキャンセルフラグがoffの試作について、配合テーブルの原料チェックを行う。
						if( "1".equals(strFlg_shisanIrai) && !"1".equals(strFlg_cancel)){
							//工程CD,工程SEQより配合テーブルからレコードを取得
							intRecIndex = getTableIndex(reqData,"tr_haigo","cd_kotei",strKouteiCd,"seq_kotei",strKouteiSeq);
							//原料CDが入力されていない時入力エラー
							strCd_genryo = reqData.getFieldVale("tr_haigo", intRecIndex, "cd_genryo");
							if(strCd_genryo == null || strCd_genryo.isEmpty()){
								em.ThrowException(
										ExceptionKind.一般Exception,
										"E000222",
										"","","");
							}
							strNm_genryo = reqData.getFieldVale("tr_haigo",intRecIndex, "nm_genryo");
							if(!"".equals(strNm_genryo)
									 && !"N".equals(strCd_genryo.substring(0,1))
									 && !"n".equals(strCd_genryo.substring(0,1))
								){

								//strCd_genryo = toString(reqData.getFieldVale("tr_haigo",i,"cd_genryo"));
								//strCd_kaisha = toString(reqData.getFieldVale("tr_haigo",i,"cd_kaisha"));
								strCd_genryo = toString(reqData.getFieldVale("tr_haigo",intRecIndex,"cd_genryo"));
								// 20160915 MOD Start 基本情報の製造会社を取得
								//strCd_kaisha = toString(reqData.getFieldVale("tr_haigo",intRecIndex,"cd_kaisha"));
								strCd_kaisha = reqData.getFieldVale("tr_shisakuhin",0,"cd_kaisha");
								// 20160915 MOD End

								strSQL = new StringBuffer();
								strSQL.append("select ");
								strSQL.append("	M401.cd_genryo AS cd_genryo ");
								strSQL.append("FROM ");
								strSQL.append("  ma_genryo M401 ");
								strSQL.append("LEFT JOIN ");
								strSQL.append("  ma_genryokojo M402");
								strSQL.append(" ON M401.cd_genryo = M402.cd_genryo ");
								strSQL.append("WHERE");
								strSQL.append("  M401.cd_genryo = '" + strCd_genryo + "' ");
								strSQL.append("  and M401.cd_kaisha = '" + strCd_kaisha +"' ");

								lstSearchAry = this.searchDB.dbSearch_notError(strSQL.toString());

								//製造会社に属する原料が存在しない場合エラー
								if(lstSearchAry.size() <= 0 ){

									// 20160420 MOD KPX@1600766 Start
//									em.ThrowException(
//											ExceptionKind.一般Exception,
//											"E000223",
//											"","","");
									// 20160915 MOD KPX@1600766 Start
									// エラー原料情報の設定
//									errMsg += System.getProperty("line.separator");
									// 20160913 ADD KPX@1600766 Start
//									errMsg += "サンプルNo：" + nm_sample + "　";
									// 20160913 ADD KPX@1600766 End
//									errMsg += strCd_genryo + "：" + strNm_genryo;
									errMsg.append(System.getProperty("line.separator"));
									errMsg.append(nm_sample + "　" + strCd_genryo + "：" + strNm_genryo);
									// 20160420 ADD KPX@1600766 End
									// 20160915 MOD KPX@1600766 End
								}
							}
						}
					}
				}

				// 20160420 ADD KPX@1600766 Start
				try{
					// 20160915 MOD KPX@1600766 Start
					// 原料エラーがある場合エラーメッセージ表示
//					if(!"".equals(errMsg)) {
					if(!"".equals(errMsg.toString())) {
					// 20160915 MOD KPX@1600766 End
						em.ThrowException(
							ExceptionKind.一般Exception,
							"E000223",
							"","","");
					}
				} catch (ExceptionUser ex) {
					// 20160915 MOD KPX@1600766 Start
					// エラーメッセージ+エラー原料情報を設定
//					ex.setUserMsg(ex.getUserMsg() + errMsg.toString());
					ex.setUserMsg(ex.getUserMsg() + errMsg);
					// 20160915 MOD KPX@1600766 End

					throw ex;
				}
				// 20160420 ADD KPX@1600766 End

			//2012/08/02 Add

				//UPD 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新チェック
				//更新日を取得し、機構新のチェックを行う。
				String strDtKosin = toString(reqData.getFieldVale("tr_shisakuhin", 0, "dt_koshin"));
				if( !"".equals(strDtKosin) ){
					//社員ID
					cd_shain = new BigDecimal(reqData.getFieldVale("tr_shisakuhin", 0, "cd_shain"));
					//年
					nen = toInteger(reqData.getFieldVale("tr_shisakuhin", 0,"nen"));
					//追番
					no_oi = toInteger(reqData.getFieldVale("tr_shisakuhin", 0, "no_oi"));
					//追番
					no_oi = toInteger(reqData.getFieldVale("tr_shisakuhin", 0, "no_oi"));

					//検索SQL生成
					strSQL = new StringBuffer();
					strSQL.append(" select ");
					strSQL.append(" 	CONVERT(VARCHAR,dt_koshin,20) ");
					strSQL.append(" from ");
					strSQL.append(" 	tr_shisakuhin ");
					strSQL.append(" where ");
					strSQL.append(" 	cd_shain = " + cd_shain);
					strSQL.append(" 	AND nen = " + nen);
					strSQL.append(" 	AND no_oi = " + no_oi);
					//DB検索
					lstSearchAry = this.searchDB.dbSearch_notError(strSQL.toString());
					//既に更新済みの時はエラーを出力して登録を中断する。
					if(lstSearchAry.size() > 0 ){
						String strTableDtKoshin = toString(lstSearchAry.get(0),"");
						if( !strDtKosin.equals(strTableDtKoshin) ){
							// 既更新
							em.ThrowException(ExceptionKind.一般Exception, "E000333", "", "", "");
						}
					}
				}


//2009/09/30 TT.A.ISONO ADD START [試作CD追番は登録時に採番するよう変更する。]

				String oiban = reqData.getFieldVale("tr_shisakuhin", 0, "no_oi");

				//試作CD追番の採番を行い、リクエストデータに試作CD追番をセットする。
				//新規登録の場合（追番無し）
				if(oiban == ""){

					this.saiban(reqData);

				//更新の場合（追番有り）
				}else{

				}

//2009/09/30 TT.A.ISONO ADD END   [試作CD追番は登録時に採番するよう変更する。]

				//【QP@00342】今回追加の試作SEQのみ取得
				for(int i = 0; i < reqData.getCntRow("tr_shisaku"); i++){

					if (toString(reqData.getFieldVale("tr_shisaku", i, "flg_shisanIrai")).equals("1")){

						//試作CD-社員ID
						cd_shain = new BigDecimal(reqData.getFieldVale("tr_shisaku", i, "cd_shain"));
						//試作CD-年
						nen = toInteger(reqData.getFieldVale("tr_shisaku", i, "nen"));
						//試作CD-追番
						no_oi = toInteger(reqData.getFieldVale("tr_shisaku", i, "no_oi"));
						//試作SEQ
						seq_shisaku = toInteger(reqData.getFieldVale("tr_shisaku", i, "seq_shisaku"));

						//SQL作成
						strSQL = null;
						//検索SQL生成
						strSQL = new StringBuffer();
						strSQL.append(" select ");
						strSQL.append(" 	cd_shain ");
						strSQL.append(" 	,nen ");
						strSQL.append(" 	,no_oi ");
						strSQL.append(" 	,seq_shisaku ");
						strSQL.append(" from ");
						strSQL.append(" 	tr_shisaku ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + cd_shain);
						strSQL.append(" 	AND nen = " + nen);
						strSQL.append(" 	AND no_oi = " + no_oi);
						strSQL.append(" 	AND seq_shisaku = " + seq_shisaku);
						strSQL.append(" 	AND flg_shisanIrai = 1 ");
						//DB検索
						lstSearchAry = this.searchDB.dbSearch_notError(strSQL.toString());

						//既に依頼済みの試作SEQの場合
						if(lstSearchAry.size() > 0){
							//コピー対象としない

						}
						//新規依頼の試作SEQの場合
						else{

							// ADD 2013/8/2 okano【QP@30151】No.34 start
							String strKoteiPt = reqData.getFieldVale("tr_shisakuhin", 0, "pt_kotei");
							String strChkPrm;
							if(toInteger(strKoteiPt) == 1){
								//製品比重の必須チェック
								strChkPrm = reqData.getFieldVale("tr_shisaku", i, "hiju");

								// チェックパラメーターの必須入力チェックを行う。
								if (strChkPrm.equals(null) || strChkPrm.equals("") || toDouble(strChkPrm) <= 0) {

									// 必須入力不正をスローする。
									em.ThrowException(ExceptionKind.一般Exception, "E000200", "試作データ画面 特性値 製品比重", "", "");
								}
							}
							else if(toInteger(strKoteiPt) == 2){
								//水相比重の必須チェック
								strChkPrm = reqData.getFieldVale("tr_shisaku", i, "hiju_sui");

								// チェックパラメーターの必須入力チェックを行う。
								if (strChkPrm.equals(null) || strChkPrm.equals("") || toDouble(strChkPrm) <= 0) {

									// 必須入力不正をスローする。
									em.ThrowException(ExceptionKind.一般Exception, "E000200", "試作データ画面 特性値 水相比重", "", "");
								}
							}
							// ADD 2013/8/2 okano【QP@30151】No.34 end

							//新規依頼の試作情報格納---------------------------------------------------------------------------
							//諸原収集
							flgCopy = true;

							//試作CD-社員ID
							cd_shain = new BigDecimal(
									reqData.getFieldVale("tr_shisaku", i, "cd_shain"));
							//試作CD-年
							nen = toInteger(
									reqData.getFieldVale("tr_shisaku", i, "nen"));
							//試作CD-追番
							no_oi = toInteger(
									reqData.getFieldVale("tr_shisaku", i, "no_oi"));
							//試作SEQ
							list_seq_shisaku.add(toInteger(
									reqData.getFieldVale("tr_shisaku", i, "seq_shisaku")));
						}
					}
				}

				//新規依頼のサンプルがある場合に、各種チェックを行う
				if(list_seq_shisaku.size() > 0){

					 //工程属性の必須チェック（新規依頼がある場合）
					for(int i = 0; i < reqData.getCntRow("tr_haigo"); i++){

						String strChkPrm = reqData.getFieldVale("tr_haigo", i, "zoku_kotei");

						// チェックパラメーターの必須入力チェックを行う。
						if (strChkPrm.equals(null) || strChkPrm.equals("")) {

							// 必須入力不正をスローする。
							em.ThrowException(ExceptionKind.一般Exception, "E000207", "試作データ画面 配合表 工程属性", "", "");
						}
					}


					strSQL = null;

					//関連会社での試算依頼チェック----------------------------------------------------------------------
					String cd_kaisha = toString(reqData.getFieldVale("tr_shisakuhin", 0, "cd_kaisha"));
//MOD 2013/11/18 shima start
//					String kewpie = ConstManager.getConstValue(Category.設定, "CD_DAIHYO_KAISHA");
//
//					//関連会社でない場合
//					if(cd_kaisha.equals(kewpie)){
//
//					}
					reqData.addFieldVale(0,0,"cd_category","K_daihyogaisha");
					reqData.addFieldVale(0,0,"id_user","");
					reqData.addFieldVale(0,0,"id_gamen","");

					//代表会社の取得
					LiteralSearchLogic literalData = new LiteralSearchLogic();
					RequestResponsKindBean reqDaihyo = literalData.ExecLogic(reqData, _userInfoData);

					List<String> listDaihyo = new ArrayList<String>();
					for(int i = 0; i < reqDaihyo.getCntRow("tr_shisakuhin") ; i ++){
						listDaihyo.add(reqDaihyo.getFieldVale("tr_shisakuhin", i, "nm_literal"));
					}

					//関連会社でない場合
					if(listDaihyo.contains(cd_kaisha)){

					}
// MOD 2013/11/18 shima end
					//関連会社の場合
					else{
						em.ThrowException(ExceptionKind.一般Exception, "E000317", "", "", "");
					}

// ADD 2013/6/28 okano【QP@30151】No.22 start --------------------------------------------
					//試算期日をクリア
					strSQL = null;
					strSQL = new StringBuffer();
					strSQL.append(" UPDATE tr_shisan_shisakuhin ");
					strSQL.append(" set dt_kizitu = NULL ");
					strSQL.append(" where ");
					strSQL.append(" cd_shain = " + cd_shain);
					strSQL.append(" and nen = " + nen);
					strSQL.append(" and no_oi = " + no_oi);
					this.execDB.execSQL(strSQL.toString());
// ADD 2013/6/28 okano【QP@30151】No.22 end ----------------------------------------------

					//排他チェック処理-----------------------------------------------------------------------------------
					//SQL作成
					strSQL = null;
					//検索SQL生成
					strSQL = new StringBuffer();
					strSQL.append(" select ");
					strSQL.append(" 	haita_id_user ");
					strSQL.append(" from ");
					strSQL.append(" 	tr_shisan_shisakuhin ");
					strSQL.append(" where ");
					strSQL.append(" 	cd_shain = " + cd_shain);
					strSQL.append(" 	AND nen = " + nen);
					strSQL.append(" 	AND no_oi = " + no_oi);
					//DB検索
					List<?> lstSearchAry_haita = this.searchDB.dbSearch_notError(strSQL.toString());
					if(lstSearchAry_haita.size() > 0){
						for(int j = 0; j < lstSearchAry_haita.size(); j++){
							Object items_haita = (Object)lstSearchAry_haita.get(j);
							if(toString(items_haita).equals("")){

							}
							else{
								//他ユーザが使用中
								em.ThrowException(ExceptionKind.一般Exception, "E000316", "", "", "");
							}
						}
					}

					//対象試作の営業ステータスが全て4の場合-------------------------------------------------------------
					//SQL作成
					strSQL = null;
					//検索SQL生成
					strSQL = new StringBuffer();
					strSQL.append(" 	select ");
					strSQL.append(" 		st_eigyo ");
					strSQL.append(" 	from ");
					strSQL.append(" 		tr_shisan_status ");
					strSQL.append(" where ");
					strSQL.append(" 	cd_shain = " + cd_shain);
					strSQL.append(" 	AND nen = " + nen);
					strSQL.append(" 	AND no_oi = " + no_oi);
					//DB検索
					List<?> lstSearchAry_st = this.searchDB.dbSearch_notError(strSQL.toString());
					if(lstSearchAry_st.size() > 0){
						int j = 0;
						for(j = 0; j < lstSearchAry_st.size(); j++){
							Object items_st_eigyo = (Object)lstSearchAry_st.get(j);
							if(toString(items_st_eigyo).equals("4")){

							}
							else{
								break;
							}
						}
						if(j == lstSearchAry_st.size()){
							//他ユーザが使用中
							em.ThrowException(ExceptionKind.一般Exception, "E000318", "", "", "");
						}
					}

				}



				//②:T110 試作品テーブル(tr_shisakuhin)の削除・登録処理を行う
				this.shisakuhinKanriDeleteSQL(reqData);
				this.shisakuhinKanriInsertSQL(reqData);
				//③:T120 配合テーブル(tr_haigo)の削除・登録処理を行う
				this.haigoKanriDeleteSQL(reqData);
				this.haigoKanriInsertSQL(reqData);
				//④:T131 試作テーブル(tr_shisaku)の削除・登録処理を行う
				this.shisakuKanriDeleteSQL(reqData);
				this.shisakuKanriInsertSQL(reqData);
				//⑤:T132 試作リストテーブル(tr_shisaku_list)の削除・登録処理を行う
				this.shisakuListKanriDeleteSQL(reqData);
				this.shisakuListKanriInsertSQL(reqData);
				//⑥:T133 製造工程テーブル(tr_cyuui)の削除・登録処理を行う
				this.seizoKouteiKanriDeleteSQL(reqData);
				this.seizoKouteiKanriInsertSQL(reqData);

//				//⑦:T140 原価資材テーブル(tr_shizai)の削除・登録処理を行う(二次分)
//				this.genkaShizaiKanriDeleteSQL();
//				this.genkaShizaiKanriInsertSQL();
				//⑧:T141 原価原料テーブル(tr_genryo)の削除・登録処理を行う(二次分)
				this.genkaGenryoKanriDeleteSQL(reqData);
				this.genkaGenryoKanriInsertSQL(reqData);

				//⑨:コミット/ロールバック処理を実行する
				//コミット　※トランザクションはsearchDBで開始（BeginTran）しているので、コミットもsearchDBで行います。
				this.searchDB.Commit();

			} catch(Exception e) {
				//ロールバック　※トランザクションはsearchDBで開始（BeginTran）しているので、ロールバックもsearchDBで行います。
				this.searchDB.Rollback();
				this.em.ThrowException(e, "");

			} finally {
			}

//2009/10/23 TT.A.ISONO ADD START [原価試算：依頼データを原価試算ＤＢにCopyする。]

//			boolean flgCopy = false;
//			BigDecimal  cd_shain = new BigDecimal("0");
//			int     nen = 0;
//			int     no_oi = 0;
//			ArrayList<Integer> list_seq_shisaku = new ArrayList();
//
//			try{
//
//				//実施判定
//				try{
//					for(int i = 0; i < reqData.getCntRow("tr_shisaku"); i++){
//
//						if (toString(reqData.getFieldVale("tr_shisaku", i, "flg_shisanIrai")).equals("1")){
//
//							//諸原収集
//							flgCopy = true;
//
//							//試作CD-社員ID
//							cd_shain = new BigDecimal(
//									reqData.getFieldVale("tr_shisaku", i, "cd_shain"));
//							//試作CD-年
//							nen = toInteger(
//									reqData.getFieldVale("tr_shisaku", i, "nen"));
//							//試作CD-追番
//							no_oi = toInteger(
//									reqData.getFieldVale("tr_shisaku", i, "no_oi"));
//							//試作SEQ
//							list_seq_shisaku.add(toInteger(
//									reqData.getFieldVale("tr_shisaku", i, "seq_shisaku")));
//
//						}
//
//					}
//
//				}catch(Exception e){
//
//				}

			if (flgCopy){

				//原価依頼クラス
				//【QP@00342】引数に枝番追加（元版なので0）
				CGEN2010_Logic clsCGEN2010_Logic = new CGEN2010_Logic();
				clsCGEN2010_Logic.ExecLogic(
						cd_shain
						, nen
						, no_oi
						,0
						, list_seq_shisaku
						, userInfoData
						);
			}

//			}catch(Exception e){
//
//			}finally{
//				//ローカル変数の開放
//				removeList(list_seq_shisaku);
//
//			}

//2009/10/23 TT.A.ISONO ADD END   [原価試算：依頼データを原価試算ＤＢにCopyする。]


//2011/04/12 QP@99999_No.67 TT Nishigawa Change Start -------------------------
			//SQL文格納用
			strSQL = new StringBuffer();

			//キャンセル実行FG
			boolean exec_cancel = false;

			//検索結果格納用
			lstSearchAry = null;

			//トランザクション開始
			execDB.BeginTran();

			try{

				//試作列データループ
				for(int i = 0; i < reqData.getCntRow("tr_shisaku"); i++){

					//キャンセル依頼があった場合
					if (toString(reqData.getFieldVale("tr_shisaku", i, "flg_cancel")).equals("1")){

						//試作CD-社員ID
						String cd_shain_cancel = toString(reqData.getFieldVale("tr_shisaku", i, "cd_shain"));
						//試作CD-年
						String nen_cancel = toString(reqData.getFieldVale("tr_shisaku", i, "nen"));
						//試作CD-追番
						String no_oi_cancel = toString(reqData.getFieldVale("tr_shisaku", i, "no_oi"));
						//試作SEQ
						String seq_shisaku_cancel = toString(reqData.getFieldVale("tr_shisaku", i, "seq_shisaku"));

						// ADD 2013/7/2 shima【QP@30151】No.37 start
						//実行SQL文（原価試算のサンプル列削除）
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_kihonjoho ");
						strSQL.append(" WHERE ");
						strSQL.append(" 	cd_shain = " + cd_shain_cancel);
						strSQL.append(" 	and nen = " + nen_cancel);
						strSQL.append(" 	and no_oi =  " + no_oi_cancel);
						strSQL.append(" 	and seq_shisaku = " + seq_shisaku_cancel);

						//SQL実行
						this.execDB.execSQL(strSQL.toString());
						// ADD 2013/7/2 shima【QP@30151】No.37 end

						//実行SQL文（原価試算のサンプル列削除）
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_shisaku ");
						strSQL.append(" WHERE ");
						strSQL.append(" 	cd_shain = " + cd_shain_cancel);
						strSQL.append(" 	and nen = " + nen_cancel);
						strSQL.append(" 	and no_oi =  " + no_oi_cancel);
						strSQL.append(" 	and seq_shisaku = " + seq_shisaku_cancel);

						//SQL実行
						this.execDB.execSQL(strSQL.toString());

						//実行SQL文（試作データの依頼FG解除）
						strSQL = new StringBuffer();
						strSQL.append(" UPDATE tr_shisaku ");
						strSQL.append("    SET  ");
						strSQL.append(" 	   [flg_shisanIrai] = NULL ");
						strSQL.append("  WHERE ");
						strSQL.append(" 	cd_shain = " + cd_shain_cancel);
						strSQL.append(" 	and nen =  " + nen_cancel);
						strSQL.append(" 	and no_oi =  " + no_oi_cancel);
						strSQL.append(" 	and seq_shisaku = " + seq_shisaku_cancel);

						//SQL実行
						this.execDB.execSQL(strSQL.toString());

						//実行フラグ設定
						exec_cancel = true;
					}
				}

				//キャンセル実行時
				if(exec_cancel){

					//キー項目取得
					String strReqShainCd = reqData.getFieldVale("tr_shisakuhin", 0, "cd_shain");
					String strReqNen = reqData.getFieldVale("tr_shisakuhin", 0, "nen");
					String strReqOiNo = reqData.getFieldVale("tr_shisakuhin", 0, "no_oi");

					//全列削除チェックSQL文
					strSQL = new StringBuffer();
					strSQL.append(" select ");
					strSQL.append(" 	cd_shain ");
					strSQL.append(" 	,nen ");
					strSQL.append(" 	,no_oi ");
					strSQL.append(" 	,no_eda ");
					strSQL.append(" 	,seq_shisaku ");
					strSQL.append(" from ");
					strSQL.append(" 	tr_shisan_shisaku ");
					strSQL.append(" where ");
					strSQL.append(" 	cd_shain = " + strReqShainCd);
					strSQL.append(" 	and nen = " + strReqNen);
					strSQL.append(" 	and no_oi = " + strReqOiNo);

					//検索実行
					lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

					//全列削除の場合
					if(lstSearchAry.size() == 0){

						//原価試算データ削除（試算_試作品）
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_shisakuhin ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//原価試算データ削除（試算_メモ）
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_memo ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//原価試算データ削除（試算_営業メモ）
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_memo_eigyo ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//原価試算データ削除（試算_配合）
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_haigo ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//原価試算データ削除（試算_資材）
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_shizai ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//原価試算データ削除（試算_ステータス）
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_status ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//原価試算データ削除（試算_ステータス履歴）（元版以外）
						strSQL = new StringBuffer();
						strSQL.append(" DELETE FROM tr_shisan_status_rireki ");
						strSQL.append(" where ");
						strSQL.append(" 	cd_shain = " + strReqShainCd);
						strSQL.append(" 	and nen = " + strReqNen);
						strSQL.append(" 	and no_oi = " + strReqOiNo);
						strSQL.append(" 	and no_eda <> 0");
						this.execDB.execSQL(strSQL.toString());

						//ステータス履歴テーブル更新
						strSQL = new StringBuffer();
						strSQL.append(" INSERT INTO tr_shisan_status_rireki ");
						strSQL.append("            (cd_shain ");
						strSQL.append("            ,nen ");
						strSQL.append("            ,no_oi ");
						strSQL.append("            ,no_eda ");
						strSQL.append("            ,dt_henkou ");
						strSQL.append("            ,cd_kaisha ");
						strSQL.append("            ,cd_busho ");
						strSQL.append("            ,id_henkou ");
						strSQL.append("            ,cd_zikko_ca ");
						strSQL.append("            ,cd_zikko_li ");
						strSQL.append("            ,st_kenkyu ");
						strSQL.append("            ,st_seisan ");
						strSQL.append("            ,st_gensizai ");
						strSQL.append("            ,st_kojo ");
						strSQL.append("            ,st_eigyo ");
						strSQL.append("            ,id_toroku ");
						strSQL.append("            ,dt_toroku) ");
						strSQL.append("      VALUES ");
						strSQL.append("            (" + cd_shain + " ");
						strSQL.append("            ," + nen + " ");
						strSQL.append("            ," + no_oi + " ");
						strSQL.append("            ," + 0 +" ");
						strSQL.append("            ,GETDATE() ");
						strSQL.append("            ," + userInfoData.getCd_kaisha() + " ");
						strSQL.append("            ," + userInfoData.getCd_busho() + " ");
						strSQL.append("            ," + userInfoData.getId_user() + " ");
						strSQL.append("            ,'wk_kenkyu'");
						strSQL.append("            ,'2' ");
						strSQL.append("            ,2 ");
						strSQL.append("            ,1 ");
						strSQL.append("            ,1 ");
						strSQL.append("            ,1 ");
						strSQL.append("            ,1 ");
						strSQL.append("            ," + userInfoData.getId_user() + " ");
						strSQL.append("            ,GETDATE() )");
						this.execDB.execSQL(strSQL.toString());

					}
					//指定列削除の場合
					else{

						//ステータステーブル更新
						strSQL = new StringBuffer();
						strSQL.append(" UPDATE tr_shisan_status ");
						strSQL.append("    SET st_kenkyu = 2 ");
						strSQL.append("       ,st_seisan = 1 ");
						strSQL.append("       ,st_gensizai = 1 ");
						strSQL.append("       ,st_kojo = 1 ");
						strSQL.append("       ,st_eigyo = 1 ");
						strSQL.append("       ,id_koshin = " + userInfoData.getId_user() + " ");
						strSQL.append("       ,dt_koshin = GETDATE() ");
						strSQL.append("  WHERE ");
						strSQL.append("    cd_shain = " + strReqShainCd);
						strSQL.append("    and nen = " + strReqNen);
						strSQL.append("    and no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());

						//ステータス履歴テーブル更新
						strSQL = new StringBuffer();
						strSQL.append(" INSERT INTO tr_shisan_status_rireki ");
						strSQL.append("  SELECT ");
						strSQL.append("    A.cd_shain ");
						strSQL.append("   ,A.nen ");
						strSQL.append("   ,A.no_oi ");
						strSQL.append("   ,A.no_eda ");
						strSQL.append("   ,GETDATE() ");
						strSQL.append("   ," + userInfoData.getCd_kaisha() + " ");
						strSQL.append("   ," + userInfoData.getCd_busho() + " ");
						strSQL.append("   ," + userInfoData.getId_user() + " ");
						strSQL.append("   ,'wk_kenkyu' ");
						strSQL.append("   ,'3' ");
						strSQL.append("   ,2 ");
						strSQL.append("   ,1 ");
						strSQL.append("   ,1 ");
						strSQL.append("   ,1 ");
						strSQL.append("   ,1 ");
						strSQL.append("   ," + userInfoData.getId_user() + " ");
						strSQL.append("   ,GETDATE() ");
						strSQL.append("  FROM ");
						strSQL.append("   tr_shisan_shisakuhin AS A ");
						strSQL.append("   INNER JOIN ma_user AS B ");
						strSQL.append("   ON A.id_toroku = B.id_user ");
						strSQL.append("  WHERE ");
						strSQL.append("   A.cd_shain = " + strReqShainCd);
						strSQL.append("   and A.nen = " + strReqNen);
						strSQL.append("   and A.no_oi = " + strReqOiNo);
						this.execDB.execSQL(strSQL.toString());
					}

					//コミット　※トランザクションはsearchDBで開始（BeginTran）しているので、コミットもsearchDBで行います。
					this.execDB.Commit();
				}

			}
			catch(Exception e) {
				//ロールバック　※トランザクションはsearchDBで開始（BeginTran）しているので、ロールバックもsearchDBで行います。
				this.execDB.Rollback();
				this.em.ThrowException(e, "");

			}
			finally{
				lstSearchAry = null;

			}



//2011/04/12 QP@99999_No.67 TT Nishigawa Change Start -------------------------

			//機能IDの設定
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//テーブル名の設定
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//⑩：正常終了時、管理結果パラメーター格納メソッドを呼出し、レスポンスデータを形成する。
			this.storageKengenDataKanri(resKind.getTableItem(strTableNm));

//2009/09/30 TT.A.ISONO ADD START [試作CD追番は登録時に採番するよう変更する。]
//採番した試作CD追番をレスポンスに追加
			resKind.addFieldVale(strTableNm, 0, "new_code",
					reqData.getFieldVale("tr_shisakuhin", 0, "cd_shain") + "-" +
					reqData.getFieldVale("tr_shisakuhin", 0, "nen") + "-" +
					reqData.getFieldVale("tr_shisakuhin", 0, "no_oi")
					);

//2009/09/30 TT.A.ISONO ADD END   [試作CD追番は登録時に採番するよう変更する。]


		} catch (Exception e) {
			this.em.ThrowException(e, "試作テーブル管理操作処理が失敗しました。");

		} finally {
			if (execDB != null) {
				execDB.Close();				//セッションのクローズ
				execDB = null;

			}

		}
		return resKind;

	}

//2009/09/30 TT.A.ISONO ADD START [試作CD追番は登録時に採番するよう変更する。]

	/**
	 * 試作CD追番の採番を行い、リクエストデータに試作CD追番をセットする。
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void saiban(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ローカル変数
		//SQL
		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL2 = new StringBuffer();
		//検索結果の格納
		List<?> lstSearchAry = null;
		//新規、試作ｺｰﾄﾞ追番の格納
		String strRetNewCode = "";

		try {

			//★試作CD追番の採番の実施

			//ユーザ情報.ユーザID
			String strUserId = userInfoData.getId_user();

			//下記条件を用いて、試作採番マスタ（ma_shisakusaiban）より、新規発行試作コード検索用SQLを作成する。
			//試作採番マスタ.試作CD-社員コード =  ユーザ情報.ユーザID
			//試作採番マスタ.試作CD-年 = システム.年の下二桁
			strSQL.append(" SELECT  ");
			strSQL.append("   RIGHT(YEAR(GETDATE()),2) AS nen ");
			strSQL.append("  ,ISNULL(MAX(M602.no_oi)+1,1) AS no_oi ");
			strSQL.append(" FROM ma_shisaku_saiban M602 ");
			strSQL.append(" WHERE M602.cd_shain =" + strUserId);
			strSQL.append("   AND M602.nen = RIGHT(YEAR(GETDATE()),2) ");

			//作成したSQLを用いて、検索処理を行い、新規発行コードを取得する。
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

			//新規発行コード　：　"ユーザ情報.ユーザID" + "-" + "システム.年数の下二桁" + "-" + "試作採番マスタ.追番の最大値+1"
			if ( lstSearchAry.size() >= 0 ) {
				Object[] items = (Object[]) lstSearchAry.get(0);
				//新規、試作ｺｰﾄﾞ追番の退避
				strRetNewCode = items[1].toString();

			}

			//★採番結果を試作採番マスタ（ma_shisakusaiban）に反映

			//②:①の追番の値により、処理を振り分ける
			if ( strRetNewCode.equals("1") ) {
				//③：試作採番マスタ（ma_shisakusaiban）の登録処理を行う

				strSQL2.append(" INSERT INTO ma_shisaku_saiban ");
				strSQL2.append("  (cd_shain, nen, no_oi, id_toroku, dt_toroku, id_koshin, dt_koshin) ");
				strSQL2.append("  VALUES ( ");
				strSQL2.append(" " + strUserId);
				strSQL2.append(" ,RIGHT(YEAR(GETDATE()),2)");
				strSQL2.append(" ," + strRetNewCode);
				strSQL2.append("  ," + strUserId + " ,GETDATE() ");		//更新者
				strSQL2.append("  ," + strUserId + " ,GETDATE() )");	//登録者

			} else {
				//④：試作採番マスタ（ma_shisakusaiban）.追番の更新処理を行う

				strSQL2.append(" UPDATE ma_shisaku_saiban ");
				strSQL2.append("   SET no_oi =" + strRetNewCode);
				strSQL2.append("      ,id_koshin =" + strUserId);
				strSQL2.append("      ,dt_koshin = GETDATE() ");
				strSQL2.append(" WHERE cd_shain =" + strUserId);
				strSQL2.append("   AND nen =RIGHT(YEAR(GETDATE()),2)");

			}

			this.execDB.execSQL(strSQL2.toString());		//SQL実行

			//★リクエストデータに試作CD追番を反映する。

			//T110 試作品テーブル(tr_shisakuhin)
			for ( int i=0; i<reqData.getCntRow("tr_shisakuhin"); i++ ) {
				reqData.setFieldVale("tr_shisakuhin", i, "no_oi", strRetNewCode);
			}
			//T120 配合テーブル(tr_haigo)
			for ( int i=0; i<reqData.getCntRow("tr_haigo"); i++ ) {
				reqData.setFieldVale("tr_haigo", i, "no_oi", strRetNewCode);
			}
			//T131 試作テーブル(tr_shisaku)
			for ( int i=0; i<reqData.getCntRow("tr_shisaku"); i++ ) {
				reqData.setFieldVale("tr_shisaku", i, "no_oi", strRetNewCode);
			}
			//T132 試作リストテーブル(tr_shisaku_list)
			for ( int i=0; i<reqData.getCntRow("tr_shisaku_list"); i++ ) {
				reqData.setFieldVale("tr_shisaku_list", i, "no_oi", strRetNewCode);
			}
			//T133 製造工程テーブル(tr_cyuui)
				for ( int i=0; i<reqData.getCntRow("tr_cyuui"); i++ ) {
					reqData.setFieldVale("tr_cyuui", i, "no_oi", strRetNewCode);
				}
			//T141 原価原料テーブル(tr_genryo)
			for ( int i=0; i<reqData.getCntRow("tr_genryo"); i++ ) {
				reqData.setFieldVale("tr_genryo", i, "no_oi", strRetNewCode);
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "試作コードの新規採番に失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;
			strSQL2 = null;

		}

	}

//2009/09/30 TT.A.ISONO ADD END   [試作CD追番は登録時に採番するよう変更する。]

	/**
	 * 試作品データ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuhinKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//①：リクエストデータを用い、試作品データ削除用SQLを作成する。

			//リクエストデータ取得
			String strReqShainCd = reqData.getFieldVale("tr_shisakuhin", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisakuhin", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisakuhin", 0, "no_oi");

			//削除用SQL作成
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisakuhin ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");

			//②：共通クラス　データベース管理を用い、試作品データの削除を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "試作品データ削除SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

		}
	}
	/**
	 * 試作品データ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuhinKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//①：リクエストデータを用い、試作品データ登録用SQLを作成する。
			for ( int i=0; i<reqData.getCntRow("tr_shisakuhin"); i++ ) {

				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");

				}
				strSQL_values.append(" SELECT ");

				//値をSQLに設定していく
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisakuhin", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "no_oi") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "no_irai") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "nm_hin") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_kaisha")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_kojo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_shubetu") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "no_shubetu")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_group")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_team")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_ikatu") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_genre") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_user") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "tokuchogenryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "youto") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_kakaku") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_eigyo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_hoho") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_juten") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "hoho_sakin") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "youki") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "yoryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_tani") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "su_iri") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_ondo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "shomikikan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "genka") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "baika") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "buturyo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_hatubai") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "uriage_k") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "rieki_k") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "uriage_h") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "rieki_h") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_nisugata") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "memo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "keta_shosu")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "kbn_haishi")) );
//2009/09/30 TT.A.ISONO ADD START [試作CD追番は登録時に採番するよう変更する。]
//試作の排他ロックは、試作データ登録時に掛けるに変更
//				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "kbn_haita")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "id_koshin")) );
//2009/09/30 TT.A.ISONO ADD END   [試作CD追番は登録時に採番するよう変更する。]
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "seq_shisaku")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "memo_shisaku") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "flg_chui")) );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_toroku") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "id_koshin") );
				//UPD 2012.4.30　【H24年度対応】更新日で試作品の更新排他制御を行うため更新
//				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_koshin") + "'" );
				strSQL_values.append(" ,CONVERT (DATETIME, GETDATE(),20)");
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
				strSQL_values.append(" ,'" + toString(reqData.getFieldVale("tr_shisakuhin", i, "pt_kotei")) + "'");
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------
				//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "flg_secret")) );
				//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
				// ADD 2013/10/22 QP@30154 okano start
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_hanseki")) );
				// ADD 2013/10/22 QP@30154 okano end
			}

			//登録用SQL作成
			strSQL.append(" INSERT INTO tr_shisakuhin ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");

			//②：共通クラス　データベース管理を用い、試作品データの登録を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "試作品データ登録SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;

		}

	}

	/**
	 * 配合データ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void haigoKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//①：リクエストデータを用い、配合データ削除用SQLを作成する。

			//リクエストデータ取得
			String strReqShainCd = reqData.getFieldVale("tr_haigo", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_haigo", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_haigo", 0, "no_oi");

			//削除用SQL作成
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_haigo ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");

			//②：共通クラス　データベース管理を用い、配合データの削除を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "配合データ削除SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

		}
	}
	/**
	 * 配合データ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void haigoKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//①：リクエストデータを用い、配合データ登録用SQLを作成する。
			for ( int i=0; i<reqData.getCntRow("tr_haigo"); i++ ) {

				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");

				}
				strSQL_values.append(" SELECT ");

				//値をSQLに設定していく
				strSQL_values.append(" " + reqData.getFieldVale("tr_haigo", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "cd_kotei") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "seq_kotei") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "nm_kotei") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "zoku_kotei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "sort_kotei")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "sort_genryo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "cd_genryo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "cd_kaisha")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "cd_busho")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "nm_genryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "tanka") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "budomari") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_abura") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_sakusan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_shokuen") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_sousan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "color") + "'") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_haigo", i, "dt_toroku") + "'" );
//				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "cd_shain") );
//				strSQL_values.append(" ,GETDATE()" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "id_koshin") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_haigo", i, "dt_koshin") + "'" );
// ADD start 20121003 QP@20505 No.24
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_msg") + "'") );
// ADD end 20121003 QP@20505 No.24

			}

			//登録用SQL作成
			strSQL.append(" INSERT INTO tr_haigo ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");

			//②：共通クラス　データベース管理を用い、配合データの登録を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "配合データ登録SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;

		}

	}

	/**
	 * 試作データ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//①：リクエストデータを用い、試作データ削除用SQLを作成する。

			//リクエストデータ取得
			String strReqShainCd = reqData.getFieldVale("tr_shisaku", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisaku", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisaku", 0, "no_oi");

			//削除用SQL作成
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisaku ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");

			//②：共通クラス　データベース管理を用い、試作データの削除を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ削除SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

		}
	}
	/**
	 * 試作データ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//①：リクエストデータを用い、試作データ登録用SQLを作成する。
			for ( int i=0; i<reqData.getCntRow("tr_shisaku"); i++ ) {

				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");

				}
				strSQL_values.append(" SELECT ");

				//値をSQLに設定していく
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisaku", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "seq_shisaku") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sort_shisaku")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "no_chui")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "nm_sample") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo") + "'") );
				//strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo_shisaku") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_print")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_auto")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "no_shisan")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho1") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho2") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho3") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho4") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho5") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_sousan")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sousan")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_shokuen")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sando_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sando_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "shokuen_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sakusan_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sakusan_suiso")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "toudo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_toudo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "nendo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_nendo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "ondo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_ondo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "ph") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_ph")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "ritu_sousan_bunseki") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sousan_bunseki")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "ritu_shokuen_bunseki") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen_bunseki")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "hiju") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_hiju")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "suibun_kasei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_suibun_kasei")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "alcohol") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_alcohol")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo_sakusei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_memo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "hyoka") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_hyoka")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title1") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value1") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free1")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title2") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value2") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free2")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title3") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value3") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free3")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "dt_shisaku") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "juryo_shiagari_g")));
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku", i, "dt_toroku") + "'" );
//				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "cd_shain") );
//				strSQL_values.append(" ,GETDATE()" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "id_koshin") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku", i, "dt_koshin") + "'" );
//2009/10/20 TT.A.ISONO ADD START [原価試算：試算依頼ｆｌｇを追加]
				try{
					strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku", i, "flg_shisanIrai") + "'" );
				}catch(Exception e){

				}
//2009/10/20 TT.A.ISONO ADD END   [原価試算：試算依頼ｆｌｇを追加]
//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
				strSQL_values.append(" ,'" + toString(reqData.getFieldVale("tr_shisaku", i, "siki_keisan"),"") + "'");
//add end   -------------------------------------------------------------------------------
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add Start -------------------------
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "hiju_sui") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_hiju_sui")) );
//2011/05/11 QP@10181_No.42_49_72 TT Nishigawa Add End --------------------------
// ADD start 20121003 QP@20505 No.24
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_msg")) );
				strSQL_values.append(" ,0"); // flg_msg（現時点では画面表示していないため）
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "freetitle_nendo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_nendo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_freeNendo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "freetitle_ondo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_ondo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_freeOndo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "freetitle_suibun_kasei") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_suibun_kasei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_freeSuibunKasei")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "freetitle_alcohol") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_alcohol") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_freeAlchol")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "jikkoSakusanNodo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_jikkoSakusanNodo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "msg_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_msg_suiso")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title4") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value4") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free4")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title5") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value5") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free5")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title6") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value6") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free6")) );
// ADD end 20121003 QP@20505 No.24
			}

			//登録用SQL作成
			strSQL.append(" INSERT INTO tr_shisaku ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");

			//②：共通クラス　データベース管理を用い、試作データの登録を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "試作データ登録SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;

		}

	}

	/**
	 * 試作リストデータ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuListKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//①：リクエストデータを用い、試作リストデータ削除用SQLを作成する。

			//リクエストデータ取得
			String strReqShainCd = reqData.getFieldVale("tr_shisaku_list", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisaku_list", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisaku_list", 0, "no_oi");

			//削除用SQL作成
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisaku_list ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");

			//②：共通クラス　データベース管理を用い、試作リストデータの削除を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "試作リストデータ削除SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

		}

	}
	/**
	 * 試作リストデータ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuListKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//①：リクエストデータを用い、試作リストデータ登録用SQLを作成する。
			for ( int i=0; i<reqData.getCntRow("tr_shisaku_list"); i++ ) {
				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");
				}
				strSQL_values.append(" SELECT ");

				//値をSQLに設定していく
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisaku_list", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "seq_shisaku") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "cd_kotei") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "seq_kotei") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku_list", i, "quantity")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku_list", i, "color") + "'") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku_list", i, "dt_toroku") + "'" );
//				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "cd_shain") );
//				strSQL_values.append(" ,GETDATE()" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "id_koshin") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku_list", i, "dt_koshin") + "'" );
// ADD start 20120914 QP@20505 No.1
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku_list", i, "juryo_shiagari_seq")) );
// ADD end 20120914 QP@20505 No.1

			}

			//登録用SQL作成
			strSQL.append(" INSERT INTO tr_shisaku_list ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");

			//②：共通クラス　データベース管理を用い、試作リストデータの登録を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "試作リストデータ登録SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;

		}

	}

	/**
	 * 製造工程データ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void seizoKouteiKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();

		try {

			//製造工程のテーブル件数が0件ではない場合、処理を続行する
			if ( reqData.getCntRow("tr_cyuui") != 0 ) {

				//①：リクエストデータを用い、製造工程データ削除用SQLを作成する。

				//リクエストデータ取得
				String strReqShainCd = reqData.getFieldVale("tr_cyuui", 0, "cd_shain");
				String strReqNen = reqData.getFieldVale("tr_cyuui", 0, "nen");
				String strReqOiNo = reqData.getFieldVale("tr_cyuui", 0, "no_oi");

				//削除用SQL作成
				strSQL.append(" DELETE ");
				strSQL.append("  FROM tr_cyuui ");
				strSQL.append("  WHERE ");
				strSQL.append("   cd_shain= " + strReqShainCd);
				strSQL.append("   AND nen= " + strReqNen);
				strSQL.append("   AND no_oi= " + strReqOiNo);
				strSQL.append("  ");

				//②：共通クラス　データベース管理を用い、製造工程データの削除を行う。
				this.execDB.execSQL(strSQL.toString());

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "製造工程データ削除SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;

		}

	}
	/**
	 * 製造工程データ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void seizoKouteiKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//製造工程のテーブル件数が0件ではない場合、処理を続行する
			if ( reqData.getCntRow("tr_cyuui") != 0 ) {

				//①：リクエストデータを用い、製造工程データ登録用SQLを作成する。
				for ( int i=0; i<reqData.getCntRow("tr_cyuui"); i++ ) {
					if ( strSQL_values.length() != 0 ) {
						strSQL_values.append(" UNION ALL ");
					}
					strSQL_values.append(" SELECT ");

					//値をSQLに設定していく
					strSQL_values.append(" " + reqData.getFieldVale("tr_cyuui", i, "cd_shain") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "nen") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "no_oi") );
					//strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "seq_shisaku") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "no_chui") );
					strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_cyuui", i, "chuijiko") + "'") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "id_toroku") );
					strSQL_values.append(" ,'" + reqData.getFieldVale("tr_cyuui", i, "dt_toroku") + "'" );
//					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "cd_shain") );
//					strSQL_values.append(" ,GETDATE()" );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "id_koshin") );
					strSQL_values.append(" ,'" + reqData.getFieldVale("tr_cyuui", i, "dt_koshin") + "'" );

				}

				//登録用SQL作成
				strSQL.append(" INSERT INTO tr_cyuui ");
				strSQL.append(strSQL_values.toString());
				strSQL.append("");

				//②：共通クラス　データベース管理を用い、製造工程データの登録を行う。
				this.execDB.execSQL(strSQL.toString());

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "製造工程データ登録SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;

		}

	}

//	/**
//	 * 原価資材データ削除SQL作成
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaShizaiKanriDeleteSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//
//			//①：リクエストデータを用い、原価資材データ削除用SQLを作成する。
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//
//			//②：共通クラス　データベース管理を用い、原価資材データの削除を行う。
//			this.execDB.execSQL(strSQL.toString());
//
//		} catch (Exception e) {
//			this.em.ThrowException(e, "原価資材データ削除SQL作成処理が失敗しました。");
//		} finally {
//		}
//	}
//	/**
//	 * 原価資材データ登録SQL作成
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaShizaiKanriInsertSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//
//			//①：リクエストデータを用い、原価資材データ登録用SQLを作成する。
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//
//			//②：共通クラス　データベース管理を用い、原価資材データの登録を行う。
//			this.execDB.execSQL(strSQL.toString());
//
//		} catch (Exception e) {
//			this.em.ThrowException(e, "原価資材データ登録SQL作成処理が失敗しました。");
//		} finally {
//		}
//	}

	/**
	 * 原価原料データ削除SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void genkaGenryoKanriDeleteSQL(RequestResponsKindBean reqData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			StringBuffer strSQL = new StringBuffer();

			//①：リクエストデータを用い、原価原料データ削除用SQLを作成する。

			//リクエストデータ取得
			String strReqShainCd = reqData.getFieldVale("tr_genryo", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_genryo", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_genryo", 0, "no_oi");

			//削除用SQL作成
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_genryo ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");

			//②：共通クラス　データベース管理を用い、原価原料データの削除を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "原価原料データ削除SQL作成処理が失敗しました。");

		} finally {

		}

	}
	/**
	 * 原価原料データ登録SQL作成
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void genkaGenryoKanriInsertSQL(RequestResponsKindBean reqData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//①：リクエストデータを用い、試作リストデータ登録用SQLを作成する。
			for ( int i=0; i<reqData.getCntRow("tr_genryo"); i++ ) {

				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");

				}

				strSQL_values.append(" SELECT ");

				//値をSQLに設定していく
				strSQL_values.append(" " + reqData.getFieldVale("tr_genryo", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "seq_shisaku") );
				//印刷Fg
				if ( !toString(reqData.getFieldVale("tr_genryo", i, "flg_print")).isEmpty() ) {
					strSQL_values.append(" ," +  reqData.getFieldVale("tr_genryo", i, "flg_print"));

				} else {
					strSQL_values.append(" ,0 ");

				}
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "zyusui") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "zyuabura") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "gokei") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "genryohi") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "genryohi1") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "hiju") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "yoryo") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "irisu") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "yukobudomari") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "reberu") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "hizyubudomari") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "heikinzyu") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "cs_genryo") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "cs_zairyohi") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "cs_keihi") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "cs_genka") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "ko_genka") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "ko_baika") + "'" );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "ko_riritu") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "dt_toroku") + "'" );
//				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "cd_shain") );
//				strSQL_values.append(" ,GETDATE()" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_genryo", i, "id_koshin") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_genryo", i, "dt_koshin") + "'" );

			}

			//登録用SQL作成
			strSQL.append(" INSERT INTO tr_genryo ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");

			//②：共通クラス　データベース管理を用い、試作リストデータの登録を行う。
			this.execDB.execSQL(strSQL.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "原価原料データ登録SQL作成処理が失敗しました。");

		} finally {
			//変数の削除
			strSQL = null;
			strSQL_values = null;

		}

	}

	/**
	 * 管理結果パラメーター格納
	 *  : 試作テーブルデータの管理結果情報をレスポンスデータへ格納する。
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageKengenDataKanri(RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//①：レスポンスデータを形成する。

			//処理結果の格納
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

		} catch (Exception e) {
			this.em.ThrowException(e, "管理結果パラメーター格納処理が失敗しました。");

		} finally {

		}

	}

	/**
	 * Nullチェック
	 * @param strValue ： チェック値
	 * @return 結果(値が空の場合、NULLを返す)
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private String checkNull(String strChkValue) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetValue = strChkValue;

		try {
			if ( strRetValue.equals("") ) {
				strRetValue = "NULL";
			} else if ( strRetValue.equals("''")) {
				strRetValue = "NULL";
			}
		} catch(Exception e) {
			this.em.ThrowException(e, "Nullチェック処理が失敗しました。");
		} finally {
		}

		return strRetValue;
	}

	//ADD 2012/08/02
	/**
	 * 指定テーブルを指定キーの指定データでサーチし、indexをリターンする。
	 * @param checkData : リクエストデータ
	 * @param strTableNm_sub : テーブル名
	 * @param strSearchKey : サーチ項目...
	 * @param strSearchData : サーチデータ(null以外)...
	 * @return テーブルのindex
	 * @throws ExceptionSystem
	 * @throws ExceptionUser	見つからない場合発生。
	 * @throws ExceptionWaning
	 */
	private int getTableIndex(RequestResponsKindBean checkData,String strTableNm_sub,String... strKeyData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

	    	//データサーチ
			int intParaCounter = 0;
			for ( int j=0; j<checkData.getCntRow( strTableNm_sub); j++ ) {

				for ( intParaCounter=0; intParaCounter<strKeyData.length; intParaCounter=intParaCounter+2){
					if( strKeyData[intParaCounter+1] == null){
						break;
					}else if ( !strKeyData[intParaCounter+1].equals(checkData.getFieldVale(strTableNm_sub, j, strKeyData[intParaCounter]))){
						break;
					}
				}
				if( intParaCounter >= strKeyData.length){
					return j;
				}

			}
			//インターフェース上見つからないことはないはず
			em.ThrowException(
	    			ExceptionKind.一般Exception,
	    			"E000224",
	    			strTableNm_sub,StringUtils.arrayToCommaDelimitedString(strKeyData),"");

		} catch (Exception e) {
			em.ThrowException(e, "");

		}
		return 0;
	}

}
