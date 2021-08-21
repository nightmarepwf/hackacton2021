using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.IO;
using InstagramApiSharp.API;
using InstagramApiSharp.API.Builder;
using InstagramApiSharp.Classes;
using InstagramApiSharp.Logger;
using Project1.Models;
using Newtonsoft.Json;
using System.Net;
using System.Text;
// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Project1.Controllers.api
{

    [Route("api/[controller]")]
    [ApiController]
    public class InstagramController : ControllerBase
    {

        [HttpGet]
        public async Task<object> Get([FromBody] string value)
        {
            var user = JsonConvert.DeserializeObject<userDesc>(value)?.user;
            return JsonConvert.SerializeObject(await Instagram.GetUserFullInfoByName(user));
        }


        [HttpPost]
        public  object Post([FromBody] string value)
        {
            
            return  Instagram.ParseByWords(); 
            
        }
    }
    public class userDesc
    {
        public string user;
    }
}
