//========================================================================================
// XML�ʐM�������� [AjaxControl.js]
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// �T�v  �FXML�ʐM�̑����I�Ȑ�����s���B
//========================================================================================

//========================================================================================
// XML�ʐM�������쏈��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@XmlId     �FXMLID
//       �F�AFuncIdList�F�@�\ID(�z��)
//       �F�BxmlSend   �F���MXML
//       �F�CxmlReqList�F�@�\ID�ʑ��MXML(�z��)
//       �F�DxmlResList�F�@�\ID�ʎ�MXML(�z��)
//       �F�EMode      �F�������[�h
//           1:�Z�b�V�����L��A2:�Z�b�V�����Ȃ��A3:���[�U���擾�̂�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �FXML�ʐM�̑����I�Ȑ�����s���B
//========================================================================================
function funAjaxConnection(XmlId, FuncIdList, xmlSend, xmlReqList, xmlResList, Mode) {

    var i;

    //XMĻ�ق̓���
    for (i = 0; i < xmlReqList.length; i++) {
        funXmlMerge(xmlSend, xmlReqList[i], XmlId);
    }

    //XML�ʐM����
    if (funXmlConnect(xmlSend) == false) {
        xmlSend.src = "";
        //������ү���ޔ�\��
        funClearRunMessage();
        return false;
    }

    //XMĻ�ق̕���
    for (i = 0; i < xmlResList.length; i++) {
        funXmlDivision(xmlSend, xmlResList[i], FuncIdList[i]);
    }

    //���ʔ���
    if (funXmlResultCheck(xmlResList) == false) {
        xmlSend.src = "";
        return false;
    }

    //����ݗLӰ�ނ̏ꍇ
    if (Mode == "1") {
        //�������ѱ�Ċm�F
        for (i = 0; i < xmlResList.length; i++) {
            if (funSessionTimeoutCheck(xmlResList[i]) == false) {
                xmlSend.src = "";
                return false;
            }
        }
    }

    return true;

}
