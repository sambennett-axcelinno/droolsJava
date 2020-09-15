import com.pnmac.pam.data.model.v3.loan.Loan;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntime;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.rule.FactHandle;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Test;

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
