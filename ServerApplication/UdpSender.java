import java.io.*;
import java.net.*;
public class UdpSender {
    public static void main(String[] args) {
        try {
            byte[] data = new byte[1000];
            DatagramSocket datagramSocket = new DatagramSocket();
            InetAddress inetAddress = InetAddress.getLocalHost();
            /*файл с именем должен лежать в корне проекта*/
            FileInputStream fileInputStream = new FileInputStream(new File("test.txt"));
            DatagramPacket packet;
            while (fileInputStream.read(data) != -1)
            {
                //создание пакета данных
                packet = new DatagramPacket(data, data.length, inetAddress, 8033);
                datagramSocket.send(packet); //отправление пакета
            }
            fileInputStream.close();
            System.out.println("File send");
        }

        catch (UnknownHostException e)
        {
        // неверный адрес получателя
            e.printStackTrace();
        } catch (SocketException e)
        {
        // возникли ошибки при передаче данных
            e.printStackTrace();
        } catch (FileNotFoundException e) {
        // не найден отправляемый файл
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
