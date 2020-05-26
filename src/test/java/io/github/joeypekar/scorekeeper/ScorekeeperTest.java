package io.github.joeypekar.scorekeeper;

import io.github.joeypekar.scorekeeper.db.DBUtils;
import io.github.joeypekar.scorekeeper.db.TestBoard;
import io.github.joeypekar.scorekeeper.util.Board;
import io.github.joeypekar.scorekeeper.util.Settings;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ScorekeeperTest {

    static Settings settings = new Settings();
    static Scorekeeper scorekeeperInstance;

    @BeforeAll
    static void settings() {


        settings.setUrl("jdbc:mysql://127.0.0.1:3306/scorekeeper_test");
        settings.setUsername("root");
        settings.setPassword("");

    }


    @Test
    @Order(1)
    void testScorekeeperSetup() {

        scorekeeperInstance = new Scorekeeper(settings);

        scorekeeperInstance.addScoreboard("Test", TestBoard.class);

        scorekeeperInstance.activeBoards.get("Test").login("TestUser");

        ((TestBoard) scorekeeperInstance.activeBoards.get("Test").members.get("TestUser")).incrementIntTest();
        ((TestBoard) scorekeeperInstance.activeBoards.get("Test").members.get("TestUser")).incrementDoubleTest(2.5);


        scorekeeperInstance.activeBoards.get("Test").logout("TestUser");

        assertTrue(DBUtils.doesTableExist(TestBoard.class, settings));


    }

    @Test
    @Order(2)
    void testLoginAndLogout() {

        scorekeeperInstance.activeBoards.get("Test").login("TestUser");

        assertTrue(DBUtils.doesRowExist("TestUser", TestBoard.class, settings));

        /* */
        TestBoard user = new TestBoard();
        user.incrementIntTest();
        user.incrementDoubleTest(2.5);

        assertEquals(user.getIntegerTest(), ((TestBoard) scorekeeperInstance.activeBoards.get("Test").members.get("TestUser")).getIntegerTest());
        assertEquals(user.getDoubleTest(), ((TestBoard) scorekeeperInstance.activeBoards.get("Test").members.get("TestUser")).getDoubleTest());
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