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

namespace Tos.Web.Controllers
{
    public class SeihinJohoNyuryoku_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"
        private const int m_kirikae_hyoji = 1;

        ///**Method
        // * Get data in case have no_haigo.
        // */
        public Object getData(int cd_kaisha, int cd_kojyo, string cd_haigo, int M_kirikae, bool modeKojyo)
        {
            if (modeKojyo)
            {
                FOODPROCSEntities context_kojyo = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();

                context_kojyo.Configuration.ProxyCreationEnabled = false;
                context.Configuration.ProxyCreationEnabled = false;

                dynamic result = new System.Dynamic.ExpandoObject();

                string strcd_haigo = String.Format("{0:D7}", cd_haigo);

                if (M_kirikae == 1)
                {
                    var query =
                          " SELECT "
                        + "   s.cd_hin "
                        + " , s.no_yusen_hyoji AS no_yusen "
                        + " , s.nm_hin AS nm_seihin "
                        + " , mk.nisugata_hyoji "
                        + " FROM ma_seihin s "
                        + " LEFT OUTER JOIN ma_konpo  mk "
                        + " ON    s.cd_hin = mk.cd_hin "
                        + " AND   mk.flg_sakujyo     <> 1 "
                        + " WHERE s.cd_haigo_hyoji = @cd_hin "
                        + " AND s.flg_sakujyo=0 "
                        + " ORDER BY s.no_yusen_hyoji ";
                    result.data = context_kojyo.Database.SqlQuery<SeihinInfo>(query, new SqlParameter("@cd_hin", strcd_haigo)).ToList();
                }
                //M_kirikae == 2
                else
                {
                    var query =
                            "SELECT "
                        + "   s.cd_hin "
                        + " , s.no_yusen "
                        + " , s.nm_hin AS nm_seihin "
                        + " , mk.nisugata_hyoji "
                        + " FROM ma_seihin s "
                        + " LEFT OUTER JOIN ma_konpo  mk "
                        + " ON    s.cd_hin = mk.cd_hin "
                        + " AND   mk.flg_sakujyo     <> 1 "
                        + " WHERE s.cd_haigo = @cd_hin "
                        + " AND s.flg_sakujyo=0 "
                        + " ORDER BY s.no_yusen ";

                    result.data = context_kojyo.Database.SqlQuery<SeihinInfo>(query, new SqlParameter("@cd_hin", strcd_haigo)).ToList();
                }

                return result;
                
            }
            else
            {
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
                context.Configuration.ProxyCreationEnabled = false;
                decimal d_cd_haigo = decimal.Parse(cd_haigo);

                List<ma_seihin_seiho> result = new List<ma_seihin_seiho>();
                result = (from m in context.ma_seihin_seiho
                          where m.cd_haigo == d_cd_haigo
                          select m).ToList().OrderBy(x => x.no_yusen).ToList();
                return result;
            }
        }


