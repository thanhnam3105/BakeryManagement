package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * 試作品データ保持
 *
 */
public class PrototypeData extends DataBase {

	private BigDecimal dciShisakuUser;		//試作CD-社員CD
	private BigDecimal dciShisakuYear;		//試作CD-年
	private BigDecimal dciShisakuNum;		//試作CD-追番
	private String strIrai;						//依頼番号
	private String strHinnm;					//品名
	private int intKaishacd;						//指定工場-会社CD
	private int intKojoco;						//指定工場-工場CD
	private String strShubetu;					//種別CD
	private String strShubetuNo;				//種別No
	private int intGroupcd;						//グループCD
	private int intTeamcd;						//チームCD
	private String strGroupNm;					//グループ名
	private String strTeamNm;					//チーム名
	private String strIkatu;						//一括表示CD
	private String strZyanru;					//ジャンルCD
	private String strUsercd;					//ユーザCD
	private String strTokutyo;					//特徴原料
	private String strYoto;						//用途
	private String strKakaku;					//価格帯CD
	private String strTantoEigyo;				//担当営業CD
	private String strSeizocd;					//製造方法CD
	private String strZyutencd;					//充填方法CD
	private String strSakin;						//殺菌方法
	private String strYokihozai;				//容器・包材
	private String strYoryo;						//容量
	private String strTani;						//容量単位CD
	private String strIrisu;						//入り数
	private String strOndo;						//取扱温度CD
	private String strShomi;						//賞味期間
	private String strGenka;					//原価
	private String strBaika;						//売価
	private String strSotei;						//想定物量
	private String strHatubai;					//発売時期
	private String strKeikakuUri;				//計画売上
	private String strKeikakuRie;				//計画利益
	private String strHanbaigoUri;				//販売後売上
	private String strHanbaigoRie;			//販売後利益
	private String strNishugata;				//荷姿CD
	private String strSogo;						//総合ﾒﾓ
	private String strShosu;						//小数指定
	private int intHaisi;							//廃止区
	private BigDecimal dciHaita;				//排他
	private int intSeihoShisaku;				//製法試作
	private String strShisakuMemo;			//試作メモ
	private int intChuiFg;						//注意事項表示
	private BigDecimal dciTorokuid;			//登録者ID
	private String strTorokuhi;					//登録日付
	private BigDecimal dciKosinId;			//更新者ID
	private String strKosinhi;					//更新日付
	private String strTorokuNm;				//登録者名
	private String strKosinNm;					//更新者名
	//2010/02/25 NAKAMURA ADD START-----------------------
	private String strHaitaKaishaNm;			//排他会社名
	private String strHaitaBushoNm;			//排他部署名
	private String strHaitaShimei;				//排他氏名
	//2010/02/25 NAKAMURA ADD END-------------------------
	
	//【QP@00342】
	private String strNmEigyoTanto;			//排他氏名
	
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	private String strPt_kotei;					//工程パターン
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
	
	//【QP@20505_No.38】2012/10/17 TT.SHIMA ADD Start
	private String strSecret;					//シークレットモードフラグ
	//【QP@20505_No.38】2012/10/17 TT.SHIMA ADD End
	
//	private ExceptionBase ex;					//エラーハンドリング
	
