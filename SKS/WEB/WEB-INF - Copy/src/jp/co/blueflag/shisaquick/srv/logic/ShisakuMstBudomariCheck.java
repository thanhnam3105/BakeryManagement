package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * �}�X�^�����`�F�b�N�c�a����
 * @author nishigawa
 * @since  2010/10/15
 */
public class ShisakuMstBudomariCheck extends LogicBase{
	
	/**
	 * �}�X�^�����`�F�b�N�c�a����
	 * : �C���X�^���X����
	 */
	public ShisakuMstBudomariCheck() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * ���앪�̓f�[�^�m�F�F�������͏��擾SQL�쐬
	 *  : �������͏����擾����SQL���쐬�B
	 * @param reqData : ���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @return ���X�|���X�f�[�^�i�@�\�j
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public RequestResponsKindBean ExecLogic(
			RequestResponsKindBean reqData
			,UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//SQL���̍쐬
			strSql = budomariDataCreateSQL(reqData, strSql);
			
			//SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());
			
			//�@�\ID�̐ݒ�
			String strKinoId = reqData.getID();
			resKind.setID(strKinoId);

			//�e�[�u�����̐ݒ�
			String strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			//���X�|���X�f�[�^�̌`��
			storageResponseData(lstRecset, resKind.getTableItem(0));
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�}�X�^�����`�F�b�N�c�a�����Ɏ��s���܂����B");
		} finally {
			//���X�g�̔j��
			removeList(lstRecset);
			if (searchDB != null) {
				//�Z�b�V�����̃N���[�Y
				searchDB.Close();
				searchDB = null;
			}

			//�ϐ��̍폜
			strSql = null;

		}
		return resKind;

	}
	
	/**
	 * �����H�ꌟ��SQL�쐬
	 *  : �����H������擾����SQL���쐬�B
	 * @param reqData�F���N�G�X�g�f�[�^
	 * @param strSql�F��������SQL
	 * @return strSql�F��������SQL
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private StringBuffer budomariDataCreateSQL(RequestResponsKindBean reqData, StringBuffer strSql) 
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{
		
		StringBuffer strAllSql = new StringBuffer();

		try {
			
			//��ЃR�[�h
			String strKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			//�����R�[�h
			String strBushoCd = reqData.getFieldVale(0, 0, "cd_busho");
			//�����R�[�h
			String strGenryoCd = reqData.getFieldVale(0, 0, "cd_genryo");
			//����
			String strBudomari = reqData.getFieldVale(0, 0, "budomari");

			strAllSql.append("  select");
			strAllSql.append("   budomari");
			strAllSql.append("  from");
			strAllSql.append("   ma_genryokojo");
			strAllSql.append("  where");
			strAllSql.append("   cd_kaisha = " + strKaishaCd );
			strAllSql.append("   and cd_busho = " + strBushoCd );
			strAllSql.append("   and cd_genryo = '" + strGenryoCd + "'");
			strAllSql.append("   and budomari = " + strBudomari );
			
			strSql = strAllSql;

		} catch (Exception e) {
			this.em.ThrowException(e, "�}�X�^�����`�F�b�N�c�a�����Ɏ��s���܂����B");
		} finally {
			//�ϐ��̍폜
			strAllSql = null;
		}
		return strSql;
	}

	/**
	 * �����H����p�����[�^�[�i�[
	 * @param lstGenryouData : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	private void storageResponseData(List<?> lstGenryouData, RequestResponsTableBean resTable)
		throws ExceptionSystem, ExceptionUser, ExceptionWaning{

		try {
			
			//�������ʂ̊i�[
			resTable.addFieldVale(0, "flg_return", "true");
			resTable.addFieldVale(0, "msg_error", "");
			resTable.addFieldVale(0, "no_errmsg", "");
			resTable.addFieldVale(0, "nm_class", "");
			resTable.addFieldVale(0, "cd_error", "");
			resTable.addFieldVale(0, "msg_system", "");
				
			//���ʂ����X�|���X�f�[�^�Ɋi�[
			//�������ʂ��Ȃ��ꍇ�i����������j
			if (lstGenryouData.size() == 0){
				resTable.addFieldVale(0, "flg_hikaku", "false");
			}
			//�������ʂ�����ꍇ�i����������j
			else{
				resTable.addFieldVale(0, "flg_hikaku", "true");
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�}�X�^�����`�F�b�N�c�a�����Ɏ��s���܂����B");

		} finally {

		}

	}
	
}