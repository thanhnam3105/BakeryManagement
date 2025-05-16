//========================================================================================
// メール送信処理 [MailControl.js]
// 作成者：E.Kitazawa
// 作成日：2015/03/03
// 概要  ：メール送信に対しての操作を行う。【QP@40812】
//========================================================================================

//メール定型文
// 2015/08/26 MOD start
// 件名は「試作名」に変更
var MailSubject = "【原価試算ステータス更新のお知らせ】";
//var MailBody1 = "以下の試作のステータスを更新しましたのでお知らせします。\n";
var MailBody1 = "表題の件、原価試算システムのステータスを更新致しました。\n";
//var MailBody2 = "\n以上、よろしくお願いいたします。\n";
var MailBody2 = "\n以上、宜しくお願い致します。\n";
// 2015/08/26 MOD end


//========================================================================================
// 連絡メール送信（原価試算ステータス更新）
// 作成者：E.Kitazawa
// 作成日：2015/03/03
// 引数  ：①userObj   ：ユーザ情報
//       ：②arySisan  ：試作情報 （試作No., 試作名, 試算期日、サンプルNo.）
//       ：③arySt     ：現在のステータス情報 （研究所, 生管, 原調, 工場, 営業）
// 戻り値：なし
// 概要  ：確認メッセージを表示する。
//========================================================================================
function funMailGenkaSisan(userObj, arySisan, arySt) {

	var mailbody = "";

	//試作No
	// 2015/08/26 MOD start
//	mailbody += "\n　・試作No　　：　";
	mailbody += "\n◆試作№　　　：　";
	mailbody +=  arySisan[0];
	//品名
//	mailbody += "\n　・品名　　　　：　";
	mailbody += "\n◆試作名　　　：　";
	mailbody += arySisan[1];
	//サンプルNo.
	mailbody += "\n◆サンプル№：　";
	mailbody +=  arySisan[3];

	//試算期日
//	mailbody += "\n　・試算期日　：　";
	mailbody += "\n◆試算期日　 ：　";
	mailbody +=  arySisan[2];

	//現在のステータス
/* ---------- 不要 -----------
*	mailbody += "\n\n　・現在のステータス";
*	mailbody += "\n　　　研究所：" ;
*	mailbody += arySt[0];
*	mailbody += "　生管：" ;
*	mailbody += arySt[1];
*	mailbody += "　原調：" ;
*	mailbody += arySt[2];
*	mailbody += "　工場：" ;
*	mailbody += arySt[3];
*	mailbody += "　営業：" ;
*	mailbody += arySt[4];
--------------------- */
	mailbody += "\n\n";

	//定型文を追加
	mailbody = MailBody1 + mailbody + MailBody2;

/* ---------- 不要 -----------
	//作業担当者
*	mailbody += "\n====================================";
*	mailbody += "\n　　　所属会社：";
*	mailbody += funXmlRead(userObj, "nm_kaisha", 0);
*	mailbody += "\n　　　所属部署：";
*	mailbody += funXmlRead(userObj, "nm_busho", 0);
*	mailbody += "\n　　　担当者　：";
*	mailbody += funXmlRead(userObj, "nm_user", 0);
*	mailbody += "\n====================================";
--------------------- */

//	mail_url = encodeURI ('mailto:'  + '?subject=' + MailSubject + '&body=' + mailbody);
	mail_url = encodeURI ('mailto:'  + '?subject=' + arySisan[1] + '&body=' + mailbody);
	// 2015/08/26 MOD start

	//メール送信画面を起動
	location.href = mail_url;

	return true;

}