	/**
	 * コンストラクタ
	 * @param xdtSetXml : XMLデータ
	 */
	public PrototypeData() {
		//スーパークラスのコンストラクタ呼び出し
		super();
		
		this.dciShisakuUser = null;
		this.dciShisakuYear = null;
		this.dciShisakuNum = null;
		this.strIrai = null;
		this.strHinnm = null;
		this.intKaishacd = -1;
		this.intKojoco = -1;
		this.strShubetu = null;
		this.strShubetuNo = null;
		this.intGroupcd = -1;
		this.intTeamcd = -1;
		this.strIkatu = null;
		this.strZyanru = null;
		this.strUsercd = null;
		this.strTokutyo = null;
		this.strYoto = null;
		this.strKakaku = null;
		this.strTantoEigyo = null;
		this.strSeizocd = null;
		this.strZyutencd = null;
		this.strSakin = null;
		this.strYokihozai = null;
		this.strYoryo = null;
		this.strTani = null;
		this.strIrisu = null;
		this.strOndo = null;
		this.strShomi = null;
		this.strGenka = null;
		this.strBaika = null;
		this.strSotei = null;
		this.strHatubai = null;
		this.strKeikakuUri = null;
		this.strKeikakuRie = null;
		this.strHanbaigoUri = null;
		this.strHanbaigoRie = null;
		this.strNishugata = null;
		this.strSogo = null;
		this.strShosu = null;
		this.intHaisi = 0;
		this.dciHaita = null;
		this.intSeihoShisaku = 0;
		this.strShisakuMemo = null;
		this.intChuiFg = 0;
		this.dciTorokuid = null;
		this.strTorokuhi = null;
		this.strTorokuNm = null;
		this.dciKosinId = null;
		this.strKosinhi = null;
		this.strKosinNm = null;
		//2010/02/25 NAKAMURA ADD START-----------
		this.strHaitaKaishaNm = null;
		this.strHaitaBushoNm = null;
		this.strHaitaShimei = null;
		//2010/02/25 NAKAMURA ADD END-------------
		
		//【QP@00342】
		this.strNmEigyoTanto = null;
		
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
		this.strPt_kotei = null;
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
		
		//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
		this.strSecret = null;
		//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
	}
	
	/**
	 * 試作CD-社員CD ゲッター
	 * @return dciShisakuUser : 試作CD-社員CDの値を返す
	 */
	public BigDecimal getDciShisakuUser() {
		return dciShisakuUser;
	}
	/**
	 * 試作CD-社員CD セッター
	 * @param _dciShisakuUser : 試作CD-社員CDの値を格納する
	 */
	public void setDciShisakuUser(BigDecimal _dciShisakuUser) {
		this.dciShisakuUser = _dciShisakuUser;
	}

	/**
	 * 試作CD-年 ゲッター
	 * @return dciShisakuYear : 試作CD-年の値を返す
	 */
	public BigDecimal getDciShisakuYear() {
		return dciShisakuYear;
	}
	/**
	 * 試作CD-年 セッター
	 * @param _dciShisakuYear : 試作CD-年の値を格納する
	 */
	public void setDciShisakuYear(BigDecimal _dciShisakuYear) {
		this.dciShisakuYear = _dciShisakuYear;
	}

	/**
	 * 試作CD-追番 ゲッター
	 * @return dciShisakuNum : 試作CD-追番の値を返す
	 */
	public BigDecimal getDciShisakuNum() {
		return dciShisakuNum;
	}
	/**
	 * 試作CD-追番 セッター
	 * @param _dciShisakuNum : 試作CD-追番の値を格納する
	 */
	public void setDciShisakuNum(BigDecimal _dciShisakuNum) {
		this.dciShisakuNum = _dciShisakuNum;
	}

	/**
	 * 依頼番号 ゲッター
	 * @return strIrai : 依頼番号の値を返す
	 */
	public String getStrIrai() {
		return strIrai;
	}
	/**
	 * 依頼番号 セッター
	 * @param _strIrai : 依頼番号の値を格納する
	 */
	public void setStrIrai(String _strIrai) {
		this.strIrai = _strIrai;
	}

	/**
	 * 品名 ゲッター
	 * @return strHinnm : 品名の値を返す
	 */
	public String getStrHinnm() {
		return strHinnm;
	}
	/**
	 * 品名 セッター
	 * @param _strHinnm : 品名の値を格納する
	 */
	public void setStrHinnm(String _strHinnm) {
		this.strHinnm = _strHinnm;
	}

	/**
	 * 指定工場-会社CD ゲッター
	 * @return intKaishacd : 指定工場-会社CDの値を返す
	 */
	public int getIntKaishacd() {
		return intKaishacd;
	}
	/**
	 * 指定工場-会社CD セッター
	 * @param _intKaishacd : 指定工場-会社CDの値を格納する
	 */
	public void setIntKaishacd(int _intKaishacd) {
		this.intKaishacd = _intKaishacd;
	}

	/**
	 * 指定工場-工場CD ゲッター
	 * @return intKojoco : 指定工場-工場CDの値を返す
	 */
	public int getIntKojoco() {
		return intKojoco;
	}
	/**
	 * 指定工場-工場CD セッター
	 * @param _intKojoco : 指定工場-工場CDの値を格納する
	 */
	public void setIntKojoco(int _intKojoco) {
		this.intKojoco = _intKojoco;
	}

