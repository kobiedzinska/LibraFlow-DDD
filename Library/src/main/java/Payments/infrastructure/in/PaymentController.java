package Payments.infrastructure.in;

import Payments.application.ports.in.*;

public class PaymentController {

	private IPaymentUseCase finePaymentService;

	public PaymentController(IPaymentUseCase finePaymentService) {
		this.finePaymentService = finePaymentService;
	}

	public void processPayment(int paymentId){
		finePaymentService.processPayment(paymentId);
	}
}