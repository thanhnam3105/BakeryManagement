package jp.co.blueflag.shisaquick.jws.tab;

import java.awt.Color;
import java.awt.Font;

import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.common.TabBase;
import jp.co.blueflag.shisaquick.jws.panel.Trial1Panel;
import jp.co.blueflag.shisaquick.jws.panel.Trial2Panel;
import jp.co.blueflag.shisaquick.jws.panel.Trial3Panel;
import jp.co.blueflag.shisaquick.jws.panel.Trial5Panel;

/****************************************************************************************
 * 
 *    �^�u����̋K��N���X
 *   @author TT nishigawa
 *   
 ****************************************************************************************/
public class TrialTab extends TabBase{
	private static final long serialVersionUID = 1L;
	
	private Trial1Panel Trial1Panel;
	private Trial2Panel Trial2Panel;
	private Trial3Panel Trial3Panel;

	private Trial5Panel Trial5Panel;
	
	private ExceptionBase ex;		//�G���[�n���h��
	
	/************************************************************************************
	 * 
	 *   �R���X�g���N�^
	 *    : ����\�^�u�𐶐�����
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	public TrialTab() throws ExceptionBase {
		super();
		
		try {
			this.setBackground(Color.WHITE);
			this.setFont(new Font("Default", Font.PLAIN, 12));
			Trial1Panel = new Trial1Panel();
			Trial2Panel = new Trial2Panel();
			Trial3Panel = new Trial3Panel();

			Trial5Panel = new Trial5Panel();
			
		} catch (Exception e) {
			e.printStackTrace();
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�^�u���쏈�������s���܂����B");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.toString());
			throw ex;
			
		} finally {
			
		}
	}
	
	
	/************************************************************************************
	 * 
	 *   �^�u�p�l���ݒ�
	 *   @author TT nishigawa
	 *   
	 ************************************************************************************/
	public void setTrialPane(){
		try{
			//�ĕ\��
			Trial1Panel.init();
			Trial2Panel = new Trial2Panel();
			Trial3Panel = new Trial3Panel();
			
			this.removeAll();
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change Start -------------------------
			//this.addTab("����\�@",  Trial1Panel);
			this.addTab("�z���\",  Trial1Panel);
			//this.addTab("����\�A",  Trial2Panel);
			this.addTab("�����l",  Trial2Panel);
			//this.addTab("����\�B",  Trial3Panel);
			this.addTab("��{���",  Trial3Panel);

			//this.addTab("�������Z���������D",  Trial5Panel);
			this.addTab("�������Z",  Trial5Panel);
			//2011/04/12 QP@10181_No.26 TT T.Satoh Change End ---------------------------
			
		}catch(Exception e){
			
		}
	}
	
	/**
	 * @return trial1Panel
	 */
	public Trial1Panel getTrial1Panel() {
		return Trial1Panel;
	}
	
	/**
	 * @return trial2Panel
	 */
	public Trial2Panel getTrial2Panel() {
		return Trial2Panel;
	}
	/**
	 * @param trial2Panel �Z�b�g���� trial2Panel
	 */
	public void setTrial2Panel(Trial2Panel trial2Panel) {
		Trial2Panel = trial2Panel;
	}

	/**
	 * @return trial3Panel
	 */
	public Trial3Panel getTrial3Panel() {
		return Trial3Panel;
	}

	/**
	 * @return trial5Panel
	 */
	public Trial5Panel getTrial5Panel() {
		return Trial5Panel;
	}
	/**
	 * @param trial5Panel �Z�b�g���� trial5Panel
	 */
	public void setTrial5Panel(Trial5Panel trial5Panel) {
		Trial5Panel = trial5Panel;
	}
	
}