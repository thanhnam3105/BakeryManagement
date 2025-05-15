using System;
using System.Collections.Generic;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Http.OData.Query;
// TODO:DbContextの名前空間を指定します。
using Tos.Web.Data;
using Tos.Web.Controllers;
using System.Web.Http.OData;
using System.Data.Common;
using System.Data.Objects;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class _202_G_LabelRenkeiController : ApiController
    {
        private HttpResponseMessage kekka;
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡されたheaderのキー項目をもとにheaderとdetailの情報を取得します。
        /// </summary>
        /// <param name="no_seq">headerのキー項目</param>
        /// <returns>ChangeResponse</returns>
        //public ChangeResponse Get(int no_seq)
        public HttpResponseMessage Get([FromUri] GLabelRenkeiRequest value)
        {
            try
            {

                // TODO:header情報を管理しているDbContextとheader,detailの型を指定します。

                using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
                {
                    context.Configuration.ProxyCreationEnabled = false;

                    // header検索
                    //var header = (from m in context./*TODO:headerの型を指定します*/
                    //        where m./*TODO:headerのキー項目を指定します*/ == no_seq
                    //        select m).SingleOrDefault();
                    var header = context.sp_shohinkaihatsu_headersearch_202(value.no_seiho).FirstOrDefault();
                    if (header == null)
                    {
                        //throw new HttpException((int)HttpStatusCode.NotFound, Properties.Resources.NoDataExsists);
                        return Request.CreateErrorResponse(HttpStatusCode.NotFound, Properties.Resources.NoDataExsists);
                    }
                    GLabelRenkeiChangeResponse result = new GLabelRenkeiChangeResponse();
                    result.Header = header;

                    // detail検索
                    //result.Detail = (from k in context./*TODO:detailの型を指定します*/
                    //        where k./*TODO:detailのキー項目を指定します*/ == no_seq
                    //        select k).ToList();
                    result.Detail = context.sp_shohinkaihatsu_detailsearch_202(value.no_seiho, value.skip, value.top).ToList();

                    //return result;
                    return Request.CreateResponse<GLabelRenkeiChangeResponse>(HttpStatusCode.OK, result);
                }
            }
            catch (Exception ex)
            {
                Logger.App.Error(ex.Message, ex);
                return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
            }

            // TODO: 上記実装を行った後に下の行は削除します
            //return null;
        }

        /// <summary>
        /// パラメータで受け渡された製品コードを元に版数の情報を取得します。
        /// </summary>
        /// <param name="cd_seihin">キー項目</param>
        /// <returns>ChangeResponse</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_dropdown_202_Result> Get_dropdown(string cd_hin)
        {
            // TODO:header情報を管理しているDbContextとheaderの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;


            // TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。
            StoredProcedureResult<sp_shohinkaihatsu_dropdown_202_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_dropdown_202_Result>();
            result.Items = context.sp_shohinkaihatsu_dropdown_202(cd_hin).ToList();

            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;

            // TODO: 上記実装を行った後に下の行は削除します
            //return null;
        }

        /// <summary>
        /// パラメータで受け渡された製品コード、版数を元に選択している版数が既に確定されていないか確認します。
        /// </summary>
        /// <param name="cd_hin,no_han">キー項目</param>
        /// <returns>ChangeResponse</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_validation_202_Result> Get_kakutei(string cd_hin, decimal no_han)
        {
            // TODO:header情報を管理しているDbContextとheaderの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;


            // TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。
            StoredProcedureResult<sp_shohinkaihatsu_validation_202_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_validation_202_Result>();
            result.Items = context.sp_shohinkaihatsu_validation_202(cd_hin, no_han).ToList();

            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;

            // TODO: 上記実装を行った後に下の行は削除します
            //return null;
        }

        /// <summary>
        /// パラメータで受け渡された OData クエリに該当するすべてのheader情報を取得します。
        /// </summary>
        /// <param name="options"> OData クエリ</param>
        /// <returns>header情報</returns>
        //public PageResult<object/*TODO:headerの型を指定します*/> Get(ODataQueryOptions<object/*TODO:headerの型を指定します*/> options)
        //{
        //    // TODO:header情報を管理しているDbContextとheaderの型を指定します。

        //    // /*TODO:header情報を管理しているDbContextを指定します*/ context = new /*TODO:header情報を管理しているDbContextを指定します*/();
        //    // context.Configuration.ProxyCreationEnabled = false;
        //    // IQueryable results = options.ApplyTo(context./*TODO:headerの型を指定します*/.AsQueryable());
        //    // return new PageResult</*TODO:headerの型を指定します*/>(results as IEnumerable</*TODO:headerの型を指定します*/>, Request.GetNextPageLink(), Request.GetInlineCount());

        //    // TODO: 上記実装を行った後に下の行は削除します
        //    return null;
        //}

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのheader情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>header情報</returns>
        //public StoredProcedureResult<object /* StoredProcedure の戻り値となる複合型を指定します。 */> Get(/*TODO: StoredProcedure のパラメータを指定します*/)
        //{
        //    // TODO:header情報を管理しているDbContextとheaderの型を指定します。

        //    // /*TODO:header情報を管理しているDbContextを指定します*/ context = new /*TODO:header情報を管理しているDbContextを指定します*/();
        //    // context.Configuration.ProxyCreationEnabled = false;

        //    // TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
        //    // ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
        //    // StoredProcedureResult<ma_kengen_view> result = new StoredProcedureResult<ma_kengen_view>();
        //    // result.Items = context.CallStoredProcedure(cd_shain, count).ToList();
        //    // result.Count = (int)count.Value;

        //    // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
        //    // return result;

        //    // TODO: 上記実装を行った後に下の行は削除します
        //    return null;
        //}


        /// <summary>
        /// パラメータで受け渡された 情報をもとにシサクイックDBにLOGを登録します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post_Log([FromBody] HozonDetailRequest options)
        {
            List<string> list = new List<string>();
            if (options == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        foreach (var item in options.condition)
                        {
                            var option = new HozonDetailRequest(item, options.no_seiho, options.date);
                        }

                        var user = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
                        CommonController common = new CommonController();
                        var log = new Event_Log
                        {
                            no_seiho = options.no_seiho,
                            cd_tanto_kaisha = Convert.ToInt32(user.cd_kaisha),
                            cd_tanto = user.EmployeeCD.ToString(),
                            cd_koshin = user.EmployeeCD.ToString(),
                            nm_shori = "G-Label連携",
                            nm_ope = options.condition[0].cd_seihin,
                            dt_shori = options.date,
                            ip_address = common.GetIPClientAddress(),
                            kbn_system = 0
                        };
                        ObjectParameter insert_kekka = new ObjectParameter("kekka", typeof(int));
                        context.sp_shohinkaihatsu_insert_log_202(log.no_seiho
                                                    , log.cd_tanto_kaisha
                                                    , log.cd_tanto
                                                    , log.cd_koshin
                                                    , log.nm_shori
                                                    , log.nm_ope
                                                    , log.dt_shori
                                                    , log.ip_address
                                                    , log.kbn_system
                                                    , insert_kekka);
                        int sp_kekka = (int)insert_kekka.Value;

                        transaction.Commit();

                        if (sp_kekka == 0)
                        {
                            return Request.CreateResponse(HttpStatusCode.OK, "OK");
                        }
                        else
                        {
                            return Request.CreateResponse(HttpStatusCode.OK, "NG");
                        }
                    }
                    catch (Exception ex)
                    {
                        // 例外をエラーログに出力します。
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        //return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                        return Request.CreateResponse(HttpStatusCode.OK, "NG");
                    }
                }
            }
        }

        /// <summary>
        /// パラメータで受け渡された 情報をもとにG3DBにデータを連携します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post_G3([FromBody] HozonDetailRequest options)
        {
            List<string> list = new List<string>();
            if (options == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            using (QPDATA2Entities context = new QPDATA2Entities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        foreach (var item in options.condition)
                        {
                            var option = new HozonDetailRequest(item, options.no_seiho, options.date);
                        }

                        var user = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
                        var renkei = new Renkei
                        {
                            id_user = user.EmployeeCD.ToString(),
                            cd_hin = options.condition[0].cd_seihin,
                            no_han = options.condition[0].no_han,
                            kbn_meisho = options.condition[0].kbn_meisho,
                            nm_meisho_hinmei = options.condition[0].nm_meisho_hinmei,
                            kbn_shomikikan = options.condition[0].kbn_shomikikan,
                            kbn_shomikikan_seizo_fukumu = options.condition[0].kbn_shomikikan_seizo_fukumu,
                            su_shomikikan_free_input = options.condition[0].su_shomikikan_free_input,
                            kbn_shomikikan_tani = options.condition[0].kbn_shomikikan_tani,
                            su_naiyoryo = options.condition[0].su_naiyoryo,
                            cd_naiyoryo_tani = options.condition[0].cd_naiyoryo_tani,
                            nm_toriatsukai_ondo = options.condition[0].nm_toriatsukai_ondo,
                            nm_seihintokusechi_kenshisakuhin = options.condition[0].nm_seihintokusechi_kenshisakuhin,
                            no_seiho = options.no_seiho,
                            cd_kaisha = user.cd_kaisha
                        };
                        ObjectParameter p_g3kekka = new ObjectParameter("g3_kekka", typeof(int));
                        context.sp_shohinkaihatsu_g3_save_202(renkei.id_user
                                                    , renkei.cd_hin
                                                    , renkei.no_han
                                                    , renkei.kbn_meisho
                                                    , renkei.nm_meisho_hinmei
                                                    , renkei.kbn_shomikikan
                                                    , renkei.kbn_shomikikan_seizo_fukumu
                                                    , renkei.su_shomikikan_free_input
                                                    , renkei.kbn_shomikikan_tani
                                                    , renkei.su_naiyoryo
                                                    , renkei.cd_naiyoryo_tani
                                                    , renkei.nm_toriatsukai_ondo
                                                    , renkei.nm_seihintokusechi_kenshisakuhin
                                                    , renkei.no_seiho
                                                    ,renkei.cd_kaisha
                                                    , p_g3kekka);
                        int sp_kekka = (int)p_g3kekka.Value;

                        transaction.Commit();

                        if (sp_kekka == 0)
                        {
                            return Request.CreateResponse(HttpStatusCode.OK, "OK");
                        }
                        else if (sp_kekka == 3)
                        {
                            return Request.CreateResponse(HttpStatusCode.OK, "LOGIN");
                        }
                        else if (sp_kekka == 4)
                        {
                            return Request.CreateResponse(HttpStatusCode.OK, "HENSYU");
                        }
                        else
                        {
                            return Request.CreateResponse(HttpStatusCode.OK, "NG");
                        }
                    }
                    catch (Exception ex)
                    {
                        // 例外をエラーログに出力します。
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        //return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                        return Request.CreateResponse(HttpStatusCode.OK, "NG");
                    }
                }
            }
        }

        //private string Ltrim(string p1, string p2)
        //{
        //    throw new NotImplementedException();
        //}

        //private string Number(string p)
        //{
        //    throw new NotImplementedException();
        //}


        /// <summary>
        /// パラメータで受け渡された 情報をもとにシサクイックDBのLOGを削除します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post_Delete_LOG([FromBody] HozonDetailRequest options)
        {
            List<string> list = new List<string>();
            if (options == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        foreach (var item in options.condition)
                        {
                            var option = new HozonDetailRequest(item, options.no_seiho, options.date);
                        }

                        var user = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
                        var log = new Event_Log
                        {
                            no_seiho = options.no_seiho,
                            cd_tanto = user.EmployeeCD.ToString(),
                            dt_shori = options.date
                        };
                        context.sp_shohinkaihatsu_delete_log_202(log.no_seiho
                                                    , log.cd_tanto
                                                    , log.dt_shori);

                        transaction.Commit();
                    }
                    catch (Exception ex)
                    {
                        // 例外をエラーログに出力します。
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        //return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                        return Request.CreateResponse(HttpStatusCode.OK, "NG");
                    }
                }
            }
            // TODO: 保存処理を実行します。
            return Request.CreateResponse(HttpStatusCode.OK, "OK");
        }

        //public HttpResponseMessage Post([FromBody]ChangeRequest value)
        //{

        //    if (value == null)
        //    {
        //        return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
        //    }

        //    // TODO：整合性チェックエラーの結果を格納するInvalidationSetを定義します。
        //    //InvalidationSet</*TODO:headerの型を指定します*/> headerInvalidations = ValidateHeader(value.Header);
        //    //if (headerInvalidations.Count > 0)
        //    //{
        //    //    return Request.CreateResponse<InvalidationSet</*TODO:headerの型を指定します*/>>(HttpStatusCode.BadRequest, headerInvalidations);
        //    //}

        //    //InvalidationSet</*TODO:detailの型を指定します*/> detailInvalidations = ValidateDetail(value.Detail);
        //    //if (detailInvalidations.Count > 0)
        //    //{
        //    //    return Request.CreateResponse<InvalidationSet</*TODO:detailの型を指定します*/>>(HttpStatusCode.BadRequest, detailInvalidations);
        //    //}
        //    // TODO: ここまで

        //    // TODO:header情報を管理しているDbContextとheader、detailの型を指定します。

        //    //using (/*TODO:header/detail情報を管理しているDbContextを指定します*/ context = new /*TODO:header/detail情報を管理しているDbContextを指定します*/())
        //    //{
        //    //   IObjectContextAdapter adapter = context as IObjectContextAdapter;
        //    //   DbConnection connection = adapter.ObjectContext.Connection;
        //    //   connection.Open();

        //    //   using (DbTransaction transaction = connection.BeginTransaction())
        //    //   {
        //    //       // TODO: header部の保存処理を実行します。
        //    //       var no_seq = SaveHeader(context, value.Header);

        //    //       // TODO: detail部の保存処理を実行します。
        //    //       SaveDetail(context, value.Detail, no_seq);

        //    //       transaction.Commit();
        //    //   }
        //    //}

        //    // TODO: 返却用のオブジェクトを生成します。
        //    var result = new ChangeResponse();
        //    result.Header = value.Header.Flatten().SingleOrDefault();
        //    result.Detail.AddRange(value.Detail.Flatten());

        //    return Request.CreateResponse<ChangeResponse>(HttpStatusCode.OK, result);
        //}

        /// <summary>
        /// パラメータで受け渡されたheader情報をもとにheader情報を論理削除します。
        /// </summary>
        /// <param name="value">header情報</param>
        /// <returns>header情報</returns>
        //[HttpPost]
        //public RenkeiChangeResponse Remove([FromBody]ChangeSet<object/*TODO:headerの型を指定します*/> value)
        //{
        //    // TODO:header情報を管理しているDbContextとheaderの型を指定します。

        //    // using (/*TODO:header情報を管理しているDbContextを指定します*/ context = new /*TODO:header情報を管理しているDbContextを指定します*/())
        //    // {
        //    //     // TODO: header部の保存処理を実行します。
        //    //     SaveHeader(context, value);
        //    // }

        //    var result = new RenkeiChangeResponse();
        //    result.Renkei = value.Flatten().SingleOrDefault();

        //    return result;
        //}

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// ヘッダー情報の整合性チェックを行います。
        ///  TODO: エンティティに対する整合性チェック (マスタ存在チェックなど) を行います。
        /// </summary>
        /// <param name="changeSet">ヘッダーの変更セット</param>
        /// <returns></returns>
        private InvalidationSet<object /*TODO:headerの型を指定します*/> ValidateHeader(ChangeSet<object /*TODO:headerの型を指定します*/> changeSet)
        {
            InvalidationSet<object /*TODO:headerの型を指定します*/> invalidations = new InvalidationSet<object /*TODO:headerの型を指定します*/>();

            // TODO: ヘッダーのサーバー入力検証

            foreach (var item in changeSet.Created)
            {
            }


            foreach (var item in changeSet.Updated)
            {
            }

            return invalidations;
        }

        /// <summary>
        /// 明細一覧情報の整合性チェックを行います。
        ///  TODO: エンティティに対する整合性チェック (マスタ存在チェックなど) を行います。
        /// </summary>
        /// <param name="changeSet">明細一覧の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<object /*TODO:detailの型を指定します*/> ValidateDetail(ChangeSet<object /*TODO:detailの型を指定します*/> changeSet)
        {

            InvalidationSet<object /*TODO:detailの型を指定します*/> invalidations = new InvalidationSet<object /*TODO:detailの型を指定します*/>();

            // TODO: 明細のサーバー入力検証
            foreach (var item in changeSet.Created)
            {
            }


            foreach (var item in changeSet.Updated)
            {
            }

            return invalidations;
        }

        /// <summary>
        /// hedaer情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="header">hedaer情報</param>
        /// <returns>headerの新規キー項目</returns>
        //private int SaveHeader(System.Data.Entity.DbContext/*TODO:header情報を管理しているDbContextを指定します*/ context, ChangeSet<object/*TODO:headerの型を指定します*/> header)
        //{
        //    int newId = -1;

        //    // TODO: 採番ロジックなどでキー項目を採番する場合はここで処理を実行します。
        //    //       identity列などを利用してデータ作成時に自動採番する場合は以下の処理をコメントアウトしてください。
        //    // newId = 新規採番キー取得ロジック;

        //    // TODO: キー項目の設定処理を実行します。
        //    //if (header.Created.Count > 0)
        //    //{
        //    //    foreach (var item in header.Created)
        //    //    {
        //    //        item.no_seq = newId;
        //    //    }
        //    //}

        //    // TODO: 登録日、ユーザー、更新日、ユーザーをセットします。
        //    header.SetDataSaveInfo(this.User.Identity);
        //    header.AttachTo(context);
        //    context.SaveChanges();

        //    // TODO: identity列などを利用してデータ作成時に自動採番する場合は、作成結果から付番されたキー項目を取得します。
        //    if (header.Created.Count > 0)
        //    {
        //        // newId = ((object/*TODO:headerの型を指定します*/)header.Created.First());/*TODO:headerのIDを指定します .no_seq;*/
        //    }

        //    return newId;
        //}

        /// <summary>
        /// detail情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="context">DbContext</param>
        /// <param name="detail">detail情報</param>
        /// <param name="newId">headerのキー項目</param>
        private void SaveDetail(System.Data.Entity.DbContext/*TODO:detail情報を管理しているDbContextを指定します*/ context, ChangeSet<object/*TODO:detailの型を指定します*/> detail, int newId = -1)
        {
            if (newId > 0)
            {
                // TODO: header情報で採番されたキー項目をdetail情報に設定します。
                foreach (var item in detail.Created)
                {
                    //item.no_seq = newId;
                }
            }

            // TODO: 登録日、ユーザー、更新日、ユーザーをセットします。
            detail.SetDataSaveInfo(this.User.Identity);
            detail.AttachTo(context);
            context.SaveChanges();
        }
        #endregion

        public CheckRow condition { get; set; }

        public HttpResponseMessage shorikekka { get; set; }

        public DateTime time_shori { get; set; }
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class GLabelRenkeiChangeRequest
    {
        public ChangeSet<sp_shohinkaihatsu_headersearch_202_Result> Header { get; set; }

        public ChangeSet<sp_shohinkaihatsu_detailsearch_202_Result> Detail { get; set; }
    }

    public class GLabelRenkeiRequest
    {
        public string no_seiho { get; set; }
        public int skip { get; set; }
        public int top { get; set; }
    }

    public class GLabelRenkeiChangeResponse
    {
        public GLabelRenkeiChangeResponse()
        {
            this.Header = new sp_shohinkaihatsu_headersearch_202_Result();
            this.Detail = new List<sp_shohinkaihatsu_detailsearch_202_Result>();
        }

        public sp_shohinkaihatsu_headersearch_202_Result Header { get; set; }
        public List<sp_shohinkaihatsu_detailsearch_202_Result> Detail { get; set; }
    }

    public class CheckRow
    {
        public int no_yusen { get; set; }
        public int cd_seihin { get; set; }
        public string nm_seihin { get; set; }
        public string nisugata_hyoji { get; set; }
        public decimal? no_han { get; set; }
        public string kbn_meisho { get; set; }
        public string nm_meisho_hinmei { get; set; }
        public string kbn_shomikikan { get; set; }
        public string kbn_shomikikan_seizo_fukumu { get; set; }
        public int su_shomikikan_free_input { get; set; }
        public string kbn_shomikikan_tani { get; set; }
        public string su_naiyoryo { get; set; }
        public int cd_naiyoryo_tani { get; set; }
        public int nm_toriatsukai_ondo { get; set; }
        public string nm_seihintokusechi_kenshisakuhin { get; set; }
    }

    public class Renkei
    {
        public string id_user { get; set; }
        public int cd_hin { get; set; }
        public decimal? no_han { get; set; }
        public string kbn_meisho { get; set; }
        public string nm_meisho_hinmei { get; set; }
        public string kbn_shomikikan { get; set; }
        public string kbn_shomikikan_seizo_fukumu { get; set; }
        public int su_shomikikan_free_input { get; set; }
        public string kbn_shomikikan_tani { get; set; }
        public string su_naiyoryo { get; set; }
        public int cd_naiyoryo_tani { get; set; }
        public int nm_toriatsukai_ondo { get; set; }
        public string nm_seihintokusechi_kenshisakuhin { get; set; }
        public string no_seiho { get; set; }
        public string cd_kaisha { get; set; }
    }

    public class HozonDetailRequest
    {
        public List<CheckRow> condition { get; set; }
        public string no_seiho { get; set; }
        public DateTime? date { get; set; }

        public HozonDetailRequest() { }
        public HozonDetailRequest(CheckRow option, string no_seiho, DateTime? date)
        {
            this.condition = new List<CheckRow>() { option };
            this.no_seiho = no_seiho;
            this.date = date;
        }
    }

    public partial class Event_Log
    {
        public string no_seiho { get; set; }
        public int cd_tanto_kaisha { get; set; }
        public string cd_tanto { get; set; }
        public string cd_koshin { get; set; }
        public string nm_shori { get; set; }
        public int nm_ope { get; set; }
        public DateTime? dt_shori { get; set; }
        public string ip_address { get; set; }
        public byte kbn_system { get; set; }
    }

    public class RenkeiChangeRequest
    {
        public ChangeSet<CheckRow> Renkei { get; set; }
    }

    public class RenkeiChangeResponse
    {
        public RenkeiChangeResponse()
        {
            this.Renkei = new ChangeSet<CheckRow>();
        }

        public ChangeSet<CheckRow> Renkei { get; set; }
    }

    #endregion
}
