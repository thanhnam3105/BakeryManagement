//========================================================================================
// ���[�����M���� [MailControl.js]
// �쐬�ҁFE.Kitazawa
// �쐬���F2015/03/03
// �T�v  �F���[�����M�ɑ΂��Ă̑�����s���B�yQP@40812�z
//========================================================================================

//���[����^��
// 2015/08/26 MOD start
// �����́u���얼�v�ɕύX
var MailSubject = "�y�������Z�X�e�[�^�X�X�V�̂��m�点�z";
//var MailBody1 = "�ȉ��̎���̃X�e�[�^�X���X�V���܂����̂ł��m�点���܂��B\n";
var MailBody1 = "�\��̌��A�������Z�V�X�e���̃X�e�[�^�X���X�V�v���܂����B\n";
//var MailBody2 = "\n�ȏ�A��낵�����肢�������܂��B\n";
var MailBody2 = "\n�ȏ�A�X�������肢�v���܂��B\n";
// 2015/08/26 MOD end


//========================================================================================
// �A�����[�����M�i�������Z�X�e�[�^�X�X�V�j
// �쐬�ҁFE.Kitazawa
// �쐬���F2015/03/03
// ����  �F�@userObj   �F���[�U���
//       �F�AarySisan  �F������ �i����No., ���얼, ���Z�����A�T���v��No.�j
//       �F�BarySt     �F���݂̃X�e�[�^�X��� �i������, ����, ����, �H��, �c�Ɓj
// �߂�l�F�Ȃ�
// �T�v  �F�m�F���b�Z�[�W��\������B
//========================================================================================
function funMailGenkaSisan(userObj, arySisan, arySt) {

	var mailbody = "";

	//����No
	// 2015/08/26 MOD start
//	mailbody += "\n�@�E����No�@�@�F�@";
	mailbody += "\n�����쇂�@�@�@�F�@";
	mailbody +=  arySisan[0];
	//�i��
//	mailbody += "\n�@�E�i���@�@�@�@�F�@";
	mailbody += "\n�����얼�@�@�@�F�@";
	mailbody += arySisan[1];
	//�T���v��No.
	mailbody += "\n���T���v�����F�@";
	mailbody +=  arySisan[3];

	//���Z����
//	mailbody += "\n�@�E���Z�����@�F�@";
	mailbody += "\n�����Z�����@ �F�@";
	mailbody +=  arySisan[2];

	//���݂̃X�e�[�^�X
/* ---------- �s�v -----------
*	mailbody += "\n\n�@�E���݂̃X�e�[�^�X";
*	mailbody += "\n�@�@�@�������F" ;
*	mailbody += arySt[0];
*	mailbody += "�@���ǁF" ;
*	mailbody += arySt[1];
*	mailbody += "�@�����F" ;
*	mailbody += arySt[2];
*	mailbody += "�@�H��F" ;
*	mailbody += arySt[3];
*	mailbody += "�@�c�ƁF" ;
*	mailbody += arySt[4];
--------------------- */
	mailbody += "\n\n";

	//��^����ǉ�
	mailbody = MailBody1 + mailbody + MailBody2;

/* ---------- �s�v -----------
	//��ƒS����
*	mailbody += "\n====================================";
*	mailbody += "\n�@�@�@������ЁF";
*	mailbody += funXmlRead(userObj, "nm_kaisha", 0);
*	mailbody += "\n�@�@�@���������F";
*	mailbody += funXmlRead(userObj, "nm_busho", 0);
*	mailbody += "\n�@�@�@�S���ҁ@�F";
*	mailbody += funXmlRead(userObj, "nm_user", 0);
*	mailbody += "\n====================================";
--------------------- */

//	mail_url = encodeURI ('mailto:'  + '?subject=' + MailSubject + '&body=' + mailbody);
	mail_url = encodeURI ('mailto:'  + '?subject=' + arySisan[1] + '&body=' + mailbody);
	// 2015/08/26 MOD start

	//���[�����M��ʂ��N��
	location.href = mail_url;

	return true;

}
