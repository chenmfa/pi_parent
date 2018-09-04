package nbcenter.test;

import java.io.IOException;
import java.util.Set;
import java.util.Vector;
import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 * Minimal Device Discovery example.
 */
public class RemoteDeviceDiscovery {

    public static final Vector/*<RemoteDevice>*/ devicesDiscovered = new Vector();

    public static void main(String[] args) throws IOException, InterruptedException {

        final Object inquiryCompletedEvent = new Object();

        devicesDiscovered.clear();
        findDevices();
        DiscoveryListener listener = new DiscoveryListener() {

            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                System.out.println("Device " + btDevice.getBluetoothAddress() + " found");
                devicesDiscovered.addElement(btDevice);
                try {
                    System.out.println("     name " + btDevice.getFriendlyName(false));
                } catch (IOException cantGetDeviceName) {
                }
            }

            public void inquiryCompleted(int discType) {
                System.out.println("Device Inquiry completed!");
                synchronized(inquiryCompletedEvent){
                    inquiryCompletedEvent.notifyAll();
                }
            }

            public void serviceSearchCompleted(int transID, int respCode) {
            }

            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
            }
        };

        synchronized(inquiryCompletedEvent) {
            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, listener);
            if (started) {
                System.out.println("wait for device inquiry to complete...");
                inquiryCompletedEvent.wait();
                System.out.println(devicesDiscovered.size() +  " device(s) found");
            }
        }
    }
    
    

    private static void findDevices() throws IOException, InterruptedException {

        final Object inquiryCompletedEvent = new Object();

        devicesDiscovered.clear();

        DiscoveryListener listener = new DiscoveryListener() {
            public void inquiryCompleted(int discType) {
                System.out.println("#" + "搜索完成");
                synchronized (inquiryCompletedEvent) {
                    inquiryCompletedEvent.notifyAll();
                }
            }

            @Override
            public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
                devicesDiscovered.add(remoteDevice);

                try {
                    System.out.println("#发现设备" + remoteDevice.getFriendlyName(false));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void servicesDiscovered(int arg0, ServiceRecord[] arg1) {
                System.out.println("#" + "servicesDiscovered");
            }

            @Override
            public void serviceSearchCompleted(int arg0, int arg1) {
                System.out.println("#" + "serviceSearchCompleted");
            }
        };

        synchronized (inquiryCompletedEvent) {

            LocalDevice ld = LocalDevice.getLocalDevice();

            System.out.println("#本机蓝牙名称:" + ld.getFriendlyName());

            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC,listener);

            if (started) {
                System.out.println("#" + "等待搜索完成...");
                inquiryCompletedEvent.wait();
                    LocalDevice.getLocalDevice().getDiscoveryAgent().cancelInquiry(listener);
                System.out.println("#发现设备数量：" + devicesDiscovered.size());
            }
        }
    }
}