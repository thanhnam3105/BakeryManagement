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
using System.Data.Common;
using System.Data.Entity.Infrastructure;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    public class _711_ShisakuCopy_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"
        
        /// <summary>
        /// 製法文書作成方法の選択-検索処理
        /// </summary>
        /// <returns>Buttons display status</returns>
        public HttpResponseMessage Get([FromUri]ShisakuCopyRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities()) 
            {
                decimal nen = Convert.ToDecimal(DateTime.Now.ToString("yy"));
                List<String> result = new List<String>();

                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        var Items = context.sp_shohinkaihatsu_copy_711(
                                                                        Conditions.kotei_pattern,
                                                                        Conditions.cd_haigo,
                                                                        Conditions.cd_shain,
                                                                        nen,
                                                                        Conditions.status_densosumi,
                                                                        Conditions.status_kojokakunin,
                                                                        Conditions.kbnhin_haigo,
                                                                        Conditions.kbnhin_shikakari,
                                                                        Conditions.kbnhin_maeshoriGenryo).ToList();
                        foreach (var item in Items)
                        {
                            result.Add(item.cd_err);
                        }
                        if (result.Count > 0)
                        {
                            transaction.Rollback();
                        }
                        else
                        {
                            transaction.Commit();
                        }
                    }
                    catch (Exception ex)
                    {
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                    }
                }
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }
        }

        /// <summary>
        /// Get number haigo which not confirmed
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        [HttpGet]
        public HttpResponseMessage CheckHaigoStatus([FromUri]ShisakuCopyRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var errCount = context.sp_shohinkaihatsu_check_711(
                                    Conditions.cd_haigo,
                                    Conditions.status_densosumi,
                                    Conditions.status_kojokakunin,
                                    Conditions.kbnhin_haigo,
                                    Conditions.kbnhin_shikakari).FirstOrDefault();
                return Request.CreateResponse(HttpStatusCode.OK, errCount ?? 0);
            }
        }
        #endregion

    }



    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// 工程パターン選択 - Copy request
    /// </summary>
    public class ShisakuCopyRequest
    {
        public decimal? cd_haigo { get; set; }
        public string kotei_pattern { get; set; }
        public decimal? cd_shain { get; set; }
        public string status_densosumi { get; set; }
        public string status_kojokakunin { get; set; }
        public string kbnhin_maeshoriGenryo { get; set; }
        public string kbnhin_haigo { get; set; }
        public string kbnhin_shikakari { get; set; }
    }

    #endregion
}
