using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TestWebNetCore.Migrations
{
    /// <inheritdoc />
    public partial class QMS_V22 : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "payment_method",
                table: "Orders",
                newName: "cd_payment_method");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "cd_payment_method",
                table: "Orders",
                newName: "payment_method");
        }
    }
}
