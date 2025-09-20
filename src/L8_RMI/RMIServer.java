/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L8_RMI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
/**
 *
 * @author OS
 */
public class RMIServer {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        LocaiteRegistry 
        CalculatorImpl cal = new CalculatorImpl();
        Naming.rebind("rmi://localhost://calx2", cal);
        
        System.out.println("Calculator RMI server is running");
    }
}
