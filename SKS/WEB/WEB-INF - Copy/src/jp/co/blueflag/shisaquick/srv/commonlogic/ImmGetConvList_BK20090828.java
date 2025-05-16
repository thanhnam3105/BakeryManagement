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
 * IME�ϊ����ꗗ�擾
 *  : IME�ϊ����ꗗ�擾�p��DLL��JNI�o�R�ŌĂяo���A���ꗗ���擾
 *  : OS�ˑ��yWindowsOS��IME����DLL�iimm32.dll�j�g�p�ׁ̈j�z
 *  
 * @author TT.Nishigawa
 * @since  2009/08/26
 * 
 *****************************************************************************/
public class ImmGetConvList_BK20090828  extends LogicBase{
	
	// ���C�u���������[�h
	static {
		System.loadLibrary("ImmGetConvList");
	}
	
	// �l�C�e�B�u���\�b�h��錾
	native static String getConvListChange(String a);
	
	// �l�C�e�B�u���\�b�h��錾
	native static String getConvListYomi(String a);
	
	
	/**
	 * IME�ϊ����ꗗ�擾�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public ImmGetConvList_BK20090828() {
		
		//���N���X�̃R���X�g���N�^
		super();
	}

	
	/****************************************************************************
	 * 
	 * �ϊ����ꗗ�擾
	 * @param  strInput   : �ϊ�����
	 * @return  ArrayList : ���ꗗ�z��
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 * 
	 ****************************************************************************/
	public ArrayList ImmGetConvListChange(String strInput)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		
		//�ԋp�p�z�񏉊���
		ArrayList ret = new ArrayList();
		
		
		//���ꗗ�擾
		try{
			
			
			//�ϊ������ޔ�
			String strRet = strInput;
			
			
			//��␔�擾
			String strList = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"IME_LIST_MAX");
			
			
			//�������ǂ����̔��f
			Pattern pattern = Pattern.compile("^[��-�]*$");
			Matcher matcher = pattern.matcher(strInput);
			boolean b= matcher.matches();
			
			
			//���������͂��ꂽ�ꍇ
			if(b){
				
				//�ϊ����擾�i�ǂ݁j
				strRet = getConvListYomi(strInput);
				
			}
			//�����ȊO�����͂��ꂽ�ꍇ
			else{
				
				//�ϊ����擾
				strRet = getConvListChange(strInput);
				
			}
			
			
			//�f�o�b�O���x���ȊO�̏ꍇ�ɏo��
			String DEBUG_LEVEL = ConstManager.getConstValue(ConstManager.Category.�ݒ�,"CONST_DEBUG_LEVEL");
			if(DEBUG_LEVEL.equals("0")){
				
				//��������
				
			}else{
				try{
					//-------------- �t�@�C���o�� --------------------
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
			
			//�ԋp��������
			String[] strArySplit = strRet.split(",");
			
			
			//�ԋp�l�ݒ�i�R���X�g�ɐݒ肵�Ă�������A�ԋp�l�ɐݒ�j
			for(int i = 0; i < strArySplit.length && i < Integer.parseInt(strList); i++){
				
				//�ԋp�p�z��ɒǉ�
				ret.add(strArySplit[i]);
				
			}
			
		} catch(Exception e){
			
			this.em.ThrowException(e, "IME�ϊ����ꗗ�擾�Ɏ��s���܂����B");
			
		} finally{
			
		}
		
		
		//�ԋp
		return ret;
		
	}
	
	
	

}
