package jp.co.blueflag.shisaquick.srv.inputcheck;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * 
 * �yJW030�z ����f�[�^��ʃ��N�G�X�g�o�^�i�V�K�j �C���v�b�g�`�F�b�N
 *
 */
public class ShisakuTourokuShinkiInputCheck extends InputCheck {

	/**
	 * �R���X�g���N�^
	 *  : ����f�[�^��� �����\�����C���v�b�g�`�F�b�N�p�R���X�g���N�^
	 */
	public ShisakuTourokuShinkiInputCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ�
	 *  : �e�f�[�^�`�F�b�N�������Ǘ�����B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	public void execInputCheck(
			RequestData checkData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		super.userInfoData = _userInfoData;

		try {
			//USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);

			//SA420�̃C���v�b�g�`�F�b�N���s���B
			exclusiveControlKeyCheck(checkData);
			
			//SA490�̃C���v�b�g�`�F�b�N���s���B
			insertValueCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * �r�����䍀�ڃC���v�b�g�`�F�b�N
	 *  : SA420�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void exclusiveControlKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//���s�敪��1�i��������j�̏ꍇ
			if (checkData.GetValueStr("SA420", 0, 0, "kubun_ziko").equals("1")) {
				//���[�UID�̕K�{�`�F�b�N���s���B
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "id_user"),"���[�UID");
	
			    //�r���敪�̕K�{�`�F�b�N���s���B
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "kubun_haita"),"�r���敪");
				//����R�[�h�̕K�{�`�F�b�N���s���B
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "cd_shain"),"����CD-�Ј�CD");
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "nen"),"����CD-�N");
			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "no_oi"),"����CD-�ǔ�");
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * �o�^���ڃ`�F�b�N
	 *  : SA490�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void insertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�e�[�u���FT110����i�e�[�u���̃C���v�b�g�`�F�b�N�i����i�e�[�u�����ڃ`�F�b�N�j���s���B
			this.shisakuhinInserValueCheck(checkData);
			//�e�[�u���FT120�z���e�[�u���̃C���v�b�g�`�F�b�N�i�z���e�[�u�����ڃ`�F�b�N�j���s���B
			this.haigoInsertValueCheck(checkData);
			//�e�[�u���FT131����e�[�u���̃C���v�b�g�`�F�b�N�i����e�[�u�����ڃ`�F�b�N�j���s���B
			this.shisakuInsertValueCheck(checkData);
			//�e�[�u���FT132���샊�X�g�e�[�u���̃C���v�b�g�`�F�b�N�i���샊�X�g�e�[�u�����ڃ`�F�b�N�j���s���B
			this.shisakuListInsertValueCheck(checkData);
			//�e�[�u���FT133�����H���e�[�u���̃C���v�b�g�`�F�b�N�i�����H���e�[�u�����ڃ`�F�b�N�j���s���B
			this.seizoKoteiInsertValueCheck(checkData);
//			//�e�[�u���FT140�������ރe�[�u���̃C���v�b�g�`�F�b�N�i�������ރe�[�u�����ڃ`�F�b�N�j���s���B
//			this.genkaShizaiInsertValueCheck(checkData);
//			//�e�[�u���FT141���������e�[�u���̃C���v�b�g�`�F�b�N�i���������e�[�u�����ڃ`�F�b�N�j���s���B
//			this.genkaGenryoInsertValueCheck(checkData);
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	
	/**
	 * ����i�e�[�u�� �o�^���ڃ`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakuhinInserValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA490";
			String strTableNm = "tr_shisakuhin";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				///
				///�K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");					//����R�[�h-�N
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");				//����R�[�h-�ǔ�
				super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"����f�[�^��� �i��");
			    ///
				///���͌����`�F�b�N
			    ///
				super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_irai"),"����f�[�^��� �˗��ԍ�",8);
//				super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"����f�[�^��� �i��",100);
				super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"����f�[�^��� �i��",100,50);
				
				try {
					///
					///�K�{���ڂ̓��̓`�F�b�N
					///
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kaisha"),"�w��H��-���CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kojo"),"�w��H��-�H��CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shubetu"),"���CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_shubetu"),"���No");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_group"),"�O���[�vCD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_team"),"�`�[��CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yoryo"),"�e��");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_tani"),"�e�ʒP��CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "su_iri"),"���萔");
				    
				    ///
					///���͌����`�F�b�N
				    ///
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_shubetu"),"���No",2);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "tokuchogenryo"),"��������",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "youto"),"�p�r",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_eigyo"),"�S���c��CD",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hoho_sakin"),"�E�ە��@",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "youki"),"�e����",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "yoryo"),"�e��",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "su_iri"),"���萔",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "shomikikan"),"�ܖ�����",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "genka"),"����",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "baika"),"����",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "buturyo"),"�z�蕨��",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "dt_hatubai"),"��������",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "uriage_k"),"�v�攄��",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "rieki_k"),"�v�旘�v",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "uriage_h"),"�̔��㔄��",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "rieki_h"),"�̔��㗘�v",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_nisugata"),"�׎pCD",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo"),"������",500);
					
				} catch (ExceptionUser ex) {
					//�ԋp���b�Z�[�W�̕ҏW
					ex.setUserMsg("����f�[�^��� ����\�B." + ex.getUserMsg());
					throw ex;
				} finally {
				}
			}
		    
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
	}

	/**
	 * �z���e�[�u�� �o�^���ڃ`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void haigoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA490";
			String strTableNm = "tr_haigo";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				String strAddMsg = "";

				///
				/// �K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");					//����R�[�h-�N
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");				//����R�[�h-�ǔ�

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kotei"),"����f�[�^��� ����\�@ �H��CD");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_kotei"),"����f�[�^��� ����\�@ �H��SEQ");
			    
				//  [����\�@]�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "����f�[�^��� ����\�@ [sort_kotei=" + checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_kotei") + "] :";
				
				///
				/// ���͌����`�F�b�N
				///
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_kotei"),strAddMsg+"�H����",60);
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"��������",60);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"��������",60,30);

				///
				/// ���l���ڂɂ��ẮA�����͈̓`�F�b�N
				///
			    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka")),0,99999.99,strAddMsg+"�P��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),0,999.99,strAddMsg+"����");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),0,999.99,strAddMsg+"���ܗL��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),0,999.99,strAddMsg+"�|�_");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),0,999.99,strAddMsg+"�H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),0,999.99,strAddMsg+"���_");
				}

				///
				/// �����_�ȉ������͂���鍀�ڂɂ��ẮA���������`�F�b�N
				///
			    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka")),2,strAddMsg+"�P��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),2,strAddMsg+"����");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),2,strAddMsg+"���ܗL��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),2,strAddMsg+"�|�_");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),2,strAddMsg+"�H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),2,strAddMsg+"���_");
				}
				
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * ����e�[�u�� �o�^���ڃ`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakuInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA490";
			String strTableNm = "tr_shisaku";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				String strAddMsg = "";
				
				///
				///�K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");					//����R�[�h-�N
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");				//����R�[�h-�ǔ�
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku"),"����f�[�^��� .����SEQ");
			    if ( checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "cd_tani").equals("ml") ) { 
			    	//����i�e�[�u���̗e�ʒP��CD���uml�v�ɐݒ肳��Ă���ꍇ
			    	super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),"����e�[�u�� ��d");
			    }

			    //����e�[�u��.�T���v��NO(����)�̎擾
				String nm_sample = checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_sample");
				if ( nm_sample.equals("") ) {
					nm_sample = "���̖���";		//�T���v��NO����̏ꍇ�A"���̖���"�Ɗi�[����
				}
				//����e�[�u��.����\�����̎擾
				String sort_shisaku = checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_shsiaku");
			    
				//  [����\�@]�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "����f�[�^��� ����\�@ [sort_shisaku=" + sort_shisaku + "] :";
				
				///
				/// [����\�@]���͌����`�F�b�N[����\�@]
				///
			    super.sizeCheckLen(nm_sample,"�T���v��NO�i���́j",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo"),strAddMsg + "����",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_shisaku"),strAddMsg + "���상��",400);

				///
				/// [����\�@]���t�`�F�b�N
				///
//			    super.dateCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "dt_shisaku"), strAddMsg + "������t");

			    
				//  [����\�A]�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "����f�[�^��� ����\�A [sort_shisaku=" + sort_shisaku + ", nm_sample=" + nm_sample + "] :";
				
				///
				/// [����\�A]���͌����`�F�b�N
				///
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_sakusei"),strAddMsg + "�쐬����",150);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nendo"),strAddMsg + "�S�x",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hyoka"),strAddMsg + "�]��",200);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title1"),strAddMsg + "�t���[�@�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value1"),strAddMsg + "�t���[�@���e",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title2"),strAddMsg + "�t���[�A�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value2"),strAddMsg + "�t���[�A���e",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title3"),strAddMsg + "�t���[�B�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value3"),strAddMsg + "�t���[�B���e",20);

				///
				/// [����\�A]���l���ڂɂ��ẮA�����͈̓`�F�b�N
				///
			    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),0,999.99,strAddMsg + "���_");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),0,999.99,strAddMsg + "�H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso")),0,999.99,strAddMsg + "�������_�x");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso")),0,999.99,strAddMsg + "�������H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso").isEmpty()) ) {
					super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso")),0,999.99,strAddMsg + "�������|�_");
				}

				///
				/// [����\�A]�����_�ȉ������͂���鍀�ڂɂ��ẮA���������`�F�b�N
				///
			    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),2,strAddMsg + "���_");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),2,strAddMsg + "�H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso")),2,strAddMsg + "�������_�x");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso")),2,strAddMsg + "�������H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso")),2,strAddMsg + "�������|�_"); 
				}
			    
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	
	/**
	 * ����e�[�u��.����\�����̎擾
	 * @param strShisakuSeq : ����SEQ
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @return ����e�[�u��.����\����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String getShisakuSort(String strShisakuSeq, RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetShisakuSort = "";
		String strKinoNm = "SA490";
		String strTableNm_sub = "tr_shisaku";

		try {
	    	//����\�����̎擾
			for ( int j=0; j<checkData.GetRecCnt(strKinoNm, strTableNm_sub); j++ ) {
				//���샊�X�g.����SEQ = ����.����SEQ
				if ( strShisakuSeq.equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "seq_shisaku")) ) {
					strRetShisakuSort = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "sort_shisaku");
					break;
				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		return strRetShisakuSort;
	}

	/**
	 * �z���e�[�u��.�H�����̎擾
	 * @param strKoteiCd : �H��CD
	 * @param strKoteiSeq : �H��SEQ
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @return �z���e�[�u��.�H����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String getKoteiSort(String strKoteiCd, String strKoteiSeq, RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		String strRetKoteiSort = "";
		String strKinoNm = "SA490";
		String strTableNm_sub = "tr_haigo";

		try {
			//�H�����̎擾
			for ( int j=0; j<checkData.GetRecCnt(strKinoNm, strTableNm_sub); j++ ) {
				//���샊�X�g.�H��CD = �z��.�H��CD & ���샊�X�g.�H��SEQ = �z��.�H��SEQ 
				if ( strKoteiCd.equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "cd_kotei"))
						&& strKoteiSeq.equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "seq_kotei")) ) {
					strRetKoteiSort = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "sort_kotei");
					break;
				}
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
		}
		return strRetKoteiSort;
	}
	
	/**
	 * ���샊�X�g�e�[�u�� �o�^���ڃ`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void shisakuListInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA490";
			String strTableNm = "tr_shisaku_list";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
				///
				///�K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");					//����R�[�h-�N
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");				//����R�[�h-�ǔ�

			    //���샊�X�g.����SEQ
			    String strShisakuSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku");
			    super.hissuInputCheck(strShisakuSeq,"����f�[�^ ���.����SEQ");
			    //���샊�X�g.�H��CD
			    String strKouteiCd = checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kotei");
			    super.hissuInputCheck(strShisakuSeq,"����f�[�^ ���.�H��CD");
			    //���샊�X�g.�H��SEQ
			    String strKouteiSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_kotei");
			    super.hissuInputCheck(strShisakuSeq,"����f�[�^ ���.�H��SEQ");

			    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
			    if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "quantity").isEmpty()) ) {
				    try {
						
						///
						/// ���l���ڂɂ��ẮA�����͈̓`�F�b�N
				    	/// [0�`99999.xxxx]
						///
				    	int intKetaSu = 0;
				    	if ( !checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "keta_shosu").isEmpty() ) {
						    intKetaSu = Integer.parseInt(checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "keta_shosu"));	
				    	}
					    String strKetaSu = "99999";
					    if ( intKetaSu > 0 ) {
					    	strKetaSu += ".";
						    for( int j=0; j<intKetaSu; j++ ) {
						    	strKetaSu += "9";
						    }
					    }
				    	super.rangeNumCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "quantity")),0,Double.parseDouble(strKetaSu),"��");
	
						///
						/// �����_�ȉ������͂���鍀�ڂɂ��ẮA���������`�F�b�N
						///
				    	if ( intKetaSu > 0 ) {
				    		super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "quantity")),intKetaSu,"��");
				    	}
					    
				    } catch ( ExceptionUser ex) {		//�`�F�b�N�Ɉ������������ꍇ
						String strMsg = "";
						String sort_shisaku = "";
						String sort_kotei = "";
						
				    	//����\�����̎擾
						sort_shisaku = getShisakuSort(strShisakuSeq, checkData);
						
						//�H�����̎擾
						sort_kotei = getKoteiSort(strKouteiCd, strKouteiSeq, checkData);
						
						//���b�Z�[�W�̐ݒ�
						strMsg = "����f�[�^ ����\�@";
						strMsg +=  " [sort_shisaku=" + sort_shisaku;
						strMsg += ",sort_kotei=" + sort_kotei;
						strMsg += "] :" + ex.getUserMsg();
						
						ex.setUserMsg(strMsg);
						throw ex;
				    } finally {
				    }
			    }
			    
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

	/**
	 * �����H���e�[�u�� �o�^���ڃ`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void seizoKoteiInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			String strKinoNm = "SA490";
			String strTableNm = "tr_cyuui";
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				///
				///�K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");					//����R�[�h-�N
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");				//����R�[�h-�ǔ�
			    //�����H�� ����SEQ
			    String strShisakuSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku");
			    super.hissuInputCheck(strShisakuSeq,"����f�[�^��� ����SEQ");
			    //�����H�� ����SEQ
			    String strChuiNo = checkData.GetValueStr(strKinoNm, strTableNm, i, "no_chui");
		    	super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_chui"),"����f�[�^��� ���ӎ���No");
			    
			    try {
					///
					/// ���͌����`�F�b�N
					///
				    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "chuijiko"),"���ӎ���",400);
				    
			    } catch (ExceptionUser ex) {

			    	//����\�����̎擾
			    	String sort_shisaku = getShisakuSort(strShisakuSeq, checkData);
								    	
					//�ԋp���b�Z�[�W�̕ҏW
					ex.setUserMsg("����f�[�^��� ����\�@ [sort_shisaku=" + sort_shisaku + ", no_chui=" + strChuiNo + "] :" + ex.getUserMsg());
					throw ex;
				} finally {
				}
			    
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}

//	/**
//	 * �������ރe�[�u�� �o�^���ڃ`�F�b�N
//	 * @param requestData : ���N�G�X�g�f�[�^
//	 * @throws ExceptionWaning 
//	 * @throws ExceptionUser 
//	 * @throws ExceptionSystem 
//	 */
//	private void genkaShizaiInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//
//		try {
//			String strKinoNm = "SA490";
//			String strTableNm = "tr_shizai";
//			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
//				///
//				///�K�{���ڂ̓��̓`�F�b�N
//				///
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����CD-�Ј�CD");		//����R�[�h-�Ј�CD
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����CD-�N");					//����R�[�h-�N
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����CD-�ǔ�");				//����R�[�h-�ǔ�
//			    
//			}
//		} catch (Exception e) {
//			this.em.ThrowException(e, "");
//		} finally {
//			
//		}
//	}
//
//	/**
//	 * ���������e�[�u�� �o�^���ڃ`�F�b�N
//	 * @param requestData : ���N�G�X�g�f�[�^
//	 * @throws ExceptionWaning 
//	 * @throws ExceptionUser 
//	 * @throws ExceptionSystem 
//	 */
//	private void genkaGenryoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//
//		try {
//			String strKinoNm = "SA490";
//			String strTableNm = "tr_genryo";
//			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
//				///
//				///�K�{���ڂ̓��̓`�F�b�N
//				///
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����CD-�Ј�CD");		//����R�[�h-�Ј�CD
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����CD-�N");					//����R�[�h-�N
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����CD-�ǔ�");				//����R�[�h-�ǔ�
//			    
//			}
//		} catch (Exception e) {
//			this.em.ThrowException(e, "");
//		} finally {
//			
//		}
//	}	
	

}
