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
using System.Data.Objects.SqlClient;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _707_LineKenSaKu_DiaLogController : ApiController
    {
        /// </summary>
        /// <param name="Conditions"> OData クエリ</param>
        /// <returns>target情報</returns>
        /// 
        [HttpGet]
        public HttpResponseMessage Get([FromUri]paramSearchDiaLog Conditions)
        {
            List<resultDialog> result = new List<resultDialog>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                //Check su_linecode_standard
                var check_length = (from p in context.ma_kojyo
                                    where p.cd_kaisha == Conditions.cd_kaisha && p.cd_kojyo == Conditions.cd_kojyo
                                    select new
                                    {
                                        p.su_linecode_standard
                                    }).FirstOrDefault();
                if (Conditions.kbn_bumon == 1)// developer
                {
                    result = (from p in context.ma_line_togo
                              where p.cd_kaisha == Conditions.cd_kaisha && p.cd_seizokojo == Conditions.cd_kojyo
                              orderby p.cd_line
                              select new resultDialog
                              {
                                  cd_line = SqlFunctions.StringConvert((double)p.cd_line).Trim(),
                                  nm_line = p.nm_line
                              }).ToList();
                }
                using (FOODPROCSEntities context2 = new FOODPROCSEntities(Conditions.cd_kaisha, Conditions.cd_kojyo))// cd_kaisha=1; cd_kojyo=6
                {

                    if (Conditions.kbn_bumon == 2)// factory
                    {
                        result = (from p in context2.ma_line
                                  where p.flg_sakujyo == false //=0 
                                  orderby p.cd_line
                                  select new resultDialog
                                   {
                                       cd_line = p.cd_line,
                                       nm_line = p.nm_line
                                   }).ToList();
                    }

                }
                foreach (var x in result)
                {
                    x.cd_line = x.cd_line.PadLeft(check_length.su_linecode_standard, '0');
                }
            }

            return Request.CreateResponse(HttpStatusCode.OK, result);
        }
        public class resultDialog
        {
            public string cd_line { get; set; }
            public string nm_line { get; set; }
        }
        public class paramSearchDiaLog
        {
            public int cd_kaisha { get; set; }
            public int cd_kojyo { get; set; }
            public int kbn_bumon { get; set; }
        }
    }
}
