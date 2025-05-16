package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * �yQP@00342�z
 * ���C�����j���[�@���������t���O�擾
 *  �@�\ID�FFGEN2130
 *
 * @author Nishigawa
 * @since  2011/01/28
 */
public class FGEN2130_Logic extends LogicBase{

	/**
	 * ���C�����j���[�@���������t���O�擾
	 * : �C���X�^���X����
	 */
	public FGEN2130_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * ���C�����j���[�@���������t���O�擾
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
	 * @author TT.Nishigawa
	 * @since  2009/10/21
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
			this.em.ThrowException(e, "���C�����j���[�@���������t���O�擾���������s���܂����B");

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
			strSql.append(" 	   flg_kenkyu  ");
			strSql.append(" 	   ,flg_seisan  ");
			strSql.append(" 	   ,flg_genshizai  ");
			strSql.append(" 	   ,flg_kojo  ");
			strSql.append(" 	   ,flg_eigyo  ");
			strSql.append(" FROM ma_busho  ");
			strSql.append(" WHERE cd_kaisha = " + userInfoData.getCd_kaisha());
			strSql.append(" AND cd_busho = " + userInfoData.getCd_busho());

		} catch (Exception e) {
			this.em.ThrowException(e, "");
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

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				//�yADD 2015/07/27 TT.Kitazawa�yQP@40812�zNo.14 start -----------------------------------
				resTable.addFieldVale(i, "help_file", toString(ConstManager.getConstValue(Category.�ݒ�, "HELPFILE_PATH"),""));
				//ADD 2015/07/27 TT.Kitazawa�yQP@40812�zNo.14  END -------------------------------------


				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "flg_kenkyu", toString(items[0],""));
				resTable.addFieldVale(i, "flg_seikan", toString(items[1],""));
				resTable.addFieldVale(i, "flg_gentyo", toString(items[2],""));
				resTable.addFieldVale(i, "flg_kojo", toString(items[3],""));
				resTable.addFieldVale(i, "flg_eigyo", toString(items[4],""));
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
