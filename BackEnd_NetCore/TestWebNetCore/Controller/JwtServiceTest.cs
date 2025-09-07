//using Microsoft.EntityFrameworkCore;
//using Microsoft.Extensions.Configuration;
//using Microsoft.IdentityModel.Tokens;
//using System.IdentityModel.Tokens.Jwt;
//using System.Security.Claims;
//using System.Text;
//using TestWebNetCore.Data;
//using TestWebNetCore.Models.RequestModels;

//namespace TestWebNetCore.Controller
//{
//    public class JwtService
//    {
//        private readonly AppDbContext _dbContext;
//        private readonly IConfiguration _configuration;
//        public JwtService(AppDbContext dbContext, IConfiguration configuration)
//        {
//            _configuration = configuration;
//            _dbContext = dbContext;
//        }

//        public async Task<UserRequest?> Authenticate(UserRequest request)
//        {
//            if (string.IsNullOrWhiteSpace(request.UserName) || string.IsNullOrWhiteSpace(request.AccessToken))
//            { return null; }

//            var userAccount = await _dbContext.User.FirstOrDefaultAsync(x => x.cd_user == request.UserName);
//            if (userAccount == null) return null;

//            var issuer = _configuration["JwtConfig:Issuer"];
//            var audience = _configuration["JwtConfig:Audience"];
//            var key = _configuration["JwtConfig:Key"];
//            var tokenValidityMins = _configuration.GetValue<int>("JwtConfig:TokenValidityMins");
//            var tokenExpiryTimeStamp = DateTime.UtcNow.AddMinutes(tokenValidityMins);


//            var tokenDescriptor = new SecurityTokenDescriptor
//            {
//                Subject = new ClaimsIdentity(new[]
//                {
//                    new Claim(JwtRegisteredClaimNames.Name, request.UserName)
//                }),
//                Expires = tokenExpiryTimeStamp,
//                Issuer = issuer,
//                Audience = audience,
//                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(Encoding.ASCII.GetBytes(key)),
//                    SecurityAlgorithms.HmacSha512Signature)
//            };

//            var tokenHander = new JwtSecurityTokenHandler();
//            var securityToken = tokenHander.CreateToken(tokenDescriptor);
//            var accessToken = tokenHander.WriteToken(securityToken);

//            return new UserRequest
//            {
//                AccessToken = accessToken,
//                UserName = request.UserName,
//                ExpiresIn = (int)tokenExpiryTimeStamp.Subtract(DateTime.UtcNow).TotalSeconds
//            };
//        }
//    }


//}