        ///**Method
        // * Get line name 
        // */
        public Object getHinName(int cd_kaisha, int cd_kojyo, string cd_hin, bool modeKojyo, int? M_kirikae)
        {
            if (modeKojyo)
            {
                FOODPROCSEntities context_kojyo = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
                context_kojyo.Configuration.ProxyCreationEnabled = false;
                Info_Hin result = new Info_Hin();

                var query = "   SELECT TOP 1                                                    " +
                            "       seihin.cd_hin                                               " +
                            "       ,seihin.nm_hin  AS nm_seihin                                " +
                            "       ,haigo_mei.cd_haigo                                         " +
                            "       ,haigo_mei_hyoji.cd_haigo AS cd_haigo_hyoji                 " +
                            "       ,konpo.nisugata_hyoji                                       " +
                            "       ,seihin.cd_haigo AS cd_haigo_check                          " +
                            "       ,seihin.cd_haigo_hyoji AS cd_haigo_hyoji_check              " +
                            "   FROM                                                            " +
                            "   (                                                               " +
                            "       SELECT                                                      " +
                            "           cd_hin                                                  " +
                            "           ,nm_hin                                                 " +
                            "           ,cd_haigo                                               " +
                            "           ,cd_haigo_hyoji                                         " +
                            "       FROM ma_seihin                                              " +
                            "       WHERE cd_hin = @cd_hin                                      " +
                            "       AND flg_mishiyo = 0                                         " +
                            "       AND flg_sakujyo = 0                                         " +
                            "   )seihin                                                         " +
                            "   LEFT JOIN ma_konpo konpo                                        " +
                            "   ON konpo.cd_hin  = @cd_hin                                      " +
                            "   AND konpo.flg_mishiyo = 0                                       " +
                            "   AND konpo.flg_sakujyo = 0                                       " +
                            "   LEFT JOIN ma_haigo_mei_hyoji haigo_mei_hyoji                    " +
                            "   ON seihin.cd_haigo_hyoji = haigo_mei_hyoji.cd_haigo             " +
                            "   AND haigo_mei_hyoji.no_han = 1                                  " +
                            "   AND haigo_mei_hyoji.flg_mishiyo = 0                             " +
                            "   AND haigo_mei_hyoji.flg_sakujyo = 0                             " +
                            "   LEFT JOIN ma_haigo_mei haigo_mei                                " +
                            "   ON seihin.cd_haigo = haigo_mei.cd_haigo                         " +
                            "   AND haigo_mei.no_han = 1                                        " +
                            "   AND haigo_mei.flg_mishiyo = 0                                   " +
                            "   AND haigo_mei.flg_sakujyo = 0                                   ";

                result = context_kojyo.Database.SqlQuery<Info_Hin>(query, new SqlParameter("@cd_hin", cd_hin)).FirstOrDefault();

                if (result == null)
                {
                    return result;
                }

                if (M_kirikae == m_kirikae_hyoji)
                {
                    if (result.cd_haigo_hyoji_check == "XXXXXXX" || result.cd_haigo_hyoji_check == "XXXXXXXXXXXX")
                    {
                        result.cd_haigo = null;
                    }
                    else
                    {
                        result.cd_haigo = result.cd_haigo_hyoji;
                    }
                }
                else
                {
                    if (result.cd_haigo_check == "XXXXXXX" || result.cd_haigo_check == "XXXXXXXXXXXX")
                    {
                        result.cd_haigo = null;
                    }
                }
                
                return result;
            }
            else
            {
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
                context.Configuration.ProxyCreationEnabled = false;

                decimal d_cd_hin = decimal.Parse(cd_hin);

                ma_seihin_seiho seihin_seiho = new ma_seihin_seiho();
                seihin_seiho = (from m in context.ma_seihin_seiho
                                where m.cd_hin == d_cd_hin
                                select m).FirstOrDefault();
                if (seihin_seiho != null)
                {
                    decimal? cd_haigo = seihin_seiho.cd_haigo;
                    if(cd_haigo != null){
                        ma_haigo_header haigo_header = new ma_haigo_header();
                        haigo_header = (from m in context.ma_haigo_header
                                        where m.cd_haigo == cd_haigo
                                        select m).FirstOrDefault();
                        if (haigo_header == null)
                        {
                            seihin_seiho.cd_haigo = null;
                        }
                    }
                }
                return seihin_seiho;
            }
        }


        #endregion

    }



    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    
    public class Info_Hin
    {
        public string cd_hin { get; set; }
        public string nm_seihin { get; set; }
        public string cd_haigo { get; set; }
        public string cd_haigo_hyoji { get; set; }
        public string nisugata_hyoji { get; set; }
        public string cd_haigo_check { get; set; }
        public string cd_haigo_hyoji_check { get; set; }
    }

    public class SeihinInfo
    {
        public int? no_yusen { get; set; }
        public string cd_hin { get; set; }
        public string nm_seihin { get; set; }
        public string nisugata_hyoji { get; set; }
    }

    #endregion
}
