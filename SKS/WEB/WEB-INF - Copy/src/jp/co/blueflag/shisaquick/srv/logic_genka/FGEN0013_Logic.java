package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN0020_Logic;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * �������Z���ޕ\������DB����
 *  : �������Z��ʂ̎��ޕ����̏����擾����B
 *  �@�\ID�FFGEN0010_Logic
 *
 * @author Nishigawa
 * @since  2009/10/22
 */
public class FGEN0013_Logic extends LogicBase{

	//����NO
	BigDecimal cd_shisaku_syainID = new BigDecimal(-1);
	int cd_shisaku_nen = -1;
	int cd_shisaku_oi = -1;
	int cd_shisaku_eda = -1; //�yQP@00342�z

	//���ޏ��
	List<?> listShizai = null;

	/**
	 * �������Z���ޕ\������DB����
	 * : �C���X�^���X����
	 */
	public FGEN0013_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �������Z�����\��
	 *  : �������Z��ʂ̌��������̏����擾����B
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

		//�Čv�Z���N�G�X�g
		RequestResponsKindBean reqDataKeisan = null;
		//�Čv�Z���X�|���X
		RequestResponsKindBean resDataKeisan = null;
		//�������Z�����\�����X�|���X
		RequestResponsKindBean ret = null;
		//�Čv�Z�N���X
		CGEN0020_Logic clsCGEN0020_Logic = null;


