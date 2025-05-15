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
    public class _151_ShishakuData_HaigoHyo_Tab_Controller : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡されたheaderのキー項目をもとにheaderとdetailの情報を取得します。
        /// </summary>
        /// <param name="no_seq">headerのキー項目</param>
        /// <returns>ShisakuhyoTabSearchResponse</returns>
        public ShisakuhyoTabSearchResponse_151 Get([FromUri] paramSearchHaigo_151 paraSearch)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                ShisakuhyoTabSearchResponse_151 SearchResponse = new ShisakuhyoTabSearchResponse_151();
                //get data shisaku
                SearchResponse.sampleItemOriginal = context.tr_shisaku_bf.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi).OrderBy(x => x.sort_shisaku).ToList();
                SearchResponse.sampleListOriginal = context.tr_shisaku_list_bf.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi).OrderBy(x => x.seq_shisaku).ToList();

                //create list column sample
                var list = new List<Dictionary<string, object>>();
                var dict = new Dictionary<string, object>();

                foreach (var sampleItem in SearchResponse.sampleItemOriginal)
                {
                    //get data quantity with seq_shisaku
                    //get property name with class tr_shisaku
                    var shisakuListData = SearchResponse.sampleListOriginal.Where(x => x.seq_shisaku == sampleItem.seq_shisaku);
                    var properties = typeof(tr_shisaku_bf).GetProperties(BindingFlags.Public | BindingFlags.Instance);

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
                            //dict["total_kotei" + item_list.cd_kotei] = shisakuListData.Where(x => x.cd_shain == item_list.cd_shain && x.nen == item_list.nen && x.no_oi == item_list.no_oi && x.seq_shisaku == item_list.seq_shisaku && x.cd_kotei == item_list.cd_kotei).Select(x => x.quantity).Sum();
                            //dict["total_kotei" + item_list.cd_kotei] = ((decimal)dict["total_kotei" + item_list.cd_kotei]).ToString("n" + paraSearch.keta_shosu);
                            //1~n工程仕上重量（ｇ）
                            dict["total_finish_weight" + item_list.cd_kotei] = shisakuListData.Where(x => x.cd_shain == item_list.cd_shain && x.nen == item_list.nen && x.no_oi == item_list.no_oi && x.seq_shisaku == item_list.seq_shisaku && x.cd_kotei == item_list.cd_kotei).Select(x => x.juryo_shiagari_seq).FirstOrDefault();
                            if (dict["total_finish_weight" + item_list.cd_kotei] != null)
                            {
                                dict["total_finish_weight" + item_list.cd_kotei] = ((decimal)dict["total_finish_weight" + item_list.cd_kotei]).ToString("n" + paraSearch.keta_shosu);
                            }
                            //合計仕上重量（ｇ）
                            dict["juryo_shiagari_seq" + item_list.cd_kotei] = ((decimal)(item_list.juryo_shiagari_seq ?? 0)).ToString("n" + paraSearch.keta_shosu);
                        }
                    }

                    //合計重量（ｇ）
                    //dict["total_weight"] = shisakuListData.Select(x => x.quantity).Sum();
                    //dict["total_weight"] = ((decimal)dict["total_weight"]).ToString("n" + paraSearch.keta_shosu);
                    //get data genryo
                    //var genryo = context.tr_genryo.Where(x => x.cd_shain == sampleItem.cd_shain && x.nen == sampleItem.nen && x.no_oi == sampleItem.no_oi && x.seq_shisaku == sampleItem.seq_shisaku).FirstOrDefault();
                    //if (genryo != null)
                    //{
                    //    //原料費（ｋｇ）
                    //    dict["genryohi"] = genryo.genryohi;
                    //    //原料費（１個）
                    //    dict["genryohi1"] = genryo.genryohi1;
                    //}

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
                SearchResponse.haigoItem = context.sp_shohinkaihatsu_search_151_haigo_list(paraSearch.cd_shain, paraSearch.nen, paraSearch.no_oi, paraSearch.tanka_hyoujigaisha, paraSearch.hyoji1, paraSearch.hyoji9, paraSearch.daihyo_kaisha, paraSearch.daihyo_kojo).ToList();

                // TODO: 上記実装を行った後に下の行は削除します
                return SearchResponse;
            }
        }

        /// <summary>
        /// Sync total calc param
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        [HttpGet]
        public sp_shohinkaihatsu_search_151_genryo_select_Result GetGenryoData([FromUri] PostGenryoParam_151 value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var grList = context.sp_shohinkaihatsu_search_151_genryo_select(value.cd_kaisha, value.cd_genryo).ToList();
                sp_shohinkaihatsu_search_151_genryo_select_Result result = null;
                int cd_busho = 0;
                if (!isGenryoB(value.cd_genryo))
                {
                    cd_busho = value.cd_busho;
                }
                var bushoKaisha = grList.Where(x => x.cd_kaisha == value.cd_kaisha && x.cd_busho == cd_busho).FirstOrDefault();
                if (bushoKaisha != null)
                {
                    result = bushoKaisha;
                }
                else
                {
                    var noneBusho = grList.Where(x => x.cd_kaisha == value.cd_kaisha).OrderBy(x => x.cd_busho);
                    if (noneBusho.Count() > 0)
                    {
                        var onlyKaisha = noneBusho.First();

                        result = onlyKaisha;
                    }
                    else
                    {
                        var noneKaishaBusho = grList.OrderBy(x => x.cd_kaisha).ThenBy(x => x.cd_busho).FirstOrDefault();
                        if (noneKaishaBusho != null)
                        {
                            result = noneKaishaBusho;
                        }
                    }
                }

                return result;
            }
        }

        /// <summary>
        /// 原料コードロストフォーカス時
        /// </summary>
        /// <param name="value"></param>
        /// <returns>FocusGenryo_151</returns>
        public FocusGenryo_Result_151 Post_GenryoFocus([FromBody] PostGenryoParam_151 value)
        {
            // TODO:header情報を管理しているDbContextとheaderの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                var dataGenryo = (from c in context.ma_genryo.Where(x => x.cd_genryo == value.cd_genryo)
                                  join pet in context.ma_genryokojo on new { c.cd_kaisha, c.cd_genryo } equals new { pet.cd_kaisha, pet.cd_genryo } into gj
                                  from subpet in gj.DefaultIfEmpty()
                                  select new FocusGenryo_151
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
                                      kbn_haishi = c.kbn_haishi
                                  }).ToList();

                FocusGenryo_Result_151 result = new FocusGenryo_Result_151();
                result.data = new List<FocusGenryo_151>();
                result.isShowDialog = false;

                int cd_busho = 0;
                if (!isGenryoB(value.cd_genryo))
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
                if (result.data.Count() <= 0 && isGenryoB(value.cd_genryo) && value.cd_genryo.Length < 6)
                {
                    bool isShowDialog = (context.ma_genryokojo.Where(x => x.cd_genryo.IndexOf(value.cd_genryo) == 0).FirstOrDefault() != null);
                    result.isShowDialog = isShowDialog;
                }

                return result;
            }
        }

        /// <summary>
        /// Check if the cd_genryo is begining with 'B'
        /// </summary>
        /// <param name="cd_genryo"></param>
        /// <returns></returns>
        private bool isGenryoB(string cd_genryo)
        {
            if (cd_genryo == null)
                return false;
            return (cd_genryo.IndexOf('B') == 0);
        }

        /// <summary>
        /// 担当会社／担当工場変更時	
        /// </summary>
        /// <param name="value"></param>
        /// <returns>sp_shohinkaihatsu_search_151_change_kojo_event_Result</returns>
        public List<sp_shohinkaihatsu_search_151_change_kojo_event_Result> Post_GenryoKojo([FromBody] PostGenryoParam_151 value)
        {
             //TODO:header情報を管理しているDbContextとheaderの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                string str_genryo = string.Join(",", value.genryo_group.Select(x => x.cd_genryo).ToArray());
                var dataGenryo = context.sp_shohinkaihatsu_search_151_change_kojo_event(str_genryo).ToList();

                List<sp_shohinkaihatsu_search_151_change_kojo_event_Result> result = new List<sp_shohinkaihatsu_search_151_change_kojo_event_Result>();
                foreach (var item in value.genryo_group)
                {
                    int cd_busho = 0;
                    if (!isGenryoB(item.cd_genryo))
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
                            if (isGenryoB(item.cd_genryo))
                            {
                                onlyKaisha.nm_genryo = noneBusho.OrderBy(x => x.nm_genryo).First().nm_genryo;
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
    public class ShisakuhyoTabSearchResponse_151
    {
        public IEnumerable<sp_shohinkaihatsu_search_151_haigo_list_Result> haigoItem { get; set; }
        public List<Dictionary<string, object>> sampleItem { get; set; }
        public List<tr_shisaku_bf> sampleItemOriginal { get; set; }
        public List<tr_shisaku_list_bf> sampleListOriginal { get; set; }
    }

    public class GenryoList_151
    {
        public string cd_genryo { get; set; }
        public int cd_kaisha { get; set; }
    }

    /// <summary>
    // param get genryo
    /// </summary>
    public class PostGenryoParam_151
    {
        public int cd_kaisha { get; set; }
        public List<GenryoList_151> genryo_group { get; set; }
        public string cd_genryo { get; set; }
        public int cd_busho { get; set; }
        public bool flg_daihyo { get; set; }
    }

    /// <summary>
    /// param Search
    /// </summary>
    public class paramSearchHaigo_151
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
    }

    /// <summary>
    /// infomation genryo from ma_genryokojo and ma_genryo
    /// </summary>
    public class FocusGenryo_151
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
    }

    /// <summary>
    /// result lot fosus
    /// </summary>
    public class FocusGenryo_Result_151
    {
        public List<FocusGenryo_151> data { get; set; }
        public bool isShowDialog { get; set; }
    }

    #endregion
}
