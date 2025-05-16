//========================================================================================
// 画面操作処理 [DisplayControl.js]
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 概要  ：画面項目に対しての操作を行う。
//========================================================================================

var CurrentPage = "";   //選択ページ
var CurrentRow = "";    //選択行位置

//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.14
var hndl = null;        //setInterval() のハンドル値

//========================================================================================
// ログイン情報表示
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlUser ：ユーザ情報格納XML名
//       ：②pattern ：処理パターン
//           1:ﾒﾆｭｰ、2:ﾒﾆｭｰ以外
//       ：③ObjectId：設定オブジェクトID
// 戻り値：なし
// 概要  ：ユーザ情報表示用のHTML文を生成、出力する。
//========================================================================================
function funInformationDisplay(xmlUser, pattern, ObjectId) {

    var obj;              //設定先オブジェクト
    var OutputHtml;       //出力HTML
    var ShainName;        //社員名
    var KaishaName;       //会社名
    var BushoName;        //部署名
    var GroupName;        //グループ名
    var TeamName;         //チーム名
    var YakuName;         //役職名
    var SysVersion;       //システムバージョン

    obj = document.getElementById(ObjectId);

    //ﾊﾟﾀｰﾝ判定
    if (pattern == 1) {
        //ﾒﾆｭｰ
	    ShainName = funXmlRead(xmlUser, "nm_user", 0);
	    KaishaName = funXmlRead(xmlUser, "nm_kaisha", 0);
	    BushoName = funXmlRead(xmlUser, "nm_busho", 0);

        GroupName = funXmlRead(xmlUser, "nm_group", 0);
        TeamName = funXmlRead(xmlUser, "nm_team", 0);
        YakuName = funXmlRead(xmlUser, "nm_literal", 0);

        SysVersion = funXmlRead(xmlUser, "systemversion", 0);

        OutputHtml = "<table align=\"right\" width=\"300px\">";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";

        //【QP@00342】
        OutputHtml += "        <td align=\"right\" width=\"150px\">バージョン情報：</td>";

        OutputHtml += "        <td align=\"right\" width=\"150px\">" + SysVersion + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
        OutputHtml += "        <td align=\"right\">社員名：</td>";
        OutputHtml += "        <td align=\"right\">" + ShainName + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
        OutputHtml += "        <td align=\"right\">所属会社名：</td>";
        OutputHtml += "        <td align=\"right\">" + KaishaName + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
        OutputHtml += "        <td align=\"right\">所属部署名：</td>";
        OutputHtml += "        <td align=\"right\">" + BushoName + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
        OutputHtml += "        <td align=\"right\">所属グループ名：</td>";
        OutputHtml += "        <td align=\"right\">" + GroupName + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
        OutputHtml += "        <td align=\"right\">所属チーム名：</td>";
        OutputHtml += "        <td align=\"right\">" + TeamName + "</td>";
        OutputHtml += "    </tr>";
        OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";

        //管理台帳No304修正 TT西川 2009/09/29
        //------------------------------------------------------------- START

        //OutputHtml += "        <td align=\"right\">役職名：</td>";
        //OutputHtml += "        <td align=\"right\">" + YakuName + "</td>";

        OutputHtml += "        <td align=\"right\"></td>";
        OutputHtml += "        <td align=\"right\"></td>";

        //------------------------------------------------------------- END

        OutputHtml += "    </tr>";
        OutputHtml += "</table>";


    }
    else if(pattern == 110){
        //原価試算画面

        ShainName = funXmlRead(xmlUser[1], "nm_user", 0);
        KaishaName = funXmlRead(xmlUser[1], "nm_kaisha", 0);
        BushoName = funXmlRead(xmlUser[1], "nm_busho", 0);

        OutputHtml = "<table width=\"99%\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td width=\"*%\"></td>";
        OutputHtml += "        <td width=\"60\">所属会社：</td>";
        OutputHtml += "        <td width=\"160\">" + KaishaName + "</td>";
        OutputHtml += "        <td width=\"60\">所属部署：</td>";
        OutputHtml += "        <td width=\"120\">" + BushoName + "</td>";
        OutputHtml += "        <td width=\"50\">担当者：</td>";
        OutputHtml += "        <td width=\"200\">" + ShainName + "</td>";
        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";
    }
    else {
        //ﾒﾆｭｰ以外
	    ShainName = funXmlRead(xmlUser, "nm_user", 0);
	    KaishaName = funXmlRead(xmlUser, "nm_kaisha", 0);
	    BushoName = funXmlRead(xmlUser, "nm_busho", 0);

        OutputHtml = "<table width=\"99%\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td width=\"*%\"></td>";
        OutputHtml += "        <td width=\"60\">所属会社：</td>";
        OutputHtml += "        <td width=\"160\">" + KaishaName + "</td>";
        OutputHtml += "        <td width=\"60\">所属部署：</td>";
        OutputHtml += "        <td width=\"120\">" + BushoName + "</td>";
        OutputHtml += "        <td width=\"50\">担当者：</td>";
        OutputHtml += "        <td width=\"150\">" + ShainName + "</td>";
        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";
    }

    //HTMLを出力
    obj.innerHTML = OutputHtml;

    return true;

}
//【KPX@1602367】2016/09/01 May Thu add start
//========================================================================================
// ログイン情報表示
// 作成者：May Thu
// 作成日：2016/09/01
// 概要  ：発注情報表示用のHTML文を生成、出力する。
//========================================================================================

function funOrderInfoDisplay(xmlUser, pattern, ObjectId) {
	var OrderCount;       //発注件数
	var ShainName;        //社員名
	var i;
	var OutputHtml;       //出力HTML
    obj = document.getElementById(ObjectId);

    //件数の取得
    var reccnt = funGetLength(xmlUser);
 	OutputHtml = "<table align=\"right\" width=\"300px\">";
	OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
	OutputHtml += "    <td colspan=\"2\" align=\"right\" width=\"300px\">------------------------------------------</td>";
	OutputHtml += "    </tr>";
	if(reccnt ==  0)
	{
			OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
			OutputHtml += "    <td colspan=\"2\" align=\"left\" width=\"300px\">作業中のデータが存在しません。</td>";
			OutputHtml += "    </tr>";
	}
	//発注件数がある場合
	else{
		OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
		OutputHtml += "    <td colspan=\"2\" align=\"left\" width=\"300px\">作業中のデータが存在します。</td>";
		OutputHtml += "    </tr>";
		for (i = 0; i < reccnt ; i++){
		    ShainName = funXmlRead(xmlUser, "nm_hattyu", i);
            OrderCount= funXmlRead(xmlUser, "ORDER_CNT", i);      //発注件数
		    OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
            OutputHtml += "        <td align=\"left\" width=\"150px\">" + ShainName + "</td>";
			OutputHtml += "        <td align=\"right\" width=\"150px\">" + OrderCount + "件</td>";
			OutputHtml += "    </tr>";
		}
	}
//    OutputHtml += "    </tr>";
	OutputHtml += "    <tr style=\"text-align:right;font-size:12px;\">";
    OutputHtml += "        <td colspan=\"2\" align=\"right\" width=\"300px\">------------------------------------------</td>";
    OutputHtml += "    </tr>";
    OutputHtml += "</table>";
	//HTMLを出力
    obj.innerHTML = OutputHtml;
    return true;
 }
