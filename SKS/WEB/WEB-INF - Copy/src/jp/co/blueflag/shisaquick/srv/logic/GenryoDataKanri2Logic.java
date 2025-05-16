package jp.co.blueflag.shisaquick.srv.logic;

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
 *
 * �����f�[�^�Ǘ��ADB����
 *  : �����f�[�^�폜�����̂c�a�ɑ΂���Ɩ����W�b�N�̎���
 *
 * @author TT.k-katayama
 * @since  2009/04/14
 *
 */
public class GenryoDataKanri2Logic extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public GenryoDataKanri2Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �����f�[�^2����Ǘ�
	 * @param reqKind : �@�\���N�G�X�g�f�[�^
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

		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�F���N�G�X�g�f�[�^���A�����敪�𒊏o���A�e�������\�b�h���ďo���B
			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//			String strReqKakuteiCd = reqData.getFieldVale(0, 0, "cd_kakutei");

			String strGenryocd = "";

			if ( strReqShoriKbn.equals("0") ) {
				//0 : �o�^����
				strGenryocd = this.genryoKanri2InsertSQL(reqData,0);

			} else if ( strReqShoriKbn.equals("1") ) {
				//1 : �X�V����
				this.genryoKanri2UpdateSQL(reqData);

//				//�m��R�[�h�����݂���ꍇ�́A�����R�[�h=�m��R�[�h�ɂ��o�^�������s���B
//				if ( !(strReqKakuteiCd.equals(null) || strReqKakuteiCd.equals("")) ) {
//					this.genryoKanri2InsertSQL(reqData,1);
//				}
			} else {
			}

			//�B�F����I�����A�Ǘ����ʃp�����[�^�[�i�[���\�b�h���ďo���A���X�|���X�f�[�^���`������B

			//�@�\ID�̐ݒ�
			resKind.setID(reqData.getID());
			//�e�[�u�����̐ݒ�
			resKind.addTableItem(reqData.getTableID(0));

			this.storageGenryoDataKanri2(strGenryocd,resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^2����Ǘ����������s���܂����B");
		} finally {
			if (execDB != null) {
				//�Z�b�V�����̃N���[�Y
				execDB.Close();
				execDB = null;
			}
		}

		return resKind;
	}

	/**
	 * �������͏��}�X�^�o�^�pSQL�쐬
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param intKakuteiChk : �m��`�F�b�N [0:�����R�[�h�ɂ��o�^, 1:�m��R�[�h�ɂ��o�^]
	 * @return �쐬SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createInsertGenryoSQL(RequestResponsKindBean reqData, int intKakuteiChk) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strSQL_M401 = new StringBuffer();

		try {

			// �@�\���N�G�X�g�f�[�^�̎擾
			String strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			String strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			String strReqSakusanRitu = reqData.getFieldVale(0, 0, "ritu_sakusan");
			String strReqShokuenRitu = reqData.getFieldVale(0, 0, "ritu_shokuen");
			String strReqSousanRitu = reqData.getFieldVale(0, 0, "ritu_sousan");
			String strReqAburaRitu = reqData.getFieldVale(0, 0, "ritu_abura");
			String strReqHyojian = reqData.getFieldVale(0, 0, "hyojian");
			String strReqTenkabutu = reqData.getFieldVale(0, 0, "tenkabutu");
			String strReqMemo = reqData.getFieldVale(0, 0, "memo");
			String strReqEiyoNo1 = reqData.getFieldVale(0, 0, "no_eiyo1");
			String strReqEiyoNo2 = reqData.getFieldVale(0, 0, "no_eiyo2");
			String strReqEiyoNo3 = reqData.getFieldVale(0, 0, "no_eiyo3");
			String strReqEiyoNo4 = reqData.getFieldVale(0, 0, "no_eiyo4");
			String strReqEiyoNo5 = reqData.getFieldVale(0, 0, "no_eiyo5");
			String strReqWariai1 = reqData.getFieldVale(0, 0, "wariai1");
			String strReqWariai2 = reqData.getFieldVale(0, 0, "wariai2");
			String strReqWariai3 = reqData.getFieldVale(0, 0, "wariai3");
			String strReqWariai4 = reqData.getFieldVale(0, 0, "wariai4");
			String strReqWariai5 = reqData.getFieldVale(0, 0, "wariai5");
			String strReqHaishiKbn = reqData.getFieldVale(0, 0, "kbn_haishi");
			String strReqKakuteiCd = reqData.getFieldVale(0, 0, "cd_kakutei");
			String strReqKakuninId = reqData.getFieldVale(0, 0, "id_kakunin");
			String strReqKakuninDt = reqData.getFieldVale(0, 0, "dt_kakunin");
			String strReqTorokuId = reqData.getFieldVale(0, 0, "id_toroku");
			String strReqTorokuDt = reqData.getFieldVale(0, 0, "dt_toroku");
// ADD start 20121005 QP@20505 No.24
			String strReqMsgRitu = "";
// MOD start 20121126 QP@20505 �ۑ�No.10
//			try{
//				strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
//			}catch(Exception exc){
//			}
			strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
// MOD end 20121126 QP@20505 �ۑ�No.10
// ADD end 20121005 QP@20505 No.24

			//�p�����[�^�󔒎��̏���
			if ( strReqSakusanRitu.equals("") ) {
				strReqSakusanRitu = "NULL";
			}
			if ( strReqShokuenRitu.equals("") ) {
				strReqShokuenRitu = "NULL";
			}
			if ( strReqSousanRitu.equals("") ) {
				strReqSousanRitu = "NULL";
			}
			if ( strReqAburaRitu.equals("") ) {
				strReqAburaRitu = "NULL";
			}
// ADD start 20121005 QP@20505 No.24
// MOD start 20121126 QP@20505 �ۑ�No.10
//			if ( "".equals(strReqMsgRitu ) ) {
			if ( strReqMsgRitu.equals("") ) {
// MOD end 20121126 QP@20505 �ۑ�No.10
				strReqMsgRitu = "NULL";
			}
// ADD end 20121005 QP@20505 No.24
			if ( strReqHaishiKbn.equals("") ) {
				strReqHaishiKbn = "0";
			}
			if ( strReqKakuninId.equals("") ) {
				strReqKakuninId = "NULL";
			}

// MOD start 20190712 KPX@1900110
			//M401 �������͏��}�X�^�@�o�^�pSQL
//			strSQL_M401.append(" INSERT INTO ma_genryo VALUES ( ");
			strSQL_M401.append(" INSERT INTO ma_genryo");
			strSQL_M401.append(" ( ");
			strSQL_M401.append(" cd_kaisha ");
			strSQL_M401.append(" ,cd_genryo ");
			strSQL_M401.append(" ,ritu_sakusan ");
			strSQL_M401.append(" ,ritu_shokuen ");
			strSQL_M401.append(" ,ritu_sousan ");
			strSQL_M401.append(" ,ritu_abura ");
			strSQL_M401.append(" ,hyojian ");
			strSQL_M401.append(" ,tenkabutu ");
			strSQL_M401.append(" ,memo ");
			strSQL_M401.append(" ,no_eiyo1 ");
			strSQL_M401.append(" ,no_eiyo2 ");
			strSQL_M401.append(" ,no_eiyo3 ");
			strSQL_M401.append(" ,no_eiyo4 ");
			strSQL_M401.append(" ,no_eiyo5 ");
			strSQL_M401.append(" ,wariai1 ");
			strSQL_M401.append(" ,wariai2 ");
			strSQL_M401.append(" ,wariai3 ");
			strSQL_M401.append(" ,wariai4 ");
			strSQL_M401.append(" ,wariai5 ");
			strSQL_M401.append(" ,nisugata_hyoji ");
			strSQL_M401.append(" ,rank_ibutsu ");
			strSQL_M401.append(" ,shiyokahi_genryo ");
			strSQL_M401.append(" ,shiyokahi_riyu ");
			strSQL_M401.append(" ,shiyokahi_cd_genryo_daitai ");
			strSQL_M401.append(" ,shiyokahi_nm_genryo_daitai ");
			strSQL_M401.append(" ,trouble_joho ");
			strSQL_M401.append(" ,trouble_gaiyo ");
			strSQL_M401.append(" ,trouble_naiyo_shosai ");
			strSQL_M401.append(" ,sakkin_chomieki_yohi ");
			strSQL_M401.append(" ,sakkin_chomieki_joken ");
			strSQL_M401.append(" ,NB_genteigenryo ");
			strSQL_M401.append(" ,NB_joken ");
			strSQL_M401.append(" ,NB_riyu ");
			strSQL_M401.append(" ,kasseikoso ");
			strSQL_M401.append(" ,gosei_tenkabutu ");
			strSQL_M401.append(" ,biko ");
			strSQL_M401.append(" ,kbn_haishi ");
			strSQL_M401.append(" ,cd_kakutei ");
			strSQL_M401.append(" ,id_kakunin ");
			strSQL_M401.append(" ,dt_kakunin ");
			strSQL_M401.append(" ,id_toroku ");
			strSQL_M401.append(" ,dt_toroku ");
			strSQL_M401.append(" ,id_koshin ");
			strSQL_M401.append(" ,dt_koshin ");
			strSQL_M401.append(" ,ritu_msg ");
			strSQL_M401.append(" ) ");
			strSQL_M401.append(" VALUES ( ");
// MOD end 20190712 KPX@1900110

			strSQL_M401.append(" " + strReqKaishaCd);
			if ( intKakuteiChk == 0 ) {
				strSQL_M401.append(" ,'" + strReqGenryoCd + "'");
			} else {
				strSQL_M401.append(" ,'" + strReqKakuteiCd + "'");
			}
			strSQL_M401.append(" ," + strReqSakusanRitu);
			strSQL_M401.append(" ," + strReqShokuenRitu);
			strSQL_M401.append(" ," + strReqSousanRitu);
			strSQL_M401.append(" ," + strReqAburaRitu);
			strSQL_M401.append(" ,'" + strReqHyojian + "'");
			strSQL_M401.append(" ,'" + strReqTenkabutu + "'");
			strSQL_M401.append(" ,'" + strReqMemo + "'");
			strSQL_M401.append(" ,'" + strReqEiyoNo1 + "'");
			strSQL_M401.append(" ,'" + strReqEiyoNo2 + "'");
			strSQL_M401.append(" ,'" + strReqEiyoNo3 + "'");
			strSQL_M401.append(" ,'" + strReqEiyoNo4 + "'");
			strSQL_M401.append(" ,'" + strReqEiyoNo5 + "'");
			strSQL_M401.append(" ,'" + strReqWariai1 + "'");
			strSQL_M401.append(" ,'" + strReqWariai2 + "'");
			strSQL_M401.append(" ,'" + strReqWariai3 + "'");
			strSQL_M401.append(" ,'" + strReqWariai4 + "'");
			strSQL_M401.append(" ,'" + strReqWariai5 + "'");
// ADD start 20190712 KPX@1900110
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
			strSQL_M401.append(" ,NULL");
// ADD end 20190712 KPX@1900110
			strSQL_M401.append(" ," + strReqHaishiKbn);
			if ( intKakuteiChk == 0 ) {
				strSQL_M401.append(" ,'" + strReqKakuteiCd + "'");
			} else {
				strSQL_M401.append(" ,''");
			}
			strSQL_M401.append(" ," + strReqKakuninId);
			if ( !strReqKakuninDt.equals("") ) {
				strSQL_M401.append(" ,'" + strReqKakuninDt + "'");
			} else {
				strSQL_M401.append(" ,NULL");
			}
			strSQL_M401.append(" ," + strReqTorokuId);
			strSQL_M401.append(" ,'" + strReqTorokuDt + "'");
			strSQL_M401.append(" ," + strReqTorokuId);
			strSQL_M401.append(" ,'" + strReqTorokuDt + "'");
// ADD start 20121005 QP@20505 No.24
			strSQL_M401.append(" ," + strReqMsgRitu);
// ADD end 20121005 QP@20505 No.24
			strSQL_M401.append("  ) ");

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^2�o�^(M401)SQL�쐬���������s���܂����B");
		} finally {
		}

		return strSQL_M401;
	}

	/**
	 * �����}�X�^�o�^�pSQL�쐬
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param intKakuteiChk : �m��`�F�b�N [0:�����R�[�h�ɂ��o�^, 1:�m��R�[�h�ɂ��o�^]
	 * @return �쐬SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createInsertGenryokojoSQL(RequestResponsKindBean reqData, int intKakuteiChk) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strSQL_M402 = new StringBuffer();

		try {

			// �@�\���N�G�X�g�f�[�^�̎擾
			String strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			String strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			String strReqGenryoNm = reqData.getFieldVale(0, 0, "nm_genryo");
			String strReqSakusanRitu = reqData.getFieldVale(0, 0, "ritu_sakusan");
			String strReqShokuenRitu = reqData.getFieldVale(0, 0, "ritu_shokuen");
			String strReqSousanRitu = reqData.getFieldVale(0, 0, "ritu_sousan");
			String strReqAburaRitu = reqData.getFieldVale(0, 0, "ritu_abura");
			String strReqHaishiKbn = reqData.getFieldVale(0, 0, "kbn_haishi");
			String strReqKakuninId = reqData.getFieldVale(0, 0, "id_kakunin");
			String strReqKakuteiCd = reqData.getFieldVale(0, 0, "cd_kakutei");
			String strReqTorokuId = reqData.getFieldVale(0, 0, "id_toroku");
			String strReqTorokuDt = reqData.getFieldVale(0, 0, "dt_toroku");
// ADD start 20121005 QP@20505 No.24
			String strReqMsgRitu = "";
// MOD start 20121126 QP@20505 �ۑ�No.10
//			try{
//				strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
//			}catch(Exception e){
//			}
			strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
// MOD end 20121126 QP@20505 �ۑ�No.10
// ADD end 20121005 QP@20505 No.24

			//�p�����[�^�󔒎��̏���
			if ( strReqSakusanRitu.equals("") ) {
				strReqSakusanRitu = "NULL";
			}
			if ( strReqShokuenRitu.equals("") ) {
				strReqShokuenRitu = "NULL";
			}
			if ( strReqSousanRitu.equals("") ) {
				strReqSousanRitu = "NULL";
			}
			if ( strReqAburaRitu.equals("") ) {
				strReqAburaRitu = "NULL";
			}
// ADD start 20121005 QP@20505 No.24
// MOD start 20121126 QP@20505 �ۑ�No.10
//			if ( "".equals(strReqMsgRitu) ) {
			if ( strReqMsgRitu.equals("") ) {
// MOD end 20121126 QP@20505 �ۑ�No.10
				strReqMsgRitu = "NULL";
			}
// ADD end 20121005 QP@20505 No.24
			if ( strReqHaishiKbn.equals("") ) {
				strReqHaishiKbn = "0";
			}
			if ( strReqKakuninId.equals("") ) {
				strReqKakuninId = "NULL";
			}
// MOD start 20190712 KPX@1900110
			//M402 �����}�X�^�@�o�^�pSQL
//			strSQL_M402.append("INSERT INTO ma_genryokojo VALUES ( ");
			strSQL_M402.append("INSERT INTO ma_genryokojo ");
			strSQL_M402.append(" ( ");
			strSQL_M402.append(" cd_kaisha ");
			strSQL_M402.append(" ,cd_genryo ");
			strSQL_M402.append(" ,cd_busho ");
			strSQL_M402.append(" ,nm_genryo ");
			strSQL_M402.append(" ,tanka ");
			strSQL_M402.append(" ,budomari ");
			strSQL_M402.append(" ,nisugata ");
			strSQL_M402.append(" ,kikakusho ");
			strSQL_M402.append(" ,dt_konyu ");
			strSQL_M402.append(" ,id_toroku ");
			strSQL_M402.append(" ,dt_toroku ");
			strSQL_M402.append(" ,id_koshin ");
			strSQL_M402.append(" ,dt_koshin ");
			strSQL_M402.append(" ,flg_shiyo ");
			strSQL_M402.append(" ,flg_mishiyo ");
			strSQL_M402.append(" ) ");
			strSQL_M402.append(" VALUES ( ");
// MOD end 20190712 KPX@1900110
			strSQL_M402.append("  " + strReqKaishaCd);
			if ( intKakuteiChk == 0 ) {
				strSQL_M402.append(" ,'" + strReqGenryoCd +  "'");
			} else {
				strSQL_M402.append(" ,'" + strReqKakuteiCd +  "'");
			}
			strSQL_M402.append(" ," + ConstManager.getConstValue(Category.�ݒ�, "SHINKIGENRYO_BUSHOCD"));
			strSQL_M402.append(" ,'" + strReqGenryoNm +  "'");
			strSQL_M402.append(" ,NULL");
			strSQL_M402.append(" ,NULL");
			strSQL_M402.append(" ,NULL");
			strSQL_M402.append(" ,NULL");
			strSQL_M402.append(" ,NULL");
			strSQL_M402.append(" ," + strReqTorokuId);
			strSQL_M402.append(" ,'" + strReqTorokuDt + "'");
			strSQL_M402.append(" ," + strReqTorokuId);
			strSQL_M402.append(" ,'" + strReqTorokuDt + "'");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			strSQL_M402.append(" ,0");			//�g�p����
			strSQL_M402.append(" ,0");			//���g�p
//add end --------------------------------------------------------------------------------------
			// ADD start 20121005 QP@20505 No.24
			// ADD end 20121005 QP@20505 No.24
			strSQL_M402.append(")");

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^2�o�^(M402)SQL�쐬���������s���܂����B");
		} finally {
		}

		return strSQL_M402;
	}

	/**
	 * �����f�[�^2�o�^SQL�쐬
	 *  : �o�^S�pSQL���쐬���A�������͏��}�X�^�Ƀp�����[�^��o�^����
	 *   �� �Ώۃe�[�u�� : ma_genryo, ma_genryokojo
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @param intKakuteiChk : �m��`�F�b�N [0:�����R�[�h�ɂ��o�^, 1:�m��R�[�h�ɂ��o�^]
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String genryoKanri2InsertSQL(RequestResponsKindBean reqData, int intKakuteiChk)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL_M401 = null;
		StringBuffer strSQL_M402 = null;
		String strRetNewCode = "";

		try {

//2009/09/29 TT.A.ISONO ADD START [�V�K����CD�͓o�^���ɍ̔Ԃ���悤�ύX����B]

//			//�@�F�����f�[�^�o�^�pSQL�쐬�������Ăяo���ASQL���擾����B
//			strSQL_M401 = createInsertGenryoSQL(reqData, intKakuteiChk);
//			strSQL_M402 = createInsertGenryokojoSQL(reqData, intKakuteiChk);
//
//			//�A�F�f�[�^�x�[�X�Ǘ���p���A�����f�[�^�̍폜���s���B
//			super.createExecDB();								//DB�X�V�̐���
//			this.execDB.BeginTran();							//�g�����U�N�V�����J�n

			//�V�K�����̺��ނ��̔Ԃ���
			StringBuffer strSQL = new StringBuffer();
			StringBuffer strUpdSQL = new StringBuffer();
			List<?> lstSearchAry = null;

			//�̔ԃ}�X�^.�L�[1 = �����R�[�h
			//�̔ԃ}�X�^.�L�[2 = N
			strSQL.append(" SELECT MAX(M601.no_seq)+1 AS no_seq_max ");
			strSQL.append(" FROM ma_saiban M601 WITH(UPDLOCK, ROWLOCK)");
			strSQL.append(" WHERE M601.key1='�����R�[�h' AND M601.key2='N' ");

			try{

				//�A�F�쐬����SQL��p���āA�����������s���A�V�K���s�R�[�h���擾����B
				super.createSearchDB();
				searchDB.BeginTran();
				lstSearchAry = this.searchDB.dbSearch(strSQL.toString());

				//�V�K���s�R�[�h��1���i�[����
				if ( lstSearchAry.size() >= 0 ) {
					//�V�K���s�R�[�h�@�F�@�̔ԃ}�X�^.�̔Ԃ̍ő�l+1
					strRetNewCode = lstSearchAry.get(0).toString();

				} else {
					//�������ʂ����݂��Ȃ������ꍇ
					strRetNewCode = "1";

				}

				//���[�U��� : ���[�UID
				String strUserId = "";
				//���[�U���̃��[�UID���擾
				strUserId = userInfoData.getId_user();

				//�B�F���L������p���āA�̔ԃ}�X�^�ima_saiban�j.�̔Ԃ̎����̔ԗpSQL���쐬����B

				//�̔ԃ}�X�^.�L�[1 = �����R�[�h
				//�̔ԃ}�X�^.�L�[2 = N
				strUpdSQL.append(" UPDATE ma_saiban ");
				strUpdSQL.append("   SET no_seq =" + strRetNewCode);
				strUpdSQL.append("      ,id_koshin =" + strUserId);
				strUpdSQL.append("      ,dt_koshin = GETDATE() ");
				strUpdSQL.append(" WHERE key1='�����R�[�h' AND key2='N' ");

				//�C�F�쐬����SQL��p���āA�X�V�������s���B
				super.createExecDB();								//DB�X�V�̐���
				execDB.setSession(searchDB.getSession());
				this.execDB.execSQL(strUpdSQL.toString());		//SQL���s

				//���ނ̍̔�
				strRetNewCode = "000000" + strRetNewCode;
				strRetNewCode = "N" +
				strRetNewCode.substring(strRetNewCode.length()-5,strRetNewCode.length()
						);

				//���N�G�X�g�f�[�^�ɍ̔Ԃ������ނ𔽉f����
				reqData.setFieldVale(0, 0, "cd_genryo", strRetNewCode);

				//�@�F�����f�[�^�o�^�pSQL�쐬�������Ăяo���ASQL���擾����B
				strSQL_M401 = createInsertGenryoSQL(reqData, intKakuteiChk);
				strSQL_M402 = createInsertGenryokojoSQL(reqData, intKakuteiChk);

//2009/09/29 TT.A.ISONO ADD END   [�V�K����CD�͓o�^���ɍ̔Ԃ���悤�ύX����B]

//			try{
				//M401 �������͏��}�X�^�@�o�^�pSQL���s
				this.execDB.execSQL(strSQL_M401.toString());
				//M402 �����}�X�^�@�o�^�pSQL���s
				this.execDB.execSQL(strSQL_M402.toString());

				//�R�~�b�g�@���g�����U�N�V������searchDB�ŊJ�n�iBeginTran�j���Ă���̂ŁA�R�~�b�g��searchDB�ōs���܂��B
				this.searchDB.Commit();

			}catch(Exception e){
				//���[���o�b�N�@���g�����U�N�V������searchDB�ŊJ�n�iBeginTran�j���Ă���̂ŁA���[���o�b�N��searchDB�ōs���܂��B
				this.searchDB.Rollback();

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^2�o�^SQL�쐬���������s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSQL_M401 = null;
			strSQL_M402 = null;
		}


		//�̔ԃR�[�h�ԋp
		return strRetNewCode;

	}

	/**
	 * �����f�[�^2�X�VSQL�쐬
	 *  : �X�V�pSQL���쐬���A�����}�X�^���X�V����
	 *   �� �Ώۃe�[�u�� : ma_genryo, ma_genryokojo
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void genryoKanri2UpdateSQL(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL_M401 = new StringBuffer();
		StringBuffer strSQL_M402 = new StringBuffer();

		try {

			//�@�F���N�G�X�g�f�[�^���A�����f�[�^�X�V���s���ׂ�SQL���쐬����B

			// �@�\���N�G�X�g�f�[�^�̎擾
			String strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			String strReqGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			String strReqGenryoNm = reqData.getFieldVale(0, 0, "nm_genryo");
			String strReqSakusanRitu = reqData.getFieldVale(0, 0, "ritu_sakusan");
			String strReqShokuenRitu = reqData.getFieldVale(0, 0, "ritu_shokuen");
			String strReqSousanRitu = reqData.getFieldVale(0, 0, "ritu_sousan");
			String strReqAburaRitu = reqData.getFieldVale(0, 0, "ritu_abura");
			String strReqHyojian = reqData.getFieldVale(0, 0, "hyojian");
			String strReqTenkabutu = reqData.getFieldVale(0, 0, "tenkabutu");
			String strReqMemo = reqData.getFieldVale(0, 0, "memo");
			String strReqEiyoNo1 = reqData.getFieldVale(0, 0, "no_eiyo1");
			String strReqEiyoNo2 = reqData.getFieldVale(0, 0, "no_eiyo2");
			String strReqEiyoNo3 = reqData.getFieldVale(0, 0, "no_eiyo3");
			String strReqEiyoNo4 = reqData.getFieldVale(0, 0, "no_eiyo4");
			String strReqEiyoNo5 = reqData.getFieldVale(0, 0, "no_eiyo5");
			String strReqWariai1 = reqData.getFieldVale(0, 0, "wariai1");
			String strReqWariai2 = reqData.getFieldVale(0, 0, "wariai2");
			String strReqWariai3 = reqData.getFieldVale(0, 0, "wariai3");
			String strReqWariai4 = reqData.getFieldVale(0, 0, "wariai4");
			String strReqWariai5 = reqData.getFieldVale(0, 0, "wariai5");
			String strReqHaishiKbn = reqData.getFieldVale(0, 0, "kbn_haishi");
			String strReqKakuteiCd = reqData.getFieldVale(0, 0, "cd_kakutei");
			String strReqKakuninId = reqData.getFieldVale(0, 0, "id_kakunin");
			String strReqKakuninDt = reqData.getFieldVale(0, 0, "dt_kakunin");
//			String strReqKakuninKbn = reqData.getFieldVale(0, 0, "kbn_kakunin");
//			String strReqTorokuId = reqData.getFieldVale(0, 0, "id_toroku");
//			String strReqTorokuDt = reqData.getFieldVale(0, 0, "dt_toroku");
			String strFgHenkou = reqData.getFieldVale(0, 0, "fg_henkou");
			// ADD start 20121005 QP@20505 No.24
			String strReqMsgRitu = null;
// MOD start 20121126 QP@20505 �ۑ�No.10
//			//���͒l���͉�ʂ���̍X�V�̏ꍇMSG�͍X�V���Ȃ�
//			try{
//				strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
//			}catch(Exception exc){
//			}
			strReqMsgRitu = reqData.getFieldVale(0, 0, "ritu_msg");
// MOD end 20121126 QP@20505 �ۑ�No.10
			// ADD end 20121005 QP@20505 No.24

			//�p�����[�^�󔒎��̏���
			if ( strReqSakusanRitu.equals("") ) {
				strReqSakusanRitu = "NULL";
			}
			if ( strReqShokuenRitu.equals("") ) {
				strReqShokuenRitu = "NULL";
			}
			if ( strReqSousanRitu.equals("") ) {
				strReqSousanRitu = "NULL";
			}
			if ( strReqAburaRitu.equals("") ) {
				strReqAburaRitu = "NULL";
			}
// ADD start 20121005 QP@20505 No.24
// MOD start 20121126 QP@20505 �ۑ�No.10
			//���͒l���͉�ʂ���̍X�V�̏ꍇMSG�͍X�V���Ȃ�
//			if( "".equals(strReqMsgRitu) ) {
			if( strReqMsgRitu.equals("") ) {
// MOD end 20121126 QP@20505 �ۑ�No.10
				strReqMsgRitu = "NULL";
			}
// ADD end 20121005 QP@20505 No.24
			if ( strReqHaishiKbn.equals("") ) {
				strReqHaishiKbn = "0";
			}
//			if ( strReqKakuteiCd.equals("") ) {
//				strReqKakuteiCd = "NULL";
//			}
			if ( strReqKakuninId.equals("") ) {
				strReqKakuninId = "NULL";
			}

			//SQL�쐬

			//M401 �������͏��}�X�^�@�X�V�pSQL
			strSQL_M401.append("UPDATE ma_genryo SET ");
			strSQL_M401.append("       ritu_sakusan = " + strReqSakusanRitu);
			strSQL_M401.append("      ,ritu_shokuen = " + strReqShokuenRitu);
			strSQL_M401.append("      ,ritu_sousan = " + strReqSousanRitu);
			strSQL_M401.append("      ,ritu_abura = " + strReqAburaRitu);
			strSQL_M401.append("      ,hyojian = '" + strReqHyojian + "'");
			strSQL_M401.append("      ,tenkabutu = '" + strReqTenkabutu + "'");
			strSQL_M401.append("      ,memo = '" + strReqMemo + "'");
			strSQL_M401.append("      ,no_eiyo1 = '" + strReqEiyoNo1 + "'");
			strSQL_M401.append("      ,no_eiyo2 = '" + strReqEiyoNo2 + "'");
			strSQL_M401.append("      ,no_eiyo3 = '" + strReqEiyoNo3 + "'");
			strSQL_M401.append("      ,no_eiyo4 = '" + strReqEiyoNo4 + "'");
			strSQL_M401.append("      ,no_eiyo5 = '" + strReqEiyoNo5 + "'");
			strSQL_M401.append("      ,wariai1 = '" + strReqWariai1 + "'");
			strSQL_M401.append("      ,wariai2 = '" + strReqWariai2 + "'");
			strSQL_M401.append("      ,wariai3 = '" + strReqWariai3 + "'");
			strSQL_M401.append("      ,wariai4 = '" + strReqWariai4 + "'");
			strSQL_M401.append("      ,wariai5 = '" + strReqWariai5 + "'");
			strSQL_M401.append("      ,kbn_haishi = " + strReqHaishiKbn);
			strSQL_M401.append("      ,cd_kakutei = '" + strReqKakuteiCd + "'");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
//			strSQL_M401.append("      ,id_kakunin = " + strReqKakuninId);
			//�m�F���@0:NULL�i�[, 1:���[�U���i�[
			if ( !strReqKakuninDt.equals("") ) {
//				strSQL_M401.append("      ,dt_kakunin = '" + strReqKakuninDt + "'");
				strSQL_M401.append("      ,id_kakunin = " + userInfoData.getId_user());
				strSQL_M401.append("      ,dt_kakunin = GETDATE()");
				if(strFgHenkou.equals("true")){
					strSQL_M401.append("     ,id_koshin = " + userInfoData.getId_user());
					strSQL_M401.append("     ,dt_koshin = GETDATE()");
				}

			} else {
				strSQL_M401.append("      ,id_kakunin = NULL");
				strSQL_M401.append("      ,dt_kakunin = NULL");
				strSQL_M401.append("     ,id_koshin = " + userInfoData.getId_user());
				strSQL_M401.append("     ,dt_koshin = GETDATE()");
			}
// ADD start 20121005 QP@20505 No.24
// MOD start 20121126 QP@20505 �ۑ�No.10
//			//���͒l���͉�ʂ���̍X�V�̏ꍇMSG�͍X�V���Ȃ�
//			if(strReqMsgRitu != null){
//				strSQL_M401.append("      ,ritu_msg = " + strReqMsgRitu);
//			}
			strSQL_M401.append("      ,ritu_msg = " + strReqMsgRitu);
// MOD end 20121126 QP@20505 �ۑ�No.10
// ADD end 20121005 QP@20505 No.24
//add end --------------------------------------------------------------------------------------
//			strSQL_M401.append("      ,id_koshin = " + strReqTorokuId);
//			strSQL_M401.append("      ,dt_koshin = '" + strReqTorokuDt + "'");
//			strSQL_M401.append("     ,id_koshin = " + userInfoData.getId_user());
//			strSQL_M401.append("     ,dt_koshin = GETDATE()");
			strSQL_M401.append(" WHERE cd_kaisha = " + strReqKaishaCd);
			strSQL_M401.append("  AND  cd_genryo = '" + strReqGenryoCd + "'");

			//M402 �����}�X�^�@�X�V�pSQL
			strSQL_M402.append("UPDATE ma_genryokojo SET");
			strSQL_M402.append(" nm_genryo = '" + strReqGenryoNm + "'");
//			strSQL_M402.append(" ,id_koshin = " + strReqTorokuId);
//			strSQL_M402.append(" ,dt_koshin = '" + strReqTorokuDt + "'");
			strSQL_M402.append(" ,id_koshin = " + userInfoData.getId_user());
			strSQL_M402.append(" ,dt_koshin = GETDATE()");
			strSQL_M402.append(" WHERE cd_kaisha = " + strReqKaishaCd);
			strSQL_M402.append("  AND  cd_busho = " + ConstManager.getConstValue(Category.�ݒ�, "SHINKIGENRYO_BUSHOCD"));
			strSQL_M402.append("  AND  cd_genryo = '" + strReqGenryoCd + "'");

			//�A�F�f�[�^�x�[�X�Ǘ���p���A�����f�[�^�̍폜���s���B
			super.createExecDB();								//DB�X�V�̐���
			this.execDB.BeginTran();							//�g�����U�N�V�����J�n

			try{
				//M401 �������͏��}�X�^�@�X�V�pSQL���s
				this.execDB.execSQL(strSQL_M401.toString());
				//M402 �����}�X�^�@�X�V�pSQL���s
				this.execDB.execSQL(strSQL_M402.toString());

				this.execDB.Commit();							//�R�~�b�g

			}catch(Exception e){
				this.execDB.Rollback();							//���[���o�b�N
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^2�X�VSQL�쐬���������s���܂����B");
		} finally {
			//�ϐ��̍폜
			strSQL_M401 = null;
			strSQL_M402 = null;
		}

	}

	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : �����f�[�^2�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageGenryoDataKanri2(String genryo, RequestResponsTableBean resTable)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");

			resTable.addFieldVale(0, "cd_genryo", genryo);

		} catch (Exception e) {
			this.em.ThrowException(e, "�Ǘ����ʃp�����[�^�[�i�[���������s���܂����B");

		} finally {

		}

	}

}
