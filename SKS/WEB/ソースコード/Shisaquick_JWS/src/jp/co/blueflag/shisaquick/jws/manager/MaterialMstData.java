package jp.co.blueflag.shisaquick.jws.manager;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.base.MaterialData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;


/***********************************************************************
 *
 * �����}�X�^�f�[�^�ێ�
 *  : ���X�g�p�̌����f�[�^�Ɋւ�������Ǘ�����
 *
 **********************************************************************/
public class MaterialMstData extends DataBase {


	/*******************************************************************
	 * �����o
	 *
	 ******************************************************************/
	private MaterialData mtdtSelect;		//���X�g���őI�����ꂽ�����f�[�^(���͒l���͉�ʎ��ꗗ�I���f�[�^)
	private MaterialData mtdtTaihi;		//�ޔ�p�̌����f�[�^�i���͒l���͉�ʎ����ʃf�[�^�j
	private ArrayList aryMateData;		//�����f�[�^�z��
	private ArrayList aryMateChkData;		//���͌����m�F�f�[�^�z��
	private XmlData xdtData;				//XML�f�[�^
	private ExceptionBase ex;				//�G���[�n���h�����O

	/*******************************************************************
	 * �R���X�g���N�^
	 *
	 ******************************************************************/
	public MaterialMstData() throws ExceptionBase{
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();

		try {
			this.mtdtSelect = null;
			this.mtdtSelect = new MaterialData();
			this.mtdtTaihi = new MaterialData();
			this.aryMateData = new ArrayList();
			this.aryMateChkData = new ArrayList();

		} catch ( Exception e ) {
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�����}�X�^�f�[�^�ێ��̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {

		}
	}

	/**
	 * (����)XML�f�[�^�̐ݒ�
	 * @param xmlData : XML�f�[�^
	 * @param kinoId : �@�\ID
	 */
	public void setMaterialData(XmlData xmlData,String kinoId) throws ExceptionBase{
		this.xdtData = xmlData;

		 try{
			this.aryMateData.clear();

			//�@XML�f�[�^�i�[
			String strKinoId = kinoId;

			//�S�̔z��擾
			ArrayList mateData = xdtData.GetAryTag(strKinoId);

			//�@�\�z��擾
			ArrayList kinoData = (ArrayList)mateData.get(0);

			//�e�[�u���z��擾
			ArrayList tableData = (ArrayList)kinoData.get(1);


			//���R�[�h�擾
			for(int i=1; i<tableData.size(); i++){
			//�@�P���R�[�h�擾
			ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
				//�@�����f�[�^����
				MaterialData mateInit = new MaterialData();

				//�����f�[�^�֊i�[
				for(int j=0; j<recData.size(); j++){

					//�@���ږ��擾
					String recNm = ((String[])recData.get(j))[1];
					//�@���ڒl�擾
					String recVal = ((String[])recData.get(j))[2];

					/*****************�����f�[�^�֒l�Z�b�g*********************/
					//�@��ЃR�[�h
					 if(recNm == "cd_kaisha"){
						mateInit.setIntKaishacd(checkNullInt(recVal));

					//�@�����R�[�h
					}if(recNm == "cd_busho"){
						mateInit.setIntBushocd(checkNullInt(recVal));

					//�@�����R�[�h
					}if(recNm == "cd_genryo"){
						mateInit.setStrGenryocd(checkNullString(recVal));

					//�@��Ж�
					}if(recNm == "nm_kaisha"){
						mateInit.setStrKaishanm(checkNullString(recVal));

					// ������
					}if(recNm == "nm_busho"){
						mateInit.setStrBushonm(checkNullString(recVal));

					//�@������
					}if(recNm == "nm_genryo"){
						mateInit.setStrGenryonm(checkNullString(recVal));

					//�@�|�_
					}if(recNm == "ritu_sakusan"){
						mateInit.setDciSakusan(checkNullDecimal(recVal));

					//�@�H��
					}if(recNm == "ritu_shokuen"){
						mateInit.setDciShokuen(checkNullDecimal(recVal));
// ADD start 20121003 QP@20505 No.24
					//�@�l�r�f
					}if(recNm == "ritu_msg"){
						mateInit.setDciMsg(checkNullDecimal(recVal));
// ADD end 20121003 QP@20505 No.24

					//�@���_
					}if(recNm == "ritu_sousan"){
						mateInit.setDciSousan(checkNullDecimal(recVal));

					//�@���ܗL��
					}if(recNm == "ritu_abura"){
						mateInit.setDciGanyu(checkNullDecimal(recVal));

					//�@�\����
					}if(recNm == "hyojian"){
						mateInit.setStrHyoji(checkNullString(recVal));

					//�@�Y����
					}if(recNm == "tenkabutu"){
						mateInit.setStrTenka(checkNullString(recVal));

					//�@�h�{�v�Z�H�i�ԍ�1
					}if(recNm == "no_eiyo1"){
						mateInit.setStrEiyono1(checkNullString(recVal));

					//�@�h�{�v�Z�H�i�ԍ�2
					}if(recNm == "no_eiyo2"){
						mateInit.setStrEiyono2(checkNullString(recVal));

					//�@�h�{�v�Z�H�i�ԍ�3
					}if(recNm == "no_eiyo3"){
						mateInit.setStrEiyono3(checkNullString(recVal));

					//�@�h�{�v�Z�H�i�ԍ�4
					}if(recNm == "no_eiyo4"){
						mateInit.setStrEiyono4(checkNullString(recVal));

					//�@�h�{�v�Z�H�i�ԍ�5
					}if(recNm == "no_eiyo5"){
						mateInit.setStrEiyono5(checkNullString(recVal));

					//�@����1
					}if(recNm == "wariai1"){
						mateInit.setStrWariai1(checkNullString(recVal));

					//�@����2
					}if(recNm == "wariai2"){
						mateInit.setStrWariai2(checkNullString(recVal));

					//�@����3
					}if(recNm == "wariai3"){
						mateInit.setStrWariai3(checkNullString(recVal));

					//�@����4
					}if(recNm == "wariai4"){
						mateInit.setStrWariai4(checkNullString(recVal));

					//�@����5
					}if(recNm == "wariai5"){
						mateInit.setStrWariai5(checkNullString(recVal));

					//�@����
					}if(recNm == "memo"){
						mateInit.setStrMemo(checkNullString(recVal));

					//�@���͓�
					}if(recNm == "dt_toroku"){
						mateInit.setStrNyurokuhi(checkNullString(recVal));

					//�@���͎�ID
					}if(recNm == "id_toroku"){
						mateInit.setDciNyurokucd(checkNullDecimal(recVal));

					//�@���͎Җ�
					}if(recNm == "nm_toroku"){
						mateInit.setStrNyurokunm(checkNullString(recVal));

//					//�@���͏��m�F�t���O
//					}if(recNm == "���͏��m�F�t���O"){


					//�@�m�F��
					}if(recNm == "dt_kakunin"){
						mateInit.setStrKakuninhi(checkNullString(recVal));

					//�@�m�F��ID
					}if(recNm == "id_kakunin"){
						mateInit.setDciKakunincd(checkNullDecimal(recVal));

					//�@�m�F�Җ�
					}if(recNm == "nm_kakunin"){
						mateInit.setStrKakuninnm(checkNullString(recVal));

					//�@�p�~�敪
					}if(recNm == "kbn_haishi"){
						mateInit.setIntHaisicd(checkNullInt(recVal));

					//�@�m��R�[�h
					}if(recNm == "cd_kakutei"){
						mateInit.setStrkakuteicd(checkNullString(recVal));

					//�@�ŏI�w����
					}if(recNm == "dt_konyu"){
						mateInit.setStrKonyu(checkNullString(recVal));

					//�@�P��
					}if(recNm == "tanka"){
						mateInit.setDciTanka(checkNullDecimal(recVal));

					//�@����
					}if(recNm == "budomari"){
						mateInit.setDciBudomari(checkNullDecimal(recVal));

					//�@�o�^��ID
					}if(recNm == "id_toroku"){
						mateInit.setDciTorokuId(checkNullDecimal(recVal));

					//�@�o�^���t
					}if(recNm == "dt_toroku"){
						mateInit.setStrTorokuDt(checkNullString(recVal));

					//�@�o�^�Җ�
					}if(recNm == "nm_toroku"){
						mateInit.setStrTorokuNm(checkNullString(recVal));

					//�@�X�V��ID
					}if(recNm == "id_koshin"){
						mateInit.setDciKosinId(checkNullDecimal(recVal));

					//�@�X�V���t
					}if(recNm == "dt_koshin"){
						mateInit.setStrKosinDt(checkNullString(recVal));

					//�@�X�V�Җ�
					}if(recNm == "nm_koshin"){
						mateInit.setStrKosinNm(checkNullString(recVal));

					//�@�\������
					}if(recNm == "list_max_row"){
						mateInit.setIntListRowMax(checkNullInt(recVal));

					//�@���R�[�h����
					}if(recNm == "max_row"){
						mateInit.setIntMaxRow(checkNullInt(recVal));

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.4
					// �g�p���уt���O
					}if(recNm == "flg_shiyo"){
						mateInit.setIntShiyoFlg(checkNullInt(recVal));

					// ���g�p�t���O
					}if(recNm == "flg_mishiyo"){
						mateInit.setIntMishiyoFlg(checkNullInt(recVal));
//add end --------------------------------------------------------------------------------------
					}

//add start --------------------------------------------------------------------------------------
//QP@00412_�V�T�N�C�b�N���� No.31
					if(recNm == "ma_budomari"){
						mateInit.setMa_dciBudomari(checkNullDecimal(recVal));
					}
//add end --------------------------------------------------------------------------------------

				}
				//�@�����z��֒ǉ�
				this.aryMateData.add(mateInit);
			}

		} catch(ExceptionBase e) {
			throw e;

		} catch(Exception e) {
			e.printStackTrace();
			 //�G���[�ݒ�
			 ex = new ExceptionBase();
			 ex.setStrErrCd("");
			 ex.setStrErrmsg("�����}�X�^�f�[�^�ێ���(����)XML�f�[�^�ݒ肪���s���܂���");
			 ex.setStrErrShori(this.getClass().getName());
			 ex.setStrMsgNo("");
			 ex.setStrSystemMsg(e.getMessage());
			 throw ex;

		 }finally{

		 }
	 }

	/**
	 * (���͌����m�F)XML�f�[�^�̐ݒ�
	 * @param xmlData : XML�f�[�^
	 */
	public void setMaterialChkData(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;

		 try{
			this.aryMateChkData.clear();

			//�@XML�f�[�^�i�[
			String strKinoId = "SA590";

			//�S�̔z��擾
			ArrayList mateData = xdtData.GetAryTag(strKinoId);

			//�@�\�z��擾
			ArrayList kinoData = (ArrayList)mateData.get(0);

			//�e�[�u���z��擾
			ArrayList tableData = (ArrayList)kinoData.get(1);


			//���R�[�h�擾
			for(int i=1; i<tableData.size(); i++){
				//�@�P���R�[�h�擾
				ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
				//�@�����f�[�^����
				MaterialData mateInit = new MaterialData();

				//�����f�[�^�֊i�[
				for(int j=0; j<recData.size(); j++){

					//�@���ږ��擾
					String recNm = ((String[])recData.get(j))[1];
					//�@���ڒl�擾
					String recVal = ((String[])recData.get(j))[2];

					/*****************�����f�[�^�֒l�Z�b�g*********************/
					//�@��ЃR�[�h
					 if(recNm == "cd_kaisha"){
						mateInit.setIntKaishacd(checkNullInt(recVal));

					//�@�����R�[�h
					}if(recNm == "cd_busho"){
						mateInit.setIntBushocd(checkNullInt(recVal));

					//�@�����R�[�h
					}if(recNm == "cd_genryo"){
						mateInit.setStrGenryocd(checkNullString(recVal));

					//�@������
					}if(recNm == "nm_genryo"){
						mateInit.setStrGenryonm(checkNullString(recVal));

					//�@�P��
					}if(recNm == "tanka"){
						mateInit.setDciTanka(checkNullDecimal(recVal));

					//�@����
					}if(recNm == "budomari"){
						mateInit.setDciBudomari(checkNullDecimal(recVal));

					//�@�|�_
					}if(recNm == "ritu_sakusan"){
						mateInit.setDciSakusan(checkNullDecimal(recVal));

					//�@�H��
					}if(recNm == "ritu_shokuen"){
						mateInit.setDciShokuen(checkNullDecimal(recVal));
// ADD start 20121003 QP@20505 No.24
					//�@�l�r�f
					}if(recNm == "ritu_msg"){
						mateInit.setDciMsg(checkNullDecimal(recVal));
// ADD end 20121003 QP@20505 No.24
					//�@���_
					}if(recNm == "ritu_sousan"){
						mateInit.setDciSousan(checkNullDecimal(recVal));

					//�@���ܗL��
					}if(recNm == "ritu_abura"){
						mateInit.setDciGanyu(checkNullDecimal(recVal));

					//�@��Ж�
					}if(recNm == "nm_kaisha"){
						mateInit.setStrKaishanm(checkNullString(recVal));

					//�@������
					}if(recNm == "nm_busho"){
						mateInit.setStrBushonm(checkNullString(recVal));
					}

				}
				//�@�����z��֒ǉ�
				this.aryMateChkData.add(mateInit);
			}

		} catch(ExceptionBase e) {
			throw e;

		} catch(Exception e) {
			 //�G���[�ݒ�
			 ex = new ExceptionBase();
			 ex.setStrErrCd("");
			 ex.setStrErrmsg("�����}�X�^�f�[�^�ێ���(���͌����m�F)XML�f�[�^�̐ݒ肪���s���܂���");
			 ex.setStrErrShori(this.getClass().getName());
			 ex.setStrMsgNo("");
			 ex.setStrSystemMsg(e.getMessage());
			 throw ex;

		 }finally{

		 }
	 }


	 /*******************************************************************
	  * �����f�[�^�z��ǉ�
	  * @param mtdtAddData : �����f�[�^
	  *
	  ******************************************************************/
	 public void AddAryGenryo(MaterialData mtdtAddData) {
		 this.aryMateData.add(mtdtAddData);
	 }

	 /*******************************************************************
	  * �����f�[�^�z��ǉ�
	  * @param mtdtAddData : �����f�[�^
	  *
	  ******************************************************************/
	 public void AddAryGenryoChk(MaterialData mtdtAddData) {
		 this.aryMateChkData.add(mtdtAddData);
	 }


	 /*******************************************************************
	  * �I�������f�[�^�X�V
	  * @param strGenryocd : �����R�[�h
	  * @param intKaishacd : ��ЃR�[�h
	  *
	  ******************************************************************/
	 public void Selectmate(String strGenryocd, int intKaishacd ) throws ExceptionBase{
		 try{
			 this.mtdtSelect = this.getSiteiMate(strGenryocd, intKaishacd);

		 }catch(ExceptionBase ex){
			 throw ex;
		 }catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����}�X�^�f�[�^�ێ��̑I�������f�[�^�X�V�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		 }finally{

		 }
	 }


	 /*******************************************************************
	  * �I�������f�[�^�擾
	  * @return �����f�[�^
	  *
	  ******************************************************************/
	 public MaterialData getSelMate() {
		 return this.mtdtSelect;
	 }
	 public void setSelMate(MaterialData mt) {
		 this.mtdtSelect = mt;
	 }


	 /*******************************************************************
	  * �I�������f�[�^����
	  * @param strGenryocd : �����R�[�h
	  * @param intKaishacd : ��ЃR�[�h
	  * @return : �����f�[�^
	  *
	  ******************************************************************/
	 public MaterialData getSiteiMate(String strGenryocd, int intKaishacd) throws ExceptionBase{
		String strSelGenryo = strGenryocd;			//�@�����F�����R�[�h�擾
		int intSelKaisha = intKaishacd;				//�@�����F��ЃR�[�h�擾
		MaterialData getMate = new MaterialData();	//�@�ԋp�p�����f�[�^����
		boolean intHitfg = false;					//�@�Ώ�Fg

		try{
			//�@�����}�X�^����
			for(int i=0;i<this.aryMateData.size();i++){
				MaterialData selMate = (MaterialData)this.aryMateData.get(i);	//�@�����Ώۃf�[�^�擾
				String strMateGenryo = selMate.getStrGenryocd();	//�@�����R�[�h�擾
				int intMateKaisha = selMate.getIntKaishacd();		//�@��ЃR�[�h�擾

				//�@�����ƃ}�X�^�̌����R�[�h�A��ЃR�[�h���r
				if((strSelGenryo.equals(strMateGenryo.toString())) && (intSelKaisha == intMateKaisha)){
					getMate = selMate;		//�@�ԋp�p�����f�[�^�ɑΏۃf�[�^��ݒ�
					intHitfg = true;		//�@�Ώۃf�[�^�L
				}
			}
			//�@�������ʂ��O���̏ꍇ
			if(!intHitfg){
				//�G���[�ݒ�
				ex = new ExceptionBase();
				ex.setStrErrCd("");
				ex.setStrErrmsg("�I�������f�[�^�����Ɏ��s�F�O���ł�");
				ex.setStrErrShori("MaterialMstData:getSiteiMate");
				ex.setStrMsgNo("");
				ex.setStrSystemMsg("UserError");
				throw ex;
			}
		}catch(ExceptionBase ex){
			throw ex;

		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�I�������f�[�^�����Ɏ��s���܂���");
			ex.setStrErrShori("MaterialMstData:getSiteiMate");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;

		}finally{

		}

		//�@�������ʂ�ԋp
		return getMate;
	 }

	/*******************************************************************
	 * �ޔ�p�̌����f�[�^ �Q�b�^�[
	 * @return mtdtTaihi : �ޔ�p�̌����f�[�^�̒l��Ԃ�
	 *
	 ******************************************************************/
	public MaterialData getMtdtTaihi() {
		return mtdtTaihi;
	}


	/*******************************************************************
	 * �ޔ�p�̌����f�[�^ �Z�b�^�[
	 * @param _mtdtTaihi : �ޔ�p�̌����f�[�^�̒l���i�[����
	 *
	 ******************************************************************/
	public void setMtdtTaihi(MaterialData _mtdtTaihi) {
		this.mtdtTaihi = _mtdtTaihi;
	}


	/*******************************************************************
	 * �����f�[�^�z�� �Q�b�^�[
	 * @return aryMateData : �����f�[�^�z��̒l��Ԃ�
	 *
	 ******************************************************************/
	public ArrayList getAryMateData() {
		return aryMateData;
	}


	/*******************************************************************
	 * �����f�[�^�z�� �Z�b�^�[
	 * @param _aryMateData : �����f�[�^�z��̒l���i�[����
	 *
	 ******************************************************************/
	public void setAryMateData(ArrayList _aryMateData) {
		this.aryMateData = _aryMateData;
	}


	/*******************************************************************
	 * ���͌����m�F�f�[�^�z�� �Q�b�^�[
	 * @return aryMateData : ���͌����m�F�f�[�^�z��̒l��Ԃ�
	 *
	 ******************************************************************/
	public ArrayList getAryMateChkData() {
		return aryMateChkData;
	}


	/*******************************************************************
	 * ���͌����m�F�f�[�^�z�� �Z�b�^�[
	 * @param _aryMateChkData : ���͌����m�F�f�[�^�z��̒l���i�[����
	 *
	 ******************************************************************/
	public void setAryMateChkData(ArrayList _aryMateChkData) {
		this.aryMateChkData = _aryMateChkData;
	}

	/*******************************************************************
	 * �����f�[�^�z�� �m�F
	 *
	 ******************************************************************/
	public void DispMateData() {
		for(int i=0;i<this.aryMateData.size();i++){
			MaterialData dispMate = new MaterialData();
			dispMate = (MaterialData)this.aryMateData.get(i);
			//�@�e�X�g�\��
			System.out.println("********************* " + (i+1) + "���� ***************************");
			dispMate.dispMateData();
		}
	}

	/**
	 * �󕶎��`�F�b�N�iString�j
	 */
	public String checkNullString(String val){
		String ret = null;
		try{
			//�l���󕶎��łȂ��ꍇ
			if(!val.equals("")){
				ret = val;
			}
		}catch(Exception e){

		}finally{

		}
		return ret;
	}

	/**
	 * �󕶎��`�F�b�N�iDecimal�j
	 */
	public BigDecimal checkNullDecimal(String val){
		BigDecimal ret = null;
		try{
			//�l���󕶎��łȂ��ꍇ
			if(!val.equals("")){
				ret = new BigDecimal(val);
			}
		}catch(Exception e){

		}finally{

		}
		return ret;
	}

	/**
	 * �󕶎��`�F�b�N�iInt�j
	 */
	public int checkNullInt(String val){
		int ret = -1;
		try{
			//�l���󕶎��łȂ��ꍇ
			if(!val.equals("")){
				ret = Integer.parseInt(val);
			}
		}catch(Exception e){

		}finally{

		}
		return ret;
	}
}
