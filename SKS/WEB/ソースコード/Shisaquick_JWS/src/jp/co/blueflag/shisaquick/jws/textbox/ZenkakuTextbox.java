package jp.co.blueflag.shisaquick.jws.textbox;

import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import jp.co.blueflag.shisaquick.jws.common.TextboxBase;

/**
 *
 * �S�p�e�L�X�g�{�b�N�X
 *  : �S�p�����̂ݓ��͉\�B
 *  
 * @author k-katayama
 * @since 2009/04/02
 *
 */
public class ZenkakuTextbox extends TextboxBase {
	private static final long serialVersionUID = 1L;

	/**
	 * �R���X�g���N�^
	 */
	public ZenkakuTextbox() {
        super();
        
        //�������ɐݒ�
        this.setHorizontalAlignment(SwingConstants.LEFT);
        
        //�\����Ԃɐݒ�
        this.setVisible(true);
    }

	/**
	 * �e�L�X�g�{�b�N�X�̃t�H���g���f���̍쐬
	 * @return �S�p�����pDocument
	 */
    protected Document createDefaultModel() {
	      return new ZenkakuDocument();
    }

    /**
     * �S�p�����pDocument�N���X
     * 
     * @author k-katayama
     */
    static class ZenkakuDocument extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	
    	/**
    	 * �����}������
    	 */
        public void insertString(int offset, String text, AttributeSet attributes) 
	          throws BadLocationException {
        	try{
        		
        		if (text == null || !checkZenkaku(text)) {

        			// �S�p�ł͂Ȃ���v���Ȃ��ꍇ�A�G���[
        			throw new BadLocationException(text, offset);
        			
        		}
        	}catch(BadLocationException e){
        		throw e;
        	}
        	super.insertString(offset,text,attributes);
        }
        
        /**
        * �S�p�`�F�b�N
        * @param String str �`�F�b�N��������
        * @return boolean true:�S�p false:�S�p�ȊO�̕����񂠂�
        */
        private boolean checkZenkaku(String str) {
        	
        	byte[] bytes;
        	
        	bytes = str.getBytes();
        	
        	// length��2�{���擾
        	int lentime = str.length() * 2;
        	 
        	StringBuffer sb = new StringBuffer(str);
        	for (int i = 0; i < str.length(); i++) {
        		if ('\n' == sb.charAt(i)) {
        			lentime = lentime - 2;
        		}
        	}
        	
        	// length��2�{�ƁA�����̃o�C�g�����ꏏ�ł���ΑS�đS�p
        	if (lentime == bytes.length) {
        		return true;
        	}

        	return false;
        }
    }
}