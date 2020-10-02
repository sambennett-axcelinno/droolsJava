import com.pnmac.pam.data.model.v3.loan.Loan;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntime;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.runtime.rule.FactHandle;
import org.apache.poi.ss.formula.functions.Finance;
import com.pnmac.pam.utils.Utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class AppFunctions {

    public void runProcess(Loan loan) {
        String groupID = "com.myspace";
        String artifactId = "PNMACTestProject";
        String version = "1.0.1-SNAPSHOT";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("loanMain", loan);

        KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId(groupID, artifactId, version);
        KieContainer kContainer = ks.newKieContainer(releaseId);
        KieSession kSession = kContainer.newKieSession();
        KieRuntime kRuntime = (KieRuntime) kSession;
        ProcessInstance processInstance = kRuntime.startProcess("PNMACTestProject.TestProcess", params);
        System.out.println("Completed");
    }

    public void runWithData(Loan loan) {
        String groupID = "com.myspace";
        String artifactId = "PNMACTestProject";
        String version = "1.0.1-SNAPSHOT";

        KieServices ks = KieServices.Factory.get();
        ReleaseId releaseId = ks.newReleaseId(groupID,  artifactId,  version);
        KieContainer kContainer = ks.newKieContainer(releaseId);
        KieSession kSession = kContainer.newKieSession();
        kSession.getAgenda().getAgendaGroup("Hello").setFocus();

        FactHandle factHandle;
        factHandle = kSession.insert(loan);

        int num = kSession.fireAllRules();
        System.out.println(num);
        System.out.println("Completed");
    }

    private Double calculateAverage(List<Double> marks) {
        Double sum = 0.0;
        if(!marks.isEmpty()) {
            for (Double mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
    }

    public Double calculateTwelveMonthAverage(Double interestRate, Integer numOfPayments, Double loanAmount) {
        List<Double> remainingBalanceList = new ArrayList<Double>();
        Double moveRateDecimalPlace = interestRate/100;
        Double rateDivide = moveRateDecimalPlace/12;
        Double ppmt = (-1*Finance.ppmt(rateDivide, 1, numOfPayments, loanAmount));
        Double remainingBalance = loanAmount - ppmt;
        remainingBalanceList.add(remainingBalance);
        for (int i = 2; i <= 12; i++) {
            ppmt = (-1*Finance.ppmt(rateDivide, i, numOfPayments, loanAmount));
            remainingBalance = remainingBalance - ppmt;
            remainingBalanceList.add(remainingBalance);
        }

        Double remainingBalanceAverage = calculateAverage(remainingBalanceList);
        remainingBalanceAverage = Utils.formatDouble(remainingBalanceAverage);

        return remainingBalanceAverage;
    }

    public boolean isValidIsoDateTime(String date) {
        try {
            DateTimeFormatter.ISO_ZONED_DATE_TIME.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public Boolean delPipelineProtectionHelper(Loan loan, String date) {
        if (loan == null) {
            return null;
        }
        if (loan.getTpo() != null && loan.getTpo().getUnderwritingDelegated() != null && loan.getWork() != null && loan.getWork().getEvents() != null) {
            if (loan.getTpo().getUnderwritingDelegated()) {
                if (loan.getWork().getEvents().getDocIndexingCompleteStatusChangeAt() != null && isValidIsoDateTime(date)) {
                    return loan.getWork().getEvents().getDocIndexingCompleteStatusChangeAt().isAfter(ZonedDateTime.parse(date));
                }
            }
            else if (!loan.getTpo().getUnderwritingDelegated()) {
                if (loan.getWork().getEvents().getEligibilityDocIndexingCompleteStatusChangeAt() != null && isValidIsoDateTime(date)) {
                    return loan.getWork().getEvents().getEligibilityDocIndexingCompleteStatusChangeAt().isAfter(ZonedDateTime.parse(date));
                }
            }
        }
        return false;
    }

    public Boolean nonDelPipelineProtectionHelper(Loan loan, String date) {
        if (loan == null) {
            return null;
        }
        if (loan.getWork() != null && loan.getWork().getEvents() != null) {
            if (loan.getWork().getEvents().getDocIndexingCompleteStatusChangeAt() != null && isValidIsoDateTime(date)) {
                return loan.getWork().getEvents().getDocIndexingCompleteStatusChangeAt().isAfter(ZonedDateTime.parse(date));
            }
        }
        return false;
    }
}
