//========================================================================================
// XML���쏈�� [XmlControl.js]
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// �T�v  �FXML�t�@�C���ɑ΂��Ă̑�����s���B
//========================================================================================

//========================================================================================
// XML����(����)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlMerge   �F������XML
//       �F�AxmlAddition�F�ǉ�XML
//       �F�BXmlId      �FXMLID
// �߂�l�F�Ȃ�
// �T�v  �F������XML�t�@�C�����P��XML�t�@�C���ɓ�������B
//========================================================================================
function funXmlMerge(xmlMerge, xmlAddition, XmlId) {

    var xmlBuff;
    var objNode;
    var xmldatBuff;

    xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    if (xmlMerge.xml == "") {
        //�V����ɰ�ނ��쐬
        objNode = xmlBuff.createNode(1,XmlId,"");
    } else {
        //������ƂȂ�XMĻ�ق̓ǂݍ���
        xmlBuff.load(xmlMerge);
        objNode = xmlBuff.documentElement;
    }

    //�ǉ�����DOM�޷���Ă̺�߰���쐬
    xmldatBuff = xmlAddition.cloneNode(true);


    //�쐬��������ĂɁAxmļ�ق̓��e��ǉ�
    objNode.appendChild(xmldatBuff.documentElement);

    if (xmlBuff.xml == "") {
        xmlBuff.appendChild(objNode);
    }

    xmlMerge.load(xmlBuff);

    return true;

}

//========================================================================================
// XML����(����)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlDivision�F������XML
//       �F�AxmlSave    �F�i�[��XML
//       �F�BFuncId     �F�@�\ID
// �߂�l�F�Ȃ�
// �T�v  �F�P��XML�t�@�C���𕡐���XML�t�@�C���ɕ�������B
//========================================================================================
function funXmlDivision(xmlDivision, xmlSave, FuncId) {

    var xmlBuff;
    var objNode;
    var recCnt;
    var i;

    xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    //�qɰ�ނ̐�����������
    recCnt = xmlDivision.documentElement.childNodes.length;
    if (recCnt == 0) {
        return true;
    }

    //�V����ɰ�ނ��쐬
    objNode = xmlBuff.createNode(1,FuncId,"");

    //�擪�̎qɰ�ނ��擾
    objNode = xmlDivision.documentElement.firstChild;
    if (objNode.nodeName == FuncId) {
        xmlBuff.appendChild(objNode);

    } else {
        for (i = 1; i < recCnt; i++) {
            //���̎qɰ�ނ��擾
            objNode = objNode.nextSibling;
            if (objNode.nodeName == FuncId) {
                xmlBuff.appendChild(objNode);
                break;
            }
        }
    }


    xmlSave.load(xmlBuff);

    return true;

}

//========================================================================================
// XML�ǂݏo������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlRead      �F�ǂݏo����XML
//       �F�AAttributeName�F���ږ�
//       �F�BRowNo        �F�s�ԍ�
// �߂�l�F�擾�l
// �T�v  �FXML�t�@�C������w�荀�ڂ̒l���擾����B
//========================================================================================
function funXmlRead(xmlRead, AttributeName, RowNo) {

    var objNode;

    try {
        if (xmlRead.xml == "") {
            return "";
        }

        objNode = xmlRead.documentElement.childNodes.item(0).childNodes;

        //�qɰ�ނ̐�����������
        if (objNode.length == 0) {
            return "";
        }
        //�����l���擾
        return objNode.item(RowNo).getAttributeNode(AttributeName).value;

    } catch (e) {
        return "";
    }

}

