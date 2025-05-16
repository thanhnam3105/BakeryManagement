package jp.co.blueflag.shisaquick.jws.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import jp.co.blueflag.shisaquick.jws.base.ShisanData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/***********************************************************************
 * 
 * 試算履歴データ管理
 *  : リスト用の試算履歴データに関する情報を管理する
 *
 **********************************************************************/
public class ShisanRirekiKanriData extends DataBase {

	/*******************************************************************
	 * メンバ
	 ******************************************************************/
	private ArrayList aryShisanRirekiData;		//試算履歴データ配列
	private ArrayList aryShisanKakuteiNo;		//試算確定サンプルNoデータ配列
	private int intLastShisakuSeq;				//最終試算確定　試作SEQ
	private XmlData xdtData;						//XMLデータ
	private ExceptionBase ex;						//エラーハンドリング	

	/*******************************************************************
	 * 
	 * コンストラクタ
	 * 
	 ******************************************************************/
	public ShisanRirekiKanriData() throws ExceptionBase{
		//スーパークラスのコンストラクタ呼び出し
		super();	
		
		try {
			this.aryShisanRirekiData = new ArrayList();
			this.aryShisanKakuteiNo = new ArrayList();
			this.intLastShisakuSeq = 0;
			
		} catch ( Exception e ) {			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試算履歴データ管理のコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}

	/*******************************************************************
	 * 0001.データ設定
	 *  : XMLデータより試作テーブルデータを生成する。
	 * @param xdtData : XMLデータ
	 * @throws ExceptionBase 
	 ******************************************************************/
	public void setShisanRirekiData(XmlData xdtSetXml) throws ExceptionBase{
		this.xdtData = xdtSetXml;
		
		try {
			//試算履歴データ配列を初期化
			this.aryShisanRirekiData.clear();
			
			/**********************************************************
			 *　ShisanRirekiデータ格納
			 *********************************************************/
			//機能IDの設定
			String strKinoId = "SA840";
			
			//全体配列取得
			ArrayList userData = xdtData.GetAryTag(strKinoId);
			
			//機能配列取得
			ArrayList kinoData = (ArrayList)userData.get(0);

			//テーブル配列取得
			ArrayList tableData = (ArrayList)kinoData.get(1);

			//レコード取得
			for(int i=1; i<tableData.size(); i++){
				//　１レコード取得
				ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
				
				ShisanData shisanRirekiData = new ShisanData();
				
				//試算履歴データへ格納
				for(int j=0; j<recData.size(); j++){
						
					//　項目名取得
					String recNm = ((String[])recData.get(j))[1];
					//　項目値取得
					String recVal = ((String[])recData.get(j))[2];
	
					/*****************ShisanRirekiデータへ値セット*********************/
					//　試作CD-社員CD
					 if(recNm == "cd_shain"){
						 shisanRirekiData.setDciShisakuUser(checkNullDecimal(recVal));
						
					//　試作CD-年
					}if(recNm == "nen"){
						shisanRirekiData.setDciShisakuYear(checkNullDecimal(recVal));
					
					//　試作CD-追番
					}if(recNm == "no_oi"){
						shisanRirekiData.setDciShisakuNum(checkNullDecimal(recVal));
						
					//　試作SEQ
					}if(recNm == "seq_shisaku"){
						shisanRirekiData.setIntShisakuSeq(checkNullInt(recVal));
						
					//　試作表示順
					}if(recNm == "sort_rireki"){
						shisanRirekiData.setIntRirekiNo(checkNullInt(recVal));

					//　サンプルNO（名称）
					}if(recNm == "nm_sample"){
						shisanRirekiData.setStrSampleNo(checkNullString(recVal));

					//　試算日付
					}if(recNm == "dt_shisan"){
						shisanRirekiData.setStrShisanHi(checkNullString(recVal));
						
					//　登録者ID
					}if(recNm == "id_toroku"){
						shisanRirekiData.setDciTorokuId(new BigDecimal(recVal));
						
					//　登録日付
					}if(recNm == "dt_toroku"){
						shisanRirekiData.setStrTorokuHi(checkNullString(recVal));
						
					}
					
				}
				//　試算履歴データ配列へ追加
				this.aryShisanRirekiData.add(shisanRirekiData);
			}
			
		} catch (ExceptionBase e ) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試算履歴データのデータ設定に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/*******************************************************************
	 * 0002.データ設定(試算確定サンプルNo)
	 *  : XMLデータより試作テーブルデータを生成する。
	 * @param xdtData : XMLデータ
	 * @throws ExceptionBase 
	 ******************************************************************/
	public void setShisanKakuteiNoData(XmlData xdtSetXml) throws ExceptionBase{
		this.xdtData = xdtSetXml;
		
		try {
			//試算履歴データ配列を初期化
			this.aryShisanKakuteiNo.clear();
			
			/**********************************************************
			 *　ShisanRirekiデータ格納
			 *********************************************************/
			//機能IDの設定
			String strKinoId = "SA830";
			
			//全体配列取得
			ArrayList userData = xdtData.GetAryTag(strKinoId);
			
			//機能配列取得
			ArrayList kinoData = (ArrayList)userData.get(0);

			//テーブル配列取得
			ArrayList tableData = (ArrayList)kinoData.get(1);

			//レコード取得
			for(int i=1; i<tableData.size(); i++){
				//　１レコード取得
				ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
				
				ShisanData shisanRirekiData = new ShisanData();
				
				//試算履歴データへ格納
				for(int j=0; j<recData.size(); j++){
						
					//　項目名取得
					String recNm = ((String[])recData.get(j))[1];
					//　項目値取得
					String recVal = ((String[])recData.get(j))[2];
	
					/*****************ShisanRirekiデータへ値セット*********************/
					//　試作SEQ
					if(recNm == "seq_shisaku"){
						shisanRirekiData.setIntShisakuSeq(checkNullInt(recVal));
						
					//　サンプルNO（名称）
					}if(recNm == "nm_sample"){
						shisanRirekiData.setStrSampleNo(checkNullString(recVal));

					//　試算日付
					}if(recNm == "dt_shisan"){
						shisanRirekiData.setStrShisanHi(checkNullString(recVal));
						
					//　最終試算確定　試作SEQ
					}if(recNm == "seq_shisaku_last"){
						this.intLastShisakuSeq = checkNullInt(recVal);
						
					}
					
				}
				//　試算履歴データ配列へ追加
				this.aryShisanKakuteiNo.add(shisanRirekiData);
			}
			
		} catch (ExceptionBase e ) {
			throw e;
			
		} catch (Exception e) {
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("試算確定サンプルNoデータのデータ設定に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/*******************************************************************
	 * 
	 * 0003.試算確定サンプルNo 試算データ取得処理
	 * @param intSelectedIndex : 選択番号
	 * @throws ExceptionBase 
	 * 
	 ******************************************************************/
	public ShisanData SearchShisanKakuteiData(int intSelectedIndex) throws ExceptionBase{

		//新規リストインスタンス生成
		ShisanData ret = null;
		
		try{
			
			//試算データを取得
			if ( this.aryShisanKakuteiNo.size() > 0
					&& intSelectedIndex >= 0 ) {
				ret = (ShisanData)this.aryShisanKakuteiNo.get(intSelectedIndex);
			}
			
		} catch (Exception e) {
			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("試算確定サンプルNo 試算データ取得が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			
		}
		return ret;
		
	}

	/*******************************************************************
	 * 
	 * 0004.最終試算確定対象の試算データ検索処理
	 * @throws ExceptionBase 
	 * 
	 ******************************************************************/
	public ShisanData SearchLastShisanData() throws ExceptionBase{

		//新規リストインスタンス生成
		ShisanData ret = new ShisanData();
		
		try{
			
			Iterator ite = this.aryShisanKakuteiNo.iterator();
			
			//リスト件数分ループ
			while(ite.hasNext()){
				//試算データオブジェクト取得
				ShisanData shisanData = (ShisanData)ite.next();
				
				//引数：試作SEQと試算データオブジェクト：試作SEQが一致した場合
				if (this.intLastShisakuSeq  == shisanData.getIntShisakuSeq()){
					//返却リストに試算データオブジェクト追加
					ret = shisanData;
					
				}
			}
			
		} catch (Exception e) {
			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("最終試算確定対象の試算データ検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			
		}
		return ret;
		
	}

	/*******************************************************************
	 * 
	 * 最終試算確定対象の試作データ 設定
	 * @param _intLastShisakuSeq : 試作SEQ
	 * @throws ExceptionBase 
	 * 
	 ******************************************************************/
	public void SetLastShisanData(int _intLastShisakuSeq) throws ExceptionBase {
		
		try{
			
			//最終試算確定 試作SEQを設定する
			this.intLastShisakuSeq = _intLastShisakuSeq;

			//最終試算確定 試算日を設定する
			Iterator ite = this.aryShisanKakuteiNo.iterator();
			
			//リスト件数分ループ
			while(ite.hasNext()){
				//試算データオブジェクト取得
				ShisanData shisanData = (ShisanData)ite.next();
				
				//引数：試作SEQと試算データオブジェクト：試作SEQが一致した場合
				if (this.intLastShisakuSeq  == shisanData.getIntShisakuSeq()){
					//返却リストに試算データオブジェクト追加
					shisanData.setStrShisanHi(DataCtrl.getInstance().getTrialTblData().getSysDate());
					
				}
				
			}
			
		} catch (Exception e) {
			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("最終試算確定対象の試算データ検索が失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			
		}
		
	}

	/*******************************************************************
	 * 
	 * 試算分析データ配列 ゲッター
	 * @return aryShisanRirekiData : 試算分析データ配列の値を返す
	 * 
	 ******************************************************************/
	public ArrayList getAryShisanRirekiData() {
		return aryShisanRirekiData;
	}

	/*******************************************************************
	 * 
	 * 試算分析データ配列 ゲッター
	 * @return aryShisanRirekiData : 試算分析データ配列の値を返す
	 * 
	 ******************************************************************/
	public ArrayList getAryShisanKakuteiData() {
		return aryShisanKakuteiNo;
	}

	/*******************************************************************
	 * 
	 * 空文字チェック（String）
	 * @param val : 対象値
	 * 
	 ******************************************************************/
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

	/*******************************************************************
	 * 
	 * 空文字チェック（Decimal）
	 * @param val : 対象値
	 * 
	 ******************************************************************/
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

	/*******************************************************************
	 * 
	 * 空文字チェック（Int）
	 * @param val : 対象値
	 * 
	 ******************************************************************/
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
