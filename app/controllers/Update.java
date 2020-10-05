package controllers;

import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import io.ebean.SqlUpdate;
import play.Logger;
import controllers.DAO.EbeanTestServer;

import java.util.List;

import play.mvc.Result;
import util.encryption.Encryption;
import util.encryption.EncryptionFactory;

import static play.mvc.Results.ok;

public class Update {

    public Result updatePassword()
    {
        SqlQuery sqlQuery = EbeanTestServer.createSqlQuery("select * from login_details where email_id='narenkanan@gmail.com'");
        List<SqlRow> rows = sqlQuery.findList();
        for (SqlRow row: rows
             ) {
            Logger.info("password " + row.getString("password"));
            Encryption encryption = EncryptionFactory.getInstance();
            String enryptedPassword = encryption.encrypt(row.getString("password"));
            SqlUpdate sqlUpdateout = EbeanTestServer.createUpdateQuery("update login_details set password='" + enryptedPassword + "' where email_id='"
                    + row.getString("email_id") + "' and id='" + row.getString("id") + "'");
            sqlUpdateout.execute();

        }
        return ok(views.html.error.render());
    }
}
