package jp.co.blueflag.shisaquick.srv.common;

/**
 * 
 * �萔�ێ�
 *  : �ÓI�萔�̋L�q�A�ێ�
 *
 */
 public class ConstVars {

//	//DB�̐ڑ�������@AppConText�ֈړ�
//	public static final String DB_CONECT = "";
	
//	//�f�o�b�O���[�h(0�`3) Xml���X�g�i�ݒ�j�ֈړ��@CONST_DEBUG_LEVEL
//	public static final int DEBUG_MODE = 3;
 
	//XML�@DB�@Session
	
	//�c�a�Z�b�V�����ݒ�XML�i����p�j
	public static final String CONST_XML_PATH_DB1 = "conf/hibernate.cfg.xml";
		
	//�c�a�Z�b�V�����ݒ�XML�i���@�x���A�g�j
	public static final String CONST_XML_PATH_DB2 = "conf/hibernate.cfg2.xml";
 
	//�c�a�Z�b�V�����ݒ�XML�i�\���j
	public static final String CONST_XML_PATH_DB3 = "conf/hibernate.cfg3.xml";
 
	//XML�@Const�@List
	
	//�R���X�gXML�̊i�[�ʒu+�t�@�C����(���b�Z�[�W�p)
	public static final String CONST_XML_PATH_MSG = "conf/Const_Msg.xml";
		
	//�R���X�gXML�̊i�[�ʒu+�t�@�C����(�ݒ�p)
	public static final String CONST_XML_PATH_SETING = "conf/Const_Setting.xml";

	//�R���X�gXML�̊i�[�ʒu+�t�@�C����(�G�N�Z���e���v���[�g)
	public static final String CONST_XML_PATH_EXCEL_TEMPLATES = "conf/Const_Excel_Templates.xml";

	//APP�@ConText
	
	//�C���v�b�g�`�F�b�N��DI APPConText�t�@�C���p�X
	public static final String APPCONTEXT_NM_INPUTCHECK = "conf/appContext_InputCheck.xml";

	//�f�[�^�`�F�b�N��DI APPConText�t�@�C���p�X
	public static final String APPCONTEXT_NM_DATACHECK = "conf/appContext_DataCheck.xml";

	//�Ɩ����W�b�N��DI APPConText�t�@�C���p�X
	public static final String APPCONTEXT_NM_LOGIC = "conf/appContext_Logic.xml";

	//�Ǘ��N���X��DI APPConText�t�@�C���p�X
	public static final String APPCONTEXT_NM_MANAGER = "conf/appContext_Manager.xml";

	//�f�[�^�A�N�Z�X�I�u�W�F�N�g��DI�@AppConText�t�@�C���p�X
	public static final String APPCONTEXT_NM_DAO = "conf/appContext_BD.xml";

	//���̑��@�ÓI�ݒ�l
	
	//�T�[�u���b�gXML���N�G�X�g�G���R�[�h
	public static final String SRV_XML_REQ_ENCODE = "UTF-8";

	//�T�[�u���b�gXML���X�|���X�G���R�[�h
	public static final String SRV_XML_RESP_ENCODE = "text/xml; charset=UTF-8";
	
	//log4j�̃G���[���O�o�́A�ݒ�t�@�C����
	public static final String CONST_ROG4J_ERRORLOG_SETING = "conf/log4j_ErrorLog.xml";
	
	//���N�G�X�g�J�E���g
	public static long ReqCount = 0;

}
