package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.commonlogic.JWSLiteralSearchLogic;

/**
 * 
 * �yS3-59 : SA640�z JWS���e���������i���������j�c�a�����̎���
 *  : ���͒l�ύX�m�F������DB�ɑ΂���Ɩ����W�b�N�̎���
 * 
 * @author TT.katayama
 * @since 2009/04/07
 *
 */
public class JWSLiteralTokutyoSearchLogic extends LogicBase {

	private JWSLiteralSearchLogic literalSearch = null;		//JWS�p���e�������������N���X
	
	/**
	 * �R���X�g���N�^
	 */
	public JWSLiteralTokutyoSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

		//JWS�p���e�������������N���X
		this.literalSearch = new JWSLiteralSearchLogic(); 
	}

	/**
	 * JWS���e���������i���������j���W�b�N�Ǘ�
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
			String strCd_Categori = "K_tokucyogenryo";
			//�����������s���A���ʂ��i�[����B
			resBean = this.literalSearch.ExecLogic(reqData,strCd_Categori,userInfoData);
		} catch (Exception e) {
			this.em.ThrowException(e, "JWS���e���������i���������j���W�b�N�Ǘ������Ɏ��s���܂����B");
		} finally {
		}
		return resBean;
	}

}
