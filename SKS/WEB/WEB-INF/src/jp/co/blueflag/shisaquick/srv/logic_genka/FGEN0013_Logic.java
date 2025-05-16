package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN0020_Logic;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * 原価試算資材表示検索DB処理
 *  : 原価試算画面の資材部分の情報を取得する。
 *  機能ID：FGEN0010_Logic
 *
 * @author Nishigawa
 * @since  2009/10/22
 */
public class FGEN0013_Logic extends LogicBase{

	//試作NO
	BigDecimal cd_shisaku_syainID = new BigDecimal(-1);
	int cd_shisaku_nen = -1;
	int cd_shisaku_oi = -1;
	int cd_shisaku_eda = -1; //【QP@00342】

	//資材情報
	List<?> listShizai = null;

	/**
	 * 原価試算資材表示検索DB処理
	 * : インスタンス生成
	 */
	public FGEN0013_Logic() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * 原価試算原料表示
	 *  : 原価試算画面の原料部分の情報を取得する。
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

		//再計算リクエスト
		RequestResponsKindBean reqDataKeisan = null;
		//再計算レスポンス
		RequestResponsKindBean resDataKeisan = null;
		//原価試算原料表示レスポンス
		RequestResponsKindBean ret = null;
		//再計算クラス
		CGEN0020_Logic clsCGEN0020_Logic = null;


