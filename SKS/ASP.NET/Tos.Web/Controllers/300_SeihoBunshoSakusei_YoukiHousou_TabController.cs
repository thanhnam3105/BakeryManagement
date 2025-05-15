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

    public class _300_SeihoBunshoSakusei_YoukiHousou_TabController : ApiController
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
        /// <param name="Conditions">製法番号,容器包装コード</param>
        /// <returns>製法文書_Tab2_容器包装テーブル, 製法文書_Tab2_容器包装資材テーブル</returns>
        public ma_seiho_bunsho_yokihoso Get([FromUri] YoukiHousouSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                ma_seiho_bunsho_yokihoso result = new ma_seiho_bunsho_yokihoso();
                context.Configuration.ProxyCreationEnabled = false;
                // Get 製法文書_Tab2_容器包装テーブル
                if (Conditions.mode == NEW_COPY || Conditions.mode == EDIT_COPY)
                {
                    result = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                }
                else
                {
                    result = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                }

                return result;
            }
        }

        /// <summary>
        /// Get [容器包装] data
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns>容器包装マスタ UNION 製法文書_Tab2_容器包装テーブル</returns>
        public YokiHosoComboDataResponse GetYokiHosoComboData([FromUri] YoukiHousouSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                YokiHosoComboDataResponse result = new YokiHosoComboDataResponse();
                context.Configuration.ProxyCreationEnabled = false;
                result.lstYokiHoso = (from i in context.ma_yoki_hoso
                                      select new YokiHosoComboDataResponse.MasterDat { 
                                          cd_yoki_hoso = i.cd_yoki_hoso,
                                          nm_yoki_hoso = i.nm_yoki_hoso
                                      }).OrderBy(x => x.nm_yoki_hoso).ToList();
                ma_seiho_bunsho_yokihoso header = null;
                if (Conditions.mode == NEW_COPY || Conditions.mode == EDIT_COPY)
                {
                    header = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                }
                else
                {
                    header = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                }
                if (header != null) 
                {
                    result.cd_yoki_hoso = header.cd_yoki_hoso;
                    result.nm_yoki_hoso = header.nm_yoki_hoso;
                }

                return result;
            }
        }

        /// <summary>
        /// Get all combobox data for free input in TAB 2
        /// </summary>
        /// <returns></returns>
        public List<sp_shohinkaihatsu_search_300_youkihousou_masterdata_Result> GetMasterData()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                List<sp_shohinkaihatsu_search_300_youkihousou_masterdata_Result> result = new List<sp_shohinkaihatsu_search_300_youkihousou_masterdata_Result>();
                context.Configuration.ProxyCreationEnabled = false;

                result = context.sp_shohinkaihatsu_search_300_youkihousou_masterdata().ToList().OrderBy(x => x.name).ToList();
                return result;
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public YoukiHousouSearchResponse GetDetailYokihoso([FromUri] YoukiHousouSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                YoukiHousouSearchResponse result = new YoukiHousouSearchResponse();
                context.Configuration.ProxyCreationEnabled = false;
                // prepair data
                short? cd_yoki_hoso = (short)Conditions.cd_yoki_hoso;
                // Search data
                ma_yoki_hoso templatedHeader;
                List<sp_shohinkaihatsu_search_300_youkihousou_shizai_Result> templatedDetail;
                // Switching search mode
                switch (Conditions.mode)
                {
                    case NEW:
                        result.headerData = createData(Conditions);
                        if (Conditions.cd_yoki_hoso == Conditions.cd_yoki_hoso_free)
                        {
                            // Nothing to do
                        }
                        else
                        {
                            // Get template data
                            templatedHeader = context.ma_yoki_hoso.Where(x => x.cd_yoki_hoso == Conditions.cd_yoki_hoso).FirstOrDefault();
                            templatedDetail = context.sp_shohinkaihatsu_search_300_youkihousou_shizai(cd_yoki_hoso, Conditions.cd_category_maker, Conditions.cd_category_zaishitsu, Conditions.cd_category_recycle).ToList();
                            // Merge with new header data
                            refillHeader(templatedHeader, result.headerData, Conditions);
                            foreach (var item in templatedDetail)
                            {
                                result.detailData.Add(createData(item, result.headerData, Conditions));
                            }
                        }
                        break;
                    case NEW_COPY:
                        result.headerData = createData(Conditions);
                        if (Conditions.cd_yoki_hoso == Conditions.cd_yoki_hoso_free)
                        {
                            result.headerDataCopy = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                            result.detailData = context.ma_seiho_bunsho_yokihoso_shizai.Where(x => x.no_seiho == Conditions.no_seiho_copy).ToList();
                            syncNoSeiho(result.detailData, Conditions.no_seiho);
                        }
                        else
                        {
                            // Get template data
                            templatedHeader = context.ma_yoki_hoso.Where(x => x.cd_yoki_hoso == Conditions.cd_yoki_hoso).FirstOrDefault();
                            templatedDetail = context.sp_shohinkaihatsu_search_300_youkihousou_shizai(cd_yoki_hoso, Conditions.cd_category_maker, Conditions.cd_category_zaishitsu, Conditions.cd_category_recycle).ToList();
                            // Merge with new header data
                            result.headerDataCopy = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                            // Copy detail data free komoku
                            var copyDataDetail = context.ma_seiho_bunsho_yokihoso_shizai.Where(x => x.no_seiho == Conditions.no_seiho_copy).ToList();
                            if (result.headerDataCopy == null)
                            {
                                result.headerDataCopy = createData(Conditions);
                            }
                            refillHeader(templatedHeader, result.headerDataCopy, Conditions);
                            // Create detail data
                            foreach (var item in templatedDetail)
                            {
                                var newDetail = createData(item, result.headerData, Conditions);
                                newDetail.nm_free_komoku = copyDataDetail.Where(x => x.cd_yoki_hoso_shizai == newDetail.cd_yoki_hoso_shizai).Select(x => x.nm_free_komoku).FirstOrDefault();
                                result.detailData.Add(newDetail);
                                mergeNameShizai(item, result.headerDataCopy);
                            }
                        }
                        break;
                    case VIEW:
                        result.headerData = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        result.detailData = context.ma_seiho_bunsho_yokihoso_shizai.Where(x => x.no_seiho == Conditions.no_seiho).ToList();
                        result.detailDataOld = reOrderDetailData(result.detailData, result.headerData);
                        break;
                    case EDIT:
                        result.headerData = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        result.detailDataOld = reOrderDetailData(context.ma_seiho_bunsho_yokihoso_shizai.Where(x => x.no_seiho == Conditions.no_seiho).ToList(), result.headerData);

                        if (Conditions.cd_yoki_hoso == Conditions.cd_yoki_hoso_free)
                        {
                            result.detailData = context.ma_seiho_bunsho_yokihoso_shizai.Where(x => x.no_seiho == Conditions.no_seiho).ToList();
                        }
                        else
                        {
                            // Get template data
                            templatedHeader = context.ma_yoki_hoso.Where(x => x.cd_yoki_hoso == Conditions.cd_yoki_hoso).FirstOrDefault();
                            templatedDetail = context.sp_shohinkaihatsu_search_300_youkihousou_shizai(cd_yoki_hoso, Conditions.cd_category_maker, Conditions.cd_category_zaishitsu, Conditions.cd_category_recycle).ToList();
                            // Merge with new header data
                            refillHeader(templatedHeader, result.headerData, Conditions);
                            // Create detail data
                            foreach (var item in templatedDetail)
                            {
                                var newDetail = createData(item, result.headerData, Conditions);
                                //newDetail.nm_free_komoku = result.detailDataOld.Where(x => x.cd_yoki_hoso_shizai == newDetail.cd_yoki_hoso_shizai).Select(x => x.nm_free_komoku).FirstOrDefault();
                                result.detailData.Add(newDetail);

                            }
                        }
                        break;
                    case EDIT_COPY:
                        result.headerData = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        result.headerDataCopy = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        result.detailDataOld = reOrderDetailData(context.ma_seiho_bunsho_yokihoso_shizai.Where(x => x.no_seiho == Conditions.no_seiho).ToList(), result.headerData);

                        if (Conditions.cd_yoki_hoso == Conditions.cd_yoki_hoso_free)
                        {
                            result.detailData = context.ma_seiho_bunsho_yokihoso_shizai.Where(x => x.no_seiho == Conditions.no_seiho_copy).ToList();
                            syncNoSeiho(result.detailData, Conditions.no_seiho);
                        }
                        else
                        {
                            // Get template data
                            templatedHeader = context.ma_yoki_hoso.Where(x => x.cd_yoki_hoso == Conditions.cd_yoki_hoso).FirstOrDefault();
                            templatedDetail = context.sp_shohinkaihatsu_search_300_youkihousou_shizai(cd_yoki_hoso, Conditions.cd_category_maker, Conditions.cd_category_zaishitsu, Conditions.cd_category_recycle).ToList();
                            // Merge with new header data
                            refillHeader(templatedHeader, result.headerDataCopy, Conditions);
                            // Copy detail data free komoku
                            var copyDataDetail = context.ma_seiho_bunsho_yokihoso_shizai.Where(x => x.no_seiho == Conditions.no_seiho_copy).ToList();
                            // Create detail data
                            foreach (var item in templatedDetail)
                            {
                                //result.detailData.Add(createData(item, result.headerData, Conditions));
                                var newDetail = createData(item, result.headerData, Conditions);
                                newDetail.nm_free_komoku = copyDataDetail.Where(x => x.cd_yoki_hoso_shizai == newDetail.cd_yoki_hoso_shizai).Select(x => x.nm_free_komoku).FirstOrDefault();
                                result.detailData.Add(newDetail);
                            }
                        }
                        break;
                }
                reOrderDataSet(result, Conditions.mode);
                return result;
            }
        }

        /// <summary>
        /// Copy mode (unavalable now)
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public YoukiHousouSearchResponse GetCopyData([FromUri] YoukiHousouSearchRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                YoukiHousouSearchResponse result = new YoukiHousouSearchResponse();
                context.Configuration.ProxyCreationEnabled = false;
                // prepair data
                short? cd_yoki_hoso = (short)Conditions.cd_yoki_hoso;
                string no_seiho = Conditions.no_seiho;
                ma_yoki_hoso templatedHeader;
                List<sp_shohinkaihatsu_search_300_youkihousou_shizai_Result> templatedDetail;
                // Switching search mode
                if ((Conditions.mode == NEW_COPY || Conditions.mode == EDIT_COPY) && !Conditions.isChangeYokihoso)
                {
                    no_seiho = Conditions.no_seiho_copy;
                    ma_seiho_bunsho_yokihoso copyHoso = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == no_seiho).FirstOrDefault();
                    if (copyHoso != null)
                    {
                        cd_yoki_hoso = (short)copyHoso.cd_yoki_hoso;
                        result.headerData = copyHoso;
                    }
                    else
                    {
                        cd_yoki_hoso = null;
                    }
                }

                if (result.headerData == null)
                {
                    result.headerData = createData(Conditions);
                }

                result.detailData = new List<ma_seiho_bunsho_yokihoso_shizai>();
                if (cd_yoki_hoso == Conditions.cd_yoki_hoso_free)
                {
                    if (Conditions.isChangeYokihoso)
                    {
                        // DO not thing
                    }
                    else
                    {
                        result.headerData = context.ma_seiho_bunsho_yokihoso.Where(x => x.no_seiho == no_seiho).FirstOrDefault();
                        result.detailData = context.ma_seiho_bunsho_yokihoso_shizai.Where(x => x.no_seiho == no_seiho).ToList();
                    }
                }
                else
                {
                    // Get template data
                    templatedHeader = context.ma_yoki_hoso.Where(x => x.cd_yoki_hoso == cd_yoki_hoso).FirstOrDefault();
                    templatedDetail = context.sp_shohinkaihatsu_search_300_youkihousou_shizai(cd_yoki_hoso, Conditions.cd_category_maker, Conditions.cd_category_zaishitsu, Conditions.cd_category_recycle).ToList();
                    // Merge with new header data
                    refillHeader(templatedHeader, result.headerData, Conditions);
                    // Create detail data
                    foreach (var item in templatedDetail)
                    {
                        result.detailData.Add(createData(item, result.headerData, Conditions));
                    }
                }
                reOrderDataSet(result, Conditions.mode);
                return result;
            }
            
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="templatedHeader"></param>
        /// <param name="headerData"></param>
        /// <param name="Conditions"></param>
        private void refillHeader(ma_yoki_hoso templatedHeader, ma_seiho_bunsho_yokihoso headerData, YoukiHousouSearchRequest Conditions)
        {
            if (templatedHeader == null || headerData == null)
            {
                return;
            }
            DataCopier.ReFill(templatedHeader, headerData);
            // Reset name shizai
            headerData.nm_yoki_hoso_shizai01 = null;
            headerData.nm_yoki_hoso_shizai02 = null;
            headerData.nm_yoki_hoso_shizai03 = null;
            headerData.nm_yoki_hoso_shizai04 = null;
            headerData.nm_yoki_hoso_shizai05 = null;
            headerData.nm_yoki_hoso_shizai06 = null;
            headerData.nm_yoki_hoso_shizai07 = null;
            headerData.nm_yoki_hoso_shizai08 = null;
            headerData.nm_yoki_hoso_shizai09 = null;
            headerData.nm_yoki_hoso_shizai10 = null;
            headerData.no_seiho = Conditions.no_seiho;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="templatedDetail"></param>
        /// <param name="detailData"></param>
        /// <param name="Conditions"></param>
        private void refillDetail(sp_shohinkaihatsu_search_300_youkihousou_shizai_Result templatedDetail, ma_seiho_bunsho_yokihoso_shizai detailData, YoukiHousouSearchRequest Conditions)
        {
            if (templatedDetail == null || detailData == null)
            {
                return;
            }
            DataCopier.ReFill(templatedDetail, detailData);
            //detailData.cd_yoki_hoso_shizai = templatedDetail.cd_yoki_hoso_shizai;
            //detailData.cd_maker01 = templatedDetail.cd_maker01;
            //detailData.nm_maker01 = templatedDetail.nm_maker01;
            //detailData.cd_maker02 = templatedDetail.cd_maker02;
            //detailData.nm_maker02 = templatedDetail.nm_maker02;
            //detailData.cd_maker03 = templatedDetail.cd_maker03;
            //detailData.nm_maker03 = templatedDetail.nm_maker03;
            //detailData.cd_maker04 = templatedDetail.cd_maker04;
            //detailData.nm_maker04 = templatedDetail.nm_maker04;
            //detailData.cd_maker05 = templatedDetail.cd_maker05;
            //detailData.nm_maker05 = templatedDetail.nm_maker05;
            //detailData.no_shizai_kikakusho01 = templatedDetail.no_shizai_kikakusho01;
            //detailData.no_shizai_kikakusho02 = templatedDetail.no_shizai_kikakusho02;
            //detailData.no_shizai_kikakusho03 = templatedDetail.no_shizai_kikakusho03;
            //detailData.no_shizai_kikakusho04 = templatedDetail.no_shizai_kikakusho04;
            //detailData.no_shizai_kikakusho05 = templatedDetail.no_shizai_kikakusho05;
            //detailData.cd_zaishitsu01 = templatedDetail.cd_zaishitsu01;
            //detailData.nm_zaishitsu01 = templatedDetail.nm_zaishitsu01;
            //detailData.cd_zaishitsu02 = templatedDetail.cd_zaishitsu02;
            //detailData.nm_zaishitsu02 = templatedDetail.nm_zaishitsu02;
            //detailData.cd_zaishitsu03 = templatedDetail.cd_zaishitsu03;
            //detailData.nm_zaishitsu03 = templatedDetail.nm_zaishitsu03;
            //detailData.cd_zaishitsu04 = templatedDetail.cd_zaishitsu04;
            //detailData.nm_zaishitsu04 = templatedDetail.nm_zaishitsu04;
            //detailData.cd_zaishitsu05 = templatedDetail.cd_zaishitsu05;
            //detailData.nm_zaishitsu05 = templatedDetail.nm_zaishitsu05;
            //detailData.cd_recycle_hyoji01 = templatedDetail.cd_recycle_hyoji01;
            //detailData.nm_recycle_hyoji01 = templatedDetail.nm_recycle_hyoji01;
            //detailData.cd_recycle_hyoji02 = templatedDetail.cd_recycle_hyoji02;
            //detailData.nm_recycle_hyoji02 = templatedDetail.nm_recycle_hyoji02;
            //detailData.cd_recycle_hyoji03 = templatedDetail.cd_recycle_hyoji03;
            //detailData.nm_recycle_hyoji03 = templatedDetail.nm_recycle_hyoji03;
            //detailData.cd_recycle_hyoji04 = templatedDetail.cd_recycle_hyoji04;
            //detailData.nm_recycle_hyoji04 = templatedDetail.nm_recycle_hyoji04;
            //detailData.cd_recycle_hyoji05 = templatedDetail.cd_recycle_hyoji05;
            //detailData.nm_recycle_hyoji05 = templatedDetail.nm_recycle_hyoji05;
            //detailData.nm_size01 = templatedDetail.nm_size01;
            //detailData.nm_size02 = templatedDetail.nm_size02;
            //detailData.nm_size03 = templatedDetail.nm_size03;
            //detailData.nm_size04 = templatedDetail.nm_size04;
            //detailData.nm_size05 = templatedDetail.nm_size05;
            detailData.no_seiho = Conditions.no_seiho;
            detailData.cd_yoki_hoso = Conditions.cd_yoki_hoso;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="templatedDetail"></param>
        /// <param name="headerData"></param>
        private void mergeNameShizai(sp_shohinkaihatsu_search_300_youkihousou_shizai_Result templatedDetail, ma_seiho_bunsho_yokihoso headerData)
        {
            if (templatedDetail == null || headerData == null)
            {
                return;
            }
            if (headerData.cd_yoki_hoso_shizai01 == templatedDetail.cd_yoki_hoso_shizai)
            {
                headerData.nm_yoki_hoso_shizai01 = templatedDetail.nm_yoki_hoso_shizai;
            }
            if (headerData.cd_yoki_hoso_shizai02 == templatedDetail.cd_yoki_hoso_shizai)
            {
                headerData.nm_yoki_hoso_shizai02 = templatedDetail.nm_yoki_hoso_shizai;
            }
            if (headerData.cd_yoki_hoso_shizai03 == templatedDetail.cd_yoki_hoso_shizai)
            {
                headerData.nm_yoki_hoso_shizai03 = templatedDetail.nm_yoki_hoso_shizai;
            }
            if (headerData.cd_yoki_hoso_shizai04 == templatedDetail.cd_yoki_hoso_shizai)
            {
                headerData.nm_yoki_hoso_shizai04 = templatedDetail.nm_yoki_hoso_shizai;
            }
            if (headerData.cd_yoki_hoso_shizai05 == templatedDetail.cd_yoki_hoso_shizai)
            {
                headerData.nm_yoki_hoso_shizai05 = templatedDetail.nm_yoki_hoso_shizai;
            }
            if (headerData.cd_yoki_hoso_shizai06 == templatedDetail.cd_yoki_hoso_shizai)
            {
                headerData.nm_yoki_hoso_shizai06 = templatedDetail.nm_yoki_hoso_shizai;
            }
            if (headerData.cd_yoki_hoso_shizai07 == templatedDetail.cd_yoki_hoso_shizai)
            {
                headerData.nm_yoki_hoso_shizai07 = templatedDetail.nm_yoki_hoso_shizai;
            }
            if (headerData.cd_yoki_hoso_shizai08 == templatedDetail.cd_yoki_hoso_shizai)
            {
                headerData.nm_yoki_hoso_shizai08 = templatedDetail.nm_yoki_hoso_shizai;
            }
            if (headerData.cd_yoki_hoso_shizai09 == templatedDetail.cd_yoki_hoso_shizai)
            {
                headerData.nm_yoki_hoso_shizai09 = templatedDetail.nm_yoki_hoso_shizai;
            }
            if (headerData.cd_yoki_hoso_shizai10 == templatedDetail.cd_yoki_hoso_shizai)
            {
                headerData.nm_yoki_hoso_shizai10 = templatedDetail.nm_yoki_hoso_shizai;
            }
        }

        /// <summary>
        /// Create new header data
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        private ma_seiho_bunsho_yokihoso createData(YoukiHousouSearchRequest Conditions)
        {
            return new ma_seiho_bunsho_yokihoso() 
            {
                no_seiho = Conditions.no_seiho,
                cd_yoki_hoso = Conditions.cd_yoki_hoso
            };
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="detailData"></param>
        /// <param name="headerData"></param>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        private ma_seiho_bunsho_yokihoso_shizai createData(sp_shohinkaihatsu_search_300_youkihousou_shizai_Result detailData, ma_seiho_bunsho_yokihoso headerData, YoukiHousouSearchRequest Conditions)
        {
            ma_seiho_bunsho_yokihoso_shizai data = new ma_seiho_bunsho_yokihoso_shizai();
            refillDetail(detailData, data, Conditions);
            mergeNameShizai(detailData, headerData);
            return data;
        }

        /// <summary>
        /// Sync no_seiho in copy data
        /// </summary>
        /// <param name="lstDetail"></param>
        /// <param name="no_seiho"></param>
        private void syncNoSeiho(List<ma_seiho_bunsho_yokihoso_shizai> lstDetail, string no_seiho)
        {
            if (lstDetail != null)
            {
                foreach (var item in lstDetail)
                {
                    item.no_seiho = no_seiho;
                }
            }
        }
        /// <summary>
        /// reorder detail data list
        /// </summary>
        /// <param name="data"></param>
        private void reOrderDataSet(YoukiHousouSearchResponse data, string mode)
        {
            var headerData = data.headerData;
            if (mode == NEW_COPY || mode == EDIT_COPY)
            {
                headerData = data.headerDataCopy;
            }
            if (headerData == null || data.detailData == null || data.detailData.Count == 0)
            {
                return;
            }
            var searchDataSet = data.detailData;
            data.detailData = new List<ma_seiho_bunsho_yokihoso_shizai>();
            data.detailData.Add(searchDataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai01).FirstOrDefault());
            data.detailData.Add(searchDataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai02).FirstOrDefault());
            data.detailData.Add(searchDataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai03).FirstOrDefault());
            data.detailData.Add(searchDataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai04).FirstOrDefault());
            data.detailData.Add(searchDataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai05).FirstOrDefault());
            data.detailData.Add(searchDataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai06).FirstOrDefault());
            data.detailData.Add(searchDataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai07).FirstOrDefault());
            data.detailData.Add(searchDataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai08).FirstOrDefault());
            data.detailData.Add(searchDataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai09).FirstOrDefault());
            data.detailData.Add(searchDataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai10).FirstOrDefault());
        }

        /// <summary>
        /// Order by detail data
        /// </summary>
        /// <param name="dataSet"></param>
        /// <param name="headerData"></param>
        /// <returns></returns>
        private List<ma_seiho_bunsho_yokihoso_shizai> reOrderDetailData(List<ma_seiho_bunsho_yokihoso_shizai> dataSet, ma_seiho_bunsho_yokihoso headerData)
        {
            if (headerData == null || dataSet == null)
            {
                return dataSet;
            }
            List<ma_seiho_bunsho_yokihoso_shizai> result = new List<ma_seiho_bunsho_yokihoso_shizai>();
            result.Add(dataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai01).FirstOrDefault());
            result.Add(dataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai02).FirstOrDefault());
            result.Add(dataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai03).FirstOrDefault());
            result.Add(dataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai04).FirstOrDefault());
            result.Add(dataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai05).FirstOrDefault());
            result.Add(dataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai06).FirstOrDefault());
            result.Add(dataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai07).FirstOrDefault());
            result.Add(dataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai08).FirstOrDefault());
            result.Add(dataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai09).FirstOrDefault());
            result.Add(dataSet.Where(x => x.cd_yoki_hoso_shizai == headerData.cd_yoki_hoso_shizai10).FirstOrDefault());
            return result;
        }

        #endregion

    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// Search request param
    /// </summary>
    public class YoukiHousouSearchRequest
    {
        public string no_seiho { get; set; }
        public string no_seiho_copy { get; set; }
        public int cd_yoki_hoso { get; set; }
        public int cd_yoki_hoso_free { get; set; }
        public string cd_category_maker { get; set; }
        public string cd_category_zaishitsu { get; set; }
        public string cd_category_recycle { get; set; }
        public bool isChangeYokihoso { get; set; }
        public string mode { get; set; }
    }
    
    /// <summary>
    /// Search result data
    /// </summary>
    //public class YoukiHousouFreeSearchResponse
    //{
    //    public ma_seiho_bunsho_yokihoso headerData { get; set; }
    //    public List<ma_seiho_bunsho_yokihoso_shizai> detailData { get; set; }
    //}

    /// <summary>
    /// Search result data
    /// </summary>
    public class YoukiHousouSearchResponse
    {
        public ma_seiho_bunsho_yokihoso headerData { get; set; }
        public ma_seiho_bunsho_yokihoso headerDataCopy { get; set; }
        public List<ma_seiho_bunsho_yokihoso_shizai> detailData { get; set; }
        public List<ma_seiho_bunsho_yokihoso_shizai> detailDataOld { get; set; }

        public YoukiHousouSearchResponse() 
        {
            detailData = new List<ma_seiho_bunsho_yokihoso_shizai>();
            detailDataOld = new List<ma_seiho_bunsho_yokihoso_shizai>();
        }
    }

    /// <summary>
    /// cd_yoki_hoso combobox data
    /// </summary>
    public class YokiHosoComboDataResponse
    {
        // 容器包装名
        public List<MasterDat> lstYokiHoso { get; set; }
        public int? cd_yoki_hoso { get; set; }
        public string nm_yoki_hoso { get; set; }

        public class MasterDat
        {
            public int? cd_yoki_hoso { get; set; }
            public string nm_yoki_hoso { get; set; }
        }
    }
    #endregion
}