//【KPX@1602367】2016/09/01 May Thu add end
/*
//========================================================================================
// ページリンク作成
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①PageNo   ：現在のページ番号
//       ：②MaxPageNo：最終ページ番号
//       ：③LinkId   ：ページリンク設定オブジェクトID
//       ：④TblId    ：明細部テーブルオブジェクトID
// 戻り値：なし
// 概要  ：ページ遷移用のHTML文を生成、出力する。
//========================================================================================
function funCreatePageLink(PageNo, MaxPageNo, LinkId, TblId) {

    var obj;              //設定先オブジェクト
    var OutputHtml;       //出力HTML
    var i;
    var startidx;
    var endidx;

    obj = document.getElementById(LinkId);

    if (MaxPageNo != 0) {
        OutputHtml = "<table width=\"99%\" height=\"50\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td align=\"center\">";

        if (PageNo == "1") {
            OutputHtml += "最初";
        } else {
            OutputHtml += "<a href=\"javascript:funPageMove(1);\"><font color=\"blue\">最初</font></a>";
        }

        OutputHtml += "&nbsp;&nbsp;&nbsp;　";

        //開始ﾍﾟｰｼﾞ、終了ﾍﾟｰｼﾞ位置の取得
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
            OutputHtml += "　";
            if (i == PageNo) {
                OutputHtml += "<span style=\"font-size:20px\">";
                OutputHtml += i;
                OutputHtml += "</span>&nbsp;";
            } else {
                OutputHtml += "<a href=\"javascript:funPageMove(" + i + ");\"><font color=\"blue\">" + i + "</font></a>&nbsp;";
            }
        }

        OutputHtml += "&nbsp;&nbsp;　";

        if (MaxPageNo == "1" || PageNo == MaxPageNo) {
            OutputHtml += "最後";
        } else {
            OutputHtml += "<a href=\"javascript:funPageMove(" + MaxPageNo + ");\"><font color=\"blue\">最後</font></a>";
        }

        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";

    } else {
        //ﾃﾞｰﾀが存在しない場合はﾘﾝｸを表示しない
        OutputHtml = "";
    }

    //HTMLを出力
    obj.innerHTML = OutputHtml;
    CurrentPage = PageNo;

    return true;

}
*/
//========================================================================================
// ページリンク作成
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①PageNo   ：現在のページ番号
//       ：②MaxPageNo：最終ページ番号
//       ：③LinkId   ：ページリンク設定オブジェクトID
//       ：④TblId    ：明細部テーブルオブジェクトID
// 戻り値：なし
// 概要  ：ページ遷移用のHTML文を生成、出力する。
//========================================================================================
function funCreatePageLink(PageNo, MaxPageNo, LinkId, TblId) {

    var obj;              //設定先オブジェクト
    var OutputHtml;       //出力HTML
    var i;
    var startidx;
    var endidx;

    obj = document.getElementById(LinkId);

    if (MaxPageNo != 0) {
        OutputHtml = "<table width=\"99%\" height=\"50\">";
        OutputHtml += "    <tr>";
        OutputHtml += "        <td align=\"center\">";

        if (PageNo == "1") {
            OutputHtml += "最初";
        } else {
            OutputHtml += "<span onClick=\"funPageMove(1);\" style=\"cursor:pointer;color:blue;\"><u>最初</u></span>";
        }

        OutputHtml += "&nbsp;&nbsp;&nbsp;　";

        //開始ﾍﾟｰｼﾞ、終了ﾍﾟｰｼﾞ位置の取得
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
            OutputHtml += "　";
            if (i == PageNo) {
                OutputHtml += "<span style=\"font-size:20px\">";
                OutputHtml += i;
                OutputHtml += "</span>&nbsp;";
            } else {
                OutputHtml += "<span onClick=\"funPageMove(" + i + ");\" style=\"cursor:pointer;color:blue;\"><u>" + i + "</u></span>&nbsp;";
            }
        }

        OutputHtml += "&nbsp;&nbsp;　";

        if (MaxPageNo == "1" || PageNo == MaxPageNo) {
            OutputHtml += "最後";
        } else {
            OutputHtml += "<span onClick=\"funPageMove(" + MaxPageNo + ");\" style=\"cursor:pointer;color:blue;\"><u>最後</u></span>";
        }

        OutputHtml += "        </td>";
        OutputHtml += "    </tr>";
        OutputHtml += "</table>";

    } else {
        //ﾃﾞｰﾀが存在しない場合はﾘﾝｸを表示しない
        OutputHtml = "";
    }

    //HTMLを出力
    obj.innerHTML = OutputHtml;
    CurrentPage = PageNo;

    return true;

}

