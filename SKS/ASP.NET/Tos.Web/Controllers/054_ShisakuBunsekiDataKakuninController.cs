using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.Common;
using System.Data.Entity.Infrastructure;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Web;
using System.Web.Http;
using System.Web.Http.OData;
using System.Web.Http.OData.Query;
using Tos.Web.Controllers.Helpers;
using Tos.Web.Data;
using Tos.Web.Logging;
using Tos.Web.Properties;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _054_ShisakuBunsekiDataKakuninController : ApiController
    {
        #region "CSVファイルの項目設定"

        private static readonly TextFieldSetting[] CsvFileSettings = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "cd_genryo", DisplayName = "原料CD"},
            new TextFieldSetting() { PropertyName = "nm_genryo", DisplayName = "原料名", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "nm_busho", DisplayName = "工場", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "shiyo", DisplayName = "三ヶ月"},
            new TextFieldSetting() { PropertyName = "mishiyo", DisplayName = "未使用"},
            new TextFieldSetting() { PropertyName = "wt_kaniku", DisplayName = "果肉量"},
            new TextFieldSetting() { PropertyName = "wt_sato", DisplayName = "砂糖量"},
            new TextFieldSetting() { PropertyName = "wt_kayosei_kokeibutsu", DisplayName = "可溶性固形物"},
            new TextFieldSetting() { PropertyName = "wt_kanmido", DisplayName = "甘味度"},
            new TextFieldSetting() { PropertyName = "wt_hiju", DisplayName = "比重"},
            new TextFieldSetting() { PropertyName = "wt_ireme", DisplayName = "入れ目量"},
            new TextFieldSetting() { PropertyName = "wt_1_kan", DisplayName = "1缶重量"},
            new TextFieldSetting() { PropertyName = "kbn_sato", DisplayName = "砂糖甘味料"},
            new TextFieldSetting() { PropertyName = "kbn_gum", DisplayName = "ペクチンガム類"},
            new TextFieldSetting() { PropertyName = "tani", DisplayName = "単位"},
            new TextFieldSetting() { PropertyName = "tanka", DisplayName = "単価"},
            new TextFieldSetting() { PropertyName = "hyojian", DisplayName = "表示案", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "RMLABELSAMPLE", DisplayName = "表示案G-Mer", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "tenkabutu", DisplayName = "添加物", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "AMS_YO", DisplayName = "添加物G-Mer（表示要）", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "AMS_FUYO", DisplayName = "添加物G-Mer（表示不要）", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "ALG_ALR", DisplayName = "アレルギー情報G-Mer（表示要）", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "ALG_FUYO", DisplayName = "アレルギー情報G-Mer（表示不要）", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "memo", DisplayName = "メモ", WrapChar = "\""},
            new TextFieldSetting() { PropertyName = "dt_konyu", DisplayName = "最終使用日", Format = "yyyy/MM/dd"},
            new TextFieldSetting() { PropertyName = "cd_kakutei", DisplayName = "確定コード"},
            new TextFieldSetting() { PropertyName = "haishi", DisplayName = "廃止区"}
        };

        #endregion

        #region "Controllerで公開するAPI"
        /// <summary>
        /// 検索条件に一致するデータを抽出しCSVを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>作成されたCSVファイルのパス</returns>
        public HttpResponseMessage Get_csv([FromUri]Param_Shisaku_Search_054 value)
        {

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
                string userName = UserInfo.GetUserNameFromIdentity(this.User.Identity);
                string filename = userName + "_試作分析データ確認（アヲハタ）_"/*Properties.Resources.//TODO:DownloadFileNameの定数を指定します*/;
                string csvname = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;

                var result = context.sp_shohinkaihatsu_search_054(value.cd_kaisha
                                                       , value.cd_busho
                                                       , value.cd_genryo
                                                       , value.nm_genryo
                                                       , value.checkGenryo
                                                       , value.checkBGenryo
                                                       , value.flg_shiyo
                                                       , value.id_session
                                                       , value.tani_OAH
                                                       , value.skip
                                                       , value.top
                                                       ).ToList();

                foreach (var i in result)
                {
                    i.nm_genryo = i.nm_genryo != null ? i.nm_genryo.Replace("\n", "").Replace("\r", "").Replace("\r\n", "") : null;
                    i.nm_busho = i.nm_busho != null ? i.nm_busho.Replace("\n", "").Replace("\r", "").Replace("\r\n", "") : null;
                    i.hyojian = i.hyojian != null ? i.hyojian.Replace("\n", "").Replace("\r", "").Replace("\r\n", "") : null;
                    i.RMLABELSAMPLE = i.RMLABELSAMPLE != null ? i.RMLABELSAMPLE.Replace("\n", "").Replace("\r", "").Replace("\r\n", "") : null;
                    i.tenkabutu = i.tenkabutu != null ? i.tenkabutu.Replace("\n", "").Replace("\r", "").Replace("\r\n", "") : null;
                    i.AMS_YO = i.AMS_YO != null ? i.AMS_YO.Replace("\n", "").Replace("\r", "").Replace("\r\n", "") : null;
                    i.AMS_FUYO = i.AMS_FUYO != null ? i.AMS_FUYO.Replace("\n", "").Replace("\r", "").Replace("\r\n", "") : null;
                    i.ALG_ALR = i.ALG_ALR != null ? i.ALG_ALR.Replace("\n", "").Replace("\r", "").Replace("\r\n", "") : null;
                    i.ALG_FUYO = i.ALG_FUYO != null ? i.ALG_FUYO.Replace("\n", "").Replace("\r", "").Replace("\r\n", "") : null;
                    i.memo = i.memo != null ? i.memo.Replace("\n", "").Replace("\r", "").Replace("\r\n", "") : null;
                }


                MemoryStream stream = new MemoryStream();
                TextFieldFile<sp_shohinkaihatsu_search_054_Result> tFile
                                    = new TextFieldFile<sp_shohinkaihatsu_search_054_Result>(stream,
                                                                          Encoding.GetEncoding(Properties.Resources.Encoding), CsvFileSettings);
                tFile.Delimiters = new string[] { "," };
                // TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
                tFile.IsFirstRowHeader = true;
                tFile.WriteFields(result as IEnumerable<sp_shohinkaihatsu_search_054_Result>);

                return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
            }
        }

        /// <summary>
        /// エラー情報CSVを作成します
        /// </summary>
        /// <returns></returns>
        //private HttpResponseMessage createErrorCsv(TextFieldFile<sp_shohinkaihatsu_search_054_Result> tFile)
        //{
        //    // 保存Csvファイル名（ユーザID_downloadError_現在日時.csv）
        //    //string name = UserInfo.GetUserNameFromIdentity(this.User.Identity);
        //    string filename = "UploadError_試作分析データ確認_";
        //    //string filename = name + "UploadError_"/*Properties.Resources.//TODO:UploadErrorFileNameの定数を指定します*/;
        //    string csvName = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;
        //    MemoryStream stream = tFile.GetErrorStream();
        //    stream.Position = 0;
        //    return FileUploadDownloadUtility.CreateFileResponse(HttpStatusCode.BadRequest, stream, csvName);
        //}



        /// <summary>
        /// パラメータで受け渡された OData クエリに該当するすべてのtarget情報を取得します。
        /// </summary>
        /// <param name="options"> OData クエリ</param>
        /// <returns>target情報</returns>
        public object Get_busho(int cd_kaisha, short? fg_hyoji)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                List<ma_busho> result = new List<ma_busho>();
                result = context.ma_busho.Where(m => m.cd_kaisha == cd_kaisha && m.fg_hyoji == fg_hyoji).OrderBy(x => x.cd_busho).ToList();

                return result;
            }
        }

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_054_Result> Get([FromUri]Param_Shisaku_Search_054 value)
        {
            //TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();

            //set timeout
            ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = Settings.Default.TimeOutQuery;

            context.Configuration.ProxyCreationEnabled = false;

            //TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
            //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
            StoredProcedureResult<sp_shohinkaihatsu_search_054_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_054_Result>();

            result.Items = context.sp_shohinkaihatsu_search_054(value.cd_kaisha
                                                        , value.cd_busho
                                                        , value.cd_genryo
                                                        , value.nm_genryo
                                                        , value.checkGenryo
                                                        , value.checkBGenryo
                                                        , value.flg_shiyo
                                                        , value.id_session
                                                        , value.tani_OAH
                                                        , value.skip
                                                        , value.top
                                                        ).ToList();

            result.Count = result.Items.Count();
            // TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;

            // //TODO: 上記実装を行った後に下の行は削除します
            //return null;
        }

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
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
        ///// <returns></returns>
        //public HttpResponseMessage Post([FromBody]ChangeSet<changeSetShisakuBunseki> value)
        //{
        //    if (value == null)
        //    {
        //        return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
        //    }

        //    //// TODO: キー項目の重複チェックを行います。
        //    //InvalidationSet<changeSetShisakuBunseki> headerInvalidations = IsAlreadyExists(value);
        //    //if (headerInvalidations.Count > 0)
        //    //{
        //    //    return Request.CreateResponse<InvalidationSet<changeSetShisakuBunseki>>(HttpStatusCode.BadRequest, headerInvalidations);
        //    //}

        //    using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
        //    {
        //        var result = context.sp_shohinkaihatsu_delete_004(value.cd_kaisha
        //                                                   , value.cd_busho
        //                                                   , value.cd_genryo).ToList();
        //    }

        //    // TODO: 保存処理を実行します。
        //    var result = SaveData(value);
        //    return Request.CreateResponse<changeSetShisakuBunseki>(HttpStatusCode.OK, result);

        //}

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        //public HttpResponseMessage Put([FromBody]ChangeSet<ma_genryokojo> value)
        //{

        //    var result = SaveData(value);
        //    return Request.CreateResponse<ma_genryokojo>(HttpStatusCode.OK, result);

        //}

        ///// <summary>
        ///// パラメーターで受け渡されたtarget情報を削除します
        ///// </summary>
        ///// <param name="value"></param>
        ///// <returns></returns>
        public HttpResponseMessage Delete([FromBody]ChangeSet<ma_genryokojo> value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                try
                {
                    List<ma_genryokojo> deleteData = value.Deleted;
                    ChangeSet<ma_genryo> genryo = new ChangeSet<ma_genryo>();
                    ChangeSet<ma_genryo_bf> genryoBf = new ChangeSet<ma_genryo_bf>();
                    ChangeSet<ma_genryokojo_bf> genryoKojoBf = new ChangeSet<ma_genryokojo_bf>();

                    foreach (var i in deleteData)
                    {
                        List<ma_genryo> genryoData = context.ma_genryo.Where(c => c.cd_genryo == i.cd_genryo && c.cd_kaisha == i.cd_kaisha).ToList();
                        genryo.Deleted.AddRange(genryoData);

                        List<ma_genryo_bf> genryoBfData = context.ma_genryo_bf.Where(c => c.cd_genryo == i.cd_genryo && c.cd_kaisha == i.cd_kaisha).ToList();
                        genryoBf.Deleted.AddRange(genryoBfData);

                        List<ma_genryokojo_bf> genryokojoBfData = context.ma_genryokojo_bf.Where(c => c.cd_genryo == i.cd_genryo && c.cd_kaisha == i.cd_kaisha && c.cd_busho == i.cd_busho).ToList();
                        genryoKojoBf.Deleted.AddRange(genryokojoBfData);
                    }

                    genryo.AttachTo(context);
                    value.AttachTo(context);
                    value.SetSeihoDataSaveInfo(this.User.Identity);
                    genryoBf.AttachTo(context);
                    genryoKojoBf.AttachTo(context);

                    context.SaveChanges();

                }
                catch (DbUpdateConcurrencyException ex)
                {
                    // 例外をエラーログに出力します。
                    Logger.App.Error(ex.Message, ex);
                    return Request.CreateResponse(HttpStatusCode.Conflict, ex);
                }
                catch (Exception ex)
                {
                    // 例外をエラーログに出力します。
                    Logger.App.Error(ex.Message, ex);
                    return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                }
                
                string success = "Success";
                return Request.CreateResponse<string>(HttpStatusCode.OK, success);
            }
        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        //private InvalidationSet<ma_genryokojo> IsAlreadyExists(ChangeSet<ma_genryokojo> value)
        //{
        //    InvalidationSet<ma_genryokojo> result = new InvalidationSet<ma_genryokojo>();

        //    //using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
        //    //{
        //    //    foreach (var item in value.Created)
        //    //    {
        //    //        // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
        //    //        //bool isDepulicate = false;
            
        //    //        //var createdCount = value.Created.Count(target => target.cd_kaisha == item.cd_kaisha);
        //    //        var isDeleted = value.Deleted.Exists(target => target.cd_kaisha == item.cd_kaisha && target.cd_genryo == item.cd_genryo && target.cd_busho == item.cd_busho);
        //    //        //var isDatabaseExists = (context.ma_genryokojo.Find(item.cd_kaisha) != null);

        //    //        //isDepulicate |= (createdCount > 1);
        //    //        //isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

        //    //        //if (isDepulicate)
        //    //        //{
        //    //        //    result.Add(new Invalidation<ma_genryokojo>(Properties.Resources.ValidationKey, item, "cd_kaisha, cd_genryo, cd_busho"));
        //    //        //}
        //    //    }
        //    //}

        //    return result;
        //}

        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        //private ma_genryokojo SaveData(ChangeSet<ma_genryokojo> value, ChangeSet<ma_genryo> x)
        //{
        //    using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
        //    {

        //        value.SetDataSaveInfo(this.User.Identity);
        //        value.AttachTo(context);
        //        x.SetDataSaveInfo(this.User.Identity);
        //        x.AttachTo(context);

        //        context.SaveChanges();
        //    }

        //    //TODO: 返却用のオブジェクトを生成します。
        //    var result = new ma_genryokojo();
        //    //result.Detail.AddRange(value.Flatten());
        //    return result;

        //    // TODO: 上記実装を行った後に下の行は削除します
        //    //return null;
        //}

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"


    public class Param_Shisaku_Search_054
    {
        public int cd_kaisha { get; set; }
        public string cd_busho { get; set; }
        public string cd_genryo { get; set; }
        public string nm_genryo { get; set; }
        public bool? checkBGenryo { get; set; }
        public bool? checkGenryo { get; set; }
        public short flg_shiyo { get; set; }
        public string tani_OAH { get; set; }
        public int id_session { get; set; }
        public int skip { get; set; }
        public int top { get; set; }
    }

    #endregion
}
