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
using System.Data;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class KengenMasterController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された OData クエリに該当するすべてのtarget情報を取得します。
        /// </summary>
        /// <param name="options"> OData クエリ</param>
        /// <returns>target情報</returns>
        public DataKengenKinoMaster Get(short? cd_kengen)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                DataKengenKinoMaster result = new DataKengenKinoMaster();
                result.header = context.ma_kengen.Where(m => m.cd_kengen == cd_kengen).ToList();
                result.detail = context.ma_kinou.Where(m => m.cd_kengen == cd_kengen).ToList();

                return result;
            }
        }

        /// <summary>
        /// パラメータで受け渡された target のキー項目をもとに target情報を取得します。
        /// </summary>
        /// <param name="id"> target のキー項目</param>
        /// <returns> target 情報</returns>
        public object GetGamenKinoData(string kbn_type, long? id_gamen, long? id_kino, long? id_data)
        {
            // TODO: target 情報を管理しているDbContextと target の型を指定します。

             using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
             {
                 context.Configuration.ProxyCreationEnabled = false;

                 var result = context.sp_shohinkaihatsu_gamen_kino_data_009(kbn_type, id_gamen, id_kino, id_data).ToList();

                 return result;
             }
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]ChangeSetkengenMaster value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            var result = new KengenMasterChangeResponse();
            decimal user_id = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            DateTime dateNow = DateTime.Now;
            short? kengenCode = 0;

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {

                    // TODO: 保存処理を実行します。
                    if (value.header.Created.Count() > 0) {
                        var item = value.header.Created[0];
                        item.kbn_kengen_bunrui = 0;

                        ma_saiban saiban = context.ma_saiban.Where(m => m.key1 == Properties.Resources.KengenCodeSaiban).FirstOrDefault();

                        if (saiban == null)
                        {
                            ma_saiban add = new ma_saiban();

                            add.key1 = Properties.Resources.KengenCodeSaiban;
                            add.key2 = "";
                            add.no_seq = 1;
                            add.id_toroku = user_id;
                            add.id_koshin = user_id;
                            add.dt_koshin = dateNow;
                            add.dt_toroku = dateNow;

                            context.ma_saiban.Add(add);
                            context.SaveChanges();

                            item.cd_kengen = 1;
                            kengenCode = 1;
                        }
                        else {
                            short no_seq = short.Parse((saiban.no_seq + 1).ToString());
                            item.cd_kengen = no_seq;
                            saiban.no_seq = no_seq;
                            saiban.id_koshin = user_id;
                            saiban.dt_koshin = dateNow;

                            context.ma_saiban.Attach(saiban);
                            context.Entry<ma_saiban>(saiban).State = EntityState.Modified;
                            context.SaveChanges();

                            kengenCode = no_seq;
                        }
                    }

                    if (value.detail.Created.Count() > 0 && value.header.Created.Count() > 0)
                    {
                        foreach (var item in value.detail.Created) {
                            item.cd_kengen = short.Parse(kengenCode.ToString());
                        }
                    }

                    // TODO: キー項目の重複チェックを行います。
                    InvalidationSet<ma_kengen> headerInvalidations = IsAlreadyExistsKengen(value.header);
                    if (headerInvalidations.Count > 0)
                    {
                        return Request.CreateResponse<InvalidationSet<ma_kengen>>(HttpStatusCode.BadRequest, headerInvalidations);
                    }

                    InvalidationSet<ma_kinou> detailInvalidations = IsAlreadyExistsKinou(value.detail);
                    if (detailInvalidations.Count > 0)
                    {
                        return Request.CreateResponse<InvalidationSet<ma_kinou>>(HttpStatusCode.BadRequest, detailInvalidations);
                    }

                    result = SaveHeaderData(context, result, value.header);
                    result = SaveDetailData(context, result, value.detail);
                    if (value.cd_kengen != null || kengenCode != null)
                    {
                        short? id_system_1 = short.Parse(Properties.Resources.id_system_1);
                        short? id_system_2 = short.Parse(Properties.Resources.id_system_2);
                        short? id_system_3 = short.Parse(Properties.Resources.id_system_3);

                        short? id_gamen_200 = short.Parse(Properties.Resources.id_gamen_200);
                        short? id_kino_100 = short.Parse(Properties.Resources.id_kino_100);
                        short? id_kino_102 = short.Parse(Properties.Resources.id_kino_102);
                        short? id_kino_103 = short.Parse(Properties.Resources.id_kino_103);

                        short? kbn_eigyo_1 = short.Parse(Properties.Resources.kbn_eigyo_1);
                        short? kbn_eigyo_2 = short.Parse(Properties.Resources.kbn_eigyo_2);
                        short? kbn_eigyo_3 = short.Parse(Properties.Resources.kbn_eigyo_3);
                        context.sp_shohinkaihatsu_update_kengen_bunrui_009((value.cd_kengen == null ? kengenCode : value.cd_kengen), id_system_1, id_system_2, id_system_3, user_id, dateNow
                                                                            , id_gamen_200, id_kino_100, id_kino_102, id_kino_103
                                                                            , Properties.Settings.Default.kbn_kengen_shisa
                                                                            , Properties.Settings.Default.kbn_kengen_seiho_kaihatsu
                                                                            , Properties.Settings.Default.kbn_kengen_seiho_kojo
                                                                            , Properties.Settings.Default.kbn_kengen_shisa_seiho_kaihatsu
                                                                            , Properties.Settings.Default.kbn_kengen_shisa_seiho_kojo
                                                                            , kbn_eigyo_1, kbn_eigyo_2, kbn_eigyo_3);
                    }

                    transaction.Commit();
                }
            }
            return Request.CreateResponse<KengenMasterChangeResponse>(HttpStatusCode.OK, result);

        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        //public HttpResponseMessage Put([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        //{

        //    var result = SaveData(value);
        //    return Request.CreateResponse<SearchInputChangeResponse>(HttpStatusCode.OK, result);

        //}

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を削除します
        /// </summary>
        /// <param name="value">cd_kengen</param>
        /// <returns></returns>
        public HttpResponseMessage Delete([FromBody]kengenCode value)
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

                    var result = new KengenMasterChangeResponse();

                    context.sp_shohinkaihatsu_delete_009(value.cd_kengen);
                    context.SaveChanges();
                    transaction.Commit();

                    return Request.CreateResponse<KengenMasterChangeResponse>(HttpStatusCode.OK, result);
                }
            }
        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<ma_kengen> IsAlreadyExistsKengen(ChangeSet<ma_kengen> value)
        {
            InvalidationSet<ma_kengen> result = new InvalidationSet<ma_kengen>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;
            
                    var createdCount = value.Created.Count(target => target.cd_kengen == item.cd_kengen);
                    var isDeleted = value.Deleted.Exists(target => target.cd_kengen == item.cd_kengen);
                    var isDatabaseExists = (context.ma_kengen.Find(item.cd_kengen) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<ma_kengen>(Properties.Resources.ValidationKey, item, "cd_kengen"));
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
        private InvalidationSet<ma_kinou> IsAlreadyExistsKinou(ChangeSet<ma_kinou> value)
        {
            InvalidationSet<ma_kinou> result = new InvalidationSet<ma_kinou>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;
            
                    var createdCount = value.Created.Count(target => target.cd_kengen == item.cd_kengen && target.id_gamen == item.id_gamen && target.id_kino == item.id_kino);
                    var isDeleted = value.Deleted.Exists(target => target.cd_kengen == item.cd_kengen && target.id_gamen == item.id_gamen && target.id_kino == item.id_kino);
                    var isDatabaseExists = (context.ma_kinou.Find(item.cd_kengen, item.id_gamen, item.id_kino) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<ma_kinou>(Properties.Resources.ValidationKey, item, "id_gamen"));
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
        private KengenMasterChangeResponse SaveHeaderData(ShohinKaihatsuEntities context, KengenMasterChangeResponse result, ChangeSet<ma_kengen> value)
        {
            value.SetDataSaveInfo(this.User.Identity);                
            value.AttachTo(context);
            context.SaveChanges();

            // TODO: 返却用のオブジェクトを生成します。
            result.Header.AddRange(value.Flatten());
            return result;
        }

        /// <summary>
        /// target 情報の一括更新（追加・更新・削除）を実行します
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns>target 情報の更新結果オブジェクト</returns>
        private KengenMasterChangeResponse SaveDetailData(ShohinKaihatsuEntities context, KengenMasterChangeResponse result, ChangeSet<ma_kinou> value)
        {
            value.SetDataSaveInfo(this.User.Identity);
            value.AttachTo(context);
            context.SaveChanges();

            // TODO: 返却用のオブジェクトを生成します。
            result.Detail.AddRange(value.Flatten());
            return result;
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class KengenMasterChangeResponse
    {
        public KengenMasterChangeResponse()
        {
            this.Header = new List<ma_kengen>();
            this.Detail = new List<ma_kinou>();
        }

        public List<ma_kengen> Header { get; set; }
        public List<ma_kinou> Detail { get; set; }
    }

    //Get data header and detail.
    public class DataKengenKinoMaster {
        public List<ma_kengen> header { get; set; }
        public List<ma_kinou> detail { get; set; }
    }

    /// <summary>
    /// ChangeSet header and detail
    /// </summary>
    public class ChangeSetkengenMaster
    {
        public ChangeSet<ma_kengen> header { get; set; }
        public ChangeSet<ma_kinou> detail { get; set; }
        public short? cd_kengen { get; set; }
    }

    /// <summary>
    /// kengen Code
    /// </summary>
    public class kengenCode
    {
        public short? cd_kengen { get; set; }
    }
    #endregion
}
