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
        public DbSet<Customer> Customer { get; set; }
        public DbSet<Common> Common { get; set; }
        public DbSet<User> User { get; set; }
        public DbSet<Roles> Roles { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Product>().HasKey(od => new { od.cd_product });
            modelBuilder.Entity<Order>().HasKey(od => new { od.cd_order  });
            modelBuilder.Entity<OrderDetail>().HasKey(od => new { od.cd_order, od.cd_order_detail });
            modelBuilder.Entity<Customer>().HasKey(od => new { od.cd_customer });
            modelBuilder.Entity<Common>().HasKey(od => new { od.cd_category, od.cd_common });
            modelBuilder.Entity<User>().HasKey(od => new { od.cd_user});
            modelBuilder.Entity<Roles>().HasKey(od => new { od.cd_role });
            base.OnModelCreating(modelBuilder);
        }
    }
}
