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
 * �h�{�v�Z���i�ܒ��j�@�@�\ID�FSA430
 * @author isono
 * @since  2009/05/25
 */
public class EiryouKeisan1Logic extends LogicBaseExcel {
	
	/**
	 * �R���X�g���N�^
	 */
	public EiryouKeisan1Logic() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
	}
	/**
	 * �T���v���������𐶐�����
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
			super.createSearchDB();
			lstRecset = getData(reqData);

			//Excel�t�@�C������
			DownLoadPath = makeExcelFile(lstRecset);

			//���X�|���X�f�[�^����
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "�h�{�v�Z���i�ܒ��j�̐����Ɏ��s���܂����B");

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
			ret.setID("SA430");
			
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
			em.ThrowException(e, "�h�{�v�Z���i�ܒ��j");

		} finally {

		}
		return ret;
		
	}
	/**
	 * �T���v���������𐶐�����
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
			super.ExcelReadTemplate("�������i�ܒ��j");
			
			//�_�E�����[�h�p��EXCEL�𐶐�����
			for (int i = 0; i < lstRecset.size(); i++) {
				
				//�������ʂ�1�s�������o��
				Object[] items = (Object[]) lstRecset.get(i);

				try{
					//Excel�ɒl���Z�b�g����
					//���얼
					super.ExcelSetValue("���얼", toString(items[0]));
					//���@�ԍ�
					super.ExcelSetValue("���@�ԍ�", toString(items[1]));
					//�H�i�ԍ�
					super.ExcelSetValue("�H�i�ԍ�", toString(items[2])); 
					//�g�p��
					super.ExcelSetValue("�g�p��", 
							toRound( toDouble(toString(items[3])) * 1000, 3));
					
				}catch(ExceptionWaning e){
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;
					
				}finally{
					
				}
				
			}
			//�G�N�Z���t�@�C�����_�E�����[�h�t�H���_�ɐ�������
			ret = super.ExcelOutput();
			
		} catch (Exception e) {
			em.ThrowException(e, "�h�{�v�Z���i�ܒ��j�Aexcel�̐����Ɏ��s���܂����B");

		} finally {

		}
		return ret;
		
	}
	/**
	 * �Ώۂ̎���i�f�[�^����������
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
			em.ThrowException(e, "�h�{�v�Z���i�ܒ��j�ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;
		}
		return ret;
		
	}
	/**
	 * ���N�G�X�g�f�[�^���A����f�[�^����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : ����f�[�^����SQL
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
			String strSeq_shisaku = "";
			
			//����iCD�@�Ј��R�[�h
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//����No�i�T���v���ԍ��j
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku");
	
			//SQL���̍쐬	
			ret.append(" SELECT ");
			ret.append(" T110.nm_hin AS NM_HIN ");
			ret.append(" ,ISNULL(T131.no_seiho1, '') AS NO_SEIHO ");
			ret.append(" ,ANS.no_eiyo AS EIYO ");
			ret.append(" ,SUM(ANS.��) AS RYO ");
			ret.append(" FROM ");
			ret.append(" ( ");
			ret.append(" SELECT ");
			ret.append("   T131.cd_shain ");
			ret.append(" , T131.nen ");
			ret.append(" , T131.no_oi ");
			ret.append(" , T131.seq_shisaku ");
			ret.append(" , T120.cd_genryo ");
			ret.append(" , T120.cd_kaisha ");
			ret.append(" , T401.no_eiyo1 AS no_eiyo ");
			ret.append(" , T401.wariai1 AS wariai ");
			ret.append(" , T132.quantity ");
			ret.append(" , CASE ISNULL(T401.wariai1, 0) ");
			ret.append(" WHEN '' THEN 0 ELSE ISNULL(T401.wariai1, 0) ");
			ret.append(" END * 0.01 * ISNULL(T132.quantity, 0) AS �� ");
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append(" ON T131.cd_shain = T132.cd_shain ");
			ret.append(" AND T131.nen = T132.nen ");
			ret.append(" AND T131.no_oi = T132.no_oi ");
			ret.append(" AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append(" ON T132.cd_shain = T120.cd_shain ");
			ret.append(" AND T132.nen = T120.nen ");
			ret.append(" AND T132.no_oi = T120.no_oi ");
			ret.append(" AND T132.cd_kotei = T120.cd_kotei ");
			ret.append(" AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS T401 ");
			ret.append(" ON T120.cd_genryo = T401.cd_genryo ");
			ret.append(" AND T120.cd_kaisha = T401.cd_kaisha ");
			ret.append(" WHERE  ");
			ret.append("     T131.cd_shain = " + strCd_shain + " ");
			ret.append(" AND T131.nen = " + strNen + " ");
			ret.append(" AND T131.no_oi = " + strNo_oi + " ");
			ret.append(" AND T131.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(T401.no_eiyo1, '') != ''  ");
			ret.append(" UNION ALL ");
			ret.append(" SELECT ");
			ret.append("   T131.cd_shain ");
			ret.append(" , T131.nen ");
			ret.append(" , T131.no_oi ");
			ret.append(" , T131.seq_shisaku ");
			ret.append(" , T120.cd_genryo ");
			ret.append(" , T120.cd_kaisha ");
			ret.append(" , T401.no_eiyo2 AS no_eiyo  ");
			ret.append(" , T401.wariai2 AS wariai ");
			ret.append(" , T132.quantity ");
			ret.append(" , CASE ISNULL(T401.wariai2, 0) ");
			ret.append(" WHEN '' THEN 0 ELSE ISNULL(T401.wariai2, 0) ");
			ret.append(" END * 0.01 * ISNULL(T132.quantity, 0) AS �� ");
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append(" ON T131.cd_shain = T132.cd_shain ");
			ret.append(" AND T131.nen = T132.nen ");
			ret.append(" AND T131.no_oi = T132.no_oi ");
			ret.append(" AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append(" ON T132.cd_shain = T120.cd_shain ");
			ret.append(" AND T132.nen = T120.nen ");
			ret.append(" AND T132.no_oi = T120.no_oi ");
			ret.append(" AND T132.cd_kotei = T120.cd_kotei ");
			ret.append(" AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS T401 ");
			ret.append(" ON T120.cd_genryo = T401.cd_genryo ");
			ret.append(" AND T120.cd_kaisha = T401.cd_kaisha ");
			ret.append(" WHERE  ");
			ret.append("     T131.cd_shain = " + strCd_shain + " ");
			ret.append(" AND T131.nen = " + strNen + " ");
			ret.append(" AND T131.no_oi = " + strNo_oi + " ");
			ret.append(" AND T131.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(T401.no_eiyo1, '') != ''  ");
			ret.append(" UNION ALL ");
			ret.append(" SELECT ");
			ret.append("   T131.cd_shain ");
			ret.append(" , T131.nen ");
			ret.append(" , T131.no_oi ");
			ret.append(" , T131.seq_shisaku ");
			ret.append(" , T120.cd_genryo ");
			ret.append(" , T120.cd_kaisha ");
			ret.append(" , T401.no_eiyo3 AS no_eiyo  ");
			ret.append(" , T401.wariai3 AS wariai ");
			ret.append(" , T132.quantity ");
			ret.append(" , CASE ISNULL(T401.wariai3, 0) ");
			ret.append(" WHEN '' THEN 0 ELSE ISNULL(T401.wariai3, 0) ");
			ret.append(" END * 0.01 * ISNULL(T132.quantity, 0) AS �� ");
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append(" ON T131.cd_shain = T132.cd_shain ");
			ret.append(" AND T131.nen = T132.nen ");
			ret.append(" AND T131.no_oi = T132.no_oi ");
			ret.append(" AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append(" ON T132.cd_shain = T120.cd_shain ");
			ret.append(" AND T132.nen = T120.nen ");
			ret.append(" AND T132.no_oi = T120.no_oi ");
			ret.append(" AND T132.cd_kotei = T120.cd_kotei ");
			ret.append(" AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS T401 ");
			ret.append(" ON T120.cd_genryo = T401.cd_genryo ");
			ret.append(" AND T120.cd_kaisha = T401.cd_kaisha ");
			ret.append(" WHERE  ");
			ret.append("     T131.cd_shain = " + strCd_shain + " ");
			ret.append(" AND T131.nen = " + strNen + " ");
			ret.append(" AND T131.no_oi = " + strNo_oi + " ");
			ret.append(" AND T131.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(T401.no_eiyo1, '') != ''  ");
			ret.append(" UNION ALL ");
			ret.append(" SELECT ");
			ret.append("   T131.cd_shain ");
			ret.append(" , T131.nen ");
			ret.append(" , T131.no_oi ");
			ret.append(" , T131.seq_shisaku ");
			ret.append(" , T120.cd_genryo ");
			ret.append(" , T120.cd_kaisha ");
			ret.append(" , T401.no_eiyo4 AS no_eiyo  ");
			ret.append(" , T401.wariai4 AS wariai ");
			ret.append(" , T132.quantity ");
			ret.append(" , CASE ISNULL(T401.wariai4, 0) ");
			ret.append(" WHEN '' THEN 0 ELSE ISNULL(T401.wariai4, 0) ");
			ret.append(" END * 0.01 * ISNULL(T132.quantity, 0) AS �� ");
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append(" ON T131.cd_shain = T132.cd_shain ");
			ret.append(" AND T131.nen = T132.nen ");
			ret.append(" AND T131.no_oi = T132.no_oi ");
			ret.append(" AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append(" ON T132.cd_shain = T120.cd_shain ");
			ret.append(" AND T132.nen = T120.nen ");
			ret.append(" AND T132.no_oi = T120.no_oi ");
			ret.append(" AND T132.cd_kotei = T120.cd_kotei ");
			ret.append(" AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS T401 ");
			ret.append(" ON T120.cd_genryo = T401.cd_genryo ");
			ret.append(" AND T120.cd_kaisha = T401.cd_kaisha ");
			ret.append(" WHERE  ");
			ret.append("     T131.cd_shain = " + strCd_shain + " ");
			ret.append(" AND T131.nen = " + strNen + " ");
			ret.append(" AND T131.no_oi = " + strNo_oi + " ");
			ret.append(" AND T131.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(T401.no_eiyo1, '') != ''  ");
			ret.append(" UNION ALL ");
			ret.append(" SELECT ");
			ret.append("   T131.cd_shain ");
			ret.append(" , T131.nen ");
			ret.append(" , T131.no_oi ");
			ret.append(" , T131.seq_shisaku ");
			ret.append(" , T120.cd_genryo ");
			ret.append(" , T120.cd_kaisha ");
			ret.append(" , T401.no_eiyo5 AS no_eiyo  ");
			ret.append(" , T401.wariai5 AS wariai ");
			ret.append(" , T132.quantity ");
			ret.append(" , CASE ISNULL(T401.wariai5, 0) ");
			ret.append(" WHEN '' THEN 0 ELSE ISNULL(T401.wariai5, 0) ");
			ret.append(" END * 0.01 * ISNULL(T132.quantity, 0) AS �� ");
			ret.append(" FROM tr_shisaku AS T131 ");
			ret.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			ret.append(" ON T131.cd_shain = T132.cd_shain ");
			ret.append(" AND T131.nen = T132.nen ");
			ret.append(" AND T131.no_oi = T132.no_oi ");
			ret.append(" AND T131.seq_shisaku = T132.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS T120 ");
			ret.append(" ON T132.cd_shain = T120.cd_shain ");
			ret.append(" AND T132.nen = T120.nen ");
			ret.append(" AND T132.no_oi = T120.no_oi ");
			ret.append(" AND T132.cd_kotei = T120.cd_kotei ");
			ret.append(" AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS T401 ");
			ret.append(" ON T120.cd_genryo = T401.cd_genryo ");
			ret.append(" AND T120.cd_kaisha = T401.cd_kaisha ");
			ret.append(" WHERE  ");
			ret.append("     T131.cd_shain = " + strCd_shain + " ");
			ret.append(" AND T131.nen = " + strNen + " ");
			ret.append(" AND T131.no_oi = " + strNo_oi + " ");
			ret.append(" AND T131.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(T401.no_eiyo1, '') != ''  ");
			ret.append(" ) AS ANS ");
			ret.append(" LEFT JOIN tr_shisakuhin AS T110 ");
			ret.append(" ON ANS.cd_shain = T110.cd_shain ");
			ret.append(" AND ANS.nen = T110.nen ");
			ret.append(" AND ANS.no_oi = T110.no_oi ");
			ret.append(" LEFT JOIN tr_shisaku AS T131 ");
			ret.append(" ON T110.cd_shain = T131.cd_shain ");
			ret.append(" AND T110.nen = T131.nen ");
			ret.append(" AND T110.no_oi = T131.no_oi ");
			ret.append(" AND T110.seq_shisaku = T131.seq_shisaku ");
			ret.append(" WHERE ");
			ret.append("  ANS.�� > 0 ");
			ret.append(" GROUP BY  ");
			ret.append("  T110.nm_hin ");
			ret.append(" ,T131.no_seiho1 ");
			ret.append(" ,ANS.no_eiyo ");
	
		} catch (Exception e) {
			this.em.ThrowException(e, "�h�{�v�Z���i�ܒ��j�A����SQL�̐����Ɏ��s���܂����B");
			
		} finally {
	
		}
		return ret;
		
	}

}
