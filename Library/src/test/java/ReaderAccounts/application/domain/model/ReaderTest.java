package ReaderAccounts.application.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Reader aggregate (Identity / ReaderAccounts Context)")
class ReaderTest {

    @Test
    @DisplayName("block() sets the blocked flag (PU4)")
    void block() {
        Reader reader = new Reader();
        reader.block();
        assertTrue(reader.getBlocked());
    }

    @Test
    @DisplayName("unblock() clears the blocked flag")
    void unblock() {
        Reader reader = new Reader();
        reader.block();
        reader.unblock();
        assertFalse(reader.getBlocked());
    }

    @Test
    @DisplayName("the full constructor keeps inherited User fields and reader fields")
    void fullConstructor() {
        Profile profile = new Profile("Jan", "Kowalski", "90010112345");
        Reader reader = new Reader("login", "pass", "mail@x.pl", null, profile, 5, true);

        assertAll(
                () -> assertEquals("login", reader.getLogin()),
                () -> assertEquals("mail@x.pl", reader.getEmail()),
                () -> assertEquals(5, reader.getReaderId()),
                () -> assertTrue(reader.getBlocked()),
                () -> assertEquals("Kowalski", reader.getProfile().getSurname())
        );
    }
}
