/*
 * �t�@�C���`�F�b�N�N���X
 * �t�@�C���p�X�ɑ��݂���Ώۃt�@�C���̑��݃`�F�b�N
 */
package jp.co.blueflag.shisaquick.srv.common;

import java.io.File;

/**
 *
 * Class���@: �t�@�C���`�F�b�N
 * �����T�v : �Ώۃt�@�C���̑��݃`�F�b�N�i�T�[�o�[���j
 *
 * @since   2014/08/27
 * @author  E.Kitazawa
 * @version 1.0
 */
public class FileChk {

	private ExceptionManager em;		//Exception�Ǘ��N���X

	/**
	 * �R���X�g���N�^
	 *  : �t�@�C���R�s�[�p�R���X�g���N�^
	 */
	public FileChk() {
		//�N���X���������Ƃ��āAExceptionManager�̃C���X�^���X�𐶐�����B
		this.em = new ExceptionManager(this.getClass().getName());

	}

	/**
	 * �t�@�C���`�F�b�N
	 *  : �Ώۃt�@�C�����T�[�o�[�ɑ��݂��邩�`�F�b�N����B
	 *
	 * @param  strFilePath
	 * @return int    �t�@�C�������݂���i�ʏ�t�@�C���j�F1
	 *                �t�@�C�������݂���i�t�H���_�[�j�F2
	 *                �t�@�C�������݂���i���̑��j�F9
	 *                �t�@�C�������݂��Ȃ����F-1
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public int fileChkLogic(String strFilePath) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int retint = -1;			// �߂�l�̐錾;

		try {
			if (strFilePath.equals("")) {
				return retint;
			}

			//�t�@�C��Obj�쐬
			File file = new File(strFilePath);

			// �t�@�C�����݃`�F�b�N�i�t�@�C���������̓f�B���N�g���j
			if (file.exists()) {
				// �t�@�C�������݂��鎞�Atrue ��Ԃ�
				retint = 9;

				if (file.isFile()) {
					retint = 1;
				} else if (file.isDirectory()) {
					retint = 2;
				}
			}

		} catch( Exception ex ) {
			this.em.ThrowException(ex, "");
		} finally {

		}
		return retint;
	}

	/**
	 * �f�B���N�g�����t�@�C���`�F�b�N
	 *  : �Ώۃf�B���N�g�����Ƀt�@�C�������݂��邩�`�F�b�N����B
	 *
	 * @param  strDir
	 * @return String[] �Ώۃf�B���N�g�����̃t�@�C���ꗗ
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public String[] fileListChkLogic(String strDir) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String[] retFile = null;			// �߂�l�̐錾;

		try {
			if (strDir.equals("")) {
				return retFile;
			}

			//�t�@�C��Obj�쐬
			File dir = new File(strDir);

			// �t�H���_�[���̃t�@�C���ꗗ
			File[] files1 = dir.listFiles();

			if (files1 != null && files1.length > 0) {
				// �t�H���_�[���Ƀt�@�C�����݂��鎞�A�t�@�C�����X�g��Ԃ�
				retFile = new String[files1.length];
				for (int i=0; i<files1.length; i++) {
					retFile[i] = files1[i].getName();
				}
			}

		} catch( Exception ex ) {
			this.em.ThrowException(ex, "");
		} finally {

		}
		return retFile;
	}

}
