/* 
 * �t�@�C�������폜�N���X
 * �t�@�C���p�X�ɑ��݂���Ώۃt�@�C�����폜
 */
package jp.co.blueflag.shisaquick.srv.common;

import java.io.File;

/**
 * 
 * Class���@: �t�@�C���폜
 * �����T�v : �_�E�����[�h���ꂽ�Ώۃt�@�C�����T�[�o�[���폜����B
 *
 * @since   2009/03/16
 * @author  TT.Furuta
 * @version 1.0
 */
public class FileDelete {

	private ExceptionManager em;		//Exception�Ǘ��N���X
	
	/**
	 * �R���X�g���N�^
	 *  : �t�@�C���폜�p�R���X�g���N�^
	 */
	public FileDelete() {
		//�N���X���������Ƃ��āAExceptionManager�̃C���X�^���X�𐶐�����B
		this.em = new ExceptionManager(this.getClass().getName());
		
	}
	
	/**
	 * �t�@�C���폜
	 *  : �T�[�o�[�ɑ��݂���Ώۃt�@�C�����폜����B
	 *  
	 * @param  strFilePath
	 * @return �Ȃ�
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void fileDeleteLogic(String strFilePath) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {
			//�t�@�C��Obj�쐬
			File file = new File(strFilePath);
			
			//�t�@�C���폜
			file.delete();
			
		} catch( Exception ex ) {
			this.em.ThrowException(ex, "");
		} finally {
			
		}
		
	}
}
