package jp.co.blueflag.shisaquick.srv.logic_gencho;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseJExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

public class FGEN3670_Logic extends LogicBaseJExcel {

	/**
	 * ������}�X�^Excel�\�𐶐�����
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

		//���X�|���X�f�[�^�i�@�\�j
		RequestResponsKindBean ret = null;
		//�����f�[�^
		List<?> lstRecset = null;
		//�G�N�Z���t�@�C���p�X
		String downLoadPath = "";

		try {
			// DB����
			super.createSearchDB();
			lstRecset = getData(reqData);

			// Excel�t�@�C������
			downLoadPath = makeExcelFile1(lstRecset, reqData);

			//���X�|���X�f�[�^����
			ret = CreateRespons(downLoadPath, reqData);
			ret.setID(reqData.getID());


		} catch (Exception e) {
			em.ThrowException(e, "������}�X�^Excel�\�̐����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//DB�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
		}
		return reqData;

	}


	/**
	 * �Ώۂ̔�����}�X�^�[�f�[�^����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData(RequestResponsKindBean KindBean) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		//��������
		List<?> ret = null;
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();

		try {


			strSql = MakeSQLBuf(KindBean);

			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());


			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");

			}

		} catch (Exception e) {
			em.ThrowException(e, "���ގ�z�f�[�^�ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;

		}
		return ret;

	}

	private StringBuffer MakeSQLBuf(RequestResponsKindBean kindBean) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQL���̍쐬
		String cdhattyu = toString(kindBean.getFieldVale(0, 0, "cdhattyu"));
		String nmhattyu = toString(kindBean.getFieldVale(0, 0, "nmhattyu"));
		StringBuffer ret = new StringBuffer();

		String strCategoryCd = "C_hattyuusaki";
		try {

			//SQL���̍쐬
			ret.append("SELECT");
			ret.append("  RIGHT('000000' + convert(varchar,cd_literal), 6)");//EXCEL�̕\����0�p�f�B���O�Ή�
			ret.append(" ,nm_literal");
			ret.append(" ,no_sort");
			ret.append(" ,cd_2nd_literal");
			ret.append(" ,nm_2nd_literal");
			ret.append(" ,no_2nd_sort");
			ret.append(" ,mail_address");
			ret.append(" ,flg_mishiyo");
			ret.append(" FROM ma_literal");
			ret.append(" WHERE cd_category = '");
			ret.append(strCategoryCd);
			ret.append("'");
			if (cdhattyu != "") {
				ret.append(" AND cd_literal = '");
				ret.append(cdhattyu);
				ret.append("'");
			}
			if (nmhattyu != "") {
				ret.append(" AND cd_2nd_literal = '");
				ret.append(cdhattyu);
				ret.append("'");
			}
			ret.append("ORDER BY");
			ret.append(" flg_mishiyo");
			ret.append(" ,cd_literal");
			ret.append(" ,cd_2nd_literal");

		} catch (Exception e) {
			this.em.ThrowException(e, "���ގ�z�f�[�^�A����SQL�̐����Ɏ��s���܂����B");

		} finally {

		}

		return ret;
	}

	/**
	 * ������}�X�^�[Excel�𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile1(List<?> lstRecset, RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String ret = "";

		try {

			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("�����}�X�^");
			ret = super.ExcelOutput("");
			for (int i = 0; i < lstRecset.size(); i++) {

				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset.get(i);

				String strHattyuusakiCd = toString(items[0]);
				String strHattyuusakiNm = toString(items[1]);
				String strHattyuusakiNmHyoujijun = toString(items[2]);
				String strTantoushaCd = toString(items[3]);
				String strTantousha = toString(items[4]);
				String strTantoushahyoujiNm = toString(items[5]);
				String strMailAddress = toString(items[6]);
				String strMisiyou = toString(items[7]);

				// Excel�ɒl���Z�b�g����
				super.ExcelSetValue("������R�[�h", strHattyuusakiCd);
				super.ExcelSetValue("�����於", strHattyuusakiNm);
				super.ExcelSetValue("������\����", strHattyuusakiNmHyoujijun);
				super.ExcelSetValue("�S���҃R�[�h", strTantoushaCd);
				super.ExcelSetValue("�S����", strTantousha);
				super.ExcelSetValue("�S���ҕ\����", strTantoushahyoujiNm);
				super.ExcelSetValue("���[���A�h���X", strMailAddress);
				super.ExcelSetValue("���g�p", strMisiyou);
			}

		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {
			super.ExcelWrite();
			super.close();
		}
		return ret;
	}

	/**
	 * ���X�|���X�f�[�^�𐶐�����
	 * @param DownLoadPath : �t�@�C���p�X�����t�@�C���i�[��(�_�E�����[�h�p�����[�^)
	 * @return RequestResponsKindBean : ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private RequestResponsKindBean CreateRespons(String DownLoadPath,  RequestResponsKindBean reqData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {

			//�t�@�C���p�X	�����t�@�C���i�[��
			reqData.addFieldVale(0, 0, "URLValue", DownLoadPath);
			//�������ʇ@	������
			reqData.addFieldVale(0, 0, "flg_return", "true");
			//�������ʇA	���b�Z�[�W
			reqData.addFieldVale(0, 0, "msg_error", "");
			//�������ʇB	��������
			reqData.addFieldVale(0, 0, "no_errmsg", "");
			//�������ʇE	���b�Z�[�W�ԍ�
			reqData.addFieldVale(0, 0, "nm_class", "");
			//�������ʇC	�G���[�R�[�h
			reqData.addFieldVale(0, 0, "cd_error", "");
			//�������ʇD	�V�X�e�����b�Z�[�W
			reqData.addFieldVale(0, 0, "msg_system", "");
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return reqData;
	}
}
