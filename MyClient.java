
import java.io.*;
import java.net.*;

public class MyClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 50000);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            dout.write(("HELO\n").getBytes());
            dout.flush();
            String str = (String) in.readLine();

            System.out.println(str);
            dout.write(("AUTH nihir\n").getBytes());
            dout.flush();
            String str1 = (String) in.readLine();
            System.out.println(str1);
            dout.write(("REDY\n").getBytes());
            str1 = (String) in.readLine();
            System.out.println(str1);
            dout.write(("GETS All 2 300 500\n").getBytes());
            str1 = (String) in.readLine();
            System.out.println(str1);
            String[] extracter = str1.split(" ");
            int nRecs = Integer.parseInt(extracter[1]);
            dout.write(("OK\n").getBytes());
            for (int i = 0; i < nRecs; i++) {
                str1 = null;
                str1 = (String) in.readLine();
                System.out.println(str1);

            }

            dout.write(("OK\n").getBytes());
            str1 = (String) in.readLine();
            System.out.println(str1);
            dout.write(("SCHD 0 4xlarge 1\n").getBytes());
            str1 = (String) in.readLine();
            dout.close();
            s.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
