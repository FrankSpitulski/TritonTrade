using System;

using Android.App;
using Android.Content;
using Android.Content.PM;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;

namespace TritonTrade.Droid
{
	[Activity(Label = "TritonTrade.Droid", Icon = "@drawable/icon", Theme = "@style/MyTheme", MainLauncher = true, ConfigurationChanges = ConfigChanges.ScreenSize | ConfigChanges.Orientation)]
	public class MainActivity : global::Xamarin.Forms.Platform.Android.FormsAppCompatActivity
	{
		protected override void OnCreate(Bundle bundle)
		{
			TabLayoutResource = Resource.Layout.Tabbar;
			ToolbarResource = Resource.Layout.Toolbar;

			base.OnCreate(bundle);

			global::Xamarin.Forms.Forms.Init(this, bundle);

			LoadApplication(new App());

            test();
		}

        private void test()
        {
            Server.login("test@ucsd.edu", "123");

            Server.addNewUser("Test", "/defaultUser.jpg", "bio", "(555) 555-5555", "test@ucsd.edu", "123");

            Server.login("test@ucsd.edu", "123");
        }
	}
}
