//========================================================================================
// �`�F�b�N���� [CheckControl.js]
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// �T�v  �F�T�[�o�������ʂ̃`�F�b�N���s���B
//========================================================================================

var DebugLevel;    //���ޯ������

//========================================================================================
// ���ʔ��菈��(����)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlResult   �F��������XML��
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F�T�[�o�ŏ������ꂽ���ʂ̔�����s���B
//========================================================================================
function funXmlResultCheck(xmlResult) {

    var ErrMsg;         //���b�Z�[�W
    var ClsName;        //��������
    var ErrCd;          //�G���[�R�[�h
    var SysMsg;         //�V�X�e�����b�Z�[�W
    var MsgNo;          //���b�Z�[�W�ԍ�
    var OutputMsg;      //�o�̓��b�Z�[�W
    var i;
    var resultflg;
    var CurrentFolder;

    resultflg = false;

    for (i = 0; i < xmlResult.length; i++) {
        if (xmlResult[i].xml == "") {
            continue;
        }

        //ResultXML�̏ꍇ
        if (xmlResult[i].documentElement.nodeName == ConResult) {
            //���ޯ�����ق̑ޔ�
            DebugLevel = funXmlRead(xmlResult[i], "debuglevel", 0);
            resultflg = true;

            //�������ʂ̔���
            if (funXmlRead(xmlResult[i], "flg_return", 0) == "false") {
                //�ُ펞�ʹװү���ނ�\��
                ErrMsg = funXmlRead(xmlResult[i], "msg_error", 0);
                ClsName = funXmlRead(xmlResult[i], "nm_class", 0);
                MsgNo = funXmlRead(xmlResult[i], "no_errmsg", 0);
                ErrCd = funXmlRead(xmlResult[i], "cd_error", 0);
                SysMsg = funXmlRead(xmlResult[i], "msg_system", 0);

                //�o��ү���ނ̐���
                if (DebugLevel == 0) {
                    OutputMsg = ErrMsg;
                } else {
                    OutputMsg = ErrMsg;
                    OutputMsg += "<br><br>ү���ޔԍ��F" + MsgNo;
                    OutputMsg += "<br>�����ӏ��F" + ClsName;
                    OutputMsg += "<br>���Ѵװ���ށF" + ErrCd;
                    OutputMsg += "<br>���Ѵװү���ށF" + SysMsg;
                }

                //�������Z��ʃZ�b�V�����`�F�b�N
                //����ݐ؂�̏ꍇ
                if (MsgNo == ConSessionErrCd) {
                    var CurrentFolder_genka = location.pathname.split("/")[3];
                    //�������Z���
                    if( CurrentFolder_genka == "SQ110GenkaShisan" ){
                        //��ʂ��I��
                        funErrorMsgBox(E000011);
                        parent.close();
                        return false;
                    }
                    //�ގ��i�������
                    else if( CurrentFolder_genka == "RuiziSearch.jsp" ){
                        //��ʂ��I��
                        funErrorMsgBox(E000011);
                        window.close();
                        window.dialogArguments.parent.close();
                        return false;
                    // ADD 2015/03/03 TT.Kitazawa�yQP@40812�zstart
                    //  "SQ160GenkaShisan_Eigyo" �� OutputMsg���ݒ肳��Ă��Ȃ���
                    } else if( OutputMsg == "" ) {
                        //��ʂ��I��
                        funErrorMsgBox(E000011);
                        parent.close();
                        return false;
                    // ADD 2015/03/03 TT.Kitazawa�yQP@40812�zend
                    }
                }

                //�װү����
                funErrorMsgBox(OutputMsg);
                funClearRunMessage();

                //����ݐ؂�̏ꍇ
                if (MsgNo == ConSessionErrCd) {
                    CurrentFolder = location.pathname.split("/")[2];

                    if (CurrentFolder != "SQ051GenryoInput" && CurrentFolder != "SQ081TantoSearch" &&
                        CurrentFolder != "SQ082KasihaAdd" && CurrentFolder != "SQ091KengenAdd") {
                        //۸޲݉�ʂ֑J��
                        funUrlConnect("../SQ010Login/SQ010Login.jsp", ConConectGet, "");
                    }
                }

                //���ʔ���(��)
                if (funResultCheck(xmlResult) == false) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    //ResultXML�����݂��Ȃ��ꍇ
    if (resultflg == false) {
        //���ʔ�����ُ�ŕԂ�
        funErrorMsgBox(E000001);
        return false;
    }

    return true;

}

//========================================================================================
// ���ʔ��菈��(��)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlResult   �F��������XML��
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F�T�[�o�ŏ������ꂽ���ʂ̔�����s���B
//========================================================================================
function funResultCheck(xmlResult) {

    var ErrMsg;         //���b�Z�[�W
    var ClsName;        //��������
    var ErrCd;          //�G���[�R�[�h
    var SysMsg;         //�V�X�e�����b�Z�[�W
    var MsgNo;          //���b�Z�[�W�ԍ�
    var OutputMsg;      //�o�̓��b�Z�[�W
    var i;

    for (i = 0; i < xmlResult.length; i++) {
        if (xmlResult[i].xml == "") {
            return false;
        }

        //UserInfoXML����ResultXML�ȊO�̏ꍇ
        if (xmlResult[i].documentElement.nodeName != ConResult && xmlResult[i].documentElement.nodeName != ConUserInfo) {
            //�������ʂ̔���
            if (funXmlRead(xmlResult[i], "flg_return", 0) == "false") {
/*                //�ُ펞�ʹװү���ނ�\��
                ErrMsg = funXmlRead(xmlResult, "msg_error", 0);
                ClsName = funXmlRead(xmlResult, "nm_class", 0);
                MsgNo = funXmlRead(xmlResult, "no_errmsg", 0);
                ErrCd = funXmlRead(xmlResult, "cd_error", 0);
                SysMsg = funXmlRead(xmlResult, "msg_system", 0);

                //�o��ү���ނ̐���
                if (DebugLevel == 0) {
                    OutputMsg = ErrMsg;
                } else {
                    OutputMsg = ErrMsg;
                    OutputMsg += "<br><br>ү���ޔԍ��F" + MsgNo;
                    OutputMsg += "<br>�����ӏ��F" + ClsName;
                    OutputMsg += "<br>���Ѵװ���ށF" + ErrCd;
                    OutputMsg += "<br>���Ѵװү���ށF" + SysMsg;
                }

                //�װү����
                funErrorMsgBox(OutputMsg);
                funClearRunMessage();*/

                return false;
            }
        }
    }

    return true;

}

//========================================================================================
// �Z�b�V�����^�C���A�E�g�m�F����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlUser      �F���[�U���i�[XML��
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �F�T�[�o�ŕێ�����Ă���Z�b�V�������̃^�C���A�E�g�m�F���s���B
//========================================================================================
function funSessionTimeoutCheck(xmlUser) {

    var ErrMsg;         //���b�Z�[�W
    var ClsName;        //��������
    var ErrCd;          //�G���[�R�[�h
    var SysMsg;         //�V�X�e�����b�Z�[�W
    var MsgNo;          //���b�Z�[�W�ԍ�
    var OutputMsg;      //�o�̓��b�Z�[�W

    if (xmlUser.xml == "") {
        return true;
    }

    if (xmlUser.documentElement.nodeName == ConUserInfo) {
        //�������ʂ̔���
        if (funXmlRead(xmlUser, "flg_return", 0) == "false") {
            //�ُ펞�ʹװү���ނ�\��
            ErrMsg = funXmlRead(xmlUser, "msg_error", 0);
            ClsName = funXmlRead(xmlUser, "nm_class", 0);
            MsgNo = funXmlRead(xmlUser, "no_errmsg", 0);
            ErrCd = funXmlRead(xmlUser, "cd_error", 0);
            SysMsg = funXmlRead(xmlUser, "msg_system", 0);

            //�ُ펞�ʹװ��ʂ�\��
//            location.href = "../common/SessionTimeout.html";
            //�o��ү���ނ̐���
            if (DebugLevel == 0) {
                OutputMsg = ErrMsg;
            } else {
                OutputMsg = ErrMsg;
                OutputMsg += "<br><br>ү���ޔԍ��F" + MsgNo;
                OutputMsg += "<br>�����ӏ��F" + ClsName;
                OutputMsg += "<br>���Ѵװ���ށF" + ErrCd;
                OutputMsg += "<br>���Ѵװү���ށF" + SysMsg;
            }

            //�ُ펞�ʹװү���ނ�\��
            funErrorMsgBox(OutputMsg);
            funClearRunMessage();

            return false;
        }
    }

    return true;

}
