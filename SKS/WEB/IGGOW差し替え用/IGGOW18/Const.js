//========================================================================================
// �萔�錾 [Const.js]
// �쐬�ҁFM.Jinbo
// �쐬���F2009/03/13
// �T�v  �F�萔�̐錾���s���B
//========================================================================================

    //JRE1.4.2�C���X�g�[���[URL  �������ɍ��킹�ĕύX����
    //var ConJreInstallUrl = "http://shisaquick/Shisaquick/j2re-1_4_2_19-windows-i586-p.exe";
    // 20120607 hagiwara �V�T�N�C�b�N�T�[�o�ڍs�����̈�
    var ConJreInstallUrl = "http://vo-shisaquick:4043/Shisaquick/j2re-1_4_2_19-windows-i586-p.exe";

    //JRE1.5.0�C���X�g�[���[URL  �������ɍ��킹�ĕύX����
    //var ConJreInstallUrl_5 = "http://shisaquick/Shisaquick/jre-1_5_0_14-windows-i586-p.exe";

    //JRE1.6.0�C���X�g�[���[URL  �������ɍ��킹�ĕύX����
    //var ConJreInstallUrl_6 = "http://shisaquick/Shisaquick/jre-6u31-windows-i586-s.exe";
    // 20120607 hagiwara �V�T�N�C�b�N�T�[�o�ڍs�����̈�
    var ConJreInstallUrl_6 = "http://vo-shisaquick:4043/Shisaquick/jre-6u31-windows-i586-s.exe";

    //JavaWebStart�̃o�[�W�����`�F�b�N()
    var ConCheckJWSver = "1.6.0.0:::1.7.0.0";

    //Servlet�Ăяo���pURL�p�X(�ŏ��ƍŌ�̃X���b�V���͕s�v)  �������ɍ��킹�ĕύX����
    var ConUrlPath = "Shisaquick";

    //��؂蕶��
    var ConDelimiter = ":::";

    //�V�K�����R�[�h�̐擪����
    var ConGenryoCdPrefix = "N";

