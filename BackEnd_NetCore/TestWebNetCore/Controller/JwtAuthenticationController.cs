using Microsoft.AspNetCore.Identity.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using TestWebNetCore.Data;
using TestWebNetCore.Models;

namespace TestWebNetCore.Controller
{
    public class JwtAuthenticationController : ControllerBase
    {
        private readonly IConfiguration _configuration;

        private readonly AppDbContext _dbContext;
        public JwtAuthenticationController(AppDbContext dbContext, IConfiguration configuration)
        {
            _configuration = configuration;
            _dbContext = dbContext;
        }

        // api Login
        [HttpPost("login")]
        public IActionResult Login([FromBody] LoginRequest request)
        {
            var userAccount = (from user in _dbContext.User
                               join role in _dbContext.Roles on user.cd_role equals role.cd_role
                               where user.cd_user == request.Username && user.password == request.Password
                               select new
                               {
                                   Username = user.nm_user,
                                   Roles = user.cd_role.ToString()
                               }).ToList();

            if (userAccount.Count() == 0)
            {
                return Unauthorized();
            }

            request.Roles = userAccount.Select(n => n.Roles).ToList();
            var token = GenerateJwtToken(request);
            return Ok(new { Token = token });
        }

        private string GenerateJwtToken(LoginRequest userLogin)
        {
            var jwtSettings = _configuration.GetSection("JwtConfig");
            var key = Encoding.UTF8.GetBytes(jwtSettings["Key"]!);

            // Ví dụ: user có nhiều quyền
            //var roles = new List<string> { "Admin", "Manager" };
            var roles = userLogin.Roles;

            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.Name, userLogin.Username)
                //,new Claim(ClaimTypes.Role, userLogin.Roles) // trường hợp chỉ 1 user chỉ có 1 role duy nhất
            };

            // Thêm nhiều role claim
            claims.AddRange(roles.Select(role => new Claim(ClaimTypes.Role, role)));

            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(claims),
                Expires = DateTime.UtcNow.AddMinutes(Convert.ToDouble(jwtSettings["DurationInMinutes"])),
                Issuer = jwtSettings["Issuer"],
                Audience = jwtSettings["Audience"],
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature)
            };

            var tokenHandler = new JwtSecurityTokenHandler();
            var token = tokenHandler.CreateToken(tokenDescriptor);
            return tokenHandler.WriteToken(token);
        }
    }
    public class LoginRequest
    {
        public string Username { get; set; }
        public string Password { get; set; }
        public List<string> Roles { get; set; }
    }
}
