package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * �������Z�@�X�V���߯����� :
 * 		�������Z�A�X�V���̲��߯�����
 *
 * @author Nishigawa
 * @since 2009/10/28
 */
public class RGEN0041_inputcheck extends InputCheck {

	//�yQP@00342�z
	String kenkyu = "";
	String seikan = "";
	String gentyo = "";
	String kojo = "";
	String eigyo = "";
	String setting = "";
	String ragio_kesu_kg = "";

	/**
	 * �R���X�g���N�^ : �������Z�A���ޏ��\��ͯ�ް���߯������R���X�g���N�^
	 */
	public RGEN0041_inputcheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                  execInputCheck�i�����J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : �e�f�[�^�`�F�b�N�������Ǘ�����B
	 *
	 * @param requestData
	 *            : ���N�G�X�g�f�[�^
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
			// USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);

			//�yQP@00342�z���N�G�X�g�f�[�^���畔���t���O�擾
			kenkyu = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "busho_kenkyu"));
			seikan = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "busho_seikan"));
			gentyo = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "busho_gentyo"));
			kojo = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "busho_kojo"));
			eigyo = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "busho_eigyo"));

			//�X�V���@�擾
			setting = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "setting"));

			//�v�Z���@�I��
			ragio_kesu_kg = toString(checkData.GetValueStr("FGEN0040", "kihon", 0, "ragio_kesu_kg"));

			//FGEN0040�̃C���v�b�g�`�F�b�N���s���B
			insertValueCheck(checkData);

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                           insertValueCheck�i���߯������J�n�J�n�j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �o�^���ڃ`�F�b�N
	 *  : FGEN0040�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void insertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//�e�[�u���F[kihon]�̃C���v�b�g�`�F�b�N
			this.kihonInsertValueCheck(checkData);

			//�e�[�u���F[genryo]�̃C���v�b�g�`�F�b�N
			this.genryoInsertValueCheck(checkData);

			//�e�[�u���F[keisan]�̃C���v�b�g�`�F�b�N
			this.keisanInsertValueCheck(checkData);

			//�e�[�u���F[shizai]�̃C���v�b�g�`�F�b�N
			this.shizaiInsertValueCheck(checkData);

			// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
			//�e�[�u���F[kihonsub]�̃C���v�b�g�`�F�b�N
			this.kihonsubInsertValueCheck(checkData);
			// ADD 2013/7/2 shima�yQP@30151�zNo.37 end

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}


	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                              TableValueCheck�i�eð��ٲ��߯������j
	//
	//--------------------------------------------------------------------------------------------------------------

	/**
	 * �e�[�u���F[kihon]�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kihonInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "kihon";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {



					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �S�����
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					//�K�{�`�F�b�N
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kaisya"),"�������");


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �S���H��
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					//�K�{�`�F�b�N
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_kojyo"),"�����H��");


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   ���萔
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "irisu") ).equals("") ){

						//���l�`�F�b�N
						super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "irisu"), "", ","), "���萔");

						//�����`�F�b�N�i60���ȓ��j
						super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "irisu"), "", ","),"���萔",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �׎p
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "nisugata") ).equals("") ){

						//�����`�F�b�N�i26���ȓ��j
						// �yQP@10713�z20111111 HISAHORI MOD START
						//super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nisugata"),"�׎p",26);
						super.sizeHalfFullLengthCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nisugata"),"�׎p",26,13);
						// �yQP@10713�z20111111 HISAHORI MOD END
					}

					// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
/*
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   ��]����
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka") ).equals("") ){

						super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka"), "", ","), "��]����");

						//�����`�F�b�N�i60���ȓ��j
						super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka"), "", ","),"��]����",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �����P��
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					//�K�{�`�F�b�N�i������]�����͂���Ă���ꍇ�̂݁j
					if( toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka") ).equals( "" ) ){

						//�������Ȃ�

					}
					else{

						//�K�{�`�F�b�N
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka_cd_tani"),"��]�����P��");

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   ������]
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika") ).equals("") ){

						//���l�`�F�b�N
						// �yQP@10713�z20111110 TT H.SHIMA �����ύX
						//super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","), "��]����");
						super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","), "��]����");

						//�����`�F�b�N�i60���ȓ��j
						// �yQP@10713�z20111110 TT H.SHIMA �����ύX
						//super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","),"��]����",60);
						super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","),"��]����",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �z�蕨��
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "butu_sotei") ).equals("") ){

						//�����`�F�b�N�i60���ȓ��j
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "butu_sotei"),"�z�蕨��",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �̔�����
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "ziki_hanbai") ).equals("") ){

						//�����`�F�b�N�i60���ȓ��j
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "ziki_hanbai"),"�̔�����",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �v�攄��
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_uriage") ).equals("") ){

						//�����`�F�b�N�i60���ȓ��j
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_uriage"),"�v�攄��",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �v�旘�v
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_rieki") ).equals("") ){

						//�����`�F�b�N�i60���ȓ��j
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_rieki"),"�v�旘�v",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �̔��㔄��
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_uriage") ).equals("") ){

						//�����`�F�b�N�i60���ȓ��j
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_uriage"),"�̔��㔄��",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �̔��㗘�v
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_rieki") ).equals("") ){

						//�����`�F�b�N�i60���ȓ��j
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_rieki"),"�̔��㗘�v",60);

					}


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �������b�g
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//�yQP@00342�z�������H��̏ꍇ�͐������b�g�K�{
					//�yQP@10713�z���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
					//if(kojo.equals("1") && setting.equals("2")){
					if( ( kojo.equals("1") || seikan.equals("1") ) && setting.equals("2")){
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto"),"�������b�g");
					}

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto") ).equals("") ){

						//�����`�F�b�N�i60���ȓ��j
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto"),"�������b�g",60);

					}
*/
					// DEL 2013/7/2 shima�yQP@30151�zNo.37 end


