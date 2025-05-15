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
using System.Data.Objects.SqlClient;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _709_BunruiKensaku_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// Get kbn_hin from ShohinKaihatsu for Combobox
        /// </summary>
        /// <param name="kbn_hin"></param>
        /// <returns></returns>
        public ma_kbn_hin Get_ma_kbn_hin_lab(byte kbn_hin)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                ma_kbn_hin result = new ma_kbn_hin();

                switch (kbn_hin)
                {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        result = context.ma_kbn_hin.Where(x => x.kbn_hin == kbn_hin).FirstOrDefault();
                        break;
                    case 6:
                        result.kbn_hin = kbn_hin;
                        result.nm_kbn_hin = Properties.Resources.JikaGenyyo;
                        break;
                }
                return result;
            }
        }


        /// <summary>
        /// Get kbn_hin from FOODPROCS for Combobox
        /// </summary>
        /// <returns>Get list kbn_hin</returns>
        public object Get_ma_kbn_hin_factory(byte kbn_hin, int? cd_kaisha, int? cd_kojyo)
        {
           
            int kaisha = cd_kaisha ?? 0;
            int kojyo = cd_kojyo ?? 0;
            if (kaisha == 0 || kojyo == 0)
            {
                return null;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(kaisha, kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;

                switch (kbn_hin)
                {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        {
                            var query = "SELECT kbn_hin, nm_kbn_hin"
                                       + " FROM ma_kbn_hin"
                                       + " WHERE kbn_hin =  @kbn_hin";

                            object results = context.Database.SqlQuery<ma_kbn_hin_fp>(query, new SqlParameter("@kbn_hin", kbn_hin)).FirstOrDefault();
                            return results;
                            //break;
                        }
                    case 6:
                        {
                            //var query = "SELECT kbn_hin, nm_kbn_hin"
                            //           + " FROM ma_kbn_hin";
                            //results = context.Database.SqlQuery<ma_kbn_hin_fp>(query);
                            var results = new ma_kbn_hin_fp();
                            results.kbn_hin = kbn_hin.ToString();
                            results.nm_kbn_hin = Properties.Resources.JikaGenyyo;
                            return results;
                            //break;
                        }
                }
            }

            return null;
        }

        /// <summary>
        /// Get ma_bunrui from ShohinKaihatsu for Search
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public List<vw_ma_bunrui> Get_Bunrui_Lab([FromUri]BunruiKensakuhRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                List<vw_ma_bunrui> result = new List<vw_ma_bunrui>();

                result = context.vw_ma_bunrui.Where(x => x.cd_kaisha == Conditions.cd_kaisha
                                                      && x.cd_kojyo == Conditions.cd_kojyo
                                                      && x.kbn_hin == Conditions.kbn_hin
                                                      && x.flg_mishiyo == false
                                                      && (Conditions.bunrui == null
                                                         || SqlFunctions.StringConvert((decimal)x.cd_bunrui).Contains(Conditions.bunrui)
                                                         || x.nm_bunrui.Contains(Conditions.bunrui))).ToList();
                return result;
            }
        }

        /// <summary>
        /// Get ma_bunrui from FOODPROCS for Search
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public object Get_Bunrui_factory([FromUri]BunruiKensakuhRequest Conditions)
        {
            object results;
            int cd_kaisha = Conditions.cd_kaisha ?? 0;
            int cd_kojyo = Conditions.cd_kojyo ?? 0;
            if (cd_kaisha == 0 || cd_kojyo == 0)
            {
                return null;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;


                var query = "SELECT cd_bunrui, nm_bunrui"
                            + " FROM ma_bunrui"
                            + " WHERE kbn_hin =  @kbn_hin"
                            + " AND flg_sakujyo = 0"
                            + " AND flg_mishiyo = 0"
                            + " AND (@bunrui IS NULL OR cd_bunrui like ('%'+ @bunrui +'%') OR nm_bunrui like ('%'+ @bunrui +'%')) "
                            + " ORDER BY cd_bunrui";

                results = context.Database.SqlQuery<ma_bunrui_fp>(query, new SqlParameter("@kbn_hin", Conditions.kbn_hin)
                                                                       , new SqlParameter("@bunrui", Conditions.bunrui ?? (object)DBNull.Value)).ToList();
          
                return results;
            }
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class BunruiKensakuResponse
    {
        public BunruiKensakuResponse()
        {
            this.Detail = new List<object/*TODO: target の型を指定します*/>();
        }

        public List<object/*TODO: target の型を指定します*/> Detail { get; set; }
    }

    public class BunruiKensakuhRequest
    {
        public string bunrui { get; set; }
        public byte kbn_hin { get; set; }
        //public int? mode { get; set; }
        public int? cd_kaisha { get; set; }
        public int? cd_kojyo { get; set; }
    }

    #endregion
}
