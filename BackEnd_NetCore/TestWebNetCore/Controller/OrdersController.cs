using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using TestWebNetCore.Data;
using TestWebNetCore.Models;

namespace MyApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class OrdersController : ControllerBase
    {
        private readonly AppDbContext _context;

        public OrdersController(AppDbContext context)
        {
            _context = context;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<Order>>> GetOrders()
        {
            return await _context.Orders.ToListAsync();
        }

        [HttpPost]
        public async Task<ActionResult<Order>> CreateOrder(Order order)
        {
            _context.Orders.Add(order);
            await _context.SaveChangesAsync();
            return CreatedAtAction(nameof(GetOrders), new { id = order.cd_order }, order);
        }

        [HttpPut]
        public async Task<ActionResult<Order>> UpdateOrder(Order order)
        {
            _context.Orders.Update(order);
            await _context.SaveChangesAsync();
            return CreatedAtAction(nameof(GetOrders), new { id = order.cd_order }, order);
        }

        //[HttpDelete("{id}")]
        //public async Task<IActionResult> DeleteOrder(int id)
        //{
        //    var order = await _context.Orders.FindAsync(id);
        //    if (order == null) return NotFound();

        //    _context.Orders.Remove(order);
        //    await _context.SaveChangesAsync();

        //    return NoContent();
        //}

        [HttpGet("GetOrderDetails")]
        public async Task<ActionResult<IEnumerable<OrderDetail>>> GetOrderDetails([FromQuery] string cd_order)
        {
            var details = await _context.OrderDetail
                .Where(d => d.cd_order == cd_order)
                .ToListAsync();

            //var data = from orderDetail in _context.OrderDetail
            //           join order in _context.Orders on orderDetail.cd_order equals order.cd_order
            //           where orderDetail.cd_order == cd_order
            //           select new
            //           {
            //               order.cd_order,
            //               order.cd_staff,
            //               order.cd_branch,
            //               order.cd_customer,
            //               order.cd_status,
            //           };

            if (details == null || details.Count == 0)
            {
                return NotFound();
            }

            return Ok(details);
        }


    }
}
