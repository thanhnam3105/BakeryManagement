package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * 営業担当者データ
 *  : 営業担当者データを保持する
 * 
 * @author TT.Nishigawa
 * @since  2011/02/10
 */
public class EigyoTantoData extends DataBase{

	private String no_gyo = "";				//行No
	private String id_user = "";				//ユーザーID
	private String nm_user = "";			//ユーザー名称
	private String cd_kaisha = "";			//会社CD
	private String nm_kaisha = "";		//会社名称
	private String cd_busho = "";			//部署CD
	private String nm_busho = "";			//部署名称
	private String honbukengen = "";		//本部権限
	private String id_josi = "";				//上司ID
	private String nm_josi = "";			//上司名
	private int intMaxRow = 0;		//総件数
	private int intListRowMax = 0;	//頁内表示件数
	
	/**
	 * コンストラクタ
	 * @param xdtSetXml : XMLデータ
	 */
	public EigyoTantoData() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		this.id_user = null;
		this.nm_user = null;
		this.cd_kaisha = null;
		this.nm_kaisha = null;
		this.cd_busho = null;
		this.nm_busho = null;
		this.honbukengen = null;
		this.id_josi = null;
		this.nm_josi = null;
	}
	
	public String getNo_gyo() {
		return no_gyo;
	}

	public void setNo_gyo(String no_gyo) {
		this.no_gyo = no_gyo;
	}
	
	/**
	 * ユーザーID ゲッター
	 * @return id_user : ユーザーIDを返す
	 */
	public String getId_user() {
		return id_user;
	}
	/**
	 * ユーザーID セッター
	 * @param _id_user : ユーザーIDを格納する
	 */
	public void setId_user(String _id_user) {
		id_user = _id_user;
	}
	/**
	 * ユーザー名称 ゲッター
	 * @return nm_user : ユーザー名称を返す
	 */
	public String getNm_user() {
		return nm_user;
	}
	/**
	 * ユーザー名称 セッター
	 * @param _nm_user : ユーザー名称を格納する
	 */
	public void setNm_user(String _nm_user) {
		nm_user = _nm_user;
	}
	/**
	 * 会社コード ゲッター
	 * @return cd_kaisha : 会社CDを返す
	 */
	public String getCd_kaisha() {
		return cd_kaisha;
	}
	/**
	 * 会社コード セッター
	 * @param _cd_kaisha : 会社CDを格納する
	 */
	public void setCd_kaisha(String _cd_kaisha) {
		cd_kaisha = _cd_kaisha;
	}
	/**
	 * 会社名称 ゲッター
	 * @return nm_kaisha : 会社名称を返す
	 */
	public String getNm_kaisha() {
		return nm_kaisha;
	}
	/**
	 * 会社名称 セッター
	 * @param _nm_kaisha : 会社名称を格納する
	 */
	public void setNm_kaisha(String _nm_kaisha) {
		nm_kaisha = _nm_kaisha;
	}
	/**
	 * 部署コード ゲッター
	 * @return cd_busho : 部署CDを返す
	 */
	public String getCd_busho() {
		return cd_busho;
	}
	/**
	 * 部署コード セッター
	 * @param _cd_busho : 部署CDを格納する
	 */
	public void setCd_busho(String _cd_busho) {
		cd_busho = _cd_busho;
	}
	/**
	 * 部署名称 ゲッター
	 * @return nm_busho : 部署名称を返す
	 */
	public String getNm_busho() {
		return nm_busho;
	}
	/**
	 * 部署名称 セッター
	 * @param _nm_busho : 部署名称を格納する
	 */
	public void setNm_busho(String _nm_busho) {
		nm_busho = _nm_busho;
	}
	
	/**
	 * 本部権限 ゲッター
	 * @return honbukengen : 本部権限を返す
	 */
	public String getHonbukengen() {
		return honbukengen;
	}
	/**
	 * 本部権限 セッター
	 * @param honbukengen : 本部権限を格納する
	 */
	public void setHonbukengen(String honbukengen) {
		this.honbukengen = honbukengen;
	}
	
	/**
	 * 上司ID ゲッター
	 * @return id_josi : 上司IDを返す
	 */
	public String getId_josi() {
		return id_josi;
	}
	/**
	 * 上司ID セッター
	 * @param id_josi : 上司IDを格納する
	 */
	public void setId_josi(String id_josi) {
		this.id_josi = id_josi;
	}
	
	/**
	 * 上司名 ゲッター
	 * @return nm_josi : 上司IDを返す
	 */
	public String getNm_josi() {
		return nm_josi;
	}
	/**
	 * 上司名 セッター
	 * @param nm_josi : 上司IDを格納する
	 */
	public void setNm_josi(String nm_josi) {
		this.nm_josi = nm_josi;
	}
	
	/**
	 * 総件数 ゲッター
	 * @return intMaxRow : 総件数を返す
	 */
	public int getIntMaxRow() {
		return intMaxRow;
	}
	/**
	 * 総件数 セッター
	 * @param intMaxRow : 総件数を格納する
	 */
	public void setIntMaxRow(int intMaxRow) {
		this.intMaxRow = intMaxRow;
	}

	/**
	 * 頁内表示件数 ゲッター
	 * @return intMaxRow : 頁内表示件数を返す
	 */
	public int getIntListRowMax() {
		return intListRowMax;
	}
	/**
	 * 頁内表示件数 セッター
	 * @param intMaxRow : 頁内表示件数を格納する
	 */
	public void setIntListRowMax(int intListRowMax) {
		this.intListRowMax = intListRowMax;
	}
	

}
