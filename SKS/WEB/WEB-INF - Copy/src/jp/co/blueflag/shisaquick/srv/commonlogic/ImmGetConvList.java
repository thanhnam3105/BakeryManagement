package jp.co.blueflag.shisaquick.srv.commonlogic;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/*****************************************************************************
 * 
 * �����ϊ����ꗗ�擾
 *  : DB�ima_yomigana�j�ϊ����ꗗ�擾
 *  
 * @author TT.ISONO
 * @since  2009/08/28
 * 
 *****************************************************************************/
public class ImmGetConvList  extends LogicBase{
	
	/**
	 * �����ϊ����ꗗ�擾�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public ImmGetConvList() {
		
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
		//DB��������
		List<?> lstSearchData = null;
		//SQL�i�[
		StringBuffer strSql;
		
		//���ꗗ�擾
		try{

			//��␔�̍ő�l���擾
			double ListMax = toDouble(ConstManager.getConstValue(ConstManager.Category.�ݒ�,"IME_LIST_MAX"));

			//����SQL�𐶐�
			strSql = createSQL(strInput);
			
			//�f�[�^�x�[�X�����A�������ʂ��擾����B
			createSearchDB();
			lstSearchData = searchDB.dbSearch(strSql.toString());
			
			//�ԋp�p�z��ɕϊ��O�̕�����ǉ�
			ret.add(strInput);
			
			for (int i = 0; i < lstSearchData.size() ; i++) {

				//��␔�̍ő�l�𒴂��Ă��邩�m�F
				if (i < ListMax-1){
					//���ő�l�ȓ��̏ꍇ�A�ǉ�
					Object[] items = (Object[]) lstSearchData.get(i);
					ret.add(items[0]);
					
				}
			
			}
			
		} catch(Exception e){
			this.em.ThrowException(e, "�ϊ����ꗗ�̎擾�Ɏ��s���܂����B");
			
		} finally{
			
			//DB�Z�b�V�����J��
			searchDB.Close();
			//���[�J���ϐ��̊J��
			removeList(lstSearchData);
			
		}
		
		//�ԋp
		return ret;
		
	}

	/**
	 * �����ϊ����ꗗ �����pSQL�쐬
	 * @param �ϊ��Ώە�����
	 * @return �쐬SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createSQL(String strTg)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		//SQL���쐬����B		
		try {

			strSQL.append("select ");
			strSQL.append("  nm_kana as nm_target ");
			strSQL.append(" ,dt_toroku ");
			strSQL.append(" from ");
			strSQL.append("  ma_yomigana ");
			strSQL.append(" where ");
			strSQL.append("  nm_kanji = '" + strTg + "' ");
			strSQL.append(" union all ");
			strSQL.append(" select ");
			strSQL.append("  nm_kanji as nm_target ");
			strSQL.append(" ,dt_toroku ");
			strSQL.append(" from ");
			strSQL.append("  ma_yomigana ");
			strSQL.append(" where ");
			strSQL.append("  nm_kana = '" + strTg + "' ");
			strSQL.append(" order by dt_toroku DESC ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����ϊ����ꗗ �����pSQL�쐬�����s���܂����B");
			
		} finally {
			//���[�J���ϐ��̍폜
			
		}
		//�쐬����SQL��ԋp
		return strSQL;
		
	}

}
