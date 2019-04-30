package com.htlord.assignment.general;

import com.htlord.assignment.model.Employee;
import com.htlord.assignment.model.PhoneCall;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Serv {

    static int MAX_FR = 3;
    static int MAX_TL = 1;
    static int MAX_PM = 1;
    static int PHONE_NUMB = 10;

    // Start to simulate call center.
    public static void main(String[] args) throws Exception{
        try {
            if (args.length == 3) {
                MAX_FR = Integer.valueOf(args[0]);
                MAX_TL = Integer.valueOf(args[1]);
                MAX_PM = Integer.valueOf(args[2]);
                PHONE_NUMB = Integer.valueOf(args[3]);
                return;
            }
        }catch (Exception e){
            throw new Exception("Error input args");
        }

        ModelGenerator mg = new ModelGenerator();

        // Generate phone call
        // Add phone call into pool for sharing data
        PhoneCallPool pcp = PhoneCallPool.getInstance();
        List<PhoneCall> pcc1 = mg.generatePhoneCalls(PHONE_NUMB);
        pcp.importPC(pcc1);

        // Generate employees
        // Declare threads
        List<Employee> ec = mg.generateEmployees(new int[]{MAX_FR, MAX_TL, MAX_PM});
        ExecutorService serv = Executors.newFixedThreadPool(MAX_FR+MAX_TL+MAX_PM);
        List<Callable<Integer>> c = new ArrayList<Callable<Integer>>();
        for(Employee e:ec){
            c.add(e);
        }

        //Call and print results
        pcp.dumpPC();
        System.out.println("Result...");
        while(true) {
            try {
                serv.invokeAll(c);
                if(pcp.getSPCSize()==pcp.getMaxPC()){
                    serv.shutdown();
                    System.out.println("Done...");
                    break;
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }
}
