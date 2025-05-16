package jp.co.blueflag.shisaquick.srv.base;


import java.util.List;
import java.io.File;
import java.io.BufferedWriter; 
import java.io.FileWriter;
import java.io.IOException;

import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * CSV�o�̓t�@�C���쐬
 *  : �T�[�o�[�w��t�H���_�ւ�CSV�t�@�C���̍쐬�@
 *
 */
public class FileCreateCSV extends ObjectBase {
	
	/**
	 * �R���X�g���N�^
	 *  : CSV�o�̓t�@�C���쐬�R���X�g���N�^
	 */
	public FileCreateCSV() {
		super();
		
	}
	/**
	 * CSV�t�@�C���쐬
	 *  : CSV�t�@�C�����쐬����B
	 * @param strFilePath : �t�@�C�����iFULL�p�X�j
	 * @param lstCreateCSV : �o�̓f�[�^
	 * @return �o�̓t�@�C���� 
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void csvFileCreater(
			String strFilePath
			, List<?> lstCreateCSV
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning { 

	    BufferedWriter bw = null;
		
		try {
			//�t�@�C���I�u�W�F�N�g�̐���
			File file = new File(strFilePath);
			//�t�@�C���L���m�F�i�����ꍇ�́A�V������������j
			checkBeforeWritefile(file);
			//�o�̓o�b�t�@�𐶐�
		    bw = new BufferedWriter(new FileWriter(file));

		    //�o�̓f�[�^�̊i�[
		    setData(lstCreateCSV, ",", bw);

		    //�t�@�C���̏o��
    		bw.newLine();
			
    		//�t�@�C���̃N���[�Y
			if (bw != null){
			    bw.close();
			}
			
		} catch(Exception e) {
			em.ThrowException(e, "CSV�̏o�͂Ɏ��s���܂����B");
			
		} finally {
			
		}
		
	}
	
	/**
	 * �t�@�C���̎��Ԃ��m�F����B
	 * @param file : �o�̓t�@�C���̃t�@�C���I�u�W�F�N�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
    private void checkBeforeWritefile(File file) 
    throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//�t�@�C���̗L���𔻒�
			if (file.exists()){
				//�t�@�C�������݂���ꍇ
				
				//�t�@�C���̑����𔻒肷��i�Ώۂ��t�@�C�����H�@�������݉\���H�j
				if (file.isFile() && file.canWrite()){
					//�t�@�C�����������݉\�ȏꍇ
					
	            }else{
	            	//�t�@�C�����������ݕs�\�ȏꍇ
	            	em.ThrowException(ExceptionKind.���Exception
	            			, "E000401"
	            			, file.getName()
	            			, ""
	            			, "");
	            	
	            }

			}else{
				//�t�@�C�������݂��Ȃ��ꍇ
				
				//�V�����t�@�C���𐶐�����
				file.createNewFile();
				
			}
			
		} catch(IOException e) {
			this.em.ThrowException(ExceptionKind.���Exception
        			, "E000402"
        			, ""
        			, ""
        			, "");
			
		} catch(Exception e) {
			this.em.ThrowException(e, "�t�@�C���̐����Ɏ��s���܂����B");
			
		} finally {
			
		}

    }
	/**
	 * �o��Data��CSV�`����BufferedWriter�Ɋi�[����
	 * @param listData : �o�̓f�[�^
	 * @param separator : CSV��؂蕶��
	 * @param bufferedWriter : �o�̓f�[�^�i�[�I�u�W�F�N�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void  setData(
			List<?> listData
			, String separator
			, BufferedWriter bw
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//�f�[�^�i�[
			for (int i = 0; i < listData.size(); i++) {
				
				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) listData.get(i);

				for (int ix = 0; ix < items.length; ix++) {
					//�f�[�^���Z�b�g����
					bw.write(toString(items[ix]));
					
					//�ŏI���ڈȊO�̏ꍇ
					if (ix < items.length-1) {
						//CSV�Z�p���[�^���Z�b�g����
					    bw.write(separator);
					}
					
				}
				//���s���s��
				bw.newLine();

			}
			
		} catch(Exception e) {
			this.em.ThrowException(e, "CSV�o�̓o�b�t�@�̐����Ɏ��s���܂����B");
			
		} finally {
			
		}

	}
	
}
