package jp.co.blueflag.shisaquick.jws.manager;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.*;


/**
 * 
 * 分析値確認クラス
 *
 */
public class AnalysisCheck {

	private TrialTblData midtCheck;				//比較用の試作テーブルデータ
	private MaterialData mtmsCheck;			//比較用の原料マスタデータ
	private MessageCtrl msdCheck;				//比較内容を表示
	
	/**
	 * 分析値確認 コンストラクタ
	 */
	public AnalysisCheck() {
		this.midtCheck = null;
		this.mtmsCheck = null;
		this.msdCheck = null;
	}
	
	/**
	 * 分析値比較
	 *  : 試作データと原料データの分析値を確認する
	 */
	public void CheckHikaku() {
		
	}
	
	/**
	 * 比較結果表示
	 *  : 試作テーブルデータと原料マスタデータの比較結果を表示
	 */
	public void PrintHikaku() {
		
	}

	/**
	 * 試作テーブルデータ ゲッター
	 * @return midtCheck : 試作テーブルデータの値を返す
	 */
	public TrialTblData getMidtCheck() {
		return midtCheck;
	}
	/**
	 * 試作テーブルデータ セッター
	 * @param _midtCheck : 試作テーブルデータの値を格納する
	 */
	public void setMidtCheck(TrialTblData _midtCheck) {
		this.midtCheck = _midtCheck;
	}

	/**
	 * 原料データ ゲッター
	 * @return mtmsCheck : 原料マスタデータの値を返す
	 */
	public MaterialData getMtmsCheck() {
		return mtmsCheck;
	}
	/**
	 * 原料データ セッター
	 * @param _mtmsCheck : 原料データの値を格納する
	 */
	public void setMtmsCheck(MaterialData _mtmsCheck) {
		this.mtmsCheck = _mtmsCheck;
	}

	/**
	 * 比較内容 ゲッター
	 * @return msdCheck : 比較内容の値を返す
	 */
	public MessageCtrl getMsdCheck() {
		return msdCheck;
	}
	/**
	 * 比較内容 セッター
	 * @param _msdCheck : 比較内容の値を格納する
	 */
	public void setMsdCheck(MessageCtrl _msdCheck) {
		this.msdCheck = _msdCheck;
	}
	
}
