package jp.co.blueflag.shisaquick.srv.inputcheck;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import org.springframework.util.StringUtils;

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

//2009/09/30 TT.A.ISONO DEL START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]
//����f�[�^�̔r������́A�o�^���ɍs�����߁ASA420�̓J�b�g

			//SA420�̃C���v�b�g�`�F�b�N���s���B
			//exclusiveControlKeyCheck(checkData);

//2009/09/30 TT.A.ISONO DEL END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

			//SA490�̃C���v�b�g�`�F�b�N���s���B
			insertValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}
	}

//	/**
//	 * �r�����䍀�ڃC���v�b�g�`�F�b�N
//	 *  : SA420�̃C���v�b�g�`�F�b�N���s���B
//	 * @param requestData : ���N�G�X�g�f�[�^
//	 * @throws ExceptionWaning
//	 * @throws ExceptionUser
//	 * @throws ExceptionSystem
//	 */
//	private void exclusiveControlKeyCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {
//
//		try {
//			//���s�敪��1�i��������j�̏ꍇ
//			if (checkData.GetValueStr("SA420", 0, 0, "kubun_ziko").equals("1")) {
//				//���[�UID�̕K�{�`�F�b�N���s���B
////			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "id_user"),"���[�UID");
//
//			    //�r���敪�̕K�{�`�F�b�N���s���B
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "kubun_haita"),"�r���敪");
//				//����R�[�h�̕K�{�`�F�b�N���s���B
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "cd_shain"),"����CD-�Ј�CD");
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "nen"),"����CD-�N");
//			    super.hissuInputCheck(checkData.GetValueStr("SA420", 0, 0, "no_oi"),"����CD-�ǔ�");
//
//			}
//		} catch (Exception e) {
//			this.em.ThrowException(e, "");
//		} finally {
//
//		}
//	}

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
			//�f�[�^�����`�F�b�N�i�z���ʃZ�����`�F�b�N�j
			super.DataCellCheck(checkData);

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
			//�e�[�u���FT141���������e�[�u���̃C���v�b�g�`�F�b�N�i���������e�[�u�����ڃ`�F�b�N�j���s���B
			this.genkaGenryoInsertValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}

	/**
	 * ����i�e�[�u�� �o�^���ڃ`�F�b�N
	 *  : SA490�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shisakuhinInserValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";
		String strTableNm = "tr_shisakuhin";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				///�K�{���ڂ̓��̓`�F�b�N
				///
				//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");
			    //����R�[�h-�N
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");

//2009/09/30 TT.A.ISONO DEL START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

			    //����R�[�h-�ǔ�
//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");

//2009/09/30 TT.A.ISONO DEL END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"����f�[�^��� �i��");
				//super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_shubetu"),"����f�[�^��� ��ʔԍ�");

			    ///
				///���͌����`�F�b�N
			    ///
				super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_irai"),"����f�[�^��� �˗��ԍ�",8);
				super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"����f�[�^��� �i��",100,50);

				//�yQP@00342�z
				//URL�\�񕶎��`�F�b�N
				super.checkStringURL(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_hin"),"����f�[�^��� �i��");

				super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_shubetu"),"����f�[�^��� ��ʔԍ�",2);
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.21
//				super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_shisaku"),"������",400);
				String strShisakuMemo = checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_shisaku");
				super.sizeCheckLen(strShisakuMemo.replaceAll("\n", ""),"������",1000);
				super.sizeCheckLen(strShisakuMemo,"������",2000);
//mod end --------------------------------------------------------------------------------------

				try {

					///
					///�K�{���ڂ̓��̓`�F�b�N
					///
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kaisha"),"�w��H��-���CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kojo"),"�w��H��-�H��CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shubetu"),"���CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_group"),"�O���[�vCD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_team"),"�`�[��CD");
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yoryo"),"�e��");

//mod start --------------------------------------------------------------------------------------
//QP@10181_�V�T�N�C�b�N����
//					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_tani"),"�e�ʒP��CD");
					String strChkPrm = checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_tani");
					// �`�F�b�N�p�����[�^�[�̕K�{���̓`�F�b�N���s���B
					if (strChkPrm.equals(null) || strChkPrm.equals("")) {
						em.ThrowException(ExceptionKind.���Exception, "E000412", "", "", "");
					}
//mod end ---------------------------------------------------------------------------------------

					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "su_iri"),"���萔");

				    ///
					///���͌����`�F�b�N
				    ///
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
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "dt_hatubai"),"�̔�����",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "uriage_k"),"�v�攄��",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "rieki_k"),"�v�旘�v",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "uriage_h"),"�̔��㔄��",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "rieki_h"),"�̔��㗘�v",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_nisugata"),"�׎pCD",60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo"),"������",500);

				} catch (ExceptionUser ex) {
					//�ԋp���b�Z�[�W�̕ҏW
					ex.setUserMsg("����f�[�^��� ��{��� " + ex.getUserMsg());
					throw ex;

				} finally {

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;

		}
	}

	/**
	 * �z���e�[�u�� �o�^���ڃ`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void haigoInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";
		String strTableNm = "tr_haigo";
		String strAddMsg = "";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				/// �K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");					//����R�[�h-�N

//2009/09/30 TT.A.ISONO DEL START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");				//����R�[�h-�ǔ�

//2009/09/30 TT.A.ISONO DEL END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kotei"),"����f�[�^��� �z���\ �H��CD");
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_kotei"),"����f�[�^��� �z���\ �H��SEQ");

				//  [����\�@]�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "����f�[�^��� �z���\ [�H���F" + checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_kotei") + "]";

				///
				/// ���͌����`�F�b�N(�H����)
				///
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_kotei"),strAddMsg+"�H����",60);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_kotei"),strAddMsg+"�H����",60,30);

				//  [����\�@]�ǉ����b�Z�[�W�̕ҏW
			    String genryoCd = checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_genryo");
				if(!(genryoCd != null && genryoCd.length() > 0)){
					genryoCd += "������";
				}
				strAddMsg = "����f�[�^��� �z���\ [�H���F" + checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_kotei");
				strAddMsg +=  ", �������ށF" + genryoCd + "]";

				///
				/// ���͌����`�F�b�N(��������)
				///
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"��������",60);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_genryo"),strAddMsg+"��������",60,30);

				///
				/// ���l���ڂɂ��ẮA�����͈̓`�F�b�N
				///
			    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka").isEmpty()) ) {
					super.rangeNumCheck(
							new BigDecimal(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka")))
							, new BigDecimal("0")
							, new BigDecimal("99999999.99")
							, strAddMsg+"�P��");
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
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"),2,strAddMsg+"�P��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari")),3,2,strAddMsg+"����");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_abura")),3,2,strAddMsg+"���ܗL��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sakusan")),3,2,strAddMsg+"�|�_");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),3,2,strAddMsg+"�H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),3,2,strAddMsg+"���_");
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;

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

		String strKinoNm = "SA490";
		String strTableNm = "tr_shisaku";
		String strAddMsg = "";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				///�K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");					//����R�[�h-�N

//2009/09/30 TT.A.ISONO DEL START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");				//����R�[�h-�ǔ�

//2009/09/30 TT.A.ISONO DEL END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku"),"����f�[�^��� .����SEQ");
			    //if ( checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "cd_tani").equals(ConstManager.getConstValue(Category.�ݒ�, "ML_TANI_CD")) ) {
			    	//����i�e�[�u���̗e�ʒP��CD���uml�v�ɐݒ肳��Ă���ꍇ
			    	//super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),"����e�[�u�� ��d");
			    //}

			    //����e�[�u��.�T���v��NO(����)�̎擾
				String nm_sample = checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_sample");
				if ( nm_sample.equals("") ) {
					nm_sample = "���̖���";		//�T���v��NO����̏ꍇ�A"���̖���"�Ɗi�[����
				}
				//����e�[�u��.����\�����̎擾
				String sort_shisaku = checkData.GetValueStr(strKinoNm, strTableNm, i, "sort_shisaku");

				//  [����\�@]�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "����f�[�^��� �z���\ [�����F" + sort_shisaku;
				strAddMsg += ", �T���v��NO(����)�F" + nm_sample + "]";

				///
				/// [����\�@]���t�`�F�b�N
				///
			    if ( !checkData.GetValueStr(strKinoNm, strTableNm, i, "dt_shisaku").isEmpty() ) {
			    	super.dateCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "dt_shisaku"), strAddMsg + "������t");
			    }

				///
				/// [����\�@]���͌����`�F�b�N[����\�@]
				///
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.7
			    //super.sizeCheckLen(nm_sample,strAddMsg + "�T���v��NO�i���́j",20);
			    super.sizeCheckLen(nm_sample,strAddMsg + "�T���v��NO�i���́j",200);
//mod end ----------------------------------------------------------------------------------------

			    //�yQP@20505�zNo5 2012/10/12 TT H.SHIMA MOD Start
//			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo"),strAddMsg + "����",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo"),strAddMsg + "����",50);
				//�yQP@20505�zNo5 2012/10/12 TT H.SHIMA MOD End
			    //super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_shisaku"),strAddMsg + "���상��",400);

				//  [����\�A]�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "����f�[�^��� �����l [�����F" + sort_shisaku;
				strAddMsg += ", �T���v��NO(����)�F" + nm_sample + "]";

				///
				/// [����\�A]���͌����`�F�b�N
				///
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_sakusei"),strAddMsg + "�쐬����",150);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nendo"),strAddMsg + "�S�x",20,10);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hyoka"),strAddMsg + "�]��",200);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title1"),strAddMsg + "�t���[�@�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value1"),strAddMsg + "�t���[�@���e",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title2"),strAddMsg + "�t���[�A�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value2"),strAddMsg + "�t���[�A���e",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title3"),strAddMsg + "�t���[�B�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value3"),strAddMsg + "�t���[�B���e",20);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "toudo"),strAddMsg + "���x",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ondo"),strAddMsg + "���x",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ph"),strAddMsg + "PH",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan_bunseki"),strAddMsg + "���_�F����",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen_bunseki"),strAddMsg + "�H���F����",20,10);
			    super.sizeHalfFullLengthCheck_hankaku(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),strAddMsg + "��d",20,10);
			    super.sizeHalfFullLengthCheck_hankaku(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju_sui"),strAddMsg + "������d",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "suibun_kasei"),strAddMsg + "��������",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "alcohol"),strAddMsg + "�A���R�[��",20,10);
// ADD start 20121003 QP@20505 No.24
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "freetitle_suibun_kasei"),strAddMsg + "�t���[���������^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_suibun_kasei"),strAddMsg + "�t���[��������",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "freetitle_alcohol"),strAddMsg + "�t���[�A���R�[���^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_alcohol"),strAddMsg + "�t���[�A���R�[��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "freetitle_nendo"),strAddMsg + "�t���[�S�x�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_nendo"),strAddMsg + "�t���[�S�x",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "freetitle_ondo"),strAddMsg + "�t���[���x�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_ondo"),strAddMsg + "�t���[���x",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title4"),strAddMsg + "�t���[�C�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value4"),strAddMsg + "�t���[�C���e",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title5"),strAddMsg + "�t���[�D�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value5"),strAddMsg + "�t���[�D���e",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_title6"),strAddMsg + "�t���[�E�^�C�g��",20);
			    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "free_value6"),strAddMsg + "�t���[�E���e",20);
// ADD end 20121003 QP@20505 No.24
			    ///
				/// [����\�A]���x�`�A���R�[���̒l�́A���l�`�F�b�N���s��
				///
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "toudo").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "toudo"),strAddMsg + "���x");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ondo").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ondo"),strAddMsg + "���x");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ph").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ph"),strAddMsg + "PH");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan_bunseki").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan_bunseki"),strAddMsg + "���_�F����");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen_bunseki").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen_bunseki"),strAddMsg + "�H���F����");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),strAddMsg + "��d");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "suibun_kasei").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "suibun_kasei"),strAddMsg + "��������");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "alcohol").isEmpty()) ) {
					this.NumericCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "alcohol"),strAddMsg + "�A���R�[��");
				}

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
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_g").isEmpty()) ) {
					//super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_g"),10,4,strAddMsg + "���v�d��d��");
					super.rangeNumCheck(
								new BigDecimal(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_g"))
								,new BigDecimal("0")
								,new BigDecimal("9999999999.9999")
								,strAddMsg + "���v�d��d��");
				}

				///
				/// [����\�A]�����_�ȉ������͂���鍀�ڂɂ��ẮA���������`�F�b�N
				///
			    //���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_sousan")),3,2,strAddMsg + "���_");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "ritu_shokuen")),3,2,strAddMsg + "�H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sando_suiso")),3,2,strAddMsg + "�������_�x");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "shokuen_suiso")),3,2,strAddMsg + "�������H��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso").isEmpty()) ) {
					super.shousuRangeCheck(Double.parseDouble(checkData.GetValueStr(strKinoNm, strTableNm, i, "sakusan_suiso")),3,2,strAddMsg + "�������|�_");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_g").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_g"),10,4,strAddMsg + "���v�d��d��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju").isEmpty()) ) {
					if(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju").indexOf(".") != -1){
						super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"), 3, strAddMsg + "��d");
					}
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;

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
	private String getShisakuSort(String strShisakuSeq, RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strRetShisakuSort = "";
		String strKinoNm = "SA490";
		String strTableNm_sub = "tr_shisaku";

		try {

			//����\�����̎擾
			for ( int j=0; j<checkData.GetRecCnt(strKinoNm, strTableNm_sub); j++ ) {

				//���샊�X�g.����SEQ = ����.����SEQ
				if ( strShisakuSeq.equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "seq_shisaku")) ) {

					strRetShisakuSort = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "sort_shisaku") + "(�����NO�F";

					String strSample = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "nm_sample");
					if(strSample != null && strSample.length() > 0){
						strRetShisakuSort = strRetShisakuSort  + checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "nm_sample") + ")";
					}else{
						strRetShisakuSort = strRetShisakuSort + "���̖���)";
					}

					break;

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm_sub = null;

		}
		return strRetShisakuSort;

	}

	/**
	 * �z���e�[�u��.�H�����E�������̎擾
	 * @param strKoteiCd : �H��CD
	 * @param strKoteiSeq : �H��SEQ
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @return �z���e�[�u��.�H���� + ":::" + �z���e�[�u��.������
	 *             ������u:::�v�ŋ�؂�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String getKoteiSort(String strKoteiCd, String strKoteiSeq, RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

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
					strRetKoteiSort += ":::";

					String genryoCd = checkData.GetValueStr(strKinoNm, strTableNm_sub, j, "cd_genryo");
					if(genryoCd != null && genryoCd.length() > 0){
						strRetKoteiSort += genryoCd;
					}else{
						strRetKoteiSort += "������";
					}

					break;

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm_sub = null;

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
	private void shisakuListInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";
		String strTableNm = "tr_shisaku_list";

		//ADD 2012.4.30�@�yH24�N�x�Ή��z�C��4�@�����̓��͂ɑ΂��錴���̓��̓`�F�b�N���s���B
		String strSeq_shisaku = "";//�u���[�N�L�[:����SEQ
		String strFlg_shisanIrai = "";//����˗��t���O(1�ň˗��ς�)
		String strCd_genryo = "";//�����R�[�h
		String strNm_genryo = "";//������
		int intRecIndex = 0;//�擾���R�[�h�C���f�b�N�X
		String strFlg_cancel = "";//����˗��L�����Z��
		// 20160913 ADD KPX@1600766 Start
		String nm_sample = "";// �T���v����
		// 20160913 ADD KPX@1600766 End

		try {
			// 20160915 MOD KPX@1600766 Start
	    	// 20160420 ADD KPX@1600766 Start
//			String errGenryo = ""; // �G���[�������
	    	StringBuffer errGenryo = new StringBuffer(""); // �G���[�������
	    	// 20160420 ADD KPX@1600766 End
	    	// 20160915 MOD KPX@1600766 End

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				///�K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");					//����R�[�h-�N

//2009/09/30 TT.A.ISONO DEL START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");				//����R�[�h-�ǔ�

//2009/09/30 TT.A.ISONO DEL END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

			    //���샊�X�g.����SEQ
			    String strShisakuSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku");
			    super.hissuInputCheck(strShisakuSeq,"����f�[�^��� ����SEQ");
			    //���샊�X�g.�H��CD
			    String strKouteiCd = checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kotei");
			    super.hissuInputCheck(strShisakuSeq,"����f�[�^��� �H��CD");
			    //���샊�X�g.�H��SEQ
			    String strKouteiSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_kotei");
			    super.hissuInputCheck(strShisakuSeq,"����f�[�^��� �H��SEQ");

				//ADD 2012.4.30�@�yH24�N�x�Ή��z�C��4�@�����̓��͂ɑ΂��錴���̓��̓`�F�b�N���s���B
				//����˗��ς݃t���O������e�[�u�����擾�ۑ�����B
				if( !strSeq_shisaku.equals(strShisakuSeq)){
					intRecIndex = getTableIndex(checkData,"tr_shisaku","seq_shisaku",strShisakuSeq);
					strFlg_shisanIrai = checkData.GetValueStr(strKinoNm, "tr_shisaku", intRecIndex, "flg_shisanIrai");
					strFlg_cancel = checkData.GetValueStr(strKinoNm, "tr_shisaku", intRecIndex, "flg_cancel");
				}

				// 20160913 ADD KPX@1600766 Start
				nm_sample = checkData.GetValueStr(strKinoNm, "tr_shisaku", intRecIndex, "nm_sample");
				// 20160913 ADD KPX@1600766 End

				//���@���L�̍��ڂ͒l��NULL(�󕶎�)�ł͂Ȃ��ꍇ�A�`�F�b�N���s���B
			    String strRyo = checkData.GetValueStr(strKinoNm, strTableNm, i, "quantity");
			    if ( !(strRyo.isEmpty()) ) {

				    try {

						///
						/// ���l���ڂɂ��ẮA�����͈̓`�F�b�N
				    	/// [0�`99999.xxxx]
						///
				    	int intKetaSu = 0;
				    	if ( !checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "keta_shosu_val1").isEmpty() ) {
						    intKetaSu = Integer.parseInt(checkData.GetValueStr(strKinoNm, "tr_shisakuhin", 0, "keta_shosu_val1"));

				    	}
					    String strKetaSu = "99999";
					    if ( intKetaSu > 0 ) {

					    	strKetaSu += ".";
						    for( int j=0; j<intKetaSu; j++ ) {
						    	strKetaSu += "9";

						    }

					    }
				    	super.rangeNumCheck(Double.parseDouble(strRyo),0,Double.parseDouble(strKetaSu),"��");

						///
						/// �����_�ȉ������͂���鍀�ڂɂ��ẮA���������`�F�b�N
						///
				    	if ( intKetaSu > 0 ) {
				    		super.shousuRangeCheck(strRyo,5,intKetaSu,"��");

				    	} else if ( strRyo.indexOf(".") != -1 ) {
				    		super.shousuRangeCheck(strRyo,5,intKetaSu,"��");

				    	}

						//ADD 2012.4.30�@�yH24�N�x�Ή��z�C��4�@�����̓��͂ɑ΂��錴���̓��̓`�F�b�N���s���B
						//���Z�˗��ς݂Ŋ��L�����Z���t���O��off�̎���ɂ��āA�z���e�[�u���̌����`�F�b�N���s���B
						if( "1".equals(strFlg_shisanIrai) && !"1".equals(strFlg_cancel)){
							//�H��CD,�H��SEQ���z���e�[�u�����烌�R�[�h���擾
							intRecIndex = getTableIndex(checkData,"tr_haigo","cd_kotei",strKouteiCd,"seq_kotei",strKouteiSeq);
							//����CD�����͂���Ă��Ȃ������̓G���[
							strCd_genryo = checkData.GetValueStr(strKinoNm, "tr_haigo", intRecIndex, "cd_genryo");
							if(strCd_genryo == null || strCd_genryo.isEmpty()){
								em.ThrowException(
										ExceptionKind.���Exception,
										"E000222",
										"","","");
							}
							//�������̈ꕶ���ڂɁ�(�����}�X�^���o�^)�̏ꍇ���̓G���[
							strNm_genryo = checkData.GetValueStr(strKinoNm, "tr_haigo", intRecIndex, "nm_genryo");
							if( !"".equals(strNm_genryo)
									 &&	"��".equals(strNm_genryo.substring(0,1))
									 && !"N".equals(strCd_genryo.substring(0,1))
									 && !"n".equals(strCd_genryo.substring(0,1))

								){
								// 20160420 MOD KPX@1600766 Start
//								em.ThrowException(
//										ExceptionKind.���Exception,
//										"E000223",
//										"","","");
								// 20160915 MOD KPX@1600766 Start
								// �G���[�������̐ݒ�
//								errGenryo += System.getProperty("line.separator");
								// 20160913 ADD KPX@1600766 Start
//								errGenryo += "�T���v��No�F" + nm_sample + "�@";
								// 20160913 ADD KPX@1600766 End
//								errGenryo += strCd_genryo + "�F" + strNm_genryo;
								// 20160420 MOD KPX@1600766 End
								errGenryo.append(System.getProperty("line.separator"));
								errGenryo.append(nm_sample + "�@" + strCd_genryo + "�F" + strNm_genryo);
								// 20160915 MOD KPX@1600766 END
							}
						}

				    } catch ( ExceptionUser ex) {		//�`�F�b�N�Ɉ������������ꍇ
						//���b�Z�[�W�̐ݒ�
						String strMsg = "";
						strMsg = "����f�[�^ �z���\";
						strMsg +=  " [�����F" + getShisakuSort(strShisakuSeq, checkData);
						strMsg += ", �H���F" + getKoteiSort(strKouteiCd, strKouteiSeq, checkData).split(":::")[0];
						strMsg += "(�������ށF" + getKoteiSort(strKouteiCd, strKouteiSeq, checkData).split(":::")[1] +")";
						strMsg += "]" + ex.getUserMsg();
						ex.setUserMsg(strMsg);

						//�G�N�Z�v�V������throw����
						throw ex;

				    } finally {

				    }

			    }

			    //QP@20505_No1 2012/10/29 ADD Start
			    //�H���d��d��
			    String juryo_shiagari_seq = strShisakuSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_seq");
			    if( !(juryo_shiagari_seq.isEmpty()) ){
			    	super.rangeNumCheck(
			    			new BigDecimal(checkData.GetValueStr(strKinoNm, strTableNm, i, "juryo_shiagari_seq"))
			    			,new BigDecimal("0")
			    			,new BigDecimal("9999999999.9999")
			    			,"����f�[�^��� �H���d��d��");
			    }
				//QP@20505_No1 2012/10/29 ADD End

			}

			// 20160420 ADD KPX@1600766 Start
			try{
				// 20160915 MOD KPX@1600766 Start
				// �����G���[������ꍇ�G���[���b�Z�[�W�\��
				//if(!"".equals(errGenryo)) {
				if(!"".equals(errGenryo.toString())) {
				// 20160915 MOD KPX@1600766 End
					em.ThrowException(
						ExceptionKind.���Exception,
						"E000223",
						"","","");
				}
			} catch (ExceptionUser ex) {
				// 20160915 MOD KPX@1600766 Start
				// �G���[���b�Z�[�W+�G���[��������ݒ�
//				ex.setUserMsg(ex.getUserMsg() + errGenryo);
				ex.setUserMsg(ex.getUserMsg() + errGenryo.toString());
				// 20160915 MOD KPX@1600766 End

				throw ex;
			}
			// 20160420 ADD KPX@1600766 End

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;

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

		String strKinoNm = "SA490";
		String strTableNm = "tr_cyuui";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				///�K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����f�[�^��� ����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����f�[�^��� ����CD-�N");					//����R�[�h-�N

//2009/09/30 TT.A.ISONO DEL START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����f�[�^��� ����CD-�ǔ�");				//����R�[�h-�ǔ�

//2009/09/30 TT.A.ISONO DEL END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

			    //�����H�� ����SEQ
			    //String strShisakuSeq = checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku");
			    //super.hissuInputCheck(strShisakuSeq,"����f�[�^��� ����SEQ");
			    //�����H�� ����SEQ
			    String strChuiNo = checkData.GetValueStr(strKinoNm, strTableNm, i, "no_chui");
		    	super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_chui"),"����f�[�^��� ���ӎ���No");

			    try {
					///
					/// ���͌����`�F�b�N
					///
//mod start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.21
//				    super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "chuijiko"),"���ӎ���",400);
			    	String strSeizoKotei = checkData.GetValueStr(strKinoNm, strTableNm, i, "chuijiko");
				    super.sizeCheckLen(strSeizoKotei.replaceAll("\n", ""),"���ӎ���",1000);
				    super.sizeCheckLen(strSeizoKotei,"���ӎ���",2000);
//mod end --------------------------------------------------------------------------------------

			    } catch (ExceptionUser ex) {

			    	//����\�����̎擾
			    	//String sort_shisaku = getShisakuSort(strShisakuSeq, checkData);

					//�ԋp���b�Z�[�W�̕ҏW
					ex.setUserMsg("����f�[�^��� �z���\ [���ӎ���No�F" + strChuiNo + "]" + ex.getUserMsg());
					throw ex;

				} finally {

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			strKinoNm = null;
			strTableNm = null;

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

	//TODO
	/**
	 * ���������e�[�u�� �o�^���ڃ`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void genkaGenryoInsertValueCheck(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";
		String strTableNm = "tr_genryo";
		String strAddMsg = "";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				///
				///�K�{���ڂ̓��̓`�F�b�N
				///
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shain"),"����CD-�Ј�CD");		//����R�[�h-�Ј�CD
			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nen"),"����CD-�N");					//����R�[�h-�N

//2009/09/30 TT.A.ISONO DEL START [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

//			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "no_oi"),"����CD-�ǔ�");				//����R�[�h-�ǔ�

//2009/09/30 TT.A.ISONO DEL END   [����CD�ǔԂ͓o�^���ɍ̔Ԃ���悤�ύX����B]

			    super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seq_shisaku"),"����SEQ");			//����SEQ

			    //����e�[�u��.�T���v��NO(����)�̎擾
				String nm_sample = checkData.GetValueStr(strKinoNm, "tr_shisaku", i, "nm_sample");
				if ( nm_sample.equals("") ) {
					nm_sample = "���̖���";		//�T���v��NO����̏ꍇ�A"���̖���"�Ɗi�[����

				}
				//����e�[�u��.����\�����̎擾
				String sort_shisaku = checkData.GetValueStr(strKinoNm, "tr_shisaku", i, "sort_shisaku");

				//  [����\�@]�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "����f�[�^��� �������Z<����>�D [�����F" + sort_shisaku;
				strAddMsg += ", �T���v��NO(����)�F" + nm_sample + "]";

				///
				///���͌����`�F�b�N
				///
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyusui"),strAddMsg + "�[�U�ʐ���",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyuabura"),strAddMsg + "�[�U�ʖ���",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "gokei"),strAddMsg + "���v��",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi"),strAddMsg + "������(kg)",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi1"),strAddMsg + "������i1�{���j",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),strAddMsg + "��d",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yoryo"),strAddMsg + "�e��",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "irisu"),strAddMsg + "���萔",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yukobudomari"),strAddMsg + "�L������",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "reberu"),strAddMsg + "���x����",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hizyubudomari"),strAddMsg + "��d���Z��",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinzyu"),strAddMsg + "���Ϗ[�U��",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genryo"),strAddMsg + "������/CS",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_zairyohi"),strAddMsg + "�ޗ���",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_keihi"),strAddMsg + "�Œ��",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genka"),strAddMsg + "�����v/CS",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_genka"),strAddMsg + "�����v/��",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_baika"),strAddMsg + "����",20,10);
			    super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_riritu"),strAddMsg + "�e��",20,10);

				///
				/// �����_�ȉ������͂���鍀�ڂɂ��ẮA���������`�F�b�N
				///
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyusui").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyusui"),2,strAddMsg+"�[�U�ʐ���");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyuabura").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "zyuabura"),2,strAddMsg+"�[�U�ʖ���");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "gokei").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "gokei"),3,strAddMsg+"���v��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi"),2,strAddMsg+"������(kg)");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi1").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "genryohi1"),2,strAddMsg+"������i1�{���j");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hiju"),3,strAddMsg+"��d");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "yukobudomari").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yukobudomari"),2,strAddMsg+"�L������");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "reberu").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "reberu"),2,strAddMsg+"���x����");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "hizyubudomari").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "hizyubudomari"),2,strAddMsg+"��d���Z��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinzyu").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinzyu"),2,strAddMsg+"���Ϗ[�U��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genryo").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genryo"),2,strAddMsg+"������/CS");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_zairyohi").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_zairyohi"),2,strAddMsg+"�ޗ���");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_keihi").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_keihi"),2,strAddMsg+"�Œ��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genka").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cs_genka"),2,strAddMsg+"�����v/CS");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_genka").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_genka"),2,strAddMsg+"�����v/��");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_baika").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_baika"),2,strAddMsg+"����");
				}
				if ( !(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_riritu").isEmpty()) ) {
					super.shousuRangeCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "ko_riritu"),2,strAddMsg+"�e��");
				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {
			//�ϐ��̍폜
			strKinoNm = null;
			strTableNm = null;
			strAddMsg = null;

		}

	}

	/**
	 * ���l�`�F�b�N
	 * @param strChkParam : �`�F�b�N�p�����[�^
	 * @param strChkName : �`�F�b�N����
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void NumericCheck(String strChkParam, String strChkName)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

try {

			try {
		    	// �`�F�b�N�p�����[�^�[�̐��l�`�F�b�N���s���B
				Double.parseDouble(strChkParam);
				//Integer.parseInt(strChkParam);

			} catch (NumberFormatException ne) {
				//���l�ł͂Ȃ��ꍇ�AException��Throw����
		    	em.ThrowException(
		    			ExceptionKind.���Exception,
		    			"E000012",
		    			strChkName, "","");

			}

		} catch (Exception e) {
			em.ThrowException(e, "�C���v�b�g�`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}
	}

	//ADD 2012.4.30�@�yH24�N�x�Ή��z�C��4�@�����̓��͂ɑ΂��錴���̓��̓`�F�b�N���s���B
	/**
	 * �w��e�[�u�����w��L�[�̎w��f�[�^�ŃT�[�`���Aindex�����^�[������B
	 * @param checkData : ���N�G�X�g�f�[�^
	 * @param strTableNm_sub : �e�[�u����
	 * @param strSearchKey : �T�[�`����...
	 * @param strSearchData : �T�[�`�f�[�^(null�ȊO)...
	 * @return �e�[�u����index
	 * @throws ExceptionSystem
	 * @throws ExceptionUser	������Ȃ��ꍇ�����B
	 * @throws ExceptionWaning
	 */
	private int getTableIndex(RequestData checkData,String strTableNm_sub,String... strKeyData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "SA490";

		try {

	    	//�f�[�^�T�[�`
			int intParaCounter = 0;
			for ( int j=0; j<checkData.GetRecCnt(strKinoNm, strTableNm_sub); j++ ) {

				for ( intParaCounter=0; intParaCounter<strKeyData.length; intParaCounter=intParaCounter+2){
					if( strKeyData[intParaCounter+1] == null){
						break;
					}else if ( !strKeyData[intParaCounter+1].equals(checkData.GetValueStr(strKinoNm, strTableNm_sub, j, strKeyData[intParaCounter]))){
						break;
					}
				}
				if( intParaCounter >= strKeyData.length){
					return j;
				}

			}
			//�C���^�[�t�F�[�X�㌩����Ȃ����Ƃ͂Ȃ��͂�
			em.ThrowException(
	    			ExceptionKind.���Exception,
	    			"E000224",
	    			strTableNm_sub,StringUtils.arrayToCommaDelimitedString(strKeyData),"");

		} catch (Exception e) {
			em.ThrowException(e, "");

		}
		return 0;
	}

}
