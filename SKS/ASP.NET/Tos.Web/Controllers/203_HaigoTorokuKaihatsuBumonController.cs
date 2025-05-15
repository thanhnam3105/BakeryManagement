using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Http.OData.Query;
// TODO:DbContextの名前空間を指定します。
using Tos.Web.Data;
using Tos.Web.Controllers;
using System.Web.Http.OData;
using System.Data.Common;
using System.Data.Objects;
using Tos.Web.Logging;
using System.Threading;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class HaigoTorokuKaihatsuBumonController : ApiController
    {
        #region "Controllerで公開するAPI"
        private const int KbnHin_Haigo = 3;
        private const int kbnHin_Genryo = 1;
        private const int Genryo_rangeMin = 51980;
        private const int Genryo_rangeMax = 51989;
        private const int MaxNoseq = 9999;
        private const int Mode_Seiho_Shinki_Toroku = 1;
        private const int Mode_Shosai = 2;
        private const int Mode_Haigo_Shinki = 3;
        private const int Mode_Seiho_Copy = 4;
        private const int KbnHin_Sagyo = 9;
        private const decimal minHaigo = 199999;
        private static List<processID> priorityInsertSeihoProcessing = new List<processID>();
        private static long p_ID = 0;

        ///**Method
        // * Get data mode shosai
        // */
        public Object getDataModeShosai(decimal cd_haigo)
        {
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;
           
            dynamic result = new System.Dynamic.ExpandoObject();
            result.ma_haigo_header = new System.Dynamic.ExpandoObject();
            result.addColumnHeader = new System.Dynamic.ExpandoObject();
            result.listAddColumnMeisai = new List<System.Dynamic.ExpandoObject>();

            //get ma_haigo_header.
            ma_haigo_header haigo_header = new ma_haigo_header();
            haigo_header = (from m in context.ma_haigo_header
                            where m.cd_haigo == cd_haigo
                            select m).FirstOrDefault();
            result.ma_haigo_header = haigo_header;

            if (haigo_header != null)
            {
                //get ma_seiho
                ma_seiho seiho = new ma_seiho();
                seiho = (from m in context.ma_seiho
                         where m.no_seiho == haigo_header.no_seiho
                         select m).FirstOrDefault();
                result.ma_seiho = seiho;

                if (seiho == null)
                {
                    result.ma_haigo_header = null;
                    return result;
                }
                else
                {
                    int kaisha = int.Parse(haigo_header.no_seiho.Substring(0,4));   
                    result.ma_haigo_header = haigo_header;
                    //get list ma_haigo_meisai
                    List<ma_haigo_meisai> listHaigoMeisai = new List<ma_haigo_meisai>();
                    listHaigoMeisai = (from m in context.ma_haigo_meisai
                                       where m.cd_haigo == haigo_header.cd_haigo
                                       orderby m.no_kotei, m.no_tonyu
                                       select m).ToList();
                    if (listHaigoMeisai.Count == 0)
                    {
                        result.ma_haigo_header = null;
                        result.ma_seiho = null;
                        return result;
                    }
                    else
                    {
                        result.listHaigoMeisai = listHaigoMeisai;

                        //get name user toroku
                        ma_user_togo user_toroku = new ma_user_togo();
                        decimal cd_toroku = decimal.Parse(haigo_header.cd_toroku);
                        user_toroku = (from m in context.ma_user_togo
                                        where m.id_user == cd_toroku
                                        select m).FirstOrDefault();

                        if (user_toroku == null)
                        {
                            result.addColumnHeader.nm_user_toroku = null;
                        }
                        else
                        {
                            result.addColumnHeader.nm_user_toroku = user_toroku.nm_user;
                        }

                        //get name user koshin
                        //ma_user_togo user_koshin = new ma_user_togo();
                        ma_user_togo user_koshin = null;

                        decimal cd_koshin ;
                        if (Decimal.TryParse(haigo_header.cd_koshin, out cd_koshin))
                        {
                            user_koshin = (from m in context.ma_user_togo
                                           where m.id_user == cd_koshin
                                           select m).FirstOrDefault();
                        }

                        if (user_koshin == null)
                        {
                            result.addColumnHeader.nm_user_henko = null;
                        }
                        else
                        {
                            result.addColumnHeader.nm_user_henko = user_koshin.nm_user;
                        }

                        string status = haigo_header.status.ToString();

                        //get name status.
                        ma_literal literal = new ma_literal();
                        literal = (from m in context.ma_literal
                                   where m.cd_category == Properties.Settings.Default.kbn_status
                                   && m.cd_literal == status
                                   select m).FirstOrDefault();

                        result.addColumnHeader.nm_status = literal.nm_literal;

                        //get list ma_seihin_seiho
                        List<ma_seihin_seiho> listSeihinSeiho = new List<ma_seihin_seiho>();
                        listSeihinSeiho = (from m in context.ma_seihin_seiho
                                           where m.cd_haigo == haigo_header.cd_haigo
                                           select m).ToList();

                        result.listSeihinSeiho = listSeihinSeiho;

                        if (listSeihinSeiho.Count > 0)
                        {
                            //get nm_seihin
                            ma_seihin_seiho seihin_seiho = new ma_seihin_seiho();
                            seihin_seiho = (from m in listSeihinSeiho
                                            where m.no_yusen == 1
                                            select m).FirstOrDefault();

                            if (seihin_seiho == null)
                            {
                                result.addColumnHeader.nm_seihin = null;
                            }
                            else
                            {
                                result.addColumnHeader.nm_seihin = seihin_seiho.nm_seihin;
                            }
                        }
                        else
                        {
                            result.addColumnHeader.nm_seihin = null;
                        }

                        //get name kojyo
                        vw_kaisha_kojyo kaisha_kojyo = new vw_kaisha_kojyo();
                        kaisha_kojyo = (from m in context.vw_kaisha_kojyo
                                        where m.cd_kaisha == haigo_header.cd_kaisha_daihyo
                                        && m.cd_kojyo == haigo_header.cd_kojyo_daihyo
                                        select m).FirstOrDefault();
                        if (kaisha_kojyo == null)
                        {
                            result.addColumnHeader.nm_kojyo = null;
                            result.addColumnHeader.su_code_standard = null;
                        }
                        else
                        {
                            result.addColumnHeader.nm_kojyo = kaisha_kojyo.nm_kaisha + "　　" + kaisha_kojyo.nm_kojyo;
                            result.addColumnHeader.su_code_standard = kaisha_kojyo.su_code_standard;
                        }

                        List<ma_haigo_header> listHeader = new List<ma_haigo_header>();
                        listHeader = (from m in context.ma_haigo_header
                                      where m.no_seiho == haigo_header.no_seiho
                                      && m.cd_haigo != haigo_header.cd_haigo
                                      select m).ToList();

                        ma_haigo_header header = new ma_haigo_header();
                        header = (from m in listHeader
                                  where m.kbn_hin == KbnHin_Haigo
                                  && m.cd_haigo != haigo_header.cd_haigo
                                  select m).FirstOrDefault();
                        
                        result.addColumnHeader.countNumberHaigo = listHeader.Count;

                        if (header != null)
                        {
                            result.addColumnHeader.haveHaigo = true;
                        }
                        else
                        {
                            result.addColumnHeader.haveHaigo = false;
                        }

                        for (int i = 0; i < listHaigoMeisai.Count; i++)
                        {
                            dynamic addColumnMeisai = new System.Dynamic.ExpandoObject();

                            //get info hinmei
                            vw_hin hin = new vw_hin();
                            decimal cd_hin = listHaigoMeisai[i].cd_hin;
                            int kbn_hin = listHaigoMeisai[i].kbn_hin;
                            if (kbn_hin == KbnHin_Sagyo)
                            {
                                addColumnMeisai.no_kikaku = null;
                                addColumnMeisai.hijyu = null;
                                addColumnMeisai.cd_tani_hin = null;
                                addColumnMeisai.kbn_shikakari = null;
                                addColumnMeisai.kbn_hin = KbnHin_Sagyo;
                                addColumnMeisai.hasHinmei = true;
                                result.listAddColumnMeisai.Add(addColumnMeisai);
                            }
                            else
                            {
                                hin = (from m in context.vw_hin
                                       where m.cd_kaisha == haigo_header.cd_kaisha_daihyo
                                       && m.cd_kojyo == haigo_header.cd_kojyo_daihyo
                                       && m.cd_hin == cd_hin
                                       && m.kbn_hin_toroku == kbn_hin
                                       && m.kbn_shikakari == 0
                                       select m).FirstOrDefault();

                                if (hin != null)
                                {
                                    addColumnMeisai.no_kikaku = hin.no_kikaku;
                                    addColumnMeisai.hijyu = hin.hijyu;
                                    addColumnMeisai.cd_tani_hin = hin.cd_tani_hin;
                                    addColumnMeisai.kbn_shikakari = hin.kbn_shikakari;
                                    addColumnMeisai.kbn_hin = hin.kbn_hin;
                                    addColumnMeisai.hasHinmei = true;
                                }
                                else
                                {
                                    vw_hin_shikakari hin_shikakari = new vw_hin_shikakari();
                                    hin_shikakari = (from m in context.vw_hin_shikakari
                                                     where m.cd_kaisha == kaisha
                                                     && m.kbn_hin == kbn_hin
                                                     && m.cd_hin == cd_hin
                                                     && m.kbn_shikakari == 0
                                                     select m).FirstOrDefault();

                                    if (hin_shikakari == null)
                                    {
                                        addColumnMeisai.no_kikaku = null;
                                        addColumnMeisai.hijyu = null;
                                        addColumnMeisai.cd_tani_hin = null;
                                        addColumnMeisai.kbn_shikakari = null;
                                        addColumnMeisai.kbn_hin = kbn_hin;
                                        addColumnMeisai.hasHinmei = false;
                                    }
                                    else
                                    {
                                        addColumnMeisai.no_kikaku = hin_shikakari.no_kikaku;
                                        addColumnMeisai.hijyu = hin_shikakari.hijyu;
                                        addColumnMeisai.cd_tani_hin = hin_shikakari.cd_tani_hin;
                                        addColumnMeisai.kbn_shikakari = hin_shikakari.kbn_shikakari;
                                        addColumnMeisai.kbn_hin = hin_shikakari.kbn_hin;
                                        addColumnMeisai.hasHinmei = true;
                                    }
                                }

                                result.listAddColumnMeisai.Add(addColumnMeisai);
                            }
                        }

                        //ma_seiho_denso
                        List<ma_seiho_denso> listSeihoDenso = new List<ma_seiho_denso>();
                        listSeihoDenso = (from m in context.ma_seiho_denso
                                          where m.no_seiho == haigo_header.no_seiho
                                          select m).ToList();
                        result.listSeihoDenso = listSeihoDenso;

                        //ma_seizo_line
                        List<ma_seizo_line> listSeizoLine = new List<ma_seizo_line>();
                        listSeizoLine = (from m in context.ma_seizo_line
                                         where m.cd_haigo == haigo_header.cd_haigo
                                         select m).ToList();
                        result.listSeizoLine = listSeizoLine;

                    }
                }

            }

            return result;
        }

        ///**Method
        // * Get data mode haigo shinki
        // */
        public Object getDataModeHaigoShinki(string no_seiho, decimal cd_haigo)
        {
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;

            dynamic result = new System.Dynamic.ExpandoObject();
            //result.ma_haigo_header = new System.Dynamic.ExpandoObject();
            result.addColumnHeader = new System.Dynamic.ExpandoObject();

            //get ma_seiho
            ma_seiho seiho = new ma_seiho();
            seiho = (from m in context.ma_seiho
                     where m.no_seiho == no_seiho
                     select m).FirstOrDefault();
            result.ma_seiho = seiho;
            //get haigo_header
            result.cd_seiho_bunrui = context.ma_haigo_header.Where(x => x.cd_haigo == cd_haigo).Select(x => x.cd_seiho_bunrui).FirstOrDefault();

            if (seiho != null)
            {
                //get ma_seiho_denso
                ma_seiho_denso seiho_denso = new ma_seiho_denso();
                seiho_denso = (from m in context.ma_seiho_denso
                                  where m.no_seiho == no_seiho
                                  && m.flg_daihyo_kojyo == true
                                  select m).FirstOrDefault();
                result.ma_seiho_denso = seiho_denso;

                //get name kojyo
                vw_kaisha_kojyo kaisha_kojyo = new vw_kaisha_kojyo();
                kaisha_kojyo = (from m in context.vw_kaisha_kojyo
                                where m.cd_kaisha == seiho_denso.cd_kaisha
                                && m.cd_kojyo == seiho_denso.cd_kojyo
                                select m).FirstOrDefault();
                if (kaisha_kojyo == null)
                {
                    result.addColumnHeader.nm_kojyo = null;
                }
                else
                {
                    result.addColumnHeader.nm_kojyo = kaisha_kojyo.nm_kaisha + "      " + kaisha_kojyo.nm_kojyo;
                }
                result.addColumnHeader.cd_kaisha_daihyo = seiho_denso.cd_kaisha;
                result.addColumnHeader.cd_kojyo_daihyo = seiho_denso.cd_kojyo;
                result.addColumnHeader.qty_kihon = kaisha_kojyo.qty_kihon;
                result.addColumnHeader.su_code_standard = kaisha_kojyo.su_code_standard;
                List<ma_seiho_denso> listSeihoDenso = new List<ma_seiho_denso>();
                listSeihoDenso = (from m in context.ma_seiho_denso
                                  where m.no_seiho == no_seiho
                                  select m).ToList();
                result.listSeihoDenso = listSeihoDenso;

                ma_haigo_header haigo_header = new ma_haigo_header();
                haigo_header = (from m in context.ma_haigo_header
                                where m.no_seiho == no_seiho
                                && m.kbn_hin == KbnHin_Haigo
                                select m).FirstOrDefault();

                if (haigo_header != null)
                {
                    result.addColumnHeader.haveHaigo = true;
                }
                else
                {
                    result.addColumnHeader.haveHaigo = false;
                }
            }

            return result;
        }

        /// <summary>
        /// Check the waiting queue 
        /// FIFO
        /// </summary>
        /// <param name="processID"></param>
        private void preventConflict(processID processID)
        {
            // Prevent conflict when insert the new seiho
            priorityInsertSeihoProcessing.Add(processID);
            // Check if current process is the first of priority
            while (priorityInsertSeihoProcessing[0].ID != processID.ID)
            {
                processID.waitTime += processID.singleWaitingTime;
                if (processID.waitTime > processID.maxWaiting)
                {
                    throw (new Exception("Request timeout. Many user in processing. Please try again later."));
                }
                Thread.Sleep(processID.singleWaitingTime);
            }
        }
        
        /// <summary>
        /// パラメーターで受け渡された見積情報を新規追加します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]SaveHaigoTorokuKaihatsuBumon parameter)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();
                context.Configuration.LazyLoadingEnabled = false;
                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        decimal userName = 0;
                        Decimal.TryParse(UserInfo.GetUserNameFromIdentity(this.User.Identity), out userName);
                        DateTime date = DateTime.Now;
                        string strName = userName.ToString();
                        Response_HaigoTorokuKaihatsuBumon_Save result = new Response_HaigoTorokuKaihatsuBumon_Save(); ;


                        CommonController common = new CommonController();

                        int? no_seq = 0;

                        if (parameter.M_HaigoTorokuKaihatsu == Mode_Seiho_Shinki_Toroku || parameter.M_HaigoTorokuKaihatsu == Mode_Seiho_Copy)
                        {
                            processID processID = new processID() { ID = p_ID++, waitTime = 0 };
                            string no_seiho = "";
                            decimal cd_haigo = 0;
                            try
                            {
                                // Insert conflict prevent
                                preventConflict(processID);
                                // Continue process after previous user completed their transactions
                                // Get new no_seiho = max(no_seiho in DB) + 1
                                no_seq = context.sp_shohinkaihatsu_getMaxNoSeq_203(parameter.cs_ma_seiho.Created[0].no_seiho).First().no_seq;
                                no_seq = no_seq + 1;

                                if (no_seq > MaxNoseq)
                                {
                                    return Request.CreateErrorResponse(HttpStatusCode.NotFound, "no_seiho");
                                }

                                no_seiho = parameter.cs_ma_seiho.Created[0].no_seiho + no_seq.ToString().PadLeft(4, '0');
                                result.no_seiho = no_seiho;
                                parameter.cs_ma_seiho.Created[0].no_seiho = no_seiho;
                                parameter.cs_ma_seiho.AttachTo(context);
                                context.SaveChanges();

                                decimal? maxHaigo;
                                //try
                                //{
                                //    maxHaigo = context.ma_haigo_header.Max(x => x.cd_haigo);
                                //}
                                //catch (Exception)
                                //{
                                //    maxHaigo = minHaigo;
                                //}
                                maxHaigo = context.ma_haigo_header.OrderByDescending(x => x.cd_haigo).Select(x => x.cd_haigo).FirstOrDefault();
                                if (maxHaigo == null)
                                {
                                    maxHaigo = minHaigo;
                                }

                                cd_haigo = (decimal)maxHaigo + 1;
                                result.cd_haigo = cd_haigo;

                                if (cd_haigo < Properties.Settings.Default.minRangeHaigo || cd_haigo > Properties.Settings.Default.maxRangeHaigo)
                                {
                                    return Request.CreateErrorResponse(HttpStatusCode.NotFound, "haigo_out_of_range");
                                }

                                parameter.cs_ma_haigo_header.Created[0].no_seiho = no_seiho;
                                parameter.cs_ma_haigo_header.Created[0].cd_haigo = cd_haigo;
                                parameter.cs_ma_haigo_header.Created[0].dt_henko = date;
                                parameter.cs_ma_haigo_header.Created[0].dt_toroku = date;
                                parameter.cs_ma_haigo_header.Created[0].cd_toroku = strName;
                                parameter.cs_ma_haigo_header.Created[0].dt_henko = date;
                                parameter.cs_ma_haigo_header.Created[0].cd_koshin = strName;
                                parameter.cs_ma_haigo_header.AttachTo(context);
                                context.SaveChanges();

                            }
                            catch (Exception ex)
                            {
                                // Process in error
                                Logger.App.Error(ex.Message, ex);
                                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex.Message);
                            }
                            finally
                            {
                                // Remove completed process
                                priorityInsertSeihoProcessing.Remove(processID);
                            }

                            // Continue other un-conflictable task
                            bool isInsertFirst = true;

                            for (int i = 0; i < parameter.cs_ma_haigo_meisai.Created.Count(); i++)
                            {
                                int insertFirst = 0;
                                parameter.cs_ma_haigo_meisai.Created[i].cd_haigo = cd_haigo;
                                if (isInsertFirst)
                                {
                                    insertFirst = 1;
                                    isInsertFirst = false;
                                }

                                context.sp_shohinkaihatsu_insert_haigoMeisai_203(cd_haigo, parameter.cs_ma_haigo_meisai.Created[i].no_kotei
                                    , parameter.cs_ma_haigo_meisai.Created[i].no_tonyu
                                    , parameter.cs_ma_haigo_meisai.Created[i].cd_hin
                                    , parameter.cs_ma_haigo_meisai.Created[i].flg_shitei
                                    , parameter.cs_ma_haigo_meisai.Created[i].kbn_hin
                                    , parameter.cs_ma_haigo_meisai.Created[i].kbn_shikakari
                                    , parameter.cs_ma_haigo_meisai.Created[i].nm_hin
                                    , parameter.cs_ma_haigo_meisai.Created[i].cd_mark
                                    , parameter.cs_ma_haigo_meisai.Created[i].qty_haigo
                                    , parameter.cs_ma_haigo_meisai.Created[i].qty_nisugata
                                    , parameter.cs_ma_haigo_meisai.Created[i].gosa
                                    , parameter.cs_ma_haigo_meisai.Created[i].budomari
                                    , parameter.cs_ma_haigo_meisai.Created[i].kbn_bunkatsu
                                    ,insertFirst
                                    );

                            }

                            for (int i = 0; i < parameter.cs_ma_seiho_denso.Created.Count(); i++)
                            {
                                parameter.cs_ma_seiho_denso.Created[i].no_seiho = no_seiho;
                            }
                            parameter.cs_ma_seiho_denso.AttachTo(context);

                            for (int i = 0; i < parameter.cs_ma_seizo_line.Created.Count(); i++)
                            {
                                parameter.cs_ma_seizo_line.Created[i].cd_haigo = cd_haigo;
                                parameter.cs_ma_seizo_line.Created[i].dt_henko = date;
                                parameter.cs_ma_seizo_line.Created[i].cd_koshin = strName;
                            }
                            parameter.cs_ma_seizo_line.AttachTo(context);

                            for (int i = 0; i < parameter.cs_ma_seihin_seiho.Updated.Count(); i++)
                            {
                                parameter.cs_ma_seihin_seiho.Updated[i].cd_haigo = cd_haigo;
                                context.sp_shohinkaihatsu_update_seihinSeiho_203(parameter.cs_ma_seihin_seiho.Updated[i].cd_hin
                                                                            , parameter.cs_ma_seihin_seiho.Updated[i].no_yusen
                                                                            , cd_haigo
                                                                            , parameter.cs_ma_seihin_seiho.Updated[i].cd_koshin_kaisha
                                                                            , parameter.cs_ma_seihin_seiho.Updated[i].cd_koshin);
                            }

                            parameter.cs_tr_event_log.Created[0].no_seiho = no_seiho;
                            parameter.cs_tr_event_log.Created[0].ip_address = common.GetIPClientAddress();
                            parameter.cs_tr_event_log.Created[0].cd_tanto = strName;
                            parameter.cs_tr_event_log.Created[0].cd_koshin = strName;
                            parameter.cs_tr_event_log.Created[0].dt_shori = date;
                            context.sp_shohinkaihatsu_insert_200_event_log(no_seiho, parameter.cs_tr_event_log.Created[0].cd_tanto_kaisha
                                , parameter.cs_tr_event_log.Created[0].cd_tanto
                                , parameter.cs_tr_event_log.Created[0].cd_koshin
                                , parameter.cs_tr_event_log.Created[0].nm_shori
                                , parameter.cs_tr_event_log.Created[0].nm_ope
                                , parameter.cs_tr_event_log.Created[0].dt_shori
                                , parameter.cs_tr_event_log.Created[0].ip_address
                                , parameter.cs_tr_event_log.Created[0].kbn_system);

                            context.SaveChanges();
                        }

                        //Mode shosai
                        else if (parameter.M_HaigoTorokuKaihatsu == Mode_Shosai)
                        {
                            decimal cd_haigo = parameter.cs_ma_haigo_header.Updated[0].cd_haigo;
                            string no_seiho = parameter.cs_ma_haigo_header.Updated[0].no_seiho;
                            result.cd_haigo = cd_haigo;
                            result.no_seiho = no_seiho;
                            //ma_seiho
                            parameter.cs_ma_seiho.AttachTo(context);
                            context.SaveChanges();

                            //ma_haigo_header
                            parameter.cs_ma_haigo_header.Updated[0].dt_henko = date;
                            parameter.cs_ma_haigo_header.Updated[0].cd_koshin = strName;
                            parameter.cs_ma_haigo_header.AttachTo(context);
                            context.SaveChanges();

                            //ma_haigo_meisai
                            bool isInsertFirst = true; 
                            for (int i = 0; i < parameter.cs_ma_haigo_meisai.Created.Count; i++)
                            {
                                int insertFirst = 0;
                                parameter.cs_ma_haigo_meisai.Created[i].cd_haigo = cd_haigo;
                                if (isInsertFirst)
                                {
                                    insertFirst = 1;
                                    isInsertFirst = false;
                                }

                                context.sp_shohinkaihatsu_insert_haigoMeisai_203(cd_haigo, parameter.cs_ma_haigo_meisai.Created[i].no_kotei
                                    , parameter.cs_ma_haigo_meisai.Created[i].no_tonyu
                                    , parameter.cs_ma_haigo_meisai.Created[i].cd_hin
                                    , parameter.cs_ma_haigo_meisai.Created[i].flg_shitei
                                    , parameter.cs_ma_haigo_meisai.Created[i].kbn_hin
                                    , parameter.cs_ma_haigo_meisai.Created[i].kbn_shikakari
                                    , parameter.cs_ma_haigo_meisai.Created[i].nm_hin
                                    , parameter.cs_ma_haigo_meisai.Created[i].cd_mark
                                    , parameter.cs_ma_haigo_meisai.Created[i].qty_haigo
                                    , parameter.cs_ma_haigo_meisai.Created[i].qty_nisugata
                                    , parameter.cs_ma_haigo_meisai.Created[i].gosa
                                    , parameter.cs_ma_haigo_meisai.Created[i].budomari
                                    , parameter.cs_ma_haigo_meisai.Created[i].kbn_bunkatsu
                                    , insertFirst
                                    );
                            }

                            //ma_seiho_denso
                            parameter.cs_ma_seiho_denso.AttachTo(context);

                            //ma_seizo_line
                            for (int i = 0; i < parameter.cs_ma_seizo_line.Created.Count; i++)
                            {
                                parameter.cs_ma_seizo_line.Created[i].dt_henko = date;
                                parameter.cs_ma_seizo_line.Created[i].cd_koshin = strName;
                            }

                            for (int i = 0; i < parameter.cs_ma_seizo_line.Updated.Count; i++)
                            {
                                parameter.cs_ma_seizo_line.Updated[i].dt_henko = date;
                                parameter.cs_ma_seizo_line.Updated[i].cd_koshin = strName;
                            }
                            parameter.cs_ma_seizo_line.AttachTo(context);

                            //ma_seihin_seiho
                            for (int i = 0; i < parameter.cs_ma_seihin_seiho.Updated.Count; i++)
                            {
                                context.sp_shohinkaihatsu_update_seihinSeiho_203(parameter.cs_ma_seihin_seiho.Updated[i].cd_hin
                                                                            , parameter.cs_ma_seihin_seiho.Updated[i].no_yusen
                                                                            , parameter.cs_ma_seihin_seiho.Updated[i].cd_haigo
                                                                            , parameter.cs_ma_seihin_seiho.Updated[i].cd_koshin_kaisha
                                                                            , parameter.cs_ma_seihin_seiho.Updated[i].cd_koshin);
                            }

                            //tr_event_log
                            parameter.cs_tr_event_log.Created[0].ip_address = common.GetIPClientAddress();
                            parameter.cs_tr_event_log.Created[0].cd_tanto = strName;
                            parameter.cs_tr_event_log.Created[0].cd_koshin = strName;
                            parameter.cs_tr_event_log.Created[0].dt_shori = date;
                            context.sp_shohinkaihatsu_insert_200_event_log(parameter.cs_tr_event_log.Created[0].no_seiho
                                , parameter.cs_tr_event_log.Created[0].cd_tanto_kaisha
                                , parameter.cs_tr_event_log.Created[0].cd_tanto
                                , parameter.cs_tr_event_log.Created[0].cd_koshin
                                , parameter.cs_tr_event_log.Created[0].nm_shori
                                , parameter.cs_tr_event_log.Created[0].nm_ope
                                , parameter.cs_tr_event_log.Created[0].dt_shori
                                , parameter.cs_tr_event_log.Created[0].ip_address
                                , parameter.cs_tr_event_log.Created[0].kbn_system);

                            context.SaveChanges();
                        }
                        else if (parameter.M_HaigoTorokuKaihatsu == Mode_Haigo_Shinki)
                        {
                            string no_seiho = parameter.cs_ma_seiho.Updated[0].no_seiho;
                            result.no_seiho = no_seiho;
                            decimal cd_haigo = 0;

                            processID processID = new processID() { ID = p_ID++, waitTime = 0 };
                            try
                            {
                                // Insert conflict prevent
                                preventConflict(processID);
                                //ma_seiho
                                parameter.cs_ma_seiho.AttachTo(context);
                                context.SaveChanges();
                                decimal? maxHaigo;
                                //try
                                //{
                                //    maxHaigo = context.ma_haigo_header.Max(x => x.cd_haigo);
                                //}
                                //catch (Exception)
                                //{
                                //    maxHaigo = minHaigo;
                                //}
                                maxHaigo = context.ma_haigo_header.OrderByDescending(x => x.cd_haigo).Select(x => x.cd_haigo).FirstOrDefault();
                                if (maxHaigo == null)
                                {
                                    maxHaigo = minHaigo;
                                }

                                cd_haigo = (decimal)maxHaigo + 1;
                                result.cd_haigo = cd_haigo;
                                if (cd_haigo < Properties.Settings.Default.minRangeHaigo || cd_haigo > Properties.Settings.Default.maxRangeHaigo)
                                {
                                    return Request.CreateErrorResponse(HttpStatusCode.NotFound, "haigo_out_of_range");
                                }
                                parameter.cs_ma_haigo_header.Created[0].no_seiho = no_seiho;
                                parameter.cs_ma_haigo_header.Created[0].cd_haigo = cd_haigo;
                                parameter.cs_ma_haigo_header.Created[0].dt_henko = date;
                                parameter.cs_ma_haigo_header.Created[0].dt_toroku = date;
                                parameter.cs_ma_haigo_header.Created[0].cd_toroku = strName;
                                parameter.cs_ma_haigo_header.Created[0].dt_henko = date;
                                parameter.cs_ma_haigo_header.Created[0].cd_koshin = strName;
                                parameter.cs_ma_haigo_header.AttachTo(context);
                                context.SaveChanges();
                            }
                            catch (Exception ex)
                            {
                                // Process in error
                                Logger.App.Error(ex.Message, ex);
                                return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex.Message);
                            }
                            finally
                            {
                                // Remove completed process
                                priorityInsertSeihoProcessing.Remove(processID);
                            }

                            // Continue other un-conflictable task
                            //ma_haigo_meisai
                            bool isInsertFirst = true;
                            for (int i = 0; i < parameter.cs_ma_haigo_meisai.Created.Count(); i++)
                            {
                                int insertFirst = 0;
                                parameter.cs_ma_haigo_meisai.Created[i].cd_haigo = cd_haigo;
                                if (isInsertFirst)
                                {
                                    insertFirst = 1;
                                    isInsertFirst = false;
                                }
                                context.sp_shohinkaihatsu_insert_haigoMeisai_203(cd_haigo, parameter.cs_ma_haigo_meisai.Created[i].no_kotei
                                    , parameter.cs_ma_haigo_meisai.Created[i].no_tonyu
                                    , parameter.cs_ma_haigo_meisai.Created[i].cd_hin
                                    , parameter.cs_ma_haigo_meisai.Created[i].flg_shitei
                                    , parameter.cs_ma_haigo_meisai.Created[i].kbn_hin
                                    , parameter.cs_ma_haigo_meisai.Created[i].kbn_shikakari
                                    , parameter.cs_ma_haigo_meisai.Created[i].nm_hin
                                    , parameter.cs_ma_haigo_meisai.Created[i].cd_mark
                                    , parameter.cs_ma_haigo_meisai.Created[i].qty_haigo
                                    , parameter.cs_ma_haigo_meisai.Created[i].qty_nisugata
                                    , parameter.cs_ma_haigo_meisai.Created[i].gosa
                                    , parameter.cs_ma_haigo_meisai.Created[i].budomari
                                    , parameter.cs_ma_haigo_meisai.Created[i].kbn_bunkatsu
                                    , insertFirst
                                    );
                            }

                            //ma_seiho_denso
                            parameter.cs_ma_seiho_denso.AttachTo(context);

                            //ma_seizo_line
                            for (int i = 0; i < parameter.cs_ma_seizo_line.Created.Count; i++)
                            {
                                parameter.cs_ma_seizo_line.Created[i].cd_haigo = cd_haigo;
                                parameter.cs_ma_seizo_line.Created[i].dt_henko = date;
                                parameter.cs_ma_seizo_line.Created[i].cd_koshin = strName;
                            }
                            parameter.cs_ma_seizo_line.AttachTo(context);

                            //ma_seihin_seiho
                            for (int i = 0; i < parameter.cs_ma_seihin_seiho.Updated.Count; i++)
                            {
                                parameter.cs_ma_seihin_seiho.Updated[i].cd_haigo = cd_haigo;
                                context.sp_shohinkaihatsu_update_seihinSeiho_203(parameter.cs_ma_seihin_seiho.Updated[i].cd_hin
                                                                            , parameter.cs_ma_seihin_seiho.Updated[i].no_yusen
                                                                            , cd_haigo
                                                                            , parameter.cs_ma_seihin_seiho.Updated[i].cd_koshin_kaisha
                                                                            , parameter.cs_ma_seihin_seiho.Updated[i].cd_koshin);
                            }

                            //tr_event_log
                            parameter.cs_tr_event_log.Created[0].ip_address = common.GetIPClientAddress();
                            parameter.cs_tr_event_log.Created[0].cd_tanto = strName;
                            parameter.cs_tr_event_log.Created[0].cd_koshin = strName;
                            parameter.cs_tr_event_log.Created[0].dt_shori = date;
                            context.sp_shohinkaihatsu_insert_200_event_log(parameter.cs_tr_event_log.Created[0].no_seiho
                                , parameter.cs_tr_event_log.Created[0].cd_tanto_kaisha
                                , parameter.cs_tr_event_log.Created[0].cd_tanto
                                , parameter.cs_tr_event_log.Created[0].cd_koshin
                                , parameter.cs_tr_event_log.Created[0].nm_shori
                                , parameter.cs_tr_event_log.Created[0].nm_ope
                                , parameter.cs_tr_event_log.Created[0].dt_shori
                                , parameter.cs_tr_event_log.Created[0].ip_address
                                , parameter.cs_tr_event_log.Created[0].kbn_system);
                            context.SaveChanges();
                        }

                        transaction.Commit();
                        return Request.CreateResponse<Response_HaigoTorokuKaihatsuBumon_Save>(HttpStatusCode.OK, result);
                    }
                    catch (Exception ex)
                    {
                        Logger.App.Error(ex.Message, ex);
                        transaction.Rollback();
                        if (ex is DbUpdateConcurrencyException || ex.InnerException is DbUpdateConcurrencyException)
                        {
                            return Request.CreateErrorResponse(HttpStatusCode.Conflict, ex);
                        }
                        else
                        {
                            return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
                        }
                    }
                }
            }

        }


        ///**Method
        // * Get info hinmei
        // */
        public List<sp_shohinkaihatsu_searchInfoHinmei_203_Result> getInfoHinmei(int cd_kaisha_daihyo, int cd_kojyo_daihyo, int cd_kaisha, decimal cd_hin, int? kbn_hin, int? kbn_shikakari)
        {
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;
            List<sp_shohinkaihatsu_searchInfoHinmei_203_Result> result = new List<sp_shohinkaihatsu_searchInfoHinmei_203_Result>();
            result = context.sp_shohinkaihatsu_searchInfoHinmei_203(cd_kaisha_daihyo, cd_kojyo_daihyo, cd_kaisha, cd_hin, kbn_hin, kbn_shikakari).ToList();
            return result;
        }

        public List<ma_haigo_meisai> post_checkExistsHinmei([FromBody] RequestParameter_HaigoTorokuKaihatsuBumon_CheckExistsHinmei parameter)
        {
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;
            List<ma_haigo_meisai> errors = new List<ma_haigo_meisai>();
            for (int i = 0; i < parameter.data.Count; i++)
            {
                if (parameter.data[i].kbn_hin == KbnHin_Sagyo)
                {
                    continue;
                }

                decimal cd_hin = parameter.data[i].cd_hin;
                int kbn_hin = parameter.data[i].kbn_hin;
                int? kbn_shikakari = parameter.data[i].kbn_shikakari;
                vw_hin hin = new vw_hin();
                hin = (from m in context.vw_hin
                       where m.cd_kaisha == parameter.cd_kaisha_daihyo
                        && m.cd_kojyo == parameter.cd_kojyo_daihyo
                        && m.cd_hin == cd_hin
                        && m.kbn_hin_toroku == kbn_hin
                        && m.kbn_shikakari == kbn_shikakari
                        select m).FirstOrDefault();

                if (hin == null)
                {
                    vw_hin_shikakari hin_shikakari = new vw_hin_shikakari();
                    hin_shikakari = (from m in context.vw_hin_shikakari
                                     where m.cd_kaisha == parameter.cd_kaisha
                                     && m.cd_hin == cd_hin
                                     && m.kbn_hin_toroku == kbn_hin
                                     && m.kbn_shikakari == kbn_shikakari
                                     select m).FirstOrDefault();

                    if (hin_shikakari == null)
                    {
                        errors.Add(parameter.data[i]);
                    }
                }
                else
                {
                    kbn_hin = hin.kbn_hin;
                    if (kbn_hin == kbnHin_Genryo && (hin.cd_hin < Genryo_rangeMin || hin.cd_hin > Genryo_rangeMax) && hin.no_kikaku == null)
                    {
                        parameter.data[i].qty_haigo = -1;
                        errors.Add(parameter.data[i]);
                    }
                }
            }
            return errors;
        }

        

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class SaveHaigoTorokuKaihatsuBumon
    {
        public ChangeSet<ma_seiho> cs_ma_seiho { get; set; }
        public ChangeSet<ma_haigo_header> cs_ma_haigo_header { get; set; }
        public ChangeSet<ma_haigo_meisai> cs_ma_haigo_meisai { get; set; }
        public ChangeSet<ma_seiho_denso> cs_ma_seiho_denso { get; set; }
        public ChangeSet<ma_seizo_line> cs_ma_seizo_line { get; set; }
        public ChangeSet<tr_event_log> cs_tr_event_log { get; set; }
        public ChangeSet<ma_seihin_seiho> cs_ma_seihin_seiho { get; set; }
        public int M_HaigoTorokuKaihatsu;

        public SaveHaigoTorokuKaihatsuBumon()
        {
            cs_ma_seiho = new ChangeSet<ma_seiho>();
            cs_ma_haigo_header = new ChangeSet<ma_haigo_header>();
            cs_ma_haigo_meisai = new ChangeSet<ma_haigo_meisai>();
            cs_ma_seiho_denso = new ChangeSet<ma_seiho_denso>();
            cs_ma_seizo_line = new ChangeSet<ma_seizo_line>();
            cs_tr_event_log = new ChangeSet<tr_event_log>();
            cs_ma_seihin_seiho = new ChangeSet<ma_seihin_seiho>();
            M_HaigoTorokuKaihatsu = 0;
        }
    }

    public class RequestParameter_HaigoTorokuKaihatsuBumon_CheckExistsHinmei
    {
        public List<ma_haigo_meisai> data { get; set; }
        public int cd_kaisha_daihyo { get; set; }
        public int cd_kojyo_daihyo { get; set; }
        public int cd_kaisha { get; set; }
    }

    public class Response_HaigoTorokuKaihatsuBumon_Save
    {
        public decimal cd_haigo { get; set; }
        public string no_seiho { get; set; }
    }

    

    public class FilterCSV
    {
        public decimal cd_haigo { get; set; }
        public Int16 cd_kaisha_daihyo { get; set; }
        public Int16 cd_kojyo_daihyo { get; set; }
        public Int16 no_seiho_kaisha { get; set; }
    }

    public class processID
    {
        public static int maxWaiting = 500000;        // 100 sec
        public static int singleWaitingTime = 100;    // 100 ms
        public long ID { get; set; }
        public int waitTime { get; set; }
    }
    #endregion

}