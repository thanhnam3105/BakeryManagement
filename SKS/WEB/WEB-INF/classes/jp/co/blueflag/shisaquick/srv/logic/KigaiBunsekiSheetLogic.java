package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * ��Q���̓V�[�g�𐶐�����
 * @author k-katayama
 * @since  2009/05/21
 */
public class KigaiBunsekiSheetLogic extends LogicBaseExcel {
	
	/**
	 * �R���X�g���N�^
	 */
	public KigaiBunsekiSheetLogic() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
	}
	/**
	 * ��Q���̓V�[�g�𐶐�����
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
		String DownLoadPath = "";
		
		try {
			//DB����
//			super.createSearchDB();
//			lstRecset = getData(reqData);

			//Excel�t�@�C������
//			DownLoadPath = makeExcelFile(lstRecset);

			//���X�|���X�f�[�^����
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "��Q���̓V�[�g�̐����Ɏ��s���܂����B");

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
			ret.setID("SA810");
			
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
	 * ��Q���̓V�[�g(EXCEL)�𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile(List<?> lstRecset) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		
		try {
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("��Q���̓V�[�g");
			
			//�_�E�����[�h�p��EXCEL�𐶐�����
			for (int i = 0; i < lstRecset.size(); i++) {
				
				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset.get(i);

				try{
					//Excel�ɒl���Z�b�g����
					//�i�R�[�h
					super.ExcelSetValue("�i�R�[�h", toString(items[4]));
					
				}catch(ExceptionWaning e){
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;
					
				}finally{
					
				}
				
			}
			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
			ret = super.ExcelOutput();
			
		} catch (Exception e) {
			em.ThrowException(e, "");

		} finally {

		}
		return ret;
		
	}
	/**
	 * �Ώۂ̃f�[�^����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//��������
		List<?> ret = null;
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//SQL���̍쐬
			strSql = MakeSQLBuf(KindBean);
			
			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "��Q���̓V�[�g�ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;
		}
		return ret;
		
	}
	/**
	 * ���N�G�X�g�f�[�^���A����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : ����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();

		try {

			String strCd_shain = "";
			String strNen = "";
			String strNo_oi = "";
			
			//����iCD�@�Ј��R�[�h
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL���̍쐬	
			ret.append(" SELECT ");
	
		} catch (Exception e) {
			this.em.ThrowException(e, "��Q���̓V�[�g�A����SQL�̐����Ɏ��s���܂����B");			
		} finally {	
		}
		return ret;
		
	}

}