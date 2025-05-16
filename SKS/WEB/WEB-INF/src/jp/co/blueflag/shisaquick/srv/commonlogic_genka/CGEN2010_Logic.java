package jp.co.blueflag.shisaquick.srv.commonlogic_genka;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 *
 * 原価試算依頼データを試算dbにコピーし会社/工場の洗い替えを行う
 *  : 試作毎の洗い替え
 * @author isono
 * @since  2009/10/22
 *
 */
public class CGEN2010_Logic extends LogicBase {

	//クラスメンバ変数

	//洗い替え用　会社CD
	int cls_cd_kaisha = 0;
	//洗い替え用　工場CD
	int cls_cd_kojo = 0;

	/**
	 * コンストラクタ
	 */
	public CGEN2010_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 原価試算依頼データのcopyの実装
	 * ：原価試算用copy
	 * 　会社/工場洗い替え
	 * @param cd_shain	：試作CD　社員ID
	 * @param nen		：試作CD	年
	 * @param no_oi		：試作CD　追番
	 * @param no_eda	：【QP@00342】枝番
	 * @param list_seq_shisaku	：依頼の有ったｻﾝﾌﾟﾙの試作SEQリスト
	 * @param userInfo	：ユーザー情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void ExecLogic(

			BigDecimal             	cd_shain
			,int                nen
			,int                no_oi
			,int				 no_eda
			,ArrayList<Integer> list_seq_shisaku
			,UserInfoData       userInfo

			)

	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		userInfoData = userInfo;
		//SQLバッファ
		StringBuffer strSQL = null;
		//検索結果バッファ
		List<?> lstSearchAry = null;
		//【QP@00342】検索結果バッファ枝番
		List<?> lstSearchAry_eda = null;
		//洗い替え実行Fg
		boolean blnAraigae = false;


		//①原価試算依頼の有った、ｻﾝﾌﾟﾙを原価試算にｺﾋﾟｰする。

