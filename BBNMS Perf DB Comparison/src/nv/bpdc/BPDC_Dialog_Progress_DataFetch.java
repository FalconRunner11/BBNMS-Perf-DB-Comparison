/**
 * This class presents the user with a dialog that displays the progress of fetching table data from a given database.
 * A separate instance of this dialog is created for each database being checked.
 */

package nv.bpdc;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class BPDC_Dialog_Progress_DataFetch extends JOptionPane implements ActionListener, PropertyChangeListener {
	
	//-----------------------------------------------------------------//
	
	/** Declare and initialize final variables **/
	
	private final String dialogTitle = "BBNMS Perf DB Comparison";
	private final BPDC_Dialog_Error errorDialog = new BPDC_Dialog_Error();
	
	//-----------------------------------------------------------------//
	
	/** Declare global variables **/
	
	private JFrame hubFrame;
	private JDialog dialog;
	private Task task;
	private int taskStatus;		// 0 = canceled, 1 = completed
	private JProgressBar progressBar;
	private JLabel statusLabel;
	private JButton cancelButton;
	private String connectionString;
	private String username;
	private String password;
	private String queryText;
	private ArrayList<ArrayList<String>> queryResults;
	
	//-----------------------------------------------------------------//
	
	/** Initialize global variables **/
	
	private void initVars() {
		taskStatus = 0;
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
		
		queryResults = new ArrayList<ArrayList<String>>();
	}
	
	//-----------------------------------------------------------------//
	
	/** Abstract methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Implemented methods **/
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			cancelCheck();
		}
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
    }
	
	//-----------------------------------------------------------------//
	
	/** Create and manage GUI components **/
	
	protected void showMessageDialog(JFrame inc_frame) {
		hubFrame = inc_frame;
		initVars();
		createAndShowGUI();
	}
	
	private void createAndShowGUI() {
		final JOptionPane pane = new JOptionPane(buildMainPanel(), JOptionPane.ERROR_MESSAGE, JOptionPane.PLAIN_MESSAGE);
		pane.setComponentOrientation((getRootFrame()).getComponentOrientation());
		pane.setMessageType(PLAIN_MESSAGE);
		pane.setOptions(new Object[] {});		// Removes default JOptionPane buttons, so that custom ones may be used.
		dialog = pane.createDialog(null, dialogTitle);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent wE) {
				cancelCheck();
			}
			public void windowClosed(WindowEvent wE) {
				// Take no additional action once the JDialog is closed.
			}
		});
		dialog.pack();
		dialog.validate();
		dialog.setLocationRelativeTo(hubFrame);
		dialog.setVisible(true);
	}
	
	private JPanel buildMainPanel() {
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints mainPanelConstraints = new GridBagConstraints();
		int currentGridX;
		int currentGridY;
		
		// Status panel
		currentGridX = 0;
		currentGridY = 0;
		mainPanelConstraints.gridx = currentGridX;
		mainPanelConstraints.gridy = currentGridY;
		mainPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		mainPanelConstraints.insets = new Insets(5, 10, 0, 10);
		mainPanel.add(buildStatusPanel(), mainPanelConstraints);
		
		// Cancel button
		currentGridX = 0;
		currentGridY++;
		mainPanelConstraints.gridx = currentGridX;
		mainPanelConstraints.gridy = currentGridY;
		mainPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		mainPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		mainPanelConstraints.insets = new Insets(5, 10, 10, 10);
		mainPanel.add(cancelButton, mainPanelConstraints);
		
		return mainPanel;
	}
	
	private JPanel buildStatusPanel() {
		JPanel statusPanel = new JPanel(new GridBagLayout());
		GridBagConstraints statusPanelConstraints = new GridBagConstraints();
		statusPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(EtchedBorder.RAISED), "Status"));
		statusPanel.setPreferredSize(new Dimension(600, 75));
		
		int currentGridX;
		int currentGridY;
		
		// Progress bar
		currentGridX = 0;
		currentGridY = 0;
		statusPanelConstraints.gridx = currentGridX;
		statusPanelConstraints.gridy = currentGridY;
		statusPanelConstraints.weightx = 0.0;
		statusPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
		statusPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		statusPanelConstraints.insets = new Insets(5, 10, 0, 10);
		statusPanel.add(progressBar, statusPanelConstraints);
		
		// Sub-status label
		currentGridX = 0;
		currentGridY++;
		statusPanelConstraints.gridx = currentGridX;
		statusPanelConstraints.gridy = currentGridY;
		statusPanelConstraints.weightx = 1.0;
		statusPanelConstraints.fill = GridBagConstraints.NONE;
		statusPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		statusPanelConstraints.insets = new Insets(5, 10, 10, 10);
		statusPanel.add(statusLabel, statusPanelConstraints);
		
		return statusPanel;
	}
	
	//-----------------------------------------------------------------//
	
	/** Protected methods **/
	
	protected void setQueryParameters(String inc_connectionString, String inc_username, String inc_password, String inc_queryText) {
		connectionString = inc_connectionString;
		username = inc_username;
		password = inc_password;
		queryText = inc_queryText;
		progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        statusLabel = new JLabel("Processing \"" + inc_connectionString + " ...");
	}
	
	protected int getTaskStatus() {
		return taskStatus;
	}
	
	protected ArrayList<ArrayList<String>> getQueryResults() {
		return queryResults;
	}
	
	//-----------------------------------------------------------------//
	
	/** Private methods **/
	
	private void cancelCheck() {
		taskStatus = 0;
		dialog.dispose();
	}
	
	private void closeDialog() {
		taskStatus = 1;
		dialog.dispose();
	}

	//-----------------------------------------------------------------//
	
	/** SwingWorkers **/
	
	class Task extends SwingWorker<Void, Void> {
		public Void doInBackground() {
			
            BPDC_Query query = new BPDC_Query(connectionString, username, password, queryText);
			if (query.getException() != null) {
				errorDialog.setExceptionDialog(null, query.getException());
			}
			queryResults = query.getResult();
            
			statusLabel.setText("Complete");
        	// Pause to reflect update in statusLabel text.
        	try {
				Thread.sleep(500);
			}
        	catch (InterruptedException e) {
				errorDialog.setExceptionDialog(null, e);
			}
            return null;
        }
 
        public void done() {
            dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            closeDialog();
        }
    }
	
	//-----------------------------------------------------------------//
}
