package jp.co.blueflag.shisaquick.jws.common;

import java.util.ArrayList;

/*******************************************************************************************
 *
 * 処理モード指定
 *
 *******************************************************************************************/
public class ModeCtrl {

	//参照モード編集パターン
	private ArrayList aryModeCheck_Sansho = new ArrayList();
	//詳細モード編集パターン
	private ArrayList aryModeCheck_Shosai = new ArrayList();
	//新規モード編集パターン
	private ArrayList aryModeCheck_Sinki = new ArrayList();
	//製法コピーモード編集パターン
	private ArrayList aryModeCheck_Copy = new ArrayList();

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
	//試作コピーモード編集パターン
	private ArrayList aryModeCheck_ShisakuCopy = new ArrayList();
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end

	ExceptionBase ex;

	/*****************************************************************************************
	 *
	 * コンストラクタ
	 * 　：コンポーネント編集パターンを登録する
	 * 　@author TT nishigawa
	 *
	 *****************************************************************************************/
	public ModeCtrl() throws ExceptionBase{
		try{

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
/*******************************************************************************************
 *
 * 試作コピーモード
 *
 *******************************************************************************************/
			//------------------------ 試作データ画面 -----------------------------
			//試作CD-ユーザ
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0001 , "false"});
			//試作CD-年
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0002 , "false"});
			//試作CD-追番
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0003 , "false"});
			//依頼番号
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0004 , "false"});
			//品名
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0005 , "false"});
			//製法No-ユーザ
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0006 , "false"});
			//製法No-種別CD
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0007 , "false"});
			//製法No-種別No
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0008 , "false"});
			//製法No-追番
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0009 , "false"});
			//廃止
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0010 , "false"});
			//登録
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0011 , "false"});
			//終了
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0012 , "true"});
			//特徴コピー
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0013 , "true"});
			//全コピー
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0014 , "true"});
			//種別番号
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0015 , "false"});

			//一つ戻る
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0152 , "false"});

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0153 , "false"});
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
			//シークレット
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0190 , "false"});
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
			
			//--------------------------- 配合表(試作表①) --------------------------------
			//試作選択
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0016 , "true"});
			//注意事項No
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0017 , "false"});
			//日付
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0018 , "false"});
			//ｻﾝﾌﾟﾙNo
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0019 , "false"});
			//メモ
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0020 , "false"});
			//印刷Fg
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0021 , "true"});
			//工程選択
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0022 , "false"});
			//原料選択
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0023 , "false"});
			//工程属性
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0024 , "false"});
			//原料CD
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0025 , "false"});
			//工程名
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0026 , "false"});
			//原料名
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0027 , "false"});
			//単価
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0028 , "false"});
			//歩留
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0029 , "false"});
			//油含有率
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0030 , "false"});
			//量
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0031 , "false"});
			//製造工程
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0032 , "true"});
			//原料一覧
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0033 , "false"});
			//原料分析
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0034 , "false"});
			//工程挿入
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0035 , "false"});
			//工程移動（↑）
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0036 , "false"});
			//工程移動（↓）
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0037 , "false"});
			//工程削除
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0038 , "false"});
			//原料挿入
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0039 , "false"});
			//原料移動（↑）
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0040 , "false"});
			//原料移動（↓）
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0041 , "false"});
			//原料削除
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0042 , "false"});
			//試作列追加
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0043 , "false"});
			//試作列削除
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0044 , "false"});
			//試作列コピー
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0045 , "false"});
			//試作列移動（←）
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0046 , "false"});
			//試作列移動（→）
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0047 , "false"});
			//試作表出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0048 , "false"});
			//ｻﾝﾌﾟﾙ説明書出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0049 , "false"});
			//仕上重量
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0124 , "false"});
			//試作リストコピー
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0125 , "false"});
			//栄養計算書出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0126 , "false"});

			//--------------------------- 製造工程 --------------------------------
			//製造工程選択
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0050 , "true"});
			//内容入力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0051 , "false"});
			//常に表示
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0052 , "false"});
			//新規
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0053 , "false"});
			//更新
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0054 , "false"});

			//--------------------------- 特性値(試作表②) --------------------------------
			//自動計算
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0055 , "false"});
			//総酸・食塩出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0056 , "false"});
			//水相中出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0057 , "false"});
			//糖度出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0058 , "false"});
			//粘度・温度出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0059 , "false"});
			//PH出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0060 , "false"});
			//総酸・食塩出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0061 , "false"});
			//比重出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0062 , "false"});
			//水分活性出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0063 , "false"});
			//アルコール出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0064 , "false"});
			//フリータイトル1出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0065 , "false"});
			//フリータイトル2出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0066 , "false"});
			//フリータイトル3出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0067 , "false"});
			//一括チェック
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0068 , "false"});
			//フリータイトル1
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0069 , "false"});
			//フリータイトル2
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0070 , "false"});
			//フリータイトル3
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0071 , "false"});
			//総酸
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0072 , "false"});
			//食塩
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0073 , "false"});
			//水相中酸度
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0074 , "false"});
			//水相中食塩
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0075 , "false"});
			//水相中酢酸
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0076 , "false"});
			//糖度
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0077 , "false"});
			//粘度
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0078 , "false"});
			//温度
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0079 , "false"});
			//PH
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0080 , "false"});
			//総酸分析
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0081 , "false"});
			//食塩分析
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0082 , "false"});
			//比重
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0083 , "false"});
			//水分活性
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0084 , "false"});
			//アルコール
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0085 , "false"});
			//フリー内容1
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0086 , "false"});
			//フリー内容2
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0087 , "false"});
			//フリー内容3
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0088 , "false"});
			//作成メモ
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0089 , "false"});
			//評価
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0090 , "false"});
			//分析値変更確認
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0091 , "true"});
// ADD start 20121016 QP@20505 No.11
			//原料分析マスタ値取得
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0155 , "true"});
// ADD end 20121016 QP@20505 No.11
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			//水相比重
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0154 , "false"});
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20121001 QP@20505 No.1
			//水分活性フリータイトル
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0160 , "false"});
			//水分活性フリー内容
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0161 , "false"});
			//水分活性フリー出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0162 , "false"});
			//アルコールフリータイトル
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0163 , "false"});
			//アルコールフリー内容
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0164 , "false"});
			//アルコールフリー出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0165 , "false"});
			//実効酢酸濃度
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0166 , "false"}); // false
			//実効酢酸濃度 出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0167 , "false"});
			//水相中ＭＳＧ
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0168 , "false"}); // false
			//水相中ＭＳＧ 出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0169 , "false"});
			//粘度フリータイトル
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0170 , "false"});
			//粘度フリー内容
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0171 , "false"});
			//粘度フリー内容 出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0172 , "false"});
			//温度フリータイトル
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0173 , "false"});
			//温度フリー内容
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0174 , "false"});
			//温度フリー内容 出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0175 , "false"});
			//フリー④タイトル
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0176 , "false"});
			//フリー④内容
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0177 , "false"});
			//フリー④ 出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0178 , "false"});
			//フリー⑤タイトル
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0179 , "false"});
			//フリー⑤内容
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0180 , "false"});
			//フリー⑤ 出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0181 , "false"});
			//フリー⑥タイトル
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0182 , "false"});
			//フリー⑥内容
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0183 , "false"});
			//フリー⑥ 出力
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0184 , "false"});
// ADD end 20121001 QP@20505 No.1

			//--------------------------- 基本情報(試作表③) --------------------------------
			//所属グループ
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0092 , "false"});
			//所属チーム
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0093 , "false"});
			//一括表示
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0094 , "false"});
			//ジャンル
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0095 , "false"});
			//ユーザ
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0096 , "false"});
			//特徴原料
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0097 , "false"});
			//用途
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0098 , "false"});
			//価格帯
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0099 , "false"});
			//種別
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0100 , "false"});
			//少数指定
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0101 , "false"});
			//担当会社
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0102 , "false"});
			//担当工場
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0103 , "false"});
			//担当営業
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0104 , "false"});
			//製造方法
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0105 , "false"});
			//充填方法
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0106 , "false"});
			//殺菌方法
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0107 , "false"});
			//容器包材
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0108 , "false"});
			//容量
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0109 , "false"});
			//容量（単位）
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0110 , "false"});
			//入り数
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0111 , "false"});
			//荷姿
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0112 , "false"});
			//取扱温度
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0113 , "false"});
			//賞味期間
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0114 , "false"});
			//原価希望
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0115 , "false"});
			//売価希望
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0116 , "false"});
			//想定物量
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0117 , "false"});
			//販売時期
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0118 , "false"});
			//計画売上
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0119 , "false"});
			//計画利益
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0120 , "false"});
			//販売後売上
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0121 , "false"});
			//販売後利益
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0122 , "false"});
			//総合メモ
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0123 , "false"});

			//--------------------------- 原価試算(試作表⑤) --------------------------------
			//印刷FG
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0149 , "false"});
			//有効歩留
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0127 , "false"});
			//平均充填量
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0128 , "false"});
			//経費
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0129 , "false"});
			//売価
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0130 , "false"});
			//試算確定サンプルNo
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0131, "false"});
			//試算履歴参照
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0132, "false"});
			//原価試算表印刷
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0133, "false"});
			//充填量水相
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0134 , "false"});
			//充填量油相
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0135 , "false"});
			//合計量
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0136 , "false"});
			//原料費(kg)
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0137 , "false"});
			//原料費(１本当)
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0138 , "false"});
			//比重
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0139 , "false"});
			//容量
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0140 , "false"});
			//入数
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0141 , "false"});
			//レベル量
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0142 , "false"});
			//比重加算量
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0143 , "false"});
			//材料費
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0145 , "false"});
			//原価計/cs
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0146 , "false"});
			//原価計/個
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0147 , "false"});
			//粗利
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0148 , "false"});
			//原価試算登録
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0150 , "false"});
			//原価試算依頼Fg
			aryModeCheck_ShisakuCopy.add(new String[]{JwsConstManager.JWS_COMPONENT_0151 , "false"});
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end


