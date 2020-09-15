import com.pnmac.pam.data.model.v3.loan.Loan;

public class RunApp {

    public static void main(String[] args) {
        Loan loan = new Loan();
        loan.setUnderwritingEscrowIndicator(true);
        AppFunctions appFunctions = new AppFunctions();
        appFunctions.runProcess(loan);
        //appFunctions.runWithData(loan);
        //runProcess(loan);
        //runWithData(loan);
        System.out.println(loan.getProposedDuesAmount());
        System.out.println(loan.getLoanIssues().getIssues().contains("12345678-90ab-cdef-ghij-klmnopqrstuv"));
    }
}
