using Microsoft.AspNetCore.Mvc;

using System.Threading.Tasks;
using Project1.Models;
using Microsoft.AspNetCore.Cors;
using Newtonsoft.Json;
// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Project1.Controllers.api
{


    [Route("api/[controller]")]
    [ApiController]
    public class EventController : ControllerBase
    {
        [HttpPost]
        public object Post([FromBody] string value)
        {
            evt value_obj = JsonConvert.DeserializeObject<evt>(value);
            return Events.GetList(value_obj.id);
        }
    }
    public class evt {
        public string id;
    }
    

}
