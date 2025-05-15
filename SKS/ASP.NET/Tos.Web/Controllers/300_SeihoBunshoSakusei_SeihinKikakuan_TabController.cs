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

    public class _300_SeihoBunshoSakusei_SeihinKikakuan_TabController : ApiController
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
        /// <returns>製法文書_Tab6_製品規格案及び取扱基準, 製法文書_Tab6_製品特性値一覧</returns>
        public SeihiKikakuanTabSearchRespone Get([FromUri] SeihiKikakuanTabSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                SeihiKikakuanTabSearchRespone result = new SeihiKikakuanTabSearchRespone();
                result.detailData = new List<ma_seiho_bunsho_tokuseichi>();
                ma_seiho_bunsho_seihinkikaku headerData;
                ma_seiho_bunsho_seihinkikaku headerDataCopy;
                switch(Conditions.mode)
                {
                    // 編集・参照モードで該当データ取得処理
                    case EDIT:
                    case VIEW:
                        headerData = context.ma_seiho_bunsho_seihinkikaku.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        if (headerData != null)
                        {
                            result.headerData = new SeihiKikakuanTabSearchRespone.ma_seiho_bunsho_seihinkikaku_EXT();
                            DataCopier.ReFill(headerData, result.headerData);
                            result.headerData.ts = headerData.ts.ToArray();
                        }
                        result.detailData = context.ma_seiho_bunsho_tokuseichi.Where(x => x.no_seiho == Conditions.no_seiho).ToList();
                        break;
                    // 編集・参照モードで該当データ取得処理 - Copy mode
                    case EDIT_COPY:
                        headerData = context.ma_seiho_bunsho_seihinkikaku.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        headerDataCopy = context.ma_seiho_bunsho_seihinkikaku.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        if (headerData != null)
                        {
                            result.headerData = new SeihiKikakuanTabSearchRespone.ma_seiho_bunsho_seihinkikaku_EXT();
                            DataCopier.ReFill(headerData, result.headerData);
                            result.headerData.no_seiho = Conditions.no_seiho;
                            result.headerData.ts = headerData.ts.ToArray();
                            mergeCopyData(result.headerData, headerDataCopy);
                        }
                        result.detailData = context.ma_seiho_bunsho_tokuseichi.Where(x => x.no_seiho == Conditions.no_seiho_copy).ToList();
                        result.detailDataOld = context.ma_seiho_bunsho_tokuseichi.Where(x => x.no_seiho == Conditions.no_seiho).ToList();
                        break;
                    // 新規モードで初期表示のみ
                    case NEW:
                        // Create new data
                        result.headerData = createData(Conditions);
                        // Retrive shisaku data
                        tr_shisakuhin Shisaku = context.tr_shisakuhin.Where(x => x.cd_shain == Conditions.cd_shain && x.nen == Conditions.nen && x.no_oi == Conditions.no_oi).FirstOrDefault();
                        if (Shisaku != null)
                        {
                            result.headerData.su_naiyoryo = Shisaku.yoryo;
                            result.headerData.kbn_toriatsukai_ondo = Shisaku.cd_ondo;
                            result.headerData.kbn_shomikikan_tani = Shisaku.shomikikan_tani;
                            if (Shisaku.cd_tani == null)
                            {
                                result.headerData.cd_naiyoryo_tani = null;
                            }
                            else
                            {
                                result.headerData.cd_naiyoryo_tani = Byte.Parse(Shisaku.cd_tani);
                            }
                            if (Shisaku.shomikikan == null)
                            {
                                result.headerData.su_shomikikan_free_input = null;
                            }
                            else
                            {
                                result.headerData.su_shomikikan_free_input = short.Parse(Shisaku.shomikikan);
                            }
                        }
                        break;
                    // 新規モードで初期表示のみ - Copy data
                    case NEW_COPY:
                        result.headerData = createData(Conditions);
                        headerDataCopy = context.ma_seiho_bunsho_seihinkikaku.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        mergeCopyData(result.headerData, headerDataCopy);
                        result.detailData = context.ma_seiho_bunsho_tokuseichi.Where(x => x.no_seiho == Conditions.no_seiho_copy).ToList();
                        break;
                }
                if (result.headerData != null)
                {
                    //result.headerData.cd_naiyoryo_tani = Conditions.cd_naiyoryo_tani;
                    result.headerData.pt_kotei = Conditions.pt_kotei;
                    // Sync pt_kotei with TAB 4 in VIEW mode
                    if (Conditions.mode == VIEW)
                    {
                        ma_seiho_bunsho_haigo_chui TAB4 = context.ma_seiho_bunsho_haigo_chui.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        if (TAB4 != null)
                        {
                            result.headerData.pt_kotei = TAB4.pt_kotei;
                        }
                    }
                }
                // Get nm_naiyoryo_tani
                if (result.headerData != null) 
                {
                    string cd_naiyoryo_tani = (result.headerData.cd_naiyoryo_tani == null) ? null : String.Format("{0:D3}", result.headerData.cd_naiyoryo_tani);
                    ma_literal tani = context.ma_literal.Where(x => x.cd_category == Conditions.yoryo_tani
                                                                    && x.cd_literal == cd_naiyoryo_tani
                                                                 ).FirstOrDefault();
                    if (tani != null)
                    {
                        result.headerData.nm_naiyoryo_tani = tani.nm_literal;
                    }
                }
                return result;
            }
        }

        /// <summary>
        /// Get copy data
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public SeihiKikakuanTabCopyResponse getCopyData([FromUri] SeihiKikakuanTabSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                SeihiKikakuanTabCopyResponse result = new SeihiKikakuanTabCopyResponse();
                result.headerData = context.ma_seiho_bunsho_seihinkikaku.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                result.detailData = context.ma_seiho_bunsho_tokuseichi.Where(x => x.no_seiho == Conditions.no_seiho_copy).ToList();
                return result;
            }
        }

        /// <summary>
        /// Get name 配合強度 from literal
        /// </summary>
        /// <param name="cd_shain"></param>
        /// <param name="nen"></param>
        /// <param name="no_oi"></param>
        /// <param name="seq_shisaku"></param>
        /// <param name="cd_category"></param>
        /// <returns></returns>
        [HttpGet]
        public string getHaigoKyodo(decimal cd_shain, decimal nen, decimal no_oi, short seq_shisaku, string cd_category)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var haigoKyodo = context.tr_shisaku.Where(x => x.cd_shain == cd_shain 
                                                            && x.nen == nen 
                                                            && x.no_oi == no_oi 
                                                            && x.seq_shisaku == seq_shisaku)
                                                   .Select(x => x.haigo_kyodo).FirstOrDefault();
                if (haigoKyodo != null)
                {
                    string result = context.ma_literal.Where(x => x.cd_category == cd_category && x.cd_literal == haigoKyodo).Select(x => x.nm_literal).FirstOrDefault();
                    if (result != null) 
                    {
                        return result;
                    }
                }
                return String.Empty;
            }
        }

        #endregion

        /// <summary>
        /// Create new header data in NEW mode
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        private SeihiKikakuanTabSearchRespone.ma_seiho_bunsho_seihinkikaku_EXT createData(SeihiKikakuanTabSearchRequest Conditions)
        {
            return new SeihiKikakuanTabSearchRespone.ma_seiho_bunsho_seihinkikaku_EXT()
            {
                no_seiho = Conditions.no_seiho
            };
        }

        /// <summary>
        /// Merge current data with copy data
        /// </summary>
        /// <param name="headerData"></param>
        /// <param name="headerDataCopy"></param>
        private void mergeCopyData(ma_seiho_bunsho_seihinkikaku headerData, ma_seiho_bunsho_seihinkikaku headerDataCopy)
        {
            if (headerData != null)
            {
                bool isExistCopy = headerDataCopy == null ? false : true;
                headerData.su_naiyoryo = isExistCopy ? headerDataCopy.su_naiyoryo : null;
                headerData.cd_naiyoryo_tani = isExistCopy ? headerDataCopy.cd_naiyoryo_tani : null;
                headerData.nm_free_tokuseichi = isExistCopy ? headerDataCopy.nm_free_tokuseichi : null;
                headerData.nm_free_biseibutsu = isExistCopy ? headerDataCopy.nm_free_biseibutsu : null;
                headerData.kbn_shomikikan = isExistCopy ? headerDataCopy.kbn_shomikikan : null;
                headerData.kbn_shomikikan_seizo_fukumu = isExistCopy ? headerDataCopy.kbn_shomikikan_seizo_fukumu : null;
                headerData.su_shomikikan_free_input = isExistCopy ? headerDataCopy.su_shomikikan_free_input : null;
                headerData.kbn_shomikikan_tani = isExistCopy ? headerDataCopy.kbn_shomikikan_tani : null;
                headerData.kbn_toriatsukai_ondo = isExistCopy ? headerDataCopy.kbn_toriatsukai_ondo : null;
                headerData.kbn_meisho = isExistCopy ? headerDataCopy.kbn_meisho : null;
                headerData.nm_meisho_hinmei = isExistCopy ? headerDataCopy.nm_meisho_hinmei : null;
                headerData.su_nendo_sokuteichi = isExistCopy ? headerDataCopy.su_nendo_sokuteichi : null;
                headerData.dt_nendo_sokuteichi = isExistCopy ? headerDataCopy.dt_nendo_sokuteichi : null;
                headerData.nm_seihintokusechi_title1 = isExistCopy ? headerDataCopy.nm_seihintokusechi_title1 : null;
                headerData.nm_seihintokusechi_title2 = isExistCopy ? headerDataCopy.nm_seihintokusechi_title2 : null;
                headerData.nm_seihintokusechi_title3 = isExistCopy ? headerDataCopy.nm_seihintokusechi_title3 : null;
            }
        }
        
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class SeihiKikakuanTabCopyResponse
    {
        public ma_seiho_bunsho_seihinkikaku headerData { get; set; }
        public List<ma_seiho_bunsho_tokuseichi> detailData { get; set; }
    }

    // return value for [検索処理]
    public class SeihiKikakuanTabSearchRespone
    {
        // Header data
        public ma_seiho_bunsho_seihinkikaku_EXT headerData { get; set; }
        public ma_seiho_bunsho_seihinkikaku headerDataOld { get; set; }
        // detail data
        public List<ma_seiho_bunsho_tokuseichi> detailData { get; set; }
        public List<ma_seiho_bunsho_tokuseichi> detailDataOld { get; set; }

        public class ma_seiho_bunsho_seihinkikaku_EXT : ma_seiho_bunsho_seihinkikaku 
        {
            // Parameters
            public string pt_kotei { get; set; }
            public string nm_naiyoryo_tani { get; set; }
        }
    }

    public class SeihiKikakuanTabSearchRequest
    {
        public string no_seiho { get; set; }
        public string no_seiho_copy { get; set; }
        public decimal? cd_shain { get; set; }
        public decimal? nen { get; set; }
        public decimal? no_oi { get; set; }
        public string yoryo_tani { get; set; }
        public byte? cd_naiyoryo_tani { get; set;}
        public string pt_kotei { get; set; }
        public string mode { get; set; }
        public string cd_category_shomikikan { get; set; }

    }
    #endregion
}
