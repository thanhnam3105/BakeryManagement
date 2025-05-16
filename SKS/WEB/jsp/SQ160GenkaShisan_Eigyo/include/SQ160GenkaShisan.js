//========================================================================================
// 共通変数
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
//========================================================================================
//色指定
 var color_read_font="#0060FF";
 var color_read="#ffffff";
 var color_henshu="#ffff88";
 var hiduke = "";

//========================================================================================
// 初期処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：画面の初期処理を行う
//========================================================================================
function funLoad() {

    return true;

}


//========================================================================================
// ヘッダーフレーム、初期表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad_head() {

    //ﾌｫｰﾑへの参照
    var frm = document.frm00;

    //共通情報表示
    funGetKyotuInfo(1);

    return true;

}

//========================================================================================
// 明細フレーム、初期表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad_dtl() {

    //基本情報表示
    funKihonHyoji();

    return true;

}

//========================================================================================
// 明細フレーム、基本情報表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：画面の基本情報表示処理を行う
//========================================================================================
function funKihonHyoji() {

    //基本情報取得
    funGetKihonInfo(1);

    return true;

}

//========================================================================================
// 明細フレーム、原料情報表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：画面の原料情報表示処理を行う
//========================================================================================
function funGenryoHyoji() {

    //原料情報取得
    funGetGenryoInfo(1);

    return true;

}

//========================================================================================
// 明細フレーム、資材情報表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：画面の資材情報表示処理を行う
//========================================================================================
function funShizaiHyoji() {

    //資材情報取得
    funGetShizaiInfo(1);

    return true;

}


//========================================================================================
// 原価試算、共通情報取得＆表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：原価試算、共通情報取得
//========================================================================================
function funGetKyotuInfo(mode) {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN0010";
    // MOD 2013/10/22 QP@30154 okano start
//	    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0010","FGEN2020");
//	    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0010I , xmlFGEN2020I );
//	    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0010O , xmlFGEN2020O );
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0010","FGEN2020","FGEN2130");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0010I , xmlFGEN2020I , xmlFGEN2130I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0010O , xmlFGEN2020O , xmlFGEN2130O );
    // MOD 2013/10/22 QP@30154 okano end


    //------------------------------------------------------------------------------------
    //                                  共通情報取得
    //------------------------------------------------------------------------------------
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0010, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }


    //------------------------------------------------------------------------------------
    //                                    権限設定
    //------------------------------------------------------------------------------------
    funSaveKengenInfo();


    //------------------------------------------------------------------------------------
    //                                  共通情報表示
    //------------------------------------------------------------------------------------
    //ﾕｰｻﾞ情報表示
    funInformationDisplay(xmlResAry, 110, "divUserInfo");


    //試作コード表示
    frm.txtShainCd.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_shain", 0, 0);
    frm.txtNen.value = funXmlRead_3(xmlResAry[2], "kihon", "nen", 0, 0);
    frm.txtOiNo.value = funXmlRead_3(xmlResAry[2], "kihon", "no_oi", 0, 0);
    frm.txtEdaNo.value = funXmlRead_3(xmlResAry[2], "kihon", "no_eda", 0, 0);

    //品名表示
    frm.txtHinNm.value = funXmlRead_3(xmlResAry[2], "kihon", "hin", 0, 0);

    //採用サンプルNo設定
    frm.txtSaiyou.value = funXmlRead_3(xmlResAry[2], "kihon", "saiyo_nm", 0, 0);

    //試算期日設定
    frm.txtKizitu.value = funXmlRead_3(xmlResAry[2], "kihon", "dt_kizitu", 0, 0);
    hiduke = funXmlRead_3(xmlResAry[2], "kihon", "dt_kizitu", 0, 0);

    //枝番種類設定
    frm.txtShuruiEda.value = funXmlRead_3(xmlResAry[2], "kihon", "shurui_eda", 0, 0);

    // 依頼番号設定
    divIraiNo.innerText = "IR@" + funXmlRead_3(xmlResAry[2], "kihon", "no_irai", 0, 0);


    //各項目の設定（ヘッダ部分のHidden項目）
    frm.strHaitaCdShisaku.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_shisaku_haita", 0, 0);
    frm.strHaitaNmShisaku.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_shisaku_haita", 0, 0);
    frm.strHaitaKaisha.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_kaisha_haita", 0, 0);
    frm.strHaitaBusho.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_busho_haita", 0, 0);
    frm.strHaitaUser.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_user_haita", 0, 0);
    frm.strKengenMoto.value = funXmlRead_3(xmlResAry[2], "kihon", "kengen_moto", 0, 0);

    //現在ｽﾃｰﾀｽの設定
    frm.hdnStatus_kenkyu.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0);
    frm.hdnStatus_seikan.value = funXmlRead_3(xmlResAry[3], "kihon", "st_seisan", 0, 0);
    frm.hdnStatus_gentyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_gensizai", 0, 0);
    frm.hdnStatus_kojo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kojo", 0, 0);
    frm.hdnStatus_eigyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_eigyo", 0, 0);
    // ADD 2013/10/22 QP@30154 okano start
    //所属部署フラグ設定
    frm.hdnBusho_kenkyu.value = funXmlRead_3(xmlResAry[4], "table", "flg_kenkyu", 0, 0);
	frm.hdnBusho_seikan.value = funXmlRead_3(xmlResAry[4], "table", "flg_seikan", 0, 0);
	frm.hdnBusho_gentyo.value = funXmlRead_3(xmlResAry[4], "table", "flg_gentyo", 0, 0);
	frm.hdnBusho_kojo.value = funXmlRead_3(xmlResAry[4], "table", "flg_kojo", 0, 0);
	frm.hdnBusho_eigyo.value = funXmlRead_3(xmlResAry[4], "table", "flg_eigyo", 0, 0);
    // ADD 2013/10/22 QP@30154 okano end

    //メッセージボックス表示
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;
    //排他の場合
    if(frm.strKengenMoto.value == "999"){
        //表示
        funHaitaDisp();

        headerFrm.btnToroku.disabled = true;
    }

    // ヘルプの表示
    frm.strHelpPath.value = funXmlRead_3(xmlResAry[2], "kihon", "help_file", 0, 0);

    //【シサクイックH24年度対応】修正５ 2012/04/16 Add Start
    frm.hdnSaiyou_column.value = funXmlRead_3(xmlResAry[2], "kihon", "saiyo_no", 0, 0);
    //【シサクイックH24年度対応】修正５ 2012/04/16 Add End

    //------------------------------------------------------------------------------------
    //                                 明細ﾌﾚｰﾑ描画
    //------------------------------------------------------------------------------------
    //明細ﾌﾚｰﾑの描画
    parent.detail.location.href="GenkaShisan_dtl_Eigyo.jsp";

    //処理終了
    return true;
}
function funHaitaDisp() {
    //ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;
    //各項目の取得
    var cd_shisaku = headerFrm.strHaitaCdShisaku.value;
    var nm_shisaku = headerFrm.strHaitaNmShisaku.value;
    var kaisha = headerFrm.strHaitaKaisha.value;
    var busho = headerFrm.strHaitaBusho.value;
    var user = headerFrm.strHaitaUser.value;
    //排他情報表示
    alert("下記ユーザが使用中です。\n" + "試作CD：" + cd_shisaku + "\n試作名：" + nm_shisaku + "\n会社 ： " + kaisha + "\n部署 ： " + busho + "\n氏名 ： " + user);
    return true;
}
function funHaitaDisp_btn() {
    //ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;

    //承認ボタンにフォーカスセット
    parent.header.focus();

    //各項目の取得
    var cd_shisaku = headerFrm.strHaitaCdShisaku.value;
    var nm_shisaku = headerFrm.strHaitaNmShisaku.value;
    var kaisha = headerFrm.strHaitaKaisha.value;
    var busho = headerFrm.strHaitaBusho.value;
    var user = headerFrm.strHaitaUser.value;
    var Kengen = headerFrm.strKengenMoto.value;
    //排他の場合
    if(Kengen.toString() == "999"){
        //排他情報表示
        alert("下記ユーザが使用中です。\n" + "試作CD：" + cd_shisaku + "\n試作名：" + nm_shisaku + "\n会社 ： " + kaisha + "\n部署 ： " + busho + "\n氏名 ： " + user);
    }else{
        alert("他に参照されている方はいません。");
    }
    return true;
}

//========================================================================================
// ｽﾃｰﾀｽ履歴画面を起動
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
//========================================================================================
function funStatusRireki_btn() {

	//ﾌｫｰﾑへの参照
	var headerFrm = parent.header.document.frm00;

    var XmlId = "RGEN2160";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

    //承認ボタンにフォーカスセット
    parent.header.focus();

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2160, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

   	//ステータス履歴へ遷移
    window.open("../SQ140StatusRireki/SQ140StatusRireki.jsp","_blank","menubar=no,resizable=yes");

    return true;

}



//========================================================================================
// 原価試算、基本情報取得＆表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：原価試算、基本情報取得
//========================================================================================
function funGetKihonInfo(mode) {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN0011";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0011");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0011I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0011O );
    var Kengen;
    //【QP@10713】シサクイック改良 No13
    var Nisugata;


    //------------------------------------------------------------------------------------
    //                                  基本情報取得
    //------------------------------------------------------------------------------------
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0011, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                  基本情報表示
    //------------------------------------------------------------------------------------

    //------------------------------------ 所属グループ ----------------------------------
    frm.txtGroup.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_gurupu", 0, 0);


    //------------------------------------- 所属チーム -----------------------------------
    frm.txtTeam.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_chi-mu", 0, 0);


    //------------------------------------- 一括表示 -------------------------------------
    frm.txtIkkatu.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_ikatu", 0, 0);


    //-------------------------------------- ユーザ --------------------------------------
    frm.txtUser.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_user", 0, 0);


    //--------------------------------------- 用途 ---------------------------------------
    frm.txtYouto.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_yoto", 0, 0);


// ADD 2013/10/22 QP@30154 okano start
    //------------------------------------- 販責会社 -------------------------------------
    frm.txtHanseki.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_hanseki", 0, 0);
// ADD 2013/10/22 QP@30154 okano end
    //------------------------------------- 製造会社 -------------------------------------
    frm.txtSeizoKaisha.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_kaisya", 0, 0);


    //------------------------------------- 製造工場 -------------------------------------
    frm.txtSeizoKojo.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_jojyo", 0, 0);


    //------------------------------------- 担当営業 -------------------------------------
    frm.txtTantoEigyo.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_tantoegyo", 0, 0);


    //------------------------------------- 製造方法 -------------------------------------
    frm.txtSeizohoho.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_seizohoho", 0, 0);


    //------------------------------------- 充填方法 -------------------------------------
    frm.txtJutenhoho.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_jyutenhoho", 0, 0);


    //------------------------------------- 殺菌方法 -------------------------------------
    frm.txtSakinhoho.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_sakinhoho", 0, 0);


    //------------------------------------ 容器・包材 ------------------------------------
    frm.txtYouki.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_yoki", 0, 0);


    //---------------------------------- 容量（数値入力）---------------------------------
    frm.txtYouryo.value = funXmlRead_3(xmlResAry[2], "kihon", "yoryo", 0, 0);
    //【QP@10713】シサクイック改良 No.13
    Nisugata = funXmlRead_3(xmlResAry[2], "kihon", "yoryo", 0, 0);
    setKanma(frm.txtYouryo);


    //---------------------------------- 容量（単位）---------------------------------
    frm.txtYouryo_tani.value = funXmlRead_3(xmlResAry[2], "kihon", "yoryo_tani", 0, 0);
    //【QP@10713】シサクイック改良 No.13
    Nisugata = Nisugata + " " + funXmlRead_3(xmlResAry[2], "kihon", "yoryo_tani", 0, 0);


    //-------------------------------------- 入り数 --------------------------------------
    frm.txtIrisu.value = funXmlRead_3(xmlResAry[2], "kihon", "irisu", 0, 0);
    //【QP@10713】シサクイック改良 No.13
    Nisugata = Nisugata + " / " +  funXmlRead_3(xmlResAry[2], "kihon", "irisu", 0, 0);
    setKanma(frm.txtIrisu);


    //--------------------------------------- 荷姿 ---------------------------------------
    //【QP@10713】シサクイック改良 No.13
    if(funXmlRead_3(xmlResAry[2], "kihon", "nm_nisugata", 0, 0) == null){
    	frm.txtNisugata.value = Nisugata;
    }else{
    	frm.txtNisugata.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_nisugata", 0, 0);
    }


    //------------------------------------- 取扱温度 -------------------------------------
    frm.txtOndo.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_ondo", 0, 0);


    //------------------------------------- 賞味期間 -------------------------------------
    frm.txtShomiKikan.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_kikan", 0, 0);


// DEL 2013/7/2 shima【QP@30151】No.37 start
//    //------------------------------------- 原価希望 -------------------------------------
//    frm.txtGenkaKibo.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_genka", 0, 0);
//    setKanma(frm.txtGenkaKibo);
//
//
//    //----------------------------------- 原価希望単位 -----------------------------------
//    //原価希望単位コンボボックス生成
//    funCreateComboBox(frm.ddlGenkaTani , xmlResAry[2] , 4, 1);
//    //原価希望単位コンボボックス選択
//    funDefaultIndex(frm.ddlGenkaTani, 4);
//
//
//    //------------------------------------- 売価希望 -------------------------------------
//    frm.txtBaikaKibo.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_baika", 0, 0);
//    setKanma(frm.txtBaikaKibo);
//
//
//    //----------------------------------- 売価希望単位 -----------------------------------
//    frm.txtBaikaTani.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_genka_nm_tani", 0, 0);
//
//
//    //------------------------------------- 想定物量 -------------------------------------
//    frm.txtSoteiButuryo.value = funXmlRead_3(xmlResAry[2], "kihon", "soote_buturyo", 0, 0);
//
//
//    //------------------------------------- 発売時期 -------------------------------------
//    frm.txtHatubaiJiki.value = funXmlRead_3(xmlResAry[2], "kihon", "ziki_hatubai", 0, 0);
//
//
//    //------------------------------------- 販売期間 -------------------------------------
//    //販売期間（通年orスポット）コンボボックス生成
//    funCreateComboBox(frm.ddlHanbaiKikan_t , xmlResAry[2] , 6, 1);
//    funDefaultIndex(frm.ddlHanbaiKikan_t, 5);
//
//    //販売期間（数値）
//    frm.txtHanbaiKikan_s.value = funXmlRead_3(xmlResAry[2], "kihon", "kikan_hanbai_suti", 0, 0);
//
//    //販売期間（ヶ月）コンボボックス生成
//    funCreateComboBox(frm.ddlHanbaiKikan_k , xmlResAry[2] , 7, 1);
//    funDefaultIndex(frm.ddlHanbaiKikan_k, 6);
//
//
//    //------------------------------------- 計画売上 -------------------------------------
//    frm.txtKeikakuUriage.value = funXmlRead_3(xmlResAry[2], "kihon", "keikaku_uriage", 0, 0);
//
//
//    //------------------------------------- 計画利益 -------------------------------------
//    frm.txtKeikakuRieki.value = funXmlRead_3(xmlResAry[2], "kihon", "keikaku_rieki", 0, 0);
//
//
//    //------------------------------------ 販売後売上 ------------------------------------
//    frm.txtHanbaigoUriage.value = funXmlRead_3(xmlResAry[2], "kihon", "hanbaigo_uriage", 0, 0);
//
//
//    //------------------------------------ 販売後利益 ------------------------------------
//    frm.txtHanbaigoRieki.value = funXmlRead_3(xmlResAry[2], "kihon", "hanbaigo_rieki", 0, 0);
//
//
//    //------------------------------------ 製造ロット ------------------------------------
//    frm.txtSeizoRot.value = funXmlRead_3(xmlResAry[2], "kihon", "seizo_roto", 0, 0);
// DEL 2013/7/2 shima【QP@30151】No.37 end

	// ADD 2013/7/2 shima【QP@30151】No.37 start
	//サンプル毎の基本情報表示
    funKihonSubDisplay(xmlResAry[2],"divKihonSub");

    var recCnt;
    var i;
    //列数取得
    recCnt = frm.cnt_sample.value;

    for(i = 0; i < recCnt;i++){
    	//------------------------------------- 原価希望 -------------------------------------
    	setKanma(document.getElementById("txtGenkaKibo" + i));

        //----------------------------------- 原価希望単位 -----------------------------------
        //原価希望単位コンボボックス生成
        funCreateComboBox(document.getElementById("ddlGenkaTani" + i) , xmlResAry[2] , 4, 1);

        //原価希望単位コンボボックス選択
        funDefaultIndex(document.getElementById("ddlGenkaTani" + i), 4);

        //------------------------------------- 売価希望 -------------------------------------
        setKanma(document.getElementById("txtBaikaKibo" + i));

        //------------------------------------- 販売期間 -------------------------------------
        //販売期間（通年orスポット）コンボボックス生成
        funCreateComboBox(document.getElementById("ddlHanbaiKikan_t" + i) , xmlResAry[2] , 6, 1);
        funDefaultIndex(document.getElementById("ddlHanbaiKikan_t" + i), 5);

        //販売期間（ヶ月）コンボボックス生成
        funCreateComboBox(document.getElementById("ddlHanbaiKikan_k" + i) , xmlResAry[2] , 7, 1);
        funDefaultIndex(document.getElementById("ddlHanbaiKikan_k" + i), 6);

        // ADD 2013/12/4 okano【QP@30154】start
        setKanma(document.getElementById("txtSoteiButuryo_s" + i));
        // ADD 2013/12/4 okano【QP@30154】end
        // ADD 2013/9/6 okano【QP@30151】No.30 start
        //------------------------------------- 想定物量 -------------------------------------
        funCreateComboBox(document.getElementById("ddlSoteiButuryo_u" + i) , xmlResAry[2] , 8, 1);
        funDefaultIndex(document.getElementById("ddlSoteiButuryo_u" + i), 7);

        funCreateComboBox(document.getElementById("ddlSoteiButuryo_k" + i) , xmlResAry[2] , 9, 1);
        funDefaultIndex(document.getElementById("ddlSoteiButuryo_k" + i), 8);
        // ADD 2013/9/6 okano【QP@30151】No.30 end

    }
	// ADD 2013/7/2 shima【QP@30151】No.37 end

    //----------------------------------- 原価試算メモ-------------------------------
    //frm.txtGenkaMemo.value = funXmlRead_3(xmlResAry[2], "kihon", "memo_genkashisan", 0, 0);


    //----------------------------- 原価試算メモ（営業連絡用） --------------------------
    frm.txtGenkaMemoEigyo.value = funXmlRead_3(xmlResAry[2], "kihon", "memo_genkashisan_eigyo", 0, 0);


    //------------------------------------ コード桁数 -------------------------------------
    frm.hdnCdketasu.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_ketasu", 0, 0);

    //------------------------------------------------------------------------------------
    //                            原料情報表示イベント発生
    //------------------------------------------------------------------------------------
    //原料情報表示イベントを発生させる
    frm.BtnEveGenryo.fireEvent('onclick');

    //画面制御
    kengen_statusSetting();

    //処理終了
    return true;

}


