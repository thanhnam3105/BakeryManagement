package jp.co.blueflag.shisaquick.srv.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.co.blueflag.shisaquick.srv.base.ExcelSetting.enBindDirection;
import jp.co.blueflag.shisaquick.srv.base.ExcelSetting.enBindKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Excel�I�u�W�F�N�g
 *  ; �e���v���[�gExcel�t�@�C�������ɁA�ҏW�p��Excel�t�@�C���𐶐����AExcel�t�@�C�����Ǘ�����B
 * @author isono
 * @since  2009/04/14
 */
public class ExcelObject extends ObjectBase{

	//Excel���[�N�u�b�N�I�u�W�F�N�g
	private HSSFWorkbook workbook = null;
	//�ݒ���
	private ExcelSetting excelSetting = null;
	//���[��	�iLog�o�͎��Ɏg�p����A���[���j
	private String PrintName = "";
	//�J�����g�̐ݒ���
	private ExcelSetting.SettingRow CurrentSettingRow = null;

	/**
	 * �R���X�g���N�^
	 * @param strPrintName : ���[��	�iLog�o�͎��Ɏg�p����A���[���j
	 */
	public ExcelObject(String strPrintName) {
		super();
		//�A���[���������oPrintName�ɑޔ�����B
		PrintName = strPrintName;

	}
	/**
	 * �G�N�Z���u�b�N�𐶐�����
	 *  : �e���v���[�g��Ǎ��ݐV�����u�b�N�𐶐�����
	 * @param TemplateNm : �e���v���[�g�̃t�@�C����(FULL�p�X)
	 * @param del : �V�[�g�폜 true:�폜�@false:��\��
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	//MOD 2013/06/18 ogawa start        Setting�V�[�g�̍폜���\���ɕύX
	//�C���O�̃\�[�X
	//public void CreateNewBook(String TemplateNm)
	//�C����̃\�[�X
	public void CreateNewBook(String TemplateNm, boolean del)
	//MOD 2013/06/18 ogawa  end
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			//�@	�e���v���[�gExcel�t�@�C���̓Ǎ���
			//�e���v���[�g�̃t�@�C���������Book��޼ު�Ă𐶐�����
			//Book�I�u�W�F�N�g�������oworkbook�Ɋi�[����
			ReadTemplate(TemplateNm);

			//�A	�����oworkbook���uSetting�v�V�[�g�𒊏o����
			//�����o�uexcelSetting�v�Ɋi�[����
			ReadSettingSheet();

			//�B	�����oworkbook���uSetting�v�V�[�g���폜����
			//MOD 2013/06/18 ogawa start        Setting�V�[�g�̍폜���\���ɕύX
			//�C���O�̃\�[�X
			//RemovSetting();
			//�C����̃\�[�X
			RemovSetting(del);
			//MOD 2013/06/18 ogawa  end

		} catch (Exception e) {
			this.em.ThrowException(e, "�e���v���[�g��Ǎ��݂Ɏ��s���܂����B");

		} finally {

		}
	}
	/**
	 * �l�������N���ږ��ɊY������Cell�ɑ������B
	 * @param LinkNm : �����N���ږ�
	 * @param Value : �������l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void SetValue(String LinkNm, String Value)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		HSSFCell cell = null;

		try {
			//����Ώۂ̐ݒ�����擾
			SearchSttingRow(LinkNm);
			//���X�g�̏ꍇ�A�ő�s/��𒴂������`�F�b�N����iExceptionWaning : �ő�s/��𒴂����ꍇ�j
			ChekMaxRow();
			//�Ώۂ̃Z�����擾
			cell = SearchCell();
			//�Ώۂ̃Z���ɒl��������
			cell.setCellValue(Value);
			//�����̏����i������@�����X�g�̏ꍇ���̃Z���ɍ��W���ړ�����j
			MoveNextCell();

		} catch (Exception e) {
			this.em.ThrowException(e, "�l�̑���Ɏ��s���܂����B");

		} finally {

		}

	}
	/**
	 * �l�������N���ږ��ɊY������Cell�ɐݒ肷��
	 * @param LinkNm : �����N���ږ�
	 * @param Value : �������l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void SetValue(String LinkNm, double Value)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		HSSFCell cell = null;

		try {
			//����Ώۂ̐ݒ�����擾
			SearchSttingRow(LinkNm);
			//���X�g�̏ꍇ�A�ő�s/��𒴂������`�F�b�N����iExceptionWaning : �ő�s/��𒴂����ꍇ�j
			ChekMaxRow();
			//�Ώۂ̃Z�����擾
			cell = SearchCell();
			//�Ώۂ̃Z���ɒl��������
			cell.setCellValue(Value);
			//�����̏����i������@�����X�g�̏ꍇ���̃Z���ɍ��W���ړ�����j
			MoveNextCell();

		} catch (Exception e) {
			this.em.ThrowException(e, "�l�̑���Ɏ��s���܂����B");

		} finally {

		}

	}
	/**
	 * �l�������N���ږ��ɊY������Cell�ɐݒ肷��
	 * @param LinkNm : �����N���ږ�
	 * @param Value : �������l
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �ő�s/��𒴂����ꍇ
	 */
	public void SetValue(String LinkNm, boolean Value)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		HSSFCell cell = null;

		try {
			//����Ώۂ̐ݒ�����擾
			SearchSttingRow(LinkNm);
			//���X�g�̏ꍇ�A�ő�s/��𒴂������`�F�b�N����iExceptionWaning : �ő�s/��𒴂����ꍇ�j
			ChekMaxRow();
			//�Ώۂ̃Z�����擾
			cell = SearchCell();
			//�Ώۂ̃Z���ɒl��������
			cell.setCellValue(Value);
			//�����̏����i������@�����X�g�̏ꍇ���̃Z���ɍ��W���ړ�����j
			MoveNextCell();

		} catch (Exception e) {
			this.em.ThrowException(e, "�l�̑���Ɏ��s���܂����B");

		} finally {

		}

	}
	/**
	 * �G�N�Z���I�u�W�F�N�g���w��̃t�@�C�����Ńt�@�C���𐶐�����
	 * @param OutFileNm : �t�@�C����(FULL�p�X)
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void CreateExcelFile(String OutFileNm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		FileOutputStream out = null;

		try {
			//�t�@�C���X�g���[���𐶐�����
			out = new FileOutputStream(OutFileNm);

			//Excel�u�b�N���o�͂���
            workbook.write(out);

        //FileNotFoundException�����������ꍇ
		} catch(FileNotFoundException e) {
			this.em.ThrowException(ExceptionKind.���Exception
        			, "E000402"
        			, ""
        			, ""
        			, "");

		} catch (Exception e) {
			this.em.ThrowException(e, "Excel�t�@�C���̏o�͂Ɏ��s���܂����B");

		} finally {
            try {
    			//�t�@�C���X�g���[���̃N���[�Y  2009/07/24 null�̏ꍇ�A��O����������ׁAnull�`�F�b�N��ǉ�
            	if (out != null){
            		out.close();
            	}
            }catch(Exception e){
    			this.em.ThrowException(e, "");

            }

		}

	}

	/**
	 * �ő�s�𒴂������`�F�b�N����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning : �ő�s/��𒴂����ꍇ
	 */
	private void ChekMaxRow()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//���X�g�����肷��
			if(CurrentSettingRow.getExcelBindKind()
					== ExcelSetting.enBindKind.���X�g){
				//���X�g�̏ꍇ

				//���X�g�̕����𔻒肷��
				if (CurrentSettingRow.getExcelBindDirection()
						== ExcelSetting.enBindDirection.�c){
					//�c�����̏ꍇ

					//�ő�s�𒴂���������
					if (CurrentSettingRow.getMaxRow()
							< CurrentSettingRow.getRow()){
						//�������ꍇ

						//Exception���X���[����
						em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception,
								"W000402",
								PrintName,
								Integer.toString(CurrentSettingRow.getMaxRow()),
								"");

					}else{
						//�����Ȃ��ꍇ

						//�������Ȃ�

					}

				}else if(CurrentSettingRow.getExcelBindDirection()
						== ExcelSetting.enBindDirection.��){
					//�������̏ꍇ

					//�ő��𒴂���������
					if (CurrentSettingRow.getMaxRow()
							< CurrentSettingRow.getLine()){
						//�������ꍇ

						//Exception���X���[����
						em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception,
								"W000402",
								PrintName,
								Integer.toString(CurrentSettingRow.getMaxRow()),
								"");

					}else{
						//�����Ȃ��ꍇ

						//�������Ȃ�

					}

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "���X�g�̍ő�s���`�F�b�N�Ɏ��s���܂����B");

		} finally {

		}

	}
	/**
	 * ������@�����X�g�̏ꍇ�A���̃Z���ɍ��W���ړ�����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void MoveNextCell()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//������@�����X�g�����肷��
			if (CurrentSettingRow.getExcelBindKind() == ExcelSetting.enBindKind.���X�g){
				//���X�g�̏ꍇ

				//���X�g�����𔻒肷��
				if (CurrentSettingRow.getExcelBindDirection()
						== ExcelSetting.enBindDirection.�c){
					//�c�̏ꍇ
					CurrentSettingRow.setRow(CurrentSettingRow.getRow() + 1);

				}else{
					//���̏ꍇ
					CurrentSettingRow.setLine(CurrentSettingRow.getLine() + 1);

				}

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "���̃Z���Ɉړ������s���܂����B");

		} finally {

		}
	}
	/**
	 * �ݒ胊�X�g���ݒ������肵�����oCurrentSettingRow�Ɋi�[
	 * @param LinkNm : �����N���ږ�
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void SearchSttingRow(String LinkNm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			//�ݒ���������oCurrentSettingRow�Ɋi�[
			CurrentSettingRow = excelSetting.GetSetting(LinkNm);

		} catch (Exception e) {
			this.em.ThrowException(e, "�ݒ���̓���Ɏ��s���܂����B");

		} finally {

		}

	}
	/**
	 * ����Ώۂ̃Z���I�u�W�F�N�g�𒊏o����
	 * @return HSSFCell : ����Ώۂ̃Z���I�u�W�F�N�g
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private HSSFCell SearchCell()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		HSSFCell ret = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;

		try {
			//�Z���̓���
			//�V�[�g�̎擾
			sheet = workbook.getSheet(CurrentSettingRow.getSheet());
			//�s�̎擾
			row = sheet.getRow(CurrentSettingRow.getRow());
			if (row == null){
				row = sheet.createRow(CurrentSettingRow.getRow());
			}
			//�Z���̎擾
			ret = row.getCell(CurrentSettingRow.getLine());
			if (ret == null){
				ret = row.createCell(CurrentSettingRow.getLine());
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "����Ώۂ̃Z���I�u�W�F�N�g�𒊏o�Ɏ��s���܂����B");

		} finally {

		}

		return ret;
	}
	/**
	 * �e���v���[�g�G�N�Z���t�@�C���������o�uworkbook�v�ɓǂݍ��ށB
	 * @param TemplateNm : �t�@�C�����iFULL�p�X�j
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void ReadTemplate(String TemplateNm)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

        FileInputStream in = null;
        workbook = null;

		try {
			//�G�N�Z���e���v���[�g��ǂݍ���
			in = new FileInputStream(TemplateNm);
            POIFSFileSystem fs = new POIFSFileSystem(in);
            workbook = new HSSFWorkbook(fs);

		} catch (Exception e) {
			this.em.ThrowException(e, "�e���v���[�g�G�N�Z���t�@�C���̓ǂݍ��݂Ɏ��s���܂����B");

		} finally {
			try{
				//�C���v�b�g�X�g���[���̃N���[�Y
				in.close();

			}catch(Exception e){
				this.em.ThrowException(e, "");

			}

		}

    }
//MOD 2013/06/18 ogawa start        Setting�V�[�g�̍폜���\���ɕύX
//�C���O�̃\�[�X
	/**
	 * Setting�V�[�g���폜����B
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
//	private void RemovSetting()
//		    throws ExceptionSystem, ExceptionUser, ExceptionWaning{
//
//				try {
//					//Setting�V�[�g���폜����
//					workbook.removeSheetAt(workbook.getSheetIndex("Setting"));
//				} catch (Exception e) {
//					this.em.ThrowException(e, "Setting�V�[�g�̍폜�Ɏ��s���܂����B");
//
//				} finally {
//
//				}
//		    }
//�C����̃\�[�X
	/**
	 * Setting�V�[�g���폜����B
	 * @param del : �V�[�g�폜 true:�폜�@false:��\��
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void RemovSetting(boolean del)
		    throws ExceptionSystem, ExceptionUser, ExceptionWaning{

				try {
					if(del){
					//Setting�V�[�g���폜����
						workbook.removeSheetAt(workbook.getSheetIndex("Setting"));
					}else{
					//Setting�V�[�g���\���ɂ���
						workbook.setSheetHidden(workbook.getSheetIndex("Setting"), true);
					}
				} catch (Exception e) {
					this.em.ThrowException(e, "Setting�V�[�g�̍폜�Ɏ��s���܂����B");

				} finally {

				}
		    }

	//MOD 2013/06/18 ogawa  end
	/**
	 * Setting�V�[�g���ݒ�������o�uexcelSetting�v�ɑޔ�����B
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void ReadSettingSheet()
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		HSSFSheet sheet = null;
		HSSFRow row = null;

		//�������V�[�g
		String strSheet = "";
		//��������
		int intLine = -1;
		//�������s
		int intRow = -1;
		//������@
		enBindKind enmKind = null;
		//�������
		enBindDirection enmDirection = null;
		//���X�g�s��
		int maxRow = -1;
		//�����N���ږ�
		String LinkNm = "";

		try {

			//excelSetting�C���X�^���X����
			excelSetting = new ExcelSetting(PrintName);

			//Setting�V�[�g�𒊏o����
			sheet = workbook.getSheet("Setting");

			//Setting�V�[�g�̏���excelSetting�𐶐�����
			for(int i = 2; i < 65536; i++){
				//�ݒ�s�̒��o
				row = sheet.getRow(i);
				//�Ώۍs�̊m�F
				if(row.getCell(1).getRichStringCellValue().getString() == ""){
					break;
				}

				//�������V�[�g
				strSheet = row.getCell(1).getRichStringCellValue().getString();
				//��������
				intLine = (int) row.getCell(2).getNumericCellValue();
				//�������s
				intRow =  (int) row.getCell(3).getNumericCellValue();
				//������@
				if(row.getCell(4).getRichStringCellValue().getString().equals("���")){
					enmKind = ExcelSetting.enBindKind.���;

				}else if(row.getCell(4).getRichStringCellValue().getString().equals("���X�g")){
					enmKind = ExcelSetting.enBindKind.���X�g;

				}else{
					enmKind = null;

				}
				//�������
				if(row.getCell(5).getRichStringCellValue().getString().equals("�c")){
					enmDirection = ExcelSetting.enBindDirection.�c;

				}else if(row.getCell(5).getRichStringCellValue().getString().equals("��")){
					enmDirection = ExcelSetting.enBindDirection.��;

				}else{
					enmDirection = null;

				}
				//���X�g�s��
				if(enmKind == ExcelSetting.enBindKind.���X�g){

					if (enmDirection == ExcelSetting.enBindDirection.�c){
						maxRow = (int) row.getCell(6).getNumericCellValue()
						+ intRow -1;

					}else if(enmDirection == ExcelSetting.enBindDirection.��){
						maxRow = (int) row.getCell(6).getNumericCellValue()
						+ intLine -1;

					}

				}else{
					maxRow = 0;

				}
				//�����N���ږ�
				LinkNm = row.getCell(7).getRichStringCellValue().getString();

				//excelSetting�𐶐�
				excelSetting.AddSetting(
						strSheet,
						intLine,
						intRow,
						enmKind,
						enmDirection,
						maxRow,
						LinkNm);

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "Setting�V�[�g��蒊�o�����ݒ���̊i�[�Ɏ��s���܂����B");

		} finally {

		}

	}

}