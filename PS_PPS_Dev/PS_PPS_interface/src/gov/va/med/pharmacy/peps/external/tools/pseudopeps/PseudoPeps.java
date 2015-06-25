/**
 * Source file created in 2009 by Southwest Research Institute
 */


package gov.va.med.pharmacy.peps.external.tools.pseudopeps;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gov.va.med.exception.FoundationsException;
import gov.va.med.pharmacy.peps.common.utility.PPSConstants;
import gov.va.med.vistalink.rpc.RpcRequest;
import gov.va.med.vistalink.rpc.RpcRequestFactory;
import gov.va.med.vistalink.rpc.RpcResponse;
import gov.va.med.vistalink.security.CallbackHandlerSwing;
import gov.va.med.vistalink.security.VistaKernelPrincipalImpl;


/**
 * Allows PEPS XML messages to be sent to VistA over VistALink.
 * 
 * user: super1 password: nnnnnn0-
 */
public class PseudoPeps {

    private static final String XOBV_RPC_CONTEXT = "XOBV VISTALINK TESTER";
    private static final String SERVER_NAME = "VistAServer";
    private static final String PPSNDFMSSYNCH = "PPS NDFMS SYNC";
    private static final String[] ACTION_ITEMS = new String[] {
        "Word Processing Test", "Add NDF Domain(s)", "Add PDM Domain(s)", "Add Orderable Item", "Add Product Item",
        "Add NDC Item" };
    private static final String DISCONNECT = "Disconnected: Please connect to VistA (see 'etc/jaas.config')";

    private JTextArea filesTextArea;
    private JTextArea responseTextArea;
    private JFrame frame;
    private JLabel statusLabel;
    private JButton loginButton;
    private JButton actionButton;
    private JButton logoutButton;
    private JComboBox actionBox;

    private Map<String, RpcAction> actions;

    private VistaKernelPrincipalImpl userPrincipal;
    private LoginContext loginContext;

