package jp.co.blueflag.shisaquick.jws.manager;

import java.math.BigDecimal;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.*;


/**
 * 
 * ユーザマスタデータ保持
 * : ユーザに関する情報を管理する
 */
public class UserMstData extends DataBase {
	
	private BigDecimal dciUserid;				//ユーザID
	private String strUsernm;			//氏名
	private int intKaishscd;			//会社CD
	private String strKaishanm;			//会社名
	private int intBushocd;				//部署CD
	private String strBushonm;			//部署名
	//2010/02/25 NAKAMURA ADD START---------------
	private String strHaitaKaishanm;	//排他会社名
	private String strHaitaBushonm;		//排他部署名
	private String strHaitaShimei;		//排他氏名
	//2010/02/25 NAKAMURA ADD END-----------------
	private int intGroupcd;				//グループCD
	private String strGroupnm;			//グループ名
	private int intTeamcd;				//チームCD
	private String strTeamnm;			//チーム名
	private String strYakucd;			//役職CD
	private String strYakunm;			//役職名
	private ArrayList arySeizoData;		//製造担当会社データ
	private ArrayList aryAuthData;		//権限データ
	private XmlData xdtData;			//XMLデータ
	private ExceptionBase ex;			//エラーハンドリング
	
	/**
	 * コンストラクタ
	 */
	public UserMstData(){
		//スーパークラスのコンストラクタ呼び出し
		super();	
	}
	
