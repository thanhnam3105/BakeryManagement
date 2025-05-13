using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TestWebNetCore.Data;
using TestWebNetCore.Models;

namespace MyApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductsController : ControllerBase
    {
        private readonly AppDbContext _context;

        public ProductsController(AppDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Product>>> GetProducts()
        {
            return await _context.Products.ToListAsync();
        }

        [HttpPost]
        public async Task<ActionResult<Product>> CreateProduct([FromBody] Product data)
        {
            _context.Products.Add(data);
            await _context.SaveChangesAsync();
            return CreatedAtAction(nameof(GetProducts), new { id = data.Id }, data);
        }

        [HttpPut]
        public async Task<ActionResult<Product>> UpdateProduct([FromBody] Product data)
        {
            _context.Products.Update(data);
            await _context.SaveChangesAsync();
            return CreatedAtAction(nameof(GetProducts), new { id = data.Id }, data);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteProduct(int id)
        {
            var Product = await _context.Products.FindAsync(id);
            if (Product == null) return NotFound();

            _context.Products.Remove(Product);
            await _context.SaveChangesAsync();

            return NoContent();
        }
    }
}
