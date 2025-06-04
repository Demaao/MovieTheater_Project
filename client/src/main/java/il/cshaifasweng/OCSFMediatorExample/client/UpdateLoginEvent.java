package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Employee;
import il.cshaifasweng.OCSFMediatorExample.entities.HeadManager;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;

import java.io.Serializable;
import java.util.List;

public class UpdateLoginEvent implements Serializable{
    private Employee employee;
    public UpdateLoginEvent(Employee employee){
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }
}


