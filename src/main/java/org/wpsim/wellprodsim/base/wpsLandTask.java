package org.wpsim.wellprodsim.base;

import BESA.Emotional.Semantics;
import org.wpsim.civicauthority.data.LandInfo;
import org.wpsim.peasantfamily.data.PeasantFamilyBelieves;
import org.wpsim.peasantfamily.emotions.EmotionalEvaluator;

public class wpsLandTask extends wpsTask {
    private boolean isStarted = false;

    protected void updateConfig(PeasantFamilyBelieves believes, int totalRequiredTime) {
        if (!isStarted) {
            for (LandInfo currentLandInfo : believes.getAssignedLands()) {
                currentLandInfo.setTotalRequiredTime(totalRequiredTime);
                currentLandInfo.setElapsedWorkTime(0);
            }
            this.isStarted = true;
        }
    }

    protected void increaseWorkDone(PeasantFamilyBelieves believes, String landName, int workDone) {
        EmotionalEvaluator evaluator = new EmotionalEvaluator("EmotionalRulesFull");
        double factor = 1;
        int newWorkDone = 0;
        if (believes.isHaveEmotions()) {
            factor = evaluator.emotionalFactor(believes.getEmotionsListCopy(), Semantics.Emotions.Happiness);
        }
        newWorkDone = (int) Math.ceil(workDone * factor);
        for (LandInfo currentLandInfo : believes.getAssignedLands()) {
            if (currentLandInfo.getLandName().equals(landName)) {
                currentLandInfo.increaseElapsedWorkTime(newWorkDone);
                //ReportBESA.info(currentLandInfo.getLandName() + ", sumando " + newWorkDone + " al trabajo realizado, con un factor de " + factor + ", originalmente era " + workDone);
            }
        }
    }

    protected boolean isWorkDone(PeasantFamilyBelieves believes, String landName) {
        for (LandInfo currentLandInfo : believes.getAssignedLands()) {
            if (currentLandInfo.getLandName().equals(landName)) {
                //ReportBESA.info("currentLandInfo.getLandName() " + currentLandInfo.getLandName());
                return currentLandInfo.elapsedWorkTimeIsDone();
            }
        }
        return false;
    }

}
