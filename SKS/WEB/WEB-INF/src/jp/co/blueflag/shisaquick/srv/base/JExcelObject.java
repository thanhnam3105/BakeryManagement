package jp.co.blueflag.shisaquick.srv.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.TimeZone;

import jp.co.blueflag.shisaquick.srv.base.ExcelSetting.enBindDirection;
import jp.co.blueflag.shisaquick.srv.base.ExcelSetting.enBindKind;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * JExcel�I�u�W�F�N�g
 *  ; �e���v���[�gExcel�t�@�C�������ɁA�ҏW�p��Excel�t�@�C���𐶐����AExcel�t�@�C�����Ǘ�����B
 * @author isono
 * @since  2009/04/14
 */
public class JExcelObject extends ObjectBase{

	//Excel�e���v���[�g���[�N�u�b�N�I�u�W�F�N�g	
	protected jxl.Workbook workbook = null;
	//�o��Excel���[�N�u�b�N�I�u�W�F�N�g
	protected jxl.write.WritableWorkbook writebook = null;

	
	private int counter = 0;
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
	public JExcelObject(String strPrintName) {
		super();
		//�A���[���������oPrintName�ɑޔ�����B
		PrintName = strPrintName;

	}
	/**
	 * �G�N�Z���u�b�N�𐶐�����
	 *  : �e���v���[�g��Ǎ��ݐV�����u�b�N�𐶐�����
	 * @param TemplateNm : �e���v���[�g�̃t�@�C����(FULL�p�X)
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void CreateNewBook(String TemplateNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		try {

			//�@	�e���v���[�gExcel�t�@�C���̓Ǎ���	
			//�e���v���[�g�̃t�@�C���������Book��޼ު�Ă𐶐�����	
			//Book�I�u�W�F�N�g�������oworkbook�Ɋi�[����
			ReadTemplate(TemplateNm);
			
			//�A	�����oworkbook���uSetting�v�V�[�g�𒊏o����	
			//�����o�uexcelSetting�v�Ɋi�[����
			ReadSettingSheet();
			
			/*************** 2012/04/09 ************
			 * ���L�̗��R���Setting�V�[�g�͔�\���Ƃ��č폜���Ȃ��B
			 * �EWritableSheet�łȂ���sheet���폜�ł��Ȃ��B
			 * �EWritableWorkbook��Sheet���폜�����excel������B
			 */
//			//�B	�����oworkbook���uSetting�v�V�[�g���폜����
//			RemovSetting();
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�e���v���[�g��Ǎ��݂Ɏ��s���܂����B");
			this.close();

		} finally {
		}
	}
	/**
	 * �G�N�Z���I�u�W�F�N�g���w��̃t�@�C�����Ńt�@�C���𐶐���
	 * �e���v���[�g���R�s�[����Setting�̃V�[�g���폜����B
	 * @param OutFileNm : �t�@�C����(FULL�p�X)
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	public void CreateExcelFile(String OutFileNm) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			// System.gc()�u�K�x�[�W�R���N�V�����v�̎��s��OFF�ɐݒ� 
			WorkbookSettings settings = new WorkbookSettings(); 
			settings.setGCDisabled(true);
			//�쐬&�e���v���[�g�R�s�[
			writebook = jxl.Workbook.createWorkbook(new File(OutFileNm), workbook,settings);
			
        //FileNotFoundException�����������ꍇ
		} catch(FileNotFoundException e) {
			this.em.ThrowException(ExceptionKind.���Exception
        			, "E000402"
        			, ""
        			, ""
        			, "");
						
		} catch (Exception e) {
			this.em.ThrowException(e, "Excel�t�@�C���̏o�͂Ɏ��s���܂����B");
			this.close();

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
		counter ++;
		try {
			//����Ώۂ̐ݒ�����擾
			SearchSttingRow(LinkNm);
			//���X�g�̏ꍇ�A�ő�s/��𒴂������`�F�b�N����iExceptionWaning : �ő�s/��𒴂����ꍇ�j
			ChekMaxRow();
			//�����̃Z���쐬
			jxl.write.Label label = new jxl.write.Label(CurrentSettingRow.getLine(),CurrentSettingRow.getRow(),Value);
			//�Ώۂ̃Z�����擾
			//�V�[�g�̎擾
			jxl.write.WritableSheet sheet = writebook.getSheet(CurrentSettingRow.getSheet());
			jxl.Cell cell = sheet.getWritableCell(CurrentSettingRow.getLine(), CurrentSettingRow.getRow());// <- �����ݐ悪��̏ꍇ��Blank���Ԃ�
			if(cell != null){
				// ���̃Z���̃t�H�[�}�b�g����V�����Z���ɐݒ肷��
				if (cell.getCellFeatures() != null) {
					label.setCellFeatures(new jxl.write.WritableCellFeatures(cell.getCellFeatures()));
				}
				if (cell.getCellFormat() != null) {
					label.setCellFormat(cell.getCellFormat());
				}
				
			}
			
			// �Z�����㏑��
			sheet.addCell(label);
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
		counter ++;

		try {
			//����Ώۂ̐ݒ�����擾
			SearchSttingRow(LinkNm);
			//���X�g�̏ꍇ�A�ő�s/��𒴂������`�F�b�N����iExceptionWaning : �ő�s/��𒴂����ꍇ�j
			ChekMaxRow();
			//�����̃Z���쐬
			jxl.write.Number number = new jxl.write.Number(CurrentSettingRow.getLine(),CurrentSettingRow.getRow(),Value);
			//�Ώۂ̃Z�����擾
			//�V�[�g�̎擾
			jxl.write.WritableSheet sheet = writebook.getSheet(CurrentSettingRow.getSheet());
			jxl.Cell cell = sheet.getWritableCell(CurrentSettingRow.getLine(), CurrentSettingRow.getRow());// <- �����ݐ悪��̏ꍇ��Blank���Ԃ�
			if(cell != null){
				// ���̃Z���̃t�H�[�}�b�g����V�����Z���ɐݒ肷��
				if (cell.getCellFeatures() != null) {
					number.setCellFeatures(new jxl.write.WritableCellFeatures(cell.getCellFeatures()));
				}
				if (cell.getCellFormat() != null) {
					number.setCellFormat(cell.getCellFormat());
				}
			}
			// �Z�����㏑��
			sheet.addCell(number);
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

		try {
			//����Ώۂ̐ݒ�����擾
			SearchSttingRow(LinkNm);
			//���X�g�̏ꍇ�A�ő�s/��𒴂������`�F�b�N����iExceptionWaning : �ő�s/��𒴂����ꍇ�j
			ChekMaxRow();
			//�����̃Z���쐬
			jxl.write.Boolean bool = new jxl.write.Boolean(CurrentSettingRow.getLine(),CurrentSettingRow.getRow(),Value);
			//�Ώۂ̃Z�����擾
			//�V�[�g�̎擾
			jxl.write.WritableSheet sheet = writebook.getSheet(CurrentSettingRow.getSheet());
			jxl.Cell cell = sheet.getWritableCell(CurrentSettingRow.getLine(), CurrentSettingRow.getRow());// <- �����ݐ悪��̏ꍇ��Blank���Ԃ�
			if(cell != null){
				// ���̃Z���̃t�H�[�}�b�g����V�����Z���ɐݒ肷��
				if (cell.getCellFeatures() != null) {
					bool.setCellFeatures(new jxl.write.WritableCellFeatures(cell.getCellFeatures()));
				}
				if (cell.getCellFormat() != null) {
					bool.setCellFormat(cell.getCellFormat());
				}
			}
			// �Z�����㏑��
			sheet.addCell(bool);
			//�����̏����i������@�����X�g�̏ꍇ���̃Z���ɍ��W���ړ�����j
			MoveNextCell();
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�l�̑���Ɏ��s���܂����B");

		} finally {
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
			this.close();

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
			this.close();

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
			this.close();

		} finally {
		}
		
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

		workbook = null;

		try {
			//�G�N�Z���e���v���[�g��ǂݍ���
			workbook = Workbook.getWorkbook(new File(TemplateNm));
		} catch (Exception e) {
			this.em.ThrowException(e, "�e���v���[�g�G�N�Z���t�@�C���̓ǂݍ��݂Ɏ��s���܂����B");
			this.close();

		} finally {
		}
        
    }
	/**
	 * Setting�V�[�g���ݒ�������o�uexcelSetting�v�ɑޔ�����B
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private void ReadSettingSheet() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		Sheet sheet = null;
		Cell[] rows = null;

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
				rows = sheet.getRow(i);
				//�Ώۍs�̊m�F
				if(rows[1].getContents() == ""){
					break;
				}
				
				//�������V�[�g
				strSheet = rows[1].getContents(); 
				//��������
				intLine = Integer.parseInt(rows[2].getContents()); 
				//�������s
				intRow =  Integer.parseInt(rows[3].getContents());  
				//������@
				if("���".equals(rows[4].getContents())){
					enmKind = ExcelSetting.enBindKind.���;
					
				}else if("���X�g".equals(rows[4].getContents())){
					enmKind = ExcelSetting.enBindKind.���X�g;
					
				}else{
					enmKind = null;
					
				}
				//�������
				if("�c".equals(rows[5].getContents())){
					enmDirection = ExcelSetting.enBindDirection.�c;
					
				}else if("��".equals(rows[5].getContents())){
					enmDirection = ExcelSetting.enBindDirection.��;
					
				}else{
					enmDirection = null;
					
				}
				//���X�g�s��
				if(enmKind == ExcelSetting.enBindKind.���X�g){
					
					if (enmDirection == ExcelSetting.enBindDirection.�c){
						maxRow = Integer.parseInt(rows[6].getContents())
						+ intRow -1; 
						
					}else if(enmDirection == ExcelSetting.enBindDirection.��){
						maxRow = Integer.parseInt(rows[6].getContents())
						+ intLine -1; 
						
					}
					
				}else{
					maxRow = 0; 

				}
				//�����N���ږ�
				LinkNm = rows[7].getContents(); 

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
			this.close();

		} finally {
		}

	}
	/**
	 * Excelobject���t�@�C���ɏo�͂���
	 * @param FNameOption : �t�@�C�����I�v�V�����i�t�@�C����_�t�@�C�����I�v�V����_�N����_�����b�j
	 * @return�@String : �_�E�����[�h�t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void ExcelWrite() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			writebook.write();
		} catch (Exception e) {
			em.ThrowException(e, "");
		} finally {
			this.close();
		}
	}
	/**
	 * �g�p�I�u�W�F�N�g�̊J��
	 * @param FNameOption : �t�@�C�����I�v�V�����i�t�@�C����_�t�@�C�����I�v�V����_�N����_�����b�j
	 * @return�@String : �_�E�����[�h�t�@�C����
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	protected void close() 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try{
			if(writebook != null){
				writebook.close();
			}
			writebook = null;
			if(workbook != null){
				workbook.close();
			}
		} catch (Exception e) {
			
		} finally {
			writebook = null;
			workbook = null;
				
		}
	}
	public Object getValue(Cell cell) {
		  if (cell == null) {
		    return null;
		  }
		  CellType type = cell.getType();
		  if(CellType.DATE == type || CellType.DATE_FORMULA == type) {
		      Date date = ((DateCell) cell).getDate();
		      // Excel����ǂݎ�����l�ɑ΂��āA�f�t�H���g�^�C���]�[����GMT�Ƃ̍�����]������K�v�������ׁB
		      date.setTime(date.getTime() - TimeZone.getDefault().getRawOffset());
		      return date;
		  } else if(CellType.NUMBER == type || CellType.NUMBER_FORMULA == type) {
		      return new BigDecimal(((NumberCell) cell).getValue());
		  } else if(CellType.BOOLEAN == type || CellType.BOOLEAN_FORMULA == type) {
		      return Boolean.valueOf(((BooleanCell) cell).getValue());
		  } else if(CellType.LABEL == type || CellType.STRING_FORMULA == type) {
		      String s = cell.getContents();
		    if ("".equals(s)) {
		      s = null;
		    }
		    return s;
		  } else {
		      return cell.getContents();
		  }
		}
	
}