//========================================================================================
// 権限編集、ｽﾃｰﾀｽ編集
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
//========================================================================================
function kengen_statusSetting(){

    var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

    //権限取得
    var Kengen = headerFrm.strKengenMoto.value;

	// ADD 2013/7/2 shima【QP@30151】No.37 start
	var recCnt;
    var i;

    // ADD 2013/10/22 QP@30154 okano start
    //部署取得
    var kenkyu = headerFrm.hdnBusho_kenkyu.value;
    var seikan = headerFrm.hdnBusho_seikan.value;
    var gentyo = headerFrm.hdnBusho_gentyo.value;
    var kojo = headerFrm.hdnBusho_kojo.value;
    var eigyo = headerFrm.hdnBusho_eigyo.value;
    // ADD 2013/10/22 QP@30154 okano end

//20160628  KPX@1502111_No.10 ADD start
    //サンプルコピーボタン権限の設定
    // 閲覧権限の時使用不可
    kengenBottunSetting(detailFrm.btnSampleCopy);
    //営業採用以降は使用不可
    if (headerFrm.hdnStatus_eigyo.value > 3) {
    	detailFrm.btnSampleCopy.disabled = true;
    }
//20160628  KPX@1502111_No.10 ADD end

    //列数取得
    recCnt = detailFrm.cnt_sample.value;
    // ADD 2013/7/2 shima【QP@30151】No.37 end

    //排他
    if(Kengen.toString() == "999"){

    	//各コンポーネント制御
    	headerFrm.btnToroku.disabled=true;

        //20160628  KPX@1502111_No.10 ADD start
    	detailFrm.btnSampleCopy.disabled = true;
        //20160628  KPX@1502111_No.10 ADD end

    	headerFrm.txtKizitu.disabled=true;
    	headerFrm.txtKizitu.style.backgroundColor=color_read;

    	detailFrm.txtYouryo.disabled=true;
    	detailFrm.txtYouryo.style.backgroundColor=color_read;

    	detailFrm.txtIrisu.disabled=true;
    	detailFrm.txtIrisu.style.backgroundColor=color_read;

    	detailFrm.txtNisugata.disabled=true;
    	detailFrm.txtNisugata.style.backgroundColor=color_read;

		// MOD 2013/7/2 shima【QP@30151】No.37 start
    	for(i = 0 ; i < recCnt ; i++){

//    		detailFrm.txtGenkaKibo.disabled=true;
//    		detailFrm.txtGenkaKibo.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtGenkaKibo" + i).disabled=true;
    		detailDoc.getElementById("txtGenkaKibo" + i).style.backgroundColor=color_read;

//    		detailFrm.ddlGenkaTani.disabled=true;
//    		detailFrm.ddlGenkaTani.style.backgroundColor=color_read;
    		detailDoc.getElementById("ddlGenkaTani" + i).disabled=true;
    		detailDoc.getElementById("ddlGenkaTani" + i).style.backgroundColor=color_read;

//    		detailFrm.txtBaikaKibo.disabled=true;
//    		detailFrm.txtBaikaKibo.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtBaikaKibo" + i).disabled=true;
    		detailDoc.getElementById("txtBaikaKibo" + i).style.backgroundColor=color_read;

			// ADD 2013/9/6 okano【QP@30151】No.30 start
    		detailDoc.getElementById("txtSoteiButuryo_s" + i).disabled=true;
    		detailDoc.getElementById("txtSoteiButuryo_s" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).disabled=true;
    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).disabled=true;
    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).style.backgroundColor=color_read;
    		// ADD 2013/9/6 okano【QP@30151】No.30 end

//    		detailFrm.txtSoteiButuryo.disabled=true;
//    		detailFrm.txtSoteiButuryo.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtSoteiButuryo" + i).disabled=true;
    		detailDoc.getElementById("txtSoteiButuryo" + i).style.backgroundColor=color_read;

//    		detailFrm.txtHatubaiJiki.disabled=true;
//    		detailFrm.txtHatubaiJiki.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtHatubaiJiki" + i).disabled=true;
    		detailDoc.getElementById("txtHatubaiJiki" + i).style.backgroundColor=color_read;

//    		detailFrm.ddlHanbaiKikan_t.disabled=true;
//    		detailFrm.ddlHanbaiKikan_t.style.backgroundColor=color_read;
    		detailDoc.getElementById("ddlHanbaiKikan_t" + i).disabled=true;
    		detailDoc.getElementById("ddlHanbaiKikan_t" + i).style.backgroundColor=color_read;

//    		detailFrm.txtHanbaiKikan_s.disabled=true;
//    		detailFrm.txtHanbaiKikan_s.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtHanbaiKikan_s" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_read;

//    		detailFrm.ddlHanbaiKikan_k.disabled=true;
//    		detailFrm.ddlHanbaiKikan_k.style.backgroundColor=color_read;
    		detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled=true;
    		detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_read;

//    		detailFrm.txtKeikakuUriage.disabled=true;
//    		detailFrm.txtKeikakuUriage.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtKeikakuUriage" + i).disabled=true;
    		detailDoc.getElementById("txtKeikakuUriage" + i).style.backgroundColor=color_read;

//    		detailFrm.txtKeikakuRieki.disabled=true;
//    		detailFrm.txtKeikakuRieki.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtKeikakuRieki" + i).disabled=true;
    		detailDoc.getElementById("txtKeikakuRieki" + i).style.backgroundColor=color_read;

//    		detailFrm.txtHanbaigoUriage.disabled=true;
//    		detailFrm.txtHanbaigoUriage.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtHanbaigoUriage" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaigoUriage" + i).style.backgroundColor=color_read;

//    		detailFrm.txtHanbaigoRieki.disabled=true;
//    		detailFrm.txtHanbaigoRieki.style.backgroundColor=color_read;
    		detailDoc.getElementById("txtHanbaigoRieki" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaigoRieki" + i).style.backgroundColor=color_read;

    	}
    	// MOD 2013/7/2 shima【QP@30151】No.37 end

    	detailFrm.txtGenkaMemoEigyo.disabled=true;
    	detailFrm.txtGenkaMemoEigyo.style.backgroundColor=color_read;

    }

    //排他以外
    // ADD 2013/10/22 QP@30154 okano start
    //研究所
    else if(kenkyu == "1"){

    	//各コンポーネント制御
    	headerFrm.btnToroku.disabled=true;

    	//20160628  KPX@1502111_No.10 ADD start
    	detailFrm.btnSampleCopy.disabled = true;
        //20160628  KPX@1502111_No.10 ADD end

    	headerFrm.txtKizitu.disabled=true;
    	headerFrm.txtKizitu.style.backgroundColor=color_read;

    	detailFrm.txtYouryo.disabled=true;
    	detailFrm.txtYouryo.style.backgroundColor=color_read;

    	detailFrm.txtIrisu.disabled=true;
    	detailFrm.txtIrisu.style.backgroundColor=color_read;

    	detailFrm.txtNisugata.disabled=true;
    	detailFrm.txtNisugata.style.backgroundColor=color_read;

    	for(i = 0 ; i < recCnt ; i++){

    		detailDoc.getElementById("txtGenkaKibo" + i).disabled=true;
    		detailDoc.getElementById("txtGenkaKibo" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlGenkaTani" + i).disabled=true;
    		detailDoc.getElementById("ddlGenkaTani" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtBaikaKibo" + i).disabled=true;
    		detailDoc.getElementById("txtBaikaKibo" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtSoteiButuryo_s" + i).disabled=true;
    		detailDoc.getElementById("txtSoteiButuryo_s" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).disabled=true;
    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).disabled=true;
    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).style.backgroundColor=color_read;
    		detailDoc.getElementById("txtSoteiButuryo" + i).disabled=true;
    		detailDoc.getElementById("txtSoteiButuryo" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtHatubaiJiki" + i).disabled=true;
    		detailDoc.getElementById("txtHatubaiJiki" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlHanbaiKikan_t" + i).disabled=true;
    		detailDoc.getElementById("ddlHanbaiKikan_t" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtHanbaiKikan_s" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled=true;
    		detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtKeikakuUriage" + i).disabled=true;
    		detailDoc.getElementById("txtKeikakuUriage" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtKeikakuRieki" + i).disabled=true;
    		detailDoc.getElementById("txtKeikakuRieki" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtHanbaigoUriage" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaigoUriage" + i).style.backgroundColor=color_read;

    		detailDoc.getElementById("txtHanbaigoRieki" + i).disabled=true;
    		detailDoc.getElementById("txtHanbaigoRieki" + i).style.backgroundColor=color_read;

    	}

    	detailFrm.txtGenkaMemoEigyo.disabled=true;
    	detailFrm.txtGenkaMemoEigyo.style.backgroundColor=color_read;
    }
    // ADD 2013/10/22 QP@30154 okano end
    else{
    	//版番号により制御
    	var eda = headerFrm.txtEdaNo.value;
    	if( eda == 0 ){
    		detailFrm.txtYouryo.disabled="true";
	    	detailFrm.txtYouryo.style.backgroundColor=color_read;
    	}

    	//販売期間設定
    	changeKikan();
    	//ｽﾃｰﾀｽにより制御
    	var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
    	var st_seikan = headerFrm.hdnStatus_seikan.value;
    	var st_gentyo = headerFrm.hdnStatus_gentyo.value;
    	var st_kojo = headerFrm.hdnStatus_kojo.value;
    	var st_eigyo = headerFrm.hdnStatus_eigyo.value;

    	//現在の生産管理部ステータス　>= 2　及び　営業ステータス = 4　の場合
    	if( st_seikan >= 2 || st_eigyo == 4 ){

    		headerFrm.txtKizitu.disabled=true;
	    	headerFrm.txtKizitu.style.backgroundColor=color_read;

    		detailFrm.txtYouryo.disabled=true;
	    	detailFrm.txtYouryo.style.backgroundColor=color_read;

	    	detailFrm.txtIrisu.disabled=true;
	    	detailFrm.txtIrisu.style.backgroundColor=color_read;

	    	detailFrm.txtNisugata.disabled=true;
	    	detailFrm.txtNisugata.style.backgroundColor=color_read;

			// MOD 2013/7/2 shima【QP@30151】No.37 start
	    	for(i = 0; i < recCnt ;i++ ){
//	    		detailFrm.txtGenkaKibo.disabled=true;
//	    		detailFrm.txtGenkaKibo.style.backgroundColor=color_read;
	    		detailDoc.getElementById("txtGenkaKibo" + i).disabled=true;
	    		detailDoc.getElementById("txtGenkaKibo" + i).style.backgroundColor=color_read;
//	    	}

//		    	detailFrm.ddlGenkaTani.disabled=true;
//		    	detailFrm.ddlGenkaTani.style.backgroundColor=color_read;
		    	detailDoc.getElementById("ddlGenkaTani" + i).disabled=true;
		    	detailDoc.getElementById("ddlGenkaTani" + i).style.backgroundColor=color_read;

//		    	detailFrm.txtBaikaKibo.disabled=true;
//		    	detailFrm.txtBaikaKibo.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtBaikaKibo" + i).disabled=true;
		    	detailDoc.getElementById("txtBaikaKibo" + i).style.backgroundColor=color_read;

				// ADD 2013/9/6 okano【QP@30151】No.30 start
	    		detailDoc.getElementById("txtSoteiButuryo_s" + i).disabled=true;
	    		detailDoc.getElementById("txtSoteiButuryo_s" + i).style.backgroundColor=color_read;

	    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).disabled=true;
	    		detailDoc.getElementById("ddlSoteiButuryo_u" + i).style.backgroundColor=color_read;

	    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).disabled=true;
	    		detailDoc.getElementById("ddlSoteiButuryo_k" + i).style.backgroundColor=color_read;
	    		// ADD 2013/9/6 okano【QP@30151】No.30 end

//		    	detailFrm.txtSoteiButuryo.disabled=true;
//		    	detailFrm.txtSoteiButuryo.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtSoteiButuryo" + i).disabled=true;
		    	detailDoc.getElementById("txtSoteiButuryo" + i).style.backgroundColor=color_read;

//		    	detailFrm.txtHatubaiJiki.disabled=true;
//		    	detailFrm.txtHatubaiJiki.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtHatubaiJiki" + i).disabled=true;
		    	detailDoc.getElementById("txtHatubaiJiki" + i).style.backgroundColor=color_read;

//		    	detailFrm.ddlHanbaiKikan_t.disabled=true;
//		    	detailFrm.ddlHanbaiKikan_t.style.backgroundColor=color_read;
		    	detailDoc.getElementById("ddlHanbaiKikan_t" + i).disabled=true;
		    	detailDoc.getElementById("ddlHanbaiKikan_t" + i).style.backgroundColor=color_read;

//		    	detailFrm.txtHanbaiKikan_s.disabled=true;
//		    	detailFrm.txtHanbaiKikan_s.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtHanbaiKikan_s" + i).disabled=true;
		    	detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_read;

//		    	detailFrm.ddlHanbaiKikan_k.disabled=true;
//		    	detailFrm.ddlHanbaiKikan_k.style.backgroundColor=color_read;
		    	detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled=true;
		    	detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_read;

//		    	detailFrm.txtKeikakuUriage.disabled=true;
//		    	detailFrm.txtKeikakuUriage.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtKeikakuUriage" + i).disabled=true;
		    	detailDoc.getElementById("txtKeikakuUriage" + i).style.backgroundColor=color_read;

//		    	detailFrm.txtKeikakuRieki.disabled=true;
//		    	detailFrm.txtKeikakuRieki.style.backgroundColor=color_read;
		    	detailDoc.getElementById("txtKeikakuRieki" + i).disabled=true;
		    	detailDoc.getElementById("txtKeikakuRieki" + i).style.backgroundColor=color_read;

	    	}
	    	// MOD 2013/7/2 shima【QP@30151】No.37 end
	    }

    }

    return true;
}


//========================================================================================
// 販売期間選択処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
//========================================================================================
function changeKikan(){

	var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    // ADD 2013/7/2 shima【QP@30151】No.37 start
    var recCnt;
    //列数取得
    recCnt = detailFrm.cnt_sample.value;

    var i;
    // ADD 2013/7/2 shima【QP@30151】No.37 end

    // MOD 2013/7/2 shima【QP@30151】No.37 start
    for(i = 0;i < recCnt;i++){

		//選択値取得
	//	var val = detailFrm.ddlHanbaiKikan_t.options[detailFrm.ddlHanbaiKikan_t.selectedIndex].value;
		var val = detailDoc.getElementById("ddlHanbaiKikan_t" + i).options[detailDoc.getElementById("ddlHanbaiKikan_t" + i).selectedIndex].value;

		//スポット　の場合
		if( val == "2" ){

			//試算中止列か
			var chusi = detailDoc.getElementById("col_chusi" + i ).value;
//20160628  KPX@1502111 ADD start
			var koumokuchk =detailDoc.getElementById("col_kotei" + i ).value;
			if(koumokuchk == "1"){
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled = false;
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_read;

				detailDoc.getElementById("txtHanbaiKikan_s" + i).readOnly=false;
				detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_read;
//20160628  KPX@1502111 ADD end
			} else if(chusi == "1"){
//			if(chusi == "1"){
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled = false;
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor="#c0c0c0";

				detailDoc.getElementById("txtHanbaiKikan_s" + i).readOnly=false;
				detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor="#c0c0c0";
			}else{
//				detailFrm.ddlHanbaiKikan_k.disabled = false;
//				detailFrm.ddlHanbaiKikan_k.style.backgroundColor=color_henshu;
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled = false;
				detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_henshu;

//				detailFrm.txtHanbaiKikan_s.readOnly=false;
//			    detailFrm.txtHanbaiKikan_s.style.backgroundColor=color_henshu;
				detailDoc.getElementById("txtHanbaiKikan_s" + i).readOnly=false;
				detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_henshu;
			}
			// ADD 2013/9/6 okano【QP@30151】No.30 start
			detailDoc.getElementById("ddlSoteiButuryo_k" + i).selectedIndex = 1;
			// ADD 2013/9/6 okano【QP@30151】No.30 end
		}
		else{
//			detailFrm.ddlHanbaiKikan_k.selectedIndex = 0;
//			detailFrm.ddlHanbaiKikan_k.disabled = true;
//			detailFrm.ddlHanbaiKikan_k.style.backgroundColor=color_read;
			detailDoc.getElementById("ddlHanbaiKikan_k" + i).selectedIndex = 0;
			detailDoc.getElementById("ddlHanbaiKikan_k" + i).disabled = true;
			detailDoc.getElementById("ddlHanbaiKikan_k" + i).style.backgroundColor=color_read;

//			detailFrm.txtHanbaiKikan_s.value="";
//			detailFrm.txtHanbaiKikan_s.readOnly=true;
//		    detailFrm.txtHanbaiKikan_s.style.backgroundColor=color_read;
			detailDoc.getElementById("txtHanbaiKikan_s" + i).value="";
			detailDoc.getElementById("txtHanbaiKikan_s" + i).readOnly=true;
			detailDoc.getElementById("txtHanbaiKikan_s" + i).style.backgroundColor=color_read;
			// ADD 2013/9/6 okano【QP@30151】No.30 start
			if( val == "1"){
				detailDoc.getElementById("ddlSoteiButuryo_k" + i).selectedIndex = 2;
			} else {
				detailDoc.getElementById("ddlSoteiButuryo_k" + i).selectedIndex = 0;
			}
			// ADD 2013/9/6 okano【QP@30151】No.30 end
		}
    }
    // MOD 2013/7/2 shima【QP@30151】No.37 end

}

//========================================================================================
// 原価試算、原料情報取得＆表示処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：原価試算、原料情報取得
//========================================================================================
function funGetGenryoInfo(mode) {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN0012";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0012");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0012I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0012O );
    var keisanKomoku;

    //------------------------------------------------------------------------------------
    //                                   原料情報取得
    //------------------------------------------------------------------------------------
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0012, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                  原料情報表示
    //------------------------------------------------------------------------------------
    //試算情報表示
    funGenryoShisanDisplay(xmlResAry[2], "divGenryoShisan");

    //処理終了
    return true;

}

