using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TestWebNetCore.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate3 : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "unit",
                table: "OrderDetail",
                newName: "cd_unit");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "cd_unit",
                table: "OrderDetail",
                newName: "unit");
        }
    }
}
