package jp.co.blueflag.shisaquick.jws.common;

import java.util.ArrayList;

/*******************************************************************************************
 *
 * �������[�h�w��
 *
 *******************************************************************************************/
public class ModeCtrl {

	//�Q�ƃ��[�h�ҏW�p�^�[��
	private ArrayList aryModeCheck_Sansho = new ArrayList();
	//�ڍ׃��[�h�ҏW�p�^�[��
	private ArrayList aryModeCheck_Shosai = new ArrayList();
	//�V�K���[�h�ҏW�p�^�[��
	private ArrayList aryModeCheck_Sinki = new ArrayList();
	//���@�R�s�[���[�h�ҏW�p�^�[��
	private ArrayList aryModeCheck_Copy = new ArrayList();

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
	//����R�s�[���[�h�ҏW�p�^�[��
	private ArrayList aryModeCheck_ShisakuCopy = new ArrayList();
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end

	ExceptionBase ex;

	/*****************************************************************************************
	 *
	 * �R���X�g���N�^
	 * �@�F�R���|�[�l���g�ҏW�p�^�[����o�^����
	 * �@@author TT nishigawa
	 *
	 *****************************************************************************************/
	public ModeCtrl() throws ExceptionBase{
		try{

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
/*******************************************************************************************
 *
 * ����R�s�[���[�h
 *
 *******************************************************************************************/
			//------------------------ ����f�[�^��� -----------------------------
			//����CD-���[�U
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0001 , "false"});
			//����CD-�N
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0002 , "false"});
			//����CD-�ǔ�
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0003 , "false"});
			//�˗��ԍ�
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0004 , "false"});
			//�i��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0005 , "false"});
			//���@No-���[�U
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0006 , "false"});
			//���@No-���CD
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0007 , "false"});
			//���@No-���No
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0008 , "false"});
			//���@No-�ǔ�
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0009 , "false"});
			//�p�~
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0010 , "false"});
			//�o�^
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0011 , "false"});
			//�I��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0012 , "true"});
			//�����R�s�[
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0013 , "true"});
			//�S�R�s�[
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0014 , "true"});
			//��ʔԍ�
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0015 , "false"});

			//��߂�
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0152 , "false"});

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0153 , "false"});
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
			//�V�[�N���b�g
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0190 , "false"});
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End
			
			//--------------------------- �z���\(����\�@) --------------------------------
			//����I��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0016 , "true"});
			//���ӎ���No
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0017 , "false"});
			//���t
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0018 , "false"});
			//�����No
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0019 , "false"});
			//����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0020 , "false"});
			//���Fg
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0021 , "true"});
			//�H���I��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0022 , "false"});
			//�����I��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0023 , "false"});
			//�H������
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0024 , "false"});
			//����CD
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0025 , "false"});
			//�H����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0026 , "false"});
			//������
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0027 , "false"});
			//�P��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0028 , "false"});
			//����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0029 , "false"});
			//���ܗL��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0030 , "false"});
			//��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0031 , "false"});
			//�����H��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0032 , "true"});
			//�����ꗗ
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0033 , "false"});
			//��������
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0034 , "false"});
			//�H���}��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0035 , "false"});
			//�H���ړ��i���j
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0036 , "false"});
			//�H���ړ��i���j
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0037 , "false"});
			//�H���폜
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0038 , "false"});
			//�����}��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0039 , "false"});
			//�����ړ��i���j
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0040 , "false"});
			//�����ړ��i���j
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0041 , "false"});
			//�����폜
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0042 , "false"});
			//�����ǉ�
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0043 , "false"});
			//�����폜
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0044 , "false"});
			//�����R�s�[
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0045 , "false"});
			//�����ړ��i���j
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0046 , "false"});
			//�����ړ��i���j
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0047 , "false"});
			//����\�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0048 , "false"});
			//����ِ������o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0049 , "false"});
			//�d��d��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0124 , "false"});
			//���샊�X�g�R�s�[
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0125 , "false"});
			//�h�{�v�Z���o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0126 , "false"});

			//--------------------------- �����H�� --------------------------------
			//�����H���I��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0050 , "true"});
			//���e����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0051 , "false"});
			//��ɕ\��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0052 , "false"});
			//�V�K
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0053 , "false"});
			//�X�V
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0054 , "false"});

			//--------------------------- �����l(����\�A) --------------------------------
			//�����v�Z
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0055 , "false"});
			//���_�E�H���o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0056 , "false"});
			//�������o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0057 , "false"});
			//���x�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0058 , "false"});
			//�S�x�E���x�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0059 , "false"});
			//PH�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0060 , "false"});
			//���_�E�H���o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0061 , "false"});
			//��d�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0062 , "false"});
			//���������o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0063 , "false"});
			//�A���R�[���o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0064 , "false"});
			//�t���[�^�C�g��1�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0065 , "false"});
			//�t���[�^�C�g��2�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0066 , "false"});
			//�t���[�^�C�g��3�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0067 , "false"});
			//�ꊇ�`�F�b�N
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0068 , "false"});
			//�t���[�^�C�g��1
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0069 , "false"});
			//�t���[�^�C�g��2
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0070 , "false"});
			//�t���[�^�C�g��3
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0071 , "false"});
			//���_
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0072 , "false"});
			//�H��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0073 , "false"});
			//�������_�x
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0074 , "false"});
			//�������H��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0075 , "false"});
			//�������|�_
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0076 , "false"});
			//���x
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0077 , "false"});
			//�S�x
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0078 , "false"});
			//���x
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0079 , "false"});
			//PH
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0080 , "false"});
			//���_����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0081 , "false"});
			//�H������
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0082 , "false"});
			//��d
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0083 , "false"});
			//��������
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0084 , "false"});
			//�A���R�[��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0085 , "false"});
			//�t���[���e1
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0086 , "false"});
			//�t���[���e2
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0087 , "false"});
			//�t���[���e3
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0088 , "false"});
			//�쐬����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0089 , "false"});
			//�]��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0090 , "false"});
			//���͒l�ύX�m�F
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0091 , "true"});
// ADD start 20121016 QP@20505 No.11
			//�������̓}�X�^�l�擾
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0155 , "true"});
// ADD end 20121016 QP@20505 No.11
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			//������d
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0154 , "false"});
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
// ADD start 20121001 QP@20505 No.1
			//���������t���[�^�C�g��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0160 , "false"});
			//���������t���[���e
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0161 , "false"});
			//���������t���[�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0162 , "false"});
			//�A���R�[���t���[�^�C�g��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0163 , "false"});
			//�A���R�[���t���[���e
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0164 , "false"});
			//�A���R�[���t���[�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0165 , "false"});
			//�����|�_�Z�x
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0166 , "false"}); // false
			//�����|�_�Z�x �o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0167 , "false"});
			//�������l�r�f
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0168 , "false"}); // false
			//�������l�r�f �o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0169 , "false"});
			//�S�x�t���[�^�C�g��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0170 , "false"});
			//�S�x�t���[���e
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0171 , "false"});
			//�S�x�t���[���e �o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0172 , "false"});
			//���x�t���[�^�C�g��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0173 , "false"});
			//���x�t���[���e
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0174 , "false"});
			//���x�t���[���e �o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0175 , "false"});
			//�t���[�C�^�C�g��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0176 , "false"});
			//�t���[�C���e
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0177 , "false"});
			//�t���[�C �o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0178 , "false"});
			//�t���[�D�^�C�g��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0179 , "false"});
			//�t���[�D���e
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0180 , "false"});
			//�t���[�D �o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0181 , "false"});
			//�t���[�E�^�C�g��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0182 , "false"});
			//�t���[�E���e
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0183 , "false"});
			//�t���[�E �o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0184 , "false"});