//========================================================================================
// 権限関連処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    var reccnt;
    var i;

    //権限ループ
    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);

        //原価試算（営業）画面
        if (GamenId.toString() == ConGmnIdGenkaShisanEigyo.toString()) {

            //権限設定
            headerFrm.hdnKengen.value = KinoId.toString();

        }
    }

    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①XmlId  ：XMLID
//       ：②reqAry ：機能ID別送信XML(配列)
//       ：③Mode   ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput(XmlId, reqAry, mode) {

    var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    var i;

    for (i = 0; i < reqAry.length; i++) {

        //画面初期表示（共通情報）
        if (XmlId.toString() == "RGEN0010") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0010
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
                case 2:    //FGEN2020
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
        //原価試算画面起動情報通知
        else if (XmlId.toString() == "RGEN2160"){
            // XMLより試作コード取得
            var no_shisaku = headerFrm.txtShainCd.value;
            var nen = headerFrm.txtNen.value;
            var oi = headerFrm.txtOiNo.value;
            var no_eda = headerFrm.txtEdaNo.value;

            var put_shisaku = no_shisaku + "-" + nen + "-" + oi + "-" + no_eda;

            // 試作コード置換【社員CD::年:::追番:::枝番】
            var put_shisaku = put_shisaku.replace(/-/g,":::");

            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    funXmlWrite(reqAry[i], "MOVEMENT_CONDITION", put_shisaku, 0);
                    break;
            }
        }
        //画面初期表示（基本情報）
        else if (XmlId.toString() == "RGEN0011"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0011
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }

        //画面初期表示（原料情報）
        else if (XmlId.toString() == "RGEN0012"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0012
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
        else if (XmlId.toString() == "RGEN0090") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0090
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }

     // ADD 2015/07/24 TT.Kitazawa【QP@40812】No.1 start
        //印刷
        else if (XmlId.toString() == "RGEN2240"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2240
                    //ループカウント
                    var recCnt;
                    var j;
                    //-------------------------- [table]テーブル格納 -------------------------
                    //列数取得
                    recCnt = detailFrm.cnt_sample.value;
                    //XMLレコード挿入カウント
                    var recInsert = 0;
                    //XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){
                        //列数に対するオブジェクトが存在する場合
                        if(detailDoc.getElementById("chkInsatu"+j)){
                            //オブジェクトがチェックされている場合
                            if(detailDoc.getElementById("chkInsatu"+j).checked){
                                if (recInsert != 0) {
                                    funAddRecNode_Tbl(reqAry[i], "FGEN2240", "table");
                                }
                                // 試作CD-社員CD
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", headerFrm.txtShainCd.value, recInsert);
                                // 試作CD-年
                                funXmlWrite_Tbl(reqAry[i], "table", "nen", headerFrm.txtNen.value, recInsert);
                                // 試作CD-追番
                                funXmlWrite_Tbl(reqAry[i], "table", "no_oi", headerFrm.txtOiNo.value, recInsert);
                                // 試作CD-枝番
                                funXmlWrite_Tbl(reqAry[i], "table", "no_eda", headerFrm.txtEdaNo.value, recInsert);
                                // 試作SEQ
                                funXmlWrite_Tbl(reqAry[i], "table", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, recInsert);
                                //===========================================================================
                                // 画面表示の計算項目の値を渡す：
                                //   固定ファイルの値は再計算して保存していない場合、画面の値と異なる為
                                //===========================================================================
                                // 原価計（円）／ケース
                                funXmlWrite_Tbl(reqAry[i], "table", "genkakei", detailDoc.getElementById("genkakei"+j).value, recInsert);
                                // 原価計（円）／個
                                funXmlWrite_Tbl(reqAry[i], "table", "genkakeiKo", detailDoc.getElementById("genkakeiKo"+j).value, recInsert);
                                // 原価計（円）／Kg
                                funXmlWrite_Tbl(reqAry[i], "table", "kg_genkake", detailDoc.getElementById("txtKgGenkake_"+j).value, recInsert);
                                // 売価
                                funXmlWrite_Tbl(reqAry[i], "table", "baika", detailDoc.getElementById("baika"+j).value, recInsert);
                                // 粗利（％）
                                funXmlWrite_Tbl(reqAry[i], "table", "arari", detailDoc.getElementById("arari"+j).value, recInsert);

                                // XMLレコード挿入カウント+1
                                recInsert++;
                            }
                        }
                    }
                    break;
            }
        }
     // ADD 2015/07/24 TT.Kitazawa【QP@40812】No.1 end

        //登録   *** 不要！ ***
        else if (XmlId.toString() == "RGEN0041"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0040

                    //ループカウント
                    var recCnt;
                    var j;

                    //-------------------------- [kihon]テーブル格納 --------------------------
                    // 試作CD-社員CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", headerFrm.txtShainCd.value, 0);
                    // 試作CD-年
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", headerFrm.txtNen.value, 0);
                    // 試作CD-追番
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", headerFrm.txtOiNo.value, 0);
                    // 【QP@00342】枝番
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", "0", 0);
                    // 採用サンプルＮＯ
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_sanpuru", headerFrm.ddlSaiyoSample.options[headerFrm.ddlSaiyoSample.selectedIndex].value, 0);
                    // 工場　担当会社
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    // 工場　担当工場
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kojyo", detailFrm.ddlSeizoKojo.options[detailFrm.ddlSeizoKojo.selectedIndex].value, 0);
                    // 入り数
                    funXmlWrite_Tbl(reqAry[i], "kihon", "irisu", detailFrm.txtIrisu.value, 0);
                    // 荷姿
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nisugata", detailFrm.txtNisugata.value, 0);
                    // 原価希望
                    funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka", detailFrm.txtGenkaKibo.value, 0);
                    // 原価希望単位CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_cd_tani", detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].value, 0);
                    // 売価希望
                    funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_baika", detailFrm.txtBaikaKibo.value, 0);
                    // 想定物量
                    // ADD 2013/9/6 okano【QP@30151】No.30 start
                    // MOD 2013/11/11 QP@30154 okano start
//	                    var s = detailFrm.txtSoteiButuryo_s.value * 1000;
//	                    s = Math.round(s)/1000;
//	                    funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei_s", s, 0);
                    funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei_s", detailFrm.txtSoteiButuryo_s.value, 0);
                    // MOD 2013/11/11 QP@30154 okano end
                    funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei_u", detailFrm.ddlSoteiButuryo_u.options[detailFrm.ddlSoteiButuryo_u.selectedIndex].value, 0);
                    funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei_k", detailFrm.ddlSoteiButuryo_k.options[detailFrm.ddlSoteiButuryo_k.selectedIndex].value, 0);
                    // ADD 2013/9/6 okano【QP@30151】No.30 end
                    funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei", detailFrm.txtSoteiButuryo.value, 0);
                    // 販売時期
                    funXmlWrite_Tbl(reqAry[i], "kihon", "ziki_hanbai", detailFrm.txtHanbaiJiki.value, 0);
                    // 計画売上
                    funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_uriage", detailFrm.txtKeikakuUriage.value, 0);
                    // 計画利益
                    funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_rieki", detailFrm.txtKeikakuRieki.value, 0);
                    // 販売後売上
                    funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_uriage", detailFrm.txtHanbaigoUriage.value, 0);
                    // 販売後利益
                    funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_rieki", detailFrm.txtHanbaigoRieki.value, 0);
                    // 製造ロット
                    funXmlWrite_Tbl(reqAry[i], "kihon", "seizo_roto", detailFrm.txtSeizoRot.value, 0);
                    // 原価試算メモ
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan", detailFrm.txtGenkaMemo.value, 0);
                    // 計算項目（固定費/ケースor固定費/kg）
                    var koteihiFg = "";
                    for (j = 0; j < detailFrm.radioKoteihi.length; j++){
                       if(detailFrm.radioKoteihi[j].checked == true){
                           koteihiFg = detailFrm.radioKoteihi[j].value;
                       }
                    }
                    funXmlWrite_Tbl(reqAry[i], "kihon", "ragio_kesu_kg", koteihiFg, 0);

                    //-------------------------- [genryo]テーブル格納 -------------------------
                    //原料表の行数取得
                    recCnt = detailFrm.cnt_genryo.value;
                    //原料行カウント
                    var gyoCnt = 1;
                    //XMLレコード挿入カウント
                    var recInsert = 0;
                    for( j = 0; j < recCnt; j++ ){

                        //原料行数に対するオブジェクトが存在する場合
                        if(detailDoc.getElementById("hdnCd_kotei_"+gyoCnt)){
                            if (recInsert != 0) {
                                //レコード挿入
                                funAddRecNode_Tbl(reqAry[i], "FGEN0040", "genryo");
                            }
                            // 工程CD
                            funXmlWrite_Tbl(reqAry[i], "genryo", "cd_kotei", detailDoc.getElementById("hdnCd_kotei_"+gyoCnt).value, recInsert);
                            // 工程SEQ
                            funXmlWrite_Tbl(reqAry[i], "genryo", "seq_kotei", detailDoc.getElementById("hdnSeq_kotei_"+gyoCnt).value, recInsert);
                            // 原料CD
                            funXmlWrite_Tbl(reqAry[i], "genryo", "cd_genryo", detailDoc.getElementById("txtCd_genryo_"+gyoCnt).value, recInsert);
                            // 単価
                            funXmlWrite_Tbl(reqAry[i], "genryo", "tanka", detailDoc.getElementById("txtTanka_"+gyoCnt).value, recInsert);
                            // 歩留
                            funXmlWrite_Tbl(reqAry[i], "genryo", "budomari", detailDoc.getElementById("txtBudomari_"+gyoCnt).value, recInsert);
                            // 行番号
                            funXmlWrite_Tbl(reqAry[i], "genryo", "no_gyo", gyoCnt, recInsert);
                            // XMLレコード挿入カウント+1
                            recInsert++;
                        }

                        //原料行カウント+1
                        gyoCnt++;
                    }


                    //-------------------------- [keisan]テーブル格納 -------------------------
                    //計算表の列数取得
                    recCnt = detailFrm.cnt_sample.value;
                    //XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0040", "keisan");
                        }
                        // 試作SEQ
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);
                        // 試算日
                        funXmlWrite_Tbl(reqAry[i], "keisan", "shisan_date", detailDoc.getElementById("txtShisanHi_"+j).value, j);
                        // 有効歩留（％）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "yuuko_budomari", detailDoc.getElementById("txtYukoBudomari_"+j).value, j);
                        // 平均充填量（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "heikinjyutenryo", detailDoc.getElementById("txtHeikinZyu_"+j).value, j);
                        // 固定費/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_kotehi", detailDoc.getElementById("txtCaseKotei_"+j).value, j);
                        // 固定費/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_kotehi", detailDoc.getElementById("txtKgKotei_"+j).value, j);
                        // 原価計/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genkake", detailDoc.getElementById("txtKgGenkake_"+j).value, j);
                    }


                    //-------------------------- [shizai]テーブル格納 -------------------------
                    //資材テーブルオブジェクト取得
                    var tblShizai = detailDoc.getElementById("tblList4");
                    //資材表の行数取得
                    recCnt = tblShizai.rows.length;
                    //XMLレコード挿入カウント
                    var recInsert = 0;
                    //XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){

                        //資材行に何も入力されていない場合
                        if( detailFrm.txtCdShizai[j].value == ""
                            && detailFrm.txtNmShizai[j].value == ""
                            && detailFrm.txtTankaShizai[j].value == ""
                            && detailFrm.txtBudomariShizai[j].value == ""
                            && detailFrm.txtSiyouShizai[j].value == ""){

                            //何もしない


                        }else{

                            //XMLへ追加
                            if (recInsert != 0) {
                                funAddRecNode_Tbl(reqAry[i], "FGEN0040", "shizai");
                            }
                            // 資材SEQ
                            funXmlWrite_Tbl(reqAry[i], "shizai", "seq_shizai", recInsert+1, recInsert);
                            // 会社CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_kaisya", detailFrm.hdnKaisha_Shizai[j].value, recInsert);
                            // 工場CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_kojyo", detailFrm.hdnKojo_Shizai[j].value, recInsert);
                            // 資材CD
                            funXmlWrite_Tbl(reqAry[i], "shizai", "cd_shizai", detailFrm.txtCdShizai[j].value, recInsert);
                            // 資材名
                            funXmlWrite_Tbl(reqAry[i], "shizai", "nm_shizai", detailFrm.txtNmShizai[j].value, recInsert);
                            // 単価
                            funXmlWrite_Tbl(reqAry[i], "shizai", "tanka", detailFrm.txtTankaShizai[j].value, recInsert);
                            // 歩留（％）
                            funXmlWrite_Tbl(reqAry[i], "shizai", "budomari", detailFrm.txtBudomariShizai[j].value, recInsert);
                            // 使用量/ｹｰｽ
                            funXmlWrite_Tbl(reqAry[i], "shizai", "shiyouryo", detailFrm.txtSiyouShizai[j].value, recInsert);
                            // 登録者ID
                            funXmlWrite_Tbl(reqAry[i], "shizai", "id_toroku", detailFrm.hdnId_toroku[j].value, recInsert);
                            // 登録日
                            funXmlWrite_Tbl(reqAry[i], "shizai", "dt_toroku", detailFrm.hdnDt_toroku[j].value, recInsert);
                            // XMLレコード挿入カウント+1
                            recInsert++;
                        }
                    }

                    break;
            }
        }
    }

    return true;

}

//========================================================================================
// コンボボックス作成処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②xmlData  ：設定XML
//       ：③mode     ：モード
//       ：④karaFg   ：空白選択の設定（0：空白無し、1：空白有り）
// 概要  ：コンボボックスを作成する
//========================================================================================
function funCreateComboBox(obj, xmlData, mode, karaFg) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;
    var objNewOption;
    var atbName;
    var atbCd;
    var i;
	var tableNm;

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(obj, 2);

    //件数取得
    reccnt = funGetLength(xmlData);

    //ﾃﾞｰﾀが存在しない場合
    if (reccnt == 0) {
        //処理を中断
        return true;
    }

    //属性名の取得
    switch (mode) {

        //採用サンプルNo
        case 1:
            atbName = "nm_sample"; //サンプル名
            atbCd = "seq_shisaku"; //試作SEQ
            tableNm = "tr_shisan_shisaku";           //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得

            break;

        //製造会社
        case 2:
            atbName = "nm_kaisya"; //会社名
            atbCd = "cd_kaisya"; //会社CD
            tableNm = "kaisya";           //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;

        //製造工場
        case 3:
            atbName = "nm_kojyo"; //工場名
            atbCd = "cd_kojyo"; //工場CD
            tableNm = "kojo";           //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;

        //原価希望単位
        case 4:
            atbName = "kibo_genka_nm_tani"; //リテラル名
            atbCd = "kibo_genka_cd_tani"; //リテラルCD
            tableNm = "tani";           //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;

        //製造工場（会社変更時）
        case 5:
            atbName = "nm_kojyo"; //工場名
            atbCd = "cd_kojyo"; //工場CD
            tableNm = "kojyo";           //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;

        //販売期間　通年orスポット
        case 6:
            atbName = "nm_literal"; //リテラル名
            atbCd = "cd_literal"; //リテラルCD
            tableNm = "tani_hanbai_T";           //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;

        //販売期間　ヶ月
        case 7:
            atbName = "nm_literal"; //リテラル名
            atbCd = "cd_literal"; //リテラルCD
            tableNm = "tani_hanbai_K";           //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;

        // ADD 2013/9/6 okano【QP@30151】No.30 start
        //想定物量　単位
        case 8:
            atbName = "nm_literal"; //リテラル名
            atbCd = "cd_literal"; //リテラルCD
            tableNm = "sotei_buturyo_U"; //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;

        //想定物量　期間
        case 9:
            atbName = "nm_literal"; //リテラル名
            atbCd = "cd_literal"; //リテラルCD
            tableNm = "sotei_buturyo_K"; //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;
        // ADD 2013/9/6 okano【QP@30151】No.30 end

    }

    for (i = 0; i < reccnt; i++) {
        if (funXmlRead_3(xmlData, tableNm, atbCd, 0, i) != "" && funXmlRead_3(xmlData, tableNm, atbName, 0, i) != "") {
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = funXmlRead_3(xmlData, tableNm, atbName, 0, i);
            objNewOption.value = funXmlRead_3(xmlData, tableNm, atbCd, 0, i);
        }
    }

    //先頭行の削除
    if(karaFg == 0){
        obj.options[0] = null;
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
    obj.selectedIndex = 0;

    return true;
}

//========================================================================================
// デフォルト値選択処理
// 作成者：Y.nishigawa
// 作成日：2011/01/28
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②mode     ：モード
// 概要  ：コンボボックスのデフォルト値を選択する
//========================================================================================
function funDefaultIndex(obj, mode) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var selIndex;
    var i;

    //XMLとコンボボックスVALUE値を判定
    for (i = 0; i < obj.length; i++) {
        switch (mode) {
            case 1:    //採用サンプルNo
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0010O, "kihon", "saiyo_no", 0, 0)) {
                    selIndex = i;
                }
                break;
            case 2:    //製造会社
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "cd_kaisya", 0, 0)) {
                    selIndex = i;
                }
                break;
            case 3:    //製造工場（初期表示）
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "cd_kojyo", 0, 0)) {
                    selIndex = i;
                }
                break;
            case 4:    //原価希望単位
            	// MOD 2013/7/2 shima【QP@30151】No.37 start
            	var No = obj.name.slice(12);
//                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "kibo_genka_cd_tani", 0, 0)) {
            	if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihonsub", "kibo_genka_cd_tani", 0, No)) {
                    selIndex = i;
                }
                // MOD 2013/7/2 shima【QP@30151】No.37 end
                break;
            case 5:    //販売期間（通年orスポット）
            	// MOD 2013/7/2 shima【QP@30151】No.37 start
            	var No = obj.name.slice(16);
//            	if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "kikan_hanbai_t_cd", 0, 0)) {
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihonsub", "kikan_hanbai_t_cd", 0, No)) {
                    selIndex = i;
                }
                // MOD 2013/7/2 shima【QP@30151】No.37 end
                break;
            case 6:    //販売期間（ヶ月）
            	// MOD 2013/7/2 shima【QP@30151】No.37 start
            	var No = obj.name.slice(16);
