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
    public class MarkKensaku_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="cd_kaisha"> cd_kaisha </param>
        /// <param name="cd_kojyo"> cd_kojyo</param>
        /// <param name="no_gamen"> no_gamen</param>
        /// <returns> result ma_mark </returns>
        public object Get(int cd_kaisha, int cd_kojyo, int no_gamen)
        {
            // TODO: target 情報を管理しているDbContextと target の型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                if (no_gamen == int.Parse(Properties.Resources.id_gamen_203))
                {
                    context.Configuration.ProxyCreationEnabled = false;
                    //dev
                    var result = context.vw_ma_mark
                                        .Where(x => x.cd_kaisha == cd_kaisha && x.cd_kojyo == cd_kojyo)
                                        .OrderBy(x => x.cd_mark)
                                        .ToList();
                    if (result == null)
                    {
                        throw new HttpException((int)HttpStatusCode.NotFound, Properties.Resources.NoDataExsists);
                    }
                    return result;
                }
                else
                {
                    //kojyo

                    FOODPROCSEntities context_kojyo = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
                    context_kojyo.Configuration.ProxyCreationEnabled = false;

                    var result = context_kojyo.vw_ma_mark
                                              .OrderBy(x => x.cd_mark)
                                              .ToList();
                    if (result == null)
                    {
                        throw new HttpException((int)HttpStatusCode.NotFound, Properties.Resources.NoDataExsists);
                    }
                    return result;
                    
                }
            }

        }
        #endregion
    }
}