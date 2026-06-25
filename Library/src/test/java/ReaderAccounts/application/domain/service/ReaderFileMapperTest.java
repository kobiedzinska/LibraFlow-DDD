package ReaderAccounts.application.domain.service;

import ReaderAccounts.application.domain.model.Profile;
import ReaderAccounts.application.domain.model.Reader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ReaderFileMapper (Identity persistence mapping)")
class ReaderFileMapperTest {

    private Reader sampleReader() {
        return new Reader("jkowalski", "secret", "j@k.pl",
                LocalDateTime.of(2026, 1, 1, 9, 0),
                new Profile("Jan", "Kowalski", "90010112345"),
                5, false);
    }

    @Test
    @DisplayName("maps a reader to a line and back without losing core fields")
    void roundTrip() {
        Reader original = sampleReader();

        String line = ReaderFileMapper.mapToLine(original);
        Reader parsed = ReaderFileMapper.mapToReader(line);

        assertAll(
                () -> assertNotNull(parsed),
                () -> assertEquals(5, parsed.getReaderId()),
                () -> assertEquals("jkowalski", parsed.getLogin()),
                () -> assertEquals("j@k.pl", parsed.getEmail()),
                () -> assertEquals(false, parsed.getBlocked()),
                () -> assertEquals("Kowalski", parsed.getProfile().getSurname()),
                () -> assertEquals("90010112345", parsed.getProfile().getPESEL())
        );
    }

    @Test
    @DisplayName("a malformed line (too few fields) maps to null")
    void malformedLine() {
        assertNull(ReaderFileMapper.mapToReader("1;onlyTwo"));
    }

    @Test
    @DisplayName("generateNextId returns 1 when the file does not exist")
    void nextIdForMissingFile() throws IOException {
        Path missing = Path.of("does-not-exist-" + System.nanoTime() + ".txt");
        assertEquals(1, ReaderFileMapper.generateNextId(missing));
    }

    @Test
    @DisplayName("generateNextId returns max(id)+1 for an existing file")
    void nextIdFromFile() throws IOException {
        Path tmp = Files.createTempFile("readers", ".txt");
        try {
            Files.writeString(tmp,
                    ReaderFileMapper.mapToLine(sampleReader()) + System.lineSeparator());
            assertEquals(6, ReaderFileMapper.generateNextId(tmp));
        } finally {
            Files.deleteIfExists(tmp);
        }
    }
}
