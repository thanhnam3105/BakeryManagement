package jp.co.blueflag.shisaquick.srv.datacheck;

import jp.co.blueflag.shisaquick.srv.base.DataCheck;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager.ExceptionKind;
import jp.co.blueflag.shisaquick.srv.jnlp.ShisakuNoKengenCheck;
import jp.co.blueflag.shisaquick.srv.jnlp.ShisakuSecretCheck;

/**
 * 
 * JNLP�쐬����DB�`�F�b�N
 *  : JNLP�t�@�C���̍쐬��DB�`�F�b�N
 * 
 * @author TT.jinbo
 * @since 2009/08/03
 *
 */
public class JNLPCreateDataCheck extends DataCheck {

	private ShisakuNoKengenCheck shisakuCheck = null;		//����No�����`�F�b�N�N���X
	//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
	private ShisakuSecretCheck shisakuSecretCheck = null;	//����No�V�[�N���b�g�`�F�b�N�N���X
	//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End
	
	/**
	 * �R���X�g���N�^
	 */
	public JNLPCreateDataCheck() {
		//���N���X�̃R���X�g���N�^
		super();

		//����No�����`�F�b�N�N���X
		shisakuCheck = new ShisakuNoKengenCheck(); 
		//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
		//����No�V�[�N���b�g�`�F�b�N�N���X
		shisakuSecretCheck = new ShisakuSecretCheck();
		//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End
	}

	/**
	 * JNLP�쐬����DB�`�F�b�N�Ǘ�
	 * @param reqData : �@�\���N�G�X�g�f�[�^
	 * @param userInfoData : ���[�U�[���
	 * @throws ExceptionWaning 
	 * @throws ExceptionUser 
	 * @throws ExceptionSystem 
	 */
	public void execDataCheck(
			RequestResponsKindBean reqData,
			UserInfoData _userInfoData
			) 
	throws ExceptionSystem, ExceptionUser, ExceptionWaning {
		
		//���[�U�[���ޔ�
		userInfoData = _userInfoData;

		try {
			String strShisakuNo = "";
			String strMode = "";
			
			strShisakuNo = reqData.getFieldVale(0, 0, "no_shisaku");
			strMode = reqData.getFieldVale(0, 0, "mode");
			
			//�V�K���[�h�ł̋N���ȊO�̏ꍇ
			if (!strMode.equals("110")) {
				//����No�̌����`�F�b�N���s���B
				if (shisakuCheck.execDataCheck(reqData, userInfoData) == false) {
					em.ThrowException(ExceptionKind.���Exception,"E000308", strShisakuNo, "", "");
				}
				//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD Start
				//�ҏW���[�h or ���@�R�s�[���[�h�ł̋N���̏ꍇ
				else if(strMode.equals("100") || strMode.equals("120")){
					//����No�̃V�[�N���b�g�`�F�b�N���s���B
					if (shisakuSecretCheck.execDataCheck(reqData,_userInfoData) == false) {
						em.ThrowException(ExceptionKind.���Exception,"E000334", strShisakuNo, "", "");
					}
				}
				//�yQP@20505_No.38�z2012/10/17 TT H.SHIMA ADD End
			}
			
		} catch (Exception e) {
			em.ThrowException(e, "JNLP�쐬DB�`�F�b�N�����Ɏ��s���܂����B");
			
		} finally {

		}

	}

}