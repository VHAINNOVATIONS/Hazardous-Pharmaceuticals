/**
 * Source file created in 2006 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudonym;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.pharmacy.peps.external.tools.pseudonym.net.RequestRunner;


/**
 * This class imitates the behavior of a VistA server. It will accept incoming mumps commands sent to it via VistALink and
 * respond with the appropriate VistALink responses.
 */
public class Pseudonym {
    private static final Logger LOG = Logger.getLogger(Pseudonym.class);
    private static final String SLEEP_INTERUPTION = "Application was interrupted while trying to sleep";

    private int port;
    private boolean listening;
    private ServerSocket serverSocket;
    private Thread listener;

    
    /**
     * This constructor sets up the new object to be prepared to listen on a specific port number when started.
     * 
     * @param port port number to listen on
     */
    public Pseudonym(int port) {
        this.port = port;
    }
    
    /**
     * Instantiates and runs the server on a specific port. Used for command line running and testing.
     * 
     * @param args expects one integer for the port to run on
     */
    public static void main(String[] args) {
        try {
            if (args.length == 1) {
                int port = Integer.parseInt(args[0]);

                Pseudonym pseudonym = new Pseudonym(port);
                pseudonym.start();
            } else {
                LOG.error("PseudonyM usage: [port]");
                System.exit(0);
            }
        } catch (Throwable t) {
            LOG.error("An error occurred", t);
            System.exit(0);
        }
    }



    /**
     * Used to start an instance of the server. This method loads the commands from the properties file into memory, sets up
     * the listener socket and prepares the server to accept incoming requests.
     * 
     * @return boolean true if server started or false if the server was already running
     * @throws PseudonymException if an exception occurred while initializing the commands or if there was a problem trying
     *             to setup the listening socket. Also if the application was interrupted while trying to sleep. If thrown
     *             the server was not correctly started.
     */
    public synchronized boolean start() throws PseudonymException {
        if (listening) {
            return false;
        }

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e1) {
            throw new PseudonymException("An error occurred while trying to listen on this port", e1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {

            public void run() {
                if ((serverSocket != null) && (!serverSocket.isClosed())) {
                    try {
                        serverSocket.close();
                    } catch (Exception e) {
                        LOG.error("Error occurred while trying to close the socket", e);
                    }
                }
            }
        });

        this.listener = new Thread() {

            public void run() {
                try {
                    for (int count = 1; listening; count++) {
                        Socket socket = serverSocket.accept();

                        RequestRunner runner = new RequestRunner(socket, count);
                        runner.start();
                    }
                } catch (IOException e) {
                    LOG.error("Error occurred while setting up the socket to listen for requests", e);
                }
            }
        };

        listener.start();

        while (!listener.isAlive()) {
            try {
                Thread.sleep(PPSConstants.I200);
            } catch (InterruptedException e) {
                throw new PseudonymException(SLEEP_INTERUPTION, e);
            }
        }

        this.listening = true;

        LOG.info("PseudonyM listening on localhost at port " + port);

        return true;
    }

    /**
     * This method stops the currently running instance of the server. When called, it will block until the server has
     * stopped.
     * 
     * @throws PseudonymException thrown if the socket was not able to be closed correctly, or if the application was
     *             interrupted while sleeping.
     */
    public synchronized void stop() throws PseudonymException {
        if (listening) {
            this.listening = false;

            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new PseudonymException("An error occurred while closing the listening socket", e);
            }

            while (listener.isAlive()) {
                try {
                    Thread.sleep(PPSConstants.I200);
                } catch (InterruptedException e) {
                    throw new PseudonymException(SLEEP_INTERUPTION, e);
                }
            }
        }
    }
}