// ADD end 20121001 QP@20505 No.1

			//--------------------------- ��{���(����\�B) --------------------------------
			//�����O���[�v
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0092 , "false"});
			//�����`�[��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0093 , "false"});
			//�ꊇ�\��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0094 , "false"});
			//�W������
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0095 , "false"});
			//���[�U
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0096 , "false"});
			//��������
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0097 , "false"});
			//�p�r
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0098 , "false"});
			//���i��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0099 , "false"});
			//���
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0100 , "false"});
			//�����w��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0101 , "false"});
			//�S�����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0102 , "false"});
			//�S���H��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0103 , "false"});
			//�S���c��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0104 , "false"});
			//�������@
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0105 , "false"});
			//�[�U���@
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0106 , "false"});
			//�E�ە��@
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0107 , "false"});
			//�e����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0108 , "false"});
			//�e��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0109 , "false"});
			//�e�ʁi�P�ʁj
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0110 , "false"});
			//���萔
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0111 , "false"});
			//�׎p
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0112 , "false"});
			//�戵���x
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0113 , "false"});
			//�ܖ�����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0114 , "false"});
			//������]
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0115 , "false"});
			//������]
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0116 , "false"});
			//�z�蕨��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0117 , "false"});
			//�̔�����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0118 , "false"});
			//�v�攄��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0119 , "false"});
			//�v�旘�v
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0120 , "false"});
			//�̔��㔄��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0121 , "false"});
			//�̔��㗘�v
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0122 , "false"});
			//��������
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0123 , "false"});

			//--------------------------- �������Z(����\�D) --------------------------------
			//���FG
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0149 , "false"});
			//�L������
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0127 , "false"});
			//���Ϗ[�U��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0128 , "false"});
			//�o��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0129 , "false"});
			//����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0130 , "false"});
			//���Z�m��T���v��No
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0131, "false"});
			//���Z�����Q��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0132, "false"});
			//�������Z�\���
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0133, "false"});
			//�[�U�ʐ���
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0134 , "false"});
			//�[�U�ʖ���
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0135 , "false"});
			//���v��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0136 , "false"});
			//������(kg)
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0137 , "false"});
			//������(�P�{��)
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0138 , "false"});
			//��d
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0139 , "false"});
			//�e��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0140 , "false"});
			//����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0141 , "false"});
			//���x����
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0142 , "false"});
			//��d���Z��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0143 , "false"});
			//�ޗ���
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0145 , "false"});
			//�����v/cs
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0146 , "false"});
			//�����v/��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0147 , "false"});
			//�e��
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0148 , "false"});
			//�������Z�o�^
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0150 , "false"});
			//�������Z�˗�Fg
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0151 , "false"});
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end


