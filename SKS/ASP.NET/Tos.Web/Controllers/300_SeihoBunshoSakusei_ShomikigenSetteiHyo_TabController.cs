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

    public class _300_SeihoBunshoSakusei_ShomikigenSetteiHyo_TabController : ApiController
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
        /// <returns>TAB 8 [賞味期間設定表]</returns>
        public ShomikigenSetteiHyoResponse Get([FromUri]ShomikigenSetteiHyoSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                ShomikigenSetteiHyoResponse result = new ShomikigenSetteiHyoResponse();
                context.Configuration.ProxyCreationEnabled = false;

                UserTorokuData userData = null;
                //if (Conditions.mode == NEW || Conditions.mode == NEW_COPY)
                {
                    ma_haigo_header haigoData = context.ma_haigo_header.Where(x => x.no_seiho == Conditions.no_seiho && x.kbn_hin == Conditions.kbn_haigo).OrderBy(x => x.dt_toroku).FirstOrDefault();
                    if (haigoData != null && haigoData.cd_toroku != null)
                    {
                        decimal id_user = decimal.Parse(haigoData.cd_toroku);
                        int cd_kaisha = haigoData.cd_toroku_kaisha ?? 0;
                        userData = new UserTorokuData();
                        var UserInfo = context.ma_user_togo.Where(x => x.cd_kaisha == cd_kaisha && x.id_user == id_user).FirstOrDefault();
                        if (UserInfo != null)
                        {
                            userData.nm_user = UserInfo.nm_user;
                            userData.nm_bunsho = context.ma_busho.Where(x => x.cd_kaisha == cd_kaisha && x.cd_busho == UserInfo.cd_busho).Select(x => x.nm_busho).FirstOrDefault(); 
                        }
                    }
                }
                // Switching search mode
                switch (Conditions.mode)
                {
                    case NEW:
                        result.currentData = createData(Conditions, userData);
                        break;
                    case NEW_COPY:
                        result.currentData = createData(Conditions, userData);
                        result.copyData = context.ma_seiho_bunsho_shomikigen.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        break;
                    case EDIT:
                    case VIEW:
                        result.currentData = context.ma_seiho_bunsho_shomikigen.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        break;
                    case EDIT_COPY:
                        result.currentData = context.ma_seiho_bunsho_shomikigen.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        result.copyData = context.ma_seiho_bunsho_shomikigen.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        break;
                }
                if (result.currentData != null && !Conditions.isApplied) 
                {
                    result.currentData.nm_kaihatsubusho = userData == null ? "" : userData.nm_bunsho;
                    result.currentData.nm_kaihatsutanto = userData == null ? "" : userData.nm_user;                
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
        public ma_seiho_bunsho_shomikigen getCopyData([FromUri] ShomikigenSetteiHyoSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                return context.ma_seiho_bunsho_shomikigen.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
            }
        }

        #endregion

        /// <summary>
        /// Create new data in NEW mode
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        private ma_seiho_bunsho_shomikigen createData(ShomikigenSetteiHyoSearchRequest Conditions, UserTorokuData userData)
        {
            return new ma_seiho_bunsho_shomikigen()
            {
                no_seiho = Conditions.no_seiho,
                no_anzen_keisu = 0,
                nm_kaihatsubusho = userData == null ? "" : userData.nm_bunsho,
                nm_kaihatsutanto = userData == null ? "" : userData.nm_user
            };
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="data"></param>
        /// <param name="copyData"></param>
        //private void mergeCopyData(ma_seiho_bunsho_shomikigen data, ma_seiho_bunsho_shomikigen copyData)
        //{
        //    if (data != null) 
        //    {
        //        bool notExistCopy = (copyData == null);
        //        data.nm_hozon_shiken_kankaku           = notExistCopy ? null : copyData.nm_hozon_shiken_kankaku;
        //        data.nm_hozon_shiken_kikan             = notExistCopy ? null : copyData.nm_hozon_shiken_kikan;
        //        data.biko                              = notExistCopy ? null : copyData.biko;
        //        data.nm_shomikigen_biko                = notExistCopy ? null : copyData.nm_shomikigen_biko;
        //        data.nm_rikagaku_shiken_komoku         = notExistCopy ? null : copyData.nm_rikagaku_shiken_komoku;
        //        data.nm_rikagaku_shiken_hinshitsu      = notExistCopy ? null : copyData.nm_rikagaku_shiken_hinshitsu;
        //        data.nm_rikagaku_shiken_kikan          = notExistCopy ? null : copyData.nm_rikagaku_shiken_kikan;
        //        data.nm_rikagaku_shiken_hokoku         = notExistCopy ? null : copyData.nm_rikagaku_shiken_hokoku;
        //        data.nm_biseibutsu_shiken_komoku       = notExistCopy ? null : copyData.nm_biseibutsu_shiken_komoku;
        //        data.nm_biseibutsu_shiken_hinshitsu    = notExistCopy ? null : copyData.nm_biseibutsu_shiken_hinshitsu;
        //        data.nm_biseibutsu_shiken_kikan        = notExistCopy ? null : copyData.nm_biseibutsu_shiken_kikan;
        //        data.nm_biseibutsu_shiken_hokoku       = notExistCopy ? null : copyData.nm_biseibutsu_shiken_hokoku;
        //        data.nm_kanno_hyoka_komoku             = notExistCopy ? null : copyData.nm_kanno_hyoka_komoku;
        //        data.nm_kanno_hyoka_hinshitsu          = notExistCopy ? null : copyData.nm_kanno_hyoka_hinshitsu;
        //        data.nm_kanno_hyoka_kikan              = notExistCopy ? null : copyData.nm_kanno_hyoka_kikan;
        //        data.nm_kanno_hyoka_hokoku             = notExistCopy ? null : copyData.nm_kanno_hyoka_hokoku;
        //        data.no_anzen_keisu                    = notExistCopy ? null : copyData.no_anzen_keisu;
        //        data.nm_kaihatsubusho                  = notExistCopy ? null : copyData.nm_kaihatsubusho;
        //        data.nm_kaihatsutanto                  = notExistCopy ? null : copyData.nm_kaihatsutanto;
        //        data.nm_kanno_hyoka_kikan              = notExistCopy ? null : copyData.nm_kanno_hyoka_kikan;
        //    }
        //}
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// Search request param
    /// </summary>
    public class ShomikigenSetteiHyoSearchRequest
    {
        public string no_seiho { get; set; }
        public string no_seiho_copy { get; set; }
        public byte kbn_haigo { get; set; }
        public string mode { get; set; }
        public bool isApplied { get; set; }
    }

    /// <summary>
    /// Toroku user data
    /// </summary>
    public class UserTorokuData
    {
        public string nm_bunsho { get; set; }
        public string nm_user { get; set; }
    }

    /// <summary>
    /// Search result data
    /// </summary>
    public class ShomikigenSetteiHyoResponse
    {
        public ma_seiho_bunsho_shomikigen currentData { get; set; }
        public ma_seiho_bunsho_shomikigen copyData { get; set; }
    }
    #endregion
}
