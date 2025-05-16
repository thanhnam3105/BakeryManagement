package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.SearchBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.base.BaseDao.DBCategory;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import java.util.List;

/**
 * QP@10181 No.67
 * �ySA490�z �o�^�i�ҏW�jDB�`�F�b�N
 * @author TT.Nishigawa
 * @since  2011/05/21
 */
public class SA490DataCheck extends DataCheck{

	/**
	 *  �o�^�i�ҏW�j����DB�`�F�b�N�����p�R���X�g���N�^
	 */
	public SA490DataCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �o�^�i���@�R�s�[�j����DB�`�F�b�N
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		//�T���v����
		String errSampleNo = "";

		try {

			//�����f�[�^���[�v
			for(int i = 0; i < reqData.getCntRow("tr_shisaku"); i++){

				//�L�����Z���˗����������ꍇ
				if (toString(reqData.getFieldVale("tr_shisaku", i, "flg_cancel")).equals("1")){

					//����CD-�Ј�ID
					String cd_shain = toString(reqData.getFieldVale("tr_shisaku", i, "cd_shain"));
					//����CD-�N
					String nen = toString(reqData.getFieldVale("tr_shisaku", i, "nen"));
					//����CD-�ǔ�
					String no_oi = toString(reqData.getFieldVale("tr_shisaku", i, "no_oi"));
					//����SEQ
					String seq_shisaku = toString(reqData.getFieldVale("tr_shisaku", i, "seq_shisaku"));

					//�r���`�F�b�N
					checkHaita(reqData, cd_shain, nen, no_oi);

					//�L�����Z�����̉c�ƃX�e�[�^�X�̃`�F�b�N
					checkStatus(reqData, cd_shain, nen, no_oi);

					//�L�����Z�����̎��Z���̓��̓`�F�b�N
					String retNo = checkShisanHi(reqData, cd_shain, nen, no_oi, seq_shisaku);
					if(retNo.length() > 0){
						errSampleNo =  errSampleNo + retNo + "\n";
					}

// 20160616  KPX@1502111_No.5 ADD start
					//�T���v��NO�i���́j
					String nm_sample = toString(reqData.getFieldVale("tr_shisaku", i, "nm_sample"));
					//���ƌ����F�z�������N�̓o�^�`�F�b�N
					chekHaigoLink(reqData, cd_shain, nen, no_oi, seq_shisaku, nm_sample);
// 20160616  KPX@1502111_No.5 ADD end

				}
			}

			//�L�����Z���ΏۂŎ��Z���̓��͂��������ꍇ
			if(errSampleNo.length() > 0){
				em.ThrowException(ExceptionKind.���Exception,"E000406", errSampleNo, "", "");
			}

		}
		catch (Exception e) {
			this.em.ThrowException(e, "");

		}
		finally {
			if (searchBD != null) {
				//�Z�b�V�����̃N���[�Y
				searchBD.Close();
				searchBD = null;
			}
		}

	}

	/**
	 * �L�����Z�����̉c�ƃX�e�[�^�X�̃`�F�b�N
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void checkHaita(
			RequestResponsKindBean reqData
			,String cd_shain
			,String nen
			,String no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql1 = new StringBuffer();
		List<?> lstRecset = null;

		try {

			//DB�Z�b�V�����̐ݒ�
			super.createSearchDB();

			//�X�e�[�^�X�擾�pSQL�쐬
			strSql1.append(" select");
			strSql1.append("	RIGHT('0000000000' + CONVERT(varchar,cd_shain),10) AS cd_shain");
			strSql1.append("	,RIGHT('00' + CONVERT(varchar,nen),2) AS nen");
			strSql1.append("	,RIGHT('000' + CONVERT(varchar,no_oi),3) AS no_oi");
			strSql1.append("	,RIGHT('000' + CONVERT(varchar,no_eda),3) AS no_eda");
			strSql1.append("	,haita_id_user");
			strSql1.append(" from");
			strSql1.append("	tr_shisan_shisakuhin");
			strSql1.append(" where");
			strSql1.append("	cd_shain = " + cd_shain);
			strSql1.append("	and nen = " + nen);
			strSql1.append("	and no_oi = " + no_oi);
			strSql1.append("	and haita_id_user IS NOT NULL");

			//SQL�����s
			lstRecset = searchBD.dbSearch(strSql1.toString());

			//�r�����łȂ��ꍇ
			if (lstRecset.size() == 0){

			}
			//�r�����̏ꍇ
			else{
				em.ThrowException(ExceptionKind.���Exception,"E000407", "", "", "");
			}

		}
		catch (Exception e) {
			this.em.ThrowException(e, "");

		}
		finally {
			//���X�g�̔j��
			removeList(lstRecset);

			//�ϐ��̍폜
			strSql1 = null;
		}

	}

	/**
	 * �L�����Z�����̉c�ƃX�e�[�^�X�̃`�F�b�N
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void checkStatus(
			RequestResponsKindBean reqData
			,String cd_shain
			,String nen
			,String no_oi
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql1 = new StringBuffer();
		List<?> lstRecset = null;

		try {

			//DB�Z�b�V�����̐ݒ�
			super.createSearchDB();

			//�X�e�[�^�X�擾�pSQL�쐬
			strSql1.append(" select");
			strSql1.append("	RIGHT('0000000000' + CONVERT(varchar,cd_shain),10) AS cd_shain");
			strSql1.append("	,RIGHT('00' + CONVERT(varchar,nen),2) AS nen");
			strSql1.append("	,RIGHT('000' + CONVERT(varchar,no_oi),3) AS no_oi");
			strSql1.append("	,RIGHT('000' + CONVERT(varchar,no_eda),3) AS no_eda");
			strSql1.append(" from");
			strSql1.append("	tr_shisan_status");
			strSql1.append(" where");
			strSql1.append("	cd_shain = " + cd_shain);
			strSql1.append("	and nen = " + nen);
			strSql1.append("	and no_oi = " + no_oi);
			strSql1.append("	and st_eigyo >= 2");

			//SQL�����s
			lstRecset = searchBD.dbSearch(strSql1.toString());

			//�c�ƃX�e�[�^�X���Q�ȏ�̂��̂��Ȃ��ꍇ
			if (lstRecset.size() == 0){

			}
			//�c�ƃX�e�[�^�X���Q�ȏ�̂��̂�����ꍇ
			else{
				String errNo = "";

				for (int i = 0; i < lstRecset.size(); i++) {

					Object[] items = (Object[]) lstRecset.get(i);

					//���ʂ����X�|���X�f�[�^�Ɋi�[
					String ShisakuNo = toString(items[0]) + "-" + toString(items[1]) + "-" + toString(items[2]) + "-" + toString(items[3]);
					errNo = errNo + ShisakuNo + "\n";
				}

				em.ThrowException(ExceptionKind.���Exception,"E000405", errNo, "", "");
			}

		}
		catch (Exception e) {
			this.em.ThrowException(e, "");

		}
		finally {
			//���X�g�̔j��
			removeList(lstRecset);

			//�ϐ��̍폜
			strSql1 = null;
		}

	}

	/**
	 * �L�����Z�����̎��Z���̓��̓`�F�b�N
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private String checkShisanHi(
			RequestResponsKindBean reqData
			,String cd_shain
			,String nen
			,String no_oi
			,String seq_shisaku
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql1 = new StringBuffer();
		List<?> lstRecset = null;

		String ret = "";

		try {
			//DB�Z�b�V�����̐ݒ�
			super.createSearchDB();

			//���Z���擾�pSQL�쐬
			strSql1.append(" select distinct");
			strSql1.append(" 	T2.nm_sample");
			strSql1.append(" from");
			strSql1.append(" 	tr_shisan_shisaku AS T1");
			strSql1.append(" 	inner join tr_shisaku AS T2");
			strSql1.append(" 		ON T1.cd_shain = T2.cd_shain");
			strSql1.append(" 		and T1.nen = T2.nen");
			strSql1.append(" 		and T1.no_oi = T2.no_oi");
			strSql1.append(" 		and T1.seq_shisaku = T2.seq_shisaku");
			strSql1.append(" where");
			strSql1.append(" 	T1.cd_shain = " + cd_shain);
			strSql1.append(" 	and T1.nen = " + nen);
			strSql1.append(" 	and T1.no_oi = " + no_oi);
			strSql1.append(" 	and T1.seq_shisaku = " + seq_shisaku);
			strSql1.append(" 	and T1.dt_shisan IS NOT NULL");


			//SQL�����s
			lstRecset = searchBD.dbSearch(strSql1.toString());

			//���Z�������͂���Ă���T���v���񂪂Ȃ��ꍇ
			if (lstRecset.size() == 0){

			}
			//���Z�������͂���Ă���T���v���񂪂���ꍇ
			else{

					//���ʂ����X�|���X�f�[�^�Ɋi�[
					ret = (String)lstRecset.get(0);
			}
		}
		catch (Exception e) {
			this.em.ThrowException(e, "");

		}
		finally {
			//���X�g�̔j��
			removeList(lstRecset);

			//�ϐ��̍폜
			strSql1 = null;
		}

		//�ԋp
		return ret;
	}

// 20160616  KPX@1502111_No.5 ADD start
	/**
	 * �L�����Z�����̔z�������N�o�^�`�F�b�N
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void chekHaigoLink(
			RequestResponsKindBean reqData
			,String cd_shain
			,String nen
			,String no_oi
			,String seq_shisaku
			,String nm_sample
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSql1 = new StringBuffer();
		List<?> lstRecset = null;

		try {

			//DB�Z�b�V�����̐ݒ�
			super.createSearchDB();

			//�X�e�[�^�X�擾�pSQL�쐬
			strSql1.append(" select");
			strSql1.append("	cd_kaisha");
			strSql1.append("	,cd_genryo");
			strSql1.append("	,cd_shain");
			strSql1.append("	,nen");
			strSql1.append("	,no_oi");
			strSql1.append("	,seq_shisaku");
			strSql1.append(" from");
			strSql1.append("	tr_haigo_link");
			strSql1.append(" where");
			strSql1.append("	cd_shain = " + cd_shain);
			strSql1.append("	and nen = " + nen);
			strSql1.append("	and no_oi = " + no_oi);
			strSql1.append("	and seq_shisaku = " + seq_shisaku);

			//SQL�����s
			lstRecset = searchBD.dbSearch(strSql1.toString());

			//���ƌ����F�z�������N�ɓo�^����Ă���ꍇ
			if (lstRecset.size() > 0){

//				Object[] items = (Object[]) lstRecset.get(0);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
//				String strGenryo = "\n  ���CD�F" + toString(items[0]) + "\n  ����CD�F" + toString(items[1])  + "\n  �T���v��No�F" + nm_sample;

				em.ThrowException(ExceptionKind.���Exception,"E000413", "", "", "");
			}

		}
		catch (Exception e) {
			this.em.ThrowException(e, "");

		}
		finally {
			//���X�g�̔j��
			removeList(lstRecset);

			//�ϐ��̍폜
			strSql1 = null;
		}

	}
// 20160616  KPX@1502111_No.5 ADD end

}

