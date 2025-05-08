using System.ComponentModel.DataAnnotations;

namespace TestWebNetCore.Models
{
    public class Order
    {
        public int Id { get; set; }
        public string order_id { get; set; }
        public string customer_id { get; set; }
        public string staff_id { get; set; }
        public string branch_id { get; set; }
        public DateTime order_date { get; set; }
        public DateTime delivery_date { get; set; }
        public string status { get; set; }
        public decimal total_amount { get; set; }
        public string delivery_address { get; set; }
        public string payment_method { get; set; }
    }
}
