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
using System.Threading;
using Tos.Web.Properties;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class BunsekichiNyuryokuController : ApiController
    {

        #region "Static variables"
        private static List<processID> priorityInsertQueue = new List<processID>();
        private static long p_ID = 0;
        #endregion

        private static readonly double localOffset = TimeZoneInfo.Local.GetUtcOffset(DateTime.UtcNow).TotalHours;
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された OData クエリに該当するすべてのtarget情報を取得します。
        /// </summary>
        /// <param name="options"> OData クエリ</param>
        /// <returns>target情報</returns>
        public PageResult<object/*TODO:targetの型を指定します*/> Get(ODataQueryOptions<object/*TODO:targetの型を指定します*/> options)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            // /*TODO:target情報を管理しているDbContextを指定します*/ context = new /*TODO:target情報を管理しているDbContextを指定します*/();
            // context.Configuration.ProxyCreationEnabled = false;
            // IQueryable results = options.ApplyTo(context./*TODO:targetの型を指定します*/.AsQueryable());
            // return new PageResult</*TODO:targetの型を指定します*/>(results as IEnumerable</*TODO:targetの型を指定します*/>, Request.GetNextPageLink(), Request.GetInlineCount());

            // TODO: 上記実装を行った後に下の行は削除します
            return null;
        }

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_005_Result> Get(int cd_kaisha,int? cd_busho ,string cd_genryo)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();

            //set timeout
            ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = Settings.Default.TimeOutQuery;

            context.Configuration.ProxyCreationEnabled = false;

            // TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
            //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
            StoredProcedureResult<sp_shohinkaihatsu_search_005_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_005_Result>();

            // 引数で渡された工場コードで原料工場マスタにデータがあればその工場のデータを取得する、なければ工場コードが一番若いデータを取得する
            var existData = context.sp_shohinkaihatsu_existData_005(cd_kaisha, cd_genryo, cd_busho).FirstOrDefault();
            if (existData == null || int.Parse(existData.ToString()) == 0)
            {
                cd_busho = null;
            }        
            result.Items = context.sp_shohinkaihatsu_search_005(cd_kaisha,cd_busho,cd_genryo).ToList();
            if (result.Items.Count() > 0)
            {
                result.Count = result.Items.Count();
            }
            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;

        }

        ///// <summary>
        ///// パラメータで受け渡された製品コードを元に版数の情報を取得します。
        ///// </summary>
        ///// <param name="cd_seihin">キー項目</param>
        ///// <returns>ChangeResponse</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_dropdown_kaisha_Result> Get_kaisha(decimal id_user)
        {
            // TODO:header情報を管理しているDbContextとheaderの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;


            // TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。
            //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
            StoredProcedureResult<sp_shohinkaihatsu_dropdown_kaisha_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_dropdown_kaisha_Result>();
            result.Items = context.sp_shohinkaihatsu_dropdown_kaisha(id_user).ToList();

            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;

        }


        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
        public object Get(int id)
        {
            // TODO: target 情報を管理しているDbContextと target の型を指定します。

            // using (/*TODO: target 情報を管理しているDbContextを指定します*/ context = new /*TODO: target 情報を管理しているDbContextを指定します*/())
            // {
            //     context.Configuration.ProxyCreationEnabled = false;

            //     return (from m in context./*TODO: target の型を指定します*/
            //             where m./*TODO:headerのキー項目を指定します*/ == id
            //             select m).SingleOrDefault();
            // }

            // TODO: 上記実装を行った後に下の行は削除します
            return null;
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]changeSetBunsekichiNyuryoku value)
        {
            changeSetBunsekichiNyuryoku result = new changeSetBunsekichiNyuryoku();
            // Single runing
            var passID = getProcessPass();
            try
            {

            sp_shohinkaihatsu_search_005_Result itemUpdate;
            sp_shohinkaihatsu_insert_005_Result genryoCD;
            decimal userID = Convert.ToDecimal(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            bool createData = true;
            bool updateGenryoKojo = value.updateNmGenryo;
            int cd_kaisha = value.ma_genryo.Created[0].cd_kaisha;
            string cd_kakutei = value.ma_genryo.Created[0].cd_kakutei;
            //DateTime dt_kakunin = (DateTime)value.ma_genryo.Created[0].dt_kakunin;
            //if (value.dt_kakunin != null)
            //{
            //    if (value.dt_kakunin.Hour != 0)
            //    {
            //        value.dt_kakunin = value.dt_kakunin.AddHours(localOffset);
            //    }
            //}
            
            // TODO: 保存処理を実行します。            
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                //会社コード、確定コードの組み合わせが原料マスタに存在するか確認
                if (cd_kakutei != "" && cd_kakutei != null)
                {
                    if (!existsKakutei(context, value.ma_genryo))
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.BadRequest, string.Format(Properties.Resources.AP0102, cd_kaisha, "確定コード", cd_kakutei));
                    }
                }
                IObjectContextAdapter adapter = context as IObjectContextAdapter;

                //set timeout
                ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = Settings.Default.TimeOutQuery;

                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    try
                    {
                        // TODO: 保存処理を実行します。
                        sp_shohinkaihatsu_search_005_Result item = new sp_shohinkaihatsu_search_005_Result();

                        //原料マスタの情報
                        item.cd_kaisha = value.ma_genryo.Created[0].cd_kaisha;
                        item.cd_genryo = null;
                        item.ritu_sakusan = value.ma_genryo.Created[0].ritu_sakusan;
                        item.ritu_shokuen = value.ma_genryo.Created[0].ritu_shokuen;
                        item.ritu_sousan = value.ma_genryo.Created[0].ritu_sousan;
                        item.ritu_abura = value.ma_genryo.Created[0].ritu_abura;
                        item.hyojian = value.ma_genryo.Created[0].hyojian;
                        item.tenkabutu = value.ma_genryo.Created[0].tenkabutu;
                        item.memo = value.ma_genryo.Created[0].memo;
                        item.kbn_haishi = value.flg_haishi;
                        item.cd_kakutei = value.ma_genryo.Created[0].cd_kakutei;
                        item.ritu_msg = value.ma_genryo.Created[0].ritu_msg;
                        //原料工場マスタの情報
                        item.nm_genryo = value.nm_genryo;
                        item.cd_busho = value.cd_busho;
                  
                        genryoCD = context.sp_shohinkaihatsu_insert_005(
                                                                        createData
                                                                        , updateGenryoKojo
                                                                        , item.cd_kaisha
                                                                        , item.cd_genryo
                                                                        , item.ritu_sakusan
                                                                        , item.ritu_shokuen
                                                                        , item.ritu_sousan
                                                                        , item.ritu_abura
                                                                        , item.hyojian
                                                                        , item.tenkabutu
                                                                        , item.memo
                                                                        , item.kbn_haishi
                                                                        , item.cd_kakutei
                                                                        , value.id_kakunin
                                                                        , value.dt_kakunin
                                                                        , userID
                                                                        , item.ritu_msg
                                                                        , value.chageFlg
                                                                        , item.cd_busho
                                                                        , item.nm_genryo
                                                                        , item.ts
                                                                        ).FirstOrDefault();
                        context.SaveChanges();
                        transaction.Commit();
                        itemUpdate = context.sp_shohinkaihatsu_search_005(item.cd_kaisha, item.cd_busho, genryoCD.cd_genryo).FirstOrDefault();
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
           return Request.CreateResponse<sp_shohinkaihatsu_search_005_Result>(HttpStatusCode.OK, itemUpdate);

            }
            finally
            {
                // Remove the pass when completing current request
                priorityInsertQueue.Remove(passID);
            }
        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Put([FromBody]changeSetBunsekichiNyuryoku value)
        {
            
            // Single runing
            var passID = getProcessPass();
            try
            {
          
            sp_shohinkaihatsu_search_005_Result itemUpdate;
            sp_shohinkaihatsu_insert_005_Result genryoCD;           
            decimal userID = Convert.ToDecimal(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            bool createData = false;
            bool updateGenryoKojo = value.updateNmGenryo;
            int cd_kaisha = value.ma_genryo.Updated[0].cd_kaisha;
            string cd_genryo = value.ma_genryo.Updated[0].cd_genryo;
            string cd_kakutei = value.ma_genryo.Updated[0].cd_kakutei;
            
            //InvalidationSet<ma_genryo> genryoInvalidations = genryoIsAlreadyExists(value.ma_genryo);

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                //会社コード、原料コードの組み合わせが原料マスタに存在するか確認
                if (!existsGenryo(context, value.ma_genryo))
                {
                    return Request.CreateErrorResponse(HttpStatusCode.BadRequest, string.Format(Properties.Resources.AP0102, cd_kaisha, "原料コード", cd_genryo));
                }

                //会社コード、確認コードの組み合わせが原料マスタに存在するか確認
                if ( cd_kakutei != "" && cd_kakutei != null)
                {
                    if (!existsKakutei(context, value.ma_genryo))
                    {
                        return Request.CreateErrorResponse(HttpStatusCode.BadRequest, string.Format(Properties.Resources.AP0102, cd_kaisha,"確定コード",cd_kakutei));
                    }
                }

                IObjectContextAdapter adapter = context as IObjectContextAdapter;

                //set timeout
                ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = Settings.Default.TimeOutQuery;

                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {                   
                    try
                    {
                        // TODO: 保存処理を実行します。
                        sp_shohinkaihatsu_search_005_Result item = new sp_shohinkaihatsu_search_005_Result();
                        //genryo = context.ma_genryo.Where(x => x.cd_kaisha == cd_kaisha && x.cd_genryo == cd_genryo).ToList();
               
                        //原料マスタの情報
                        item.cd_kaisha = value.ma_genryo.Updated[0].cd_kaisha;
                        item.cd_genryo = value.ma_genryo.Updated[0].cd_genryo;
                        item.ritu_sakusan = value.ma_genryo.Updated[0].ritu_sakusan;
                        item.ritu_shokuen = value.ma_genryo.Updated[0].ritu_shokuen;
                        item.ritu_sousan = value.ma_genryo.Updated[0].ritu_sousan;
                        item.ritu_sakusan = value.ma_genryo.Updated[0].ritu_sakusan;
                        item.ritu_abura = value.ma_genryo.Updated[0].ritu_abura;
                        item.hyojian = value.ma_genryo.Updated[0].hyojian;
                        item.tenkabutu = value.ma_genryo.Updated[0].tenkabutu;
                        item.memo = value.ma_genryo.Updated[0].memo;
                        item.kbn_haishi = value.flg_haishi;
                        item.cd_kakutei = value.ma_genryo.Updated[0].cd_kakutei;
                        item.ritu_msg = value.ma_genryo.Updated[0].ritu_msg;
                        item.ts = value.ma_genryo.Updated[0].ts;
                        //原料工場マスタの情報
                        item.nm_genryo = value.nm_genryo;
                        item.cd_busho = value.cd_busho;


                        genryoCD = context.sp_shohinkaihatsu_insert_005(
                                                                        createData
                                                                        , updateGenryoKojo
                                                                        , item.cd_kaisha
                                                                        , item.cd_genryo                           
                                                                        , item.ritu_sakusan
                                                                        , item.ritu_shokuen
                                                                        , item.ritu_sousan
                                                                        , item.ritu_abura
                                                                        , item.hyojian
                                                                        , item.tenkabutu
                                                                        , item.memo
                                                                        , item.kbn_haishi
                                                                        , item.cd_kakutei
                                                                        , value.id_kakunin
                                                                        , value.dt_kakunin
                                                                        , userID
                                                                        , item.ritu_msg
                                                                        , value.chageFlg
                                                                        , item.cd_busho
                                                                        , item.nm_genryo
                                                                        , item.ts
                                                                       ).FirstOrDefault();
                        context.SaveChanges();
                        transaction.Commit();
                        itemUpdate = context.sp_shohinkaihatsu_search_005(item.cd_kaisha, item.cd_busho, genryoCD.cd_genryo).FirstOrDefault();
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
            return Request.CreateResponse<sp_shohinkaihatsu_search_005_Result>(HttpStatusCode.OK, itemUpdate);

            }
            finally
            {
                // Remove the pass when completing current request
                priorityInsertQueue.Remove(passID);
            }
        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を削除します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Delete([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {

            var result = SaveData(value);
            return Request.CreateResponse<SearchInputChangeResponse>(HttpStatusCode.OK, result);

        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。(原料マスタ)
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<ma_genryo> genryoIsAlreadyExists(ChangeSet<ma_genryo> value)
        {
            InvalidationSet<ma_genryo> result = new InvalidationSet<ma_genryo>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;
            
                    var createdCount = value.Created.Count(target => target.cd_kaisha == item.cd_kaisha && target.cd_genryo == item.cd_genryo);
                    var isDeleted = value.Deleted.Exists(target => target.cd_kaisha == item.cd_kaisha && target.cd_genryo == item.cd_genryo);
                    var isDatabaseExists = (context.ma_genryo.Find(item.cd_kaisha , item.cd_genryo) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<ma_genryo>(Properties.Resources.ValidationKey, item, "ma_genryo"));
                    }
                }
            }

            return result;
        }
        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。(原料工場マスタ)
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<ma_genryokojo> genryokojoIsAlreadyExists(ChangeSet<ma_genryokojo> value)
        {
            InvalidationSet<ma_genryokojo> result = new InvalidationSet<ma_genryokojo>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.cd_kaisha == item.cd_kaisha && target.cd_genryo == item.cd_genryo && target.cd_busho == item.cd_busho );
                    var isDeleted = value.Deleted.Exists(target => target.cd_kaisha == item.cd_kaisha && target.cd_genryo == item.cd_genryo && target.cd_busho == item.cd_busho );
                    var isDatabaseExists = (context.ma_genryo.Find(item.cd_kaisha, item.cd_genryo, item.cd_busho) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<ma_genryokojo>(Properties.Resources.ValidationKey, item, "ma_genryokojo"));
                    }
                }
            }

            return result;
        }

        /// <summary>
        /// 会社コード・品名コードがマスタ存在するか
        /// </summary>
        /// <param name="header"></param>
        /// <param name="status"></param>
        /// <returns></returns>
        private bool existsGenryo(ShohinKaihatsuEntities context ,ChangeSet<ma_genryo> ma_genryo)
        {
            bool exists = false;
            int cd_kaisha = 0;
            string cd_genryo = null;


            if (ma_genryo.Updated.Count > 0)
            {
                cd_kaisha = ma_genryo.Updated[0].cd_kaisha;
                cd_genryo = ma_genryo.Updated[0].cd_genryo;
            }

            if (cd_kaisha == 0 || cd_genryo == null)
            {
                return false;
            }

            var genryoExist = context.ma_genryo.Find(cd_kaisha , cd_genryo);

            if (genryoExist == null)
            {         
                   //count = ZERO => cd_tekiyo does not exists
                exists = false;
            }
            else
            {
                exists = true;
            }
            return exists;
        }

        // <summary>
        /// 会社コード・確定コードがマスタ存在するか
        /// </summary>
        /// <param name="header"></param>
        /// <param name="status"></param>
        /// <returns></returns>
        private bool existsKakutei(ShohinKaihatsuEntities context, ChangeSet<ma_genryo> ma_genryo)
        {
            bool exists = false;
            int cd_kaisha = 0;
            string cd_kakutei = null;


            if (ma_genryo.Updated.Count > 0)
            {
                cd_kaisha = ma_genryo.Updated[0].cd_kaisha;
                cd_kakutei = ma_genryo.Updated[0].cd_kakutei;
            }

            if (ma_genryo.Created.Count > 0)
            {
                cd_kaisha = ma_genryo.Created[0].cd_kaisha;
                cd_kakutei = ma_genryo.Created[0].cd_kakutei;
            }

            if (cd_kaisha == 0 || cd_kakutei == null)
            {
                return false;
            }

            var kakuteiExist = context.ma_genryo.Find(cd_kaisha, cd_kakutei);

            if (kakuteiExist == null)
            {
                //count = ZERO => cd_tekiyo does not exists
                exists = false;
            }
            else
            {
                exists = true;
            }
            return exists;
        }


        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        private void SaveGenryoData(ShohinKaihatsuEntities context, ChangeSet<ma_genryo> value)
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
        private void SaveGenryoKojoData(ShohinKaihatsuEntities context, ChangeSet<ma_genryokojo> value)
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
        private SearchInputChangeResponse SaveData(ChangeSet<object/*TODO:targetの型を指定します*/> value)
        {
            //★★★id_koshin、id_toroku用に変わることを覚えておく
            //using (/*TODO:target情報を管理しているDbContextを指定します*/ context = new /*TODO:target情報を管理しているDbContextを指定します*/())
            //{

            //    value.SetDataSaveInfo(this.User.Identity);                
            //    value.AttachTo(context);
            //    context.SaveChanges();
            //}

            // TODO: 返却用のオブジェクトを生成します。
            //var result = new SearchInputChangeResponse();
            //result.Detail.AddRange(value.Flatten());
            //return result;

            // TODO: 上記実装を行った後に下の行は削除します
            return null;
        }

        /// <summary>
        /// Fn support prevent conflict when insert new data
        /// Current insert request will wait until previous request is completed
        /// </summary>
        /// <returns></returns>
        private processID getProcessPass()
        {
            // Create new pass
            processID result = new processID()
            {
                ID = p_ID++,
                waitTime = 0
            };
            // Add pass to priority queue
            priorityInsertQueue.Add(result);
            // Check if current process is the first of priority
            while (priorityInsertQueue[0].ID != result.ID)
            {
                result.waitTime += processID.singleWaitingTime;
                if (result.waitTime > processID.maxWaiting)
                {
                    throw (new Exception("Request timeout."));
                }
                Thread.Sleep(processID.singleWaitingTime);
            }
            return result;
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class SearchInputChangeResponse
    {
        public SearchInputChangeResponse()
        {
            this.Detail = new List<object/*TODO: target の型を指定します*/>();
        }

        public List<object/*TODO: target の型を指定します*/> Detail { get; set; }
    }

    public class changeSetBunsekichiNyuryoku
    {
        public ChangeSet<ma_genryo> ma_genryo { get; set; }
        public int cd_busho { get; set; }
        public string nm_genryo { get; set; }
        public short flg_haishi { get; set; }
        public decimal? id_kakunin { get; set; }
        public DateTime? dt_kakunin { get; set; }
        public bool updateNmGenryo { get; set; }
        public bool chageFlg { get; set; }
    }

    #endregion
}
