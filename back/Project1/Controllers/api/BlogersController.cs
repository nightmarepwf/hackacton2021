using Microsoft.AspNetCore.Mvc;

using System.Threading.Tasks;
using Project1.Models;
using Newtonsoft.Json;
// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Project1.Controllers.api
{

    [Route("api/[controller]")]
    [ApiController]
    public class BlogersController : ControllerBase
    {
        [HttpGet]
        public object Get()
        {
            return Blogers.GetList();
        }

        [HttpPost]
        public async Task<object> Post([FromBody] string value)
        {
            bloger_value obj = JsonConvert.DeserializeObject<bloger_value>(value);
            if (await Blogers.CreateEvent(obj)) return Blogers.GetList();
            return null;
        }

    }
}
