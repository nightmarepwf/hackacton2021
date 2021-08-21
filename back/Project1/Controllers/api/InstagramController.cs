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
// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Project1.Controllers.api
{

    [Route("api/[controller]")]
    [ApiController]
    public class InstagramController : ControllerBase
    {
        

        [HttpGet]
        public async Task<bool> Get()
        {
            return await Instagram.GetUserFullInfoByName("noch4009");
        }




        //[HttpPost]
        //public void Post([FromBody] string value)
        //{
        //}

        //// PUT api/<DefaultController>/5
        //[HttpPut("{id}")]
        //public void Put(int id, [FromBody] string value)
        //{
        //}

        //// DELETE api/<DefaultController>/5
        //[HttpDelete("{id}")]
        //public void Delete(int id)
        //{
        //}
    }
}
