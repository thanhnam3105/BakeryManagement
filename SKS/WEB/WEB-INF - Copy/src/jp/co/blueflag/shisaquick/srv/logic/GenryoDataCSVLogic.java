package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseCSV;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * �����f�[�^CSV�𐶐�����
 * @author jinbo
 * @since  2009/05/13
 */
public class GenryoDataCSVLogic extends LogicBaseCSV {
	
	/**
	 * �R���X�g���N�^
	 */
	public GenryoDataCSVLogic() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
	}
	
	/**
	 * �����f�[�^CSV�𐶐�����
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
			strFilePath = CSVOutput("�����}�X�^", lstRecset);
			
			//���X�|���X�f�[�^����
			ret = storageGenryoDataCSV(strFilePath);
			
		} catch (Exception e) {
			em.ThrowException(e, "�����f�[�^CSV�̐����Ɏ��s���܂����B");

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
	private RequestResponsKindBean storageGenryoDataCSV(String DownLoadPath) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		RequestResponsKindBean ret = null;
		
		try {
			//���X�|���X�𐶐�����
			ret = new RequestResponsKindBean();
			//�@�\ID��ݒu����
			ret.setID("SA360");
			
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
	 * �Ώۂ̌����f�[�^����������
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
			strSql = genryoDataCSVCreateSQL(KindBean);
			
			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 1){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "�����f�[�^�ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;
		}
		return ret;
		
	}
	
	/**
	 * ���N�G�X�g�f�[�^���A�����f�[�^����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : �����f�[�^����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer genryoDataCSVCreateSQL(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();

		try {

			String strJokenKbn1 = "";
			String strJokenKbn2 = "";
			String strCd_Genryo = "";
			String strName_Genryo = "";
			String strCd_Kaisha = "";
			String strCd_Kojo = "";
			String dataId = null;
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			String strShiyoFlg = "";
//add end --------------------------------------------------------------------------------------
			
			//�Ώی����i�V�K�����j�̎擾
			strJokenKbn1 = reqData.getFieldVale(0, 0, "kbn_joken1");
			//�Ώی����i���������j�̎擾
			strJokenKbn2 = reqData.getFieldVale(0, 0, "kbn_joken2");
			//�����R�[�h�̎擾
			strCd_Genryo = reqData.getFieldVale(0, 0, "cd_genryo");
			//�������̎擾
			strName_Genryo = reqData.getFieldVale(0, 0, "nm_genryo");
			//��ЃR�[�h
			strCd_Kaisha = reqData.getFieldVale(0, 0, "cd_kaisha");
			//�H��R�[�h
			strCd_Kojo = reqData.getFieldVale(0, 0, "cd_kojo");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//�g�p���уt���O
			strShiyoFlg = reqData.getFieldVale(0, 0, "flg_shiyo");
//add end --------------------------------------------------------------------------------------

			String strSqlTanto = "SELECT Shin.cd_kaisha FROM tr_shisakuhin Shin JOIN ma_tantokaisya Tanto ON Shin.cd_kaisha = Tanto.cd_tantokaisha AND Tanto.id_user = " + userInfoData.getId_user();
	
			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals("25")){
					//�����}�X�^�b�r�u��ʂ̃f�[�^ID��ݒ�
					dataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
			//SQL���̍쐬	
			ret.append(" SELECT ");
			ret.append("  cd_genryo ");
			ret.append(" , nm_genryo ");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			ret.append(" , flg_shiyo ");
			ret.append(" , flg_mishiyo ");
//add end --------------------------------------------------------------------------------------
			ret.append(" , ritu_sakusan ");
			ret.append(" , ritu_shokuen ");
			ret.append(" , ritu_sousan ");
			ret.append(" , ritu_abura ");
			ret.append(" , hyojian ");
			ret.append(" , tenkabutu ");
			ret.append(" , memo ");
			ret.append(" , no_eiyo1 ");
			ret.append(" , wariai1 ");
			ret.append(" , no_eiyo2 ");
			ret.append(" , wariai2 ");
			ret.append(" , no_eiyo3 ");
			ret.append(" , wariai3 ");
			ret.append(" , no_eiyo4 ");
			ret.append(" , wariai4 ");
			ret.append(" , no_eiyo5 ");
			ret.append(" , wariai5 ");
			ret.append(" , dt_konyu ");
			ret.append(" , cd_kakutei ");
			ret.append(" , nm_haishi ");
			ret.append(" FROM (");
			ret.append(" SELECT ");
			ret.append("  0 AS kbn ");
			ret.append(" , '�����R�[�h' AS cd_genryo ");
			ret.append(" , '������' AS nm_genryo ");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			ret.append(" , '"+ ConstManager.getConstValue(Category.�ݒ�, "NM_SHIYO_JISEKI") +"' AS flg_shiyo ");
			ret.append(" , '���g�p' AS flg_mishiyo ");
//add end --------------------------------------------------------------------------------------
			ret.append(" , '�|�_(%)' AS ritu_sakusan ");
			ret.append(" , '�H��(%)' AS ritu_shokuen ");
			ret.append(" , '���_(%)' AS ritu_sousan ");
			ret.append(" , '���ܗL��(%)' AS ritu_abura ");
			ret.append(" , '�\����' AS hyojian ");
			ret.append(" , '�Y����' AS tenkabutu ");
			ret.append(" , '����' AS memo ");
			ret.append(" , '�h�{�v�Z�H�i�ԍ�1' AS no_eiyo1 ");
			ret.append(" , '����1(%)' AS wariai1 ");
			ret.append(" , '�h�{�v�Z�H�i�ԍ�2' AS no_eiyo2 ");
			ret.append(" , '����2(%)' AS wariai2 ");
			ret.append(" , '�h�{�v�Z�H�i�ԍ�3' AS no_eiyo3 ");
			ret.append(" , '����3(%)' AS wariai3 ");
			ret.append(" , '�h�{�v�Z�H�i�ԍ�4' AS no_eiyo4 ");
			ret.append(" , '����4(%)' AS wariai4 ");
			ret.append(" , '�h�{�v�Z�H�i�ԍ�5' AS no_eiyo5 ");
			ret.append(" , '����5(%)' AS wariai5 ");
			ret.append(" , '�ŏI�w����' AS dt_konyu ");
			ret.append(" , '�m��R�[�h' AS cd_kakutei ");
			ret.append(" , '�p�~��' AS nm_haishi ");
			ret.append(" UNION ");

			ret.append("SELECT");
			ret.append("  1 AS kbn ");
//			ret.append(" ,A.cd_kaisha");
			ret.append(" ,'\"' + REPLACE(A.cd_genryo,'\"','\"\"') + '\"' AS cd_genryo");
			ret.append(" ,'\"' + REPLACE(A.nm_genryo,'\"','\"\"') + '\"' AS nm_genryo");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			ret.append(" ,CASE LEFT(A.cd_genryo,1) WHEN 'N' THEN '-' ELSE " 
							+ "(CASE ISNULL(A.flg_shiyo,0) WHEN 0 THEN '�~' WHEN 1 THEN '��' END) END flg_shiyo");
			ret.append(" ,CASE LEFT(A.cd_genryo,1) WHEN 'N' THEN '' ELSE " 
							+ "(CASE ISNULL(A.flg_mishiyo,0) WHEN 0 THEN '' WHEN 1 THEN '��' END) END flg_mishiyo");
//add end --------------------------------------------------------------------------------------
			ret.append(" ,ISNULL(CONVERT(varchar,B.ritu_sakusan),'') as ritu_sakusan");
			ret.append(" ,ISNULL(CONVERT(varchar,B.ritu_shokuen),'') as ritu_shokuen");
			ret.append(" ,ISNULL(CONVERT(varchar,B.ritu_sousan),'') as ritu_sousan");
			ret.append(" ,ISNULL(CONVERT(varchar,B.ritu_abura),'') as ritu_abura");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.hyojian,''),'\"','\"\"') + '\"' as hyojian");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.tenkabutu,''),'\"','\"\"') + '\"' as tenkabutu");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.memo,''),'\"','\"\"') + '\"' as memo");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.no_eiyo1,''),'\"','\"\"') + '\"' as no_eiyo1");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.wariai1,''),'\"','\"\"') + '\"' as wariai1");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.no_eiyo2,''),'\"','\"\"') + '\"' as no_eiyo2");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.wariai2,''),'\"','\"\"') + '\"' as wariai2");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.no_eiyo3,''),'\"','\"\"') + '\"' as no_eiyo3");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.wariai3,''),'\"','\"\"') + '\"' as wariai3");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.no_eiyo4,''),'\"','\"\"') + '\"' as no_eiyo4");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.wariai4,''),'\"','\"\"') + '\"' as wariai4");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.no_eiyo5,''),'\"','\"\"') + '\"' as no_eiyo5");
			ret.append(" ,'\"' + REPLACE(ISNULL(B.wariai5,''),'\"','\"\"') + '\"' as wariai5");
			ret.append(" ,ISNULL(convert(varchar(10),A.dt_konyu,111),'') as dt_konyu");
			ret.append(" ,ISNULL(convert(varchar,B.cd_kakutei),'') as cd_kakutei");
			ret.append(" ,CASE B.kbn_haishi WHEN 0 THEN '�g�p�\' WHEN 1 THEN '�p�~' END as nm_haishi");
//			ret.append(" FROM ma_genryokojo A");

			//�H�ꂪ�I������Ă���ꍇ
			if (!strCd_Kojo.equals("")) {
				ret.append(" FROM ma_genryokojo A");
			} else {
				ret.append(" FROM (");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
				ret.append(" SELECT MG1.cd_kaisha, MG1.cd_genryo, MG1.cd_busho, MG2.nm_genryo, '' as dt_konyu, MG2.flg_shiyo, MG2.flg_mishiyo");
//add end --------------------------------------------------------------------------------------
				ret.append(" FROM (");
				ret.append(" SELECT cd_kaisha, cd_genryo, MIN(cd_busho) as cd_busho");
				ret.append(" FROM ma_genryokojo");
				ret.append(" GROUP BY cd_kaisha, cd_genryo");
				ret.append(" ) as MG1");
				ret.append(" INNER JOIN ma_genryokojo MG2");
				ret.append(" ON MG1.cd_kaisha = MG2.cd_kaisha");
				ret.append(" AND MG1.cd_busho = MG2.cd_busho");
				ret.append(" AND MG1.cd_genryo = MG2.cd_genryo");
				ret.append(" ) A");
			}
			
			ret.append("      LEFT JOIN ma_genryo B");
			ret.append("      ON A.cd_kaisha = B.cd_kaisha");
			ret.append("      AND A.cd_genryo = B.cd_genryo");
			ret.append(" WHERE A.cd_kaisha = ");
			ret.append(strCd_Kaisha);
//			ret.append(" AND A.cd_busho IN (");
//			ret.append(strCd_Kojo);
//			ret.append("," + ConstManager.getConstValue(ConstManager.Category.�ݒ�, "SHINKIGENRYO_BUSHOCD") + ")");

			//�H�ꂪ�I������Ă���ꍇ
			if (!strCd_Kojo.equals("")) {
				ret.append(" AND A.cd_busho IN (");
				ret.append(strCd_Kojo);
				ret.append("," + ConstManager.getConstValue(ConstManager.Category.�ݒ�, "SHINKIGENRYO_BUSHOCD") + ")");
			}
			
			//�����R�[�h�����͂���Ă���ꍇ
			if (!strCd_Genryo.equals("")) {
				ret.append(" AND B.cd_genryo = '");
				ret.append(strCd_Genryo);
				ret.append("'");
			}
			
			//�����������͂���Ă���ꍇ
			if (!strName_Genryo.equals("")) {
				ret.append(" AND A.nm_genryo LIKE '%");
				ret.append(strName_Genryo);
				ret.append("%'");
			}

			//�V�K�����������Ώۂ̏ꍇ
			if (!strJokenKbn1.equals("") && strJokenKbn2.equals("")) {
				ret.append(" AND SUBSTRING(B.cd_genryo,1,1) > '9'");
			
			//���������������Ώۂ̏ꍇ
			}else if (strJokenKbn1.equals("") && !strJokenKbn2.equals("")) {
				ret.append(" AND SUBSTRING(B.cd_genryo,1,1) <= '9'");
			}
			
			//�������������ݒ�
			//�S�����
			if(dataId.equals("1")) {
				ret.append(" AND A.cd_kaisha in ( ");
				ret.append(strSqlTanto + " ) ");

			//���H�ꕪ
			} else if (dataId.equals("2")) { 
				ret.append(" AND A.cd_busho = ");
				ret.append(userInfoData.getCd_busho());
			}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//�g�p����
			if (strShiyoFlg.equals("1")) {
				ret.append(" AND A.flg_shiyo = 1");
			}
//add end --------------------------------------------------------------------------------------
			
			ret.append(" ) tbl ");
			ret.append(" ORDER BY ");
			ret.append("     tbl.kbn, tbl.cd_genryo ");
	
		} catch (Exception e) {
			this.em.ThrowException(e, "�����f�[�^CSV�A����SQL�̐����Ɏ��s���܂����B");
			
		} finally {
	
		}
		return ret;
		
	}

}
