/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym.net;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.external.tools.pseudonym.Command;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.io.MumpsStream;


/**
 * Class used to accept requests and send the corresponding response
 */
public class RequestRunner extends Thread {
    private static final Logger LOG = Logger.getLogger(RequestRunner.class);
    private static final String US_ASCII = "US-ASCII";

    private Socket socket;
    private int number;
    private String secParam;

    /**
     * Constructor used to set the socket and connection number
     * @param socket socket to accept requests and send responses on
     * @param number connection number of this runner
     */
    public RequestRunner(Socket socket, int number) {
        super("RequestRunner");

        this.socket = socket;
        this.number = number;
    }

    /**
     * Monitors the socket for incoming requests and, when requests come in, responds with the correct response
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        LOG.debug("connection # " + number + " from  " + socket.getInetAddress().getHostAddress() + " initiated");

        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), US_ASCII));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), US_ASCII));

            try {
                for (int count = 1; socket.isConnected(); count++) {
                    String request = MumpsStream.read(in);

                    if (request.length() == 0) {
                        break;
                    }

                    LOG.debug("[ connection #" + number + "] [request #" + count + "] " + request);

                    Command command;
                    
                    if (secParam == null) {
                        command = new Command();
                    } else {
                        command = new Command(secParam);
                    }
                    
                    String response = command.getResponse(request);
                    
                    secParam = command.getSecParam();

                    if (response.length() == 0) {
                        LOG.debug("closing connection #" + number);

                        break;
                    }

                    LOG.debug("[connection #" + number + "] [response #" + count + "] " + response);
                    MumpsStream.write(out, response);
                }
            } finally {
                out.close();
                in.close();
                socket.close();
            }
        } catch (Exception e) {
            LOG.error("unable to handle the request", e);
        }

        LOG.debug("connection #" + number + " from " + socket.getInetAddress().getHostAddress() + " terminated");
    }
}
