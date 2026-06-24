package Catalog.infrastructure.out;

import Catalog.application.ports.out.*;
import Catalog.application.domain.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CopyRepository implements ICopyRepository {

	private static final String FILE_PATH =
			"Library/src/main/resources/Database/copy.txt";

	@Override
	public void saveCopy(Copy copy) {

		List<String> lines = new ArrayList<>();
		boolean found = false;

		try {

			File file = new File(FILE_PATH);

			if (file.exists()) {

				BufferedReader reader =
						new BufferedReader(new FileReader(file));

				String line;

				while ((line = reader.readLine()) != null) {

					String[] parts = line.split(";");

					Integer id =
							Integer.parseInt(parts[0]);

					if (id.equals(copy.getCopyId())) {

						lines.add(toLine(copy));
						found = true;

					} else {

						lines.add(line);
					}
				}

				reader.close();
			}

			if (!found) {
				lines.add(toLine(copy));
			}

			BufferedWriter writer =
					new BufferedWriter(new FileWriter(file));

			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}

			writer.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Copy findCopy(int copyId) {

		try {

			BufferedReader reader =
					new BufferedReader(new FileReader(FILE_PATH));

			String line;

			while ((line = reader.readLine()) != null) {

				String[] parts = line.split(";");

				Integer id =
						Integer.parseInt(parts[0]);

				if (id.equals(copyId)) {

					reader.close();

					return new Copy(
							id,
							Integer.parseInt(parts[1]),
							CopyStatus.valueOf(parts[2])
					);
				}
			}

			reader.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	private String toLine(Copy copy) {

		return copy.getCopyId() + ";" +
				copy.getBookId() + ";" +
				copy.getCopyStatus();
	}
}