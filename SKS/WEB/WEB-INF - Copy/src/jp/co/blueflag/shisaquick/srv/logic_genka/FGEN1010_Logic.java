package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ��ЃR���{�{�b�N�X����
 * @author Nishigawa
 * @create 2009/11/05
 */
public class FGEN1010_Logic extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN1010_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = _userInfoData;
		//�����o�b�t�@
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;
		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();
			resKind.setID("FGEN1010");

			String kaishaCd = "";
			String DataId = "";

			//��ЃR�[�h�̎擾
			kaishaCd = reqData.getFieldVale(0, 0, "cd_kaisya");
			DataId = userInfoData.getID_data("170");
			DataId = userInfoData.getID_data("170");

			// SQL���̍쐬
			strSql.append("SELECT DISTINCT cd_kaisha,nm_kaisha");
			strSql.append(" FROM ma_busho");

			//�yQP@00342�z�H��t���O���P�̂��̂����\��
			strSql.append(" WHERE	flg_kojo = 1 ");

			// 2015/07/08 ADD ���O�C�����[�U�Ɠ�����Ђ��擾 Start
			// ���O�C�����[�U�[�Ɠ�����Ђ̂��̂����擾
			strSql.append(" AND cd_kaisha = " + userInfoData.getCd_kaisha());
			// 2015/07/08 ADD ���O�C�����[�U�Ɠ�����Ђ��擾 End

			strSql.append(" ORDER BY cd_kaisha");

			// SQL�����s
			super.createSearchDB();
			try{
				lstRecset = searchDB.dbSearch(strSql.toString());

			}catch(ExceptionWaning e){

			}

			// ���X�|���X�f�[�^�̌`��
			storageKaisyaCmb(lstRecset, resKind);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			//�Z�b�V�����̃N���[�Y
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
			}
			//�ϐ��̍폜
			strSql = null;
		}
		return resKind;
	}

	private void storageKaisyaCmb(

			List<?> lstGroupCmb
			, RequestResponsKindBean resData
			)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {

			if (lstGroupCmb == null){

				//�������ʇ@	flg_return
				resData.addFieldVale("kaisya", "rec" + "0", "flg_return", "true");
				//�������ʇA	msg_error
				resData.addFieldVale("kaisya", "rec" + "0", "msg_error", "�Y�������Ђ�����܂���");
				//�������ʇB	nm_class
				resData.addFieldVale("kaisya", "rec" + "0", "nm_class", "");
				//�������ʇE	no_errmsg
				resData.addFieldVale("kaisya", "rec" + "0", "no_errmsg", "");
				//�������ʇC	cd_error
				resData.addFieldVale("kaisya", "rec" + "0", "cd_error", "");
				//�������ʇD	msg_system
				resData.addFieldVale("kaisya", "rec" + "0", "msg_system", "");
				//���CD	cd_kaisya
				resData.addFieldVale("kaisya", "rec" + "0", "cd_kaisya", "");
				//��Ж�	nm_kaisya
				resData.addFieldVale("kaisya", "rec" + "0", "nm_kaisya", "");

			}else{

				for (int i = 0; i < lstGroupCmb.size(); i++) {

					//�������ʂ̊i�[
					Object[] items = (Object[]) lstGroupCmb.get(i);

					//�������ʇ@	flg_return
					resData.addFieldVale("kaisya", "rec" + toString(i), "flg_return", "true");
					//�������ʇA	msg_error
					resData.addFieldVale("kaisya", "rec" + toString(i), "msg_error", "");
					//�������ʇB	nm_class
					resData.addFieldVale("kaisya", "rec" + toString(i), "nm_class", "");
					//�������ʇE	no_errmsg
					resData.addFieldVale("kaisya", "rec" + toString(i), "no_errmsg", "");
					//�������ʇC	cd_error
					resData.addFieldVale("kaisya", "rec" + toString(i), "cd_error", "");
					//�������ʇD	msg_system
					resData.addFieldVale("kaisya", "rec" + toString(i), "msg_system", "");
					//���CD	cd_kaisya
					resData.addFieldVale("kaisya", "rec" + toString(i), "cd_kaisya", toString(items[0]));
					//��Ж�	nm_kaisya
					resData.addFieldVale("kaisya", "rec" + toString(i), "nm_kaisya", toString(items[1]));

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

}
