
using Microsoft.Data.SqlClient;
using Newtonsoft.Json;
using Project1.Data;


namespace Project1.Models
{
    public class Rating
    {
        public static object UpdateRating(rt value)
        {
            string result;


            SqlParameter[] dbParams = new SqlParameter[]
{
        new SqlParameter("@id", value.id),
        new SqlParameter("@rating", value.rating),

};
            DataProvider.executeProcedure("dbo.update_bloger_rating", dbParams);


            SqlParameter[] newdbParams = new SqlParameter[]
{
        new SqlParameter("@id", value.id),
};

            result = JsonConvert.SerializeObject(DataProvider.executeProcedure("dbo.get_bloger_one", newdbParams)?.Tables?[0]);

            return result;
        }
    }
    public class rt
    {
        public string id;
        public string rating;
    }
}
