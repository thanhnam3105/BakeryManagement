package jp.co.blueflag.shisaquick.srv.base;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpSession;

import jp.co.blueflag.shisaquick.srv.base.SearchBaseDao;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ConstManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ConstManager.Category;

/**
 * 
 * UserInfoData
 *  : ���[�U�[��{���f�[�^(�Z�b�V����)��ێ�����
 * 
 * @author TT.furuta
 * @since  2009/03/28
 */
public class UserInfoData extends SearchBaseDao{

	private String systemversion = "";	//�V�X�e���o�[�W����
	private String id_user = "";			//���[�U�[ID
	private String nm_user = "";			//���[�U�[����
	private String cd_kaisha = "";		//���CD
	private String nm_kaisha = "";		//��Ж���
	private String cd_busho = "";		//����CD
	private String nm_busho = "";		//��������
	private String cd_group = "";		//�O���[�vCD
	private String nm_group = "";		//�O���[�v��
	private String cd_team = "";			//�`�[��CD
	private String nm_team = "";			//�`�[����
	private String cd_literal = "";		//���e����CD
	private String nm_literal = "";		//���e������
	private ArrayList<String> id_gamen;		//���ID
	private ArrayList<String> id_kino;		//�@�\ID
	private ArrayList<String> id_data;		//�f�[�^ID
	private ArrayList<String> cd_tantokaisha;	//�����S����ЃR�[�h
	private ArrayList<String> nm_tantokaisha;	//�����S����Ж�
	
	//JSP�N������
	private ArrayList<String> movement_condition;
	
