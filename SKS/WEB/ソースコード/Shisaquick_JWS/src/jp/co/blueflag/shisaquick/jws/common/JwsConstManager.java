package jp.co.blueflag.shisaquick.jws.common;

import java.awt.Color;

/**
 *  定数定義クラス
 */
public final class JwsConstManager{

	//-------------------------- JWSバージョン -------------------------------
	public static String JWS_VERSION = "ver_2.41";

	//---------------------------- 描画クラス  --------------------------------
	public static String UI_CLASS_NAME = "javax.swing.plaf.metal.MetalLookAndFeel";
	//public static String UI_CLASS_NAME = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	//------------------------- キユーピーコード ------------------------------
	public static String JWS_CD_KEWPIE = "1";

	//-------------------------- 単位「ml」コード ------------------------------
	public static String JWS_CD_TANI = "001";

	//--------------------- 原料コード桁数（デフォルト） -------------------------
	public static String JWS_KETA_GENRYO = "6";

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
	//--------------------- 最大行数（原料行＋工程行） -------------------------
	public static int JWS_ROW_MAX = 150;
//add end --------------------------------------------------------------------------------------

	//---------------------------- モード定義  --------------------------------
	//参照
	public static String JWS_MODE_0000 = "000";
	//詳細
	public static String JWS_MODE_0001 = "100";
	//新規
	public static String JWS_MODE_0002 = "110";
	//製法コピー
	public static String JWS_MODE_0003 = "120";

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
	//試作コピーモード編集パターン
	public static String JWS_MODE_0004 = "130";
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end


	//-------------------------- 各色定数定義  -------------------------------
	//テーブル選択色
	public static final Color TABLE_SELECTED_COLOR = new Color(124,252,0);
	//テーブル行選択色
	public static final Color TABLE_GYO_SELECTED_COLOR = new Color(175,226,255);
	//項目背景色（青色）
	public static final Color SHISAKU_ITEM_COLOR = new Color(175,226,255);
	//項目背景色（黄色）
	public static final Color SHISAKU_ITEM_COLOR2 = new Color(255,255,153);
	//項目背景色（タイトル）
	public static final Color SHISAKU_TITLE_COLOR = new Color(0,102,204);
	//項目背景色（レベル）
	public static final Color SHISAKU_LEVEL_COLOR = new Color(238,0,0);
	//項目背景色（F2編集項目）
	public static final Color SHISAKU_F2_COLOR = new Color(-3342388);
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
	//項目背景色（ピンク色）
	public static final Color SHISAKU_ITEM_COLOR3 = new Color(255,175,175);
	//項目背景色（白色）
	public static final Color SHISAKU_ITEM_COLOR4 = new Color(255,255,255);
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End


	//---------------------------- 記号定義  ----------------------------------
	//会社外原料
	public static String JWS_MARK_0001 = "★";
	//同一会社原料
	public static String JWS_MARK_0002 = "☆";
	//製法
	public static String JWS_MARK_0003 = "＊";
	//原価試算
	public static String JWS_MARK_0004 = "$";
	//原料費計算項目
	public static String JWS_MARK_0005 = "※";

	//--------------------------- タイトル定義  --------------------------------
	//システムID＋システム名
	public static String JWS_TITLE = "シサクイックシステム（04_0043_0001）";

	//--------------------------- メッセージ定義  --------------------------------
	public static String JWS_ERROR_0021 = "工程属性が混在しています";
	//2011/04/13 QP@10181_No.26 TT T.Satoh Change Start -------------------------
	//public static String JWS_ERROR_0022 = "試作表①の工程名を選択してください";
	public static String JWS_ERROR_0022 = "配合表の工程名を選択してください";
	//public static String JWS_ERROR_0023 = "試作表①の工程名を選択してください";
	public static String JWS_ERROR_0023 = "配合表の工程名を選択してください";
	//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.1
	public static String JWS_ERROR_0024 = "最大行数が" + JWS_ROW_MAX + "行を超えました。行を追加できません";
//add end --------------------------------------------------------------------------------------

//2011/05/19　【QP@10181_No.12】全コピー列指定　TT.NISHIGAWA　start
	public static String JWS_ERROR_0025 = "コピーするサンプル列が選択されていません。\nコピー対象の印刷FGをチェックして下さい。";
	public static String JWS_ERROR_0026 = "下記サンプルNoをコピーします。よろしいですか？";
	public static String JWS_ERROR_0027 = "15";
	public static String JWS_ERROR_0028 = "※" + JWS_ERROR_0027 + "列までしか表示されません。";
//2011/05/19　【QP@10181_No.12】全コピー列指定　TT.NISHIGAWA　end

