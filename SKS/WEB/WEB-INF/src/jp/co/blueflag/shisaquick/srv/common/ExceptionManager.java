package jp.co.blueflag.shisaquick.srv.common;

import java.util.Calendar;


/**
 * 
 * ��O����
 *  : ��O�����̔�����s���B
 * 
 * @author TT.k-katayama
 * @since  2009/03/26
 *
 */
public class ExceptionManager {

	//�N���X��
	private String strClassName = null;
	//���O�o�͗p�ݒ�t�@�C��
	private String strLogSetingFileNm = null;
	//���O�Ǘ�	
	//private LogManager logManager;
	//�V�X�e����O
	private ExceptionSystem exceptionSystem = null;
	//��ʗ�O
	private ExceptionUser exceptionUser = null;
	//�x����O
	private ExceptionWaning exceptionWaning = null;

	/**
	 * ��O�����̎��
	 *   ���Exception
	 *   �x��Exception
	 *   �V�X�e��Exception
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public enum ExceptionKind {
		���Exception,
		�x��Exception,
		�V�X�e��Exception
	}	
	
	/**
	 * ��O�����N���X �R���X�g���N�^
	 * @param strClassName : �N���X��
	 *  
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public ExceptionManager(String _strClassName) {
		//�C���X�^���X�ϐ��̏�����
		strClassName = null;
		strLogSetingFileNm = null;
		exceptionSystem = null;
		exceptionUser = null;
		exceptionWaning = null;
		
		//�@ �N���X���������oClassName�Ɋi�[����
		this.strClassName = _strClassName;
		
		//�A log�ݒ�xml�t�@�C�������擾����
		this.strLogSetingFileNm = ConstManager.CONST_ROG4J_ERRORLOG_SETING; 
		
	}
	
	/**
	 * �G�N�Z�v�V�����X���[(1)
	 * @param ex : �G�N�Z�v�V����
	 * @param logMsg : ���O�o��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public void ThrowException(
			Exception ex
			, String logMsg
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		ExceptionBase excBase = null; 
		
		//Exception�̎�ނ𔻒肵ExceptionSystem�Ɋi�[���AException�̃��O���o�͂���B
		excBase = cnvException(ex, logMsg);
		
		//Exception�������o�Ɋi�[����
		if ( excBase.getClass().equals(ExceptionSystem.class) ) {
			//ExceptionSystem �c �Ȃɂ����Ȃ�
			exceptionSystem = (ExceptionSystem) excBase;
			
		} else if ( excBase.getClass().equals(ExceptionUser.class) ) {
			//ExceptionUser �c �Ȃɂ����Ȃ�
			exceptionUser = (ExceptionUser) excBase;
			
		} else if ( excBase.getClass().equals(ExceptionWaning.class) ) {
			//ExceptionWaning �c �Ȃɂ����Ȃ�
			exceptionWaning = (ExceptionWaning) excBase;
			
		}
		
		//�D ��O�̃X���[ ���C���X�^���X�̂���N���X���X���[
		ThrowException();		

	}
	/**
	 * Exception�̎�ނ𔻒肵ExceptionSystem�Ɋi�[����B
	 * Exception�̃��O���o�͂���B
	 * @param ex�FException
	 * @param logMsg�F���O�o�̓��b�Z�[�W
	 * @return�FExceptionBase
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public ExceptionBase cnvException(
			Exception ex
			, String logMsg
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		ExceptionBase ret = null;

		ExceptionSystem excSystem = null;
		ExceptionUser excUser = null;
		ExceptionWaning excWaning = null;
		
		String strDateTime = "";		//���݂̓��t(YYY/MM/DD hh:mm:dd)
		String strErrorCD = "";			//�V�X�e���G���[�R�[�h
		String strErrorMsg = "";		//�V�X�e���G���[���b�Z�[�W
		String strLogOutMsg = "";		//���O�o�͗p���b�Z�[�W
		String strUserMsg = "";			//���[�U�[�p���b�Z�[�W
		
		//�@ Exception�̎�ނ𔻕ʂ���
		if ( ex.getClass().equals(ExceptionSystem.class) ) {
			//ExceptionSystem �c �Ȃɂ����Ȃ�
			excSystem = (ExceptionSystem) ex;
			
			//Log�o�͍���
			strLogOutMsg = 
				strDateTime 
			+ " [�� Trace] " 
			+ " �N���X����" 
			+ this.strClassName 
			+ " �����N���X��" 
			+ excSystem.getClassName(); 
			
			// 2) logManager�N���X�̃C���X�^���X�𐶐���Log���o�͂���
			//this.logManager = new LogManager();
			LogManager.setStrStingXmlNm(this.strLogSetingFileNm);
			LogManager.systemLog(strLogOutMsg);
			
		} else if ( ex.getClass().equals(ExceptionUser.class) ) {
			//ExceptionUser �c �Ȃɂ����Ȃ�
			excUser = (ExceptionUser) ex;
			
		} else if ( ex.getClass().equals(ExceptionWaning.class) ) {
			//ExceptionWaning �c �Ȃɂ����Ȃ�
			excWaning = (ExceptionWaning) ex;
			
		} else {
			//SystemException(�{FramWork�ȊO��Throw���ꂽException)
			
			//�A log���o�͂���
			// 1) ���b�Z�[�W�̕ҏW
			strDateTime = this.getDateTimeNow();
			strErrorCD = ex.getClass().getName();
			strErrorMsg = this.getSystemErrMessage(ex);
			//Log�o�͍���
			strLogOutMsg = 
				strDateTime 
			+ " [SYSTEM EXCEPTION] �y" 
			+ logMsg
			+ "�z �N���X����" 
			+ this.strClassName 
			+ " �G�N�Z�v�V�����̎�ށ�" 
			+ strErrorCD 
			+ " ���b�Z�[�W��" 
			+ strErrorMsg; 
			
			// 2) logManager�N���X�̃C���X�^���X�𐶐���Log���o�͂���
			//this.logManager = new LogManager();
			LogManager.setStrStingXmlNm(this.strLogSetingFileNm);
			LogManager.systemLog(strLogOutMsg);
			
			//�B ���[�U�[�p���b�Z�[�W�̐���
			try {
				strUserMsg = ConstManager.getConstValue(ConstManager.Category.���b�Z�[�W, strErrorCD );
			} catch (ExceptionWaning e) {
				//ExceptionWaning�̏ꍇ�͏����𑱍s����
			} finally {

			}
			
			//�C Exception��ExceptionSystem�Ɋi�[
			excSystem = new ExceptionSystem();
			//���[�U�[���b�Z�[�W
			excSystem.setUserMsg(strUserMsg);
			//�N���X��
			excSystem.setClassName(this.strClassName);
			//���b�Z�[�W�ԍ�
			excSystem.setMsgNumber("");
			//�V�X�e���G���[�R�[�h
			excSystem.setSystemErrorCD(ex.getClass().getName());
			//�V�X�e���G���[���b�Z�[�W
			excSystem.setSystemErrorMsg(strErrorMsg);
			
			//�i�[�ς݂�Exception��j������B
			ex = null;
			
		}
		
		//��O��߂�l�ɐݒ肷��B 
		if ( excSystem != null ) {
			ret = excSystem;
		} else if ( excUser != null ) {
			ret = excUser;
		} else if ( excWaning != null ) {
			ret = excWaning;
		}
		return ret;
		
	}
	/**
	 * ��O����ʃN���X�ɃX���[����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void ThrowException() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		//�D ��O�̃X���[ ���C���X�^���X�̂���N���X���X���[
		if ( this.exceptionSystem != null ) {
			throw this.exceptionSystem;
			
		} else if ( this.exceptionUser != null ) {
			throw this.exceptionUser;
			
		} else if ( this.exceptionWaning != null ) {
			throw this.exceptionWaning;
			
		}

	}
	/**
	 * �G�N�Z�v�V�����X���[(2)
	 * @param ExKind : ��O���
	 * @param MsgNumber : ���b�Z�[�W�ԍ�
	 * @param ChangMsg1 : �u������������@
	 * @param ChangMsg2 : �u������������A
	 * @param ChangMsg3 : �u������������B
	 * @param DebugMsg  : ���O�o�̓��b�Z�[�W
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public void ThrowException(
			ExceptionKind ExKind
			, String MsgNumber
			, String ChangMsg1
			, String ChangMsg2
			, String ChangMsg3
			, String DebugMsg
		)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���݂̓��t
		String strDateTime = "";
		//���[�U�[���b�Z�[�W
		String strUserMsg = "";
		//���O�ɏo�͂��郁�b�Z�[�W		
		String strLogOutMsg = "";
		
		//�@ ��O�̎�ނ𔻒肵�AException�̃C���X�^���X�𐶐�
		if ( ExKind == ExceptionKind.���Exception ) {
			this.exceptionUser = new ExceptionUser();
			
		} else if ( ExKind == ExceptionKind.�x��Exception ) {
			this.exceptionWaning = new ExceptionWaning();
			
		} else if ( ExKind == ExceptionKind.�V�X�e��Exception ) {
			this.exceptionSystem = new ExceptionSystem();
			
		}

		//�A ���b�Z�[�W�̕ҏW
		try {
			strUserMsg = ConstManager.getConstValue(ConstManager.Category.���b�Z�[�W, MsgNumber);
			
		} catch (ExceptionWaning e) {
			// ���߂�l��ExceptionWaning�̏ꍇ�́A�������s	

		} finally {	
		
		}
				
		//�u������������Ń��b�Z�[�W�����H����
		strUserMsg = strUserMsg.replace("$1", ChangMsg1);
		strUserMsg = strUserMsg.replace("$2", ChangMsg2);
		strUserMsg = strUserMsg.replace("$3", ChangMsg3);
		
		//�B ��O�̕ҏW
		if ( ExKind == ExceptionKind.���Exception ) {
			this.exceptionUser.setUserMsg(strUserMsg);				//���[�U�[���b�Z�[�W
			this.exceptionUser.setClassName(this.strClassName);		//�N���X��
			this.exceptionUser.setMsgNumber(MsgNumber);				//���b�Z�[�W�ԍ�
			this.exceptionUser.setSystemErrorCD("");				//�V�X�e���G���[�R�[�h
			this.exceptionUser.setSystemErrorMsg("");				//�V�X�e���G���[���b�Z�[�W

		} else if ( ExKind == ExceptionKind.�x��Exception ) {
			this.exceptionWaning.setUserMsg(strUserMsg);			//���[�U�[���b�Z�[�W
			this.exceptionWaning.setClassName(this.strClassName);	//�N���X��
			this.exceptionWaning.setMsgNumber(MsgNumber);			//���b�Z�[�W�ԍ�
			this.exceptionWaning.setSystemErrorCD("");				//�V�X�e���G���[�R�[�h
			this.exceptionWaning.setSystemErrorMsg("");				//�V�X�e���G���[���b�Z�[�W

		} else if ( ExKind == ExceptionKind.�V�X�e��Exception ) {
			this.exceptionSystem.setUserMsg(strUserMsg);			//���[�U�[���b�Z�[�W
			this.exceptionSystem.setClassName(this.strClassName);	//�N���X��
			this.exceptionSystem.setMsgNumber(MsgNumber);			//���b�Z�[�W�ԍ�
			this.exceptionSystem.setSystemErrorCD("");				//�V�X�e���G���[�R�[�h
			this.exceptionSystem.setSystemErrorMsg("");				//�V�X�e���G���[���b�Z�[�W

		}
		
		//�C log�̏o��
		// 1) ���b�Z�[�W�̕ҏW
		// YYYY/MM/DD hh:mm:ss �N���X�� ���b�Z�[�W�ԍ� ���[�U�[���b�Z�[�W ���O�o�̓��b�Z�[�W
		strDateTime = this.getDateTimeNow();	
		strLogOutMsg = 
		" �y" 
		+ DebugMsg
		+ "�z �N���X����" 
		+ this.strClassName 
		+ " ���b�Z�[�W�ԍ���" 
		+ MsgNumber 
		+ " ���b�Z�[�W��" 
		+ strUserMsg; 

		// 2) logManger�N���X�̃C���X�^���X�𐶐�
		//this.logManager = new LogManager();
		LogManager.setStrStingXmlNm(this.strLogSetingFileNm);
		
		// 3) ���O���o�͂���
		// ��O��ނ̔���
		if ( ExKind == ExceptionKind.���Exception ) {
			LogManager.userErrorLog(
					strDateTime 
					+ " [USER EXCEPTION] " 
					+ strLogOutMsg);
			
		} else if ( ExKind == ExceptionKind.�x��Exception ) {
			LogManager.warningErrorLog(
					strDateTime 
					+ " [WANING EXCEPTION] " 
					+ strLogOutMsg);

		} else if ( ExKind == ExceptionKind.�V�X�e��Exception ) {
			LogManager.systemLog(
					strDateTime 
					+ " [SYSTEM EXCEPTION] " 
					+ strLogOutMsg);

		}

		//�D ��O�̃X���[ ���C���X�^���X�̂���N���X���X���[
		if ( this.exceptionSystem != null ) {
			throw this.exceptionSystem;

		} else if ( this.exceptionUser != null ) {
			throw this.exceptionUser;

		} else if ( this.exceptionWaning != null ) {
			throw this.exceptionWaning;

		}
	}

	/**
	 * �G�N�Z�v�V�����X���[(3)
	 * @param ExKind : ��O���
	 * @param MsgNumber : ���b�Z�[�W�ԍ�
	 * @param ChangMsg1 : �u������������@
	 * @param ChangMsg2 : �u������������A
	 * @param ChangMsg3 : �u������������B
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public void ThrowException(
			ExceptionKind ExKind
			, String MsgNumber
			, String ChangMsg1
			, String ChangMsg2
			, String ChangMsg3
		) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		this.ThrowException(ExKind, MsgNumber, ChangMsg1, ChangMsg2, ChangMsg3, "");

	}
	
	/**
	 * ���O�o��
	 * @param strMsg : ���O�o�̓��b�Z�[�W
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	public void DebugLog(
			String strMsg
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strDateTime;			//���݂̓��t
		String strLogOutMsg = "";
		
		//�@ log���o�͂���
		
		// 1) ���b�Z�[�W�̕ҏW
		// YYY/MM/DD hh:mm:dd �N���X�� ���O�o�̓��b�Z�[�W 
		strDateTime = this.getDateTimeNow();	
		strLogOutMsg = strDateTime
		+ " [DEBUG LOG] �y" 
		+ strMsg
		+ "�z �N���X����" 
		+ this.strClassName; 
		
		// 2) logManger�N���X�̃C���X�^���X�𐶐���Log���o�͂���
		//this.logManager = new LogManager();
		LogManager.setStrStingXmlNm(this.strLogSetingFileNm);
		LogManager.infoLog(strLogOutMsg);
		
	}
	
	/**
	 * ���݂̓��t�E���Ԃ��擾
	 * @return [YYYY/MM/DD hh:mm:ss]���擾
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	private String getDateTimeNow() {
		String strDateTime = "";

		//���݂̔N�������Ԃ��擾
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);			//���݂̔N���擾
	    int mon = cal.get(Calendar.MONTH) + 1;	//���݂̌����擾
	    int d = cal.get(Calendar.DATE);			//���݂̓����擾
	    int h = cal.get(Calendar.HOUR_OF_DAY);	//���݂̎����擾
	    int min = cal.get(Calendar.MINUTE);		//���݂̕����擾
	    int sec = cal.get(Calendar.SECOND);		//���݂̕b���擾
	    
	    //YYYY/MM/DD hh:mm:ss
	    strDateTime = y + "/" + mon + "/" + d + " " + h + ":" + min + ":" + sec;
		
		return strDateTime;
	}
	
	/**
	 * �V�X�e���G���[���b�Z�[�W�̎擾
	 * @param ex : Exception
	 * @return �V�X�e���G���[���b�Z�[�W
	 * 
	 * @author TT.k-katayama
	 * @since  2009/03/26
	 */
	private String getSystemErrMessage(Exception ex) {
		String strErrMessage = "";

		//���b�Z�[�W
		strErrMessage += ex.getMessage();
		//���\�b�h��
		strErrMessage += " " + ex.getStackTrace()[0].getMethodName();
		//�t�@�C����
		strErrMessage += " " + ex.getStackTrace()[0].getFileName();
		//�s�ԍ�
		strErrMessage += " " + ex.getStackTrace()[0].getLineNumber();
		
		return strErrMessage;

	}
	
}
