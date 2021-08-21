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
            return result;
        }

        public static bool CreateEvent(event_value value_obj)
        {
            SqlParameter[] dbParams = new SqlParameter[]
{
        new SqlParameter("@title", value_obj.title),
        new SqlParameter("@event_description", value_obj.event_description),
        new SqlParameter("@event_date", value_obj.event_date),
};
            DataProvider.executeProcedure("dbo.create_event",dbParams);

            return true;
        }
    }

    public class event_value
    {
        public string title;
        public string event_description;
        public string event_date;
    }
}
