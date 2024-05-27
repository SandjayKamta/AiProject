package Settings;

import com.example.chatbot42_project2_groep_7a.db.DatabaseConnection;
import com.example.chatbot42_project2_groep_7a.db.Queries;
import com.example.chatbot42_project2_groep_7a.db.SHA256Encryption;
import com.example.chatbot42_project2_groep_7a.db.SqlExecutor;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class QueriesTest {
        @Test
        public void testSelect_ReturnsResultSet() throws SQLException {
            // Create a mock SqlExecutor
            SqlExecutor sqlExecutor = Mockito.mock(SqlExecutor.class);

            // Create a mock ResultSet
            ResultSet mockResultSet = Mockito.mock(ResultSet.class);

            // Set up the mock behavior for the executeQuery method
            Mockito.when(sqlExecutor.executeQuery(ArgumentMatchers.any(String.class), ArgumentMatchers.eq("test@example.com"), ArgumentMatchers.eq("password")))
                    .thenReturn(mockResultSet);

            // Create an instance of Queries with the mock SqlExecutor
            Queries queries = new Queries(sqlExecutor);

            // Call the method under test
            ResultSet result = queries.Select("test@example.com", "password");

            // Assert that the result is not null
            Assertions.assertEquals(mockResultSet, result);
        }

        @Test
        public void testSelect_GetsData() throws SQLException {
            // Create a mock SqlExecutor
            SqlExecutor sqlExecutor = Mockito.mock(SqlExecutor.class);

            // Create a mock ResultSet
            ResultSet mockResultSet = Mockito.mock(ResultSet.class);

            // Set up the mock behavior for the executeQuery method
            Mockito.when(sqlExecutor.executeQuery(ArgumentMatchers.any(String.class), ArgumentMatchers.eq("test@example.com"), ArgumentMatchers.eq("password")))
                    .thenReturn(mockResultSet);

            // Create an instance of Queries with the mock SqlExecutor
            Queries queries = new Queries(sqlExecutor);

            // Call the method under test
            ResultSet result = queries.Select("test@example.com", "password");

            String mockEmail = mockResultSet.getString("email");
            String mockPassword = mockResultSet.getString("password");

            String expectedEmail = result.getString("email");
            String expectedPassword = result.getString("password");

            // Assert that the result is not null
            Assertions.assertEquals(mockEmail, expectedEmail);
            Assertions.assertEquals(mockPassword, expectedPassword);
        }


    }


