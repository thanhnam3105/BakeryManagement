package jp.co.blueflag.shisaquick.srv.inputcheck_genka;

import jp.co.blueflag.shisaquick.srv.base.InputCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestData;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

/**
 * �������Z�i�c�Ɓj�f�[�^�����C���v�b�g�`�F�b�N 
 * 
 * @author Y.Nishigawa
 * @since 2011/01/28
 */
public class RGEN2180_inputcheck extends InputCheck {

	/**
	 * �R���X�g���N�^ : �������Z�i�c�Ɓj�f�[�^�C���v�b�g�`�F�b�N�R���X�g���N�^
	 */
	public RGEN2180_inputcheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �C���v�b�g�`�F�b�N�Ǘ� : ���̓`�F�b�N�̊Ǘ����s���B
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
			//�@USERINFO�̃C���v�b�g�`�F�b�N���s���B
			super.userInfoCheck(checkData);
			
			//�@�ݒ�X�e�[�^�X�擾
			String st_setting = toString(checkData.GetValueStr("FGEN2160", 0, 0, "st_setting"));

			//�@FGEN2160�̃C���v�b�g�`�F�b�N�i���ʁj���s���B
			FGEN2160Check_kyotu(checkData);
			
			// ���Z�˗��A�m�F�����A�̗p�L���m��i�̗p�L��j�̏ꍇ
			if( st_setting.equals("1") || st_setting.equals("2") || st_setting.equals("3") ){
				
				//�X�e�[�^�X�ύX���̃C���v�b�g�`�F�b�N���s��
				FGEN2160Check_StatusHenkou(checkData);
				
			}
			
