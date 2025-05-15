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
using System.IO;
using Tos.Web.Properties;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class SeihoTorokuController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された 値に該当するすべてのtarget情報を StoredProcedure を利用して取得します。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public StoredProcedureResult<sp_shohinkaihatsu_search_204_Result> Get([FromUri] SeihoTorokuPara value)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;


            byte haishi = byte.Parse(Properties.Resources.haishi);
            byte henshuchu_status = byte.Parse(Properties.Resources.henshuchu_status);
            byte JushinchuStatus = byte.Parse(Properties.Resources.JushinchuStatus);

            //TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
            //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
            StoredProcedureResult<sp_shohinkaihatsu_search_204_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_204_Result>();
            //set timeout
            ((IObjectContextAdapter)context).ObjectContext.CommandTimeout = Settings.Default.TimeOutQuery;

            result.Items = context.sp_shohinkaihatsu_search_204(value.no_seiho_kaisha
                                                                , value.no_seiho_shurui
                                                                , value.no_seiho_nen
                                                                , value.no_seiho_renban
                                                                , value.nm_seiho
                                                                , value.nm_kaisha
                                                                , value.kbn_denso_jyotai
                                                                , value.nm_kojyo
                                                                , value.dt_seiho_sakusei_from
                                                                , value.dt_seiho_sakusei_to
                                                                , value.nm_tanto_shinsei
                                                                , value.nm_tanto_denso
                                                                , value.kbn_sort
                                                                , value.cd_category_27
                                                                , Properties.Resources.cd_jyotai_0
                                                                , Properties.Resources.cd_jyotai_1
                                                                , Properties.Resources.cd_jyotai_2
                                                                , value.skip
                                                                , value.top
                                                                , henshuchu_status
                                                                , haishi
                                                                , JushinchuStatus).ToList();
            result.Count = 0;
            if (result.Items.Count() > 0)
            {
                result.Count = (int)result.Items.FirstOrDefault().total_rows;
            }

            //TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;
        }

        /// <summary>
        /// Get haigo with no_seiho。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        public List<sp_shohinkaihatsu_haigoseiho_204_Result> GetHaigo([FromUri] string no_seiho, string kbn_status)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;

            //TODO: DbContext のメソッドとしてストアドプロシージャを呼び出します。※ストアドのアウトプットパラメータとしてcountが返却される前提です
            //ObjectParameter count = new ObjectParameter("rowcount", typeof(int));
            var result = context.sp_shohinkaihatsu_haigoseiho_204(no_seiho, kbn_status).ToList();

            //TODO: DbContext のメソッドとしてデータカウントを取得するストアドプロシージャを呼び出します。
            return result;
        }

        /// <summary>
        /// Check status ma_haigo_header。
        /// </summary>
        /// <param name="options"></param>
        /// <returns>target情報</returns>
        [HttpGet]
        public bool CheckHaigoStatus([FromUri] string no_seiho)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;

            byte henshuchu_status = byte.Parse(Properties.Resources.henshuchu_status);

            var result = context.ma_haigo_header.Where(m => m.no_seiho == no_seiho && m.kbn_haishi != 1 && m.status == henshuchu_status).FirstOrDefault();

            if (result != null) {
                return true;
            }

            return false;
        } 

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]ParaSeihoTorokuPost data)
        {
            ChangeSet<ma_seiho_denso> value = data.seiho;
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }
            var result = new SeihoToriokuChangeResponse();

            var dateNow = DateTime.Now;

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();

                using (DbTransaction transaction = connection.BeginTransaction())
                {
                    // TODO: キー項目の重複チェックを行います。
                    InvalidationSet<ma_seiho_denso> headerInvalidations = IsAlreadyExists(value);
                    if (headerInvalidations.Count > 0)
                    {
                        return Request.CreateResponse<InvalidationSet<ma_seiho_denso>>(HttpStatusCode.BadRequest, headerInvalidations);
                    }

                    foreach (var item in value.Created)
                    {

                        if (item.flg_denso_taisho)
                        {
                            item.cd_denso_tanto_kaisha = data.cd_kaisha_login;
                            item.cd_denso_tanto = data.id_user_login;
                            item.dt_denso_toroku = dateNow;
                        }
                        else
                        {
                            item.cd_denso_tanto_kaisha = null;
                            item.cd_denso_tanto = null;
                            item.dt_denso_toroku = null;
                        }

                    }

                    foreach (var item in value.Updated)
                    {
                        var dataOriginal = context.ma_seiho_denso.Where(m => m.no_seiho == item.no_seiho && m.cd_kaisha == item.cd_kaisha && m.cd_kojyo == item.cd_kojyo).FirstOrDefault();
                        if (dataOriginal != null)
                        {
                            if (item.flg_denso_taisho != dataOriginal.flg_denso_taisho && item.flg_denso_taisho)
                            {
                                item.cd_denso_tanto_kaisha = data.cd_kaisha_login;
                                item.cd_denso_tanto = data.id_user_login;
                                item.dt_denso_toroku = dateNow;
                            }
                            if (item.flg_denso_taisho != dataOriginal.flg_denso_taisho && !item.flg_denso_taisho)
                            {
                                item.cd_denso_tanto_kaisha = null;
                                item.cd_denso_tanto = null;
                                item.dt_denso_toroku = null;
                            }

                            if (item.flg_denso_taisho == dataOriginal.flg_denso_taisho) {
                                item.cd_denso_tanto_kaisha = dataOriginal.cd_denso_tanto_kaisha;
                                item.cd_denso_tanto = dataOriginal.cd_denso_tanto;
                                item.dt_denso_toroku = dataOriginal.dt_denso_toroku;
                            }
                        }
                    }
                   
                    // TODO: 保存処理を実行します。
                    result = SaveData(value);

                    //申請済	
                    var Shonin2Status = byte.Parse(Properties.Resources.Shonin2Status);
                    //伝送済み
                    var denso_zumi_status = byte.Parse(Properties.Resources.denso_zumi_status);
                    List<ParaDataHaigoUpdate> listSeiho = new List<ParaDataHaigoUpdate>();
                    foreach (var item in value.Created)
                    {
                        ParaDataHaigoUpdate haigo = new ParaDataHaigoUpdate();
                        var dataSeiho = context.ma_seiho_denso.Where(m => m.no_seiho == item.no_seiho && m.flg_denso_taisho == true).FirstOrDefault();
                        if (dataSeiho != null && listSeiho.Where(m => m.no_seiho == item.no_seiho).FirstOrDefault() == null)
                        {
                            haigo.no_seiho = item.no_seiho;
                            haigo.status = denso_zumi_status;

                            listSeiho.Add(haigo);
                        }
                        if (dataSeiho == null && listSeiho.Where(m => m.no_seiho == item.no_seiho).FirstOrDefault() == null)
                        {
                            haigo.no_seiho = item.no_seiho;
                            haigo.status = Shonin2Status;

                            listSeiho.Add(haigo);
                        }
                    }
                    foreach (var item in value.Updated)
                    {
                        ParaDataHaigoUpdate haigo = new ParaDataHaigoUpdate();
                        var dataSeiho = context.ma_seiho_denso.Where(m => m.no_seiho == item.no_seiho && m.flg_denso_taisho == true).FirstOrDefault();
                        if (dataSeiho != null && listSeiho.Where(m => m.no_seiho == item.no_seiho).FirstOrDefault() == null)
                        {
                            haigo.no_seiho = item.no_seiho;
                            haigo.status = denso_zumi_status;

                            listSeiho.Add(haigo);
                        }
                        if (dataSeiho == null && listSeiho.Where(m => m.no_seiho == item.no_seiho).FirstOrDefault() == null)
                        {
                            haigo.no_seiho = item.no_seiho;
                            haigo.status = Shonin2Status;

                            listSeiho.Add(haigo);
                        }
                    }
                    foreach (var item in value.Deleted)
                    {
                        ParaDataHaigoUpdate haigo = new ParaDataHaigoUpdate();
                        var dataSeiho = context.ma_seiho_denso.Where(m => m.no_seiho == item.no_seiho && m.flg_denso_taisho == true).FirstOrDefault();
                        if (dataSeiho != null && listSeiho.Where(m => m.no_seiho == item.no_seiho).FirstOrDefault() == null)
                        {
                            haigo.no_seiho = item.no_seiho;
                            haigo.status = denso_zumi_status;

                            listSeiho.Add(haigo);
                        }
                        if (dataSeiho == null && listSeiho.Where(m => m.no_seiho == item.no_seiho).FirstOrDefault() == null)
                        {
                            haigo.no_seiho = item.no_seiho;
                            haigo.status = Shonin2Status;

                            listSeiho.Add(haigo);
                        }
                    }

                    foreach (var item in data.deleteData)
                    {
                        var dataDenso = context.ma_seiho_denso.Where(m => m.no_seiho == item.no_seiho && m.cd_kaisha == item.cd_kaisha && m.cd_kojyo == item.cd_kojyo).FirstOrDefault();
                        if (dataDenso != null) {
                            context.ma_seiho_denso.Attach(dataDenso);
                            context.ma_seiho_denso.Remove(dataDenso);
                        }
                    }
                   
                    foreach (var item in listSeiho)
                    {
                        var statusValue = Shonin2Status;
                        if (statusValue == item.status) {
                            statusValue = denso_zumi_status;
                        }
                        var listHaigo = context.ma_haigo_header.Where(m => m.no_seiho == item.no_seiho && m.status == statusValue).ToList();

                        foreach (var haigo in listHaigo)
                        {
                            haigo.status = item.status;
                            haigo.dt_henko = DateTime.Now;
                            haigo.cd_koshin_kaisha = data.cd_kaisha_login;
                            haigo.cd_koshin = data.id_user_login;

                            context.ma_haigo_header.Attach(haigo);
                            context.Entry<ma_haigo_header>(haigo).State = EntityState.Modified;
                        }
                    }
                    context.SaveChanges();

                    transaction.Commit();
                }
            }
            return Request.CreateResponse<SeihoToriokuChangeResponse>(HttpStatusCode.OK, result);

        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を更新します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Put([FromBody]NotApplySeiho value)
        {
            var result = new SeihoToriokuChangeResponse();
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();
                CommonController common = new CommonController();
                using (DbTransaction transaction = connection.BeginTransaction())
                {

                    var dataDenso = context.ma_seiho_denso.Where(m => m.no_seiho == value.no_seiho && m.flg_denso_jyotai == true).FirstOrDefault();
                    if (dataDenso != null) {
                        return Request.CreateResponse<string>(HttpStatusCode.Conflict, "AP0061");
                    }

                    var DateNow = DateTime.Now;
                    var ip_address = common.GetIPClientAddress();
                    context.sp_shohinkaihatsu_deleteapply_204(value.no_seiho, value.id_user_login, value.cd_kaisha_login, DateNow, ip_address, value.nm_shori, value.nm_ope);

                    string nm_file = value.no_seiho;

                    string templatepath = HttpContext.Current.Server.MapPath(Properties.Settings.Default.sourcePath_no_ikkatsu);
                    string templateFile = templatepath + "\\" + value.no_seiho + Properties.Resources.ExcelExtension;

                    if (File.Exists(templateFile))
                    {
                        File.Delete(templateFile);
                    }

                    transaction.Commit();
                }
            }
            return Request.CreateResponse<SeihoToriokuChangeResponse>(HttpStatusCode.OK, result);

        }

        /// <summary>
        /// パラメーターで受け渡されたtarget情報を削除します
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        //public SeihoToriokuChangeResponse Delete([FromBody]ChangeSet<object/*TODO:targetの型を指定します*/> value)
        //{

        //    var result = SaveData(value);
        //    return Request.CreateResponse<SeihoToriokuChangeResponse>(HttpStatusCode.OK, result);

        //}

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<ma_seiho_denso> IsAlreadyExists(ChangeSet<ma_seiho_denso> value)
        {
            InvalidationSet<ma_seiho_denso> result = new InvalidationSet<ma_seiho_denso>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;
            
                    var createdCount = value.Created.Count(target => target.no_seiho == item.no_seiho && target.cd_kaisha == item.cd_kaisha && target.cd_kojyo == item.cd_kojyo);
                    var isDeleted = value.Deleted.Exists(target => target.no_seiho == item.no_seiho && target.cd_kaisha == item.cd_kaisha && target.cd_kojyo == item.cd_kojyo);
                    var isDatabaseExists = (context.ma_seiho_denso.Find(item.no_seiho, item.cd_kaisha, item.cd_kojyo) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<ma_seiho_denso>(Properties.Resources.ValidationKey, item, "no_seiho"));
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
        private SeihoToriokuChangeResponse SaveData(ChangeSet<ma_seiho_denso> value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {            
                value.AttachTo(context);
                context.SaveChanges();
            }

            // TODO: 返却用のオブジェクトを生成します。
            var result = new SeihoToriokuChangeResponse();
            result.Detail.AddRange(value.Flatten());
            return result;
        }

        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class SeihoToriokuChangeResponse
    {
        public SeihoToriokuChangeResponse()
        {
            this.Detail = new List<ma_seiho_denso>();
        }

        public List<ma_seiho_denso> Detail { get; set; }
    }

    //Parameter from screen search
    public class SeihoTorokuPara {
        public string no_seiho_kaisha { get; set; }
        public string no_seiho_shurui { get; set; }
        public string no_seiho_nen { get; set; }
        public string no_seiho_renban { get; set; }
        public string nm_seiho { get; set; }
        public short? nm_kaisha { get; set; }
        public string kbn_denso_jyotai { get; set; }
        public short? nm_kojyo { get; set; }
        public DateTime? dt_seiho_sakusei_from { get; set; }
        public DateTime? dt_seiho_sakusei_to { get; set; }
        public string nm_tanto_shinsei { get; set; }
        public string nm_tanto_denso { get; set; }
        public short? kbn_sort { get; set; }
        public string cd_category_27 { get; set; }
        public short skip { get; set; }
        public short top { get; set; }
    }

    //Para not apply
    public class NotApplySeiho {
        public string no_seiho { get; set; }
        public string nm_shori { get; set; }
        public string nm_ope { get; set; }
        public decimal id_user_login { get; set; }
        public int cd_kaisha_login { get; set; }

    }

    //Para save
    public class ParaSeihoTorokuPost {
        public ChangeSet<ma_seiho_denso> seiho { get; set; }
        public List<ParaDataDelete> deleteData { get; set; }
        public string id_user_login { get; set; }
        public int cd_kaisha_login { get; set; }
    }

    //List data delete
    public class ParaDataDelete {
        public string no_seiho { get; set; }
        public short? cd_kaisha { get; set; }
        public short? cd_kojyo { get; set; }
    }

    //List data update ma_haigo_header
    public class ParaDataHaigoUpdate
    {
        public string no_seiho { get; set; }
        public byte status { get; set; }
    }
    #endregion
}
