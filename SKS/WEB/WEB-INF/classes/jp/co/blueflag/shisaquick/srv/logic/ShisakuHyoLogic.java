package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBaseExcel;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * �ySA460�z ����\�𐶐�����
 * 
 * @author k-katayama
 * @since 2009/05/20
 *
 */
public class ShisakuHyoLogic extends LogicBaseExcel {

	/**
	 * �R���X�g���N�^
	 */
	public ShisakuHyoLogic() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
	}

	/**
	 * ����\�𐶐�����
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
		//�z���f�[�^�p�����f�[�^
		List<?> lstHaigoRecset = null;
		//����f�[�^�p�����f�[�^
		List<?> lstShisakuRecset = null;
		//�G�N�Z���t�@�C���p�X
		String DownLoadPath = "";
		
		try {
			//DB���� : �z���f�[�^�̌���
			super.createSearchDB();
			lstHaigoRecset = getHaigoData(reqData);
			
			//DB���� : ����f�[�^�̌���
			lstShisakuRecset = getShisakuData(reqData);
						
			//Excel�t�@�C������
			DownLoadPath = makeExcelFile(lstHaigoRecset, lstShisakuRecset);
			
			//���X�|���X�f�[�^����
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "����\�̐����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstHaigoRecset);
			removeList(lstShisakuRecset);
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
			ret.setID("SA460");
			
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
	 * ����\�𐶐�����
	 * @param lstHaigoRecset : �z���f�[�^�����f�[�^���X�g
	 * @param lstShisakuRecset : ����f�[�^�����f�[�^���X�g
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile(List<?> lstHaigoRecset, List<?> lstShisakuRecset) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		int i;
		
		try {		
			
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("����\");

			//�_�E�����[�h�p��EXCEL�𐶐�����
			
			// �@ : ��ʊ�{�����_�E�����[�h�pEXCEL�ɐݒ�
			
			// �������ʎ擾
			Object[] items = (Object[]) lstHaigoRecset.get(0);
			
			// Excel�ɒl���Z�b�g
			super.ExcelSetValue("����R�[�h", toString(items[0]));
			super.ExcelSetValue("�˗��ԍ�", toString(items[1]));
			super.ExcelSetValue("�i��", toString(items[2]));
			super.ExcelSetValue("����", toString(items[3]));
			super.ExcelSetValue("�I���H��", toString(items[4]));
			super.ExcelSetValue("�����", toString(items[5]));
			super.ExcelSetValue("���s��", toString(items[6]));
			super.ExcelSetValue("��������", toString(items[7]));

			// �A : �z���f�[�^���_�E�����[�h�pEXCEL�ɐݒ�
			for ( i = 0; i < lstHaigoRecset.size(); i++ ) {
				
				// �������ʎ擾
				items = (Object[]) lstHaigoRecset.get(i);
				
				try{
					// Excel�ɒl���Z�b�g����
					super.ExcelSetValue("�����R�[�h", toString(items[8]));
					super.ExcelSetValue("������", toString(items[9]));
					super.ExcelSetValue("�P��", toString(items[10]));
					super.ExcelSetValue("����", toString(items[11]));
					super.ExcelSetValue("��1", toString(items[12]));
					super.ExcelSetValue("��2", toString(items[13]));
					super.ExcelSetValue("��3", toString(items[14]));
					super.ExcelSetValue("��4", toString(items[15]));
					super.ExcelSetValue("��5", toString(items[16]));
					super.ExcelSetValue("��6", toString(items[17]));
					super.ExcelSetValue("��7", toString(items[18]));
					super.ExcelSetValue("��8", toString(items[19]));
					super.ExcelSetValue("��9", toString(items[20]));
					super.ExcelSetValue("��10", toString(items[21]));
					
				} catch (ExceptionWaning e) {
					// �ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;
					
				} finally {
					items = null;			
					
				}
				
			}
			
			//�A : ����f�[�^���_�E�����[�h�pEXCEL�ɐݒ�

			//�������ʎ擾
			items = (Object[]) lstShisakuRecset.get(0);
			
			//���ӎ����E���상��
			super.ExcelSetValue("���상��", toString(items[4]));
			super.ExcelSetValue("�����H��/���ӎ���", toString(items[7]));
			
			for ( i = 0; i < lstShisakuRecset.size(); i++ ) {
				
				//�������ʎ擾
				items = (Object[]) lstShisakuRecset.get(i);
				
				try{
					//Excel�ɒl���Z�b�g����
					super.ExcelSetValue("���t", toString(items[1]));
					super.ExcelSetValue("NO", toString(items[2]));
					super.ExcelSetValue("����", toString(items[3]));
					super.ExcelSetValue("�R�����g���eNO", toString(items[2]));
					super.ExcelSetValue("�R�����g���e", toString(items[5]) + "\n\n" + toString(items[6]));
					
				} catch (ExceptionWaning e) {
					//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
					break;
					
				} finally {
					items = null;			
					
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
	 * �Ώۂ̔z���f�[�^����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getHaigoData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		//��������
		List<?> lstHaigoData = null;
		List<?> lstShisakuListData = null;
		//�o�̓f�[�^
		List<?> ret = null;
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//�@ :�@�z���f�[�^�̌���
			
			//SQL���̍쐬
			strSql = MakeHaigoSQLBuf(KindBean);
			
			//SQL�����s
			lstHaigoData = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstHaigoData.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				
			}

			//�A :�@���샊�X�g�f�[�^�̌���
			
			//SQL���̍쐬
			strSql = MakeShisakuListSQLBuf(KindBean);
			
			//SQL�����s
			lstShisakuListData = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (lstShisakuListData.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				
			}

			//�B �z���f�[�^�Ǝ��샊�X�g�f�[�^�����킹�āA�o�͗p���X�g���쐬����
			ret = CreateHaigoShisakuListData(lstHaigoData, lstShisakuListData);
			
		} catch (Exception e) {
			em.ThrowException(e, "����\�o�́A�z���f�[�^�����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstHaigoData);
			removeList(lstShisakuListData);
			
			//�ϐ��̍폜
			strSql = null;

		}
		return ret;
		
	}
	/**
	 * �Ώۂ̎���f�[�^����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getShisakuData(RequestResponsKindBean KindBean) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//��������
		List<?> ret = null;
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//�@ ����f�[�^��������
			
			//SQL���̍쐬
			strSql = MakeShisakuSQLBuf(KindBean);
			
			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "����\�o�́A����f�[�^�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;

		}
		return ret;
		
	}
	/**
	 * ���N�G�X�g�f�[�^���A�z���f�[�^����SQL�𐶐�����
	 *  : ����i�e�[�u���A�z���e�[�u�����������A�z���f�[�^�𐶐�����B
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : �z���f�[�^����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeHaigoSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strCd_kotei = "";
		String strSeq_kotei = "";

		try {

			//���N�G�X�g�p�����[�^���A����R�[�h���擾����
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL���̍쐬
			ret.append(" SELECT DISTINCT ");
			ret.append("  RIGHT('0000000000'+CONVERT(varchar,T110.cd_shain),10)  ");
			ret.append("   +'-'+ RIGHT('00'+CONVERT(varchar,T110.nen),2)  ");
			ret.append("   +'-'+ RIGHT('000'+CONVERT(varchar,T110.no_oi),3) AS cd_shisaku  ");
			ret.append("  ,CONVERT(VARCHAR,T110.no_irai) AS no_irai ");
			ret.append("  ,T110.nm_hin AS nm_hin ");
			ret.append("  ,ISNULL(T110.memo,'') AS sogo_memo ");
			ret.append("  ,M104.nm_kaisha AS nm_kaisha ");
			ret.append("  ,M104.nm_busho AS nm_kojo ");
			ret.append("  ,M101_shain.nm_user AS nm_shain ");
			ret.append("  ,CONVERT(VARCHAR,GETDATE(),111) + ' ' ");
			ret.append("    + CONVERT(VARCHAR,DATEPART(hour,GETDATE())) + ':' ");
			ret.append("    + CONVERT(VARCHAR,DATEPART(minute,GETDATE()))  AS dt_hakkou ");
			ret.append("  ,T120.cd_kotei AS cd_kotei ");
			ret.append("  ,T120.seq_kotei AS seq_kotei ");
			ret.append("  ,ISNULL(T120.nm_kotei,'') AS nm_kotei ");
			ret.append("  ,ISNULL(T120.zoku_kotei,'') AS zoku_kotei ");
			ret.append("  ,ISNULL(T120.cd_genryo,'') AS cd_genryo ");
			ret.append("  ,ISNULL(T120.nm_genryo,'') AS nm_genryo ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.tanka),'') AS tanka ");
			ret.append("  ,ISNULL(CONVERT(VARCHAR,T120.budomari),'') AS budomari ");
			ret.append("  ,ISNULL(T120.color,'') AS color ");
			ret.append("  ,T120.sort_kotei AS sort_kotei ");
			ret.append("  ,T120.sort_genryo AS sort_genryo ");
			ret.append(" FROM tr_shisakuhin T110 ");
			ret.append("  LEFT JOIN tr_haigo T120 ");
			ret.append("   ON  T120.cd_shain = T110.cd_shain ");
			ret.append("   AND T120.nen = T110.nen ");
			ret.append("   AND T120.no_oi = T110.no_oi ");
			ret.append("  LEFT JOIN ma_busho M104 ");
			ret.append("   ON  M104.cd_kaisha = T110.cd_kaisha ");
			ret.append("   AND M104.cd_busho = T110.cd_kojo ");
			ret.append("  LEFT JOIN ma_user M101_shain ");
			ret.append("   ON  M101_shain.id_user = T110.cd_shain ");
			ret.append(" WHERE  ");
			
			//����R�[�h�����������ɐݒ�
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);
			
			//�H��CD�E�H��SEQ�����������ɐݒ�
			ret.append(" AND ( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//���N�G�X�g�p�����[�^���A�H��CD�E�H��SEQ���擾����
				strCd_kotei = reqData.getFieldVale(0, i, "cd_kotei");
				strSeq_kotei = reqData.getFieldVale(0, i, "seq_kotei");
				
				//������ǉ�
				if ( i != 0 ) {
					ret.append("  OR ");
				}
				ret.append("   (T120.cd_kotei=" + strCd_kotei);
				ret.append("    AND T120.seq_kotei=" + strSeq_kotei + ")");
				
			}
			ret.append(" ) ");

			//�擾��
			ret.append(" ORDER BY T120.sort_kotei, T120.sort_genryo ");

			
		} catch (Exception e) {
			this.em.ThrowException(e, "����\�A�z���f�[�^����SQL�̐����Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strCd_kotei = null;
			strSeq_kotei = null;
	
		}
		return ret;
		
	}
	/**
	 * ���N�G�X�g�f�[�^���A���샊�X�g�f�[�^����SQL�𐶐�����
	 * @param reqData : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return StringBuffer : ����f�[�^����SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeShisakuListSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";
		String strCd_kotei = "";
		String strSeq_kotei = "";

		try {

			//���N�G�X�g�p�����[�^���A����R�[�h���擾����
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
	
			//SQL���̍쐬
			ret.append(" SELECT T132.seq_shisaku AS seq_shisaku ");
			ret.append("    ,T132.cd_kotei AS cd_kotei ");
			ret.append("    ,T132.seq_kotei AS seq_kotei ");
			ret.append("    ,ISNULL(CONVERT(VARCHAR,T132.quantity),'') AS quantity ");
			ret.append("    ,ISNULL(T132.color,'') AS color ");
			ret.append("  FROM tr_shisakuhin T110 ");
			ret.append("    LEFT JOIN tr_haigo T120 ");
			ret.append("     ON  T120.cd_shain = T110.cd_shain ");
			ret.append("     AND T120.nen = T110.nen ");
			ret.append("     AND T120.no_oi = T110.no_oi ");
			ret.append("    LEFT JOIN tr_shisaku T131 ");
			ret.append("     ON  T131.cd_shain = T110.cd_shain ");
			ret.append("     AND T131.nen = T110.nen ");
			ret.append("     AND T131.no_oi = T110.no_oi ");
			ret.append("    LEFT JOIN tr_shisaku_list T132 ");
			ret.append("     ON  T132.cd_shain = T110.cd_shain ");
			ret.append("     AND T132.nen = T110.nen ");
			ret.append("     AND T132.no_oi = T110.no_oi ");
			ret.append("     AND T132.seq_shisaku = T131.seq_shisaku ");
			ret.append("     AND T132.cd_kotei = T120.cd_kotei ");
			ret.append("     AND T132.seq_kotei = T120.seq_kotei ");
			ret.append(" WHERE  ");

			//����R�[�h�����������ɐݒ�
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);

			//����SEQ
			ret.append("  AND T132.seq_shisaku IN( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//���N�G�X�g�p�����[�^���A����SEQ���擾����
				strSeq_shisaku = reqData.getFieldVale(0, i, "seq_shisaku");
				
				//SQL���ɒǉ�
				if ( i != 0 ) {
					ret.append(",");
					
				}
				ret.append(strSeq_shisaku);
				
			}
			ret.append("  ) ");
			
			//�H��CD�E�H��SEQ�����������ɐݒ�
			ret.append(" AND ( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//���N�G�X�g�p�����[�^���A�H��CD�E�H��SEQ���擾����
				strCd_kotei = reqData.getFieldVale(0, i, "cd_kotei");
				strSeq_kotei = reqData.getFieldVale(0, i, "seq_kotei");
				
				//������ǉ�
				if ( i != 0 ) {
					ret.append("  OR ");
					
				}
				ret.append("   (T132.cd_kotei=" + strCd_kotei);
				ret.append("    AND T132.seq_kotei=" + strSeq_kotei + ")");
				
			}
			ret.append(" ) ");
			
			//�擾��
			ret.append(" ORDER BY T131.sort_shisaku, T120.sort_kotei, T120.sort_genryo ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����\�A���샊�X�g�f�[�^����SQL�̐����Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strSeq_shisaku = null;
			strCd_kotei = null;
			strSeq_kotei = null;
	
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
	private StringBuffer MakeShisakuSQLBuf(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();
		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";

		try {
			//���N�G�X�g�p�����[�^���A����R�[�h���擾����
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			
			//SQL���̍쐬
			ret.append(" SELECT  ");
			ret.append("    T131.seq_shisaku AS seq_shisaku ");
			ret.append("   ,ISNULL(CONVERT(VARCHAR,T131.dt_shisaku,111),'') AS dt_shisaku ");
			ret.append("   ,ISNULL(T131.nm_sample,'') AS nm_sample ");
			ret.append("   ,ISNULL(T131.memo,'') AS memo ");
			ret.append("   ,ISNULL(T131.memo_shisaku,'') AS memo_shisaku ");
			
			ret.append("   ,CASE WHEN T131.flg_memo=1 ");
			ret.append("     THEN ISNULL(T131.memo_sakusei,'') ");
			ret.append("     ELSE '' END AS memo_sakusei ");

			ret.append("   ,CASE WHEN T131.flg_hyoka=1 ");
			ret.append("     THEN ISNULL(T131.hyoka,'') ");
			ret.append("    ELSE '' END AS hyoka ");
			
			ret.append("   ,ISNULL(T133.chuijiko,'') AS chuijiko ");
			ret.append("   ,T131.sort_shisaku AS sort_shisaku ");
			ret.append(" FROM tr_shisakuhin T110 ");
			ret.append("  LEFT JOIN tr_shisaku T131 ");
			ret.append("   ON  T131.cd_shain = T110.cd_shain ");
			ret.append("   AND T131.nen = T110.nen ");
			ret.append("   AND T131.no_oi = T110.no_oi ");
			ret.append("  LEFT JOIN tr_cyuui T133 ");
			ret.append("   ON  T133.cd_shain = T110.cd_shain ");
			ret.append("   AND T133.nen = T110.nen ");
			ret.append("   AND T133.no_oi = T110.no_oi ");
			ret.append("   AND T133.no_chui = T131.no_chui ");
			ret.append(" WHERE  ");

			//����R�[�h�����������ɐݒ�
			ret.append("  T110.cd_shain=" + strCd_shain);
			ret.append("  AND T110.nen=" + strNen);
			ret.append("  AND T110.no_oi=" + strNo_oi);

			//����SEQ
			ret.append("  AND T131.seq_shisaku IN( ");
			for ( int i=0; i<reqData.getCntRow(0); i++ ) {
				
				//���N�G�X�g�p�����[�^���A����SEQ���擾����
				strSeq_shisaku = reqData.getFieldVale(0, i, "seq_shisaku");
				
				//SQL���ɒǉ�
				if ( i != 0 ) {
					ret.append(",");
					
				}
				ret.append(strSeq_shisaku);
				
			}
			ret.append("  ) ");
			
			//�擾��
			ret.append(" ORDER BY T131.sort_shisaku ");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����\�A����f�[�^����SQL�̐����Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strCd_shain = null;
			strNen = null;
			strNo_oi = null;
			strSeq_shisaku = null;
	
		}
		return ret;
		
	}
	
	/**
	 * �z���E���샊�X�g�f�[�^�̍쐬
	 * @param lstHaigoData : �z���f�[�^
	 * @param lstShisakuListData : ���샊�X�g�f�[�^
	 * @return �o�̓f�[�^
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> CreateHaigoShisakuListData(List<?> lstHaigoData, List<?> lstShisakuListData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		List<Object[]> ret = new ArrayList<Object[]>();
		
		//�ݒ�p�I�u�W�F�N�g
		Object[] add_items = null;
		Object[] haigo_items = null;
		Object[] shisakuList_items = null;
		//�H��CD�E�H��SEQ
		String strCd_kotei = null;
		String strSeq_kotei = null;
		
		//�ޔ�p�H��CD
		String strCd_kotei_taihi = "";
		//���샊�X�g�ݒ�p�J�E���g
		int intShisakuListCount = 0;
		
		try {
			//�z���f�[�^���X�g�̐ݒ�
			for (int i = 0; i < lstHaigoData.size(); i++ ) {
				
				//�z���f�[�^ �������ʎ擾
				haigo_items = (Object[]) lstHaigoData.get(i);
				
				//�ǉ��p�I�u�W�F�N�g����
				add_items = new Object[22];
				
				//��{���̐ݒ�
				add_items[0] = haigo_items[0].toString();			//����R�[�h
				add_items[1] = haigo_items[1].toString();			//�˗��ԍ�
				add_items[2] = haigo_items[2].toString();			//�i��
				add_items[3] = haigo_items[4].toString();			//��Ж�
				add_items[4] = haigo_items[5].toString();			//�H�ꖼ
				add_items[5] = haigo_items[6].toString();			//�����
				add_items[6] = haigo_items[7].toString();			//���s��
				add_items[7] = haigo_items[3].toString();			//��������
				
				//�z���f�[�^�������ʂ̐ݒ�
				
				// �H��CD�E�H��SEQ�̎擾
				strCd_kotei = haigo_items[8].toString();			//�H��CD
				strSeq_kotei = haigo_items[9].toString();			//�H��SEQ
				
				// �H���s�̐ݒ�
				if ( !strCd_kotei.equals(strCd_kotei_taihi) ) {
					//�H��CD��ޔ�
					strCd_kotei_taihi = strCd_kotei; 
										
					//�H��CD�Ƒޔ�p�H��CD���قȂ�ꍇ�A�u�H������ �H�����v��ݒ�
					add_items[8] = "";
					add_items[9] = haigo_items[11].toString() + " " + haigo_items[10].toString();
					for ( int j = 10; j < add_items.length; j++ ) {
						add_items[j] = "";
						
					}
					
					//���X�g��s�ǉ�
					ret.add(add_items);	
					
				}
				
				//�ǉ��p�I�u�W�F�N�g�Đ���
				add_items = new Object[22];
				
				//��{���̐ݒ�
				add_items[0] = haigo_items[0].toString();			//����R�[�h
				add_items[1] = haigo_items[1].toString();			//�˗��ԍ�
				add_items[2] = haigo_items[2].toString();			//�i��
				add_items[3] = haigo_items[3].toString();			//��Ж�
				add_items[4] = haigo_items[4].toString();			//�H�ꖼ
				add_items[5] = haigo_items[5].toString();			//�����
				add_items[6] = haigo_items[6].toString();			//���s��
				add_items[7] = haigo_items[7].toString();			//��������
				
				//�z���f�[�^�̐ݒ�
				add_items[8] = haigo_items[12].toString();		//�����R�[�h 
				add_items[9] = haigo_items[13].toString();		//������
				add_items[10] = haigo_items[14].toString();		//�P��
				add_items[11] = haigo_items[15].toString();		//����

				//���샊�X�g���ʂ��擾(10���ڂ̂�)
				intShisakuListCount = 0;
				for (int j = 0; intShisakuListCount < 10; j++ ) {
					
					//���샊�X�g.�ʂ�ݒ�
					if ( j < lstShisakuListData.size() ) {
						
						//���샊�X�g�f�[�^�@�������ʎ擾
						shisakuList_items = (Object[]) lstShisakuListData.get(j);
						
						//�z���f�[�^�Ǝ��샊�X�g�f�[�^�̍H��CD�E�H��SEQ��������
						if ( strCd_kotei.equals(shisakuList_items[1].toString()) && strSeq_kotei.equals(shisakuList_items[2].toString()) ) {
						
							//�ʂ�ݒ�
							add_items[intShisakuListCount + 12] = shisakuList_items[3].toString();

							//�J�E���g��i�߂�
							intShisakuListCount++;
							
						}
												
					} else {
						
						//�ʂ�ݒ�
						add_items[intShisakuListCount + 12] = "";
						
						//�J�E���g��i�߂�
						intShisakuListCount++;
						
					}
															
				}
				
				//���X�g��s�ǉ�
				ret.add(add_items);	
				
			}
			
		} catch(Exception e) {
			this.em.ThrowException(e, "����\�A�z���E���샊�X�g�f�[�^�̍쐬�����Ɏ��s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			add_items = null;
			haigo_items = null;
			shisakuList_items = null;
			strCd_kotei = null;
			strSeq_kotei = null;
			strCd_kotei_taihi = null;
			intShisakuListCount = 0;
			
		}
		
		return ret;
	}

	
}
