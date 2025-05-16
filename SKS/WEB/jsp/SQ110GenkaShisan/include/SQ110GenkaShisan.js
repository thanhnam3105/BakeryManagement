
//========================================================================================
// 共通変数
// 作成者：Y.Nishigawa
// 作成日：2011/01/28
//========================================================================================
//色指定
 var color_read="#ffffff";
 var color_henshu="#ffff88";

var CurrentRow;
//【QP@10713】20111114 hagiwara add start
var cnt_sample;
//【QP@10713】20111114 hagiwara add end

//20160513  KPX@1600766 ADD start
// グループ会社単価開示フラグ（リテラルマスタ）
var tankaHyoujiFlg = "";
//20160513  KPX@1600766 ADD end

// 【初期表示不具合対応】20211108 BRC.yanagita ADD start
// 画面初期表示フラグ（原価試算）
var shokiHyoujiFlg = "0";
// 【初期表示不具合対応】20211108 BRC.yanagita ADD end


//========================================================================================
// 初期処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/21
// 概要  ：画面の初期処理を行う
//========================================================================================
function funLoad() {

    return true;

}


//========================================================================================
// ヘッダーフレーム、初期表示処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/21
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad_head() {

    //ﾌｫｰﾑへの参照
    var frm = document.frm00;

  //【QP@30297】add start 20140501
    //共通情報表示
    funGetKyotuInfo(1);
  //【QP@30297】add end 20140501

    return true;

}

//========================================================================================
// 明細フレーム、初期表示処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad_dtl() {

    //基本情報表示
    funKihonHyoji();
    //ADD 20140501 start 【QP@30297】
    funSaveKengenInfo2();
    //ADD 20140501 end
    return true;

}

// ADD start 20140919 【QP@30154】No.11の課題対応
// ========================================================================================
// 画面ロード後、ステータスによってDB値を補正
// 作成者：hisahori
// 作成日：2014/09/19
// 概要 ：営業が確認完了済のデータで、tr_shisan_shisaku_koteiにデータがない場合、値を登録。
// 営業確認完了のデータは全サンプルにおいて項目固定チェックがオンでなければならない。（生管ユーザでチェックはずすことはできる）
// ========================================================================================
// 営業フラグ=3以上、かつ、項目固定チェックがオフの場合
// 生管フラグ≧3 に変更 【KPX@1502111_No7】
//MOD start 20150722 パラメータ追加
//function funLoad_datahosei(){
function funLoad_datahosei(mode){
//MOD end 20150722
	var frm = document.frm00;
	var headerFrm = parent.header.document.frm00;
	var flgcheck = "";
	var recCnt = 0;
	// MOD start 20150722
	// 営業フラグ=3以上、かつ、項目固定チェックがオフの場合
//	if (headerFrm.hdnStatus_eigyo.value >= 3 && frm.chkKoumoku_0.value != "1") {

//20160607  KPX@1502111_No7 MOD start
	//「営3」⇒「生3」 に変更
//	if (headerFrm.hdnStatus_eigyo.value >= 3 ) {
	if (headerFrm.hdnStatus_seikan.value >= 3 ) {
//20160607  KPX@1502111 MOD end
		if (mode == 0) {
			// 項目固定フラグがONで固定ファイルが無いとき
			if (funKoteiChk(1) == 1) {
				// 項目固定フラグのチェックを外す処理を実行した時、再表示

			// 【初期表示不具合対応】20211111 BRC.yanagita ADD start
			// 画面初期表示フラグを「１」に更新
			
				shokiHyoujiFlg = "1"
				funLoad_dtl();
			// 【初期表示不具合対応】20211111 BRC.yanagita ADD end
			};
		} else {
			// 固定ファイルがない時、データを作成する
			flgcheck = "check";
			// 【初期表示不具合対応】20211111 BRC.yanagita ADD start
			// 画面初期表示フラグを「２」に更新
			
			shokiHyoujiFlg = "2"
			// 【初期表示不具合対応】20211111 BRC.yanagita ADD end
		
			funTorokuKoumokuKotei(1);
			funLoad_dtl();
			// 再計算フラグをoffにする（項目固定チェックフラグをDBに登録しているだけなので、再計算は不要。）
			headerFrm.FgSaikeisan.value = "false";
		}
		// MOD end 20150722
	}
}
// ADD end 20140919

//========================================================================================
// 明細フレーム、基本情報表示処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
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
// 作成日：2009/10/23
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
// 作成日：2009/10/23
// 概要  ：画面の資材情報表示処理を行う
//========================================================================================
function funShizaiHyoji() {

    //資材情報取得
    funGetShizaiInfo(1);

    return true;

}

//========================================================================================
// 工場一覧取得
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
// 概要  ：画面の工場一覧取得処理を行う
//========================================================================================
function funKojoChange() {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN1020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN1020");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN1020I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN1020O );
    var mode = 1;

    //------------------------------------------------------------------------------------
    //                                  工場情報取得
    //------------------------------------------------------------------------------------
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN1020, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                  工場情報表示
    //------------------------------------------------------------------------------------
    //工場コンボボックス生成
    funCreateComboBox(frm.ddlSeizoKojo , xmlResAry[2] , 5, 1);


    //桁数設定
    frm.hdnCdketasu.value = funXmlRead(xmlResAry[2], "cd_keta", 0);

    return true;
}


//========================================================================================
// 原料洗い替え
// 作成者：Y.Nishigawa
// 作成日：2009/11/02
// 概要  ：画面の原料洗い替え処理を行う
//========================================================================================
function funAraigae(){

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN0020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0060");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0060I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0060O );
    var mode = 1;

    //空白が選択された場合は処理を行わない
    if(frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value == ""){
        return false;
    }

    //MOD 2015/03/30 TT.Kitazawa【QP@40812】追加-No.2 start
    // 製造会社の変更有無でメッセージを変更
    var msg = "";
    var flgKaisha = 0;
    if (frm.hdnKaisha.value == frm.ddlSeizoKaisya.options[frm.ddlSeizoKaisya.selectedIndex].value) {
    //確認メッセージを表示
// 2010.11.05 Mod Arai Start メッセージ変更 --------------------------------------------------------
    //var msg = "製造工場を" + frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].innerText + "へ変更します。よろしいですか？";
//    var msg = "製造工場を" + frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].innerText + "へ変更し、データ保存まで行います。よろしいですか？";
// 2010.11.05 Mod Arai End -------------------------------------------------------------------------
        msg = "製造工場を" + frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].innerText + "へ変更し、データ保存まで行います。よろしいですか？";
    } else {
        // 製造会社を変更
    	flgKaisha = 1;
        msg = "製造会社を" + frm.ddlSeizoKaisya.options[frm.ddlSeizoKaisya.selectedIndex].innerText + "へ変更し、データ保存まで行います。\\n";
        msg += "変更後、画面を閉じます。よろしいですか？";

    }
    //MOD 2015/03/30 TT.Kitazawa【QP@40812】追加-No.2 end

    if(funConfMsgBox(msg) == ConBtnYes){
        // 2010.10.21 Del Arai 【バグ対応 メッセージ非表示】 Start
        //再計算フラグをオン
        //setFg_saikeisan();
        //再計算フラグをoffにする
        var headerFrm = parent.header.document.frm00;
        headerFrm.FgSaikeisan.value = "false";
        // 2010.10.21 Del Arai 【バグ対応 メッセージ非表示】 End

    }else{

        return false;

    }

    //------------------------------------------------------------------------------------
    //                               原料洗い替え情報取得
    //------------------------------------------------------------------------------------
    //XMLの初期化
    setTimeout("xmlFGEN0060I.src = '../../model/FGEN0060I.xml';", ConTimer);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0020, xmlReqAry, xmlResAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ADD 2015/03/30 TT.Kitazawa【QP@40812】追加-No.2 start
    // 製造会社変更時、画面を閉じる
    if (flgKaisha) {
        //排他解除
        funcHaitaKaijo(mode);
        //終了メッセージを表示
        funInfoMsgBox(S000001);

        //ヘッダーフレームの終了ボタン押下フラグ
        parent.header.window.end_click = true;
        //画面を閉じる
        parent.close();
        return true;
    }
    //ADD 2015/03/30 TT.Kitazawa【QP@40812】追加-No.2 end

    //------------------------------------------------------------------------------------
    //                                原料洗い替え情報表示
    //------------------------------------------------------------------------------------
    //基本情報表示
    funGetKihonInfo(mode);

    //原料情報表示
    //funGetGenryoInfo(mode);

    //Hidden項目に会社設定
    frm.hdnKaisha.value = frm.ddlSeizoKaisya.options[frm.ddlSeizoKaisya.selectedIndex].value;

    //Hidden項目に工場設定
    frm.hdnKojo.value = frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value;

    //終了メッセージを表示
    funInfoMsgBox(S000001);

    return true;
}

//========================================================================================
// 【QP@00342】ｽﾃｰﾀｽ履歴画面を起動
// 作成者：Y.Nishigawa
// 作成日：2009/10/20
// 引数  ：なし
//========================================================================================
function funStatusRireki_btn() {

	//ﾌｫｰﾑへの参照
	var headerFrm = parent.header.document.frm00;

	//【QP@00342】承認ボタンにフォーカスセット
    parent.header.focus();

    var XmlId = "RGEN2160";
    var FuncIdAry = new Array(ConResult,ConUserInfo);
    var xmlReqAry = new Array(xmlUSERINFO_I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O);

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
// 原価試算、共通情報取得＆表示処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/21
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
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0010", "FGEN2020", "FGEN2130");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0010I, xmlFGEN2020I, xmlFGEN2130I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0010O, xmlFGEN2020O, xmlFGEN2130O );


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

    //【QP@00342】現在ｽﾃｰﾀｽの設定
    frm.hdnStatus_kenkyu.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0);
    frm.hdnStatus_seikan.value = funXmlRead_3(xmlResAry[3], "kihon", "st_seisan", 0, 0);
    frm.hdnStatus_gentyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_gensizai", 0, 0);
    frm.hdnStatus_kojo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kojo", 0, 0);
    frm.hdnStatus_eigyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_eigyo", 0, 0);

    //【QP@00342】所属部署フラグ設定------------------------------------------------------------------------
    frm.hdnBusho_kenkyu.value = funXmlRead_3(xmlResAry[4], "table", "flg_kenkyu", 0, 0);
	frm.hdnBusho_seikan.value = funXmlRead_3(xmlResAry[4], "table", "flg_seikan", 0, 0);
	frm.hdnBusho_gentyo.value = funXmlRead_3(xmlResAry[4], "table", "flg_gentyo", 0, 0);
	frm.hdnBusho_kojo.value = funXmlRead_3(xmlResAry[4], "table", "flg_kojo", 0, 0);
	frm.hdnBusho_eigyo.value = funXmlRead_3(xmlResAry[4], "table", "flg_eigyo", 0, 0);

    //【QP@00342】試作コード表示
    frm.txtShainCd.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_shain", 0, 0);
    frm.txtNen.value = funXmlRead_3(xmlResAry[2], "kihon", "nen", 0, 0);
    frm.txtOiNo.value = funXmlRead_3(xmlResAry[2], "kihon", "no_oi", 0, 0);
    frm.txtEdaNo.value = funXmlRead_3(xmlResAry[2], "kihon", "no_eda", 0, 0);

    //品名表示
    frm.txtHinNm.value = funXmlRead_3(xmlResAry[2], "kihon", "hin", 0, 0);

    //【QP@00342】採用サンプルNo設定
    frm.txtSaiyou.value = funXmlRead_3(xmlResAry[2], "kihon", "saiyo_nm", 0, 0);

    //【QP@00342】試算期日設定
    frm.txtKizitu.value = funXmlRead_3(xmlResAry[2], "kihon", "dt_kizitu", 0, 0);

    //【QP@00342】枝番種類設定
    frm.txtShuruiEda.value = funXmlRead_3(xmlResAry[2], "kihon", "shurui_eda", 0, 0);

    // 2010.10.01 Add Arai 【QP@00412_シサクイック改良　案件№27】 START
    //依頼番号設定
    divIraiNo.innerText = "IR@" + funXmlRead_3(xmlResAry[2], "kihon", "no_irai", 0, 0);
	// 2010.10.01 Add Arai 【QP@00412_シサクイック改良　案件№27】 END

// ADD start 【H24年度対応】 2012/4/18 hisahori
	frm.hdnNmMotHin.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_motHin", 0, 0);
// ADD end 【H24年度対応】 2012/4/18 hisahori

    //再計算ボタンの権限編集
    //kengenBottunSetting(frm.btnSaikeisan);

    //登録ボタンの権限編集
    //kengenBottunSetting(frm.btnToroku);


    //【QP@00342】各項目の設定（ヘッダ部分のHidden項目）
    frm.strHaitaCdShisaku.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_shisaku_haita", 0, 0);
    frm.strHaitaNmShisaku.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_shisaku_haita", 0, 0);
    frm.strHaitaKaisha.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_kaisha_haita", 0, 0);
    frm.strHaitaBusho.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_busho_haita", 0, 0);
    frm.strHaitaUser.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_user_haita", 0, 0);
    frm.strKengenMoto.value = funXmlRead_3(xmlResAry[2], "kihon", "kengen_moto", 0, 0);

    //【QP@00342】現在ｽﾃｰﾀｽの設定
    frm.hdnStatus_kenkyu.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kenkyu", 0, 0);
    frm.hdnStatus_seikan.value = funXmlRead_3(xmlResAry[3], "kihon", "st_seisan", 0, 0);
    frm.hdnStatus_gentyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_gensizai", 0, 0);
    frm.hdnStatus_kojo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_kojo", 0, 0);
    frm.hdnStatus_eigyo.value = funXmlRead_3(xmlResAry[3], "kihon", "st_eigyo", 0, 0);

    //メッセージボックス表示
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;
    //排他の場合
    if(frm.strKengenMoto.value == "999"){
        //表示
        funHaitaDisp();

        headerFrm.btnTyusi.disabled = true;
        headerFrm.btnEdaban.disabled = true;
        headerFrm.btnSaikeisan.disabled = true;
        headerFrm.btnToroku.disabled = true;
    }

    // 2010/05/12 シサクイック（原価）要望 【案件No12】ヘルプの表示　TT西川 START==============
    frm.strHelpPath.value = funXmlRead_3(xmlResAry[2], "kihon", "help_file", 0, 0);
    // 2010/05/12 シサクイック（原価）要望 【案件No12】ヘルプの表示　TT西川 END  ==============


    //【シサクイックH24年度対応】修正５ 2012/04/16 Add Start
    frm.hdnSaiyou_column.value = funXmlRead_3(xmlResAry[2], "kihon", "saiyo_no", 0, 0);
    //【シサクイックH24年度対応】修正５ 2012/04/16 Add End


    //------------------------------------------------------------------------------------
    //                                 明細ﾌﾚｰﾑ描画
    //------------------------------------------------------------------------------------
    //明細ﾌﾚｰﾑの描画
    parent.detail.location.href="GenkaShisan_dtl.jsp";

    //処理終了
    return true;
}
function funHaitaDisp() {
    //ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;
    //【QP@00342】各項目の取得
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

    //【QP@00342】承認ボタンにフォーカスセット
    parent.header.focus();

    //【QP@00342】各項目の取得
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
// 原価試算、基本情報取得＆表示処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
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
    //製造会社コンボボックス生成
    funCreateComboBox(frm.ddlSeizoKaisya , xmlResAry[2] , 2, 0);
    //製造会社コンボボックス選択
    funDefaultIndex(frm.ddlSeizoKaisya, 2);
    //編集権限
    kengenComboSetting(frm.ddlSeizoKaisya);
    //Hidden項目に会社設定
    frm.hdnKaisha.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_kaisya", 0, 0);
    //Hidden項目に工場設定
    frm.hdnKojo.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_kojyo", 0, 0);

    //------------------------------------- 製造工場 -------------------------------------
    //製造工場コンボボックス生成
    funCreateComboBox(frm.ddlSeizoKojo , xmlResAry[2] , 3, 1);
    //製造工場コンボボックス選択
    funDefaultIndex(frm.ddlSeizoKojo, 3);
    //編集権限
    kengenComboSetting(frm.ddlSeizoKojo);
    //工場変更ボタン権限編集
    kengenBottunSetting(frm.btnArigae);

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


    //---------------------------------- 【QP@00342】容量（数値入力）---------------------------------
    frm.txtYouryo.value = funXmlRead_3(xmlResAry[2], "kihon", "yoryo", 0, 0);
    setKanma(frm.txtYouryo);

    //---------------------------------- 【QP@00342】容量（単位）---------------------------------
    frm.txtYouryo_tani.value = funXmlRead_3(xmlResAry[2], "kihon", "yoryo_tani", 0, 0);


    //-------------------------------------- 【QP@00342】入り数 --------------------------------------
    frm.txtIrisu.value = funXmlRead_3(xmlResAry[2], "kihon", "irisu", 0, 0);
    setKanma(frm.txtIrisu);


    //--------------------------------------- 【QP@00342】荷姿 ---------------------------------------
    frm.txtNisugata.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_nisugata", 0, 0);


    //------------------------------------- 取扱温度 -------------------------------------
    frm.txtOndo.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_ondo", 0, 0);


    //------------------------------------- 賞味期間 -------------------------------------
    frm.txtShomiKikan.value = funXmlRead_3(xmlResAry[2], "kihon", "nm_kikan", 0, 0);

// DEL 2013/7/2 shima【QP@30151】No.37 start
//    //------------------------------------- 【QP@00342】原価希望 -------------------------------------
//    frm.txtGenkaKibo.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_genka", 0, 0);
//    setKanma(frm.txtGenkaKibo);
//
//
//    //----------------------------------- 【QP@00342】原価希望単位 -----------------------------------
//    frm.txtGenkaTani.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_genka_nm_tani", 0, 0);
//    frm.hdnGenkaTaniCd.value = funXmlRead_3(xmlResAry[2], "kihon", "kibo_genka_cd_tani", 0, 0);
//
//
//    //-------------------------------------【QP@00342】 売価希望 -------------------------------------
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
//	//------------------------------------- 【QP@00342】発売時期 -------------------------------------
//    frm.txtHatubaiJiki.value = funXmlRead_3(xmlResAry[2], "kihon", "ziki_hatubai", 0, 0);
//
//
//    //------------------------------------- 【QP@00342】販売期間 -------------------------------------
//    //販売期間（通年orスポット）
//    frm.txtHanbaiKikan_t.value = funXmlRead_3(xmlResAry[2], "kihon", "kikan_hanbai_t", 0, 0);
//
//    //販売期間（数値）
//    frm.txtHanbaiKikan_s.value = funXmlRead_3(xmlResAry[2], "kihon", "kikan_hanbai_suti", 0, 0);
//
//    //販売期間（ヶ月）
//    frm.txtHanbaiKikan_k.value = funXmlRead_3(xmlResAry[2], "kihon", "kikan_hanbai_k", 0, 0);
//
//
//    //------------------------------------- 【QP@00342】計画売上 -------------------------------------
//    frm.txtKeikakuUriage.value = funXmlRead_3(xmlResAry[2], "kihon", "keikaku_uriage", 0, 0);
//
//
//    //------------------------------------- 【QP@00342】計画利益 -------------------------------------
//    frm.txtKeikakuRieki.value = funXmlRead_3(xmlResAry[2], "kihon", "keikaku_rieki", 0, 0);
//
//
//    //------------------------------------ 【QP@00342】販売後売上 ------------------------------------
//    frm.txtHanbaigoUriage.value = funXmlRead_3(xmlResAry[2], "kihon", "hanbaigo_uriage", 0, 0);
//
//
//    //------------------------------------ 【QP@00342】販売後利益 ------------------------------------
//    frm.txtHanbaigoRieki.value = funXmlRead_3(xmlResAry[2], "kihon", "hanbaigo_rieki", 0, 0);
//
//
//    //------------------------------------ 【QP@00342】製造ロット ------------------------------------
//    frm.txtSeizoRot.value = funXmlRead_3(xmlResAry[2], "kihon", "seizo_roto", 0, 0);
// DEL 2013/7/2 shima【QP@30151】No.37 end

// ADD 2013/7/2 shima【QP@30151】No.37 start
    //サンプル毎の基本情報表示
    funKihonSubDisplay(xmlResAry[2],"divKihonSub");

    var recCnt;
    var i;
    //列数取得
    recCnt = frm.cnt_sample.value;
    //
    for(i = 0; i < recCnt;i++){
    	// MOD 2013/12/4 okano【QP@30154】start
//	    	setKanma(document.getElementById("txtGenkaKibo" + i).value);
//	    	setKanma(document.getElementById("txtBaikaKibo" + i).value);
    	setKanma(document.getElementById("txtGenkaKibo" + i));
    	setKanma(document.getElementById("txtBaikaKibo" + i));
    	// MOD 2013/12/4 okano【QP@30154】end
    	// ADD 2013/12/4 okano【QP@30154】start
    	setKanma(document.getElementById("txtSoteiButuryo_s" + i));
    	// ADD 2013/12/4 okano【QP@30154】end
    }
// ADD 2013/7/2 shima【QP@30151】No.37 end

    //----------------------------------- 【QP@00342】原価試算メモ-------------------------------
    frm.txtGenkaMemo.value = funXmlRead_3(xmlResAry[2], "kihon", "memo_genkashisan", 0, 0);


    //----------------------------- 【QP@00342】原価試算メモ（営業連絡用） --------------------------
    frm.txtGenkaMemoEigyo.value = funXmlRead_3(xmlResAry[2], "kihon", "memo_genkashisan_eigyo", 0, 0);


    //------------------------------------ コード桁数 -------------------------------------
    frm.hdnCdketasu.value = funXmlRead_3(xmlResAry[2], "kihon", "cd_ketasu", 0, 0);

    //【QP@00342】画面制御
    kengenSetting_kihon();

//20160617  KPX@1502111_No.5 ADD start
    //原料情報表示前、自家原料チェックを実行
    // 再計算ボタン：処理可能（工場：＜確認完了、生管：＜確認完了）
    // 連携先の試作ステータス≧営業３の時、自家原料単価（原価計(円)/Kg）を設定する
    // 自家原料歩留：ステータスに関係なく値が設定されていない時、insに100を入れる
    var headerFrm = parent.header.document.frm00;
    if(headerFrm.btnSaikeisan.disabled == false) {
    	//自家原料の単価情報を更新
    	funRenkeiGenryo();
    }
//20160617  KPX@1502111_No.5 ADD end


    //------------------------------------------------------------------------------------
    //                            原料情報表示イベント発生
    //------------------------------------------------------------------------------------
    //原料情報表示イベントを発生させる
    frm.BtnEveGenryo.fireEvent('onclick');


    //処理終了
    return true;

}


//========================================================================================
// 【QP@00342】画面制御（共通＆基本情報部）
// 作成者：Y.Nishigawa
// 作成日：2009/10/28
//========================================================================================
function kengenSetting_kihon(){

    var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

	// ADD 2013/7/2 shima【QP@30151】No.37 start
	var i;
	var recCnt;
	// ADD 2013/7/2 shima【QP@30151】No.37 end

    //部署取得
    var kenkyu = headerFrm.hdnBusho_kenkyu.value;
    var seikan = headerFrm.hdnBusho_seikan.value;
    var gentyo = headerFrm.hdnBusho_gentyo.value;
    var kojo = headerFrm.hdnBusho_kojo.value;
    var eigyo = headerFrm.hdnBusho_eigyo.value;

	// ADD 2013/7/2 shima【QP@30151】No.37 start
	//列数取得
    recCnt = detailFrm.cnt_sample.value;
    // ADD 2013/7/2 shima【QP@30151】No.37 end

    //排他の場合
    if(headerFrm.strKengenMoto.value == "999"){

    	//終了、ステータス履歴、使用中表示、ヘルプ表示　以外は使用不可
    	headerFrm.btnSaikeisan.disabled = true;
    	headerFrm.btnToroku.disabled = true;
    	//headerFrm.btnInsatu.disabled = true;

    	detailFrm.ddlSeizoKaisya.disabled=true;
    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

    	detailFrm.ddlSeizoKojo.disabled=true;
    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

    	detailFrm.btnArigae.disabled = true;

		// MOD 2013/7/2 shima【QP@30151】No.37 start
    	for(i = 0;i < recCnt;i++){
//    		detailFrm.txtSeizoRot.disabled=true;
//    		detailFrm.txtSeizoRot.style.backgroundColor=color_read;
			// MOD 2014/8/7 shima【QP@30154】No.80 start
//    		detailDoc.getElementById("txtSeizoRot" + i).disabled = true;
    		detailDoc.getElementById("txtSeizoRot" + i).style.backgroundColor=color_read;
    		detailDoc.getElementById("txtSeizoRot" + i).readOnly = true;
    		// MOD 2014/8/7 shima【QP@30154】No.80 end
    	}
    	// MOD 2013/7/2 shima【QP@30151】No.37 end

    	detailFrm.txtGenkaMemo.disabled=true;
    	detailFrm.txtGenkaMemo.style.backgroundColor=color_read;

    	detailFrm.txtGenkaMemoEigyo.disabled=true;
    	detailFrm.txtGenkaMemoEigyo.style.backgroundColor=color_read;

    }
    //排他でない場合
    else{
    	//編集権限
	    if(headerFrm.hdnKengen.value == ConFuncIdEdit.toString()){

	        //生産管理部
	        if(seikan == "1"){
	        	headerDoc.getElementById("btnTyusi").style.visibility = "visible";
		        headerDoc.getElementById("btnEdaban").style.visibility = "visible";

		        headerFrm.btnTyusi.disabled=false;
	        	headerFrm.btnEdaban.disabled=false;

	        }

	        //原資材調達部
	        else if(gentyo == "1"){
	        	detailFrm.btnArigae.disabled=true;

		        detailFrm.ddlSeizoKaisya.disabled=true;
		    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

		    	detailFrm.ddlSeizoKojo.disabled=true;
		    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

				// MOD 2013/7/2 shima【QP@30151】No.37 start
		    	for(i = 0;i < recCnt;i++){
//		    		detailFrm.txtSeizoRot.disabled=true;
//		    		detailFrm.txtSeizoRot.style.backgroundColor=color_read;
					// MOD 2014/8/7 shima【QP@30154】No.80 start
//		    		detailDoc.getElementById("txtSeizoRot"+i).disabled=true;
		    		detailDoc.getElementById("txtSeizoRot"+i).style.backgroundColor=color_read;
	        		detailDoc.getElementById("txtSeizoRot"+i).readOnly=true;
	        		// MOD 2014/8/7 shima【QP@30154】No.80 end
		    	}
		    	// MOD 2013/7/2 shima【QP@30151】No.37 end

	        }

	        //工場
	        else if(kojo == "1"){
	        	detailFrm.btnArigae.disabled=true;

	        	detailFrm.ddlSeizoKaisya.disabled=true;
		    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

		    	detailFrm.ddlSeizoKojo.disabled=true;
		    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

		    	//工場の確認完了の場合
		    	/*
				 * if(headerFrm.hdnStatus_kojo.value == "2"){
				 * detailFrm.txtSeizoRot.disabled=true;
				 * detailFrm.txtSeizoRot.style.backgroundColor=color_read; }
				 */
	        }

	        //ステータス制御
	        var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
		    var st_seikan = headerFrm.hdnStatus_seikan.value;
		    var st_gentyo = headerFrm.hdnStatus_gentyo.value;
		    var st_kojo    = headerFrm.hdnStatus_kojo.value;
		    var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

		    headerFrm.btnSaikeisan.disabled = false;

		    //生産管理部ステータス >= 2　の場合
	        if( st_seikan >= 2 ){
				detailFrm.ddlSeizoKaisya.disabled=true;
		    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

		    	detailFrm.ddlSeizoKojo.disabled=true;
		    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

		    	detailFrm.btnArigae.disabled = true;
	        }

	        //20160607  【KPX@1502111_No8】 ADD start
	        //工場ステータス ≧ 2 　の場合（部署：工場）
	        //　部署：生管の場合は、生産管理部ステータス ≧ 3
	        if( kojo == "1" && st_kojo >= 2 ){
	        	for(i = 0;i < recCnt; i++){
	        		detailDoc.getElementById("txtSeizoRot" + i).style.backgroundColor = color_read;
	        		detailDoc.getElementById("txtSeizoRot" + i).readOnly = true;
	        	}
		    	headerFrm.btnSaikeisan.disabled = true;
	        }
	        //20160607  【KPX@1502111_No8】 ADD end

	        //生産管理部ステータス >= 3　の場合
	        if( st_seikan >= 3 ){
	        	// MOD 2013/7/2 shima【QP@30151】No.37 start
	        	for(i = 0;i < recCnt; i++){
	        		//detailFrm.txtSeizoRot.disabled=true;
	        		//detailFrm.txtSeizoRot.style.backgroundColor=color_read;
	        		// MOD 2014/8/7 shima【QP@30154】No.80 start
//	        		detailDoc.getElementById("txtSeizoRot" + i).disabled = true;
	        		detailDoc.getElementById("txtSeizoRot" + i).style.backgroundColor = color_read;
	        		detailDoc.getElementById("txtSeizoRot" + i).readOnly = true;
	        		// MOD 2014/8/7 shima【QP@30154】No.80 end
	        	}
	        	// MOD 2013/7/2 shima【QP@30151】No.37 end
		    	headerFrm.btnSaikeisan.disabled = true;
	        }

	        //営業ステータス >= 4　の場合
	        if( st_eigyo >= 4 ){
	        	headerFrm.btnTyusi.disabled=true;

	        	headerFrm.btnEdaban.disabled=true;

	        	detailFrm.ddlSeizoKaisya.disabled=true;
		    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

		    	detailFrm.ddlSeizoKojo.disabled=true;
		    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

	        	// MOD 2013/7/2 shima【QP@30151】No.37 start
	        	for(i = 0;i < recCnt; i++){
	        		//detailFrm.txtSeizoRot.disabled=true;
	        		//detailFrm.txtSeizoRot.style.backgroundColor=color_read;
	        		// MOD 2014/8/7 shima【QP@30154】No.80 start
//	        		detailDoc.getElementById("txtSeizoRot" + i).disabled = true;
	        		detailDoc.getElementById("txtSeizoRot" + i).style.backgroundColor = color_read;
	        		detailDoc.getElementById("txtSeizoRot" + i).readOnly = true;
	        		// MOD 2014/8/7 shima【QP@30154】No.80 end
	        	}
	        	// MOD 2013/7/2 shima【QP@30151】No.37 end

		    	headerFrm.btnSaikeisan.disabled = true;
	        }
	    }
	    //閲覧+Excel権限
	    else if(headerFrm.hdnKengen.value == ConFuncIdReadExcel.toString()){

	    	headerFrm.btnSaikeisan.disabled = true;

	    	detailFrm.ddlSeizoKaisya.disabled=true;
	    	detailFrm.ddlSeizoKaisya.style.backgroundColor=color_read;

	    	detailFrm.ddlSeizoKojo.disabled=true;
	    	detailFrm.ddlSeizoKojo.style.backgroundColor=color_read;

	    	detailFrm.btnArigae.disabled = true;

			// MOD 2013/7/2 shima【QP@30151】No.37 start
	        for(i = 0;i < recCnt; i++){
	        	//detailFrm.txtSeizoRot.disabled=true;
	        	//detailFrm.txtSeizoRot.style.backgroundColor=color_read;
	        	// MOD 2014/8/7 shima【QP@30154】No.80 start
//	        	detailDoc.getElementById("txtSeizoRot" + i).disabled = true;
	        	detailDoc.getElementById("txtSeizoRot" + i).style.backgroundColor = color_read;
        		detailDoc.getElementById("txtSeizoRot" + i).readOnly = true;
        		// MOD 2014/8/7 shima【QP@30154】No.80 end
	        }
	        // MOD 2013/7/2 shima【QP@30151】No.37 end

	    }
    }


 // 20160513  KPX@1600766 ADD start
    if (kenkyu == "1") {
    	// 研究所：試作の製造会社に対する単価開示フラグを取得
    	tankaHyoujiFlg = funGetTankaFlg();

    	if (tankaHyoujiFlg == "1" || tankaHyoujiFlg == "0")  {
    		// 再計算ボタン非活性
    		headerFrm.btnSaikeisan.disabled = true;
    	}
    } else {
    	// 研究所以外は全表示（画面表示制御をしている為）
    	tankaHyoujiFlg = "9";
    }
// 20160513  KPX@1600766 ADD end
    return true;
}


//========================================================================================
// 原価試算、原料情報取得＆表示処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：原価試算、原料情報取得
//========================================================================================
function funGetGenryoInfo(mode) {

    var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

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
    //                                  原料情報表示
    //------------------------------------------------------------------------------------
    //原料テーブル左側
    funGenryo_LeftDisplay(xmlResAry[2], "divGenryo_Left");

    //原料テーブル右側
    funGenryo_RightDisplay(xmlResAry[2], "divGenryo_Right");

    //【シサクイックH24年度対応】No46 2012/04/20 ADD Start
    //原料テーブル項目非表示
    funGenryo_DispDecision(xmlResAry[2]);
    //【シサクイックH24年度対応】No46 2012/04/20 ADD End

    //計算項目表示
    keisanKomoku = funXmlRead_3(xmlResAry[2], "kihon", "ragio_kesu_kg", 0, 0);

    //1:固定費/ｹｰｽ
    frm.radioKoteihi[0].checked = "true";
    if(keisanKomoku.toString() == "1"){
        frm.radioKoteihi[0].checked = "true";
    }
    //2:固定費/kg
    else if(keisanKomoku.toString() == "2"){
        frm.radioKoteihi[1].checked = "true";
        //【QP@10713】 11/10/31 TT H.Shima
        radio_state = false;
    }

    //権限編集
    kengenRadioSetting(frm.radioKoteihi[0]);
    kengenRadioSetting(frm.radioKoteihi[1]);

    //【QP@00342】原資材調達部の場合（固定費ﾗｼﾞｵﾎﾞﾀﾝは選択不可）
    if(headerFrm.hdnBusho_gentyo.value == "1"){
    	frm.radioKoteihi[0].disabled = true;
    	frm.radioKoteihi[1].disabled = true;
    }

    //製造工程表示
    //frm.txtSeizoKotei.value = funXmlRead_3(xmlResAry[2], "kihon", "koute", 0, 0);
//20160622  KPX@1502111_No.10 ADD start
    //サンプルコピーボタンの権限編集（原調はfunGenryoShisanDisplayで使用不可に変更）
    kengenBottunSetting(frm.btnSampleCopy);
//20160622  KPX@1502111_No.10 ADD end

    //試算情報表示
    funGenryoShisanDisplay(xmlResAry[2], "divGenryoShisan");

    //マスタ単価・歩留ボタンの権限編集
    kengenBottunSetting(frm.btnBckMst);

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.38
    //サンプルNoコンボボックス生成
    funCreateComboBox(frm.ddlSeizoNo , xmlResAry[2] , 6, 0);
    //製造工程明細の初期表示
    seizo_output();
//mod end   -------------------------------------------------------------------------------

    // 20170515 KPX@1700856 ADD Start
    funGetTankaZeroGenryo();
    // 20170515 KPX@1700856 ADD End
    
    //------------------------------------------------------------------------------------
    //                            資材情報表示イベント発生
    //------------------------------------------------------------------------------------
    //資材情報表示イベントを発生させる
    frm.BtnEveShizai.fireEvent('onclick');

    //処理終了
    return true;

}


//========================================================================================
// 原価試算、資材情報取得＆表示処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：正常終了:true／異常終了:false
// 概要  ：原価試算、資材情報取得
//========================================================================================
function funGetShizaiInfo(mode) {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN0013";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0013");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0013I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0013O );

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
    //                                  原料情報表示
    //------------------------------------------------------------------------------------
    //資材テーブル表示
    funShizaiDisplay(xmlResAry[2],"divGenryoShizai");
    funSetDtShisan();

    //一括選択 権限編集
    kengenCheckboxSetting(frm.chkIkkatuShizai);

    //類似品検索 権限編集
    kengenBottunSetting(frm.btnRuiziSearch);

    //資材削除 権限編集
    kengenBottunSetting(frm.btnShizaiDelete);


	// 【初期表示不具合対応】20211111 BRC.yanagita ADD start

    // 画面初期表示が初回(画面初期表示フラグが「０」)のとき以下処理を実行
	if (shokiHyoujiFlg == "0"){
	
	// 【初期表示不具合対応】20211111 BRC.yanagita ADD end
	
// MOD start 20150722
// ADD start 20140919
//    funLoad_datahosei();
    // 営業フラグ≧3
    // 固定データがないデータがあった場合、項目固定フラグのチェックを外す
    funLoad_datahosei(0);


// ADD end 20140919
// MOD end 20150722

	// 【初期表示不具合対応】20211111 BRC.yanagita ADD start
	}
	else if (shokiHyoujiFlg == "1"){
	// 【初期表示不具合対応】20211111 BRC.yanagita ADD end
// MOD start 20150722
// ADD start 20140919	
	    // 固定データがないデータがあった場合、レコードを追加する
		 funLoad_datahosei(1); 
// ADD end 20140919
// MOD end 20150722
	// 【初期表示不具合対応】20211111 BRC.yanagita ADD start
	}
	else {
		shokiHyoujiFlg = "0"; //画面初期表示フラグを初期値(画面初期表示フラグ＝「０」)に戻す
	}
	// 【初期表示不具合対応】20211111 BRC.yanagita ADD end

    //処理終了
    return true;

}

