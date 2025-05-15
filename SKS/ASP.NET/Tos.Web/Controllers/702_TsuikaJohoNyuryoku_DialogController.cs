using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.OData;
using System.Web.Http.OData.Query;
using Tos.Web.Data;
using Tos.Web.DataFP;
using System.Data.SqlClient;
using System.Data;

namespace Tos.Web.Controllers
{
    public class TsuikaJohoNyuryoku_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"

        private const string LineCodeDefaultInModeKojyo  = "999";

        ///**Method
        // * Get data in case not have no_haigo.
        // */
        public List<sp_shohinkaihatsu_searchInCaseNotNoHaigo_702_Result> getDataInCaseNotNoHaigo(int cd_kaisha, int cd_kojyo, bool modeKojyo)
        {

            if (modeKojyo)
            {
                FOODPROCSEntities context_kojyo = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();

                context_kojyo.Configuration.ProxyCreationEnabled = false;
                context.Configuration.ProxyCreationEnabled = false;

                vw_ma_kojyo kojyo = new vw_ma_kojyo();

                kojyo = (from m in context.vw_ma_kojyo 
                          where m.cd_kaisha == cd_kaisha
                          && m.cd_kojyo == cd_kojyo
                          select m).FirstOrDefault();

                string cur_cd_line = CommonController.getFullString(kojyo.cd_line.ToString(), kojyo.su_linecode_standard);
                
                var line = (from m in context_kojyo.ma_line
                            where m.cd_line == cur_cd_line
                            && m.flg_sakujyo == false
                            select new { 
                                cd_line = m.cd_line,
                                nm_line = m.nm_line
                            }).FirstOrDefault();

                List<sp_shohinkaihatsu_searchInCaseNotNoHaigo_702_Result> result = new List<sp_shohinkaihatsu_searchInCaseNotNoHaigo_702_Result>();
                sp_shohinkaihatsu_searchInCaseNotNoHaigo_702_Result item = new sp_shohinkaihatsu_searchInCaseNotNoHaigo_702_Result();
                item.cd_kaisha = (short)cd_kaisha;
                item.cd_kojyo = (short)cd_kojyo;
                item.budomari = kojyo.budomari;
                item.qty_max = kojyo.qty_max;
                item.qty_kihon = kojyo.qty_kihon;
                item.hijyu = kojyo.hijyu;
                //item.cd_line = short.Parse(LineCodeDefaultInModeKojyo);
                item.cd_line = kojyo.cd_line;
                item.su_linecode_standard = kojyo.su_linecode_standard;
                if (kojyo.cd_setsubi != null && kojyo.cd_setsubi != 0)
                {
                    item.cd_setsubi = kojyo.cd_setsubi;
                }
                
                if (line != null)
                {
                    item.nm_line = line.nm_line;
                }
                else
                {
                    item.nm_line = null;
                }
                result.Add(item);
                return result;
            }
            else
            {
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
                context.Configuration.ProxyCreationEnabled = false;
                List<sp_shohinkaihatsu_searchInCaseNotNoHaigo_702_Result> result = new List<sp_shohinkaihatsu_searchInCaseNotNoHaigo_702_Result>();

                result = context.sp_shohinkaihatsu_searchInCaseNotNoHaigo_702(cd_kaisha, cd_kojyo).ToList();

                return result;
            }
        }

