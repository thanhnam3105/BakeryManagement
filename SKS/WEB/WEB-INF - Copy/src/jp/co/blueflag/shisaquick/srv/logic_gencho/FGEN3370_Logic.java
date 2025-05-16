package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipException;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 資材手配依頼書Excel出力処理
 *  機能ID：FGEN3370　
 *
 * @author TT.Shima
 * @since  2014/10/07
 */
public class FGEN3370_Logic extends LogicBaseJExcel {
	/** 配列のための文字列リスト */
	private static String[] STRING_KEY_LIST = new String [] {"\\(1\\)","\\(2\\)","\\(3\\)","\\(4\\)","\\(5\\)","\\(6\\)","\\(7\\)","\\(8\\)","\\(9\\)","\\(10\\)","\\(11\\)","\\(12\\)"};
	/** */
	private static String EXCEL_KAKUITYOUSI = ".xls";
	/** */
	private static String ZIPFILE_KAKUITYOUSI = ".zip";
  	/** 製品名取得のための文字列 */
	private static String STRING_SHEIHIN_KEY = "^\\(1\\)([^(]+)";
  	/** 製品名取得のための文字列 */
	private static String STRING_SHEIHIN_KEY_1 = "^\\(1\\)";
	/**
	 * 資材手配依頼書Excel出力コンストラクタ
	 * : インスタンス生成
	 */
	public FGEN3370_Logic(){
		super();
	}