					///////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �������Z����
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan") ).equals("") ){

//MOD 2013/07/18 ogawa �yQP@30151�zNo.33 start
//�C���O�\�[�X
						//�����`�F�b�N�i500���ȓ��j
//						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan"),"�������Z����", 500);
//�C����\�[�X
						//�����`�F�b�N�i2000���ȓ��j
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan"),"�������Z����", 2000);
//MOD 2013/07/18 ogawa �yQP@30151�zNo.33 end

					}

					//�yQP@00342�z
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////
					//   �������Z�����i�c�ƘA���p�j
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////

					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan_eigyo") ).equals("") ){

//MOD 2013/07/18 ogawa �yQP@30151�zNo.33 start
//�C���O�\�[�X
						//�����`�F�b�N�i500���ȓ��j
//						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan_eigyo"),"�������Z�����i�c�ƘA���p�j", 500);
//�C����\�[�X
						//�����`�F�b�N�i2000���ȓ��j
						super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "memo_genkashisan_eigyo"),"�������Z�����i�c�ƘA���p�j", 2000);
//MOD 2013/07/18 ogawa �yQP@30151�zNo.33 end

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
	 * �e�[�u���F[genryo]�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void genryoInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "genryo";
		String strAddMsg = "";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

			    //�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "�y�������z [ �s���F" + checkData.GetValueStr(strKinoNm, strTableNm, i, "no_gyo") + "]";

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �P��
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//�yQP@00342�z�������H��A�����ޒ��B���̏ꍇ
				//�yQP@10713�z���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
				//if(kojo.equals("1") || gentyo.equals("1")){
				if(kojo.equals("1") || gentyo.equals("1") || seikan.equals("1")){
					if(setting.equals("2")){
						//�K�{�`�F�b�N
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), strAddMsg + "�P��");
					}
					else{

					}
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka") ).equals("") ){
					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ","), strAddMsg + "�P��");

					//***** MOD�yH24�N�x�Ή��z20120424 hagiwara S **********
					//�͈̓`�F�b�N�i0.0 �`999999.99�j
					//super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ",") ), 0, 999999.99, strAddMsg + "�P��");

					//�͈̓`�F�b�N�i0.01 �`999999.99�j
					if(Double.parseDouble(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ",")) != 0){
						super.rangeNumCheck(Double.parseDouble(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ",") ), 0.01, 999999.99, strAddMsg + "�P��");
					}
					//***** MOD�yH24�N�x�Ή��z20120424 hagiwara E **********
				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ����
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//�yQP@00342�z�������H��A�����ޒ��B���̏ꍇ
				//�yQP@10713�z���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
				//if(kojo.equals("1") || gentyo.equals("1")){
				if(kojo.equals("1") || gentyo.equals("1") || seikan.equals("1")){
					if(setting.equals("2")){
						//�K�{�`�F�b�N
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), strAddMsg + "����");
					}
					else{

					}

				}
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari") ).equals("") ){
					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ","), strAddMsg + "����");

					//***** MOD�yH24�N�x�Ή��z20120424 hagiwara S **********
					//�͈̓`�F�b�N�i0.0 �`999.99�j
					//super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ",") ), 0, 999.99, strAddMsg + "����");

					//�͈̓`�F�b�N�i0.01 �`999.99�j
					if(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ",") ) != 0){
						super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ",") ), 0.01, 999.99, strAddMsg + "����");
					}
					//***** MOD�yH24�N�x�Ή��z20120424 hagiwara E **********
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
	 * �e�[�u���F[keisan]�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void keisanInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "keisan";
		String strAddMsg = "";

		try {

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				//�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "�y���Z���ځz [ �񐔁F" + ( i + 1 ) + "]";

				//�yQP@00342�z���Z���~
				String fg_chusi = toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "fg_chusi"));

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ���Z���t
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//�yH24�N�x�Ή��zNo.11 Start

				//�yQP@00342�z���Z���~
