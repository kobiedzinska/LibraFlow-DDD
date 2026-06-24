package Catalog.infrastructure.out;

import Catalog.application.ports.out.*;
import Catalog.application.domain.model.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BookRepository implements IBookRepository {

	private static final String FILE_PATH =
			"Library/src/main/resources/Database/book.txt";

	@Override
	public void saveBook(Book book) {


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

					if (id.equals(book.getBookId())) {

						lines.add(toLine(book));
						found = true;

					} else {

						lines.add(line);
					}
				}

				reader.close();
			}

			if (!found) {
				lines.add(toLine(book));
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
	public Book findBook(int bookId) {

		try {

			BufferedReader reader =
					new BufferedReader(new FileReader(FILE_PATH));

			String line;

			while ((line = reader.readLine()) != null) {

				String[] parts = line.split(";");

				Integer id =
						Integer.parseInt(parts[0]);

				if (id.equals(bookId)) {

					reader.close();

					return new Book(
							id,
							parts[1],
							parts[2],
							parts[3],
							parts[4],
							parts[5],
							Arrays.asList(parts[6].split(",")),
							parts[7]
					);
				}
			}

			reader.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	private String toLine(Book book) {

		String categories =
				String.join(",", book.getCategory());

		return book.getBookId() + ";" +
				book.getTitle() + ";" +
				book.getAuthor() + ";" +
				book.getISBN() + ";" +
				book.getPublisher() + ";" +
				book.getPublicationYear() + ";" +
				categories + ";" +
				book.getDescription();
	}


	@Override
	public List<Book> search(String query) {

		List<Book> result = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

			String line;

			while ((line = reader.readLine()) != null) {

				if (line.isBlank()) continue;

				String[] parts = line.split(";");

				Book book = new Book(
						Integer.parseInt(parts[0]),
						parts[1],
						parts[2],
						parts[3],
						parts[4],
						parts[5],
						List.of(parts[6].split(",")),
						parts[7]
				);

				if (matches(book, query)) {
					result.add(book);
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	private boolean matches(Book b, String q) {
		String query = q.toLowerCase();

		return b.getTitle().toLowerCase().contains(query)
				|| b.getAuthor().toLowerCase().contains(query)
				|| b.getISBN().equals(query);
	}
}