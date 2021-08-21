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
            var result = JsonConvert.SerializeObject(DataProvider.executeProcedure("dbo.get_blogers")?.Tables?[0]);
            return result;
        }

        public static bool CreateEvent(bloger_value value_obj)
        {
            SqlParameter[] dbParams = new SqlParameter[]
{
        new SqlParameter("@u_name", value_obj.u_name),
        new SqlParameter("@u_soname", value_obj.u_soname),
        new SqlParameter("@u_otch", value_obj.u_otch),
        new SqlParameter("@email", value_obj.email),
        new SqlParameter("@instagram", value_obj.instagram),
        new SqlParameter("@youtube", value_obj.youtube),
        new SqlParameter("@date_last_reject", value_obj.date_last_reject)
};
            DataProvider.executeProcedure("dbo.create_bloger",dbParams);

            return true;
        }
    }

    public class bloger_value
    {
        public string u_name,
 u_soname,
 u_otch="",
 email,
 instagram,
 youtube="",
 date_last_reject = "";
    }
}
