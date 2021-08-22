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
    public class SetConfirmController : ControllerBase
    {
        [HttpPost]
        public object Post([FromBody] string value)
        {
            std value_obj = JsonConvert.DeserializeObject<std>(value);
            return Events.SetConfirm(value_obj);
        }
    }

    

}
