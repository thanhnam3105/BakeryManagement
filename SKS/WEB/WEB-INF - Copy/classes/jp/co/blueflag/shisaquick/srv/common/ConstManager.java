package jp.co.blueflag.shisaquick.srv.common;

/**
 * 
 * �萔�Ǘ�
 *  : �T�[�o�[�Ŏg�p����A���b�Z�[�W���̒萔�l���Ǘ�����B
 * @author TT.furuta
 * @since  2009/03/24
 */
public final class ConstManager extends ConstVars {
	
	//�萔XML�i�[�i���b�Z�[�W�j
	private static XmlConstList objXmlConstList_MSG = null;
	//�萔XML�i�[�i�ݒ�j
	private static XmlConstList objXmlConstList_SETING = null;
	//�萔XML�i�[�i�G�N�Z���e���v���[�g�j
	private static XmlConstList objXmlConstList_EXCEL_TEMPLATES = null;
	//���[�gURL
	private static String RootURL = "";
	//���[�gAPP�p�X
	private static String RootAppPath = "";
	
	/**
	 * �R���X�g���X�g�̃J�e�S�� 
	 */
	public static enum Category{
		���b�Z�[�W,
		�ݒ�,
		�G�N�Z���e���v���[�g
	}
	/**
	 * �R���X�g�̎擾
	 *  : �w�肳�ꂽ�J�e�S��/�R�[�h�i�L�[���[�h�j�̃R���X�g�l��ԋp����
	 * @param enmCategory : �R���X�g�̃J�e�S��
	 * @param strCode : �R���X�g�̃R�[�h
	 * @return �R���X�g�l
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public static String getConstValue(Category enmCategory, String strCode) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String ret ="";
		
		//Enum�L�[���[�h�F���b�Z�[�W
		if (enmCategory == Category.���b�Z�[�W) {
			//�C���X�^���X���Ȃ��ꍇ����
			if (objXmlConstList_MSG == null){
				objXmlConstList_MSG = new XmlConstList(CONST_XML_PATH_MSG);
			}
			ret = objXmlConstList_MSG.GetConstValue(strCode);

		//Enum�L�[���[�h�F�ݒ�
		} else if (enmCategory == Category.�ݒ�){
			//�C���X�^���X���Ȃ��ꍇ����
			if (objXmlConstList_SETING == null){
				objXmlConstList_SETING = new XmlConstList(CONST_XML_PATH_SETING);
			}
			ret = objXmlConstList_SETING.GetConstValue(strCode);

		//Enum�L�[���[�h�F�G�N�Z���e���v���[�g
		} else if (enmCategory == Category.�G�N�Z���e���v���[�g){
			//�C���X�^���X���Ȃ��ꍇ����
			if (objXmlConstList_EXCEL_TEMPLATES == null){
				objXmlConstList_EXCEL_TEMPLATES = new XmlConstList(CONST_XML_PATH_EXCEL_TEMPLATES);
			}
			ret = objXmlConstList_EXCEL_TEMPLATES.GetConstValue(strCode);

		}
		
		return ret;
	}
	/**
	 * ���[�gURL�擾
	 * @return : ���[�gURL
	 */
	public static String getRootURL(){
		
		return RootURL;
		
	}
	/**
	 * ���[�gURL�ݒ�
	 * @param _RootURL : ���[�gURL
	 */
	public static void setRootURL(String _RootURL){
		
		RootURL = _RootURL;
		
	}
	/**
	 * ���[�gAPP�p�X�擾
	 * @return : ���[�gAPP�p�X
	 */
	public static String getRootAppPath(){
		
		return RootAppPath;
		
	}
	/**
	 * ���[�gAPP�p�X�ݒ�
	 * @param _RootURL : ���[�gAPP�p�X
	 */
	public static void setRootAppPath(String _RootAppPath){
		
		RootAppPath = _RootAppPath;
		
	}
	
}