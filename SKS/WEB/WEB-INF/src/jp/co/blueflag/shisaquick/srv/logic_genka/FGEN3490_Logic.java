package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yKPX@1602367�z
 * ���C�����j���[�@������ƒ��̏����擾����
 *  �@�\ID�FFGEN3490
 *
 * @author May Thu
 * @since  2016/09/01
 */
public class FGEN3490_Logic extends LogicBase{

	/**
	 * ���C�����j���[�@����ƒ��̏����擾����
	 * : �C���X�^���X����
	 */
	public FGEN3490_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * ���C�����j���[�@����ƒ��̏����擾����
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

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;

		try {

			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//���X�|���X�f�[�^�̌`��
			this.genkaKihonSetting(resKind, reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);

			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜

		}
		return resKind;

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting�i���X�|���X�f�[�^�̌`���j
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * ���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author May Thu
	 * @since  2016/09/01
	 */
	private void genkaKihonSetting(

			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;

		//���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {
			//�e�[�u����
			String strTblNm = "table";

			//�f�[�^�擾SQL�쐬
			strSqlBuf = this.createGenkaKihonSQL(reqData);

			//���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());

			//���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);

			//�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));

		} catch (Exception e) {
			this.em.ThrowException(e, "���C�����j���[�@����ƒ��̏����擾���������s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);

			if (searchDB != null) {
				//�Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;

			}

			//�ϐ��̍폜
			strSqlBuf = null;

		}

	}
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL�iSQL�������j
	//
   //--------------------------------------------------------------------------------------------------------------
	/**
	 * �f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaKihonSQL(
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {
			//SQL���̍쐬
			strSql.append(" SELECT ");
			strSql.append(" M01.nm_user,count(*) AS ORDER_CNT  ");
			strSql.append(" FROM tr_shisan_shizai T01 ");
			strSql.append("inner join ma_user M01 on (");
			strSql.append("		   T01.cd_shain = M01.id_user");
			strSql.append("		)");
			strSql.append(" WHERE (T01.cd_hattyu IS NULL ");
			strSql.append(" OR ISNULL(T01.dt_hattyu,'') = '' )" );
			strSql.append(" AND ISNULL(T01.cd_shizai_new,'') <> '' " );
			strSql.append(" group by M01.nm_user" );
			strSql.append(" order by M01.nm_user" );
		} catch (Exception e) {
			this.em.ThrowException(e, "FGEN3490_Logic:createGenkaKihonSQL");
		} finally {

		}
		return strSql;
	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData�i�p�����[�^�[�i�[�j
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �p�����[�^�[�i�[
	 *  : ���X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenkaKihon(

			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {

			for (int i = 0; i < lstGenkaHeader.size(); i++) {
				Object[] items = (Object[]) lstGenkaHeader.get(i);
				//�������ʂ�
				resTable.addFieldVale(i, "nm_hattyu", toString(items[0],""));
				resTable.addFieldVale(i, "ORDER_CNT", toString(items[1],""));
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "FGEN3490_Logic:storageGenkaKihon");
		} finally {

		}

	}

}
