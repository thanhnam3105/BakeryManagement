//========================================================================================
// 定数宣言 [Const.js]
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 概要  ：定数の宣言を行う。
//========================================================================================

    //JRE1.4.2インストーラーURL  ★★環境に合わせて変更★★
    //var ConJreInstallUrl = "http://shisaquick/Shisaquick/j2re-1_4_2_19-windows-i586-p.exe";
    // 20120607 hagiwara シサクイックサーバ移行試験の為
    var ConJreInstallUrl = "http://vo-shisaquick:4043/Shisaquick/j2re-1_4_2_19-windows-i586-p.exe";

    //JRE1.5.0インストーラーURL  ★★環境に合わせて変更★★
    //var ConJreInstallUrl_5 = "http://shisaquick/Shisaquick/jre-1_5_0_14-windows-i586-p.exe";

    //JRE1.6.0インストーラーURL  ★★環境に合わせて変更★★
    //var ConJreInstallUrl_6 = "http://shisaquick/Shisaquick/jre-6u31-windows-i586-s.exe";
    // 20120607 hagiwara シサクイックサーバ移行試験の為
    var ConJreInstallUrl_6 = "http://vo-shisaquick:4043/Shisaquick/jre-6u31-windows-i586-s.exe";

    //JavaWebStartのバージョンチェック()
    var ConCheckJWSver = "1.6.0.0:::1.7.0.0";

    //Servlet呼び出し用URLパス(最初と最後のスラッシュは不要)  ★★環境に合わせて変更★★
    var ConUrlPath = "Shisaquick";

    //区切り文字
    var ConDelimiter = ":::";

    //新規原料コードの先頭文字
    var ConGenryoCdPrefix = "N";

