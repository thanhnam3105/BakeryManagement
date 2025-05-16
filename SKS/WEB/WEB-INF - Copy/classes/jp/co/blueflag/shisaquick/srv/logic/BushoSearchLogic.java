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
 * ���������i��ۯ���޳ݗp�jDB����
 *  : ��ۯ���޳ݗp�@���������f�[�^���oSQL���쐬����B
 * M104_busho�e�[�u������A�f�[�^�̒��o���s���B
 * 
 * @author TT.katayama
 * @since 2009/04/08
 */
public class BushoSearchLogic extends LogicBase {

	/**
	 * ���������i��ۯ���޳ݗp�j�p�R���X�g���N�^ : �C���X�^���X����
	 */
	public BushoSearchLogic() {
		//���N���X�̃R���X�g���N�^
		super();
		
	}

	/**
	 * �R���{�p�F�������擾SQL�쐬
	 *  M104_busho�e�[�u������A�f�[�^�̒��o���s���B
	 * 
	 * @param reqKind : �@�\���N�G�X�g�f�[�^
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
		String strReqKaishaCd = "";
		//���[�U���
		String strUDataId = "";
		String strUKinoId = "";

		List<?> lstRecset = null;
		StringBuffer strSql = new StringBuffer();

		RequestResponsKindBean resKind = null;
		
		try {
			//���X�|���X�f�[�^�i�@�\�j����
			resKind = new RequestResponsKindBean();

			//�����f�[�^ID�̎擾

			//�@�F�@�\���N�G�X�g�f�[�^�N���X��p���A���e�����}�X�^�����擾����SQL���쐬����B
			
			//�@�\���N�G�X�g�f�[�^��胆�[�UID�Ɖ��ID���擾
			strReqKaishaCd = reqData.getFieldVale(0, 0, "cd_kaisha");
			strReqGamenId = reqData.getFieldVale(0, 0, "id_gamen");

			//���[�U���̃��[�UID���擾
			strReqUserId = userInfoData.getId_user();
			
			//�����f�[�^ID�擾
			for (int i = 0; i < userInfoData.getId_gamen().size(); i++) {
				if (userInfoData.getId_gamen().get(i).toString().equals(strReqGamenId)){
					//�@�\ID��ݒ�
					strUKinoId = userInfoData.getId_kino().get(i).toString(); 
					//�f�[�^ID��ݒ�
					strUDataId = userInfoData.getId_data().get(i).toString();
				}
			}
			
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
					} else if ( strUDataId.equals("4") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("20") ) {
				//�������͏��iJSP�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("10") ) {					//�{��
					if ( strUDataId.equals("2") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�폜�̂݁j
					if ( strUDataId.equals("2") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("50") ) {		//�ҏW�i�폜�{CSV�o�́j
					if ( strUDataId.equals("2") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						
						
					} else if ( strUDataId.equals("2") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						
						
					} else if ( strUDataId.equals("2") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW
					if ( strUDataId.equals("1") ) {
						//�S�����
						
						
					} else if ( strUDataId.equals("2") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						
					} else if ( strUDataId.equals("2") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("60") ) {
				//�e���e�����}�X�^�iJSP�j
				
				//�����ɂ�镪��
				if ( strUKinoId.equals("20") ) {					//�ҏW�i�X�V�̂݁j
					if ( strUDataId.equals("1") ) {
						//����O���[�v
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("50") ) {		//�ҏW�i�X�V�{CSV�o�́j
					if ( strUDataId.equals("1") ) {
						//����O���[�v
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else  if ( strUKinoId.equals("40") ) {		//�ҏW�i�V�X�e���Ǘ��ҁj
					if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} else if ( strReqGamenId.equals("90") ) {
				//�S���҃}�X�^�iJSP�j
				
				//��ЃR�[�h�������͂̏ꍇ�i���[�UID�̃��X�g�t�H�[�J�X���j
				if (strReqKaishaCd.equals("")) {
					//���[�UID�̍Ď擾
					strReqUserId = reqData.getFieldVale(0, 0, "id_user");
				}

				//�����ɂ�镪��
				if ( strUKinoId.equals("21") ) {					//�ҏW�i�����߽ܰ�ނ̂݁j
					 if ( strUDataId.equals("1") ) {
						//�����̂�
						strSql = this.createSQLMyBushoTantoMst(strReqUserId, strReqKaishaCd);
						 
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBushoTantoMst(strReqUserId, strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�S�āj
					if ( strUDataId.equals("1") ) {
						//�����̂�
						strSql = this.createSQLMyBushoTantoMst(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBushoTantoMst(strReqUserId, strReqKaishaCd);
						
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
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����				
						strSql = this.createSQLAllBusho(strReqKaishaCd);
								
					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("4") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {			//�ҏW
					if ( strUDataId.equals("1") ) {
						//����O���[�v���i�{�l+���ذ�ް�ȏ�j		
						strSql = this.createSQLAllBusho(strReqKaishaCd);
										
					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����					
						strSql = this.createSQLAllBusho(strReqKaishaCd);
							
					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("4") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����						
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("4") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("2") ) {
						//����O���[�v���S�����						
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("3") ) {
						//����O���[�v���S����Њ��i�{�l+���ذ�ް�ȏ�j
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					} else if ( strUDataId.equals("4") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�X�V�j
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j		
						strSql = this.createSQLAllBusho(strReqKaishaCd);
										
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						strSql = this.createSQLAllBusho(strReqKaishaCd);
								
					} else if ( strUDataId.equals("2") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
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
						strSql = this.createSQLAllBusho(strReqKaishaCd);
									
					} else if ( strUDataId.equals("2") ) {
						//������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else if ( strUKinoId.equals("20") ) {		//�ҏW�i�폜�j
					if ( strUDataId.equals("1") ) {
						//����O���[�v���S����Њ��i�{�l+��i�j				
						strSql = this.createSQLAllBusho(strReqKaishaCd);
								
					} else if ( strUDataId.equals("2") ) {
						//��������
						strSql = this.createSQLMyBusho(strReqUserId, strReqKaishaCd);
						
					} else if ( strUDataId.equals("9") ) {
						//�S��
						strSql = this.createSQLAllBusho(strReqKaishaCd);
						
					}
				} else {
					//�Ώۂ̋@�\ID�ł͂Ȃ��ꍇ
					this.em.ThrowException(ExceptionManager.ExceptionKind.�x��Exception, "W000400", strKinoId, "", "");
				}
			} 
			
			//�A�F�f�[�^�x�[�X������p���A�����R���{�{�b�N�X�f�[�^���擾����B
			super.createSearchDB();
			lstRecset = searchDB.dbSearch(strSql.toString());

			//�B�F�R���{�p�F�����p�����[�^�[�i�[���\�b�h���ďo���A�@�\���X�|���X�f�[�^���`������B
			
			// �@�\ID�̐ݒ�
			strKinoId = reqData.getID();
			resKind.setID(strKinoId);
			
			// �e�[�u�����̐ݒ�
			strTableNm = reqData.getTableID(0);
			resKind.addTableItem(strTableNm);

			// ���X�|���X�f�[�^�̌`��
			storageKaishaCmb(lstRecset, resKind.getTableItem(0));

		} catch (Exception e) {
			this.em.ThrowException(e, "�R���{�p�F�������擾SQL�쐬���������s���܂����B");
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
		
		//�C�F�@�\���X�|���X�f�[�^��ԋp����B
		return resKind;
	}
	
	/**
	 * �����u�S�āv�̕����f�[�^�擾�pSQL�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLAllBusho(String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL���̍쐬
		strRetSql.append("SELECT distinct cd_busho, nm_busho");
		strRetSql.append(" FROM ma_busho");
		strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);
		strRetSql.append(" ORDER BY cd_busho");
		
		return strRetSql;
	}
	
	/**
	 * �����u���������v�̕����f�[�^�擾�pSQL�쐬
	 * @param strUserId : ���[�UID
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLMyBusho(String strUserId, String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();
		
		String strBushoCd = userInfoData.getCd_busho();

		// SQL���̍쐬
		strRetSql.append(" SELECT distinct ");
		strRetSql.append(" M101.cd_busho AS cd_busho,  ");
		strRetSql.append(" ISNULL(M104.nm_busho,'') AS nm_busho ");
		strRetSql.append(" FROM ma_user M101 ");
		strRetSql.append("  LEFT JOIN ma_busho M104 ");
		strRetSql.append("   ON M104.cd_busho = M101.cd_busho ");
		strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
		strRetSql.append("  AND M101.cd_busho =" + strBushoCd);
		strRetSql.append("  AND M101.id_user =" + strUserId);
		
		return strRetSql;
	}
	
	/**
	 * �S���҃}�X�^��p�F�����u�S�āv�̕����f�[�^�擾�pSQL�쐬
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLAllBushoTantoMst(String strUserId, String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();

		// SQL���̍쐬
		strRetSql.append("SELECT distinct cd_busho, nm_busho");
		strRetSql.append(" FROM ma_busho");
		if (strKaishaCd.equals("")) {
			strRetSql.append(" WHERE cd_kaisha = (SELECT cd_kaisha FROM ma_user WHERE id_user = " + strUserId + ")");
		} else {
			strRetSql.append(" WHERE cd_kaisha =" + strKaishaCd);
		}
		strRetSql.append(" ORDER BY cd_busho");
		
		return strRetSql;
	}
	
	/**
	 * �S���҃}�X�^��p�F�����u�����̂݁v�̕����f�[�^�擾�pSQL�쐬
	 * @param strUserId : ���[�UID
	 * @param strKaishaCd : ��ЃR�[�h
	 * @return �쐬SQL
	 */
	private StringBuffer createSQLMyBushoTantoMst(String strUserId, String strKaishaCd) {
		StringBuffer strRetSql = new StringBuffer();
		
		String strBushoCd = userInfoData.getCd_busho();

		// SQL���̍쐬
		strRetSql.append(" SELECT distinct ");
		strRetSql.append(" M101.cd_busho AS cd_busho,  ");
		strRetSql.append(" ISNULL(M104.nm_busho,'') AS nm_busho ");
		strRetSql.append(" FROM ma_user M101 ");
		strRetSql.append("  LEFT JOIN ma_busho M104 ");
		strRetSql.append("   ON M104.cd_kaisha = M101.cd_kaisha ");
		strRetSql.append("   AND M104.cd_busho = " + strBushoCd);
		if (strKaishaCd.equals("")) {
			strRetSql.append(" WHERE M101.id_user =" + strUserId);
		} else {
			strRetSql.append(" WHERE M101.cd_kaisha =" + strKaishaCd);
			strRetSql.append("  AND M101.cd_busho =" + strBushoCd);
			strRetSql.append("  AND M101.id_user =" + strUserId);
		}
		
		return strRetSql;
	}
	
	/**
	 * �R���{�p�F�����p�����[�^�[�i�[
	 *  : �����R���{�{�b�N�X�������X�|���X�f�[�^�֊i�[����B
	 * �擾�����A���X�|���X�f�[�^�ێ��u�@�\ID�FSA290O�v�ɐݒ肷��B
	 * 
	 * @param lstGroupCmb : �������ʏ�񃊃X�g
	 * @throws ExceptionWaning
	 * @throws ExceptionUser
	 * @throws ExceptionSystem
	 */
	private void storageKaishaCmb(List<?> lstGroupCmb, RequestResponsTableBean resTable)
	throws ExceptionSystem,	ExceptionUser, ExceptionWaning {

		try {
			
			for (int i = 0; i < lstGroupCmb.size(); i++) {

				//�������ʂ̊i�[
				resTable.addFieldVale(i, "flg_return", "true");
				resTable.addFieldVale(i, "msg_error", "");
				resTable.addFieldVale(i, "no_errmsg", "");
				resTable.addFieldVale(i, "nm_class", "");
				resTable.addFieldVale(i, "cd_error", "");
				resTable.addFieldVale(i, "msg_system", "");
				
				Object[] items = (Object[]) lstGroupCmb.get(i);

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(i, "cd_busho", items[0].toString());
				resTable.addFieldVale(i, "nm_busho", items[1].toString());
				
			}

			if (lstGroupCmb.size() == 0) {

				//�������ʂ̊i�[
				resTable.addFieldVale(0, "flg_return", "true");
				resTable.addFieldVale(0, "msg_error", "");
				resTable.addFieldVale(0, "no_errmsg", "");
				resTable.addFieldVale(0, "nm_class", "");
				resTable.addFieldVale(0, "cd_error", "");
				resTable.addFieldVale(0, "msg_system", "");

				//���ʂ����X�|���X�f�[�^�Ɋi�[
				resTable.addFieldVale(0, "cd_busho", "");
				resTable.addFieldVale(0, "nm_busho", "");

			}

		} catch (Exception e) {
			this.em.ThrowException(e, "�R���{�p�F�����p�����[�^�[�i�[���������s���܂����B");

		} finally {

		}

	}
	
}
