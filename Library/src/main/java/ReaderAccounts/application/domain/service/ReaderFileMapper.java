package ReaderAccounts.application.domain.service;

import ReaderAccounts.application.domain.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class ReaderFileMapper {

    private static final String SEP = ";";

    public static String mapToLine(Reader r) {
        Profile p = r.getProfile();
        return String.join(SEP,
                String.valueOf(r.getReaderId()),
                nullSafe(r.getLogin()),
                nullSafe(r.getPassword()),
                nullSafe(r.getEmail()),
                r.getCreatedAt() == null ? "" : r.getCreatedAt().toString(),
                String.valueOf(r.getBlocked()),
                p == null ? "" : nullSafe(p.getName()),
                p == null ? "" : nullSafe(p.getSurname()),
                p == null ? "" : nullSafe(p.getPESEL())
        );
    }

    public static Reader mapToReader(String line) {
        String[] f = line.split(SEP, -1);
        if (f.length < 6) {
            return null;
        }

        Profile profile = new Profile();

        if (f.length > 6) profile.setName(emptyToNull(f[6]));
        if (f.length > 7) profile.setSurname(emptyToNull(f[7]));
        if (f.length > 8) profile.setPESEL(emptyToNull(f[8]));

        Reader r = new Reader();
        r.setReaderId(parseInt(f[0]));
        r.setLogin(emptyToNull(f[1]));
        r.setPassword(emptyToNull(f[2]));
        r.setEmail(emptyToNull(f[3]));
        r.setCreatedAt(f[4].isEmpty() ? null : LocalDateTime.parse(f[4]));
        r.setBlocked(Boolean.parseBoolean(f[5]));
        r.setProfile(profile);

        return r;
    }

    public static int generateNextId(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            return 1;
        }
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines
                    .filter(l -> l != null && !l.strip().isEmpty())
                    .map(l -> l.split(SEP, -1)[0])
                    .mapToInt(ReaderFileMapper::parseInt)
                    .max()
                    .orElse(0) + 1;
        }
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s.strip());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private static String emptyToNull(String s) {
        return s == null || s.isEmpty() ? null : s;
    }
}