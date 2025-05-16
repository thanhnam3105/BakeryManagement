package jp.co.blueflag.shisaquick.srv.base;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * レスポンスデータ保持
 *  : XMLIDごとの各レスポンスデータ保持クラスを包括
 * @author TT.furuta
 * @since  2009/03/24
 */
public class ResponsData extends RequestData{

	private String strCurrentKindID;		//操作中の機能ID
	private String strCurrentTableName;	//操作中のテーブル名
	private int 	iCurrentKindIdx;		//操作中の機能IDインデックス
	private int 	iCurrentTableIdx;		//操作中のテーブル名インデックス 
	
	/**
	 * コンストラクタ
	 *  : レスポンスデータ保持コンストラクタ
	 * @param XmlID XmlのID
	 */
	public ResponsData(String XmlID) throws Exception {
		super(null);
		//XMLIDを設定する。
		super.setID(XmlID);
		
		iCurrentKindIdx  = 0;
		iCurrentTableIdx = 0;
	}
	
	/**
	 * 行追加
	 *  : 機能ID/テーブル/行を追加する
	 * @param requestResponsRow : 追加行
	 * @return 追加行のインデックス
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public int addRow(RequestResponsRowBean requestResponsRow) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//追加行インデックス
		int iAddIdx = 0;
		
		try {
			//メンバ変数：strCurrentKindID/strCurrentTableNameが未設定の場合動作しない
			if (null != strCurrentKindID && null != strCurrentTableName){
			
				//機能Bean取得
				RequestResponsKindBean respKinoBean  =(RequestResponsKindBean) super.getItemList().get(iCurrentKindIdx);
				//テーブルBean取得
				RequestResponsTableBean respTableBean =(RequestResponsTableBean) respKinoBean.getItemList().get(iCurrentTableIdx);
								
				//テーブルBeanに行データ設定
				respTableBean.getItemList().add(requestResponsRow);
				
				//インデックス設定
				iAddIdx = respTableBean.GetCnt() -1;
			}
		
		} catch (Exception e){
			
			em.ThrowException(e, "行追加に失敗しました。");
			
		} finally {
			
		}

		return iAddIdx;
	}
	/**
	 * 機能処理結果追加
	 *  : 機能ID毎の処理結果を追加する
	 * @param benKaind : 機能処理結果
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void AddKind(RequestResponsKindBean benKaind) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			
			//①：機能処理結果を追加する
			super.getItemList().add(benKaind);
			
		} catch (Exception e) {
			
			em.ThrowException(e, "機能処理結果追加に失敗しました。");
			
		} finally {
			
		}
	}
	/**
	 * 機能処理結果追加
	 *  : 機能ID毎の処理結果を追加する
	 * @param benKaind : 機能処理結果
	 * @param addIndex : 追加位置Index
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void AddKind(RequestResponsBean benKaind,int addIndex)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			
			//①：機能処理結果を追加する
			super.getItemList().add(addIndex, benKaind);
			
		} catch (Exception e) {
			
			em.ThrowException(e, "機能処理結果追加に失敗しました。");
			
		} finally {
			
		}
	}

	/**
	 * カレント機能ID
	 *  : 操作中の機能IDを確保
	 * @param strKindID : 機能ID
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void CurrentKindID(String strKindID) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		boolean blFlg = false;
		
		try {
			
			//機能ID数ループ
			for (int i=0; i < super.GetKindCnt(); i++) {
				
				//機能IDが一致した場合
				if (strKindID.equals(super.GetKindID(i))){
					//機能ID退避
					this.strCurrentKindID = strKindID;
					//機能インデックス退避
					this.iCurrentKindIdx = i;
					//判定フラグ：true
					blFlg = true;
				}
			}
			
			//機能IDが存在しなかった場合
			if (!blFlg){
				
				RequestResponsKindBean reqRespBean = new RequestResponsKindBean();
				
				//機能ID設定
				reqRespBean.setID(strKindID);
				
				//機能IDを新たに機能IDリスト（基底クラスArray）に追加
				super.getItemList().add(reqRespBean);
				//機能ID退避
				this.strCurrentKindID = strKindID;
				//機能インデックス退避
//				this.iCurrentKindIdx = 0;
				this.iCurrentKindIdx = super.GetKindCnt()-1;
			}
			
		} catch (Exception e) {
			
			em.ThrowException(e, "カレント機能IDの設定に失敗しました。");
			
		} finally {
			
		}
		
	}
	
	/**
	 * カレントテーブル名
	 *  : 操作中のテーブル名を確保
	 * @param TableName : テーブル名
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void CurrentTableName(String strTableName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		try {
			
			boolean blFlg		= false;

			//カレント機能IDが未設定の場合何もしない
			if (null != strCurrentKindID){
				
				//機能IDに紐付くテーブル数ループ
				for (int i=0;i<super.GetTableCnt(iCurrentKindIdx);i++){
					
					//取得テーブル名称が一致した場合
					if (strTableName.equals(super.GetTableName(iCurrentKindIdx, i))){
						
						//テーブル名を退避
						this.strCurrentTableName = strTableName;
						//テーブル名インデックス退避
						this.iCurrentTableIdx = i;
						//判定フラグ：true
						blFlg = true;

					}
				}
				
				//テーブル名が存在しなかった場合
				if (!blFlg){
					
					RequestResponsTableBean reqRespBean = new RequestResponsTableBean();
					
					//テーブル名設定
					reqRespBean.setID(strTableName);
					//機能IDBean
					RequestResponsKindBean reqRespKino = (RequestResponsKindBean) GetItem(iCurrentKindIdx);
						//((RequestResponsBean) super.getItemList().get(iCurrentKindIdx));
					
					//テーブル名を新たに機能ID中のテーブルリスト（基底クラスArray）に追加
//					reqRespKino.getItemList().add(reqRespBean);
					reqRespKino.addTableItem(reqRespBean);
					
					//テーブル名を退避
					this.strCurrentTableName = strTableName;
					
					//テーブル名インデックス退避
					this.iCurrentTableIdx = 0;
				}

			}
			
		} catch (Exception e) {
			
			em.ThrowException(e, "カレントテーブル名の設定に失敗しました。");
			
		} finally {
			
		}
		this.strCurrentTableName = strTableName;
	}
}
