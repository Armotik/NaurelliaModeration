package fr.armotik.naurelliamoderation.utilsclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Report {

    private final int id;
    private final UUID reporter_uuid;
    private final UUID target_uuid;
    private final String reason;
    private final String date;
    private boolean isTreated;
    private static List<Report> reports;
    private static int reportID = 1;

    public Report( UUID reporter_uuid, UUID target_uuid, String reason, String date, boolean isTreated) {
        this.reporter_uuid = reporter_uuid;
        this.target_uuid = target_uuid;
        this.reason = reason;
        this.date = date;
        this.isTreated = isTreated;

        if (reports == null) reports = new ArrayList<>();

        reports.add(this);

        this.id = reportID;
        reportID++;
    }

    public static List<Report> getReports() {
        return reports;
    }

    public static void setReports(List<Report> reports) {
        Report.reports = reports;
    }

    public UUID getReporter_uuid() {
        return reporter_uuid;
    }

    public UUID getTarget_uuid() {
        return target_uuid;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {
        return date;
    }

    public boolean isTreated() {
        return isTreated;
    }

    public void setTreated(boolean treated) {
        isTreated = treated;
    }

    public int getId() {
        return id;
    }
}
