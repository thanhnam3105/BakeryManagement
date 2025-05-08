using Microsoft.AspNetCore.SignalR;
using System.Threading.Tasks;

namespace TestWebNetCore.Hubs
{
    public class ItemsHub : Hub
    {
        public async Task SendItemUpdate(string message)
        {
            await Clients.All.SendAsync("ReceiveItemUpdate", message);
        }
    }
}