	/**
	 * 資材手配依頼書を生成する
	 * @param reqData : リクエストデータ
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

		//レスポンスデータ（機能）
		RequestResponsKindBean ret = null;

		// ExcelResult
		List<?> lstRecset = null;

		List<?> lstPdf = null;

		StringBuffer strSqlBuf = null;

		//エクセルファイルパス
		String downLoadPath = "";

		try {
			//DB検索
			super.createSearchDB();
			lstRecset = getData(reqData);

			// Excel
			String strRevision = toString( reqData.getFieldVale(0, 0, "revision"));
			if("1".equals(strRevision)) {
				// 新版Excelファイル生成
				downLoadPath = makeExcelFile1(lstRecset, reqData);

			} else if ("2".equals(strRevision)) {
				// 改版Excelファイル生成
				downLoadPath = makeExcelFile2(lstRecset, reqData);
			}



			//ダウンロード用フォルダー
			String temp =
				  ConstManager.getRootAppPath()
				+ ConstManager.getConstValue(Category.設定, "DOWNLOAD_FOLDER_TEMP");
			temp +=	"/";

			String strHinmei             = toString(reqData.getFieldVale(0, 0, "hinmei"));
			//ダウンロードファイル名
			String FPAth = getShizaiTehaiIraishoFileName(strHinmei, ZIPFILE_KAKUITYOUSI);


			// 相対パスを取得
			String str = ConstManager.getRootAppPath();
			String path = System.getProperty("user.dir");
System.out.println("path:[" + path + "]");
			int pathLength = path.length();
System.out.println("pathLength:[" + pathLength + "]");
//			String soutaiPath = str.substring(pathLength + 1);
			String soutaiPath = str;
System.out.println("soutaiPath:[" + soutaiPath + "]");
System.out.println("temp:[" + temp + "]");

			// PDFパス取得
			int len = reqData.getCntRow(reqData.getTableID(0));
			List<Object> aryObj = new ArrayList<Object>();

			for (int i = 0; i < len; i++) {
				try {
					lstPdf = createSQL(reqData, i);
					if (lstPdf != null && lstPdf.size() != 0) {
						aryObj.add(lstPdf.get(0));
					}
				} catch (Exception e) {

					this.em.ThrowException(e, "発注先マスタ情報検索処理が失敗しました。");
				} finally {

				}

			}


			String[] dwnPath = downLoadPath.split(":::");

			File   file  = new File(temp + FPAth);	//作成するzipファイルの名前

			List<File> fileList = new ArrayList<File>();
			Object[] items = null;
			fileList.add(new File(soutaiPath + ConstManager.getConstValue(Category.設定, "DOWNLOAD_FOLDER") + "\\" + dwnPath[0]));
			for (int fileCnt = 0; fileCnt < aryObj.size() ; fileCnt++) {
				items = (Object[]) aryObj.get(fileCnt);
				//START 2017/02/09 Makoto Takada 取得パス、ファイル名が空であれば処理スキップ
				if( toString(items[1]).equals("") || toString(items[0]).equals("") ) {
					continue;
				}
				//END 2017/02/09 Makoto Takada 取得パス、ファイル名が空であれば処理スキップ
				fileList.add(new File(soutaiPath + ConstManager.getConstValue(Category.設定, "UPLOAD_HANSITA_FOLDER") + "\\"+ items[1].toString()+ "\\" + items[0].toString()));
			}
			File[] files = (File[]) fileList.toArray(new File[0]);

			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));
			zos.setEncoding("MS932"); //Ant版のみ

			try {
				encode(zos, files);
			} catch (Exception e) {
				em.ThrowException(e, "ファイルの圧縮に失敗しました。");
			} finally {
				zos.close();
			}
			String pdffile = "";
			pdffile = getPDFfileNames(files, pdffile);
			// メール内容にデータを設定する
			// 発注先名,担当者名
			ret = CreateRespons(FPAth, downLoadPath, pdffile, lstRecset, reqData);
			ret.setID(reqData.getID());

		} catch (Exception e) {
			em.ThrowException(e, "資材手配依頼書の生成に失敗しました。");

		} finally {
			//リストの破棄
			removeList(lstRecset);
			if (searchDB != null) {
				//DBセッションのクローズ
				searchDB.Close();
				searchDB = null;
			}
		}
		return ret;

	}

	private String getPDFfileNames(File[] files, String pdffile) {
		for (int i =1 ; i< files.length ; i++) {
			pdffile += files[i].getName() ;
			//最終行以外はつける。
			if(i == files.length -1) {
				continue;
			}
			pdffile += ":::";
		}
		return pdffile;
	}


	static byte[] buf = new byte[1024];

	static void encode(ZipOutputStream zos, File[] files) throws Exception {
		for (File file : files) {
			if (file.isDirectory()) {
				encode(zos, file.listFiles());
			} else {
				//ZipEntry entry = new ZipEntry(file.getPath().replace('\\', '/'));
				ZipEntry entry = new ZipEntry(file.getName());
				zos.putNextEntry(entry);
				try {
					 InputStream is = new BufferedInputStream(new FileInputStream(file));

					 int len = 0;
					 while ((len = is.read(buf)) != -1) {
						 zos.write(buf, 0, len);

					 }
				} catch (ZipException e) {

					e.setStackTrace(null);

				}
			}
		}
	}

	/**
	 * データ取得SQL作成
	 * @param reqData：リクエストデータ
	 * @param strSql：検索条件SQL
	 * @return StringBuffer：検索条件SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private List<?> createSQL(
            RequestResponsKindBean reqData, int num
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL格納用
		StringBuffer strSql = new StringBuffer();

		List<?> lst = null;
		try {

			// 社員コード
			String strShainCd  = reqData.getFieldVale(0, num, "cd_shain");
			// 年
			String strNen  = reqData.getFieldVale(0, num, "nen");
			// 追番
			String strNoOi = reqData.getFieldVale(0, num, "no_oi");
			// seq資材
			String strSeqShizai = reqData.getFieldVale(0, num, "seq_shizai");
			// 枝番
			String strNoEda = reqData.getFieldVale(0, num, "no_eda");

			// 数値に変換
			int shainCd = Integer.parseInt(strShainCd);
			int nen = Integer.parseInt(strNen);
			int noOi = Integer.parseInt(strNoOi);
			int seqShizai = Integer.parseInt(strSeqShizai);
			int noEda = Integer.parseInt(strNoEda);

			//SQL文の作成
			strSql.append("SELECT");
			strSql.append("  nm_file_henshita");
			strSql.append(" ,file_path_henshita");
			strSql.append(" FROM tr_shisan_shizai");
			strSql.append(" WHERE cd_shain = ");
			strSql.append(shainCd);
			strSql.append(" AND nen = ");
			strSql.append(nen);
			strSql.append(" AND no_oi = ");
			strSql.append(noOi);
			strSql.append(" AND seq_shizai = ");
			strSql.append(seqShizai);
			strSql.append(" AND no_eda = ");
			strSql.append(noEda);

			lst = searchDB.dbSearch_notError(strSql.toString());

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

		return lst;
	}

	/**
	 * 資材手配依頼書(新版)を生成する
	 * @param lstRecset : 検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(
			List<?> lstRecset,
			RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("資材手配依頼書(新版)");
			String strHinmei             = toString(reqData.getFieldVale(0, 0, "hinmei"));
			ret = super.ExcelOutputRawName(getShizaiTehaiIraishoFileName(strHinmei, EXCEL_KAKUITYOUSI) );

			//検索結果の1行分を取り出す
			Object[] items = (Object[]) lstRecset.get(0);

			ret += ":::";
			// メールアドレス１
			ret += toString(items[3]);
			ret += ":::";
			// メールアドレス２
			ret += toString(items[6]);

			String strHattyusakiCom1     = toString(items[1]);
			String strHattyusakiUser1    = toString(items[2]);
			String strHattyusakiCom2     = toString(items[4]);
			String strHattyusakiUser2    = toString(items[5]);
			String strNmKaisya           = userInfoData.getNm_kaisha();
			String strNmBusho            = userInfoData.getNm_busho();
			String strTantosya           = userInfoData.getNm_user();
			String strNaiyo              = toString(reqData.getFieldVale(0, 0, "naiyo"));
			String strSyohin             = toString(reqData.getFieldVale(0, 0, "cd_shohin"));
			String strNisugata           = toString(reqData.getFieldVale(0, 0, "nisugata"));
			String strTaisyosizai        = toString(items[0]);
			//String strHattyusaki         = toString(items[3]);
			String strNonyusaki          = toString(reqData.getFieldVale(0, 0, "nounyusaki"));
			String strNewShizaiCd        = toString(reqData.getFieldVale(0, 0, "new_shizai_cd"));
			String strSekkei1            = toString(reqData.getFieldVale(0, 0, "sekkei1"));
			String strSekkei2            = toString(reqData.getFieldVale(0, 0, "sekkei2"));
			String strSekkei3            = toString(reqData.getFieldVale(0, 0, "sekkei3"));
			String strZaishitsu          = toString(reqData.getFieldVale(0, 0, "zaishitsu"));
			String strBikoTehai          = toString(reqData.getFieldVale(0, 0, "biko_tehai"));
			String strPrintColor         = toString(reqData.getFieldVale(0, 0, "printcolor"));
			String strNoColor            = toString(reqData.getFieldVale(0, 0, "no_color"));
			String strNouki              = toString(reqData.getFieldVale(0, 0, "nouki"));
			String strSuryo              = toString(reqData.getFieldVale(0, 0, "suryo"));

			//Excelに値をセットする
			super.ExcelSetValue("発注先１", strHattyusakiCom1);
			super.ExcelSetValue("ユーザ名１", strHattyusakiUser1.replaceAll("様", ""));
			super.ExcelSetValue("発注先２", strHattyusakiCom2);
			super.ExcelSetValue("ユーザ名２", strHattyusakiUser2.replaceAll("様", ""));
			super.ExcelSetValue("会社名", strNmKaisya);
			super.ExcelSetValue("部署名", strNmBusho);
			super.ExcelSetValue("担当者", strTantosya);
			super.ExcelSetValue("改版目的", strNaiyo);
			super.ExcelSetValue("商品コード", strSyohin);
			super.ExcelSetValue("品名", strHinmei);
			super.ExcelSetValue("荷姿", strNisugata);
			super.ExcelSetValue("対象資材", strTaisyosizai);
			//super.ExcelSetValue("発注先", strHattyusaki);
			super.ExcelSetValue("納入先", strNonyusaki);
			super.ExcelSetValue("新資材コード", strNewShizaiCd);
			super.ExcelSetValue("設計１", strSekkei1);
			super.ExcelSetValue("設計２", strSekkei2);
			super.ExcelSetValue("設計３", strSekkei3);
			super.ExcelSetValue("材質", strZaishitsu);
			super.ExcelSetValue("備考", strBikoTehai);
			super.ExcelSetValue("印刷色", strPrintColor);
			super.ExcelSetValue("色番号", strNoColor);
			super.ExcelSetValue("納期", strNouki);
			super.ExcelSetValue("数量", strSuryo);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			super.ExcelWrite();
			super.close();
		}
		System.out.println(ret);
		return ret;
	}

	/**
	 * 資材手配依頼書(改版)を生成する
	 * @param lstRecset : 検索データリスト
	 * @return　String : ダウンロード用のパス
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile2(
			List<?> lstRecset,
			RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//EXCELテンプレートを読み込む
			super.ExcelReadTemplate("資材手配依頼書(改版)");
			String strHinmei             = toString(reqData.getFieldVale(0, 0, "hinmei"));
			ret = super.ExcelOutputRawName(getShizaiTehaiIraishoFileName(strHinmei, EXCEL_KAKUITYOUSI) );

			//検索結果の1行分を取り出す
			Object[] items = (Object[]) lstRecset.get(0);

			ret += ":::";
			// メールアドレス１
			ret += toString(items[3]);
			ret += ":::";
			// メールアドレス２
			ret += toString(items[6]);

			String strHattyusakiCom1     = toString(items[1]);
			String strHattyusakiUser1    = toString(items[2]);
			String strHattyusakiCom2     = toString(items[4]);
			String strHattyusakiUser2    = toString(items[5]);
			String strNmKaisya           = userInfoData.getNm_kaisha();
			String strNmBusho            = userInfoData.getNm_busho();
			String strTantosya           = userInfoData.getNm_user();
			String strNaiyo              = toString(reqData.getFieldVale(0, 0, "naiyo"));
			String strSyohin             = toString(reqData.getFieldVale(0, 0, "cd_shohin"));
			String strNisugata           = toString(reqData.getFieldVale(0, 0, "nisugata"));
			String strTaisyosizai        = toString(items[0]);
			//String strHattyusaki         = toString(items[3]);
			String strNonyusaki          = toString(reqData.getFieldVale(0, 0, "nounyusaki"));
			String strOldShizaiCd        = toString(reqData.getFieldVale(0, 0, "old_shizai_cd"));
			String strNewShizaiCd        = toString(reqData.getFieldVale(0, 0, "new_shizai_cd"));
			String strSekkei1            = toString(reqData.getFieldVale(0, 0, "sekkei1"));
			String strSekkei2            = toString(reqData.getFieldVale(0, 0, "sekkei2"));
			String strSekkei3            = toString(reqData.getFieldVale(0, 0, "sekkei3"));
			String strZaishitsu          = toString(reqData.getFieldVale(0, 0, "zaishitsu"));
			String strBikoTehai          = toString(reqData.getFieldVale(0, 0, "biko_tehai"));
			String strPrintColor         = toString(reqData.getFieldVale(0, 0, "printcolor"));
			String strNoColor            = toString(reqData.getFieldVale(0, 0, "no_color"));
			String strHenkounaiyoushosai = toString(reqData.getFieldVale(0, 0, "henkounaiyoushosai"));
			String strNouki              = toString(reqData.getFieldVale(0, 0, "nouki"));
			String strSuryo              = toString(reqData.getFieldVale(0, 0, "suryo"));
			String strOldShizaiZaiko     = toString(reqData.getFieldVale(0, 0, "old_sizaizaiko"));
			String strRakuhan            = toString(reqData.getFieldVale(0, 0, "rakuhan"));
			String strSiyohenko          = "";
			String valSiyohenko          = toString(reqData.getFieldVale(0, 0, "siyohenko"));
			if("1".equals(valSiyohenko)){
				strSiyohenko = "あり";
			} else if ("2".equals(valSiyohenko)){
				strSiyohenko = "無し";
			}

			//Excelに値をセットする
			super.ExcelSetValue("発注先１", strHattyusakiCom1);
			super.ExcelSetValue("ユーザ名１", strHattyusakiUser1.replaceAll("様", ""));
			super.ExcelSetValue("発注先２", strHattyusakiCom2);
			super.ExcelSetValue("ユーザ名２", strHattyusakiUser2.replaceAll("様", ""));
			super.ExcelSetValue("会社名", strNmKaisya);
			super.ExcelSetValue("部署名", strNmBusho);
			super.ExcelSetValue("担当者", strTantosya);
			super.ExcelSetValue("改版目的", strNaiyo);
			super.ExcelSetValue("商品コード", strSyohin);
			super.ExcelSetValue("品名", strHinmei);
			super.ExcelSetValue("荷姿", strNisugata);
			super.ExcelSetValue("対象資材", strTaisyosizai);
			//super.ExcelSetValue("発注先", strHattyusaki);
			super.ExcelSetValue("納入先", strNonyusaki);
			super.ExcelSetValue("旧資材コード", strOldShizaiCd);
			super.ExcelSetValue("新資材コード", strNewShizaiCd);
			super.ExcelSetValue("設計１", strSekkei1);
			super.ExcelSetValue("設計２", strSekkei2);
			super.ExcelSetValue("設計３", strSekkei3);
			super.ExcelSetValue("材質", strZaishitsu);
			super.ExcelSetValue("備考", strBikoTehai);
			super.ExcelSetValue("印刷色", strPrintColor);
			super.ExcelSetValue("色番号", strNoColor);
			super.ExcelSetValue("変更内容詳細", strHenkounaiyoushosai);
			super.ExcelSetValue("納期", strNouki);
			super.ExcelSetValue("数量", strSuryo);
			super.ExcelSetValue("旧資材在庫", strOldShizaiZaiko);
			super.ExcelSetValue("落版", strRakuhan);
			super.ExcelSetValue("仕様変更", strSiyohenko);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			super.ExcelWrite();
			super.close();
		}
		System.out.println(ret);

		return ret;
	}

	/**
	 * 資材手配依頼書ファイル名を取得する。
	 * @param strHinmei
	 * @return
	 */
	private String getShizaiTehaiIraishoFileName(String strHinmei, String kakutyousi) {
		String  ret ="";
		Date date = new Date();
		if(strHinmei != null && !strHinmei.equals("")) {
			ret = getSehinName(strHinmei);
		}
		ret += "_";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_kkmmss");

		ret += sdf.format(date);

		ret +=	kakutyousi;

		System.out.println("ret:" + ret);
		return ret;
	}
  	/**
	 * 製品名を取得する。
	 * @param strHinmei
	 * @return
	 */
	private String getSehinName(String strHinmei) {
		//(1)にマッチするかどうかをチェック
		Pattern p = Pattern.compile(STRING_SHEIHIN_KEY_1);
		Matcher m = p.matcher(strHinmei);
		String result = "";
		//最初に複数検索にマッチするかを検索
		if(m.find()) {
			//次に(1)(2)の間の中身を取得
			p = Pattern.compile(STRING_SHEIHIN_KEY);
			m = p.matcher(strHinmei);
			//最初に複数検索にマッチするかを検索
			if(m.find()) {
				System.out.println("resutl:[" + p.toString() + "]");
				result = convertNULL(m.group(1));
				System.out.println("resutl:[" + result + "]");
				if(!result.equals("")) {
					return result;
				}
			}
			return "";
		}
		return strHinmei;
	}


