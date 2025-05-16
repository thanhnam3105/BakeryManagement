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
 * �T���v���������𐶐�����
 * @author isono
 * @since  2009/04/09
 */
public class SampleSetumeishoLogic extends LogicBaseExcel {
	
	/**
	 * �R���X�g���N�^
	 */
	public SampleSetumeishoLogic() {
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
		List<?> lstRecset1 = null;
		List<?> lstRecset2 = null;
		List<?> lstRecset3 = null;
		//�G�N�Z���t�@�C���p�X
		String DownLoadPath = "";
		
		try {
			//DB����
			super.createSearchDB();
			lstRecset1 = getData1(reqData);
			lstRecset2 = getData2(reqData);
			lstRecset3 = getData3(reqData);

			//Excel�t�@�C������
			DownLoadPath = makeExcelFile(lstRecset1,lstRecset2,lstRecset3);

			//���X�|���X�f�[�^����
			ret = CreateRespons(DownLoadPath);
			
		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������̐����Ɏ��s���܂����B");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset1);
			removeList(lstRecset2);
			removeList(lstRecset3);
			
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
			ret.setID("SA450");
			
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
	 * �T���v���������𐶐�����
	 * @param lstRecset : �����f�[�^���X�g
	 * @return�@String : �_�E�����[�h�p�̃p�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String makeExcelFile(List<?> lstRecset1, List<?> lstRecset2, List<?> lstRecset3) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String ret = "";
		
		try {
			//EXCEL�e���v���[�g��ǂݍ���
			super.ExcelReadTemplate("�T���v��������");

			//�T���v����ڂ̏o��
			if(lstRecset1 != null){
				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset1.size(); i++) {
					
					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset1.get(i);

					try{
						//Excel�ɒl���Z�b�g����
						//�i��
						super.ExcelSetValue("�i��1", toString(items[12]));
						//�T���v���ԍ�
						super.ExcelSetValue("�r�d�p1", toString(items[3]));
						//�i�R�[�h
						super.ExcelSetValue("�i�R�[�h1", toString(items[4]));
						//������
						super.ExcelSetValue("������1", toString(items[5]));
						//�z��
						super.ExcelSetValue("�z��1", toString(items[6]));
						//����
						super.ExcelSetValue("����1", toString(items[7]));
						//�\���ď��
						super.ExcelSetValue("�\���ď��1", toString(items[8]));
						//�Y�������
						super.ExcelSetValue("�Y�������1", toString(items[9]));
						//�h�{�v�Z�p�@�H�i�ԍ�
						super.ExcelSetValue("�h�{�v�Z�p�@�H�i�ԍ�1", toString(items[10]));
						//����
						super.ExcelSetValue("����1", toString(items[11]));
						
					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;
						
					}finally{
						
					}
					
				}

			}

			//�T���v���Q�ڂ̏o��
			if(lstRecset1 != null){
				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset2.size(); i++) {
					
					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset2.get(i);

					try{
						//Excel�ɒl���Z�b�g����
						//�i��
						super.ExcelSetValue("�i��2", toString(items[12]));
						//�T���v���ԍ�
						super.ExcelSetValue("�r�d�p2", toString(items[3]));
						//�i�R�[�h
						super.ExcelSetValue("�i�R�[�h2", toString(items[4]));
						//������
						super.ExcelSetValue("������2", toString(items[5]));
						//�z��
						super.ExcelSetValue("�z��2", toString(items[6]));
						//����
						super.ExcelSetValue("����2", toString(items[7]));
						//�\���ď��
						super.ExcelSetValue("�\���ď��2", toString(items[8]));
						//�Y�������
						super.ExcelSetValue("�Y�������2", toString(items[9]));
						//�h�{�v�Z�p�@�H�i�ԍ�
						super.ExcelSetValue("�h�{�v�Z�p�@�H�i�ԍ�2", toString(items[10]));
						//����
						super.ExcelSetValue("����2", toString(items[11]));
						
					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;
						
					}finally{
						
					}
					
				}

			}

			//�T���v���R�ڂ̏o��
			if(lstRecset1 != null){
				//�_�E�����[�h�p��EXCEL�𐶐�����
				for (int i = 0; i < lstRecset3.size(); i++) {
					
					//�������ʂ�1�s�������o��
					Object[] items = (Object[]) lstRecset3.get(i);

					try{
						//Excel�ɒl���Z�b�g����
						//�i��
						super.ExcelSetValue("�i��3", toString(items[12]));
						//�T���v���ԍ�
						super.ExcelSetValue("�r�d�p3", toString(items[3]));
						//�i�R�[�h
						super.ExcelSetValue("�i�R�[�h3", toString(items[4]));
						//������
						super.ExcelSetValue("������3", toString(items[5]));
						//�z��
						super.ExcelSetValue("�z��3", toString(items[6]));
						//����
						super.ExcelSetValue("����3", toString(items[7]));
						//�\���ď��
						super.ExcelSetValue("�\���ď��3", toString(items[8]));
						//�Y�������
						super.ExcelSetValue("�Y�������3", toString(items[9]));
						//�h�{�v�Z�p�@�H�i�ԍ�
						super.ExcelSetValue("�h�{�v�Z�p�@�H�i�ԍ�3", toString(items[10]));
						//����
						super.ExcelSetValue("����3", toString(items[11]));
						
					}catch(ExceptionWaning e){
						//�ő�s���𒴂���ƁAExceptionWaning��Throw�����̂Ń��[�v���I������B
						break;
						
					}finally{
						
					}
					
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
	 * ����i�f�[�^(�T���v���P��)����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData1(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//��������
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";
		
		try {

			//����iCD�@�Ј��R�[�h
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//����No�i�T���v���ԍ��j
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku1");
			
			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������T���v���P�ADB�����Ɏ��s���܂����B");

		} finally {

		}
		return ret;
		
	}
	/**
	 * ����i�f�[�^(�T���v���Q��)����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData2(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//��������
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";
		
		try {

			//����iCD�@�Ј��R�[�h
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//����No�i�T���v���ԍ��j
			strSeq_shisaku = reqData.getFieldVale(0, 0, "seq_shisaku2");
			
			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������T���v���Q�ADB�����Ɏ��s���܂����B");

		} finally {

		}
		return ret;
		
	}
	/**
	 * ����i�f�[�^(�T���v���R��)����������
	 * @param KindBean : ���N�G�X�g�f�[�^�i�@�\�j
	 * @return List : �������ʂ̃��X�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �Y���f�[�^����
	 */
	private List<?> getData3(RequestResponsKindBean reqData) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//��������
		List<?> ret = null;

		String strCd_shain = "";
		String strNen = "";
		String strNo_oi = "";
		String strSeq_shisaku = "";
		
		try {

			//����iCD�@�Ј��R�[�h
			strCd_shain = reqData.getFieldVale(0, 0, "cd_shain");
			//����iNo�@�N
			strNen = reqData.getFieldVale(0, 0, "nen");
			//����iNo�@�ǔ�
			strNo_oi = reqData.getFieldVale(0, 0, "no_oi");
			//����No�i�T���v���ԍ��j
			strSeq_shisaku = toString(reqData.getFieldVale(0, 0, "seq_shisaku3"));
			
			if (strSeq_shisaku != ""){
				ret = getData(strCd_shain, strNen, strNo_oi,strSeq_shisaku);
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������T���v���R�ADB�����Ɏ��s���܂����B");

		} finally {

		}
		return ret;
		
	}
	/**
	 * �Ώۂ̎���i�f�[�^����������
	 * @param strCd_shain : ����Ј�cd
	 * @param strNen : ����N
	 * @param strNo_oi : �ǔ�
	 * @param strSeq_shisaku : ����No�i�T���v���ԍ��j
	 * @return list : ��������
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> getData(String strCd_shain, String strNen,String strNo_oi,String strSeq_shisaku) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//��������
		List<?> ret = null;
		//SQL�@StringBuffer
		StringBuffer strSql = new StringBuffer();
		
		try {
			//SQL���̍쐬
			strSql = MakeSQLBuf(strCd_shain,strNen,strNo_oi,strSeq_shisaku);
			
			//SQL�����s
			ret = searchDB.dbSearch(strSql.toString());
			
			//�������ʂ��Ȃ��ꍇ
			if (ret.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "�T���v���������ADB�����Ɏ��s���܂����B");

		} finally {
			//�ϐ��̍폜
			strSql = null;
		}
		return ret;
		
	}
	/**
	 * ���N�G�X�g�f�[�^���A����f�[�^����SQL�𐶐�����
	 * @param strCd_shain : ����Ј�cd
	 * @param strNen : ����N
	 * @param strNo_oi : �ǔ�
	 * @param strSeq_shisaku : ����No�i�T���v���ԍ��j
	 * @return String : sql
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer MakeSQLBuf(String strCd_shain,	String strNen,String strNo_oi, String strSeq_shisaku) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
	
		StringBuffer ret = new StringBuffer();

		try {

			//SQL���̍쐬	
			ret.append(" SELECT ");
			ret.append("  A.cd_shain AS ����ԍ�_�Ј�CD ");
			ret.append(" , A.nen AS ����ԍ�_�N ");
			ret.append(" , A.no_oi AS ����ԍ�_�ǔ� ");
			ret.append(" , A.seq_shisaku AS ����SEQ ");
			ret.append(" , D.cd_genryo AS �i�R�[�h ");
			ret.append(" , D.nm_genryo AS ������ ");
			ret.append(" , C.quantity AS �z�� ");
			ret.append(" , D.budomari AS ���� ");
			ret.append(" , E.hyojian AS �\���ď�� ");
			ret.append(" , E.tenkabutu AS �Y������� ");
			ret.append(" , E.no_eiyo1 ");
			ret.append(" + ',' +  E.no_eiyo2  ");
			ret.append(" + ',' +  E.no_eiyo3  ");
			ret.append(" + ',' +  E.no_eiyo4  ");
			ret.append(" + ',' +  E.no_eiyo5 AS �h�{�v�Z�p�H�i�ԍ� ");
			ret.append(" , E.memo AS ���� ");
			ret.append(" , B.nm_hin AS �i�� ");
			ret.append(" FROM  ");
			ret.append(" tr_shisaku AS A ");
			ret.append(" LEFT JOIN tr_shisakuhin AS B ");
			ret.append(" ON A.cd_shain = B.cd_shain ");
			ret.append(" AND A.nen = B.nen ");
			ret.append(" AND A.no_oi = B.no_oi ");
			ret.append(" LEFT JOIN tr_shisaku_list AS C ");
			ret.append(" ON A.cd_shain = C.cd_shain ");
			ret.append(" AND A.nen = C.nen ");
			ret.append(" AND A.no_oi = C.no_oi ");
			ret.append(" AND A.seq_shisaku = C.seq_shisaku ");
			ret.append(" LEFT JOIN tr_haigo AS D ");
			ret.append(" ON C.cd_shain = D.cd_shain ");
			ret.append(" AND C.nen = D.nen ");
			ret.append(" AND C.no_oi = D.no_oi ");
			ret.append(" AND C.cd_kotei = D.cd_kotei ");
			ret.append(" AND C.seq_kotei = D.seq_kotei ");
			ret.append(" LEFT JOIN ma_genryo AS E ");
			ret.append(" ON B.cd_kaisha = E.cd_kaisha ");
			ret.append(" AND D.cd_genryo = E.cd_genryo ");
			ret.append(" WHERE  ");
			ret.append("     A.cd_shain = " + strCd_shain + " ");
			ret.append(" AND A.nen = " + strNen + " ");
			ret.append(" AND A.no_oi = " + strNo_oi + " ");
			ret.append(" AND A.seq_shisaku = " + strSeq_shisaku + " ");
			ret.append(" AND ISNULL(C.quantity,0) > 0 ");
	
		} catch (Exception e) {
			this.em.ThrowException(e, "�T���v���������A����SQL�̐����Ɏ��s���܂����B");
			
		} finally {
	
		}
		return ret;
		
	}

}