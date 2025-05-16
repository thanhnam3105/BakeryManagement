package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * �������Z���ʕ\������DB����
 *  : �������Z��ʂ�ͯ�ް�����̏����擾����B
 *  �@�\ID�FFGEN0010
 *  
 * @author Nishigawa
 * @since  2009/10/20
 */
public class FGEN0010_Logic extends LogicBase{
	
	private String kengen_moto;
	
	/**
	 * �������Z���ʕ\������DB����
	 * : �C���X�^���X����
	 */
	public FGEN0010_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * �������Z���ʕ\��
	 *  : �������Z��ʂ�ͯ�ް�����̏����擾����B
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
			
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			
			//����R�[�h�ݒ�i���N�G�X�g�Ɏ���R�[�h���Ȃ��ꍇ�AUSERINFO��莎��R�[�h���擾�j
			if( toString(reqData.getFieldVale(0, 0, "cd_shain")) == "" ){
				
				//����R�[�h_�Ј�CD�A�N�A�ǔԁ@�ݒ�
				reqData.setFieldVale(0, 0, "cd_shain", toString(userInfoData.getMovement_condition().get(0)) );
				reqData.setFieldVale(0, 0, "nen", toString(userInfoData.getMovement_condition().get(1)) );
				reqData.setFieldVale(0, 0, "no_oi", toString(userInfoData.getMovement_condition().get(2)) );
				//�yQP@00342�z
				reqData.setFieldVale(0, 0, "no_eda", toString(userInfoData.getMovement_condition().get(3)) );
				
			}
			
			
			//�������Z[ kihon ]�@���X�|���X�f�[�^�̌`��
			this.genkaKihonSetting(resKind, reqData);
			
			
			//�������Z[ tr_shisan_shisaku ]�@���X�|���X�f�[�^�̌`��
			this.genkaTr_shisan_shisakuSetting(resKind, reqData);
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
		
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			
		}
		return resKind;
		
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                         DataSetting�i���X�|���X�f�[�^�̌`���j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �������Z���ʏ��[ kihon ]���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void genkaKihonSetting(
			
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData 
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;	
		//�yQP@00342�z
		List<?> lstRecset2 = null;	
		
		//���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {
			
			//�e�[�u����
			String strTblNm = "kihon";	

//�y�V�T�N�C�b�N�i�����j�v�]�z�r������@TT.Nishigawa 2010/2/16 START -----------------------------------															
			
			//�r�����[�U�擾
			strSqlBuf = this.createGenkaHaitaSQL(reqData);
			super.createSearchDB();

			//�yQP@00342�z
			lstRecset2 = searchDB.dbSearch(strSqlBuf.toString());

			//�yQP@00342�z�r��ID�擾
			String haita_id = "";
			if(lstRecset2.size() > 0){
				Object[] items = (Object[])lstRecset2.get(0);
				haita_id = toString(items[8]);
			}
			
			//�����擾
			ArrayList aryGamen = userInfoData.getId_gamen();
			ArrayList aryKino = userInfoData.getId_kino();
			
			//�������[�v�i�������Z��ʂ̌�����T���j
			for(int i=0; i<aryGamen.size(); i++){
				
//				//�������Z���ID
//				if(aryGamen.get(i).equals("170")){
//					
//					//�{�������Ă��錠����ޔ�
//					this.kengen_moto = toString(aryKino.get(i));
//					
//					//�ҏW�����̏ꍇ
//					if( toString(aryKino.get(i)).equals("20") ){
//						
//						//�r���敪��NULL�̏ꍇ
//						if(haita_id.equals("")){
//							
//							//���g��ID�ɂĔr����������
//							updateHaitaKbn(reqData);
//							
//						}
//						//�r���敪�����O�C�����[�U��ID�ƈ�v����ꍇ
//						else if(haita_id.equals(userInfoData.getId_user())){
//							
//							//�������Ȃ�
//							
//						}
//						//�r���敪�����O�C�����[�U��ID�ƈ�v���Ȃ��ꍇ
//						else{
//							
//							//�������Z�������u�{���v�ɕύX
//							aryKino.set(i, "70");
//							
//						}
//						
//					}
//				}
				
				//�yQP@00342�z
				//���ID�擾
				String gamenId = toString(aryGamen.get(i));
				
				//�������Z���ID or �������Z��ʁi�c�ƁjID
				if(aryGamen.get(i).equals("170") || aryGamen.get(i).equals("190") ){
					
					//�{�������Ă��錠����ޔ�
					this.kengen_moto = toString(aryKino.get(i));
					
						
					//�r���敪��NULL�̏ꍇ
					if(haita_id.equals("")){
							
						//���g��ID�ɂĔr����������
						updateHaitaKbn(reqData);
						
					}
					//�r���敪�����O�C�����[�U��ID�ƈ�v����ꍇ
					else if(haita_id.equals(userInfoData.getId_user())){
							
						//�������Ȃ�
							
					}
					//�r���敪�����O�C�����[�U��ID�ƈ�v���Ȃ��ꍇ
					else{
							
						//�������Z�������u�r���v�ɕύX
						this.kengen_moto = "999";
							
					}
				}
			}
			
//�y�V�T�N�C�b�N�i�����j�v�]�z�r������@TT.Nishigawa 2010/2/16 END -------------------------------------
			
			
			//�@�������Z���ʏ��[ kihon ]�f�[�^�擾SQL�쐬
			strSqlBuf = this.createGenkaKihonSQL(reqData);
			
			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			
			//�yQP@00342�z
			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaKihon(lstRecset , lstRecset2 , resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z���ʏ��[ kihon ]�f�[�^�������������s���܂����B");
			
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			
			if (searchDB != null) {
				//�Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;
				
			}

			//�ϐ��̍폜
			strSqlBuf = null;

		}
		
	}
	
	
	/**
	 * �r���敪�̍X�V
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void updateHaitaKbn(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			// �g�����U�N�V�����J�n
			super.createExecDB();
			execDB.BeginTran();
			
			try {
				strSQL.append(" UPDATE tr_shisan_shisakuhin");
				strSQL.append("   SET ");
				strSQL.append("        haita_id_user = " + toString(userInfoData.getId_user()));
				strSQL.append(" WHERE ");
				strSQL.append("     cd_shain = " + 
						toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
				strSQL.append(" AND nen = " + 
						toString(reqData.getFieldVale(0, 0, "nen")) + " ");
				strSQL.append(" AND no_oi = " + 
						toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");

				//�yQP@00342�z
				strSQL.append(" AND no_eda = " + 
						toString(reqData.getFieldVale(0, 0, "no_eda")) + " ");

				
				//���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�r���敪�̍X�V���s��
				this.execDB.execSQL(strSQL.toString());
				
				// �R�~�b�g
				execDB.Commit();
				
			} catch(Exception e) {
				// ���[���o�b�N
				execDB.Rollback();
				this.em.ThrowException(e, "");
				
			} finally {
				if (execDB != null) {
					execDB.Close();				//�Z�b�V�����̃N���[�Y
					execDB = null;
				}
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�r���敪�̍X�V���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			
		}
	}
	
	
	
	/**
	 * �������Z���ʏ��[ tr_shisan_shisaku ]�@���X�|���X�f�[�^�̌`��
	 * @param resKind : ���X�|���X�f�[�^
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 *  
	 * @author TT.Nishigawa
	 * @since  2009/10/21
	 */
	private void genkaTr_shisan_shisakuSetting(
			
			 RequestResponsKindBean resKind
			,RequestResponsKindBean reqData 
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���R�[�h�l�i�[���X�g
		List<?> lstRecset = null;	
		//���R�[�h�l�i�[���X�g
		StringBuffer strSqlBuf = null;

		try {
			
			//�e�[�u����
			String strTblNm = "tr_shisan_shisaku";	

			//�@�������Z���ʏ��[ tr_shisan_shisaku ]�f�[�^�擾SQL�쐬
			strSqlBuf = this.createGenkaTr_shisan_shisakuSQL(reqData);
			
			//�A���ʃN���X�@�f�[�^�x�[�X������p����SQL���s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSqlBuf.toString());
			
			//�B���X�|���X�f�[�^�Ƀe�[�u���ǉ�
			resKind.addTableItem(strTblNm);
			
			//�C�ǉ������e�[�u���փ��R�[�h�i�[
			this.storageGenkaTr_shisan_shisaku(lstRecset, resKind.getTableItem(strTblNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z���ʏ��[ tr_shisan_shisaku ]�f�[�^�������������s���܂����B");
			
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			
			if (searchDB != null) {
				//�Z�b�V�����̉��
				this.searchDB.Close();
				searchDB = null;
				
			}

			//�ϐ��̍폜
			strSqlBuf = null;

		}
		
	}
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL�iSQL�������j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �������Z���ʏ��[ kihon ]�f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createGenkaKihonSQL(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		
		try {
			
			//SQL���̍쐬
			strSql.append(" SELECT ");
			strSql.append("  T310.cd_shain AS cd_shain ");
			strSql.append(" ,T310.nen AS nen ");
			strSql.append(" ,T310.no_oi AS no_oi ");
			strSql.append(" ,T310.no_eda AS no_eda ");
//MOD start �yH24�N�x�Ή��z 20120416 hisahori
//			strSql.append(" ,T110.nm_hin AS hin ");
			strSql.append(" ,CASE WHEN T310.nm_edaShisaku IS NOT NULL ");
			strSql.append("  THEN ");
			strSql.append("	 CASE RTRIM(T310.nm_edaShisaku) WHEN '' ");
			strSql.append("	 THEN T110.nm_hin ");
			strSql.append("	 ELSE T110.nm_hin + ' �y' + T310.nm_edaShisaku + '�z' END ");
			strSql.append(" ELSE T110.nm_hin END AS hin ");
			//MOD end �yH24�N�x�Ή��z 20120416 hisahori
			strSql.append(" ,M101_KEN.nm_user AS nm_user_KEN ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T310.ken_dt_koshin ");
			strSql.append(" 	,111) AS ken_dt_koshin ");
			strSql.append(" ,M101_SEI.nm_user AS nm_user_SEI ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T310.sei_dt_koshin ");
			strSql.append(" 	,111) AS sei_dt_koshin ");
			strSql.append(" ,M101_GEN.nm_user AS nm_user_GEN ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T310.gen_dt_koshin ");
			strSql.append(" 	,111) AS gen_dt_koshin ");
			strSql.append(" ,M101_KJO.nm_user AS nm_user_KJO ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T310.kojo_dt_koshin ");
			strSql.append(" 	,111) AS kojo_dt_koshin ");
			strSql.append(" ,T310.saiyo_sample AS saiyo_sample ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T310.sam_dt_koshin ");
			strSql.append(" 	,111) AS sam_dt_koshin ");
			strSql.append(" ,M101_SAM.nm_user AS nm_user_SAM ");
			strSql.append(" ,M104.nm_kaisha AS nm_kaisha_haita ");
			strSql.append(" ,M104.nm_busho AS nm_busho_haita ");
			strSql.append(" ,M101_HAITA.nm_user AS nm_user_haita ");
			strSql.append(" ,T310.haita_id_user AS haita_id_user ");
			strSql.append(" ,CONVERT(VARCHAR ");
			strSql.append(" 	,T110.no_irai) AS no_irai ");
			strSql.append(" ,M105.nm_��iteral AS shurui_eda ");
			strSql.append(" ,CONVERT(VARCHAR,T310.dt_kizitu,111) AS dt_kizitu ");
			strSql.append(" ,CASE T310.saiyo_sample WHEN -1  ");
			strSql.append(" 	THEN '�̗p�Ȃ�' ");
			strSql.append(" 	ELSE T131.nm_sample ");
			strSql.append("  END AS nm_sample ");
// ADD start �yH24�N�x�Ή��z 20120420 hisahori
			strSql.append(" ,T110.nm_hin AS nm_motHin ");
// ADD end �yH24�N�x�Ή��z 20120420 hisahori
			strSql.append(" FROM tr_shisan_shisakuhin AS T310 ");
			strSql.append(" LEFT JOIN tr_shisakuhin AS T110 ");
			strSql.append(" ON T310.cd_shain = T110.cd_shain ");
			strSql.append(" AND T310.nen = T110.nen ");
			strSql.append(" AND T310.no_oi = T110.no_oi ");
			strSql.append(" LEFT JOIN dbo.ma_user AS M101_KEN ");
			strSql.append(" ON T310.ken_id_koshin = M101_KEN.id_user ");
			strSql.append(" LEFT JOIN dbo.ma_user AS M101_SEI ");
			strSql.append(" ON T310.sei_id_koshin = M101_SEI.id_user ");
			strSql.append(" LEFT JOIN dbo.ma_user AS M101_GEN ");
			strSql.append(" ON T310.gen_id_koshin = M101_GEN.id_user ");
			strSql.append(" LEFT JOIN dbo.ma_user AS M101_KJO ");
			strSql.append(" ON T310.kojo_id_koshin = M101_KJO.id_user ");
			strSql.append(" LEFT JOIN dbo.ma_user AS M101_SAM ");
			strSql.append(" ON T310.sam_id_koshin = M101_SAM.id_user ");
			strSql.append(" LEFT JOIN ma_user AS M101_HAITA ");
			strSql.append(" ON T310.haita_id_user = M101_HAITA.id_user ");
			strSql.append(" LEFT JOIN ma_busho AS M104 ");
			strSql.append(" ON M101_HAITA.cd_kaisha = M104.cd_kaisha ");
			strSql.append(" AND M101_HAITA.cd_busho = M104.cd_busho ");
			strSql.append(" LEFT JOIN ma_literal AS M105 ");
			strSql.append(" ON M105.cd_category = 'shurui_eda' ");
			strSql.append(" AND M105.cd_��iteral = T310.shurui_eda ");
			strSql.append(" LEFT JOIN tr_shisaku AS T131 ");
			strSql.append(" ON T131.cd_shain = T310.cd_shain ");
			strSql.append(" AND T131.nen = T310.nen ");
			strSql.append(" AND T131.no_oi = T310.no_oi ");
			strSql.append(" AND T131.seq_shisaku = T310.saiyo_sample ");
			strSql.append(" WHERE ");
			strSql.append("     T310.cd_shain = " + 
					toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
			strSql.append(" AND T310.nen = " + 
					toString(reqData.getFieldVale(0, 0, "nen")) + " ");
			strSql.append(" AND T310.no_oi = " + 
					toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");

			//�yQP@00342�z
			strSql.append(" AND T310.no_eda = " + 
					toString(reqData.getFieldVale(0, 0, "no_eda")) + " ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  createSQL�iSQL�������j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**�yQP@00342�z
	 * �r���f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createGenkaHaitaSQL(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		
		try {
			
			//SQL���̍쐬
			strSql.append(" SELECT ");
			strSql.append("  T310.cd_shain AS cd_shain ");
			strSql.append(" ,T310.nen AS nen ");
			strSql.append(" ,T310.no_oi AS no_oi ");
			strSql.append(" ,T310.no_eda AS no_eda ");
// ADD start �yH24�N�x�Ή��z 20120420 hisahori
//			strSql.append(" ,T110.nm_hin AS hin ");
			strSql.append(" ,CASE WHEN T310.nm_edaShisaku IS NOT NULL ");
			strSql.append("  THEN ");
			strSql.append("	 CASE RTRIM(T310.nm_edaShisaku) WHEN '' ");
			strSql.append("	 THEN T110.nm_hin ");
			strSql.append("	 ELSE T110.nm_hin + ' �y' + T310.nm_edaShisaku + '�z' END ");
			strSql.append(" ELSE T110.nm_hin END AS hin ");
// ADD end �yH24�N�x�Ή��z 20120420 hisahori
			strSql.append(" ,M104.nm_kaisha AS nm_kaisha_haita ");
			strSql.append(" ,M104.nm_busho AS nm_busho_haita ");
			strSql.append(" ,M101_HAITA.nm_user AS nm_user_haita ");
			strSql.append(" ,T310.haita_id_user AS haita_id_user ");
			strSql.append(" FROM tr_shisan_shisakuhin AS T310 ");
			strSql.append(" LEFT JOIN tr_shisakuhin AS T110 ");
			strSql.append(" ON T310.cd_shain = T110.cd_shain ");
			strSql.append(" AND T310.nen = T110.nen ");
			strSql.append(" AND T310.no_oi = T110.no_oi ");
			strSql.append(" LEFT JOIN ma_user AS M101_HAITA ");
			strSql.append(" ON T310.haita_id_user = M101_HAITA.id_user ");
			strSql.append(" LEFT JOIN ma_busho AS M104 ");
			strSql.append(" ON M101_HAITA.cd_kaisha = M104.cd_kaisha ");
			strSql.append(" AND M101_HAITA.cd_busho = M104.cd_busho ");
			strSql.append(" WHERE ");
			strSql.append("     T310.cd_shain = " + 
					toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
			strSql.append(" AND T310.nen = " + 
					toString(reqData.getFieldVale(0, 0, "nen")) + " ");
			strSql.append(" AND T310.no_oi = " + 
					toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");
			strSql.append(" AND T310.haita_id_user IS NOT NULL ");

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
		return strSql;
	}
	
	/**
	 * �������Z���ʏ��[ tr_shisan_shisaku ]�f�[�^�擾SQL�쐬
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer createGenkaTr_shisan_shisakuSQL(
			
			RequestResponsKindBean reqData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		//SQL�i�[�p
		StringBuffer strSql = new StringBuffer();
		
		try {
			
			//SQL���̍쐬
			
			//-- �e�X�g ----------------------------------------------------------+
//			strSql.append("SELECT TOP 3");
//			strSql.append(" nm_sample");
//			strSql.append(" ,seq_shisaku");
//			strSql.append(" FROM tr_shisaku");
			//-- �e�X�g ----------------------------------------------------------+
			strSql.append(" SELECT DISTINCT ");
			strSql.append("  T131.nm_sample ");
			strSql.append(" ,T131.seq_shisaku ");
			
			//�yQP@00342�z
			strSql.append(" ,T131.sort_shisaku ");
			
			strSql.append(" FROM tr_shisan_shisaku AS T331 ");
			strSql.append(" LEFT JOIN tr_shisaku AS T131 ");
			strSql.append(" ON  T331.cd_shain = T131.cd_shain ");
			strSql.append(" AND T331.nen      = T131.nen ");
			strSql.append(" AND T331.no_oi    = T131.no_oi ");

			//�yQP@00342�z
			strSql.append(" AND T331.seq_shisaku    = T131.seq_shisaku ");

			strSql.append(" WHERE ");
			strSql.append("     T331.cd_shain = " + 
					toString(reqData.getFieldVale(0, 0, "cd_shain")) + " ");
			strSql.append(" AND T331.nen = " + 
					toString(reqData.getFieldVale(0, 0, "nen")) + " ");
			strSql.append(" AND T331.no_oi = " + 
					toString(reqData.getFieldVale(0, 0, "no_oi")) + " ");
			
			//�yQP@00342�z
			strSql.append(" AND T331.no_eda = " + 
					toString(reqData.getFieldVale(0, 0, "no_eda")) + " ");
			strSql.append(" AND T331.fg_chusi IS NULL ");
			
			
//2010/01/21 ISONO ADD START ����˗�����Ă��鎎�삪�̗p�T���v��No�̑Ώ�
			
			strSql.append(" AND T131.flg_shisanIrai = 1"); 
			
//2010/01/21 ISONO ADD END   ����˗�����Ă��鎎�삪�̗p�T���v��No�̑Ώ�
			
			strSql.append(" order by T131.sort_shisaku");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
		return strSql;
		
	}
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  storageData�i�p�����[�^�[�i�[�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	/**
	 * �������Z���ʏ��[ kihon ]�p�����[�^�[�i�[
	 *  : �������Z��ʂ�ͯ�ް���������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @param lstGenkaHaita  : �yQP@00342�z�������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenkaKihon(
			
			  List<?> lstGenkaHeader
			,  List<?> lstGenkaHaita
			, RequestResponsTableBean resTable
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_shain", getRight("0000000000" + toString(items[0],""),10));
				resTable.addFieldVale(i, "nen", getRight("00" + toString(items[1],""), 2));
				resTable.addFieldVale(i, "no_oi", getRight("000" + toString(items[2],""), 3));
	
				//�yQP@00342�z
				resTable.addFieldVale(i, "no_eda", getRight("000" + toString(items[3],""), 3));

				resTable.addFieldVale(i, "hin", toString(items[4],""));
				resTable.addFieldVale(i, "kenkyu_tanto", toString(items[5],""));
				resTable.addFieldVale(i, "kenkyu_date", toString(items[6],""));
				resTable.addFieldVale(i, "seihon_tanto", toString(items[7],""));
				resTable.addFieldVale(i, "seihon_date", toString(items[8],""));
				resTable.addFieldVale(i, "genshizai_tanto", toString(items[9],""));
				resTable.addFieldVale(i, "genshizai_date", toString(items[10],""));
				resTable.addFieldVale(i, "kozyo_tanto",toString(items[11],""));
				resTable.addFieldVale(i, "kozyo_date", toString(items[12],""));
				resTable.addFieldVale(i, "saiyo_no", toString(items[13],""));
				resTable.addFieldVale(i, "saiyo_nm", toString(items[23],""));
				resTable.addFieldVale(i, "saiyo_date", toString(items[14],""));
				resTable.addFieldVale(i, "sam_id_koshin", toString(items[15],""));
// ADD start �yH24�N�x�Ή��z 2012/4/18 hisahori
				resTable.addFieldVale(i, "nm_motHin", toString(items[24],""));				
// ADD end �yH24�N�x�Ή��z 2012/4/18 hisahori

				//�yQP@00342�z
				if(lstGenkaHaita.size() == 0){
					resTable.addFieldVale(i, "cd_shisaku_haita", "");
					resTable.addFieldVale(i, "nm_shisaku_haita", "");
					resTable.addFieldVale(i, "nm_kaisha_haita","");
					resTable.addFieldVale(i, "nm_busho_haita", "");
					resTable.addFieldVale(i, "nm_user_haita", "");
					resTable.addFieldVale(i, "kengen_moto", "");
				}
				else{
					Object[] items2 = (Object[]) lstGenkaHaita.get(0);
					String cd_shisaku_haita = getRight("0000000000" + toString(items2[0],""),10);
					cd_shisaku_haita = cd_shisaku_haita + "-" + getRight("00" + toString(items2[1],""), 2);
					cd_shisaku_haita = cd_shisaku_haita + "-" + getRight("000" + toString(items2[2],""), 3);
					cd_shisaku_haita = cd_shisaku_haita + "-" + getRight("000" + toString(items2[3],""), 3);
					resTable.addFieldVale(i, "cd_shisaku_haita", cd_shisaku_haita);
					resTable.addFieldVale(i, "nm_shisaku_haita", toString(items2[4]));
					
					
					//�y�V�T�N�C�b�N�i�����j�v�]�z�r������@TT.Nishigawa 2010/2/16 START -----------------------------------															
					//DB�擾�l��ݒ�
					resTable.addFieldVale(i, "nm_kaisha_haita", toString(items2[5],""));
					resTable.addFieldVale(i, "nm_busho_haita", toString(items2[6],""));
					resTable.addFieldVale(i, "nm_user_haita", toString(items2[7],""));
					resTable.addFieldVale(i, "kengen_moto", toString(this.kengen_moto,""));
					//�y�V�T�N�C�b�N�i�����j�v�]�z�r������@TT.Nishigawa 2010/2/16 END -------------------------------------
				}
				

				
				//�y�V�T�N�C�b�N�i�����j�v�]�z�Č�No12�@�w���v�\���@TT.Nishigawa 2010/5/12 START -----------------------------------															
				//DB�擾�l��ݒ�
				resTable.addFieldVale(i, "help_file", toString(ConstManager.getConstValue(Category.�ݒ�, "HELPFILE_PATH"),""));
				//�y�V�T�N�C�b�N�i�����j�v�]�z�Č�No12�@�w���v�\���@TT.Nishigawa 2010/5/12 END -------------------------------------

				//�yQP@00412_�V�T�N�C�b�N���ǁ@�Č���27�z T.Arai 2010.10.01 START
				resTable.addFieldVale(i, "no_irai", toString(items[20],""));
				//�yQP@00412_�V�T�N�C�b�N���ǁ@�Č���27�z T.Arai 2010.10.01 END
				
				
				//�yQP@00342�z
				resTable.addFieldVale(i, "shurui_eda", toString(items[21],""));
				resTable.addFieldVale(i, "dt_kizitu", toString(items[22],""));
			
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	/**
	 * �������Z���ʏ��[ tr_shisan_shisaku ]�p�����[�^�[�i�[
	 *  : �������Z��ʂ̃T���v��No�������X�|���X�f�[�^�֊i�[����B
	 * @param lstGenkaHeader : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageGenkaTr_shisan_shisaku(
			
			  List<?> lstGenkaHeader
			, RequestResponsTableBean resTable
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			for (int i = 0; i < lstGenkaHeader.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGenkaHeader.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "nm_sample",  toString(items[0],""));
				resTable.addFieldVale(i, "seq_shisaku",  toString(items[1],""));

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {

		}
		
	}
	
}
