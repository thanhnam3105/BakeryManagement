//========================================================================================
// XML操作処理 [XmlControl.js]
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 概要  ：XMLファイルに対しての操作を行う。
//========================================================================================

//========================================================================================
// XML操作(統合)
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlMerge   ：統合先XML
//       ：②xmlAddition：追加XML
//       ：③XmlId      ：XMLID
// 戻り値：なし
// 概要  ：複数のXMLファイルを１つのXMLファイルに統合する。
//========================================================================================
function funXmlMerge(xmlMerge, xmlAddition, XmlId) {

    var xmlBuff;
    var objNode;
    var xmldatBuff;

    xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    if (xmlMerge.xml == "") {
        //新しいﾉｰﾄﾞを作成
        objNode = xmlBuff.createNode(1,XmlId,"");
    } else {
        //統合先となるXMLﾌｧｲﾙの読み込み
        xmlBuff.load(xmlMerge);
        objNode = xmlBuff.documentElement;
    }

    //追加するDOMﾄﾞｷｭﾒﾝﾄのｺﾋﾟｰを作成
    xmldatBuff = xmlAddition.cloneNode(true);


    //作成したｴﾚﾒﾝﾄに、xmlﾌｧｲﾙの内容を追加
    objNode.appendChild(xmldatBuff.documentElement);

    if (xmlBuff.xml == "") {
        xmlBuff.appendChild(objNode);
    }

    xmlMerge.load(xmlBuff);

    return true;

}

