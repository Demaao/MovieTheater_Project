package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;

import java.io.Serializable;
import java.util.List;

public class UpdateComplaintsEvent implements Serializable {
    private final List<Complaint> complaints;
    public UpdateComplaintsEvent(List<Complaint> complaints) { this.complaints = complaints;
    }
    public List<Complaint> getComplaints() { return complaints; }
}

