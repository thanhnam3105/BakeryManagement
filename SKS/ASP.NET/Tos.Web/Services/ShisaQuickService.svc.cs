//------------------------------------------------------------------------------
// <copyright file="WebDataService.svc.cs" company="Microsoft">
//     Copyright (c) Microsoft Corporation.  All rights reserved.
// </copyright>
//------------------------------------------------------------------------------
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Services;
using System.Data.Services.Common;
using System.Linq;
using System.Net;
using System.ServiceModel.Web;
using System.Web;
using Tos.Web.Logging;
using Tos.Web.Data;

namespace Tos.Web.Services
{
    //created from �yTemplateDataService(Ver1.6)�z Template
    public class ShisaQuickService : DataService<ShohinKaihatsuEntities>
    {
        // This method is called only once to initialize service-wide policies.
        /// <summary>
        /// �T�[�r�X�̏������������`���܂��B
        /// Entity �ւ̃A�N�Z�X���[���A����уT�[�r�X�I�y���[�V�����ւ̃A�N�Z�X���[�����`���܂��B
        /// </summary>
        /// <param name="config"></param>
        public static void InitializeService(DataServiceConfiguration config)
        {
            // TODO: �����T���v���ɏ]����Entity �ւ̃A�N�Z�X���[���A����уT�[�r�X�I�y���[�V�����ւ̃A�N�Z�X���[�����`���Ă��������B
            // config.SetEntitySetAccessRule("MyEntityset", EntitySetRights.AllRead);
            // config.SetServiceOperationAccessRule("MyServiceOperation", ServiceOperationRights.All);
            config.SetEntitySetAccessRule("ma_literal", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_group", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_team", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_user_togo", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_kbn_hin", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_category", EntitySetRights.AllRead);
            //config.SetEntitySetAccessRule("ma_bunrui", EntitySetRights.AllRead);
            // ma_bunrui is replaced with vw_ma_bunrui
            config.SetEntitySetAccessRule("vw_ma_bunrui", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_no_seiho_shurui", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_seiho_bunrui", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_ma_kojyo", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("tr_shisaku", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_seiho_bunsho_hyoshi", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_seiho_bunsho_genryo_setsubi", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_seiho_bunsho_kakuninjiko", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_hin", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_ma_mark", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_kaisha_kojyo", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_kaisha", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_group", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_busho", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_team", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_kengen", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_shohinkaihatsu_dialog_g3_nm_meisho_hinmei", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_seiho_bunsho_shomikigen", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_kojyo", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_seihin_seiho", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_tani", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_seiho_bunsho_shomikigen", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_shohinkaihatsu_008_busho_kaisha", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_seiho_denso", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_shohinkaihatsu_dialog_kaisha_501", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_yoki_hoso_shizai", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_hin_syurui", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_haigo_header", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_kotei", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_tantokaisya", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_shohinkaihatsu_ikkatsuShutsuryoToroku_206", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("tr_shisan_status", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("tr_shisan_shisaku", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("tr_cyuui", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_user_new", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("tr_shisan_shisakuhin", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_gate_bunrui", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_gate_check", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("tr_gate_header", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_ma_gate_check", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("tr_shisakuhin", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("tr_gate_meisai", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("tr_gate_attachment", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("vw_yoki_hoso_shizai", EntitySetRights.AllRead);
            config.SetEntitySetAccessRule("ma_user", EntitySetRights.AllRead);

            config.DataServiceBehavior.MaxProtocolVersion = DataServiceProtocolVersion.V3;
        }

        /// <summary>
        /// Data Services �Ŕ���������O���n���h�����܂��B
        /// �����ł̓T�[�o�[�̃G���[���O�ɃG���[�����L�^���A��O�̏ڍׂȏ����N���C�A���g�ɒʒm����悤�ݒ肵�Ă��܂��B
        /// </summary>
        /// <param name="args">����������O�̏ڍׂƁA�֘A���� HTTP �����̏ڍׂ��i�[���������B</param>
        protected override void HandleException(HandleExceptionArgs args)
        {
            Logger.App.Error(args.Exception.Message, args.Exception);

            args.UseVerboseErrors = true;

            //�������s�G���[���n���h�����ČĂяo�����ɕԂ��܂��B
            if (args.Exception != null
                && args.Exception is OptimisticConcurrencyException
                || args.Exception.InnerException is OptimisticConcurrencyException)
            {
                throw new DataServiceException((int)HttpStatusCode.Conflict, args.Exception.Message);
            }

            //TODO: �����ɌʂŃf�[�^�x�[�X�Ŕ���������O�̃n���h����ǉ����܂��B
            System.Data.UpdateException updateException = args.Exception as System.Data.UpdateException;
            if (updateException != null)
            {
                System.Data.SqlClient.SqlException ex = updateException.InnerException as System.Data.SqlClient.SqlException;
                if (ex != null)
                {
                    if (ex.Number == Tos.Web.Data.SqlErrorNumbers.PrimaryKeyViolation)
                    {
                        throw new DataServiceException((int)HttpStatusCode.InternalServerError, Properties.Resources.PrimaryKeyViolation);
                    }

                    if (ex.Number == Tos.Web.Data.SqlErrorNumbers.NotNullAllow)
                    {
                        throw new DataServiceException((int)HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
                    }
                }
            }
        }
    }
}