/*******************************************************************************************
 *
 * 参照モード
 *
 *******************************************************************************************/
			//------------------------ 試作データ画面 -----------------------------
			//試作CD-ユーザ
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0001 , "false"});
			//試作CD-年
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0002 , "false"});
			//試作CD-追番
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0003 , "false"});
			//依頼番号
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0004 , "false"});
			//品名
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0005 , "false"});
			//製法No-ユーザ
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0006 , "false"});
			//製法No-種別CD
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0007 , "false"});
			//製法No-種別No
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0008 , "false"});
			//製法No-追番
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0009 , "false"});
			//廃止
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0010 , "false"});
			//登録
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0011 , "false"});
			//終了
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0012 , "true"});
			//特徴コピー
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0013 , "false"});
			//全コピー
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0014 , "false"});
			//種別番号
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0015 , "false"});

			//一つ戻る
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0152 , "false"});

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0153 , "false"});
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
			
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
			//シークレット
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0190 , "false"});
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End
			
			//--------------------------- 配合表(試作表①) --------------------------------
			//試作選択
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0016 , "true"});
			//注意事項No
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0017 , "false"});
			//日付
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0018 , "false"});
			//ｻﾝﾌﾟﾙNo
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0019 , "false"});
			//メモ
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0020 , "false"});
			//印刷Fg
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0021 , "false"});
			//工程選択
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0022 , "false"});
			//原料選択
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0023 , "false"});
			//工程属性
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0024 , "false"});
			//原料CD
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0025 , "false"});
			//工程名
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0026 , "false"});
			//原料名
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0027 , "false"});
			//単価
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0028 , "false"});
			//歩留
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0029 , "false"});
			//油含有率
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0030 , "false"});
			//量
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0031 , "false"});
			//製造工程
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0032 , "true"});
			//原料一覧
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0033 , "false"});
			//原料分析
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0034 , "false"});
			//工程挿入
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0035 , "false"});
			//工程移動（↑）
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0036 , "false"});
			//工程移動（↓）
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0037 , "false"});
			//工程削除
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0038 , "false"});
			//原料挿入
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0039 , "false"});
			//原料移動（↑）
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0040 , "false"});
			//原料移動（↓）
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0041 , "false"});
			//原料削除
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0042 , "false"});
			//試作列追加
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0043 , "false"});
			//試作列削除
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0044 , "false"});
			//試作列コピー
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0045 , "false"});
			//試作列移動（←）
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0046 , "false"});
			//試作列移動（→）
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0047 , "false"});
			//試作表出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0048 , "false"});
			//ｻﾝﾌﾟﾙ説明書出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0049 , "false"});
			//仕上重量
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0124 , "false"});
			//試作リストコピー
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0125 , "false"});
			//栄養計算書出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0126 , "false"});

			//--------------------------- 製造工程 --------------------------------
			//製造工程選択
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0050 , "true"});
			//内容入力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0051 , "false"});
			//常に表示
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0052 , "false"});
			//新規
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0053 , "false"});
			//更新
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0054 , "false"});

			//--------------------------- 特性値(試作表②) --------------------------------
			//自動計算
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0055 , "false"});
			//総酸・食塩出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0056 , "false"});
			//水相中出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0057 , "false"});
			//糖度出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0058 , "false"});
			//粘度・温度出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0059 , "false"});
			//PH出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0060 , "false"});
			//総酸・食塩出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0061 , "false"});
			//比重出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0062 , "false"});
			//水分活性出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0063 , "false"});
			//アルコール出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0064 , "false"});
			//フリータイトル1出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0065 , "false"});
			//フリータイトル2出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0066 , "false"});
			//フリータイトル3出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0067 , "false"});
			//一括チェック
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0068 , "false"});
			//フリータイトル1
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0069 , "false"});
			//フリータイトル2
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0070 , "false"});
			//フリータイトル3
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0071 , "false"});
			//総酸
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0072 , "false"});
			//食塩
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0073 , "false"});
			//水相中酸度
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0074 , "false"});
			//水相中食塩
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0075 , "false"});
			//水相中酢酸
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0076 , "false"});
			//糖度
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0077 , "false"});
			//粘度
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0078 , "false"});
			//温度
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0079 , "false"});
			//PH
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0080 , "false"});
			//総酸分析
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0081 , "false"});
			//食塩分析
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0082 , "false"});
			//比重
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0083 , "false"});
			//水分活性
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0084 , "false"});
			//アルコール
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0085 , "false"});
			//フリー内容1
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0086 , "false"});
			//フリー内容2
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0087 , "false"});
			//フリー内容3
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0088 , "false"});
			//作成メモ
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0089 , "false"});
			//評価
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0090 , "false"});
			//分析値変更確認
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0091 , "true"});
// ADD start 20121016 QP@20505 No.11
			//原料分析マスタ値取得
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0155 , "true"});
// ADD end 20121016 QP@20505 No.11
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			//水相比重
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0154 , "false"});
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20121001 QP@20505 No.1
			//水分活性フリータイトル
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0160 , "false"});
			//水分活性フリー内容
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0161 , "false"});
			//水分活性フリー出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0162 , "false"});
			//アルコールフリータイトル
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0163 , "false"});
			//アルコールフリー内容
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0164 , "false"});
			//アルコールフリー出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0165 , "false"});
			//実効酢酸濃度
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0166 , "false"}); // false
			//実効酢酸濃度 出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0167 , "false"});
			//水相中ＭＳＧ
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0168 , "false"}); // false
			//水相中ＭＳＧ 出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0169 , "false"});
			//粘度フリータイトル
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0170 , "false"});
			//粘度フリー内容
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0171 , "false"});
			//粘度フリー内容 出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0172 , "false"});
			//温度フリータイトル
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0173 , "false"});
			//温度フリー内容
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0174 , "false"});
			//温度フリー内容 出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0175 , "false"});
			//フリー④タイトル
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0176 , "false"});
			//フリー④内容
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0177 , "false"});
			//フリー④ 出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0178 , "false"});
			//フリー⑤タイトル
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0179 , "false"});
			//フリー⑤内容
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0180 , "false"});
			//フリー⑤ 出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0181 , "false"});
			//フリー⑥タイトル
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0182 , "false"});
			//フリー⑥内容
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0183 , "false"});
			//フリー⑥ 出力
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0184 , "false"});
// ADD end 20121001 QP@20505 No.1

			//--------------------------- 基本情報(試作表③) --------------------------------
			//所属グループ
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0092 , "false"});
			//所属チーム
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0093 , "false"});
			//一括表示
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0094 , "false"});
			//ジャンル
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0095 , "false"});
			//ユーザ
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0096 , "false"});
			//特徴原料
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0097 , "false"});
			//用途
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0098 , "false"});
			//価格帯
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0099 , "false"});
			//種別
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0100 , "false"});
			//少数指定
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0101 , "false"});
			//担当会社
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0102 , "false"});
			//担当工場
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0103 , "false"});
			//担当営業
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0104 , "false"});
			//製造方法
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0105 , "false"});
			//充填方法
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0106 , "false"});
			//殺菌方法
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0107 , "false"});
			//容器包材
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0108 , "false"});
			//容量
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0109 , "false"});
			//容量（単位）
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0110 , "false"});
			//入り数
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0111 , "false"});
			//荷姿
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0112 , "false"});
			//取扱温度
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0113 , "false"});
			//賞味期間
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0114 , "false"});
			//原価希望
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0115 , "false"});
			//売価希望
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0116 , "false"});
			//想定物量
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0117 , "false"});
			//販売時期
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0118 , "false"});
			//計画売上
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0119 , "false"});
			//計画利益
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0120 , "false"});
			//販売後売上
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0121 , "false"});
			//販売後利益
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0122 , "false"});
			//総合メモ
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0123 , "false"});

			//--------------------------- 原価試算(試作表⑤) --------------------------------
			//印刷FG
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0149 , "false"});
			//有効歩留
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0127 , "false"});
			//平均充填量
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0128 , "false"});
			//経費
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0129 , "false"});
			//売価
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0130 , "false"});
			//試算確定サンプルNo
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0131, "false"});
			//試算履歴参照
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0132, "false"});
			//原価試算表印刷
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0133, "false"});
			//充填量水相
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0134 , "false"});
			//充填量油相
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0135 , "false"});
			//合計量
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0136 , "false"});
			//原料費(kg)
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0137 , "false"});
			//原料費(１本当)
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0138 , "false"});
			//比重
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0139 , "false"});
			//容量
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0140 , "false"});
			//入数
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0141 , "false"});
			//レベル量
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0142 , "false"});
			//比重加算量
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0143 , "false"});
			//材料費
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0145 , "false"});
			//原価計/cs
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0146 , "false"});
			//原価計/個
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0147 , "false"});
			//粗利
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0148 , "false"});
			//原価試算登録
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0150 , "false"});
			//原価試算依頼Fg
			aryModeCheck_Sansho.add(new String[]{JwsConstManager.JWS_COMPONENT_0151 , "false"});


