package com.pp;

import com.pp.pojo.User;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JdbcTest {


    @Test
    public  void testUpdate() throws Exception {
        //1.加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.创建连接
        String url="jdbc:mysql://localhost:3306/web01";
        String user="root";
        String password="root";
        Connection connection = DriverManager.getConnection(url, user, password);
        //3.获取执行sql语句的对象
        Statement statement = connection.createStatement();
        //4.执行语句
        String sql="update user set age=20 where id=1";
        int count = statement.executeUpdate(sql);
        System.out.println("sql语句执行完毕后影响的记录数为"+count);
        //5.释放资源
        statement.close();
        connection.close();
    }



    @Test//查询sql并把每条数据返回到User对象中
    public  void testSelect() {
        // 数据库连接信息
        String URL = "jdbc:mysql://localhost:3306/web01";
        String USERNAME = "root";
        String PASSWORD = "root";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;//封装查询返回的结果
        List<User> users = new ArrayList<>();

        try {
            // 加载数据库驱动（MySQL 8.0+ 使用 mysql-connector-j）
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 获取数据库连接
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // 定义 SQL 查询语句
            String sql = "SELECT id, username, password, name, age FROM user WHERE username = ? AND password = ?";//预编译SQL
            pstmt = conn.prepareStatement(sql);//执行sql的对象
            pstmt.setString(1, "daqiao");
            pstmt.setString(2, "123456");

            // 执行查询
            rs = pstmt.executeQuery();

            // 遍历结果集，将每一行数据封装到 User 对象中
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                users.add(user);
            }

            // 输出所有查询到的 User 对象
            for (User u : users) {
                System.out.println(u);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败：" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("数据库操作异常：" + e.getMessage());
        } finally {
            // 关闭资源
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("资源关闭异常：" + e.getMessage());
            }
        }
}
}