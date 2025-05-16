package jp.co.blueflag.shisaquick.jws.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;

/**
 * 
 * リテラルデータ保持
 *  : リテラルデータの管理を行う
 *
 */
public class LiteralData extends DataBase {

	private String strCategoryCd;		//カテゴリコード
	private ArrayList aryLiteralCd;			//リテラルコード
	private ArrayList aryLiteralNm;		//リテラル名
	private ArrayList aryValue1;				//リテラル値1
	private ArrayList aryValue2;				//リテラル値2
	private ArrayList arySortNo;				//表示順
	private ArrayList aryGroupCd;				//グループコード
	
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	private ArrayList aryBiko;				//備考
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
	
	private String strKinoId;				//機能ID
	private XmlData xdtData;			//XMLデータ
	private ExceptionBase ex;			//エラーハンドリング
	
	/**
	 * コンストラクタ
	 * @param strKinoId : 機能ID
	 */
	public LiteralData(String strKinoId) {
		//スーパークラスのコンストラクタ呼び出し
		super();
		//機能IDをセット
		this.strKinoId = strKinoId;
		
		this.strCategoryCd = "";
		this.aryLiteralCd = new ArrayList();
		this.aryLiteralNm = new ArrayList();
		this.aryValue1 = new ArrayList();
		this.aryValue2 = new ArrayList();
		this.arySortNo = new ArrayList();
		this.aryGroupCd = new ArrayList();
		
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
		this.aryBiko = new ArrayList();
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
		
	}
	
