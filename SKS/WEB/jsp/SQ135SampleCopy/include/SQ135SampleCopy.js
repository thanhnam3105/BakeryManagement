//========================================================================================
// �����\������
// �쐬�ҁFE.Kitazawa
// �쐬���F2016/06/22
// �T�v  �F��ʂ̏����\���������s��
//========================================================================================
function funLoad() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var cpyMoto = new Array();		//�R�s�[���T���v����
	var cpySaki = new Array();		//�R�s�[��T���v����
	var arrSample;					//[0]�T���v�����A[1]�R�s�[��flg

	//�T���v����
	var cntSample = window.dialogArguments.length;

    for(var i = 0; i < cntSample; i++ ){
    	//[0]�T���v�����A[1]�R�s�[��flg
    	arrSample = window.dialogArguments[i].split(ConDelimiter);

//20160826 KPX@1502111_No.10 DEL Start
//    	//�R�s�[���T���v���ɒǉ�
//    	cpyMoto.push(arrSample[0]);
//20160826 KPX@1502111_No.10 DEL End
    	
    	//�R�s�[��ւ̒ǉ��t���O
    	if (arrSample[1] == "0") {
    		//���Z���~�łȂ����ڌŒ�`�F�b�NOFF�̏ꍇ
    		cpySaki.push(arrSample[0]);
    	} else {
    		//���Z���~�A���͍��ڌŒ�`�F�b�NON�̏ꍇ
    		// �󕶎���ǉ��i��ԍ������炳�Ȃ��ׁj
    		cpySaki.push("");
    	}
    	
//20160826 KPX@1502111_No.10 ADD Start
    	// �R�s�[���ւ̒ǉ��t���O
    	if (arrSample[2] == "0") {
    		//���ڂɒl�̓��͂�����ꍇ
    		cpyMoto.push(arrSample[0]);
    	} else {
    		//���ڂɒl�̓��͂������ꍇ
    		cpyMoto.push("");
    	}
//20160826 KPX@1502111_No.10 ADD End
    }

    //�R�s�[���T���v���R���{�u�b�N�X�̐ݒ�
    funCreateComboBox(frm.selCopyMoto, cpyMoto, 1);
    //�R�s�[��T���v���R���{�u�b�N�X�̐ݒ�
    funCreateComboBox(frm.selCopySaki, cpySaki, 1);

    return true;

}


//========================================================================================
// �R���{�{�b�N�X�쐬����
// �쐬�ҁFE.Kitazawa
// �쐬���F2016/06/22
// ����  �F�@obj      �F�R���{�{�b�N�X�I�u�W�F�N�g
//       �F�AarrData  �F�ݒ�z��
//       �F�BkaraFg   �F�󔒑I���̐ݒ�i0�F�󔒖����A1�F�󔒗L��j
// �T�v  �F�R���{�{�b�N�X���쐬����
//========================================================================================
function funCreateComboBox(obj, arrData, karaFg) {

    var frm = document.frm00;    //̫�тւ̎Q��
    var reccnt;
    var objNewOption;

    //�����ޯ���̸ر
    funClearSelect(obj, 2);

    //�����擾
    reccnt = arrData.length;
    //�ް������݂��Ȃ��ꍇ
    if (reccnt == 0) {
        //�����𒆒f
        return true;
    }

    //�R���{�{�b�N�X�̐ݒ�
    for (var i = 0; i < reccnt; i++) {
    	//�T���v��������̏ꍇ�ǉ����Ȃ�
    	if (arrData[i] != "") {
    		//�T���v�����̒ǉ�
    		objNewOption = document.createElement("option");
    		obj.options.add(objNewOption);
    		objNewOption.innerText = arrData[i];
    		objNewOption.value = i;
    	}
    }

    //�擪�s�̍폜
    if(karaFg == 0){
        obj.options[0] = null;
    }

    //�����ޯ������̫�ĕ\��
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// �T���v���R�s�[�I���F�m��{�^��
// �쐬�ҁFE.Kitazawa
// �쐬���F2016/06/22
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�I������Ԃ�
//========================================================================================
function funCopy(){

	var frm = document.frm00;

	var sel1 = frm.selCopyMoto.options[frm.selCopyMoto.selectedIndex].value;
	var sel2 = frm.selCopySaki.options[frm.selCopySaki.selectedIndex].value;

	if (sel1 == "" || sel2 == "") {
		//�T���v����I�����Ă��Ȃ�
        funErrorMsgBox(E000045);
        return false;
	}

	//�߂�l�̐ݒ�
    var arrRtnVal = new Array(sel1, sel2);
    //�I�������T���v�����Ԃ�
	window.returnValue = arrRtnVal;

	//��ʂ����
    close(self);
}

//========================================================================================
// ��ʏI��
// �쐬�ҁFE.Kitazawa
// �쐬���F2016/06/22
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F��ʂ����B
//========================================================================================
function funClose() {

	//�߂�l�̐ݒ�
	window.returnValue = new Array("false", "");

    //��ʂ����
    close(self);
}