//========================================================================================
// 権限関連処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
// 概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo() {

    //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;
    var obj = xmlUSERINFO_O;
    var GamenId;
    var KinoId;
    // ADD 2013/11/5 QP@30154 okano start
    var DataId;
    // ADD 2013/11/5 QP@30154 okano end
    var reccnt;
    var i;

    //権限ループ
    reccnt = funGetLength(obj);
    for (i = 0; i < reccnt; i++) {
        GamenId = funXmlRead(obj, "id_gamen", i);
        KinoId = funXmlRead(obj, "id_kino", i);
        // ADD 2013/11/5 QP@30154 okano start
        DataId = funXmlRead(obj, "id_data", i);
        // ADD 2013/11/5 QP@30154 okano end

        //原価試算画面
        if (GamenId.toString() == ConGmnIdGenkaShisan.toString()) {

            //権限設定
            headerFrm.hdnKengen.value = KinoId.toString();
            // ADD 2013/11/5 QP@30154 okano start
            headerFrm.hdnDataId.value = DataId.toString();
            // ADD 2013/11/5 QP@30154 okano end

        }
    }

    return true;

}

//========================================================================================
// XMLファイルに書き込み
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
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

                    //【QP@00342】
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

     // 20160513  KPX@1600766 ADD start
        // グループ会社の単価開示フラグ取得
        else if (XmlId.toString() == "JSP0630") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //SA100
                    funXmlWrite(reqAry[i], "cd_category", "K_tanka_hyoujigaisha", 0);
                    funXmlWrite(reqAry[i], "cd_literal", detailFrm.hdnKaisha.value, 0);
                    // データなしでもエラーとしない
                    funXmlWrite(reqAry[i], "req_flg", "1", 0);
                    break;
            }
        }
     // 20160513  KPX@1600766 ADD end

        // 20170515 KPX@1700856 ADD Start
        // 単価0円許可原料コード取得
        else if (XmlId.toString() == "RGEN2290") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2290
                    funXmlWrite(reqAry[i], "cd_category", "K_tanka_zero_genryo", 0);
                    // データなしでもエラーとしない
                    funXmlWrite(reqAry[i], "req_flg", "1", 0);
                    break;
            }
        }
        // 20170515 KPX@1700856 ADD end
        
        //ADD 2015/07/31 TT.Kitazawa【QP@40812】No.6 end
        //メール送信時再、取得（スータス情報）
        else if (XmlId.toString() == "RGEN2020") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2020
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
      //ADD 2015/07/31 TT.Kitazawa【QP@40812】No.6 end

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

                    //【QP@00342】
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
                    //【QP@00342】
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }

        //画面初期表示（原料情報）
        else if (XmlId.toString() == "RGEN0013"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0013
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    //【QP@00342】
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
        //【QP@00342】原価試算画面起動情報通知
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

        //登録
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
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // 採用サンプルＮＯ
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "no_sanpuru", headerFrm.ddlSaiyoSample.options[headerFrm.ddlSaiyoSample.selectedIndex].value, 0);

                    // 工場　担当会社
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    // 工場　担当工場
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kojyo", detailFrm.ddlSeizoKojo.options[detailFrm.ddlSeizoKojo.selectedIndex].value, 0);

                    // 入り数
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "irisu", detailFrm.txtIrisu.value, 0);
                    // 荷姿
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "nisugata", detailFrm.txtNisugata.value, 0);
                    // 原価希望
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka", detailFrm.txtGenkaKibo.value, 0);
                    // 原価希望単位CD
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_cd_tani", detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].value, 0);
                    // 売価希望
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_baika", detailFrm.txtBaikaKibo.value, 0);
                    // 想定物量
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei", detailFrm.txtSoteiButuryo.value, 0);
                    // 販売時期
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "ziki_hanbai", detailFrm.txtHanbaiJiki.value, 0);
                    // 計画売上
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_uriage", detailFrm.txtKeikakuUriage.value, 0);
                    // 計画利益
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_rieki", detailFrm.txtKeikakuRieki.value, 0);
                    // 販売後売上
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_uriage", detailFrm.txtHanbaigoUriage.value, 0);
                    // 販売後利益
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_rieki", detailFrm.txtHanbaigoRieki.value, 0);

                    // 製造ロット
                    // DEL 2013/7/2 shima【QP@30151】No.37 start
//                    funXmlWrite_Tbl(reqAry[i], "kihon", "seizo_roto", detailFrm.txtSeizoRot.value, 0);
                    // DEL 2013/7/2 shima【QP@30151】No.37 end

                    // 原価試算メモ
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan", detailFrm.txtGenkaMemo.value, 0);

                    //【QP@00342】原価試算メモ（営業連絡用）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan_eigyo", detailFrm.txtGenkaMemoEigyo.value, 0);

                    //【QP@00342】選択ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "setting", headerFrm.hdnInsStatus.value, 0);

                    //【QP@00342】研究所ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kenkyu", headerFrm.hdnStatus_kenkyu.value, 0);

                    //【QP@00342】生産管理部ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_seikan", headerFrm.hdnStatus_seikan.value, 0);

                    //【QP@00342】原資材調達部ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_gentyo", headerFrm.hdnStatus_gentyo.value, 0);

                    //【QP@00342】工場ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kojo", headerFrm.hdnStatus_kojo.value, 0);

                    //【QP@00342】営業ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_eigyo", headerFrm.hdnStatus_eigyo.value, 0);

                    //【QP@00342】部署設定（研究所）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_kenkyu", headerFrm.hdnBusho_kenkyu.value, 0);

                    //【QP@00342】部署設定（生産管理部）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_seikan", headerFrm.hdnBusho_seikan.value, 0);

                    //【QP@00342】部署設定（原資材調達部）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_gentyo", headerFrm.hdnBusho_gentyo.value, 0);

                    //【QP@00342】部署設定（工場）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_kojo", headerFrm.hdnBusho_kojo.value, 0);

                    //【QP@00342】部署設定（営業）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_eigyo", headerFrm.hdnBusho_eigyo.value, 0);


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
//ADD 2013/07/11 ogawa 【QP@30151】No.13 start
                        //項目固定チェック
                        if (detailDoc.getElementById("chkKoumoku_"+j).checked) {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "1", j);
                        } else {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "0", j);
                        }
//ADD 2013/07/11 ogawa 【QP@30151】No.13 end
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
                        // ADD 2013/11/1 QP@30154 okano start
                        // 利益/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_rieki", detailDoc.getElementById("txtCaseRieki_"+j).value, j);
                        // 利益/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_rieki", detailDoc.getElementById("txtKgRieki_"+j).value, j);
                        // ADD 2013/11/1 QP@30154 okano end
                        // 原価計/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genkake", detailDoc.getElementById("txtKgGenkake_"+j).value, j);

                        // 【QP@00342】試算中止

                        funXmlWrite_Tbl(reqAry[i], "keisan", "fg_chusi", detailDoc.getElementById("hdnSisanChusi_"+j).value, j);

                        // ADD 2014/1/10 QP@30154 追加課題No.11 nishigawa start
                        // 充填量水相（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyusui", detailDoc.getElementById("txtSuiZyuten_"+j).value, j);
                        // 充填量油相（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyuabura", detailDoc.getElementById("txtYuZyuten_"+j).value, j);
                        // 合計量（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "gokei", detailDoc.getElementById("total"+j).value, j);
                        // 比重
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hiju", detailDoc.getElementById("hijyu"+j).value, j);
                        // レベル量（㎏）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "reberu", detailDoc.getElementById("txtLebelRyo_"+j).value, j);
                        // 比重加算量（㎏）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hijukasan", detailDoc.getElementById("hijyuKasan"+j).value, j);
                        // 原料費（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genryo", detailDoc.getElementById("genryohi"+j).value, j);
                        // 材料費（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_zairyohi", detailDoc.getElementById("zairyohi"+j).value, j);
                        // 原価計（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genka", detailDoc.getElementById("genkakei"+j).value, j);
                        // 原価計（円）/個
                        funXmlWrite_Tbl(reqAry[i], "keisan", "ko_genka", detailDoc.getElementById("genkakeiKo"+j).value, j);
                        // 原料費（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genryo", detailDoc.getElementById("genryohiKG"+j).value, j);
                        // 材料費（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_zairyohi", detailDoc.getElementById("zairyohiKG"+j).value, j);
                        // 原価計（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genka", detailDoc.getElementById("txtKgGenkake_"+j).value, j);
                        // 売価
                        funXmlWrite_Tbl(reqAry[i], "keisan", "baika", detailDoc.getElementById("baika"+j).value, j);
                        // 粗利（％）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "arari", detailDoc.getElementById("sori"+j).value, j);
                        // ADD 2014/1/10 QP@30154 追加課題No.11 nishigawa end
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

                    // ADD 2013/7/2 shima【QP@30151】No.37 start
                    //-------------------------- [kihonsub]テーブル格納 -------------------------
                    //計算表の列数取得
                    recCnt = detailFrm.cnt_sample.value;
                    //XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){

                    	if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0040", "kihonsub");
                        }

                        // 試作SEQ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);

                        // 製造ロット
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seizo_roto", detailDoc.getElementById("txtSeizoRot"+j).value, j);

                    }
                    // ADD 2013/7/2 shima【QP@30151】No.37 end

                    break;
            }
        }

        // ADD start 20140919
        // 項目固定チェック対象項目値 登録
        else if (XmlId.toString() == "RGEN2021"){
            switch (i) {
                case 0:    // USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    // FGEN2021

                    // ループカウント
                    var recCnt;
                    var j;

                    // -------------------------- [kihon]テーブル格納
					// --------------------------
                    // 試作CD-社員CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", headerFrm.txtShainCd.value, 0);
                    // 試作CD-年
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", headerFrm.txtNen.value, 0);
                    // 試作CD-追番
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", headerFrm.txtOiNo.value, 0);
                    // 【QP@00342】枝番
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // -------------------------- [keisan]テーブル格納
					// -------------------------
                    // 計算表の列数取得
                    recCnt = detailFrm.cnt_sample.value;

                    // XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN2021", "keisan");
                        }
                        // 試作SEQ
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);
                        // ------------------------
                        // 充填量水相（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyusui", detailDoc.getElementById("txtSuiZyuten_"+j).value, j);
                        // 充填量油相（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyuabura", detailDoc.getElementById("txtYuZyuten_"+j).value, j);
                        // 合計量（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "gokei", detailDoc.getElementById("total"+j).value, j);
                        // 比重
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hiju", detailDoc.getElementById("hijyu"+j).value, j);
                        // レベル量（㎏）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "reberu", detailDoc.getElementById("txtLebelRyo_"+j).value, j);
                        // 比重加算量（㎏）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hijukasan", detailDoc.getElementById("hijyuKasan"+j).value, j);
                        // 原料費（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genryo", detailDoc.getElementById("genryohi"+j).value, j);
                        // 材料費（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_zairyohi", detailDoc.getElementById("zairyohi"+j).value, j);
                        // 原価計（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genka", detailDoc.getElementById("genkakei"+j).value, j);
                        // 原価計（円）/個
                        funXmlWrite_Tbl(reqAry[i], "keisan", "ko_genka", detailDoc.getElementById("genkakeiKo"+j).value, j);
                        // 原料費（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genryo", detailDoc.getElementById("genryohiKG"+j).value, j);
                        // 材料費（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_zairyohi", detailDoc.getElementById("zairyohiKG"+j).value, j);
                        // 原価計（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genka", detailDoc.getElementById("txtKgGenkake_"+j).value, j);
                        // 売価
                        funXmlWrite_Tbl(reqAry[i], "keisan", "baika", detailDoc.getElementById("baika"+j).value, j);
                        // 粗利（％）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "arari", detailDoc.getElementById("sori"+j).value, j);
                    }
                    break;
            }
        }
        // ADD end 20140919

        // ADD start 20150722
        // 項目固定チェックON 固定ファイルなしの時、項目固定チェックをOFFにする
        // 再計算で値がセットされる
        else if (XmlId.toString() == "RGEN2022"){
            switch (i) {
                case 0:    // USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    // FGEN2022

                    // ループカウント
                    var recCnt;
                    var j;

                    // -------------------------- [kihon]テーブル格納
					// --------------------------
                    // 試作CD-社員CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_shain", headerFrm.txtShainCd.value, 0);
                    // 試作CD-年
                    funXmlWrite_Tbl(reqAry[i], "kihon", "nen", headerFrm.txtNen.value, 0);
                    // 試作CD-追番
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_oi", headerFrm.txtOiNo.value, 0);
                    // 【QP@00342】枝番
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // -------------------------- [keisan]テーブル格納
					// -------------------------
                    // 計算表の列数取得
                    recCnt = detailFrm.cnt_sample.value;

                    // XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN2022", "keisan");
                        }
                        // 試作SEQ
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);
                        // 項目固定チェック
                        funXmlWrite_Tbl(reqAry[i], "keisan", "fg_koumokuchk", detailDoc.getElementById("chkKoumoku_"+j).value, j);
                   }
                    break;
            }
        }
        // ADD end 20150722

        //工場検索（会社変更時）
        else if (XmlId.toString() == "RGEN1020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //RGEN1020
                    funXmlWrite(reqAry[i], "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    funXmlWrite(reqAry[i], "cd_kojyo", "", 0);
                    funXmlWrite(reqAry[i], "id_gamen", GenkaSisanId, 0);
                    break;
            }
        }

        //原料洗い替え
        else if (XmlId.toString() == "RGEN0020"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0060

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
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // 採用サンプルＮＯ
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "no_sanpuru", headerFrm.ddlSaiyoSample.options[headerFrm.ddlSaiyoSample.selectedIndex].value, 0);
                    //新会社CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "new_cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    //新工場CD
                    funXmlWrite_Tbl(reqAry[i], "kihon", "new_cd_kojyo", detailFrm.ddlSeizoKojo.options[detailFrm.ddlSeizoKojo.selectedIndex].value, 0);
                    // 工場　担当会社
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kaisya", detailFrm.hdnKaisha.value, 0);
                    // 工場　担当工場
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kojyo", detailFrm.hdnKojo.value, 0);
                    // 入り数
                    funXmlWrite_Tbl(reqAry[i], "kihon", "irisu", detailFrm.txtIrisu.value, 0);
                    // 荷姿
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "nisugata", detailFrm.txtNisugata.value, 0);

                    // DEL 2013/7/2 shima【QP@30151】No.37 start
                    // 原価希望
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka", detailFrm.txtGenkaKibo.value, 0);
                    // DEL 2013/7/2 shima【QP@30151】No.37 end

                    // 原価希望単位CD
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_cd_tani", detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].value, 0);

                    // DEL 2013/7/2 shima【QP@30151】No.37 start
                    // 売価希望
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_baika", detailFrm.txtBaikaKibo.value, 0);
                    // 想定物量
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei", detailFrm.txtSoteiButuryo.value, 0);
                    // DEL 2013/7/2 shima【QP@30151】No.37 end

                    // 販売時期
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "ziki_hanbai", detailFrm.txtHanbaiJiki.value, 0);

                    // DEL 2013/7/2 shima【QP@30151】No.37 start
                    // 計画売上
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_uriage", detailFrm.txtKeikakuUriage.value, 0);
                    // 計画利益
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_rieki", detailFrm.txtKeikakuRieki.value, 0);
                    // 販売後売上
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_uriage", detailFrm.txtHanbaigoUriage.value, 0);
                    // 販売後利益
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_rieki", detailFrm.txtHanbaigoRieki.value, 0);
                    // 製造ロット
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "seizo_roto", detailFrm.txtSeizoRot.value, 0);
                    // DEL 2013/7/2 shima【QP@30151】No.37 end

                    // 原価試算メモ
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan", detailFrm.txtGenkaMemo.value, 0);

                    // 【QP@00342】原価試算メモ（営業連絡用）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan_eigyo", detailFrm.txtGenkaMemoEigyo.value, 0);

                //MOD 2015/03/03 TT.Kitazawa【QP@40812】No.12 start
                    //【QP@00342】選択ステータス設定（0固定：仮保存）
//                    funXmlWrite_Tbl(reqAry[i], "kihon", "setting", "0", 0);
                    //【QP@40812】作業コード設定（9固定：仮保存（工場変更））
                    funXmlWrite_Tbl(reqAry[i], "kihon", "setting", "9", 0);
                //MOD 2015/03/03 TT.Kitazawa【QP@40812】No.12 end

                    //【QP@00342】研究所ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kenkyu", headerFrm.hdnStatus_kenkyu.value, 0);

                    //【QP@00342】生産管理部ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_seikan", headerFrm.hdnStatus_seikan.value, 0);

                    //【QP@00342】原資材調達部ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_gentyo", headerFrm.hdnStatus_gentyo.value, 0);

                    //【QP@00342】工場ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_kojo", headerFrm.hdnStatus_kojo.value, 0);

                    //【QP@00342】営業ステータス設定
                    funXmlWrite_Tbl(reqAry[i], "kihon", "st_eigyo", headerFrm.hdnStatus_eigyo.value, 0);

                    //【QP@00342】部署設定（研究所）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_kenkyu", headerFrm.hdnBusho_kenkyu.value, 0);

                    //【QP@00342】部署設定（生産管理部）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_seikan", headerFrm.hdnBusho_seikan.value, 0);

                    //【QP@00342】部署設定（原資材調達部）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_gentyo", headerFrm.hdnBusho_gentyo.value, 0);

                    //【QP@00342】部署設定（工場）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_kojo", headerFrm.hdnBusho_kojo.value, 0);

                    //【QP@00342】部署設定（営業）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "busho_eigyo", headerFrm.hdnBusho_eigyo.value, 0);

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
                                funAddRecNode_Tbl(reqAry[i], "FGEN0060", "genryo");
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
                            funAddRecNode_Tbl(reqAry[i], "FGEN0060", "keisan");
                        }
//ADD 2013/07/11 ogawa 【QP@30151】No.13 start
                        // //項目固定チェック
                        if (detailDoc.getElementById("chkKoumoku_"+j).checked) {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "1", j);
                        } else {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "0", j);
                        }
//ADD 2013/07/11 ogawa 【QP@30151】No.13 end
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
                        // ADD 2013/11/1 QP@30154 okano start
                        // 利益/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_rieki", detailDoc.getElementById("txtCaseRieki_"+j).value, j);
                        // 利益/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_rieki", detailDoc.getElementById("txtKgRieki_"+j).value, j);
                        // ADD 2013/11/1 QP@30154 okano end
                        // 原価計/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genkake", detailDoc.getElementById("txtKgGenkake_"+j).value, j);

                        // 【QP@00342】試算中止

                        funXmlWrite_Tbl(reqAry[i], "keisan", "fg_chusi", detailDoc.getElementById("hdnSisanChusi_"+j).value, j);

                        // ADD 2014/1/10 QP@30154 追加課題No.11 nishigawa start
                        // 充填量水相（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyusui", detailDoc.getElementById("txtSuiZyuten_"+j).value, j);
                        // 充填量油相（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyuabura", detailDoc.getElementById("txtYuZyuten_"+j).value, j);
                        // 合計量（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "gokei", detailDoc.getElementById("total"+j).value, j);
                        // 比重
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hiju", detailDoc.getElementById("hijyu"+j).value, j);
                        // レベル量（㎏）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "reberu", detailDoc.getElementById("txtLebelRyo_"+j).value, j);
                        // 比重加算量（㎏）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hijukasan", detailDoc.getElementById("hijyuKasan"+j).value, j);
                        // 原料費（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genryo", detailDoc.getElementById("genryohi"+j).value, j);
                        // 材料費（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_zairyohi", detailDoc.getElementById("zairyohi"+j).value, j);
                        // 原価計（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genka", detailDoc.getElementById("genkakei"+j).value, j);
                        // 原価計（円）/個
                        funXmlWrite_Tbl(reqAry[i], "keisan", "ko_genka", detailDoc.getElementById("genkakeiKo"+j).value, j);
                        // 原料費（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genryo", detailDoc.getElementById("genryohiKG"+j).value, j);
                        // 材料費（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_zairyohi", detailDoc.getElementById("zairyohiKG"+j).value, j);
                        // 原価計（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genka", detailDoc.getElementById("txtKgGenkake_"+j).value, j);
                        // 売価
                        funXmlWrite_Tbl(reqAry[i], "keisan", "baika", detailDoc.getElementById("baika"+j).value, j);
                        // 粗利（％）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "arari", detailDoc.getElementById("sori"+j).value, j);
                        // ADD 2014/1/10 QP@30154 追加課題No.11 nishigawa end
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
                                funAddRecNode_Tbl(reqAry[i], "FGEN0060", "shizai");
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

                    // ADD 2013/7/2 shima【QP@30151】No.37 start
                    //-------------------------- [kihonsub]テーブル格納 -------------------------
                    //行数取得
                    recCnt = detailFrm.cnt_sample.value;
                    //XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){
                    	//XMLへ追加
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0060", "kihonsub");
                        }
                        // 試作SEQ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);

	                    // 原価希望
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "kibo_genka", detailDoc.getElementById("txtGenkaKibo"+j).value, j);
	                    // 売価希望
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "kibo_baika", detailDoc.getElementById("txtBaikaKibo"+j).value, j);
	                    // 想定物量
	                    // ADD 2013/9/6 okano【QP@30151】No.30 start
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "butu_sotei_s", detailDoc.getElementById("txtSoteiButuryo_s"+j).value, j);
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "butu_sotei_u", detailDoc.getElementById("txtSoteiButuryo_u"+j).value, j);
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "butu_sotei_k", detailDoc.getElementById("txtSoteiButuryo_k"+j).value, j);
	                    // ADD 2013/9/6 okano【QP@30151】No.30 end
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "butu_sotei", detailDoc.getElementById("txtSoteiButuryo"+j).value, j);
	                    // 計画売上
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "keikaku_uriage", detailDoc.getElementById("txtKeikakuUriage"+j).value, j);
	                    // 計画利益
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "keikaku_rieki", detailDoc.getElementById("txtKeikakuRieki"+j).value, j);
	                    // 販売後売上
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "hanbaigo_uriage", detailDoc.getElementById("txtHanbaigoUriage"+j).value, j);
	                    // 販売後利益
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "hanbaigo_rieki", detailDoc.getElementById("txtHanbaigoRieki"+j).value, j);
	                    // 製造ロット
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "seizo_roto", detailDoc.getElementById("txtSeizoRot"+j).value, j);

                    }
                    // ADD 2013/7/2 shima【QP@30151】No.37 end

                    break;
            }
        }


        //再計算
        else if (XmlId.toString() == "RGEN0030"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0030

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

                    //【QP@00342】
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // 入り数
                    funXmlWrite_Tbl(reqAry[i], "kihon", "irisu", detailFrm.txtIrisu.value, 0);
                    // 処理モード
                    funXmlWrite_Tbl(reqAry[i], "kihon", "mode", "1", 0);

                    // DEL 2013/7/2 shima【QP@30151】No.37 start
                    // 原価希望
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka", detailFrm.txtGenkaKibo.value, 0);

                    //【QP@00342】
                    // 原価希望単位CD
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_tani", detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].value, 0);
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_tani", detailFrm.hdnGenkaTaniCd.value, 0);


                    // 売価希望
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_baika", detailFrm.txtBaikaKibo.value, 0);
                    // DEL 2013/7/2 shima【QP@30151】No.37 end

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
                                funAddRecNode_Tbl(reqAry[i], "FGEN0030", "genryo");
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
                            // XMLレコード挿入カウント+1
                            recInsert++;
                        }

                        //原料行カウント+1
                        gyoCnt++;
                    }


                    //-------------------------- [keisan]テーブル格納 -------------------------
                    //試算項目未入力列（確認メッセージ用）
                    var msgRetu = "";
                    //計算表の列数取得
                    recCnt = detailFrm.cnt_sample.value;
                    //XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0030", "keisan");
                        }
//ADD 2013/07/11 ogawa 【QP@30151】No.13 start
                        // //項目固定チェック
                        if (detailDoc.getElementById("chkKoumoku_"+j).checked) {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "1", j);
                        } else {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "0", j);
                        }
//ADD 2013/07/11 ogawa 【QP@30151】No.13 end
                        // 試作SEQ
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);
                        // 有効歩留（％）
                        var yuuko_budomari = detailDoc.getElementById("txtYukoBudomari_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "yuuko_budomari", yuuko_budomari, j);
                        // 平均充填量（ｇ）
                        var heikinjyutenryo = detailDoc.getElementById("txtHeikinZyu_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "heikinjyutenryo", heikinjyutenryo, j);
                        // 固定費/ケース
                        var kesu_kotehi = detailDoc.getElementById("txtCaseKotei_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_kotehi", kesu_kotehi, j);
                        // 固定費/KG
                        var kg_kotehi = detailDoc.getElementById("txtKgKotei_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_kotehi", kg_kotehi, j);
                        // ADD 2013/11/1 QP@30154 okano start
                        // 利益/ケース
                        var kesu_rieki = detailDoc.getElementById("txtCaseRieki_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_rieki", kesu_rieki, j);
                        // 利益/KG
                        var kg_rieki = detailDoc.getElementById("txtKgRieki_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_rieki", kg_rieki, j);
                        // ADD 2013/11/1 QP@30154 okano end
                        // 試算日
                        funXmlWrite_Tbl(reqAry[i], "keisan", "shisan_date", detailDoc.getElementById("txtShisanHi_"+j).value, j);

                        //未入力項目のチェック（有効歩留・平均充填量・固定費のいずれかが未入力の場合）
                        //【QP@00342】
                        //固定費/ケースの場合（固定費/ケースの入力チェック）
                        if(koteihiFg == "1"){
                            // MOD 2013/11/1 QP@30154 okano start
//                        		if(yuuko_budomari == "" || heikinjyutenryo == "" || kesu_kotehi == ""){
                            // MOD 2013/11/25 QP@30154 okano start
//                        		if(yuuko_budomari == "" || heikinjyutenryo == "" || kesu_kotehi == "" || kesu_rieki == ""){
                            if(yuuko_budomari == "" || heikinjyutenryo == "" || kesu_kotehi == ""){
                            // MOD 2013/11/25 QP@30154 okano end
                            // MOD 2013/11/1 QP@30154 okano end
	                        	//【QP@00342】試算中止
	                        	var fg_chusi = detailDoc.getElementById("hdnSisanChusi_"+j).value;
	                        	if(fg_chusi == "1"){
	                        		//試算中止の場合はメッセージをださない
	                        	}
	                        	else{
	                                msgRetu += "【" + (j+1) + "列目】";
	                        	}
	                        }
                        }
                        //固定費/KGの場合（固定費/KGの入力チェック）
                        else if(koteihiFg == "2"){
                            // MOD 2013/11/1 QP@30154 okano start
//                        		if(yuuko_budomari == "" || heikinjyutenryo == "" || kg_kotehi == "" ){
                            // MOD 2013/11/25 QP@30154 okano start
//                        		if(yuuko_budomari == "" || heikinjyutenryo == "" || kg_kotehi == "" || kg_rieki == "" ){
                            if(yuuko_budomari == "" || heikinjyutenryo == "" || kg_kotehi == "" ){
                            // MOD 2013/11/25 QP@30154 okano end
                            // MOD 2013/11/1 QP@30154 okano end
	                        	//【QP@00342】試算中止
	                        	var fg_chusi = detailDoc.getElementById("hdnSisanChusi_"+j).value;
	                        	if(fg_chusi == "1"){
	                        		//試算中止の場合はメッセージをださない
	                        	}
	                        	else{
	                                msgRetu += "【" + (j+1) + "列目】";
	                        	}
	                        }
                        }


//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.38
                        //製造工程版
                        var seizo_han = detailDoc.getElementById("txtSeizoHan_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seizokotei_han", seizo_han, j);
                        //製造工程
                        var seizo_shosai = detailDoc.getElementById("hdnSeizoShosai_"+j).value;
                        funXmlWrite_Tbl(reqAry[i], "keisan", "seizokotei_shosai", seizo_shosai, j);
//mod end   -------------------------------------------------------------------------------

						// 【QP@00342】試算中止

                        funXmlWrite_Tbl(reqAry[i], "keisan", "fg_chusi", detailDoc.getElementById("hdnSisanChusi_"+j).value, j);

                        // ADD 2014/1/10 QP@30154 追加課題No.11 nishigawa start
                        // 充填量水相（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyusui", detailDoc.getElementById("txtSuiZyuten_"+j).value, j);
                        // 充填量油相（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "zyuabura", detailDoc.getElementById("txtYuZyuten_"+j).value, j);
                        // 合計量（ｇ）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "gokei", detailDoc.getElementById("total"+j).value, j);
                        // 比重
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hiju", detailDoc.getElementById("hijyu"+j).value, j);
                        // レベル量（㎏）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "reberu", detailDoc.getElementById("txtLebelRyo_"+j).value, j);
                        // 比重加算量（㎏）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "hijukasan", detailDoc.getElementById("hijyuKasan"+j).value, j);
                        // 原料費（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genryo", detailDoc.getElementById("genryohi"+j).value, j);
                        // 材料費（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_zairyohi", detailDoc.getElementById("zairyohi"+j).value, j);
                        // 原価計（円）/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "cs_genka", detailDoc.getElementById("genkakei"+j).value, j);
                        // 原価計（円）/個
                        funXmlWrite_Tbl(reqAry[i], "keisan", "ko_genka", detailDoc.getElementById("genkakeiKo"+j).value, j);
                        // 原料費（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genryo", detailDoc.getElementById("genryohiKG"+j).value, j);
                        // 材料費（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_zairyohi", detailDoc.getElementById("zairyohiKG"+j).value, j);
                        // 原価計（円）/㎏
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genka", detailDoc.getElementById("txtKgGenkake_"+j).value, j);
                        // 売価
                        funXmlWrite_Tbl(reqAry[i], "keisan", "baika", detailDoc.getElementById("baika"+j).value, j);
                        // 粗利（％）
                        funXmlWrite_Tbl(reqAry[i], "keisan", "arari", detailDoc.getElementById("sori"+j).value, j);
                        // ADD 2014/1/10 QP@30154 追加課題No.11 nishigawa end

                    }


                    //-------------------------- [shizai]テーブル格納 -------------------------
                    //未入力フラグ
                    var minyuFg = false;
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
                                funAddRecNode_Tbl(reqAry[i], "FGEN0030", "shizai");
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
                            if(detailFrm.txtTankaShizai[j].value == ""){
                                minyuFg = true;
                            }

                            // 歩留（％）
                            funXmlWrite_Tbl(reqAry[i], "shizai", "budomari", detailFrm.txtBudomariShizai[j].value, recInsert);
                            if(detailFrm.txtBudomariShizai[j].value == ""){
                                minyuFg = true;
                            }

                            // 使用量/ｹｰｽ
                            funXmlWrite_Tbl(reqAry[i], "shizai", "shiyouryo", detailFrm.txtSiyouShizai[j].value, recInsert);
                            if(detailFrm.txtSiyouShizai[j].value == ""){
                                minyuFg = true;
                            }

                            // 登録者ID
                            funXmlWrite_Tbl(reqAry[i], "shizai", "id_toroku", detailFrm.hdnId_toroku[j].value, recInsert);
                            // 登録日
                            funXmlWrite_Tbl(reqAry[i], "shizai", "dt_toroku", detailFrm.hdnDt_toroku[j].value, recInsert);
                            // XMLレコード挿入カウント+1
                            recInsert++;
                        }
                    }

                    //単価・歩留・使用量の未入力確認チェック
                    if(minyuFg){
                        //確認メッセージを表示
                        if(funConfMsgBox(E000012) == ConBtnYes){

                        }else{

                            return false;
                        }
                    }

                    //平均充填量・有効歩留・固定費が未入力確認チェック
                    if(msgRetu != ""){
                        //確認メッセージを表示
                        if(funConfMsgBox(msgRetu + "\\n" + E000013) == ConBtnYes){

                        }else{

                            return false;
                        }
                    }

                    // ADD 2013/7/2 shima【QP@30151】No.37 start
                    //-------------------------- [kihonsub]テーブル格納 -------------------------
                    //列数取得
                    recCnt = detailFrm.cnt_sample.value;
                    //XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0030", "kihonsub");
                        }
                        // 試作SEQ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);

	                    // 原価希望
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "kibo_genka",  detailDoc.getElementById("txtGenkaKibo"+j).value, j);

	                    // 原価希望単位CD
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "kibo_genka_tani", detailDoc.getElementById("hdnGenkaTaniCd"+j).value, j);

	                    // 売価希望
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "kibo_baika", detailDoc.getElementById("txtBaikaKibo"+j).value, j);
                    }
                    // ADD 2013/7/2 shima【QP@30151】No.37 end

                    break;
            }
        }

        //入力項目の変更確認
        else if (XmlId.toString() == "RGEN0050"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0020

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
                    // 【QP@00342】試作CD-枝番
                    funXmlWrite_Tbl(reqAry[i], "kihon", "no_eda", headerFrm.txtEdaNo.value, 0);

                    // 採用サンプルＮＯ
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "no_sanpuru", headerFrm.ddlSaiyoSample.options[headerFrm.ddlSaiyoSample.selectedIndex].value, 0);
                    // 工場　担当会社
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    // 工場　担当工場
                    funXmlWrite_Tbl(reqAry[i], "kihon", "cd_kojyo", detailFrm.ddlSeizoKojo.options[detailFrm.ddlSeizoKojo.selectedIndex].value, 0);
                    // 入り数
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "irisu", detailFrm.txtIrisu.value, 0);
                    // 荷姿
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "nisugata", detailFrm.txtNisugata.value, 0);
                    // 原価希望
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka", detailFrm.txtGenkaKibo.value, 0);
                    // 原価希望単位CD
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_genka_cd_tani", detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].value, 0);
                    // 売価希望
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "kibo_baika", detailFrm.txtBaikaKibo.value, 0);
                    // 想定物量
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "butu_sotei", detailFrm.txtSoteiButuryo.value, 0);
                    // 販売時期
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "ziki_hanbai", detailFrm.txtHanbaiJiki.value, 0);
                    // 計画売上
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_uriage", detailFrm.txtKeikakuUriage.value, 0);
                    // 計画利益
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "keikaku_rieki", detailFrm.txtKeikakuRieki.value, 0);
                    // 販売後売上
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_uriage", detailFrm.txtHanbaigoUriage.value, 0);
                    // 販売後利益
                    //funXmlWrite_Tbl(reqAry[i], "kihon", "hanbaigo_rieki", detailFrm.txtHanbaigoRieki.value, 0);

                    // DEL 2013/7/2 shima【QP@30151】No.37 start
                    // 製造ロット
//                    funXmlWrite_Tbl(reqAry[i], "kihon", "seizo_roto", detailFrm.txtSeizoRot.value, 0);
                    // DEL 2013/7/2 shima【QP@30151】No.37 end

                    // 原価試算メモ
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan", detailFrm.txtGenkaMemo.value, 0);
                    // 原価試算メモ（営業連絡用）
                    funXmlWrite_Tbl(reqAry[i], "kihon", "memo_genkashisan_eigyo", detailFrm.txtGenkaMemoEigyo.value, 0);
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
                                funAddRecNode_Tbl(reqAry[i], "FGEN0020", "genryo");
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
                            funAddRecNode_Tbl(reqAry[i], "FGEN0020", "keisan");
                        }
//ADD 2013/07/11 ogawa 【QP@30151】No.13 start
                        // //項目固定チェック
                       if (detailDoc.getElementById("chkKoumoku_"+j).checked) {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "1", j);
                        } else {
                            funXmlWrite_Tbl(reqAry[i], "keisan","fg_koumokuchk", "", j);
                        }
