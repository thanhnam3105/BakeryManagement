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
 * ���ގ�z�˗���Excel�o�͏���
 *  �@�\ID�FFGEN3370�@
 *
 * @author TT.Shima
 * @since  2014/10/07
 */
public class FGEN3370_Logic extends LogicBaseJExcel {
	/** �z��̂��߂̕����񃊃X�g */
	private static String[] STRING_KEY_LIST = new String [] {"\\(1\\)","\\(2\\)","\\(3\\)","\\(4\\)","\\(5\\)","\\(6\\)","\\(7\\)","\\(8\\)","\\(9\\)","\\(10\\)","\\(11\\)","\\(12\\)"};
	/** */
	private static String EXCEL_KAKUITYOUSI = ".xls";
	/** */
	private static String ZIPFILE_KAKUITYOUSI = ".zip";
  	/** ���i���擾�̂��߂̕����� */
	private static String STRING_SHEIHIN_KEY = "^\\(1\\)([^(]+)";
  	/** ���i���擾�̂��߂̕����� */
	private static String STRING_SHEIHIN_KEY_1 = "^\\(1\\)";
	/**
	 * ���ގ�z�˗���Excel�o�̓R���X�g���N�^
	 * : �C���X�^���X����
	 */
	public FGEN3370_Logic(){
		super();
	}

