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
using Tos.Web.DataFP;
using System.Data.SqlClient;
using System.Data;

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class CommonController : ApiController
    {
        #region Function common
        public readonly string FlgFlase = "0";
        public readonly string FlgTrue = "1";
        public readonly string NoHanFirst = "1";
        /// <summary>
        /// Get IP address  
        /// </summary>
        /// <returns>IP address</returns>
        public string GetIPClientAddress()
        {
            System.Web.HttpContext context = System.Web.HttpContext.Current;
            string ipAddress = context.Request.ServerVariables["HTTP_X_FORWARDED_FOR"];

            if (!string.IsNullOrEmpty(ipAddress))
            {
                string[] addresses = ipAddress.Split(',');
                if (addresses.Length != 0)
                {
                    return addresses[0];
                }
            }
            return context.Request.ServerVariables["REMOTE_ADDR"];
        }

        /// <summary>
        /// Check cd_kaisha and cd_kojyo
        /// </summary>
        /// <returns>Get data infor with cd_kaisha and cd_kojyo</returns>
        public bool CheckKaishaKojyo(int cd_kaisha, int cd_kojyo) {
            bool result = false;
            using(ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                var kaishakojyo = context.vw_kaisha_kojyo.Where(m => m.cd_kaisha == cd_kaisha && m.cd_kojyo == cd_kojyo).FirstOrDefault();
                if (kaishakojyo != null) {
                    result = true;
                }
            }

            return result;
        }

        /// <summary>
        /// Get kbn_hin 3,4
        /// </summary>
        /// <returns>Get list kbn_hin</returns>
        public object GetShinaKubun(int cd_kaisha, int cd_kojyo)
        {
            object results;

            if (!CheckKaishaKojyo(cd_kaisha, cd_kojyo)) {
                return null;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;

                string kbn_hin_haigo = Properties.Resources.kbn_hin_haigo,
                    kbn_hin_shikakarihin = Properties.Resources.kbn_hin_shikakarihin;
                var query = "SELECT kbn_hin, nm_kbn_hin"
                            + " FROM ma_kbn_hin"
                            + " WHERE kbn_hin IN (@kbn_hin_haigo, @kbn_hin_shikakarihin)"
                            + " ORDER BY kbn_hin";

                results = context.Database.SqlQuery<ma_kbn_hin_fp>(query, new SqlParameter("@kbn_hin_haigo", kbn_hin_haigo)
                                                                        , new SqlParameter("@kbn_hin_shikakarihin", kbn_hin_shikakarihin)).ToList();
            }

            return results;
        }

        /// <summary>
        /// Get bunrui
        /// </summary>
        /// <returns>Get list ma_bunrui</returns>
        public object GetMaBunrui(int cd_kaisha, int cd_kojyo)
        {
            object results;
            if (!CheckKaishaKojyo(cd_kaisha, cd_kojyo))
            {
                return null;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;

                string kbn_hin_haigo = Properties.Resources.kbn_hin_haigo,
                    kbn_hin_shikakarihin = Properties.Resources.kbn_hin_shikakarihin;
                var query = "SELECT cd_bunrui, nm_bunrui"
                            + " FROM ma_bunrui"
                            + " WHERE kbn_hin = @kbn_hin_shikakarihin"
                            + " AND flg_sakujyo = @FlgFlase"
                            + " AND flg_mishiyo = @FlgFlase"
                            + " ORDER BY cd_bunrui";

                results = context.Database.SqlQuery<ma_bunrui_fp>(query, new SqlParameter("@kbn_hin_shikakarihin", kbn_hin_shikakarihin)
                                                                        , new SqlParameter("@FlgFlase", FlgFlase)).ToList();
            }

            return results;
        }

        /// <summary>
        /// Get haigo
        /// </summary>
        /// <returns>Get list haigo</returns>
        public object GetHaigo(string m_kirikae, int cd_kaisha, int cd_kojyo, string cd_code)
        {
            object results;
            if (!CheckKaishaKojyo(cd_kaisha, cd_kojyo))
            {
                return null;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;

                string kbn_hin_haigo = Properties.Resources.kbn_hin_haigo,
                    kbn_hin_shikakarihin = Properties.Resources.kbn_hin_shikakarihin;
                var query = "SELECT cd_haigo, nm_haigo, kbn_hin";
                if (m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    query = query + " FROM ma_haigo_mei_hyoji";
                } else {
                    query = query + " FROM ma_haigo_mei";
                }
                query = query + " WHERE kbn_hin IN (@kbn_hin_haigo, @kbn_hin_shikakarihin)"
                        + " AND flg_sakujyo = @FlgFlase"
                        + " AND flg_mishiyo = @FlgFlase"
                        + " AND no_han = @NoHanFirst"
                        + " AND cd_haigo = @cd_haigo";
                if (m_kirikae == Properties.Resources.m_kirikae_foodprocs)
                {
                    query = query + " AND qty_kihon = qty_haigo_h";
                }

                results = context.Database.SqlQuery<haigoData>(query, new SqlParameter("@kbn_hin_haigo", kbn_hin_haigo)
                                                                        , new SqlParameter("@kbn_hin_shikakarihin", kbn_hin_shikakarihin)
                                                                        , new SqlParameter("@cd_haigo", cd_code)
                                                                        , new SqlParameter("@NoHanFirst", NoHanFirst)
                                                                        , new SqlParameter("@FlgFlase", FlgFlase)).ToList();
            }

            return results;
        }

        /// <summary>
        /// Get seihin
        /// </summary>
        /// <returns>Get list seihin</returns>
        public object GetSeihin(int m_kirikae, int cd_kaisha, int cd_kojyo, string cd_code)
        {
            object results;
            if (!CheckKaishaKojyo(cd_kaisha, cd_kojyo))
            {
                return null;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;
                var query = "SELECT cd_hin, nm_hin";
                query = query + " FROM ma_seihin";
                query = query + " WHERE cd_hin = @cd_hin";
                query = query + " AND flg_sakujyo = @FlgFlase";
                query = query + " AND flg_mishiyo = @FlgFlase";


                results = context.Database.SqlQuery<seihinData>(query, new SqlParameter("@cd_hin", cd_code)
                                                                , new SqlParameter("@FlgFlase", FlgFlase)).ToList();
            }

            return results;
        }

        /// <summary>
        /// Get seihin
        /// </summary>
        /// <returns>Get list seihin</returns>
        public object GetGenryo(string m_kirikae, int cd_kaisha, int cd_kojyo, decimal cd_code)
        {
            object results;
            if (!CheckKaishaKojyo(cd_kaisha, cd_kojyo))
            {
                return null;
            }
            using (FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;

                var kbn_hin_shikakarihin = Properties.Resources.kbn_hin_shikakarihin;

                var query = "SELECT TOP 1 cd_hin, nm_hin, kbn_hin";
                if (m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    query = query + " FROM SS_vw_hin_hyoji";
                }
                else
                {
                    query = query + " FROM SS_vw_hin";
                }
                query = query + " WHERE cd_hin = @cd_hin";
                query = query + " ORDER BY cd_hin, kbn_hin";

                var parameters = new object[]
                {
                    new SqlParameter("@cd_hin", SqlDbType.Decimal) { Value = cd_code }
                };

                results = context.Database.SqlQuery<genryoData>(query, parameters).ToList();
            }

            return results;
        }

        /// <summary>
        /// Fill 0 at the begin of string
        /// value: 1 and length 
        /// </summary>
        /// <param name="value"></param>
        /// <param name="length"></param>
        /// <returns></returns>
        public static string getFullString(string value, int? length)
        {
            string wrap = "00000000000000000000";
            if (value == null || length == null)
            {
                return string.Empty;
            }
            if (value.Length >= length)
            {
                return value;
            }
            value = wrap + value;
            return value.Substring(value.Length - (length ?? 0));
        }

        /// <summary>
        /// Fill 0 at the end of string
        /// value: 1 and length 
        /// </summary>
        /// <param name="value"></param>
        /// <param name="length"></param>
        /// <returns></returns>
        public static string addTrailingZero(string value, int? length)
        {
            string wrap = "00000000000000000000";
            if (value == null || value == string.Empty || length == null || length == 0)
            {
                return value;
            }
            int n;
            bool isNumeric = int.TryParse(value, out n);
            if (!isNumeric)
            {
                return value;
            }
            int dotIndex = value.IndexOf(".");
            if (dotIndex >= 0 && value.Length - dotIndex > length)
            {
                return value;
            }
            if (dotIndex < 0)
            {
                dotIndex = value.Length;
                value += ".";
                value += wrap;
            }
            
            return value.Substring(0, dotIndex + 1 + (length ?? 0));
        }

        /// <summary>
        /// Convert color to number
        /// </summary>
        /// <param name="value"></param>
        /// <param name="length"></param>
        /// <returns></returns>
        public ColorConvert GetNumberColor(string hexValue)
        {
            string hexaColorDefault = "#FFFFFF";
            if (hexValue == null) {
                hexValue = hexaColorDefault;
            }
            // Convert the hex string back to the number
            ColorConvert color = new ColorConvert();
            color.hexaColor = hexValue;
            color.numberColor = int.Parse("FF" + hexValue.Replace("#", ""), System.Globalization.NumberStyles.HexNumber);

            return color;
        }

        /// <summary>
        /// Convert color to number
        /// </summary>
        /// <param name="value"></param>
        /// <param name="length"></param>
        /// <returns></returns>
        public ColorConvert GetHexaColor(int? intValue)
        {
            int numberColorDefault = -1;
            if (intValue != null) {
                intValue = numberColorDefault;
            }
            // Convert integer 182 as a hex in a string variable
            string hexValue = ((int)intValue).ToString("X");
            hexValue = "#" + hexValue.Substring(2, 6);
            // Convert the number back to the hex string
            ColorConvert color = new ColorConvert();
            color.hexaColor = hexValue;
            color.numberColor = (int)intValue;

            return color;
        }

        /// <summary>
        /// get creen active log for menu
        /// </summary>
        /// <param name="cd_kaisha"></param>
        /// <param name="id_user"></param>
        /// <returns></returns>
        public object GetLogCreen(int? cd_kaisha, string id_user)
        {
            LogActive log = new LogActive();
            return log.GetLog(cd_kaisha, id_user);
        }

        /// <summary>
        /// write active log
        /// </summary>
        /// <param name="param"></param>
        [HttpPost]
        public void PostWriteLog([FromBody] paramsLog param)
        {
            UserInfo userInfo = Tos.Web.Data.UserInfo.CreateFromAuthorityMaster(this.User.Identity);
            param.userInfo = userInfo;
            LogActive log = new LogActive(param);
            log.WriteLog();
        }

        #endregion
    }



    //List kbn_hin
    public class ma_kbn_hin_fp
    {
        public string kbn_hin { get; set; }
        public string nm_kbn_hin { get; set; }
    }

    //List bunrui
    public class ma_bunrui_fp
    {
        public string cd_bunrui { get; set; }
        public string nm_bunrui { get; set; }
    }

    //List haigo
    public class haigoData {
        public string cd_haigo { get; set; }
        public string nm_haigo { get; set; }
        public string kbn_hin { get; set; }        
    }

    //List seihin
    public class seihinData
    {
        public string cd_hin { get; set; }
        public string nm_hin { get; set; }
    }

    //List genryo
    public class genryoData
    {
        public decimal cd_hin { get; set; }
        public string nm_hin { get; set; }
        public int kbn_hin { get; set; }
    }

    //Color convert
    public class ColorConvert {
        public string hexaColor { get; set; }
        public int numberColor { get; set; }
    }

    //
    public class LogActive
    {
        private string cd_game { get; set; }
        private string cd_taisho_data { get; set; }
        private string nm_mode { get; set; }
        private UserInfo userInfo { get; set; }

        public LogActive() {}

        public LogActive(paramsLog param)
        {
            this.cd_game = param.cd_game;
            this.cd_taisho_data = param.cd_taisho_data;
            this.nm_mode = param.nm_mode;
            this.userInfo = param.userInfo;
        }

        //get a list of screens of user data active in the past day
        public List<DataLog> GetLog(int? cd_kaisha, string id_user)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                List<DataLog> results = new List<DataLog>();

                DateTime yesterday = DateTime.Now.AddDays((double)-1).Date;

                results = context.vw_shohinkaihatsu_log_operation
                    .Where(x => x.cd_tanto_kaisha == cd_kaisha && x.cd_tanto == id_user && x.d_operation == yesterday)
                    .GroupBy(x => x.cd_gamen)
                    .Select(x => new DataLog
                    {
                        no_sort = x.FirstOrDefault().no_sort,
                        nm_literal = x.FirstOrDefault().nm_literal,
                        count = x.Count()
                    })
                    .OrderBy(x => x.no_sort)
                    .ToList();

                return results;
            }
        }

        //write data log active
        public void WriteLog()
        {
            ChangeSet<tr_log_operation> changeSet = new ChangeSet<tr_log_operation>();
            tr_log_operation log = new tr_log_operation();

            log.cd_gamen = cd_game;
            log.cd_taisho_data = cd_taisho_data;
            log.nm_mode = nm_mode;

            log.cd_tanto_kaisha = Int32.Parse(userInfo.cd_kaisha);
            log.cd_tanto = userInfo.EmployeeCD.ToString();
            log.nm_mascot = GetHostNameClientComputer();
            log.dt_operation = DateTime.Now;

            changeSet.Created.Add(log);

            SaveLog(changeSet);
        }

        //save tr_operation_log
        private void SaveLog(ChangeSet<tr_log_operation> changeSet)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                changeSet.AttachTo(context);
                context.SaveChanges();
            }
        }

        //get host name
        private string GetHostNameClientComputer()
        {
            //try
            //{
            //    IPHostEntry entry = Dns.GetHostEntry(HttpContext.Current.Request.ServerVariables["REMOTE_ADDR"]);
            //    string hostName = entry.HostName.ToString();
            //    //if (hostName != null && hostName.Split('.').Count() > 1)
            //    //{
            //    //    hostName = hostName.Split('.')[0];
            //    //}
            //    return hostName;
            //}
            //catch(Exception ex)
            //{
                CommonController common = new CommonController();
                return common.GetIPClientAddress();
            //}
        }

        public class DataLog
        {
            public short? no_sort { get; set; }
            public string nm_literal { get; set; }
            public int count { get; set; }
        }
    }

    //param log from client
    public class paramsLog
    {
        public string cd_game { get; set; }
        public string cd_taisho_data { get; set; }
        public string nm_mode { get; set; }
        public UserInfo userInfo { get; set; }
    }
}
