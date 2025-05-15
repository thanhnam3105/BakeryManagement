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
using System.Data.SqlClient;
using Tos.Web.Data;
using Tos.Web.DataFP;
using System.Data.SqlClient;
using Tos.Web.Logging;
using System.Data;

namespace Tos.Web.Controllers
{
    //created from 【CsvUploadDownloadController(Ver2.0)】 Template
    public class _208_HaigoIchiranDownloadCSVController : ApiController
    {
        #region "CSVファイルの項目設定"
        //Tab search
        //配合情報で検索
        private readonly short HaigoTab = 1;
        //展開情報で検索
        private readonly short TenkaiTab = 2;
        //原料情報で検索
        private readonly short GenryoTab = 3;
        public readonly string FlgFalse = "0";
        public readonly string FlgTrue = "1";
        public readonly string NoHanFirst = "1";
        public readonly string KubunShikakari = "3";
        public readonly string NameShikakari = "配";
        public readonly string KubunHaigo = "4";
        public readonly string NameHaigo = "仕";
        public readonly string NamekbnRoku = "自家原料";
        public readonly string KubunRoku = "6";
        public readonly string Kg = "Kg";
        public readonly string L = "L";
        public readonly string KbnVwShi =  "04";
        public readonly string KbnVwIchi = "11";
        private static readonly TextFieldSetting[] CSV208_Download = new TextFieldSetting[]
        {
            new TextFieldSetting() { PropertyName = "cd_haigo", DisplayName = "配合コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_haigo", DisplayName = "配合名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_haigo_r", DisplayName = "配合名略",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_hin", DisplayName = "品区分コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_kbn_hin", DisplayName = "品区分名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_bunrui", DisplayName = "分類コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_bunrui_bunrui", DisplayName = "分類名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "budomari", DisplayName = "歩留"},
            new TextFieldSetting() { PropertyName = "qty_kihon", DisplayName = "基本重量"},
            new TextFieldSetting() { PropertyName = "ritsu_kihon", DisplayName = "基本倍率"},
            new TextFieldSetting() { PropertyName = "cd_setsubi", DisplayName = "設備コード"},
            new TextFieldSetting() { PropertyName = "nm_setsubi_setsubi", DisplayName = "設備名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "flg_gasan", DisplayName = "仕込み合算フラグ"},
            new TextFieldSetting() { PropertyName = "qty_max", DisplayName = "仕込み最大重量"},
            new TextFieldSetting() { PropertyName = "nm_seiho", DisplayName = "製法名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "no_han", DisplayName = "版番号"},
            new TextFieldSetting() { PropertyName = "qty_haigo_h", DisplayName = "配合重量"},
            new TextFieldSetting() { PropertyName = "qty_haigo_kei", DisplayName = "配合合計重量"},
            new TextFieldSetting() { PropertyName = "biko", DisplayName = "備考",WrapChar="\"",},
            new TextFieldSetting() { PropertyName = "no_seiho", DisplayName = "製法番号",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "cd_tanto_seizo", DisplayName = "製造担当者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_tanto_seizo", DisplayName = "製造担当者名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "dt_seizo", DisplayName = "製造更新日付",Format="yyyy/MM/dd"},
            new TextFieldSetting() { PropertyName = "cd_tanto_hinkan", DisplayName = "品管担当者コード",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "nm_tanto_hinkan", DisplayName = "品管担当者名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "dt_hinkan", DisplayName = "品管更新日付",Format="yyyy/MM/dd"},
            new TextFieldSetting() { PropertyName = "dt_from", DisplayName = "有効日付（開始）",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "dt_to", DisplayName = "有効日付（終了）",Format="yyyy/MM/dd HH:mm:ss"},
            new TextFieldSetting() { PropertyName = "kbn_vw", DisplayName = "V/W区分（Kg/L）"},
            new TextFieldSetting() { PropertyName = "nm_kbn_vw", DisplayName = "V/W区分名"},
            new TextFieldSetting() { PropertyName = "hijyu", DisplayName = "比重"},
            new TextFieldSetting() { PropertyName = "flg_shorihin", DisplayName = "処理品フラグ"},
            new TextFieldSetting() { PropertyName = "flg_hinkan", DisplayName = "品管担当フラグ"},
            new TextFieldSetting() { PropertyName = "flg_seizo", DisplayName = "製造担当フラグ"},
            new TextFieldSetting() { PropertyName = "flg_sakujyo", DisplayName = "削除フラグ"},
            new TextFieldSetting() { PropertyName = "flg_mishiyo", DisplayName = "未使用フラグ"},
            new TextFieldSetting() { PropertyName = "dt_toroku", DisplayName = "登録日時"},
            new TextFieldSetting() { PropertyName = "dt_henko", DisplayName = "変更日時"},
            new TextFieldSetting() { PropertyName = "cd_koshin", DisplayName = "更新者コード"},
            new TextFieldSetting() { PropertyName = "nm_tanto_koshin", DisplayName = "更新者名",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "kbn_shiagari", DisplayName = "仕上区分"},
            new TextFieldSetting() { PropertyName = "cd_haigo_seiho", DisplayName = "配合コード（製法）",WrapChar="\""},
            new TextFieldSetting() { PropertyName = "flg_seiho_base", DisplayName = "製法原本フラグ"},
        };

        #endregion

        #region "Controllerで公開するAPI"

        /// <summary>
        /// Download CSV 208 HaigoIchiran
        /// </summary>
        /// <param name="options"></param>
        /// <returns>CSV file</returns>
        public HttpResponseMessage Get([FromUri] CSV_208_Model value)
        {
            // 保存CSVファイル名（ユーザID_CsvDownload_現在日時.csv）
            decimal _userName = Convert.ToDecimal(UserInfo.GetUserNameFromIdentity(this.User.Identity));
            string userName = "_" + _userName.ToString() + "_";
            string filename = Properties.Resources.CSV208_Download + userName;
            string csvname = filename + DateTime.Now.ToString("yyyyMMddHHmmss") + Properties.Resources.CsvExtension;

            IEnumerable results;
          
            using (FOODPROCSEntities context = new FOODPROCSEntities(value.cd_kaisha, value.cd_kojyo))
            {
                context.Configuration.ProxyCreationEnabled = false;

                var declareValue =  " DECLARE @dateSystem DATETIME = CONVERT(VARCHAR(10), GETDATE(), 111);";

                var tableTemp = " CREATE TABLE #haigomei( cd_haigo VARCHAR(13) );";
                var insertTableTemp = " INSERT INTO #haigomei ( cd_haigo )";
                insertTableTemp = insertTableTemp + " SELECT cd_haigo";
                insertTableTemp = insertTableTemp + " FROM ma_haigo_mei_hyoji haigo_hyoji";
                insertTableTemp = insertTableTemp + " WHERE haigo_hyoji.flg_seiho_base = @FlgTrue";
                insertTableTemp = insertTableTemp + " GROUP BY haigo_hyoji.cd_haigo;";

                var query = "";
                //hyoji mode
                if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji)
                {
                    if (value.kansakujyoho == HaigoTab)
                    {

                        query = query + " WITH CTE AS (";
                        query = query + " SELECT DISTINCT";
                        query = query + " haigo_hyoji.cd_haigo";
                        query = query + " , haigo_hyoji.nm_haigo";
                        query = query + " , haigo_hyoji.nm_haigo_r";
                        query = query + " , haigo_hyoji.kbn_hin";
                        query = query + " , CASE ";
                        query = query + " WHEN haigo_hyoji.kbn_hin = @KubunRoku THEN @NamekbnRoku";
                        query = query + " ELSE kbn_hin.nm_kbn_hin";
                        query = query + " END AS nm_kbn_hin";
                        query = query + " , haigo_hyoji.cd_bunrui";
                        query = query + " , bunrui.nm_bunrui AS nm_bunrui_bunrui";
                        query = query + " , haigo_hyoji.budomari";
                        query = query + " , haigo_hyoji.qty_kihon";
                        query = query + " , haigo_hyoji.ritsu_kihon";
                        query = query + " , haigo_hyoji.cd_setsubi";
                        query = query + " , setsubi.nm_setsubi AS nm_setsubi_setsubi";
                        query = query + " , haigo_hyoji.flg_gasan";
                        query = query + " , haigo_hyoji.qty_max";
                        query = query + " , haigo_hyoji.nm_seiho";
                        query = query + " , haigo_hyoji.no_han";
                        query = query + " , MAX(haigo_hyoji.no_han) OVER(PARTITION BY haigo_hyoji.cd_haigo) AS max_no_han";
                        query = query + " , haigo_hyoji.qty_haigo_h";
                        query = query + " , haigo_hyoji.qty_haigo_kei";
                        query = query + " , haigo_hyoji.biko";
                        query = query + " , haigo_hyoji.no_seiho";
                        query = query + " , haigo_hyoji.cd_tanto_seizo";
                        query = query + " , tanto_seizo.nm_tanto AS nm_tanto_seizo";
                        query = query + " , haigo_hyoji.dt_seizo";
                        query = query + " , haigo_hyoji.cd_tanto_hinkan";
                        query = query + " , tanto_hinkan.nm_tanto AS nm_tanto_hinkan";
                        query = query + " , haigo_hyoji.dt_hinkan";
                        query = query + " , haigo_hyoji.dt_from";
                        query = query + " , haigo_hyoji.dt_to";
                        query = query + " , haigo_hyoji.kbn_vw";

                        query = query + " , CASE";
                        query = query + " WHEN haigo_hyoji.kbn_vw = @KbnVwShi THEN @Kg ";
                        query = query + " WHEN haigo_hyoji.kbn_vw = @KbnVwIchi THEN @L ";
                        query = query + " ELSE '' ";
                        query = query + " END AS nm_kbn_vw";
                        query = query + " , haigo_hyoji.hijyu";
                        query = query + " , haigo_hyoji.flg_shorihin";
                        query = query + " , haigo_hyoji.flg_hinkan";
                        query = query + " , haigo_hyoji.flg_seizo";
                        query = query + " , haigo_hyoji.flg_sakujyo";
                        query = query + " , haigo_hyoji.flg_mishiyo";
                        query = query + " , haigo_hyoji.dt_toroku";
                        query = query + " , haigo_hyoji.dt_henko";
                        query = query + " , haigo_hyoji.cd_koshin";
                        query = query + " , tanto_koshin.nm_tanto AS nm_tanto_koshin";
                        query = query + " , haigo_hyoji.kbn_shiagari";
                        query = query + " , haigo_hyoji.cd_haigo_seiho";
                        query = query + " , haigo_hyoji.flg_seiho_base";

                        query = query + " FROM ma_haigo_mei_hyoji haigo_hyoji";

                        query = query + " LEFT JOIN ma_seihin seihin";
                        query = query + " ON haigo_hyoji.cd_haigo = seihin.cd_haigo_hyoji";
                        query = query + " AND seihin.flg_sakujyo = @FlgFalse";
                        query = query + " AND seihin.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_kbn_hin kbn_hin";
                        query = query + " ON haigo_hyoji.kbn_hin = kbn_hin.kbn_hin";

                        query = query + " LEFT JOIN ma_bunrui bunrui";
                        query = query + " ON haigo_hyoji.kbn_hin = bunrui.kbn_hin";
                        query = query + " AND haigo_hyoji.cd_bunrui = bunrui.cd_bunrui";
                        query = query + " AND bunrui.flg_sakujyo = @FlgFalse";
                        query = query + " AND bunrui.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_setsubi setsubi";
                        query = query + " ON haigo_hyoji.cd_setsubi = setsubi.cd_setsubi";
                        query = query + " AND setsubi.flg_sakujyo = @FlgFalse";
                        query = query + " AND setsubi.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_seizo";
                        query = query + " ON haigo_hyoji.cd_tanto_seizo = tanto_seizo.cd_tanto";
                        query = query + " AND tanto_seizo.flg_sakujyo = @FlgFalse";
                        query = query + " AND tanto_seizo.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_hinkan";
                        query = query + " ON haigo_hyoji.cd_tanto_hinkan = tanto_hinkan.cd_tanto";
                        query = query + " AND tanto_hinkan.flg_sakujyo = @FlgFalse";
                        query = query + " AND tanto_hinkan.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_koshin";
                        query = query + " ON haigo_hyoji.cd_koshin = tanto_koshin.cd_tanto";
                        query = query + " AND tanto_koshin.flg_sakujyo = @FlgFalse";
                        query = query + " AND tanto_koshin.flg_mishiyo = @FlgFalse";


                        query = query + " WHERE";
                        query = query + " haigo_hyoji.flg_sakujyo = @FlgFalse";
                        //配合コード指定";
                        query = query + " AND (@cd_haigo IS NULL OR haigo_hyoji.cd_haigo = @cd_haigo)";
                        //製法番号(XXXX-XXX-XX-XXXX)";
                        query = query + " AND (@no_seiho_kaisha IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 1, 4) = @no_seiho_kaisha)";
                        query = query + " AND (@no_seiho_shurui IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 6, 3) = @no_seiho_shurui)";
                        query = query + " AND (@no_seiho_nen IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 10, 2) = @no_seiho_nen)	";
                        query = query + " AND (@no_seiho_renban IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 13, 4) = @no_seiho_renban)	";
                        //code and name 配合名/コード";
                        query = query + " AND ( @nm_haigo IS NULL OR (haigo_hyoji.nm_haigo LIKE ('%!'+@nm_haigo+'%') ESCAPE '!')";
                        query = query + " OR (haigo_hyoji.cd_haigo LIKE ('%!'+@nm_haigo+'%') ESCAPE '!') )";
                        //code and name 製品名/コード";
                        query = query + " AND ( @nm_seihin IS NULL OR (seihin.nm_hin LIKE ('%!'+@nm_seihin+'%') ESCAPE '!')";
                        query = query + " OR (seihin.cd_hin LIKE ('%!'+@nm_seihin+'%') ESCAPE '!') )";
                        //品区分";
                        query = query + " AND (@kbn_hin IS NULL OR haigo_hyoji.kbn_hin = @kbn_hin)";
                        //仕掛品分類";
                        query = query + " AND (@cd_bunrui IS NULL OR haigo_hyoji.cd_bunrui = @cd_bunrui)";
                        //品管未確認のみ";
                        query = query + " AND (@flg_hinkan IS NULL OR haigo_hyoji.flg_hinkan = @flg_hinkan)";
                        //製造未確認のみ";
                        query = query + " AND (@flg_seizo IS NULL OR haigo_hyoji.flg_seizo = @flg_seizo)";
                        //原本も含む";
                        query = query + " AND (@flg_seiho_base IS NULL OR ISNULL(haigo_hyoji.flg_seiho_base, 0) = @flg_seiho_base)";
                        //未使用を含む";
                        query = query + " AND (@flg_mishiyo IS NULL OR haigo_hyoji.flg_mishiyo = @flg_mishiyo)";
                        //現在有効な版を対象";
                        query = query + " AND (@flg_genzai IS NULL ";
                        query = query + " OR ( haigo_hyoji.dt_from <= @dateSystem";
                        query = query + " AND haigo_hyoji.dt_to >= @dateSystem";
                        query = query + " AND (";
                        query = query + " (haigo_hyoji.flg_mishiyo = @FlgFalse";
                        query = query + " AND haigo_hyoji.no_han > 1)";

                        query = query + " OR (haigo_hyoji.no_han = @NoHanFirst ";
                        query = query + " AND NOT EXISTS( SELECT ";
                        query = query + " haigo_hyoji_exists.cd_haigo";
                        query = query + " FROM ma_haigo_mei_hyoji haigo_hyoji_exists";
                        query = query + " WHERE haigo_hyoji.cd_haigo = haigo_hyoji_exists.cd_haigo";
                        query = query + " AND haigo_hyoji_exists.flg_sakujyo = @FlgFalse";
                        query = query + " AND haigo_hyoji_exists.flg_mishiyo = @FlgFalse";
                        query = query + " AND @dateSystem BETWEEN haigo_hyoji_exists.dt_from AND haigo_hyoji_exists.dt_to";
                        query = query + " AND haigo_hyoji_exists.no_han > 1";
                        query = query + " ))))))";

                    }
                    else if (value.kansakujyoho == TenkaiTab)
                    {
                        query = query + " WITH CTE AS (";
                        query = query + " SELECT DISTINCT ";
                        query = query + " haigo_hyoji.cd_haigo";
                        query = query + " , haigo_hyoji.nm_haigo";
                        query = query + " , haigo_hyoji.nm_haigo_r";
                        query = query + " , haigo_hyoji.kbn_hin";
                        query = query + " , haigo_hyoji.nm_kbn_hin_csv AS nm_kbn_hin";
                        query = query + " , haigo_hyoji.cd_bunrui";
                        query = query + " , haigo_hyoji.nm_bunrui AS nm_bunrui_bunrui";
                        query = query + " , haigo_hyoji.budomari";
                        query = query + " , haigo_hyoji.qty_kihon";
                        query = query + " , haigo_hyoji.ritsu_kihon";
                        query = query + " , haigo_hyoji.cd_setsubi";
                        query = query + " , haigo_hyoji.nm_setsubi AS nm_setsubi_setsubi";
                        query = query + " , CAST(haigo_hyoji.flg_gasan AS INT) AS flg_gasan";
                        query = query + " , haigo_hyoji.qty_max";
                        query = query + " , haigo_hyoji.nm_seiho";
                        query = query + " , haigo_hyoji.no_han";
                        query = query + " , haigo_hyoji.qty_haigo_h";
                        query = query + " , haigo_hyoji.qty_haigo_kei";
                        query = query + " , haigo_hyoji.biko";
                        query = query + " , haigo_hyoji.no_seiho";
                        query = query + " , haigo_hyoji.cd_tanto_seizo";
                        query = query + " , haigo_hyoji.nm_tanto_seizo";
                        query = query + " , haigo_hyoji.dt_seizo";
                        query = query + " , haigo_hyoji.cd_tanto_hinkan";
                        query = query + " , haigo_hyoji.nm_tanto_hinkan";
                        query = query + " , haigo_hyoji.dt_hinkan";
                        query = query + " , haigo_hyoji.dt_from_csv AS dt_from";
                        query = query + " , haigo_hyoji.dt_to_csv AS dt_to";
                        query = query + " , haigo_hyoji.kbn_vw";

                        query = query + " , CASE";
                        query = query + " WHEN haigo_hyoji.kbn_vw = @KbnVwShi THEN @Kg";
                        query = query + " WHEN haigo_hyoji.kbn_vw = @KbnVwIchi THEN @L";
                        query = query + " ELSE ''";
                        query = query + " END AS nm_kbn_vw";
                        query = query + " , haigo_hyoji.hijyu";
                        query = query + " , CAST(haigo_hyoji.flg_shorihin AS INT) AS flg_shorihin";
                        query = query + " , CAST(haigo_hyoji.flg_hinkan AS INT) AS flg_hinkan";
                        query = query + " , CAST(haigo_hyoji.flg_seizo AS INT) AS flg_seizo";
                        query = query + " , CAST(haigo_hyoji.flg_sakujyo AS INT) AS flg_sakujyo";
                        query = query + " , CAST(haigo_hyoji.flg_mishiyo AS INT) AS flg_mishiyo";
                        query = query + " , haigo_hyoji.dt_toroku";
                        query = query + " , haigo_hyoji.dt_henko";
                        query = query + " , haigo_hyoji.cd_koshin";
                        query = query + " , haigo_hyoji.nm_koshin AS nm_tanto_koshin";
                        query = query + " , CAST(haigo_hyoji.kbn_shiagari AS INT) AS kbn_shiagari";
                        query = query + " , haigo_hyoji.cd_haigo_seiho";
                        query = query + " , CAST(haigo_hyoji.flg_seiho_base AS INT) AS flg_seiho_base";
                        query = query + " FROM fnHaigoichiranTenkai(@id_system, @dt_hidzuke, @cd_haigo_parttwo, @cd_hin_parttwo, 100) AS haigo_hyoji)";

                    }
                    else if (value.kansakujyoho == GenryoTab)
                    {
                        query = query + " WITH TENPURU AS (";
                        query = query + " SELECT haigo_recipe_hyoji.cd_haigo";
                        query = query + " , haigo_recipe_hyoji.no_han";
                        query = query + " , haigo_recipe_hyoji.cd_hin";
                        query = query + " , haigo_recipe_hyoji.kbn_hin";
                        query = query + " , haigo_recipe_hyoji.nm_hin AS nm_hin_recipe_hyoji";
                        query = query + " , haigo_recipe_hyoji.qty_haigo_h";
                        query = query + " , genshizai_seihin.nm_hin";
                        query = query + " , genshizai_seihin.no_kikaku";
                        query = query + " , genshizai_seihin.nm_kikaku";
                        query = query + " , genshizai_seihin.nm_hanbai";
                        query = query + " , genshizai_seihin.nm_seizo";
                        query = query + " FROM ma_haigo_recipe_hyoji haigo_recipe_hyoji";

                        query = query + " LEFT JOIN (";
                        query = query + " SELECT genshizai.cd_hin";
                        query = query + " , genshizai.nm_hin";
                        query = query + " , genshizai.no_kikaku";
                        query = query + " , genshizai.nm_kikaku";
                        query = query + " , genshizai.nm_hanbai";
                        query = query + " , genshizai.nm_seizo ";
                        query = query + " FROM ma_genshizai genshizai";
                        query = query + " WHERE genshizai.flg_mishiyo = @FlgFalse";
                        query = query + " AND genshizai.flg_sakujyo = @FlgFalse";
                        query = query + " UNION ALL";
                        query = query + " SELECT seihin.cd_hin";
                        query = query + " , seihin.nm_hin";
                        query = query + " , seihin.no_kikaku";
                        query = query + " , seihin.nm_kikaku";
                        query = query + " , seihin.nm_hanbai";
                        query = query + " , seihin.nm_seizo ";
                        query = query + " FROM ma_seihin seihin";

                        query = query + " WHERE seihin.kbn_seihin = 1";
                        query = query + " AND seihin.flg_mishiyo = @FlgFalse";
                        query = query + " AND seihin.flg_sakujyo = @FlgFalse";
                        query = query + " ) genshizai_seihin";
                        query = query + " ON haigo_recipe_hyoji.cd_hin = genshizai_seihin.cd_hin";

                        query = query + " WHERE haigo_recipe_hyoji.flg_mishiyo = @FlgFalse";
                        query = query + " AND haigo_recipe_hyoji.flg_sakujyo = @FlgFalse";
                        query = query + " ),";

                        query = query + " CTE AS( SELECT DISTINCT";
                        query = query + " haigo_mei_hyoji.cd_haigo";
                        query = query + " , haigo_mei_hyoji.nm_haigo";
                        query = query + " , haigo_mei_hyoji.nm_haigo_r";
                        query = query + " , haigo_mei_hyoji.kbn_hin";
                        query = query + " , CASE ";
                        query = query + " WHEN haigo_mei_hyoji.kbn_hin = @KubunRoku THEN @NamekbnRoku";
                        query = query + " ELSE kbn_hin.nm_kbn_hin";
                        query = query + " END AS nm_kbn_hin";
                        query = query + " , haigo_mei_hyoji.cd_bunrui";
                        query = query + " , bunrui.nm_bunrui AS nm_bunrui_bunrui";
                        query = query + " , haigo_mei_hyoji.budomari";
                        query = query + " , haigo_mei_hyoji.qty_kihon";
                        query = query + " , haigo_mei_hyoji.ritsu_kihon";
                        query = query + " , haigo_mei_hyoji.cd_setsubi";
                        query = query + " , setsubi.nm_setsubi AS nm_setsubi_setsubi ";
                        query = query + " , haigo_mei_hyoji.flg_gasan";
                        query = query + " , haigo_mei_hyoji.qty_max";
                        query = query + " , haigo_mei_hyoji.nm_seiho";
                        query = query + " , haigo_mei_hyoji.no_han";
                        query = query + " , haigo_mei_hyoji.qty_haigo_h";
                        query = query + " , haigo_mei_hyoji.qty_haigo_kei";
                        query = query + " , haigo_mei_hyoji.biko";
                        query = query + " , haigo_mei_hyoji.no_seiho";
                        query = query + " , haigo_mei_hyoji.cd_tanto_seizo";
                        query = query + " , tanto_seizo.nm_tanto AS nm_tanto_seizo";
                        query = query + " , haigo_mei_hyoji.dt_seizo";
                        query = query + " , haigo_mei_hyoji.cd_tanto_hinkan";
                        query = query + " , tanto_hinkan.nm_tanto AS nm_tanto_hinkan";
                        query = query + " , haigo_mei_hyoji.dt_hinkan";
                        query = query + " , haigo_mei_hyoji.dt_from";
                        query = query + " , haigo_mei_hyoji.dt_to";
                        query = query + " , haigo_mei_hyoji.kbn_vw";

                        query = query + " , CASE";
                        query = query + " WHEN haigo_mei_hyoji.kbn_vw = @KbnVwShi THEN @Kg";
                        query = query + " WHEN haigo_mei_hyoji.kbn_vw = @KbnVwIchi THEN @L";
                        query = query + " ELSE ''";
                        query = query + " END AS nm_kbn_vw";
                        query = query + " , haigo_mei_hyoji.hijyu";
                        query = query + " , haigo_mei_hyoji.flg_shorihin";
                        query = query + " , haigo_mei_hyoji.flg_hinkan";
                        query = query + " , haigo_mei_hyoji.flg_seizo";
                        query = query + " , haigo_mei_hyoji.flg_sakujyo";
                        query = query + " , haigo_mei_hyoji.flg_mishiyo";
                        query = query + " , haigo_mei_hyoji.dt_toroku";
                        query = query + " , haigo_mei_hyoji.dt_henko";
                        query = query + " , haigo_mei_hyoji.cd_koshin";
                        query = query + " , tanto_koshin.nm_tanto AS nm_tanto_koshin";
                        query = query + " , haigo_mei_hyoji.kbn_shiagari";
                        query = query + " , haigo_mei_hyoji.cd_haigo_seiho";
                        query = query + " , haigo_mei_hyoji.flg_seiho_base";
                        query = query + " FROM ma_haigo_mei_hyoji haigo_mei_hyoji";

                        query = query + " INNER JOIN TENPURU tenpuru";
                        query = query + " ON haigo_mei_hyoji.cd_haigo = tenpuru.cd_haigo";
                        query = query + " AND haigo_mei_hyoji.no_han = tenpuru.no_han";
                        //query = query + " AND haigo_mei_hyoji.qty_haigo_h = tenpuru.qty_haigo_h";

                        query = query + " LEFT JOIN ma_haigo_mei haigo_mei";
                        query = query + " ON haigo_mei_hyoji.cd_haigo = haigo_mei.cd_haigo";
                        query = query + " AND haigo_mei_hyoji.no_han = haigo_mei.no_han";
                        query = query + " AND haigo_mei.flg_sakujyo = @FlgFalse";

                        query = query + " LEFT JOIN #haigomei haigo_original";
                        query = query + " ON haigo_mei_hyoji.cd_haigo = haigo_original.cd_haigo";

                        query = query + " LEFT JOIN ma_kbn_hin kbn_hin";
                        query = query + " ON haigo_mei_hyoji.kbn_hin = kbn_hin.kbn_hin";

                        query = query + " LEFT JOIN ma_bunrui bunrui";
                        query = query + " ON haigo_mei_hyoji.kbn_hin = bunrui.kbn_hin";
                        query = query + " AND haigo_mei_hyoji.cd_bunrui = bunrui.cd_bunrui";
                        query = query + " AND bunrui.flg_sakujyo = @FlgFalse AND bunrui.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_setsubi setsubi";
                        query = query + " ON haigo_mei_hyoji.cd_setsubi = setsubi.cd_setsubi";
                        query = query + " AND setsubi.flg_sakujyo = @FlgFalse AND setsubi.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_koshin";
                        query = query + " ON haigo_mei_hyoji.cd_koshin = tanto_koshin.cd_tanto";
                        query = query + " AND tanto_koshin.flg_sakujyo = @FlgFalse AND tanto_koshin.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_seizo";
                        query = query + " ON haigo_mei_hyoji.cd_tanto_seizo = tanto_seizo.cd_tanto";
                        query = query + " AND tanto_seizo.flg_sakujyo = @FlgFalse AND tanto_seizo.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_hinkan";
                        query = query + " ON haigo_mei_hyoji.cd_tanto_hinkan = tanto_hinkan.cd_tanto";
                        query = query + " AND tanto_hinkan.flg_sakujyo = @FlgFalse AND tanto_hinkan.flg_mishiyo = @FlgFalse";

                        query = query + " WHERE haigo_mei_hyoji.flg_sakujyo = @FlgFalse";
                        //原料コード
                        query = query + " AND (@cd_genryo IS NULL OR tenpuru.cd_hin = @cd_genryo)  ";
                        query = query + " AND (@kbn_hin_genryo IS NULL OR tenpuru.kbn_hin = @kbn_hin_genryo)";
                        //原料名
                        query = query + " AND (( @nm_genryo_text IS NULL OR (tenpuru.nm_hin LIKE ('%!'+@nm_genryo_text+'%') ESCAPE '!') )";
                        query = query + " OR ( @nm_genryo_text IS NULL OR (tenpuru.nm_hin_recipe_hyoji LIKE ('%!'+@nm_genryo_text+'%') ESCAPE '!') ))";
                        //企画書No.
                        query = query + " AND (@cd_kikaku IS NULL OR tenpuru.no_kikaku LIKE ( + @cd_kikaku + '%'))";
                        //規格書商品名
                        query = query + " AND (@nm_kikaku_shohin IS NULL OR (tenpuru.nm_kikaku LIKE ('%!'+@nm_kikaku_shohin+'%') ESCAPE '!'))";
                        //製造社名
                        query = query + " AND (@nm_seiho_mei IS NULL OR (tenpuru.nm_seizo LIKE ('%!'+@nm_seiho_mei+'%') ESCAPE '!'))";
                        //販売社名
                        query = query + " AND (@nm_hanbai_mei IS NULL OR (tenpuru.nm_hanbai LIKE ('%!'+@nm_hanbai_mei+'%') ESCAPE '!')))";
                    }
                }
                    //FOODPROC mode
                else
                {
                    if (value.kansakujyoho == HaigoTab)
                    {
                        query = query + " WITH CTE AS (";
                        query = query + " SELECT DISTINCT";
                        query = query + " haigo_hyoji.cd_haigo";
                        query = query + " , haigo_hyoji.nm_haigo";
                        query = query + " , haigo_hyoji.nm_haigo_r";
                        query = query + " , haigo_hyoji.kbn_hin";
                        query = query + " , CASE ";
                        query = query + " WHEN haigo_hyoji.kbn_hin = @KubunRoku THEN @NamekbnRoku";
                        query = query + " ELSE kbn_hin.nm_kbn_hin";
                        query = query + " END AS nm_kbn_hin";
                        query = query + " , haigo_hyoji.cd_bunrui";
                        query = query + " , bunrui.nm_bunrui AS nm_bunrui_bunrui";
                        query = query + " , haigo_hyoji.budomari";
                        query = query + " , haigo_hyoji.qty_kihon";
                        query = query + " , haigo_hyoji.ritsu_kihon";
                        query = query + " , haigo_hyoji.cd_setsubi";
                        query = query + " , setsubi.nm_setsubi AS nm_setsubi_setsubi";
                        query = query + " , haigo_hyoji.flg_gasan";
                        query = query + " , haigo_hyoji.qty_max";
                        query = query + " , haigo_hyoji.nm_seiho";
                        query = query + " , haigo_hyoji.no_han";
                        query = query + " , MAX(haigo_hyoji.no_han) OVER(PARTITION BY haigo_hyoji.cd_haigo) AS max_no_han";
                        query = query + " , haigo_hyoji.qty_haigo_h";
                        query = query + " , haigo_hyoji.qty_haigo_kei";
                        query = query + " , haigo_hyoji.biko";
                        query = query + " , haigo_hyoji.no_seiho";
                        query = query + " , haigo_hyoji.cd_tanto_seizo";
                        query = query + " , tanto_seizo.nm_tanto AS nm_tanto_seizo";
                        query = query + " , haigo_hyoji.dt_seizo";
                        query = query + " , haigo_hyoji.cd_tanto_hinkan";
                        query = query + " , tanto_hinkan.nm_tanto AS nm_tanto_hinkan";
                        query = query + " , haigo_hyoji.dt_hinkan";
                        query = query + " , haigo_hyoji.dt_from";
                        query = query + " , haigo_hyoji.dt_to";
                        query = query + " , haigo_hyoji.kbn_vw";

                        query = query + " , CASE";
                        query = query + " WHEN haigo_hyoji.kbn_vw = @KbnVwShi THEN @Kg";
                        query = query + " WHEN haigo_hyoji.kbn_vw = @KbnVwIchi THEN @L";
                        query = query + " ELSE ''";
                        query = query + " END AS nm_kbn_vw";
                        query = query + " , haigo_hyoji.hijyu";
                        query = query + " , haigo_hyoji.flg_shorihin";
                        query = query + " , haigo_hyoji.flg_hinkan";
                        query = query + " , haigo_hyoji.flg_seizo";
                        query = query + " , haigo_hyoji.flg_sakujyo";
                        query = query + " , haigo_hyoji.flg_mishiyo";
                        query = query + " , haigo_hyoji.dt_toroku";
                        query = query + " , haigo_hyoji.dt_henko";
                        query = query + " , haigo_hyoji.cd_koshin";
                        query = query + " , tanto_koshin.nm_tanto AS nm_tanto_koshin";
                        query = query + " , haigo_hyoji.kbn_shiagari";
                        query = query + " , haigo_hyoji.cd_haigo_seiho";
                        query = query + " , haigo_hyoji.flg_seiho_base";

                        query = query + " FROM ma_haigo_mei haigo_hyoji";

                       
                        query = query + " LEFT JOIN ma_seihin seihin";
                        query = query + " ON haigo_hyoji.cd_haigo = seihin.cd_haigo";
                        query = query + " AND seihin.flg_sakujyo = @FlgFalse";
                        query = query + " AND seihin.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_setsubi setsubi";
                        query = query + " ON haigo_hyoji.cd_setsubi = setsubi.cd_setsubi";
                        query = query + " AND setsubi.flg_sakujyo = @FlgFalse AND setsubi.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_seizo";
                        query = query + " ON haigo_hyoji.cd_tanto_seizo = tanto_seizo.cd_tanto";
                        query = query + " AND tanto_seizo.flg_sakujyo = @FlgFalse AND tanto_seizo.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_hinkan";
                        query = query + " ON haigo_hyoji.cd_tanto_hinkan = tanto_hinkan.cd_tanto";
                        query = query + " AND tanto_hinkan.flg_sakujyo = @FlgFalse AND tanto_hinkan.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_koshin";
                        query = query + " ON haigo_hyoji.cd_koshin = tanto_koshin.cd_tanto";
                        query = query + " AND tanto_koshin.flg_sakujyo = @FlgFalse AND tanto_koshin.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_kbn_hin kbn_hin";
                        query = query + " ON haigo_hyoji.kbn_hin = kbn_hin.kbn_hin";

                        query = query + " LEFT JOIN ma_bunrui bunrui";
                        query = query + " ON haigo_hyoji.kbn_hin = bunrui.kbn_hin";
                        query = query + " AND haigo_hyoji.cd_bunrui = bunrui.cd_bunrui";
                        query = query + " AND bunrui.flg_sakujyo = @FlgFalse AND bunrui.flg_mishiyo = @FlgFalse";


                        query = query + " WHERE haigo_hyoji.flg_sakujyo = @FlgFalse";
                        query = query + " AND haigo_hyoji.qty_kihon = haigo_hyoji.qty_haigo_h";
                        //配合コード指定
                        query = query + " AND (@cd_haigo IS NULL OR haigo_hyoji.cd_haigo = @cd_haigo)";
                        //製法番号(XXXX-XXX-XX-XXXX)
                        query = query + " AND (@no_seiho_kaisha IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 1, 4) = @no_seiho_kaisha)";
                        query = query + " AND (@no_seiho_shurui IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 6, 3) = @no_seiho_shurui)";
                        query = query + " AND (@no_seiho_nen IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 10, 2) = @no_seiho_nen)	";
                        query = query + " AND (@no_seiho_renban IS NULL OR SUBSTRING(haigo_hyoji.no_seiho, 13, 4) = @no_seiho_renban)";
                        //code and name 配合名/コード
                        query = query + " AND ( @nm_haigo IS NULL OR (haigo_hyoji.nm_haigo LIKE ('%!'+@nm_haigo+'%') ESCAPE '!')";
                        query = query + " OR (haigo_hyoji.cd_haigo LIKE ('%!'+@nm_haigo+'%') ESCAPE '!') )";
                        //code and name 製品名/コード
                        query = query + " AND ( @nm_seihin IS NULL OR (seihin.nm_hin LIKE ('%!'+@nm_seihin+'%') ESCAPE '!')";
                        query = query + " OR (seihin.cd_hin LIKE ('%!'+@nm_seihin+'%') ESCAPE '!') )";
                        //品区分
                        query = query + " AND (@kbn_hin IS NULL OR haigo_hyoji.kbn_hin = @kbn_hin)";
                        //仕掛品分類
                        query = query + " AND (@cd_bunrui IS NULL OR haigo_hyoji.cd_bunrui = @cd_bunrui)";
                        //品管未確認のみ
                        query = query + " AND (@flg_hinkan IS NULL OR haigo_hyoji.flg_hinkan = @flg_hinkan)";
                        //製造未確認のみ
                        query = query + " AND (@flg_seizo IS NULL OR haigo_hyoji.flg_seizo = @flg_seizo)";
                        //原本も含む
                        query = query + " AND (@flg_seiho_base IS NULL OR ISNULL(haigo_hyoji.flg_seiho_base, 0) = @flg_seiho_base)";
                        //未使用を含む
                        query = query + " AND (@flg_mishiyo IS NULL OR haigo_hyoji.flg_mishiyo = @flg_mishiyo)";
                        //現在有効な版を対象
                        query = query + " AND (@flg_genzai IS NULL";
                        query = query + " OR ( haigo_hyoji.dt_from <= @dateSystem";
                        query = query + " AND haigo_hyoji.dt_to >= @dateSystem";
                        query = query + " AND ( (haigo_hyoji.flg_mishiyo = @FlgFalse";
                        query = query + " AND haigo_hyoji.no_han > 1)";

                        query = query + " OR (haigo_hyoji.no_han = @NoHanFirst ";
                        query = query + " AND NOT EXISTS(";
                        query = query + " SELECT haigo_hyoji_exists.cd_haigo";
                        query = query + " FROM ma_haigo_mei haigo_hyoji_exists";
                        query = query + " WHERE  haigo_hyoji.cd_haigo = haigo_hyoji_exists.cd_haigo";
                        query = query + " AND haigo_hyoji_exists.flg_sakujyo = @FlgFalse";
                        query = query + " AND haigo_hyoji_exists.flg_mishiyo = @FlgFalse";
                        query = query + " AND haigo_hyoji_exists.qty_kihon = haigo_hyoji_exists.qty_haigo_h";
                        query = query + " AND @dateSystem BETWEEN haigo_hyoji_exists.dt_from AND haigo_hyoji_exists.dt_to";
                        query = query + " AND haigo_hyoji_exists.no_han > 1";
                        query = query + " ))))))";
                    }
                    else if (value.kansakujyoho == TenkaiTab)
                    {
                        query = query + " WITH CTE AS (";
                        query = query + " SELECT DISTINCT ";
                        query = query + "   haigo_hyoji.cd_haigo";
                        query = query + " , haigo_hyoji.nm_haigo";
                        query = query + " , haigo_hyoji.nm_haigo_r";
                        query = query + " , haigo_hyoji.kbn_hin";
                        query = query + " , haigo_hyoji.nm_kbn_hin_csv AS nm_kbn_hin";
                        query = query + " , haigo_hyoji.cd_bunrui";
                        query = query + " , haigo_hyoji.nm_bunrui AS nm_bunrui_bunrui";
                        query = query + " , haigo_hyoji.budomari";
                        query = query + " , haigo_hyoji.qty_kihon";
                        query = query + " , haigo_hyoji.ritsu_kihon";
                        query = query + " , haigo_hyoji.cd_setsubi";
                        query = query + " , haigo_hyoji.nm_setsubi AS nm_setsubi_setsubi";
                        query = query + " , CAST(haigo_hyoji.flg_gasan AS INT) AS flg_gasan";
                        query = query + " , haigo_hyoji.qty_max";
                        query = query + " , haigo_hyoji.nm_seiho";
                        query = query + " , haigo_hyoji.no_han";
                        query = query + " , haigo_hyoji.qty_haigo_h";
                        query = query + " , haigo_hyoji.qty_haigo_kei";
                        query = query + " , haigo_hyoji.biko";
                        query = query + " , haigo_hyoji.no_seiho";
                        query = query + " , haigo_hyoji.cd_tanto_seizo";
                        query = query + " , haigo_hyoji.nm_tanto_seizo";
                        query = query + " , haigo_hyoji.dt_seizo";
                        query = query + " , haigo_hyoji.cd_tanto_hinkan";
                        query = query + " , haigo_hyoji.nm_tanto_hinkan";
                        query = query + " , haigo_hyoji.dt_hinkan";
                        query = query + " , haigo_hyoji.dt_from_csv AS dt_from";
                        query = query + " , haigo_hyoji.dt_to_csv AS dt_to";
                        query = query + " , haigo_hyoji.kbn_vw";

                        query = query + " , CASE";
                        query = query + " WHEN haigo_hyoji.kbn_vw = @KbnVwShi THEN @Kg";
                        query = query + " WHEN haigo_hyoji.kbn_vw = @KbnVwIchi THEN @L";
                        query = query + " ELSE ''";
                        query = query + " END AS nm_kbn_vw";
                        query = query + " , haigo_hyoji.hijyu";
                        query = query + " , CAST(haigo_hyoji.flg_shorihin AS INT) AS flg_shorihin";
                        query = query + " , CAST(haigo_hyoji.flg_hinkan AS INT) AS flg_hinkan";
                        query = query + " , CAST(haigo_hyoji.flg_seizo AS INT) AS flg_seizo";
                        query = query + " , CAST(haigo_hyoji.flg_sakujyo AS INT) AS flg_sakujyo";
                        query = query + " , CAST(haigo_hyoji.flg_mishiyo AS INT) AS flg_mishiyo";
                        query = query + " , haigo_hyoji.dt_toroku";
                        query = query + " , haigo_hyoji.dt_henko";
                        query = query + " , haigo_hyoji.cd_koshin";
                        query = query + " , haigo_hyoji.nm_koshin AS nm_tanto_koshin";
                        query = query + " , CAST(haigo_hyoji.kbn_shiagari AS INT) AS kbn_shiagari";
                        query = query + " , haigo_hyoji.cd_haigo_seiho";
                        query = query + " , CAST(haigo_hyoji.flg_seiho_base AS INT) AS flg_seiho_base";
                        query = query + " FROM fnHaigoichiranTenkai(@id_system, @dt_hidzuke, @cd_haigo_parttwo, @cd_hin_parttwo, 100) AS haigo_hyoji)";
                    }
                    else if (value.kansakujyoho == GenryoTab)
                    {
                        query = query + " WITH TENPURU AS (";
                        query = query + " SELECT haigo_recipe.cd_haigo";
                        query = query + " , haigo_recipe.no_han";
                        query = query + " , haigo_recipe.cd_hin";
                        query = query + " , haigo_recipe.kbn_hin";
                        query = query + " , haigo_recipe.nm_hin AS nm_hin_recipe";
                        query = query + " , haigo_recipe.qty_haigo_h";
                        query = query + " , genshizai_seihin.nm_hin";
                        query = query + " , genshizai_seihin.no_kikaku";
                        query = query + " , genshizai_seihin.nm_kikaku";
                        query = query + " , genshizai_seihin.nm_hanbai";
                        query = query + " , genshizai_seihin.nm_seizo";
                        query = query + " FROM ma_haigo_recipe haigo_recipe";

                        query = query + " LEFT JOIN (";
                        query = query + " SELECT genshizai.cd_hin";
                        query = query + " , genshizai.nm_hin";
                        query = query + " , genshizai.no_kikaku";
                        query = query + " , genshizai.nm_kikaku";
                        query = query + " , genshizai.nm_hanbai";
                        query = query + " , genshizai.nm_seizo ";
                        query = query + " FROM ma_genshizai genshizai";

                        query = query + " WHERE genshizai.flg_mishiyo = @FlgFalse";
                        query = query + " AND genshizai.flg_sakujyo = @FlgFalse";
                        query = query + " UNION ALL";
                        query = query + " SELECT seihin.cd_hin";
                        query = query + " , seihin.nm_hin";
                        query = query + " , seihin.no_kikaku";
                        query = query + " , seihin.nm_kikaku";
                        query = query + " , seihin.nm_hanbai";
                        query = query + " , seihin.nm_seizo ";
                        query = query + " FROM ma_seihin seihin";

                        query = query + " WHERE seihin.kbn_seihin = 1";
                        query = query + " AND seihin.flg_mishiyo = @FlgFalse";
                        query = query + " AND seihin.flg_sakujyo = @FlgFalse";
                        query = query + " ) genshizai_seihin";
                        query = query + " ON haigo_recipe.cd_hin = genshizai_seihin.cd_hin";
                        query = query + " WHERE haigo_recipe.flg_mishiyo = @FlgFalse";
                        query = query + " AND haigo_recipe.flg_sakujyo = @FlgFalse";
                        query = query + " ),";
                        query = query + " CTE AS(";
                        query = query + " SELECT DISTINCT";
                        query = query + " haigo_mei.cd_haigo";
                        query = query + " , haigo_mei.nm_haigo";
                        query = query + " , haigo_mei.nm_haigo_r";
                        query = query + " , haigo_mei.kbn_hin";
                        query = query + " , CASE ";
                        query = query + " WHEN haigo_mei.kbn_hin = @KubunRoku THEN @NamekbnRoku";
                        query = query + " ELSE kbn_hin.nm_kbn_hin";
                        query = query + " END AS nm_kbn_hin";
                        query = query + " , haigo_mei.cd_bunrui";
                        query = query + " , bunrui.nm_bunrui AS nm_bunrui_bunrui";
                        query = query + " , haigo_mei.budomari";
                        query = query + " , haigo_mei.qty_kihon";
                        query = query + " , haigo_mei.ritsu_kihon";
                        query = query + " , haigo_mei.cd_setsubi";
                        query = query + " , setsubi.nm_setsubi AS nm_setsubi_setsubi";
                        query = query + " , haigo_mei.flg_gasan";
                        query = query + " , haigo_mei.qty_max";
                        query = query + " , haigo_mei.nm_seiho";
                        query = query + " , haigo_mei.no_han";
                        query = query + " , haigo_mei.qty_haigo_h";
                        query = query + " , haigo_mei.qty_haigo_kei";
                        query = query + " , haigo_mei.biko";
                        query = query + " , haigo_mei.no_seiho";
                        query = query + " , haigo_mei.cd_tanto_seizo";
                        query = query + " , tanto_seizo.nm_tanto AS nm_tanto_seizo";
                        query = query + " , haigo_mei.dt_seizo";
                        query = query + " , haigo_mei.cd_tanto_hinkan";
                        query = query + " , tanto_hinkan.nm_tanto AS nm_tanto_hinkan";
                        query = query + " , haigo_mei.dt_hinkan";
                        query = query + " , haigo_mei.dt_from";
                        query = query + " , haigo_mei.dt_to";
                        query = query + " , haigo_mei.kbn_vw";

                        query = query + " , CASE";
                        query = query + " WHEN haigo_mei.kbn_vw = @KbnVwShi THEN @Kg";
                        query = query + " WHEN haigo_mei.kbn_vw = @KbnVwIchi THEN @L";
                        query = query + " ELSE ''";
                        query = query + " END AS nm_kbn_vw";
                        query = query + " , haigo_mei.hijyu";
                        query = query + " , haigo_mei.flg_shorihin";
                        query = query + " , haigo_mei.flg_hinkan";
                        query = query + " , haigo_mei.flg_seizo";
                        query = query + " , haigo_mei.flg_sakujyo";
                        query = query + " , haigo_mei.flg_mishiyo";
                        query = query + " , haigo_mei.dt_toroku";
                        query = query + " , haigo_mei.dt_henko";
                        query = query + " , haigo_mei.cd_koshin";
                        query = query + " , tanto_koshin.nm_tanto AS nm_tanto_koshin";
                        query = query + " , haigo_mei.kbn_shiagari";
                        query = query + " , haigo_mei.cd_haigo_seiho";
                        query = query + " , haigo_mei.flg_seiho_base";
                        query = query + " FROM ma_haigo_mei haigo_mei";

                        query = query + " INNER JOIN TENPURU tenpuru";
                        query = query + " ON haigo_mei.cd_haigo = tenpuru.cd_haigo";
                        query = query + " AND haigo_mei.no_han = tenpuru.no_han";
                        query = query + " AND haigo_mei.qty_haigo_h = tenpuru.qty_haigo_h";

                        query = query + " LEFT JOIN ma_kbn_hin kbn_hin";
                        query = query + " ON  haigo_mei.kbn_hin = kbn_hin.kbn_hin";

                        query = query + " LEFT JOIN ma_bunrui bunrui";
                        query = query + " ON  haigo_mei.kbn_hin = bunrui.kbn_hin";
                        query = query + " AND haigo_mei.cd_bunrui = bunrui.cd_bunrui";
                        query = query + " AND bunrui.flg_sakujyo = @FlgFalse AND bunrui.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_setsubi setsubi";
                        query = query + " ON  haigo_mei.cd_setsubi = setsubi.cd_setsubi";
                        query = query + " AND setsubi.flg_sakujyo = @FlgFalse AND setsubi.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_koshin";
                        query = query + " ON  haigo_mei.cd_koshin = tanto_koshin.cd_tanto";
                        query = query + " AND tanto_koshin.flg_sakujyo = @FlgFalse AND setsubi.flg_mishiyo = @FlgFalse";


                        query = query + " LEFT JOIN SS_vw_hin vw_hin";
                        query = query + " ON  tenpuru.cd_hin = vw_hin.cd_hin";
                        query = query + " AND tenpuru.kbn_hin = vw_hin.kbn_hin_toroku";

                        query = query + " LEFT JOIN ma_tanto tanto_seizo";
                        query = query + " ON  haigo_mei.cd_tanto_seizo = tanto_seizo.cd_tanto";
                        query = query + " AND tanto_seizo.flg_sakujyo = @FlgFalse AND tanto_seizo.flg_mishiyo = @FlgFalse";

                        query = query + " LEFT JOIN ma_tanto tanto_hinkan";
                        query = query + " ON  haigo_mei.cd_tanto_hinkan = tanto_hinkan.cd_tanto";
                        query = query + " AND tanto_hinkan.flg_sakujyo = @FlgFalse AND tanto_hinkan.flg_mishiyo = @FlgFalse";

                        query = query + " WHERE haigo_mei.flg_sakujyo = @FlgFalse";
                        query = query + " AND haigo_mei.qty_kihon = haigo_mei.qty_haigo_h";
                        //原料コード
                        query = query + " AND (@cd_genryo IS NULL OR tenpuru.cd_hin = @cd_genryo) ";
                        query = query + " AND (@kbn_hin_genryo IS NULL OR vw_hin.kbn_hin = @kbn_hin_genryo)";
                        //原料名
                        query = query + " AND (( @nm_genryo_text IS NULL OR (tenpuru.nm_hin LIKE ('%!'+@nm_genryo_text+'%') ESCAPE '!') )";
                        query = query + " OR ( @nm_genryo_text IS NULL OR (tenpuru.nm_hin_recipe LIKE ('%!'+@nm_genryo_text+'%') ESCAPE '!') ))";
                        //企画書No.
                        query = query + " AND (@cd_kikaku IS NULL OR tenpuru.no_kikaku LIKE ( + @cd_kikaku + '%'))";
                        //規格書商品名
                        query = query + " AND (@nm_kikaku_shohin IS NULL OR (tenpuru.nm_kikaku LIKE ('%!'+@nm_kikaku_shohin+'%') ESCAPE '!'))";
                        //製造社名
                        query = query + " AND (@nm_seiho_mei IS NULL OR (tenpuru.nm_seizo LIKE ('%!'+@nm_seiho_mei+'%') ESCAPE '!'))";
                        //販売社名
                        query = query + " AND (@nm_hanbai_mei IS NULL OR (tenpuru.nm_hanbai LIKE ('%!'+@nm_hanbai_mei+'%') ESCAPE '!')))";
                    }
                }

                var searchData = " SELECT cd_haigo, nm_haigo, nm_haigo_r";
                searchData = searchData + " , kbn_hin, nm_kbn_hin, cd_bunrui, nm_bunrui_bunrui, budomari, qty_kihon, ritsu_kihon";
                searchData = searchData + " , cd_setsubi, nm_setsubi_setsubi, CAST(flg_gasan AS INT) AS flg_gasan, qty_max, nm_seiho, no_han ,qty_haigo_h";
                searchData = searchData + " , qty_haigo_kei, REPLACE(REPLACE(biko,'\r',''),'\n',' ') AS biko, no_seiho, cd_tanto_seizo, nm_tanto_seizo, dt_seizo, cd_tanto_hinkan";
                searchData = searchData + " , nm_tanto_hinkan, dt_hinkan, dt_from, dt_to, kbn_vw, nm_kbn_vw, hijyu, CAST(flg_shorihin AS INT) AS flg_shorihin";
                searchData = searchData + " , CAST(flg_hinkan AS INT) AS flg_hinkan, CAST(flg_seizo AS INT) AS flg_seizo, CAST(flg_sakujyo AS INT) AS flg_sakujyo, CAST(flg_mishiyo AS INT) AS flg_mishiyo, dt_toroku, dt_henko, cd_koshin";
                searchData = searchData + " , nm_tanto_koshin, CAST(kbn_shiagari AS INT) AS kbn_shiagari, cd_haigo_seiho, CAST(flg_seiho_base AS INT) AS flg_seiho_base";
                searchData = searchData + " FROM CTE";
                if (value.m_kirikae != Properties.Resources.m_kirikae_hyoji && value.kansakujyoho == HaigoTab)
                {
                    searchData = searchData + " WHERE @flg_genzai IS NULL OR no_han = max_no_han";
                }
                searchData = searchData + " ORDER BY ";
                searchData = searchData + " (CASE @kbn_sort WHEN 1 THEN cd_haigo END) ASC";
                searchData = searchData + " , (CASE @kbn_sort WHEN 2 THEN cd_haigo END) DESC";
                searchData = searchData + " , (CASE @kbn_sort WHEN 3 THEN no_seiho END) ASC";
                searchData = searchData + " , (CASE @kbn_sort WHEN 4 THEN no_seiho END) DESC";
                searchData = searchData + " , CASE @kbn_sort WHEN 3 THEN cd_haigo END";
                searchData = searchData + " , CASE @kbn_sort WHEN 4 THEN cd_haigo END";
                searchData = searchData + " , no_han ";



                var dropTempTable = " DROP TABLE #haigomei";

                var querySearch = "";
                if (value.m_kirikae == Properties.Resources.m_kirikae_hyoji && value.kansakujyoho != TenkaiTab)
                {
                    querySearch = declareValue + tableTemp + insertTableTemp + query + searchData + dropTempTable;
                }
                else
                {
                    querySearch = declareValue + query + searchData;
                }

                var parameters = new object[]
                {
                    new SqlParameter("@id_system", SqlDbType.SmallInt) { Value = value.id_system },
                    new SqlParameter("@kbn_sort", SqlDbType.SmallInt) { Value = value.kbn_sort },

                    new SqlParameter("@cd_haigo", SqlDbType.VarChar, 13) { Value = (object)value.cd_haigo ?? DBNull.Value },
                    new SqlParameter("@flg_seiho_base", SqlDbType.SmallInt) { Value = (object)value.flg_seiho_base ?? DBNull.Value },
                    new SqlParameter("@no_seiho_kaisha", SqlDbType.VarChar, 4) { Value = (object)value.no_seiho_kaisha ?? DBNull.Value },
                    new SqlParameter("@no_seiho_shurui", SqlDbType.VarChar, 3) { Value = (object)value.no_seiho_shurui ?? DBNull.Value },
                    new SqlParameter("@no_seiho_nen", SqlDbType.VarChar, 2) { Value = (object)value.no_seiho_nen ?? DBNull.Value },
                    new SqlParameter("@no_seiho_renban", SqlDbType.VarChar, 4) { Value = (object)value.no_seiho_renban ?? DBNull.Value },
                    new SqlParameter("@nm_haigo", SqlDbType.VarChar, 60) { Value = (object)value.nm_haigo ?? DBNull.Value },
                    new SqlParameter("@kbn_hin", SqlDbType.VarChar, 1) { Value = (object)value.kbn_hin ?? DBNull.Value },
                    new SqlParameter("@cd_bunrui", SqlDbType.VarChar, 2) { Value = (object)value.cd_bunrui ?? DBNull.Value },
                    new SqlParameter("@nm_seihin", SqlDbType.VarChar, 60) { Value = (object)value.nm_seihin ?? DBNull.Value },
                    new SqlParameter("@flg_hinkan", SqlDbType.SmallInt) { Value = (object)value.flg_hinkan ?? DBNull.Value },
                    new SqlParameter("@flg_seizo", SqlDbType.SmallInt) { Value = (object)value.flg_seizo ?? DBNull.Value },
                    new SqlParameter("@flg_genzai", SqlDbType.SmallInt) { Value = (object)value.flg_genzai ?? DBNull.Value },
                    new SqlParameter("@flg_mishiyo", SqlDbType.SmallInt) { Value = (object)value.flg_mishiyo ?? DBNull.Value },

                    new SqlParameter("@dt_hidzuke", SqlDbType.DateTime) { Value = (object)value.dt_hidzuke ?? DBNull.Value },
                    new SqlParameter("@cd_haigo_parttwo", SqlDbType.VarChar, 13) { Value = (object)value.cd_haigo_parttwo ?? DBNull.Value },
                    new SqlParameter("@cd_hin_parttwo", SqlDbType.VarChar, 13) { Value = (object)value.cd_hin_parttwo ?? DBNull.Value },

                    new SqlParameter("@cd_genryo", SqlDbType.VarChar, 13) { Value = (object)value.cd_genryo ?? DBNull.Value },
                    new SqlParameter("@kbn_hin_genryo", SqlDbType.VarChar, 1) { Value = (object)value.kbn_hin_genryo ?? DBNull.Value },
                    new SqlParameter("@nm_genryo_text", SqlDbType.VarChar, 60) { Value = (object)value.nm_genryo_text ?? DBNull.Value },
                    new SqlParameter("@cd_kikaku", SqlDbType.VarChar, 17) { Value = (object)value.cd_kikaku ?? DBNull.Value },
                    new SqlParameter("@nm_kikaku_shohin", SqlDbType.VarChar, 60) { Value = (object)value.nm_kikaku_shohin ?? DBNull.Value },
                    new SqlParameter("@nm_seiho_mei", SqlDbType.VarChar, 60) { Value = (object)value.nm_seiho_mei ?? DBNull.Value },
                    new SqlParameter("@nm_hanbai_mei", SqlDbType.VarChar, 60) { Value = (object)value.nm_hanbai_mei ?? DBNull.Value },

                    new SqlParameter("@FlgFalse", SqlDbType.VarChar, 1) { Value = FlgFalse },
                    new SqlParameter("@FlgTrue", SqlDbType.VarChar, 1) { Value = FlgTrue },
                    new SqlParameter("@NoHanFirst", SqlDbType.VarChar, 1) { Value = NoHanFirst },
                    new SqlParameter("@KubunRoku", SqlDbType.VarChar, 1) { Value = KubunRoku },
                    new SqlParameter("@NamekbnRoku", SqlDbType.VarChar, 10) { Value = NamekbnRoku },
                    new SqlParameter("@Kg", SqlDbType.VarChar, 2) { Value = Kg },
                    new SqlParameter("@L", SqlDbType.VarChar, 2) { Value = L },
                    new SqlParameter("@KbnVwShi", SqlDbType.VarChar, 2) { Value = KbnVwShi },
                    new SqlParameter("@KbnVwIchi", SqlDbType.VarChar, 2) { Value = KbnVwIchi }

                };

                results = context.Database.SqlQuery<ResultCSV_208>(querySearch, parameters).ToList();
            }


            MemoryStream stream = new MemoryStream();
            TextFieldFile<ResultCSV_208> tFile
                                = new TextFieldFile<ResultCSV_208>(stream,
                                                                      Encoding.GetEncoding(Properties.Resources.Encoding), CSV208_Download);
            tFile.Delimiters = new string[] { "," };
            // TODO: ヘッダーを有効にするには IsFirstRowHeader を true に設定にします。
            tFile.IsFirstRowHeader = true;
            tFile.WriteFields(results as IEnumerable<ResultCSV_208>);

            return FileUploadDownloadUtility.CreateFileResponse(stream, csvname);
        }


        #endregion
    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// テーブル（ビュー）の項目＋更新区分を追加してアップロード・ダウンロードする場合に、追加する項目を定義します
    /// </summary>
    public class CSV_208_Model
    {
        public int cd_kaisha { get; set; }
        public int cd_kojyo { get; set; }
        public string m_kirikae { get; set; }
        public short id_system { get; set; }
        public short kbn_sort { get; set; }
        public short kansakujyoho { get; set; }
        public string cd_haigo { get; set; }
        public string flg_seiho_base { get; set; }
        public string no_seiho_kaisha { get; set; }
        public string no_seiho_shurui { get; set; }
        public string no_seiho_nen { get; set; }
        public string no_seiho_renban { get; set; }
        public string nm_haigo { get; set; }
        public string kbn_hin { get; set; }
        public string cd_bunrui { get; set; }
        public string nm_seihin { get; set; }
        public string flg_hinkan { get; set; }
        public string flg_seizo { get; set; }
        public string flg_genzai { get; set; }
        public string flg_mishiyo { get; set; }
        public string dt_hidzuke { get; set; }
        public string cd_haigo_parttwo { get; set; }
        public string cd_hin_parttwo { get; set; }
        public string cd_genryo { get; set; }
        public string kbn_hin_genryo { get; set; }
        public string nm_genryo_text { get; set; }
        public string cd_kikaku { get; set; }
        public string nm_kikaku_shohin { get; set; }
        public string nm_seiho_mei { get; set; }
        public string nm_hanbai_mei { get; set; }
    }
    public class ResultCSV_208
    {
        public string cd_haigo { get; set; }
        public string nm_haigo { get; set; }
        public string nm_haigo_r { get; set; }
        public string kbn_hin { get; set; }
        public string nm_kbn_hin { get; set; }
        public string cd_bunrui { get; set; }
        public string nm_bunrui_bunrui { get; set; }
        public double budomari { get; set; }
        public int qty_kihon { get; set; }
        public Nullable<double> ritsu_kihon { get; set; }
        public string cd_setsubi { get; set; }
        public string nm_setsubi_setsubi { get; set; }
        public int? flg_gasan { get; set; }
        public Nullable<double> qty_max { get; set; }
        public string nm_seiho { get; set; }
        public int no_han { get; set; }
        public int qty_haigo_h { get; set; }
        public Nullable<double> qty_haigo_kei { get; set; }
        public string biko { get; set; }
        public string no_seiho { get; set; }
        public string cd_tanto_seizo { get; set; }
        public string nm_tanto_seizo { get; set; }
        public Nullable<System.DateTime> dt_seizo { get; set; }
        public string cd_tanto_hinkan { get; set; }
        public string nm_tanto_hinkan { get; set; }
        public Nullable<System.DateTime> dt_hinkan { get; set; }
        public Nullable<System.DateTime> dt_from { get; set; }
        public Nullable<System.DateTime> dt_to { get; set; }
        public string kbn_vw { get; set; }
        public string nm_kbn_vw { get; set; }
        public Nullable<double> hijyu { get; set; }
        public int? flg_shorihin { get; set; }
        public int? flg_hinkan { get; set; }
        public int? flg_seizo { get; set; }
        public int? flg_sakujyo { get; set; }
        public int? flg_mishiyo { get; set; }
        public Nullable<System.DateTime> dt_toroku { get; set; }
        public Nullable<System.DateTime> dt_henko { get; set; }
        public string cd_koshin { get; set; }
        public string nm_tanto_koshin { get; set; }
        public int? kbn_shiagari { get; set; }
        public string cd_haigo_seiho { get; set; }
        public int? flg_seiho_base { get; set; }
    }
    #endregion

}
