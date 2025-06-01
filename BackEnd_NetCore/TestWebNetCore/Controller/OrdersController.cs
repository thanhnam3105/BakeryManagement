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
            var data = await (from order in _context.Orders

                              join customer in _context.Customer on order.cd_customer equals customer.cd_customer into leftCustomer
                              from customer in leftCustomer.DefaultIfEmpty()

                              join status in _context.Common on new { cd_status = order.cd_status, cd_category = "01" }
                                    equals new { cd_status = status.cd_common, cd_category = status.cd_category } into leftStatus
                              from status in leftStatus.DefaultIfEmpty()

                              join payment in _context.Common on new { cd_payment_method = order.cd_payment_method, cd_category = "02" }
                                    equals new { cd_payment_method = payment.cd_common, cd_category = payment.cd_category } into leftPayment
                              from payment in leftPayment.DefaultIfEmpty()

                              orderby order.cd_order descending
                              select new
                              {
                                  order.cd_order,
                                  order.cd_staff,
                                  order.cd_branch,
                                  order.cd_customer,
                                  order.cd_status,
                                  order.cd_payment_method,
                                  order.delivery_address,
                                  order.notes,
                                  order.dt_delivery,
                                  order.dt_order,
                                  order.dt_create,
                                  order.cd_create,
                                  order.cd_update,
                                  order.dt_update,

                                  total_amount = order.total_amount,

                                  nm_status = status.nm_common,
                                  nm_payment = payment.nm_common,

                                  customer.nm_customer
                              }).ToListAsync();
            return Ok(data);
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

        private async Task<object> GetDataOrder(string cdOrder)
        {
            var totalAmount = _context.OrderDetail.Where(n => n.cd_order == cdOrder).Sum(d => d.quantity * d.price);

            var data = await (from orderDetail in _context.OrderDetail

                              join order in _context.Orders on orderDetail.cd_order equals order.cd_order

                              join customer in _context.Customer on order.cd_customer equals customer.cd_customer into leftCustomer
                              from customer in leftCustomer.DefaultIfEmpty()

                              join user in _context.User on order.cd_staff equals user.cd_user into leftUser
                              from user in leftUser.DefaultIfEmpty()

                              join status in _context.Common on new { cd_status = order.cd_status, cd_category = "01" }
                                 equals new { cd_status = status.cd_common, cd_category = status.cd_category } into leftStatus
                              from status in leftStatus.DefaultIfEmpty()

                              join payment in _context.Common on new { cd_payment_method = order.cd_payment_method, cd_category = "02" }
                                 equals new { cd_payment_method = payment.cd_common, cd_category = payment.cd_category } into leftPayment
                              from payment in leftPayment.DefaultIfEmpty()

                              join unit in _context.Common on new { cd_unit = orderDetail.cd_unit, cd_category = "03" }
                                 equals new { cd_unit = unit.cd_common, cd_category = unit.cd_category } into leftUnit
                              from unit in leftUnit.DefaultIfEmpty()

                              join product in _context.Products on orderDetail.cd_product equals product.cd_product

                              where orderDetail.cd_order == cdOrder
                              orderby orderDetail.cd_product
                              select new
                              {
                                  order.cd_order,
                                  order.cd_staff,
                                  order.cd_branch,
                                  order.cd_customer,
                                  order.cd_status,
                                  order.delivery_address,
                                  total_amount = totalAmount,
                                  order.notes,
                                  order.dt_delivery,
                                  order.dt_order,
                                  order.dt_create,
                                  order.cd_create,
                                  order.cd_update,
                                  order.dt_update,

                                  nm_staff = user.nm_user,

                                  orderDetail.cd_product,
                                  orderDetail.price,
                                  orderDetail.quantity,
                                  total = orderDetail.quantity * orderDetail.price,

                                  product.nm_product,
                                  product.image,

                                  nm_unit = unit.nm_common,
                                  nm_quatity_unit = orderDetail.quantity.ToString() + " (" + unit.nm_common + ")",
                                  nm_status = status.nm_common,
                                  nm_payment = payment.nm_common,

                                  customer.nm_customer
                              }).ToListAsync();

            return data;
        }

        [HttpGet("GetOrderDetails")]
        public async Task<ActionResult<IEnumerable<OrderDetail>>> GetOrderDetails([FromQuery] string cd_order)
        {
            var totalAmount = _context.OrderDetail.Where(n => n.cd_order == cd_order).Sum(d => d.quantity * d.price);

            var details = await (from orderDetail in _context.OrderDetail

                                 join order in _context.Orders on orderDetail.cd_order equals order.cd_order

                                 join customer in _context.Customer on order.cd_customer equals customer.cd_customer into leftCustomer
                                 from customer in leftCustomer.DefaultIfEmpty()

                                 join user in _context.User on order.cd_staff equals user.cd_user into leftUser
                                 from user in leftUser.DefaultIfEmpty()

                                 join status in _context.Common on new { cd_status = order.cd_status, cd_category = "01" }
                                    equals new { cd_status = status.cd_common, cd_category = status.cd_category } into leftStatus
                                 from status in leftStatus.DefaultIfEmpty()

                                 join payment in _context.Common on new { cd_payment_method = order.cd_payment_method, cd_category = "02" }
                                    equals new { cd_payment_method = payment.cd_common, cd_category = payment.cd_category } into leftPayment
                                 from payment in leftPayment.DefaultIfEmpty()

                                 join unit in _context.Common on new { cd_unit = orderDetail.cd_unit, cd_category = "03" }
                                    equals new { cd_unit = unit.cd_common, cd_category = unit.cd_category } into leftUnit
                                 from unit in leftUnit.DefaultIfEmpty()

                                 join product in _context.Products on orderDetail.cd_product equals product.cd_product

                                 where orderDetail.cd_order == cd_order
                                 orderby orderDetail.cd_product
                                 select new
                                 {
                                     order.cd_order,
                                     order.cd_staff,
                                     order.cd_branch,
                                     order.cd_customer,
                                     order.cd_status,
                                     order.delivery_address,
                                     total_amount = totalAmount,
                                     order.notes,
                                     order.dt_delivery,
                                     order.dt_order,
                                     order.dt_create,
                                     order.cd_create,
                                     order.cd_update,
                                     order.dt_update,

                                     nm_staff = user.nm_user,

                                     orderDetail.cd_product,
                                     orderDetail.price,
                                     orderDetail.quantity,
                                     total = orderDetail.quantity * orderDetail.price,

                                     product.nm_product,
                                     product.image,

                                     nm_unit = unit.nm_common,
                                     nm_quatity_unit = orderDetail.quantity.ToString() + " (" + unit.nm_common + ")",
                                     nm_status = status.nm_common,
                                     nm_payment = payment.nm_common,

                                     customer.nm_customer
                                 }).ToListAsync();

            if (details == null || details.Count == 0)
            {
                return NotFound();
            }

            return Ok(details);
        }


    }
}
