package jp.co.blueflag.shisaquick.srv.datacheck_genka;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.math.BigDecimal;
import java.util.List;


/**
 * 【QP@00342】
 * 担当者マスタメンテ（営業）　担当者検索（営業）データチェック
 *  機能ID：FGEN2060
 *  
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2060_datacheck extends DataCheck{
	
	
	/**
	 * 担当者マスタメンテ（営業）　担当者検索（営業）データチェック
	 * : インスタンス生成
	 */
	public FGEN2060_datacheck() {
		//基底クラスのコンストラクタ
		super();
	}
	
	
	/**
	 * 担当者マスタメンテ（営業）　担当者検索（営業）データチェック
	 * @param reqData : リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//ユーザー情報退避
		userInfoData = _userInfoData;
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;
		
		try {
			//リクエストからデータ抽出
			String id_user = reqData.getFieldVale(0, 0, "id_user");
			
			//機能ID取得用
			String strUKinoId = "";
			String strUDataId = "";
			
			//営業（一般）権限コード取得
			String strEigyoIppan = 
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_IPPAN");
			//営業（本部権限）権限コード取得
			String strEigyoHonbu = 
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_HONBU");
			//営業（システム管理者）権限コード取得
			String strEigyoSystem = 
				ConstManager.getConstValue(ConstManager.Category.設定,"EIGYO_KENGEN_SYSTEM");
			
			//権限データID取得
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("200")){
					//機能IDを設定
					strUKinoId = userInfoData.getId_kino().get(i).toString(); 
					//データIDを設定
					strUDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			
			//権限が「自分のみ」の場合
			if(strUKinoId.equals("100") && strUDataId.equals("1")){
				
				if(id_user.equals(userInfoData.getId_user())){
					
				}
				else{
					em.ThrowException(ExceptionKind.一般Exception,"E000310","", "", "");
				}
				
			}
			//権限が「全ての営業担当者（一般のみ）」の場合
			else if(strUKinoId.equals("101") && strUDataId.equals("1")){
				
				//ユーザ検索
				strSql.append(" SELECT    ");
				strSql.append(" 	cd_kengen   ");
				strSql.append(" 	,cd_kaisha   ");
				strSql.append(" 	,cd_busho   ");
				strSql.append(" FROM ma_user   ");
				strSql.append(" WHERE id_user= "+id_user);
				
				// SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//データが存在しない場合はそのまま
				if (lstRecset.size() == 0){
					
				}
				//データが存在する場合にエラーチェック
				else{
					//検索結果取得
					Object[] items = (Object[]) lstRecset.get(0);
					String cd_kengen = toString(items[0],"");
					
					//営業担当者（一般）　以外はエラー
					if(cd_kengen.equals(strEigyoIppan)){
						
					}
					else{
						em.ThrowException(ExceptionKind.一般Exception,"E000311","", "", "");
					}
				}
			}
			//権限が「全ての営業担当者（一般、本部権限）」の場合
			else if(strUKinoId.equals("102") && strUDataId.equals("1")){
				//ユーザ検索
				strSql.append(" SELECT    ");
				strSql.append(" 	cd_kengen   ");
				strSql.append(" 	,cd_kaisha   ");
				strSql.append(" 	,cd_busho   ");
				strSql.append(" FROM ma_user   ");
				strSql.append(" WHERE id_user= "+id_user);
				
				// SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//データが存在しない場合はそのまま
				if (lstRecset.size() == 0){
					
				}
				//データが存在する場合にエラーチェック
				else{
					//検索結果取得
					Object[] items = (Object[]) lstRecset.get(0);
					String cd_kengen = toString(items[0],"");
					// ADD 2013/11/7 QP@30154 okano start
					String cd_kaisha = toString(items[1],"");
					// ADD 2013/11/7 QP@30154 okano end
					
					//自分の場合
					if(id_user.equals(userInfoData.getId_user())){
						
					}
					//自分以外の場合
					else{
						//営業担当者（一般）以外はエラー
						// MOD 2013/11/7 QP@30154 okano start
//							if(cd_kengen.equals(strEigyoIppan)){
						if(cd_kengen.equals(strEigyoIppan) && cd_kaisha.equals(userInfoData.getCd_kaisha())){
						// MOD 2013/11/7 QP@30154 okano end
							
						}
						else{
							em.ThrowException(ExceptionKind.一般Exception,"E000311","", "", "");
						}
					}
				}
				
			}
			//権限が「全て（営業）」の場合
			else if(strUKinoId.equals("103") && strUDataId.equals("1")){
				
				//ユーザ検索
				strSql.append(" SELECT    ");
				strSql.append(" 	cd_kengen   ");
				strSql.append(" 	,cd_kaisha   ");
				strSql.append(" 	,cd_busho   ");
				strSql.append(" FROM ma_user   ");
				strSql.append(" WHERE id_user= "+id_user);
				
				// SQLを実行
				super.createSearchDB();
				lstRecset = searchBD.dbSearch(strSql.toString());
				
				//データが存在しない場合はそのまま
				if (lstRecset.size() == 0){
					
				}
				//データが存在する場合にエラーチェック
				else{
					//検索結果取得
					Object[] items = (Object[]) lstRecset.get(0);
					String cd_kengen = toString(items[0],"");
					
					//営業担当者　以外はエラー
					if( cd_kengen.equals(strEigyoIppan) 
							|| cd_kengen.equals(strEigyoHonbu) 
							|| cd_kengen.equals(strEigyoSystem)  ){
						
					}
					else{
						em.ThrowException(ExceptionKind.一般Exception,"E000313","", "", "");
					}
				}
				
			}
			
			
		} catch (Exception e) {
			
			this.em.ThrowException(e, "担当者マスタメンテ（営業）　担当者検索（営業）データチェック処理に失敗しました。");
			
		} finally {
			
			//リストの破棄
			removeList(lstRecset);
			
			//セッションのクローズ
			if (searchBD != null) {
				searchBD.Close();
				searchBD = null;
			}
			
			//変数の削除
			strSql = null;
			
		}
	}
}
