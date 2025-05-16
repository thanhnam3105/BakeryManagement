package jp.co.blueflag.shisaquick.srv.base;

import java.util.ArrayList;

import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * Excel�ݒ���Ǘ��N���X
 * @author isono
 * @since  2009/04/14
 */
public class ExcelSetting extends ObjectBase {

	//Excel�ݒ胊�X�g
	protected ArrayList<SettingRow> ListSetting;
	//���[��	�iLog�o�͎��Ɏg�p����A���[���j
	String PrintName = "";
	
	/**
	 * Excel�ݒ���i������@�j
	 * @author isono
	 * @since  2009/04/14
	 */
	public static enum enBindKind{
		���,
		���X�g
	}
	/**
	 * Excel�ݒ���i��������j
	 * @author isono
	 * @since  2009/04/14
	 */
	public static enum enBindDirection{
		�c,
		��
	}
	/**
	 * Excel�ݒ���N���X
	 * @author isono
	 * @since  2009/04/14
	 */
	public class SettingRow extends ObjectBase{

		//�������V�[�g
		String strSheet = "";
		//��������
		int intLine = 0;
		//�������s
		int intRow = 0;
		//������@
		enBindKind ExcelBindKind = null;	
		//�������
		enBindDirection ExcelBindDirection = null;	
		//���X�g�s��
		int maxRow = 0;
		//�����N���ږ�		
		String LinkNm = "";	

		/**
		 * �R���X�g���N�^
		 */
		public SettingRow(){
			super();
			
		}
		/**
		 * �������V�[�g �Q�b�^�[
		 * @return String�F�������V�[�g��
		 */
		public String getSheet() {
			return strSheet;
		}
		/**
		 * �������� �Q�b�^�[
		 * @return int�F��������
		 */
		public int getLine() {
			return intLine;
		}
		/**
		 * �������s �Q�b�^�[
		 * @return int�F�������s
		 */
		public int getRow() {
			return intRow;
		}
		/**
		 * ������@ �Q�b�^�[
		 * @return enBindKind�F������@
		 */
		public enBindKind getExcelBindKind() {
			return ExcelBindKind;
		}
		/**
		 * ������� �Q�b�^�[
		 * @return enBindDirection�F�������
		 */
		public enBindDirection getExcelBindDirection() {
			return ExcelBindDirection;
		}
		/**
		 * ���X�g�ő�s�� �Q�b�^�[
		 * @return int�F���X�g�ő�s��
		 */
		public int getMaxRow() {
			return maxRow;
		}
		/**
		 * �����N���ږ� �Q�b�^�[
		 * @return String�F�����N���ږ�
		 */
		public String getLinkNm() {
			return LinkNm;
		}
		/**
		 * �������V�[�g�� �Z�b�^�[
		 * @param String : �������V�[�g��
		 */
		public void setSheet(String _strSheet) {
			strSheet = _strSheet;
		}
		/**
		 * �������� �Z�b�^�[
		 * @param int : ��������
		 */
		public void setLine(int _intLine) {
			intLine = _intLine;
		}
		/**
		 * �������s �Z�b�^�[
		 * @param int : �������s
		 */
		public void setRow(int _intRow) {
			intRow = _intRow;
		}
		/**
		 * ������@ �Z�b�^�[
		 * @param enBindKind : ������@
		 */
		public void setExcelBindKind(enBindKind _ExcelBindKind) {
			ExcelBindKind = _ExcelBindKind;
		}
		/**
		 * ������� �Z�b�^�[
		 * @param enBindDirection : �������
		 */
		public void setExcelBindDirection(enBindDirection _ExcelBindDirection) {
			ExcelBindDirection = _ExcelBindDirection;
		}
		/**
		 * ���X�g�s�� �Z�b�^�[
		 * @param int : ���X�g�s��
		 */
		public void setMaxRow(int _maxRow) {
			maxRow = _maxRow;
		}
		/**
		 * �����N���ږ� �Z�b�^�[
		 * @param _cd_team : �����N���ږ�
		 */
		public void setLinkNm(String _LinkNm) {
			LinkNm = _LinkNm;
		}

	}

