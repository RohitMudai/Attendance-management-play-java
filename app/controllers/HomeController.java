package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import io.ebean.SqlUpdate;
import models.Products_Info;
import models.ProjectModel;
import org.springframework.util.StringUtils;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import controllers.DAO.EbeanTestServer;

import javax.inject.Inject;
import java.util.List;

import static java.time.LocalDateTime.now;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
    @Inject
    private FormFactory formFactory;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {

        return ok(views.html.index.render());
    }

    public Result products() {
        return ok(views.html.products.render());
    }

    public Result names() {

        return ok(views.html.projectmodel.render());
    }

    public Result productsinfo() {
        Form<Products_Info> basicform = formFactory.form(Products_Info.class).bindFromRequest();
        //  String name = products_info.getName();
        if (basicform.hasErrors()) {
            //return badRequest("enter your name again");
            return ok(views.html.error.render());
        } else {
            Products_Info products_info = basicform.get();
            /*SqlQuery sqlQuery = EbeanTestServer.createSqlQuery("select * from attend_details where name='"
                    + products_info.getName() + "' and emp_id='" + products_info.getEmp_id() + "'");  and checkout_time is null
                    List<SqlRow> rows = sqlQuery.findList();*/
            /*SqlQuery sqlQuery = EbeanTestServer.createSqlQuery("select * from attend_details where name='"+ products_info.getName() + "' and date =curdate()");
            List<SqlRow> rows = sqlQuery.findList();*/
            SqlQuery sqlQuery = EbeanTestServer.createSqlQuery("select * from attend_details where emp_id='" + products_info.getEmp_id() + "' and date(checkin_time)=date(now())");
            List<SqlRow> rows = sqlQuery.findList();
            if (rows.isEmpty()) {
                SqlUpdate sqlUpdate = EbeanTestServer.createUpdateQuery("insert into attend_details (name,checkin_time,emp_id) values('"
                        + products_info.getName() + "',now(),'" + products_info.getEmp_id() + "')");
                sqlUpdate.execute();


            }
            /*if(!rows.isEmpty()) {
                SqlUpdate sqlUpdate = EbeanTestServer.createUpdateQuery("insert into attend_details (name,checkin_time,emp_id) values('" + products_info.getName() + "',now(),'" + products_info.getEmp_id() + "')");
                sqlUpdate.execute();
            }*/
            else {
                /*SqlQuery sqlQuery = EbeanTestServer.createSqlQuery("select count(*) from attend_details ");*/
                SqlUpdate sqlUpdateout = EbeanTestServer.createUpdateQuery("update attend_details set checkout_time=now() where name='"
                        + products_info.getName() + "' and emp_id='" + products_info.getEmp_id() + "' and date(checkin_time)=date(now())");
                sqlUpdateout.execute();
                return ok("you have checked out: " + products_info.getName());
            }

                /*SqlUpdate sqlUpdatenew = EbeanTestServer.createUpdateQuery("insert into attend_details (name,checkin_time,emp_id,date) values('" + products_info.getName() + "',now(),'" + products_info.getEmp_id() + "',curdate())");
                sqlUpdatenew.execute();*/
            return ok("you have checked in: " + products_info.getName());
        }
            /*SqlUpdate sqlUpdatenew = EbeanTestServer.createUpdateQuery("insert into attend_details (name,checkin_time,emp_id) values('" + products_info.getName() + "',now(),'" + products_info.getEmp_id() + "')");
            sqlUpdatenew.execute();*/
          /* SqlQuery sqlQuerynew = EbeanTestServer.createSqlQuery("select * from attend_details where name='" + products_info.getName() + "' and checkout_time=  IS NOT NULL ");
            List<SqlRow> rowsnew = sqlQuerynew.findList();*/
           /* Logger.error("Total number of records: "+rowsnew.size());
            for(SqlRow row : rowsnew) {
                System.out.println("Name: "+row.getString("name"));
            }*/
           /* if(!rowsnew.isEmpty()) {
                SqlUpdate sqlUpdatenew = EbeanTestServer.createUpdateQuery("insert into attend_details (name,checkin_time,emp_id) values('" + products_info.getName() + "',now(),'" + products_info.getEmp_id() + "')");
                sqlUpdatenew.execute();
            }*/
            /*List<SqlRow> rows = EbeanTestServer.createSqlQuery("select * from attend_details where name='"+ products_info.getName() + "' and date(checkin_time) ='"+now()+"' and date(checkout_time) ='"+null+"'").findList();
            Logger.error("Total number of records: "+rows.size());
            for(SqlRow row : rows) {
                System.out.println("Name: "+row.getString("name"));
            }*/
        //  return ok("Handling json by Form only: " + products_info.getName());
    }


    public Result hello() {
        Form<ProjectModel> basicform = formFactory.form(ProjectModel.class).bindFromRequest();
        ProjectModel projectModel = basicform.get();
        return ok("Hello " + projectModel.getName() + " " /*+ lastname*/ + projectModel.getEmp_id());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result jsonhandler() {
        JsonNode json = request().body().asJson();
        String name = json.findPath("name").textValue();
        return ok("json handler content is " + name);

    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result jsonhandlertwo() {
        JsonNode json = request().body().asJson();
        String name = json.findPath("name").textValue();
        String emp_id = json.findPath("emp_id").textValue();
        return ok(Json.toJson(name + emp_id));
    }

    public Result register() {
        return ok(views.html.login.render());
    }

    public Result loginPage() {
        String email = session().get("email");
        String role = session().get("role");
        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(role)) {
            return ok(views.html.loginpage.render("Welcome User "));
        } else if (!StringUtils.isEmpty(email) && role.equalsIgnoreCase("admin")) {
            return ok(views.html.admindashboard.render(email));
        } else if (!StringUtils.isEmpty(email) && role.equalsIgnoreCase("user")) {
            return ok(views.html.logout.render());
        } else {
            return ok(views.html.loginpage.render("welcome User"));
        }

    }

    public Result attendance() {

        return ok(views.html.names.render());
    }

    public Result dashboard() {
        String email = session().get("email");
        String role = session().get("role");
        if (!StringUtils.isEmpty(email) && role.equalsIgnoreCase("user")) {
            return ok(views.html.logout.render());
        } else {
            return redirect("/loginpage");
        }
    }

    public Result loggingOut() {
        session().clear();
        return redirect("/loginpage");
    }

    public Result admindashboard() {
        String email = session().get("email");
        String role = session().get("role");
        if (!StringUtils.isEmpty(email) && role.equalsIgnoreCase("admin")) {
            return ok(views.html.admindashboard.render(email));
        } else {

            return redirect("/loginpage");
        }
    }

    public Result registerBoot() {
        return ok(views.html.registration.render());
    }


}
