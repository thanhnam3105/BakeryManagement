package jp.co.blueflag.shisaquick.srv.logic_gencho;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import java.util.List;

/**
 * �yQP@30297�z
 *  ��ތ���
 *  �@�\ID�FFGEN3010
 *
 * @author Sakamoto
 * @since  2014/01/28
 */
public class FGEN3010_Logic extends LogicBase{

	/**
	 * ��ތ���
	 * : �C���X�^���X����
	 */
	public FGEN3010_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * ��ތ���
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public RequestResponsKindBean ExecLogic(
			 RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���[�U�[���ޔ�
		userInfoData = _userInfoData;

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;

		try {

			// ���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			// �@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			// ���X�|���X�f�[�^�̌`��
			this.setData(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			// ���X�g�̔j��
			removeList(lstRecset);

			if (searchDB != null) {
				// �Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			// �ϐ��̍폜

		}
		return resKind;

	}

	/**
	 * ���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void setData(
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		// ���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;

		// ���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {
			//�e�[�u����
			String strTblNm = "table";

			//�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod start
			// �f�[�^�擾SQL�쐬
//			strSqlBuf = this.createSQL();
			strSqlBuf = this.createSQL(reqData);
			//�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod end

			// ���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			// ���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			// �ǉ������e�[�u���փ��R�[�h�i�[
			this.storageData(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "��ތ������������s���܂����B");

		} finally {
			// ���X�g�̔j��
			removeList(lstRecset);

			if (searchDB != null) {
				// �Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;

			}

			// �ϐ��̍폜
			strSqlBuf = null;

		}

	}

	//�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod start
	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
//	private StringBuffer createSQL()
	private StringBuffer createSQL(
			RequestResponsKindBean reqData
			)
    //�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod end
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		// SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			// SQL���̍쐬
		    //�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod start
			// ���������擾
			String strCdMaker = reqData.getFieldVale(0, 0, "cd_maker");


/*			strSql.append(" SELECT cd_��iteral  ");
			strSql.append("       ,nm_��iteral  ");
			strSql.append(" FROM  ma_literal  ");
			strSql.append(" WHERE cd_category='houzai'  ");
			strSql.append(" ORDER BY  no_sort ");
*/
			strSql.append(" SELECT ");
			strSql.append("        cd_2nd_literal  ");
			strSql.append("       ,nm_2nd_literal  ");
			strSql.append("       ,cd_��iteral  ");
			strSql.append("       ,nm_��iteral  ");
			strSql.append("       ,ISNULL(flg_mishiyo,'0')  AS flg_mishiyo");									//���g�p�t���O�yKPX@1602367�zadd
			strSql.append(" FROM  ma_literal  ");
			strSql.append(" WHERE cd_category = 'maker_name'  ");
			strSql.append("   AND cd_��iteral = '" + strCdMaker + "'  ");
			strSql.append(" ORDER BY  no_2nd_sort");
			//�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod end

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageData(
			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				// �������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				// ���ʂ����X�|���X�f�[�^�Ɋi�[
			    //�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod start
//				resTable.addFieldVale(i, "cd_literal", toString(items[0],""));
//				resTable.addFieldVale(i, "nm_literal", toString(items[1],""));
				resTable.addFieldVale(i, "cd_2nd_literal", toString(items[0],""));
				resTable.addFieldVale(i, "nm_2nd_literal", toString(items[1],""));
				resTable.addFieldVale(i, "cd_literal", toString(items[2],""));
				resTable.addFieldVale(i, "nm_literal", toString(items[3],""));
			    //�yQP@30297�zNo.19 E.kitazawa �ۑ�Ή� --------------------- mod end
				resTable.addFieldVale(i, "flg_mishiyo", toString(items[4],""));		//���g�p�t���O�yKPX@1602367�zadd
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}
}