//                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "kikan_hanbai_k_cd", 0, 0)) {
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihonsub", "kikan_hanbai_k_cd", 0, No)) {
                    selIndex = i;
                }
                // MOD 2013/7/2 shima【QP@30151】No.37 end
                break;
            // ADD 2013/9/6 okano【QP@30151】No.30 start
            case 7:    //想定物量（製品単位）
            	var No = obj.name.slice(17);
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihonsub", "sotei_buturyo_u_cd", 0, No)) {
                    selIndex = i;
                }
                break;
            case 8:    //想定物量（期間単位）
            	var No = obj.name.slice(17);
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihonsub", "sotei_buturyo_k_cd", 0, No)) {
                    selIndex = i;
                }
                break;
            // ADD 2013/9/6 okano【QP@30151】No.30 end
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
//サンプル毎の基本情報テーブル情報表示
//作成者：H.Shima
//作成日：2013/7/2
//引数  ：①xmlUser ：更新情報格納XML名
//      ：②ObjectId：設定オブジェクトID
//戻り値：なし
//概要  ：サンプル毎の基本情報テーブル表示用のHTML文を生成、出力する。
//========================================================================================
function funKihonSubDisplay(xmlData, ObjectId) {

	var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

    var OutputHtml;                         //出力HTML
    var i;                                  //ループカウント
    var cnt_sample;

    var genka_kibo;							//希望原価
    var genka_tani;							//
    var genka_tanicd;						//
    var baika_kibo;							//希望特約
    var baika_tani;							//
    var sotei_buturyo;						//想定物量
    // ADD 2013/9/6 okano【QP@30151】No.30 start
    var sotei_buturyo_s;
    var sotei_buturyo_u;
    var sotei_buturyo_k;
    // ADD 2013/9/6 okano【QP@30151】No.30 end
    var hatubai_jiki;						//初回納品時期
    var hanbai_kikan_t;						//販売期間
    var hanbai_kikan_s;						//
    var hanbai_kikan_k;						//
    var keikaku_uriage;						//計画売上
    var keikaku_rieki;						//計画利益
    var hanbaigo_uriage;					//販売後売上
    var hanbaigo_rieki;						//販売後利益
    var seizo_rot;							//製造ロット
    // ADD 2014/8/7 shima【QP@30154】No.63 start
    var fg_chusi;							//中止フラグ
    var fg_koumokuchk;						//項目固定チェック
    // ADD 2014/8/7 shima【QP@30154】No.63 end

    var tableKihonNm = "kihon";
    var tableKihonSubNm = "kihonsub";

    var obj;
    //HTML出力オブジェクト設定
    obj = detailDoc.getElementById(ObjectId);

    //列数取得
    cnt_sample = funXmlRead_3(xmlData, tableKihonNm, "cnt_genryo", 0, i);

    OutputHtml = "";

    OutputHtml += "<input type=\"hidden\" id=\"cnt_sample\" name=\"cnt_sample\" value=\"" + cnt_sample + "\">";

    //中止フラグの設定
    for(i = 0;i < cnt_sample; i++){
    	//中止フラグ取得
		var fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
        OutputHtml += "<input type=\"hidden\" id=\"col_chusi" + i + "\" name=\"col_chusi" + i + "\" value=\"" + fg_chusi + "\">";

//20160628  KPX@1502111 ADD start
        //項目固定チェックフラグ
        fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);
        OutputHtml += "<input type=\"hidden\" id=\"col_kotei" + i + "\" name=\"col_kotei" + i + "\" value=\"" + fg_koumokuchk + "\">";
//20160628  KPX@1502111 ADD end
    }

// MOD start 2015/09/07 TT.Kitazawa
//    OutputHtml += "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" bordercolor=\"#000000\" style=\"table-layout:fixed;border-left-style:none;border-right-style:none;\">";
    OutputHtml += "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" bordercolor=\"#000000\" style=\"table-layout:fixed;\">";
// MOD end 2015/09/07 TT.Kitazawa
    OutputHtml += "<tr>";

	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.1,16 start
    //------------------------------------- 印刷チェック -------------------------------------
    // seq_shisaku
    for(i = 0; i < cnt_sample; i++){
    	OutputHtml += "<th class=\"columntitle\" height=\"20px\" width=\"215\">";
    	OutputHtml += "    <input type=\"checkbox\" name=\"chkInsatu" + i + "\" id=\"chkInsatu" + i + "\" height=\"19px\" value=\"" + i + "\" tabindex=\"6\"/>";
        OutputHtml += "</th>";
    }
    OutputHtml += "</tr><tr>";
    //------------------------------------- サンプルNo. -------------------------------------
    var nm_sample = "";
    // MOD start 2015/08/26 【QP@40812】No.6 TT.Kitazawa
    var no_iraisample = "";

    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
    	nm_sample = funXmlRead_3(xmlData, tableKihonSubNm, "nm_sample", 0, i);
		if( no_iraisample == ""){
			no_iraisample = nm_sample;
		}
		else{
			no_iraisample = no_iraisample + "," + nm_sample;
		}

    	//HTML生成
// MOD start 2015/09/07 TT.Kitazawa
//        OutputHtml += "<td bgcolor=\"\" height=\"19px\" width=\"215\">";
        OutputHtml += "<td bgcolor=\"\" height=\"20px\" width=\"215\">";
// MOD end 2015/09/07 TT.Kitazawa
        OutputHtml += "    <input type=\"text\" class=\"table_text_view\" id = \"nm_sample" + i + "\" readonly style=\"border-width:0px;text-align:right;\" value=\"" + nm_sample + "\" tabindex=\"-1\" />";
        OutputHtml += "</td>";
    }
	headerFrm.hdnNo_iraisampleForMail.value = no_iraisample;
    // MOD end 2015/08/26 【QP@40812】No.6 2015/08/26 TT.Kitazawa
    OutputHtml += "</tr><tr>";
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.16 end

    // MOD 2014/8/7 shima【QP@30154】No.63 start
    //------------------------------------- 希望原価 -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
        genka_kibo = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
        fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML生成
        // MOD 2013/9/6 okano【QP@30151】No.30 start
//        	OutputHtml += "<td bgcolor=\"\" height=\"19px\" width=\"160\">";
// MOD start 2015/09/07 TT.Kitazawa
//        OutputHtml += "<td bgcolor=\"\" height=\"19px\" width=\"215\">";
        OutputHtml += "<td bgcolor=\"\" height=\"20px\" width=\"215\">";
