package Loan.infrastructure.out;

import Loan.application.domain.model.Loan;
import Loan.application.domain.model.LoanStatus;
import Loan.application.port.out.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository implements ILoanRepository {

	private static final String FILE_PATH =
			"Library/src/main/resources/Database/loan.txt";

	@Override
	public void saveLoan(Loan loan) {
		if (loan.getLoanId() == -1) {
			loan.setLoanId(getNextId());
		}

		List<String> lines = new ArrayList<>();
		boolean updated = false;

		File file = new File(FILE_PATH);

		if (file.exists()) {

			try (BufferedReader reader =
						 new BufferedReader(new FileReader(file))) {

				String line;

				while ((line = reader.readLine()) != null) {

					String[] data = line.split(";");

					Integer currentId =
							Integer.parseInt(data[0]);

					if (currentId.equals(loan.getLoanId())) {

						lines.add(toLine(loan));
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
			lines.add(toLine(loan));
		}

		try (BufferedWriter writer =
					 new BufferedWriter(new FileWriter(file))) {

			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Loan findLoan(int loanId) {
		System.out.println("findLoan");

		try (BufferedReader reader =
					 new BufferedReader(new FileReader(FILE_PATH))) {

			String line;

			while ((line = reader.readLine()) != null) {

				String[] data = line.split(";");

				int id = Integer.parseInt(data[0]); // FIX

				if (id == loanId) { // FIX (int -> ==)

					return new Loan(
							id,
							Integer.parseInt(data[1]),
							Integer.parseInt(data[2]),
							LocalDateTime.parse(data[3]),
							LocalDateTime.parse(data[4]),
							LoanStatus.valueOf(data[5]),
							"null".equals(data[6])
									? null
									: LocalDateTime.parse(data[6])
					);
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	@Override
	public int countActiveLoansByReaderId(int readerId) throws IOException {

		return (int) Files.lines(Path.of(FILE_PATH))
				.map(line -> line.split(";"))
				.filter(data -> Integer.parseInt(data[1]) == readerId)
				.filter(data -> data[5].equals("BORROWED") || data[5].equals("OVERDUE"))
				.count();
	}


	private int getNextId() {
		int maxId = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

			String line;
			while ((line = reader.readLine()) != null) {
				int id = Integer.parseInt(line.split(";")[0]);
				maxId = Math.max(maxId, id);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return maxId + 1;
	}

	private String toLine(Loan loan) {

		return loan.getLoanId() + ";" +
				loan.getReaderId() + ";" +
				loan.getCopyId() + ";" +
				loan.getDueDate() + ";" +
				loan.getBorrowedAt() + ";" +
				loan.getLoanStatus() + ";" +
				(loan.getReturnedAt() == null
						? "null"
						: loan.getReturnedAt());
	}
}