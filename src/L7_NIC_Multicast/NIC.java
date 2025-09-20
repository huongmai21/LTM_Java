/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package L7_NIC_Multicast;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 *
 * @author OS
 */
public class NIC {
    public static void main(String[] args) throws SocketException {
       Enumeration<NetworkInterface> nifs=  NetworkInterface.getNetworkInterfaces();
       while(nifs.hasMoreElements()){
           NetworkInterface ni = nifs.nextElement();
           if(ni.isUp()){
               System.out.println(ni);
           }
       }
    }
}
