using System.ComponentModel.DataAnnotations;

namespace TestWebNetCore.Models
{
    public class Product
    {
        [Required]
        [Key]
        public string cd_product { get; set; }
        public string nm_product { get; set; }
        public Nullable<decimal> price { get; set; }
        public string? cd_category { get; set; }
        public string? cd_size { get; set; }
        public string? image { get; set; }
        public string? cd_status { get; set; }
        public string? cd_create { get; set; }
        public string? cd_update { get; set; }
        public Nullable<System.DateTime> dt_create { get; set; }
        public Nullable<System.DateTime> dt_update { get; set; }
    }
}
