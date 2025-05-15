﻿using System;
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

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class _102_GenryoIchiranDialogController : ApiController
    {
        //#region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡されたheaderのキー項目をもとにheaderとdetailの情報を取得します。
        /// </summary>
        /// <param name="no_seq">headerのキー項目</param>
        /// <returns>ResultsMasterData</returns>
        public ResultsMasterData GetMasterData([FromUri] paramSearchDialog param)
        {

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                ResultsMasterData Results = new ResultsMasterData();

                Results.busho = context.ma_busho.Where(x => x.fg_hyoji == param.busho_hyoji).OrderBy(x => x.cd_busho).ToList();
                //会社コード　=　1（キユーピー）の場合、
                //・部署コード　=　1（研究開発本部）　→　【区分／コード一覧＃定数/セッティング用＃新規原料＃新規登録原料】でセット。	
                foreach (var kojo in Results.busho)
                {
                    if (kojo.cd_kaisha == param.cd_kaisha_change_name && kojo.cd_busho == param.cd_busho_change_name)
                    {
                        kojo.nm_busho = param.name_change;
                    }
                }

                if (param.cd_kengen != Properties.Settings.Default.cd_kengen_system_admin)
                {
                    decimal employeeCD = 0;
                    Decimal.TryParse(UserInfo.GetUserNameFromIdentity(this.User.Identity), out employeeCD);

                    Results.kaisha = (from c in context.ma_tantokaisya.Where(x => x.id_user == employeeCD)
                                      join p in context.ma_kaisha on c.cd_tantokaisha equals p.cd_kaisha
                                      select p).OrderBy(x => x.cd_kaisha).ToList();
                }
                else
                {
                    Results.kaisha = context.ma_kaisha.OrderBy(x => x.cd_kaisha).ToList();
                }
                return Results;
            }
        }

        /// <summary>
        /// パラメータで受け渡されたheaderのキー項目をもとにheaderとdetailの情報を取得します。
        /// </summary>
        /// <param name="no_seq">headerのキー項目</param>
        /// <returns>ChangeResponse</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_102_hyoji_kojyo_Result> GetDetailData([FromUri] paramSearchDialog param)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                //bool @flg_hyouji = false;
                string str_kaisha = param.cd_kaisha.ToString();

                bool @flg_hyouji = (context.ma_literal.Where(x => x.cd_category == param.tanka_hyoujigaisha && x.cd_literal == str_kaisha && (x.value1 == param.hyoji1 || x.value1 == param.hyoji9)).FirstOrDefault() != null);

                StoredProcedureResult<sp_shohinkaihatsu_search_102_hyoji_kojyo_Result> Tate = new StoredProcedureResult<sp_shohinkaihatsu_search_102_hyoji_kojyo_Result>();
                Tate.Items = context.sp_shohinkaihatsu_search_102_hyoji_kojyo(param.cd_genryo, param.cd_kaisha, param.nm_genryo, param.cd_busho, @flg_hyouji, param.sankagatsu, param.subeteko, param.top).ToList();
                Tate.Count = 0;

                if (Tate.Items.Count() > 0)
                {
                    Tate.Count = (int)Tate.Items.First().count_item;
                }

                return Tate;
            }
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
        /// パラメータで受け渡されたheader情報・detail情報をもとにエントリーheader情報・detail情報を一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
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
        //public HeaderChangeResponse Remove([FromBody]ChangeSet<object/*TODO:headerの型を指定します*/> value)
        //{
        //    // TODO:header情報を管理しているDbContextとheaderの型を指定します。

        //    // using (/*TODO:header情報を管理しているDbContextを指定します*/ context = new /*TODO:header情報を管理しているDbContextを指定します*/())
        //    // {
        //    //     // TODO: header部の保存処理を実行します。
        //    //     SaveHeader(context, value);
        //    // }

        //    var result = new HeaderChangeResponse();
        //    result.Header = value.Flatten().SingleOrDefault();

        //    return result;
        //}

        //#endregion

        //#region "Controller内で利用する関数群"

        ///// <summary>
        ///// ヘッダー情報の整合性チェックを行います。
        /////  TODO: エンティティに対する整合性チェック (マスタ存在チェックなど) を行います。
        ///// </summary>
        ///// <param name="changeSet">ヘッダーの変更セット</param>
        ///// <returns></returns>
        //private InvalidationSet<object /*TODO:headerの型を指定します*/> ValidateHeader(ChangeSet<object /*TODO:headerの型を指定します*/> changeSet)
        //{
        //    InvalidationSet<object /*TODO:headerの型を指定します*/> invalidations = new InvalidationSet<object /*TODO:headerの型を指定します*/>();

        //    // TODO: ヘッダーのサーバー入力検証

        //    foreach (var item in changeSet.Created)
        //    {
        //    }


        //    foreach (var item in changeSet.Updated)
        //    {
        //    }

        //    return invalidations;
        //}

        ///// <summary>
        ///// 明細一覧情報の整合性チェックを行います。
        /////  TODO: エンティティに対する整合性チェック (マスタ存在チェックなど) を行います。
        ///// </summary>
        ///// <param name="changeSet">明細一覧の変更セット</param>
        ///// <returns></returns>
        //private InvalidationSet<object /*TODO:detailの型を指定します*/> ValidateDetail(ChangeSet<object /*TODO:detailの型を指定します*/> changeSet)
        //{

        //    InvalidationSet<object /*TODO:detailの型を指定します*/> invalidations = new InvalidationSet<object /*TODO:detailの型を指定します*/>();

        //    // TODO: 明細のサーバー入力検証
        //    foreach (var item in changeSet.Created)
        //    {
        //    }


        //    foreach (var item in changeSet.Updated)
        //    {
        //    }

        //    return invalidations;
        //}

        ///// <summary>
        ///// hedaer情報の一括更新（追加・更新・削除）を実行します
        ///// </summary>
        ///// <param name="context">DbContext</param>
        ///// <param name="header">hedaer情報</param>
        ///// <returns>headerの新規キー項目</returns>
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

        ///// <summary>
        ///// detail情報の一括更新（追加・更新・削除）を実行します
        ///// </summary>
        ///// <param name="context">DbContext</param>
        ///// <param name="detail">detail情報</param>
        ///// <param name="newId">headerのキー項目</param>
        //private void SaveDetail(System.Data.Entity.DbContext/*TODO:detail情報を管理しているDbContextを指定します*/ context, ChangeSet<object/*TODO:detailの型を指定します*/> detail, int newId = -1)
        //{
        //    if (newId > 0)
        //    {
        //        // TODO: header情報で採番されたキー項目をdetail情報に設定します。
        //        foreach (var item in detail.Created)
        //        {
        //            //item.no_seq = newId;
        //        }
        //    }

        //    // TODO: 登録日、ユーザー、更新日、ユーザーをセットします。
        //    detail.SetDataSaveInfo(this.User.Identity);
        //    detail.AttachTo(context);
        //    context.SaveChanges();
        //}
        //#endregion
    }

    //#region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    //public class ChangeRequest
    //{
    //    public ChangeSet<object/*TODO:headerの型を指定します*/> Header { get; set; }

    //    public ChangeSet<object/*TODO:detailの型を指定します*/> Detail { get; set; }
    //}

    //public class ChangeResponse
    //{
    //    public ChangeResponse()
    //    {
    //        this.Header = new object/*TODO:headerの型を指定します*/();
    //        this.Detail = new List<object/*TODO:detailの型を指定します*/>();
    //    }

    //    public object/*TODO:headerの型を指定します*/ Header { get; set; }
    //    public List<object/*TODO:detailの型を指定します*/> Detail { get; set; }
    //}


    //public class HeaderChangeRequest
    //{
    //    public ChangeSet<object/*TODO:headerの型を指定します*/> Header { get; set; }
    //}

    //public class HeaderChangeResponse
    //{
    //    public HeaderChangeResponse()
    //    {
    //        this.Header = new object/*TODO:headerの型を指定します*/();
    //    }

    //    public object/*TODO:headerの型を指定します*/ Header { get; set; }
    //}

    //#endregion

    public class paramSearchDialog
    {
        public int busho_hyoji { get; set; }
        public short cd_kengen { get; set; }
        public string cd_genryo { get; set; }
        public string nm_genryo { get; set; }
        public int cd_kaisha { get; set; }
        public int cd_kaisha_change_name { get; set; }
        public int? cd_busho { get; set; }
        public int cd_busho_change_name { get; set; }
        public string name_change { get; set; }
        public bool sankagatsu { get; set; }
        public bool subeteko { get; set; }
        public bool flg_hyoji_table { get; set; }
        public string tanka_hyoujigaisha { get; set; }
        public int hyoji1 { get; set; }
        public int hyoji9 { get; set; }
        public int top { get; set; }
    }

    public class ResultsMasterData
    {
        public ResultsMasterData()
        {
            this.kaisha = new List<ma_kaisha>();
            this.busho = new List<ma_busho>();
        }

        public List<ma_kaisha> kaisha { get; set; }
        public List<ma_busho> busho { get; set; }
    }
}
