package jp.co.blueflag.shisaquick.srv.base;

import java.util.Calendar;

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
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void ExcelReadTemplate(String PrintName) 
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
			excelObject.CreateNewBook(xmlfile);
			
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
			//Excelobject���t�@�C���ɏo�͂���

			//�_�E�����[�h�p�t�H���_�[
			String FPAth =
				  ConstManager.getRootAppPath()
				+ ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER"); 
			FPAth +=	"/"; 
			//�_�E�����[�h�t�@�C����
			ret = MakeFileName();
			//�t�@�C���̐���
			excelObject.CreateExcelFile(FPAth + ret);
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}
	/**
	 * �o�̓t�@�C�����𐶐�����
	 * @return : �t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeFileName() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		String ret = "";
		
		try {
			//�t�@�C�����̐���
			
			//���[��
			ret +=	printName;
			//���[�U�[ID
			ret +=	"_";
			ret +=	userInfoData.getId_user();
			ret +=	"_";
			
			//���݂̔N�������Ԃ��擾
			Calendar cal = Calendar.getInstance();

			//�N
			ret += Integer.toString(cal.get(Calendar.YEAR));
			//��
			ret += Integer.toString(cal.get(Calendar.MONTH));
			//��
			ret += Integer.toString(cal.get(Calendar.DATE));
			//��
			ret +=	"_";
			ret += Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
			//��
			ret += Integer.toString(cal.get(Calendar.MINUTE));
			//�b
			ret += Integer.toString(cal.get(Calendar.SECOND));

			ret +=	".xls";

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}
	
}
