using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Principal;
using System.Web;

namespace Tos.Web.Data
{
    /// <summary>
    /// ユーザー情報を定義します。
    /// </summary>
    public class UserInfo
    {
        /// <summary>
        /// ユーザー情報を定義するクラスのインスタンスを初期化します。
        /// </summary>
        public UserInfo()
        {
            this.EmployeeCD = 0;
            this.Organization = string.Empty;
            this.Branch = string.Empty;
            this.Name = string.Empty;
            this.cd_kengen = new short();
            this.kbn_kengen_bunrui = new short();
            this.Roles = new List<RoleInfo>();
            this.su_code_standard = 0;
            //this.id_gamen = new List<string>();
            //this.id_kino = new List<string>();
            //this.id_data = new List<string>();
            //this.cd_tantokaisha = new List<string>();
            //this.nm_tantokaisha = new List<string>();
            this.movement_condition = new List<string>();
            this.list_kengen = new List<vw_user_gamen_info>();
        }

        /// <summary>
        /// 現在ログインしているユーザーの社員番号
        /// </summary>
        public decimal EmployeeCD { get; set; }

        /// <summary>
        /// 現在ログインしているユーザーの組織
        /// </summary>
        public string Organization { get; set; }

        /// <summary>
        /// 現在ログインしているユーザーの所属
        /// </summary>
        public string Branch { get; set; }

        /// <summary>
        /// 現在ログインしているユーザー名
        /// </summary>
        public string Name { get; set; }

        /// <summary>
        /// 現在ログインしているユーザー権限
        /// </summary>
        public short cd_kengen { get; set; }

        /// <summary>
        /// 現在ログインしている権限分類
        /// </summary>
        public Nullable<short> kbn_kengen_bunrui { get; set; }

        /// <summary>
        /// 現在ログインしているユーザーが付与されているロール（権限）
        /// </summary>
        public List<RoleInfo> Roles { get; set; }

        /// <summary>
        /// 会社CD
        /// </summary>
        public string cd_kaisha { get; set; }

        /// <summary>
        /// 会社名称
        /// </summary>
        public string nm_kaisha { get; set; }

        /// <summary>
        /// 部署CD
        /// </summary>
        public string cd_busho { get; set; }

        /// <summary>
        /// 部署名称
        /// </summary>
        public string nm_busho { get; set; }

        /// <summary>
        /// グループCD
        /// </summary>
        public string cd_group { get; set; }

        /// <summary>
        /// グループ名
        /// </summary>
        public string nm_group { get; set; }

        /// <summary>
        /// チームCD
        /// </summary>
        public string cd_team { get; set; }

        /// <summary>
        /// チーム名
        /// </summary>
        public string nm_team { get; set; }

        /// <summary>
        /// リテラルCD
        /// </summary>
        public string cd_literal { get; set; }

        /// <summary>
        /// リテラル名
        /// </summary>
        public string nm_literal { get; set; }

        /// <summary>
        /// 
        /// </summary>
        public bool flg_kaishakan_sansyo { get; set; }

        /// <summary>
        /// 
        /// </summary>
        public bool flg_kojyokan_sansyo { get; set; }

        /// <summary>
        /// 
        /// </summary>
        public int?  su_code_standard { get; set; }

        /// <summary>
        /// 
        /// </summary>
        public int su_linecode_standard { get; set; }

        /// <summary>
        /// 
        /// </summary>
        public List<vw_user_gamen_info> list_kengen { get; set; }
        ///// <summary>
        ///// 画面ID
        ///// </summary>
        //public List<string> id_gamen { get; set; }

        ///// <summary>
        ///// 機能ID
        ///// </summary>
        //public List<string> id_kino { get; set; }

        ///// <summary>
        ///// データID
        ///// </summary>
        //public List<string> id_data { get; set; }

        ///// <summary>
        ///// 製造担当会社コード
        ///// </summary>
        //public List<string> cd_tantokaisha { get; set; }

        ///// <summary>
        ///// 製造担当会社名
        ///// </summary>
        //public List<string> nm_tantokaisha { get; set; }

        /// <summary>
        /// JSP起動条件
        /// </summary>
        public List<string> movement_condition { get; set; }
     