//========================================================================================
// XML�ǂݏo������_2
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
// ����  �F�@xmlRead      �F�ǂݏo����XML
//       �F�ATableNo      �F�e�[�u���s�ԍ�
//       �F�BAttributeName�F���ږ�
//       �F�CRowNo        �F�s�ԍ�
// �߂�l�F�擾�l
// �T�v  �FXML�t�@�C������w�荀�ڂ̒l���擾����B
//========================================================================================
function funXmlRead_2(xmlRead, TableNo, AttributeName, RowNo) {

    var objNode;

    try {
        if (xmlRead.xml == "") {
            return "";
        }

        objNode = xmlRead.documentElement.childNodes.item(TableNo).childNodes;

        //�qɰ�ނ̐�����������
        if (objNode.length == 0) {
            return "";
        }
        //�����l���擾
        return objNode.item(RowNo).getAttributeNode(AttributeName).value;

    } catch (e) {
        return "";
    }

}

//========================================================================================
// XML�ǂݏo������_3
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/22
// ����  �F�@xmlRead      �F�ǂݏo����XML
//       �F�ATableNm      �F�e�[�u������
//       �F�BAttributeName�F���ږ�
//       �F�CTableNo      �F�e�[�u���s�ԍ�
//       �F�DRowNo        �F���R�[�h�s�ԍ�
// �߂�l�F�擾�l
// �T�v  �FXML�t�@�C������w�荀�ڂ̒l���擾����B
//========================================================================================
function funXmlRead_3(xmlRead, TableNm, AttributeName, TableNo, RowNo) {

    var objNode;

    try {
        if (xmlRead.xml == "") {
            return "";
        }

        //XML���^�O���Ō������A�m�[�h���X�g���擾�i�ŏ���1���ځj
        objNode = xmlRead.getElementsByTagName(TableNm)[TableNo].childNodes;

        //�qɰ�ނ̐�����������
        if (objNode.length == 0) {
            return "";
        }

        //�����l���擾
        return objNode.item(RowNo).getAttributeNode(AttributeName).value;

    } catch (e) {
        return "";
    }

}

//========================================================================================
// XML�������ݏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlWrite     �F�������ݐ�XML
//       �F�AAttributeName�F���ږ�
//       �F�BsetValue     �F�ݒ�l
//       �F�CRowNo        �F�s�ԍ�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �FXML�t�@�C���̎w�荀�ڂɒl��ݒ肷��B
//========================================================================================
function funXmlWrite(xmlWrite, AttributeName, setValue, RowNo) {

    var objNode;

    try {
        if (xmlWrite.xml == "") {
            return false;
        }

        objNode = xmlWrite.documentElement.childNodes.item(0).childNodes;

        //�qɰ�ނ̐�����������
        if (objNode.length == 0) {
            return true;
        }

        //�����l��ݒ�
        objNode.item(RowNo).setAttribute(AttributeName, setValue);

        return true;

    } catch (e) {
        return false;
    }

}

//========================================================================================
// XML�������ݏ���(�e�[�u���w��)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlWrite     �F�������ݐ�XML
//       �F�ATableName    �F�e�[�u����
//       �F�BAttributeName�F���ږ�
//       �F�CsetValue     �F�ݒ�l
//       �F�DRowNo        �F�s�ԍ�
// �߂�l�F����I��:true�^�ُ�I��:false
// �T�v  �FXML�t�@�C���̎w�荀�ڂɒl��ݒ肷��B
//========================================================================================
function funXmlWrite_Tbl(xmlWrite, TableName, AttributeName, setValue, RowNo) {

    var objNode;
    var tNode;
    var recCnt;
    var i;

    try {
        if (xmlWrite.xml == "") {
            return false;
        }

        //�qɰ�ނ̐�����������
        recCnt = xmlWrite.documentElement.childNodes.length;
        if (recCnt == 0) {
            //�s��ǉ�
            funAddRecNode(xmlWrite, xmlWrite.documentElement.nodeName, TableName);
        } else {
            //�擪�̎qɰ�ނ��擾
            tNode = xmlWrite.documentElement.firstChild;
            if (tNode.nodeName == TableName) {
                objNode = xmlWrite.documentElement.childNodes.item(0).childNodes;

            } else {
                for (i = 1; i < recCnt; i++) {
                    //���̎qɰ�ނ��擾
                    tNode = tNode.nextSibling;
                    if (tNode.nodeName == TableName) {
                        objNode = xmlWrite.documentElement.childNodes.item(i).childNodes;
                        break;
                    }
                }
            }
        }

        //�qɰ�ނ̐�����������
        if (objNode.length == 0) {
            return true;
        }

        //�����l��ݒ�
        objNode.item(RowNo).setAttribute(AttributeName, setValue);

        return true;

    } catch (e) {
        return false;
    }

}

