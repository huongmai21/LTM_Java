/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L8_RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author OS
 */
public class CalculatorClient {
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        Calculator cal = (Calculator)Naming.lookup("rmi://localhost/calx2");
        int sum = cal.add(100, 200);
        System.out.println("sum (from remote server): " + sum);
    }
}
