using System;
using System.Collections.Generic;
//using System.Web.Http.OData;
using System.Data.Common;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
//using System.Web.Http.OData.Query;
// TODO:DbContextの名前空間を指定します。
using Tos.Web.Data;

namespace Tos.Web.Controllers
{
    //created from 【HeaderDetailController(Ver1.7)】 Template
    public class ShishakuData_GenkaShisan_Tab_Controller : ApiController
    {
        #region "Controllerで公開するAPI"

        /// <summary>
        /// パラメータで受け渡されたheaderのキー項目をもとにheaderとdetailの情報を取得します。
        /// </summary>
        /// <param name="no_seq">headerのキー項目</param>
        /// <returns>GenkaShisanChangeResponse</returns>
        public GenkaShisanSearchResponse Get([FromUri] paramSearch paraSearch)
        {

            //return sampleItem;
            // TODO:header情報を管理しているDbContextとheader,detailの型を指定します。

            using (ShohinKaihatsuEntities context = new ShohinKaihatsuEntities())
            {
                context.Configuration.ProxyCreationEnabled = false;

                decimal cd_login = decimal.Parse(UserInfo.GetUserNameFromIdentity(this.User.Identity));
                GenkaShisanSearchResponse SearchResponse = new GenkaShisanSearchResponse();
                //get data genryo
                SearchResponse.genryo = (
                    //from genryo in (context.tr_genryo.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi))
                    //from sampl in context.tr_shisaku.Where(x => x.cd_shain == genryo.cd_shain && x.nen == genryo.nen && x.no_oi == genryo.no_oi && x.seq_shisaku == genryo.seq_shisaku).DefaultIfEmpty()
                    from sampl in context.tr_shisaku.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi)
                    from genryo in context.tr_genryo.Where(x => x.cd_shain == sampl.cd_shain && x.nen == sampl.nen && x.no_oi == sampl.no_oi && x.seq_shisaku == sampl.seq_shisaku).DefaultIfEmpty()
                    select new tr_genryo_new
                    {
                        cd_shain = sampl.cd_shain,
                        nen = sampl.nen,
                        no_oi = sampl.no_oi,
                        seq_shisaku = sampl.seq_shisaku,
                        isNew = genryo.seq_shisaku == null,
                        nm_sample = sampl.nm_sample ?? "",
                        sort_shisaku = sampl.sort_shisaku,
                        flg_shisanIrai = sampl.flg_shisanIrai,
                        flg_print = genryo.flg_print,
                        zyusui = genryo.zyusui,
                        zyuabura = genryo.zyuabura,
                        gokei = genryo.gokei,
                        genryohi = genryo.genryohi,
                        genryohi1 = genryo.genryohi1,
                        hiju = genryo.hiju,
                        hiju_sui = sampl.hiju_sui,
                        yoryo = genryo.yoryo,
                        irisu = genryo.irisu,
                        yukobudomari = genryo.yukobudomari,
                        reberu = genryo.reberu,
                        hizyubudomari = genryo.hizyubudomari,
                        heikinzyu = genryo.heikinzyu,
                        cs_genryo = genryo.cs_genryo,
                        cs_zairyohi = genryo.cs_zairyohi,
                        cs_keihi = genryo.cs_keihi,
                        cs_genka = genryo.cs_genka,
                        ko_genka = genryo.ko_genka,
                        ko_baika = genryo.ko_baika,
                        ko_riritu = genryo.ko_riritu,
                        id_toroku = genryo.id_toroku == null ? cd_login : genryo.id_toroku,
                        dt_toroku = genryo.dt_toroku == null ? DateTime.Today : genryo.dt_toroku,
                        id_koshin = genryo.id_koshin == null ? cd_login : genryo.id_koshin,
                        dt_koshin = genryo.dt_koshin == null ? DateTime.Today : genryo.dt_koshin,
                        ts = genryo.ts
                    }).OrderBy(x => x.sort_shisaku).ToList();

                //原価原料テーブルにデータがなくてもサンプルNoの列は表示されるようになっています。
                var shisahin = context.tr_shisakuhin.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi).FirstOrDefault();
                foreach (var item in SearchResponse.genryo)
                {
                    if (item.isNew == true)
                    {
                        item.zyusui = "0.00";
                        item.zyuabura = "0.00";
                        item.yoryo = shisahin.yoryo;
                        item.irisu = shisahin.su_iri;
                        item.gokei = "0.000";
                        item.genryohi = "0";
                        item.genryohi1 = "0.00";
                        item.hiju = context.tr_shisaku.Where(x => x.cd_shain == paraSearch.cd_shain && x.nen == paraSearch.nen && x.no_oi == paraSearch.no_oi).FirstOrDefault().hiju;
                        item.hizyubudomari = "0.00";
                        item.cs_genryo = "0.00";
                        item.ko_baika = shisahin.baika;
                        item.flg_print = 0;
                    }
                }

                // TODO: 上記実装を行った後に下の行は削除します
                return SearchResponse;
            }
        }

        #endregion

    }

    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    /// <summary>
    /// 試算確定サンプルＮｏ
    /// </summary>
    public class GenkaShisanSearchResponse
    {
        public GenkaShisanSearchResponse()
        {
            this.genryo = new List<tr_genryo_new>();
        }
        public List<tr_genryo_new> genryo { get; set; }
    }

    /// <summary>
    /// new genryo class 
    /// </summary>
    public class tr_genryo_new : tr_genryo
    {
        public string nm_sample { get; set; }
        public string hiju_sui { get; set; }
        public Nullable<short> sort_shisaku { get; set; }
        public Nullable<short> flg_shisanIrai { get; set; }
        public bool isNew { get; set; }
    }

    ///// <summary>
    ///// param search from screent
    ///// </summary>
    //public class kakutePara
    //{
    //    public decimal cd_shain { get; set; }
    //    public decimal nen { get; set; }
    //    public decimal no_oi { get; set; }
    //    public short seq_shisaku { get; set; }
    //    public string nm_sample { get; set; }
    //    public decimal EmployeeCD { get; set; }
    //}

    #endregion
}
