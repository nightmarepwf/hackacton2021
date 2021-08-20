
using Microsoft.EntityFrameworkCore;
using Microsoft.Data.SqlClient;
using Microsoft.Extensions.Configuration;
using System.Data;
using System;
using System.Data.Common;

namespace Project1.Data
{
    public class DataProvider
    {
        public static DataSet executeProcedure(String procudureName, params SqlParameter[] sqlParameters)
        {
            return executeSqlCommand(procudureName, CommandType.StoredProcedure, sqlParameters);
        }
        public static DataSet executeSql(String commandText, params SqlParameter[] sqlParameters)
        {
            return executeSqlCommand(commandText, CommandType.Text, sqlParameters);
        }
        public static DataSet executeSqlCommand(String commandText, CommandType Commandtype, params SqlParameter[] sqlParameters)
        {
            IConfigurationRoot configuration = new ConfigurationBuilder()
                .SetBasePath(AppDomain.CurrentDomain.BaseDirectory)
                .AddJsonFile("appsettings.json")
                .Build();
            using SqlConnection connection = new(configuration.GetConnectionString("DefaultConnection"));
            DataSet myset = new DataSet();
            using (var command = new SqlCommand(commandText, connection))
            {
                command.CommandType = Commandtype;
                foreach (var _kv in sqlParameters)
                {
                    DbParameter _dbpara = command.CreateParameter();
                    _dbpara.ParameterName = _kv.ParameterName;
                    _dbpara.Value = _kv.Value;
                    command.Parameters.Add(_dbpara);
                }
                connection.Open();
                DbDataAdapter adapter = DbProviderFactories.GetFactory(connection).CreateDataAdapter();
                adapter.SelectCommand = command;
                adapter.Fill(myset);
            }
            return myset;
        }
    }
}