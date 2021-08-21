using Microsoft.AspNetCore.Identity;
using Microsoft.Data.SqlClient;
using Newtonsoft.Json;
using Project1.Data;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Threading.Tasks;

namespace Project1.Models
{
    public class Events
    {
        public static object GetList(string id)
        {
            string result;


            SqlParameter[] dbParams = new SqlParameter[]
{
        new SqlParameter("@id", id),

};

            if (id == "-1")
            {
                 result= JsonConvert.SerializeObject(DataProvider.executeProcedure("dbo.get_events", dbParams)?.Tables?[0]);
            }
            else
            { 
                
                var event_info=DataProvider.executeProcedure("dbo.get_events", dbParams)?.Tables?[0];
                var tags = DataProvider.executeProcedure("dbo.get_tags", dbParams)?.Tables?[0];
                var mentions = DataProvider.executeProcedure("dbo.get_mentions", dbParams)?.Tables?[0];
                var blogers = DataProvider.executeProcedure("dbo.get_blogers_to_event", dbParams)?.Tables?[0];
                result = JsonConvert.SerializeObject(new event_full_info()
                {
                    event_info = event_info,
                    tags = tags,
                    mentions = mentions,
                    blogers = blogers
                });
  
            }
         
            return result;
        }
        public static bool SyncBlogers(List<bloger> blogers, string id, event_value evt)
        {
            foreach (bloger usr in blogers)
            {
                SqlParameter[] dbParams = new SqlParameter[]
{
        new SqlParameter("@event_id", id),
        new SqlParameter("@bloger_id", usr.ID)
};
                DataProvider.executeProcedure("dbo.sync_user_for_event", dbParams);
                //Instagram.SendMessage(usr.instagram,"Приглашаем вас на мероприятие "+ evt.title) ;
            }
            return true;
        }
        public static bool SyncTags(List<tag> tags, List<mention> mentions, string id)
        {
            foreach (tag tg in tags)
            {
                SqlParameter[] dbParams = new SqlParameter[]
{
        new SqlParameter("@event_id", id),
        new SqlParameter("@tag", tg.tagName),
         new SqlParameter("@tag_type", 1)
        
};
                DataProvider.executeProcedure("dbo.sync_tags_for_event", dbParams);
               
            }
            if (mentions == null) return true;
            foreach (mention mn in mentions)
            {
                SqlParameter[] dbParams = new SqlParameter[]
{
        new SqlParameter("@event_id", id),
        new SqlParameter("@tag", mn.mentionName),
         new SqlParameter("@tag_type", 2)
};
                DataProvider.executeProcedure("dbo.sync_tags_for_event", dbParams);

            }
            return true;
        }


        public static bool CreateEvent(event_value value_obj)
        {
            SqlParameter[] dbParams = new SqlParameter[]
{
        new SqlParameter("@title", value_obj.title),
        new SqlParameter("@event_description", value_obj.event_description),
        new SqlParameter("@event_date", value_obj.event_date),
};
            var result = DataProvider.executeProcedure("dbo.create_event",dbParams  )?.Tables?[0];
            string id = result.Rows[0][0].ToString();
            var blogers = JsonConvert.DeserializeObject<List<bloger>>(JsonConvert.SerializeObject(value_obj.blogers));
            var tags = JsonConvert.DeserializeObject<List<tag>>(JsonConvert.SerializeObject(value_obj.tags));
            var mentions = JsonConvert.DeserializeObject<List<mention>>(JsonConvert.SerializeObject(value_obj.mentions));

            if (!SyncBlogers(blogers, id, value_obj)) return false;
            if (!SyncTags(tags, mentions, id)) return false;

            return true;
        }
    }

    public class bloger
    {
        public string ID;
        public string instagram;

    }
    public class tag
    {
        public string ID;
        public string tagName;

    }

    public class mention
    {
        public string ID;
        public string mentionName;

    }
    public class event_value
    {
        public string title;
        public string event_description;
        public string event_date;
        public object blogers;
        public object tags;
        public object mentions;
    }

    public class event_full_info
    {
        public DataTable event_info;
        public DataTable tags;
        public DataTable mentions;
        public DataTable blogers;
    }
}
