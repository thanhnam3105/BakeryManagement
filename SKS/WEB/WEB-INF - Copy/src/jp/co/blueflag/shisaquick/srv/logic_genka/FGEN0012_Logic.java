package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN0020_Logic;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * 原価試算原料表示検索DB処理
 *  : 原価試算画面の原料部分の情報を取得する。
 *  機能ID：FGEN0012_Logic
 *  
 * @author Nishigawa
 * @since  2009/10/23
 */
public class FGEN0012_Logic extends LogicBase{
	
	//試作NO
	BigDecimal cd_shisaku_syainID = new BigDecimal(-1);
	int cd_shisaku_nen = -1;
	int cd_shisaku_oi = -1;
	int cd_shisaku_eda = -1; //【QP@00342】
	
	//試作情報
	List<?> listShisaku = null;	
	//ｻﾝﾌﾟﾙ情報
	List<?> listSanpuru = null;	
	//原料情報
	List<?> listGenryo = null;	
	//資材情報
	List<?> listShizai = null;
	// ADD 2013/7/2 shima【QP@30151】No.37 start
	//基本情報サブ
	List<?> listKihonSub = null;
	// ADD 2013/7/2 shima【QP@30151】No.37 end
	
	/**
	 * 原価試算原料表示検索DB処理
	 * : インスタンス生成
	 */
	public FGEN0012_Logic() {
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
			removeList(listShisaku);	
			removeList(listSanpuru);	
			removeList(listGenryo);	
			removeList(listShizai);	
			// ADD 2013/7/2 shima【QP@30151】No.37 start
			removeList(listKihonSub);
			// ADD 2013/7/2 shima【QP@30151】No.37 end

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
			

			//ID設定
			reqDataKeisan.setID("CGEN0020");
			
			//基本リクエストの生成
			makeReqDataKeisan_Kihon(reqDataKeisan);
			//原料リクエストの生成
			makeReqDataKeisan_Genryo(reqDataKeisan);
			//計算リクエストの生成
			makeReqDataKeisan_Keisan(reqDataKeisan);
			//資材リクエストの生成
			makeReqDataKeisan_Shizai(reqDataKeisan);
			// ADD 2013/7/2 shima【QP@30151】No.37 start
			//基本情報サブリクエストの生成
			makeReqDataKihon_Sub(reqDataKeisan);
			// ADD 2013/7/2 shima【QP@30151】No.37 end
		
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
		
		
		try{

			
			resData.setID("FGEN0012");
			
			//基本（Kihon）レスポンス生成
			resData.addTableItem(resDataKeisan.getTableItem("kihon"));
			
			//原料（genryo）レスポンス生成
			makeResData_genryo(resData, resDataKeisan);
			
			//試作（shisaku＋列seq）レスポンス生成
			for(int i = 0 ; i < toInteger(resData.getFieldVale("kihon", "rec", "cnt_sanpuru")); i++){
				
				resData.addTableItem(
						resDataKeisan.getTableItem("shisaku" + toString(i)));
				
			}
			
			//計算（keisan）レスポンス生成
			resData.addTableItem(resDataKeisan.getTableItem("keisan"));
		
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　レスポンス生成に失敗しました。 ");
			
		}finally{
			
		}
		
	}
	/**
	 * 原料情報レスオンス生成
	 * @param resData		：編集結果　レスポンスデータ
	 * @param resDataKeisan	：計算クラスの結果
	 * @param listGenryo	：DB情報　原料情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeResData_genryo(

			 RequestResponsKindBean resData
			,RequestResponsKindBean resDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		int cntRow = 0;
		int cntKote = 0;
		int intCdKote = -1;
		String strHenkou = "";
		String strTemp1 = "";
		String strTemp2 = "";
		
		try{

			for (int i = 0 ; i < listGenryo.size(); i++){

				Object[] items = (Object[]) listGenryo.get(i);
				
				//工程行の設定
				if (intCdKote == toInteger(items[3])){
					
				}else{

					cntRow++;
					cntKote++;
					intCdKote = toInteger(items[3]);
					
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", toString(items[3]));
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", toString(items[18]));
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", toString(items[17]));
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
					
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 START -----------------------------------												
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_kojo_tanka", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kaisha_tanka", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kojo_tanka", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_kojo_budomari", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kaisha_budomari", "");
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kojo_budomari", "");
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 END -------------------------------------												
					
				}

				cntRow++;

				// ----------------------------------------------------------------------+
				// 処理結果①	flg_return
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "flg_return", "true");
				// ----------------------------------------------------------------------+
				// 処理結果②	msg_error
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "msg_error", "");
				// ----------------------------------------------------------------------+
				// 処理結果③	nm_class
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "nm_class", "");
				// ----------------------------------------------------------------------+
				// 処理結果⑥	no_errmsg
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "no_errmsg", "");
				// ----------------------------------------------------------------------+
				// 処理結果④	cd_error
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_error", "");
				// ----------------------------------------------------------------------+
				// 処理結果⑤	msg_system
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "msg_system", "");
				// ----------------------------------------------------------------------+
				// 工程順		sort_kotei
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "sort_kotei", toString(items[13]));
				// ----------------------------------------------------------------------+
				// 工程CD		cd_kotei
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_kotei", toString(items[3]));
				// ----------------------------------------------------------------------+
				// 工程SEQ		seq_kotei
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "seq_kotei", toString(items[4]));
				// ----------------------------------------------------------------------+
				// 原料CD		cd_genryo
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_genryo", toString(items[10]));
				// ----------------------------------------------------------------------+
				// 原料名		nm_genryo
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "nm_genryo", toString(items[5]));
				// ----------------------------------------------------------------------+
				// 変更連絡		henko_renraku
				// ----------------------------------------------------------------------+
//				if (toDouble(items[11], -1) == toDouble(items[15], -1)
//					&&	toDouble(items[12], -1) == toDouble(items[16], -1)){
//					strHenkou = "";
//
//				}else{
//					strHenkou = "◎";
//					
//				}

//				//原料CDの先頭1文字を取得
//				strTemp1 = toString(items[10]);
//				if(!strTemp1.equals("")){
//					strTemp1 = strTemp1.substring(0, 1);
//					
//				}
//				//原料名の先頭1文字を取得
//				strTemp2 = toString(items[5]);
//				if(!strTemp1.equals("")){
//					strTemp1 = strTemp1.substring(0, 1);
//					
//				}
//				if (strTemp1.equals("N") || strTemp2.equals("★")){
//					//原料CDの先頭が”N”（新規原料）　又は、原料名の先頭が”★”（3ヶ月間未使用原料）の場合
//
//					//研究所試作の値と比較する。
//					if (toDouble(items[11], -1) != toDouble(items[15], -1)
//						||	toDouble(items[12], -1) != toDouble(items[16], -1)){
//						strHenkou = "◎";
//
//					}else{
//						strHenkou = "";
//
//					}
//					
//				}else{
//					//新規原料、3ヶ月間未使用原料、以外の場合
//					
//					//マスタの値と比較する。
//					if (toDouble(items[11], -1) != toDouble(items[8], -1)
//						||	toDouble(items[12], -1) != toDouble(items[9], -1)){
//						strHenkou = "◎";
//
//					}else{
//						strHenkou = "";
//
//					}
//					
//				}
				
				//マスタの値と比較する。
				if (toDouble(items[11], -1) != toDouble(items[8], -1)
					||	toDouble(items[12], -1) != toDouble(items[9], -1)){
					strHenkou = "◎";

				}else{
					strHenkou = "";

				}
				
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "henko_renraku", strHenkou);
				
//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.32
				// ----------------------------------------------------------------------+
				// 単価			tanka
				// ----------------------------------------------------------------------+
//				if(toDouble(items[11], -1) > -1){
//					resData.addFieldVale(
//							"genryo", "rec" + toString(cntRow)
//							, "tanka"
//							, toString(
//									toDouble(items[11], -1)
//									, 2
//									, 2
//									, true
//									, "")
//							);
//					
//				}else{
//					resData.addFieldVale("genryo", "rec" + toString(cntRow)
//							, "tanka", "");
//					
//				}
				if(toDouble(items[11], -1) > -1){
					resData.addFieldVale(
							"genryo", "rec" + toString(cntRow)
							, "tanka"
							, toString(
									toDouble(items[11], -1)
									, 2
									, 2
									, true
									, "0.00")
							);
					
				}else{
					resData.addFieldVale("genryo", "rec" + toString(cntRow)
							, "tanka", "0.00");
					
				}
				// ----------------------------------------------------------------------+
				// 歩留			budomari
				// ----------------------------------------------------------------------+
//				if(toDouble(items[12], -1) > -1){
//					resData.addFieldVale(
//							"genryo", "rec" + toString(cntRow)
//							, "budomari"
//							, toString(
//									toDouble(items[12], -1)
//									, 2
//									, 2
//									, true
//									, "")
//							);
//					
//				}else{
//					resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari"
//							, "");
//					
//				}
				if(toDouble(items[12], -1) > -1){
					resData.addFieldVale(
							"genryo", "rec" + toString(cntRow)
							, "budomari"
							, toString(
									toDouble(items[12], -1)
									, 2
									, 2
									, true
									, "0.00")
							);
					
				}else{
					resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari"
							, "0.00");
					
				}
//mod end --------------------------------------------------------------------------------
				
				// ----------------------------------------------------------------------+
				// 原料行フラグ	genryo_fg
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "genryo_fg", "1");
				
				
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 START -----------------------------------							
				// ----------------------------------------------------------------------+
				// 単価　工場名		                nm_kojo_tanka
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "nm_kojo_tanka", toString(items[19]));
				// ----------------------------------------------------------------------+
				// 単価　会社コード（マスタ）		cd_kaisha_tanka
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_kaisha_tanka", toString(items[20]));
				// ----------------------------------------------------------------------+
				// 単価　工場コード（マスタ）		cd_kojo_tanka
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_kojo_tanka", toString(items[21]));
				// ----------------------------------------------------------------------+
				// 歩留り　工場名		                nm_kojo_budomari
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "nm_kojo_budomari", toString(items[22]));
				// ----------------------------------------------------------------------+
				// 歩留り　会社コード（マスタ）		cd_kaisha_budomari
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_kaisha_budomari", toString(items[23]));
				// ----------------------------------------------------------------------+
				// 歩留り　工場コード（マスタ）		cd_kojo_budomari
				// ----------------------------------------------------------------------+
				resData.addFieldVale("genryo", "rec" + toString(cntRow)
						, "cd_kojo_budomari", toString(items[24]));
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 END -------------------------------------
				
				
			}
			
			//工程重量合計行の設定
			for (int i = 0 ; i < cntKote; i++){

				cntRow++;
				
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", toString(i + 1) + "工程(Kg)");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
				resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
				
			}
			
			//合計重量行の設定

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "合計重量(Kg)");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			
			//合計仕上重量の設定

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "合計仕上重量(Kg)");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			//総酸の設定

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "総酸(％)");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			//食塩の設定

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "食塩(％)");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			//水相中酸度の設定

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "水相中酸度");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			//水相中食塩の設定

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "水相中食塩");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
			//水相中酢酸の設定

			cntRow++;
			
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "flg_return", "true");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_class", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "no_errmsg", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_error", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "msg_system", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "sort_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "seq_kotei", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "cd_genryo", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "nm_genryo", "水相中酢酸");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "henko_renraku", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "tanka", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "budomari", "");
			resData.addFieldVale("genryo", "rec" + toString(cntRow), "genryo_fg", "2");
			
		
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
			
			//試作情報検索

			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  T310.cd_shain ");		//0
			strSQL.append(" ,T310.nen ");			//1
			strSQL.append(" ,T310.no_oi ");			//2
			strSQL.append(" ,T310.su_iri ");		//3
			strSQL.append(" ,T310.genka ");			//4
			strSQL.append(" ,T310.baika ");			//5
			strSQL.append(" ,T310.cd_genka_tani ");	//6
			strSQL.append(" ,T310.fg_keisan ");		//7
			
			//【QP@00342】
			strSQL.append(" ,T310.no_eda ");			//8
			
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shisakuhin AS T310 ");
			strSQL.append(" LEFT JOIN tr_shisakuhin AS T110 ");
			strSQL.append(" ON  T310.cd_shain = T110.cd_shain ");
			strSQL.append(" AND T310.nen      = T110.nen ");
			strSQL.append(" AND T310.no_oi    = T110.no_oi ");
			strSQL.append(" WHERE ");
			strSQL.append("     T310.cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T310.nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T310.no_oi    = " + toString(cd_shisaku_oi) + " ");
			//【QP@00342】
			strSQL.append(" AND T310.no_eda    = " + toString(cd_shisaku_eda) + " ");
			//DB検索
			listShisaku = this.searchDB.dbSearch(strSQL.toString());
			
			//ｻﾝﾌﾟﾙ情報検索
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append("  T131.cd_shain ");			//0
			strSQL.append(" ,T131.nen ");				//1
			strSQL.append(" ,T131.no_oi ");				//2
			strSQL.append(" ,T131.seq_shisaku ");		//3
			strSQL.append(" ,T131.juryo_shiagari_g ");	//4
			strSQL.append(" ,T141.zyusui ");			//5
			strSQL.append(" ,T141.zyuabura ");			//6
			strSQL.append(" ,T141.hiju ");				//7
			
			//課題管理台帳　No116　：　試作日付→登録日に変更　TT.Nishigawa START -----------------------------------
			strSQL.append(" ,CONVERT(VARCHAR,T331.dt_toroku,111) ");	//8
			//課題管理台帳　No116　：　試作日付→登録日に変更　TT.Nishigawa END    -----------------------------------
			
			strSQL.append(" ,T131.nm_sample ");			//9
			strSQL.append(" ,CONVERT(VARCHAR,T331.dt_shisan,111) ");	//10
			strSQL.append(" ,T141.gokei ");				//11
			strSQL.append(" ,T331.budomari ");			//12
			strSQL.append(" ,T331.heikinjuten ");		//13
			strSQL.append(" ,T331.cs_kotei ");			//14
			strSQL.append(" ,T331.kg_kotei ");			//15
			// ADD 2013/11/1 QP@30154 okano start
			strSQL.append(" ,T331.cs_rieki ");			//16
			strSQL.append(" ,T331.kg_rieki ");			//17
			// ADD 2013/11/1 QP@30154 okano end
			
//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.38			
			strSQL.append(" ,T133.no_chui ");			//18
			strSQL.append(" ,T133.chuijiko ");			//19
//mod end --------------------------------------------------------------------------------
			
			//【QP@00342】
			strSQL.append(" ,T331.fg_chusi ");			//20
//ADD 2013/07/09 ogawa 【QP@30151】No.13 start
			strSQL.append(" ,T331.fg_koumokuchk ");		//21 項目固定チェック
//ADD 2013/07/09 ogawa 【QP@30151】No.13 end
			
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start	
			strSQL.append(" ,T332.zyusui ");			//22 固定項目表示用：充填量水相（ｇ）
			strSQL.append(" ,T332.zyuabura ");			//23 固定項目表示用：充填量油相（ｇ）
			strSQL.append(" ,T332.gokei ");				//24 固定項目表示用：合計量（ｇ） 
			strSQL.append(" ,T332.hiju ");				//25 固定項目表示用：比重
			strSQL.append(" ,T332.reberu ");			//26 固定項目表示用：レベル量（㎏）
			strSQL.append(" ,T332.hijukasan ");			//27 固定項目表示用：比重加算量（㎏） 
			strSQL.append(" ,T332.cs_genryo ");			//28 固定項目表示用：原料費（円）/ケース
			strSQL.append(" ,T332.cs_zairyohi ");		//29 固定項目表示用：材料費（円）/ケース
			strSQL.append(" ,T332.cs_genka ");			//30 固定項目表示用：原価計（円）/ケース
			strSQL.append(" ,T332.ko_genka ");			//31 固定項目表示用：原価計（円）/個
			strSQL.append(" ,T332.kg_genryo ");			//32 固定項目表示用：原料費（円）/㎏ 
			strSQL.append(" ,T332.kg_zairyohi ");		//33 固定項目表示用：材料費（円）/㎏ 
			strSQL.append(" ,T332.kg_genka ");			//34 固定項目表示用：原価計（円）/㎏
			strSQL.append(" ,T332.baika ");				//35 固定項目表示用：売価 
			strSQL.append(" ,T332.arari ");				//36 固定項目表示用：粗利（％） 
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
			
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisaku AS T131 ");
			strSQL.append(" LEFT JOIN tr_shisan_shisaku AS T331 ");
			strSQL.append(" ON  T331.cd_shain    = T131.cd_shain ");
			strSQL.append(" AND T331.nen         = T131.nen ");
			strSQL.append(" AND T331.no_oi       = T131.no_oi ");
			strSQL.append(" AND T331.seq_shisaku = T131.seq_shisaku ");
			strSQL.append(" LEFT JOIN tr_genryo AS T141 ");
			strSQL.append(" ON  T131.cd_shain    = T141.cd_shain ");
			strSQL.append(" AND T131.nen         = T141.nen ");
			strSQL.append(" AND T131.no_oi       = T141.no_oi ");
			strSQL.append(" AND T131.seq_shisaku = T141.seq_shisaku ");
			
//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.38			
			strSQL.append(" LEFT JOIN dbo.tr_cyuui AS T133 ");
			strSQL.append(" ON T131.cd_shain = T133.cd_shain ");	
			strSQL.append(" AND T131.nen = T133.nen ");	
			strSQL.append(" AND T131.no_oi = T133.no_oi ");	
			strSQL.append(" AND T131.no_chui = T133.no_chui ");	
//mod end --------------------------------------------------------------------------------
			
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start				
			strSQL.append(" LEFT JOIN tr_shisan_shisaku_kotei AS T332 ");
			strSQL.append(" ON T332.cd_shain = T331.cd_shain ");
			strSQL.append(" AND T332.nen = T331.nen ");
			strSQL.append(" AND T332.no_oi = T331.no_oi ");
			strSQL.append(" AND T332.seq_shisaku = T331.seq_shisaku ");
			strSQL.append(" AND T332.no_eda = T331.no_eda ");
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end			
			
			//【QP@00342】
			strSQL.append(" WHERE ");
			strSQL.append("     T331.cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T331.nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T331.no_oi    = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND T331.no_eda    = " + toString(cd_shisaku_eda) + " ");
			strSQL.append(" AND T131.flg_shisanIrai    = 1 ");
			strSQL.append(" ORDER BY ");
			strSQL.append("  T131.cd_shain ");
			strSQL.append(" ,T131.nen ");
			strSQL.append(" ,T131.no_oi ");
			strSQL.append(" ,T131.sort_shisaku ");
			
			//DB検索
			listSanpuru = this.searchDB.dbSearch(strSQL.toString());
			
			//原料情報
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL生成
			strSQL.append(" SELECT ");
			strSQL.append(" T120.cd_shain ");		//0
			strSQL.append(" ,T120.nen ");			//1
			strSQL.append(" ,T120.no_oi ");			//2
			strSQL.append(" ,T120.cd_kotei ");		//3
			strSQL.append(" ,T120.seq_kotei ");		//4
			strSQL.append(" ,T320.nm_genryo ");		//5
			strSQL.append(" ,T320.tanka_ins ");		//6
			strSQL.append(" ,T320.budomari_ins ");	//7
			strSQL.append(" ,T320.tanka_ma ");		//8
			strSQL.append(" ,T320.budomar_ma ");	//9
			strSQL.append(" ,T120.cd_genryo ");		//10
			strSQL.append(" ,ISNULL(T320.tanka_ins,T320.tanka_ma) AS tankaD ");		//11
			strSQL.append(" ,ISNULL(T320.budomari_ins,T320.budomar_ma) AS budomarD ");	//12
			strSQL.append(" ,T120.sort_kotei ");	//13
			strSQL.append(" ,T120.sort_genryo ");	//14
			strSQL.append(" ,T120.tanka ");			//15
			strSQL.append(" ,T120.budomari ");		//16
			strSQL.append(" ,T120.nm_kotei ");		//17
			strSQL.append(" ,M302.nm_literal ");	//18
			
			
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 START -----------------------------------
			
			//単価　工場名
			strSQL.append(" ,CASE WHEN T320.tanka_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,M104_tanka.nm_busho) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as nm_kojo_tanka ");	//19
			
			//単価　会社コード（マスタ）
			strSQL.append(" ,CASE WHEN T320.tanka_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,T320.cd_kaisha_tanka) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as cd_kaisha_tanka ");	//20

			//単価　工場コード（マスタ）
			strSQL.append(" ,CASE WHEN T320.tanka_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,T320.cd_kojo_tanka) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as cd_kojo_tanka ");	//21

			//歩留り　工場名
			strSQL.append(" ,CASE WHEN T320.budomari_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,M104_budomari.nm_busho) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as nm_kojo_budomari ");	//22

			//歩留り　会社コード（マスタ）
			strSQL.append(" ,CASE WHEN T320.budomari_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,T320.cd_kaisha_budomari) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as cd_kaisha_budomari ");	//23

			//歩留り　工場コード（マスタ）
			strSQL.append(" ,CASE WHEN T320.budomari_ins IS NULL THEN ");
			strSQL.append(" 	CONVERT(VARCHAR,T320.cd_kojo_budomari) ");
			strSQL.append("  ELSE ");
			strSQL.append(" 	CONVERT(VARCHAR,NULL) ");
			strSQL.append("  END as cd_kojo_budomari ");	//24
			
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 END --------------------------------------
			
			
			strSQL.append(" FROM ");
			strSQL.append("           tr_haigo AS T120 ");
			strSQL.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			strSQL.append(" ON  T120.cd_shain = T320.cd_shain ");
			strSQL.append(" AND T120.nen      = T320.nen ");
			strSQL.append(" AND T120.no_oi    = T320.no_oi ");
			strSQL.append(" AND T120.cd_kotei = T320.cd_kotei ");
			strSQL.append(" AND T120.seq_kotei= T320.seq_kotei ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kote' ");
			strSQL.append(" AND T120.zoku_kotei  = M302.cd_literal ");
			
			
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 START -----------------------------------			
			
			//単価　工場名
			strSQL.append(" LEFT JOIN ma_busho AS M104_tanka ");
			strSQL.append(" 	ON T320.cd_kaisha_tanka = M104_tanka.cd_kaisha ");
			strSQL.append(" 	AND T320.cd_kojo_tanka = M104_tanka.cd_busho ");

			//歩留り　工場名
			strSQL.append(" LEFT JOIN ma_busho AS M104_budomari ");
			strSQL.append(" 	ON T320.cd_kaisha_budomari = M104_budomari.cd_kaisha ");
			strSQL.append(" 	AND T320.cd_kojo_budomari = M104_budomari.cd_busho ");
			
//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/2/16 END --------------------------------------
			
			
//			strSQL.append(" WHERE ");
//			strSQL.append("     T120.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
//			strSQL.append(" AND T120.nen       = " + toString(cd_shisaku_nen) + " ");
//			strSQL.append(" AND T120.no_oi     = " + toString(cd_shisaku_oi) + " ");
//			strSQL.append(" ORDER BY ");
//			strSQL.append("  T120.cd_shain ");
//			strSQL.append(" ,T120.nen ");
//			strSQL.append(" ,T120.no_oi ");
//			strSQL.append(" ,T120.sort_kotei ");
//			strSQL.append(" ,T120.sort_genryo ");
			
			//【QP@00342】
			strSQL.append(" WHERE ");
			strSQL.append("     T320.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T320.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T320.no_oi     = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND T320.no_eda     = " + toString(cd_shisaku_eda) + " ");
			strSQL.append(" ORDER BY ");
			strSQL.append("  T120.cd_shain ");
			strSQL.append(" ,T120.nen ");
			strSQL.append(" ,T120.no_oi ");
			strSQL.append(" ,T120.sort_kotei ");
			strSQL.append(" ,T120.sort_genryo ");
			
			//DB検索
			listGenryo = this.searchDB.dbSearch(strSQL.toString());

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
			
			// ADD 2013/7/2 shima【QP@30151】No.37 start
			//サンプル毎の基本情報
			
			strSQL = null;
			strSQL = new StringBuffer();
			
			//SQL生成
			strSQL.append("SELECT T313.cd_shain , ");
			strSQL.append("T313.nen , ");
			strSQL.append("T313.no_oi , ");
			strSQL.append("T313.no_eda , ");
			strSQL.append("T313.seq_shisaku, ");
			strSQL.append("T313.genka , ");
			strSQL.append("T313.baika , ");
			strSQL.append("T313.cd_genka_tani ");
			strSQL.append("FROM tr_shisan_kihonjoho AS T313 ");
			strSQL.append("LEFT JOIN tr_shisakuhin AS T110 ");
			strSQL.append("ON T313.cd_shain = T110.cd_shain ");
			strSQL.append("AND T313.nen = T110.nen ");
			strSQL.append("AND T313.no_oi = T110.no_oi ");
			strSQL.append("INNER JOIN tr_shisaku AS T131 ");
			strSQL.append("ON T313.cd_shain = T131.cd_shain ");
			strSQL.append("AND T313.nen = T131.nen ");
			strSQL.append("AND T313.no_oi = T131.no_oi ");
			strSQL.append("AND T313.seq_shisaku = T131.seq_shisaku ");
			strSQL.append("WHERE T313.cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append("AND T313.nen = " + toString(cd_shisaku_nen) + " ");
			strSQL.append("AND T313.no_oi = " + toString(cd_shisaku_oi) + " ");
			strSQL.append("AND T313.no_eda = " + toString(cd_shisaku_eda) + " ");
			strSQL.append("ORDER BY ");
			strSQL.append("  T131.cd_shain ");
			strSQL.append(" ,T131.nen ");
			strSQL.append(" ,T131.no_oi ");
			strSQL.append(" ,T131.sort_shisaku ");
			
			//DB検索
			listKihonSub = this.searchDB.dbSearch(strSQL.toString());
			// ADD 2013/7/2 shima【QP@30151】No.37 end
		
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

			Object[] items = (Object[]) listShisaku.get(0);
			
			// ----------------------------------------------------------------------+
			// 試作CD-社員CD	cd_shain
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "cd_shain", toString(items[0]));
			// ----------------------------------------------------------------------+
			// 試作CD-年		nen
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "nen", toString(items[1]));
			// ----------------------------------------------------------------------+
			// 試作CD-追番	no_oi
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "no_oi", toString(items[2]));
			
			//【QP@00342】
			// ----------------------------------------------------------------------+
			// 試作CD-枝番	no_eda
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "no_eda", toString(items[8]));
			
			
			// ----------------------------------------------------------------------+
			// 入り数			irisu
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "irisu", toString(items[3]));
			// ----------------------------------------------------------------------+
			// 処理モード		mode
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "mode", "1");
			// ----------------------------------------------------------------------+
			// 原価希望		kibo_genka
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_genka", toString(items[4]));
			// ----------------------------------------------------------------------+
			// 売価希望		kibo_baika
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_baika", toString(items[5]));
			// ----------------------------------------------------------------------+
			// 原価単位CD	kibo_genka_tani
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_genka_tani", toString(items[6]));
			// ----------------------------------------------------------------------+
			// 計算項目（固定費/ケースor固定費/kg）	ragio_kesu_kg
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "ragio_kesu_kg", toString(items[7]));
		
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　DB情報取得に失敗しました。 ");
			
		}finally{
			
		}
		
	}
	/**
	 * 原料リクエストの生成
	 * @param reqDataKeisan	：編集後リクエスト
	 * @param listGenryo	：原料情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan_Genryo(

			 RequestResponsKindBean reqDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try{

			for(int i = 0 ; i < listGenryo.size(); i++){

				Object[] items = (Object[]) listGenryo.get(i);

				// ----------------------------------------------------------------------+
				// 工程CD	cd_kotei
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("genryo", "rec" + toString(i)
						, "cd_kotei", toString(items[3]));
				// ----------------------------------------------------------------------+
				// 工程SEQ	seq_kotei
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("genryo", "rec" + toString(i)
						, "seq_kotei", toString(items[4]));
				// ----------------------------------------------------------------------+
				// 原料CD	cd_genryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("genryo", "rec" + toString(i)
						, "cd_genryo", toString(items[10]));
				// ----------------------------------------------------------------------+
				// 単価		tanka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("genryo", "rec" + toString(i)
						, "tanka", toString(items[11]));
				// ----------------------------------------------------------------------+
				// 歩留		budomari
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("genryo", "rec" + toString(i)
						, "budomari", toString(items[12]));
			}
		
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　DB情報取得に失敗しました。 ");
			
		}finally{
			
		}
		
	}
	/**
	 * 計算リクエストの生成
	 * @param reqDataKeisan	:生成結果
	 * @param listSanpuru	:サンプル情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan_Keisan(

			 RequestResponsKindBean reqDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try{

			for(int i = 0 ; i < listSanpuru.size(); i++){

				Object[] items = (Object[]) listSanpuru.get(i);
				
				// ----------------------------------------------------------------------+
				// 試作SEQ		seq_shisaku
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "seq_shisaku", toString(items[3]));
				// ----------------------------------------------------------------------+
				// 有効歩留（％）	yuuko_budomari
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "yuuko_budomari", toString(items[12]));
				// ----------------------------------------------------------------------+
				// 平均充填量（ｇ）	heikinjyutenryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "heikinjyutenryo", toString(items[13]));
				// ----------------------------------------------------------------------+
				// 固定費/ケース	kesu_kotehi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kesu_kotehi", toString(items[14]));
				// ----------------------------------------------------------------------+
				// 固定費/KG		kg_kotehi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kg_kotehi", toString(items[15]));
				// ADD 2013/11/1 QP@30154 okano start
				// ----------------------------------------------------------------------+
				// 固定費/ケース	kesu_kotehi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kesu_rieki", toString(items[16]));
				// ----------------------------------------------------------------------+
				// 固定費/KG		kg_kotehi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kg_rieki", toString(items[17]));
				// ADD 2013/11/1 QP@30154 okano end
				
				
//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.38			
				// ----------------------------------------------------------------------+
				// 製造工程版		seizokotei_han
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "seizokotei_han", toString(items[18]));
				
				// ----------------------------------------------------------------------+
				// 製造工程		seizokotei_shosai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "seizokotei_shosai", toString(items[19]));
//mod end --------------------------------------------------------------------------------
				
				//【QP@00342】
				// ----------------------------------------------------------------------+
				// 試算中止	fg_chusi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "fg_chusi", toString(items[20]));
				
//ADD 2013/07/9 ogawa 【QP@30151】No.13 start
				// ----------------------------------------------------------------------+
				// 項目固定チェック	fg_koumokuchk
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "fg_koumokuchk", toString(items[21]));
//ADD 2013/07/09 ogawa 【QP@30151】No.13 end
				
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start	
				// ----------------------------------------------------------------------+
				// 充填量水相（ｇ）	zyusui
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "zyusui", toString(items[22]));
				
				// ----------------------------------------------------------------------+
				// 充填量油相（ｇ）	zyuabura
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "zyuabura", toString(items[23]));
				
				// ----------------------------------------------------------------------+
				// 合計量（ｇ） 	gokei
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "gokei", toString(items[24]));
				
				// ----------------------------------------------------------------------+
				// 比重 			hiju
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "hiju", toString(items[25]));
				
				// ----------------------------------------------------------------------+
				// レベル量（㎏） 	reberu
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "reberu", toString(items[26]));
				
				// ----------------------------------------------------------------------+
				// 比重加算量（㎏）  hijukasan
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "hijukasan", toString(items[27]));
				
				// ----------------------------------------------------------------------+
				// 原料費（円）/ケース  cs_genryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "cs_genryo", toString(items[28]));
				
				// ----------------------------------------------------------------------+
				// 材料費（円）/ケース  cs_zairyohi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "cs_zairyohi", toString(items[29]));
				
				// ----------------------------------------------------------------------+
				// 原価計（円）/ケース  cs_genka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "cs_genka", toString(items[30]));
				
				// ----------------------------------------------------------------------+
				// 原価計（円）/個  ko_genka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "ko_genka", toString(items[31]));
				
				// ----------------------------------------------------------------------+
				// 原料費（円）/㎏   kg_genryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kg_genryo", toString(items[32]));
				
				// ----------------------------------------------------------------------+
				// 材料費（円）/㎏   kg_zairyohi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kg_zairyohi", toString(items[33]));
				
				// ----------------------------------------------------------------------+
				// 原価計（円）/㎏   kg_genka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "kg_genka", toString(items[34]));
				
				// ----------------------------------------------------------------------+
				// 売価   			 baika
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "baika", toString(items[35]));
				
				// ----------------------------------------------------------------------+
				// 粗利（％）    	 arari
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("keisan", "rec" + toString(i)
						, "arari", toString(items[36]));
				
//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
				
			}
		
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　DB情報取得に失敗しました。 ");
			
		}finally{
			
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
				// 更新者ID	ID_koshin
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "ID_koshin", toString(items[14]));
				// ----------------------------------------------------------------------+
				// 更新日付	DT_koshin
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "DT_koshin", toString(items[15]));

			}
		
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算原料表示　DB情報取得に失敗しました。 ");
			
		}finally{
			
		}
		
	}
	
	// ADD 2013/7/2 shima【QP@30151】No.37 start
	/**
	 * 基本情報サブリクエストの生成
	 * @param reqDataKeisan	:生成結果
	 * @param listSanpuru	:サンプル情報
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKihon_Sub(

			 RequestResponsKindBean reqDataKeisan
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try{

			for(int i = 0 ; i < listKihonSub.size(); i++){

				Object[] items = (Object[]) listKihonSub.get(i);
				// ----------------------------------------------------------------------+
				// 試作CD-社員CD	cd_shain
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "cd_shain", toString(items[0]));
				// ----------------------------------------------------------------------+
				// 試作CD-年		nen
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "nen", toString(items[1]));
				// ----------------------------------------------------------------------+
				// 試作CD-追番	no_oi
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "no_oi", toString(items[2]));
				// ----------------------------------------------------------------------+
				// 試作CD-枝番	no_eda
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "no_eda", toString(items[3]));
				// ----------------------------------------------------------------------+
				// 試作SEQ		seq_shisaku
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "seq_shisaku", toString(items[4]));
				// ----------------------------------------------------------------------+
				// 原価希望		kibo_genka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "kibo_genka", toString(items[5]));
				// ----------------------------------------------------------------------+
				// 売価希望		kibo_baika
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "kibo_baika", toString(items[6]));
				// ----------------------------------------------------------------------+
				// 原価単位CD	kibo_genka_tani
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("kihonsub", "rec" + toString(i)
						, "kibo_genka_tani", toString(items[7]));
				
			}
		
		}catch(Exception e){
			//例外のスロー
			this.em.ThrowException(e, "原価試算基本情報サブ表示　DB情報取得に失敗しました。 ");
			
		}finally{
			
		}
		
	}
	// ADD 2013/7/2 shima【QP@30151】No.37 end
	
}