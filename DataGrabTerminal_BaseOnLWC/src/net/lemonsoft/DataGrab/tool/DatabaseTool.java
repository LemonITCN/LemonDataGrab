package net.lemonsoft.DataGrab.tool;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 工具类 - 数据库相关
 * Created by LiuRi on 16/4/21.
 */
public class DatabaseTool {

    /**
     * 从数据库连接池中获取一个数据库链接
     *
     * @return 数据库连接池中的链接
     */
    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite://" + ResourceTool.sharedInstance().getResourcePath() + "ldg_dataGrabTerminal.db");//filename为你的SQLite数
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新数据库中的数据
     *
     * @param sqlString 要执行的SQL语句
     * @param objects   SQL中要插入的对象
     * @return 执行成功返回影响的记录的行数, 失败返回-1
     */
    public static int update(String sqlString, Object... objects) {
        Connection connection = null;
        connection = getConnection();
        int result = -1;
        try {
            result = new QueryRunner().update(connection, sqlString, objects);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return result;
    }

    /**
     * 查询数据库中的数据
     *
     * @param sqlString 要执行查询的SQL语句
     * @param objects   SQL中要插入的对象
     * @return 执行成功所查寻到的数据, 查询失败返回null
     */
    public static List<Map<String, Object>> query(String sqlString, Object... objects) {
        Connection connection = null;
        connection = getConnection();
        List<Map<String, Object>> result = null;
        try {
            result = new QueryRunner().query(connection, sqlString, new MapListHandler(), objects);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return result;
    }

    /**
     * 查询指定SQL语句查询到的数量
     *
     * @param sqlString 要查询的SQL语句
     * @param objects   查询用到的对象
     * @return 查询到的数据的数量
     */
    public static int count(String sqlString, Object... objects) {
        Connection connection = null;
        connection = getConnection();
        int count = 0;
        try {
            count = Integer.valueOf(new QueryRunner().query(connection, sqlString, new ScalarHandler(), objects).toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return count;
    }

    /**
     * 对数据库的链接进行关闭
     *
     * @param connection 要关闭的数据库的链接
     */
    public static void close(Connection connection) {
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

}
