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
using System.Data.Entity.Infrastructure;
using System.Data.Common;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class TantoshaMasterController : ApiController
    {
        #region "Controllerで公開するAPI"

        public static readonly int eigyo_sale = 1;

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
        public List<bushobunrui> GetBusho(int cd_kaisha, Nullable<short> kbn_kengen_bunrui)
        {
            // TODO: target 情報を管理しているDbContextと target の型を指定します。

            if (kbn_kengen_bunrui == null)
            {
                return new List<bushobunrui>();
            }

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                //2019/11/20 : START : Request #15796
                //製法作成支援の権限が選択されている(権限分類が2または3の)ユーザの場合
                //所属部署のコンボボックスリストには工場マスタに存在している部署のみを表示対象としていますが
                //これを、工場マスタに存在しなくても表示されるように変更してください。
                //return (from busho in (context.ma_busho.Where(x => x.cd_kaisha == cd_kaisha && (x.flg_eigyo == null || x.flg_eigyo != eigyo_sale)))
                //        from kojyo in context.vw_ma_kojyo.Where(x => x.cd_kaisha == busho.cd_kaisha && x.cd_kojyo == busho.cd_busho).DefaultIfEmpty()
                //        select new bushobunrui
                //        {
                //            cd_busho = busho.cd_busho,
                //            nm_busho = busho.nm_busho,
                //            cd_kojyo = kojyo.cd_kojyo
                //        }).Where(x => (x.cd_kojyo != null &&
                //           (kbn_kengen_bunrui == Properties.Settings.Default.kbn_kengen_seiho_kaihatsu ||
                //           kbn_kengen_bunrui == Properties.Settings.Default.kbn_kengen_seiho_kojo)) ||
                //           (kbn_kengen_bunrui == Properties.Settings.Default.kbn_kengen_shisa ||
                //           kbn_kengen_bunrui == Properties.Settings.Default.kbn_kengen_shisa_seiho_kaihatsu ||
                //           kbn_kengen_bunrui == Properties.Settings.Default.kbn_kengen_shisa_seiho_kojo)
                //       ).OrderBy(x => x.cd_busho).ToList();

                short cd_kojyo = 0;
                //return (from busho in (context.ma_busho.Where(x => x.cd_kaisha == cd_kaisha && (x.flg_eigyo == null || x.flg_eigyo != eigyo_sale)))
                return (from busho in (context.ma_busho.Where(x => x.cd_kaisha == cd_kaisha))
                        select new bushobunrui
                        {
                            cd_busho = busho.cd_busho,
                            nm_busho = busho.nm_busho,
                            cd_kojyo = cd_kojyo
                        }).OrderBy(x => x.cd_busho).ToList();


                //2019/11/20 : END : Request #15796
            }
        }

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
        public List<tantokaisya> GetTantoKaisha(decimal id_user)
        {
            // TODO: target 情報を管理しているDbContextと target の型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                return (from user in (context.ma_tantokaisya.Where(x => x.id_user == id_user))
                        from kaisha in context.ma_kaisha.Where(x => x.cd_kaisha == user.cd_tantokaisha).DefaultIfEmpty()
                        select new tantokaisya
                        {
                            id_user = user.id_user,
                            cd_tantokaisha = user.cd_tantokaisha,
                            id_toroku = user.id_toroku,
                            dt_toroku = user.dt_toroku,
                            id_koshin = user.id_koshin,
                            dt_koshin = user.dt_koshin,
                            ts = user.ts,
                            cd_kaisha = user.cd_tantokaisha,
                            nm_kaisha = kaisha.nm_kaisha
                        }).ToList();
            }
        }
        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_010_Result> GetData([FromUri] tantoMasterParanSearch param)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;

            //TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
            //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
            StoredProcedureResult<sp_shohinkaihatsu_search_010_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_010_Result>();
            result.Items = context.sp_shohinkaihatsu_search_010(param.id_user, param.cd_kaisha, param.cd_group, param.nm_user, param.cd_busho, param.cd_team, param.cd_category, param.top, param.skip).ToList();
            if (result.Items.Count() > 0)
            {

                result.Count = (int)result.Items.Select(x => x.counts).First();
            }

            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;
        }

        ///// <summary>
        ///// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        ///// </summary>
        ///// <param name="id"> target のキー項目</param>
        ///// <returns> target 情報</returns>
        //public object Get(int id)
        //{
        //    // TODO: target 情報を管理しているDbContextと target の型を指定します。

        //    // using (/*TODO: target 情報を管理しているDbContextを指定します*/ context = new /*TODO: target 情報を管理しているDbContextを指定します*/())
        //    // {
        //    //     context.Configuration.ProxyCreationEnabled = false;

        //    //     return (from m in context./*TODO: target の型を指定します*/
        //    //             where m./*TODO:headerのキー項目を指定します*/ == id
        //    //             select m).SingleOrDefault();
        //    // }

        //    // TODO: 上記実装を行った後に下の行は削除します
        //    return null;
        //}

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]changeSetTantoMaster value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }
            string eigyoCheck = "";
            // TODO: キー項目の重複チェックを行います。
            InvalidationSet<ma_user_togo> userInvalidations = userIsAlreadyExists(value.ma_user_togo, value.flg_eigyo, ref eigyoCheck, value.kbn_kengen_bunrui, value.password_org);
            if (userInvalidations.Count > 0)
            {
                return Request.CreateResponse<InvalidationSet<ma_user_togo>>(HttpStatusCode.BadRequest, userInvalidations);
            }

            if (eigyoCheck != "")
            {
                return Request.CreateResponse<string>(HttpStatusCode.BadRequest, eigyoCheck);
            }

            // TODO: キー項目の重複チェックを行います。
            InvalidationSet<ma_tantokaisya> tantoInvalidations = tantoIsAlreadyExists(value.ma_tantokaisya, value.id_user);
            if (tantoInvalidations.Count > 0)
            {
                return Request.CreateResponse<InvalidationSet<ma_tantokaisya>>(HttpStatusCode.BadRequest, tantoInvalidations);
            }

            ma_user_togo ReturnMa_user_togo = new ma_user_togo();
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {

                    // TODO: 保存処理を実行します。
                    ReturnMa_user_togo = SaveUserData(context, value.ma_user_togo);
                    SaveTantoData(context, value.ma_tantokaisya);

                    transaction.Commit();
                }

                if (ReturnMa_user_togo.id_user != 0)
                {

                    return Request.CreateResponse<sp_shohinkaihatsu_search_010_Result>(HttpStatusCode.OK, context.sp_shohinkaihatsu_search_010(ReturnMa_user_togo.id_user, ReturnMa_user_togo.cd_kaisha, null, null, null, null, value.cd_category, 1, 0).FirstOrDefault());
                }
            }

            return Request.CreateResponse<ma_user_togo>(HttpStatusCode.OK, ReturnMa_user_togo);
        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Put([FromBody]changeSetTantoMaster value)
        {
            //sp_shohinkaihatsu_search_010_Result itemUpdate;

            ma_user_togo ReturnMa_user_togo = new ma_user_togo();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();
                using (DbTransaction transaction = connection.BeginTransaction())
                {

                    string eigyoCheck = "";
                    // TODO: キー項目の重複チェックを行います。
                    InvalidationSet<ma_user_togo> userInvalidations = userIsAlreadyExists(value.ma_user_togo, value.flg_eigyo, ref eigyoCheck, value.kbn_kengen_bunrui, value.password_org);
                    if (userInvalidations.Count > 0)
                    {
                        return Request.CreateResponse<InvalidationSet<ma_user_togo>>(HttpStatusCode.BadRequest, userInvalidations);
                    }

                    if (eigyoCheck != "")
                    {
                        return Request.CreateResponse<string>(HttpStatusCode.BadRequest, eigyoCheck);
                    }

                    // TODO: 保存処理を実行します。
                    ReturnMa_user_togo = SaveUserData(context, value.ma_user_togo);
                    SaveTantoData(context, value.ma_tantokaisya);

                    context.SaveChanges();
                    transaction.Commit();
                }

                if (ReturnMa_user_togo.id_user != 0)
                {

                    return Request.CreateResponse<sp_shohinkaihatsu_search_010_Result>(HttpStatusCode.OK, context.sp_shohinkaihatsu_search_010(ReturnMa_user_togo.id_user, ReturnMa_user_togo.cd_kaisha, null, null, null, null, value.cd_category, 1, 0).FirstOrDefault());
                }
            }

            return Request.CreateResponse<ma_user_togo>(HttpStatusCode.OK, ReturnMa_user_togo);
        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を削除します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Delete([FromBody]changeSetTantoMaster value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    SaveUserData(context, value.ma_user_togo);

                    //get list kaishatanto
                    value.ma_tantokaisya = new ChangeSet<ma_tantokaisya>();
                    value.ma_tantokaisya.Deleted.AddRange(context.ma_tantokaisya.Where(x => x.id_user == value.id_user));

                    SaveTantoData(context, value.ma_tantokaisya);

                    transaction.Commit();
                }
            }

            return Request.CreateResponse<string>(HttpStatusCode.OK, "");
        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<ma_tantokaisya> tantoIsAlreadyExists(ChangeSet<ma_tantokaisya> value, int id_user)
        {
            InvalidationSet<ma_tantokaisya> result = new InvalidationSet<ma_tantokaisya>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    item.id_user = id_user;
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.id_user == item.id_user && target.cd_tantokaisha == item.cd_tantokaisha);
                    var isDeleted = value.Deleted.Exists(target => target.id_user == item.id_user && target.cd_tantokaisha == item.cd_tantokaisha);
                    var isDatabaseExists = (context.ma_tantokaisya.Find(item.id_user, item.cd_tantokaisha) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<ma_tantokaisya>(Properties.Resources.ValidationKey, item, "ma_tantokaisya"));
                    }
                }
            }

            return result;
        }
        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<ma_user_togo> userIsAlreadyExists(ChangeSet<ma_user_togo> value, int flg_eigyo, ref string eigyoCheck, bool kbn_kengen_bunrui, string password_org)
        {
            InvalidationSet<ma_user_togo> result = new InvalidationSet<ma_user_togo>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                decimal id_user = 0;
                int cd_kaisha = 0;
                int? cd_busho = null;
                bool isCreate = false;
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.id_user == item.id_user && target.cd_kaisha == item.cd_kaisha);
                    var isDeleted = value.Deleted.Exists(target => target.id_user == item.id_user && target.cd_kaisha == item.cd_kaisha);
                    var isDatabaseExists = (context.ma_user_togo.Find(item.id_user, item.cd_kaisha) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<ma_user_togo>(Properties.Resources.ValidationKey, item, "ma_user_togo"));
                    }
                    else
                    {
                        item.dt_password = DateTime.Now;
                    }

                    id_user = item.id_user;
                    cd_kaisha = item.cd_kaisha;
                    cd_busho = item.cd_busho;
                    isCreate = true;
                }

                //check update date password
                foreach (var item in value.Updated)
                {
                    if (password_org != item.password)
                    {
                        item.dt_password = DateTime.Now;
                    }

                    id_user = item.id_user;
                    cd_kaisha = item.cd_kaisha;
                    cd_busho = item.cd_busho;
                }

                //重複の時
                if (result.Count() > 0)
                {
                    return result;
                }

                var dataEigyo = (from b in context.ma_busho
                                 where b.cd_kaisha == cd_kaisha && b.cd_busho == cd_busho && b.flg_eigyo == flg_eigyo
                                 select new
                                 {
                                     cd_kaisha = b.cd_kaisha,
                                     cd_busho = b.cd_busho,
                                     flg_eigyo = b.flg_eigyo
                                 }).FirstOrDefault();

                //if (dataEigyo != null)
                //{
                //    eigyoCheck = "flg_eigyo";
                //    return result;
                //}

                //if (kbn_kengen_bunrui && isCreate)
                if (kbn_kengen_bunrui)
                {
                    var isExitUserNew = context.ma_user_new.Where(x => x.id_user == id_user && x.cd_kaisha == cd_kaisha).FirstOrDefault();
                    var isExitUserReigai = context.ma_user_reigai.Where(x => x.id_user == id_user && x.cd_kaisha == cd_kaisha).FirstOrDefault();
                    if (isExitUserNew == null && isExitUserReigai == null)
                    {
                        eigyoCheck = "isNotExitUserNew";
                    }
                }
            }

            return result;
        }

        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        private void SaveTantoData(ShohinKaihatsuEntities context, ChangeSet<ma_tantokaisya> value)
        {
            value.SetDataSaveInfo(this.User.Identity);
            value.AttachTo(context);
            context.SaveChanges();
        }
        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        private ma_user_togo SaveUserData(ShohinKaihatsuEntities context, ChangeSet<ma_user_togo> value)
        {

            value.SetDataSaveInfo(this.User.Identity);
            value.AttachTo(context);
            context.SaveChanges();

            if (value.Updated.Count > 0)
            {
                return value.Updated[0];
            }

            if (value.Deleted.Count > 0)
            {
                return value.Deleted[0];
            }

            return new ma_user_togo();
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class TantoChangeResponse
    {
        public TantoChangeResponse()
        {
            this.Detail = new List<object/*TODO: target の型を指定します*/>();
        }

        public List<object/*TODO: target の型を指定します*/> Detail { get; set; }
    }

    /// <summary>
    /// changeset for detail input
    /// </summary>
    public class changeSetTantoMaster
    {
        public ChangeSet<ma_user_togo> ma_user_togo { get; set; }
        public ChangeSet<ma_tantokaisya> ma_tantokaisya { get; set; }
        public int id_user { get; set; }
        public Nullable<int> cd_kaisha { get; set; }
        public string cd_category { get; set; }
        public int flg_eigyo { get; set; }
        public bool kbn_kengen_bunrui { get; set; }
        public string password_org { get; set; }
    }

    /// <summary>
    /// data busho
    /// </summary>
    public class bushobunrui : ma_busho
    {
        public Nullable<short> cd_kojyo { get; set; }
    }

    /// <summary>
    /// list data tanto kaisha
    /// </summary>
    public class tantokaisya : ma_tantokaisya
    {
        public int cd_kaisha { get; set; }
        public string nm_kaisha { get; set; }
    }

    /// <summary>
    /// param search
    /// </summary>
    public class tantoMasterParanSearch
    {
        public Nullable<decimal> id_user { get; set; }
        public Nullable<int> cd_kaisha { get; set; }
        public Nullable<short> cd_group { get; set; }
        public string nm_user { get; set; }
        public Nullable<int> cd_busho { get; set; }
        public Nullable<short> cd_team { get; set; }
        public Nullable<int> top { get; set; }
        public Nullable<int> skip { get; set; }
        public string cd_category { get; set; }
    }
    #endregion
}