    /**
     * Build screen.
     */
    public PseudoPeps() {
        actions = new HashMap<String, RpcAction>();
        actions.put(ACTION_ITEMS[0], new WordProcessingAction());
        actions.put(ACTION_ITEMS[1], new AddNdfDomainAction());
        actions.put(ACTION_ITEMS[2], new AddPdmDomainAction());
        actions.put(ACTION_ITEMS[PPSConstants.I3], new AddOrderableItemAction());
        actions.put(ACTION_ITEMS[PPSConstants.I4], new AddProductItemAction());
        actions.put(ACTION_ITEMS[PPSConstants.I5], new AddNdcItemAction());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(createStatus(), BorderLayout.SOUTH);
        mainPanel.add(createButtons(), BorderLayout.NORTH);
        mainPanel.add(createTextArea(), BorderLayout.CENTER);

        frame = new JFrame("PseudoPeps");
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                logout();
            }
        });

        frame.setSize(PPSConstants.I800, PPSConstants.I600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Launch the utility.
     * 
     * @param args not used
     * @throws UnsupportedLookAndFeelException  UnsupportedLookAndFeelException
     * @throws IllegalAccessException  IllegalAccessException
     * @throws InstantiationException  InstantiationException
     * @throws ClassNotFoundException ClassNotFoundException 
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, 
        IllegalAccessException, UnsupportedLookAndFeelException {
        System.setProperty("java.security.auth.login.config", "etc/jaas.config");

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new PseudoPeps();
            }
        });
    }

    /** 
     * DummyClass placeholder
     */
    public void dummyClass() {
       
    }
   
    /**
     * Login to VistA.
     */
    private void login() {
        try {
            CallbackHandlerSwing cbhSwing = new CallbackHandlerSwing(frame);

            loginContext = new LoginContext(SERVER_NAME, cbhSwing);
            loginContext.login();
            userPrincipal = VistaKernelPrincipalImpl.getKernelPrincipal(loginContext.getSubject());

            actionBox.setSelectedIndex(0);
            statusLabel.setText("Connected as " + userPrincipal.getName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Login error", JOptionPane.ERROR_MESSAGE);
            statusLabel.setText(DISCONNECT);
            logout();
        }
    }

    /**
     * Logout of VistA.
     */
    private void logout() {
        if (userPrincipal != null) {
            try {
                loginContext.logout();
            } catch (LoginException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Logout error", JOptionPane.ERROR_MESSAGE);
            }

            statusLabel.setText(DISCONNECT);
            userPrincipal = null;
        }
    }

    /**
     * Create status bar.
     * 
     * @return bar
     */
    private JComponent createStatus() {
        statusLabel = new JLabel();
        statusLabel.setText(DISCONNECT);
        statusLabel.setFocusable(false);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.add(statusLabel);

        return labelPanel;
    }

    /**
     * Create buttons.
     * 
     * @return buttons
     */
    private JComponent createButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                login();

                loginButton.setEnabled(userPrincipal == null);
                logoutButton.setEnabled(userPrincipal != null);
                actionButton.setEnabled(userPrincipal != null);
                actionBox.setEnabled(userPrincipal != null);
            }
        });
        buttonPanel.add(loginButton);

        logoutButton = new JButton("Logout");
        logoutButton.setEnabled(false);
        logoutButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loginButton.setEnabled(true);
                logoutButton.setEnabled(false);
                actionButton.setEnabled(false);
                actionBox.setEnabled(false);

                logout();
            }
        });
        buttonPanel.add(logoutButton);

        buttonPanel.add(new JSeparator(JSeparator.VERTICAL));
        buttonPanel.add(new JLabel("PEPS Message:"));

        actionBox = new JComboBox(ACTION_ITEMS);
        actionBox.setEnabled(false);
        actionBox.setEditable(false);
        actionBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                RpcAction action = actions.get(actionBox.getSelectedItem());

                StringBuffer text = new StringBuffer();
                text.append("Request:\n\n").append(action.getRpc()).append("\n");

                for (int i = 0; i < action.getFiles().length; i++) {
                    text.append("(" + (i + 1) + ") " + action.getFiles()[i] + "\n");
                }

                filesTextArea.setText(text.toString());
            }
        });
        buttonPanel.add(actionBox);

        actionButton = new JButton("Send");
        actionButton.setEnabled(false);
        actionButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                RpcAction action = actions.get(actionBox.getSelectedItem());
                String response = "Response:\n\n";
               
                try {
                    responseTextArea.setForeground(Color.BLACK);
                    responseTextArea.setText(response + action.doAction());
                } catch (Exception ex) {
                    responseTextArea.setForeground(Color.RED);
                    responseTextArea.setText(response + ex.getMessage());
                }

                responseTextArea.setCaretPosition(0);
            }
        });
        buttonPanel.add(actionButton);

        return buttonPanel;
    }

    /**
     * Create text areas.
     * 
     * @return text areas.
     */
    private JComponent createTextArea() {
        JSplitPane textPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        textPanel.setDividerLocation(PPSConstants.I300);

        filesTextArea = new JTextArea("Request:\n");
        filesTextArea.setEditable(false);
        textPanel.setLeftComponent(new JScrollPane(filesTextArea));

        responseTextArea = new JTextArea("Response:\n");
        responseTextArea.setEditable(false);
        textPanel.setRightComponent(new JScrollPane(responseTextArea));

        return textPanel;
    }

    /**
     * Generic action.
     */
    private abstract class RpcAction {

        /**
         * getFiles
         * 
         * @return String[]
         */
        public abstract String[] getFiles();

        /**
         * getRpc
         * 
         * @return String
         */
        public abstract String getRpc();

        /**
         * doAction
         * @throws IOException IOException
         * @throws FoundationsException FoundationsException
         * 
         * @return String
         */
        public abstract String doAction() throws IOException, FoundationsException;

        /**
         * sendFiiles
         * 
         * @param files input files
         * @return String
         * @throws FoundationsException  FoundationsException
         * @throws IOException IOException
         */
        protected String sendFiles(String[] files) throws FoundationsException, IOException {
            StringBuilder builder = new StringBuilder();

            for (String file : files) {
                builder.append("-------------------------- " + file + " --------------------------\n");

                builder.append(sendFile(new File(file))).append("\n");
            }
            
            return builder.toString();
        }

        /**
         * sendFile
         * 
         * @param file File
         * @return String
         * @throws FoundationsException  FoundationsException
         * @throws IOException  IOException
         */
        protected String sendFile(File file) throws FoundationsException, IOException {
            RpcRequest request = RpcRequestFactory.getRpcRequest(XOBV_RPC_CONTEXT, getRpc());
            request.setParams(new ArrayList<String>(Arrays.asList(new String[] { readXml(file) })));

            RpcResponse response = userPrincipal.getAuthenticatedConnection().executeRPC(request);

            return response.getResults();
        }

        /**
         * readXML for RpcAction
         * 
         * @param file File
         * @return String
         * @throws IOException exception
         */
        protected String readXml(File file) throws IOException {
            InputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try {
                byte[] buffer = new byte[PPSConstants.I1024];

                for (int length = in.read(buffer); length > 0; length = in.read(buffer)) {
                    out.write(buffer, 0, length);
                }
            } finally {

                // Close the input files
                in.close();
                out.close();
            }

            return new String(out.toByteArray());
        }
    }

    /**
     * WordProcessingAction
     */
    private class WordProcessingAction extends RpcAction {

        /**
         * getFiles
         * 
         * @return String[]
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getFiles()
         */
        public String[] getFiles() {
            return new String[] {};
        }

        /**
         * getRPC for WordProcessingAction
         * 
         * @return String
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getRpc()
         */
        public String getRpc() {
            return "XOBV TEST WORD PROCESSING";
        }

        /**
         * doAction for WordProcessingAction
         * 
         * @return String
         * @throws IOException IOException
         * @throws FoundationsException FoundationsException
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#doAction()
         */
        public String doAction() throws IOException, FoundationsException {
            RpcRequest request = RpcRequestFactory.getRpcRequest(XOBV_RPC_CONTEXT, getRpc());
            RpcResponse response = userPrincipal.getAuthenticatedConnection().executeRPC(request);

            return response.getResults();
        }
    }

    /**
     * AddNdcItemAction
     */
    private class AddNdcItemAction extends RpcAction {

        /**
         * getFiles
         * 
         * @return String[]
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getFiles()
         */
        public String[] getFiles() {
            return new String[] { "etc/add/ndcItem.xml" };
        }

        /**
         * getRpc for AddNdcItemAction
         * 
         * @return String
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getRpc()
         */
        public String getRpc() {
            return PPSNDFMSSYNCH;
        }

        /**
         * doAction
         * 
         * @return String
         * @throws IOException IOException
         * @throws FoundationsException  FoundationsException
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#doAction()
         */
        public String doAction() throws FoundationsException, IOException {
            return sendFiles(getFiles());
        }
    }

    /**
     * AddNdfDomainAction
     */
    private class AddNdfDomainAction extends RpcAction {

        /**
         * getFiles
         * 
         * @return String[]
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getFiles()
         */
        public String[] getFiles() {
            return new String[] { "etc/add/ndfDomain.xml" };
        }

        /**
         * getRpc for AddNdfDomainAction
         * 
         * @return String
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getRpc()
         */
        public String getRpc() {
            return PPSNDFMSSYNCH;
        }

        /**
         * doAction for AddNdfDomainAction
         * 
         * @return String
         * @throws IOException IOException
         * @throws FoundationsException FoundationsException.  This is a VA Exception
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#doAction()
         */
        public String doAction() throws FoundationsException, IOException {
            return sendFiles(getFiles());
        }
    }

    /**
     * AddPdmDomainAction for PseudoPeps
     */
    private class AddPdmDomainAction extends RpcAction {

        /**
         * getFiles
         * 
         * @return String[]
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getFiles()
         */
        public String[] getFiles() {
            return new String[] { "etc/add/pdmDomain.xml" };
        }

        /**
         * getRpc for AddPdmDomainAction
         * 
         * @return String
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getRpc()
         */
        public String getRpc() {
            return PPSNDFMSSYNCH;
        }

        /**
         * doAction for AddPdmDomainAction
         * 
         * @return String
         * @throws IOException IOException. Input output exception
         * @throws FoundationsException FoundationsException
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#doAction()
         */
        public String doAction() throws FoundationsException, IOException {
            return sendFiles(getFiles());
        }
    }

    /**
     * AddOrderableItemAction
     */
    private class AddOrderableItemAction extends RpcAction {

        /**
         * getFiles
         * 
         * @return String[]
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getFiles()
         */
        public String[] getFiles() {
            return new String[] { "etc/add/orderableItem.xml" };
        }

        /**
         * getRpc for AddOrderableItemAction
         * 
         * @return String
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getRpc()
         */
        public String getRpc() {
            return PPSNDFMSSYNCH;
        }

        /**
         * doAction for AddOrderableItemAction
         * 
         * @return String
         * @throws IOException IOException
         * @throws FoundationsException FoundationsException
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#doAction()
         */
        public String doAction() throws FoundationsException, IOException {
            return sendFiles(getFiles());
        }
    }

    /**
     * AddProductItemAction
     */
    private class AddProductItemAction extends RpcAction {

        /**
         * getFiles
         * 
         * @return String[]
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getFiles()
         */
        public String[] getFiles() {
            return new String[] { "etc/add/productItem.xml" };
        }

        /**
         * getRpc for AddProductItemAction
         * 
         * @return String
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#getRpc()
         */
        public String getRpc() {
            return PPSNDFMSSYNCH;
        }

        /**
         * doAction for AddProductItemAction
         * 
         * @return String
         * @throws IOException 
         * @throws FoundationsException 
         * 
         * @see gov.va.med.pharmacy.peps.external.tools.pseudopeps.PseudoPeps.RpcAction#doAction()
         */
        public String doAction() throws FoundationsException, IOException {
            return sendFiles(getFiles());
        }
    }
}
