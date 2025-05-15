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
using System.Globalization;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _708_GenryoMasterSansho_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_708_Result> Get_lab([FromUri]GenryoMasterSanshoRequest Conditions)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;
            decimal? cd_hin = Convert.ToDecimal(Conditions.cd_hin, CultureInfo.InvariantCulture);
            StoredProcedureResult<sp_shohinkaihatsu_search_708_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_708_Result>();
            result.Items = context.sp_shohinkaihatsu_search_708(cd_hin, 
                                                                Conditions.cd_kaisha,
                                                                Conditions.cd_kojyo).ToList();

            return result;

        }

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
        public object Get_factory([FromUri]GenryoMasterSanshoRequest Conditions)
        {
            object results;
            short cd_kaisha = Conditions.cd_kaisha ?? 0;
            short cd_kojyo = Conditions.cd_kojyo ?? 0;
            if (cd_kaisha == 0 || cd_kojyo == 0)
            {
                return null;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;
                
                var query = "SELECT "
                                + "genshizai.[cd_hin]"
                                + ",genshizai.[nm_hin]"
                                + ",genshizai.[nm_hin_r]"
                                + ",genshizai.[nisugata_qty]"
                                + ",genshizai.[nisugata_hyoji]"
                                + ",genshizai.[su_iri]"
                                + ",genshizai.[qty]"
                                + ",genshizai.[cd_tani_shiyo]"
                                + ",genshizai.[cd_shukei]"
                                + ",genshizai.[budomari]"
                                + ",genshizai.[hijyu]"
                                + ",CAST(genshizai.[size_hachu] AS VARCHAR(30)) size_hachu"
                                + ",genshizai.[su_leadtime]"
                                + ",genshizai.[su_saitei]"
                                + ",genshizai.[kin_tanka]"
                                + ",genshizai.[biko]"
                                + ",genshizai.[cd_maker_hin]"
                                + ",genshizai.[kikan_shomi]"
                                + ",genshizai.[kikan_shomi_kaifu]"
                                + ",genshizai.[kbn_ksys_denso]"
                                + ",genshizai.[flg_mishiyo]"
                                + ",genshizai.[dt_toroku]"
                                + ",genshizai.[dt_henko]"
                                + ",genshizai.[no_kikaku]"
                                + ",genshizai.[nm_kikaku]"
                                + ",genshizai.[nm_seizo]"
                                + ",genshizai.[nm_hanbai]"
                                + ",genshizai.[dt_toroku_kikaku]"
                                + ",genshizai.[nm_bussitsu]"
                                + ",genshizai.[cd_doto_hin]"
                                + ",genshizai.[dt_doto_SET_DATE]"
                                + ",genshizai.[cd_doto_kaisha]"
                                + ",bunrui.[nm_bunrui]"
                                + ",hini.[nm_hini]"
                                + ",tani.[nm_tani]"
                                + ",kura.[nm_kura]"
                                + ",zei.[nm_zei]"
                                + ",niuke.[nm_niuke]"
                                + ",ssname21.[nm_kino] AS nm_kino21"
                                + ",ssname22.[nm_kino] AS nm_kino22"
                                + ",ssname23.[nm_kino] AS nm_kino23"
                                + ",ssname23_kaifu.[nm_kino] AS nm_kino23_kaifu"
                                + ",tanto.nm_tanto"

                           + " FROM ma_genshizai genshizai"
                           + " LEFT JOIN ma_hini hini"
                           + " ON genshizai.cd_hini = hini.cd_hini AND hini.flg_sakujyo = 0 AND hini.flg_mishiyo = 0"
                           + " LEFT JOIN ma_bunrui bunrui"
                           + " ON genshizai.cd_bunrui = bunrui.cd_bunrui AND bunrui.kbn_hin = 1 AND bunrui.flg_sakujyo = 0 AND bunrui.flg_mishiyo = 0"
                           + " LEFT JOIN ma_tani tani"
                           + " ON genshizai.cd_tani_nonyu = tani.cd_tani AND tani.flg_mishiyo = 0"
                           + " LEFT JOIN ma_kura kura"
                           + " ON genshizai.cd_kura = kura.cd_kura AND kura.flg_sakujyo = 0 AND kura.flg_mishiyo = 0"
                           + " LEFT JOIN ma_zei zei"
                           + " ON genshizai.kbn_zei = zei.kbn_zei"
                           + " LEFT JOIN ma_niuke niuke"
                           + " ON genshizai.cd_niuke = niuke.cd_niuke AND niuke.flg_sakujyo = 0 AND niuke.flg_mishiyo = 0"
                           + " LEFT JOIN SS_ma_name ssname21"
                           + " ON genshizai.kbn_jyotai = ssname21.cd_code AND ssname21.cd_bunrui = 21"
                           + " LEFT JOIN SS_ma_name ssname22"
                           + " ON genshizai.cd_hini_gmat = ssname22.cd_code AND ssname22.cd_bunrui = 22"
                           + " LEFT JOIN SS_ma_name ssname23"
                           + " ON genshizai.tani_shomi = ssname23.cd_code AND ssname23.cd_bunrui = 23"
                           + " LEFT JOIN SS_ma_name ssname23_kaifu"
                           + " ON genshizai.tani_shomi_kaifu = ssname23_kaifu.cd_code AND ssname23_kaifu.cd_bunrui = 23"
                           + " LEFT JOIN ma_tanto tanto"
                           + " ON tanto.cd_tanto = genshizai.cd_koshin AND tanto.flg_sakujyo = 0 AND tanto.flg_mishiyo = 0 "

                           + " WHERE cd_hin =  @cd_hin AND genshizai.flg_sakujyo = 0";

                results = context.Database.SqlQuery<ma_genshizai_fp>(query, new SqlParameter("@cd_hin", Conditions.cd_hin)).FirstOrDefault();
                //return results;
            }

            // TODO: 上記実装を行った後に下の行は削除します
            return results;
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class ma_genshizai_fp
    {
        public string cd_hin { get; set; }
        public string nm_hin { get; set; }
        public string nm_hin_r { get; set; }
        public Nullable<double> nisugata_qty { get; set; }
        public string nisugata_hyoji { get; set; }
        public int su_iri { get; set; }
        public double qty { get; set; }
        public string cd_tani_shiyo { get; set; }
        public string cd_shukei { get; set; }
        public double budomari { get; set; }
        public double hijyu { get; set; }
        public string size_hachu { get; set; }
        public Nullable<int> su_leadtime { get; set; }
        public Nullable<double> su_saitei { get; set; }
        public Nullable<double> kin_tanka { get; set; }
        public string biko { get; set; }
        public string cd_maker_hin { get; set; }
        public Nullable<int> kikan_shomi { get; set; }
        public Nullable<int> kikan_shomi_kaifu { get; set; }
        public string kbn_ksys_denso { get; set; }
        public bool flg_mishiyo { get; set; }
        public System.DateTime dt_toroku { get; set; }
        public System.DateTime dt_henko { get; set; }
        public string no_kikaku { get; set; }
        public string nm_kikaku { get; set; }
        public string nm_seizo { get; set; }
        public string nm_hanbai { get; set; }
        public Nullable<System.DateTime> dt_toroku_kikaku { get; set; }
        public string nm_bussitsu { get; set; }
        public string cd_doto_hin { get; set; }
        public Nullable<System.DateTime> dt_doto_SET_DATE { get; set; }
        public string cd_doto_kaisha { get; set; }
        public string nm_bunrui { get; set; }
        public string nm_hini { get; set; }
        public string nm_tani { get; set; }
        public string nm_kura { get; set; }
        public string nm_zei { get; set; }
        public string nm_niuke { get; set; }
        public string nm_kino21 { get; set; }
        public string nm_kino22 { get; set; }
        public string nm_kino23 { get; set; }
        public string nm_kino23_kaifu { get; set; }
        public string nm_tanto { get; set; }
    }

    public class GenryoMasterSanshoRequest
    {
        public string cd_hin { get; set; }
        public short? cd_kaisha { get; set; }
        public short? cd_kojyo { get; set; }
    }

    #endregion
}