	/**
	 * 種別CD ゲッター
	 * @return strShubetu : 種別CDの値を返す
	 */
	public String getStrShubetu() {
		return strShubetu;
	}
	/**
	 * 種別CD セッター
	 * @param _strShubetu : 種別CDの値を格納する
	 */
	public void setStrShubetu(String _strShubetu) {
		this.strShubetu = _strShubetu;
	}
	
	/**
	 * 種別No ゲッター
	 * @return strShubetuNo : 種別Noの値を返す
	 */
	public String getStrShubetuNo() {
		return strShubetuNo;
	}
	/**
	 * 種別No セッター
	 * @param _intShubetuNo : 種別Noの値を格納する
	 */
	public void setStrShubetuNo(String _strShubetuNo) {
		if(_strShubetuNo!=null && _strShubetuNo.length() > 0 && _strShubetuNo.length() == 1){
			_strShubetuNo = "0" + _strShubetuNo;
		}
		this.strShubetuNo = _strShubetuNo;
	}

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
	 * チームCD ゲッター
	 * @return intTeamcd : チームCDの値を返す
	 */
	public int getIntTeamcd() {
		return intTeamcd;
	}
	/**
	 * チームCD セッター
	 * @param intTeamcd : チームCDの値を格納する
	 */
	public void setIntTeamcd(int _intTeamcd) {
		this.intTeamcd = _intTeamcd;
	}

	/**
	 * 一括表示CD ゲッター
	 * @return strIkatu : 一括表示CDの値を返す
	 */
	public String getStrIkatu() {
		return strIkatu;
	}
	/**
	 * 一括表示CD セッター
	 * @param _strIkatu : 一括表示CDの値を格納する
	 */
	public void setStrIkatu(String _strIkatu) {
		this.strIkatu = _strIkatu;
	}

	/**
	 * ジャンルCD ゲッター
	 * @return strZyanru : ジャンルCDの値を返す
	 */
	public String getStrZyanru() {
		return strZyanru;
	}
	/**
	 * ジャンルCD セッター
	 * @param strZyanru : ジャンルCDの値を格納する
	 */
	public void setStrZyanru(String _strZyanru) {
		this.strZyanru = _strZyanru;
	}

	/**
	 * ユーザCD ゲッター
	 * @return strUsercd : ユーザCDの値を返す
	 */
	public String getStrUsercd() {
		return strUsercd;
	}
	/**
	 * ユーザCD セッター
	 * @param _strUsercd : ユーザCDの値を格納する
	 */
	public void setStrUsercd(String _strUsercd) {
		this.strUsercd = _strUsercd;
	}

	/**
	 * 特徴原料 ゲッター
	 * @return strTokutyo : 特徴原料の値を返す
	 */
	public String getStrTokutyo() {
		return strTokutyo;
	}
	/**
	 * 特徴原料 セッター
	 * @param _strTokutyo : 特徴原料の値を格納する
	 */
	public void setStrTokutyo(String _strTokutyo) {
		this.strTokutyo = _strTokutyo;
	}

	/**
	 * 用途 ゲッター
	 * @return strYoto : 用途の値を返す
	 */
	public String getStrYoto() {
		return strYoto;
	}
	/**
	 * 用途 セッター
	 * @param _strYoto : 用途の値を格納する
	 */
	public void setStrYoto(String _strYoto) {
		this.strYoto = _strYoto;
	}

	/**
	 * 価格帯CD ゲッター
	 * @return strKakaku : 価格帯CDの値を返す
	 */
	public String getStrKakaku() {
		return strKakaku;
	}
	/**
	 * 価格帯CD セッター
	 * @param _strKakaku : 価格帯CDの値を格納する
	 */
	public void setStrKakaku(String _strKakaku) {
		this.strKakaku = _strKakaku;
	}

