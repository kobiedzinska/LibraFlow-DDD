package ReaderAccounts.infrastructure.out.persistence;

import ReaderAccounts.application.domain.model.Reader;
import ReaderAccounts.application.domain.service.ReaderFileMapper;
import ReaderAccounts.application.domain.model.*;
import ReaderAccounts.application.ports.out.persistence.IReaderRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static ReaderAccounts.application.domain.service.ReaderFileMapper.generateNextId;
import static ReaderAccounts.application.domain.service.ReaderFileMapper.mapToLine;

public class ReaderRepository implements IReaderRepository {
	private static final Path FILE_PATH =
			Path.of("Library/src/main/resources/Database/readers.txt");


/*	public void saveReader(Reader reader) {

		if (reader.getReaderId() == 0) {
            try {
                reader.setReaderId(generateNextId(Path.of(FILE_PATH)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

		List<String> lines = new ArrayList<>();
		boolean updated = false;

		File file = new File(FILE_PATH);

		if (file.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {

				String line;

				while ((line = br.readLine()) != null) {

					String[] data = line.split(";");

					int id = Integer.parseInt(data[0]);

					if (id == reader.getReaderId()) {
						lines.add(toLine(reader));
						updated = true;
					} else {
						lines.add(line);
					}
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (!updated) {
			lines.add(toLine(reader));
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

			for (String line : lines) {
				bw.write(line);
				bw.newLine();
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}*/
	@Override
	public void saveReader(Reader reader) {

		if (reader.getReaderId() == -1) {
			try {
				reader.setReaderId(ReaderFileMapper.generateNextId(FILE_PATH));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		List<String> lines = new ArrayList<>();
		boolean updated = false;

		File file = FILE_PATH.toFile();

		if (file.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {

				String line;

				while ((line = br.readLine()) != null) {

					if (line.isBlank()) continue;

					String[] data = line.split(";");
					int id = Integer.parseInt(data[0]);

					if (id == reader.getReaderId()) {
						lines.add(toLine(reader));
						updated = true;
					} else {
						lines.add(line);
					}
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (!updated) {
			lines.add(toLine(reader));
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

			for (String line : lines) {
				bw.write(line);
				bw.newLine();
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * @param readerId
	 */
	@Override
	public Reader findUser(int readerId) {

		if (!Files.exists(FILE_PATH)) {
			return null;
		}

		try (Stream<String> lines = Files.lines(FILE_PATH)) {

			return lines
					.filter(l -> l != null && !l.isBlank())
					.map(ReaderFileMapper::mapToReader)
					.filter(Objects::nonNull)
					.filter(r -> r.getReaderId() == readerId)
					.findFirst()
					.orElse(null);

		} catch (IOException e) {
			throw new RuntimeException("Cannot read readers file", e);
		}
	}

	@Override
	public boolean existsByLogin(String login) {
		if (!Files.exists(FILE_PATH)) {
			return false;
		}

		try (Stream<String> lines = Files.lines(FILE_PATH)) {
			return lines
					.filter(line -> line != null && !line.strip().isEmpty())
					.map(ReaderFileMapper::mapToReader)
					.anyMatch(r -> r != null && login.equals(r.getLogin()));

		} catch (IOException e) {
			throw new RuntimeException("Cannot read accounts file", e);
		}
	}

	@Override
	public boolean existsByEmail(String email) {
		if (!Files.exists(FILE_PATH)) {
			return false;
		}

		try (Stream<String> lines = Files.lines(FILE_PATH)) {
			return lines
					.filter(line -> line != null && !line.strip().isEmpty())
					.map(ReaderFileMapper::mapToReader)
					.anyMatch(r -> r != null && email.equals(r.getEmail()));

		} catch (IOException e) {
			throw new RuntimeException("Cannot read accounts file", e);
		}
	}

	private String toLine(Reader reader) {

		return reader.getReaderId() + ";" +
				reader.getLogin() + ";" +
				reader.getPassword() + ";" +
				reader.getEmail() + ";" +
				reader.getCreatedAt() + ";" +
				reader.getProfile() + ";" +
				reader.getBlocked();
	}
}