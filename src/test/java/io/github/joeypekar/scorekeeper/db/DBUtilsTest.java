package io.github.joeypekar.scorekeeper.db;

import io.github.joeypekar.scorekeeper.util.Settings;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBUtilsTest {

    static final String testID1 = "FirstTest";
    static final String testID2 = "SecondTest";

    static Settings settings = new Settings();
    static TestBoard test1 = new TestBoard();
    static TestBoard test2 = new TestBoard();

    @BeforeAll
    static void settings() {

        settings.setUrl("jdbc:mysql://127.0.0.1:3306/scorekeeper_test");
        settings.setUsername("root");
        settings.setPassword("");

    }

    @BeforeAll
    static void setupUpTest() {

        test1.setId(testID1);
        test1.setIntegerTest(5);
        test1.setDoubleTest(10.5);


        test2.setId(testID2);
        test2.setIntegerTest(8);
        test2.setDoubleTest(25.64);

    }


    @Test
    @Order(1)
    void testTableCreation() {

        /* Run Create Table with TestBoard.class */
        DBUtils.createTable(TestBoard.class, settings);

        assertTrue(DBUtils.doesTableExist(TestBoard.class, settings));


    }

    @Test
    @Order(2)
    void testWrite() {


        DBUtils.writeToDatabase(test1, settings);
        assertTrue(DBUtils.doesRowExist(testID1, TestBoard.class, settings));

        DBUtils.writeToDatabase(test2, settings);
        assertTrue(DBUtils.doesRowExist(testID2, TestBoard.class, settings));

    }

    @Test
    @Order(3)
    void testRead() {

        /* Test 1 */
        TestBoard DBTest1 = DBUtils.readFromDatabase(testID1, TestBoard.class, settings);

        assertEquals(test1.getId(), DBTest1.getId());
        assertEquals(test1.getIntegerTest(), DBTest1.getIntegerTest());
        assertEquals(test1.getDoubleTest(), DBTest1.getDoubleTest());

        TestBoard DBTest2 = DBUtils.readFromDatabase(testID2, TestBoard.class, settings);

        assertEquals(test2.getId(), DBTest2.getId());
        assertEquals(test2.getIntegerTest(), DBTest2.getIntegerTest());
        assertEquals(test2.getDoubleTest(), DBTest2.getDoubleTest());

    }

    @AfterAll
    static void dropTable() {

        try {

            String query = "drop table " + TestBoard.class.getSimpleName() + ";";

            Connection conn = DriverManager.getConnection(settings.getUrl(), settings.getUsername(), settings.getPassword());
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            stmt.close();
            conn.close();

            System.out.println("Table Successfully Dropped");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

}