	/**
	 * 担当営業CD ゲッター
	 * @return strTantoEigyo : 担当営業CDの値を返す
	 */
	public String getStrTantoEigyo() {
		return strTantoEigyo;
	}
	/**
	 * 担当営業CD セッター
	 * @param _strTantoEigyo : 担当営業CDの値を格納する
	 */
	public void setStrTantoEigyo(String _strTantoEigyo) {
		this.strTantoEigyo = _strTantoEigyo;
	}

	/**
	 * 製造方法CD ゲッター
	 * @return strSeizocd : 製造方法CDの値を返す
	 */
	public String getStrSeizocd() {
		return strSeizocd;
	}
	/**
	 * 製造方法CD セッター
	 * @param _strSeizocd : 製造方法CDの値を格納する
	 */
	public void setStrSeizocd(String _strSeizocd) {
		this.strSeizocd = _strSeizocd;
	}

	/**
	 * 充填方法CD ゲッター
	 * @return strZyutencd : 充填方法CDの値を返す
	 */
	public String getStrZyutencd() {
		return strZyutencd;
	}
	/**
	 * 充填方法CD セッター
	 * @param _strZyutencd : 充填方法CDの値を格納する
	 */
	public void setStrZyutencd(String _strZyutencd) {
		this.strZyutencd = _strZyutencd;
	}

	/**
	 * 殺菌方法 ゲッター
	 * @return strSakin : 殺菌方法の値を返す
	 */
	public String getStrSakin() {
		return strSakin;
	}
	/**
	 * 殺菌方法 セッター
	 * @param _strSakin : 殺菌方法の値を格納する
	 */
	public void setStrSakin(String _strSakin) {
		this.strSakin = _strSakin;
	}

	/**
	 * 容器・包材 ゲッター
	 * @return strYokihozai : 容器・包材の値を返す
	 */
	public String getStrYokihozai() {
		return strYokihozai;
	}
	/**
	 * 容器・包材 セッター
	 * @param _strYokihozai : 容器・包材の値を格納する
	 */
	public void setStrYokihozai(String _strYokihozai) {
		this.strYokihozai = _strYokihozai;
	}

	/**
	 * 容量 ゲッター
	 * @return strYoryo : 容量の値を返す
	 */
	public String getStrYoryo() {
		return strYoryo;
	}
	/**
	 * 容量 セッター
	 * @param _strYoryo : 容量の値を格納する
	 */
	public void setStrYoryo(String _strYoryo) {
		this.strYoryo = _strYoryo;
	}

	/**
	 * 容量単位CD ゲッター
	 * @return strTani : 容量単位CDの値を返す
	 */
	public String getStrTani() {
		return strTani;
	}
	/**
	 * 容量単位CD セッター
	 * @param _strTani : 容量単位CDの値を格納する
	 */
	public void setStrTani(String _strTani) {
		this.strTani = _strTani;
	}

	/**
	 * 入り数 ゲッター
	 * @return strIrisu : 入り数の値を返す
	 */
	public String getStrIrisu() {
		return strIrisu;
	}
	/**
	 * 入り数 セッター
	 * @param _strIrisu : 入り数の値を格納する
	 */
	public void setStrIrisu(String _strIrisu) {
		this.strIrisu = _strIrisu;
	}

	/**
	 * 取扱温度CD ゲッター
	 * @return strOndo : 取扱温度CDの値を返す
	 */
	public String getStrOndo() {
		return strOndo;
	}
	/**
	 * 取扱温度CD セッター
	 * @param _strOndo : 取扱温度CDの値を格納する
	 */
	public void setStrOndo(String _strOndo) {
		this.strOndo = _strOndo;
	}

	/**
	 * 賞味期間 ゲッター
	 * @return strShomi : 賞味期間の値を返す
	 */
	public String getStrShomi() {
		return strShomi;
	}
	/**
	 * 賞味期間 セッター
	 * @param _strShomi : 賞味期間の値を格納する
	 */
	public void setStrShomi(String _strShomi) {
		this.strShomi = _strShomi;
	}

	/**
	 * 原価 ゲッター
	 * @return strGenka : 原価の値を返す
	 */
	public String getStrGenka() {
		return strGenka;
	}
	/**
	 * 原価 セッター
	 * @param _strGenka : 原価の値を格納する
	 */
	public void setStrGenka(String _strGenka) {
		this.strGenka = _strGenka;
	}

