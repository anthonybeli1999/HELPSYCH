using System;
using System.Data.Entity;
using System.Data.Entity.Core.Objects;
using System.Data.Entity.ModelConfiguration.Conventions;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Web;
using Proyecto_HELPSYCH.Models;


namespace Proyecto_HELPSYCH.App_Start
{
    public class DBContext : DbContext
    {
        public DBContext()
          : base("DBEntities")
        {
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Conventions.Remove<PluralizingTableNameConvention>();
        }

        public virtual DbSet<Designation> Designations { get; set; }
        public virtual DbSet<Employee> Employees { get; set; }
    }
}