		try {

			cd_shisaku_syainID = toDecimal(reqData.getFieldVale("kihon", "rec", "cd_shain"), "-1");
			cd_shisaku_nen = toInteger(reqData.getFieldVale("kihon", "rec", "nen"), -1);
			cd_shisaku_oi = toInteger(reqData.getFieldVale("kihon", "rec", "no_oi"), -1);

			//【QP@00342】
			cd_shisaku_eda = toInteger(reqData.getFieldVale("kihon", "rec", "no_eda"), -1);

			//DB情報取得
			getDBKomoku();

			//リクエストインスタンス
			reqDataKeisan = new RequestResponsKindBean();
			//再計算リクエスト生成
			makeReqDataKeisan(reqDataKeisan);

			//再計算
			clsCGEN0020_Logic = new CGEN0020_Logic();
			resDataKeisan = clsCGEN0020_Logic.ExecLogic(reqDataKeisan, userInfoData);

			//レスポンスのインスタンス
			ret = new RequestResponsKindBean();
			//原価試算原料表示レスポンス生成
			makeResData(ret, resDataKeisan);


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//リストの破棄
			removeList(listShizai);

		}
		return ret;

	}
	/**
	 * 再計算クラスに渡すリクエストを生成
	 * @param reqDataKeisan	：生成後リクエスト
	 * @param listShisaku	：DB試作情報
	 * @param listSanpuru	：DBサンプル情報
	 * @param listGenryo	：DB原料情報
	 * @param listShizai	：DB資材情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan(

			 RequestResponsKindBean reqDataKeisan
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {


		try{

			//基本リクエストの生成
			makeReqDataKeisan_Kihon(reqDataKeisan);

			//ID設定
			reqDataKeisan.setID("CGEN0020");

			//資材リクエストの生成
			makeReqDataKeisan_Shizai(reqDataKeisan);

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　DB情報取得に失敗しました。 ");

		}finally{

		}

	}
	/**
	 * 基本リクエストの生成
	 * @param reqDataKeisan	：生成結果　リクエスト
	 * @param listShisaku	：DB情報　試作
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan_Kihon(

			 RequestResponsKindBean reqDataKeisan
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try{

			// ----------------------------------------------------------------------+
			// 試作CD-社員CD	cd_shain
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "cd_shain", toString(cd_shisaku_syainID));
			// ----------------------------------------------------------------------+
			// 試作CD-年		nen
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "nen", toString(cd_shisaku_nen));
			// ----------------------------------------------------------------------+
			// 試作CD-追番	no_oi
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "no_oi", toString(cd_shisaku_oi));
			// ----------------------------------------------------------------------+
			// 【QP@00342】試作CD-追番	no_eda
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "no_eda", toString(cd_shisaku_eda));
			// ----------------------------------------------------------------------+
			// 入り数			irisu
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "irisu", toString(""));
			// ----------------------------------------------------------------------+
			// 処理モード		mode
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "mode", "2");
			// ----------------------------------------------------------------------+
			// 原価希望		kibo_genka
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_genka", toString(""));
			// ----------------------------------------------------------------------+
			// 売価希望		kibo_baika
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_baika", toString(""));
			// ----------------------------------------------------------------------+
			// 原価単位CD	kibo_genka_tani
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_genka_tani", toString(""));
			// ----------------------------------------------------------------------+
			// 計算項目（固定費/ケースor固定費/kg）	ragio_kesu_kg
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "ragio_kesu_kg", toString(""));

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　DB情報取得に失敗しました。 ");

		}finally{

		}

	}
	/**
	 * レスポンスデータの生成
	 * @param resData		：編集結果　レスポンスデータ
	 * @param resDataKeisan	：計算クラスの結果
	 * @param listShisaku	：DB情報　試作情報
	 * @param listSanpuru	：DB情報　サンプル情報
	 * @param listGenryo	：DB情報　原料情報
	 * @param listShizai	：DB情報　資材情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeResData(

			 RequestResponsKindBean resData
			,RequestResponsKindBean resDataKeisan
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int cnt = 0;
		int cntShizai = 0;

		try{


			resData.setID("FGEN0013");

			try{

				// E.kitazawa javaエラー対応 (NullPointerException)--- add start 2015/03/03
				// 存在チェック
				RequestResponsTableBean been = resDataKeisan.getTableItem("shizai");
				if(been == null) {
					// shizai データが無いとき（catch で設定しない）
					cnt = toInteger(ConstManager.getConstValue(ConstManager.Category.設定,"GENK_SHIZAI_LIST_ADD_CNT"));
				} else {
				// E.kitazawa javaエラー対応 --------------------- add end

					cntShizai = resDataKeisan.getCntRow("shizai");
					cnt = toInteger(resDataKeisan.getFieldVale("kihon", "rec", "cnt_shizai"));
				}

			}catch(Exception e){

				cnt = toInteger(ConstManager.getConstValue(ConstManager.Category.設定,"GENK_SHIZAI_LIST_ADD_CNT"));


			}

			//基本（Kihon）レスポンス生成
			resData.addTableItem(resDataKeisan.getTableItem("kihon"));
			//手入力分の行を負荷
			resData.setFieldVale("kihon", "rec", "cnt_shizai"
					, toString(cnt));

			if (cntShizai > 0){
				//資材（shizai）レスポンス生成
				resData.addTableItem(resDataKeisan.getTableItem("shizai"));

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　レスポンス生成に失敗しました。 ");

		}finally{

		}

	}
	/**
	 * DB情報取得
	 * @param listShisaku	： 検索結果　試作情報
	 * @param listSanpuru	： 検索結果　サンプル情報
	 * @param listGenryo	： 検索結果　原料情報
	 * @param listShizai	： 検索結果　資材情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void getDBKomoku()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQLバッファ
		StringBuffer strSQL = new StringBuffer();

		try{

			//DBコネクション
			createSearchDB();

			//資材情報

			strSQL = null;
			strSQL = new StringBuffer();

			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  cd_shain ");	//0
			strSQL.append(" ,nen ");		//1
			strSQL.append(" ,no_oi ");		//2
			strSQL.append(" ,seq_shizai ");	//3
			strSQL.append(" ,no_sort ");	//4
			strSQL.append(" ,cd_kaisha ");	//5
			strSQL.append(" ,cd_busho  ");	//6
			strSQL.append(" ,cd_shizai ");	//7
			strSQL.append(" ,nm_shizai ");	//8
			strSQL.append(" ,tanka ");		//9
			strSQL.append(" ,budomari ");	//10
			strSQL.append(" ,cs_siyou ");	//11
			strSQL.append(" ,id_toroku ");	//12
			strSQL.append(" ,CONVERT(VARCHAR,dt_toroku,111) ");	//13
			strSQL.append(" ,id_koshin ");	//14
			strSQL.append(" ,CONVERT(VARCHAR,dt_koshin,111) ");	//15
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shizai ");
			strSQL.append(" WHERE ");
			strSQL.append("     cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND no_oi    = " + toString(cd_shisaku_oi) + " ");

			//【QP@00342】
			strSQL.append(" AND no_eda    = " + toString(cd_shisaku_eda) + " ");
			strSQL.append(" ORDER BY ");
			strSQL.append("  no_sort ");
			//DB検索
			listShizai = this.searchDB.dbSearch(strSQL.toString());

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　DB情報取得に失敗しました。 \nSQL:"
					+ strSQL.toString());

		}finally{
			//DBコネクション開放
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}
			//ローカル変数の開放
			strSQL = null;

		}

	}
	/**
	 * 資材リクエストの生成
	 * @param reqDataKeisan	:編集結果リクエスト
	 * @param listShizai	:資材情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan_Shizai(

			 RequestResponsKindBean reqDataKeisan
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try{

			for(int i = 0 ; i < listShizai.size(); i++){

				Object[] items = (Object[]) listShizai.get(i);

				// ----------------------------------------------------------------------+
				// 資材SEQ	seq_shizai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "seq_shizai", toString(items[3]));
				// ----------------------------------------------------------------------+
				// 会社CD	cd_kaisya
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "cd_kaisya", toString(items[5]));
				// ----------------------------------------------------------------------+
				// 工場CD	cd_kojyo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "cd_kojyo", toString(items[6]));
				// ----------------------------------------------------------------------+
				// 資材CD	cd_shizai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "cd_shizai", toString(items[7]));
				// ----------------------------------------------------------------------+
				// 資材名	nm_shizai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "nm_shizai", toString(items[8]));
				// ----------------------------------------------------------------------+
				// 単価		tanka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "tanka", toString(items[9]));
				// ----------------------------------------------------------------------+
				// 歩留（％）	budomari
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "budomari", toString(items[10]));
				// ----------------------------------------------------------------------+
				// 使用量/ｹｰｽ	shiyouryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "shiyouryo", toString(items[11]));
				// ----------------------------------------------------------------------+
				// 登録者ID id_toroku
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "id_toroku", toString(items[12]));
				// ----------------------------------------------------------------------+
				// 登録日付 dt_toroku
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "dt_toroku", toString(items[13]));

			}

		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　DB情報取得に失敗しました。 ");

		}finally{

		}

	}

}