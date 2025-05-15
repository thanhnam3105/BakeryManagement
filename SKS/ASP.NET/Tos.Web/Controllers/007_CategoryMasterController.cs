using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using Tos.Web.Controllers.Helpers;
using Tos.Web.Data;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    public class CategoryMasterController : ApiController
    {
        public HttpResponseMessage Get([FromUri] param param)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;
                ma_literal result = context.ma_literal.Where(x => x.cd_literal == param.cd_literal && x.cd_category == param.cd_category).FirstOrDefault();
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }
        }

        public HttpResponseMessage GetLiteralCombo([FromUri] GetLiteral input)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                List<ma_literal> result = new List<ma_literal>();
                context.Configuration.ProxyCreationEnabled = false;
                if (input.mode == 1) { 
                    result = context.ma_literal.Where(x => x.cd_category == input.cd_category && (x.cd_group == input.temp || x.cd_group == null)).OrderBy(x => x.no_sort).ToList();
                }
                else if (input.mode == 2){
                    result = (from literal in context.ma_literal
                              join groups in context.ma_group on literal.cd_group equals groups.cd_group into leftRights
                              from leftRight in leftRights.DefaultIfEmpty()
                              where (leftRight.cd_kaisha == input.temp || literal.cd_group == null) && literal.cd_category == input.cd_category
                              orderby literal.no_sort
                              select literal).ToList();

                }
                else if (input.mode == 9) {
                    result = context.ma_literal.Where(x => x.cd_category == input.cd_category).OrderBy(x => x.no_sort).ToList();
                }
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }
        }

        public class GetLiteral
        {
            public int? temp { get; set; }
            public string cd_category { get; set; }
            public int mode { get; set; }
        }

        public class param
        {
            public string cd_literal { get; set; }
            public string cd_category { get; set; }
        }
        public class ShohinKaihatsuChangeResponse
        {
            public ShohinKaihatsuChangeResponse()
            {
                this.Detail = new List<ma_literal>();
            }

            public List<ma_literal> Detail { get; set; }
        }
        /// <summary>
        /// target 情報の主キーによる重複チェックを行います。
        /// </summary>
        /// <param name="value">target 情報の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<ma_literal> IsAlreadyExists(ChangeSet<ma_literal> value)
        {
            InvalidationSet<ma_literal> result = new InvalidationSet<ma_literal>();

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                foreach (var item in value.Created)
                {
                    ma_literal ma_literal = new ma_literal();
                    ma_saiban ma_saiban = context.ma_saiban.Where(x => x.key1 == "リテラルコード"
                                                                    && x.key2 == item.cd_category).FirstOrDefault();
                    if (ma_saiban == null)
                    {
                        ma_literal.cd_literal = "001";
                    }
                    else
                    {
                        var temp = "00" + (ma_saiban.no_seq + 1).ToString();
                        ma_literal.cd_literal = temp.Substring(temp.Length - 3, 3);
                    }

                    var isDatabaseExists = (context.ma_literal.Where(x => x.cd_literal == ma_literal.cd_literal && x.cd_category == item.cd_category).FirstOrDefault() != null);

                    if (isDatabaseExists)
                    {
                        result.Add(new Invalidation<ma_literal>(Properties.Resources.ValidationKey, ma_literal, "cd_literal"));
                    }
                }
            }

            return result;
        }
        /// <summary>
        /// SaveShisakuList
        /// </summary>
        /// <param name="context"></param>
        /// <param name="value"></param>
        private void SaveLiteral(ShohinKaihatsuEntities context, ChangeSet<ma_literal> value)
        {

            value.SetDataSaveInfo(this.User.Identity);
            value.AttachTo(context);
            context.SaveChanges();
            //// TODO: 上記実装を行った後に下の行は削除します
            //var result = new ShohinKaihatsuChangeResponse();
            //return result;
        }
        /// <summary>
        /// SaveShisakuList
        /// </summary>
        /// <param name="context"></param>
        /// <param name="value"></param>
        private void SaveSaiBan(ShohinKaihatsuEntities context, ChangeSet<ma_saiban> value)
        {

            value.SetDataSaveInfo(this.User.Identity);
            value.AttachTo(context);
            context.SaveChanges();
            //// TODO: 上記実装を行った後に下の行は削除します
            //var result = new ShohinKaihatsuChangeResponse();
            //return result;
        }
        public class ShikakuDataChangeSet
        {
            //public ChangeSet<ma_saiban> ma_saiban { get; set; }
            public ChangeSet<ma_literal> ma_literal { get; set; }
        }
        /// <summary>
        /// 明細一覧情報の整合性チェックを行います。
        ///  TODO: エンティティに対する整合性チェック (マスタ存在チェックなど) を行います。
        /// </summary>
        /// <param name="changeSet">明細一覧の変更セット</param>
        /// <returns></returns>
        private InvalidationSet<ma_literal> ValidateDetail(ChangeSet<ma_literal> changeSet)
        {
            InvalidationSet<ma_literal> invalidations = new InvalidationSet<ma_literal>();
            // TODO: 明細のサーバー入力検証
            foreach (var item in changeSet.Created)
            {
            }
            foreach (var item in changeSet.Updated)
            {
            }
            return invalidations;
        }
        public HttpResponseMessage Post([FromBody]ShikakuDataChangeSet value)
        {
            if (value == null)
            {
                return Request.CreateErrorResponse(HttpStatusCode.BadRequest, Properties.Resources.NotNullAllow);
            }

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                IObjectContextAdapter adapter = context as IObjectContextAdapter;
                DbConnection connection = adapter.ObjectContext.Connection;
                connection.Open();
                using (DbTransaction transaction = connection.BeginTransaction())
                {   
                    // TODO: detail部の保存処理を実行します。
                    try
                    {

                        if (value.ma_literal.Created.Count > 0)
                        {
                            decimal no_seq_default = 1;
                            var cd_literal_default = "001";
                            string cd_category = value.ma_literal.Created[0].cd_category;

                            InvalidationSet<ma_literal> exist_literal = IsAlreadyExists(value.ma_literal);
                            if (exist_literal.Count > 0)
                            {
                                return Request.CreateResponse<InvalidationSet<ma_literal>>(HttpStatusCode.BadRequest, exist_literal);
                            }

                            ChangeSet<ma_saiban> Saiban = new ChangeSet<ma_saiban>();
                            ChangeSet<ma_literal> Literal = new ChangeSet<ma_literal>();
                            var itemSaiban = context.ma_saiban.Where(x => x.key1 == "リテラルコード" && x.key2 == cd_category).FirstOrDefault();
                            value.ma_literal.Created[0].cd_2nd_literal = "";
                            
                            if (itemSaiban != null)
                            {
                                //s_no_seq = itemSaiban.no_seq + 1;
                                //itemSaiban.no_seq = s_no_seq;
                                itemSaiban.no_seq += 1;

                                //if (s_no_seq > 999)
                                //{
                                //    itemSaiban.no_seq = 999;
                                //    s_no_seq = 1;
                                //}
                                Saiban.Updated.Add(itemSaiban);
                                var temp = "00" + itemSaiban.no_seq.ToString() ;
                                value.ma_literal.Created[0].cd_literal = temp.Substring(temp.Length - 3, 3); 
                            }
                            else {
                                var createSaiban = new ma_saiban
                                {
                                    key1 = "リテラルコード",
                                    key2 = cd_category,
                                    no_seq = no_seq_default
                                };
                                Saiban.Created.Add(createSaiban);
                                value.ma_literal.Created[0].cd_literal = cd_literal_default;
                            }
                            SaveSaiBan(context, Saiban);

                        }
                       
                        SaveLiteral(context, value.ma_literal);
                        transaction.Commit();
                    }
                    catch (Exception ex)
                    {
                        // 例外をエラーログに出力します。
                        transaction.Rollback();
                        Logger.App.Error(ex.Message, ex);
                        if (ex is DbUpdateConcurrencyException || ex.InnerException is DbUpdateConcurrencyException)
                        {
                            return Request.CreateErrorResponse(HttpStatusCode.Conflict, ex);
                        }
                        return Request.CreateResponse(HttpStatusCode.BadRequest, ex);
                    }
                }

            }
            var result = new ShikakuDataChangeSet();
            return Request.CreateResponse<ShikakuDataChangeSet>(HttpStatusCode.OK, result);
            // TODO: 返却用のオブジェクトを生成します。
            //var result = new ShohinKaihatsuChangeResponse();
            //result.Detail.AddRange(value.Flatten());
            //result.Detail.AddRange(value.Detail.Flatten());

        }
    }

}