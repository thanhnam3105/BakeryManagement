package jp.co.blueflag.shisaquick.jws.textbox;

import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import jp.co.blueflag.shisaquick.jws.common.JwsConstManager;
import jp.co.blueflag.shisaquick.jws.common.TextboxBase;

/**
 *
 * ���p�e�L�X�g�{�b�N�X
 *  : ���p�����̂ݓ��͉\�B
 *  
 * @author k-katayama
 * @since 2009/04/02
 *
 */
public class HankakuTextbox extends TextboxBase {
	private static final long serialVersionUID = 1L;
	
	//���͕�������
	private int intMaxLength;
	
	/**
	 * �R���X�g���N�^
	 */
	public HankakuTextbox() {
        super();
        
        //�������ɐݒ�
        this.setHorizontalAlignment(SwingConstants.LEFT);
        
        //�\����Ԃɐݒ�
        this.setVisible(true);
        
        //�����o�̏�����
        intMaxLength = Integer.parseInt(JwsConstManager.JWS_KETA_GENRYO);
    }
	
	/**
	 * ���͕������� �Q�b�^�[
	 * @return ���͕�����
	 */
	public int getIntMaxLength() {
		return intMaxLength;
	}

	/**
	 * ���͕������� �Z�b�^�[
	 * @param ���͕�����
	 */
	public void setIntMaxLength(int intMaxLength) {
		this.intMaxLength = intMaxLength;
	}
	

	/**
	 * �e�L�X�g�{�b�N�X�̃t�H���g���f���̍쐬
	 * @return ���p�����pDocument
	 */
    protected Document createDefaultModel() {
	      return new HankakuDocument();
    }

    /**
     * ���p�pDocument�N���X
     * 
     * @author k-katayama
     */
    class HankakuDocument extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	
    	/**
    	 * �����}������
    	 */
        public void insertString(int offset, String text, AttributeSet attributes) 
	          throws BadLocationException {
        	try{
        		
        		if (text == null || !checkHankaku(text)) {
        			// ���p�ł͂Ȃ��ꍇ�A�G���[
        			throw new BadLocationException(text, offset);
        		}
        	}catch(BadLocationException e){
        		throw e;
        	}
        	super.insertString(offset,text,attributes);

	    }

        /**
        * ���p�`�F�b�N
        * @param String str �`�F�b�N��������
        * @return boolean true:���p false:���p�ȊO�̕����񂠂�
        */
        private boolean checkHankaku(String str) {
        	byte[] bytes;
        	bytes = str.getBytes();
        	int len = str.length();
        	StringBuffer sb = new StringBuffer(str);

        	for (int i = 0; i < str.length(); i++) {
        		// ���s�R�[�h�͑ΏۊO
        		if ('\n' == sb.charAt(i)) {
        			len = len - 2;
        		}
        	}

        	// len�ƈ���������̃o�C�g�����ꏏ�ł���΂��ׂĔ��p
        	if (len == bytes.length) {
        		return true;
        	}
        	return false;
        }
    }
    
    /**
     * ���p�pDocument�N���X�i�����R�[�h�p�j
     * 
     * @author k-katayama
     */
    public class HankakuDocumentGenryo extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	
    	/**
    	 * �����}������
    	 */
        public void insertString(int offset, String text, AttributeSet attributes) 
	          throws BadLocationException {
        	try{
        		
        		//���͓��e���擾
        		int intLength = HankakuTextbox.this.getText().length();
        		
        		
        		// ���p�ł͂Ȃ��ꍇ�A���͕������𒴂����ꍇ�ɃG���[
        		if (text == null || !checkHankaku(text) || intLength>=intMaxLength || text.length() > intMaxLength) {
        			
        			throw new BadLocationException(text, offset);
        			
        		}
        	}catch(BadLocationException e){
        		throw e;
        	}
        	super.insertString(offset,text,attributes);

	    }

        /**
        * ���p�`�F�b�N
        * @param String str �`�F�b�N��������
        * @return boolean true:���p false:���p�ȊO�̕����񂠂�
        */
        private boolean checkHankaku(String str) {
        	byte[] bytes;
        	bytes = str.getBytes();
        	int len = str.length();
        	StringBuffer sb = new StringBuffer(str);

        	for (int i = 0; i < str.length(); i++) {
        		// ���s�R�[�h�͑ΏۊO
        		if ('\n' == sb.charAt(i)) {
        			len = len - 2;
        		}
        	}

        	// len�ƈ���������̃o�C�g�����ꏏ�ł���΂��ׂĔ��p
        	if (len == bytes.length) {
        		return true;
        	}
        	return false;
        }
    }
}