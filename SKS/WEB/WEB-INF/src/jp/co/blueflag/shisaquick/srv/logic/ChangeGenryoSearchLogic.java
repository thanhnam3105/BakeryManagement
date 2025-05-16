package jp.co.blueflag.shisaquick.srv.logic;

import java.util.ArrayList;
import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * ���͒l�ύX�m�F�c�a����DB����
 *  : JWS����\�A��ʂ́u���͒l�̕ύX�m�F�v�{�^�����Ăяo���B
 * �e�[�u������A�f�[�^�̒��o���s���B
 * 
 * @author TT.katayama
 * @since 2009/04/10
 */
public class ChangeGenryoSearchLogic extends LogicBase {

	/**
	 * ���͒l�ύX�m�F�p�R���X�g���N�^ : �C���X�^���X����
	 */
	public ChangeGenryoSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���͒l�ύX�m�F���擾SQL�쐬
	 * @param reqKind : �@�\���N�G�X�g�f�[�^
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

		StringBuffer strSql = null;
		List<?> lstGenryoSearchData = null;
		List<?> lstKaishaBushoData = null;
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
			
			//�B�F �����}�X�^ �����pSQL�쐬���\�b�h�Ăяo��SQL���擾����B
			strSql = new StringBuffer();
			strSql = createBushoMstSQL(reqData);

			//�A�F�f�[�^�x�[�X������p���A�����}�X�^ �������ʂ��擾����B
//			super.createSearchDB();
			lstKaishaBushoData = searchDB.dbSearch(strSql.toString());
			
			//�C�F ���N�G�X�g�f�[�^�ƌ������ʂ��r���A���͒l�ύX�m�F�f�[�^���擾����
			lstRecset = setBunsekiHenkouData(reqData, lstGenryoSearchData);
			
			//�D�F ���͒l�ύX�m�F�f�[�^�ɉ�Ж��E��������ݒ肷��
			lstRecset = setKaishaBushoData(lstRecset, lstKaishaBushoData);
			
			//�E�F���͒l�ύX�m�F�f�[�^�p�����[�^�[�i�[���\�b�h���ďo���A���X�|���X�f�[�^���`������B
			
			// �@�\ID�̐ݒ�
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			// �e�[�u�����̐ݒ�
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);
			
			// ���X�|���X�f�[�^�̌`��
			storageBunsekiHenko(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "���͒l�ύX�m�F���擾���������s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstGenryoSearchData);
			removeList(lstKaishaBushoData);
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSql = null;
			strKinoId = null;
			strTableNm = null;
		}
		
		//�F�F�@�\���X�|���X�f�[�^��ԋp����B
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
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M402.tanka),'') AS tanka ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M402.budomari),'') AS budomari ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_abura),'') AS ritu_abura ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_sakusan),'') AS ritu_sakusan ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_shokuen),'') AS ritu_shokuen ");
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_sousan),'') AS ritu_sousan ");
			strRetSQL.append("   ,'' AS nm_kaisha ");
			strRetSQL.append("   ,'' AS nm_busho ");
			//ADD start 20121031 QP@20505
			strRetSQL.append("   ,ISNULL(CONVERT(VARCHAR,M401.ritu_msg),'') AS ritu_msg ");
			//ADD end 20121031 QP@20505
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
//				String strReqGenryoNm = reqData.getFieldVale(0, i, "nm_genryo");
//				String strReqTanka = reqData.getFieldVale(0, i, "tanka");
//				String strReqBudomari = reqData.getFieldVale(0, i, "budomari");
//				String strReqAburaRitu = reqData.getFieldVale(0, i, "ritu_abura");
//				String strReqSakusanRitu = reqData.getFieldVale(0, i, "ritu_sakusan");
//				String strReqShokuenRitu = reqData.getFieldVale(0, i, "ritu_shokuen");
//				String strReqSousanRit = reqData.getFieldVale(0, i, "ritu_sousan");
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
//				strSQL_where.append("  AND M402.nm_genryo = '" + strReqGenryoNm + "' ");
//				strSQL_where.append("  AND M402.tanka =" + strReqTanka );
//				strSQL_where.append("  AND M402.budomari =" + strReqBudomari );
//				strSQL_where.append("  AND M401.ritu_abura =" + strReqAburaRitu );
//				strSQL_where.append("  AND M401.ritu_sakusan =" + strReqSakusanRitu );
//				strSQL_where.append("  AND M401.ritu_shokuen =" + strReqShokuenRitu );
//				strSQL_where.append("  AND M401.ritu_sousan =" + strReqSousanRit );
				strSQL_where.append("  )  ");
			}
			strRetSQL.append(strSQL_where.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���͒l�ύX�m�F��񌟍��pSQL�쐬���������s���܂����B");
		} finally {
			//�ϐ��̍폜
			strSQL_where = null;
		}
		
		//�A:�쐬����SQL��ԋp
		return strRetSQL;
	}

	/**
	 * �����}�X�^ �����pSQL�쐬
	 * @param lstBunsekiHenkouData : ���͒l�ύX�m�F�f�[�^ 
	 * @return �쐬SQL
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private StringBuffer createBushoMstSQL(RequestResponsKindBean reqData)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		StringBuffer strRetSQL = new StringBuffer();
		StringBuffer strSQL_where = new StringBuffer();		//���������p
		int intRecCount = 0;

		//�@�F���N�G�X�g�f�[�^���A�����}�X�^�����擾����SQL���쐬����B
		try {

			//M401[ma_busho]���擾
			strRetSQL.append(" SELECT DISTINCT ");
			strRetSQL.append("   M104.cd_kaisha AS cd_kaisha ");
			strRetSQL.append("  ,M104.cd_busho AS cd_busho ");
			strRetSQL.append("  ,M104.nm_kaisha AS nm_kaisha ");
			strRetSQL.append("  ,M104.nm_busho AS nm_busho ");
			strRetSQL.append("  FROM ma_busho M104 ");

			//�@�\���N�G�X�g�f�[�^�����擾
			intRecCount = reqData.getCntRow(0);
			
			//����������ݒ�
			for ( int i=0 ; i<intRecCount; i++ ) {
				//���N�G�X�g�p�����[�^�̎擾
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");
				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
				//���������̒ǉ�
				if ( strSQL_where.length() == 0 ) {
					strSQL_where.append(" WHERE ");	
				} else {
					strSQL_where.append(" OR ");
				}
				strSQL_where.append("  (  ");
				strSQL_where.append("  M104.cd_kaisha =" + strReqKaishaCd);
				strSQL_where.append("  AND M104.cd_busho =" + strReqBushoCd);
				strSQL_where.append("  )  ");
			}
			strRetSQL.append(strSQL_where.toString());
			
		} catch(Exception e) {
			this.em.ThrowException(e, "��Ж��E������ �����pSQL�쐬���������s���܂����B");
		} finally {
		}

		//�A:�쐬����SQL��ԋp
		return strRetSQL;
	}
	
	/**
	 * ���͒l�ύX�m�F�f�[�^�ݒ�
	 *  �F ���N�G�X�g�f�[�^�ƌ����������ʂ��r���A�ύX����Ă��鍀�ڂ�ݒ肷��B
	 *  ���N�G�X�g�f�[�^�Ɖ�Ж��E�������������ʂ��r���A��Ж��E��������ݒ肷��B
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param lstGenryoSearchData : ������������
	 * @param lstKaishaBushoData : ��Ж��E��������������
	 * @return ���͒l�ύX�m�F�f�[�^ 
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> setBunsekiHenkouData(RequestResponsKindBean reqData, List<?> lstGenryoSearchData)
	    throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		ArrayList<Object[]> lstRetBunsekiData = new ArrayList<Object[]>();
		int intRecCount = 0;
		Object[] data = null;
	    
		try {
			intRecCount = reqData.getCntRow(0);			

			//���N�G�X�g�f�[�^�ƌ����������ʂ��r���A�ݒ�
			for ( int i=0 ; i<intRecCount; i++ ) {
				
				boolean blnSameChk = true;
				boolean blnDeleteChk = true;

				//MOD start 20121031 QP@20505
//				data = new Object[12];
				data = new Object[13];
				//MOD end 20121031 QP@20505
				
				//���N�G�X�g�p�����[�^�̎擾
				String strReqKaishaCd = reqData.getFieldVale(0, i, "cd_kaisha");
				String strReqBushoCd = reqData.getFieldVale(0, i, "cd_busho");
				String strReqGenryoCd = reqData.getFieldVale(0, i, "cd_genryo");
//				String strReqGenryoNm = reqData.getFieldVale(0, i, "nm_genryo");
//				String strReqTanka = reqData.getFieldVale(0, i, "tanka");
//				String strReqBudomari = reqData.getFieldVale(0, i, "budomari");
				String strReqAburaRitu = reqData.getFieldVale(0, i, "ritu_abura");
				String strReqSakusanRitu = reqData.getFieldVale(0, i, "ritu_sakusan");
				String strReqShokuenRitu = reqData.getFieldVale(0, i, "ritu_shokuen");
				String strReqSousanRit = reqData.getFieldVale(0, i, "ritu_sousan");
				//ADD start 20121031 QP@20505
				String strReqMSGRit = reqData.getFieldVale(0, i, "ritu_msg");
				//ADD end 20121031 QP@20505

				for (int j = 0; j < lstGenryoSearchData.size(); j++) {

					Object[] items = (Object[]) lstGenryoSearchData.get(j);

					//���N�G�X�g���ꂽ������DB��茟�����������̃}�b�`���O
					
					//�L�[����(��ЃR�[�h�A�����R�[�h�A�����R�[�h)�@��r
					if ( !(strReqKaishaCd.equals(items[0].toString())
							&& strReqBushoCd.equals(items[1].toString())
							&& strReqGenryoCd.equals(items[2].toString())) ) {
						
						items = null;
						blnDeleteChk = true;
						
						continue;
						
					}
					
					blnDeleteChk = false;

					//���앪�͒l��DB���͒l�̍������o
					
//					//������
//					blnSameChk = strReqGenryoNm.equals(items[3].toString());
//					if ( !blnSameChk ) {
//						data = items;
//						break;
//					}
//					//�P��
//					if ( (!strReqTanka.equals("NULL")) && (!items[4].equals("")) ) {
//						blnSameChk = (Double.parseDouble(strReqTanka) == Double.parseDouble(items[4].toString()));
//					} else {
//						blnSameChk = ((strReqTanka.equals("NULL")) && (items[4].equals("")));
//					}
//					if ( !blnSameChk ) {
//						data = items;
//						break;
//					}
//					//����
//					if ( (!strReqBudomari.equals("NULL")) && (!items[5].equals("")) ) {
//						blnSameChk = (Double.parseDouble(strReqBudomari) == Double.parseDouble(items[5].toString()));
//					} else {
//						blnSameChk = ((strReqBudomari.equals("NULL")) && (items[5].equals("")));
//					}
//					if ( !blnSameChk ) {
//						data = items;
//						break;
//					}
					//���ܗL��
					if ( (!strReqAburaRitu.equals("NULL")) && (!items[6].equals("")) ) {
						blnSameChk = (Double.parseDouble(strReqAburaRitu) == Double.parseDouble(items[6].toString()));
					} else {
						blnSameChk = ((strReqAburaRitu.equals("NULL")) && (items[6].equals("")));
					}
					if ( !blnSameChk ) {
						data = items;
						break;
					}
					//�|�_
					if ( (!strReqSakusanRitu.equals("NULL")) && (!items[7].equals("")) ) {
						blnSameChk = (Double.parseDouble(strReqSakusanRitu) == Double.parseDouble(items[7].toString()));
					} else {
						blnSameChk = ((strReqSakusanRitu.equals("NULL")) && (items[7].equals("")));
					}
					if ( !blnSameChk ) {
						data = items;
						break;
					}
					//�H��
					if ( (!strReqShokuenRitu.equals("NULL")) && (!items[8].equals("")) ) {
						blnSameChk = (Double.parseDouble(strReqShokuenRitu) == Double.parseDouble(items[8].toString()));
					} else {
						blnSameChk = ((strReqShokuenRitu.equals("NULL")) && (items[8].equals("")));
					}
					if ( !blnSameChk ) {
						data = items;
						break;
					}
					//���_
					if ( (!strReqSousanRit.equals("NULL")) && (!items[9].equals("")) ) {
						blnSameChk = (Double.parseDouble(strReqSousanRit) == Double.parseDouble(items[9].toString()));
					} else {
						blnSameChk = ((strReqSousanRit.equals("NULL")) && (items[9].equals("")));
					}
					//ADD start 20121031 QP@20505
					//MSG
					if ( (!strReqMSGRit.equals("NULL")) && (!items[12].equals("")) ) {
						blnSameChk = (Double.parseDouble(strReqMSGRit) == Double.parseDouble(items[12].toString()));
					} else {
						blnSameChk = ((strReqMSGRit.equals("NULL")) && (items[12].equals("")));
					}
					//MOD end 20121031 QP@20505
					if ( !blnSameChk ) {
						data = items;
						break;
					}
					
					//�ύX�ӏ������̏ꍇ�A���[�v���甲����
					blnSameChk = true;
					items = null;
					break;
					
				}
				
				//�ύX�m�F
				if ( blnDeleteChk ) {
					//���݂��Ȃ��ꍇ					
					data[0] = strReqKaishaCd;
					data[1] = strReqBushoCd;
					data[2] = strReqGenryoCd;
					data[3] = "�����͑��݂��܂���";
					data[4] = "0";
					data[5] = "0";
					data[6] = "0";
					data[7] = "0";
					data[8] = "0";
					data[9] = "0";
					data[10] = "";
					data[11] = "";
					//ADD start 20121031 QP@20505
					data[12] = "";
					//ADD end 20121031 QP@20505
					lstRetBunsekiData.add(data);
				} else {
					if ( !blnSameChk ) {
						//�f�[�^���قȂ�ꍇ
						lstRetBunsekiData.add(data);
					}
				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "���͒l�ύX�m�F�f�[�^�ݒ菈�������s���܂����B");
		} finally {
			//���X�g�J��
			removeList(lstGenryoSearchData);
			
			intRecCount = 0;
			data = null;
		}
		
		return lstRetBunsekiData;
	}

	/**
	 * ���͒l�ύX�m�F�f�[�^ ��Ж��E�������ݒ�
	 *  �F���͒l�ύX�m�F�f�[�^�Ɖ�Ж��E�������������ʂ��r���A��Ж��E��������ݒ肷��B
	 * @param lstBunsekiHenkouData : ���͒l�ύX�m�F�f�[�^
	 * @param lstKaishaBushoData : ��Ж��E��������������
	 * @return ���͒l�ύX�m�F�f�[�^ 
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private List<?> setKaishaBushoData(List<?> lstBunsekiHenkouData, List<?> lstKaishaBushoData)
	    throws ExceptionSystem, ExceptionUser, ExceptionWaning {		
		List<Object[]> lstRetBunsekiData = (List<Object[]>) lstBunsekiHenkouData;
	    
		try {

			//���͒l�ύX�m�F�f�[�^�Ɖ�Ж��E�������������ʂ��r���A��Ж��E��������ݒ�
			for (int i = 0; i < lstBunsekiHenkouData.size(); i++) {
				Object[] items = (Object[]) lstBunsekiHenkouData.get(i);
				
				for ( int j=0; j<lstKaishaBushoData.size(); j++ ) {
					Object[] nameItems = (Object[]) lstKaishaBushoData.get(j);
					
					//��ЃR�[�h�E�����R�[�h�@��r
					if ( !(items[0].toString().equals(nameItems[0].toString()) && items[1].toString().equals(nameItems[1].toString())) ) {
						continue;
					}
					//��Ж���ݒ�
					items[10] = nameItems[2];
					//��������ݒ�
					items[11] = nameItems[3];
					break;
				}
				
				lstRetBunsekiData.set(i, items);
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���͒l�ύX�m�F�f�[�^ ��Ж��E�������ݒ菈�������s���܂����B");
		} finally {
			//���X�g�J��
			removeList(lstKaishaBushoData);
		}
		
		return lstRetBunsekiData;
	}
	
	/**
	 * ���͒l�ύX�m�F�p�����[�^�[�i�[
	 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA590O�v�ɐݒ肷��B
	 * 
	 * @param lstBunsekiData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageBunsekiHenko(List<?> lstBunsekiData, RequestResponsTableBean resTable)
			throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//�@�F�����@�������ʏ�񃊃X�g�ɕێ����Ă���e�p�����[�^�[���@�\���X�|���X�f�[�^�֊i�[����B
		try {

			for (int i = 0; i < lstBunsekiData.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstBunsekiData.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale(i, "cd_busho", items[1].toString());
				resTable.addFieldVale(i, "cd_genryo", items[2].toString());
				resTable.addFieldVale(i, "nm_genryo", items[3].toString());
				resTable.addFieldVale(i, "tanka", items[4].toString());
				resTable.addFieldVale(i, "budomari", items[5].toString());
				resTable.addFieldVale(i, "ritu_abura", items[6].toString());
				resTable.addFieldVale(i, "ritu_sakusan", items[7].toString());
				resTable.addFieldVale(i, "ritu_shokuen", items[8].toString());
				resTable.addFieldVale(i, "ritu_sousan", items[9].toString());
				resTable.addFieldVale(i, "nm_kaisha", items[10].toString());
				resTable.addFieldVale(i, "nm_busho", items[11].toString());
				//ADD start 20121031 QP@20505
				resTable.addFieldVale(i, "ritu_msg", items[12].toString());
				//ADD end 20121031 QP@20505
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "���͒l�ύX�m�F�p�����[�^�[�i�[���������s���܂����B");
	
		} finally {

		}

	}
	
}
