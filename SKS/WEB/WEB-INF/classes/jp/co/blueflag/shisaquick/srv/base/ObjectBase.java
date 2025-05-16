package jp.co.blueflag.shisaquick.srv.base;

import java.math.BigDecimal;
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

		String ret = "";
		
		if (obj != null){
			ret = obj.toString();
			
		}
		return ret;
		
	}
	/**
	 * Object�𐔒l�ɕϊ�����
	 * @param obj : �Ώۂ̃I�u�W�F�N�g
	 * @return double : (�s�\�ȏꍇ��0��Ԃ�)
	 */
	protected static double toDouble(Object obj){
		
		double ret = 0;

		try {
			ret = Double.parseDouble(toString(obj));

		} catch(NumberFormatException e) {

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

}
