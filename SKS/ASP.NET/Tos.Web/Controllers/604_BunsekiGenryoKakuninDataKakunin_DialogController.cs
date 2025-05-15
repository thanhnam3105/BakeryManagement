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

namespace Tos.Web.Controllers
{
    //created from 【SearchInputController(Ver1.7)】 Template
    public class _604_BunsekiGenryoKakuninDataKakunin_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"
        private const string HaigoIsNull = "未登録";
        private const string GenryoIsNull = "原料は存在しません";
        
        public Object Post([FromBody] RequestParameter_BunsekiGenryoKakuninDataKakunin_Search parameter)
        {
            // TODO:target情報を管理しているDbContextとtargetの型を指定します。    

            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;

            List<tr_haigo> lst_tr_haigo = new List<tr_haigo>();
            lst_tr_haigo = parameter.data;
            var list1 =
                (
                    from haigo in lst_tr_haigo
                    join kaisha_temp in context.ma_kaisha on haigo.cd_kaisha equals kaisha_temp.cd_kaisha into kaisha_temp_
                    from kaisha in kaisha_temp_.DefaultIfEmpty()
                    join busho_temp in context.ma_busho on new { cd_kaisha = haigo.cd_kaisha, cd_busho = haigo.cd_busho } equals new { cd_kaisha = (int?)busho_temp.cd_kaisha, cd_busho = (int?)busho_temp.cd_busho } into busho_temp_
                    from busho in busho_temp_.DefaultIfEmpty()
                    where haigo.cd_genryo != "999999"
                    && haigo.cd_genryo != null
                    && haigo.cd_genryo != ""
                    select new
                    {
                        sort_kotei = haigo.sort_kotei,
                        cd_genryo = haigo.cd_genryo,
                        nm_kaisha = kaisha == null ? null :kaisha.nm_kaisha,
                        nm_busho = busho == null ? null : busho.nm_busho,
                        nm_genryo = haigo.nm_genryo,
                        ritu_abura = haigo.ritu_abura,
                        ritu_sakusan = haigo.ritu_sakusan,
                        ritu_shokuen = haigo.ritu_shokuen,
                        ritu_sousan = haigo.ritu_sousan,
                        ritu_msg = haigo.ritu_msg,
                        cd_kaisha = haigo.cd_kaisha,
                        cd_busho = haigo.cd_busho,
                        sort_genryo = haigo.sort_genryo
                    }
                ).ToList();

            var list2 = 
                (
                    from genryo in context.ma_genryo
                    join genryoKojyo_temp in context.ma_genryokojo
                    on new { cd_kaisha = genryo.cd_kaisha, cd_genryo = genryo.cd_genryo } equals new { cd_kaisha = genryoKojyo_temp.cd_kaisha, cd_genryo = genryoKojyo_temp.cd_genryo } into genryoKojyo_temp_
                    from genryoKojyo in genryoKojyo_temp_.DefaultIfEmpty()
                    select new 
                    {
                        cd_genryo = genryo.cd_genryo,
                        nm_genryo = genryoKojyo == null ? null: genryoKojyo.nm_genryo,
                        ritu_abura = genryo.ritu_abura,
                        ritu_sakusan = genryo.ritu_sakusan,
                        ritu_shokuen = genryo.ritu_shokuen,
                        ritu_sousan = genryo.ritu_sousan,
                        ritu_msg = genryo.ritu_msg,
                        cd_kaisha = genryo.cd_kaisha,
                        cd_busho = genryoKojyo == null ? null : (int?)genryoKojyo.cd_busho
                    }
                    
                ).ToList();

            var result =
                (
                    from a in list1
                    join b_temp in list2
                    on new { cd_busho = a.cd_busho, cd_kaisha = a.cd_kaisha, cd_genryo = a.cd_genryo } equals new { cd_busho = (int?)b_temp.cd_busho, cd_kaisha = (int?)b_temp.cd_kaisha, cd_genryo = b_temp.cd_genryo } into b_temp_
                    from b in b_temp_.DefaultIfEmpty()
                    where  b == null
                        || a.nm_genryo != b.nm_genryo
                        || a.ritu_abura != b.ritu_abura
                        || a.ritu_sakusan != b.ritu_sakusan
                        || a.ritu_shokuen != b.ritu_shokuen
                        || a.ritu_sousan != b.ritu_sousan
                        || a.ritu_msg != b.ritu_msg
                    orderby a.sort_kotei, a.sort_genryo
                    select new
                    {
                        sort_kotei = a.sort_kotei,
                        cd_genryo = a.cd_genryo,
                        nm_kaisha = a.nm_kaisha == null ? "" : a.nm_kaisha,
                        nm_busho = a.nm_busho == null ? "" : a.nm_busho,
                        nm_genryo = a.nm_genryo == null ? HaigoIsNull : a.nm_genryo,
                        ritu_abura = a.ritu_abura == null ? HaigoIsNull : ((decimal)a.ritu_abura).ToString("0.00"),
                        ritu_sakusan = a.ritu_sakusan == null ? HaigoIsNull : ((decimal)a.ritu_sakusan).ToString("0.00"),
                        ritu_shokuen = a.ritu_shokuen == null ? HaigoIsNull : ((decimal)a.ritu_shokuen).ToString("0.00"),
                        ritu_sousan = a.ritu_sousan == null ? HaigoIsNull : ((decimal)a.ritu_sousan).ToString("0.00"),
                        ritu_msg = a.ritu_msg == null ? HaigoIsNull : ((decimal)a.ritu_msg).ToString("0.00"),
                        nm_genryo1 = b == null ? GenryoIsNull : (b.nm_genryo == null ? GenryoIsNull : b.nm_genryo),
                        ritu_abura1 = b == null ? GenryoIsNull : (b.ritu_abura == null ? GenryoIsNull : ((decimal)b.ritu_abura).ToString("0.00")),
                        ritu_sakusan1 = b == null ? GenryoIsNull : (b.ritu_sakusan == null ? GenryoIsNull : ((decimal)b.ritu_sakusan).ToString("0.00")),
                        ritu_shokuen1 = b == null ? GenryoIsNull : (b.ritu_shokuen == null ? GenryoIsNull : ((decimal)b.ritu_shokuen).ToString("0.00")),
                        ritu_sousan1 = b == null ? GenryoIsNull : (b.ritu_sousan == null ? GenryoIsNull : ((decimal)b.ritu_sousan).ToString("0.00")),
                        ritu_msg1 = b == null ? GenryoIsNull : (b.ritu_msg == null ? GenryoIsNull : ((decimal)b.ritu_msg).ToString("0.00"))
                    }
                ).ToList();

            // TODO: 上記実装を行った後に下の行は削除します
            return result;
        }

        public class SearchGenryo
        {
            public decimal cd_shain { get; set; }
            public decimal nen { get; set; }
            public decimal no_oi { get; set; }
        }

        public class RequestParameter_BunsekiGenryoKakuninDataKakunin_Search
        {
            public List<tr_haigo> data { get; set; }
        }
        #endregion

    }
}
