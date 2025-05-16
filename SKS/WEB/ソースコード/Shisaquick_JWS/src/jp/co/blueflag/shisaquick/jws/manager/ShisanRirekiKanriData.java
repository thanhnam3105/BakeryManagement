package jp.co.blueflag.shisaquick.jws.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import jp.co.blueflag.shisaquick.jws.base.ShisanData;
import jp.co.blueflag.shisaquick.jws.base.XmlData;
import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;
import jp.co.blueflag.shisaquick.jws.data.DataCtrl;

/***********************************************************************
 * 
 * ���Z�����f�[�^�Ǘ�
 *  : ���X�g�p�̎��Z�����f�[�^�Ɋւ�������Ǘ�����
 *
 **********************************************************************/
public class ShisanRirekiKanriData extends DataBase {

	/*******************************************************************
	 * �����o
	 ******************************************************************/
	private ArrayList aryShisanRirekiData;		//���Z�����f�[�^�z��
	private ArrayList aryShisanKakuteiNo;		//���Z�m��T���v��No�f�[�^�z��
	private int intLastShisakuSeq;				//�ŏI���Z�m��@����SEQ
	private XmlData xdtData;						//XML�f�[�^
	private ExceptionBase ex;						//�G���[�n���h�����O	

	/*******************************************************************
	 * 
	 * �R���X�g���N�^
	 * 
	 ******************************************************************/
	public ShisanRirekiKanriData() throws ExceptionBase{
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();	
		
		try {
			this.aryShisanRirekiData = new ArrayList();
			this.aryShisanKakuteiNo = new ArrayList();
			this.intLastShisakuSeq = 0;
			
		} catch ( Exception e ) {			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���Z�����f�[�^�Ǘ��̃R���X�g���N�^�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
	}

	/*******************************************************************
	 * 0001.�f�[�^�ݒ�
	 *  : XML�f�[�^��莎��e�[�u���f�[�^�𐶐�����B
	 * @param xdtData : XML�f�[�^
	 * @throws ExceptionBase 
	 ******************************************************************/
	public void setShisanRirekiData(XmlData xdtSetXml) throws ExceptionBase{
		this.xdtData = xdtSetXml;
		
		try {
			//���Z�����f�[�^�z���������
			this.aryShisanRirekiData.clear();
			
			/**********************************************************
			 *�@ShisanRireki�f�[�^�i�[
			 *********************************************************/
			//�@�\ID�̐ݒ�
			String strKinoId = "SA840";
			
			//�S�̔z��擾
			ArrayList userData = xdtData.GetAryTag(strKinoId);
			
			//�@�\�z��擾
			ArrayList kinoData = (ArrayList)userData.get(0);

			//�e�[�u���z��擾
			ArrayList tableData = (ArrayList)kinoData.get(1);

			//���R�[�h�擾
			for(int i=1; i<tableData.size(); i++){
				//�@�P���R�[�h�擾
				ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
				
				ShisanData shisanRirekiData = new ShisanData();
				
				//���Z�����f�[�^�֊i�[
				for(int j=0; j<recData.size(); j++){
						
					//�@���ږ��擾
					String recNm = ((String[])recData.get(j))[1];
					//�@���ڒl�擾
					String recVal = ((String[])recData.get(j))[2];
	
					/*****************ShisanRireki�f�[�^�֒l�Z�b�g*********************/
					//�@����CD-�Ј�CD
					 if(recNm == "cd_shain"){
						 shisanRirekiData.setDciShisakuUser(checkNullDecimal(recVal));
						
					//�@����CD-�N
					}if(recNm == "nen"){
						shisanRirekiData.setDciShisakuYear(checkNullDecimal(recVal));
					
					//�@����CD-�ǔ�
					}if(recNm == "no_oi"){
						shisanRirekiData.setDciShisakuNum(checkNullDecimal(recVal));
						
					//�@����SEQ
					}if(recNm == "seq_shisaku"){
						shisanRirekiData.setIntShisakuSeq(checkNullInt(recVal));
						
					//�@����\����
					}if(recNm == "sort_rireki"){
						shisanRirekiData.setIntRirekiNo(checkNullInt(recVal));

					//�@�T���v��NO�i���́j
					}if(recNm == "nm_sample"){
						shisanRirekiData.setStrSampleNo(checkNullString(recVal));

					//�@���Z���t
					}if(recNm == "dt_shisan"){
						shisanRirekiData.setStrShisanHi(checkNullString(recVal));
						
					//�@�o�^��ID
					}if(recNm == "id_toroku"){
						shisanRirekiData.setDciTorokuId(new BigDecimal(recVal));
						
					//�@�o�^���t
					}if(recNm == "dt_toroku"){
						shisanRirekiData.setStrTorokuHi(checkNullString(recVal));
						
					}
					
				}
				//�@���Z�����f�[�^�z��֒ǉ�
				this.aryShisanRirekiData.add(shisanRirekiData);
			}
			
		} catch (ExceptionBase e ) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���Z�����f�[�^�̃f�[�^�ݒ�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/*******************************************************************
	 * 0002.�f�[�^�ݒ�(���Z�m��T���v��No)
	 *  : XML�f�[�^��莎��e�[�u���f�[�^�𐶐�����B
	 * @param xdtData : XML�f�[�^
	 * @throws ExceptionBase 
	 ******************************************************************/
	public void setShisanKakuteiNoData(XmlData xdtSetXml) throws ExceptionBase{
		this.xdtData = xdtSetXml;
		
		try {
			//���Z�����f�[�^�z���������
			this.aryShisanKakuteiNo.clear();
			
			/**********************************************************
			 *�@ShisanRireki�f�[�^�i�[
			 *********************************************************/
			//�@�\ID�̐ݒ�
			String strKinoId = "SA830";
			
			//�S�̔z��擾
			ArrayList userData = xdtData.GetAryTag(strKinoId);
			
			//�@�\�z��擾
			ArrayList kinoData = (ArrayList)userData.get(0);

			//�e�[�u���z��擾
			ArrayList tableData = (ArrayList)kinoData.get(1);

			//���R�[�h�擾
			for(int i=1; i<tableData.size(); i++){
				//�@�P���R�[�h�擾
				ArrayList recData = ((ArrayList)((ArrayList)tableData.get(i)).get(0));
				
				ShisanData shisanRirekiData = new ShisanData();
				
				//���Z�����f�[�^�֊i�[
				for(int j=0; j<recData.size(); j++){
						
					//�@���ږ��擾
					String recNm = ((String[])recData.get(j))[1];
					//�@���ڒl�擾
					String recVal = ((String[])recData.get(j))[2];
	
					/*****************ShisanRireki�f�[�^�֒l�Z�b�g*********************/
					//�@����SEQ
					if(recNm == "seq_shisaku"){
						shisanRirekiData.setIntShisakuSeq(checkNullInt(recVal));
						
					//�@�T���v��NO�i���́j
					}if(recNm == "nm_sample"){
						shisanRirekiData.setStrSampleNo(checkNullString(recVal));

					//�@���Z���t
					}if(recNm == "dt_shisan"){
						shisanRirekiData.setStrShisanHi(checkNullString(recVal));
						
					//�@�ŏI���Z�m��@����SEQ
					}if(recNm == "seq_shisaku_last"){
						this.intLastShisakuSeq = checkNullInt(recVal);
						
					}
					
				}
				//�@���Z�����f�[�^�z��֒ǉ�
				this.aryShisanKakuteiNo.add(shisanRirekiData);
			}
			
		} catch (ExceptionBase e ) {
			throw e;
			
		} catch (Exception e) {
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���Z�m��T���v��No�f�[�^�̃f�[�^�ݒ�Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		} finally {
			
		}
		
	}

	/*******************************************************************
	 * 
	 * 0003.���Z�m��T���v��No ���Z�f�[�^�擾����
	 * @param intSelectedIndex : �I��ԍ�
	 * @throws ExceptionBase 
	 * 
	 ******************************************************************/
	public ShisanData SearchShisanKakuteiData(int intSelectedIndex) throws ExceptionBase{

		//�V�K���X�g�C���X�^���X����
		ShisanData ret = null;
		
		try{
			
			//���Z�f�[�^���擾
			if ( this.aryShisanKakuteiNo.size() > 0
					&& intSelectedIndex >= 0 ) {
				ret = (ShisanData)this.aryShisanKakuteiNo.get(intSelectedIndex);
			}
			
		} catch (Exception e) {
			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("���Z�m��T���v��No ���Z�f�[�^�擾�����s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			
		}
		return ret;
		
	}

	/*******************************************************************
	 * 
	 * 0004.�ŏI���Z�m��Ώۂ̎��Z�f�[�^��������
	 * @throws ExceptionBase 
	 * 
	 ******************************************************************/
	public ShisanData SearchLastShisanData() throws ExceptionBase{

		//�V�K���X�g�C���X�^���X����
		ShisanData ret = new ShisanData();
		
		try{
			
			Iterator ite = this.aryShisanKakuteiNo.iterator();
			
			//���X�g���������[�v
			while(ite.hasNext()){
				//���Z�f�[�^�I�u�W�F�N�g�擾
				ShisanData shisanData = (ShisanData)ite.next();
				
				//�����F����SEQ�Ǝ��Z�f�[�^�I�u�W�F�N�g�F����SEQ����v�����ꍇ
				if (this.intLastShisakuSeq  == shisanData.getIntShisakuSeq()){
					//�ԋp���X�g�Ɏ��Z�f�[�^�I�u�W�F�N�g�ǉ�
					ret = shisanData;
					
				}
			}
			
		} catch (Exception e) {
			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�ŏI���Z�m��Ώۂ̎��Z�f�[�^���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			
		}
		return ret;
		
	}

	/*******************************************************************
	 * 
	 * �ŏI���Z�m��Ώۂ̎���f�[�^ �ݒ�
	 * @param _intLastShisakuSeq : ����SEQ
	 * @throws ExceptionBase 
	 * 
	 ******************************************************************/
	public void SetLastShisanData(int _intLastShisakuSeq) throws ExceptionBase {
		
		try{
			
			//�ŏI���Z�m�� ����SEQ��ݒ肷��
			this.intLastShisakuSeq = _intLastShisakuSeq;

			//�ŏI���Z�m�� ���Z����ݒ肷��
			Iterator ite = this.aryShisanKakuteiNo.iterator();
			
			//���X�g���������[�v
			while(ite.hasNext()){
				//���Z�f�[�^�I�u�W�F�N�g�擾
				ShisanData shisanData = (ShisanData)ite.next();
				
				//�����F����SEQ�Ǝ��Z�f�[�^�I�u�W�F�N�g�F����SEQ����v�����ꍇ
				if (this.intLastShisakuSeq  == shisanData.getIntShisakuSeq()){
					//�ԋp���X�g�Ɏ��Z�f�[�^�I�u�W�F�N�g�ǉ�
					shisanData.setStrShisanHi(DataCtrl.getInstance().getTrialTblData().getSysDate());
					
				}
				
			}
			
		} catch (Exception e) {
			
			//�G���[�ݒ�
			this.ex = new ExceptionBase();
			this.ex.setStrErrCd("");
			this.ex.setStrErrmsg("�ŏI���Z�m��Ώۂ̎��Z�f�[�^���������s���܂���");
			this.ex.setStrErrShori(this.getClass().getName());
			this.ex.setStrMsgNo("");
			this.ex.setStrSystemMsg(e.getMessage());
			throw ex;

		} finally {
			
		}
		
	}

	/*******************************************************************
	 * 
	 * ���Z���̓f�[�^�z�� �Q�b�^�[
	 * @return aryShisanRirekiData : ���Z���̓f�[�^�z��̒l��Ԃ�
	 * 
	 ******************************************************************/
	public ArrayList getAryShisanRirekiData() {
		return aryShisanRirekiData;
	}

	/*******************************************************************
	 * 
	 * ���Z���̓f�[�^�z�� �Q�b�^�[
	 * @return aryShisanRirekiData : ���Z���̓f�[�^�z��̒l��Ԃ�
	 * 
	 ******************************************************************/
	public ArrayList getAryShisanKakuteiData() {
		return aryShisanKakuteiNo;
	}

	/*******************************************************************
	 * 
	 * �󕶎��`�F�b�N�iString�j
	 * @param val : �Ώےl
	 * 
	 ******************************************************************/
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

	/*******************************************************************
	 * 
	 * �󕶎��`�F�b�N�iDecimal�j
	 * @param val : �Ώےl
	 * 
	 ******************************************************************/
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

	/*******************************************************************
	 * 
	 * �󕶎��`�F�b�N�iInt�j
	 * @param val : �Ώےl
	 * 
	 ******************************************************************/
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
