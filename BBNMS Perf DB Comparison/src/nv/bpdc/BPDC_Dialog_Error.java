/**
 * This class presents the user with a dialog that displays information about any exceptions or general errors which are thrown by the application.
 */

package nv.bpdc;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class BPDC_Dialog_Error extends JOptionPane implements ActionListener {
	
	//-----------------------------------------------------------------//
	
	/** Declare and initialize final variables **/
	
	private final String dialogTitle = "BBNMS Perf DB Comparison";
	
	//-----------------------------------------------------------------//
	
	/** Declare global variables **/
	
	private JFrame hubFrame;
	private JLabel label;
	private JDialog dialog;
	private JTextArea textArea;
	private JButton okButton;
	
	//-----------------------------------------------------------------//
	
	/** Initialize global variables **/
	
	private void initVars() {
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		
		textArea = new JTextArea();
		textArea.setColumns(20);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
	}
	
	//-----------------------------------------------------------------//
	
	/** Abstract methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Implemented methods **/
	
	public void actionPerformed(ActionEvent e) {
		dialog.dispose();
	}
	
	//-----------------------------------------------------------------//
	
	/** Protected methods **/
	
	protected void setExceptionDialog(JFrame inc_frame, Exception inc_exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		inc_exception.printStackTrace(pw);
		String stackTrace = sw.toString();
		initVars();
		createAndShowGUI(inc_frame, "Exception:  " + inc_exception.getClass(), stackTrace);
	}
	
	protected void setErrorDialog(JFrame inc_frame, String inc_errorMessage) {
		initVars();
		createAndShowGUI(inc_frame, "Application Error", inc_errorMessage);
	}
	
	//-----------------------------------------------------------------//
	
	/** Private methods **/
	
	private void createAndShowGUI(JFrame inc_frame, String inc_labelText, String inc_messageText) {
		hubFrame = inc_frame;
		label = new JLabel(inc_labelText);
		textArea.setText(inc_messageText);
		final JOptionPane pane = new JOptionPane(buildMainPanel(), JOptionPane.ERROR_MESSAGE, JOptionPane.PLAIN_MESSAGE);
		pane.setComponentOrientation((getRootFrame()).getComponentOrientation());
		pane.setMessageType(PLAIN_MESSAGE);
		pane.setOptions(new Object[] {});		// Removes default JOptionPane buttons, so that custom ones may be used.
		dialog = pane.createDialog(null, dialogTitle);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent wE) {
				// Take no additional action as the JDialog is closing.
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
		mainPanel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(EtchedBorder.RAISED), "Error/Exception Encountered!"));
		
		// Error label
		currentGridX = 0;
		currentGridY = 0;
		mainPanelConstraints.gridx = currentGridX;
		mainPanelConstraints.gridy = currentGridY;
		mainPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		mainPanelConstraints.insets = new Insets(5, 10, 0, 10);
		mainPanel.add(label, mainPanelConstraints);
		
		// Error message scrollpane
		currentGridX = 0;
		currentGridY++;
		JScrollPane textAreaScrollPane = new JScrollPane(textArea);
		textAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		textAreaScrollPane.setPreferredSize(new Dimension(300, 200));
		mainPanelConstraints.gridx = currentGridX;
		mainPanelConstraints.gridy = currentGridY;
		mainPanelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		mainPanelConstraints.insets = new Insets(0, 10, 10, 10);
		mainPanel.add(textAreaScrollPane, mainPanelConstraints);
		
		// OK button
		currentGridX = 0;
		currentGridY++;
		mainPanelConstraints.gridx = currentGridX;
		mainPanelConstraints.gridy = currentGridY;
		mainPanelConstraints.anchor = GridBagConstraints.PAGE_START;
		mainPanelConstraints.insets = new Insets(0, 10, 10, 10);
		mainPanel.add(okButton, mainPanelConstraints);
		
		return mainPanel;
	}
	
	//-----------------------------------------------------------------//
	
}
