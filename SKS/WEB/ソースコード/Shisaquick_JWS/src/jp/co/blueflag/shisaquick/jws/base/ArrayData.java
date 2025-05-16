package jp.co.blueflag.shisaquick.jws.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.common.*;


/**
 * 
 * 配列データ
 *  : 配列データの管理を行う
 *
 */
public class ArrayData extends DataBase {

	private ArrayList aryArrayData;	//操作用配列
	private ExceptionBase ex;			//エラーハンドリング
	private XmlData xdtData;
	
	private static int CONST_CD		= 0;	//コード取得用定数
	private static int CONST_NAME		= 1;	//名称取得用定数
	private static int CONST_LIST		= 2;	//リスト取得用定数
	
	/**
	 * コンストラクタ
	 */
	public ArrayData() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		ex = new ExceptionBase();
	}
	
	/**
	 * 単一検索（コード指定）
	 *  : 1次元コードと2次元コードより配列を検索
	 * @param strCode1 : 1次元コード
	 * @param strCode2 : 2次元コード 
	 * @return 返却配列
	 */
	public ArrayList selCodeShiteiT(String strCode1, String strCode2) throws ExceptionBase {
		
		ArrayList retArray = null;
		
		try {
			
			ArrayList aryOne = null;	//第一階層配列
			ArrayList aryTwo = null;	//第二階層配列
			
			//配列要素数ループ
			for (int i=0;i < aryArrayData.size(); i++){
				
				//第一階層配列取得
				aryOne = (ArrayList) aryArrayData.get(i); 
				
				//引数：1次元コードと第一階層配列：取得コードが一致した場合
				if (strCode1.equals(aryOne.get(CONST_CD).toString())){

					//返却配列インスタンス生成
					retArray = new ArrayList();
					
					//1次元項目設定
					retArray.add(aryOne.get(CONST_CD));	//CD設定
					retArray.add(aryOne.get(CONST_NAME));	//名称設定
					retArray.add(new ArrayList());	//第二階層配列生成
					
					//第二階層配列取得
					aryTwo = (ArrayList) aryOne.get(CONST_LIST); 

					//配列要素数ループ
					for (int j=0;j < aryTwo.size(); j++){
						
						//引数：第二階層コードと第二階層配列：取得コードが一致した場合
						if (strCode2.equals(aryTwo.get(0).toString())){
							((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_CD));	//CD設定
							((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_NAME));	//名称設定
						}
						
					}
					
				}
			}
			
		} catch (Exception e) {
			
			ex.setStrErrmsg("続行不能なエラーが発生しました。システム担当に連絡してください。");
			ex.setStrErrShori("ArrayData.selCodeShiteiT");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("配列データの管理に失敗しました。(単一検索：コード)");

			throw ex;
			
		} finally {
			
		}
		
		return retArray;
	}

	/**
	 * 単一検索（名称指定）
	 *  : 1次元コード名称と2次元コード名称より配列を検索
	 * @param strName1 : 1次元コード名称
	 * @param strName2 : 2次元コード名称
	 * @return 返却配列
	 * @throws ExceptionBase 
	 */
	public ArrayList selNameShiteiT(String strName1, String strName2) throws ExceptionBase {
		
		ArrayList retArray = null;
		
		try {
			
			ArrayList aryOne = null;	//第一階層配列
			ArrayList aryTwo = null;	//第二階層配列
			
			//配列要素数ループ
			for (int i=0;i < aryArrayData.size(); i++){
				
				//第一階層配列取得
				aryOne = (ArrayList) aryArrayData.get(i); 
				
				//引数：1次元コード名称と第一階層配列：取得コード名称が一致した場合
				if (strName1.equals(aryOne.get(CONST_NAME).toString())){

					//返却配列インスタンス生成
					retArray = new ArrayList();
					
					//1次元項目設定
					retArray.add(aryOne.get(CONST_CD));	//CD設定
					retArray.add(aryOne.get(CONST_NAME));	//名称設定
					retArray.add(new ArrayList());	//第二階層配列生成
					
					//第二階層配列取得
					aryTwo = (ArrayList) aryOne.get(2); 

					//配列要素数ループ
					for (int j=0;j < aryTwo.size(); j++){
						
						//引数：2次元コード名称と第二階層配列：取得コード名称が一致した場合
						if (strName2.equals(aryTwo.get(CONST_NAME).toString())){
							((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_CD));	//CD設定
							((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_NAME));	//名称設定
						}
						
					}
					
				}
			}
			
		} catch (Exception e) {
			
			ex.setStrErrmsg("続行不能なエラーが発生しました。システム担当に連絡してください。");
			ex.setStrErrShori("ArrayData.selNameShiteiT");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("配列データの管理に失敗しました。(単一検索：名称)");

			throw ex;

		} finally {
			
		}
		
		return retArray;
	}

	/**
	 * 複数検索（コード指定）
	 *  : 1次元コードより配列を検索
	 * @param strCode1 : 1次元コード 
	 * @return 返却配列
	 * @throws ExceptionBase 
	 */
	public ArrayList selCodeShiteiT(String strCode1) throws ExceptionBase { 

		ArrayList retArray = null;
		
		try {
			
			ArrayList aryOne = null;	//第一階層配列
			ArrayList aryTwo = null;	//第二階層配列
			
			//配列要素数ループ
			for (int i=0;i < aryArrayData.size(); i++){
				
				//第一階層配列取得
				aryOne = (ArrayList) aryArrayData.get(i); 
				
				//引数：1次元コードと第一階層配列：取得コードが一致した場合
				if (strCode1.equals(aryOne.get(CONST_CD).toString())){

					//返却配列インスタンス生成
					retArray = new ArrayList();
					
					//1次元項目設定
					retArray.add(aryOne.get(CONST_CD));	//CD設定
					retArray.add(aryOne.get(CONST_NAME));	//名称設定
					retArray.add(new ArrayList());	//第二階層配列生成
					
					//第二階層配列取得
					aryTwo = (ArrayList) aryOne.get(CONST_LIST); 

					//配列要素数ループ
					for (int j=0;j < aryTwo.size(); j++){
						
						((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_CD));	//CD設定
						((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_NAME));	//名称設定
						
					}
					
				}
			}
			
		} catch (Exception e) {
			
			ex.setStrErrmsg("続行不能なエラーが発生しました。システム担当に連絡してください。");
			ex.setStrErrShori("ArrayData.selCodeShiteiT");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("配列データの管理に失敗しました。(複数検索：コード)");

			throw ex;

		} finally {
			
		}
		
		return retArray;

	}

	/**
	 * 複数検索（名称指定）
	 *  : 1次元コード名称より配列を検索
	 * @param strName1 : 1次元コード名称
	 * @return 返却配列
	 * @throws ExceptionBase 
	 */
	public ArrayList selNameShiteiT(String strName1) throws ExceptionBase { 

		ArrayList retArray = null;
		
		try {
			
			ArrayList aryOne = null;	//第一階層配列
			ArrayList aryTwo = null;	//第二階層配列
			
			//配列要素数ループ
			for (int i=0;i < aryArrayData.size(); i++){
				
				//第一階層配列取得
				aryOne = (ArrayList) aryArrayData.get(i); 
				
				//引数：1次元コード名称と第一階層配列：取得コード名称が一致した場合
				if (strName1.equals(aryOne.get(CONST_NAME).toString())){

					//返却配列インスタンス生成
					retArray = new ArrayList();
					
					//1次元項目設定
					retArray.add(aryOne.get(CONST_CD));	//CD設定
					retArray.add(aryOne.get(CONST_NAME));	//名称設定
					retArray.add(new ArrayList());	//第二階層配列生成
					
					//第二階層配列取得
					aryTwo = (ArrayList) aryOne.get(2); 

					//配列要素数ループ
					for (int j=0;j < aryTwo.size(); j++){
						
						((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_CD));	//CD設定
						((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_NAME));	//名称設定
						
					}
					
				}
			}
			
		} catch (Exception e) {
			
			ex.setStrErrmsg("続行不能なエラーが発生しました。システム担当に連絡してください。");
			ex.setStrErrShori("ArrayData.selNameShiteiT");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("配列データの管理に失敗しました。(複数検索：コード)");

			throw ex;

		} finally {
			
		}
		
		return retArray;

	}

	
	/**
	 * 操作用配列 ゲッター
	 * @return aryArrayData : 操作用配列の値を返す
	 */
	public ArrayList getAryArrayData() {
		return aryArrayData;
	}
	/**
	 * 操作用配列 セッター
	 * @param _aryArrayData : 操作用配列の値を格納する
	 */
	public void setAryArrayData(ArrayList _aryArrayData) {
		this.aryArrayData = _aryArrayData;
	}

}