	/**
	 * 売価 ゲッター
	 * @return strBaika : 売価の値を返す
	 */
	public String getStrBaika() {
		return strBaika;
	}
	/**
	 * 売価 セッター
	 * @param _strBaika : 売価の値を格納する
	 */
	public void setStrBaika(String _strBaika) {
		this.strBaika = _strBaika;
	}

	/**
	 * 想定物量 ゲッター
	 * @return strSotei : 想定物量の値を返す
	 */
	public String getStrSotei() {
		return strSotei;
	}
	/**
	 * 想定物量 セッター
	 * @param _strSotei : 想定物量の値を格納する
	 */
	public void setStrSotei(String _strSotei) {
		this.strSotei = _strSotei;
	}

	/**
	 * 発売時期 ゲッター
	 * @return strHatubai : 発売時期の値を返す
	 */
	public String getStrHatubai() {
		return strHatubai;
	}
	/**
	 * 発売時期 セッター
	 * @param _strHatubai : 発売時期の値を格納する
	 */
	public void setStrHatubai(String _strHatubai) {
		this.strHatubai = _strHatubai;
	}

	/**
	 * 計画売上 ゲッター
	 * @return strKeikakuUri : 計画売上の値を返す
	 */
	public String getStrKeikakuUri() {
		return strKeikakuUri;
	}
	/**
	 * 計画売上 セッター
	 * @param _strKeikakuUri : 計画売上の値を格納する
	 */
	public void setStrKeikakuUri(String _strKeikakuUri) {
		this.strKeikakuUri = _strKeikakuUri;
	}

	/**
	 * 計画利益 ゲッター
	 * @return strKeikakuRie : 計画利益の値を返す
	 */
	public String getStrKeikakuRie() {
		return strKeikakuRie;
	}
	/**
	 * 計画利益 セッター
	 * @param _strKeikakuRie : 計画利益の値を格納する
	 */
	public void setStrKeikakuRie(String _strKeikakuRie) {
		this.strKeikakuRie = _strKeikakuRie;
	}

	/**
	 * 販売後売上 ゲッター
	 * @return strHanbaigoUri : 販売後売上の値を返す
	 */
	public String getStrHanbaigoUri() {
		return strHanbaigoUri;
	}
	/**
	 * 販売後売上 セッター
	 * @param _strHanbaigoUri : 販売後売上の値を格納する
	 */
	public void setStrHanbaigoUri(String _strHanbaigoUri) {
		this.strHanbaigoUri = _strHanbaigoUri;
	}

	/**
	 * 販売後利益 ゲッター
	 * @return strHanbaigoRie : 販売後利益の値を返す
	 */
	public String getStrHanbaigoRie() {
		return strHanbaigoRie;
	}
	/**
	 * 販売後利益 セッター
	 * @param _strHanbaigoRie : 販売後利益の値を格納する
	 */
	public void setStrHanbaigoRie(String _strHanbaigoRie) {
		this.strHanbaigoRie = _strHanbaigoRie;
	}

	/**
	 * 荷姿CD ゲッター
	 * @return strNishugata : 荷姿CDの値を返す
	 */
	public String getStrNishugata() {
		return strNishugata;
	}
	/**
	 * 荷姿CD セッター
	 * @param _strNishugata : 荷姿CDの値を格納する
	 */
	public void setStrNishugata(String _strNishugata) {
		this.strNishugata = _strNishugata;
	}

	/**
	 * 総合ﾒﾓ ゲッター
	 * @return strSogo : 総合ﾒﾓの値を返す
	 */
	public String getStrSogo() {
		return strSogo;
	}
	/**
	 * 総合ﾒﾓ セッター
	 * @param _strSogo : 総合ﾒﾓの値を格納する
	 */
	public void setStrSogo(String _strSogo) {
		this.strSogo = _strSogo;
	}

	/**
	 * 小数指定 ゲッター
	 * @return strShosu : 小数指定のコードを返す
	 */
	public String getStrShosu() {
		return strShosu;
	}
	/**
	 * 小数指定 セッター
	 * @param _intShosu : 小数指定の値を格納する
	 */
	public void setStrShosu(String _strShosu) {
		this.strShosu = _strShosu;
	}

