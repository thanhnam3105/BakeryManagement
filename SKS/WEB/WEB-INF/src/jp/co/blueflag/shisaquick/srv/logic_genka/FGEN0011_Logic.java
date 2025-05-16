package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 *
 * �������Z��{���\������DB����
 *  : �������Z��ʂ̊�{��񕔕��̏����擾����B
 *  �@�\ID�FFGEN0010_Logic
 *
 * @author Nishigawa
 * @since  2009/10/22
 */
public class FGEN0011_Logic extends LogicBase{

	int clsCD_Kaisy = 0;

	/**
	 * �������Z��{���\������DB����
	 * : �C���X�^���X����
	 */
	public FGEN0011_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �������Z��{���\��
	 *  : �������Z��ʂ̊�{��񕔕��̏����擾����B
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


			//�������Z��{���[ kihon ]�e�[�u���@���X�|���X�f�[�^�̌`��
			this.genkaKihonSetting(resKind,reqData);

			//�������Z��{���[ kaisya ]�e�[�u���@���X�|���X�f�[�^�̌`��
			this.genkaKaisyaSetting(resKind,reqData);

			//�������Z��{���[ kojo ]�e�[�u���@���X�|���X�f�[�^�̌`��
			this.genkaKojoSetting(resKind,reqData);

			//�������Z��{���[ tani ]�e�[�u���@���X�|���X�f�[�^�̌`��
			this.genkaTaniSetting(resKind,reqData);

			//�yQP@00342�z�������Z��{���[ �̔����ԁi�ʔNor�X�|�b�g�j ]�e�[�u���@���X�|���X�f�[�^�̌`��
			this.genkaTani_HanbaiT_Setting(resKind,reqData);

			//�yQP@00342�z�������Z��{���[ �̔����ԁi�����j ]�e�[�u���@���X�|���X�f�[�^�̌`��
			this.genkaTani_HanbaiK_Setting(resKind,reqData);

			// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
			//�������Z��{���[�z�蕨�ʁi�P�ʁj]�e�[�u���@���X�|���X�f�[�^�̌`��
			this.genkaTani_ButuryoU_Setting(resKind,reqData);

			//�������Z��{���[�z�蕨�ʁi���ԁj]�e�[�u���@���X�|���X�f�[�^�̌`��
			this.genkaTani_ButuryoK_Setting(resKind,reqData);
			// ADD 2013/9/6 okano�yQP@30151�zNo.30 end

			// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			//�yQP@30151�z�������Z��{���[ kihonsub ]�e�[�u���@���X�|���X�f�[�^�̌`��
			this.genkaKihonSubSetting(resKind,reqData);
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 end


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

		}
		return resKind;

	}
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting�i���X�|���X�f�[�^�̌`���j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �������Z��{���[ kihon ]�e�[�u���@���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.Nishigawa
	 * @since  2009/10/22
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
			String strTblNm = "kihon";


			//�@�������Z��{���[ kihon ]�e�[�u���擾SQL�쐬
			strSqlBuf = this.createGenkaKihonSQL(reqData);


			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());


			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);


			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihon(lstRecset, resKind.getTableItem(strTblNm));


		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z��{���[ kihon ]�f�[�^�������������s���܂����B");

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

	/**
	 * �������Z��{���[ kaisya ]�e�[�u���@���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.Nishigawa
	 * @since  2009/10/22
	 */
	private void genkaKaisyaSetting(

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
			String strTblNm = "kaisya";


			//�@�������Z��{���[ kaisya ]�e�[�u���擾SQL�쐬
			strSqlBuf = this.createGenkaKaisyaSQL(reqData);


			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());


			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);


			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKaisya(lstRecset, resKind.getTableItem(strTblNm));


		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z��{���[ kaisya ]�f�[�^�������������s���܂����B");

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

	/**
	 * �������Z��{���[ kojo ]�e�[�u���@���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.Nishigawa
	 * @since  2009/10/22
	 */
	private void genkaKojoSetting(

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
			String strTblNm = "kojo";


			//�@�������Z��{���[ kojo ]�e�[�u���擾SQL�쐬
			strSqlBuf = this.createGenkaKojoSQL(reqData);


			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());


			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);


			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKojo(lstRecset, resKind.getTableItem(strTblNm));


		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z��{���[ kojo ]�f�[�^�������������s���܂����B");

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

	/**
	 * �������Z��{���[ tani ]�e�[�u���@���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.Nishigawa
	 * @since  2009/10/22
	 */
	private void genkaTaniSetting(

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
			String strTblNm = "tani";


			//�@�������Z��{���[ tani ]�e�[�u���擾SQL�쐬
			strSqlBuf = this.createGenkaTaniSQL(reqData);


			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());


			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);


			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaTani(lstRecset, resKind.getTableItem(strTblNm));


		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z��{���[ tani ]�f�[�^�������������s���܂����B");

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

	/**�yQP@00342�z
	 * �������Z��{���[ �̔����ԁi�ʔNor�X�|�b�g�j ]�e�[�u���@���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.Nishigawa
	 * @since  2009/10/22
	 */
	private void genkaTani_HanbaiT_Setting(

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
			String strTblNm = "tani_hanbai_T";


			//�@�������Z��{���[ tani ]�e�[�u���擾SQL�쐬
			strSqlBuf = this.createGenkaTani_Hanbai_T_SQL(reqData);


			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());


			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);


			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenka_HanbaiT_Tani(lstRecset, resKind.getTableItem(strTblNm));


		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z��{���[ �̔����ԁi�ʔNor�X�|�b�g�j  ]�f�[�^�������������s���܂����B");

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

	/**
	 * �������Z��{���[ �̔����ԁi�ʔNor�X�|�b�g�j ]�e�[�u���@���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.Nishigawa
	 * @since  2009/10/22
	 */
	private void genkaTani_HanbaiK_Setting(

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
			String strTblNm = "tani_hanbai_K";


			//�@�������Z��{���[ tani ]�e�[�u���擾SQL�쐬
			strSqlBuf = this.createGenkaTani_Hanbai_K_SQL(reqData);


			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());


			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);


			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenka_HanbaiK_Tani(lstRecset, resKind.getTableItem(strTblNm));


		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z��{���[�̔����ԁi�ʔNor�X�|�b�g�j ]�f�[�^�������������s���܂����B");

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

	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	/**
	 * �������Z�T���v�����̊�{���e�[�u���@���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 *
	 * @author TT.Shima
	 * @since  2013/06/24
	 */
	private void genkaKihonSubSetting(

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
			String strTblNm = "kihonsub";


			//�@�������Z��{���[ kihonsub ]�e�[�u���擾SQL�쐬
			strSqlBuf = this.createGenkaKihonSubSQL(reqData);


			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());


			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);


			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihonSub(lstRecset, resKind.getTableItem(strTblNm));

			//[kihon]�ɍs����ݒ�
			resKind.addFieldVale("kihon", "rec", "cnt_genryo"
					, toString(lstRecset.size()));


		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�T���v�����̊�{���f�[�^�������������s���܂����B");

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
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

	// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
	private void genkaTani_ButuryoU_Setting(

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
			String strTblNm = "sotei_buturyo_U";


			//�@�������Z��{���[ tani ]�e�[�u���擾SQL�쐬
			strSqlBuf = this.createGenkaSotei_Buturyo_U_SQL(reqData);


			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());


			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);


			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenka_ButuryoU_Tani(lstRecset, resKind.getTableItem(strTblNm));


		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z��{���[ �z�蕨�ʁi�P�ʁj  ]�f�[�^�������������s���܂����B");

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

	private void genkaTani_ButuryoK_Setting(

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
			String strTblNm = "sotei_buturyo_K";


			//�@�������Z��{���[ tani ]�e�[�u���擾SQL�쐬
			strSqlBuf = this.createGenkaSotei_Buturyo_K_SQL(reqData);


			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());


			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);


			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenka_ButuryoK_Tani(lstRecset, resKind.getTableItem(strTblNm));


		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z��{���[ �z�蕨�ʁi���ԁj  ]�f�[�^�������������s���܂����B");

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
	// ADD 2013/9/6 okano�yQP@30151�zNo.30 end

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL�iSQL�������j
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �������Z��{���[ kihon ]�e�[�u���擾SQL�쐬
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
			strSql.append("  M105.nm_group AS nm_group ");
			strSql.append(" ,M106.nm_team AS nm_team ");
			strSql.append(" ,M302_IKT.nm_literal AS nm_literal_IKT ");
			strSql.append(" ,M302_USR.nm_literal AS nm_literal_USR ");
			strSql.append(" ,T110.youto AS youto ");
			strSql.append(" ,T310.cd_kaisha AS cd_kaisha ");
			strSql.append(" ,M104.nm_kaisha AS nm_kaisha ");
			strSql.append(" ,T310.cd_kojo AS cd_kojo ");
			strSql.append(" ,M104.nm_busho AS nm_busho ");

			//�yQP@00342�z
			//strSql.append(" ,T110.cd_eigyo AS cd_eigyo ");
			strSql.append(" ,M110.nm_user ");

			strSql.append(" ,M302_HOH.nm_literal AS nm_literal_HOH ");
			strSql.append(" ,M302_JTH.nm_literal AS nm_literal_JTH ");
			strSql.append(" ,T110.hoho_sakin AS hoho_sakin ");
			strSql.append(" ,T110.youki AS youki ");
			strSql.append(" ,T310.yoryo AS yoryo ");
			strSql.append(" ,M302_TNI.nm_literal AS yoryo_tani ");
			strSql.append(" ,T310.su_iri AS su_iri ");
			strSql.append(" ,T310.cd_nisugata AS nm_nisugata ");
			strSql.append(" ,M302_OND.nm_literal AS nm_literal_OND ");
			// CHG 2019/09/17 BRC.ida start
			//strSql.append(" ,T110.shomikikan AS shomikikan ");
			strSql.append(" ,CASE WHEN T110.shomikikan IS NULL THEN NULL ");
			strSql.append("  ELSE T110.shomikikan + M302_TNI_SYUMI.nm_literal END AS shomikikan ");
			// CHG 2019/09/17 BRC.ida start
			strSql.append(" ,T310.genka AS genka ");
			strSql.append(" ,M302_TNI_G.nm_literal AS nm_literal_TNI_G ");
			strSql.append(" ,T310.cd_genka_tani AS cd_genka_tani ");
			strSql.append(" ,T310.baika AS baika ");
			strSql.append(" ,T310.buturyo AS buturyo ");
			strSql.append(" ,T310.dt_hatubai AS dt_hatubai ");
			strSql.append(" ,T310.uriage_k AS uriage_k ");
			strSql.append(" ,T310.rieki_k AS rieki_k ");
			strSql.append(" ,T310.uriage_h AS uriage_h ");
			strSql.append(" ,T310.rieki_h AS rieki_h ");
			strSql.append(" ,T310.lot AS lot ");
			strSql.append(" ,T311.memo AS memo_sisan ");

			//�yQP@00342�z
			strSql.append(" ,T312.memo_eigyo AS memo_eigyo ");
			strSql.append(" ,ISNULL(M104.keta_genryo ");
			strSql.append(" 	,6) AS keta_genryo ");
			strSql.append(" ,M302_Hanbai_T.nm_literal AS kikan_hanbai_ts ");
			strSql.append(" ,M302_Hanbai_K.nm_literal AS kikan_hanbai_k ");
			strSql.append(" ,T310.kikan_hanbai_sen AS kikan_hanbai_t_cd ");
			strSql.append(" ,T310.kikan_hanbai_tani AS kikan_hanbai_k_cd ");
			strSql.append(" ,T310.kikan_hanbai_suti AS kikan_hanbai_suti ");
			// ADD 2013/10/22 QP@30154 okano start
			strSql.append(" ,M104_hanseki.nm_kaisha AS nm_Hanseki ");
			// ADD 2013/10/22 QP@30154 okano end

			strSql.append(" FROM tr_shisan_shisakuhin AS T310 ");
			strSql.append(" LEFT JOIN tr_shisakuhin AS T110 ");
			strSql.append(" ON T310.cd_shain = T110.cd_shain ");
			strSql.append(" AND T310.nen = T110.nen ");
			strSql.append(" AND T310.no_oi = T110.no_oi ");
			strSql.append(" LEFT JOIN ma_group AS M105 ");
			strSql.append(" ON T110.cd_group = M105.cd_group ");
			strSql.append(" LEFT JOIN ma_team AS M106 ");
			strSql.append(" ON T110.cd_group = M106.cd_group ");
			strSql.append(" AND T110.cd_team = M106.cd_team ");
			strSql.append(" LEFT JOIN ma_literal AS M302_IKT ");
			strSql.append(" ON 'K_ikatuhyouzi' = M302_IKT.cd_category ");
			strSql.append(" AND T110.cd_ikatu = M302_IKT.cd_literal ");
			strSql.append(" LEFT JOIN ma_literal AS M302_USR ");
			strSql.append(" ON 'K_yuza' = M302_USR.cd_category ");
			strSql.append(" AND T110.cd_user = M302_USR.cd_literal ");
			strSql.append(" LEFT JOIN ma_busho AS M104 ");
			strSql.append(" ON T310.cd_kaisha = M104.cd_kaisha ");
			strSql.append(" AND T310.cd_kojo = M104.cd_busho ");
			strSql.append(" LEFT JOIN ma_literal AS M302_HOH ");
			strSql.append(" ON 'K_seizohoho' = M302_HOH.cd_category ");
			strSql.append(" AND T110.cd_hoho = M302_HOH.cd_literal ");
			strSql.append(" LEFT JOIN ma_literal AS M302_JTH ");
			strSql.append(" ON 'K_jyutenhoho' = M302_JTH.cd_category ");
			strSql.append(" AND T110.cd_juten = M302_JTH.cd_literal ");
			strSql.append(" LEFT JOIN ma_literal AS M302_TNI ");
			strSql.append(" ON 'K_tani' = M302_TNI.cd_category ");
			strSql.append(" AND T110.cd_tani = M302_TNI.cd_literal ");
			strSql.append(" LEFT JOIN ma_literal AS M302_OND ");
			strSql.append(" ON 'K_toriatukaiondo'= M302_OND.cd_category ");
			strSql.append(" AND T110.cd_ondo = M302_OND.cd_literal ");
			strSql.append(" LEFT JOIN ma_literal AS M302_TNI_G ");
			strSql.append(" ON 'K_tani_genka' = M302_TNI_G.cd_category ");
			strSql.append(" AND T310.cd_genka_tani= M302_TNI_G.cd_literal ");
			strSql.append(" LEFT JOIN tr_shisan_memo AS T311 ");
			strSql.append(" ON T310.cd_shain = T311.cd_shain ");
			strSql.append(" AND T310.nen = T311.nen ");
			strSql.append(" AND T310.no_oi = T311.no_oi ");
			strSql.append(" LEFT JOIN tr_shisan_memo_eigyo AS T312 ");
			strSql.append(" ON T310.cd_shain = T312.cd_shain ");
			strSql.append(" AND T310.nen = T312.nen ");
			strSql.append(" AND T310.no_oi = T312.no_oi ");
			strSql.append(" LEFT JOIN ma_literal AS M302_Hanbai_T ");
			strSql.append(" ON 'hanbai_kikan_sentaku'= M302_Hanbai_T.cd_category ");
			strSql.append(" AND T310.kikan_hanbai_sen = M302_Hanbai_T.cd_literal ");
			strSql.append(" LEFT JOIN ma_literal AS M302_Hanbai_K ");
			strSql.append(" ON 'hanbai_kikan_tani'= M302_Hanbai_K.cd_category ");
			strSql.append(" AND T310.kikan_hanbai_tani = M302_Hanbai_K.cd_literal ");
			// ADD 2013/10/22 QP@30154 okano start
			strSql.append(" LEFT JOIN ma_busho AS M104_hanseki ");
			strSql.append(" ON T310.cd_hanseki = M104_hanseki.cd_kaisha ");
			// ADD 2013/10/22 QP@30154 okano end
			// ADD 2019/09/17 BRC.ida start
			strSql.append(" LEFT JOIN ma_literal AS M302_TNI_SYUMI ");
			strSql.append(" ON  M302_TNI_SYUMI.cd_category = '23' ");
			strSql.append(" AND T110.shomikikan_tani = M302_TNI_SYUMI.cd_literal ");
			// ADD 2019/09/17 BRC.ida end

			//�yQP@00342�z
			strSql.append(" LEFT JOIN ma_user AS M110 ");
			strSql.append(" ON T110.cd_eigyo = M110.id_user ");

			strSql.append(" WHERE ");
			strSql.append("     T310.cd_shain = " +
					toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
			strSql.append(" AND T310.nen = " +
					toString(reqData.getFieldVale(0, 0, "nen")) + " ");
			strSql.append(" AND T310.no_oi = " +
					toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");
			strSql.append(" AND T310.no_eda = " +
					toString(reqData.getFieldVale(0, 0, "no_eda")) + " ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �������Z��{���[ kaisya ]�e�[�u���擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaKaisyaSQL(

			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			// �������Z�F������ЃR���{�{�b�N�X�쐬�p
			//SQL���̍쐬
			strSql.append("SELECT DISTINCT");
			strSql.append("  cd_kaisha AS cd_kaisya");
			strSql.append(" ,nm_kaisha AS nm_kaisya");
			strSql.append(" FROM ma_busho");

			//�yQP@00342�z�H��t���O���P�̂��̂����\��
			strSql.append(" WHERE	flg_kojo = 1 ");

			if (userInfoData.getID_data("170").equals("4")){
				//���H��݂̂̏ꍇ
				strSql.append(" AND ");
				strSql.append(" cd_kaisha = " + userInfoData.getCd_kaisha() + " ");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �������Z��{���[ kojo ]�e�[�u���擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaKojoSQL(

			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_busho AS cd_kojyo");
			strSql.append(" ,nm_busho AS nm_kojyo");
			strSql.append(" FROM ma_busho");

			strSql.append(" WHERE ");
			if (userInfoData.getID_data("170").equals("4")){
				// id_data=4 �̎��A�������Z�ꗗ�Ŏ��H�ꕪ�������o����Ȃ��̂� userInfoData�ł���

				//���H��݂̂̏ꍇ
				strSql.append(" cd_kaisha = " + userInfoData.getCd_kaisha() + " ");
				strSql.append("AND cd_busho = " + userInfoData.getCd_busho() + " ");

			//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
//			}else if(userInfoData.getID_data("170").equals("9")){
//
//				//�S��
//				strSql.append(" cd_kaisha = " + toString(clsCD_Kaisy) + " ");
//
//				//�yQP@00342�z�H��t���O���P�̂��̂����\�� + ������
//				strSql.append(" AND	flg_kojo = 1 ");
//				strSql.append(" OR (cd_kaisha = 1 AND cd_busho=1) ");
			//DEL 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

			}else{
				//�yQP@00342�z�S��
				strSql.append(" cd_kaisha = " + toString(clsCD_Kaisy) + " ");

				//�yQP@00342�z�H��t���O���P�̂��̂����\�� + ������
				strSql.append(" AND	flg_kojo = 1 ");
				//MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 start
				// �������Z�F�����H�ꏉ���\���p�i���Z�f�[�^�̍H��R�[�h���IndexSEL�j
				// id_data=1�A2�A9 �̎��AclsCD_Kaisy�F���Z�f�[�^�̉�ЃR�[�h
//				strSql.append(" OR (cd_kaisha = 1 AND cd_busho=1) ");
				if (clsCD_Kaisy == 1) {
					// ��ЃR�[�h���L���[�s�[�̎��̂݁A��������ǉ�
					strSql.append(" OR (cd_kaisha = 1 AND cd_busho=1) ");
				}
				//MOD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.12 end

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �������Z��{���[ tani ]�e�[�u���擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaTaniSQL(

			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_literal AS kibo_genka_cd_tani");
			strSql.append(" ,nm_literal AS kibo_genka_nm_tani");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = 'K_tani_genka'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �yQP@00342�z�������Z��{���[ �̔����ԁi�ʔNor�X�|�b�g�j ]�e�[�u���擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaTani_Hanbai_T_SQL(

			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_literal AS kibo_genka_cd_tani");
			strSql.append(" ,nm_literal AS kibo_genka_nm_tani");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = 'hanbai_kikan_sentaku'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	/**
	 * �������Z��{���[ �̔����ԁi�����j ]�e�[�u���擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaTani_Hanbai_K_SQL(

			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_literal AS kibo_genka_cd_tani");
			strSql.append(" ,nm_literal AS kibo_genka_nm_tani");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = 'hanbai_kikan_tani'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	/**
	 * �������Z�T���v�����̊�{���e�[�u���擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private StringBuffer createGenkaKihonSubSQL(

			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//SQL���̍쐬
			strSql.append("SELECT  ");
			strSql.append("  T313.genka AS genka ");
			strSql.append(" ,M302_TNI_G.nm_literal AS nm_literal_TNI_G ");
			strSql.append(" ,T313.cd_genka_tani AS cd_genka_tani ");
			strSql.append(" ,T313.baika AS baika ");
			// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
			strSql.append(" ,M302_Buturyo_U.nm_literal AS sotei_buturyo_u ");
			strSql.append(" ,M302_Buturyo_K.nm_literal AS sotei_buturyo_k ");
			strSql.append(" ,T313.buturyo_suti AS buturyo_s ");
			strSql.append(" ,T313.buturyo_seihin AS sotei_buturyo_u_cd ");
			strSql.append(" ,T313.buturyo_kikan AS sotei_buturyo_k_cd ");
			// ADD 2013/9/6 okano�yQP@30151�zNo.30 end
			strSql.append(" ,T313.buturyo AS buturyo ");
			strSql.append(" ,T313.dt_hatubai AS dt_hatubai ");
			strSql.append(" ,M302_Hanbai_T.nm_literal AS kikan_hanbai_ts ");
			strSql.append(" ,M302_Hanbai_K.nm_literal AS kikan_hanbai_k ");
			strSql.append(" ,T313.kikan_hanbai_sen AS kikan_hanbai_t_cd ");
			strSql.append(" ,T313.kikan_hanbai_suti AS kikan_hanbai_suti ");
			strSql.append(" ,T313.kikan_hanbai_tani AS kikan_hanbai_k_cd ");
			strSql.append(" ,T313.uriage_k AS uriage_k ");
			strSql.append(" ,T313.rieki_k AS rieki_k ");
			strSql.append(" ,T313.uriage_h AS uriage_h ");
			strSql.append(" ,T313.rieki_h AS rieki_h ");
			strSql.append(" ,T313.lot AS lot ");
			strSql.append(" ,T331.fg_chusi AS fg_chusi ");
			// ADD 2014/8/7 shima�yQP@30154�zNo.63 start
			strSql.append(" ,T331.fg_koumokuchk ");
			// ADD 2014/8/7 shima�yQP@30154�zNo.63 end
			// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.16 start
			strSql.append(" ,T131.nm_sample ");
			// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.16 end
			strSql.append(" FROM ");
			strSql.append("  tr_shisan_kihonjoho AS T313 ");
			strSql.append(" LEFT JOIN tr_shisaku AS T131 ");
			strSql.append("  ON T313.cd_shain = T131.cd_shain ");
			strSql.append("  AND T313.nen = T131.nen ");
			strSql.append("  AND T313.no_oi = T131.no_oi ");
			strSql.append("  AND T313.seq_shisaku = T131.seq_shisaku ");
			strSql.append(" LEFT JOIN ma_literal AS M302_TNI_G ");
			strSql.append("  ON 'K_tani_genka' = M302_TNI_G.cd_category ");
			strSql.append("  AND T313.cd_genka_tani = M302_TNI_G.cd_literal ");
			strSql.append(" LEFT JOIN ma_literal AS M302_Hanbai_T  ");
			strSql.append("  ON 'hanbai_kikan_sentaku' = M302_Hanbai_T.cd_category ");
			strSql.append("  AND T313.kikan_hanbai_sen = M302_Hanbai_T.cd_literal ");
			strSql.append(" LEFT JOIN ma_literal AS M302_Hanbai_K ");
			strSql.append("  ON 'hanbai_kikan_tani' = M302_Hanbai_K.cd_category ");
			strSql.append("  AND T313.kikan_hanbai_tani = M302_Hanbai_K.cd_literal ");
			strSql.append(" INNER JOIN tr_shisan_shisaku T331 ");
			strSql.append("  ON T331.cd_shain = T313.cd_shain ");
			strSql.append("  AND T331.nen = T313.nen ");
			strSql.append("  AND T331.no_oi = T313.no_oi ");
			strSql.append("  AND T331.seq_shisaku = T313.seq_shisaku  ");
			strSql.append("  AND T331.no_eda = T313.no_eda ");
			// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
			strSql.append(" LEFT JOIN ma_literal AS M302_Buturyo_U  ");
			strSql.append("  ON 'sotei_buturyo_seihin' = M302_Buturyo_U.cd_category ");
			strSql.append("  AND T313.buturyo_seihin = M302_Buturyo_U.cd_literal ");
			strSql.append(" LEFT JOIN ma_literal AS M302_Buturyo_K ");
			strSql.append("  ON 'sotei_buturyo_kikan' = M302_Buturyo_K.cd_category ");
			strSql.append("  AND T313.buturyo_kikan = M302_Buturyo_K.cd_literal ");
			// ADD 2013/9/6 okano�yQP@30151�zNo.30 end

			strSql.append(" WHERE ");
			strSql.append("     T313.cd_shain = " +
					toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
			strSql.append(" AND T313.nen = " +
					toString(reqData.getFieldVale(0, 0, "nen")) + " ");
			strSql.append(" AND T313.no_oi = " +
					toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");
			strSql.append(" AND T313.no_eda = " +
					toString(reqData.getFieldVale(0, 0, "no_eda")) + " ");
			strSql.append(" AND T131.flg_shisanIrai = 1 ");

			strSql.append(" ORDER BY ");
			strSql.append("  T313.cd_shain, T313.nen, T313.no_oi, T131.sort_shisaku ");


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

	// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
	private StringBuffer createGenkaSotei_Buturyo_U_SQL(

			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_literal AS kibo_genka_cd_tani");
			strSql.append(" ,nm_literal AS kibo_genka_nm_tani");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = 'sotei_buturyo_seihin'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}

	private StringBuffer createGenkaSotei_Buturyo_K_SQL(

			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();

		try {

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  cd_literal AS kibo_genka_cd_tani");
			strSql.append(" ,nm_literal AS kibo_genka_nm_tani");
			strSql.append(" FROM ma_literal");
			strSql.append(" WHERE cd_category = 'sotei_buturyo_kikan'");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	// ADD 2013/9/6 okano�yQP@30151�zNo.30 end

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData�i�p�����[�^�[�i�[�j
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �������Z��{���[ kihon ]�e�[�u���p�����[�^�[�i�[
	 *  : �������Z��ʂ�ͯ�ް���������X�|���X�f�[�^�֊i�[����B
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

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "nm_gurupu", toString(items[0],""));
				resTable.addFieldVale(i, "nm_chi-mu", toString(items[1],""));
				resTable.addFieldVale(i, "nm_ikatu", toString(items[2],""));
				resTable.addFieldVale(i, "nm_user", toString(items[3],""));
				resTable.addFieldVale(i, "nm_yoto",  toString(items[4],""));
				resTable.addFieldVale(i, "cd_kaisya",  toString(items[5],""));
				clsCD_Kaisy = toInteger(toString(items[5]));
				resTable.addFieldVale(i, "nm_kaisya", toString(items[6],""));
				resTable.addFieldVale(i, "cd_kojyo",  toString(items[7],""));
				resTable.addFieldVale(i, "nm_jojyo",  toString(items[8],""));
				resTable.addFieldVale(i, "nm_tantoegyo",  toString(items[9],""));
				resTable.addFieldVale(i, "nm_seizohoho",  toString(items[10],""));
				resTable.addFieldVale(i, "nm_jyutenhoho",  toString(items[11],""));
				resTable.addFieldVale(i, "nm_sakinhoho",  toString(items[12],""));
				resTable.addFieldVale(i, "nm_yoki",  toString(items[13],""));
				resTable.addFieldVale(i, "yoryo",  toString(items[14],""));

				//�yQP@00342�z
				resTable.addFieldVale(i, "yoryo_tani",  toString(items[15],""));
				resTable.addFieldVale(i, "irisu",  toString(items[16],""));
				resTable.addFieldVale(i, "nm_nisugata",  toString(items[17],""));
				resTable.addFieldVale(i, "nm_ondo",  toString(items[18],""));
				resTable.addFieldVale(i, "nm_kikan",  toString(items[19],""));
				resTable.addFieldVale(i, "kibo_genka",  toString(items[20],""));
				resTable.addFieldVale(i, "kibo_genka_nm_tani",  toString(items[21],""));
				resTable.addFieldVale(i, "kibo_genka_cd_tani",  toString(items[22],""));
				resTable.addFieldVale(i, "kibo_baika",  toString(items[23],""));
				resTable.addFieldVale(i, "soote_buturyo",  toString(items[24],""));
				resTable.addFieldVale(i, "ziki_hatubai",  toString(items[25],""));
				resTable.addFieldVale(i, "keikaku_uriage",  toString(items[26],""));
				resTable.addFieldVale(i, "keikaku_rieki",  toString(items[27],""));
				resTable.addFieldVale(i, "hanbaigo_uriage",  toString(items[28],""));
				resTable.addFieldVale(i, "hanbaigo_rieki",  toString(items[29],""));
				resTable.addFieldVale(i, "seizo_roto",  toString(items[30],""));
				resTable.addFieldVale(i, "memo_genkashisan",  toString(items[31],""));
				resTable.addFieldVale(i, "memo_genkashisan_eigyo",  toString(items[32],""));
				//����/���ތ���
				resTable.addFieldVale(i, "cd_ketasu",  toString(items[33],""));

				resTable.addFieldVale(i, "kikan_hanbai_t",  toString(items[34],""));
				resTable.addFieldVale(i, "kikan_hanbai_k",  toString(items[35],""));
				resTable.addFieldVale(i, "kikan_hanbai_t_cd",  toString(items[36],""));
				resTable.addFieldVale(i, "kikan_hanbai_k_cd",  toString(items[37],""));
				resTable.addFieldVale(i, "kikan_hanbai_suti",  toString(items[38],""));
				// ADD 2013/10/22 QP@30154 okano start
				resTable.addFieldVale(i, "cd_hanseki",  toString(items[39],""));
				// ADD 2013/10/22 QP@30154 okano end

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �������Z��{���[ kaisya ]�e�[�u���p�����[�^�[�i�[
	 *  : �������Z��ʂ̃T���v��No�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenkaKaisya(

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

				Object[] items = (Object[]) lstGenkaHeader.get(i);


				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_kaisya", toString(items[0],""));
				resTable.addFieldVale(i, "nm_kaisya", toString(items[1],""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}

	/**
	 * �������Z��{���[ kojo ]�e�[�u���p�����[�^�[�i�[
	 *  : �������Z��ʂ̃T���v��No�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenkaKojo(

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

				Object[] items = (Object[]) lstGenkaHeader.get(i);


				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_kojyo", toString(items[0],""));
				resTable.addFieldVale(i, "nm_kojyo", toString(items[1],""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}

	/**
	 * �������Z��{���[ tani ]�e�[�u���p�����[�^�[�i�[
	 *  : �������Z��ʂ̃T���v��No�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenkaTani(

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

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "kibo_genka_cd_tani", toString(items[0],""));
				resTable.addFieldVale(i, "kibo_genka_nm_tani", toString(items[1],""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}

	/**
	 * �yQP@00342�z�������Z��{���[ �̔����ԁi�ʔNor�X�|�b�g�j ]�e�[�u���p�����[�^�[�i�[
	 *  : �������Z��ʂ̃T���v��No�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenka_HanbaiT_Tani(

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

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_literal", toString(items[0],""));
				resTable.addFieldVale(i, "nm_literal", toString(items[1],""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}

	/**
	 * �yQP@00342�z�������Z��{���[ �̔����ԁi�����j ]�e�[�u���p�����[�^�[�i�[
	 *  : �������Z��ʂ̃T���v��No�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenka_HanbaiK_Tani(

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

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_literal", toString(items[0],""));
				resTable.addFieldVale(i, "nm_literal", toString(items[1],""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}

	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	/**
	 * �yQP@30151�z�������Z�T���v�����̊�{���e�[�u���p�����[�^�[�i�[
	 *  : �������Z��ʂ̃T���v��No�̊�{���������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenkaKihonSub(

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

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				// MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//				resTable.addFieldVale(i, "kibo_genka", toString(items[0],""));
//				resTable.addFieldVale(i, "kibo_genka_nm_tani", toString(items[1],""));
//				resTable.addFieldVale(i, "kibo_genka_cd_tani", toString(items[2],""));
//				resTable.addFieldVale(i, "kibo_baika", toString(items[3],""));
//				resTable.addFieldVale(i, "soote_buturyo", toString(items[4],""));
//				resTable.addFieldVale(i, "ziki_hatubai", toString(items[5],""));
//				resTable.addFieldVale(i, "kikan_hanbai_t", toString(items[6],""));
//				resTable.addFieldVale(i, "kikan_hanbai_k", toString(items[7],""));
//				resTable.addFieldVale(i, "kikan_hanbai_t_cd", toString(items[8],""));
//				resTable.addFieldVale(i, "kikan_hanbai_suti", toString(items[9],""));
//				resTable.addFieldVale(i, "kikan_hanbai_k_cd", toString(items[10],""));
//				resTable.addFieldVale(i, "keikaku_uriage", toString(items[11],""));
//				resTable.addFieldVale(i, "keikaku_rieki", toString(items[12],""));
//				resTable.addFieldVale(i, "hanbaigo_uriage", toString(items[13],""));
//				resTable.addFieldVale(i, "hanbaigo_rieki", toString(items[14],""));
//				resTable.addFieldVale(i, "seizo_roto", toString(items[15],""));
//				resTable.addFieldVale(i, "fg_chusi", toString(items[16],""));

				resTable.addFieldVale(i, "kibo_genka", toString(items[0],""));
				resTable.addFieldVale(i, "kibo_genka_nm_tani", toString(items[1],""));
				resTable.addFieldVale(i, "kibo_genka_cd_tani", toString(items[2],""));
				resTable.addFieldVale(i, "kibo_baika", toString(items[3],""));
				resTable.addFieldVale(i, "sotei_buturyo_u", toString(items[4],""));
				resTable.addFieldVale(i, "sotei_buturyo_k", toString(items[5],""));
				resTable.addFieldVale(i, "soote_buturyo_s", toString(items[6],""));
				resTable.addFieldVale(i, "sotei_buturyo_u_cd", toString(items[7],""));
				resTable.addFieldVale(i, "sotei_buturyo_k_cd", toString(items[8],""));
				resTable.addFieldVale(i, "soote_buturyo", toString(items[9],""));
				resTable.addFieldVale(i, "ziki_hatubai", toString(items[10],""));
				resTable.addFieldVale(i, "kikan_hanbai_t", toString(items[11],""));
				resTable.addFieldVale(i, "kikan_hanbai_k", toString(items[12],""));
				resTable.addFieldVale(i, "kikan_hanbai_t_cd", toString(items[13],""));
				resTable.addFieldVale(i, "kikan_hanbai_suti", toString(items[14],""));
				resTable.addFieldVale(i, "kikan_hanbai_k_cd", toString(items[15],""));
				resTable.addFieldVale(i, "keikaku_uriage", toString(items[16],""));
				resTable.addFieldVale(i, "keikaku_rieki", toString(items[17],""));
				resTable.addFieldVale(i, "hanbaigo_uriage", toString(items[18],""));
				resTable.addFieldVale(i, "hanbaigo_rieki", toString(items[19],""));
				resTable.addFieldVale(i, "seizo_roto", toString(items[20],""));
				resTable.addFieldVale(i, "fg_chusi", toString(items[21],""));
				// MOD 2013/9/6 okano�yQP@30151�zNo.30 end
				// ADD 2014/8/7 shima�yQP@30154�zNo.63 start
				resTable.addFieldVale(i, "fg_koumokuchk", toString(items[22],""));
				// ADD 2014/8/7 shima�yQP@30154�zNo.63 end
				// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.16 start
				resTable.addFieldVale(i, "nm_sample", toString(items[23],""));
				// ADD 2015/03/03 TT.Kitazawa�yQP@40812�zNo.16 end
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

	// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
	private void storageGenka_ButuryoU_Tani(

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

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_literal", toString(items[0],""));
				resTable.addFieldVale(i, "nm_literal", toString(items[1],""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}

	private void storageGenka_ButuryoK_Tani(

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

				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_literal", toString(items[0],""));
				resTable.addFieldVale(i, "nm_literal", toString(items[1],""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}

	}
	// ADD 2013/9/6 okano�yQP@30151�zNo.30 end

}
