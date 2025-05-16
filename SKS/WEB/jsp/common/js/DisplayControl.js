//========================================================================================
// ��ʑ��쏈�� [DisplayControl.js]
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// �T�v  �F��ʍ��ڂɑ΂��Ă̑�����s���B
//========================================================================================

var CurrentPage = "";   //�I���y�[�W
var CurrentRow = "";    //�I���s�ʒu

//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.14
var hndl = null;        //setInterval() �̃n���h���l

//========================================================================================
// ���O�C�����\��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlUser �F���[�U���i�[XML��
//       �F�Apattern �F�����p�^�[��
//           1:�ƭ��A2:�ƭ��ȊO
//       �F�BObjectId�F�ݒ�I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F���[�U���\���p��HTML���𐶐��A�o�͂���B
//========================================================================================
function funInformationDisplay(xmlUser, pattern, ObjectId) {

    var obj;              //�ݒ��I�u�W�F�N�g
    var OutputHtml;       //�o��HTML
    var ShainName;        //�Ј���
    var KaishaName;       //��Ж�
    var BushoName;        //������
    var GroupName;        //�O���[�v��
    var TeamName;         //�`�[����
    var YakuName;         //��E��
    var SysVersion;       //�V�X�e���o�[�W����

    obj = document.getElementById(ObjectId);

    //����ݔ���
    if (pattern == 1) {
        //�ƭ�
	    ShainName = funXmlRead(xmlUser, "nm_user", 0);
	    KaishaName = funXmlRead(xmlUser, "nm_kaisha", 0);
	    BushoName = funXmlRead(xmlUser, "nm_busho", 0);

        GroupName = funXmlRead(xmlUser, "nm_group", 0);
        TeamName = funXmlRead(xmlUser, "nm_team", 0);
        YakuName = funXmlRead(xmlUser, "nm_literal", 0);

        SysVersion = funXmlRead(xmlUser, "systemversion", 0);

        OutputHtml = "<table align=\"right\" width=\"300px\">";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";

        //�yQP@00342�z
        OutputHtml += "        <td align=\"right\" width=\"150px\">�o�[�W�������F</td>";

        OutputHtml += "        <td align=\"right\" width=\"150px\">" + SysVersion + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
        OutputHtml += "        <td align=\"right\">�Ј����F</td>";
        OutputHtml += "        <td align=\"right\">" + ShainName + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
        OutputHtml += "        <td align=\"right\">������Ж��F</td>";
        OutputHtml += "        <td align=\"right\">" + KaishaName + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
        OutputHtml += "        <td align=\"right\">�����������F</td>";
        OutputHtml += "        <td align=\"right\">" + BushoName + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
        OutputHtml += "        <td align=\"right\">�����O���[�v���F</td>";
        OutputHtml += "        <td align=\"right\">" + GroupName + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
        OutputHtml += "        <td align=\"right\">�����`�[�����F</td>";
        OutputHtml += "        <td align=\"right\">" + TeamName + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";

        //�Ǘ��䒠No304�C�� TT���� 2009/09/29
        //------------------------------------------------------------- START

        //OutputHtml += "        <td align=\"right\">��E���F</td>";
        //OutputHtml += "        <td align=\"right\">" + YakuName + "</td>";

        OutputHtml += "        <td align=\"right\"></td>";
        OutputHtml += "        <td align=\"right\"></td>";

        //------------------------------------------------------------- END

        OutputHtml += "    </tr>";
        OutputHtml += "</table>";


    }
    else if(pattern == 110){
        //�������Z���

        ShainName = funXmlRead(xmlUser[1], "nm_user", 0);
        KaishaName = funXmlRead(xmlUser[1], "nm_kaisha", 0);
        BushoName = funXmlRead(xmlUser[1], "nm_busho", 0);

        OutputHtml = "<table width=\"99%\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td width=\"*%\"></td>";
        OutputHtml += "        <td width=\"60\">������ЁF</td>";
        OutputHtml += "        <td width=\"160\">" + KaishaName + "</td>";
        OutputHtml += "        <td width=\"60\">���������F</td>";
        OutputHtml += "        <td width=\"120\">" + BushoName + "</td>";
        OutputHtml += "        <td width=\"50\">�S���ҁF</td>";
        OutputHtml += "        <td width=\"200\">" + ShainName + "</td>";
        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";
    }
    else {
        //�ƭ��ȊO
	    ShainName = funXmlRead(xmlUser, "nm_user", 0);
	    KaishaName = funXmlRead(xmlUser, "nm_kaisha", 0);
	    BushoName = funXmlRead(xmlUser, "nm_busho", 0);

        OutputHtml = "<table width=\"99%\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td width=\"*%\"></td>";
        OutputHtml += "        <td width=\"60\">������ЁF</td>";
        OutputHtml += "        <td width=\"160\">" + KaishaName + "</td>";
        OutputHtml += "        <td width=\"60\">���������F</td>";
        OutputHtml += "        <td width=\"120\">" + BushoName + "</td>";
        OutputHtml += "        <td width=\"50\">�S���ҁF</td>";
        OutputHtml += "        <td width=\"150\">" + ShainName + "</td>";
        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";
    }

    //HTML���o��
    obj.innerHTML = OutputHtml;

    return true;

}
//�yKPX@1602367�z2016/09/01 May Thu add start
//========================================================================================
// ���O�C�����\��
// �쐬�ҁFMay Thu
// �쐬���F2016/09/01
// �T�v  �F�������\���p��HTML���𐶐��A�o�͂���B
//========================================================================================

function funOrderInfoDisplay(xmlUser, pattern, ObjectId) {
	var OrderCount;       //��������
	var ShainName;        //�Ј���
	var i;
	var OutputHtml;       //�o��HTML
    obj = document.getElementById(ObjectId);

    //�����̎擾
    var reccnt = funGetLength(xmlUser);
 	OutputHtml = "<table align=\"right\" width=\"300px\">";
	OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
	OutputHtml += "    <td colspan=\"2\" align=\"right\" width=\"300px\">------------------------------------------</td>";
	OutputHtml += "    </tr>";
	if(reccnt ==  0)
	{
			OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
			OutputHtml += "    <td colspan=\"2\" align=\"left\" width=\"300px\">��ƒ��̃f�[�^�����݂��܂���B</td>";
			OutputHtml += "    </tr>";
	}
	//��������������ꍇ
	else{
		OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
		OutputHtml += "    <td colspan=\"2\" align=\"left\" width=\"300px\">��ƒ��̃f�[�^�����݂��܂��B</td>";
		OutputHtml += "    </tr>";
		for (i = 0; i < reccnt ; i++){
		    ShainName = funXmlRead(xmlUser, "nm_hattyu", i);
            OrderCount= funXmlRead(xmlUser, "ORDER_CNT", i);      //��������
		    OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
            OutputHtml += "        <td align=\"left\" width=\"150px\">" + ShainName + "</td>";
			OutputHtml += "        <td align=\"right\" width=\"150px\">" + OrderCount + "��</td>";
			OutputHtml += "    </tr>";
		}
	}
//    OutputHtml += "    </tr>";
	OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
    OutputHtml += "        <td colspan=\"2\" align=\"right\" width=\"300px\">------------------------------------------</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "</table>";
	//HTML���o��
    obj.innerHTML = OutputHtml;
    return true;
 }
