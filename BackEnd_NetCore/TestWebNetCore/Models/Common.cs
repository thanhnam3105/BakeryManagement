using System.ComponentModel.DataAnnotations;

namespace TestWebNetCore.Models
{
    public class Common
    {
        public string cd_category { get; set; }
        public string cd_common { get; set; }
        public string? nm_common { get; set; }
        public bool? flg_use { get; set; }
        public string? cd_create { get; set; }
        public string? cd_update { get; set; }
        public Nullable<System.DateTime> dt_create { get; set; }
        public Nullable<System.DateTime> dt_update { get; set; }
    }
}
