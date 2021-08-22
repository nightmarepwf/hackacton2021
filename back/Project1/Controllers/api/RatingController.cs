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
    public class RatingController : ControllerBase
    {
        [HttpPost]
        public object Post([FromBody] string value)
        {
            rt value_obj = JsonConvert.DeserializeObject<rt>(value);
            return Rating.UpdateRating(value_obj);
        }
    }


}
