package com.htlord.assignment.model;

import com.htlord.assignment.general.PhoneCallPool;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;


// Multi-thread callable class for operating phone call and simulate free employee
public class Employee implements Callable{
    private static Random rand = new Random();
    private UUID id;
    private Priority position;

    public Employee(){}

    public Employee(UUID id, Priority position){
        rand.setSeed(System.currentTimeMillis());
        this.id = id;
        this.position = position;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Priority getPosition() {
        return position;
    }

    public void setPosition(Priority position) {
        this.position = position;
    }

    // If the phone call can not be solved. Escalate to next level.
    // e.g. FR -> TL -> PM
    public void escalate(PhoneCall pc, int pcpIndex){
        PhoneCallPool.getInstance().pushPC(pc, pcpIndex+1);
    }

    // For simulate employee take several time to try to solve the phone call
    public void doSleep(int sec){
        int factor = sec;
        try {
            Thread.sleep(Long.valueOf(factor) * Long.valueOf(1000));
        }catch (Exception e){}
    }

    // Implement callable for achieve multi-thread. While employee is free meanwhile
    // it is not occupied thread. Then get phone call from phone call pool try to
    // solve the phone call.
    public Integer call(){
        // Pull phone call
        PhoneCallPool pcp = PhoneCallPool.getInstance();
        int pcpIndex = 0;
        switch (this.position){
            case TL:
                pcpIndex = 1;
                break;
            case PM:
                pcpIndex = 2;
                break;
        }
        PhoneCall pc = pcp.pullPC(pcpIndex);

        // Simulate 0-5 sec to try to solve phone call
        int factor = rand.nextInt(5);
        doSleep(factor);

        // RTD(roll the dice) to decide is the phone call be solved, or not.
        boolean solved = rand.nextBoolean();

        // Operating solved or escalate
        // CAUTION! PM ALWAYS SOLVE THE PHONE CALL
        if(solved){
            System.out.printf("E(%s)[%s...] cost %s s for solve PC(%s)[%s...]\n",
                    this.position,
                    this.id.toString().substring(0,8),
                    factor,
                    pc.getLevel(),
                    pc.getId().toString().substring(0,8));
            pcp.pushSPC(pc);
        }else{
            if(this.position!= Priority.PM){
                this.escalate(pc, pcpIndex);
                System.out.printf("E(%s)[%s...] cost %s s for escalate PC(%s)[%s...]\n",
                        this.position,
                        this.id.toString().substring(0, 8),
                        factor,
                        pc.getLevel(),
                        pc.getId().toString().substring(0, 8));
            }else {
                System.out.printf("E(%s)[%s...] cost %s s for solve PC(%s)[%s...]\n",
                        this.position,
                        this.id.toString().substring(0, 8),
                        factor,
                        pc.getLevel(),
                        pc.getId().toString().substring(0, 8));
                pcp.pushSPC(pc);
            }
        }

        return Integer.valueOf(1);
    }
}
