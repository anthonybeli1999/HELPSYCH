using Proyecto_HELPSYCH.App_Start;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Optimization;
using System.Web.Routing;
using System.Web.Helpers;
using System.Security.Claims;

namespace Proyecto_HELPSYCH
{
    public class MvcApplication : System.Web.HttpApplication
    {
        protected void Application_Start()
        {
            AreaRegistration.RegisterAllAreas();
            FilterConfig.RegisterGlobalFilters(GlobalFilters.Filters);
            RouteConfig.RegisterRoutes(RouteTable.Routes);
            BundleConfig.RegisterBundles(BundleTable.Bundles);
            System.Web.Helpers.AntiForgeryConfig.UniqueClaimTypeIdentifier =
            System.Security.Claims.ClaimTypes.NameIdentifier;
            AntiForgeryConfig.UniqueClaimTypeIdentifier = ClaimTypes.Email;
          
        }
    }
}
