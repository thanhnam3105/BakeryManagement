using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System.Text;
using TestWebNetCore.Data;
using TestWebNetCore.Hubs;

var builder = WebApplication.CreateBuilder(args);


//thiết lập Authentication JWT - START
// Đọc thông số JWT từ cấu hình
var jwtSettings = builder.Configuration.GetSection("JwtTEST");

builder.Services.AddAuthentication(options =>
{
    //dùng JWT
    options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
    options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
}).AddJwtBearer(options =>
{
    options.RequireHttpsMetadata = false;
    options.SaveToken = true;
    options.TokenValidationParameters = new TokenValidationParameters
    {
        ValidateIssuer = true,   // Bắt buộc kiểm tra Issuer
        ValidateAudience = true, // Bắt buộc kiểm tra Audience
        ValidateLifetime = true, // Kiểm tra thời hạn token (exp)
        ValidateIssuerSigningKey = true, // Kiểm tra chữ ký token
        ValidIssuer = jwtSettings["Issuer"],       // Đúng Issuer thì mới hợp lệ
        ValidAudience = jwtSettings["Audience"],   // Đúng Audience thì mới hợp lệ
        IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(jwtSettings["Key"]!)) // Key để verify chữ ký
    };
});
builder.Services.AddAuthorization();
//thiết lập Authentication JWT - END

//thiết lập Authentication cookie
//builder.Services.AddAuthentication(options =>
//{
//    options.DefaultAuthenticateScheme = CookieAuthenticationDefaults.AuthenticationScheme;
//    options.DefaultChallengeScheme = CookieAuthenticationDefaults.AuthenticationScheme;
//}).AddCookie(options =>
//{
//    //options.Cookie = new CookieBuilder {
//        //Name = "BakeryCookie"//,              //đổi tên cookie, ko đổi thì lấy tên mặc định
//        //Domain = "localhost"                //địa chỉ lưu cookie, ko thiết lập thì lấy mặc định theo địa chỉ hiện tại
//    //};
//    options.LoginPath = "/api/authen/unauthorized";
//    options.LogoutPath = "/api/authen/logout";
//});

// nam.pt - Cấu hình DbContext sử dụng PostgreSQL - START
// Bật CORS
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAll",
        policy => policy.AllowAnyOrigin()  // Cho phép tất cả các nguồn (frontend)
                        .AllowAnyMethod()  // Cho phép tất cả các phương thức (GET, POST, PUT, DELETE)
                        .AllowAnyHeader()); // Cho phép tất cả các headers
});
builder.Services.AddSignalR();
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddControllers();
// nam.pt - Cấu hình DbContext sử dụng PostgreSQL - END
// Add services to the container.
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();


var app = builder.Build();
app.UseCors("AllowAll"); // nam.pt Kích hoạt CORS
app.MapControllers(); // nam.pt
app.UseRouting();
// nếu có authentication
app.UseAuthentication();
// dòng này BẮT BUỘC khi bạn dùng [Authorize]
app.UseAuthorization();

app.UseEndpoints(endpoints => {
    endpoints.MapHub<ItemsHub>("/itemHub");
});
// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

var summaries = new[]
{
    "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
};

app.MapGet("/weatherforecast", () =>
{
    var forecast = Enumerable.Range(1, 5).Select(index =>
        new WeatherForecast
        (
            DateOnly.FromDateTime(DateTime.Now.AddDays(index)),
            Random.Shared.Next(-20, 55),
            summaries[Random.Shared.Next(summaries.Length)]
        ))
        .ToArray();
    return forecast;
})
.WithName("GetWeatherForecast")
.WithOpenApi();

app.Run();

internal record WeatherForecast(DateOnly Date, int TemperatureC, string? Summary)
{
    public int TemperatureF => 32 + (int)(TemperatureC / 0.5556);
}
