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
using System.Threading;

namespace Tos.Web.Controllers
{

    public class _300_SeihoBunshoSakusei_HaigoSeizoChuiJiko_TabController : ApiController
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
        /// Search function for TAB 4 [配合・製造上の注意事項]
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public HaigoSeizoChuiJikoResponse Get([FromUri] HaigoSeizoChuiJikoRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                // Declarations
                decimal? cd_shain = Conditions.cd_shain;
                decimal? nen = Conditions.nen;
                decimal? no_oi = Conditions.no_oi;
                short? seq_shisaku = Conditions.seq_shisaku;
                string materialSeiho = Conditions.no_seiho;
                string pt_kotei = Conditions.pt_kotei;
                // Re-calculate [ドレッシング充填量] in NEW and EDIT mode
                bool isCalc = true;
                HaigoSeizoChuiJikoResponse result = new HaigoSeizoChuiJikoResponse();
                //ma_seiho materialSeihoData = context.ma_seiho.Where(x => x.no_seiho == materialSeiho).FirstOrDefault();
                //tr_shisakuhin Shisaku = null;
                //// Get shisaku key
                //if (materialSeihoData != null)
                //{
                //    cd_shain = materialSeihoData.cd_shain;
                //    nen = materialSeihoData.nen;
                //    no_oi = materialSeihoData.no_oi;
                //    seq_shisaku = materialSeihoData.seq_shisaku;
                //}
                //// Get pt_kotei
                //if (cd_shain != null)
                //{
                //    Shisaku = context.tr_shisakuhin.Where(x => x.cd_shain == cd_shain && x.nen == nen && x.no_oi == no_oi).FirstOrDefault();
                //    if (Shisaku != null)
                //    {
                //        pt_kotei = Shisaku.pt_kotei;
                //    }
                //}
                // Fill parameters base on mode
                switch (Conditions.mode)
                { 
                    case NEW:
                        result.currentData = createData(Conditions, pt_kotei);
                        break;
                    case NEW_COPY:
                        result.currentData = createData(Conditions, pt_kotei);
                        result.copyData = context.ma_seiho_bunsho_haigo_chui.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        break;
                    case EDIT:
                    case VIEW:
                        if (Conditions.mode == VIEW)
                        {
                            isCalc = false;
                        }
                        result.currentData = context.ma_seiho_bunsho_haigo_chui.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        break;
                    case EDIT_COPY:
                        result.currentData = context.ma_seiho_bunsho_haigo_chui.Where(x => x.no_seiho == Conditions.no_seiho).FirstOrDefault();
                        result.copyData = context.ma_seiho_bunsho_haigo_chui.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                        break;
                }
                // Create new data when no data found
                if (result.currentData == null)
                {
                    result.currentData = createData(Conditions, pt_kotei);
                }
                // Sync pt_kotei with tr_shisaku.pt_kotei in EDIT mode
                if (Conditions.mode == EDIT || Conditions.mode == EDIT_COPY)
                {
                    result.currentData.pt_kotei = pt_kotei;
                }
                // Just calc when pt_kotei = [調味料2液タイプ]
                if (isCalc && result.currentData.pt_kotei == Conditions.kbn_pt_kotei)
                {
                    // Find all calculate materials
                    var calMaterial = context.sp_shohinkaihatsu_search_300_haigoseizochuijiko(
                                                                                                cd_shain,
                                                                                                nen,
                                                                                                no_oi,
                                                                                                seq_shisaku,
                                                                                                Conditions.kbn_pt_kotei,
                                                                                                Conditions.kbn_chomi,
                                                                                                Conditions.kbn_suisho,
                                                                                                Conditions.kbn_yusho
                                                                                            ).FirstOrDefault();
                    // Calculate [ドレッシング充填量]
                    if (calMaterial != null)
                    {
                        calculate(result.currentData, calMaterial, Conditions.cd_tani_g);
                        result.cd_tani = calMaterial.cd_tani;
                    }
                    else
                    {
                        // ②0.920固定
                        result.currentData.su_haigo_yuso_hiju = (decimal)0.920;
                    }
                }

                return result;
            }
        }

        /// <summary>
        /// Update [ドレッシング充填量] data when main page change the Shisaku [試作No]
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        [HttpGet]
        public HaigoSeizoChuiJikoResponse ChangeShisaku([FromUri] HaigoSeizoChuiJikoRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                HaigoSeizoChuiJikoResponse result = new HaigoSeizoChuiJikoResponse();
                result.currentData = new ma_seiho_bunsho_haigo_chui();
                tr_shisakuhin Shisaku = context.tr_shisakuhin.Where(x => x.cd_shain == Conditions.cd_shain && x.nen == Conditions.nen && x.no_oi == Conditions.no_oi).FirstOrDefault();
                if (Shisaku != null)
                {
                    result.currentData.pt_kotei = Shisaku.pt_kotei;
                }
                // Re-calculate [ドレッシング充填量] when pt_kotei eq [調味料2液タイプ]
                if (result.currentData.pt_kotei == Conditions.kbn_pt_kotei)
                {
                    // Find all calculate materials
                    var calMaterial = context.sp_shohinkaihatsu_search_300_haigoseizochuijiko(
                                                                                                Conditions.cd_shain,
                                                                                                Conditions.nen,
                                                                                                Conditions.no_oi,
                                                                                                Conditions.seq_shisaku,
                                                                                                Conditions.pt_kotei,
                                                                                                Conditions.kbn_chomi,
                                                                                                Conditions.kbn_suisho,
                                                                                                Conditions.kbn_yusho
                                                                                            ).FirstOrDefault();
                    // Calculate [ドレッシング充填量]
                    if (calMaterial != null)
                    {
                        calculate(result.currentData, calMaterial, Conditions.cd_tani_g);
                        result.cd_tani = calMaterial.cd_tani;
                    }
                    else
                    {
                        // ②0.920固定
                        result.currentData.su_haigo_yuso_hiju = (decimal)0.920;
                    }
                }
                return result;
            }
        }

        /// <summary>
        /// Reload copy data
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        public ma_seiho_bunsho_haigo_chui getCopyData([FromUri]HaigoSeizoChuiJikoRequest Conditions)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var result = context.ma_seiho_bunsho_haigo_chui.Where(x => x.no_seiho == Conditions.no_seiho_copy).FirstOrDefault();
                if (result != null)
                {
                    result.pt_kotei = Conditions.pt_kotei;
                }
                return result;
            }
        }


        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// Create new [ma_seiho_bunsho_haigo_chui] in new mode
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        private ma_seiho_bunsho_haigo_chui createData(HaigoSeizoChuiJikoRequest Conditions, string pt_kotei)
        {
            return new ma_seiho_bunsho_haigo_chui() { 
                no_seiho = Conditions.no_seiho,
                pt_kotei = pt_kotei
            };
        }

        /// <summary>
        /// Apply the calculate to result data for cd_tani = 002 (g)
        /// </summary>
        /// <param name="result"></param>
        /// <param name="calMaterial"></param>
        private void calculateA2(ma_seiho_bunsho_haigo_chui result, sp_shohinkaihatsu_search_300_haigoseizochuijiko_Result calMaterial)
        {
            decimal n;
            if (calMaterial.sum_chomi == null || calMaterial.sum_suisho == null || calMaterial.sum_yusho == null)
            {
                return;
            }
            decimal? sumAll = calMaterial.sum_chomi + calMaterial.sum_suisho + calMaterial.sum_yusho;
            if (decimal.TryParse(calMaterial.yoryo, out n) && sumAll != 0)
            {
                decimal yoryo = decimal.Parse(calMaterial.yoryo);
                // ①水相g=(殺菌調味液g+2液水相g)／(殺菌調味液g+2液水相g+2液油相g)×製品容量
                result.su_haigo_suiso_g = (calMaterial.sum_chomi + calMaterial.sum_suisho) / (sumAll) * yoryo;
                result.su_haigo_suiso_g = convertLimit(result.su_haigo_suiso_g, (decimal)(9999.9));
                // ②油相g=(2液油相g)／(殺菌調味液g+2液水相g+2液油相g)×製品容量
                result.su_haigo_yuso_g = (calMaterial.sum_yusho) / (sumAll) * yoryo;
                result.su_haigo_yuso_g = convertLimit(result.su_haigo_yuso_g, (decimal)(9999.9));
                // ③合計g=水相g+油相g
                result.su_haigo_gokei_g = result.su_haigo_suiso_g + result.su_haigo_yuso_g;
                result.su_haigo_gokei_g = convertLimit(result.su_haigo_gokei_g, (decimal)(9999.9));
            }
            result.su_haigo_suiso_g = Round(result.su_haigo_suiso_g, 1);
            result.su_haigo_yuso_g = Round(result.su_haigo_yuso_g, 1);
            result.su_haigo_gokei_g = Round(result.su_haigo_gokei_g, 1);
        }

        /// <summary>
        /// Apply the calculate to result data for cd_tani != 002 (g)
        /// </summary>
        /// <param name="result">result data</param>
        /// <param name="calMaterial">material for calculate</param>
        private void calculate(ma_seiho_bunsho_haigo_chui result, sp_shohinkaihatsu_search_300_haigoseizochuijiko_Result calMaterial, string cd_tani_g)
        {
            decimal n;
            ResetCalcData(result);
            if (calMaterial.cd_tani == cd_tani_g)
            {
                calculateA2(result, calMaterial);
                return;
            }
            // ①特性値の水相比重（実測）からとばす
            if (decimal.TryParse(calMaterial.hiju_sui, out n))
            {
                result.su_haigo_suiso_hiju = decimal.Parse(calMaterial.hiju_sui);
                result.su_haigo_suiso_hiju = convertLimit(result.su_haigo_suiso_hiju, (decimal)(9.999), true);
            }
            // ②0.920固定
            result.su_haigo_yuso_hiju = (decimal)0.920;
            // ③基本情報の容量ml
            if (decimal.TryParse(calMaterial.yoryo, out n))
            {
                result.su_haigo_gokei_ml = decimal.Parse(calMaterial.yoryo);
                result.su_haigo_gokei_ml = convertLimit(result.su_haigo_gokei_ml, (decimal)(99999.9), true);
            }
            if (result.su_haigo_suiso_hiju != 0 && result.su_haigo_gokei_ml != null 
                && calMaterial.sum_chomi != null && calMaterial.sum_suisho != null && calMaterial.sum_yusho != null
                && (calMaterial.sum_chomi + calMaterial.sum_suisho) != 0) 
            {       
                decimal? B = ((calMaterial.sum_chomi + calMaterial.sum_suisho) / result.su_haigo_suiso_hiju + calMaterial.sum_yusho / result.su_haigo_yuso_hiju);
                if (B != null && B != 0)
                {
                    // A＝基本情報の容量ml／（（配合表の殺菌調味液ｇ＋２液水相ｇ）／水相比重＋2液油相ｇ／０．９２０）
                    decimal? A = result.su_haigo_gokei_ml / B;
                    if (A != null) 
                    {
                        // ④（配合表の殺菌調味液ｇ＋２液水相ｇ）／水相比重×A
                        result.su_haigo_suiso_ml = (calMaterial.sum_chomi + calMaterial.sum_suisho) / result.su_haigo_suiso_hiju * A;
                        result.su_haigo_suiso_ml = convertLimit(result.su_haigo_suiso_ml, (decimal)(9999.9));
                        // ⑤（2液油相ｇ）／0.920×A
                        result.su_haigo_yuso_ml = calMaterial.sum_yusho / result.su_haigo_yuso_hiju * A;
                        result.su_haigo_yuso_ml = convertLimit(result.su_haigo_yuso_ml, (decimal)(9999.9));
                        if (result.su_haigo_suiso_ml != null) 
                        {
                            // ⑥水相ml×水相比重
                            result.su_haigo_suiso_g = result.su_haigo_suiso_ml * result.su_haigo_suiso_hiju;
                            result.su_haigo_suiso_g = convertLimit(result.su_haigo_suiso_g, (decimal)(9999.9));
                            // ⑦油相ml×0.920
                            result.su_haigo_yuso_g = result.su_haigo_yuso_ml * result.su_haigo_yuso_hiju;
                            result.su_haigo_yuso_g = convertLimit(result.su_haigo_yuso_g, (decimal)(9999.9));
                            // ⑧水相ｇ＋油相ｇ
                            result.su_haigo_gokei_g = result.su_haigo_suiso_g + result.su_haigo_yuso_g;
                            result.su_haigo_gokei_g = convertLimit(result.su_haigo_gokei_g, (decimal)(99999.9));
                            if (result.su_haigo_gokei_ml != 0)
                            {
                                // ⑨合計g／合計ml
                                result.su_haigo_gokei_hiju = result.su_haigo_gokei_g / result.su_haigo_gokei_ml;
                                result.su_haigo_gokei_hiju = convertLimit(result.su_haigo_gokei_hiju, (decimal)(9.999));
                            }
                        }
                    }
                }
            }
            result.su_haigo_suiso_g = Round(result.su_haigo_suiso_g, 1);
            result.su_haigo_suiso_ml = Round(result.su_haigo_suiso_ml, 1);
            result.su_haigo_suiso_hiju = Round(result.su_haigo_suiso_hiju, 3);
            result.su_haigo_yuso_g = Round(result.su_haigo_yuso_g, 1);
            result.su_haigo_yuso_ml = Round(result.su_haigo_yuso_ml, 1);
            result.su_haigo_yuso_hiju = Round(result.su_haigo_yuso_hiju, 3);
            result.su_haigo_gokei_g = Round(result.su_haigo_gokei_g, 1);
            result.su_haigo_gokei_ml = Round(result.su_haigo_gokei_ml, 1);
            result.su_haigo_gokei_hiju = Round(result.su_haigo_gokei_hiju, 3, true);
        }

        /// <summary>
        /// Reset all calculated value
        /// </summary>
        /// <param name="result"></param>
        private void ResetCalcData(ma_seiho_bunsho_haigo_chui result)
        {
            result.su_haigo_suiso_g = null;
            result.su_haigo_suiso_ml = null;
            result.su_haigo_suiso_hiju = null;
            result.su_haigo_yuso_g = null;
            result.su_haigo_yuso_ml = null;
            result.su_haigo_yuso_hiju = null;
            result.su_haigo_gokei_g = null;
            result.su_haigo_gokei_ml = null;
            result.su_haigo_gokei_hiju = null;
        }

        /// <summary>
        /// If value over the limit -> null. If value need to convert to 0 
        /// </summary>
        /// <returns></returns>
        private decimal? convertLimit(decimal? value, decimal limit, bool isConvertZero = false)
        {

            if (value > limit)
            {
                value = null;
            }
            else 
            {
                if (value == null && isConvertZero)
                {
                    return 0;
                }
            }
            return value;
        }

        /// <summary>
        /// Get rounded value
        /// </summary>
        /// <param name="value"></param>
        /// <param name="num"></param>
        /// <returns></returns>
        private decimal? Round(decimal? value, int num, bool isCiel = false)
        {
            if (value == null)
            {
                return null;
            }
            decimal res = value ?? 0;
            if (isCiel)
            {
                decimal exp = (decimal)Math.Pow(10, num);
                return Math.Ceiling(res * exp) / exp;
            }
            return Math.Round(res, num);
        }


        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// TAB 4 [配合・製造上の注意事項] search response data
    /// </summary>
    public class HaigoSeizoChuiJikoResponse
    {
        public ma_seiho_bunsho_haigo_chui currentData { get; set; }
        public ma_seiho_bunsho_haigo_chui copyData { get; set; }
        public string cd_tani { get; set; }
    }

    /// <summary>
    /// TAB 4 [配合・製造上の注意事項] search request param
    /// </summary>
    public class HaigoSeizoChuiJikoRequest
    {
        // Monitor parameters
        public string no_seiho { get; set; }
        public string no_seiho_copy { get; set; }
        // Shisaku parameters
        public decimal? cd_shain { get; set; }
        public decimal? nen { get; set; }
        public decimal? no_oi { get; set; }
        public short? seq_shisaku { get; set; }
        public string pt_kotei { get; set; }
        public string cd_tani_g { get; set; }
        // Const value
        public string kbn_pt_kotei { get; set; }
        public string kbn_chomi { get; set; }
        public string kbn_suisho { get; set; }
        public string kbn_yusho { get; set; }
        // Tab mode
        public string mode { get; set; }
    }

    #endregion
}
