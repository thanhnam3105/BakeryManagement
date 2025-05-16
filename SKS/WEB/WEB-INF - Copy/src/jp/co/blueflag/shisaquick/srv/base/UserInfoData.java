package jp.co.blueflag.shisaquick.srv.base;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpSession;

import jp.co.blueflag.shisaquick.srv.base.SearchBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 
 * UserInfoData
 *  : ユーザー基本情報データ(セッション)を保持する
 * 
 * @author TT.furuta
 * @since  2009/03/28
 */
public class UserInfoData extends SearchBaseDao{

	private String systemversion = "";	//システムバージョン
	private String id_user = "";			//ユーザーID
	private String nm_user = "";			//ユーザー名称
	private String cd_kaisha = "";		//会社CD
	private String nm_kaisha = "";		//会社名称
	private String cd_busho = "";		//部署CD
	private String nm_busho = "";		//部署名称
	private String cd_group = "";		//グループCD
	private String nm_group = "";		//グループ名
	private String cd_team = "";			//チームCD
	private String nm_team = "";			//チーム名
	private String cd_literal = "";		//リテラルCD
	private String nm_literal = "";		//リテラル名
	private ArrayList<String> id_gamen;		//画面ID
	private ArrayList<String> id_kino;		//機能ID
	private ArrayList<String> id_data;		//データID
	private ArrayList<String> cd_tantokaisha;	//製造担当会社コード
	private ArrayList<String> nm_tantokaisha;	//製造担当会社名
	
	//JSP起動条件
	private ArrayList<String> movement_condition;
	