/*******************************************************************************************
 *
 * �Q�ƃ��[�h
 *
 *******************************************************************************************/
			//------------------------ ����f�[�^��� -----------------------------
			//����CD-���[�U
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0001 , "false"});
			//����CD-�N
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0002 , "false"});
			//����CD-�ǔ�
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0003 , "false"});
			//�˗��ԍ�
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0004 , "false"});
			//�i��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0005 , "false"});
			//���@No-���[�U
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0006 , "false"});
			//���@No-���CD
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0007 , "false"});
			//���@No-���No
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0008 , "false"});
			//���@No-�ǔ�
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0009 , "false"});
			//�p�~
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0010 , "false"});
			//�o�^
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0011 , "false"});
			//�I��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0012 , "true"});
			//�����R�s�[
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0013 , "false"});
			//�S�R�s�[
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0014 , "false"});
			//��ʔԍ�
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0015 , "false"});

			//��߂�
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0152 , "false"});

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0153 , "false"});
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
			
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
			//�V�[�N���b�g
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0190 , "false"});
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End
			
			//--------------------------- �z���\(����\�@) --------------------------------
			//����I��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0016 , "true"});
			//���ӎ���No
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0017 , "false"});
			//���t
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0018 , "false"});
			//�����No
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0019 , "false"});
			//����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0020 , "false"});
			//���Fg
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0021 , "false"});
			//�H���I��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0022 , "false"});
			//�����I��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0023 , "false"});
			//�H������
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0024 , "false"});
			//����CD
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0025 , "false"});
			//�H����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0026 , "false"});
			//������
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0027 , "false"});
			//�P��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0028 , "false"});
			//����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0029 , "false"});
			//���ܗL��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0030 , "false"});
			//��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0031 , "false"});
			//�����H��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0032 , "true"});
			//�����ꗗ
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0033 , "false"});
			//��������
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0034 , "false"});
			//�H���}��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0035 , "false"});
			//�H���ړ��i���j
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0036 , "false"});
			//�H���ړ��i���j
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0037 , "false"});
			//�H���폜
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0038 , "false"});
			//�����}��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0039 , "false"});
			//�����ړ��i���j
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0040 , "false"});
			//�����ړ��i���j
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0041 , "false"});
			//�����폜
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0042 , "false"});
			//�����ǉ�
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0043 , "false"});
			//�����폜
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0044 , "false"});
			//�����R�s�[
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0045 , "false"});
			//�����ړ��i���j
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0046 , "false"});
			//�����ړ��i���j
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0047 , "false"});
			//����\�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0048 , "false"});
			//����ِ������o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0049 , "false"});
			//�d��d��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0124 , "false"});
			//���샊�X�g�R�s�[
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0125 , "false"});
			//�h�{�v�Z���o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0126 , "false"});

			//--------------------------- �����H�� --------------------------------
			//�����H���I��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0050 , "true"});
			//���e����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0051 , "false"});
			//��ɕ\��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0052 , "false"});
			//�V�K
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0053 , "false"});
			//�X�V
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0054 , "false"});

			//--------------------------- �����l(����\�A) --------------------------------
			//�����v�Z
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0055 , "false"});
			//���_�E�H���o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0056 , "false"});
			//�������o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0057 , "false"});
			//���x�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0058 , "false"});
			//�S�x�E���x�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0059 , "false"});
			//PH�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0060 , "false"});
			//���_�E�H���o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0061 , "false"});
			//��d�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0062 , "false"});
			//���������o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0063 , "false"});
			//�A���R�[���o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0064 , "false"});
			//�t���[�^�C�g��1�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0065 , "false"});
			//�t���[�^�C�g��2�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0066 , "false"});
			//�t���[�^�C�g��3�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0067 , "false"});
			//�ꊇ�`�F�b�N
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0068 , "false"});
			//�t���[�^�C�g��1
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0069 , "false"});
			//�t���[�^�C�g��2
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0070 , "false"});
			//�t���[�^�C�g��3
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0071 , "false"});
			//���_
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0072 , "false"});
			//�H��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0073 , "false"});
			//�������_�x
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0074 , "false"});
			//�������H��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0075 , "false"});
			//�������|�_
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0076 , "false"});
			//���x
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0077 , "false"});
			//�S�x
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0078 , "false"});
			//���x
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0079 , "false"});
			//PH
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0080 , "false"});
			//���_����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0081 , "false"});
			//�H������
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0082 , "false"});
			//��d
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0083 , "false"});
			//��������
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0084 , "false"});
			//�A���R�[��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0085 , "false"});
			//�t���[���e1
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0086 , "false"});
			//�t���[���e2
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0087 , "false"});
			//�t���[���e3
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0088 , "false"});
			//�쐬����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0089 , "false"});
			//�]��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0090 , "false"});
			//���͒l�ύX�m�F
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0091 , "true"});
// ADD start 20121016 QP@20505 No.11
			//�������̓}�X�^�l�擾
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0155 , "true"});
// ADD end 20121016 QP@20505 No.11
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			//������d
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0154 , "false"});
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
// ADD start 20121001 QP@20505 No.1
			//���������t���[�^�C�g��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0160 , "false"});
			//���������t���[���e
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0161 , "false"});
			//���������t���[�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0162 , "false"});
			//�A���R�[���t���[�^�C�g��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0163 , "false"});
			//�A���R�[���t���[���e
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0164 , "false"});
			//�A���R�[���t���[�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0165 , "false"});
			//�����|�_�Z�x
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0166 , "false"}); // false
			//�����|�_�Z�x �o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0167 , "false"});
			//�������l�r�f
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0168 , "false"}); // false
			//�������l�r�f �o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0169 , "false"});
			//�S�x�t���[�^�C�g��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0170 , "false"});
			//�S�x�t���[���e
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0171 , "false"});
			//�S�x�t���[���e �o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0172 , "false"});
			//���x�t���[�^�C�g��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0173 , "false"});
			//���x�t���[���e
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0174 , "false"});
			//���x�t���[���e �o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0175 , "false"});
			//�t���[�C�^�C�g��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0176 , "false"});
			//�t���[�C���e
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0177 , "false"});
			//�t���[�C �o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0178 , "false"});
			//�t���[�D�^�C�g��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0179 , "false"});
			//�t���[�D���e
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0180 , "false"});
			//�t���[�D �o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0181 , "false"});
			//�t���[�E�^�C�g��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0182 , "false"});
			//�t���[�E���e
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0183 , "false"});
			//�t���[�E �o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0184 , "false"});
// ADD end 20121001 QP@20505 No.1

			//--------------------------- ��{���(����\�B) --------------------------------
			//�����O���[�v
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0092 , "false"});
			//�����`�[��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0093 , "false"});
			//�ꊇ�\��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0094 , "false"});
			//�W������
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0095 , "false"});
			//���[�U
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0096 , "false"});
			//��������
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0097 , "false"});
			//�p�r
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0098 , "false"});
			//���i��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0099 , "false"});
			//���
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0100 , "false"});
			//�����w��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0101 , "false"});
			//�S�����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0102 , "false"});
			//�S���H��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0103 , "false"});
			//�S���c��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0104 , "false"});
			//�������@
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0105 , "false"});
			//�[�U���@
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0106 , "false"});
			//�E�ە��@
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0107 , "false"});
			//�e����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0108 , "false"});
			//�e��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0109 , "false"});
			//�e�ʁi�P�ʁj
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0110 , "false"});
			//���萔
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0111 , "false"});
			//�׎p
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0112 , "false"});
			//�戵���x
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0113 , "false"});
			//�ܖ�����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0114 , "false"});
			//������]
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0115 , "false"});
			//������]
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0116 , "false"});
			//�z�蕨��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0117 , "false"});
			//�̔�����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0118 , "false"});
			//�v�攄��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0119 , "false"});
			//�v�旘�v
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0120 , "false"});
			//�̔��㔄��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0121 , "false"});
			//�̔��㗘�v
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0122 , "false"});
			//��������
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0123 , "false"});

			//--------------------------- �������Z(����\�D) --------------------------------
			//���FG
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0149 , "false"});
			//�L������
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0127 , "false"});
			//���Ϗ[�U��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0128 , "false"});
			//�o��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0129 , "false"});
			//����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0130 , "false"});
			//���Z�m��T���v��No
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0131, "false"});
			//���Z�����Q��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0132, "false"});
			//�������Z�\���
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0133, "false"});
			//�[�U�ʐ���
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0134 , "false"});
			//�[�U�ʖ���
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0135 , "false"});
			//���v��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0136 , "false"});
			//������(kg)
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0137 , "false"});
			//������(�P�{��)
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0138 , "false"});
			//��d
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0139 , "false"});
			//�e��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0140 , "false"});
			//����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0141 , "false"});
			//���x����
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0142 , "false"});
			//��d���Z��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0143 , "false"});
			//�ޗ���
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0145 , "false"});
			//�����v/cs
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0146 , "false"});
			//�����v/��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0147 , "false"});
			//�e��
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0148 , "false"});
			//�������Z�o�^
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0150 , "false"});
			//�������Z�˗�Fg
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0151 , "false"});


