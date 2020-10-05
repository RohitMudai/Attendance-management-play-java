package controllers.DAO;

import io.ebean.*;

public class EbeanTestServer {
    private static EbeanServer testDB = Ebean.getServer("test");

    public static ExpressionFactory expression() {
        return testDB.getExpressionFactory();
    }

    public static <T> Query<T> createQuery(Class<T> beanType) {
        return testDB.createQuery(beanType);
    }

    public static <T> SqlQuery createSqlQuery(String sql) {
        return testDB.createSqlQuery(sql);
    }

    public static void beginTransaction() {
        testDB.beginTransaction();
    }

    public static void commitTransaction() {
        testDB.commitTransaction();
    }

    public static void rollbackTransaction() {
        testDB.rollbackTransaction();
    }

    public static void endTransaction() {
        testDB.endTransaction();
    }


    public static void save(java.lang.Object bean) {
        testDB.save(bean);
    }

    public static void update(java.lang.Object bean) {
        testDB.update(bean);
    }

    public static void delete(java.lang.Object bean) {
        testDB.delete(bean);
    }

    public static void refresh(java.lang.Object bean) {
        testDB.refresh(bean);
    }

    public static SqlUpdate createUpdateQuery(String s) {
        return testDB.createSqlUpdate(s);
    }


    public static void execute(SqlUpdate sqlUpdate) {
        testDB.execute(sqlUpdate);
    }

    public static EbeanServer getServer(String serverName) {
        return testDB;
    }

    public static <T> SqlQuery createMagentoSqlQuery(String sql) {
        return testDB.createSqlQuery(sql);
    }
}
