package jp.co.blueflag.shisaquick.jws.manager;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;

/*****************************************************************************************
 * 
 * �_�E�����[�h�p�X�f�[�^ �N���X
 *   @author k-katayama
 *   @since 2009/06/01
 *   
 *****************************************************************************************/
public class DownloadPathData extends DataBase {

	private String strDownloadPath;		//�_�E�����[�h�p�X
	private XmlData xdtData;				//XML�f�[�^
	
	private ExceptionBase ex;				//�G���[����
	
	/************************************************************************************
	 * 
	 *  �R���X�g���N�^
	 *   @param xmlData : XML�f�[�^
	 *   @param strKinoId : �@�\ID
	 *   
	 ************************************************************************************/
	public DownloadPathData() throws ExceptionBase {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		try {
			setStrDownloadPath(null);
			xdtData = null;		
			ex = null;
			
		} catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�_�E�����[�h�p�X�f�[�^�̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/************************************************************************************
	 * 
	 *  XML�f�[�^�̐ݒ�
	 *   @param xmlData : XML�f�[�^
	 *   @param strKinoId : �@�\ID
	 *   
	 ************************************************************************************/
	public void setDownloadPathData(XmlData xmlData, String strKinoId) throws ExceptionBase{
		
		this.xdtData = xmlData;
		
		try{
			
			//--------------------------------------------------------
			//
			//�@�_�E�����[�h�p�X�f�[�^ �i�[
			//
			//--------------------------------------------------------

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
				
				//�_�E�����[�h�p�X�f�[�^�֊i�[
				for(int j=0; j<recData.size(); j++){
					
					//�@���ږ��擾
					String recNm = ((String[])recData.get(j))[1];
					//�@���ڒl�擾
					String recVal = ((String[])recData.get(j))[2];
	
					//---------------- SA800�f�[�^�֒l�Z�b�g --------------------
					if ( recNm == "URLValue" ) {
						//URL
						this.strDownloadPath = recVal;
						
					}
				}
			}
		
		}catch(ExceptionBase e){
			throw e;
			
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�_�E�����[�h�p�X�f�[�^��XML�f�[�^�ݒ�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}

	/**
	 * �_�E�����[�h�p�X �Z�b�^�[
	 * @param _strDownloadPath : �_�E�����[�h�p�X�̒l���i�[���� 
	 */
	public void setStrDownloadPath(String _strDownloadPath) {
		this.strDownloadPath = _strDownloadPath;
	}

	/**
	 * �_�E�����[�h�p�X �Q�b�^�[
	 * @return strDownloadPath : �_�E�����[�h�p�X�̒l��Ԃ�
	 */
	public String getStrDownloadPath() {
		return strDownloadPath;
	}

}