	/**
	 * 対象の資材手配データを検索する
	 * @param KindBean : リクエストデータ（機能）
	 * @return List : 検索結果のリスト
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : 該当データ無し
	 */
	private List<?> getData(RequestResponsKindBean KindBean)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//検索結果
		List<?> ret = null;

		StringBuffer strSql = new StringBuffer();

		try {

			//SQL文の作成
			strSql = MakeSQLBuf(KindBean);

			//SQLを実行
			ret = searchDB.dbSearch(strSql.toString());

			//検索結果がない場合
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.警告Exception,"W000401", strSql.toString(), "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e, "資材手配データ、DB検索に失敗しました。");

		} finally {
			//変数の削除
			strSql = null;

		}
		return ret;

	}

	/**
	 * リクエストデータより、検索SQLを生成する
	 * @param reqData : リクエストデータ（機能）
	 * @return StringBuffer : 検索SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLBuf(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try {
			// 発注先コード1
			String strHattyusaki1 = toString(reqData.getFieldVale(0, 0, "hattyusaki_com1"));
			// 担当者コード1
			String strHattyusakiUser1 = toString(reqData.getFieldVale(0, 0, "hattyusaki_user1"));
			// 発注先コード2
			String strHattyusaki2 = toString(reqData.getFieldVale(0, 0, "hattyusaki_com2"));
			// 担当者コード2
			String strHattyusakiUser2 = toString(reqData.getFieldVale(0, 0, "hattyusaki_user2"));

			String strTaisyosizai = toString(reqData.getFieldVale(0, 0, "taisyosizai"));
			//String strHattyusaki  = toString(reqData.getFieldVale(0, 0, "hattyusaki"));

			ret.append(" SELECT ");
			ret.append("     M302.nm_ｌiteral, ");
			ret.append("     M403A.nm_ｌiteral AS nm_hattyusaki1, ");
			ret.append("     M403A.nm_2nd_ｌiteral AS nm_2nd_hattyusaki1, ");
			ret.append("     M403A.mail_address AS mail_address1, ");
			ret.append("     M403B.nm_ｌiteral AS nm_hattyusaki2, ");
			ret.append("     M403B.nm_2nd_ｌiteral AS nm_2nd_hattyusaki2, ");
			ret.append("     M403B.mail_address AS mail_address2 ");
			//ret.append("     M403C.nm_ｌiteral AS nm_hattyusaki ");
			ret.append(" FROM ");
			ret.append("    (SELECT DISTINCT ");
			ret.append("         '1' AS keycol, ");
			ret.append("         nm_ｌiteral ");
			ret.append("     FROM ");
			ret.append("         ma_literal ");
			ret.append("     WHERE ");
			ret.append("         cd_category = 'C_taisyosizai' ");
			ret.append("     AND cd_literal = '" + strTaisyosizai + "') M302 ");
			ret.append(" LEFT JOIN ");
			ret.append("    (SELECT ");
			ret.append("         '1' as keycol, ");
			ret.append("         ISNULL(nm_ｌiteral, '') AS nm_ｌiteral, ");
			ret.append("         ISNULL(nm_2nd_ｌiteral, '') AS nm_2nd_ｌiteral, ");
			ret.append("         ISNULL(mail_address, '') AS mail_address ");
			ret.append("     FROM ");
			ret.append("         ma_literal ");
			ret.append("     WHERE ");
			ret.append("         cd_category = 'C_hattyuusaki' ");
			ret.append("     AND cd_literal = '" + strHattyusaki1 + "'");
			ret.append("     AND cd_2nd_literal = '" + strHattyusakiUser1 + "') M403A ");
			ret.append(" ON M302.keycol = M403A.keycol ");
			ret.append(" LEFT JOIN ");
			ret.append("    (SELECT ");
			ret.append("         '1' as keycol, ");
			ret.append("         ISNULL(nm_ｌiteral, '') AS nm_ｌiteral, ");
			ret.append("         ISNULL(nm_2nd_ｌiteral, '') AS nm_2nd_ｌiteral, ");
			ret.append("         ISNULL(mail_address, '') AS mail_address ");
			ret.append("     FROM ");
			ret.append("         ma_literal ");
			ret.append("     WHERE ");
			ret.append("         cd_category = 'C_hattyuusaki' ");
			ret.append("     AND cd_literal = '" + strHattyusaki2 + "'");
			ret.append("     AND cd_2nd_literal = '" + strHattyusakiUser2 + "') M403B ");
			ret.append(" ON M302.keycol = M403B.keycol ");
//			ret.append(" LEFT JOIN ");
//			ret.append("    (SELECT ");
//			ret.append("         '1' as keycol, ");
//			ret.append("         ISNULL(nm_ｌiteral, '') AS nm_ｌiteral ");
//			ret.append("     FROM ");
//			ret.append("         ma_literal ");
//			ret.append("     WHERE ");
//			ret.append("         cd_category = 'C_hattyuusaki' ");
//			ret.append("     AND cd_literal = '" + strHattyusaki + "') M403C ");
//			ret.append(" ON M302.keycol = M403C.keycol ");

		} catch (Exception e) {
			this.em.ThrowException(e, "資材手配データ、検索SQLの生成に失敗しました。");

		} finally {

		}
		return ret;

	}

	/**
	 * レスポンスデータを生成する
	 * @param DownLoadPath : ファイルパス生成ファイル格納先(ダウンロードパラメータ)
	 * @return RequestResponsKindBean : レスポンスデータ（機能）
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private RequestResponsKindBean CreateRespons(String DownLoadPath, String excelFileName,
			String pdfFileName, List<?> hatyuData, RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		Map<String, String> koujyoMap = new HashMap<String, String>();
		koujyoMap.put("仙川", "1");
		koujyoMap.put("挙母", "2");
		koujyoMap.put("伊丹", "3");
		koujyoMap.put("鳥栖", "4");
		koujyoMap.put("五霞", "5");
		koujyoMap.put("中河原", "6");
		koujyoMap.put("泉佐野", "7");
		koujyoMap.put("階上", "8");
		koujyoMap.put("富士吉田", "9");
		String SEPSTR = ",";
		RequestResponsKindBean ret = null;

		try {
			//レスポンスを生成する
			ret = new RequestResponsKindBean();

			String[] str = excelFileName.split(":::");
			if(hatyuData != null && hatyuData.size() != 0) {
				//検索結果の1行分を取り出す
				Object[] items = (Object[]) hatyuData.get(0);
				//START　2017.03.24 対象資材を取得  Makoto Takada
				ret.addFieldVale(0, 0, "nm_Taisyosizai", toString(items[0]));				
				//END　　 2017.03.24  対象資材を取得 Makoto Takada
				//ファイルパス	生成ファイル格納先
				ret.addFieldVale(0, 0, "nm_hattyusaki1", toString(items[1]));
				ret.addFieldVale(0, 0, "nm_2nd_hattyusaki1", toString(items[2]));
				ret.addFieldVale(0, 0, "nm_hattyusaki2", toString(items[4]));
				ret.addFieldVale(0, 0, "nm_2nd_hattyusaki2", toString(items[5]));
			}

			//ファイルパス	生成ファイル格納先
			ret.addFieldVale(0, 0, "URLValue", DownLoadPath);
			//処理結果１ 成功可否
			ret.addFieldVale(0, 0, "flg_return", "true");
			// excelFileName
			ret.addFieldVale(0, 0, "excelFileName", str[0]);
			// pdfFiieName
			ret.addFieldVale(0, 0, "pdfFileName", pdfFileName);
			if (str.length > 1) {
				// メールアドレス１
				ret.addFieldVale(0, 0, "mail_address1", str[1]);
			}
			String mailadress2 = "";
			if (str.length > 2) {
				// メールアドレス２
				mailadress2 = str[2];
				mailadress2       += SEPSTR + ConstManager.getConstValue(Category.設定, "SIZAIKA_MEMBER_MAILIST") ;
				String strNonyusaki          = toString(reqData.getFieldVale(0, 0, "nounyusaki"));
				String koujyomailist = getKojyoName(strNonyusaki, koujyoMap);

				mailadress2       +=  koujyomailist;
				ret.addFieldVale(0, 0, "mail_address2", mailadress2);
			} else {
				// メールアドレス２
				mailadress2       += SEPSTR + ConstManager.getConstValue(Category.設定, "SIZAIKA_MEMBER_MAILIST") ;
				String strNonyusaki          = toString(reqData.getFieldVale(0, 0, "nounyusaki"));
				String koujyomailist = getKojyoName(strNonyusaki, koujyoMap);

				mailadress2       += koujyomailist ;
				ret.addFieldVale(0, 0, "mail_address2", mailadress2);
			}

			Object[] items = (Object[]) hatyuData.get(0);
			// 発注先名1
			ret.addFieldVale(0, 0, "hattyusakiCd1", toString(items[0]));

			//処理結果２	メッセージ
			ret.addFieldVale(0, 0, "msg_error", "");
			//処理結果３	処理名称
			ret.addFieldVale(0, 0, "no_errmsg", "");
			//処理結果６	メッセージ番号
			ret.addFieldVale(0, 0, "nm_class", "");
			//処理結果４	エラーコード
			ret.addFieldVale(0, 0, "cd_error", "");
			//処理結果５	システムメッセージ
			ret.addFieldVale(0, 0, "msg_system", "");

		} catch (Exception e) {
			em.ThrowException(e, "");
		}
		return ret;

	}
	/**
	 * 工場名を取得する。
	 * @param strNonyusaki
	 * @param map
	 * @return
	 */
	public String getKojyoName(String strNonyusaki, Map map) throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		String regex = "^(.+?)工場";
		String SEPSTR = ",";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(strNonyusaki);
		String result = "";
		if(m.find()) {
			result =  m.group(1);

			if(map.get(result) != null) {
				return SEPSTR + (String) ConstManager.getConstValue(Category.設定, "KOUJYO_" + map.get(result));
			}
		}
		//初期化して複数として処理をもう一度やり直す。
		result = "";
		for(int i = 0 ; i < STRING_KEY_LIST.length ; i++) {
			if(!getMatchKey(strNonyusaki, map, STRING_KEY_LIST[i]).equals("")) {
				result += "," + ConstManager.getConstValue(Category.設定, "KOUJYO_" + getMatchKey(strNonyusaki, map, STRING_KEY_LIST[i]));
			}
		}
		return result;
	}

	private String getMatchKey(String strNonyusaki, Map map, String key) {
		String regex;
		Pattern p;
		Matcher m;
		String result="";
		regex = key + "(.+?)工場";

		p = Pattern.compile(regex);
		m = p.matcher(strNonyusaki);
		if(m.find()) {
			result =  m.group(1);

			if(map.get(result) != null) {
				return (String)map.get(result);
			}
		}
		return  "";
	}
	/**
	 *
	 * @param str
	 * @return
	 */
    private String convertNULL(String str ) {
    	if(str == null) {
    		return "";
    	}
    	 return str;
    }
}