//========================================================================================
// XML操作(分割)
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlDivision：分割元XML
//       ：②xmlSave    ：格納先XML
//       ：③FuncId     ：機能ID
// 戻り値：なし
// 概要  ：１つのXMLファイルを複数のXMLファイルに分割する。
//========================================================================================
function funXmlDivision(xmlDivision, xmlSave, FuncId) {

    var xmlBuff;
    var objNode;
    var recCnt;
    var i;

    xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    //子ﾉｰﾄﾞの数をﾁｪｯｸする
    recCnt = xmlDivision.documentElement.childNodes.length;
    if (recCnt == 0) {
        return true;
    }

    //新しいﾉｰﾄﾞを作成
    objNode = xmlBuff.createNode(1,FuncId,"");

    //先頭の子ﾉｰﾄﾞを取得
    objNode = xmlDivision.documentElement.firstChild;
    if (objNode.nodeName == FuncId) {
        xmlBuff.appendChild(objNode);

    } else {
        for (i = 1; i < recCnt; i++) {
            //次の子ﾉｰﾄﾞを取得
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
// XML読み出し処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlRead      ：読み出し元XML
//       ：②AttributeName：項目名
//       ：③RowNo        ：行番号
// 戻り値：取得値
// 概要  ：XMLファイルから指定項目の値を取得する。
//========================================================================================
function funXmlRead(xmlRead, AttributeName, RowNo) {

    var objNode;

    try {
        if (xmlRead.xml == "") {
            return "";
        }

        objNode = xmlRead.documentElement.childNodes.item(0).childNodes;

        //子ﾉｰﾄﾞの数をﾁｪｯｸする
        if (objNode.length == 0) {
            return "";
        }
        //属性値を取得
        return objNode.item(RowNo).getAttributeNode(AttributeName).value;

    } catch (e) {
        return "";
    }

}

//========================================================================================
// XML読み出し処理_2
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
// 引数  ：①xmlRead      ：読み出し元XML
//       ：②TableNo      ：テーブル行番号
//       ：③AttributeName：項目名
//       ：④RowNo        ：行番号
// 戻り値：取得値
// 概要  ：XMLファイルから指定項目の値を取得する。
//========================================================================================
function funXmlRead_2(xmlRead, TableNo, AttributeName, RowNo) {

    var objNode;

    try {
        if (xmlRead.xml == "") {
            return "";
        }

        objNode = xmlRead.documentElement.childNodes.item(TableNo).childNodes;

        //子ﾉｰﾄﾞの数をﾁｪｯｸする
        if (objNode.length == 0) {
            return "";
        }
        //属性値を取得
        return objNode.item(RowNo).getAttributeNode(AttributeName).value;

    } catch (e) {
        return "";
    }

}

//========================================================================================
// XML読み出し処理_3
// 作成者：Y.Nishigawa
// 作成日：2009/10/22
// 引数  ：①xmlRead      ：読み出し元XML
//       ：②TableNm      ：テーブル名称
//       ：③AttributeName：項目名
//       ：④TableNo      ：テーブル行番号
//       ：⑤RowNo        ：レコード行番号
// 戻り値：取得値
// 概要  ：XMLファイルから指定項目の値を取得する。
//========================================================================================
function funXmlRead_3(xmlRead, TableNm, AttributeName, TableNo, RowNo) {

    var objNode;

    try {
        if (xmlRead.xml == "") {
            return "";
        }

        //XMLをタグ名で検索し、ノードリストを取得（最初の1件目）
        objNode = xmlRead.getElementsByTagName(TableNm)[TableNo].childNodes;

        //子ﾉｰﾄﾞの数をﾁｪｯｸする
        if (objNode.length == 0) {
            return "";
        }

        //属性値を取得
        return objNode.item(RowNo).getAttributeNode(AttributeName).value;

    } catch (e) {
        return "";
    }

}

//========================================================================================
// XML書き込み処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlWrite     ：書き込み先XML
//       ：②AttributeName：項目名
//       ：③setValue     ：設定値
//       ：④RowNo        ：行番号
// 戻り値：正常終了:true／異常終了:false
// 概要  ：XMLファイルの指定項目に値を設定する。
//========================================================================================
function funXmlWrite(xmlWrite, AttributeName, setValue, RowNo) {

    var objNode;

    try {
        if (xmlWrite.xml == "") {
            return false;
        }

        objNode = xmlWrite.documentElement.childNodes.item(0).childNodes;

        //子ﾉｰﾄﾞの数をﾁｪｯｸする
        if (objNode.length == 0) {
            return true;
        }

        //属性値を設定
        objNode.item(RowNo).setAttribute(AttributeName, setValue);

        return true;

    } catch (e) {
        return false;
    }

}

//========================================================================================
// XML書き込み処理(テーブル指定)
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlWrite     ：書き込み先XML
//       ：②TableName    ：テーブル名
//       ：③AttributeName：項目名
//       ：④setValue     ：設定値
//       ：⑤RowNo        ：行番号
// 戻り値：正常終了:true／異常終了:false
// 概要  ：XMLファイルの指定項目に値を設定する。
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

        //子ﾉｰﾄﾞの数をﾁｪｯｸする
        recCnt = xmlWrite.documentElement.childNodes.length;
        if (recCnt == 0) {
            //行を追加
            funAddRecNode(xmlWrite, xmlWrite.documentElement.nodeName, TableName);
        } else {
            //先頭の子ﾉｰﾄﾞを取得
            tNode = xmlWrite.documentElement.firstChild;
            if (tNode.nodeName == TableName) {
                objNode = xmlWrite.documentElement.childNodes.item(0).childNodes;

            } else {
                for (i = 1; i < recCnt; i++) {
                    //次の子ﾉｰﾄﾞを取得
                    tNode = tNode.nextSibling;
                    if (tNode.nodeName == TableName) {
                        objNode = xmlWrite.documentElement.childNodes.item(i).childNodes;
                        break;
                    }
                }
            }
        }

        //子ﾉｰﾄﾞの数をﾁｪｯｸする
        if (objNode.length == 0) {
            return true;
        }

        //属性値を設定
        objNode.item(RowNo).setAttribute(AttributeName, setValue);

        return true;

    } catch (e) {
        return false;
    }

}

//========================================================================================
// XMLレコード件数取得処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlData ：件数取得XML
// 戻り値：レコード件数
// 概要  ：XMLファイルレコード件数を取得する
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
            //子ﾉｰﾄﾞの存在ﾁｪｯｸ
            if (obj.hasChildNodes()) {
                //存在する場合
                reccnt = obj.childNodes.length;
            } else {
                //存在しない場合
                reccnt = 0;
            }

        } catch (e) {

        }
    }

    return reccnt;

}

//========================================================================================
// XMLレコード件数取得処理_2
// 作成者：Y.Nishigawa
// 作成日：2009/10/21
// 引数  ：①xmlData ：件数取得XML
//       ：②TableNo ：テーブル行番号
// 戻り値：レコード件数
// 概要  ：XMLファイルレコード件数を取得する
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
            //子ﾉｰﾄﾞの存在ﾁｪｯｸ
            if (obj.hasChildNodes()) {
                //存在する場合
                reccnt = obj.childNodes.length;
            } else {
                //存在しない場合
                reccnt = 0;
            }

        } catch (e) {

        }
    }

    return reccnt;

}

