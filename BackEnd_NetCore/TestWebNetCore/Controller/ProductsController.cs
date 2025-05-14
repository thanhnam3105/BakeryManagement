using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json.Linq;
using System.Data.Common;
using System.Net;
using TestWebNetCore.Data;
using TestWebNetCore.Models;
using TestWebNetCore.Utilities;

namespace MyApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductsController : ControllerBase
    {
        private readonly ILogger<ProductsController> _logger;
        private readonly AppDbContext _context;

        public ProductsController(AppDbContext context, ILogger<ProductsController> logger)
        {
            _context = context;
            _logger = logger;

        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Product>>> GetProducts()
        {
            return await _context.Products.ToListAsync();
        }

        [HttpPost]
        public async Task<IActionResult> CreateProduct([FromBody] Product values)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            await using var transaction = await _context.Database.BeginTransactionAsync();

            try
            {
                _context.Products.Add(values);
                await _context.SaveChangesAsync();

                // TODO: Add any additional related logic here, e.g., logging, audit trails, etc.

                await transaction.CommitAsync();

                return CreatedAtAction(nameof(GetProducts), new { id = values.cd_product }, values);
            }
            catch (DbUpdateException dbEx)
            {
                await transaction.RollbackAsync();
                // Log technical exception details here (use ILogger)
                _logger.LogError(dbEx, "Database update failed while creating product.");
                return StatusCode(StatusCodes.Status500InternalServerError, "A database error occurred while creating the product.");
            }
            catch (Exception ex)
            {
                await transaction.RollbackAsync();
                // Log unexpected exception
                _logger.LogError(ex, "Unexpected error occurred while creating product.");
                return StatusCode(StatusCodes.Status500InternalServerError, "An unexpected error occurred.");
            }
        }


        [HttpPut]
        public async Task<ActionResult<Product>> UpdateProduct([FromBody] Product data)
        {
            _context.Products.Update(data);
            await _context.SaveChangesAsync();
            return CreatedAtAction(nameof(GetProducts), new { id = data.cd_product }, data);
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
