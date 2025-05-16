package jp.co.blueflag.shisaquick.jws.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;

/**
 * 
 * ��Ѓf�[�^�ێ�
 *  : ��Ѓf�[�^�̊Ǘ����s��
 *
 */
public class KaishaData extends DataBase {

	private ArrayList artKaishaCd;	//��ЃR�[�h
	private ArrayList aryKaishaNm;	//��Ж�
	private ArrayList aryKaishaGenryo;	//������
	
	private XmlData xdtData;			//XML�f�[�^
	private ExceptionBase ex;			//�G���[�n���h�����O
	
	/**
	 * �R���X�g���N�^
	 * @param strKinoId : �@�\ID
	 */
	public KaishaData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		this.artKaishaCd = new ArrayList();
		this.aryKaishaNm = new ArrayList();
		this.aryKaishaGenryo = new ArrayList();
	}
	
	/**
	 * XML�f�[�^�̐ݒ�
	 * @param xmlData : XML�f�[�^
	 */
	public void setKaishaData(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;
		
		try{
			
			//���ڂ̃N���A
			this.artKaishaCd.clear();
			this.aryKaishaNm.clear();
			
			/**********************************************************
			 *�@���e�����f�[�^�i�[
			 *********************************************************/
			
			//�@�\ID�̐ݒ�
			String strKinoId = "SA140"; 
			
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
					if ( recNm == "cd_kaisha" ) {
						//��ЃR�[�h
						this.artKaishaCd.add(recVal);
					} else if ( recNm == "nm_kaisha" ) {
						//��Ж�
						this.aryKaishaNm.add(recVal);
					} else if ( recNm == "keta_genryo" ) {
						//������
						this.aryKaishaGenryo.add(recVal);
					}
				}
			}
			
		}catch(ExceptionBase e){
			throw e;
		
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("��Ѓf�[�^�̏������Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}
	
	/**
	 * �yQP@00342�zXML�f�[�^�̐ݒ�@�c�ƕ���
	 * @param xmlData : XML�f�[�^
	 */
	public void setKaishaData_Eigyo(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;
		
		try{
			
			//���ڂ̃N���A
			this.artKaishaCd.clear();
			this.aryKaishaNm.clear();
			
			/**********************************************************
			 *�@���e�����f�[�^�i�[
			 *********************************************************/
			
			//�@�\ID�̐ݒ�
			String strKinoId = "FGEN2090"; 
			
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
					if ( recNm == "cd_kaisha" ) {
						//��ЃR�[�h
						this.artKaishaCd.add(recVal);
					} else if ( recNm == "nm_kaisha" ) {
						//��Ж�
						this.aryKaishaNm.add(recVal);
					}
				}
			}
			
		}catch(ExceptionBase e){
			throw e;
		
		}catch(Exception e){
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("��Ѓf�[�^�̏������Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
		}
	}

	/**
	 * ��ЃR�[�h �Q�b�^�[
	 * @return artKaishaCd : ��ЃR�[�h�̒l��Ԃ�
	 */
	public ArrayList getArtKaishaCd() {
		return artKaishaCd;
	}
	/**
	 * ��ЃR�[�h �Z�b�^�[
	 * @param _artKaishaCd : ��ЃR�[�h�̒l���i�[����
	 */
	public void setArtKaishaCd(ArrayList _artKaishaCd) {
		this.artKaishaCd = _artKaishaCd;
	}

	/**
	 * ��Ж� �Q�b�^�[
	 * @return aryKaishaNm : ��Ж��̒l��Ԃ�
	 */
	public ArrayList getAryKaishaNm() {
		return aryKaishaNm;
	}
	/**
	 * ��Ж� �Z�b�^�[
	 * @param _aryKaishaNm : ��Ж��̒l���i�[����
	 */
	public void setAryKaishaNm(ArrayList _aryKaishaNm) {
		this.aryKaishaNm = _aryKaishaNm;
	}
	
	/**
	 * ������ �Q�b�^�[
	 * @return aryKaishaGenryo : �������̒l��Ԃ�
	 */
	public ArrayList getAryKaishaGenryo() {
		return aryKaishaGenryo;
	}
	/**
	 * ������ �Z�b�^�[
	 * @param aryKaishaGenryo : �������̒l���i�[����
	 */
	public void setAryKaishaGenryo(ArrayList aryKaishaGenryo) {
		this.aryKaishaGenryo = aryKaishaGenryo;
	}

	/**
	 * ��Ѓf�[�^�\��
	 */
	public void dispKaishaData(){
		for ( int i = 0; i < this.getArtKaishaCd().size(); i++ ) {
			System.out.println("\n--------" + i + "----------START");
			System.out.println("cd_kaisha" + i + ":" + this.getArtKaishaCd().get(i).toString());
			System.out.println("nm_kaisha" + i + ":" + this.getAryKaishaNm().get(i).toString());
			System.out.println("keta_genryo" + i + ":" + this.getAryKaishaGenryo().get(i).toString());
			System.out.println("--------------------END");
		}
	}

}