//+----------------------------------------------------------------------------
//  システム情報定義
//+----------------------------------------------------------------------------
    //画面ID
    var ConLoginId         = "SQ010";       //ログイン
    var ConMainMenuId      = "SQ020";       //メインメニュー
    var ConMstMenuId       = "SQ030";       //マスタメニュー
    var ConShisakuListId   = "SQ040";       //試作データ一覧
    var ConGenryoInfoMstId = "SQ050";       //原料分析情報マスタメンテナンス
    var ConGenryoInputId   = "SQ051";       //分析値入力
    var ConLiteralMstId    = "SQ060";       //リテラルマスタメンテナンス
    // 【QP@40404】2014/10/14 add start
    var ConGentyoLiteralMstId = "SQ061";    //原資材調達部リテラルマスタメンテナンス
    // 【QP@40404】2014/10/14 add end
    var ConGroupMstId      = "SQ070";       //グループマスタメンテナンス
    var ConTantoMstId      = "SQ080";       //担当者マスタメンテナンス
    var ConTantoSearchId   = "SQ081";       //担当者検索
    var ConKasihaAddId     = "SQ082";       //製造担当会社追加
    var ConKengenMstId     = "SQ090";       //権限マスタメンテナンス
    var ConKengenAddId     = "SQ091";       //権限機能追加
    var ConTankaBudomariId = "SQ100";       //全工場単価歩留
    var GenkaSisanId       = "SQ110";       //原価試算画面
    var RuizihinKensakuId  = "SQ111";       //類似品検索画面
    //【QP@00342】
    var ConGenkaListId   = "SQ120";       //原価試算一覧
    var ConStatusRirekiId  = "SQ140";       //ステータス履歴画面
    var ConStatusClearId   = "SQ130";        //ステータスクリア画面
    var ConEigyoTantoMstId   = "SQ150";        //担当者メンテ（営業）画面
    var ConEigyoTantoSearchId   = "SQ151";       //担当者検索
    // 【QP@30297】
    var ConGenchoMenuId    = "SQ170";       // 原資材調達部メニュー
    var ConCostTblListId   = "SQ180";       // コストテーブル一覧
    var ConCostTblAddId    = "SQ190";       // コストテーブル登録・承認
    var ConCostTblRefId    = "SQ200";       // コストテーブル参照

    // 【QP@40404】2014/09/01 TT E.Kitazawa add start
    var ConDesignSpaceAddId    = "SQ210";       // デザインスペース登録
    var ConShiryoRefId         = "SQ220";       // 参考資料
    var ConShizaiTehaiInputId  = "SQ230";       // 資材手配入力
    var ConDesignSpaceDLId     = "SQ240";       // デザインスペースダウンロード
    var ConShizaiTehaiOutputId = "SQ250";       // 資材手配依頼書出力
    var ConShizaiCodeListId    = "SQ260";       // 複数一括選択
    var ConGentyoLiteralMstId  = "SQ300";       //原資財調達部用リテラルマスタメンテナンス
    // 【QP@40404】2014/09/01 TT E.Kitazawa add end
	// 【KPX@1602367】2016/08/31 May Thu add start
    var ConBasePriceListId = "SQ270"					//ベース単価一覧
    var ConBasePriceAddId  = "SQ280"					//ベース単価登録・承認
	var ConRuiziDataId    = "SQ290";					//類似データ呼出
	var ConHattyuLiteralMstId  = "SQ310"				//発注先マスタ
	var ConShizaiTehaiZumiListId  = "SQ320"				//資材手配済一覧
    var ConSeihinSearchId      = "SQ330";               //製品コードあいまい検索
   // 【KPX@1602367】2016/08/31 May Thu add　end
    /****************************/
    /*    タ イ ト ル 情 報     */
    /****************************/
    //システムID(シサクイック)
    var ConSystemId = "シサクイックシステム(04_0043_0001)　";
    //【QP@00342】
    var ConSystemId_genka = "原価試算システム　";

    //画面ID(タイトル)
    var ConLogin         = ConLoginId + "Login_ログイン";
    var ConMainMenu      = ConMainMenuId + "MainMenu_メインメニュー";
    var ConMstMenu       = ConMstMenuId + "MstMenu_マスタメニュー";
    var ConShisakuList   = ConShisakuListId + "ShisakuList_試作データ一覧";
    var ConGenryoInfoMst = ConGenryoInfoMstId + "GenryoInfoMst_原料分析情報マスタメンテナンス";
    var ConGenryoInput   = ConGenryoInputId + "GenryoInput_分析値入力";
    var ConLiteralMst    = ConLiteralMstId + "LiteralMst_カテゴリマスタメンテナンス";
    var ConGroupMst      = ConGroupMstId + "GroupMst_グループマスタメンテナンス";
    var ConTantoMst      = ConTantoMstId + "TantoMst_担当者マスタメンテナンス";
    var ConTantoSearch   = ConTantoSearchId + "TantoSearch_担当者検索";
    var ConKasihaAdd     = ConKasihaAddId + "KasihaAdd_製造担当会社追加";
    var ConKengenMst     = ConKengenMstId + "KengenMst_権限マスタメンテナンス";
    var ConKengenAdd     = ConKengenAddId + "KengenAdd_権限機能追加";
    var ConTankaBudomari = ConTankaBudomariId + "TankaBudomari_全工場単価歩留";
    //【QP@00342】
    var ConGenkaList = ConGenkaListId + "ShisanList_原価試算一覧";
    var ConStatusRireki = ConStatusRirekiId + "StatusRireki_ステータス履歴";
    var ConStatusClear  = ConStatusClearId + "StatusClear_ステータスクリア";
    var ConEigyoTantoMst  = ConEigyoTantoMstId + "EigyoTantoMst_担当者マスタメンテナンス（営業）";
    var ConEigyoTantoSearch   = ConEigyoTantoSearchId + "EigyoTantoSearch_担当者検索（営業）";
    // 【QP@30297】
    var ConGenchoMenu     = ConGenchoMenuId + "GenchoMenu_原資材調達部メニュー";
    var ConCostTblList    = ConCostTblListId + "CostTblList_コストテーブル一覧";
    var ConCostTblAdd     = ConCostTblAddId + "CostTblAdd_コストテーブル登録・承認";
    var ConCostTblRef     = ConCostTblRefId + "CostTblRef_コストテーブル参照";

    // 【QP@40404】2014/09/01 TT E.Kitazawa add start
    var ConDesignSpaceAdd    = ConDesignSpaceAddId + "DesignSpace_デザインスペース登録";
    var ConShiryoRef         = ConShiryoRefId + "ShiryoRef_参考資料";
    var ConShizaiTehaiInput  = ConShizaiTehaiInputId + "ShizaiTehaiInput_資材手配入力";
    var ConDesignSpaceDL     = ConDesignSpaceDLId + "DesignSpaceDL_デザインスペースダウンロード";
    var ConShizaiTehaiOutput = ConShizaiTehaiOutputId + "ShizaiTehaiOutput_資材手配依頼書出力";
    var ConShizaiCodeList    = ConShizaiCodeListId + "ShizaiCodeList_複数一括選択";
    var ConGentyoLiteralMst  = ConGentyoLiteralMstId + "GentyoLiteralMst_原資材調達部カテゴリマスタメンテナンス";
    // 【QP@40404】2014/09/01 TT E.Kitazawa add end
    // 【KPX@1602367】2016/08/31 May Thu add start
    var ConBasePriceList    = ConBasePriceListId + "BasePriceList_ベース単価一覧";
    var ConBasePriceAdd     = ConBasePriceAddId + "BasePriceAdd_ベース単価登録・承認";
    var ConRuiziData        = ConRuiziDataId + "RuiziData_類似データ呼出";
	var ConHattyuLiteralMst = ConHattyuLiteralMstId + "HattyuLiteralMst_発注先マスタ";
    var ConShizaiTehaiZumiList    = ConShizaiTehaiZumiListId + "ShizaiTehaiZumiList_資材手配済一覧";
    var ConSeihinSearch     = ConSeihinSearchId + "ConSeihinSearch_製品コードあいまい検索ダイアログ";
   // 【KPX@1602367】2016/08/31 May Thu add end

    /****************************/
    /*        権 限 情 報       */
    /****************************/
    //権限(画面ID)
    var ConGmnIdShisakuList     = "10";    //試作データ一覧
    var ConGmnIdGenryoInfoMst   = "20";    //原料分析情報マスタ
    var ConGmnIdGenryoInfoCsv   = "25";    //原料分析情報マスタCSV
    var ConGmnIdGenryoInputNew  = "30";    //分析値入力(新規)
    var ConGmnIdGenryoInputUpd  = "40";    //分析値入力(詳細)
    var ConGmnIdTankaBudomari   = "50";    //全工場単価歩留
    var ConGmnIdLiteralMst      = "60";    //リテラルマスタ
    var ConGmnIdLiteralCsv      = "65";    //リテラルマスタCSV
    var ConGmnIdGroupMst        = "70";    //グループマスタ
    var ConGmnIdKengenMst       = "80";    //権限マスタ
    var ConGmnIdTantoMst        = "90";    //担当者マスタ
    var ConGmnIdShisakuDataEdit = "100";   //試作データ画面(詳細)
    var ConGmnIdShisakuDataNew  = "110";   //試作データ画面(新規)
    var ConGmnIdSeihoCopy       = "120";   //試作データ画面(製法支援コピー)
    var ConGmnIdGenkaShisan     = "170";   //原価試算画面
    //【QP@00342】
    var ConGmnIdGenkaShisanItiran     = "180";   //原価試算一覧画面
    var ConGmnIdGenkaShisanEigyo     = "190";   //原価試算画面（営業）
    var ConGmnIdEigyoTantoMst     = "200";   //担当者マスタ（営業）
    // 【QP@30297】
    var ConGmnIdCostTblAdd       = "210";   //コストテーブル登録・承認
    var ConGmnIdCostTblList      = "220";   //コストテーブル一覧
    var ConGmnIdCostTblRef       = "230";   //コストテーブル参照

    // 【QP@40404】2014/09/01 TT E.Kitazawa add start
    var ConGmnIdDesignSpaceAdd    = "240";   // デザインスペース登録
    var ConGmnIdShiryoRef         = "270";   // 参考資料
    var ConGmnIdShizaiTehaiInput  = "260";   // 資材手配入力
    var ConGmnIdDesignSpaceDL     = "250";   // デザインスペースダウンロード
    var ConGmnIdShizaiTehaiOutput = "280";   // 資材手配依頼書出力
    var ConGmnIdShizaiCodeList    = "290";   // 複数一括選択
    var ConGmnIdGentyoLiteralMst  = "300";   // 原資材調達部 カテゴリマスタメンテナンス
    // 【QP@40404】2014/09/01 TT E.Kitazawa add end
    // 【KPX@1602367】2016/08/31 May Thu add start
    var ConGmnIdBasePriceAdd       = "310";		//ベース単価登録・承認
    var ConGmnIdBasePriceList      = "320";		//ベース単価一覧
    var ConGmnIdRuiziData          = "330";		// 類似データ呼出
	var ConGmnIdShizaiTehaiList    = "340";		//資材手配済一覧
	var ConGmnIdHattyuLiteralMst   = "350";	    //発注先マスタ
	var ConGmdIdSeihinSearch       = "360";     //製品コードあいまい検索

    // 【KPX@1602367】2016/08/31 May Thu add end
    
    // 【KPX@1603044】2017/06/02 nakamura add start
	var ConGmdIdGenchoPage       = "370";     //原資材マスタ
    // 【KPX@1603044】2017/06/02 nakamura add end
    
    //【KPX@1602367】MAKOTO TAKADA  ADD START
    var ConGenchoMenuIdSeqArray = new Array(ConGmdIdGenchoPage,ConGmnIdShizaiTehaiInput,ConGmnIdShizaiTehaiOutput,ConGmnIdShizaiTehaiList,
    		ConGmnIdCostTblAdd,ConGmnIdCostTblList,ConGmnIdCostTblRef,ConGmnIdBasePriceAdd,ConGmnIdBasePriceList,
										  ConGmnIdHattyuLiteralMst,
                                          ConGmnIdGentyoLiteralMst,ConGmnIdDesignSpaceAdd,ConGmnIdDesignSpaceDL);
    //【KPX@1602367】MAKOTO TAKADA  ADD END

    //権限(機能ID)
    var ConFuncIdRead   = "10";    //閲覧
    var ConFuncIdEdit   = "20";    //編集
    var ConFuncIdEdit2  = "21";    //編集(氏名ﾊﾟｽﾜｰﾄﾞのみ)
    // ADD 2013/9/25 okano【QP@30151】No.28 start
    var ConFuncIdEditKari  = "22";    //編集(仮登録ユーザ)
    var ConFuncIdEditPass  = "23";    //編集(パスワード変更ユーザ)
    var ConFuncIdEditCash  = "24";    //編集(キャッシュレス登録ユーザ)
    // ADD 2013/9/25 okano【QP@30151】No.28 end
    var ConFuncIdNew    = "30";    //新規
    var ConFuncIdAll    = "40";    //編集(全て)
    var ConFuncIdSysMgr = "50";    //システム管理者用
    var ConFuncIdGenkaShisan = "60";    //原価試算
    var ConFuncIdReadExcel = "70"; //閲覧（Excel出力）
    //【QP@00342】
    var ConFuncIdItiranRead = "80"; //閲覧
    var ConFuncIdGenkaEditEigyo = "90"; //編集
    var ConFuncIdGenkaRefEigyo = "91"; //閲覧
    var ConFuncIdEigyoTantoEditIppan = "100"; //編集（一般）
    var ConFuncIdEigyoTantoEditKari = "101"; //編集（仮登録ユーザ）
    var ConFuncIdEigyoTantoEditHonbu = "102"; //編集（本部権限）
    var ConFuncIdEigyoTantoEditSystem = "103"; //編集（システム管理者）
    // ADD 2013/9/25 okano【QP@30151】No.28 start
    var ConFuncIdEigyoTantoEditPass = "104"; //編集（パスワード変更ユーザ）
    var ConFuncIdEigyoTantoEditCash = "105"; //編集（キャッシュレス登録ユーザ）
	// ADD 2013/9/25 okano【QP@30151】No.28 end



    /****************************/
    /*      そ の 他 情 報      */
    /****************************/
    //機能ID(共通情報取得用)
    var ConUserInfo = "USERINFO";
    var ConResult   = "RESULT";

    //通信パターン
    var ConConectGet  = "GET";
    var ConConectPost = "POST";

    //メッセージボックス戻り値
    var ConBtnYes    = "yes";        //はい
    var ConBtnNo     = "no";         //いいえ
    var ConBtnClose  = "close";      //閉じる
    var ConBtnCancel = "cancel";     //キャンセル
    var ConBtnAbort  = "abort";      //中止

    //カラー指定
    var activeSelectedColor   = "aquamarine";    //一覧選択状態(アクティブ時)
    var deactiveSelectedColor = "white";         //一覧選択状態(非アクティブ時)
    var haishiRowColor = "gray";                 //一覧選択状態(廃止時)

    //セッション切れエラーメッセージコード
    var ConSessionErrCd = "E000101";

    //タイマー時間
    var ConTimer = 100;

    //原価依頼記号
    var GenkaMark = "$";

    //原価試算画面（採用サンプルNo：採用無し）
    var SaiyouNashiValue = "-1";

    //原価試算画面（編集可否）
    var henshuOkColor = "#ffff88";
    var henshuNgColor = "#ffffff";
    var henshuOkClass = "table_text_disb";
    var henshuNgClass = "table_text_view";
    
    //原資材マスタURL
    var GenchoUrl = "http://vo-gencho/WEB/Pages/MainMenu.aspx";

