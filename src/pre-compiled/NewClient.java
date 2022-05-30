import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class NewClient {
    private static boolean firstLoop = true;// to ensure that the number of cores doesnt change
    private static int serverPicker = 0;// to iterate for the job scheduling part
    
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 50000);// making and starting the socket for the connection
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));// buffered reader that
                                                                                              // will receive the
                                                                                              // messages from the
                                                                                              // ds-server

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());// DataOutputStream that will enable us to
                                                                              // send messages to send messages back to
                                                                              // the server

            dout.write(("HELO\n").getBytes());// initiating the handshake
            dout.flush();// clearing the output stream
            String str = (String) in.readLine(); // taking in input from the server and changing into string

            System.out.println(str);// printing out the message from the server
            dout.write(("AUTH nihir\n").getBytes());// Authenticating my identity to the server
            dout.flush();// clearing the output stream
            String str1 = (String) in.readLine();// taking in input from the server and changing into string
            System.out.println(str1);// printing out the message from the server which will be ok
            // Handshake is done
            String Largest_type = null;// making a string that will hold the laplete Project Documention/Sponsor MarkAssignmentrgest server type
            ArrayList<Integer> largest_serverID = new ArrayList<Integer>();// this is basically an arraylist that will
                                                                           // hold all the ids of the largest server
                                                                           // type (a safety measuere if the id dont all
                                                                           // start from 0 to n)
            int numCores = 0;// an integer variable that will hold the max cores which will be used to find
                             // the largest server

            dout.write(("REDY\n").getBytes());// sending the server REDY which basically signals the server that the
                                              // client is ready to take in input and the hand shake is done
            str1 = (String) in.readLine();// Reading to learn the message the server has sent us
            System.out.println(str1);// Printing out the output which will be the JOBN or something else
            String looper = str1.substring(0, 4);// Only taking the first four chars of the message as that is all we
                                                 // need to find out what type of message is it
            ArrayList<String> jobList = new ArrayList<String>();
            int j=0;
            while (!looper.equals("NONE")) {// using the previous line of code and checking if it had returned NONE
                String first=null;                            // which means the server doesnt have anything to do for us
                jobList.add(str1);
                String[] jobDetails = str1.split(" ");// splitting the earlier message and putting all its attribute
                                                      // into an array
                if ((jobDetails[0].toUpperCase()).equals("JOBN")) {// since this stage only concerns with Jobs and their
                                                                   // schdeuling we are only taking jobn statements
                int jobCore=Integer.parseInt(jobDetails[4]);
                
                    dout.write(("GETS Capable " + jobDetails[4] + " " + jobDetails[5] + " " + jobDetails[6] + "\n")// putting
                                                                                                                   // together
                                                                                                                   // a
                                                                                                                   // Gets
                                                                                                                   // capable
                                                                                                                   // command
                            .getBytes());
                    str1 = (String) in.readLine();// taking in next input from server
                    System.out.println(str1);// printing out what they sent us which will be the DATA and how much data
                                             // they have
                    String[] extracter = str1.split(" ");// splitting it because we want to know how many servers they
                                                         // have
                    int nRecs = Integer.parseInt(extracter[1]);// changing the string we changes into integer
                    dout.write(("OK\n").getBytes());// sending in OK to the server
                    for (int i = 0; i < nRecs; i++) {
                        str1 = (String) in.readLine();// taking in server details
                        System.out.println(str1);// printing out the server details
                        jobList.add(str1);


                    }
                    ArrayList<String> newList= new ArrayList<String>();
                    
                    // for(int i=0; i<jobList.size();i++){
                    //     String currentJob= jobList.get(i);
                    //     String [] spliiter= currentJob.split(" ");
                    //     if (i==0){
                    //         String maxCores=
                    //     }

                    

                    dout.write(("OK\n").getBytes());// sending OK to the server
                    str1 = (String) in.readLine();// taking in input from the server
                    System.out.println(str1);// printing out the reply
                    String [] details= 
                    
                    
                }

                dout.write(("REDY\n").getBytes());// sending ready so that the server send us another task
                str1 = (String) in.readLine();// taking in the response
                looper = str1.substring(0, 4);// splitting and storing the first four characters as to check if it is
                                              // NONE or JOBN or neither
                
            }
            dout.write(("QUIT\n").getBytes());// Sending termination command to the server
            str1 = (String) in.readLine();// taking in their termination message
            System.out.println(str1);// printing the termination message which should be QUIT
            dout.close();// closingthe data output stream
            s.close();// closing the scoket hence terminating the connection

        } catch (Exception e) { // catching the cases where we for soem reason arent able to execute try
            System.out.println(e);// printing the exception
        }
    }

}
