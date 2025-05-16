package jp.co.blueflag.shisaquick.jws.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;

/**
 * 
 * 部署データ保持
 *  : 部署データの管理を行う
 *
 */
public class BushoData extends DataBase {

	private ArrayList artBushoCd;			//部署コード
	private ArrayList aryBushoNm;		//部署名
	
	private XmlData xdtData;				//XMLデータ
	private ExceptionBase ex;				//エラーハンドリング
	
	/**
	 * コンストラクタ
	 * @param strKinoId : 機能ID
	 */
	public BushoData() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		this.artBushoCd = new ArrayList();
		this.aryBushoNm = new ArrayList();
	}
	
	/**
	 * XMLデータの設定
	 * @param xmlData : XMLデータ
	 */
	public void setBushoData(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;
		
		try{

			//項目のクリア
			this.artBushoCd.clear();
			this.aryBushoNm.clear();
			
			/**********************************************************
			 *　リテラルデータ格納
			 *********************************************************/
			
			//機能IDの設定
			String strKinoId = "SA290"; 
			
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
				
				//ユーザデータへ格納
				for(int j=0; j<recData.size(); j++){
					
					//　項目名取得
					String recNm = ((String[])recData.get(j))[1];
					//　項目値取得
					String recVal = ((String[])recData.get(j))[2];
					
					/*****************リテラルデータへ値セット*********************/
					if ( recNm == "cd_busho" ) {
						//リテラルコード
						this.artBushoCd.add(recVal);
					} else if ( recNm == "nm_busho" ) {
						//リテラル名
						this.aryBushoNm.add(recVal);
					}
				}
			}
			
		}catch(ExceptionBase e){
			throw e;		
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("部署データの初期化に失敗しました");
			ex.setStrErrShori("KaishaData:setKaishaData");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
	
	/**
	 * 【QP@00342】XMLデータの設定　営業部署取得
	 * @param xmlData : XMLデータ
	 */
	public void setBushoData_Eigyo(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;
		
		try{

			//項目のクリア
			this.artBushoCd.clear();
			this.aryBushoNm.clear();
			
			/**********************************************************
			 *　リテラルデータ格納
			 *********************************************************/
			
			//機能IDの設定
			String strKinoId = "FGEN2070"; 
			
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
				
				//ユーザデータへ格納
				for(int j=0; j<recData.size(); j++){
					
					//　項目名取得
					String recNm = ((String[])recData.get(j))[1];
					//　項目値取得
					String recVal = ((String[])recData.get(j))[2];
					
					/*****************リテラルデータへ値セット*********************/
					if ( recNm == "cd_busho" ) {
						//リテラルコード
						this.artBushoCd.add(recVal);
					} else if ( recNm == "nm_busho" ) {
						//リテラル名
						this.aryBushoNm.add(recVal);
					}
				}
			}
			
		}catch(ExceptionBase e){
			throw e;		
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("部署データの初期化に失敗しました");
			ex.setStrErrShori("KaishaData:setKaishaData");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}

	/**
	 * 部署コード ゲッター
	 * @return artBushoCd : 部署コードの値を返す
	 */
	public ArrayList getArtBushoCd() {
		return artBushoCd;
	}
	/**
	 * 部署コード セッター
	 * @param _artBushoCd : 部署コードの値を格納する
	 */
	public void setArtBushoCd(ArrayList _artBushoCd) {
		this.artBushoCd = _artBushoCd;
	}

	/**
	 * 部署名 ゲッター
	 * @return aryBushoNm : 部署名の値を返す
	 */
	public ArrayList getAryBushoNm() {
		return aryBushoNm;
	}
	/**
	 * 部署名 セッター
	 * @param _aryBushoNm : 部署名の値を格納する
	 */
	public void setAryBushoNm(ArrayList _aryBushoNm) {
		this.aryBushoNm = _aryBushoNm;
	}

	/**
	 * 部署データ表示
	 */
	public void dispBushoData(){
		for ( int i = 0; i < this.getArtBushoCd().size(); i++ ) {
			System.out.println("\n--------" + i + "----------START");
			System.out.println("cd_busho" + i + ":" + this.getArtBushoCd().get(i).toString());
			System.out.println("nm_busho" + i + ":" + this.getAryBushoNm().get(i).toString());
			System.out.println("--------------------END");
		}
	}

}