        ///**Method
        // * Get data in case have no_haigo.
        // */
        public TsuikaJohoNyuryokuDialogResponse_Search getDataInCaseNoHaigo( string cd_haigo, int cd_kaisha, int cd_kojyo, int M_kirikae, bool modeKojyo)
        {
            if (modeKojyo)
            {
                FOODPROCSEntities context_kojyo = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();

                context_kojyo.Configuration.ProxyCreationEnabled = false;
                context.Configuration.ProxyCreationEnabled = false;

                TsuikaJohoNyuryokuDialogResponse_Search result = new TsuikaJohoNyuryokuDialogResponse_Search();

                string str_cd_haigo = String.Format("{0:D7}", cd_haigo);

                vw_ma_kojyo kojyo = new vw_ma_kojyo();
                kojyo = (from m in context.vw_ma_kojyo
                         where m.cd_kaisha == cd_kaisha
                         && m.cd_kojyo == cd_kojyo
                         select m).FirstOrDefault();

                if (M_kirikae == 1)
                {
                    var haigo_mei_hyoji = (
                                                from m in context_kojyo.ma_haigo_mei_hyoji
                                                where m.cd_haigo == str_cd_haigo
                                                && m.no_han == 1
                                                && m.flg_sakujyo == false
                                                select new
                                                {
                                                    budomari = m.budomari,
                                                    qty_kihon = m.qty_kihon,
                                                    hijyu = m.hijyu,
                                                    qty_max = m.qty_max,
                                                    flg_gasan = m.flg_gasan,
                                                    flg_shorihin = m.flg_shorihin,
                                                    cd_setsubi = m.cd_setsubi,
                                                    cd_bunrui = m.cd_bunrui
                                                }
                                            ).FirstOrDefault();

                    if (haigo_mei_hyoji != null)
                    {
                        result.Header.cd_haigo = str_cd_haigo;
                        result.Header.budomari = haigo_mei_hyoji.budomari;
                        result.Header.qty_kihon = haigo_mei_hyoji.qty_kihon;
                        result.Header.hijyu = haigo_mei_hyoji.hijyu;
                        result.Header.qty_max = haigo_mei_hyoji.qty_max;
                        result.Header.flg_gasan = haigo_mei_hyoji.flg_gasan;
                        result.Header.flg_shorihin = haigo_mei_hyoji.flg_shorihin;
                        result.Header.cd_setsubi_string = haigo_mei_hyoji.cd_setsubi;
                        result.Header.cd_bunrui_string = haigo_mei_hyoji.cd_bunrui;
                        

                        if (kojyo != null)
                        {
                            result.Header.su_linecode_standard = kojyo.su_linecode_standard;
                        }
                    }
                    else
                    {
                        result.Header.cd_haigo = null;
                    }

                    
                    var lst_seizo_line_hyoji = (from m in context_kojyo.ma_seizo_line_hyoji
                                                where m.cd_haigo == str_cd_haigo
                                                && m.flg_sakujyo == false
                                                select new{
                                                    no_yusen = m.no_yusen,
                                                    cd_line = m.cd_line
                                                }).ToList();

                    var lst_line = (from m in context_kojyo.ma_line
                                    where m.flg_sakujyo == false
                                    select new
                                    {
                                        cd_line = m.cd_line,
                                        nm_line = m.nm_line
                                    }).ToList();

                    result.Detail = (
                                        from t1 in lst_seizo_line_hyoji
                                        join t2 in lst_line
                                        on t1.cd_line equals t2.cd_line into t3
                                        from l in t3.DefaultIfEmpty()
                                        select new TsuikaJohoNyuryokuDialogResponse_Detail()
                                        {
                                            no_yusen = t1.no_yusen,
                                            //cd_line = int.Parse(t1.cd_line),
                                            cd_line = t1.cd_line,
                                            nm_line = l == null ? null : l.nm_line
                                        }
                                     ).ToList().OrderBy(x => x.no_yusen).ToList();


                    return result;
                }
                else
                {
                    
                    var haigo_mei = (
                                    from m in context_kojyo.ma_haigo_mei
                                    where m.cd_haigo == str_cd_haigo
                                    && m.no_han == 1
                                    //&& m.flg_mishiyo == false
                                    && m.flg_sakujyo == false
                                    select new
                                    {
                                        budomari = m.budomari,
                                        qty_kihon = m.qty_kihon,
                                        hijyu = m.hijyu,
                                        qty_max = m.qty_max,
                                        flg_gasan = m.flg_gasan,
                                        flg_shorihin = m.flg_shorihin,
                                        cd_setsubi = m.cd_setsubi,
                                        cd_bunrui = m.cd_bunrui
                                    }
                                ).FirstOrDefault();


                    if (haigo_mei != null)
                    {
                        result.Header.cd_haigo = str_cd_haigo;
                        result.Header.budomari = haigo_mei.budomari;
                        result.Header.qty_kihon = haigo_mei.qty_kihon;
                        result.Header.hijyu = haigo_mei.hijyu;
                        result.Header.qty_max = haigo_mei.qty_max;
                        result.Header.flg_gasan = haigo_mei.flg_gasan;
                        result.Header.flg_shorihin = haigo_mei.flg_shorihin;
                        result.Header.cd_setsubi_string = haigo_mei.cd_setsubi;
                        result.Header.cd_bunrui_string = haigo_mei.cd_bunrui;
                        if (kojyo != null)
                        {
                            result.Header.su_linecode_standard = kojyo.su_linecode_standard;
                        }
                    }
                    else
                    {
                        result.Header.cd_haigo = null;
                    }

                    var lst_seizo_line = (from m in context_kojyo.ma_seizo_line
                                          where m.cd_haigo == str_cd_haigo
                                          && m.flg_mishiyo == false
                                          && m.flg_sakujyo == false
                                          select new {
                                              no_yusen = m.no_yusen,
                                              cd_line = m.cd_line
                                          }).ToList();

                    var lst_line = (from m in context_kojyo.ma_line
                                    where m.flg_sakujyo == false
                                    select new
                                    {
                                        cd_line = m.cd_line,
                                        nm_line = m.nm_line
                                    }).ToList();

                    result.Detail = (
                                        from t1 in lst_seizo_line
                                        join t2 in lst_line
                                        on t1.cd_line equals t2.cd_line into t3
                                        from l in t3.DefaultIfEmpty()
                                        select new TsuikaJohoNyuryokuDialogResponse_Detail()
                                        {
                                            no_yusen = t1.no_yusen,
                                            //cd_line = int.Parse(t1.cd_line),
                                            cd_line = t1.cd_line,
                                            nm_line = l == null ? null : l.nm_line
                                        }
                                     ).ToList().OrderBy(x => x.no_yusen).ToList();
                    return result;
                }
            }
            else
            {
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
                context.Configuration.ProxyCreationEnabled = false;

                TsuikaJohoNyuryokuDialogResponse_Search result = new TsuikaJohoNyuryokuDialogResponse_Search();

                decimal d_cd_haigo = decimal.Parse(cd_haigo);

                ma_haigo_header haigo_header = new ma_haigo_header();
                haigo_header = (from m in context.ma_haigo_header
                                where m.cd_haigo == d_cd_haigo
                               select m).FirstOrDefault();

                vw_ma_kojyo kojyo = new vw_ma_kojyo();
                kojyo = (from m in context.vw_ma_kojyo
                         where m.cd_kaisha == cd_kaisha
                         && m.cd_kojyo == cd_kojyo
                         select m).FirstOrDefault();

                if (haigo_header != null)
                {
                    result.Header.cd_haigo = cd_haigo;
                    result.Header.budomari = haigo_header.budomari;
                    result.Header.qty_kihon = haigo_header.qty_kihon;
                    result.Header.hijyu = haigo_header.hijyu;
                    result.Header.cd_setsubi = haigo_header.cd_setsubi;
                    result.Header.cd_bunrui = haigo_header.cd_bunrui;
                    result.Header.qty_max = haigo_header.qty_max;
                    result.Header.flg_gasan = haigo_header.flg_gasan;
                    result.Header.flg_shorihin = false;
                    if(kojyo != null){
                        result.Header.su_linecode_standard = kojyo.su_linecode_standard;
                    }
                }
                else
                {
                    result.Header.cd_haigo = null; ;
                }

                List<vw_ma_seizo_line> lst_seizo_line = new List<vw_ma_seizo_line>();
                lst_seizo_line = (from m in context.vw_ma_seizo_line
                                  where m.cd_haigo == d_cd_haigo
                                  select m).ToList();

                List<ma_line_togo> lst_line_togo = new List<ma_line_togo>();
                lst_line_togo = (from m in context.ma_line_togo
                                 where m.cd_kaisha == cd_kaisha
                                 && m.cd_seizokojo == cd_kojyo
                                 select m).ToList();

                result.Detail = (
                                    from t1 in lst_seizo_line
                                    join t2 in lst_line_togo
                                    on t1.cd_line equals t2.cd_line into t3
                                    from x in t3.DefaultIfEmpty()
                                    select new TsuikaJohoNyuryokuDialogResponse_Detail()
                                    {
                                        no_yusen = t1.no_yusen,
                                        cd_line = t1.cd_line.ToString(),
                                        nm_line = x == null ? null : x.nm_line
                                    }
                                ).ToList().OrderBy(x => x.no_yusen).ToList();
                return result;
            }
        }

