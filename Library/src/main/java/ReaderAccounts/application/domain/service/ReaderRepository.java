package ReaderAccounts.application.domain.service;

import ReaderAccounts.application.ports.out.*;
import ReaderAccounts.application.domain.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import static ReaderAccounts.application.domain.service.ReaderFileMapper.generateNextId;
import static ReaderAccounts.application.domain.service.ReaderFileMapper.mapToLine;

public class ReaderRepository implements IUserRepository {
	private static final Path FILE_PATH = Path.of("Database/accounts.txt");

	/**
	 *
	 * @param r
	 */
	@Override
	public void saveReader(Reader r) {
		try {
			int newId = generateNextId(FILE_PATH);
			r.setReaderId(newId);

			String line = mapToLine(r);

			Files.writeString(
					FILE_PATH,
					line + System.lineSeparator(),
					StandardOpenOption.CREATE,
					StandardOpenOption.APPEND
			);

		} catch (IOException e) {
			throw new RuntimeException("Cannot save reader", e);
		}
	}

	/**
	 *
	 * @param rId
	 */
	@Override
	public Reader findUser(int rId) {
		if (!Files.exists(FILE_PATH)) {
			return null;
		}

		try (Stream<String> lines = Files.lines(FILE_PATH)) {
			return lines
					.filter(line -> line != null && !line.strip().isEmpty())
					.map(ReaderFileMapper::mapToReader)
					.filter(r -> r != null && r.getReaderId() == rId)
					.findFirst()
					.orElse(null);

		} catch (IOException e) {
			throw new RuntimeException("Cannot read accounts file", e);
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
}