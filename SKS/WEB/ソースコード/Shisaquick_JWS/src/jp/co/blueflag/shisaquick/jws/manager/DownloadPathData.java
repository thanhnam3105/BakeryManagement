package jp.co.blueflag.shisaquick.jws.manager;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;

/*****************************************************************************************
 * 
 * ダウンロードパスデータ クラス
 *   @author k-katayama
 *   @since 2009/06/01
 *   
 *****************************************************************************************/
public class DownloadPathData extends DataBase {

	private String strDownloadPath;		//ダウンロードパス
	private XmlData xdtData;				//XMLデータ
	
	private ExceptionBase ex;				//エラー操作
	
	/************************************************************************************
	 * 
	 *  コンストラクタ
	 *   @param xmlData : XMLデータ
	 *   @param strKinoId : 機能ID
	 *   
	 ************************************************************************************/
	public DownloadPathData() throws ExceptionBase {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		try {
			setStrDownloadPath(null);
			xdtData = null;		
			ex = null;
			
		} catch ( Exception e ) {			
			//エラー設定
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("ダウンロードパスデータのコンストラクタが失敗しました");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 *  XMLデータの設定
	 *   @param xmlData : XMLデータ
	 *   @param strKinoId : 機能ID
	 *   
	 ************************************************************************************/
	public void setDownloadPathData(XmlData xmlData, String strKinoId) throws ExceptionBase{
		
		this.xdtData = xmlData;
		
		try{
			
			//--------------------------------------------------------
			//
			//　ダウンロードパスデータ 格納
			//
			//--------------------------------------------------------

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
				
				//ダウンロードパスデータへ格納
				for(int j=0; j<recData.size(); j++){
					
					//　項目名取得
					String recNm = ((String[])recData.get(j))[1];
					//　項目値取得
					String recVal = ((String[])recData.get(j))[2];
	
					//---------------- SA800データへ値セット --------------------
					if ( recNm == "URLValue" ) {
						//URL
						this.strDownloadPath = recVal;
						
					}
				}
			}
		
		}catch(ExceptionBase e){
			throw e;
			
		}catch(Exception e){
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("ダウンロードパスデータのXMLデータ設定に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}

	/**
	 * ダウンロードパス セッター
	 * @param _strDownloadPath : ダウンロードパスの値を格納する 
	 */
	public void setStrDownloadPath(String _strDownloadPath) {
		this.strDownloadPath = _strDownloadPath;
	}

	/**
	 * ダウンロードパス ゲッター
	 * @return strDownloadPath : ダウンロードパスの値を返す
	 */
	public String getStrDownloadPath() {
		return strDownloadPath;
	}

}