//ADD 2013/07/11 ogawa 【QP@30151】No.13 end
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
                        // ADD 2013/11/1 QP@30154 okano start
                        // 利益/ケース
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kesu_rieki", detailDoc.getElementById("txtCaseRieki_"+j).value, j);
                        // 利益/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_rieki", detailDoc.getElementById("txtKgRieki_"+j).value, j);
                        // ADD 2013/11/1 QP@30154 okano end
                        // 原価計/KG
                        funXmlWrite_Tbl(reqAry[i], "keisan", "kg_genkake", detailDoc.getElementById("txtKgGenkake_"+j).value, j);

                        // 【QP@00342】試算中止

                        funXmlWrite_Tbl(reqAry[i], "keisan", "fg_chusi", detailDoc.getElementById("hdnSisanChusi_"+j).value, j);
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
                                funAddRecNode_Tbl(reqAry[i], "FGEN0020", "shizai");
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
                            // XMLレコード挿入カウント+1
                            recInsert++;
                        }
                    }

                    // ADD 2013/7/2 shima【QP@30151】No.37 start
                    //-------------------------- [kihonsub]テーブル格納 -------------------------
                    //列数取得
                    recCnt = detailFrm.cnt_sample.value;
                    //XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){
                        if (j != 0) {
                            funAddRecNode_Tbl(reqAry[i], "FGEN0020", "kihonsub");
                        }
                        // 試作SEQ
                        funXmlWrite_Tbl(reqAry[i], "kihonsub", "seq_shisaku", detailDoc.getElementById("hdnSeq_Shisaku_"+j).value, j);

	                    // 製造ロット
	                    funXmlWrite_Tbl(reqAry[i], "kihonsub", "seizo_roto", detailDoc.getElementById("txtSeizoRot"+j).value, j);
                    }
                    // ADD 2013/7/2 shima【QP@30151】No.37 end


                    break;
            }
        }


        //印刷
        else if (XmlId.toString() == "RGEN0051"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0050

                    //ループカウント
                    var recCnt;
                    var j;
                    //-------------------------- [table]テーブル格納 -------------------------
                    //列数取得
                    recCnt = detailFrm.cnt_sample.value;
                    //XMLレコード挿入カウント
                    var recInsert = 0;
                    //XMLへ書き込み
    				// 20160524  KPX@1600766 ADD start
                    // 工場　担当会社
                    funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, recInsert);
    				// 20160524  KPX@1600766 ADD end

                    for( j = 0; j < recCnt; j++ ){
                        //列数に対するオブジェクトが存在する場合
                        if(detailDoc.getElementById("chkInsatu"+j)){
                            //オブジェクトがチェックされている場合
                            if(detailDoc.getElementById("chkInsatu"+j).checked){
                                if (recInsert != 0) {
                                    funAddRecNode_Tbl(reqAry[i], "FGEN0050", "table");
                                }
                                // 試作CD-社員CD
                                funXmlWrite_Tbl(reqAry[i], "table", "cd_shain", headerFrm.txtShainCd.value, recInsert);
                                // 試作CD-年
                                funXmlWrite_Tbl(reqAry[i], "table", "nen", headerFrm.txtNen.value, recInsert);
                                // 試作CD-追番
                                funXmlWrite_Tbl(reqAry[i], "table", "no_oi", headerFrm.txtOiNo.value, recInsert);

                                // 【QP@00342】試作CD-枝番
			                    funXmlWrite_Tbl(reqAry[i], "table", "no_eda", headerFrm.txtEdaNo.value, recInsert);

                                // 試作SEQ
                                funXmlWrite_Tbl(reqAry[i], "table", "seq_shisaku", detailDoc.getElementById("chkInsatu"+j).value, recInsert);

                                // 【QP@00342】充填量水相
			                    funXmlWrite_Tbl(reqAry[i], "table", "zyuten_sui", detailDoc.getElementById("txtSuiZyuten_"+j).value, recInsert);

			                    // 【QP@00342】充填量油相
			                    funXmlWrite_Tbl(reqAry[i], "table", "zyuten_yu", detailDoc.getElementById("txtYuZyuten_"+j).value, recInsert);


                                // XMLレコード挿入カウント+1
                                recInsert++;
                            }
                        }

                    }

                    break;
            }
        }

        //マスタ単価・歩留
        else if (XmlId.toString() == "RGEN0070"){
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0070

                    //ループカウント
                    var recCnt;
                    var j;
                    //-------------------------- [table]テーブル格納 -------------------------
                    //列数取得
                    recCnt = detailFrm.cnt_genryo.value;
                    //XMLレコード挿入カウント
                    var recInsert = 0;
                    //原料行カウント
                    var gyoCnt = 1;
                    //XMLへ書き込み
                    for( j = 0; j < recCnt; j++ ){
                        //列数に対するオブジェクトが存在する場合
                        if(detailDoc.getElementById("chkGenryo_"+gyoCnt)){
                            //オブジェクトがチェックされている場合
                            if(detailDoc.getElementById("chkGenryo_"+gyoCnt).checked){
                                if (recInsert != 0) {
                                    funAddRecNode_Tbl(reqAry[i], "FGEN0070", "genryo");
                                }
                                // 試作CD-社員CD
                                funXmlWrite_Tbl(reqAry[i], "genryo", "cd_shain", headerFrm.txtShainCd.value, recInsert);
                                // 試作CD-年
                                funXmlWrite_Tbl(reqAry[i], "genryo", "nen", headerFrm.txtNen.value, recInsert);
                                // 試作CD-追番
                                funXmlWrite_Tbl(reqAry[i], "genryo", "no_oi", headerFrm.txtOiNo.value, recInsert);

                                // 【QP@00342】試作CD-枝番
                                funXmlWrite_Tbl(reqAry[i], "genryo", "no_eda", headerFrm.txtEdaNo.value, recInsert);

                                // 行番号
                                funXmlWrite_Tbl(reqAry[i], "genryo", "seq_gyo", gyoCnt, recInsert);
                                // 工程CD
                                funXmlWrite_Tbl(reqAry[i], "genryo", "cd_kotei", detailDoc.getElementById("hdnCd_kotei_"+gyoCnt).value, recInsert);
                                // 工程SEQ
                                funXmlWrite_Tbl(reqAry[i], "genryo", "seq_kotei", detailDoc.getElementById("hdnSeq_kotei_"+gyoCnt).value, recInsert);
                                // 原料CD
                                funXmlWrite_Tbl(reqAry[i], "genryo", "cd_genryo", detailDoc.getElementById("txtCd_genryo_"+gyoCnt).value, recInsert);
                                // XMLレコード挿入カウント+1
                                recInsert++;
                            }
                        }

                        //原料行カウント+1
                        gyoCnt++;

                    }

                    break;
            }
        //2010/02/23 NAKAMURA ADD START-------------------------------------------------
        }else if (XmlId.toString() == "RGEN0090") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0090
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    //【QP@00342】
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
        //2010/02/23 NAKAMURA ADD END---------------------------------------------------

        //資材検索
        if (XmlId.toString() == "RGEN0080") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN0080
                    //資材テーブルオブジェクト取得
                    tblShizai = detailDoc.getElementById("tblList4");
                    //資材表の行数取得
                    recCnt = tblShizai.rows.length;
                    //XMLレコード挿入カウント
                    var recInsert = 0;
                    //XMLへ書き込み
                    // 資材SEQ
                    funXmlWrite_Tbl(reqAry[i], "table", "seq_shizai", 1, 0);
                    // 会社CD
                    funXmlWrite_Tbl(reqAry[i], "table", "cd_kaisya", detailFrm.ddlSeizoKaisya.options[detailFrm.ddlSeizoKaisya.selectedIndex].value, 0);
                    // 工場CD
                    funXmlWrite_Tbl(reqAry[i], "table", "cd_kojyo", detailFrm.ddlSeizoKojo.options[detailFrm.ddlSeizoKojo.selectedIndex].value, 0);
                    // 資材CD
                    funXmlWrite_Tbl(reqAry[i], "table", "cd_shizai", detailFrm.txtCdShizai[CurrentRow].value, 0);

                    break;
            }
        }
        //【QP@00342】枝番作成
        else if (XmlId.toString() == "RGEN2200") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2180
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "shurui", headerFrm.hdnShuruiEda.value, 0);
//ADD start 20120410 hisahori
                    funXmlWrite(reqAry[i], "eda_nm_shisaku", headerFrm.hdnShisakuNmEda.value, 0);
//ADD end 20120410 hisahori
                  //【QP@40812】No.3 ADD start 2015/03/03 TT.Kitazawa
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                  //【QP@40812】No.3 ADD end 2015/03/03 TT.Kitazawa
                    break;
            }
        }
//20160617  KPX@502111_No.5 ADD start
        //自家原料情報取得・更新
        else if (XmlId.toString() == "RGEN2280") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2280
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    break;
            }
        }
        //自家原料情報取得・更新
        else if (XmlId.toString() == "JW821") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2260
                    funXmlWrite(reqAry[i], "cd_shain", headerFrm.txtShainCd.value, 0);
                    funXmlWrite(reqAry[i], "nen", headerFrm.txtNen.value, 0);
                    funXmlWrite(reqAry[i], "no_oi", headerFrm.txtOiNo.value, 0);
                    funXmlWrite(reqAry[i], "no_eda", headerFrm.txtEdaNo.value, 0);
                    //検索モード：試作Ｎｏより
                    funXmlWrite(reqAry[i], "syoriMode", "1", 0);
                    break;
            }
        }
//20160617  KPX@502111_No.5 ADD start

    }

    return true;

}


//========================================================================================
// 資材行削除処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/28
// 概要  ：選択されている資材行を削除する
//========================================================================================
function funDeleteShizai(){

    //明細ﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document;
    var frm = document.frm00;

    //テーブル参照
    var tblShizai = detailDoc.getElementById("tblList4");
    var gyoCount = tblShizai.rows.length;

    //資材行の削除
    for(var i=0; i<gyoCount; i++){
        //選択されている行の場合
        if(frm.chkShizaiGyo[i].checked){
            //行削除
            tblShizai.deleteRow(i);
            i = i - 1;
            gyoCount = gyoCount - 1;
        }
    }

    //現在の行数を取り直し
    gyoCount = tblShizai.rows.length;

    //資材行の追加
    for(var i=gyoCount; i<20; i++){
        //最終行追加
        funAddShizai();
    }

    return true;
}

//========================================================================================
// 資材行追加処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/28
// 概要  ：最終行を追加する
//========================================================================================
function funAddShizai(){

    //明細ﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document;
    var frm = document.frm00;

    // 工場　担当会社
    var cd_kaisha = frm.ddlSeizoKaisya.options[frm.ddlSeizoKaisya.selectedIndex].value;
    // 工場　担当工場
    var cd_kojo = frm.ddlSeizoKojo.options[frm.ddlSeizoKojo.selectedIndex].value;

    //テーブル参照
    var tblShizai = detailDoc.getElementById("tblList4");

    //行数取得
    var strlength = tblShizai.rows.length;

    //TR要素追加
    var row = tblShizai.insertRow(strlength);

    //TD要素追加
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
    var cell8 = row.insertCell(7);

    //選択
    cell1.style.backgroundColor = "#ffff88";
    cell1.className = "column";
    cell1.setAttribute("class","column");
    cell1.setAttribute("width","48px");
    cell1.setAttribute("align","center");
    cell1.innerHTML = "<input type=\"checkbox\" name=\"chkShizaiGyo\" tabindex=\"27\" />&nbsp;";

    //工場記号
    cell2.className = "column";
    cell2.setAttribute("class","column");
    cell2.setAttribute("width","50px");
    cell2.setAttribute("align","center");
    cell2.innerHTML = "<input type=\"text\" id=\"txtKigouKojo\" name=\"txtKigouKojo\" class=\"table_text_view\" readonly style=\"text-align:center;\" value=\"\" tabindex=\"-1\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnKaisha_Shizai\" name=\"hdnKaisha_Shizai\" value=\"" + cd_kaisha + "\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnKojo_Shizai\"   name=\"hdnKojo_Shizai\" value=\"" + cd_kojo + "\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnId_toroku\"   name=\"hdnId_toroku\" value=\"\" />";
    cell2.innerHTML += "<input type=\"hidden\"  id=\"hdnDt_toroku\"   name=\"hdnDt_toroku\" value=\"\" />";

    //資材コード
    cell3.style.backgroundColor = "#ffff88";
    cell3.className = "column";
    cell3.setAttribute("class","column");
    cell3.setAttribute("width","100px");
    cell3.setAttribute("align","center");

    //【QP@00342】資材テーブル修正
    //cell3.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funInsertCdZero(this);funShizaiSearch()\" id=\"txtCdShizai\" name=\"txtCdShizai\" class=\"table_text_disb\" style=\"text-align:center\" value=\"\" tabindex=\"27\" />";
	cell3.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funInsertCdZero(this);funChangeSelectRowColor3(this);funShizaiSearch();\" id=\"txtCdShizai\" name=\"txtCdShizai\" class=\"table_text_disb\" style=\"text-align:center\" value=\"\" tabindex=\"27\" />";

    //資材名
    cell4.style.backgroundColor = "#ffff88";
    cell4.className = "column";
    cell4.setAttribute("class","column");
    cell4.setAttribute("width","353px");
    cell4.setAttribute("align","left");
    cell4.innerHTML = "<input type=\"text\" id=\"txtNmShizai\" name=\"txtNmShizai\" class=\"table_text_disb\"  style=\"ime-mode:active;text-align:left\" value=\"\" tabindex=\"27\" />";

    //単価
    cell5.style.backgroundColor = "#ffff88";
    cell5.className = "column";
    cell5.setAttribute("class","column");
    cell5.setAttribute("width","100px");
    cell5.setAttribute("align","right");
    cell5.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtTankaShizai\" name=\"txtTankaShizai\" class=\"table_text_disb\" style=\"text-align:right\" value=\"\" tabindex=\"27\" />";

    //歩留
    cell6.style.backgroundColor = "#ffff88";
    cell6.className = "column";
    cell6.setAttribute("class","column");
    cell6.setAttribute("width","80px");
    cell6.setAttribute("align","right");
    cell6.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomariShizai\" name=\"txtBudomariShizai\" class=\"table_text_disb\" style=\"text-align:right\" value=\"\" tabindex=\"27\" />";

    //使用量/ケース
    cell7.style.backgroundColor = "#ffff88";
    cell7.className = "column";
    cell7.setAttribute("class","column");
    cell7.setAttribute("width","120");
    cell7.setAttribute("align","right");
    cell7.innerHTML = "<input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtSiyouShizai\" name=\"txtSiyouShizai\" class=\"table_text_disb\" style=\"text-align:right;\" value=\"\" tabindex=\"27\" />";

    //金額
    cell8.className = "column";
    cell8.setAttribute("class","column");
    cell8.setAttribute("width","100px");
    cell8.setAttribute("align","right");
    cell8.innerHTML = "&nbsp;";

    return true;
}

//========================================================================================
// コンボボックス作成処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/21
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

            //「採用無し」挿入
            objNewOption = document.createElement("option");
            obj.options.add(objNewOption);
            objNewOption.innerText = "採用無し";
            objNewOption.value = SaiyouNashiValue;

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

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.38
        //サンプルNoコンボボックス
        case 6:
            atbName = "nm_sanpuru"; //サンプルNo
            atbCd = "seq_shisaku"; //試作SEQ
            tableNm = "keisan";           //テーブル行
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得

            for (i = 0; i < reccnt; i++) {
		        if (funXmlRead_3(xmlData, tableNm, atbCd, 0, i) != "") {
		            objNewOption = document.createElement("option");
		            obj.options.add(objNewOption);
		            if(funXmlRead_3(xmlData, tableNm, atbName, 0, i) == ""){
		            	objNewOption.innerText = "";
		            }
		            else{
			            objNewOption.innerText = funXmlRead_3(xmlData, tableNm, atbName, 0, i);
		            }
		            objNewOption.value = funXmlRead_3(xmlData, tableNm, atbCd, 0, i);
		        }
   			}

   			//先頭行の削除
    	    obj.options[0] = null;

		    //ｺﾝﾎﾞﾎﾞｯｸｽのﾃﾞﾌｫﾙﾄ表示
		    obj.selectedIndex = 0;

		    return true;

//mod end   -------------------------------------------------------------------------------

		//【QP@00342】枝番種類
        case 7:
            atbName = "nm_literal";
            atbCd = "cd_literal";
            tableNm = "table";
            reccnt = funGetLength_3(xmlData, tableNm, 0); //件数取得
            break;

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
// 作成日：2009/10/22
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
                if (obj.options[i].value == funXmlRead_3(xmlFGEN0011O, "kihon", "kibo_genka_cd_tani", 0, 0)) {
                    selIndex = i;
                }
                break;
        }
    }

    //ｺﾝﾎﾞﾎﾞｯｸｽのｲﾝﾃﾞｯｸｽ指定
    obj.selectedIndex = selIndex;

    return true;
}

//========================================================================================
// 更新情報表示
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
// 引数  ：①xmlUser ：更新情報格納XML名
//       ：②ObjectId：設定オブジェクトID
// 戻り値：なし
// 概要  ：更新情報表示用のHTML文を生成、出力する。
//========================================================================================
function funUpdateDisplay(xmlData, ObjectId) {

    var obj;              //設定先オブジェクト
    var tableNm;          //読み込みテーブル名
    var OutputHtml;       //出力HTML
    var kenkyu_tanto;     //研究所更新者
    var kenkyu_date;      //研究所更新日付
    var seihon_tanto;     //生産本部更新者
    var seihon_date       //生産本部更新日付
    var genshizai_tanto;  //原資材調達更新者
    var genshizai_date;   //原資材調達更新日付
    var kozyo_tanto;      //工場更新者
    var kozyo_date;       //工場更新日付
    var saiyo_date;       //採用ｻﾝﾌﾟﾙNo更新日
    var sam_id_koshin;    //サンプルNo更新者

    //HTML出力オブジェクト設定
    obj = document.getElementById(ObjectId);

    //テーブル名設定
    tableNm = "kihon";

    //XMLより値取得
    kenkyu_tanto = funXmlRead_3(xmlData, tableNm, "kenkyu_tanto", 0, 0);
    kenkyu_date = funXmlRead_3(xmlData, tableNm, "kenkyu_date", 0, 0);
    seihon_tanto = funXmlRead_3(xmlData, tableNm, "seihon_tanto", 0, 0);
    seihon_date = funXmlRead_3(xmlData, tableNm, "seihon_date", 0, 0);
    genshizai_tanto = funXmlRead_3(xmlData, tableNm, "genshizai_tanto", 0, 0);
    genshizai_date = funXmlRead_3(xmlData, tableNm, "genshizai_date", 0, 0);
    kozyo_tanto = funXmlRead_3(xmlData, tableNm, "kozyo_tanto", 0, 0);
    kozyo_date = funXmlRead_3(xmlData, tableNm, "kozyo_date", 0, 0);
    saiyo_date = funXmlRead_3(xmlData, tableNm, "saiyo_date", 0, 0);
    sam_id_koshin = funXmlRead_3(xmlData, tableNm, "sam_id_koshin", 0, 0);

    //出力HTML設定
    OutputHtml = "<table width=\"360px\" border=\"0\">";
    OutputHtml += "    <tr>";
    OutputHtml += "        <td width=\"100px\">";
    OutputHtml += "           研究所：";
    OutputHtml += "        </td>";
    OutputHtml += "        <td width=\"150px\">";
    OutputHtml += "           <input type=\"text\" style=\"width:110px;\" class=\"table_text_view\" readonly value=\"" + kenkyu_tanto + "\" tabindex=\"-1\" />";
    OutputHtml += "        </td>";
    OutputHtml += "        <td width=\"80px\">" + kenkyu_date + "</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "    <tr>";
    OutputHtml += "        <td>生産管理：</td>";
    OutputHtml += "        <td>";
    OutputHtml += "           <input type=\"text\" style=\"width:110px;\" class=\"table_text_view\" readonly value=\"" + seihon_tanto + "\" tabindex=\"-1\" />";
    OutputHtml += "        </td>";
    OutputHtml += "        <td>" + seihon_date + "</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "    <tr>";
    OutputHtml += "        <td>原資材調達：</td>";
    OutputHtml += "        <td>";
    OutputHtml += "           <input type=\"text\" style=\"width:110px;\" class=\"table_text_view\" readonly value=\"" + genshizai_tanto + "\" tabindex=\"-1\" />";
    OutputHtml += "        </td>";
    OutputHtml += "        <td>" + genshizai_date + "</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "    <tr>";
    OutputHtml += "        <td>工場：</td>";
    OutputHtml += "        <td>";
    OutputHtml += "           <input type=\"text\" style=\"width:110px;\" class=\"table_text_view\" readonly value=\"" + kozyo_tanto + "\" tabindex=\"-1\" />";
    OutputHtml += "        </td>";
    OutputHtml += "        <td>" + kozyo_date + "</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "    <tr>";
    OutputHtml += "        <td>サンプルNo確定：</td>";
    OutputHtml += "        <td>";
    OutputHtml += "           <input type=\"text\" style=\"width:110px;\" class=\"table_text_view\" readonly value=\"" + sam_id_koshin + "\" tabindex=\"-1\" />";
    OutputHtml += "        </td>";
    OutputHtml += "        <td>" + saiyo_date + "</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "</table>";

    //HTMLを出力s
    obj.innerHTML = OutputHtml;

    return true;

}

//========================================================================================
//原価試算、サンプル毎の情報取得＆表示処理
//作成者：H.Shima
//作成日：2013/7/2
//引数  ：①xmlData ：設定XML
//      ：②ObjectId：設定オブジェクトID
//戻り値：なし
//概要  ：基本情報テーブル（下側）表示用のHTML文を生成、出力する。
//========================================================================================
function funKihonSubDisplay(xmlData, ObjectId){

	var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
	var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
	var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
	var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

	var OutputHtml;       //出力HTML
	var i;                //ループカウント
	var cnt_sample;

	var genka_kibo;							//希望原価
	var genka_tani;							//
	var genka_tanicd;						//
	var baika_kibo;							//希望特約
	var baika_tani;							//
	// ADD 2013/9/6 okano【QP@30151】No.30 start
	var sotei_buturyo_s;
	var sotei_buturyo_u;
	var sotei_buturyo_k;
	// ADD 2013/9/6 okano【QP@30151】No.30 end
	var sotei_buturyo;						//想定物量
	var hatubai_jiki;						//初回納品時期
	var hanbai_kikan_t;						//販売期間
	var hanbai_kikan_s;						//
	var hanbai_kikan_k;						//
	var keikaku_uriage;						//計画売上
	var keikaku_rieki;						//計画利益
	var hanbaigo_uriage;					//
	var hanbaigo_rieki;						//
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

	OutputHtml += "<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" bordercolor=\"#000000\" style=\"table-layout:fixed;\">";

	OutputHtml += "<tr>";

	//【QP@40812】No.16 ADD start 2015/03/03 TT.Kitazawa
	//------------------------------------- サンプルNo. -------------------------------------
    //サンプルNo
    var nm_sample = "";
    //【QP@40812】No.6 MOD start 2015/08/26 TT.Kitazawa
    var no_iraisample = "";
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		nm_sample = funXmlRead_3(xmlData, tableKihonSubNm, "nm_sample", 0, i);
		// メール渡し用：サンプルNo.の保存
		if( no_iraisample == ""){
			no_iraisample = nm_sample;
		}
		else{
			no_iraisample = no_iraisample + "," + nm_sample;
		}

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\" width=\"175\" >";
		OutputHtml += "    <input type=\"text\" id = \"nm_sample" + i + "\" readonly style=\"width:100%;border-width:0px;text-align:right;\" value=\"" + nm_sample + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}
	headerFrm.hdnNo_iraisampleForMail.value = no_iraisample;
	//【QP@40812】No.6 MOD end 2015/08/26 TT.Kitazawa

//	OutputHtml += "</tr><tr>" ;          /* del 2015/08/21 */
	//【QP@40812】No.16 ADD end  2015/03/03 TT.Kitazawa

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- 原価希望 -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		genka_kibo = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka", 0, i);
		genka_tani = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka_nm_tani", 0, i);
		genka_tanicd = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka_cd_tani", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\" width=\"175\" >";
		OutputHtml += "    <input type=\"text\" id=\"txtGenkaKibo" + i + "\" name=\"txtGenkaKibo" + i + "\" style=\"width:80px;\" class=\"table_text_view\" readonly  value=\"" + genka_kibo + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtGenkaTani" + i + "\" name=\"txtGenkaTani" + i + "\" style=\"width:70px;\"class=\"table_text_view\" readonly value=\"" + genka_tani + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"hidden\" id=\"hdnGenkaTaniCd" + i + "\" name=\"hdnGenkaTaniCd" + i + "\" style=\"width:82px;\" value=\"" + genka_tanicd + "\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- 売価希望 -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		baika_kibo = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_baika", 0, i);
		baika_tani = funXmlRead_3(xmlData, tableKihonSubNm, "kibo_genka_nm_tani", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtBaikaKibo" + i + "\" name=\"txtBaikaKibo" + i + "\" style=\"width:80px;\" class=\"table_text_view\" readonly  value=\"" + baika_kibo + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtBaikaTani" + i + "\" name=\"txtBaikaTani" + i + "\" style=\"width:70px;\"class=\"table_text_view\" readonly value=\"" + baika_tani + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	// ADD 2015/05/15 TT.Kitazawa【QP@40812】No.20 start
	//------------------------------------- 販売期間 -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		hanbai_kikan_t = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_t", 0, i);
		hanbai_kikan_s = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_suti", 0, i);
		hanbai_kikan_k = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_k", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_t" + i + "\" name=\"txtHanbaiKikan_t" + i + "\" style=\"width:65px;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_t + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:20px;text-align:right;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_s + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_k" + i + "\" name=\"txtHanbaiKikan_k" + i + "\" style=\"width:65px;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_k + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}
	OutputHtml += "</tr><tr>" ;
	// ADD 2015/05/15 TT.Kitazawa【QP@40812】No.20 start

	//------------------------------------- 想定物量 -------------------------------------
	// ADD 2013/9/6 okano【QP@30151】No.30 start
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		sotei_buturyo_s = funXmlRead_3(xmlData, tableKihonSubNm, "soote_buturyo_s", 0, i);
		if( sotei_buturyo_s == "" ){
		} else {
			sotei_buturyo_s = sotei_buturyo_s * 1;
		}
		sotei_buturyo_u = funXmlRead_3(xmlData, tableKihonSubNm, "sotei_buturyo_u", 0, i);
		sotei_buturyo_k = funXmlRead_3(xmlData, tableKihonSubNm, "sotei_buturyo_k", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		// MOD 2013/12/24 QP@30154 okano start
//			OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" style=\"width:70px;text-align:right;\"class=\"table_text_view\" readonly value=\"" + sotei_buturyo_s + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_s" + i + "\" name=\"txtSoteiButuryo_s" + i + "\" style=\"width:70px;\"class=\"table_text_view\" readonly value=\"" + sotei_buturyo_s + "\" tabindex=\"-1\" />";
		// MOD 2013/12/24 QP@30154 okano end
		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_u" + i + "\" name=\"txtSoteiButuryo_u" + i + "\" style=\"width:40px;text-align:right;\"class=\"table_text_view\" readonly value=\"" + sotei_buturyo_u + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo_k" + i + "\" name=\"txtSoteiButuryo_k" + i + "\" style=\"width:50px;\"class=\"table_text_view\" readonly value=\"" + sotei_buturyo_k + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>";
	// ADD 2013/9/6 okano【QP@30151】No.30 end

	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		sotei_buturyo = funXmlRead_3(xmlData, tableKihonSubNm, "soote_buturyo", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtSoteiButuryo" + i + "\" style=\"ime-mode:active;\" name=\"txtSoteiButuryo" + i + "\" class=\"table_text_view\" readonly value=\"" + sotei_buturyo + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- 発売時期 -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		hatubai_jiki = funXmlRead_3(xmlData, tableKihonSubNm, "ziki_hatubai", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtHatubaiJiki" + i + "\" style=\"ime-mode:active;\" name=\"txtHatubaiJiki" + i + "\" class=\"table_text_view\" readonly value=\"" + hatubai_jiki + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	// DEL 2015/05/15 TT.Kitazawa【QP@40812】No.20 start（上に移動）
/*    //------------------------------------- 販売期間 -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		hanbai_kikan_t = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_t", 0, i);
		hanbai_kikan_s = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_suti", 0, i);
		hanbai_kikan_k = funXmlRead_3(xmlData, tableKihonSubNm, "kikan_hanbai_k", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_t" + i + "\" name=\"txtHanbaiKikan_t" + i + "\" style=\"width:65px;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_t + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_s" + i + "\" name=\"txtHanbaiKikan_s" + i + "\" style=\"width:20px;text-align:right;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_s + "\" tabindex=\"-1\" />";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaiKikan_k" + i + "\" name=\"txtHanbaiKikan_k" + i + "\" style=\"width:65px;\"class=\"table_text_view\" readonly value=\"" + hanbai_kikan_k + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}
	OutputHtml += "</tr><tr>" ;
 */
	// DEL 2015/05/15 TT.Kitazawa【QP@40812】No.20 end

	//------------------------------------- 計画売上 -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		keikaku_uriage = funXmlRead_3(xmlData, tableKihonSubNm, "keikaku_uriage", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtKeikakuUriage" + i + "\" style=\"ime-mode:active;\" name=\"txtKeikakuUriage" + i + "\" class=\"table_text_view\" readonly value=\"" + keikaku_uriage + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- 計画利益 -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		keikaku_rieki = funXmlRead_3(xmlData, tableKihonSubNm, "keikaku_rieki", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtKeikakuRieki" + i + "\" style=\"ime-mode:active;\" name=\"txtKeikakuRieki" + i + "\" class=\"table_text_view\" readonly value=\"" + keikaku_rieki + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- 販売後売上 -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		hanbaigo_uriage = funXmlRead_3(xmlData, tableKihonSubNm, "hanbaigo_uriage", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoUriage" + i + "\" style=\"ime-mode:active;\" name=\"txtHanbaigoUriage" + i + "\" class=\"table_text_view\" readonly value=\"" + hanbaigo_uriage + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- 販売後利益 -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		//XMLデータ取得
		hanbaigo_rieki = funXmlRead_3(xmlData, tableKihonSubNm, "hanbaigo_rieki", 0, i);

		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		OutputHtml += "    <input type=\"text\" id=\"txtHanbaigoRieki" + i + "\"  style=\"ime-mode:active;\" name=\"txtHanbaigoRieki" + i + "\" class=\"table_text_view\" readonly value=\"" + hanbaigo_rieki + "\" tabindex=\"-1\" />";
		OutputHtml += "</td>";
	}

	OutputHtml += "</tr><tr>" ;

	//------------------------------------- 製造ロット -------------------------------------
	for(i = 0; i < cnt_sample; i++){
		// MOD 2014/8/7 shima【QP@30154】No.63 start
		//XMLデータ取得
		seizo_rot = funXmlRead_3(xmlData, tableKihonSubNm, "seizo_roto", 0, i);
		//中止フラグ取得
		fg_chusi = funXmlRead_3(xmlData, tableKihonSubNm, "fg_chusi", 0, i);
		//項目固定チェック取得
		fg_koumokuchk = funXmlRead_3(xmlData, tableKihonSubNm, "fg_koumokuchk", 0, i);
		//HTML生成
		OutputHtml += "<td bgcolor=\"\" height=\"19px\">";
		if(fg_koumokuchk == "1"){
			//項目チェック列は背景白色
			OutputHtml += "    <input type=\"text\" id=\"txtSeizoRot" + i + "\" style=\"ime-mode:active;background-color:" + color_read + "\" name=\"txtSeizoRot" + i + "\" class=\"table_text_act\" readonly value=\"" + seizo_rot + "\" tabindex=\"14\" />";
		} else if(fg_chusi == "1"){
			//試算中止列は背景色をグレーに
			OutputHtml += "    <input type=\"text\" id=\"txtSeizoRot" + i + "\" style=\"ime-mode:disabled;background-color:#c0c0c0\";\" name=\"txtSeizoRot" + i + "\" class=\"table_text_act\" value=\"" + seizo_rot + "\" tabindex=\"14\" />";
		}else{
			OutputHtml += "    <input type=\"text\" id=\"txtSeizoRot" + i + "\" style=\"ime-mode:active;\" name=\"txtSeizoRot" + i + "\" class=\"table_text_act\" value=\"" + seizo_rot + "\" tabindex=\"14\" />";
		}
		OutputHtml += "</td>";
		// MOD 2014/8/7 shima【QP@30154】No.63 end
	}

	OutputHtml += "</tr></table>" ;


	//------------------------------------------------------------------------------------
	//                                  HTML出力
	//------------------------------------------------------------------------------------
	//HTMLを出力
	obj.innerHTML = OutputHtml;

	OutputHtml = null;

	return true;
}

//========================================================================================
// 原料テーブル（左側）変更連絡情報表示
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
// 引数  ：①xmlUser ：更新情報格納XML名
// 戻り値：なし
// 概要  ：原料テーブル（左側）変更連絡情報表示
//========================================================================================
function funGenryo_HenkouDisplay(xmlData) {

    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var tablekihonNm = "henkou";
    var reccnt = funGetLength_3(xmlData, tablekihonNm, 0); //件数取得
    var henko_renraku;

    //変更連絡設定
    for(i = 0; i < reccnt; i++){

        //XMLデータ取得
        henko_renraku = funKuhakuChg(funXmlRead_3(xmlData, tablekihonNm, "henkourenraku", 0, i));

        //変更
        detailDoc.getElementById("txtHenkouRen_"+(i+1)).value = henko_renraku;
    }

    return true;
}


//========================================================================================
// 原料テーブル（左側）情報表示
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
// 引数  ：①xmlUser ：更新情報格納XML名
//       ：②ObjectId：設定オブジェクトID
// 戻り値：なし
// 概要  ：原料テーブル（左側）表示用のHTML文を生成、出力する。
//========================================================================================
function funGenryo_LeftDisplay(xmlData, ObjectId) {

    var headerFrm = parent.header.document.frm00; //ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var Kengen = headerFrm.hdnKengen.value;       //権限：機能ID
    var obj;              //設定先オブジェクト
    var tablekihonNm;     //読み込みテーブル名
    var tableGenryoNm;    //読み込みテーブル名
    var OutputHtml;       //出力HTML
    var cnt_genryo;       //行数
    var sort_kotei;       //工程
    var cd_kotei;         //工程CD
    var seq_kotei;        //工程SEQ
    var cd_genryo;        //原料CD
    var nm_genryo;        //原料名
    var henko_renraku;    //変更
    var tanka;            //単価
    var budomari;         //歩留
    var genryo_fg;        //原料行フラグ
    var i;                //ループカウント
    //2010/02/18 NAKAMURA ADD START------------
    var hdnKojoNmTanka;		//工場名 単価
    var hdnKojoNmBudomari;	//工場名 歩留
    //2010/02/18 NAKAMURA ADD END--------------

    //HTML出力オブジェクト設定
    obj = detailDoc.getElementById(ObjectId);
    OutputHtml = "";

    //テーブル名設定
    tablekihonNm = "kihon";
    tableGenryoNm = "genryo";

    //行数取得
    cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

    //出力HTML設定
    OutputHtml += "<input type=\"hidden\" id=\"cnt_genryo\" name=\"cnt_genryo\" value=\"" + cnt_genryo + "\">";

	//テーブル表示
	// MOD 2013/7/2 shima【QP@30151】No.37 start
	//OutputHtml += "<table id=\"tblList1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"475px\" style=\"word-break:break-all;word-wrap:break-word;\">";
	OutputHtml += "<table id=\"tblList1\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" width=\"605px\" style=\"word-break:break-all;word-wrap:break-word;\">";
	// MOD 2013/7/2 shima【QP@30151】No.37 end
    for(i = 0; i < cnt_genryo; i++){

        //HTML
        var sentaku_checkbox; //選択チェックボックス
        var tanka_textbox;    //単価テキストボックス
        var budomari_textbox; //歩留テキストボックス
        var cd_kotei_hidden;  //工程CD隠し項目
        var seq_kotei_hidden; //工程SEQ隠し項目
        var chkDisabled;      //チェックボックス入力設定
        var txtReadonly;      //テキストボックス入力設定
        var txtClass;         //テキストボックス背景色
// 20160513  KPX@1600766 ADD start
        var txtType = "text";    //テキストボックスタイプ
// 20160513  KPX@1600766 ADD start

        //XMLデータ取得
        sort_kotei = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "sort_kotei", 0, i));
        cd_kotei = funXmlRead_3(xmlData, tableGenryoNm, "cd_kotei", 0, i);
        seq_kotei = funXmlRead_3(xmlData, tableGenryoNm, "seq_kotei", 0, i);
        cd_genryo = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "cd_genryo", 0, i));
        nm_genryo = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_genryo", 0, i));
        henko_renraku = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "henko_renraku", 0, i));
        tanka = funXmlRead_3(xmlData, tableGenryoNm, "tanka", 0, i);
        budomari = funXmlRead_3(xmlData, tableGenryoNm, "budomari", 0, i);

        genryo_fg = funXmlRead_3(xmlData, tableGenryoNm, "genryo_fg", 0, i);
        //2010/02/18 NAKAMURA ADD START----------------------------------------
        hdnKojoNmTanka = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_kojo_tanka", 0, i));
        hdnKojoNmBudomari = funKuhakuChg(funXmlRead_3(xmlData, tableGenryoNm, "nm_kojo_budomari", 0, i));
        //2010/02/18 NAKAMURA ADD END------------------------------------------


        //【QP@00342】排他
        if(headerFrm.strKengenMoto.value == "999"){
        	chkDisabled = "disabled";
            txtReadonly = "readonly";
            txtClass = henshuNgClass;
        }
        else{
        	//編集権限
        	if(Kengen.toString() == ConFuncIdEdit.toString()){

        		//編集可能に設定
        		chkDisabled = "";
	            txtReadonly = "";
	            txtClass = henshuOkClass;

        		//【QP@00342】ステータス制御
				var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
				var st_seikan = headerFrm.hdnStatus_seikan.value;
				var st_gentyo = headerFrm.hdnStatus_gentyo.value;
				var st_kojo    = headerFrm.hdnStatus_kojo.value;
				var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

				//【QP@00342】部署取得
			    var kenkyu = headerFrm.hdnBusho_kenkyu.value;
			    var seikan = headerFrm.hdnBusho_seikan.value;
			    var gentyo = headerFrm.hdnBusho_gentyo.value;
			    var kojo = headerFrm.hdnBusho_kojo.value;
			    var eigyo = headerFrm.hdnBusho_eigyo.value;

				//【QP@00342】原資材調達部で確認完了の場合
				/*
				 * if( gentyo == "1" && st_gentyo == 2){ chkDisabled =
				 * "disabled"; txtReadonly = "readonly"; txtClass =
				 * henshuNgClass; }
				 */

				//【QP@00342】工場で確認完了の場合
				/*
				 * if( kojo == "1" && st_kojo == 2){ chkDisabled = "disabled";
				 * txtReadonly = "readonly"; txtClass = henshuNgClass; }
				 */


			    //20160607 【KPX@1502111_No8】 ADD start
			    // 工場で確認完了（工場ステータス ≧ 2） の場合
		        // 部署：生管は生産管理部ステータス ≧ 3
			    if( kojo == "1" && st_kojo >= 2 ){
					chkDisabled = "disabled";
		            txtReadonly = "readonly";
		            txtClass = henshuNgClass;
				}
				//20160607 【KPX@1502111_No8】 ADD end

				//【QP@00342】生産管理部ステータス >= 3 or 営業ステータス >= 4　の場合
				if( st_seikan >= 3 || st_eigyo >= 4){
					chkDisabled = "disabled";
		            txtReadonly = "readonly";
		            txtClass = henshuNgClass;
				}
	        }
	        //【QP@00342】閲覧+Excel権限
	        else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
	            chkDisabled = "disabled";
	            txtReadonly = "readonly";
	            txtClass = henshuNgClass;
	        }
        }

// 20160513  KPX@1600766 ADD start
        //研究所以外は全表示
        //研究所：グループ会社の単価表示制御（リテラルマスタより）
        if (tankaHyoujiFlg == "1" || tankaHyoujiFlg == "0")  {
            chkDisabled = "disabled";
            txtReadonly = "readonly";
            txtClass = henshuNgClass;

            if (tankaHyoujiFlg == "0") {
                //単価非表示
                txtType = "hidden";
            }
        }
