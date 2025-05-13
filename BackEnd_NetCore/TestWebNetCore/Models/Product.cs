using System.ComponentModel.DataAnnotations;

namespace TestWebNetCore.Models
{
    public class Product
    {
        public int Id { get; set; }
        public string cake_id { get; set; }
        public string name { get; set; }
        public decimal price { get; set; }
        public string category { get; set; }
        public string stock { get; set; }
        public string size { get; set; }
        public string image { get; set; }
        public string status { get; set; }
    }
}
