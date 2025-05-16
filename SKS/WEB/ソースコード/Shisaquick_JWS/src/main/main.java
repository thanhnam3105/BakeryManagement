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
 * ���C���N���X
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
			//�Q��
//			DataCtrl.getInstance().getParamData().setStrSisaku("1-9-1");
//			DataCtrl.getInstance().getParamData().setStrMode("000");
			//�ڍ�
//			DataCtrl.getInstance().getParamData().setStrSisaku("1-9-288");
//			DataCtrl.getInstance().getParamData().setStrSisaku("1-9-1");
//			DataCtrl.getInstance().getParamData().setStrSisaku("2441-9-4");
			DataCtrl.getInstance().getParamData().setStrSisaku("2441-9-3");
//			DataCtrl.getInstance().getParamData().setStrSisaku("5100000000-9-2");
//			DataCtrl.getInstance().getParamData().setStrSisaku("9999999999-9-100");
//			DataCtrl.getInstance().getParamData().setStrSisaku("9999999999-11-67");
			DataCtrl.getInstance().getParamData().setStrMode("100");
			//�V�K
//			DataCtrl.getInstance().getParamData().setStrSisaku("0-0-0");
//			DataCtrl.getInstance().getParamData().setStrMode("110");
			//���@�R�s�[
//			DataCtrl.getInstance().getParamData().setStrSisaku("1-9-1");
//			DataCtrl.getInstance().getParamData().setStrSisaku("9999999999-9-100");
//			DataCtrl.getInstance().getParamData().setStrMode("120");
			
			//�_�E�����[�h�p�҂���ʕ\��
			DataCtrl.getInstance().getDownloadDisp().setVisible(true);
			
			//�p�����[�^�f�[�^�ݒ�
			if(args.length > 0){
				
				DataCtrl.getInstance().getParamData().setDciUser(new BigDecimal(args[0]));
				DataCtrl.getInstance().getParamData().setStrSisaku(args[1]);
				DataCtrl.getInstance().getParamData().setStrMode(args[2]);
				
			}
			
			//Ajax�ʐM�p�A�h���X�̐ݒ�
			try {
				DataCtrl.getInstance().getJnlpConnect().setAddressAjax();
				
			} catch (UnavailableServiceException e) {
				//DataCtrl.getInstance().getMessageCtrl().PrintMessageString("Jnlp���ڑ����s���Ă���܂���B");
				//�^�C���e�b�N
				DataCtrl.getInstance().getJnlpConnect().setStrAddressAjax("http://localhost:8080/Shisaquick_SRV/AjaxServlet");
				//DataCtrl.getInstance().getJnlpConnect().setStrAddressAjax("http://localhost:8080/Shisaquick_SRV_poi2.0/AjaxServlet");
				//�g�E
				//DataCtrl.getInstance().getJnlpConnect().setStrAddressAjax("http://localhost:8080/Shisaquick/AjaxServlet");
				
			} finally {
				
			}
		
			//��ʃ^�C�g���ݒ�
			String title = "����f�[�^���";
			
			//����f�[�^��ʕ\��
			TrialMainDisp TrialMainDisp = new TrialMainDisp(title);
			TrialMainDisp.addWindowListener(getWindowEvent());
			TrialMainDisp.setVisible(true);
			TrialMainDisp.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			
			//DataCtrl.getInstance().getTrialTblData().dispHaigo();
			
			
			//�_�E�����[�h�p�҂���ʔ�\��
			DataCtrl.getInstance().getDownloadDisp().setVisible(false);
			
		} catch ( ExceptionBase e ) {
			
			DataCtrl.getInstance().getMessageCtrl().addWindowListener(getWindowEvent());
			DataCtrl.getInstance().PrintMessage(e);		//���b�Z�[�W�̕\��
			
		} catch(Exception e) {
			
			//�G���[�ݒ�
			e.printStackTrace();
			ExceptionBase ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���C�����������s���܂���");
			ex.setStrErrShori("main");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			DataCtrl.getInstance().PrintMessage(ex);		//���b�Z�[�W�̕\��
			
		}finally{
			
		}
	}

	/**
	 * Window�C�x���g�擾
	 * @return WindowListener
	 * @throws ExceptionBase
	 */
	public static WindowListener getWindowEvent() {
		return new WindowListener() {
			/**
			 * ��ʏI���C�x���g
			 */
			public void windowClosing(WindowEvent e) {
				
				if ( DataCtrl.getInstance().getResultData().isReturnFlgCheck() ) {
					try{
						
						//��ʎ擾
						TrialMainDisp TrialMainDisp = (TrialMainDisp)e.getSource();
						
						//�I������
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