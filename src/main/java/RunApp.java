import com.pnmac.pam.data.model.v3.loan.Loan;
import org.apache.poi.ss.formula.functions.Finance;
import com.pnmac.pam.utils.Utils;

public class RunApp {

    public static void main(String[] args) {
        Loan loan = new Loan();
        loan.setUnderwritingEscrowIndicator(true);
        AppFunctions appFunctions = new AppFunctions();
        //appFunctions.runProcess(loan);
        //appFunctions.runWithData(loan);
        //runProcess(loan);
        //runWithData(loan);
        //System.out.println(loan.getProposedDuesAmount());
        //System.out.println(loan.getLoanIssues().getIssues().contains("12345678-90ab-cdef-ghij-klmnopqrstuv"));

        Double pmt = (-1*Finance.pmt(.0275/12, 360, 152575));
        Double ppmt = (-1*Finance.ppmt(.0275/12, 12, 360, 152575));
        Double v = (1+(.1/12));
        Integer t = (-(36/12)*12);
        Double result =(1000000*(10.0/12))/(1-Math.pow(v,t));
        System.out.println(result);
        System.out.println(Utils.formatDouble(pmt));
        System.out.println(Utils.formatDouble(ppmt));
        System.out.println(2.75/100);
        System.out.println(appFunctions.calculateCDMonthlyAmount(2.75, 360, 152575.0));
    }
}
