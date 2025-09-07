//using Microsoft.AspNetCore.Authentication;
//using Microsoft.AspNetCore.Authentication.Cookies;
//using Microsoft.AspNetCore.Mvc;
//using System.Security.Claims;
//using TestWebNetCore.Models.RequestModels;

//namespace TestWebNetCore.Controller
//{
//    [Route("api/[controller]")]
//    [ApiController]
//    public class AuthenController : ControllerBase
//    {
//        [HttpGet("unauthorized")]
//        public IActionResult GetUnauthorized()
//        {
//            return Unauthorized();
//        }

//        [HttpPost("login")]
//        public async Task<ActionResult> LoginCookie([FromBody] UserRequest userRequest)
//        {
//            string roleName = string.Empty;

//            if (userRequest.UserName.Equals("User")) {
//                roleName = "User";
//            }
//            else if (userRequest.UserName.Equals("Admin"))
//            {
//                roleName = "Admin";
//            }
//            else
//            {
//                roleName = "Guest";
//            }

//            var claims = new List<Claim> {
//                new Claim(ClaimTypes.Name,userRequest.UserName),
//                new Claim(ClaimTypes.Role,roleName),
//            };

//            var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
//            var pricinpal = new ClaimsPrincipal(identity);

//            var authProperties = new AuthenticationProperties
//            {
//                AllowRefresh = true,
//                ExpiresUtc = DateTimeOffset.UtcNow.AddMinutes(1),
//                IsPersistent = true
//            };

//            await HttpContext.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, pricinpal, authProperties);

//            return Ok("Login success");
//        }

//        [HttpPost("logout")]
//        public async Task<IActionResult> Logout()
//        {
//            //await HttpContext.SignOutAsync("BakeryCookie");
//            await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
//            return Ok("Logout success");
//        }
//    }
//}