// 20160513  KPX@1600766 ADD end


        if(genryo_fg == "1"){
            //原料行の場合は選択、単価、歩留、工程CD、工程SEQオブジェクトの生成
            sentaku_checkbox = "<input type=\"checkbox\" id=\"chkGenryo_" + (i+1) + "\" name=\"chkGenryo_" + (i+1) + "\" height=\"12px\" " + chkDisabled + "  tabindex=\"18\" />";
// 20160513  KPX@1600766 MOD start
            //研究所：単価非表示のグループ会社は表示を隠す
//            tanka_textbox = "<input type=\"text\" onchange=\"ShisakuGenka_Tankachanged(" + (i+1) + ");setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\"   id=\"txtTanka_" + (i+1) + "\" name=\"txtTanka_" + (i+1) + "\"  class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + tanka + "\" " + txtReadonly + " tabindex=\"18\" onDblClick=\"openWin(" + (i+1) + ")\" />";
//            budomari_textbox = "<input type=\"text\" onchange=\"ShisakuGenka_Budomarichanged(" + (i+1) + ");setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomari_" + (i+1) + "\" name=\"txtBudomari_" + (i+1) + "\"  class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + budomari + "\" " + txtReadonly + " tabindex=\"18\" onDblClick=\"openWin(" + (i+1) + ")\" />";
            tanka_textbox = "<input type=\"" + txtType + "\" onchange=\"ShisakuGenka_Tankachanged(" + (i+1) + ");setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\"   id=\"txtTanka_" + (i+1) + "\" name=\"txtTanka_" + (i+1) + "\"  class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + tanka + "\" " + txtReadonly + " tabindex=\"18\" onDblClick=\"openWin(" + (i+1) + ")\" />";
            budomari_textbox = "<input type=\"" + txtType + "\" onchange=\"ShisakuGenka_Budomarichanged(" + (i+1) + ");setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomari_" + (i+1) + "\" name=\"txtBudomari_" + (i+1) + "\"  class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + budomari + "\" " + txtReadonly + " tabindex=\"18\" onDblClick=\"openWin(" + (i+1) + ")\" />";
// 20160513  KPX@1600766 MOD start
            cd_kotei_hidden = "            <input type=\"hidden\"  id=\"hdnCd_kotei_" + (i+1) + "\" name=\"hdnCd_kotei_" + (i+1) + "\" value=\"" + cd_kotei + "\">";
            seq_kotei_hidden = "            <input type=\"hidden\" id=\"hdnSeq_kotei_" + (i+1) + "\" name=\"hdnSeq_kotei_" + (i+1) + "\" value=\"" + seq_kotei + "\">";

        	//2010/02/16 NAKAMURA ADD START---
        	//行
        	OutputHtml += "        		<input type=\"hidden\" id=\"gyo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + (i + 1) + "\" title=\"" + (i + 1) + "\" tabindex=\"-1\" name=\"gyo\" style=\"text-align:center;\" />";
        	//2010/02/16 NAKAMURA ADD END-----

        }else if(genryo_fg == "2"){
            //工程行の場合は選択、単価、歩留、工程CD、工程SEQは空白
            sentaku_checkbox = "&nbsp;";
            tanka_textbox = "&nbsp;";
            budomari_textbox = "&nbsp;";
            cd_kotei_hidden = "";
            seq_kotei_hidden = "";
        }

        //テーブルタグ生成
        //【シサクイックH24年度対応】No46 2012/04/20 MOD Start
//        OutputHtml += "    <tr class=\"disprow\">";
        OutputHtml += "    <tr class=\"disprow\" id=\"tableRowL_" + i + "\" name=\"tableRowL_" + i + "\">";
        //【シサクイックH24年度対応】No46 2012/04/20 MOD End

        //選択
        OutputHtml += "        <td class=\"column\" style=\"text-align:right;width:20px;\">" + sentaku_checkbox + "</td>";

        //工程
        OutputHtml += "        <td class=\"column\" style=\"text-align:right;width:20px;\"><input type=\"text\" id=\"sort_kote\" name=\"sort_kote\" class=\"table_text_view\" style=\"text-align:center;\" readonly value=\"" + sort_kotei + "\" tabindex=\"-1\" /></td>";

        //2010/02/18 NAKAMURA UPDATE START---------------------------
        //原料コード
        //OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:105px;\">";
        //OutputHtml += "            <input type=\"text\" id=\"txtCd_genryo_" + (i+1) + "\" name=\"txtCd_genryo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + cd_genryo + "\" tabindex=\"-1\" onDblClick=\"openWin(" + (i+1) + ")\" />";
        //OutputHtml += "        </td>";
		if(genryo_fg == "1"){
			//原料行の場合Wクリック時のイベントを設定
	        OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:105px;\">";
	        OutputHtml += "            <input type=\"text\" id=\"txtCd_genryo_" + (i+1) + "\" name=\"txtCd_genryo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + cd_genryo + "\" tabindex=\"-1\" onDblClick=\"openWin(" + (i+1) + ")\" />";
	        OutputHtml += "        </td>";
		}else if(genryo_fg == "2"){
			//工程行の場合Wクリックのイベントは未設定
	        OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:105px;\">";
	        OutputHtml += "            <input type=\"text\" id=\"txtCd_genryo_" + (i+1) + "\" name=\"txtCd_genryo_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + cd_genryo + "\" tabindex=\"-1\" />";
	        OutputHtml += "        </td>";
		}
		//2010/02/18 NAKAMURA UDPATE END-----------------------------

        //原料名
		// MOD 2013/7/2 shima【QP@30151】No.37 start
		//OutputHtml += "        <td class=\"column\" style=\"width:180px;\">";
		OutputHtml += "        <td class=\"column\" style=\"width:310px;\">";
		// MOD 2013/7/2 shima【QP@30151】No.37 end
        //2010/02/18 NAKAMURA UPDATE START------------------------------------
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly value=\"" + nm_genryo + "\" title=\"" + nm_genryo + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id=\"txtCd_genryoNm_" + (i+1) + "\" class=\"table_text_view\" readonly value=\"" + nm_genryo + "\" title=\"" + nm_genryo + "\" tabindex=\"-1\" onDblClick=\"openWin(" + (i+1) + ")\" />";
        //2010/02/18 NAKAMURA UPDATE END--------------------------------------

        //工程CD
        OutputHtml += cd_kotei_hidden;
        //工程SEQ
        OutputHtml += seq_kotei_hidden;
        OutputHtml += "        </td>";

        //変更
        OutputHtml += "        <td class=\"column\" style=\"text-align:center;width:20px;\"><input type=\"text\" name=\"txtHenkouRen_"+(i+1)+"\" id=\"txtHenkouRen_"+(i+1)+"\" class=\"table_text_view\" readonly value=\"" + henko_renraku + "\" onDblClick=\"openWin(" + (i+1) + ")\" tabindex=\"-1\" /></td>";

        //単価
        OutputHtml += "        <td class=\"column\" style=\"width:70px;\">";
        OutputHtml += "            " + tanka_textbox;
        OutputHtml += "        </td>";

        //歩留
        OutputHtml += "        <td class=\"column\" style=\"width:45px;\">";
        OutputHtml += "            " + budomari_textbox;
        OutputHtml += "        </td>";

        //2010/02/18 NAKAMURA ADD START-------------------
        //工場名 単価
        OutputHtml += "        <input type=\"hidden\" value=\"" + hdnKojoNmTanka + "\" name=\"hdnKojoNmTanka_" + (i+1) + "\" id=\"hdnKojoNmTanka_" + (i+1) + "\" >";
        //工場名 歩留
        OutputHtml += "        <input type=\"hidden\" value=\"" + hdnKojoNmBudomari + "\" name=\"hdnKojoNmTanka_" + (i+1) + "\" id=\"hdnKojoNmBudomari_" + (i+1) + "\" >";
		//2010/02/18 NAKAMURA ADD END---------------------

		OutputHtml += "    </tr>";

    }

    OutputHtml += "</table>";


    //HTMLを出力
    obj.innerHTML = OutputHtml;

    OutputHtml = null;

    return true;
}

//========================================================================================
// 原料詳細情報表示 動作確認
// 作成者：Y.Nishigawa
// 作成日：2010/02/12
// 引数  ：gyoNo(Wクリックされた行番号)
// 戻り値：なし
// 概要  ：原料詳細情報表示
//========================================================================================
var win; //サブウィンドウオブジェクト（原価試算画面内　グローバル変数）
var subwinShowBln;		//サブウィンドウ表示フラグ（原価試算画面内　グローバル変数）
function openWin(wclickgyoNo){

    //※検討事項
    //　サブウィンドウを閉じるのと同時に、素早く再度表示を行った場合にエラーが発生する
    //　エラー発生後はサブウィンドウが表示されなくなる
    //　対策として、原料行の連打防止　etc

    //サブウィンドウが生成されていない場合
    if(win == null){

        //サブウィンドウ内HTML
        var outputHtml;

        var detailDoc = parent.detail.document;		//明細ﾌﾚｰﾑのDocument参照

        var gyoNo;				//行番号
        var GenryoCd;			//原料コード
        var GenryoNm;			//原料名
        var KojoNmTanka;		//単価　工場名
        var KojoNmBudomari;		//単価　工場名
        var subwinOnCloseEv;	//サブウィンドウクローズ時イベント
        gyoNo = detailDoc.getElementById("gyo_" + wclickgyoNo);							//指定行の行番号表示用の列制御参照
        GenryoCd = detailDoc.getElementById("txtCd_genryo_" + wclickgyoNo);				//指定行の原料コード表示用の列制御参照
        GenryoNm = detailDoc.getElementById("txtCd_genryoNm_" + wclickgyoNo);			//指定行の原料名表示用の列制御参照
        KojoNmTanka = detailDoc.getElementById("hdnKojoNmTanka_" + wclickgyoNo);		// 指定行の単価
																						// 工場名保持列制御参照
        KojoNmBudomari = detailDoc.getElementById("hdnKojoNmBudomari_" + wclickgyoNo);	// 指定行の歩留
																						// 工場名保持列制御参照

        //サブウィンドウ内HTMLを記述
        outputHtml = "";

    	outputHtml = outputHtml + "		 	<input type=\"hidden\" style=\"border:none;\" id=\"GyoNo\" size=\"35\" readonly name=\"GyoNo\" value=\"" + gyoNo.value + "\" />";

        outputHtml = outputHtml + "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
        /*
		 * outputHtml = outputHtml + " <tr>"; outputHtml = outputHtml + "
		 * <td style=\"text-align:left;\">行番号</td>"; outputHtml = outputHtml + "
		 * <td style=\"text-align:left;\">：</td>"; outputHtml = outputHtml + "
		 * <td>"; outputHtml = outputHtml + " <input type=\"text\"
		 * style=\"border:none;\" id=\"GyoNo\" size=\"35\" readonly
		 * name=\"GyoNo\" value=\"" + gyoNo.value + "\" />"; outputHtml =
		 * outputHtml + " </td>"; outputHtml = outputHtml + " </tr>";
		 */
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">原料コード</td>";
    	outputHtml = outputHtml + "      <td style=\"text-align:left;\">：</td>";
        outputHtml = outputHtml + "      <td>";
        outputHtml = outputHtml + "		 	<input type=\"text\" style=\"border:none;\" id=\"GenryoCd\" size=\"35\" readonly name=\"GenryoCd\" value=\"" + GenryoCd.value + "\" />";
        outputHtml = outputHtml + "		 </td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">原料名</td>";
    	outputHtml = outputHtml + "      <td style=\"text-align:left;\">：</td>";
        outputHtml = outputHtml + "		 <td>";
        outputHtml = outputHtml + "		 	<input type=\"text\" style=\"border:none;\" id=\"GenryoNm\" size=\"35\" readonly name=\"GenryoNm\" value=\"" + GenryoNm.value + "\" />";
        outputHtml = outputHtml + "		 </td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">単価　工場名</td>";
    	outputHtml = outputHtml + "      <td style=\"text-align:left;\">：</td>";
        outputHtml = outputHtml + "		 <td>";
        outputHtml = outputHtml + "		 	<input type=\"text\" style=\"border:none;\" id=\"TankaKojoNm\" size=\"35\" readonly name=\"TankaKojoNm\" value=\"" + KojoNmTanka.value + "\" />";
        outputHtml = outputHtml + "		 </td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "   <tr>";
        outputHtml = outputHtml + "      <td style=\"text-align:left;\">歩留　工場名</td>";
    	outputHtml = outputHtml + "      <td style=\"text-align:left;\">：</td>";
        outputHtml = outputHtml + "		 <td>";
        outputHtml = outputHtml + "		 	<input type=\"text\" style=\"border:none;\" id=\"BudomariKojoNm\" size=\"35\" readonly name=\"BudomariKojoNm\" value=\"" + KojoNmBudomari.value + "\" />";
        outputHtml = outputHtml + "		 </td>";
        outputHtml = outputHtml + "   </tr>";
        outputHtml = outputHtml + "</table>";

        //サブウィンドウ生成
        win = new Window("win01", {
                    title: "原料詳細情報"
                    ,className: "alphacube"
                    //,top:595
                    //,left:15
                    ,top:600+event.y //マウスポインタのY座標（+600調整）
                    ,left:30+event.x //マウスポインタのX座標（+30調整）
                    ,width:280
                    ,height:80
                    ,resizable:false
                    ,minimizable:false
                    ,maximizable:false
                    ,opacity:0.9
                    ,hideEffect:Element.hide
                    //,parent:headerFrm
              });

        //サブウィンドウ内に「閉じる」ボタンを追加
        win.setDestroyOnClose();

        //サブウィンドウ内にHTMLを設定
        win.setHTMLContent(outputHtml);

        //サブウィンドウクローズ時イベント
		subwinOnCloseEv = {
			onClose: function(eventName, winObj) {
				subwinShowBln = false;
			}
		}

		//サブウィンドウにイベントを設定
		Windows.addObserver(subwinOnCloseEv);

		//サブウィンドウ表示フラグオン
		subwinShowBln = true;

        //サブウィンドウを表示
        win.show();

    }
    //サブウィンドウが生成されている場合
    else{

        try{
            //表示されているサブウィンドウを破棄（catch対象）
            win.destroy();

            //サブウィンドウを初期化
            win = null;

            //自身を呼び出し、サブウィンドウの再表示
            openWin(wclickgyoNo);

        }
        //win=null処理だけでは画面上のサブウィンドウは初期化されずに、
        //win.destroy()実行後にエラーとなる。
        //上記理由により、サブウィンドウが閉じられている場合は、
        //win.destroy()の例外を捕捉し、サブウィンドウの初期化と再表示を行う
        catch (e) {

            //サブウィンドウを初期化
            win = null;

            //自身を呼び出し、サブウィンドウの再表示
            openWin(wclickgyoNo);

        }
    }
}

//========================================================================================
// 原料テーブル（右側）情報表示
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
// 引数  ：①xmlUser ：更新情報格納XML名
//       ：②ObjectId：設定オブジェクトID
// 戻り値：なし
// 概要  ：原料テーブル（右側）表示用のHTML文を生成、出力する。
//========================================================================================
function funGenryo_RightDisplay(xmlData, ObjectId) {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var obj;              //設定先オブジェクト
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var tablekihonNm;     //読み込みテーブル名
    var tableHaigoNm;     //読み込みテーブル名
    var OutputHtml;       //出力HTML
    var cnt_genryo;       //行数
    //【QP@10713】20111031 hagiwara del start
    //var cnt_sample;       //列数
    //【QP@10713】20111031 hagiwara del end
    var table_size;       //テーブル幅
    var seq_shisaku;      //試作SEQ
    var shisakuDate;      //試作日付
    var nm_sample;        //サンプルNO（名称）
    var haigo;            //配合
    var kingaku;          //金額
    var i;                //ループカウント
    var j;                //ループカウント
    var color = "#ffffff";

// 20160513  KPX@1600766 ADD start
    var txtType= "text";      //テキストボックスタイプ
    if (tankaHyoujiFlg == "0") {
    	//単価表示不可：金額非表示
    	txtType = "hidden";
    }
//20160513  KPX@1600766 ADD start

    //------------------------------------------------------------------------------------
    //                                    初期設定
    //------------------------------------------------------------------------------------
    //HTML出力オブジェクト設定
    obj = detailDoc.getElementById(ObjectId);
    OutputHtml = "";

    //テーブル名設定
    tablekihonNm = "kihon";
    tableHaigoNm = "shisaku";

    //列数取得
    cnt_sample = funXmlRead_3(xmlData, tablekihonNm, "cnt_sanpuru", 0, 0);

    //行数取得
    cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

    //テーブル幅取得
    table_size = 175 * cnt_sample;


    //------------------------------------------------------------------------------------
    //                                  テーブル生成
    //------------------------------------------------------------------------------------
    //テーブル生成
    OutputHtml += "<table id=\"dataTable2\" name=\"dataTable2\" cellspacing=\"0\" width=\"" + table_size + "px;\">";

    //右側ヘッダサイズ指定
    OutputHtml += "<colgroup>";
    for(i = 0; i < cnt_sample; i++){
        OutputHtml += "   <col style=\"width:175px;\"/>";
    }
    OutputHtml += "</colgroup>";

    //右側ヘッダテーブル設定
    OutputHtml += "<thead class=\"rowtitle\">";
    OutputHtml += "    <tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
    for(i = 0; i < cnt_sample; i++){

        //XMLデータ取得
        seq_shisaku = funXmlRead_3(xmlData, tableHaigoNm+i, "seq_shisaku", 0, 1);
        shisakuDate = funXmlRead_3(xmlData, tableHaigoNm+i, "shisakuDate", 0, 1);
        nm_sample = funXmlRead_3(xmlData, tableHaigoNm+i, "nm_sample", 0, 1);

        OutputHtml += "        <th class=\"columntitle\">";
        OutputHtml += "            <table frame=\"void\" width=\"100%\" height=\"50px\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolor=\"#bbbbbb\">";
        OutputHtml += "                <tr><td align=\"center\" colspan=\"2\" height=\"12px\"><input type=\"checkbox\" name=\"chkInsatu" + i + "\" id=\"chkInsatu" + i + "\" height=\"12px\" value=\"" + seq_shisaku + "\" tabindex=\"19\"/></td></tr>";

        //試作日付
        if(shisakuDate.length < 10){
            shisakuDate = "0000/00/00";
            color = "#0066FF";
        }
        OutputHtml += "                <tr><td align=\"center\" colspan=\"2\"><font color=\""+ color +"\">" + shisakuDate + "</font></td></tr>";

        //サンプルNo
        OutputHtml += "                <tr>";
        OutputHtml += "                   <td style=\"width:175px;\" align=\"center\" colspan=\"2\">";
        OutputHtml += "                      <input type=\"text\" style=\"border-width:0px;background-color:#0066FF;color:#FFFFFF;text-align:center;\" readonly value=\"" + nm_sample + "\" tabindex=\"-1\" />";

        OutputHtml += "                   </td>";
        OutputHtml += "                </tr>";

    	OutputHtml += "                <tr><td align=\"center\"><font color=\"#ffffff\" style=\"\">配合(kg)※</font></td><td align=\"center\" style=\"width:45%;\"><font color=\"#ffffff\">金額(円)</font></td></tr>";
        OutputHtml += "            </table>";
        OutputHtml += "        </th>";
    }
    OutputHtml += "    </tr>";
    OutputHtml += "</thead>";

    //右側明細テーブル設定
    OutputHtml += "<tbody>";
    OutputHtml += "    <table class=\"detail\" align=\"left\" id=\"tblList2\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:" + table_size + "px;display:list-item\">";

    //行ループ
    for(i = 0; i < cnt_genryo; i++){

    	//【シサクイックH24年度対応】No46 2012/04/20 MOD Start
//    	OutputHtml += "        <tr class=\"disprow\">";
        OutputHtml += "        <tr class=\"disprow\" id=\"tableRowR_" + i + "\" name=\"tableRowR_" + i + "\" >";
        //【シサクイックH24年度対応】No46 2012/04/20 MOD End

        //列ループ
        for(j = 0; j < cnt_sample; j++){

            //XMLデータ取得
            haigo  = funXmlRead_3(xmlData, tableHaigoNm+j, "haigo", 0, i+2);
            kingaku = funXmlRead_3(xmlData, tableHaigoNm+j, "kingaku", 0, i+2);

            OutputHtml += "            <td class=\"column\" style=\"width:175px;\" align=\"left\">";
            OutputHtml += "                <table frame=\"void\" width=\"100%\" height=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\">";
            OutputHtml += "                    <tr>";
            OutputHtml += "                        <td class=\"dot_r\" style=\"width:55%;text-align:right;\">" + "<input type=\"text\" style=\"width:82px;border-width:0px;background-color:#FFFFFF;text-align:right;\" readonly value=\"" + haigo + "\" tabindex=\"-1\" />" + "</td>";
// 20160513  KPX@1600766 MOD start
//            OutputHtml += "                        <td class=\"dot_l\" style=\"width:45%;text-align:right;\">" + "<input type=\"text\" style=\"width:67px;border-width:0px;background-color:#FFFFFF;text-align:right;\" readonly value=\"" + kingaku + "\" tabindex=\"-1\" />" + "</td>";
            OutputHtml += "                        <td class=\"dot_l\" style=\"width:45%;text-align:right;\">" + "<input type=\"" + txtType + "\" style=\"width:67px;border-width:0px;background-color:#FFFFFF;text-align:right;\" readonly value=\"" + kingaku + "\" tabindex=\"-1\" />" + "</td>";
// 20160513  KPX@1600766 MOD end
            OutputHtml += "                    </tr>";
            OutputHtml += "                </table>";
            OutputHtml += "            </td>";
        }
        OutputHtml += "        </tr>";
    }

    OutputHtml += "    </table>";
    OutputHtml += "</tbody>";

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
// 作成日：2009/10/23
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
	//【QP@10713】20111031 TT H.SHIMA
    var frm = document.frm00;				//フォーム参照
    var obj;              //設定先オブジェクト
    var tablekihonNm;     //読み込みテーブル名
    var tableKeisanNm;    //読み込みテーブル名
    var OutputHtml;       //出力HTML
    var cnt_genryo;       //行数
    var cnt_sample;       //列数
    var table_size;       //テーブル幅
    var txtReadonly;      //テキストボックス入力設定
    var txtClass;         //テキストボックス背景色

//ADD 2013/07/09 ogawa 【QP@30151】No.13 start
    var fg_KoumokuChk     //項目固定チェック
//ADD 2013/07/09 ogawa 【QP@30151】No.13 end
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
    var kg_kotehi;		  //固定費/KG
    var kg_genkake;       //原価計/KG
    var baika;            //売価
    var arari;            //粗利（％）
    // ADD 2013/11/1 QP@30154 okano start
    var kesu_rieki;       //利益/ケース
    var kg_rieki;         //利益/KG
    // ADD 2013/11/1 QP@30154 okano end

    var i;                //ループカウント
    var j;                //ループカウント


    //【QP@00342】部署取得
    var kenkyu = headerFrm.hdnBusho_kenkyu.value;
    var seikan = headerFrm.hdnBusho_seikan.value;
    var gentyo = headerFrm.hdnBusho_gentyo.value;
    var kojo = headerFrm.hdnBusho_kojo.value;
    var eigyo = headerFrm.hdnBusho_eigyo.value;

    //【QP@00342】ステータス制御
	var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
	var st_seikan = headerFrm.hdnStatus_seikan.value;
	var st_gentyo = headerFrm.hdnStatus_gentyo.value;
	var st_kojo    = headerFrm.hdnStatus_kojo.value;
	var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

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
    // ADD 2013/11/1 QP@30154 okano start
    var cd_kaisha = funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0);
    // ADD 2013/11/1 QP@30154 okano end
// 20160513  KPX@1600766 ADD start
    var txtType = "text";      //テキストボックスタイプ
// 20160513  KPX@1600766 ADD end

    //------------------------------------------------------------------------------------
    //                                    初期設定
    //------------------------------------------------------------------------------------
    //【QP@00342】排他
    if(headerFrm.strKengenMoto.value == "999"){
    	txtReadonly = "readonly";
        txtClass = henshuNgClass;
    }
    else{
    	//編集権限
	    if(Kengen.toString() == ConFuncIdEdit.toString()){
	    	//【QP@00342】編集可能（生産管理部、工場）
	    	if(seikan == "1" || kojo == "1"){
	    		txtReadonly = "";
	       		txtClass = henshuOkClass;

	       		//【QP@00342】工場で確認完了の場合
	       		/*
				 * if( kojo == "1" && st_kojo == 2){ chkDisabled = "disabled";
				 * txtReadonly = "readonly"; txtClass = henshuNgClass; }
				 */

	       		//20160607 【KPX@1502111_No8】 ADD start
	        	// 部署：工場で確認完了(工場ステータス≧2)の場合
		        // 部署：生管は生産管理部ステータス ≧ 3
				if( kojo == "1" && st_kojo >= 2 ){
					chkDisabled = "disabled";
		            txtReadonly = "readonly";
		            txtClass = henshuNgClass;
				}
				//20160607 【KPX@1502111_No8】 ADD end

	       		//【QP@00342】生産管理部ステータス >= 3 or 営業ステータス >= 4　の場合
				if( st_seikan >= 3 || st_eigyo >= 4){
					chkDisabled = "disabled";
		            txtReadonly = "readonly";
		            txtClass = henshuNgClass;
				}
	    	}
	    	else{
	    		txtReadonly = "readonly";
		        txtClass = henshuNgClass;
// 20160513  KPX@1600766 ADD start
		        //編集不可
		        if (frm.radioKoteihi != null) {
		        	frm.radioKoteihi[0].disabled = true;
		        	frm.radioKoteihi[1].disabled = true;
		        }
// 20160513  KPX@1600766 ADD end
// 20160622  KPX@1502111_No.10 ADD start
		        //サンプルコピーボタンの権限編集
		        detailDoc.frm00.btnSampleCopy.disabled = true;
// 20160622  KPX@1502111_No.10 ADD end
		    }


	    }
	    //【QP@00342】閲覧+Excel権限
	    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
	        txtReadonly = "readonly";
	        txtClass = henshuNgClass;
	    }
    }

// 20160513  KPX@1600766 ADD start
    //研究所：単価表示すべて以外は編集不可
   	if (tankaHyoujiFlg == "1" ) {
        txtReadonly = "readonly";
        txtClass = henshuNgClass;
    } else if (tankaHyoujiFlg == "0") {
        txtReadonly = "readonly";
        txtClass = henshuNgClass;
        //単価非表示
        txtType = "hidden";
    }
//20160513  KPX@1600766 ADD end


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
    table_size = 175 * cnt_sample;

	// DEL 2013/7/2 shima【QP@30151】No.37 start
    //試作列数
	//OutputHtml += "<input type=\"hidden\" id=\"cnt_sample\" name=\"cnt_sample\" value=\"" + cnt_sample + "\">";
	// DEL 2013/7/2 shima【QP@30151】No.37 end
    //試作SEQ
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        seq_shisaku = funXmlRead_3(xmlData, tableKeisanNm, "seq_shisaku", 0, i);

        //試作SEQ
        OutputHtml += "<input type=\"hidden\"  id=\"hdnSeq_Shisaku_" + i + "\" name=\"hdnSeq_Shisaku_" + i + "\" value=\"" + seq_shisaku + "\">";

        //【シサクイックH24年度対応】修正５ 2012/04/13 Start
        arrShisaku_seq[i] = seq_shisaku;
        //【シサクイックH24年度対応】修正５ 2012/04/13 End
    }

    //------------------------------------------------------------------------------------
    //                                  テーブル生成
    //------------------------------------------------------------------------------------
    OutputHtml += "<table class=\"detail\" id=\"tblList3\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:" + table_size + "px;display:list-item\" bordercolordark=\"#000000\">";

//ADD 2013/07/10 ogawa 【QP@30151】No.13 start    //項目固定チェック
    OutputHtml += "    <tr>";
    var seikanchkBuf="";
	//MOD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
    //項目固定チェックは排他　or　生産管理でない場合、制御不可能とする（ステータスによる制御は行わない）
    if((seikan != 1) || (headerFrm.strKengenMoto.value == "999")){
    	seikanchkBuf = "disabled=\"disabled\"";
    }
	//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
    	fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
        //HTML生成
        OutputHtml += "        <td align=\"center\" style=\"width:175px;height:18px\" >";
        //【QP@40812】No.30 MOD start 2015/03/03 TT.Kitazawa
        //  固定チェックを解除のみした時、再計算させなくてもOK
        if(fg_KoumokuChk == "1"){
//            OutputHtml += "            <input type=\"checkbox\" onclick=\"setFg_saikeisan(" + i +");funKChangeMode(" + i + ")\" name=\"chkKoumoku_" + i + "\" id=\"chkKoumoku_" + i + "\" height=\"12px\" value=\"" + fg_KoumokuChk + "\" tabindex=\"19\" " + seikanchkBuf + " checked=\"checked\" />";
            OutputHtml += "            <input type=\"checkbox\" onclick=\"funKChangeMode(" + i + ")\" name=\"chkKoumoku_" + i + "\" id=\"chkKoumoku_" + i + "\" height=\"12px\" value=\"" + fg_KoumokuChk + "\" tabindex=\"19\" " + seikanchkBuf + " checked=\"checked\" />";
        }else{
            OutputHtml += "            <input type=\"checkbox\" onclick=\"setFg_saikeisan(" + i +");funKChangeMode(" + i + ")\"   name=\"chkKoumoku_" + i + "\" id=\"chkKoumoku_" + i + "\" height=\"12px\" value=\"" + fg_KoumokuChk + "\" tabindex=\"19\" " + seikanchkBuf + " />";
        }
        //【QP@40812】No.30 MOD end 2015/03/03 TT.Kitazawa
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

//ADD 2013/07/10 ogawa 【QP@30151】No.13 end
    //サンプルNo
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        nm_sanpuru = funXmlRead_3(xmlData, tableKeisanNm, "nm_sanpuru", 0, i);

        //HTML生成
        OutputHtml += "        <td style=\"width:175px;height:18px;\">";

        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "          <input type=\"text\" readonly style=\"border-width:0px;text-align:right;\" value=\"" + nm_sanpuru + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"txtSample" + i + "\" readonly style=\"width:100%;border-width:0px;text-align:right;\" value=\"" + nm_sanpuru + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

//mod start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.38
    //製造工程版
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        seizo_han = funXmlRead_3(xmlData, tableKeisanNm, "seizokotei_han", 0, i);
        seizo_shosai = funXmlRead_3(xmlData, tableKeisanNm, "seizokotei_shosai", 0, i);
        seq_shisaku_seizo = funXmlRead_3(xmlData, tableKeisanNm, "seq_shisaku", 0, i);
        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";

        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "          <input type=\"text\"  id=\"txtSeizoHan_" + i + "\" name=\"txtSeizoHan_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"" + seizo_han + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\"  id=\"txtSeizoHan_" + i + "\" name=\"txtSeizoHan_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"" + seizo_han + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"hidden\"  id=\"hdnSeizoShosai_" + i + "\" name=\"hdnSeizoShosai_" + i + "\" value=\"" + seizo_shosai + "\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";
//mod end --------------------------------------------------------------------------------

    //試算日
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){

        //XMLデータ取得
        shisan_date = funXmlRead_3(xmlData, tableKeisanNm, "shisan_date", 0, i);
        var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);

        //【QP@00342】編集可能（工場）
        var txtReadonly_sisanbi = "readonly";
	    var txtClass_sisanbi = henshuNgClass;

        /*
		 * if(kojo == "1" && st_kojo != "2" && st_eigyo != "4"){
		 * txtReadonly_sisanbi = ""; txtClass_sisanbi = henshuOkClass; }
		 */

	    //【H24年度対応】No.11 Start
        /*
		 * if(kojo == "1" && st_seikan != "3" && st_eigyo != "4"){
		 * txtReadonly_sisanbi = ""; txtClass_sisanbi = henshuOkClass; }
		 */
	    //【H24年度対応】No.11 End

        //【QP@00342】試算中止
        var val = "";
        var tab = 20;
        if(fg_chusi == "1"){
        	val = "試算中止";
        	txtReadonly_sisanbi = "readonly";
	    	txtClass_sisanbi = henshuNgClass;
	    	tab = -1;
        }
        else{
        	val = shisan_date;
        }

        //HTML生成
        OutputHtml += "        <td style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" id=\"txtShisanHi_" + i + "\" name=\"txtShisanHi_" + i + "\" onblur=\"hidukeSetting(this)\" class=\"" + txtClass_sisanbi + "\" style=\"text-align:right;\" value=\"" + val + "\" " + txtReadonly_sisanbi + " tabindex=\"" + tab + "\" />";
        //【H24年度対応】No.11 Start
//        OutputHtml += "            <input type=\"text\" id=\"txtShisanHi_" + i + "\" name=\"txtShisanHi_" + i + "\" onblur=\"hidukeSetting(this)\" class=\"" + txtClass_sisanbi + "\" style=\"text-align:right;\" value=\"" + val + "\" " + txtReadonly_sisanbi + " tabindex=\"" + tab + "\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"text\" id=\"txtShisanHi_" + i + "\" name=\"txtShisanHi_" + i + "\" class=\"" + txtClass_sisanbi + "\" style=\"text-align:right;\" value=\"" + val + "\" " + txtReadonly_sisanbi + " tabindex=\"" + tab + "\" onFocus = \"funLineHighLight(this)\" />";
        //【H24年度対応】No.11 End
        OutputHtml += "            <input type=\"hidden\" id=\"hdnSisanChusi_" + i + "\" name=\"hdnSisanChusi_" + i + "\" value=\"" + fg_chusi + "\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //充填量水相（ｇ）
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        jyuuten_suiso = funXmlRead_3(xmlData, tableKeisanNm, "jyuuten_suiso", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" id=\"txtSuiZyuten_" + i + "\" name=\"txtSuiZyuten_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ jyuuten_suiso + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id=\"txtSuiZyuten_" + i + "\" name=\"txtSuiZyuten_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ jyuuten_suiso + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //充填量油相（ｇ）
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        jyuuten_yuso = funXmlRead_3(xmlData, tableKeisanNm, "jyuuten_yuso", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" id=\"txtYuZyuten_" + i + "\" name=\"txtYuZyuten_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ jyuuten_yuso + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id=\"txtYuZyuten_" + i + "\" name=\"txtYuZyuten_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ jyuuten_yuso + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end

        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //合計量
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        gookezyuryo = funXmlRead_3(xmlData, tableKeisanNm, "gookezyuryo", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ gookezyuryo + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"total" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ gookezyuryo + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //比重
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        hizyu = funXmlRead_3(xmlData, tableKeisanNm, "hizyu", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ hizyu + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"hijyu" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ hizyu + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //有効歩留（％）
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
    	//【H24年度対応No15】2012/04/16 kazama mod start
    	//xmlデータ取得
    	var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
//MOD 2013/07/10 ogawa 【QP@30151】No.13 start    //項目固定チェック
//修正前ソース
    	//【H24年度対応No15】2012/05/08 TT H.SHIMA mod start
    	//試算中止
//    	if(fg_chusi == "1"){
//    	if(fg_chusi == "1" && txtClass == henshuOkClass){
    	//【H24年度対応No15】2012/05/08 TT H.SHIMA mod end
//修正後ソース
       	//XMLデータ取得
    	fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);

// 20160513  KPX@1600766 MOD start
    	if(fg_KoumokuChk == "1"){
     		//XMLデータ取得
    		yuuko_budomari = funXmlRead_3(xmlData, tableKeisanNm, "yuuko_budomari", 0, i);
     		//HTML生成
     		OutputHtml += "        <td style=\"width:175px;height:18px;\">";
//     		OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" value=\"" + yuuko_budomari + "\" readonly tabindex=\"21\" onFocus = \"funLineHighLight(this)\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" />";
     		OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" value=\"" + yuuko_budomari + "\" readonly tabindex=\"21\" onFocus = \"funLineHighLight(this)\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" />";
     		OutputHtml += "        </td>";
         }else if(fg_chusi == "1" && txtClass == henshuOkClass){
//MOD 2013/07/10 ogawa 【QP@30151】No.13 end

    		//XMLデータ取得
    		yuuko_budomari = funXmlRead_3(xmlData, tableKeisanNm, "yuuko_budomari", 0, i);
    		//HTML生成
    		OutputHtml += "        <td style=\"height:18px;\">";
//    		OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" value=\"" + yuuko_budomari + "\" " + txtReadonly + " tabindex=\"21\" onFocus = \"funLineHighLight(this)\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" />";
    		OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" value=\"" + yuuko_budomari + "\" " + txtReadonly + " tabindex=\"21\" onFocus = \"funLineHighLight(this)\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" />";
    		OutputHtml += "        </td>";
    		if(fg_chusi[i+1] != 1){
    			// 試算中止設定の終了
    			fg_chusi = 0;
    		}
    	}else{
	        //XMLデータ取得
	        yuuko_budomari = funXmlRead_3(xmlData, tableKeisanNm, "yuuko_budomari", 0, i);
	        //HTML生成
	        OutputHtml += "        <td style=\"height:18px;\">";
	        //【QP@00342】
	        // 【QP@10713】20111101 hagiwara mod start
	        //OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + yuuko_budomari + "\" " + txtReadonly + " tabindex=\"21\" />";
//	        OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + yuuko_budomari + "\" " + txtReadonly + " tabindex=\"21\" onFocus = \"funLineHighLight(this)\" />";
	        OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuKiri(this,2);setKanma(this)\" id=\"txtYukoBudomari_" + i + "\" name=\"txtYukoBudomari_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + yuuko_budomari + "\" " + txtReadonly + " tabindex=\"21\" onFocus = \"funLineHighLight(this)\" />";
	        // 【QP@10713】20111101 hagiwara mod end
	        OutputHtml += "        </td>";
    	}
    	//【H24年度対応】2012/04/16 kazama mod end
// 20160513  KPX@1600766 MOD end
    }
    OutputHtml += "    </tr>";



    //レベル量（ｇ）
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        reberuryo = funXmlRead_3(xmlData, tableKeisanNm, "reberuryo", 0, i);

        //HTML生成
        OutputHtml += "        <td style=\"height:18px;\">";


        // 【QP@10713】20111101 hagiwara mod start
// 20160513  KPX@1600766 MOD start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" id=\"txtLebelRyo_" + i + "\" name=\"txtLebelRyo_" + i + "\" value=\""+ reberuryo + "\" tabindex=\"-1\" />";
//        OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" id=\"txtLebelRyo_" + i + "\" name=\"txtLebelRyo_" + i + "\" value=\""+ reberuryo + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" id=\"txtLebelRyo_" + i + "\" name=\"txtLebelRyo_" + i + "\" value=\""+ reberuryo + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
// 20160513  KPX@1600766 MOD end
        // 【QP@10713】20111101 hagiwara mod end

        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //平均充填量（ｇ）
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
    	//【H24年度対応】2012/04/16 kazama mod start
    	//xmlデータ取得
    	var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);

//MOD 2013/07/10 ogawa 【QP@30151】No.13 start    //項目固定チェック
//修正前ソース
    	//【H24年度対応No15】2012/05/08 TT H.SHIMA mod start
    	//試算中止
//    	if(fg_chusi == "1"){
//    	if(fg_chusi == "1" && txtClass == henshuOkClass){
    	//【H24年度対応No15】2012/05/08 TT H.SHIMA mod end
//修正後ソース
       	//XMLデータ取得
    	fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);