//========================================================================================
// XMLレコード件数取得処理_3
// 作成者：Y.Nishigawa
// 作成日：2009/10/21
// 引数  ：①xmlData ：件数取得XML
//       ：②TableNm ：テーブル名称
//       ：②TableNo ：テーブル行番号
// 戻り値：レコード件数
// 概要  ：XMLファイルレコード件数を取得する
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
            //子ﾉｰﾄﾞの存在ﾁｪｯｸ
            if (obj.hasChildNodes()) {
                //存在する場合
                reccnt = obj.childNodes.length;
            } else {
                //存在しない場合
                reccnt = 0;
            }

        } catch (e) {

        }
    }

    return reccnt;

}

//========================================================================================
// XMLレコード追加処理
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlData ：行追加XML
//       ：②FuncId  ：機能ID
// 戻り値：なし
// 概要  ：XMLに新規RECノードを追加する
//========================================================================================
function funAddRecNode(xmlData, FuncId) {

    var xmlBuff;
    var fNode;
    var tNode;
    var rNode;

    xmlBuff = new ActiveXObject("Microsoft.XMLDOM");

    if (xmlData.xml == "") {
        //新しいﾉｰﾄﾞを作成
        fNode = xmlBuff.createNode(1, FuncId, "");
        tNode = xmlBuff.createNode(1, "table", "");
    } else {
        //現状のﾉｰﾄﾞを取得
        fNode = xmlData.documentElement.cloneNode(false);
        tNode = xmlData.documentElement.childNodes.item(0).cloneNode(true);
    }

    try {
        //Recﾉｰﾄﾞの追加
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
// XMLレコード追加処理(テーブル指定)
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlData   ：行追加XML
//       ：②FuncId    ：機能ID
//       ：③TableName ：テーブル名
// 戻り値：なし
// 概要  ：XMLに新規RECノードを追加する
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
        //新しいﾉｰﾄﾞを作成
        fNode = xmlBuff.createNode(1, FuncId, "");
        tNode = xmlBuff.createNode(1, TableName, "");

        //Recﾉｰﾄﾞの追加
        rNode = xmlBuff.createNode(1, "rec", "");
        tNode.appendChild(rNode);
        fNode.appendChild(tNode);
        xmlBuff.appendChild(fNode);
        xmlData.load(xmlBuff);

    } else {
        //現状のﾉｰﾄﾞを取得
        fNode = xmlData.documentElement;

        //子ﾉｰﾄﾞの数をﾁｪｯｸする
        recCnt = xmlData.documentElement.childNodes.length;
        if (recCnt == 0) {
            tNode = xmlBuff.createNode(1, TableName, "");
        } else {
            //先頭の子ﾉｰﾄﾞを取得
            objNode = xmlData.documentElement.firstChild;
            if (objNode.nodeName == TableName) {
                tNode = xmlData.documentElement.childNodes.item(0);

            } else {
                for (i = 1; i < recCnt; i++) {
                    //次の子ﾉｰﾄﾞを取得
                    objNode = objNode.nextSibling;
                    if (objNode.nodeName == TableName) {
                        tNode = xmlData.documentElement.childNodes.item(i);
                        break;
                    }
                }
            }
        }

        //Recﾉｰﾄﾞの追加
        rNode = xmlBuff.createNode(1, "rec", "");
        tNode.appendChild(rNode);
        xmlBuff.appendChild(fNode);
        xmlData.load(xmlBuff);
    }

    return true;

}

//========================================================================================
// 選択行削除(テーブル指定)
// 作成者：M.Jinbo
// 作成日：2009/03/13
// 引数  ：①xmlTable ：リスト用XML
// 戻り値：なし
// 概要  ：選択行を削除する。
//========================================================================================
function funRowDelete_Tbl(xmlData, TableName, RowNo) {

    var objNode;
    var fNode;
    var tNode;
    var ndDel;
    var recCnt;
    var i;

    //現状のﾉｰﾄﾞを取得
    fNode = xmlData.documentElement;

    //子ﾉｰﾄﾞの数をﾁｪｯｸする
    recCnt = xmlData.documentElement.childNodes.length;
    if (recCnt == 0) {
        tNode = xmlBuff.createNode(1, TableName, "");
    } else {
        //先頭の子ﾉｰﾄﾞを取得
        objNode = xmlData.documentElement.firstChild;
        if (objNode.nodeName == TableName) {
            tNode = xmlData.documentElement.childNodes.item(0);

        } else {
            for (i = 1; i < recCnt; i++) {
                //次の子ﾉｰﾄﾞを取得
                objNode = objNode.nextSibling;
                if (objNode.nodeName == TableName) {
                    tNode = xmlData.documentElement.childNodes.item(i);
                    break;
                }
            }
        }
    }

    //選択行を削除
    ndDel = tNode.childNodes.item(RowNo);
    tNode.removeChild(ndDel);

    return true;

}