	/**
	 * �V�X�e���o�[�W���� �Q�b�^�[
	 * @return systemversion : �V�X�e���o�[�W������Ԃ�
	 */
	public String getSystemversion() {
		return systemversion;
	}
	/**
	 * �V�X�e���o�[�W���� �Z�b�^�[
	 * @param _systemversion : �V�X�e���o�[�W�������i�[����
	 */
	public void setSystemversion(String _systemversion) {
		systemversion = _systemversion;
	}
	/**
	 * ���[�U�[ID �Q�b�^�[
	 * @return id_user : ���[�U�[ID��Ԃ�
	 */
	public String getId_user() {
		return id_user;
	}
	/**
	 * ���[�U�[ID �Z�b�^�[
	 * @param _id_user : ���[�U�[ID���i�[����
	 */
	public void setId_user(String _id_user) {
		id_user = _id_user;
	}
	/**
	 * ���[�U�[���� �Q�b�^�[
	 * @return nm_user : ���[�U�[���̂�Ԃ�
	 */
	public String getNm_user() {
		return nm_user;
	}
	/**
	 * ���[�U�[���� �Z�b�^�[
	 * @param _nm_user : ���[�U�[���̂��i�[����
	 */
	public void setNm_user(String _nm_user) {
		nm_user = _nm_user;
	}
	/**
	 * ��ЃR�[�h �Q�b�^�[
	 * @return cd_kaisha : ���CD��Ԃ�
	 */
	public String getCd_kaisha() {
		return cd_kaisha;
	}
	/**
	 * ��ЃR�[�h �Z�b�^�[
	 * @param _cd_kaisha : ���CD���i�[����
	 */
	public void setCd_kaisha(String _cd_kaisha) {
		cd_kaisha = _cd_kaisha;
	}
	/**
	 * ��Ж��� �Q�b�^�[
	 * @return nm_kaisha : ��Ж��̂�Ԃ�
	 */
	public String getNm_kaisha() {
		return nm_kaisha;
	}
	/**
	 * ��Ж��� �Z�b�^�[
	 * @param _nm_kaisha : ��Ж��̂��i�[����
	 */
	public void setNm_kaisha(String _nm_kaisha) {
		nm_kaisha = _nm_kaisha;
	}
	/**
	 * �����R�[�h �Q�b�^�[
	 * @return cd_busho : ����CD��Ԃ�
	 */
	public String getCd_busho() {
		return cd_busho;
	}
	/**
	 * �����R�[�h �Z�b�^�[
	 * @param _cd_busho : ����CD���i�[����
	 */
	public void setCd_busho(String _cd_busho) {
		cd_busho = _cd_busho;
	}
	/**
	 * �������� �Q�b�^�[
	 * @return nm_busho : �������̂�Ԃ�
	 */
	public String getNm_busho() {
		return nm_busho;
	}
	/**
	 * �������� �Z�b�^�[
	 * @param _nm_busho : �������̂��i�[����
	 */
	public void setNm_busho(String _nm_busho) {
		nm_busho = _nm_busho;
	}
	/**
	 * �O���[�v�R�[�h �Q�b�^�[
	 * @return cd_group : �O���[�vCD��Ԃ�
	 */
	public String getCd_group() {
		return cd_group;
	}
	/**
	 * �O���[�v�R�[�h �Z�b�^�[
	 * @param _cd_group : �O���[�vCD���i�[����
	 */
	public void setCd_group(String _cd_group) {
		cd_group = _cd_group;
	}
	/**
	 * �O���[�v�� �Q�b�^�[
	 * @return nm_group : �O���[�v����Ԃ�
	 */
	public String getNm_group() {
		return nm_group;
	}
	/**
	 * �O���[�v�� �Z�b�^�[
	 * @param _nm_group : �O���[�v�����i�[����
	 */
	public void setNm_group(String _nm_group) {
		nm_group = _nm_group;
	}
	/**
	 * �`�[���R�[�h �Q�b�^�[
	 * @return cd_team : �`�[��CD��Ԃ�
	 */
	public String getCd_team() {
		return cd_team;
	}
	/**
	 * �`�[���R�[�h �Z�b�^�[
	 * @param _cd_team : �`�[��CD���i�[����
	 */
	public void setCd_team(String _cd_team) {
		cd_team = _cd_team;
	}
	/**
	 * �`�[������ �Q�b�^�[
	 * @return nm_team : �`�[�����̂�Ԃ�
	 */
	public String getNm_team() {
		return nm_team;
	}
	/**
	 * �`�[������ �Z�b�^�[
	 * @param _nm_team : �`�[�����̂��i�[����
	 */
	public void setNm_team(String _nm_team) {
		nm_team = _nm_team;
	}
	/**
	 * ���e�����R�[�h �Q�b�^�[
	 * @return cd_literal : ���e�����R�[�h��Ԃ�
	 */
	public String getCd_literal() {
		return cd_literal;
	}
	/**
	 * ���e�����R�[�h �Z�b�^�[
	 * @param _cd_literal : ���e�����R�[�h���i�[����
	 */
	public void setCd_literal(String _cd_literal) {
		cd_literal = _cd_literal;
	}
	/**
	 * ���e�������� �Q�b�^�[
	 * @return nm_literal : ���e��������Ԃ�
	 */
	public String getNm_literal() {
		return nm_literal;
	}
	/**
	 * ���e�������� �Z�b�^�[
	 * @param _nm_literal : ���e���������i�[����
	 */
	public void setNm_literal(String _nm_literal) {
		nm_literal = _nm_literal;
	}
	/**
	 * ���ID �Q�b�^�[
	 * @return id_gamen : ���ID��Ԃ�
	 */
	public ArrayList<?> getId_gamen() {
		return id_gamen;
	}
	/**
	 * ���ID �Z�b�^�[
	 * @param _id_gamen : ���ID���i�[����
	 */
	public void setId_gamen(ArrayList<String> _id_gamen) {
		id_gamen = _id_gamen;
	}
	/**
	 * �@�\ID �Q�b�^�[
	 * @return id_kino : �@�\ID��Ԃ�
	 */
	public ArrayList<?> getId_kino() {
		return id_kino;
	}
	/**
	 * �@�\ID �Z�b�^�[
	 * @param _id_kino : �@�\ID���i�[����
	 */
	public void setId_kino(ArrayList<String> _id_kino) {
		id_kino = _id_kino;
	}
	/**
	 * �f�[�^ID �Q�b�^�[
	 * @return id_data : �f�[�^ID��Ԃ�
	 */
	public ArrayList<?> getId_data() {
		return id_data;
	}
	/**
	 * �f�[�^ID �Z�b�^�[
	 * @param _id_data : �f�[�^ID���i�[����
	 */
	public void setId_data(ArrayList<String> _id_data) {
		id_data = _id_data;
	}
	/**
	 * jsp�N������ �Q�b�^�[
	 * @return id_data : �f�[�^ID��Ԃ�
	 */
	public ArrayList<?> getMovement_condition() {
		return movement_condition;
	}
	/**
	 * jsp�N������ �Z�b�^�[
	 * @param _id_data : �f�[�^ID���i�[����
	 */
	public void setMovement_condition(ArrayList<String> _Movement_condition) {
		movement_condition = _Movement_condition;
	}
	/**
	 * �����S����ЃR�[�h �Q�b�^�[
	 * @return id_data : �����S����ЃR�[�h��Ԃ�
	 */
	public ArrayList<?> getCd_tantokaisha() {
		return cd_tantokaisha;
	}
	/**
	 * �����S����ЃR�[�h �Z�b�^�[
	 * @param _id_data : �����S����ЃR�[�h���i�[����
	 */
	public void setCd_tantokaisha(ArrayList<String> _cd_tantokaisha) {
		cd_tantokaisha = _cd_tantokaisha;
	}
	/**
	 * �����S����Ж� �Q�b�^�[
	 * @return id_data : �����S����Ж���Ԃ�
	 */
	public ArrayList<?> getNm_tantokaisha() {
		return nm_tantokaisha;
	}
	/**
	 * �����S����Ж� �Z�b�^�[
	 * @param _id_data : �����S����Ж����i�[����
	 */
	public void setNm_tantokaisha(ArrayList<String> _nm_tantokaisha) {
		nm_tantokaisha = _nm_tantokaisha;
	}