	/**
	 * システムバージョン ゲッター
	 * @return systemversion : システムバージョンを返す
	 */
	public String getSystemversion() {
		return systemversion;
	}
	/**
	 * システムバージョン セッター
	 * @param _systemversion : システムバージョンを格納する
	 */
	public void setSystemversion(String _systemversion) {
		systemversion = _systemversion;
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
	 * グループコード ゲッター
	 * @return cd_group : グループCDを返す
	 */
	public String getCd_group() {
		return cd_group;
	}
	/**
	 * グループコード セッター
	 * @param _cd_group : グループCDを格納する
	 */
	public void setCd_group(String _cd_group) {
		cd_group = _cd_group;
	}
	/**
	 * グループ名 ゲッター
	 * @return nm_group : グループ名を返す
	 */
	public String getNm_group() {
		return nm_group;
	}
	/**
	 * グループ名 セッター
	 * @param _nm_group : グループ名を格納する
	 */
	public void setNm_group(String _nm_group) {
		nm_group = _nm_group;
	}
	/**
	 * チームコード ゲッター
	 * @return cd_team : チームCDを返す
	 */
	public String getCd_team() {
		return cd_team;
	}
	/**
	 * チームコード セッター
	 * @param _cd_team : チームCDを格納する
	 */
	public void setCd_team(String _cd_team) {
		cd_team = _cd_team;
	}
	/**
	 * チーム名称 ゲッター
	 * @return nm_team : チーム名称を返す
	 */
	public String getNm_team() {
		return nm_team;
	}
	/**
	 * チーム名称 セッター
	 * @param _nm_team : チーム名称を格納する
	 */
	public void setNm_team(String _nm_team) {
		nm_team = _nm_team;
	}
	/**
	 * リテラルコード ゲッター
	 * @return cd_literal : リテラルコードを返す
	 */
	public String getCd_literal() {
		return cd_literal;
	}
	/**
	 * リテラルコード セッター
	 * @param _cd_literal : リテラルコードを格納する
	 */
	public void setCd_literal(String _cd_literal) {
		cd_literal = _cd_literal;
	}
	/**
	 * リテラル名称 ゲッター
	 * @return nm_literal : リテラル名を返す
	 */
	public String getNm_literal() {
		return nm_literal;
	}
	/**
	 * リテラル名称 セッター
	 * @param _nm_literal : リテラル名を格納する
	 */
	public void setNm_literal(String _nm_literal) {
		nm_literal = _nm_literal;
	}
	/**
	 * 画面ID ゲッター
	 * @return id_gamen : 画面IDを返す
	 */
	public ArrayList<?> getId_gamen() {
		return id_gamen;
	}
	/**
	 * 画面ID セッター
	 * @param _id_gamen : 画面IDを格納する
	 */
	public void setId_gamen(ArrayList<String> _id_gamen) {
		id_gamen = _id_gamen;
	}
	/**
	 * 機能ID ゲッター
	 * @return id_kino : 機能IDを返す
	 */
	public ArrayList<?> getId_kino() {
		return id_kino;
	}
	/**
	 * 機能ID セッター
	 * @param _id_kino : 機能IDを格納する
	 */
	public void setId_kino(ArrayList<String> _id_kino) {
		id_kino = _id_kino;
	}
	/**
	 * データID ゲッター
	 * @return id_data : データIDを返す
	 */
	public ArrayList<?> getId_data() {
		return id_data;
	}
	/**
	 * データID セッター
	 * @param _id_data : データIDを格納する
	 */
	public void setId_data(ArrayList<String> _id_data) {
		id_data = _id_data;
	}
	/**
	 * jsp起動条件 ゲッター
	 * @return id_data : データIDを返す
	 */
	public ArrayList<?> getMovement_condition() {
		return movement_condition;
	}
	/**
	 * jsp起動条件 セッター
	 * @param _id_data : データIDを格納する
	 */
	public void setMovement_condition(ArrayList<String> _Movement_condition) {
		movement_condition = _Movement_condition;
	}
	/**
	 * 製造担当会社コード ゲッター
	 * @return id_data : 製造担当会社コードを返す
	 */
	public ArrayList<?> getCd_tantokaisha() {
		return cd_tantokaisha;
	}
	/**
	 * 製造担当会社コード セッター
	 * @param _id_data : 製造担当会社コードを格納する
	 */
	public void setCd_tantokaisha(ArrayList<String> _cd_tantokaisha) {
		cd_tantokaisha = _cd_tantokaisha;
	}
	/**
	 * 製造担当会社名 ゲッター
	 * @return id_data : 製造担当会社名を返す
	 */
	public ArrayList<?> getNm_tantokaisha() {
		return nm_tantokaisha;
	}
	/**
	 * 製造担当会社名 セッター
	 * @param _id_data : 製造担当会社名を格納する
	 */
	public void setNm_tantokaisha(ArrayList<String> _nm_tantokaisha) {
		nm_tantokaisha = _nm_tantokaisha;
	}

	//セッションクラス
	private HttpSession session;
	//ユーザー情報リスト
	private List<?> lstUserInfo;
	//ユーザー権限情報
	private List<?> lstUserKengenInfo;

	/**
	 * コンストラクタ
	 *  : セッションチェックコンストラクタ
	 * @param session : セッション
	 */
	public UserInfoData(HttpSession _session) {
		//基底ｸﾗｽのコンストラクタ
		super(DBCategory.DB1);
		
		//クラス変数のクリア
		session = null;
		lstUserInfo = null;
		lstUserKengenInfo = null;
		
		//セッション情報をメンバに格納
		session = _session;

	}

	/**
	 * ユーザー情報参照メイン処理
	 *  : 処理区分に応じて、セッション情報とUserInfoDataの更新を行う
	 * @param strShoriKbn : 処理区分
	 * @param strUserID：ユーザーID
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void CreatUserInfo(String strShoriKbn, String strUserId) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			
			//処理区分を判定する
			if (strShoriKbn.equals("1")){
				//処理区分1：通常時
				//SessionよりUserInfoDataを生成する

				//１）セッションよりユーザー情報/権限を取得しUserInfioDataに格納
				GetUserInfo_Session();
	
			} else if (strShoriKbn.equals("2")){
				//処理区分2：ログイン
				//DBよりUserInfoDataを生成する
				//UserInfoDataの情報をSessionに格納する

				//１）DBよりユーザー情報/権限を取得しUserInfioDataに格納
				GetUserInfo_DB(strUserId);
				//２）UserInfioDataよりSessionに格納
				SetUserInfo_Session();
				
			//処理区分：JWS時
			} else if (strShoriKbn.equals("3")){
				//処理区分3：jws用
				//DBよりUserInfoDataを生成する

				//１）DBよりユーザー情報/権限を取得しUserInfioDataに格納
				GetUserInfo_DB(strUserId);
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "ユーザー情報参照に失敗しました。");
		} finally {
			
		}
	}
	/**
	 * インスタンス変数のリストを破棄する。
	 */
	public void removeList(){
		//画面ID		
		removeList(id_gamen);
		id_gamen = null;
		//機能ID
		removeList(id_kino);
		id_kino = null;
		//データID
		removeList(id_data);
		id_data = null;
		//製造担当会社コード
		removeList(cd_tantokaisha);
		cd_tantokaisha = null;
		//製造担当会社名
		removeList(nm_tantokaisha);
		nm_tantokaisha = null;
		
	}

	/**
	 * セッション情報格納
	 *  : UserInfoDataの情報をSessionに格納する。
	 *  @throws ExceptionSystem
	 *  @throws ExceptionUser
	 *  @throws ExceptionWaning
	 */
	private void SetUserInfo_Session() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			
			//システムバージョン設定
			session.setAttribute("systemversion", ConstManager.getConstValue(Category.設定, "SYSTEM_VERSION"));
			//ユーザーＩＤ設定
			session.setAttribute("id_user", id_user);
			//ユーザー名設定
			session.setAttribute("nm_user", nm_user);
			//会社コード設定
			session.setAttribute("cd_kaisha", cd_kaisha);
			//会社名設定
			session.setAttribute("nm_kaisha", nm_kaisha);
			//部署コード設定
			session.setAttribute("cd_busho", cd_busho);
			//部署名設定
			session.setAttribute("nm_busho", nm_busho);
			//グループコード設定
			session.setAttribute("cd_group", cd_group);
			//グループ名設定
			session.setAttribute("nm_group", nm_group);
			//チームコード設定
			session.setAttribute("cd_team", cd_team);
			//チーム名設定
			session.setAttribute("nm_team", nm_team);
			//役職コード設定
			session.setAttribute("cd_literal", cd_literal);
			//役職名設定
			session.setAttribute("nm_literal", nm_literal);
			//製造担当会社コード設定
			session.setAttribute("cd_tantokaisha", ListToString(cd_tantokaisha, ":::"));
			//製造担当会社名設定
			session.setAttribute("nm_tantokaisha", ListToString(nm_tantokaisha, ":::"));
			//画面ID設定
			session.setAttribute("id_gamen", ListToString(id_gamen, ":::"));
			//機能ID
			session.setAttribute("id_kino", ListToString(id_kino, ":::"));
			//データID設定
			session.setAttribute("id_data", ListToString(id_data, ":::"));

						
		} catch (Exception e) {
			em.ThrowException(e, "ユーザー情報セットに失敗しました。");

		} finally {
			
		}

	}
	/**
	 * ユーザー情報検索
	 *  : Sessionよりユーザー情報を取得する。
	 *  @throws ExceptionSystem
	 *  @throws ExceptionUser
	 *  @throws ExceptionWaning
	 */
	private void GetUserInfo_Session() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//システムバージョン設定
			setSystemversion(session.getAttribute("systemversion").toString());
			//ユーザーＩＤ設定
			setId_user(session.getAttribute("id_user").toString());
			//ユーザー名設定
			setNm_user(session.getAttribute("nm_user").toString());
			//会社コード設定
			setCd_kaisha(session.getAttribute("cd_kaisha").toString());
			//会社名設定
			setNm_kaisha(session.getAttribute("nm_kaisha").toString());
			//部署コード設定
			setCd_busho(session.getAttribute("cd_busho").toString());
			//部署名設定
			setNm_busho(session.getAttribute("nm_busho").toString());
			//グループコード設定
			setCd_group(session.getAttribute("cd_group").toString());
			//グループ名設定
			setNm_group(session.getAttribute("nm_group").toString());
			//チームコード設定
			setCd_team(session.getAttribute("cd_team").toString());
			//チーム名設定
			setNm_team(session.getAttribute("nm_team").toString());
			//役職コード設定
			setCd_literal(session.getAttribute("cd_literal").toString());
			//役職名設定
			setNm_literal(session.getAttribute("nm_literal").toString());
			//製造担当会社コード設定
			setCd_tantokaisha(StringToList(session.getAttribute("cd_tantokaisha").toString(), ":::"));		
			//製造担当会社名設定
			setNm_tantokaisha(StringToList(session.getAttribute("nm_tantokaisha").toString(), ":::"));		
			//画面ID設定
			setId_gamen(StringToList(session.getAttribute("id_gamen").toString(), ":::"));		
			//機能ID
			setId_kino(StringToList(session.getAttribute("id_kino").toString(), ":::"));		
			//データID設定
			setId_data(StringToList(session.getAttribute("id_data").toString(), ":::"));
			
		} catch (Exception e) {
			em.ThrowException(e, "ユーザー基本情報参照に失敗しました。");

		} finally {
			
		}

	}
	/**
	 * ユーザー情報検索
	 *  : DBよりユーザー情報を取得する。
	 * @param strUserId
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void GetUserInfo_DB(String strUserId) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		ArrayList<String> arrTantoKaishaCd = null;
		ArrayList<String> arrTantoKaishaNm  = null;
		ArrayList<String> arrGamenId = null;
		ArrayList<String> arrKinoId  = null;
		ArrayList<String> arrDataId  = null;
		
		try{
			//ユーザー基本情報をDBより取得
			GetUserInfo_DB_Kihon(strUserId);
			//ユーザー権限情報をDBより取得
			GetUserInfo_DB_Kengen(strUserId);
			
			//UserInfoDataにユーザー基本情報/権限情報を格納する
			arrTantoKaishaCd = new ArrayList<String>();
			arrTantoKaishaNm  = new ArrayList<String>();
			arrGamenId = new ArrayList<String>();
			arrKinoId  = new ArrayList<String>();
			arrDataId  = new ArrayList<String>();

			//リスト要素数ループ
			for (int i=0;i<lstUserInfo.size();i++){

				Object[] items = (Object[]) lstUserInfo.get(i);

				int index = 0;
				//システムバージョン設定
				setSystemversion(ConstManager.getConstValue(Category.設定, "SYSTEM_VERSION"));
				//ユーザーＩＤ設定
				setId_user(items[index++].toString());
				//ユーザー名設定
				setNm_user(items[index++].toString());
				//会社コード設定
				setCd_kaisha(items[index++].toString());
				//会社名設定
				setNm_kaisha(items[index++].toString());
				//部署コード設定
				setCd_busho(items[index++].toString());
				//部署名設定
				setNm_busho(items[index++].toString());
				//グループコード設定
				setCd_group(items[index++].toString());
				//グループ名設定
				setNm_group(items[index++].toString());
				//チームコード設定
				setCd_team(items[index++].toString());
				//チーム名設定
				setNm_team(items[index++].toString());
				//役職コード設定
				setCd_literal(items[index++].toString());
				//役職名設定
				setNm_literal(items[index++].toString());
				//製造担当会社コード設定
				arrTantoKaishaCd.add(items[index++].toString());		
				//製造担当会社名設定
				arrTantoKaishaNm.add(items[index++].toString());

			}
			
			//製造担当会社コード設定
			setCd_tantokaisha(arrTantoKaishaCd);		
			//製造担当会社名設定
			setNm_tantokaisha(arrTantoKaishaNm);

			//リスト要素数ループ
			for (int i=0;i<lstUserKengenInfo.size();i++){

				Object[] items = (Object[]) lstUserKengenInfo.get(i);

				int index = 0;
				//画面ID設定
				arrGamenId.add(items[index++].toString());		
				//機能ID
				arrKinoId.add(items[index++].toString());
				//データID設定
				arrDataId.add(items[index++].toString());
				
			}

			//画面ID設定
			setId_gamen(arrGamenId);		
			//機能ID
			setId_kino(arrKinoId);
			//データID設定
			setId_data(arrDataId);
			
		} catch (Exception e) {
			em.ThrowException(e, "ユーザー基本情報参照に失敗しました。");

		} finally {
			//DBセッションのクローズ
			Close();

		}
		
	}
	/**
	 * ユーザー基本情報検索
	 *  : DBよりユーザー基本情報を検索する。
	 *  @param strUserID：ユーザーID
	 *  @throws ExceptionSystem
	 *  @throws ExceptionUser
	 *  @throws ExceptionWaning
	 */
	private void GetUserInfo_DB_Kihon(String strUserId) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();
		lstUserInfo = null;

		//DBセッションを生成
		//SearchBaseDao searchDB = new SearchBaseDao(DBCategory.DB1);
		
		try {
			//③：SQL作成
			strSQL.append("SELECT DISTINCT ");
			strSQL.append("A.id_user AS id_user, ");
			strSQL.append("A.nm_user AS nm_user, ");
			strSQL.append("ISNULL(B.cd_kaisha, 0) as cd_kaisha, ");
			strSQL.append("ISNULL(B.nm_kaisha, '') as nm_kaisha, ");
			strSQL.append("ISNULL(B.cd_busho, 0) as cd_busho, ");
			strSQL.append("ISNULL(B.nm_busho, '') as nm_busho, ");
			strSQL.append("ISNULL(C.cd_group, 0) as cd_group, ");
			strSQL.append("ISNULL(C.nm_group, '') as nm_group, ");
			strSQL.append("ISNULL(D.cd_team, 0) as cd_team, ");
			strSQL.append("ISNULL(D.nm_team, '') as nm_team, ");
			strSQL.append("ISNULL(E.cd_literal, 0) as cd_literal, ");
			strSQL.append("ISNULL(E.nm_literal, '') as nm_literal, ");
			strSQL.append("ISNULL(F.cd_tantokaisha, 0) as cd_tantokaisha, ");
			strSQL.append("ISNULL(G.nm_kaisha, '') as nm_tantokaisha ");
			strSQL.append("FROM ma_user A  ");
			strSQL.append("LEFT JOIN ma_busho B ON A.cd_kaisha = B.cd_Kaisha AND A.cd_busho = B.cd_busho ");
			strSQL.append("LEFT JOIN ma_group C ON A.cd_group = C.cd_group ");
			strSQL.append("LEFT JOIN ma_team D ON A.cd_group = D.cd_group AND A.cd_team = D.cd_team ");
			strSQL.append("LEFT JOIN ma_literal E ON A.cd_yakushoku = E.cd_literal AND 'K_yakusyoku' = E.cd_category ");
			strSQL.append("LEFT JOIN ma_tantokaisya F ON A.id_user = F.id_user ");
			strSQL.append("LEFT JOIN ma_busho G ON F.cd_tantokaisha = G.cd_kaisha ");
			strSQL.append("WHERE A.id_user = "); 
			strSQL.append(strUserId); 
			
			//④：DB検索
			lstUserInfo = dbSearch(strSQL.toString());

		} catch (Exception e) {
			em.ThrowException(e, "ユーザー基本情報参照に失敗しました。");
			
		} finally {
			//DBセッションのクローズ
//			searchDB.Close();
//			searchDB = null;
			
		}

	}
	/**
	 * ユーザー権限情報検索
	 *  : DBよりユーザー権限情報を検索する。
	 *  @param strUserID：ユーザーID
	 *  @throws ExceptionSystem
	 *  @throws ExceptionUser
	 *  @throws ExceptionWaning
	 */
	private void GetUserInfo_DB_Kengen(String strUserId) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();
		lstUserKengenInfo = null;

		//DBセッションを生成
		//SearchBaseDao searchDB = new SearchBaseDao(DBCategory.DB1);
		
		try {
			//③：SQL作成
			strSQL.append("SELECT ");
			strSQL.append("ISNULL(B.id_gamen, 0) as id_gamen, ");
			strSQL.append("ISNULL(B.id_kino, 0) as id_kino, ");
			strSQL.append("ISNULL(B.id_data, 0) as id_data ");
			strSQL.append("FROM ma_user A  ");
			strSQL.append("LEFT JOIN ma_kinou B ON A.cd_kengen = B.cd_kengen ");
			strSQL.append("WHERE A.id_user = "); 
			strSQL.append(strUserId); 

			//④：DB検索
			lstUserKengenInfo = dbSearch(strSQL.toString());

		} catch (Exception e) {
			em.ThrowException(e, "ユーザー権限情報参照に失敗しました。");

		} finally {
			//DBセッションのクローズ
//			searchDB.Close();
//			searchDB = null;
			
		}
		
	}
	/**
	 * ユーザーの保有するデータIDを返却する。
	 * @param gamenID	：画面ID
	 * @return　String	：データID
	 */
	public String getID_data(String gamenID){
		String ret = "";
		
		//権限取得
		for (int i=0;i < this.getId_gamen().size();i++){
			
			if (this.getId_gamen().get(i).toString().equals(gamenID)){
				//データIDを設定
				ret = this.getId_data().get(i).toString();
			}
		}
		
		return ret;
		
	}
	
	/**
	 * ユーザーの保有する機能IDを返却する。
	 * @param gamenID	：画面ID
	 * @return　String	：機能ID
	 */
	public String getId_kino(String gamenID){
		String ret = "";
		
		//権限取得
		for (int i=0;i < this.getId_gamen().size();i++){
			
			if (this.getId_gamen().get(i).toString().equals(gamenID)){
				//データIDを設定
				ret = this.getId_kino().get(i).toString();
			}
		}
		
		return ret;
		
	}

}
