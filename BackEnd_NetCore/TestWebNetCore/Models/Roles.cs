using System.ComponentModel.DataAnnotations;

namespace TestWebNetCore.Models
{
    public class Roles
    {
        public int cd_role { get; set; }
        public string nm_role { get; set; }
        public int cd_group { get; set; }
        public int flg_delete { get; set; }
        public string? cd_create { get; set; }
        public string? cd_update { get; set; }
        public Nullable<System.DateTime> dt_create { get; set; }
        public Nullable<System.DateTime> dt_update { get; set; }
    }
}
