//package nbcenter.test;
//
//import javax.bluetooth.*;
//import javax.microedition.io.*;
//import com.atinav.BCC;
//public class WirelessDevice implements DiscoveryListener {
//    LocalDevice localDevice = null; 
//    
//    public WirelessDevice (){ 
//        //setting the port number using Atinav's BCC
//        BCC.setPortName("COM1"); 
//        L2CAPConnection
//        //setting the baud rate using Atinav's BCC
//        BCC.setBaudRate(57600);
//        
//        //connectable mode using Atinav's BCC
//        BCC.setConnectable(true);
//        
//        //Set discoverable mode using Atinav's BCC 
//        BCC.setDiscoverable(DiscoveryAgent.GIAC); 
//        
//        try{
//            localDevice = LocalDevice.getLoaclDevice(); 
//        }
//        catch (BluetoothStateException exp) {
//        }
//        
//        // implementation of methods in DiscoveryListener class
//        // of javax.bluetooth goes here
//        
//        // now do some work
//    }
//
//    @Override
//    public void deviceDiscovered(RemoteDevice arg0, DeviceClass arg1) {
//      // TODO Auto-generated method stub
//      
//    }
//
//    @Override
//    public void inquiryCompleted(int arg0) {
//      // TODO Auto-generated method stub
//      
//    }
//
//    @Override
//    public void serviceSearchCompleted(int arg0, int arg1) {
//      // TODO Auto-generated method stub
//      
//    }
//
//    @Override
//    public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
//      // TODO Auto-generated method stub
//      
//    }
//}