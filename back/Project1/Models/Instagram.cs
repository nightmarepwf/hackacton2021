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
using Microsoft.Data.SqlClient;
using Project1.Data;
using InstagramApiSharp;
using System.Net;
using System.Text;

namespace Project1.Models
{
    public class Instagram
    {
        private static IInstaApi InstaApi;
        private static UserSessionData InstUser()
        {

     
            var result = DataProvider.executeProcedure("dbo.get_su_inst")?.Tables;
            return (result.Count > 0) ?
                 new UserSessionData
                 {
                     UserName = result?[0]?.Rows?[0]?["user_login"]?.ToString(),
                     Password = result?[0]?.Rows?[0]?["user_pwd"]?.ToString(),
                 }
                : null;

        }

        public static async Task<bool> Auth()
        {
            var userSession = InstUser();
            var delay = RequestDelay.FromSeconds(2, 2);
            InstaApi = InstaApiBuilder.CreateBuilder()
                .SetUser(userSession)
                .UseLogger(new DebugLogger(LogLevel.All)) // use logger for requests and debug messages
                .SetRequestDelay(delay)
                .Build();

            const string stateFile = "state.bin";
            try
            {
                if (System.IO.File.Exists(stateFile))
                {
                    Console.WriteLine("Loading state from file");
                    using (var fs = System.IO.File.OpenRead(stateFile))
                    {
                        InstaApi.LoadStateDataFromString(new StreamReader(fs).ReadToEnd());

                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            try
            {
                if (!InstaApi.IsUserAuthenticated)
                {
                    // login
                    Console.WriteLine($"Logging in as {userSession.UserName}");
                    delay.Disable();
                    var logInResult = await InstaApi.LoginAsync();
                    delay.Enable();
                    if (!logInResult.Succeeded)
                    {
                        Console.WriteLine($"Unable to login: {logInResult.Info.Message}");
                        return false;
                    }
                }
                var state = InstaApi.GetStateDataAsString();
                //var recipientsResult = await InstaApi.MessagingProcessor.GetRankedRecipientsAsync();
                //if (!recipientsResult.Succeeded)
                //{
                //    return false;
                //}

                //foreach (var thread in recipientsResult.Value.Threads)
                //    Console.WriteLine($"Threadname: {thread.ThreadTitle}, users: {thread.Users.Count}");

                //var inboxThreads = await InstaApi.MessagingProcessor.GetDirectInboxAsync(InstagramApiSharp.PaginationParameters.MaxPagesToLoad(1));
                //if (!inboxThreads.Succeeded)
                //{
                //    return false;
                //}
                //Console.WriteLine($"Got {inboxThreads.Value.Inbox.Threads.Count} inbox threads");
                //foreach (var thread in inboxThreads.Value.Inbox.Threads)
                //    Console.WriteLine($"Threadname: {thread.Title}, users: {thread.Users.Count}");
                //var firstThread = inboxThreads.Value.Inbox.Threads.FirstOrDefault();
                // send message to specific thread
                //var sendMessageResult = await InstaApi.MessagingProcessor.SendDirectTextAsync($"{firstThread.Users.FirstOrDefault()?.Pk}",
                //    firstThread.ThreadId, "test");
                //Console.WriteLine(sendMessageResult.Succeeded ? "Message sent" : "Unable to send message");

               

            }
            catch (Exception )
            {
                return false;
            }
            return true;
        }

        public static async Task<bool> ParseFollowers()
        {
            var auth = await Auth();
            if (!auth) return false;
            try
            {

                var userFollowers = await InstaApi.UserProcessor
                            .GetUserFollowersAsync("noch4009", PaginationParameters.MaxPagesToLoad(1));
               
            }
            catch (Exception)
            {

                return false;
            }
            return true;

        }

        public static async Task<object> GetUserFullInfoByName(string name)
        {
            var auth = await Auth();
            if (!auth) return false;
            try
            {
                var instUser = await InstaApi.UserProcessor.GetUserInfoByUsernameAsync(name);
                return instUser;
            }
            catch (Exception )
            {
                return false;
            }


        }
        public static async Task<bool> SendMessage(string name, string msg)
        {
            var auth = await Auth();
            if (!auth) return false;
            try
            {
                var instUser = await InstaApi.UserProcessor.GetUserInfoByUsernameAsync(name);
                var sendMessageResult = await InstaApi.MessagingProcessor.SendDirectTextAsync($"{instUser?.Value?.Pk}", string.Empty, msg);
            }
            catch (Exception )
            {
                return false;
            }
            return true;

        }

        public static async Task<bool> CheckHashtags(string name, string msg)
        {
            var auth = await Auth();
            if (!auth) return false;
            try
            {
                var instUser = await InstaApi.UserProcessor.GetUserInfoByUsernameAsync(name);
                var sendMessageResult = await InstaApi.MessagingProcessor.SendDirectTextAsync($"{instUser?.Value?.Pk}", string.Empty, msg);
            }
            catch (Exception)
            {
                return false;
            }
            return true;

        }
        public static object  ParseByWords()
        {
            var request = (HttpWebRequest)WebRequest.Create("https://www.instagram.com/");
            var authrequest = (HttpWebRequest)WebRequest.Create("https://www.instagram.com/accounts/login/ajax/");
      
             
            var searchrequest = (HttpWebRequest)WebRequest.Create("https://www.instagram.com/web/search/topsearch/?context=blended&query={Самара Путешествие}&rank_token={0.871279}&include_ree=true");
            
            
            authrequest.UserAgent = "user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit / 537.36(KHTML, like Gecko) Chrome / 79.0.3945.130 Safari / 537.36";
            request.UserAgent = "user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit / 537.36(KHTML, like Gecko) Chrome / 79.0.3945.130 Safari / 537.36";
            request.CookieContainer = new CookieContainer();
            authrequest.CookieContainer = new CookieContainer();
            searchrequest.CookieContainer = new CookieContainer();
            string csrftoken = "";
            using (var response = (HttpWebResponse)request.GetResponse())
            {
                // Print the properties of each cookie.
                foreach (Cookie cook in response.Cookies)
                {
                    authrequest.CookieContainer.Add(cook);

                    if (cook.Name == "csrftoken")
                    {
                        csrftoken = cook.Value.ToString();
                    }
                }
                response.Close();
            }

            authrequest.ContentType = "application/x-www-form-urlencoded";
            authrequest.Headers.Add("x-ig-app-id", "936619743392459");
            authrequest.Headers.Add("x-csrftoken", csrftoken);
            searchrequest.ContentType = "application/x-www-form-urlencoded";
            searchrequest.Headers.Add("x-ig-app-id", "936619743392459");
            searchrequest.Headers.Add("x-csrftoken", csrftoken);
            authrequest.Method = "POST";
            var result = DataProvider.executeProcedure("dbo.get_su_inst")?.Tables;

            string postData = "username="+ result?[0]?.Rows?[0]?["user_login"]?.ToString() + "&enc_password="+result?[0]?.Rows?[0]?["user_epwd"]?.ToString()+"&queryParams={}&optIntoOneTap=false&stopDeletionNonce=&trustedDeviceRecords={}";
            byte[] byteArray = Encoding.UTF8.GetBytes(postData);
            authrequest.ContentType = "application/x-www-form-urlencoded";
            authrequest.ContentLength = byteArray.Length;
            Stream dataStream = authrequest.GetRequestStream();
            dataStream.Write(byteArray, 0, byteArray.Length);
            dataStream.Close();
            HttpWebResponse tresponse;
            tresponse = (HttpWebResponse)authrequest.GetResponse();
            foreach (Cookie cook in tresponse.Cookies)
            {
                searchrequest.CookieContainer.Add(cook);

                if (cook.Name == "csrftoken")
                {
                    csrftoken = cook.Value.ToString();
                }

            }

            using (dataStream = tresponse.GetResponseStream())
            {

                // Open the stream using a StreamReader for easy access.
                StreamReader reader = new StreamReader(dataStream);
                // Read the content.
                string responseFromServer1 = reader.ReadToEnd();
                // Display the content.

            }

            // Close the response.
            tresponse.Close();


            var sresponse = (HttpWebResponse)searchrequest.GetResponse();

            string responseFromServer="";
            using (dataStream = sresponse.GetResponseStream())
            {

                // Open the stream using a StreamReader for easy access.
                StreamReader reader = new StreamReader(dataStream);
                // Read the content.
                responseFromServer = reader.ReadToEnd();
                // Display the content.
                Console.WriteLine(responseFromServer);
            }

            // Close the response.
            sresponse.Close();




            return responseFromServer;

        }
    }
}
