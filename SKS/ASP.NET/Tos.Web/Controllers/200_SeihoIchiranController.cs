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
using System.Data.Common;
using System.Data.Entity.Infrastructure;
using Tos.Web.Logging;
using System.Data;
using Tos.Web.Properties;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _200_SeihoIchiranController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された OData クエリに該当するすべてのtarget情報を取得します。
        /// </summary>
        /// <param name="options"> OData クエリ</param>
        /// <returns>target情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_200_Result> Get([FromUri] Filter param)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。
            //Filter param = new Filter();
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;
            //((IObjectContextAdapter)context).ObjectContext.CommandTimeout = 0;
            ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = Settings.Default.TimeOutIchiran_200;
            //IQueryable results = options.ApplyTo(context./*TODO:targetの型を指定します*/.AsQueryable());
            // return new PageResult</*TODO:targetの型を指定します*/>(results as IEnumerable</*TODO:targetの型を指定します*/>, Request.GetNextPageLink(), Request.GetInlineCount());
            StoredProcedureResult<sp_shohinkaihatsu_search_200_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_200_Result>();

            var cd_kensaku = UserInfo.GetUserNameFromIdentity(this.User.Identity).ToString();

            var data = context.sp_shohinkaihatsu_search_200(
                               param.no_seiho1,
                               param.no_seiho2,
                               param.no_seiho3,
                               param.no_seiho4,
                               param.nm_seiho,
                               param.cd_seiho_bunrui,
                               param.dt_seiho_sakusei_start,
                               param.dt_seiho_sakusei_end,
                               param.nm_seiho_sakusei,
                               param.status,
                               param.kbn_haishi,
                               param.cd_seihin,
                               param.nm_seihin,
                               param.nm_haigo,
                               param.cd_kaisha_daihyo,
                               param.cd_kojyo_daihyo,
                               param.nm_seizo,
                               param.cd_hin,
                               param.kbn_shikakari,
                               param.nm_hin,
                               param.no_kikaku1,
                               param.no_kikaku2,
                               param.no_kikaku3,
                               param.nm_kikaku,
                               param.nm_hanbai,
                               param.kbn_hin,
                               param.kbn_kengen,
                               param.cd_kaisha,
                               param.sort,
                               param.top,
                               param.skip,
                               param.mode,
                               cd_kensaku

                ).ToList();
            result.Items = data;
            if (result.Items.Count() > 0)
                result.Count = int.Parse(data[0].cnt.ToString());
            else
                result.Count = 0;
            //result.Count = result.Items[0].cnt;
            // TODO: 上記実装を行った後に下の行は削除します
            return result;
        }


        /// <summary>
        /// パラメータで受け渡された OData クエリに該当するすべてのtarget情報を取得します。
        /// </summary>
        /// <param name="options"> OData クエリ</param>
        /// <returns>target情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_200_gethinmei_Result> GetHin([FromUri] param param)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。
            //Filter param = new Filter();
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;
            //IQueryable results = options.ApplyTo(context./*TODO:targetの型を指定します*/.AsQueryable());
            // return new PageResult</*TODO:targetの型を指定します*/>(results as IEnumerable</*TODO:targetの型を指定します*/>, Request.GetNextPageLink(), Request.GetInlineCount());
            StoredProcedureResult<sp_shohinkaihatsu_search_200_gethinmei_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_200_gethinmei_Result>();


            result.Items = context.sp_shohinkaihatsu_search_200_gethinmei(param.cd_kaisha
                                                                        , param.cd_kojyo
                                                                        , param.cd_hin
                                                                        , param.no_seiho_kaisha
                                                                        ).ToList();

            return result;
        }


        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody] ChangeSet<ma_haigo_header> value)
        {
            if (value == null)
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
                        //Tosvn: Delete data in ma_haigo_header table
                        ChangeSet<ma_haigo_header> haigo_header = new ChangeSet<ma_haigo_header>();
                        haigo_header.Deleted.AddRange(value.Deleted);
                        haigo_header.AttachTo(context);
                        context.SaveChanges();

                        var cd_haigo = value.Deleted[0].cd_haigo;
                        //Tosvn: Delete data in ma_haigo_meisai table
                        ChangeSet<Tos.Web.Data.ma_haigo_meisai> haigo_meisai = new ChangeSet<Tos.Web.Data.ma_haigo_meisai>();
                        haigo_meisai.Deleted.AddRange(context.ma_haigo_meisai.Where(x => x.cd_haigo == cd_haigo).ToList());
                        haigo_meisai.AttachTo(context);
                        context.SaveChanges();

                        //Tosvn: Delete data in ma_seizo_line table
                        ChangeSet<Tos.Web.Data.ma_seizo_line> seizo_line = new ChangeSet<Tos.Web.Data.ma_seizo_line>();
                        seizo_line.Deleted.AddRange(context.ma_seizo_line.Where(x => x.cd_haigo == cd_haigo).ToList());
                        seizo_line.AttachTo(context);
                        context.SaveChanges();

                        //Tosvn: Update data in ma_seihin_seiho table (don't have primary key)
                        List<ma_seihin_seiho> seihin_seiho = new List<ma_seihin_seiho>();
                        seihin_seiho = context.ma_seihin_seiho.Where(x => x.cd_haigo == cd_haigo).ToList();

                        //Tosvn: Get infor of user login
                        var user = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
                        DateTime date = DateTime.Now;

                        foreach (dynamic item in seihin_seiho)
                        {
                            item.cd_koshin = user.EmployeeCD.ToString();
                            item.dt_henko = date;
                            item.cd_koshin_kaisha = Convert.ToInt32(user.cd_kaisha);

                            context.sp_shohinkaihatsu_update_200_seihinseiho(item.cd_hin
                                                           , item.cd_haigo
                                                           , item.cd_koshin
                                                           , item.dt_henko
                                                           , item.cd_koshin_kaisha
                                                           , item.ts);
                        }

                        //Tosvn: insert tr_event_log (don't have primary key)
                        CommonController common = new CommonController();
                        var log = new tr_event_log
                        {
                            no_seiho = value.Deleted[0].no_seiho,
                            cd_tanto_kaisha = Convert.ToInt32(user.cd_kaisha),
                            cd_tanto = user.EmployeeCD.ToString(),
                            cd_koshin = user.EmployeeCD.ToString(),
                            nm_shori = "製法一覧",
                            nm_ope = "削除",
                            dt_shori = date,
                            ip_address = common.GetIPClientAddress(),
                            kbn_system = 0
                        };
                        context.sp_shohinkaihatsu_insert_200_event_log(log.no_seiho
                                                    , log.cd_tanto_kaisha
                                                    , log.cd_tanto
                                                    , log.cd_koshin
                                                    , log.nm_shori
                                                    , log.nm_ope
                                                    , log.dt_shori
                                                    , log.ip_address
                                                    , log.kbn_system);

                        transaction.Commit();
                    }
                    catch (Exception ex)
                    {
                        // 例外をエラーログに出力します。
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                    }
                }
            }
            // TODO: 保存処理を実行します。
            //var result = new HaigoChangeResponse();
            return Request.CreateResponse(HttpStatusCode.OK, "OK");
        }

        #endregion

    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class Filter
    {
        public string no_seiho1 { get; set; }
        public string no_seiho2 { get; set; }
        public string no_seiho3 { get; set; }
        public string no_seiho4 { get; set; }
        public string nm_seiho { get; set; }
        public Nullable<Int16> cd_seiho_bunrui { get; set; }
        public Nullable<DateTime> dt_seiho_sakusei_start { get; set; }
        public Nullable<DateTime> dt_seiho_sakusei_end { get; set; }
        public string nm_seiho_sakusei { get; set; }
        public Nullable<byte> status { get; set; }
        public Nullable<byte> kbn_haishi { get; set; }
        public Nullable<Decimal> cd_seihin { get; set; }
        public string nm_seihin { get; set; }
        public string nm_haigo { get; set; }
        public Nullable<Int16> cd_kaisha_daihyo { get; set; }
        public Nullable<Int16> cd_kojyo_daihyo { get; set; }
        public string nm_seizo { get; set; }
        public Nullable<Decimal> cd_hin { get; set; }
        public Nullable<byte> kbn_shikakari { get; set; }
        public string nm_hin { get; set; }
        public string no_kikaku1 { get; set; }
        public string no_kikaku2 { get; set; }
        public string no_kikaku3 { get; set; }
        public string nm_kikaku { get; set; }
        public string nm_hanbai { get; set; }
        public Nullable<byte> kbn_hin { get; set; }
        public int kbn_kengen { get; set; }
        public Nullable<Int16> cd_kaisha { get; set; }
        public Byte sort { get; set; }
        public int skip { get; set; }
        public int top { get; set; }
        public int mode { get; set; }
        public byte set_haishi { get; set; }
        public byte set_hin { get; set; }
        public string set_category { get; set; }
        public int set_kengen { get; set; }
        public byte set_mishiyo { get; set; }
        public byte set_denso_taisho { get; set; }

    }


    public class param
    {
        public Nullable<Int16> cd_kaisha { get; set; }
        public Nullable<Int16> cd_kojyo { get; set; }
        public Nullable<Decimal> cd_hin { get; set; }
        public Nullable<Int16> no_seiho_kaisha { get; set; }
    }
    /// <summary>
    /// data kojyo
    /// </summary>
    public class kojyo_busho
    {
        public short cd_kojyo { get; set; }
        public string nm_busho { get; set; }
    }

    #endregion
}
