package jp.co.blueflag.shisaquick.jws.manager;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.base.MaterialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;


/***********************************************************************
 *
 * 原料マスタデータ保持
 *  : リスト用の原料データに関する情報を管理する
 *
 **********************************************************************/
public class MaterialMstData extends DataBase {


	/*******************************************************************
	 * メンバ
	 *
	 ******************************************************************/
	private MaterialData mtdtSelect;		//リスト内で選択された原料データ(分析値入力画面時一覧選択データ)
	private MaterialData mtdtTaihi;		//退避用の原料データ（分析値入力画面時結果データ）
	private ArrayList aryMateData;		//原料データ配列
	private ArrayList aryMateChkData;		//分析原料確認データ配列
	private XmlData xdtData;				//XMLデータ
	private ExceptionBase ex;				//エラーハンドリング

	/*******************************************************************
	 * コンストラクタ
	 *
	 ******************************************************************/
	public MaterialMstData() throws ExceptionBase{
		//スーパークラスのコンストラクタ呼び出し
		super();

		try {
			this.mtdtSelect = null;
			this.mtdtSelect = new MaterialData();
			this.mtdtTaihi = new MaterialData();
			this.aryMateData = new ArrayList();
			this.aryMateChkData = new ArrayList();

		} catch ( Exception e ) {
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("原料マスタデータ保持のコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * (原料)XMLデータの設定
	 * @param xmlData : XMLデータ
	 * @param kinoId : 機能ID
	 */
	public void setMaterialData(XmlData xmlData,String kinoId) throws ExceptionBase{
		this.xdtData = xmlData;

		 try{
			this.aryMateData.clear();

			//　XMLデータ格納
			String strKinoId = kinoId;

			//全体配列取得
			ArrayList mateData = xdtData.GetAryTag(strKinoId);

			//機能配列取得
			ArrayList kinoData = (ArrayList)mateData.get(0);

			//テーブル配列取得
			ArrayList tableData = (ArrayList)kinoData.get(1);


			//レコード取得
			for(int i=1; i<tableData.size(); i++){
			//　１レコード取得
			ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
				//　原料データ生成
				MaterialData mateInit = new MaterialData();

				//原料データへ格納
				for(int j=0; j<recData.size(); j++){

					//　項目名取得
					String recNm = ((String[])recData.get(j))[1];
					//　項目値取得
					String recVal = ((String[])recData.get(j))[2];

					/*****************原料データへ値セット*********************/
					//　会社コード
					 if(recNm == "cd_kaisha"){
						mateInit.setIntKaishacd(checkNullInt(recVal));

					//　部署コード
					}if(recNm == "cd_busho"){
						mateInit.setIntBushocd(checkNullInt(recVal));

					//　原料コード
					}if(recNm == "cd_genryo"){
						mateInit.setStrGenryocd(checkNullString(recVal));

					//　会社名
					}if(recNm == "nm_kaisha"){
						mateInit.setStrKaishanm(checkNullString(recVal));

					// 部署名
					}if(recNm == "nm_busho"){
						mateInit.setStrBushonm(checkNullString(recVal));

					//　原料名
					}if(recNm == "nm_genryo"){
						mateInit.setStrGenryonm(checkNullString(recVal));

					//　酢酸
					}if(recNm == "ritu_sakusan"){
						mateInit.setDciSakusan(checkNullDecimal(recVal));

					//　食塩
					}if(recNm == "ritu_shokuen"){
						mateInit.setDciShokuen(checkNullDecimal(recVal));
// ADD start 20121003 QP@20505 No.24
					//　ＭＳＧ
					}if(recNm == "ritu_msg"){
						mateInit.setDciMsg(checkNullDecimal(recVal));
// ADD end 20121003 QP@20505 No.24

					//　総酸
					}if(recNm == "ritu_sousan"){
						mateInit.setDciSousan(checkNullDecimal(recVal));

					//　油含有率
					}if(recNm == "ritu_abura"){
						mateInit.setDciGanyu(checkNullDecimal(recVal));

					//　表示案
					}if(recNm == "hyojian"){
						mateInit.setStrHyoji(checkNullString(recVal));

					//　添加物
					}if(recNm == "tenkabutu"){
						mateInit.setStrTenka(checkNullString(recVal));

					//　栄養計算食品番号1
					}if(recNm == "no_eiyo1"){
						mateInit.setStrEiyono1(checkNullString(recVal));

					//　栄養計算食品番号2
					}if(recNm == "no_eiyo2"){
						mateInit.setStrEiyono2(checkNullString(recVal));

					//　栄養計算食品番号3
					}if(recNm == "no_eiyo3"){
						mateInit.setStrEiyono3(checkNullString(recVal));

					//　栄養計算食品番号4
					}if(recNm == "no_eiyo4"){
						mateInit.setStrEiyono4(checkNullString(recVal));

					//　栄養計算食品番号5
					}if(recNm == "no_eiyo5"){
						mateInit.setStrEiyono5(checkNullString(recVal));

					//　割合1
					}if(recNm == "wariai1"){
						mateInit.setStrWariai1(checkNullString(recVal));

					//　割合2
					}if(recNm == "wariai2"){
						mateInit.setStrWariai2(checkNullString(recVal));

					//　割合3
					}if(recNm == "wariai3"){
						mateInit.setStrWariai3(checkNullString(recVal));

					//　割合4
					}if(recNm == "wariai4"){
						mateInit.setStrWariai4(checkNullString(recVal));

					//　割合5
					}if(recNm == "wariai5"){
						mateInit.setStrWariai5(checkNullString(recVal));

					//　メモ
					}if(recNm == "memo"){
						mateInit.setStrMemo(checkNullString(recVal));

					//　入力日
					}if(recNm == "dt_toroku"){
						mateInit.setStrNyurokuhi(checkNullString(recVal));

					//　入力者ID
					}if(recNm == "id_toroku"){
						mateInit.setDciNyurokucd(checkNullDecimal(recVal));

					//　入力者名
					}if(recNm == "nm_toroku"){
						mateInit.setStrNyurokunm(checkNullString(recVal));

//					//　分析情報確認フラグ
//					}if(recNm == "分析情報確認フラグ"){


					//　確認日
					}if(recNm == "dt_kakunin"){
						mateInit.setStrKakuninhi(checkNullString(recVal));

					//　確認者ID
					}if(recNm == "id_kakunin"){
						mateInit.setDciKakunincd(checkNullDecimal(recVal));

					//　確認者名
					}if(recNm == "nm_kakunin"){
						mateInit.setStrKakuninnm(checkNullString(recVal));

					//　廃止区分
					}if(recNm == "kbn_haishi"){
						mateInit.setIntHaisicd(checkNullInt(recVal));

					//　確定コード
					}if(recNm == "cd_kakutei"){
						mateInit.setStrkakuteicd(checkNullString(recVal));

					//　最終購入日
					}if(recNm == "dt_konyu"){
						mateInit.setStrKonyu(checkNullString(recVal));

					//　単価
					}if(recNm == "tanka"){
						mateInit.setDciTanka(checkNullDecimal(recVal));

					//　歩留
					}if(recNm == "budomari"){
						mateInit.setDciBudomari(checkNullDecimal(recVal));

					//　登録者ID
					}if(recNm == "id_toroku"){
						mateInit.setDciTorokuId(checkNullDecimal(recVal));

					//　登録日付
					}if(recNm == "dt_toroku"){
						mateInit.setStrTorokuDt(checkNullString(recVal));

					//　登録者名
					}if(recNm == "nm_toroku"){
						mateInit.setStrTorokuNm(checkNullString(recVal));

					//　更新者ID
					}if(recNm == "id_koshin"){
						mateInit.setDciKosinId(checkNullDecimal(recVal));

					//　更新日付
					}if(recNm == "dt_koshin"){
						mateInit.setStrKosinDt(checkNullString(recVal));

					//　更新者名
					}if(recNm == "nm_koshin"){
						mateInit.setStrKosinNm(checkNullString(recVal));

					//　表示件数
					}if(recNm == "list_max_row"){
						mateInit.setIntListRowMax(checkNullInt(recVal));

					//　レコード件数
					}if(recNm == "max_row"){
						mateInit.setIntMaxRow(checkNullInt(recVal));

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
					// 使用実績フラグ
					}if(recNm == "flg_shiyo"){
						mateInit.setIntShiyoFlg(checkNullInt(recVal));

					// 未使用フラグ
					}if(recNm == "flg_mishiyo"){
						mateInit.setIntMishiyoFlg(checkNullInt(recVal));
//add end --------------------------------------------------------------------------------------
					}

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.31
					if(recNm == "ma_budomari"){
						mateInit.setMa_dciBudomari(checkNullDecimal(recVal));
					}
//add end --------------------------------------------------------------------------------------

				}
				//　原料配列へ追加
				this.aryMateData.add(mateInit);
			}

		} catch(ExceptionBase e) {
			throw e;

		} catch(Exception e) {
			e.printStackTrace();
			 //エラー設定
			 ex = new ExceptionBase();
			 ex.setStrErrCd("");
			 ex.setStrErrmsg("原料マスタデータ保持の(原料)XMLデータ設定が失敗しました");
			 ex.setStrErrShori(this.getClass().getName());
			 ex.setStrMsgNo("");
			 ex.setStrSystemMsg(e.getMessage());
			 throw ex;

		 }finally{

		 }
	 }

	/**
	 * (分析原料確認)XMLデータの設定
	 * @param xmlData : XMLデータ
	 */
	public void setMaterialChkData(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;

		 try{
			this.aryMateChkData.clear();

			//　XMLデータ格納
			String strKinoId = "SA590";

			//全体配列取得
			ArrayList mateData = xdtData.GetAryTag(strKinoId);

			//機能配列取得
			ArrayList kinoData = (ArrayList)mateData.get(0);

			//テーブル配列取得
			ArrayList tableData = (ArrayList)kinoData.get(1);


			//レコード取得
			for(int i=1; i<tableData.size(); i++){
				//　１レコード取得
				ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
				//　原料データ生成
				MaterialData mateInit = new MaterialData();

				//原料データへ格納
				for(int j=0; j<recData.size(); j++){

					//　項目名取得
					String recNm = ((String[])recData.get(j))[1];
					//　項目値取得
					String recVal = ((String[])recData.get(j))[2];

					/*****************原料データへ値セット*********************/
					//　会社コード
					 if(recNm == "cd_kaisha"){
						mateInit.setIntKaishacd(checkNullInt(recVal));

					//　部署コード
					}if(recNm == "cd_busho"){
						mateInit.setIntBushocd(checkNullInt(recVal));

					//　原料コード
					}if(recNm == "cd_genryo"){
						mateInit.setStrGenryocd(checkNullString(recVal));

					//　原料名
					}if(recNm == "nm_genryo"){
						mateInit.setStrGenryonm(checkNullString(recVal));

					//　単価
					}if(recNm == "tanka"){
						mateInit.setDciTanka(checkNullDecimal(recVal));

					//　歩留
					}if(recNm == "budomari"){
						mateInit.setDciBudomari(checkNullDecimal(recVal));

					//　酢酸
					}if(recNm == "ritu_sakusan"){
						mateInit.setDciSakusan(checkNullDecimal(recVal));

					//　食塩
					}if(recNm == "ritu_shokuen"){
						mateInit.setDciShokuen(checkNullDecimal(recVal));
// ADD start 20121003 QP@20505 No.24
					//　ＭＳＧ
					}if(recNm == "ritu_msg"){
						mateInit.setDciMsg(checkNullDecimal(recVal));
// ADD end 20121003 QP@20505 No.24
					//　総酸
					}if(recNm == "ritu_sousan"){
						mateInit.setDciSousan(checkNullDecimal(recVal));

					//　油含有率
					}if(recNm == "ritu_abura"){
						mateInit.setDciGanyu(checkNullDecimal(recVal));

					//　会社名
					}if(recNm == "nm_kaisha"){
						mateInit.setStrKaishanm(checkNullString(recVal));

					//　部署名
					}if(recNm == "nm_busho"){
						mateInit.setStrBushonm(checkNullString(recVal));
					}

				}
				//　原料配列へ追加
				this.aryMateChkData.add(mateInit);
			}

		} catch(ExceptionBase e) {
			throw e;

		} catch(Exception e) {
			 //エラー設定
			 ex = new ExceptionBase();
			 ex.setStrErrCd("");
			 ex.setStrErrmsg("原料マスタデータ保持の(分析原料確認)XMLデータの設定が失敗しました");
			 ex.setStrErrShori(this.getClass().getName());
			 ex.setStrMsgNo("");
			 ex.setStrSystemMsg(e.getMessage());
			 throw ex;

		 }finally{

		 }
	 }


	 /*******************************************************************
	  * 原料データ配列追加
	  * @param mtdtAddData : 原料データ
	  *
	  ******************************************************************/
	 public void AddAryGenryo(MaterialData mtdtAddData) {
		 this.aryMateData.add(mtdtAddData);
	 }

	 /*******************************************************************
	  * 原料データ配列追加
	  * @param mtdtAddData : 原料データ
	  *
	  ******************************************************************/
	 public void AddAryGenryoChk(MaterialData mtdtAddData) {
		 this.aryMateChkData.add(mtdtAddData);
	 }


	 /*******************************************************************
	  * 選択原料データ更新
	  * @param strGenryocd : 原料コード
	  * @param intKaishacd : 会社コード
	  *
	  ******************************************************************/
	 public void Selectmate(String strGenryocd, int intKaishacd ) throws ExceptionBase{
		 try{
			 this.mtdtSelect = this.getSiteiMate(strGenryocd, intKaishacd);

		 }catch(ExceptionBase ex){
			 throw ex;
		 }catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("原料マスタデータ保持の選択原料データ更新に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		 }finally{

		 }
	 }


	 /*******************************************************************
	  * 選択原料データ取得
	  * @return 原料データ
	  *
	  ******************************************************************/
	 public MaterialData getSelMate() {
		 return this.mtdtSelect;
	 }
	 public void setSelMate(MaterialData mt) {
		 this.mtdtSelect = mt;
	 }


	 /*******************************************************************
	  * 選択原料データ検索
	  * @param strGenryocd : 原料コード
	  * @param intKaishacd : 会社コード
	  * @return : 原料データ
	  *
	  ******************************************************************/
	 public MaterialData getSiteiMate(String strGenryocd, int intKaishacd) throws ExceptionBase{
		String strSelGenryo = strGenryocd;			//　引数：原料コード取得
		int intSelKaisha = intKaishacd;				//　引数：会社コード取得
		MaterialData getMate = new MaterialData();	//　返却用原料データ生成
		boolean intHitfg = false;					//　対象Fg

		try{
			//　原料マスタ検索
			for(int i=0;i<this.aryMateData.size();i++){
				MaterialData selMate = (MaterialData)this.aryMateData.get(i);	//　検索対象データ取得
				String strMateGenryo = selMate.getStrGenryocd();	//　原料コード取得
				int intMateKaisha = selMate.getIntKaishacd();		//　会社コード取得

				//　引数とマスタの原料コード、会社コードを比較
				if((strSelGenryo.equals(strMateGenryo.toString())) && (intSelKaisha == intMateKaisha)){
					getMate = selMate;		//　返却用原料データに対象データを設定
					intHitfg = true;		//　対象データ有
				}
			}
			//　検索結果が０件の場合
			if(!intHitfg){
				//エラー設定
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				ex.setStrErrmsg("選択原料データ検索に失敗：０件です");
				ex.setStrErrShori("MaterialMstData:getSiteiMate");
				ex.setStrMsgNo("");
				ex.setStrSystemMsg("UserError");
				throw ex;
			}
		}catch(ExceptionBase ex){
			throw ex;

		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("選択原料データ検索に失敗しました");
			ex.setStrErrShori("MaterialMstData:getSiteiMate");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}

		//　検索結果を返却
		return getMate;
	 }

	/*******************************************************************
	 * 退避用の原料データ ゲッター
	 * @return mtdtTaihi : 退避用の原料データの値を返す
	 *
	 ******************************************************************/
	public MaterialData getMtdtTaihi() {
		return mtdtTaihi;
	}


	/*******************************************************************
	 * 退避用の原料データ セッター
	 * @param _mtdtTaihi : 退避用の原料データの値を格納する
	 *
	 ******************************************************************/
	public void setMtdtTaihi(MaterialData _mtdtTaihi) {
		this.mtdtTaihi = _mtdtTaihi;
	}


	/*******************************************************************
	 * 原料データ配列 ゲッター
	 * @return aryMateData : 原料データ配列の値を返す
	 *
	 ******************************************************************/
	public ArrayList getAryMateData() {
		return aryMateData;
	}


	/*******************************************************************
	 * 原料データ配列 セッター
	 * @param _aryMateData : 原料データ配列の値を格納する
	 *
	 ******************************************************************/
	public void setAryMateData(ArrayList _aryMateData) {
		this.aryMateData = _aryMateData;
	}


	/*******************************************************************
	 * 分析原料確認データ配列 ゲッター
	 * @return aryMateData : 分析原料確認データ配列の値を返す
	 *
	 ******************************************************************/
	public ArrayList getAryMateChkData() {
		return aryMateChkData;
	}


	/*******************************************************************
	 * 分析原料確認データ配列 セッター
	 * @param _aryMateChkData : 分析原料確認データ配列の値を格納する
	 *
	 ******************************************************************/
	public void setAryMateChkData(ArrayList _aryMateChkData) {
		this.aryMateChkData = _aryMateChkData;
	}

	/*******************************************************************
	 * 原料データ配列 確認
	 *
	 ******************************************************************/
	public void DispMateData() {
		for(int i=0;i<this.aryMateData.size();i++){
			MaterialData dispMate = new MaterialData();
			dispMate = (MaterialData)this.aryMateData.get(i);
			//　テスト表示
			System.out.println("********************* " + (i+1) + "件目 ***************************");
			dispMate.dispMateData();
		}
	}

	/**
	 * 空文字チェック（String）
	 */
	public String checkNullString(String val){
		String ret = null;
		try{
			//値が空文字でない場合
			if(!val.equals("")){
				ret = val;
			}
		}catch(Exception e){

		}finally{

		}
		return ret;
	}

	/**
	 * 空文字チェック（Decimal）
	 */
	public BigDecimal checkNullDecimal(String val){
		BigDecimal ret = null;
		try{
			//値が空文字でない場合
			if(!val.equals("")){
				ret = new BigDecimal(val);
			}
		}catch(Exception e){

		}finally{

		}
		return ret;
	}

	/**
	 * 空文字チェック（Int）
	 */
	public int checkNullInt(String val){
		int ret = -1;
		try{
			//値が空文字でない場合
			if(!val.equals("")){
				ret = Integer.parseInt(val);
			}
		}catch(Exception e){

		}finally{

		}
		return ret;
	}
}
