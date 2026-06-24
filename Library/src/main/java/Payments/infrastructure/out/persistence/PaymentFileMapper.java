package Payments.infrastructure.out.persistence;

import Payments.application.domain.model.Payment;
import Payments.application.domain.model.PaymentStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class PaymentFileMapper {
    static int generateNextId(Path filePath) throws IOException {

        if (!Files.exists(filePath)) {
            return 1;
        }

        return Files.lines(filePath)
                .map(line -> line.split(";")[0]) // paymentId
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0) + 1;
    }
    static String mapToLine(Payment p) {
        return p.getPaymentId() + ";" +
                p.getReaderId() + ";" +
                p.getLoanId() + ";" +
                p.getAmount() + ";" +
                p.getDueDate() + ";" +
                p.getPaymentStatus();
    }
/*    public static Payment mapToPayment(String line) {

        String[] parts = line.split(";");

        int paymentId = Integer.parseInt(parts[0]);
        int readerId = Integer.parseInt(parts[1]);
        int loanId = Integer.parseInt(parts[2]);
        double amount = Double.parseDouble(parts[3]);
        LocalDateTime dueDate = LocalDateTime.parse(parts[4]);
        PaymentStatus status = PaymentStatus.valueOf(parts[5]);

        Payment p = new Payment(readerId, loanId, amount);

        p.setPaymentId(paymentId);
        p.setDueDate(dueDate);
        p.setPaymentStatus(status);

        return p;
    }*/

    public static Payment mapToPayment(String line) {

        String[] parts = line.split(";");

        int paymentId = Integer.parseInt(parts[0]);
        int readerId = Integer.parseInt(parts[1]);
        int loanId = Integer.parseInt(parts[2]);
        double amount = Double.parseDouble(parts[3]);
        LocalDateTime dueDate = LocalDateTime.parse(parts[4]);
        PaymentStatus status = PaymentStatus.valueOf(parts[5]);

        return new Payment(
                paymentId,
                readerId,
                loanId,
                amount,
                dueDate,
                status
        );
    }
}