// 20160513  KPX@1600766 MOD start
        if(fg_KoumokuChk == "1"){
            //XMLデータ取得
            heikinjyutenryo = funXmlRead_3(xmlData, tableKeisanNm, "heikinjyutenryo", 0, i);

            //HTML生成
            OutputHtml += "        <td style=\"width:175px;height:18px;\">";
//            OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + heikinjyutenryo + "\" readonly tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
            OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + heikinjyutenryo + "\" readonly tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
            OutputHtml += "        </td>";

         }else if(fg_chusi == "1" && txtClass == henshuOkClass){
//MOD 2013/07/10 ogawa 【QP@30151】No.13 end

    		//XMLデータ取得
    		heikinjyutenryo = funXmlRead_3(xmlData, tableKeisanNm, "heikinjyutenryo", 0, i);

    		//HTML生成
    		OutputHtml += "        <td style=\"height:18px;\">";
//    		OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + heikinjyutenryo + "\" " + txtReadonly + " tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
    		OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + heikinjyutenryo + "\" " + txtReadonly + " tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
    		OutputHtml += "        </td>";

    	}else{
	        //XMLデータ取得
	        heikinjyutenryo = funXmlRead_3(xmlData, tableKeisanNm, "heikinjyutenryo", 0, i);

	        //HTML生成
	        OutputHtml += "        <td style=\"height:18px;\">";
	        //【QP@00342】
	        // 【QP@10713】20111101 hagiwara mod start
	        //OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + heikinjyutenryo + "\" " + txtReadonly + " tabindex=\"22\" />";
//	        OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + heikinjyutenryo + "\" " + txtReadonly + " tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
	        OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtHeikinZyu_" + i + "\" name=\"txtHeikinZyu_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + heikinjyutenryo + "\" " + txtReadonly + " tabindex=\"22\" onFocus = \"funLineHighLight(this)\" />";
	        // 【QP@10713】20111101 hagiwara mod end
	        OutputHtml += "        </td>";
    	}
// 20160513  KPX@1600766 MOD end
    	//【H24年度対応】2012/04/16 kazama mod end
    }
    OutputHtml += "    </tr>";



    //比重加算量（ｇ）
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        hizyukasanryo = funXmlRead_3(xmlData, tableKeisanNm, "hizyukasanryo", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ hizyukasanryo + "\" tabindex=\"-1\" />";
// 20160513  KPX@1600766 MOD start
//        OutputHtml += "            <input type=\"text\" id = \"hijyuKasan" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ hizyukasanryo + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" id = \"hijyuKasan" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ hizyukasanryo + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
// 20160513  KPX@1600766 MOD end
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //原料費/ケース
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        kesu_genryohi = funXmlRead_3(xmlData, tableKeisanNm, "kesu_genryohi", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genryohi + "\" tabindex=\"-1\" />";
// 20160513  KPX@1600766 MOD start
//        OutputHtml += "            <input type=\"text\" id = \"genryohi" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genryohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" id = \"genryohi" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genryohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
// 20160513  KPX@1600766 MOD end
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

// 20160513  KPX@1600766 ADD start
    if (tankaHyoujiFlg == "1") {
        //材料費～利益／ケースまで非表示
        txtType = "hidden";
    }
// 20160513  KPX@1600766 ADD end

    //材料費/ケース
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        kesu_zairyohi = funXmlRead_3(xmlData, tableKeisanNm, "kesu_zairyohi", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_zairyohi + "\" tabindex=\"-1\" />";
// 20160513  KPX@1600766 MOD start
//        OutputHtml += "            <input type=\"text\" id = \"zairyohi" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_zairyohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" id = \"zairyohi" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_zairyohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
//20160513  KPX@1600766 MOD end
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";


    //固定費/ケース
	OutputHtml += "    <tr>";
	for(i = 0; i < cnt_sample; i++){
		//【H24年度対応】2012/04/16 kazama mod start
		//xmlデータ取得
		var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
//ADD 2013/07/10 ogawa 【QP@30151】No.13 start    //項目固定チェック
        //XMLデータ取得
		fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
//ADD 2013/07/10 ogawa 【QP@30151】No.13 end    //項目固定チェック

// 20160513  KPX@1600766 MOD start
		//XMLデータ取得
		kesu_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kesu_kotehi", 0, i);

      //【H24年度対応No15】2012/05/08 TT H.SHIMA mod start
    	//試算中止
//    	if(fg_chusi == "1"){
    	if(fg_chusi == "1" && txtClass == henshuOkClass){
    	//【H24年度対応No15】2012/05/08 TT H.SHIMA mod end

    		//XMLデータ取得
//			kesu_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kesu_kotehi", 0, i);

			//HTML生成
			OutputHtml += "        <td style=\"height:18px;\">";

			if(parent.detail.document.frm00.radioKoteihi[0].checked){
//MOD 2013/07/10 ogawa 【QP@30151】No.13 start    //項目固定チェック
//修正前ソース
				//OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" />";
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
//修正後ソース
                if(fg_KoumokuChk == "1"){
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                }else{
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                }
//MOD 2013/07/10 ogawa 【QP@30151】No.13 end    //項目固定チェック
			}else{
				//OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" />";
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
			}

			OutputHtml += "        </td>";

		}else{
    		//XMLデータ取得
//			kesu_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kesu_kotehi", 0, i);

			//HTML生成
			OutputHtml += "        <td style=\"height:18px;\">";

//【QP@10713】2011/11/14 TT H.SHIMA -MOD Start
			// 【QP@10713】20111117 hagiwara mod start
			if(parent.detail.document.frm00.radioKoteihi[0].checked){
			// 【QP@10713】20111117 hagiwara mod end
//MOD 2013/07/10 ogawa 【QP@30151】No.13 start    //項目固定チェック
//修正前ソース
				//【QP@00342】
				// 【QP@10713】20111031 hagiwara mod start
				//OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" />";
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
				// 【QP@10713】20111031 hagiwara mod end
//修正後ソース
                if(fg_KoumokuChk == "1"){
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_kotehi + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                }else{
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
                }
//MOD 2013/07/10 ogawa 【QP@30151】No.13 end    //項目固定チェック

			}else{
				// 【QP@10713】20111031 hagiwara mod start
				//OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" />";
				OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseKotei_" + i + "\" name=\"txtCaseKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_kotehi + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				// 【QP@10713】20111031 hagiwara mod end
			}
			OutputHtml += "        </td>";
		}
		//【H24年度対応】2012/04/16 kazama mod end
//20160513  KPX@1600766 MOD end



	// ADD 2013/11/1 okano【QP@30154】start
	}
	OutputHtml += "    </tr>";

    //利益/ケース
	OutputHtml += "    <tr>";
	for(i = 0; i < cnt_sample; i++){
		//xmlデータ取得
		var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        //XMLデータ取得
		fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
		//XMLデータ取得
		kesu_rieki = funXmlRead_3(xmlData, tableKeisanNm, "kesu_rieki", 0, i);

		//HTML生成
		OutputHtml += "        <td style=\"height:18px;\">";

		// MOD 2013/12/24 QP@30154 okano start
//			if(kojo != "1"){
// 20160513  KPX@1600766 MOD start
		if(cd_kaisha == "1"){
			if(fg_chusi == "1" && txtClass == henshuOkClass){

				if(parent.detail.document.frm00.radioKoteihi[0].checked){
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}

			}else{

				if(parent.detail.document.frm00.radioKoteihi[0].checked){
					if(fg_KoumokuChk == "1"){
//						OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
					}else{
						if(txtClass == henshuOkClass){
//							OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
							OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						}else{
//							OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
							OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						}
					}
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}
			}
		}else{
		// MOD 2013/12/24 QP@30154 okano end
	    	//試算中止
	    	if(fg_chusi == "1" && txtClass == henshuOkClass){

				if(parent.detail.document.frm00.radioKoteihi[0].checked){
	                if(fg_KoumokuChk == "1"){
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                }else{
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                }
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}

			}else{

				if(parent.detail.document.frm00.radioKoteihi[0].checked){
	                if(fg_KoumokuChk == "1"){
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kesu_rieki + "\" readonly tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                }else{
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" onFocus = \"funLineHighLight(this)\" />";
	                }

				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}
			}
//20160513  KPX@1600766 MOD end
		// DEL 2013/12/24 QP@30154 okano start
//			} else{
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtCaseRieki_" + i + "\" name=\"txtCaseRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kesu_rieki + "\" " + txtReadonly + " tabindex=\"23\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
		// DEL 2013/12/24 QP@30154 okano end
		}
		OutputHtml += "        </td>";

	}
	OutputHtml += "    </tr>";
	// ADD 2013/11/1 okano【QP@30154】end



	//【H24年度対応】2012/04/16 kazama add start
//	shisan_fg = shisan_fg2		//試算中止フラグ再生
	//【H24年度対応】2012/04/16 kazama add end

    //原価計/ケース
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        kesu_genkake = funXmlRead_3(xmlData, tableKeisanNm, "kesu_genkake", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"genkakei" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kesu_genkake + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //《参考：個あたり》
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"sankouKo" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //原価計/個
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        ko_genkake = funXmlRead_3(xmlData, tableKeisanNm, "ko_genkake", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"genkakeiKo" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ ko_genkake + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //《参考：KGあたり》
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"sankouKG" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\"\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";


// 20160513  KPX@1600766 ADD start
    if (tankaHyoujiFlg == "1") {
        //原料費/KG 表示
        txtType = "text";
    }
// 20160513  KPX@1600766 ADD end

    //原料費/KG
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        kg_genryohi = funXmlRead_3(xmlData, tableKeisanNm, "kg_genryohi", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genryohi + "\" tabindex=\"-1\" />";
// 20160513  KPX@1600766 MOD start
//        OutputHtml += "            <input type=\"text\" id = \"genryohiKG" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genryohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" id = \"genryohiKG" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genryohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
// 20160513  KPX@1600766 MOD end
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";


// 20160513  KPX@1600766 ADD start
    if (tankaHyoujiFlg == "1") {
        //材料費～利益／KGまで非表示
        txtType = "hidden";
    }
// 20160513  KPX@1600766 ADD end

    //材料費/KG
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        kg_zairyohi = funXmlRead_3(xmlData, tableKeisanNm, "kg_zairyohi", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_zairyohi + "\" tabindex=\"-1\" />";
// 20160513  KPX@1600766 MOD start
//        OutputHtml += "            <input type=\"text\" id = \"zairyohiKG" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_zairyohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        OutputHtml += "            <input type=\"" + txtType + "\" id = \"zairyohiKG" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_zairyohi + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
//20160513  KPX@1600766 MOD end
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //固定費/KG
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
    	//【H24年度対応】2012/04/19 kazama mod start
    	//xmlデータ取得
    	var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
//ADD 2013/07/10 ogawa 【QP@30151】No.13 start    //項目固定チェック
        //XMLデータ取得
    	fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
//ADD 2013/07/10 ogawa 【QP@30151】No.13 end    //項目固定チェック

// 20160513  KPX@1600766 MOD start
		kg_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kg_kotehi", 0, i);

		//【H24年度対応No15】2012/05/08 TT H.SHIMA mod start
    	//試算中止
//    	if(fg_chusi == "1"){
    	if(fg_chusi == "1" && txtClass == henshuOkClass){
    	//【H24年度対応No15】2012/05/08 TT H.SHIMA mod end

			//XMLデータ取得
//    		kg_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kg_kotehi", 0, i);

			//HTML生成
			OutputHtml += "        <td style=\"height:18px;\">";

			if(!parent.detail.document.frm00.radioKoteihi[0].checked){
//MOD 2013/07/10 ogawa 【QP@30151】No.13 start    //項目固定チェック
//修正前ソース
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
//修正後ソース
                if(fg_KoumokuChk == "1"){
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                }else{
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                }
//MOD 2013/07/10 ogawa 【QP@30151】No.13 end    //項目固定チェック
			}else{
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
			}

		}else{
			//XMLデータ取得
//			kg_kotehi = funXmlRead_3(xmlData, tableKeisanNm, "kg_kotehi", 0, i);

			//HTML生成
			OutputHtml += "        <td style=\"height:18px;\">";

	//【QP@10713】2011/10/21 TT H.SHIMA -MOD Start
			// 【QP@10713】20111117 hagiwara mod start
			if(!parent.detail.document.frm00.radioKoteihi[0].checked){
				// 【QP@10713】20111117 hagiwara mod end
//MOD 2013/07/10 ogawa 【QP@30151】No.13 start    //項目固定チェック
//修正前ソース
				//【QP@00342】
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
//修正後ソース
                if(fg_KoumokuChk == "1"){
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_kotehi + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                }else{
//                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
                }
//MOD 2013/07/10 ogawa 【QP@30151】No.13 end    //項目固定チェック
			}else{
//				OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgKotei_" + i + "\" name=\"txtKgKotei_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_kotehi + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
			}
		}
 //20160513  KPX@1600766 MOD end
       //【H24年度対応】2012/04/19 kazama mod end
        OutputHtml += "        </td>";
    //【QP@10713】2011/10/21 TT H.SHIMA -MOD End
    }
    OutputHtml += "    </tr>";



	// ADD 2013/11/1 okano【QP@30154】start
    //利益/KG
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
    	//xmlデータ取得
    	var fg_chusi = funXmlRead_3(xmlData, tableKeisanNm, "fg_chusi", 0, i);
        //XMLデータ取得
    	fg_KoumokuChk = funXmlRead_3(xmlData, tableKeisanNm, "fg_koumokuchk", 0, i);
		//XMLデータ取得
		kg_rieki = funXmlRead_3(xmlData, tableKeisanNm, "kg_rieki", 0, i);


		//HTML生成
		OutputHtml += "        <td style=\"height:18px;\">";
		// MOD 2013/12/24 QP@30154 okano start
//	    	if(kojo != "1"){
		if(cd_kaisha == "1"){
			if(fg_chusi == "1" && txtClass == henshuOkClass){

// 20160513  KPX@1600766 MOD start
				if(!parent.detail.document.frm00.radioKoteihi[0].checked){
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}

			}else{

				if(!parent.detail.document.frm00.radioKoteihi[0].checked){
					if(fg_KoumokuChk == "1"){
//						OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
					}else{
						if(txtClass == henshuOkClass){
//							OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
							OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						}else{
//							OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
							OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" readonly tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
						}
					}
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i + ")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"-1\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}
			}
		}else{
		// MOD 2013/12/24 QP@30154 okano end
	    	//試算中止
	    	if(fg_chusi == "1" && txtClass == henshuOkClass){

				if(!parent.detail.document.frm00.radioKoteihi[0].checked){
	                if(fg_KoumokuChk == "1"){
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                }else{
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                }
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}

			}else{

				if(!parent.detail.document.frm00.radioKoteihi[0].checked){
	                if(fg_KoumokuChk == "1"){
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;background-color:#c0c0c0\" value=\"" + kg_rieki + "\" readonly tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                }else{
//	                    OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                    OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"" + txtClass + "\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" onFocus = \"funLineHighLight(this)\" />";
	                }
				}else{
//					OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
					OutputHtml += "            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
				}
			}
//20160513  KPX@1600766 MOD end
		// DEL 2013/12/24 QP@30154 okano start
//	    	} else {
//	    		OutputHtml += "            <input type=\"text\" onchange=\"setFg_saikeisan_sisan(" + i +")\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtKgRieki_" + i + "\" name=\"txtKgRieki_" + i + "\" class=\"table_text_view\" style=\"text-align:right;ime-mode:disabled;\" value=\"" + kg_rieki + "\" " + txtReadonly + " tabindex=\"24\" readOnly = \"readonly\" onFocus = \"funLineHighLight(this)\" />";
		// DEL 2013/12/24 QP@30154 okano end
    	}
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";
	// ADD 2013/11/1 okano【QP@30154】end



    //原価計/KG
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        kg_genkake = funXmlRead_3(xmlData, tableKeisanNm, "kg_genkake", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" id=\"txtKgGenkake_" + i + "\" name=\"txtKgGenkake_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genkake + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id=\"txtKgGenkake_" + i + "\" name=\"txtKgGenkake_" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ kg_genkake + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //売価
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        baika = funXmlRead_3(xmlData, tableKeisanNm, "baika", 0, i);

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"baika" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ baika + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";



    //粗利（％）
    OutputHtml += "    <tr>";
    for(i = 0; i < cnt_sample; i++){
        //XMLデータ取得
        arari = funXmlRead_3(xmlData, tableKeisanNm, "arari", 0, i);
        if(arari == ""){
        }
        else{
        	arari = arari + "　％";
        }

        //HTML生成
        OutputHtml += "        <td  style=\"height:18px;\">";
        // 【QP@10713】20111101 hagiwara mod start
        //OutputHtml += "            <input type=\"text\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ arari + "\" tabindex=\"-1\" />";
        OutputHtml += "            <input type=\"text\" id = \"sori" + i + "\" class=\"table_text_view\" readonly style=\"text-align:right;\" value=\""+ arari + "\" tabindex=\"-1\" onFocus = \"funLineHighLight(this)\" />";
        // 【QP@10713】20111101 hagiwara mod end
        OutputHtml += "        </td>";
    }
    OutputHtml += "    </tr>";

    OutputHtml += "</table>";


    //------------------------------------------------------------------------------------
    //                                  HTML出力
    //------------------------------------------------------------------------------------
    //HTMLを出力
    obj.innerHTML = OutputHtml;

    OutputHtml = null;

	//【シサクイックH24年度対応】修正５ 2012/04/13 Start
    if(saiyo_column > 0){
    	funSaiyoDisp(saiyo_column,arrShisaku_seq,cnt_sample);
    }
	//【シサクイックH24年度対応】修正５ 2012/04/13 End

    return true;
}


//========================================================================================
// 資材テーブル情報表示
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
// 引数  ：①xmlUser ：更新情報格納XML名
//       ：②ObjectId：設定オブジェクトID
// 戻り値：なし
// 概要  ：資材テーブル表示用のHTML文を生成、出力する。
//========================================================================================
function funShizaiDisplay(xmlData, ObjectId) {

    var headerFrm = parent.header.document.frm00; //ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var detailFrm = parent.detail.document.frm00; //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    var Kengen = headerFrm.hdnKengen.value;       //権限：機能ID
    var obj;              //設定先オブジェクト
    var tablekihonNm;     //読み込みテーブル名
    var tableShizaiNm;    //読み込みテーブル名
    var OutputHtml;       //出力HTML
    var chkDisabled;      //チェックボックス入力設定
    var txtReadonly;      //テキストボックス入力設定
    var txtClass;         //テキストボックス背景
    var objColor;         //オブジェクト背景色

    var cnt_genryo;       //行数
    var goke_shizai;      //資材金額合計
    var seq_shizai;       //資材SEQ
    var cd_kaisya;        //会社CD
    var cd_kojyo;         //工場CD
    var kigo_kojyo;       //工場記号
    var cd_shizai;        //資材CD
    var nm_shizai;        //資材名
    var tanka;            //単価
    var budomari;         //歩留（％）
    var shiyouryo;        //使用量/ｹｰｽ
    var kei_kingaku;      //金額計
    var id_toroku;        //登録者ID
    var dt_toroku;        //登録日

    var i;                //ループカウント

    //【QP@00342】ステータス制御
	var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
	var st_seikan = headerFrm.hdnStatus_seikan.value;
	var st_gentyo = headerFrm.hdnStatus_gentyo.value;
	var st_kojo    = headerFrm.hdnStatus_kojo.value;
	var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

	//【QP@00342】部署取得
	var kenkyu = headerFrm.hdnBusho_kenkyu.value;
	var seikan = headerFrm.hdnBusho_seikan.value;
	var gentyo = headerFrm.hdnBusho_gentyo.value;
	var kojo = headerFrm.hdnBusho_kojo.value;
	var eigyo = headerFrm.hdnBusho_eigyo.value;
// 20160513  KPX@1600766 ADD start
    var txtType = "text";      //テキストボックスタイプ
// 20160513  KPX@1600766 ADD end

    //【QP@00342】排他
    if(headerFrm.strKengenMoto.value == "999"){
    	chkDisabled = "disabled";
        txtReadonly = "readonly";
        txtClass = henshuNgClass;
        objColor = henshuNgColor;
    }
    else{
    	//【QP@00342】編集権限
	    if(Kengen.toString() == ConFuncIdEdit.toString()){
	        chkDisabled = "";
	        txtReadonly = "";
	        txtClass = henshuOkClass;
	        objColor = henshuOkColor;

	        //【QP@00342】原資材調達部で確認完了の場合
			/*
			 * if( gentyo == "1" && st_gentyo == 2){ chkDisabled = "disabled";
			 * txtReadonly = "readonly"; txtClass = henshuNgClass; objColor =
			 * henshuNgColor; }
			 */

	        //【QP@00342】工場で確認完了の場合
			/*
			 * if( kojo == "1" && st_kojo == 2){ chkDisabled = "disabled";
			 * txtReadonly = "readonly"; txtClass = henshuNgClass; objColor =
			 * henshuNgColor; }
			 */

	        //20160607 【KPX@1502111_No8】 ADD start
	        // 部署：工場で確認完了(工場ステータス≧2)の場合
	        // 部署：生管は生産管理部ステータス ≧ 3
			if( kojo == "1" && st_kojo >= 2 ){
				chkDisabled = "disabled";
		        txtReadonly = "readonly";
		        txtClass = henshuNgClass;
		        objColor = henshuNgColor;
			}
	        //20160607 【KPX@1502111_No8】 ADD end

	        //【QP@00342】生産管理部ステータス >= 3 or 営業ステータス >= 4　の場合
			if( st_seikan >= 3 || st_eigyo >= 4){
				chkDisabled = "disabled";
		        txtReadonly = "readonly";
		        txtClass = henshuNgClass;
		        objColor = henshuNgColor;
			}

	    }
	    //【QP@00342】閲覧+Excel権限
	    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
	        chkDisabled = "disabled";
	        txtReadonly = "readonly";
	        txtClass = henshuNgClass;
	        objColor = henshuNgColor;
	    }
    }

 // 20160513  KPX@1600766 ADD start
    //研究所：単価表示すべて以外は編集不可
    if (tankaHyoujiFlg == "1" || tankaHyoujiFlg == "0") {
        chkDisabled = "disabled";
        txtReadonly = "readonly";
        txtClass = henshuNgClass;
        objColor = henshuNgColor;
        //単価・歩留他 非表示
        txtType = "hidden";
    }
//20160513  KPX@1600766 ADD end

    //HTML出力オブジェクト設定
    obj = detailDoc.getElementById(ObjectId);
    OutputHtml = "";

    //テーブル名設定
    tablekihonNm = "kihon";
    tableShizaiNm = "shizai";

    //行数取得
    cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_shizai", 0, 0);
    detailFrm.hdnShizaiCount.value = cnt_genryo;

    goke_shizai = funXmlRead_3(xmlData, tablekihonNm, "goke_shizai", 0, 0);
// 20160513  KPX@1600766 ADD start
	if (txtType == "hidden") {
		goke_shizai = "&nbsp;";
	}
//20160513  KPX@1600766 ADD end

	//資材計表示
    OutputHtml += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"1\" bordercolor=\"#888888\" width=\"975\" height=\"16px\">";
    OutputHtml += "    <tr><td width=\"800\">資材計</td>";
    OutputHtml += "    <td align=\"right\"><label name=\"lblShizaikei\" id=\"lblShizaikei\">" + goke_shizai + "</label></td></tr>";
    OutputHtml += "</table>";


    //資材行数設定
    OutputHtml += "<input type=\"hidden\" id=\"cnt_shizai\" name=\"cnt_shizai\" value=\"" + cnt_genryo + "\">";

    //出力HTML設定
    OutputHtml += "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"970px\" height=\"400px\">";
    OutputHtml += "<tr>";
    OutputHtml += "    <td colspan=\"2\" valign=\"top\">";
    OutputHtml += "        <div class=\"scroll_genka\" id=\"sclList4\" style=\"width:975px;height:97%;overflow-x:hidden;\" rowSelect=\"true\" />";
    OutputHtml += "        <table id=\"dataTable4\" name=\"dataTable4\" cellspacing=\"0\" width=\"955px;\" align=\"center\">";
    OutputHtml += "            <colgroup>";
    OutputHtml += "                <col style=\"width:50px;\"/>";
    OutputHtml += "                <col style=\"width:50px;\"/>";
    OutputHtml += "                <col style=\"width:100px;\"/>";
    OutputHtml += "                <col style=\"width:355px;\"/>";
    OutputHtml += "                <col style=\"width:100px;\"/>";
    OutputHtml += "                <col style=\"width:80px;\"/>";
    OutputHtml += "                <col style=\"width:120px;\"/>";
    OutputHtml += "                <col style=\"width:100px;\"/>";
    OutputHtml += "            </colgroup>";
    OutputHtml += "            <thead class=\"rowtitle\">";
    OutputHtml += "                <tr style=\"top:expression(offsetParent.scrollTop);position:relative;\">";
    OutputHtml += "                    <th class=\"columntitle\">選択</th>";
    OutputHtml += "                    <th class=\"columntitle\">工場</th>";
    OutputHtml += "                    <th class=\"columntitle\">資材コード</th>";
    OutputHtml += "                    <th class=\"columntitle\">資材名</th>";
    OutputHtml += "                    <th class=\"columntitle\">単価※</th>";
    OutputHtml += "                    <th class=\"columntitle\">歩留(%)※</th>";
    OutputHtml += "                    <th class=\"columntitle\">使用量/ｹｰｽ※</th>";
    OutputHtml += "                    <th class=\"columntitle\">金額</th>";
    OutputHtml += "                </tr>";
    OutputHtml += "            </thead>";
    OutputHtml += "            <tbody>";
    OutputHtml += "                <table class=\"detail\" id=\"tblList4\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\" style=\"width:956px;display:list-item\">";

    //資材情報表示ループ
    for( i = 0; i < cnt_genryo; i++ ){

        //XMLデータ取得
        seq_shizai = funXmlRead_3(xmlData, tableShizaiNm, "seq_shizai", 0, i);
        cd_kaisya = funXmlRead_3(xmlData, tableShizaiNm, "cd_kaisya", 0, i);
        cd_kojyo = funXmlRead_3(xmlData, tableShizaiNm, "cd_kojyo", 0, i);
        kigo_kojyo = funXmlRead_3(xmlData, tableShizaiNm, "kigo_kojyo", 0, i);
        cd_shizai = funXmlRead_3(xmlData, tableShizaiNm, "cd_shizai", 0, i);
        nm_shizai = funXmlRead_3(xmlData, tableShizaiNm, "nm_shizai", 0, i);
        tanka = funXmlRead_3(xmlData, tableShizaiNm, "tanka", 0, i);
        budomari = funXmlRead_3(xmlData, tableShizaiNm, "budomari", 0, i);
        shiyouryo = funXmlRead_3(xmlData, tableShizaiNm, "shiyouryo", 0, i);
        kei_kingaku = funXmlRead_3(xmlData, tableShizaiNm, "kei_kingaku", 0, i);
// 20160513  KPX@1600766 ADD start
        if (txtType == "hidden") {
            kei_kingaku =  "&nbsp;";
        }
//20160513  KPX@1600766 ADD end

        id_toroku = funXmlRead_3(xmlData, tableShizaiNm, "id_toroku", 0, i);
        dt_toroku = funXmlRead_3(xmlData, tableShizaiNm, "dt_toroku", 0, i);


        OutputHtml += "                    <tr class=\"disprow\">";

        //選択
        OutputHtml += "                        <td bgcolor=\"" + objColor + "\" class=\"column\" width=\"50px\" align=\"center\">";
        OutputHtml += "                            <input name=\"chkShizaiGyo\" type=\"checkbox\" " + chkDisabled + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";
        //工場記号
        OutputHtml += "                        <td class=\"column\" width=\"50px\" align=\"center\">";
        OutputHtml += "                            <input type=\"text\"    id=\"txtKigouKojo\"     name=\"txtKigouKojo\" class=\"table_text_view\" readonly style=\"text-align:center;\" value=\""+ kigo_kojyo + "\" tabindex=\"-1\" />";
        //会社コード
        OutputHtml += "                            <input type=\"hidden\"  id=\"hdnKaisha_Shizai\" name=\"hdnKaisha_Shizai\" value=\"" + cd_kaisya + "\" />";
        //工場コード
        OutputHtml += "                            <input type=\"hidden\"  id=\"hdnKojo_Shizai\"   name=\"hdnKojo_Shizai\" value=\"" + cd_kojyo + "\" />";
        //登録者ID
        OutputHtml += "                            <input type=\"hidden\"  id=\"hdnId_toroku\"   name=\"hdnId_toroku\" value=\"" + id_toroku + "\" />";
        //登録日
        OutputHtml += "                            <input type=\"hidden\"  id=\"hdnDt_toroku\"   name=\"hdnDt_toroku\" value=\"" + dt_toroku + "\" />";
        OutputHtml += "                        </td>";

        //資材コード
        OutputHtml += "                        <td class=\"column\" width=\"100px\" align=\"center\">";
        //【QP@00342】資材テーブル修正
        //OutputHtml += "                            <input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funInsertCdZero(this);funShizaiSearch()\" id=\"txtCdShizai\" name=\"txtCdShizai\" class=\"" + txtClass + "\" style=\"text-align:center\" value=\"" + cd_shizai + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                            <input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funInsertCdZero(this);funChangeSelectRowColor3(this);funShizaiSearch();\" id=\"txtCdShizai\" name=\"txtCdShizai\" class=\"" + txtClass + "\" style=\"text-align:center\" value=\"" + cd_shizai + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";

        //資材名
        OutputHtml += "                        <td class=\"column\" width=\"353px\" align=\"left\">";
        OutputHtml += "                            <input type=\"text\" style=\"ime-mode:active;text-align:left\" id=\"txtNmShizai\" name=\"txtNmShizai\" class=\"" + txtClass + "\" value=\"" + nm_shizai + "\"  " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";

// 20160513  KPX@1600766 MOD start
        //単価
        OutputHtml += "                        <td class=\"column\" width=\"100px\" align=\"right\">";
//        OutputHtml += "                            <input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtTankaShizai\" name=\"txtTankaShizai\" class=\"" + txtClass + "\" style=\"text-align:right\" value=\"" + tanka + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtTankaShizai\" name=\"txtTankaShizai\" class=\"" + txtClass + "\" style=\"text-align:right\" value=\"" + tanka + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";
        //歩留
        OutputHtml += "                        <td class=\"column\" width=\"80px\" align=\"right\">";
//        OutputHtml += "                            <input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomariShizai\" name=\"txtBudomariShizai\" class=\"" + txtClass + "\" style=\"text-align:right\" value=\"" + budomari + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,2);setKanma(this)\" id=\"txtBudomariShizai\" name=\"txtBudomariShizai\" class=\"" + txtClass + "\" style=\"text-align:right\" value=\"" + budomari + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";
        //使用量/ケース
        OutputHtml += "                        <td class=\"column\" width=\"120px\">";
//        OutputHtml += "                            <input type=\"text\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtSiyouShizai\" name=\"txtSiyouShizai\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + shiyouryo + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                            <input type=\"" + txtType + "\" onchange=\"setFg_saikeisan();\" onblur=\"funShosuUp(this,6);setKanma(this)\" id=\"txtSiyouShizai\" name=\"txtSiyouShizai\" class=\"" + txtClass + "\" style=\"text-align:right;\" value=\"" + shiyouryo + "\" " + txtReadonly + " tabindex=\"27\" />";
        OutputHtml += "                        </td>";
// 20160513  KPX@1600766 MOD end
        //金額
        OutputHtml += "                        <td class=\"column\" width=\"100px\" align=\"right\">" + kei_kingaku + "</td>";

        OutputHtml += "                    </tr>";
    }

    OutputHtml += "                </table>";
    OutputHtml += "            </tbody>";
    OutputHtml += "        </table>";
    OutputHtml += "    </td>";
    OutputHtml += "</tr>";
    OutputHtml += "</table>";

    //HTMLを出力
    obj.innerHTML = OutputHtml;

    OutputHtml = null;

    return true;
}

//========================================================================================
// ステータスクリア画面用の項目を変数に保持
// 作成者：Hisahori
// 作成日：2012/06/15
// 引数  ：なし
// 戻り値：なし
// 概要  ：hidden項目に、サンプルNOのキー、サンプルNO、試算日、試算中止フラグをセットする
//========================================================================================
function funSetDtShisan() {
	var frm = document.frm00;				//フォーム参照
    var detailDoc = parent.detail.document;
	var cntroop = frm.cnt_sample.value;
	var setSeqShisaku = "";
	var setSampleNo = "";
	var setShisanHi = "";
	var setShisanChushi = "";
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start
	var setChkKoumoku = "";					//項目固定チェック
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end

	for(var m = 0; m < cntroop; m++){
		if (m == 0){
			setSeqShisaku = setSeqShisaku + detailDoc.getElementById("hdnSeq_Shisaku_" + m).value;
		} else {
			setSeqShisaku = setSeqShisaku + "," + detailDoc.getElementById("hdnSeq_Shisaku_" + m).value;
		}
	}
	for(var i = 0; i < cntroop; i++){
		if (i == 0){
			setSampleNo = setSampleNo + detailDoc.getElementById("txtSample" + i).value;
		} else {
			setSampleNo = setSampleNo + "," + detailDoc.getElementById("txtSample" + i).value;
		}
	}
	for(var j = 0; j < cntroop; j++){
		if (j == 0){
			setShisanHi = setShisanHi + detailDoc.getElementById("txtShisanHi_" + j).value;
		} else {
			setShisanHi = setShisanHi + "," + detailDoc.getElementById("txtShisanHi_" + j).value;
		}
	}
	for(var k = 0; k < cntroop; k++){
		if (k == 0){
			setShisanChushi = setShisanChushi + detailDoc.getElementById("hdnSisanChusi_" + k).value;
		} else {
			setShisanChushi = setShisanChushi + "," + detailDoc.getElementById("hdnSisanChusi_" + k).value;
		}
	}
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start
	//項目固定チェック
	for(var k = 0; k < cntroop; k++){
		if (k == 0){
			setChkKoumoku = setChkKoumoku + detailDoc.getElementById("chkKoumoku_" + k).value;
		} else {
			setChkKoumoku = setChkKoumoku + "," + detailDoc.getElementById("chkKoumoku_" + k).value;
		}
	}
	frm.hidsetChkKoumoku.value = setChkKoumoku;
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end

	frm.hidsetSeqShisaku.value = setSeqShisaku;
	frm.hidsetSampleNo.value = setSampleNo;
	frm.hidsetShisanHi.value = setShisanHi;
	frm.hidsetShisanChushi.value = setShisanChushi;
	return true;
}


//========================================================================================
// 登録ボタン押下処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/28
// 引数  ：なし
// 概要  ：原価、資材情報の登録を行う
//========================================================================================
function funToroku() {

	var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    var retVal;

    //【QP@00342】承認ボタンにフォーカスセット
    parent.header.focus();

	//【QP@00342】部署取得
    var kenkyu = headerFrm.hdnBusho_kenkyu.value;
    var seikan = headerFrm.hdnBusho_seikan.value;
    var gentyo = headerFrm.hdnBusho_gentyo.value;
    var kojo = headerFrm.hdnBusho_kojo.value;
    var eigyo = headerFrm.hdnBusho_eigyo.value;

    //【QP@00342】権限取得
    var Kengen = headerFrm.hdnKengen.value;

    //【QP@00342】編集権限
	if(Kengen.toString() == ConFuncIdEdit.toString()){
		//【QP@00342】ステータス変更ダイアログ表示
	    args = new Array();
    	args[0] = headerDoc;
		args[1] = detailDoc;

	    //【QP@00342】ステータス変更画面を起動する
		// 正常終了の場合、選択ﾗｼﾞｵﾎﾞﾀﾝ値が戻る
	    retVal = funOpenModalDialog("../SQ110GenkaShisan/GenkaShisan_Status.jsp", args, "dialogHeight:350px;dialogWidth:500px;status:no;scroll:no");

	    //【QP@00342】終了ボタン押下
	    if(retVal == "false"){
	    	//仮保存ステータスを設定
	    	headerFrm.hdnInsStatus.value = "0";

	    	return true;
	    }
	    //【QP@00342】ステータスクリア
	    else if(retVal == "Clear"){

	    	//再表示
	        funGetKyotuInfo(1);

	    }
		//【QP@00342】ステータスが選択されている場合
	    else{
	    	//選択ステータスを退避
		    headerFrm.hdnInsStatus.value = retVal;

//20160617  KPX@502111_No.5 ADD start
		    // 工場ステータス：完了時
		    if (kojo == 1 && retVal == 2) {
		    	// 連携先自家原料の営業ステータス確認完了前の時
		    	//（ステータス値をセット、＞営業3の時 ""）
		    	if (headerFrm.hdnRenkeiStatus_eigyo.value > 0) {
		            //エラー表示
		            funErrorMsgBox(E000042);
		            return false;
		    	}
		    }
		    // 生管ステータス：完了時
		    if (seikan == 1 && retVal == 2) {
		    	// 連携先自家原料の営業ステータス確認完了前の時
		    	if (headerFrm.hdnRenkeiStatus_eigyo.value > 0) {
		    		//エラー表示
		            funErrorMsgBox(E000042);
		            return false;
		    	}
		    }
//20160617  KPX@502111_No.5 ADD end

		    //登録処理
	    	// MOD 2015/06/12 TT.Kitazawa【QP@40812】No.6 start
		    // 選択ステータスを渡す
//		    if(funTorokuInfo(1)){
		    if(funTorokuInfo(1, retVal)){
	    	// MOD 2015/06/12 TT.Kitazawa【QP@40812】No.6 end

		    	//登録成功時に再表示を行う
		    	funGetKyotuInfo(1);

		    }
	    }
	}
	//【QP@00342】閲覧+Excel権限
	else if(Kengen.toString() == ConFuncIdReadExcel.toString()){

		//登録処理
    	// MOD 2015/06/12 TT.Kitazawa【QP@40812】No.6 start
//	    funTorokuInfo(1);
	    funTorokuInfo(1, 0);
    	// MOD 2015/06/12 TT.Kitazawa【QP@40812】No.6 end
	}

    return true;

}

//========================================================================================
// 登録処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/28
// 引数  ：①mode  ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
//         ②val   ：選択番号
// 概要  ：原価、資材情報の登録を行う
//========================================================================================
function funTorokuInfo(mode, val) {

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN0041";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0040");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0040I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0040O );

    //------------------------------------------------------------------------------------
    //                               計算内容の変更確認
    //------------------------------------------------------------------------------------
    //再計算が行われていない場合
    if(frm.FgSaikeisan.value == "true"){
        //エラーメッセージを表示
        funErrorMsgBox(E000006);
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                      登録
    //------------------------------------------------------------------------------------
    //XMLの初期化
    setTimeout("xmlFGEN0040I.src = '../../model/FGEN0040I.xml';", ConTimer);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0041, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                     結果表示
    //------------------------------------------------------------------------------------
    //完了ﾒｯｾｰｼﾞの表示
    dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
    	// MOD 2015/06/12 TT.Kitazawa【QP@40812】No.6 start
        if(val > 0){
        	// メール起動確認メッセージに変更：「YES」の時メール起動
        	// メール起動前に、更新後ステータスを取得する
    		if(funConfMsgBox(dspMsg) == ConBtnYes){
    			funMail();
    		}
    	} else {
    		//正常
    		funInfoMsgBox(dspMsg);
    	}
        // MOD 2015/06/12 TT.Kitazawa【QP@40812】No.6 end
        //処理終了
	    return true;
    }
    else{
    	return false;
    }
}

// ADD start 20140919
// ========================================================================================
// 登録処理
// 作成者：hisahori
// 作成日：2014/09/19
// 引数 ：
// 概要 ：項目固定チェック対象の値を登録
// ========================================================================================
function funTorokuKoumokuKotei(mode) {
    // ------------------------------------------------------------------------------------
    // 変数宣言
    // ------------------------------------------------------------------------------------
    var frm = document.frm00;    // ﾌｫｰﾑへの参照
    var XmlId = "RGEN2021";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2021");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2021I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2021O );

    // ------------------------------------------------------------------------------------
    // 登録
    // ------------------------------------------------------------------------------------
    // XMLの初期化
    setTimeout("xmlFGEN2021I.src = '../../model/FGEN2021I.xml';", ConTimer);

    // 引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    // ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2021, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    // ------------------------------------------------------------------------------------
    // 結果表示
    // ------------------------------------------------------------------------------------
    // 完了ﾒｯｾｰｼﾞの表示
//   dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
//   if (funXmlRead(xmlResAry[0], "flg_return", 0) == "true") {
//   //正常
//   funInfoMsgBox(dspMsg);
//   //処理終了
//   return true;
//   }
//   else{
//   return false;
//   }

    return true;

}
// ADD end 20140919

//ADD start 20150722
//========================================================================================
//登録処理
//作成者：kitazawa
//作成日：2015/07/22
//引数 ：
//概要 ：項目固定チェックONで固定データが存在しないデータをチェック
//       項目固定チェックをOFFにする ⇒ 再計算が実行される
//========================================================================================
function funKoteiChk(mode) {
 // ------------------------------------------------------------------------------------
 // 変数宣言
 // ------------------------------------------------------------------------------------
 var frm = document.frm00;    // ﾌｫｰﾑへの参照
 var XmlId = "RGEN2022";
 var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2022");
 var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2022I );
 var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2022O );

 // ------------------------------------------------------------------------------------
 // 登録
 // ------------------------------------------------------------------------------------
 // XMLの初期化
 setTimeout("xmlFGEN2022I.src = '../../model/FGEN2022I.xml';", ConTimer);

 // 引数をXMLﾌｧｲﾙに設定
 if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
     funClearRunMessage2();
     return -1;
 }

 // ｾｯｼｮﾝ情報、共通情報を取得
 if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2022, xmlReqAry, xmlResAry, mode) == false) {
     return -1;
 }

 var msg_cd = funXmlRead(xmlResAry[2], "msg_cd", 0);
 return msg_cd;

}
//ADD end 20150722