	/**
	 * �R���X�g���N�^
	 */
	public ExcelSetting(
			String strPrintName
	){

		//�A���[���������oPrintName�ɑޔ�����B
		PrintName = strPrintName;
		//�B�C���X�^���X����
		ListSetting = new ArrayList<SettingRow>();
		
	}
	/**
	 * Excel�ݒ��ǉ�����B
	 * @param strSheet�F�������V�[�g
	 * @param intLine�F��������
	 * @param intRow�F�������s
	 * @param enmKind�F������@
	 * @param enmDirection�F���X�g����
	 * @param maxRow�F���X�g�s��
	 * @param LinkNm�F�����N���ږ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void AddSetting(
			String strSheet, 
			int intLine, 
			int intRow, 
			enBindKind enmKind, 
			enBindDirection enmDirection, 
			int maxRow, 
			String LinkNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{

			SettingRow setting = new SettingRow();
			
			//�������V�[�g
			setting.setSheet(strSheet);
			//��������
			setting.setLine(intLine);
			//�������s
			setting.setRow(intRow);
			//������@
			setting.setExcelBindKind(enmKind);
			//�������
			setting.setExcelBindDirection(enmDirection);
			//���X�g�s��
			setting.setMaxRow(maxRow);
			//�����N���ږ�
			setting.setLinkNm(LinkNm);
			
			//���X�g�ɒǉ�����
			ListSetting.add(setting);

		}catch(Exception e){
			em.ThrowException(e, "Excel�ݒ�̒ǉ��Ɏ��s���܂����B");
			
		}finally{
			
		}
		
	}
	/**
	 * �ݒ�̎擾
	 * @param SttingIndex : �ݒ�̃C���f�b�N�X
	 * @return SettingRow : �ݒ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public SettingRow GetSetting(int SttingIndex) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		SettingRow ret = null;

		try{
			//�@	���X�g���ݒ�C���f�b�N�X�ɊY������uSettingRow�v��ԋp����
			ret = ListSetting.get(SttingIndex);

		}catch(Exception e){
			em.ThrowException(e, "�ݒ�̎擾�Ɏ��s���܂����B");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �ݒ�̎擾
	 * @param LinkNm : �����N���ږ�
	 * @return SettingRow : �ݒ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public SettingRow GetSetting(String LinkNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		SettingRow ret = null;

		try{
			int ix = -1;
			//�@	�����N���ږ��������ɂ��āAGetLinkNmNo���Ăяo���C���f�b�N�X���擾����B
			ix = GetLinkNmNo(LinkNm);
			//�A	�C���f�b�N�X�������ɂ��āAGetSetting���Ăяo���B
			ret = ListSetting.get(ix);

		}catch(Exception e){
			em.ThrowException(e, "�ݒ�̎擾�Ɏ��s���܂����B");
			
		}finally{
			
		}
		return ret;
		
	}
	/**
	 * �����N���ږ��ɊY������A�ݒ�̃C���f�b�N�X���擾����B
	 * @param LinkNm : �����N���ږ�
	 * @return int : �ݒ�̃C���f�b�N�X
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private int GetLinkNmNo(String LinkNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		int ret = -1;

		try{
			for (int i = 0; i < ListSetting.size(); i++){

//				if(ListSetting.get(i).getLinkNm() == LinkNm){
				if(ListSetting.get(i).getLinkNm().equals(LinkNm)){
					ret = i;
					break;

				}
				
			}
			
		}catch(Exception e){
			em.ThrowException(e, "�����N���ږ��ɊY������A�ݒ�̃C���f�b�N�X�̎擾�Ɏ��s���܂����B");
			
		}finally{
			
		}
		return ret;
		
	}
	
}