        ///**Method
        // * Get data Bunrui combobox
        // */
        public Object getDataBunruiCombobox(int cd_kaisha, int cd_kojyo, int kbn_hin, bool modeKojyo)
        {
            if (modeKojyo)
            {
                FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
                context.Configuration.ProxyCreationEnabled = false;
                string str_kbn_hin = kbn_hin.ToString();
                Object bunrui = new Object();
                bunrui = (from m in context.ma_bunrui
                          where m.kbn_hin == str_kbn_hin
                          && m.flg_mishiyo == false
                          && m.flg_sakujyo == false
                          select new
                          {
                              cd_bunrui = m.cd_bunrui,
                              nm_bunrui = m.nm_bunrui
                          }).ToList().OrderBy(m => m.cd_bunrui).ToList();
                return bunrui;
            }
            else
            {
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
                context.Configuration.ProxyCreationEnabled = false;
                Object bunrui = new Object();
                bunrui = (from m in context.vw_ma_bunrui
                          where m.cd_kaisha == cd_kaisha
                          && m.cd_kojyo == cd_kojyo
                          && m.kbn_hin == kbn_hin
                          && m.flg_mishiyo == false
                          select new
                          {
                              cd_bunrui = m.cd_bunrui,
                              nm_bunrui = m.nm_bunrui
                          }).ToList().OrderBy(m => m.cd_bunrui).ToList();
                return bunrui;
            }
        }

