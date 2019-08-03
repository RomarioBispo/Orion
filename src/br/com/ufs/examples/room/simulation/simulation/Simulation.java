package br.com.ufs.examples.room.simulation.simulation;

import br.com.ufs.examples.room.simulation.Class.Class;

import java.util.concurrent.TimeUnit;

/**
 * This class is used to concept proof for the Orion Framework.
 * To this only purpose, the project developed in Felipe Matheus undergraduate thesis was used.
 * Your project was modified to use the framework.
 *
 * @author Romario Bispo, Felipe Matheus.
 * @version %I%, %G%
 * @since 1.0
 * @see br.com.ufs.examples.room.context.context.ContextExample;
 * */

public class Simulation {

    public static void main(String[] args) throws Exception {

        int classNumber = 1;

        while(true) {
            System.out.println("Iniciando aula "+ classNumber++);
            Class classObj = new Class();
            classObj.startClass();

            TimeUnit.SECONDS.sleep(8);
        }
    }
}
