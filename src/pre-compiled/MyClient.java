
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class MyClient {
    private static boolean firstLoop = true;//to ensure that the number of cores doesnt change
    private static int serverPicker = 0;//to iterate for the job scheduling part

    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 50000);//making and starting the socket for the connection
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));//buffered reader that will receive the messages from the ds-server

            DataOutputStream dout = new DataOutputStream(s.getOutputStream());//DataOutputStream that will enable us to send messages to send messages back to the server

            dout.write(("HELO\n").getBytes());//initiating the handshake
            dout.flush();//clearing the output stream
            String str = (String) in.readLine(); //taking in input from the server and changing into string

            System.out.println(str);//printing out the message from the server
            dout.write(("AUTH nihir\n").getBytes());//Authenticating my identity to the server
            dout.flush();//clearing the output stream
            String str1 = (String) in.readLine();//taking in input from the server and changing into string
            System.out.println(str1);//printing out the message from the server which will be ok
            //Handshake is done
            String Largest_type = null;//making a string that will hold the largest server type
            ArrayList<Integer> largest_serverID = new ArrayList<Integer>();//this is basically an arraylist that will hold all the ids of the largest server type (a safety measuere if the id dont all start from 0 to n)
            int numCores = 0;//an integer variable that will hold the max cores which will be used to find the largest server
            
            dout.write(("REDY\n").getBytes());//sending the server REDY which basically signals the server that the client is ready to take in input and the hand shake is done
            str1 = (String) in.readLine();//Reading to learn the message the server has sent us
            System.out.println(str1);//Printing out the output which will be the JOBN or something else 
            String looper = str1.substring(0, 4);//Only taking the first four chars of the message as that is all we need to find out what type of message is it
            ArrayList<String> jobList=new ArrayList<String>();
            while (!looper.equals("NONE")) {//using the previous line of code and checking if it had returned NONE which means the server doesnt have anything to do for us
                jobList.add(str1);
                String[] jobDetails = str1.split(" ");//splitting the earlier message and putting all its attribute into an array
                if ((jobDetails[0].toUpperCase()).equals("JOBN")) {//since this stage only concerns with Jobs and their schdeuling we are only taking jobn statements 
                    dout.write(("GETS Capable " + jobDetails[4] + " " + jobDetails[5] + " " + jobDetails[6] + "\n")//putting together a Gets capable command 
                            .getBytes());
                    str1 = (String) in.readLine();//taking in next input from server
                    System.out.println(str1);//printing out what they sent us which will be the DATA and how much data they have
                    String[] extracter = str1.split(" ");//splitting it because we want to know how many servers they have
                    int nRecs = Integer.parseInt(extracter[1]);// changing the string we changes into integer
                    dout.write(("OK\n").getBytes());//sending in OK to the server

                    for (int i = 0; i < nRecs; i++) {//making a loop to find the largest server type and then also keeping the server Ids in the arraylist

                        str1 = (String) in.readLine();//taking in server details
                        System.out.println(str1);//printing out the server details
                        if (firstLoop) {//this is to make sure we only find the biggest server the first time
                            String[] splitter = str1.split(" ");//splitting the server details and putting in an array
                            int core = Integer.parseInt(splitter[4]);//taking out the number of cores
                            int id = Integer.parseInt(splitter[1]);//taking out the ID

                            if (numCores < core) {//checking if the cores of this server is more than the max Cores found yet
                                Largest_type = splitter[0];//taking out the server type and putting it as the largest server
                                numCores = core;//taking out the cores from the server and putting it as the max cores
                                largest_serverID.clear();//clearing the largest server Id list as we have a new largest server type
                                largest_serverID.add(id);//putting in the ID number of the server
                            }

                            else if (Largest_type.equals(splitter[0])) {//if the largest type server is the same as current server
                                largest_serverID.add(id);//adding the id number of current server
                            }

                        }

                    }

                    dout.write(("OK\n").getBytes());//sending OK to the server
                    str1 = (String) in.readLine();//taking in input from the server
                    System.out.println(str1);//printing out the reply

                    if (serverPicker < largest_serverID.size()) {//conditional for the job scheduling to iterate through the servers
                        dout.write(
                                ("SCHD " + jobDetails[2] + " " + Largest_type + " " + largest_serverID.get(serverPicker)//scheduling a job to the server using the jobDetails from earlier, mad using server picker to iterate though largest_serverID 
                                        + "\n").getBytes());
                        str1 = (String) in.readLine();//taking in the reply to the scheduled job
                        System.out.println(str1);//printing the reply
                        serverPicker++;//adding the iterator
                    }
                    if (serverPicker > (largest_serverID.size()) - 1) {//when the iterator is either the size of the ID pool or more
                        serverPicker = 0;//resetting the iterator to zero
                    }
                }

                dout.write(("REDY\n").getBytes());//sending ready so that the server send us another task
                str1 = (String) in.readLine();//taking in the response
                looper = str1.substring(0, 4);//splitting and storing the first four characters as to check if it is NONE or JOBN or neither
                firstLoop = false;//setting the first loop to false so we only sort through the servers once
            }
            dout.write(("QUIT\n").getBytes());//Sending termination command to the server
            str1 = (String) in.readLine();//taking in their termination message
            System.out.println(str1);//printing the termination message which should be QUIT
            dout.close();//closingthe data output stream
            s.close();//closing the scoket hence terminating the connection

        } catch (Exception e) { //catching the cases where we for soem reason arent able to execute try
            System.out.println(e);//printing the exception
        }
    }

}
