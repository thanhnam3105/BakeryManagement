using System.Collections.Generic;
using System.Linq;
using System.Web.Http;
//using System.Web.Http.OData.Query;
// TODO:DbContextの名前空間を指定します。
using Tos.Web.Data;
//using System.Web.Http.OData;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class _151_ShishakuData_KihonJohyo_Tab_Controller : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// get data for all combobox
        /// </summary>
        /// <param name="paraSearch"></param>
        /// <returns>LoadMasterDataRespon</returns>
        public DataMasterKihonJohyo_151 GetMasterData([FromUri] paramLoadMaster_151 paraSearch)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                DataMasterKihonJohyo_151 Results = new DataMasterKihonJohyo_151();
                var literalMaster = context.ma_literal;

                //販責会社
                //Results.Hanbai_kaisha = (from m in context.ma_kaisha
                //                         select new tanto_kaisha_151
                //                         {
                //                             cd_kaisha = m.cd_kaisha,
                //                             nm_kaisha = m.nm_kaisha
                //                         }).OrderBy(x => x.cd_kaisha).ToList();

                //担当工場
                //※権限「システム管理者」の場合
                Results.Tanto_kojo = context.ma_busho.Where(x => x.fg_hyoji == paraSearch.busho_hyoji && x.cd_kaisha == paraSearch.cd_kaisha).OrderBy(x => x.cd_busho).ToList();

                //※表示文字変換		
                //研究所（部署マスタ．会社コード= 1 AND 部署コード = 1）	
                //foreach (var kojo in Results.Tanto_kojo)
                //{
                //    if (kojo.cd_kaisha == paraSearch.cd_kaisha_change_name && kojo.cd_busho == paraSearch.cd_busho_change_name)
                //    {
                //        kojo.nm_busho = paraSearch.name_change;
                //    }
                //}

                //2019-09-20 : Bug #15396 : 試作データの基本情報
                //「paraSearch.cd_kengen ==  Properties.Settings.Default.cd_kengen_system_admin」から「paraSearch.cd_kengen > 0」 までする
                //担当会社  
                //if (paraSearch.cd_kengen > 0)
                //{
                //    //※権限「システム管理者」の場合
                //    Results.Tanto_kaisha = Results.Hanbai_kaisha;

                //}
                //else
                //{
                //    //上記以外の場合			
                //    //会社マスタ.会社コード = ログイン社員の担当製造会社マスタ.担当製造会社CD	
                //    //Results.Tanto_kaisha = (from tanto in context.ma_tantokaisya.Where(x => x.id_user == paraSearch.EmployeeCD)
                //    //                        from kaisha in context.ma_kaisha.Where(x => x.cd_kaisha == tanto.cd_tantokaisha).DefaultIfEmpty()
                //    //                        select new tanto_kaisha_151
                //    //                        {
                //    //                            cd_kaisha = (short)tanto.cd_tantokaisha,
                //    //                            nm_kaisha = kaisha.nm_kaisha
                //    //                        }).OrderBy(x => x.cd_kaisha).ToList();

                //}
                Results.K_syubetu = literalMaster.Where(x => x.cd_category == paraSearch.K_syubetu && (x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_shosu = literalMaster.Where(x => x.cd_category == paraSearch.K_shosu && (x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.B_sakinhoho = literalMaster.Where(x => x.cd_category == paraSearch.B_sakinhoho && (x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.B_yoki = literalMaster.Where(x => x.cd_category == paraSearch.B_yoki && (x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_syomikikan = literalMaster.Where(x => x.cd_category == paraSearch.K_syomikikan && (x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.B_toriatukaiondo = literalMaster.Where(x => x.cd_category == paraSearch.B_toriatukaiondo && (x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.B_naiyobunrui = literalMaster.Where(x => x.cd_category == paraSearch.B_naiyobunrui && (x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.B_brand = literalMaster.Where(x => x.cd_category == paraSearch.B_brand && (x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                //Results.B_seizoline = literalMaster.Where(x => x.cd_category == paraSearch.B_seizoline && (x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.B_tani = literalMaster.Where(x => x.cd_category == paraSearch.B_tani && (x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                var kaisha = context.ma_kaisha.Where(x => x.cd_kaisha == paraSearch.cd_kaisha).FirstOrDefault();
                if (kaisha != null) {
                    Results.nm_kaisha = kaisha.nm_kaisha;
                }
                return Results;
            }
        }

        /// <summary>
        /// Get list of seizo line base on cd_kojo
        /// </summary>
        /// <param name="paraSearch"></param>
        /// <returns></returns>
        [HttpGet]
        public List<ma_literal> GetSeizoLine([FromUri] paramLoadMaster_151 paraSearch)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                DataMasterKihonJohyo_151 Results = new DataMasterKihonJohyo_151();
                return context.ma_literal.Where(x => x.cd_category == paraSearch.B_seizoline && (x.cd_group == paraSearch.cd_group || x.cd_group == null) && x.value1 == paraSearch.cd_kojo).OrderBy(x => x.no_sort).ToList();
            }
        }

        ///// <summary>
        ///// パラメータで受け渡されたheaderのキー項目をもとにheaderとdetailの情報を取得します。
        ///// </summary>
        ///// <param name="no_seq">headerのキー項目</param>
        ///// <returns>KihonJohyoSearchResponse</returns>
        //public KihonJohyoSearchResponse Get([FromUri] paramSearch paraSearch)
        //{

        //    //return sampleItem;
        //    // TODO:header情報を管理しているDbContextとheader,detailの型を指定します。

        //    using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
        //    {
        //        context.Configuration.ProxyCreationEnabled = false;

        //        KihonJohyoSearchResponse SearchResponse = new KihonJohyoSearchResponse();
        //        //get data shisaku
        //        SearchResponse.genryo = context.tr_genryo.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi).OrderBy(x => x.seq_shisaku).ToList();

        //        // TODO: 上記実装を行った後に下の行は削除します
        //        return SearchResponse;
        //    }
        //}
        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"


    /// <summary>
    /// param get all data combobox tab kihon johyo
    /// </summary>
    public class paramLoadMaster_151
    {
        public string K_syubetu { get; set; }
        public string K_shosu { get; set; }
        public string B_sakinhoho { get; set; }
        public string B_yoki { get; set; }
        public string K_syomikikan { get; set; }
        public string B_toriatukaiondo { get; set; }
        public string B_naiyobunrui { get; set; }
        public string B_brand { get; set; }
        public string B_seizoline { get; set; }
        public string B_tani { get; set; }
        public int busho_hyoji { get; set; }
        public int cd_kaisha { get; set; }
        public short cd_kengen { get; set; }
        public decimal EmployeeCD { get; set; }
        public int cd_busho { get; set; }
        public short cd_group { get; set; }
        public int cd_kaisha_change_name { get; set; }//会社コード
        public int cd_busho_change_name { get; set; }//工場コード
        public string name_change { get; set; }//新しい工場名
        public int? cd_kojo { get; set; }//新しい工場名
    }

    /// <summary>
    /// result load master data tab kihon johyo
    /// </summary>
    public class DataMasterKihonJohyo_151
    {
        public List<ma_literal> K_syubetu { get; set; }
        public List<ma_literal> K_shosu { get; set; }
        public List<ma_literal> B_sakinhoho { get; set; }
        public List<ma_literal> B_yoki { get; set; }
        public List<ma_literal> K_syomikikan { get; set; }
        public List<ma_literal> B_toriatukaiondo { get; set; }
        public List<ma_literal> B_naiyobunrui { get; set; }
        public List<ma_literal> B_brand { get; set; }
        public List<ma_literal> B_seizoline { get; set; }
        public List<ma_literal> B_tani { get; set; }
        public List<ma_busho> Tanto_kojo { get; set; }
        public string nm_kaisha { get; set; }
    }

    /// <summary>
    /// 担当会社
    /// </summary>
    public class tanto_kaisha_151
    {
        public short cd_kaisha { get; set; }
        public string nm_kaisha { get; set; }
    }

    /// <summary>
    /// 
    /// </summary>
    //public class KihonJohyoSearchResponse
    //{
    //    public KihonJohyoSearchResponse()
    //    {
    //        this.genryo = new List<tr_genryo>();
    //    }
    //    public List<tr_genryo> genryo { get; set; }
    //}
    #endregion
}