//				if(fg_chusi.equals("1")){
//
//				}
//				else{
					//�������H��̏ꍇ
					//�yQP@10713�z2011/11/02 TT H.SHIMA -MOD	���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
					//if(kojo.equals("1") && setting.equals("2")){
//					if((kojo.equals("1") || seikan.equals("1")) && setting.equals("2")){
//						//�K�{�`�F�b�N
//						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "shisan_date"), strAddMsg + "���Z��");
//					}
//
//					if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "shisan_date") ).equals("") ){
//
//						//���t�`�F�b�N
//						super.dateCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "shisan_date"), strAddMsg + "���Z��");
//
//					}
//				}

				//�yH24�N�x�Ή��zNo.11 End

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �L������
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//�yQP@00342�z�������H��̏ꍇ
				//�yQP@10713�z2011/11/02 TT H.SHIMA -MOD	���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
				//if(kojo.equals("1")){
				if(kojo.equals("1") || seikan.equals("1")){
					//�yQP@00342�z���Z���~
					if(fg_chusi.equals("1")){

					}
					else{
						if(setting.equals("2")){
							//�K�{�`�F�b�N
							super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "yuuko_budomari"), strAddMsg + "�L������");
						}
						else{

						}
					}
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "yuuko_budomari") ).equals("") ){

					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "yuuko_budomari"), "", ","), strAddMsg + "�L������");

					//�͈̓`�F�b�N�i0.0 �`999.99�j
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "yuuko_budomari"), "", ",") ), 0, 999.99, strAddMsg + "�L������");

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ���Ϗ[�U��
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//�yQP@00342�z�������H��̏ꍇ
				//�yQP@10713�z2011/11/02 TT H.SHIMA -MOD	���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
				//if(kojo.equals("1")){
				if(kojo.equals("1") || seikan.equals("1")){
					//�yQP@00342�z���Z���~
					if(fg_chusi.equals("1")){

					}
					else{
						if(setting.equals("2")){
							//�K�{�`�F�b�N
							super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinjyutenryo"), strAddMsg + "���Ϗ[�U��");
						}
						else{

						}
					}
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinjyutenryo") ).equals("") ){

					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinjyutenryo"), "", ","), strAddMsg + "���Ϗ[�U��");

					//�͈̓`�F�b�N�i0.0�`999999.999999�j
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "heikinjyutenryo"), "", ",") ), 0, 999999.999999, strAddMsg + "���Ϗ[�U��");

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �Œ��/�P�[�X
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//�yQP@00342�z�������H��̏ꍇ
				//�yQP@10713�z2011/11/02 TT H.SHIMA -MOD	���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
				//if(kojo.equals("1")){
				if(kojo.equals("1") || seikan.equals("1")){
					//�yQP@00342�z���Z���~
					if(fg_chusi.equals("1")){

					}
					else{
						if(setting.equals("2") && ragio_kesu_kg.equals("1")){
							//�K�{�`�F�b�N
							super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_kotehi"), strAddMsg + "�Œ��/�P�[�X");
						}
						else{

						}
					}
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_kotehi") ).equals("") ){

					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_kotehi"), "", ","), strAddMsg + "�Œ��/�P�[�X");

					//�͈̓`�F�b�N�i0.0 �`999999.99�j
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_kotehi"), "", ",") ), 0, 999999.99, strAddMsg + "�Œ��/�P�[�X");

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �Œ��/kg
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//�yQP@00342�z�������H��̏ꍇ
				//�yQP@10713�z2011/11/02 TT H.SHIMA -MOD	���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
				//if(kojo.equals("1")){
				if(kojo.equals("1") || seikan.equals("1")){
					//�yQP@00342�z���Z���~
					if(fg_chusi.equals("1")){

					}
					else{
						if(setting.equals("2") && ragio_kesu_kg.equals("2")){
							//�K�{�`�F�b�N
							super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_kotehi"), strAddMsg + "�Œ��/kg");
						}
						else{

						}
					}
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_kotehi") ).equals("") ){

					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_kotehi"), "", ","), strAddMsg + "�Œ��/kg");

					//�͈̓`�F�b�N�i0.0 �`999999.99�j
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_kotehi"), "", ",") ), 0, 999999.99, strAddMsg + "�Œ��/kg");

				}
                // ADD 2013/11/1 QP@30154 okano start
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ���v/�P�[�X
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				// DEL 2013/12/6 QP@30154 okano start
//					if(kojo.equals("1") || seikan.equals("1")){
//						//���Z���~
//						if(fg_chusi.equals("1")){
//	
//						}
//						else{
//							if(setting.equals("2") && ragio_kesu_kg.equals("1")){
//								//�K�{�`�F�b�N
//								super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_rieki"), strAddMsg + "���v/�P�[�X");
//							}
//							else{
//	
//							}
//						}
//					}
				// DEL 2013/12/6 QP@30154 okano end

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_rieki") ).equals("") ){

					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_rieki"), "", ","), strAddMsg + "���v/�P�[�X");

					//�͈̓`�F�b�N�i0.0 �`999999.99�j
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kesu_rieki"), "", ",") ), 0, 999999.99, strAddMsg + "���v/�P�[�X");

				}

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ���v/kg
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				// DEL 2013/12/6 QP@30154 okano start
//					if(kojo.equals("1") || seikan.equals("1")){
//						//���Z���~
//						if(fg_chusi.equals("1")){
//	
//						}
//						else{
//							if(setting.equals("2") && ragio_kesu_kg.equals("2")){
//								//�K�{�`�F�b�N
//								super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_rieki"), strAddMsg + "���v/kg");
//							}
//							else{
//	
//							}
//						}
//					}
				// DEL 2013/12/6 QP@30154 okano end

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_rieki") ).equals("") ){

					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_rieki"), "", ","), strAddMsg + "���v/kg");

					//�͈̓`�F�b�N�i0.0 �`999999.99�j
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kg_rieki"), "", ",") ), 0, 999999.99, strAddMsg + "���v/kg");

				}
                // ADD 2013/11/1 QP@30154 okano end

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
	 * �e�[�u���F[shizai]�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void shizaiInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		String strKinoNm = "FGEN0040";
		String strTableNm = "shizai";
		String strAddMsg = "";

		try {


			//���ސ��̃`�F�b�N
			if( checkData.GetRecCnt(strKinoNm, strTableNm) > 15 ){

				//�s�����X���[����B
		    	em.ThrowException(
		    			ExceptionKind.���Exception,
		    			"E000403",
		    			"",
		    			"",
		    			"");
			}

			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {

				//�ǉ����b�Z�[�W�̕ҏW
				strAddMsg = "�y���ޏ��z [ �s���F" + ( i + 1 ) + "]";

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ���ރR�[�h
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shizai") ).equals("") ){

					//�����`�F�b�N�i13���ȓ��j
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "cd_shizai"), strAddMsg + "���ރR�[�h", 13);

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ���ޖ�
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//�yQP@00342�z�������H��̏ꍇ
				//�yQP@10713�z���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
				//if(kojo.equals("1") && setting.equals("2")){
				if((kojo.equals("1") || seikan.equals("1")) && setting.equals("2")){
					//�K�{�`�F�b�N
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_shizai"), strAddMsg + "���ޖ�");
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_shizai") ).equals("") ){

//2010/02/15�@T.T.Isono�@UPDATE�@START�@���ޖ���36���ȓ��ɕύX
					//�����`�F�b�N�i60���ȓ��j
//					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_shizai"), strAddMsg + "���ޖ�", 60);
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "nm_shizai"), strAddMsg + "���ޖ�", 36);
//2010/02/15�@T.T.Isono�@UPDATE�@END�@�@���ޖ���36���ȓ��ɕύX

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ���ޒP��
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//�yQP@00342�z�������H��̏ꍇ
				//�yQP@10713�z���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
				//if(kojo.equals("1") && setting.equals("2")){
				if((kojo.equals("1") || seikan.equals("1")) && setting.equals("2")){
					//�K�{�`�F�b�N
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), strAddMsg + "���ޒP��");
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka") ).equals("") ){

					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ","), strAddMsg + "���ޒP��");

					//�͈̓`�F�b�N�i0.0 �`999999.99�j
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "tanka"), "", ",") ), 0, 999999.99, strAddMsg + "���ޒP��");

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ���ޕ���
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//�yQP@00342�z�������H��̏ꍇ
				//�yQP@10713�z���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
				//if(kojo.equals("1") && setting.equals("2")){
				if((kojo.equals("1") || seikan.equals("1")) && setting.equals("2")){
					//�K�{�`�F�b�N
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), strAddMsg + "���ޕ���");
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari") ).equals("") ){

					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ","), strAddMsg + "���ޕ���");

					//�͈̓`�F�b�N�i0.0 �`999.99�j
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "budomari"), "", ",") ), 0, 999.99, strAddMsg + "���ޕ���");

				}


				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �g�p��/�P�[�X
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////

				//�yQP@00342�z�������H��̏ꍇ
				//�yQP@10713�z���Y�����ŃX�e�[�^�X���u�m�F�����v���ɂ͍H�ꌠ�����͕K�{���ڂ̏������s��
				//if(kojo.equals("1") && setting.equals("2")){
				if((kojo.equals("1") || seikan.equals("1")) && setting.equals("2")){
					//�K�{�`�F�b�N
					super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "shiyouryo"), strAddMsg + "�g�p��/�P�[�X");
				}

				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "shiyouryo") ).equals("") ){

					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "shiyouryo"), "", ","), strAddMsg + "�g�p��/�P�[�X");

					//�͈̓`�F�b�N�i0.0�`9999.999999�j
					super.rangeNumCheck(Double.parseDouble( toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "shiyouryo"), "", ",") ), 0, 9999.999999, strAddMsg + "�g�p��/�P�[�X");

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

	// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
	/**
	 * �e�[�u���F[kihonsub]�̃C���v�b�g�`�F�b�N
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void kihonsubInsertValueCheck(RequestData checkData)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		String strKinoNm = "FGEN0040";
		String strTableNm = "kihonsub";
		
		try {
			
			for ( int i=0; i<checkData.GetRecCnt(strKinoNm, strTableNm); i++ ) {
				
				//���Z���~
				String fg_chusi = toString(checkData.GetValueStr(strKinoNm, "keisan", i, "fg_chusi"));
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ��]����
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka") ).equals("") ){
				
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka"), "", ","), "��]����");
				
					//�����`�F�b�N�i60���ȓ��j
					super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka"), "", ","),"��]����",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �����P��
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				//���Z���~
				if(fg_chusi.equals("1")){

				}
				else{
					//�K�{�`�F�b�N�i������]�����͂���Ă���ꍇ�̂݁j
					if( toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka") ).equals( "" ) ){
					
						//�������Ȃ�
					
					}
					else{
					
						//�K�{�`�F�b�N
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_genka_cd_tani"),"��]�����P��");
					
					}
				}
					
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   ������]
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika") ).equals("") ){
				
					//���l�`�F�b�N
					super.numberCheck(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","), "��]����");
				
					//�����`�F�b�N�i60���ȓ��j
					super.sizeCheckLen(toString(checkData.GetValueStr(strKinoNm, strTableNm, i, "kibo_baika"), "", ","),"��]����",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �z�蕨��
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "butu_sotei") ).equals("") ){
				
					//�����`�F�b�N�i60���ȓ��j
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "butu_sotei"),"�z�蕨��",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �̔�����
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "ziki_hanbai") ).equals("") ){
				
					//�����`�F�b�N�i60���ȓ��j
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "ziki_hanbai"),"�̔�����",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �v�攄��
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_uriage") ).equals("") ){
				
					//�����`�F�b�N�i60���ȓ��j
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_uriage"),"�v�攄��",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �v�旘�v
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_rieki") ).equals("") ){
				
					//�����`�F�b�N�i60���ȓ��j
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "keikaku_rieki"),"�v�旘�v",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �̔��㔄��
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_uriage") ).equals("") ){
				
					//�����`�F�b�N�i60���ȓ��j
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_uriage"),"�̔��㔄��",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �̔��㗘�v
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_rieki") ).equals("") ){
				
					//�����`�F�b�N�i60���ȓ��j
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "hanbaigo_rieki"),"�̔��㗘�v",60);
				
				}
				
				
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//   �������b�g
				///////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if( ( kojo.equals("1") || seikan.equals("1") ) && setting.equals("2")){
					//���Z���~
					if(fg_chusi.equals("1")){

					}
					else{
						super.hissuInputCheck(checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto"),"�������b�g");
					}
				}
				
				if( !toString( checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto") ).equals("") ){
				
					//�����`�F�b�N�i60���ȓ��j
					super.sizeCheckLen(checkData.GetValueStr(strKinoNm, strTableNm, i, "seizo_roto"),"�������b�g",60);
				
				}
					
			}
		} catch (Exception e) {
			this.em.ThrowException(e, "");
			
		} finally {
			strKinoNm = null;
			strTableNm = null;
		
		}
	}
	// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
}
