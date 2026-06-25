package UI;

import Loan.application.domain.model.CopyOverdue;
import Loan.application.domain.model.CopyReturned;
import Payments.application.domain.model.PaymentCompleted;
import SharedKernel.DomainEvent;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UIEventListener {
    private final TextField textField;

    public UIEventListener(TextField textField) {
        this.textField = textField;
    }


    public void onEvent(DomainEvent event){
        Platform.runLater(() ->
                textField.setText(event.getClass().getSimpleName())
        );
    }
}