/*******************************************************************************************
 *
 * �ڍ׃��[�h
 *
 *******************************************************************************************/
			//------------------------ ����f�[�^��� -----------------------------
			//����CD-���[�U
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0001 , "false"});
			//����CD-�N
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0002 , "false"});
			//����CD-�ǔ�
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0003 , "false"});
			//�˗��ԍ�
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0004 , "true"});
			//�i��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0005 , "true"});
			//���@No-���[�U
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0006 , "false"});
			//���@No-���CD
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0007 , "false"});
			//���@No-���No
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0008 , "false"});
			//���@No-�ǔ�
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0009 , "false"});
			//�p�~
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0010 , "true"});
			//�o�^
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0011 , "true"});
			//�I��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0012 , "true"});
			//�����R�s�[
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0013 , "true"});
			//�S�R�s�[
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0014 , "true"});
			//��ʔԍ�
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0015 , "true"});

			//��߂�
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0152 , "true"});

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0153 , "true"});
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
			
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
			//�V�[�N���b�g
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0190 , "true"});
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

			//--------------------------- �z���\(����\�@) --------------------------------
			//����I��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0016 , "true"});
			//���ӎ���No
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0017 , "true"});
			//���t
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0018 , "true"});
			//�����No
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0019 , "true"});
			//����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0020 , "true"});
			//���Fg
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0021 , "true"});
			//�H���I��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0022 , "true"});
			//�����I��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0023 , "true"});
			//�H������
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0024 , "true"});
			//����CD
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0025 , "true"});
			//�H����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0026 , "true"});
			//������
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0027 , "true"});
			//�P��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0028 , "true"});
			//����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0029 , "true"});
			//���ܗL��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0030 , "true"});
			//��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0031 , "true"});
			//�����H��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0032 , "true"});
			//�����ꗗ
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0033 , "true"});
			//��������
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0034 , "true"});
			//�H���}��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0035 , "true"});
			//�H���ړ��i���j
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0036 , "true"});
			//�H���ړ��i���j
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0037 , "true"});
			//�H���폜
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0038 , "true"});
			//�����}��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0039 , "true"});
			//�����ړ��i���j
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0040 , "true"});
			//�����ړ��i���j
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0041 , "true"});
			//�����폜
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0042 , "true"});
			//�����ǉ�
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0043 , "true"});
			//�����폜
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0044 , "true"});
			//�����R�s�[
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0045 , "true"});
			//�����ړ��i���j
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0046 , "true"});
			//�����ړ��i���j
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0047 , "true"});
			//����\�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0048 , "true"});
			//����ِ������o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0049 , "true"});
			//�d��d��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0124 , "true"});
			//���샊�X�g�R�s�[
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0125 , "true"});
			//�h�{�v�Z���o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0126 , "true"});

			//--------------------------- �����H�� --------------------------------
			//�����H���I��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0050 , "true"});
			//���e����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0051 , "true"});
			//��ɕ\��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0052 , "true"});
			//�V�K
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0053 , "true"});
			//�X�V
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0054 , "true"});

			//--------------------------- �����l(����\�A) --------------------------------
			//�����v�Z
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0055 , "true"});
			//���_�E�H���o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0056 , "true"});
			//�������o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0057 , "true"});
			//���x�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0058 , "true"});
			//�S�x�E���x�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0059 , "true"});
			//PH�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0060 , "true"});
			//���_�E�H���o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0061 , "true"});
			//��d�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0062 , "true"});
			//���������o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0063 , "true"});
			//�A���R�[���o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0064 , "true"});
			//�t���[�^�C�g��1�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0065 , "true"});
			//�t���[�^�C�g��2�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0066 , "true"});
			//�t���[�^�C�g��3�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0067 , "true"});
			//�ꊇ�`�F�b�N
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0068 , "true"});
			//�t���[�^�C�g��1
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0069 , "true"});
			//�t���[�^�C�g��2
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0070 , "true"});
			//�t���[�^�C�g��3
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0071 , "true"});
			//���_
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0072 , "false"});
			//�H��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0073 , "false"});
			//�������_�x
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0074 , "false"});
			//�������H��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0075 , "false"});
			//�������|�_
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0076 , "false"});
			//���x
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0077 , "true"});
			//�S�x
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0078 , "true"});
			//���x
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0079 , "true"});
			//PH
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0080 , "true"});
			//���_����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0081 , "true"});
			//�H������
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0082 , "true"});
			//��d
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0083 , "true"});
			//��������
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0084 , "true"});
			//�A���R�[��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0085 , "true"});
			//�t���[���e1
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0086 , "true"});
			//�t���[���e2
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0087 , "true"});
			//�t���[���e3
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0088 , "true"});
			//�쐬����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0089 , "true"});
			//�]��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0090 , "true"});
			//���͒l�ύX�m�F
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0091 , "true"});
// ADD start 20121016 QP@20505 No.11
			//�������̓}�X�^�l�擾
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0155 , "true"});
// ADD end 20121016 QP@20505 No.11
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			//������d
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0154 , "true"});
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
// ADD start 20121001 QP@20505 No.1
			//���������t���[�^�C�g��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0160 , "true"});
			//���������t���[���e
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0161 , "true"});
			//���������t���[�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0162 , "true"});
			//�A���R�[���t���[�^�C�g��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0163 , "true"});
			//�A���R�[���t���[���e
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0164 , "true"});
			//�A���R�[���t���[�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0165 , "true"});
			//�����|�_�Z�x
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0166 , "false"}); // false
			//�����|�_�Z�x �o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0167 , "true"});
			//�������l�r�f
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0168 , "false"}); // false
			//�������l�r�f �o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0169 , "true"});
			//�S�x�t���[�^�C�g��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0170 , "true"});
			//�S�x�t���[���e
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0171 , "true"});
			//�S�x�t���[���e �o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0172 , "true"});
			//���x�t���[�^�C�g��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0173 , "true"});
			//���x�t���[���e
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0174 , "true"});
			//���x�t���[���e �o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0175 , "true"});
			//�t���[�C�^�C�g��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0176 , "true"});
			//�t���[�C���e
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0177 , "true"});
			//�t���[�C �o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0178 , "true"});
			//�t���[�D�^�C�g��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0179 , "true"});
			//�t���[�D���e
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0180 , "true"});
			//�t���[�D �o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0181 , "true"});
			//�t���[�E�^�C�g��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0182 , "true"});
			//�t���[�E���e
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0183 , "true"});
			//�t���[�E �o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0184 , "true"});
