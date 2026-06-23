package Payments.infrastructure.out.persistence;

import Payments.application.domain.model.*;
import Payments.application.ports.out.persistence.IPaymentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import static Payments.infrastructure.out.persistence.PaymentFileMapper.generateNextId;
import static Payments.infrastructure.out.persistence.PaymentFileMapper.mapToLine;

public class PaymentRepository implements IPaymentRepository {
	private static final Path FILE_PATH = Path.of("payments.txt");


	/**
	 * 
	 * @param paymentId
	 */
	@Override
	public Payment findPayment(int paymentId) {
		if (!Files.exists(FILE_PATH)) {
			return null;
		}

		try (Stream<String> lines = Files.lines(FILE_PATH)) {
			return lines
					.filter(line -> line != null && !line.strip().isEmpty())
					.map(PaymentFileMapper::mapToPayment)
					.filter(p -> p != null && p.getPaymentId() == paymentId)
					.findFirst()
					.orElse(null);

		} catch (IOException e) {
			throw new RuntimeException("Cannot read payments file", e);
		}
	}


	@Override
	public void savePayment(Payment p) {
		try {
			int newId = generateNextId(FILE_PATH);
			p.setPaymentId(newId);

			String line = mapToLine(p);

			Files.writeString(
					FILE_PATH,
					line + System.lineSeparator(),
					StandardOpenOption.CREATE,
					StandardOpenOption.APPEND
			);

		} catch (IOException e) {
			throw new RuntimeException("Cannot save payment", e);
		}
	}


}