package jp.co.blueflag.shisaquick.srv.base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * ���W�b�N�x�[�X�iCSV�o�͗p�j
 * @author isono
 *
 */
public class LogicBaseCSV extends LogicBase {
	
	//CSV�Ǘ��N���X
	protected FileCreateCSV fileCreateCSV = null;
	
	/**
	 * �R���X�g���N�^
	 */
	public LogicBaseCSV(){
		super();
		
	}
	/**
	 * CSV�t�@�C�����_�E�����[�h�t�H���_�ɏo�͂���B
	 * @param outFileName : �t�@�C����
	 * @param dataList : �o�͂���f�[�^(DB�̌�������)
	 * @return : �_�E�����[�h�t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected String CSVOutput(
			String outFileName
			, List<?> dataList) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		
		try {
			//CSV���t�@�C���ɏo�͂���

			//�_�E�����[�h�p�t�H���_�[
			String FPAth =
				  ConstManager.getRootAppPath()
				+ ConstManager.getConstValue(Category.�ݒ�, "DOWNLOAD_FOLDER"); 
			FPAth +=	"/"; 

			//�_�E�����[�h�t�@�C����
			ret = MakeFileName(outFileName);

			//�t�@�C���̐���
			fileCreateCSV = new FileCreateCSV();
			fileCreateCSV.csvFileCreater(FPAth + ret, dataList);
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}
	/**
	 * �o�̓t�@�C�����𐶐�����
	 * @param printName : �o�̓t�@�C����
	 * @return : �t�@�C�����i�g����j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String MakeFileName(String printName) 
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
			
//			�yH24�N�x�Ή��z2012/06/01 TT H.SHIMA MOD Start
			
//			//���݂̔N�������Ԃ��擾
//			Calendar cal = Calendar.getInstance();
//
//			//�N
//			ret += Integer.toString(cal.get(Calendar.YEAR));
//			//��
//			ret += Integer.toString(cal.get(Calendar.MONTH));
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
			
			ret +=	".csv";

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}
	
}
