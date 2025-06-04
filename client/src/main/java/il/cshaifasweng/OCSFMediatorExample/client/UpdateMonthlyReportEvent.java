package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.DailyReportData;
import java.time.LocalDate;
import java.util.Map;

public class UpdateMonthlyReportEvent {
    private Map<LocalDate, DailyReportData> reportData;

    public UpdateMonthlyReportEvent(Map<LocalDate, DailyReportData> reportData) {
        this.reportData = reportData;
    }

    public Map<LocalDate, DailyReportData> getReportData() {
        return reportData;
    }
}
