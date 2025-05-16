package jp.co.blueflag.shisaquick.jws.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.common.DataBase;
import jp.co.blueflag.shisaquick.jws.common.ExceptionBase;

/**
 * 
 * ���e�����f�[�^�ێ�
 *  : ���e�����f�[�^�̊Ǘ����s��
 *
 */
public class LiteralData extends DataBase {

	private String strCategoryCd;		//�J�e�S���R�[�h
	private ArrayList aryLiteralCd;			//���e�����R�[�h
	private ArrayList aryLiteralNm;		//���e������
	private ArrayList aryValue1;				//���e�����l1
	private ArrayList aryValue2;				//���e�����l2
	private ArrayList arySortNo;				//�\����
	private ArrayList aryGroupCd;				//�O���[�v�R�[�h
	
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	private ArrayList aryBiko;				//���l
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
	
	private String strKinoId;				//�@�\ID
	private XmlData xdtData;			//XML�f�[�^
	private ExceptionBase ex;			//�G���[�n���h�����O
	
	/**
	 * �R���X�g���N�^
	 * @param strKinoId : �@�\ID
	 */
	public LiteralData(String strKinoId) {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		//�@�\ID���Z�b�g
		this.strKinoId = strKinoId;
		
		this.strCategoryCd = "";
		this.aryLiteralCd = new ArrayList();
		this.aryLiteralNm = new ArrayList();
		this.aryValue1 = new ArrayList();
		this.aryValue2 = new ArrayList();
		this.arySortNo = new ArrayList();
		this.aryGroupCd = new ArrayList();
		
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
		this.aryBiko = new ArrayList();
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
		
	}
	
