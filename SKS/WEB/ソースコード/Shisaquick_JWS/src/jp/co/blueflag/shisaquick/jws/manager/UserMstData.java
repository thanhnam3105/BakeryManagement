package jp.co.blueflag.shisaquick.jws.manager;

import java.math.BigDecimal;
import java.util.ArrayList;

import jp.co.blueflag.shisaquick.jws.base.*;
import jp.co.blueflag.shisaquick.jws.common.*;


/**
 * 
 * ���[�U�}�X�^�f�[�^�ێ�
 * : ���[�U�Ɋւ�������Ǘ�����
 */
public class UserMstData extends DataBase {
	
	private BigDecimal dciUserid;				//���[�UID
	private String strUsernm;			//����
	private int intKaishscd;			//���CD
	private String strKaishanm;			//��Ж�
	private int intBushocd;				//����CD
	private String strBushonm;			//������
	//2010/02/25 NAKAMURA ADD START---------------
	private String strHaitaKaishanm;	//�r����Ж�
	private String strHaitaBushonm;		//�r��������
	private String strHaitaShimei;		//�r������
	//2010/02/25 NAKAMURA ADD END-----------------
	private int intGroupcd;				//�O���[�vCD
	private String strGroupnm;			//�O���[�v��
	private int intTeamcd;				//�`�[��CD
	private String strTeamnm;			//�`�[����
	private String strYakucd;			//��ECD
	private String strYakunm;			//��E��
	private ArrayList arySeizoData;		//�����S����Ѓf�[�^
	private ArrayList aryAuthData;		//�����f�[�^
	private XmlData xdtData;			//XML�f�[�^
	private ExceptionBase ex;			//�G���[�n���h�����O
	
	/**
	 * �R���X�g���N�^
	 */
	public UserMstData(){
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();	
	}
	
