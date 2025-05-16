package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * ���ޏ��擾DB����
 *  �@�\ID�FFGEN3350�@
 *  
 * @author TT.Shima
 * @since  2014/09/25
 */
public class FGEN3350_Logic extends LogicBase {
	
	/**
	 * ���ޏ��擾�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public FGEN3350_Logic(){
		super();
	}
	
	/**
	 * ���ޏ��擾 : ���ޏ����擾����B
	 * 
	 * @param reqData
	 *            : �@�\���N�G�X�g�f�[�^
	 * @param userInfoData
	 *            : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(RequestResponsKindBean reqData,
			UserInfoData _userInfoData) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		// ���[�U�[���ޔ�
		userInfoData = _userInfoData;
		List<?> lstRecset = null;
		StringBuffer strSqlWhere = new StringBuffer();
		StringBuffer strSql = new StringBuffer();

		RequestResponsKindBean resKind = null;

		try {
			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// SQL�쐬�iWhere��擾�j
			strSqlWhere = createWhereSQL(reqData, strSqlWhere);

			// SQL�쐬�i���ރf�[�^�擾�j
			strSql = createTehaiSQL(reqData, strSql, strSqlWhere);

			// DB�C���X�^���X����
			createSearchDB();

			// �������s�i���ރf�[�^�擾�j
			lstRecset = searchDB.dbSearch(strSql.toString());

			// �������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0) {
				em.ThrowException(ExceptionKind.�x��Exception, "W000401",
						strSql.toString(), "", "");
			}

			// �@�\ID�̐ݒ�
			resKind.setID(reqData.getID());

			// �e�[�u�����̐ݒ�
			resKind.addTableItem(reqData.getTableID(0));

			// ���X�|���X�f�[�^�̌`��
			storageShizaiData(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "���ޏ��擾DB�����Ɏ��s���܂����B");
		} finally {
			// ���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				// �Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			// �ϐ��̍폜
			strSqlWhere = null;
			strSql = null;
		}
		return resKind;

	}
	
	/**
	 * �擾SQL�쐬
	 *  : ��z�����擾����SQL���쐬�B
	 * 
	 * @param strSql
	 *            : ��������SQL
	 * @param arrCondition
	 *            �F������������
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createTehaiSQL(RequestResponsKindBean reqData,
			StringBuffer strSql, StringBuffer strWhere) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {
		
		StringBuffer strAllSql = new StringBuffer();
		
		try {
			// SQL���̍쐬
			strAllSql.append(" SELECT ");
			strAllSql.append("   sekkei1, ");
			strAllSql.append("   sekkei2, ");
			strAllSql.append("   sekkei3, ");
			strAllSql.append("   zaishitsu, ");
			strAllSql.append("   biko_tehai, ");
			strAllSql.append("   printcolor, ");
			strAllSql.append("   no_color, ");
			strAllSql.append("   henkounaiyoushosai, ");
			strAllSql.append("   nouki, ");
			strAllSql.append("   suryo, ");
			strAllSql.append("   old_sizaizaiko, ");
			strAllSql.append("   rakuhan ");
			strAllSql.append(" FROM ");
			strAllSql.append("   tr_shizai_tehai ");
			strAllSql.append(strWhere);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���ޏ��擾DB�����Ɏ��s���܂����B");
		} finally {

		}
		return strAllSql;
	}
	
	/**
	 * Where��擾SQL�쐬
	 * 
	 * @param reqData
	 *            : ���N�G�X�g�f�[�^
	 * @param strSql
	 *            : ��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createWhereSQL(RequestResponsKindBean reqData,
			StringBuffer strSql) throws ExceptionSystem, ExceptionUser,
			ExceptionWaning {

		StringBuffer strWhere = new StringBuffer();

		try {
			// ���������擾---------------------------------------------------------------------
			
			// �Ј��R�[�h
			String cd_shain = toString(reqData.getFieldVale(0, 0, "cd_shain"));
			
			// �N
			String nen = toString(reqData.getFieldVale(0, 0, "nen"));
			
			// �ǔ�
			String no_oi = toString(reqData.getFieldVale(0, 0, "no_oi"));
			
			// �s�ԍ�
			String seq_shizai = toString(reqData.getFieldVale(0, 0, "seq_shizai"));
			
			// �}��
			String no_eda = toString(reqData.getFieldVale(0, 0, "no_eda"));
			
			// WHERE��SQL�쐬----------------------------------------------------------------
			strWhere.append(" WHERE ");
			strWhere.append("     cd_shain = '" + cd_shain + "' ");
			strWhere.append(" AND nen = '" + nen + "' ");
			strWhere.append(" AND no_oi = '" + no_oi + "' ");
			strWhere.append(" AND seq_shizai = '" + seq_shizai + "' ");
			strWhere.append(" AND no_eda = '" + no_eda + "' ");

			
		} catch (Exception e) {

			em.ThrowException(e, "���ޏ��擾DB�����Ɏ��s���܂����B");

		} finally {

		}

		return strWhere;
	}
	
	/**
	 * ���ޏ��p�����[�^�[�i�[ : ���ޏ������X�|���X�f�[�^�֊i�[����B
	 * 
	 * @param lstRecset
	 *            : �������ʏ�񃊃X�g
	 * @param resTable
	 *            : ���X�|���X���
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageShizaiData(List<?> lstRecset,
			RequestResponsTableBean resTable) throws ExceptionSystem,
			ExceptionUser, ExceptionWaning {

		try {
			// �������ʎ擾
			Object[] items = (Object[]) lstRecset.get(0);
			String sekkei1 = toString(items[0]);
			String sekkei2 = toString(items[1]);
			String sekkei3 = toString(items[2]);
			String zaishitsu = toString(items[3]);
			String biko_tehai = toString(items[4]);
			String printcolor = toString(items[5]);
			String no_color = toString(items[6]);
			String henkounaiyoushosai = toString(items[7]);
			String nouki = toString(items[8]);
			String suryo = toString(items[9]);
			String old_sizaizaiko = toString(items[10]);
			String rakuhan = toString(items[11]);
			
			if(0 < lstRecset.size()){
				// �������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				// ���X�|���X����
				resTable.addFieldVale(0, "sekkei1", sekkei1);
				resTable.addFieldVale(0, "sekkei2", sekkei2);
				resTable.addFieldVale(0, "sekkei3", sekkei3);
				resTable.addFieldVale(0, "zaishitsu", zaishitsu);
				resTable.addFieldVale(0, "biko_tehai", biko_tehai);
				resTable.addFieldVale(0, "printcolor", printcolor);
				resTable.addFieldVale(0, "no_color", no_color);
				resTable.addFieldVale(0, "henkounaiyoushosai", henkounaiyoushosai);
				resTable.addFieldVale(0, "nouki", nouki);
				resTable.addFieldVale(0, "suryo", suryo);
				resTable.addFieldVale(0, "old_sizaizaiko", old_sizaizaiko);
				resTable.addFieldVale(0, "rakuhan", rakuhan);
			} 

		} catch (Exception e) {
			this.em.ThrowException(e, "���ޏ��擾DB�����Ɏ��s���܂����B");

		} finally {

		}

	}

}
