using Microsoft.AspNetCore.Identity;
using Microsoft.Data.SqlClient;
using Newtonsoft.Json;
using Project1.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Project1.Models
{
    public class Events
    {
        public static object GetList()
        {


            var result = JsonConvert.SerializeObject(DataProvider.executeProcedure("dbo.get_events")?.Tables?[0]);
            var list_count = JsonConvert.SerializeObject(DataProvider.executeProcedure("dbo.get_events")?.Tables?[0]);
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
            var blogers = JsonConvert.SerializeObject(value_obj.blogers);
            var blogers_obj = JsonConvert.DeserializeObject<List<bloger>>(blogers);
            if (!SyncBlogers(blogers_obj, id, value_obj)) return false;
            return true;
        }
    }

    public class bloger
    {
        public string ID;
        public string instagram;

    }
    public class event_value
    {
        public string title;
        public string event_description;
        public string event_date;
        public object blogers;
        public object tags;
    }
}
