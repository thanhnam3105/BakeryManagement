package jp.co.blueflag.shisaquick.srv.jnlp;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsFieldBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.ResponsData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * JNLP作成
 *  : メンバ変数の値を基に、JNLPファイルを作成する
 *  
 * @author TT.k-katayama
 * @since  2009/03/28
 *
 */
public class JNLPCreateLogic extends LogicBase {

	private ResponsData resData = null;
	
	
	/**
	 * コンストラクタ
	 * @param requestData : リクエストデータ
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/28
	 */
	public JNLPCreateLogic() {
		//基底クラスのコンストラクタ
		super();

		//インスタンス変数の初期化
		resData = null;
	}

	/**
	 * ロジック実装
	 * @param reqData : 機能リクエストデータ
	 * @param userInfoData : ユーザー情報
	 * @return レスポンスデータ（機能）
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//ユーザー情報退避
		userInfoData = _userInfoData;

		try {
			resData = new ResponsData("");
			String strFileName = "";

			// ①：JNLP作成メソッドを呼び出し、作成されたファイルパスを取得する。
			strFileName = this.CreateJNLP(reqData);
						
			// ②:RequestResponsBeanに値を格納し、返却する。
			
			// 機能IDの設定
			String strKinoId = reqData.getID();
			resData.CurrentKindID(strKinoId);

			// テーブル名の設定
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resData.CurrentTableName(strTableNm);
			
			// パラメータ格納、ResponsDataにセット
			this.storageGroupCmb(strFileName);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			if (searchDB != null) {
				//セッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
		}

		return (RequestResponsKindBean) resData.GetItem(0);
	}	
	
	/**
	 * JNLP読み込み
	 *  : 基となるJNLPファイルの読み込みを行う
	 * @param strFileName : 読み込み先ファイル名
	 * @return Documentオブジェクト
	 * 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/28
	 */
	private Document ReadJNLP(String strFileName) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		Document doc = null;
		
		try {
			//プロジェクトフォルダのパスを取得
			String strRealPath = ConstManager.getRootAppPath();
			//XMLを読み込む
			// ドキュメントビルダーファクトリを生成
			DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
			// ドキュメントビルダーを生成
			DocumentBuilder builder = dbfactory.newDocumentBuilder();
			// パースを実行してDocumentオブジェクトを取得
			doc = builder.parse(strRealPath + "/jws/" + strFileName);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "JNLPファイルの読み込みに失敗しました。");
		} finally {
		}
		
		return doc;
	}
	
	/**
	 * JNLP作成
	 *  : JNLPファイルを作成する
	 * @param reqData : 機能リクエストデータ
	 * @return 作成されたJNLPファイルパス
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/28
	 */
	public String CreateJNLP(RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetJnlpPath = "";

		//① : 機能リクエストデータを解析し、JNLPファイルを作成する
		try {
			
			// 1)JNLP読み込みメソッドを使用し、テンプレート用JNLPファイルを読み込む（template.jnlp）
			Document document = this.ReadJNLP("template.jnlp");
			
			// ルート要素を取得
			Element rootElement = document.getDocumentElement();
			// application-desc要素のリストを取得
			NodeList rootList = rootElement.getElementsByTagName("application-desc");
			// argument要素を取得
			Element argElement = (Element)rootList.item(0);
	        
			// 2)機能リクエストデータの値を取得する。(※機能ID : SA550I)

			String strUserId = null;
			String strTrialCD = null;
			String strMode = null;

			// ユーザID
			strUserId = reqData.getFieldVale(0, 0, "id_user");
			// 試作コード
			strTrialCD = reqData.getFieldVale(0, 0, "no_shisaku");
			// モード
			strMode = reqData.getFieldVale(0, 0, "mode");
			
			// 3)JNLPファイル内のデータに解析した機能リクエストデータの値を格納する。
			argElement.getElementsByTagName("argument").item(0).getFirstChild().setNodeValue(strUserId);
			argElement.getElementsByTagName("argument").item(1).getFirstChild().setNodeValue(strTrialCD);
			argElement.getElementsByTagName("argument").item(2).getFirstChild().setNodeValue(strMode);
			
			// 4)jwsフォルダ内に新規JNLPファイルを作成する。(※例 : new_ + ユーザID + .jnlp)
			
			// プロジェクトフォルダパス
			String strRealPath = ConstManager.getRootAppPath();
			// 新規JNLPファイル名
			String strNewJnlpName = "new_" + strUserId + ".jnlp";
			
			// 新規JNLPファイル作成
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			File newXML = new File(strRealPath + "/jws/" + strNewJnlpName);
			FileOutputStream os = new FileOutputStream(newXML);
			StreamResult result = new StreamResult(os); 
			transformer.transform(source, result);
			
			// 作成したJNLPファイルパスを設定
			strRetJnlpPath = strNewJnlpName; 
			
		} catch (Exception e) {
			this.em.ThrowException(e, "JNLPを作成できませんでした。");
		} finally {
		}
		
		//② : 作成したファイルのパスをstrJNLPpathとして返却する
		return strRetJnlpPath;
	}

	/**
	 * コンボ用：グループパラメーター格納
	 *  : グループコンボボックス情報をレスポンスデータへ格納する。
	 * @param strFileName : 検索結果情報リスト
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void storageGroupCmb(String strFileName) throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		RequestResponsRowBean resBean = null;

		try {
			
			resBean = new RequestResponsRowBean();
			resBean.setID("rec");
			
			//処理結果の格納
			resBean.getItemList().add(new RequestResponsFieldBean("flg_return", "true"));
			resBean.getItemList().add(new RequestResponsFieldBean("msg_error", ""));
			resBean.getItemList().add(new RequestResponsFieldBean("no_errmsg", ""));
			resBean.getItemList().add(new RequestResponsFieldBean("nm_class", ""));
			resBean.getItemList().add(new RequestResponsFieldBean("cd_error", ""));
			resBean.getItemList().add(new RequestResponsFieldBean("msg_system", ""));

			//JNLPファイルパス格納
			resBean.getItemList().add(new RequestResponsFieldBean("jnlp_path", strFileName));

			//リクエストBeenに取得結果を追加
			resData.addRow(resBean);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//変数の削除
			resBean = null;
		}
	}
	
}
