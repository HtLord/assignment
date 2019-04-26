package com.htlord.assignment.general;

import com.htlord.assignment.model.Employee;
import com.htlord.assignment.model.PhoneCall;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Serv {

    static final int MAX_FR = 3;
    static final int MAX_TL = 1;
    static final int MAX_PM = 1;

    public static void main(String[] args){
        ModelGenerator mg = new ModelGenerator();
        List<Employee> ec = mg.generateEmployees(new int[]{MAX_FR, MAX_TL, MAX_PM});
        List<PhoneCall> pcc1 = mg.generatePhoneCalls(20);

        PhoneCallPool pcp = PhoneCallPool.getInstance();
        ExecutorService serv = Executors.newFixedThreadPool(MAX_FR+MAX_TL+MAX_PM);

        pcp.importPC(pcc1);

        List<Callable<Integer>> c = new ArrayList<Callable<Integer>>();
        for(Employee e:ec){
            c.add(e);
        }

        pcp.dumpPC();
        System.out.println("Result...");
        while(true) {
            try {
                serv.invokeAll(c);
                if(pcp.getSPCSize()==pcp.getMaxPC()){
                    serv.shutdown();
                    break;
                }
            }catch (Exception e){

            }
        }

    }
}
