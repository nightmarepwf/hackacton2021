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
            var user = JsonConvert.DeserializeObject<userDesc>(value)?.user ;
            return JsonConvert.SerializeObject( await Instagram.GetUserFullInfoByName(user));
        }


        [HttpPost]
        public async Task<object> Post([FromBody] string value)
        {
         
            var request = (HttpWebRequest)WebRequest.Create("https://www.instagram.com/");
            request.CookieContainer = new CookieContainer();

            using (var response = (HttpWebResponse)request.GetResponse())
            {
                // Print the properties of each cookie.
                foreach (Cookie cook in response.Cookies)
                {
                    Console.WriteLine("Cookie:");
                    Console.WriteLine($"{cook.Name} = {cook.Value}");
                    Console.WriteLine($"Domain: {cook.Domain}");
                    Console.WriteLine($"Path: {cook.Path}");
                    Console.WriteLine($"Port: {cook.Port}");
                    Console.WriteLine($"Secure: {cook.Secure}");

                    Console.WriteLine($"When issued: {cook.TimeStamp}");
                    Console.WriteLine($"Expires: {cook.Expires} (expired? {cook.Expired})");
                    Console.WriteLine($"Don't save: {cook.Discard}");
                    Console.WriteLine($"Comment: {cook.Comment}");
                    Console.WriteLine($"Uri for comments: {cook.CommentUri}");
                    Console.WriteLine($"Version: RFC {(cook.Version == 1 ? 2109 : 2965)}");

                    // Show the string representation of the cookie.
                    Console.WriteLine($"String: {cook}");
                }
            }
            return null;
        }
    }
    public class userDesc
    {
        public string user;
    }
}