	public static String JWS_ERROR_0029 = "分析値マスタの最新情報に更新します。よろしいですか？";

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.33,34
	public static String JWS_ERROR_0040 = "原価試作依頼されているため工程削除できません。";
	public static String JWS_ERROR_0041 = "原価試作依頼されているため移動できません。";
	public static String JWS_ERROR_0042 = "原価試作依頼されているため原料削除できません。";
	public static String JWS_ERROR_0043 = "原価試作依頼されているため列削除できません。";
	public static String JWS_ERROR_0045 = "他試作の計算を行います。よろしいですか？";
	public static String JWS_ERROR_0046 = "他試作の計算を行います。よろしいですか？。\n※貼り付け先のセルの値も再計算に含まれる可能性があります。";
	public static String JWS_ERROR_0047 = "他列の計算で使用されています。削除しますか？";
	public static boolean JWS_FLG_DISP = false; //再表示フラグ
	public static Color JWS_DISABLE_COLOR = new Color(220, 220, 220);
//add end   -------------------------------------------------------------------------------

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.2
	public static String JWS_ERROR_0048 = "下記サンプルNoが重複しています。変更して下さい。\n";
//add end   -------------------------------------------------------------------------------

//2011/05/19　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
// MOD start 20121009 QP@20505 No.24
//	public static String JWS_CONFIRM_0010 = "工程パターンを変更します。\n変更後は、工程属性の再確認をお願いします。";
	public static String JWS_CONFIRM_0010 = "工程パターンを変更します。\n工程と単位がクリアされます。選択してください。";
// MOD end 20121009 QP@20505 No.24
	public static String JWS_CONFIRM_0011 = "「水相比重」をクリアしますがよろしいでしょうか？";
// ADD start 20121003 QP@20505 No.24
	public static String JWS_CONFIRM_0012 = "「製品比重」をクリアしますがよろしいでしょうか？";
	public static String JWS_CONFIRM_0013 = "「製品比重」と「特性値」をクリアしますがよろしいでしょうか？";
	public static String JWS_CONFIRM_0014 = "「特性値」をクリアしますがよろしいでしょうか？";
	public static String JWS_CONFIRM_0015 = "「水相比重」と「特性値」をクリアしますがよろしいでしょうか？";
	public static String JWS_CONFIRM_0016 = "「合計仕上重量」と「特性値」をクリアしますがよろしいでしょうか？";
// ADD end 20121003 QP@20505 No.24
	public static String JWS_KOTEITYPE_1 = "1";
	public static String JWS_KOTEITYPE_2 = "2";
	public static String JWS_KOTEITYPE_3 = "3";
//2011/05/19　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

//add start -------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.7
	//--------------------------- コピー先計算  ------------------------------
	//コピー先計算可否
	public static boolean JWS_COPY_0002 = true;
	//括弧開き
	public static String JWS_COPY_0003 = "(";
	//括弧閉じ
	public static String JWS_COPY_0004 = ")";
	//試作SEQ設定文字列
	public static String JWS_COPY_0005 = ":::";
//add end   -------------------------------------------------------------------------------