		try {

			cd_shisaku_syainID = toDecimal(reqData.getFieldVale("kihon", "rec", "cd_shain"), "-1");
			cd_shisaku_nen = toInteger(reqData.getFieldVale("kihon", "rec", "nen"), -1);
			cd_shisaku_oi = toInteger(reqData.getFieldVale("kihon", "rec", "no_oi"), -1);

			//�yQP@00342�z
			cd_shisaku_eda = toInteger(reqData.getFieldVale("kihon", "rec", "no_eda"), -1);

			//DB���擾
			getDBKomoku();

			//���N�G�X�g�C���X�^���X
			reqDataKeisan = new RequestResponsKindBean();
			//�Čv�Z���N�G�X�g����
			makeReqDataKeisan(reqDataKeisan);

			//�Čv�Z
			clsCGEN0020_Logic = new CGEN0020_Logic();
			resDataKeisan = clsCGEN0020_Logic.ExecLogic(reqDataKeisan, userInfoData);

			//���X�|���X�̃C���X�^���X
			ret = new RequestResponsKindBean();
			//�������Z�����\�����X�|���X����
			makeResData(ret, resDataKeisan);


		} catch (Exception e) {
			this.em.ThrowException(e, "");
		} finally {
			//���X�g�̔j��
			removeList(listShizai);

		}
		return ret;

	}
	/**
	 * �Čv�Z�N���X�ɓn�����N�G�X�g�𐶐�
	 * @param reqDataKeisan	�F�����ナ�N�G�X�g
	 * @param listShisaku	�FDB������
	 * @param listSanpuru	�FDB�T���v�����
	 * @param listGenryo	�FDB�������
	 * @param listShizai	�FDB���ޏ��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan(

			 RequestResponsKindBean reqDataKeisan
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {


		try{

			//��{���N�G�X�g�̐���
			makeReqDataKeisan_Kihon(reqDataKeisan);

			//ID�ݒ�
			reqDataKeisan.setID("CGEN0020");

			//���ރ��N�G�X�g�̐���
			makeReqDataKeisan_Shizai(reqDataKeisan);

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B ");

		}finally{

		}

	}
	/**
	 * ��{���N�G�X�g�̐���
	 * @param reqDataKeisan	�F�������ʁ@���N�G�X�g
	 * @param listShisaku	�FDB���@����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan_Kihon(

			 RequestResponsKindBean reqDataKeisan
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try{

			// ----------------------------------------------------------------------+
			// ����CD-�Ј�CD	cd_shain
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "cd_shain", toString(cd_shisaku_syainID));
			// ----------------------------------------------------------------------+
			// ����CD-�N		nen
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "nen", toString(cd_shisaku_nen));
			// ----------------------------------------------------------------------+
			// ����CD-�ǔ�	no_oi
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "no_oi", toString(cd_shisaku_oi));
			// ----------------------------------------------------------------------+
			// �yQP@00342�z����CD-�ǔ�	no_eda
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "no_eda", toString(cd_shisaku_eda));
			// ----------------------------------------------------------------------+
			// ���萔			irisu
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "irisu", toString(""));
			// ----------------------------------------------------------------------+
			// �������[�h		mode
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "mode", "2");
			// ----------------------------------------------------------------------+
			// ������]		kibo_genka
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_genka", toString(""));
			// ----------------------------------------------------------------------+
			// ������]		kibo_baika
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_baika", toString(""));
			// ----------------------------------------------------------------------+
			// �����P��CD	kibo_genka_tani
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "kibo_genka_tani", toString(""));
			// ----------------------------------------------------------------------+
			// �v�Z���ځi�Œ��/�P�[�Xor�Œ��/kg�j	ragio_kesu_kg
			// ----------------------------------------------------------------------+
			reqDataKeisan.addFieldVale("kihon", "rec", "ragio_kesu_kg", toString(""));

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B ");

		}finally{

		}

	}
	/**
	 * ���X�|���X�f�[�^�̐���
	 * @param resData		�F�ҏW���ʁ@���X�|���X�f�[�^
	 * @param resDataKeisan	�F�v�Z�N���X�̌���
	 * @param listShisaku	�FDB���@������
	 * @param listSanpuru	�FDB���@�T���v�����
	 * @param listGenryo	�FDB���@�������
	 * @param listShizai	�FDB���@���ޏ��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeResData(

			 RequestResponsKindBean resData
			,RequestResponsKindBean resDataKeisan
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		int cnt = 0;
		int cntShizai = 0;

		try{


			resData.setID("FGEN0013");

			try{

				// E.kitazawa java�G���[�Ή� (NullPointerException)--- add start 2015/03/03
				// ���݃`�F�b�N
				RequestResponsTableBean been = resDataKeisan.getTableItem("shizai");
				if(been == null) {
					// shizai �f�[�^�������Ƃ��icatch �Őݒ肵�Ȃ��j
					cnt = toInteger(ConstManager.getConstValue(ConstManager.Category.�ݒ�,"GENK_SHIZAI_LIST_ADD_CNT"));
				} else {
				// E.kitazawa java�G���[�Ή� --------------------- add end

					cntShizai = resDataKeisan.getCntRow("shizai");
					cnt = toInteger(resDataKeisan.getFieldVale("kihon", "rec", "cnt_shizai"));
				}

			}catch(Exception e){

				cnt = toInteger(ConstManager.getConstValue(ConstManager.Category.�ݒ�,"GENK_SHIZAI_LIST_ADD_CNT"));


			}

			//��{�iKihon�j���X�|���X����
			resData.addTableItem(resDataKeisan.getTableItem("kihon"));
			//����͕��̍s�𕉉�
			resData.setFieldVale("kihon", "rec", "cnt_shizai"
					, toString(cnt));

			if (cntShizai > 0){
				//���ށishizai�j���X�|���X����
				resData.addTableItem(resDataKeisan.getTableItem("shizai"));

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@���X�|���X�����Ɏ��s���܂����B ");

		}finally{

		}

	}
	/**
	 * DB���擾
	 * @param listShisaku	�F �������ʁ@������
	 * @param listSanpuru	�F �������ʁ@�T���v�����
	 * @param listGenryo	�F �������ʁ@�������
	 * @param listShizai	�F �������ʁ@���ޏ��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void getDBKomoku()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		//SQL�o�b�t�@
		StringBuffer strSQL = new StringBuffer();

		try{

			//DB�R�l�N�V����
			createSearchDB();

			//���ޏ��

			strSQL = null;
			strSQL = new StringBuffer();

			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  cd_shain ");	//0
			strSQL.append(" ,nen ");		//1
			strSQL.append(" ,no_oi ");		//2
			strSQL.append(" ,seq_shizai ");	//3
			strSQL.append(" ,no_sort ");	//4
			strSQL.append(" ,cd_kaisha ");	//5
			strSQL.append(" ,cd_busho  ");	//6
			strSQL.append(" ,cd_shizai ");	//7
			strSQL.append(" ,nm_shizai ");	//8
			strSQL.append(" ,tanka ");		//9
			strSQL.append(" ,budomari ");	//10
			strSQL.append(" ,cs_siyou ");	//11
			strSQL.append(" ,id_toroku ");	//12
			strSQL.append(" ,CONVERT(VARCHAR,dt_toroku,111) ");	//13
			strSQL.append(" ,id_koshin ");	//14
			strSQL.append(" ,CONVERT(VARCHAR,dt_koshin,111) ");	//15
			strSQL.append(" FROM ");
			strSQL.append("           tr_shisan_shizai ");
			strSQL.append(" WHERE ");
			strSQL.append("     cd_shain = " + toString(cd_shisaku_syainID) + " ");
			strSQL.append(" AND nen      = " + toString(cd_shisaku_nen) + " ");
			strSQL.append(" AND no_oi    = " + toString(cd_shisaku_oi) + " ");

			//�yQP@00342�z
			strSQL.append(" AND no_eda    = " + toString(cd_shisaku_eda) + " ");
			strSQL.append(" ORDER BY ");
			strSQL.append("  no_sort ");
			//DB����
			listShizai = this.searchDB.dbSearch(strSQL.toString());

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B \nSQL:"
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
	/**
	 * ���ރ��N�G�X�g�̐���
	 * @param reqDataKeisan	:�ҏW���ʃ��N�G�X�g
	 * @param listShizai	:���ޏ��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void makeReqDataKeisan_Shizai(

			 RequestResponsKindBean reqDataKeisan
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {

		try{

			for(int i = 0 ; i < listShizai.size(); i++){

				Object[] items = (Object[]) listShizai.get(i);

				// ----------------------------------------------------------------------+
				// ����SEQ	seq_shizai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "seq_shizai", toString(items[3]));
				// ----------------------------------------------------------------------+
				// ���CD	cd_kaisya
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "cd_kaisya", toString(items[5]));
				// ----------------------------------------------------------------------+
				// �H��CD	cd_kojyo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "cd_kojyo", toString(items[6]));
				// ----------------------------------------------------------------------+
				// ����CD	cd_shizai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "cd_shizai", toString(items[7]));
				// ----------------------------------------------------------------------+
				// ���ޖ�	nm_shizai
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "nm_shizai", toString(items[8]));
				// ----------------------------------------------------------------------+
				// �P��		tanka
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "tanka", toString(items[9]));
				// ----------------------------------------------------------------------+
				// �����i���j	budomari
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "budomari", toString(items[10]));
				// ----------------------------------------------------------------------+
				// �g�p��/���	shiyouryo
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "shiyouryo", toString(items[11]));
				// ----------------------------------------------------------------------+
				// �o�^��ID id_toroku
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "id_toroku", toString(items[12]));
				// ----------------------------------------------------------------------+
				// �o�^���t dt_toroku
				// ----------------------------------------------------------------------+
				reqDataKeisan.addFieldVale("shizai", "rec" + toString(i)
						, "dt_toroku", toString(items[13]));

			}

		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�����\���@DB���擾�Ɏ��s���܂����B ");

		}finally{

		}

	}

}