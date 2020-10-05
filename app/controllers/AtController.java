package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.LoginDto;
import io.ebean.*;
import models.LoginDetails;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import controllers.DAO.EbeanTestServer;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static java.time.LocalDate.now;
import static sun.misc.PerformanceLogger.setTime;

public class AtController extends Controller {
    @Inject
    private FormFactory formFactory;

    public Result atControl() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String name = requestData.get("name");
        String emp_id = requestData.get("emp_id");
        //String lastname = requestData.get("lastname");
        SqlQuery sqlQuery = EbeanTestServer.createSqlQuery("select * from attend_details where emp_id='" + emp_id + "' and date(checkin_time)=date(now())");
        List<SqlRow> rows = sqlQuery.findList();
        if (rows.isEmpty()) {
            return ok("1");
        }
        return ok("2");
    }

    public Result checkIn() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String name = requestData.get("name");
        String emp_id = requestData.get("emp_id");
        Logger.info("name" + name + " emp id=" + emp_id);
        SqlUpdate sqlUpdate = EbeanTestServer.createUpdateQuery("insert into attend_details (name,checkin_time,emp_id) values('"
                + name + "',now(),'" + emp_id + "')");
        sqlUpdate.execute();
        return ok(Json.toJson("you have checked In \n please CheckOut"));
    }

    public Result sass() {
        return ok(views.html.sass.render());
    }

    public Result checkOut() {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String name = requestData.get("name");
        String emp_id = requestData.get("emp_id");
        SqlUpdate sqlUpdateout = EbeanTestServer.createUpdateQuery("update attend_details set checkout_time=now() where name='"
                + name + "' and emp_id='" + emp_id + "' and date(checkin_time)=date(now())");
        sqlUpdateout.execute();
        return ok(Json.toJson("you have Checked out"));
    }

    public Result registrationForm() {
        Form<LoginDetails> loginDetailsForm = formFactory.form(LoginDetails.class).bindFromRequest();
        if (loginDetailsForm.hasErrors()) {
            return ok(views.html.login.render());
        } else {
            LoginDetails logindetail = loginDetailsForm.get();
            List<LoginDetails> loginDetailss = LoginDetails
                    .find
                    .query()
                    .where()
                    .eq("email", logindetail.getEmail())
                    .findList();

            if (loginDetailss.isEmpty()) {
                EbeanTestServer.save(logindetail);

                return ok("1");
                //  return ok(views.html.loginpage.render("Your Rregistration was Successfull ","seccess"));
            } else {
                return ok("2");
            }
        }
    }

    public Result loggingin() {
        Form<LoginDto> loginDetailsForm = formFactory.form(LoginDto.class).bindFromRequest();
        LoginDto loginDto = loginDetailsForm.get();
        Logger.info("email: " + loginDto.getEmail());
        List<LoginDetails> loginDetailss = LoginDetails
                .find
                .query()
                .where()
                .eq("email", loginDto.getEmail())
                .eq("password", loginDto.getPassword())
                .eq("role", loginDto.getRole())
                .findList();

        if (loginDetailss.isEmpty()) {
            return ok(views.html.loginpage.render("login details invalid"));
        } else {
            if (loginDto.getRole().equalsIgnoreCase("admin")) {
                /*HashMap<String, String> hash_map = new HashMap<String, String>();
                hash_map.put("email", loginDto.getEmail());
                hash_map.put("role", loginDto.getRole());*/
                session("email", loginDto.getEmail());
                //session().adding(hash_map);

                return redirect("/admindashboard ").addingToSession(request(), "role", loginDto.getRole());
                //return ok(views.html.admindashboard.render()).addingToSession(request(),"role",loginDto.getRole());
            } else {
                session("email", loginDto.getEmail());
                return redirect("/dashboard").addingToSession(request(), "role", loginDto.getRole());
                //return ok(views.html.logout.render()).addingToSession(request(),"role",loginDto.getRole());
            }
        }
    }


    public Result encode() {
        try {
            //  URL url = new URL("http://localhost:9000/loginpage");
            // String toEncode = url.getPath();
            String url = "http://localhost:9000/loginpage";
            String encodeURL = URLEncoder.encode(url, "UTF-8");
            Logger.info("url: " + encodeURL);
            return ok();
        } catch (UnsupportedEncodingException e) {
            return ok();//"Issue while encoding" +e.getMessage();
        }
    }

    public Result dataTable() {
        JsonNodeFactory f = JsonNodeFactory.instance;
        ArrayNode tableContents = new ArrayNode(f);
        List<SqlRow> sqlQueryList = EbeanTestServer.createSqlQuery("select * from login_details order by id desc").findList();
        for (SqlRow row : sqlQueryList) {
            ObjectNode tableRow = Json.newObject();
            tableRow.put("1", row.getString("first_name"));
            tableRow.put("2", row.getString("last_name"));
            tableRow.put("3", row.getString("Phone_no"));
            tableRow.put("4", row.getString("email_id"));
            tableContents.add(tableRow);
        }
        return ok(Json.toJson(tableContents));
    }

    public Result attendReports() {
        JsonNodeFactory ar = JsonNodeFactory.instance;
        ArrayNode tableContents = new ArrayNode(ar);
        List<SqlRow> sqlQueryList = EbeanTestServer.createSqlQuery("select * from attend_details")
                .findList();
        for (SqlRow row : sqlQueryList) {
            ObjectNode tableRow = Json.newObject();
            Logger.info("id : " + row.getString("id"));
            tableRow.put("1", row.getString("name"));
            tableRow.put("2", row.getString("checkin_time"));
            tableRow.put("3", row.getString("checkout_time"));
            tableRow.put("4", row.getString("emp_id"));
            tableContents.add(tableRow);

        }
        return ok(Json.toJson(tableContents));
    }

    public Result profile() {
        String email = session().get("email");
        LoginDetails loginDetails = new LoginDetails().find.
                query()
                .where()
                .eq("email", email)
                .eq("role", "admin")
                .findOne();
        Logger.info("loginDetails", loginDetails.getFname());
        return ok(views.html.editprofile.render(loginDetails));
    }

    public Result topGenre() {
        JsonNodeFactory ar = JsonNodeFactory.instance;
        ArrayNode tableContents = new ArrayNode(ar);
        List<SqlRow> sqlQueryList = EbeanTestServer.createSqlQuery("select genre,count(name) as totalmovie, sum(gross)-sum(budget) as totalearning from movies group by genre order by sum(gross)-sum(budget) desc limit 10")
                .findList();
        for (SqlRow row : sqlQueryList) {
            ObjectNode tableRow = Json.newObject();
            Logger.info("id : " + row.getString("genre"));
            tableRow.put("1", row.getString("genre"));
            tableRow.put("2", row.getBigDecimal("totalmovie"));
            tableRow.put("3", row.getBigDecimal("totalearning"));
            tableContents.add(tableRow);

        }
        return ok(Json.toJson(tableContents));
    }

    public Result topProduction() {
        JsonNodeFactory ar = JsonNodeFactory.instance;
        ArrayNode tableContents = new ArrayNode(ar);
        List<SqlRow> sqlQueryList = EbeanTestServer.createSqlQuery("SELECT company, sum(gross) as totalgross,sum(gross)-sum(budget) as profit " +
                "from movies group by company order by profit desc limit 10")
                .findList();
        for (SqlRow row : sqlQueryList) {
            ObjectNode tableRow = Json.newObject();
            // Logger.info("id : " + row.getBigDecimal("totalgross"));
            tableRow.put("1", row.getString("company"));
            tableRow.put("2", row.getBigDecimal("totalgross"));
            tableRow.put("3", row.getBigDecimal("profit"));
            tableContents.add(tableRow);

        }
        return ok(Json.toJson(tableContents));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result topMoviesOfProduction() {

        JsonNode json = request().body().asJson();
        String name = json.findPath("data").textValue();
        Logger.info("id : " + name);
        JsonNodeFactory ar = JsonNodeFactory.instance;
        ArrayNode tableContents = new ArrayNode(ar);
        List<SqlRow> sqlQueryList = EbeanTestServer.createSqlQuery("select name,gross as earning,year from movies where company='" + name + "' order by earning desc limit 100")
                .findList();
        for (SqlRow row : sqlQueryList) {
            ObjectNode tableRow = Json.newObject();
            Logger.info("id : " + row.getString("name"));
            tableRow.put("1", row.getString("name"));
            tableRow.put("2", row.getBigDecimal("earning"));
            tableRow.put("3", row.getInteger("year"));
            tableContents.add(tableRow);

        }
        return ok(Json.toJson(tableContents));
    }
}

