package jp.co.blueflag.shisaquick.srv.base;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;

/**
 * �I�u�W�F�N�g�̃x�[�X
 * @author isono
 *
 */
public class ObjectBase {

	//Exception�Ǘ��N���X
	protected ExceptionManager em;
	
	public ObjectBase(){
		//�N���X���������Ƃ��āAExceptionManager�̃C���X�^���X�𐶐�����B
		this.em = new ExceptionManager(this.getClass().getName());

	}
	
	/**
	 * Object��String��Ԃ��B
	 * @param obj : �Ώۂ̃I�u�W�F�N�g
	 * @return : String�inull�̏ꍇ�́h�h��Ԃ��j
	 */
	protected static String toString(Object obj ){

		return toString( obj,  "");
		
	}
	protected static String toString(Object obj,  String DefVal){

		return toString(obj, DefVal, true);
		
	}
	protected static String toString(Object obj,  String DefVal, boolean flg ){

		String ret = DefVal;
		
		try{
			ret = obj.toString();
			
		}catch(Exception e){
			
		}
		
		if (flg){
			
			if (ret.equals("")){
				ret = DefVal;
			}
			
		}
		
		return ret;
		
	}
	protected static String toString(Object obj,  String DefVal ,String reprceTg){

		String ret = "";
		try{
			ret = toString(obj, DefVal, true);

			ret = ret.replaceAll(reprceTg, "");
		
		}catch(Exception e){
			
		}
		return ret;

	}
	/**
	 * �_�u�������w��̃t�H�[�}�b�g�ŃX�g�����O�ɕϊ����܂��B
	 * @param target	�F�ҏW�Ώ�
	 * @param syosu		�F�����w��i0�`...�j
	 * @param kirisute	�F�w�茅�ȉ��̏����i1�F�؎̂āA2�F�؂�グ�A3�F�l�̌ܓ��j
	 * @param kanma		�F�������̃J���}�ҏW�itrue�F���{�Afalse�F�����{�j
	 * @param DefVal	�F���s���̕ԋp�l
	 * @return
	 */
	protected static String toString(
			
			double target
			, int syosu
			, int kirisute
			, boolean kanma 
			, String DefVal
			){
		
		String ret = DefVal;
		BigDecimal tg = null;
		String strZero = "0000000000";
		
		try{
			if (kirisute == 1){
				//�؎̂�
				tg = new BigDecimal(toRoundDown(target, syosu));

			}else if(kirisute == 2){
				//�؂�グ
				tg = new BigDecimal(toRoundUp(target, syosu));
				
			}else if(kirisute == 3){
				//�l�̌ܓ�
				tg = new BigDecimal(toRoundString(target, syosu));
		
			}
			
			if (syosu > 0){
				strZero = "." + getRight(strZero, syosu);	
				
			}else{
				strZero = "";

			}
			
			if (kanma){
				//�J���}�ҏW
				DecimalFormat decimalformat = new DecimalFormat("###,##0" + strZero); 
				ret = decimalformat.format(tg); 
				
			}else{
				//�J���}�ҏW�Ȃ�
				DecimalFormat decimalformat = new DecimalFormat("#####0" + strZero); 
				ret = decimalformat.format(tg); 
				
			}
			
		}catch(Exception e){
			
		}
		return ret;
		
	}
	/**
	 * �I�u�W�F�N�g��BigDecimal�ɕϊ�����B
	 * @param obj : �Ώۂ̃I�u�W�F�N�g
	 * @return : BigDecimal�i�ϊ��s�\�ȏꍇ�́A0�j
	 */
	protected BigDecimal toDecimal(Object obj){

		return toDecimal(obj,"0");
		
	}
	protected BigDecimal toDecimal(Object obj, String defVal){
		
		BigDecimal ret = new BigDecimal(defVal);
		
		try{
			ret = new BigDecimal(toString(obj));
			
		}catch(Exception e){
			
		}
		return ret;
		
	}
	/**
	 * Object�𐔒l�ɕϊ�����
	 * @param obj : �Ώۂ̃I�u�W�F�N�g
	 * @return double : (�s�\�ȏꍇ��0��Ԃ�)
	 */
	protected static double toDouble(Object obj){

		return toDouble(obj, 0);
		
	}
	protected static double toDouble(Object obj, double defVal){
		
		double ret = defVal;

		try {
			ret = Double.parseDouble(toString(obj,  "" , ","));

		} catch(NumberFormatException e) {

        }
		return ret;
		
	}
	/**
	 * Object�𐔒l�ɕϊ�����(int)
	 * @param obj : �Ώۂ̃I�u�W�F�N�g
	 * @return double : (�s�\�ȏꍇ��0��Ԃ�)
	 */
	protected static int toInteger(Object obj){
		
		return toInteger(obj, 0);
		
	}
	protected static int toInteger(Object obj, int defVal){
		
		int ret = defVal;

		try {
			ret = Integer.parseInt(toString(obj));

		} catch(Exception e) {

        }
		return ret;
		
	}
	/**
	 * �l�̌ܓ�
	 * @param value : �Ώۂ̒l
	 * @param round : �l�̌ܓ��̌���
	 * @return double : �l�̌ܓ��̌���
	 */
	protected static double toRound(double value, int round){
		
		double ret = 0;

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		ret = bd.setScale(round, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return ret;
		
	}
	/**
	 * �؂�グ
	 * @param value : �Ώۂ̒l
	 * @param round : �؂�グ�̌���
	 * @return double : �؂�グ�̌���
	 */
	protected static double toRoundUp(double value, int round){
		
		double ret = 0;

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		ret = bd.setScale(round, BigDecimal.ROUND_UP).doubleValue();
		
		return ret;
		
	}
	/**
	 * �؂�̂�
	 * @param value : �Ώۂ̒l
	 * @param round : �؂�̂Ă̌���
	 * @return double : �؂�̂Ă̌���
	 */
	protected static double toRoundDown(double value, int round){
		
		double ret = 0;

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		ret = bd.setScale(round, BigDecimal.ROUND_DOWN).doubleValue();
		
		return ret;
		
	}
	/**
	 * �l�̌ܓ�
	 * @param value : �Ώۂ̒l
	 * @param round : �l�̌ܓ��̌���
	 * @return double : �l�̌ܓ��̌���(������^)
	 */
	protected static String toRoundString(double value, int round){
		
		String ret = "";

		BigDecimal bd = new BigDecimal(String.valueOf(value));
		ret = bd.setScale(round, BigDecimal.ROUND_HALF_UP).toString();
		
		return ret;
		
	}
	/**
	 * ������̉E����w�肵�������������؂�o��
	 * @param tgStr�@:�@�^�[�Q�b�g�̕�����
	 * @param ix�@:�@�؂�o��������
	 * @return�@String�@�F�@�؂�o���ꂽ������i�G���[�̏ꍇ�́h�h�j
	 */
	protected static String getRight(String tgStr, int ix) {
		
		String ret = "";
		
		try{
			ret = tgStr.substring(tgStr.length()-ix,tgStr.length());
			
		}catch(Exception e){
			
		}
		return ret;
	      
	}
	
	
	/**
	 * ���X�gObject�̔j��
	 * @param listObj : �j���Ώۂ̃��X�g
	 */
	protected static void removeList(Object Obj){

		try{
			for(int ix = ((List<?>)Obj).size()-1; ix > -1; ix--){
				removeList(((List<?>)Obj).get(ix));
				((List<?>)Obj).remove(ix);
				
			}
			
		}catch(Exception e){

		} finally {
			Obj = null;
		}
		
	}

	/**
	 * ���X�g����؂蕶���t��������ɕϊ�����
	 * @param tgList : �ϊ��Ώۂ̃��X�g
	 * @param sp : ��؂蕶��
	 * @return String �ϊ����ʂ̕�����
	 */
	protected String ListToString(List<String> tgList, String sp){
		
		String ret = "";
		
		for (int ix = 0; ix < tgList.size(); ix++ ){
			ret += tgList.get(ix) + sp;
			
		}
		return ret;
		
	}
	/**
	 * ��؂蕶���t���̕���������X�g�ɕϊ�����
	 * @param tgString : �ϊ��Ώۂ̋�؂蕶���t��������
	 * @param sp : ��؂蕶��
	 * @return ArrayList<String> �ϊ����ʃ��X�g
	 */
	protected ArrayList<String> StringToList(String tgString, String sp){
		
		ArrayList<String> ret = new ArrayList<String>();
		
		String[] strings = tgString.split(sp);
		//�t�@�C������URL�𐶐�
		for(int i = 0; i < strings.length; i++){
			//�_�E�����[�hURL���i�[
			ret.add(strings[i]);
		}
		strings = null;
		
		return ret;
		
	}
	
	/**
	 * ��؂蕶���t���̕��������t�^�ɕϊ��i�x�x�x�x�^�l�l�^�c�c�j
	 * @param strdate : �ϊ��Ώۂ̓��͓��t
	 * @return string : �ϊ�����t
	 */
	protected String cnvDateFormat(String strdate){
		
		String ret = null;
		String[] strTemps = null;
		
		try{
			
			//�z��ɕ���
			if(strdate.split("/").length == 3){
				//�@�u/�v�ŕ���
				strTemps = strdate.split("/");
			}else if(strdate.split("-").length == 3){
				//�A�u-�v�ŕ���
				strTemps = strdate.split("-");
			}else if(strdate.split("\\.").length == 3){
				//�B�u.�v�ŕ���(�u.�v�͐��K�\���Łu�����P�����v�̈Ӗ��ƂȂ�̂ŁA�o�b�N�X���b�V����t�^���đΉ�)
				strTemps = strdate.split("\\.");
			}else if(strdate.length() == 8){
				//��؂蕶������8��
				strdate = strdate.substring(0,4) + "/" +strdate.substring(4,6) + "/" + strdate.substring(6,8);
				strTemps = strdate.split("/");
			}else if(strdate.length() == 6){
				//��؂蕶������6��
				strdate = strdate.substring(0,2) + "/" +strdate.substring(2,4) + "/" + strdate.substring(4,6);
				strTemps = strdate.split("/");
			}else{
				//��L�ȊO�̏ꍇ�͌��̒l��Ԃ�
				return strdate;
			}
			
			//���t�̃t�H�[�}�b�g���w��
			MessageFormat mf = new MessageFormat("{0,number,0000}/{1,number,00}/{2,number,00}");
			
			//�N��4���ȊO�̏ꍇ�ɁA�t�H�[�}�b�g��ύX
			if(toString(strTemps[0]).length() != 4){
				
				//���݂̔N���擾
				Calendar cal1 = Calendar.getInstance();  //�I�u�W�F�N�g�̐���
				String year = Integer.toString(cal1.get(Calendar.YEAR));       //���݂̔N���擾
				String f_year = year.substring(0,1); //�N�̈ꕶ���ڂ��擾�i�t�H�[�}�b�g�p�j
				
				//�N�̈ꕶ���ڂɌ��݂̔N�����w��
				mf = new MessageFormat("{0,number," + f_year + "000}/{1,number,00}/{2,number,00}");
			}
			//�N��0���ȊO�̏ꍇ�ɁA���������̂܂ܕԋp
			if(toString(strTemps[0]).length() == 0){
				
				return strdate;
				
			}
			
			Object[] dateobj = {toInteger(strTemps[0]), toInteger(strTemps[1]), toInteger(strTemps[2])};
			ret = mf.format(dateobj);
			
			//�N�����u�O�v�̏ꍇ�u�Q�v�ɒu������
			//ret = ret.replaceFirst("0", "2");
			
		}catch(Exception e){
			
			return strdate;
			
		}
		
		return ret;
	}
	

}