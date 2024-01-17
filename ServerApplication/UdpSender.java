import java.io.*;
import java.net.*;
public class UdpSender {
    public static void main(String[] args) {
        try {
            byte[] data = new byte[1000];
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress inetAddress = InetAddress.getLocalHost();
            /*the file with the name should be in the root of the project*/
            FileInputStream fileInputStream = new FileInputStream(new File("test.txt"));
            DatagramPacket packet;
            while (fileInputStream.read(data) != -1)
            {
                //creating a data package
                packet = new DatagramPacket(data, data.length, inetAddress, 8033);
                datagramSocket.send(packet); //sending a package
            }
            fileInputStream.close();
            System.out.println("File send");
        }

        catch (UnknownHostException e)
        {
        // invalid recipient address
            e.printStackTrace();
        } catch (SocketException e)
        {
        // errors occurred during data transmission
            e.printStackTrace();
        } catch (FileNotFoundException e) {
        // the file being sent was not found
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
