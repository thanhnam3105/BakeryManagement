package jp.co.blueflag.shisaquick.jws.textbox;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import jp.co.blueflag.shisaquick.jws.common.TextboxBase;

/**
 *
 * ���l�e�L�X�g�{�b�N�X
 *  : ���p���l�̂ݓ��͉\�B
 *  
 * @author k-katayama
 * @since 2009/04/02
 *
 */
public class NumelicTextbox extends TextboxBase {
	private static final long serialVersionUID = 1L;
	
	String strShosuten = "";

	/**
	 * �R���X�g���N�^
	 */
	public NumelicTextbox() {
        super();
        
        //�E�����ɐݒ�
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        
        //�\����Ԃɐݒ�
        this.setVisible(true);
    }

	/**
	 * �e�L�X�g�{�b�N�X�̃t�H���g���f���̍쐬
	 * @return ���l�pDocument
	 */
    protected Document createDefaultModel() {
	      return new NumericDocument();
    }
    
    /**
     * ���l�pDocument�N���X
     * 
     * @author k-katayama
     */
    class NumericDocument extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	
    	/**
    	 * �����}������
    	 */
        public void insertString(int offset, String text, AttributeSet attributes) 
	          throws BadLocationException {
        	
        	try{

        		//���͓��e���擾
        		String strInput = NumelicTextbox.this.getText();
        		
        		//���͓��e�ɏ����_�����͂���Ă���ꍇ
        		if(strInput.indexOf(".") > -1){
        			
        			//���͕������u���l�v�łȂ��ꍇ�ɃG���[
            		if (text == null || !text.matches("^[0-9]+$")) {
            			
            			throw new BadLocationException(text, offset);
            			
            		}
        			
           		//���͓��e�ɏ����_�����͂���Ă��Ȃ��ꍇ
        		}else{
        			
        			//���͕������u���l or �����_]�łȂ��ꍇ�ɃG���[
            		if (text == null || !text.matches("^[0-9.]+$")) {
            			
            			throw new BadLocationException(text, offset);
            			
            		}
            		
        		}
        		
        	}catch(BadLocationException e){
        		throw e;
        	}
        	super.insertString(offset,text,attributes);

	      }
        
    }
}

