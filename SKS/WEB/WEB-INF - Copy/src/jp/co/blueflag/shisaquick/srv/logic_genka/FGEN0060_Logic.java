package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN0010_Logic;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN2020_Logic;

/**
 * ���/�H��􂢑ւ��iFGEN0060�j�̎���
 * @author isono
 * @cleate 2009/11/02
 */
public class FGEN0060_Logic extends LogicBase {

	/**
	 * �R���X�g���N�^
	 */
	public FGEN0060_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	/**
	 * �������Z��� ��ЁA�H��􂢑ւ�
	 * 
	 * @param reqData : �@�\���N�G�X�g�f�[�^
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
		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		//�������Z �􂢑ւ��N���X
		CGEN0010_Logic clsCGEN0010_Logic = null;
		//�������Z �o�^�Ǘ��N���X
		CGEN2020_Logic clsCGEN2020_Logic = null;
		
		try {
			
			//�������Z �o�^�Ǘ��N���X�C���X�^���X����
			clsCGEN2020_Logic = new CGEN2020_Logic();
			//�������Z �o�^�Ǘ��N���X���s
			resKind = clsCGEN2020_Logic.ExecLogic(reqData, userInfoData);

			//��ЁA�H��􂢑ւ��N���X�C���X�^���X����
			clsCGEN0010_Logic = new CGEN0010_Logic();
			//��ЁA�H��􂢑ւ��N���X���s �yQP@00342�z�����Ɏ}�Ԓǉ�
			clsCGEN0010_Logic.ExecLogic(
					  toDecimal(reqData.getFieldVale("kihon", "rec", "cd_shain"))
					, toInteger(reqData.getFieldVale("kihon", "rec", "nen"))
					, toInteger(reqData.getFieldVale("kihon", "rec", "no_oi"))
					, toInteger(reqData.getFieldVale("kihon", "rec", "new_cd_kaisya"))
					, toInteger(reqData.getFieldVale("kihon", "rec", "new_cd_kojyo"))
					, toInteger(reqData.getFieldVale("kihon", "rec", "no_eda"))
					, userInfoData);

		} catch (Exception e) {
			em.ThrowException(e, "�������Z��� ��ЁA�H��􂢑ւ������s���܂����B");
			
		} finally {

		}
		return resKind;

	}
	
}