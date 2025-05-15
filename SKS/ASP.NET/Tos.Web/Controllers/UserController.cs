/** 最終更新日 : 2016-10-17 **/
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Tos.Web.Data;
using System.Security.Principal;
using System.Web;

namespace Tos.Web.Controllers
{
    [Authorize]
    public class UserController : ApiController
    {
        /// <summary>
        /// 現在ログインしているユーザーの情報を取得します。
        /// GET api/<controller>
        /// </summary>
        public UserInfo Get()
        {
            UserInfo result = UserInfo.CreateFromAuthorityMaster(this.User.Identity);

            if (result == null)
            {
                throw new HttpException((int)HttpStatusCode.NotFound, Properties.Resources.NoUserExists);
            }

            return result;
        }

        /// <summary>
        ///ユーザーIDと画面IDから権限を取得する。
        /// </summary>
        /// <param name="id_gamen"></param>
        /// <param name="id_user"></param>
        /// <returns></returns>
        public object GetKengenGamen(int id_gamen, decimal id_user, decimal cd_kaisha)
        {

            object result = UserInfo.KengenGamen(id_gamen, id_user, cd_kaisha);

            if (result == null)
            {
                throw new HttpException((int)HttpStatusCode.BadRequest, Properties.Resources.NoUserExists);
            }

            return result;
        }

        /// <summary>
        ///Get company。
        /// </summary>
        /// <param name="id_gamen"></param>
        /// <param name="id_user"></param>
        /// <returns></returns>
        [HttpGet]
        public object GetCompany(int? cd_kaisha)
        {

            object result = UserInfo.GetCompany(cd_kaisha);

            if (result == null)
            {
                throw new HttpException((int)HttpStatusCode.BadRequest, Properties.Resources.NoUserExists);
            }

            return result;
        }

        /// <summary>
        ///Get company。
        /// </summary>
        /// <param name="id_gamen"></param>
        /// <param name="id_user"></param>
        /// <returns></returns>
        [HttpGet]
        public object GetUser(decimal? id_user, int? cd_kaisha)
        {

            object result = UserInfo.GetUser(id_user, cd_kaisha);

            if (result == null)
            {
                throw new HttpException((int)HttpStatusCode.BadRequest, Properties.Resources.NoUserExists);
            }

            return result;
        }
    }
}
