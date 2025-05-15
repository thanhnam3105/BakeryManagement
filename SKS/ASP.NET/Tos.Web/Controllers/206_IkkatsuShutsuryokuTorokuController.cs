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
using System.Data.Objects;
using System.IO;
using Tos.Web.Logging;
using System.Security.Principal;
using System.Runtime.InteropServices;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _206_IkkatsuShutsuryokuTorokuController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された OData クエリに該当するすべてのtarget情報を取得します。
        /// </summary>
        /// <param name="options"> OData クエリ</param>
        /// <returns>target情報</returns>
        public HttpResponseMessage Get()
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                var result = (from p in context.ma_literal
                              where p.cd_category == Properties.Resources.cd_category_25
                              orderby p.cd_literal ascending
                              select new
                              {
                                  cd_literal = p.cd_literal,
                                  nm_literal = p.nm_literal
                              }).ToList();
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }

        }
        public HttpResponseMessage GetSearch([FromUri] paramSearch param)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var user = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
                context.Configuration.ProxyCreationEnabled = false;
                StoredProcedureResult<sp_shohinkaihatsu_search_206_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_206_Result>();
                var data=context.sp_shohinkaihatsu_search_206( param.flg_seiho
                                                               ,param.flg_denso
                                                                ,param.flg_kakunin
                                                                ,param.dt_from
                                                                ,param.dt_to
                                                                ,user.cd_kaisha.PadLeft(4, '0')
                                                            ).ToList();
                result.Items = data;
                result.Count = result.Items.Count();
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }
        }

        public class paramSearch
        {
            public int flg_denso { get; set; }
            public int flg_kakunin { get; set; }
            public string flg_seiho { get; set; }
            public string dt_from { get; set; }
            public string dt_to { get; set; }
        }

        public HttpResponseMessage GetSearchDetail([FromUri] int no_ikkatsu)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                var user = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
                context.Configuration.ProxyCreationEnabled = false;
                StoredProcedureResult<sp_shohinkaihatsu_search_view_206_Result> result = new StoredProcedureResult<sp_shohinkaihatsu_search_view_206_Result>();
                var data = context.sp_shohinkaihatsu_search_view_206(
                                                                    Properties.Resources.cd_category_27
                                                                    ,no_ikkatsu
                                                                ).ToList();
                result.Items = data;
                result.Count = result.Items.Count();
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }
        }
        [DllImport("advapi32.DLL", SetLastError = true)]
        public static extern int LogonUser(string lpszUsername, string lpszDomain, string lpszPassword, int dwLogonType, int dwLogonProvider, ref IntPtr phToken);
        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage PostCopyFile([FromBody]ChangeSetIkkatsuToroku value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }

            
            // TODO: 保存処理を実行します。
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                try
                {
                    var result = new NoIkkatsuChangeResponse();
                    //get no_ikkatsu
                    ObjectParameter no_ikkatsu = new ObjectParameter("no_ikkatsu", typeof(Int32));
                    ObjectParameter cd_error = new ObjectParameter("cd_error", typeof(string));
                    context.sp_get_no_ikkatsu(no_ikkatsu, cd_error);
                    var ikkatsu = decimal.Parse(no_ikkatsu.Value.ToString());

                    //result
                    result.no_ikkatsu = no_ikkatsu.Value.ToString();
                    result.resCopy = false;

                    var user = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
                    var datenow = DateTime.Now;
                    //copy first after insert
                    for (var i = 0; i < value.table.Created.Count; i++)
                    {
                        try
                        {
                            var path = HttpContext.Current.Server.MapPath(Properties.Settings.Default.sourcePath_no_ikkatsu);
                            var FileInFolder = Directory.GetFiles(path);
                            if (FileInFolder.Length != 0)
                            {
                                //search seiho bunsho in source path
                                foreach (string strFilename in FileInFolder)
                                {
                                    var fileName = Path.GetFileName(strFilename);
                                    string extension = Path.GetExtension(strFilename);
                                    //check file name with no_seiho (- 5 is '.xlsx')
                                    if (fileName.Substring(0, fileName.Length - extension.Length) == value.table.Created[i].no_seiho)
                                    {
                                        // Use static Path methods to extract only the file name from the path.
                                        //var pathNoIkkatsu = HttpContext.Current.Server.MapPath(Properties.Settings.Default.destinationPath_no_ikkatsu) + ikkatsu;
                                        var pathNoIkkatsu = Properties.Settings.Default.destinationPath_no_ikkatsu + ikkatsu;
                                        if (Properties.Settings.Default.userServer == "" || Properties.Settings.Default.passServer == "")
                                        {
                                            Directory.CreateDirectory(pathNoIkkatsu);
                                            var destFile = Path.Combine(pathNoIkkatsu, fileName);
                                            File.Copy(strFilename, destFile, true);
                                            value.table.Created[i].flg_seihobunsho_output = 1;//copy success = 1
                                            value.table.Created[i].flg_haigosakusei_output = 1;//copy fail = 2
                                            break;
                                        }
                                        else
                                        {
                                            IntPtr admin_token = default(IntPtr);
                                            //Added these 3 lines
                                            WindowsIdentity wid_current = WindowsIdentity.GetCurrent();
                                            WindowsIdentity wid_admin = null;
                                            WindowsImpersonationContext wic = null;
                                            string[] separators = { "//" };
                                            string domain = Properties.Settings.Default.destinationPath_no_ikkatsu.ToString().Split(separators, StringSplitOptions.None)[1];
                                            domain = domain.Split(new[] { "/" }, StringSplitOptions.None)[0];
                                            if (LogonUser(Properties.Settings.Default.userServer, domain, Properties.Settings.Default.passServer, 9, 0, ref admin_token) != 0)
                                            {
                                                wid_admin = new WindowsIdentity(admin_token);
                                                wic = wid_admin.Impersonate();
                                                Directory.CreateDirectory(pathNoIkkatsu);
                                                var destFile = Path.Combine(pathNoIkkatsu, fileName);
                                                File.Copy(strFilename, destFile, true);
                                                value.table.Created[i].flg_seihobunsho_output = 1;//copy success = 1
                                                value.table.Created[i].flg_haigosakusei_output = 1;//copy fail = 2
                                                break;
                                            }
                                        }
                                    }
                                    else
                                    {
                                        value.table.Created[i].flg_seihobunsho_output = 2;//copy success = 1
                                        value.table.Created[i].flg_haigosakusei_output = 2;//copy fail = 2
                                    }
                                }
                            }
                            else
                            {
                                value.table.Created[i].flg_seihobunsho_output = 2;//copy success = 1
                                value.table.Created[i].flg_haigosakusei_output = 2;//copy fail = 2
                            }
                        }
                        catch (Exception er)
                        {
                            Logger.App.Error(er.Message, er);
                            value.table.Created[i].flg_seihobunsho_output = 2;//copy success = 1
                            value.table.Created[i].flg_haigosakusei_output = 2;//copy fail = 2
                        }
                        value.table.Created[i].no_ikkatsu = ikkatsu;
                        value.table.Created[i].flg_seihobunsho = 1;
                        value.table.Created[i].flg_haigosakusei = 1;
                        value.table.Created[i].dt_from = DateTime.Parse(value.header.Created[0].dt_from);
                        value.table.Created[i].dt_to = DateTime.Parse(value.header.Created[0].dt_to);
                        value.table.Created[i].flg_01 = (byte)value.header.Created[0].flg_01;
                        value.table.Created[i].flg_19 = (byte)value.header.Created[0].flg_19;
                        value.table.Created[i].flg_20 = (byte)value.header.Created[0].flg_20;
                        value.table.Created[i].flg_21 = (byte)value.header.Created[0].flg_21;
                        value.table.Created[i].flg_denso = (byte)value.header.Created[0].flg_denso;
                        value.table.Created[i].flg_kakunin = (byte)value.header.Created[0].flg_kakunin;
                        value.table.Created[i].kbn_kaihatsu_kaisha = 0;
                        value.table.Created[i].memo = value.header.Created[0].memo;
                        value.table.Created[i].status = (byte)value.detail.Created[i].flg_denso_jyotai;
                        value.table.Created[i].dt_seiho_sakusei = DateTime.Parse(value.detail.Created[i].dt_seiho_sakusei);
                        value.table.Created[i].dt_denso_toroku = DateTime.Parse(value.detail.Created[i].dt_denso_toroku);
                        if (value.detail.Created[i].dt_denso_kanryo == "" || value.detail.Created[i].dt_denso_kanryo == null)
                        {
                            value.table.Created[i].dt_denso_kanryo = null;
                        }
                        else
                        {
                            value.table.Created[i].dt_denso_kanryo = DateTime.Parse(value.detail.Created[i].dt_denso_kanryo);
                        }
                        value.table.Created[i].dt_toroku = datenow;
                        value.table.Created[i].cd_toroku_kaisha = int.Parse(user.cd_kaisha);
                        value.table.Created[i].cd_toroku = user.EmployeeCD.ToString();
                    }

                    foreach (var item in value.table.Created)
                    {
                        //copy fail
                        if ((int)item.flg_haigosakusei_output == 2)
                        {
                            result.resCopy = true;
                        }
                    }

                    result.table = value.table;

                    return Request.CreateResponse<NoIkkatsuChangeResponse>(HttpStatusCode.OK, result);
                }
                catch (Exception ex)
                {
                    return Request.CreateResponse<string>(HttpStatusCode.BadRequest, ex.Message);
                }
            }
        }

        /// <summary>
        /// パラメータで受け渡された target 情報をもとにエントリーを一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]NoIkkatsuChangeResponse value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.InternalServerError, Properties.Resources.NotNullAllow);
            }


            // TODO: 保存処理を実行します。
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                try
                {
                    IObjectContextAdapter adapter = context as IObjectContextAdapter;
                    DbConnection connection = adapter.ObjectContext.Connection;
                    connection.Open();

                    using (DbTransaction transaction = connection.BeginTransaction())
                    {
                        // TODO: 保存処理を実行します。
                        var datenow = DateTime.Now;
                        for (var i = 0; i < value.table.Created.Count; i++)
                        {
                            value.table.Created[i].dt_toroku = datenow;
                        }
                        SaveData(context, value.table);

                        transaction.Commit();
                    }
                    return Request.CreateResponse<NoIkkatsuChangeResponse>(HttpStatusCode.OK, value);
                }
                catch (Exception ex)
                {
                    return Request.CreateResponse<string>(HttpStatusCode.BadRequest, ex.Message);
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
        private InvalidationSet<cn_no_ikkatsu> IsAlreadyExists(ChangeSet<cn_no_ikkatsu> value)
        {
            InvalidationSet<cn_no_ikkatsu> result = new InvalidationSet<cn_no_ikkatsu>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    // TODO: 変更セット内で重複したキーが入力されたかどうかのチェック、およびデータベース上に重複した主キーがあるかどうかのチェックを行います。
                    bool isDepulicate = false;

                    var createdCount = value.Created.Count(target => target.no_ikkatsu == item.no_ikkatsu);
                    var isDeleted = value.Deleted.Exists(target => target.no_ikkatsu == item.no_ikkatsu);
                    var isDatabaseExists = (context.tr_ikkatsu_output.Find(item.no_ikkatsu) != null);

                    isDepulicate |= (createdCount > 1);
                    isDepulicate |= (!isDeleted && createdCount == 1 && isDatabaseExists);

                    if (isDepulicate)
                    {
                        result.Add(new Invalidation<cn_no_ikkatsu>(Properties.Resources.ValidationKey, item, "no_ikkatsu"));
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
        private void SaveData(ShohinKaihatsuEntities context, ChangeSet<tr_ikkatsu_output> value)
        {
            //value.SetDataSaveInfo(this.User.Identity);                
            value.AttachTo(context);
            context.SaveChanges();
        }


        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"


    /// <summary>
    /// ChangeSet header and detail
    /// </summary>
    public class ChangeSetIkkatsuToroku
    {
        public ChangeSet<headerIkkatsuToroku> header { get; set; }
        public ChangeSet<detailIkkatsuToroku> detail { get; set; }
        public ChangeSet<tr_ikkatsu_output> table { get; set; }
    }
    public class headerIkkatsuToroku
    {
        public int flg_denso { get; set; }
        public int flg_kakunin { get; set; }
        public string dt_from { get; set; }
        public string dt_to { get; set; }

        public int flg_01 { get; set; }
        public int flg_19 { get; set; }
        public int flg_20 { get; set; }
        public int flg_21 { get; set; }

        public string memo { get; set; }

    }
    public class detailIkkatsuToroku
    {
        public string dt_denso_kanryo { get; set; }
        public string dt_denso_toroku { get; set; }
        public string dt_seiho_sakusei { get; set; }
        public int flg_denso_jyotai { get; set; }
        public string nm_literal { get; set; }
        public string nm_seiho { get; set; }
        public string no_seiho { get; set; }
    }
    public class IkkatsuShutsuryoTorokuChangeResponse
    {
        public IkkatsuShutsuryoTorokuChangeResponse()
        {
            this.Detail = new List<tr_ikkatsu_output>();
        }

        public List<tr_ikkatsu_output> Detail { get; set; }
    }
    public class NoIkkatsuChangeResponse
    {
        public string no_ikkatsu { get; set; }
        public bool resCopy { get; set; }
        public ChangeSet<tr_ikkatsu_output> table { get; set; }
    }

    #endregion
}
