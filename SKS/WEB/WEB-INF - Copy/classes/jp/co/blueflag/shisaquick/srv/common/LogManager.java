package jp.co.blueflag.shisaquick.srv.common;

import org.apache.log4j.*;
import org.apache.log4j.xml.*;

/**
 * 
 * ���O�o�̓N���X
 *  : �f�o�b�O�E�G���[�E�V�X�e���G���[�E�g���[�X���̃��O�o�͂��s���B
 *    ���O�o�͐ݒ�Ɋւ��ẮAlog4j�ݒ�XML�t�@�C�����ɂĐݒ�B
 *  
 * @author TT.k-katayama
 * @since  2009/03/25
 *
 */
public class LogManager {

	private static Logger logger = null;			//���O�o�̓N���X
	private static int intDebugLevel = -1;			//�f�o�b�O���x��
	private static String strStingXmlNm = "";		//log4j�ݒ�XML�t�@�C����
	
//	/**
//	 * ���O�o�̓N���X �R���X�g���N�^
//	 * @throws ExceptionWaning 
//	 * @throws ExceptionUser 
//	 * @throws ExceptionSystem 
//	 * 
//	 * @author TT.k-katayama
//	 * @since  2009/03/25
//	 */
//	public LogManager() 
//	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		
//		//�A �R���X�g���f�o�b�O���x�����擾����
//		String strDebugLv = ConstManager.getConstValue(ConstManager.Category.�ݒ�, "CONST_DEBUG_LEVEL");
//		this.intDebugLevel = Integer.parseInt(strDebugLv);
//		
//	}
	
	/**
	 * ���O�o��
	 *  : DebugLevel0�̃��O���o�͂���
	 * @param strLogOutMsg	: ���O�o�̓��b�Z�[�W
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public static void systemLog(String strLogOutMsg) {

		GetDebugLevel();
		
		//�@ log4j Logger�̃C���X�^���X�𐶐�����
		CreateLogger();
		
		//�A �f�o�b�O���O�̏o�͂��s��
		logger.debug(strLogOutMsg);
	}

	/**
	 * ���O�o��
	 *  : DebugLevel1�̃��O���o�͂���
	 * @param strLogOutMsg	: ���O�o�̓��b�Z�[�W
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public static void userErrorLog(String strLogOutMsg) {

		GetDebugLevel();
		
		//�@ �����ointDebugLevel�𔻒肷��
		if ( intDebugLevel >= 1 ) {
			// �����ointDebugLevel��1�ȏ�̏ꍇ�A�������s
			
			//�A log4j Logger�̃C���X�^���X�𐶐�����
			CreateLogger();
			
			//�B �f�o�b�O���O�̏o�͂��s��
			logger.debug(strLogOutMsg);
		}
	}

	/**
	 * ���O�o��
	 *  : DebugLevel2�̃��O���o�͂���
	 * @param strLogOutMsg	: ���O�o�̓��b�Z�[�W
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public static void warningErrorLog(String strLogOutMsg) {

		GetDebugLevel();
		
		//�@ �����ointDebugLevel�𔻒肷��
		if ( intDebugLevel >= 2 ) {
			// �����ointDebugLevel��2�ȏ�̏ꍇ�A�������s
			
			//�A log4j Logger�̃C���X�^���X�𐶐�����
			CreateLogger();
			
			//�B �f�o�b�O���O�̏o�͂��s��
			logger.debug(strLogOutMsg);
			
		}
	}

	/**
	 * ���O�o��
	 *  : DebugLevel3�̃��O���o�͂���
	 * @param strLogOutMsg	: ���O�o�̓��b�Z�[�W
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/25
	 */
	public static void infoLog(String strLogOutMsg) {

		GetDebugLevel();
		
		//�@ �����ointDebugLevel�𔻒肷��
		if ( intDebugLevel >= 3 ) {
			// �����ointDebugLevel��3�ȏ�̏ꍇ�A�������s
			
			//�A log4j Logger�̃C���X�^���X�𐶐�����
			CreateLogger();
			
			//�B �f�o�b�O���O�̏o�͂��s��
			logger.debug(strLogOutMsg);
		}
	}
	
	/**
	 * log4j Logger�̃C���X�^���X�𐶐�����
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	private static void CreateLogger() {

		// ���O�o�̓N���X�𐶐�
		logger = Logger.getLogger("");
		logger.setLevel(Level.DEBUG);

		// �����ostrStingXmlNm�ɐݒ肳�ꂽlog4j�ݒ�t�@�C����ǂݍ���
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		DOMConfigurator.configure(loader.getResource(strStingXmlNm));
	}

	/**
	 * log4j�ݒ�XML�t�@�C���� �Q�b�^�[
	 * @return strStingXmlNm : log4j�ݒ�XML�t�@�C�����̒l��Ԃ�
	 */
	public static String getStrStingXmlNm() {
		return strStingXmlNm;
	}
	/**
	 * log4j�ݒ�XML�t�@�C���� �Z�b�^�[
	 * @param _strStingXmlNm : log4j�ݒ�XML�t�@�C�����̒l���i�[����
	 */
	public static void setStrStingXmlNm(String _strStingXmlNm) {
		strStingXmlNm = _strStingXmlNm;
	}
	
	private static void GetDebugLevel() {

		//�A �R���X�g���f�o�b�O���x�����擾����
		String strDebugLv;
		
		try {
			if (intDebugLevel < 0 ){
				strDebugLv = ConstManager.getConstValue(ConstManager.Category.�ݒ�, "CONST_DEBUG_LEVEL");
				intDebugLevel = Integer.parseInt(strDebugLv);

			}

		} catch (Exception e) {
			intDebugLevel = 3;

		}

	}
	
}
