package jp.co.blueflag.shisaquick.srv.base;


import java.util.List;
import java.io.File;
import java.io.BufferedWriter; 
import java.io.FileWriter;
import java.io.IOException;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * CSV出力ファイル作成
 *  : サーバー指定フォルダへのCSVファイルの作成　
 *
 */
public class FileCreateCSV extends ObjectBase {
	
	/**
	 * コンストラクタ
	 *  : CSV出力ファイル作成コンストラクタ
	 */
	public FileCreateCSV() {
		super();
		
	}
	/**
	 * CSVファイル作成
	 *  : CSVファイルを作成する。
	 * @param strFilePath : ファイル名（FULLパス）
	 * @param lstCreateCSV : 出力データ
	 * @return 出力ファイル名 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void csvFileCreater(
			String strFilePath
			, List<?> lstCreateCSV
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning { 

	    BufferedWriter bw = null;
		
		try {
			//ファイルオブジェクトの生成
			File file = new File(strFilePath);
			//ファイル有無確認（無い場合は、新しく生成する）
			checkBeforeWritefile(file);
			//出力バッファを生成
		    bw = new BufferedWriter(new FileWriter(file));

		    //出力データの格納
		    setData(lstCreateCSV, ",", bw);

		    //ファイルの出力
    		bw.newLine();
			
    		//ファイルのクローズ
			if (bw != null){
			    bw.close();
			}
			
		} catch(Exception e) {
			em.ThrowException(e, "CSVの出力に失敗しました。");
			
		} finally {
			
		}
		
	}
	
	/**
	 * ファイルの実態を確認する。
	 * @param file : 出力ファイルのファイルオブジェクト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
    private void checkBeforeWritefile(File file) 
    throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//ファイルの有無を判定
			if (file.exists()){
				//ファイルが存在する場合
				
				//ファイルの属性を判定する（対象がファイルか？　書き込み可能か？）
				if (file.isFile() && file.canWrite()){
					//ファイルが書き込み可能な場合
					
	            }else{
	            	//ファイルが書き込み不可能な場合
	            	em.ThrowException(ExceptionKind.一般Exception
	            			, "E000401"
	            			, file.getName()
	            			, ""
	            			, "");
	            	
	            }

			}else{
				//ファイルが存在しない場合
				
				//新しいファイルを生成する
				file.createNewFile();
				
			}
			
		} catch(IOException e) {
			this.em.ThrowException(ExceptionKind.一般Exception
        			, "E000402"
        			, ""
        			, ""
        			, "");
			
		} catch(Exception e) {
			this.em.ThrowException(e, "ファイルの生成に失敗しました。");
			
		} finally {
			
		}

    }
	/**
	 * 出力DataをCSV形式でBufferedWriterに格納する
	 * @param listData : 出力データ
	 * @param separator : CSV区切り文字
	 * @param bufferedWriter : 出力データ格納オブジェクト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void  setData(
			List<?> listData
			, String separator
			, BufferedWriter bw
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//データ格納
			for (int i = 0; i < listData.size(); i++) {
				
				//検索結果の1行分を取り出す
				Object[] items = (Object[]) listData.get(i);

				for (int ix = 0; ix < items.length; ix++) {
					//データをセットする
					bw.write(toString(items[ix]));
					
					//最終項目以外の場合
					if (ix < items.length-1) {
						//CSVセパレータをセットする
					    bw.write(separator);
					}
					
				}
				//改行を行う
				bw.newLine();

			}
			
		} catch(Exception e) {
			this.em.ThrowException(e, "CSV出力バッファの生成に失敗しました。");
			
		} finally {
			
		}

	}
	
}