// MOD end 2015/09/07 TT.Kitazawa
        // MOD 2013/9/6 okano【QP@30151】No.30 end
        if(fg_koumokuchk == "1"){
        	//固定項目チェック列は編集不可に
        	OutputHtml += "<input type=\"text\" id=\"txtGenkaKibo" + i + "\" name=\"txtGenkaKibo" + i + "\" style=\"width:125px;height:20px; class=\"table_text_disb\" onblur=\"setKanma(this);\" readonly value=\"" + genka_kibo + "\" tabindex=\"5\" />";
        	OutputHtml += "<select name=\"ddlGenkaTani" + i + "\" id=\"ddlGenkaTani" + i + "\" onChange=\"baikaTaniSetting("+ i +");\" disabled style=\"width:87px;height:16px;\" tabindex=\"6\" />";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	OutputHtml += "<input type=\"text\" id=\"txtGenkaKibo" + i + "\" name=\"txtGenkaKibo" + i + "\" style=\"width:125px;height:20px;background-color:#c0c0c0\" class=\"table_text_disb\" onblur=\"setKanma(this);\" value=\"" + genka_kibo + "\" tabindex=\"5\" />";
        	OutputHtml += "<select name=\"ddlGenkaTani" + i + "\" id=\"ddlGenkaTani" + i + "\" onChange=\"baikaTaniSetting("+ i +");\" style=\"background-color:#c0c0c0;width:87px;height:16px;\" tabindex=\"6\" />";
        }else{
        	OutputHtml += "<input type=\"text\" id=\"txtGenkaKibo" + i + "\" name=\"txtGenkaKibo" + i + "\" style=\"width:125px;height:20px;\" class=\"table_text_disb\" onchange=\"setFg_Henkou();\" onblur=\"setKanma(this);\" value=\"" + genka_kibo + "\" tabindex=\"5\" />";
        	OutputHtml += "<select name=\"ddlGenkaTani" + i + "\" id=\"ddlGenkaTani" + i + "\" onChange=\"setFg_Henkou();baikaTaniSetting("+ i +");\" style=\"background-color:#ffff88;width:87px;height:16px;\" tabindex=\"6\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    //------------------------------------- 希望特約 -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
        baika_kibo = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_baika", 0, i);
    	baika_tani = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka_nm_tani", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//固定項目チェック列は編集不可に
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaKibo" + i + "\" name=\"txtBaikaKibo" + i + "\" onblur=\"setKanma(this);\" style=\"background-color:#ffffff;width:125px;\" readonly class=\"table_text_disb\" value=\"" + baika_kibo + "\" tabindex=\"7\" />";
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaTani" + i + "\" name=\"txtBaikaTani" + i + "\" style=\"width:80px;\"class=\"table_text_view\" readonly value=\"" + baika_tani + "\" tabindex=\"-1\" />";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaKibo" + i + "\" name=\"txtBaikaKibo" + i + "\" onblur=\"setKanma(this);\" style=\"width:125px;background-color:#c0c0c0\" class=\"table_text_disb\"  value=\"" + baika_kibo + "\" tabindex=\"7\" />";
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaTani" + i + "\" name=\"txtBaikaTani" + i + "\" style=\"width:80px;\"class=\"table_text_view\" readonly value=\"" + baika_tani + "\" tabindex=\"-1\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaKibo" + i + "\" name=\"txtBaikaKibo" + i + "\" onchange=\"setFg_Henkou();\" onblur=\"setKanma(this);\" style=\"width:125px;\" class=\"table_text_disb\"  value=\"" + baika_kibo + "\" tabindex=\"7\" />";
        	OutputHtml += "    <input type=\"text\" id=\"txtBaikaTani" + i + "\" name=\"txtBaikaTani" + i + "\" style=\"width:80px;\"class=\"table_text_view\" readonly value=\"" + baika_tani + "\" tabindex=\"-1\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.20 start
    //------------------------------------- 販売期間 -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
        hanbai_kikan_s = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_suti", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//固定項目チェック列は編集不可に
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"changeKikan();\" disabled style=\"background-color:#ffffff;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" readonly style=\"width:70px;text-align:right;background-color:#ffffff;\" class=\"table_text_disb\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" disabled style=\"background-color:#ffffff;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"changeKikan();\" style=\"background-color:#c0c0c0;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:70px;text-align:right;background-color:#c0c0c0;\" class=\"table_text_disb\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" style=\"background-color:#c0c0c0;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
    	}else{
    		OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"setFg_Henkou();changeKikan();\" style=\"background-color:#ffff88;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:70px;text-align:right;\" class=\"table_text_disb\" onchange=\"setFg_Henkou();\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" onChange=\"setFg_Henkou()\" style=\"background-color:#ffff88;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
    	}
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.20 end

    //------------------------------------- 想定物量 -------------------------------------
    // ADD 2013/9/6 okano【QP@30151】No.30 start
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
		sotei_buturyo_s = funXmlRead_3(xmlData, tableKihonSubNm, "soote_buturyo_s", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

		if( sotei_buturyo_s == "" ){
		} else {
			sotei_buturyo_s = sotei_buturyo_s * 1;
		}

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//固定項目チェック列は編集不可に
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" onblur=\"setKanma(this);\" style=\"background-color:#ffffff;width:70px;height:19px;\" readonly class=\"table_text_disb\" value=\"" + sotei_buturyo_s + "\" tabindex=\"8\" />";
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_u" + i + "\" id=\"ddlSoteiButuryo_u" + i + "\" style=\"background-color:#ffffff;width:60px;\" disabled tabindex=\"9\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_k" + i + "\" id=\"ddlSoteiButuryo_k" + i + "\" disabled=\"true\" style=\"background-color:#ffffff;width:70px;\" readonly tabindex=\"-1\" >";
        	OutputHtml += "    </select>";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	// MOD 2013/12/4 okano【QP@30154】start
//        		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" style=\"background-color:#c0c0c0;width:70px;height:19px;\" class=\"table_text_disb\" value=\"" + sotei_buturyo_s + "\" tabindex=\"8\" />";
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" onblur=\"setKanma(this);\" style=\"background-color:#c0c0c0;width:70px;height:19px;\" class=\"table_text_disb\" value=\"" + sotei_buturyo_s + "\" tabindex=\"8\" />";
        	// MOD 2013/12/4 okano【QP@30154】end
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_u" + i + "\" id=\"ddlSoteiButuryo_u" + i + "\" style=\"background-color:#c0c0c0;width:60px;\" tabindex=\"9\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_k" + i + "\" id=\"ddlSoteiButuryo_k" + i + "\" disabled=\"true\" style=\"background-color:#c0c0c0;width:70px;\" tabindex=\"-1\" >";
        	OutputHtml += "    </select>";
        }else{
        	// MOD 2013/12/4 okano【QP@30154】start
//        		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" onchange=\"setFg_Henkou();\" style=\"width:70px;height:19px;text-align:right;\" class=\"table_text_disb\" value=\"" + sotei_buturyo_s + "\" tabindex=\"8\" />";
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" onblur=\"setKanma(this);\" onchange=\"setFg_Henkou();\" style=\"width:70px;height:19px;\" class=\"table_text_disb\" value=\"" + sotei_buturyo_s + "\" tabindex=\"8\" />";
        	// MOD 2013/12/4 okano【QP@30154】end
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_u" + i + "\" id=\"ddlSoteiButuryo_u" + i + "\" onChange=\"setFg_Henkou()\" style=\"background-color:#ffff88;width:60px;\" tabindex=\"9\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <select name=\"ddlSoteiButuryo_k" + i + "\" id=\"ddlSoteiButuryo_k" + i + "\" onChange=\"setFg_Henkou()\" disabled=\"true\" style=\"width:70px;\" tabindex=\"-1\" >";
        	OutputHtml += "    </select>";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    // ADD 2013/9/6 okano【QP@30151】No.30 end

    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
        sotei_buturyo = funXmlRead_3(xmlData, tableKihonSubNm, "soote_buturyo", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//固定項目チェック列は編集不可に
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo" + i + "\" name=\"txtSoteiButuryo" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" readonly class=\"table_text_act\" value=\"" + sotei_buturyo + "\" tabindex=\"10\" />";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo" + i + "\" name=\"txtSoteiButuryo" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" class=\"table_text_act\" value=\"" + sotei_buturyo + "\" tabindex=\"10\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo" + i + "\" name=\"txtSoteiButuryo" + i + "\" onchange=\"setFg_Henkou();\" style=\"ime-mode:active;\" class=\"table_text_act\" value=\"" + sotei_buturyo + "\" tabindex=\"10\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    //------------------------------------- 発売時期 -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
        hatubai_jiki = funXmlRead_3(xmlData, tableKihonSubNm, "ziki_hatubai", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//固定項目チェック列は編集不可に
        	OutputHtml += "    <input type=\"text\" id=\"txtHatubaiJiki" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" name=\"txtHatubaiJiki" + i + "\" readonly class=\"table_text_act\" value=\"" + hatubai_jiki + "\" onblur=\"setNenGetu(this)\" tabindex=\"11\" />";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	OutputHtml += "    <input type=\"text\" id=\"txtHatubaiJiki" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" name=\"txtHatubaiJiki" + i + "\" class=\"table_text_act\" value=\"" + hatubai_jiki + "\" onblur=\"setNenGetu(this)\" tabindex=\"11\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtHatubaiJiki" + i + "\" style=\"ime-mode:active;\" onchange=\"setFg_Henkou();\" name=\"txtHatubaiJiki" + i + "\" class=\"table_text_act\" value=\"" + hatubai_jiki + "\" onblur=\"setNenGetu(this)\" tabindex=\"11\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

	// DEL 2015/03/03 TT.Kitazawa【QP@40812】No.20 start
/*    //------------------------------------- 販売期間 （上に移動）-------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
        hanbai_kikan_s = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_suti", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//固定項目チェック列は編集不可に
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"changeKikan();\" disabled style=\"background-color:#ffffff;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" readonly style=\"width:70px;text-align:right;background-color:#ffffff;\" class=\"table_text_disb\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" disabled style=\"background-color:#ffffff;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"changeKikan();\" style=\"background-color:#c0c0c0;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:70px;text-align:right;background-color:#c0c0c0;\" class=\"table_text_disb\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" style=\"background-color:#c0c0c0;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
    	}else{
    		OutputHtml += "    <select name=\"ddlHanbaiKikan_t" + i + "\" id=\"ddlHanbaiKikan_t" + i + "\" onChange=\"setFg_Henkou();changeKikan();\" style=\"background-color:#ffff88;width:65px;height:16px;\" tabindex=\"12\" >";
        	OutputHtml += "    </select>";
        	OutputHtml += "    <span class=\"ninput\"><input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:70px;text-align:right;\" class=\"table_text_disb\" onchange=\"setFg_Henkou();\" value=\"" + hanbai_kikan_s + "\" tabindex=\"13\" /></span>";
        	OutputHtml += "    <select name=\"ddlHanbaiKikan_k" + i + "\" id=\"ddlHanbaiKikan_k" + i + "\" onChange=\"setFg_Henkou()\" style=\"background-color:#ffff88;width:65px;height:16px;\" tabindex=\"14\" >";
        	OutputHtml += "    </select>";
    	}
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";
*/
	// DEL 2015/03/03 TT.Kitazawa【QP@40812】No.20 end

    //------------------------------------- 計画売上 -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
        keikaku_uriage = funXmlRead_3(xmlData, tableKihonSubNm, "keikaku_uriage", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//固定項目チェック列は編集不可に
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuUriage" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" readonly name=\"txtKeikakuUriage" + i + "\" class=\"table_text_act\" value=\"" + keikaku_uriage + "\" tabindex=\"15\" />";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuUriage" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" name=\"txtKeikakuUriage" + i + "\" class=\"table_text_act\" value=\"" + keikaku_uriage + "\" tabindex=\"15\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuUriage" + i + "\" style=\"ime-mode:active;\" onchange=\"setFg_Henkou();\" name=\"txtKeikakuUriage" + i + "\" class=\"table_text_act\" value=\"" + keikaku_uriage + "\" tabindex=\"15\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    //------------------------------------- 計画利益 -------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
        keikaku_rieki = funXmlRead_3(xmlData, tableKihonSubNm, "keikaku_rieki", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//固定項目チェック列は編集不可に
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuRieki" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" name=\"txtKeikakuRieki" + i + "\" class=\"table_text_act\" readonly value=\"" + keikaku_rieki + "\" tabindex=\"16\" />";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuRieki" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" name=\"txtKeikakuRieki" + i + "\" class=\"table_text_act\" value=\"" + keikaku_rieki + "\" tabindex=\"16\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtKeikakuRieki" + i + "\" style=\"ime-mode:active;\" name=\"txtKeikakuRieki" + i + "\" onchange=\"setFg_Henkou();\" class=\"table_text_act\" value=\"" + keikaku_rieki + "\" tabindex=\"16\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    //------------------------------------ 販売後売上 ------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
        hanbaigo_uriage = funXmlRead_3(xmlData, tableKihonSubNm, "hanbaigo_uriage", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	//固定項目チェック列は編集不可に
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoUriage" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" name=\"txtHanbaigoUriage" + i + "\" readonly class=\"table_text_act\" value=\"" + hanbaigo_uriage + "\" tabindex=\"17\" />";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoUriage" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" name=\"txtHanbaigoUriage" + i + "\" class=\"table_text_act\" value=\"" + hanbaigo_uriage + "\" tabindex=\"17\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoUriage" + i + "\" style=\"ime-mode:active;\" name=\"txtHanbaigoUriage" + i + "\" onchange=\"setFg_Henkou();\" class=\"table_text_act\" value=\"" + hanbaigo_uriage + "\" tabindex=\"17\" />";
        }
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr><tr>";

    //------------------------------------ 販売後利益 ------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
    	hanbaigo_rieki = funXmlRead_3(xmlData, tableKihonSubNm, "hanbaigo_rieki", 0, i);
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		fg_koumokuchk =  funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        if(fg_koumokuchk == "1"){
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoRieki" + i + "\" style=\"ime-mode:active;background-color:#ffffff;\" name=\"txtHanbaigoRieki" + i + "\" readonly class=\"table_text_act\" value=\"" + hanbaigo_rieki + "\" tabindex=\"18\" />";
        }else if(fg_chusi == "1"){
        	//試算中止列は背景色をグレーに
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoRieki" + i + "\" style=\"ime-mode:active;background-color:#c0c0c0;\" name=\"txtHanbaigoRieki" + i + "\" class=\"table_text_act\" value=\"" + hanbaigo_rieki + "\" tabindex=\"18\" />";
        }else{
        	OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoRieki" + i + "\" style=\"ime-mode:active;\" name=\"txtHanbaigoRieki" + i + "\" onchange=\"setFg_Henkou();\" class=\"table_text_act\" value=\"" + hanbaigo_rieki + "\" tabindex=\"18\" />";
        }
        OutputHtml += "</td>";
    }
    // ADD 2014/8/7 shima【QP@30154】No.63 end

    OutputHtml += "</tr><tr>";

    //------------------------------------ 製造ロット ------------------------------------
    for(i = 0; i < cnt_sample; i++){
    	//XMLデータ取得
        seizo_rot = funXmlRead_3(xmlData, tableKihonSubNm, "seizo_roto", 0, i);

    	//HTML生成
        OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
        OutputHtml += "    <input type=\"text\" id=\"txtSeizoRot" + i + "\" name=\"txtSeizoRot" + i + "\" class=\"table_text_view\" readonly value=\"" + seizo_rot + "\" tabindex=\"-1\" />";
        OutputHtml += "</td>";
    }

    OutputHtml += "</tr>";
    OutputHtml += "</table>";


    //------------------------------------------------------------------------------------
    //                                  HTML出力
    //------------------------------------------------------------------------------------
    //HTMLを出力
    obj.innerHTML = OutputHtml;

    OutputHtml = null;

    return true;
}


//========================================================================================
// 原料試算テーブル情報表示
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①xmlUser ：更新情報格納XML名
//       ：②ObjectId：設定オブジェクトID
// 戻り値：なし
// 概要  ：原料試算テーブル表示用のHTML文を生成、出力する。
//========================================================================================
function funGenryoShisanDisplay(xmlData, ObjectId) {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var headerFrm = parent.header.document.frm00; //ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var Kengen = headerFrm.hdnKengen.value;       //権限：機能ID
    var obj;              //設定先オブジェクト
    var tablekihonNm;     //読み込みテーブル名
    var tableKeisanNm;    //読み込みテーブル名
    var OutputHtml;       //出力HTML
    var cnt_genryo;       //行数
    var cnt_sample;       //列数
    var table_size;       //テーブル幅
    var txtReadonly;      //テキストボックス入力設定
    var txtClass;         //テキストボックス背景色

    var nm_sanpuru        //サンプルNo
    var seq_shisaku;      //試作SEQ
    var shisan_date;      //試算日
    var jyuuten_suiso;    //充填量水相（ｇ）
    var jyuuten_yuso;     //充填量油相（ｇ）
    var gookezyuryo;      //合計量
    var hizyu;            //比重
    var yuuko_budomari;   //有効歩留（％）
    var reberuryo;        //レベル量（ｇ）
    var heikinjyutenryo;  //平均充填量（ｇ）
    var hizyukasanryo;    //比重加算量（ｇ）
    var kesu_genryohi;    //原料費/ケース
    var kesu_zairyohi;    //材料費/ケース
    var kesu_kotehi;      //固定費/ケース
    var kesu_genkake;     //原価計/ケース
    var ko_genkake;       //原価計/個
    var kg_genryohi;      //原料費/KG
    var kg_zairyohi;      //材料費/KG
    var kg_kotehi;        //固定費/KG
    var kg_genkake;       //原価計/KG
    var baika;            //売価
    var arari;            //粗利（％）

    var i;                //ループカウント
    var j;                //ループカウント

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.38
    var seizo_han;         //製造工程版
    var seizo_shosai;      //製造工程
    var seq_shisaku_seizo; //試作SEQ
//mod end --------------------------------------------------------------------------------

//【シサクイックH24年度対応】修正５ 2012/04/13 Start
    var arrShisaku_seq = new Array();
    var saiyo_column;
    saiyo_column = headerFrm.hdnSaiyou_column.value;
//【シサクイックH24年度対応】修正５ 2012/04/13 End
    // ADD 2015/07/28 TT.Kitazawa【QP@40812】No.10 start
    var fg_koteichk = "";	// 項目固定チェック
    // ADD 2015/07/28 TT.Kitazawa【QP@40812】No.10 end


    //------------------------------------------------------------------------------------
    //                                    初期設定
    //------------------------------------------------------------------------------------
    //編集権限
    if(Kengen.toString() == ConFuncIdEdit.toString()){
        txtReadonly = "";
        txtClass = henshuOkClass;
    }
    //閲覧+Excel権限
    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
        txtReadonly = "readonly";
        txtClass = henshuNgClass;
    }

    //HTML出力オブジェクト設定
    obj = detailDoc.getElementById(ObjectId);
    OutputHtml = "";

    //テーブル名設定
    tablekihonNm = "kihon";
    tableKeisanNm = "keisan";

    //列数取得
    cnt_sample = funXmlRead_3(xmlData, tablekihonNm, "cnt_sanpuru", 0, 0);

    //行数取得
    cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

    //テーブル幅取得
    // MOD 2013/9/6 okano【QP@30151】No.30 start
//    	table_size = 160 * cnt_sample;
    table_size = 215 * cnt_sample;
    // MOD 2013/9/6 okano【QP@30151】No.30 end

	// DEL 2013/7/2 shima【QP@30151】No.37 start
    //試作列数
//    OutputHtml += "<input type=\"hidden\" id=\"cnt_sample\" name=\"cnt_sample\" value=\"" + cnt_sample + "\">";
	// DEL 2013/7/2 shima【QP@30151】No.37 end

	// ADD 2015/05/15 TT.Kitazawa【QP@40812】No.10 start
    // 【試算項目】の表示制御
    var ary_hyoji_chk = new Array (cnt_sample);
	// ADD 2015/05/15 TT.Kitazawa【QP@40812】No.10 end

    //試作SEQ
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        seq_shisaku = funXmlRead_3(xmlData, tableKeisanNm, "seq_shisaku", 0, i);

        //【シサクイックH24年度対応】修正５ 2012/04/16 Start
        arrShisaku_seq[i] = seq_shisaku;
        //【シサクイックH24年度対応】修正５ 2012/04/16 End

        //試作SEQ
        OutputHtml += "<input type=\"hidden\"  id=\"hdnSeq_Shisaku_" + i + "\" name=\"hdnSeq_Shisaku_" + i + "\" value=\"" + seq_shisaku + "\">";
    }

    //------------------------------------------------------------------------------------
    //                                  テーブル生成
    //------------------------------------------------------------------------------------
    OutputHtml += "<table class=\"detail\" id=\"tblList3\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:" + table_size + "px;display:list-item\" bordercolordark=\"#000000\">";

    //サンプルNo ---------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        nm_sanpuru = funXmlRead_3(xmlData, tableKeisanNm, "nm_sanpuru", 0, i);

        //HTML生成
        // MOD 2013/9/6 okano【QP@30151】No.30 start
//        	OutputHtml += "        <td align=\"right\"  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td align=\"right\"  style=\"width:210px;height:18px;\">";
        // MOD 2013/9/6 okano【QP@30151】No.30 end
        //【シサクイックH24年度対応】修正５ 2012/04/16 Start
        //OutputHtml += "            <input type=\"text\" readonly style=\"border-width:0px;text-align:right;\" value=\"" + nm_sanpuru + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" class=\"table_text_view\" id = \"txtSample" + i + "\" readonly style=\"border-width:0px;text-align:right;\" value=\"" + nm_sanpuru + "\" tabindex=\"-1\" />";
        //【シサクイックH24年度対応】修正５ 2012/04/16 End
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    //試算日 -----------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
    	// ADD 2015/07/28 TT.Kitazawa【QP@40812】No.10 start
    	// 表示チェックの初期化
    	ary_hyoji_chk[i] = 0;
    	// 項目固定チェック
        fg_koteichk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
        // ADD 2015/07/28 TT.Kitazawa【QP@40812】No.10 end

        //XMLデータ取得
        shisan_date = funXmlRead_3(xmlData, tableKeisanNm, "shisan_date", 0, i);

        //【QP@00342】試算中止
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        var val = "";
        if(fg_chusi == "1"){
        	val = "試算中止";
        }
        else{
        	// ADD 2015/07/28 TT.Kitazawa【QP@40812】No.10 star
        	// 項目固定チェックがONで、試算日が設定されている時、表示する（生管ｽﾃｰﾀｽはﾁｪｯｸしない）
        	if((shisan_date != "") && (fg_koteichk == "1") ) {
        		ary_hyoji_chk[i] = 1;
        	}
        	// ADD 2015/07/28 TT.Kitazawa【QP@40812】No.10 end

        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 star
        	////【QP@10713】シサクイック改良 No.132011/10/26 TT H.SHIMA ADD START 「試算日も生３以前は表示しない。」
//            shisan_date = seikan_status_check(shisan_date);
            shisan_date = seikan_status_check(shisan_date, ary_hyoji_chk[i]);
            ////【QP@10713】シサクイック改良 No.132011/10/26 TT H.SHIMA ADD END   「試算日も生３以前は表示しない。」
        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 end

            val = shisan_date;
        }

        //HTML生成
        // MOD 2013/9/6 okano【QP@30151】No.30 start
//        	OutputHtml += "        <td style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td style=\"height:18px;\">";
        // MOD 2013/ okano【QP@30151】No.30 end
        //【シサクイックH24年度対応】修正５ 2012/04/16 Start
//        OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\"  value=\"" + val + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" class=\"table_text_view\" id=\"txtShisanHi_" + i + "\" readonly style=\"text-align:right;\"  value=\"" + val + "\" tabindex=\"-1\" />";
        //【シサクイックH24年度対応】修正５ 2012/04/16 End
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    //原価計/ケース -------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        kesu_genkake = funXmlRead_3(xmlData, tableKeisanNm, "kesu_genkake", 0, i);
        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 star
//        kesu_genkake = seikan_status_check(kesu_genkake);
        kesu_genkake = seikan_status_check(kesu_genkake, ary_hyoji_chk[i]);
        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 end

        //試算中止列は非表示
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        if(fg_chusi == "1"){
        	kesu_genkake = "";
        }

		//【QP@10713】2011/10/26 TT H.SHIMA ADD START 「「○○(円)/○○」の表示に変更」
        //HTML生成
        // MOD 2013/9/6 okano【QP@30151】No.30 start
//        	OutputHtml += "        <td  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td  style=\"height:18px;\">";
        // MOD 2013/9/6 okano【QP@30151】No.30 end
        if(kesu_genkake == ""){
        	//【シサクイックH24年度対応】修正５ 2012/04/16 Start
        	// OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"genkakei" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "\" tabindex=\"-1\" />";
        	//【シサクイックH24年度対応】修正５ 2012/04/16 End
        }
        else{
        	//【シサクイックH24年度対応】修正５ 2012/04/16 Start
       		// OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "（円）/ケース\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"genkakei" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "（円）/ケース\" tabindex=\"-1\" />";
        	//【シサクイックH24年度対応】修正５ 2012/04/16 End
        }

        //【QP@10713】2011/10/26 TT H.SHIMA ADD END    「「○○(円)/○○」の表示に変更」
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    //原価計/個 ---------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        ko_genkake = funXmlRead_3(xmlData, tableKeisanNm, "ko_genkake", 0, i);
        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 star
//        ko_genkake = seikan_status_check(ko_genkake);
        ko_genkake = seikan_status_check(ko_genkake, ary_hyoji_chk[i]);
        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 end

        //試算中止列は非表示
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        if(fg_chusi == "1"){
        	ko_genkake = "";
        }

        //【QP@10713】2011/10/26 TT H.SHIMA ADD START 「「○○(円)/○○」の表示に変更」
        //HTML生成
        // MOD 2013/9/6 okano【QP@30151】No.30 start
//        	OutputHtml += "        <td  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td  style=\"width:height:18px;\">";
        // MOD 2013/9/6 okano【QP@30151】No.30 end
        if(ko_genkake == ""){
        	//【シサクイックH24年度対応】修正５ 2012/04/16 Start
        	//OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"genkakeiKo" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "\" tabindex=\"-1\" />";
        	//【シサクイックH24年度対応】修正５ 2012/04/16 End
        }
        else{
        	//【シサクイックH24年度対応】修正５ 2012/04/16 Start
        	//OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "（円）/個&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"genkakeiKo" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "（円）/個&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	//【シサクイックH24年度対応】修正５ 2012/04/16 End
        }
        //【QP@10713】2011/10/26 TT H.SHIMA ADD END    「「○○(円)/○○」の表示に変更」
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    //原価計/KG --------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        kg_genkake = funXmlRead_3(xmlData, tableKeisanNm, "kg_genkake", 0, i);
        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 star
//        kg_genkake = seikan_status_check(kg_genkake);
        kg_genkake = seikan_status_check(kg_genkake, ary_hyoji_chk[i]);
       // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 end

        //試算中止列は非表示
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        if(fg_chusi == "1"){
        	kg_genkake = "";
        }

        //【QP@10713】2011/10/26 TT H.SHIMA ADD START 「「○○(円)/○○」の表示に変更」
        //HTML生成
        // MOD 2013/9/6 okano【QP@30151】No.30 start
//        	OutputHtml += "        <td  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td  style=\"height:18px;\">";
        // MOD 2013/9/6 okano【QP@30151】No.30 end
        if(kg_genkake == ""){
        	OutputHtml += "            <input type=\"text\" id=\"txtKgGenkake_" + i + "\" name=\"txtKgGenkake_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genkake + "\" tabindex=\"-1\" />";
        }
        else{
        	//【QP@10713】2012/03/06 TT H.SHIMA MOD START 「原価計/個を表示していた不具合修正」
//        	OutputHtml += "            <input type=\"text\" id=\"txtKgGenkake_" + i + "\" name=\"txtKgGenkake_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "（円）/kg&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id=\"txtKgGenkake_" + i + "\" name=\"txtKgGenkake_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genkake + "（円）/kg&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	//【QP@10713】2012/03/06 TT H.SHIMA MOD END   「原価計/個を表示していた不具合修正」
        }
        //【QP@10713】2011/10/26 TT H.SHIMA ADD END    「「○○(円)/○○」の表示に変更」
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    //売価 ------------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        baika = funXmlRead_3(xmlData, tableKeisanNm, "baika", 0, i);
        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 star
//        baika = seikan_status_check(baika);
        baika = seikan_status_check(baika, ary_hyoji_chk[i]);
        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 end

        //試算中止列は非表示
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        if(fg_chusi == "1"){
        	baika = "";
        }

        //【QP@10713】2011/10/26 TT H.SHIMA ADD START 「「○○(円)/○○」の表示に変更」
        //HTML生成
        // MOD 2013/9/6 okano【QP@30151】No.30 start
//        	OutputHtml += "        <td  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td  style=\"height:18px;\">";
        // MOD 2013/9/6 okano【QP@30151】No.30 end
        if(baika == ""){
        	//【シサクイックH24年度対応】修正５ 2012/04/16 Start
//        	OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"baika" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "\" tabindex=\"-1\" />";
        	//【シサクイックH24年度対応】修正５ 2012/04/16 End
        }
        else{
        	//【シサクイックH24年度対応】修正５ 2012/04/16 Start
//        	OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	OutputHtml += "            <input type=\"text\" id = \"baika" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;\" tabindex=\"-1\" />";
        	//【シサクイックH24年度対応】修正５ 2012/04/16 End
        }
        //【QP@10713】2011/10/26 TT H.SHIMA ADD END    「「○○(円)/○○」の表示に変更」
        OutputHtml += "        </td>";


    }
    OutputHtml += "    </tr>";

    //粗利（％） ---------------------------------------------------------
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        arari = funXmlRead_3(xmlData, tableKeisanNm, "arari", 0, i);
        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 star
//        arari = seikan_status_check(arari);
        arari = seikan_status_check(arari, ary_hyoji_chk[i]);
        // MOD 2015/05/15 TT.Kitazawa【QP@40812】No.10 end
        if(arari == ""){
        }
        else{
        	arari = arari + "　％";
        }

        //試算中止列は非表示
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        if(fg_chusi == "1"){
        	arari = "";
        }

        //HTML生成
        // MOD 2013/9/6 okano【QP@30151】No.30 start
//        	OutputHtml += "        <td  style=\"width:165px;height:18px;\">";
        OutputHtml += "        <td  style=\"height:18px;\">";
        // MOD 2013/9/6 okano【QP@30151】No.30 end
        //【シサクイックH24年度対応】修正５ 2012/04/16 Start
//        OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ arari + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"arari" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ arari + "\" tabindex=\"-1\" />";
        //【シサクイックH24年度対応】修正５ 2012/04/16 End
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";
    OutputHtml += "</table>";

    //------------------------------------------------------------------------------------
    //                                  HTML出力
    //------------------------------------------------------------------------------------
    //HTMLを出力
    obj.innerHTML = OutputHtml;

    //【シサクイックH24年度対応】修正５ 2012/04/16 Start
    if(saiyo_column > 0){
    	funSaiyoDisp(saiyo_column,arrShisaku_seq,cnt_sample);
    }
	//【シサクイックH24年度対応】修正５ 2012/04/16 End

    return true;
}