//========================================================================================
// マスタ単価・歩留ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/10/29
// 概要  ：マスタの単価、歩留値を取得する
//========================================================================================
function funBckMst(){

    //再計算フラグをonにする
    setFg_saikeisan();

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var detailDoc = parent.detail.document;
    var headerFrm = parent.header.document.frm00; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var XmlId = "RGEN0070";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0070");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0070I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0070O );
    var mode = 1;


    //------------------------------------------------------------------------------------
    //                              マスタ単価・歩留洗い戻し
    //------------------------------------------------------------------------------------
    //XMLの初期化
    setTimeout("xmlFGEN0070I.src = '../../model/FGEN0070I.xml';", ConTimer);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0070, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }


    //------------------------------------------------------------------------------------
    //                                     原料表示
    //------------------------------------------------------------------------------------
    //結果判定
    if (funXmlRead(xmlResAry[0], "flg_return", 0) == "false") {
        //ｴﾗｰ発生時はﾒｯｾｰｼﾞを表示
        dspMsg = funXmlRead(xmlResAry[0], "msg_error", 0);
        funInfoMsgBox(dspMsg);
        return false;
    }

    //原料表示
    var reccnt = funGetLength_3(xmlResAry[2], "genryo", 0); //件数取得
    for(var i = 0; i < reccnt; i++){
        var seq_gyo = funXmlRead(xmlResAry[2], "seq_gyo", i);
        detailDoc.getElementById("txtTanka_"+seq_gyo).value = funXmlRead(xmlResAry[2], "tanka", i);
        detailDoc.getElementById("txtBudomari_"+seq_gyo).value = funXmlRead(xmlResAry[2], "budomari", i);
        detailDoc.getElementById("txtHenkouRen_"+seq_gyo).value = funXmlRead(xmlResAry[2], "henko_renraku", i);
        //【QP@10713】 2011/12/08 TT H.Shima DEL Start
		//cnt = seq_gyo - 1;
        //detailDoc.getElementById("txtTanka_"+seq_gyo).value = maTanka[cnt];
        //detailDoc.getElementById("txtBudomari_"+seq_gyo).value = maBudomari[cnt];
		//【QP@10713】 2011/12/08 TT H.Shima DEL End

//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/5/19 START -----------------------------------
        detailDoc.getElementById("hdnKojoNmTanka_"+seq_gyo).value = funXmlRead(xmlResAry[2], "tanka_kojoNm", i);
        detailDoc.getElementById("hdnKojoNmBudomari_"+seq_gyo).value = funXmlRead(xmlResAry[2], "budomari_kojoNm", i);

        //サブウィンドウが表示されているか
        if(subwinShowBln){
            //サブウィンドウの[行番号]取得
            subwinGyoNo = $("GyoNo");

            //単価変更行と表示しているサブウィンドウの行番号が一致した場合
            if(seq_gyo == subwinGyoNo.value){

                //サブウィンドウの[単価　工場名]を設定
                subwinTankaKojoNm = $("TankaKojoNm");
                subwinTankaKojoNm.value = funXmlRead(xmlResAry[2], "tanka_kojoNm", i);;

                //サブウィンドウの[歩留　工場名]を設定
                subwinTankaKojoNm = $("BudomariKojoNm");
                subwinTankaKojoNm.value = funXmlRead(xmlResAry[2], "budomari_kojoNm", i);;

                //ウィンドウのリフレッシュ
                win.refresh();
             }
        }

//【シサクイック（原価）要望】単価・歩留　工場名取得　TT.Nishigawa 2010/5/19 END   -----------------------------------

        //原料のチェックを外す
        detailDoc.getElementById("chkGenryo_" + seq_gyo).checked = false;

    }

    return true;
}

//========================================================================================
// 再計算ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/10/29
// 概要  ：再計算を行う
//========================================================================================
function funSaikeisan(){

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var headerFrm = parent.header.document.frm00; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var XmlId = "RGEN0030";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0030");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0030I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0030O );
    var mode = 1;

    //承認ボタンにフォーカスセット
    parent.header.focus();

    //------------------------------------------------------------------------------------
    //                               再計算フラグ初期化
    //------------------------------------------------------------------------------------
    //再計算フラグをoffにする
    headerFrm.FgSaikeisan.value = "false";


    //------------------------------------------------------------------------------------
    //                                     再計算
    //------------------------------------------------------------------------------------
    //XMLの初期化
    setTimeout("xmlFGEN0030I.src = '../../model/FGEN0030I.xml';", ConTimer);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0030, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                     結果表示
    //------------------------------------------------------------------------------------
    //原料テーブル左側
    //funGenryo_LeftDisplay(xmlResAry[2], "divGenryo_Left");
    //原料テーブル右側
    funGenryo_RightDisplay(xmlResAry[2], "divGenryo_Right");

    //【シサクイックH24年度対応】No46 2012/04/20 ADD Start
    //原料テーブル項目非表示
    funGenryo_DispDecision(xmlResAry[2]);
    //【シサクイックH24年度対応】No46 2012/04/20 ADD End

    //試算情報表示
    funGenryoShisanDisplay(xmlResAry[2], "divGenryoShisan");
    //資材テーブル表示
    funShizaiDisplay(xmlResAry[2],"divGenryoShizai");
//    funSetDtShisan();
    //変更連絡表示
    funGenryo_HenkouDisplay(xmlResAry[2]);

    //再計算処理結果表示
    var Errmsg = funXmlRead_3(xmlResAry[2], "kihon", "err_msg", 0, 0);
    if(Errmsg == ""){
        //終了メッセージを表示
        funInfoMsgBox(S000002);
    }
    else{
        //エラーメッセージを表示
        funInfoMsgBox("再計算実行結果：\n"+Errmsg);

    }

    //処理終了
    return true;

}

//========================================================================================
// 印刷ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/10/29
// 概要  ：印刷を行う
//========================================================================================
function funInsatu(){

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var headerFrm = parent.header.document.frm00; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var XmlId = "RGEN0051";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0050");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0050I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0050O );
    var mode = 1;

    //【QP@00342】承認ボタンにフォーカスセット
    parent.header.focus();

    //------------------------------------------------------------------------------------
    //                               入力項目の変更確認
    //------------------------------------------------------------------------------------
    if(funInputCheck(mode) == "true"){
        //処理なし

    }else{
        //メッセージ表示
        if(funXmlRead(xmlFGEN0020O, "hantei", 0) == "false"){
            funInfoMsgBox(E000009);
        }
        return false;
    }


    //------------------------------------------------------------------------------------
    //                                     印刷
    //------------------------------------------------------------------------------------
    //XMLの初期化
    setTimeout("xmlFGEN0050I.src = '../../model/FGEN0050I.xml';", ConTimer);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0051, xmlReqAry, xmlResAry, mode) == false) {
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
        return false;
    }

    //ﾌｧｲﾙﾊﾟｽの退避
    frm.strFilePath.value = funXmlRead(xmlFGEN0050O, "URLValue", 0);

    //ﾀﾞｳﾝﾛｰﾄﾞ画面の起動
    funFileDownloadUrlConnect(ConConectGet, frm);

    //処理終了
    return true;

}

//========================================================================================
// 入力項目の変更確認
// 作成者：Y.Nishigawa
// 作成日：2009/10/29
// 概要  ：入力項目の変更確認を行う
//========================================================================================
function funInputCheck(mode){

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var headerFrm = parent.header.document.frm00; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var XmlId = "RGEN0050";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0020");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0020I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0020O );

    //------------------------------------------------------------------------------------
    //                                入力項目の変更確認
    //------------------------------------------------------------------------------------
    //XMLの初期化
    setTimeout("xmlFGEN0020I.src = '../../model/FGEN0020I.xml';", ConTimer);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0050, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                     結果返却
    //------------------------------------------------------------------------------------
    //結果を返却
    return funXmlRead(xmlResAry[2], "hantei", 0);


}

//========================================================================================
// 資材検索
// 作成者：Y.Nishigawa
// 作成日：2009/10/29
// 概要  ：試作コードより資材の検索を行う
//========================================================================================
function funShizaiSearch(){

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var headerFrm = parent.header.document.frm00; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var XmlId = "RGEN0080";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN0080");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN0080I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN0080O );
    var mode = 1;

    //【QP@00342】資材テーブル修正
    //資材コードが未入力でない場合　且つ　disabledでない場合
    if(frm.txtCdShizai[CurrentRow].value != "" && frm.txtCdShizai[CurrentRow].readOnly != true){

        //------------------------------------------------------------------------------------
        //                                    資材検索
        //------------------------------------------------------------------------------------
        //引数をXMLﾌｧｲﾙに設定
        if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
            funClearRunMessage2();
            return false;
        }

        //ｾｯｼｮﾝ情報、共通情報を取得
        if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0080, xmlReqAry, xmlResAry, mode) == false) {
            return false;
        }

        //------------------------------------------------------------------------------------
        //                                     結果表示
        //------------------------------------------------------------------------------------
        //結果判定
        dspMsg = funXmlRead(xmlResAry[2], "msg_error", 0);
        if (dspMsg != "") {
            //ｴﾗｰ発生時はﾒｯｾｰｼﾞを表示
            funInfoMsgBox(dspMsg);
        }

        //会社CD
        frm.hdnKaisha_Shizai[CurrentRow].value = funXmlRead(xmlResAry[2], "cd_kaisya", 0);
        //工場CD
        frm.hdnKojo_Shizai[CurrentRow].value = funXmlRead(xmlResAry[2], "cd_kojyo", 0);
        //工場記号
        frm.txtKigouKojo[CurrentRow].value = funXmlRead(xmlResAry[2], "kigo_kojyo", 0);
        //資材名
        frm.txtNmShizai[CurrentRow].value = funXmlRead(xmlResAry[2], "nm_shizai", 0);
        //単価
        frm.txtTankaShizai[CurrentRow].value = funXmlRead(xmlResAry[2], "tanka", 0);
        //歩留
        frm.txtBudomariShizai[CurrentRow].value = funXmlRead(xmlResAry[2], "budomari", 0);


    }

    return true;
}

//========================================================================================
// 再計算フラグ変更
// 作成者：Y.Nishigawa
// 作成日：2009/10/29
// 概要  ：再計算フラグをonにする
//========================================================================================
function setFg_saikeisan(){

    //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var headerFrm = parent.header.document.frm00;

    //再計算フラグをonにする
    headerFrm.FgSaikeisan.value = "true";

    return true;

}
// 【QP@00342】試算中止
function setFg_saikeisan_sisan(index){

    //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var headerFrm = parent.header.document.frm00;
    var detailDoc = parent.detail.document;		//明細ﾌﾚｰﾑのDocument参照

    var fg = detailDoc.getElementById("hdnSisanChusi_"+index).value;

    //再計算フラグをonにする
    if(fg == "1"){
    }
    else{
    	headerFrm.FgSaikeisan.value = "true";
    }


    return true;

}

//========================================================================================
// [試作原価情報]テーブルの[単価]変更時イベント
// 作成者：E.Nakamura
// 作成日：2010/02/18
// 概要  ：テーブル内保持情報[単価　工場名]の値をクリアし、再計算フラグをonにする
//========================================================================================
function ShisakuGenka_Tankachanged(ChangeGyoNo){

	var detailDoc = parent.detail.document;		//明細ﾌﾚｰﾑのDocument参照
	var KojoNmTanka;							//[単価　工場名]保持列制御用
	var subwinGyoNo;							//サブウィンドウの[行番号]制御用
	var subwinTankaKojoNm;						//サブウィンドウの[単価　工場名]制御用

	//単価変更行の[単価　工場名]列制御参照
	KojoNmTanka = detailDoc.getElementById("hdnKojoNmTanka_" + ChangeGyoNo);

	//単価変更行の[単価　工場名]列の値をクリア
    KojoNmTanka.value = "";

    //サブウィンドウが表示されているか
    if(subwinShowBln){
    	//サブウィンドウの[行番号]取得
    	subwinGyoNo = $("GyoNo");
    	if(ChangeGyoNo == subwinGyoNo.value){
    		//単価変更行と表示しているサブウィンドウの行番号が一致した場合
    		//サブウィンドウの[単価　工場名]の制御参照を行いクリア
    		subwinTankaKojoNm = $("TankaKojoNm");
    		subwinTankaKojoNm.value = "";
    		//サブウィンドウのページリフレッシュ
    		win.refresh();
    	}
    }

    //再計算フラグをonにする
    setFg_saikeisan();

    return true;

}

//========================================================================================
// [試作原価情報]テーブルの[歩留]変更時イベント
// 作成者：E.Nakamura
// 作成日：2010/02/18
// 概要  ：テーブル内保持情報[歩留　工場名]の値をクリアし、再計算フラグをonにする
//========================================================================================
function ShisakuGenka_Budomarichanged(ChangeGyoNo){

	var detailDoc = parent.detail.document;		//明細ﾌﾚｰﾑのDocument参照
	var KojoNmBudomari;							//[歩留　工場名]保持列制御用
	var subwinGyoNo;							//サブウィンドウの[行番号]制御用
	var subwinBudomariKojoNm;					//サブウィンドウの[歩留　工場名]制御用

	//歩留変更行の[歩留　工場名]列制御参照
	KojoNmBudomari = detailDoc.getElementById("hdnKojoNmBudomari_" + ChangeGyoNo);

	//歩留変更行の[歩留　工場名]列の値をクリア
    KojoNmBudomari.value = "";

    //サブウィンドウが表示されているか
    if(subwinShowBln){
    	//サブウィンドウの[行番号]取得
    	subwinGyoNo = $("GyoNo");
    	if(ChangeGyoNo == subwinGyoNo.value){
    		//単価変更行と表示しているサブウィンドウの行番号が一致した場合

    		//サブウィンドウの[歩留　工場名]の制御参照を行いクリア
    		subwinBudomariKojoNm = $("BudomariKojoNm");
    		subwinBudomariKojoNm.value = "";
    		//サブウィンドウのページリフレッシュ
    		win.refresh();
    	}
    }

    //再計算フラグをonにする
    setFg_saikeisan();

    return true;

}

//========================================================================================
// 整数部カンマ挿入
// 作成者：Y.Nishigawa
// 作成日：2009/10/29
// 概要  ：整数部3桁毎にカンマを挿入する
//========================================================================================
function setKanma(obj){

    obj.value = funAddComma(obj.value);
    return true;
}


//========================================================================================
// 空白置換関数（"" → "&nbsp;"）
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
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
// 作成日：2009/10/28
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
// 作成日：2009/10/28
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
// 作成日：2009/10/28
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
// 作成日：2009/10/28
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
// 作成日：2009/10/28
// 引数  ：①obj    :ラジオボタンオブジェクト
// 概要  ：ラジオボタンの権限編集
//========================================================================================
function kengenBottunSetting(obj){

    //ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.hdnKengen.value;

    //【QP@00342】ステータス制御
	var st_kenkyu = headerFrm.hdnStatus_kenkyu.value;
	var st_seikan = headerFrm.hdnStatus_seikan.value;
	var st_gentyo = headerFrm.hdnStatus_gentyo.value;
	var st_kojo    = headerFrm.hdnStatus_kojo.value;
	var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

	//【QP@00342】部署取得
	var kenkyu = headerFrm.hdnBusho_kenkyu.value;
	var seikan = headerFrm.hdnBusho_seikan.value;
	var gentyo = headerFrm.hdnBusho_gentyo.value;
	var kojo = headerFrm.hdnBusho_kojo.value;
	var eigyo = headerFrm.hdnBusho_eigyo.value;

    //【QP@00342】排他
    if(headerFrm.strKengenMoto.value == "999"){
    	obj.disabled = true;
    }
    else{
    	//【QP@00342】編集権限
	    if(Kengen.toString() == ConFuncIdEdit.toString()){
	        obj.disabled = false;

	        //【QP@00342】原資材調達部で確認完了の場合
	        /*
			 * if( gentyo == "1" && st_gentyo == 2){ obj.disabled = true; }
			 */

	        //【QP@00342】工場で確認完了の場合
	        /*
			 * if( kojo == "1" && st_kojo == 2){ obj.disabled = true; }
			 */

       		//20160607 KPX@1502111_No8】ADD start
	        // 部署：工場で確認完了(工場ステータス≧2)の場合
			if( kojo == "1" && st_kojo >= 2 ){
				obj.disabled = true;
			}
       		//20160607 KPX@1502111_No8】ADD end

	        //【QP@00342】生産管理部ステータス >= 3 or 営業ステータス >= 4　の場合
			if( st_seikan >= 3 || st_eigyo >= 4){
				obj.disabled = true;
			}
	    }
	    //【QP@00342】閲覧+Excel権限
	    else if(Kengen.toString() == ConFuncIdReadExcel.toString()){
	        obj.disabled = true;
	    }
	 // 20160513  KPX@1600766 ADD start
    	if (tankaHyoujiFlg == "1" || tankaHyoujiFlg == "0")  {
    		//研究所：単価開示すべて以外の場合
    		obj.disabled = true;
    	}
	 // 20160513  KPX@1600766 ADD end
    }
    return true;
}


//========================================================================================
// 類似品検索画面を起動（ダイアログ）
// 作成者：Y.Nishigawa
// 作成日：2009/10/20
// 引数  ：なし
// 概要  ：類似品検索画面を起動する
//========================================================================================
function funRuiziSearch() {

    //類似品検索画面を起動
    funOpenModalDialog("../SQ111RuiziSearch/RuiziSearch.jsp", window, "dialogHeight:" + window.screen.height + "px;dialogWidth:" + window.screen.width + "px;status:no;");

    return true;

}

//========================================================================================
// 小数0埋め、切り上げ処理
// 作成者：Y.Nishigawa
// 作成日：2009/10/20
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
// 作成日：2009/10/20
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
// 作成日：2009/10/20
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
// 作成日：2009/11/03
// 引数  ：①オブジェクト
// 概要  ：原価単位→売価単位へ設定する
//========================================================================================
function baikaTaniSetting(){

    var detailFrm = document.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

    detailFrm.txtBaikaTani.value = detailFrm.ddlGenkaTani.options[detailFrm.ddlGenkaTani.selectedIndex].innerText;

    return true;
}


//========================================================================================
// 日付フォーマット設定呼び出し
// 作成者：Y.Nishigawa
// 作成日：2009/11/03
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
// 作成日：2009/11/03
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
// 資材の一括選択
// 作成者：Y.Nishigawa
// 作成日：2009/11/03
// 引数  ：①オブジェクト
// 概要  ：yyyy/mm/dd形式へ変換する
//========================================================================================
function shizaiIkkatu(){

    var frm = document.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照

    //テーブル参照
    var tblShizai = detailDoc.getElementById("tblList4");

    var gyoCount = tblShizai.rows.length;

    if(frm.chkIkkatuShizai.checked){

        for(var i=0; i<gyoCount; i++){
            frm.chkShizaiGyo[i].checked = true;
        }

    }else{

        for(var i=0; i<gyoCount; i++){
            frm.chkShizaiGyo[i].checked = false;
        }

    }
}

