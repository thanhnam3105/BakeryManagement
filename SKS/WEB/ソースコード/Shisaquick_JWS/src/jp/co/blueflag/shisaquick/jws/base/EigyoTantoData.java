package jp.co.blueflag.shisaquick.jws.base;

import java.math.BigDecimal;

import jp.co.blueflag.shisaquick.jws.common.*;

/**
 * 
 * �c�ƒS���҃f�[�^
 *  : �c�ƒS���҃f�[�^��ێ�����
 * 
 * @author TT.Nishigawa
 * @since  2011/02/10
 */
public class EigyoTantoData extends DataBase{

	private String no_gyo = "";				//�sNo
	private String id_user = "";				//���[�U�[ID
	private String nm_user = "";			//���[�U�[����
	private String cd_kaisha = "";			//���CD
	private String nm_kaisha = "";		//��Ж���
	private String cd_busho = "";			//����CD
	private String nm_busho = "";			//��������
	private String honbukengen = "";		//�{������
	private String id_josi = "";				//��iID
	private String nm_josi = "";			//��i��
	private int intMaxRow = 0;		//������
	private int intListRowMax = 0;	//�œ��\������
	
	/**
	 * �R���X�g���N�^
	 * @param xdtSetXml : XML�f�[�^
	 */
	public EigyoTantoData() {
		//�X�[�p�[�N���X�̃R���X�g���N�^�Ăяo��
		super();
		
		this.id_user = null;
		this.nm_user = null;
		this.cd_kaisha = null;
		this.nm_kaisha = null;
		this.cd_busho = null;
		this.nm_busho = null;
		this.honbukengen = null;
		this.id_josi = null;
		this.nm_josi = null;
	}
	
	public String getNo_gyo() {
		return no_gyo;
	}

	public void setNo_gyo(String no_gyo) {
		this.no_gyo = no_gyo;
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
	 * �{������ �Q�b�^�[
	 * @return honbukengen : �{��������Ԃ�
	 */
	public String getHonbukengen() {
		return honbukengen;
	}
	/**
	 * �{������ �Z�b�^�[
	 * @param honbukengen : �{���������i�[����
	 */
	public void setHonbukengen(String honbukengen) {
		this.honbukengen = honbukengen;
	}
	
	/**
	 * ��iID �Q�b�^�[
	 * @return id_josi : ��iID��Ԃ�
	 */
	public String getId_josi() {
		return id_josi;
	}
	/**
	 * ��iID �Z�b�^�[
	 * @param id_josi : ��iID���i�[����
	 */
	public void setId_josi(String id_josi) {
		this.id_josi = id_josi;
	}
	
	/**
	 * ��i�� �Q�b�^�[
	 * @return nm_josi : ��iID��Ԃ�
	 */
	public String getNm_josi() {
		return nm_josi;
	}
	/**
	 * ��i�� �Z�b�^�[
	 * @param nm_josi : ��iID���i�[����
	 */
	public void setNm_josi(String nm_josi) {
		this.nm_josi = nm_josi;
	}
	
	/**
	 * ������ �Q�b�^�[
	 * @return intMaxRow : ��������Ԃ�
	 */
	public int getIntMaxRow() {
		return intMaxRow;
	}
	/**
	 * ������ �Z�b�^�[
	 * @param intMaxRow : ���������i�[����
	 */
	public void setIntMaxRow(int intMaxRow) {
		this.intMaxRow = intMaxRow;
	}

	/**
	 * �œ��\������ �Q�b�^�[
	 * @return intMaxRow : �œ��\��������Ԃ�
	 */
	public int getIntListRowMax() {
		return intListRowMax;
	}
	/**
	 * �œ��\������ �Z�b�^�[
	 * @param intMaxRow : �œ��\���������i�[����
	 */
	public void setIntListRowMax(int intListRowMax) {
		this.intListRowMax = intListRowMax;
	}
	

}
