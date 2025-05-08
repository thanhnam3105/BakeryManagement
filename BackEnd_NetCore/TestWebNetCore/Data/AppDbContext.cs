using Microsoft.EntityFrameworkCore;
using TestWebNetCore.Models;

namespace TestWebNetCore.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }
        public DbSet<Order> Orders { get; set; }
    }
}
