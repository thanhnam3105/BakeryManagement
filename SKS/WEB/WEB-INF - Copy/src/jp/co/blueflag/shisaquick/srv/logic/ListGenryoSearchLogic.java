package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsRowBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * 
 * ���쌴������DB����
 *  : ���N�G�X�g�f�[�^��p���A���쌴���������oSQL���쐬����B
 * �擾�����A�@�\���X�|���X�f�[�^�ێ��u�@�\ID�FSA570�v�ɐݒ肷��B
 * 
 * @author TT.katayama
 * @since 2009/04/13
 */
public class ListGenryoSearchLogic extends LogicBase {

	/**
	 * ���쌴�������R���X�g���N�^ : �C���X�^���X����
	 */
	public ListGenryoSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���쌴���f�[�^�擾
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

		//���N�G�X�g�f�[�^
		String strKinoId = "";
		String strTableNm = "";

		StringBuffer strSql = new StringBuffer();
		List<?> lstGenryoSearchData = null;
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@�F�����}�X�^ �����pSQL�쐬���\�b�h�Ăяo��SQL���擾����B
			strSql = new StringBuffer();
			strSql = this.createGenryoMstSQL(reqData);
			
			//�A�F�f�[�^�x�[�X������p���A�����}�X�^ �������ʂ��擾����B
			super.createSearchDB();
			lstGenryoSearchData = searchDB.dbSearch(strSql.toString());

			//�B�F ���N�G�X�g�f�[�^�ƌ������ʂ��r���A���͒l�ύX�m�F�f�[�^���擾����
			lstRecset = setGenryoBunsekiData(reqData, lstGenryoSearchData);
			
			//�������ʂ��Ȃ��ꍇ
			if (lstRecset.size() == 0){
				em.ThrowException(ExceptionKind.�x��Exception,"W000401", strSql.toString(), "", "");
				
			}

			//�C�F���쌴���p�����[�^�[�i�[���\�b�h���ďo���A���X�|���X�f�[�^���`������B
			
			// �@�\ID�̐ݒ�
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			// �e�[�u�����̐ݒ�
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// ���X�|���X�f�[�^�̌`��
			storageBunsekiHenko(lstRecset, resKind.getTableItem(0),reqData);

		} catch (Exception e) {
			this.em.ThrowException(e, "���쌴���f�[�^�擾���������s���܂����B");
			
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
				
			}

			//�ϐ��̍폜
			strKinoId = null;
			strTableNm = null;
			strSql = null;
			lstRecset = null;
			
		}
		//�D�F�@�\���X�|���X�f�[�^��ԋp����B
		return resKind;
		
	}

	
	/**
	 * �����}�X�^ �����pSQL�쐬
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @return �쐬SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createGenryoMstSQL(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		StringBuffer strRetSQL = new StringBuffer();
		StringBuffer strSQL_where = new StringBuffer();		//���������p
		int intRecCount = 0;
		
		//�@�F���N�G�X�g�f�[�^���A�����}�X�^�����擾����SQL���쐬����B		
		try {

			//M401[ma_genryo, ma_genryokojo]���擾
			strRetSQL.append(" SELECT DISTINCT ");
			strRetSQL.append("   M401.cd_kaisha AS cd_kaisha ");
			strRetSQL.append("   ,ISNULL(M402.cd_busho,'') AS cd_busho ");
			strRetSQL.append("   ,M401.cd_genryo AS cd_genryo ");
			
//			strRetSQL.append("   ,ISNULL(M402.nm_genryo,'���������݂��܂���') AS nm_genryo ");
			strRetSQL.append("   ,ISNULL(M402_geryoname.nm_genryo,'���������݂��܂���') AS nm_genryo ");
			
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
			strRetSQL.append("   ,ISNULL(M401.hyojian,'') AS hyojian ");
			strRetSQL.append("   ,ISNULL(M401.tenkabutu,'') AS tenkabutu ");
			strRetSQL.append("   ,ISNULL(M401.memo,'') AS memo ");
			strRetSQL.append("   ,ISNULL(M401.no_eiyo1,'') AS no_eiyo1 ");
			strRetSQL.append("   ,ISNULL(M401.no_eiyo2,'') AS no_eiyo2 ");
			strRetSQL.append("   ,ISNULL(M401.no_eiyo3,'') AS no_eiyo3 ");
			strRetSQL.append("   ,ISNULL(M401.no_eiyo4,'') AS no_eiyo4 ");
			strRetSQL.append("   ,ISNULL(M401.no_eiyo5,'') AS no_eiyo5 ");
			strRetSQL.append("   ,ISNULL(M401.wariai1,'') AS wariai1 ");
			strRetSQL.append("   ,ISNULL(M401.wariai2,'') AS wariai2 ");
			strRetSQL.append("   ,ISNULL(M401.wariai3,'') AS wariai3 ");
			strRetSQL.append("   ,ISNULL(M401.wariai4,'') AS wariai4 ");
			strRetSQL.append("   ,ISNULL(M401.wariai5,'') AS wariai5 ");
			
			//�p�~�敪����
			strRetSQL.append("  ,M401.kbn_haishi AS kbn_haishi ");
			strRetSQL.append("  ,CASE M401.kbn_haishi ");
			strRetSQL.append("    WHEN 0 THEN '�g�p�\' ");
			strRetSQL.append("    WHEN 1 THEN '�p�~' END as nm_haishi ");
			
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.cd_kakutei),'') AS cd_kakutei ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.id_kakunin),'') AS id_kakunin ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.dt_kakunin,111),'') AS dt_kakunin ");			
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS budomari ");
			strRetSQL.append("  ,ISNULL(M402.nisugata,'') AS nisugata ");
			strRetSQL.append("  ,ISNULL(M402.kikakusho,'') AS kikakusho ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.dt_konyu,111),'') AS dt_konyu ");
			
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.13
//			strRetSQL.append("  ,M401.id_toroku AS id_toroku ");
//			strRetSQL.append("  ,CONVERT(VARCHAR,M401.dt_toroku,111) AS dt_toroku ");
			strRetSQL.append("  ,M401.id_koshin AS id_koshin ");
			strRetSQL.append("  ,CONVERT(VARCHAR,M401.dt_koshin,111) AS dt_koshin ");
//mod end --------------------------------------------------------------------------------------

			//�������E���ו\�������i���}���j
			strRetSQL.append("  ,'' AS max_row ");
			strRetSQL.append("  ,'' AS list_max_row ");
			
			//�m�F�ҁE���͎�
			strRetSQL.append("  ,ISNULL( M101_kakunin.nm_user, '') AS nm_kakunin ");
			strRetSQL.append("  ,ISNULL( M101_nyuryoku.nm_user, '') AS nm_koshin ");
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
			//�g�p���сE���g�p
			strRetSQL.append("  ,ISNULL( M402.flg_shiyo, '') AS flg_shiyo ");
			strRetSQL.append("  ,ISNULL( M402.flg_mishiyo, '') AS flg_mishiyo ");
//add end --------------------------------------------------------------------------------------
			
			// ADD start 20121025 QP@20505 No.24
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_msg),'') AS ritu_msg ");
			// ADD end 20121025 QP@20505 No.24
			
			strRetSQL.append(" FROM ma_genryo AS M401 ");
			strRetSQL.append("  LEFT JOIN ma_genryokojo AS M402 ");
			strRetSQL.append("   ON  M402.cd_kaisha = M401.cd_kaisha ");
			strRetSQL.append("   AND M402.cd_genryo = M401.cd_genryo ");
			
			
			strRetSQL.append("  AND M402.cd_busho =" + reqData.getFieldVale(0, 0, "cd_busho"));
			//strRetSQL.append("  OR M402.cd_busho = 0");
			
			
			strRetSQL.append("  LEFT JOIN ma_user AS M101_kakunin ");
			strRetSQL.append("   ON M101_kakunin.id_user = M401.id_kakunin ");
			strRetSQL.append("  LEFT JOIN ma_user AS M101_nyuryoku ");
			//mod start --------------------------------------------------------------------------------------
			//QP@00412_�V�T�N�C�b�N���� No.13
//						strRetSQL.append("   ON M101_nyuryoku.id_user = M401.id_toroku ");
						strRetSQL.append("   ON M101_nyuryoku.id_user = M401.id_koshin ");
			//mod end --------------------------------------------------------------------------------------			
			
			strRetSQL.append("  LEFT JOIN ma_genryokojo AS M402_geryoname ");
			strRetSQL.append("   ON  M402_geryoname.cd_kaisha = M401.cd_kaisha ");
			strRetSQL.append("   AND M402_geryoname.cd_genryo = M401.cd_genryo ");
			
			

			
			
			//�@�\���N�G�X�g�f�[�^�����擾
			intRecCount = reqData.getCntRow(0);
			
			//����������ݒ�
			for ( int i=0 ; i<intRecCount; i++ ) {
				
				//���N�G�X�g�p�����[�^�̎擾
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");

//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
//				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
//				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
//mod end --------------------------------------------------------------------------------------
				
				String strReqGenryoCd = reqData.getFieldVale(0, i, "cd_genryo");
				
				//���������̒ǉ�
				if ( strSQL_where.length() == 0 ) {
					strSQL_where.append(" WHERE ");
					
				} else {
					strSQL_where.append(" OR ");
					
				}
				strSQL_where.append("  (  ");
				strSQL_where.append("  (M401.cd_kaisha =" + strReqKaishaCd);
				strSQL_where.append("  AND M401.cd_genryo ='" + strReqGenryoCd + "' ) ");
				
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
				//strSQL_where.append("  AND M402.cd_busho =" + strReqBushoCd);
				//strSQL_where.append("  AND M402.cd_busho =" + strReqBushoCd);
//				strSQL_where.append("  OR ( M402.cd_kaisha = 1 AND M402.cd_busho = 0 AND M401.cd_genryo ='" + strReqGenryoCd + "')");
							
//mod end --------------------------------------------------------------------------------------
				
				strSQL_where.append("  )  ");
				
			}
			strRetSQL.append(strSQL_where.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���쌴���f�[�^��񌟍��pSQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL_where = null;
			
		}
		//�A:�쐬����SQL��ԋp
		return strRetSQL;
		
	}
	
	/**
	 * ���쌴���f�[�^�ݒ�
	 *  �F ���N�G�X�g�f�[�^�ƌ����������ʂ��r���A�������ʂ����݂��邩�ǂ����m�F����B
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param lstGenryoSearchData : ������������
	 * @return ���쌴���f�[�^ 
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> setGenryoBunsekiData(RequestResponsKindBean reqData, List<?> lstGenryoSearchData)
	    throws ExceptionSystem, ExceptionUser, ExceptionWaning {		
		
		ArrayList<Object[]> lstChkBunsekiData = new ArrayList<Object[]>();
		ArrayList<Object[]> lstRetBunsekiData = new ArrayList<Object[]>();
		int intRecCount = 0;
		int intListRowMax = 0;
		int intReqSelectRow = 0;
		Object[] data = null;
		boolean blnDeleteChk = true;
	    
		try {

			//�ő�\�����ڐ���ݒ�
			intListRowMax = Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX"));

			//�I���y�[�W�ԍ��̎擾
			intReqSelectRow = Integer.parseInt(reqData.getFieldVale(0, 0, "num_selectRow"));
			
			//���N�G�X�g�f�[�^���̎擾
			intRecCount = reqData.getCntRow(0);	

			//���N�G�X�g�f�[�^�ƌ����������ʂ��r���A�ݒ�
			for ( int i=0 ; i<intRecCount; i++ ) {

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
//				data = new Object[37];
//QP@20505 MOD start 20121030 No.24
//				data = new Object[39];
				data = new Object[40];
//QP@20505 MOD end 20121030 No.24
//add end --------------------------------------------------------------------------------------
				
				//���N�G�X�g�p�����[�^�̎擾
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");
				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
				String strReqGenryoCd = reqData.getFieldVale(0, i, "cd_genryo");

				for (int j = 0; j < lstGenryoSearchData.size(); j++) {
					
					//�������ʂ��擾
					Object[] items = (Object[]) lstGenryoSearchData.get(j);

					//�L�[����(��ЃR�[�h�A�����R�[�h�A�����R�[�h)���r����
					if ( (strReqKaishaCd.equals(items[0].toString())
							//&& strReqBushoCd.equals(items[1].toString())
							&& strReqGenryoCd.equals(items[2].toString())) ) {

						//�����f�[�^�����݂���ꍇ
						blnDeleteChk = false;
						data = items;
						break;
						
					} else {
						//�����f�[�^�����݂��Ȃ��ꍇ
						blnDeleteChk = true;
						
					}
					
				}
				
				//���݃`�F�b�N
				if ( blnDeleteChk ) {
					//���݂��Ȃ��ꍇ					
					data[0] = strReqKaishaCd;
					data[1] = strReqBushoCd;
					data[2] = strReqGenryoCd;
					data[3] = "���������݂��܂���";
					for ( int j=4; j<data.length; j++ ) {
						data[j] = "";	
						
					}
					
				}
				
				//��������ݒ�
				data[33] = intRecCount;

				//���ו\��������ݒ�
				data[34] = intListRowMax;
				
				//�f�[�^���i�[
				lstRetBunsekiData.add(data);
				
			}
			
//			//�y�[�W��������(�Ώۃy�[�W�̃��X�g��\��)
//			for ( int i = (intReqSelectRow-1) * intListRowMax; i < (((intReqSelectRow-1) * intListRowMax) + intListRowMax); ++i ) {
//				
//				if ( i < lstChkBunsekiData.size() ) {
//					//�f�[�^���i�[
//					lstRetBunsekiData.add(lstChkBunsekiData.get(i));
//					
//				} else {
//					//�\�������I�[�o�[���́A���[�v���甲����
//					break;
//					
//				}
//				
//			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���쌴���f�[�^�ݒ菈�������s���܂����B");
			
		} finally {
			//���X�g�J��
			removeList(lstGenryoSearchData);
			removeList(lstChkBunsekiData);
			
			//�ϐ��폜
			intRecCount = 0;
			intListRowMax = 0;
			intReqSelectRow = 0;
			data = null;
			
		}
		return lstRetBunsekiData;
		
	}

	/**
	 * ���쌴���p�����[�^�[�i�[
	 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA570O�v�ɐݒ肷��B
	 * @param lstGenryoData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageBunsekiHenko(List<?> lstGenryoData
			,RequestResponsTableBean resTable
			,RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		Object[] items = null;
		Object[] tgItems = null;
		
		//�@�F�����@�������ʏ�񃊃X�g�ɕێ����Ă���e�p�����[�^�[���@�\���X�|���X�f�[�^�֊i�[����B

		try {
			
			//���N�G�X�g�f�[�^���s�ԍ��Ń\�[�g����
			List<Object> tglist = reqData.getTableItem(0).getItemList();

			Collections.sort(tglist, new Comparator<Object>() {
				public int compare(Object tc1, Object tc2) {
					int tc1Name = 0;
					int tc2Name = 0;
					try {

						tc1Name = Integer.parseInt(((RequestResponsRowBean) tc1).getFieldVale("gyo_no"));
						tc2Name =  Integer.parseInt(((RequestResponsRowBean) tc2).getFieldVale("gyo_no"));

					} catch (Exception e) {

					}
					if (tc1Name < tc2Name) {
						return -1;
					} else if (tc1Name > tc2Name) {
						return 1;
					}
					return 0;
				}
			});
			
			//�y�[�W��������(�Ώۃy�[�W�̃��X�g��\��)
			//�ő�\�����ڐ���ݒ�
			int intListRowMax = Integer.parseInt(ConstManager.getConstValue(ConstManager.Category.�ݒ�,"LIST_ROW_MAX"));
			//�I���y�[�W�ԍ��̎擾
			int intReqSelectRow = Integer.parseInt(reqData.getFieldVale(0, 0, "num_selectRow"));
			ArrayList<RequestResponsRowBean> rettglist = new ArrayList<RequestResponsRowBean>();
			
			for ( int i = (intReqSelectRow-1) * intListRowMax; i < (((intReqSelectRow-1) * intListRowMax) + intListRowMax); ++i ) {
				
				if ( i < tglist.size() ) {
					//�f�[�^���i�[
					rettglist.add((RequestResponsRowBean)tglist.get(i));
					
				} else {
					//�\�������I�[�o�[���́A���[�v���甲����
					break;
					
				}
				
			}
			
			//���N�G�X�g�̌������A���X�|���X�𐶐�����
			for(int ix = 0; ix < rettglist.size(); ix++){

				//���N�G�X�g���ꂽ�����ɊY������f�[�^����������
				for (int i = 0; i < lstGenryoData.size(); i++) {
					
					items = (Object[]) lstGenryoData.get(i);
					tgItems = null;

					if (toString(((RequestResponsRowBean) rettglist.get(ix)).getFieldVale("cd_genryo")).equals(toString(items[2]))){
						//�Y������f�[�^������ꍇ
						tgItems = items;
						break;
						
					}
					
				}
				
				//
				if ( tgItems != null ) {
					//�������ʂ̊i�[
					resTable.addFieldVale("rec" + ix, "flg_return", "true");
					resTable.addFieldVale("rec" + ix, "msg_error", "");
					resTable.addFieldVale("rec" + ix, "no_errmsg", "");
					resTable.addFieldVale("rec" + ix, "nm_class", "");
					resTable.addFieldVale("rec" + ix, "cd_error", "");
					resTable.addFieldVale("rec" + ix, "msg_system", "");
					
					//���ʂ����X�|���X�f�[�^�Ɋi�[
					resTable.addFieldVale("rec" + ix, "cd_kaisha", tgItems[0].toString());
					resTable.addFieldVale("rec" + ix, "cd_busho", tgItems[1].toString());
					resTable.addFieldVale("rec" + ix, "cd_genryo", tgItems[2].toString());
					resTable.addFieldVale("rec" + ix, "nm_genryo", tgItems[3].toString());
					resTable.addFieldVale("rec" + ix, "ritu_sakusan", tgItems[4].toString());
					resTable.addFieldVale("rec" + ix, "ritu_shokuen", tgItems[5].toString());
					resTable.addFieldVale("rec" + ix, "ritu_sousan", tgItems[6].toString());
					resTable.addFieldVale("rec" + ix, "ritu_abura", tgItems[7].toString());
					resTable.addFieldVale("rec" + ix, "hyojian", tgItems[8].toString());
					resTable.addFieldVale("rec" + ix, "tenkabutu", tgItems[9].toString());
					resTable.addFieldVale("rec" + ix, "memo", tgItems[10].toString());
					resTable.addFieldVale("rec" + ix, "no_eiyo1", tgItems[11].toString());
					resTable.addFieldVale("rec" + ix, "no_eiyo2", tgItems[12].toString());
					resTable.addFieldVale("rec" + ix, "no_eiyo3", tgItems[13].toString());
					resTable.addFieldVale("rec" + ix, "no_eiyo4", tgItems[14].toString());
					resTable.addFieldVale("rec" + ix, "no_eiyo5", tgItems[15].toString());
					resTable.addFieldVale("rec" + ix, "wariai1", tgItems[16].toString());
					resTable.addFieldVale("rec" + ix, "wariai2", tgItems[17].toString());
					resTable.addFieldVale("rec" + ix, "wariai3", tgItems[18].toString());
					resTable.addFieldVale("rec" + ix, "wariai4", tgItems[19].toString());
					resTable.addFieldVale("rec" + ix, "wariai5", tgItems[20].toString());
					resTable.addFieldVale("rec" + ix, "kbn_haishi", tgItems[21].toString());
					resTable.addFieldVale("rec" + ix, "nm_haishi", tgItems[22].toString());
					resTable.addFieldVale("rec" + ix, "cd_kakutei", tgItems[23].toString());
					resTable.addFieldVale("rec" + ix, "id_kakunin", tgItems[24].toString());
					resTable.addFieldVale("rec" + ix, "nm_kakunin", tgItems[35].toString());
					resTable.addFieldVale("rec" + ix, "dt_kakunin", tgItems[25].toString());
					resTable.addFieldVale("rec" + ix, "tanka", tgItems[26].toString());
					resTable.addFieldVale("rec" + ix, "budomari", tgItems[27].toString());
					resTable.addFieldVale("rec" + ix, "nisugata", tgItems[28].toString());
					resTable.addFieldVale("rec" + ix, "kikakusho", tgItems[29].toString());
					resTable.addFieldVale("rec" + ix, "dt_konyu", tgItems[30].toString());
					resTable.addFieldVale("rec" + ix, "id_koshin", tgItems[31].toString());
					resTable.addFieldVale("rec" + ix, "nm_koshin", tgItems[36].toString());
					resTable.addFieldVale("rec" + ix, "dt_koshin", tgItems[32].toString());
					resTable.addFieldVale("rec" + ix, "max_row", tgItems[33].toString());
					resTable.addFieldVale("rec" + ix, "list_max_row", tgItems[34].toString());
//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
					resTable.addFieldVale("rec" + ix, "flg_shiyo", tgItems[37].toString());
					resTable.addFieldVale("rec" + ix, "flg_mishiyo", tgItems[38].toString());
//add end --------------------------------------------------------------------------------------
					
					// ADD start 20121025 QP@20505 No.24
					resTable.addFieldVale("rec" + ix, "ritu_msg", tgItems[39].toString());
					// ADD end 20121025 QP@20505 No.24
				}
				
			}
			

		} catch (Exception e) {
			this.em.ThrowException(e, "���쌴���p�����[�^�[�i�[���������s���܂����B");
			
		} finally {
			//���X�g�J��
			removeList(lstGenryoData);
			
			tgItems = null;

		}

	}
	
}
