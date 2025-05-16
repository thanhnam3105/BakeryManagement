package jp.co.blueflag.shisaquick.jws.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;

/**
 * 
 * �����f�[�^�ێ�
 *  : �����f�[�^�̊Ǘ����s��
 *
 */
public class BushoData extends DataBase {

	private ArrayList artBushoCd;			//�����R�[�h
	private ArrayList aryBushoNm;		//������
	
	private XmlData xdtData;				//XML�f�[�^
	private ExceptionBase ex;				//�G���[�n���h�����O
	
	/**
	 * �R���X�g���N�^
	 * @param strKinoId : �@�\ID
	 */
	public BushoData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		this.artBushoCd = new ArrayList();
		this.aryBushoNm = new ArrayList();
	}
	
	/**
	 * XML�f�[�^�̐ݒ�
	 * @param xmlData : XML�f�[�^
	 */
	public void setBushoData(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;
		
		try{

			//���ڂ̃N���A
			this.artBushoCd.clear();
			this.aryBushoNm.clear();
			
			/**********************************************************
			 *�@���e�����f�[�^�i�[
			 *********************************************************/
			
			//�@�\ID�̐ݒ�
			String strKinoId = "SA290"; 
			
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
				
				//���[�U�f�[�^�֊i�[
				for(int j=0; j<recData.size(); j++){
					
					//�@���ږ��擾
					String recNm = ((String[])recData.get(j))[1];
					//�@���ڒl�擾
					String recVal = ((String[])recData.get(j))[2];
					
					/*****************���e�����f�[�^�֒l�Z�b�g*********************/
					if ( recNm == "cd_busho" ) {
						//���e�����R�[�h
						this.artBushoCd.add(recVal);
					} else if ( recNm == "nm_busho" ) {
						//���e������
						this.aryBushoNm.add(recVal);
					}
				}
			}
			
		}catch(ExceptionBase e){
			throw e;		
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����f�[�^�̏������Ɏ��s���܂���");
			ex.setStrErrShori("KaishaData:setKaishaData");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
	
	/**
	 * �yQP@00342�zXML�f�[�^�̐ݒ�@�c�ƕ����擾
	 * @param xmlData : XML�f�[�^
	 */
	public void setBushoData_Eigyo(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;
		
		try{

			//���ڂ̃N���A
			this.artBushoCd.clear();
			this.aryBushoNm.clear();
			
			/**********************************************************
			 *�@���e�����f�[�^�i�[
			 *********************************************************/
			
			//�@�\ID�̐ݒ�
			String strKinoId = "FGEN2070"; 
			
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
				
				//���[�U�f�[�^�֊i�[
				for(int j=0; j<recData.size(); j++){
					
					//�@���ږ��擾
					String recNm = ((String[])recData.get(j))[1];
					//�@���ڒl�擾
					String recVal = ((String[])recData.get(j))[2];
					
					/*****************���e�����f�[�^�֒l�Z�b�g*********************/
					if ( recNm == "cd_busho" ) {
						//���e�����R�[�h
						this.artBushoCd.add(recVal);
					} else if ( recNm == "nm_busho" ) {
						//���e������
						this.aryBushoNm.add(recVal);
					}
				}
			}
			
		}catch(ExceptionBase e){
			throw e;		
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�����f�[�^�̏������Ɏ��s���܂���");
			ex.setStrErrShori("KaishaData:setKaishaData");
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}

	/**
	 * �����R�[�h �Q�b�^�[
	 * @return artBushoCd : �����R�[�h�̒l��Ԃ�
	 */
	public ArrayList getArtBushoCd() {
		return artBushoCd;
	}
	/**
	 * �����R�[�h �Z�b�^�[
	 * @param _artBushoCd : �����R�[�h�̒l���i�[����
	 */
	public void setArtBushoCd(ArrayList _artBushoCd) {
		this.artBushoCd = _artBushoCd;
	}

	/**
	 * ������ �Q�b�^�[
	 * @return aryBushoNm : �������̒l��Ԃ�
	 */
	public ArrayList getAryBushoNm() {
		return aryBushoNm;
	}
	/**
	 * ������ �Z�b�^�[
	 * @param _aryBushoNm : �������̒l���i�[����
	 */
	public void setAryBushoNm(ArrayList _aryBushoNm) {
		this.aryBushoNm = _aryBushoNm;
	}

	/**
	 * �����f�[�^�\��
	 */
	public void dispBushoData(){
		for ( int i = 0; i < this.getArtBushoCd().size(); i++ ) {
			System.out.println("\n--------" + i + "----------START");
			System.out.println("cd_busho" + i + ":" + this.getArtBushoCd().get(i).toString());
			System.out.println("nm_busho" + i + ":" + this.getAryBushoNm().get(i).toString());
			System.out.println("--------------------END");
		}
	}

}
