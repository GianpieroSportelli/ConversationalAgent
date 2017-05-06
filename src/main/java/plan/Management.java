/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SPORT
 */
public class Management implements Serializable {

        double spese = 0;

        double accrediti = 0;
        double bilancio = 0;

        List<Transaction> history;

        public Management() {
            history = new LinkedList<>();
        }

        public void addTransaction(boolean up, double value) {
            if (up) {
                accrediti += value;
                bilancio += value;
            } else {
                spese += value;
                bilancio -= value;
            }
            history.add(new Transaction(up, value));
        }

        public double getSpese() {
            return spese;
        }

        public double getAccrediti() {
            return accrediti;
        }

        public double getBilancio() {
            return bilancio;
        }

        public List<Transaction> getHistory() {
            return history;
        }

        public void write(String url, Management m) throws FileNotFoundException, IOException {
            FileOutputStream fout = new FileOutputStream(url);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(m);
            fout.close();
            oos.close();
        }

        public Management read(String url) throws ClassNotFoundException, IOException {
            FileInputStream fout;
            Management management = null;
            try {
                fout = new FileInputStream(url);
                ObjectInputStream oos = new ObjectInputStream(fout);
                if (oos.toString() != null) {
                    management = (Management) oos.readObject();
                }
                fout.close();
                oos.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RegisterPlan.class.getName()).log(Level.INFO, "NON Esiste il file creo l'oggetto e lo salvo!!!");
                management = new Management();
                management.write(url, management);
            }

            return management;
        }

        class Transaction implements Serializable {

            boolean up;
            double value;
            long time;

            Transaction(boolean up, double value) {
                this.up = up;
                this.value = value;
                time = System.currentTimeMillis();
            }
        }

    }
