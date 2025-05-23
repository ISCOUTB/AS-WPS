package org.wpsim.peasantfamily.tasks.L4SkillsResources;

import BESA.Emotional.EmotionalEvent;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.data.utils.TimeConsumedBy;
import org.wpsim.wellprodsim.base.wpsTask;
import rational.mapping.Believes;

import java.security.SecureRandom;  // Cambiado a SecureRandom

/**
 *
 * @author jairo
 */
public class AlternativeWorkTask extends wpsTask {

    private static final SecureRandom RANDOM = new SecureRandom();  // Usar SecureRandom

    /**
     *
     * @param parameters
     */
    @Override
    public void executeTask(Believes parameters) {
        this.setExecuted(false);
        PeasantFamilyBelieves believes = (PeasantFamilyBelieves) parameters;
        believes.useTime(TimeConsumedBy.AlternativeWorkTask.getTime());
        believes.addTaskToLog(believes.getInternalCurrentDate());
        believes.processEmotionalEvent(new EmotionalEvent("FAMILY", "WORK", "MONEY"));
        // https://www.eluniversal.com.co/cartagena/poner-a-trabajar-una-mototaxi-asi-se-mueve-este-negocio-en-cartagena-CX7541242
        // http://www.scielo.org.co/scielo.php?script=sci_arttext&pid=S0120-55522013000300012
        believes.getPeasantProfile().increaseMoney(RANDOM.nextInt(25001) + 10000.0);
    }
}