	/**
	 * 廃止区 ゲッター
	 * @return intHaisi : 廃止区の値を返す
	 */
	public int getIntHaisi() {
		return intHaisi;
	}
	/**
	 * 廃止区 セッター
	 * @param _intHaisi : 廃止区の値を格納する
	 */
	public void setIntHaisi(int _intHaisi) {
		this.intHaisi = _intHaisi;
	}
	
	/**
	 * 排他区分 ゲッター
	 * @return dciHaita : 排他区分の値を返す
	 */
	public BigDecimal getDciHaita() {
		return dciHaita;
	}

	/**
	 * 排他区分 セッター
	 * @param _dciHaita : 排他区分の値を格納する
	 */
	public void setDciHaita(BigDecimal _dciHaita) {
		this.dciHaita = _dciHaita;
	}
	
	/**
	 * @return intSeihoShisaku
	 */
	public int getIntSeihoShisaku() {
		return intSeihoShisaku;
	}

	/**
	 * @param intSeihoShisaku セットする intSeihoShisaku
	 */
	public void setIntSeihoShisaku(int intSeihoShisaku) {
		this.intSeihoShisaku = intSeihoShisaku;
	}
	
	/**
	 * 試作メモ ゲッター
	 * @return strShisakuMemo : 試作メモの値を返す
	 */
	public String getStrShisakuMemo() {
		return strShisakuMemo;
	}

	/**
	 * 試作メモ セッター
	 * @param strShisakuMemo : 試作メモの値を格納する
	 */
	public void setStrShisakuMemo(String strShisakuMemo) {
		this.strShisakuMemo = strShisakuMemo;
	}
	
	/**
	 * 注意事項表示 ゲッター
	 * @return intChuiFg : 注意事項表示の値を返す
	 */
	public int getIntChuiFg() {
		return intChuiFg;
	}

	/**
	 * 注意事項表示 セッター
	 * @param intChuiFg : 注意事項表示の値を格納する
	 */
	public void setIntChuiFg(int intChuiFg) {
		this.intChuiFg = intChuiFg;
	}

	/**
	 * 登録者ID ゲッター
	 * @return dciTorokuid : 登録者IDの値を返す
	 */
	public BigDecimal getDciTorokuid() {
		return dciTorokuid;
	}
	/**
	 * 登録者ID セッター
	 * @param _dciTorokuid : 登録者IDの値を格納する
	 */
	public void setDciTorokuid(BigDecimal _dciTorokuid) {
		this.dciTorokuid = _dciTorokuid;
	}

	/**
	 * 登録日付 ゲッター
	 * @return strTorokuhi : 登録日付の値を返す
	 */
	public String getStrTorokuhi() {
		return strTorokuhi;
	}
	/**
	 * 登録日付 セッター
	 * @param _strTorokuhi : 登録日付の値を格納する
	 */
	public void setStrTorokuhi(String _strTorokuhi) {
		this.strTorokuhi = _strTorokuhi;
	}

	/**
	 * 更新者ID ゲッター
	 * @return dciKosinId : 更新者IDの値を返す
	 */
	public BigDecimal getDciKosinId() {
		return dciKosinId;
	}
	/**
	 * 更新者ID セッター
	 * @param _dciKosinId : 更新者IDの値を格納する
	 */
	public void setDciKosinid(BigDecimal _dciKosinId) {
		this.dciKosinId = _dciKosinId;
	}

	/**
	 * 更新日付 ゲッター
	 * @return strKosinhi : 更新日付の値を返す
	 */
	public String getStrKosinhi() {
		return strKosinhi;
	}
	/**
	 * 更新日付 セッター
	 * @param _strKosinhi : 更新日付の値を格納する
	 */
	public void setStrKosinhi(String _strKosinhi) {
		this.strKosinhi = _strKosinhi;
	}
	
	/**
	 * 登録者名 ゲッター
	 * @return strTorokuNm : 登録者名の値を返す
	 */
	public String getStrTorokuNm() {
		return strTorokuNm;
	}
	/**
	 * 登録者名 セッター
	 * @param _strTorokuNm : 登録者名の値を格納する
	 */
	public void setStrTorokuNm(String _strTorokuNm) {
		this.strTorokuNm = _strTorokuNm;
	}
	