			// �̗p�L���m��i�̗p�L��j�̏ꍇ
			if( st_setting.equals("3") ){
				
				//�̗p�����No�擾
				String no_saiyou = toString(checkData.GetValueStr("FGEN2160", 0, 0, "no_saiyou"));
				super.hissuInputCheck(no_saiyou,"�̗p�����No");
				
			}
			
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {

		}
	}

	/**
	 * �������Z�i�c�Ɓj�f�[�^�@���ʃC���v�b�g�`�F�b�N
	 *  : FGEN2160�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void FGEN2160Check_kyotu(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//���N�G�X�g����}�Ԏ擾
			String eda = toString(checkData.GetValueStr("FGEN2160", 0, 0, "no_eda"));
			
			//���Z����
			String dt_kizitu = toString(checkData.GetValueStr("FGEN2160", 0, 0, "dt_kizitu"));
			if(dt_kizitu.equals("")){
				
			}
			else{
				//���Z���������t�œ��͂���Ă��邩
				super.dateCheck(dt_kizitu, "���Z����");
			}
			
			
			//�e��
			String yoryo = toString(checkData.GetValueStr("FGEN2160", 0, 0, "yoryo"), "", ",");
			//���ł̏ꍇ
			if(eda.equals("000")){
				
			}
			//�}�ł̏ꍇ
			else{
				if(yoryo.equals("")){
					
				}
				else{
					//�e�ʂ����l�œ��͂���Ă��邩�i�}�ł̏ꍇ�̂݁j
					super.numberCheck(yoryo, "�e�ʁi���l���́j");
					
					//�e�ʂ�60���ȓ��œ��͂���Ă��邩�i�}�ł̏ꍇ�̂݁j
					super.sizeCheckLen(yoryo,"�e�ʁi���l���́j",60);
				}
			}
			
			//���萔
			String su_iri = toString(checkData.GetValueStr("FGEN2160", 0, 0, "su_iri"), "", ",");
			if(su_iri.equals("")){
				
			}
			else{
				//���萔�����l�œ��͂���Ă��邩
				super.numberCheck(su_iri, "���萔�i���l���́j");
				
				//���萔��60���ȓ��œ��͂���Ă��邩
				super.sizeCheckLen(su_iri,"���萔�i���l���́j",60);
			}
			
			//�׎p
			String nisugata = toString(checkData.GetValueStr("FGEN2160", 0, 0, "nisugata"));
			if(nisugata.equals("")){
				
			}
			else{
				//�׎p��26���ȓ��œ��͂���Ă��邩
				// �yQP@10713�z201111114 HISAHORI MOD START 
				//super.sizeCheckLen(nisugata,"�׎p",26);
				super.sizeHalfFullLengthCheck(nisugata,"�׎p",26,13);
			}
			
			// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
			//�T���v�����̊�{���
			for ( int i=0; i<checkData.GetRecCnt("FGEN2160", "kihonsub"); i++ ) {
			
				//��]����
//				String genka = toString(checkData.GetValueStr("FGEN2160", 0, 0, "genka"), "", ",");
				String genka = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "genka"), "", ",");
				if(genka.equals("")){
					
				}
				else{
					//��]���������l�œ��͂���Ă��邩
					super.numberCheck(genka, "��]�����i���l���́j");	
					
					//��]������60���ȓ��œ��͂���Ă��邩
					super.sizeCheckLen(genka,"��]�����i���l���́j",60);
				}
				
				//��]����
//				String baika = toString(checkData.GetValueStr("FGEN2160", 0, 0, "baika"), "", ",");
				String baika = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "baika"), "", ",");
				if(baika.equals("")){
					
				}
				else{
					//��]���������l�œ��͂���Ă��邩
					//�yQP@10713�z20111110 TT H.SHIMA �����ύX
					//super.numberCheck(baika, "��]�����i���l���́j");	
					super.numberCheck(baika, "��]����i���l���́j");
					
					//��]������60���ȓ��œ��͂���Ă��邩
					//�yQP@10713�z20111110 TT H.SHIMA �����ύX
					//super.sizeCheckLen(baika,"��]�����i���l���́j",60);	
					super.sizeCheckLen(baika,"��]����i���l���́j",60);
				}
				
				//�z�蕨��
//				String sotei_buturyo = toString(checkData.GetValueStr("FGEN2160", 0, 0, "sotei_buturyo"));
				// MOD 2013/12/4 okano�yQP@30154�zstart
//					String sotei_buturyo = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo"), "", ",");
				String sotei_buturyo = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo"));
				// MOD 2013/12/4 okano�yQP@30154�zend
				if(sotei_buturyo.equals("")){
					
				}
				else{
					//�z�蕨�ʂ�60���ȓ��œ��͂���Ă��邩
					// MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//						super.sizeCheckLen(sotei_buturyo,"�z�蕨��",60);
					super.sizeCheckLen(sotei_buturyo,"�z�蕨�ʁi���l�j",60);
					// MOD 2013/9/6 okano�yQP@30151�zNo.30 end
				}
				
				// ADD 2013/9/6 okano�yQP@30151�zNo.30 start
				String sotei_buturyo_s = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo_s"), "", ",");
				if(sotei_buturyo_s.equals("")){
					
				}
				else{
					//�z�蕨�ʂ����l�œ��͂���Ă��邩
					super.numberCheck(sotei_buturyo_s, "�z�蕨�ʁi���l�j");
					//�z�蕨�ʂ�9���ȓ��œ��͂���Ă��邩
					super.sizeCheckLen(sotei_buturyo_s,"�z�蕨�ʁi���l�j",9);
				}
				// ADD 2013/9/6 okano�yQP@30151�zNo.30 end
				
				// ADD 2013/7/19 shima�yQP@30151�zNo.37 start
				String dt_hatubai = toString(checkData.GetValueStr("FGEN2160", "kihonsub", 0, "dt_hatubai"));
				if(dt_hatubai.equals("")){
					
				}else{
					//����[�i������60���ȓ��œ��͂���Ă��邩
					super.sizeCheckLen(dt_hatubai,"����[�i����",60);
				// ADD 2013/7/19 shima�yQP@30151�zNo.37 end
				//�����������uYYYY�NMM���v�A�uYYYY�NM���v�`���œ��͂���Ă��邩
	//			String dt_hatubai = toString(checkData.GetValueStr("FGEN2160", 0, 0, "dt_hatubai"));
	//			if(dt_hatubai.equals("")){
	//				
	//			}else{
	//				try{
	//					//�N���擾
	//					String nen = dt_hatubai.split("�N")[0].toString();
	//					String tuki = dt_hatubai.split("�N")[1].toString();
	//					int chk_nen = Integer.parseInt(nen);
	//					int chk_tuki = Integer.parseInt(tuki.split("��")[0].toString());
	//					
	//					//�N�`�F�b�N
	//					if(chk_nen >= 2000 && chk_nen <= 9999){
	//						
	//					}
	//					else{
	//						// �K�{���͕s�����X���[����B
	//						em.ThrowException(ExceptionKind.���Exception, "E000315", "3", "", "");
	//					}
	//					//���`�F�b�N
	//					if(chk_tuki >= 1 && chk_tuki <=12){
	//						
	//					}
	//					else{
	//						// �K�{���͕s�����X���[����B
	//						em.ThrowException(ExceptionKind.���Exception, "E000315", "3", "", "");
	//					}
	//					
	//				}
	//				catch(Exception e){
	//					// �K�{���͕s�����X���[����B
	//					em.ThrowException(ExceptionKind.���Exception, "E000315", "3", "", "");
	//				}
				}
				
				
				
				//�v�攄��
//				String keikakuuriage = toString(checkData.GetValueStr("FGEN2160", 0, 0, "keikakuuriage"));
				String keikakuuriage = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "keikakuuriage"), "", ",");
				if(keikakuuriage.equals("")){
					
				}
				else{
					//�v�攄�オ60���ȓ��œ��͂���Ă��邩
					super.sizeCheckLen(keikakuuriage,"�v�攄��",60);
				}
				
				//�v�旘�v
//				String keikakurieki = toString(checkData.GetValueStr("FGEN2160", 0, 0, "keikakurieki"));
				String keikakurieki = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "keikakurieki"), "", ",");
				if(keikakurieki.equals("")){
					
				}
				else{
					//�v�旘�v��60���ȓ��œ��͂���Ă��邩
					super.sizeCheckLen(keikakurieki,"�v�旘�v",60);
				}
				
				//�̔��㔄�オ60���ȓ��œ��͂���Ă��邩
//				String kuhaku_1 = toString(checkData.GetValueStr("FGEN2160", 0, 0, "kuhaku_1"));
				String kuhaku_1 = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "kuhaku_1"), "", ",");
				if(kuhaku_1.equals("")){
					
				}
				else{
					super.sizeCheckLen(kuhaku_1,"�󔒗�1",60);
				}
				
				//�̔��㗘�v��60���ȓ��œ��͂���Ă��邩
//				String kuhaku_2 = toString(checkData.GetValueStr("FGEN2160", 0, 0, "kuhaku_2"));
				String kuhaku_2 = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "kuhaku_2"), "", ",");
				if(kuhaku_2.equals("")){
					
				}
				else{
					super.sizeCheckLen(kuhaku_2,"�󔒗�2",60);
				}
				
				//�̔����ԁi���l�j
//				String hanabai_s = toString(checkData.GetValueStr("FGEN2160", 0, 0, "hanabai_s"), "", ",");
				String hanabai_s = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "hanabai_s"), "", ",");
				if(hanabai_s.equals("")){
					
				}
				else{
					//��]���������l�œ��͂���Ă��邩
					super.numberCheck(hanabai_s, "�̔����ԁi���l�j");
					
					//��]������9���ȓ��œ��͂���Ă��邩
					super.sizeCheckLen(hanabai_s,"�̔����ԁi���l�j",9);
				}
			}
			// MOD 2013/7/2 shima�yQP@30151�zNo.37 end
			
			//�������Z�����i�c�ƘA���p�j��500���ȓ��œ��͂���Ă��邩
			String memo_eigyo = toString(checkData.GetValueStr("FGEN2160", 0, 0, "memo_eigyo"));
			if(memo_eigyo.equals("")){
				
			}
			else{
//MOD 2013/07/18 ogawa �yQP@30151�zNo.33 start
//�C���O�\�[�X
//				super.sizeCheckLen(memo_eigyo,"�������Z�����i�c�ƘA���p�j",500);
//�C����\�[�X
				super.sizeCheckLen(memo_eigyo,"�������Z�����i�c�ƘA���p�j",2000);
//MOD 2013/07/18 ogawa �yQP@30151�zNo.33 end
			}
			

		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
	
	/**
	 * �������Z�i�c�Ɓj�f�[�^�@�X�e�[�^�X�ύX���C���v�b�g�`�F�b�N
	 *  : FGEN2160�̃C���v�b�g�`�F�b�N���s���B
	 * @param requestData : ���N�G�X�g�f�[�^
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void FGEN2160Check_StatusHenkou(RequestData checkData) throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try {
			//���Z����
			String dt_kizitu = toString(checkData.GetValueStr("FGEN2160", 0, 0, "dt_kizitu"));
			super.hissuInputCheck(dt_kizitu,"���Z����");
			
			//�e��
			String yoryo = toString(checkData.GetValueStr("FGEN2160", 0, 0, "yoryo"), "", ",");
			super.hissuInputCheck(yoryo,"�e��");
			
			//���萔
			String su_iri = toString(checkData.GetValueStr("FGEN2160", 0, 0, "su_iri"), "", ",");
			super.hissuInputCheck(su_iri,"���萔");
			
			//�׎p
			String nisugata = toString(checkData.GetValueStr("FGEN2160", 0, 0, "nisugata"));
			super.hissuInputCheck(nisugata,"�׎p");
			
			// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
			for ( int i=0; i<checkData.GetRecCnt("FGEN2160", "kihonsub"); i++ ) {
				
				//���Z���~
				String fg_chusi = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "chusi"), "", ",");
				
				//���Z���~
				if( fg_chusi.equals("1")){
					//�K�{�`�F�b�N���s��Ȃ�
					
				}else{
					//��]����
					String genka = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "genka"), "", ",");
					super.hissuInputCheck(genka,"��]����");
					
					//��]�����i�P�ʁj
					String genka_tani = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "genka_tani"), "", ",");
					super.hissuInputCheck(genka_tani,"��]�����i�P�ʁj");
					
					//��]����
					String baika = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "baika"), "", ",");
					//super.hissuInputCheck(baika,"��]����");	
					//�yQP@10713�z20111110 TT H.SHIMA �����ύX
					super.hissuInputCheck(baika,"��]����");
					
					//�z�蕨��
					// MOD 2013/9/6 okano�yQP@30151�zNo.30 start
//						String sotei_buturyo = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo"));
//						super.hissuInputCheck(sotei_buturyo,"�z�蕨��");
					String sotei_buturyo_s = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo_s"));
					super.hissuInputCheck(sotei_buturyo_s,"�z�蕨�ʁi���l�j");
					
					String sotei_buturyo_u = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo_u"));
					super.hissuInputCheck(sotei_buturyo_u,"�z�蕨�ʁi�P�ʁj");
					
					String sotei_buturyo_k = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "sotei_buturyo_k"));
					super.hissuInputCheck(sotei_buturyo_k,"�z�蕨�ʁi���ԁj");
					// MOD 2013/9/6 okano�yQP@30151�zNo.30 end
					
					//��������
					String dt_hatubai = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "dt_hatubai"));
					super.hissuInputCheck(dt_hatubai,"��������");
					
					//�̔����ԁi�ʔNor�X�|�b�g�j
					String hanbai_t = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "hanbai_t"));
					super.hissuInputCheck(hanbai_t,"�̔����ԁi�ʔN or �X�|�b�g�j");
					
					//�̔����ԁi�����j
					if( hanbai_t.equals("2") ){
						String hanabai_s = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "hanabai_s"));
						super.hissuInputCheck(hanabai_s,"�̔����ԁi���l�j");
						
						String hanabai_k = toString(checkData.GetValueStr("FGEN2160", "kihonsub", i, "hanabai_k"));
						super.hissuInputCheck(hanabai_k,"�̔����ԁi���� or ���ԁj");
					}
					
				}
			
			}
			// MOD 2013/7/2 shima�yQP@30151�zNo.37 end
			
		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			
		}
	}
}
