package jp.co.blueflag.shisaquick.srv.commonlogic_genka;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * �v�Z���ڎZ�o
 * @author isono
 * @create 2009/10/26
 */
public class CGEN0020_Logic extends LogicBase {

	//�Ώۃf�[�^������
	//����No-�Ј�ID
	BigDecimal cd_shisaku_syainID = new BigDecimal(-1);
	//����No-�N
	int cd_shisaku_nen = -1;
	//����No-�ǔ�
	int cd_shisaku_oi = -1;

	//�yQP@00342�z
	//����No-�}��
	int cd_shisaku_eda = -1;

	//�H���O���[�v�i1�F�����t�p�^�[���@2�F���̑������t�ȊO�o�^�[���@3�F�v�Z�s�\�j
	int flgKoteGroup = -1;
	//��]�����P��
	String strNm_tani_kibogenka = "";

	// DB���擾���鍀��
	//�y�v�Z�����@DB�擾���ځz�e��
	double douYouryo = -1;
	//�y�v�Z�����@DB�擾���ځz�z��(�s)
	double douHaigo = -1;
	//�y�v�Z�����@DB�擾���ځz���v�d��d��(�s)
	double douGokeShiagari = -1;
	//�y�v�Z�����@DB�擾���ځz�[�U�ʐ���(��)
	double douZyutenSui = -1;
	//�y�v�Z�����@DB�擾���ځz�[�U�ʖ���(��)
	double douZyutenYu = -1;
	//�y�v�Z�����@DB�擾���ځz��d
	double douHizyu = -1;

	// �������擾���鍀��
	//�y�v�Z�����@�������ځ@�z�v�Z���ځi�Œ��/�P�[�Xor�Œ��/kg�j
	int intRagio_kesu_kg = -1;
	//�y�v�Z�����@�������ځ@�z���萔
	double douIrisu = -1;
	//�y�v�Z�����@�������ځ@�z��]����
	double douKiboGenka = -1;
	//�y�v�Z�����@�������ځ@�z��]�����P��CD
	int intKiboGenkaTani = -1;
	//�y�v�Z�����@�������ځ@�z��]����
	double douKiboBaika = -1;
	//�y�v�Z�����@�������ځ@�z�P��(�~/�s)
	double douTanka_gen = -1;
	//�y�v�Z�����@�������ځ@�z����(��)
	double douBudomari_Gen = -1;
	//�y�v�Z�����@�������ځ@�z�L������(��)
	double douYukoBudomari = -1;
	//�y�v�Z�����@�������ځ@�z���Ϗ[�U��(��)
	double douHeikinZyuten = -1;
	//�y�v�Z�����@�������ځ@�z�P��(�~)  ����
	double douTanka_Shizai = -1;
	//�y�v�Z�����@�������ځ@�z����(��)  ����
	double douBudomari_Shizai = -1;
	//�y�v�Z�����@�������ځ@�z�g�p��/���  ����
	double douShiyouryo = -1;

	// �ȉ��̍��ڂ́A���\�b�h�ɂĎ擾
	//�y�v�Z�����@�v�Z���ځ@�z���x����(��)
	double douLevelRyo = -1;
	//�y�v�Z�����@�v�Z���ځ@�z�ޗ���(�~)/�P�[�X
	//�y�v�Z�����@�v�Z���ځ@�z������(�~)/�P�[�X
	double douGenryohiKesu = -1;
	//�y�v�Z�����@�v�Z���ځ@�z�����v(�~)/�P�[�X
	double douGenkaKei_Kesu = -1;
	//�y�v�Z�����@�������ځ@�z�Œ��(�~)/�P�[�X
	double douKotehi_Kesu = -1;
	//�y�v�Z�����@�v�Z���ځ@�z�����v(�~)/��
	double douGenkaKei_Ko = -1;
	//�y�v�Z�����@�v�Z���ځ@�z�����v(�~)/kg
	double douGenkaKei_Kg = -1;
	//�y�v�Z�����@�������ځ@�z�Œ��(�~)/kg
	double douKotehi_Kg = -1;
	// ADD 2013/11/1 QP@30154 okano start
	//�y�v�Z�����@�������ځ@�z���v(�~)/�P�[�X
	double douRieki_Kesu = -1;
	//�y�v�Z�����@�������ځ@�z���v(�~)/kg
	double douRieki_Kg = -1;
	// ADD 2013/11/1 QP@30154 okano end
	//�y�v�Z�����@�v�Z���ځ@�z���z�v  ����
	double douKingakuKei_Shizai = -1;
	//�y�v�Z�����@�v�Z���ځ@�z���z    ����
	//�y�@�@�@�@�@�v�Z���ځ@�z���z(�~)
	//�y�@�@�@�@�@�v�Z���ځ@�z��d���Z��(��)
	double douHizykasanRyo = -1;
	//�y�@�@�@�@�@�v�Z���ځ@�z������(�~)/kg
	double douGenryohiKg = -1;
	//�y�@�@�@�@�@�v�Z���ځ@�z�ޗ���(�~)/kg
	double douZairyohi_Kg = -1;
	//�y�@�@�@�@�@�v�Z���ځ@�z����
	//�y�@�@�@�@�@�v�Z���ځ@�z�e��(��)
	double douArari = -1;

	//������
	List<?> listShisaku = null;
	//����ُ��
	List<?> listSanpuru = null;
	//�������
	List<?> listGenryo = null;

	//�������v�P��
	double gokeTanka_sui = -1;
	//�������v�d��
	double gokeJyuryo_sui = -1;
	//�������v�P��
	double gokeTanka_yu = -1;
	//�������v�d��
	double gokeJyuryo_yu = -1;

	//�yQP@00342�z
	//�e�ʁi���Łj
	double yoryo_moto = -1;

