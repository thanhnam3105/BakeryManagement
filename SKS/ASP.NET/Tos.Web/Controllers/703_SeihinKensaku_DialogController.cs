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

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _703_SeihinKensaku_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのheader情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>header情報</returns>
        public object Get([FromUri]SeihinKensakuSearchRequest Conditions)
        {
            dynamic result = new System.Dynamic.ExpandoObject();

            StoredProcedureResult<sp_shohinkaihatsu_search_703_Result> data_lab = new StoredProcedureResult<sp_shohinkaihatsu_search_703_Result>();

            if (Conditions == null || Conditions.mode == 0)
            {
                result.Count = null;
                result.Items = null;
                return result;
            }

            int cd_kaisha = Conditions.cd_kaisha ?? 0;
            int cd_kojyo = Conditions.cd_kojyo ?? 0;
            bool check = Conditions.check;
            IQueryable<SeihinKensakuSearchResponse> searchQuery = null;

            switch (Conditions.mode)
            {
                case 1:
                    using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                    {
                        data_lab.Items = context.sp_shohinkaihatsu_search_703(Conditions.seihin,
                                                                               check,
                                                                               Conditions.top).ToList();
                        result.Items = data_lab.Items;
                    }
                    break;
                case 2:
                    if (Conditions.cd_kaisha == null || Conditions.cd_kojyo == null)
                    {
                        result.Count = null;
                        result.Items = null;
                        return result;
                    }
                    else
                    {
                        using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
                        {
                            string seihin = Conditions.seihin;
                            if (check == true)
                            {

                                searchQuery = (from x in context.ma_seihin
                                               join y in context.ma_konpo
                                               on new { cd_hin = x.cd_hin, flg_sakujyo = false, flg_mishiyo = false } equals new { cd_hin = y.cd_hin, flg_sakujyo = y.flg_sakujyo, flg_mishiyo = y.flg_mishiyo } into ps
                                               from p in ps.DefaultIfEmpty()
                                               join z in context.ma_haigo_mei_hyoji
                                               on new { cd_haigo_hyoji = x.cd_haigo_hyoji, flg_sakujyo = false, flg_mishiyo = false, no_han = 1 } equals new { cd_haigo_hyoji = z.cd_haigo, flg_sakujyo = z.flg_sakujyo, flg_mishiyo = z.flg_mishiyo, no_han = z.no_han } into qs
                                               from q in qs.DefaultIfEmpty()
                                               where
                                                        x.flg_sakujyo == false
                                                     && x.flg_mishiyo == false
                                                     & x.cd_haigo_hyoji == null
                                                     && (
                                                            Conditions.seihin == null
                                                         || x.cd_hin.Contains(seihin)
                                                         || x.nm_hin.Contains(seihin)
                                                     )
                                               orderby x.cd_hin
                                               select new SeihinKensakuSearchResponse()
                                               {
                                                   cd_seihin = x.cd_hin,
                                                   nm_seihin = x.nm_hin,
                                                   nisugata_hyoji = p.nisugata_hyoji,
                                                   no_seiho = null,
                                                   cd_haigo_hyoji = x.cd_haigo_hyoji,
                                                   nm_haigo = q.nm_haigo
                                               }).Distinct();
                            }
                            else
                            {
                                searchQuery = (from x in context.ma_seihin
                                               join y in context.ma_konpo
                                               on new { cd_hin = x.cd_hin, flg_sakujyo = false, flg_mishiyo = false } equals new { cd_hin = y.cd_hin, flg_sakujyo = y.flg_sakujyo, flg_mishiyo = y.flg_mishiyo } into ps
                                               from p in ps.DefaultIfEmpty()
                                               join z in context.ma_haigo_mei_hyoji
                                               on new { cd_haigo_hyoji = x.cd_haigo_hyoji, flg_sakujyo = false, flg_mishiyo = false, no_han = 1 } equals new { cd_haigo_hyoji = z.cd_haigo, flg_sakujyo = z.flg_sakujyo, flg_mishiyo = z.flg_mishiyo, no_han = z.no_han } into qs
                                               from q in qs.DefaultIfEmpty()
                                               where x.flg_sakujyo == false
                                                     && x.flg_mishiyo == false
                                                     && (
                                                            Conditions.seihin == null
                                                         || x.cd_hin.Contains(seihin)
                                                         || x.nm_hin.Contains(seihin)
                                                     )
                                               orderby x.cd_hin
                                               select new SeihinKensakuSearchResponse()
                                               {
                                                   cd_seihin = x.cd_hin,
                                                   nm_seihin = x.nm_hin,
                                                   nisugata_hyoji = p.nisugata_hyoji,
                                                   no_seiho = null,
                                                   cd_haigo_hyoji = x.cd_haigo_hyoji,
                                                   nm_haigo = q.nm_haigo
                                               }).Distinct();
                            }
                            result.Count = searchQuery.Count();
                            result.Items = searchQuery.Take(Conditions.top).ToList();
                        }
                    }
                    break;
                case 3:
                    if (Conditions.cd_kaisha == null || Conditions.cd_kojyo == null)
                    {
                        result.Count = null;
                        result.Items = null;
                        return result;
                    }
                    else
                    {
                        using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
                        {
                            string seihin = Conditions.seihin;
                            searchQuery = (from x in context.ma_seihin
                                           join y in context.ma_konpo
                                           on new { cd_hin = x.cd_hin, flg_sakujyo = false, flg_mishiyo = false } equals new { cd_hin = y.cd_hin, flg_sakujyo = y.flg_sakujyo, flg_mishiyo = y.flg_mishiyo } into ps
                                           from p in ps.DefaultIfEmpty()
                                           join z in context.ma_haigo_mei
                                           on new { cd_haigo = x.cd_haigo, flg_sakujyo = false, flg_mishiyo = false, no_han = 1 } equals new { cd_haigo = z.cd_haigo, flg_sakujyo = z.flg_sakujyo, flg_mishiyo = z.flg_mishiyo, no_han = z.no_han } into qs
                                           from q in qs.DefaultIfEmpty()
                                           where x.flg_sakujyo == false
                                                 && x.flg_mishiyo == false
                                                 && (
                                                        Conditions.seihin == null
                                                     || x.cd_hin.Contains(seihin)
                                                     || x.nm_hin.Contains(seihin)
                                                 )
                                           orderby x.cd_hin
                                           select new SeihinKensakuSearchResponse()
                                           {
                                               cd_seihin = x.cd_hin,
                                               nm_seihin = x.nm_hin,
                                               nisugata_hyoji = p.nisugata_hyoji,
                                               no_seiho = null,
                                               cd_haigo_hyoji = x.cd_haigo,
                                               nm_haigo = q.nm_haigo
                                           }).Distinct();
                            result.Count = searchQuery.Count();
                            result.Items = searchQuery.Take(Conditions.top).ToList();
                        }
                    }
                    break;
            }

            if (searchQuery == null)
            {
                //result.Count = result.Items.Count() > 0 ? (result.Items.First().cnt ?? 0) : 0;
                data_lab.Count = data_lab.Items.Count() > 0 ? (data_lab.Items.First().cnt ?? 0) : 0;
                result.Count = data_lab.Count;
            }
             
            return result;
        }

        #endregion


    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class SeihinKensakuSearchResponse
    {
        public string cd_seihin { get; set; }
        public string nm_seihin { get; set; }
        public string nisugata_hyoji { get; set; }
        public string no_seiho { get; set; }
        public string cd_haigo_hyoji { get; set; }
        public string nm_haigo { get; set; }
        public int cnt { get; set; }
    }

    public class SeihinKensakuSearchRequest
    {
        // Search conditions
        public string seihin { get; set; }
        public bool check { get; set; }
        public int? mode { get; set; }
        public int? cd_kaisha { get; set; }
        public int? cd_kojyo { get; set; }
        // Pagging
        public int top { get; set; }
    }

    #endregion
}
