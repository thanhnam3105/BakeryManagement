using System.Collections.Generic;
using System.Linq;
//using System.Web.Http.OData;
using System.Reflection;
using System.Web.Http;
//using System.Web.Http.OData.Query;
// TODO:DbContextの名前空間を指定します。
using Tos.Web.Data;

namespace Tos.Web.Controllers
{

    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class ShishakuData_HaigoHyo_Tab_Controller : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡されたheaderのキー項目をもとにheaderとdetailの情報を取得します。
        /// </summary>
        /// <param name="no_seq">headerのキー項目</param>
        /// <returns>ShisakuhyoTabSearchResponse</returns>
        public ShisakuhyoTabSearchResponse Get([FromUri] paramSearchHaigo paraSearch)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                ShisakuhyoTabSearchResponse SearchResponse = new ShisakuhyoTabSearchResponse();
                //get data shisaku
                SearchResponse.sampleItemOriginal = context.tr_shisaku.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi).OrderBy(x => x.sort_shisaku).ToList();
                SearchResponse.sampleListOriginal = context.tr_shisaku_list.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi).OrderBy(x => x.seq_shisaku).ToList();

                //create list column sample
                var list = new List<Dictionary<string, object>>();
                var dict = new Dictionary<string, object>();

                foreach (var sampleItem in SearchResponse.sampleItemOriginal)
                {
                    //get data quantity with seq_shisaku
                    //get property name with class tr_shisaku
                    var shisakuListData = SearchResponse.sampleListOriginal.Where(x => x.seq_shisaku == sampleItem.seq_shisaku);
                    var properties = typeof(tr_shisaku).GetProperties(BindingFlags.Public | BindingFlags.Instance);

                    //reset property sample 
                    dict = new Dictionary<string, object>();

                    //create new property for item column sample with list tr_shisaku 
                    foreach (var items in properties)
                    {
                        PropertyInfo property = sampleItem.GetType().GetProperty(items.Name);
                        var value = property.GetValue(sampleItem, null);

                        dict[items.Name] = value;
                    }

                    //create new property with list tr_shisaku_list 
                    short cd_kotei = -1;
                    foreach (var item_list in shisakuListData)
                    {
                        string quantity;
                        if (item_list.quantity == null)
                        {
                            quantity = "";
                        }
                        else
                        {
                            quantity = ((decimal)item_list.quantity).ToString("n" + paraSearch.keta_shosu);
                        }

                        dict["quantity_" + item_list.cd_kotei + "_" + item_list.seq_kotei + "_" + item_list.seq_shisaku] = quantity;

                        if (item_list.cd_kotei != cd_kotei)
                        {
                            cd_kotei = item_list.cd_kotei;
                            //1~n 工程（ｇ）
                            dict["total_kotei" + item_list.cd_kotei] = shisakuListData.Where(x => x.cd_shain == item_list.cd_shain && x.nen == item_list.nen && x.no_oi == item_list.no_oi && x.seq_shisaku == item_list.seq_shisaku && x.cd_kotei == item_list.cd_kotei).Select(x => x.quantity).Sum();
                            dict["total_kotei" + item_list.cd_kotei] = ((decimal)dict["total_kotei" + item_list.cd_kotei]).ToString("n" + paraSearch.keta_shosu);
                            //1~n工程仕上重量（ｇ）
                            dict["total_finish_weight" + item_list.cd_kotei] = shisakuListData.Where(x => x.cd_shain == item_list.cd_shain && x.nen == item_list.nen && x.no_oi == item_list.no_oi && x.seq_shisaku == item_list.seq_shisaku && x.cd_kotei == item_list.cd_kotei).Select(x => x.juryo_shiagari_seq).FirstOrDefault();
                            if (dict["total_finish_weight" + item_list.cd_kotei] != null)
                            {
                                dict["total_finish_weight" + item_list.cd_kotei] = ((decimal)dict["total_finish_weight" + item_list.cd_kotei]).ToString("n" + 4);
                            }
                            //合計仕上重量（ｇ）
                            dict["juryo_shiagari_seq" + item_list.cd_kotei] = ((decimal)(item_list.juryo_shiagari_seq ?? 0)).ToString("n" + 4);
                        }
                    }

                    //合計重量（ｇ）
                    dict["total_weight"] = shisakuListData.Select(x => x.quantity).Sum();
                    dict["total_weight"] = ((decimal)dict["total_weight"]).ToString("n" + paraSearch.keta_shosu);
                    //get data genryo
                    var genryo = context.tr_genryo.Where(x => x.cd_shain == sampleItem.cd_shain && x.nen == sampleItem.nen && x.no_oi == sampleItem.no_oi && x.seq_shisaku == sampleItem.seq_shisaku).FirstOrDefault();
                    if (genryo != null)
                    {
                        //原料費（ｋｇ）
                        dict["genryohi"] = genryo.genryohi;
                        //原料費（１個）
                        dict["genryohi1"] = genryo.genryohi1;
                    }

                    //選択された試作列の試作SEQコピー
                    dict["flg_isKopiLast"] = false;
                    if (sampleItem.seq_shisaku == paraSearch.seq_shisaku)
                    {
                        dict["flg_isKopiLast"] = true;
                    }

                    //他列の計算で使用されている場合、
                    dict["siki_keisan_head"] = dict["siki_keisan"];

                    list.Add(dict);
                }

                SearchResponse.sampleItem = list;
                SearchResponse.haigoItem = context.sp_shohinkaihatsu_search_101_haigo_list(paraSearch.cd_shain, paraSearch.nen, paraSearch.no_oi, paraSearch.tanka_hyoujigaisha, paraSearch.hyoji1, paraSearch.hyoji9, paraSearch.daihyo_kaisha, paraSearch.daihyo_kojo).ToList();

                // TODO: 上記実装を行った後に下の行は削除します
                return SearchResponse;
            }
        }

        /// <summary>
        /// 分析マスタの最新情報に更新。
        /// </summary>
        /// <param name="value"></param>
        /// <returns>ma_genryo</returns>
        public List<ma_genryo> Post_SaikinJoho([FromBody] List<GenryoList> value)
        {
            // TODO:header情報を管理しているDbContextとheaderの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                var dataGenryo = (from c in value
                                  join p in context.ma_genryo on new { c.cd_genryo, c.cd_kaisha } equals new { p.cd_genryo, p.cd_kaisha }
                                  select p).ToList();

                return dataGenryo;
            }
        }


        /// <summary>
        /// 原料コードロストフォーカス時
        /// </summary>
        /// <param name="value"></param>
        /// <returns>FocusGenryo</returns>
        public FocusGenryoResult Post_GenryoFocus([FromBody] PostGenryoParam value)
        {
            // TODO:header情報を管理しているDbContextとheaderの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                var dataGenryo = (from c in context.ma_genryo.Where(x => x.cd_genryo == value.cd_genryo)
                                  join pet in context.ma_genryokojo on new { c.cd_kaisha, c.cd_genryo } equals new { pet.cd_kaisha, pet.cd_genryo } into gj
                                  from subpet in gj.DefaultIfEmpty()
                                  select new FocusGenryo
                                  {
                                      cd_kaisha = c.cd_kaisha,
                                      cd_genryo = c.cd_genryo,
                                      cd_genryo_gry = c.cd_genryo,
                                      nm_genryo = subpet.nm_genryo,
                                      cd_busho = subpet.cd_busho,
                                      ritu_sakusan = c.ritu_sakusan,
                                      ritu_shokuen = c.ritu_shokuen,
                                      ritu_sousan = c.ritu_sousan,
                                      ritu_abura = c.ritu_abura,
                                      tanka = subpet.tanka,
                                      budomari = subpet.budomari,
                                      budomari_gry = null,
                                      ritu_msg = c.ritu_msg,
                                      kbn_haishi = c.kbn_haishi,
                                      gensanchi = c.flg_gensanchi
                                  }).ToList();

                FocusGenryoResult result = new FocusGenryoResult();
                result.data = new List<FocusGenryo>();
                result.isShowDialog = false;

                int cd_busho = 0;
                if (value.cd_genryo.IndexOf('N') != 0)
                {
                    cd_busho = value.cd_busho;
                }

                var bushoKaisha = dataGenryo.Where(x => x.cd_kaisha == value.cd_kaisha && x.cd_busho == cd_busho).FirstOrDefault();
                if (bushoKaisha != null)
                {
                    bushoKaisha.budomari_gry = bushoKaisha.budomari;
                    result.data.Add(bushoKaisha);
                }
                else
                {
                    var noneBusho = dataGenryo.Where(x => x.cd_kaisha == value.cd_kaisha).OrderBy(x => x.cd_busho);
                    if (noneBusho.Count() > 0)
                    {
                        var onlyKaisha = noneBusho.First();
                        // 基本情報タブで選択した工場が代表工場（部署マスタ.会社コード= CD_DAIHYO_KAISHA  AND 部署コード = CD_DAIHYO_KOJO）の場合
                        // 単価：検索結果で単価の一番高いものを取得	
                        // 歩留：検索結果で歩留の一番低いものを取得	
                        if (value.flg_daihyo == true)
                        {
                            onlyKaisha = noneBusho.OrderByDescending(x => x.tanka).First();
                            //onlyKaisha.budomari_gry = onlyKaisha.budomari;
                            onlyKaisha.budomari = noneBusho.Min(x => x.budomari);
                        }

                        result.data.Add(onlyKaisha);
                    }
                    else
                    {
                        var noneKaishaBusho = dataGenryo.OrderBy(x => x.cd_kaisha).ThenBy(x => x.cd_busho).FirstOrDefault();
                        if (noneKaishaBusho != null)
                        {
                            result.data.Add(noneKaishaBusho);
                        }
                    }
                }

                //原料一覧画面起動
                if (result.data.Count() <= 0 && value.cd_genryo.IndexOf('N') == 0 && value.cd_genryo.Length < 6)
                {
                    bool isShowDialog = (context.ma_genryokojo.Where(x=>x.cd_genryo.IndexOf(value.cd_genryo) == 0).FirstOrDefault() != null);
                    result.isShowDialog = isShowDialog;
                }

                return result;
            }
        }

        /// <summary>
        /// 担当会社／担当工場変更時	
        /// </summary>
        /// <param name="value"></param>
        /// <returns>sp_shohinkaihatsu_search_101_change_kojo_event_Result</returns>
        public List<sp_shohinkaihatsu_search_101_change_kojo_event_Result> Post_GenryoKojo([FromBody] PostGenryoParam value)
        {
            // TODO:header情報を管理しているDbContextとheaderの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                string str_genryo = string.Join(",", value.genryo_group.Select(x => x.cd_genryo).ToArray());
                var dataGenryo = context.sp_shohinkaihatsu_search_101_change_kojo_event(str_genryo).ToList();

                List<sp_shohinkaihatsu_search_101_change_kojo_event_Result> result = new List<sp_shohinkaihatsu_search_101_change_kojo_event_Result>();
                foreach (var item in value.genryo_group)
                {
                    int cd_busho = 0;
                    if (item.cd_genryo.IndexOf('N') != 0)
                    {
                        cd_busho = value.cd_busho;
                    }

                    var bushoKaisha = dataGenryo.Where(x => x.cd_kaisha == value.cd_kaisha && x.cd_genryo == item.cd_genryo && x.cd_busho == cd_busho).FirstOrDefault();
                    if (bushoKaisha != null)
                    {
                        bushoKaisha.budomari_gry = bushoKaisha.budomari;
                        result.Add(bushoKaisha);
                    }
                    else
                    {
                        var noneBusho = dataGenryo.Where(x => x.cd_kaisha == value.cd_kaisha && x.cd_genryo == item.cd_genryo).OrderBy(x => x.cd_busho);
                        if (noneBusho.Count() > 0)
                        {
                            var onlyKaisha = noneBusho.First();
                            // 基本情報タブで選択した工場が代表工場（部署マスタ.会社コード= CD_DAIHYO_KAISHA  AND 部署コード = CD_DAIHYO_KOJO）の場合
                            // 単価：検索結果で単価の一番高いものを取得	
                            // 歩留：検索結果で歩留の一番低いものを取得	
                            if (value.flg_daihyo == true)
                            {
                                onlyKaisha = noneBusho.OrderByDescending(x => x.tanka).ThenBy(x => x.cd_busho).First();
                                //onlyKaisha.budomari_gry = onlyKaisha.budomari;
                                onlyKaisha.budomari = noneBusho.Min(x => x.budomari);
                            }
                            else
                            {
                                if (item.cd_genryo.IndexOf('N') != 0)
                                {
                                    onlyKaisha.nm_genryo = noneBusho.OrderBy(x => x.nm_genryo).First().nm_genryo;
                                }
                            }

                            result.Add(onlyKaisha);
                        }
                        else
                        {
                            var noneKaishaBusho = dataGenryo.Where(x => x.cd_genryo == item.cd_genryo).OrderBy(x => x.cd_kaisha).ThenBy(x => x.cd_busho).FirstOrDefault();
                            if (noneKaishaBusho != null)
                            {
                                result.Add(noneKaishaBusho);
                            }
                        }
                    }
                }
                return result;
            }
        }
        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// Shisakuhyo tab search response
    /// </summary>
    public class ShisakuhyoTabSearchResponse
    {
        public IEnumerable<sp_shohinkaihatsu_search_101_haigo_list_Result> haigoItem { get; set; }
        public List<Dictionary<string, object>> sampleItem { get; set; }
        public List<tr_shisaku> sampleItemOriginal { get; set; }
        public List<tr_shisaku_list> sampleListOriginal { get; set; }
    }

    public class GenryoList
    {
        public string cd_genryo { get; set; }
        public int cd_kaisha { get; set; }
    }

    /// <summary>
    // param get genryo
    /// </summary>
    public class PostGenryoParam
    {
        public int cd_kaisha { get; set; }
        public List<GenryoList> genryo_group { get; set; }
        public string cd_genryo { get; set; }
        public int cd_busho { get; set; }
        public bool flg_daihyo { get; set; }
    }

    /// <summary>
    /// param Search
    /// </summary>
    public class paramSearchHaigo
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
        public string tanka_hyoujigaisha { get; set; }
        public int hyoji1 { get; set; }
        public int hyoji9 { get; set; }
        public int daihyo_kaisha { get; set; }
        public int daihyo_kojo { get; set; }
        public int keta_shosu { get; set; }
        public short seq_shisaku { get; set; }
        public int cd_kaisha_shisakuhin { get; set; }
        public int cd_busho_shisakuhin { get; set; }
    }

    /// <summary>
    /// infomation genryo from ma_genryokojo and ma_genryo
    /// </summary>
    public class FocusGenryo
    {
        public long id { get; set; }
        public int cd_kaisha { get; set; }
        public string cd_genryo { get; set; }
        public string cd_genryo_gry { get; set; }
        public string nm_genryo { get; set; }
        public int? cd_busho { get; set; }
        public decimal? ritu_sakusan { get; set; }
        public decimal? ritu_shokuen { get; set; }
        public decimal? ritu_sousan { get; set; }
        public decimal? ritu_abura { get; set; }
        public decimal? tanka { get; set; }
        public decimal? budomari { get; set; }
        public decimal? budomari_gry { get; set; }
        public decimal? ritu_msg { get; set; }
        public short kbn_haishi { get; set; }
        public short? gensanchi { get; set; }
    }

    /// <summary>
    /// result lot fosus
    /// </summary>
    public class FocusGenryoResult {
        public List<FocusGenryo> data { get; set; }
        public bool isShowDialog { get; set; }
    }

    #endregion
}
