/**
 * ==========================================================================
 * __      __ _ __   ___  *    WellProdSim                                  *
 * \ \ /\ / /| '_ \ / __| *    @version 1.0                                 *
 * \ V  V / | |_) |\__ \ *    @since 2023                                  *
 * \_/\_/  | .__/ |___/ *                                                 *
 * | |          *    @author Jairo Serrano                        *
 * |_|          *    @author Enrique Gonzalez                     *
 * ==========================================================================
 * Social Simulator used to estimate productivity and well-being of peasant *
 * families. It is event oriented, high concurrency, heterogeneous time     *
 * management and emotional reasoning BDI.                                  *
 * ==========================================================================
 */
package org.wpsim.peasantfamily.data;

import BESA.Emotional.EmotionAxis;
import BESA.Emotional.EmotionalEvent;
import BESA.Emotional.Semantics;
import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.System.AdmBESA;
import BESA.Log.ReportBESA;
import org.json.JSONObject;
import org.wpsim.peasantfamily.emotions.EmotionalEvaluator;
import org.wpsim.peasantfamily.guards.fromsimulationcontrol.ToControlMessage;
import org.wpsim.simulationcontrol.guards.SimulationControlGuard;
import org.wpsim.simulationcontrol.util.ControlCurrentDate;
import org.wpsim.simulationcontrol.data.DateHelper;
import org.wpsim.civicauthority.data.LandInfo;
import org.wpsim.peasantfamily.data.utils.*;
import org.wpsim.peasantfamily.emotions.EmotionalComponent;
import org.wpsim.wellprodsim.wpsStart;
import org.wpsim.viewerlens.util.wpsReport;
import rational.data.InfoData;
import rational.mapping.Believes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.wpsim.wellprodsim.wpsStart.params;

/**
 * @author jairo
 */
public class PeasantFamilyBelieves extends EmotionalComponent implements Believes {
    public PeasantFamilyBelieves(PeasantFamilyBelieves original) {
        this.peasantProfile = original.getPeasantProfile();
        this.currentDay = original.getCurrentDay();
        this.timeLeftOnDay = original.getTimeLeftOnDay();
        this.newDay = original.isNewDay();
        this.internalCurrentDate = original.getInternalCurrentDate();
        this.currentSeason = original.getCurrentSeason();
        this.robberyAccount = original.getRobberyAccount();
        this.toPay = original.getToPay();
        this.currentPeasantActivityType = original.getCurrentActivity();
        this.currentMoneyOrigin = original.getCurrentMoneyOrigin();
        this.currentResourceNeededType = original.getCurrentResourceNeededType();
        this.currentPeasantLeisureType = original.getCurrentPeasantLeisureType();
        this.priceList = new HashMap<>(original.getPriceList());
        this.peasantFamilyHelper = original.getPeasantFamilyHelper();
        this.wait = original.isWaiting();
        this.updatePriceList = original.shouldUpdatePriceList(); // Asegúrate de tener este getter
        this.loanDenied = original.isLoanDenied();
        this.daysToWorkForOther = original.getDaysToWorkForOther();
        this.taskLog = new HashMap<>(original.getOrderedTasksByDateJson());        
    }
    //PeasantFamilyBelieves copia = new PeasantFamilyBelieves(original);