//========================================================================================
// 終了ボタン押下時
// 作成者：Y.Nishigawa
// 作成日：2009/10/28
// 引数  ：なし
// 概要  ：画面を終了し、試作データ一覧画面へ遷移する
//========================================================================================
function funEnd(){

    //------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var mode = 1;

    //【QP@00342】承認ボタンにフォーカスセット
    parent.header.focus();

    //------------------------------------------------------------------------------------
    //                               計算内容の変更確認
    //------------------------------------------------------------------------------------
    //再計算が行われていない場合
    if(frm.FgSaikeisan.value == "true"){

        //確認メッセージを表示
        if(funConfMsgBox(E000007) == ConBtnYes){

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

    }else{
        //何もしない

    }

    //------------------------------------------------------------------------------------
    //                               入力項目の変更確認
    //------------------------------------------------------------------------------------
    if(funInputCheck(mode) == "true"){
        //処理なし

    }else{
        //メッセージ表示
        if(funXmlRead(xmlFGEN0020O, "hantei", 0) == "false"){

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
        }
        return false;
    }

    //2010/02/23 NAKAMURA ADD START-----
	//排他解除
	funcHaitaKaijo(mode)
    //2010/02/23 NAKAMURA ADD END-------

    //画面項目開放
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照

    //画面を閉じる
    parent.close();

    return true;
}

//========================================================================================
// 排他解除処理
// 作成者：E.Nakamura
// 作成日：2010/02/23
// 引数  ：mode  ：処理モード
//           	1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：なし
// 概要  ：終了ボタン押下時　排他制御を解除する
//========================================================================================
function funcHaitaKaijo(mode){

	//ﾍｯﾀﾞｰﾌｫｰﾑ参照
    var headerFrm = parent.header.document.frm00;
    var Kengen = headerFrm.strKengenMoto.value;

    //【QP@00342】編集権限（排他でない場合）
    if(Kengen.toString() != "999"){

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
// 選択行操作
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：なし
// 戻り値：なし
// 概要  ：選択行の背景色を変更する。
//========================================================================================
//【QP@00342】資材テーブル修正
/*function funChangeSelectRowColor2() {

    var oSrc = event.srcElement, oTBL, oTR, oTD;
    var BeforeRow;

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {
        for ( oTBL = oSrc.parentElement; oTBL.tagName != "TABLE"; oTBL = oTBL.parentElement ) {}
        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {}

        BeforeRow = (CurrentRow == "" ? 0 : CurrentRow / 1);

        //背景色を変更
        //oTBL.rows(oTR.rowIndex).style.backgroundColor = activeSelectedColor;

        if ((BeforeRow != oTR.rowIndex) && (BeforeRow + 1 <= oTBL.rows.length)){
            //背景色を戻す
            //oTBL.rows(BeforeRow).style.backgroundColor = deactiveSelectedColor;
        }

        //ｶﾚﾝﾄ行の退避
        CurrentRow = oTR.rowIndex;
    }

    return true;

}*/
function funChangeSelectRowColor3(oSrc) {

    //テーブル参照
    var oTBL, oTR, oTD;
    var BeforeRow;

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {

        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {

        }

        BeforeRow = (CurrentRow == "" ? 0 : CurrentRow / 1);

        //ｶﾚﾝﾄ行の退避
        CurrentRow = oTR.rowIndex;
    }

    return true;

}

//========================================================================================
// 処理中
// 作成者：Y.Nishigawa
// 作成日：2009/11/11
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
// 作成日：2009/11/11
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
//	//【QP@00342】承認ボタンにフォーカスセット
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


//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.38
//========================================================================================
// 製造工程表示
// 作成者：Y.Nishigawa
// 作成日：2010/10/08
// 引数  ：なし
// 戻り値：なし
// 概要  ：サンプルNoコンボボックスより選択された製造工程の詳細を表示する
//========================================================================================
function seizo_output(){

    //ﾌｫｰﾑ、ﾄﾞｷｭﾒﾝﾄへの参照
    var frm = document.frm00;
    var detailDoc = parent.detail.document;

    //コンボボックスで指定されたキーを基に明細表示
    var no = frm.ddlSeizoNo.selectedIndex;
    var seizo_shosai = detailDoc.getElementById("hdnSeizoShosai_"+no);
    frm.txtSeizoKotei.value = seizo_shosai.value;

}
//add end   -------------------------------------------------------------------------------


//========================================================================================
// 【QP@00342】試算中止ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2010/10/08
//========================================================================================
function ShisanChusi(){

	var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

    //【H24年度対応】2012/04/17 kazama add start
    var yuuko_budomari;		//有効歩留（％）
    var heikinjyutenryo;	//平均充填量（ｇ）
    var kesu_kotehi;		//固定費/ケース
    var kg_kotehi;			//固定費/KG
    // ADD 2013/11/1 okano【QP@30154】start
    var kesu_rieki;			//利益/ケース
    var kg_rieki;			//利益/KG
    // ADD 2013/11/1 okano【QP@30154】end
    //【H24年度対応】2012/04/17 kazama add end
//ADD 2013/07/12 ogawa 【QP@30151】No.13 start
    var set_color;
//ADD 2013/07/12 ogawa 【QP@30151】No.13 end

    //承認ボタンにフォーカスセット
    parent.header.focus();

    //列数取得
	var recCnt = detailFrm.cnt_sample.value;

	//選択列チェック
	var chkFg = false;
	for( var j = 0; j < recCnt; j++ ){

		//列数に対するオブジェクトが存在する場合
	    if(detailDoc.getElementById("chkInsatu"+j)){

		    //オブジェクトがチェックされている場合
		    if(detailDoc.getElementById("chkInsatu"+j).checked){
		    	chkFg = true;
			}
		}
	}

	//選択列がない場合
	if(chkFg == false){
		//選択されていない場合
		funInfoMsgBox(E000019);

		return;
	}

//20160617  KPX@1502111_No.5 ADD start
	//連携登録の確認
	chkHaigoLink();
	//試作が配合リンクに登録されている場合
	if (detailFrm.hdnRenkeiSeqShisaku.value > 0) {
		for(j = 0; j < recCnt; j++ ){
		    //選択列
		    if(detailDoc.getElementById("chkInsatu"+j).checked){
		    	//選択列の試作シーケンスが登録されているか
		    	if(detailFrm.hdnRenkeiSeqShisaku.value == detailDoc.getElementById("hdnSeq_Shisaku_"+j).value) {
		    		//連携登録されているサンプルは試算中止不可
		    		funErrorMsgBox(E000043);

		    		return;
		    	}
		    }
		}
	}
//20160617  KPX@1502111_No.5 ADD end

	//確認メッセージ表示
	if(funConfMsgBox(E000018) == ConBtnYes){

		//列数取得
		for( var j = 0; j < recCnt; j++ ){

			// ID作成
			txtId1 = "txtYukoBudomari_" + j;
			txtId2 = "txtHeikinZyu_" + j;
			txtId3 = "txtCaseKotei_" + j;
			txtId4 = "txtKgKotei_" + j;
			// ADD 2013/11/1 okano【QP@30154】start
			txtId5 = "txtCaseRieki_" + j;
			txtId6 = "txtKgRieki_" + j;
			// ADD 2013/11/1 okano【QP@30154】end
			//チェックボックスフラグ取得
			var checkbox = detailFrm.radioKoteihi[0].checked;

			//列数に対するオブジェクトが存在する場合
			if(detailDoc.getElementById("chkInsatu"+j)){

				//オブジェクトがチェックされている場合
				if(detailDoc.getElementById("chkInsatu"+j).checked){

					// 試算日
					detailDoc.getElementById("txtShisanHi_"+j).value = "試算中止";

					// 試算中止
					detailDoc.getElementById("hdnSisanChusi_"+j).value = "1";

					//【H24年度対応No15】2012/04/17 kazama add start
					detailDoc.getElementById(txtId1).style.background = "#c0c0c0";
					detailDoc.getElementById(txtId2).style.background = "#c0c0c0";

					if(checkbox == true){
						detailDoc.getElementById(txtId3).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano【QP@30154】start
						detailDoc.getElementById(txtId5).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano【QP@30154】end
					}else{
						detailDoc.getElementById(txtId4).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano【QP@30154】start
						detailDoc.getElementById(txtId6).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano【QP@30154】end
					}
					//【H24年度対応No15】2012/04/17 kazama add end
				//【H24年度対応No15】2012/04/26 SHIMA add Start
				}else{
					if(detailDoc.getElementById("hdnSisanChusi_"+j).value == 1){
						detailDoc.getElementById(txtId1).style.background = "#c0c0c0";
						detailDoc.getElementById(txtId2).style.background = "#c0c0c0";

						if(checkbox == true){
							detailDoc.getElementById(txtId3).style.background = "#c0c0c0";
							// ADD 2013/11/1 okano【QP@30154】start
							detailDoc.getElementById(txtId5).style.background = "#c0c0c0";
							// ADD 2013/11/1 okano【QP@30154】end
						}else{
							detailDoc.getElementById(txtId4).style.background = "#c0c0c0";
							// ADD 2013/11/1 okano【QP@30154】start
							detailDoc.getElementById(txtId6).style.background = "#c0c0c0";
							// ADD 2013/11/1 okano【QP@30154】end
						}
					}else{
//MOD 2013/07/11 ogawa 【QP@30151】No.13 start
//修正前ソース
//						detailDoc.getElementById(txtId1).style.background = "#ffff88";
//						detailDoc.getElementById(txtId2).style.background = "#ffff88";
//
//						if(checkbox == true){
//							detailDoc.getElementById(txtId3).style.background = "#ffff88";
//						}else{
//							detailDoc.getElementById(txtId4).style.background = "#ffff88";
//						}
//修正後ソース
						//固定チェックがONの場合はグレーにする
						if (detailDoc.getElementById("chkKoumoku_"+ j).checked) {
							set_color = "#c0c0c0";
					    }else{
							set_color = "#ffff88";
					    }
						detailDoc.getElementById(txtId1).style.background = set_color;
						detailDoc.getElementById(txtId2).style.background = set_color;
						if(checkbox == true){
							detailDoc.getElementById(txtId3).style.background = set_color;
							// ADD 2013/11/1 okano【QP@30154】start
							detailDoc.getElementById(txtId5).style.background = set_color;
							// ADD 2013/11/1 okano【QP@30154】end
						}else{
							detailDoc.getElementById(txtId4).style.background = set_color;
							// ADD 2013/11/1 okano【QP@30154】start
							detailDoc.getElementById(txtId6).style.background = set_color;
							// ADD 2013/11/1 okano【QP@30154】end
						}
//MOD 2013/07/11 ogawa 【QP@30151】No.13 end
					}
				//【H24年度対応No15】2012/04/26 SHIMA add End
				}
			}
		}

		//完了メッセージ
		funInfoMsgBox(S000003);

		return;

	}
    else{
    	//何もしない
        return true;
    }
}



//--------------------------------------------枝番作成--------------------------------------------------

//========================================================================================
// 【QP@00342】枝番作成ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2010/10/08
//========================================================================================
function fun_Edaban(){

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

    //枝番作成画面を起動する
//MOD start 2012/4/6 hisahori
//    retVal = funOpenModalDialog("../SQ110GenkaShisan/GenkaShisan_Edaban.jsp", args, "dialogHeight:100px;dialogWidth:450px;status:no;scroll:no");
    retVal = funOpenModalDialog("../SQ110GenkaShisan/GenkaShisan_Edaban.jsp", args, "dialogHeight:150px;dialogWidth:640px;status:no;scroll:no");
//MOD end 2012/4/6 hisahori

    //終了
// MOD start 20120410 hisahori
//    if(retVal == null){
//    	return true;
//    }
//    else if(retVal == "false"){
//    	//何もしない
//    	return true;
//    }
    if(retVal == null){
    	return true;
    }
	else if(retVal[0] == "false"){  // 終了ボタンで閉じられた場合、返り値がfalseの場合
    	return true;
    }
// MOD end 20120410 hisahori
    //登録
    else{

    	//枝番退避
// MOD start 2012/4/10 hisahori
//    	headerFrm.hdnShuruiEda.value = retVal;
    	headerFrm.hdnShuruiEda.value = retVal[0];
    	headerFrm.hdnShisakuNmEda.value = retVal[1];
// MOD end 2012/4/10 hisahori

    	//変更確認
    	if(funInputCheck(1) == "false"){
        	//確認メッセージ
        	if(funConfMsgBox(E000017) == ConBtnYes){

    	    	// MOD 2015/06/12 TT.Kitazawa【QP@40812】No.6 start
//        		if(funTorokuInfo(1)){
            	if(funTorokuInfo(1, 0)){
        	    // MOD 2015/06/12 TT.Kitazawa【QP@40812】No.6 end
                	//枝番作成
                	funExec_Edaban(1);
                }
                //登録失敗
                else{
                	return false;

                }
	        }
	        else{
	        	//枝番作成
	        	funExec_Edaban(1);

	        }
  		}
  		else{
  			//枝番作成
  			funExec_Edaban(1);

  		}

  		return true;
    }
}

//========================================================================================
// 【QP@00342】枝番作成処理
// 作成者：Y.Nishigawa
// 作成日：2010/10/08
//========================================================================================
function funExec_Edaban(mode){

	var headerDoc = parent.header.document; //ﾍｯﾀﾞﾌﾚｰﾑのDocument参照
    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var headerFrm = headerDoc.frm00;        //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

	//------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2200";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2180");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2180I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2180O );
    var mode = 1;

    //------------------------------------------------------------------------------------
    //                                  枝番作成
    //------------------------------------------------------------------------------------
// ADD start 20120413 hisahori
    //XMLの初期化
    setTimeout("xmlFGEN2180I.src = '../../model/FGEN2180I.xml';", ConTimer);
// ADD end 20120413 hisahori

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2200, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //排他解除
    funcHaitaKaijo(mode);

    //枝番設定
    headerFrm.txtEdaNo.value = funXmlRead_3(xmlResAry[2], "kihon", "no_eda", 0, 0);

    //再表示
// MOD 2012/4/4 hisahori
//    funGetKyotuInfo(1);

	    var FuncIdAry = new Array(ConResult,ConUserInfo);
	    var xmlReqAry2 = new Array(xmlUSERINFO_I);
	    var xmlResAry2 = new Array(xmlRESULT,xmlUSERINFO_O);

	    //引数をXMLﾌｧｲﾙに設定（作成した枝番を設定）
	    if (funReadyOutput("RGEN2160", xmlReqAry2, 1) == false) {
	        return false;
	    }
	    //ｾｯｼｮﾝ情報、共通情報を取得
	    if (funAjaxConnection("RGEN2160", FuncIdAry, xmlRGEN2160, xmlReqAry2, xmlResAry2, mode) == false) {
	        return false;
	    }
	    //▼画面を閉じる（終了ボタン押下時の処理と同じ）
    	end_click = true; //終了ボタン押下フラグ
        parent.close(); //画面を閉じる
	    //▲

		//原価試算画面（営業）を表示
	    window.open("../SQ160GenkaShisan_Eigyo/GenkaShisan_Eigyo.jsp","_blank","menubar=no,resizable=yes");

// MOD 2012/4/4 hisahori

}

//========================================================================================
// 【QP@00342】枝番作成子画面　初期表示
// 作成者：Y.Nishigawa
// 作成日：2010/10/08
//========================================================================================
function funLoad_Edaban(){
	//------------------------------------------------------------------------------------
    //                                    変数宣言
    //------------------------------------------------------------------------------------
    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2190";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2170");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2170I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2170O );
    var mode = 1;

    //------------------------------------------------------------------------------------
    //                                  工場情報取得
    //------------------------------------------------------------------------------------
    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput_Edaban(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2190, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

    //------------------------------------------------------------------------------------
    //                                 枝番種類コンボボックス表示
    //------------------------------------------------------------------------------------
    //枝番種類コンボボックス生成
    funCreateComboBox(frm.ddlEda , xmlResAry[2] , 7, 0);

//ADD start 2012/4/6 hisahori
	// 品名を親（原価試算）画面から取得し、枝番作成画面の試作名に表示
	var opener_header = window.dialogArguments[0].frm00; 	//ﾍｯﾀﾞﾌｫｰﾑ
    var frm = document.frm00;
   	var str_HinNm = opener_header.hdnNmMotHin.value;
    frm.txtShisakuNm.value = str_HinNm;
//ADD end 2012/4/6 hisahori
}

//========================================================================================
// 【QP@00342】枝番作成子画面　登録ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2010/10/08
//========================================================================================
function edaban_toroku(){

	var frm = document.frm00;

// MOD start 2012/4/10 hisahori
//	//枝番選択値の取得
//	var sentaku = frm.ddlEda.options[frm.ddlEda.selectedIndex].value;
//	//戻り値の設定
//	window.returnValue = sentaku;

	var sentaku = frm.ddlEda.options[frm.ddlEda.selectedIndex].value;
	var EdaNm = frm.txtEdaShisakuNm.value;
    var arrRtnVal = new Array(sentaku, EdaNm);
	window.returnValue = arrRtnVal;
// MOD end 2012/4/10 hisahori

	//画面を閉じる
    close(self);

}

//========================================================================================
// 【QP@00342】枝番作成子画面　終了ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2010/10/08
//========================================================================================
function edaban_close(){

	//戻り値の設定
// MOD start 20120419 hisahori
//	window.returnValue = "false";
    var arrRtnVal = new Array("false", "");
	window.returnValue = arrRtnVal;
// MOD end 20120419 hisahori

	//画面を閉じる
    close(self);

}

//========================================================================================
// 【QP@00342】XMLファイルに書き込み（枝番作成子画面）
// 作成者：Y.Nishigawa
// 作成日：2009/10/23
// 引数  ：①XmlId  ：XMLID
//       ：②reqAry ：機能ID別送信XML(配列)
//       ：③Mode   ：処理モード
//           1:セッション有り、2:セッションなし、3:ユーザ情報取得のみ
// 戻り値：なし
// 概要  ：機能IDごとのXMLファイルに引数を設定する
//========================================================================================
function funReadyOutput_Edaban(XmlId, reqAry, mode) {

    var i;
    for (i = 0; i < reqAry.length; i++) {
        //画面初期表示（ｽﾃｰﾀｽ設定）
        if (XmlId.toString() == "RGEN2190") {
            switch (i) {
                case 0:    //USERINFO
                    funXmlWrite(reqAry[i], "kbn_shori", mode, 0);
                    funXmlWrite(reqAry[i], "id_user", "", 0);
                    break;
                case 1:    //FGEN2170
                    break;
            }
        }


    }
    return true;
}


//=================================== ステータス設定ダイアログ　JavaScript  ================================

//========================================================================================
// 【QP@00342】ｽﾃｰﾀｽ設定初期表示
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
//========================================================================================
function funLoad_Status(){

	var opener_header = window.dialogArguments[0].frm00; 	//ﾍｯﾀﾞﾌｫｰﾑ
	var opener_detail = window.dialogArguments[1].frm00;		//明細ﾌｫｰﾑ
    var frm = document.frm00;

    //戻り値の初期化
	window.returnValue = "false";

   	//ステータス取得
   	var st_kenkyu = opener_header.hdnStatus_kenkyu.value;
   	var st_seikan = opener_header.hdnStatus_seikan.value;
   	var st_gentyo = opener_header.hdnStatus_gentyo.value;
   	var st_kojo = opener_header.hdnStatus_kojo.value;
   	var st_eigyo = opener_header.hdnStatus_eigyo.value;

   	//部署取得
    var kenkyu = opener_header.hdnBusho_kenkyu.value;
    var seikan = opener_header.hdnBusho_seikan.value;
    var gentyo = opener_header.hdnBusho_gentyo.value;
    var kojo = opener_header.hdnBusho_kojo.value;
    var eigyo = opener_header.hdnBusho_eigyo.value;
    // ADD 2013/11/5 QP@30154 okano start
    var DataId = opener_header.hdnDataId.value;
    // ADD 2013/11/5 QP@30154 okano end

    //現在ｽﾃｰﾀｽ設定
    document.getElementById("divStatusKenkyu_now").innerHTML = funStatusSetting("1",st_kenkyu);
    document.getElementById("divStatusSeikan_now").innerHTML = funStatusSetting("2",st_seikan);
    document.getElementById("divStatusGentyo_now").innerHTML = funStatusSetting("3",st_gentyo);
    document.getElementById("divStatusKojo_now").innerHTML = funStatusSetting("4",st_kojo);
    document.getElementById("divStatusEigyo_now").innerHTML = funStatusSetting("5",st_eigyo);

    //画面制御
    var output_html = "";
    output_html = output_html + "<table id=\"dataTable\" name=\"dataTable\" border=\"0\" cellspacing=\"0\" width=\"99%\" align=\"left\"> ";

    //生産管理部の場合
	if( seikan == "1"){
		output_html = output_html + "   <tr> ";
		output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"0\" tabindex=\"3\" checked></td> ";
		output_html = output_html + "    	<td>保存（ステータス変更無し）</td> ";
		output_html = output_html + "   </tr> ";
		output_html = output_html + "   <tr> ";

		//試算依頼　：　ステータス「生1」「営2」の場合に編集可
		if(st_seikan == 1 && st_eigyo == 2){
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"1\" tabindex=\"4\"></td> ";
		}
		else{
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"1\" tabindex=\"4\" disabled></td> ";
		}
		output_html = output_html + "    	<td>試算依頼</td> ";
		output_html = output_html + "   </tr> ";
		output_html = output_html + "   <tr> ";

		//確認完了　：　ステータス「生2」「原2」「工2」の場合に編集可
		// MOD 2013/11/5 QP@30154 okano start
//			if(st_seikan == 2 && st_gentyo == 2 && st_kojo == 2 && st_eigyo != 4){
		if(((st_seikan == 2 && st_gentyo == 2 && st_kojo == 2) || (DataId == 1 && st_seikan != 3 && st_eigyo == 2)) && st_eigyo != 4){
		// MOD 2013/11/5 QP@30154 okano end
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\"></td> ";
		}
		else{
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\" disabled></td> ";
		}
		output_html = output_html + "    	<td>確認完了</td> ";
		output_html = output_html + "   </tr> ";

		/*
		 * output_html = output_html + " <tr> ";
		 *
		 * //確認取消し ： ステータス「営３」以降は編集不可 if( st_eigyo >= 3 || st_seikan < 3){
		 * output_html = output_html + " <td align=\"center\"><input
		 * type=\"radio\" name=\"chk\" value=\"3\" tabindex=\"6\" disabled></td> "; }
		 * else{ output_html = output_html + " <td align=\"center\"><input
		 * type=\"radio\" name=\"chk\" value=\"3\" tabindex=\"6\"></td> "; }
		 * output_html = output_html + " <td>確認取消し</td> "; output_html =
		 * output_html + " </tr> ";
		 */
	}
	//原資材調達部の場合
	else if( gentyo == "1"){
		output_html = output_html + "   <tr> ";
		output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"0\" tabindex=\"3\" checked></td> ";
		output_html = output_html + "    	<td>保存（ステータス変更無し）</td> ";
		output_html = output_html + "   </tr> ";
		output_html = output_html + "   <tr> ";
		//確認完了　：　ステータス「原1」の場合に編集可
		if(st_gentyo == 1 && st_eigyo != 4){
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\"></td> ";
		}
		else{
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\" disabled></td> ";
		}
		output_html = output_html + "    	<td>確認完了</td> ";
		output_html = output_html + "   </tr> ";

		/*
		 * output_html = output_html + " <tr> ";
		 *
		 * //確認取消し ： ステータス「生1～2」「原2」の場合に編集可 if(st_seikan >= 1 && st_seikan <= 2 &&
		 * st_gentyo == 2 && st_eigyo != 4){ output_html = output_html + "
		 * <td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"3\"
		 * tabindex=\"6\"></td> "; } else{ output_html = output_html + "
		 * <td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"3\"
		 * tabindex=\"6\" disabled></td> "; } output_html = output_html + "
		 * <td>確認取消し</td> "; output_html = output_html + " </tr> ";
		 */
	}
	//工場の場合
	else if( kojo == "1"){
		output_html = output_html + "   <tr> ";
		output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"0\" tabindex=\"3\" checked></td> ";
		output_html = output_html + "    	<td>保存（ステータス変更無し）</td> ";
		output_html = output_html + "   </tr> ";
		output_html = output_html + "   <tr> ";

		//確認完了　：　ステータス「営2」「生2」「工1」の場合に編集可
		if(st_eigyo == 2 && st_seikan == 2 && st_kojo == 1){
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\"></td> ";
		}
		else{
			output_html = output_html + "    	<td align=\"center\"><input type=\"radio\" name=\"chk\" value=\"2\" tabindex=\"5\" disabled></td> ";
		}

		output_html = output_html + "    	<td>確認完了</td> ";
		output_html = output_html + "   </tr> ";

		/*
		 * output_html = output_html + " <tr> ";
		 *
		 * //確認取消し ： ステータス「生３」以降は編集不可 if(st_seikan >= 3 || st_eigyo == 4 ||
		 * st_kojo != 2){ output_html = output_html + " <td align=\"center\"><input
		 * type=\"radio\" name=\"chk\" value=\"3\" tabindex=\"6\" disabled></td> "; }
		 * else{ output_html = output_html + " <td align=\"center\"><input
		 * type=\"radio\" name=\"chk\" value=\"3\" tabindex=\"6\"></td> "; }
		 * output_html = output_html + " <td>確認取消し</td> "; output_html =
		 * output_html + " </tr> ";
		 */
	}
	output_html = output_html + "</table> ";

    //HTML設定
    document.getElementById("status_table").innerHTML = output_html;

    //ステータスクリアボタン
    if( seikan == "1"){
    	document.getElementById("btnClear").style.visibility = "visible";
    }

	return true;
}


//========================================================================================
// 【QP@00342】ｽﾃｰﾀｽ設定　登録ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
//========================================================================================
function fun_Toroku_status(){

	//***** ADD【H24年度対応】20120420 hagiwara S **********
	var headFrm = window.dialogArguments[0].frm00;								// ヘッダーフォーム
	var dtlFrm = window.dialogArguments[1].frm00;								// 明細ﾌｫｰﾑ
	var divGenryo_Left = dtlFrm.document.getElementById("divGenryo_Left");		// 原料情報表示エリア
	var cntGenryo = divGenryo_Left.document.getElementById("cnt_genryo").value;	// 原料情報行数
	var genryoList = divGenryo_Left.document.getElementById("tblList1");		// 原料情報
	var errFlg = 0;																// 1:エラーメッセージ、処理中断(仮保存込み)
																				// 2:エラーメッセージ(仮保存込み)
	var errGyou = 0;															// エラーが最初に見つかった行
	//***** ADD【H24年度対応】20120420 hagiwara E **********

	//選択ﾗｼﾞｵﾎﾞﾀﾝ値取得
    var j;
    var st_setting;
    var radio = document.getElementsByName("chk");
    for( j=0; j<radio.length; j++ ){
	    if( radio[j].checked ){
	   		st_setting = radio[j].value;
	    }
    }
    
    // 20170515 KPX@1700856 ADD Start
    var arrTankaZeroGenryo = dtlFrm.hdnTankaZeroGenryo.value.split(ConDelimiter);
    // 20170515 KPX@1700856 ADD end

    //戻り値の設定
	window.returnValue = st_setting;

	//***** ADD【H24年度対応】20120420 hagiwara S **********
	// 単価または歩留が空白またはゼロの場合、エラーメッセージ表示
	// エラー判定フラグを初期化
	errFlg = 0;

    for(var i = 1; i < cntGenryo; i++){
    	// チェックボックス、原料CDの両方が存在する
// ADD start 20120713 hisahori
		// どのサンプルにも配合されていない原料行は非表示となっているため、単価・歩留のチェックしない
		if(genryoList.rows[i].style.display != "none"){
// ADD end 20120713 hisahori
	    	if((genryoList.rows[i].cells[0].childNodes[0].value === "on") && (genryoList.rows[i].cells[2].childNodes[0].value !== "") && (genryoList.rows[i].cells[2].childNodes[0].value !== undefined)){
	    		
	    		// 20170515 KPX@1700856 MOD Start
	    		var targetGenryoCd = genryoList.rows[i].cells[2].childNodes[0].value;
	    		var isNotTankaZeroGenryoCd = true;
	    		for(var j = 0;j < arrTankaZeroGenryo.length ; j++){
	    			// 原料CDが0円許可の場合(6桁0埋め)
	    			if(targetGenryoCd === ("000000"+arrTankaZeroGenryo[j]).slice(-6)){
	    				isNotTankaZeroGenryoCd = false;
	    			}
	    		}
	    		
	    		// 原料CDが7024以外の場合
	    		//if(genryoList.rows[i].cells[2].childNodes[0].value !== "007024"){
	    		// 原料CDが単価0円許可以外の場合
	    		if(isNotTankaZeroGenryoCd){
	    		// 20170515 KPX@1700856 MOD End
	    			// 単価=(0 or 空 or undefined) or 歩留=(0 or 空 or undefined)の場合
	    			if((genryoList.rows[i].cells[5].childNodes[0].value === "0.00") || (genryoList.rows[i].cells[5].childNodes[0].value === "") || (genryoList.rows[i].cells[5].childNodes[0].value === undefined) || (genryoList.rows[i].cells[6].childNodes[0].value === "0.00") || (genryoList.rows[i].cells[6].childNodes[0].value === "") || (genryoList.rows[i].cells[6].childNodes[0].value === undefined)){

	    				// 生産管理部の場合
	    				if(headFrm.hdnBusho_seikan.value === "1"){
	    					// ステータスが「完了」かもしくは、確認完了のラジオボタンにチェックが入っている場合
	    					if(headFrm.hdnStatus_seikan.value === "3" || st_setting === "2"){
	    						errFlg = 1;
	    						if(errGyou === 0) errGyou = i;
	    					}
	//    					【H24年度対応 修正２】2012/05/29 TT H.SHIMA DEL Start
	    					// ステータスが「-」「依頼」かもしくは試算依頼のラジオボタンにチェックが入っている場合
	//    					else if(headFrm.hdnStatus_seikan.value === "1" || headFrm.hdnStatus_seikan.value === "2" || st_setting === "1"){
	//    						errFlg = 2;
	//    						if(errGyou === 0) errGyou = i;
	//    					}
	//    					【H24年度対応 修正２】2012/05/29 TT H.SHIMA DEL End
	    				}
	    				// 原資材調達部
	    				else if(headFrm.hdnBusho_gentyo.value === "1"){
	    					// ステータスが「完了」かもしくは、確認完了のラジオボタンにチェックが入っている場合
	    					if(headFrm.hdnStatus_gentyo.value === "2" || st_setting === "2"){
	    						errFlg = 1;
	    						if(errGyou === 0) errGyou = i;
	    					}
	//    					【H24年度対応 修正２】2012/05/30 TT H.SHIMA DEL Start
	    					// ステータスが「-」の場合
	//    					else if(headFrm.hdnStatus_gentyo.value === "1"){
	//    						errFlg = 2;
	//    						if(errGyou === 0) errGyou = i;
	//    					}
	//    					【H24年度対応 修正２】2012/05/30 TT H.SHIMA DEL End
	    				}
	    				// 工場の場合
	    				else if(headFrm.hdnBusho_kojo.value === "1"){
	    					// ステータスが「完了」かもしくは、確認完了のラジオボタンにチェックが入っている場合
	    					if(headFrm.hdnStatus_kojo.value === "2" || st_setting === "2"){
	    						errFlg = 1;
	    						if(errGyou === 0) errGyou = i;
	    					}
	//    					【H24年度対応 修正２】2012/05/29 TT H.SHIMA DEL Start
	    					// ステータスが「-」の場合
	//    					else if(headFrm.hdnStatus_kojo.value === "1"){
	//    						errFlg = 2;
	//    						if(errGyou === 0) errGyou = i;
	//    					}
	//    					【H24年度対応 修正２】2012/05/29 TT H.SHIMA DEL End
	    				}
	    			}
	    		}
	    	}
// ADD start 20120713 hisahori
		}
// ADD end 20120713 hisahori
    }

    // 各エラー種に対応した処理をおこなう
    switch(errFlg){
	    case 1:
	    	// エラーメッセージを表示し、登録処理を中断
			//funErrorMsgBox("【原料情報】[行数" + errGyou + "]" + E000024);
			funErrorMsgBox("【原料情報】" + E000024);
			window.returnValue = "false";
			break;
	    case 2:
	    	// エラーメッセージを表示
			//funErrorMsgBox("【原料情報】[行数" + errGyou + "]" + E000024);
			funErrorMsgBox("【原料情報】" + E000024);
			break;
    }
    //***** ADD【H24年度対応】20120420 hagiwara E **********

	//画面を閉じる
    close(self);
}

//========================================================================================
// 【QP@00342】ｽﾃｰﾀｽ設定　終了ボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
//========================================================================================
function fun_Close_st(){

	//戻り値の設定
	window.returnValue = "false";

	//画面を閉じる
    close(self);
}

//========================================================================================
// 【QP@00342】ｽﾃｰﾀｽ設定　ステータスクリアボタン押下
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
//========================================================================================
function fun_status_clear(){

	var opener_header = window.dialogArguments[0].frm00; 	//ﾍｯﾀﾞﾌｫｰﾑ
	var opener_detail = window.dialogArguments[1].frm00;		//明細ﾌｫｰﾑ

    var retVal;

    args = new Array();
    args[0] = opener_header.txtShainCd.value;
	args[1] = opener_header.txtNen.value;
	args[2] = opener_header.txtOiNo.value;
	args[3] = opener_header.txtEdaNo.value;
// ADD start 20120615 hisahori
	args[4] = opener_detail.hidsetSeqShisaku.value;
	args[5] = opener_detail.hidsetSampleNo.value;
	args[6] = opener_detail.hidsetShisanHi.value;
	args[7] = opener_detail.hidsetShisanChushi.value;
// ADD start 20120615 hisahori

	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 start
	args[8] = opener_detail.hidsetChkKoumoku.value;		//項目固定チェック
	// ADD 2015/03/03 TT.Kitazawa【QP@40812】No.29 end

    //ステータスクリア画面を起動する
//    retVal = funOpenModalDialog("../SQ130StatusClear/SQ130StatusClear.jsp", args, "dialogHeight:500px;dialogWidth:650px;status:no;scroll:no");
	// MOD 20160607  KPX@1502111_No9 start
//    retVal = funOpenModalDialog("../SQ130StatusClear/SQ130StatusClear.jsp", args, "dialogHeight:650px;dialogWidth:650px;status:no;scroll:no");
    retVal = funOpenModalDialog("../SQ130StatusClear/SQ130StatusClear.jsp", args, "dialogHeight:500px;dialogWidth:650px;status:no;scroll:no");
	// MOD 20160607  KPX@1502111_No9 end

    //クリア成功時
    if(retVal == "Clear"){
    	//戻り値の設定
		window.returnValue = retVal;

		//画面を閉じる
	    close(self);
    }
}

//========================================================================================
//【QP@10713】固定費計算条件処理データ切り替え処理
//作成者：H.Shima
//作成日：2011/10/21
//更新者：Ryo.Hagiwara
//更新日：2011/11/17
//引数  ：切り替えフラグ　１　→　「固定費/ケース」　2　→　「固定費/KG」
//概要  ：画面の制御を行う
//========================================================================================
function funChangeMode(changeFlg) {

	var headId1 = document.getElementById("kotehi_case");
	var headId2 = document.getElementById("kotehi_kg");
	// ADD 2013/11/1 okano【QP@30154】start
	var headId3 = document.getElementById("rieki_case");
	var headId4 = document.getElementById("rieki_kg");
	// ADD 2013/11/1 okano【QP@30154】end

	//【H24年度対応No15】2012/04/16 kazama add start
	var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
	var tableKeisanNm;    //読み込みテーブル名
    tableKeisanNm = "keisan";
	//【H24年度対応No15】2012/04/25 kazama add end
	// MOD 2013/12/24 QP@30154 okano start
//		// ADD 2013/11/11 okano【QP@30154】start
//	    var headerFrm = parent.header.document.frm00; //ﾍｯﾀﾞｰﾌｫｰﾑ参照
//		// ADD 2013/11/11 okano【QP@30154】end
    var cd_kaisha = funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0);
    // MOD 2013/12/24 QP@30154 okano end

	if(changeFlg == 1){
		for(var i = 0; i < cnt_sample; i++){
			//【H24年度対応No15】2012/04/16 kazama add start
			//試算中止
			var fg_chusi = detailDoc.getElementById("hdnSisanChusi_"+i).value;
			//【H24年度対応No15】2012/04/16 kazama add end

			// ID作成
			txtId1 = "txtCaseKotei_" + i;
			txtId2 = "txtKgKotei_" + i;
			// ADD 2013/11/1 okano【QP@30154】start
			txtId3 = "txtCaseRieki_" + i;
			txtId4 = "txtKgRieki_" + i;
			// ADD 2013/11/1 okano【QP@30154】end

			//【H24年度対応No15】2012/04/16 kazama add start
	// 【QP@10713】20111117 hagiwara mod start
			if(fg_chusi == 1){
				// readOnly切り替え
				if(document.frm00.radioKoteihi != undefined){
//MOD 2013/07/11 ogawa 【QP@30151】No.13 start
//修正前ソース
//					document.getElementById(txtId1).readOnly = "";
//修正後ソース
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						document.getElementById(txtId1).readOnly = "readonly";
						// ADD 2013/11/1 okano【QP@30154】start
						document.getElementById(txtId3).readOnly = "readonly";
						// ADD 2013/11/1 okano【QP@30154】end
				    }else{
						document.getElementById(txtId1).readOnly = "";
						// ADD 2013/11/1 okano【QP@30154】start
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							document.getElementById(txtId3).readOnly = "";
						} else {
							document.getElementById(txtId3).readOnly = "readonly";
						}
						// ADD 2013/11/1 okano【QP@30154】end
				    }
//MOD 2013/07/11 ogawa 【QP@30151】No.13 end
					document.getElementById(txtId2).readOnly = "readonly";
					document.getElementById(txtId1).style.background = "#c0c0c0";
					document.getElementById(txtId2).style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】start
					document.getElementById(txtId4).readOnly = "readonly";
					// MOD 2013/12/24 QP@30154 okano start
//						if(headerFrm.hdnBusho_kojo.value != "1"){
//							document.getElementById(txtId3).style.background = "#c0c0c0";
//						} else {
//							document.getElementById(txtId3).style.background = "#ffffff";
//						}
					document.getElementById(txtId3).style.background = "#c0c0c0";
					// MOD 2013/12/24 QP@30154 okano end
					document.getElementById(txtId4).style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】end
				}else{
//MOD 2013/07/11 ogawa 【QP@30151】No.13 start
//修正前ソース
//					parent.detail.document.frm00.txtId1.readOnly = "";
//修正後ソース
				    if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						parent.detail.document.frm00.txtId1.readOnly ="readonly";
						// ADD 2013/11/1 okano【QP@30154】start
						parent.detail.document.frm00.txtId3.readOnly ="readonly";
						// ADD 2013/11/1 okano【QP@30154】end
				    }else{
						parent.detail.document.frm00.txtId1.readOnly = "";
						// ADD 2013/11/1 okano【QP@30154】start
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							parent.detail.document.frm00.txtId3.readOnly = "";
						} else {
							parent.detail.document.frm00.txtId3.readOnly = "readonly";
						}
						// ADD 2013/11/1 okano【QP@30154】end

				    }
//MOD 2013/07/11 ogawa 【QP@30151】No.13 end
					parent.detail.document.frm00.txtId2.readOnly = "readonly";
					// テキストボックス色切り替え
					parent.detail.document.frm00.txtId1.style.background = "#c0c0c0";
					parent.detail.document.frm00.txtId2.style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】start
					parent.detail.document.frm00.txtId4.readOnly = "readonly";
					// テキストボックス色切り替え
					// MOD 2013/12/24 QP@30154 okano start
//						if(headerFrm.hdnBusho_kojo.value != "1"){
//							parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
//						} else {
//							parent.detail.document.frm00.txtId3.style.background = "#ffffff";
//						}
					parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
					// MOD 2013/12/24 QP@30154 okano end
					parent.detail.document.frm00.txtId4.style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】end
				}
			}else{
				// readOnly切り替え
				if(document.frm00.radioKoteihi != undefined){
//MOD 2013/07/11 ogawa 【QP@30151】No.13 start
//修正前ソース
//					document.getElementById(txtId1).readOnly = "";
//					document.getElementById(txtId2).readOnly = "readonly";
//					document.getElementById(txtId1).style.background = "#ffff88";
//					document.getElementById(txtId2).style.background = "#ffffff";
//修正後ソース
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						document.getElementById(txtId1).readOnly = "readonly";
						document.getElementById(txtId1).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano【QP@30154】start
						document.getElementById(txtId3).readOnly = "readonly";
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
//								document.getElementById(txtId3).style.background = "#c0c0c0";
//							} else {
//								document.getElementById(txtId3).style.background = "#ffffff";
//							}
						document.getElementById(txtId3).style.background = "#c0c0c0";
						// MOD 2013/12/24 QP@30154 okano end
						// ADD 2013/11/1 okano【QP@30154】end
				    }else{
						document.getElementById(txtId1).readOnly = "";
						document.getElementById(txtId1).style.background = "#ffff88";
						// ADD 2013/11/1 okano【QP@30154】start
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							document.getElementById(txtId3).readOnly = "";
							document.getElementById(txtId3).style.background = "#ffff88";
						} else {
							document.getElementById(txtId3).readOnly = "readonly";
							// MOD 2013/12/24 QP@30154 okano start
//								document.getElementById(txtId3).style.background = "#ffffff";
							document.getElementById(txtId3).style.background = "#c0c0c0";
							// MOD 2013/12/24 QP@30154 okano end
						}
						// ADD 2013/11/1 okano【QP@30154】end
				    }
					document.getElementById(txtId2).readOnly = "readonly";
					document.getElementById(txtId2).style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】start
					document.getElementById(txtId4).readOnly = "readonly";
					document.getElementById(txtId4).style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】end
//MOD 2013/07/11 ogawa 【QP@30151】No.13 end
				}else{
//MOD 2013/07/11 ogawa 【QP@30151】No.13 start
//修正前ソース
//					parent.detail.document.frm00.txtId1.readOnly = "";
//					parent.detail.document.frm00.txtId2.readOnly = "readonly";
//					// テキストボックス色切り替え
//					parent.detail.document.frm00.txtId1.style.background = "#ffff88";
//					parent.detail.document.frm00.txtId2.style.background = "#ffffff";
//修正後ソース
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						parent.detail.document.frm00.txtId1.readOnly = "readonly";
						// テキストボックス色切り替え
						parent.detail.document.frm00.txtId1.style.background = "#c0c0c0";
						// ADD 2013/11/1 okano【QP@30154】start
						parent.detail.document.frm00.txtId3.readOnly = "readonly";
						// テキストボックス色切り替え
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
//								parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
//							} else {
//								parent.detail.document.frm00.txtId3.style.background = "#ffffff";
//							}
						parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
						// MOD 2013/12/24 QP@30154 okano end
						// ADD 2013/11/1 okano【QP@30154】end
				    }else{
						parent.detail.document.frm00.txtId1.readOnly = "";
						// テキストボックス色切り替え
						parent.detail.document.frm00.txtId1.style.background = "#ffff88";
						// ADD 2013/11/1 okano【QP@30154】start
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							parent.detail.document.frm00.txtId3.readOnly = "";
							// テキストボックス色切り替え
							parent.detail.document.frm00.txtId3.style.background = "#ffff88";
							// ADD 2013/11/1 okano【QP@30154】end
						} else {
							parent.detail.document.frm00.txtId3.readOnly = "readonly";
							// MOD 2013/12/24 QP@30154 okano start
//								parent.detail.document.frm00.txtId3.style.background = "#ffffff";
							parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
							// MOD 2013/12/24 QP@30154 okano end
						}
					}
					parent.detail.document.frm00.txtId2.readOnly = "readonly";
					// テキストボックス色切り替え
					parent.detail.document.frm00.txtId2.style.background = "#ffffff";
//MOD 2013/07/11 ogawa 【QP@30151】No.13 end
					// ADD 2013/11/1 okano【QP@30154】start
					parent.detail.document.frm00.txtId4.readOnly = "readonly";
					// テキストボックス色切り替え
					parent.detail.document.frm00.txtId4.style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】end
				}
			}
			//【H24年度対応】2012/04/18 kazama mod end
		}
	}
	else if(changeFlg == 2){
		for(var i = 0; i < cnt_sample; i++){
			//【H24年度対応No15】2012/04/18 kazama mod start
			//【QP@00342】試算中止
        	var fg_chusi = detailDoc.getElementById("hdnSisanChusi_"+i).value;
			txtId1 = "txtCaseKotei_" + i;
			txtId2 = "txtKgKotei_" + i;
			// ADD 2013/11/1 okano【QP@30154】start
			txtId3 = "txtCaseRieki_" + i;
			txtId4 = "txtKgRieki_" + i;
			// ADD 2013/11/1 okano【QP@30154】end

			if(fg_chusi == 1){
				if(document.frm00.radioKoteihi != undefined){
					document.getElementById(txtId1).readOnly = "readonly";
					// ADD 2013/11/1 okano【QP@30154】start
					document.getElementById(txtId3).readOnly = "readonly";
					// ADD 2013/11/1 okano【QP@30154】end
//MOD 2013/07/11 ogawa 【QP@30151】No.13 start
//修正前ソース
//					document.getElementById(txtId2).readOnly = "";
//修正後ソース
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						document.getElementById(txtId2).readOnly = "readonly";
						// ADD 2013/11/1 okano【QP@30154】start
						document.getElementById(txtId4).readOnly = "readonly";
						// ADD 2013/11/1 okano【QP@30154】end
				    }else{
						document.getElementById(txtId2).readOnly = "";
						// ADD 2013/11/1 okano【QP@30154】start
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							document.getElementById(txtId4).readOnly = "";
						} else {
							document.getElementById(txtId4).readOnly = "readonly";
						}
						// ADD 2013/11/1 okano【QP@30154】end
				    }
//MOD 2013/07/11 ogawa 【QP@30151】No.13 end
					document.getElementById(txtId1).style.background = "#ffffff";
					document.getElementById(txtId2).style.background = "#c0c0c0";
					// ADD 2013/11/1 okano【QP@30154】start
					document.getElementById(txtId3).style.background = "#ffffff";
					// MOD 2013/12/24 QP@30154 okano start
//						if(headerFrm.hdnBusho_kojo.value != "1"){
//							document.getElementById(txtId4).style.background = "#c0c0c0";
//						} else {
//							document.getElementById(txtId4).style.background = "#ffffff";
//						}
					document.getElementById(txtId4).style.background = "#c0c0c0";
					// MOD 2013/12/24 QP@30154 okano end
					// ADD 2013/11/1 okano【QP@30154】end
				}else{
					parent.detail.document.frm00.txtId1.readOnly = "readonly";
					// ADD 2013/11/1 okano【QP@30154】start
					parent.detail.document.frm00.txtId3.readOnly = "readonly";
					// ADD 2013/11/1 okano【QP@30154】end
//MOD 2013/07/11 ogawa 【QP@30151】No.13 start
//修正前ソース
//					parent.detail.document.frm00.txtId2.readOnly = "";
//修正後ソース
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						parent.detail.document.frm00.txtId2.readOnly = "readonly";
						// ADD 2013/11/1 okano【QP@30154】start
						parent.detail.document.frm00.txtId4.readOnly = "readonly";
						// ADD 2013/11/1 okano【QP@30154】end
				    }else{
						parent.detail.document.frm00.txtId2.readOnly = "";
						// ADD 2013/11/1 okano【QP@30154】start
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							parent.detail.document.frm00.txtId4.readOnly = "";
						} else {
							parent.detail.document.frm00.txtId4.readOnly = "readonly";
						}
						// ADD 2013/11/1 okano【QP@30154】end
				    }
//MOD 2013/07/11 ogawa 【QP@30151】No.13 end
					// テキストボックス色切り替え
					parent.detail.document.frm00.txtId1.style.background = "#ffffff";
					parent.detail.document.frm00.txtId2.style.background = "#c0c0c0";
					// ADD 2013/11/1 okano【QP@30154】start
					parent.detail.document.frm00.txtId3.style.background = "#ffffff";
					// MOD 2013/12/24 QP@30154 okano start
//						if(headerFrm.hdnBusho_kojo.value != "1"){
//							parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
//						} else {
//							parent.detail.document.frm00.txtId4.style.background = "#ffffff";
//						}
					parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
					// MOD 2013/12/24 QP@30154 okano end
					// ADD 2013/11/1 okano【QP@30154】end
				}
				if(fg_chusi[i+1] != 1){
					//試算中止設定の終了
					fg_chusi = 0;
				}

			}else{

				if(document.frm00.radioKoteihi != undefined){
//MOD 2013/07/11 ogawa 【QP@30151】No.13 start
//修正前ソース
//					document.getElementById(txtId1).readOnly = "readonly";
//					document.getElementById(txtId2).readOnly = "";
//					document.getElementById(txtId1).style.background = "#ffffff";
//					document.getElementById(txtId2).style.background = "#ffff88";
//修正後ソース
					document.getElementById(txtId1).readOnly = "readonly";
					document.getElementById(txtId1).style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】start
					document.getElementById(txtId3).readOnly = "readonly";
					document.getElementById(txtId3).style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】end
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						document.getElementById(txtId2).readOnly = "readonly";
						document.getElementById(txtId2).style.background = "#c0c0c0";
						// ADD 2013/11/1 okano【QP@30154】start
						document.getElementById(txtId4).readOnly = "readonly";
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
//								document.getElementById(txtId4).style.background = "#c0c0c0";
//							} else {
//								document.getElementById(txtId4).style.background = "#ffffff";
//							}
						document.getElementById(txtId4).style.background = "#c0c0c0";
						// MOD 2013/12/24 QP@30154 okano end
						// ADD 2013/11/1 okano【QP@30154】end
				    }else{
						document.getElementById(txtId2).readOnly = "";
						document.getElementById(txtId2).style.background = "#ffff88";
						// ADD 2013/11/1 okano【QP@30154】start
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							document.getElementById(txtId4).readOnly = "";
							document.getElementById(txtId4).style.background = "#ffff88";
						} else {
							document.getElementById(txtId4).readOnly = "readonly";
							// MOD 2013/12/24 QP@30154 okano start
//								document.getElementById(txtId4).style.background = "#ffffff";
							document.getElementById(txtId4).style.background = "#c0c0c0";
							// MOD 2013/12/24 QP@30154 okano end
						}
						// ADD 2013/11/1 okano【QP@30154】end
				    }
