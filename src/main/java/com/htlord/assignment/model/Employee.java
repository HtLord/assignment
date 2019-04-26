package com.htlord.assignment.model;

import com.htlord.assignment.general.PhoneCallPool;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

public class Employee implements Callable{
    private UUID id;
    private Priority position;

    public Employee(){}
    public Employee(UUID id, Priority position){
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

    public void escalate(PhoneCall pc){
        switch (pc.getLevel()){
            case FR:
                pc.setLevel(Priority.TL);
                break;
            case TL:
                pc.setLevel(Priority.PM);
                break;
        }
    }

    public void doSleep(int sec){
        int factor = sec;
        try {
            Thread.sleep(Long.valueOf(factor) * Long.valueOf(1000));
        }catch (Exception e){}
    }

    public Integer call(){
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        int factor = rand.nextInt(5);
        doSleep(factor);

        boolean solved = rand.nextBoolean();
        PhoneCallPool pcp = PhoneCallPool.getInstance();
        PhoneCall pc = pcp.pullPC();

        if(pc.getLevel()==this.position){
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
                    this.escalate(pc);
                    System.out.printf("E(%s)[%s...] cost %s s for escalate PC(%s)[%s...]\n",
                            this.position,
                            this.id.toString().substring(0, 8),
                            factor,
                            pc.getLevel(),
                            pc.getId().toString().substring(0, 8));
                    pcp.pushPC(pc);
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
        }else{
            pcp.pushPC(pc);
        }

        return Integer.valueOf(1);
    }
}
