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
    public class Blogers
    {
        public static object GetList()
        {

            var result = JsonConvert.SerializeObject(new return_obj() {
                old_blogers = DataProvider.executeProcedure("dbo.get_blogers")?.Tables?[0],
                new_blogers=JsonConvert.DeserializeObject<search_bloger>(Instagram.ParseByWords().ToString())
            });


            return result;
        }

        public static async Task<bool> CreateEvent(bloger_value value_obj)
        {

            //var tmp = JsonConvert.DeserializeObject((await Instagram.GetUserFullInfoByName(value_obj.instagram)).ToString());

            SqlParameter[] dbParams = new SqlParameter[]
{
        new SqlParameter("@u_name", value_obj.u_name),
        new SqlParameter("@u_soname", value_obj.u_soname),
        new SqlParameter("@u_otch", value_obj.u_otch),
        new SqlParameter("@email", value_obj.email),
        new SqlParameter("@instagram", value_obj.instagram),
        new SqlParameter("@youtube", value_obj.youtube),
        new SqlParameter("@date_last_reject", value_obj.date_last_reject)
        //new SqlParameter("@followers", tmp.Value.)
};
            DataProvider.executeProcedure("dbo.create_bloger", dbParams);

            return true;
        }
    }

    public class bloger_value
    {
        public string u_name,
 u_soname,
 u_otch = "",
 email,
 instagram,
 youtube = "",
 date_last_reject = "";
    }

    public class search_bloger
    {
        public object users;
    }

    public class return_obj
    {
        public object old_blogers;
        public object new_blogers;
    }
}
