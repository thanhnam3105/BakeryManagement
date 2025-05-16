package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
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
			storageBunsekiHenko(lstRecset, resKind.getTableItem(0));

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
			strRetSQL.append("   ,M402.cd_busho AS cd_busho ");
			strRetSQL.append("   ,M401.cd_genryo AS cd_genryo ");
			
			strRetSQL.append("   ,M402.nm_genryo AS nm_genryo ");
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
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M401.dt_kakunin),'') AS dt_kakunin ");			
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS budomari ");
			strRetSQL.append("  ,ISNULL(M402.nisugata,'') AS nisugata ");
			strRetSQL.append("  ,ISNULL(M402.kikakusho,'') AS kikakusho ");
			strRetSQL.append("  ,ISNULL(CONVERT(VARCHAR,M402.dt_konyu,20),'') AS dt_konyu ");
			strRetSQL.append("  ,M401.id_toroku AS id_toroku ");
			strRetSQL.append("  ,M401.dt_toroku AS dt_toroku ");

			//�������E���ו\�������i���}���j
			strRetSQL.append("  ,'' AS max_row ");
			strRetSQL.append("  ,'' AS list_max_row ");
			
			strRetSQL.append(" FROM ma_genryo AS M401 ");
			strRetSQL.append("  LEFT JOIN ma_genryokojo AS M402 ");
			strRetSQL.append("   ON  M402.cd_kaisha = M401.cd_kaisha ");
			strRetSQL.append("   AND M402.cd_genryo = M401.cd_genryo ");
			
			//�@�\���N�G�X�g�f�[�^�����擾
			intRecCount = reqData.getCntRow(0);
			
			//����������ݒ�
			for ( int i=0 ; i<intRecCount; i++ ) {
				
				//���N�G�X�g�p�����[�^�̎擾
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");
				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
				String strReqGenryoCd = reqData.getFieldVale(0, i, "cd_genryo");
				
				//���������̒ǉ�
				if ( strSQL_where.length() == 0 ) {
					strSQL_where.append(" WHERE ");
					
				} else {
					strSQL_where.append(" OR ");
					
				}
				strSQL_where.append("  (  ");
				strSQL_where.append("  M401.cd_kaisha =" + strReqKaishaCd);
				strSQL_where.append("  AND M402.cd_busho =" + strReqBushoCd);
				strSQL_where.append("  AND M401.cd_genryo ='" + strReqGenryoCd + "' ");
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
				
				data = new Object[35];
				
				//���N�G�X�g�p�����[�^�̎擾
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");
				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
				String strReqGenryoCd = reqData.getFieldVale(0, i, "cd_genryo");

				for (int j = 0; j < lstGenryoSearchData.size(); j++) {
					
					//�������ʂ��擾
					Object[] items = (Object[]) lstGenryoSearchData.get(j);

					//�L�[����(��ЃR�[�h�A�����R�[�h�A�����R�[�h)���r����
					if ( (strReqKaishaCd.equals(items[0].toString())
							&& strReqBushoCd.equals(items[1].toString())
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
				lstChkBunsekiData.add(data);
				
			}
			
			//�y�[�W��������(�Ώۃy�[�W�̃��X�g��\��)
			for ( int i = (intReqSelectRow-1) * intListRowMax; i < (((intReqSelectRow-1) * intListRowMax) + intListRowMax); ++i ) {
				
				if ( i < lstChkBunsekiData.size() ) {
					//�f�[�^���i�[
					lstRetBunsekiData.add(lstChkBunsekiData.get(i));
					
				} else {
					//�\�������I�[�o�[���́A���[�v���甲����
					break;
					
				}
				
			}
			
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
	private void storageBunsekiHenko(List<?> lstGenryoData, RequestResponsTableBean resTable) 
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//�@�F�����@�������ʏ�񃊃X�g�ɕێ����Ă���e�p�����[�^�[���@�\���X�|���X�f�[�^�֊i�[����B
		try {
			
			for (int i = 0; i < lstGenryoData.size(); i++) {
				
				//�������ʂ̊i�[
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenryoData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale("rec" + i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale("rec" + i, "cd_busho", items[1].toString());
				resTable.addFieldVale("rec" + i, "cd_genryo", items[2].toString());
				resTable.addFieldVale("rec" + i, "nm_genryo", items[3].toString());
				resTable.addFieldVale("rec" + i, "ritu_sakusan", items[4].toString());
				resTable.addFieldVale("rec" + i, "ritu_shokuen", items[5].toString());
				resTable.addFieldVale("rec" + i, "ritu_sousan", items[6].toString());
				resTable.addFieldVale("rec" + i, "ritu_abura", items[7].toString());
				resTable.addFieldVale("rec" + i, "hyojian", items[8].toString());
				resTable.addFieldVale("rec" + i, "tenkabutu", items[9].toString());
				resTable.addFieldVale("rec" + i, "memo", items[10].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo1", items[11].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo2", items[12].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo3", items[13].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo4", items[14].toString());
				resTable.addFieldVale("rec" + i, "no_eiyo5", items[15].toString());
				resTable.addFieldVale("rec" + i, "wariai1", items[16].toString());
				resTable.addFieldVale("rec" + i, "wariai2", items[17].toString());
				resTable.addFieldVale("rec" + i, "wariai3", items[18].toString());
				resTable.addFieldVale("rec" + i, "wariai4", items[19].toString());
				resTable.addFieldVale("rec" + i, "wariai5", items[20].toString());
				resTable.addFieldVale("rec" + i, "kbn_haishi", items[21].toString());
				resTable.addFieldVale("rec" + i, "nm_haishi", items[22].toString());
				resTable.addFieldVale("rec" + i, "cd_kakutei", items[23].toString());
				resTable.addFieldVale("rec" + i, "id_kakunin", items[24].toString());
				resTable.addFieldVale("rec" + i, "dt_kakunin", items[25].toString());
				resTable.addFieldVale("rec" + i, "tanka", items[26].toString());
				resTable.addFieldVale("rec" + i, "budomari", items[27].toString());
				resTable.addFieldVale("rec" + i, "nisugata", items[28].toString());
				resTable.addFieldVale("rec" + i, "kikakusho", items[29].toString());
				resTable.addFieldVale("rec" + i, "dt_konyu", items[30].toString());
				resTable.addFieldVale("rec" + i, "id_toroku", items[31].toString());
				resTable.addFieldVale("rec" + i, "dt_toroku", items[32].toString());
				resTable.addFieldVale("rec" + i, "max_row", items[33].toString());
				resTable.addFieldVale("rec" + i, "list_max_row", items[34].toString());
				
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "���쌴���p�����[�^�[�i�[���������s���܂����B");
			
		} finally {
			//���X�g�J��
			removeList(lstGenryoData);

		}

	}
	
}
