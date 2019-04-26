package com.htlord.assignment.general;

import com.htlord.assignment.model.Employee;
import com.htlord.assignment.model.PhoneCall;
import com.htlord.assignment.model.Priority;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModelGenerator {
    public List<Employee> generateEmployees(int[] formula){
        List<Employee> ec = new ArrayList<Employee>();
        for(int i = 0; i<formula[0]; i++){
            ec.add(new Employee(UUID.randomUUID(), Priority.FR));
        }
        for(int i = 0; i<formula[1]; i++){
            ec.add(new Employee(UUID.randomUUID(), Priority.TL));
        }
        for(int i = 0; i<formula[2]; i++){
            ec.add(new Employee(UUID.randomUUID(), Priority.PM));
        }
        return ec;
    }

    public List<PhoneCall> generatePhoneCalls(int num){
        List<PhoneCall> pcc = new ArrayList<PhoneCall>();
        for(int i=0; i<num; i++){
            pcc.add(new PhoneCall(UUID.randomUUID(), Priority.FR));
        }
        return pcc;
    }
}
