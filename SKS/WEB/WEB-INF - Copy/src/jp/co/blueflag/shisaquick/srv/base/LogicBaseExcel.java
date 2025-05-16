package jp.co.blueflag.shisaquick.srv.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * ���W�b�N�x�[�X�iEXCEL�o�͗p�j
 * @author isono
 *
 */
public class LogicBaseExcel extends LogicBase {

	//EXCEL�Ǘ��N���X
	protected ExcelObject excelObject = null;
	//���[��
	private String printName;

	/**
	 * �R���X�g���N�^
	 */
	public LogicBaseExcel(){
		super();

	}
	/**
	 * �G�N�Z���e���v���[�g��ǂݍ���
	 * @param PrintName : ���[���iConst_Excel_Templates.xml�̃L�[���[�h�j
	 * @param del : �V�[�g�폜 true:�폜�@false:��\��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	//EXCEL�e���v���[�g��ǂݍ���
	//MOD 2013/06/18 ogawa start        Setting�V�[�g�̍폜���\���ɕύX
	//�C���O�̃\�[�X
	//protected void ExcelReadTemplate(String PrintName)
	//�C����̃\�[�X
	protected void ExcelReadTemplate(String PrintName, boolean del)
	//MOD 2013/06/18 ogawa  end
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//���[���̑ޔ�
			printName = PrintName;
			//�e���v���[�g�t�@�C����Path���擾
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			String xmlfile = loader.getResource(
					ConstManager.getConstValue(Category.�ݒ�, "EXCEL_TEMPLATES_FOLDER")
					+	"/"
					+	ConstManager.getConstValue(Category.�G�N�Z���e���v���[�g, PrintName)
					).getPath();
			//�G�N�Z���I�u�W�F�N�g�̃C���X�^���X����
			excelObject = new ExcelObject(PrintName);
			//�e���v���[�g�̓ǂݍ���
			//MOD 2013/06/18 ogawa start        Setting�V�[�g�̍폜���\���ɕύX
			//�C���O�̃\�[�X
			//excelObject.CreateNewBook(xmlfile);
			//�C����̃\�[�X
			excelObject.CreateNewBook(xmlfile, del);
			//MOD 2013/06/18 ogawa  end

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}

	}
	/**
	 * Excel��Cell�Ƀf�[�^���Z�b�g����
	 * @param LinkNm : �G�N�Z����Setting�V�[�g�̃����N���ږ�
	 * @param Value : �l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void ExcelSetValue(String LinkNm, String Value)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//Excel�Ƀf�[�^���Z�b�g����
			excelObject.SetValue(LinkNm, Value);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}

	}
	/**
	 * Excel��Cell�Ƀf�[�^���Z�b�g����
	 * @param LinkNm : �G�N�Z����Setting�V�[�g�̃����N���ږ�
	 * @param Value : �l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void ExcelSetValue(String LinkNm, boolean Value)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//Excel�Ƀf�[�^���Z�b�g����
			excelObject.SetValue(LinkNm, Value);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}

	}
	/**
	 * Excel��Cell�Ƀf�[�^���Z�b�g����
	 * @param LinkNm : �G�N�Z����Setting�V�[�g�̃����N���ږ�
	 * @param Value : �l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void ExcelSetValue(String LinkNm, Double Value)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//Excel�Ƀf�[�^���Z�b�g����
			excelObject.SetValue(LinkNm, Value);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}

	}
	/**
	 * Excelobject���t�@�C���ɏo�͂���
	 * @return�@String : �_�E�����[�h�t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String ExcelOutput()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			ret = ExcelOutput("");

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	/**
	 * Excelobject���t�@�C���ɏo�͂���
	 * @param FNameOption : �t�@�C�����I�v�V�����i�t�@�C����_�t�@�C�����I�v�V����_�N����_�����b�j
	 * @return�@String : �_�E�����[�h�t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String ExcelOutput(String FNameOption)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//Excelobject���t�@�C���ɏo�͂���

			//�_�E�����[�h�p�t�H���_�[
			String FPAth =
				  ConstManager.getRootAppPath()
				+ ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER");
			FPAth +=	"/";

			//�_�E�����[�h�t�@�C����
			ret = MakeFileName(FNameOption);

			//�t�@�C���̐���
			excelObject.CreateExcelFile(FPAth + ret);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	/**
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	/**
	 * �o�̓t�@�C�����𐶐�����
	 * @param FNameOption : �t�@�C�����I�v�V����
	 * @return : �t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeFileName(String FNameOption)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//�t�@�C�����̐���

			//���[��
			ret +=	printName;

			//�t�@�C�����I�v�V����
			if (!FNameOption.equals("")){
				//�t�@�C�������w�肳��Ă���ꍇ�A�t�@�C�����Ƀt�@�C�����I�v�V������t������B
				ret +=	"_";
				ret +=	FNameOption;

			}

			//���[�U�[ID
			ret +=	"_";
			ret +=	userInfoData.getId_user();
			ret +=	"_";

//			�yH24�N�x�Ή��z2012/06/01 TT H.SHIMA MOD Start

//			//���݂̔N�������Ԃ��擾
//			Calendar cal = Calendar.getInstance();
//
//			//�N
//			ret += Integer.toString(cal.get(Calendar.YEAR));
//			//��
//			ret += Integer.toString(cal.get(Calendar.MONTH) + 1);
//			//��
//			ret += Integer.toString(cal.get(Calendar.DATE));
//			//��
//			ret +=	"_";
//			ret += Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
//			//��
//			ret += Integer.toString(cal.get(Calendar.MINUTE));
//			//�b
//			ret += Integer.toString(cal.get(Calendar.SECOND));

			Date date = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_kkmmss");

			ret += sdf.format(date);

//			�yH24�N�x�Ή��z2012/06/01 TT H.SHIMA MOD End

			ret +=	".xls";

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}


	/**
	 * Excelobject���t�@�C���ɏo�͂���
	 * @param FNameOption : �t�@�C�����I�v�V�����i�t�@�C����_�t�@�C�����I�v�V����_�N����_�����b�j
	 * @return�@String : �_�E�����[�h�t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String ExcelOutput_genka(String FNameOption , String strNm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//Excelobject���t�@�C���ɏo�͂���

			//�_�E�����[�h�p�t�H���_�[
			String FPAth =
				  ConstManager.getRootAppPath()
				+ ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER");
			FPAth +=	"/";

			//�_�E�����[�h�t�@�C����
			ret = MakeFileName_genka(FNameOption,strNm);

			//�t�@�C���̐���
			excelObject.CreateExcelFile(FPAth + ret);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	/**
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	/**
	 * �o�̓t�@�C�����𐶐�����
	 * @param FNameOption : �t�@�C�����I�v�V����
	 * @return : �t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeFileName_genka(String FNameOption , String strNm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//�t�@�C�����̐���

			//�֎~�����̒u��
			strNm = chnageErrChar(strNm);

			//���[��
			ret +=FNameOption + "_" + strNm + "_";

//			�yH24�N�x�Ή��z2012/06/01 TT H.SHIMA MOD Start

//			//���݂̔N�������Ԃ��擾
//			Calendar cal = Calendar.getInstance();
//
//			//�N
//			ret += (Integer.toString(cal.get(Calendar.YEAR))).substring(2, 4);
//			//��
//			ret += Integer.toString(cal.get(Calendar.MONTH) + 1);
//			//��
//			ret += Integer.toString(cal.get(Calendar.DATE));

			Date date = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			ret += sdf.format(date);

//			�yH24�N�x�Ή��z2012/06/01 TT H.SHIMA MOD End

			ret +=	".xls";

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}




	//***** ADD �yH24�N�x�Ή��z 2012/04/16 hagiwara S **********
	/**
	 * Excelobject���t�@�C���ɏo�͂���
	 * @param FName : �t�@�C����
	 * @return�@String : �_�E�����[�h�t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String ExcelOutput_genka(String FName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//Excelobject���t�@�C���ɏo�͂���

			//�_�E�����[�h�p�t�H���_�[
			String FPAth =
				  ConstManager.getRootAppPath()
				+ ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER");
			FPAth +=	"/";

			//�_�E�����[�h�t�@�C����
			ret = MakeFileName_genka(FName);

			//�t�@�C���̐���
			excelObject.CreateExcelFile(FPAth + ret);

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	/**
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	/**
	 * �o�̓t�@�C�����𐶐�����
	 * @param FName : �t�@�C����
	 * @return : �t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeFileName_genka(String FName)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";

		try {
			//�t�@�C�����̐���

			//�֎~�����̒u��
			FName = chnageErrChar(FName);

			//���[��
			ret += FName + "_";

//			�yH24�N�x�Ή��z2012/06/01 TT H.SHIMA MOD Start

//			//���݂̔N�������Ԃ��擾
//			Calendar cal = Calendar.getInstance();
//
//			//�N
//			ret += (Integer.toString(cal.get(Calendar.YEAR))).substring(2, 4);
//			//��
//			ret += Integer.toString(cal.get(Calendar.MONTH) + 1);
//			//��
//			ret += Integer.toString(cal.get(Calendar.DATE));

			Date date = new Date();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			ret += sdf.format(date);

//			�yH24�N�x�Ή��z2012/06/01 TT H.SHIMA MOD End

			ret +=	".xls";

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}
	//***** ADD �yH24�N�x�Ή��z 2012/04/16 hagiwara E **********



	/**
	 * �֎~�����̒u��
	 * @param FNameOption : �t�@�C�����I�v�V����
	 * @return : �t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String chnageErrChar(String strNm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = strNm;

		try {
			//�֎~�����̒u��
			// \ / : * ? " > < |
			ret = ret.replaceAll("\\\\","��");
			ret = ret.replaceAll("/","��");
			ret = ret.replaceAll(":","��");
			ret = ret.replaceAll("\\*","��");
			ret = ret.replaceAll("\\?","��");
			ret = ret.replaceAll("\"","��");
			ret = ret.replaceAll(">","��");
			ret = ret.replaceAll("<","��");
			ret = ret.replaceAll("\\|","��");

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;

	}

}
