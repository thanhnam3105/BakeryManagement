package jp.co.blueflag.shisaquick.srv.logic;

import jp.co.blueflag.shisaquick.srv.base.LogicBase;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsKindBean;
import jp.co.blueflag.shisaquick.srv.base.RequestResponsTableBean;
import jp.co.blueflag.shisaquick.srv.base.UserInfoData;
import jp.co.blueflag.shisaquick.srv.common.ExceptionManager;
import jp.co.blueflag.shisaquick.srv.common.ExceptionSystem;
import jp.co.blueflag.shisaquick.srv.common.ExceptionUser;
import jp.co.blueflag.shisaquick.srv.common.ExceptionWaning;

import java.util.List;

/**
 * 
 * ��Ќ����i��ۯ���޳ݗp�jDB���� : ��ۯ���޳ݗp�@��Ќ����f�[�^���oSQL���쐬����B
 * M104_busho�e�[�u������A�f�[�^�̒��o���s���B
 * 
 * @author itou
 * @since 2009/04/02
 * @author TT.katayama
 * @since 2009/04/07
 */
public class KaishaSearchLogic extends LogicBase {

	/**
	 * ��Ќ����i��ۯ���޳ݗp�j�p�R���X�g���N�^ : �C���X�^���X����
	 */
	public KaishaSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();

	}

	/**
	 * �R���{�p�F��Џ��擾SQL�쐬 M104_busho�e�[�u������A�f�[�^�̒��o���s���B
	 * 
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

		//���N�G�X�g�f�[�^
		String strKinoId = "";
		String strTableNm = "";
		String strReqUserId = "";
		String strReqGamenId = "";
		//���[�U���
		String strUDataId = "";
		String strUKinoId = "";

		StringBuffer strSql = new StringBuffer();
		List<?> lstRecset = null;

		//���X�|���X�f�[�^�N���X
		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�����f�[�^ID�̎擾

			//�@�F�@�\���N�G�X�g�f�[�^�N���X��p���A���e�����}�X�^�����擾����SQL���쐬����B
			
			//�@�\���N�G�X�g�f�[�^��胆�[�UID�Ɖ��ID���擾
			strReqUserId = reqData.getFieldVale(0, 0, "id_user");
			strReqGamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//���[�U���̃��[�UID���擾
			if ( strReqUserId.equals("") ) {
				strReqUserId = userInfoData.getId_user();
			}
			
			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strReqGamenId)){
					//�@�\ID��ݒ�
					strUKinoId = userInfoData.getId_kino().get(i).toString(); 
					//�f�[�^ID��ݒ�
					strUDataId = userInfoData.getId_data().get(i).toString();
				}
			}

			// SQL���̍쐬
//			strSql.append("SELECT distinct cd_kaisha, nm_kaisha");
//			strSql.append(" FROM ma_busho");
//			strSql.append(" ORDER BY cd_kaisha");
			
			//���ID�A�@�\ID�A�f�[�^ID�ɂ�鏈���̐���
			if ( strReqGamenId.equals("10") ) {
				//����f�[�^�ꗗ�iJSP�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���i�{�l+���ذ�ް�ȏ�j						
					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����						
					} else if ( strUDataId.equals("3") ) {
						//�S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("4") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("20") || strReqGamenId.equals("25") ) {
				//�������͏��iJSP�j

				if (strUKinoId.equals("")) {
					//�����f�[�^ID�擾
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("25")){
							//�@�\ID��ݒ�
							strUKinoId = userInfoData.getId_kino().get(i).toString(); 
							//�f�[�^ID��ݒ�
							strUDataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//�S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�폜�̂݁j
					if ( strUDataId.equals("1") ) {
						//�S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("30") ) {
				//���͒l���́i�V�K�j�iJSP�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("30") ) {					//�V�K
					if ( strUDataId.equals("1") ) {
						//�S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("40") ) {
				//���͒l���́i�ڍׁj�iJSP�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//�S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW(�V�K�����̂�)
					if ( strUDataId.equals("1") ) {
						//�S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else if ( strUKinoId.equals("40") ) {		//�ҏW(�S��)
					if ( strUDataId.equals("1") ) {
						//�S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("50") ) {
				//�S�H��P��������ʁiJSP�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j						
						strSql = this.createSQLTantoKaisha(strReqUserId);

					} else if ( strUDataId.equals("2") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("60") || strReqGamenId.equals("65") ) {
				//�e���e�����}�X�^�iJSP�j
				
				if (strUKinoId.equals("")) {
					//�����f�[�^ID�擾
					for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
						if (userInfoData.getId_gamen().get(i).toString().equals("65")){
							//�@�\ID��ݒ�
							strUKinoId = userInfoData.getId_kino().get(i).toString(); 
							//�f�[�^ID��ݒ�
							strUDataId = userInfoData.getId_data().get(i).toString();
						}
					}
				}
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//CSV�o�́i��ʁj
					if ( strUDataId.equals("1") ) {
						//����O���[�v						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else  if ( strUKinoId.equals("20") ) {					//�ҏW�i��ʁj
					if ( strUDataId.equals("1") ) {
						//����O���[�v						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else  if ( strUKinoId.equals("50") ) {		//�ҏW�i�V�X�e���Ǘ��ҁj
					if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("70") ) {
				//�O���[�v�}�X�^�iJSP�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("20") ) {					//�ҏW
					if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("80") ) {
				//�����}�X�^�iJSP�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("20") ) {					//�ҏW
					if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("90") ) {
				//�S���҃}�X�^�iJSP�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("21") ) {					//�ҏW�i�����߽ܰ�ނ̂݁j
					 if ( strUDataId.equals("1") ) {
						//�����̂�
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�S�āj
					if ( strUDataId.equals("1") ) {
						//�����̂�
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("100") ) {
				//����f�[�^��ʁi�ڍׁj(JWS)
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
									
					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("4") ) {
						//���H�ꕪ(�����)
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else if ( strUKinoId.equals("20") ) {			//�ҏW
					if ( strUDataId.equals("1") ) {
						//����O���[�v���i�{�l+���ذ�ް�ȏ�j			
						strSql = this.createSQLTantoKaisha(strReqUserId);
									
					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
												
					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("4") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("110") ) {	
				//����f�[�^��ʁi�V�K�j(JWS)

				//�����ɂ�镪��
				if ( strUKinoId.equals("30") ) {			//�V�K
					if ( strUDataId.equals("1") ) {
						//����O���[�v���i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("4") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("120") ) {
				//����f�[�^��ʁi���@�x���R�s�[�j(JWS)

				//�����ɂ�镪��
				if ( strUKinoId.equals("20") ) {			//�ҏW�i�V�K�j
					if ( strUDataId.equals("1") ) {
						//����O���[�v���i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("4") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("130") ) {	
				//���͒l���́i�V�K�j�iJWS�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�X�V�j
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("140") ) {
				//���͒l���́i�ڍׁj�iJWS�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("20") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("150") ) {	
				//�����ꗗ
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("160") ) {
				//������񕪐́iJWS�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�폜�j
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j
						strSql = this.createSQLTantoKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("2") ) {
						//���H�ꕪ
						strSql = this.createSQLMyKaisha(strReqUserId);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllKaisha();
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
					
				}
			} 
			
			// SQL�����s
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			// �@�\ID�̐ݒ�
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			// �e�[�u�����̐ݒ�
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// ���X�|���X�f�[�^�̌`��
			storageKaishaCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "");
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
	 * �����u�S�āv�̉�Ѓf�[�^�擾�pSQL�쐬
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLAllKaisha() {
		StringBuffer strRetSql = new StringBuffer();

		// SQL���̍쐬
		strRetSql.append("SELECT distinct cd_kaisha, nm_kaisha");
		strRetSql.append(" FROM ma_busho");
		strRetSql.append(" ORDER BY cd_kaisha");
		
		return strRetSql;
	}
	
	/**
	 * �����u����Е��v�̉�Ѓf�[�^�擾�pSQL�쐬
	 * @param strUserId : ���[�UID
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLMyKaisha(String strUserId) {
		StringBuffer strRetSql = new StringBuffer();
		
		String strKaishaCd = userInfoData.getCd_kaisha();

		// SQL���̍쐬
		strRetSql.append(" SELECT distinct ");
		strRetSql.append(" M101.cd_kaisha AS cd_kaisha,  ");
		strRetSql.append(" ISNULL(M104.nm_kaisha,'') AS nm_kaisha ");
		strRetSql.append(" FROM ma_user M101 ");
		strRetSql.append("  LEFT JOIN ma_busho M104 ");
		strRetSql.append("   ON M104.cd_kaisha = M101.cd_kaisha ");
		strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M101.id_user =" + strUserId);
		
		return strRetSql;
	}
	
	/**
	 * �����u�S����Ёv�̉�Ѓf�[�^�擾�pSQL�쐬
	 * @param strUserId : ���[�UID
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLTantoKaisha(String strUserId) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL���̍쐬
		strRetSql.append(" SELECT distinct  ");
		strRetSql.append(" ISNULL(M107.cd_tantokaisha,'') AS cd_tantokaisha, ");
		strRetSql.append(" ISNULL(M104.nm_kaisha,'') AS nm_kaisha ");
		strRetSql.append(" FROM ma_user M101 ");
		strRetSql.append("  LEFT JOIN ma_tantokaisya M107 ");
		strRetSql.append("   ON M107.id_user = M101.id_user ");
		strRetSql.append("  LEFT JOIN ma_busho M104 ");
		strRetSql.append("   ON M104.cd_kaisha = M107.cd_tantokaisha ");
		strRetSql.append(" WHERE M101.id_user =" + strUserId);
		
		return strRetSql;
	}

	/**
	 * �R���{�p�F��Ѓp�����[�^�[�i�[ : ��ЃR���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA140O�v�ɐݒ肷��B
	 * 
	 * @param lstGroupCmb
	 *            : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageKaishaCmb(List<?> lstGroupCmb, RequestResponsTableBean resTable)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {
			
			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale("rec" + i, "flg_return", "true");
				resTable.addFieldVale("rec" + i, "msg_error", "");
				resTable.addFieldVale("rec" + i, "no_errmsg", "");
				resTable.addFieldVale("rec" + i, "nm_class", "");
				resTable.addFieldVale("rec" + i, "cd_error", "");
				resTable.addFieldVale("rec" + i, "msg_system", "");
				
				Object[] items = (Object[]) lstGroupCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale("rec" + i, "cd_kaisha", items[0].toString());
				resTable.addFieldVale("rec" + i, "nm_kaisha", items[1].toString());
				
			}

			if (lstGroupCmb.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale("rec0", "flg_return", "true");
				resTable.addFieldVale("rec0", "msg_error", "");
				resTable.addFieldVale("rec0", "no_errmsg", "");
				resTable.addFieldVale("rec0", "nm_class", "");
				resTable.addFieldVale("rec0", "cd_error", "");
				resTable.addFieldVale("rec0", "msg_system", "");
	
				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale("rec0", "cd_kaisha", "");
				resTable.addFieldVale("rec0", "nm_kaisha", "");
				
			}

		} catch (Exception e) {
			this.em.ThrowException(e, "");

		} finally {

		}

	}
	
}
