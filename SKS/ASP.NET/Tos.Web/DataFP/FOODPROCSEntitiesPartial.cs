namespace Tos.Web.DataFP
{
    using System;
    using System.Data.Entity;
    using System.Collections.Generic;
    using System.Linq;
    using System.Web;


    public partial class FOODPROCSEntities : DbContext
    {

        // 会社コードと工場コードを元にＦＰのEntityを作成する
        public FOODPROCSEntities(int kaishaCode, int kojoCode)
            : base("name=" + String.Format("{0:D4}", kaishaCode) + "_" + String.Format("{0:D4}", kojoCode) + "FPROCEntities")
        {
        }
    }
}