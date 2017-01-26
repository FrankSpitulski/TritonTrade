using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MySql.Data.MySqlClient;

namespace TestDatabase2

{
	class Program
	{
		static void Main(string[] args)
		{
			/*List<ulong> list = new List<ulong>();
            list.Add(1);
            list.Add(2);
            Server.searchUserIDs(list);
            */

			Server.login("test@ucsd.edu", "123");

			Server.addNewUser("Test", "/defaultUser.jpg", "bio", "(555) 555-5555", "test@ucsd.edu", "123");

			Server.login("test@ucsd.edu", "123");

			dataDump();


			Console.ReadLine();

		}

		static void dataDump()
		{
			MySql.Data.MySqlClient.MySqlConnection conn;

			string serverName = "spitulski.no-ip.biz";
			string uid = "Michelangelo";
			string pwd = "Leonardo";
			string database = "TritonTrade";
			string myConnectionString = "server=" + serverName + ";uid=" + uid + ";pwd=" + pwd + ";database=" + database + ";";

			Console.WriteLine();

			try
			{
				conn = new MySql.Data.MySqlClient.MySqlConnection(myConnectionString);
				conn.Open();
				System.Data.DataSet data = new System.Data.DataSet();
				MySqlCommand cmd = new MySqlCommand("SELECT * FROM users;", conn);
				using (MySqlDataAdapter adapter = new MySqlDataAdapter(cmd))
				{
					adapter.Fill(data);
				}
				foreach (System.Data.DataTable table in data.Tables)
				{
					foreach (System.Data.DataRow dataRow in table.Rows)
					{
						foreach (var item in dataRow.ItemArray)
						{
							Console.Write("[" + item + " " + item.GetType() + "]");
						}
						Console.WriteLine();
					}
				}

				data.Clear();
				Console.WriteLine();
				cmd = new MySqlCommand("SELECT * FROM posts;", conn);
				using (MySqlDataAdapter adapter = new MySqlDataAdapter(cmd))
				{
					adapter.Fill(data);
				}
				foreach (System.Data.DataTable table in data.Tables)
				{
					foreach (System.Data.DataRow dataRow in table.Rows)
					{
						foreach (var item in dataRow.ItemArray)
						{
							Console.Write("[" + item + " " + item.GetType() + "]");
						}
						Console.WriteLine();
					}
				}

				conn.Close();
			}
			catch (MySql.Data.MySqlClient.MySqlException ex)
			{
				Console.Out.WriteLine(ex);

			}
		}

	}
}
