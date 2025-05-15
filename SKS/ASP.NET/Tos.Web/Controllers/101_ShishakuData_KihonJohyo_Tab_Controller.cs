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
    public class ShishakuData_KihonJohyo_Tab_Controller : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// get data for all combobox
        /// </summary>
        /// <param name="paraSearch"></param>
        /// <returns>LoadMasterDataRespon</returns>
        public DataMasterKihonJohyo GetMasterData([FromUri] paramLoadMaster paraSearch)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                DataMasterKihonJohyo Results = new DataMasterKihonJohyo();
                var literalMaster = context.ma_literal;

                //販責会社
                Results.Hanbai_kaisha = (from m in context.ma_kaisha
                                         select new tanto_kaisha
                                         {
                                             cd_kaisha = m.cd_kaisha,
                                             nm_kaisha = m.nm_kaisha
                                         }).OrderBy(x => x.cd_kaisha).ToList();

                //担当工場
                //※権限「システム管理者」の場合
                Results.Tanto_kojo = context.ma_busho.Where(x => x.fg_hyoji == paraSearch.busho_hyoji).OrderBy(x => x.cd_busho).ToList();

                //※表示文字変換		
                //研究所（部署マスタ．会社コード= 1 AND 部署コード = 1）	
                foreach (var kojo in Results.Tanto_kojo)
                {
                    if (kojo.cd_kaisha == paraSearch.cd_kaisha_change_name && kojo.cd_busho == paraSearch.cd_busho_change_name)
                    {
                        kojo.nm_busho = paraSearch.name_change;
                    }
                }

                //2019-09-20 : Bug #15396 : 試作データの基本情報
                //「paraSearch.cd_kengen ==  Properties.Settings.Default.cd_kengen_system_admin」から「paraSearch.cd_kengen > 0」 までする
                //担当会社  
                if (paraSearch.cd_kengen > 0)
                {
                    //※権限「システム管理者」の場合
                    Results.Tanto_kaisha = Results.Hanbai_kaisha;

                }
                else
                {
                    //上記以外の場合			
                    //会社マスタ.会社コード = ログイン社員の担当製造会社マスタ.担当製造会社CD	
                    Results.Tanto_kaisha = (from tanto in context.ma_tantokaisya.Where(x => x.id_user == paraSearch.EmployeeCD)
                                            from kaisha in context.ma_kaisha.Where(x => x.cd_kaisha == tanto.cd_tantokaisha).DefaultIfEmpty()
                                            select new tanto_kaisha
                                            {
                                                cd_kaisha = (short)tanto.cd_tantokaisha,
                                                nm_kaisha = kaisha.nm_kaisha
                                            }).OrderBy(x => x.cd_kaisha).ToList();

                }

                //2019-09-20 : START : Bug #15396 : 試作データの基本情報
                //「paraSearch.cd_kengen ==  Properties.Settings.Default.cd_kengen_system_admin」から「paraSearch.cd_kengen > 0」 までする
                Results.K_ikatuhyouzi = literalMaster.Where(x => x.cd_category == paraSearch.K_ikatuhyouzi && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_jyanru = literalMaster.Where(x => x.cd_category == paraSearch.K_jyanru && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_yuza = literalMaster.Where(x => x.cd_category == paraSearch.K_yuza && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_tokucyogenryo = literalMaster.Where(x => x.cd_category == paraSearch.K_tokucyogenryo && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_yoto = literalMaster.Where(x => x.cd_category == paraSearch.K_yoto && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_kakakutai = literalMaster.Where(x => x.cd_category == paraSearch.K_kakakutai && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_syubetu = literalMaster.Where(x => x.cd_category == paraSearch.K_syubetu && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_shosu = literalMaster.Where(x => x.cd_category == paraSearch.K_shosu && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_seizohoho = literalMaster.Where(x => x.cd_category == paraSearch.K_seizohoho && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_jyutenhoho = literalMaster.Where(x => x.cd_category == paraSearch.K_jyutenhoho && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_sakinhoho = literalMaster.Where(x => x.cd_category == paraSearch.K_sakinhoho && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                //2019-09-16 : START : Request #15376 基本情報タブ　容器・包材 : カテゴリマスタの容器・包材を出すよう修正をお願い致します。
                //Results.K_yoki = context.ma_yoki_hoso.OrderBy(x => x.cd_yoki_hoso).ToList();
                Results.K_yoki = literalMaster.Where(x => x.cd_category == paraSearch.K_yoki && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                //2019-09-16 : END : Request #15376 基本情報タブ　容器・包材 : カテゴリマスタの容器・包材を出すよう修正をお願い致します。
                Results.K_tani = literalMaster.Where(x => x.cd_category == paraSearch.K_tani && x.value1 != null).OrderBy(x => x.no_sort).ToList();
                Results.K_nisugata = literalMaster.Where(x => x.cd_category == paraSearch.K_nisugata && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_toriatukaiondo = literalMaster.Where(x => x.cd_category == paraSearch.K_toriatukaiondo && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                Results.K_syomikikan = literalMaster.Where(x => x.cd_category == paraSearch.K_syomikikan && (paraSearch.cd_kengen > 0 || x.cd_group == paraSearch.cd_group || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();

                //2019-09-20 : END : Bug #15396 : 試作データの基本情報
                return Results;
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
    public class paramLoadMaster
    {
        //2019-09-16 : START : Request #15376 基本情報タブ　容器・包材 : カテゴリマスタの容器・包材を出すよう修正をお願い致します。
        public string K_yoki { get; set; }
        //2019-09-16 : END : Request #15376 基本情報タブ　容器・包材 : カテゴリマスタの容器・包材を出すよう修正をお願い致します。
        public string K_ikatuhyouzi { get; set; }
        public string K_jyanru { get; set; }
        public string K_yuza { get; set; }
        public string K_tokucyogenryo { get; set; }
        public string K_yoto { get; set; }
        public string K_kakakutai { get; set; }
        public string K_syubetu { get; set; }
        public string K_shosu { get; set; }
        public string K_seizohoho { get; set; }
        public string K_jyutenhoho { get; set; }
        public string K_sakinhoho { get; set; }
        public string K_tani { get; set; }
        public string K_nisugata { get; set; }
        public string K_toriatukaiondo { get; set; }
        public string K_syomikikan { get; set; }
        public int busho_hyoji { get; set; }
        public int cd_kaisha { get; set; }
        public short cd_kengen { get; set; }
        public decimal EmployeeCD { get; set; }
        public int cd_busho { get; set; }
        public short cd_group { get; set; }
        public int cd_kaisha_change_name { get; set; }//会社コード
        public int cd_busho_change_name { get; set; }//工場コード
        public string name_change { get; set; }//新しい工場名
    }

    /// <summary>
    /// result load master data tab kihon johyo
    /// </summary>
    public class DataMasterKihonJohyo
    {
        public List<ma_literal> K_ikatuhyouzi { get; set; }
        public List<ma_literal> K_jyanru { get; set; }
        public List<ma_literal> K_yuza { get; set; }
        public List<ma_literal> K_tokucyogenryo { get; set; }
        public List<ma_literal> K_yoto { get; set; }
        public List<ma_literal> K_kakakutai { get; set; }
        public List<ma_literal> K_syubetu { get; set; }
        public List<ma_literal> K_shosu { get; set; }
        public List<ma_literal> K_seizohoho { get; set; }
        public List<ma_literal> K_jyutenhoho { get; set; }
        public List<ma_literal> K_sakinhoho { get; set; }
        //2019-09-16 : START : Request #15376 基本情報タブ　容器・包材 : カテゴリマスタの容器・包材を出すよう修正をお願い致します。
        //public List<ma_yoki_hoso> K_yoki { get; set; }
        public List<ma_literal> K_yoki { get; set; }
        //2019-09-16 : END : Request #15376 基本情報タブ　容器・包材 : カテゴリマスタの容器・包材を出すよう修正をお願い致します。
        public List<ma_literal> K_tani { get; set; }
        public List<ma_literal> K_nisugata { get; set; }
        public List<ma_literal> K_toriatukaiondo { get; set; }
        public List<ma_literal> K_syomikikan { get; set; }
        public List<tanto_kaisha> Hanbai_kaisha { get; set; }
        public List<tanto_kaisha> Tanto_kaisha { get; set; }
        public List<ma_busho> Tanto_kojo { get; set; }
    }

    /// <summary>
    /// 担当会社
    /// </summary>
    public class tanto_kaisha
    {
        public short cd_kaisha { get; set; }
        public string nm_kaisha { get; set; }
    }

    /// <summary>
    /// 
    /// </summary>
    public class KihonJohyoSearchResponse
    {
        public KihonJohyoSearchResponse()
        {
            this.genryo = new List<tr_genryo>();
        }
        public List<tr_genryo> genryo { get; set; }
    }
    #endregion
}
