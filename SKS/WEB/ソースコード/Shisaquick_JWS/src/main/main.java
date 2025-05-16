package main;


import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.jnlp.UnavailableServiceException;
import javax.swing.WindowConstants;

import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.*;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;
import jp.co.blueflag.shisaquick.jws.disp.TrialMainDisp;
import jp.co.blueflag.shisaquick.jws.manager.XmlConnection;

/**
 * 
 * メインクラス
 * 
 */
class main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			//test
			DataCtrl.getInstance().getParamData().setDciUser(new BigDecimal("2441"));
			DataCtrl.getInstance().getParamData().setDciUser(new BigDecimal("9999999999"));
//			DataCtrl.getInstance().getParamData().setDciUser(new BigDecimal("2001"));
//			DataCtrl.getInstance().getParamData().setDciUser(new BigDecimal("1"));
			//参照
//			DataCtrl.getInstance().getParamData().setStrSisaku("1-9-1");
//			DataCtrl.getInstance().getParamData().setStrMode("000");
			//詳細
//			DataCtrl.getInstance().getParamData().setStrSisaku("1-9-288");
//			DataCtrl.getInstance().getParamData().setStrSisaku("1-9-1");
//			DataCtrl.getInstance().getParamData().setStrSisaku("2441-9-4");
			DataCtrl.getInstance().getParamData().setStrSisaku("2441-9-3");
//			DataCtrl.getInstance().getParamData().setStrSisaku("5100000000-9-2");
//			DataCtrl.getInstance().getParamData().setStrSisaku("9999999999-9-100");
//			DataCtrl.getInstance().getParamData().setStrSisaku("9999999999-11-67");
			DataCtrl.getInstance().getParamData().setStrMode("100");
			//新規
//			DataCtrl.getInstance().getParamData().setStrSisaku("0-0-0");
//			DataCtrl.getInstance().getParamData().setStrMode("110");
			//製法コピー
//			DataCtrl.getInstance().getParamData().setStrSisaku("1-9-1");
//			DataCtrl.getInstance().getParamData().setStrSisaku("9999999999-9-100");
//			DataCtrl.getInstance().getParamData().setStrMode("120");
			
			//ダウンロード用待ち画面表示
			DataCtrl.getInstance().getDownloadDisp().setVisible(true);
			
			//パラメータデータ設定
			if(args.length > 0){
				
				DataCtrl.getInstance().getParamData().setDciUser(new BigDecimal(args[0]));
				DataCtrl.getInstance().getParamData().setStrSisaku(args[1]);
				DataCtrl.getInstance().getParamData().setStrMode(args[2]);
				
			}
			
			//Ajax通信用アドレスの設定
			try {
				DataCtrl.getInstance().getJnlpConnect().setAddressAjax();
				
			} catch (UnavailableServiceException e) {
				//DataCtrl.getInstance().getMessageCtrl().PrintMessageString("Jnlpより接続を行っておりません。");
				//タイムテック
				DataCtrl.getInstance().getJnlpConnect().setStrAddressAjax("http://localhost:8080/Shisaquick_SRV/AjaxServlet");
				//DataCtrl.getInstance().getJnlpConnect().setStrAddressAjax("http://localhost:8080/Shisaquick_SRV_poi2.0/AjaxServlet");
				//トウ
				//DataCtrl.getInstance().getJnlpConnect().setStrAddressAjax("http://localhost:8080/Shisaquick/AjaxServlet");
				
			} finally {
				
			}
		
			//画面タイトル設定
			String title = "試作データ画面";
			
			//試作データ画面表示
			TrialMainDisp TrialMainDisp = new TrialMainDisp(title);
			TrialMainDisp.addWindowListener(getWindowEvent());
			TrialMainDisp.setVisible(true);
			TrialMainDisp.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			
			//DataCtrl.getInstance().getTrialTblData().dispHaigo();
			
			
			//ダウンロード用待ち画面非表示
			DataCtrl.getInstance().getDownloadDisp().setVisible(false);
			
		} catch ( ExceptionBase e ) {
			
			DataCtrl.getInstance().getMessageCtrl().addWindowListener(getWindowEvent());
			DataCtrl.getInstance().PrintMessage(e);		//メッセージの表示
			
		} catch(Exception e) {
			
			//エラー設定
			e.printStackTrace();
			ExceptionBase ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("メイン処理が失敗しました");
			ex.setStrErrShori("main");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			DataCtrl.getInstance().PrintMessage(ex);		//メッセージの表示
			
		}finally{
			
		}
	}

	/**
	 * Windowイベント取得
	 * @return WindowListener
	 * @throws ExceptionBase
	 */
	public static WindowListener getWindowEvent() {
		return new WindowListener() {
			/**
			 * 画面終了イベント
			 */
			public void windowClosing(WindowEvent e) {
				
				if ( DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
					try{
						
						//画面取得
						TrialMainDisp TrialMainDisp = (TrialMainDisp)e.getSource();
						
						//終了処理
						TrialMainDisp.getTp().shuryo();
						
					}catch(Exception ex){
						
						ex.printStackTrace();
						
					}
					
				}
			}	
			
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
		};
	}
	
}