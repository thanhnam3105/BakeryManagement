package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * JWS-����e�[�u���Ǘ�DB����
 *  : JWS-����e�[�u���Ǘ�DB�����̂c�a�ɑ΂���Ɩ����W�b�N�̎���
 *  
 * @author TT.k-katayama
 * @since  2009/04/15
 *
 */
public class ShisakuTblKanriDataChekck extends LogicBase {
	
	/**
	 * �R���X�g���N�^
	 */
	public ShisakuTblKanriDataChekck() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * ����e�[�u���Ǘ�����
	 * @param reqData : �@�\���N�G�X�g�f�[�^
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
		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�@:�g�����U�N�V�������J�n����
			super.createExecDB();								//DB�X�V�̐���
			this.execDB.BeginTran();							//�g�����U�N�V�����J�n			
			
			try {

				//�A:T110 ����i�e�[�u��(tr_shisakuhin)�̍폜�E�o�^�������s��
				this.shisakuhinKanriDeleteSQL(reqData);
				this.shisakuhinKanriInsertSQL(reqData);
				//�B:T120 �z���e�[�u��(tr_haigo)�̍폜�E�o�^�������s��
				this.haigoKanriDeleteSQL(reqData);
				this.haigoKanriInsertSQL(reqData);
				//�C:T131 ����e�[�u��(tr_shisaku)�̍폜�E�o�^�������s��
				this.shisakuKanriDeleteSQL(reqData);
				this.shisakuKanriInsertSQL(reqData);
				//�D:T132 ���샊�X�g�e�[�u��(tr_shisaku_list)�̍폜�E�o�^�������s��
				this.shisakuListKanriDeleteSQL(reqData);
				this.shisakuListKanriInsertSQL(reqData);
				//�E:T133 �����H���e�[�u��(tr_cyuui)�̍폜�E�o�^�������s��
				this.seizoKouteiKanriDeleteSQL(reqData);
				this.seizoKouteiKanriInsertSQL(reqData);
				
//				//�F:T140 �������ރe�[�u��(tr_shizai)�̍폜�E�o�^�������s��(�񎟕�)
//				this.genkaShizaiKanriDeleteSQL();
//				this.genkaShizaiKanriInsertSQL();
//				//�G:T141 ���������e�[�u��(tr_genryo)�̍폜�E�o�^�������s��(�񎟕�)
//				this.genkaGenryoKanriDeleteSQL();
//				this.genkaGenryoKanriInsertSQL();

				//�H:�R�~�b�g/���[���o�b�N���������s����
				this.execDB.Commit();							//�R�~�b�g
				
			} catch(Exception e) {
				this.execDB.Rollback();							//���[���o�b�N
				this.em.ThrowException(e, "");
				
			} finally {
				
			}
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//�I�F����I�����A�Ǘ����ʃp�����[�^�[�i�[���\�b�h���ďo���A���X�|���X�f�[�^���`������B
			this.storageKengenDataKanri(resKind.getTableItem(strTableNm));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����e�[�u���Ǘ����쏈�������s���܂����B");
			
		} finally {
			if (execDB != null) {
				execDB.Close();				//�Z�b�V�����̃N���[�Y
				execDB = null;
				
			}
			
		}
		return resKind;

	}

	/**
	 * ����i�f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuhinKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			//�@�F���N�G�X�g�f�[�^��p���A����i�f�[�^�폜�pSQL���쐬����B
			
			//���N�G�X�g�f�[�^�擾
			String strReqShainCd = reqData.getFieldVale("tr_shisakuhin", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisakuhin", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisakuhin", 0, "no_oi");
			
			//�폜�pSQL�쐬
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisakuhin ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");
						
			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A����i�f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����i�f�[�^�폜SQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			
		}
	}
	/**
	 * ����i�f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuhinKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();
		
		try {
			
			//�@�F���N�G�X�g�f�[�^��p���A����i�f�[�^�o�^�pSQL���쐬����B
			for ( int i=0; i<reqData.getCntRow("tr_shisakuhin"); i++ ) {
				
				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");
					
				}
				strSQL_values.append(" SELECT ");
				
				//�l��SQL�ɐݒ肵�Ă���
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisakuhin", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "no_oi") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "no_irai") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "nm_hin") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_kaisha")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_kojo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_shubetu") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "no_shubetu")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_group")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "cd_team")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_ikatu") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_genre") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_user") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "tokuchogenryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "youto") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_kakaku") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_eigyo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_hoho") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_juten") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "hoho_sakin") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "youki") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "yoryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_tani") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "su_iri") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_ondo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "shomikikan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "genka") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "baika") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "buturyo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_hatubai") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "uriage_k") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "rieki_k") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "uriage_h") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "rieki_h") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "cd_nisugata") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisakuhin", i, "memo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "keta_shosu")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "kbn_haishi")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "kbn_haita")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisakuhin", i, "seq_shisaku")) );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_toroku") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisakuhin", i, "id_koshin") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisakuhin", i, "dt_koshin") + "'" );

			}

			//�o�^�pSQL�쐬
			strSQL.append(" INSERT INTO tr_shisakuhin ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");
						
			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A����i�f�[�^�̓o�^���s���B
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����i�f�[�^�o�^SQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;
			
		}
		
	}

	/**
	 * �z���f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void haigoKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			//�@�F���N�G�X�g�f�[�^��p���A�z���f�[�^�폜�pSQL���쐬����B
			
			//���N�G�X�g�f�[�^�擾
			String strReqShainCd = reqData.getFieldVale("tr_haigo", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_haigo", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_haigo", 0, "no_oi");
			
			//�폜�pSQL�쐬
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_haigo ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");
			
			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�z���f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�z���f�[�^�폜SQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			
		}
	}
	/**
	 * �z���f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void haigoKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();
		
		try {
			
			//�@�F���N�G�X�g�f�[�^��p���A�z���f�[�^�o�^�pSQL���쐬����B
			for ( int i=0; i<reqData.getCntRow("tr_haigo"); i++ ) {
				
				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");
					
				}
				strSQL_values.append(" SELECT ");
				
				//�l��SQL�ɐݒ肵�Ă���
				strSQL_values.append(" " + reqData.getFieldVale("tr_haigo", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "cd_kotei") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "seq_kotei") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "nm_kotei") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "zoku_kotei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "sort_kotei")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "sort_genryo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "cd_genryo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "cd_kaisha")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_haigo", i, "cd_busho")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "nm_genryo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "tanka") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "budomari") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_abura") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_sakusan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_shokuen") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "ritu_sousan") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_haigo", i, "color") + "'") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_haigo", i, "dt_toroku") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_haigo", i, "cd_shain") );
				strSQL_values.append(" ,GETDATE()" );

			}

			//�o�^�pSQL�쐬
			strSQL.append(" INSERT INTO tr_haigo ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");
			
			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�z���f�[�^�̓o�^���s���B
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�z���f�[�^�o�^SQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;
			
		}
		
	}

	/**
	 * ����f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			//�@�F���N�G�X�g�f�[�^��p���A����f�[�^�폜�pSQL���쐬����B
			
			//���N�G�X�g�f�[�^�擾
			String strReqShainCd = reqData.getFieldVale("tr_shisaku", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisaku", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisaku", 0, "no_oi");
			
			//�폜�pSQL�쐬
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisaku ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");
			
			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A����f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^�폜SQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			
		}
	}
	/**
	 * ����f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();
		
		try {

			//�@�F���N�G�X�g�f�[�^��p���A����f�[�^�o�^�pSQL���쐬����B
			for ( int i=0; i<reqData.getCntRow("tr_shisaku"); i++ ) {
				
				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");
					
				}
				strSQL_values.append(" SELECT ");
				
				//�l��SQL�ɐݒ肵�Ă���
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisaku", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "seq_shisaku") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sort_shisaku")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "no_chui")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "nm_sample") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo_shisaku") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_print")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_auto")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "no_shisan")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho1") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho2") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho3") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho4") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "no_seiho5") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_sousan")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sousan")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_shokuen")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sando_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sando_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "shokuen_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "sakusan_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sakusan_suiso")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "toudo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_toudo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "nendo") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_nendo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ondo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_ondo")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ph")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_ph")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_sousan_bunseki")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_sousan_bunseki")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "ritu_shokuen_bunseki")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_shokuen_bunseki")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "hiju")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_hiju")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "suibun_kasei")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_suibun_kasei")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "alcohol")) );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_alcohol")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "memo_sakusei") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_memo")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "hyoka") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_hyoka")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title1") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value1") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free1")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title2") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value2") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free2")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_title3") + "'") );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "free_value3") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "flg_free3")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku", i, "dt_shisaku") + "'") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku", i, "juryo_shiagari_g")));
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku", i, "dt_toroku") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku", i, "cd_shain") );
				strSQL_values.append(" ,GETDATE()" );

			}

			//�o�^�pSQL�쐬
			strSQL.append(" INSERT INTO tr_shisaku ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");
			
			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A����f�[�^�̓o�^���s���B
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "����f�[�^�o�^SQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;
			
		}
		
	}

	/**
	 * ���샊�X�g�f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuListKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
			
			//�@�F���N�G�X�g�f�[�^��p���A���샊�X�g�f�[�^�폜�pSQL���쐬����B
			
			//���N�G�X�g�f�[�^�擾
			String strReqShainCd = reqData.getFieldVale("tr_shisaku_list", 0, "cd_shain");
			String strReqNen = reqData.getFieldVale("tr_shisaku_list", 0, "nen");
			String strReqOiNo = reqData.getFieldVale("tr_shisaku_list", 0, "no_oi");
			
			//�폜�pSQL�쐬
			strSQL.append(" DELETE ");
			strSQL.append("  FROM tr_shisaku_list ");
			strSQL.append("  WHERE ");
			strSQL.append("   cd_shain= " + strReqShainCd);
			strSQL.append("   AND nen= " + strReqNen);
			strSQL.append("   AND no_oi= " + strReqOiNo);
			strSQL.append("  ");
			
			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̍폜���s���B
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���샊�X�g�f�[�^�폜SQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			
		}
		
	}
	/**
	 * ���샊�X�g�f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void shisakuListKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {
			
			//�@�F���N�G�X�g�f�[�^��p���A���샊�X�g�f�[�^�o�^�pSQL���쐬����B
			for ( int i=0; i<reqData.getCntRow("tr_shisaku_list"); i++ ) {
				if ( strSQL_values.length() != 0 ) {
					strSQL_values.append(" UNION ALL ");
				}
				strSQL_values.append(" SELECT ");
				
				//�l��SQL�ɐݒ肵�Ă���
				strSQL_values.append(" " + reqData.getFieldVale("tr_shisaku_list", i, "cd_shain") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "nen") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "no_oi") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "seq_shisaku") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "cd_kotei") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "seq_kotei") );
				strSQL_values.append(" ," + checkNull(reqData.getFieldVale("tr_shisaku_list", i, "quantity")) );
				strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_shisaku_list", i, "color") + "'") );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "id_toroku") );
				strSQL_values.append(" ,'" + reqData.getFieldVale("tr_shisaku_list", i, "dt_toroku") + "'" );
				strSQL_values.append(" ," + reqData.getFieldVale("tr_shisaku_list", i, "cd_shain") );
				strSQL_values.append(" ,GETDATE()" );

			}

			//�o�^�pSQL�쐬
			strSQL.append(" INSERT INTO tr_shisaku_list ");
			strSQL.append(strSQL_values.toString());
			strSQL.append("");
			
			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���샊�X�g�f�[�^�̓o�^���s���B
			this.execDB.execSQL(strSQL.toString());
			
		} catch (Exception e) {
			this.em.ThrowException(e, "���샊�X�g�f�[�^�o�^SQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;
			
		}
		
	}

	/**
	 * �����H���f�[�^�폜SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void seizoKouteiKanriDeleteSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		
		try {
						
			//�����H���̃e�[�u��������0���ł͂Ȃ��ꍇ�A�����𑱍s����
			if ( reqData.getCntRow("tr_cyuui") != 0 ) {

				//�@�F���N�G�X�g�f�[�^��p���A�����H���f�[�^�폜�pSQL���쐬����B
				
				//���N�G�X�g�f�[�^�擾
				String strReqShainCd = reqData.getFieldVale("tr_cyuui", 0, "cd_shain");
				String strReqNen = reqData.getFieldVale("tr_cyuui", 0, "nen");
				String strReqOiNo = reqData.getFieldVale("tr_cyuui", 0, "no_oi");
				
				//�폜�pSQL�쐬
				strSQL.append(" DELETE ");
				strSQL.append("  FROM tr_cyuui ");
				strSQL.append("  WHERE ");
				strSQL.append("   cd_shain= " + strReqShainCd);
				strSQL.append("   AND nen= " + strReqNen);
				strSQL.append("   AND no_oi= " + strReqOiNo);
				strSQL.append("  ");
				
				//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�����H���f�[�^�̍폜���s���B
				this.execDB.execSQL(strSQL.toString());
				
			}			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����H���f�[�^�폜SQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			
		}
		
	}
	/**
	 * �����H���f�[�^�o�^SQL�쐬
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void seizoKouteiKanriInsertSQL(RequestResponsKindBean reqData)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		StringBuffer strSQL = new StringBuffer();
		StringBuffer strSQL_values = new StringBuffer();

		try {

			//�����H���̃e�[�u��������0���ł͂Ȃ��ꍇ�A�����𑱍s����
			if ( reqData.getCntRow("tr_cyuui") != 0 ) {
			
				//�@�F���N�G�X�g�f�[�^��p���A�����H���f�[�^�o�^�pSQL���쐬����B
				for ( int i=0; i<reqData.getCntRow("tr_cyuui"); i++ ) {
					if ( strSQL_values.length() != 0 ) {
						strSQL_values.append(" UNION ALL ");
					}
					strSQL_values.append(" SELECT ");
					
					//�l��SQL�ɐݒ肵�Ă���
					strSQL_values.append(" " + reqData.getFieldVale("tr_cyuui", i, "cd_shain") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "nen") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "no_oi") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "seq_shisaku") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "no_chui") );
					strSQL_values.append(" ," + checkNull("'" + reqData.getFieldVale("tr_cyuui", i, "chuijiko") + "'") );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "id_toroku") );
					strSQL_values.append(" ,'" + reqData.getFieldVale("tr_cyuui", i, "dt_toroku") + "'" );
					strSQL_values.append(" ," + reqData.getFieldVale("tr_cyuui", i, "cd_shain") );
					strSQL_values.append(" ,GETDATE()" );
	
				}
	
				//�o�^�pSQL�쐬
				strSQL.append(" INSERT INTO tr_cyuui ");
				strSQL.append(strSQL_values.toString());
				strSQL.append("");			
				
				//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�����H���f�[�^�̓o�^���s���B
				this.execDB.execSQL(strSQL.toString());
			
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�����H���f�[�^�o�^SQL�쐬���������s���܂����B");
			
		} finally {
			//�ϐ��̍폜
			strSQL = null;
			strSQL_values = null;
			
		}
		
	}

//	/**
//	 * �������ރf�[�^�폜SQL�쐬
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaShizaiKanriDeleteSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//			
//			//�@�F���N�G�X�g�f�[�^��p���A�������ރf�[�^�폜�pSQL���쐬����B
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//			
//			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�������ރf�[�^�̍폜���s���B
//			this.execDB.execSQL(strSQL.toString());
//			
//		} catch (Exception e) {
//			this.em.ThrowException(e, "�������ރf�[�^�폜SQL�쐬���������s���܂����B");
//		} finally {
//		}
//	}
//	/**
//	 * �������ރf�[�^�o�^SQL�쐬
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaShizaiKanriInsertSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//			
//			//�@�F���N�G�X�g�f�[�^��p���A�������ރf�[�^�o�^�pSQL���쐬����B
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//			
//			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A�������ރf�[�^�̓o�^���s���B
//			this.execDB.execSQL(strSQL.toString());
//			
//		} catch (Exception e) {
//			this.em.ThrowException(e, "�������ރf�[�^�o�^SQL�쐬���������s���܂����B");
//		} finally {
//		}
//	}

//	/**
//	 * ���������f�[�^�폜SQL�쐬
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaGenryoKanriDeleteSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//			
//			//�@�F���N�G�X�g�f�[�^��p���A���������f�[�^�폜�pSQL���쐬����B
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//			
//			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���������f�[�^�̍폜���s���B
//			this.execDB.execSQL(strSQL.toString());
//			
//		} catch (Exception e) {
//			this.em.ThrowException(e, "���������f�[�^�폜SQL�쐬���������s���܂����B");
//		} finally {
//		}
//	}
//	/**
//	 * ���������f�[�^�o�^SQL�쐬
//	 * @throws ExceptionSystem
//	 * @throws ExceptionUser
//	 * @throws ExceptionWaning
//	 */
//	private void genkaGenryoKanriInsertSQL() throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//		try {
//			StringBuffer strSQL = new StringBuffer();
//			
//			//�@�F���N�G�X�g�f�[�^��p���A���������f�[�^�o�^�pSQL���쐬����B
//			String strReqShoriKbn = reqData.getFieldVale(0, 0, "kbn_shori");
//			
//			//�A�F���ʃN���X�@�f�[�^�x�[�X�Ǘ���p���A���������f�[�^�̓o�^���s���B
//			this.execDB.execSQL(strSQL.toString());
//			
//		} catch (Exception e) {
//			this.em.ThrowException(e, "���������f�[�^�o�^SQL�쐬���������s���܂����B");
//		} finally {
//		}
//	}
	
	/**
	 * �Ǘ����ʃp�����[�^�[�i�[
	 *  : ����e�[�u���f�[�^�̊Ǘ����ʏ������X�|���X�f�[�^�֊i�[����B
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageKengenDataKanri(RequestResponsTableBean resTable) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {			
			//�@�F���X�|���X�f�[�^���`������B

			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�Ǘ����ʃp�����[�^�[�i�[���������s���܂����B");
			
		} finally {

		}
		
	}
	
	/**
	 * Null�`�F�b�N
	 * @param strValue �F �`�F�b�N�l
	 * @return ����(�l����̏ꍇ�ANULL��Ԃ�)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private String checkNull(String strChkValue) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetValue = strChkValue;
		
		try {
			if ( strRetValue.equals("") ) {
				strRetValue = "NULL";
			} else if ( strRetValue.equals("''")) {
				strRetValue = "NULL";
			}
		} catch(Exception e) {
			this.em.ThrowException(e, "Null�`�F�b�N���������s���܂����B");
		} finally {
		}
		
		return strRetValue; 
	}

}