// ADD end 20121001 QP@20505 No.1
			//--------------------------- ��{���(����\�B) --------------------------------
			//�����O���[�v
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0092 , "false"});
			//�����`�[��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0093 , "false"});
			//�ꊇ�\��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0094 , "true"});
			//�W������
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0095 , "true"});
			//���[�U
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0096 , "true"});
			//��������
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0097 , "true"});
			//�p�r
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0098 , "true"});
			//���i��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0099 , "true"});
			//���
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0100 , "true"});
			//�����w��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0101 , "true"});
			//�S�����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0102 , "true"});
			//�S���H��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0103 , "true"});
			//�S���c��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0104 , "true"});
			//�������@
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0105 , "true"});
			//�[�U���@
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0106 , "true"});
			//�E�ە��@
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0107 , "true"});
			//�e����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0108 , "true"});
			//�e��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0109 , "true"});
			//�e�ʁi�P�ʁj
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0110 , "true"});
			//���萔
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0111 , "true"});
			//�׎p
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0112 , "true"});
			//�戵���x
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0113 , "true"});
			//�ܖ�����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0114 , "true"});
			//������]
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0115 , "true"});
			//������]
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0116 , "true"});
			//�z�蕨��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0117 , "true"});
			//�̔�����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0118 , "true"});
			//�v�攄��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0119 , "true"});
			//�v�旘�v
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0120 , "true"});
			//�̔��㔄��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0121 , "true"});
			//�̔��㗘�v
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0122 , "true"});
			//��������
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0123 , "true"});

			//--------------------------- �������Z(����\�D) --------------------------------
			//���FG
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0149 , "true"});
			//�L������
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0127 , "true"});
			//���Ϗ[�U��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0128 , "true"});
			//�o��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0129 , "true"});
			//����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0130 , "true"});
			//���Z�m��T���v��No
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0131, "true"});
			//���Z�����Q��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0132, "true"});
			//�������Z�\���
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0133, "true"});
			//�[�U�ʐ���
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0134 , "true"});
			//�[�U�ʖ���
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0135 , "true"});
			//���v��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0136 , "true"});
			//������(kg)
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0137 , "true"});
			//������(�P�{��)
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0138 , "true"});
			//��d
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0139 , "true"});
			//�e��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0140 , "true"});
			//����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0141 , "true"});
			//���x����
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0142 , "true"});
			//��d���Z��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0143 , "true"});
			//�ޗ���
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0145 , "true"});
			//�����v/cs
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0146 , "true"});
			//�����v/��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0147 , "true"});
			//�e��
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0148 , "true"});
			//�������Z�o�^
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0150 , "true"});
			//�������Z�˗�Fg
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0151 , "true"});

