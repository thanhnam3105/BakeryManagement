package jp.co.blueflag.shisaquick.jws.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;

/**
 * 
 * 会社データ保持
 *  : 会社データの管理を行う
 *
 */
public class KaishaData extends DataBase {

	private ArrayList artKaishaCd;	//会社コード
	private ArrayList aryKaishaNm;	//会社名
	private ArrayList aryKaishaGenryo;	//原料桁
	
	private XmlData xdtData;			//XMLデータ
	private ExceptionBase ex;			//エラーハンドリング
	
	/**
	 * コンストラクタ
	 * @param strKinoId : 機能ID
	 */
	public KaishaData() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		this.artKaishaCd = new ArrayList();
		this.aryKaishaNm = new ArrayList();
		this.aryKaishaGenryo = new ArrayList();
	}
	
	/**
	 * XMLデータの設定
	 * @param xmlData : XMLデータ
	 */
	public void setKaishaData(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;
		
		try{
			
			//項目のクリア
			this.artKaishaCd.clear();
			this.aryKaishaNm.clear();
			
			/**********************************************************
			 *　リテラルデータ格納
			 *********************************************************/
			
			//機能IDの設定
			String strKinoId = "SA140"; 
			
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
					if ( recNm == "cd_kaisha" ) {
						//会社コード
						this.artKaishaCd.add(recVal);
					} else if ( recNm == "nm_kaisha" ) {
						//会社名
						this.aryKaishaNm.add(recVal);
					} else if ( recNm == "keta_genryo" ) {
						//原料桁
						this.aryKaishaGenryo.add(recVal);
					}
				}
			}
			
		}catch(ExceptionBase e){
			throw e;
		
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("会社データの初期化に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
	
	/**
	 * 【QP@00342】XMLデータの設定　営業部署
	 * @param xmlData : XMLデータ
	 */
	public void setKaishaData_Eigyo(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;
		
		try{
			
			//項目のクリア
			this.artKaishaCd.clear();
			this.aryKaishaNm.clear();
			
			/**********************************************************
			 *　リテラルデータ格納
			 *********************************************************/
			
			//機能IDの設定
			String strKinoId = "FGEN2090"; 
			
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
					if ( recNm == "cd_kaisha" ) {
						//会社コード
						this.artKaishaCd.add(recVal);
					} else if ( recNm == "nm_kaisha" ) {
						//会社名
						this.aryKaishaNm.add(recVal);
					}
				}
			}
			
		}catch(ExceptionBase e){
			throw e;
		
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("会社データの初期化に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}

	/**
	 * 会社コード ゲッター
	 * @return artKaishaCd : 会社コードの値を返す
	 */
	public ArrayList getArtKaishaCd() {
		return artKaishaCd;
	}
	/**
	 * 会社コード セッター
	 * @param _artKaishaCd : 会社コードの値を格納する
	 */
	public void setArtKaishaCd(ArrayList _artKaishaCd) {
		this.artKaishaCd = _artKaishaCd;
	}

	/**
	 * 会社名 ゲッター
	 * @return aryKaishaNm : 会社名の値を返す
	 */
	public ArrayList getAryKaishaNm() {
		return aryKaishaNm;
	}
	/**
	 * 会社名 セッター
	 * @param _aryKaishaNm : 会社名の値を格納する
	 */
	public void setAryKaishaNm(ArrayList _aryKaishaNm) {
		this.aryKaishaNm = _aryKaishaNm;
	}
	
	/**
	 * 原料桁 ゲッター
	 * @return aryKaishaGenryo : 原料桁の値を返す
	 */
	public ArrayList getAryKaishaGenryo() {
		return aryKaishaGenryo;
	}
	/**
	 * 原料桁 セッター
	 * @param aryKaishaGenryo : 原料桁の値を格納する
	 */
	public void setAryKaishaGenryo(ArrayList aryKaishaGenryo) {
		this.aryKaishaGenryo = aryKaishaGenryo;
	}

	/**
	 * 会社データ表示
	 */
	public void dispKaishaData(){
		for ( int i = 0; i < this.getArtKaishaCd().size(); i++ ) {
			System.out.println("\n--------" + i + "----------START");
			System.out.println("cd_kaisha" + i + ":" + this.getArtKaishaCd().get(i).toString());
			System.out.println("nm_kaisha" + i + ":" + this.getAryKaishaNm().get(i).toString());
			System.out.println("keta_genryo" + i + ":" + this.getAryKaishaGenryo().get(i).toString());
			System.out.println("--------------------END");
		}
	}

}
