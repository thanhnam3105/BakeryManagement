package jp.co.blueflag.shisaquick.jws.textbox;

import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import jp.co.blueflag.shisaquick.jws.common.TextboxBase;

/**
 *
 * 全角テキストボックス
 *  : 全角文字のみ入力可能。
 *  
 * @author k-katayama
 * @since 2009/04/02
 *
 */
public class ZenkakuTextbox extends TextboxBase {
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 */
	public ZenkakuTextbox() {
        super();
        
        //左揃えに設定
        this.setHorizontalAlignment(SwingConstants.LEFT);
        
        //表示状態に設定
        this.setVisible(true);
    }

	/**
	 * テキストボックスのフォルトモデルの作成
	 * @return 全角文字用Document
	 */
    protected Document createDefaultModel() {
	      return new ZenkakuDocument();
    }

    /**
     * 全角文字用Documentクラス
     * 
     * @author k-katayama
     */
    static class ZenkakuDocument extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	
    	/**
    	 * 文字挿入処理
    	 */
        public void insertString(int offset, String text, AttributeSet attributes) 
	          throws BadLocationException {
        	try{
        		
        		if (text == null || !checkZenkaku(text)) {

        			// 全角ではない一致しない場合、エラー
        			throw new BadLocationException(text, offset);
        			
        		}
        	}catch(BadLocationException e){
        		throw e;
        	}
        	super.insertString(offset,text,attributes);
        }
        
        /**
        * 全角チェック
        * @param String str チェック元文字列
        * @return boolean true:全角 false:全角以外の文字列あり
        */
        private boolean checkZenkaku(String str) {
        	
        	byte[] bytes;
        	
        	bytes = str.getBytes();
        	
        	// lengthの2倍を取得
        	int lentime = str.length() * 2;
        	 
        	StringBuffer sb = new StringBuffer(str);
        	for (int i = 0; i < str.length(); i++) {
        		if ('\n' == sb.charAt(i)) {
        			lentime = lentime - 2;
        		}
        	}
        	
        	// lengthの2倍と、引数のバイト数が一緒であれば全て全角
        	if (lentime == bytes.length) {
        		return true;
        	}

        	return false;
        }
    }
}