/*******************************************************************************************
 *
 * 詳細モード
 *
 *******************************************************************************************/
			//------------------------ 試作データ画面 -----------------------------
			//試作CD-ユーザ
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0001 , "false"});
			//試作CD-年
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0002 , "false"});
			//試作CD-追番
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0003 , "false"});
			//依頼番号
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0004 , "true"});
			//品名
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0005 , "true"});
			//製法No-ユーザ
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0006 , "false"});
			//製法No-種別CD
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0007 , "false"});
			//製法No-種別No
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0008 , "false"});
			//製法No-追番
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0009 , "false"});
			//廃止
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0010 , "true"});
			//登録
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0011 , "true"});
			//終了
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0012 , "true"});
			//特徴コピー
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0013 , "true"});
			//全コピー
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0014 , "true"});
			//種別番号
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0015 , "true"});

			//一つ戻る
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0152 , "true"});

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0153 , "true"});
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
			
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
			//シークレット
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0190 , "true"});
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

			//--------------------------- 配合表(試作表①) --------------------------------
			//試作選択
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0016 , "true"});
			//注意事項No
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0017 , "true"});
			//日付
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0018 , "true"});
			//ｻﾝﾌﾟﾙNo
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0019 , "true"});
			//メモ
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0020 , "true"});
			//印刷Fg
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0021 , "true"});
			//工程選択
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0022 , "true"});
			//原料選択
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0023 , "true"});
			//工程属性
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0024 , "true"});
			//原料CD
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0025 , "true"});
			//工程名
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0026 , "true"});
			//原料名
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0027 , "true"});
			//単価
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0028 , "true"});
			//歩留
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0029 , "true"});
			//油含有率
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0030 , "true"});
			//量
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0031 , "true"});
			//製造工程
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0032 , "true"});
			//原料一覧
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0033 , "true"});
			//原料分析
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0034 , "true"});
			//工程挿入
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0035 , "true"});
			//工程移動（↑）
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0036 , "true"});
			//工程移動（↓）
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0037 , "true"});
			//工程削除
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0038 , "true"});
			//原料挿入
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0039 , "true"});
			//原料移動（↑）
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0040 , "true"});
			//原料移動（↓）
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0041 , "true"});
			//原料削除
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0042 , "true"});
			//試作列追加
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0043 , "true"});
			//試作列削除
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0044 , "true"});
			//試作列コピー
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0045 , "true"});
			//試作列移動（←）
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0046 , "true"});
			//試作列移動（→）
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0047 , "true"});
			//試作表出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0048 , "true"});
			//ｻﾝﾌﾟﾙ説明書出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0049 , "true"});
			//仕上重量
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0124 , "true"});
			//試作リストコピー
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0125 , "true"});
			//栄養計算書出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0126 , "true"});

			//--------------------------- 製造工程 --------------------------------
			//製造工程選択
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0050 , "true"});
			//内容入力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0051 , "true"});
			//常に表示
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0052 , "true"});
			//新規
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0053 , "true"});
			//更新
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0054 , "true"});

			//--------------------------- 特性値(試作表②) --------------------------------
			//自動計算
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0055 , "true"});
			//総酸・食塩出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0056 , "true"});
			//水相中出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0057 , "true"});
			//糖度出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0058 , "true"});
			//粘度・温度出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0059 , "true"});
			//PH出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0060 , "true"});
			//総酸・食塩出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0061 , "true"});
			//比重出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0062 , "true"});
			//水分活性出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0063 , "true"});
			//アルコール出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0064 , "true"});
			//フリータイトル1出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0065 , "true"});
			//フリータイトル2出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0066 , "true"});
			//フリータイトル3出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0067 , "true"});
			//一括チェック
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0068 , "true"});
			//フリータイトル1
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0069 , "true"});
			//フリータイトル2
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0070 , "true"});
			//フリータイトル3
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0071 , "true"});
			//総酸
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0072 , "false"});
			//食塩
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0073 , "false"});
			//水相中酸度
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0074 , "false"});
			//水相中食塩
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0075 , "false"});
			//水相中酢酸
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0076 , "false"});
			//糖度
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0077 , "true"});
			//粘度
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0078 , "true"});
			//温度
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0079 , "true"});
			//PH
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0080 , "true"});
			//総酸分析
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0081 , "true"});
			//食塩分析
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0082 , "true"});
			//比重
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0083 , "true"});
			//水分活性
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0084 , "true"});
			//アルコール
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0085 , "true"});
			//フリー内容1
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0086 , "true"});
			//フリー内容2
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0087 , "true"});
			//フリー内容3
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0088 , "true"});
			//作成メモ
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0089 , "true"});
			//評価
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0090 , "true"});
			//分析値変更確認
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0091 , "true"});
// ADD start 20121016 QP@20505 No.11
			//原料分析マスタ値取得
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0155 , "true"});
// ADD end 20121016 QP@20505 No.11
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			//水相比重
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0154 , "true"});
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20121001 QP@20505 No.1
			//水分活性フリータイトル
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0160 , "true"});
			//水分活性フリー内容
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0161 , "true"});
			//水分活性フリー出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0162 , "true"});
			//アルコールフリータイトル
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0163 , "true"});
			//アルコールフリー内容
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0164 , "true"});
			//アルコールフリー出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0165 , "true"});
			//実効酢酸濃度
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0166 , "false"}); // false
			//実効酢酸濃度 出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0167 , "true"});
			//水相中ＭＳＧ
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0168 , "false"}); // false
			//水相中ＭＳＧ 出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0169 , "true"});
			//粘度フリータイトル
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0170 , "true"});
			//粘度フリー内容
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0171 , "true"});
			//粘度フリー内容 出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0172 , "true"});
			//温度フリータイトル
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0173 , "true"});
			//温度フリー内容
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0174 , "true"});
			//温度フリー内容 出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0175 , "true"});
			//フリー④タイトル
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0176 , "true"});
			//フリー④内容
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0177 , "true"});
			//フリー④ 出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0178 , "true"});
			//フリー⑤タイトル
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0179 , "true"});
			//フリー⑤内容
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0180 , "true"});
			//フリー⑤ 出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0181 , "true"});
			//フリー⑥タイトル
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0182 , "true"});
			//フリー⑥内容
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0183 , "true"});
			//フリー⑥ 出力
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0184 , "true"});
// ADD end 20121001 QP@20505 No.1
			//--------------------------- 基本情報(試作表③) --------------------------------
			//所属グループ
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0092 , "false"});
			//所属チーム
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0093 , "false"});
			//一括表示
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0094 , "true"});
			//ジャンル
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0095 , "true"});
			//ユーザ
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0096 , "true"});
			//特徴原料
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0097 , "true"});
			//用途
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0098 , "true"});
			//価格帯
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0099 , "true"});
			//種別
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0100 , "true"});
			//少数指定
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0101 , "true"});
			//担当会社
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0102 , "true"});
			//担当工場
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0103 , "true"});
			//担当営業
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0104 , "true"});
			//製造方法
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0105 , "true"});
			//充填方法
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0106 , "true"});
			//殺菌方法
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0107 , "true"});
			//容器包材
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0108 , "true"});
			//容量
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0109 , "true"});
			//容量（単位）
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0110 , "true"});
			//入り数
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0111 , "true"});
			//荷姿
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0112 , "true"});
			//取扱温度
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0113 , "true"});
			//賞味期間
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0114 , "true"});
			//原価希望
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0115 , "true"});
			//売価希望
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0116 , "true"});
			//想定物量
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0117 , "true"});
			//販売時期
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0118 , "true"});
			//計画売上
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0119 , "true"});
			//計画利益
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0120 , "true"});
			//販売後売上
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0121 , "true"});
			//販売後利益
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0122 , "true"});
			//総合メモ
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0123 , "true"});

			//--------------------------- 原価試算(試作表⑤) --------------------------------
			//印刷FG
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0149 , "true"});
			//有効歩留
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0127 , "true"});
			//平均充填量
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0128 , "true"});
			//経費
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0129 , "true"});
			//売価
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0130 , "true"});
			//試算確定サンプルNo
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0131, "true"});
			//試算履歴参照
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0132, "true"});
			//原価試算表印刷
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0133, "true"});
			//充填量水相
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0134 , "true"});
			//充填量油相
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0135 , "true"});
			//合計量
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0136 , "true"});
			//原料費(kg)
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0137 , "true"});
			//原料費(１本当)
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0138 , "true"});
			//比重
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0139 , "true"});
			//容量
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0140 , "true"});
			//入数
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0141 , "true"});
			//レベル量
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0142 , "true"});
			//比重加算量
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0143 , "true"});
			//材料費
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0145 , "true"});
			//原価計/cs
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0146 , "true"});
			//原価計/個
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0147 , "true"});
			//粗利
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0148 , "true"});
			//原価試算登録
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0150 , "true"});
			//原価試算依頼Fg
			aryModeCheck_Shosai.add(new String[]{JwsConstManager.JWS_COMPONENT_0151 , "true"});