/*******************************************************************************************
 *
 * �V�K���[�h
 *
 *******************************************************************************************/
			//����CD-���[�U
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0001 , "false"});
			//����CD-�N
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0002 , "false"});
			//����CD-�ǔ�
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0003 , "false"});
			//�˗��ԍ�
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0004 , "true"});
			//�i��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0005 , "true"});
			//���@No-���[�U
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0006 , "false"});
			//���@No-���CD
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0007 , "false"});
			//���@No-���No
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0008 , "false"});
			//���@No-�ǔ�
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0009 , "false"});
			//�p�~
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0010 , "true"});
			//�o�^
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0011 , "true"});
			//�I��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0012 , "true"});
			//�����R�s�[
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0013 , "false"});
			//�S�R�s�[
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0014 , "false"});
			//��ʔԍ�
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0015 , "true"});

			//��߂�
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0152 , "true"});

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0153 , "true"});
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
			
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
			//�V�[�N���b�g
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0190 , "true"});
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

			//--------------------------- �z���\(����\�@) --------------------------------
			//����I��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0016 , "true"});
			//���ӎ���No
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0017 , "true"});
			//���t
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0018 , "true"});
			//�����No
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0019 , "true"});
			//����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0020 , "true"});
			//���Fg
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0021 , "true"});
			//�H���I��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0022 , "true"});
			//�����I��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0023 , "true"});
			//�H������
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0024 , "true"});
			//����CD
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0025 , "true"});
			//�H����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0026 , "true"});
			//������
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0027 , "true"});
			//�P��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0028 , "true"});
			//����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0029 , "true"});
			//���ܗL��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0030 , "true"});
			//��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0031 , "true"});
			//�����H��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0032 , "true"});
			//�����ꗗ
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0033 , "true"});
			//��������
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0034 , "true"});
			//�H���}��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0035 , "true"});
			//�H���ړ��i���j
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0036 , "true"});
			//�H���ړ��i���j
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0037 , "true"});
			//�H���폜
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0038 , "true"});
			//�����}��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0039 , "true"});
			//�����ړ��i���j
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0040 , "true"});
			//�����ړ��i���j
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0041 , "true"});
			//�����폜
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0042 , "true"});
			//�����ǉ�
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0043 , "true"});
			//�����폜
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0044 , "true"});
			//�����R�s�[
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0045 , "true"});
			//�����ړ��i���j
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0046 , "true"});
			//�����ړ��i���j
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0047 , "true"});
			//����\�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0048 , "true"});
			//����ِ������o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0049 , "true"});
			//�d��d��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0124 , "true"});
			//���샊�X�g�R�s�[
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0125 , "true"});
			//�h�{�v�Z���o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0126 , "true"});

			//--------------------------- �����H�� --------------------------------
			//�����H���I��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0050 , "true"});
			//���e����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0051 , "true"});
			//��ɕ\��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0052 , "true"});
			//�V�K
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0053 , "true"});
			//�X�V
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0054 , "true"});

			//--------------------------- �����l(����\�A) --------------------------------
			//�����v�Z
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0055 , "true"});
			//���_�E�H���o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0056 , "true"});
			//�������o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0057 , "true"});
			//���x�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0058 , "true"});
			//�S�x�E���x�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0059 , "true"});
			//PH�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0060 , "true"});
			//���_�E�H���o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0061 , "true"});
			//��d�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0062 , "true"});
			//���������o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0063 , "true"});
			//�A���R�[���o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0064 , "true"});
			//�t���[�^�C�g��1�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0065 , "true"});
			//�t���[�^�C�g��2�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0066 , "true"});
			//�t���[�^�C�g��3�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0067 , "true"});
			//�ꊇ�`�F�b�N
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0068 , "true"});
			//�t���[�^�C�g��1
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0069 , "true"});
			//�t���[�^�C�g��2
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0070 , "true"});
			//�t���[�^�C�g��3
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0071 , "true"});
			//���_
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0072 , "false"});
			//�H��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0073 , "false"});
			//�������_�x
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0074 , "false"});
			//�������H��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0075 , "false"});
			//�������|�_
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0076 , "false"});
			//���x
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0077 , "true"});
			//�S�x
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0078 , "true"});
			//���x
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0079 , "true"});
			//PH
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0080 , "true"});
			//���_����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0081 , "true"});
			//�H������
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0082 , "true"});
			//��d
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0083 , "true"});
			//��������
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0084 , "true"});
			//�A���R�[��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0085 , "true"});
			//�t���[���e1
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0086 , "true"});
			//�t���[���e2
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0087 , "true"});
			//�t���[���e3
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0088 , "true"});
			//�쐬����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0089 , "true"});
			//�]��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0090 , "true"});
			//���͒l�ύX�m�F
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0091 , "true"});
// ADD start 20121016 QP@20505 No.11
			//�������̓}�X�^�l�擾
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0155 , "true"});
// ADD end 20121016 QP@20505 No.11
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			//������d
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0154 , "true"});
// ADD start 20121001 QP@20505 No.1
			//���������t���[�^�C�g��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0160 , "true"});
			//���������t���[���e
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0161 , "true"});
			//���������t���[�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0162 , "true"});
			//�A���R�[���t���[�^�C�g��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0163 , "true"});
			//�A���R�[���t���[���e
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0164 , "true"});
			//�A���R�[���t���[�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0165 , "true"});
			//�����|�_�Z�x
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0166 , "false"}); // false
			//�����|�_�Z�x �o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0167 , "true"});
			//�������l�r�f
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0168 , "false"}); // false
			//�������l�r�f �o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0169 , "true"});
			//�S�x�t���[�^�C�g��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0170 , "true"});
			//�S�x�t���[���e
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0171 , "true"});
			//�S�x�t���[���e �o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0172 , "true"});
			//���x�t���[�^�C�g��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0173 , "true"});
			//���x�t���[���e
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0174 , "true"});
			//���x�t���[���e �o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0175 , "true"});
			//�t���[�C�^�C�g��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0176 , "true"});
			//�t���[�C���e
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0177 , "true"});
			//�t���[�C �o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0178 , "true"});
			//�t���[�D�^�C�g��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0179 , "true"});
			//�t���[�D���e
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0180 , "true"});
			//�t���[�D �o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0181 , "true"});
			//�t���[�E�^�C�g��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0182 , "true"});
			//�t���[�E���e
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0183 , "true"});
			//�t���[�E �o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0184 , "true"});
// ADD end 20121001 QP@20505 No.1
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end

			//--------------------------- ��{���(����\�B) --------------------------------
			//�����O���[�v
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0092 , "false"});
			//�����`�[��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0093 , "false"});
			//�ꊇ�\��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0094 , "true"});
			//�W������
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0095 , "true"});
			//���[�U
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0096 , "true"});
			//��������
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0097 , "true"});
			//�p�r
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0098 , "true"});
			//���i��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0099 , "true"});
			//���
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0100 , "true"});
			//�����w��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0101 , "true"});
			//�S�����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0102 , "true"});
			//�S���H��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0103 , "true"});
			//�S���c��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0104 , "true"});
			//�������@
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0105 , "true"});
			//�[�U���@
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0106 , "true"});
			//�E�ە��@
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0107 , "true"});
			//�e����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0108 , "true"});
			//�e��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0109 , "true"});
			//�e�ʁi�P�ʁj
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0110 , "true"});
			//���萔
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0111 , "true"});
			//�׎p
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0112 , "true"});
			//�戵���x
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0113 , "true"});
			//�ܖ�����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0114 , "true"});
			//������]
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0115 , "true"});
			//������]
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0116 , "true"});
			//�z�蕨��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0117 , "true"});
			//�̔�����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0118 , "true"});
			//�v�攄��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0119 , "true"});
			//�v�旘�v
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0120 , "true"});
			//�̔��㔄��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0121 , "true"});
			//�̔��㗘�v
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0122 , "true"});
			//��������
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0123 , "true"});

			//--------------------------- �������Z(����\�D) --------------------------------
			//���FG
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0149 , "true"});
			//�L������
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0127 , "true"});
			//���Ϗ[�U��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0128 , "true"});
			//�o��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0129 , "true"});
			//����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0130 , "true"});
			//���Z�m��T���v��No
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0131, "false"});
			//���Z�����Q��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0132, "false"});
			//�������Z�\���
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0133, "true"});
			//�[�U�ʐ���
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0134 , "true"});
			//�[�U�ʖ���
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0135 , "true"});
			//���v��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0136 , "true"});
			//������(kg)
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0137 , "true"});
			//������(�P�{��)
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0138 , "true"});
			//��d
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0139 , "true"});
			//�e��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0140 , "true"});
			//����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0141 , "true"});
			//���x����
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0142 , "true"});
			//��d���Z��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0143 , "true"});
			//�ޗ���
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0145 , "true"});
			//�����v/cs
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0146 , "true"});
			//�����v/��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0147 , "true"});
			//�e��
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0148 , "true"});
			//�������Z�o�^
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0150 , "false"});
			//�������Z�˗�Fg
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0151 , "true"});

