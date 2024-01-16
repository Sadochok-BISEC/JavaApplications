import java.io.*;
import java.net.*;
public class UdpRecipient {
    public static void main(String[] args) {
        File file = new File("test.txt");
        System.out.println("Прием данных...");
        try{
            //Прием файла
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
            /* установка времени ожидания: если в течение 10 секунд
               не принято ни одного пакета, прием данных заканчивается*/
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
            // если время ожидания вышло
            fileOutputStream.close();
            System.out.println("Истекло время ожидания, прием данных закончен");
        }

    }
}

