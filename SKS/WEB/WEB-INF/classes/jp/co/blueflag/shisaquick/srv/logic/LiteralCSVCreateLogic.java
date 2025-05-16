package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseCSV;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * ���e�����f�[�^CSV�𐶐�����
 * @author jinbo
 * @since  2009/05/13
 */
public class LiteralCSVCreateLogic extends LogicBaseCSV {
	
	/**
	 * �R���X�g���N�^
	 */
	public LiteralCSVCreateLogic() {
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
			//DB����
			super.createSearchDB();
			lstRecset = getData(reqData);

			//CSV�t�@�C������
			strFilePath = CSVOutput("���e�����}�X�^", lstRecset);

			//���X�|���X�f�[�^����
			ret = storageLiteralDataCSV(strFilePath);
			
		} catch (Exception e) {
			em.ThrowException(e, "���e�����f�[�^CSV�̐����Ɏ��s���܂����B");

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
	private RequestResponsKindBean storageLiteralDataCSV(String DownLoadPath) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsKindBean ret = null;
		
		try {
			//���X�|���X�𐶐�����
			ret = new RequestResponsKindBean();
			//�@�\ID��ݒu����
			ret.setID("SA320");
			
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
	 * �Ώۂ̃��e�����f�[�^����������
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
			strSql = literalDataCSVCreateSQL(KindBean);
			
			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 1){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "���e�����f�[�^�ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;
		}
		return ret;
		
	}
	
	/**
	 * ���N�G�X�g�f�[�^���A���e�����f�[�^����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : ���e�����f�[�^����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer literalDataCSVCreateSQL(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();

		try {

			String strCd_Category = "";
			String dataId = null;

			//�J�e�S���R�[�h
			strCd_Category = reqData.getFieldVale(0, 0, "cd_category");
	
			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("65")){
					//���e�����}�X�^�b�r�u��ʂ̃f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL���̍쐬	
			ret.append(" SELECT ");
			ret.append("  cd_category ");
			ret.append(" , nm_category ");
			ret.append(" , cd_literal ");
			ret.append(" , nm_��iteral ");
			ret.append(" , value1 ");
			ret.append(" , value2 ");
			ret.append(" , no_sort ");
			ret.append(" , biko ");
			ret.append(" , flg_edit ");
			ret.append(" , cd_group ");
			ret.append(" , nm_group ");
			ret.append(" FROM (");
			ret.append(" SELECT ");
			ret.append("  0 AS kbn ");
			ret.append(" , '�J�e�S���R�[�h' AS cd_category ");
			ret.append(" , '�J�e�S����' AS nm_category ");
			ret.append(" , '���e�����R�[�h' AS cd_literal ");
			ret.append(" , '���e������' AS nm_��iteral ");
			ret.append(" , '���e�����l1' AS value1 ");
			ret.append(" , '���e�����l2' AS value2 ");
			ret.append(" , '�\����' AS no_sort ");
			ret.append(" , '���l' AS biko ");
			ret.append(" , '�ҏWFlg' AS flg_edit ");
			ret.append(" , '�O���[�v�R�[�h' AS cd_group ");
			ret.append(" , '�O���[�v��' AS nm_group ");
			ret.append(" UNION ");
			ret.append(" SELECT ");
			ret.append("  1 AS kbn ");
			ret.append(" , '\"' + REPLACE(A.cd_category,'\"','\"\"') + '\"' AS cd_category ");
			ret.append(" , '\"' + REPLACE(A.nm_category,'\"','\"\"') + '\"' AS nm_category ");
			ret.append(" , '\"' + REPLACE(B.cd_literal,'\"','\"\"') + '\"' AS cd_literal ");
			ret.append(" , '\"' + REPLACE(B.nm_��iteral,'\"','\"\"') + '\"' AS nm_��iteral ");
			ret.append(" , ISNULL(CONVERT(varchar,B.value1),'') AS value1 ");
			ret.append(" , ISNULL(CONVERT(varchar,B.value2),'') AS value2 ");
			ret.append(" , ISNULL(CONVERT(varchar,B.no_sort),'') AS no_sort ");
			ret.append(" , '\"' + REPLACE(ISNULL(CONVERT(varchar,B.biko),''),'\"','\"\"') + '\"' AS biko ");
			ret.append(" , ISNULL(CONVERT(varchar,B.flg_edit),'') AS flg_edit ");
			ret.append(" , ISNULL(CONVERT(varchar,B.cd_group),'') AS cd_group ");
			ret.append(" , '\"' + REPLACE(ISNULL(CONVERT(varchar,C.nm_group),''),'\"','\"\"') + '\"' AS nm_group ");
			ret.append(" FROM ");
			ret.append(" ma_category AS A ");
			ret.append(" INNER JOIN ma_literal AS B ");
			ret.append(" ON A.cd_category = B.cd_category ");
			ret.append(" LEFT JOIN ma_group AS C ");
			ret.append(" ON B.cd_group = C.cd_group ");
			ret.append(" WHERE ");
			ret.append("     A.cd_category = '" + strCd_Category + "' ");
			
			//�������������ݒ�
			//����O���[�v
			if(dataId.equals("1")) {
				ret.append(" AND (B.cd_group IS NULL");
				ret.append(" OR B.cd_group = ");
				ret.append(userInfoData.getCd_group());
				ret.append(" ) ");
			}

			ret.append(" ) tbl ");
			ret.append(" ORDER BY ");
			ret.append("     tbl.kbn, tbl.cd_literal ");
	
		} catch (Exception e) {
			this.em.ThrowException(e, "���e�����f�[�^CSV�A����SQL�̐����Ɏ��s���܂����B");
			
		} finally {
	
		}
		return ret;
		
	}

}