//========================================================================================
// XML���R�[�h�����擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlData �F�����擾XML
// �߂�l�F���R�[�h����
// �T�v  �FXML�t�@�C�����R�[�h�������擾����
//========================================================================================
function funGetLength(xmlData) {

    var reccnt;
    var obj;

    reccnt = 0;

    if (xmlData.xml == "") {
        return reccnt;

    } else {
        obj = xmlData.documentElement.childNodes.item(0);

        try {
            //�qɰ�ނ̑�������
            if (obj.hasChildNodes()) {
                //���݂���ꍇ
                reccnt = obj.childNodes.length;
            } else {
                //���݂��Ȃ��ꍇ
                reccnt = 0;
            }

        } catch (e) {

        }
    }

    return reccnt;

}

//========================================================================================
// XML���R�[�h�����擾����_2
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/21
// ����  �F�@xmlData �F�����擾XML
//       �F�ATableNo �F�e�[�u���s�ԍ�
// �߂�l�F���R�[�h����
// �T�v  �FXML�t�@�C�����R�[�h�������擾����
//========================================================================================
function funGetLength_2(xmlData, TableNo) {

    var reccnt;
    var obj;

    reccnt = 0;

    if (xmlData.xml == "") {
        return reccnt;

    } else {
        obj = xmlData.documentElement.childNodes.item(TableNo);

        try {
            //�qɰ�ނ̑�������
            if (obj.hasChildNodes()) {
                //���݂���ꍇ
                reccnt = obj.childNodes.length;
            } else {
                //���݂��Ȃ��ꍇ
                reccnt = 0;
            }

        } catch (e) {

        }
    }

    return reccnt;

}

//========================================================================================
// XML���R�[�h�����擾����_3
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/10/21
// ����  �F�@xmlData �F�����擾XML
//       �F�ATableNm �F�e�[�u������
//       �F�ATableNo �F�e�[�u���s�ԍ�
// �߂�l�F���R�[�h����
// �T�v  �FXML�t�@�C�����R�[�h�������擾����
//========================================================================================
function funGetLength_3(xmlData, TableNm, TableNo) {

    var reccnt;
    var obj;

    reccnt = 0;

    if (xmlData.xml == "") {
        return reccnt;

    } else {
        obj = xmlData.getElementsByTagName(TableNm)[TableNo];

        try {
            //�qɰ�ނ̑�������
            if (obj.hasChildNodes()) {
                //���݂���ꍇ
                reccnt = obj.childNodes.length;
            } else {
                //���݂��Ȃ��ꍇ
                reccnt = 0;
            }

        } catch (e) {

        }
    }

    return reccnt;

}

//========================================================================================
// XML���R�[�h�ǉ�����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlData �F�s�ǉ�XML
//       �F�AFuncId  �F�@�\ID
// �߂�l�F�Ȃ�
// �T�v  �FXML�ɐV�KREC�m�[�h��ǉ�����
//========================================================================================
function funAddRecNode(xmlData, FuncId) {

    var xmlBuff;
    var fNode;
    var tNode;
    var rNode;

    xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    if (xmlData.xml == "") {
        //�V����ɰ�ނ��쐬
        fNode = xmlBuff.createNode(1, FuncId, "");
        tNode = xmlBuff.createNode(1, "table", "");
    } else {
        //�����ɰ�ނ��擾
        fNode = xmlData.documentElement.cloneNode(false);
        tNode = xmlData.documentElement.childNodes.item(0).cloneNode(true);
    }

    try {
        //Recɰ�ނ̒ǉ�
        rNode = xmlBuff.createNode(1, "rec", "");
        tNode.appendChild(rNode);
        fNode.appendChild(tNode);
        xmlBuff.appendChild(fNode);
        xmlData.load(xmlBuff);

    } catch (e) {

    }

    return true;

}

