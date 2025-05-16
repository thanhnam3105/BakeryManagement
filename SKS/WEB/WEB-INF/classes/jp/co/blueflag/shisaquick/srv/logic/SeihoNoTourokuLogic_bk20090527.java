package jp.co.blueflag.shisaquick.srv.logic;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.SearchBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UpdateBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionBase;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * ����f�[�^��� �o�^(���@�R�s�[)����
 * @author k-katayama
 * @since  2009/05/21
 */
public class SeihoNoTourokuLogic extends LogicBase{

	private SearchBaseDao searchDB_Seiho = null;
	private UpdateBaseDao execDB_Seiho = null;

	/**
	 * �R���{�p�F�J�e�S����񌟍�DB�����p�R���X�g���N�^ 
	 * : �C���X�^���X����
	 */
	public SeihoNoTourokuLogic() {
		//���N���X�̃R���X�g���N�^
		super();
		
		//�C���X�^���X�ϐ��̏�����
		searchDB_Seiho = null;
		execDB_Seiho = null;
	}

	/**
	 * ����f�[�^��� �o�^(���@�R�s�[)����
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

		List<?> lstRecset = null;

		RequestResponsKindBean resKind = null;

		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//���O���̃N���A
			deleteLogInfo(reqData);
			
			//***** �f�[�^���擾 ***************
			//����f�[�^�̎擾
			lstRecset = serchShisakuInfo(reqData, lstRecset);
			
			//�z���R�[�h�̎擾
			String strHaigoCd = serchHaigoCd(reqData, lstRecset);

			//���@�ԍ��̎擾
			String strSeihoNo = serchSeihoNo(reqData, lstRecset);
			
			//�H��}�X�^�̎擾
			Object[] itemsKojo = serchKojoMst(reqData, lstRecset);
			
			//***** ���@�x���f�[�^��o�^ ***************
			insertSeiho(reqData, lstRecset, itemsKojo, strHaigoCd, strSeihoNo);

			//***** �V�T�N�C�b�N�f�[�^���X�V ***************
			insertShisaku(reqData, lstRecset, strSeihoNo);
					
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = ((RequestResponsTableBean) reqData.GetItem(0)).getID();
			resKind.addTableItem(strTableNm);

			//����I�����A�Ǘ����ʃp�����[�^�[�i�[���\�b�h���ďo���A���X�|���X�f�[�^���`������B
			storageSeihoNo(resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
		}
		
		return resKind;
	}
	
	/**
	 * ����f�[�^�擾����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param lstRecset : �������ʊi�[���X�g
	 * @return �������ʊi�[���X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private List<?> serchShisakuInfo(
			RequestResponsKindBean reqData,
			List<?> lstRecset
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSql = new StringBuffer();
		
		try{
			//����f�[�^�擾�����pSQL���̍쐬���s��
			strSql = createSearchSQL(reqData, strSql);
			
			//�f�[�^�x�[�X������p���A����f�[�^���擾����
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//�Ώۃf�[�^�����݂��Ȃ��ꍇ
			if (lstRecset.size() == 0) {
				em.ThrowException(ExceptionKind.�x��Exception,"W000403", "", "", "");
			}
			
		}catch(Exception e){
			//�����������s
			this.em.ThrowException(e, "");
		} finally {
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}
			
			//�ϐ��̍폜
			strSql = null;
		}
		
		return lstRecset;
	}
	
	/**
	 * �z���R�[�h�擾����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param lstRecset : �������ʊi�[���X�g
	 * @return �������ʊi�[���X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private String serchHaigoCd(
			RequestResponsKindBean reqData,
			List<?> lstRecset
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecsetNo = null;
		
		String strHaigoCd = "";
		
		try{
			Object itemsNo = null;;

			//�Z�b�V�����̐ݒ�
			searchDB_Seiho = new SearchBaseDao(ConstManager.CONST_XML_PATH_DB2);
			
			//�z���R�[�h�擾�����pSQL���̍쐬���s��
			strSql = createGetHaigoCdSQL(reqData, strSql);

			//�f�[�^�x�[�X������p���A�z���R�[�h���擾����
			lstRecsetNo = searchDB_Seiho.dbSearch(strSql.toString());
			itemsNo = (Object) lstRecsetNo.get(0);
			strHaigoCd = itemsNo.toString();
			
		}catch(Exception e){
			//�����������s
			this.em.ThrowException(e, "");
		} finally {
			//���X�g�̔p��
			removeList(lstRecsetNo);
			if (searchDB_Seiho != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB_Seiho.Close();
				searchDB_Seiho = null;
			}

			//�ϐ��̍폜
			strSql = null;
		}
		
		return strHaigoCd;
	}
	
	/**
	 * ���@�ԍ��擾����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param lstRecset : �������ʊi�[���X�g
	 * @return �������ʊi�[���X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private String serchSeihoNo(
			RequestResponsKindBean reqData,
			List<?> lstRecset
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecsetNo = null;
		
		String strSeihoNo = "";
		
		try{
			Object[] items = (Object[]) lstRecset.get(0);
			Object itemsNo = null;

			//�Z�b�V�����̐ݒ�
			searchDB_Seiho = new SearchBaseDao(ConstManager.CONST_XML_PATH_DB2);
			
			//���@�ԍ��擾�����pSQL���̍쐬���s��
			strSql = createGetSeihoNoSQL(reqData, strSql, items[13].toString());
			
			//�f�[�^�x�[�X������p���A���@�ԍ����擾����
			lstRecsetNo = searchDB_Seiho.dbSearch(strSql.toString());
			itemsNo = (Object) lstRecsetNo.get(0);
			strSeihoNo = itemsNo.toString();
			
		}catch(Exception e){
			//�����������s
			this.em.ThrowException(e, "");
		} finally {
			//���X�g�̔p��
			removeList(lstRecsetNo);
			if (searchDB_Seiho != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB_Seiho.Close();
				searchDB_Seiho = null;
			}

			//�ϐ��̍폜
			strSql = null;
		}
		
		return strSeihoNo;
	}
	
	/**
	 * �H��}�X�^�擾����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param lstRecset : �������ʊi�[���X�g
	 * @return �������ʊi�[���X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private Object[] serchKojoMst(
			RequestResponsKindBean reqData,
			List<?> lstRecset
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strSql = new StringBuffer();
		List<?> lstRecsetKojo = null;
		
		Object[] itemsKojo = null;
		
		try{
			Object[] items = (Object[]) lstRecset.get(0);
			
			//�Z�b�V�����̐ݒ�
			searchDB_Seiho = new SearchBaseDao(ConstManager.CONST_XML_PATH_DB2);
			
			//�H��}�X�^�擾�����pSQL���̍쐬���s��
			strSql = createKojoSQL(reqData, strSql, items);

			//�f�[�^�x�[�X������p���A�H��}�X�^���擾����
			lstRecsetKojo = searchDB_Seiho.dbSearch(strSql.toString());
			itemsKojo = (Object[]) lstRecsetKojo.get(0);

		}catch(Exception e){
			//�����������s
			this.em.ThrowException(e, "");
		} finally {
			//���X�g�̔p��
			removeList(lstRecsetKojo);
			if (searchDB_Seiho != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB_Seiho.Close();
				searchDB_Seiho = null;
			}

			//�ϐ��̍폜
			strSql = null;
		}
		
		return itemsKojo;
	}
	
	/**
	 * ���@�x���ւ̓o�^����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param lstRecset : �������ʊi�[���X�g
	 * @return �������ʊi�[���X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertSeiho(
			RequestResponsKindBean reqData,
			List<?> lstRecset,
			Object[] itemsKojo,
			String strHaigoCd,
			String strSeihoNo
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		ExceptionBase excBase = null; 

		StringBuffer strSql_Ins1 = new StringBuffer();	//�z���w�b�_�o�^�p
		StringBuffer strSql_Ins2 = new StringBuffer();	//�z�����דo�^�p
		StringBuffer strSql_Ins3 = new StringBuffer();	//���@�}�X�^�o�^�p
		StringBuffer strSql_Ins4 = new StringBuffer();	//���@�`���}�X�^�o�^�p
		StringBuffer strSql_Ins5 = new StringBuffer();	//�������C���}�X�^�o�^�p

		String strTableName = "";
		Object[] items = null;
		
		try{
			//�g�����U�N�V�������J�n����
			execDB_Seiho = new UpdateBaseDao(ConstManager.CONST_XML_PATH_DB2);
			this.execDB_Seiho.BeginTran();

			//***** ���@�x���V�X�e���ւ̃f�[�^�o�^ ***************
			//�z���w�b�_�o�^�����pSQL���̍쐬���s��
			items = (Object[]) lstRecset.get(0);
			strSql_Ins1 = createHaigoHeaderSQL(reqData, strSql_Ins1, items, itemsKojo, strHaigoCd, strSeihoNo);

			//�z���w�b�_�o�^�pSQL���s
			strTableName = "ma_haigo_header";
			execDB_Seiho.execSQL(strSql_Ins1.toString());

			//�V�T�N�C�b�N�V�X�e���ւ̃��O�o��
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//�����f�[�^���A���׃f�[�^��o�^����
			for (int i = 0; i < lstRecset.size(); i++) {
				//�z�����דo�^�����pSQL���̍쐬���s��
				items = (Object[]) lstRecset.get(i);
				strSql_Ins2 = createHaigoMeisaiSQL(reqData, items, strHaigoCd);

				//�z�����דo�^�pSQL���s
				strTableName = "ma_haigo_meisai";
				execDB_Seiho.execSQL(strSql_Ins2.toString());

				//�V�T�N�C�b�N�V�X�e���ւ̃��O�o��
				insertLogInfo(reqData, items, strSeihoNo, strTableName, "");
			}

			//���@�}�X�^�o�^�����pSQL���̍쐬���s��
			items = (Object[]) lstRecset.get(0);
			strSql_Ins3 = createSeihoSQL(reqData, strSql_Ins3, items, strSeihoNo);

			//���@�}�X�^�o�^�pSQL���s
			strTableName = "ma_seiho";
			execDB_Seiho.execSQL(strSql_Ins3.toString());

			//�V�T�N�C�b�N�V�X�e���ւ̃��O�o��
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//���@�`���}�X�^�o�^�����pSQL���̍쐬���s��
			items = (Object[]) lstRecset.get(0);
			strSql_Ins4 = createSeihoDensoSQL(reqData, strSql_Ins4, items, strSeihoNo);

			//���@�`���}�X�^�o�^�pSQL���s
			strTableName = "ma_seiho_denso";
			execDB_Seiho.execSQL(strSql_Ins4.toString());

			//�V�T�N�C�b�N�V�X�e���ւ̃��O�o��
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//�������C���}�X�^�o�^�����pSQL���̍쐬���s��
			items = (Object[]) lstRecset.get(0);
			strSql_Ins5 = createSeizoLineSQL(reqData, strSql_Ins5, items, itemsKojo, strHaigoCd);

			//�������C���}�X�^�o�^�pSQL���s
			strTableName = "ma_seizo_line";
			execDB_Seiho.execSQL(strSql_Ins5.toString());

			//�V�T�N�C�b�N�V�X�e���ւ̃��O�o��
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//�R�~�b�g���������s����
			execDB_Seiho.Commit();
			
		}catch(Exception e){
			//���[���o�b�N���������s����
			execDB_Seiho.Rollback();
			
			excBase = this.em.cnvException(e, "");

			//�V�T�N�C�b�N�V�X�e���ւ̃��O�o��
			insertLogInfo(reqData, items, strSeihoNo, strTableName, excBase.getSystemErrorMsg());

			//�o�^�������s
			this.em.ThrowException();
		} finally {
			if (execDB_Seiho != null) {
				//�Z�b�V�����̃N���[�Y
				execDB_Seiho.Close();
				execDB_Seiho = null;
			}

			//�ϐ��̍폜
			strSql_Ins1 = null;
			strSql_Ins2 = null;
			strSql_Ins3 = null;
			strSql_Ins4 = null;
			strSql_Ins5 = null;
		}
		
	}
	
	/**
	 * �V�T�N�C�b�N�ւ̓o�^����
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param lstRecset : �������ʊi�[���X�g
	 * @return �������ʊi�[���X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertShisaku(
			RequestResponsKindBean reqData,
			List<?> lstRecset,
			String strSeihoNo
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		ExceptionBase excBase = null; 

		StringBuffer strSql_Upd1 = new StringBuffer();	//����e�[�u���X�V�p
		StringBuffer strSql_Upd2 = new StringBuffer();	//����i�e�[�u���X�V�p

		String strTableName = "";
		Object[] items = null;
		
		try{
			//�g�����U�N�V�������J�n����
			super.createExecDB();
			this.execDB.BeginTran();

			items = (Object[]) lstRecset.get(0);

			//����e�[�u���X�V�����pSQL���̍쐬���s��
			strSql_Upd1 = createShisakuUpdateSQL(reqData, strSql_Upd1, strSeihoNo);

			//����e�[�u���X�V�pSQL���s
			strTableName = "tr_shisaku";
			this.execDB.execSQL(strSql_Upd1.toString());

			//�V�T�N�C�b�N�V�X�e���ւ̃��O�o��
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//����i�e�[�u���X�V�����pSQL���̍쐬���s��
			strSql_Upd2 = createShisakuhinUpdateSQL(reqData, strSql_Upd2);

			//����i�e�[�u���X�V�pSQL���s
			strTableName = "tr_shisakuhin";
			this.execDB.execSQL(strSql_Upd2.toString());

			//�V�T�N�C�b�N�V�X�e���ւ̃��O�o��
			insertLogInfo(reqData, items, strSeihoNo, strTableName, "");

			//�R�~�b�g���������s����
			this.execDB.Commit();
			
		}catch(Exception e){
			//���[���o�b�N���������s����
			this.execDB.Rollback();

			excBase = this.em.cnvException(e, "");

			//�V�T�N�C�b�N�V�X�e���ւ̃��O�o��
			insertLogInfo(reqData, items, strSeihoNo, strTableName, excBase.getSystemErrorMsg());

			//�X�V�������s
			this.em.ThrowException();
		} finally {
			if (execDB != null) {
				//�Z�b�V�����̃N���[�Y
				execDB.Close();
				execDB = null;
			}
			
			//�ϐ��̍폜
			strSql_Upd1 = null;
			strSql_Upd2 = null;
		}
		
	}
	
	/**
	 * ����f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSearchSQL(
			RequestResponsKindBean reqData, 
			StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String strShainCd = "";
		String strNen = "";
		String strOiNo = "";
		String strShisakuSeq = "";
		
		try {
			//�p�����[�^�̎擾
			strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strOiNo = reqData.getFieldVale(0, 0, "no_oi");
			strShisakuSeq = reqData.getFieldVale(0, 0, "seq_shisaku");

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  TBL.nm_hin");
			strSql.append(" ,TBL.cd_kaisha");
			strSql.append(" ,TBL.cd_kojo");
			strSql.append(" ,TBL.id_koshin");
			strSql.append(" ,TBL.dt_toroku");
			strSql.append(" ,TBL.dt_koshin");
			strSql.append(" ,TBL.cd_kotei");
			strSql.append(" ,TBL.nm_kotei");
			strSql.append(" ,TBL.no_sort");
			strSql.append(" ,TBL.cd_genryo");
			strSql.append(" ,TBL.nm_genryo");
			strSql.append(" ,TBL.quantity ");
			strSql.append(" ,TBL.juryo_shiagari_g");
			strSql.append(" ,TBL.cd_shubetu");
			strSql.append(" ,TBL.nm_sample");
			strSql.append(" ,GTBL.quantity_g");
			strSql.append(" FROM (");
			strSql.append(" SELECT");
			strSql.append("  T110.nm_hin");
			strSql.append(" ,T110.cd_kaisha");
			strSql.append(" ,T110.cd_kojo");
			strSql.append(" ,T110.id_koshin");
			strSql.append(" ,T110.dt_toroku");
			strSql.append(" ,T110.dt_koshin");
			strSql.append(" ,T120.sort_kotei");
			strSql.append(" ,T120.cd_kotei");
			strSql.append(" ,T120.nm_kotei");
			strSql.append(" ,1 as no_sort");
			strSql.append(" ,'' as cd_genryo");
			strSql.append(" ,'' as nm_genryo");
			strSql.append(" ,NULL as quantity ");
			strSql.append(" ,T131.juryo_shiagari_g");
			strSql.append(" ,T110.cd_shubetu + RIGHT('00' + CONVERT(VARCHAR,T110.no_shubetu),2) as cd_shubetu");
			strSql.append(" ,T131.nm_sample");
			strSql.append(" FROM");
			strSql.append("  tr_shisakuhin T110");
			strSql.append("  INNER JOIN tr_haigo T120");
			strSql.append("  ON T110.cd_shain  = T120.cd_shain ");
			strSql.append("  AND T110.nen = T120.nen");
			strSql.append("  AND T110.no_oi = T120.no_oi");
			strSql.append("  INNER JOIN tr_shisaku T131");
			strSql.append("  ON T110.cd_shain  = T131.cd_shain ");
			strSql.append("  AND T110.nen = T131.nen");
			strSql.append("  AND T110.no_oi = T131.no_oi");
			strSql.append("  AND T131.seq_shisaku = " + strShisakuSeq);
			strSql.append(" WHERE T110.cd_shain = " + strShainCd);
			strSql.append(" AND T110.nen = " + strNen);
			strSql.append(" AND T110.no_oi = " + strOiNo);
			strSql.append(" UNION");
			strSql.append(" SELECT");
			strSql.append("  T110.nm_hin");
			strSql.append(" ,T110.cd_kaisha");
			strSql.append(" ,T110.cd_kojo");
			strSql.append(" ,T110.id_koshin");
			strSql.append(" ,T110.dt_toroku");
			strSql.append(" ,T110.dt_koshin");
			strSql.append(" ,T120.sort_kotei");
			strSql.append(" ,T120.cd_kotei");
			strSql.append(" ,T120.nm_kotei");
			strSql.append(" ,Row_Number() over(partition by T120.cd_kotei order by T120.cd_kotei,T120.sort_genryo)+1 as no_sort");
			strSql.append(" ,T120.cd_genryo");
			strSql.append(" ,T120.nm_genryo");
			strSql.append(" ,T132.quantity ");
			strSql.append(" ,T131.juryo_shiagari_g");
			strSql.append(" ,T110.cd_shubetu + RIGHT('00' + CONVERT(VARCHAR,T110.no_shubetu),2) as cd_shubetu");
			strSql.append(" ,T131.nm_sample");
			strSql.append(" FROM");
			strSql.append("  tr_shisakuhin T110");
			strSql.append("  INNER JOIN tr_haigo T120");
			strSql.append("  ON T110.cd_shain  = T120.cd_shain ");
			strSql.append("  AND T110.nen = T120.nen");
			strSql.append("  AND T110.no_oi = T120.no_oi");
			strSql.append("  INNER JOIN tr_shisaku T131");
			strSql.append("  ON T110.cd_shain  = T131.cd_shain ");
			strSql.append("  AND T110.nen = T131.nen");
			strSql.append("  AND T110.no_oi = T131.no_oi");
			strSql.append("  AND T131.seq_shisaku = " + strShisakuSeq);
			strSql.append("  INNER JOIN tr_shisaku_list T132");
			strSql.append("  ON T120.cd_shain  = T132.cd_shain ");
			strSql.append("  AND T120.nen = T132.nen");
			strSql.append("  AND T120.no_oi = T132.no_oi");
			strSql.append("  AND T120.cd_kotei = T132.cd_kotei");
			strSql.append("  AND T120.seq_kotei = T132.seq_kotei");
			strSql.append("  AND T132.seq_shisaku = " + strShisakuSeq);
			strSql.append(" WHERE T110.cd_shain = " + strShainCd);
			strSql.append(" AND T110.nen = " + strNen);
			strSql.append(" AND T110.no_oi = " + strOiNo);
			strSql.append(" ) TBL");
			strSql.append(" ,(SELECT SUM(ISNULL(quantity,0)) as quantity_g");		
			strSql.append("   FROM tr_shisaku_list");		
			strSql.append("   WHERE cd_shain = " + strShainCd);
			strSql.append("   AND nen = " + strNen);
			strSql.append("   AND no_oi = " + strOiNo);
			strSql.append("   AND seq_shisaku = " + strShisakuSeq);
			strSql.append("   GROUP BY");
			strSql.append("    cd_shain ");
			strSql.append("   ,nen ");
			strSql.append("   ,no_oi ");
			strSql.append(" ) GTBL");
			strSql.append(" ORDER BY");
			strSql.append(" TBL.sort_kotei, TBL.no_sort");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^�擾SQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}

	/**
	 * ����f�[�^�X�VSQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F�X�VSQL
	 * @param strSeihoNo�F���@�ԍ�
	 * @return strSql�F�X�VSQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createShisakuUpdateSQL(
			RequestResponsKindBean reqData, 
			StringBuffer strSql,
			String strSeihoNo) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String strShainCd = "";
		String strNen = "";
		String strOiNo = "";
		String strShisakuSeq = "";
		
		try {
			//�p�����[�^�̎擾
			strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strOiNo = reqData.getFieldVale(0, 0, "no_oi");
			strShisakuSeq = reqData.getFieldVale(0, 0, "seq_shisaku");

			//SQL���̍쐬
			strSql.append("UPDATE tr_shisaku SET");
			strSql.append("  no_seiho1 = '" + strSeihoNo + "'");
			strSql.append(" ,no_seiho2 = no_seiho1");
			strSql.append(" ,no_seiho3 = no_seiho2");
			strSql.append(" ,no_seiho4 = no_seiho3");
			strSql.append(" ,no_seiho5 = no_seiho4");
			strSql.append(" ,id_koshin = " + userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_shain = " + strShainCd);
			strSql.append(" AND nen = " + strNen);
			strSql.append(" AND no_oi = " + strOiNo);
			strSql.append(" AND seq_shisaku = " + strShisakuSeq);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^�X�VSQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}

	/**
	 * ����i�f�[�^�X�VSQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F�X�VSQL
	 * @return strSql�F�X�VSQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createShisakuhinUpdateSQL(
			RequestResponsKindBean reqData, 
			StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String strShainCd = "";
		String strNen = "";
		String strOiNo = "";
		String strShisakuSeq = "";
		
		try {
			//�p�����[�^�̎擾
			strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strOiNo = reqData.getFieldVale(0, 0, "no_oi");
			strShisakuSeq = reqData.getFieldVale(0, 0, "seq_shisaku");

			//SQL���̍쐬
			strSql.append("UPDATE tr_shisakuhin SET");
			strSql.append("  seq_shisaku = " + strShisakuSeq);
			strSql.append(" ,id_koshin = " + userInfoData.getId_user());
			strSql.append(" ,dt_koshin = GETDATE()");
			strSql.append(" WHERE cd_shain = " + strShainCd);
			strSql.append(" AND nen = " + strNen);
			strSql.append(" AND no_oi = " + strOiNo);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����i�f�[�^�X�VSQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}

	/**
	 * ���O���폜����
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void deleteLogInfo(
			RequestResponsKindBean reqData) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		UpdateBaseDao execDB_Log = null;
		StringBuffer strSql = new StringBuffer();
		
		try {
			//�g�����U�N�V�����̊J�n
			execDB_Log = new UpdateBaseDao(ConstManager.CONST_XML_PATH_DB1);
			execDB_Log.BeginTran();
			
			//SQL���̍쐬
			strSql.append("DELETE");
			strSql.append(" FROM wk_log");
			strSql.append(" WHERE id_user = ");
			strSql.append(userInfoData.getId_user());
			
			//SQL�̎��s
			execDB_Log.execSQL(strSql.toString());

			//�R�~�b�g
			execDB_Log.Commit();

		} catch (Exception e) {
			//���[���o�b�N
			execDB_Log.Rollback();
			
			this.em.ThrowException(e, "���O���폜���������s���܂����B");
		} finally {
			if (execDB_Log != null) {
				//�Z�b�V�����̃N���[�Y
				execDB_Log.Close();
				execDB_Log = null;
			}
			
			//�ϐ��̍폜
			strSql = null;
		}
	}

	/**
	 * ���O���o�^����
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSeihoNo�F���@�ԍ�
	 * @param strTableName�F�e�[�u����
	 * @param strGenryoCd�F�����R�[�h
	 * @param strErrMsg�F�G���[���b�Z�[�W
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertLogInfo(
			RequestResponsKindBean reqData, 
			Object[] items, 
			String strSeihoNo,
			String strTableName,
			String strErrMsg) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		UpdateBaseDao execDB_Log = null;
		StringBuffer strSql = new StringBuffer();
		
		String strShainCd = "";
		String strNen = "";
		String strOiNo = "";
		String strShisakuSeq = "";
		
		try {
			//�p�����[�^�̎擾
			strShainCd = reqData.getFieldVale(0, 0, "cd_shain");
			strNen = reqData.getFieldVale(0, 0, "nen");
			strOiNo = reqData.getFieldVale(0, 0, "no_oi");
			strShisakuSeq = reqData.getFieldVale(0, 0, "seq_shisaku");

			//�g�����U�N�V�����̊J�n
			execDB_Log = new UpdateBaseDao(ConstManager.CONST_XML_PATH_DB1);
			execDB_Log.BeginTran();
			
			//SQL���̍쐬
			strSql.append("INSERT INTO wk_log (");
			strSql.append("  id_user");
			strSql.append(" ,dt_system");
			strSql.append(" ,cd_shain");
			strSql.append(" ,nen");
			strSql.append(" ,no_oi");
			strSql.append(" ,seq_shisaku");
			strSql.append(" ,nm_sample");
			strSql.append(" ,no_seiho");
			strSql.append(" ,nm_table");		
			strSql.append(" ,cd_kotei");
			strSql.append(" ,cd_genryo");
			strSql.append(" ,err_msg");
			strSql.append(" ) VALUES (");
			strSql.append(userInfoData.getId_user());
			strSql.append(" ,GETDATE()");
			strSql.append(" ," + strShainCd);
			strSql.append(" ," + strNen);
			strSql.append(" ," + strOiNo);
			strSql.append(" ," + strShisakuSeq);
			strSql.append(" ,'" + items[14].toString() + "'");
			strSql.append(" ,'" + strSeihoNo + "'");
			strSql.append(" ,'" + strTableName + "'");
			if (strTableName.equals("ma_haigo_meisai")) {
				strSql.append(" ,'" + items[6].toString() + "'");
				strSql.append(" ,'" + items[9].toString() + "'");
			} else {
				strSql.append(" ,''");
				strSql.append(" ,''");
			}
			strSql.append(" ,'" + strErrMsg + "'");
			strSql.append(" )");
			
			//SQL�̎��s
			execDB_Log.execSQL(strSql.toString());

			//�R�~�b�g
			execDB_Log.Commit();

		} catch (Exception e) {
			//���[���o�b�N
			execDB_Log.Rollback();
			
			this.em.ThrowException(e, "���O���o�^���������s���܂����B");
		} finally {
			if (execDB_Log != null) {
				//�Z�b�V�����̃N���[�Y
				execDB_Log.Close();
				execDB_Log = null;
			}
			
			//�ϐ��̍폜
			strSql = null;
		}
	}

	/**
	 * �z���R�[�h�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createGetHaigoCdSQL(
			RequestResponsKindBean reqData, 
			StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  ISNULL(MAX(cd_haigo)+1,1) as cd_haigo");
			strSql.append(" FROM");
			strSql.append("  ma_haigo_header");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�z���R�[�h�擾SQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}

	/**
	 * ���@�ԍ��擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @param strShubetuCd�F��ʃR�[�h
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createGetSeihoNoSQL(
			RequestResponsKindBean reqData, 
			StringBuffer strSql,
			String strShubetuCd) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		String strNen = "";
		
		try {
			//�p�����[�^�̎擾
			strNen = reqData.getFieldVale(0, 0, "nen");

			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  RIGHT('0000' + CONVERT(VARCHAR," + userInfoData.getCd_kaisha() + "),4) + '-' + ");
			strSql.append("  '" + strShubetuCd + "-' + ");
			strSql.append("  '" + strNen + "-' + ");
			strSql.append("  RIGHT('0000' + CONVERT(VARCHAR,ISNULL(CONVERT(int,substring(MAX(no_seiho),13,4)),0)+1),4) as no_seiho");
			strSql.append(" FROM");
			strSql.append("  ma_seiho");
			strSql.append(" WHERE substring(no_seiho,1,4) = RIGHT('0000' + CONVERT(VARCHAR," + userInfoData.getCd_kaisha() + "),4)");
			strSql.append(" AND substring(no_seiho,6,3) = '" + strShubetuCd + "'");
			strSql.append(" AND substring(no_seiho,10,2) = '" + strNen + "'");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���@�ԍ��擾SQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}


	/**
	 * �H��}�X�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @param items�F����f�[�^�̌�������
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createKojoSQL(
			RequestResponsKindBean reqData, 
			StringBuffer strSql,
			Object[] items) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//SQL���̍쐬
			strSql.append("SELECT");
			strSql.append("  budomari");
			strSql.append(" ,qty_kihon");
			strSql.append(" ,ritsu_kihon");
			strSql.append(" ,cd_setsubi");
			strSql.append(" ,qty_max");
			strSql.append(" ,hijyu");
			strSql.append(" ,cd_line");
			strSql.append(" FROM");
			strSql.append("  ma_kojyo");
			strSql.append(" WHERE cd_kaisha = " + items[1].toString());
			strSql.append(" AND cd_kojyo = " + items[2].toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�H��}�X�^�擾SQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}

	/**
	 * �z���w�b�_�o�^SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F�o�^SQL
	 * @param items�F����f�[�^�̌�������
	 * @param itemsKojo�F�H��}�X�^�̌�������
	 * @param strHaigoCd�F�z���R�[�h�擾����
	 * @param strSeihoNo�F���@�ԍ�
	 * @return strSql�F�o�^SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createHaigoHeaderSQL(
			RequestResponsKindBean reqData, 
			StringBuffer strSql,
			Object[] items, 
			Object[] itemsKojo,
			String strHaigoCd, 
			String strSeihoNo) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//SQL���̍쐬
			strSql.append("INSERT INTO ma_haigo_header (");
			strSql.append("  cd_haigo");
			strSql.append(" ,nm_haigo");
			strSql.append(" ,nm_haigo_r");
			strSql.append(" ,cd_kaisha_daihyo");
			strSql.append(" ,cd_kojyo_daihyo");
			strSql.append(" ,kbn_hin");
			strSql.append(" ,cd_bunrui");
			strSql.append(" ,budomari");
			strSql.append(" ,qty_kihon");
			strSql.append(" ,ritsu_kihon");
			strSql.append(" ,cd_setsubi");
			strSql.append(" ,flg_gasan");
			strSql.append(" ,qty_max");
			strSql.append(" ,qty_haigo_kei");
			strSql.append(" ,biko");
			strSql.append(" ,no_seiho");
			strSql.append(" ,cd_kaisha");
			strSql.append(" ,kbn_vw");
			strSql.append(" ,hijyu");
			strSql.append(" ,flg_mishiyo");
			strSql.append(" ,kbn_haishi");
			strSql.append(" ,kbn_shiagari");
			strSql.append(" ,status");
			strSql.append(" ,cd_seiho_bunrui");
			strSql.append(" ,no_seiho_sanko");
			strSql.append(" ,dt_toroku");
			strSql.append(" ,cd_toroku_kaisha");
			strSql.append(" ,cd_toroku");
			strSql.append(" ,dt_henko");
			strSql.append(" ,cd_koshin_kaisha");
			strSql.append(" ,cd_koshin");
			strSql.append(" ,cd_haigo_sanko");
			strSql.append(" ,cd_dcp_aoh");
			strSql.append(" ,cd_mxt_aoh");
			strSql.append(" ,kbn_cnv_aoh");
			strSql.append(" ) VALUES (");
			strSql.append("  " + strHaigoCd);
			strSql.append(" ,'" + items[0].toString() + "'");
			strSql.append(" ,NULL");
			strSql.append(" ," + items[1].toString());
			strSql.append(" ," + items[2].toString());
			strSql.append(" ,3");//���m�F���E���ݒ�
			strSql.append(" ,NULL");
			strSql.append(" ," + itemsKojo[0].toString());//�H��}�X�^�Fbudomari
			strSql.append(" ," + itemsKojo[1].toString());//�H��}�X�^�Fqty_kihon
			strSql.append(" ," + itemsKojo[2].toString());//�H��}�X�^�Fritsu_kihon
			strSql.append(" ," + itemsKojo[3].toString());//�H��}�X�^�Fcd_setsubi
			strSql.append(" ,0");
			strSql.append(" ," + itemsKojo[4].toString());//�H��}�X�^�Fqty_max
			if (items[12] != null) {
				//�d�オ��d�ʂ����͂���Ă���ꍇ
				strSql.append(" ," + items[12].toString());//�d�オ��d��
			} else {
				//�d�オ��d�ʂ����͂���Ă��Ȃ��ꍇ
				strSql.append(" ," + items[15].toString());//�z���̍��v�d��
			}
			strSql.append(" ,NULL");
			strSql.append(" ,'" + strSeihoNo + "'");
			strSql.append(" ," + userInfoData.getCd_kaisha());
			strSql.append(" ,'04'");
			strSql.append(" ," + itemsKojo[5].toString());//�H��}�X�^�Fhijyu
			strSql.append(" ,0");
			strSql.append(" ,0");
			if (items[12] != null) {
				//�d�オ��d�ʂ����͂���Ă���ꍇ
				strSql.append(" ,1");
			} else {
				//�d�オ��d�ʂ����͂���Ă��Ȃ��ꍇ
				strSql.append(" ,0");
			}
			strSql.append(" ,0");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,'" + items[4].toString() + "'");
			strSql.append(" ," + userInfoData.getCd_kaisha());
			strSql.append(" ,'" + userInfoData.getId_user() + "'");
			strSql.append(" ,'" + items[5].toString() + "'");
			strSql.append(" ," + userInfoData.getCd_kaisha());
			strSql.append(" ,'" + userInfoData.getId_user() + "'");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" )");
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�z���w�b�_�o�^SQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}

	/**
	 * �z�����דo�^SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param items�F����f�[�^�̌�������
	 * @param strHaigoCd�F�z���R�[�h�擾����
	 * @return strSql�F�o�^SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createHaigoMeisaiSQL(
			RequestResponsKindBean reqData, 
			Object[] items, 
			String strHaigoCd) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSql = new StringBuffer();

		try {
			//SQL���̍쐬
			strSql.append("INSERT INTO ma_haigo_meisai (");
			strSql.append("  cd_haigo");
			strSql.append(" ,no_kotei");
			strSql.append(" ,no_tonyu");
			strSql.append(" ,cd_hin");
			strSql.append(" ,flg_shitei");
			strSql.append(" ,kbn_hin");
			strSql.append(" ,kbn_shikakari");
			strSql.append(" ,nm_hin");
			strSql.append(" ,cd_mark");
			strSql.append(" ,qty_haigo");
			strSql.append(" ,qty_nisugata");
			strSql.append(" ,gosa");
			strSql.append(" ,budomari");
			strSql.append(" ,kbn_bunkatsu");
			strSql.append(" ) VALUES (");
			strSql.append("  " + strHaigoCd);
			strSql.append(" ," + items[6].toString());
			strSql.append(" ," + items[8].toString());
			//�sNo��"1"�̏ꍇ
			if (items[8].toString().equals("1")) {
				//�H��
				strSql.append(" ,999999");
				strSql.append(" ,0");
				strSql.append(" ,9");
				strSql.append(" ,NULL");
				strSql.append(" ,'" + items[7].toString() + "'");
				strSql.append(" ,18");
				strSql.append(" ,NULL");
				strSql.append(" ,NULL");
				strSql.append(" ,NULL");
				strSql.append(" ,NULL");
			} else {
				//����
				strSql.append(" ," + items[9].toString());
				strSql.append(" ,0");
				strSql.append(" ,1");
				strSql.append(" ,0");
				strSql.append(" ,'" + items[10].toString() + "'");
				strSql.append(" ,0");
				if (items[11] != null) {
					strSql.append(" ," + items[11].toString());
				} else {
					strSql.append(" ,NULL");
				}
				strSql.append(" ,NULL");
				strSql.append(" ,NULL");
				strSql.append(" ,100");
			}
			strSql.append(" ,0");
			strSql.append(" )");
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�z�����דo�^SQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}

	/**
	 * ���@�}�X�^�o�^SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F�o�^SQL
	 * @param items�F����f�[�^�̌�������
	 * @param strSeihoNo�F���@�ԍ�
	 * @return strSql�F�o�^SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSeihoSQL(
			RequestResponsKindBean reqData, 
			StringBuffer strSql,
			Object[] items, 
			String strSeihoNo) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//SQL���̍쐬
			strSql.append("INSERT INTO ma_seiho (");
			strSql.append("  no_seiho");
			strSql.append(" ,nm_seiho");
			strSql.append(" ,dt_seiho_sakusei");
			strSql.append(" ,nm_seiho_sakusei_1");
			strSql.append(" ,nm_seiho_sakusei_2");
			strSql.append(" ,nm_seiho_bunsho_before");
			strSql.append(" ,cd_shinsei_tanto_kaisha");
			strSql.append(" ,cd_shinsei_tanto");
			strSql.append(" ,nm_seiho_sekinin");
			strSql.append(" ,dt_seiho_shinsei");
			strSql.append(" ) VALUES (");
			strSql.append("  '" + strSeihoNo + "'");
			strSql.append(" ,'" + items[0].toString() + "'");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" )");		
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���@�}�X�^�o�^SQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}

	/**
	 * ���@�`���}�X�^�o�^SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F�o�^SQL
	 * @param items�F����f�[�^�̌�������
	 * @param strSeihoNo�F���@�ԍ�
	 * @return strSql�F�o�^SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSeihoDensoSQL(
			RequestResponsKindBean reqData, 
			StringBuffer strSql,
			Object[] items, 
			String strSeihoNo) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//SQL���̍쐬
			strSql.append("INSERT INTO ma_seiho_denso (");
			strSql.append("  no_seiho");
			strSql.append(" ,cd_kaisha");
			strSql.append(" ,cd_kojyo");
			strSql.append(" ,flg_denso_taisho");
			strSql.append(" ,flg_denso_jyotai");
			strSql.append(" ,dt_denso_toroku");
			strSql.append(" ,dt_denso_kanryo");
			strSql.append(" ,flg_daihyo_kojyo");
			strSql.append(" ,cd_denso_tanto_kaisha");
			strSql.append(" ,cd_denso_tanto");
			strSql.append(" ,biko");
			strSql.append(" ) VALUES (");
			strSql.append("  '" + strSeihoNo + "'");
			strSql.append(" ," + userInfoData.getCd_kaisha());
			strSql.append(" ," + items[2].toString());
			strSql.append(" ,0");
			strSql.append(" ,0");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,1");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" ,NULL");
			strSql.append(" )");
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���@�`���}�X�^�o�^SQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}

	/**
	 * �������C���}�X�^�o�^SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F�o�^SQL
	 * @param items�F����f�[�^�̌�������
	 * @param itemsKojo�F�H��}�X�^�̌�������
	 * @param strHaigoCd�F�z���R�[�h�擾����
	 * @return strSql�F�o�^SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createSeizoLineSQL(
			RequestResponsKindBean reqData, 
			StringBuffer strSql,
			Object[] items, 
			Object[] itemsKojo,
			String strHaigoCd) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//SQL���̍쐬
			strSql.append("INSERT INTO ma_seizo_line (");
			strSql.append("  cd_haigo");
			strSql.append(" ,no_yusen");
			strSql.append(" ,cd_line");
			strSql.append(" ,dt_henko");
			strSql.append(" ,cd_koshin_kaisha");
			strSql.append(" ,cd_koshin");
			strSql.append(" ) VALUES (");
			strSql.append("  " + strHaigoCd);
			strSql.append(" ,1");
			strSql.append(" ," + itemsKojo[6].toString());//�H��}�X�^�Fcd_line
			strSql.append(" ,GETDATE()");
			strSql.append(" ," + userInfoData.getCd_kaisha());
			strSql.append(" ,'" + items[3].toString() + "'");
			strSql.append(" )");
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�������C���}�X�^�o�^SQL�쐬���������s���܂����B");
		} finally {
		}
		return strSql;
	}

	/**
	 * �p�����[�^�[�i�[
	 *  : �������X�|���X�f�[�^�֊i�[����B
	 * @param resTable : ���X�|���X�e�[�u�����
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageSeihoNo(RequestResponsTableBean resTable) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�p�����[�^�[�i�[���������s���܂����B");
		} finally {

		}
	}
	
}
