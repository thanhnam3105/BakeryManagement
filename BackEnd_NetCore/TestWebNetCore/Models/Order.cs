using System.ComponentModel.DataAnnotations;

namespace TestWebNetCore.Models
{
    public class Order
    {
        [Required]
        [Key]
        public string cd_order { get; set; }
        public string cd_customer { get; set; }
        public string cd_staff { get; set; }
        public string cd_branch { get; set; }
        public Nullable<System.DateTime> dt_order { get; set; }
        public Nullable<System.DateTime> dt_delivery { get; set; }
        public string? cd_status { get; set; }
        public decimal total_amount { get; set; }
        public string? delivery_address { get; set; }
        public string? cd_payment_method { get; set; }
        public string? cd_create { get; set; }
        public string? cd_update { get; set; }
        public Nullable<System.DateTime> dt_create { get; set; }
        public Nullable<System.DateTime> dt_update { get; set; }
    }
}
