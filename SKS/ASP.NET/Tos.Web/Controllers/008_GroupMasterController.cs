using System;
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
using System.Data;


namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class GroupMasterController : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡された OData クエリに該当するすべてのheader情報を取得します。
        /// </summary>
        /// <param name="options"> OData クエリ</param>
        /// <returns>header情報</returns>
        public List<GroupSearchResponse> Get(short? cd_group, int mode)
        {
            // TODO:header情報を管理しているDbContextとheader,detailの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                List<GroupSearchResponse> result = new List<GroupSearchResponse>();

                result = (from x in context.ma_group
                          join y in context.ma_kaisha
                          on x.cd_kaisha equals y.cd_kaisha into ps
                          from p in ps.DefaultIfEmpty()
                          where x.cd_group == cd_group
                          select new GroupSearchResponse()
                              {
                                  nm_group = x.nm_group,
                                  cd_kaisha = x.cd_kaisha,
                                  nm_kaisha = p.nm_kaisha,
                                  ts_group = x.ts
                              }
                              ).ToList();
                return result;
            }
        }

        /// <summary>
        /// パラメータで受け渡されたheader情報・detail情報をもとにエントリーheader情報・detail情報を一括更新（追加・更新・削除）します。
        /// </summary>
        /// <param name="value"></param>
        /// <returns></returns>
        public HttpResponseMessage Post([FromBody]GroupChangeSet value)
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
                    if (value.mode == 1) //Mode Group
                    {
                        //InvalidationSet<GroupChangeSet> ma_group_Invalidations = IsAlreadyExistsGroup(value);
                        //if (ma_group_Invalidations.Count > 0)
                        //{
                        //    return Request.CreateResponse<InvalidationSet<GroupChangeSet>>(HttpStatusCode.BadRequest, ma_group_Invalidations);
                        //}

                        InvalidationSet<GroupChangeSet> ma_group_Invalidations = Save_ma_group(value);
                        if (ma_group_Invalidations.Count > 0)
                        {
                            return Request.CreateResponse<InvalidationSet<GroupChangeSet>>(HttpStatusCode.BadRequest, ma_group_Invalidations);
                        }
                    }
                    else //Mode Team
                    {
                        //InvalidationSet<GroupChangeSet> ma_team_Invalidations = IsAlreadyExistsTeam(value);
                        //if (ma_team_Invalidations.Count > 0)
                        //{
                        //    return Request.CreateResponse<InvalidationSet<GroupChangeSet>>(HttpStatusCode.BadRequest, ma_team_Invalidations);
                        //}

                        InvalidationSet<GroupChangeSet> ma_team_Invalidations = Save_ma_team(value);
                        if (ma_team_Invalidations.Count > 0)
                        {
                            return Request.CreateResponse<InvalidationSet<GroupChangeSet>>(HttpStatusCode.BadRequest, ma_team_Invalidations);
                        }
                    }

                    //Save ma_saiban:
                    //Save_ma_saiban(value);

                    transaction.Commit();
                }
            }

            // TODO: 返却用のオブジェクトを生成します。
            var result = new GroupSearchResponse();

            //return Request.CreateResponse<GroupSearchResponse>(HttpStatusCode.OK, result);
            return Request.CreateResponse(HttpStatusCode.OK, "success");
        }
        
        /// <summary>
        /// Create new saiban when it not exist
        /// </summary>
        /// <param name="key1"></param>
        /// <param name="key2"></param>
        /// <returns></returns>
        private ma_saiban createSaiban(string key1, string key2)
        { 
            decimal user_id = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            DateTime dateNow = DateTime.Now;
            return new ma_saiban()
            {
                no_seq = 1,
                key1 = key1,
                key2 = key2,
                id_toroku = user_id,
                dt_toroku = dateNow,
                id_koshin = user_id,
                dt_koshin = dateNow
            };
        }

        /// <summary>
        /// Get next saiban, if not exist then create once
        /// </summary>
        /// <param name="key1"></param>
        /// <param name="key2"></param>
        /// <returns></returns>
        private short getSaiban(string key1, string key2)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                short result = 1;
                ma_saiban ma_saiban = context.ma_saiban.Where(x => x.key1 == key1
                                                                && x.key2 == key2).FirstOrDefault();
                if (ma_saiban == null)
                {
                    context.ma_saiban.Add(createSaiban(key1, key2));
                }
                else
                { 
                    ma_saiban.no_seq++;
                    result = (short)ma_saiban.no_seq;
                }
                context.SaveChanges();
                return result;
            }
        }

        //Save data ma_group in Post
        private InvalidationSet<GroupChangeSet> Save_ma_group(GroupChangeSet value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                InvalidationSet<GroupChangeSet> result = new InvalidationSet<GroupChangeSet>();
                ChangeSet<ma_group> dataSave = new ChangeSet<ma_group>();
                UserInfo shain = UserInfo.CreateFromAuthorityMaster(this.User.Identity);

                decimal user_id = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity));
                DateTime dateNow = DateTime.Now;

                if (value.cd_group == 0) //INSERT table ma_group
                {
                    ma_group ma_group = new ma_group();
                    short newGroupCD = getSaiban(Properties.Resources.group_code, "");
                    result = IsAlreadyExistsGroup(value, newGroupCD);
                    if (result.Count == 0)
                    {
                        ma_group.cd_group = newGroupCD;
                        ma_group.nm_group = value.nm_group;
                        ma_group.id_toroku = user_id;
                        ma_group.id_koshin = user_id;
                        ma_group.dt_toroku = dateNow;
                        ma_group.dt_koshin = dateNow;
                        ma_group.cd_kaisha = value.cd_kaisha;
                        dataSave.Created.Add(ma_group);
                    }
                }
                else //UPDATE table ma_group
                {
                    ma_group ma_group = context.ma_group.Where(x => x.cd_group == value.cd_group).FirstOrDefault();
                    if (ma_group != null)
                    {
                        ma_group.nm_group = value.nm_group;
                        ma_group.id_koshin = user_id;
                        ma_group.dt_koshin = dateNow;
                        ma_group.cd_kaisha = value.cd_kaisha;
                        ma_group.ts = value.ts_group;
                        dataSave.Updated.Add(ma_group);
                    }
                }

                dataSave.SetDataSaveInfo(this.User.Identity);
                dataSave.AttachTo(context);
                context.SaveChanges();
                return result;
            }
        }

        //Save data ma_team in Post 
        private InvalidationSet<GroupChangeSet> Save_ma_team(GroupChangeSet value)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                ChangeSet<ma_team> dataSave = new ChangeSet<ma_team>();
                UserInfo shain = UserInfo.CreateFromAuthorityMaster(this.User.Identity);
                InvalidationSet<GroupChangeSet> result = new InvalidationSet<GroupChangeSet>();

                decimal user_id = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity));
                DateTime dateNow = DateTime.Now;

                if (value.cd_team == 0) //INSERT table ma_team
                {
                    ma_team ma_team = new ma_team();
                    var key2 = value.cd_group.ToString();
                    short newTeamCD = getSaiban(Properties.Resources.team_code, key2);
                    result = IsAlreadyExistsTeam(value, newTeamCD);
                    if (result.Count == 0)
                    {
                        ma_team.cd_group = value.cd_group;
                        ma_team.cd_team = newTeamCD;
                        ma_team.nm_team = value.nm_team;
                        ma_team.id_toroku = user_id;
                        ma_team.id_koshin = user_id;
                        ma_team.dt_toroku = dateNow;
                        ma_team.dt_koshin = dateNow;
                        dataSave.Created.Add(ma_team);
                    }
                }

                else //UPDATE table ma_team
                {
                    ma_team ma_team = context.ma_team.Where(x => x.cd_group == value.cd_group && x.cd_team == value.cd_team).FirstOrDefault();
                    if (ma_team != null)
                    {
                        ma_team.nm_team = value.nm_team;
                        ma_team.id_koshin = user_id;
                        ma_team.dt_koshin = dateNow;
                        ma_team.ts = value.ts_team;
                        dataSave.Updated.Add(ma_team);
                    }
                }

                dataSave.SetDataSaveInfo(this.User.Identity);
                dataSave.AttachTo(context);
                context.SaveChanges();
                return result;
            }
        }

        #endregion

        #region "Controller内で利用する関数群"

        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<GroupChangeSet> IsAlreadyExistsTeam(GroupChangeSet value, short no_seq)
        {
            InvalidationSet<GroupChangeSet> result = new InvalidationSet<GroupChangeSet>();
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                if (value.cd_team == 0)
                {
                    ma_team ma_team_validationKey = context.ma_team.Where(x => x.cd_group == value.cd_group && x.cd_team == no_seq).FirstOrDefault();
                    
                    if (ma_team_validationKey != null)
                    {
                        value.cd_team = ma_team_validationKey.cd_team;
                        result.Add(new Invalidation<GroupChangeSet>(Properties.Resources.ValidationKey, value, "cd_team"));
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
        private InvalidationSet<GroupChangeSet> IsAlreadyExistsGroup(GroupChangeSet value, short no_seq)
        {
            InvalidationSet<GroupChangeSet> result = new InvalidationSet<GroupChangeSet>();
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                if (value.cd_group == 0)
                {
                    ma_group ma_group_validationKey = context.ma_group.Where(x => x.cd_group == no_seq).FirstOrDefault();
                    
                    if (ma_group_validationKey != null)
                    {
                        value.cd_group = ma_group_validationKey.cd_group;
                        result.Add(new Invalidation<GroupChangeSet>(Properties.Resources.ValidationKey, value, "cd_group"));
                    }
                }
            }
            return result;
        }

        /// <summary>
        /// パラメータで受け渡されたheader情報をもとにheader情報を論理削除します。
        /// </summary>
        /// <param name="value">header情報</param>
        /// <returns>header情報</returns>
        //[HttpPost]
        //public HeaderGroupMChangeResponse Remove([FromBody]ChangeSet<object/*TODO:headerの型を指定します*/> value)
        //{
        //    // TODO:header情報を管理しているDbContextとheaderの型を指定します。

        //    // using (/*TODO:header情報を管理しているDbContextを指定します*/ context = new /*TODO:header情報を管理しているDbContextを指定します*/())
        //    // {
        //    //     // TODO: header部の保存処理を実行します。
        //    //     SaveHeader(context, value);
        //    // }

        //    var result = new HeaderGroupMChangeResponse();
        //    result.Header = value.Flatten().SingleOrDefault();

        //    return result;
        //}


        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    public class GroupRequest
    {
        public ChangeSet<object/*TODO:headerの型を指定します*/> Header { get; set; }

        public ChangeSet<object/*TODO:detailの型を指定します*/> Detail { get; set; }
    }

    public class GroupSearchResponse
    {
        public string nm_group { get; set; }
        //public short cd_team { get; set; }
        //public string nm_team { get; set; }
        public int? cd_kaisha { get; set; }
        public string nm_kaisha { get; set; }

        public byte[] ts_group { get; set; }
        public byte[] ts_team { get; set; }
    }

    public class GroupChangeSet
    {
        public int mode { get; set; }

        public short cd_group { get; set; }
        public short cd_kaisha { get; set; }
        public short cd_team { get; set; }

        public string nm_group { get; set; }
        public string nm_team { get; set; }

        public byte[] ts_group { get; set; }
        public byte[] ts_team { get; set; }
    }

    #endregion
}