/*******************************************************************************************
 *
 * 新規モード
 *
 *******************************************************************************************/
			//試作CD-ユーザ
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0001 , "false"});
			//試作CD-年
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0002 , "false"});
			//試作CD-追番
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0003 , "false"});
			//依頼番号
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0004 , "true"});
			//品名
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0005 , "true"});
			//製法No-ユーザ
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0006 , "false"});
			//製法No-種別CD
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0007 , "false"});
			//製法No-種別No
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0008 , "false"});
			//製法No-追番
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0009 , "false"});
			//廃止
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0010 , "true"});
			//登録
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0011 , "true"});
			//終了
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0012 , "true"});
			//特徴コピー
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0013 , "false"});
			//全コピー
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0014 , "false"});
			//種別番号
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0015 , "true"});

			//一つ戻る
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0152 , "true"});

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0153 , "true"});
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
			
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
			//シークレット
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0190 , "true"});
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

			//--------------------------- 配合表(試作表①) --------------------------------
			//試作選択
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0016 , "true"});
			//注意事項No
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0017 , "true"});
			//日付
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0018 , "true"});
			//ｻﾝﾌﾟﾙNo
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0019 , "true"});
			//メモ
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0020 , "true"});
			//印刷Fg
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0021 , "true"});
			//工程選択
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0022 , "true"});
			//原料選択
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0023 , "true"});
			//工程属性
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0024 , "true"});
			//原料CD
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0025 , "true"});
			//工程名
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0026 , "true"});
			//原料名
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0027 , "true"});
			//単価
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0028 , "true"});
			//歩留
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0029 , "true"});
			//油含有率
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0030 , "true"});
			//量
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0031 , "true"});
			//製造工程
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0032 , "true"});
			//原料一覧
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0033 , "true"});
			//原料分析
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0034 , "true"});
			//工程挿入
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0035 , "true"});
			//工程移動（↑）
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0036 , "true"});
			//工程移動（↓）
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0037 , "true"});
			//工程削除
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0038 , "true"});
			//原料挿入
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0039 , "true"});
			//原料移動（↑）
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0040 , "true"});
			//原料移動（↓）
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0041 , "true"});
			//原料削除
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0042 , "true"});
			//試作列追加
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0043 , "true"});
			//試作列削除
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0044 , "true"});
			//試作列コピー
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0045 , "true"});
			//試作列移動（←）
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0046 , "true"});
			//試作列移動（→）
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0047 , "true"});
			//試作表出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0048 , "true"});
			//ｻﾝﾌﾟﾙ説明書出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0049 , "true"});
			//仕上重量
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0124 , "true"});
			//試作リストコピー
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0125 , "true"});
			//栄養計算書出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0126 , "true"});

			//--------------------------- 製造工程 --------------------------------
			//製造工程選択
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0050 , "true"});
			//内容入力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0051 , "true"});
			//常に表示
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0052 , "true"});
			//新規
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0053 , "true"});
			//更新
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0054 , "true"});

			//--------------------------- 特性値(試作表②) --------------------------------
			//自動計算
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0055 , "true"});
			//総酸・食塩出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0056 , "true"});
			//水相中出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0057 , "true"});
			//糖度出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0058 , "true"});
			//粘度・温度出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0059 , "true"});
			//PH出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0060 , "true"});
			//総酸・食塩出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0061 , "true"});
			//比重出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0062 , "true"});
			//水分活性出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0063 , "true"});
			//アルコール出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0064 , "true"});
			//フリータイトル1出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0065 , "true"});
			//フリータイトル2出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0066 , "true"});
			//フリータイトル3出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0067 , "true"});
			//一括チェック
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0068 , "true"});
			//フリータイトル1
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0069 , "true"});
			//フリータイトル2
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0070 , "true"});
			//フリータイトル3
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0071 , "true"});
			//総酸
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0072 , "false"});
			//食塩
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0073 , "false"});
			//水相中酸度
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0074 , "false"});
			//水相中食塩
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0075 , "false"});
			//水相中酢酸
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0076 , "false"});
			//糖度
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0077 , "true"});
			//粘度
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0078 , "true"});
			//温度
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0079 , "true"});
			//PH
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0080 , "true"});
			//総酸分析
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0081 , "true"});
			//食塩分析
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0082 , "true"});
			//比重
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0083 , "true"});
			//水分活性
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0084 , "true"});
			//アルコール
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0085 , "true"});
			//フリー内容1
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0086 , "true"});
			//フリー内容2
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0087 , "true"});
			//フリー内容3
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0088 , "true"});
			//作成メモ
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0089 , "true"});
			//評価
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0090 , "true"});
			//分析値変更確認
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0091 , "true"});
// ADD start 20121016 QP@20505 No.11
			//原料分析マスタ値取得
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0155 , "true"});
// ADD end 20121016 QP@20505 No.11
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			//水相比重
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0154 , "true"});
// ADD start 20121001 QP@20505 No.1
			//水分活性フリータイトル
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0160 , "true"});
			//水分活性フリー内容
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0161 , "true"});
			//水分活性フリー出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0162 , "true"});
			//アルコールフリータイトル
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0163 , "true"});
			//アルコールフリー内容
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0164 , "true"});
			//アルコールフリー出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0165 , "true"});
			//実効酢酸濃度
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0166 , "false"}); // false
			//実効酢酸濃度 出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0167 , "true"});
			//水相中ＭＳＧ
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0168 , "false"}); // false
			//水相中ＭＳＧ 出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0169 , "true"});
			//粘度フリータイトル
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0170 , "true"});
			//粘度フリー内容
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0171 , "true"});
			//粘度フリー内容 出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0172 , "true"});
			//温度フリータイトル
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0173 , "true"});
			//温度フリー内容
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0174 , "true"});
			//温度フリー内容 出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0175 , "true"});
			//フリー④タイトル
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0176 , "true"});
			//フリー④内容
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0177 , "true"});
			//フリー④ 出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0178 , "true"});
			//フリー⑤タイトル
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0179 , "true"});
			//フリー⑤内容
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0180 , "true"});
			//フリー⑤ 出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0181 , "true"});
			//フリー⑥タイトル
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0182 , "true"});
			//フリー⑥内容
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0183 , "true"});
			//フリー⑥ 出力
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0184 , "true"});
// ADD end 20121001 QP@20505 No.1
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end

			//--------------------------- 基本情報(試作表③) --------------------------------
			//所属グループ
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0092 , "false"});
			//所属チーム
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0093 , "false"});
			//一括表示
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0094 , "true"});
			//ジャンル
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0095 , "true"});
			//ユーザ
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0096 , "true"});
			//特徴原料
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0097 , "true"});
			//用途
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0098 , "true"});
			//価格帯
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0099 , "true"});
			//種別
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0100 , "true"});
			//少数指定
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0101 , "true"});
			//担当会社
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0102 , "true"});
			//担当工場
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0103 , "true"});
			//担当営業
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0104 , "true"});
			//製造方法
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0105 , "true"});
			//充填方法
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0106 , "true"});
			//殺菌方法
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0107 , "true"});
			//容器包材
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0108 , "true"});
			//容量
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0109 , "true"});
			//容量（単位）
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0110 , "true"});
			//入り数
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0111 , "true"});
			//荷姿
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0112 , "true"});
			//取扱温度
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0113 , "true"});
			//賞味期間
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0114 , "true"});
			//原価希望
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0115 , "true"});
			//売価希望
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0116 , "true"});
			//想定物量
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0117 , "true"});
			//販売時期
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0118 , "true"});
			//計画売上
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0119 , "true"});
			//計画利益
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0120 , "true"});
			//販売後売上
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0121 , "true"});
			//販売後利益
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0122 , "true"});
			//総合メモ
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0123 , "true"});

			//--------------------------- 原価試算(試作表⑤) --------------------------------
			//印刷FG
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0149 , "true"});
			//有効歩留
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0127 , "true"});
			//平均充填量
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0128 , "true"});
			//経費
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0129 , "true"});
			//売価
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0130 , "true"});
			//試算確定サンプルNo
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0131, "false"});
			//試算履歴参照
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0132, "false"});
			//原価試算表印刷
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0133, "true"});
			//充填量水相
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0134 , "true"});
			//充填量油相
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0135 , "true"});
			//合計量
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0136 , "true"});
			//原料費(kg)
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0137 , "true"});
			//原料費(１本当)
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0138 , "true"});
			//比重
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0139 , "true"});
			//容量
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0140 , "true"});
			//入数
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0141 , "true"});
			//レベル量
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0142 , "true"});
			//比重加算量
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0143 , "true"});
			//材料費
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0145 , "true"});
			//原価計/cs
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0146 , "true"});
			//原価計/個
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0147 , "true"});
			//粗利
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0148 , "true"});
			//原価試算登録
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0150 , "false"});
			//原価試算依頼Fg
			aryModeCheck_Sinki.add(new String[]{JwsConstManager.JWS_COMPONENT_0151 , "true"});

