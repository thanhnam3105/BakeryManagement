package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN0020_Logic;

/**
 * �������Z�@�Čv�Z�iFID�FFGEN0030�j�̎���
 * @author isono
 *
 */
public class FGEN0030_Logic extends LogicBase {

	/**
	 * �������Z�@�Čv�Z�iFID�FFGEN0030�j�̎���
	 * : �C���X�^���X����
	 */
	public FGEN0030_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �������Z�@�Čv�Z�iFID�FFGEN0030�j
	 *  : �Čv�Z�̎���
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
		//�Čv�Z�N���X
		CGEN0020_Logic clsCGEN0020_Logic = null;
		//���X�|���X
		RequestResponsKindBean ret = null;
		
		try {

			//�Čv�Z�N���X����
			clsCGEN0020_Logic = new CGEN0020_Logic();
			ret = clsCGEN0020_Logic.ExecLogic(reqData, userInfoData);
			
			//�t�@���N�V����ID�ݒ�
			ret.setID("FGEN0030");
			
		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z�@�Čv�Z�Ɏ��s���܂����B");
		} finally {

		}
		return ret;
		
	}
	
}
