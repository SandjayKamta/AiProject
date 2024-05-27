package Settings;

import com.example.chatbot42_project2_groep_7a.ui.Settings.EmailService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class EmailServiceTest {
    //Deze test controleerd of het nieuwe email adres en de bevestiging van het nieuwe email adres met elkaar overeenkomen.
    @Test
    public void testIsEmailGelijk() {
        Assertions.assertTrue(EmailService.isEmailGelijk("example@example.com", "example@example.com"));
        Assertions.assertFalse(EmailService.isEmailGelijk("example@example.com", "wrongemail@wrongemail.com"));
    }
}