	/**
	 * ���ގ�z�˗����𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//���X�|���X�f�[�^�i�@�\�j
		RequestResponsKindBean ret = null;

		// ExcelResult
		List<?> lstRecset = null;

		List<?> lstPdf = null;

		StringBuffer strSqlBuf = null;

		//�G�N�Z���t�@�C���p�X
		String downLoadPath = "";

		try {
			//DB����
			super.createSearchDB();
			lstRecset = getData(reqData);

			// Excel
			String strRevision = toString( reqData.getFieldVale(0, 0, "revision"));
			if("1".equals(strRevision)) {
				// �V��Excel�t�@�C������
				downLoadPath = makeExcelFile1(lstRecset, reqData);

			} else if ("2".equals(strRevision)) {
				// ����Excel�t�@�C������
				downLoadPath = makeExcelFile2(lstRecset, reqData);
			}



			//�_�E�����[�h�p�t�H���_�[
			String temp =
				  ConstManager.getRootAppPath()
				+ ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER_TEMP");
			temp +=	"/";

			String strHinmei             = toString(reqData.getFieldVale(0, 0, "hinmei"));
			//�_�E�����[�h�t�@�C����
			String FPAth = getShizaiTehaiIraishoFileName(strHinmei, ZIPFILE_KAKUITYOUSI);


			// ���΃p�X���擾
			String str = ConstManager.getRootAppPath();
			String path = System.getProperty("user.dir");
System.out.println("path:[" + path + "]");
			int pathLength = path.length();
System.out.println("pathLength:[" + pathLength + "]");
//			String soutaiPath = str.substring(pathLength + 1);
			String soutaiPath = str;
System.out.println("soutaiPath:[" + soutaiPath + "]");
System.out.println("temp:[" + temp + "]");

			// PDF�p�X�擾
			int len = reqData.getCntRow(reqData.getTableID(0));
			List<Object> aryObj = new ArrayList<Object>();

			for (int i = 0; i < len; i++) {
				try {
					lstPdf = createSQL(reqData, i);
					if (lstPdf != null && lstPdf.size() != 0) {
						aryObj.add(lstPdf.get(0));
					}
				} catch (Exception e) {

					this.em.ThrowException(e, "������}�X�^��񌟍����������s���܂����B");
				} finally {

				}

			}


			String[] dwnPath = downLoadPath.split(":::");

			File   file  = new File(temp + FPAth);	//�쐬����zip�t�@�C���̖��O

			List<File> fileList = new ArrayList<File>();
			Object[] items = null;
			fileList.add(new File(soutaiPath + ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER") + "\\" + dwnPath[0]));
			for (int fileCnt = 0; fileCnt < aryObj.size() ; fileCnt++) {
				items = (Object[]) aryObj.get(fileCnt);
				//START 2017/02/09 Makoto Takada �擾�p�X�A�t�@�C��������ł���Ώ����X�L�b�v
				if( toString(items[1]).equals("") || toString(items[0]).equals("") ) {
					continue;
				}
				//END 2017/02/09 Makoto Takada �擾�p�X�A�t�@�C��������ł���Ώ����X�L�b�v
				fileList.add(new File(soutaiPath + ConstManager.getConstValue(Category.�ݒ�, "UPLOAD_HANSITA_FOLDER") + "\\"+ items[1].toString()+ "\\" + items[0].toString()));
			}
			File[] files = (File[]) fileList.toArray(new File[0]);

			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file));
			zos.setEncoding("MS932"); //Ant�ł̂�

			try {
				encode(zos, files);
			} catch (Exception e) {
				em.ThrowException(e, "�t�@�C���̈��k�Ɏ��s���܂����B");
			} finally {
				zos.close();
			}
			String pdffile = "";
			pdffile = getPDFfileNames(files, pdffile);
			// ���[�����e�Ƀf�[�^��ݒ肷��
			// �����於,�S���Җ�
			ret = CreateRespons(FPAth, downLoadPath, pdffile, lstRecset, reqData);
			ret.setID(reqData.getID());

		} catch (Exception e) {
			em.ThrowException(e, "���ގ�z�˗����̐����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//DB�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
		}
		return ret;

	}

	private String getPDFfileNames(File[] files, String pdffile) {
		for (int i =1 ; i< files.length ; i++) {
			pdffile += files[i].getName() ;
			//�ŏI�s�ȊO�͂���B
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
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return StringBuffer�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private List<?> createSQL(
            RequestResponsKindBean reqData, int num
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		List<?> lst = null;
		try {

			// �Ј��R�[�h
			String strShainCd  = reqData.getFieldVale(0, num, "cd_shain");
			// �N
			String strNen  = reqData.getFieldVale(0, num, "nen");
			// �ǔ�
			String strNoOi = reqData.getFieldVale(0, num, "no_oi");
			// seq����
			String strSeqShizai = reqData.getFieldVale(0, num, "seq_shizai");
			// �}��
			String strNoEda = reqData.getFieldVale(0, num, "no_eda");

			// ���l�ɕϊ�
			int shainCd = Integer.parseInt(strShainCd);
			int nen = Integer.parseInt(strNen);
			int noOi = Integer.parseInt(strNoOi);
			int seqShizai = Integer.parseInt(strSeqShizai);
			int noEda = Integer.parseInt(strNoEda);

			//SQL���̍쐬
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
	 * ���ގ�z�˗���(�V��)�𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @return�@String : �_�E�����[�h�p�̃p�X
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
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("���ގ�z�˗���(�V��)");
			String strHinmei             = toString(reqData.getFieldVale(0, 0, "hinmei"));
			ret = super.ExcelOutputRawName(getShizaiTehaiIraishoFileName(strHinmei, EXCEL_KAKUITYOUSI) );

			//�������ʂ�1�s�������o��
			Object[] items = (Object[]) lstRecset.get(0);

			ret += ":::";
			// ���[���A�h���X�P
			ret += toString(items[3]);
			ret += ":::";
			// ���[���A�h���X�Q
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

			//Excel�ɒl���Z�b�g����
			super.ExcelSetValue("������P", strHattyusakiCom1);
			super.ExcelSetValue("���[�U���P", strHattyusakiUser1.replaceAll("�l", ""));
			super.ExcelSetValue("������Q", strHattyusakiCom2);
			super.ExcelSetValue("���[�U���Q", strHattyusakiUser2.replaceAll("�l", ""));
			super.ExcelSetValue("��Ж�", strNmKaisya);
			super.ExcelSetValue("������", strNmBusho);
			super.ExcelSetValue("�S����", strTantosya);
			super.ExcelSetValue("���ŖړI", strNaiyo);
			super.ExcelSetValue("���i�R�[�h", strSyohin);
			super.ExcelSetValue("�i��", strHinmei);
			super.ExcelSetValue("�׎p", strNisugata);
			super.ExcelSetValue("�Ώێ���", strTaisyosizai);
			//super.ExcelSetValue("������", strHattyusaki);
			super.ExcelSetValue("�[����", strNonyusaki);
			super.ExcelSetValue("�V���ރR�[�h", strNewShizaiCd);
			super.ExcelSetValue("�݌v�P", strSekkei1);
			super.ExcelSetValue("�݌v�Q", strSekkei2);
			super.ExcelSetValue("�݌v�R", strSekkei3);
			super.ExcelSetValue("�ގ�", strZaishitsu);
			super.ExcelSetValue("���l", strBikoTehai);
			super.ExcelSetValue("����F", strPrintColor);
			super.ExcelSetValue("�F�ԍ�", strNoColor);
			super.ExcelSetValue("�[��", strNouki);
			super.ExcelSetValue("����", strSuryo);

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
	 * ���ގ�z�˗���(����)�𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @return�@String : �_�E�����[�h�p�̃p�X
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
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("���ގ�z�˗���(����)");
			String strHinmei             = toString(reqData.getFieldVale(0, 0, "hinmei"));
			ret = super.ExcelOutputRawName(getShizaiTehaiIraishoFileName(strHinmei, EXCEL_KAKUITYOUSI) );

			//�������ʂ�1�s�������o��
			Object[] items = (Object[]) lstRecset.get(0);

			ret += ":::";
			// ���[���A�h���X�P
			ret += toString(items[3]);
			ret += ":::";
			// ���[���A�h���X�Q
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
				strSiyohenko = "����";
			} else if ("2".equals(valSiyohenko)){
				strSiyohenko = "����";
			}

			//Excel�ɒl���Z�b�g����
			super.ExcelSetValue("������P", strHattyusakiCom1);
			super.ExcelSetValue("���[�U���P", strHattyusakiUser1.replaceAll("�l", ""));
			super.ExcelSetValue("������Q", strHattyusakiCom2);
			super.ExcelSetValue("���[�U���Q", strHattyusakiUser2.replaceAll("�l", ""));
			super.ExcelSetValue("��Ж�", strNmKaisya);
			super.ExcelSetValue("������", strNmBusho);
			super.ExcelSetValue("�S����", strTantosya);
			super.ExcelSetValue("���ŖړI", strNaiyo);
			super.ExcelSetValue("���i�R�[�h", strSyohin);
			super.ExcelSetValue("�i��", strHinmei);
			super.ExcelSetValue("�׎p", strNisugata);
			super.ExcelSetValue("�Ώێ���", strTaisyosizai);
			//super.ExcelSetValue("������", strHattyusaki);
			super.ExcelSetValue("�[����", strNonyusaki);
			super.ExcelSetValue("�����ރR�[�h", strOldShizaiCd);
			super.ExcelSetValue("�V���ރR�[�h", strNewShizaiCd);
			super.ExcelSetValue("�݌v�P", strSekkei1);
			super.ExcelSetValue("�݌v�Q", strSekkei2);
			super.ExcelSetValue("�݌v�R", strSekkei3);
			super.ExcelSetValue("�ގ�", strZaishitsu);
			super.ExcelSetValue("���l", strBikoTehai);
			super.ExcelSetValue("����F", strPrintColor);
			super.ExcelSetValue("�F�ԍ�", strNoColor);
			super.ExcelSetValue("�ύX���e�ڍ�", strHenkounaiyoushosai);
			super.ExcelSetValue("�[��", strNouki);
			super.ExcelSetValue("����", strSuryo);
			super.ExcelSetValue("�����ލ݌�", strOldShizaiZaiko);
			super.ExcelSetValue("����", strRakuhan);
			super.ExcelSetValue("�d�l�ύX", strSiyohenko);

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
	 * ���ގ�z�˗����t�@�C�������擾����B
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
	 * ���i�����擾����B
	 * @param strHinmei
	 * @return
	 */
	private String getSehinName(String strHinmei) {
		//(1)�Ƀ}�b�`���邩�ǂ������`�F�b�N
		Pattern p = Pattern.compile(STRING_SHEIHIN_KEY_1);
		Matcher m = p.matcher(strHinmei);
		String result = "";
		//�ŏ��ɕ��������Ƀ}�b�`���邩������
		if(m.find()) {
			//����(1)(2)�̊Ԃ̒��g���擾
			p = Pattern.compile(STRING_SHEIHIN_KEY);
			m = p.matcher(strHinmei);
			//�ŏ��ɕ��������Ƀ}�b�`���邩������
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
	 * �Ώۂ̎��ގ�z�f�[�^����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData(RequestResponsKindBean KindBean)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		List<?> ret = null;

		StringBuffer strSql = new StringBuffer();

		try {

			//SQL���̍쐬
			strSql = MakeSQLBuf(KindBean);

			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());

			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e, "���ގ�z�f�[�^�ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;

		}
		return ret;

	}

	/**
	 * ���N�G�X�g�f�[�^���A����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : ����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLBuf(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		StringBuffer ret = new StringBuffer();

		try {
			// ������R�[�h1
			String strHattyusaki1 = toString(reqData.getFieldVale(0, 0, "hattyusaki_com1"));
			// �S���҃R�[�h1
			String strHattyusakiUser1 = toString(reqData.getFieldVale(0, 0, "hattyusaki_user1"));
			// ������R�[�h2
			String strHattyusaki2 = toString(reqData.getFieldVale(0, 0, "hattyusaki_com2"));
			// �S���҃R�[�h2
			String strHattyusakiUser2 = toString(reqData.getFieldVale(0, 0, "hattyusaki_user2"));

			String strTaisyosizai = toString(reqData.getFieldVale(0, 0, "taisyosizai"));
			//String strHattyusaki  = toString(reqData.getFieldVale(0, 0, "hattyusaki"));

			ret.append(" SELECT ");
			ret.append("     M302.nm_��iteral, ");
			ret.append("     M403A.nm_��iteral AS nm_hattyusaki1, ");
			ret.append("     M403A.nm_2nd_��iteral AS nm_2nd_hattyusaki1, ");
			ret.append("     M403A.mail_address AS mail_address1, ");
			ret.append("     M403B.nm_��iteral AS nm_hattyusaki2, ");
			ret.append("     M403B.nm_2nd_��iteral AS nm_2nd_hattyusaki2, ");
			ret.append("     M403B.mail_address AS mail_address2 ");
			//ret.append("     M403C.nm_��iteral AS nm_hattyusaki ");
			ret.append(" FROM ");
			ret.append("    (SELECT DISTINCT ");
			ret.append("         '1' AS keycol, ");
			ret.append("         nm_��iteral ");
			ret.append("     FROM ");
			ret.append("         ma_literal ");
			ret.append("     WHERE ");
			ret.append("         cd_category = 'C_taisyosizai' ");
			ret.append("     AND cd_literal = '" + strTaisyosizai + "') M302 ");
			ret.append(" LEFT JOIN ");
			ret.append("    (SELECT ");
			ret.append("         '1' as keycol, ");
			ret.append("         ISNULL(nm_��iteral, '') AS nm_��iteral, ");
			ret.append("         ISNULL(nm_2nd_��iteral, '') AS nm_2nd_��iteral, ");
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
			ret.append("         ISNULL(nm_��iteral, '') AS nm_��iteral, ");
			ret.append("         ISNULL(nm_2nd_��iteral, '') AS nm_2nd_��iteral, ");
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
//			ret.append("         ISNULL(nm_��iteral, '') AS nm_��iteral ");
//			ret.append("     FROM ");
//			ret.append("         ma_literal ");
//			ret.append("     WHERE ");
//			ret.append("         cd_category = 'C_hattyuusaki' ");
//			ret.append("     AND cd_literal = '" + strHattyusaki + "') M403C ");
//			ret.append(" ON M302.keycol = M403C.keycol ");

		} catch (Exception e) {
			this.em.ThrowException(e, "���ގ�z�f�[�^�A����SQL�̐����Ɏ��s���܂����B");

		} finally {

		}
		return ret;

	}

	/**
	 * ���X�|���X�f�[�^�𐶐�����
	 * @param DownLoadPath : �t�@�C���p�X�����t�@�C���i�[��(�_�E�����[�h�p�����[�^)
	 * @return RequestResponsKindBean : ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private RequestResponsKindBean CreateRespons(String DownLoadPath, String excelFileName,
			String pdfFileName, List<?> hatyuData, RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		Map<String, String> koujyoMap = new HashMap<String, String>();
		koujyoMap.put("���", "1");
		koujyoMap.put("����", "2");
		koujyoMap.put("�ɒO", "3");
		koujyoMap.put("����", "4");
		koujyoMap.put("�܉�", "5");
		koujyoMap.put("���͌�", "6");
		koujyoMap.put("�򍲖�", "7");
		koujyoMap.put("�K��", "8");
		koujyoMap.put("�x�m�g�c", "9");
		String SEPSTR = ",";
		RequestResponsKindBean ret = null;

		try {
			//���X�|���X�𐶐�����
			ret = new RequestResponsKindBean();

			String[] str = excelFileName.split(":::");
			if(hatyuData != null && hatyuData.size() != 0) {
				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) hatyuData.get(0);
				//START�@2017.03.24 �Ώێ��ނ��擾  Makoto Takada
				ret.addFieldVale(0, 0, "nm_Taisyosizai", toString(items[0]));				
				//END�@�@ 2017.03.24  �Ώێ��ނ��擾 Makoto Takada
				//�t�@�C���p�X	�����t�@�C���i�[��
				ret.addFieldVale(0, 0, "nm_hattyusaki1", toString(items[1]));
				ret.addFieldVale(0, 0, "nm_2nd_hattyusaki1", toString(items[2]));
				ret.addFieldVale(0, 0, "nm_hattyusaki2", toString(items[4]));
				ret.addFieldVale(0, 0, "nm_2nd_hattyusaki2", toString(items[5]));
			}

			//�t�@�C���p�X	�����t�@�C���i�[��
			ret.addFieldVale(0, 0, "URLValue", DownLoadPath);
			//�������ʂP ������
			ret.addFieldVale(0, 0, "flg_return", "true");
			// excelFileName
			ret.addFieldVale(0, 0, "excelFileName", str[0]);
			// pdfFiieName
			ret.addFieldVale(0, 0, "pdfFileName", pdfFileName);
			if (str.length > 1) {
				// ���[���A�h���X�P
				ret.addFieldVale(0, 0, "mail_address1", str[1]);
			}
			String mailadress2 = "";
			if (str.length > 2) {
				// ���[���A�h���X�Q
				mailadress2 = str[2];
				mailadress2       += SEPSTR + ConstManager.getConstValue(Category.�ݒ�, "SIZAIKA_MEMBER_MAILIST") ;
				String strNonyusaki          = toString(reqData.getFieldVale(0, 0, "nounyusaki"));
				String koujyomailist = getKojyoName(strNonyusaki, koujyoMap);

				mailadress2       +=  koujyomailist;
				ret.addFieldVale(0, 0, "mail_address2", mailadress2);
			} else {
				// ���[���A�h���X�Q
				mailadress2       += SEPSTR + ConstManager.getConstValue(Category.�ݒ�, "SIZAIKA_MEMBER_MAILIST") ;
				String strNonyusaki          = toString(reqData.getFieldVale(0, 0, "nounyusaki"));
				String koujyomailist = getKojyoName(strNonyusaki, koujyoMap);

				mailadress2       += koujyomailist ;
				ret.addFieldVale(0, 0, "mail_address2", mailadress2);
			}

			Object[] items = (Object[]) hatyuData.get(0);
			// �����於1
			ret.addFieldVale(0, 0, "hattyusakiCd1", toString(items[0]));

			//�������ʂQ	���b�Z�[�W
			ret.addFieldVale(0, 0, "msg_error", "");
			//�������ʂR	��������
			ret.addFieldVale(0, 0, "no_errmsg", "");
			//�������ʂU	���b�Z�[�W�ԍ�
			ret.addFieldVale(0, 0, "nm_class", "");
			//�������ʂS	�G���[�R�[�h
			ret.addFieldVale(0, 0, "cd_error", "");
			//�������ʂT	�V�X�e�����b�Z�[�W
			ret.addFieldVale(0, 0, "msg_system", "");

		} catch (Exception e) {
			em.ThrowException(e, "");
		}
		return ret;

	}
	/**
	 * �H�ꖼ���擾����B
	 * @param strNonyusaki
	 * @param map
	 * @return
	 */
	public String getKojyoName(String strNonyusaki, Map map) throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		String regex = "^(.+?)�H��";
		String SEPSTR = ",";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(strNonyusaki);
		String result = "";
		if(m.find()) {
			result =  m.group(1);

			if(map.get(result) != null) {
				return SEPSTR + (String) ConstManager.getConstValue(Category.�ݒ�, "KOUJYO_" + map.get(result));
			}
		}
		//���������ĕ����Ƃ��ď�����������x��蒼���B
		result = "";
		for(int i = 0 ; i < STRING_KEY_LIST.length ; i++) {
			if(!getMatchKey(strNonyusaki, map, STRING_KEY_LIST[i]).equals("")) {
				result += "," + ConstManager.getConstValue(Category.�ݒ�, "KOUJYO_" + getMatchKey(strNonyusaki, map, STRING_KEY_LIST[i]));
			}
		}
		return result;
	}

	private String getMatchKey(String strNonyusaki, Map map, String key) {
		String regex;
		Pattern p;
		Matcher m;
		String result="";
		regex = key + "(.+?)�H��";

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
