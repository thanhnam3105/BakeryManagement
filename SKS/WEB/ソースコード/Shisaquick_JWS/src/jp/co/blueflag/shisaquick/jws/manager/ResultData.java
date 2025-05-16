package jp.co.blueflag.shisaquick.jws.manager;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/**
 * 
 * Result�f�[�^�ێ��N���X
 *  : Servlet�Ƃ̒ʐM�ɂ�蔭�������G���[���b�Z�[�W���i�[����
 * 
 * @author TT.katayama
 * @since 2009/04/22
 *
 */
public class ResultData extends DataBase {

	private XmlData xdtData;			//XML�f�[�^
	private ExceptionBase ex;			//�G���[�n���h�����O

	private String strReturnFlg;			//������
	private String strErrorMsg;				//�G���[���b�Z�[�W
	private String strClassNm;				//��������(�N���X��)
	private String strErrmsgNo;			//�G���[���b�Z�[�W�ԍ�
	private String strErrorCd;				//�G���[�R�[�h
	private String strSystemMsg;			//�V�X�e���G���[���b�Z�[�W
	private String strDebuglevel;			//�f�o�b�O���x��

	/**
	 * �R���X�g���N�^
	 */
	public ResultData() throws ExceptionBase{
		//�X�[�p�[�N���X�̃R���X�g���N�^���Ăяo��
		super();
		
		strReturnFlg = "";
		strErrorMsg = "";
		strClassNm = "";
		strErrmsgNo = "";
		strErrorCd = "";
		strSystemMsg = "";
		strDebuglevel = "0";
		
		try {
			//�G���[�n���h�����O�N���X�̐���
			this.ex = new ExceptionBase();

			//Result�f�[�^��������
			this.initResultData();
			
		} catch ( Exception e ) {	
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("Result�f�[�^�ێ��̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}
	
	/**
	 * 0001.�f�[�^�ݒ�
	 *  : XML�f�[�^��莎��e�[�u���f�[�^�𐶐�����B
	 * @param xdtData : XML�f�[�^
	 * @throws ExceptionBase 
	 */
	public void setResultData(XmlData xdtSetXml) throws ExceptionBase{
		this.xdtData = xdtSetXml;
		
		try {
			//Result�f�[�^��������
			this.initResultData();
			
			/**********************************************************
			 *�@Result�f�[�^�i�[
			 *********************************************************/
			//�@�\ID�̐ݒ�
			String strKinoId = "RESULT";
			
			//�S�̔z��擾
			ArrayList userData = xdtData.GetAryTag(strKinoId);
			
			//�@�\�z��擾
			ArrayList kinoData = (ArrayList)userData.get(0);

			//�e�[�u���z��擾
			ArrayList tableData = (ArrayList)kinoData.get(1);

			//���R�[�h�擾
			for(int i=1; i<tableData.size(); i++){
				//�@�P���R�[�h�擾
				ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
				
				//�����f�[�^�֊i�[
				for(int j=0; j<recData.size(); j++){
						
					//�@���ږ��擾
					String recNm = ((String[])recData.get(j))[1];
					//�@���ڒl�擾
					String recVal = ((String[])recData.get(j))[2];
	
					/*****************Result�f�[�^�֒l�Z�b�g*********************/
					if ( recNm == "flg_return" ) {
						//������
						this.strReturnFlg = recVal;
					}
					if ( recNm == "msg_error" ) {
						//�G���[���b�Z�[�W
						this.strErrorMsg = recVal;
					}
					if ( recNm == "nm_class" ) {
						//��������(�N���X��)
						this.strClassNm = recVal;
					}
					if ( recNm == "no_errmsg" ) {
						//�G���[���b�Z�[�W�ԍ�
						this.strErrmsgNo = recVal;
					}
					if ( recNm == "cd_error" ) {
						//�G���[�R�[�h
						this.strErrorCd = recVal;
					}
					if ( recNm == "msg_system" ) {
						//�V�X�e���G���[���b�Z�[�W
						this.strSystemMsg = recVal;
					}
					if ( recNm == "debuglevel" ) {
						//�f�o�b�O���x��
						this.strDebuglevel = recVal;
					}
					
				}
			}
			
		} catch (ExceptionBase e ) {
			throw e;
			
		} catch (Exception e) {
			e.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("Result�f�[�^�̃f�[�^�ݒ�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}
	
	/**
	 * Result�f�[�^����������
	 */
	public void initResultData() {
		this.strReturnFlg = "";
		this.strErrorMsg = "";
		this.strClassNm = "";
		this.strErrmsgNo = "";
		this.strErrorCd = "";
		this.strSystemMsg = "";
		this.strDebuglevel = "";
	}
	
	/**
	 * �������ʃ`�F�b�N���\�b�h
	 * @return true:��������, false:�������s 
	 */
	public boolean isReturnFlgCheck(){
		boolean blnRetChk = true;
		
		try{
			if ( this.getStrReturnFlg().equals("false") ) {
				blnRetChk = false;
			}	
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("Result�f�[�^�̏������ʃ`�F�b�N�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			DataCtrl.getInstance().getMessageCtrl().PrintErrMessage(ex);
			
		}finally{
			
		}
		
		return blnRetChk;
	}
	
	/**
	 * �f�[�^�\��
	 */
	public void dispData() throws ExceptionBase{
		
		try{
			System.out.println(" \n������ : " + this.getStrReturnFlg());
			System.out.println(" �G���[���b�Z�[�W : " + this.getStrErrorMsg());
			System.out.println(" ��������(�N���X��) : " + this.getStrClassNm());
			System.out.println(" �G���[���b�Z�[�W�ԍ� : " + this.getStrErrmsgNo());
			System.out.println(" �G���[�R�[�h : " + this.getStrErrorCd());
			System.out.println(" �V�X�e���G���[���b�Z�[�W : " + this.getStrSystemMsg());
			System.out.println(" �f�o�b�O���x�� : " + this.getStrDebuglevel());
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("Result�f�[�^�̃f�[�^�\���Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
	}

	/**
	 * ������ �Q�b�^�[
	 * @return strReturnFlg : �����ۂ̒l��Ԃ�
	 */
	public String getStrReturnFlg() {
		return strReturnFlg;
	}
	/**
	 * ������ �Z�b�^�[
	 * @param _strReturnFlg : �����ۂ̒l���i�[����
	 */
	public void setStrReturnFlg(String _strReturnFlg) {
		this.strReturnFlg = _strReturnFlg;
	}

	/**
	 * �G���[���b�Z�[�W �Q�b�^�[
	 * @return strErrorMsg : �G���[���b�Z�[�W�̒l��Ԃ�
	 */
	public String getStrErrorMsg() {
		return strErrorMsg;
	}
	/**
	 * �G���[���b�Z�[�W �Z�b�^�[
	 * @param _strErrorMsg : �G���[���b�Z�[�W�̒l���i�[����
	 */
	public void setStrErrorMsg(String _strErrorMsg) {
		this.strErrorMsg = _strErrorMsg;
	}

	/**
	 * ��������(�N���X��) �Q�b�^�[
	 * @return strClassNm : ��������(�N���X��)�̒l��Ԃ�
	 */
	public String getStrClassNm() {
		return strClassNm;
	}
	/**
	 * ��������(�N���X��) �Z�b�^�[
	 * @param _strClassNm : ��������(�N���X��)�̒l���i�[����
	 */
	public void setStrClassNm(String _strClassNm) {
		this.strClassNm = _strClassNm;
	}

	/**
	 * �G���[���b�Z�[�W�ԍ� �Q�b�^�[
	 * @return strErrmsgNo : �G���[���b�Z�[�W�ԍ��̒l��Ԃ�
	 */
	public String getStrErrmsgNo() {
		return strErrmsgNo;
	}
	/**
	 * �G���[���b�Z�[�W�ԍ� �Z�b�^�[
	 * @param _strErrmsgNo : �G���[���b�Z�[�W�ԍ��̒l���i�[����
	 */
	public void setStrErrmsgNo(String _strErrmsgNo) {
		this.strErrmsgNo = _strErrmsgNo;
	}

	/**
	 * �G���[�R�[�h �Q�b�^�[
	 * @return strErrorCd : �G���[�R�[�h�̒l��Ԃ�
	 */
	public String getStrErrorCd() {
		return strErrorCd;
	}
	/**
	 * �G���[�R�[�h �Z�b�^�[
	 * @param _strErrorCd : �G���[�R�[�h�̒l���i�[����
	 */
	public void setStrErrorCd(String _strErrorCd) {
		this.strErrorCd = _strErrorCd;
	}

	/**
	 * �V�X�e���G���[���b�Z�[�W �Q�b�^�[
	 * @return strSystemMsg : �V�X�e���G���[���b�Z�[�W�̒l��Ԃ�
	 */
	public String getStrSystemMsg() {
		return strSystemMsg;
	}
	/**
	 * �V�X�e���G���[���b�Z�[�W �Z�b�^�[
	 * @param _strSystemMsg : �V�X�e���G���[���b�Z�[�W�̒l���i�[����
	 */
	public void setStrSystemMsg(String _strSystemMsg) {
		this.strSystemMsg = _strSystemMsg;
	}

	/**
	 * �f�o�b�O���x�� �Q�b�^�[
	 * @return strDebuglevel : �f�o�b�O���x���̒l��Ԃ�
	 */
	public String getStrDebuglevel() {
		return strDebuglevel;
	}
	/**
	 * �f�o�b�O���x�� �Z�b�^�[
	 * @param _strDebuglevel : �f�o�b�O���x���̒l���i�[����
	 */
	public void setStrDebuglevel(String _strDebuglevel) {
		this.strDebuglevel = _strDebuglevel;
	}
	
}