	/**
	 * �R���X�g���N�^
	 */
	public CGEN0020_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	/**
	 * �v�Z���ڂ̎Z�o���s��
	 * @param reqData	�F���N�G�X�g�f�[�^
	 * @param userInfo	�F���[�U�[���
	 * @return RequestResponsKindBean	�F�v�Z����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public RequestResponsKindBean ExecLogic(

			RequestResponsKindBean reqData
			,UserInfoData          userInfo
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//���[�U�[���ޔ�
		userInfoData = userInfo;
		//���X�|���X�f�[�^
		RequestResponsKindBean ret = null;


		try{

			//����No�ޔ�
			cd_shisaku_syainID = toDecimal(reqData.getFieldVale("kihon", "rec", "cd_shain"),"-1");
			cd_shisaku_nen = toInteger(reqData.getFieldVale("kihon", "rec", "nen"),-1);
			cd_shisaku_oi = toInteger(reqData.getFieldVale("kihon", "rec", "no_oi"),-1);

			//�yQP@00342�z
			cd_shisaku_eda = toInteger(reqData.getFieldVale("kihon", "rec", "no_eda"),-1);

			//DB�擾���ڂ̎擾
			//�v�Z����
			getDBKomoku();

			// DEL 2013/7/2 shima�yQP@30151�zNo.37 start
//			//��]�����̒P��
//			strNm_tani_kibogenka = seachNmTaniKibogenka(
//					toString(reqData.getFieldVale("kihon", "rec", "kibo_genka_tani")));
			// DEL 2013/7/2 shima�yQP@30151�zNo.37 end

			//���X�|���X�̃C���X�^���X
			ret = new RequestResponsKindBean();
			//�v�Z���ڎZ�o
			calcuExec(reqData, ret);

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�����̍Čv�Z�Ɏ��s���܂����B");

		}finally{
			//���[�J���ϐ��̊J��
			removeList(listShisaku);
			removeList(listSanpuru);
			removeList(listGenryo);
		}

		return ret;

	}
	/**
	 * �v�Z���ځA�Z�o�̎���
	 * @param reqData		�F���N�G�X�g�f�[�^
	 * @param ret			�F�v�Z���ʃf�[�^
	 * @param listShisaku	�F�������ʁi������j
	 * @param listSanpuru	�F�������ʁi����ُ��j
	 * @param listGenryo	�F�������ʁi�������j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuExec(

			RequestResponsKindBean reqData
			, RequestResponsKindBean resData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//�v�Z���ʃf�[�^�N���X����
		resData.setID("CGEN0020");

		try{
			//��{����
			resData.addFieldVale("kihon", "rec", "flg_return", "true");
			resData.addFieldVale("kihon", "rec", "msg_error", "");
			resData.addFieldVale("kihon", "rec", "nm_class", "");
			resData.addFieldVale("kihon", "rec", "no_errmsg", "");
			resData.addFieldVale("kihon", "rec", "cd_error", "");
			resData.addFieldVale("kihon", "rec", "msg_system", "");

			if (toString(reqData.getFieldVale("kihon", "rec", "mode")).equals("1")){
				//�������z�̎Z�o
				calcuKingakuGenryo(reqData, resData);

				//���ދ��z�̎Z�o
				calcuKingakuShizai(reqData, resData, listShisaku, listGenryo);

				//�������̎Z�o
				calcuGenka(reqData, resData);

			}else{
				//���ދ��z�̎Z�o
				calcuKingakuShizai(reqData, resData, listShisaku, listGenryo);

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�v�Z���ځA�Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * �v�Z���ڂ��Z�o�����X�|���X�f�[�^�𐶐�����B
	 * @param reqData		�F���N�G�X�g�f�[�^
	 * @param resData		�F���X�|���X�f�[�^�i���ʂ��i�[����܂��j
	 * @param listShisaku	�FDB������
	 * @param listSanpuru	�FDB�T���v�����
	 * @param listGenryo	�FDB�������
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenka(

			RequestResponsKindBean reqData
			, RequestResponsKindBean resData
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int i = 0;
		int ix = 0;
		Object[] items = null;
		String errMsg = "";

		try{

			items = (Object[]) listShisaku.get(0);
			//�e��
			douYouryo = toDouble(items[3], -1);

			for (i =0; i < reqData.getCntRow("keisan"); i++){

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				String strKoteiChk = reqData.getFieldVale("keisan", i, "fg_koumokuchk");
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				//DB���̓���i�T���v�����j
				ix = seachSanpuruIndex(
						reqData.getFieldVale("keisan", i, "seq_shisaku")
						);
				items = (Object[]) listSanpuru.get(ix);

				//���R�[�h��������RESULT�ǉ�
				resData.addFieldVale("keisan", "rec" + toString(i), "flg_return", "true");
				resData.addFieldVale("keisan", "rec" + toString(i), "msg_error", "");
				resData.addFieldVale("keisan", "rec" + toString(i), "nm_class", "");
				resData.addFieldVale("keisan", "rec" + toString(i), "no_errmsg", "");
				resData.addFieldVale("keisan", "rec" + toString(i), "cd_error", "");
				resData.addFieldVale("keisan", "rec" + toString(i), "msg_system", "");

				// ----------------------------------------------------------------------+
				// ����SEQ		seq_shisaku
				// ----------------------------------------------------------------------+
				resData.addFieldVale("keisan", "rec" + toString(i), "seq_shisaku"
						, reqData.getFieldVale("keisan", i, "seq_shisaku"));
				// ----------------------------------------------------------------------+
				// ���Z��		shisan_date
				// ----------------------------------------------------------------------+

				//���N�G�X�g�Ɏ��Z�������݂��邩�̊m�F
//2010/03/02 UPDATE ISONO START [�o�O�Ή��@����ں��ޖ��i���Frec��Frec1�j������Ă��邽��shisan_date���擾�ł��Ȃ��Bں��ނ���ޯ���擾�ɕύX]
//				int index = reqData.getItemNo("keisan" , "rec" + toString(i) , "shisan_date");
				int index = reqData.getItemNo("keisan" , i , "shisan_date");
//2010/03/02 UPDATE ISONO END   [�o�O�Ή��@����ں��ޖ��i���Frec��Frec1�j������Ă��邽��shisan_date���擾�ł��Ȃ��Bں��ނ���ޯ���擾�ɕύX]

				if( index > -1 ){

					//���N�G�X�g�Ɏ��Z��������ꍇ�͗D�悵�ĕԋp����
					resData.addFieldVale("keisan", "rec" + toString(i), "shisan_date"
							, toString(reqData.getFieldVale("keisan", i, "shisan_date")));

				}
				else{

					//���N�G�X�g�Ɏ��Z�����Ȃ��ꍇ��DB�����l��ԋp����
					resData.addFieldVale("keisan", "rec" + toString(i), "shisan_date"
							, toString(items[10]));

				}

//				try{
//					//���N�G�X�g�Ɏ��Z��������ꍇ�͗D�悵�ĕԋp����
//					resData.addFieldVale("keisan", "rec" + toString(i), "shisan_date"
//							, toString(reqData.getFieldVale("keisan", i, "shisan_date")));
//				}catch(Exception e){
//					//���N�G�X�g�Ɏ��Z�����Ȃ��ꍇ��DB�����l��ԋp����
//					resData.addFieldVale("keisan", "rec" + toString(i), "shisan_date"
//							, toString(items[10]));
//
//				}

				// ----------------------------------------------------------------------+
				// �����No�i���́j	nm_sanpuru
				// ----------------------------------------------------------------------+
				resData.addFieldVale("keisan", "rec" + toString(i), "nm_sanpuru"
						, toString(items[9]));
				// ----------------------------------------------------------------------+
				// �[�U�ʐ����i���j	jyuuten_suiso
				// ----------------------------------------------------------------------+
				douZyutenSui = toDouble(items[5], -1);

				//�yQP@00342�z����
				if(cd_shisaku_eda == 0){

				}
				//�}��
				else{
					//���ł̐����[�U��/���ł̓��e�ʁ~�}�ł̓��e��
					douZyutenSui = douZyutenSui / yoryo_moto * douYouryo;
				}

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					douZyutenSui = toDouble(reqData.getFieldVale("keisan", i, "zyusui"), -1);
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				if(douZyutenSui > -1){
					resData.addFieldVale("keisan", "rec" + toString(i), "jyuuten_suiso"
							, toString(douZyutenSui, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "jyuuten_suiso"
							, "");

				}

				// ----------------------------------------------------------------------+
				// �[�U�ʖ����i���j	jyuuten_yuso
				// ----------------------------------------------------------------------+
				douZyutenYu = toDouble(items[6], -1);

				//�yQP@00342�z����
				if(cd_shisaku_eda == 0){

				}
				//�}��
				else{
					//���ł̏[�U�ʖ���/���ł̓��e�ʁ~�}�ł̓��e��
					douZyutenYu = douZyutenYu / yoryo_moto * douYouryo;
				}

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					douZyutenYu = toDouble(reqData.getFieldVale("keisan", i, "zyuabura"), -1);
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				if (douZyutenYu > -1){
					resData.addFieldVale("keisan", "rec" + toString(i), "jyuuten_yuso"
							, toString(douZyutenYu, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "jyuuten_yuso"
							, "");

				}

				// ----------------------------------------------------------------------+
				// ���v��		gookezyuryo
				// ----------------------------------------------------------------------+
				//�yQP@00342�z
//				resData.addFieldVale("keisan", "rec" + toString(i), "gookezyuryo"
//						, toString(items[11]));
				//����
				if(cd_shisaku_eda == 0){
					resData.addFieldVale("keisan", "rec" + toString(i), "gookezyuryo"
							, toString(items[11]));
				}
				//�}��
				else{
					//�[�U�ʐ����i���j + �[�U�ʖ����i���j
					double goukei = douZyutenSui + douZyutenYu;
					resData.addFieldVale("keisan", "rec" + toString(i), "gookezyuryo"
							, toString(goukei, 2, 2, true, ""));

				}

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "gookezyuryo"
							, toString(reqData.getFieldVale("keisan", i, "gokei"),""));
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				// ----------------------------------------------------------------------+
				// ��d			hizyu
				// ----------------------------------------------------------------------+
				resData.addFieldVale("keisan", "rec" + toString(i), "hizyu"
						, toString(items[7]));

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					resData.addFieldVale("keisan", "rec" + toString(i), "hizyu"
							, reqData.getFieldVale("keisan", i, "hiju"));
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				// ----------------------------------------------------------------------+
				// �L�������i���j	yuuko_budomari
				// ----------------------------------------------------------------------+
				douYukoBudomari = toDouble(reqData.getFieldVale("keisan", i, "yuuko_budomari"), -1);
				if(douYukoBudomari > -1){
					resData.addFieldVale(
							"keisan", "rec" + toString(i)
							, "yuuko_budomari"
							, toString(
									douYukoBudomari
									, 2
									, 1
									, false
									, "")
							);

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "yuuko_budomari"
							, "");

				}
				// ----------------------------------------------------------------------+
				// ���x���ʁi���j	reberuryo	?
				// ----------------------------------------------------------------------+
				calcuReberuryo(
						douYouryo
						, toDouble(reqData.getFieldVale("kihon", "rec", "irisu"), -1)
						);

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					douLevelRyo=toDouble(reqData.getFieldVale("keisan", i, "reberu"), -1);
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				if (douLevelRyo > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "reberuryo"
							, toString(douLevelRyo, 6, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "reberuryo"
							, "");

				}

				// ----------------------------------------------------------------------+
				// ���Ϗ[�U�ʁi���j	heikinjyutenryo
				// ----------------------------------------------------------------------+
				douHeikinZyuten = toDouble(reqData.getFieldVale("keisan", i, "heikinjyutenryo"), -1);
				if(douHeikinZyuten > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "heikinjyutenryo"
							, toString(
									douHeikinZyuten
									, 6
									, 2
									, true
									, "")
							);

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "heikinjyutenryo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// ��d���Z�ʁi���j	hizyukasanryo
				// ----------------------------------------------------------------------+
				calcuHizyukasanryo(
						douYouryo
						, toDouble(reqData.getFieldVale("kihon", "rec", "irisu"), -1)
						, toDouble(items[7], -1)
						);

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					douHizykasanRyo=toDouble(reqData.getFieldVale("keisan", i, "hijukasan"), -1);
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				if (douHizykasanRyo > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "hizyukasanryo"
							, toString(douHizykasanRyo, 6, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "douHizykasanRyo"
							, "");

				}

				// ----------------------------------------------------------------------+
				// ������/�P�[�X	kesu_genryohi
				// ----------------------------------------------------------------------+
				calcuGenryohiKesu(
						reqData
						, reqData.getFieldVale("keisan", i, "seq_shisaku")
						);

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					douGenryohiKesu=toDouble(reqData.getFieldVale("keisan", i, "cs_genryo"), -1);
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				if (douGenryohiKesu > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_genryohi"
							, toString(douGenryohiKesu, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_genryohi"
							, "");

				}

				// ----------------------------------------------------------------------+
				// �ޗ���/�P�[�X	kesu_zairyohi
				// ----------------------------------------------------------------------+
				if (douKingakuKei_Shizai > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_zairyohi"
							, toString(douKingakuKei_Shizai, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_zairyohi"
							, "");

				}

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_zairyohi"
							, toString(toDouble(reqData.getFieldVale("keisan", i, "cs_zairyohi"),-1), 2, 2, true, ""));
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				// ----------------------------------------------------------------------+
				// �Œ��/�P�[�X	kesu_kotehi
				// ----------------------------------------------------------------------+
				//MOD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg").equals("1")
						|| strKoteiChk.equals("1")){
					//���W�I�{�^�����Œ�/������I������Ă���ꍇ
					//���͒l��ݒ�
					douKotehi_Kesu = toDouble(reqData.getFieldVale("keisan", i, "kesu_kotehi"));

				}else{
					//���W�I�{�^�����Œ�/Kg���I������Ă���ꍇ
					//�v�Z�l��ݒ�
					calcuKoteihi_Kesu(reqData, i);

				}
				//MOD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
				if (douKotehi_Kesu > 0){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_kotehi"
							, toString(douKotehi_Kesu, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_kotehi"
							, "");

				}

				// ADD 2013/11/1 QP@30154 okano start
				// ----------------------------------------------------------------------+
				// ���v/�P�[�X	kesu_rieki
				// ----------------------------------------------------------------------+
				//MOD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg").equals("1")
						|| strKoteiChk.equals("1")){
					//���W�I�{�^�����Œ�/������I������Ă���ꍇ
					//���͒l��ݒ�
					douRieki_Kesu = toDouble(reqData.getFieldVale("keisan", i, "kesu_rieki"));

				}else{
					//���W�I�{�^�����Œ�/Kg���I������Ă���ꍇ
					//�v�Z�l��ݒ�
					calcuRieki_Kesu(reqData, i);

				}
				//MOD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
				if (douRieki_Kesu > 0){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_rieki"
							, toString(douRieki_Kesu, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_rieki"
							, "");
					// ADD 2013/12/4 QP@30154 okano start
					douRieki_Kesu = 0;
					// ADD 2013/12/4 QP@30154 okano end

				}
				// ADD 2013/11/1 QP@30154 okano end

				// ----------------------------------------------------------------------+
				// �����v/�P�[�X	kesu_genkake
				// ----------------------------------------------------------------------+
				calcuGenkaKei_Kesu();

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					douGenkaKei_Kesu=toDouble(reqData.getFieldVale("keisan", i, "cs_genka"), -1);
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				if (douGenkaKei_Kesu > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kesu_genkake"
							, toString(douGenkaKei_Kesu, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_genkake"
							, "");

				}
				// ----------------------------------------------------------------------+
				// �����v/��		ko_genkake
				// ----------------------------------------------------------------------+
				calcuGenkaKei_ko();

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					douGenkaKei_Ko=toDouble(reqData.getFieldVale("keisan", i, "ko_genka"), -1);
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				if (douGenkaKei_Ko > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "ko_genkake"
							, toString(douGenkaKei_Ko, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kesu_genkake"
							, "");

				}
				// ----------------------------------------------------------------------+
				// ������/KG		kg_genryohi
				// ----------------------------------------------------------------------+
				calcuGenryohiKg();

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					douGenryohiKg=toDouble(reqData.getFieldVale("keisan", i, "kg_genryo"), -1);
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				if (douGenryohiKg > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kg_genryohi"
							, toString(douGenryohiKg, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kg_genryohi"
							, "");

				}
				// ----------------------------------------------------------------------+
				// �ޗ���/KG		kg_zairyohi
				// ----------------------------------------------------------------------+
				calcuZairyohiKg();

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					douZairyohi_Kg=toDouble(reqData.getFieldVale("keisan", i, "kg_zairyohi"), -1);
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				if (douZairyohi_Kg > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kg_zairyohi"
							, toString(douZairyohi_Kg, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kg_zairyohi"
							, "");

				}
				// ----------------------------------------------------------------------+
				// �Œ��/KG		kg_kotehi
				// ----------------------------------------------------------------------+
				//MOD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg").equals("2")
						|| strKoteiChk.equals("1")){
					//���W�I�{�^�����Œ�/kg���I������Ă���ꍇ
					//���͒l��ݒ�
					douKotehi_Kg = toDouble(reqData.getFieldVale("keisan", i, "kg_kotehi"));

				}else{
					//���W�I�{�^�����Œ�/������I������Ă���ꍇ
					//�v�Z�l��ݒ�
					calcuKoteihi_Kg(reqData, i);

				}
				//MOD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
				if (douKotehi_Kg > 0){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kg_kotehi"
							, toString(douKotehi_Kg, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kg_kotehi"
							, "");

				}

				// ADD 2013/11/1 QP@30154 okano start
				// ----------------------------------------------------------------------+
				// ���v/KG		kg_rieki
				// ----------------------------------------------------------------------+
				//MOD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg").equals("2")
						|| strKoteiChk.equals("1")){
					//���W�I�{�^�����Œ�/kg���I������Ă���ꍇ
					//���͒l��ݒ�
					douRieki_Kg = toDouble(reqData.getFieldVale("keisan", i, "kg_rieki"));

				}else{
					//���W�I�{�^�����Œ�/������I������Ă���ꍇ
					//�v�Z�l��ݒ�
					calcuRieki_Kg(reqData, i);

				}
				//MOD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end
				if (douRieki_Kg > 0){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kg_rieki"
							, toString(douRieki_Kg, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kg_rieki"
							, "");
					// ADD 2013/12/4 QP@30154 okano start
					douRieki_Kg = 0;
					// ADD 2013/12/4 QP@30154 okano end

				}
				// ADD 2013/11/1 QP@30154 okano end
				// ----------------------------------------------------------------------+
				// �����v/KG		kg_genkake
				// ----------------------------------------------------------------------+
				calcuGenkaKei_Kg();

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					douGenkaKei_Kg=toDouble(reqData.getFieldVale("keisan", i, "kg_genka"), -1);
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				if (douGenkaKei_Kg > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "kg_genkake"
							, toString(douGenkaKei_Kg, 2, 2, true, ""));

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "kg_genkake"
							, "");

				}

				// ----------------------------------------------------------------------+
				// ����			baika
				// ----------------------------------------------------------------------+

				// ADD 2013/7/2 shima�yQP@30151�zNo.37 start
				//��]�����̒P��
				strNm_tani_kibogenka = seachNmTaniKibogenka(
						toString(reqData.getFieldVale("kihonsub",i, "kibo_genka_tani")));
				// ADD 2013/7/2 shima�yQP@30151�zNo.37 end
				// MOD 2013/7/2 shima�yQP@30151�zNo.37 start
//				douKiboBaika = toDouble(reqData.getFieldVale("kihon", "rec", "kibo_baika"), -1);
				douKiboBaika = toDouble(reqData.getFieldVale("kihonsub", i, "kibo_baika"), -1);
				// MOD 2013/7/2 shima�yQP@30151�zNo.37 end

				if (douKiboBaika > -1){
					resData.addFieldVale(
							"keisan"
							, "rec" + toString(i)
							, "baika"
							, toString(douKiboBaika, 2, 2, true, "") + strNm_tani_kibogenka);

				}else{
					resData.addFieldVale("keisan", "rec" + toString(i), "baika"
							, "");

				}

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				//�P�ʂ��ꏏ�ɕێ����Ă���ׁA���̃^�C�~���O�Őݒ肷��
				if(strKoteiChk.equals("1")){
					resData.addFieldVale("keisan", "rec" + toString(i), "baika"
							, reqData.getFieldVale("keisan", i, "baika"));
				}
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				// ----------------------------------------------------------------------+
				// �e���i���j		arari
				// ----------------------------------------------------------------------+
//				if(reqData.getFieldVale("kihon", "rec", "kibo_genka_tani").equals("001")){
				if(reqData.getFieldVale("kihonsub", i, "kibo_genka_tani").equals("001")){
					//������]�P�ʂ��@�@�̏ꍇ
					calcuArari_Ko();

				}else{
					//������]�P�ʂ��@kg�@�̏ꍇ
					calcuArari_Kg();

				}

//				if (douArari > -1){
//					resData.addFieldVale(
//							"keisan"
//							, "rec" + toString(i)
//							, "arari"
//							, toString(douArari, 2, 2, true, ""));
//
//				}else{
//					resData.addFieldVale("keisan", "rec" + toString(i), "arari"
//							, "");

//				}

				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 start
				//�Œ荀�ڂ��`�F�b�N����Ă���ꍇ�́A�v�Z�l�ł͂Ȃ���ʒl or DB�l��ݒ肷��
				if(strKoteiChk.equals("1")){
					//���K�\���F���l�m�F�p
					String strArari = reqData.getFieldVale("keisan", i, "arari");
					Pattern pattern = Pattern.compile("^[-]?[0-9,]*[.]?[0-9]+");
					Matcher matcher = pattern.matcher(strArari);
					if (matcher.find()) {
						strArari = matcher.group();
					} else {
						strArari = "";
					}
					douArari=toDouble(strArari, -1);
				}

				resData.addFieldVale(
						"keisan"
						, "rec" + toString(i)
						, "arari"
						, toString(douArari, 2, 2, true, ""));
				//ADD 2014/01/15 nishigawa �yQP@30154�z�ǉ��ۑ�No.11 end

				// ----------------------------------------------------------------------+
				//�@���x���ʁE���Ϗ[�U�ʂ̔�r
				// ----------------------------------------------------------------------+
				//�yQP@00342�z���Z���~�̏ꍇ�̓`�F�b�N�ΏۊO
				if(toString(reqData.getFieldVale("keisan", i, "fg_chusi")).equals("")){
					if( douLevelRyo > douHeikinZyuten && douHeikinZyuten > -1 ){
						errMsg += "�y"+(i+1)+"��ځz";
					}
				}
				else{

				}

//mod start -------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.38
				// ----------------------------------------------------------------------+
				// �����H����			seizokotei_han
				// ----------------------------------------------------------------------+
				resData.addFieldVale("keisan", "rec" + toString(i), "seizokotei_han"
							, reqData.getFieldVale("keisan", i, "seizokotei_han"));
				// ----------------------------------------------------------------------+
				// �����H��			seizokotei_shosai
				// ----------------------------------------------------------------------+
				resData.addFieldVale("keisan", "rec" + toString(i), "seizokotei_shosai"
							, reqData.getFieldVale("keisan", i, "seizokotei_shosai"));

//mod end --------------------------------------------------------------------------------


				//�yQP@00342�z���Z���~
				resData.addFieldVale("keisan", "rec" + toString(i), "fg_chusi"
						, reqData.getFieldVale("keisan", i, "fg_chusi"));

//ADD 2013/07/9 ogawa �yQP@30151�zNo.13 start
				// ----------------------------------------------------------------------+
				// ���ڌŒ�`�F�b�N	fg_koumokuchk
				// ----------------------------------------------------------------------+

				resData.addFieldVale("keisan", "rec" + toString(i), "fg_koumokuchk"
						, reqData.getFieldVale("keisan", i, "fg_koumokuchk"));
//ADD 2013/07/09 ogawa �yQP@30151�zNo.13 end
			}
			//���X�|���X��{�̒ǉ�
			// ----------------------------------------------------------------------+
			//�@�T���v�����i��j
			// ----------------------------------------------------------------------+
			resData.addFieldVale("kihon", "rec", "cnt_sanpuru"
					, toString(reqData.getCntRow("keisan")));
			// ----------------------------------------------------------------------+
			//�@�����H��
			// ----------------------------------------------------------------------+
			resData.addFieldVale("kihon", "rec", "koute"
					, toString(seachSeizoKote(reqData)));
			// ----------------------------------------------------------------------+
			//�@�v�Z���ځi�Œ��/�P�[�Xor�Œ��/kg�j
			// ----------------------------------------------------------------------+
			resData.addFieldVale("kihon", "rec", "ragio_kesu_kg"
					, toString(reqData.getFieldVale("kihon", "rec", "ragio_kesu_kg")));



			// ----------------------------------------------------------------------+
			//�@���x���ʁE���Ϗ[�U�ʂ̔�r
			// ----------------------------------------------------------------------+
			if( toString(errMsg).equals("") ){

			}else{
				resData.addFieldVale("kihon", "rec", "err_msg"
						, errMsg + "���Ϗ[�U�ʂ̓��x���ʈȏ�̒l����͂��ĉ������B");
			}



		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�����̎Z�o�Ɏ��s���܂����B");

		}finally{
			//���[�J���ϐ��̊J��
			items = null;

		}

	}
	/**
	 * �e���i���j/kg�@�̎Z�o
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuArari_Kg(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@�i1�|�����v�i�~�j/Kg����]�����j�~100
		//+�@
		//+�@�y�v�Z�����@�v�Z���ځ@�z�����v(�~)/kg	douGenkaKei_Kg
		//+�@�y�v�Z�����@�������ځ@�z��]����			douKiboBaika
		//+�@
		//+--------------------------------------------+

		//�y�@�@�@�@�@�v�Z���ځ@�z�e��(��)
		douArari = -1;

		try{

			//�v�Z���{����
			if (
					douGenkaKei_Kg > -1
					&&	douKiboBaika > -1
					){

				//�v�Z
				if (douKiboBaika > 0){
					douArari = ( 1 - douGenkaKei_Kg / douKiboBaika ) * 100;

				}else{
					douArari = 0;

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�e���i���j/kg�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * �e���i���j/�@�̎Z�o
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuArari_Ko(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@�i1�|�����v�i�~�j/����]�����j�~100
		//+�@
		//+�@�y�v�Z�����@�v�Z���ځ@�z�����v(�~)/��	douGenkaKei_Ko
		//+�@�y�v�Z�����@�������ځ@�z��]����			douKiboBaika
		//+�@
		//+--------------------------------------------+

		//�y�@�@�@�@�@�v�Z���ځ@�z�e��(��)
		douArari = -1;

		try{

			//�v�Z���{����
			if (
					douGenkaKei_Ko > -1
					&&	douKiboBaika > -1
					){

				//�v�Z
				if (douKiboBaika > 0){
					douArari = ( 1 - douGenkaKei_Ko / douKiboBaika ) * 100;

				}else{
					douArari = 0;

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�e���i���j/�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * �����v/Kg�̎Z�o
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenkaKei_Kg(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@�y2009/11/27�@�ۑ�Ǘ��F119�@�ύX�z�����v/�P�[�X�����x����(g)�~1000
		//+�@�����v/�P�[�X�����x����(g)
		//+�@
		//+�@�y�v�Z�����@�v�Z���ځ@�z�����v(�~)/�P�[�X	douGenkaKei_Kesu
		//+�@�y�v�Z�����@�v�Z���ځ@�z���x����(��)		douLevelRyo
		//+�@
		//+--------------------------------------------+

		//�y�v�Z�����@�v�Z���ځ@�z�����v(�~)/kg
		douGenkaKei_Kg = -1;

		try{

			//�v�Z���{����
			// MOD 2013/12/7 QP@30154 okano start
//				if (
//						douGenkaKei_Kesu > -1
//						&&	douLevelRyo > -1
//						){
//
//					//�v�Z
//					if(douLevelRyo > 0){
//	//					douGenkaKei_Kg = douGenkaKei_Kesu / douLevelRyo * 1000;
//						douGenkaKei_Kg = douGenkaKei_Kesu / douLevelRyo;
//
//					}else{
//						douGenkaKei_Kg = 0;
//
//					}
//
//				}
			if (
					douGenryohiKg > -1
					&&	douZairyohi_Kg > -1
					&&	douKotehi_Kg > -1
					){
						douGenkaKei_Kg = douGenryohiKg + douZairyohi_Kg + douKotehi_Kg + douRieki_Kg;

			}
			// MOD 2013/12/7 QP@30154 okano end

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�����v/Kg�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * �Œ��/Kg�̎Z�o
	 * @param reqData	�F���N�G�X�g�f�[�^
	 * @param seq		�F��C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuKoteihi_Kg(

			RequestResponsKindBean reqData
			, int seq
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@�y2009/11/27�@�ۑ�Ǘ��F119�@�ύX�z�Œ��/�P�[�X�����x����(g)�~1000
		//+�@�Œ��/�P�[�X�����x����(g)
		//+�@
		//+�@�y�v�Z�����@�������ځ@�z�Œ��(�~)/�P�[�X	douKotehi_Kesu
		//+�@�y�v�Z�����@�v�Z���ځ@�z���x����(��)		douLevelRyo
		//+�@
		//+--------------------------------------------+

		//�y�v�Z�����@�������ځ@�z�Œ��(�~)/kg
		douKotehi_Kg = -1;

		try{

			//�v�Z�����̎��W
			//�Œ��(�~)/kg
			douKotehi_Kesu = toDouble(reqData.getFieldVale("keisan", seq, "kesu_kotehi"), -1);

			//�v�Z���{����
			if (
					douKotehi_Kesu > -1
				&&	douLevelRyo > -1
					){

				if (	douKotehi_Kesu > 0
						&&	douLevelRyo > 0
					){

//					douKotehi_Kg = douKotehi_Kesu / douLevelRyo * 1000;
					douKotehi_Kg = douKotehi_Kesu / douLevelRyo;

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�Œ��/Kg�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	// ADD 2013/11/1 QP@30154 okano start
	/**
	 * ���v/Kg�̎Z�o
	 * @param reqData	�F���N�G�X�g�f�[�^
	 * @param seq		�F��C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuRieki_Kg(

			RequestResponsKindBean reqData
			, int seq
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@�y2009/11/27�@�ۑ�Ǘ��F119�@�ύX�z���v/�P�[�X�����x����(g)�~1000
		//+�@���v/�P�[�X�����x����(g)
		//+�@
		//+�@�y�v�Z�����@�������ځ@�z���v(�~)/�P�[�X	douKotehi_Kesu
		//+�@�y�v�Z�����@�v�Z���ځ@�z���x����(��)		douLevelRyo
		//+�@
		//+--------------------------------------------+

		//�y�v�Z�����@�������ځ@�z���v(�~)/kg
		douRieki_Kg = -1;

		try{

			//�v�Z�����̎��W
			//���v(�~)/kg
			douRieki_Kesu = toDouble(reqData.getFieldVale("keisan", seq, "kesu_rieki"), -1);

			//�v�Z���{����
			if (
					douRieki_Kesu > -1
				&&	douLevelRyo > -1
					){

				if (	douRieki_Kesu > 0
						&&	douLevelRyo > 0
					){

					douRieki_Kg = douRieki_Kesu / douLevelRyo;

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@���v/Kg�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	// ADD 2013/11/1 QP@30154 okano end
	/**
	 * �ޗ���/KG�̎Z�o
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuZairyohiKg(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@�y2009/11/27�@�ۑ�Ǘ��F119�@�ύX�z�ޗ���/�P�[�X�����x����(g)�~1000
		//+�@�ޗ���/�P�[�X�����x����(g)
		//+�@
		//+�@�y�v�Z�����@�v�Z���ځ@�z���z�v  ����	douKingakuKei_Shizai
		//+�@�y�v�Z�����@�v�Z���ځ@�z���x����(��)	douLevelRyo
		//+�@
		//+--------------------------------------------+

		//�y�@�@�@�@�@�v�Z���ځ@�z�ޗ���(�~)/kg
		douZairyohi_Kg = -1;

		try{

			//�v�Z���{����
			if (
					douKingakuKei_Shizai > -1
					&&	douLevelRyo > -1
					){

				if(douLevelRyo > 0){
//					douZairyohi_Kg = douKingakuKei_Shizai / douLevelRyo * 1000;
					douZairyohi_Kg = douKingakuKei_Shizai / douLevelRyo;

				}else{
					douZairyohi_Kg = 0;

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�ޗ���/KG�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * ������/KG�̎Z�o
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenryohiKg(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@�y2009/11/27�@�ۑ�Ǘ��F119�@�ύX�z������/�P�[�X�����x����(g)�~1000
		//+�@������/�P�[�X�����x����(g)
		//+�@
		//+�@�y�v�Z�����@�v�Z���ځ@�z������(�~)/�P�[�X	douGenryohiKesu
		//+�@�y�v�Z�����@�v�Z���ځ@�z���x����(��)		douLevelRyo
		//+�@
		//+--------------------------------------------+

		//�y�@�@�@�@�@�v�Z���ځ@�z������(�~)/kg
		douGenryohiKg = -1;

		try{

			//�v�Z���{����
			if (
					douGenryohiKesu > -1
					&&	douLevelRyo > -1
					){

				if (douLevelRyo > 0){
//					douGenryohiKg = douGenryohiKesu / douLevelRyo * 1000;
					// MOD 2013/8/2 okano�yQP@30151�zNo.34 start
//						douGenryohiKg = douGenryohiKesu / douLevelRyo;
					douGenryohiKg = douGenryohiKesu / ( douLevelRyo * douHizyu );
					// MOD 2013/8/2 okano�yQP@30151�zNo.34 end

				}else{
					douGenryohiKg = 0;

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@������/KG�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * �����v/�̎Z�o
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenkaKei_ko(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@�����v/�P�[�X������
		//+�@
		//+�@�y�v�Z�����@�v�Z���ځ@�z�����v(�~)/�P�[�X	douGenkaKei_Kesu
		//+�@�y�v�Z�����@�������ځ@�z���萔			douIrisu
		//+�@
		//+--------------------------------------------+

		//�y�v�Z�����@�v�Z���ځ@�z�����v(�~)/��
		douGenkaKei_Ko = -1;

		try{

			//�v�Z���{����
			if (
					douGenkaKei_Kesu > -1
					&&	douIrisu > -1
					){

				if (douIrisu > 0){
					douGenkaKei_Ko = douGenkaKei_Kesu / douIrisu;

				}else{
					douGenkaKei_Ko = 0;

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�����v/�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * �����v/�P�[�X�̎Z�o
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenkaKei_Kesu(

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@������/�P�[�X+�ޗ���/�P�[�X+�Œ��/�P�[�X
		//+�@
		//+�@�y�v�Z�����@�������ځ@�z�Œ��(�~)/�P�[�X	douKotehi_Kesu
		//+�@�y�v�Z�����@�v�Z���ځ@�z���z�v  ����		douKingakuKei_Shizai
		//+�@�y�v�Z�����@�v�Z���ځ@�z������(�~)/�P�[�X	douGenryohiKesu
		//+�@
		//+--------------------------------------------+
		//�y�v�Z�����@�v�Z���ځ@�z�����v(�~)/�P�[�X
		douGenkaKei_Kesu = -1;

		try{

			//�v�Z���{����
			if (
					douKotehi_Kesu > -1
					&&	douKingakuKei_Shizai > -1
					&&	douGenryohiKesu > -1
					){

				// MOD 2013/11/7 QP@30154 okano start
//					douGenkaKei_Kesu = douGenryohiKesu + douKingakuKei_Shizai + douKotehi_Kesu;
				douGenkaKei_Kesu = douGenryohiKesu + douKingakuKei_Shizai + douKotehi_Kesu + douRieki_Kesu;
				// MOD 2013/11/7 QP@30154 okano end
			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�����v/�P�[�X�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * �Œ��/�P�[�X�̎Z�o
	 * @param reqData	�F���N�G�X�g�f�[�^
	 * @param seq		�F��C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuKoteihi_Kesu(

			RequestResponsKindBean reqData
			, int seq
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@�y2009/11/27�@�ۑ�Ǘ��F119�@�ύX�z�Œ��/�s�~���x���ʁi���j/1000
		//+�@�Œ��/�s�~���x���ʁi���j
		//+�@
		//+�@�y�v�Z�����@�������ځ@�z�Œ��(�~)/kg	douKotehi_Kg
		//+�@�y�v�Z�����@�v�Z���ځ@�z���x����(��)		douLevelRyo
		//+�@
		//+--------------------------------------------+

		//�y�v�Z�����@�������ځ@�z�Œ��(�~)/�P�[�X
		douKotehi_Kesu = -1;

		try{

			//�v�Z�����̎��W
			//�Œ��(�~)/kg
			douKotehi_Kg = toDouble(reqData.getFieldVale("keisan", seq, "kg_kotehi"), -1);

			//�v�Z���{����
			if (
					douKotehi_Kg > -1
				&&	douLevelRyo > -1
					){

				if (
						douKotehi_Kg > 0
					&&	douLevelRyo > 0
						){

//					douKotehi_Kesu = douKotehi_Kg * douLevelRyo / 1000;
					douKotehi_Kesu = douKotehi_Kg * douLevelRyo;

				}


			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�Œ��/�P�[�X�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	// ADD 2013/11/1 QP@30154 okano start
	/**
	 * ���v/�P�[�X�̎Z�o
	 * @param reqData	�F���N�G�X�g�f�[�^
	 * @param seq		�F��C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuRieki_Kesu(

			RequestResponsKindBean reqData
			, int seq
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+�@
		//+�@�y2009/11/27�@�ۑ�Ǘ��F119�@�ύX�z���v/�s�~���x���ʁi���j/1000
		//+�@���v/�s�~���x���ʁi���j
		//+�@
		//+�@�y�v�Z�����@�������ځ@�z���v(�~)/kg	douRieki_Kg
		//+�@�y�v�Z�����@�v�Z���ځ@�z���x����(��)		douLevelRyo
		//+�@
		//+--------------------------------------------+

		//�y�v�Z�����@�������ځ@�z���v(�~)/�P�[�X
		douRieki_Kesu = -1;

		try{

			//�v�Z�����̎��W
			//���v(�~)/kg
			douRieki_Kg = toDouble(reqData.getFieldVale("keisan", seq, "kg_rieki"), -1);

			//�v�Z���{����
			if (
					douRieki_Kg > -1
				&&	douLevelRyo > -1
					){

				if (
						douRieki_Kg > 0
					&&	douLevelRyo > 0
						){

					douRieki_Kesu = douRieki_Kg * douLevelRyo;

				}


			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@���v/�P�[�X�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	// ADD 2013/11/1 QP@30154 okano end
	/**
	 * ������i�~�j/�P�[�X�̎Z�o
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenryohiKesu(

			RequestResponsKindBean reqData
			, String seq_shisaku
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//		���������̏ꍇ
		//       ���e�����}�X�^�̊Y���H����VALUE1��"1"�̍H��
		//
		//		�����̑��������ȊO�̏ꍇ
		//       ���e�����}�X�^�̊Y���H����VALUE1��"2"�̍H��
		//
		//      �����݂��Ă���ꍇ�͌v�Z�ł��Ȃ��B
		//
		//+--------------------------------------------+

		douGenryohiKesu = -1;

		try{

			//�H���̎�ޔ���
			if (flgKoteGroup == 1){
				//�����t�p�^�[��
				douGenryohiKesu = calcuGenryohiKesu_Cyoumi(reqData, seq_shisaku);

			}else if (flgKoteGroup == 2){
				//���̑������t�ȊO�o�^�[��
				douGenryohiKesu = calcuGenryohiKesu_Sonota(reqData, seq_shisaku);

			}else{
				//�H���̎�ނ����݂��Ă���̂ŁA�v�Z�ł��Ȃ�
				//Skip!

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@������i�~�j/�P�[�X�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * ������i�~�j/�P�[�X(�����t�ȊO)�̎Z�o
	 * @param reqData		�F���N�G�X�g�f�[�^
	 * @param listSanpuru	�FDB�T���v�����
	 * @param listGenryo	�FDB�������
	 * @param seq_shisaku	�F����SEQ
	 * @return double		�F�Z�o����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private double calcuGenryohiKesu_Sonota(

			RequestResponsKindBean reqData
			, String seq_shisaku
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//
		//		�����̑��������ȊO�̏ꍇ
		//		�@�y2009/11/27�@�ۑ�Ǘ��F119�@�ύX�z�u���z�v�v�����v�d��d�ʁi���j�~���Ϗ[�U�ʁi���j��1000��(�L�������i���j��100)
		//		�@�u���z�v�v�����v�d��d�ʁi���j�~���Ϗ[�U�ʁi���j��(�L�������i���j��100)
		//		�@�@
		//		�@�u���z�v�v
		//		�@�@�u�z��:�D�v�~�u�P��:�B�v�� (�u����:�C�v��100)�̌v
		//
		// �y�v�Z�����@DB�擾���ځz���v�d��d��(�s)douGokeShiagari
		// �y�v�Z�����@�������ځ@�z���Ϗ[�U��(��)	douHeikinZyuten
		// �y�v�Z�����@�������ځ@�z�L������(��)	douYukoBudomari
		//
		//+--------------------------------------------+

		//���z�v
		double gokeKingaku = -1;

		Object[] items = null;
		double ret = -1;

		try{

			//�v�Z�����̎��W

			//���v�d���d�ʁi�T���v��4�j
			int ix = seachSanpuruIndex(seq_shisaku);

			items = (Object[]) listSanpuru.get(ix);

			douGokeShiagari = toDouble(items[4], -1);

			//���z�v�̏W�v
			gokeKingaku = calcuGenryohiKesu_Sonota_Syuke(
					reqData
					, seq_shisaku);

			//�����̃`�F�b�N
			if (
					douGokeShiagari > -1
				&&	douHeikinZyuten > -1
				&&	douYukoBudomari > -1
				&&	gokeKingaku > -1

			){

				//�v�Z
				if(douGokeShiagari > 0){
//					ret = gokeKingaku / douGokeShiagari * douHeikinZyuten / 1000
//					/ (douYukoBudomari / 100);
					ret = gokeKingaku / douGokeShiagari * douHeikinZyuten
					/ (douYukoBudomari / 100);

				}else{
					ret = 0;

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@������i�~�j/�P�[�X(�����t�ȊO)�̎Z�o�Ɏ��s���܂����B");

		}finally{
			//���[�J���ϐ��̊J��
			items = null;

		}
		return ret;

	}
	/**
	 * ���z�v�̏W�v
	 * @param reqData		�F���N�G�X�g�f�[�^
	 * @param listGenryo	�FDB�������
	 * @param seq_shisaku	�F����SEQ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private double calcuGenryohiKesu_Sonota_Syuke(

			RequestResponsKindBean reqData
			, String seq_shisaku
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//
		//		�@�u���z�v�v
		//		�@�@�z���~�P����(������100)�̌v
		//
		// �y�v�Z�����@DB�擾���ځz�z��(�s)		douHaigo
		// �y�v�Z�����@�������ځ@�z�P��(�~/�s)	douTanka_gen
		// �y�v�Z�����@�������ځ@�z����(��)		douBudomari_Gen
		//
		//+--------------------------------------------+

		Object[] items = null;

		//���z�v
		double gokeKingaku = 0;

		try{

			//����DB������

			for (int i =0; i < reqData.getCntRow("genryo"); i++){

				int ix = seachGenryoIndex(
						seq_shisaku
						, reqData.getFieldVale("genryo", i, "cd_kotei")
						, reqData.getFieldVale("genryo", i, "seq_kotei")
						);

				items = (Object[]) listGenryo.get(ix);

				//�W�v

				//�z����
				douHaigo = toDouble(items[8], -1);
				//�P��
				douTanka_gen = toDouble(reqData.getFieldVale("genryo", i, "tanka"), -1);
				//������
				douBudomari_Gen = toDouble(reqData.getFieldVale("genryo", i, "budomari"), -1);

				//���z�v
				if ( douHaigo > 0 && douTanka_gen > 0 && douBudomari_Gen > 0 ){
					gokeKingaku += douHaigo * douTanka_gen / (douBudomari_Gen / 100);

				}else{

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@������i�~�j/�P�[�X(�����t�p�^�[��)�A���v�P���C���v�d�ʂ̎Z�o�Ɏ��s���܂����B");

		}finally{
			//���[�J���ϐ��̊J��
			items = null;

		}
		return gokeKingaku;

	}
	/**
	 * ������i�~�j/�P�[�X(�����t�p�^�[��)�̎Z�o
	 * @param reqData		�F���N�G�X�g�f�[�^
	 * @param listGenryo	�FDB�������
	 * @param seq_shisaku	�F����SEQ
	 * @return double		�F�Z�o����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private double calcuGenryohiKesu_Cyoumi(

			RequestResponsKindBean reqData
			, String seq_shisaku
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//
		//		���������̏ꍇ
		//		�@(�@(�u�������v�P���v���u�������v�d�ʁv�~�[�U�ʐ����i���j��1000�j
		//		�@�{�i�u�������v�P���v���u�������v�d�ʁv�~�[�U�ʖ����i���j��1000) )
		//		�@�~���Ϗ[�U�ʁi���j�����x����(g)��(�L��������100)�~����
		//
		//		�@�������Ƃ́A�E�ے����t�H���Ɛ����H�����w��
		//       ���e�����}�X�^�̊Y���H����VALUE2��"1"��"2"�̍H��
		//		�@�@�������v�P��
		//		�@�@�@�z���~�P����(������100)�̌v
		//		�@�@�������v�d��
		//		�@�@�@�z���̌v
		//
		//		�@�������Ƃ́A�����H�����w��
		//       ���e�����}�X�^�̊Y���H����VALUE2��"3"�̍H��
		//		�@�@�������v�P��
		//		�@�@�@�z���~�P����(������100)�̌v
		//		�@�@�������v�d��
		//		�@�@�@�z���̌v
		//
		// �y�v�Z�����@DB�擾���ځz�[�U�ʐ���(��)	douZyutenSui
		// �y�v�Z�����@DB�擾���ځz�[�U�ʖ���(��)	douZyutenYu
		// �y�v�Z�����@�������ځ@�z���Ϗ[�U��(��)	douHeikinZyuten
		// �y�v�Z�����@�������ځ@�z�L������(��)	douYukoBudomari
		// �y�v�Z�����@�������ځ@�z����			douIrisu
		// �y�v�Z�����@�v�Z���ځ@�z���x����(��)		douLevelRyo
		//
		//+--------------------------------------------+

		double ret = -1;

		try{

			//�v�Z�����̎��W

			//����
			douIrisu = toDouble(reqData.getFieldVale("kihon", "rec", "irisu"), -1);

			//�������v�P���E�������v�d�ʁE�������v�P���E�������v�d�ʂ̏W�v
			calcuGenryohiKesu_Cyoumi_Syuke(
					reqData
					, seq_shisaku);

			//�����̃`�F�b�N
			if (
					douZyutenSui > -1	//�H10.22	�n�j
				&&	douZyutenYu > -1	//�H12.3		�n�j
				&&	douHeikinZyuten > -1//�H52.825	�n�j
				&&	douLevelRyo > -1	//�H1000.0	�n�j
				&&	douYukoBudomari > -1//�H83.25	�n�j
				&&	douIrisu > -1		//�H10.0		�n�j
				&&	gokeTanka_sui > -1	//�H10923.546660882188	�n�j
				&&	gokeJyuryo_sui > -1	//�H107.25	�n�j
				&&	gokeTanka_yu > -1	//�H461.12	�n�j
				&&	gokeJyuryo_yu > -1	//�H1.5399999999999998	�n�j

			){

				//�v�Z
				double a = 0;
				double b = 0;
				if (gokeTanka_sui > 0
				&& gokeJyuryo_sui > 0
				&& douZyutenSui > 0){

					a = (gokeTanka_sui / gokeJyuryo_sui * douZyutenSui / 1000);
					//�H1.040919784374974
				}
				if (gokeTanka_yu > 0
				&& gokeJyuryo_yu > 0
				&& douZyutenYu > 0){

					b = (gokeTanka_yu / gokeJyuryo_yu * douZyutenYu / 1000);
					//�H	3.682971428571429
				}
				ret = (a + b) * douHeikinZyuten / douLevelRyo / (douYukoBudomari / 100) * douIrisu;
//				ret = ((gokeTanka_sui / gokeJyuryo_sui * douZyutenSui / 1000)
//				+ (gokeTanka_yu / gokeJyuryo_yu * douZyutenYu / 1000))
//				* douHeikinZyuten / douLevelRyo / (douYukoBudomari / 100) * douIrisu;
					//�H2.997472111998723
			}
//			else{
//				ret = 0;
//			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@������i�~�j/�P�[�X(�����t�p�^�[��)�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}
		return ret;

	}
	/**
	 * ���v�P���C���v�d�ʂ��W�v����B
	 * @param reqData			�F���N�G�X�g�f�[�^
	 * @param listGenryo		�FDB�������
	 * @param seq_shisaku		�F����SEQ
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuGenryohiKesu_Cyoumi_Syuke(

			RequestResponsKindBean reqData
			, String seq_shisaku
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//
		//		�@�������Ƃ́A�E�ے����t�H���Ɛ����H�����w��
		//       ���e�����}�X�^�̊Y���H����VALUE2��"1"��"2"�̍H��
		//		�@�@�������v�P��
		//		�@�@�@�z���~�P����(������100)�̌v
		//		�@�@�������v�d��
		//		�@�@�@�z���̌v
		//
		//		�@�������Ƃ́A�����H�����w��
		//       ���e�����}�X�^�̊Y���H����VALUE2��"3"�̍H��
		//		�@�@�������v�P��
		//		�@�@�@�z���~�P����(������100)�̌v
		//		�@�@�������v�d��
		//		�@�@�@�z���̌v
		//
		// �y�v�Z�����@DB�擾���ځz�z��(�s)		douHaigo
		// �y�v�Z�����@�������ځ@�z�P��(�~/�s)	douTanka_gen
		// �y�v�Z�����@�������ځ@�z����(��)		douBudomari_Gen
		//
		//+--------------------------------------------+

		Object[] items = null;

		//�������v�P��
		gokeTanka_sui = 0;
		//�������v�d��
		gokeJyuryo_sui = 0;
		//�������v�P��
		gokeTanka_yu = 0;
		//�������v�d��
		gokeJyuryo_yu = 0;

		try{

			//����DB������

			for (int i =0; i < reqData.getCntRow("genryo"); i++){

				int ix = seachGenryoIndex(
						seq_shisaku
						, reqData.getFieldVale("genryo", i, "cd_kotei")
						, reqData.getFieldVale("genryo", i, "seq_kotei")
						);

				items = (Object[]) listGenryo.get(ix);

				//�W�v

				//�z����
				douHaigo = toDouble(items[8], -1);
				//�P��
				douTanka_gen = toDouble(reqData.getFieldVale("genryo", i, "tanka"), -1);
				//������
				douBudomari_Gen = toDouble(reqData.getFieldVale("genryo", i, "budomari"), -1);

				if (toInteger(items[11]) == 1 || toInteger(items[11]) == 2){
					//�����W�v


					if (douHaigo > 0){

						//���v�P��
						if (douBudomari_Gen > 0){
							gokeTanka_sui += douHaigo * douTanka_gen / (douBudomari_Gen / 100);

						}else{

						}
						//���v�d��
						gokeJyuryo_sui += douHaigo;

					}

				}else{
					//�����W�v

					if (douHaigo > 0){


						//���v�P��
						if (douBudomari_Gen > 0){
							gokeTanka_yu += douHaigo * douTanka_gen / (douBudomari_Gen / 100);

						}else{

						}
						//���v�d��
						gokeJyuryo_yu += douHaigo;

					}

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@������i�~�j/�P�[�X(�����t�p�^�[��)�A���v�P���C���v�d�ʂ̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * ��d���Z�ʁi���j�̌v�Z
	 * @param yory	�F�e��
	 * @param irisu	�F����
	 * @param irisu	�F��d
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuHizyukasanryo(

			double yory
			, double irisu
			, double hizyu
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+ �v�Z��
		//+
		//+ 2009/12/?? KG�����킹��@�e�ʁ~�����~��d
		//+ 2009/12/17�@�Z�o���@�𼻸����ɍ��킹��@�i�e�ʁ~������1000�j�~��d
		//+ ���Ϗ[�U�ʁ~��d
		//+
		//+ �y�v�Z�����@DB�擾���ځz�e��
		//+ �y�v�Z�����@�������ځ@�z���萔
		//+ �y�v�Z�����@DB�擾���ځz��d
		//+--------------------------------------------+
		douYouryo = yory;
		douIrisu = irisu;
		douHizyu = hizyu;

		//�y�@�@�@�@�@�v�Z���ځ@�z��d���Z��(��)
		douHizykasanRyo = -1;

		try{
			if (douHeikinZyuten > -1
			&&	douHizyu > -1
					){
				//�v�Z
//				douHizykasanRyo = (douYouryo * douIrisu ) * douHizyu;
//				douHizykasanRyo = (douYouryo * douIrisu / 1000 ) * douHizyu;
				douHizykasanRyo = douHeikinZyuten * douHizyu;


			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@���x���ʂ̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * ���x����(��)�̌v�Z
	 * @param yory	�F�e��
	 * @param irisu	�F����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuReberuryo(

			double yory
			, double irisu
	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+ �v�Z��
		//+
		//+ �y2009/11/27�@�ۑ�Ǘ��F119�@�ύX�z�e�ʁ~����
		//+ �e�ʁ~������1000
		//+
		//+ �y�v�Z�����@DB�擾���ځz�e��
		//+ �y�v�Z�����@�������ځ@�z���萔
		//+--------------------------------------------+
		douYouryo = yory;
		douIrisu = irisu;

		//�y�v�Z�����@�v�Z���ځ@�z���x����(��)
		douLevelRyo = -1;

		try{
			if (douYouryo > -1
			&&	douIrisu > -1
			){
//				douLevelRyo = douYouryo * douIrisu;
				douLevelRyo = douYouryo * douIrisu / 1000;

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@���x���ʂ̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * ���ދ��z���v�Z����
	 * @param reqData		�F���N�G�X�g�f�[�^
	 * @param resData		�F�v�Z���ʁi���X�|���X�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuKingakuShizai(

			RequestResponsKindBean reqData
			, RequestResponsKindBean resData
			, List<?> listShisaku
			, List<?> listGenryo

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+ �v�Z��
		//+
		//+ �P�����i�����i���j��100�j�~�g�p��/���
		//+
		//+ �y�v�Z�����@�������ځ@�z�P��(�~)		����
		//+ �y�v�Z�����@�������ځ@�z����(��)		����
		//+ �y�v�Z�����@�������ځ@�z�g�p��/��� 	����
		//+--------------------------------------------+
		douTanka_Shizai = -1;
		douBudomari_Shizai = -1;
		douShiyouryo = -1;

		double douResult = 0;
		int i = -1;
		//���z���v
		douKingakuKei_Shizai = 0;
		String kigo = "";
		int keta = -1;

		try{
			reqData.getCntRow("shizai");

		}catch(Exception e){
			return;
		}

		try{

			for (i = 0; i < reqData.getCntRow("shizai"); i++){

				//�H��L��/���ރR�[�h����������
				Object[] items = seachKojyoKigo(
						reqData.getFieldVale("shizai", i, "cd_kaisya")
						, reqData.getFieldVale("shizai", i, "cd_kojyo")
						);

				kigo = "";
				keta = -1;

				if(items == null){

				}else{
					//�H��L��
					kigo = toString(items[0], "");
					//����/���ރR�[�h����
					keta = toInteger(items[1], -1);

				}
				if (keta > -1){

				}else{
					keta = 6;
				}

				//�v�Z�����̎��W
				//�g�p��/���
				 douShiyouryo = toDouble(reqData.getFieldVale("shizai", i, "shiyouryo"), -1);
				//�P��
				 douTanka_Shizai = toDouble(reqData.getFieldVale("shizai", i, "tanka"), -1);
				//������
				 douBudomari_Shizai = toDouble(reqData.getFieldVale("shizai", i, "budomari"), -1);

				 douResult = -1;

				if(douShiyouryo > -1
				&& douTanka_Shizai > -1
				&& douBudomari_Shizai > -1
				){
					//�v�Z
					if(douBudomari_Shizai > 0){
						douResult = douTanka_Shizai / (douBudomari_Shizai / 100) * douShiyouryo;

					}else{
						douResult = 0;

					}

					//���z���v�@���Z
					douKingakuKei_Shizai += douResult;

				}

				//���R�[�h��������RESULT�ǉ�
				resData.addFieldVale("shizai", "rec" + toString(i), "flg_return", "true");
				resData.addFieldVale("shizai", "rec" + toString(i), "msg_error", "");
				resData.addFieldVale("shizai", "rec" + toString(i), "nm_class", "");
				resData.addFieldVale("shizai", "rec" + toString(i), "no_errmsg", "");
				resData.addFieldVale("shizai", "rec" + toString(i), "cd_error", "");
				resData.addFieldVale("shizai", "rec" + toString(i), "msg_system", "");
				// ----------------------------------------------------------------------+
				// ����SEQ
				// ----------------------------------------------------------------------+
				resData.addFieldVale("shizai", "rec" + toString(i), "seq_shizai"
						, reqData.getFieldVale("shizai", i, "seq_shizai"));
				// ----------------------------------------------------------------------+
				// ���CD
				// ----------------------------------------------------------------------+
				resData.addFieldVale("shizai", "rec" + toString(i), "cd_kaisya"
						, reqData.getFieldVale("shizai", i, "cd_kaisya"));
				// ----------------------------------------------------------------------+
				// �H��CD
				// ----------------------------------------------------------------------+
				resData.addFieldVale("shizai", "rec" + toString(i), "cd_kojyo"
						, reqData.getFieldVale("shizai", i, "cd_kojyo"));
				// ----------------------------------------------------------------------+
				// �H��L��
				// ----------------------------------------------------------------------+
				resData.addFieldVale("shizai", "rec" + toString(i), "kigo_kojyo"
						, kigo);
				// ----------------------------------------------------------------------+
				// ����CD
				// ----------------------------------------------------------------------+
				if(toDouble(reqData.getFieldVale("shizai", i, "cd_shizai"), -1) > -1){
					resData.addFieldVale(
							"shizai"
							, "rec" + toString(i)
							, "cd_shizai"
							, getRight("0000000000" + reqData.getFieldVale("shizai", i, "cd_shizai"), keta)
							);

				}else{
					resData.addFieldVale("shizai", "rec" + toString(i), "cd_shizai"
							, "");

				}
				// ----------------------------------------------------------------------+
				// ���ޖ�
				// ----------------------------------------------------------------------+
				resData.addFieldVale("shizai", "rec" + toString(i), "nm_shizai"
						, reqData.getFieldVale("shizai", i, "nm_shizai"));
				// ----------------------------------------------------------------------+
				// �P��
				// ----------------------------------------------------------------------+
				if (douTanka_Shizai > -1){
					resData.addFieldVale(
							"shizai"
							, "rec" + toString(i)
							, "tanka"
							, toString(douTanka_Shizai, 2, 2, true, ""));

				}else{
					resData.addFieldVale("shizai", "rec" + toString(i), "tanka"
							, "");

				}
				// ----------------------------------------------------------------------+
				// �����i���j
				// ----------------------------------------------------------------------+
				if (douBudomari_Shizai > -1){
					resData.addFieldVale(
							"shizai"
							, "rec" + toString(i)
							, "budomari"
							, toString(douBudomari_Shizai, 2, 2, true, ""));

				}else{
					resData.addFieldVale("shizai", "rec" + toString(i), "budomari"
							, "");

				}
				// ----------------------------------------------------------------------+
				// �g�p��/���
				// ----------------------------------------------------------------------+
				if (douShiyouryo > -1){
					resData.addFieldVale(
							"shizai"
							, "rec" + toString(i)
							, "shiyouryo"
							, toString(douShiyouryo, 6, 2, true, ""));

				}else{
					resData.addFieldVale("shizai", "rec" + toString(i), "shiyouryo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// ���z
				// ----------------------------------------------------------------------+
				if (douResult > -1){
					resData.addFieldVale(
							"shizai"
							, "rec" + toString(i)
							, "kei_kingaku"
							, toString(douResult, 2, 2, true, ""));

				}else{
					resData.addFieldVale("shizai", "rec" + toString(i), "kei_kingaku"
							, "");

				}

				// ----------------------------------------------------------------------+
				// �o�^��ID
				// ----------------------------------------------------------------------+
				//���N�G�X�g�Ɏ��Z�������݂��邩�̊m�F
				int index = reqData.getItemNo("shizai", i, "id_toroku");

				if( index > -1 ){

					resData.addFieldVale("shizai", "rec" + toString(i), "id_toroku"
							, reqData.getFieldVale("shizai", i, "id_toroku"));

				}
				else{


				}

				// ----------------------------------------------------------------------+
				// �o�^���t
				// ----------------------------------------------------------------------+
				//���N�G�X�g�Ɏ��Z�������݂��邩�̊m�F
				int index2 = reqData.getItemNo("shizai", i, "dt_toroku");

				if( index2 > -1 ){

					resData.addFieldVale("shizai", "rec" + toString(i), "dt_toroku"
							, reqData.getFieldVale("shizai", i, "dt_toroku"));

				}
				else{


				}

//				try{
//					// ----------------------------------------------------------------------+
//					// �o�^��ID
//					// ----------------------------------------------------------------------+
//					resData.addFieldVale("shizai", "rec" + toString(i), "id_toroku"
//							, reqData.getFieldVale("shizai", i, "id_toroku"));
//					// ----------------------------------------------------------------------+
//					// �o�^���t
//					// ----------------------------------------------------------------------+
//					resData.addFieldVale("shizai", "rec" + toString(i), "dt_toroku"
//							, reqData.getFieldVale("shizai", i, "dt_toroku"));
//				}catch(Exception e){
//
//				}



			}

			// ----------------------------------------------------------------------+
			// ���z���v
			// ----------------------------------------------------------------------+
			if (douKingakuKei_Shizai > -1){
				resData.addFieldVale(
						"kihon"
						, "rec"
						, "goke_shizai"
						, toString(douKingakuKei_Shizai, 2, 2, true, ""));

			}else{
				resData.addFieldVale("kihon", "rec", "goke_shizai"
						, "");

			}
			// ----------------------------------------------------------------------+
			// ����Max�s��
			// ----------------------------------------------------------------------+
			resData.addFieldVale("kihon", "rec", "cnt_shizai"
					, toString(
							i
							+ toInteger(ConstManager.getConstValue(Category.�ݒ�, "GENK_SHIZAI_LIST_ADD_CNT")))
							);


		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@���ދ��z�̎Z�o�Ɏ��s���܂����B");

		}finally{

		}

	}
	/**
	 * �������z���v�Z����
	 * @param reqData		�F���N�G�X�g�f�[�^
	 * @param resData		�F�v�Z���ʁi���X�|���X�j
	 * @param listShisaku	�F�������ʁi������j
	 * @param listGenryo	�F�������ʁi�������j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void calcuKingakuGenryo(

			RequestResponsKindBean reqData
			, RequestResponsKindBean resData

	)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+ �v�Z��
		//+
		//+ �P�����i������100�j�~�z��
		//+
		//+ �y�v�Z�����@�������ځ@�z�P��(�~/�s)�@����
		//+ �y�v�Z�����@�������ځ@�z����(��)�@�@����
		//+ �y�v�Z�����@DB�擾���ځz�z��(�s)
		//+--------------------------------------------+
		douHaigo = -1;
		douTanka_gen = -1;
		douBudomari_Gen = -1;

		int keta_shosu = 0;
		double douResult = 0;
		Object[] items = null;

		//�H��CD��r�o�b�t�@
		int intCd_kotei = -1;
		//rec�̓Y����
		int cntRec = 0;
		//�H���O���[�v�i1�F�����t�p�^�[���@2�F���̑������t�ȊO�o�^�[���@3�F�v�Z�s�\�j
		flgKoteGroup = -1;

		//�z���H���v
		ArrayList<Double> lstGoukeJyuryo_kote = null;
		//�z�����v
		double GoukeJyuryo_total = 0;

		int ii = 0;
		int i = 0;
		int iy = 0;

		try{

			//�����@�z�������w��@�ޔ�
			items = (Object[]) listShisaku.get(0);
			keta_shosu = toInteger(items[4], 0);

			for (ii = 0; ii < reqData.getCntRow("keisan"); ii++){
				//�T���v�����̏���

				//���X�|���X��{�ikihon�j����
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "flg_return", "true");
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "msg_error", "");
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "nm_class", "");
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "no_errmsg", "");
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "cd_error", "");
				resData.addFieldVale("shisaku" + toString(ii), "kihon", "msg_system", "");

				//���X�|���Xͯ�ް�iheder�j����
				//DB�T���v�����擾
				iy = seachSanpuruIndex(reqData.getFieldVale("keisan", ii, "seq_shisaku"));
				items = (Object[]) listSanpuru.get(iy);

				resData.addFieldVale("shisaku" + toString(ii)
						, "heder", "seq_shisaku", toString(items[3],"�@"));
				resData.addFieldVale("shisaku" + toString(ii)
						, "heder", "shisakuDate", toString(items[8],"�@"));
				resData.addFieldVale("shisaku" + toString(ii)
						, "heder", "nm_sample", toString(items[9],"�@"));

				//�T���v�����̏�����
				GoukeJyuryo_total = 0;
				cntRec = -1;
				intCd_kotei = -1;
				removeList(lstGoukeJyuryo_kote);
				lstGoukeJyuryo_kote = new ArrayList<Double>();

				for (i =0; i < reqData.getCntRow("genryo"); i++){
					//������(haigo�{�sSEQ)�̏���

					int ix = seachGenryoIndex(
							  reqData.getFieldVale("keisan", ii, "seq_shisaku")
							, reqData.getFieldVale("genryo", i, "cd_kotei")
							, reqData.getFieldVale("genryo", i, "seq_kotei")
							);

					items = (Object[]) listGenryo.get(ix);

					//�H���O���[�v�̓���i1�F�����t�p�^�[���@2�F���̑������t�ȊO�o�^�[���@3�F�v�Z�s�\�j
					if (flgKoteGroup == -1){
						flgKoteGroup = toInteger(items[10]);

					}else if(flgKoteGroup == toInteger(items[10])){

					}else{
						flgKoteGroup = 3;

					}

					//�H���\���s(�󔒍s)�̒ǉ�

					if (intCd_kotei == toInteger(items[9])){

					}else{
						cntRec += 1;
						//�z��
						resData.addFieldVale("shisaku" + toString(ii), "haigo" + toString(cntRec)
								, "haigo", "");
						//���z
						resData.addFieldVale("shisaku" + toString(ii), "haigo" + toString(cntRec)
								, "kingaku", "");
						//�H��CD�̑ޔ�
						intCd_kotei = toInteger(items[9]);

						//�H���v�̏�����
						lstGoukeJyuryo_kote.add(toDouble(0));

						//�ύX�A���@�H���i�󔒁j�@2009/11/12�ǉ�
						if(ii == 0){
							resData.addFieldVale("henkou", "rec" + toString(cntRec)
									, "henkourenraku", "");

						}

					}

					//�z����
					douHaigo = toDouble(items[8], -1);
					//�P��
					douTanka_gen = toDouble(reqData.getFieldVale("genryo", i, "tanka"), -1);
					//������
					douBudomari_Gen = toDouble(reqData.getFieldVale("genryo", i, "budomari"), -1);

					douResult = -1;

					if(douHaigo > -1
					&& douTanka_gen > -1
					&& douBudomari_Gen > -1
					){
						//�v�Z�i���z�j
						if(douBudomari_Gen > 0){
							douResult = douTanka_gen / (douBudomari_Gen / 100) * douHaigo;

						}else{
							douResult = 0;

						}
					}


					//�z���@�H���v
					lstGoukeJyuryo_kote.set(lstGoukeJyuryo_kote.size()-1,
					lstGoukeJyuryo_kote.get(lstGoukeJyuryo_kote.size()-1) + toDouble(items[8], 0));
					//�z���@���v
					GoukeJyuryo_total += toDouble(items[8], 0);

					//�v�Z���ʃf�[�^�i���X�|���X�j����
					cntRec += 1;
					// ----------------------------------------------------------------------+
					// �z��
					// ----------------------------------------------------------------------+
					if (douHaigo > -1){
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "haigo"
								, toString(douHaigo, keta_shosu, 2, true, ""));

					}else{
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "haigo"
								, "");

					}
					// ----------------------------------------------------------------------+
					// ���z
					// ----------------------------------------------------------------------+
					if (douResult > -1){
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "kingaku"
								, toString(douResult, 2, 2, true, ""));

					}else{
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "kingaku"
								, "");

					}

					//�y�V�T�N�C�b�NH24�N�x�Ή��zNo46 2012/04/20 ADD Start
					//�����H��
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "sort"
							, toString(items[7]));
					//�y�V�T�N�C�b�NH24�N�x�Ή��zNo46 2012/04/20 ADD End

					//�ύX�A���@2009/11/12�ǉ�
					if(ii == 0){
						//�}�X�^�̒l�Ɣ�r����B
						if (douTanka_gen != toDouble(items[12], -1)
							||	douBudomari_Gen != toDouble(items[13], -1)){
							resData.addFieldVale("henkou", "rec" + toString(cntRec)
									, "henkourenraku", "��");

						}else{
							resData.addFieldVale("henkou", "rec" + toString(cntRec)
									, "henkourenraku", "");

						}

					}


				}

				//�H���v�s�ǉ�

				for (int ix = 0 ; ix < lstGoukeJyuryo_kote.size() ; ix++){

					cntRec += 1;

					// ----------------------------------------------------------------------+
					// �H���v�s�@�z��
					// ----------------------------------------------------------------------+
					if (lstGoukeJyuryo_kote.get(ix) > -1){
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "haigo"
								, toString(lstGoukeJyuryo_kote.get(ix), keta_shosu, 2, true, ""));

					}else{
						resData.addFieldVale(
								"shisaku" + toString(ii)
								, "haigo" + toString(cntRec)
								, "haigo"
								, "");

					}
					// ----------------------------------------------------------------------+
					// �H���v�s�@���z
					// ----------------------------------------------------------------------+
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "kingaku"
							, "");

				}

				//���v�s�ǉ�

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// ���v�s�@�z��
				// ----------------------------------------------------------------------+
				if (GoukeJyuryo_total > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(GoukeJyuryo_total, keta_shosu, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// ���v�s�@���z
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");



				//DB�T���v�����擾
				iy = seachSanpuruIndex(reqData.getFieldVale("keisan", ii, "seq_shisaku"));
				items = (Object[]) listSanpuru.get(iy);

				//���v�d���d��

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// ���v�d���d�ʁ@�z��
				// ----------------------------------------------------------------------+
				if (toDouble(items[4], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[4], -1), 4, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// ���v�d���d�ʁ@���z
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");

				//���_

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// ���_�@�z��
				// ----------------------------------------------------------------------+
				if (toDouble(items[12], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[12], -1), 2, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// ���_�@���z
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");

				//�H��

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// �H���@�z��
				// ----------------------------------------------------------------------+
				if (toDouble(items[13], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[13], -1), 2, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// �H���@���z
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");

				//�������_�x

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// �������_�x�@�z��
				// ----------------------------------------------------------------------+
				if (toDouble(items[14], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[14], -1), 2, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// �������_�x�@���z
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");

				//�������H��

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// �������H���@�z��
				// ----------------------------------------------------------------------+
				if (toDouble(items[15], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[15], -1), 2, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// �������H���@���z
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");

				//�������|�_

				cntRec += 1;

				// ----------------------------------------------------------------------+
				// �������|�_�@�z��
				// ----------------------------------------------------------------------+
				if (toDouble(items[16], -1) > -1){
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, toString(toDouble(items[16], -1), 2, 2, true, ""));

				}else{
					resData.addFieldVale(
							"shisaku" + toString(ii)
							, "haigo" + toString(cntRec)
							, "haigo"
							, "");

				}
				// ----------------------------------------------------------------------+
				// �������|�_�@���z
				// ----------------------------------------------------------------------+
				resData.addFieldVale(
						"shisaku" + toString(ii)
						, "haigo" + toString(cntRec)
						, "kingaku"
						, "");


			}
			//���X�|���X��{�̒ǉ�
			// ----------------------------------------------------------------------+
			//�@�������i�s�j
			// ----------------------------------------------------------------------+
			resData.addFieldVale("kihon", "rec", "cnt_genryo"
					, toString(cntRec + 1));

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@�������z�̎Z�o�Ɏ��s���܂����B");

		}finally{
			//���[�J���ϐ��̊J��
			items = null;
			removeList(lstGoukeJyuryo_kote);

		}

	}
	/**
	 * �T���v�����ilistSanpuru�j���A�����ɍ��v����C���f�b�N�X�����o����
	 * @param seq_shisaku	�F����SEQ
	 * @param listGenryo	�F�������ʁi�T���v�����j
	 * @return int	�F�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachSanpuruIndex(

			  String seq_shisaku
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;

		try{

			for (int i = 0; i < listSanpuru.size(); i++){

				Object[] items = (Object[]) listSanpuru.get(i);

				//�z�����}�b�`���O
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(seq_shisaku) == toInteger(items[3])
						){
					//�Y�������ꍇ�A�I��
					ret = i;
					break;

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * �������ilistGenryo�j���A�����ɍ��v����C���f�b�N�X�����o����
	 * @param seq_shisaku	�F����SEQ
	 * @param cd_kotei		�F�H��CD
	 * @param seq_kotei		�F�H��SEQ
	 * @param listGenryo	�F�������ʁi�������j
	 * @return int	�F�C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int seachGenryoIndex(

			  String seq_shisaku
			, String cd_kotei
			, String seq_kotei
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int ret = -1;

		try{

			for (int i = 0; i < listGenryo.size(); i++){

				Object[] items = (Object[]) listGenryo.get(i);

				//�z�����}�b�`���O
				if (
						cd_shisaku_syainID.equals(items[0])
					&&	cd_shisaku_nen == toInteger(items[1])
					&&	cd_shisaku_oi == toInteger(items[2])
					&&	toInteger(seq_shisaku) == toInteger(items[3])
					&&	toInteger(cd_kotei) == toInteger(items[4])
					&&	toInteger(seq_kotei) == toInteger(items[5])
						){
					//�Y�������ꍇ�A�I��
					ret = i;
					break;

				}

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "");

		}finally{

		}
		return ret;

	}
	/**
	 * ��]�����P�ʂ��擾����
	 * @param seq_shisaku	�F�P�ʃR�[�h
	 * @return string :��]�����P��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String seachNmTaniKibogenka(

			    String cdTani
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQL�o�b�t�@
		StringBuffer strSQL = new StringBuffer();
		//���ʃo�b�t�@
		List<?> listResult = null;
		//��]�����P��
		String ret = "";

		try{

			//DB�R�l�N�V����
			createSearchDB();

			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("     nm_literal ");
			strSQL.append(" ,   cd_literal ");
			strSQL.append(" FROM ");
			strSQL.append("     ma_literal ");
			strSQL.append(" WHERE ");
			strSQL.append("     cd_category = 'K_tani_genka' ");
			strSQL.append(" AND cd_literal  = '" + toString(cdTani) + "' ");
			//DB����
			listResult = this.searchDB.dbSearch(strSQL.toString());

			try{
				//��]�����P��
				if ( listResult.size() >= 0 ) {

					for (int i = 0; i < listResult.size(); i++) {

						Object[] items = (Object[]) listResult.get(i);

						ret = toString(items[0]);

					}

				}

			}catch(Exception e){

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "��]�����A�P�ʂ̎擾 �Ɏ��s���܂����B\nSQL:"
					+ strSQL.toString());

		}finally{
			//DB�R�l�N�V�����J��
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}
			//���[�J���ϐ��̊J��
			strSQL = null;
			removeList(listResult);

		}
		return ret;

	}
	/**
	 * ��ƍH�����擾����
	 * @param reqData	�F���N�G�X�g�f�[�^
	 * @return String	�F��������
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private String seachSeizoKote(

		    RequestResponsKindBean reqData
		)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQL�o�b�t�@
		StringBuffer strSQL = new StringBuffer();
		//���ʃo�b�t�@
		List<?> listResult = null;
		String strWhere = "null";
		//��ƍH��
		String ret = "";


		try{

			//DB�R�l�N�V����
			createSearchDB();

			//�������W
			for (int i = 0 ; i < reqData.getCntRow("keisan"); i++){
				strWhere += "," + toString(reqData.getFieldVale("keisan", i, "seq_shisaku"));
			}

			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  T133.chuijiko ");
			strSQL.append(" ,T131.cd_shain ");
			strSQL.append(" FROM ");
			strSQL.append("    (SELECT ");
			strSQL.append("      cd_shain ");
			strSQL.append("     ,nen ");
			strSQL.append("     ,no_oi ");
			strSQL.append("     ,MAX(no_chui) AS no_chui ");
			strSQL.append("     FROM ");
			strSQL.append("     tr_shisaku ");
			strSQL.append("     WHERE ");
			strSQL.append("         cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append("     AND nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append("     AND no_oi    = " + toString(cd_shisaku_oi) + " ");
			strSQL.append("     AND seq_shisaku IN (" + strWhere + ") ");
			strSQL.append("     GROUP BY ");
			strSQL.append("      cd_shain ");
			strSQL.append("     ,nen ");
			strSQL.append("     ,no_oi ");
			strSQL.append("     ) AS T131 ");
			strSQL.append(" LEFT JOIN tr_cyuui AS T133 ");
			strSQL.append(" ON  T131.cd_shain = T133.cd_shain ");
			strSQL.append(" AND T131.nen      = T133.nen ");
			strSQL.append(" AND T131.no_oi    = T133.no_oi ");
			strSQL.append(" AND T131.no_chui  = T133.no_chui ");
			//DB����
			listResult = this.searchDB.dbSearch(strSQL.toString());

			try{
				//��ƍH���擾
				if ( listResult.size() >= 0 ) {

					for (int i = 0; i < listResult.size(); i++) {

						Object[] items = (Object[])listResult.get(i);

						ret = toString(items[0]);

					}

				}

			}catch(Exception e){

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "��ƍH���̎擾 �Ɏ��s���܂����B\nSQL:"
					+ strSQL.toString());

		}finally{
			//DB�R�l�N�V�����J��
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}
			//���[�J���ϐ��̊J��
			strSQL = null;
			removeList(listResult);

		}
		return ret;

	}
	/**
	 * �H��L�����擾����
	 * @param seq_shisaku	�F����SEQ
	 * @param listGenryo	�F�������ʁi�������j
	 * @return String		�F�H��L��/�R�[�h����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private Object[] seachKojyoKigo(

			    String cdKaisya
			  , String cdKojyo
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQL�o�b�t�@
		StringBuffer strSQL = new StringBuffer();
		//���ʃo�b�t�@
		List<?> listResult = null;

		Object[] ret = null;

		try{

			//DB�R�l�N�V����
			createSearchDB();

			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  M302.nm_literal ");
			strSQL.append(" ,M104.keta_genryo ");
			strSQL.append(" FROM ");
			strSQL.append("           ma_busho   AS M104 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kigo_kojyo'  ");
			strSQL.append(" AND M302.cd_literal = CONVERT(varchar, M104.cd_kaisha) + '-' + CONVERT(varchar, M104.cd_busho) ");
			strSQL.append(" WHERE ");
			strSQL.append("     M104.cd_kaisha =" + toString(cdKaisya,"null") + " ");
			strSQL.append(" AND M104.cd_busho  =" + toString(cdKojyo,"null") + " ");
			//DB����
			listResult = this.searchDB.dbSearch(strSQL.toString());

			try{
				//�H��L���擾
				if ( listResult.size() >= 0 ) {

					for (int i = 0; i < listResult.size(); i++) {

						ret = (Object[]) listResult.get(i);

					}

				}

			}catch(Exception e){

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�H��L���̎擾 �Ɏ��s���܂����B\nSQL:"
					+ strSQL.toString());

		}finally{
			//DB�R�l�N�V�����J��
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}
			//���[�J���ϐ��̊J��
			strSQL = null;
			removeList(listResult);

		}
		return ret;

	}
	/**
	 * DB���擾����v�Z�����̌���
	 * @param listShisaku	�F�������ʁi������j
	 * @param listSanpuru	�F�������ʁi����ُ��j
	 * @param listGenryo	�F�������ʁi�������j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void getDBKomoku()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//+�@memo�@--------------------------------------+
		//+ DB���擾����v�Z����
		//+ ���얈
		//+ �y�v�Z�����@DB�擾���ځz�e��
		//+ ����ٖ�
		//+ �y�v�Z�����@DB�擾���ځz���v�d��d��(�s)
		//+ �y�v�Z�����@DB�擾���ځz�[�U�ʐ���(��)
		//+ �y�v�Z�����@DB�擾���ځz�[�U�ʖ���(��)
		//+ �y�v�Z�����@DB�擾���ځz��d
		//+ ������
		//+ �y�v�Z�����@DB�擾���ځz�z��(�s)
		//+--------------------------------------------+

		//SQL�o�b�t�@
		StringBuffer strSQL = new StringBuffer();

		try{

			//DB�R�l�N�V����
			createSearchDB();

			//�����񌟍�

			//SQL����
//			strSQL.append(" SELECT ");
//			strSQL.append("  cd_shain ");	//0
//			strSQL.append(" ,nen ");		//1
//			strSQL.append(" ,no_oi ");		//2
//			strSQL.append(" ,yoryo ");		//3
//			strSQL.append(" ,keta_shosu ");	//4
//			strSQL.append(" FROM ");
//			strSQL.append("     tr_shisakuhin ");
//			strSQL.append(" WHERE ");
//			strSQL.append("     cd_shain = " + toString(cd_shisaku_syainID) + " ");
//			strSQL.append(" AND nen      = " + toString(cd_shisaku_nen) + " ");
//			strSQL.append(" AND no_oi    = " + toString(cd_shisaku_oi) + " ");

			//�yQP@00342�z
			strSQL.append(" SELECT ");
			strSQL.append(" 	 T310.cd_shain AS cd_shain "); 		//0
			strSQL.append(" 	,T310.nen AS nen "); 						//1
			strSQL.append(" 	,T310.no_oi AS no_oi "); 					//2
			strSQL.append(" 	,T310.yoryo AS yoryo "); 				//3
			strSQL.append(" 	,T110.keta_shosu AS keta_shosu "); 	//4
			strSQL.append(" FROM ");
			strSQL.append(" 	tr_shisakuhin AS T110 ");
			strSQL.append(" 	LEFT JOIN tr_shisan_shisakuhin AS T310 ");
			strSQL.append(" 	ON T110.cd_shain = T310.cd_shain ");
			strSQL.append(" 	AND T110.nen = T310.nen ");
			strSQL.append(" 	AND T110.no_oi = T310.no_oi ");
			strSQL.append(" WHERE ");
			strSQL.append("     T310.cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T310.nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T310.no_oi    = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND T310.no_eda    = " + toString(cd_shisaku_eda) + " ");

			//DB����
			listShisaku = this.searchDB.dbSearch(strSQL.toString());

			//����ُ�񌟍�

			strSQL = null;
			strSQL = new StringBuffer();

			//�y�V�T�N�C�b�NH24�Ή��zNo43 Start
			//�yQP@00342�z����
			if(cd_shisaku_eda == 0){
				//SQL����
				strSQL.append(" SELECT ");
				strSQL.append("  T131.cd_shain ");			//0
				strSQL.append(" ,T131.nen ");				//1
				strSQL.append(" ,T131.no_oi ");				//2
				strSQL.append(" ,T131.seq_shisaku ");		//3
				strSQL.append(" ,T131.juryo_shiagari_g ");	//4
				strSQL.append(" ,T141.zyusui ");			//5
				strSQL.append(" ,T141.zyuabura ");			//6
				strSQL.append(" ,T141.hiju ");				//7

				//�ۑ�Ǘ��䒠�@No116�@�F�@������t���o�^���ɕύX�@TT.Nishigawa START -----------------------------------
				//strSQL.append(" ,CONVERT(VARCHAR,T131.dt_shisaku,111) AS A ");	//8
				strSQL.append(" ,CONVERT(VARCHAR,T331.dt_toroku,111) AS A ");	//8
				//�ۑ�Ǘ��䒠�@No116�@�F�@������t���o�^���ɕύX�@TT.Nishigawa END     -----------------------------------

				strSQL.append(" ,T131.nm_sample ");			//9
				strSQL.append(" ,CONVERT(VARCHAR,T331.dt_shisan,111) AS B ");	//10
				strSQL.append(" ,T141.gokei ");				//11
				strSQL.append(" ,T131.ritu_sousan ");		//12
				strSQL.append(" ,T131.ritu_shokuen ");		//13
				strSQL.append(" ,T131.sando_suiso ");		//14
				strSQL.append(" ,T131.shokuen_suiso ");		//15
				strSQL.append(" ,T131.sakusan_suiso ");		//16
				strSQL.append(" FROM ");
				strSQL.append("           tr_shisaku AS T131 ");
				strSQL.append(" LEFT JOIN tr_genryo AS T141 ");
				strSQL.append(" ON  T131.cd_shain    = T141.cd_shain ");
				strSQL.append(" AND T131.nen         = T141.nen ");
				strSQL.append(" AND T131.no_oi       = T141.no_oi ");
				strSQL.append(" AND T131.seq_shisaku = T141.seq_shisaku ");
				strSQL.append(" LEFT JOIN tr_shisan_shisaku AS T331 ");
				strSQL.append(" ON  T131.cd_shain    = T331.cd_shain ");
				strSQL.append(" AND T131.nen         = T331.nen ");
				strSQL.append(" AND T131.no_oi       = T331.no_oi ");
				strSQL.append(" AND T131.seq_shisaku = T331.seq_shisaku ");

	//			strSQL.append(" WHERE ");
	//			strSQL.append("     T131.cd_shain = " + toString(cd_shisaku_syainID) + " ");
	//			strSQL.append(" AND T131.nen      = " + toString(cd_shisaku_nen) + " ");
	//			strSQL.append(" AND T131.no_oi    = " + toString(cd_shisaku_oi) + " ");
				//�yQP@00342�z
				strSQL.append(" WHERE ");
				strSQL.append("     T331.cd_shain = " + toString(cd_shisaku_syainID) + " ");
				strSQL.append(" AND T331.nen      = " + toString(cd_shisaku_nen) + " ");
				strSQL.append(" AND T331.no_oi    = " + toString(cd_shisaku_oi) + " ");
				strSQL.append(" AND T331.no_eda    = " + toString(cd_shisaku_eda) + " ");
			}
			//�}��
			else{
				//SQL����
				strSQL.append(" SELECT ");
				strSQL.append("  T131.cd_shain ");			//0
				strSQL.append(" ,T131.nen ");				//1
				strSQL.append(" ,T131.no_oi ");				//2
				strSQL.append(" ,T131.seq_shisaku ");		//3
				strSQL.append(" ,T131.juryo_shiagari_g ");	//4
				strSQL.append(" ,T141.zyusui ");			//5
				strSQL.append(" ,T141.zyuabura ");			//6
				strSQL.append(" ,T141.hiju ");				//7
				strSQL.append(" ,CONVERT(VARCHAR,T_moto.dt_toroku,111) AS A ");	//8
				strSQL.append(" ,T131.nm_sample ");			//9
				strSQL.append(" ,CONVERT(VARCHAR,T331.dt_shisan,111) AS B ");	//10
				strSQL.append(" ,T141.gokei ");				//11
				strSQL.append(" ,T131.ritu_sousan ");		//12
				strSQL.append(" ,T131.ritu_shokuen ");		//13
				strSQL.append(" ,T131.sando_suiso ");		//14
				strSQL.append(" ,T131.shokuen_suiso ");		//15
				strSQL.append(" ,T131.sakusan_suiso ");		//16
				strSQL.append(" FROM ");
				strSQL.append("           tr_shisaku AS T131 ");
				strSQL.append(" LEFT JOIN tr_genryo AS T141 ");
				strSQL.append(" ON  T131.cd_shain    = T141.cd_shain ");
				strSQL.append(" AND T131.nen         = T141.nen ");
				strSQL.append(" AND T131.no_oi       = T141.no_oi ");
				strSQL.append(" AND T131.seq_shisaku = T141.seq_shisaku ");
				strSQL.append(" LEFT JOIN tr_shisan_shisaku AS T331 ");
				strSQL.append(" ON  T131.cd_shain    = T331.cd_shain ");
				strSQL.append(" AND T131.nen         = T331.nen ");
				strSQL.append(" AND T131.no_oi       = T331.no_oi ");
				strSQL.append(" AND T131.seq_shisaku = T331.seq_shisaku ");
				strSQL.append(" LEFT join ( select ");
				strSQL.append(" 	seq_shisaku,");
				strSQL.append(" 	dt_toroku ");
				strSQL.append(" 	FROM ");
				strSQL.append(" 	tr_shisan_shisaku");
				strSQL.append(" 	WHERE ");
				strSQL.append(" 	cd_shain = " + toString(cd_shisaku_syainID) + " ");
				strSQL.append(" 	AND nen = " + toString(cd_shisaku_nen) + " ");
				strSQL.append(" 	AND no_oi = " + toString(cd_shisaku_oi) + " ");
				strSQL.append(" 	AND no_eda = 0 ");
				strSQL.append(" ) AS T_moto");
				strSQL.append(" ON T131.seq_shisaku = T_moto.seq_shisaku  ");
				strSQL.append(" WHERE ");
				strSQL.append("     T331.cd_shain = " + toString(cd_shisaku_syainID) + " ");
				strSQL.append(" AND T331.nen      = " + toString(cd_shisaku_nen) + " ");
				strSQL.append(" AND T331.no_oi    = " + toString(cd_shisaku_oi) + " ");
				strSQL.append(" AND T331.no_eda    = " + toString(cd_shisaku_eda) + " ");
			}
			//�y�V�T�N�C�b�NH24�Ή��zNo43 End


			//DB����
			listSanpuru = this.searchDB.dbSearch(strSQL.toString());

			//�������

			strSQL = null;
			strSQL = new StringBuffer();

			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  T120.cd_shain ");		//0
			strSQL.append(" ,T120.nen ");			//1
			strSQL.append(" ,T120.no_oi ");			//2
			strSQL.append(" ,T132.seq_shisaku ");	//3
			strSQL.append(" ,T120.cd_kotei ");		//4
			strSQL.append(" ,T120.seq_kotei ");		//5
			strSQL.append(" ,T120.sort_kotei ");	//6
			strSQL.append(" ,T120.sort_genryo ");	//7
			strSQL.append(" ,T132.quantity ");		//8
			strSQL.append(" ,T120.cd_kotei ");		//9
			strSQL.append(" ,M302.value1 ");		//10
			strSQL.append(" ,M302.value2 ");		//11
			strSQL.append(" ,T320.tanka_ma ");		//12
			strSQL.append(" ,T320.budomar_ma ");	//13
			strSQL.append(" FROM ");
			strSQL.append("           tr_haigo AS T120 ");
			strSQL.append(" LEFT JOIN tr_shisaku_list AS T132 ");
			strSQL.append(" ON  T120.cd_shain  = T132.cd_shain ");
			strSQL.append(" AND T120.nen       = T132.nen ");
			strSQL.append(" AND T120.no_oi     = T132.no_oi ");
			strSQL.append(" AND T120.cd_kotei  = T132.cd_kotei ");
			strSQL.append(" AND T120.seq_kotei = T132.seq_kotei ");

			strSQL.append(" LEFT JOIN tr_shisan_haigo AS T320 ");
			strSQL.append(" ON  T120.cd_shain  = T320.cd_shain ");
			strSQL.append(" AND T120.nen       = T320.nen ");
			strSQL.append(" AND T120.no_oi     = T320.no_oi ");
			strSQL.append(" AND T120.cd_kotei  = T320.cd_kotei ");
			strSQL.append(" AND T120.seq_kotei = T320.seq_kotei ");

			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  'K_kote'        = M302.cd_category ");
			strSQL.append(" AND T120.zoku_kotei = M302.cd_literal ");


//			strSQL.append(" WHERE ");
//			strSQL.append("     T120.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
//			strSQL.append(" AND T120.nen       = " + toString(cd_shisaku_nen) + " ");
//			strSQL.append(" AND T120.no_oi     = " + toString(cd_shisaku_oi) + " ");
			//�yQP@00342�z
			strSQL.append(" WHERE ");
			strSQL.append("     T320.cd_shain  = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND T320.nen       = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND T320.no_oi     = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND T320.no_eda     = " + toString(cd_shisaku_eda) + " ");

			strSQL.append(" ORDER BY ");
			strSQL.append("  T120.cd_shain ");
			strSQL.append(" ,T120.nen ");
			strSQL.append(" ,T120.no_oi ");
			strSQL.append(" ,T132.seq_shisaku ");
			strSQL.append(" ,T120.sort_kotei ");
			strSQL.append(" ,T120.sort_genryo ");
			//DB����
			listGenryo = this.searchDB.dbSearch(strSQL.toString());


			//�yQP@00342�z���ł̗e�ʎ擾
			strSQL = null;
			strSQL = new StringBuffer();
			strSQL.append(" SELECT ");
			strSQL.append(" 	yoryo AS yoryo ");
			strSQL.append(" FROM ");
			strSQL.append(" 	tr_shisan_shisakuhin");
			strSQL.append(" WHERE ");
			strSQL.append("     cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND no_oi    = " + toString(cd_shisaku_oi) + " ");
			strSQL.append(" AND no_eda    = 0 ");
			//DB����
			List<?> yoryo_list = this.searchDB.dbSearch(strSQL.toString());
			Object items = (Object) yoryo_list.get(0);
			yoryo_moto = toDouble(items,-1);


		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�v�Z���ڎZ�o�@DB���擾����v�Z�����̌����Ɏ��s���܂����B \nSQL:"
					+ strSQL.toString());

		}finally{
			//DB�R�l�N�V�����J��
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;

			}
			//���[�J���ϐ��̊J��
			strSQL = null;

		}

	}

}