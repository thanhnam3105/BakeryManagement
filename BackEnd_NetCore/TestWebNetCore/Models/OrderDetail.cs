using System.ComponentModel.DataAnnotations;

namespace TestWebNetCore.Models
{
    public class OrderDetail
    {
        public string cd_order { get; set; }
        public string cd_order_detail { get; set; }
        public string? cd_product { get; set; }
        public decimal? quantity { get; set; }
        public string? cd_unit { get; set; }
    }
}
