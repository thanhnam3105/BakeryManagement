using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Http.OData.Query;
using Tos.Web.Data;
using Tos.Web.Controllers;
using System.Web.Http.OData;
using System.Data.Common;
using System.Data.Objects;
using Tos.Web.Logging;
using System.Security.Principal;

namespace Tos.Web.Controllers
{

    public class _300_SeihoBunshoSakuseiController : ApiController
    {
        #region "Const"

        // Error codes
        private const string PROCESS_SUCCESS = "success";
        private const string CONFLICT_ERROR = "AP0010";
        private const string NODATA_ERROR = "AP0024";
        private const string UNEXPECTED_ERROR = "AP0009";
        // Modes
        private const string NEW = "new";
        private const string EDIT = "edit";
        private const string VIEW = "view";
        // Shonin level
        private const int LEVEL_UNCHANGE = -1;
        private const int LEVEL_0 = 0;
        private const int LEVEL_1 = 1;
        private const int LEVEL_2 = 2;
        private const int LEVEL_3 = 3;
        // Log labels
        private const string LB_APPLY_LOG = "製法申請";
        private const string LB_CANCEL_APPLY_LOG = "申請取消";
        private const string LB_OPE_LOG = "登録";

        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// Get main data of main page
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public SeihoBunsyoShakuseiSearchResponse Get([FromUri]SeihoBunsyoShakuseiSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                SeihoBunsyoShakuseiSearchResponse result = new SeihoBunsyoShakuseiSearchResponse();
                result.seihoData = context.ma_seiho.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                /// No data exist
                if (result.seihoData == null)
                {
                    return result;
                }

                // Get 製品種類
                //ma_seiho_bunrui hinSyurui = context.ma_seiho_bunrui.Where(x => x.no == result.seihoData.cd)

                tr_shisakuhin shisaku = context.tr_shisakuhin.Where(x => x.cd_shain == result.seihoData.cd_shain
                                                                        && x.nen == result.seihoData.nen
                                                                        && x.no_oi == result.seihoData.no_oi).FirstOrDefault();
                // Get shisaku info
                if (shisaku != null)
                {
                    result.pt_kotei = shisaku.pt_kotei;
                    result.cd_naiyoryo_tani = Byte.Parse(shisaku.cd_tani);
                    result.yoryo = shisaku.yoryo;
                }

                ma_haigo_header mainHaigo = context.ma_haigo_header.Where(x => x.no_seiho == Conditions.no_seiho && x.kbn_hin == Conditions.kbn_haigo).OrderBy(x => x.dt_toroku).FirstOrDefault();
                /// No haigo exist
                if (mainHaigo == null)
                {
                    result.su_code_standard = 0;
                }
                else
                {
                    /// Get su_code_standard
                    ma_kaisha kaisha = context.ma_kaisha.Where(x => x.cd_kaisha == mainHaigo.cd_kaisha).FirstOrDefault();
                    if (kaisha != null)
                    {
                        result.su_code_standard = kaisha.su_code_standard;
                    }
                }
                result.dataTab1 = context.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                result.haigoHeaderData = context.ma_haigo_header.Where(x => x.no_seiho == Conditions.no_seiho).ToList();
                result.seihoDensoData = context.ma_seiho_denso.Where(x => x.no_seiho == Conditions.no_seiho).ToList();
                /// Get nm_hin_syurui
                string cd_hin_syurui = Conditions.no_seiho.Substring(5, 1);
                ma_hin_syurui hinSyurui = context.ma_hin_syurui.Where(x => x.cd_hin_syurui == cd_hin_syurui).FirstOrDefault();
                result.nm_hin_syurui = hinSyurui == null ? "" : hinSyurui.nm_hin_syurui;
                /// Return data
                return result;
            }
        }


