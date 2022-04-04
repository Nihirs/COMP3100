
import java.io.*;
import java.net.*;
import java.util.ArrayList;

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
            //dout.write(("REDY\n").getBytes());
            //str1 = (String) in.readLine();
            
            ArrayList<String> serverList=new ArrayList<String>();
            String Largest_type=null;
            ArrayList<Integer> largest_serverID=new ArrayList<Integer>();
            int numCores=0;
            //while(!((String)in.readLine()).equals("NONE")){
            dout.write(("REDY\n").getBytes());
            str1 = (String) in.readLine();
            System.out.println(str1);
            String [] jobDetails=str1.split(" ");
            if((jobDetails[0].toUpperCase()).equals("JOBN")){
            dout.write(("GETS Capable "+jobDetails[4]+" " +jobDetails[5]+ " "+jobDetails[6] +"\n").getBytes());
            str1 = (String) in.readLine();
            System.out.println(str1);
            String[] extracter = str1.split(" ");
            int nRecs = Integer.parseInt(extracter[1]);
            dout.write(("OK\n").getBytes());
            
            for (int i = 0; i < nRecs; i++) {
                
             //   str1 = null;
                str1 = (String) in.readLine();
              //  serverList.add(str1);
              //  String[] splitter= str1.split(" ");
               // int core= Integer.parseInt(splitter[4]);
               // int id=Integer.parseInt(splitter[1]);

               // if((splitter[2].toLowerCase())!=("unavailable")){
               // if(Largest_type.equals(splitter[0]) ){
                 //   largest_serverID.add(id);
               // }
               // if(numCores<core){
                //    Largest_type=splitter[0];
               //     numCores=core;
               //     largest_serverID.clear();
               //     largest_serverID.add(id);
               // }
           //}
               System.out.println(str1);

            }
         
        }
            dout.write(("OK\n").getBytes());
            str1 = (String) in.readLine();
            System.out.println(str1);
            for(int i=0;i<largest_serverID.size();i++){
            dout.write(("SCHD "+jobDetails[2]+" "+Largest_type+" "+largest_serverID.get(i)+"\n").getBytes());
            str1 = (String) in.readLine();
            }
        //}
            dout.write(("QUIT\n").getBytes());
            str1=(String) in.readLine();
            System.out.println(str1);
            dout.close();
            s.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
   
}
