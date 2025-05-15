using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Http.OData.Query;
using Tos.Web.Data;
using Tos.Web.Controllers;
using System.Web.Http.OData;
using System.Data.Common;
using System.Data.Objects;

namespace Tos.Web.Controllers
{

    public class _300_SeihoBunshoSakusei_GenryoSetsubi_TabController : ApiController
    {
        #region "Const"
        private const string NEW = "new";
        private const string NEW_COPY = "new_copy";
        private const string EDIT = "edit";
        private const string EDIT_COPY = "edit_copy";
        private const string VIEW = "view";
        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// 検索処理
        /// </summary>
        /// <param name="Conditions">Request param</param>
        /// <returns>Tab3_原料・機械設備・製造方法・表示事項</returns>
        public GenryoSetsubiTabSearchResponse Get([FromUri] GenryoSetsubiTabSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                GenryoSetsubiTabSearchResponse result = new GenryoSetsubiTabSearchResponse();
                switch (Conditions.mode)
                { 
                    //新規
                    case NEW:
                        result.currentData = createData(Conditions);
                        break;
                    case NEW_COPY:
                        result.currentData = createData(Conditions);
                        result.copyData = context.ma_seiho_bunsho_genryo_setsubi.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        break;
                    //編集
                    case EDIT:
                    //Read-only
                    case VIEW:
                        result.currentData = context.ma_seiho_bunsho_genryo_setsubi.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        break;
                    //編集 - Copy
                    case EDIT_COPY:
                        result.currentData = context.ma_seiho_bunsho_genryo_setsubi.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        result.copyData = context.ma_seiho_bunsho_genryo_setsubi.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        break;

                }
                return result;
            }
        }

        /// <summary>
        /// Reload copy data
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        [HttpGet]
        public ma_seiho_bunsho_genryo_setsubi getCopyData([FromUri] GenryoSetsubiTabSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                return context.ma_seiho_bunsho_genryo_setsubi.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
            }
        }

        #endregion

        /// <summary>
        /// Create data in new mode
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        private ma_seiho_bunsho_genryo_setsubi createData(GenryoSetsubiTabSearchRequest Conditions)
        {
            return new ma_seiho_bunsho_genryo_setsubi()
            {
                no_seiho = Conditions.no_seiho
            };
        }
        
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// [原料・機械設備・製造方法・表示事項] TAB request param
    /// </summary>
    public class GenryoSetsubiTabSearchRequest
    {
        public string no_seiho { get; set; }
        public string no_seiho_copy { get; set; }
        public string mode { get; set; }
    }

    /// <summary>
    /// [原料・機械設備・製造方法・表示事項] TAB response data
    /// </summary>
    public class GenryoSetsubiTabSearchResponse
    {
        public ma_seiho_bunsho_genryo_setsubi currentData { get; set; }
        public ma_seiho_bunsho_genryo_setsubi copyData { get; set; }
    }

    #endregion
}