	/**
	 * 更新者名 ゲッター
	 * @return strKosinNm : 更新者名の値を返す
	 */
	public String getStrKosinNm() {
		return strKosinNm;
	}
	/**
	 * 更新者名 セッター
	 * @param _strKosinNm : 更新者名の値を格納する
	 */
	public void setStrKosinNm(String _strKosinNm) {
		this.strKosinNm = _strKosinNm;
	}
	
	/**
	 * グループ名 ゲッター
	 * @return strGroupNm : 登録者名の値を返す
	 */
	public String getStrGroupNm() {
		return strGroupNm;
	}
	/**
	 * グループ名 セッター
	 * @param _strGroupNm : 登録者名の値を格納する
	 */
	public void setStrGroupNm(String _strGroupNm) {
		this.strGroupNm = _strGroupNm;
	}
	
	/**
	 * チーム名 ゲッター
	 * @return strTeamNm : 更新者名の値を返す
	 */
	public String getStrTeamNm() {
		return strTeamNm;
	}
	/**
	 * チーム名 セッター
	 * @param _strTeamNm : 更新者名の値を格納する
	 */
	public void setStrTeamNm(String _strTeamNm) {
		this.strTeamNm = _strTeamNm;
	}
	
	//2010/02/25 NAKAMURA ADD START---------------------
	/**
	 * 排他会社名 ゲッター
	 * @return strHaitaKaishaNm : 排他会社名の値を返す
	 */
	public String getStrHaitaKaishaNm() {
		return strHaitaKaishaNm;
	}
	/**
	 * 排他会社名 セッター
	 * @param _strHaitaKaishaNm : 排他会社名の値を格納する
	 */
	public void setStrHaitaKaishaNm(String _strHaitaKaishaNm) {
		this.strHaitaKaishaNm = _strHaitaKaishaNm;
	}
	
	/**
	 * 排他部署名 ゲッター
	 * @return strHaitaBushoNm : 排他部署名の値を返す
	 */
	public String getStrHaitaBushoNm() {
		return strHaitaBushoNm;
	}
	/**
	 * 排他部署名 セッター
	 * @param _strHaitaBushoNm : 排他部署名の値を格納する
	 */
	public void setStrHaitaBushoNm(String _strHaitaBushoNm) {
		this.strHaitaBushoNm = _strHaitaBushoNm;
	}	
	
	/**
	 * 排他氏名 ゲッター
	 * @return strHaitaShimei : 排他氏名の値を返す
	 */
	public String getStrHaitaShimei() {
		return strHaitaShimei;
	}
	/**
	 * 排他氏名　セッター
	 * @param _strHaitaShimei : 排他氏名の値を格納する
	 */
	public void setStrHaitaShimei(String _strHaitaShimei) {
		this.strHaitaShimei = _strHaitaShimei;
	}	
	//2010/02/25 NAKAMURA ADD END-----------------------
	
	//【QP@00342】
	/**
	 * 担当者営業名 ゲッター
	 * @return strNmEigyoTanto : 担当者営業名の値を返す
	 */
	public String getStrNmEigyoTanto() {
		return strNmEigyoTanto;
	}
	/**
	 * 担当者営業名 セッター
	 * @param strNmEigyoTanto : 担当者営業名の値を格納する
	 */
	public void setStrNmEigyoTanto(String strNmEigyoTanto) {
		this.strNmEigyoTanto = strNmEigyoTanto;
	}
	
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	/**
	 * 工程パターン ゲッター
	 * @return strPt_kotei : 工程パターンの値を返す
	 */
	public String getStrPt_kotei() {
		return strPt_kotei;
	}
	/**
	 * 工程パターン セッター
	 * @param strPt_kotei : 工程パターンの値を格納する
	 */
	public void setStrPt_kotei(String strPt_kotei) {
		this.strPt_kotei = strPt_kotei;
	}
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
	/**
	 * シークレット ゲッター
	 * @return strSecret : シークレットモードの値を返す
	 */
	public String getStrSecret() {
		return strSecret;
	}
	/**
	 * シークレット セッター
	 * @param strSecret : シークレットモードの値を格納する
	 */
	public void setStrSecret(String strSecret) {
		this.strSecret = strSecret;
	}
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

}