	/**
	 * XMLデータの設定
	 * @param xmlData : XMLデータ
	 */
	public void setLiteralData(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;
		
		try{

			//項目のクリア
			this.aryLiteralCd.clear();
			this.aryLiteralNm.clear();
			this.aryValue1.clear();
			this.aryValue2.clear();
			this.arySortNo.clear();
			this.aryGroupCd.clear();
			
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			this.aryBiko.clear();
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
			
			/**********************************************************
			 *　リテラルデータ格納
			 *********************************************************/
			
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
					if ( recNm == "cd_category" ) {
						//カテゴリコード
						this.setStrCategoryCd(recVal);
					} else if ( recNm == "cd_literal" ) {
						//リテラルコード
						this.aryLiteralCd.add(recVal);
					} else if ( recNm == "nm_literal" ) {
						//リテラル名
						this.aryLiteralNm.add(recVal);
					} else if ( recNm == "value1" ) {
						//リテラル値1
						this.aryValue1.add(recVal);
					} else if ( recNm == "value2" ) {
						//リテラル値2
						this.aryValue2.add(recVal);
					} else if ( recNm == "no_sort" ) {
						//表示順
						this.arySortNo.add(recVal);
					} else if ( recNm == "cd_group" ) {
						//グループコード
						this.aryGroupCd.add(recVal);
					}
					
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
					else if ( recNm == "biko" ) {
						//グループコード
						this.aryBiko.add(recVal);
					}
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
					
				}
			}
			
		}catch(ExceptionBase e){
			throw e;
		
		}catch(Exception e){
			e.printStackTrace();
			
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("リテラルデータ[" + strKinoId + "]の初期化に失敗しました");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
			
		}
	}
	
	/**
	 * リテラル名 検索
	 * @return strCategoriCd : リテラル名の値を返す
	 */
	public String selectLiteralNm(String strCd) {
		int index = -1;
		for(int i=0; i<aryLiteralCd.size(); i++){
			String chk = (String)aryLiteralCd.get(i);
			if(strCd.equals(chk)){
				index = i;
			}
		}
		if(index >= 0){
			return (String)aryLiteralNm.get(index);
		}else{
			return "";
		}
	}
	
	/**
	 * リテラル値１ 検索
	 * @return strCategoriCd : リテラル値１ の値を返す
	 */
	public int selectLiteralVal1(int strCd) {
		int index = -1;
		for(int i=0; i<aryLiteralCd.size(); i++){
			int chk = Integer.parseInt((String)aryLiteralCd.get(i));
			if(strCd == chk){
				index = i;
			}
		}
		if(index >= 0){
			return Integer.parseInt((String)aryValue1.get(index));
		}else{
			return 0;
		}
	}

	/**
	 * リテラル値１ 検索
	 * @return strCategoriCd : リテラル値１ の値を返す
	 */
	public String selectLiteralVal1(String strCd) {
		int index = -1;
		for(int i=0; i<aryLiteralCd.size(); i++){
			String chk = aryLiteralCd.get(i).toString();
			if(strCd.equals(chk)){
				index = i;
			}
		}
		if(index >= 0){
			return aryValue1.get(index).toString();
		}else{
			return "";
		}
	}

	/**
	 * リテラル値１ 検索
	 * @return strCategoriCd : リテラル値１ の値を返す
	 */
	public String selectLiteralVal2(String strCd) {
		int index = -1;
		for(int i=0; i<aryLiteralCd.size(); i++){
			String chk = aryLiteralCd.get(i).toString();
			if(strCd.equals(chk)){
				index = i;
			}
		}
		if(index >= 0){
			return aryValue2.get(index).toString();
		}else{
			return "";
		}
	}
	
	/**
	 * リテラルコード 検索
	 * @return strCategoriCd : カテゴリコードの値を返す
	 */
	public String selectLiteralCd(int index) {
			return (String)aryLiteralCd.get(index);
	}
	
	
	/**
	 * リテラルコード 検索
	 * @return strCategoriCd : カテゴリコードの値を返す
	 */
	public String selectLiteralCd(String strNm) {
		int index = -1;
		for(int i=0; i<aryLiteralNm.size(); i++){
			String chk = aryLiteralNm.get(i).toString();
			if(strNm.equals(chk)){
				index = i;
			}
		}
		if(index >= 0){
			return aryLiteralCd.get(index).toString();
		}else{
			return "";
		}
	}
	

	/**
	 * カテゴリコード ゲッター
	 * @return strCategoriCd : カテゴリコードの値を返す
	 */
	public String getStrCategoryCd() {
		return strCategoryCd;
	}
	/**
	 * カテゴリコード セッター
	 * @param strCategoriCd : カテゴリコードの値を格納する
	 */
	public void setStrCategoryCd(String _strCategoriCd) {
		this.strCategoryCd = _strCategoriCd;
	}
	
	/**
	 * リテラルコード ゲッター
	 * @return aryLiteralCd : リテラルコードの値を返す
	 */
	public ArrayList getAryLiteralCd() {
		return aryLiteralCd;
	}
	/**
	 * リテラルコード セッター
	 * @param _aryLiteralCd : リテラルコードの値を格納する
	 */
	public void setAryLiteralCd(ArrayList _aryLiteralCd) {
		this.aryLiteralCd = _aryLiteralCd;
	}

	/**
	 * リテラル名 ゲッター
	 * @return aryLiteralNm : リテラル名の値を返す
	 */
	public ArrayList getAryLiteralNm() {
		return aryLiteralNm;
	}
	/**
	 * リテラル名 セッター
	 * @param _aryLiteralNm : リテラル名の値を格納する
	 */
	public void setAryLiteralNm(ArrayList _aryLiteralNm) {
		this.aryLiteralNm = _aryLiteralNm;
	}

	/**
	 * リテラル値1 ゲッター
	 * @return aryValue1 : リテラル値1の値を返す
	 */
	public ArrayList getAryValue1() {
		return aryValue1;
	}
	/**
	 * リテラル値1 セッター
	 * @param _aryValue1 : リテラル値1の値を格納する
	 */
	public void setAryValue1(ArrayList _aryValue1) {
		this.aryValue1 = _aryValue1;
	}

	/**
	 * リテラル値2 ゲッター
	 * @return aryValue2 : リテラル値2の値を返す
	 */
	public ArrayList getAryValue2() {
		return aryValue2;
	}
	/**
	 * リテラル値2 セッター
	 * @param _aryValue2 : リテラル値2の値を格納する
	 */
	public void setAryValue2(ArrayList _aryValue2) {
		this.aryValue2 = _aryValue2;
	}

	/**
	 * 表示順 ゲッター
	 * @return arySortNo : 表示順の値を返す
	 */
	public ArrayList getArySortNo() {
		return arySortNo;
	}
	/**
	 * 表示順 セッター
	 * @param _arySortNo : 表示順の値を格納する
	 */
	public void setArySortNo(ArrayList _arySortNo) {
		this.arySortNo = _arySortNo;
	}

	/**
	 * グループコード ゲッター
	 * @return aryGroupCd : グループコードの値を返す
	 */
	public ArrayList getAryGroupCd() {
		return aryGroupCd;
	}
	/**
	 * グループコード セッター
	 * @param _aryGroupCd : グループコードの値を格納する
	 */
	public void setAryGroupCd(ArrayList _aryGroupCd) {
		this.aryGroupCd = _aryGroupCd;
	}

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	/**
	 * 備考 ゲッター
	 * @return aryBiko : 備考の値を返す
	 */
	public ArrayList getAryBiko() {
		return aryBiko;
	}
	/**
	 * 備考 セッター
	 * @param aryBiko : 備考の値を格納する
	 */
	public void setAryBiko(ArrayList aryBiko) {
		this.aryBiko = aryBiko;
	}
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
	
	/**
	 * リテラルデータ表示
	 */
	public void dispLiteralData(){
		System.out.println("\ncd_categori ：" + this.getStrCategoryCd());
		
		for ( int i = 0; i < this.getAryGroupCd().size(); i++ ) {
			System.out.println("--------" + i + "----------START");
			System.out.println("cd_literal :" + this.getAryLiteralCd().get(i).toString());
			System.out.println("nm_literal :" + this.getAryLiteralNm().get(i).toString());
			System.out.println("value1 :"+ this.getAryValue1().get(i).toString());
			System.out.println("value2 :"+ this.getAryValue2().get(i).toString());
			System.out.println("no_sort :"+ this.getArySortNo().get(i).toString());
			System.out.println("cd_group :"+ this.getAryGroupCd().get(i).toString());	
			System.out.println("biko :"+ this.getAryBiko().get(i).toString());	
			System.out.println("--------------------END");
		}
	}

}
