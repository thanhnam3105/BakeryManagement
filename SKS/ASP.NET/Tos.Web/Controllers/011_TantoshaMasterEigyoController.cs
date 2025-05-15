using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Tos.Web.Data;
using System.Data.Entity.Infrastructure;
using System.Data.Common;
using Tos.Web.Properties;
using System.Data;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class TantoshaMasterEigyoController : ApiController
    {
        #region "Controllerで公開するAPI"


        /// <summary>
        /// GetData
        /// </summary>
        /// <param name="param"></param>
        /// <returns> sp_shohinkaihatsu_search_011_Result </returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_011_Result> GetData([FromUri] MasterParanSearch param)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;

            //Tosvn: Get infor of user login
            var user = UserInfo.CreateFromAuthorityMaster(this.User.Identity);

            //TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
            //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
            StoredProcedureResult<sp_shohinkaihatsu_search_011_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_011_Result>();
            result.Items = context.sp_shohinkaihatsu_search_011(
                                    param.id_user, user.EmployeeCD, param.nm_user, param.cd_kaisha, param.cd_busho, param.id_kino,
                                    short.Parse(Resources.kbn_eigyo_1), short.Parse(Resources.kbn_eigyo_2), short.Parse(Resources.kbn_eigyo_3),
                                    short.Parse(Resources.kengen_eigyo_48), short.Parse(Resources.kengen_eigyo_114), short.Parse(Resources.kengen_eigyo_50),
                                    param.top, param.skip
                            ).ToList();

            if (result.Items.Count() > 0)
            {

                result.Count = (int)result.Items.First().counts;
            }

            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;
        }

        /// <summary>
        /// GetMember
        /// </summary>
        /// <param name="id_user"></param>
        /// <returns> mamember </returns>
        public getUserNew GetMaUserNew(decimal id_user)
        {
            // TODO: target 情報を管理しているDbContextと target の型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                //var newuser = context.ma_user_new.Where(x => x.id_user == id_user).FirstOrDefault();
                var usernew = (from newuser in (context.ma_user_new.Where(x => x.id_user == id_user))
                               from kengen in context.ma_kengen.Where(x => x.cd_kengen == newuser.cd_kengen).DefaultIfEmpty()
                               select new getUserNew
                               {
                                   id_user = newuser.id_user,
                                   password = newuser.password,
                                   cd_kengen = newuser.cd_kengen,
                                   nm_user = newuser.nm_user,
                                   cd_kaisha = newuser.cd_kaisha,
                                   cd_busho = newuser.cd_busho,
                                   dt_toroku = newuser.dt_toroku,
                                   cd_group = newuser.cd_group,
                                   cd_team = newuser.cd_team,
                                   cd_yakushoku = newuser.cd_yakushoku,

                                   kbn_eigyo = kengen.kbn_eigyo
                               }).FirstOrDefault();


                return usernew;
            }
        }

        /// <summary>
        /// GetMember
        /// </summary>
        /// <param name="id_user"></param>
        /// <returns> mamember </returns>
        public List<mamember> GetMember(decimal id_user)
        {
            // TODO: target 情報を管理しているDbContextと target の型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                return (from member in (context.ma_member.Where(x => x.id_user == id_user))
                        from user in context.ma_user_togo.Where(x => x.id_user == member.id_member).Take(1).DefaultIfEmpty()
                        select new mamember
                        {
                            id_user = member.id_user,
                            id_member = member.id_member,
                            no_sort = member.no_sort,
                            id_toroku = member.id_toroku,
                            dt_toroku = member.dt_koshin,
                            id_koshin = member.id_koshin,
                            dt_koshin = member.dt_koshin,

                            nm_member = user.nm_user
                        }).ToList();
            }
        }

        /// <summary>
        /// GetComboboxKaisha
        /// </summary>
        /// <param name=""></param>
        /// <returns> combokaisha </returns>
        public List<combokaisha> GetComboboxKaisha()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                var flag_eigyo = short.Parse(Properties.Resources.flag_eigyo);
                return (from busho in (context.ma_busho.Where(x => x.flg_eigyo == flag_eigyo))
                        from kaisha in context.ma_kaisha.Where(x => x.cd_kaisha == busho.cd_kaisha)
                        select new combokaisha
                        {
                            cd_kaisha = busho.cd_kaisha,
                            nm_kaisha = kaisha.nm_kaisha,
                        })
                        .Distinct()
                        .OrderBy(x => x.cd_kaisha)
                        .ToList();
            }
        }

        /// <summary>
        /// GetComboboxBusho
        /// </summary>
        /// <param name="cd_kaisha"></param>
        /// <returns> combobusho </returns>
        public List<combobusho> GetComboboxBusho(int cd_kaisha, int? cd_kengen)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                var kbn_eigyo = (from kengen in context.ma_kengen.Where(x => x.cd_kengen == cd_kengen) select kengen.kbn_eigyo).FirstOrDefault();
                var flag_eigyo = short.Parse(Properties.Resources.flag_eigyo);
                var flag_honbu = short.Parse(Properties.Resources.flag_honbu);
                if (kbn_eigyo == 1 || cd_kengen == null)
                {
                    return (from busho in context.ma_busho.Where(x => x.cd_kaisha == cd_kaisha && x.flg_eigyo == flag_eigyo)
                            select new combobusho
                            {
                                cd_busho = busho.cd_busho,
                                nm_busho = busho.nm_busho,
                            })
                        .OrderBy(x => x.cd_busho)
                        .ToList();
                }
                else
                {
                    return (from busho in context.ma_busho.Where(x => x.cd_kaisha == cd_kaisha && x.flg_eigyo == flag_eigyo && x.flg_honbu == flag_honbu)
                            select new combobusho
                            {
                                cd_busho = busho.cd_busho,
                                nm_busho = busho.nm_busho,
                            })
                        .OrderBy(x => x.cd_busho)
                        .ToList();
                }

            }
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]changeSetTantoMasterEigyo value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            // TODO: キー項目の重複チェックを行います。
            InvalidationSet<ma_user_togo> headerInvalidations = userIsAlreadyExists(value.ma_user_togo);
            if (headerInvalidations.Count > 0)
            {
                return Request.CreateResponse<InvalidationSet<ma_user_togo>>(HttpStatusCode.BadRequest, headerInvalidations);
            }

            // TODO: 保存処理を実行します。
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {

                    // TODO: 保存処理を実行します。
                    SaveUserData(context, value.ma_user_togo);
                    SaveMemberData(context, value.ma_member);

                    transaction.Commit();
                }
                List<ma_member> member = context.ma_member.Where(x => x.id_user == value.id_user).ToList();
                for (int i = 0; i < member.Count; i++ )
                {
                    member[i].no_sort = (short)(i+1);
                    context.ma_member.Attach(member[i]);
                    context.Entry<ma_member>(member[i]).State = EntityState.Modified;
                    context.SaveChanges();
                }

            }

            return Request.CreateResponse<string>(HttpStatusCode.OK, "");

        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Put([FromBody]changeSetTantoMasterEigyo value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        // TODO: キー項目の重複チェックを行います。
                        InvalidationSet<ma_user_togo> userInvalidations = userIsAlreadyExists(value.ma_user_togo);
                        if (userInvalidations.Count > 0)
                        {
                            return Request.CreateResponse<InvalidationSet<ma_user_togo>>(HttpStatusCode.BadRequest, userInvalidations);
                        }

                        // TODO: 保存処理を実行します。
                        SaveUserData(context, value.ma_user_togo);

                        SaveMemberData(context, value.ma_member);

                        transaction.Commit();
                    }
                    catch (DbUpdateConcurrencyException ex)
                    {
                        // 排他エラー
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        return Request.CreateErrorResponse(HttpStatusCode.Conflict, Properties.Resources.ServiceError);
                    }
                    catch (Exception ex) {
                        // 例外をエラーログに出力します。
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                    }
                }
                List<ma_member> member = context.ma_member.Where(x => x.id_user == value.id_user).OrderBy(x=>x.no_sort).ToList();
                for (int i = 0; i < member.Count; i++)
                {
                    member[i].no_sort = (short)(i + 1);
                    context.ma_member.Attach(member[i]);
                    context.Entry<ma_member>(member[i]).State = EntityState.Modified;
                    context.SaveChanges();
                }
            }

            return Request.CreateResponse<string>(HttpStatusCode.OK, "");
        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を削除します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Delete([FromBody]changeSetTantoMasterEigyo value)
        {

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {

                    // TODO: 保存処理を実行します。
                    SaveUserData(context, value.ma_user_togo);

                    //get list member
                    value.ma_member = new ChangeSet<ma_member>();
                    value.ma_member.Deleted.AddRange(context.ma_member.Where(x => x.id_user == value.id_user));

                    SaveMemberData(context, value.ma_member);

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
        private InvalidationSet<ma_user_togo> userIsAlreadyExists(ChangeSet<ma_user_togo> value)
        {
            InvalidationSet<ma_user_togo> result = new InvalidationSet<ma_user_togo>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
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
                        result.Add(new Invalidation<ma_user_togo>(Properties.Resources.ValidationKey, item, "id_user"));
                    }
                    item.dt_password = DateTime.Now;
                }
                foreach (var item in value.Updated)
                {
                    var ischangepassword = context.ma_user_togo.Where(x => x.id_user == item.id_user && x.password == item.password).ToList();
                    if (ischangepassword.Count() == 0)
                    {
                        item.dt_password = DateTime.Now;
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
        private InvalidationSet<ma_member> memberIsAlreadyExists(ChangeSet<ma_member> value, int id_user)
        {
            InvalidationSet<ma_member> result = new InvalidationSet<ma_member>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    item.id_user = id_user;
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.id_user == item.id_user && target.id_member == item.id_member);
                    var isDeleted = value.Deleted.Exists(target => target.id_user == item.id_user && target.id_member == item.id_member);
                    var isDatabaseExists = (context.ma_tantokaisya.Find(item.id_user, item.id_member) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<ma_member>(Properties.Resources.ValidationKey, item, "id_member"));
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
        private void SaveUserData(ShohinKaihatsuEntities context, ChangeSet<ma_user_togo> value)
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
        private void SaveMemberData(ShohinKaihatsuEntities context, ChangeSet<ma_member> value)
        {
            value.SetDataSaveInfo(this.User.Identity);
            value.AttachTo(context);
            context.SaveChanges();
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    //public class SearchChangeResponse
    //{
    //    public SearchChangeResponse()
    //    {
    //        this.Detail = new List<object/*TODO: target の型を指定します*/>();
    //    }

    //    public List<object/*TODO: target の型を指定します*/> Detail { get; set; }
    //}

    /// <summary>
    /// changeset for detail ma_new_user
    /// </summary>
    public class getUserNew : ma_user_new
    {
        public short? kbn_eigyo { get; set; }
    }

    /// <summary>
    /// changeset for detail input
    /// </summary>
    public class changeSetTantoMasterEigyo
    {
        public ChangeSet<ma_user_togo> ma_user_togo { get; set; }
        public ChangeSet<ma_member> ma_member { get; set; }
        public int id_user { get; set; }
        public string nm_member { get; set; }
    }

    /// <summary>
    /// list combobox kaisha
    /// </summary>
    public class combokaisha
    {
        public int cd_kaisha { get; set; }
        public string nm_kaisha { get; set; }
    }
    /// <summary>
    /// list combobox busho
    /// </summary>
    public class combobusho
    {
        public int cd_busho { get; set; }
        public string nm_busho { get; set; }
    }

    /// <summary>
    /// list data member
    /// </summary>
    public class mamember : ma_member
    {
        public string nm_member { get; set; }
    }

    /// <summary>
    /// param search
    /// </summary>
    public class MasterParanSearch
    {
        public Nullable<decimal> id_user { get; set; }
        public string nm_user { get; set; }
        public Nullable<int> cd_kaisha { get; set; }
        public Nullable<int> cd_busho { get; set; }
        public Nullable<int> id_kino { get; set; }
        public Nullable<int> top { get; set; }
        public Nullable<int> skip { get; set; }
    }
    #endregion
}