        ///**Method
        // * Get data Setsubi combobox
        // */
        public Object getDataSetsubiCombobox(int cd_kaisha, int cd_kojyo, bool modeKojyo)
        {
            if (modeKojyo)
            {
                FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
                context.Configuration.ProxyCreationEnabled = false;

                Object setsubi = new Object();
                setsubi = (from m in context.ma_setsubi
                           where m.flg_mishiyo == false
                           && m.flg_sakujyo == false
                           select new
                           {
                               cd_setsubi = m.cd_setsubi,
                               nm_setsubi = m.nm_setsubi
                           }).ToList().OrderBy(m => m.cd_setsubi).ToList();
                return setsubi;
            }
            else
            {
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
                context.Configuration.ProxyCreationEnabled = false;
                Object setsubi = new Object();
                setsubi = (from m in context.vw_ma_setsubi
                           where m.cd_kaisha == cd_kaisha
                           && m.cd_kojyo == cd_kojyo
                           && m.flg_mishiyo == false
                           select new
                           {
                               cd_setsubi = m.cd_setsubi,
                               nm_setsubi = m.nm_setsubi
                           }).ToList().OrderBy(m => m.cd_setsubi).ToList();
                return setsubi;
            }
        }


        ///**Method
        // * Get line name 
        // */
        public Object getLineName(int cd_kaisha, int cd_kojyo, int cd_line, int su_linecode_standard, bool modeKojyo)
        {
            if (modeKojyo)
            {
                FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
                context.Configuration.ProxyCreationEnabled = false;
                string query = "";
                object[] parameterSQL;
                query =
                (
                    @"
                        SELECT TOP 1
                        cd_line
                        ,nm_line 
                        FROM ma_line
                        WHERE CONVERT(INT,cd_line) = @cd_line
                        AND flg_sakujyo = 0
                    "
                );

                parameterSQL = new object[]
                {
                    new SqlParameter("@cd_line", SqlDbType.Int) { Value = (object)cd_line ?? DBNull.Value },
                };

                var line = context.Database.SqlQuery<LineFrom>(query, parameterSQL).FirstOrDefault();
                
                return line;
            }
            else
            {
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
                context.Configuration.ProxyCreationEnabled = false;
                var line_togo = (from m in context.ma_line_togo
                                 where m.cd_kaisha == cd_kaisha
                                 && m.cd_seizokojo == cd_kojyo
                                 && m.cd_line == cd_line
                                 select new
                                 {
                                     cd_line = m.cd_line,
                                     nm_line = m.nm_line
                                 }).FirstOrDefault();
                return line_togo;
            }
        }


        #endregion

    }



    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class TsuikaJohoNyuryokuDialogResponse_Header
    {
        public string cd_haigo { get; set; }
        public double budomari{get;set;}
        public int qty_kihon{get;set;}
        public int? cd_setsubi { get; set; }
        public string cd_setsubi_string { get; set; }
        public int? cd_bunrui { get; set; }
        public string cd_bunrui_string { get; set; }
        public double? hijyu { get; set; }
        public double? qty_max { get; set; }
        public bool? flg_gasan{get;set;}
        public bool? flg_shorihin{get;set;}
        public int su_linecode_standard { get; set; }



        public TsuikaJohoNyuryokuDialogResponse_Header()
        {
            this.cd_haigo = null;
            this.budomari = 0;
            this.qty_kihon = 0;
            this.hijyu = null;
            this.qty_max = null;
            this.flg_gasan = null;
            this.flg_shorihin = null;
            this.su_linecode_standard = 0;
            this.cd_setsubi = null;
            this.cd_setsubi_string = null;
            this.cd_bunrui = null;
            this.cd_bunrui_string = null;
        }
    }

    public class TsuikaJohoNyuryokuDialogResponse_Detail
    {
        public int no_yusen{get;set;}
        public string cd_line{get;set;}
        public string nm_line{get;set;}

        public TsuikaJohoNyuryokuDialogResponse_Detail()
        {
            this.no_yusen = 0;
            this.cd_line = "0";
            this.nm_line = "";
        }
        
    }

    public class TsuikaJohoNyuryokuDialogResponse_Search
    {
        public TsuikaJohoNyuryokuDialogResponse_Header Header{get;set;}
        public List<TsuikaJohoNyuryokuDialogResponse_Detail> Detail { get; set; }

        public TsuikaJohoNyuryokuDialogResponse_Search()
        {
            this.Header = new TsuikaJohoNyuryokuDialogResponse_Header();
            this.Detail = new List<TsuikaJohoNyuryokuDialogResponse_Detail>();
        }
    }

    //Result data ma_line FROM
    public class LineFrom
    {
        public string cd_line { get; set; }
        public string nm_line { get; set; }
    }

    #endregion
}
