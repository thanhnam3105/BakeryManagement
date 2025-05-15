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
using System.Web.Http.OData.Query;
using Tos.Web.Controllers.Helpers;
using Tos.Web.Data;
using Tos.Web.Logging;

namespace Tos.Web.Controllers
{
    //created from 【CsvUploadDownloadController(Ver2.0)】 Template
    public class CategoryMaster_CSVDownloadController : ApiController
    {
        #region "CSVファイルの項目設定"

        private static readonly TextFieldSetting[] CsvFileSettings = new TextFieldSetting[]
        {
            
            new TextFieldSetting() { PropertyName = "cd_category", DisplayName = "カテゴリコード"
            },
             new TextFieldSetting() { PropertyName = "nm_category", DisplayName = "カテゴリ名"
            },
            new TextFieldSetting() { PropertyName = "cd_literal", DisplayName = "リテラルコード"
            },
             new TextFieldSetting() { PropertyName = "nm_literal", DisplayName = "リテラル名"
            },
               new TextFieldSetting() { PropertyName = "value1", DisplayName = "リテラル値1"
            },
               new TextFieldSetting() { PropertyName = "value2", DisplayName = "リテラル値2"
            },
             new TextFieldSetting() { PropertyName = "no_sort", DisplayName = "表示順"
            },
             new TextFieldSetting() { PropertyName = "biko", DisplayName = "備考"
            },
             new TextFieldSetting() { PropertyName = "flg_edit", DisplayName = "編集Flg"
            },
             new TextFieldSetting() { PropertyName = "cd_group", DisplayName = "グループコード"
            },
             new TextFieldSetting() { PropertyName = "nm_group", DisplayName = "グループ名"
            }
        };

        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// 検索条件に一致するデータを抽出しCSVを作成します。（Downloadフォルダに実体ファイル作成後、パスを返却）
        /// </summary>
        /// <param name="options">検索条件</param>
        /// <returns>作成されたCSVファイルのパス</returns>
        public HttpResponseMessage Get([FromUri]string cd_category)
        {
            // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
            string userName = UserInfo.GetUserNameFromIdentity(this.User.Identity);
            
            //string filename = "カテゴリマスタ" + userName + "CsvDownload_";
            string csvname = "カテゴリマスタ_" + Convert.ToDecimal(userName) +"_" + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;
            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                //テーブル（ビュー）の項目のみでダウンロードする場合は以下を使用します
                //    IQueryable results = options.ApplyTo(context./*TODO:【1】CsvDownloadTableの型を指定します*/.AsQueryable());

                //テーブル（ビュー）の項目＋更新区分を追加してダウンロードする場合は以下を使用します
                List<paramCSVdown> results = (from ml in context.ma_literal
                                              //join mc in context.ma_category on ml.cd_category equals mc.cd_category
                                              //join mg in context.ma_group on ml.cd_group equals mg.cd_group
                                              join mg in context.ma_group on ml.cd_group equals mg.cd_group into gl
                                              from temp in gl.DefaultIfEmpty()
                                              join mc in context.ma_category on ml.cd_category equals mc.cd_category
                                              where mc.cd_category == cd_category
                                              select new paramCSVdown
                                              {
                                                  cd_category = mc.cd_category,
                                                  nm_category = mc.nm_category,
                                                  cd_literal = ml.cd_literal,
                                                  nm_literal = ml.nm_literal,
                                                  value1 = ml.value1,
                                                  value2 = ml.value2,
                                                  flg_edit = ml.flg_edit,
                                                  no_sort = ml.no_sort,
                                                  biko = ml.biko,
                                                  cd_group = ml.cd_group,
                                                  nm_group = temp.nm_group
                                              }).OrderBy(x => x.cd_literal).ToList();
                MemoryStream stream = new MemoryStream();
                TextFieldFile<paramCSVdown> tFile
                                = new TextFieldFile<paramCSVdown>(stream, Encoding.GetEncoding(Properties.Resources.Encoding), CsvFileSettings);
                tFile.Delimiters = new string[] { "," };
                //// TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
                tFile.IsFirstRowHeader = true;
                tFile.WriteFields(results as IEnumerable<paramCSVdown>);
                return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
            }
        }
        public class paramCSVdown
        {
            public string cd_category { get; set; }
            public string nm_category { get; set; }
            public string cd_literal { get; set; }
            public string nm_literal { get; set; }
            public string biko { get; set; }
            public Nullable<int> value1 { get; set; }
            public Nullable<int> value2 { get; set; }
            public short flg_edit { get; set; }
            public short no_sort { get; set; }
            public Nullable<short> cd_group { get; set; }
            public string nm_group { get; set; }
        }

        #endregion

    }
}