		try{

			//DBコネクション生成
			super.createExecDB();
			super.createSearchDB();

			//トランザクション開始
			this.searchDB.BeginTran();
			execDB.setSession(searchDB.getSession());


			//【QP@00342】
			//枝番検索SQL作成
			strSQL = null;
			//検索SQL生成
			strSQL = makeSearchSQL_Eda(cd_shain, nen, no_oi);
			//DB検索
			lstSearchAry_eda = this.searchDB.dbSearch_notError(strSQL.toString());


			//試算　試作品テーブル（T310）有無確認（会社/工場情報も取ってくる）
			//検索SQL生成
			strSQL = makeSearchSQL_T310(cd_shain, nen, no_oi);
			//DB検索
			lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

			if ( lstSearchAry.size() > 0 ) {

				Object[] items = (Object[]) lstSearchAry.get(0);

				//T310が存在しない場合
				if(toString(items[0]).equals("")){

					//試算　試作品テーブル（T310）追加
					//更新SQL生成
					strSQL = makeExecSQL_T310(cd_shain, nen, no_oi);
					//DB更新
					this.execDB.execSQL(strSQL.toString());

					//T110の会社CD、工場CDを格納
					//会社CDの退避
					cls_cd_kaisha = Integer.valueOf(toString(items[3])).intValue() ;
					//工場CDの退避
					cls_cd_kojo = Integer.valueOf(toString(items[4])).intValue() ;

					//原価試算メモテーブル作成
					strSQL = null;
					strSQL = makeExecSQL_T311(cd_shain, nen, no_oi);
					//DB更新
					this.execDB.execSQL(strSQL.toString());

					//原価試算メモ（営業連絡用）テーブル作成
					strSQL = null;
					strSQL = makeExecSQL_T312(cd_shain, nen, no_oi);
					//DB更新
					this.execDB.execSQL(strSQL.toString());

				}
				else{
					//枝番数ループ
					for(int j = 0; j < lstSearchAry_eda.size(); j++){

						//枝番取得
						Object[] items_eda = (Object[])lstSearchAry_eda.get(j);
						String roop_no_eda = toString(items_eda[3]);

						//営業ステータス取得
						int st_eigyo = Integer.parseInt(toString(items_eda[6]));

						//営業ステータスが4未満の試作に追加
						if(st_eigyo < 4 ){

							//試算　試作品テーブル（T310）依頼回数更新
							//更新SQL生成
							strSQL = makeExecSQL_sisanShisakuhin(cd_shain, nen, no_oi,roop_no_eda);
							//DB更新
							this.execDB.execSQL(strSQL.toString());
						}
					}
				}

				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
				//再試算依頼時に試算日をクリア
				strSQL = null;
				//更新SQL生成
				strSQL = makeExecSQL_DtShisanClear(cd_shain, nen, no_oi);
				//DB更新
				this.execDB.execSQL(strSQL.toString());
				//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

			}else{

			}


			//【QP@00342】
			//試算　試作テーブルへデータ登録 --------------------------------------------------------
			//依頼のある試作SEQループ
			for (int i = 0; i < list_seq_shisaku.size(); i++) {

				//枝番がある場合
				if( lstSearchAry_eda.size() > 0 ){

					//元版、枝版へ試作を追加

					//枝番数ループ
					for(int j = 0; j < lstSearchAry_eda.size(); j++){

						//枝番取得
						Object[] items_eda = (Object[])lstSearchAry_eda.get(j);
						String roop_no_eda = toString(items_eda[3]);

						//営業ステータス取得
						int st_eigyo = Integer.parseInt(toString(items_eda[6]));

						//営業ステータスが4未満の試作に追加
						if(st_eigyo < 4 ){

							// ADD 2013/7/2 shima【QP@30151】No.37 start
							//試算　基本情報テーブル（T313）追加
							strSQL = makeExecSQL_T313(cd_shain, nen, no_oi, list_seq_shisaku.get(i),roop_no_eda);
							//DB更新
							this.execDB.execSQL(strSQL.toString());
							// ADD 2013/7/2 shima【QP@30151】No.37 end

							//試算　試作テーブル（T331）追加
							//更新SQL生成
							strSQL = makeExecSQL_T331(cd_shain, nen, no_oi, list_seq_shisaku.get(i),roop_no_eda);
							//DB更新
							this.execDB.execSQL(strSQL.toString());
							//洗い替えFgをセット
							items_eda[7] = "true";
							//洗い替えFgをセット
							blnAraigae = true;
						}
					}
				}
				//枝番がない場合（試算データがない場合）
				else{

					//元版へ追加

					// ADD 2013/7/2 shima【QP@30151】No.37 start
					//試算　基本情報テーブル（T313）追加
					strSQL = makeExecSQL_T313(cd_shain, nen, no_oi, list_seq_shisaku.get(i), "0");
					//DB更新
					this.execDB.execSQL(strSQL.toString());

					strSQL = null;
					// ADD 2013/7/2 shima【QP@30151】No.37 end

					//試算　試作テーブル（T331）追加
					//更新SQL生成
					strSQL = makeExecSQL_T331(cd_shain, nen, no_oi, list_seq_shisaku.get(i),"0");
					//DB更新
					this.execDB.execSQL(strSQL.toString());
					//洗い替えFgをセット
					blnAraigae = true;

				}
			}

			//【QP@00342】
			//試算　配合テーブル（T320）へデータ登録 --------------------------------------------------------
			//枝番がある場合
			if( lstSearchAry_eda.size() > 0 ){

				//試算　配合テーブル（T320）有無確認
				strSQL = null;
				//検索SQL生成
				strSQL = makeSearchSQL_T320(cd_shain, nen, no_oi);
				//DB検索
				lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

				if ( lstSearchAry.size() >= 0 ) {

					for (int ix = 0; ix < lstSearchAry.size(); ix++) {

						strSQL = null;
						Object[] items = (Object[]) lstSearchAry.get(ix);

						if(toString(items[5]).equals("")){

							//元版、枝版へ配合を追加
							//枝番数ループ
							for(int j = 0; j < lstSearchAry_eda.size(); j++){
								//枝番取得
								Object[] items_eda = (Object[])lstSearchAry_eda.get(j);
								String roop_no_eda = toString(items_eda[3]);

								//試算　配合テーブル（T320）追加
								//更新SQL生成
								strSQL = makeExecSQL_T320(items,roop_no_eda);

								//DB更新
								this.execDB.execSQL(strSQL.toString());
							}
						}
					}
				}
			}
			else{
				//元版へ配合を追加
				//試算　配合テーブル（T320）有無確認
				strSQL = null;
				//検索SQL生成
				strSQL = makeSearchSQL_T320(cd_shain, nen, no_oi);
				//DB検索
				lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

				if ( lstSearchAry.size() >= 0 ) {

					for (int ix = 0; ix < lstSearchAry.size(); ix++) {

						strSQL = null;
						Object[] items = (Object[]) lstSearchAry.get(ix);

						if(toString(items[5]).equals("")){
							//試算　配合テーブル（T320）追加
							//更新SQL生成
							strSQL = makeExecSQL_T320(items,"0");

							//DB更新
							this.execDB.execSQL(strSQL.toString());

						}
					}
				}
			}

			//ステータスの登録、更新
			//洗い替え実行Fgがセットされている場合（新規に試算依頼されるデータがある場合）
			if( blnAraigae ){

				//枝番がある場合（元版＋枝版への処理）
				if( lstSearchAry_eda.size() > 0 ){

					//枝番数ループ
					for(int j = 0; j < lstSearchAry_eda.size(); j++){

						//枝番取得
						Object[] items_eda = (Object[])lstSearchAry_eda.get(j);
						int roop_no_eda = Integer.parseInt(toString(items_eda[3]));

						//営業ステータス取得
						int st_eigyo = Integer.parseInt(toString(items_eda[6]));

						//営業ステータスが4未満の枝版を洗い替え
						if(st_eigyo < 4 ){

							//ステータスの更新
							strSQL = null;
							strSQL = makeUpdateSQL_T441(cd_shain, nen, no_oi, roop_no_eda);
							//DB更新
							this.execDB.execSQL(strSQL.toString());

							//ステータス履歴の登録
							strSQL = null;
							strSQL = makeExecSQL_T442(cd_shain, nen, no_oi,roop_no_eda);
							//DB更新
							this.execDB.execSQL(strSQL.toString());

						}
					}
				}
				//枝版がない場合（元版だけの処理）
				else{
					//ステータスの登録
					strSQL = null;
					strSQL = makeInsertSQL_T441(cd_shain, nen, no_oi, 0);
					//DB更新
					this.execDB.execSQL(strSQL.toString());

					//ステータス履歴の登録
					strSQL = null;
					strSQL = makeExecSQL_T442(cd_shain, nen, no_oi, 0);
					//DB更新
					this.execDB.execSQL(strSQL.toString());
				}
			}

			//DBコミット
			this.searchDB.Commit();

		}catch(Exception e){
			//DBロールバック
			this.searchDB.Rollback();

			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{
			//DBコネクション開放
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}

			//ローカル変数の開放
			removeList(lstSearchAry);
			strSQL = null;

		}

		//②原価試算ﾃﾞｰﾀの会社工場で原料の洗い替えを行う。

