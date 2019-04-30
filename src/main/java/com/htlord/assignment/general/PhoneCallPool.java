package com.htlord.assignment.general;

import com.htlord.assignment.model.PhoneCall;
import com.htlord.assignment.model.Priority;

import java.util.ArrayList;
import java.util.List;

// Containing phone call for multi-thread to share it.
public class PhoneCallPool {
    private static PhoneCallPool pcp;
    private static List<PhoneCall>[] pcc;
    private static List<PhoneCall> spcc;
    private static int maxPC = 0;

    public PhoneCallPool(){}

    // Provide singleton
    public static PhoneCallPool getInstance(){
        if(pcp==null){
            pcp = new PhoneCallPool();
            pcp.pcc = new List[3];
            pcp.pcc[0] = new ArrayList<PhoneCall>();
            pcp.pcc[1] = new ArrayList<PhoneCall>();
            pcp.pcc[2] = new ArrayList<PhoneCall>();
            pcp.spcc = new ArrayList<PhoneCall>();
            return pcp;
        }else{
            return pcp;
        }
    }

    // Add phone calls
    public void importPC(List<PhoneCall> outer){
        if(pcp==null) {
            pcp = new PhoneCallPool();
            pcp.pcc[0] = new ArrayList<PhoneCall>();
            pcp.spcc = new ArrayList<PhoneCall>();
            this.pcp.pcc[0].addAll(outer);
            this.pcp.maxPC = outer.size();
        }else{
            this.pcp.pcc[0].addAll(outer);
            this.pcp.maxPC = outer.size();
        }
    }

    // Dump phone call info more dumped detail check class PhoneCall.
    public void dumpPC(){
        for(PhoneCall pc:pcc[0]){
            System.out.println(pc.toString());
        }
    }

    // A free employee will use it to digest phone call
    synchronized public PhoneCall pullPC(int i){
        return !pcp.pcc[i].isEmpty()?pcp.pcc[i].remove(0):null;
    }

    // If the phone cal can not be solved. Then employee should do some operation(
    // escalate) then call this method for waiting solved.
    synchronized public void pushPC(PhoneCall pc, int i){
        this.pcp.pcc[i].add(pc);
    }

    // If the phone call be solved. Add it to this list to record it. The another
    // reason is counting as a signal to stop the process.
    synchronized public void pushSPC(PhoneCall pc){
        this.pcp.spcc.add(pc);
    }

    public static int getMaxPC(){
        return pcp.maxPC;
    }

    public static int getSPCSize(){
        return pcp.spcc.size();
    }
}
