using System;
using System.Collections.Generic;
//using System.Web.Http.OData;
using System.Data.Common;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web.Http;
using System.Web.Script.Serialization;
//using System.Web.Http.OData.Query;
// TODO:DbContextの名前空間を指定します。
using Tos.Web.Data;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class _151_ShishakuDataController : ApiController
    {
        #region "Controllerで公開するAPI"
        public static readonly int mode_update_Addnew = 1;//新規	
        public static readonly int mode_update_Eddit = 2;//詳細	
        public static readonly int mode_update_kopi = 3;//コピー	
        public static readonly int mode_update_Etsuran = 4;//閲覧	
        public static readonly int mode_update_Zencopy = 5;//全コピーモード
        public static readonly int mode_update_Featurecopy = 6;//特徴コピー
        public static readonly int flg_shisanIrai = 1;//原価試算依頼ある。
        //Bug #15389 : START : 製法コピー時
        //付番される配合コードが200000～299999でないと保存できないようになっている
        public static readonly int min_haigo_cd = 200000;//配合コードから。
        public static readonly int max_haigo_cd = 299999;//配合コードまで。
        //Bug #15389 : END : 製法コピー時

        /// <summary>
        /// get data for all combobox 
        /// </summary>
        /// <param name="paraSearch"></param>
        /// <returns>LoadMasterDataRespon</returns>
        public LoadMasterDataRespone_151 GetMasterData([FromUri] paramSearch_151 paraSearch)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                LoadMasterDataRespone_151 Results = new LoadMasterDataRespone_151();
                Results.ShubetsuBango = context.ma_literal.Where(x => x.cd_category == paraSearch.shubetsu_bango).OrderBy(x => x.no_sort).ToList();
                Results.KoteiPatan = context.ma_literal.Where(x => x.cd_category == paraSearch.kotei_patan_AOH).OrderBy(x => x.no_sort).ToList();
                Results.CyuuiBango = context.tr_cyuui_bf.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi).OrderBy(x => x.no_chui).ToList();
                Results.no_chui_max = Results.CyuuiBango.OrderByDescending(x => x.no_chui).Select(x => x.no_chui).FirstOrDefault();
                Results.KoteiZokusei = context.ma_literal.Where(x => x.cd_category == paraSearch.Kotei_zokusei_AOH).OrderBy(x => x.no_sort).ToList();
                Results.TorokuMeisho = context.ma_literal.Where(x => x.cd_category == paraSearch.kbn_meisho).OrderBy(x => x.no_sort).ToList();
                Results.HaigoKyodo = context.ma_literal.Where(x => x.cd_category == paraSearch.kbn_haigo_kyodo).OrderBy(x => x.no_sort).ToList();
                Results.GenryoTani = context.ma_literal.Where(x => x.cd_category == paraSearch.genryo_tani_AOH).OrderBy(x => x.no_sort).ToList();

                return Results;
            }
        }

        /// <summary>
        /// パラメータで受け渡されたheaderのキー項目をもとにheaderの情報を取得します。
        /// </summary>
        /// <param name="no_seq">headerのキー項目</param>
        /// <returns>object new</returns>
        public HttpResponseMessage Get([FromUri] paramSearch_151 paraSearch)
        {
            //原価試算依頼ある。
            int checkIri = 0;
            using (ShohinKaihatsuEntities contextnew = new ShohinKaihatsuEntities())
            {
                checkIri = contextnew.tr_shisaku_bf.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi).Take(1).ToList().Count();
            }

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                //■試作品テーブル 登録
                var shisakuhin = (from shisa in (context.tr_shisakuhin_bf.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi))
                                  from sampl in context.tr_shisaku_bf.Where(x => x.cd_shain == shisa.cd_shain && x.nen == shisa.nen && x.no_oi == shisa.no_oi && x.seq_shisaku == shisa.seq_shisaku).DefaultIfEmpty()
                                  //from user in context_SKS.ma_user_togo.Where(x => x.id_user == shisa.cd_eigyo).DefaultIfEmpty()
                                  select new
                                  {
                                      cd_shain = shisa.cd_shain,
                                      nen = shisa.nen,
                                      no_oi = shisa.no_oi,
                                      no_irai = shisa.no_irai,
                                      nm_hin = shisa.nm_hin,
                                      cd_kaisha = shisa.cd_kaisha,
                                      cd_kojo = shisa.cd_kojo,
                                      cd_shubetu = shisa.cd_shubetu,
                                      no_shubetu = shisa.no_shubetu,
                                      cd_group = shisa.cd_group,
                                      cd_team = shisa.cd_team,
                                      //cd_ikatu = shisa.cd_ikatu,
                                      //cd_genre = shisa.cd_genre,
                                      //cd_user = shisa.cd_user,
                                      //tokuchogenryo = shisa.tokuchogenryo,
                                      //youto = shisa.youto,
                                      //cd_kakaku = shisa.cd_kakaku,
                                      //cd_eigyo = shisa.cd_eigyo,
                                      //nm_eigyo = user.nm_user,
                                      cd_hoho = shisa.cd_hoho,
                                      //cd_juten = shisa.cd_juten,
                                      hoho_sakin = shisa.hoho_sakin,
                                      youki = shisa.youki,
                                      yoryo = shisa.yoryo,
                                      cd_tani = shisa.cd_tani,
                                      su_iri = shisa.su_iri,
                                      cd_ondo = shisa.cd_ondo,
                                      shomikikan = shisa.shomikikan,
                                      shomikikan_tani = shisa.shomikikan_tani,
                                      genka = shisa.genka,
                                      //baika = shisa.baika,
                                      buturyo = shisa.buturyo,
                                      hatubai = shisa.hatubai,
                                      //uriage_k = shisa.uriage_k,
                                      //rieki_k = shisa.rieki_k,
                                      //uriage_h = shisa.uriage_h,
                                      //rieki_h = shisa.rieki_h,
                                      //cd_nisugata = shisa.cd_nisugata,
                                      memo = shisa.memo,
                                      keta_shosu = shisa.keta_shosu,
                                      kbn_haishi = shisa.kbn_haishi,
                                      kbn_haita = shisa.kbn_haita,
                                      seq_shisaku = shisa.seq_shisaku,
                                      sampl.no_seiho1,
                                      memo_shisaku = shisa.memo_shisaku,
                                      flg_chui = shisa.flg_chui,
                                      id_toroku = shisa.id_toroku,
                                      dt_toroku = shisa.dt_toroku,
                                      id_koshin = shisa.id_koshin,
                                      dt_koshin = shisa.dt_koshin,
                                      pt_kotei = shisa.pt_kotei,
                                      //flg_secret = shisa.flg_secret,
                                      cd_hanseki = shisa.cd_hanseki,
                                      ts = shisa.ts,
                                      // AOH - edit
                                      cd_naiyobunrui = shisa.cd_naiyobunrui,
                                      brand = shisa.brand
                                  });

                //製法No（会社コード）
                string seiho_no_shain = string.Empty;
                //製法No（種別）
                string seiho_no_nen = string.Empty;
                //製法No（年）
                string seiho_no_oi = string.Empty;
                //製法No（追番）
                string seiho_no = string.Empty;

                var seiho = shisakuhin.FirstOrDefault();
                if (seiho != null && seiho.no_seiho1 != null)
                {
                    string[] sp_seiho = seiho.no_seiho1.Split('-');
                    seiho_no_shain = sp_seiho[0];
                    seiho_no_nen = sp_seiho[1];
                    seiho_no_oi = sp_seiho[2];
                    seiho_no = sp_seiho[3];
                }

                var results = (from shisa in shisakuhin
                               from koshin in context.ma_user.Where(x => x.id_user == shisa.id_koshin).DefaultIfEmpty()
                               from toroku in context.ma_user.Where(x => x.id_user == shisa.id_toroku).DefaultIfEmpty()
                               from grouppu in context.ma_group.Where(x => x.cd_group == shisa.cd_group).DefaultIfEmpty()
                               from team in context.ma_team.Where(x => x.cd_group == shisa.cd_group && x.cd_team == shisa.cd_team).DefaultIfEmpty()
                               from hanseki in context.ma_kaisha.Where(x => x.cd_kaisha == shisa.cd_hanseki).DefaultIfEmpty()
                               from kaisha in context.ma_kaisha.Where(x => x.cd_kaisha == shisa.cd_kaisha).DefaultIfEmpty()
                               select new shisakuhinResult_151
                               {
                                   cd_shain = shisa.cd_shain,
                                   nen = shisa.nen,
                                   no_oi = shisa.no_oi,
                                   no_irai = shisa.no_irai != null ? shisa.no_irai.Replace(" ", "") : null,
                                   nm_hin = shisa.nm_hin,
                                   cd_kaisha = shisa.cd_kaisha,
                                   cd_kaisha_org = shisa.cd_kaisha,
                                   cd_kojo = shisa.cd_kojo,
                                   cd_shubetu = shisa.cd_shubetu,
                                   no_shubetu = shisa.no_shubetu,
                                   cd_group = shisa.cd_group,
                                   cd_team = shisa.cd_team,
                                   //cd_ikatu = shisa.cd_ikatu,
                                   //cd_genre = shisa.cd_genre,
                                   //cd_user = shisa.cd_user,
                                   //tokuchogenryo = shisa.tokuchogenryo,
                                   //youto = shisa.youto,
                                   //cd_kakaku = shisa.cd_kakaku,
                                   //cd_eigyo = shisa.cd_eigyo,
                                   //nm_eigyo = shisa.nm_eigyo,
                                   cd_hoho = shisa.cd_hoho,
                                   //cd_juten = shisa.cd_juten,
                                   hoho_sakin = shisa.hoho_sakin,
                                   youki = shisa.youki,
                                   yoryo = shisa.yoryo,
                                   cd_tani = shisa.cd_tani,
                                   su_iri = shisa.su_iri,
                                   cd_ondo = shisa.cd_ondo,
                                   shomikikan = shisa.shomikikan,
                                   shomikikan_tani = shisa.shomikikan_tani,
                                   genka = shisa.genka,
                                   //baika = shisa.baika,
                                   buturyo = shisa.buturyo,
                                   hatubai = shisa.hatubai,
                                   //uriage_k = shisa.uriage_k,
                                   //rieki_k = shisa.rieki_k,
                                   //uriage_h = shisa.uriage_h,
                                   //rieki_h = shisa.rieki_h,
                                   //cd_nisugata = shisa.cd_nisugata,
                                   memo = shisa.memo,
                                   keta_shosu = shisa.keta_shosu,
                                   kbn_haishi = shisa.kbn_haishi,
                                   kbn_haita = shisa.kbn_haita, 
                                   seq_shisaku = shisa.seq_shisaku,
                                   memo_shisaku = shisa.memo_shisaku,
                                   flg_chui = shisa.flg_chui,
                                   id_toroku = shisa.id_toroku,
                                   dt_toroku = shisa.dt_toroku,
                                   id_koshin = shisa.id_koshin,
                                   dt_koshin = shisa.dt_koshin,
                                   pt_kotei = shisa.pt_kotei,
                                   //flg_secret = shisa.flg_secret,
                                   cd_hanseki = shisa.cd_hanseki,
                                   ts = shisa.ts,
                                   seiho_no_shain = seiho_no_shain,
                                   seiho_no_nen = seiho_no_nen,
                                   seiho_no_oi = seiho_no_oi,
                                   seiho_no = seiho_no,
                                   nm_group = grouppu.nm_group,
                                   nm_team = team.nm_team,
                                   nm_toroku = toroku.nm_user,// addnew column
                                   nm_koshin = koshin.nm_user,// addnew column
                                   flg_shisanIrai = checkIri,
                                   nm_hanseki = hanseki.nm_kaisha,
                                   nm_kaisha = kaisha.nm_kaisha,
                                   cd_naiyobunrui = shisa.cd_naiyobunrui,
                                   brand = shisa.brand
                               }).FirstOrDefault();

                if (results == null)
                {
                    return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
                }

                return Request.CreateResponse<shisakuhinResult_151>(HttpStatusCode.OK, results);
                //return ;
            }
        }

        /// <summary>
        /// 排他区分を更新する。
        /// Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。
        /// </summary>
        /// <param name="paraSearch"></param>
        /// <returns></returns>
        public HttpResponseMessage Post_haita([FromBody] paramSearch_151 paraSearch)
        {
            if (paraSearch == null)
            {
                return Request.CreateResponse<string>(HttpStatusCode.OK, "OK");
            }

            using (ShohinKaihatsuEntities contextHaita = new ShohinKaihatsuEntities())
            {
                inforUsingDataHaita_151 results = new inforUsingDataHaita_151();
                try
                {
                    // 2020-03-13 : START : Request #16147 複数人で画面起動時の制御
                    //TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
                    var Items = contextHaita.sp_shohinkaihatsu_haita_check_151(paraSearch.cd_shain, paraSearch.nen, paraSearch.no_oi, paraSearch.EmployeeCD, paraSearch.isOpen).ToList();
                    if (Items.Count() < 1)
                    {
                        return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
                    }

                    if (Items[0].msg == "AP0007")
                    {
                        return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
                    }

                    if (Items[0].msg == "OK")
                        {
                            return Request.CreateResponse<string>(HttpStatusCode.OK, "OK");
                        }

                    return Request.CreateResponse<string>(HttpStatusCode.OK, new JavaScriptSerializer().Serialize(Items[0]));

                    ////file shisa
                    //var item = (from x in contextHaita.tr_shisakuhin_bf
                    //            where x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi
                    //            select x).FirstOrDefault();

                    ////data is not exit
                    //if (item == null)
                    //{
                    //    return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "AP0007");
                    //}

                    //if (item.kbn_haita != null && paraSearch.isOpen == true)
                    //{
                    //    //user login for used
                    //    if (item.kbn_haita == paraSearch.EmployeeCD)
                    //    {
                    //        return Request.CreateResponse<string>(HttpStatusCode.OK, "OK");
                    //    }

                    //    //different user login
                    //    string userHaitaKaisha = "";
                    //    string userHaitaBusho = "";
                    //    string userHaitaName = "";

                    //    var userHaita = contextHaita.ma_user_togo.Where(x => x.id_user == item.kbn_haita).FirstOrDefault();

                    //    if (userHaita != null)
                    //    {
                    //        userHaitaName = userHaita.nm_user;
                    //        userHaitaKaisha = contextHaita.ma_kaisha.Where(x => x.cd_kaisha == userHaita.cd_kaisha).Select(x => x.nm_kaisha).FirstOrDefault();
                    //        userHaitaBusho = contextHaita.ma_busho.Where(x => x.cd_kaisha == userHaita.cd_kaisha && x.cd_busho == userHaita.cd_busho).Select(x => x.nm_busho).FirstOrDefault();
                    //    }

                    //    userHaitaKaisha = userHaitaKaisha ?? "";
                    //    userHaitaBusho = userHaitaBusho ?? "";
                    //    userHaitaName = userHaitaName ?? "";

                    //    results.userHaitaKaisha = userHaitaKaisha;
                    //    results.userHaitaBusho = userHaitaBusho;
                    //    results.userHaitaName = userHaitaName;

                    //    return Request.CreateResponse<string>(HttpStatusCode.OK, new JavaScriptSerializer().Serialize(results));
                    //}

                    ////data is freedom
                    //if (item.kbn_haita != null && item.kbn_haita != paraSearch.EmployeeCD)
                    //{
                    //    return Request.CreateResponse<string>(HttpStatusCode.OK, "OK");
                    //}

                    //ChangeSet<tr_shisakuhin_bf> changeSetHaita = new ChangeSet<tr_shisakuhin_bf>();
                    //item.kbn_haita = paraSearch.isOpen ? paraSearch.EmployeeCD : null;
                    //changeSetHaita.Updated.Add(item);

                    //changeSetHaita.AttachTo(contextHaita);
                    //contextHaita.SaveChanges();

                    //return Request.CreateResponse<string>(HttpStatusCode.OK, "OK");

                    // 2020-03-13 : END : Request #16147 複数人で画面起動時の制御
                }
                catch (Exception ex)
                {
                    // 2020-03-13 : START : Request #16147 複数人で画面起動時の制御
                    // 例外をエラーログに出力します。
                    //transaction.Rollback();
                    Logger.App.Error(ex.Message, ex);
                    //if (paraSearch.isOpen && (ex is DbUpdateConcurrencyException || ex.InnerException is DbUpdateConcurrencyException))
                    //{
                    //    if (results.userHaitaBusho == null) {
                    //        var item = (from x in contextHaita.tr_shisakuhin_bf
                    //                    where x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi
                    //                    select x).FirstOrDefault();

                    //        //different user login
                    //        string userHaitaKaisha = "";
                    //        string userHaitaBusho = "";
                    //        string userHaitaName = "";

                    //        var userHaita = contextHaita.ma_user_togo.Where(x => x.id_user == item.kbn_haita).FirstOrDefault();

                    //        if (userHaita != null)
                    //        {
                    //            userHaitaName = userHaita.nm_user;
                    //            userHaitaKaisha = contextHaita.ma_kaisha.Where(x => x.cd_kaisha == userHaita.cd_kaisha).Select(x => x.nm_kaisha).FirstOrDefault();
                    //            userHaitaBusho = contextHaita.ma_busho.Where(x => x.cd_kaisha == userHaita.cd_kaisha && x.cd_busho == userHaita.cd_busho).Select(x => x.nm_busho).FirstOrDefault();
                    //        }

                    //        userHaitaKaisha = userHaitaKaisha ?? "";
                    //        userHaitaBusho = userHaitaBusho ?? "";
                    //        userHaitaName = userHaitaName ?? "";

                    //        results.userHaitaKaisha = userHaitaKaisha;
                    //        results.userHaitaBusho = userHaitaBusho;
                    //        results.userHaitaName = userHaitaName;
                    //    }
                    //    return Request.CreateResponse<string>(HttpStatusCode.OK, new JavaScriptSerializer().Serialize(results));
                    //    //return Request.CreateErrorResponse(HttpStatusCode.Conflict, ex);
                    //}

                    // 2020-03-13 : END : Request #16147 複数人で画面起動時の制御
                    return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                }
            }
        }

        /// <summary>
        /// 試作表分析データ確認画面のデータが準備する。
        /// </summary>
        /// <param name="value">原料一覧</param>
        /// <returns>id_session</returns>
        public HttpResponseMessage Post_GenryoDelivery([FromBody] List<wk_shisaku_genryo_delivery_bf> value)
        {
            int maxKey = 0;
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                ChangeSet<wk_shisaku_genryo_delivery_bf> deliveryChangeset = new ChangeSet<wk_shisaku_genryo_delivery_bf>();

                //START : 一番高いセッションIDを貰います
                maxKey = context.wk_shisaku_genryo_delivery_bf.OrderByDescending(x => x.id_session).Select(x => x.id_session).FirstOrDefault() + 1;
                //END : 一番高いセッションIDを貰います

                DateTime sysTime = DateTime.Now;
                //新しいセッションIDを使用する。
                foreach (var item in value)
                {
                    //セッションID
                    item.id_session = maxKey;
                    //登録日付
                    item.dt_toroku = sysTime;
                    deliveryChangeset.Created.AddRange(value);
                }

                deliveryChangeset.AttachTo(context);
                context.SaveChanges();

                return Request.CreateResponse<string>(HttpStatusCode.OK, maxKey.ToString());
            }
        }

        /// <summary>
        /// パラメータで受け渡されたheader情報・detail情報をもとにエントリーheader情報・detail情報を一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]ShikakuDataChangeSet_151 value)
        {

            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            // TODO:header情報を管理しているDbContextとheader、detailの型を指定します。

            tr_shisakuhin_bf ReturnValue = new tr_shisakuhin_bf();
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    if (value.mode_update == mode_update_Addnew || value.mode_update == mode_update_Zencopy || value.mode_update == mode_update_Featurecopy)
                    {
                        ChangeSet<ma_shisaku_saiban_bf> shisaku_saiban = new ChangeSet<ma_shisaku_saiban_bf>();
                        updateKey(context, ref value, ref shisaku_saiban, ref ReturnValue);
                        try
                        {
                            SaveShisakuSaiban(context, shisaku_saiban);
                        }
                        catch
                        {
                            return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "ma_shisaku_saiban_bf");
                        }
                    }

                    //試作品テーブル : キー項目の重複チェックを行います。
                    InvalidationSet<tr_shisakuhin_bf> shisakuhinInvalidations = shisakuhinIsAlreadyExists(value.tr_shisakuhin_bf, ref ReturnValue);
                    if (shisakuhinInvalidations.Count > 0)
                    {
                        return Request.CreateResponse<InvalidationSet<tr_shisakuhin_bf>>(HttpStatusCode.BadRequest, shisakuhinInvalidations);
                    }
                    SaveShisakuhin(context, value.tr_shisakuhin_bf);

                    //配合トラン : キー項目の重複チェックを行います。
                    InvalidationSet<tr_haigo_bf> haigoInvalidations = haigoIsAlreadyExists(value.tr_haigo_bf, ref ReturnValue);
                    if (haigoInvalidations.Count > 0)
                    {
                        return Request.CreateResponse<InvalidationSet<tr_haigo_bf>>(HttpStatusCode.BadRequest, haigoInvalidations);
                    }
                    SaveHaigo(context, value.tr_haigo_bf);

                    //試作テーブル : キー項目の重複チェックを行います。
                    InvalidationSet<tr_shisaku_bf> shisakuInvalidations = shisakuIsAlreadyExists(value.tr_shisaku_bf, ref ReturnValue);
                    if (shisakuInvalidations.Count > 0)
                    {
                        return Request.CreateResponse<InvalidationSet<tr_shisaku_bf>>(HttpStatusCode.BadRequest, shisakuInvalidations);
                    }
                    SaveShisaku(context, value.tr_shisaku_bf);

                    //試作リストテーブル : キー項目の重複チェックを行います。
                    InvalidationSet<tr_shisaku_list_bf> shisakuListInvalidations = shisakuListIsAlreadyExists(value.tr_shisaku_list_bf, ref ReturnValue);
                    if (shisakuListInvalidations.Count > 0)
                    {
                        return Request.CreateResponse<InvalidationSet<tr_shisaku_list_bf>>(HttpStatusCode.BadRequest, shisakuListInvalidations);
                    }
                    SaveShisakuList(context, value.tr_shisaku_list_bf);

                    //原価原料トラン : キー項目の重複チェックを行います。
                    //InvalidationSet<tr_genryo_bf> genryoInvalidations = genryoIsAlreadyExists(value.tr_genryo_bf, ref ReturnValue);
                    //if (genryoInvalidations.Count > 0)
                    //{
                    //    return Request.CreateResponse<InvalidationSet<tr_genryo_bf>>(HttpStatusCode.BadRequest, genryoInvalidations);
                    //}

                    //SaveGenryo(context, value.tr_genryo_bf);

                    //製造工程テーブル : キー項目の重複チェックを行います。
                    InvalidationSet<tr_cyuui_bf> cyuuiInvalidations = cyuuiIsAlreadyExists(value.tr_cyuui_bf, ref ReturnValue);
                    if (cyuuiInvalidations.Count > 0)
                    {
                        return Request.CreateResponse<InvalidationSet<tr_cyuui_bf>>(HttpStatusCode.BadRequest, cyuuiInvalidations);
                    }

                    SaveCyuui(context, value.tr_cyuui_bf);

                    //ゲートヘッダーテーブル 登録
                    //SaveTrGateHeader(context, value.tr_gate_header);
                    //登録_製法コピー
                    if (value.mode_update == mode_update_kopi)
                    {
                        ReturnValue.cd_shain = value.cd_shain;
                        ReturnValue.nen = value.nen;
                        ReturnValue.no_oi = value.no_oi;

                        //Bug #15389 : START : 製法コピー時
                        //付番される配合コードが200000～299999でないと保存できないようになっている
                        //■配合ヘッダ 登録
                        var haigo_header_max = context.ma_haigo_header.OrderByDescending(x => x.cd_haigo).FirstOrDefault();

                        decimal cd_haigo_max = 1;
                        //配合ヘッダの最大の配合コード＋１						
                        if (haigo_header_max != null)
                        {
                            cd_haigo_max = haigo_header_max.cd_haigo + 1;
                        }

                        if (cd_haigo_max < min_haigo_cd || cd_haigo_max > max_haigo_cd)
                        {
                            return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "Haigo_range_check");
                        }
                        //Bug #15389 : END : 製法コピー時

                        CopyForSeiho(context, value, value.settingDefault, cd_haigo_max);
                    }

                    if (ReturnValue.no_oi == 0)
                    {
                        ReturnValue.cd_shain = value.cd_shain;
                        ReturnValue.nen = value.nen;
                        ReturnValue.no_oi = value.no_oi;
                    }

                    if (ReturnValue.no_oi != 0)
                    {
                        value.cd_shain = ReturnValue.cd_shain;
                        value.nen = ReturnValue.nen;
                        value.no_oi = ReturnValue.no_oi;
                    }

                    //2-2.新規原価試算依頼がある場合
                    //var irai = iraiData(context, ReturnValue, value.iraiSelected, value.settingDefault.daihyogaisha, value.cd_kaisha, value.cd_busho, value.cd_nisugata, value.settingDefault);
                    //if (irai.StatusCode == HttpStatusCode.OK)
                    //{
                        transaction.Commit();
                    //}
                    //else
                    //{
                    //    transaction.Rollback();
                    //    return irai;
                    //}
                }
            }

            return Request.CreateResponse<tr_shisakuhin_bf>(HttpStatusCode.OK, ReturnValue);
        }

        /// <summary>
        /// 配合を製法にコピーする。
        /// </summary>
        /// <param name="context"></param>
        /// <param name="value"></param>
        /// //Bug #15389 : 製法コピー時 : 新しい配合パラム
        public void CopyForSeiho(ShohinKaihatsuEntities context, ShikakuDataChangeSet_151 value, settingDefault_151 setting, decimal cd_haigo_max)
        {
            decimal cd_login = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            DateTime sysTime = DateTime.Now;

            if (value.tr_shisakuhin_bf.Updated.Count() <= 0)
            {
                value.tr_shisakuhin_bf.Updated.Add(context.tr_shisakuhin_bf.Where(x => x.cd_shain == value.cd_shain && x.nen == value.nen && x.no_oi == value.no_oi).FirstOrDefault());
            }

            value.tr_shisakuhin_bf.Updated[0].seq_shisaku = value.kopi.seq_shisaku;
            value.tr_shisakuhin_bf.Updated[0].id_koshin = cd_login;
            value.tr_shisakuhin_bf.Updated[0].dt_koshin = sysTime;

            //save shisakuhin
            value.tr_shisakuhin_bf.AttachTo(context);
            context.SaveChanges();

            var shisakuhin = value.tr_shisakuhin_bf.Updated[0];
            var nen = decimal.Parse(DateTime.Now.ToString("yy")).ToString("00");
            //2019-09-10 : START : Bug #15328 製法支援コピー時の製法番号の付番
            //支援コピー時の製法番号はログイン者の会社コードが登録されるようになっていました。
            //var no_seiho1 = ((int)shisakuhin.cd_kaisha).ToString("0000") + "-" + value.kopi.shubetu + "-" + nen + "-";
            var no_seiho1 = value.cd_kaisha.ToString("0000") + "-" + value.kopi.shubetu + "-" + nen + "-";
            //2019-09-10 : END : Bug #15328 製法支援コピー時の製法番号の付番
            var seiho = context.ma_seiho.Where(r => r.no_seiho.StartsWith(no_seiho1)).OrderByDescending(x => x.no_seiho).FirstOrDefault();

            string seiho_bango = "0001";
            if (seiho != null)
            {
                seiho_bango = (int.Parse(seiho.no_seiho.Trim().Substring(seiho.no_seiho.Length - 4)) + 1).ToString("0000");
            }
            no_seiho1 = no_seiho1 + seiho_bango;

            var shisaku = context.tr_shisaku_bf.Where(x => x.cd_shain == value.cd_shain && x.nen == value.nen && x.no_oi == value.no_oi && x.seq_shisaku == value.kopi.seq_shisaku).FirstOrDefault();

            shisaku.no_seiho5 = shisaku.no_seiho4;//製法No-5
            shisaku.no_seiho4 = shisaku.no_seiho3;//製法No-4
            shisaku.no_seiho3 = shisaku.no_seiho2;//製法No-3
            shisaku.no_seiho2 = shisaku.no_seiho1;//製法No-2
            shisaku.no_seiho1 = no_seiho1;//製法No-1
            shisaku.id_koshin = cd_login;
            shisaku.dt_koshin = sysTime;
            value.tr_shisaku_bf.Updated.Add(shisaku);

            //Bug #15389 : START : 製法コピー時
            //付番される配合コードが200000～299999でないと保存できないようになっている
            //■配合ヘッダ 登録
            //var haigo_header_max = context.ma_haigo_header.OrderByDescending(x => x.cd_haigo).FirstOrDefault();
            var kojyo = context.ma_kojyo.Where(x => x.cd_kaisha == shisakuhin.cd_kaisha && x.cd_kojyo == shisakuhin.cd_kojo).FirstOrDefault();

            //decimal cd_haigo_max = 1;
            ////配合ヘッダの最大の配合コード＋１						
            //if (haigo_header_max != null)
            //{
            //    cd_haigo_max = haigo_header_max.cd_haigo + 1;
            //}
            //Bug #15389 : END : 製法コピー時

            short? cd_seiho_bunrui = null;
            if (shisakuhin.cd_naiyobunrui != null)
            {
                cd_seiho_bunrui = short.Parse(shisakuhin.cd_naiyobunrui);
            }

            //新しい配合ヘッダ
            ChangeSet<ma_haigo_header> haigo_header_new = new ChangeSet<ma_haigo_header>();
            //先頭から60バイト 
            string nm_shisakuhin = HaigoNameFromHin(shisakuhin.nm_hin);
            //配合コード
            haigo_header_new.Created.Add(new ma_haigo_header
            {
                //配合コード
                cd_haigo = cd_haigo_max,
                //配合名
                nm_haigo = nm_shisakuhin,
                //配合名略
                nm_haigo_r = null,
                //代表会社コード
                cd_kaisha_daihyo = (short)shisakuhin.cd_kaisha,
                //代表工場コード
                cd_kojyo_daihyo = (short)shisakuhin.cd_kojo,
                //品区分コード
                kbn_hin = value.settingDefault.kbn_hin_3,
                //分類コード
                cd_bunrui = null,
                //歩留
                budomari = kojyo.budomari,
                //基本重量
                qty_kihon = kojyo.qty_kihon,
                //基本倍率
                ritsu_kihon = kojyo.ritsu_kihon,
                //設備コード
                cd_setsubi = kojyo.cd_setsubi,
                //仕込み合算フラグ
                flg_gasan = value.settingDefault.flg_gasan,
                //仕込み最大重量
                qty_max = kojyo.qty_max,
                //配合合計重量
                //qty_haigo_kei = shisaku.juryo_shiagari_g != null ? (double)shisaku.juryo_shiagari_g : value.kopi.total_juryo_shiagari_seq,
                qty_haigo_kei = (double)(shisaku.juryo_shiagari_g ?? 0),
                //備考
                biko = null,
                //製法番号
                no_seiho = no_seiho1,
                //会社コード
                cd_kaisha = value.kopi.cd_kaisha,
                //V/W区分（Kg/L）
                kbn_vw = value.settingDefault.kbn_vw,
                //比重
                //hijyu = value.settingDefault.hijyu,
                hijyu = kojyo.hijyu,
                //未使用フラグ
                flg_mishiyo = false,
                //廃止区分
                kbn_haishi = 0,
                //仕上区分
                //kbn_shiagari = shisaku.juryo_shiagari_g != null ? value.settingDefault.gokeishiagariari : value.settingDefault.gokeishiagarinashi,
                kbn_shiagari = value.settingDefault.gokeishiagariari,
                //ステータス
                status = value.settingDefault.henshuchu,
                //製法分類
                cd_seiho_bunrui = cd_seiho_bunrui,
                //参考製法番号
                no_seiho_sanko = null,
                //登録日時
                dt_toroku = DateTime.Now,
                //登録者会社コード
                cd_toroku_kaisha = value.kopi.cd_kaisha,
                //登録者コード
                //2020-05-06 START Q&A #17422
                //製法支援コピーを行った人が作成者となる
                //cd_toroku = shisakuhin.id_toroku.ToString(),
                cd_toroku = cd_login.ToString(),
                //2020-05-06 END Q&A #17422
                //更新日時
                dt_henko = DateTime.Now,
                //更新者会社コード
                cd_koshin_kaisha = value.kopi.cd_kaisha,
                //更新者コード
                //2020-05-06 START Q&A #17422
                //製法支援コピーを行った人が作成者となる
                //cd_koshin = shisakuhin.id_koshin.ToString(),
                cd_koshin = cd_login.ToString(),
                //2020-05-06 END Q&A #17422
                //参考配合コード 
                cd_haigo_sanko = null,
                //DCPBFコード
                cd_dcp_aoh = null,
                //MXTBFコード
                cd_mxt_aoh = null,
                //BFコンバート区分
                kbn_cnv_aoh = null
            });

            haigo_header_new.AttachTo(context);
            context.SaveChanges();

            //■配合明細 登録
            var haigo = context.tr_haigo_bf.Where(x => x.cd_shain == value.cd_shain && x.nen == value.nen && x.no_oi == value.no_oi).OrderBy(x => x.sort_kotei).ThenBy(x => x.sort_genryo);
            var genryo_seiho = context.ma_genryo_seiho.Where(x => x.cd_kaisha == shisakuhin.cd_kaisha && x.cd_kojyo == shisakuhin.cd_kojo);
            var shisaku_list = context.tr_shisaku_list_bf.Where(x => x.cd_shain == value.cd_shain && x.nen == value.nen && x.no_oi == value.no_oi && x.seq_shisaku == value.kopi.seq_shisaku);
            var kanzanSeihoList = value.kanzanSeihoCopy.Where(x => x.seq_shisaku == value.kopi.seq_shisaku).ToList();

            ChangeSet<ma_haigo_meisai> haigo_meisai = new ChangeSet<ma_haigo_meisai>();

            //投入順
            byte no_tonyu = 1;
            short cd_kotei = 0;
            foreach (var item in haigo)
            {
                if (item.cd_kotei != cd_kotei)
                {
                    no_tonyu = 1;
                    cd_kotei = item.cd_kotei;
                    haigo_meisai.Created.Add(new ma_haigo_meisai
                    {
                        //配合コード
                        cd_haigo = cd_haigo_max,
                        //工程番号
                        no_kotei = (byte)item.sort_kotei,
                        //投入順
                        no_tonyu = no_tonyu,
                        //原料CD
                        cd_hin = setting.cd_command,
                        //指定原料フラグ
                        flg_shitei = value.settingDefault.flg_shitei_nashi,
                        //品区分
                        kbn_hin = value.settingDefault.kbn_hin_9,
                        //仕掛品区分
                        kbn_shikakari = null,
                        //工程名
                        nm_hin = item.nm_kotei != null ? HaigoNameFromHin(item.nm_kotei) : "",
                        //マークコード
                        cd_mark = value.settingDefault.mark_18,
                        //配合重量
                        qty_haigo = null,
                        //荷姿重量
                        qty_nisugata = null,
                        //誤差
                        gosa = null,
                        //歩留
                        budomari = null,
                        //分割区分
                        kbn_bunkatsu = value.settingDefault.kbn_bunkatsu_0,
                    });
                }

                int n;
                bool isNumeric = int.TryParse(item.cd_genryo, out n);

                if (!isNumeric)
                {
                    continue;
                }

                //原料CD
                decimal cd_hin = Genryocode(item.cd_genryo, setting);
                //配合重量
                var qtyHaigo = qty_haigo(kanzanSeihoList, item, cd_hin, setting);

                if (cd_hin != setting.cd_command && qtyHaigo == null || qtyHaigo == 0)
                {
                    //※0,null配合の原料は対象外とする
                    continue;
                }

                //原料は工程ごとに2からの連番						
                no_tonyu += 1;
                var genryo_sh = genryo_seiho.Where(x => x.cd_hin == cd_hin).FirstOrDefault();
                haigo_meisai.Created.Add(new ma_haigo_meisai
                {
                    //配合コード
                    cd_haigo = cd_haigo_max,
                    //工程番号
                    no_kotei = (byte)item.sort_kotei,
                    //投入順
                    no_tonyu = no_tonyu,
                    //原料CD
                    cd_hin = cd_hin,
                    //指定原料フラグ
                    flg_shitei = value.settingDefault.flg_shitei_nashi,
                    //品区分
                    kbn_hin = value.settingDefault.kbn_hin_1,
                    //仕掛品区分
                    kbn_shikakari = value.settingDefault.shikakari_kaihatsu,
                    //工程名
                    nm_hin = (genryo_sh != null ? genryo_sh.nm_hin : null) ?? "",
                    //マークコード
                    //cd_mark = value.settingDefault.mark_0,
                    cd_mark = (item.cd_tani == value.settingDefault.cd_tani_gr_g && (item.cd_tani_master == value.settingDefault.cd_tani_gr_kg || item.cd_tani_master == value.settingDefault.cd_tani_gr_can)) ? value.settingDefault.mark_12 : value.settingDefault.mark_0,
                    //配合重量
                    qty_haigo = qtyHaigo,
                    //荷姿重量
                    qty_nisugata = genryo_sh != null ? genryo_sh.qty : null,
                    //誤差
                    gosa = null,
                    //歩留
                    budomari = Budomari(genryo_sh, cd_hin, value.settingDefault),
                    //分割区分
                    kbn_bunkatsu = value.settingDefault.kbn_bunkatsu_0,
                });
            }

            haigo_meisai.AttachTo(context);
            context.SaveChanges();

            //■製法マスタ 登録
            ChangeSet<ma_seiho> seiho_new = new ChangeSet<ma_seiho>();
            seiho_new.Created.Add(new ma_seiho
            {
                //製法番号
                no_seiho = no_seiho1,
                //製法名 
                nm_seiho = nm_shisakuhin,
                //製法作成日
                dt_seiho_sakusei = null,
                //製法作成者１
                nm_seiho_sakusei_1 = null,
                //製法作成者２
                nm_seiho_sakusei_2 = null,
                //製法文書名・前
                nm_seiho_bunsho_before = null,
                //製法申請者会社コード
                cd_shinsei_tanto_kaisha = null,
                //製法申請者コード
                cd_shinsei_tanto = null,
                //製法責任者
                nm_seiho_sekinin = null,
                //製法申請日
                dt_seiho_shinsei = null,
                //リニューアルフラグ
                flg_renewal = null,
                //試作CD-社員CD
                cd_shain = value.cd_shain,
                //試作CD-追番
                no_oi = value.no_oi,
                //試作CD-年
                nen = value.nen,
                //試作SEQ
                seq_shisaku = value.kopi.seq_shisaku,
                //サンプルNO（名称）
                nm_sample = shisaku.nm_sample
            });

            seiho_new.AttachTo(context);
            context.SaveChanges();

            //■製法伝送マスタ 登録
            ChangeSet<ma_seiho_denso> seiho_denso = new ChangeSet<ma_seiho_denso>();
            seiho_denso.Created.Add(new ma_seiho_denso
            {
                //製法番号					
                no_seiho = no_seiho1,
                //会社コード											
                cd_kaisha = (short)shisakuhin.cd_kaisha,
                //工場コード						
                cd_kojyo = (short)shisakuhin.cd_kojo,
                //伝送対象フラグ										
                flg_denso_taisho = false,
                //伝送状態フラグ					
                flg_denso_jyotai = value.settingDefault.flg_denso_jyotai,
                //伝送登録日							
                dt_denso_toroku = null,
                //伝送完了日										
                dt_denso_kanryo = null,
                //代表工場フラグ								
                flg_daihyo_kojyo = value.settingDefault.flg_daihyo_kojyo,
                //伝送登録者会社コード			
                cd_denso_tanto_kaisha = null,
                //伝送登録者コード				
                cd_denso_tanto = null,
                //伝送備考								
                biko = null
            });

            seiho_denso.AttachTo(context);
            context.SaveChanges();

            //■製造可能ラインマスタ 登録	
            ChangeSet<ma_seizo_line> seizo_line = new ChangeSet<ma_seizo_line>();
            seizo_line.Created.Add(new ma_seizo_line
            {
                //配合コード				
                cd_haigo = cd_haigo_max,
                //優先順位										
                no_yusen = 1,
                //ラインコード				
                cd_line = kojyo.cd_line,
                //変更日時										
                dt_henko = DateTime.Now,
                //更新者会社コード									
                cd_koshin_kaisha = value.kopi.cd_kaisha,
                //更新者コード						
                //2020-05-06 START Q&A #17422
                //製法支援コピーを行った人が作成者となる
                //cd_koshin = shisakuhin.id_koshin.ToString()
                cd_koshin = cd_login.ToString()
                //2020-05-06 END Q&A #17422	
            });

            seizo_line.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// 歩留
        /// </summary>
        /// <param name="genryo_sh"></param>
        /// <param name="cd_hin"></param>
        /// <returns>double?</returns>
        public double? Budomari(ma_genryo_seiho genryo_sh, decimal cd_hin, settingDefault_151 setting)
        {
            //品コード アンマッチの場合
            if (genryo_sh == null)
            {
                //原料でコード＝999999の場合
                if (cd_hin == setting.cd_command)
                {
                    return null;
                }
                //原料でコード≠999999の場合
                return setting.manbudomari;
            }
            //品コード マッチの場合
            return genryo_sh.budomari;
        }

        /// <summary>
        /// 配合重量
        /// </summary>
        /// <param name="shisaku_list"></param>
        /// <param name="cd_genryo"></param>
        /// <returns>double?</returns>
        //public double? qty_haigo(IEnumerable<tr_shisaku_list_bf> shisaku_list, tr_haigo_bf haigo, decimal cd_hin, settingDefault_151 setting)
        //{
        //    //原料でコード＝999999の場合
        //    if (cd_hin == setting.cd_command)
        //    {
        //        return null;
        //    }
        //    //原料でコード≠999999の場合
        //    ///return (double?)shisaku_list.Where(x => x.cd_kotei == haigo.cd_kotei && x.seq_kotei == haigo.seq_kotei).Select(x => x.quantity).FirstOrDefault();
        //    return (double?)shisaku_list.Where(x => x.cd_kotei == haigo.cd_kotei && x.seq_kotei == haigo.seq_kotei).Select(x => x.quantity_kanzan).FirstOrDefault();
        //}
        public double? qty_haigo(List<KanzanSeisho> shisaku_list, tr_haigo_bf haigo, decimal cd_hin, settingDefault_151 setting)
        {
            //原料でコード＝999999の場合
            if (cd_hin == setting.cd_command)
            {
                return null;
            }
            //原料でコード≠999999の場合
            ///return (double?)shisaku_list.Where(x => x.cd_kotei == haigo.cd_kotei && x.seq_kotei == haigo.seq_kotei).Select(x => x.quantity).FirstOrDefault();
            return (double?)shisaku_list.Where(x => x.cd_kotei == haigo.cd_kotei && x.seq_kotei == haigo.seq_kotei).Select(x => x.quantity_kanzan).FirstOrDefault();
        }

        /// <summary>
        /// コメント行の場合（99999999999と、6桁を超える値が登録された場合）
        /// </summary>
        /// <param name="cd_genryo"></param>
        /// <returns>品コード</returns>
        public decimal Genryocode(string cd_genryo, settingDefault_151 setting)
        {
            if (cd_genryo.Length > 6)
            {
                return setting.cd_command;
            }
            return decimal.Parse(cd_genryo);
        }

        /// <summary>
        /// 先頭から60バイト
        /// </summary>
        /// <param name="nm_hin"></param>
        /// <returns>配合名</returns>
        public string HaigoNameFromHin(string nm_hin)
        {
            string nm_haigo = string.Empty;
            foreach (var charactor in nm_hin)
            {
                if (Encoding.GetEncoding(Properties.Resources.Encoding).GetByteCount(nm_haigo + charactor) <= 60)
                {
                    nm_haigo += charactor;
                }
                else
                {
                    break;
                }
            }
            return nm_haigo;
        }

        /// <summary>
        /// check irai shisaku
        /// </summary>
        /// <param name="context"></param>
        /// <param name="ReturnValue"></param>
        /// <param name="iraiSelected"></param>
        //public HttpResponseMessage iraiData(ShohinKaihatsuEntities context, tr_shisakuhin_bf ReturnValue, List<iraiSelected> iraiSelected, string daihyogaisha, short cd_kaisha, int? cd_busho, string cd_nisugata, settingDefault setting)
        //{
        //    #region //原価試算依頼があるくない場合
        //    if (iraiSelected == null || iraiSelected.Count == 0)
        //    {
        //        return Request.CreateResponse<string>(HttpStatusCode.OK, "");
        //    }

        //    decimal cd_login = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity));
        //    DateTime sysTime = DateTime.Now;

        //    var shisakuhin = context.tr_shisakuhin_bf.Where(x => x.cd_shain == ReturnValue.cd_shain && x.nen == ReturnValue.nen && x.no_oi == ReturnValue.no_oi).FirstOrDefault();

        //    if (shisakuhin == null)
        //    {
        //        return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "");
        //    }
        //    #endregion

        //    var irai = (from c in iraiSelected.Where(x => x.flg_disabled == false)
        //                join p in context.tr_shisaku_bf.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi)
        //                on c.seq_shisaku equals p.seq_shisaku
        //                select new
        //                {
        //                    seq_shisaku = c.seq_shisaku,
        //                    juryo_shiagari_g = p.juryo_shiagari_g
        //                }).ToList();


        //    //2-2.原価試算依頼がある場合
        //    if (irai.Count() > 0)
        //    {
        //        //関連会社（代表会社（[ma_literal].[cd_category]= "K_daihyogaisha"）ではない）での試算依頼チェックを行う		
        //        //関連会社が指定されている場合、エラーメッセージ表示		
        //        //「関連会社での試算依頼はできません。(AP0078)」	
        //        var cd_kaishagaisha = shisakuhin.cd_kaisha.ToString();
        //        var gaisha = context.ma_literal.Where(x => x.cd_category == daihyogaisha && x.nm_literal == cd_kaishagaisha).FirstOrDefault();
        //        if (gaisha == null)
        //        {
        //            return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "K_daihyogaisha");
        //        }

        //        //■試算試作品テーブル
        //        var shisan_shisakuhin = context.tr_shisan_shisakuhin.Where(x => x.cd_shain == ReturnValue.cd_shain && x.nen == ReturnValue.nen && x.no_oi == ReturnValue.no_oi);
        //        //・全ての試作データの試算期日をクリアする
        //        ChangeSet<tr_shisan_shisakuhin> shisan_shisakuhin_changeset = new ChangeSet<tr_shisan_shisakuhin>();
        //        foreach (var shisan in shisan_shisakuhin)
        //        {
        //            shisan.dt_kizitu = null;
        //            shisan.id_koshin = cd_login;
        //            shisan.dt_koshin = sysTime;
        //            shisan_shisakuhin_changeset.Updated.Add(shisan);
        //        }
        //        shisan_shisakuhin_changeset.AttachTo(context);
        //        context.SaveChanges();

        //        //・対象試作の営業ステータス（tr_shisan_status.st_eigyo）が全て4（採用）の場合、エラーメッセージ表示
        //        var status_eigyo = (from shisan in context.tr_shisan_shisakuhin.Where(x => x.cd_shain == ReturnValue.cd_shain && x.nen == ReturnValue.nen && x.no_oi == ReturnValue.no_oi)
        //                            join status in context.tr_shisan_status.Where(x => x.cd_shain == ReturnValue.cd_shain && x.nen == ReturnValue.nen && x.no_oi == ReturnValue.no_oi)
        //                            on new { shisan.cd_shain, shisan.nen, shisan.no_oi, shisan.no_eda } equals new { status.cd_shain, status.nen, status.no_oi, status.no_eda }
        //                            select new
        //                            {
        //                                st_eigyo = status.st_eigyo
        //                            }).ToList();
        //        if (status_eigyo.Count > 0)
        //        {
        //            var eigyo4 = status_eigyo.Where(x => x.st_eigyo != setting.eigyo_status_saiyo).FirstOrDefault();

        //            if (eigyo4 == null)
        //            {
        //                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, "st_eigyo_4");
        //            }
        //        }

        //        //・依頼データの登録処理を行う
        //        //１）枝番号検索
        //        var no_eda_check = (
        //                    from shisa in context.tr_shisan_shisakuhin.Where(x => x.cd_shain == ReturnValue.cd_shain && x.nen == ReturnValue.nen && x.no_oi == ReturnValue.no_oi)
        //                    join status in context.tr_shisan_status.Where(x => x.cd_shain == ReturnValue.cd_shain && x.nen == ReturnValue.nen && x.no_oi == ReturnValue.no_oi) on
        //                    shisa.no_eda equals status.no_eda
        //                    select new
        //                    {
        //                        cd_shain = shisa.cd_shain,
        //                        nen = shisa.nen,
        //                        no_oi = shisa.no_oi,
        //                        no_eda = shisa.no_eda,
        //                        cd_kaisha = shisa.cd_kaisha,
        //                        cd_kojo = shisa.cd_kojo,
        //                        st_eigyo = status.st_eigyo,
        //                        cd_shain_status = status.cd_shain,
        //                        nen_status = status.nen,
        //                        no_oi_status = status.no_oi,
        //                        no_eda_status = status.no_eda,
        //                        st_kenkyu = status.st_kenkyu,
        //                        st_seisan = status.st_seisan,
        //                        st_gensizai = status.st_gensizai,
        //                        st_kojo = status.st_kojo,
        //                        id_toroku = status.id_toroku,
        //                        dt_toroku = status.dt_toroku,
        //                        id_koshin = status.id_koshin,
        //                        dt_koshin = status.dt_koshin
        //                    });

        //        //２）試算試作品テーブルに依頼があるかどうか検索する
        //        var shisakuhin_check = (
        //            from shisan in context.tr_shisan_shisakuhin.Where(x => x.cd_shain == ReturnValue.cd_shain && x.nen == ReturnValue.nen && x.no_oi == ReturnValue.no_oi)
        //            from shisa in context.tr_shisakuhin_bf.Where(x => x.cd_shain == shisan.cd_shain && x.nen == shisan.nen && x.no_oi == shisan.no_oi).DefaultIfEmpty()
        //            select new
        //            {
        //                cd_shain = shisa.cd_shain,
        //                nen = shisa.nen,
        //                no_oi = shisa.no_oi,
        //                cd_kaisha_shisan = shisan.cd_kaisha,
        //                kojo_shisan = shisan.cd_kojo,
        //                cd_kaisha = shisa.cd_kaisha,
        //                cd_kojo = shisa.cd_kojo
        //            });

        //        //３）試算試作品テーブル、原価試算メモテーブル、原価試算メモ（営業連絡用）テーブルに追加								
        //        //２の検索結果がない場合
        //        if (shisakuhin_check.Count() == 0)
        //        {
        //            //試算試作品テーブル
        //            ChangeSet<tr_shisan_shisakuhin> shisan_hin = new ChangeSet<tr_shisan_shisakuhin>();
        //            shisan_hin.Created.Add(new tr_shisan_shisakuhin
        //            {
        //                cd_shain = ReturnValue.cd_shain,//試作CD-社員CD
        //                nen = ReturnValue.nen,//試作CD-年
        //                no_oi = ReturnValue.no_oi,//試作CD-追番
        //                no_eda = 0,//枝番号

        //                cd_kaisha = shisakuhin.cd_kaisha,//指定工場-cd_kaisha
        //                cd_kojo = shisakuhin.cd_kojo,//指定工場-工場CD
        //                su_iri = shisakuhin.su_iri,//入り数
        //                genka = shisakuhin.genka,//原価

        //                baika = shisakuhin.baika,//売価
        //                cd_genka_tani = null,//原価単位
        //                buturyo = null,//想定物量
        //                dt_hatubai = shisakuhin.dt_hatubai,//発売時期

        //                uriage_k = shisakuhin.uriage_k,//計画売上
        //                rieki_k = shisakuhin.rieki_k,//計画利益
        //                uriage_h = shisakuhin.uriage_h,//販売後売上
        //                rieki_h = shisakuhin.rieki_h,//販売後利益

        //                cd_nisugata = cd_nisugata,//荷姿CD
        //                lot = null,//製造ロット
        //                saiyo_sample = null,//採用サンプルNo
        //                ken_id_koshin = cd_login,//研究所更新者

        //                ken_dt_koshin = sysTime,//研究所更新日
        //                sei_id_koshin = null,//生産管理更新者
        //                sei_dt_koshin = null,//生産管理更新日
        //                gen_id_koshin = null,//原資材調達更新者

        //                gen_dt_koshin = null,//原資材調達更新日
        //                kojo_id_koshin = null,//工場更新者
        //                kojo_dt_koshin = null,//工場更新日
        //                sam_dt_koshin = null,//サンプル確定更新日

        //                fg_keisan = setting.fg_keisan,//計算項目（固定費/ケース or 固定費/KG）
        //                id_toroku = cd_login,//登録者ID
        //                dt_toroku = sysTime,//登録日付
        //                id_koshin = cd_login,//更新者ID 

        //                dt_koshin = sysTime,//更新日付
        //                sam_id_koshin = null,//サンプルNO確定更新者
        //                haita_id_user = null,//排他区分（ﾕｰｻﾞID）
        //                kikan_hanbai_sen = null,//販売期間（通年orスポット）

        //                kikan_hanbai_tani = null,//販売期間（単位）
        //                shurui_eda = null,//枝版種類
        //                yoryo = shisakuhin.yoryo,//容量
        //                dt_kizitu = null,//試算期日

        //                su_irai = 1,//依頼回数
        //                kikan_hanbai_suti = null,//販売期間（数値）
        //                nm_edaShisaku = null,//試作枝番名
        //                cd_hanseki = shisakuhin.cd_hanseki,//販責会社
        //            });

        //            shisan_hin.AttachTo(context);
        //            context.SaveChanges();

        //            //原価試算メモテーブル
        //            ChangeSet<tr_shisan_memo> memo = new ChangeSet<tr_shisan_memo>();
        //            memo.Created.Add(new tr_shisan_memo
        //            {
        //                cd_shain = ReturnValue.cd_shain,//試作CD-社員CD
        //                nen = ReturnValue.nen,//試作CD-年
        //                no_oi = ReturnValue.no_oi,//試作CD-追番
        //                memo = null,//試算メモ
        //                id_toroku = cd_login,//登録者ID
        //                dt_toroku = sysTime,//登録日付
        //                id_koshin = null,//更新者ID
        //                dt_koshin = null//更新日付
        //            });

        //            memo.AttachTo(context);
        //            context.SaveChanges();

        //            //原価試算メモ（営業連絡用）テーブルに追加	
        //            ChangeSet<tr_shisan_memo_eigyo> memo_eigyo = new ChangeSet<tr_shisan_memo_eigyo>();
        //            memo_eigyo.Created.Add(new tr_shisan_memo_eigyo
        //            {
        //                cd_shain = ReturnValue.cd_shain,//試作CD-社員CD
        //                nen = ReturnValue.nen,//試作CD-年
        //                no_oi = ReturnValue.no_oi,//試作CD-追番
        //                memo_eigyo = null,//原価試算メモ（営業連絡用
        //                id_toroku = cd_login,//登録者ID
        //                dt_toroku = sysTime,//登録日付
        //                id_koshin = null,//更新者ID
        //                dt_koshin = null//更新日付
        //            });

        //            memo_eigyo.AttachTo(context);
        //            context.SaveChanges();
        //        }
        //        else
        //        {
        //            //２の検索結果がある場合						
        //            //営業ステータスが４でない(tr_shisan_status.st_eigyo < 4 )試作に対して、サンプル追加を行う					
        //            //試算試作品テーブルの依頼回数更新する				
        //            var no_eda_check_not_4 = no_eda_check.Where(x => x.st_eigyo < setting.eigyo_status_saiyo);
        //            ChangeSet<tr_shisan_shisakuhin> shisan_update = new ChangeSet<tr_shisan_shisakuhin>();

        //            //var max_su_irai = context.tr_shisan_shisakuhin.Where(x => x.cd_shain == ReturnValue.cd_shain && x.nen == ReturnValue.nen && x.no_oi == ReturnValue.no_oi).OrderByDescending(x => x.su_irai).Select(x => x.su_irai).FirstOrDefault();
        //            foreach (var edaItem in no_eda_check_not_4)
        //            {

        //                var shisan_update_irai = context.tr_shisan_shisakuhin.Where(x => x.cd_shain == edaItem.cd_shain && x.nen == edaItem.nen && x.no_oi == edaItem.no_oi && x.no_eda == edaItem.no_eda).FirstOrDefault();

        //                if (shisan_update_irai != null)
        //                {

        //                    shisan_update_irai.su_irai = (shisan_update_irai.su_irai ?? 0) + 1;
        //                    shisan_update_irai.id_koshin = cd_login;
        //                    shisan_update_irai.dt_koshin = sysTime;

        //                    shisan_update.Updated.Add(shisan_update_irai);
        //                }
        //            }

        //            shisan_update.AttachTo(context);
        //            context.SaveChanges();
        //        }
        //        //４）項目固定チェックがオフの各サンプル列の試算日をクリアする
        //        var koumokuchk = context.tr_shisan_shisaku.Where(x => x.cd_shain == ReturnValue.cd_shain && x.nen == ReturnValue.nen && x.no_oi == ReturnValue.no_oi && x.fg_koumokuchk != 1).ToList();
        //        ChangeSet<tr_shisan_shisaku> shisaku_koumo = new ChangeSet<tr_shisan_shisaku>();
        //        foreach (var itemKouno in koumokuchk)
        //        {
        //            itemKouno.dt_shisan = null;
        //            //itemKouno.id_koshin = cd_login;
        //            //itemKouno.dt_koshin = sysTime;

        //            shisaku_koumo.Updated.Add(itemKouno);
        //        }

        //        shisaku_koumo.AttachTo(context);
        //        context.SaveChanges();

        //        //試算基本情報テーブル　登録
        //        ChangeSet<tr_shisan_kihonjoho> shisan_kihonjoho = new ChangeSet<tr_shisan_kihonjoho>();
        //        //試算試作テーブル　登録
        //        ChangeSet<tr_shisan_shisaku> shisan_shisaku = new ChangeSet<tr_shisan_shisaku>();
        //        //５）試算試作テーブルへデータ登録			
        //        //１の検索結果がある場合　（枝番号がある）		
        //        if (no_eda_check.Count() > 0)
        //        {
        //            //営業ステータスが４でない(tr_shisan_status.st_eigyo < 4 )試作に対して、サンプル追加を行う				
        //            //試算基本情報テーブル（tr_shisan_kihonjoho）に追加			
        //            //試算試作テーブル（tr_shisan_shisaku）に追加	
        //            var no_eda_check_not_4 = no_eda_check.Where(x => x.st_eigyo < setting.eigyo_status_saiyo);

        //            foreach (var itemIrai in irai)
        //            {
        //                foreach (var item in no_eda_check_not_4)
        //                {
        //                    //試算基本情報テーブル　登録
        //                    shisan_kihonjoho.Created.Add(new tr_shisan_kihonjoho
        //                    {
        //                        //試作CD-社員CD										
        //                        cd_shain = shisakuhin.cd_shain,
        //                        //試作CD-年								
        //                        nen = shisakuhin.nen,
        //                        //試作CD-追番								
        //                        no_oi = shisakuhin.no_oi,
        //                        //試作SEQ										
        //                        seq_shisaku = itemIrai.seq_shisaku,
        //                        //枝番号											
        //                        no_eda = item.no_eda,
        //                        //原価										
        //                        genka = shisakuhin.genka,
        //                        //売価							
        //                        baika = shisakuhin.baika,
        //                        //原価単位 cd_genka_tani			
        //                        //想定物量 buturyo			
        //                        //発売時期											
        //                        dt_hatubai = shisakuhin.dt_hatubai,
        //                        //販売期間（通年orスポット）kikan_hanbai_sen			
        //                        //販売期間（数値）kikan_hanbai_suti			
        //                        //販売期間（単位）kikan_hanbai_tani			
        //                        //計画売上										
        //                        uriage_k = shisakuhin.uriage_k,
        //                        //計画利益										
        //                        rieki_k = shisakuhin.rieki_k,
        //                        //製造ロット	lot			
        //                        //販売後売上											
        //                        uriage_h = shisakuhin.uriage_h,
        //                        //販売後利益										
        //                        rieki_h = shisakuhin.rieki_h,
        //                        //登録者ID										
        //                        id_toroku = cd_login,
        //                        //登録日付								
        //                        dt_toroku = sysTime,
        //                        //更新者ID									
        //                        id_koshin = cd_login,
        //                        //更新日付								
        //                        dt_koshin = sysTime
        //                        //物量_数値 buturyo_suti			
        //                        //物量_製品 buturyo_seihin			
        //                        //物量_期間 buturyo_kikan			
        //                    });
        //                    ////試算試作テーブル　登録
        //                    shisan_shisaku.Created.Add(new tr_shisan_shisaku
        //                    {
        //                        //試作CD-社員CD											
        //                        cd_shain = shisakuhin.cd_shain,
        //                        //試作CD-年							
        //                        nen = shisakuhin.nen,
        //                        //試作CD-追番								
        //                        no_oi = shisakuhin.no_oi,
        //                        //試作SEQ						
        //                        seq_shisaku = itemIrai.seq_shisaku,
        //                        //枝番号							
        //                        no_eda = item.no_eda,
        //                        //合計仕上がり重量					
        //                        juryo_shiagari_g = itemIrai.juryo_shiagari_g,
        //                        //試算日	dt_shisan		
        //                        //有効歩留　budomari		
        //                        //平均充填量	heikinjuten		
        //                        //固定費/ケース cs_kotei		
        //                        //固定費/ｋｇ kg_kotei		
        //                        //登録者ID											
        //                        id_toroku = 9999,
        //                        //登録日付											
        //                        dt_toroku = sysTime,
        //                        //更新者ID									
        //                        id_koshin = 9999,
        //                        //更新日付											
        //                        dt_koshin = sysTime,
        //                        //中止フラグ											
        //                        fg_chusi = null,
        //                        //項目固定チェック										
        //                        fg_koumokuchk = 0
        //                        //利益/ケース cs_rieki		
        //                        //利益/kg kg_rieki		

        //                    });
        //                }
        //            }

        //        }
        //        else
        //        {
        //            //１）の検索結果がない場合　（枝番号がない）											
        //            //枝番号が0で試算基本情報テーブル（tr_shisan_kihonjoho）に追加									
        //            //枝番号が0で試算試作テーブル（tr_shisan_shisaku）に追加		
        //            foreach (var itemIrai in irai)
        //            {
        //                //試算基本情報テーブル　登録
        //                shisan_kihonjoho.Created.Add(new tr_shisan_kihonjoho
        //                {
        //                    //試作CD-社員CD										
        //                    cd_shain = shisakuhin.cd_shain,
        //                    //試作CD-年								
        //                    nen = shisakuhin.nen,
        //                    //試作CD-追番								
        //                    no_oi = shisakuhin.no_oi,
        //                    //試作SEQ										
        //                    seq_shisaku = itemIrai.seq_shisaku,
        //                    //枝番号											
        //                    no_eda = 0,
        //                    //原価										
        //                    genka = shisakuhin.genka,
        //                    //売価							
        //                    baika = shisakuhin.baika,
        //                    //原価単位 cd_genka_tani			
        //                    //想定物量 buturyo			
        //                    //発売時期											
        //                    dt_hatubai = shisakuhin.dt_hatubai,
        //                    //販売期間（通年orスポット）kikan_hanbai_sen			
        //                    //販売期間（数値）kikan_hanbai_suti			
        //                    //販売期間（単位）kikan_hanbai_tani			
        //                    //計画売上										
        //                    uriage_k = shisakuhin.uriage_k,
        //                    //計画利益										
        //                    rieki_k = shisakuhin.rieki_k,
        //                    //製造ロット	lot			
        //                    //販売後売上											
        //                    uriage_h = shisakuhin.uriage_h,
        //                    //販売後利益										
        //                    rieki_h = shisakuhin.rieki_h,
        //                    //登録者ID										
        //                    id_toroku = cd_login,
        //                    //登録日付								
        //                    dt_toroku = sysTime,
        //                    //更新者ID									
        //                    id_koshin = cd_login,
        //                    //更新日付								
        //                    dt_koshin = sysTime
        //                    //物量_数値 buturyo_suti			
        //                    //物量_製品 buturyo_seihin			
        //                    //物量_期間 buturyo_kikan			
        //                });
        //                ////試算試作テーブル　登録
        //                shisan_shisaku.Created.Add(new tr_shisan_shisaku
        //                {
        //                    //試作CD-社員CD											
        //                    cd_shain = shisakuhin.cd_shain,
        //                    //試作CD-年							
        //                    nen = shisakuhin.nen,
        //                    //試作CD-追番								
        //                    no_oi = shisakuhin.no_oi,
        //                    //試作SEQ						
        //                    seq_shisaku = itemIrai.seq_shisaku,
        //                    //枝番号							
        //                    no_eda = 0,
        //                    //合計仕上がり重量					
        //                    juryo_shiagari_g = itemIrai.juryo_shiagari_g,
        //                    //試算日	dt_shisan		
        //                    //有効歩留 budomari		
        //                    //平均充填量 heikinjuten		
        //                    //固定費/ケース cs_kotei		
        //                    //固定費/ｋｇ kg_kotei		
        //                    //登録者ID											
        //                    id_toroku = 9999,
        //                    //登録日付											
        //                    dt_toroku = sysTime,
        //                    //更新者ID									
        //                    id_koshin = 9999,
        //                    //更新日付											
        //                    dt_koshin = sysTime,
        //                    //中止フラグ											
        //                    fg_chusi = null,
        //                    //項目固定チェック										
        //                    fg_koumokuchk = 0
        //                    //利益/ケース cs_rieki		
        //                    //利益/kg kg_rieki	
        //                });
        //            }
        //        }

        //        shisan_kihonjoho.AttachTo(context);
        //        shisan_shisaku.AttachTo(context);
        //        context.SaveChanges();

        //        //６）試算配合テーブルへデータ登録
        //        //  試算配合テーブルに検索
        //        var shisanHaigo = (from haigo in context.tr_haigo_bf.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi)
        //                           from shisan_haigo in context.tr_shisan_haigo.Where(x => x.cd_shain == haigo.cd_shain && x.nen == haigo.nen && x.no_oi == haigo.no_oi && x.cd_kotei == haigo.cd_kotei && x.seq_kotei == haigo.seq_kotei).DefaultIfEmpty()
        //                           select new
        //                           {
        //                               cd_shain = haigo.cd_shain,
        //                               nen = haigo.nen,
        //                               no_oi = haigo.no_oi,
        //                               cd_kotei = haigo.cd_kotei,
        //                               seq_kotei = haigo.seq_kotei,
        //                               cd_shain_SHISAN = (decimal?)shisan_haigo.cd_shain,
        //                               nen_SHISAN = (decimal?)shisan_haigo.nen,
        //                               no_oi_SHISAN = (decimal?)shisan_haigo.no_oi,
        //                               cd_kotei_SHISAN = (short?)shisan_haigo.cd_kotei,
        //                               seq_kotei_SHISAN = (short?)shisan_haigo.seq_kotei,
        //                               haigo.nm_genryo,
        //                               haigo.tanka,
        //                               haigo.budomari
        //                           }).Where(x => x.cd_shain_SHISAN == null).ToList();

        //        //上記検索結果がある場合
        //        if (shisanHaigo.Count() > 0)
        //        {
        //            //試算配合テーブル　登録
        //            ChangeSet<tr_shisan_haigo> shisan_haigo_new = new ChangeSet<tr_shisan_haigo>();
        //            //１の検索結果がある場合　（枝番号がある）
        //            if (no_eda_check.Count() > 0)
        //            {
        //                foreach (var eda in no_eda_check)
        //                {
        //                    foreach (var haigo in shisanHaigo)
        //                    {
        //                        shisan_haigo_new.Created.Add(new tr_shisan_haigo
        //                        {
        //                            //試作CD-社員CD		
        //                            cd_shain = haigo.cd_shain,
        //                            //試作CD-年									
        //                            nen = haigo.nen,
        //                            //試作CD-追番				
        //                            no_oi = haigo.no_oi,
        //                            //枝番号					
        //                            cd_kotei = haigo.cd_kotei,
        //                            //工程CD								
        //                            seq_kotei = haigo.seq_kotei,
        //                            //工程SEQ						
        //                            no_eda = eda.no_eda,
        //                            //原料名称							
        //                            nm_genryo = haigo.nm_genryo,
        //                            //単価（入力） tanka_ins							
        //                            //歩留（入力） budomari_ins							
        //                            //単価（マスタ）										
        //                            tanka_ma = haigo.tanka,
        //                            //歩留（マスタ）											
        //                            budomar_ma = haigo.budomari,
        //                            //登録者ID										
        //                            id_toroku = cd_login,
        //                            //登録日付											
        //                            dt_toroku = sysTime,
        //                            //更新者ID											
        //                            id_koshin = cd_login,
        //                            //更新日付											
        //                            dt_koshin = sysTime
        //                            //会社単価コード	 cd_kaisha_tanka							
        //                            //工場単価コード	 cd_kojo_tanka							
        //                            //会社歩留コード	 cd_kaisha_budomari							
        //                            //工場歩留コード	 cd_kojo_budomari							

        //                        });
        //                    }
        //                }
        //            }
        //            else
        //            {
        //                //１）の検索結果がない場合　（枝番号がない）
        //                foreach (var haigo in shisanHaigo)
        //                {
        //                    shisan_haigo_new.Created.Add(new tr_shisan_haigo
        //                    {
        //                        //試作CD-社員CD		
        //                        cd_shain = haigo.cd_shain,
        //                        //試作CD-年									
        //                        nen = haigo.nen,
        //                        //試作CD-追番				
        //                        no_oi = haigo.no_oi,
        //                        //枝番号					
        //                        cd_kotei = haigo.cd_kotei,
        //                        //工程CD								
        //                        seq_kotei = haigo.seq_kotei,
        //                        //工程SEQ						
        //                        no_eda = 0,
        //                        //原料名称							
        //                        nm_genryo = haigo.nm_genryo,
        //                        //単価（入力） tanka_ins							
        //                        //歩留（入力） budomari_ins							
        //                        //単価（マスタ）										
        //                        tanka_ma = haigo.tanka,
        //                        //歩留（マスタ）											
        //                        budomar_ma = haigo.budomari,
        //                        //登録者ID										
        //                        id_toroku = cd_login,
        //                        //登録日付											
        //                        dt_toroku = sysTime,
        //                        //更新者ID											
        //                        id_koshin = cd_login,
        //                        //更新日付											
        //                        dt_koshin = sysTime
        //                        //会社単価コード	 cd_kaisha_tanka							
        //                        //工場単価コード	 cd_kojo_tanka							
        //                        //会社歩留コード	 cd_kaisha_budomari							
        //                        //工場歩留コード	 cd_kojo_budomari						
        //                    });
        //                }
        //            }
        //            shisan_haigo_new.AttachTo(context);
        //            context.SaveChanges();
        //        }

        //        #region ７）ステータスの登録、更新する
        //        //１の検索結果がある場合　（枝番号がある）
        //        //原価試算ステータステーブル　更新
        //        ChangeSet<tr_shisan_status> shisan_status = new ChangeSet<tr_shisan_status>();
        //        //ステータス履歴テーブル　登録
        //        ChangeSet<tr_shisan_status_rireki> shisan_status_rireki = new ChangeSet<tr_shisan_status_rireki>();

        //        if (no_eda_check.Count() > 0)
        //        {
        //            var no_eda_check_not_4 = no_eda_check.Where(x => x.st_eigyo < setting.eigyo_status_saiyo && x.cd_shain_status != null);

        //            foreach (var status in no_eda_check_not_4)
        //            {
        //                shisan_status.Updated.Add(new tr_shisan_status
        //                {
        //                    //試作CD-社員CD									
        //                    cd_shain = status.cd_shain_status,
        //                    //試作CD-年									
        //                    nen = status.nen_status,
        //                    //試作CD-追番					
        //                    no_oi = status.no_oi_status,
        //                    //枝番号								
        //                    no_eda = status.no_eda,
        //                    //研究所ステータス										
        //                    st_kenkyu = setting.genka_shisan_status_irai,
        //                    //生産管理部ステータス	
        //                    st_seisan = setting.genka_shisan_status_nashi,
        //                    //原資材調達部ステータス	
        //                    st_gensizai = setting.genka_shisan_status_nashi,
        //                    //工場ステータス			
        //                    st_kojo = setting.genka_shisan_status_nashi,
        //                    //営業ステータス		
        //                    st_eigyo = setting.genka_shisan_status_nashi,
        //                    ////登録者ID				
        //                    //id_toroku = status.id_toroku,
        //                    ////登録日付								
        //                    //dt_toroku = status.dt_toroku,
        //                    //更新者ID								
        //                    id_koshin = cd_login,
        //                    //更新日付			
        //                    dt_koshin = sysTime

        //                });

        //                shisan_status_rireki.Created.Add(new tr_shisan_status_rireki
        //                {
        //                    //試作CD-社員CD									
        //                    cd_shain = status.cd_shain_status,
        //                    //試作CD-年							
        //                    nen = status.nen_status,
        //                    //試作CD-追番			
        //                    no_oi = status.no_oi_status,
        //                    //枝番号								
        //                    no_eda = status.no_eda,
        //                    //変更日時					
        //                    dt_henkou = DateTime.Now,
        //                    //会社						
        //                    cd_kaisha = cd_kaisha,
        //                    //部署CD									
        //                    cd_busho = cd_busho,
        //                    //変更者ID										
        //                    id_henkou = cd_login,
        //                    //部署業務　カテゴリCD											
        //                    cd_zikko_ca = "wk_kenkyu",
        //                    //部署業務　リテラルCD								
        //                    cd_zikko_li = "1",
        //                    //研究所ステータス										
        //                    st_kenkyu = setting.genka_shisan_status_irai,
        //                    //生産管理部ステータス										
        //                    st_seisan = setting.genka_shisan_status_nashi,
        //                    //原資材調達部ステータス						
        //                    st_gensizai = setting.genka_shisan_status_nashi,
        //                    //工場ステータス								
        //                    st_kojo = setting.genka_shisan_status_nashi,
        //                    //営業ステータス							
        //                    st_eigyo = setting.genka_shisan_status_nashi,
        //                    //登録者ID									
        //                    id_toroku = cd_login,
        //                    //登録日付				
        //                    dt_toroku = sysTime
        //                });
        //            }
        //        }
        //        else
        //        {
        //            //１）の検索結果がない場合　（枝番号がない）
        //            shisan_status.Created.Add(new tr_shisan_status
        //            {
        //                //試作CD-社員CD									
        //                cd_shain = shisakuhin.cd_shain,
        //                //試作CD-年									
        //                nen = shisakuhin.nen,
        //                //試作CD-追番					
        //                no_oi = shisakuhin.no_oi,
        //                //枝番号								
        //                no_eda = 0,
        //                //研究所ステータス										
        //                st_kenkyu = setting.genka_shisan_status_irai,
        //                //生産管理部ステータス	
        //                st_seisan = setting.genka_shisan_status_nashi,
        //                //原資材調達部ステータス	
        //                st_gensizai = setting.genka_shisan_status_nashi,
        //                //工場ステータス			
        //                st_kojo = setting.genka_shisan_status_nashi,
        //                //営業ステータス		
        //                st_eigyo = setting.genka_shisan_status_nashi,
        //                //登録者ID				
        //                id_toroku = cd_login,
        //                //登録日付								
        //                dt_toroku = sysTime,
        //                ////更新者ID								
        //                //id_koshin = cd_login,
        //                ////更新日付			
        //                //dt_koshin = sysTime

        //            });

        //            shisan_status_rireki.Created.Add(new tr_shisan_status_rireki
        //            {
        //                //試作CD-社員CD									
        //                cd_shain = shisakuhin.cd_shain,
        //                //試作CD-年									
        //                nen = shisakuhin.nen,
        //                //試作CD-追番					
        //                no_oi = shisakuhin.no_oi,
        //                //枝番号								
        //                no_eda = 0,
        //                //変更日時					
        //                dt_henkou = DateTime.Now,
        //                //会社						
        //                cd_kaisha = cd_kaisha,
        //                //部署CD									
        //                cd_busho = cd_busho,
        //                //変更者ID										
        //                id_henkou = cd_login,
        //                //部署業務　カテゴリCD											
        //                cd_zikko_ca = "wk_kenkyu",
        //                //部署業務　リテラルCD								
        //                cd_zikko_li = "1",
        //                //研究所ステータス										
        //                st_kenkyu = setting.genka_shisan_status_irai,
        //                //生産管理部ステータス										
        //                st_seisan = setting.genka_shisan_status_nashi,
        //                //原資材調達部ステータス						
        //                st_gensizai = setting.genka_shisan_status_nashi,
        //                //工場ステータス								
        //                st_kojo = setting.genka_shisan_status_nashi,
        //                //営業ステータス							
        //                st_eigyo = setting.genka_shisan_status_nashi,
        //                //登録者ID									
        //                id_toroku = cd_login,
        //                //登録日付				
        //                dt_toroku = sysTime
        //            });
        //        }

        //        shisan_status.AttachTo(context);
        //        context.SaveChanges();

        //        shisan_status_rireki.AttachTo(context);
        //        context.SaveChanges();
        //        #endregion

        //        #region //８）原価試算データの会社工場で原料の洗い替えを行う
        //        //１の検索結果がある場合　（枝番号がある）
        //        //試算試作品テーブル更新
        //        ChangeSet<tr_shisan_shisakuhin> shisakuhinKoshin = new ChangeSet<tr_shisan_shisakuhin>();
        //        //洗い替え場合の試算配合テーブル更新
        //        ChangeSet<tr_shisan_haigo> haigoKoshin = new ChangeSet<tr_shisan_haigo>();
        //        //試算試作品テーブル
        //        var shisakuhinList = context.tr_shisan_shisakuhin.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi).ToList();
        //        if (no_eda_check.Count() > 0)
        //        {
        //            //取得された枝番号がパラメータとして以下処理を実施			
        //            //営業ステータスが４でない(tr_shisan_status.st_eigyo < 4 )試作に対して			
        //            //・洗い替え原料データ検索		
        //            //・試算試作品テーブル更新		
        //            //・上記洗い替え原料データ検索が成功の場合		
        //            //試算配合テーブル（tr_shisan_haigo）更新	
        //            var no_eda_check_not_4 = no_eda_check.Where(x => x.st_eigyo < setting.eigyo_status_saiyo);
        //            foreach (var status in no_eda_check_not_4)
        //            {
        //                ShisanHaigoKoshin(context, shisakuhin, shisakuhinList, ref shisakuhinKoshin, ref haigoKoshin, status.no_eda, sysTime, cd_login, setting);
        //            }
        //        }
        //        else
        //        {
        //            //１の検索結果がない場合　（枝番号がない）
        //            //枝番号が0でパラメータとして以下処理を実施			
        //            //・洗い替え原料データ検索		
        //            //・試算試作品テーブル更新		
        //            //・上記洗い替え原料データ検索が成功の場合		
        //            //試算配合テーブル（tr_shisan_haigo）更新	
        //            ShisanHaigoKoshin(context, shisakuhin, shisakuhinList, ref shisakuhinKoshin, ref haigoKoshin, 0, sysTime, cd_login, setting);
        //        }
        //        shisakuhinKoshin.AttachTo(context);
        //        haigoKoshin.AttachTo(context);
        //        context.SaveChanges();
        //        #endregion
        //    }

        //    #region //2-3-2. 依頼キャンセル処理
        //    var cancelIrai = iraiSelected.Where(x => x.flg_cancel == true);
        //    if (cancelIrai.Count() > 0)
        //    {
        //        //試算基本情報テーブル 削除
        //        ChangeSet<tr_shisan_kihonjoho> shisan_kihonjoho_new = new ChangeSet<tr_shisan_kihonjoho>();
        //        var kihonjohoDel = (from cancel in cancelIrai
        //                            join joho in context.tr_shisan_kihonjoho.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi)
        //                            on cancel.seq_shisaku equals joho.seq_shisaku
        //                            select joho).ToList();
        //        shisan_kihonjoho_new.Deleted.AddRange(kihonjohoDel);
        //        shisan_kihonjoho_new.AttachTo(context);

        //        //試算tr_shisaku_bf 削除
        //        ChangeSet<tr_shisan_shisaku> shisan_shisaku_new = new ChangeSet<tr_shisan_shisaku>();
        //        var shisakuDel = (from cancel in cancelIrai
        //                          join shisaku in context.tr_shisan_shisaku.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi)
        //                          on cancel.seq_shisaku equals shisaku.seq_shisaku
        //                          select shisaku);
        //        shisan_shisaku_new.Deleted.AddRange(shisakuDel);
        //        shisan_shisaku_new.AttachTo(context);

        //        //tr_shisaku_bf 更新
        //        ChangeSet<tr_shisaku_bf> shisaku_up = new ChangeSet<tr_shisaku_bf>();
        //        var shisakuUp = (from cancel in cancelIrai
        //                         join shisaku in context.tr_shisaku_bf.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi)
        //                         on cancel.seq_shisaku equals shisaku.seq_shisaku
        //                         select shisaku);

        //        foreach (var shisaku in shisakuUp)
        //        {
        //            shisaku.flg_shisanIrai = null;
        //            shisaku_up.Updated.Add(shisaku);
        //        }

        //        shisaku_up.AttachTo(context);
        //        context.SaveChanges();

        //        //・検索処理を行う
        //        var checkExit = context.tr_shisan_shisaku.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi);
        //        if (checkExit.Count() > 0)
        //        {
        //            //■原価試算ステータステーブル 更新
        //            ChangeSet<tr_shisan_status> statuskoshin = new ChangeSet<tr_shisan_status>();
        //            var statusKoshin = context.tr_shisan_status.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi);
        //            foreach (var status in statusKoshin)
        //            {
        //                //試作CD-社員CD											
        //                status.cd_shain = shisakuhin.cd_shain;
        //                //試作CD-年							
        //                status.nen = shisakuhin.nen;
        //                //試作CD-追番			
        //                status.no_oi = shisakuhin.no_oi;
        //                //研究所ステータス			
        //                status.st_kenkyu = setting.genka_shisan_status_irai;
        //                //生産管理部ステータス											
        //                status.st_seisan = setting.genka_shisan_status_nashi;
        //                //原資材調達部ステータス											
        //                status.st_gensizai = setting.genka_shisan_status_nashi;
        //                //工場ステータス
        //                status.st_kojo = setting.genka_shisan_status_nashi;
        //                //営業ステータス
        //                status.st_eigyo = setting.genka_shisan_status_nashi;
        //                //更新者ID		
        //                status.id_koshin = cd_login;
        //                //更新日付				
        //                status.dt_koshin = sysTime;

        //                statuskoshin.Updated.Add(status);
        //            }

        //            //■ステータス履歴テーブル 登録
        //            ChangeSet<tr_shisan_status_rireki> rirekiadd = new ChangeSet<tr_shisan_status_rireki>();
        //            rirekiadd.Created.Add(new tr_shisan_status_rireki
        //            {
        //                //試作CD-社員CD											
        //                cd_shain = shisakuhin.cd_shain,
        //                //試作CD-年							
        //                nen = shisakuhin.nen,
        //                //試作CD-追番			
        //                no_oi = shisakuhin.no_oi,
        //                //枝番号						
        //                no_eda = 0,
        //                //変更日時	
        //                dt_henkou = DateTime.Now,
        //                //会社CD				
        //                cd_kaisha = cd_kaisha,
        //                //部署CD								
        //                cd_busho = cd_busho,
        //                //変更者ID				
        //                id_henkou = cd_login,
        //                //部署業務　カテゴリCD
        //                cd_zikko_ca = "wk_kenkyu",
        //                //部署業務　リテラルCD									
        //                cd_zikko_li = "3",
        //                //研究所ステータス
        //                st_kenkyu = setting.genka_shisan_status_irai,
        //                //生産管理部ステータス											
        //                st_seisan = setting.genka_shisan_status_nashi,
        //                //原資材調達部ステータス									
        //                st_gensizai = setting.genka_shisan_status_nashi,
        //                //工場ステータス									
        //                st_kojo = setting.genka_shisan_status_nashi,
        //                //営業ステータス									
        //                st_eigyo = setting.genka_shisan_status_nashi,
        //                //登録者ID		
        //                id_toroku = cd_login,
        //                //登録日付				
        //                dt_toroku = sysTime
        //            });

        //            statuskoshin.AttachTo(context);
        //            rirekiadd.AttachTo(context);
        //            context.SaveChanges();
        //        }
        //        else
        //        {
        //            //■試算試作品テーブル削除
        //            ChangeSet<tr_shisan_shisakuhin> shisakuhindel = new ChangeSet<tr_shisan_shisakuhin>();
        //            shisakuhindel.Deleted.AddRange(context.tr_shisan_shisakuhin.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi));
        //            //■試算メモテーブル 削除
        //            ChangeSet<tr_shisan_memo> memodel = new ChangeSet<tr_shisan_memo>();
        //            memodel.Deleted.AddRange(context.tr_shisan_memo.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi));
        //            //■試算メモ営業連絡用テーブル 削除
        //            ChangeSet<tr_shisan_memo_eigyo> memo_eigyodel = new ChangeSet<tr_shisan_memo_eigyo>();
        //            memo_eigyodel.Deleted.AddRange(context.tr_shisan_memo_eigyo.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi));
        //            //■試算配合テーブル 削除
        //            ChangeSet<tr_shisan_haigo> haigodel = new ChangeSet<tr_shisan_haigo>();
        //            haigodel.Deleted.AddRange(context.tr_shisan_haigo.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi));
        //            //■試算原価資材テーブル 削除
        //            ChangeSet<tr_shisan_shizai> shizaidel = new ChangeSet<tr_shisan_shizai>();
        //            shizaidel.Deleted.AddRange(context.tr_shisan_shizai.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi));
        //            //■原価試算ステータステーブル 削除
        //            ChangeSet<tr_shisan_status> statusdel = new ChangeSet<tr_shisan_status>();
        //            statusdel.Deleted.AddRange(context.tr_shisan_status.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi));
        //            //■ステータス履歴テーブル 削除
        //            ChangeSet<tr_shisan_status_rireki> rirekidel = new ChangeSet<tr_shisan_status_rireki>();
        //            rirekidel.Deleted.AddRange(context.tr_shisan_status_rireki.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi && x.no_eda != 0));
        //            //■ステータス履歴テーブル 登録
        //            ChangeSet<tr_shisan_status_rireki> rirekiadd = new ChangeSet<tr_shisan_status_rireki>();
        //            rirekiadd.Created.Add(new tr_shisan_status_rireki
        //            {
        //                //試作CD-社員CD											
        //                cd_shain = shisakuhin.cd_shain,
        //                //試作CD-年							
        //                nen = shisakuhin.nen,
        //                //試作CD-追番			
        //                no_oi = shisakuhin.no_oi,
        //                //枝番号						
        //                no_eda = 0,
        //                //変更日時	
        //                dt_henkou = DateTime.Now,
        //                //cd_kaisha				
        //                cd_kaisha = cd_kaisha,
        //                //部署CD								
        //                cd_busho = cd_busho,
        //                //変更者ID				
        //                id_henkou = cd_login,
        //                //部署業務　カテゴリCD
        //                cd_zikko_ca = "wk_kenkyu",
        //                //部署業務　リテラルCD									
        //                cd_zikko_li = "2",
        //                //研究所ステータス
        //                st_kenkyu = setting.genka_shisan_status_irai,
        //                //生産管理部ステータス											
        //                st_seisan = setting.genka_shisan_status_nashi,
        //                //原資材調達部ステータス									
        //                st_gensizai = setting.genka_shisan_status_nashi,
        //                //工場ステータス									
        //                st_kojo = setting.genka_shisan_status_nashi,
        //                //営業ステータス									
        //                st_eigyo = setting.genka_shisan_status_nashi,
        //                //登録者ID		
        //                id_toroku = cd_login,
        //                //登録日付				
        //                dt_toroku = sysTime
        //            });

        //            shisakuhindel.AttachTo(context);
        //            memodel.AttachTo(context);
        //            memo_eigyodel.AttachTo(context);
        //            haigodel.AttachTo(context);
        //            shizaidel.AttachTo(context);
        //            statusdel.AttachTo(context);
        //            rirekidel.AttachTo(context);
        //            rirekiadd.AttachTo(context);
        //            context.SaveChanges();
        //        }
        //    }
        //    #endregion

        //    return Request.CreateResponse<string>(HttpStatusCode.OK, "OK");
        //}

        /// <summary>
        /// 原価試算データの会社工場で原料の洗い替えを行う
        /// </summary>
        /// <param name="context"></param>
        /// <param name="shisakuhin"></param>
        /// <param name="shisakuhinList"></param>
        /// <param name="shisakuhinKoshin"></param>
        /// <param name="haigoKoshin"></param>
        /// <param name="no_eda"></param>
        /// <param name="sysTime"></param>
        /// <param name="cd_login"></param>
        //private void ShisanHaigoKoshin(ShohinKaihatsuEntities context, tr_shisakuhin_bf shisakuhin, List<tr_shisan_shisakuhin> shisakuhinList, ref ChangeSet<tr_shisan_shisakuhin> shisakuhinKoshin, ref ChangeSet<tr_shisan_haigo> haigoKoshin, decimal no_eda, DateTime sysTime, decimal? cd_login, settingDefault setting)
        //{
        //    //洗い替え原料データ検索
        //    var shisan_haigo_list = context.sp_shohinkaihatsu_search_101_shisan_haigo_list(shisakuhin.cd_shain, shisakuhin.nen, shisakuhin.no_oi, shisakuhin.cd_kaisha, shisakuhin.cd_kojo, no_eda);
        //    //試算試作品テーブル
        //    var shisakuhinEda = shisakuhinList.Where(x => x.no_eda == no_eda).FirstOrDefault();
        //    if (shisakuhinEda != null)
        //    {
        //        //会社コードー
        //        shisakuhinEda.cd_kaisha = shisakuhin.cd_kaisha;
        //        //部署コードー
        //        shisakuhinEda.cd_kojo = shisakuhin.cd_kojo;
        //        //更新者ID
        //        shisakuhinEda.id_koshin = cd_login;
        //        //更新日付
        //        shisakuhinEda.dt_koshin = sysTime;

        //        //■試算試作品テーブル更新
        //        shisakuhinKoshin.Updated.Add(shisakuhinEda);
        //    }
        //    //洗い替え場合の試算配合テーブル
        //    var shisanHaigoKoshin = context.tr_shisan_haigo.Where(x => x.cd_shain == shisakuhin.cd_shain && x.nen == shisakuhin.nen && x.no_oi == shisakuhin.no_oi && x.no_eda == no_eda);
        //    //試算配合テーブル（tr_shisan_haigo）更新
        //    foreach (var haigo in shisan_haigo_list)
        //    {
        //        //原料CD
        //        String strGenryoCD = haigo.cd_genryo;
        //        //原料名
        //        String strGenryoNm = haigo.nm_genryo_SHISAKU ?? "";
        //        //N原料名
        //        String strNGenryoNm = haigo.nm_genryo_N;
        //        //会社CD MAX単価
        //        int? cd_kaisha_MAX_TANKA = haigo.cd_kaisha_NEW;
        //        //工場CD MAX単価
        //        int? cd_kojo_MAX_TANKA = haigo.cd_kojo_NEW;
        //        //会社CD MIN歩留
        //        int? cd_kaisha_MIN_BUDOMARI = haigo.cd_kaisha_NEW;
        //        //工場CD MIN歩留
        //        int? cd_kojo_MIN_BUDOMARI = haigo.cd_kojo_NEW;

        //        //原料名が空の場合
        //        if (strGenryoNm == "" || strGenryoNm == null)
        //        {

        //        }
        //        //原料名が空でない場合
        //        else
        //        {
        //            //★☆記号削除
        //            if (strGenryoNm.IndexOf('★') == 0 || strGenryoNm.IndexOf('☆') == 0)
        //            {
        //                //星記号削除
        //                strGenryoNm = strGenryoNm.Substring(1);
        //            }
        //        }
        //        //単価　試作入力
        //        decimal? strTanka_Shisaku = haigo.tanka_SHISAKU;
        //        //歩留　試作入力
        //        decimal? strBudomari_Shisaku = haigo.budomari_SHISAKU;
        //        //単価　工場原料マスタ
        //        decimal? strTanka_KOUZYO_MAS = haigo.tanka_MASTA;
        //        //歩留　工場原料マスタ
        //        decimal? strBudomari_KOUZYO_MAS = haigo.budomari_MASTA;
        //        //単価　会社内MAX
        //        decimal? strTanka_MAX = haigo.tanka_MAX;
        //        //歩留　会社内MIN
        //        decimal? strBudomari_MIN = haigo.budomari_MIN;
        //        //試作CD　社員
        //        decimal strCd_shain = haigo.cd_shain;
        //        //試作CD　年
        //        decimal strNen = haigo.nen;
        //        //試作CD　追番
        //        decimal strNo_oi = haigo.no_oi;
        //        //工程CD
        //        short? strCd_kotei = haigo.cd_kotei;
        //        //工程SEQ
        //        short? strSeq_kotei = haigo.seq_kotei;
        //        //使用実績
        //        short? strFg_Shiyo = haigo.flg_shiyo;
        //        //編集項目用バッファ
        //        //原料名
        //        String strGenryo = "";
        //        //単価
        //        decimal? strTanka = null;
        //        //歩留
        //        decimal? strBudomari = null;

        //        if (strGenryoCD != null && strGenryoCD != "" && strGenryoCD.IndexOf("N") == 0)
        //        {
        //            //5 原料CDの先頭に”N”（新規原料ｼﾝﾎﾞﾙ）が設定されている場合
        //            //原料名
        //            strGenryo = strNGenryoNm;
        //            //シサクイック（原価）要望【案件No3】原料名の取得方法変更

        //            //単価
        //            strTanka = strTanka_Shisaku;
        //            //歩留
        //            strBudomari = strBudomari_Shisaku;

        //            //START MAX単価、MIN歩留　会社、工場の追加			
        //            //会社CD MAX単価
        //            cd_kaisha_MAX_TANKA = null;
        //            //工場CD MAX単価
        //            cd_kojo_MAX_TANKA = null;
        //            //会社CD MIN歩留
        //            cd_kaisha_MIN_BUDOMARI = null;
        //            //工場CD MIN歩留
        //            cd_kojo_MIN_BUDOMARI = null;
        //            //MAX単価、MIN歩留　会社、工場の追加			

        //        }
        //        else if (strBudomari_KOUZYO_MAS != null || strBudomari_KOUZYO_MAS != null)
        //        {
        //            //1 新会社CD、新工場CD、原料CDに該当するマスタ（工場原料マスタ）が存在する場合
        //            //単価
        //            strTanka = strTanka_KOUZYO_MAS;
        //            //歩留
        //            strBudomari = strBudomari_KOUZYO_MAS;

        //            //START MAX単価、MIN歩留　会社、工場の追加			
        //            //会社CD MAX単価
        //            cd_kaisha_MAX_TANKA = haigo.cd_kaisha_NEW;
        //            //工場CD MAX単価
        //            cd_kojo_MAX_TANKA = haigo.cd_kojo_NEW;
        //            //会社CD MIN歩留
        //            cd_kaisha_MIN_BUDOMARI = haigo.cd_kaisha_NEW;
        //            //工場CD MIN歩留
        //            cd_kojo_MIN_BUDOMARI = haigo.cd_kojo_NEW;
        //            //MAX単価、MIN歩留　会社、工場の追加			

        //        }
        //        else if (strBudomari_KOUZYO_MAS == null && strBudomari_KOUZYO_MAS == null)
        //        {
        //            //2 新会社CD、新工場CD、原料CDに該当するマスタ（工場原料マスタ）が存在しない場合

        //            if (strTanka_MAX != null || strBudomari_MIN != null)
        //            {
        //                //3 新会社CD、原料CDに該当するマスタ（工場原料マスタ）が存在する場合

        //                //原料名
        //                strGenryoNm = "☆" + strGenryoNm;
        //                //単価
        //                strTanka = strTanka_MAX;
        //                //歩留
        //                strBudomari = strBudomari_MIN;

        //                //MAX単価、MIN歩留　会社、工場の追加			
        //                //会社CD MAX単価
        //                cd_kaisha_MAX_TANKA = haigo.cd_kaisha_MAX_TANKA;
        //                //工場CD MAX単価
        //                cd_kojo_MAX_TANKA = haigo.cd_busho_MAX_TANKA;
        //                //会社CD MIN歩留
        //                cd_kaisha_MIN_BUDOMARI = haigo.cd_kaisha_MIN_BUDOMARI;
        //                //工場CD MIN歩留
        //                cd_kojo_MIN_BUDOMARI = haigo.cd_busho_MIN_BUDOMARI;
        //                //MAX単価、MIN歩留　会社、工場の追加			
        //            }
        //            else
        //            {
        //                //4 新会社CD、原料CDに該当するマスタ（工場原料マスタ）が存在しない場合

        //                //原料名
        //                //strGenryo = "★" + "";
        //                strGenryo = "";

        //                //単価
        //                strTanka = null;
        //                //歩留
        //                strBudomari = null;

        //                //start MAX単価、MIN歩留　会社、工場の追加			
        //                //会社CD MAX単価
        //                cd_kaisha_MAX_TANKA = null;
        //                //工場CD MAX単価
        //                cd_kojo_MAX_TANKA = null;
        //                //会社CD MIN歩留
        //                cd_kaisha_MIN_BUDOMARI = null;
        //                //工場CD MIN歩留
        //                cd_kojo_MIN_BUDOMARI = null;
        //                //end MAX単価、MIN歩留　会社、工場の追加		
        //            }
        //        }

        //        if (strGenryoCD != null && strGenryoCD != "" && strGenryoCD.IndexOf("N") == 0)
        //        {

        //        }
        //        else
        //        {

        //            //3ヶ月使用実績があるもの=1
        //            if (strFg_Shiyo == setting.shiyo_ari)
        //            {
        //                strGenryo = strGenryoNm;
        //            }
        //            //3ヶ月使用実績がないもの=0
        //            else if (strFg_Shiyo == setting.shiyo_nashi)
        //            {
        //                //★☆記号削除
        //                if (strGenryoNm != "" && strGenryoNm != null)
        //                {
        //                    if (strGenryoNm.IndexOf("★") == 0 || strGenryoNm.IndexOf("☆") == 0)
        //                    {
        //                        //星記号削除
        //                        strGenryoNm = strGenryoNm.Substring(1);
        //                    }
        //                }
        //                strGenryo = "★" + strGenryoNm;
        //            }
        //            //上記以外=null
        //            else
        //            {
        //                if (strGenryoNm == null || strGenryoNm == "")
        //                {
        //                    strGenryo = strGenryoNm;
        //                }
        //                else
        //                {
        //                    //★☆記号削除
        //                    if (strGenryoNm.IndexOf("★") == 0 || strGenryoNm.IndexOf("☆") == 0)
        //                    {
        //                        //星記号削除
        //                        strGenryoNm = strGenryoNm.Substring(1);
        //                    }
        //                    strGenryo = "★" + strGenryoNm;
        //                }
        //            }
        //        }

        //        var haigoKoshinItem = shisanHaigoKoshin.Where(x => x.cd_kotei == strCd_kotei && x.seq_kotei == strSeq_kotei).FirstOrDefault();
        //        if (haigoKoshinItem != null)
        //        {
        //            //原料名称
        //            haigoKoshinItem.nm_genryo = strGenryo ?? "";
        //            //単価（マスタ）
        //            haigoKoshinItem.tanka_ma = strTanka;
        //            //歩留（マスタ）
        //            haigoKoshinItem.budomar_ma = strBudomari;
        //            //会社単価コード
        //            haigoKoshinItem.cd_kaisha_tanka = cd_kaisha_MAX_TANKA;
        //            //工場単価コード
        //            haigoKoshinItem.cd_kojo_tanka = cd_kojo_MAX_TANKA;
        //            //会社歩留コード
        //            haigoKoshinItem.cd_kaisha_budomari = cd_kaisha_MIN_BUDOMARI;
        //            //工場歩留コード
        //            haigoKoshinItem.cd_kojo_budomari = cd_kojo_MIN_BUDOMARI;
        //            //更新者ID
        //            haigoKoshinItem.id_koshin = cd_login;
        //            //更新日付
        //            haigoKoshinItem.dt_koshin = sysTime;

        //            haigoKoshin.Updated.Add(haigoKoshinItem);
        //        }
        //    }
        //}

        /// <summary>
        /// 試作品テーブルテーブルの情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<tr_shisakuhin_bf> shisakuhinIsAlreadyExists(ChangeSet<tr_shisakuhin_bf> value, ref tr_shisakuhin_bf ReturnValue)
        {
            InvalidationSet<tr_shisakuhin_bf> result = new InvalidationSet<tr_shisakuhin_bf>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi);
                    var isDeleted = value.Deleted.Exists(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi);
                    var isDatabaseExists = (context.tr_shisakuhin_bf.Find(item.cd_shain, item.nen, item.no_oi) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_shisakuhin_bf>(Properties.Resources.ValidationKey, item, "tr_shisakuhin_bf"));
                    }
                }

                foreach (var item in value.Updated)
                {
                    ReturnValue.cd_shain = item.cd_shain;
                    ReturnValue.nen = item.nen;
                    ReturnValue.no_oi = item.no_oi;
                    break;
                }
            }

            return result;
        }

        /// <summary>
        /// 配合トランテーブルの情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<tr_haigo_bf> haigoIsAlreadyExists(ChangeSet<tr_haigo_bf> value, ref tr_shisakuhin_bf ReturnValue)
        {
            InvalidationSet<tr_haigo_bf> result = new InvalidationSet<tr_haigo_bf>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {

                bool isFirst = true;
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi && target.cd_kotei == item.cd_kotei && target.seq_kotei == item.seq_kotei);
                    var isDeleted = value.Deleted.Exists(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi && target.cd_kotei == item.cd_kotei && target.seq_kotei == item.seq_kotei);
                    var isDatabaseExists = (context.tr_haigo_bf.Find(item.cd_shain, item.nen, item.no_oi, item.cd_kotei, item.seq_kotei) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_haigo_bf>(Properties.Resources.ValidationKey, item, "tr_haigo_bf"));
                    }
                    if (isFirst)
                    {
                        isFirst = false;
                        ReturnValue.cd_shain = item.cd_shain;
                        ReturnValue.nen = item.nen;
                        ReturnValue.no_oi = item.no_oi;
                    }
                }

                foreach (var item in value.Updated)
                {
                    ReturnValue.cd_shain = item.cd_shain;
                    ReturnValue.nen = item.nen;
                    ReturnValue.no_oi = item.no_oi;
                    break;
                }
            }

            return result;
        }

        /// <summary>
        /// 試作テーブルテーブルの情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<tr_shisaku_bf> shisakuIsAlreadyExists(ChangeSet<tr_shisaku_bf> value, ref tr_shisakuhin_bf ReturnValue)
        {
            InvalidationSet<tr_shisaku_bf> result = new InvalidationSet<tr_shisaku_bf>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                bool isFirst = true;
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi && target.seq_shisaku == item.seq_shisaku);
                    var isDeleted = value.Deleted.Exists(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi && target.seq_shisaku == item.seq_shisaku);
                    var isDatabaseExists = (context.tr_shisaku_bf.Find(item.cd_shain, item.nen, item.no_oi, item.seq_shisaku) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_shisaku_bf>(Properties.Resources.ValidationKey, item, "tr_shisaku_bf"));
                    }

                    if (isFirst)
                    {
                        isFirst = false;
                        ReturnValue.cd_shain = item.cd_shain;
                        ReturnValue.nen = item.nen;
                        ReturnValue.no_oi = item.no_oi;
                    }
                }

                foreach (var item in value.Updated)
                {
                    ReturnValue.cd_shain = item.cd_shain;
                    ReturnValue.nen = item.nen;
                    ReturnValue.no_oi = item.no_oi;
                    break;
                }
            }

            return result;
        }

        /// <summary>
        /// 試作リストテーブルテーブルの情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<tr_shisaku_list_bf> shisakuListIsAlreadyExists(ChangeSet<tr_shisaku_list_bf> value, ref tr_shisakuhin_bf ReturnValue)
        {
            InvalidationSet<tr_shisaku_list_bf> result = new InvalidationSet<tr_shisaku_list_bf>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                bool isFirst = true;
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi && target.seq_shisaku == item.seq_shisaku && target.cd_kotei == item.cd_kotei && target.seq_kotei == item.seq_kotei);
                    var isDeleted = value.Deleted.Exists(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi && target.seq_shisaku == item.seq_shisaku && target.cd_kotei == item.cd_kotei && target.seq_kotei == item.seq_kotei);
                    var isDatabaseExists = (context.tr_shisaku_list_bf.Find(item.cd_shain, item.nen, item.no_oi, item.seq_shisaku, item.cd_kotei, item.seq_kotei) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_shisaku_list_bf>(Properties.Resources.ValidationKey, item, "tr_shisaku_list_bf"));
                    }

                    if (isFirst)
                    {
                        isFirst = false;
                        ReturnValue.cd_shain = item.cd_shain;
                        ReturnValue.nen = item.nen;
                        ReturnValue.no_oi = item.no_oi;
                    }
                }

                foreach (var item in value.Updated)
                {
                    ReturnValue.cd_shain = item.cd_shain;
                    ReturnValue.nen = item.nen;
                    ReturnValue.no_oi = item.no_oi;
                    //break;
                }
            }

            return result;
        }

        /// <summary>
        /// 原価原料トランテーブルの情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        //private InvalidationSet<tr_genryo_bf> genryoIsAlreadyExists(ChangeSet<tr_genryo_bf> value, ref tr_shisakuhin_bf ReturnValue)
        //{
        //    InvalidationSet<tr_genryo_bf> result = new InvalidationSet<tr_genryo_bf>();

        //    using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
        //    {
        //        bool isFirst = true;
        //        foreach (var item in value.Created)
        //        {
        //            // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
        //            bool isDepulicate = false;

        //            var createdCount = value.Created.Count(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi && target.seq_shisaku == item.seq_shisaku);
        //            var isDeleted = value.Deleted.Exists(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi && target.seq_shisaku == item.seq_shisaku);
        //            var isDatabaseExists = (context.tr_genryo_bf.Find(item.cd_shain, item.nen, item.no_oi, item.seq_shisaku) != null);

        //            isDepulicate |= (createdCount > 1);
        //            isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

        //            if (isDepulicate)
        //            {
        //                result.Add(new Invalidation<tr_genryo_bf>(Properties.Resources.ValidationKey, item, "tr_genryo_bf"));
        //            }

        //            if (isFirst)
        //            {
        //                isFirst = false;
        //                ReturnValue.cd_shain = item.cd_shain;
        //                ReturnValue.nen = item.nen;
        //                ReturnValue.no_oi = item.no_oi;
        //            }
        //        }

        //        foreach (var item in value.Updated)
        //        {
        //            ReturnValue.cd_shain = item.cd_shain;
        //            ReturnValue.nen = item.nen;
        //            ReturnValue.no_oi = item.no_oi;
        //            break;
        //        }
        //    }

        //    return result;
        //}
        //
        /// <summary>
        /// 原価原料トランテーブルの情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<tr_cyuui_bf> cyuuiIsAlreadyExists(ChangeSet<tr_cyuui_bf> value, ref tr_shisakuhin_bf ReturnValue)
        {
            InvalidationSet<tr_cyuui_bf> result = new InvalidationSet<tr_cyuui_bf>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                bool isFirst = true;
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi && target.no_chui == item.no_chui);
                    var isDeleted = value.Deleted.Exists(target => target.cd_shain == item.cd_shain && target.nen == item.nen && target.no_oi == item.no_oi && target.no_chui == item.no_chui);
                    var isDatabaseExists = (context.tr_cyuui_bf.Find(item.cd_shain, item.nen, item.no_oi, item.no_chui) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<tr_cyuui_bf>(Properties.Resources.ValidationKey, item, "tr_cyuui_bf"));
                    }

                    if (isFirst)
                    {
                        isFirst = false;
                        ReturnValue.cd_shain = item.cd_shain;
                        ReturnValue.nen = item.nen;
                        ReturnValue.no_oi = item.no_oi;
                    }
                }

                foreach (var item in value.Updated)
                {
                    ReturnValue.cd_shain = item.cd_shain;
                    ReturnValue.nen = item.nen;
                    ReturnValue.no_oi = item.no_oi;
                    break;
                }
            }

            return result;
        }

        /// <summary>
        /// update data key with key of shisakuhin
        /// </summary>
        /// <param name="context"></param>
        /// <param name="value"></param>
        public void updateKey(ShohinKaihatsuEntities context, ref ShikakuDataChangeSet_151 value, ref ChangeSet<ma_shisaku_saiban_bf> shisaku_saiban, ref tr_shisakuhin_bf ReturnValue)
        {
            #region get infor key tr_shisakuhin_bf
            decimal cd_shain = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            decimal nen = decimal.Parse(DateTime.Now.ToString("yy"));
            decimal no_oi = 1;

            //社員コードとシステムの年からoi番号を貰います
            var no_oi_max = context.ma_shisaku_saiban_bf.Where(x => x.cd_shain == cd_shain && x.nen == nen).FirstOrDefault();

            if (no_oi_max == null)
            {
                shisaku_saiban.Created.Add(new ma_shisaku_saiban_bf
                {
                    cd_shain = cd_shain,
                    nen = nen,
                    no_oi = no_oi
                });
            }
            else
            {
                no_oi = (no_oi_max.no_oi ?? 0) + no_oi;
                no_oi_max.no_oi = no_oi;
                shisaku_saiban.Updated.Add(no_oi_max);
            }

            value.tr_shisakuhin_bf.Created[0].cd_shain = cd_shain;
            value.tr_shisakuhin_bf.Created[0].nen = nen;
            value.tr_shisakuhin_bf.Created[0].no_oi = no_oi;

            ReturnValue.cd_shain = cd_shain;
            ReturnValue.nen = nen;
            ReturnValue.no_oi = no_oi;

            if (value.mode_update == mode_update_Zencopy)
            {
                value.tr_shisakuhin_bf.Created[0].ts = null;
            }

            #endregion get infor key tr_shisakuhin_bf

            #region update key haigo

            foreach (var item in value.tr_haigo_bf.Created)
            {
                item.cd_shain = cd_shain;
                item.nen = nen;
                item.no_oi = no_oi;

                if (value.mode_update == mode_update_Zencopy)
                {
                    item.ts = null;
                }
            }

            #endregion update key haigo

            #region update key tr_shisaku_bf

            foreach (var item in value.tr_shisaku_bf.Created)
            {
                item.cd_shain = cd_shain;
                item.nen = nen;
                item.no_oi = no_oi;

                if (value.mode_update == mode_update_Zencopy)
                {
                    item.no_seiho1 = null;
                    item.no_seiho2 = null;
                    item.no_seiho3 = null;
                    item.no_seiho4 = null;
                    item.no_seiho5 = null;
                    item.ts = null;
                }
            }

            #endregion update key tr_shisaku_bf

            #region update key tr_shisaku_list_bf

            foreach (var item in value.tr_shisaku_list_bf.Created)
            {
                item.cd_shain = cd_shain;
                item.nen = nen;
                item.no_oi = no_oi;

                if (value.mode_update == mode_update_Zencopy)
                {
                    item.ts = null;
                }
            }

            #endregion update key tr_shisaku_list_bf

            #region update key tr_genryo_bf

            //foreach (var item in value.tr_genryo_bf.Created)
            //{
            //    item.cd_shain = cd_shain;
            //    item.nen = nen;
            //    item.no_oi = no_oi;

            //    if (value.mode_update == mode_update_Zencopy)
            //    {
            //        item.ts = null;
            //    }
            //}

            #endregion update key tr_genryo_bf

            #region update key tr_cyuui_bf

            foreach (var item in value.tr_cyuui_bf.Created)
            {
                item.cd_shain = cd_shain;
                item.nen = nen;
                item.no_oi = no_oi;

                if (value.mode_update == mode_update_Zencopy)
                {
                    item.ts = null;
                }
            }

            #endregion update key tr_cyuui_bf
        }
        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// Save tr_genryo_bf
        /// </summary>
        /// <param name="context"></param>
        /// <param name="header"></param>
        //private void SaveGenryo(ShohinKaihatsuEntities context, ChangeSet<tr_genryo_bf> header)
        //{
        //    // TODO: 登録日、ユーザー、更新日、ユーザーをセットします。
        //    header.SetDataSaveInfo(this.User.Identity);
        //    header.AttachTo(context);
        //    context.SaveChanges();
        //}

        /// <summary>
        /// Save tr_cyuui_bf
        /// </summary>
        /// <param name="context"></param>
        /// <param name="header"></param>
        private void SaveCyuui(ShohinKaihatsuEntities context, ChangeSet<tr_cyuui_bf> header)
        {
            // TODO: 登録日、ユーザー、更新日、ユーザーをセットします。
            header.SetDataSaveInfo(this.User.Identity);
            header.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// Save tr_shisaku_list_bf
        /// </summary>
        /// <param name="context"></param>
        /// <param name="header"></param>
        private void SaveShisakuList(ShohinKaihatsuEntities context, ChangeSet<tr_shisaku_list_bf> header)
        {
            // TODO: 登録日、ユーザー、更新日、ユーザーをセットします。
            header.SetDataSaveInfo(this.User.Identity);
            header.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// Save tr_shisaku_bf
        /// </summary>
        /// <param name="context"></param>
        /// <param name="header"></param>
        private void SaveShisaku(ShohinKaihatsuEntities context, ChangeSet<tr_shisaku_bf> header)
        {
            // TODO: 登録日、ユーザー、更新日、ユーザーをセットします。
            header.SetDataSaveInfo(this.User.Identity);
            header.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// Save tr_haigo_bf
        /// </summary>
        /// <param name="context"></param>
        /// <param name="header"></param>
        private void SaveHaigo(ShohinKaihatsuEntities context, ChangeSet<tr_haigo_bf> header)
        {
            // TODO: 登録日、ユーザー、更新日、ユーザーをセットします。
            header.SetDataSaveInfo(this.User.Identity);
            header.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// save tr_shisakuhin_bf
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="header">hedaer情報</param>
        /// <returns>headerの新規キー項目</returns>
        private void SaveShisakuhin(ShohinKaihatsuEntities context, ChangeSet<tr_shisakuhin_bf> header)
        {
            // TODO: 登録日、ユーザー、更新日、ユーザーをセットします。
            header.SetDataSaveInfo(this.User.Identity);
            header.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// save ma_shisaku_saiban_bf
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="header">hedaer情報</param>
        /// <returns>headerの新規キー項目</returns>
        private void SaveShisakuSaiban(ShohinKaihatsuEntities context, ChangeSet<ma_shisaku_saiban_bf> header)
        {
            header.SetDataSaveInfo(this.User.Identity);
            header.AttachTo(context);
            context.SaveChanges();
        }

        /// <summary>
        /// save tr_gate_header
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="header">hedaer情報</param>
        /// <returns>headerの新規キー項目</returns>
        //private void SaveTrGateHeader(ShohinKaihatsuEntities context, ChangeSet<tr_gate_header> header)
        //{
        //    header.SetDataSaveInfo(this.User.Identity);
        //    header.AttachTo(context);
        //    context.SaveChanges();
        //}

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// respon data master for init page
    /// </summary>
    public class LoadMasterDataRespone_151
    {
        public LoadMasterDataRespone_151()
        {
            this.ShubetsuBango = new List<ma_literal>();
            this.KoteiPatan = new List<ma_literal>();
            this.CyuuiBango = new List<tr_cyuui_bf>();
            this.KoteiZokusei = new List<ma_literal>();
            this.TorokuMeisho = new List<ma_literal>();
            this.HaigoKyodo = new List<ma_literal>();
        }

        public List<ma_literal> ShubetsuBango { get; set; }
        public List<ma_literal> KoteiPatan { get; set; }
        public List<tr_cyuui_bf> CyuuiBango { get; set; }
        public List<ma_literal> KoteiZokusei { get; set; }
        public List<ma_literal> TorokuMeisho { get; set; }
        public List<ma_literal> HaigoKyodo { get; set; }
        public List<ma_literal> GenryoTani { get; set; }
        public short no_chui_max { get; set; }
    }

    /// <summary>
    /// changeSet page
    /// </summary>
    public class ShikakuDataChangeSet_151
    {
        //public ShikakuDataChangeSet_151()
        //{
        //}
        public int mode_update { get; set; }
        public short cd_kaisha { get; set; }
        public int? cd_busho { get; set; }
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
        public string cd_nisugata { get; set; }
        //public settingDefault settingDefault { get; set; }
        public ChangeSet<tr_shisakuhin_bf> tr_shisakuhin_bf { get; set; }
        public ChangeSet<tr_haigo_bf> tr_haigo_bf { get; set; }
        public ChangeSet<tr_shisaku_bf> tr_shisaku_bf { get; set; }
        public ChangeSet<tr_shisaku_list_bf> tr_shisaku_list_bf { get; set; }
        public ChangeSet<tr_cyuui_bf> tr_cyuui_bf { get; set; }
        //public ChangeSet<tr_genryo_bf> tr_genryo_bf { get; set; }
        public List<iraiSelected> iraiSelected { get; set; }
        public kopiInfor kopi { get; set; }
        public settingDefault_151 settingDefault { get; set; }
        public List<KanzanSeisho> kanzanSeihoCopy { get; set; }
    }

    public class KanzanSeisho
    {
        public short seq_shisaku { get; set; }
        public short cd_kotei { get; set; }
        public short seq_kotei { get; set; }
        public Nullable<decimal> quantity_kanzan { get; set; }
    }

    /// <summary>
    /// infor shisa copy
    /// </summary>
    public class kopiInfor_151
    {
        public short cd_kaisha { get; set; }
        //public decimal cd_shain { get; set; }
        //public decimal nen { get; set; }
        //public decimal no_oi { get; set; }
        public short seq_shisaku { get; set; }
        public string shubetu { get; set; }
        public double total_juryo_shiagari_seq { get; set; }
    }

    /// <summary>
    /// parameter search
    /// </summary>
    public class paramSearch_151
    {
        public decimal cd_shain { get; set; }
        public decimal nen { get; set; }
        public decimal no_oi { get; set; }
        public string shubetsu_bango { get; set; }
        public string kotei_patan_AOH { get; set; }
        public string Kotei_zokusei_AOH { get; set; }
        public string kbn_meisho { get; set; }
        public string kbn_haigo_kyodo { get; set; }
        public short cd_kengen { get; set; }
        public short cd_group { get; set; }
        public string genryo_tani_AOH { get; set; }
        public Nullable<decimal> EmployeeCD { get; set; }
        public bool isOpen { get; set; }
    }

    /// <summary>
    /// irai data
    /// </summary>
    public class iraiSelected_151
    {
        public bool flg_disabled { get; set; }
        public bool flg_cancel { get; set; }
        public short seq_shisaku { get; set; }
        public Nullable<decimal> juryo_shiagari_g { get; set; }
    }

    /// <summary>
    /// Web アプリケーション固有の設定・定数です。
    /// </summary>
    public class settingDefault_151
    {
        //廃止区分
        public int genka_shisan_status_nashi { get; set; }//使用
        public int genka_shisan_status_irai { get; set; }//廃止
        public byte kbn_hin_1 { get; set; }//品区分_1
        public byte kbn_hin_3 { get; set; }//品区分_3 
        public byte kbn_hin_9 { get; set; }//品区分_9        
        public bool flg_gasan { get; set; }//仕込み合算フラグ
        public string kbn_vw { get; set; }//V/W区分 
        public double? hijyu { get; set; }//比重_1
        public bool gokeishiagarinashi { get; set; }//合計仕上なし
        public bool gokeishiagariari { get; set; }//合計仕上あり
        public byte henshuchu { get; set; }//編集中 
        public bool flg_shitei_nashi { get; set; }//指定原料フラグ
        public int mark_0 { get; set; }//マーク_0
        public int mark_12 { get; set; }//マーク_P
        public int mark_18 { get; set; }//マーク_18
        public byte shikakari_kaihatsu { get; set; }//仕掛品区分
        public byte kbn_bunkatsu_0 { get; set; }//分割区分_0
        public int manbudomari { get; set; }//満歩留
        public bool flg_daihyo_kojyo { get; set; }//代表工場フラグ
        public bool flg_denso_jyotai { get; set; }//伝送状態フラグ
        public string daihyogaisha { get; set; }
        public short fg_keisan { get; set; }//計算項目（固定費/ケース or 固定費/KG）
        public short shiyo_nashi { get; set; }//使用実績なし
        public short shiyo_ari { get; set; }//使用実績あり
        //public short eigyo_status_nashi { get; set; }//なし		
        //public short eigyo_status_irai { get; set; }//依頼		
        //public short eigyo_status_kanryo { get; set; }//完了		
        public short eigyo_status_saiyo { get; set; }//採用		
        public decimal cd_command { get; set; }//コメンＣＤ行

        public short cd_tani_gr_g { get; set; }
        public short cd_tani_gr_kg { get; set; }
        public short cd_tani_gr_can { get; set; }
    }

    /// <summary>
    /// result shisakuhin
    /// </summary>
    public class shisakuhinResult_151 : tr_shisakuhin_bf
    {
        public Nullable<int> cd_kaisha_org { get; set; }
        public string nm_eigyo { get; set; }
        public string seiho_no_shain { get; set; }
        public string seiho_no_nen { get; set; }
        public string seiho_no_oi { get; set; }
        public string seiho_no { get; set; }
        public string nm_group { get; set; }
        public string nm_team { get; set; }
        public string nm_toroku { get; set; }
        public string nm_koshin { get; set; }
        public string nm_hanseki { get; set; }
        public string nm_kaisha { get; set; }
        public int flg_shisanIrai { get; set; }
    }

    /// <summary>
    /// infor user using shisakuhin
    /// Request #16147 : 使用中（他の人が開いている）の場合、初期表示時に「他の方が開いているため、参照モードで開きます」のメッセージを通知し、参照モードで開く。
    /// </summary>
    public class inforUsingDataHaita_151
    {
        public string userHaitaKaisha { get; set; }
        public string userHaitaBusho { get; set; }
        public string userHaitaName { get; set; }
    }
    #endregion
}
