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
using System.Data.SqlClient;
using Tos.Web.DataFP;
using System.Data;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _212_GenryoIchiranController : ApiController
    {
        #region "Controllerで公開するAPI"
        private readonly int sort_cd_hin = 1;
        public readonly bool FlgFlase = false;
        /// <summary>
        /// get nm_bunrui with cd_bunrui, cd_kaisha, cd_kojyo
        /// </summary>
        /// <param name=""> </param>
        /// <returns>nm_bunrui</returns>
        public object getBunrui([FromUri] get_bunrui param)
        {
            object results;
            if (param.Kbn_bumon == 1)
            {
                ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();

                context.Configuration.ProxyCreationEnabled = false;
                results = (from items in context.vw_ma_bunrui
                           where items.cd_kaisha == param.cd_kaisha
                              && items.cd_kojyo == param.cd_kojyo
                              && items.kbn_hin == param.kbn_hin
                              && items.cd_bunrui == param.cd_bunrui
                              && items.flg_mishiyo == false
                           select items).ToList();
            }
            else
            {
                FOODPROCSEntities context = new FOODPROCSEntities((int)param.cd_kaisha,(int)param.cd_kojyo);

                context.Configuration.ProxyCreationEnabled = false;
                string str_kbn_hin = param.kbn_hin.ToString();
                string str_cd_bunrui = (param.cd_bunrui < 10 ? "0" + param.cd_bunrui.ToString() : param.cd_bunrui.ToString());
                var query = "SELECT cd_bunrui, nm_bunrui"
                            + " FROM ma_bunrui"
                            + " WHERE kbn_hin = @kbn_hin"
                            + " AND flg_sakujyo = 0 "
                            + " AND flg_mishiyo = 0 "
                            + " AND cd_bunrui = @cd_bunrui"
                            + " ORDER BY cd_bunrui";
                var sqlparam = new object[] {
                    new SqlParameter("@kbn_hin",SqlDbType.VarChar,1){ Value = str_kbn_hin},
                    new SqlParameter("@cd_bunrui",SqlDbType.VarChar,2) { Value = str_cd_bunrui},
                    new SqlParameter("@FlgFlase",SqlDbType.Bit){ Value = FlgFlase}
                };
                results = context.Database.SqlQuery<ma_bunrui_fp>(query, sqlparam).ToList();

            }
            return results;
        }
        /// <summary>
        /// search list 
        /// </summary>
        /// <param name=""> </param>
        /// <returns>list</returns>
        public List<object> Get([FromUri] GenryoIchiran param)
        {
            List<object> results = new List<object>();
            string flg_query = "";
            if (param.check_right2 == false)
            {
                flg_query = " AND GENSHIZAI.flg_mishiyo = 0 ";
            }
            if (param.Kbn_bumon == Convert.ToInt16(Properties.Resources.kbn_bumon_lab))
            {
                param.zero = Convert.ToInt16(Properties.Resources.cd_jyotai_0);
                param.one = Convert.ToInt16(Properties.Resources.cd_jyotai_1);
                param.two = Convert.ToInt16(Properties.Resources.cd_jyotai_2);
                using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                {
                    results = context.sp_shohinkaihatsu_search_212(
                                                                     param.cd_kaisha
                                                                    , param.cd_bunrui
                                                                    , param.cd_hin
                                                                    , param.nm_hin
                                                                    , param.nm_bussitsu
                                                                    , param.no_kikaku1
                                                                    , param.no_kikaku2
                                                                    , param.no_kikaku3
                                                                    , param.nm_kikaku
                                                                    , param.nm_hanbai
                                                                    , param.nm_seizo
                                                                    , param.cd_kojyo
                                                                    , param.zero
                                                                    , param.one
                                                                    , param.two
                                                                    , param.kbn_hin
                                                                    , param.sort
                                                                    , param.top
                                                                    , param.skip
                                                                    , param.check_left
                                                                    , param.check_right1
                                                                    , param.check_right2
                                                                    , param.check_right3
                                                                    
                                                                   ).Cast<object>().ToList();
                }
            }
            else if (param.Kbn_bumon == Convert.ToInt16(Properties.Resources.kbn_bumon_factory))
            {
                var sqlparams = new object[] {
                        new SqlParameter("@cd_bunrui",SqlDbType.VarChar,2) { Value = (object)param.cd_bunrui_str ?? DBNull.Value},
                        new SqlParameter("@cd_hin",SqlDbType.VarChar,13){ Value = (object)param.cd_hin_str ?? DBNull.Value},
                        new SqlParameter("@nm_hin",SqlDbType.VarChar,60){ Value = (object)param.nm_hin ?? DBNull.Value},
                        new SqlParameter("@nm_bussitsu",SqlDbType.VarChar,74){ Value = (object)param.nm_bussitsu ?? DBNull.Value},
                        new SqlParameter("@no_kikaku1",SqlDbType.VarChar,17){ Value = (object)param.no_kikaku1 ?? DBNull.Value},
                        new SqlParameter("@no_kikaku2",SqlDbType.VarChar,17){ Value = (object)param.no_kikaku2 ?? DBNull.Value},
                        new SqlParameter("@no_kikaku3",SqlDbType.VarChar,17){ Value = (object)param.no_kikaku3 ?? DBNull.Value},
                        new SqlParameter("@nm_kikaku",SqlDbType.VarChar,80){ Value = (object)param.nm_kikaku ?? DBNull.Value},
                        new SqlParameter("@nm_hanbai",SqlDbType.VarChar,80){ Value = (object)param.nm_hanbai ?? DBNull.Value},
                        new SqlParameter("@nm_seizo",SqlDbType.VarChar,80){ Value = (object)param.nm_seizo ?? DBNull.Value},
                        new SqlParameter("@kbn_hin",SqlDbType.VarChar,1){ Value = (object)param.kbn_hin ?? DBNull.Value},
                         new SqlParameter("@sort",SqlDbType.Int){ Value = param.sort},
                        new SqlParameter("@top",SqlDbType.Int){ Value = param.top},
                        new SqlParameter("@skip",SqlDbType.Int){ Value = param.skip}
                      };
                using (FOODPROCSEntities context = new FOODPROCSEntities(param.cd_kaisha, (int)param.cd_kojyo))
                {
                    string query;
                   

                    if (param.check_left == false && param.check_right3 == false)
                    {
                        query = "DECLARE @start NUMERIC = @skip + 1 " +
                                   "       ,@end   NUMERIC = @top + @skip " +
                                   "       ,@defaultDATA INT = 1;" +
                                   "WITH CTE AS(" +
                                   "        SELECT                                                                                             " +
                                   "          GENSHIZAI.cd_hin                                                                              " +
                                   "         ,GENSHIZAI.nm_hin                                                                                        " +
                                   "         ,nm_hin_r                                                                                      " +
                                   "         ,nisugata_hyoji                                                                                " +
                                   "         ,cd_shukei                                                                                     " +
                                   "         ,BUNRUI.nm_bunrui                                                                              " +
                                   "         ,HINI.nm_hini                                                                                  " +
                                   "         ,GENSHIZAI.flg_mishiyo                                                                      " +
                                   "         ,GENSHIZAI.dt_toroku                                                                        " +
                                   "         ,no_kikaku                                                                                     " +
                                   "         ,nm_kikaku                                                                                     " +
                                   "         ,nm_bussitsu                                                                                   " +
                                   "         ,nm_seizo                                                                                      " +
                                   "         ,nm_hanbai                                                                                     " +
                                   "     FROM ma_genshizai GENSHIZAI                                                                        " +

                                   "     LEFT JOIN ma_bunrui BUNRUI                                                                         " +
                                   "     ON GENSHIZAI.kbn_hin  = BUNRUI.kbn_hin                                                         " +
                                   "     AND GENSHIZAI.cd_bunrui= BUNRUI.cd_bunrui                                                       " +
                                   "     AND BUNRUI.flg_mishiyo = 0                                                                         " +
                                   "     AND BUNRUI.flg_sakujyo = 0                                                                          " +

                                   "     LEFT JOIN ma_hini HINI                                                                                        " +
                                   "     ON GENSHIZAI.cd_hini = HINI.cd_hini                                                                    " +
                                   "     AND HINI.flg_sakujyo = 0                                                                            " +
                                   "     AND HINI.flg_mishiyo = 0                                                                               " +

                                   "     WHERE                                                                                              " +
                                   "         GENSHIZAI.flg_sakujyo = 0                                                            " +
                                   "         AND (@cd_bunrui IS NULL OR GENSHIZAI.cd_bunrui = @cd_bunrui)                                " +
                                   "         AND (@cd_hin IS NULL OR GENSHIZAI.cd_hin  = @cd_hin)                                         " +
                                   "         AND (@nm_hin IS NULL OR ((GENSHIZAI.nm_hin LIKE ('%!' + @nm_hin + '%') ESCAPE '!')        " +
                                   "                                  OR GENSHIZAI.cd_hin LIKE ('%!' + @nm_hin + '%') ESCAPE '!')) " +
                                   "         AND (@nm_bussitsu IS NULL OR (GENSHIZAI.nm_bussitsu LIKE ('%!'+@nm_bussitsu+'%') ESCAPE '!') )" +
                                   "         AND (@no_kikaku1 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,1,4) = @no_kikaku1)               " +
                                   "         AND (@no_kikaku2 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,6,6) = @no_kikaku2)              " +
                                   "         AND (@no_kikaku3 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,13,5) = @no_kikaku3)             " +
                                   "         AND (@nm_kikaku IS NULL OR (GENSHIZAI.nm_kikaku LIKE ('%!'+@nm_kikaku+'%') ESCAPE '!') )    " +
                                   "         AND (@nm_hanbai IS NULL OR (GENSHIZAI.nm_hanbai LIKE ('%!'+@nm_hanbai+'%') ESCAPE '!') )    " +
                                   "         AND (@nm_seizo IS NULL OR (GENSHIZAI.nm_seizo LIKE ('%!'+@nm_seizo+'%') ESCAPE '!' ) )      " +
                                   "         AND GENSHIZAI.kbn_hin = '1' " +
                                   "         AND GENSHIZAI.cd_hin <> '000000' " +
                                   "         AND GENSHIZAI.cd_hin <> '00000000000' ";
                        query += flg_query;
                        query += " ) ";
                        query += " SELECT ";
                        query += "       NextSearch.cd_hin ";
                        query += "      ,NextSearch.nm_hin ";
                        query += "      ,NextSearch.nm_hin_r ";
                        query += "      ,NextSearch.nisugata_hyoji ";
                        query += "      ,NextSearch.cd_shukei ";
                        query += "      ,NextSearch.nm_bunrui ";
                        query += "      ,NextSearch.nm_hini ";
                        query += "      ,NextSearch.flg_mishiyo ";
                        query += "      ,NextSearch.dt_toroku";
                        query += "      ,NextSearch.no_kikaku ";
                        query += "      ,NextSearch.nm_kikaku ";
                        query += "      ,NextSearch.nm_bussitsu ";
                        query += "      ,NextSearch.nm_seizo ";
                        query += "      ,NextSearch.nm_hanbai  ";
                        query += "      ,NextSearch.total_rows";
                        query += " FROM ";
                        query += "     (";
                        query += "          SELECT";
                        query += "              c.cd_hin";
                        query += "              ,c.nm_hin";
                        query += "              ,c.nm_hin_r";
                        query += "              ,c.nisugata_hyoji";
                        query += "              ,c.cd_shukei";
                        query += "              ,c.nm_bunrui";
                        query += "              ,c.nm_hini";
                        query += "              ,c.flg_mishiyo";
                        query += "              ,c.dt_toroku";
                        query += "              ,c.no_kikaku";
                        query += "              ,c.nm_kikaku";
                        query += "              ,c.nm_bussitsu";
                        query += "              ,c.nm_seizo";
                        query += "              ,c.nm_hanbai";
                        query += "              ,COUNT(@defaultDATA) OVER() total_rows";
                        query += "              ,ROW_NUMBER() OVER(ORDER BY  ";
                    if (param.sort == sort_cd_hin)
                    {
                        query += "                                  c.cd_hin ASC,c.nm_hin ASC ";
                    }
                    else
                    {
                        query += "                                  c.nm_hin ASC,c.cd_hin ASC ";
                    }
                        query += "                               ) AS ROWNUM";
                        query += "          FROM CTE c";
                        query += "    ) NextSearch";
                        query += " WHERE ROWNUM BETWEEN @start AND @end";
                        results = context.Database.SqlQuery<ResultGenryoIchiranFOOD>(query, sqlparams).Cast<object>().ToList();
                    }
                    else if (param.check_left == true && param.check_right3 == false)
                    {
                        query = "DECLARE @start NUMERIC = @skip + 1 " +
                               "       ,@end   NUMERIC = @top + @skip " +
                               "       ,@defaultDATA INT = 1; " +
                               "WITH CTE AS(" +
                               "        SELECT                                                                                             " +
                               "          GENSHIZAI.cd_hin                                                                              " +
                               "         ,GENSHIZAI.nm_hin                                                                                        " +
                               "         ,nm_hin_r                                                                                      " +
                               "         ,nisugata_hyoji                                                                                " +
                               "         ,cd_shukei                                                                                     " +
                               "         ,BUNRUI.nm_bunrui                                                                              " +
                               "         ,HINI.nm_hini                                                                                  " +
                               "         ,GENSHIZAI.flg_mishiyo                                                                      " +
                               "         ,GENSHIZAI.dt_toroku                                                                        " +
                               "         ,no_kikaku                                                                                     " +
                               "         ,nm_kikaku                                                                                     " +
                               "         ,nm_bussitsu                                                                                   " +
                               "         ,nm_seizo                                                                                      " +
                               "         ,nm_hanbai                                                                                     " +
                               "     FROM ma_genshizai GENSHIZAI                                                                        " +

                               "     LEFT JOIN ma_bunrui BUNRUI                                                                         " +
                               "     ON GENSHIZAI.kbn_hin  = BUNRUI.kbn_hin                                                         " +
                               "     AND GENSHIZAI.cd_bunrui= BUNRUI.cd_bunrui                                                       " +
                               "     AND BUNRUI.flg_mishiyo = 0                                                                         " +
                               "     AND BUNRUI.flg_sakujyo = 0                                                                          " +

                               "     LEFT JOIN ma_hini HINI                                                                                        " +
                               "     ON GENSHIZAI.cd_hini = HINI.cd_hini                                                                    " +
                               "     AND HINI.flg_sakujyo = 0                                                                            " +
                               "     AND HINI.flg_mishiyo = 0                                                                               " +

                               "     WHERE                                                                                              " +
                               "         GENSHIZAI.flg_sakujyo = 0                                                            " +
                               "         AND (@cd_bunrui IS NULL OR GENSHIZAI.cd_bunrui = @cd_bunrui)                                " +
                               "         AND (@nm_hin IS NULL OR ((GENSHIZAI.nm_hin LIKE ('%!' + @nm_hin + '%') ESCAPE '!')        " +
                                   "                                  OR GENSHIZAI.cd_hin LIKE ('%!' + @nm_hin + '%') ESCAPE '!')) " +
                               "         AND (@nm_bussitsu IS NULL OR (GENSHIZAI.nm_bussitsu LIKE ('%!'+@nm_bussitsu+'%') ESCAPE '!') )" +
                               "         AND (@no_kikaku1 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,1,4) = @no_kikaku1)               " +
                               "         AND (@no_kikaku2 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,6,6) = @no_kikaku2)              " +
                               "         AND (@no_kikaku3 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,13,5) = @no_kikaku3)             " +
                               "         AND (@nm_kikaku IS NULL OR (GENSHIZAI.nm_kikaku LIKE ('%!'+@nm_kikaku+'%') ESCAPE '!') )    " +
                               "         AND (@nm_hanbai IS NULL OR (GENSHIZAI.nm_hanbai LIKE ('%!'+@nm_hanbai+'%') ESCAPE '!') )    " +
                               "         AND (@nm_seizo IS NULL OR (GENSHIZAI.nm_seizo LIKE ('%!'+@nm_seizo+'%') ESCAPE '!' ) )      " +
                               "         AND GENSHIZAI.kbn_hin = '1' " +
                               "         AND GENSHIZAI.cd_hin <> '000000' " +
                               "         AND GENSHIZAI.cd_hin <> '00000000000' " +
                               "         AND ( GENSHIZAI.cd_doto_hin = ( SELECT " +
                               "                                   cd_doto_hin" +
                               "                                 FROM ma_genshizai " +
                               "                                  WHERE cd_hin = @cd_hin ) " +
                               "                OR GENSHIZAI.cd_doto_hin = @cd_hin " +
                               "                OR GENSHIZAI.cd_hin = ( SELECT " +
                               "                                            cd_doto_hin " +
                               "                                          FROM ma_genshizai" +
                               "                                          WHERE cd_hin = @cd_hin)) " +
                               "        AND (GENSHIZAI.cd_doto_kaisha = (SELECT " +
                               "                                            cd_doto_kaisha " +
                               "                                         FROM ma_genshizai" +
                               "                                         WHERE cd_hin = @cd_hin) " +
                               "                OR GENSHIZAI.cd_hin = @cd_hin)";
                        query += flg_query;
                        query += " ) ";
                        query += " SELECT ";
                        query += "       NextSearch.cd_hin ";
                        query += "      ,NextSearch.nm_hin ";
                        query += "      ,NextSearch.nm_hin_r ";
                        query += "      ,NextSearch.nisugata_hyoji ";
                        query += "      ,NextSearch.cd_shukei ";
                        query += "      ,NextSearch.nm_bunrui ";
                        query += "      ,NextSearch.nm_hini ";
                        query += "      ,NextSearch.flg_mishiyo ";
                        query += "      ,NextSearch.dt_toroku";
                        query += "      ,NextSearch.no_kikaku ";
                        query += "      ,NextSearch.nm_kikaku ";
                        query += "      ,NextSearch.nm_bussitsu ";
                        query += "      ,NextSearch.nm_seizo ";
                        query += "      ,NextSearch.nm_hanbai ";
                        query += "      ,NextSearch.total_rows";
                        query += " FROM ";
                        query += "     (";
                        query += "          SELECT";
                        query += "              c.cd_hin";
                        query += "              ,c.nm_hin";
                        query += "              ,c.nm_hin_r";
                        query += "              ,c.nisugata_hyoji";
                        query += "              ,c.cd_shukei";
                        query += "              ,c.nm_bunrui";
                        query += "              ,c.nm_hini";
                        query += "              ,c.flg_mishiyo";
                        query += "              ,c.dt_toroku";
                        query += "              ,c.no_kikaku";
                        query += "              ,c.nm_kikaku";
                        query += "              ,c.nm_bussitsu";
                        query += "              ,c.nm_seizo";
                        query += "              ,c.nm_hanbai";
                        query += "              ,COUNT(@defaultDATA) OVER() total_rows";
                        query += "              ,ROW_NUMBER() OVER(ORDER BY  ";
                    if (param.sort == sort_cd_hin)
                    {
                        query += "                                  c.cd_hin ASC,c.nm_hin ASC ";
                    }
                    else
                    {
                        query += "                                  c.nm_hin ASC,c.cd_hin ASC ";
                    }
                        query += "                               ) AS ROWNUM";
                        query += "          FROM CTE c";
                        query += "    ) NextSearch";
                        query += " WHERE ROWNUM BETWEEN @start AND @end";
                        results = context.Database.SqlQuery<ResultGenryoIchiranFOOD>(query, sqlparams).Cast<object>().ToList();
                    }
                    else if (param.check_left == false && param.check_right3 == true)
                    {
                        query = "DECLARE @start NUMERIC = @skip + 1 " +
                               "       ,@end   NUMERIC = @top + @skip " +
                               "       ,@defaultDATA INT = 1; " +
                               " WITH CTE AS(" +
                               "        SELECT                                                                                             " +
                               "          GENSHIZAI.cd_hin                                                                              " +
                               "         ,GENSHIZAI.nm_hin                                                                                        " +
                               "         ,nm_hin_r                                                                                      " +
                               "         ,nisugata_hyoji                                                                                " +
                               "         ,cd_shukei                                                                                     " +
                               "         ,BUNRUI.nm_bunrui                                                                              " +
                               "         ,HINI.nm_hini                                                                                  " +
                               "         ,GENSHIZAI.flg_mishiyo                                                                      " +
                               "         ,GENSHIZAI.dt_toroku                                                                        " +
                               "         ,no_kikaku                                                                                     " +
                               "         ,nm_kikaku                                                                                     " +
                               "         ,nm_bussitsu                                                                                   " +
                               "         ,nm_seizo                                                                                      " +
                               "         ,nm_hanbai                                                                                     " +
                               "     FROM ma_genshizai GENSHIZAI                                                                        " +

                               "     LEFT JOIN ma_bunrui BUNRUI                                                                         " +
                               "     ON GENSHIZAI.kbn_hin  = BUNRUI.kbn_hin                                                         " +
                               "     AND GENSHIZAI.cd_bunrui= BUNRUI.cd_bunrui                                                       " +
                               "     AND BUNRUI.flg_mishiyo = 0                                                                         " +
                               "     AND BUNRUI.flg_sakujyo = 0                                                                          " +

                               "     LEFT JOIN ma_hini HINI                                                                                        " +
                               "     ON GENSHIZAI.cd_hini = HINI.cd_hini                                                                    " +
                               "     AND HINI.flg_sakujyo = 0                                                                            " +
                               "     AND HINI.flg_mishiyo = 0                                                                               " +
                               "     INNER JOIN SS_vw_genryo_tokushu TOKUSHU                                                                        " +
                               "     ON GENSHIZAI.cd_hin = TOKUSHU.cd_hin                                                                   " +

                               "     WHERE                                                                                              " +
                               "         GENSHIZAI.flg_sakujyo = 0                                                            " +
                               "         AND (@cd_bunrui IS NULL OR GENSHIZAI.cd_bunrui = @cd_bunrui)                                " +
                               "         AND (@cd_hin IS NULL OR GENSHIZAI.cd_hin = @cd_hin)                                         " +
                               "         AND (@nm_hin IS NULL OR ((GENSHIZAI.nm_hin LIKE ('%!' + @nm_hin + '%') ESCAPE '!')        " +
                               "                                  OR GENSHIZAI.cd_hin LIKE ('%!' + @nm_hin + '%') ESCAPE '!')) " +
                               "         AND (@nm_bussitsu IS NULL OR (GENSHIZAI.nm_bussitsu LIKE ('%!'+@nm_bussitsu+'%') ESCAPE '!') )" +
                               "         AND (@no_kikaku1 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,1,4) = @no_kikaku1)               " +
                               "         AND (@no_kikaku2 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,6,6) = @no_kikaku2)              " +
                               "         AND (@no_kikaku3 IS NULL OR SUBSTRING(GENSHIZAI.no_kikaku,13,5) = @no_kikaku3)             " +
                               "         AND (@nm_kikaku IS NULL OR (GENSHIZAI.nm_kikaku LIKE ('%!'+@nm_kikaku+'%') ESCAPE '!') )    " +
                               "         AND (@nm_hanbai IS NULL OR (GENSHIZAI.nm_hanbai LIKE ('%!'+@nm_hanbai+'%') ESCAPE '!') )    " +
                               "         AND (@nm_seizo IS NULL OR (GENSHIZAI.nm_seizo LIKE ('%!'+@nm_seizo+'%') ESCAPE '!' ) )      " +
                               "         AND GENSHIZAI.kbn_hin = '1' " +
                               "         AND GENSHIZAI.cd_hin <> '000000' " +
                               "         AND GENSHIZAI.cd_hin <> '00000000000' ";
                                

                        query += flg_query;
                        query += " ) ";
                        query += " SELECT ";
                        query += "       NextSearch.cd_hin ";
                        query += "      ,NextSearch.nm_hin ";
                        query += "      ,NextSearch.nm_hin_r ";
                        query += "      ,NextSearch.nisugata_hyoji ";
                        query += "      ,NextSearch.cd_shukei ";
                        query += "      ,NextSearch.nm_bunrui ";
                        query += "      ,NextSearch.nm_hini ";
                        query += "      ,NextSearch.flg_mishiyo ";
                        query += "      ,NextSearch.dt_toroku ";
                        query += "      ,NextSearch.no_kikaku ";
                        query += "      ,NextSearch.nm_kikaku ";
                        query += "      ,NextSearch.nm_bussitsu ";
                        query += "      ,NextSearch.nm_seizo ";
                        query += "      ,NextSearch.nm_hanbai ";
                        query += "      ,NextSearch.total_rows";
                        query += " FROM ";
                        query += "     (";
                        query += "          SELECT";
                        query += "              c.cd_hin";
                        query += "              ,c.nm_hin";
                        query += "              ,c.nm_hin_r";
                        query += "              ,c.nisugata_hyoji";
                        query += "              ,c.cd_shukei";
                        query += "              ,c.nm_bunrui";
                        query += "              ,c.nm_hini";
                        query += "              ,c.flg_mishiyo";
                        query += "              ,c.dt_toroku";
                        query += "              ,c.no_kikaku";
                        query += "              ,c.nm_kikaku";
                        query += "              ,c.nm_bussitsu";
                        query += "              ,c.nm_seizo";
                        query += "              ,c.nm_hanbai";
                        query += "              ,COUNT(@defaultDATA) OVER() total_rows";
                        query += "              ,ROW_NUMBER() OVER(ORDER BY  ";
                    if (param.sort == sort_cd_hin)
                    {
                        query += "                                  c.cd_hin ASC,c.nm_hin ASC ";
                    }
                    else {
                        query += "                                  c.nm_hin ASC,c.cd_hin ASC ";   
                    }
                        query += "                               ) AS ROWNUM";
                        query += "          FROM CTE c";
                        query += "    ) NextSearch";
                        query += " WHERE ROWNUM BETWEEN @start AND @end";
                        results = context.Database.SqlQuery<ResultGenryoIchiranFOOD>(query, sqlparams).Cast<object>().ToList();
                    }
                }
            }
            return results;
        }
        #endregion
    }

    public class GenryoIchiran
    {
        public short? cd_bunrui { get; set; }
        public int? cd_hin { get; set; }
        public string cd_hin_str { get; set; }
        public string cd_bunrui_str { get; set; }
        public short cd_kaisha { get; set; }
        public short? cd_kojyo { get; set; }
        public bool check_left { get; set; }
        public bool check_right1 { get; set; }
        public bool check_right2 { get; set; }
        public bool check_right3 { get; set; }
        public int Kbn_bumon { get; set; }
        public byte? kbn_hin { get; set; }
        public string nm_bussitsu { get; set; }
        public string nm_hanbai { get; set; }
        public string nm_hin { get; set; }
        public string nm_kikaku { get; set; }
        public string nm_seizo { get; set; }
        public string no_kikaku1 { get; set; }
        public string no_kikaku2 { get; set; }
        public string no_kikaku3 { get; set; }
        public int sort { get; set; }
        public int top { get; set; }
        public int skip { get; set; }
        public short zero { get; set; }
        public short one { get; set; }
        public short two { get; set; }
    }
    public class ResultGenryoIchiran
    {
        public short cd_kojyo { get; set; }
        public decimal cd_hin { get; set; }
        public string nm_hin { get; set; }
        public string nm_hin_r { get; set; }
        public string nisugata_hyoji { get; set; }
        public string cd_shukei { get; set; }
        public string nm_hini { get; set; }
        public Nullable<bool> flg_mishiyo { get; set; }
        public Nullable<System.DateTime> dt_toroku { get; set; }
        public Nullable<byte> kbn_haishi { get; set; }
        public string no_kikaku { get; set; }
        public string nm_kikaku { get; set; }
        public string nm_bussitsu { get; set; }
        public string nm_seizo { get; set; }
        public string nm_hanbai { get; set; }
        public string nm_bunrui { get; set; }
        public string nm_busho { get; set; }
        public int total_rows { get; set; }
    }
    public class ResultGenryoIchiranFOOD
    {
        public string cd_hin { get; set; }
        public string nm_hin { get; set; }
        public string nm_hin_r { get; set; }
        public string nisugata_hyoji { get; set; }
        public string cd_shukei { get; set; }
        public string nm_hini { get; set; }
        public Nullable<bool> flg_mishiyo { get; set; }
        public Nullable<System.DateTime> dt_toroku { get; set; }
        public Nullable<byte> kbn_haishi { get; set; }
        public string no_kikaku { get; set; }
        public string nm_kikaku { get; set; }
        public string nm_bussitsu { get; set; }
        public string nm_seizo { get; set; }
        public string nm_hanbai { get; set; }
        public string nm_bunrui { get; set; }
        public string nm_kojyo { get; set; }
        public int total_rows { get; set; }
    }

    public class get_ma_kbn_hin
    {
        public Byte kbn_hin { get; set; }
        public string nm_kbn_hin { get; set; }
    }
    public class get_bunrui
    {
        public Nullable<int> Kbn_bumon { get; set; }
        public Nullable<int> cd_kaisha {get;set;}
        public Nullable<int> cd_kojyo {get;set;}
        public int kbn_hin {get;set;}
        public Nullable<int> cd_bunrui {get;set;}
    }
}
