package jp.co.blueflag.shisaquick.srv.logic_genka;

import java.util.List;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

/**
 * ���ތ����i�R�[�h���j
 * �F�Y���̉�ЁA�H��A���ރR�[�h�ɊY�����鎑�ޏ����擾����B
 * @author isono
 * @create 2009/11/04
 */
public class FGEN0080_Logic extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN0080_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	/**
	 * �������Z ���ތ����̎���
	 * �F�Y���̉�ЁA�H��A���ރR�[�h�ɊY�����鎑�ޏ����擾����B
	 * @param reqData		: ���N�G�X�g�f�[�^
	 * @param userInfoData	: ���[�U�[���
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
		//���X�|���X
		RequestResponsKindBean ret = null;
		//�A�C�e���I�u�W�F�N�g
		Object[] items = null;
		
		try {
			//���X�|���X�C���X�^���X����
			ret = new RequestResponsKindBean();
			ret.setID("FGEN0080");
			
			//DB����
			items = searchShizai(reqData);
			//���X�|���X�̃Z�b�g
			if (items == null){
				//�������ʇ@	flg_return
				ret.addFieldVale("table", "rec", "flg_return", "true");
				//�������ʇA	msg_error
				ret.addFieldVale("table", "rec", "msg_error", "�R�[�h�ɊY�����鎑�ނ�����܂���B");
				//�������ʇB	nm_class
				ret.addFieldVale("table", "rec", "nm_class", "");
				//�������ʇE	no_errmsg
				ret.addFieldVale("table", "rec", "no_errmsg", "");
				//�������ʇC	cd_error
				ret.addFieldVale("table", "rec", "cd_error", "");
				//�������ʇD	msg_system
				ret.addFieldVale("table", "rec", "msg_system", "");
				//����SEQ	seq_shizai
				ret.addFieldVale("table", "rec", "seq_shizai", "");
				//���CD	cd_kaisya
				ret.addFieldVale("table", "rec", "cd_kaisya", "");
				//�H��CD	cd_kojyo
				ret.addFieldVale("table", "rec", "cd_kojyo", "");
				//�H��L��	kigo_kojyo
				ret.addFieldVale("table", "rec", "kigo_kojyo", "");
				//����CD	cd_shizai
				ret.addFieldVale("table", "rec", "cd_shizai", "");
				//���ޖ�	nm_shizai
				ret.addFieldVale("table", "rec", "nm_shizai", "");
				//�P��	tanka
				ret.addFieldVale("table", "rec", "tanka", "");
				//����	budomari
				ret.addFieldVale("table", "rec", "budomari", "");
				
			}else{
				//�������ʇ@	flg_return
				ret.addFieldVale("table", "rec", "flg_return", "true");
				//�������ʇA	msg_error
				ret.addFieldVale("table", "rec", "msg_error", "");
				//�������ʇB	nm_class
				ret.addFieldVale("table", "rec", "nm_class", "");
				//�������ʇE	no_errmsg
				ret.addFieldVale("table", "rec", "no_errmsg", "");
				//�������ʇC	cd_error
				ret.addFieldVale("table", "rec", "cd_error", "");
				//�������ʇD	msg_system
				ret.addFieldVale("table", "rec", "msg_system", "");
				//����SEQ	seq_shizai
				ret.addFieldVale("table", "rec", "seq_shizai"
						, reqData.getFieldVale("table", "rec", "seq_shizai"));
				//���CD	cd_kaisya
				ret.addFieldVale("table", "rec", "cd_kaisya", toString(items[1], ""));
				//�H��CD	cd_kojyo
				ret.addFieldVale("table", "rec", "cd_kojyo", toString(items[2], ""));
				//�H��L��	kigo_kojyo
				ret.addFieldVale("table", "rec", "kigo_kojyo", toString(items[6], ""));
				//����CD	cd_shizai
				ret.addFieldVale("table", "rec", "cd_shizai", toString(items[0], ""));
				//���ޖ�	nm_shizai
				ret.addFieldVale("table", "rec", "nm_shizai", toString(items[3], ""));
				//�P��	tanka
				ret.addFieldVale("table", "rec", "tanka", toString(toDouble(items[4]), 2, 2 , true, ""));
				//����	budomari
				ret.addFieldVale("table", "rec", "budomari", toString(toDouble(items[5]), 2, 2 , true, ""));
				
			}
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z ���ތ����Ɏ��s���܂����B");

		} finally {

		}
		return ret;
		
	}
	/**
	 * ���ޏ��̌���
	 * @param reqData	�F���N�G�X�g�f�[�^
	 * @return Object[]	�F��������
	 * @throws ExceptionSystem
	 * @throws ExceptionUser
	 * @throws ExceptionWaning
	 */
	private Object[] searchShizai(
			
			RequestResponsKindBean reqData
			)
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//SQL�o�b�t�@
		StringBuffer strSQL = new StringBuffer();
		//���ʃo�b�t�@
		List<?> listResult = null;
		//���ޏ��
		Object[] ret = null;
		
		try{

			//DB�R�l�N�V����
			createSearchDB();

			//SQL����
			strSQL.append(" SELECT ");
			strSQL.append("  M901.cd_shizai ");	//0
			strSQL.append(" ,M901.cd_kaisha ");	//1
			strSQL.append(" ,M901.cd_busho  ");	//2
			strSQL.append(" ,M901.nm_shizai ");	//3
			strSQL.append(" ,M901.tanka ");		//4
			strSQL.append(" ,M901.budomari ");	//5
			strSQL.append(" ,M302.nm_literal ");//6
			strSQL.append(" FROM ");
			strSQL.append("           ma_shizai AS M901 ");
			strSQL.append(" LEFT JOIN ma_literal AS M302 ");
			strSQL.append(" ON  M302.cd_category = 'K_kigo_kojyo' ");
			strSQL.append(" AND M302.cd_literal  = CONVERT(varchar, M901.cd_kaisha) + '-' + CONVERT(varchar, M901.cd_busho) ");
			strSQL.append(" WHERE ");
			strSQL.append("     M901.cd_shizai = " 
					+ toString(reqData.getFieldVale("table", "rec", "cd_shizai"), "null") + " ");
			strSQL.append(" AND M901.cd_kaisha = " 
					+ toString(reqData.getFieldVale("table", "rec", "cd_kaisya"), "null") + " ");
			strSQL.append(" AND M901.cd_busho  = " 
					+ toString(reqData.getFieldVale("table", "rec", "cd_kojyo"), "null") + " ");
			//DB����
			try{
				listResult = this.searchDB.dbSearch(strSQL.toString());

				//���ޏ��
				if ( listResult.size() >= 0 ) {
					
					for (int i = 0; i < listResult.size(); i++) {
					
						ret = (Object[]) listResult.get(i);
						
					}

				}

			}catch(ExceptionWaning e){
				
			}
			
		}catch(Exception e){
			//��O�̃X���[
			this.em.ThrowException(e, "�������Z�@���ޏ��̌��� �Ɏ��s���܂����B\nSQL:"
					+ strSQL.toString());
			
		}finally{
			//DB�R�l�N�V�����J��
			if (searchDB != null) {
				searchDB.Close();
				searchDB = null;
				
			}
			//���[�J���ϐ��̊J��
			strSQL = null;
			removeList(listResult);

		}
		return ret;
		
	}


	
	
	
	
	
	
	
	
}