/*******************************************************************************************
 *
 * ���@�R�s�[���[�h
 *
 *******************************************************************************************/
			//����CD-���[�U
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0001 , "false"});
			//����CD-�N
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0002 , "false"});
			//����CD-�ǔ�
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0003 , "false"});
			//�˗��ԍ�
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0004 , "false"});
			//�i��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0005 , "false"});
			//���@No-���[�U
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0006 , "false"});
			//���@No-���CD
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0007 , "false"});
			//���@No-���No
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0008 , "false"});
			//���@No-�ǔ�
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0009 , "false"});
			//�p�~
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0010 , "false"});
			//�o�^
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0011 , "true"});
			//�I��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0012 , "true"});
			//�����R�s�[
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0013 , "false"});
			//�S�R�s�[
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0014 , "false"});
			//��ʔԍ�
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0015 , "true"});

			//��߂�
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0152 , "true"});

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0153 , "false"});
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
			
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
			//�V�[�N���b�g
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0190 , "true"});
			//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End

			//--------------------------- �z���\(����\�@) --------------------------------
			//����I��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0016 , "true"});
			//���ӎ���No
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0017 , "false"});
			//���t
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0018 , "false"});
			//�����No
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0019 , "false"});
			//����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0020 , "false"});
			//���Fg
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0021 , "false"});
			//�H���I��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0022 , "false"});
			//�����I��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0023 , "false"});
			//�H������
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0024 , "false"});
			//����CD
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0025 , "true"});
			//�H����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0026 , "false"});
			//������
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0027 , "true"});
			//�P��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0028 , "true"});
			//����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0029 , "true"});
			//���ܗL��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0030 , "true"});
			//��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0031 , "false"});
			//�����H��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0032 , "true"});
			//�����ꗗ
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0033 , "true"});
			//��������
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0034 , "true"});
			//�H���}��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0035 , "false"});
			//�H���ړ��i���j
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0036 , "false"});
			//�H���ړ��i���j
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0037 , "false"});
			//�H���폜
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0038 , "false"});
			//�����}��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0039 , "false"});
			//�����ړ��i���j
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0040 , "false"});
			//�����ړ��i���j
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0041 , "false"});
			//�����폜
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0042 , "false"});
			//�����ǉ�
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0043 , "false"});
			//�����폜
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0044 , "false"});
			//�����R�s�[
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0045 , "false"});
			//�����ړ��i���j
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0046 , "false"});
			//�����ړ��i���j
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0047 , "false"});
			//����\�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0048 , "false"});
			//����ِ������o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0049 , "false"});
			//�d��d��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0124 , "false"});
			//���샊�X�g�R�s�[
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0125 , "false"});
			//�h�{�v�Z���o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0126 , "false"});

			//--------------------------- �����H�� --------------------------------
			//�����H���I��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0050 , "true"});
			//���e����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0051 , "false"});
			//��ɕ\��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0052 , "false"});
			//�V�K
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0053 , "false"});
			//�X�V
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0054 , "false"});

			//--------------------------- �����l(����\�A) --------------------------------
			//�����v�Z
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0055 , "false"});
			//���_�E�H���o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0056 , "false"});
			//�������o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0057 , "false"});
			//���x�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0058 , "false"});
			//�S�x�E���x�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0059 , "false"});
			//PH�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0060 , "false"});
			//���_�E�H���o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0061 , "false"});
			//��d�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0062 , "false"});
			//���������o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0063 , "false"});
			//�A���R�[���o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0064 , "false"});
			//�t���[�^�C�g��1�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0065 , "false"});
			//�t���[�^�C�g��2�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0066 , "false"});
			//�t���[�^�C�g��3�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0067 , "false"});
			//�ꊇ�`�F�b�N
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0068 , "false"});
			//�t���[�^�C�g��1
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0069 , "false"});
			//�t���[�^�C�g��2
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0070 , "false"});
			//�t���[�^�C�g��3
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0071 , "false"});
			//���_
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0072 , "false"});
			//�H��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0073 , "false"});
			//�������_�x
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0074 , "false"});
			//�������H��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0075 , "false"});
			//�������|�_
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0076 , "false"});
			//���x
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0077 , "false"});
			//�S�x
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0078 , "false"});
			//���x
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0079 , "false"});
			//PH
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0080 , "false"});
			//���_����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0081 , "false"});
			//�H������
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0082 , "false"});
			//��d
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0083 , "false"});
			//��������
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0084 , "false"});
			//�A���R�[��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0085 , "false"});
			//�t���[���e1
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0086 , "false"});
			//�t���[���e2
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0087 , "false"});
			//�t���[���e3
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0088 , "false"});
			//�쐬����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0089 , "false"});
			//�]��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0090 , "false"});
			//���͒l�ύX�m�F
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0091 , "true"});
// ADD start 20121016 QP@20505 No.11
			//�������̓}�X�^�l�擾
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0155 , "true"});
// ADD end 20121016 QP@20505 No.11
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			//������d
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0154 , "false"});
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
// ADD start 20121001 QP@20505 No.1
			//���������t���[�^�C�g��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0160 , "false"});
			//���������t���[���e
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0161 , "false"});
			//���������t���[�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0162 , "false"});
			//�A���R�[���t���[�^�C�g��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0163 , "false"});
			//�A���R�[���t���[���e
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0164 , "false"});
			//�A���R�[���t���[�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0165 , "false"});
			//�����|�_�Z�x
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0166 , "false"}); // false
			//�����|�_�Z�x �o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0167 , "false"});
			//�������l�r�f
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0168 , "false"}); // false
			//�������l�r�f �o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0169 , "false"});
			//�S�x�t���[�^�C�g��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0170 , "false"});
			//�S�x�t���[���e
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0171 , "false"});
			//�S�x�t���[���e �o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0172 , "false"});
			//���x�t���[�^�C�g��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0173 , "false"});
			//���x�t���[���e
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0174 , "false"});
			//���x�t���[���e �o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0175 , "false"});
			//�t���[�C�^�C�g��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0176 , "false"});
			//�t���[�C���e
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0177 , "false"});
			//�t���[�C �o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0178 , "false"});
			//�t���[�D�^�C�g��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0179 , "false"});
			//�t���[�D���e
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0180 , "false"});
			//�t���[�D �o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0181 , "false"});
			//�t���[�E�^�C�g��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0182 , "false"});
			//�t���[�E���e
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0183 , "false"});
			//�t���[�E �o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0184 , "false"});