	//------------------------ コンポーネント名定義  ------------------------------
	//試作CD-ユーザ
	public static String JWS_COMPONENT_0001 = "試作CD-ユーザ";
	//試作CD-年
	public static String JWS_COMPONENT_0002 = "試作CD-年";
	//試作CD-追番
	public static String JWS_COMPONENT_0003 = "試作CD-追番";
	//依頼番号
	public static String JWS_COMPONENT_0004 = "依頼番号";
	//品名
	public static String JWS_COMPONENT_0005 = "品名";
	//製法No-ユーザ
	public static String JWS_COMPONENT_0006 = "製法No-ユーザ";
	//製法No-種別
	public static String JWS_COMPONENT_0007 = "製法No-種別";
	//製法No-年
	public static String JWS_COMPONENT_0008 = "製法No-年";
	//製法No-追番
	public static String JWS_COMPONENT_0009 = "製法No-追番";
	//廃止
	public static String JWS_COMPONENT_0010 = "廃止";
	//登録
	public static String JWS_COMPONENT_0011 = "登録";
	//終了
	public static String JWS_COMPONENT_0012 = "終了";
	//特徴コピー
	public static String JWS_COMPONENT_0013 = "特徴コピー";
	//全コピー
	public static String JWS_COMPONENT_0014 = "全コピー";
	//種別番号
	public static String JWS_COMPONENT_0015 = "種別番号";
	//試作選択
	public static String JWS_COMPONENT_0016 = "試作選択";
	//注意事項No
	public static String JWS_COMPONENT_0017 = "注意事項No";
	//日付
	public static String JWS_COMPONENT_0018 = "日付";
	//ｻﾝﾌﾟﾙNo
	public static String JWS_COMPONENT_0019 = "ｻﾝﾌﾟﾙNo";
	//メモ
	public static String JWS_COMPONENT_0020 = "メモ";
	//印刷Fg
	public static String JWS_COMPONENT_0021 = "印刷Fg";
	//工程選択
	public static String JWS_COMPONENT_0022 = "工程選択";
	//原料選択
	public static String JWS_COMPONENT_0023 = "原料選択";
	//工程属性
	public static String JWS_COMPONENT_0024 = "工程属性";
	//原料CD
	public static String JWS_COMPONENT_0025 = "原料CD";
	//工程名
	public static String JWS_COMPONENT_0026 = "工程名";
	//原料名
	public static String JWS_COMPONENT_0027 = "原料名";
	//単価
	public static String JWS_COMPONENT_0028 = "単価";
	//歩留
	public static String JWS_COMPONENT_0029 = "歩留";
	//油含有率
	public static String JWS_COMPONENT_0030 = "油含有率";
	//量
	public static String JWS_COMPONENT_0031 = "量";
	//製造工程
	public static String JWS_COMPONENT_0032 = "製造工程";
	//原料一覧
	public static String JWS_COMPONENT_0033 = "原料一覧";
	//原料分析
	public static String JWS_COMPONENT_0034 = "原料分析";
	//工程挿入
	public static String JWS_COMPONENT_0035 = "工程挿入";
	//工程移動（↑）
	public static String JWS_COMPONENT_0036 = "工程移動上";
	//工程移動（↓）
	public static String JWS_COMPONENT_0037 = "工程移動下";
	//工程削除
	public static String JWS_COMPONENT_0038 = "工程削除";
	//原料挿入
	public static String JWS_COMPONENT_0039 = "原料挿入";
	//原料移動（↑）
	public static String JWS_COMPONENT_0040 = "原料移動上";
	//原料移動（↓）
	public static String JWS_COMPONENT_0041 = "原料移動下";
	//原料削除
	public static String JWS_COMPONENT_0042 = "原料削除";
	//試作列追加
	public static String JWS_COMPONENT_0043 = "試作列追加";
	//試作列削除
	public static String JWS_COMPONENT_0044 = "試作列削除";
	//試作列コピー
	public static String JWS_COMPONENT_0045 = "試作列コピー";
	//試作列移動（←）
	public static String JWS_COMPONENT_0046 = "試作列移動左";
	//試作列移動（→）
	public static String JWS_COMPONENT_0047 = "試作列移動右";
	//試作表出力
	public static String JWS_COMPONENT_0048 = "試作表出力";
	//ｻﾝﾌﾟﾙ説明書出力
	public static String JWS_COMPONENT_0049 = "ｻﾝﾌﾟﾙ説明書出力";
	//製造工程選択
	public static String JWS_COMPONENT_0050 = "製造工程選択";
	//内容入力
	public static String JWS_COMPONENT_0051 = "内容入力";
	//常に表示
	public static String JWS_COMPONENT_0052 = "常に表示";
	//新規
	public static String JWS_COMPONENT_0053 = "新規";
	//更新
	public static String JWS_COMPONENT_0054 = "更新";
	//自動計算
	public static String JWS_COMPONENT_0055 = "自動計算";
	//総酸・食塩出力
	public static String JWS_COMPONENT_0056 = "総酸食塩出力";
	//水相中出力
	public static String JWS_COMPONENT_0057 = "水相中出力";
	//糖度出力
	public static String JWS_COMPONENT_0058 = "糖度出力";
	//粘度・温度出力
	public static String JWS_COMPONENT_0059 = "粘度温度出力";
	//PH出力
	public static String JWS_COMPONENT_0060 = "PH出力";
	//総酸・食塩出力
	public static String JWS_COMPONENT_0061 = "総酸食塩出力";
	//比重出力
	public static String JWS_COMPONENT_0062 = "比重出力";
	//水分活性出力
	public static String JWS_COMPONENT_0063 = "水分活性出力";
	//アルコール出力
	public static String JWS_COMPONENT_0064 = "アルコール出力";
	//フリータイトル1出力
	public static String JWS_COMPONENT_0065 = "フリータイトル1出力";
	//フリータイトル2出力
	public static String JWS_COMPONENT_0066 = "フリータイトル2出力";
	//フリータイトル3出力
	public static String JWS_COMPONENT_0067 = "フリータイトル3出力";
	//一括チェック
	public static String JWS_COMPONENT_0068 = "一括チェック";
	//フリータイトル1
	public static String JWS_COMPONENT_0069 = "フリータイトル1";
	//フリータイトル2
	public static String JWS_COMPONENT_0070 = "フリータイトル2";
	//フリータイトル3
	public static String JWS_COMPONENT_0071 = "フリータイトル3";
	//総酸
	public static String JWS_COMPONENT_0072 = "総酸";
	//食塩
	public static String JWS_COMPONENT_0073 = "食塩";
	//水相中酸度
	public static String JWS_COMPONENT_0074 = "水相中酸度";
	//水相中食塩
	public static String JWS_COMPONENT_0075 = "水相中食塩";
	//水相中酢酸
	public static String JWS_COMPONENT_0076 = "水相中酢酸";
	//糖度
	public static String JWS_COMPONENT_0077 = "糖度";
	//粘度
	public static String JWS_COMPONENT_0078 = "粘度";
	//温度
	public static String JWS_COMPONENT_0079 = "温度";
	//PH
	public static String JWS_COMPONENT_0080 = "PH";
	//総酸分析
	public static String JWS_COMPONENT_0081 = "総酸分析";
	//食塩分析
	public static String JWS_COMPONENT_0082 = "食塩分析";
	//比重
	public static String JWS_COMPONENT_0083 = "比重";
	//水分活性
	public static String JWS_COMPONENT_0084 = "水分活性";
	//アルコール
	public static String JWS_COMPONENT_0085 = "アルコール";
	//フリー内容1
	public static String JWS_COMPONENT_0086 = "フリー内容1";
	//フリー内容2
	public static String JWS_COMPONENT_0087 = "フリー内容2";
	//フリー内容3
	public static String JWS_COMPONENT_0088 = "フリー内容3";
	//作成メモ
	public static String JWS_COMPONENT_0089 = "作成メモ";
	//評価
	public static String JWS_COMPONENT_0090 = "評価";
	//分析値変更確認
	public static String JWS_COMPONENT_0091 = "分析値変更確認";
// ADD start 20121016 QP@20505 No.11
	//原料分析マスタ値取得
	public static String JWS_COMPONENT_0155 = "原料分析マスタ値取得";   // 155
// ADD end 20121016 QP@20505 No.11
	//所属グループ
	public static String JWS_COMPONENT_0092 = "所属グループ";
	//所属チーム
	public static String JWS_COMPONENT_0093 = "所属チーム";
	//一括表示
	public static String JWS_COMPONENT_0094 = "一括表示";
	//ジャンル
	public static String JWS_COMPONENT_0095 = "ジャンル";
	//ユーザ
	public static String JWS_COMPONENT_0096 = "ユーザ";
	//特徴原料
	public static String JWS_COMPONENT_0097 = "特徴原料";
	//用途
	public static String JWS_COMPONENT_0098 = "用途";
	//価格帯
	public static String JWS_COMPONENT_0099 = "価格帯";
	//種別
	public static String JWS_COMPONENT_0100 = "種別";
	//少数指定
	public static String JWS_COMPONENT_0101 = "少数指定";
//ADD 2013/06/19 ogawa 【QP@30151】No.9 start
	//販責会社
	public static String JWS_COMPONENT_0156 = "販責会社";
//ADD 2013/06/19 ogawa 【QP@30151】No.9 end
	//担当会社
	public static String JWS_COMPONENT_0102 = "担当会社";
	//担当工場
	public static String JWS_COMPONENT_0103 = "担当工場";
	//担当営業
	public static String JWS_COMPONENT_0104 = "担当営業";
	//製造方法
	public static String JWS_COMPONENT_0105 = "製造方法";
	//充填方法
	public static String JWS_COMPONENT_0106 = "充填方法";
	//殺菌方法
	public static String JWS_COMPONENT_0107 = "殺菌方法";
	//容器包材
	public static String JWS_COMPONENT_0108 = "容器包材";
	//容量
	public static String JWS_COMPONENT_0109 = "容量";
	//容量（単位）
	public static String JWS_COMPONENT_0110 = "容量（単位）";
	//入り数
	public static String JWS_COMPONENT_0111 = "入り数";
	//荷姿
	public static String JWS_COMPONENT_0112 = "荷姿";
	//取扱温度
	public static String JWS_COMPONENT_0113 = "取扱温度";
	//賞味期間
	public static String JWS_COMPONENT_0114 = "賞味期間";
	//原価希望
	public static String JWS_COMPONENT_0115 = "原価希望";
	//売価希望
	public static String JWS_COMPONENT_0116 = "売価希望";
	//想定物量
	public static String JWS_COMPONENT_0117 = "想定物量";
	//販売時期
	public static String JWS_COMPONENT_0118 = "販売時期";
	//計画売上
	public static String JWS_COMPONENT_0119 = "計画売上";
	//計画利益
	public static String JWS_COMPONENT_0120 = "計画利益";
	//販売後売上
	public static String JWS_COMPONENT_0121 = "販売後売上";
	//販売後利益
	public static String JWS_COMPONENT_0122 = "販売後利益";
	//総合メモ
	public static String JWS_COMPONENT_0123 = "総合メモ";
	//仕上重量
	public static String JWS_COMPONENT_0124 = "仕上重量";
	//試作リストコピー
	public static String JWS_COMPONENT_0125 = "試作リストコピー";
	//栄養計算書出力
	public static String JWS_COMPONENT_0126 = "栄養計算書出力";
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
	//シークレット
	public static String JWS_COMPONENT_0190 = "シークレット";
	//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

// ADD start 20120928 QP@20505 No.24
	//水分活性フリータイトル
	public static String JWS_COMPONENT_0160 = "水分活性フリータイトル";
	//水分活性フリー内容
	public static String JWS_COMPONENT_0161 = "水分活性フリー内容";
	//水分活性フリー出力
	public static String JWS_COMPONENT_0162 = "水分活性フリー出力";
	//アルコール フリータイトル
	public static String JWS_COMPONENT_0163 = "アルコールフリータイトル";
	//アルコール フリー内容
	public static String JWS_COMPONENT_0164 = "アルコールフリー内容";
	//アルコール フリー出力
	public static String JWS_COMPONENT_0165 = "アルコールフリー出力";
	//実行酢酸濃度
	public static String JWS_COMPONENT_0166 = "実行酢酸濃度";
	//実行酢酸濃度 出力
	public static String JWS_COMPONENT_0167 = "実行酢酸濃度出力";
	//水相中ＭＳＧ
	public static String JWS_COMPONENT_0168 = "水相中ＭＳＧ";
	//水相中ＭＳＧ出力
	public static String JWS_COMPONENT_0169 = "水相中ＭＳＧ出力";
	//粘度フリータイトル
	public static String JWS_COMPONENT_0170 = "粘度フリータイトル";
	//粘度フリー内容
	public static String JWS_COMPONENT_0171 = "粘度フリー内容";
	//粘度フリー出力
	public static String JWS_COMPONENT_0172 = "粘度フリー出力";
	//温度フリータイトル
	public static String JWS_COMPONENT_0173 = "温度フリータイトル";
	//温度フリー内容
	public static String JWS_COMPONENT_0174 = "温度フリー内容";
	//温度フリー出力
	public static String JWS_COMPONENT_0175 = "温度フリー出力";
	//フリータイトル4
	public static String JWS_COMPONENT_0176 = "フリータイトル4";
	//フリー内容4
	public static String JWS_COMPONENT_0177 = "フリー内容4";
	//フリータイトル4出力
	public static String JWS_COMPONENT_0178 = "フリータイトル4出力";
	//フリータイトル5
	public static String JWS_COMPONENT_0179 = "フリータイトル5";
	//フリー内容5
	public static String JWS_COMPONENT_0180 = "フリー内容5";
	//フリータイトル5出力
	public static String JWS_COMPONENT_0181 = "フリータイトル5出力";
	//フリータイトル6
	public static String JWS_COMPONENT_0182 = "フリータイトル6";
	//フリー内容6
	public static String JWS_COMPONENT_0183 = "フリー内容6";
	//フリータイトル6出力
	public static String JWS_COMPONENT_0184 = "フリータイトル6出力";
// ADD end 20120928 QP@20505 No.24

