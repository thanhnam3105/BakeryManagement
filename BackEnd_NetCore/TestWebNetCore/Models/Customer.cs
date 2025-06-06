﻿using System.ComponentModel.DataAnnotations;

namespace TestWebNetCore.Models
{
    public class Customer
    {
        public string cd_customer { get; set; }
        public string nm_customer { get; set; }
        public string? phone { get; set; }
        public string? email { get; set; }
        public string? address { get; set; }
        public decimal? points { get; set; }
        public string? cd_create { get; set; }
        public string? cd_update { get; set; }
        public Nullable<System.DateTime> dt_create { get; set; }
        public Nullable<System.DateTime> dt_update { get; set; }
    }
}