		try{

			//洗い替え実行Fgがセットされている場合（新規に試算依頼されるデータがある場合）
			if( blnAraigae ){

				//【QP@00342】
				//枝番がある場合（元版＋枝版への処理）
				if( lstSearchAry_eda.size() > 0 ){

					//枝番数ループ
					for(int j = 0; j < lstSearchAry_eda.size(); j++){

						//枝番取得
						Object[] items_eda = (Object[])lstSearchAry_eda.get(j);
						int roop_no_eda = Integer.parseInt(toString(items_eda[3]));

						//営業ステータス取得
						int st_eigyo = Integer.parseInt(toString(items_eda[6]));

						//営業ステータスが4未満の枝版を洗い替え
						if(st_eigyo < 4 ){

							//会社、部署取得
							int cd_kaisha =  Integer.parseInt(toString(items_eda[4]));
							int cd_busho =  Integer.parseInt(toString(items_eda[5]));

							//枝番号毎に洗い替え
							CGEN0010_Logic clsCGEN0010_Logic = new CGEN0010_Logic();
							clsCGEN0010_Logic.ExecLogic(cd_shain, nen, no_oi, cd_kaisha, cd_busho, roop_no_eda , userInfo);

						}
					}
				}
				//枝版がない場合（元版だけの処理）
				else{
					//枝番号0で洗い替え
					CGEN0010_Logic clsCGEN0010_Logic = new CGEN0010_Logic();
					clsCGEN0010_Logic.ExecLogic(cd_shain, nen, no_oi, cls_cd_kaisha, cls_cd_kojo, 0 , userInfo);
				}
			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算データの原料、会社/工場洗い替えが失敗しました。");

		}finally{

		}

	}