	/**
	 * ���[�U��� �Z�b�^�[
	 * @param xdtSetXml : XML�f�[�^
	 */
	public void setUserData(XmlData xdtSetXml) throws ExceptionBase{
		
		try{
			this.dciUserid = new BigDecimal(0);
			this.strUsernm = "";
			this.intKaishscd = 0;
			this.strKaishanm = "";
			this.intBushocd = 0;
			this.strBushonm = "";
			this.intGroupcd = 0;
			this.strGroupnm = "";
			this.intTeamcd = 0;
			this.strTeamnm = "";
			this.strYakucd = "";
			this.strYakunm = "";
			this.arySeizoData = new ArrayList();
			this.aryAuthData = new ArrayList();
			this.xdtData = xdtSetXml;
			this.ex = new ExceptionBase();
			
			/**********************************************************
			 *�@���[�U�f�[�^�i�[
			 *********************************************************/
			//�@User�f�[�^�@�\ID�i�[
			String strKinoId = "USERINFO";
			
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
				//�@�����i�[�z��
				String[] strAuth = new String[3];
				
				//���[�U�f�[�^�֊i�[
				for(int j=0; j<recData.size(); j++){
					
					//�@���ږ��擾
					String recNm = ((String[])recData.get(j))[1];
					//�@���ڒl�擾
					String recVal = ((String[])recData.get(j))[2];
					
					/*****************User�f�[�^�֒l�Z�b�g*********************/
					//�@���[�UID
					 if(recNm == "id_user"){
						this.setDciUserid(new BigDecimal(recVal));
						
					//�@���[�U��
					}if(recNm == "nm_user"){
						this.setStrUsernm(recVal);
					
					//�@��ЃR�[�h
					}if(recNm == "cd_kaisha"){
						this.setIntKaishscd(Integer.parseInt(recVal));
						
					//�@��Ж�
					}if(recNm == "nm_kaisha"){
						this.setStrKaishanm(recVal);
						
					//�@�����R�[�h
					}if(recNm == "cd_busho"){
						this.setIntBushocd(Integer.parseInt(recVal));
						
					//�@������
					}if(recNm == "nm_busho"){
						this.setStrBushonm(recVal);
						
					//�@�O���[�v�R�[�h
					}if(recNm == "cd_group"){
						this.setIntGroupcd(Integer.parseInt(recVal));
						
					//�@�O���[�v��
					}if(recNm == "nm_group"){
						this.setStrGroupnm(recVal);
						
					//�@�`�[���R�[�h
					}if(recNm == "cd_team"){
						this.setIntTeamcd(Integer.parseInt(recVal));
						
					//�@�`�[����
					}if(recNm == "nm_team"){
						this.setStrTeamnm(recVal);
						
					//�@��E�R�[�h
					}if(recNm == "cd_literal"){
						this.setStrYakucd(recVal);
						
					//�@��E��
					}if(recNm == "nm_literal"){
						this.setStrYakunm(recVal);
						
					//�@���ID�i�����j
					}if(recNm == "id_gamen"){
						strAuth[0] = recVal;
						
					//�@�@�\�h�c�i�����j
					}if(recNm == "id_kino"){
						strAuth[1] = recVal;
						
					//�@�Q�Ɖ\�f�[�^�h�c�i�����j
					}if(recNm == "id_data"){
						strAuth[2] = recVal;
						
					}
				}
				//�����f�[�^�ǉ�
				aryAuthData.add(strAuth);
			}
		}catch(ExceptionBase e){
			throw e;
		
		}catch(Exception e){
			e.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("���[�U�}�X�^�̏������Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}
		
	}
	
	/**
	 * ���[�U��� �Z�b�^�[
	 * @param xdtSetXml : XML�f�[�^
	 */
	public void setSeizoData(XmlData xdtSetXml) throws ExceptionBase{
		
		/**********************************************************
		 *�@�S��������Ѓf�[�^�i�[
		 *********************************************************/
		//�@�S�������f�[�^�@�\ID�i�[
		String strKinoId = "SA210";
		
		//�S�̔z��擾
		ArrayList seizoData = xdtSetXml.GetAryTag(strKinoId);
		
		//�@�\�z��擾
		ArrayList kinoData = (ArrayList)seizoData.get(0);
		
		//�e�[�u���z��擾
		ArrayList tableData = (ArrayList)kinoData.get(1);
		
		//���R�[�h�擾
		for(int k=1; k<tableData.size(); k++){
			//�@�P���R�[�h�擾
			ArrayList recData = ((ArrayList)((ArrayList)tableData.get(k)).get(0));
			//�@�S��������Њi�[�z��
			String[] strSeizo = new String[3];
			
			//�S�������f�[�^�֊i�[
			for(int l=0; l<recData.size(); l++){
				
				//�@���ږ��擾
				String recNm = ((String[])recData.get(l))[1];
				//�@���ڒl�擾
				String recVal = ((String[])recData.get(l))[2];
				
				/*****************�S�������f�[�^�֒l�Z�b�g*********************/
				//�@�S��������ЃR�[�h
				 if(recNm == "cd_kaisha"){
					strSeizo[0] = recVal;
					
				//�@�S��������Ж�
				}if(recNm == "nm_kaisha"){
					strSeizo[1] = recVal;
					
				}
			}
			//�@�S��������Ѓf�[�^�ǉ�
			arySeizoData.add(strSeizo);
		}
	}

	/**
	 * �r�����[�U��� �Z�b�^�[
	 * @param xdtSetXml : XML�f�[�^
	 */	
	public void setHaitaUserData(XmlData xdtSetXml) throws ExceptionBase{

		try{
			this.strHaitaKaishanm = "";
			this.strHaitaBushonm = "";
			this.strHaitaShimei = "";
			this.xdtData = xdtSetXml;
			this.ex = new ExceptionBase();
			
			/**********************************************************
			 *�@�r�����[�U�f�[�^�i�[
			 *********************************************************/
			//�@�r��User�f�[�^�@�\ID�i�[
			String strKinoId = "SA480";
			
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
					
					/*****************�r��User�f�[�^�֒l�Z�b�g*********************/
					//�@�r����Ж�
					 if(recNm == "nm_kaisha_haita"){
						this.setStrHaitaKaishanm(recVal);
						
					//�@�r��������
					}if(recNm == "nm_busho_haita"){
						this.setStrHaitaBushonm(recVal);
					
					//�@�r������
					}if(recNm == "nm_user_haita"){
						this.setStrHaitaShimei(recVal);
						
					}
				}
			}
		}catch(ExceptionBase e){
			throw e;
		
		}catch(Exception e){
			e.printStackTrace();
			//�G���[�ݒ�
			ex = new ExceptionBase();
			ex.setStrErrCd("");
			ex.setStrErrmsg("�r�����̏������Ɏ��s���܂���");
			ex.setStrErrShori(this.getClass().getName());
			ex.setStrMsgNo("");
			ex.setStrSystemMsg(e.getMessage());
			throw ex;
			
		}finally{
			
		}

	}

	/**
	 * ���[�UID �Q�b�^�[
	 * @return dciUserid : ���[�UID�̒l��Ԃ�
	 */
	public BigDecimal getDciUserid() {
		return dciUserid;
	}
	/**
	 * ���[�UID �Z�b�^�[
	 * @param _dciUserid : ���[�UID�̒l���i�[����
	 */
	public void setDciUserid(BigDecimal _dciUserid) {
		this.dciUserid = _dciUserid;
	}

	/**
	 * �����f�[�^�@�Q�b�^�[
	 * @return intAuth : ����CD�̒l��Ԃ�
	 */
	public ArrayList getAryAuthData() {
		return aryAuthData;
	}
	/**
	 * �����f�[�^�@�Z�b�^�[
	 * @param _intAuth : ����CD�̒l���i�[����
	 */
	public void setAryAuthData(ArrayList _aryAuthData) {
		this.aryAuthData = _aryAuthData;
	}
	/**
	 * ���� �Q�b�^�[
	 * @return strUsernm : �����̒l��Ԃ�
	 */
	public String getStrUsernm() {
		return strUsernm;
	}
	/**
	 * ���� �Z�b�^�[
	 * @param _strUsernm : �����̒l���i�[����
	 */
	public void setStrUsernm(String _strUsernm) {
		this.strUsernm = _strUsernm;
	}

	/**
	 * ���CD �Q�b�^�[
	 * @return intKaishscd : ���CD�̒l��Ԃ�
	 */
	public int getIntKaishscd() {
		return intKaishscd;
	}

	/**
	 * ���CD �Z�b�^�[
	 * @param _intKaishscd : ���CD�̒l���i�[����
	 */
	public void setIntKaishscd(int _intKaishscd) {
		this.intKaishscd = _intKaishscd;
	}

	/**
	 * ��Ж� �Q�b�^�[
	 * @return strKaishanm : ��Ж��̒l��Ԃ�
	 */
	public String getStrKaishanm() {
		return strKaishanm;
	}

	/**
	 * ��Ж� �Z�b�^�[
	 * @param _strKaishanm : ��Ж��̒l���i�[����
	 */
	public void setStrKaishanm(String _strKaishanm) {
		this.strKaishanm = _strKaishanm;
	}

	/**
	 * ����CD �Q�b�^�[
	 * @return intBushocd : ����CD�̒l��Ԃ�
	 */
	public int getIntBushocd() {
		return intBushocd;
	}

	/**
	 * ����CD �Z�b�^�[
	 * @param _intBushocd : ����CD�̒l���i�[����
	 */
	public void setIntBushocd(int _intBushocd) {
		this.intBushocd = _intBushocd;
	}

	/**
	 * ������ �Q�b�^�[
	 * @return strBushonm : �������̒l��Ԃ�
	 */
	public String getStrBushonm() {
		return strBushonm;
	}

	/**
	 * ������ �Z�b�^�[
	 * @param _strBushonm : �������̒l���i�[����
	 */
	public void setStrBushonm(String _strBushonm) {
		this.strBushonm = _strBushonm;
	}
	
	//2010/02/25 NAKAMURA ADD START------------------
	/**
	 * �r����Ж� �Q�b�^�[
	 * @return strHaitaKaishanm : �r����Ж��̒l��Ԃ�
	 */
	public String getStrHaitaKaishanm() {
		return strHaitaKaishanm;
	}

	/**
	 * �r����Ж� �Z�b�^�[
	 * @param _strHaitaKaishanm : �r����Ж��̒l���i�[����
	 */
	public void setStrHaitaKaishanm(String _strHaitaKaishanm) {
		this.strHaitaKaishanm = _strHaitaKaishanm;
	}	
	
	/**
	 * �r�������� �Q�b�^�[
	 * @return strHaitaBushonm : �r���������̒l��Ԃ�
	 */
	public String getStrHaitaBushonm() {
		return strHaitaBushonm;
	}

	/**
	 * �r�������� �Z�b�^�[
	 * @param _strHaitaBushonm : �r���������̒l���i�[����
	 */
	public void setStrHaitaBushonm(String _strHaitaBushonm) {
		this.strHaitaBushonm = _strHaitaBushonm;
	}	
	
	/**
	 * �r������ �Q�b�^�[
	 * @return strHaitaShimei : �r�������̒l��Ԃ�
	 */
	public String getStrHaitaShimei() {
		return strHaitaShimei;
	}

	/**
	 * �r������ �Z�b�^�[
	 * @param _strHaitaShimei : �r�������̒l���i�[����
	 */
	public void setStrHaitaShimei(String _strHaitaShimei) {
		this.strHaitaShimei = _strHaitaShimei;
	}		
	//2010/02/25 NAKAMURA ADD END--------------------
	

	/**
	 * �O���[�vCD �Q�b�^�[
	 * @return intGroupcd : �O���[�vCD�̒l��Ԃ�
	 */
	public int getIntGroupcd() {
		return intGroupcd;
	}

	/**
	 * �O���[�vCD �Z�b�^�[
	 * @param _intGroupcd : �O���[�vCD�̒l���i�[����
	 */
	public void setIntGroupcd(int _intGroupcd) {
		this.intGroupcd = _intGroupcd;
	}

	/**
	 * �O���[�v�� �Q�b�^�[
	 * @return intGroupnm : �O���[�v���̒l��Ԃ�
	 */
	public String getStrGroupnm() {
		return strGroupnm;
	}

	/**
	 * �O���[�v�� �Z�b�^�[
	 * @param _intGroupnm : �O���[�v���̒l���i�[����
	 */
	public void setStrGroupnm(String _strGroupnm) {
		this.strGroupnm = _strGroupnm;
	}

	/**
	 * �`�[��CD �Q�b�^�[
	 * @return intTeamcd : �`�[��CD�̒l��Ԃ�
	 */
	public int getIntTeamcd() {
		return intTeamcd;
	}

	/**
	 * �`�[��CD �Z�b�^�[
	 * @param _intTeamcd : �`�[��CD�̒l���i�[����
	 */
	public void setIntTeamcd(int _intTeamcd) {
		this.intTeamcd = _intTeamcd;
	}

	/**
	 * �`�[���� �Q�b�^�[
	 * @return strTeamnm : �`�[�����̒l��Ԃ�
	 */
	public String getStrTeamnm() {
		return strTeamnm;
	}

	/**
	 * �`�[���� �Z�b�^�[
	 * @param _strTeamnm : �`�[�����̒l���i�[����
	 */
	public void setStrTeamnm(String _strTeamnm) {
		this.strTeamnm = _strTeamnm;
	}

	/**
	 * ��ECD �Q�b�^�[
	 * @return strYakucd : ��ECD�̒l��Ԃ�
	 */
	public String getStrYakucd() {
		return strYakucd;
	}

	/**
	 * ��ECD �Z�b�^�[
	 * @param _strYakucd : ��ECD�̒l���i�[����
	 */
	public void setStrYakucd(String _strYakucd) {
		this.strYakucd = _strYakucd;
	}

	/**
	 * ��E�� �Q�b�^�[
	 * @return strYakunm : ��E���̒l��Ԃ�
	 */
	public String getStrYakunm() {
		return strYakunm;
	}

	/**
	 * ��E�� �Z�b�^�[
	 * @param _strYakunm : ��E���̒l���i�[����
	 */
	public void setStrYakunm(String _strYakunm) {
		this.strYakunm = _strYakunm;
	}

	/**
	 * �����S����Ѓf�[�^ �Z�b�^�[
	 * @param _arySeizoData : �����S����Ѓf�[�^�̒l���i�[����
	 */
	public void setArySeizoData(ArrayList _arySeizoData) {
		this.arySeizoData = _arySeizoData;
	}

	/**
	 * �����S����Ѓf�[�^ �Q�b�^�[
	 * @return arySeizoData : �����S����Ѓf�[�^�̒l��Ԃ�
	 */
	public ArrayList getArySeizoData() {
		return arySeizoData;
	}
	
	/**
	 * ���[�U�f�[�^�\��
	 */
	public void dsipUserData(){
		System.out.println("");
		System.out.println("���[�UID�F" + this.getDciUserid());
		System.out.println("���[�U���F" + this.getStrUsernm());
		System.out.println("��ЃR�[�h�F" + this.getIntKaishscd());
		System.out.println("��Ж��F" + this.getStrKaishanm());
		System.out.println("�����R�[�h�F" + this.getIntBushocd());
		System.out.println("�������F" + this.getStrBushonm());
		System.out.println("�O���[�v�R�[�h�F" + this.getIntGroupcd());
		System.out.println("�O���[�v���F" + this.getStrGroupnm());
		System.out.println("�`�[���R�[�h�F" + this.getIntTeamcd());
		System.out.println("�`�[�����F" + this.getStrTeamnm());
		System.out.println("��E�R�[�h�F" + this.getStrYakucd());
		System.out.println("��E���F" + this.getStrYakunm());
		
		for(int i=0;i<this.arySeizoData.size();i++){
			String[] strDispSeizo = (String[])arySeizoData.get(i);
			System.out.println("�S��������ЃR�[�h�F" + strDispSeizo[0]);
			System.out.println("�S��������Ж��F" + strDispSeizo[1]);
		}
		
		for(int i=0;i<this.aryAuthData.size();i++){
			String[] strDispAuth = (String[])aryAuthData.get(i);
			System.out.println("���ID�F" + strDispAuth[0]);
			System.out.println("�@�\ID�F" + strDispAuth[1]);
			System.out.println("�f�[�^ID�F" + strDispAuth[2]);
		}
	}
}
