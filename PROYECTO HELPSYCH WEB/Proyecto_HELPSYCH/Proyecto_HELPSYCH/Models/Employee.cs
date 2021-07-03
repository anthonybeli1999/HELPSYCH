using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace Proyecto_HELPSYCH.Models
{
    public class Employee
    {
        [Key]
        public int EmployeeID { get; set; }
        public string Name { get; set; }
        public string CNIC { get; set; }
        [ForeignKey("DesignationID")]
        public Designation Designation { get; set; }
        public int DesignationID { get; set; }
    }
}