        /// <summary>
        /// ユーザー情報を定義するクラスのインスタンスを初期化します。
        /// </summary>
        public static UserInfo CreateFromAuthorityMaster(IIdentity identity)
        {
            UserInfo result = new UserInfo();

            // 統合ID権限テーブルから認可情報を取得します。
            //using (AuthorityMasterEntities context = new AuthorityMasterEntities())
            //{

            //    decimal employeeCD = 0;
            //    Decimal.TryParse(UserInfo.GetUserNameFromIdentity(identity), out employeeCD);
            //    result.EmployeeCD = employeeCD;

            //    var roleInfos = (from r in context.vw_shain_info
            //                     where r.cd_shain == employeeCD
            //                     select r).ToList();

            //    if (roleInfos.Count == 0)
            //    {
            //        return null;
            //    }

            //    foreach (var roleInfo in roleInfos)
            //    {
            //        result.EmployeeCD = roleInfo.cd_shain;
            //        result.Name = roleInfo.nm_shain;
            //        result.Branch = roleInfo.nm_shozoku;
            //        result.Organization = roleInfo.nm_kaisha;

            //        result.Roles.Add(new RoleInfo
            //        {
            //            AuthorityCode = roleInfo.cd_kengen,
            //            ContentCode = roleInfo.cd_kengen
            //        });
            //    }
            //}
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                decimal employeeCD = 0;
                Decimal.TryParse(UserInfo.GetUserNameFromIdentity(identity), out employeeCD);
                result.EmployeeCD = employeeCD;

                string cd_kaisha = "";
                var request = HttpContext.Current.Request;
                cd_kaisha = request.Cookies["cd_kaisha"].Value;

                int kaishaCode = 0;
                int.TryParse(cd_kaisha, out kaishaCode);

                var shainInfos = (from r in context.vw_user_info
                                  where r.id_user == employeeCD
                                  && r.cd_kaisha == kaishaCode
                                  select r).ToList();
                if (shainInfos.Count == 0)
                {
                    return null;
                }
                foreach (var info in shainInfos)
                {
                    result.EmployeeCD = info.id_user;
                    result.Name = info.nm_user;
                    //result.Branch = roleInfo.cd_group;
                    result.cd_kengen = info.cd_kengen;
                    result.kbn_kengen_bunrui = info.kbn_kengen_bunrui;
                    result.Organization = info.nm_kaisha;
                    result.cd_kaisha = info.cd_kaisha.ToString();
                    result.nm_kaisha = info.nm_kaisha;

                    if (info.cd_busho == null)
                    {
                        result.cd_busho = null;
                    }
                    else
                    {
                        result.cd_busho = info.cd_busho.ToString();
                    }
                    result.nm_busho = info.nm_busho;
                    result.cd_group = info.cd_group.ToString();
                    result.nm_group = info.nm_group;
                    result.cd_team = info.cd_team.ToString();
                    result.nm_team = info.nm_team;
                    result.cd_literal = info.cd_literal.ToString();
                    result.nm_literal = info.nm_literal;
                    result.flg_kaishakan_sansyo = info.flg_kaishakan_sansyo;
                    result.flg_kojyokan_sansyo = info.flg_kojyokan_sansyo;
                    result.su_linecode_standard = info.su_linecode_standard;
                    result.su_code_standard = info.su_code_standard;

                    result.Roles.Add(new RoleInfo
                    {
                        AuthorityCode = info.cd_kengen,
                        ContentCode = info.cd_kengen
                    });
                }

                result.list_kengen = KengenGamen(null, employeeCD, kaishaCode);

                //foreach (var info in gamenInfos)
                //{
                //    result.id_gamen.Add(info.id_gamen.ToString());
                //    result.id_kino.Add(info.id_kino.ToString());
                //    result.id_data.Add(info.id_data.ToString());
                //}
            }
            return result;
        }

        /// <summary>
        ///ユーザーIDと画面IDから権限を取得する。
        /// </summary>
        /// <param name="id_gamen"></param>
        /// <param name="id_user"></param>
        /// <returns>list vw_user_gamen_info</returns>
        public static List<vw_user_gamen_info> KengenGamen(Nullable<int> id_gamen, decimal id_user, decimal cd_kaisha)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                return context.vw_user_gamen_info.Where(x => x.id_user == id_user && x.cd_kaisha == cd_kaisha && (id_gamen == null || x.id_gamen == id_gamen)).ToList();
            }
        }

        /// <summary>
        ///Get Company List
        /// </summary>
        /// <param name="cd_kaisha"></param>
        /// <returns>list Company</returns>
        public static List<ma_kaisha> GetCompany(int? cd_kaisha)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                return context.ma_kaisha.Where(x => (x.cd_kaisha == cd_kaisha || cd_kaisha == null)).ToList();
            }
        }

        /// <summary>
        ///Get User List
        /// </summary>
        /// <param name="id_user"></param>
        /// <returns>list User</returns>
        public static List<ma_user_togo> GetUser(decimal? id_user, int? cd_kaisha)
        {
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                return context.ma_user_togo.Where(x => x.id_user == id_user && x.cd_kaisha == cd_kaisha).ToList();
            }
        }

        /// <summary>
        /// ユーザー名を取得します
        /// </summary>
        /// <param name="identity">実行ユーザー情報</param>
        /// <returns>ユーザー名</returns>
        public static string GetUserNameFromIdentity(IIdentity identity)
        {
            string name = identity.Name;
            int separator = name.IndexOf("\\");
            return (separator > -1) ? name.Substring(separator + 1, name.Length - separator - 1) : name;
        }
    }

    public class RoleInfo
    {
        public RoleInfo()
        {
            this.AuthorityCode = 0;
            this.ContentCode = 0;
        }

        /// <summary>
        /// 権限コード
        /// </summary>
        public int AuthorityCode { get; set; }

        /// <summary>
        /// 内容コード
        /// </summary>
        public int ContentCode { get; set; }

    }
}