	//�Z�b�V�����N���X
	private HttpSession session;
	//���[�U�[��񃊃X�g
	private List<?> lstUserInfo;
	//���[�U�[�������
	private List<?> lstUserKengenInfo;

	/**
	 * �R���X�g���N�^
	 *  : �Z�b�V�����`�F�b�N�R���X�g���N�^
	 * @param session : �Z�b�V����
	 */
	public UserInfoData(HttpSession _session) {
		//���׽�̃R���X�g���N�^
		super(DBCategory.DB1);
		
		//�N���X�ϐ��̃N���A
		session = null;
		lstUserInfo = null;
		lstUserKengenInfo = null;
		
		//�Z�b�V�������������o�Ɋi�[
		session = _session;

	}

	/**
	 * ���[�U�[���Q�ƃ��C������
	 *  : �����敪�ɉ����āA�Z�b�V��������UserInfoData�̍X�V���s��
	 * @param strShoriKbn : �����敪
	 * @param strUserID�F���[�U�[ID
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void CreatUserInfo(String strShoriKbn, String strUserId) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			
			//�����敪�𔻒肷��
			if (strShoriKbn.equals("1")){
				//�����敪1�F�ʏ펞
				//Session���UserInfoData�𐶐�����

				//�P�j�Z�b�V������胆�[�U�[���/�������擾��UserInfioData�Ɋi�[
				GetUserInfo_Session();
	
			} else if (strShoriKbn.equals("2")){
				//�����敪2�F���O�C��
				//DB���UserInfoData�𐶐�����
				//UserInfoData�̏���Session�Ɋi�[����

				//�P�jDB��胆�[�U�[���/�������擾��UserInfioData�Ɋi�[
				GetUserInfo_DB(strUserId);
				//�Q�jUserInfioData���Session�Ɋi�[
				SetUserInfo_Session();
				
			//�����敪�FJWS��
			} else if (strShoriKbn.equals("3")){
				//�����敪3�Fjws�p
				//DB���UserInfoData�𐶐�����

				//�P�jDB��胆�[�U�[���/�������擾��UserInfioData�Ɋi�[
				GetUserInfo_DB(strUserId);
				
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "���[�U�[���Q�ƂɎ��s���܂����B");
		} finally {
			
		}
	}
	/**
	 * �C���X�^���X�ϐ��̃��X�g��j������B
	 */
	public void removeList(){
		//���ID		
		removeList(id_gamen);
		id_gamen = null;
		//�@�\ID
		removeList(id_kino);
		id_kino = null;
		//�f�[�^ID
		removeList(id_data);
		id_data = null;
		//�����S����ЃR�[�h
		removeList(cd_tantokaisha);
		cd_tantokaisha = null;
		//�����S����Ж�
		removeList(nm_tantokaisha);
		nm_tantokaisha = null;
		
	}

