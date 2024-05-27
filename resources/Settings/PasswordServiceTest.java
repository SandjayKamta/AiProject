package Settings;

import com.example.chatbot42_project2_groep_7a.ui.Settings.PasswordService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordServiceTest {
//Deze test controleerd of het nieuwe wachtwoord en de bevestiging van het nieuwe wachtwoord met elkaar overeenkomen.

    @Test
    public void testIsWachtwoordGelijk() {
        String password1 = "password";
        String password2 = "password";

        Assertions.assertTrue(PasswordService.isWachtwoordGelijk(password1, password2));

        password2 = "ppaasswwoorrdd";
        Assertions.assertFalse(PasswordService.isWachtwoordGelijk(password1, password2));
    }
}