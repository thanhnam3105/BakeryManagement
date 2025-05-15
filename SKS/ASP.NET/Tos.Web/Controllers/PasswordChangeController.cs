/** 最終更新日 : 2016-10-17 **/
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using Tos.Web.Account;
using Tos.Web.Data;

namespace Tos.Web.Controllers
{
    [Authorize]
    public class PasswordChangeController : ApiController
    {
        /// <summary>
        /// Change password
        /// </summary>
        /// <param name="value"></param>
        public void Post([FromBody]PasswordChangeRequest value)
        {
            // パスワード変更に処理を呼び出します。
            MixedAuthentication.PasswordChangeFormAuthenticate(value.userid, value.cd_kaisha, value.password, value.newpassword);
        }

        /// <summary>
        /// Check valid password
        /// </summary>
        /// <param name="cd_user"></param>
        /// <param name="password"></param>
        /// <returns></returns>
        [HttpGet]
        public bool checkpassword(decimal cd_user, string password)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities()) 
            {
                if (context.ma_user_togo.Where(x => x.id_user == cd_user && x.password == password).Count() > 0) 
                {
                    return true;   
                }
                return false;
            }
        }

        /// <summary>
        /// Get user info without login
        /// </summary>
        /// <param name="Conditions"></param>
        /// <returns></returns>
        [HttpGet]
        public GetUserInfoResponse GetUserInfo([FromUri] GetUserInfoRequest Conditions)
        {
            GetUserInfoResponse result = new GetUserInfoResponse();
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities()) 
            {
                vw_user_info user = context.vw_user_info.Where(x => x.id_user == Conditions.user_id && x.cd_kaisha == Conditions.cd_kaisha).FirstOrDefault();
                if (user != null)
                {
                    result.EmployeeCD = Conditions.user_id;
                    result.Name = user.nm_user;
                    result.cd_kaisha = Conditions.cd_kaisha;
                    result.nm_kaisha = user.nm_kaisha;
                }
                else
                {
                    return null;
                }
            }
            return result;
        }
    }

    /// <summary>
    /// Password change request param
    /// </summary>
    public class PasswordChangeRequest
    {
        public string userid { get; set; }
        public int cd_kaisha { get; set; }
        public string password { get; set; }
        public string newpassword { get; set; }
    }

    /// <summary>
    /// Get user info request param
    /// </summary>
    public class GetUserInfoRequest
    {
        public decimal user_id { get; set; }
        public int cd_kaisha { get; set; }
    }

    /// <summary>
    /// Get user info response param
    /// </summary>
    public class GetUserInfoResponse
    {
        public decimal EmployeeCD { get; set; }
        public string Name { get; set; }
        public int cd_kaisha { get; set; }
        public string nm_kaisha { get; set; }
    }

}
