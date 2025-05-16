//========================================================================================
// 初期表示処理
// 作成者：E.Kitazawa
// 作成日：2016/06/22
// 概要  ：画面の初期表示処理を行う
//========================================================================================
function funLoad() {

	var frm = document.frm00;    //ﾌｫｰﾑへの参照
	var cpyMoto = new Array();		//コピー元サンプル名
	var cpySaki = new Array();		//コピー先サンプル名
	var arrSample;					//[0]サンプル名、[1]コピー先flg

	//サンプル数
	var cntSample = window.dialogArguments.length;

    for(var i = 0; i < cntSample; i++ ){
    	//[0]サンプル名、[1]コピー先flg
    	arrSample = window.dialogArguments[i].split(ConDelimiter);

//20160826 KPX@1502111_No.10 DEL Start
//    	//コピー元サンプルに追加
//    	cpyMoto.push(arrSample[0]);
//20160826 KPX@1502111_No.10 DEL End
    	
    	//コピー先への追加フラグ
    	if (arrSample[1] == "0") {
    		//試算中止でなく項目固定チェックOFFの場合
    		cpySaki.push(arrSample[0]);
    	} else {
    		//試算中止、又は項目固定チェックONの場合
    		// 空文字を追加（列番号をずらさない為）
    		cpySaki.push("");
    	}
    	
//20160826 KPX@1502111_No.10 ADD Start
    	// コピー元への追加フラグ
    	if (arrSample[2] == "0") {
    		//項目に値の入力がある場合
    		cpyMoto.push(arrSample[0]);
    	} else {
    		//項目に値の入力が無い場合
    		cpyMoto.push("");
    	}
//20160826 KPX@1502111_No.10 ADD End
    }

    //コピー元サンプルコンボブックスの設定
    funCreateComboBox(frm.selCopyMoto, cpyMoto, 1);
    //コピー先サンプルコンボブックスの設定
    funCreateComboBox(frm.selCopySaki, cpySaki, 1);

    return true;

}


//========================================================================================
// コンボボックス作成処理
// 作成者：E.Kitazawa
// 作成日：2016/06/22
// 引数  ：①obj      ：コンボボックスオブジェクト
//       ：②arrData  ：設定配列
//       ：③karaFg   ：空白選択の設定（0：空白無し、1：空白有り）
// 概要  ：コンボボックスを作成する
//========================================================================================
function funCreateComboBox(obj, arrData, karaFg) {

    var frm = document.frm00;    //ﾌｫｰﾑへの参照
    var reccnt;
    var objNewOption;

    //ｺﾝﾎﾞﾎﾞｯｸｽのｸﾘｱ
    funClearSelect(obj, 2);

    //件数取得
    reccnt = arrData.length;
    //ﾃﾞｰﾀが存在しない場合
    if (reccnt == 0) {
        //処理を中断
        return true;
    }

    //コンボボックスの設定
    for (var i = 0; i < reccnt; i++) {
    	//サンプル名が空の場合追加しない
    	if (arrData[i] != "") {
    		//サンプル名の追加
    		objNewOption = document.createElement("option");
    		obj.options.add(objNewOption);
    		objNewOption.innerText = arrData[i];
    		objNewOption.value = i;
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
// サンプルコピー選択：確定ボタン
// 作成者：E.Kitazawa
// 作成日：2016/06/22
// 引数  ：なし
// 戻り値：なし
// 概要  ：選択情報を返す
//========================================================================================
function funCopy(){

	var frm = document.frm00;

	var sel1 = frm.selCopyMoto.options[frm.selCopyMoto.selectedIndex].value;
	var sel2 = frm.selCopySaki.options[frm.selCopySaki.selectedIndex].value;

	if (sel1 == "" || sel2 == "") {
		//サンプルを選択していない
        funErrorMsgBox(E000045);
        return false;
	}

	//戻り値の設定
    var arrRtnVal = new Array(sel1, sel2);
    //選択したサンプル列を返す
	window.returnValue = arrRtnVal;

	//画面を閉じる
    close(self);
}

//========================================================================================
// 画面終了
// 作成者：E.Kitazawa
// 作成日：2016/06/22
// 引数  ：なし
// 戻り値：なし
// 概要  ：画面を閉じる。
//========================================================================================
function funClose() {

	//戻り値の設定
	window.returnValue = new Array("false", "");

    //画面を閉じる
    close(self);
}