//========================================================================================
// 生産管理部ｽﾃｰﾀｽチェック
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 更新者：H.Shima
// 更新日：2015/10/22
// 引数  ：val 項目値
//       ：chk 表示 = 1、非表示 = 0  （試算日が設定されている時、=1）
//========================================================================================
// MOD 2015/10/22 TT.Shima【QP@40812】No.10 start
// MOD 2015/03/03 TT.Kitazawa【QP@40812】No.10 start
//function seikan_status_check(val){
function seikan_status_check(val, chk){
	// 試算結果の表示：生管ｽﾃｰﾀｽをﾁｪｯｸしない
	var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

	var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
    var st_seikan = headerFrm.hdnStatus_seikan.value;
    var st_gentyo = headerFrm.hdnStatus_gentyo.value;
    var st_kojo = headerFrm.hdnStatus_kojo.value;
    var st_eigyo = headerFrm.hdnStatus_eigyo.value;

//    //生産管理部ｽﾃｰﾀｽが < 3　の場合
//    if( st_seikan < 3 ){
//    	val = "";
//    }
//
//	// 試算日が設定されている時、ｽﾃｰﾀｽに関係なく表示
//	if(chk == 0) {
//		val = "";
//	}

    //生産管理部ｽﾃｰﾀｽが < 3　の場合
    // かつ 試算日が設定されていない場合、表示しない
    if ( st_seikan < 3 && chk == 0) {
    	val = "";
    }

//MOD 2015/03/03 TT.Kitazawa【QP@40812】No.10 end
//MOD 2015/10/22 TT.Shima【QP@40812】No.10 end
    return val;
}

//========================================================================================
// 承認ボタン押下処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
//========================================================================================
function funToroku() {

	var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    var retVal;

    //承認ボタンにフォーカスセット
    parent.header.focus();

    args = new Array();
    args[0] = headerDoc;
	args[1] = detailDoc;

    //担当者検索画面を起動する
    retVal = funOpenModalDialog("../SQ160GenkaShisan_Eigyo/GenkaShisan_Eigyo_Status.jsp", args, "dialogHeight:380px;dialogWidth:500px;status:no;scroll:no");

	//登録時完了
	if(retVal){
		//再表示
	    funGetKyotuInfo(1);

	    // ADD 2015/05/15 TT.Kitazawa【QP@40812】No.6 start
	    if(retVal == "mail") {
	    	// 終了メッセージの後、メール起動
	    	funMail();
	    }
        // ADD 2015/05/15 TT.Kitazawa【QP@40812】No.6 end

	    //変更フラグ初期化
	    headerFrm.FgHenkou.value=false;
	}
	//登録未完了
	else{
		//何もしない
	}

    return true;

}

//========================================================================================
// 変更フラグ
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：変更フラグをonにする
//========================================================================================
function setFg_Henkou(){

    //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var headerFrm = parent.header.document.frm00;

    //変更フラグをonにする
    headerFrm.FgHenkou.value = "true";

    return true;

}


//========================================================================================
// 整数部カンマ挿入
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 概要  ：整数部3桁毎にカンマを挿入する
//========================================================================================
function setKanma(obj){

    obj.value = funAddComma(obj.value);
    return true;
}

//========================================================================================
// 年月挿入
// 作成者：Y.Nishigawa
// 作成日：2009/10/29
//========================================================================================
function setNenGetu(obj){
/*
	var val = obj.value;
	try{
		if(val == ""){
		}
		else{
			var nen = val.indexOf("年");
			var tuki = val.indexOf("月");

			if(nen == -1){
				var ret_nen = val.slice(0,4);
				ret_nen = ret_nen + "年" + val.slice(4);
				val = ret_nen;
			}
			if(tuki == -1){
				val = val + "月";
			}
		}
	}catch(e){

    }

    obj.value = val;
*/
}

//========================================================================================
// 試算期日チェック
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 更新者：H.Shima
// 更新日：2012/04/05
//========================================================================================
function chkKizitu(obj){

	//オブジェクトのvalue値取得
	var val = obj.value;

	//【シサクイックH24対応】No41 Start
	//配列に分解
	if(val.split("/").length == 3){
		//「/」で分解
		strTemps = val.split("/");
	}else if(val.length == 6){
		//区切り文字無し6桁
		strdate = val.substring(0,2) + "/" + val.substring(2,4) + "/" + val.substring(4,6);
		strTemps = strdate.split("/");
	}else if(val.length == 7){
		//区切り文字無し7桁
		strdate = val.substring(0,3) + "/" + val.substring(3,5) + "/" + val.substring(5,7);
		strTemps = strdate.split("/");
	}else if(val.length == 8){
		//区切り文字無し8桁
		strdate = val.substring(0,4) + "/" + val.substring(4,6) + "/" + val.substring(6,8);
		strTemps = strdate.split("/");
	}else{
		return true;
	}

	//年が4桁未満の場合に、フォーマットを変更
	if(strTemps[0].length < 4){

		//現在の年を取得
		var date = new Date();						//オブジェクトの生成
		var year = date.getYear().toString();		//現在の年を取得
		var f_year = year.substring(0,1);			//年の一文字目を取得（フォーマット用）

		//年の一文字目に現在の年数を指定
		var formatdate = f_year + ("00" + strTemps[0]).slice(-3) + "/" + ("0" + strTemps[1]).slice(-2) + "/" + ("0" + strTemps[2]).slice(-2)

		val = formatdate;
	}else if(strTemps[0].length >= 5){
		return true;
	}
	//【シサクイックH24対応】No41 End

	if(hiduke == val){
//		return;
		return false;
	}

	hiduke = val;

	//妥当な日付の場合にチェック
	if(isDate(val)){

		//曜日配列
		var myTbl = new Array("日","月","火","水","木","金","土");
		var arrDate = val.split("/");

		//入力日付設定
		var date_nyuryoku  = new Date(arrDate[0] , arrDate[1] - 1 ,arrDate[2]);

		//現在日時設定
		var date_chk = new Date();
		date_chk  = new Date(date_chk.getYear() , date_chk.getMonth() ,date_chk.getDate());

		//過去日のチェック
		if(date_nyuryoku.getTime() < date_chk.getTime()){

			//メッセージ表示
			funInfoMsgBox(E000020);

			//値初期化
			hiduke = "";
			obj.value="";
//			return;
			return false;



			//確認メッセージ
			/*if(funConfMsgBox(E000015) == ConBtnYes){
				return;
			}
			else{
				obj.value="";
				return;
			}*/
		}

		//5営業日チェック
		var eigyo = 5;
		for(var i=0; i<eigyo; i++){
			//現在日時取得
			var baseSec = date_chk.getTime();

			//日数 * 1日のミリ秒数（1日分）
		    var addSec = 1 * 86400000;

		    //現在日時に1日を加算
		    var targetSec = baseSec + addSec;

		    //加算日時を設定
		    date_chk.setTime(targetSec);

		    //加算日時の曜日取得
		    var youbi = myTbl[date_chk.getDay()];

		    //土・日の場合
		    if(youbi == "土" || youbi == "日"){
		    	//カウントしない
		    	i--;
		    }
		}

		//営業日チェック（入力日が5営業日以内の場合）
		if( date_nyuryoku.getTime() <= date_chk.getTime() ){
			//確認メッセージ
			if(funConfMsgBox(E000016) == ConBtnYes){
//				return;
				return true;
			}
			else{
				hiduke = "";
				obj.value="";
//				return;
				return false;
			}
		}
	}
	return true;
}
//日付妥当性チェック
function isDate(text) {
    if (text.length == 0 || text== "") {
        return false;
    }
    var arrDate = text.split("/");
    if(arrDate.length == 3) {
        var date = new Date(arrDate[0] , arrDate[1] - 1 ,arrDate[2]);
        if(date.getFullYear() == arrDate[0] &&
          (date.getMonth() == arrDate[1] - 1) &&
           date.getDate() == arrDate[2]) {
            return true;
        }
    }
    return false;
}




//========================================================================================
// 空白置換関数（"" → "&nbsp;"）
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 概要  ："" → "&nbsp;" へ置換する
//========================================================================================
function funKuhakuChg(strChk) {

    //空白の場合
    if(strChk == ""){
        return "";
    }
    //空白でない場合
    else{
        return strChk;
    }

}


//========================================================================================
// コンボボックスの権限編集
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①obj    :コンボボックスオブジェクト
// 概要  ：コンボボックスの権限編集
//========================================================================================
function kengenComboSetting(obj){

    //ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;

    //編集権限
    if(Kengen.toString() == ConFuncIdEdit.toString()){
        obj.style.backgroundColor = henshuOkColor;
        obj.disabled = false;
    }
    //閲覧+Excel権限
    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
        obj.style.backgroundColor = henshuNgColor;
        obj.disabled = true;
    }

    return true;
}

//========================================================================================
// テキストエリアの権限編集
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①obj    :テキストエリアオブジェクト
// 概要  ：テキストエリアの権限編集
//========================================================================================
function kengenTextareaSetting(obj){

    //ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;

    //編集権限
    if(Kengen.toString() == ConFuncIdEdit.toString()){
        obj.style.backgroundColor = henshuOkColor;
        obj.readOnly = false;
    }
    //閲覧+Excel権限
    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
        obj.style.backgroundColor = henshuNgColor;
        obj.readOnly = true;
    }

    return true;
}

//========================================================================================
// ラジオボタンの権限編集
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①obj    :ラジオボタンオブジェクト
// 概要  ：ラジオボタンの権限編集
//========================================================================================
function kengenRadioSetting(obj){

    kengenBottunSetting(obj);

    return true;
}

//========================================================================================
// チェックボックスの権限編集
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①obj    :チェックボックスオブジェクト
// 概要  ：チェックボックスの権限編集
//========================================================================================
function kengenCheckboxSetting(obj){

    kengenBottunSetting(obj);

    return true;
}

//========================================================================================
// ボタンの権限編集
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①obj    :ラジオボタンオブジェクト
// 概要  ：ラジオボタンの権限編集
//========================================================================================
function kengenBottunSetting(obj){

    //ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;

    //編集権限
    if(Kengen.toString() == ConFuncIdEdit.toString()){
        obj.disabled = false;
    }
    //閲覧+Excel権限
    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
        obj.disabled = true;
    }
  //20160628  KPX@1502111_No.10 ADD start
    //画面ID=190、閲覧権限の場合
    else if(Kengen.toString() == 91){
        obj.disabled = true;
    }
  //20160628  KPX@1502111_No.10 ADD end
    return true;
}

//========================================================================================
// 小数0埋め、切り上げ処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①オブジェクト
//       ：②桁数
// 概要  ：小数0埋め、切り上げ処理
//========================================================================================
function funShosuUp(obj, keta){

    obj.value = funShosuKiriage(obj.value, keta);

    return true;
}

//========================================================================================
// 小数0埋め、切捨て処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①オブジェクト
//       ：②桁数
// 概要  ：小数0埋め、切捨て処理
//========================================================================================
function funShosuKiri(obj, keta){

    obj.value = funShosuKirisute(obj.value, keta);

    return true;
}



//========================================================================================
// コード0埋め処理
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①オブジェクト
// 概要  ：コード0埋め処理
//========================================================================================
function funInsertCdZero(obj){

    var frm = document.frm00;

    funBuryZero(obj, frm.hdnCdketasu.value);
    return true;
}


//========================================================================================
// 売価単位設定
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 編集者：H.Shima
// 編集日：2013/07/02
// 引数  ：①オブジェクト
// 概要  ：原価単位→売価単位へ設定する
//========================================================================================
function baikaTaniSetting(obj){

    var detailFrm = document.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
	var detailDoc = parent.detail.document;

//    detailFrm.txtBaikaTani.value = detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].innerText;
    detailDoc.getElementById("txtBaikaTani" + obj).value = detailDoc.getElementById("ddlGenkaTani" + obj ).options[detailDoc.getElementById("ddlGenkaTani" + obj).selectedIndex].innerText;

    return true;
}


//========================================================================================
// 日付フォーマット設定呼び出し
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①オブジェクト
// 概要  ：yyyy/mm/dd形式へ変換する
//========================================================================================
function hidukeSetting(obj){

    //obj.value = funDateFomatChange(obj.value);
	FormatDateYYYYMMDD(obj, obj.value, 8);
    return true;
}

//========================================================================================
// 日付フォーマット設定
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①オブジェクト
// 概要  ：yyyy/mm/dd形式へ変換する
//========================================================================================
function FormatDateYYYYMMDD(obj, val, len){

    str_val = val;

    // ********** 半角数値チェック ********** //
    checkFlg = 0;
    arrNumber = new Array("1","2","3","4","5","6","7","8","9","0","/");
    for(i = 0; i < str_val.length; i++) {
        c_val = str_val.substring(i, i + 1);
        for(j = 0; j < arrNumber.length; j++){
            c_num = arrNumber[j];
            if (c_val == c_num) {
                checkFlg++;
            }
        }
    }

    if (str_val.length != checkFlg) {
        obj.value = val;
        return;
    }

    // ********** 半角数値チェック ********** //

    // ********** 文字列から[/]を除去する ********** //
    ret_val = '';
    for(i = 0; i < val.length; i++){
        c_val = val.substring(i, i + 1);
        if (c_val == '/') {
            c_dumy = '';
        } else {
            c_dumy = c_val;
        }
        ret_val = ret_val + c_dumy;
    }

    str_val = ret_val;

    // ********** 文字列から[/]を除去する ********** //

    // ********** 文字列長チェック ********** //
    if (str_val.length != len) {
        obj.value = val;
        return;
    }

    // ********** 文字列長チェック ********** //

    // ********** 日付チェック ********** //
    n_y = str_val.substring(0,4);
    n_m = str_val.substring(4,6);
    n_d = str_val.substring(6,8);

    monthEndDay = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

    cal = new Date;

    n_day = 0;

    // 月チェック
    if ((n_m < 1) || (12 < n_m)) {
    	obj.value = val;
    	return;
    }

    // うるう年計算
    cal.setYear(n_y);
    cal.setMonth(n_m - 1);
    n_day = monthEndDay[n_m - 1];

    if ((n_m == 2)&&(((n_y%4 == 0)&&(n_y%100 != 0))||(n_y%400 == 0))){
    	n_day = 29;
    }

    if ((n_d < 0) || (n_day < n_d)) {
        obj.value = val;
        return;
    }

    // ********** 日付チェック ********** //

    // ********** フォーマット文字列返還 ********** //
    obj.value = n_y + "/" + n_m + "/" + n_d;

    // ********** フォーマット文字列返還 ********** //
}

//========================================================================================
// 終了ボタン押下時
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 概要  ：画面を終了し、試作データ一覧画面へ遷移する
//========================================================================================
function funEnd(){

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var mode = 1;

    //承認ボタンにフォーカスセット
    parent.header.focus();

    //------------------------------------------------------------------------------------
    //                               入力項目の変更確認
    //------------------------------------------------------------------------------------
    if(frm.FgHenkou.value == "true"){
        //確認メッセージを表示
        if(funConfMsgBox(E000008) == ConBtnYes){

	      	//2010/02/23 NAKAMURA ADD START-----
	      	//排他解除
	      	funcHaitaKaijo(mode)
	        //2010/02/23 NAKAMURA ADD END-------

            //画面を閉じる
            parent.close();

            return true;
        }else{
            //何もしない
            return true;
        }

        return false;
    }else{
    	//処理なし

    }

	//排他解除
	funcHaitaKaijo(mode)

    //画面を閉じる
    parent.close();

    return true;
}

//========================================================================================
// 排他解除処理
// 作成者：E.Nakamura
// 作成日：2011/01/28
// 引数  ：mode  ：処理モード
//           	1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：なし
// 概要  ：終了ボタン押下時　排他制御を解除する
//========================================================================================
function funcHaitaKaijo(mode){

	//ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.strKengenMoto.value;

    //編集権限
    if( Kengen.toString() == "999" ){
    	return true;
    }
    else{

        //------------------------------------------------------------------------------------
        //                                    変数宣言
        //------------------------------------------------------------------------------------
        var frm = document.frm00;    //ﾌｫｰﾑへの参照
        var XmlId = "RGEN0090";
        var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0090");
        var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0090I );
        var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0090O );

        //------------------------------------------------------------------------------------
        //                                  共通情報取得
        //------------------------------------------------------------------------------------
        //引数をXMLﾌｧｲﾙに設定
        if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
            funClearRunMessage2();
            return false;
        }

        //ｾｯｼｮﾝ情報、共通情報を取得
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0090, xmlReqAry, xmlResAry, mode) == false) {
            return false;
        }
    }

    return true;

}

//========================================================================================
// 処理中
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 戻り値：なし
// 概要  ：処理中の画像を表示する
//========================================================================================
function funShowRunMessage2() {

    //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    var frm = parent.detail.document.frm00;

    frm.BtnEveStart.fireEvent('onclick');

    return true;

}

//========================================================================================
// 処理終了
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：なし
// 戻り値：なし
// 概要  ：処理中の画像を非表示にする
//========================================================================================
function funClearRunMessage2() {

    //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    var frm = parent.detail.document.frm00;

    frm.BtnEveEnd.fireEvent('onclick');

    return true;

}


