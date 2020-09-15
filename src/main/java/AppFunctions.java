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
}