/*******************************************************************************************
 *
 * 製法コピーモード
 *
 *******************************************************************************************/
			//試作CD-ユーザ
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0001 , "false"});
			//試作CD-年
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0002 , "false"});
			//試作CD-追番
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0003 , "false"});
			//依頼番号
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0004 , "false"});
			//品名
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0005 , "false"});
			//製法No-ユーザ
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0006 , "false"});
			//製法No-種別CD
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0007 , "false"});
			//製法No-種別No
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0008 , "false"});
			//製法No-追番
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0009 , "false"});
			//廃止
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0010 , "false"});
			//登録
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0011 , "true"});
			//終了
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0012 , "true"});
			//特徴コピー
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0013 , "false"});
			//全コピー
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0014 , "false"});
			//種別番号
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0015 , "true"});

			//一つ戻る
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0152 , "true"});

//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0153 , "false"});
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
			
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD Start
			//シークレット
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0190 , "true"});
			//【QP@20505_No.38】2012/10/17 TT H.SHIMA ADD End

			//--------------------------- 配合表(試作表①) --------------------------------
			//試作選択
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0016 , "true"});
			//注意事項No
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0017 , "false"});
			//日付
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0018 , "false"});
			//ｻﾝﾌﾟﾙNo
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0019 , "false"});
			//メモ
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0020 , "false"});
			//印刷Fg
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0021 , "false"});
			//工程選択
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0022 , "false"});
			//原料選択
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0023 , "false"});
			//工程属性
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0024 , "false"});
			//原料CD
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0025 , "true"});
			//工程名
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0026 , "false"});
			//原料名
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0027 , "true"});
			//単価
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0028 , "true"});
			//歩留
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0029 , "true"});
			//油含有率
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0030 , "true"});
			//量
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0031 , "false"});
			//製造工程
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0032 , "true"});
			//原料一覧
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0033 , "true"});
			//原料分析
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0034 , "true"});
			//工程挿入
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0035 , "false"});
			//工程移動（↑）
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0036 , "false"});
			//工程移動（↓）
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0037 , "false"});
			//工程削除
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0038 , "false"});
			//原料挿入
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0039 , "false"});
			//原料移動（↑）
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0040 , "false"});
			//原料移動（↓）
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0041 , "false"});
			//原料削除
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0042 , "false"});
			//試作列追加
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0043 , "false"});
			//試作列削除
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0044 , "false"});
			//試作列コピー
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0045 , "false"});
			//試作列移動（←）
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0046 , "false"});
			//試作列移動（→）
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0047 , "false"});
			//試作表出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0048 , "false"});
			//ｻﾝﾌﾟﾙ説明書出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0049 , "false"});
			//仕上重量
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0124 , "false"});
			//試作リストコピー
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0125 , "false"});
			//栄養計算書出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0126 , "false"});

			//--------------------------- 製造工程 --------------------------------
			//製造工程選択
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0050 , "true"});
			//内容入力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0051 , "false"});
			//常に表示
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0052 , "false"});
			//新規
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0053 , "false"});
			//更新
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0054 , "false"});

			//--------------------------- 特性値(試作表②) --------------------------------
			//自動計算
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0055 , "false"});
			//総酸・食塩出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0056 , "false"});
			//水相中出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0057 , "false"});
			//糖度出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0058 , "false"});
			//粘度・温度出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0059 , "false"});
			//PH出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0060 , "false"});
			//総酸・食塩出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0061 , "false"});
			//比重出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0062 , "false"});
			//水分活性出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0063 , "false"});
			//アルコール出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0064 , "false"});
			//フリータイトル1出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0065 , "false"});
			//フリータイトル2出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0066 , "false"});
			//フリータイトル3出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0067 , "false"});
			//一括チェック
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0068 , "false"});
			//フリータイトル1
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0069 , "false"});
			//フリータイトル2
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0070 , "false"});
			//フリータイトル3
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0071 , "false"});
			//総酸
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0072 , "false"});
			//食塩
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0073 , "false"});
			//水相中酸度
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0074 , "false"});
			//水相中食塩
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0075 , "false"});
			//水相中酢酸
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0076 , "false"});
			//糖度
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0077 , "false"});
			//粘度
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0078 , "false"});
			//温度
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0079 , "false"});
			//PH
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0080 , "false"});
			//総酸分析
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0081 , "false"});
			//食塩分析
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0082 , "false"});
			//比重
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0083 , "false"});
			//水分活性
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0084 , "false"});
			//アルコール
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0085 , "false"});
			//フリー内容1
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0086 , "false"});
			//フリー内容2
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0087 , "false"});
			//フリー内容3
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0088 , "false"});
			//作成メモ
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0089 , "false"});
			//評価
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0090 , "false"});
			//分析値変更確認
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0091 , "true"});
// ADD start 20121016 QP@20505 No.11
			//原料分析マスタ値取得
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0155 , "true"});
// ADD end 20121016 QP@20505 No.11
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　start
			//水相比重
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0154 , "false"});
//2011/05/30　【QP@10181_No.42_49_72】工程パターン制御　TT.NISHIGAWA　end
// ADD start 20121001 QP@20505 No.1
			//水分活性フリータイトル
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0160 , "false"});
			//水分活性フリー内容
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0161 , "false"});
			//水分活性フリー出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0162 , "false"});
			//アルコールフリータイトル
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0163 , "false"});
			//アルコールフリー内容
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0164 , "false"});
			//アルコールフリー出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0165 , "false"});
			//実効酢酸濃度
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0166 , "false"}); // false
			//実効酢酸濃度 出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0167 , "false"});
			//水相中ＭＳＧ
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0168 , "false"}); // false
			//水相中ＭＳＧ 出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0169 , "false"});
			//粘度フリータイトル
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0170 , "false"});
			//粘度フリー内容
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0171 , "false"});
			//粘度フリー内容 出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0172 , "false"});
			//温度フリータイトル
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0173 , "false"});
			//温度フリー内容
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0174 , "false"});
			//温度フリー内容 出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0175 , "false"});
			//フリー④タイトル
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0176 , "false"});
			//フリー④内容
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0177 , "false"});
			//フリー④ 出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0178 , "false"});
			//フリー⑤タイトル
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0179 , "false"});
			//フリー⑤内容
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0180 , "false"});
			//フリー⑤ 出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0181 , "false"});
			//フリー⑥タイトル
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0182 , "false"});
			//フリー⑥内容
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0183 , "false"});
			//フリー⑥ 出力
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0184 , "false"});
// ADD end 20121001 QP@20505 No.1

			//--------------------------- 基本情報(試作表③) --------------------------------
			//所属グループ
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0092 , "false"});
			//所属チーム
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0093 , "false"});
			//一括表示
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0094 , "false"});
			//ジャンル
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0095 , "false"});
			//ユーザ
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0096 , "false"});
			//特徴原料
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0097 , "false"});
			//用途
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0098 , "false"});
			//価格帯
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0099 , "false"});
			//種別
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0100 , "false"});
			//少数指定
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0101 , "false"});
			//担当会社
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0102 , "false"});
			//担当工場
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0103 , "false"});
			//担当営業
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0104 , "false"});
			//製造方法
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0105 , "false"});
			//充填方法
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0106 , "false"});
			//殺菌方法
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0107 , "false"});
			//容器包材
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0108 , "false"});
			//容量
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0109 , "false"});
			//容量（単位）
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0110 , "false"});
			//入り数
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0111 , "false"});
			//荷姿
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0112 , "false"});
			//取扱温度
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0113 , "false"});
			//賞味期間
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0114 , "false"});
			//原価希望
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0115 , "false"});
			//売価希望
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0116 , "false"});
			//想定物量
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0117 , "false"});
			//販売時期
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0118 , "false"});
			//計画売上
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0119 , "false"});
			//計画利益
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0120 , "false"});
			//販売後売上
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0121 , "false"});
			//販売後利益
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0122 , "false"});
			//総合メモ
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0123 , "false"});

			//--------------------------- 原価試算(試作表⑤) --------------------------------
			//印刷FG
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0149 , "false"});
			//有効歩留
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0127 , "false"});
			//平均充填量
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0128 , "false"});
			//経費
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0129 , "false"});
			//売価
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0130 , "false"});
			//試算確定サンプルNo
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0131, "false"});
			//試算履歴参照
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0132, "false"});
			//原価試算表印刷
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0133, "false"});
			//充填量水相
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0134 , "false"});
			//充填量油相
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0135 , "false"});
			//合計量
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0136 , "false"});
			//原料費(kg)
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0137 , "false"});
			//原料費(１本当)
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0138 , "false"});
			//比重
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0139 , "false"});
			//容量
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0140 , "false"});
			//入数
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0141 , "false"});
			//レベル量
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0142 , "false"});
			//比重加算量
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0143 , "false"});
			//材料費
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0145 , "false"});
			//原価計/cs
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0146 , "false"});
			//原価計/個
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0147 , "false"});
			//粗利
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0148 , "false"});
			//原価試算登録
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0150 , "false"});
			//原価試算依頼Fg
			aryModeCheck_Copy.add(new String[]{JwsConstManager.JWS_COMPONENT_0151 , "false"});





		}catch(Exception e){
			e.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("モード編集に失敗しました。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}

	}

	/*****************************************************************************************
	 *
	 * モード編集チェック
	 * @param  strCtrlNm  : コントロール名
	 * @param  strMode   : モードID
	 * @return  boolean    : 編集可否
	 *
	 *****************************************************************************************/
	public boolean checkModeCtrl(String strCtrlNm , String strMode) throws ExceptionBase{
		//返却値
		boolean ret = false;
		//確認用配列
		ArrayList chkArray = new ArrayList();

		try{
			//参照モードの場合
			if(strMode.equals(JwsConstManager.JWS_MODE_0000)){
				chkArray = aryModeCheck_Sansho;
			}
			//詳細モードの場合
			else if(strMode.equals(JwsConstManager.JWS_MODE_0001)){
				chkArray = aryModeCheck_Shosai;
			}
			//新規モードの場合
			else if(strMode.equals(JwsConstManager.JWS_MODE_0002)){
				chkArray = aryModeCheck_Sinki;
			}
			//製法コピーモードの場合
			else if(strMode.equals(JwsConstManager.JWS_MODE_0003)){
				chkArray = aryModeCheck_Copy;
			}

//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　start
			//試作コピーモード編集パターン
			else if(strMode.equals(JwsConstManager.JWS_MODE_0004)){
				chkArray = aryModeCheck_ShisakuCopy;
			}
//2011/05/19　【QP@10181_No.11】試作コピーモード処理　TT.NISHIGAWA　end


			//使用可否チェック
			for(int i=0; i<chkArray.size(); i++){
				//編集パターン取得
				String[] selModePtn = (String[])chkArray.get(i);
				//同名コントロールの場合
				if(selModePtn[0].equals(strCtrlNm)){
					//編集可否指定
					if(selModePtn[1].equals("true")){
						ret = true;
					}else{
						ret = false;
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			//エラー設定
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("モード編集チェックに失敗しました。");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}

		//返却
		return ret;
	}

}