//DEL 2015/05/15 TT.Kitazawa【QP@40812】No.14 start
// 2010/05/12 シサクイック（原価）要望 【案件No12】ヘルプの表示　TT西川 START==============
//var hndl;
//function funHelpDisp() {
//
//	//承認ボタンにフォーカスセット
//    parent.header.focus();
//
//    //ヘルプ表示（同期を取る為に一定周期で実行）
//    hndl = setInterval("funOpenHelp()",1000);
//    return true;
//}
//function funOpenHelp() {
//    try{
//        //ﾍｯﾀﾞｰﾌｫｰﾑ参照
//        var headerFrm = parent.header.document.frm00;
//
//        //各項目の取得
//        var helppath = headerFrm.strHelpPath.value;
//        //ウィンドウの設定
//        var w = screen.availWidth-10;
//        var h = screen.availHeight-30;
//        //ウィンドウオープン
//        var nwin=window.open(helppath,"HelpPage","menubar=no,resizable=yes,scrollbars=yes,width="+w+",height="+h+",left=0,top=0");
//        nwin.document.title="原価試算システム　ヘルプ画面";
//        //一定周期で実行していたものを解除
//        clearInterval(hndl);
//     }catch(e){
//
//     }
//}
// 2010/05/12 シサクイック（原価）要望 【案件No12】ヘルプの表示　TT西川 END  ==============
//DEL 2015/05/15 TT.Kitazawa【QP@40812】No.14 end




//=================================== ステータス設定ダイアログ　JavaScript  ================================

//========================================================================================
// ｽﾃｰﾀｽ設定初期表示
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
//========================================================================================
function funLoad_Status(){

	var opener_header = window.dialogArguments[0].frm00; 	//ﾍｯﾀﾞﾌｫｰﾑ
	var opener_detail = window.dialogArguments[1].frm00;		//明細ﾌｫｰﾑ
    var frm = document.frm00;

    //戻り値の初期化
	window.returnValue = false;

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var XmlId = "RGEN2170";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0010");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0010I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0010O);

    //------------------------------------------------------------------------------------
    //                                  共通情報取得
    //------------------------------------------------------------------------------------
    //引数をXMLﾌｧｲﾙに設定
    var mode = 1;
    if (funReadyOutput_Status(XmlId, xmlReqAry, mode ) == false) {
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2170, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

   	//ステータス取得
   	var st_kenkyu = opener_header.hdnStatus_kenkyu.value;
   	var st_seikan = opener_header.hdnStatus_seikan.value;
   	var st_gentyo = opener_header.hdnStatus_gentyo.value;
   	var st_kojo = opener_header.hdnStatus_kojo.value;
   	var st_eigyo = opener_header.hdnStatus_eigyo.value;

    //現在ｽﾃｰﾀｽ設定
    document.getElementById("divStatusKenkyu_now").innerHTML = funStatusSetting("1",st_kenkyu);
    document.getElementById("divStatusSeikan_now").innerHTML = funStatusSetting("2",st_seikan);
    document.getElementById("divStatusGentyo_now").innerHTML = funStatusSetting("3",st_gentyo);
    document.getElementById("divStatusKojo_now").innerHTML = funStatusSetting("4",st_kojo);
    document.getElementById("divStatusEigyo_now").innerHTML = funStatusSetting("5",st_eigyo);

    //採用ｻﾝﾌﾟﾙNO設定
    funCreateComboBox(frm.ddlSaiyoSample , xmlResAry[2] , 1, 1);
    funDefaultIndex(frm.ddlSaiyoSample, 1);

    //試算依頼
	//現在の営業ステータス　=　1　の場合
	if(st_eigyo == 1){
		frm.chk[1].disabled = false;
	}
	else{
		frm.chk[1].disabled = true;
	}

	//確認完了
	//現在の営業ステータス　=　2　且つ　生産管理部ステータス = 3 の場合
	if(st_eigyo == 2 && st_seikan == 3){
		frm.chk[2].disabled = false;
	}
	else{
		frm.chk[2].disabled = true;
	}

	//採用有無確定（採用有り）
	//（現在の営業ステータス　>=　2　且つ　現在の営業ステータス　<=　3）　且つ　生産管理部ステータス = 3 の場合
	if( ( st_eigyo >= 2 && st_eigyo <= 3 ) && st_seikan == 3){
		frm.chk[3].disabled = false;

	}
	else{
		frm.chk[3].disabled = true;
	}

	//採用有無確定（採用有り）
	//（現在の営業ステータス　>=　2　且つ　現在の営業ステータス　<=　3）　且つ　生産管理部ステータス = 3 の場合
	if( ( st_eigyo >= 2 && st_eigyo <= 3 ) && st_seikan == 3){
		frm.chk[4].disabled = false;

	}
	else{
		frm.chk[4].disabled = true;
	}

	//試算途中での不採用決定
	//現在の生産管理部ステータス　< 3　且つ　営業ステータス < 4 の場合
	if( st_seikan < 3 && st_eigyo < 4 ){
		frm.chk[5].disabled = false;

	}
	else{
		frm.chk[5].disabled = true;
	}

	return true;
}

//========================================================================================
// 採用ｻﾝﾌﾟﾙNoコンボボックスの制御
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
//========================================================================================
function chkSaiyou(){

	var opener_header = window.dialogArguments[0].frm00; 	//ﾍｯﾀﾞﾌｫｰﾑ
	var frm = document.frm00; //ﾌｫｰﾑ参照

	//ｽﾃｰﾀｽ取得
   	var st_kenkyu = opener_header.hdnStatus_kenkyu.value;
   	var st_seikan = opener_header.hdnStatus_seikan.value;
   	var st_gentyo = opener_header.hdnStatus_gentyo.value;
   	var st_kojo = opener_header.hdnStatus_kojo.value;
   	var st_eigyo = opener_header.hdnStatus_eigyo.value;

	//採用有無確定（採用有り） 選択時
	if(frm.chk[3].checked){
		//採用ｻﾝﾌﾟﾙNoコンボボックスを使用可能
		frm.ddlSaiyoSample.disabled = false;
		frm.ddlSaiyoSample.style.backgroundColor=color_henshu;

	}
	//採用有無確定（採用有り）以外 選択時
	else{
		//採用ｻﾝﾌﾟﾙNoコンボボックスを使用不可
		frm.ddlSaiyoSample.disabled = true;
		frm.ddlSaiyoSample.style.backgroundColor=color_read;

		//採用前のｽﾃｰﾀｽ時は採用ｻﾝﾌﾟﾙNO初期化
		if( ( st_eigyo >= 2 && st_eigyo <= 3 ) && st_seikan == 3){
			frm.ddlSaiyoSample.selectedIndex = 0;
		}
	}
}

//========================================================================================
// XMLファイルに書き込み（ｽﾃｰﾀｽ設定画面）
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
// 引数  ：①XmlId  ：XMLID
//       ：②reqAry ：機能ID別送信XML(配列)
//       ：③Mode   ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput_Status(XmlId, reqAry, mode) {

	var opener_header = window.dialogArguments[0].frm00; 	//ﾍｯﾀﾞﾌｫｰﾑ
	var opener_detail = window.dialogArguments[1].frm00;		//明細ﾌｫｰﾑ
	// ADD 2013/7/2 shima【QP@30151】No.37 start
	var detailDoc = window.dialogArguments[1];
	// ADD 2013/7/2 shima【QP@30151】No.37 end
    var frm = document.frm00;

    var i;
    for (i = 0; i < reqAry.length; i++) {
        //画面初期表示（ｽﾃｰﾀｽ設定）
        if (XmlId.toString() == "RGEN2170") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0010
                    funXmlWrite(reqAry[i], "cd_shain", opener_header.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", opener_header.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", opener_header.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", opener_header.txtEdaNo.value, 0);
                    break;
            }
        }
        //登録
        else if(XmlId.toString() == "RGEN2180"){
        	switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2160
                	//選択ﾗｼﾞｵﾎﾞﾀﾝ値取得
                	var j;
                	var st_setting;
                	var radio = document.getElementsByName("chk");
                	for( j=0; j<radio.length; j++ ){
                		if( radio[j].checked ){
                			st_setting = radio[j].value;
                		}
                	}

					// DEL 2013/7/2 shima【QP@30151】No.37 start
                	//-------------------------- [kihon]テーブル格納 -------------------------
                	/*
                	//XML書き込み
                	funXmlWrite(reqAry[i], "st_setting", st_setting, 0);

                    funXmlWrite(reqAry[i], "cd_shain", opener_header.txtShainCd.value, 0);

                    funXmlWrite(reqAry[i], "nen", opener_header.txtNen.value, 0);

                    funXmlWrite(reqAry[i], "no_oi", opener_header.txtOiNo.value, 0);

                    funXmlWrite(reqAry[i], "no_eda", opener_header.txtEdaNo.value, 0);

                    funXmlWrite(reqAry[i], "dt_kizitu", opener_header.txtKizitu.value, 0);

                    funXmlWrite(reqAry[i], "yoryo", opener_detail.txtYouryo.value, 0);

                    funXmlWrite(reqAry[i], "su_iri", opener_detail.txtIrisu.value, 0);

                    funXmlWrite(reqAry[i], "nisugata", opener_detail.txtNisugata.value, 0);

                    funXmlWrite(reqAry[i], "genka", opener_detail.txtGenkaKibo.value, 0);

                    var genka_tani = opener_detail.ddlGenkaTani.options[opener_detail.ddlGenkaTani.selectedIndex].value;
                    funXmlWrite(reqAry[i], "genka_tani", genka_tani, 0);

                    funXmlWrite(reqAry[i], "baika", opener_detail.txtBaikaKibo.value, 0);

                    funXmlWrite(reqAry[i], "sotei_buturyo", opener_detail.txtSoteiButuryo.value, 0);

                    funXmlWrite(reqAry[i], "dt_hatubai", opener_detail.txtHatubaiJiki.value, 0);

                    var hanbai_t = opener_detail.ddlHanbaiKikan_t.options[opener_detail.ddlHanbaiKikan_t.selectedIndex].value;
                    funXmlWrite(reqAry[i], "hanbai_t", hanbai_t, 0);

                    funXmlWrite(reqAry[i], "hanabai_s", opener_detail.txtHanbaiKikan_s.value, 0);

                    var hanabai_k = opener_detail.ddlHanbaiKikan_k.options[opener_detail.ddlHanbaiKikan_k.selectedIndex].value;
                    funXmlWrite(reqAry[i], "hanabai_k", hanabai_k, 0);

                    funXmlWrite(reqAry[i], "keikakuuriage", opener_detail.txtKeikakuUriage.value, 0);

                    funXmlWrite(reqAry[i], "keikakurieki", opener_detail.txtKeikakuRieki.value, 0);

                    funXmlWrite(reqAry[i], "kuhaku_1", opener_detail.txtHanbaigoUriage.value, 0);

                    funXmlWrite(reqAry[i], "kuhaku_2", opener_detail.txtHanbaigoRieki.value, 0);

                    funXmlWrite(reqAry[i], "memo_eigyo", opener_detail.txtGenkaMemoEigyo.value, 0);

                    var no_saiyou = frm.ddlSaiyoSample.options[frm.ddlSaiyoSample.selectedIndex].value;
                    funXmlWrite(reqAry[i], "no_saiyou", no_saiyou, 0);

                    funXmlWrite(reqAry[i], "st_kenkyu", opener_header.hdnStatus_kenkyu.value, 0);

                    funXmlWrite(reqAry[i], "st_seikan", opener_header.hdnStatus_seikan.value, 0);

                    funXmlWrite(reqAry[i], "st_gentyo", opener_header.hdnStatus_gentyo.value, 0);

                    funXmlWrite(reqAry[i], "st_kojo", opener_header.hdnStatus_kojo.value, 0);

                    funXmlWrite(reqAry[i], "st_eigyo", opener_header.hdnStatus_eigyo.value, 0);

					*/
					// DEL 2013/7/2 shima【QP@30151】No.37 end
					// ADD 2013/7/2 shima【QP@30151】No.37 start
                	//XML書き込み
                	funXmlWrite_Tbl(reqAry[i], "kihon", "st_setting", st_setting, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", opener_header.txtShainCd.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", opener_header.txtNen.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", opener_header.txtOiNo.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", opener_header.txtEdaNo.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "dt_kizitu", opener_header.txtKizitu.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "yoryo", opener_detail.txtYouryo.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "su_iri", opener_detail.txtIrisu.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "nisugata", opener_detail.txtNisugata.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_eigyo", opener_detail.txtGenkaMemoEigyo.value, 0);

                    var no_saiyou = frm.ddlSaiyoSample.options[frm.ddlSaiyoSample.selectedIndex].value;
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_saiyou", no_saiyou, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kenkyu", opener_header.hdnStatus_kenkyu.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_seikan", opener_header.hdnStatus_seikan.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_gentyo", opener_header.hdnStatus_gentyo.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kojo", opener_header.hdnStatus_kojo.value, 0);

                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_eigyo", opener_header.hdnStatus_eigyo.value, 0);


                    //-------------------------- [kihonsub]テーブル格納 -------------------------
                    //サンプル毎の基本情報の列数取得
                    recCnt = opener_detail.cnt_sample.value;

                    //XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){

                    	if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN2160", "kihonsub");
                        }

                        // 試作SEQ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);

                        // 原価希望
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "genka", detailDoc.getElementById("txtGenkaKibo"+j).value, j);
                        // 原価希望単位CD
                        var genka_tani = detailDoc.getElementById("ddlGenkaTani"+j).options[detailDoc.getElementById("ddlGenkaTani"+j).selectedIndex].value
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "genka_tani", genka_tani, j);
                        // 売価希望
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "baika", detailDoc.getElementById("txtBaikaKibo"+j).value, j);
                        // 想定物量
                        // ADD 2013/9/6 okano【QP@30151】No.30 start
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "sotei_buturyo_s", detailDoc.getElementById("txtSoteiButuryo_s"+j).value, j);

                        var sotei_buturyo_u = detailDoc.getElementById("ddlSoteiButuryo_u"+j).options[detailDoc.getElementById("ddlSoteiButuryo_u"+j).selectedIndex].value;
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "sotei_buturyo_u", sotei_buturyo_u, j);

                        var sotei_buturyo_k = detailDoc.getElementById("ddlSoteiButuryo_k"+j).options[detailDoc.getElementById("ddlSoteiButuryo_k"+j).selectedIndex].value;
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "sotei_buturyo_k", sotei_buturyo_k, j);
                        // ADD 2013/9/6 okano【QP@30151】No.30 end
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "sotei_buturyo", detailDoc.getElementById("txtSoteiButuryo"+j).value, j);

                        // 発売時期
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "dt_hatubai", detailDoc.getElementById("txtHatubaiJiki"+j).value, j);

                        var hanbai_t = detailDoc.getElementById("ddlHanbaiKikan_t"+j).options[detailDoc.getElementById("ddlHanbaiKikan_t"+j).selectedIndex].value;
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "hanbai_t", hanbai_t, j);

                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "hanabai_s", detailDoc.getElementById("txtHanbaiKikan_s"+j).value, j);

                        var hanabai_k = detailDoc.getElementById("ddlHanbaiKikan_k"+j).options[detailDoc.getElementById("ddlHanbaiKikan_k"+j).selectedIndex].value;
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "hanabai_k", hanabai_k, j);

                        // 計画売上
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "keikakuuriage", detailDoc.getElementById("txtKeikakuUriage"+j).value, j);
                        // 計画利益
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "keikakurieki", detailDoc.getElementById("txtKeikakuRieki"+j).value, j);
                        // 販売後売上
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "kuhaku_1", detailDoc.getElementById("txtHanbaigoUriage"+j).value, j);
                        // 販売後利益
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "kuhaku_2", detailDoc.getElementById("txtHanbaigoRieki"+j).value, j);

                        //中止フラグ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "chusi", detailDoc.getElementById("col_chusi" + j ).value , j);

                    }
                    // ADD 2013/7/2 shima【QP@30151】No.37 end

                    break;
            }
        }
    }
    return true;
}