        /// <summary>
        /// Save all data from page [製法文書作成]
        /// Including validate check before save and save the data
        /// All data will be saved in the same transaction
        /// Once Error rising, all previous saving data will be UNDONE (ROLLBACK)
        /// </summary>
        /// <param name="value">All changeset</param>
        /// <returns>PROCESS STATUS</returns>
        public HttpResponseMessage Post([FromBody]SeihoBunsyoShakuseiSaveRequest value)
        {

            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        string STATUS = "";
                        int ShinseiLevel = GetShinseiLevel(value);

                        if (value.isUpdateShonin)
                        {
                            // Update shinsei data
                            STATUS = UpdateShinei(value, ShinseiLevel, context);
                            if (STATUS != PROCESS_SUCCESS)
                            {
                                transaction.Rollback();
                                return RisingResponse(STATUS);
                            }
                            // Update ma_seiho data
                            UpdateShoninSeiho(value.SeihoData, value, context);
                        }

                        // Save data for [表紙] (TAB 1)
                        STATUS = SaveHyoshiData(value.Hyoshi_1, ShinseiLevel, context);
                        if (STATUS != PROCESS_SUCCESS)
                        {
                            transaction.Rollback();
                            return RisingResponse(STATUS);
                        }
                        // Save data for [容器包装] (TAB 2)
                        STATUS = SaveYoukiHousouData(value.YoukiHousou_2, context);
                        if (STATUS != PROCESS_SUCCESS)
                        {
                            transaction.Rollback();
                            return RisingResponse(STATUS);
                        }
                        // Save data for [原料・機械設備・製造方法・表示事項] (TAB 3)
                        STATUS = SaveZairyoData(value.Zairyo_3, context);
                        if (STATUS != PROCESS_SUCCESS)
                        {
                            transaction.Rollback();
                            return RisingResponse(STATUS);
                        }

                        // Save data for [配合・製造上の注意事項] (TAB 4)
                        STATUS = SaveHaigoSeizoChuiJikoData(value.HaigoSeizoChuiJiko_4, context);
                        if (STATUS != PROCESS_SUCCESS)
                        {
                            transaction.Rollback();
                            return RisingResponse(STATUS);
                        }

                        // Save data for [製造工程図] (TAB 5)
                        STATUS = SaveSeizoKoteizuData(value.SeizoKoteizu_5, context);
                        if (STATUS != PROCESS_SUCCESS)
                        {
                            transaction.Rollback();
                            return RisingResponse(STATUS);
                        }

                        // Save data for [製品規格案及び取扱基準] (TAB 6)
                        STATUS = SaveSeihiKikakuanData(value.SeihiKikakuan_6, context);
                        if (STATUS != PROCESS_SUCCESS)
                        {
                            transaction.Rollback();
                            return RisingResponse(STATUS);
                        }

                        // Save data for [製法上の確認事項] (TAB 7)
                        STATUS = SaveSeihojyoKakuninjikoData(value.SeihojyoKakuninjiko_7, context);
                        if (STATUS != PROCESS_SUCCESS)
                        {
                            transaction.Rollback();
                            return RisingResponse(STATUS);
                        }

                        // Save data for [賞味期間設定表] (TAB 8)
                        STATUS = SaveShomikigenSetteiHyoData(value.ShomikigenSetteiHyo_8, context);
                        if (STATUS != PROCESS_SUCCESS)
                        {
                            transaction.Rollback();
                            return RisingResponse(STATUS);
                        }

                        // Save data for [商品開発履歴表] (TAB 9)
                        STATUS = SaveRirekiHyoData(value.RirekiHyo_9, context);
                        if (STATUS != PROCESS_SUCCESS)
                        {
                            transaction.Rollback();
                            return RisingResponse(STATUS);
                        }

                        // Save data for Main seiho data
                        STATUS = SaveSeihoData(value.SeihoData, context);
                        if (STATUS != PROCESS_SUCCESS)
                        {
                            transaction.Rollback();
                            return RisingResponse(STATUS);
                        }

                        // Complete saving all TABs
                        transaction.Commit();
                        return RisingResponse(PROCESS_SUCCESS);
                    }
                    catch (DbUpdateConcurrencyException ex)
                    {
                        // 排他エラー
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        return Request.CreateErrorResponse(HttpStatusCode.Conflict, Properties.Resources.ServiceError);
                    }
                    catch (Exception ex)
                    {
                        // 例外をエラーログに出力します。
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                    }
                }
            }
        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// Get shinsei level for update haigo header
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        private int GetShinseiLevel(SeihoBunsyoShakuseiSaveRequest Conditions)
        {
            if (Conditions == null || !Conditions.isUpdateShonin)
            {
                return LEVEL_UNCHANGE;
            }
            if (Conditions.isShonin2)
            {
                return LEVEL_3;
            }
            if (Conditions.isShonin1)
            {
                return LEVEL_2;
            }
            if (Conditions.isShinseiCreate)
            {
                return LEVEL_1;
            }
            return LEVEL_0;
        }

        /// <summary>
        /// Get shinsei level for update haigo header
        /// </summary>
        /// <param name="newDataSet"></param>
        /// <returns></returns>
        private int GetShinseiLevel(ChangeSet<ma_seiho_bunsho_hyoshi> newDataSet)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                ma_seiho_bunsho_hyoshi newData = null;
                // Get new shonin level
                if (newDataSet.Created.Count() > 0)
                {
                    newData = newDataSet.Created[0];
                }
                if (newDataSet.Updated.Count() > 0)
                {
                    newData = newDataSet.Updated[0];
                }
                if (newDataSet.Deleted.Count() > 0)
                {
                    newData = newDataSet.Deleted[0];
                }
                if (newData == null)
                {
                    return LEVEL_UNCHANGE;
                }
                // Compare with old shonin level
                ma_seiho_bunsho_hyoshi oldData = context.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == newData.no_seiho).FirstOrDefault();
                if (oldData == null)
                {
                    if (newData.cd_shonin2 != null && newData.cd_shonin2 != String.Empty)
                    {
                        return LEVEL_3;
                    }
                    if (newData.cd_shonin1 != null && newData.cd_shonin1 != String.Empty)
                    {
                        return LEVEL_2;
                    }
                    if (newData.cd_create != null && newData.cd_create != String.Empty)
                    {
                        return LEVEL_1;
                    }
                }
                else
                {
                    if (newData.cd_shonin2 != null && newData.cd_shonin2 != String.Empty)
                    {
                        return newData.cd_shonin2 != oldData.cd_shonin2 ? LEVEL_3 : LEVEL_UNCHANGE;
                    }
                    if (newData.cd_shonin1 != null && newData.cd_shonin1 != String.Empty)
                    {
                        return newData.cd_shonin1 != oldData.cd_shonin1 ? LEVEL_2 : LEVEL_UNCHANGE;
                    }
                    if (newData.cd_create != null && newData.cd_create != String.Empty)
                    {
                        return newData.cd_create != oldData.cd_create ? LEVEL_1 : LEVEL_UNCHANGE;
                    }
                    else
                    {
                        if (oldData.cd_create != null && oldData.cd_create != String.Empty)
                        {
                            return LEVEL_0;
                        }
                    }
                }

                return LEVEL_UNCHANGE;
            }
        }

        /// <summary>
        /// Update haigo header and seiho denso in shinsei mode
        /// </summary>
        /// <param name="value"></param>
        /// <param name="context"></param>
        /// <returns></returns>
        private string UpdateShinei(SeihoBunsyoShakuseiSaveRequest value, int shiseiLevel, ShohinKaihatsuEntities context)
        {
            if (value != null && shiseiLevel != LEVEL_UNCHANGE)
            {
                ChangeSet<ma_haigo_header> lstHaigoHeader = new ChangeSet<ma_haigo_header>();
                ChangeSet<ma_seiho_denso> lstSeihoDenso = new ChangeSet<ma_seiho_denso>();
                decimal userName = 0;
                Decimal.TryParse(UserInfo.GetUserNameFromIdentity(this.User.Identity), out userName);
                DateTime date = DateTime.Now;
                string strUserName = userName.ToString();
                if (value.haigoHeaderData != null)
                {
                    foreach (var item in value.haigoHeaderData)
                    {
                        if (shiseiLevel == LEVEL_0)
                        {
                            item.status = 0;
                        }
                        if (shiseiLevel == LEVEL_1)
                        {
                            item.status = 1;
                        }
                        if (shiseiLevel == LEVEL_2)
                        {
                            item.status = 2;
                        }
                        if (shiseiLevel == LEVEL_3)
                        {
                            item.status = 3;
                        }
                        //item.dt_henko = date;
                        //item.cd_koshin = strUserName;
                        lstHaigoHeader.Updated.Add(item);
                    }
                    lstHaigoHeader.AttachTo(context);
                }
                // In case remove apply
                if (value.seihoDensoData != null && value.isRemoveApply)
                {
                    foreach (var item in value.seihoDensoData)
                    {
                        item.flg_denso_taisho = false;
                        item.dt_denso_toroku = null;
                        item.cd_denso_tanto = null;
                        item.cd_denso_tanto_kaisha = null;
                        item.biko = null;
                        lstSeihoDenso.Updated.Add(item);
                    }
                    lstSeihoDenso.AttachTo(context);
                }
                context.SaveChanges();
            }
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// // Save data for [表紙] (TAB 1)
        /// </summary>
        /// <param name="value">Changeset</param>
        /// <param name="context">Entity</param>
        /// <returns>SAVING STATUS</returns>
        private string SaveHyoshiData(ChangeSet<ma_seiho_bunsho_hyoshi> value, int ShinseiLevel, ShohinKaihatsuEntities context)
        {
            if (value == null)
            {
                return PROCESS_SUCCESS;
            }
            value.SetSeihoDataSaveInfo(this.User.Identity);
            // Convert string empty to null
            foreach (var item in value.Created) 
            { 
                convertNull(item);
                var deleted = value.Deleted.FirstOrDefault();
                if (deleted != null)
                {
                    item.cd_toroku = deleted.cd_toroku;
                    item.dt_toroku = deleted.dt_toroku;
                }
                // Check conflict when create bunsho
                using (ShohinKaihatsuEntities context_search = new ShohinKaihatsuEntities())
                {
                    ma_seiho_bunsho_hyoshi existsData = context_search.ma_seiho_bunsho_hyoshi.Where(x => x.no_seiho == item.no_seiho).FirstOrDefault();
                    if (existsData != null) 
                    {
                        return CONFLICT_ERROR;
                    }
                }
                // Check shonin_memo
                if (ShinseiLevel == 3) {
                    item.shonin_memo = null;
                }
            }
            foreach (var item in value.Updated) { 
                convertNull(item);
                // Check shonin_memo
                if (ShinseiLevel == 3)
                {
                    item.shonin_memo = null;
                }
            }
            value.AttachTo(context);
            context.SaveChanges();
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// // Save data for [容器包装] (TAB 2)
        /// </summary>
        /// <param name="value">Changeset</param>
        /// <param name="context">Entity</param>
        /// <returns>SAVING STATUS</returns>
        private string SaveYoukiHousouData(SeihoBunsyoShakuseiSaveRequest.YoukiHousouTabChangeRequest value, ShohinKaihatsuEntities context)
        {
            if (value == null)
            {
                return PROCESS_SUCCESS;
            }
            if (value.headerData != null)
            {
                value.headerData.SetSeihoDataSaveInfo(this.User.Identity);
                // Convert string empty to null
                foreach (var item in value.headerData.Created) 
                { 
                    convertNull(item);
                    var deleted = value.headerData.Deleted.FirstOrDefault();
                    if (deleted != null)
                    {
                        item.cd_toroku = deleted.cd_toroku;
                        item.dt_toroku = deleted.dt_toroku;
                    }
                }
                foreach (var item in value.headerData.Updated) { convertNull(item); }
                value.headerData.AttachTo(context);
            }
            if (value.detailData != null)
            {
                // Keep toroku information if not null
                value.detailData.SetSeihoDataSaveInfo(this.User.Identity, true);
                // Convert string empty to null
                foreach (var item in value.detailData.Created) 
                {
                    convertNull(item);
                }
                foreach (var item in value.detailData.Updated) { convertNull(item); }
                value.detailData.AttachTo(context);
            }
            context.SaveChanges();
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// // Save data for [原料・機械設備・製造方法・表示事項] (TAB 3)
        /// </summary>
        /// <param name="value">Changeset</param>
        /// <param name="context">Entity</param>
        /// <returns>SAVING STATUS</returns>
        private string SaveZairyoData(ChangeSet<ma_seiho_bunsho_genryo_setsubi> value, ShohinKaihatsuEntities context)
        {
            if (value == null)
            {
                return PROCESS_SUCCESS;
            }
            value.SetSeihoDataSaveInfo(this.User.Identity);
            // Convert string empty to null
            foreach (var item in value.Created) 
            {
                convertNull(item);
                var deleted = value.Deleted.FirstOrDefault();
                if (deleted != null)
                {
                    item.cd_toroku = deleted.cd_toroku;
                    item.dt_toroku = deleted.dt_toroku;
                }
            }
            foreach (var item in value.Updated) { convertNull(item); }
            value.AttachTo(context);
            context.SaveChanges();
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// // Save data for [配合・製造上の注意事項] (TAB 4)
        /// </summary>
        /// <param name="value">Changeset</param>
        /// <param name="context">Entity</param>
        /// <returns>SAVING STATUS</returns>
        private string SaveHaigoSeizoChuiJikoData(ChangeSet<ma_seiho_bunsho_haigo_chui> value, ShohinKaihatsuEntities context)
        {
            if (value == null)
            {
                return PROCESS_SUCCESS;
            }
            value.SetSeihoDataSaveInfo(this.User.Identity);
            // Convert string empty to null
            foreach (var item in value.Created) 
            {
                convertNull(item);
                var deleted = value.Deleted.FirstOrDefault();
                if (deleted != null)
                {
                    item.cd_toroku = deleted.cd_toroku;
                    item.dt_toroku = deleted.dt_toroku;
                }
            }
            foreach (var item in value.Updated) { convertNull(item); }
            value.AttachTo(context);
            context.SaveChanges();
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// Save data for [製造工程図] (TAB 5)
        /// </summary>
        /// <param name="value">Changeset</param>
        /// <param name="context">Entity</param>
        /// <returns>SAVING STATUS</returns>
        private string SaveSeizoKoteizuData(ChangeSet<ma_seiho_bunsho_koteizu_b> value, ShohinKaihatsuEntities context)
        {
            if (value == null)
            {
                return PROCESS_SUCCESS;
            }
            value.SetSeihoDataSaveInfo(this.User.Identity, true);
            // Convert string empty to null
            foreach (var item in value.Created) 
            {
                convertNull(item);
            }
            foreach (var item in value.Updated) { convertNull(item); }
            value.AttachTo(context);
            context.SaveChanges();
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// Save data for [製品規格案及び取扱基準] (TAB 6)
        /// </summary>
        /// <param name="value">Changeset</param>
        /// <param name="context">Entity</param>
        /// <returns>SAVING STATUS</returns>
        private string SaveSeihiKikakuanData(SeihoBunsyoShakuseiSaveRequest.SeihiKikakuanTabChangeRequest value, ShohinKaihatsuEntities context)
        {
            if (value == null) 
            {
                return PROCESS_SUCCESS;
            }
            if (value.headerData != null)
            {
                value.headerData.SetSeihoDataSaveInfo(this.User.Identity);
                // Convert string empty to null
                foreach (var item in value.headerData.Created) 
                { 
                    convertNull(item);
                    ma_seiho_bunsho_seihinkikaku deleted = value.headerData.Deleted.FirstOrDefault();
                    if (deleted != null)
                    {
                        item.cd_toroku = deleted.cd_toroku;
                        item.dt_toroku = deleted.dt_toroku;
                    }
                }
                foreach (var item in value.headerData.Updated) { convertNull(item); }
                value.headerData.AttachTo(context);
            }
            if (value.detailData != null)
            {
                value.detailData.SetSeihoDataSaveInfo(this.User.Identity, true);
                // Convert string empty to null
                foreach (var item in value.detailData.Created) 
                { 
                    convertNull(item);
                }
                foreach (var item in value.detailData.Updated) { convertNull(item); }
                value.detailData.AttachTo(context);
            }
            context.SaveChanges();
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// Save data for [製法上の確認事項] (TAB 7)
        /// </summary>
        /// <param name="value">Changeset</param>
        /// <param name="context">Entity</param>
        /// <returns>SAVING STATUS</returns>
        private string SaveSeihojyoKakuninjikoData(ChangeSet<ma_seiho_bunsho_kakuninjiko> value, ShohinKaihatsuEntities context)
        {
            if (value == null)
            {
                return PROCESS_SUCCESS;
            }
            value.SetSeihoDataSaveInfo(this.User.Identity);
            // Convert string empty to null
            foreach (var item in value.Created) 
            {
                convertNull(item);
                var deleted = value.Deleted.FirstOrDefault();
                if (deleted != null)
                {
                    item.cd_toroku = deleted.cd_toroku;
                    item.dt_toroku = deleted.dt_toroku;
                }
            }
            foreach (var item in value.Updated) { convertNull(item); }
            value.AttachTo(context);
            context.SaveChanges();
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// Save data for [賞味期間設定表] (TAB 8)
        /// </summary>
        /// <param name="value">Changeset</param>
        /// <param name="context">Entity</param>
        /// <returns>SAVING STATUS</returns>
        private string SaveShomikigenSetteiHyoData(ChangeSet<ma_seiho_bunsho_shomikigen> value, ShohinKaihatsuEntities context)
        {
            if (value == null)
            {
                return PROCESS_SUCCESS;
            }
            value.SetSeihoDataSaveInfo(this.User.Identity);
            // Convert string empty to null
            foreach (var item in value.Created) 
            {
                convertNull(item);
                var deleted = value.Deleted.FirstOrDefault();
                if (deleted != null)
                {
                    item.cd_toroku = deleted.cd_toroku;
                    item.dt_toroku = deleted.dt_toroku;
                }
            }
            foreach (var item in value.Updated) { convertNull(item); }
            value.AttachTo(context);
            context.SaveChanges();
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// Save data for [商品開発履歴表] (TAB 9)
        /// </summary>
        /// <param name="value">Changeset</param>
        /// <param name="context">Entity</param>
        /// <returns>SAVING STATUS</returns>
        private string SaveRirekiHyoData(ChangeSet<ma_seiho_bunsho_shohin_rireki> value, ShohinKaihatsuEntities context)
        {
            if (value == null)
            {
                return PROCESS_SUCCESS;
            }
            value.SetSeihoDataSaveInfo(this.User.Identity);
            // Convert string empty to null
            foreach (var item in value.Created) 
            {
                convertNull(item);
                var deleted = value.Deleted.FirstOrDefault();
                if (deleted != null)
                {
                    item.cd_toroku = deleted.cd_toroku;
                    item.dt_toroku = deleted.dt_toroku;
                }
            }
            foreach (var item in value.Updated) { convertNull(item); }
            value.AttachTo(context);
            context.SaveChanges();
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// Save data for Main seiho data
        /// </summary>
        /// <param name="value">Changeset</param>
        /// <param name="context">Entity</param>
        /// <returns>SAVING STATUS</returns>
        private string SaveSeihoData(ChangeSet<ma_seiho> value, ShohinKaihatsuEntities context)
        {
            if (value == null)
            {
                return PROCESS_SUCCESS;
            }
            //value.SetSeihoDataSaveInfo(this.User.Identity);
            value.AttachTo(context);
            context.SaveChanges();
            return PROCESS_SUCCESS;
        }

        /// <summary>
        /// Convert string empty to null (TAB 1)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_hyoshi data)
        {
            if (data != null)
            {
                if (data.no_hyojiyo_seiho == String.Empty) data.no_hyojiyo_seiho = null;
                if (data.cd_tokisaki == String.Empty) data.cd_tokisaki = null;
                if (data.cd_brand == String.Empty) data.cd_brand = null;
                if (data.dt_seizo_kaishi_yotei == String.Empty) data.dt_seizo_kaishi_yotei = null;
                if (data.nm_shozoku_group == String.Empty) data.nm_shozoku_group = null;
                if (data.nm_shozoku_team == String.Empty) data.nm_shozoku_team = null;
                if (data.nm_team_leader == String.Empty) data.nm_team_leader = null;
                if (data.nm_juyo_gijitsu_joho == String.Empty) data.nm_juyo_gijitsu_joho = null;
                if (data.nm_zenbankara_henkoten == String.Empty) data.nm_zenbankara_henkoten = null;
                if (data.nm_seihin_tokucho == String.Empty) data.nm_seihin_tokucho = null;
                if (data.nm_kyocho_hyoji == String.Empty) data.nm_kyocho_hyoji = null;
                if (data.cd_create_shozoku_busho == String.Empty) data.cd_create_shozoku_busho = null;
                if (data.nm_create_shozoku_busho == String.Empty) data.nm_create_shozoku_busho = null;
                if (data.cd_create == String.Empty) data.cd_create = null;
                if (data.nm_create == String.Empty) data.nm_create = null;
                if (data.cd_shonin1_shozoku_busho == String.Empty) data.cd_shonin1_shozoku_busho = null;
                if (data.nm_shonin1_shozoku_busho == String.Empty) data.nm_shonin1_shozoku_busho = null;
                if (data.cd_shonin1 == String.Empty) data.cd_shonin1 = null;
                if (data.nm_shonin1 == String.Empty) data.nm_shonin1 = null;
                if (data.cd_shonin2_shozoku_busho == String.Empty) data.cd_shonin2_shozoku_busho = null;
                if (data.nm_shonin2_shozoku_busho == String.Empty) data.nm_shonin2_shozoku_busho = null;
                if (data.cd_shonin2 == String.Empty) data.cd_shonin2 = null;
                if (data.nm_shonin2 == String.Empty) data.nm_shonin2 = null;
            }
        }

        /// <summary>
        /// Convert string empty to null (TAB 2 header)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_yokihoso data)
        {
            if (data != null)
            {
                if (data.nm_yoki_hoso == String.Empty) data.nm_yoki_hoso = null;
                if (data.nm_yoki_hoso_shizai01 == String.Empty) data.nm_yoki_hoso_shizai01 = null;
                if (data.nm_yoki_hoso_shizai02 == String.Empty) data.nm_yoki_hoso_shizai02 = null;
                if (data.nm_yoki_hoso_shizai03 == String.Empty) data.nm_yoki_hoso_shizai03 = null;
                if (data.nm_yoki_hoso_shizai04 == String.Empty) data.nm_yoki_hoso_shizai04 = null;
                if (data.nm_yoki_hoso_shizai05 == String.Empty) data.nm_yoki_hoso_shizai05 = null;
                if (data.nm_yoki_hoso_shizai06 == String.Empty) data.nm_yoki_hoso_shizai06 = null;
                if (data.nm_yoki_hoso_shizai07 == String.Empty) data.nm_yoki_hoso_shizai07 = null;
                if (data.nm_yoki_hoso_shizai08 == String.Empty) data.nm_yoki_hoso_shizai08 = null;
                if (data.nm_yoki_hoso_shizai09 == String.Empty) data.nm_yoki_hoso_shizai09 = null;
                if (data.nm_yoki_hoso_shizai10 == String.Empty) data.nm_yoki_hoso_shizai10 = null;
                if (data.nm_free_title_komoku == String.Empty) data.nm_free_title_komoku = null;
            }
        }

        /// <summary>
        /// Convert string empty to null (TAB 2 detail)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_yokihoso_shizai data)
        {
            if (data != null)
            {
                if (data.nm_maker01 == String.Empty) data.nm_maker01 = null;
                if (data.nm_maker02 == String.Empty) data.nm_maker02 = null;
                if (data.nm_maker03 == String.Empty) data.nm_maker03 = null;
                if (data.nm_maker04 == String.Empty) data.nm_maker04 = null;
                if (data.nm_maker05 == String.Empty) data.nm_maker05 = null;
                if (data.nm_zaishitsu01 == String.Empty) data.nm_zaishitsu01 = null;
                if (data.nm_zaishitsu02 == String.Empty) data.nm_zaishitsu02 = null;
                if (data.nm_zaishitsu03 == String.Empty) data.nm_zaishitsu03 = null;
                if (data.nm_zaishitsu04 == String.Empty) data.nm_zaishitsu04 = null;
                if (data.nm_zaishitsu05 == String.Empty) data.nm_zaishitsu05 = null;
                if (data.nm_recycle_hyoji01 == String.Empty) data.nm_recycle_hyoji01 = null;
                if (data.nm_recycle_hyoji02 == String.Empty) data.nm_recycle_hyoji02 = null;
                if (data.nm_recycle_hyoji03 == String.Empty) data.nm_recycle_hyoji03 = null;
                if (data.nm_recycle_hyoji04 == String.Empty) data.nm_recycle_hyoji04 = null;
                if (data.nm_recycle_hyoji05 == String.Empty) data.nm_recycle_hyoji05 = null;
                if (data.nm_size01 == String.Empty) data.nm_size01 = null;
                if (data.nm_size02 == String.Empty) data.nm_size02 = null;
                if (data.nm_size03 == String.Empty) data.nm_size03 = null;
                if (data.nm_size04 == String.Empty) data.nm_size04 = null;
                if (data.nm_size05 == String.Empty) data.nm_size05 = null;
                if (data.nm_free_komoku == String.Empty) data.nm_free_komoku = null;
            }
        }

        /// <summary>
        /// Convert string empty to null (TAB 3)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_genryo_setsubi data)
        {
            if (data != null)
            {
                if (data.nm_genryo_free_komoku == String.Empty) data.nm_genryo_free_komoku = null;
                if (data.nm_kikai_setsubi_free_komoku == String.Empty) data.nm_kikai_setsubi_free_komoku = null;
                if (data.nm_seizo_hoho_free_komoku == String.Empty) data.nm_seizo_hoho_free_komoku = null;
                if (data.nm_hyoji_jiko_free_komoku == String.Empty) data.nm_hyoji_jiko_free_komoku = null;
            }
        }

        /// <summary>
        /// Convert string empty to null (TAB 4)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_haigo_chui data)
        {
            if (data != null)
            {
                if (data.nm_haigo_free_tokki_jiko == String.Empty) data.nm_haigo_free_tokki_jiko = null;
                if (data.nm_chuiten_free_komoku == String.Empty) data.nm_chuiten_free_komoku = null;
                if (data.pt_kotei == String.Empty) data.pt_kotei = null;
            }
        }

        /// <summary>
        /// Convert string empty to null (TAB 5)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_koteizu_b data)
        {
            if (data != null)
            {
                if (data.nm_free_mark == String.Empty) data.nm_free_mark = null;
                if (data.cd_input_genryo == String.Empty) data.cd_input_genryo = null;
                if (data.nm_input_genryo == String.Empty) data.nm_input_genryo = null;
                if (data.id_color == String.Empty) data.id_color = null;
            }
        }

        /// <summary>
        /// Convert string empty to null (TAB 6)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_seihinkikaku data)
        {
            if (data != null) 
            {
                if (data.su_naiyoryo == String.Empty)
                {
                    data.su_naiyoryo = null;
                }
                else
                {
                    if (data.su_naiyoryo != null) data.su_naiyoryo = data.su_naiyoryo.Replace(",", "");
                }
                if (data.nm_free_tokuseichi == String.Empty) data.nm_free_tokuseichi = null;
                if (data.nm_free_biseibutsu == String.Empty) data.nm_free_biseibutsu = null;
                if (data.kbn_shomikikan == String.Empty) data.kbn_shomikikan = null;
                if (data.kbn_shomikikan_seizo_fukumu == String.Empty) data.kbn_shomikikan_seizo_fukumu = null;
                if (data.kbn_shomikikan_tani == String.Empty) data.kbn_shomikikan_tani = null;
                if (data.kbn_toriatsukai_ondo == String.Empty) data.kbn_toriatsukai_ondo = null;
                if (data.kbn_meisho == String.Empty) data.kbn_meisho = null;
                if (data.nm_meisho_hinmei == String.Empty) data.nm_meisho_hinmei = null;
                if (data.dt_nendo_sokuteichi == String.Empty) data.dt_nendo_sokuteichi = null;
                if (data.nm_seihintokusechi_title1 == String.Empty) data.nm_seihintokusechi_title1 = null;
                if (data.nm_seihintokusechi_title2 == String.Empty) data.nm_seihintokusechi_title2 = null;
                if (data.nm_seihintokusechi_title3 == String.Empty) data.nm_seihintokusechi_title3 = null;
            }
        }

        /// <summary>
        /// Convert string empty to null (TAB 6)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_tokuseichi data)
        {
            if (data != null)
            {
                if (data.nm_seihintokusechi_combo_input == String.Empty) data.nm_seihintokusechi_combo_input = null;
                if (data.nm_seihintokusechi_ronrichi == String.Empty) data.nm_seihintokusechi_ronrichi = null;
                if (data.nm_seihintokusechi_kenshisakuhin == String.Empty) data.nm_seihintokusechi_kenshisakuhin = null;
                if (data.nm_seihintokusechi_tp == String.Empty) data.nm_seihintokusechi_tp = null;
                if (data.nm_seihintokusechi_input1 == String.Empty) data.nm_seihintokusechi_input1 = null;
                if (data.nm_seihintokusechi_input2 == String.Empty) data.nm_seihintokusechi_input2 = null;
                if (data.nm_seihintokusechi_input3 == String.Empty) data.nm_seihintokusechi_input3 = null;
            }
        }

        /// <summary>
        /// Convert string empty to null (TAB 7)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_kakuninjiko data)
        {
            if (data != null)
            {
                if (data.nm_free_kanren_tokkyo == String.Empty) data.nm_free_kanren_tokkyo = null;
                if (data.nm_free_tasha_kanren_tokkyo == String.Empty) data.nm_free_tasha_kanren_tokkyo = null;
                if (data.nm_shokuhin_tenkabutsu == String.Empty) data.nm_shokuhin_tenkabutsu = null;
                if (data.nm_eigyo_kyoka_gyoshu01 == String.Empty) data.nm_eigyo_kyoka_gyoshu01 = null;
                if (data.nm_eigyo_kyoka_gyoshu02 == String.Empty) data.nm_eigyo_kyoka_gyoshu02 = null;
                if (data.nm_eigyo_kyoka_gyoshu03 == String.Empty) data.nm_eigyo_kyoka_gyoshu03 = null;
                if (data.nm_eigyo_kyoka_gyoshu04 == String.Empty) data.nm_eigyo_kyoka_gyoshu04 = null;
                if (data.nm_eigyo_kyoka_gyoshu05 == String.Empty) data.nm_eigyo_kyoka_gyoshu05 = null;
                if (data.nm_free_genryo_shitei == String.Empty) data.nm_free_genryo_shitei = null;
                if (data.nm_free_genryo_seigen == String.Empty) data.nm_free_genryo_seigen = null;
                if (data.nm_free_sonota == String.Empty) data.nm_free_sonota = null;
            }
        }

        /// <summary>
        /// Convert string empty to null (TAB 8)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_shomikigen data)
        {
            if (data != null)
            {
                if (data.nm_hozon_shiken_kankaku == String.Empty) data.nm_hozon_shiken_kankaku = null;
                if (data.nm_hozon_shiken_kikan == String.Empty) data.nm_hozon_shiken_kikan = null;
                if (data.biko == String.Empty) data.biko = null;
                if (data.nm_shomikigen_biko == String.Empty) data.nm_shomikigen_biko = null;
                if (data.nm_rikagaku_shiken_komoku == String.Empty) data.nm_rikagaku_shiken_komoku = null;
                if (data.nm_rikagaku_shiken_hinshitsu == String.Empty) data.nm_rikagaku_shiken_hinshitsu = null;
                if (data.nm_rikagaku_shiken_kikan == String.Empty) data.nm_rikagaku_shiken_kikan = null;
                if (data.nm_rikagaku_shiken_hokoku == String.Empty) data.nm_rikagaku_shiken_hokoku = null;
                if (data.nm_biseibutsu_shiken_komoku == String.Empty) data.nm_biseibutsu_shiken_komoku = null;
                if (data.nm_biseibutsu_shiken_hinshitsu == String.Empty) data.nm_biseibutsu_shiken_hinshitsu = null;
                if (data.nm_biseibutsu_shiken_kikan == String.Empty) data.nm_biseibutsu_shiken_kikan = null;
                if (data.nm_biseibutsu_shiken_hokoku == String.Empty) data.nm_biseibutsu_shiken_hokoku = null;
                if (data.nm_kanno_hyoka_komoku == String.Empty) data.nm_kanno_hyoka_komoku = null;
                if (data.nm_kanno_hyoka_hinshitsu == String.Empty) data.nm_kanno_hyoka_hinshitsu = null;
                if (data.nm_kanno_hyoka_kikan == String.Empty) data.nm_kanno_hyoka_kikan = null;
                if (data.nm_kanno_hyoka_hokoku == String.Empty) data.nm_kanno_hyoka_hokoku = null;
                if (data.nm_kaihatsubusho == String.Empty) data.nm_kaihatsubusho = null;
                if (data.nm_kaihatsutanto == String.Empty) data.nm_kaihatsutanto = null;
            }
        }


        /// <summary>
        /// Convert string empty to null (TAB 9)
        /// </summary>
        /// <param name="data"></param>
        private void convertNull(ma_seiho_bunsho_shohin_rireki data)
        {
            if (data != null)
            {
                if (data.no_seiho_rireki_naiyo == String.Empty) data.no_seiho_rireki_naiyo = null;
            }
        }

        /// <summary>
        /// Create response message, base on saving status
        /// </summary>
        /// <param name="STATUS">SAVING STATUS</param>
        /// <returns>ResponseMessage</returns>
        private HttpResponseMessage RisingResponse(string STATUS)
        {
            if (STATUS == PROCESS_SUCCESS)
            {
                return Request.CreateResponse(HttpStatusCode.OK, PROCESS_SUCCESS);
            }
            if (STATUS == CONFLICT_ERROR)
            {
                return Request.CreateResponse(HttpStatusCode.Conflict, CONFLICT_ERROR);
            }
            else
                return Request.CreateResponse(HttpStatusCode.BadRequest, STATUS);
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="value"></param>
        /// <param name="shoninLevel"></param>
        private void UpdateShoninSeiho(ChangeSet<ma_seiho> value, SeihoBunsyoShakuseiSaveRequest Conditions, ShohinKaihatsuEntities context)
        {
            ma_seiho data = (value == null) ? null : (value.Updated.Count > 0 ? value.Updated[0] : null);
            if (data == null || Conditions == null || !Conditions.isUpdateShonin)
            {
                return;
            }
            UserInfo userData = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
            DateTime NOW = DateTime.Now;

            // In case remove apply 
            if (Conditions.isRemoveApply)
            {
                data.dt_seiho_shinsei = null;
                InsertLog(Conditions.no_seiho, userData, context, false);
            }
            bool isUpdateShinsei = false;
            if (Conditions.isShinseiCreate)
            {
                data.dt_seiho_sakusei = NOW.Date;
                data.nm_seiho_sakusei_1 = userData.Name;
                isUpdateShinsei = true;
            }
            if (Conditions.isShonin1)
            {
                data.nm_seiho_sakusei_2 = userData.Name;
                isUpdateShinsei = true;
            }
            if (Conditions.isShonin2)
            {
                data.nm_seiho_sekinin = userData.Name;
                data.dt_seiho_shinsei = NOW.Date;
                InsertLog(Conditions.no_seiho, userData, context, true);
                isUpdateShinsei = true;
            }
            // In case apply
            if (isUpdateShinsei)
            {
                data.cd_shinsei_tanto_kaisha = Int32.Parse(userData.cd_kaisha);
                data.cd_shinsei_tanto = userData.EmployeeCD.ToString();
            }
        }

        /// <summary>
        /// Create LOG when shonin2 apply or cancel shonin2 apply
        /// </summary>
        /// <param name="no_seiho"></param>
        /// <param name="userData"></param>
        /// <param name="context"></param>
        /// <param name="isApply"></param>
        private void InsertLog(string no_seiho, UserInfo userData, ShohinKaihatsuEntities context, bool isApply)
        {
            ChangeSet<tr_event_log> Log = new ChangeSet<tr_event_log>();
            tr_event_log LogData = new tr_event_log();
            // Create LOG data
            if (userData == null)
            {
                return;
            }
            LogData.no_seiho = no_seiho;
            if (userData.cd_kaisha != null)
            {
                LogData.cd_tanto_kaisha = Int32.Parse(userData.cd_kaisha);
            }
            LogData.cd_tanto = userData.EmployeeCD.ToString();
            LogData.cd_koshin = userData.EmployeeCD.ToString();
            // Apply or cancel apply
            LogData.nm_shori = isApply ? LB_APPLY_LOG : LB_CANCEL_APPLY_LOG;
            LogData.nm_ope = LB_OPE_LOG;
            LogData.dt_shori = DateTime.Now;
            LogData.ip_address = (new CommonController()).GetIPClientAddress();
            LogData.kbn_system = 0;
            Log.Created.Add(LogData);
            // Save created LOG
            context.sp_shohinkaihatsu_insert_200_event_log(LogData.no_seiho
                                                         , LogData.cd_tanto_kaisha
                                                         , LogData.cd_tanto
                                                         , LogData.cd_koshin
                                                         , LogData.nm_shori
                                                         , LogData.nm_ope
                                                         , LogData.dt_shori
                                                         , LogData.ip_address
                                                         , LogData.kbn_system);
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// 製法文書作成- Page load search 
    /// </summary>
    public class SeihoBunsyoShakuseiSearchRequest
    {
        public string no_seiho { get; set; }
        public int? kbn_haigo { get; set; }
         
    }

    /// <summary>
    /// 製法文書作成- Page load search result
    /// </summary>
    public class SeihoBunsyoShakuseiSearchResponse
    {
        public ma_seiho seihoData { get; set; }
        public int su_code_standard { get; set; }
        public ma_seiho_bunsho_hyoshi dataTab1 { get; set; }
        public string pt_kotei { get; set; }
        public byte cd_naiyoryo_tani { get; set; }
        public string yoryo { get; set; }
        public string nm_hin_syurui { get; set; }
        // List of haigo header use for update shinsei
        public List<ma_haigo_header> haigoHeaderData { get; set; }
        // List of seiho denso use for update shisei
        public List<ma_seiho_denso> seihoDensoData { get; set; }
    }

    /// <summary>
    /// Saving data
    /// </summary>
    public class SeihoBunsyoShakuseiSaveRequest
    {
        // Changeset tab 1 [表紙]
        public ChangeSet<ma_seiho_bunsho_hyoshi> Hyoshi_1 { get; set; }
        // Changeset tab 2 [容器包装]
        public YoukiHousouTabChangeRequest YoukiHousou_2 { get; set; }
        // Changeset tab 3 [原料・機械設備・製造方法・表示事項]
        public ChangeSet<ma_seiho_bunsho_genryo_setsubi> Zairyo_3 { get; set; }
        // Changeset tab 4 [配合・製造上の注意事項]
        public ChangeSet<ma_seiho_bunsho_haigo_chui> HaigoSeizoChuiJiko_4 { get; set; }
        // Changeset tab 5 [製造工程図]
        public ChangeSet<ma_seiho_bunsho_koteizu_b> SeizoKoteizu_5 { get; set; }
        // Changeset tab 6 [製品規格案及び取扱基準]
        public SeihiKikakuanTabChangeRequest SeihiKikakuan_6 { get; set; }
        // Changeset tab 7 [製法上の確認事項]
        public ChangeSet<ma_seiho_bunsho_kakuninjiko> SeihojyoKakuninjiko_7 { get; set; }
        // Changeset tab 8 [賞味期間設定表]
        public ChangeSet<ma_seiho_bunsho_shomikigen> ShomikigenSetteiHyo_8 { get; set; }
        // Changeset tab 9 [商品開発履歴表]
        public ChangeSet<ma_seiho_bunsho_shohin_rireki> RirekiHyo_9 { get; set; }
        // Changeset ma_seiho
        public ChangeSet<ma_seiho> SeihoData { get; set; }
        // List of haigo header use for update shinsei
        public List<ma_haigo_header> haigoHeaderData { get; set; }
        // List of seiho denso use for update shisei
        public List<ma_seiho_denso> seihoDensoData { get; set; }
        // Is updated shonin
        public bool isUpdateShonin { get; set; }
        // Is removed shonin
        public bool isRemoveApply { get; set; }
        // Is shinsei create
        public bool isShinseiCreate { get; set; }
        // Is shonin1
        public bool isShonin1 { get; set; }
        // Is shonin2
        public bool isShonin2 { get; set; }
        // Old shonin level
        public int oldShoninLevel { get; set; }
        // no_seiho
        public string no_seiho { get; set; }

        public class SeihiKikakuanTabChangeRequest
        {
            public ChangeSet<ma_seiho_bunsho_seihinkikaku> headerData { get; set; }
            public ChangeSet<ma_seiho_bunsho_tokuseichi> detailData { get; set; }
        }

        public class YoukiHousouTabChangeRequest
        {
            public ChangeSet<ma_seiho_bunsho_yokihoso> headerData { get; set; }
            public ChangeSet<ma_seiho_bunsho_yokihoso_shizai> detailData { get; set; }
        }
    }

    #endregion
}
