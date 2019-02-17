/**Alex G
 * get a new desk
 * @version 0.0
 */
package RalphStahp;
import java.net.*;
import java.io.*;
import java.util.*;
import java.net.InetAddress;

public class blogThing{
    public static void main(String[] args) throws Exception{
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("System IP Address : " +
                    (localhost.getHostAddress()).trim());


            String systemIpAddress = "";
            try
            {
                    URL url_name = new URL("http://bot.whatismyipaddress.com");

                    BufferedReader sc =
                            new BufferedReader(new InputStreamReader(url_name.openStream()));

                    // reads system IPAddress
                    systemIpAddress = sc.readLine().trim();
            }
            catch (Exception e)
            {
                    systemIpAddress= "Cannot Execute Properly";
            }
            System.out.println("Public IP Address: " + systemIpAddress +"\n");


}
        }