//========================================================================================
// 登録ボタン押下（ｽﾃｰﾀｽ設定画面）
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
//========================================================================================
function fun_Toroku(){

	var opener_header = window.dialogArguments[0].frm00; 	//ﾍｯﾀﾞﾌｫｰﾑ
	var opener_detail = window.dialogArguments[1].frm00;		//明細ﾌｫｰﾑ
    var frm = document.frm00;

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var XmlId = "RGEN2180";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2160");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2160I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2160O);

    //------------------------------------------------------------------------------------
    //                                  共通情報取得
    //------------------------------------------------------------------------------------

    //【シサクイックH24対応】No41 Start
    //試算依頼ステータス変更時に試算期日をチェック
    // MOD 2013/7/2 okano【QP@30151】No.22 start
	//	var date;
	//	date = opener_header.txtKizitu.value
	//	if(frm.chk[1].checked && date.length > 0){
	//		if(!chkKizitu(opener_header.txtKizitu)){
	//			return false;
	//		}
	//	}
	var date;
	var st_eigyo = opener_header.hdnStatus_eigyo.value;
	var st_seikan = opener_header.hdnStatus_seikan.value;
	date = opener_header.txtKizitu.value
	if(((frm.chk[0].checked && (st_eigyo == 1 || (st_eigyo == 2 && st_seikan == 1))) || frm.chk[1].checked) && date.length > 0){
		if(!chkKizitu(opener_header.txtKizitu)){
			return false;
		}
	}
	// MOD 2013/7/2 okano【QP@30151】No.22 end
	//【シサクイックH24対応】No41 End

	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.3 start
    //XMLの初期化("kihonsub"が実行の度追加される為)
    setTimeout("xmlFGEN2160I.src = '../../model/FGEN2160I.xml';", ConTimer);
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.3 end

    //引数をXMLﾌｧｲﾙに設定
    var mode = 1;
    if (funReadyOutput_Status(XmlId, xmlReqAry, mode ) == false) {
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2180, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //完了ﾒｯｾｰｼﾞの表示
    var dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {

    	//MOD 2015/05/15 TT.Kitazawa【QP@40812】No.6 start
        //正常
//        funInfoMsgBox(dspMsg);

        //戻り値の設定
//	    window.returnValue = true;

    	window.returnValue = "1"
        // ステータス変更の時、メール起動確認メッセージに変更
        if(frm.chk[0].checked) {
        	// 正常終了メッセージ
        	funInfoMsgBox(dspMsg);
        } else {
        	// 「YES」の時、再表示後メール起動
        	if(funConfMsgBox(dspMsg) == ConBtnYes){
        		window.returnValue = "mail"
        	}
        }
      //MOD 2015/05/15 TT.Kitazawa【QP@40812】No.6 start
    }

    //画面を閉じる
    close(self);
    return false;

}

//========================================================================================
// 画面を閉じる（ｽﾃｰﾀｽ設定画面）
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
//========================================================================================
function fun_Close_st(){
	//画面を閉じる
    close(self);
}

//========================================================================================
// 【QP@10713】荷姿自動設定
// 作成者：H.Shima
// 作成日：2011/10/27
// 更新者：H.Shima
// 更新日：2011/11/17
//========================================================================================
function fun_Nisugataset(){

	var frm = document.frm00;

	//容量、容量単位、入り数に値が設定されている
	if(frm.txtYouryo.value != "" && frm.txtYouryo_tani.value != "" && frm.txtIrisu.value != ""){

		var NisugataStr = frm.txtYouryo.value + frm.txtYouryo_tani.value + "/" + frm.txtIrisu.value;

		//荷姿に値が設定されていない
		if(frm.txtNisugata.value == ""){
			frm.txtNisugata.value = NisugataStr;

		//荷姿に値が設定されている、設定された荷姿と計算値が違なる
		}else if(frm.txtNisugata.value != NisugataStr){
			//再計算確認
			if(funConfMsgBox(E000022) == ConBtnYes){
				frm.txtNisugata.value = NisugataStr;
			}
		}
	}

	return true;

}


//========================================================================================
//【シサクイックH24年度対応】採用サンプルNo以外グレー処理
//作成者：H.SHIMA
//作成日：2012/04/16
//更新者：
//更新日：
//引数  ：①saiyoColumn     ：採用サンプル列番号
//        ②arrSampleSeq    ：サンプル列番号(配列)
//        ③seqCnt          ：サンプル列数
//戻り値：なし
//概要  ：採用サンプルNo以外の計算項目列の背景色をグレーにする
//========================================================================================
function funSaiyoDisp(saiyoColumn,arrSampleSeq,seqCnt){
	var gray = "#C0C0C0";
	for(var i = 0; i < seqCnt; i++){
//		alert(saiyoNm + " : " + arrSampleNm[i]);
		if(saiyoColumn != arrSampleSeq[i]){
			document.getElementById("txtSample" + i).style.background = gray;			//サンプルNo
			document.getElementById("txtShisanHi_" + i).style.background = gray;		//試算日
			document.getElementById("genkakei" + i).style.background = gray;			//原価計/ケース
			document.getElementById("genkakeiKo" + i).style.background = gray;			//原価計/個
			document.getElementById("txtKgGenkake_" + i).style.background = gray;		//原価計(円)/kg
			document.getElementById("baika" + i).style.background = gray;				//売価
			document.getElementById("arari" + i).style.background = gray;				//粗利(%)

		}
	}
}

// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.1 start
//========================================================================================
// 印刷ボタン押下
// 作成者：E.Kitazawa
// 作成日：2015/03/03
// 概要  ：印刷を行う
//========================================================================================
function funInsatu(){

  //------------------------------------------------------------------------------------
  //                                    変数宣言
  //------------------------------------------------------------------------------------
  var frm = document.frm00;    //ﾌｫｰﾑへの参照
  var headerFrm = parent.header.document.frm00; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
  var XmlId = "RGEN2240";
  var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2240");
  var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2240I );
  var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2240O );
  var mode = 1;

  parent.header.focus();

  //------------------------------------------------------------------------------------
  //                               入力項目の変更確認
  //------------------------------------------------------------------------------------
  if(frm.FgHenkou.value == "true"){
	  //メッセージを表示
	  funInfoMsgBox(E000009);
	  return false;
  }

  //------------------------------------------------------------------------------------
  //                                     印刷
  //------------------------------------------------------------------------------------
  //XMLの初期化
  setTimeout("xmlFGEN2240I.src = '../../model/FGEN2240I.xml';", ConTimer);

  //引数をXMLﾌｧｲﾙに設定
  if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
      funClearRunMessage2();
      return false;
  }

  //ｾｯｼｮﾝ情報、共通情報を取得
  if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2240, xmlReqAry, xmlResAry, mode) == false) {
      return false;
  }

  //------------------------------------------------------------------------------------
  //                                     結果表示
  //------------------------------------------------------------------------------------
  //結果判定
  if (funXmlRead(xmlResAry[0], "flg_return", 0) == "false") {
      //ｴﾗｰ発生時はﾒｯｾｰｼﾞを表示
      dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
      funInfoMsgBox(dspMsg);
      //処理終了
      return false;
  }

  //ﾌｧｲﾙﾊﾟｽの退避
  frm.strFilePath.value = funXmlRead(xmlFGEN2240O, "URLValue", 0);

  //ﾀﾞｳﾝﾛｰﾄﾞ画面の起動
  funFileDownloadUrlConnect(ConConectGet, frm);

  //処理終了
  return true;

}
// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.1 end

//ADD 2015/05/15 TT.Kitazawa【QP@40812】No.6 start
//========================================================================================
//メール送信ボタン押下
//作成者：E.Kitazawa
//作成日：2015/03/03
//概要  ：メール送信画面を表示
//========================================================================================
function funMail(){
  //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
//	var opener_header = window.dialogArguments[0].frm00; 	//ﾍｯﾀﾞﾌｫｰﾑ
    //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var headerFrm = document.frm00;

  //現在のステータス情報
  var stnew_ary = new Array(5);
  stnew_ary[0] = funStatusSetting("1" ,headerFrm.hdnStatus_kenkyu.value);
  stnew_ary[1] = funStatusSetting("2", headerFrm.hdnStatus_seikan.value);
  stnew_ary[2] = funStatusSetting("3", headerFrm.hdnStatus_gentyo.value);
  stnew_ary[3] = funStatusSetting("4", headerFrm.hdnStatus_kojo.value);
  stnew_ary[4] = funStatusSetting("5", headerFrm.hdnStatus_eigyo.value);

  //試作情報
  // 20150826 TT.Kitazawa mod start
//  var sisaku_ary = new Array(3);
	var sisaku_ary = new Array(4);
	//試作No
	sisaku_ary[0] = headerFrm.txtShainCd.value + "-" + headerFrm.txtNen.value + "-" + headerFrm.txtOiNo.value + "-" + headerFrm.txtEdaNo.value;
	//品名
	sisaku_ary[1] = headerFrm.txtHinNm.value;
	//試算期日
	sisaku_ary[2] = headerFrm.txtKizitu.value;
	//サンプルNo.
	sisaku_ary[3] = headerFrm.hdnNo_iraisampleForMail.value;
	// 20150826 TT.Kitazawa mod end

	//連絡メール送信（原価試算ステータス更新）
  //    ユーザ情報、試作情報、ステータス情報を渡す
	funMailGenkaSisan(xmlUSERINFO_O, sisaku_ary, stnew_ary);

}
//ADD 2015/05/15 TT.Kitazawa【QP@40812】No.6 end

//20160628  KPX@1502111_No.10 ADD start
//========================================================================================
// サンプルコピー処理
// 作成者：E.Kitazawa
// 作成日：2016/06/22
// 引数  ：なし
// 戻り値：
// 概要  ：サンプルコピー指定画面を表示
//========================================================================================
function funSampleCopy() {

	var detailDoc = parent.detail.document;
	var frm = document.frm00;   //ﾌｫｰﾑへの参照
	var sample;					//サンプル名
//20160826 KPX@1502111_No.10 MOD Start
	var chkSaki;				//コピー先チェックフラグ
    var chkMoto;				//コピー元チェックフラグ
//	var chkCnt = 0;				//チェックカウント
    var chkCnt_saki = 0;		//コピー先チェックカウント
    var chkCnt_moto = 0;		//コピー元チェックカウント
//20160826 KPX@1502111_No.10 MOD End
	var retVal;

	var args = new Array();		//子画面に渡すパラメータ

	//列数取得
	var recCnt = frm.cnt_sample.value;

	for( var j = 0; j < recCnt; j++ ){
		// サンプル名
		sample = detailDoc.getElementById("nm_sample"+j).value;
		// コピー先に含めるかどうかのフラグ
		chkSaki = "0";
//20160826 KPX@1502111_No.10 ADD Start
		// コピー元に含めるかどうかのフラグ
		chkMoto = "0";
//20160826 KPX@1502111_No.10 ADD End
		// 試算中止
		if (detailDoc.getElementById("col_chusi"+j).value == "1") {
			chkSaki = "1";
		}
		// 項目固定チェック
		if (detailDoc.getElementById("col_kotei"+ j).value == "1") {
			chkSaki = "1";
		}

//20160826 KPX@1502111_No.10 ADD Start
		// 各項目に入力があるか
		if(funSampCpy(j, "", 0)){
			chkMoto = "1";
		}
//20160826 KPX@1502111_No.10 ADD End
		
//20160826 KPX@1502111_No.10 MOD Start
		// コピー先に含めないサンプル数
		if (chkSaki == '1') {
//			chkCnt = chkCnt + 1;
			chkCnt_saki = chkCnt_saki + 1;
		}
//20160826 KPX@1502111_No.10 MOD End
		
//20160826 KPX@1502111_No.10 ADD Start
		// コピー元に含めないサンプル数
		if(chkMoto == '1') {
			chkCnt_moto = chkCnt_moto + 1;
		}
//20160826 KPX@1502111_No.10 ADD End
		
//20160826 KPX@1502111_No.10 MOD Start
		// フラグを付加する
//		args[j] = sample + ConDelimiter + chkSaki;
		args[j] = sample + ConDelimiter + chkSaki + ConDelimiter + chkMoto;
	}
//20160826 KPX@1502111_No.10 MOD End

//20160826 KPX@1502111_No.10 MOD Start
	//全てのサンプルが試算中止、又は項目固定チェックONか
//	if (recCnt == chkCnt) {
	if (recCnt == chkCnt_saki || recCnt == chkCnt_moto) {
		// コピー先に入れるサンプルがない場合、エラーメッセージを表示
		funErrorMsgBox(E000044);
		return false;
	}
//20160826 KPX@1502111_No.10 MOD End

	//サンプルコピー画面を起動する
	var retVal = funOpenModalDialog("../SQ135SampleCopy/SQ135SampleCopy.jsp", args, "dialogHeight:250px;dialogWidth:450px;status:no;scroll:no");

	//戻り値
	if(retVal == null){
		return true;
	} else if(retVal[0] == "false"){  	// 終了ボタンで閉じられた場合
		return true;
	} else {
		//戻り値：[0]コピー元サンプル列、 [1]コピー先サンプル列
		//コピー元、コピー先が同じ時
		if (retVal[0] == retVal[1]) {
			//上書きの確認
			chkSaki = 1;
			//コピー先が空かどうかチェックする
		} else if (funSampCpy(retVal[0], retVal[1], 1)) {
			//全て空の時
			chkSaki = 0;
		} else {
			//どれかに値が入っている時
			chkSaki = 1;
		}

		if (chkSaki == 1) {
			//確認メッセージ表示
			if(funConfMsgBox(I000018) == ConBtnYes){
				//コピー実行：retVal[0] →  retVal[1]
				funSampCpy(retVal[0], retVal[1], 2);

				//変更フラグをonにする
				setFg_Henkou();
			}

		} else {
			//コピー実行：retVal[0] →  retVal[1]
			funSampCpy(retVal[0], retVal[1], 2);

			//変更フラグをonにする
			setFg_Henkou();
		}

	}
}

//========================================================================================
// 画面情報コピー処理
// 作成者：E.Kitazawa
// 作成日：2016/06/22
// 引数  ：①motoNo   ：コピー元列番号
//       ：②sakiNo   ：コピー先列番号
//       ：③mode     ：処理モード（0：motoNoデータチェック、1：sakiNoデータチェック、2：コピー実行処理）
// 戻り値：true:正常終了（mode=0,1 の時、全て空）、 false:異常あり（mode=0,1 の時、既存データあり）
// 概要  ：サンプルコピー指定画面を表示
//========================================================================================
function funSampCpy(motoNo, sakiNo, mode) {

	var detailDoc = parent.detail.document;
	var st_seikan = parent.header.document.frm00.hdnStatus_seikan.value;
	var no;

	if(mode == 0) {
		no = motoNo;
	} else if(mode == 1) {
		no = sakiNo;
	} else if(mode == 2) {
		//編集可：生管ステータス依頼前のみコピー可
		if (st_seikan < 2) {
			//希望原価、単位
			detailDoc.getElementById("txtGenkaKibo"+sakiNo).value = detailDoc.getElementById("txtGenkaKibo"+motoNo).value;
			detailDoc.getElementById("ddlGenkaTani"+sakiNo).selectedIndex = detailDoc.getElementById("ddlGenkaTani"+motoNo).selectedIndex;
			//希望特約、単位
			detailDoc.getElementById("txtBaikaKibo"+sakiNo).value = detailDoc.getElementById("txtBaikaKibo"+motoNo).value;
			detailDoc.getElementById("txtBaikaTani"+sakiNo).value = detailDoc.getElementById("txtBaikaTani"+motoNo).value;
			//販売期間
			detailDoc.getElementById("ddlHanbaiKikan_t"+sakiNo).selectedIndex = detailDoc.getElementById("ddlHanbaiKikan_t"+motoNo).selectedIndex;
			detailDoc.getElementById("txtHanbaiKikan_s"+sakiNo).value = detailDoc.getElementById("txtHanbaiKikan_s"+motoNo).value;
			detailDoc.getElementById("ddlHanbaiKikan_k"+sakiNo).selectedIndex = detailDoc.getElementById("ddlHanbaiKikan_k"+motoNo).selectedIndex;
			//想定物量
			detailDoc.getElementById("txtSoteiButuryo_s"+sakiNo).value = detailDoc.getElementById("txtSoteiButuryo_s"+motoNo).value;
			detailDoc.getElementById("ddlSoteiButuryo_u"+sakiNo).selectedIndex = detailDoc.getElementById("ddlSoteiButuryo_u"+motoNo).selectedIndex;
			detailDoc.getElementById("ddlSoteiButuryo_k"+sakiNo).selectedIndex = detailDoc.getElementById("ddlSoteiButuryo_k"+motoNo).selectedIndex;
			//想定物量備考
			detailDoc.getElementById("txtSoteiButuryo"+sakiNo).value = detailDoc.getElementById("txtSoteiButuryo"+motoNo).value;

			//初回納品時期（発売時期）
			detailDoc.getElementById("txtHatubaiJiki"+sakiNo).value = detailDoc.getElementById("txtHatubaiJiki"+motoNo).value;
			//計画売上／年
			detailDoc.getElementById("txtKeikakuUriage"+sakiNo).value = detailDoc.getElementById("txtKeikakuUriage"+motoNo).value;
			//計画利益／年
			detailDoc.getElementById("txtKeikakuRieki"+sakiNo).value = detailDoc.getElementById("txtKeikakuRieki"+motoNo).value;

			//販売期間選択変更処理
			var val = detailDoc.getElementById("ddlHanbaiKikan_t" + sakiNo).options[detailDoc.getElementById("ddlHanbaiKikan_t" + sakiNo).selectedIndex].value;
			//スポットの場合
			if( val == "2" ){
				detailDoc.getElementById("ddlHanbaiKikan_k" + sakiNo).disabled = false;
				detailDoc.getElementById("ddlHanbaiKikan_k" + sakiNo).style.backgroundColor=color_henshu;
				detailDoc.getElementById("txtHanbaiKikan_s" + sakiNo).readOnly=false;
				detailDoc.getElementById("txtHanbaiKikan_s" + sakiNo).style.backgroundColor=color_henshu;
			} else {
				//通年の場合
				detailDoc.getElementById("ddlHanbaiKikan_k" + sakiNo).disabled = true;
				detailDoc.getElementById("ddlHanbaiKikan_k" + sakiNo).style.backgroundColor=color_read;
				detailDoc.getElementById("txtHanbaiKikan_s" + sakiNo).readOnly=true;
				detailDoc.getElementById("txtHanbaiKikan_s" + sakiNo).style.backgroundColor=color_read;
			}
		}

		//項目名：空白（編集可）
		detailDoc.getElementById("txtHanbaigoUriage"+sakiNo).value = detailDoc.getElementById("txtHanbaigoUriage"+motoNo).value;
		detailDoc.getElementById("txtHanbaigoRieki"+sakiNo).value = detailDoc.getElementById("txtHanbaigoRieki"+motoNo).value;

		return true;

	} else {
		return false;
	}

	//編集データに値が入っているかどうかチェックする
	//編集可：生管ステータス依頼前のみコピー可
	if (st_seikan < 2) {
		//希望原価、単位
		if (detailDoc.getElementById("txtGenkaKibo"+no).value != "") {
			return false;
		}
		if (detailDoc.getElementById("ddlGenkaTani"+no).selectedIndex > 0) {
			return false;
		}
		//希望特約、単位
		if (detailDoc.getElementById("txtBaikaKibo"+no).value != "") {
			return false;
		}
//20160826 KPX@1502111_No.10 MOD Start
//		if (detailDoc.getElementById("txtBaikaTani"+no).value != "") {
		if (detailDoc.getElementById("txtBaikaTani"+no).value != "" &&
				detailDoc.getElementById("txtBaikaTani"+no).value != "　") {
			return false;
		}
//20160826 KPX@1502111_No.10 MOD End
		
		//販売期間
		if (detailDoc.getElementById("ddlHanbaiKikan_t"+no).selectedIndex > 0) {
			return false;
		}
		if (detailDoc.getElementById("txtHanbaiKikan_s"+no).value != "") {
			return false;
		}
		if (detailDoc.getElementById("ddlHanbaiKikan_k"+no).selectedIndex > 0) {
			return false;
		}
		//想定物量
		if (detailDoc.getElementById("txtSoteiButuryo_s"+no).value != "") {
			return false;
		}
		if (detailDoc.getElementById("ddlSoteiButuryo_u"+no).selectedIndex > 0) {
			return false;
		}
		if (detailDoc.getElementById("ddlSoteiButuryo_k"+no).selectedIndex > 0) {
			return false;
		}
		//想定物量備考
		if (detailDoc.getElementById("txtSoteiButuryo"+no).value != "") {
			return false;
		}
		//初回納品時期（発売時期）
		if (detailDoc.getElementById("txtHatubaiJiki"+no).value != "") {
			return false;
		}
		//計画売上／年
		if (detailDoc.getElementById("txtKeikakuUriage"+no).value != "") {
			return false;
		}
		//計画利益／年
		if (detailDoc.getElementById("txtKeikakuRieki"+no).value != "") {
			return false;
		}
	}

	//項目名：空白（編集可）
	if (detailDoc.getElementById("txtHanbaigoUriage"+no).value != "") {
		return false;
	}
	if (detailDoc.getElementById("txtHanbaigoRieki"+no).value != "") {
		return false;
	}

	return true;
}
//20160628  KPX@1502111_No.10 ADD end
