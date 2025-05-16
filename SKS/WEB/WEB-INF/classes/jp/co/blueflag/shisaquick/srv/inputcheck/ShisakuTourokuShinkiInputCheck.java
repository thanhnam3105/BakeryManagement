package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * 【JW030】 試作データ画面リクエスト登録（新規） インプットチェック
 *
 */
public class ShisakuTourokuShinkiInputCheck extends InputCheck {

	/**
	 * コンストラクタ
	 *  : 試作データ画面 初期表示時インプットチェック用コンストラクタ
	 */
	public ShisakuTourokuShinkiInputCheck() {
		//基底クラスのコンストラクタ
		super();

	}

	/**
	 * インプットチェック管理
	 *  : 各データチェック処理を管理する。
	 * @param requestData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execInputCheck(
			RequestData checkData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//ユーザー情報退避
		super.userInfoData = _userInfoData;

		try {
			//USERINFOのインプットチェックを行う。
			super.userInfoCheck(checkData);

			//SA420のインプットチェックを行う。
			exclusiveControlKeyCheck(checkData);
			
			//SA490のインプットチェックを行う。
			insertValueCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 排他制御項目インプットチェック
	 *  : SA420のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void exclusiveControlKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//実行区分＝1（処理する）の場合
			if (checkData.GetValueStr("SA420", 0, 0, "kubun_ziko").equals("1")) {
				//ユーザIDの必須チェックを行う。
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "id_user"),"ユーザID");
	
			    //排他区分の必須チェックを行う。
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "kubun_haita"),"排他区分");
				//試作コードの必須チェックを行う。
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "cd_shain"),"試作CD-社員CD");
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "nen"),"試作CD-年");
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "no_oi"),"試作CD-追番");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 登録項目チェック
	 *  : SA490のインプットチェックを行う。
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//テーブル：T110試作品テーブルのインプットチェック（試作品テーブル項目チェック）を行う。
			this.shisakuhinInserValueCheck(checkData);
			//テーブル：T120配合テーブルのインプットチェック（配合テーブル項目チェック）を行う。
			this.haigoInsertValueCheck(checkData);
			//テーブル：T131試作テーブルのインプットチェック（試作テーブル項目チェック）を行う。
			this.shisakuInsertValueCheck(checkData);
			//テーブル：T132試作リストテーブルのインプットチェック（試作リストテーブル項目チェック）を行う。
			this.shisakuListInsertValueCheck(checkData);
			//テーブル：T133製造工程テーブルのインプットチェック（製造工程テーブル項目チェック）を行う。
			this.seizoKoteiInsertValueCheck(checkData);
//			//テーブル：T140原価資材テーブルのインプットチェック（原価資材テーブル項目チェック）を行う。
//			this.genkaShizaiInsertValueCheck(checkData);
//			//テーブル：T141原価原料テーブルのインプットチェック（原価原料テーブル項目チェック）を行う。
//			this.genkaGenryoInsertValueCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	
	/**
	 * 試作品テーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakuhinInserValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA490";
			String strTableNm = "tr_shisakuhin";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				///
				///必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");					//試作コード-年
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");				//試作コード-追番
				super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"試作データ画面 品名");
			    ///
				///入力桁数チェック
			    ///
				super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_irai"),"試作データ画面 依頼番号",8);
//				super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"試作データ画面 品名",100);
				super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"試作データ画面 品名",100,50);
				
				try {
					///
					///必須項目の入力チェック
					///
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kaisha"),"指定工場-会社CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kojo"),"指定工場-工場CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shubetu"),"種別CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_shubetu"),"種別No");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_group"),"グループCD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_team"),"チームCD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yoryo"),"容量");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_tani"),"容量単位CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "su_iri"),"入り数");
				    
				    ///
					///入力桁数チェック
				    ///
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_shubetu"),"種別No",2);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "tokuchogenryo"),"特徴原料",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "youto"),"用途",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_eigyo"),"担当営業CD",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hoho_sakin"),"殺菌方法",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "youki"),"容器包材",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "yoryo"),"容量",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "su_iri"),"入り数",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "shomikikan"),"賞味期間",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "genka"),"原価",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "baika"),"売価",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "buturyo"),"想定物量",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "dt_hatubai"),"発売時期",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "uriage_k"),"計画売上",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "rieki_k"),"計画利益",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "uriage_h"),"販売後売上",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "rieki_h"),"販売後利益",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_nisugata"),"荷姿CD",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo"),"総合ﾒﾓ",500);
					
				} catch (ExceptionUser ex) {
					//返却メッセージの編集
					ex.setUserMsg("試作データ画面 試作表③." + ex.getUserMsg());
					throw ex;
				} finally {
				}
			}
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
	}

	/**
	 * 配合テーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void haigoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA490";
			String strTableNm = "tr_haigo";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				String strAddMsg = "";

				///
				/// 必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");					//試作コード-年
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");				//試作コード-追番

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kotei"),"試作データ画面 試作表① 工程CD");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_kotei"),"試作データ画面 試作表① 工程SEQ");
			    
				//  [試作表①]追加メッセージの編集
				strAddMsg = "試作データ画面 試作表① [sort_kotei=" + checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_kotei") + "] :";
				
				///
				/// 入力桁数チェック
				///
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_kotei"),strAddMsg+"工程名",60);
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"原料名称",60);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"原料名称",60,30);

				///
				/// 数値項目については、数字範囲チェック
				///
			    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka")),0,99999.99,strAddMsg+"単価");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),0,999.99,strAddMsg+"歩留");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),0,999.99,strAddMsg+"油含有率");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),0,999.99,strAddMsg+"酢酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),0,999.99,strAddMsg+"食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),0,999.99,strAddMsg+"総酸");
				}

				///
				/// 小数点以下が入力される項目については、小数桁数チェック
				///
			    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka")),2,strAddMsg+"単価");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),2,strAddMsg+"歩留");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),2,strAddMsg+"油含有率");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),2,strAddMsg+"酢酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),2,strAddMsg+"食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),2,strAddMsg+"総酸");
				}
				
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 試作テーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakuInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA490";
			String strTableNm = "tr_shisaku";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				String strAddMsg = "";
				
				///
				///必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");					//試作コード-年
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");				//試作コード-追番
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku"),"試作データ画面 .試作SEQ");
			    if ( checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "cd_tani").equals("ml") ) { 
			    	//試作品テーブルの容量単位CDが「ml」に設定されている場合
			    	super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),"試作テーブル 比重");
			    }

			    //試作テーブル.サンプルNO(名称)の取得
				String nm_sample = checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_sample");
				if ( nm_sample.equals("") ) {
					nm_sample = "名称無し";		//サンプルNOが空の場合、"名称無し"と格納する
				}
				//試作テーブル.試作表示順の取得
				String sort_shisaku = checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_shsiaku");
			    
				//  [試作表①]追加メッセージの編集
				strAddMsg = "試作データ画面 試作表① [sort_shisaku=" + sort_shisaku + "] :";
				
				///
				/// [試作表①]入力桁数チェック[試作表①]
				///
			    super.sizeCheckLen(nm_sample,"サンプルNO（名称）",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo"),strAddMsg + "メモ",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_shisaku"),strAddMsg + "試作メモ",400);

				///
				/// [試作表①]日付チェック
				///
//			    super.dateCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "dt_shisaku"), strAddMsg + "試作日付");

			    
				//  [試作表②]追加メッセージの編集
				strAddMsg = "試作データ画面 試作表② [sort_shisaku=" + sort_shisaku + ", nm_sample=" + nm_sample + "] :";
				
				///
				/// [試作表②]入力桁数チェック
				///
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_sakusei"),strAddMsg + "作成メモ",150);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nendo"),strAddMsg + "粘度",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hyoka"),strAddMsg + "評価",200);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title1"),strAddMsg + "フリー①タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value1"),strAddMsg + "フリー①内容",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title2"),strAddMsg + "フリー②タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value2"),strAddMsg + "フリー②内容",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title3"),strAddMsg + "フリー③タイトル",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value3"),strAddMsg + "フリー③内容",20);

				///
				/// [試作表②]数値項目については、数字範囲チェック
				///
			    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),0,999.99,strAddMsg + "総酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),0,999.99,strAddMsg + "食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso")),0,999.99,strAddMsg + "水相中酸度");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso")),0,999.99,strAddMsg + "水相中食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso")),0,999.99,strAddMsg + "水相中酢酸");
				}

				///
				/// [試作表②]小数点以下が入力される項目については、小数桁数チェック
				///
			    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),2,strAddMsg + "総酸");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),2,strAddMsg + "食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso")),2,strAddMsg + "水相中酸度");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso")),2,strAddMsg + "水相中食塩");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso")),2,strAddMsg + "水相中酢酸"); 
				}
			    
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	
	/**
	 * 試作テーブル.試作表示順の取得
	 * @param strShisakuSeq : 試作SEQ
	 * @param checkData : リクエストデータ
	 * @return 試作テーブル.試作表示順
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String getShisakuSort(String strShisakuSeq, RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetShisakuSort = "";
		String strKinoNm = "SA490";
		String strTableNm_sub = "tr_shisaku";

		try {
	    	//試作表示順の取得
			for ( int j=0; j<checkData.GetRecCnt(strKinoNm, strTableNm_sub); j++ ) {
				//試作リスト.試作SEQ = 試作.試作SEQ
				if ( strShisakuSeq.equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "seq_shisaku")) ) {
					strRetShisakuSort = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "sort_shisaku");
					break;
				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		return strRetShisakuSort;
	}

	/**
	 * 配合テーブル.工程順の取得
	 * @param strKoteiCd : 工程CD
	 * @param strKoteiSeq : 工程SEQ
	 * @param checkData : リクエストデータ
	 * @return 配合テーブル.工程順
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String getKoteiSort(String strKoteiCd, String strKoteiSeq, RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetKoteiSort = "";
		String strKinoNm = "SA490";
		String strTableNm_sub = "tr_haigo";

		try {
			//工程順の取得
			for ( int j=0; j<checkData.GetRecCnt(strKinoNm, strTableNm_sub); j++ ) {
				//試作リスト.工程CD = 配合.工程CD & 試作リスト.工程SEQ = 配合.工程SEQ 
				if ( strKoteiCd.equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "cd_kotei"))
						&& strKoteiSeq.equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "seq_kotei")) ) {
					strRetKoteiSort = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "sort_kotei");
					break;
				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		return strRetKoteiSort;
	}
	
	/**
	 * 試作リストテーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakuListInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA490";
			String strTableNm = "tr_shisaku_list";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
				///
				///必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");					//試作コード-年
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");				//試作コード-追番

			    //試作リスト.試作SEQ
			    String strShisakuSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku");
			    super.hissuInputCheck(strShisakuSeq,"試作データ 画面.試作SEQ");
			    //試作リスト.工程CD
			    String strKouteiCd = checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kotei");
			    super.hissuInputCheck(strShisakuSeq,"試作データ 画面.工程CD");
			    //試作リスト.工程SEQ
			    String strKouteiSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_kotei");
			    super.hissuInputCheck(strShisakuSeq,"試作データ 画面.工程SEQ");

			    //※　下記の項目は値がNULL(空文字)ではない場合、チェックを行う。
			    if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "quantity").isEmpty()) ) {
				    try {
						
						///
						/// 数値項目については、数字範囲チェック
				    	/// [0～99999.xxxx]
						///
				    	int intKetaSu = 0;
				    	if ( !checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "keta_shosu").isEmpty() ) {
						    intKetaSu = Integer.parseInt(checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "keta_shosu"));	
				    	}
					    String strKetaSu = "99999";
					    if ( intKetaSu > 0 ) {
					    	strKetaSu += ".";
						    for( int j=0; j<intKetaSu; j++ ) {
						    	strKetaSu += "9";
						    }
					    }
				    	super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "quantity")),0,Double.parseDouble(strKetaSu),"量");
	
						///
						/// 小数点以下が入力される項目については、小数桁数チェック
						///
				    	if ( intKetaSu > 0 ) {
				    		super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "quantity")),intKetaSu,"量");
				    	}
					    
				    } catch ( ExceptionUser ex) {		//チェックに引っかかった場合
						String strMsg = "";
						String sort_shisaku = "";
						String sort_kotei = "";
						
				    	//試作表示順の取得
						sort_shisaku = getShisakuSort(strShisakuSeq, checkData);
						
						//工程順の取得
						sort_kotei = getKoteiSort(strKouteiCd, strKouteiSeq, checkData);
						
						//メッセージの設定
						strMsg = "試作データ 試作表①";
						strMsg +=  " [sort_shisaku=" + sort_shisaku;
						strMsg += ",sort_kotei=" + sort_kotei;
						strMsg += "] :" + ex.getUserMsg();
						
						ex.setUserMsg(strMsg);
						throw ex;
				    } finally {
				    }
			    }
			    
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * 製造工程テーブル 登録項目チェック
	 * @param requestData : リクエストデータ
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void seizoKoteiInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA490";
			String strTableNm = "tr_cyuui";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				///
				///必須項目の入力チェック
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作データ画面 試作CD-社員CD");		//試作コード-社員CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作データ画面 試作CD-年");					//試作コード-年
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作データ画面 試作CD-追番");				//試作コード-追番
			    //製造工程 試作SEQ
			    String strShisakuSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku");
			    super.hissuInputCheck(strShisakuSeq,"試作データ画面 試作SEQ");
			    //製造工程 試作SEQ
			    String strChuiNo = checkData.GetValueStr(strKinoNm, strTableNm, i, "no_chui");
		    	super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_chui"),"試作データ画面 注意事項No");
			    
			    try {
					///
					/// 入力桁数チェック
					///
				    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "chuijiko"),"注意事項",400);
				    
			    } catch (ExceptionUser ex) {

			    	//試作表示順の取得
			    	String sort_shisaku = getShisakuSort(strShisakuSeq, checkData);
								    	
					//返却メッセージの編集
					ex.setUserMsg("試作データ画面 試作表① [sort_shisaku=" + sort_shisaku + ", no_chui=" + strChuiNo + "] :" + ex.getUserMsg());
					throw ex;
				} finally {
				}
			    
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

//	/**
//	 * 原価資材テーブル 登録項目チェック
//	 * @param requestData : リクエストデータ
//	 * @throws ExceptionWaning 
//	 * @throws ExceptionUser 
//	 * @throws ExceptionSystem 
//	 */
//	private void genkaShizaiInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//
//		try {
//			String strKinoNm = "SA490";
//			String strTableNm = "tr_shizai";
//			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
//				///
//				///必須項目の入力チェック
//				///
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作CD-社員CD");		//試作コード-社員CD
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作CD-年");					//試作コード-年
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作CD-追番");				//試作コード-追番
//			    
//			}
//		} catch (Exception e) {
//			this.em.ThrowException(e, "");
//		} finally {
//			
//		}
//	}
//
//	/**
//	 * 原価原料テーブル 登録項目チェック
//	 * @param requestData : リクエストデータ
//	 * @throws ExceptionWaning 
//	 * @throws ExceptionUser 
//	 * @throws ExceptionSystem 
//	 */
//	private void genkaGenryoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//
//		try {
//			String strKinoNm = "SA490";
//			String strTableNm = "tr_genryo";
//			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
//				///
//				///必須項目の入力チェック
//				///
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"試作CD-社員CD");		//試作コード-社員CD
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"試作CD-年");					//試作コード-年
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"試作CD-追番");				//試作コード-追番
//			    
//			}
//		} catch (Exception e) {
//			this.em.ThrowException(e, "");
//		} finally {
//			
//		}
//	}	
	

}