	/**
	 * �Z�b�V�������i�[
	 *  : UserInfoData�̏���Session�Ɋi�[����B
	 *  @throws ExceptionSystem
	 *  @throws ExceptionUser
	 *  @throws ExceptionWaning
	 */
	private void SetUserInfo_Session() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			
			//�V�X�e���o�[�W�����ݒ�
			session.setAttribute("systemversion", ConstManager.getConstValue(Category.�ݒ�, "SYSTEM_VERSION"));
			//���[�U�[�h�c�ݒ�
			session.setAttribute("id_user", id_user);
			//���[�U�[���ݒ�
			session.setAttribute("nm_user", nm_user);
			//��ЃR�[�h�ݒ�
			session.setAttribute("cd_kaisha", cd_kaisha);
			//��Ж��ݒ�
			session.setAttribute("nm_kaisha", nm_kaisha);
			//�����R�[�h�ݒ�
			session.setAttribute("cd_busho", cd_busho);
			//�������ݒ�
			session.setAttribute("nm_busho", nm_busho);
			//�O���[�v�R�[�h�ݒ�
			session.setAttribute("cd_group", cd_group);
			//�O���[�v���ݒ�
			session.setAttribute("nm_group", nm_group);
			//�`�[���R�[�h�ݒ�
			session.setAttribute("cd_team", cd_team);
			//�`�[�����ݒ�
			session.setAttribute("nm_team", nm_team);
			//��E�R�[�h�ݒ�
			session.setAttribute("cd_literal", cd_literal);
			//��E���ݒ�
			session.setAttribute("nm_literal", nm_literal);
			//�����S����ЃR�[�h�ݒ�
			session.setAttribute("cd_tantokaisha", ListToString(cd_tantokaisha, ":::"));
			//�����S����Ж��ݒ�
			session.setAttribute("nm_tantokaisha", ListToString(nm_tantokaisha, ":::"));
			//���ID�ݒ�
			session.setAttribute("id_gamen", ListToString(id_gamen, ":::"));
			//�@�\ID
			session.setAttribute("id_kino", ListToString(id_kino, ":::"));
			//�f�[�^ID�ݒ�
			session.setAttribute("id_data", ListToString(id_data, ":::"));

						
		} catch (Exception e) {
			em.ThrowException(e, "���[�U�[���Z�b�g�Ɏ��s���܂����B");

		} finally {
			
		}

	}
	/**
	 * ���[�U�[��񌟍�
	 *  : Session��胆�[�U�[�����擾����B
	 *  @throws ExceptionSystem
	 *  @throws ExceptionUser
	 *  @throws ExceptionWaning
	 */
	private void GetUserInfo_Session() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		try {
			//�V�X�e���o�[�W�����ݒ�
			setSystemversion(session.getAttribute("systemversion").toString());
			//���[�U�[�h�c�ݒ�
			setId_user(session.getAttribute("id_user").toString());
			//���[�U�[���ݒ�
			setNm_user(session.getAttribute("nm_user").toString());
			//��ЃR�[�h�ݒ�
			setCd_kaisha(session.getAttribute("cd_kaisha").toString());
			//��Ж��ݒ�
			setNm_kaisha(session.getAttribute("nm_kaisha").toString());
			//�����R�[�h�ݒ�
			setCd_busho(session.getAttribute("cd_busho").toString());
			//�������ݒ�
			setNm_busho(session.getAttribute("nm_busho").toString());
			//�O���[�v�R�[�h�ݒ�
			setCd_group(session.getAttribute("cd_group").toString());
			//�O���[�v���ݒ�
			setNm_group(session.getAttribute("nm_group").toString());
			//�`�[���R�[�h�ݒ�
			setCd_team(session.getAttribute("cd_team").toString());
			//�`�[�����ݒ�
			setNm_team(session.getAttribute("nm_team").toString());
			//��E�R�[�h�ݒ�
			setCd_literal(session.getAttribute("cd_literal").toString());
			//��E���ݒ�
			setNm_literal(session.getAttribute("nm_literal").toString());
			//�����S����ЃR�[�h�ݒ�
			setCd_tantokaisha(StringToList(session.getAttribute("cd_tantokaisha").toString(), ":::"));		
			//�����S����Ж��ݒ�
			setNm_tantokaisha(StringToList(session.getAttribute("nm_tantokaisha").toString(), ":::"));		
			//���ID�ݒ�
			setId_gamen(StringToList(session.getAttribute("id_gamen").toString(), ":::"));		
			//�@�\ID
			setId_kino(StringToList(session.getAttribute("id_kino").toString(), ":::"));		
			//�f�[�^ID�ݒ�
			setId_data(StringToList(session.getAttribute("id_data").toString(), ":::"));
			
		} catch (Exception e) {
			em.ThrowException(e, "���[�U�[��{���Q�ƂɎ��s���܂����B");

		} finally {
			
		}

	}
	/**
	 * ���[�U�[��񌟍�
	 *  : DB��胆�[�U�[�����擾����B
	 * @param strUserId
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void GetUserInfo_DB(String strUserId) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		ArrayList<String> arrTantoKaishaCd = null;
		ArrayList<String> arrTantoKaishaNm  = null;
		ArrayList<String> arrGamenId = null;
		ArrayList<String> arrKinoId  = null;
		ArrayList<String> arrDataId  = null;
		
		try{
			//���[�U�[��{����DB���擾
			GetUserInfo_DB_Kihon(strUserId);
			//���[�U�[��������DB���擾
			GetUserInfo_DB_Kengen(strUserId);
			
			//UserInfoData�Ƀ��[�U�[��{���/���������i�[����
			arrTantoKaishaCd = new ArrayList<String>();
			arrTantoKaishaNm  = new ArrayList<String>();
			arrGamenId = new ArrayList<String>();
			arrKinoId  = new ArrayList<String>();
			arrDataId  = new ArrayList<String>();

			//���X�g�v�f�����[�v
			for (int i=0;i<lstUserInfo.size();i++){

				Object[] items = (Object[]) lstUserInfo.get(i);

				int index = 0;
				//�V�X�e���o�[�W�����ݒ�
				setSystemversion(ConstManager.getConstValue(Category.�ݒ�, "SYSTEM_VERSION"));
				//���[�U�[�h�c�ݒ�
				setId_user(items[index++].toString());
				//���[�U�[���ݒ�
				setNm_user(items[index++].toString());
				//��ЃR�[�h�ݒ�
				setCd_kaisha(items[index++].toString());
				//��Ж��ݒ�
				setNm_kaisha(items[index++].toString());
				//�����R�[�h�ݒ�
				setCd_busho(items[index++].toString());
				//�������ݒ�
				setNm_busho(items[index++].toString());
				//�O���[�v�R�[�h�ݒ�
				setCd_group(items[index++].toString());
				//�O���[�v���ݒ�
				setNm_group(items[index++].toString());
				//�`�[���R�[�h�ݒ�
				setCd_team(items[index++].toString());
				//�`�[�����ݒ�
				setNm_team(items[index++].toString());
				//��E�R�[�h�ݒ�
				setCd_literal(items[index++].toString());
				//��E���ݒ�
				setNm_literal(items[index++].toString());
				//�����S����ЃR�[�h�ݒ�
				arrTantoKaishaCd.add(items[index++].toString());		
				//�����S����Ж��ݒ�
				arrTantoKaishaNm.add(items[index++].toString());

			}
			
			//�����S����ЃR�[�h�ݒ�
			setCd_tantokaisha(arrTantoKaishaCd);		
			//�����S����Ж��ݒ�
			setNm_tantokaisha(arrTantoKaishaNm);

			//���X�g�v�f�����[�v
			for (int i=0;i<lstUserKengenInfo.size();i++){

				Object[] items = (Object[]) lstUserKengenInfo.get(i);

				int index = 0;
				//���ID�ݒ�
				arrGamenId.add(items[index++].toString());		
				//�@�\ID
				arrKinoId.add(items[index++].toString());
				//�f�[�^ID�ݒ�
				arrDataId.add(items[index++].toString());
				
			}

			//���ID�ݒ�
			setId_gamen(arrGamenId);		
			//�@�\ID
			setId_kino(arrKinoId);
			//�f�[�^ID�ݒ�
			setId_data(arrDataId);
			
		} catch (Exception e) {
			em.ThrowException(e, "���[�U�[��{���Q�ƂɎ��s���܂����B");

		} finally {
			//DB�Z�b�V�����̃N���[�Y
			Close();

		}
		
	}
	/**
	 * ���[�U�[��{��񌟍�
	 *  : DB��胆�[�U�[��{������������B
	 *  @param strUserID�F���[�U�[ID
	 *  @throws ExceptionSystem
	 *  @throws ExceptionUser
	 *  @throws ExceptionWaning
	 */
	private void GetUserInfo_DB_Kihon(String strUserId) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();
		lstUserInfo = null;

		//DB�Z�b�V�����𐶐�
		//SearchBaseDao searchDB = new SearchBaseDao(DBCategory.DB1);
		
		try {
			//�B�FSQL�쐬
			strSQL.append("SELECT DISTINCT ");
			strSQL.append("A.id_user AS id_user, ");
			strSQL.append("A.nm_user AS nm_user, ");
			strSQL.append("ISNULL(B.cd_kaisha, 0) as cd_kaisha, ");
			strSQL.append("ISNULL(B.nm_kaisha, '') as nm_kaisha, ");
			strSQL.append("ISNULL(B.cd_busho, 0) as cd_busho, ");
			strSQL.append("ISNULL(B.nm_busho, '') as nm_busho, ");
			strSQL.append("ISNULL(C.cd_group, 0) as cd_group, ");
			strSQL.append("ISNULL(C.nm_group, '') as nm_group, ");
			strSQL.append("ISNULL(D.cd_team, 0) as cd_team, ");
			strSQL.append("ISNULL(D.nm_team, '') as nm_team, ");
			strSQL.append("ISNULL(E.cd_literal, 0) as cd_literal, ");
			strSQL.append("ISNULL(E.nm_literal, '') as nm_literal, ");
			strSQL.append("ISNULL(F.cd_tantokaisha, 0) as cd_tantokaisha, ");
			strSQL.append("ISNULL(G.nm_kaisha, '') as nm_tantokaisha ");
			strSQL.append("FROM ma_user A  ");
			strSQL.append("LEFT JOIN ma_busho B ON A.cd_kaisha = B.cd_Kaisha AND A.cd_busho = B.cd_busho ");
			strSQL.append("LEFT JOIN ma_group C ON A.cd_group = C.cd_group ");
			strSQL.append("LEFT JOIN ma_team D ON A.cd_group = D.cd_group AND A.cd_team = D.cd_team ");
			strSQL.append("LEFT JOIN ma_literal E ON A.cd_yakushoku = E.cd_literal AND 'K_yakusyoku' = E.cd_category ");
			strSQL.append("LEFT JOIN ma_tantokaisya F ON A.id_user = F.id_user ");
			strSQL.append("LEFT JOIN ma_busho G ON F.cd_tantokaisha = G.cd_kaisha ");
			strSQL.append("WHERE A.id_user = "); 
			strSQL.append(strUserId); 
			
			//�C�FDB����
			lstUserInfo = dbSearch(strSQL.toString());

		} catch (Exception e) {
			em.ThrowException(e, "���[�U�[��{���Q�ƂɎ��s���܂����B");
			
		} finally {
			//DB�Z�b�V�����̃N���[�Y
//			searchDB.Close();
//			searchDB = null;
			
		}

	}
	/**
	 * ���[�U�[������񌟍�
	 *  : DB��胆�[�U�[����������������B
	 *  @param strUserID�F���[�U�[ID
	 *  @throws ExceptionSystem
	 *  @throws ExceptionUser
	 *  @throws ExceptionWaning
	 */
	private void GetUserInfo_DB_Kengen(String strUserId) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strSQL = new StringBuffer();
		lstUserKengenInfo = null;

		//DB�Z�b�V�����𐶐�
		//SearchBaseDao searchDB = new SearchBaseDao(DBCategory.DB1);
		
		try {
			//�B�FSQL�쐬
			strSQL.append("SELECT ");
			strSQL.append("ISNULL(B.id_gamen, 0) as id_gamen, ");
			strSQL.append("ISNULL(B.id_kino, 0) as id_kino, ");
			strSQL.append("ISNULL(B.id_data, 0) as id_data ");
			strSQL.append("FROM ma_user A  ");
			strSQL.append("LEFT JOIN ma_kinou B ON A.cd_kengen = B.cd_kengen ");
			strSQL.append("WHERE A.id_user = "); 
			strSQL.append(strUserId); 

			//�C�FDB����
			lstUserKengenInfo = dbSearch(strSQL.toString());

		} catch (Exception e) {
			em.ThrowException(e, "���[�U�[�������Q�ƂɎ��s���܂����B");

		} finally {
			//DB�Z�b�V�����̃N���[�Y
//			searchDB.Close();
//			searchDB = null;
			
		}
		
	}
	/**
	 * ���[�U�[�ۗ̕L����f�[�^ID��ԋp����B
	 * @param gamenID	�F���ID
	 * @return�@String	�F�f�[�^ID
	 */
	public String getID_data(String gamenID){
		String ret = "";
		
		//�����擾
		for (int i=0;i < this.getId_gamen().size();i++){
			
			if (this.getId_gamen().get(i).toString().equals(gamenID)){
				//�f�[�^ID��ݒ�
				ret = this.getId_data().get(i).toString();
			}
		}
		
		return ret;
		
	}
	
	/**
	 * ���[�U�[�ۗ̕L����@�\ID��ԋp����B
	 * @param gamenID	�F���ID
	 * @return�@String	�F�@�\ID
	 */
	public String getId_kino(String gamenID){
		String ret = "";
		
		//�����擾
		for (int i=0;i < this.getId_gamen().size();i++){
			
			if (this.getId_gamen().get(i).toString().equals(gamenID)){
				//�f�[�^ID��ݒ�
				ret = this.getId_kino().get(i).toString();
			}
		}
		
		return ret;
		
	}

}
