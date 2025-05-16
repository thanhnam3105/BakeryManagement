package jp.co.blueflag.shisaquick.srv.logic_genka;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic_genka.CGEN2020_Logic;

/**
 * 
 * �������Z���o�^
 *  : �������Z��ʂ̓o�^
 *  
 * @author TT.Y.Nishigawa
 * @since  2009/10/28
 *
 */
public class FGEN0040_Logic extends LogicBase {
	
	/**
	 * �R���X�g���N�^
	 */
	public FGEN0040_Logic() {
		//���N���X�̃R���X�g���N�^
		super();

	}
	
	//--------------------------------------------------------------------------------------------------------------
	//
	//                                                                     ExecLogic�i�����J�n�j 
	//
	//--------------------------------------------------------------------------------------------------------------
	
	/**
	 * �������Z��� �Ǘ�����
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
		//�������Z �o�^�Ǘ��N���X
		CGEN2020_Logic clsCGEN2020_Logic = null;
		
		try {
			//�������Z �o�^�Ǘ��N���X�C���X�^���X����
			clsCGEN2020_Logic = new CGEN2020_Logic();
			//�������Z �o�^�Ǘ��N���X���s
			resKind = clsCGEN2020_Logic.ExecLogic(reqData, userInfoData);

		} catch (Exception e) {
			this.em.ThrowException(e, "�������Z��� �Ǘ����쏈�������s���܂����B");
			
		} finally {
			if (execDB != null) {
				execDB.Close();				//�Z�b�V�����̃N���[�Y
				execDB = null;
			}
		}
		return resKind;
	}
	
}	