//MOD 2013/07/11 ogawa 【QP@30151】No.13 end
				}else{
//MOD 2013/07/11 ogawa 【QP@30151】No.13 start
//修正前ソース
//					parent.detail.document.frm00.txtId1.readOnly = "readonly";
//					parent.detail.document.frm00.txtId2.readOnly = "";
					// テキストボックス色切り替え
//					parent.detail.document.frm00.txtId1.style.background = "#ffffff";
//					parent.detail.document.frm00.txtId2.style.background = "#ffff88";
//修正後ソース
					parent.detail.document.frm00.txtId1.readOnly = "readonly";
					// テキストボックス色切り替え
					parent.detail.document.frm00.txtId1.style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】start
					parent.detail.document.frm00.txtId3.readOnly = "readonly";
					// テキストボックス色切り替え
					parent.detail.document.frm00.txtId3.style.background = "#ffffff";
					// ADD 2013/11/1 okano【QP@30154】end
					if (detailDoc.getElementById("chkKoumoku_"+ i).checked) {
						parent.detail.document.frm00.txtId2.readOnly = "readonly";
						parent.detail.document.frm00.txtId2.style.background = "#c0c0c0";
						// ADD 2013/11/1 okano【QP@30154】start
						parent.detail.document.frm00.txtId4.readOnly = "readonly";
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
//								parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
//							} else{
//								parent.detail.document.frm00.txtId4.style.background = "#ffffff";
//							}
						parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
						// MOD 2013/12/24 QP@30154 okano end
						// ADD 2013/11/1 okano【QP@30154】end
				    }else{
						parent.detail.document.frm00.txtId2.readOnly = "";
						parent.detail.document.frm00.txtId2.style.background = "#ffff88";
						// ADD 2013/11/1 okano【QP@30154】start
						// MOD 2013/12/24 QP@30154 okano start
//							if(headerFrm.hdnBusho_kojo.value != "1"){
						if(cd_kaisha != "1"){
						// MOD 2013/12/24 QP@30154 okano end
							parent.detail.document.frm00.txtId4.readOnly = "";
							parent.detail.document.frm00.txtId4.style.background = "#ffff88";
						} else {
							parent.detail.document.frm00.txtId4.readOnly = "readonly";
							// MOD 2013/12/24 QP@30154 okano start
//								parent.detail.document.frm00.txtId4.style.background = "#ffffff";
							parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
							// MOD 2013/12/24 QP@30154 okano end
						}
						// ADD 2013/11/1 okano【QP@30154】end
				    }
//MOD 2013/07/11 ogawa 【QP@30151】No.13 end
				}
			}
			//【H24年度対応No15】2012/04/18 kazama mod end
		}
	}
	// 【QP@10713】20111117 hagiwara mod end
}

//ADD 2013/07/11 ogawa 【QP@30151】No.13 start
//========================================================================================
//【QP@30151】No13 項目固定チェック切り替え処理
//作成者：F.ogawa
//作成日：2013/07/11
//更新者：
//更新日：
//引数  ：一覧の項番
//概要  ：入力可不可の変更を行う
//========================================================================================
function funKChangeMode(changeCnt) {

    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

	//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 start
	//ﾍｯﾀﾞｰﾌｫｰﾑ参照 & 生産管理部と営業のステータス取得
    var headerFrm = parent.header.document.frm00;
	var st_seikan = headerFrm.hdnStatus_seikan.value;
	var st_eigyo   = headerFrm.hdnStatus_eigyo.value;

	//再計算ボタンを編集可に設定
    headerFrm.btnSaikeisan.disabled = false;

	//20160607 【KPX@1502111_No8】 ADD start
    //工場ステータス取得
    var st_kojo    = headerFrm.hdnStatus_kojo.value;
    //部署取得
    var kojo = headerFrm.hdnBusho_kojo.value;

	// 部署：工場で確認完了(工場ステータス≧2)の場合
    if( kojo == "1" && st_kojo >= 2 ){
		return true;
	}
	//20160607【KPX@1502111_No8】 ADD end

	//生産管理部の確認完了以降の場合は、項目編集せずに処理終了
	if( ( st_seikan >= 3 ) || (st_eigyo >= 4)){
		return true;
	}
	//ADD 2014/01/15 nishigawa 【QP@30154】追加課題No.11 end

    //試算中止
    var fg_chusi = detailDoc.getElementById("hdnSisanChusi_"+changeCnt).value;
    // ADD 2013/12/24 QP@30154 okano start
    var cd_kaisha = funXmlRead(xmlUSERINFO_O, "cd_kaisha", 0);
    // ADD 2013/12/24 QP@30154 okano end
    // ID作成
    txtId1 = "txtCaseKotei_" + changeCnt;    //固定費/ケース
    txtId2 = "txtKgKotei_" + changeCnt;      //固定費/㎏
    txtId3 = "txtYukoBudomari_" + changeCnt; //有効歩留（％）
    txtId4 = "txtHeikinZyu_" + changeCnt;    //平均充填量（ｇ）
	// ADD 2013/11/1 okano【QP@30154】start
    txtId5 = "txtCaseRieki_" + changeCnt;    //利益/ケース
    txtId6 = "txtKgRieki_" + changeCnt;      //利益/㎏
	// ADD 2013/11/1 okano【QP@30154】end
	// ADD 2014/8/7 shima【QP@30154】No.63 start
    txtId7 = "txtSeizoRot" + changeCnt;      //製造ロット
    // ADD 2014/8/7 shima【QP@30154】No.63 end

    //項目固定チェック ON時処理
    if (detailDoc.getElementById("chkKoumoku_"+ changeCnt).checked) {
		// readOnly切り替え
//		if(document.frm00.radioKoteihi != undefined){
			document.getElementById(txtId1).readOnly = "readonly";
			document.getElementById(txtId2).readOnly = "readonly";
			document.getElementById(txtId3).readOnly = "readonly";
			document.getElementById(txtId4).readOnly = "readonly";
			document.getElementById(txtId3).style.background = "#c0c0c0";
			document.getElementById(txtId4).style.background = "#c0c0c0";
			// ADD 2013/11/1 okano【QP@30154】start
			document.getElementById(txtId5).readOnly = "readonly";
			document.getElementById(txtId6).readOnly = "readonly";
			// ADD 2013/11/1 okano【QP@30154】end
			// ADD 2014/8/7 shima【QP@30154】No.63 start
			document.getElementById(txtId7).readOnly = "readonly";
			document.getElementById(txtId7).style.background = "#ffffff";
			// ADD 2014/8/7 shima【QP@30154】No.63 end

			//固定費/ｹｰｽの場合
			if(detailFrm.radioKoteihi[0].checked == true){
    			document.getElementById(txtId1).style.background = "#c0c0c0";
    			document.getElementById(txtId2).style.background = "#ffffff";
    			// ADD 2013/11/1 okano【QP@30154】start
    			document.getElementById(txtId5).style.background = "#c0c0c0";
    			document.getElementById(txtId6).style.background = "#ffffff";
    			// ADD 2013/11/1 okano【QP@30154】end
	    	//固定費/kgの場合
    		}else{
				document.getElementById(txtId1).style.background = "#ffffff";
				document.getElementById(txtId2).style.background = "#c0c0c0";
				// ADD 2013/11/1 okano【QP@30154】start
				document.getElementById(txtId5).style.background = "#ffffff";
				document.getElementById(txtId6).style.background = "#c0c0c0";
				// ADD 2013/11/1 okano【QP@30154】end
    		}
//    	}
//    	else {
//    		parent.detail.document.frm00.txtId1.readOnly = "readonly";
//			parent.detail.document.frm00.txtId2.readOnly = "readonly";
//			parent.detail.document.frm00.txtId3.readOnly = "readonly";
//			parent.detail.document.frm00.txtId4.readOnly = "readonly";
//			parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
//			parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
//			//固定費/ｹｰｽの場合
//			if(detailFrm.radioKoteihi[0].checked == true){
//    			// テキストボックス色切り替え
//    			parent.detail.document.frm00.txtId1.style.background = "#c0c0c0";
//    			parent.detail.document.frm00.txtId2.style.background = "#ffffff";
//        	    	//固定費/kgの場合
//    		}else{
//				// テキストボックス色切り替え
//				parent.detail.document.frm00.txtId1.style.background = "#ffffff";
//				parent.detail.document.frm00.txtId2.style.background = "#c0c0c0";
//   			}
//   		}
        //項目固定チェック OFF時処理
    }else{
    	//固定費/ｹｰｽの場合
    	if(detailFrm.radioKoteihi[0].checked == true){
    		//計算中止時
    		if(fg_chusi == 1){
    			// readOnly切り替え
//    			if(document.frm00.radioKoteihi != undefined){
    				document.getElementById(txtId1).readOnly = "";
    				document.getElementById(txtId2).readOnly = "readonly";
    				document.getElementById(txtId3).readOnly = "";
    				document.getElementById(txtId4).readOnly = "";
    				document.getElementById(txtId1).style.background = "#c0c0c0";
    				document.getElementById(txtId2).style.background = "#ffffff";
    				document.getElementById(txtId3).style.background = "#c0c0c0";
    				document.getElementById(txtId4).style.background = "#c0c0c0";
    				// ADD 2014/8/7 shima【QP@30154】No.63 start
    				document.getElementById(txtId7).readOnly = "";
    				document.getElementById(txtId7).style.background = "#c0c0c0";
    				// ADD 2014/8/7 shima【QP@30154】No.63 end

    				// ADD 2013/11/1 okano【QP@30154】start
    				// ADD 2013/12/24 QP@30154 okano start
    				if(cd_kaisha != "1"){
    				// ADD 2013/12/24 QP@30154 okano end
    				document.getElementById(txtId5).readOnly = "";
    				document.getElementById(txtId6).readOnly = "readonly";
    				// ADD 2013/12/24 QP@30154 okano start
    				}
    				// ADD 2013/12/24 QP@30154 okano end
    				document.getElementById(txtId5).style.background = "#c0c0c0";
    				document.getElementById(txtId6).style.background = "#ffffff";
    				// ADD 2013/11/1 okano【QP@30154】end
//    			}else{
//    				parent.detail.document.frm00.txtId1.readOnly = "";
//    				parent.detail.document.frm00.txtId2.readOnly = "readonly";
//    				parent.detail.document.frm00.txtId3.readOnly = "";
//    				parent.detail.document.frm00.txtId4.readOnly = "";
//    				// テキストボックス色切り替え
//    				parent.detail.document.frm00.txtId1.style.background = "#c0c0c0";
//    				parent.detail.document.frm00.txtId2.style.background = "#ffffff";
//    				parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
//    				parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
//    			}
    		}else{
    			// readOnly切り替え
//    			if(document.frm00.radioKoteihi != undefined){
    				document.getElementById(txtId1).readOnly = "";
    				document.getElementById(txtId2).readOnly = "readonly";
    				document.getElementById(txtId3).readOnly = "";
    				document.getElementById(txtId4).readOnly = "";
    				document.getElementById(txtId1).style.background = "#ffff88";
    				document.getElementById(txtId2).style.background = "#ffffff";
    				document.getElementById(txtId3).style.background = "#ffff88";
    				document.getElementById(txtId4).style.background = "#ffff88";
    				// ADD 2013/11/1 okano【QP@30154】start
    				// ADD 2013/12/24 QP@30154 okano start
    				if(cd_kaisha != "1"){
    				// ADD 2013/12/24 QP@30154 okano end
    				document.getElementById(txtId5).readOnly = "";
    				document.getElementById(txtId6).readOnly = "readonly";
    				document.getElementById(txtId5).style.background = "#ffff88";
    				document.getElementById(txtId6).style.background = "#ffffff";
    				// ADD 2013/12/24 QP@30154 okano start
    				} else {
        				document.getElementById(txtId5).style.background = "#c0c0c0";
        				document.getElementById(txtId6).style.background = "#ffffff";
    				}
    				// ADD 2013/12/24 QP@30154 okano end
    				// ADD 2013/11/1 okano【QP@30154】end
    				// ADD 2014/8/7 shima【QP@30154】No.63 start
    				document.getElementById(txtId7).readOnly = "";
    				document.getElementById(txtId7).style.background = color_henshu;
    				// ADD 2014/8/7 shima【QP@30154】No.63 end

//    			}else{
//    				parent.detail.document.frm00.txtId1.readOnly = "";
//    				parent.detail.document.frm00.txtId2.readOnly = "readonly";
//    				parent.detail.document.frm00.txtId3.readOnly = "";
//    				parent.detail.document.frm00.txtId4.readOnly = "";
//    				// テキストボックス色切り替え
//    				parent.detail.document.frm00.txtId1.style.background = "#ffff88";
//    				parent.detail.document.frm00.txtId2.style.background = "#ffffff";
//    				parent.detail.document.frm00.txtId3.style.background = "#ffff88";
//    				parent.detail.document.frm00.txtId4.style.background = "#ffff88";
//    			}
    		}
    	}
    	//固定費/kgの場合
    	else {
       		//計算中止時
    		if(fg_chusi == 1){
//    			if(document.frm00.radioKoteihi != undefined){
    				document.getElementById(txtId1).readOnly = "readonly";
    				document.getElementById(txtId2).readOnly = "";
    				document.getElementById(txtId3).readOnly = "";
    				document.getElementById(txtId4).readOnly = "";
    				document.getElementById(txtId1).style.background = "#ffffff";
    				document.getElementById(txtId2).style.background = "#c0c0c0";
    				document.getElementById(txtId3).style.background = "#c0c0c0";
    				document.getElementById(txtId4).style.background = "#c0c0c0";
    				// ADD 2013/11/1 okano【QP@30154】start
    				// ADD 2013/12/24 QP@30154 okano start
    				if(cd_kaisha != "1"){
    				// ADD 2013/12/24 QP@30154 okano end
    				document.getElementById(txtId5).readOnly = "readonly";
    				document.getElementById(txtId6).readOnly = "";
    				// ADD 2013/12/24 QP@30154 okano start
    				}
    				// ADD 2013/12/24 QP@30154 okano end
    				document.getElementById(txtId5).style.background = "#ffffff";
    				document.getElementById(txtId6).style.background = "#c0c0c0";
    				// ADD 2013/11/1 okano【QP@30154】end
    				// ADD 2014/8/7 shima【QP@30154】No.63 start
    				document.getElementById(txtId7).readOnly = "";
    				document.getElementById(txtId7).style.background = "#c0c0c0";
    				// ADD 2014/8/7 shima【QP@30154】No.63 end

//    			}else{
//    				parent.detail.document.frm00.txtId1.readOnly = "readonly";
//    				parent.detail.document.frm00.txtId2.readOnly = "";
//    				parent.detail.document.frm00.txtId3.readOnly = "";
//    				parent.detail.document.frm00.txtId4.readOnly = "";
//    				// テキストボックス色切り替え
//    				parent.detail.document.frm00.txtId1.style.background = "#ffffff";
//    				parent.detail.document.frm00.txtId2.style.background = "#c0c0c0";
//    				parent.detail.document.frm00.txtId3.style.background = "#c0c0c0";
//    				parent.detail.document.frm00.txtId4.style.background = "#c0c0c0";
//    			}
    		}else{
//    			if(document.frm00.radioKoteihi != undefined){
   					document.getElementById(txtId1).readOnly = "readonly";
   					document.getElementById(txtId2).readOnly = "";
   					document.getElementById(txtId3).readOnly = "";
   					document.getElementById(txtId4).readOnly = "";
   					document.getElementById(txtId1).style.background = "#ffffff";
   					document.getElementById(txtId2).style.background = "#ffff88";
   					document.getElementById(txtId3).style.background = "#ffff88";
   					document.getElementById(txtId4).style.background = "#ffff88";
   					// ADD 2013/11/1 okano【QP@30154】start
   					// ADD 2013/12/24 QP@30154 okano start
    				if(cd_kaisha != "1"){
   					// ADD 2013/12/24 QP@30154 okano end
   					document.getElementById(txtId5).readOnly = "readonly";
   					document.getElementById(txtId6).readOnly = "";
   					document.getElementById(txtId5).style.background = "#ffffff";
   					document.getElementById(txtId6).style.background = "#ffff88";
   					// ADD 2013/12/24 QP@30154 okano start
    				} else {
       					document.getElementById(txtId5).style.background = "#ffffff";
       					document.getElementById(txtId6).style.background = "#c0c0c0";
    				}
   					// ADD 2013/12/24 QP@30154 okano end
   					// ADD 2013/11/1 okano【QP@30154】end
					// ADD 2014/8/7 shima【QP@30154】No.63 start
    				document.getElementById(txtId7).readOnly = "";
    				document.getElementById(txtId7).style.background = color_henshu;
    				// ADD 2014/8/7 shima【QP@30154】No.63 end

//   				}else{
//   					parent.detail.document.frm00.txtId1.readOnly = "readonly";
//   					parent.detail.document.frm00.txtId2.readOnly = "";
//   					parent.detail.document.frm00.txtId3.readOnly = "";
//   					parent.detail.document.frm00.txtId4.readOnly = "";
//  					// テキストボックス色切り替え
//   					parent.detail.document.frm00.txtId1.style.background = "#ffffff";
//   					parent.detail.document.frm00.txtId2.style.background = "#ffff88";
//   					parent.detail.document.frm00.txtId3.style.background = "#ffff88";
//   					parent.detail.document.frm00.txtId4.style.background = "#ffff88";
//   				}
   			}
   		}
   	}
}
//ADD 2013/07/11 ogawa 【QP@30151】No.13 end

//========================================================================================
//【QP@10713】行選択時のハイライト処理
//作成者：Ryo.Hagiwara
//作成日：2011/11/01
//更新者：
//更新日：
//引数  ：選択DOM
//概要  ：行選択時にその行の背景色を変更する
//========================================================================================
var defaultColor;
var tempId = "";

function funLineHighLight(element){
	if(tempId != ""){
		//【H24年度対応】2012/04/25 kazama add start
		var focusColor = "";		//ハイライト色を比較するための変数

		//クリック元の背景色を保持
		focusColor = Element.getStyle(tempId.slice(0, tempId.length-1) + 0, "background-color");
		//ハイライトカラーでない
		if(focusColor != "#7fffd4"){
			//列の最後までループ
			for(var i = 0; i < defaultColor.length; i++){
				//デフォルトにクリック元の背景色を保持
				defaultColor[i] = Element.getStyle(tempId.slice(0, tempId.length-1) + i, "background-color");
			}
		}
		//【H24年度対応】2012/04/25 kazama add end

		for(var i = 0; i < defaultColor.length; i++){
			Element.setStyle(tempId.slice(0, tempId.length-1) + i, {
				'background-color':defaultColor[i]
			});
		}
	}
	defaultColor = new Array(cnt_sample);
	var id = element.id;
	tempId = id;
	var num = parseInt(id.slice(-1));

	for(var i = num; i < cnt_sample; i++){
		defaultColor[i] = Element.getStyle(id.slice(0, id.length-1) + i, "background-color");
	}
	for(var i = num-1; i >= 0; i--){
		defaultColor[i] = Element.getStyle(id.slice(0, id.length-1) + i, "background-color");
	}

	for(var i = 0; i < defaultColor.length; i++){
		Element.setStyle(id.slice(0, id.length-1) + i, {
			'background-color':'#7FFFD4'
		});
	}
}

//========================================================================================
//【シサクイックH24年度対応】採用サンプルNo以外グレー処理
//作成者：H.SHIMA
//作成日：2012/04/13
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
		if(saiyoColumn != arrSampleSeq[i]){
			document.getElementById("txtSample" + i).style.background = gray;			//サンプルNo
			document.getElementById("txtSeizoHan_" + i).style.background = gray;		//製造工程版
			document.getElementById("txtShisanHi_" + i).style.background = gray;		//試算日
			document.getElementById("txtSuiZyuten_" + i).style.background = gray;		//充填量水相(g)
			document.getElementById("txtYuZyuten_" + i).style.background = gray;		//充填量油相(g)
			document.getElementById("total" + i).style.background = gray;				//合計量(g)
			document.getElementById("hijyu" + i).style.background = gray;				//比重
			document.getElementById("txtYukoBudomari_" + i).style.background = gray;	//有効歩留(%)
			document.getElementById("txtLebelRyo_" + i).style.background = gray;		//レベル量(kg)
			document.getElementById("txtHeikinZyu_" + i).style.background = gray;		//平均重点量(kg)
			document.getElementById("hijyuKasan" + i).style.background = gray;			//比重加算量(kg)
			document.getElementById("genryohi" + i).style.background = gray;			//原料費(円)/ケース
			document.getElementById("zairyohi" + i).style.background = gray;			//材料費(円)/ケース
			document.getElementById("txtCaseKotei_" + i).style.background = gray;		//固定費(円)/ケース
			document.getElementById("genkakei" + i).style.background = gray;			//原価計(円)/ケース
			document.getElementById("sankouKo" + i).style.background = gray;			//《参考：個あたり》
			document.getElementById("genkakeiKo" + i).style.background = gray;			//原価計(円)/個
			document.getElementById("sankouKG" + i).style.background = gray;			//《参考：KGあたり》
			document.getElementById("genryohiKG" + i).style.background = gray;			//原料費(円)/kg
			document.getElementById("zairyohiKG" + i).style.background = gray;			//材料費(円)/kg
			document.getElementById("txtKgKotei_" + i).style.background = gray;			//固定費(円)/kg
			document.getElementById("txtKgGenkake_" + i).style.background = gray;		//原価計(円)/kg
			document.getElementById("baika" + i).style.background = gray;				//売価
			document.getElementById("sori" + i).style.background = gray;				//粗利(%)
			// ADD 2013/11/1 QP@30154 okano start
			document.getElementById("txtCaseRieki_" + i).style.background = gray;		//利益(円)/ケース
			document.getElementById("txtKgRieki_" + i).style.background = gray;			//利益(円)/kg
			// ADD 2013/11/1 QP@30154 okano end
		}
	}
}

//========================================================================================
//【シサクイックH24年度対応】原料情報テーブル項目非表示
//作成者：H.SHIMA
//作成日：2012/04/20
//更新者：
//更新日：
//引数  ：①xmlData ：設定XML
//戻り値：なし
//概要  ：原料情報左の原料の値が空白、もしくは0の場合は非表示
//========================================================================================
function funGenryo_DispDecision(xmlData){

	//行を表示するかの判定フラグ配列
	var rowDispflg = new Array();
	//工程行数保持配列
	var koteiArray = new Array();

	//テーブル名設定
	tablekihonNm = "kihon";
	tableHaigoNm = "shisaku";

	//列数取得
	cnt_sample = funXmlRead_3(xmlData, tablekihonNm, "cnt_sanpuru", 0, 0);
	//行数取得
	cnt_genryo = funXmlRead_3(xmlData, tablekihonNm, "cnt_genryo", 0, 0);

	var kotei_cont = 0;

	//行ループ
	for(var i = 0; i < cnt_genryo; i++){

		var atai = funXmlRead_3(xmlData, tableHaigoNm+0, "sort", 0, i+2);

		//行に値が入っているか確認用フラグ
		var nullflg  = false;

		//値の確認
		if(atai == "" || atai == undefined){
			//次の行のソート番号を見る
			var next = funXmlRead_3(xmlData, tableHaigoNm+0, "sort", 0, i+3);
			//値が入っていれば
			if(next != "" && next != undefined){			//■[工程]
				//工程行数保持
				koteiArray[kotei_cont] = i;
				kotei_cont++;
				rowDispflg[i] = "";

			//値が入っていなければ工程項目ではない
			}else{
				if(koteiArray[koteiArray.length-1] != "EOF"){//■[工程計開始]
					//取得工程行の終りを設定
					koteiArray[kotei_cont] = "EOF";
					//カウントをリセット
					kotei_cont = 0;

				//工程合計項目後か
				}else if(koteiArray[kotei_cont] == "EOF"){	//■[工程計終了]
					rowDispflg[i] = true;
				}

				if(koteiArray[kotei_cont] != "EOF"){		//■[工程合計]
					var koteirow = parseInt(koteiArray[kotei_cont]);
					nullflg = false;
					//工程の原料のフラグチェック
					for(k = eval(koteirow + 1);;k++){
						//原料項目ではない
						if(rowDispflg[k] == ""){
							break;
						//原料項目がtrueならば
						}else if(rowDispflg[k] == true){
							nullflg=true;
							break;
						}
						//取得のエラー
						if(rowDispflg[koteirow + k] == undefined){
							return;
						}
					}
					//フラグ設定
					rowDispflg[eval(koteirow)] = nullflg;
					//工程合計行にフラグ設定
					rowDispflg[i] = nullflg;
					kotei_cont++;
				}
			}
		}else{												//■[原料]
			//列ループ
			for(j = 0; j < cnt_sample && !nullflg ; j++){

				//XMLデータ取得
				haigo  = funXmlRead_3(xmlData, tableHaigoNm+j, "haigo", 0, i+2);
				kingaku = funXmlRead_3(xmlData, tableHaigoNm+j, "kingaku", 0, i+2);

				//配合に値が入っている かつ 0でない
				if(haigo != "" && haigo != 0){
					nullflg = true;
				}
			}
			rowDispflg[i] = nullflg;
		}
	}

	//初期表示時か
	if(document.frm00.btnSaikeisan == undefined){
		//テーブル非表示処理
		for(i = 0; i < rowDispflg.length ; i++){
			if(!rowDispflg[i]){
				//原料情報テーブル左非表示
				document.getElementById("tableRowL_"+i).style.display = "none";
				//原料情報テーブル右非表示
				document.getElementById("tableRowR_"+i).style.display = "none";
			}
		}
	}else{
		//テーブル非表示処理
		for(i = 0; i < rowDispflg.length ; i++){
			if(!rowDispflg[i]){
				parent.detail.document.getElementById("tableRowR_"+i).style.display = "none";
			}
		}
	}

}
//【QP@30297】add start 20140501
//========================================================================================
//権限関連処理
//作成者：Y.Nishigawa
//作成日：2009/10/23
//概要  ：権限情報の退避、画面制御を行う
//========================================================================================
function funSaveKengenInfo2() {

  var obj = xmlUSERINFO_O;
  var GamenId;
  var KinoId;
  var reccnt;
  var i;

  //権限ループ
  reccnt = funGetLength(obj);
  for (i = 0; i < reccnt; i++) {

      GamenId = funXmlRead(obj, "id_gamen", i);

      //コストテーブル参照画面
      if (GamenId.toString() == ConGmnIdCostTblRef.toString()) {
          document.getElementById("btnCostTblRef").style.visibility = "visible";
      }
  }

  return true;
}

//========================================================================================
//コスト参照ボタン押下
//========================================================================================
function funCostTblRef() {

  window.open("../SQ200CostTblRef/SQ200CostTblRef.jsp","_blank","menubar=no,resizable=yes");

  return true;

}
//【QP@30297】add end 20140501

//ADD 2015/05/15 TT.Kitazawa【QP@40812】No.6 start
//========================================================================================
// メール送信ボタン押下
// 作成者：E.Kitazawa
// 作成日：2015/03/03
// 概要  ：メール送信画面を表示
//========================================================================================
function funMail(){
    //ﾍｯﾀﾞﾌﾚｰﾑのﾌｫｰﾑ参照
    var headerFrm = document.frm00;
    var mode = 1;		// セッションなし

 // 20150831 TT.Kitazawa del start （ステータス情報の再取得不要）
 /*
    // ステータス情報が再取得前の為、最新のステータスを取得する
    var XmlId = "RGEN2020";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2020");
    var xmlReqAry = new Array(xmlUSERINFO_I , xmlFGEN2020I );
    var xmlResAry = new Array(xmlRESULT , xmlUSERINFO_O , xmlFGEN2020O );


    //------------------------------------------------------------------------------------
    //                                  ステータス情報取得
    //------------------------------------------------------------------------------------
    // XMLの初期化
    setTimeout("xmlFGEN2020I.src = '../../model/FGEN2020I.xml';", ConTimer);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, mode) == false) {
        funClearRunMessage2();
        return false;
    }

    //ｾｯｼｮﾝ情報、共通情報を取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN0010, xmlReqAry, xmlResAry, mode) == false) {
        return false;
    }

*/
    //現在のステータス情報
    var stnew_ary = new Array(5);
//  処理前のステータスい情報の為、ＤＢより再取得
//     stnew_ary[0] = funStatusSetting("1" ,headerFrm.hdnStatus_kenkyu.value);
//    stnew_ary[1] = funStatusSetting("2", headerFrm.hdnStatus_seikan.value);
//    stnew_ary[2] = funStatusSetting("3", headerFrm.hdnStatus_gentyo.value);
//    stnew_ary[3] = funStatusSetting("4", headerFrm.hdnStatus_kojo.value);
//    stnew_ary[4] = funStatusSetting("5", headerFrm.hdnStatus_eigyo.value);
/*
    stnew_ary[0] = funStatusSetting("1", funXmlRead_3(xmlResAry[2], "kihon", "st_kenkyu", 0, 0));
    stnew_ary[1] = funStatusSetting("2", funXmlRead_3(xmlResAry[2], "kihon", "st_seisan", 0, 0));
    stnew_ary[2] = funStatusSetting("3", funXmlRead_3(xmlResAry[2], "kihon", "st_gensizai", 0, 0));
    stnew_ary[3] = funStatusSetting("4", funXmlRead_3(xmlResAry[2], "kihon", "st_kojo", 0, 0));
    stnew_ary[4] = funStatusSetting("5", funXmlRead_3(xmlResAry[2], "kihon", "st_eigyo", 0, 0));
*/
    //試作情報
    // 20150826 TT.Kitazawa mod start
//    var sisaku_ary = new Array(3);
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

//20160513  KPX@1600766 ADD start
//========================================================================================
// リテラル情報取得処理
// 作成者：E.Kitazawa
// 作成日：2016/04/18
// 引数  ：なし
// 戻り値：すべて開示:9、単価開示:1、単価開示不可：0
// 概要  ：グループ会社単価開示フラグの取得を行う
//========================================================================================
function funGetTankaFlg() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "JSP0630";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"SA100");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlSA100I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlSA100O);

    // 戻り値の設定（単価開示不可）
    var ret = "0";

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return ret;
    }

    //検索条件に一致するﾘﾃﾗﾙﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJSP0630, xmlReqAry, xmlResAry, 1) == false) {
    	return ret;
    }

    ret = funXmlRead(xmlResAry[2], "value1", 0);
    //会社コードが登録されていない時
    if (ret == "") ret = "0";

    return ret;

}
//20160513  KPX@1600766 ADD end
//20160617  KPX@1502111_No.5 ADD start
//========================================================================================
// 自家原料連携情報取得・更新処理
// 作成者：E.Kitazawa
// 作成日：2016/06/17
// 引数  ：なし
// 戻り値：
// 概要  ：連携している自家原料より、試算配合データの単価更新を行う
//         工場：＜完了(工場ステータス＜2)、生管：＜完了(生管ステータス＜2)
//========================================================================================
function funRenkeiGenryo() {

    var headerFrm = parent.header.document.frm00;
    var XmlId = "RGEN2280";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2280");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2280I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2280O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
    	return false;
    }

    //検索条件に一致するﾘﾃﾗﾙﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2280, xmlReqAry, xmlResAry, 1) == false) {
    	return false;
    }

    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
    //自家原料連携データあり

        //件数取得
        var reccnt = funGetLength(xmlResAry[2]);
        //連携チェック用
        headerFrm.hdnRenkeiStatus_eigyo.value = "";
        //連携先営業ステータス
        var st_eigyo = "";
        for (var i=0 ; i<reccnt; i++) {
        	//連携先営業ステータス
        	st_eigyo = funXmlRead_3(xmlResAry[2], "table", "st_eigyo", 0, i);
        	//試算配合更新状況
        	chkKosin = funXmlRead_3(xmlResAry[2], "table", "chkKosin", 0, i);
        	//営業３以上、試算配合を更新時、再計算フラグon
        	if (st_eigyo >= 3) {
        		if (chkKosin == "1") {
        			//再計算フラグをonにする
        			setFg_saikeisan();
        		}
        	} else {
        		//工場：ステータスを完了にする時、又は生管：ステータスを完了にする時
        		//　営業＜３の連携原料があればエラー
        		headerFrm.hdnRenkeiStatus_eigyo.value = st_eigyo;
        	}
        }
    }

    return true;

}

//========================================================================================
// 試算中止確認処理
// 作成者：E.Kitazawa
// 作成日：2016/06/17
// 引数  ：なし
// 戻り値：
// 概要  ：連携登録しているサンプルの試作シーケンスを取得する
//========================================================================================
function chkHaigoLink() {

    var detailDoc = parent.detail.document; //明細ﾌﾚｰﾑのDocument参照
    var detailFrm = detailDoc.frm00;        //明細ﾌﾚｰﾑのﾌｫｰﾑ参照

    var XmlId = "JW821";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2260");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2260I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2260O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }

    //検索条件に一致するﾘﾃﾗﾙﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlJW821, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    detailFrm.hdnRenkeiSeqShisaku.value = "";
    if (funXmlRead(xmlResAry[2], "flg_return", 0) == "true") {
    	//連携登録している試作シーケンスを保存
    	detailFrm.hdnRenkeiSeqShisaku.value = funXmlRead_3(xmlResAry[2], "table", "seq_shisaku", 0, 0);
    }

    return true;

}
//20160617  KPX@1502111_No.5 ADD end
//20160622  KPX@1502111_No.10 ADD start
//========================================================================================
// サンプルコピー処理
// 作成者：E.Kitazawa
// 作成日：2016/06/22
// 引数  ：なし
// 戻り値：
// 概要  ：サンプルコピー指定画面を表示
//========================================================================================
function funSampleCopy() {

    var headerFrm = parent.header.document.frm00;
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
		sample = detailDoc.getElementById("txtSample"+j).value;
		// コピー先に含めるかどうかのフラグ
		chkSaki = "0";
//20160826 KPX@1502111_No.10 ADD Start
		// コピー元に含めるかどうかのフラグ
		chkMoto = "0";
//20160826 KPX@1502111_No.10 ADD End
		// 試算中止
		if (detailDoc.getElementById("hdnSisanChusi_"+j).value == "1") {
			chkSaki = "1";
		}
		// 項目固定チェックがON
		if (detailDoc.getElementById("chkKoumoku_"+ j).checked) {
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
			//chkCnt = chkCnt + 1;
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

    			//再計算フラグをonにする
    			headerFrm.FgSaikeisan.value = "true";
    		}

    	} else {
			//コピー実行：retVal[0] →  retVal[1]
    		funSampCpy(retVal[0], retVal[1], 2);

			//再計算フラグをonにする
			headerFrm.FgSaikeisan.value = "true";
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
	var no;

	if(mode == 0) {
		no = motoNo;
	} else if(mode == 1) {
		no = sakiNo;
	} else if(mode == 2) {
		//有効歩留（％）
		detailDoc.getElementById("txtYukoBudomari_"+sakiNo).value = detailDoc.getElementById("txtYukoBudomari_"+motoNo).value;
		//平均充填量（ｇ）
		detailDoc.getElementById("txtHeikinZyu_"+sakiNo).value = detailDoc.getElementById("txtHeikinZyu_"+motoNo).value;

		if (document.frm00.radioKoteihi[0].checked){
			//固定費/ケース
			detailDoc.getElementById("txtCaseKotei_"+sakiNo).value = detailDoc.getElementById("txtCaseKotei_"+motoNo).value;
			//利益/ケース
			detailDoc.getElementById("txtCaseRieki_"+sakiNo).value = detailDoc.getElementById("txtCaseRieki_"+motoNo).value;

		}else{
			//固定費/KG
			detailDoc.getElementById("txtKgKotei_"+sakiNo).value = detailDoc.getElementById("txtKgKotei_"+motoNo).value;
			//利益/KG
			detailDoc.getElementById("txtKgRieki_"+sakiNo).value = detailDoc.getElementById("txtKgRieki_"+motoNo).value;
		}
		return true;

	} else {
		return false;
	}


	//編集データに数値が入っているかどうかチェックする
	//有効歩留（％）
	if (detailDoc.getElementById("txtYukoBudomari_"+no).value != "") {
		return false;
	}
	//平均充填量（ｇ）
	if (detailDoc.getElementById("txtHeikinZyu_"+no).value != "") {
		return false;
	}
	if (document.frm00.radioKoteihi[0].checked){
		//固定費/ケース
		if (detailDoc.getElementById("txtCaseKotei_"+no).value != "") {
			return false;
		}
		//利益/ケース
		if (detailDoc.getElementById("txtCaseRieki_"+no).value != "") {
			return false;
		}
	} else {
		//固定費/KG
		if (detailDoc.getElementById("txtKgKotei_"+no).value != "") {
			return false;
		}
		//利益/KG
		if (detailDoc.getElementById("txtKgRieki_"+no).value != "") {
			return false;
		}
	}
	return true;
}
//20160622  KPX@1502111_No.10 ADD end

//20170515 KPX@1700856 ADD Start
//========================================================================================
//単価0円許可情報取得処理
//作成者：Shima
//作成日：2017/05/12
//引数  ：なし
//戻り値：単価0円許可原料コード
//概要  ：単価0円許可原料コードの取得を行う
//========================================================================================
function funGetTankaZeroGenryo() {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var XmlId = "RGEN2290";
    var FuncIdAry = new Array(ConResult,ConUserInfo,"FGEN2290");
    var xmlReqAry = new Array(xmlUSERINFO_I,xmlFGEN2290I);
    var xmlResAry = new Array(xmlRESULT,xmlUSERINFO_O,xmlFGEN2290O);

    //引数をXMLﾌｧｲﾙに設定
    if (funReadyOutput(XmlId, xmlReqAry, 1) == false) {
        return false;
    }
    
	//XMLの初期化
	setTimeout("xmlFGEN2290I.src = '../../model/FGEN2290I.xml';", ConTimer);
    //検索条件に一致するﾘﾃﾗﾙﾃﾞｰﾀを取得
    if (funAjaxConnection(XmlId, FuncIdAry, xmlRGEN2290, xmlReqAry, xmlResAry, 1) == false) {
        return false;
    }

    var length = funGetLength(xmlResAry[2]);
    frm.hdnRenkeiSeqShisaku.value = "";
    for(var i = 0;i < length;i++){
    	if(0 < i){
    		frm.hdnTankaZeroGenryo.value += ConDelimiter;
    	}
    	frm.hdnTankaZeroGenryo.value += funXmlRead(xmlResAry[2], "value1", i);
    }

    return true;

}
//20170515 KPX@1700856 ADD End

