package il.cshaifasweng.OCSFMediatorExample.client;


import java.io.Serializable;
import java.util.Map;

public class UpdateComplaintReportEvent implements Serializable {
    private final Map<Integer, Integer> complaintsByDay;
    public UpdateComplaintReportEvent(Map<Integer, Integer> complaintsByDay) { this.complaintsByDay = complaintsByDay;
    }
    public Map<Integer, Integer> getComplaintReportEvent() { return complaintsByDay; }



}
