using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Threading.Tasks;
using Google.Apis.Services;
using Google.Apis.YouTube.v3;



namespace Project1.Controllers.api
{
    [Route("api/[controller]")]
    [ApiController]
    public class YoutubeController : ControllerBase
    {

        // GET api/<DefaultController>/5
        [HttpGet()]
        public async Task<List<channel>> Get()
        {
            var youtubeService = new YouTubeService(new BaseClientService.Initializer()
            {
                ApiKey = "",
                ApplicationName = this.GetType().ToString()
            });

            var searchListRequest = youtubeService.Search.List("snippet");
            searchListRequest.Q = "Путшествие";
            searchListRequest.MaxResults = 50;
            searchListRequest.Type = "channel";
            searchListRequest.RegionCode = "ru";
            //searchListRequest.Order = SearchResource.ListRequest.OrderEnum.ViewCount;

            var searchListResponse = await searchListRequest.ExecuteAsync();


            List<channel> channels = new List<channel>();


            // Add each result to the appropriate list, and then display the lists of
            // matching videos, channels, and playlists.
            foreach (var searchResult in searchListResponse.Items)
            {
                channel ch = new channel() {
                    channelId = ""
                };
                channels.Add(ch);
                
            }

            return channels;
        }
        public class channel
        {
            public string channelId { get; set; }
            public string channelTitle { get; set; }
            public string description { get; set; }
            public string title { get; set; }
            public string icon { get; set; }

        }
    }
       
}
