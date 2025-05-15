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
using Tos.Web.DataFP;
using System.Data.SqlClient;
using System.Data;
using System.Threading;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class HaigoTorokuKojyoBumonController : ApiController
    {
        #region "Controllerで公開するAPI"
        private const int M_kirikae_hyoji = 1;
        private const int M_kirikae_foodprocs = 2;
        private const int KbnHin_Haigo = 3;
        private const int KbnHin_Shikakari = 4;
        private const int KbnHin_Sagyo = 9;
        private const int MaxNoseq = 9999;
        private const int Mode_shinki = 1;
        private const int Mode_shosai = 2;
        private const int Mode_copy = 3;
        private const int Mode_newVersion = 4;
        private const int Nmacs_kojyo = 2;
        private const int Nmacs_kojyo_relate = 1;
        private static List<processID_B> priorityInsertHaigoProcessing = new List<processID_B>();
        private static long p_ID = 0;
        

        ///**Method
        // * Get tani data
        // */
        public Object getTaniData(int cd_kaisha, int cd_kojyo)
        {
            FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
            context.Configuration.ProxyCreationEnabled = false;
            var result = (from m in context.ma_tani
                          select new 
                          {
                              cd_tani = m.cd_tani,
                              nm_tani = m.nm_tani,
                              flg_mishiyo = m.flg_mishiyo
                          }).ToList();
            return result;

        }

        ///**Method
        // * Get mark data
        // */
        public List<Tos.Web.DataFP.vw_ma_mark> getMarkData(int cd_kaisha, int cd_kojyo)
        {
            FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
            context.Configuration.ProxyCreationEnabled = false;
            List<Tos.Web.DataFP.vw_ma_mark> result = new List<Tos.Web.DataFP.vw_ma_mark>();
            result = (from m in context.vw_ma_mark
                      select m).ToList();
            return result;
        }

        ///**Method
        // * Get info hinmei
        // */
        public Object getInfoHinmei(int cd_kaisha, int cd_kojyo, string cd_hin, int? kbn_hin, int M_kirikae)
        {
            FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
            context.Configuration.ProxyCreationEnabled = false;
            decimal d_cd_hin;
            if (decimal.TryParse(cd_hin, out d_cd_hin))
            {
                if (M_kirikae == M_kirikae_hyoji)
                {
                    List<Tos.Web.DataFP.SS_vw_hin_hyoji> result = new List<Tos.Web.DataFP.SS_vw_hin_hyoji>();
                    result = (from m in context.SS_vw_hin_hyoji
                              where m.cd_hin == d_cd_hin
                              && (kbn_hin == null || m.kbn_hin == kbn_hin)
                              select m).ToList().OrderBy(x => x.kbn_hin).ToList();
                    return result;
                }
                else
                {
                    List<Tos.Web.DataFP.SS_vw_hin> result = new List<Tos.Web.DataFP.SS_vw_hin>();
                    result = (from m in context.SS_vw_hin
                              where m.cd_hin == d_cd_hin
                              && (kbn_hin == null || m.kbn_hin == kbn_hin)
                              select m).ToList().OrderBy(x => x.kbn_hin).ToList();
                    return result;
                }
            }
            else
            {
                if (M_kirikae == M_kirikae_hyoji)
                {
                    List<Tos.Web.DataFP.SS_vw_hin_hyoji> result = new List<Tos.Web.DataFP.SS_vw_hin_hyoji>();
                    result = (from m in context.SS_vw_hin_hyoji
                              where m.cd_hin_var == cd_hin
                              && (kbn_hin == null || m.kbn_hin == kbn_hin)
                              select m).ToList().OrderBy(x => x.kbn_hin).ToList();
                    return result;
                }
                else
                {
                    List<Tos.Web.DataFP.SS_vw_hin> result = new List<Tos.Web.DataFP.SS_vw_hin>();
                    result = (from m in context.SS_vw_hin
                              where m.cd_hin_var == cd_hin
                              && (kbn_hin == null || m.kbn_hin == kbn_hin)
                              select m).ToList().OrderBy(x => x.kbn_hin).ToList();
                    return result;
                }
            }
        }

        ///**Method
        // * Get data in combobox Han
        // */
        public Object getDataComboboxHan(int cd_kaisha, int cd_kojyo, string cd_haigo, int M_kirikae)
        {
            FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
            context.Configuration.ProxyCreationEnabled = false;
            if (M_kirikae == M_kirikae_hyoji)
            {
                var result = (from m in context.ma_haigo_mei_hyoji
                          where m.cd_haigo == cd_haigo
                          && m.flg_sakujyo == false
                          select new
                          {
                              no_han = m.no_han
                          }).ToList().OrderBy(x => x.no_han).ToList();
                return result;
            }
            else
            {
                var result = (from m in context.ma_haigo_mei
                              where m.cd_haigo == cd_haigo
                              && m.flg_sakujyo == false
                              && m.qty_kihon == m.qty_haigo_h
                              select new
                              {
                                  no_han = m.no_han
                              }).ToList().OrderBy(x => x.no_han).ToList();

                return result;
            }
        }

        ///**Method
        // * Get data mode shosai
        // */
        public Object getData(string cd_haigo, int no_han, int cd_kaisha, int cd_kojyo, int M_kirikae)
        {
            FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
            context.Configuration.ProxyCreationEnabled = false;
            dynamic result = new System.Dynamic.ExpandoObject();
            if (M_kirikae == M_kirikae_hyoji)
            {
                result.ma_haigo_mei_hyoji = new Tos.Web.DataFP.ma_haigo_mei_hyoji();
                //result.ma_tanto_seizo = new Tos.Web.DataFP.ma_tanto();
                result.ma_tanto_seizo = new wk_tanto();
                //result.ma_tanto_hinkan = new Tos.Web.DataFP.ma_tanto();
                result.ma_tanto_hinkan = new wk_tanto();
                //result.ma_tanto_koshin = new Tos.Web.DataFP.ma_tanto();
                result.ma_tanto_koshin = new wk_tanto();
                result.list_ma_haigo_recipe_hyoji = new List<Tos.Web.DataFP.ma_haigo_mei_hyoji>();
                result.list_SS_vw_hin_varchar_hyoji = new List<Tos.Web.DataFP.SS_vw_hin_varchar_hyoji>();
                result.list_ma_seizo_line_hyoji = new List<Tos.Web.DataFP.ma_seizo_line_hyoji>();
                result.list_ma_seihin = new List<Tos.Web.DataFP.ma_seihin>();

                //ma_haigo_mei_hyoji
                var haigo_mei_hyoji = (from m in context.vw_ma_haigo_mei_hyoji
                                       where m.cd_haigo == cd_haigo
                                       && m.no_han == no_han
                                       && m.flg_sakujyo == false
                                       select m).FirstOrDefault();
                if (haigo_mei_hyoji == null)
                {
                    result.ma_haigo_mei_hyoji = null;
                    return result;
                }
                result.ma_haigo_mei_hyoji = haigo_mei_hyoji;

                //ma_tanto_seizo
                if (haigo_mei_hyoji.cd_tanto_seizo == "")
                {
                    haigo_mei_hyoji.cd_tanto_seizo = null;
                }
                if (haigo_mei_hyoji.cd_tanto_seizo != null)
                {
                    Int64 num;
                    string cd_tanto;
                    bool isNum = Int64.TryParse(haigo_mei_hyoji.cd_tanto_seizo.ToString(), out num);

                    if (isNum)
                    {
                        cd_tanto = String.Format("{0:D10}", Int64.Parse(haigo_mei_hyoji.cd_tanto_seizo.ToString()));
                    }
                    else
                    {
                        cd_tanto = haigo_mei_hyoji.cd_tanto_seizo;
                    }


                    //var tanto_seizo = (from m in context.ma_tanto
                    //                   where m.cd_tanto == cd_tanto
                    //                   && m.flg_sakujyo == false
                    //                   && m.flg_mishiyo == false
                    //                   select new {
                    //                       cd_tanto = m.cd_tanto,
                    //                       nm_tanto = m.nm_tanto
                    //                   }).FirstOrDefault();
                    var tanto_seizo = getTantoData(cd_tanto, context);
                    if (tanto_seizo == null || tanto_seizo.nm_tanto == null)
                    {
                        //Tos.Web.DataFP.ma_tanto tanto = new Tos.Web.DataFP.ma_tanto();
                        wk_tanto tanto = new wk_tanto();
                        tanto.nm_tanto = cd_tanto;
                        result.ma_tanto_seizo = tanto;
                    }
                    else
                    {
                        result.ma_tanto_seizo = tanto_seizo;
                    }
                }
                else
                {
                    result.ma_tanto_seizo = null;
                }

                //ma_tanto_hinkan
                if (haigo_mei_hyoji.cd_tanto_hinkan == "")
                {
                    haigo_mei_hyoji.cd_tanto_hinkan = null;
                }
                if (haigo_mei_hyoji.cd_tanto_hinkan != null)
                {

                    Int64 num;
                    string cd_tanto;
                    bool isNum = Int64.TryParse(haigo_mei_hyoji.cd_tanto_hinkan.ToString(), out num);

                    if (isNum)
                    {
                        cd_tanto = String.Format("{0:D10}", Int64.Parse(haigo_mei_hyoji.cd_tanto_hinkan.ToString()));
                    }
                    else
                    {
                        cd_tanto = haigo_mei_hyoji.cd_tanto_hinkan;
                    }

                    //var tanto_hinkan = (from m in context.ma_tanto
                    //                    where m.cd_tanto == cd_tanto
                    //                    && m.flg_sakujyo == false
                    //                    && m.flg_mishiyo == false
                    //                    select new { 
                    //                        cd_tanto = m.cd_tanto,
                    //                        nm_tanto = m.nm_tanto
                    //                    }).FirstOrDefault();
                    var tanto_hinkan = getTantoData(cd_tanto, context);
                    if (tanto_hinkan == null || tanto_hinkan.nm_tanto == null)
                    {
                        //Tos.Web.DataFP.ma_tanto tanto = new Tos.Web.DataFP.ma_tanto();
                        wk_tanto tanto = new wk_tanto();
                        tanto.nm_tanto = cd_tanto;
                        result.ma_tanto_hinkan = tanto;
                    }
                    else
                    {
                        result.ma_tanto_hinkan = tanto_hinkan;
                    }
                }
                else
                {
                    result.ma_tanto_hinkan = null;
                }

                //ma_tanto_koshin
                if (haigo_mei_hyoji.cd_koshin == "")
                {
                    haigo_mei_hyoji.cd_koshin = null;
                }
                if (haigo_mei_hyoji.cd_koshin != null)
                {
                    Int64 num;
                    string cd_tanto;
                    bool isNum = Int64.TryParse(haigo_mei_hyoji.cd_koshin.ToString(), out num);

                    if (isNum)
                    {
                        cd_tanto = String.Format("{0:D10}", Int64.Parse(haigo_mei_hyoji.cd_koshin.ToString()));
                    }
                    else
                    {
                        cd_tanto = haigo_mei_hyoji.cd_koshin;
                    }
                    
                    //var tanto_koshin = (from m in context.ma_tanto
                    //                    where m.cd_tanto == cd_tanto
                    //                    && m.flg_sakujyo == false
                    //                    && m.flg_mishiyo == false
                    //                    select new {
                    //                        cd_tanto = m.cd_tanto,
                    //                        nm_tanto = m.nm_tanto
                    //                    }).FirstOrDefault();
                    var tanto_koshin = getTantoData(cd_tanto, context);
                    if (tanto_koshin == null || tanto_koshin.nm_tanto == null)
                    {
                        //Tos.Web.DataFP.ma_tanto tanto = new Tos.Web.DataFP.ma_tanto();
                        wk_tanto tanto = new wk_tanto();
                        tanto.nm_tanto = cd_tanto;
                        result.ma_tanto_koshin = tanto;
                    }
                    else
                    {
                        result.ma_tanto_koshin = tanto_koshin;
                    }
                }
                else
                {
                    result.ma_tanto_koshin = null;
                }


                //List ma_haigo_recipe
                List<Tos.Web.DataFP.vw_ma_haigo_recipe_hyoji> list_ma_haigo_recipe_hyoji = new List<Tos.Web.DataFP.vw_ma_haigo_recipe_hyoji>();
                list_ma_haigo_recipe_hyoji = (from m in context.vw_ma_haigo_recipe_hyoji
                                              where m.cd_haigo == cd_haigo
                                              && m.no_han == no_han
                                              && m.flg_sakujyo == false
                                              && m.flg_mishiyo == false
                                              select m).ToList().OrderBy(m => m.no_kotei).ThenBy(m => m.no_tonyu).ToList();
                result.list_ma_haigo_recipe_hyoji = list_ma_haigo_recipe_hyoji;

                //List vw_hin_varchar_hyoji
                for (int i = 0; i < list_ma_haigo_recipe_hyoji.Count; i++)
                {
                    string cd_hin = list_ma_haigo_recipe_hyoji[i].cd_hin;
                    int kbn_hin = Int32.Parse(list_ma_haigo_recipe_hyoji[i].kbn_hin);

                    if (kbn_hin == KbnHin_Sagyo)
                    {
                        Tos.Web.DataFP.SS_vw_hin_varchar_hyoji vw_hin_varchar_hyoji = new Tos.Web.DataFP.SS_vw_hin_varchar_hyoji();
                        vw_hin_varchar_hyoji.cd_hin = cd_hin;
                        vw_hin_varchar_hyoji.kbn_hin = kbn_hin;
                        vw_hin_varchar_hyoji.kbn_hin_toroku = kbn_hin;
                        result.list_SS_vw_hin_varchar_hyoji.Add(vw_hin_varchar_hyoji);
                    }
                    else
                    {
                        Tos.Web.DataFP.SS_vw_hin_varchar_hyoji vw_hin_varchar_hyoji = new Tos.Web.DataFP.SS_vw_hin_varchar_hyoji();
                        vw_hin_varchar_hyoji = (from m in context.SS_vw_hin_varchar_hyoji
                                                where m.cd_hin == cd_hin
                                                && m.kbn_hin_toroku == kbn_hin
                                                select m).FirstOrDefault();
                        result.list_SS_vw_hin_varchar_hyoji.Add(vw_hin_varchar_hyoji);
                    }

                }

                //List ma_seizo_line_hyoji
                var list_ma_seizo_line_hyoji = (from m in context.ma_seizo_line_hyoji
                                            where m.cd_haigo == cd_haigo
                                            && m.flg_mishiyo == false
                                            && m.flg_sakujyo == false
                                            select new
                                            {
                                                no_seq = m.no_seq,
                                                cd_haigo = m.cd_haigo,
                                                no_yusen = m.no_yusen,
                                                cd_line = m.cd_line
                                            }).ToList();
                result.list_ma_seizo_line_hyoji = list_ma_seizo_line_hyoji;

                //List ma_seihin
                var list_ma_seihin = (from m in context.ma_seihin
                                      where m.cd_haigo_hyoji == cd_haigo
                                      && m.flg_sakujyo == false
                                      select new {
                                          cd_hin = m.cd_hin,
                                          nm_hin = m.nm_hin,
                                          no_yusen = m.no_yusen,
                                          no_yusen_hyoji = m.no_yusen_hyoji,
                                          cd_haigo = m.cd_haigo,
                                          cd_haigo_hyoji = m.cd_haigo_hyoji,
                                          cd_koshin = m.cd_koshin
                                      }).ToList().OrderBy(x => x.no_yusen_hyoji).ToList();
                result.list_ma_seihin = list_ma_seihin;
            }//end M_kirikae = 1
            else // Start M_kirikae = 2
            {
                result.ma_haigo_mei = new Tos.Web.DataFP.ma_haigo_mei();
                //result.ma_tanto_seizo = new Tos.Web.DataFP.ma_tanto();
                result.ma_tanto_seizo = new wk_tanto();
                //result.ma_tanto_hinkan = new Tos.Web.DataFP.ma_tanto();
                result.ma_tanto_hinkan = new wk_tanto();
                //result.ma_tanto_koshin = new Tos.Web.DataFP.ma_tanto();
                result.ma_tanto_koshin = new wk_tanto();
                result.list_ma_haigo_recipe = new List<Tos.Web.DataFP.ma_haigo_mei>();
                result.list_SS_vw_hin_varchar = new List<Tos.Web.DataFP.SS_vw_hin_varchar>();
                result.list_ma_seizo_line = new List<Tos.Web.DataFP.ma_seizo_line>();
                result.list_ma_seihin = new List<Tos.Web.DataFP.ma_seihin>();

                //ma_haigo_mei
                Tos.Web.DataFP.vw_ma_haigo_mei haigo_mei = new Tos.Web.DataFP.vw_ma_haigo_mei();
                haigo_mei = (from m in context.vw_ma_haigo_mei
                                   where m.cd_haigo == cd_haigo
                                   && m.no_han == no_han
                                   && m.flg_sakujyo == false
                                   && m.qty_kihon == m.qty_haigo_h
                                   select m).FirstOrDefault();
                if (haigo_mei == null)
                {
                    result.ma_haigo_mei = null;
                    return result;
                }
                result.ma_haigo_mei = haigo_mei;

                //ma_tanto_seizo
                if (haigo_mei.cd_tanto_seizo == "")
                {
                    haigo_mei.cd_tanto_seizo = null;
                }
                if (haigo_mei.cd_tanto_seizo != null)
                {
                    Int64 num;
                    string cd_tanto;
                    bool isNum = Int64.TryParse(haigo_mei.cd_tanto_seizo.ToString(), out num);

                    if (isNum)
                    {
                        cd_tanto = String.Format("{0:D10}", Int64.Parse(haigo_mei.cd_tanto_seizo.ToString()));
                    }
                    else
                    {
                        cd_tanto = haigo_mei.cd_tanto_seizo;
                    }
                    
                    //var tanto_seizo = (from m in context.ma_tanto
                    //               where m.cd_tanto == cd_tanto
                    //               && m.flg_sakujyo == false
                    //               && m.flg_mishiyo == false
                    //               select new { 
                    //                   cd_tanto = m.cd_tanto,
                    //                   nm_tanto = m.nm_tanto
                    //               }).FirstOrDefault();
                    var tanto_seizo = getTantoData(cd_tanto, context);
                    if (tanto_seizo == null || tanto_seizo.nm_tanto == null)
                    {
                        //Tos.Web.DataFP.ma_tanto tanto = new Tos.Web.DataFP.ma_tanto();
                        wk_tanto tanto = new wk_tanto(); 
                        tanto.nm_tanto = cd_tanto;
                        result.ma_tanto_seizo = tanto;
                    }
                    else
                    {
                        result.ma_tanto_seizo = tanto_seizo;
                    }
                }
                else
                {
                    result.ma_tanto_seizo = null;
                }

                //ma_tanto_hinkan
                if (haigo_mei.cd_tanto_hinkan == "")
                {
                    haigo_mei.cd_tanto_hinkan = null;
                }
                if (haigo_mei.cd_tanto_hinkan != null)
                {
                    Int64 num;
                    string cd_tanto;
                    bool isNum = Int64.TryParse(haigo_mei.cd_tanto_hinkan.ToString(), out num);

                    if (isNum)
                    {
                        cd_tanto = String.Format("{0:D10}", Int64.Parse(haigo_mei.cd_tanto_hinkan.ToString()));
                    }
                    else
                    {
                        cd_tanto = haigo_mei.cd_tanto_hinkan;
                    }
                   
                    //var tanto_hinkan = (from m in context.ma_tanto
                    //                    where m.cd_tanto == cd_tanto
                    //                    && m.flg_sakujyo == false
                    //                    && m.flg_mishiyo == false
                    //                    select new {
                    //                        cd_tanto = m.cd_tanto,
                    //                        nm_tanto = m.nm_tanto
                    //                    }).FirstOrDefault();
                    var tanto_hinkan = getTantoData(cd_tanto, context);
                    if (tanto_hinkan == null || tanto_hinkan.nm_tanto == null)
                    {
                        //Tos.Web.DataFP.ma_tanto tanto = new Tos.Web.DataFP.ma_tanto();
                        wk_tanto tanto = new wk_tanto();
                        tanto.nm_tanto = cd_tanto;
                        result.ma_tanto_hinkan = tanto;
                    }
                    else
                    {
                        result.ma_tanto_hinkan = tanto_hinkan;
                    }
                }
                else
                {
                    result.ma_tanto_hinkan = null;
                }

                //ma_tanto_koshin
                if (haigo_mei.cd_koshin == "")
                {
                    haigo_mei.cd_koshin = null;
                }
                if (haigo_mei.cd_koshin != null)
                {
                    Int64 num;
                    string cd_tanto;
                    bool isNum = Int64.TryParse(haigo_mei.cd_koshin.ToString(), out num);

                    if (isNum)
                    {
                        cd_tanto = String.Format("{0:D10}", Int64.Parse(haigo_mei.cd_koshin.ToString()));
                    }
                    else
                    {
                        cd_tanto = haigo_mei.cd_koshin;
                    }
                    
                    //var tanto_koshin = (from m in context.ma_tanto
                    //                    where m.cd_tanto == cd_tanto
                    //                    && m.flg_sakujyo == false
                    //                    && m.flg_mishiyo == false
                    //                    select new {
                    //                        cd_tanto = m.cd_tanto,
                    //                        nm_tanto = m.nm_tanto
                    //                    }).FirstOrDefault();
                    var tanto_koshin = getTantoData(cd_tanto, context);
                    if (tanto_koshin == null || tanto_koshin.nm_tanto == null)
                    {
                        //Tos.Web.DataFP.ma_tanto tanto = new Tos.Web.DataFP.ma_tanto();
                        wk_tanto tanto = new wk_tanto();
                        tanto.nm_tanto = cd_tanto;
                        result.ma_tanto_koshin = tanto;
                    }
                    else
                    {
                        result.ma_tanto_koshin = tanto_koshin;
                    }
                }
                else
                {
                    result.ma_tanto_koshin = null;
                }


                //List ma_haigo_recipe
                List<Tos.Web.DataFP.vw_ma_haigo_recipe> list_ma_haigo_recipe = new List<Tos.Web.DataFP.vw_ma_haigo_recipe>();
                list_ma_haigo_recipe = (from m in context.vw_ma_haigo_recipe
                                              where m.cd_haigo == cd_haigo
                                              && m.no_han == no_han
                                              && m.flg_sakujyo == false
                                              && m.flg_mishiyo == false
                                              && m.qty_haigo_h == haigo_mei.qty_kihon
                                              select m).ToList().OrderBy(m => m.no_kotei).ThenBy(m => m.no_tonyu).ToList();
                result.list_ma_haigo_recipe = list_ma_haigo_recipe;

                //List vw_hin_varchar
                for (int i = 0; i < list_ma_haigo_recipe.Count; i++)
                {
                    string cd_hin = list_ma_haigo_recipe[i].cd_hin;
                    int kbn_hin = Int32.Parse(list_ma_haigo_recipe[i].kbn_hin);

                    if (kbn_hin == KbnHin_Sagyo)
                    {
                        Tos.Web.DataFP.SS_vw_hin_varchar vw_hin_varchar = new Tos.Web.DataFP.SS_vw_hin_varchar();
                        vw_hin_varchar.cd_hin = cd_hin;
                        vw_hin_varchar.kbn_hin = kbn_hin;
                        vw_hin_varchar.kbn_hin_toroku = kbn_hin;
                        result.list_SS_vw_hin_varchar.Add(vw_hin_varchar);
                    }
                    else
                    {
                        Tos.Web.DataFP.SS_vw_hin_varchar vw_hin_varchar = new Tos.Web.DataFP.SS_vw_hin_varchar();
                        vw_hin_varchar = (from m in context.SS_vw_hin_varchar
                                                where m.cd_hin == cd_hin
                                                && m.kbn_hin_toroku == kbn_hin
                                                select m).FirstOrDefault();
                        result.list_SS_vw_hin_varchar.Add(vw_hin_varchar);
                    }

                }

                //List ma_seizo_line
                
                var list_ma_seizo_line = (from m in context.ma_seizo_line
                                            where m.cd_haigo == cd_haigo
                                            && m.flg_mishiyo == false
                                            && m.flg_sakujyo == false
                                            select new{
                                                no_seq = m.no_seq,
                                                cd_haigo = m.cd_haigo,
                                                no_yusen = m.no_yusen,
                                                cd_line = m.cd_line
                                            }).ToList();
                result.list_ma_seizo_line = list_ma_seizo_line;

                //List ma_seihin
                var list_ma_seihin = (from m in context.ma_seihin
                                  where m.cd_haigo == cd_haigo
                                  && m.flg_sakujyo == false
                                  select new {
                                      cd_hin = m.cd_hin,
                                      nm_hin = m.nm_hin,
                                      no_yusen = m.no_yusen,
                                      no_yusen_hyoji = m.no_yusen_hyoji,
                                      cd_haigo = m.cd_haigo,
                                      cd_haigo_hyoji = m.cd_haigo_hyoji,
                                      cd_koshin = m.cd_koshin
                                  }).ToList().OrderBy(x => x.no_yusen).ToList();
                result.list_ma_seihin = list_ma_seihin;
            }//end M_kirikae = 2

            // SKS_MOD_2020
            //Check exists haigo
            ShohinKaihatsuEntities context_seiho = new ShohinKaihatsuEntities();
            result.kaisha_kojyo = (from m in context_seiho.vw_kaisha_kojyo
                                    where m.cd_kaisha == cd_kaisha
                                    && m.cd_kojyo == cd_kojyo
                                    select m).FirstOrDefault();
            // SKS_MOD_2020

            return result;
        }

        ///**Method
        // * Delete version
        // */
        public HttpResponseMessage get_deleteVersion(int cd_kaisha, int cd_kojyo, string cd_haigo, int no_han, int M_kirikae)
        {
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
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
                        string cd_koshin = userName.ToString();

                        string query;
                        object[] parameterSQL;
                        string table_query;
                        string no_han_query;

                        //Update header
                        query = "UPDATE {0}";
                        query = query + " SET flg_sakujyo = 1";
                        query = query + " ,flg_mishiyo = 1";
                        query = query + " ,dt_henko = GETDATE()";
                        query = query + " ,cd_koshin = @cd_koshin";
                        query = query + " WHERE cd_haigo = @cd_haigo";
                        query = query + " AND flg_sakujyo = 0";
                        query = query + " AND no_han = {1}";
                        if (M_kirikae == M_kirikae_hyoji)
                        {
                            table_query = "ma_haigo_mei_hyoji";
                        }
                        else
                        {
                            table_query = "ma_haigo_mei";
                        }

                        if (no_han == 1)
                        {
                            no_han_query = "no_han";
                        }
                        else
                        {
                            no_han_query = no_han.ToString();
                        }
                        query = String.Format(query, table_query, no_han_query);

                        parameterSQL = new object[]
                        {
                            new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)cd_koshin ?? DBNull.Value },
                            new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value  }
                        };
                        context.Database.SqlQuery<Object>(query, parameterSQL).FirstOrDefault();

                        //Update detail
                        if (no_han == 1)
                        {
                            query =
                                (
                                @"
                                    UPDATE {0}
                                    SET flg_sakujyo = 1
	                                    ,flg_mishiyo = 1
	                                    ,dt_henko = GETDATE()
	                                    ,cd_koshin = @cd_koshin
                                    WHERE cd_haigo = @cd_haigo
                                    AND flg_sakujyo = 0
                                ");

                            if (M_kirikae == M_kirikae_hyoji)
                            {
                                table_query = "ma_haigo_recipe_hyoji";
                            }
                            else
                            {
                                table_query = "ma_haigo_recipe";
                            }
                            query = String.Format(query, table_query);

                            parameterSQL = new object[]
                            {
                                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)cd_koshin ?? DBNull.Value },
                                new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value  }
                            };
                            context.Database.SqlQuery<Object>(query, parameterSQL).FirstOrDefault();
                        }
                        else
                        {
                            if (M_kirikae == M_kirikae_hyoji)
                            {
                                query =
                                (
                                @"
                                    UPDATE ma_haigo_recipe_hyoji
                                    SET flg_sakujyo = 1
	                                    ,flg_mishiyo = 1
	                                    ,dt_henko = GETDATE()
	                                    ,cd_koshin = @cd_koshin
                                    WHERE cd_haigo = @cd_haigo
                                    AND flg_sakujyo = 0
                                    AND no_han = @no_han
                                ");

                                parameterSQL = new object[]
                                {
                                    new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)cd_koshin ?? DBNull.Value },
                                    new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value  },
                                    new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)no_han ?? DBNull.Value  }
                                };
                                context.Database.SqlQuery<Object>(query, parameterSQL).FirstOrDefault();
                            }
                            else
                            {
                                query =
                                (
                                    @"
                                        UPDATE haigo_recipe 
                                        SET flg_sakujyo = 1
	                                        ,flg_mishiyo = 1
	                                        ,dt_henko = GETDATE()
	                                        ,cd_koshin = @cd_koshin
                                        FROM ma_haigo_recipe haigo_recipe
                                        WHERE cd_haigo = @cd_haigo
                                        AND no_han = @no_han
                                        AND flg_sakujyo = 0
                                        AND qty_haigo_h = 
                                        (
	                                        SELECT DISTINCT qty_haigo_h
	                                        FROM ma_haigo_mei
	                                        WHERE cd_haigo = haigo_recipe.cd_haigo
	                                        AND no_han = haigo_recipe.no_han
	                                        AND qty_kihon = qty_haigo_h
                                        )
                                    "
                                );

                                parameterSQL = new object[]
                                {
                                    new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)cd_koshin ?? DBNull.Value },
                                    new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value  },
                                    new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)no_han ?? DBNull.Value  }
                                };
                                context.Database.SqlQuery<Object>(query, parameterSQL).FirstOrDefault();
                            }
                        }

                        //Update line
                        if (no_han == 1)
                        {
                            query =
                            (
                                @"
                                UPDATE {0}
                                SET	flg_sakujyo = 1
	                                ,flg_mishiyo = 1
	                                ,dt_henko = GETDATE()
	                                ,cd_koshin = @cd_koshin
                                WHERE cd_haigo = @cd_haigo
                                AND flg_sakujyo = 0
                            "
                            );
                            if (M_kirikae == M_kirikae_hyoji)
                            {
                                table_query = "ma_seizo_line_hyoji";
                            }
                            else
                            {
                                table_query = "ma_seizo_line";
                            }

                            query = String.Format(query, table_query);

                            parameterSQL = new object[]
                            {
                                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)cd_koshin ?? DBNull.Value },
                                new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value  }
                            };
                            context.Database.SqlQuery<Object>(query, parameterSQL).FirstOrDefault();
                        }

                        transaction.Commit();
                        return Request.CreateResponse(HttpStatusCode.OK, " ");
                    }
                    catch (Exception ex)
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, ex);
                    }
                }
            }
        }
        

        /// <summary>
        /// パラメーターで受け渡された見積情報を新規追加します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]SaveHaigoTorokuKojyoBumon parameter)
        {
            using (FOODPROCSEntities context = new FOODPROCSEntities(parameter.cd_kaisha,parameter.cd_kojyo))
            {
                ShohinKaihatsuEntities context_seiho = new ShohinKaihatsuEntities();
                context_seiho.Configuration.ProxyCreationEnabled = false;

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
                        string cd_koshin = userName.ToString();
                        int cd_kaisha;
                        int cd_kojyo;
                        int su_code_standard;
                        string cd_haigo;
                        string query;
                        object[] parameterSQL;
                        int isError;
                        

                        //CommonController common = new CommonController();

                        //Mode_shinki, mode new_version
                        if (parameter.M_HaigoToroku == Mode_shinki || parameter.M_HaigoToroku == Mode_copy ||parameter.M_HaigoToroku == Mode_newVersion)
                        {
                            //Hyoji
                            if (parameter.M_kirikae == M_kirikae_hyoji)
                            {
                                string no_seiho = parameter.cs_ma_haigo_mei_hyoji.Created[0].no_seiho;
                                int no_han = parameter.cs_ma_haigo_mei_hyoji.Created[0].no_han;
                                cd_kaisha = parameter.cd_kaisha;
                                cd_kojyo = parameter.cd_kojyo;

                                // Wait until previous process is completed
                                //long ID = DateTime.Now.Ticks;
                                long ID = p_ID++;
                                processID_B P_ID = new processID_B() { ID = ID, waitTime = 0, cd_kaisha = cd_kaisha, cd_kojo = cd_kojyo };
                                priorityInsertHaigoProcessing.Add(P_ID);
                                try
                                {
                                    while (checkIDInQueue(P_ID))
                                    {
                                        P_ID.waitTime += processID_B.singleWaitingTime;
                                        if (P_ID.waitTime > processID_B.maxWaiting)
                                        {
                                            throw (new Exception("Request timeout. Many user in processing. Please try again later."));
                                        }
                                        Thread.Sleep(processID_B.singleWaitingTime);
                                    }

                                    if (no_seiho == "---")
                                    {
                                        parameter.cs_ma_haigo_mei_hyoji.Created[0].no_seiho = "";
                                    }
                                    else
                                    {
                                        //Check not exists no_seiho
                                        ma_seiho_denso seiho_denso = new ma_seiho_denso();
                                        seiho_denso = (from m in context_seiho.ma_seiho_denso
                                                       where m.no_seiho == no_seiho
                                                       && m.cd_kaisha == cd_kaisha
                                                       && m.cd_kojyo == cd_kojyo
                                                       && m.flg_denso_jyotai == true
                                                       select m).FirstOrDefault();

                                        if (seiho_denso == null)
                                        {
                                            return Request.CreateErrorResponse(HttpStatusCode.NotFound, "NotExistsNoSeiho");
                                        }
                                    }

                                    //Check exists haigo
                                    vw_kaisha_kojyo kaisha_kojyo = new vw_kaisha_kojyo();
                                    kaisha_kojyo = (from m in context_seiho.vw_kaisha_kojyo
                                                    where m.cd_kaisha == cd_kaisha
                                                    && m.cd_kojyo == cd_kojyo
                                                    select m).FirstOrDefault();

                                    //check dt_from, dt_to
                                    if (no_han > 1 && (parameter.M_kirikae == M_kirikae_hyoji || kaisha_kojyo.kbn_nmacs_kojyo == Nmacs_kojyo_relate))
                                    {
                                        query =
                                        (
                                            @"
                                                SELECT COUNT(*) 
                                                FROM ma_haigo_mei_hyoji
                                                WHERE cd_haigo = @cd_haigo 
                                                AND no_han > 1 
                                                AND no_han <> @no_han
                                                AND 
                                                (
	                                                (dt_from <= @dt_from AND dt_to >= @dt_from)
	                                                OR (dt_from <= @dt_to AND dt_to >= @dt_to) 
	                                                OR(dt_from >= @dt_from AND dt_to <=@dt_to)
                                                )
                                                AND flg_sakujyo = 0
                                            "
                                        );
                                        parameterSQL = new object[]
                                        {
                                        
                                            new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Created[0].cd_haigo ?? DBNull.Value  },
                                            new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Created[0].no_han ?? DBNull.Value  },
                                            new SqlParameter("@dt_from", SqlDbType.DateTime) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Created[0].dt_from ?? DBNull.Value },
                                            new SqlParameter("@dt_to", SqlDbType.DateTime) {  Value = (object)parameter.cs_ma_haigo_mei_hyoji.Created[0].dt_to ?? DBNull.Value }
                                        };

                                        int count = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
                                        if (count > 0)
                                        {
                                            return Request.CreateErrorResponse(HttpStatusCode.NotFound, "NohanError");
                                        }


                                    }
                                    //end check dt_from, dt_to.

                                    if (parameter.M_HaigoToroku == Mode_shinki || parameter.M_HaigoToroku == Mode_copy)
                                    {
                                        su_code_standard = kaisha_kojyo.su_code_standard;
                                        cd_haigo = parameter.cs_ma_haigo_mei_hyoji.Created[0].cd_haigo;

                                        if (kaisha_kojyo.kbn_nmacs_kojyo == Nmacs_kojyo)
                                        {
                                            List<Tos.Web.DataFP.SS_Hin_Check_sp_hyoji_qpnmacs_Result> listHaigo = new List<Tos.Web.DataFP.SS_Hin_Check_sp_hyoji_qpnmacs_Result>();
                                            listHaigo = context.SS_Hin_Check_sp_hyoji_qpnmacs(cd_haigo, su_code_standard).ToList();
                                            if (listHaigo.Count > 0)
                                            {
                                                return Request.CreateErrorResponse(HttpStatusCode.NotFound, "ExistsHaigo");
                                            }
                                        }
                                        else
                                        {
                                            List<Tos.Web.DataFP.SS_Hin_Check_sp_hyoji_Result> listHaigo = new List<Tos.Web.DataFP.SS_Hin_Check_sp_hyoji_Result>();
                                            listHaigo = context.SS_Hin_Check_sp_hyoji(cd_haigo, su_code_standard).ToList();
                                            if (listHaigo.Count > 0)
                                            {
                                                return Request.CreateErrorResponse(HttpStatusCode.NotFound, "ExistsHaigo");
                                            }
                                        }
                                    }

                                    //Save ma_haigo_mei_hyoji table
                                    if (parameter.cs_ma_haigo_mei_hyoji.Created[0].flg_seizo)
                                    {
                                        parameter.cs_ma_haigo_mei_hyoji.Created[0].dt_seizo = date;
                                    }
                                    else
                                    {
                                        parameter.cs_ma_haigo_mei_hyoji.Created[0].dt_seizo = null;
                                    }
                                    if (parameter.cs_ma_haigo_mei_hyoji.Created[0].flg_hinkan)
                                    {
                                        parameter.cs_ma_haigo_mei_hyoji.Created[0].dt_hinkan = date;
                                    }
                                    else
                                    {
                                        parameter.cs_ma_haigo_mei_hyoji.Created[0].dt_hinkan = null;
                                    }
                                    parameter.cs_ma_haigo_mei_hyoji.Created[0].dt_toroku = date;
                                    parameter.cs_ma_haigo_mei_hyoji.Created[0].dt_henko = date;
                                    // Continue insert
                                    insertHaigoMeiHyojiTable(context, parameter.cs_ma_haigo_mei_hyoji.Created[0]);
                                }
                                catch (Exception ex)
                                {
                                    throw (ex);
                                }
                                finally
                                {
                                    priorityInsertHaigoProcessing.Remove(P_ID);
                                }
                                
                                // Update np_seiho for all version of the haigo
                                if (parameter.updateAllVersion)
                                {
                                    ma_haigo_mei_hyoji new_data = parameter.cs_ma_haigo_mei_hyoji.Created[0];
                                    isError = UpdateAllVersion(context, "ma_haigo_mei_hyoji", new_data.cd_haigo, new_data.no_seiho, new_data.nm_seiho);
                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }
                                }

                                //Save ma_haigo_recipe_hyoji
                                for (int i = 0; i < parameter.cs_ma_haigo_recipe_hyoji.Created.Count; i++)
                                {
                                    parameter.cs_ma_haigo_recipe_hyoji.Created[i].dt_toroku = date;
                                    parameter.cs_ma_haigo_recipe_hyoji.Created[i].dt_henko = date;
                                    insertHaigoRecipeHyojiTable(context, parameter.cs_ma_haigo_recipe_hyoji.Created[i]);
                                }
                                
                                //Start save ma_seizo_line_hyoji
                                for (int i = 0; i < parameter.cs_ma_seizo_line_hyoji.Created.Count; i++)
                                {
                                    parameter.cs_ma_seizo_line_hyoji.Created[i].dt_toroku = date;
                                    parameter.cs_ma_seizo_line_hyoji.Created[i].dt_henko = date;
                                    insertMaSeizoLineHyojiTable(context, parameter.cs_ma_seizo_line_hyoji.Created[i]);
                                    //parameter.cs_ma_seizo_line_hyoji.Created[i].msrepl_tran_version = Guid.NewGuid();
                                }

                                for (int i = 0; i < parameter.cs_ma_seizo_line_hyoji.Updated.Count; i++)
                                {
                                    parameter.cs_ma_seizo_line_hyoji.Updated[i].dt_henko = date;
                                    updateMaSeizoLineHyojiTable(context, parameter.cs_ma_seizo_line_hyoji.Updated[i]);
                                }
                                
                                //End save ma_seizo_line_hyoji

                                //Save ma_seihin
                                for (int i = 0; i < parameter.cs_ma_seihin.Updated.Count; i++)
                                {
                                    query =         "       UPDATE ma_seihin                        " +
                                                    "   SET cd_haigo_hyoji = @cd_haigo              " +
                                                    "       ,no_yusen_hyoji = @no_yusen             " +
                                                    "       ,cd_koshin = @cd_koshin                 " +
                                                    "       ,dt_henko = GETDATE()                   " +
                                                    "   WHERE cd_hin = @cd_hin                      " +
                                                    "   AND flg_sakujyo = 0                         " +

                                                    "   IF @@ERROR <> 0                             " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 1                                " +
                                                    "   END                                         " +
                                                    "   ELSE                                        " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 0                                " +
                                                    "   END                                         ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_hin ?? DBNull.Value },
                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_haigo ?? DBNull.Value  },
                                        new SqlParameter("@no_yusen", SqlDbType.Int) { Value = (object)parameter.cs_ma_seihin.Updated[i].no_yusen ?? DBNull.Value  },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) {  Value = (object)parameter.cs_ma_seihin.Updated[i].cd_koshin ?? DBNull.Value }
                                    };

                                    isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }


                                    //Save ma_konpo
                                    query =         "   UPDATE ma_konpo                             " +
                                                    "   SET cd_koshin = @cd_koshin                  " +
                                                    "       ,dt_henko = GETDATE()                   " +
                                                    "   WHERE cd_hin = @cd_hin                      " +
                                                    "   AND flg_sakujyo = 0                         " +

                                                    "   IF @@ERROR <> 0                             " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 1                                " +
                                                    "   END                                         " +
                                                    "   ELSE                                        " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 0                                " +
                                                    "   END                                         ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_hin ?? DBNull.Value },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_koshin ?? DBNull.Value }
                                    };

                                    isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();

                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }
                                }
                                context.SaveChanges();
                            }//End Hyoji
                            // FoodProcs
                            else
                            {
                                string no_seiho = parameter.cs_ma_haigo_mei.Created[0].no_seiho;
                                int no_han = parameter.cs_ma_haigo_mei.Created[0].no_han;
                                cd_kaisha = parameter.cd_kaisha;
                                cd_kojyo = parameter.cd_kojyo;

                                // Wait until previous process is completed
                                //long ID = DateTime.Now.Ticks;
                                long ID = p_ID++;
                                processID_B P_ID = new processID_B() { ID = ID, waitTime = 0, cd_kaisha = cd_kaisha, cd_kojo = cd_kojyo };
                                priorityInsertHaigoProcessing.Add(P_ID);
                                try
                                {
                                    while (checkIDInQueue(P_ID))
                                    {
                                        P_ID.waitTime += processID_B.singleWaitingTime;
                                        if (P_ID.waitTime > processID_B.maxWaiting)
                                        {
                                            throw (new Exception("Request timeout. Many user in processing. Please try again later."));
                                        }
                                        Thread.Sleep(processID_B.singleWaitingTime);
                                    }
                                    if (no_seiho == "---")
                                    {
                                        parameter.cs_ma_haigo_mei.Created[0].no_seiho = "";
                                    }
                                    else
                                    {
                                        //Check not exists no_seiho
                                        ma_seiho_denso seiho_denso = new ma_seiho_denso();
                                        seiho_denso = (from m in context_seiho.ma_seiho_denso
                                                       where m.no_seiho == no_seiho
                                                       && m.cd_kaisha == cd_kaisha
                                                       && m.cd_kojyo == cd_kojyo
                                                       && m.flg_denso_jyotai == true
                                                       select m).FirstOrDefault();

                                        if (seiho_denso == null)
                                        {
                                            return Request.CreateErrorResponse(HttpStatusCode.NotFound, "NotExistsNoSeiho");
                                        }
                                    }

                                    //Check exists haigo
                                    vw_kaisha_kojyo kaisha_kojyo = new vw_kaisha_kojyo();
                                    kaisha_kojyo = (from m in context_seiho.vw_kaisha_kojyo
                                                    where m.cd_kaisha == cd_kaisha
                                                    && m.cd_kojyo == cd_kojyo
                                                    select m).FirstOrDefault();

                                    //check dt_from, dt_to
                                    if (no_han > 1 && (parameter.M_kirikae == M_kirikae_hyoji || kaisha_kojyo.kbn_nmacs_kojyo == Nmacs_kojyo_relate))
                                    {
                                        query =
                                        (
                                            @"
                                                SELECT COUNT(*) 
                                                FROM ma_haigo_mei
                                                WHERE cd_haigo = @cd_haigo 
                                                AND no_han > 1 
                                                AND no_han <> @no_han
                                                AND 
                                                (
	                                                (dt_from <= @dt_from AND dt_to >= @dt_from)
	                                                OR (dt_from <= @dt_to AND dt_to >= @dt_to) 
	                                                OR(dt_from >= @dt_from AND dt_to <=@dt_to)
                                                )
                                                AND flg_sakujyo = 0
                                            "
                                        );
                                        parameterSQL = new object[]
                                        {
                                        
                                            new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_haigo_mei.Created[0].cd_haigo ?? DBNull.Value  },
                                            new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)parameter.cs_ma_haigo_mei.Created[0].no_han ?? DBNull.Value  },
                                            new SqlParameter("@dt_from", SqlDbType.DateTime) { Value = (object)parameter.cs_ma_haigo_mei.Created[0].dt_from ?? DBNull.Value },
                                            new SqlParameter("@dt_to", SqlDbType.DateTime) {  Value = (object)parameter.cs_ma_haigo_mei.Created[0].dt_to ?? DBNull.Value }
                                        };

                                        int count = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
                                        if (count > 0)
                                        {
                                            return Request.CreateErrorResponse(HttpStatusCode.NotFound, "NohanError");
                                        }
                                    }
                                    //end check dt_from, dt_to.

                                    if (parameter.M_HaigoToroku == Mode_shinki || parameter.M_HaigoToroku == Mode_copy)
                                    {
                                        su_code_standard = kaisha_kojyo.su_code_standard;
                                        cd_haigo = parameter.cs_ma_haigo_mei.Created[0].cd_haigo;

                                        if (kaisha_kojyo.kbn_nmacs_kojyo == Nmacs_kojyo)
                                        {
                                            List<Tos.Web.DataFP.SS_Hin_Check_sp_qpnmacs_Result> listHaigo = new List<Tos.Web.DataFP.SS_Hin_Check_sp_qpnmacs_Result>();
                                            listHaigo = context.SS_Hin_Check_sp_qpnmacs(cd_haigo, su_code_standard).ToList();
                                            if (listHaigo.Count > 0)
                                            {
                                                return Request.CreateErrorResponse(HttpStatusCode.NotFound, "ExistsHaigo");
                                            }
                                        }
                                        else
                                        {
                                            List<Tos.Web.DataFP.SS_Hin_Check_sp_Result> listHaigo = new List<Tos.Web.DataFP.SS_Hin_Check_sp_Result>();
                                            listHaigo = context.SS_Hin_Check_sp(cd_haigo, su_code_standard).ToList();
                                            if (listHaigo.Count > 0)
                                            {
                                                return Request.CreateErrorResponse(HttpStatusCode.NotFound, "ExistsHaigo");
                                            }
                                        }
                                    }

                                    //Save ma_haigo_mei table
                                    if (parameter.cs_ma_haigo_mei.Created[0].flg_seizo)
                                    {
                                        parameter.cs_ma_haigo_mei.Created[0].dt_seizo = date;
                                    }
                                    else
                                    {
                                        parameter.cs_ma_haigo_mei.Created[0].dt_seizo = null;
                                    }
                                    if (parameter.cs_ma_haigo_mei.Created[0].flg_hinkan)
                                    {
                                        parameter.cs_ma_haigo_mei.Created[0].dt_hinkan = date;
                                    }
                                    else
                                    {
                                        parameter.cs_ma_haigo_mei.Created[0].dt_hinkan = null;
                                    }
                                    parameter.cs_ma_haigo_mei.Created[0].dt_toroku = date;
                                    parameter.cs_ma_haigo_mei.Created[0].dt_henko = date;
                                    // Continue insert
                                    insertHaigoMeiTable(context, parameter.cs_ma_haigo_mei.Created[0], parameter.M_kirikae, kaisha_kojyo.kbn_nmacs_kojyo);
                                }
                                catch (Exception ex)
                                {
                                    throw (ex);
                                }
                                finally
                                {
                                    priorityInsertHaigoProcessing.Remove(P_ID);
                                }

                                if (parameter.updateAllVersion)
                                {
                                    ma_haigo_mei new_data = parameter.cs_ma_haigo_mei.Created[0];
                                    isError = UpdateAllVersion(context, "ma_haigo_mei", new_data.cd_haigo, new_data.no_seiho, new_data.nm_seiho);
                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }
                                }

                                //Save ma_haigo_recipe
                                for (int i = 0; i < parameter.cs_ma_haigo_recipe.Created.Count; i++)
                                {
                                    parameter.cs_ma_haigo_recipe.Created[i].dt_toroku = date;
                                    parameter.cs_ma_haigo_recipe.Created[i].dt_henko = date;
                                    insertHaigoRecipeTable(context, parameter.cs_ma_haigo_recipe.Created[i]);
                                }

                                //Start save ma_seizo_line
                                for (int i = 0; i < parameter.cs_ma_seizo_line.Created.Count; i++)
                                {
                                    parameter.cs_ma_seizo_line.Created[i].dt_toroku = date;
                                    parameter.cs_ma_seizo_line.Created[i].dt_henko = date;
                                    //parameter.cs_ma_seizo_line.Created[i].rowguid = Guid.NewGuid();
                                    insertMaSeizoLineTable(context, parameter.cs_ma_seizo_line.Created[i]);
                                }
                                for (int i = 0; i < parameter.cs_ma_seizo_line.Updated.Count; i++)
                                {
                                    parameter.cs_ma_seizo_line.Updated[i].dt_henko = date;
                                    updateMaSeizoLineTable(context, parameter.cs_ma_seizo_line.Updated[i]);
                                }
                                //End save ma_seizo_line

                                //Save ma_seihin
                                for (int i = 0; i < parameter.cs_ma_seihin.Updated.Count; i++)
                                {
                                    query =         "       UPDATE ma_seihin                        " +
                                                    "   SET cd_haigo = @cd_haigo                    " +
                                                    "       ,no_yusen = @no_yusen                   " +
                                                    "       ,cd_koshin = @cd_koshin                 " +
                                                    "       ,dt_henko = GETDATE()                   " +
                                                    "   WHERE cd_hin = @cd_hin                      " +
                                                    "   AND flg_sakujyo = 0                         " +

                                                    "   IF @@ERROR <> 0                             " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 1                                " +
                                                    "   END                                         " +
                                                    "   ELSE                                        " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 0                                " +
                                                    "   END                                         ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_hin ?? DBNull.Value },
                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_haigo ?? DBNull.Value  },
                                        new SqlParameter("@no_yusen", SqlDbType.Int) { Value = (object)parameter.cs_ma_seihin.Updated[i].no_yusen ?? DBNull.Value  },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_koshin ?? DBNull.Value  }
                                    };

                                    isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }

                                    //Save ma_konpo
                                    query =         "   UPDATE ma_konpo                             " +
                                                    "   SET cd_koshin = @cd_koshin                  " +
                                                    "       ,dt_henko = GETDATE()                   " +
                                                    "   WHERE cd_hin = @cd_hin                      " +
                                                    "   AND flg_sakujyo = 0                         " +

                                                    "   IF @@ERROR <> 0                             " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 1                                " +
                                                    "   END                                         " +
                                                    "   ELSE                                        " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 0                                " +
                                                    "   END                                         ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_hin ?? DBNull.Value  },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_koshin ?? DBNull.Value  }
                                    };

                                    isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();

                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }
                                }
                                context.SaveChanges();
                            }
                        }//end mode shinki
                        //start mode shosai
                        else if (parameter.M_HaigoToroku == Mode_shosai)
                        {
                            //start M_kirikae = 1
                            if (parameter.M_kirikae == M_kirikae_hyoji)
                            {
                                string no_seiho = parameter.cs_ma_haigo_mei_hyoji.Updated[0].no_seiho;
                                int no_han = parameter.cs_ma_haigo_mei_hyoji.Updated[0].no_han;
                                DateTime dt_henko = parameter.cs_ma_haigo_mei_hyoji.Updated[0].dt_henko;
                                cd_kaisha = parameter.cd_kaisha;
                                cd_kojyo = parameter.cd_kojyo;
                                if (no_seiho == "---")
                                {
                                    parameter.cs_ma_haigo_mei_hyoji.Updated[0].no_seiho = "";
                                }
                                else
                                {
                                    //Check not exists no_seiho
                                    ma_seiho_denso seiho_denso = new ma_seiho_denso();
                                    seiho_denso = (from m in context_seiho.ma_seiho_denso
                                                   where m.no_seiho == no_seiho
                                                   && m.cd_kaisha == cd_kaisha
                                                   && m.cd_kojyo == cd_kojyo
                                                   && m.flg_denso_jyotai == true
                                                   select m).FirstOrDefault();

                                    if (seiho_denso == null)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "NotExistsNoSeiho");
                                    }
                                }

                                cd_haigo = parameter.cs_ma_haigo_mei_hyoji.Updated[0].cd_haigo;

                                //Check exists haigo
                                vw_kaisha_kojyo kaisha_kojyo = new vw_kaisha_kojyo();
                                kaisha_kojyo = (from m in context_seiho.vw_kaisha_kojyo
                                                where m.cd_kaisha == cd_kaisha
                                                && m.cd_kojyo == cd_kojyo
                                                select m).FirstOrDefault();

                                //check dt_from, dt_to
                                if (no_han > 1 && (parameter.M_kirikae == M_kirikae_hyoji || kaisha_kojyo.kbn_nmacs_kojyo == Nmacs_kojyo_relate))
                                {
                                    query =
                                    (
                                        @"
                                            SELECT COUNT(*) 
                                            FROM ma_haigo_mei_hyoji
                                            WHERE cd_haigo = @cd_haigo 
                                            AND no_han > 1 
                                            AND no_han <> @no_han
                                            AND 
                                            (
	                                            (dt_from <= @dt_from AND dt_to >= @dt_from)
	                                            OR (dt_from <= @dt_to AND dt_to >= @dt_to) 
	                                            OR(dt_from >= @dt_from AND dt_to <=@dt_to)
                                            )
                                            AND flg_sakujyo = 0
                                        "
                                    );
                                    parameterSQL = new object[]
                                    {
                                        
                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].cd_haigo ?? DBNull.Value  },
                                        new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].no_han ?? DBNull.Value  },
                                        new SqlParameter("@dt_from", SqlDbType.DateTime) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].dt_from ?? DBNull.Value },
                                        new SqlParameter("@dt_to", SqlDbType.DateTime) {  Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].dt_to ?? DBNull.Value }
                                    };

                                    int count = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
                                    if (count > 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "NohanError");
                                    }
                                }
                                //end check dt_from, dt_to.

                                //Check exists update.
                                int no_seq = parameter.cs_ma_haigo_mei_hyoji.Updated[0].no_seq;
                                FOODPROCSEntities context_temp = new FOODPROCSEntities(parameter.cd_kaisha, parameter.cd_kojyo);
                                DateTime dt_henko_old = (from m in context_temp.ma_haigo_mei_hyoji
                                                         where m.no_seq == no_seq
                                                         select m.dt_henko).FirstOrDefault();
                                if (dt_henko_old == null)
                                {
                                    return Request.CreateErrorResponse(HttpStatusCode.Conflict, "");
                                }
                                else
                                {
                                    if (DateTime.Compare(dt_henko, dt_henko_old) != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.Conflict, "");
                                    }
                                }

                                //Update ma_haigo_mei_hyoji
                                
                                bool flg_mishiyo = parameter.cs_ma_haigo_mei_hyoji.Updated[0].flg_mishiyo;
                                
                                if (parameter.cs_ma_haigo_mei_hyoji.Updated[0].flg_seizo)
                                {
                                    parameter.cs_ma_haigo_mei_hyoji.Updated[0].dt_seizo = date;
                                }

                                if (parameter.cs_ma_haigo_mei_hyoji.Updated[0].flg_hinkan)
                                {
                                    parameter.cs_ma_haigo_mei_hyoji.Updated[0].dt_hinkan = date;
                                }

                                parameter.cs_ma_haigo_mei_hyoji.Updated[0].dt_henko = date;
                                updateHaigoMeiHyojiTable(context, parameter.cs_ma_haigo_mei_hyoji.Updated[0]);
                                

                                if (no_han == 1 && flg_mishiyo)
                                {
                                    query = "       UPDATE  ma_haigo_mei_hyoji          " +
                                            "       SET dt_henko = GETDATE()            " +
                                            "       ,cd_koshin = @cd_koshin             " +
                                            "       ,flg_mishiyo = 1                    " +
                                            "       WHERE cd_haigo = @cd_haigo          " +
                                            "       AND flg_sakujyo = 0                 ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value   },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].cd_koshin ?? DBNull.Value  }
                                    };

                                    context.Database.ExecuteSqlCommand(query, parameterSQL);
                                }

                                if (no_han == 1)
                                {
                                    query = "       UPDATE  ma_haigo_mei_hyoji              " +
                                            "       SET dt_henko = GETDATE()                " +
                                            "           ,cd_koshin = @cd_koshin             " +
                                            "           ,nm_haigo = @nm_haigo               " +
                                            "           ,nm_haigo_r = @nm_haigo_r           " +
                                            "           ,kbn_hin = @kbn_hin                 " +
                                            "           ,cd_bunrui = @cd_bunrui             " +
                                            "           ,budomari = @budomari               " +
                                            "           ,qty_kihon = @qty_kihon             " +
                                            "           ,ritsu_kihon = @ritsu_kihon         " +
                                            "           ,cd_setsubi = @cd_setsubi           " +
                                            "           ,flg_gasan = @flg_gasan             " +
                                            "           ,qty_max = @qty_max                 " +
                                            "           ,qty_haigo_h = @qty_haigo_h         " +
                                            "           ,kbn_vw = @kbn_vw                   " +
                                            "           ,hijyu = @hijyu                     " +
                                            "           ,flg_shorihin=@flg_shorihin         " +
                                            "       WHERE cd_haigo = @cd_haigo              " +
                                            "       AND flg_sakujyo = 0                     " +

                                            "       IF @@ERROR <> 0                         " +
                                            "       BEGIN                                   " +
                                            "           SELECT 1                            " +
                                            "       END                                     " +
                                            "       ELSE                                    " +
                                            "       BEGIN                                   " +
                                            "        SELECT 0                               " +
                                            "       END                                     ";


                                    parameterSQL = new object[]
                                    {

                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value  },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].cd_koshin ?? DBNull.Value  },
                                        new SqlParameter("@nm_haigo", SqlDbType.VarChar, 60) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].nm_haigo ?? DBNull.Value },
                                        new SqlParameter("@nm_haigo_r", SqlDbType.VarChar, 20) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].nm_haigo_r ?? DBNull.Value },
                                        new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = parameter.cs_ma_haigo_mei_hyoji.Updated[0].kbn_hin},
                                        new SqlParameter("@cd_bunrui", SqlDbType.VarChar, 2) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].cd_bunrui ?? DBNull.Value},
                                        new SqlParameter("@budomari", SqlDbType.Float) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].budomari ?? DBNull.Value},
                                        new SqlParameter("@qty_kihon", SqlDbType.Int) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].qty_kihon ?? DBNull.Value},
                                        new SqlParameter("@ritsu_kihon", SqlDbType.Float) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].ritsu_kihon ?? DBNull.Value},
                                        new SqlParameter("@cd_setsubi", SqlDbType.VarChar,2) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].cd_setsubi ?? DBNull.Value},
                                        new SqlParameter("@flg_gasan", SqlDbType.Bit) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].flg_gasan ?? DBNull.Value},
                                        new SqlParameter("@qty_max", SqlDbType.Float) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].qty_max ?? DBNull.Value},
                                        new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].qty_haigo_h ?? DBNull.Value},
                                        new SqlParameter("@kbn_vw", SqlDbType.VarChar, 2) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].kbn_vw ?? DBNull.Value},
                                        new SqlParameter("@hijyu", SqlDbType.Float) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].hijyu ?? DBNull.Value},
                                        new SqlParameter("@flg_shorihin", SqlDbType.Bit) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].flg_shorihin ?? DBNull.Value}
                                    };
                                    isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }
                                }

                                if (parameter.updateAllVersion) 
                                {
                                    isError = UpdateAllVersion(context, "ma_haigo_mei_hyoji", cd_haigo, parameter.cs_ma_haigo_mei_hyoji.Updated[0].no_seiho, parameter.cs_ma_haigo_mei_hyoji.Updated[0].nm_seiho);
                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }
                                }

                                //end update ma_haigo_mei_hyoji
                                //Save ma_haigo_recipe_hyoji
                                for (int i = 0; i < parameter.cs_ma_haigo_recipe_hyoji.Created.Count; i++)
                                {
                                    parameter.cs_ma_haigo_recipe_hyoji.Created[i].dt_toroku = date;
                                    parameter.cs_ma_haigo_recipe_hyoji.Created[i].dt_henko = date;
                                    insertHaigoRecipeHyojiTable(context, parameter.cs_ma_haigo_recipe_hyoji.Created[i]);
                                }
                                for (int i = 0; i < parameter.cs_ma_haigo_recipe_hyoji.Updated.Count; i++)
                                {
                                    parameter.cs_ma_haigo_recipe_hyoji.Updated[i].dt_henko = date;
                                    updateHaigoRecipeHyojiTable(context, parameter.cs_ma_haigo_recipe_hyoji.Updated[i]);
                                }
                                for (int i = 0; i < parameter.cs_ma_haigo_recipe_hyoji.Deleted.Count; i++)
                                {
                                    ma_haigo_recipe_hyoji item = new ma_haigo_recipe_hyoji();
                                    item = parameter.cs_ma_haigo_recipe_hyoji.Deleted[i];
                                    item.flg_mishiyo = true;
                                    item.flg_sakujyo = true;
                                    item.cd_koshin = cd_koshin;
                                    item.dt_henko = date;
                                    deleteHaigoRecipeHyojiTable(context, item);
                                }
                                if (no_han == 1)
                                {
                                    query = "    UPDATE ma_haigo_recipe_hyoji       " +
                                            "    SET qty_haigo_h = @qty_haigo_h     " +
                                            "    WHERE cd_haigo = @cd_haigo         " +
                                            "    AND flg_sakujyo = 0                ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value  },
                                        new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)parameter.cs_ma_haigo_mei_hyoji.Updated[0].qty_haigo_h ?? DBNull.Value},
                                    };

                                    context.Database.ExecuteSqlCommand(query, parameterSQL);
                                }
                                //end Save ma_haigo_recipe_hyoji

                                //Start save ma_seizo_line_hyoji
                                for (int i = 0; i < parameter.cs_ma_seizo_line_hyoji.Created.Count; i++)
                                {
                                    parameter.cs_ma_seizo_line_hyoji.Created[i].dt_toroku = date;
                                    parameter.cs_ma_seizo_line_hyoji.Created[i].dt_henko = date;
                                    insertMaSeizoLineHyojiTable(context, parameter.cs_ma_seizo_line_hyoji.Created[i]);
                                }
                                for (int i = 0; i < parameter.cs_ma_seizo_line_hyoji.Updated.Count; i++)
                                {
                                    parameter.cs_ma_seizo_line_hyoji.Updated[i].dt_henko = date;
                                    updateMaSeizoLineHyojiTable(context, parameter.cs_ma_seizo_line_hyoji.Updated[i]);
                                }
                                //End save ma_seizo_line_hyoji
                                //Start Save ma_seihin
                                for (int i = 0; i < parameter.cs_ma_seihin.Updated.Count; i++)
                                {
                                    query = "       UPDATE ma_seihin                            " +
                                                    "   SET cd_haigo_hyoji = @cd_haigo              " +
                                                    "       ,no_yusen_hyoji = @no_yusen             " +
                                                    "       ,cd_koshin = @cd_koshin                 " +
                                                    "       ,dt_henko = GETDATE()                   " +
                                                    "   WHERE cd_hin = @cd_hin                      " +
                                                    "   AND flg_sakujyo = 0                         " +

                                                    "   IF @@ERROR <> 0                             " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 1                                " +
                                                    "   END                                         " +
                                                    "   ELSE                                        " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 0                                " +
                                                    "   END                                         ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_hin ?? DBNull.Value  },
                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_haigo ?? DBNull.Value   },
                                        new SqlParameter("@no_yusen", SqlDbType.Int) { Value = (object)parameter.cs_ma_seihin.Updated[i].no_yusen ?? DBNull.Value   },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_koshin ?? DBNull.Value  }
                                    };

                                    isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }

                                    //Start save ma_konpo
                                    query =         "   UPDATE ma_konpo                             " +
                                                    "   SET cd_koshin = @cd_koshin                  " +
                                                    "       ,dt_henko = GETDATE()                   " +
                                                    "   WHERE cd_hin = @cd_hin                      " +
                                                    "   AND flg_sakujyo = 0                         " +

                                                    "   IF @@ERROR <> 0                             " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 1                                " +
                                                    "   END                                         " +
                                                    "   ELSE                                        " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 0                                " +
                                                    "   END                                         ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) {Value = (object)parameter.cs_ma_seihin.Updated[i].cd_hin ?? DBNull.Value  },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)cd_koshin ?? DBNull.Value  }
                                    };

                                    isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();

                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }
                                    //End save ma_konpo
                                }
                                //End save ma_seihin


                            }//end M_kirikae = 1
                            else //start M_kirikae = 2
                            {
                                string no_seiho = parameter.cs_ma_haigo_mei.Updated[0].no_seiho;
                                int no_han = parameter.cs_ma_haigo_mei.Updated[0].no_han;
                                DateTime dt_henko = parameter.cs_ma_haigo_mei.Updated[0].dt_henko;
                                cd_kaisha = parameter.cd_kaisha;
                                cd_kojyo = parameter.cd_kojyo;
                                if (no_seiho == "---")
                                {
                                    parameter.cs_ma_haigo_mei.Updated[0].no_seiho = "";
                                }
                                else
                                {
                                    //Check not exists no_seiho
                                    ma_seiho_denso seiho_denso = new ma_seiho_denso();
                                    seiho_denso = (from m in context_seiho.ma_seiho_denso
                                                   where m.no_seiho == no_seiho
                                                   && m.cd_kaisha == cd_kaisha
                                                   && m.cd_kojyo == cd_kojyo
                                                   && m.flg_denso_jyotai == true
                                                   select m).FirstOrDefault();

                                    if (seiho_denso == null)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "NotExistsNoSeiho");
                                    }
                                }

                                cd_haigo = parameter.cs_ma_haigo_mei.Updated[0].cd_haigo;

                                //Check exists haigo
                                vw_kaisha_kojyo kaisha_kojyo = new vw_kaisha_kojyo();
                                kaisha_kojyo = (from m in context_seiho.vw_kaisha_kojyo
                                                where m.cd_kaisha == cd_kaisha
                                                && m.cd_kojyo == cd_kojyo
                                                select m).FirstOrDefault();

                                //check dt_from, dt_to
                                if (no_han > 1 && (parameter.M_kirikae == M_kirikae_hyoji || kaisha_kojyo.kbn_nmacs_kojyo == Nmacs_kojyo_relate))
                                {
                                    query =
                                    (
                                        @"
                                            SELECT COUNT(*) 
                                            FROM ma_haigo_mei
                                            WHERE cd_haigo = @cd_haigo 
                                            AND no_han > 1 
                                            AND no_han <> @no_han
                                            AND 
                                            (
	                                            (dt_from <= @dt_from AND dt_to >= @dt_from)
	                                            OR (dt_from <= @dt_to AND dt_to >= @dt_to) 
	                                            OR(dt_from >= @dt_from AND dt_to <=@dt_to)
                                            )
                                            AND flg_sakujyo = 0
                                        "
                                    );
                                    parameterSQL = new object[]
                                    {
                                        
                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].cd_haigo ?? DBNull.Value  },
                                        new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].no_han ?? DBNull.Value  },
                                        new SqlParameter("@dt_from", SqlDbType.DateTime) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].dt_from ?? DBNull.Value },
                                        new SqlParameter("@dt_to", SqlDbType.DateTime) {  Value = (object)parameter.cs_ma_haigo_mei.Updated[0].dt_to ?? DBNull.Value }
                                    };

                                    int count = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
                                    if (count > 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "NohanError");
                                    }
                                }
                                //end check dt_from, dt_to.

                                //Check exists update.
                                int no_seq = parameter.cs_ma_haigo_mei.Updated[0].no_seq;
                                FOODPROCSEntities context_temp = new FOODPROCSEntities(parameter.cd_kaisha, parameter.cd_kojyo);
                                DateTime dt_henko_old = (from m in context_temp.ma_haigo_mei
                                                         where m.no_seq == no_seq
                                                         select m.dt_henko).FirstOrDefault();
                                if (dt_henko_old == null)
                                {
                                    return Request.CreateErrorResponse(HttpStatusCode.Conflict, "");
                                }
                                else
                                {
                                    if (DateTime.Compare(dt_henko, dt_henko_old) != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.Conflict, "");
                                    }
                                }

                                //Update ma_haigo_mei
                                bool flg_mishiyo = parameter.cs_ma_haigo_mei.Updated[0].flg_mishiyo;
                              
                                if (parameter.cs_ma_haigo_mei.Updated[0].flg_seizo)
                                {
                                    parameter.cs_ma_haigo_mei.Updated[0].dt_seizo = date;
                                }

                                if (parameter.cs_ma_haigo_mei.Updated[0].flg_hinkan)
                                {
                                    parameter.cs_ma_haigo_mei.Updated[0].dt_hinkan = date;
                                }
                                parameter.cs_ma_haigo_mei.Updated[0].dt_henko = date;
                                updateHaigoMeiTable(context, parameter.cs_ma_haigo_mei.Updated[0]);


                                if (no_han == 1)
                                {
                                    query = "       UPDATE  ma_haigo_mei                    " +
                                            "       SET dt_henko = GETDATE()                " +
                                            "           ,cd_koshin = @cd_koshin             " +
                                            "           ,nm_haigo = @nm_haigo               " +
                                            "           ,nm_haigo_r = @nm_haigo_r           " +
                                            "           ,kbn_hin = @kbn_hin                 " +
                                            "           ,cd_bunrui = @cd_bunrui             " +
                                            "           ,budomari = @budomari               " +
                                            "           ,ritsu_kihon = @ritsu_kihon         " +
                                            "           ,cd_setsubi = @cd_setsubi           " +
                                            "           ,flg_gasan = @flg_gasan             " +
                                            "           ,qty_max = @qty_max                 " +
                                            "           ,kbn_vw = @kbn_vw                   " +
                                            "           ,hijyu = @hijyu                     " +
                                            "           ,flg_shorihin=@flg_shorihin         " +
                                            "       WHERE cd_haigo = @cd_haigo              " +
                                            "       AND flg_sakujyo = 0                     " +

                                            "       IF @@ERROR <> 0                         " +
                                            "       BEGIN                                   " +
                                            "           SELECT 1                            " +
                                            "       END                                     " +
                                            "       ELSE                                    " +
                                            "       BEGIN                                   " +
                                            "        SELECT 0                               " +
                                            "       END                                     ";


                                    parameterSQL = new object[]
                                    {

                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value  },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].cd_koshin ?? DBNull.Value  },
                                        new SqlParameter("@nm_haigo", SqlDbType.VarChar, 60) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].nm_haigo ?? DBNull.Value },
                                        new SqlParameter("@nm_haigo_r", SqlDbType.VarChar, 20) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].nm_haigo_r ?? DBNull.Value },
                                        new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = parameter.cs_ma_haigo_mei.Updated[0].kbn_hin},
                                        new SqlParameter("@cd_bunrui", SqlDbType.VarChar, 2) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].cd_bunrui ?? DBNull.Value},
                                        new SqlParameter("@budomari", SqlDbType.Float) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].budomari ?? DBNull.Value},
                                        new SqlParameter("@ritsu_kihon", SqlDbType.Float) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].ritsu_kihon ?? DBNull.Value},
                                        new SqlParameter("@cd_setsubi", SqlDbType.VarChar,2) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].cd_setsubi ?? DBNull.Value},
                                        new SqlParameter("@flg_gasan", SqlDbType.Bit) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].flg_gasan ?? DBNull.Value},
                                        new SqlParameter("@qty_max", SqlDbType.Float) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].qty_max ?? DBNull.Value},
                                        new SqlParameter("@kbn_vw", SqlDbType.VarChar, 2) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].kbn_vw ?? DBNull.Value},
                                        new SqlParameter("@hijyu", SqlDbType.Float) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].hijyu ?? DBNull.Value},
                                        new SqlParameter("@flg_shorihin", SqlDbType.Bit) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].flg_shorihin ?? DBNull.Value}
                                    };
                                    isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }
                                }


                                if (no_han == 1 && flg_mishiyo)
                                {
                                    query = "       UPDATE  ma_haigo_mei                " +
                                            "       SET dt_henko = GETDATE()            " +
                                            "       ,cd_koshin = @cd_koshin             " +
                                            "       ,flg_mishiyo = 1                    " +
                                            "       WHERE cd_haigo = @cd_haigo          " +
                                            "       AND flg_sakujyo = 0                 ";

                                    parameterSQL = new object[]
                                    {

                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value   },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].cd_koshin ?? DBNull.Value  }
                                    };

                                    context.Database.ExecuteSqlCommand(query, parameterSQL);
                                }

                                if (no_han == 1 && !flg_mishiyo)
                                {
                                    query = "       UPDATE  ma_haigo_mei                " +
                                           "       SET dt_henko = GETDATE()            " +
                                           "       ,cd_koshin = @cd_koshin             " +
                                           "       ,flg_mishiyo = 0                    " +
                                           "       WHERE cd_haigo = @cd_haigo          " +
                                           "       AND flg_sakujyo = 0                 " +
                                           "       AND no_han = 1                      ";

                                    parameterSQL = new object[]
                                    {

                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value   },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].cd_koshin ?? DBNull.Value  }
                                    };

                                    context.Database.ExecuteSqlCommand(query, parameterSQL);
                                }

                                if (parameter.updateAllVersion)
                                {
                                    isError = UpdateAllVersion(context, "ma_haigo_mei", cd_haigo, parameter.cs_ma_haigo_mei.Updated[0].no_seiho, parameter.cs_ma_haigo_mei.Updated[0].nm_seiho);                                    
                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }
                                }
                                //end update ma_haigo_mei
                                //Save ma_haigo_recipe
                                for (int i = 0; i < parameter.cs_ma_haigo_recipe.Created.Count; i++)
                                {
                                    parameter.cs_ma_haigo_recipe.Created[i].dt_toroku = date;
                                    parameter.cs_ma_haigo_recipe.Created[i].dt_henko = date;
                                    insertHaigoRecipeTable(context, parameter.cs_ma_haigo_recipe.Created[i]);
                                }
                                for (int i = 0; i < parameter.cs_ma_haigo_recipe.Updated.Count; i++)
                                {
                                    parameter.cs_ma_haigo_recipe.Updated[i].dt_henko = date;
                                    updateHaigoRecipeTable(context, parameter.cs_ma_haigo_recipe.Updated[i]);
                                }
                                for (int i = 0; i < parameter.cs_ma_haigo_recipe.Deleted.Count; i++)
                                {
                                    ma_haigo_recipe item = new ma_haigo_recipe();
                                    item = parameter.cs_ma_haigo_recipe.Deleted[i];
                                    item.flg_mishiyo = true;
                                    item.flg_sakujyo = true;
                                    item.cd_koshin = cd_koshin;
                                    item.dt_henko = date;
                                    deleteHaigoRecipeTable(context, item);
                                }

                                if (no_han == 1 && parameter.qty_kihon_old != parameter.cs_ma_haigo_mei.Updated[0].qty_kihon)
                                {
                                    query = "    UPDATE ma_haigo_recipe             " +
                                            "    SET qty_haigo_h = @qty_haigo_h     " +
                                            "    WHERE cd_haigo = @cd_haigo         " +
                                            "    AND flg_sakujyo = 0                ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value  },
                                        new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)parameter.cs_ma_haigo_mei.Updated[0].qty_haigo_h ?? DBNull.Value},
                                    };
                                    context.Database.ExecuteSqlCommand(query, parameterSQL);
                                }
                                //end Save ma_haigo_recipe

                                //Start save ma_seizo_line
                                for (int i = 0; i < parameter.cs_ma_seizo_line.Created.Count; i++)
                                {
                                    parameter.cs_ma_seizo_line.Created[i].dt_toroku = date;
                                    parameter.cs_ma_seizo_line.Created[i].dt_henko = date;
                                    insertMaSeizoLineTable(context, parameter.cs_ma_seizo_line.Created[i]);
                                }
                                for (int i = 0; i < parameter.cs_ma_seizo_line.Updated.Count; i++)
                                {
                                    parameter.cs_ma_seizo_line.Updated[i].dt_henko = date;
                                    updateMaSeizoLineTable(context, parameter.cs_ma_seizo_line.Updated[i]);
                                }
                                //End save ma_seizo_line
                                //Start save ma_seihin
                                for (int i = 0; i < parameter.cs_ma_seihin.Updated.Count; i++)
                                {
                                    query = "       UPDATE ma_seihin                            " +
                                                    "   SET cd_haigo = @cd_haigo              " +
                                                    "       ,no_yusen = @no_yusen             " +
                                                    "       ,cd_koshin = @cd_koshin                 " +
                                                    "       ,dt_henko = GETDATE()                   " +
                                                    "   WHERE cd_hin = @cd_hin                      " +
                                                    "   AND flg_sakujyo = 0                         " +

                                                    "   IF @@ERROR <> 0                             " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 1                                " +
                                                    "   END                                         " +
                                                    "   ELSE                                        " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 0                                " +
                                                    "   END                                         ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_hin ?? DBNull.Value  },
                                        new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_haigo ?? "XXXXXXX"   },
                                        new SqlParameter("@no_yusen", SqlDbType.Int) { Value = (object)parameter.cs_ma_seihin.Updated[i].no_yusen ?? DBNull.Value   },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)parameter.cs_ma_seihin.Updated[i].cd_koshin ?? DBNull.Value  }
                                    };

                                    isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }

                                    //Start save ma_konpo
                                    query =         "   UPDATE ma_konpo                             " +
                                                    "   SET cd_koshin = @cd_koshin                  " +
                                                    "       ,dt_henko = GETDATE()                   " +
                                                    "   WHERE cd_hin = @cd_hin                      " +
                                                    "   AND flg_sakujyo = 0                         " +

                                                    "   IF @@ERROR <> 0                             " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 1                                " +
                                                    "   END                                         " +
                                                    "   ELSE                                        " +
                                                    "   BEGIN                                       " +
                                                    "       SELECT 0                                " +
                                                    "   END                                         ";

                                    parameterSQL = new object[]
                                    {
                                        new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) {Value = (object)parameter.cs_ma_seihin.Updated[i].cd_hin ?? DBNull.Value  },
                                        new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)cd_koshin ?? DBNull.Value  }
                                    };

                                    isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();

                                    if (isError != 0)
                                    {
                                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, "SaveError");
                                    }
                                    //End save ma_konpo
                                }
                                //End save ma_seihin
                            }//end M_kirikae = 2
                            context.SaveChanges();
                        }//end mode shosai

                        transaction.Commit();
                        return Request.CreateResponse(HttpStatusCode.OK, " ");
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

        /// <summary>
        /// Insert ma_haigo_mei_hyoji table
        /// </summary>
        public void insertHaigoMeiHyojiTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_haigo_mei_hyoji data){
            string query =
            (
                @"
                    INSERT INTO ma_haigo_mei_hyoji
                    (
		                cd_haigo
                        ,nm_haigo
                        ,nm_haigo_r
                        ,kbn_hin
                        ,cd_bunrui
                        ,budomari
                        ,qty_kihon
                        ,ritsu_kihon
                        ,cd_setsubi
                        ,flg_gasan
                        ,qty_max
                        ,no_han
                        ,qty_haigo_h
                        ,qty_haigo_kei
                        ,biko
                        ,no_seiho
                        ,cd_tanto_seizo
                        ,dt_seizo
                        ,cd_tanto_hinkan
                        ,dt_hinkan
                        ,dt_from
                        ,dt_to
                        ,kbn_vw
                        ,hijyu
                        ,flg_shorihin
                        ,flg_hinkan
                        ,flg_seizo
                        ,flg_sakujyo
                        ,flg_mishiyo
                        ,dt_toroku
                        ,dt_henko
                        ,cd_koshin
                        ,kbn_shiagari
                        ,nm_haigo_rm
                        ,cd_haigo_seiho
                        ,flg_seiho_base
                        ,nm_seiho	
                    )
                    VALUES
                    (
                        @cd_haigo
                        ,@nm_haigo
                        ,@nm_haigo_r
                        ,@kbn_hin
                        ,@cd_bunrui
                        ,@budomari
                        ,@qty_kihon
                        ,@ritsu_kihon
                        ,@cd_setsubi
                        ,@flg_gasan
                        ,@qty_max
                        ,@no_han
                        ,@qty_haigo_h
                        ,@qty_haigo_kei
                        ,@biko
                        ,@no_seiho
                        ,@cd_tanto_seizo
                        ,@dt_seizo
                        ,@cd_tanto_hinkan
                        ,@dt_hinkan
                        ,@dt_from
                        ,@dt_to
                        ,@kbn_vw
                        ,@hijyu
                        ,@flg_shorihin
                        ,@flg_hinkan
                        ,@flg_seizo
                        ,@flg_sakujyo
                        ,@flg_mishiyo
                        ,@dt_toroku
                        ,@dt_henko
                        ,@cd_koshin
                        ,@kbn_shiagari
                        ,@nm_haigo_rm
                        ,@cd_haigo_seiho
                        ,@flg_seiho_base
                        ,@nm_seiho	
                    )
                "

            );
            object[] parameterSQL = new object[]
            {
                new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)data.cd_haigo ?? DBNull.Value  },
                new SqlParameter("@nm_haigo", SqlDbType.VarChar,60) { Value = (object)data.nm_haigo ?? DBNull.Value  },
                new SqlParameter("@nm_haigo_r", SqlDbType.VarChar,20) { Value = (object)data.nm_haigo_r ?? DBNull.Value },
                new SqlParameter("@kbn_hin", SqlDbType.VarChar,1) {  Value = (object)data.kbn_hin ?? DBNull.Value },
                new SqlParameter("@cd_bunrui", SqlDbType.VarChar,2) {  Value = (object)data.cd_bunrui ?? DBNull.Value },
                new SqlParameter("@budomari", SqlDbType.Float) {  Value = (object)data.budomari ?? DBNull.Value },
                new SqlParameter("@qty_kihon", SqlDbType.Int) {  Value = (object)data.qty_kihon ?? DBNull.Value },
                new SqlParameter("@ritsu_kihon", SqlDbType.Float) {  Value = (object)data.ritsu_kihon ?? DBNull.Value },
                new SqlParameter("@cd_setsubi", SqlDbType.VarChar,2) {  Value = (object)data.cd_setsubi ?? DBNull.Value },
                new SqlParameter("@flg_gasan", SqlDbType.Bit) {  Value = (object)data.flg_gasan ?? DBNull.Value },
                new SqlParameter("@qty_max", SqlDbType.Float) {  Value = (object)data.qty_max ?? DBNull.Value },
                new SqlParameter("@no_han", SqlDbType.Int) {  Value = (object)data.no_han ?? DBNull.Value },
                new SqlParameter("@qty_haigo_h", SqlDbType.Int) {  Value = (object)data.qty_haigo_h ?? DBNull.Value },
                new SqlParameter("@qty_haigo_kei", SqlDbType.Float) {  Value = (object)data.qty_haigo_kei ?? DBNull.Value },
                new SqlParameter("@biko", SqlDbType.VarChar,300) {  Value = (object)data.biko ?? DBNull.Value },
                new SqlParameter("@no_seiho", SqlDbType.VarChar,20) {  Value = (object)data.no_seiho ?? DBNull.Value },
                new SqlParameter("@cd_tanto_seizo", SqlDbType.VarChar,10) {  Value = (object)data.cd_tanto_seizo ?? DBNull.Value },
                new SqlParameter("@dt_seizo", SqlDbType.DateTime) {  Value = (object)data.dt_seizo ?? DBNull.Value },
                new SqlParameter("@cd_tanto_hinkan", SqlDbType.VarChar,10) {  Value = (object)data.cd_tanto_hinkan ?? DBNull.Value },
                new SqlParameter("@dt_hinkan", SqlDbType.DateTime) {  Value = (object)data.dt_hinkan ?? DBNull.Value },
                new SqlParameter("@dt_from", SqlDbType.DateTime) {  Value = (object)data.dt_from ?? DBNull.Value },
                new SqlParameter("@dt_to", SqlDbType.DateTime) {  Value = (object)data.dt_to ?? DBNull.Value },
                new SqlParameter("@kbn_vw", SqlDbType.VarChar,2) {  Value = (object)data.kbn_vw ?? DBNull.Value },
                new SqlParameter("@hijyu", SqlDbType.Float) {  Value = (object)data.hijyu ?? DBNull.Value },
                new SqlParameter("@flg_shorihin", SqlDbType.Bit) {  Value = (object)data.flg_shorihin ?? DBNull.Value },
                new SqlParameter("@flg_hinkan", SqlDbType.Bit) {  Value = (object)data.flg_hinkan ?? DBNull.Value },
                new SqlParameter("@flg_seizo", SqlDbType.Bit) {  Value = (object)data.flg_seizo ?? DBNull.Value },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) {  Value = (object)data.flg_sakujyo ?? DBNull.Value },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) {  Value = (object)data.flg_mishiyo ?? DBNull.Value },
                new SqlParameter("@dt_toroku", SqlDbType.DateTime) {  Value = (object)data.dt_toroku ?? DBNull.Value },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) {  Value = (object)data.dt_henko ?? DBNull.Value },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar,10) {  Value = (object)data.cd_koshin ?? DBNull.Value },
                new SqlParameter("@kbn_shiagari", SqlDbType.Bit) {  Value = (object)data.kbn_shiagari ?? DBNull.Value },
                new SqlParameter("@nm_haigo_rm", SqlDbType.VarChar,60) {  Value = (object)data.nm_haigo_rm ?? DBNull.Value },
                new SqlParameter("@cd_haigo_seiho", SqlDbType.VarChar,13) {  Value = (object)data.cd_haigo_seiho ?? DBNull.Value },
                new SqlParameter("@flg_seiho_base", SqlDbType.Bit) {  Value = (object)data.flg_seiho_base ?? DBNull.Value },
                new SqlParameter("@nm_seiho", SqlDbType.VarChar,120) {  Value = (object)data.nm_seiho ?? DBNull.Value }
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Update ma_haigo_mei_hyoji table
        /// </summary>
        public void updateHaigoMeiHyojiTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_haigo_mei_hyoji data)
        {
            string query =
            (
                @"
                    UPDATE ma_haigo_mei_hyoji
                    SET	nm_haigo = @nm_haigo,
	                    nm_haigo_r = @nm_haigo_r,
	                    kbn_hin = @kbn_hin,
	                    cd_bunrui = @cd_bunrui,
	                    budomari = @budomari,
	                    qty_kihon = @qty_kihon,
	                    ritsu_kihon = @ritsu_kihon,
	                    cd_setsubi = @cd_setsubi,
	                    flg_gasan = @flg_gasan,
	                    qty_max = @qty_max,
	                    no_han = @no_han,
	                    qty_haigo_h = @qty_haigo_h,
	                    qty_haigo_kei = @qty_haigo_kei,
	                    biko = @biko,
	                    no_seiho = @no_seiho,
	                    cd_tanto_seizo = @cd_tanto_seizo,
	                    dt_seizo = @dt_seizo,
	                    cd_tanto_hinkan = @cd_tanto_hinkan,
	                    dt_hinkan = @dt_hinkan,
	                    dt_from = @dt_from,
	                    dt_to = @dt_to,
	                    kbn_vw = @kbn_vw,
	                    hijyu = @hijyu,
	                    flg_shorihin = @flg_shorihin,
	                    flg_hinkan = @flg_hinkan,
	                    flg_seizo = @flg_seizo,
	                    flg_sakujyo = @flg_sakujyo,
	                    flg_mishiyo = @flg_mishiyo,
	                    dt_toroku = @dt_toroku,
	                    dt_henko = @dt_henko,
	                    cd_koshin = @cd_koshin,
	                    kbn_shiagari = @kbn_shiagari,
	                    nm_haigo_rm = @nm_haigo_rm,
	                    cd_haigo_seiho = @cd_haigo_seiho,
	                    flg_seiho_base = @flg_seiho_base,
	                    nm_seiho = @nm_seiho 
                    WHERE no_seq = @no_seq
                "
            );

            object[] parameterSQL = new object[]
            {
                new SqlParameter("@no_seq", SqlDbType.Int) { Value = (object)data.no_seq ?? DBNull.Value  },
                new SqlParameter("@nm_haigo", SqlDbType.VarChar,60) { Value = (object)data.nm_haigo ?? DBNull.Value  },
                new SqlParameter("@nm_haigo_r", SqlDbType.VarChar,20) { Value = (object)data.nm_haigo_r ?? DBNull.Value },
                new SqlParameter("@kbn_hin", SqlDbType.VarChar,1) {  Value = (object)data.kbn_hin ?? DBNull.Value },
                new SqlParameter("@cd_bunrui", SqlDbType.VarChar,2) {  Value = (object)data.cd_bunrui ?? DBNull.Value },
                new SqlParameter("@budomari", SqlDbType.Float) {  Value = (object)data.budomari ?? DBNull.Value },
                new SqlParameter("@qty_kihon", SqlDbType.Int) {  Value = (object)data.qty_kihon ?? DBNull.Value },
                new SqlParameter("@ritsu_kihon", SqlDbType.Float) {  Value = (object)data.ritsu_kihon ?? DBNull.Value },
                new SqlParameter("@cd_setsubi", SqlDbType.VarChar,2) {  Value = (object)data.cd_setsubi ?? DBNull.Value },
                new SqlParameter("@flg_gasan", SqlDbType.Bit) {  Value = (object)data.flg_gasan ?? DBNull.Value },
                new SqlParameter("@qty_max", SqlDbType.Float) {  Value = (object)data.qty_max ?? DBNull.Value },
                new SqlParameter("@no_han", SqlDbType.Int) {  Value = (object)data.no_han ?? DBNull.Value },
                new SqlParameter("@qty_haigo_h", SqlDbType.Int) {  Value = (object)data.qty_haigo_h ?? DBNull.Value },
                new SqlParameter("@qty_haigo_kei", SqlDbType.Float) {  Value = (object)data.qty_haigo_kei ?? DBNull.Value },
                new SqlParameter("@biko", SqlDbType.VarChar,300) {  Value = (object)data.biko ?? DBNull.Value },
                new SqlParameter("@no_seiho", SqlDbType.VarChar,20) {  Value = (object)data.no_seiho ?? DBNull.Value },
                new SqlParameter("@cd_tanto_seizo", SqlDbType.VarChar,10) {  Value = (object)data.cd_tanto_seizo ?? DBNull.Value },
                new SqlParameter("@dt_seizo", SqlDbType.DateTime) {  Value = (object)data.dt_seizo ?? DBNull.Value },
                new SqlParameter("@cd_tanto_hinkan", SqlDbType.VarChar,10) {  Value = (object)data.cd_tanto_hinkan ?? DBNull.Value },
                new SqlParameter("@dt_hinkan", SqlDbType.DateTime) {  Value = (object)data.dt_hinkan ?? DBNull.Value },
                new SqlParameter("@dt_from", SqlDbType.DateTime) {  Value = (object)data.dt_from ?? DBNull.Value },
                new SqlParameter("@dt_to", SqlDbType.DateTime) {  Value = (object)data.dt_to ?? DBNull.Value },
                new SqlParameter("@kbn_vw", SqlDbType.VarChar,2) {  Value = (object)data.kbn_vw ?? DBNull.Value },
                new SqlParameter("@hijyu", SqlDbType.Float) {  Value = (object)data.hijyu ?? DBNull.Value },
                new SqlParameter("@flg_shorihin", SqlDbType.Bit) {  Value = (object)data.flg_shorihin ?? DBNull.Value },
                new SqlParameter("@flg_hinkan", SqlDbType.Bit) {  Value = (object)data.flg_hinkan ?? DBNull.Value },
                new SqlParameter("@flg_seizo", SqlDbType.Bit) {  Value = (object)data.flg_seizo ?? DBNull.Value },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) {  Value = (object)data.flg_sakujyo ?? DBNull.Value },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) {  Value = (object)data.flg_mishiyo ?? DBNull.Value },
                new SqlParameter("@dt_toroku", SqlDbType.DateTime) {  Value = (object)data.dt_toroku ?? DBNull.Value },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) {  Value = (object)data.dt_henko ?? DBNull.Value },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar,10) {  Value = (object)data.cd_koshin ?? DBNull.Value },
                new SqlParameter("@kbn_shiagari", SqlDbType.Bit) {  Value = (object)data.kbn_shiagari ?? DBNull.Value },
                new SqlParameter("@nm_haigo_rm", SqlDbType.VarChar,60) {  Value = (object)data.nm_haigo_rm ?? DBNull.Value },
                new SqlParameter("@cd_haigo_seiho", SqlDbType.VarChar,13) {  Value = (object)data.cd_haigo_seiho ?? DBNull.Value },
                new SqlParameter("@flg_seiho_base", SqlDbType.Bit) {  Value = (object)data.flg_seiho_base ?? DBNull.Value },
                new SqlParameter("@nm_seiho", SqlDbType.VarChar,120) {  Value = (object)data.nm_seiho ?? DBNull.Value }
                
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Insert ma_haigo_mei table
        /// </summary>
        public void insertHaigoMeiTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_haigo_mei data, int M_kirikae, byte kbn_nmacs_kojyo)
        {
            bool isInsertDefCol = (data.no_han > 1 && M_kirikae == M_kirikae_foodprocs && kbn_nmacs_kojyo == Nmacs_kojyo) ? true : false;
            // Get data remaining in no_han = 1
            if (isInsertDefCol)
            {
                ma_haigo_mei_def_col defCol = (from x in context.ma_haigo_mei
                                               where x.cd_haigo == data.cd_haigo && x.no_han == 1
                                                     && x.flg_sakujyo == false
                                                     && x.qty_kihon == x.qty_haigo_h
                                               select new ma_haigo_mei_def_col
                                               {
                                                   flg_tenkai = x.flg_tenkai,
                                                   qty_haigo_auto = x.qty_haigo_auto,
                                                   su_kigen_min = x.su_kigen_min,
                                                   su_kigen_max = x.su_kigen_max,
                                                   flg_mishiyo_seizo = x.flg_mishiyo_seizo
                                               }).FirstOrDefault();
                if (defCol != null) 
                {
                    DataCopier.ReFill(defCol, data);
                }
            }
            // Continue insert ma_haigo_mei
            string query =
                @"
                    INSERT INTO ma_haigo_mei
                    (
		                cd_haigo
                        ,nm_haigo
                        ,nm_haigo_r
                        ,kbn_hin
                        ,cd_bunrui
                        ,budomari
                        ,qty_kihon
                        ,ritsu_kihon
                        ,cd_setsubi
                        ,flg_gasan
                        ,qty_max
                        ,no_han
                        ,qty_haigo_h
                        ,qty_haigo_kei
                        ,biko
                        ,no_seiho
                        ,cd_tanto_seizo
                        ,dt_seizo
                        ,cd_tanto_hinkan
                        ,dt_hinkan
                        ,dt_from
                        ,dt_to
                        ,kbn_vw
                        ,hijyu
                        ,flg_shorihin
                        ,flg_hinkan
                        ,flg_seizo
                        ,flg_sakujyo
                        ,flg_mishiyo
                        ,dt_toroku
                        ,dt_henko
                        ,cd_koshin
                        ,kbn_shiagari
                        ,nm_haigo_rm
                        ,cd_haigo_seiho
                        ,flg_seiho_base
                        ,nm_seiho
                        ,kbn_koshin
                        ,kin_keihi";
                if (isInsertDefCol) 
                {
                    query += @"
                        ,flg_tenkai
                        ,qty_haigo_auto
                        ,su_kigen_min
                        ,su_kigen_max
                        ,flg_mishiyo_seizo
                    ";
                }
                query += @"
                    )
                    VALUES
                    (
                        @cd_haigo
                        ,@nm_haigo
                        ,@nm_haigo_r
                        ,@kbn_hin
                        ,@cd_bunrui
                        ,@budomari
                        ,@qty_kihon
                        ,@ritsu_kihon
                        ,@cd_setsubi
                        ,@flg_gasan
                        ,@qty_max
                        ,@no_han
                        ,@qty_haigo_h
                        ,@qty_haigo_kei
                        ,@biko
                        ,@no_seiho
                        ,@cd_tanto_seizo
                        ,@dt_seizo
                        ,@cd_tanto_hinkan
                        ,@dt_hinkan
                        ,@dt_from
                        ,@dt_to
                        ,@kbn_vw
                        ,@hijyu
                        ,@flg_shorihin
                        ,@flg_hinkan
                        ,@flg_seizo
                        ,@flg_sakujyo
                        ,@flg_mishiyo
                        ,@dt_toroku
                        ,@dt_henko
                        ,@cd_koshin
                        ,@kbn_shiagari
                        ,@nm_haigo_rm
                        ,@cd_haigo_seiho
                        ,@flg_seiho_base
                        ,@nm_seiho
                        ,@kbn_koshin
                        ,@kin_keihi";
                if (isInsertDefCol) 
                {
                    query += @"
                        ,@flg_tenkai
                        ,@qty_haigo_auto
                        ,@su_kigen_min
                        ,@su_kigen_max
                        ,@flg_mishiyo_seizo
                    ";
                }
                query += @"   
                    )
                ";


            object[] parameterSQL = new object[]
            {
                new SqlParameter("@no_seq", SqlDbType.Int) { Value = (object)data.no_seq ?? DBNull.Value  },
                new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)data.cd_haigo ?? DBNull.Value  },
                new SqlParameter("@nm_haigo", SqlDbType.VarChar,60) { Value = (object)data.nm_haigo ?? DBNull.Value  },
                new SqlParameter("@nm_haigo_r", SqlDbType.VarChar,20) { Value = (object)data.nm_haigo_r ?? DBNull.Value },
                new SqlParameter("@kbn_hin", SqlDbType.VarChar,1) {  Value = (object)data.kbn_hin ?? DBNull.Value },
                new SqlParameter("@cd_bunrui", SqlDbType.VarChar,2) {  Value = (object)data.cd_bunrui ?? DBNull.Value },
                new SqlParameter("@budomari", SqlDbType.Float) {  Value = (object)data.budomari ?? DBNull.Value },
                new SqlParameter("@qty_kihon", SqlDbType.Int) {  Value = (object)data.qty_kihon ?? DBNull.Value },
                new SqlParameter("@ritsu_kihon", SqlDbType.Float) {  Value = (object)data.ritsu_kihon ?? DBNull.Value },
                new SqlParameter("@cd_setsubi", SqlDbType.VarChar,2) {  Value = (object)data.cd_setsubi ?? DBNull.Value },
                new SqlParameter("@flg_gasan", SqlDbType.Bit) {  Value = (object)data.flg_gasan ?? DBNull.Value },
                new SqlParameter("@qty_max", SqlDbType.Float) {  Value = (object)data.qty_max ?? DBNull.Value },
                new SqlParameter("@no_han", SqlDbType.Int) {  Value = (object)data.no_han ?? DBNull.Value },
                new SqlParameter("@qty_haigo_h", SqlDbType.Int) {  Value = (object)data.qty_haigo_h ?? DBNull.Value },
                new SqlParameter("@qty_haigo_kei", SqlDbType.Float) {  Value = (object)data.qty_haigo_kei ?? DBNull.Value },
                new SqlParameter("@biko", SqlDbType.VarChar,300) {  Value = (object)data.biko ?? DBNull.Value },
                new SqlParameter("@no_seiho", SqlDbType.VarChar,20) {  Value = (object)data.no_seiho ?? DBNull.Value },
                new SqlParameter("@cd_tanto_seizo", SqlDbType.VarChar,10) {  Value = (object)data.cd_tanto_seizo ?? DBNull.Value },
                new SqlParameter("@dt_seizo", SqlDbType.DateTime) {  Value = (object)data.dt_seizo ?? DBNull.Value },
                new SqlParameter("@cd_tanto_hinkan", SqlDbType.VarChar,10) {  Value = (object)data.cd_tanto_hinkan ?? DBNull.Value },
                new SqlParameter("@dt_hinkan", SqlDbType.DateTime) {  Value = (object)data.dt_hinkan ?? DBNull.Value },
                new SqlParameter("@dt_from", SqlDbType.DateTime) {  Value = (object)data.dt_from ?? DBNull.Value },
                new SqlParameter("@dt_to", SqlDbType.DateTime) {  Value = (object)data.dt_to ?? DBNull.Value },
                new SqlParameter("@kbn_vw", SqlDbType.VarChar,2) {  Value = (object)data.kbn_vw ?? DBNull.Value },
                new SqlParameter("@hijyu", SqlDbType.Float) {  Value = (object)data.hijyu ?? DBNull.Value },
                new SqlParameter("@flg_shorihin", SqlDbType.Bit) {  Value = (object)data.flg_shorihin ?? DBNull.Value },
                new SqlParameter("@flg_hinkan", SqlDbType.Bit) {  Value = (object)data.flg_hinkan ?? DBNull.Value },
                new SqlParameter("@flg_seizo", SqlDbType.Bit) {  Value = (object)data.flg_seizo ?? DBNull.Value },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) {  Value = (object)data.flg_sakujyo ?? DBNull.Value },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) {  Value = (object)data.flg_mishiyo ?? DBNull.Value },
                new SqlParameter("@dt_toroku", SqlDbType.DateTime) {  Value = (object)data.dt_toroku ?? DBNull.Value },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) {  Value = (object)data.dt_henko ?? DBNull.Value },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar,10) {  Value = (object)data.cd_koshin ?? DBNull.Value },
                new SqlParameter("@kbn_shiagari", SqlDbType.Bit) {  Value = (object)data.kbn_shiagari ?? DBNull.Value },
                new SqlParameter("@nm_haigo_rm", SqlDbType.VarChar,60) {  Value = (object)data.nm_haigo_rm ?? DBNull.Value },
                new SqlParameter("@cd_haigo_seiho", SqlDbType.VarChar,13) {  Value = (object)data.cd_haigo_seiho ?? DBNull.Value },
                new SqlParameter("@flg_seiho_base", SqlDbType.Bit) {  Value = (object)data.flg_seiho_base ?? DBNull.Value },
                new SqlParameter("@nm_seiho", SqlDbType.VarChar,120) {  Value = (object)data.nm_seiho ?? DBNull.Value },
                new SqlParameter("@kbn_koshin", SqlDbType.TinyInt) {  Value = (object)data.kbn_koshin ?? DBNull.Value },
                new SqlParameter("@kin_keihi", SqlDbType.Float) {  Value = (object)data.kin_keihi ?? DBNull.Value },
                // Default value
                new SqlParameter("@flg_tenkai", SqlDbType.Bit) {  Value = (object)data.flg_tenkai ?? DBNull.Value },
                new SqlParameter("@qty_haigo_auto", SqlDbType.Float) {  Value = (object)data.qty_haigo_auto ?? DBNull.Value },
                new SqlParameter("@su_kigen_min", SqlDbType.Int) {  Value = (object)data.su_kigen_min ?? DBNull.Value },
                new SqlParameter("@su_kigen_max", SqlDbType.Int) {  Value = (object)data.su_kigen_max ?? DBNull.Value },
                new SqlParameter("@flg_mishiyo_seizo", SqlDbType.Bit) {  Value = (object)data.flg_mishiyo_seizo ?? DBNull.Value }
            };

            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Update ma_haigo_mei table
        /// </summary>
        public void updateHaigoMeiTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_haigo_mei data)
        {
            // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ST
            // Edit: Add flg_seiho_base = 0, kbn_koshin = 1; Remove param @flg_seiho_base
            string query =
           (
               @"
                    UPDATE ma_haigo_mei
                    SET	nm_haigo = @nm_haigo,
	                    nm_haigo_r = @nm_haigo_r,
	                    kbn_hin = @kbn_hin,
	                    cd_bunrui = @cd_bunrui,
	                    budomari = @budomari,
	                    qty_kihon = @qty_kihon,
	                    ritsu_kihon = @ritsu_kihon,
	                    cd_setsubi = @cd_setsubi,
	                    flg_gasan = @flg_gasan,
	                    qty_max = @qty_max,
	                    no_han = @no_han,
	                    qty_haigo_h = @qty_haigo_h,
	                    qty_haigo_kei = @qty_haigo_kei,
	                    biko = @biko,
	                    no_seiho = @no_seiho,
	                    cd_tanto_seizo = @cd_tanto_seizo,
	                    dt_seizo = @dt_seizo,
	                    cd_tanto_hinkan = @cd_tanto_hinkan,
	                    dt_hinkan = @dt_hinkan,
	                    dt_from = @dt_from,
	                    dt_to = @dt_to,
	                    kbn_vw = @kbn_vw,
	                    hijyu = @hijyu,
	                    flg_shorihin = @flg_shorihin,
	                    flg_hinkan = @flg_hinkan,
	                    flg_seizo = @flg_seizo,
	                    flg_sakujyo = @flg_sakujyo,
	                    flg_mishiyo = @flg_mishiyo,
	                    dt_toroku = @dt_toroku,
	                    dt_henko = @dt_henko,
	                    cd_koshin = @cd_koshin,
	                    kbn_shiagari = @kbn_shiagari,
	                    nm_haigo_rm = @nm_haigo_rm,
	                    cd_haigo_seiho = @cd_haigo_seiho,
	                    flg_seiho_base = 0,
                        kbn_koshin = 1,
	                    nm_seiho = @nm_seiho
                    WHERE no_seq = @no_seq
                "
            );

            object[] parameterSQL = new object[]
            {
                new SqlParameter("@no_seq", SqlDbType.Int) { Value = (object)data.no_seq ?? DBNull.Value  },
                new SqlParameter("@nm_haigo", SqlDbType.VarChar,60) { Value = (object)data.nm_haigo ?? DBNull.Value  },
                new SqlParameter("@nm_haigo_r", SqlDbType.VarChar,20) { Value = (object)data.nm_haigo_r ?? DBNull.Value },
                new SqlParameter("@kbn_hin", SqlDbType.VarChar,1) {  Value = (object)data.kbn_hin ?? DBNull.Value },
                new SqlParameter("@cd_bunrui", SqlDbType.VarChar,2) {  Value = (object)data.cd_bunrui ?? DBNull.Value },
                new SqlParameter("@budomari", SqlDbType.Float) {  Value = (object)data.budomari ?? DBNull.Value },
                new SqlParameter("@qty_kihon", SqlDbType.Int) {  Value = (object)data.qty_kihon ?? DBNull.Value },
                new SqlParameter("@ritsu_kihon", SqlDbType.Float) {  Value = (object)data.ritsu_kihon ?? DBNull.Value },
                new SqlParameter("@cd_setsubi", SqlDbType.VarChar,2) {  Value = (object)data.cd_setsubi ?? DBNull.Value },
                new SqlParameter("@flg_gasan", SqlDbType.Bit) {  Value = (object)data.flg_gasan ?? DBNull.Value },
                new SqlParameter("@qty_max", SqlDbType.Float) {  Value = (object)data.qty_max ?? DBNull.Value },
                new SqlParameter("@no_han", SqlDbType.Int) {  Value = (object)data.no_han ?? DBNull.Value },
                new SqlParameter("@qty_haigo_h", SqlDbType.Int) {  Value = (object)data.qty_haigo_h ?? DBNull.Value },
                new SqlParameter("@qty_haigo_kei", SqlDbType.Float) {  Value = (object)data.qty_haigo_kei ?? DBNull.Value },
                new SqlParameter("@biko", SqlDbType.VarChar,300) {  Value = (object)data.biko ?? DBNull.Value },
                new SqlParameter("@no_seiho", SqlDbType.VarChar,20) {  Value = (object)data.no_seiho ?? DBNull.Value },
                new SqlParameter("@cd_tanto_seizo", SqlDbType.VarChar,10) {  Value = (object)data.cd_tanto_seizo ?? DBNull.Value },
                new SqlParameter("@dt_seizo", SqlDbType.DateTime) {  Value = (object)data.dt_seizo ?? DBNull.Value },
                new SqlParameter("@cd_tanto_hinkan", SqlDbType.VarChar,10) {  Value = (object)data.cd_tanto_hinkan ?? DBNull.Value },
                new SqlParameter("@dt_hinkan", SqlDbType.DateTime) {  Value = (object)data.dt_hinkan ?? DBNull.Value },
                new SqlParameter("@dt_from", SqlDbType.DateTime) {  Value = (object)data.dt_from ?? DBNull.Value },
                new SqlParameter("@dt_to", SqlDbType.DateTime) {  Value = (object)data.dt_to ?? DBNull.Value },
                new SqlParameter("@kbn_vw", SqlDbType.VarChar,2) {  Value = (object)data.kbn_vw ?? DBNull.Value },
                new SqlParameter("@hijyu", SqlDbType.Float) {  Value = (object)data.hijyu ?? DBNull.Value },
                new SqlParameter("@flg_shorihin", SqlDbType.Bit) {  Value = (object)data.flg_shorihin ?? DBNull.Value },
                new SqlParameter("@flg_hinkan", SqlDbType.Bit) {  Value = (object)data.flg_hinkan ?? DBNull.Value },
                new SqlParameter("@flg_seizo", SqlDbType.Bit) {  Value = (object)data.flg_seizo ?? DBNull.Value },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) {  Value = (object)data.flg_sakujyo ?? DBNull.Value },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) {  Value = (object)data.flg_mishiyo ?? DBNull.Value },
                new SqlParameter("@dt_toroku", SqlDbType.DateTime) {  Value = (object)data.dt_toroku ?? DBNull.Value },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) {  Value = (object)data.dt_henko ?? DBNull.Value },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar,10) {  Value = (object)data.cd_koshin ?? DBNull.Value },
                new SqlParameter("@kbn_shiagari", SqlDbType.Bit) {  Value = (object)data.kbn_shiagari ?? DBNull.Value },
                new SqlParameter("@nm_haigo_rm", SqlDbType.VarChar,60) {  Value = (object)data.nm_haigo_rm ?? DBNull.Value },
                new SqlParameter("@cd_haigo_seiho", SqlDbType.VarChar,13) {  Value = (object)data.cd_haigo_seiho ?? DBNull.Value },
                new SqlParameter("@nm_seiho", SqlDbType.VarChar,120) {  Value = (object)data.nm_seiho ?? DBNull.Value }
                
            };
            // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ED
            context.Database.ExecuteSqlCommand(query, parameterSQL);

            
        }

        /// <summary>
        /// Insert ma_haigo_recipe_hyoji table
        /// </summary>
        public void insertHaigoRecipeHyojiTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_haigo_recipe_hyoji data)
        {
            string query =
            (
                @"
                    INSERT INTO ma_haigo_recipe_hyoji
                    (
		                cd_haigo
                        ,no_han
                        ,qty_haigo_h
                        ,no_kotei
                        ,no_tonyu
                        ,cd_hin
                        ,kbn_hin
                        ,nm_hin
                        ,cd_mark
                        ,qty
                        ,qty_haigo
                        ,qty_nisugata
                        ,su_nisugata
                        ,qty_kowake
                        ,su_kowake
                        ,cd_futai
                        ,qty_futai
                        ,hijyu
                        ,budomari
                        ,flg_sakujyo
                        ,flg_mishiyo
                        ,dt_toroku
                        ,dt_henko
                        ,cd_koshin
                        ,kbn_jyotai
                        ,kbn_shitei
                        ,kbn_bunkatsu	
                    )
                    VALUES
                    (
                        @cd_haigo
                        ,@no_han
                        ,@qty_haigo_h
                        ,@no_kotei
                        ,@no_tonyu
                        ,@cd_hin
                        ,@kbn_hin
                        ,@nm_hin
                        ,@cd_mark
                        ,@qty
                        ,@qty_haigo
                        ,@qty_nisugata
                        ,@su_nisugata
                        ,@qty_kowake
                        ,@su_kowake
                        ,@cd_futai
                        ,@qty_futai
                        ,@hijyu
                        ,@budomari
                        ,@flg_sakujyo
                        ,@flg_mishiyo
                        ,@dt_toroku
                        ,@dt_henko
                        ,@cd_koshin
                        ,@kbn_jyotai
                        ,@kbn_shitei
                        ,@kbn_bunkatsu
                    )
                "
            );
            // Fix bug #15244 kbn_jyotai
            if (data.no_seq == 0 && data.kbn_jyotai == null)
            {
                data.kbn_jyotai = "1";
            }
            object[] parameterSQL = new object[]
            {
                new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)data.cd_haigo ?? DBNull.Value  },
                new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)data.no_han ?? DBNull.Value  },
                new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)data.qty_haigo_h ?? DBNull.Value  },
                new SqlParameter("@no_kotei", SqlDbType.Int) { Value = (object)data.no_kotei ?? DBNull.Value  },
                new SqlParameter("@no_tonyu", SqlDbType.Int) { Value = (object)data.no_tonyu ?? DBNull.Value  },
                new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)data.cd_hin ?? DBNull.Value  },
                new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)data.kbn_hin ?? DBNull.Value  },
                new SqlParameter("@nm_hin", SqlDbType.VarChar, 60) { Value = (object)data.nm_hin ?? DBNull.Value  },
                new SqlParameter("@cd_mark", SqlDbType.VarChar, 2) { Value = (object)data.cd_mark ?? DBNull.Value  },
                new SqlParameter("@qty", SqlDbType.Float) { Value = (object)data.qty ?? DBNull.Value  },
                new SqlParameter("@qty_haigo", SqlDbType.Float) { Value = (object)data.qty_haigo ?? DBNull.Value  },
                new SqlParameter("@qty_nisugata", SqlDbType.Float) { Value = (object)data.qty_nisugata ?? DBNull.Value  },
                new SqlParameter("@su_nisugata", SqlDbType.Int) { Value = (object)data.su_nisugata ?? DBNull.Value  },
                new SqlParameter("@qty_kowake", SqlDbType.Float) { Value = (object)data.qty_kowake ?? DBNull.Value  },
                new SqlParameter("@su_kowake", SqlDbType.Int) { Value = (object)data.su_kowake ?? DBNull.Value  },
                new SqlParameter("@cd_futai", SqlDbType.VarChar, 2) { Value = (object)data.cd_futai ?? DBNull.Value  },
                new SqlParameter("@qty_futai", SqlDbType.Float) { Value = (object)data.qty_futai ?? DBNull.Value  },
                new SqlParameter("@hijyu", SqlDbType.Float) { Value = (object)data.hijyu ?? DBNull.Value  },
                new SqlParameter("@budomari", SqlDbType.Float) { Value = (object)data.budomari ?? DBNull.Value  },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)data.flg_sakujyo ?? DBNull.Value  },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)data.flg_mishiyo ?? DBNull.Value  },
                new SqlParameter("@dt_toroku", SqlDbType.DateTime) { Value = (object)data.dt_toroku ?? DBNull.Value  },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)data.dt_henko ?? DBNull.Value  },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)data.cd_koshin ?? DBNull.Value  },
                new SqlParameter("@kbn_jyotai", SqlDbType.VarChar, 1) { Value = (object)data.kbn_jyotai ?? DBNull.Value  },
                new SqlParameter("@kbn_shitei", SqlDbType.VarChar, 1) { Value = (object)data.kbn_shitei ?? DBNull.Value  },
                new SqlParameter("@kbn_bunkatsu", SqlDbType.TinyInt) { Value = (object)data.kbn_bunkatsu ?? DBNull.Value  }
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Update ma_haigo_recipe_hyoji table
        /// </summary>
        public void updateHaigoRecipeHyojiTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_haigo_recipe_hyoji data)
        {
            string query =
            (
                @"
                    UPDATE ma_haigo_recipe_hyoji
                    SET	qty_haigo_h = @qty_haigo_h
	                    ,no_kotei = @no_kotei
	                    ,no_tonyu = @no_tonyu
	                    ,cd_hin = @cd_hin
	                    ,kbn_hin = @kbn_hin
	                    ,nm_hin = @nm_hin
	                    ,cd_mark = @cd_mark
	                    ,qty = @qty
	                    ,qty_haigo = @qty_haigo
	                    ,qty_nisugata = @qty_nisugata
	                    ,su_nisugata = @su_nisugata
	                    ,qty_kowake = @qty_kowake
	                    ,su_kowake = @su_kowake
	                    ,cd_futai = @cd_futai
	                    ,qty_futai = @qty_futai
	                    ,hijyu = @hijyu
	                    ,budomari = @budomari
	                    ,flg_sakujyo = @flg_sakujyo
	                    ,flg_mishiyo = @flg_mishiyo
	                    ,dt_toroku = @dt_toroku
	                    ,dt_henko = @dt_henko
	                    ,cd_koshin = @cd_koshin
	                    ,kbn_jyotai = @kbn_jyotai
	                    ,kbn_shitei = @kbn_shitei
	                    ,kbn_bunkatsu = @kbn_bunkatsu
                     WHERE no_seq = @no_seq
                "
            );
            // Fix bug #15244 kbn_jyotai
            //{
            //    data.kbn_jyotai = "1";
            //}
            object[] parameterSQL = new object[]
            {
                new SqlParameter("@no_seq", SqlDbType.Int) { Value = (object)data.no_seq ?? DBNull.Value  },
                new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)data.qty_haigo_h ?? DBNull.Value  },
                new SqlParameter("@no_kotei", SqlDbType.Int) { Value = (object)data.no_kotei ?? DBNull.Value  },
                new SqlParameter("@no_tonyu", SqlDbType.Int) { Value = (object)data.no_tonyu ?? DBNull.Value  },
                new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)data.cd_hin ?? DBNull.Value  },
                new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)data.kbn_hin ?? DBNull.Value  },
                new SqlParameter("@nm_hin", SqlDbType.VarChar, 60) { Value = (object)data.nm_hin ?? DBNull.Value  },
                new SqlParameter("@cd_mark", SqlDbType.VarChar, 2) { Value = (object)data.cd_mark ?? DBNull.Value  },
                new SqlParameter("@qty", SqlDbType.Float) { Value = (object)data.qty ?? DBNull.Value  },
                new SqlParameter("@qty_haigo", SqlDbType.Float) { Value = (object)data.qty_haigo ?? DBNull.Value  },
                new SqlParameter("@qty_nisugata", SqlDbType.Float) { Value = (object)data.qty_nisugata ?? DBNull.Value  },
                new SqlParameter("@su_nisugata", SqlDbType.Int) { Value = (object)data.su_nisugata ?? DBNull.Value  },
                new SqlParameter("@qty_kowake", SqlDbType.Float) { Value = (object)data.qty_kowake ?? DBNull.Value  },
                new SqlParameter("@su_kowake", SqlDbType.Int) { Value = (object)data.su_kowake ?? DBNull.Value  },
                new SqlParameter("@cd_futai", SqlDbType.VarChar, 2) { Value = (object)data.cd_futai ?? DBNull.Value  },
                new SqlParameter("@qty_futai", SqlDbType.Float) { Value = (object)data.qty_futai ?? DBNull.Value  },
                new SqlParameter("@hijyu", SqlDbType.Float) { Value = (object)data.hijyu ?? DBNull.Value  },
                new SqlParameter("@budomari", SqlDbType.Float) { Value = (object)data.budomari ?? DBNull.Value  },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)data.flg_sakujyo ?? DBNull.Value  },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)data.flg_mishiyo ?? DBNull.Value  },
                new SqlParameter("@dt_toroku", SqlDbType.DateTime) { Value = (object)data.dt_toroku ?? DBNull.Value  },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)data.dt_henko ?? DBNull.Value  },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)data.cd_koshin ?? DBNull.Value  },
                new SqlParameter("@kbn_jyotai", SqlDbType.VarChar, 1) { Value = (object)data.kbn_jyotai ?? DBNull.Value  },
                new SqlParameter("@kbn_shitei", SqlDbType.VarChar, 1) { Value = (object)data.kbn_shitei ?? DBNull.Value  },
                new SqlParameter("@kbn_bunkatsu", SqlDbType.TinyInt) { Value = (object)data.kbn_bunkatsu ?? DBNull.Value  }
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Delete ma_haigo_recipe_hyoji table
        /// </summary>
        public void deleteHaigoRecipeHyojiTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_haigo_recipe_hyoji data)
        {
            string query =
            (
                @"
                    UPDATE ma_haigo_recipe_hyoji
                    SET	flg_sakujyo = @flg_sakujyo
	                    ,flg_mishiyo = @flg_mishiyo
	                    ,dt_henko = @dt_henko
	                    ,cd_koshin = @cd_koshin
                     WHERE no_seq = @no_seq
                "
            );

            object[] parameterSQL = new object[]
            {
                new SqlParameter("@no_seq", SqlDbType.Int) { Value = (object)data.no_seq ?? DBNull.Value  },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)data.flg_sakujyo ?? DBNull.Value  },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)data.flg_mishiyo ?? DBNull.Value  },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)data.dt_henko ?? DBNull.Value  },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)data.cd_koshin ?? DBNull.Value  }
                
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Insert ma_haigo_recipe table
        /// </summary>
        public void insertHaigoRecipeTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_haigo_recipe data)
        {
            string query =
            (
                @"
                    INSERT INTO ma_haigo_recipe
                    (
		                cd_haigo
                        ,no_han
                        ,qty_haigo_h
                        ,no_kotei
                        ,no_tonyu
                        ,cd_hin
                        ,kbn_hin
                        ,nm_hin
                        ,cd_mark
                        ,qty
                        ,qty_haigo
                        ,qty_nisugata
                        ,su_nisugata
                        ,qty_kowake
                        ,su_kowake
                        ,cd_futai
                        ,qty_futai
                        ,hijyu
                        ,budomari
                        ,flg_sakujyo
                        ,flg_mishiyo
                        ,dt_toroku
                        ,dt_henko
                        ,cd_koshin
                        ,kbn_jyotai
                        ,kbn_shitei
                        ,kbn_bunkatsu
                    )
                    VALUES
                    (
                        @cd_haigo
                        ,@no_han
                        ,@qty_haigo_h
                        ,@no_kotei
                        ,@no_tonyu
                        ,@cd_hin
                        ,@kbn_hin
                        ,@nm_hin
                        ,@cd_mark
                        ,@qty
                        ,@qty_haigo
                        ,@qty_nisugata
                        ,@su_nisugata
                        ,@qty_kowake
                        ,@su_kowake
                        ,@cd_futai
                        ,@qty_futai
                        ,@hijyu
                        ,@budomari
                        ,@flg_sakujyo
                        ,@flg_mishiyo
                        ,@dt_toroku
                        ,@dt_henko
                        ,@cd_koshin
                        ,@kbn_jyotai
                        ,@kbn_shitei
                        ,@kbn_bunkatsu
                    )
                "
            );
            // Fix bug #15244 kbn_jyotai
            if (data.no_seq == 0 && data.kbn_jyotai == null)
            {
                data.kbn_jyotai = "1";
            }
            object[] parameterSQL = new object[]
            {
                new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)data.cd_haigo ?? DBNull.Value  },
                new SqlParameter("@no_han", SqlDbType.Int) { Value = (object)data.no_han ?? DBNull.Value  },
                new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)data.qty_haigo_h ?? DBNull.Value  },
                new SqlParameter("@no_kotei", SqlDbType.Int) { Value = (object)data.no_kotei ?? DBNull.Value  },
                new SqlParameter("@no_tonyu", SqlDbType.Int) { Value = (object)data.no_tonyu ?? DBNull.Value  },
                new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)data.cd_hin ?? DBNull.Value  },
                new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)data.kbn_hin ?? DBNull.Value  },
                new SqlParameter("@nm_hin", SqlDbType.VarChar, 60) { Value = (object)data.nm_hin ?? DBNull.Value  },
                new SqlParameter("@cd_mark", SqlDbType.VarChar, 2) { Value = (object)data.cd_mark ?? DBNull.Value  },
                new SqlParameter("@qty", SqlDbType.Float) { Value = (object)data.qty ?? DBNull.Value  },
                new SqlParameter("@qty_haigo", SqlDbType.Float) { Value = (object)data.qty_haigo ?? DBNull.Value  },
                new SqlParameter("@qty_nisugata", SqlDbType.Float) { Value = (object)data.qty_nisugata ?? DBNull.Value  },
                new SqlParameter("@su_nisugata", SqlDbType.Int) { Value = (object)data.su_nisugata ?? DBNull.Value  },
                new SqlParameter("@qty_kowake", SqlDbType.Float) { Value = (object)data.qty_kowake ?? DBNull.Value  },
                new SqlParameter("@su_kowake", SqlDbType.Int) { Value = (object)data.su_kowake ?? DBNull.Value  },
                new SqlParameter("@cd_futai", SqlDbType.VarChar, 2) { Value = (object)data.cd_futai ?? DBNull.Value  },
                new SqlParameter("@qty_futai", SqlDbType.Float) { Value = (object)data.qty_futai ?? DBNull.Value  },
                new SqlParameter("@hijyu", SqlDbType.Float) { Value = (object)data.hijyu ?? DBNull.Value  },
                new SqlParameter("@budomari", SqlDbType.Float) { Value = (object)data.budomari ?? DBNull.Value  },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)data.flg_sakujyo ?? DBNull.Value  },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)data.flg_mishiyo ?? DBNull.Value  },
                new SqlParameter("@dt_toroku", SqlDbType.DateTime) { Value = (object)data.dt_toroku ?? DBNull.Value  },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)data.dt_henko ?? DBNull.Value  },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)data.cd_koshin ?? DBNull.Value  },
                new SqlParameter("@kbn_jyotai", SqlDbType.VarChar, 1) { Value = (object)data.kbn_jyotai ?? DBNull.Value  },
                new SqlParameter("@kbn_shitei", SqlDbType.VarChar, 1) { Value = (object)data.kbn_shitei ?? DBNull.Value  },
                new SqlParameter("@kbn_bunkatsu", SqlDbType.TinyInt) { Value = (object)data.kbn_bunkatsu ?? DBNull.Value  }
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Update ma_haigo_recipe table
        /// </summary>
        public void updateHaigoRecipeTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_haigo_recipe data)
        {
            // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ST
            // Remove update su_nisugata, qty_kowake, su_kowake
            string query =
            (
                @"
                    UPDATE ma_haigo_recipe
                    SET	qty_haigo_h = @qty_haigo_h
	                    ,no_kotei = @no_kotei
	                    ,no_tonyu = @no_tonyu
	                    ,cd_hin = @cd_hin
	                    ,kbn_hin = @kbn_hin
	                    ,nm_hin = @nm_hin
	                    ,cd_mark = @cd_mark
	                    ,qty = @qty
	                    ,qty_haigo = @qty_haigo
	                    ,qty_nisugata = @qty_nisugata
	                    ,cd_futai = @cd_futai
	                    ,qty_futai = @qty_futai
	                    ,hijyu = @hijyu
	                    ,budomari = @budomari
	                    ,flg_sakujyo = @flg_sakujyo
	                    ,flg_mishiyo = @flg_mishiyo
	                    ,dt_toroku = @dt_toroku
	                    ,dt_henko = @dt_henko
	                    ,cd_koshin = @cd_koshin
	                    ,kbn_jyotai = @kbn_jyotai
	                    ,kbn_shitei = @kbn_shitei
	                    ,kbn_bunkatsu = @kbn_bunkatsu
                     WHERE no_seq = @no_seq
                "
            );
            // Bug #16903　flg_mishiyo更新不具合対応  (2020-05-05) ED
            // Fix bug #15244 kbn_jyotai
            //{
            //    data.kbn_jyotai = "1";
            //}
            object[] parameterSQL = new object[]
            {
                new SqlParameter("@no_seq", SqlDbType.Int) { Value = (object)data.no_seq ?? DBNull.Value  },
                new SqlParameter("@qty_haigo_h", SqlDbType.Int) { Value = (object)data.qty_haigo_h ?? DBNull.Value  },
                new SqlParameter("@no_kotei", SqlDbType.Int) { Value = (object)data.no_kotei ?? DBNull.Value  },
                new SqlParameter("@no_tonyu", SqlDbType.Int) { Value = (object)data.no_tonyu ?? DBNull.Value  },
                new SqlParameter("@cd_hin", SqlDbType.VarChar, 13) { Value = (object)data.cd_hin ?? DBNull.Value  },
                new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)data.kbn_hin ?? DBNull.Value  },
                new SqlParameter("@nm_hin", SqlDbType.VarChar, 60) { Value = (object)data.nm_hin ?? DBNull.Value  },
                new SqlParameter("@cd_mark", SqlDbType.VarChar, 2) { Value = (object)data.cd_mark ?? DBNull.Value  },
                new SqlParameter("@qty", SqlDbType.Float) { Value = (object)data.qty ?? DBNull.Value  },
                new SqlParameter("@qty_haigo", SqlDbType.Float) { Value = (object)data.qty_haigo ?? DBNull.Value  },
                new SqlParameter("@qty_nisugata", SqlDbType.Float) { Value = (object)data.qty_nisugata ?? DBNull.Value  },
                new SqlParameter("@su_nisugata", SqlDbType.Int) { Value = (object)data.su_nisugata ?? DBNull.Value  },
                new SqlParameter("@qty_kowake", SqlDbType.Float) { Value = (object)data.qty_kowake ?? DBNull.Value  },
                new SqlParameter("@su_kowake", SqlDbType.Int) { Value = (object)data.su_kowake ?? DBNull.Value  },
                new SqlParameter("@cd_futai", SqlDbType.VarChar, 2) { Value = (object)data.cd_futai ?? DBNull.Value  },
                new SqlParameter("@qty_futai", SqlDbType.Float) { Value = (object)data.qty_futai ?? DBNull.Value  },
                new SqlParameter("@hijyu", SqlDbType.Float) { Value = (object)data.hijyu ?? DBNull.Value  },
                new SqlParameter("@budomari", SqlDbType.Float) { Value = (object)data.budomari ?? DBNull.Value  },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)data.flg_sakujyo ?? DBNull.Value  },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)data.flg_mishiyo ?? DBNull.Value  },
                new SqlParameter("@dt_toroku", SqlDbType.DateTime) { Value = (object)data.dt_toroku ?? DBNull.Value  },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)data.dt_henko ?? DBNull.Value  },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)data.cd_koshin ?? DBNull.Value  },
                new SqlParameter("@kbn_jyotai", SqlDbType.VarChar, 1) { Value = (object)data.kbn_jyotai ?? DBNull.Value  },
                new SqlParameter("@kbn_shitei", SqlDbType.VarChar, 1) { Value = (object)data.kbn_shitei ?? DBNull.Value  },
                new SqlParameter("@kbn_bunkatsu", SqlDbType.TinyInt) { Value = (object)data.kbn_bunkatsu ?? DBNull.Value  }
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Delete ma_haigo_recipe table
        /// </summary>
        public void deleteHaigoRecipeTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_haigo_recipe data)
        {
            string query =
            (
                @"
                    UPDATE ma_haigo_recipe
                    SET	flg_sakujyo = @flg_sakujyo
	                    ,flg_mishiyo = @flg_mishiyo
	                    ,dt_henko = @dt_henko
	                    ,cd_koshin = @cd_koshin
                     WHERE no_seq = @no_seq
                "
            );

            object[] parameterSQL = new object[]
            {
                new SqlParameter("@no_seq", SqlDbType.Int) { Value = (object)data.no_seq ?? DBNull.Value  },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)data.flg_sakujyo ?? DBNull.Value  },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)data.flg_mishiyo ?? DBNull.Value  },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)data.dt_henko ?? DBNull.Value  },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)data.cd_koshin ?? DBNull.Value  }
                
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Insert ma_seizo_line_hyoji
        /// </summary>
        public void insertMaSeizoLineHyojiTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_seizo_line_hyoji data)
        {
            string query =
            (
                @"
                    INSERT INTO ma_seizo_line_hyoji
                    (
	                    cd_haigo
	                    ,no_yusen
	                    ,cd_line
	                    ,flg_sakujyo
	                    ,flg_mishiyo
	                    ,dt_toroku
	                    ,dt_henko
	                    ,cd_koshin
                    )
                    VALUES
                    (
	                    @cd_haigo
	                    ,@no_yusen
	                    ,@cd_line
	                    ,@flg_sakujyo
	                    ,@flg_mishiyo
	                    ,@dt_toroku
	                    ,@dt_henko
	                    ,@cd_koshin
                    )
                "
            );
            object[] parameterSQL = new object[]
            {
                new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)data.cd_haigo ?? DBNull.Value  },
                new SqlParameter("@no_yusen", SqlDbType.Int) { Value = (object)data.no_yusen ?? DBNull.Value  },
                new SqlParameter("@cd_line", SqlDbType.VarChar, 3) { Value = (object)data.cd_line ?? DBNull.Value  },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)data.flg_sakujyo ?? DBNull.Value  },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)data.flg_mishiyo ?? DBNull.Value  },
                new SqlParameter("@dt_toroku", SqlDbType.DateTime) { Value = (object)data.dt_toroku ?? DBNull.Value  },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)data.dt_henko ?? DBNull.Value  },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)data.cd_koshin ?? DBNull.Value  }
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Insert ma_seizo_line
        /// </summary>
        public void insertMaSeizoLineTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_seizo_line data)
        {
            string query =
            (
                @"
                    INSERT INTO ma_seizo_line
                    (
	                    cd_haigo
	                    ,no_yusen
	                    ,cd_line
	                    ,flg_sakujyo
	                    ,flg_mishiyo
	                    ,dt_toroku
	                    ,dt_henko
	                    ,cd_koshin
                    )
                    VALUES
                    (
	                    @cd_haigo
	                    ,@no_yusen
	                    ,@cd_line
	                    ,@flg_sakujyo
	                    ,@flg_mishiyo
	                    ,@dt_toroku
	                    ,@dt_henko
	                    ,@cd_koshin
                    )
                "
            );
            object[] parameterSQL = new object[]
            {
                new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)data.cd_haigo ?? DBNull.Value  },
                new SqlParameter("@no_yusen", SqlDbType.Int) { Value = (object)data.no_yusen ?? DBNull.Value  },
                new SqlParameter("@cd_line", SqlDbType.VarChar, 3) { Value = (object)data.cd_line ?? DBNull.Value  },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)data.flg_sakujyo ?? DBNull.Value  },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)data.flg_mishiyo ?? DBNull.Value  },
                new SqlParameter("@dt_toroku", SqlDbType.DateTime) { Value = (object)data.dt_toroku ?? DBNull.Value  },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)data.dt_henko ?? DBNull.Value  },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)data.cd_koshin ?? DBNull.Value  },
                new SqlParameter("@qty_noryoku", SqlDbType.Float) { Value = (object)data.qty_noryoku ?? DBNull.Value  }
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Update ma_seizo_line_hyoji
        /// </summary>
        public void updateMaSeizoLineHyojiTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_seizo_line_hyoji data)
        {
            string query =
            (
                @"
                    UPDATE ma_seizo_line_hyoji
                    SET	no_yusen = @no_yusen,
	                    cd_line = @cd_line,
	                    dt_henko = @dt_henko,
	                    cd_koshin = @cd_koshin,
                        flg_sakujyo = @flg_sakujyo,
                        flg_mishiyo = @flg_mishiyo
                    WHERE no_seq = @no_seq
                "
            );
            object[] parameterSQL = new object[]
            {
                new SqlParameter("@no_seq", SqlDbType.Int) { Value = (object)data.no_seq ?? DBNull.Value  },
                new SqlParameter("@no_yusen", SqlDbType.Int) { Value = (object)data.no_yusen ?? DBNull.Value  },
                new SqlParameter("@cd_line", SqlDbType.VarChar, 3) { Value = (object)data.cd_line ?? DBNull.Value  },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)data.dt_henko ?? DBNull.Value  },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)data.cd_koshin ?? DBNull.Value  },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)data.flg_sakujyo ?? DBNull.Value  },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)data.flg_mishiyo ?? DBNull.Value  }
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Update ma_seizo_line
        /// </summary>
        public void updateMaSeizoLineTable(FOODPROCSEntities context, Tos.Web.DataFP.ma_seizo_line data)
        {
            string query =
            (
                @"
                    UPDATE ma_seizo_line
                    SET	no_yusen = @no_yusen,
	                    cd_line = @cd_line,
	                    dt_henko = @dt_henko,
	                    cd_koshin = @cd_koshin,
                        flg_sakujyo = @flg_sakujyo,
                        flg_mishiyo = @flg_mishiyo
                    WHERE no_seq = @no_seq
                "
            );
            object[] parameterSQL = new object[]
            {
                new SqlParameter("@no_seq", SqlDbType.Int) { Value = (object)data.no_seq ?? DBNull.Value  },
                new SqlParameter("@no_yusen", SqlDbType.Int) { Value = (object)data.no_yusen ?? DBNull.Value  },
                new SqlParameter("@cd_line", SqlDbType.VarChar, 3) { Value = (object)data.cd_line ?? DBNull.Value  },
                new SqlParameter("@dt_henko", SqlDbType.DateTime) { Value = (object)data.dt_henko ?? DBNull.Value  },
                new SqlParameter("@cd_koshin", SqlDbType.VarChar, 10) { Value = (object)data.cd_koshin ?? DBNull.Value  },
                new SqlParameter("@flg_sakujyo", SqlDbType.Bit) { Value = (object)data.flg_sakujyo ?? DBNull.Value  },
                new SqlParameter("@flg_mishiyo", SqlDbType.Bit) { Value = (object)data.flg_mishiyo ?? DBNull.Value  }
            };
            context.Database.ExecuteSqlCommand(query, parameterSQL);
        }

        /// <summary>
        /// Update no_seiho for all version of the haigo
        /// </summary>
        /// <param name="context"></param>
        /// <param name="tb_name"></param>
        /// <param name="cd_haigo"></param>
        /// <param name="no_seiho"></param>
        /// <param name="nm_seiho"></param>
        /// <returns></returns>
        private int UpdateAllVersion(FOODPROCSEntities context, string tb_name, string cd_haigo, string no_seiho, string nm_seiho)
        {
            int isError = 0;
            string query = 
                "       UPDATE  " + tb_name + 
                "       SET no_seiho = @no_seiho                " +
                "           ,nm_seiho = @nm_seiho               " +
                "       WHERE cd_haigo = @cd_haigo              " +
                "       AND flg_sakujyo = 0                     " +
                "       AND (flg_seiho_base IS NULL OR flg_seiho_base <> 1) " +

                "       IF @@ERROR <> 0                         " +
                "       BEGIN                                   " +
                "           SELECT 1                            " +
                "       END                                     " +
                "       ELSE                                    " +
                "       BEGIN                                   " +
                "        SELECT 0                               " +
                "       END                                     ";

            object[] parameterSQL = new object[]
                                {
                                    new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)cd_haigo ?? DBNull.Value  },
                                    new SqlParameter("@no_seiho", SqlDbType.VarChar, 20) { Value = (object)no_seiho ?? DBNull.Value  },
                                    new SqlParameter("@nm_seiho", SqlDbType.VarChar, 120) { Value = (object)nm_seiho ?? DBNull.Value  }
                                };
            isError = context.Database.SqlQuery<int>(query, parameterSQL).FirstOrDefault();
            return isError;
        }

        /// <summary>
        /// Get tantosha info with dynamic sql
        /// </summary>
        /// <param name="cd_tanto"></param>
        /// <param name="context"></param>
        /// <returns></returns>
        private wk_tanto getTantoData(string cd_tanto, FOODPROCSEntities context)
        {
            context.Configuration.ProxyCreationEnabled = false;

            var kbn_hin_shikakarihin = Properties.Resources.kbn_hin_shikakarihin;

            var query = "";

            query += " SELECT TOP 1 ";
            query += "      cd_tanto, nm_tanto ";
            query += " FROM ma_tanto ";
            query += " WHERE ";
            query += "      cd_tanto = @cd_tanto ";
            query += "      AND flg_sakujyo = 0 ";
            query += "      AND flg_mishiyo = 0 ";

            var parameters = new object[]
                {
                    new SqlParameter("@cd_tanto", SqlDbType.VarChar) { Value = cd_tanto }
                };

            var result = context.Database.SqlQuery<wk_tanto>(query, parameters).FirstOrDefault();
            return result;
        }

        /// <summary>
        /// Check if the ID till waiting
        /// </summary>
        /// <param name="P_ID"></param>
        /// <returns></returns>
        private bool checkIDInQueue(processID_B P_ID)
        {
            //processID_B firstID = priorityInsertHaigoProcessing.Where(x => x.cd_kaisha == P_ID.cd_kaisha && x.cd_kojo == P_ID.cd_kojo).FirstOrDefault();
            processID_B firstID = priorityInsertHaigoProcessing[0];
            if (firstID.ID == P_ID.ID)
            {
                return false;
            }
            return true;
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class SaveHaigoTorokuKojyoBumon
    {
        public ChangeSet<Tos.Web.DataFP.ma_haigo_mei_hyoji> cs_ma_haigo_mei_hyoji { get; set; }
        public ChangeSet<Tos.Web.DataFP.ma_haigo_mei> cs_ma_haigo_mei { get; set; }
        public ChangeSet<Tos.Web.DataFP.ma_haigo_recipe_hyoji> cs_ma_haigo_recipe_hyoji { get; set; }
        public ChangeSet<Tos.Web.DataFP.ma_haigo_recipe> cs_ma_haigo_recipe { get; set; }
        public ChangeSet<Tos.Web.DataFP.ma_seizo_line_hyoji> cs_ma_seizo_line_hyoji { get; set; }
        public ChangeSet<Tos.Web.DataFP.ma_seizo_line> cs_ma_seizo_line { get; set; }
        public ChangeSet<Tos.Web.DataFP.ma_seihin> cs_ma_seihin { get; set; }
        public int? qty_kihon_old { get; set; }
        public bool flg_mishiyo_old { get; set; }
        public int cd_kaisha;
        public int cd_kojyo;
        public int M_HaigoToroku;
        public int M_kirikae;
        public bool updateAllVersion;

        public SaveHaigoTorokuKojyoBumon()
        {
            cs_ma_haigo_mei_hyoji = new ChangeSet<Tos.Web.DataFP.ma_haigo_mei_hyoji>();
            cs_ma_haigo_mei = new ChangeSet<Tos.Web.DataFP.ma_haigo_mei>();
            cs_ma_haigo_recipe_hyoji = new ChangeSet<Tos.Web.DataFP.ma_haigo_recipe_hyoji>();
            cs_ma_haigo_recipe = new ChangeSet<Tos.Web.DataFP.ma_haigo_recipe>();
            cs_ma_seizo_line_hyoji = new ChangeSet<Tos.Web.DataFP.ma_seizo_line_hyoji>();
            cs_ma_seizo_line = new ChangeSet<Tos.Web.DataFP.ma_seizo_line>();
            cs_ma_seihin = new ChangeSet<Tos.Web.DataFP.ma_seihin>();
            cd_kaisha = 0;
            cd_kojyo = 0;
            M_HaigoToroku = 0;
            M_kirikae = 0;
            updateAllVersion = true;
        }
    }

    public class wk_tanto
    {
        public string cd_tanto { get; set; }
        public string nm_tanto { get; set; }
    }
    
    public class ma_haigo_mei_def_col 
    {
        public Nullable<bool> flg_tenkai { get; set; }
        public Nullable<double> qty_haigo_auto { get; set; }
        public Nullable<int> su_kigen_min { get; set; }
        public Nullable<int> su_kigen_max { get; set; }
        public Nullable<bool> flg_mishiyo_seizo { get; set; }
    }

    public class processID_B : processID
    {
        public int cd_kaisha { get; set; }
        public int cd_kojo { get; set; }
    }

    #endregion
}
