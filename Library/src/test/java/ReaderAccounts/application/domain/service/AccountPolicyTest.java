package ReaderAccounts.application.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Account domain services (Identity / ReaderAccounts Context)")
class AccountPolicyTest {

    @Nested
    @DisplayName("CreateAccount — uniqueness rule (PU5)")
    class Create {

        private final CreateAccount policy = new CreateAccount();

        @Test
        @DisplayName("login and email both free -> unique")
        void bothFree() {
            assertTrue(policy.checkUnique(false, false));
        }

        @Test
        @DisplayName("taken login -> not unique")
        void loginTaken() {
            assertFalse(policy.checkUnique(true, false));
        }

        @Test
        @DisplayName("taken email -> not unique")
        void emailTaken() {
            assertFalse(policy.checkUnique(false, true));
        }
    }

    @Nested
    @DisplayName("DeleteAccount — deletion rule (PU5)")
    class Delete {

        private final DeleteAccount policy = new DeleteAccount();

        @Test
        @DisplayName("no loans and no pending payments -> can delete")
        void cleanAccount() {
            assertTrue(policy.canDelete(false, false));
        }

        @Test
        @DisplayName("active loans block deletion")
        void activeLoansBlock() {
            assertFalse(policy.canDelete(true, false));
        }

        @Test
        @DisplayName("pending payments block deletion")
        void pendingPaymentsBlock() {
            assertFalse(policy.canDelete(false, true));
        }
    }
}