    @Override
public PeasantFamilyBelieves clone() {
    return new PeasantFamilyBelieves(this);
}
    private PeasantFamilyProfile peasantProfile;
    private SeasonType currentSeason;
    private MoneyOriginType currentMoneyOrigin;
    private PeasantActivityType currentPeasantActivityType;
    private PeasantLeisureType currentPeasantLeisureType;
    private ResourceNeededType currentResourceNeededType;
    private List<LandInfo> assignedLands = new CopyOnWriteArrayList<>();
    private List<EmotionAxis> emotionsList;
    private int currentDay;
    private int robberyAccount;
    private double timeLeftOnDay;
    private boolean workerWithoutLand;
    private boolean newDay;
    private boolean haveLoan;
    private double toPay;
    private boolean loanDenied;
    private String internalCurrentDate;
    private String ptwDate;
    private Map<String, FarmingResource> priceList = new HashMap<>();
    private Map<String, Set<String>> taskLog = new ConcurrentHashMap<>();
    private int daysToWorkForOther;
    private String peasantFamilyHelper;
    private String Contractor;
    private boolean haveEmotions;
    private boolean askedForContractor;
    private boolean askedForCollaboration;
    private boolean wait;
    private boolean updatePriceList;
    private double personality;
    private double trainingLevel;
    private boolean trainingAvailable;
    /**
     * @param alias          Peasant Family Alias
     * @param peasantProfile profile of the peasant family
     */
    public PeasantFamilyBelieves(String alias, PeasantFamilyProfile peasantProfile) {
        this.setPeasantProfile(peasantProfile);
        this.internalCurrentDate = ControlCurrentDate.getInstance().getCurrentDate();
        this.peasantProfile.setPeasantFamilyAlias(alias);
        this.taskLog.clear();
        this.currentDay = 1;
        this.timeLeftOnDay = 1440;
        this.haveLoan = false;
        this.newDay = true;
        this.wait = false;
        this.priceList.clear();
        this.loanDenied = false;
        this.ptwDate = "";
        this.peasantFamilyHelper = "";
        this.Contractor = "";
        this.currentMoneyOrigin = MoneyOriginType.NONE;
        this.currentPeasantActivityType = PeasantActivityType.NONE;
        this.currentPeasantLeisureType = PeasantLeisureType.NONE;
        this.setHaveEmotions(params.emotions == 1);
        if (params.personality != -1.0) {
            this.setPersonality(params.personality);
        } else {
            this.setPersonality(0.0);
        }
        if (params.training == 1) {
            this.trainingLevel = wpsStart.config.getDoubleProperty("trainingLevel");
        } else {
            this.trainingLevel = 0.4;
        }
        changePersonalityBase(getPersonality());
    }
    public boolean isTrainingAvailable() {
        return trainingAvailable;
    }
    public void setTrainingAvailable(boolean trainingAvailable) {
        this.trainingAvailable = trainingAvailable;
    }
    public double getTrainingLevel() {
        return trainingLevel;
    }
    public boolean shouldUpdatePriceList() {
        return updatePriceList;
    }
    public void setEmotionsList(List<EmotionAxis> emotionsList) {
        this.emotionsList = emotionsList;
    }
        