//+----------------------------------------------------------------------------
//  �V�X�e������`
//+----------------------------------------------------------------------------
    //���ID
    var ConLoginId         = "SQ010";       //���O�C��
    var ConMainMenuId      = "SQ020";       //���C�����j���[
    var ConMstMenuId       = "SQ030";       //�}�X�^���j���[
    var ConShisakuListId   = "SQ040";       //����f�[�^�ꗗ
    var ConGenryoInfoMstId = "SQ050";       //�������͏��}�X�^�����e�i���X
    var ConGenryoInputId   = "SQ051";       //���͒l����
    var ConLiteralMstId    = "SQ060";       //���e�����}�X�^�����e�i���X
    var ConGroupMstId      = "SQ070";       //�O���[�v�}�X�^�����e�i���X
    var ConTantoMstId      = "SQ080";       //�S���҃}�X�^�����e�i���X
    var ConTantoSearchId   = "SQ081";       //�S���Ҍ���
    var ConKasihaAddId     = "SQ082";       //�����S����Вǉ�
    var ConKengenMstId     = "SQ090";       //�����}�X�^�����e�i���X
    var ConKengenAddId     = "SQ091";       //�����@�\�ǉ�
    var ConTankaBudomariId = "SQ100";       //�S�H��P������
    var GenkaSisanId       = "SQ110";       //�������Z���
    var RuizihinKensakuId  = "SQ111";       //�ގ��i�������
    //�yQP@00342�z
    var ConGenkaListId   = "SQ120";       //�������Z�ꗗ
    var ConStatusRirekiId  = "SQ140";       //�X�e�[�^�X�������
    var ConStatusClearId   = "SQ130";        //�X�e�[�^�X�N���A���
    var ConEigyoTantoMstId   = "SQ150";        //�S���҃����e�i�c�Ɓj���
    var ConEigyoTantoSearchId   = "SQ151";       //�S���Ҍ���
    

    /****************************/
    /*    �^ �C �g �� �� ��     */
    /****************************/
    //�V�X�e��ID(�V�T�N�C�b�N)
    var ConSystemId = "�V�T�N�C�b�N�V�X�e��(04_0043_0001)�@";
    //�yQP@00342�z
    var ConSystemId_genka = "�������Z�V�X�e���@";

    //���ID(�^�C�g��)
    var ConLogin         = ConLoginId + "Login_���O�C��";
    var ConMainMenu      = ConMainMenuId + "MainMenu_���C�����j���[";
    var ConMstMenu       = ConMstMenuId + "MstMenu_�}�X�^���j���[";
    var ConShisakuList   = ConShisakuListId + "ShisakuList_����f�[�^�ꗗ";
    var ConGenryoInfoMst = ConGenryoInfoMstId + "GenryoInfoMst_�������͏��}�X�^�����e�i���X";
    var ConGenryoInput   = ConGenryoInputId + "GenryoInput_���͒l����";
    var ConLiteralMst    = ConLiteralMstId + "LiteralMst_�J�e�S���}�X�^�����e�i���X";
    var ConGroupMst      = ConGroupMstId + "GroupMst_�O���[�v�}�X�^�����e�i���X";
    var ConTantoMst      = ConTantoMstId + "TantoMst_�S���҃}�X�^�����e�i���X";
    var ConTantoSearch   = ConTantoSearchId + "TantoSearch_�S���Ҍ���";
    var ConKasihaAdd     = ConKasihaAddId + "KasihaAdd_�����S����Вǉ�";
    var ConKengenMst     = ConKengenMstId + "KengenMst_�����}�X�^�����e�i���X";
    var ConKengenAdd     = ConKengenAddId + "KengenAdd_�����@�\�ǉ�";
    var ConTankaBudomari = ConTankaBudomariId + "TankaBudomari_�S�H��P������";
    //�yQP@00342�z
    var ConGenkaList = ConGenkaListId + "ShisanList_�������Z�ꗗ";
    var ConStatusRireki = ConStatusRirekiId + "StatusRireki_�X�e�[�^�X����";
    var ConStatusClear  = ConStatusClearId + "StatusClear_�X�e�[�^�X�N���A";
    var ConEigyoTantoMst  = ConEigyoTantoMstId + "EigyoTantoMst_�S���҃}�X�^�����e�i���X�i�c�Ɓj";
    var ConEigyoTantoSearch   = ConEigyoTantoSearchId + "EigyoTantoSearch_�S���Ҍ����i�c�Ɓj";

    /****************************/
    /*        �� �� �� ��       */
    /****************************/
    //����(���ID)
    var ConGmnIdShisakuList     = "10";    //����f�[�^�ꗗ
    var ConGmnIdGenryoInfoMst   = "20";    //�������͏��}�X�^
    var ConGmnIdGenryoInfoCsv   = "25";    //�������͏��}�X�^CSV
    var ConGmnIdGenryoInputNew  = "30";    //���͒l����(�V�K)
    var ConGmnIdGenryoInputUpd  = "40";    //���͒l����(�ڍ�)
    var ConGmnIdTankaBudomari   = "50";    //�S�H��P������
    var ConGmnIdLiteralMst      = "60";    //���e�����}�X�^
    var ConGmnIdLiteralCsv      = "65";    //���e�����}�X�^CSV
    var ConGmnIdGroupMst        = "70";    //�O���[�v�}�X�^
    var ConGmnIdKengenMst       = "80";    //�����}�X�^
    var ConGmnIdTantoMst        = "90";    //�S���҃}�X�^
    var ConGmnIdShisakuDataEdit = "100";   //����f�[�^���(�ڍ�)
    var ConGmnIdShisakuDataNew  = "110";   //����f�[�^���(�V�K)
    var ConGmnIdSeihoCopy       = "120";   //����f�[�^���(���@�x���R�s�[)
    var ConGmnIdGenkaShisan     = "170";   //�������Z���
    //�yQP@00342�z
    var ConGmnIdGenkaShisanItiran     = "180";   //�������Z�ꗗ���
    var ConGmnIdGenkaShisanEigyo     = "190";   //�������Z��ʁi�c�Ɓj
    var ConGmnIdEigyoTantoMst     = "200";   //�S���҃}�X�^�i�c�Ɓj
    

    //����(�@�\ID)
    var ConFuncIdRead   = "10";    //�{��
    var ConFuncIdEdit   = "20";    //�ҏW
    var ConFuncIdEdit2  = "21";    //�ҏW(�����߽ܰ�ނ̂�)
    var ConFuncIdNew    = "30";    //�V�K
    var ConFuncIdAll    = "40";    //�ҏW(�S��)
    var ConFuncIdSysMgr = "50";    //�V�X�e���Ǘ��җp
    var ConFuncIdGenkaShisan = "60";    //�������Z
    var ConFuncIdReadExcel = "70"; //�{���iExcel�o�́j
    //�yQP@00342�z
    var ConFuncIdItiranRead = "80"; //�{��
    var ConFuncIdGenkaEditEigyo = "90"; //�ҏW
    var ConFuncIdEigyoTantoEditIppan = "100"; //�ҏW�i��ʁj
    var ConFuncIdEigyoTantoEditKari = "101"; //�ҏW�i���o�^���[�U�j
    var ConFuncIdEigyoTantoEditHonbu = "102"; //�ҏW�i�{�������j
    var ConFuncIdEigyoTantoEditSystem = "103"; //�ҏW�i�V�X�e���Ǘ��ҁj
    
    

    /****************************/
    /*      �� �� �� �� ��      */
    /****************************/
    //�@�\ID(���ʏ��擾�p)
    var ConUserInfo = "USERINFO";
    var ConResult   = "RESULT";

    //�ʐM�p�^�[��
    var ConConectGet  = "GET";
    var ConConectPost = "POST";

    //���b�Z�[�W�{�b�N�X�߂�l
    var ConBtnYes    = "yes";        //�͂�
    var ConBtnNo     = "no";         //������
    var ConBtnClose  = "close";      //����
    var ConBtnCancel = "cancel";     //�L�����Z��
    var ConBtnAbort  = "abort";      //���~

    //�J���[�w��
    var activeSelectedColor   = "aquamarine";    //�ꗗ�I�����(�A�N�e�B�u��)
    var deactiveSelectedColor = "white";         //�ꗗ�I�����(��A�N�e�B�u��)
    var haishiRowColor = "gray";                 //�ꗗ�I�����(�p�~��)

    //�Z�b�V�����؂�G���[���b�Z�[�W�R�[�h
    var ConSessionErrCd = "E000101";

    //�^�C�}�[����
    var ConTimer = 100;
    
    //�����˗��L��
    var GenkaMark = "$";
    
    //�������Z��ʁi�̗p�T���v��No�F�̗p�����j
    var SaiyouNashiValue = "-1";
    
    //�������Z��ʁi�ҏW�ہj
    var henshuOkColor = "#ffff88";
    var henshuNgColor = "#ffffff";
    var henshuOkClass = "table_text_disb";
    var henshuNgClass = "table_text_view";
