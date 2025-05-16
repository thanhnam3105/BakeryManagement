package jp.co.blueflag.shisaquick.jws.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.common.*;


/**
 * 
 * �z��f�[�^
 *  : �z��f�[�^�̊Ǘ����s��
 *
 */
public class ArrayData extends DataBase {

	private ArrayList aryArrayData;	//����p�z��
	private ExceptionBase ex;			//�G���[�n���h�����O
	private XmlData xdtData;
	
	private static int CONST_CD		= 0;	//�R�[�h�擾�p�萔
	private static int CONST_NAME		= 1;	//���̎擾�p�萔
	private static int CONST_LIST		= 2;	//���X�g�擾�p�萔
	
	/**
	 * �R���X�g���N�^
	 */
	public ArrayData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		ex = new ExceptionBase();
	}
	
	/**
	 * �P�ꌟ���i�R�[�h�w��j
	 *  : 1�����R�[�h��2�����R�[�h���z�������
	 * @param strCode1 : 1�����R�[�h
	 * @param strCode2 : 2�����R�[�h 
	 * @return �ԋp�z��
	 */
	public ArrayList selCodeShiteiT(String strCode1, String strCode2) throws ExceptionBase {
		
		ArrayList retArray = null;
		
		try {
			
			ArrayList aryOne = null;	//���K�w�z��
			ArrayList aryTwo = null;	//���K�w�z��
			
			//�z��v�f�����[�v
			for (int i=0;i < aryArrayData.size(); i++){
				
				//���K�w�z��擾
				aryOne = (ArrayList) aryArrayData.get(i); 
				
				//�����F1�����R�[�h�Ƒ��K�w�z��F�擾�R�[�h����v�����ꍇ
				if (strCode1.equals(aryOne.get(CONST_CD).toString())){

					//�ԋp�z��C���X�^���X����
					retArray = new ArrayList();
					
					//1�������ڐݒ�
					retArray.add(aryOne.get(CONST_CD));	//CD�ݒ�
					retArray.add(aryOne.get(CONST_NAME));	//���̐ݒ�
					retArray.add(new ArrayList());	//���K�w�z�񐶐�
					
					//���K�w�z��擾
					aryTwo = (ArrayList) aryOne.get(CONST_LIST); 

					//�z��v�f�����[�v
					for (int j=0;j < aryTwo.size(); j++){
						
						//�����F���K�w�R�[�h�Ƒ��K�w�z��F�擾�R�[�h����v�����ꍇ
						if (strCode2.equals(aryTwo.get(0).toString())){
							((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_CD));	//CD�ݒ�
							((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_NAME));	//���̐ݒ�
						}
						
					}
					
				}
			}
			
		} catch (Exception e) {
			
			ex.setStrErrmsg("���s�s�\�ȃG���[���������܂����B�V�X�e���S���ɘA�����Ă��������B");
			ex.setStrErrShori("ArrayData.selCodeShiteiT");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("�z��f�[�^�̊Ǘ��Ɏ��s���܂����B(�P�ꌟ���F�R�[�h)");

			throw ex;
			
		} finally {
			
		}
		
		return retArray;
	}

	/**
	 * �P�ꌟ���i���̎w��j
	 *  : 1�����R�[�h���̂�2�����R�[�h���̂��z�������
	 * @param strName1 : 1�����R�[�h����
	 * @param strName2 : 2�����R�[�h����
	 * @return �ԋp�z��
	 * @throws ExceptionBase 
	 */
	public ArrayList selNameShiteiT(String strName1, String strName2) throws ExceptionBase {
		
		ArrayList retArray = null;
		
		try {
			
			ArrayList aryOne = null;	//���K�w�z��
			ArrayList aryTwo = null;	//���K�w�z��
			
			//�z��v�f�����[�v
			for (int i=0;i < aryArrayData.size(); i++){
				
				//���K�w�z��擾
				aryOne = (ArrayList) aryArrayData.get(i); 
				
				//�����F1�����R�[�h���̂Ƒ��K�w�z��F�擾�R�[�h���̂���v�����ꍇ
				if (strName1.equals(aryOne.get(CONST_NAME).toString())){

					//�ԋp�z��C���X�^���X����
					retArray = new ArrayList();
					
					//1�������ڐݒ�
					retArray.add(aryOne.get(CONST_CD));	//CD�ݒ�
					retArray.add(aryOne.get(CONST_NAME));	//���̐ݒ�
					retArray.add(new ArrayList());	//���K�w�z�񐶐�
					
					//���K�w�z��擾
					aryTwo = (ArrayList) aryOne.get(2); 

					//�z��v�f�����[�v
					for (int j=0;j < aryTwo.size(); j++){
						
						//�����F2�����R�[�h���̂Ƒ��K�w�z��F�擾�R�[�h���̂���v�����ꍇ
						if (strName2.equals(aryTwo.get(CONST_NAME).toString())){
							((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_CD));	//CD�ݒ�
							((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_NAME));	//���̐ݒ�
						}
						
					}
					
				}
			}
			
		} catch (Exception e) {
			
			ex.setStrErrmsg("���s�s�\�ȃG���[���������܂����B�V�X�e���S���ɘA�����Ă��������B");
			ex.setStrErrShori("ArrayData.selNameShiteiT");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("�z��f�[�^�̊Ǘ��Ɏ��s���܂����B(�P�ꌟ���F����)");

			throw ex;

		} finally {
			
		}
		
		return retArray;
	}

	/**
	 * ���������i�R�[�h�w��j
	 *  : 1�����R�[�h���z�������
	 * @param strCode1 : 1�����R�[�h 
	 * @return �ԋp�z��
	 * @throws ExceptionBase 
	 */
	public ArrayList selCodeShiteiT(String strCode1) throws ExceptionBase { 

		ArrayList retArray = null;
		
		try {
			
			ArrayList aryOne = null;	//���K�w�z��
			ArrayList aryTwo = null;	//���K�w�z��
			
			//�z��v�f�����[�v
			for (int i=0;i < aryArrayData.size(); i++){
				
				//���K�w�z��擾
				aryOne = (ArrayList) aryArrayData.get(i); 
				
				//�����F1�����R�[�h�Ƒ��K�w�z��F�擾�R�[�h����v�����ꍇ
				if (strCode1.equals(aryOne.get(CONST_CD).toString())){

					//�ԋp�z��C���X�^���X����
					retArray = new ArrayList();
					
					//1�������ڐݒ�
					retArray.add(aryOne.get(CONST_CD));	//CD�ݒ�
					retArray.add(aryOne.get(CONST_NAME));	//���̐ݒ�
					retArray.add(new ArrayList());	//���K�w�z�񐶐�
					
					//���K�w�z��擾
					aryTwo = (ArrayList) aryOne.get(CONST_LIST); 

					//�z��v�f�����[�v
					for (int j=0;j < aryTwo.size(); j++){
						
						((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_CD));	//CD�ݒ�
						((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_NAME));	//���̐ݒ�
						
					}
					
				}
			}
			
		} catch (Exception e) {
			
			ex.setStrErrmsg("���s�s�\�ȃG���[���������܂����B�V�X�e���S���ɘA�����Ă��������B");
			ex.setStrErrShori("ArrayData.selCodeShiteiT");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("�z��f�[�^�̊Ǘ��Ɏ��s���܂����B(���������F�R�[�h)");

			throw ex;

		} finally {
			
		}
		
		return retArray;

	}

	/**
	 * ���������i���̎w��j
	 *  : 1�����R�[�h���̂��z�������
	 * @param strName1 : 1�����R�[�h����
	 * @return �ԋp�z��
	 * @throws ExceptionBase 
	 */
	public ArrayList selNameShiteiT(String strName1) throws ExceptionBase { 

		ArrayList retArray = null;
		
		try {
			
			ArrayList aryOne = null;	//���K�w�z��
			ArrayList aryTwo = null;	//���K�w�z��
			
			//�z��v�f�����[�v
			for (int i=0;i < aryArrayData.size(); i++){
				
				//���K�w�z��擾
				aryOne = (ArrayList) aryArrayData.get(i); 
				
				//�����F1�����R�[�h���̂Ƒ��K�w�z��F�擾�R�[�h���̂���v�����ꍇ
				if (strName1.equals(aryOne.get(CONST_NAME).toString())){

					//�ԋp�z��C���X�^���X����
					retArray = new ArrayList();
					
					//1�������ڐݒ�
					retArray.add(aryOne.get(CONST_CD));	//CD�ݒ�
					retArray.add(aryOne.get(CONST_NAME));	//���̐ݒ�
					retArray.add(new ArrayList());	//���K�w�z�񐶐�
					
					//���K�w�z��擾
					aryTwo = (ArrayList) aryOne.get(2); 

					//�z��v�f�����[�v
					for (int j=0;j < aryTwo.size(); j++){
						
						((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_CD));	//CD�ݒ�
						((ArrayList)retArray.get(CONST_LIST)).add(aryTwo.get(CONST_NAME));	//���̐ݒ�
						
					}
					
				}
			}
			
		} catch (Exception e) {
			
			ex.setStrErrmsg("���s�s�\�ȃG���[���������܂����B�V�X�e���S���ɘA�����Ă��������B");
			ex.setStrErrShori("ArrayData.selNameShiteiT");
			ex.setStrMsgNo("");
			ex.setStrErrCd("");
			ex.setStrSystemMsg("�z��f�[�^�̊Ǘ��Ɏ��s���܂����B(���������F�R�[�h)");

			throw ex;

		} finally {
			
		}
		
		return retArray;

	}

	
	/**
	 * ����p�z�� �Q�b�^�[
	 * @return aryArrayData : ����p�z��̒l��Ԃ�
	 */
	public ArrayList getAryArrayData() {
		return aryArrayData;
	}
	/**
	 * ����p�z�� �Z�b�^�[
	 * @param _aryArrayData : ����p�z��̒l���i�[����
	 */
	public void setAryArrayData(ArrayList _aryArrayData) {
		this.aryArrayData = _aryArrayData;
	}

}
