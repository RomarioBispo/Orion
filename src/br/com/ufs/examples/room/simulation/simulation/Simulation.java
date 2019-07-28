package br.com.ufs.examples.room.simulation.simulation;
import com.java.Class.Class;

import java.util.concurrent.TimeUnit;

public class Simulation {

    public static void main(String[] args) throws Exception {

        int classNumber = 1;

        while(true) {
            System.out.println("Iniciando aula "+ classNumber++);

            Class classObj = new Class();
            classObj.startClass();

            TimeUnit.SECONDS.sleep(5);
        }
    }
}
