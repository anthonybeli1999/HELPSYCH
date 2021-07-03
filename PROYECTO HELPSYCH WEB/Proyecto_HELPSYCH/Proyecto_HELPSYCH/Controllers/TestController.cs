using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Proyecto_HELPSYCH.Models;

namespace Proyecto_HELPSYCH.Controllers
{
    [Authorize]
    public class TestController : Controller
    {
        

        private static string ApiKey = "AIzaSyDdEivzerxztZvLXrPweXmVIZHGtAvaIpI";
        private static string AuthEmail = "";
        private static string AuthPassword = "";
        private static string Bucket = "gs://helpsych-66739.appspot.com";
        // GET: Test

        int contador = 0;
        int contador2 = 0;
        int contador3 = 0;
        int contador4 = 0;
        int contador5 = 0;
        int contador6 = 0;
        int contador7 = 0;
        int contador8 = 0;
        int contador9 = 0;
        int contador10 = 0;
        int contador11 = 0;
        int suma = 0;
        public ActionResult Index()
        {
            return View();
        }
        

        public ActionResult Test6()
        {

          

            return View();
           
        }

        public ActionResult Test7()
        {
            return View();
        }


        public ActionResult Visualizar(ClsTest6 objtest)
        {

            if (Convert.ToInt32(objtest.pregunta1) == 1)
            {
                contador = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta1) == 2)
                {
                    contador = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta1) == 3)
                    {
                        contador = 3;
                    }
                }
            }

            if (Convert.ToInt32(objtest.pregunta2) == 1)
            {
                contador2 = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta2) == 2)
                {
                    contador2 = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta2) == 3)
                    {
                        contador2 = 3;
                    }
                }
            }

            if (Convert.ToInt32(objtest.pregunta3) == 1)
            {
                contador3 = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta3) == 2)
                {
                    contador3 = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta3) == 3)
                    {
                        contador3 = 3;
                    }
                }
            }

            if (Convert.ToInt32(objtest.pregunta4) == 1)
            {
                contador4 = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta4) == 2)
                {
                    contador4 = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta4) == 3)
                    {
                        contador4 = 3;
                    }
                }
            }

            if (Convert.ToInt32(objtest.pregunta5) == 1)
            {
                contador5 = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta5) == 2)
                {
                    contador5 = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta5) == 3)
                    {
                        contador5 = 3;
                    }
                }
            }

            if (Convert.ToInt32(objtest.pregunta6) == 1)
            {
                contador6 = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta6) == 2)
                {
                    contador6 = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta6) == 3)
                    {
                        contador6 = 3;
                    }
                }
            }

            if (Convert.ToInt32(objtest.pregunta7) == 1)
            {
                contador7 = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta7) == 2)
                {
                    contador7 = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta7) == 3)
                    {
                        contador7 = 3;
                    }
                }
            }

            if (Convert.ToInt32(objtest.pregunta8) == 1)
            {
                contador8 = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta8) == 2)
                {
                    contador8 = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta8) == 3)
                    {
                        contador8 = 3;
                    }
                }
            }

            if (Convert.ToInt32(objtest.pregunta9) == 1)
            {
                contador9 = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta9) == 2)
                {
                    contador9 = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta9) == 3)
                    {
                        contador9 = 3;
                    }
                }
            }

            if (Convert.ToInt32(objtest.pregunta10) == 1)
            {
                contador10 = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta10) == 2)
                {
                    contador10 = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta11) == 3)
                    {
                        contador10 = 3;
                    }
                }
            }

            if (Convert.ToInt32(objtest.pregunta11) == 1)
            {
                contador11 = 1;
            }
            else
            {
                if (Convert.ToInt32(objtest.pregunta11) == 2)
                {
                    contador11 = 2;
                }
                else
                {
                    if (Convert.ToInt32(objtest.pregunta11) == 3)
                    {
                        contador11 = 3;
                    }
                }
            }

            suma = contador + contador + contador2 + contador3 + contador4 + contador5 + contador6 + contador7 + contador8 + contador9 + contador10 + contador11;

            objtest.suma = suma;

            return View(objtest);
        }
    }
}