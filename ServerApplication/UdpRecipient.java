import java.io.*;
import java.net.*;
public class UdpRecipient {
    public static void main(String[] args) {
        File file = new File("test.txt");
        System.out.println("Receiving data...");
        try{
            // Accept file
           acceptFile(file, 8033, 1000);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    private static void acceptFile(File file, int port, int packetSize) throws IOException {

        byte[] data = new byte[packetSize];
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
        DatagramSocket datagramSocket = new DatagramSocket(port);
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        try{
            /* Setting the waiting time: if within 10 seconds
              not a single packet has been received, data reception is ending */
            datagramSocket.setSoTimeout(60000);
            while(true)
            {
                datagramSocket.receive(datagramPacket);
                fileOutputStream.write(data);
                fileOutputStream.flush();
            }
        }
        catch (SocketTimeoutException e)
        {
            // If the waiting time has expired
            fileOutputStream.close();
            System.out.println("The waiting time has expired, data reception is finished");
        }

    }
}

