package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

/**
 * Created by Olga on 07.04.2016.
 */
public class DbConnectionTest {

    @Test
    public void testDbConection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook?user=root&password=");

            //создаем объект для извлечения данных из БД
            Statement st = conn.createStatement();

            // извлекаем сет строк таблицы
            ResultSet resultSet = st.executeQuery("select group_id, group_name, group_header, group_footer from group_list");

            Groups groups = new Groups();
            
            // пробегаем по множеству результатов, каждый шаг resultSet - это указатель на одну строку таблицы
            while (resultSet.next()) {
            groups.add(new GroupData().setId(resultSet.getInt("group_id")).setName(resultSet.getString("group_name")).
                    setHeader(resultSet.getString("group_header")).setFooter(resultSet.getString("group_footer")));
        }
            resultSet.close();
            st.close();
            conn.close();

            System.out.println(groups);

            // Do something with the Connection

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
