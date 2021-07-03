using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Proyecto_HELPSYCH.Models;
using ViewModels;


namespace Proyecto_HELPSYCH.Controllers
{
   
    public class HomeController : Controller
    {
        // GET: Home
        
        private static string ApiKey = "AIzaSyDdEivzerxztZvLXrPweXmVIZHGtAvaIpI";
        private static string AuthEmail = "";
        private static string AuthPassword = "";
        private static string Bucket = "gs://helpsych-66739.appspot.com";
     
        public ActionResult Index()
        {
            return View();
        }
  

        [HttpPost]
        public ActionResult Test11(Evaluation model)
        {

            if (ModelState.IsValid)
            {
                foreach (var q in model.Questions)
                {
                    var qId = q.ID;
                    var selectedAnswer = q.SelectedAnswer;
                    // Save the data 

                }
                return RedirectToAction("Contact"); //PRG Pattern
            }
            //reload questions
            return View(model);
        }


        public ActionResult TestMenu()
        {

            
            //reload questions
            return View();
        }

        public ActionResult salasdechat()
        {


            //reload questions
            return View();
        }

        public ActionResult entrychat()
        {


            //reload questions
            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }

        public ActionResult Chat()
        {
            

            return View();
        }

        public ActionResult Chat3()
        {


            return View();
        }

        public ActionResult especialistas()
        {


            return View();
        }

        public ActionResult Menu()
        {


            return View();
        }


        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }

        public ActionResult Chat2()
        {


            return View();
        }

        public ActionResult fileupload()
        {


            return View();
        }

        public ActionResult Articulos()
        {


            return View();
        }

        public ActionResult entry()
        {


            return View();
        }

        public ActionResult login2()
        {


            return View();
        }

        public ActionResult login()
        {


            return View();
        }

        public ActionResult ListaTest()
        {


            return View();
        }

        public ActionResult entrytest()
        {


            return View();
        }
    }
}