	//-----------------原価試算(試作表⑤)---------------------------------
	//印刷FG
	public static String JWS_COMPONENT_0149 = "印刷FG";
	//有効歩留
	public static String JWS_COMPONENT_0127 = "有効歩留";
	//平均充填量
	public static String JWS_COMPONENT_0128 = "平均充填量";
	//経費
	public static String JWS_COMPONENT_0129 = "固定費";
	//売価
	public static String JWS_COMPONENT_0130 = "売価";
	//試算確定サンプルNo
	public static String JWS_COMPONENT_0131 = "試算確定サンプルNo";
	//試算履歴参照
	public static String JWS_COMPONENT_0132 = "試算履歴参照";
	//原価試算表印刷
	public static String JWS_COMPONENT_0133 = "原価試算表印刷";
	//充填量水相
	public static String JWS_COMPONENT_0134 = "充填量水相";
	//充填量油相
	public static String JWS_COMPONENT_0135 = "充填量油相";
	//合計量
	public static String JWS_COMPONENT_0136 = "合計量";
	//原料費(kg)
	public static String JWS_COMPONENT_0137 = "原料費(kg)";
	//原料費(１本当)
	public static String JWS_COMPONENT_0138 = "原料費(１本当)";
	//比重
	public static String JWS_COMPONENT_0139 = "比重";
	//容量
	public static String JWS_COMPONENT_0140 = "容量";
	//入数
	public static String JWS_COMPONENT_0141 = "入数";
	//レベル量
	public static String JWS_COMPONENT_0142 = "レベル量";
	//比重加算量
	public static String JWS_COMPONENT_0143 = "比重加算量";
	//材料費
	public static String JWS_COMPONENT_0145 = "材料費";
	//原価計/cs
	public static String JWS_COMPONENT_0146 = "原価計/cs";
	//原価計/個
	public static String JWS_COMPONENT_0147 = "原価計/個";
	//粗利
	public static String JWS_COMPONENT_0148 = "粗利";
	//原価試算登録
	public static String JWS_COMPONENT_0150 = "原価試算登録";
	//原価試算依頼Fg
	public static String JWS_COMPONENT_0151 = "原価試算依頼Fg";

	//一つ戻る
	public static String JWS_COMPONENT_0152 = "一つ戻る";

//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.8
	//代表工場CD（研究所）
	public static String JWS_CD_DAIHYO_KOJO = "";
	//代表会社CD（研究所）
	public static String JWS_CD_DAIHYO_KAISHA = "";
//add end----------------------------------------------------------------------------------------
//add start --------------------------------------------------------------------------------------
//QP@00412_シサクイック改良 No.4
	//使用実績名
	public static String JWS_NM_SHIYO = "";
//add end-----------------------------------------------------------------------------------------

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
	public static String JWS_COMPONENT_0153 = "工程パターン";
	public static String JWS_COMPONENT_0154 = "水相比重";
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

}