//�yKPX@1602367�z2016/09/01 May Thu add end
/*
//========================================================================================
// �y�[�W�����N�쐬
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@PageNo   �F���݂̃y�[�W�ԍ�
//       �F�AMaxPageNo�F�ŏI�y�[�W�ԍ�
//       �F�BLinkId   �F�y�[�W�����N�ݒ�I�u�W�F�N�gID
//       �F�CTblId    �F���ו��e�[�u���I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F�y�[�W�J�ڗp��HTML���𐶐��A�o�͂���B
//========================================================================================
function funCreatePageLink(PageNo, MaxPageNo, LinkId, TblId) {

    var obj;              //�ݒ��I�u�W�F�N�g
    var OutputHtml;       //�o��HTML
    var i;
    var startidx;
    var endidx;

    obj = document.getElementById(LinkId);

    if (MaxPageNo != 0) {
        OutputHtml = "<table width=\"99%\" height=\"50\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td align=\"center\">";

        if (PageNo == "1") {
            OutputHtml += "�ŏ�";
        } else {
            OutputHtml += "<a href=\"javascript:funPageMove(1);\"><font color=\"blue\">�ŏ�</font></a>";
        }

        OutputHtml += "&nbsp;&nbsp;&nbsp;�@";

        //�J�n�߰�ށA�I���߰�ވʒu�̎擾
        if (MaxPageNo < 11) {
            startidx = 1;
            endidx = MaxPageNo;
        } else {
            if (PageNo < 5) {
                startidx = 1;
                endidx = 10;
            } else {
                startidx = PageNo - 4;
                if (MaxPageNo < PageNo+5) {
                    startidx = MaxPageNo - 9;
                    endidx = MaxPageNo;
                } else {
                    endidx = PageNo + 5;
                }
            }
        }

        for (i = startidx; i <= endidx; i++) {
            OutputHtml += "�@";
            if (i == PageNo) {
                OutputHtml += "<span style=\"font-size:20px\">";
                OutputHtml += i;
                OutputHtml += "</span>&nbsp;";
            } else {
                OutputHtml += "<a href=\"javascript:funPageMove(" + i + ");\"><font color=\"blue\">" + i + "</font></a>&nbsp;";
            }
        }

        OutputHtml += "&nbsp;&nbsp;�@";

        if (MaxPageNo == "1" || PageNo == MaxPageNo) {
            OutputHtml += "�Ō�";
        } else {
            OutputHtml += "<a href=\"javascript:funPageMove(" + MaxPageNo + ");\"><font color=\"blue\">�Ō�</font></a>";
        }

        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";

    } else {
        //�ް������݂��Ȃ��ꍇ���ݸ��\�����Ȃ�
        OutputHtml = "";
    }

    //HTML���o��
    obj.innerHTML = OutputHtml;
    CurrentPage = PageNo;

    return true;

}
*/
//========================================================================================
// �y�[�W�����N�쐬
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@PageNo   �F���݂̃y�[�W�ԍ�
//       �F�AMaxPageNo�F�ŏI�y�[�W�ԍ�
//       �F�BLinkId   �F�y�[�W�����N�ݒ�I�u�W�F�N�gID
//       �F�CTblId    �F���ו��e�[�u���I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F�y�[�W�J�ڗp��HTML���𐶐��A�o�͂���B
//========================================================================================
function funCreatePageLink(PageNo, MaxPageNo, LinkId, TblId) {

    var obj;              //�ݒ��I�u�W�F�N�g
    var OutputHtml;       //�o��HTML
    var i;
    var startidx;
    var endidx;

    obj = document.getElementById(LinkId);

    if (MaxPageNo != 0) {
        OutputHtml = "<table width=\"99%\" height=\"50\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td align=\"center\">";

        if (PageNo == "1") {
            OutputHtml += "�ŏ�";
        } else {
            OutputHtml += "<span onClick=\"funPageMove(1);\" style=\"cursor:pointer;color:blue;\"><u>�ŏ�</u></span>";
        }

        OutputHtml += "&nbsp;&nbsp;&nbsp;�@";

        //�J�n�߰�ށA�I���߰�ވʒu�̎擾
        if (MaxPageNo < 11) {
            startidx = 1;
            endidx = MaxPageNo;
        } else {
            if (PageNo < 5) {
                startidx = 1;
                endidx = 10;
            } else {
                startidx = PageNo - 4;
                if (MaxPageNo < PageNo+5) {
                    startidx = MaxPageNo - 9;
                    endidx = MaxPageNo;
                } else {
                    endidx = PageNo + 5;
                }
            }
        }

        for (i = startidx; i <= endidx; i++) {
            OutputHtml += "�@";
            if (i == PageNo) {
                OutputHtml += "<span style=\"font-size:20px\">";
                OutputHtml += i;
                OutputHtml += "</span>&nbsp;";
            } else {
                OutputHtml += "<span onClick=\"funPageMove(" + i + ");\" style=\"cursor:pointer;color:blue;\"><u>" + i + "</u></span>&nbsp;";
            }
        }

        OutputHtml += "&nbsp;&nbsp;�@";

        if (MaxPageNo == "1" || PageNo == MaxPageNo) {
            OutputHtml += "�Ō�";
        } else {
            OutputHtml += "<span onClick=\"funPageMove(" + MaxPageNo + ");\" style=\"cursor:pointer;color:blue;\"><u>�Ō�</u></span>";
        }

        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";

    } else {
        //�ް������݂��Ȃ��ꍇ���ݸ��\�����Ȃ�
        OutputHtml = "";
    }

    //HTML���o��
    obj.innerHTML = OutputHtml;
    CurrentPage = PageNo;

    return true;

}

//========================================================================================
// ���j���[�{�^���쐬
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlUser �F���[�U���i�[XML��
//       �F�AScreenId�F���ID
//       �F�BObjectId�F�{�^���ݒ�I�u�W�F�N�gID
// �߂�l�F�Ȃ�
// �T�v  �F���j���[�{�^����HTML���𐶐��A�o�͂���B
//========================================================================================
function funCreateMenuButton(xmlUser, ScreenId, ObjectId) {

    var obj;              //�ݒ��I�u�W�F�N�g
    var OutputHtml;       //�o��HTML
    var BtnHtml;          //�o��HTML
    var reccnt;
    var btncnt;
    var GamenId;
    var i;
    var breakflg1;
    var breakflg2;
    var BlankHeight;

    OutputHtml = "";
    BtnHtml = "";
    breakflg1 = false;
    breakflg2 = false;
  //�yQP@30297�zadd start 20140501
    breakflg3 = false;
  //�yQP@30297�zadd end 20140501
    btncnt = 0;

    obj = document.getElementById(ObjectId);

    //�������̎擾
    reccnt = funGetLength(xmlUser);

    OutputHtml += "<table align=\"center\" valign=\"top\" bgcolor=\"#C2C0FF\" width=\"300\" height=\"400\">";
    OutputHtml += "<tr>";
    OutputHtml += "    <td height=\"16\"></td>";
    OutputHtml += "</tr>";

    //���ID����
    if (ScreenId.toString() == ConMainMenuId.toString()) {
        //Ҳ��ƭ�
        for (i = 0; i < reccnt; i++) {
            GamenId = funXmlRead(xmlUser, "id_gamen", i);

            //���ID�ŕ���
            switch (GamenId.toString()) {
  //�yKPX@1900110�zdel start 20190808            
                // case ConGmnIdShisakuList.toString():      //����f�[�^�ꗗ
                //     OutputHtml += "<tr>";
                //     OutputHtml += "    <td align=\"center\" height=\"50\">";
                //     OutputHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"����ꗗ\" onclick=\"funNext(1)\">";
                //     OutputHtml += "    </td>";
                //     OutputHtml += "</tr>";
                //     btncnt += 1;
                //     break;
  //�yKPX@1900110�zdel end   20190808
                case ConGmnIdGenryoInfoMst.toString():    //�������͏��}�X�^
                case ConGmnIdGenryoInfoCsv.toString():    //�������͏��}�X�^CSV
                case ConGmnIdTankaBudomari.toString():    //�S�H��P������
                case ConGmnIdLiteralMst.toString():       //���e�����}�X�^
                case ConGmnIdLiteralCsv.toString():       //���e�����}�X�^CSV
                case ConGmnIdGroupMst.toString():         //�O���[�v�}�X�^
                case ConGmnIdKengenMst.toString():        //�����}�X�^
                case ConGmnIdTantoMst.toString():         //�S���҃}�X�^
                case ConGmnIdEigyoTantoMst.toString():         //�yQP@00342�z�S���҃}�X�^�i�c�Ɓj
                    if (breakflg1 == false) {
                        //Ͻ��ƭ����݂��o�͂���Ă��Ȃ��ꍇ�AϽ��ƭ����݂𐶐�
                        OutputHtml += "<tr>";
                        OutputHtml += "    <td align=\"center\" height=\"50\">";
                        OutputHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"�}�X�^���j���[\" onclick=\"funNext(2)\">";
                        OutputHtml += "    </td>";
                        OutputHtml += "</tr>";
                        breakflg1 = true;
                        btncnt += 1;
                    }
                    break;
                case ConGmnIdGenkaShisanItiran.toString():         //�yQP@00342�z�������Z�ꗗ
                        OutputHtml += "<tr>";
                        OutputHtml += "    <td align=\"center\" height=\"50\">";
                        OutputHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"�������Z�ꗗ\" onclick=\"funNext(3)\">";
                        OutputHtml += "    </td>";
                        OutputHtml += "</tr>";
                        btncnt += 1;
                    break;
 //�yQP@30297�zadd start 20140501
                case ConGmnIdCostTblAdd.toString():         //�yQP@30297�z�R�X�g�e�[�u�����F�E�o�^
                case ConGmnIdCostTblList.toString():        //�yQP@30297�z�R�X�g�e�[�u���ꗗ
 //�yQP@40404�zadd start 20140901
                case ConGmnIdDesignSpaceAdd.toString():     //�yQP@40404�z�f�U�C���X�y�[�X�o�^
                case ConGmnIdDesignSpaceDL.toString():      //�yQP@40404�z�f�U�C���X�y�[�X�_�E�����[�h
                case ConGmnIdShizaiTehaiInput.toString():   //�yQP@40404�z���ގ�z����
                case ConGmnIdShizaiTehaiOutput.toString():  //�yQP@40404�z���ގ�z�˗����o��
                case ConGmnIdShizaiCodeList.toString():     //�yQP@40404�z�����ꊇ�I��
                case ConGmnIdGentyoLiteralMst.toString():   //�yQP@40404�z�����ޒ��B�� �J�e�S���}�X�^�����e�i���X

				//�yKPX@1602367�z2016/09/01 May Thu add start
				case ConGmnIdBasePriceAdd.toString():      // �x�[�X�P���o�^�E���F
				case ConGmnIdBasePriceList.toString():     // �x�[�X�P���ꗗ

				//�yKPX@1602367�z2016/09/01 May Thu add end
				// �yKPX@1603044�z2017/06/02 nakamura add start
				case ConGmdIdGenchoPage.toString():     // �����ރ}�X�^
				// �yKPX@1603044�z2017/06/02 nakamura add end
                    if (breakflg3 == false) {
                        OutputHtml += "<tr>";
                        OutputHtml += "    <td align=\"center\" height=\"50\">";
                        OutputHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"�����ޒ��B�����j���[\" onclick=\"funNext(4)\">";
                        OutputHtml += "    </td>";
                        OutputHtml += "</tr>";
                        breakflg3 = true;
                        btncnt += 1;
                    }
                    break;
            }
        }

    } else if (ScreenId.toString() == ConGenchoMenuId.toString()) {
        //���n���B���j���[�̎w�菇�Ԃɕ����[�v MAKOTO TAKADA ADD
        for (j = 0; j < ConGenchoMenuIdSeqArray.length; j++) {
          //Ͻ��ƭ�
            for (i = 0; i < reccnt; i++) {
             GamenId = funXmlRead(xmlUser, "id_gamen", i);

            //��ʂh�c����v���n���B���j���[�ƈ�v���Ȃ���΁A�����[�v�Ɉړ��B MAKOTO TAKADA ADD
            if(ConGenchoMenuIdSeqArray[j] != GamenId)  {
                 continue;
            }
            //���ID�ŕ���
            switch (GamenId.toString()) {
                case ConGmnIdCostTblAdd.toString():         //�yQP@30297�z�R�X�g�e�[�u���o�^�E���F
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�R�X�g�e�[�u���o�^\" onclick=\"funNext(1)\"><br>";
                    break;
                case ConGmnIdCostTblList.toString():        //�yQP@30297�z�R�X�g�e�[�u���ꗗ
                
              //2017/3/7 tamura delete start
                  //BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�R�X�g�e�[�u���ꗗ\" onclick=\"funNext(2)\"><br>";
              //2017/3/7 tamura delete end  
              //2017/3/7 tamura add start
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�R�X�g�e�[�u���ꗗ�E���F\" onclick=\"funNext(2)\"><br>";
              //2017/3/7 tamura add end
                    break;
                case ConGmnIdCostTblRef.toString():        //�yQP@30297�z�R�X�g�e�[�u���ꗗ
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�R�X�g�e�[�u���Q��\" onclick=\"funNext(15)\"><br>";
                    break;
              //�yQP@40404�zadd start 20140901
                case ConGmnIdShizaiTehaiInput.toString():   //�yQP@40404�z���ގ�z����
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"���ގ�z����\" onclick=\"funNext(5)\"><br>";
                    break;
                case ConGmnIdShizaiTehaiOutput.toString():   //�yQP@40404�z���ގ�z�˗����o��
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"���ގ�z�˗����o��\" onclick=\"funOpen(14)\"><br>";
                    break;
                case ConGmnIdGentyoLiteralMst.toString():   //�yQP@40404�z�����ޒ��B�� �J�e�S���}�X�^�����e�i���X
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�����ޒ��B��\n�J�e�S���}�X�^�����e�i���X\" onclick=\"funNext(7)\"><br>";
                    break;
              //�yQP@40404�zadd end 20140901
			     case ConGmnIdDesignSpaceAdd.toString():     //�yQP@40404�z�f�U�C���X�y�[�X�o�^
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�f�U�C���X�y�[�X�o�^\" onclick=\"funNext(3)\"><br>";
                    break;
                case ConGmnIdDesignSpaceDL.toString():      //�yQP@40404�z�f�U�C���X�y�[�X�_�E�����[�h
                	BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�f�U�C���X�y�[�X�_�E�����[�h\" onclick=\"funNext(4)\"><br>";
                break;
		   //�yKPX@1602367�zadd May Thu start 20160831
				case ConGmnIdBasePriceAdd.toString():   //�yKPX@1602367�z�x�[�X�P���o�^�E���F
              //2017/3/7 tamura delete start
                  //BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�x�[�X�P���o�^�E���F\" onclick=\"funNext(10)\"><br>";
              //2017/3/7 tamura delete end
              //2017/3/7 tamura add start
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�x�[�X�P���o�^\" onclick=\"funNext(10)\"><br>";
              //2017/3/7 tamura add end
                    break;
                case ConGmnIdBasePriceList.toString():   //�yKPX@1602367�z�x�[�X�P���ꗗ
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�x�[�X�P���ꗗ�E���F\" onclick=\"funNext(11)\"><br>";
                    break;
				case ConGmnIdShizaiTehaiList.toString():   //�yKPX@1602367�z���ގ�z�ψꗗ
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"���ގ�z�ψꗗ\" onclick=\"funNext(12)\"><br>";
                    break;
				case ConGmnIdHattyuLiteralMst.toString():   //�yKPX@1602367�z������}�X�^
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"������}�X�^\" onclick=\"funNext(13)\"><br>";
                    break;
			  //�yKPX@1602367�zadd May Thu end 20160831
			  //2017/6/2 nakamura add start
			  	case ConGmdIdGenchoPage.toString():   //// �yKPX@1603044�z2017/06/02 nakamura add
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"�����ރ}�X�^\" onclick=\"funNext(16)\"><br>";
                    break;
              //2017/6/2 nakamura add end
            }
            if (BtnHtml != "") {
                OutputHtml += "<tr>";
                OutputHtml += "    <td align=\"center\" height=\"50\">";
                OutputHtml += BtnHtml;
                OutputHtml += "    </td>";
                OutputHtml += "</tr>";
                btncnt += 1;

                BtnHtml = "";
 //�yQP@30297�zadd end 20140501
            }
        }
      }
    } else {
        //Ͻ��ƭ�
        for (i = 0; i < reccnt; i++) {
            GamenId = funXmlRead(xmlUser, "id_gamen", i);

            //���ID�ŕ���
            switch (GamenId.toString()) {
                case ConGmnIdGenryoInfoMst.toString():    //�������͏��}�X�^
                case ConGmnIdGenryoInfoCsv.toString():    //�������͏��}�X�^CSV
                    if (breakflg1 == false) {
                        BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"�������͏��}�X�^\" onclick=\"funNext(1)\"><br>";
                        breakflg1 = true;
                    }
                    break;
                case ConGmnIdTankaBudomari.toString():    //�S�H��P������
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"�S�H��P������\" onclick=\"funNext(2)\"><br>";
                    break;
                case ConGmnIdLiteralMst.toString():       //���e�����}�X�^
                case ConGmnIdLiteralCsv.toString():       //���e�����}�X�^CSV
                    if (breakflg2 == false) {
                        BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"�J�e�S���}�X�^\" onclick=\"funNext(3)\"><br>";
                        breakflg2 = true;
                    }
                    break;
                case ConGmnIdGroupMst.toString():         //�O���[�v�}�X�^
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"�O���[�v�}�X�^\" onclick=\"funNext(4)\"><br>";
                    break;
                case ConGmnIdKengenMst.toString():        //�����}�X�^
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"�����}�X�^\" onclick=\"funNext(5)\"><br>";
                    break;
                case ConGmnIdTantoMst.toString():         //�S���҃}�X�^
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"�S���҃}�X�^\" onclick=\"funNext(6)\"><br>";
                    break;
                case ConGmnIdEigyoTantoMst.toString():         //�yQP@00342�z�S���҃}�X�^�i�c�Ɓj
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"�S���҃}�X�^�i�c�Ɓj\" onclick=\"funNext(7)\"><br>";
                    break;
            }

            if (BtnHtml != "") {
                OutputHtml += "<tr>";
                OutputHtml += "    <td align=\"center\" height=\"50\">";
                OutputHtml += BtnHtml;
                OutputHtml += "    </td>";
                OutputHtml += "</tr>";
                btncnt += 1;

                BtnHtml = "";
            }
        }
    }

    BlankHeight = 380 - (50 * btncnt);

    OutputHtml += "<tr>";
    OutputHtml += "    <td height=\"" + BlankHeight + "\"></td>";
    OutputHtml += "</tr>";
    OutputHtml += "</table>";
    obj.innerHTML = OutputHtml;

    return true;

}

//========================================================================================
// �_�C�A���O�N��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@NextUrl�F�J�ڐ�URL
//       �F�AParam  �F����(�z��) or �t�H�[���I�u�W�F�N�g
//       �F�BOption �F�I�v�V�������
// �߂�l�F�����{�^������敪
// �T�v  �F�_�C�A���O��ʂ��N������B
//========================================================================================
function funOpenModalDialog(NextUrl, Param, Option) {

    var RetVal;        //�߂�l

    //Ӱ����޲�۸ނ�\��
    RetVal = showModalDialog(NextUrl, Param, Option);

    return RetVal;

}

//========================================================================================
// ���t�ҏW����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@DateValue�F�Ώۓ��t
// �߂�l�F�Ȃ�
// �T�v  �FYYYY/MM/DD�`���ւ̕ϊ����s���B
//========================================================================================
function funDateFomatChange(DateValue) {
    var ret = DateValue;
    var y = "";
    var m = "";
    var d = "";

    y = ret.substring(0 ,4);
    m = ret.substring(4 ,6);
    d = ret.substring(6 ,8);
    ret = y + "/" + m + "/" + d;

    return ret;

}

//========================================================================================
// �I���s����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�I���s�̔w�i�F��ύX����B
//========================================================================================
function funChangeSelectRowColor() {

    var oSrc = event.srcElement, oTBL, oTR, oTD;
    var BeforeRow;

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {
        for ( oTBL = oSrc.parentElement; oTBL.tagName != "TABLE"; oTBL = oTBL.parentElement ) {}
        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {}

        BeforeRow = (CurrentRow == "" ? 0 : CurrentRow / 1);

        //�w�i�F��ύX
        oTBL.rows(oTR.rowIndex).style.backgroundColor = activeSelectedColor;

        if ((BeforeRow != oTR.rowIndex) && (BeforeRow + 1 <= oTBL.rows.length)){
            //�w�i�F��߂�
            oTBL.rows(BeforeRow).style.backgroundColor = deactiveSelectedColor;
        }

        //���čs�̑ޔ�
        CurrentRow = oTR.rowIndex;
    }

    return true;

}

//========================================================================================
// �I���s�폜
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@xmlTable �F���X�g�pXML
// �߂�l�F�Ȃ�
// �T�v  �F�I���s���폜����B
//========================================================================================
function funSelectRowDelete(xmlTable) {

    var tblNode;
    var ndDel;

    //�s�����I���̏ꍇ�A�����𔲂���
    if (CurrentRow.toString() == "") {
        return true;
    }

    //�I���s���폜
    tblNode = xmlTable.documentElement.childNodes.item(0);
    ndDel = tblNode.childNodes.item(CurrentRow);
    tblNode.removeChild(ndDel);
    CurrentRow = "";

    return true;

}

//========================================================================================
// �J�����g�s�擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�Ȃ�
// �߂�l�F�J�����g�s
// �T�v  �F�I�𒆂̍s�ԍ���Ԃ��B
//========================================================================================
function funGetCurrentRow() {

    return CurrentRow;

}

//========================================================================================
// �J�����g�s�ݒ菈��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@RowNo �F�J�����g�s
// �߂�l�F�Ȃ�
// �T�v  �F�I�𒆂̍s�ԍ���ݒ肷��B
//========================================================================================
function funSetCurrentRow(RowNo) {

    CurrentRow = RowNo;

    return true;

}

//========================================================================================
// �J�����g�y�[�W�擾����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�Ȃ�
// �߂�l�F�J�����g�y�[�W
// �T�v  �F�I�𒆂̃y�[�W�ԍ���Ԃ��B
//========================================================================================
function funGetCurrentPage() {

    return CurrentPage;

}

//========================================================================================
// �J�����g�y�[�W�ݒ菈��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@pageNo �F�J�����g�y�[�W
// �߂�l�F�Ȃ�
// �T�v  �F�I�𒆂̃y�[�W�ԍ���ݒ肷��B
//========================================================================================
function funSetCurrentPage(PageNo) {

    CurrentPage = PageNo;

    return true;

}

//========================================================================================
// �J�����g�s�N���A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@obj �F�e�[�u���I�u�W�F�N�g
// �߂�l�F�Ȃ�
// �T�v  �F�I���s�̍s�ԍ����N���A����B
//========================================================================================
function funClearCurrentRow(obj) {

    if (CurrentRow.toString() != "") {
        //�w�i�F��߂�
        obj.rows(CurrentRow).style.backgroundColor = deactiveSelectedColor;
    }

    //���čs�̸ر
    CurrentRow = "";

    return true;

}

//========================================================================================
// ��ʑ��쐧��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F���������b�Z�[�W��\������B
//========================================================================================
function funShowRunMessage() {

    if (typeof(_scrtable_viewarea) == 'undefined') {
        this.viewArea = document.createElement("SPAN");
        window.document.body.appendChild(this.viewArea);

        this.viewArea.id = "_scrtable_viewarea";
        this.viewArea.style.position = "absolute";
        this.viewArea.style.backgroundColor = "whitesmoke";

        this.viewArea.style.padding = "3px";
        this.viewArea.style.borderStyle = "solid";
        this.viewArea.style.borderWidth = "4px";
        this.viewArea.style.borderColor = "gray";
        this.viewArea.style.fontSize = "20pt";
        this.viewArea.style.filter = "alpha(opacity=80)";

        var x = 0;
        var y = 0;
        var elm = window.document.body;

        while (elm) {
            x += elm.offsetLeft;
            y += elm.offsetTop;
            elm = elm.offsetParent;
        }

        this.viewArea.style.left = x + ((window.document.body.clientWidth - 250) / 2);
        this.viewArea.style.top = y + ((window.document.body.clientHeight - 120) / 2);

        this.viewArea.style.width = "250" ;     //oTBLView.clientWidth;
        this.viewArea.style.height = "120" ;    //oTBLView.clientHeight;

        this.viewArea.style.textAlign="center";

        var oTXT = document.createTextNode("*");
        this.viewArea.appendChild(oTXT);
    }

    this.viewArea = _scrtable_viewarea;

    this.viewArea.innerHTML = '<table height="100%"><tr><td valign="middle" style="font-size:17pt"><img src="../image/loading.gif">������...</td></tr></table>';

    this.viewArea.style.display="";

    return true;

}

//========================================================================================
// ��ʑ��쐧��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F���������b�Z�[�W��\������B
//========================================================================================
function funShowRunMessage2() {

    if (typeof(_scrtable_viewarea) == 'undefined') {
        this.viewArea = document.createElement("SPAN");
        window.document.body.appendChild(this.viewArea);

        this.viewArea.id = "_scrtable_viewarea";
        this.viewArea.style.position = "absolute";
        this.viewArea.style.backgroundColor = "whitesmoke";

        this.viewArea.style.padding = "3px";
        this.viewArea.style.borderStyle = "solid";
        this.viewArea.style.borderWidth = "4px";
        this.viewArea.style.borderColor = "gray";
        this.viewArea.style.fontSize = "20pt";
        this.viewArea.style.filter = "alpha(opacity=80)";

        this.viewArea.style.width = "250" ;     //oTBLView.clientWidth;
        this.viewArea.style.height = "120" ;    //oTBLView.clientHeight;

        this.viewArea.style.textAlign="center";

        var oTXT = document.createTextNode("*");
        this.viewArea.appendChild(oTXT);
    }

    this.viewArea = _scrtable_viewarea;

    this.viewArea.innerHTML = '<table height="100%"><tr><td valign="middle" style="font-size:17pt"><img src="../image/loading.gif">������...</td></tr></table>';

    this.viewArea.style.display="";

    return true;

}

//========================================================================================
// ��ʑ��쐧�����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F���������b�Z�[�W���I������B
//========================================================================================
function funClearRunMessage() {

    try {
        _scrtable_viewarea.style.display = "none";
    } catch(e) {
    }

    return true;

}

//========================================================================================
// ��ʐݒ菈��
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@GamenId �F���ID
// �߂�l�F�Ȃ�
// �T�v  �F��ʃT�C�Y�A��ʕ\���ʒu�A�^�C�g���̐ݒ���s��
//========================================================================================
function funInitScreen(GamenId) {

    var width;
    var height;

    //���ق̕ύX
    switch (GamenId) {
        case ConLoginId:          //۸޲�
            width  = 400;                                     //��ʕ�
            height = funAdjustScreenHeight(260);              //��ʍ���
            //�yQP@00342�z
            //document.title = ConSystemId + ConLogin;          //�������
            document.title = ConLogin;          //�������
            break;
        case ConMainMenuId:       //Ҳ��ƭ�
            width  = 660;                                     //��ʕ�
            height = funAdjustScreenHeight(560);              //��ʍ���
            document.title = ConSystemId + ConMainMenu;       //�������
            break;
        case ConMstMenuId:        //Ͻ��ƭ�
            width  = 660;                                     //��ʕ�
            height = funAdjustScreenHeight(560);              //��ʍ���
            document.title = ConSystemId + ConMstMenu;        //�������
            break;
        case ConShisakuListId:    //�����ް��ꗗ
//            width  = 1024;                                    //��ʕ�
//            height = funAdjustScreenHeight(768);              //��ʍ���
            width  = window.screen.width;                     //��ʕ�
            height = window.screen.height;                    //��ʍ���
            document.title = ConSystemId + ConShisakuList;    //�������
            break;
        case ConGenryoInfoMstId:  //�������͏��Ͻ�����ݽ
//            width  = 1024;                                    //��ʕ�
//            height = funAdjustScreenHeight(768);              //��ʍ���
            width  = window.screen.width;                     //��ʕ�
            height = window.screen.height;                    //��ʍ���
            document.title = ConSystemId + ConGenryoInfoMst;  //�������
            break;
        case ConGenryoInputId:    //���͒l����
            document.title = ConSystemId + ConGenryoInput;    //�������
            break;
        case ConLiteralMstId:     //����Ͻ�����ݽ
            width  = 800;                                     //��ʕ�
            height = funAdjustScreenHeight(530);              //��ʍ���
            document.title = ConSystemId + ConLiteralMst;     //�������
            break;
        case ConGroupMstId:       //��ٰ��Ͻ�����ݽ
            width  = 700;                                     //��ʕ�
            height = funAdjustScreenHeight(330);              //��ʍ���
            document.title = ConSystemId + ConGroupMst;       //�������
            break;
        case ConTantoMstId:       //�S����Ͻ�����ݽ
            width  = 750;                                     //��ʕ�
            height = funAdjustScreenHeight(710);              //��ʍ���
            document.title = ConSystemId + ConTantoMst;       //�������
            break;
        case ConTantoSearchId:    //�S���Ҍ���
            document.title = ConSystemId + ConTantoSearch;    //�������
            break;
        case ConKasihaAddId:      //�����S����Вǉ�
            document.title = ConSystemId + ConKasihaAdd;      //�������
            break;
        case ConKengenMstId:      //����Ͻ�����ݽ
//            width  = 1024;                                    //��ʕ�
//            height = funAdjustScreenHeight(768);              //��ʍ���
            width  = window.screen.width;                     //��ʕ�
            height = window.screen.height;                    //��ʍ���
            document.title = ConSystemId + ConKengenMst;      //�������
            break;
        case ConKengenAddId:      //�����@�\�ǉ�
            document.title = ConSystemId + ConKengenAdd;      //�������
            break;
        case ConTankaBudomariId:  //�S�H��P������
//            width  = 1024;                                    //��ʕ�
//            height = funAdjustScreenHeight(768);              //��ʍ���
            width  = window.screen.width;                     //��ʕ�
            height = window.screen.height;                    //��ʍ���
            document.title = ConSystemId + ConTankaBudomari;  //�������
            break;

        //�yQP@00342�z
        case ConStatusRirekiId:    //�X�e�[�^�X����
            width  = 980;                                    //��ʕ�
            height = funAdjustScreenHeight(768);              //��ʍ���
            document.title = ConSystemId_genka + ConStatusRireki;    //�������
            break;

    	//�yQP@00342�z
        case ConStatusClearId:    //�X�e�[�^�X����
            width  = 700;                                    //��ʕ�
            height = funAdjustScreenHeight(550);              //��ʍ���
            document.title = ConSystemId_genka + ConStatusClear;    //�������
            break;

        //�yQP@00342�z
        case ConEigyoTantoMstId:    //�S���҃}�X�^�����e�i�c�Ɓj
            width  = 900;                                     //��ʕ�
            height = funAdjustScreenHeight(710);              //��ʍ���
            document.title = ConSystemId_genka + ConEigyoTantoMst;    //�������
            break;

        //�yQP@00342�z
        case ConEigyoTantoSearchId:    //�S���҃}�X�^�����e�i�c�Ɓj
            document.title = ConSystemId_genka + ConEigyoTantoSearch;    //�������
            break;

        //�yQP@00342�z
        case ConGenkaListId:    //�����ް��ꗗ
            width  = window.screen.width;                     //��ʕ�
            height = window.screen.height;                    //��ʍ���
            document.title = ConSystemId_genka + ConGenkaList;    //�������
            break;
  //�yQP@30297�zadd start 20140501
        case ConGenchoMenuId:        // �����ޒ��B�����j���[
            width  = 675;                                     //��ʕ�
            height =  funAdjustScreenHeight(800);              //��ʍ���
//            width  = 660;                                     // ��ʕ�
//            height = funAdjustScreenHeight(560);              // ��ʍ���
            document.title = ConSystemId_genka + ConGenchoMenu;     // �������
            break;

        case ConCostTblListId:   // �R�X�g�e�[�u���ꗗ
        	width  = 1520;                                    //��ʕ�
        	height = funAdjustScreenHeight(760);              //��ʍ���
//            width  = window.screen.width;                     // ��ʕ�
//            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConCostTblList;    // �������
            break;

        case ConCostTblAddId:   // �R�X�g�e�[�u���o�^�E���F
            width  = window.screen.width;                     // ��ʕ�
            height = funAdjustScreenHeight(850);              //��ʍ���
//            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConCostTblAdd;    // �������
            break;

        case ConCostTblRefId:   // �R�X�g�e�[�u���Q��
            width  = window.screen.width;                     // ��ʕ�
            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConCostTblRef;    // �������
            break;
  //�yQP@30297�zadd end 20140501

  //�yQP@40404�zadd start 2014/09/01
        case ConDesignSpaceAddId:   // �f�U�C���X�y�[�X�o�^
            width  = window.screen.width;                     // ��ʕ�
            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConDesignSpaceAdd;    // �������
            break;
        case ConShiryoRefId:   // �Q�l����
            width  = 980;                                     //��ʕ�
            height = funAdjustScreenHeight(500);              //��ʍ���
            document.title = ConSystemId_genka + ConShiryoRef;    // �������
            break;
        case ConShizaiTehaiInputId:   // ���ގ�z����
            width  = window.screen.width;                     // ��ʕ�
            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConShizaiTehaiInput;    // �������
            break;
        case ConDesignSpaceDLId:   // �f�U�C���X�y�[�X�_�E�����[�h
            width  = window.screen.width;                     // ��ʕ�
            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConDesignSpaceDL;    // �������
            break;
        case ConShizaiTehaiOutputId:   // ���ގ�z�˗����o��
            width  = window.screen.width;                     // ��ʕ�
            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConShizaiTehaiOutput;    // �������
            break;
        case ConShizaiCodeListId:   // �����ꊇ�I��
            width  = window.screen.width;                     // ��ʕ�
            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConShizaiCodeList;    // �������
            break;
  //�yQP@40404�zadd end 2014/09/01
  //�yQP@40404�zADD start 2014/10/10
        case ConGentyoLiteralMstId:     //�����ޒ��B���p����Ͻ�����ݽ
            width  = 800;                                     //��ʕ�
            height = funAdjustScreenHeight(530);              //��ʍ���
            document.title = ConSystemId + ConGentyoLiteralMst;     //�������
            break;
  //�yQP@40404�zADD end 2014/10/10
    // �yKPX@1602367�z2016/09/06 add start
        case ConBasePriceListId:   // �x�[�X�P���ꗗ
            width  = 1520;                     // ��ʕ�
            height = funAdjustScreenHeight(760);              //��ʍ���
//            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConBasePriceList;    // �������
            break;

        case ConBasePriceAddId:   // �x�[�X�P���o�^�E���F
        	width  = 1600;                                    //��ʕ�
            height = funAdjustScreenHeight(820);              //��ʍ���
//            width  = window.screen.width;                     // ��ʕ�
//            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConBasePriceAdd;    // �������
            break;
        case ConRuiziDataId:   // �ގ��f�[�^�ďo
        	width = 1600;
//            width  = window.screen.width;                     // ��ʕ�
            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConRuiziData;    // �������
            break;
        case ConShizaiTehaiZumiListId:   // ���ގ�z�ψꗗ
            width  = window.screen.width;                     // ��ʕ�
            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId_genka + ConShizaiTehaiZumiList;    // �������
            break;
        case ConHattyuLiteralMstId:     //������}�X�^
        	width  = window.screen.width;                     // ��ʕ�
            height = window.screen.height;                    // ��ʍ���
            document.title = ConSystemId + ConHattyuLiteralMst;     //�������
            break;
        	// �yKPX@1602367�z May Thu�@201609016 add start
        case ConSeihinSearchId:   //���i�R�[�h�����܂�����
             width  = window.screen.width;                     // ��ʕ�
             height = window.screen.height;                    // ��ʍ���
             document.title = ConSystemId_genka + ConSeihinSearch;    // �������
             break;
 	// �yKPX@1602367�z May Thu�@201609016 add end

    }

    //��ʻ��ށE�ʒu�̐ݒ�
    resizeTo(width, height);
    moveTo((screen.width - width) / 2, (screen.height - height) / 2);

    return height;

}

//========================================================================================
// ��ʍ�������
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@height �F��ʍ���
// �߂�l�F������̉�ʍ���
// �T�v  �FIE7�̏ꍇ�A��ʂ̍����𒲐�����
//========================================================================================
function funAdjustScreenHeight(height) {

    var userAgentStr;
    var msieIndex;
    var version;

    //�o�[�W�����̎擾
    userAgentStr = navigator.userAgent;
    msieIndex = userAgentStr.indexOf("MSIE");

    //"MSIE 6.0" ,"MSIE 7.0" �o�[�W�����̑O�ɃX�y�[�X����
    version = userAgentStr.substring(msieIndex+5, userAgentStr.indexOf(".", msieIndex));

    if (version == 7) {
        //IE7�T�C�Y����
        height = height - 25;
    }

    return height;

}

//========================================================================================
// �R���g���[������(READONLY)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@objItem �F�ΏۃI�u�W�F�N�g
//       �F�Amode    �F���[�h(true/false)
// �߂�l�F�Ȃ�
// �T�v  �F�R���g���[���̎g�p�A�s�̐�����s��
//========================================================================================
function funItemReadOnly(objItem, mode) {

    //���۰ق̐���
    if (objItem.type == "text" || objItem.type == "textarea") {
        objItem.readOnly = mode;
    } else {
        objItem.disabled = mode;
    }

    if (objItem.type != "button") {
        //���۰ق̔w�i�F��ύX
        if (mode) {
            objItem.style.background = "gainsboro";
            objItem.tabIndex = "-1";
        } else {
            objItem.style.background = "white";
            objItem.tabIndex = "";
        }
    }

    return true;

}

//========================================================================================
// �R���g���[������(DISABLED)
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@objItem �F�ΏۃI�u�W�F�N�g
//       �F�Amode    �F���[�h(true/false)
// �߂�l�F�Ȃ�
// �T�v  �F�R���g���[���̎g�p�A�s�̐�����s��
//========================================================================================
function funItemDisabled(objItem, mode) {

    //���۰ق̐���
    objItem.disabled = mode;

    if (objItem.type != "button") {
        //���۰ق̔w�i�F��ύX
        if (mode) {
            objItem.style.background = "gainsboro";
        } else {
            objItem.style.background = "white";
        }
    }

    return true;

}

//========================================================================================
// �[�����ߏ���
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@obj  �F�ΏۃI�u�W�F�N�g
//       �F�Aketa �F����
// �߂�l�F�[�����ߌ�̒l
// �T�v  �F�w�茅���ɑO�[����t�����ĕԂ�
//========================================================================================
function funBuryZero(obj, keta) {

    var i;
    var retVal;

    //�Ώےl�̌�������
    if (obj.value.length == 0) {
        return true;
    } else {
        retVal = obj.value;
    }

    //�Ώےl�̑O�ɾ�ۂ�t������
    for (i = obj.value.length; i < keta; i++) {
        retVal = "0" + retVal.toString();
    }

    //�l��ݒ肷��
    obj.value = retVal;

    return true;

}

//========================================================================================
// �J���}�ҏW����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@value �F�J���}�ҏW����l
// �߂�l�F�J���}�ҏW����
// �T�v  �F�����ɑ΂��ĂR����؂�̃J���}�ҏW���s��
//========================================================================================
function funAddComma(value){

    var i;

    value = "" + value;

    //�J���}�폜
    value = value.replace(/,/g,"");

    for(i = 0; i < value.length/3; i++){
        value = value.replace(/^([+-]?\d+)(\d\d\d)/,"$1,$2");
    }

    return value;
}

//========================================================================================
// �w�菬�������A�؂�グ
// �쐬�ҁFY.nishigawa
// �쐬���F2009/11/03
// ����  �F�@value �F�؂�グ���s���l
//       �F�Aketa  �F�w�茅��
// �߂�l�F�؂�グ����
// �T�v  �F�����ɑ΂��Ďw�菬�������A�؂�グ�ҏW���s��
//========================================================================================
function funShosuKiriage(value, keta){
    var i;
    var shosuten = 1;

    if(value == "" || isNaN(value) ){
        //�������Ȃ�
        return value;

    }else{

        //�J���}�폜
        value = value.replace(/,/g,"");

        //�����؂�グ
        for( i = 0; i < keta; i++ ){
            shosuten = shosuten * 10;
        }
        value = demicalFloat(value,shosuten,"*");
        value = (Math.ceil(value))/shosuten;

        //����0����
        value = "" + value;
        var insertZero = "";
        for( i = 0; i < keta; i++ ){
            insertZero = insertZero + "0";
        }

        if(value.split(".")[1]){
            value = value.split(".")[0]+"."+(value.split(".")[1]+insertZero).substring(0,keta);
        }else{
            value = value + "." + insertZero;
        }
    }

    return value;
}

//========================================================================================
// �w�菬�������A�؎̂�
// �쐬�ҁFY.nishigawa
// �쐬���F2009/11/03
// ����  �F�@value �F�؎̂Ă��s���l
//       �F�Aketa  �F�w�茅��
// �߂�l�F�؂�グ����
// �T�v  �F�����ɑ΂��Ďw�菬�������A�؎̂ĕҏW���s��
//========================================================================================
function funShosuKirisute(value, keta){
    var i;
    var shosuten = 1;

    if(value == "" || isNaN(value) ){
        //�������Ȃ�
        return value;

    }else{

        //�J���}�폜
        value = value.replace(/,/g,"");

        //�����؂�グ
        for( i = 0; i < keta; i++ ){
            shosuten = shosuten * 10;
        }
        value = demicalFloat(value,shosuten,"*");
        value = (Math.floor(value))/shosuten;

        //����0����
        value = "" + value;
        var insertZero = "";
        for( i = 0; i < keta; i++ ){
            insertZero = insertZero + "0";
        }

        if(value.split(".")[1]){
            value = value.split(".")[0]+"."+(value.split(".")[1]+insertZero).substring(0,keta);
        }else{
            value = value + "." + insertZero;
        }
    }

    return value;
}

//========================================================================================
// �R���{�{�b�N�X�N���A����
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@obj  �F�ΏۃI�u�W�F�N�g
//       �F�Amode �F���[�h
//           1:�擪�󔒍s�Ȃ��A2:�擪�󔒍s����
// �߂�l�F�Ȃ�
// �T�v  �F�R���{�{�b�N�X�̓��e���N���A����
//========================================================================================
function funClearSelect(obj, mode) {

    var objNewOption;

    obj.options.length = 0;

    if (mode == 2) {
        objNewOption = document.createElement("option");
        obj.options.add(objNewOption);
        objNewOption.innerText = "�@";
        objNewOption.value = "";
        obj.options.selectedIndex = 0;
    } else {
        obj.options.selectedIndex = -1;
    }

}

//========================================================================================
// �yQP@00342�z�X�e�[�^�X�ݒ�
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@obj  �F�ΏۃI�u�W�F�N�g
//       �F�Amode �F���[�h
//           1:�擪�󔒍s�Ȃ��A2:�擪�󔒍s����
// �߂�l�F�Ȃ�
// �T�v  �F�R���{�{�b�N�X�̓��e���N���A����
//========================================================================================
function funStatusSetting(busho, st) {

	//������
    if(busho == "1"){
    	//�˗�
    	if(st == "2"){
    		return "�˗�";
	    }
    }
    //���Y�Ǘ�
    else if(busho == "2"){
    	//�Ȃ�
    	if(st == "1"){
    		return "-";
	    }
	    //�˗�
	    else if(st == "2"){
	    	return "�˗�";
	    }
	    //����
	    else if(st == "3"){
	    	return "����";
	    }
    }
    //�����ޒ��B
    else if(busho == "3"){
    	//�Ȃ�
    	if(st == "1"){
    		return "-";
	    }
	    //����
	    else if(st == "2"){
	    	return "����";
	    }
    }
    //�H��
    else if(busho == "4"){
    	//�Ȃ�
    	if(st == "1"){
    		return "-";
	    }
	    //����
	    else if(st == "2"){
	    	return "����";
	    }
    }
    //�c��
    else if(busho == "5"){
    	//�Ȃ�
    	if(st == "1"){
    		return "-";
	    }
	    //�˗�
	    else if(st == "2"){
	    	return "�˗�";
	    }
	    //����
	    else if(st == "3"){
	    	return "����";
	    }
	    //�̗p
	    else if(st == "4"){
	    	return "�̗p";
	    }
    }
}
//========================================================================================
// �yQP@00342�z�X�e�[�^�X�ݒ�
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// ����  �F�@obj  �F�ΏۃI�u�W�F�N�g
//       �F�Amode �F���[�h
//           1:�擪�󔒍s�Ȃ��A2:�擪�󔒍s����
// �߂�l�F�Ȃ�
// �T�v  �F�R���{�{�b�N�X�̓��e���N���A����
//========================================================================================
function funStatusSetting_img(busho, st) {

	//������
    if(busho == "1"){
    	//�˗�
    	if(st == "2"){
    		return "kenkyu_2.GIF";
	    }
    }
    //���Y�Ǘ�
    else if(busho == "2"){
    	//�Ȃ�
    	if(st == "1"){
    		return "seikan_1.GIF";
	    }
	    //�˗�
	    else if(st == "2"){
	    	return "seikan_2.GIF";
	    }
	    //����
	    else if(st == "3"){
	    	return "seikan_3.GIF";
	    }
    }
    //�����ޒ��B
    else if(busho == "3"){
    	//�Ȃ�
    	if(st == "1"){
    		return "gentyo_1.GIF";
	    }
	    //����
	    else if(st == "2"){
	    	return "gentyo_2.GIF";
	    }
    }
    //�H��
    else if(busho == "4"){
    	//�Ȃ�
    	if(st == "1"){
    		return "kojo_1.GIF";
	    }
	    //����
	    else if(st == "2"){
	    	return "kojo_2.GIF";
	    }
    }
    //�c��
    else if(busho == "5"){
    	//�Ȃ�
    	if(st == "1"){
    		return "eigyo_1.GIF";
	    }
	    //�˗�
	    else if(st == "2"){
	    	return "eigyo_2.GIF";
	    }
	    //����
	    else if(st == "3"){
	    	return "eigyo_3.GIF";
	    }
	    //�̗p
	    else if(st == "4"){
	    	return "eigyo_4.GIF";
	    }
    }
}

//========================================================================================
// �����_�v�Z
// �쐬�ҁFY.Nishigawa
// �쐬���F2009/11/27
// ����  �F�@obj  �F�ΏۃI�u�W�F�N�g
//       �F�Amode �F���[�h
//           1:�擪�󔒍s�Ȃ��A2:�擪�󔒍s����
// �߂�l�F�Ȃ�
// �T�v  �F�R���{�{�b�N�X�̓��e���N���A����
//========================================================================================
function demicalFloat(numberA,numberB,type){

    var h = (type == "*")? "+" : "-";
    var c = [get(numberA), get(numberB)];
    var A = c[0][1];
    var B = c[1][1];
    var pointA = c[0][0];
    var pointB = c[1][0];

    if (type == "*" || type == "/"){

       var k1 = eval("numberA" + type + "numberB");
       var k2 = eval("(A" + type + "B)");
       if (get(k1)[1] == k2) return k1;
       else return (pointA + pointB == 0? k1 : eval(k2 + "/Math.pow(10, pointA" + h + "pointB)"));

    }
    else if (type == "+" || type == "-"){

        var pointL = pointA;
        if (pointA < pointB) pointL = pointB;
        numberA = demicalFloat(numberA, Math.pow(10, pointL), "*");
        numberB = demicalFloat(numberB, Math.pow(10, pointL), "*");
        return eval("numberA" + type + "numberB") / Math.pow(10, pointL);
    }
}
function get(number){
    number = "" + number;
    if (number.indexOf(".") == -1) return [0, eval(number)];
    var po = number.split(".")[1].length;
    var st = number.split(".").join("");
    for (var i = 0; i < st.length; i++) if (st.charAt(0) == "0") st = st.substr(1, st.length);

    //eval()�֐���023�`070�̕������^����ƒl�̂܂܋A��Ȃ��������Ŏ��ł���\���͂Ȃ��̂ŁAeval�֐��폜
    //return [po, eval(st)];
    return [po, st];

}

//ADD 2013/07/7 ogawa �yQP@30151�zNo.39 start
//========================================================================================
//�X�y�[�X�폜����
//�쐬�ҁFF.ogawa
//�쐬���F2013/07/07
//����  �F�@obj  �F�ΏۃI�u�W�F�N�g
//�߂�l�F�X�y�[�X�폜��̒l
//�T�v  �F��������̋󔒂��폜����
//========================================================================================
function funSpaceDel(obj) {

 var i;
 var retVal;

 //���������O�̏ꍇ�A������
 if (obj.value.length == 0) {
     return true;
 } else {
     retVal = obj.value;
 }

 //�X�y�[�X�폜
 retVal = retVal.replace(/[ �@]/gm, '');

 //�l��ݒ肷��
 obj.value = retVal;

 return true;

}

//========================================================================================
//�S�p�폜����
//�쐬�ҁFF.ogawa
//�쐬���F2013/07/07
//����  �F�@obj  �F�ΏۃI�u�W�F�N�g
//�߂�l�F�X�y�[�X�폜��̒l
//�T�v  �F��������̋󔒂��폜����
//========================================================================================
function funZenkakuDatDel(obj) {

	var data1 = new Array("�O","�P","�Q","�R","�S","�T","�U","�V","�W","�X","�|","");
	var data2 = new Array("0","1","2","3","4","5","6","7","8","9","-","");
	var i, ct;
	var inVal;
	var outVal="";
	var outVal2="";
	var dt;

	//���������O�̏ꍇ�A������
	if (obj.value.length == 0) {
		return;
	} else {
		inVal = obj.value;
	}

	//�S�p���l�𔼊p���l�ɕύX����
	//���������J��Ԃ�
	for(i=0; i < obj.value.length ; i++){
		dt = inVal.substring(i,i+1);
		//�Ώۃf�[�^�idata1���ɑ��݁j����r����
		for(ct=0; data1[ct].length > 0 ; ct++){
			//�Ώۃf�[�^�idata1���ɑ��݁j�Ȃ�A�Ή�����l(data2)�Ɠ���ւ���
			if(data1[ct] == dt){
				outVal = outVal + data2[ct];
				break;
			}
		}
		//����ւ��Ȃ������ꍇ�A���̒l��ݒ�
		if(0 == data1[ct].length){
			outVal = outVal + dt;
		}
	}

	//�Ώۃf�[�^�ȊO���폜����
	//���������J��Ԃ�
	for(i=0; i < outVal.length ; i++){
		dt = outVal.substring(i,i+1);
		//�Ώۃf�[�^�idata2���ɑ��݁j����r����
		for(ct=0; data2[ct].length > 0 ; ct++){
			//�Ώۃf�[�^�idata2���ɑ��݁j�Ȃ�A�ݒ肷��
			if(data2[ct] == dt){
				outVal2 = outVal2 + dt;
				break;
			}
		}
	}

//�l��ݒ肷��
	obj.value = outVal2;

return;

}//ADD 2013/07/7 ogawa �yQP@30151�zNo.39 end

//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.14 start
//========================================================================================
//�yQP@40812�z�w���v��ʂ�\��
// �쐬�ҁFE.Kitazawa
// �쐬���F2015/03/03
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�w���v��ʂ�\������
//========================================================================================
function funHelpCall() {

	//�w���v�\���i���������ׂɈ������Ŏ��s�j

	hndl = setInterval("funOpenHelp()",1000);
	return true;
}

//========================================================================================
// �쐬�ҁFE.Kitazawa
// �쐬���F2015/03/03
// ����  �F�Ȃ�
// �߂�l�F�Ȃ�
// �T�v  �F�w���v��ʂ�\������
//========================================================================================
function funOpenHelp() {

	var frm = document.frm00;    //̫�тւ̎Q��
	var helppath = frm.strHelpPath.value;

	// ���ݒ�̏ꍇ�A�f�t�H���g�y�[�W��\��
	if (helppath == null || helppath == "") {
		return false;
	}

	//�E�B���h�E�̐ݒ�
	var w = screen.availWidth-10;
	var h = screen.availHeight-30;
	//�E�B���h�E�I�[�v��
	var nwin=window.open(helppath,"HelpPage","menubar=no,resizable=yes,scrollbars=yes,width="+w+",height="+h+",left=0,top=0");
	nwin.document.title="�������Z�V�X�e���@�w���v���";
	//�������Ŏ��s���Ă������̂�����
	clearInterval(hndl);
}
//ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.14 end

//�yKPX@1602367�z2016/09/16 May Thu add start
//========================================================================================
// ���O�C�����\��
// �쐬�ҁFMay Thu
// �쐬���F2016/09/16
// �T�v  �F�������\���p��HTML���𐶐��A�o�͂���B
//========================================================================================

function funCountInfoDisplay(count,ObjectId) {
	var Count = count;       //��������
	var OutputHtml;       //�o��HTML
    obj = document.getElementById(ObjectId);

    //�����̎擾
 	OutputHtml = "<table align=\"left\" width=\"300\">";
	OutputHtml += "    <tr style=\"text-align:left;font-size:12px;\">";
	OutputHtml += "    <td>�Y���f�[�^������" + Count + "�ł��B</td>";
	OutputHtml += "    </tr>";
    OutputHtml += "</table>";
	//HTML���o��
    obj.innerHTML = OutputHtml;
    return true;
 }
//�yKPX@1602367�z2016/09/16 May Thu add end

