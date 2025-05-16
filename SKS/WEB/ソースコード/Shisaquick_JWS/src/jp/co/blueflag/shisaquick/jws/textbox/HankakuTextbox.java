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
 * 半角テキストボックス
 *  : 半角文字のみ入力可能。
 *  
 * @author k-katayama
 * @since 2009/04/02
 *
 */
public class HankakuTextbox extends TextboxBase {
	private static final long serialVersionUID = 1L;
	
	//入力文字制限
	private int intMaxLength;
	
	/**
	 * コンストラクタ
	 */
	public HankakuTextbox() {
        super();
        
        //左揃えに設定
        this.setHorizontalAlignment(SwingConstants.LEFT);
        
        //表示状態に設定
        this.setVisible(true);
        
        //メンバの初期化
        intMaxLength = Integer.parseInt(JwsConstManager.JWS_KETA_GENRYO);
    }
	
	/**
	 * 入力文字制限 ゲッター
	 * @return 入力文字数
	 */
	public int getIntMaxLength() {
		return intMaxLength;
	}

	/**
	 * 入力文字制限 セッター
	 * @param 入力文字数
	 */
	public void setIntMaxLength(int intMaxLength) {
		this.intMaxLength = intMaxLength;
	}
	

	/**
	 * テキストボックスのフォルトモデルの作成
	 * @return 半角文字用Document
	 */
    protected Document createDefaultModel() {
	      return new HankakuDocument();
    }

    /**
     * 半角用Documentクラス
     * 
     * @author k-katayama
     */
    class HankakuDocument extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	
    	/**
    	 * 文字挿入処理
    	 */
        public void insertString(int offset, String text, AttributeSet attributes) 
	          throws BadLocationException {
        	try{
        		
        		if (text == null || !checkHankaku(text)) {
        			// 半角ではない場合、エラー
        			throw new BadLocationException(text, offset);
        		}
        	}catch(BadLocationException e){
        		throw e;
        	}
        	super.insertString(offset,text,attributes);

	    }

        /**
        * 半角チェック
        * @param String str チェック元文字列
        * @return boolean true:半角 false:半角以外の文字列あり
        */
        private boolean checkHankaku(String str) {
        	byte[] bytes;
        	bytes = str.getBytes();
        	int len = str.length();
        	StringBuffer sb = new StringBuffer(str);

        	for (int i = 0; i < str.length(); i++) {
        		// 改行コードは対象外
        		if ('\n' == sb.charAt(i)) {
        			len = len - 2;
        		}
        	}

        	// lenと引数文字列のバイト数が一緒であればすべて半角
        	if (len == bytes.length) {
        		return true;
        	}
        	return false;
        }
    }
    
    /**
     * 半角用Documentクラス（原料コード用）
     * 
     * @author k-katayama
     */
    public class HankakuDocumentGenryo extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	
    	/**
    	 * 文字挿入処理
    	 */
        public void insertString(int offset, String text, AttributeSet attributes) 
	          throws BadLocationException {
        	try{
        		
        		//入力内容を取得
        		int intLength = HankakuTextbox.this.getText().length();
        		
        		
        		// 半角ではない場合、入力文字数を超えた場合にエラー
        		if (text == null || !checkHankaku(text) || intLength>=intMaxLength || text.length() > intMaxLength) {
        			
        			throw new BadLocationException(text, offset);
        			
        		}
        	}catch(BadLocationException e){
        		throw e;
        	}
        	super.insertString(offset,text,attributes);

	    }

        /**
        * 半角チェック
        * @param String str チェック元文字列
        * @return boolean true:半角 false:半角以外の文字列あり
        */
        private boolean checkHankaku(String str) {
        	byte[] bytes;
        	bytes = str.getBytes();
        	int len = str.length();
        	StringBuffer sb = new StringBuffer(str);

        	for (int i = 0; i < str.length(); i++) {
        		// 改行コードは対象外
        		if ('\n' == sb.charAt(i)) {
        			len = len - 2;
        		}
        	}

        	// lenと引数文字列のバイト数が一緒であればすべて半角
        	if (len == bytes.length) {
        		return true;
        	}
        	return false;
        }
    }
}