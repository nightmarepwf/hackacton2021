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
    public class EventsController : ControllerBase
    {
        [HttpGet]
        public object Get()
        {
            return Events.GetList();
        }

        [HttpPost]
        public object Post([FromBody] string value)
        {
            event_value value_obj = JsonConvert.DeserializeObject<event_value>(value);
            if (Events.CreateEvent(value_obj)) return Events.GetList();
            return null;
        }


    }
}