	/**
	 * XML�f�[�^�̐ݒ�
	 * @param xmlData : XML�f�[�^
	 */
	public void setLiteralData(XmlData xmlData) throws ExceptionBase{
		this.xdtData = xmlData;
		
		try{

			//���ڂ̃N���A
			this.aryLiteralCd.clear();
			this.aryLiteralNm.clear();
			this.aryValue1.clear();
			this.aryValue2.clear();
			this.arySortNo.clear();
			this.aryGroupCd.clear();
			
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
			this.aryBiko.clear();
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
			
			/**********************************************************
			 *�@���e�����f�[�^�i�[
			 *********************************************************/
			
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
					if ( recNm == "cd_category" ) {
						//�J�e�S���R�[�h
						this.setStrCategoryCd(recVal);
					} else if ( recNm == "cd_literal" ) {
						//���e�����R�[�h
						this.aryLiteralCd.add(recVal);
					} else if ( recNm == "nm_literal" ) {
						//���e������
						this.aryLiteralNm.add(recVal);
					} else if ( recNm == "value1" ) {
						//���e�����l1
						this.aryValue1.add(recVal);
					} else if ( recNm == "value2" ) {
						//���e�����l2
						this.aryValue2.add(recVal);
					} else if ( recNm == "no_sort" ) {
						//�\����
						this.arySortNo.add(recVal);
					} else if ( recNm == "cd_group" ) {
						//�O���[�v�R�[�h
						this.aryGroupCd.add(recVal);
					}
					
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
					else if ( recNm == "biko" ) {
						//�O���[�v�R�[�h
						this.aryBiko.add(recVal);
					}
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
					
				}
			}
			
		}catch(ExceptionBase e){
			throw e;
		
		}catch(Exception e){
			e.printStackTrace();
			
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���e�����f�[�^[" + strKinoId + "]�̏������Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
		}finally{
			
		}
	}
	
	/**
	 * ���e������ ����
	 * @return strCategoriCd : ���e�������̒l��Ԃ�
	 */
	public String selectLiteralNm(String strCd) {
		int index = -1;
		for(int i=0; i<aryLiteralCd.size(); i++){
			String chk = (String)aryLiteralCd.get(i);
			if(strCd.equals(chk)){
				index = i;
			}
		}
		if(index >= 0){
			return (String)aryLiteralNm.get(index);
		}else{
			return "";
		}
	}
	
	/**
	 * ���e�����l�P ����
	 * @return strCategoriCd : ���e�����l�P �̒l��Ԃ�
	 */
	public int selectLiteralVal1(int strCd) {
		int index = -1;
		for(int i=0; i<aryLiteralCd.size(); i++){
			int chk = Integer.parseInt((String)aryLiteralCd.get(i));
			if(strCd == chk){
				index = i;
			}
		}
		if(index >= 0){
			return Integer.parseInt((String)aryValue1.get(index));
		}else{
			return 0;
		}
	}

	/**
	 * ���e�����l�P ����
	 * @return strCategoriCd : ���e�����l�P �̒l��Ԃ�
	 */
	public String selectLiteralVal1(String strCd) {
		int index = -1;
		for(int i=0; i<aryLiteralCd.size(); i++){
			String chk = aryLiteralCd.get(i).toString();
			if(strCd.equals(chk)){
				index = i;
			}
		}
		if(index >= 0){
			return aryValue1.get(index).toString();
		}else{
			return "";
		}
	}

	/**
	 * ���e�����l�P ����
	 * @return strCategoriCd : ���e�����l�P �̒l��Ԃ�
	 */
	public String selectLiteralVal2(String strCd) {
		int index = -1;
		for(int i=0; i<aryLiteralCd.size(); i++){
			String chk = aryLiteralCd.get(i).toString();
			if(strCd.equals(chk)){
				index = i;
			}
		}
		if(index >= 0){
			return aryValue2.get(index).toString();
		}else{
			return "";
		}
	}
	
	/**
	 * ���e�����R�[�h ����
	 * @return strCategoriCd : �J�e�S���R�[�h�̒l��Ԃ�
	 */
	public String selectLiteralCd(int index) {
			return (String)aryLiteralCd.get(index);
	}
	
	
	/**
	 * ���e�����R�[�h ����
	 * @return strCategoriCd : �J�e�S���R�[�h�̒l��Ԃ�
	 */
	public String selectLiteralCd(String strNm) {
		int index = -1;
		for(int i=0; i<aryLiteralNm.size(); i++){
			String chk = aryLiteralNm.get(i).toString();
			if(strNm.equals(chk)){
				index = i;
			}
		}
		if(index >= 0){
			return aryLiteralCd.get(index).toString();
		}else{
			return "";
		}
	}
	

	/**
	 * �J�e�S���R�[�h �Q�b�^�[
	 * @return strCategoriCd : �J�e�S���R�[�h�̒l��Ԃ�
	 */
	public String getStrCategoryCd() {
		return strCategoryCd;
	}
	/**
	 * �J�e�S���R�[�h �Z�b�^�[
	 * @param strCategoriCd : �J�e�S���R�[�h�̒l���i�[����
	 */
	public void setStrCategoryCd(String _strCategoriCd) {
		this.strCategoryCd = _strCategoriCd;
	}
	
	/**
	 * ���e�����R�[�h �Q�b�^�[
	 * @return aryLiteralCd : ���e�����R�[�h�̒l��Ԃ�
	 */
	public ArrayList getAryLiteralCd() {
		return aryLiteralCd;
	}
	/**
	 * ���e�����R�[�h �Z�b�^�[
	 * @param _aryLiteralCd : ���e�����R�[�h�̒l���i�[����
	 */
	public void setAryLiteralCd(ArrayList _aryLiteralCd) {
		this.aryLiteralCd = _aryLiteralCd;
	}

	/**
	 * ���e������ �Q�b�^�[
	 * @return aryLiteralNm : ���e�������̒l��Ԃ�
	 */
	public ArrayList getAryLiteralNm() {
		return aryLiteralNm;
	}
	/**
	 * ���e������ �Z�b�^�[
	 * @param _aryLiteralNm : ���e�������̒l���i�[����
	 */
	public void setAryLiteralNm(ArrayList _aryLiteralNm) {
		this.aryLiteralNm = _aryLiteralNm;
	}

	/**
	 * ���e�����l1 �Q�b�^�[
	 * @return aryValue1 : ���e�����l1�̒l��Ԃ�
	 */
	public ArrayList getAryValue1() {
		return aryValue1;
	}
	/**
	 * ���e�����l1 �Z�b�^�[
	 * @param _aryValue1 : ���e�����l1�̒l���i�[����
	 */
	public void setAryValue1(ArrayList _aryValue1) {
		this.aryValue1 = _aryValue1;
	}

	/**
	 * ���e�����l2 �Q�b�^�[
	 * @return aryValue2 : ���e�����l2�̒l��Ԃ�
	 */
	public ArrayList getAryValue2() {
		return aryValue2;
	}
	/**
	 * ���e�����l2 �Z�b�^�[
	 * @param _aryValue2 : ���e�����l2�̒l���i�[����
	 */
	public void setAryValue2(ArrayList _aryValue2) {
		this.aryValue2 = _aryValue2;
	}

	/**
	 * �\���� �Q�b�^�[
	 * @return arySortNo : �\�����̒l��Ԃ�
	 */
	public ArrayList getArySortNo() {
		return arySortNo;
	}
	/**
	 * �\���� �Z�b�^�[
	 * @param _arySortNo : �\�����̒l���i�[����
	 */
	public void setArySortNo(ArrayList _arySortNo) {
		this.arySortNo = _arySortNo;
	}

	/**
	 * �O���[�v�R�[�h �Q�b�^�[
	 * @return aryGroupCd : �O���[�v�R�[�h�̒l��Ԃ�
	 */
	public ArrayList getAryGroupCd() {
		return aryGroupCd;
	}
	/**
	 * �O���[�v�R�[�h �Z�b�^�[
	 * @param _aryGroupCd : �O���[�v�R�[�h�̒l���i�[����
	 */
	public void setAryGroupCd(ArrayList _aryGroupCd) {
		this.aryGroupCd = _aryGroupCd;
	}

//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@start
	/**
	 * ���l �Q�b�^�[
	 * @return aryBiko : ���l�̒l��Ԃ�
	 */
	public ArrayList getAryBiko() {
		return aryBiko;
	}
	/**
	 * ���l �Z�b�^�[
	 * @param aryBiko : ���l�̒l���i�[����
	 */
	public void setAryBiko(ArrayList aryBiko) {
		this.aryBiko = aryBiko;
	}
//2011/05/30�@�yQP@10181_No.42_49_72�z�H���p�^�[������@TT.NISHIGAWA�@end
	
	/**
	 * ���e�����f�[�^�\��
	 */
	public void dispLiteralData(){
		System.out.println("\ncd_categori �F" + this.getStrCategoryCd());
		
		for ( int i = 0; i < this.getAryGroupCd().size(); i++ ) {
			System.out.println("--------" + i + "----------START");
			System.out.println("cd_literal :" + this.getAryLiteralCd().get(i).toString());
			System.out.println("nm_literal :" + this.getAryLiteralNm().get(i).toString());
			System.out.println("value1 :"+ this.getAryValue1().get(i).toString());
			System.out.println("value2 :"+ this.getAryValue2().get(i).toString());
			System.out.println("no_sort :"+ this.getArySortNo().get(i).toString());
			System.out.println("cd_group :"+ this.getAryGroupCd().get(i).toString());	
			System.out.println("biko :"+ this.getAryBiko().get(i).toString());	
			System.out.println("--------------------END");
		}
	}

}