    public void increaseTrainingLevel() {
        trainingLevel += 0.1;
        setTrainingAvailable(false);
    }
    public boolean isHaveEmotions() {
        return haveEmotions;
    }
    public void setHaveEmotions(boolean haveEmotions) {
        this.haveEmotions = haveEmotions;
    }
    public boolean isAskedForContractor() {
        return askedForContractor;
    }
    public void setAskedForContractor(boolean askedForContractor) {
        this.askedForContractor = askedForContractor;
    }
    public String getContractor() {
        return Contractor;
    }
    public void setContractor(String peasantFamilyToHelp) {
        this.Contractor = peasantFamilyToHelp;
    }
    public boolean isWorkerWithoutLand() {
        return workerWithoutLand;
    }
    public void setWorkerWithoutLand() {
        this.workerWithoutLand = true;
        peasantProfile.setPurpose("worker");
    }
    public List<LandInfo> getAssignedLands() {
        return assignedLands;
    }
    /**
     * Establece los terrenos asignados a partir de un mapa proporcionado.
     * Limpia la lista actual y la llena con objetos LandInfo basados en las entradas del mapa.
     *
     * @param lands Un mapa con nombres de terrenos como claves y tipos de terreno como valores.
     */
    public void setAssignedLands(Map<String, String> lands) {
        if (lands == null) {
            return;
        }
        List<LandInfo> newAssignedLands = new ArrayList<>();
        for (Map.Entry<String, String> entry : lands.entrySet()) {
            newAssignedLands.add(
                    new LandInfo(
                            entry.getKey(),
                            entry.getValue(),
                            getPeasantProfile().getPeasantFamilyLandAlias(),
                            ControlCurrentDate.getInstance().getCurrentYear()
                    )
            );
        }
        this.assignedLands.clear();
        this.assignedLands.addAll(newAssignedLands);
    }
    /**
     * Actualiza la información del terreno en la lista.
     * Si el terreno con el mismo nombre ya existe en la lista, se actualiza con la nueva información.
     *
     * @param newLandInfo La nueva información del terreno.
     * @return true si el terreno fue actualizado exitosamente, false en caso contrario.
     */
    public void updateAssignedLands(LandInfo newLandInfo) {
        assignedLands.remove(newLandInfo);
        assignedLands.add(newLandInfo);
    }
    /**
     * Verifica si hay algún terreno disponible en la lista que no sea de tipo "water" y no esté en uso.
     * Imprime información sobre cada terreno durante la verificación.
     *
     * @return true si hay un terreno disponible, false en caso contrario.
     */
    public boolean isLandAvailable() {
        for (LandInfo landInfo : assignedLands) {
            if (!landInfo.getKind().equals("water") && !landInfo.isUsed()) {
                return true;
            }
        }
        return false;
    }
    /**
     * Busca y devuelve un terreno disponible en la lista que no sea de tipo "water" y no esté en uso.
     * El terreno devuelto se marca como usado.
     *
     * @return El terreno disponible o null si no hay ninguno.
     */
    public boolean getLandAvailable() {
        for (LandInfo landInfo : assignedLands) {
            if (!landInfo.getKind().equals("water")
                    && !landInfo.getKind().equals("forest")
                    && landInfo.getCurrentSeason().equals(SeasonType.NONE)) {
                return true;
            }
        }
        return false;
    }
    public synchronized LandInfo getLandInfo(String landName) {
        for (LandInfo landInfo : assignedLands) {
            if (landInfo.getLandName().equals(landName)) {
                return landInfo;
            }
        }
        return null;
    }
    /**
     * Establece la temporada actual para un terreno específico basado en su nombre.
     * Si se encuentra el terreno en la lista, se actualiza su temporada.
     *
     * @param landName      El nombre del terreno cuya temporada se desea actualizar.
     * @param currentSeason La nueva temporada que se desea establecer para el terreno.
     */
    public void setCurrentSeason(String landName, SeasonType currentSeason) {
        LandInfo landInfo = getLandInfo(landName);
        if (landInfo != null) {
            landInfo.setCurrentSeason(currentSeason);
        }
        this.currentSeason = currentSeason;
        this.currentSeason = currentSeason;
    }
    public void setCurrentLandKind(String landName, String currentKind) {
        LandInfo landInfo = getLandInfo(landName);
        if (landInfo != null) {
            landInfo.setKind(currentKind);
        }
    }
    /**
     * Sets the current crop care type for the specified land.
     *
     * @param landName            the name of the land
     * @param currentCropCareType the new crop care type
     */
    public void setCurrentCropCareType(String landName, CropCareType currentCropCareType) {
        LandInfo landInfo = getLandInfo(landName);
        landInfo.setCurrentCropCareType(currentCropCareType);
    }
    /**
     * Adds a task to the log for the specified date.
     *
     * @param date the date of the task
     */
    public void addTaskToLog(String date) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String fullClassName = stackTraceElements[2].getClassName();
        String[] parts = fullClassName.split("\\.");
        String taskName = parts[parts.length - 1];
        taskLog.computeIfAbsent(date, k -> ConcurrentHashMap.newKeySet()).add(taskName);
    }
    public void addTaskToLog(String date, String landName) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String fullClassName = stackTraceElements[2].getClassName();
        String[] parts = fullClassName.split("\\.");
        String taskName = parts[parts.length - 1];
        taskLog.computeIfAbsent(date, k -> ConcurrentHashMap.newKeySet()).add(taskName);
        taskLog.computeIfAbsent(date, k -> ConcurrentHashMap.newKeySet()).add(taskName + landName);
    }
    /**
     * Checks if the specified task was executed on the specified date.
     *
     * @param date     Date to check
     * @param taskName Name of the task
     * @return true if the task was executed on the specified date, false otherwise
     */
    public boolean isTaskExecutedOnDate(String date, String taskName) {
        Set<String> tasks = taskLog.getOrDefault(date, new HashSet<>());
        //System.out.println(tasks + " " + taskName + " on " + date + " r " + tasks.contains(taskName));
        return tasks.contains(taskName);
    }
    public boolean isTaskExecutedOnDateWithLand(String date, String taskName, String landName) {
        Set<String> tasks = taskLog.getOrDefault(date, new HashSet<>());
        //ReportBESA.info(tasks + " " + (taskName+landName) + " on " + date + " r " + tasks.contains(taskName+landName));
        return tasks.contains(taskName + landName);
    }
    public boolean isHaveLoan() {
        return haveLoan;
    }
    public void setHaveLoan(boolean haveLoan) {
        this.haveLoan = haveLoan;
    }
    public int getRobberyAccount() {
        return robberyAccount;
    }
    public void increaseRobberyAccount() {
        this.robberyAccount++;
    }
    public String getPtwDate() {
        return ptwDate;
    }
    public void setPtwDate(String ptwDate) {
        this.ptwDate = ptwDate;
    }
    /**
     * @return
     */
    public int getCurrentDay() {
        return currentDay;
    }
    /**
     * @param currentDay
     */
    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }
    /**
     * @return
     */
    public double getTimeLeftOnDay() {
        return timeLeftOnDay;
    }
    /**
     * @param timeLeftOnDay
     */
    public void setTimeLeftOnDay(double timeLeftOnDay) {
        this.timeLeftOnDay = timeLeftOnDay;
    }
    /**
     * @return
     */
    public String getInternalCurrentDate() {
        return internalCurrentDate;
    }
    /**
     * @param internalCurrentDate
     */
    public void setInternalCurrentDate(String internalCurrentDate) {
        this.internalCurrentDate = internalCurrentDate;
    }
    /**
     * Time unit defined by hours spent on activities.
     *
     * @param time
     */
    public void useTime(double time) {
        EmotionalEvaluator evaluator = new EmotionalEvaluator("EmotionalRulesFull");
        double factor = 1;
        if (isHaveEmotions()) {
            factor = evaluator.emotionalFactor(getEmotionsListCopy(), Semantics.Emotions.Happiness);
            time = (int) Math.ceil(time - ((factor - 1) * time));
            //ReportBESA.info(this.getAlias() + " tiene " + this.timeLeftOnDay + " descuenta con emociones " + time);
            decreaseTime(time);
        } else {
            //System.out.println(this.getAlias() + " tiene " + this.timeLeftOnDay + " descuenta " + time);
            decreaseTime(time);
        }
    }
    /**
     * Make variable reset Every Day and increment date
     */
    public void makeNewDay() {
        this.currentDay++;
        this.timeLeftOnDay = 1440;
        this.newDay = true;
        this.internalCurrentDate = ControlCurrentDate.getInstance().getDatePlusOneDay(internalCurrentDate);
        notifyInternalCurrentDay();
        if (ControlCurrentDate.getInstance().isFirstDayOfWeek(internalCurrentDate)) {
            // Report the agent's beliefs to the wpsViewer
            wpsReport.ws(this.toJson(), this.getAlias());
            // Report the agent's beliefs to the wpsViewer
            wpsReport.mental(Instant.now() + "," + this.toCSV(), this.getAlias());
        }
    }
    private void notifyInternalCurrentDay() {
        // Update the internal current date
        try {
            AdmBESA.getInstance().getHandlerByAlias(
                    wpsStart.config.getControlAgentName()
            ).sendEvent(
                    new EventBESA(
                            SimulationControlGuard.class.getName(),
                            new ToControlMessage(
                                    getAlias(),
                                    getInternalCurrentDate(),
                                    getCurrentDay()
                            )
                    )
            );
        } catch (ExceptionBESA ex) {
            ReportBESA.error(ex);
        }
    }

    /**
     * Time unit defined by hours spent on activities.
     *
     * @param time
     */
    public synchronized void decreaseTime(double time) {

        timeLeftOnDay = (int) (timeLeftOnDay - time);
        if (timeLeftOnDay <= 30) {
            this.makeNewDay();
        } else if (timeLeftOnDay < 120) {
            timeLeftOnDay = 120;
        }
        //ReportBESA.info("decreaseTime: " + time + ", Queda " + timeLeftOnDay + " para " + getPeasantProfile().getPeasantFamilyAlias());
    }

    /**
     * @param time
     * @return
     */
    public boolean haveTimeAvailable(TimeConsumedBy time) {
        return this.timeLeftOnDay - time.getTime() >= 0;
    }

    /**
     * Check if is a new Day
     *
     * @return true if is a new day
     */
    public boolean isNewDay() {
        return this.newDay;
    }

    /**
     * Set a new Day false
     *
     * @param newDay
     */
    public void setNewDay(boolean newDay) {
        this.newDay = newDay;
    }


    /**
     * @return
     */
    public ResourceNeededType getCurrentResourceNeededType() {
        return currentResourceNeededType;
    }

    /**
     *
     */
    public void setCurrentResourceNeededType(ResourceNeededType currentResourceNeededType) {
        this.currentResourceNeededType = currentResourceNeededType;
    }

    /**
     * @return
     */
    public PeasantLeisureType getCurrentPeasantLeisureType() {
        return currentPeasantLeisureType;
    }

    /**
     * @param currentPeasantLeisureType
     */
    public void setCurrentPeasantLeisureType(PeasantLeisureType currentPeasantLeisureType) {
        this.currentPeasantLeisureType = currentPeasantLeisureType;
    }

    /**
     * @return
     */
    public SeasonType getCurrentSeason() {
        return currentSeason;
    }

    /**
     * @return
     */
    public MoneyOriginType getCurrentMoneyOrigin() {
        return currentMoneyOrigin;
    }

    /**
     * @param currentMoneyOrigin the currentMoneyOrigin to set
     */
    public void setCurrentMoneyOrigin(MoneyOriginType currentMoneyOrigin) {
        this.currentMoneyOrigin = currentMoneyOrigin;
    }

    public PeasantActivityType getCurrentActivity() {
        return this.currentPeasantActivityType;
    }

    public void setCurrentActivity(PeasantActivityType peasantActivityType) {
        this.currentPeasantActivityType = peasantActivityType;
    }

    /**
     * @return the currentPeasantActivityType
     */
    public PeasantFamilyProfile getPeasantProfile() {
        return peasantProfile;
    }

    /**
     * @param peasantProfile the peasantProfile to set
     */
    private void setPeasantProfile(PeasantFamilyProfile peasantProfile) {
        this.peasantProfile = peasantProfile;
    }

    /**
     * @param infoData
     * @return
     */
    @Override
    public boolean update(InfoData infoData) {
        return true;
    }

    /**
     * @return the priceList
     */
    public Map<String, FarmingResource> getPriceList() {
        return priceList;
    }

    /**
     * @param priceList the priceList to set
     */
    public void setPriceList(Map<String, FarmingResource> priceList) {
        this.priceList = priceList;
        for (Map.Entry<String, FarmingResource> price : priceList.entrySet()) {
            if (price.getValue().getBehavior() > 0) {
                processEmotionalEvent(new EmotionalEvent("FAMILY", "PLANTING", "MONEY"));
            } else if (price.getValue().getBehavior() < 0) {
                processEmotionalEvent(new EmotionalEvent("FAMILY", "PLANTINGFAILED", "MONEY"));
            }
        }
    }


    /**
     * @return
     */
    public synchronized String toJson() {
        JSONObject dataObject = new JSONObject();
        dataObject.put("money", peasantProfile.getMoney());
        dataObject.put("initialMoney", peasantProfile.getInitialMoney());
        dataObject.put("health", peasantProfile.getHealth());
        dataObject.put("initialHealth", peasantProfile.getInitialHealth());
        dataObject.put("timeLeftOnDay", timeLeftOnDay);
        dataObject.put("newDay", newDay);
        dataObject.put("currentSeason", currentSeason);
        dataObject.put("robberyAccount", robberyAccount);
        dataObject.put("purpose", peasantProfile.getPurpose());
        dataObject.put("peasantFamilyAffinity", peasantProfile.getPeasantFamilyAffinity());
        dataObject.put("peasantLeisureAffinity", peasantProfile.getPeasantLeisureAffinity());
        dataObject.put("peasantFriendsAffinity", peasantProfile.getPeasantFriendsAffinity());
        dataObject.put("currentPeasantLeisureType", currentPeasantLeisureType);
        dataObject.put("currentResourceNeededType", currentResourceNeededType);
        dataObject.put("currentDay", currentDay);
        dataObject.put("internalCurrentDate", internalCurrentDate);
        dataObject.put("toPay", toPay);
        dataObject.put("peasantKind", peasantProfile.getPeasantKind());
        dataObject.put("peasantFamilyMinimalVital", wpsStart.config.getIntProperty("pfagent.minimalVital"));
        dataObject.put("peasantFamilyLandAlias", peasantProfile.getPeasantFamilyLandAlias());
        dataObject.put("currentActivity", currentPeasantActivityType);
        dataObject.put("farm", peasantProfile.getFarmName());
        dataObject.put("cropSize", peasantProfile.getCropSize());
        dataObject.put("loanAmountToPay", peasantProfile.getLoanAmountToPay());
        dataObject.put("tools", peasantProfile.getTools());
        dataObject.put("seeds", peasantProfile.getSeeds());
        dataObject.put("waterAvailable", peasantProfile.getWaterAvailable());
        dataObject.put("pesticidesAvailable", peasantProfile.getPesticidesAvailable());
        dataObject.put("totalHarvestedWeight", peasantProfile.getTotalHarvestedWeight());
        dataObject.put("contractor", getContractor());
        dataObject.put("daysToWorkForOther", getDaysToWorkForOther());
        dataObject.put("peasantFamilyHelper", getPeasantFamilyHelper());
        dataObject.put("waitStatus", isWaiting());
        dataObject.put("haveEmotions", isHaveEmotions());

        if (!getAssignedLands().isEmpty()) {
            dataObject.put("assignedLands", getAssignedLands());
        } else {
            dataObject.put("assignedLands", Collections.emptyList());
        }

        try {
            List<EmotionAxis> emotions = this.getEmotionsListCopy();
            emotions.forEach(emotion -> {
                dataObject.put(emotion.toString(), emotion.getCurrentValue());
            });
        } catch (Exception e) {
            dataObject.put("emotions", 0);
        }

        JSONObject finalDataObject = new JSONObject();
        finalDataObject.put("name", peasantProfile.getPeasantFamilyAlias());
        finalDataObject.put("state", dataObject.toString());

        finalDataObject.put("taskLog", getOrderedTasksByDateJson());

        return finalDataObject.toString();
    }

    public Map<String, Set<String>> getOrderedTasksByDateJson() {
        Map<LocalDate, Set<String>> sortedTasks = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Convertir y ordenar
        taskLog.forEach((dateString, tasks) -> {
            LocalDate date = LocalDate.parse(dateString, formatter);
            sortedTasks.put(date, tasks);
        });

        // Convertir de vuelta a String y crear JSON
        Map<String, Set<String>> finalData = new LinkedHashMap<>();
        sortedTasks.forEach((date, tasks) -> {
            finalData.put(date.format(formatter), tasks);
        });

        return finalData;
    }

    public Map<String, Set<String>> getTasksBySpecificDate(String specificDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateToFind = LocalDate.parse(specificDate, formatter);

        Map<String, Set<String>> tasksForSpecificDate = new LinkedHashMap<>();

        taskLog.forEach((dateString, tasks) -> {
            LocalDate date = LocalDate.parse(dateString, formatter);
            if (date.equals(dateToFind)) {
                tasksForSpecificDate.put(date.format(formatter), tasks);
            }
        });

        return tasksForSpecificDate;
    }

    public double getToPay() {
        return toPay;
    }

    public void setToPay(double toPay) {
        this.toPay = toPay;
    }

    public void discountToPay(double toPay) {
        this.toPay -= toPay;
    }

    public boolean isLoanDenied() {
        return loanDenied;
    }

    public void setLoanDenied(boolean loanDenied) {
        this.loanDenied = loanDenied;
    }

    public void decreaseHealth() {
        this.peasantProfile.decreaseHealth();
        /*if (this.getPeasantProfile().getHealth() <= 0) {
            try {
                wpsReport.debug("👻👻 murió agente " + this.peasantProfile.getPeasantFamilyAlias() + " 👻👻", this.peasantProfile.getPeasantFamilyAlias());
                AdmBESA adm = AdmBESA.getInstance();
                AgHandlerBESA agHandler = adm.getHandlerByAlias(this.peasantProfile.getPeasantFamilyAlias());
                adm.killAgent(agHandler.getAgId(), wpsStart.PASSWD);
                this.processEmotionalEvent(
                        new EmotionalEvent("FAMILY", "STARVING", "FOOD")
                );
            } catch (ExceptionBESA ex) {
                wpsReport.error(ex, this.peasantProfile.getPeasantFamilyAlias());
            }
        }*/
    }

    public boolean isPlantingSeason() {
        return DateHelper.getMonthFromStringDate(getInternalCurrentDate()) == 0 ||
                DateHelper.getMonthFromStringDate(getInternalCurrentDate()) == 3 ||
                DateHelper.getMonthFromStringDate(getInternalCurrentDate()) == 6 ||
                DateHelper.getMonthFromStringDate(getInternalCurrentDate()) == 8;
    }

    public int getDaysToWorkForOther() {
        return daysToWorkForOther;
    }

    public void setDaysToWorkForOther(int daysToWorkForOther) {
        this.daysToWorkForOther = daysToWorkForOther;
    }

    public void decreaseDaysToWorkForOther() {
        this.daysToWorkForOther = this.daysToWorkForOther - 1;
    }

    public String getPeasantFamilyHelper() {
        return peasantFamilyHelper;
    }

    public void setPeasantFamilyHelper(String peasantFamilyHelper) {
        this.peasantFamilyHelper = peasantFamilyHelper;
    }

    public String getAlias() {
        return peasantProfile.getPeasantFamilyAlias();
    }

    public synchronized String toCSV() {
        StringBuilder csvData = new StringBuilder();

        try {
            List<EmotionAxis> emotions = this.getEmotionsListCopy();
            emotions.forEach(emotion -> {
                csvData.append(getOrDefault(emotion.getCurrentValue())).append(',');
            });
        } catch (Exception e) {
            //csvData.append("NONE").append(',');
        }

        // Agregar los datos
        csvData.append(getOrDefault(peasantProfile.getMoney())).append(',')
                .append(getOrDefault(peasantProfile.getHealth())).append(',')
                .append(getOrDefault(currentSeason)).append(',')
                .append(getOrDefault(robberyAccount)).append(',')
                .append(getOrDefault(peasantProfile.getPurpose())).append(',')
                .append(getOrDefault(peasantProfile.getPeasantFamilyAffinity())).append(',')
                .append(getOrDefault(peasantProfile.getPeasantLeisureAffinity())).append(',')
                .append(getOrDefault(peasantProfile.getPeasantFriendsAffinity())).append(',')
                .append(getOrDefault(currentPeasantLeisureType)).append(',')
                .append(getOrDefault(currentResourceNeededType)).append(',')
                .append(getOrDefault(currentDay)).append(',')
                .append(getOrDefault(internalCurrentDate)).append(',')
                .append(getOrDefault(toPay)).append(',')
                .append(getOrDefault(peasantProfile.getPeasantKind())).append(',')
                .append(getOrDefault(wpsStart.config.getIntProperty("pfagent.minimalVital"))).append(',')
                .append(getOrDefault(peasantProfile.getPeasantFamilyLandAlias())).append(',')
                .append(getOrDefault(currentPeasantActivityType)).append(',')
                .append(getOrDefault(peasantProfile.getFarmName())).append(',')
                .append(getOrDefault(peasantProfile.getLoanAmountToPay())).append(',')
                .append(getOrDefault(peasantProfile.getTools())).append(',')
                .append(getOrDefault(peasantProfile.getSeeds())).append(',')
                .append(getOrDefault(peasantProfile.getWaterAvailable())).append(',')
                .append(getOrDefault(peasantProfile.getPesticidesAvailable())).append(',')
                .append(getOrDefault(peasantProfile.getHarvestedWeight())).append(',')
                .append(getOrDefault(peasantProfile.getTotalHarvestedWeight())).append(',')
                .append(getOrDefault(getContractor())).append(',')
                .append(getOrDefault(getDaysToWorkForOther())).append(',')
                .append(getOrDefault(getAlias())).append(',')
                .append(getOrDefault(isHaveEmotions())).append(',')
                .append(getOrDefault(getPeasantFamilyHelper())).append(',')
                .append(getOrDefault(isHaveEmotions()));

        //csvData.append('\n');
        return csvData.toString();
    }

    private String getOrDefault(Object value) {
        if (value == null) {
            return "NONE";
        } else if (value == "") {
            return "NONE";
        } else {
            return value.toString();
        }
    }

    public void setWait(boolean waitStatus) {
        this.wait = waitStatus;
    }

    public boolean isWaiting() {
        return this.wait;
    }

    public String getCurrentCropName() {
        //System.out.println("rice " + priceList.get("rice").getCost() + " - roots " + priceList.get("roots").getCost());
        if (priceList.get("rice").getCost() > priceList.get("roots").getCost()) {
            return "rice";
        } else {
            return "roots";
        }
    }

    public void setUpdatePriceList(boolean updatePriceList) {
        this.updatePriceList = updatePriceList;
    }

    public boolean isUpdatePriceList() {
        return updatePriceList;
    }

    public boolean isAskedForCollaboration() {
        return askedForCollaboration;
    }

    public void setAskedForCollaboration(boolean collaboration) {
        this.askedForCollaboration = collaboration;
    }

    public void changePersonalityBase(float value) {
        List<EmotionAxis> emotions = this.emotionalState.getEmotions();
        for (EmotionAxis emotion : emotions) {
            emotion.increaseBaseValue(value);
        }
    }

    public void changeHappinessBase(float value) {
        List<EmotionAxis> emotions = this.emotionalState.getEmotions();
        for (EmotionAxis emotion : emotions) {
            if (emotion.toString().equals("Happiness/Sadness")) {
                emotion.increaseBaseValue(value);
            }
        }
    }

    public float getPersonality() {
        return (float) personality;
    }

    public void setPersonality(Double personality) {
        this.personality = personality;
    }
}