// ADD end 20121001 QP@20505 No.1

			//--------------------------- ��{���(����\�B) --------------------------------
			//�����O���[�v
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0092 , "false"});
			//�����`�[��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0093 , "false"});
			//�ꊇ�\��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0094 , "false"});
			//�W������
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0095 , "false"});
			//���[�U
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0096 , "false"});
			//��������
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0097 , "false"});
			//�p�r
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0098 , "false"});
			//���i��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0099 , "false"});
			//���
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0100 , "false"});
			//�����w��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0101 , "false"});
			//�S�����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0102 , "false"});
			//�S���H��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0103 , "false"});
			//�S���c��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0104 , "false"});
			//�������@
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0105 , "false"});
			//�[�U���@
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0106 , "false"});
			//�E�ە��@
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0107 , "false"});
			//�e����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0108 , "false"});
			//�e��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0109 , "false"});
			//�e�ʁi�P�ʁj
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0110 , "false"});
			//���萔
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0111 , "false"});
			//�׎p
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0112 , "false"});
			//�戵���x
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0113 , "false"});
			//�ܖ�����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0114 , "false"});
			//������]
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0115 , "false"});
			//������]
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0116 , "false"});
			//�z�蕨��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0117 , "false"});
			//�̔�����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0118 , "false"});
			//�v�攄��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0119 , "false"});
			//�v�旘�v
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0120 , "false"});
			//�̔��㔄��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0121 , "false"});
			//�̔��㗘�v
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0122 , "false"});
			//��������
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0123 , "false"});

			//--------------------------- �������Z(����\�D) --------------------------------
			//���FG
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0149 , "false"});
			//�L������
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0127 , "false"});
			//���Ϗ[�U��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0128 , "false"});
			//�o��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0129 , "false"});
			//����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0130 , "false"});
			//���Z�m��T���v��No
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0131, "false"});
			//���Z�����Q��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0132, "false"});
			//�������Z�\���
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0133, "false"});
			//�[�U�ʐ���
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0134 , "false"});
			//�[�U�ʖ���
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0135 , "false"});
			//���v��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0136 , "false"});
			//������(kg)
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0137 , "false"});
			//������(�P�{��)
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0138 , "false"});
			//��d
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0139 , "false"});
			//�e��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0140 , "false"});
			//����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0141 , "false"});
			//���x����
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0142 , "false"});
			//��d���Z��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0143 , "false"});
			//�ޗ���
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0145 , "false"});
			//�����v/cs
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0146 , "false"});
			//�����v/��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0147 , "false"});
			//�e��
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0148 , "false"});
			//�������Z�o�^
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0150 , "false"});
			//�������Z�˗�Fg
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0151 , "false"});





		}catch(Exception e){
			e.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���[�h�ҏW�Ɏ��s���܂����B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}

	}

	/*****************************************************************************************
	 *
	 * ���[�h�ҏW�`�F�b�N
	 * @param  strCtrlNm  : �R���g���[����
	 * @param  strMode   : ���[�hID
	 * @return  boolean    : �ҏW��
	 *
	 *****************************************************************************************/
	public boolean checkModeCtrl(String strCtrlNm , String strMode) throws ExceptionBase{
		//�ԋp�l
		boolean ret = false;
		//�m�F�p�z��
		ArrayList chkArray = new ArrayList();

		try{
			//�Q�ƃ��[�h�̏ꍇ
			if(strMode.equals(JwsConstManager.JWS_MODE_0000)){
				chkArray = aryModeCheck_Sansho;
			}
			//�ڍ׃��[�h�̏ꍇ
			else if(strMode.equals(JwsConstManager.JWS_MODE_0001)){
				chkArray = aryModeCheck_Shosai;
			}
			//�V�K���[�h�̏ꍇ
			else if(strMode.equals(JwsConstManager.JWS_MODE_0002)){
				chkArray = aryModeCheck_Sinki;
			}
			//���@�R�s�[���[�h�̏ꍇ
			else if(strMode.equals(JwsConstManager.JWS_MODE_0003)){
				chkArray = aryModeCheck_Copy;
			}

//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@start
			//����R�s�[���[�h�ҏW�p�^�[��
			else if(strMode.equals(JwsConstManager.JWS_MODE_0004)){
				chkArray = aryModeCheck_ShisakuCopy;
			}
//2011/05/19�@�yQP@10181_No.11�z����R�s�[���[�h�����@TT.NISHIGAWA�@end


			//�g�p�ۃ`�F�b�N
			for(int i=0; i<chkArray.size(); i++){
				//�ҏW�p�^�[���擾
				String[] selModePtn = (String[])chkArray.get(i);
				//�����R���g���[���̏ꍇ
				if(selModePtn[0].equals(strCtrlNm)){
					//�ҏW�ێw��
					if(selModePtn[1].equals("true")){
						ret = true;
					}else{
						ret = false;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���[�h�ҏW�`�F�b�N�Ɏ��s���܂����B");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}

		//�ԋp
		return ret;
	}

}
