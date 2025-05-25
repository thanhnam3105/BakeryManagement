using Microsoft.EntityFrameworkCore;
using TestWebNetCore.Models;

namespace TestWebNetCore.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }
        public DbSet<Order> Orders { get; set; }
        public DbSet<Product> Products { get; set; }
        public DbSet<OrderDetail> OrderDetail { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Product>().HasKey(od => new { od.cd_product });
            modelBuilder.Entity<Order>().HasKey(od => new { od.cd_order  });
            modelBuilder.Entity<OrderDetail>().HasKey(od => new { od.cd_order, od.cd_order_detail });

            base.OnModelCreating(modelBuilder);
        }
    }
}