//========================================================================================
// XML���R�[�h�ǉ�����(�e�[�u���w��)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlData   �F�s�ǉ�XML
//       �F�AFuncId    �F�@�\ID
//       �F�BTableName �F�e�[�u����
// �߂�l�F�Ȃ�
// �T�v  �FXML�ɐV�KREC�m�[�h��ǉ�����
//========================================================================================
function funAddRecNode_Tbl(xmlData, FuncId, TableName) {

    var xmlBuff;
    var objNode;
    var fNode;
    var tNode;
    var rNode;
    var recCnt;
    var i;

    xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    if (xmlData.xml == "") {
        //�V����ɰ�ނ��쐬
        fNode = xmlBuff.createNode(1, FuncId, "");
        tNode = xmlBuff.createNode(1, TableName, "");

        //Recɰ�ނ̒ǉ�
        rNode = xmlBuff.createNode(1, "rec", "");
        tNode.appendChild(rNode);
        fNode.appendChild(tNode);
        xmlBuff.appendChild(fNode);
        xmlData.load(xmlBuff);

    } else {
        //�����ɰ�ނ��擾
        fNode = xmlData.documentElement;

        //�qɰ�ނ̐�����������
        recCnt = xmlData.documentElement.childNodes.length;
        if (recCnt == 0) {
            tNode = xmlBuff.createNode(1, TableName, "");
        } else {
            //�擪�̎qɰ�ނ��擾
            objNode = xmlData.documentElement.firstChild;
            if (objNode.nodeName == TableName) {
                tNode = xmlData.documentElement.childNodes.item(0);

            } else {
                for (i = 1; i < recCnt; i++) {
                    //���̎qɰ�ނ��擾
                    objNode = objNode.nextSibling;
                    if (objNode.nodeName == TableName) {
                        tNode = xmlData.documentElement.childNodes.item(i);
                        break;
                    }
                }
            }
        }

        //Recɰ�ނ̒ǉ�
        rNode = xmlBuff.createNode(1, "rec", "");
        tNode.appendChild(rNode);
        xmlBuff.appendChild(fNode);
        xmlData.load(xmlBuff);
    }

    return true;

}

//========================================================================================
// �I���s�폜(�e�[�u���w��)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlTable �F���X�g�pXML
// �߂�l�F�Ȃ�
// �T�v  �F�I���s���폜����B
//========================================================================================
function funRowDelete_Tbl(xmlData, TableName, RowNo) {

    var objNode;
    var fNode;
    var tNode;
    var ndDel;
    var recCnt;
    var i;

    //�����ɰ�ނ��擾
    fNode = xmlData.documentElement;

    //�qɰ�ނ̐�����������
    recCnt = xmlData.documentElement.childNodes.length;
    if (recCnt == 0) {
        tNode = xmlBuff.createNode(1, TableName, "");
    } else {
        //�擪�̎qɰ�ނ��擾
        objNode = xmlData.documentElement.firstChild;
        if (objNode.nodeName == TableName) {
            tNode = xmlData.documentElement.childNodes.item(0);

        } else {
            for (i = 1; i < recCnt; i++) {
                //���̎qɰ�ނ��擾
                objNode = objNode.nextSibling;
                if (objNode.nodeName == TableName) {
                    tNode = xmlData.documentElement.childNodes.item(i);
                    break;
                }
            }
        }
    }

    //�I���s���폜
    ndDel = tNode.childNodes.item(RowNo);
    tNode.removeChild(ndDel);

    return true;

}