	/**【QP@00342】
	 * 試算　試作品ﾃｰﾌﾞﾙ更新　（依頼回数）
	 * @param reqData：リクエストデータ
	 * @param lstRecset：検索結果
	 * @return strSql：更新SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer makeExecSQL_sisanShisakuhin(
			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,String no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL格納用
		StringBuffer strSql = new StringBuffer();

		try {
			//SQL文の作成
			strSql.append(" UPDATE tr_shisan_shisakuhin ");
			strSql.append(" SET  ");
			strSql.append("        su_irai = ( ");
			strSql.append(" 				select  ");
			strSql.append(" 					max(su_irai)+1 AS su_irai ");
			strSql.append(" 				from ");
			strSql.append(" 					tr_shisan_shisakuhin ");
			strSql.append(" 				where ");
			strSql.append(" 					cd_shain = " + cd_shain );
			strSql.append(" 					AND nen =" + nen );
			strSql.append(" 					AND no_oi =" + no_oi );
			strSql.append(" 					AND no_eda =" + no_eda );
			strSql.append(" 			) ");
			strSql.append("       ,id_koshin = 9999999999 ");
			strSql.append("       ,dt_koshin = GETDATE() ");
			strSql.append(" WHERE  ");
			strSql.append("       cd_shain =  " + cd_shain );
			strSql.append("       AND nen = " + nen );
			strSql.append("       AND no_oi = " + no_oi );
			strSql.append("       AND no_eda = " + no_eda );

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * 試算　配合テーブル（T320）登録諸原、検索用SQLを生成する
	 * @param cd_shain		：試作CD　社員ID
	 * @param nen			：試作CD　年
	 * @param no_oi			：試作CD　追番
	 * @return StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_T320(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" SELECT ");
			ret.append("  T120.cd_shain  AS cd_shain ");
			ret.append(" ,T120.nen       AS nen ");
			ret.append(" ,T120.no_oi     AS no_oi ");
			ret.append(" ,T120.cd_kotei  AS cd_kotei ");
			ret.append(" ,T120.seq_kotei AS seq_kotei ");
			ret.append(" ,T320.cd_shain  AS cd_shain_SHISAN ");
			ret.append(" ,T320.nen       AS nen_SHISAN ");
			ret.append(" ,T320.no_oi     AS no_oi_SHISAN ");
			ret.append(" ,T320.cd_kotei  AS cd_kotei_SHISAN ");
			ret.append(" ,T320.seq_kotei AS seq_kotei_SHISAN ");
			ret.append(" ,T120.nm_genryo AS nm_genryo ");
			ret.append(" ,T120.tanka     AS tanka ");
			ret.append(" ,T120.budomari  AS budomari ");
			ret.append(" FROM tr_haigo AS T120 ");
			ret.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			ret.append("       ON  T120.cd_shain    = T320.cd_shain ");
			ret.append("       AND T120.nen         = T320.nen ");
			ret.append("       AND T120.no_oi       = T320.no_oi ");
			ret.append("       AND T120.cd_kotei    = T320.cd_kotei ");
			ret.append("       AND T120.seq_kotei   = T320.seq_kotei ");
			ret.append(" WHERE ");
			ret.append("           T120.cd_shain    = " + cd_shain + " ");
			ret.append("       AND T120.nen         = " + nen + " ");
			ret.append("       AND T120.no_oi       = " + no_oi + " ");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}


	/**【QP@00342】
	 * 枝番データ検索SQL生成
	 * @param cd_shain	：試作CD　社員ID
	 * @param nen		：試作CD　年
	 * @param no_oi		：試作CD　追番
	 * @return	StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_Eda(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" SELECT  ");
			ret.append("  T310.cd_shain  ");
			ret.append(" ,T310.nen  ");
			ret.append(" ,T310.no_oi ");
			ret.append(" ,T310.no_eda ");
			ret.append(" ,T310.cd_kaisha ");
			ret.append(" ,T310.cd_kojo ");
			ret.append(" ,T441.st_eigyo ");
			ret.append(" ,'false' AS arai "); //洗い替えフラグ（初期値：false）
			ret.append(" FROM tr_shisan_shisakuhin AS T310 ");

			ret.append(" LEFT JOIN tr_shisan_status AS T441 ");
			ret.append(" ON T310.cd_shain = T441.cd_shain ");
			ret.append(" AND T310.nen = T441.nen ");
			ret.append(" AND T310.no_oi = T441.no_oi ");
			ret.append(" AND T310.no_eda = T441.no_eda ");

			ret.append(" WHERE  ");
			ret.append("      T310.cd_shain = " + cd_shain + " ");
			ret.append("  AND T310.nen      = " + nen + " ");
			ret.append("  AND T310.no_oi    = " + no_oi + " ");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**【QP@00342】
	 * 試算　試作品テーブル（T310）の検索SQL生成
	 * @param cd_shain	：試作CD　社員ID
	 * @param nen		：試作CD　年
	 * @param no_oi		：試作CD　追番
	 * @return	StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_T310(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" SELECT  ");
			ret.append("  T310.cd_shain  ");
			ret.append(" ,T310.nen  ");
			ret.append(" ,T310.no_oi ");
			ret.append(" ,T110.cd_kaisha AS T110_kaisha");
			ret.append(" ,T110.cd_kojo AS T110_kojo");
			ret.append(" ,T310.cd_kaisha AS T310_kaisha ");
			ret.append(" ,T310.cd_kojo AS T310_kojo ");
			ret.append(" FROM tr_shisakuhin AS T110 ");
			ret.append(" LEFT JOIN tr_shisan_shisakuhin AS T310 ");
			ret.append(" ON   T110.cd_shain = T310.cd_shain ");
			ret.append("  AND T110.nen      = T310.nen      ");
			ret.append("  AND T110.no_oi    = T310.no_oi       ");
			ret.append(" WHERE  ");
			ret.append("      T110.cd_shain = " + cd_shain + " ");
			ret.append("  AND T110.nen      = " + nen + " ");
			ret.append("  AND T110.no_oi    = " + no_oi + " ");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**【QP@00342】
	 * 試算　原価試算メモ（T311）の登録SQL生成
	 * @param cd_shain	：試作CD　社員ID
	 * @param nen		：試作CD　年
	 * @param no_oi		：試作CD　追番
	 * @return	StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T311(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_memo  ");
			ret.append("            (cd_shain  ");
			ret.append("            ,nen  ");
			ret.append("            ,no_oi  ");
			ret.append("            ,memo  ");
			ret.append("            ,id_toroku  ");
			ret.append("            ,dt_toroku  ");
			ret.append("            ,id_koshin  ");
			ret.append("            ,dt_koshin)  ");
			ret.append("      VALUES  ");
			ret.append("            (" + cd_shain);
			ret.append("            ," + nen);
			ret.append("            ," + no_oi);
			ret.append("            ,NULL");
			ret.append("            ," + userInfoData.getId_user());
			ret.append("            ,GETDATE()");
			ret.append("            ,NULL");
			ret.append("            ,NULL )");


		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**【QP@00342】
	 * 試算　原価試算メモ（T312）の登録SQL生成
	 * @param cd_shain	：試作CD　社員ID
	 * @param nen		：試作CD　年
	 * @param no_oi		：試作CD　追番
	 * @return	StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T312(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_memo_eigyo  ");
			ret.append("            (cd_shain  ");
			ret.append("            ,nen  ");
			ret.append("            ,no_oi  ");
			ret.append("            ,memo_eigyo  ");
			ret.append("            ,id_toroku  ");
			ret.append("            ,dt_toroku  ");
			ret.append("            ,id_koshin  ");
			ret.append("            ,dt_koshin)  ");
			ret.append("      VALUES  ");
			ret.append("            (" + cd_shain);
			ret.append("            ," + nen);
			ret.append("            ," + no_oi);
			ret.append("            ,NULL");
			ret.append("            ," + userInfoData.getId_user());
			ret.append("            ,GETDATE()");
			ret.append("            ,NULL");
			ret.append("            ,NULL )");


		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**【QP@00342】
	 * 試算　ステータス（T441）の登録SQL生成
	 * @param cd_shain	：試作CD　社員ID
	 * @param nen		：試作CD　年
	 * @param no_oi		：試作CD　追番
	 * @return	StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeInsertSQL_T441(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,int    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

			//ステータス履歴追加
			ret.append(" INSERT INTO tr_shisan_status  ");
			ret.append("            (cd_shain  ");
			ret.append("            ,nen  ");
			ret.append("            ,no_oi  ");
			ret.append("            ,no_eda  ");
			ret.append("            ,st_kenkyu  ");
			ret.append("            ,st_seisan  ");
			ret.append("            ,st_gensizai  ");
			ret.append("            ,st_kojo  ");
			ret.append("            ,st_eigyo  ");
			ret.append("            ,id_toroku  ");
			ret.append("            ,dt_toroku  ");
			ret.append("            ,id_koshin  ");
			ret.append("            ,dt_koshin)  ");
			ret.append("      VALUES  ");
			ret.append("            (" + cd_shain);
			ret.append("            ," + nen);
			ret.append("            ," + no_oi);
			ret.append("            ," + no_eda);
			ret.append("            ,2");
			ret.append("            ,1");
			ret.append("            ,1");
			ret.append("            ,1");
			ret.append("            ,1");
			ret.append("            ," + userInfoData.getId_user());
			ret.append("            ,GETDATE()");
			ret.append("            ,NULL");
			ret.append("            ,NULL )");


		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**【QP@00342】
	 * 試算　ステータス（T441）の登録SQL生成
	 * @param cd_shain	：試作CD　社員ID
	 * @param nen		：試作CD　年
	 * @param no_oi		：試作CD　追番
	 * @return	StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeUpdateSQL_T441(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,int    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

			//ステータステーブル更新
			ret.append(" UPDATE tr_shisan_status ");
			ret.append("    SET  ");
			ret.append("        st_kenkyu = 2");
			ret.append("       ,st_seisan = 1");
			ret.append("       ,st_gensizai = 1");
			ret.append("       ,st_kojo = 1");
			ret.append("       ,st_eigyo = 1");
			ret.append("       ,id_koshin = " + userInfoData.getId_user());
			ret.append("       ,dt_koshin = GETDATE()");
			ret.append("  WHERE ");
			ret.append(" 	  cd_shain =  " + cd_shain);
			ret.append("       AND nen =  " + nen);
			ret.append("       AND no_oi =  " + no_oi);
			ret.append("       AND no_eda =  " + no_eda);

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}

	/**【QP@00342】
	 * 試算　ステータス履歴（T442）の登録SQL生成
	 * @param cd_shain	：試作CD　社員ID
	 * @param nen		：試作CD　年
	 * @param no_oi		：試作CD　追番
	 * @return	StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T442(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,int no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

			//ステータス履歴追加
			ret.append(" INSERT INTO tr_shisan_status_rireki ");
			ret.append("            (cd_shain ");
			ret.append("            ,nen ");
			ret.append("            ,no_oi ");
			ret.append("            ,no_eda ");
			ret.append("            ,dt_henkou ");
			ret.append("            ,cd_kaisha ");
			ret.append("            ,cd_busho ");
			ret.append("            ,id_henkou ");
			ret.append("            ,cd_zikko_ca ");
			ret.append("            ,cd_zikko_li ");
			ret.append("            ,st_kenkyu ");
			ret.append("            ,st_seisan ");
			ret.append("            ,st_gensizai ");
			ret.append("            ,st_kojo ");
			ret.append("            ,st_eigyo ");
			ret.append("            ,id_toroku ");
			ret.append("            ,dt_toroku) ");
			ret.append("      VALUES ");
			ret.append("            (" + cd_shain + " ");
			ret.append("            ," + nen + " ");
			ret.append("            ," + no_oi + " ");
			ret.append("            ," + no_eda +" ");
			ret.append("            ,GETDATE() ");
			ret.append("            ," + userInfoData.getCd_kaisha() + " ");
			ret.append("            ," + userInfoData.getCd_busho() + " ");
			ret.append("            ," + userInfoData.getId_user() + " ");
			ret.append("            ,'wk_kenkyu'");
			ret.append("            ,'1' ");
			ret.append("            ,2 ");
			ret.append("            ,1 ");
			ret.append("            ,1 ");
			ret.append("            ,1 ");
			ret.append("            ,1 ");
			ret.append("            ," + userInfoData.getId_user() + " ");
			ret.append("            ,GETDATE() )");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * 試算　試作テーブル（T331）の検索用SQLを生成する
	 * @param cd_shain		：試作CD　社員ID
	 * @param nen			：試作CD　年
	 * @param no_oi			：試作CD　追番
	 * @param seq_shisaku	：試作SEQ
	 * @return StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_T331(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,int    seq_shisaku
			,String    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

//			ret.append(" SELECT ");
//			ret.append("  cd_shain ");
//			ret.append(" ,nen ");
//			ret.append(" ,no_oi ");
//			ret.append(" ,seq_shisaku ");
//			ret.append(" ,no_eda ");
//			ret.append(" FROM tr_shisan_shisaku ");
//			ret.append(" WHERE ");
//			ret.append("      cd_shain    = " + cd_shain + " ");
//			ret.append("  AND nen         = " + nen + " ");
//			ret.append("  AND no_oi       = " + no_oi + " ");
//			ret.append("  AND seq_shisaku = " + seq_shisaku + " ");

			//【QP@00342】
			ret.append(" SELECT ");
			ret.append("  T331.cd_shain ");
			ret.append(" ,T331.nen ");
			ret.append(" ,T331.no_oi ");
			ret.append(" ,T331.seq_shisaku ");
			ret.append(" ,T331.no_eda ");
			ret.append(" FROM ");
			ret.append(" tr_shisan_shisaku AS T331 ");
			ret.append(" LEFT JOIN tr_shisan_status AS T441 ");
			ret.append(" ON T441.cd_shain = T331.cd_shain  ");
			ret.append(" AND T441.nen = T331.nen ");
			ret.append(" AND T441.no_oi = T331.no_oi ");
			ret.append(" AND T441.no_eda = T331.no_eda ");
			ret.append("      cd_shain    = " + cd_shain + " ");
			ret.append("  AND nen         = " + nen + " ");
			ret.append("  AND no_oi       = " + no_oi + " ");
			ret.append("  AND seq_shisaku = " + seq_shisaku + " ");
			ret.append("  AND no_eda       = " + no_eda + " ");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * 試算　配合テーブル（T320）登録諸原、検索用SQLを生成する（元版）
	 * @param cd_shain		：試作CD　社員ID
	 * @param nen			：試作CD　年
	 * @param no_oi			：試作CD　追番
	 * @return StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_T320_motohan(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" SELECT ");
			ret.append("  T120.cd_shain  AS cd_shain ");
			ret.append(" ,T120.nen       AS nen ");
			ret.append(" ,T120.no_oi     AS no_oi ");
			ret.append(" ,T120.cd_kotei  AS cd_kotei ");
			ret.append(" ,T120.seq_kotei AS seq_kotei ");
			ret.append(" ,T320.cd_shain  AS cd_shain_SHISAN ");
			ret.append(" ,T320.nen       AS nen_SHISAN ");
			ret.append(" ,T320.no_oi     AS no_oi_SHISAN ");
			ret.append(" ,T320.cd_kotei  AS cd_kotei_SHISAN ");
			ret.append(" ,T320.seq_kotei AS seq_kotei_SHISAN ");
			ret.append(" ,T120.nm_genryo AS nm_genryo ");
			ret.append(" ,T120.tanka     AS tanka ");
			ret.append(" ,T120.budomari  AS budomari ");
			ret.append(" ,0  AS no_eda ");
			ret.append(" FROM tr_haigo AS T120 ");
			ret.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			ret.append("       ON  T120.cd_shain    = T320.cd_shain ");
			ret.append("       AND T120.nen         = T320.nen ");
			ret.append("       AND T120.no_oi       = T320.no_oi ");
			ret.append("       AND T120.cd_kotei    = T320.cd_kotei ");
			ret.append("       AND T120.seq_kotei   = T320.seq_kotei ");
			ret.append(" WHERE ");
			ret.append("           T120.cd_shain    = " + cd_shain + " ");
			ret.append("       AND T120.nen         = " + nen + " ");
			ret.append("       AND T120.no_oi       = " + no_oi + " ");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * 試算　配合テーブル（T320）登録諸原、検索用SQLを生成する（元版）
	 * @param cd_shain		：試作CD　社員ID
	 * @param nen			：試作CD　年
	 * @param no_oi			：試作CD　追番
	 * @return StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeSearchSQL_T320_edahan(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,String    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//SQLバッファ
		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" SELECT ");
			ret.append("  T120.cd_shain  AS cd_shain ");
			ret.append(" ,T120.nen       AS nen ");
			ret.append(" ,T120.no_oi     AS no_oi ");
			ret.append(" ,T120.cd_kotei  AS cd_kotei ");
			ret.append(" ,T120.seq_kotei AS seq_kotei ");
			ret.append(" ,T320.cd_shain  AS cd_shain_SHISAN ");
			ret.append(" ,T320.nen       AS nen_SHISAN ");
			ret.append(" ,T320.no_oi     AS no_oi_SHISAN ");
			ret.append(" ,T320.cd_kotei  AS cd_kotei_SHISAN ");
			ret.append(" ,T320.seq_kotei AS seq_kotei_SHISAN ");
			ret.append(" ,T120.nm_genryo AS nm_genryo ");
			ret.append(" ,T120.tanka     AS tanka ");
			ret.append(" ,T120.budomari  AS budomari ");
			ret.append(" ,T320.no_eda  AS no_eda ");
			ret.append(" FROM tr_haigo AS T120 ");
			ret.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			ret.append("       ON  T120.cd_shain    = T320.cd_shain ");
			ret.append("       AND T120.nen         = T320.nen ");
			ret.append("       AND T120.no_oi       = T320.no_oi ");
			ret.append("       AND T120.cd_kotei    = T320.cd_kotei ");
			ret.append("       AND T120.seq_kotei   = T320.seq_kotei ");
			ret.append(" WHERE ");
			ret.append("           T320.cd_shain    = " + cd_shain + " ");
			ret.append("       AND T320.nen         = " + nen + " ");
			ret.append("       AND T320.no_oi       = " + no_oi + " ");

			//【QP@00342】
			ret.append("       AND T320.no_eda       = " + no_eda + " ");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * 試算　試作品テーブル（T310）追加用SQLを生成する
	 * @param cd_shain		：試作CD　社員ID
	 * @param nen			：試作CD　年
	 * @param no_oi			：試作CD　追番
	 * @return StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T310(

			BigDecimal             	cd_shain
			,int                nen
			,int                no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_shisakuhin ");
			ret.append(" ( ");
			ret.append("  cd_shain ");
			ret.append(" ,nen ");
			ret.append(" ,no_oi ");

			//【QP@00342】
			ret.append(" ,no_eda ");

			ret.append(" ,cd_kaisha ");
			ret.append(" ,cd_kojo ");
			ret.append(" ,su_iri ");
			ret.append(" ,genka ");
			ret.append(" ,baika ");
			ret.append(" ,cd_genka_tani ");
			// DEL 2013/9/6 okano【QP@30151】No.30 start
			//ret.append(" ,buturyo ");
			// DEL 2013/9/6 okano【QP@30151】No.30 end
			ret.append(" ,dt_hatubai ");
			ret.append(" ,uriage_k ");
			ret.append(" ,rieki_k ");
			ret.append(" ,uriage_h ");
			ret.append(" ,rieki_h ");
			ret.append(" ,cd_nisugata ");
			ret.append(" ,lot ");
			ret.append(" ,saiyo_sample ");
			ret.append(" ,ken_id_koshin ");
			ret.append(" ,ken_dt_koshin ");
			ret.append(" ,sei_id_koshin ");
			ret.append(" ,sei_dt_koshin ");
			ret.append(" ,gen_id_koshin ");
			ret.append(" ,gen_dt_koshin ");
			ret.append(" ,kojo_id_koshin ");
			ret.append(" ,kojo_dt_koshin ");
			ret.append(" ,sam_dt_koshin ");
			ret.append(" ,fg_keisan ");
			ret.append(" ,id_toroku ");
			ret.append(" ,dt_toroku ");
			ret.append(" ,id_koshin ");
			ret.append(" ,dt_koshin ");

			//【QP@00342】
			ret.append(" ,yoryo ");
			ret.append(" ,su_irai ");
			//ADD 2013/10/22 QP@30154 okano start
			ret.append(" ,cd_hanseki ");
			//ADD 2013/10/22 QP@30154 okano end

			ret.append(" ) ");
			ret.append(" SELECT ");
			ret.append("  cd_shain ");
			ret.append(" ,nen ");
			ret.append(" ,no_oi ");

			//【QP@00342】
			ret.append(" ,0 ");

			ret.append(" ,cd_kaisha ");
			ret.append(" ,cd_kojo ");
			ret.append(" ,su_iri ");
			ret.append(" ,genka ");
			ret.append(" ,baika ");
			ret.append(" ,NULL ");
			// DEL 2013/9/6 okano【QP@30151】No.30 start
			//ret.append(" ,buturyo ");
			// DEL 2013/9/6 okano【QP@30151】No.30 end
			ret.append(" ,dt_hatubai ");
			ret.append(" ,uriage_k ");
			ret.append(" ,rieki_k ");
			ret.append(" ,uriage_h ");
			ret.append(" ,rieki_h ");
//2011/11/30 【QP@10713】荷姿自動編集初期設定に変更　TT H.SHIMA Start
//			ret.append(" ,cd_nisugata ");
			ret.append(" ,replace(convert(varchar, convert(MONEY, yoryo), 1), '.00', '')+nm_literal+'/'+replace(convert(varchar, convert(MONEY,su_iri),1),'.00','') AS cd_nisugata ");
//2011/11/30 【QP@10713】荷姿自動編集初期設定に変更　TT H.SHIMA End
			ret.append(" ,NULL ");

// 2010/05/19　シサクイック（原価）　試算メモの初期値はNULLに変更　TT.NISHIGAWA　START
			//ret.append(" ,memo_shisaku ");
//			ret.append(" ,NULL ");
// 2010/05/19　シサクイック（原価）　試算メモの初期値はNULLに変更　TT.NISHIGAWA　END

			ret.append(" ,NULL ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,GETDATE() ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ,1 ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,GETDATE() ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,GETDATE() ");

			//【QP@00342】
			ret.append(" ,yoryo");
			ret.append(" ,1");
			//ADD 2013/10/22 QP@30154 okano start
			ret.append(" ,cd_hanseki ");
			//ADD 2013/10/22 QP@30154 okano end

			ret.append(" FROM ");
			ret.append(" tr_shisakuhin ");
//2011/11/30 【QP@10713】荷姿自動編集初期設定に変更　TT H.SHIMA Start
			ret.append(" ,ma_literal");
//2011/11/30 【QP@10713】荷姿自動編集初期設定に変更　TT H.SHIMA End
			ret.append(" WHERE ");
			ret.append("     cd_shain = " + cd_shain + " ");
			ret.append(" AND nen      = " + nen + " ");
			ret.append(" AND no_oi    = " + no_oi + " ");
//2011/11/30 【QP@10713】荷姿自動編集初期設定に変更　TT H.SHIMA Start
			ret.append(" AND cd_literal = cd_tani ");
			ret.append(" AND cd_category = 'K_tani' ");
//2011/11/30 【QP@10713】荷姿自動編集初期設定に変更　TT H.SHIMA End

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * 試算　試作テーブル（T331）追加用SQLの生成
	 * @param cd_shain		：試作CD　社員ID
	 * @param nen			：試作CD　年
	 * @param no_oi			：試作CD　追番
	 * @param seq_shisaku	：試作SEQ
	 * @return StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T331(

			BigDecimal cd_shain
			,int    nen
			,int    no_oi
			,int    seq_shisaku
			,String    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_shisaku ");
			ret.append(" ( ");
			ret.append("  cd_shain ");				//0
			ret.append(" ,nen ");					//1
			ret.append(" ,no_oi ");					//2
			ret.append(" ,seq_shisaku ");			//3

			//【QP@00342】
			ret.append(" ,no_eda ");

			ret.append(" ,juryo_shiagari_g ");		//4
			ret.append(" ,dt_shisan ");				//5
			ret.append(" ,budomari ");				//6
			ret.append(" ,heikinjuten ");			//7
			ret.append(" ,cs_kotei ");				//8
			ret.append(" ,kg_kotei ");				//9
			ret.append(" ,id_toroku ");				//10
			ret.append(" ,dt_toroku ");				//11
			ret.append(" ,id_koshin ");				//12
			ret.append(" ,dt_koshin ");				//13
//ADD 2013/07/9 ogawa 【QP@30151】No.13 start
			//項目固定チェック ON(1)をセット
			ret.append(" ,fg_koumokuchk ");
//ADD 2013/07/09 ogawa 【QP@30151】No.13 end
			//ADD 2013/10/22 QP@30154 okano start
			ret.append(" ,cs_rieki ");
			ret.append(" ,kg_rieki ");
			//ADD 2013/10/22 QP@30154 okano end
			ret.append(" ) ");
			ret.append(" SELECT  ");
			ret.append("  T131.cd_shain ");			//0
			ret.append(" ,T131.nen ");				//1
			ret.append(" ,T131.no_oi ");			//2
			ret.append(" ,T131.seq_shisaku ");		//3

			//【QP@00342】
			ret.append(" ," + no_eda);			//2

			ret.append(" ,T131.juryo_shiagari_g ");	//4
			ret.append(" ,NULL ");					//5
			ret.append(" ,NULL ");                    //6
			ret.append(" ,NULL ");					//7

//			ret.append(" ,CASE IsNull(T141.yukobudomari,'') WHEN '' ");
//			ret.append(" THEN NULL ");
//			ret.append(" ELSE T141.yukobudomari ");
//			ret.append(" END AS budomari ");		//6
//
//			ret.append(" ,CASE IsNull(T141.heikinzyu,'') WHEN '' ");
//			ret.append(" THEN NULL ");
//			ret.append(" ELSE T141.yukobudomari ");
//			ret.append(" END AS heikinzyu ");		//7

			ret.append(" ,NULL ");					//8
			ret.append(" ,NULL ");					//9
			ret.append(" ,9999 ");					//10
			ret.append(" ,GETDATE() ");				//11
			ret.append(" ,9999 ");					//12
			ret.append(" ,GETDATE() ");				//13

//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 ※デフォルトはチェックオフstart
//ADD 2013/07/9 ogawa 【QP@30151】No.13 start
			//項目固定チェック ON(1)をセット
			//ret.append(" ,1 ");
			ret.append(" ,0 ");
//ADD 2013/07/09 ogawa 【QP@30151】No.13 end
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

			//ADD 2013/10/22 QP@30154 okano start
			ret.append(" ,NULL ");
			ret.append(" ,NULL ");
			//ADD 2013/10/22 QP@30154 okano end
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_genryo AS T141 ");
			ret.append(" ON  T131.cd_shain    = T141.cd_shain ");
			ret.append(" AND T131.nen         = T141.nen ");
			ret.append(" AND T131.no_oi       = T141.no_oi ");
			ret.append(" AND T131.seq_shisaku = T141.seq_shisaku ");
			ret.append(" WHERE ");
			ret.append("     T131.cd_shain = " + cd_shain + " ");
			ret.append(" AND T131.nen      = " + nen + " ");
			ret.append(" AND T131.no_oi    = " + no_oi + " ");
			ret.append(" AND T131.seq_shisaku  = " + seq_shisaku + " ");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * 試算　配合テーブル（T320）の追加用SQLを生成する
	 * @param items	：諸原検索結果
	 * @return StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T320(

			Object[] items
			,String no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_haigo ");
			ret.append(" ( ");
			ret.append("  cd_shain     ");
			ret.append(" ,nen          ");
			ret.append(" ,no_oi        ");

			//【QP@00342】
			ret.append(" ,no_eda        ");


			ret.append(" ,cd_kotei     ");
			ret.append(" ,seq_kotei    ");
			ret.append(" ,nm_genryo    ");
			ret.append(" ,tanka_ins ");
			ret.append(" ,budomari_ins ");
			ret.append(" ,tanka_ma ");
			ret.append(" ,budomar_ma ");
			ret.append(" ,id_toroku ");
			ret.append(" ,dt_toroku ");
			ret.append(" ,id_koshin ");
			ret.append(" ,dt_koshin ");
			ret.append(" ) ");
			ret.append(" VALUES ");
			ret.append(" ( ");
			ret.append("  " + toString(items[0]) + " ");
			ret.append(" ," + toString(items[1]) + " ");
			ret.append(" ," + toString(items[2]) + " ");

			//【QP@00342】
			ret.append(" ," + no_eda + " ");

			ret.append(" ," + toString(items[3]) + " ");
			ret.append(" ," + toString(items[4]) + " ");
			ret.append(" ,'" + toString(items[10]) + "' ");
			ret.append(" ,null ");
			ret.append(" ,null ");
			ret.append(" ," + items[11] + " ");
			ret.append(" ," + items[12] + " ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,getdate() ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,getdate() ");
			ret.append(" ) ");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}


	// ADD 2013/7/2 shima【QP@30151】No.37 start
	/**
	 * 試算　基本情報テーブル（T313）の追加用SQLを生成する
	 * @param cd_shain		：試作CD　社員ID
	 * @param nen			：試作CD　年
	 * @param no_oi			：試作CD　追番
	 * @param seq_shisaku	：試作SEQ
	 * @param no_eda		：枝番
	 * @return StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_T313(

			BigDecimal cd_shain
			,int       nen
			,int       no_oi
			,int       seq_shisaku
			,String    no_eda
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try{

			ret.append(" INSERT INTO tr_shisan_kihonjoho ");
			ret.append(" ( ");
			ret.append("  cd_shain ");
			ret.append(" ,nen ");
			ret.append(" ,no_oi ");
			ret.append(" ,seq_shisaku ");
			ret.append(" ,no_eda ");
			ret.append(" ,genka ");
			ret.append(" ,baika ");
			ret.append(" ,cd_genka_tani ");
			// DEL 2013/9/6 okano【QP@30151】No.30 start
			//ret.append(" ,buturyo ");
			// DEL 2013/9/6 okano【QP@30151】No.30 end
			ret.append(" ,dt_hatubai ");
			ret.append(" ,uriage_k ");
			ret.append(" ,rieki_k ");
			ret.append(" ,uriage_h ");
			ret.append(" ,rieki_h ");
			ret.append(" ,id_toroku ");
			ret.append(" ,dt_toroku ");
			ret.append(" ,id_koshin ");
			ret.append(" ,dt_koshin ");
			ret.append(" ) ");
			ret.append(" SELECT ");
			ret.append("  T310.cd_shain ");			//社員
			ret.append(" ,T310.nen ");				//年
			ret.append(" ,T310.no_oi ");			//追番
			ret.append(" ," + seq_shisaku + " ");	//試作SEQ
			ret.append(" ," + no_eda + " ");		//枝番
			ret.append(" ,T310.genka ");			//希望原価
			ret.append(" ,T310.baika ");			//希望特約
			ret.append(" ,NULL ");					//原価単位コード
			// DEL 2013/9/6 okano【QP@30151】No.30 start
			//ret.append(" ,T310.buturyo ");			//想定物量
			// DEL 2013/9/6 okano【QP@30151】No.30 end
			ret.append(" ,T310.dt_hatubai ");		//初回納品次期
			ret.append(" ,T310.uriage_k ");			//計画売上
			ret.append(" ,T310.rieki_k ");			//計画利益
			ret.append(" ,T310.uriage_h ");			//販売後売上
			ret.append(" ,T310.rieki_h ");			//販売後利益
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,GETDATE() ");
			ret.append(" ," + userInfoData.getId_user() + " ");
			ret.append(" ,GETDATE() ");
			ret.append(" FROM ");
			ret.append(" tr_shisan_shisakuhin T310 ");
			ret.append(" INNER JOIN tr_shisakuhin T110 ");
			ret.append(" ON  T310.cd_shain = T110.cd_shain ");
			ret.append(" AND T310.nen = T110.nen ");
			ret.append(" AND T310.no_oi = T110.no_oi ");
			ret.append(" ,ma_literal M_Lit ");
			ret.append(" WHERE ");
			ret.append("     T310.cd_shain = " + cd_shain + " ");
			ret.append(" AND T310.nen      = " + nen + " ");
			ret.append(" AND T310.no_oi    = " + no_oi + " ");

			//ADD 2014/01/21 nishigawa 【QP@30151】No.37 start
			ret.append(" AND T310.no_eda   = 0 ");
			//ADD 2014/01/21 nishigawa 【QP@30151】No.37 end

			ret.append(" AND M_Lit.cd_literal = cd_tani ");
			ret.append(" AND M_Lit.cd_category = 'K_tani' ");

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	// ADD 2013/7/2 shima【QP@30151】No.37 end


	//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
	/**
	 * 試算期日をクリア
	 * @param cd_shain		：試作CD　社員ID
	 * @param nen			：試作CD　年
	 * @param no_oi			：試作CD　追番
	 * @return StringBuffer	：SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer makeExecSQL_DtShisanClear(
			BigDecimal cd_shain
			,int       nen
			,int       no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		StringBuffer ret = new StringBuffer();
		try{
			ret.append(" update tr_shisan_shisaku ");
			ret.append(" set dt_shisan=null ");
			ret.append(" where cd_shain=" + cd_shain);
			ret.append(" and nen=" + nen);
			ret.append(" and no_oi=" + no_oi);
			//ADD 2015/09/15 kitazawa 【QP@30154】追加課題No.11 不具合修正 start
			ret.append(" and fg_koumokuchk <> 1");
			//ADD 2015/09/15 kitazawa 【QP@30154】追加課題No.11 不具合修正 end
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "");
		}finally{

		}
		return ret;
	}
}
