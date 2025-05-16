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
 * 数値テキストボックス
 *  : 半角数値のみ入力可能。
 *  
 * @author k-katayama
 * @since 2009/04/02
 *
 */
public class NumelicTextbox extends TextboxBase {
	private static final long serialVersionUID = 1L;
	
	String strShosuten = "";

	/**
	 * コンストラクタ
	 */
	public NumelicTextbox() {
        super();
        
        //右揃えに設定
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        
        //表示状態に設定
        this.setVisible(true);
    }

	/**
	 * テキストボックスのフォルトモデルの作成
	 * @return 数値用Document
	 */
    protected Document createDefaultModel() {
	      return new NumericDocument();
    }
    
    /**
     * 数値用Documentクラス
     * 
     * @author k-katayama
     */
    class NumericDocument extends PlainDocument {
    	private static final long serialVersionUID = 1L;
    	
    	/**
    	 * 文字挿入処理
    	 */
        public void insertString(int offset, String text, AttributeSet attributes) 
	          throws BadLocationException {
        	
        	try{

        		//入力内容を取得
        		String strInput = NumelicTextbox.this.getText();
        		
        		//入力内容に小数点が入力されている場合
        		if(strInput.indexOf(".") > -1){
        			
        			//入力文字が「数値」でない場合にエラー
            		if (text == null || !text.matches("^[0-9]+$")) {
            			
            			throw new BadLocationException(text, offset);
            			
            		}
        			
           		//入力内容に小数点が入力されていない場合
        		}else{
        			
        			//入力文字が「数値 or 小数点]でない場合にエラー
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