	/**
	 * ユーザ情報 セッター
	 * @param xdtSetXml : XMLデータ
	 */
	public void setUserData(XmlData xdtSetXml) throws ExceptionBase{
		
		try{
			this.dciUserid = new BigDecimal(0);
			this.strUsernm = "";
			this.intKaishscd = 0;
			this.strKaishanm = "";
			this.intBushocd = 0;
			this.strBushonm = "";
			this.intGroupcd = 0;
			this.strGroupnm = "";
			this.intTeamcd = 0;
			this.strTeamnm = "";
			this.strYakucd = "";
			this.strYakunm = "";
			this.arySeizoData = new ArrayList();
			this.aryAuthData = new ArrayList();
			this.xdtData = xdtSetXml;
			this.ex = new ExceptionBase();
			
			/**********************************************************
			 *　ユーザデータ格納
			 *********************************************************/
			//　Userデータ機能ID格納
			String strKinoId = "USERINFO";
			
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
				//　権限格納配列
				String[] strAuth = new String[3];
				
				//ユーザデータへ格納
				for(int j=0; j<recData.size(); j++){
					
					//　項目名取得
					String recNm = ((String[])recData.get(j))[1];
					//　項目値取得
					String recVal = ((String[])recData.get(j))[2];
					
					/*****************Userデータへ値セット*********************/
					//　ユーザID
					 if(recNm == "id_user"){
						this.setDciUserid(new BigDecimal(recVal));
						
					//　ユーザ名
					}if(recNm == "nm_user"){
						this.setStrUsernm(recVal);
					
					//　会社コード
					}if(recNm == "cd_kaisha"){
						this.setIntKaishscd(Integer.parseInt(recVal));
						
					//　会社名
					}if(recNm == "nm_kaisha"){
						this.setStrKaishanm(recVal);
						
					//　部署コード
					}if(recNm == "cd_busho"){
						this.setIntBushocd(Integer.parseInt(recVal));
						
					//　部署名
					}if(recNm == "nm_busho"){
						this.setStrBushonm(recVal);
						
					//　グループコード
					}if(recNm == "cd_group"){
						this.setIntGroupcd(Integer.parseInt(recVal));
						
					//　グループ名
					}if(recNm == "nm_group"){
						this.setStrGroupnm(recVal);
						
					//　チームコード
					}if(recNm == "cd_team"){
						this.setIntTeamcd(Integer.parseInt(recVal));
						
					//　チーム名
					}if(recNm == "nm_team"){
						this.setStrTeamnm(recVal);
						
					//　役職コード
					}if(recNm == "cd_literal"){
						this.setStrYakucd(recVal);
						
					//　役職名
					}if(recNm == "nm_literal"){
						this.setStrYakunm(recVal);
						
					//　画面ID（権限）
					}if(recNm == "id_gamen"){
						strAuth[0] = recVal;
						
					//　機能ＩＤ（権限）
					}if(recNm == "id_kino"){
						strAuth[1] = recVal;
						
					//　参照可能データＩＤ（権限）
					}if(recNm == "id_data"){
						strAuth[2] = recVal;
						
					}
				}
				//権限データ追加
				aryAuthData.add(strAuth);
			}
		}catch(ExceptionBase e){
			throw e;
		
		}catch(Exception e){
			e.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("ユーザマスタの初期化に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}
	
	/**
	 * ユーザ情報 セッター
	 * @param xdtSetXml : XMLデータ
	 */
	public void setSeizoData(XmlData xdtSetXml) throws ExceptionBase{
		
		/**********************************************************
		 *　担当製造会社データ格納
		 *********************************************************/
		//　担当製造データ機能ID格納
		String strKinoId = "SA210";
		
		//全体配列取得
		ArrayList seizoData = xdtSetXml.GetAryTag(strKinoId);
		
		//機能配列取得
		ArrayList kinoData = (ArrayList)seizoData.get(0);
		
		//テーブル配列取得
		ArrayList tableData = (ArrayList)kinoData.get(1);
		
		//レコード取得
		for(int k=1; k<tableData.size(); k++){
			//　１レコード取得
			ArrayList recData = ((ArrayList)((ArrayList)tableData.get(k)).get(0));
			//　担当製造会社格納配列
			String[] strSeizo = new String[3];
			
			//担当製造データへ格納
			for(int l=0; l<recData.size(); l++){
				
				//　項目名取得
				String recNm = ((String[])recData.get(l))[1];
				//　項目値取得
				String recVal = ((String[])recData.get(l))[2];
				
				/*****************担当製造データへ値セット*********************/
				//　担当製造会社コード
				 if(recNm == "cd_kaisha"){
					strSeizo[0] = recVal;
					
				//　担当製造会社名
				}if(recNm == "nm_kaisha"){
					strSeizo[1] = recVal;
					
				}
			}
			//　担当製造会社データ追加
			arySeizoData.add(strSeizo);
		}
	}

	/**
	 * 排他ユーザ情報 セッター
	 * @param xdtSetXml : XMLデータ
	 */	
	public void setHaitaUserData(XmlData xdtSetXml) throws ExceptionBase{

		try{
			this.strHaitaKaishanm = "";
			this.strHaitaBushonm = "";
			this.strHaitaShimei = "";
			this.xdtData = xdtSetXml;
			this.ex = new ExceptionBase();
			
			/**********************************************************
			 *　排他ユーザデータ格納
			 *********************************************************/
			//　排他Userデータ機能ID格納
			String strKinoId = "SA480";
			
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
					
					/*****************排他Userデータへ値セット*********************/
					//　排他会社名
					 if(recNm == "nm_kaisha_haita"){
						this.setStrHaitaKaishanm(recVal);
						
					//　排他部署名
					}if(recNm == "nm_busho_haita"){
						this.setStrHaitaBushonm(recVal);
					
					//　排他氏名
					}if(recNm == "nm_user_haita"){
						this.setStrHaitaShimei(recVal);
						
					}
				}
			}
		}catch(ExceptionBase e){
			throw e;
		
		}catch(Exception e){
			e.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("排他情報の初期化に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}

	}

	/**
	 * ユーザID ゲッター
	 * @return dciUserid : ユーザIDの値を返す
	 */
	public BigDecimal getDciUserid() {
		return dciUserid;
	}
	/**
	 * ユーザID セッター
	 * @param _dciUserid : ユーザIDの値を格納する
	 */
	public void setDciUserid(BigDecimal _dciUserid) {
		this.dciUserid = _dciUserid;
	}

	/**
	 * 権限データ　ゲッター
	 * @return intAuth : 権限CDの値を返す
	 */
	public ArrayList getAryAuthData() {
		return aryAuthData;
	}
	/**
	 * 権限データ　セッター
	 * @param _intAuth : 権限CDの値を格納する
	 */
	public void setAryAuthData(ArrayList _aryAuthData) {
		this.aryAuthData = _aryAuthData;
	}
	/**
	 * 氏名 ゲッター
	 * @return strUsernm : 氏名の値を返す
	 */
	public String getStrUsernm() {
		return strUsernm;
	}
	/**
	 * 氏名 セッター
	 * @param _strUsernm : 氏名の値を格納する
	 */
	public void setStrUsernm(String _strUsernm) {
		this.strUsernm = _strUsernm;
	}

	/**
	 * 会社CD ゲッター
	 * @return intKaishscd : 会社CDの値を返す
	 */
	public int getIntKaishscd() {
		return intKaishscd;
	}

	/**
	 * 会社CD セッター
	 * @param _intKaishscd : 会社CDの値を格納する
	 */
	public void setIntKaishscd(int _intKaishscd) {
		this.intKaishscd = _intKaishscd;
	}

	/**
	 * 会社名 ゲッター
	 * @return strKaishanm : 会社名の値を返す
	 */
	public String getStrKaishanm() {
		return strKaishanm;
	}

	/**
	 * 会社名 セッター
	 * @param _strKaishanm : 会社名の値を格納する
	 */
	public void setStrKaishanm(String _strKaishanm) {
		this.strKaishanm = _strKaishanm;
	}

	/**
	 * 部署CD ゲッター
	 * @return intBushocd : 部署CDの値を返す
	 */
	public int getIntBushocd() {
		return intBushocd;
	}

	/**
	 * 部署CD セッター
	 * @param _intBushocd : 部署CDの値を格納する
	 */
	public void setIntBushocd(int _intBushocd) {
		this.intBushocd = _intBushocd;
	}

	/**
	 * 部署名 ゲッター
	 * @return strBushonm : 部署名の値を返す
	 */
	public String getStrBushonm() {
		return strBushonm;
	}

	/**
	 * 部署名 セッター
	 * @param _strBushonm : 部署名の値を格納する
	 */
	public void setStrBushonm(String _strBushonm) {
		this.strBushonm = _strBushonm;
	}
	
	//2010/02/25 NAKAMURA ADD START------------------
	/**
	 * 排他会社名 ゲッター
	 * @return strHaitaKaishanm : 排他会社名の値を返す
	 */
	public String getStrHaitaKaishanm() {
		return strHaitaKaishanm;
	}

	/**
	 * 排他会社名 セッター
	 * @param _strHaitaKaishanm : 排他会社名の値を格納する
	 */
	public void setStrHaitaKaishanm(String _strHaitaKaishanm) {
		this.strHaitaKaishanm = _strHaitaKaishanm;
	}	
	
	/**
	 * 排他部署名 ゲッター
	 * @return strHaitaBushonm : 排他部署名の値を返す
	 */
	public String getStrHaitaBushonm() {
		return strHaitaBushonm;
	}

	/**
	 * 排他部署名 セッター
	 * @param _strHaitaBushonm : 排他部署名の値を格納する
	 */
	public void setStrHaitaBushonm(String _strHaitaBushonm) {
		this.strHaitaBushonm = _strHaitaBushonm;
	}	
	
	/**
	 * 排他氏名 ゲッター
	 * @return strHaitaShimei : 排他氏名の値を返す
	 */
	public String getStrHaitaShimei() {
		return strHaitaShimei;
	}

	/**
	 * 排他氏名 セッター
	 * @param _strHaitaShimei : 排他氏名の値を格納する
	 */
	public void setStrHaitaShimei(String _strHaitaShimei) {
		this.strHaitaShimei = _strHaitaShimei;
	}		
	//2010/02/25 NAKAMURA ADD END--------------------
	

	/**
	 * グループCD ゲッター
	 * @return intGroupcd : グループCDの値を返す
	 */
	public int getIntGroupcd() {
		return intGroupcd;
	}

	/**
	 * グループCD セッター
	 * @param _intGroupcd : グループCDの値を格納する
	 */
	public void setIntGroupcd(int _intGroupcd) {
		this.intGroupcd = _intGroupcd;
	}

	/**
	 * グループ名 ゲッター
	 * @return intGroupnm : グループ名の値を返す
	 */
	public String getStrGroupnm() {
		return strGroupnm;
	}

	/**
	 * グループ名 セッター
	 * @param _intGroupnm : グループ名の値を格納する
	 */
	public void setStrGroupnm(String _strGroupnm) {
		this.strGroupnm = _strGroupnm;
	}

	/**
	 * チームCD ゲッター
	 * @return intTeamcd : チームCDの値を返す
	 */
	public int getIntTeamcd() {
		return intTeamcd;
	}

	/**
	 * チームCD セッター
	 * @param _intTeamcd : チームCDの値を格納する
	 */
	public void setIntTeamcd(int _intTeamcd) {
		this.intTeamcd = _intTeamcd;
	}

	/**
	 * チーム名 ゲッター
	 * @return strTeamnm : チーム名の値を返す
	 */
	public String getStrTeamnm() {
		return strTeamnm;
	}

	/**
	 * チーム名 セッター
	 * @param _strTeamnm : チーム名の値を格納する
	 */
	public void setStrTeamnm(String _strTeamnm) {
		this.strTeamnm = _strTeamnm;
	}

	/**
	 * 役職CD ゲッター
	 * @return strYakucd : 役職CDの値を返す
	 */
	public String getStrYakucd() {
		return strYakucd;
	}

	/**
	 * 役職CD セッター
	 * @param _strYakucd : 役職CDの値を格納する
	 */
	public void setStrYakucd(String _strYakucd) {
		this.strYakucd = _strYakucd;
	}

	/**
	 * 役職名 ゲッター
	 * @return strYakunm : 役職名の値を返す
	 */
	public String getStrYakunm() {
		return strYakunm;
	}

	/**
	 * 役職名 セッター
	 * @param _strYakunm : 役職名の値を格納する
	 */
	public void setStrYakunm(String _strYakunm) {
		this.strYakunm = _strYakunm;
	}

	/**
	 * 製造担当会社データ セッター
	 * @param _arySeizoData : 製造担当会社データの値を格納する
	 */
	public void setArySeizoData(ArrayList _arySeizoData) {
		this.arySeizoData = _arySeizoData;
	}

	/**
	 * 製造担当会社データ ゲッター
	 * @return arySeizoData : 製造担当会社データの値を返す
	 */
	public ArrayList getArySeizoData() {
		return arySeizoData;
	}
	
	/**
	 * ユーザデータ表示
	 */
	public void dsipUserData(){
		System.out.println("");
		System.out.println("ユーザID：" + this.getDciUserid());
		System.out.println("ユーザ名：" + this.getStrUsernm());
		System.out.println("会社コード：" + this.getIntKaishscd());
		System.out.println("会社名：" + this.getStrKaishanm());
		System.out.println("部署コード：" + this.getIntBushocd());
		System.out.println("部署名：" + this.getStrBushonm());
		System.out.println("グループコード：" + this.getIntGroupcd());
		System.out.println("グループ名：" + this.getStrGroupnm());
		System.out.println("チームコード：" + this.getIntTeamcd());
		System.out.println("チーム名：" + this.getStrTeamnm());
		System.out.println("役職コード：" + this.getStrYakucd());
		System.out.println("役職名：" + this.getStrYakunm());
		
		for(int i=0;i<this.arySeizoData.size();i++){
			String[] strDispSeizo = (String[])arySeizoData.get(i);
			System.out.println("担当製造会社コード：" + strDispSeizo[0]);
			System.out.println("担当製造会社名：" + strDispSeizo[1]);
		}
		
		for(int i=0;i<this.aryAuthData.size();i++){
			String[] strDispAuth = (String[])aryAuthData.get(i);
			System.out.println("画面ID：" + strDispAuth[0]);
			System.out.println("機能ID：" + strDispAuth[1]);
			System.out.println("データID：" + strDispAuth[2]);
		}
	}
}