//========================================================================================
// メニューボタン作成
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlUser ：ユーザ情報格納XML名
//       ：②ScreenId：画面ID
//       ：③ObjectId：ボタン設定オブジェクトID
// 戻り値：なし
// 概要  ：メニューボタンのHTML文を生成、出力する。
//========================================================================================
function funCreateMenuButton(xmlUser, ScreenId, ObjectId) {

    var obj;              //設定先オブジェクト
    var OutputHtml;       //出力HTML
    var BtnHtml;          //出力HTML
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
  //【QP@30297】add start 20140501
    breakflg3 = false;
  //【QP@30297】add end 20140501
    btncnt = 0;

    obj = document.getElementById(ObjectId);

    //権限数の取得
    reccnt = funGetLength(xmlUser);

    OutputHtml += "<table align=\"center\" valign=\"top\" bgcolor=\"#C2C0FF\" width=\"300\" height=\"400\">";
    OutputHtml += "<tr>";
    OutputHtml += "    <td height=\"16\"></td>";
    OutputHtml += "</tr>";

    //画面ID判定
    if (ScreenId.toString() == ConMainMenuId.toString()) {
        //ﾒｲﾝﾒﾆｭｰ
        for (i = 0; i < reccnt; i++) {
            GamenId = funXmlRead(xmlUser, "id_gamen", i);

            //画面IDで分岐
            switch (GamenId.toString()) {
  //【KPX@1900110】del start 20190808            
                // case ConGmnIdShisakuList.toString():      //試作データ一覧
                //     OutputHtml += "<tr>";
                //     OutputHtml += "    <td align=\"center\" height=\"50\">";
                //     OutputHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"試作一覧\" onclick=\"funNext(1)\">";
                //     OutputHtml += "    </td>";
                //     OutputHtml += "</tr>";
                //     btncnt += 1;
                //     break;
  //【KPX@1900110】del end   20190808
                case ConGmnIdGenryoInfoMst.toString():    //原料分析情報マスタ
                case ConGmnIdGenryoInfoCsv.toString():    //原料分析情報マスタCSV
                case ConGmnIdTankaBudomari.toString():    //全工場単価歩留
                case ConGmnIdLiteralMst.toString():       //リテラルマスタ
                case ConGmnIdLiteralCsv.toString():       //リテラルマスタCSV
                case ConGmnIdGroupMst.toString():         //グループマスタ
                case ConGmnIdKengenMst.toString():        //権限マスタ
                case ConGmnIdTantoMst.toString():         //担当者マスタ
                case ConGmnIdEigyoTantoMst.toString():         //【QP@00342】担当者マスタ（営業）
                    if (breakflg1 == false) {
                        //ﾏｽﾀﾒﾆｭｰﾎﾞﾀﾝが出力されていない場合、ﾏｽﾀﾒﾆｭｰﾎﾞﾀﾝを生成
                        OutputHtml += "<tr>";
                        OutputHtml += "    <td align=\"center\" height=\"50\">";
                        OutputHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"マスタメニュー\" onclick=\"funNext(2)\">";
                        OutputHtml += "    </td>";
                        OutputHtml += "</tr>";
                        breakflg1 = true;
                        btncnt += 1;
                    }
                    break;
                case ConGmnIdGenkaShisanItiran.toString():         //【QP@00342】原価試算一覧
                        OutputHtml += "<tr>";
                        OutputHtml += "    <td align=\"center\" height=\"50\">";
                        OutputHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"原価試算一覧\" onclick=\"funNext(3)\">";
                        OutputHtml += "    </td>";
                        OutputHtml += "</tr>";
                        btncnt += 1;
                    break;
 //【QP@30297】add start 20140501
                case ConGmnIdCostTblAdd.toString():         //【QP@30297】コストテーブル承認・登録
                case ConGmnIdCostTblList.toString():        //【QP@30297】コストテーブル一覧
 //【QP@40404】add start 20140901
                case ConGmnIdDesignSpaceAdd.toString():     //【QP@40404】デザインスペース登録
                case ConGmnIdDesignSpaceDL.toString():      //【QP@40404】デザインスペースダウンロード
                case ConGmnIdShizaiTehaiInput.toString():   //【QP@40404】資材手配入力
                case ConGmnIdShizaiTehaiOutput.toString():  //【QP@40404】資材手配依頼書出力
                case ConGmnIdShizaiCodeList.toString():     //【QP@40404】複数一括選択
                case ConGmnIdGentyoLiteralMst.toString():   //【QP@40404】原資材調達部 カテゴリマスタメンテナンス

				//【KPX@1602367】2016/09/01 May Thu add start
				case ConGmnIdBasePriceAdd.toString():      // ベース単価登録・承認
				case ConGmnIdBasePriceList.toString():     // ベース単価一覧

				//【KPX@1602367】2016/09/01 May Thu add end
				// 【KPX@1603044】2017/06/02 nakamura add start
				case ConGmdIdGenchoPage.toString():     // 原資材マスタ
				// 【KPX@1603044】2017/06/02 nakamura add end
                    if (breakflg3 == false) {
                        OutputHtml += "<tr>";
                        OutputHtml += "    <td align=\"center\" height=\"50\">";
                        OutputHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"原資材調達部メニュー\" onclick=\"funNext(4)\">";
                        OutputHtml += "    </td>";
                        OutputHtml += "</tr>";
                        breakflg3 = true;
                        btncnt += 1;
                    }
                    break;
            }
        }

    } else if (ScreenId.toString() == ConGenchoMenuId.toString()) {
        //原始調達メニューの指定順番に分ループ MAKOTO TAKADA ADD
        for (j = 0; j < ConGenchoMenuIdSeqArray.length; j++) {
          //ﾏｽﾀﾒﾆｭｰ
            for (i = 0; i < reccnt; i++) {
             GamenId = funXmlRead(xmlUser, "id_gamen", i);

            //画面ＩＤが一致原始調達メニューと一致しなければ、次ループに移動。 MAKOTO TAKADA ADD
            if(ConGenchoMenuIdSeqArray[j] != GamenId)  {
                 continue;
            }
            //画面IDで分岐
            switch (GamenId.toString()) {
                case ConGmnIdCostTblAdd.toString():         //【QP@30297】コストテーブル登録・承認
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"コストテーブル登録\" onclick=\"funNext(1)\"><br>";
                    break;
                case ConGmnIdCostTblList.toString():        //【QP@30297】コストテーブル一覧
                
              //2017/3/7 tamura delete start
                  //BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"コストテーブル一覧\" onclick=\"funNext(2)\"><br>";
              //2017/3/7 tamura delete end  
              //2017/3/7 tamura add start
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"コストテーブル一覧・承認\" onclick=\"funNext(2)\"><br>";
              //2017/3/7 tamura add end
                    break;
                case ConGmnIdCostTblRef.toString():        //【QP@30297】コストテーブル一覧
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"コストテーブル参照\" onclick=\"funNext(15)\"><br>";
                    break;
              //【QP@40404】add start 20140901
                case ConGmnIdShizaiTehaiInput.toString():   //【QP@40404】資材手配入力
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"資材手配入力\" onclick=\"funNext(5)\"><br>";
                    break;
                case ConGmnIdShizaiTehaiOutput.toString():   //【QP@40404】資材手配依頼書出力
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"資材手配依頼書出力\" onclick=\"funOpen(14)\"><br>";
                    break;
                case ConGmnIdGentyoLiteralMst.toString():   //【QP@40404】原資材調達部 カテゴリマスタメンテナンス
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"原資材調達部\nカテゴリマスタメンテナンス\" onclick=\"funNext(7)\"><br>";
                    break;
              //【QP@40404】add end 20140901
			     case ConGmnIdDesignSpaceAdd.toString():     //【QP@40404】デザインスペース登録
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"デザインスペース登録\" onclick=\"funNext(3)\"><br>";
                    break;
                case ConGmnIdDesignSpaceDL.toString():      //【QP@40404】デザインスペースダウンロード
                	BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"デザインスペースダウンロード\" onclick=\"funNext(4)\"><br>";
                break;
		   //【KPX@1602367】add May Thu start 20160831
				case ConGmnIdBasePriceAdd.toString():   //【KPX@1602367】ベース単価登録・承認
              //2017/3/7 tamura delete start
                  //BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"ベース単価登録・承認\" onclick=\"funNext(10)\"><br>";
              //2017/3/7 tamura delete end
              //2017/3/7 tamura add start
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"ベース単価登録\" onclick=\"funNext(10)\"><br>";
              //2017/3/7 tamura add end
                    break;
                case ConGmnIdBasePriceList.toString():   //【KPX@1602367】ベース単価一覧
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"ベース単価一覧・承認\" onclick=\"funNext(11)\"><br>";
                    break;
				case ConGmnIdShizaiTehaiList.toString():   //【KPX@1602367】資材手配済一覧
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"資材手配済一覧\" onclick=\"funNext(12)\"><br>";
                    break;
				case ConGmnIdHattyuLiteralMst.toString():   //【KPX@1602367】発注先マスタ
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"発注先マスタ\" onclick=\"funNext(13)\"><br>";
                    break;
			  //【KPX@1602367】add May Thu end 20160831
			  //2017/6/2 nakamura add start
			  	case ConGmdIdGenchoPage.toString():   //// 【KPX@1603044】2017/06/02 nakamura add
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" style=\"font-size:12pt;\" value=\"原資材マスタ\" onclick=\"funNext(16)\"><br>";
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
 //【QP@30297】add end 20140501
            }
        }
      }
    } else {
        //ﾏｽﾀﾒﾆｭｰ
        for (i = 0; i < reccnt; i++) {
            GamenId = funXmlRead(xmlUser, "id_gamen", i);

            //画面IDで分岐
            switch (GamenId.toString()) {
                case ConGmnIdGenryoInfoMst.toString():    //原料分析情報マスタ
                case ConGmnIdGenryoInfoCsv.toString():    //原料分析情報マスタCSV
                    if (breakflg1 == false) {
                        BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"原料分析情報マスタ\" onclick=\"funNext(1)\"><br>";
                        breakflg1 = true;
                    }
                    break;
                case ConGmnIdTankaBudomari.toString():    //全工場単価歩留
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"全工場単価歩留\" onclick=\"funNext(2)\"><br>";
                    break;
                case ConGmnIdLiteralMst.toString():       //リテラルマスタ
                case ConGmnIdLiteralCsv.toString():       //リテラルマスタCSV
                    if (breakflg2 == false) {
                        BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"カテゴリマスタ\" onclick=\"funNext(3)\"><br>";
                        breakflg2 = true;
                    }
                    break;
                case ConGmnIdGroupMst.toString():         //グループマスタ
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"グループマスタ\" onclick=\"funNext(4)\"><br>";
                    break;
                case ConGmnIdKengenMst.toString():        //権限マスタ
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"権限マスタ\" onclick=\"funNext(5)\"><br>";
                    break;
                case ConGmnIdTantoMst.toString():         //担当者マスタ
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"担当者マスタ\" onclick=\"funNext(6)\"><br>";
                    break;
                case ConGmnIdEigyoTantoMst.toString():         //【QP@00342】担当者マスタ（営業）
                    BtnHtml += "        <input type=\"button\" name=\"btnMenu\" class=\"menubutton\" value=\"担当者マスタ（営業）\" onclick=\"funNext(7)\"><br>";
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
// ダイアログ起動
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①NextUrl：遷移先URL
//       ：②Param  ：引数(配列) or フォームオブジェクト
//       ：③Option ：オプション情報
// 戻り値：押下ボタン判定区分
// 概要  ：ダイアログ画面を起動する。
//========================================================================================
function funOpenModalDialog(NextUrl, Param, Option) {

    var RetVal;        //戻り値

    //ﾓｰﾀﾞﾙﾀﾞｲｱﾛｸﾞを表示
    RetVal = showModalDialog(NextUrl, Param, Option);

    return RetVal;

}

//========================================================================================
// 日付編集処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①DateValue：対象日付
// 戻り値：なし
// 概要  ：YYYY/MM/DD形式への変換を行う。
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
// 選択行操作
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：なし
// 戻り値：なし
// 概要  ：選択行の背景色を変更する。
//========================================================================================
function funChangeSelectRowColor() {

    var oSrc = event.srcElement, oTBL, oTR, oTD;
    var BeforeRow;

    if ( (oSrc.tagName != "TABLE") && (oSrc.tagName != "DIV") && (oSrc.tagName != "TR") ) {
        for ( oTBL = oSrc.parentElement; oTBL.tagName != "TABLE"; oTBL = oTBL.parentElement ) {}
        for ( oTR = oSrc.parentElement; oTR.tagName != "TR"; oTR = oTR.parentElement ) {}

        BeforeRow = (CurrentRow == "" ? 0 : CurrentRow / 1);

        //背景色を変更
        oTBL.rows(oTR.rowIndex).style.backgroundColor = activeSelectedColor;

        if ((BeforeRow != oTR.rowIndex) && (BeforeRow + 1 <= oTBL.rows.length)){
            //背景色を戻す
            oTBL.rows(BeforeRow).style.backgroundColor = deactiveSelectedColor;
        }

        //ｶﾚﾝﾄ行の退避
        CurrentRow = oTR.rowIndex;
    }

    return true;

}

//========================================================================================
// 選択行削除
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlTable ：リスト用XML
// 戻り値：なし
// 概要  ：選択行を削除する。
//========================================================================================
function funSelectRowDelete(xmlTable) {

    var tblNode;
    var ndDel;

    //行が未選択の場合、処理を抜ける
    if (CurrentRow.toString() == "") {
        return true;
    }

    //選択行を削除
    tblNode = xmlTable.documentElement.childNodes.item(0);
    ndDel = tblNode.childNodes.item(CurrentRow);
    tblNode.removeChild(ndDel);
    CurrentRow = "";

    return true;

}

//========================================================================================
// カレント行取得処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：なし
// 戻り値：カレント行
// 概要  ：選択中の行番号を返す。
//========================================================================================
function funGetCurrentRow() {

    return CurrentRow;

}

//========================================================================================
// カレント行設定処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①RowNo ：カレント行
// 戻り値：なし
// 概要  ：選択中の行番号を設定する。
//========================================================================================
function funSetCurrentRow(RowNo) {

    CurrentRow = RowNo;

    return true;

}

//========================================================================================
// カレントページ取得処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：なし
// 戻り値：カレントページ
// 概要  ：選択中のページ番号を返す。
//========================================================================================
function funGetCurrentPage() {

    return CurrentPage;

}

//========================================================================================
// カレントページ設定処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①pageNo ：カレントページ
// 戻り値：なし
// 概要  ：選択中のページ番号を設定する。
//========================================================================================
function funSetCurrentPage(PageNo) {

    CurrentPage = PageNo;

    return true;

}

//========================================================================================
// カレント行クリア処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①obj ：テーブルオブジェクト
// 戻り値：なし
// 概要  ：選択行の行番号をクリアする。
//========================================================================================
function funClearCurrentRow(obj) {

    if (CurrentRow.toString() != "") {
        //背景色を戻す
        obj.rows(CurrentRow).style.backgroundColor = deactiveSelectedColor;
    }

    //ｶﾚﾝﾄ行のｸﾘｱ
    CurrentRow = "";

    return true;

}

//========================================================================================
// 画面操作制御
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：なし
// 戻り値：なし
// 概要  ：処理中メッセージを表示する。
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

    this.viewArea.innerHTML = '<table height="100%"><tr><td valign="middle" style="font-size:17pt"><img src="../image/loading.gif">処理中...</td></tr></table>';

    this.viewArea.style.display="";

    return true;

}

//========================================================================================
// 画面操作制御
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：なし
// 戻り値：なし
// 概要  ：処理中メッセージを表示する。
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

    this.viewArea.innerHTML = '<table height="100%"><tr><td valign="middle" style="font-size:17pt"><img src="../image/loading.gif">処理中...</td></tr></table>';

    this.viewArea.style.display="";

    return true;

}

//========================================================================================
// 画面操作制御解除
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：なし
// 戻り値：なし
// 概要  ：処理中メッセージを終了する。
//========================================================================================
function funClearRunMessage() {

    try {
        _scrtable_viewarea.style.display = "none";
    } catch(e) {
    }

    return true;

}

//========================================================================================
// 画面設定処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①GamenId ：画面ID
// 戻り値：なし
// 概要  ：画面サイズ、画面表示位置、タイトルの設定を行う
//========================================================================================
function funInitScreen(GamenId) {

    var width;
    var height;

    //ﾀｲﾄﾙの変更
    switch (GamenId) {
        case ConLoginId:          //ﾛｸﾞｲﾝ
            width  = 400;                                     //画面幅
            height = funAdjustScreenHeight(260);              //画面高さ
            //【QP@00342】
            //document.title = ConSystemId + ConLogin;          //画面ﾀｲﾄﾙ
            document.title = ConLogin;          //画面ﾀｲﾄﾙ
            break;
        case ConMainMenuId:       //ﾒｲﾝﾒﾆｭｰ
            width  = 660;                                     //画面幅
            height = funAdjustScreenHeight(560);              //画面高さ
            document.title = ConSystemId + ConMainMenu;       //画面ﾀｲﾄﾙ
            break;
        case ConMstMenuId:        //ﾏｽﾀﾒﾆｭｰ
            width  = 660;                                     //画面幅
            height = funAdjustScreenHeight(560);              //画面高さ
            document.title = ConSystemId + ConMstMenu;        //画面ﾀｲﾄﾙ
            break;
        case ConShisakuListId:    //試作ﾃﾞｰﾀ一覧
//            width  = 1024;                                    //画面幅
//            height = funAdjustScreenHeight(768);              //画面高さ
            width  = window.screen.width;                     //画面幅
            height = window.screen.height;                    //画面高さ
            document.title = ConSystemId + ConShisakuList;    //画面ﾀｲﾄﾙ
            break;
        case ConGenryoInfoMstId:  //原料分析情報ﾏｽﾀﾒﾝﾃﾅﾝｽ
//            width  = 1024;                                    //画面幅
//            height = funAdjustScreenHeight(768);              //画面高さ
            width  = window.screen.width;                     //画面幅
            height = window.screen.height;                    //画面高さ
            document.title = ConSystemId + ConGenryoInfoMst;  //画面ﾀｲﾄﾙ
            break;
        case ConGenryoInputId:    //分析値入力
            document.title = ConSystemId + ConGenryoInput;    //画面ﾀｲﾄﾙ
            break;
        case ConLiteralMstId:     //ﾘﾃﾗﾙﾏｽﾀﾒﾝﾃﾅﾝｽ
            width  = 800;                                     //画面幅
            height = funAdjustScreenHeight(530);              //画面高さ
            document.title = ConSystemId + ConLiteralMst;     //画面ﾀｲﾄﾙ
            break;
        case ConGroupMstId:       //ｸﾞﾙｰﾌﾟﾏｽﾀﾒﾝﾃﾅﾝｽ
            width  = 700;                                     //画面幅
            height = funAdjustScreenHeight(330);              //画面高さ
            document.title = ConSystemId + ConGroupMst;       //画面ﾀｲﾄﾙ
            break;
        case ConTantoMstId:       //担当者ﾏｽﾀﾒﾝﾃﾅﾝｽ
            width  = 750;                                     //画面幅
            height = funAdjustScreenHeight(710);              //画面高さ
            document.title = ConSystemId + ConTantoMst;       //画面ﾀｲﾄﾙ
            break;
        case ConTantoSearchId:    //担当者検索
            document.title = ConSystemId + ConTantoSearch;    //画面ﾀｲﾄﾙ
            break;
        case ConKasihaAddId:      //製造担当会社追加
            document.title = ConSystemId + ConKasihaAdd;      //画面ﾀｲﾄﾙ
            break;
        case ConKengenMstId:      //権限ﾏｽﾀﾒﾝﾃﾅﾝｽ
//            width  = 1024;                                    //画面幅
//            height = funAdjustScreenHeight(768);              //画面高さ
            width  = window.screen.width;                     //画面幅
            height = window.screen.height;                    //画面高さ
            document.title = ConSystemId + ConKengenMst;      //画面ﾀｲﾄﾙ
            break;
        case ConKengenAddId:      //権限機能追加
            document.title = ConSystemId + ConKengenAdd;      //画面ﾀｲﾄﾙ
            break;
        case ConTankaBudomariId:  //全工場単価歩留
//            width  = 1024;                                    //画面幅
//            height = funAdjustScreenHeight(768);              //画面高さ
            width  = window.screen.width;                     //画面幅
            height = window.screen.height;                    //画面高さ
            document.title = ConSystemId + ConTankaBudomari;  //画面ﾀｲﾄﾙ
            break;

        //【QP@00342】
        case ConStatusRirekiId:    //ステータス履歴
            width  = 980;                                    //画面幅
            height = funAdjustScreenHeight(768);              //画面高さ
            document.title = ConSystemId_genka + ConStatusRireki;    //画面ﾀｲﾄﾙ
            break;

    	//【QP@00342】
        case ConStatusClearId:    //ステータス履歴
            width  = 700;                                    //画面幅
            height = funAdjustScreenHeight(550);              //画面高さ
            document.title = ConSystemId_genka + ConStatusClear;    //画面ﾀｲﾄﾙ
            break;

        //【QP@00342】
        case ConEigyoTantoMstId:    //担当者マスタメンテ（営業）
            width  = 900;                                     //画面幅
            height = funAdjustScreenHeight(710);              //画面高さ
            document.title = ConSystemId_genka + ConEigyoTantoMst;    //画面ﾀｲﾄﾙ
            break;

        //【QP@00342】
        case ConEigyoTantoSearchId:    //担当者マスタメンテ（営業）
            document.title = ConSystemId_genka + ConEigyoTantoSearch;    //画面ﾀｲﾄﾙ
            break;

        //【QP@00342】
        case ConGenkaListId:    //試作ﾃﾞｰﾀ一覧
            width  = window.screen.width;                     //画面幅
            height = window.screen.height;                    //画面高さ
            document.title = ConSystemId_genka + ConGenkaList;    //画面ﾀｲﾄﾙ
            break;
  //【QP@30297】add start 20140501
        case ConGenchoMenuId:        // 原資材調達部メニュー
            width  = 675;                                     //画面幅
            height =  funAdjustScreenHeight(800);              //画面高さ
//            width  = 660;                                     // 画面幅
//            height = funAdjustScreenHeight(560);              // 画面高さ
            document.title = ConSystemId_genka + ConGenchoMenu;     // 画面ﾀｲﾄﾙ
            break;

        case ConCostTblListId:   // コストテーブル一覧
        	width  = 1520;                                    //画面幅
        	height = funAdjustScreenHeight(760);              //画面高さ
//            width  = window.screen.width;                     // 画面幅
//            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConCostTblList;    // 画面ﾀｲﾄﾙ
            break;

        case ConCostTblAddId:   // コストテーブル登録・承認
            width  = window.screen.width;                     // 画面幅
            height = funAdjustScreenHeight(850);              //画面高さ
//            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConCostTblAdd;    // 画面ﾀｲﾄﾙ
            break;

        case ConCostTblRefId:   // コストテーブル参照
            width  = window.screen.width;                     // 画面幅
            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConCostTblRef;    // 画面ﾀｲﾄﾙ
            break;
  //【QP@30297】add end 20140501

  //【QP@40404】add start 2014/09/01
        case ConDesignSpaceAddId:   // デザインスペース登録
            width  = window.screen.width;                     // 画面幅
            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConDesignSpaceAdd;    // 画面ﾀｲﾄﾙ
            break;
        case ConShiryoRefId:   // 参考資料
            width  = 980;                                     //画面幅
            height = funAdjustScreenHeight(500);              //画面高さ
            document.title = ConSystemId_genka + ConShiryoRef;    // 画面ﾀｲﾄﾙ
            break;
        case ConShizaiTehaiInputId:   // 資材手配入力
            width  = window.screen.width;                     // 画面幅
            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConShizaiTehaiInput;    // 画面ﾀｲﾄﾙ
            break;
        case ConDesignSpaceDLId:   // デザインスペースダウンロード
            width  = window.screen.width;                     // 画面幅
            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConDesignSpaceDL;    // 画面ﾀｲﾄﾙ
            break;
        case ConShizaiTehaiOutputId:   // 資材手配依頼書出力
            width  = window.screen.width;                     // 画面幅
            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConShizaiTehaiOutput;    // 画面ﾀｲﾄﾙ
            break;
        case ConShizaiCodeListId:   // 複数一括選択
            width  = window.screen.width;                     // 画面幅
            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConShizaiCodeList;    // 画面ﾀｲﾄﾙ
            break;
  //【QP@40404】add end 2014/09/01
  //【QP@40404】ADD start 2014/10/10
        case ConGentyoLiteralMstId:     //原資材調達部用ﾘﾃﾗﾙﾏｽﾀﾒﾝﾃﾅﾝｽ
            width  = 800;                                     //画面幅
            height = funAdjustScreenHeight(530);              //画面高さ
            document.title = ConSystemId + ConGentyoLiteralMst;     //画面ﾀｲﾄﾙ
            break;
  //【QP@40404】ADD end 2014/10/10
    // 【KPX@1602367】2016/09/06 add start
        case ConBasePriceListId:   // ベース単価一覧
            width  = 1520;                     // 画面幅
            height = funAdjustScreenHeight(760);              //画面高さ
//            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConBasePriceList;    // 画面ﾀｲﾄﾙ
            break;

        case ConBasePriceAddId:   // ベース単価登録・承認
        	width  = 1600;                                    //画面幅
            height = funAdjustScreenHeight(820);              //画面高さ
//            width  = window.screen.width;                     // 画面幅
//            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConBasePriceAdd;    // 画面ﾀｲﾄﾙ
            break;
        case ConRuiziDataId:   // 類似データ呼出
        	width = 1600;
//            width  = window.screen.width;                     // 画面幅
            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConRuiziData;    // 画面ﾀｲﾄﾙ
            break;
        case ConShizaiTehaiZumiListId:   // 資材手配済一覧
            width  = window.screen.width;                     // 画面幅
            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId_genka + ConShizaiTehaiZumiList;    // 画面ﾀｲﾄﾙ
            break;
        case ConHattyuLiteralMstId:     //発注先マスタ
        	width  = window.screen.width;                     // 画面幅
            height = window.screen.height;                    // 画面高さ
            document.title = ConSystemId + ConHattyuLiteralMst;     //画面ﾀｲﾄﾙ
            break;
        	// 【KPX@1602367】 May Thu　201609016 add start
        case ConSeihinSearchId:   //製品コードあいまい検索
             width  = window.screen.width;                     // 画面幅
             height = window.screen.height;                    // 画面高さ
             document.title = ConSystemId_genka + ConSeihinSearch;    // 画面ﾀｲﾄﾙ
             break;
 	// 【KPX@1602367】 May Thu　201609016 add end

    }

    //画面ｻｲｽﾞ・位置の設定
    resizeTo(width, height);
    moveTo((screen.width - width) / 2, (screen.height - height) / 2);

    return height;

}

//========================================================================================
// 画面高さ調整
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①height ：画面高さ
// 戻り値：調整後の画面高さ
// 概要  ：IE7の場合、画面の高さを調整する
//========================================================================================
function funAdjustScreenHeight(height) {

    var userAgentStr;
    var msieIndex;
    var version;

    //バージョンの取得
    userAgentStr = navigator.userAgent;
    msieIndex = userAgentStr.indexOf("MSIE");

    //"MSIE 6.0" ,"MSIE 7.0" バージョンの前にスペースあり
    version = userAgentStr.substring(msieIndex+5, userAgentStr.indexOf(".", msieIndex));

    if (version == 7) {
        //IE7サイズ調整
        height = height - 25;
    }

    return height;

}

//========================================================================================
// コントロール制御(READONLY)
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①objItem ：対象オブジェクト
//       ：②mode    ：モード(true/false)
// 戻り値：なし
// 概要  ：コントロールの使用可、不可の制御を行う
//========================================================================================
function funItemReadOnly(objItem, mode) {

    //ｺﾝﾄﾛｰﾙの制御
    if (objItem.type == "text" || objItem.type == "textarea") {
        objItem.readOnly = mode;
    } else {
        objItem.disabled = mode;
    }

    if (objItem.type != "button") {
        //ｺﾝﾄﾛｰﾙの背景色を変更
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
// コントロール制御(DISABLED)
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①objItem ：対象オブジェクト
//       ：②mode    ：モード(true/false)
// 戻り値：なし
// 概要  ：コントロールの使用可、不可の制御を行う
//========================================================================================
function funItemDisabled(objItem, mode) {

    //ｺﾝﾄﾛｰﾙの制御
    objItem.disabled = mode;

    if (objItem.type != "button") {
        //ｺﾝﾄﾛｰﾙの背景色を変更
        if (mode) {
            objItem.style.background = "gainsboro";
        } else {
            objItem.style.background = "white";
        }
    }

    return true;

}

//========================================================================================
// ゼロ埋め処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①obj  ：対象オブジェクト
//       ：②keta ：桁数
// 戻り値：ゼロ埋め後の値
// 概要  ：指定桁数に前ゼロを付加して返す
//========================================================================================
function funBuryZero(obj, keta) {

    var i;
    var retVal;

    //対象値の桁数ﾁｪｯｸ
    if (obj.value.length == 0) {
        return true;
    } else {
        retVal = obj.value;
    }

    //対象値の前にｾﾞﾛを付加する
    for (i = obj.value.length; i < keta; i++) {
        retVal = "0" + retVal.toString();
    }

    //値を設定する
    obj.value = retVal;

    return true;

}

//========================================================================================
// カンマ編集処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①value ：カンマ編集する値
// 戻り値：カンマ編集結果
// 概要  ：引数に対して３桁区切りのカンマ編集を行う
//========================================================================================
function funAddComma(value){

    var i;

    value = "" + value;

    //カンマ削除
    value = value.replace(/,/g,"");

    for(i = 0; i < value.length/3; i++){
        value = value.replace(/^([+-]?\d+)(\d\d\d)/,"$1,$2");
    }

    return value;
}

//========================================================================================
// 指定小数桁数、切り上げ
// 作成者：Y.nishigawa
// 作成日：2009/11/03
// 引数  ：①value ：切り上げを行う値
//       ：②keta  ：指定桁数
// 戻り値：切り上げ結果
// 概要  ：引数に対して指定小数桁数、切り上げ編集を行う
//========================================================================================
function funShosuKiriage(value, keta){
    var i;
    var shosuten = 1;

    if(value == "" || isNaN(value) ){
        //何もしない
        return value;

    }else{

        //カンマ削除
        value = value.replace(/,/g,"");

        //小数切り上げ
        for( i = 0; i < keta; i++ ){
            shosuten = shosuten * 10;
        }
        value = demicalFloat(value,shosuten,"*");
        value = (Math.ceil(value))/shosuten;

        //小数0埋め
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
// 指定小数桁数、切捨て
// 作成者：Y.nishigawa
// 作成日：2009/11/03
// 引数  ：①value ：切捨てを行う値
//       ：②keta  ：指定桁数
// 戻り値：切り上げ結果
// 概要  ：引数に対して指定小数桁数、切捨て編集を行う
//========================================================================================
function funShosuKirisute(value, keta){
    var i;
    var shosuten = 1;

    if(value == "" || isNaN(value) ){
        //何もしない
        return value;

    }else{

        //カンマ削除
        value = value.replace(/,/g,"");

        //小数切り上げ
        for( i = 0; i < keta; i++ ){
            shosuten = shosuten * 10;
        }
        value = demicalFloat(value,shosuten,"*");
        value = (Math.floor(value))/shosuten;

        //小数0埋め
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
// コンボボックスクリア処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①obj  ：対象オブジェクト
//       ：②mode ：モード
//           1:先頭空白行なし、2:先頭空白行あり
// 戻り値：なし
// 概要  ：コンボボックスの内容をクリアする
//========================================================================================
function funClearSelect(obj, mode) {

    var objNewOption;

    obj.options.length = 0;

    if (mode == 2) {
        objNewOption = document.createElement("option");
        obj.options.add(objNewOption);
        objNewOption.innerText = "　";
        objNewOption.value = "";
        obj.options.selectedIndex = 0;
    } else {
        obj.options.selectedIndex = -1;
    }

}

//========================================================================================
// 【QP@00342】ステータス設定
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①obj  ：対象オブジェクト
//       ：②mode ：モード
//           1:先頭空白行なし、2:先頭空白行あり
// 戻り値：なし
// 概要  ：コンボボックスの内容をクリアする
//========================================================================================
function funStatusSetting(busho, st) {

	//研究所
    if(busho == "1"){
    	//依頼
    	if(st == "2"){
    		return "依頼";
	    }
    }
    //生産管理
    else if(busho == "2"){
    	//なし
    	if(st == "1"){
    		return "-";
	    }
	    //依頼
	    else if(st == "2"){
	    	return "依頼";
	    }
	    //完了
	    else if(st == "3"){
	    	return "完了";
	    }
    }
    //原資材調達
    else if(busho == "3"){
    	//なし
    	if(st == "1"){
    		return "-";
	    }
	    //完了
	    else if(st == "2"){
	    	return "完了";
	    }
    }
    //工場
    else if(busho == "4"){
    	//なし
    	if(st == "1"){
    		return "-";
	    }
	    //完了
	    else if(st == "2"){
	    	return "完了";
	    }
    }
    //営業
    else if(busho == "5"){
    	//なし
    	if(st == "1"){
    		return "-";
	    }
	    //依頼
	    else if(st == "2"){
	    	return "依頼";
	    }
	    //完了
	    else if(st == "3"){
	    	return "完了";
	    }
	    //採用
	    else if(st == "4"){
	    	return "採用";
	    }
    }
}
//========================================================================================
// 【QP@00342】ステータス設定
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①obj  ：対象オブジェクト
//       ：②mode ：モード
//           1:先頭空白行なし、2:先頭空白行あり
// 戻り値：なし
// 概要  ：コンボボックスの内容をクリアする
//========================================================================================
function funStatusSetting_img(busho, st) {

	//研究所
    if(busho == "1"){
    	//依頼
    	if(st == "2"){
    		return "kenkyu_2.GIF";
	    }
    }
    //生産管理
    else if(busho == "2"){
    	//なし
    	if(st == "1"){
    		return "seikan_1.GIF";
	    }
	    //依頼
	    else if(st == "2"){
	    	return "seikan_2.GIF";
	    }
	    //完了
	    else if(st == "3"){
	    	return "seikan_3.GIF";
	    }
    }
    //原資材調達
    else if(busho == "3"){
    	//なし
    	if(st == "1"){
    		return "gentyo_1.GIF";
	    }
	    //完了
	    else if(st == "2"){
	    	return "gentyo_2.GIF";
	    }
    }
    //工場
    else if(busho == "4"){
    	//なし
    	if(st == "1"){
    		return "kojo_1.GIF";
	    }
	    //完了
	    else if(st == "2"){
	    	return "kojo_2.GIF";
	    }
    }
    //営業
    else if(busho == "5"){
    	//なし
    	if(st == "1"){
    		return "eigyo_1.GIF";
	    }
	    //依頼
	    else if(st == "2"){
	    	return "eigyo_2.GIF";
	    }
	    //完了
	    else if(st == "3"){
	    	return "eigyo_3.GIF";
	    }
	    //採用
	    else if(st == "4"){
	    	return "eigyo_4.GIF";
	    }
    }
}

//========================================================================================
// 小数点計算
// 作成者：Y.Nishigawa
// 作成日：2009/11/27
// 引数  ：①obj  ：対象オブジェクト
//       ：②mode ：モード
//           1:先頭空白行なし、2:先頭空白行あり
// 戻り値：なし
// 概要  ：コンボボックスの内容をクリアする
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

    //eval()関数に023～070の文字列を与えると値のまま帰らない→ここで式である可能性はないので、eval関数削除
    //return [po, eval(st)];
    return [po, st];

}

//ADD 2013/07/7 ogawa 【QP@30151】No.39 start
//========================================================================================
//スペース削除処理
//作成者：F.ogawa
//作成日：2013/07/07
//引数  ：①obj  ：対象オブジェクト
//戻り値：スペース削除後の値
//概要  ：文字列内の空白を削除する
//========================================================================================
function funSpaceDel(obj) {

 var i;
 var retVal;

 //文字数が０の場合、未処理
 if (obj.value.length == 0) {
     return true;
 } else {
     retVal = obj.value;
 }

 //スペース削除
 retVal = retVal.replace(/[ 　]/gm, '');

 //値を設定する
 obj.value = retVal;

 return true;

}

//========================================================================================
//全角削除処理
//作成者：F.ogawa
//作成日：2013/07/07
//引数  ：①obj  ：対象オブジェクト
//戻り値：スペース削除後の値
//概要  ：文字列内の空白を削除する
//========================================================================================
function funZenkakuDatDel(obj) {

	var data1 = new Array("０","１","２","３","４","５","６","７","８","９","－","");
	var data2 = new Array("0","1","2","3","4","5","6","7","8","9","-","");
	var i, ct;
	var inVal;
	var outVal="";
	var outVal2="";
	var dt;

	//文字数が０の場合、未処理
	if (obj.value.length == 0) {
		return;
	} else {
		inVal = obj.value;
	}

	//全角数値を半角数値に変更する
	//文字数分繰り返す
	for(i=0; i < obj.value.length ; i++){
		dt = inVal.substring(i,i+1);
		//対象データ（data1内に存在）か比較する
		for(ct=0; data1[ct].length > 0 ; ct++){
			//対象データ（data1内に存在）なら、対応する値(data2)と入れ替える
			if(data1[ct] == dt){
				outVal = outVal + data2[ct];
				break;
			}
		}
		//入れ替えなかった場合、元の値を設定
		if(0 == data1[ct].length){
			outVal = outVal + dt;
		}
	}

	//対象データ以外を削除する
	//文字数分繰り返す
	for(i=0; i < outVal.length ; i++){
		dt = outVal.substring(i,i+1);
		//対象データ（data2内に存在）か比較する
		for(ct=0; data2[ct].length > 0 ; ct++){
			//対象データ（data2内に存在）なら、設定する
			if(data2[ct] == dt){
				outVal2 = outVal2 + dt;
				break;
			}
		}
	}

//値を設定する
	obj.value = outVal2;

return;

}//ADD 2013/07/7 ogawa 【QP@30151】No.39 end

//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.14 start
//========================================================================================
//【QP@40812】ヘルプ画面を表示
// 作成者：E.Kitazawa
// 作成日：2015/03/03
// 引数  ：なし
// 戻り値：なし
// 概要  ：ヘルプ画面を表示する
//========================================================================================
function funHelpCall() {

	//ヘルプ表示（同期を取る為に一定周期で実行）

	hndl = setInterval("funOpenHelp()",1000);
	return true;
}

//========================================================================================
// 作成者：E.Kitazawa
// 作成日：2015/03/03
// 引数  ：なし
// 戻り値：なし
// 概要  ：ヘルプ画面を表示する
//========================================================================================
function funOpenHelp() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var helppath = frm.strHelpPath.value;

	// 未設定の場合、デフォルトページを表示
	if (helppath == null || helppath == "") {
		return false;
	}

	//ウィンドウの設定
	var w = screen.availWidth-10;
	var h = screen.availHeight-30;
	//ウィンドウオープン
	var nwin=window.open(helppath,"HelpPage","menubar=no,resizable=yes,scrollbars=yes,width="+w+",height="+h+",left=0,top=0");
	nwin.document.title="原価試算システム　ヘルプ画面";
	//一定周期で実行していたものを解除
	clearInterval(hndl);
}
//ADD 2015/03/03 TT.Kitazawa【QP@40812】No.14 end

//【KPX@1602367】2016/09/16 May Thu add start
//========================================================================================
// ログイン情報表示
// 作成者：May Thu
// 作成日：2016/09/16
// 概要  ：発注情報表示用のHTML文を生成、出力する。
//========================================================================================

function funCountInfoDisplay(count,ObjectId) {
	var Count = count;       //発注件数
	var OutputHtml;       //出力HTML
    obj = document.getElementById(ObjectId);

    //件数の取得
 	OutputHtml = "<table align=\"left\" width=\"300\">";
	OutputHtml += "    <tr style=\"text-align:left;font-size:12px;\">";
	OutputHtml += "    <td>該当データ件数は" + Count + "です。</td>";
	OutputHtml += "    </tr>";
    OutputHtml += "</table>";
	//HTMLを出力
    obj.innerHTML = OutputHtml;
    return true;
 }
//【KPX@1602367】2016/09/16 May Thu add end

