package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseCSV;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * �ގ��i�����f�[�^CSV�𐶐�����
 * @author Nishigawa
 * @since  2010/2/15
 */
public class FGEN1050_Logic extends LogicBaseCSV {
	
	/**
	 * �R���X�g���N�^
	 */
	public FGEN1050_Logic() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
	}
	
	/**
	 * ���e�����f�[�^CSV�𐶐�����
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
		//CSV�t�@�C���o�͐�p�X
		String strFilePath = "";
		
		try {
			
			//���N�G�X�g�f�[�^���K���CSV�t�H�[�}�b�g�ɕϊ�
			lstRecset = makeCsvFile(reqData);
			
			//CSV�t�@�C������
			strFilePath = CSVOutput("ruizi", lstRecset);

			//���X�|���X�f�[�^����
			ret = CreateRespons(strFilePath);
			
		} catch (Exception e) {
			em.ThrowException(e, "�J�e�S���f�[�^CSV�̐����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//DB�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

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
	private RequestResponsKindBean CreateRespons(String DownLoadPath) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsKindBean ret = null;
		
		try {
			//���X�|���X�𐶐�����
			ret = new RequestResponsKindBean();
			//�@�\ID��ݒu����
			ret.setID("FGEN1050");
			
			//�t�@�C���p�X	�����t�@�C���i�[��
			ret.addFieldVale(0, 0, "URLValue", DownLoadPath);
			//�������ʇ@	������
			ret.addFieldVale(0, 0, "flg_return", "true");
			//�������ʇA	���b�Z�[�W
			ret.addFieldVale(0, 0, "msg_error", "");
			//�������ʇB	��������
			ret.addFieldVale(0, 0, "no_errmsg", "");
			//�������ʇE	���b�Z�[�W�ԍ�
			ret.addFieldVale(0, 0, "nm_class", "");
			//�������ʇC	�G���[�R�[�h
			ret.addFieldVale(0, 0, "cd_error", "");
			//�������ʇD	�V�X�e�����b�Z�[�W
			ret.addFieldVale(0, 0, "msg_system", "");
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}

	
	/**
	 * �K���CSV�t�H�[�}�b�g�ɕϊ�
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �ϊ��ナ�X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> makeCsvFile(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//�ϊ��ナ�X�g
		List<?> ret = null;
		
		try {
			
			List<Object> aryAll = new ArrayList<Object>();
			
			//���i�����^�C�g���}��
			Object[] arySeihinTitle = new Object[1];
			arySeihinTitle[0] = "���i����";
			aryAll.add(arySeihinTitle);
			
			//���i���������}��
			Object[] arySeihinZyoken = new Object[4];
			arySeihinZyoken[0] = (reqData.getFieldVale("seihin_joken", 0, "joken_nm_kaisha"));
			arySeihinZyoken[1] = (reqData.getFieldVale("seihin_joken", 0, "joken_nm_kojo"));
			arySeihinZyoken[2] = (reqData.getFieldVale("seihin_joken", 0, "joken_cd_seihin"));
			arySeihinZyoken[3] = (reqData.getFieldVale("seihin_joken", 0, "joken_nm_seihin"));
			aryAll.add(arySeihinZyoken);
			
			//2010/02/16[�\�����ږ��s�̒ǉ�] NAKAMURA ADD START-----------
			Object[] arySeihinKomoku = new Object[3];
			arySeihinKomoku[0] = "�H��";
			arySeihinKomoku[1] = "���i�R�[�h";
			arySeihinKomoku[2] = "���i��";
			aryAll.add(arySeihinKomoku);
			//2010/02/16[�\�����ږ��s�̒ǉ�] NAKAMURA ADD END-------------
			
			//���i�ꗗ�}��
			for(int i=0; i<reqData.getCntRow("seihin"); i++){
				Object[] arySeihinList = new Object[3];
				arySeihinList[0] = (reqData.getFieldVale("seihin", i, "nm_kojo"));
				arySeihinList[1] = (reqData.getFieldVale("seihin", i, "cd_seihin"));
				arySeihinList[2] = (reqData.getFieldVale("seihin", i, "nm_seihin"));
				aryAll.add(arySeihinList);
			}
			
			//���ތ����^�C�g���}��
			Object[] aryShizaiTitle = new Object[1];
			aryShizaiTitle[0] = ("���ތ���");
			aryAll.add(aryShizaiTitle);
			
			//���ތ��������}��
			Object[] aryShizaiZyoken = new Object[4];
			aryShizaiZyoken[0] = (reqData.getFieldVale("shizai_joken", 0, "joken_nm_kaisha"));
			aryShizaiZyoken[1] = (reqData.getFieldVale("shizai_joken", 0, "joken_nm_kojo"));
			aryShizaiZyoken[2] = (reqData.getFieldVale("shizai_joken", 0, "joken_cd_shizai"));
			aryShizaiZyoken[3] = (reqData.getFieldVale("shizai_joken", 0, "joken_nm_shizai"));
			aryAll.add(aryShizaiZyoken);
			
			//2010/02/16[�\�����ږ��s�̒ǉ�] NAKAMURA ADD START-----------
			Object[] aryShizaiSentakuKomoku = new Object[7];
			aryShizaiSentakuKomoku[0] = "�H��";
			aryShizaiSentakuKomoku[1] = "���ރR�[�h";
			aryShizaiSentakuKomoku[2] = "���ޖ�";
			aryShizaiSentakuKomoku[3] = "�P��";
			aryShizaiSentakuKomoku[4] = "�����i���j";
			aryShizaiSentakuKomoku[5] = "�g�p�ʁ^�P�[�X";
			aryShizaiSentakuKomoku[6] = "���i�R�[�h";
			aryAll.add(aryShizaiSentakuKomoku);
			//2010/02/16[�\�����ږ��s�̒ǉ�] NAKAMURA ADD END-------------
			
			//���ވꗗ�}��
			for(int i=0; i<reqData.getCntRow("shizai"); i++){
				Object[] aryShizaiList = new Object[7];
				aryShizaiList[0] = (reqData.getFieldVale("shizai", i, "nm_kojo"));
				aryShizaiList[1] = (reqData.getFieldVale("shizai", i, "cd_shizai"));
				aryShizaiList[2] = (reqData.getFieldVale("shizai", i, "nm_shizai"));
				aryShizaiList[3] = (reqData.getFieldVale("shizai", i, "tanka"));
				aryShizaiList[4] = (reqData.getFieldVale("shizai", i, "budomari"));
				aryShizaiList[5] = (reqData.getFieldVale("shizai", i, "siyoryo"));
				aryShizaiList[6] = (reqData.getFieldVale("shizai", i, "cd_seihin"));
				aryAll.add(aryShizaiList);
			}
			
			//�I�����ރ^�C�g���}��
			Object[] arySentakuTitle = new Object[1];
			arySentakuTitle[0] = ("�I������");
			aryAll.add(arySentakuTitle);
			
			//2010/02/16[�\�����ږ��s�̒ǉ�] NAKAMURA ADD START-----------
			aryAll.add(aryShizaiSentakuKomoku);
			//2010/02/16[�\�����ږ��s�̒ǉ�] NAKAMURA ADD END-------------
			
			//�I���ꗗ�}��
			for(int i=0; i<reqData.getCntRow("sentaku"); i++){
				Object[] arySentakuList = new Object[7];
				arySentakuList[0] = (reqData.getFieldVale("sentaku", i, "nm_kojo"));
				arySentakuList[1] = (reqData.getFieldVale("sentaku", i, "cd_shizai"));
				arySentakuList[2] = (reqData.getFieldVale("sentaku", i, "nm_shizai"));
				arySentakuList[3] = (reqData.getFieldVale("sentaku", i, "tanka"));
				arySentakuList[4] = (reqData.getFieldVale("sentaku", i, "budomari"));
				arySentakuList[5] = (reqData.getFieldVale("sentaku", i, "siyoryo"));
				arySentakuList[6] = (reqData.getFieldVale("sentaku", i, "cd_seihin"));
				aryAll.add(arySentakuList);
			}
			
			//�ԋp�l�ɐݒ�
			ret = aryAll;
			
		} catch (Exception e) {
			em.ThrowException(e, "�ގ��i�����f�[�^�ACSV�t�H�[�}�b�g�ϊ��Ɏ��s���܂����B");

		} finally {
			
		}
		return ret;
		
	}

}