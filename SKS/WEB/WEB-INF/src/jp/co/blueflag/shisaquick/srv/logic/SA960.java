package jp.co.blueflag.shisaquick.srv.logic;


import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic.JWSLiteralSearchLogic;

/**
 * QP@10181_No.42_49_72
 * �ySA960�z JWS���e���������i�����|�_�Z�x�j�c�a�����̎���
 *
 * @author Hisahori
 * @since 2011/05/30
 *
 */
public class SA960 extends LogicBase {

	private JWSLiteralSearchLogic literalSearch = null;		//JWS�p���e�������������N���X

	/**
	 * �R���X�g���N�^
	 */
	public SA960() {
		//���N���X�̃R���X�g���N�^
		super();

		//JWS�p���e�������������N���X
		this.literalSearch = new JWSLiteralSearchLogic();
	}

	/**
	 * JWS���e���������i�����|�_�Z�x�j���W�b�N�Ǘ�
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

		RequestResponsKindBean resBean = null;

		try {
			//�J�e�S���R�[�h�̐ݒ�
			String strCd_Categori = "K_jikkoSakusanNodo";
			//�����������s���A���ʂ��i�[����B
			resBean = this.literalSearch.ExecLogic(reqData,strCd_Categori,userInfoData);
		} catch (Exception e) {
			this.em.ThrowException(e, "JWS���e���������i�����|�_�Z�x�j���W�b�N�Ǘ������Ɏ��s���܂����B");
		} finally {
		}
		return resBean;
	}
}
