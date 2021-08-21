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
            catch (Exception e)
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
            catch (Exception e)
            {
                return false;
            }
            return true;

        }

        public static async Task<bool> GetUserFullInfoByName(string name)
        {
            var auth = await Auth();
            if (!auth) return false;
            try
            {
                var instUser = await InstaApi.UserProcessor.GetUserInfoByUsernameAsync(name);

            }
            catch (Exception e)
            {
                return false;
            }
            return true;

        }

        public static async Task<bool>  SendMessage(string name,string msg)
        {
            var auth = await Auth();
            if (!auth) return false;
            try
            {
                var instUser = await InstaApi.UserProcessor.GetUserInfoByUsernameAsync(name);
                var sendMessageResult = await InstaApi.MessagingProcessor.SendDirectTextAsync($"{instUser?.Value?.Pk}", string.Empty, msg);
            }
            catch (Exception e)
            {
                return false;
            }
            return true;
            
        }
    }
}
