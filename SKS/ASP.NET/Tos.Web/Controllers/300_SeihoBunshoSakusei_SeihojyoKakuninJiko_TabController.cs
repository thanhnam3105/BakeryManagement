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

    public class _300_SeihoBunshoSakusei_SeihojyoKakuninJiko_TabController : ApiController
    {

        #region "Controllerで公開するAPI"
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
        /// <param name="Conditions"></param>
        /// <returns>TAB7 [製法上の確認事項]</returns>
        public SeihojyoKakuninJikoResponse Get([FromUri]SeihojyoKakuninJikoSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                SeihojyoKakuninJikoResponse result = new SeihojyoKakuninJikoResponse();
                context.Configuration.ProxyCreationEnabled = false;
                // Switching search mode
                switch (Conditions.mode)
                {
                    case NEW:
                        result.currentData = createData(Conditions);
                        break;
                    case NEW_COPY:
                        result.currentData = createData(Conditions);
                        result.copyData = context.ma_seiho_bunsho_kakuninjiko.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        break;
                    case EDIT:
                    case VIEW:
                        result.currentData = context.ma_seiho_bunsho_kakuninjiko.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        break;
                    case EDIT_COPY:
                        result.currentData = context.ma_seiho_bunsho_kakuninjiko.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        result.copyData = context.ma_seiho_bunsho_kakuninjiko.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
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
        public ma_seiho_bunsho_kakuninjiko getCopyData([FromUri] SeihojyoKakuninJikoSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                return context.ma_seiho_bunsho_kakuninjiko.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
            }
        }

        #endregion

        /// <summary>
        /// Create new data in NEW mode
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        private ma_seiho_bunsho_kakuninjiko createData(SeihojyoKakuninJikoSearchRequest Conditions)
        {
            return new ma_seiho_bunsho_kakuninjiko()
            {
                no_seiho = Conditions.no_seiho
            };
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="templatedDetail"></param>
        /// <param name="headerData"></param>
        //private void mergeCopyData(ma_seiho_bunsho_kakuninjiko data, ma_seiho_bunsho_kakuninjiko copyData)
        //{
        //    if (data != null) 
        //    {
        //        bool notExistCopy = (copyData == null);
        //        data.nm_free_kanren_tokkyo          = notExistCopy ? null : copyData.nm_free_kanren_tokkyo;
        //        data.nm_free_tasha_kanren_tokkyo    = notExistCopy ? null : copyData.nm_free_tasha_kanren_tokkyo;
        //        data.nm_shokuhin_tenkabutsu         = notExistCopy ? null : copyData.nm_shokuhin_tenkabutsu;
        //        data.nm_free_genryo_shitei          = notExistCopy ? null : copyData.nm_free_genryo_shitei;
        //        data.nm_free_genryo_seigen          = notExistCopy ? null : copyData.nm_free_genryo_seigen;
        //        data.nm_eigyo_kyoka_gyoshu01        = notExistCopy ? null : copyData.nm_eigyo_kyoka_gyoshu01;
        //        data.nm_eigyo_kyoka_gyoshu02        = notExistCopy ? null : copyData.nm_eigyo_kyoka_gyoshu02;
        //        data.nm_eigyo_kyoka_gyoshu03        = notExistCopy ? null : copyData.nm_eigyo_kyoka_gyoshu03;
        //        data.nm_eigyo_kyoka_gyoshu04        = notExistCopy ? null : copyData.nm_eigyo_kyoka_gyoshu04;
        //        data.nm_eigyo_kyoka_gyoshu05        = notExistCopy ? null : copyData.nm_eigyo_kyoka_gyoshu05;
        //        data.nm_free_sonota                 = notExistCopy ? null : copyData.nm_free_sonota;
        //    }
        //}
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// Search request param
    /// </summary>
    public class SeihojyoKakuninJikoSearchRequest
    {
        public string no_seiho { get; set; }
        public string no_seiho_copy { get; set; }
        public string mode { get; set; }
    }

    /// <summary>
    /// Search result data
    /// </summary>
    public class SeihojyoKakuninJikoResponse
    {
        public ma_seiho_bunsho_kakuninjiko currentData { get; set; }
        public ma_seiho_bunsho_kakuninjiko copyData { get; set; }
    }
    #endregion
}
