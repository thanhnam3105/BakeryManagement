/*
 * �t�@�C�������R�s�[�N���X
 * �t�@�C���p�X�ɑ��݂���Ώۃt�@�C���̑��݃`�F�b�N�A�t�@�C���R�s�[
 */
package jp.co.blueflag.shisaquick.srv.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * Class���@: �t�@�C������
 * �����T�v : �T�[�o�[���f�B���N�g���쐬�A�t�@�C���R�s�[
 *
 * @since   2014/08/27
 * @author  E.Kitazawa
 * @version 1.0
 */
public class FileCreate {

	private ExceptionManager em;		//Exception�Ǘ��N���X

	/**
	 * �R���X�g���N�^
	 *  : �t�@�C���R�s�[�p�R���X�g���N�^
	 */
	public FileCreate() {
		//�N���X���������Ƃ��āAExceptionManager�̃C���X�^���X�𐶐�����B
		this.em = new ExceptionManager(this.getClass().getName());

	}

	/**
	 * �z���_�[�쐬
	 *  : �T�[�o�[�Ƀz���_�[�����݂��Ȃ����z���_�[���쐬����B
	 *
	 * @param  strDir
	 * @return boolean  �쐬�ɐ���
	 *                  �쐬���s
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public boolean createDirLogic(String strDir) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		boolean retBool = false;			// �߂�l�̐錾;

		try {
			if (strDir.equals("")) {
				return retBool;
			}

			//�t�@�C��Obj�쐬
			File file = new File(strDir);

			// �t�@�C�����݃`�F�b�N�i�t�@�C���������̓f�B���N�g���j
			if (!file.exists()) {
				// �t�@�C�������݂��Ȃ����A�f�B���N�g���쐬
				file.mkdir();
				retBool = true;
			}

		} catch( Exception ex ) {
			this.em.ThrowException(ex, "");
		} finally {

		}
		return retBool;
	}


	/**
	 * �t�@�C���R�s�[
	 *  : �t�@�C���R�s�[�����s����B
	 *
	 * @param  inFpath		�T�[�o�[�t�@�C�����i���͑��j
	 * 	       outFpath		�T�[�o�[�t�@�C�����i�o�͑��j
	 * @return �Ȃ�
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void fileCopyLogic(String inFpath, String outFpath) throws ExceptionSystem, ExceptionUser, ExceptionWaning {


        try {
            //File�I�u�W�F�N�g�𐶐�����
            FileInputStream fis = new FileInputStream(inFpath);
            FileOutputStream fos = new FileOutputStream(outFpath);

            //���̓t�@�C�������̂܂܏o�̓t�@�C���ɏ����o��
            byte buf[] = new byte[256];
            int len;
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }

            //�I������
            fos.flush();
            fos.close();
            fis.close();

        	fis = null;
        	fos = null;

            System.out.println("�R�s�[���������܂����B");

        } catch (IOException ex) {
            //��O������
            System.out.println("�R�s�[�Ɏ��s���܂����B");
            ex.printStackTrace();

        } finally {

		}

	}

}
