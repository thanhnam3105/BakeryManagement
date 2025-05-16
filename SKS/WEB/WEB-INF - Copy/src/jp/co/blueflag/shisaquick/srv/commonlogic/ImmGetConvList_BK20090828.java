package jp.co.blueflag.shisaquick.srv.commonlogic;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/*****************************************************************************
 * 
 * IME変換候補一覧取得
 *  : IME変換候補一覧取得用のDLLをJNI経由で呼び出し、候補一覧を取得
 *  : OS依存【WindowsOSのIME操作DLL（imm32.dll）使用の為）】
 *  
 * @author TT.Nishigawa
 * @since  2009/08/26
 * 
 *****************************************************************************/
public class ImmGetConvList_BK20090828  extends LogicBase{
	
	// ライブラリをロード
	static {
		System.loadLibrary("ImmGetConvList");
	}
	
	// ネイティブメソッドを宣言
	native static String getConvListChange(String a);
	
	// ネイティブメソッドを宣言
	native static String getConvListYomi(String a);
	
	
	/**
	 * IME変換候補一覧取得コンストラクタ 
	 * : インスタンス生成
	 */
	public ImmGetConvList_BK20090828() {
		
		//基底クラスのコンストラクタ
		super();
	}

	
	/****************************************************************************
	 * 
	 * 変換候補一覧取得
	 * @param  strInput   : 変換文字
	 * @return  ArrayList : 候補一覧配列
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 ****************************************************************************/
	public ArrayList ImmGetConvListChange(String strInput)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		
		//返却用配列初期化
		ArrayList ret = new ArrayList();
		
		
		//候補一覧取得
		try{
			
			
			//変換文字退避
			String strRet = strInput;
			
			
			//候補数取得
			String strList = ConstManager.getConstValue(ConstManager.Category.設定,"IME_LIST_MAX");
			
			
			//漢字かどうかの判断
			Pattern pattern = Pattern.compile("^[一-龠]*$");
			Matcher matcher = pattern.matcher(strInput);
			boolean b= matcher.matches();
			
			
			//漢字が入力された場合
			if(b){
				
				//変換候補取得（読み）
				strRet = getConvListYomi(strInput);
				
			}
			//漢字以外が入力された場合
			else{
				
				//変換候補取得
				strRet = getConvListChange(strInput);
				
			}
			
			
			//デバッグレベル以外の場合に出力
			String DEBUG_LEVEL = ConstManager.getConstValue(ConstManager.Category.設定,"CONST_DEBUG_LEVEL");
			if(DEBUG_LEVEL.equals("0")){
				
				//処理無し
				
			}else{
				try{
					//-------------- ファイル出力 --------------------
					FileOutputStream fos = new FileOutputStream("kakunin.txt");
				    OutputStreamWriter osw = new OutputStreamWriter(fos , "MS932");
				    BufferedWriter bw = new BufferedWriter(osw);
				    String msg = strRet;
				    bw.write(msg);
				      
				    bw.close();
				    osw.close();
				    fos.close();
				    //-----------------------------------------------
				}catch(Exception e){
					
				}
			}
			
			//返却文字分割
			String[] strArySplit = strRet.split(",");
			
			
			//返却値設定（コンストに設定してある個数分、返却値に設定）
			for(int i = 0; i < strArySplit.length && i < Integer.parseInt(strList); i++){
				
				//返却用配列に追加
				ret.add(strArySplit[i]);
				
			}
			
		} catch(Exception e){
			
			this.em.ThrowException(e, "IME変換候補一覧取得に失敗しました。");
			
		} finally{
			
		}
		
		
		//返却
		return ret;
		
	}
	
	
	

}
