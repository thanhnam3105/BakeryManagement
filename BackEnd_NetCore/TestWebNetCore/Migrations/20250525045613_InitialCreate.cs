using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TestWebNetCore.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "OrderDetail",
                columns: table => new
                {
                    cd_order = table.Column<string>(type: "text", nullable: false),
                    cd_order_detail = table.Column<string>(type: "text", nullable: false),
                    cd_product = table.Column<string>(type: "text", nullable: true),
                    quantity = table.Column<decimal>(type: "numeric", nullable: true),
                    unit = table.Column<decimal>(type: "numeric", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_OrderDetail", x => new { x.cd_order, x.cd_order_detail });
                });

            migrationBuilder.CreateTable(
                name: "Orders",
                columns: table => new
                {
                    cd_order = table.Column<string>(type: "text", nullable: false),
                    cd_customer = table.Column<string>(type: "text", nullable: true),
                    cd_staff = table.Column<string>(type: "text", nullable: true),
                    cd_branch = table.Column<string>(type: "text", nullable: true),
                    cd_status = table.Column<string>(type: "text", nullable: true),
                    cd_payment_method = table.Column<string>(type: "text", nullable: true),
                    dt_order = table.Column<DateTime>(type: "timestamp with time zone", nullable: true),
                    dt_delivery = table.Column<DateTime>(type: "timestamp with time zone", nullable: true),
                    total_amount = table.Column<decimal>(type: "numeric", nullable: true),
                    delivery_address = table.Column<string>(type: "text", nullable: true),
                    cd_create = table.Column<string>(type: "text", nullable: true),
                    cd_update = table.Column<string>(type: "text", nullable: true),
                    dt_create = table.Column<DateTime>(type: "timestamp with time zone", nullable: true),
                    dt_update = table.Column<DateTime>(type: "timestamp with time zone", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Orders", x => x.cd_order);
                });

            migrationBuilder.CreateTable(
                name: "Products",
                columns: table => new
                {
                    cd_product = table.Column<string>(type: "text", nullable: false),
                    nm_product = table.Column<string>(type: "text", nullable: false),
                    price = table.Column<decimal>(type: "numeric", nullable: true),
                    cd_category = table.Column<string>(type: "text", nullable: true),
                    cd_size = table.Column<string>(type: "text", nullable: true),
                    image = table.Column<string>(type: "text", nullable: true),
                    description = table.Column<string>(type: "text", nullable: true),
                    cd_status = table.Column<string>(type: "text", nullable: true),
                    cd_create = table.Column<string>(type: "text", nullable: true),
                    cd_update = table.Column<string>(type: "text", nullable: true),
                    dt_create = table.Column<DateTime>(type: "timestamp with time zone", nullable: true),
                    dt_update = table.Column<DateTime>(type: "timestamp with time zone", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Products", x => x.cd_product);
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "OrderDetail");

            migrationBuilder.DropTable(
                name: "Orders");

            migrationBuilder.DropTable(
                name: "Products");
        }
    }
}
