package fr.armotik.naurelliamoderation.utiles;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.armotik.naurelliamoderation.NaurelliaModeration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static final Logger logger = Logger.getLogger(Database.class.getName());
    private static HikariDataSource dataSource;
    private static final List<String> databaseStringList = NaurelliaModeration.getPlugin().getConfig().getStringList("Database");

    private Database() {
        throw new IllegalStateException("Utility Class");
    }

    private static void init() {

        if (dataSource != null && !dataSource.isClosed()) return;

        try {

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(databaseStringList.get(0));
            config.setUsername(databaseStringList.get(1));
            config.setPassword(databaseStringList.get(2));
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setMinimumIdle(10);
            config.setMaximumPoolSize(100);
            config.setMaxLifetime(30000L);

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {

            logger.severe("Error initializing the database connection pool \n" + e);
        }
    }

    /**
     * Get new connection instance
     *
     * @return connection instance or null in case of error
     */
    public static Connection getConnection() {

        init();

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            return null;
        }
    }

    /**
     * Execute Query
     *
     * @param sqlQuery query to execute
     * @return ResultSet or null in case of error
     */
    public static ResultSet executeQuery(String sqlQuery) {

        Connection conn = getConnection();
        PreparedStatement statement = null;

        try {
            assert conn != null;
            statement = conn.prepareStatement(sqlQuery);
            return statement.executeQuery();

        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            return null;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {

                ExceptionsManager.sqlExceptionLog(e);
            }
        }
    }

    /**
     * Update Query
     *
     * @param sqlQuery query to execute
     * @return the number new lines or -1 in case of error
     */
    public static int executeUpdate(String sqlQuery) {

        Connection conn = getConnection();
        PreparedStatement statement = null;

        try {
            assert conn != null;
            statement = conn.prepareStatement(sqlQuery);
            return statement.executeUpdate();

        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
            return -1;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {

                ExceptionsManager.sqlExceptionLog(e);
            }
        }
    }

    /**
     * Close current connection
     */
    public static void close() {

        if (dataSource != null && !dataSource.isClosed()) {

            dataSource.close();
        }

    }

    /**
     * Close the data source
     */
    public static void closeAll() {


        if (dataSource == null) return;

        dataSource.close();
    }

    /**
     * Test Database
     */
    public static void databaseTest() {

        Connection conn = getConnection();

        PreparedStatement statement = null;

        try {
            assert conn != null;
            statement = conn.prepareStatement("SELECT 2+2 as solution");
            ResultSet res = statement.executeQuery();

            if (res.next()) {

                assert res.getInt("solution") == 4;
                logger.log(Level.INFO, "[NaurelliaModeration] -> Database -  Database test : OK !");

                res.close();
            }

        } catch (SQLException e) {
            ExceptionsManager.sqlExceptionLog(e);
        } finally {
            try {
                if (statement != null) statement.close();

                if (conn != null) conn.close();
            } catch (SQLException e) {

                ExceptionsManager.sqlExceptionLog(e);
            }
        }
    }
}
