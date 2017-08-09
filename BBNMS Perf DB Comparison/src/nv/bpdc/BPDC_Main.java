/**
 * This class reads table data from specific DBs.
 * The X top tables for ST1, ST6, and F1 are ordered based on the X top tables from PROD.
 * The ordered data is written to a single .xls file.
 */

package nv.bpdc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BPDC_Main {
	
	//-----------------------------------------------------------------//
	
	/** Declare and initialize final variables **/
	
	private final BPDC_Dialog_Error errorDialog = new BPDC_Dialog_Error();
	
	private final String dbConfigFile = "db_config.prop";
	
	private final String prodVenusFile = "PROD_venus.txt";
	private final String prodNimbusFile = "PROD_nimbus.txt";
	private final String prodCramerFile = "PROD_cramer.txt";
	private final String prodActivationFile = "PROD_activation.txt";
	private final String prodMediationFile = "PROD_mediation.txt";
	
	private final String venusString = "VENUS";
	private final String nimbusString = "NIMBUS";
	private final String cramerString = "CRAMER";
	private final String activationString = "ACTIVATION";
	private final String mediationString = "MEDIATION";
	
	private final String prodString = "PROD";
	private final String f1String = "F1";
	private final String st1String = "ST1";
	private final String st6String = "ST6";

	private final String queryFront = "select owner, table_name, num_rows, round((num_rows * avg_row_len) / (1024 * 1024)) \"SIZE_(MB)\" " + 
									  "from dba_tables " + 
									  "where num_rows is not null and owner = '";
	private final String queryBack = "' " + 
									 "order by num_rows desc";
	
	private final int desiredTableComparisonLimit = 25;
	
	//-----------------------------------------------------------------//
	
	/** Declare global variables **/
	
	private ArrayList<BPDC_Environment> environmentsList;
	
	private ArrayList<BPDC_TableDataRow> prodVenusData;
	private ArrayList<BPDC_TableDataRow> st1VenusData;
	private ArrayList<BPDC_TableDataRow> st6VenusData;
	private ArrayList<BPDC_TableDataRow> f1VenusData;
	private ArrayList<BPDC_TableDataRow> prodNimbusData;
	private ArrayList<BPDC_TableDataRow> st1NimbusData;
	private ArrayList<BPDC_TableDataRow> st6NimbusData;
	private ArrayList<BPDC_TableDataRow> f1NimbusData;
	private ArrayList<BPDC_TableDataRow> prodCramerData;
	private ArrayList<BPDC_TableDataRow> st1CramerData;
	private ArrayList<BPDC_TableDataRow> st6CramerData;
	private ArrayList<BPDC_TableDataRow> f1CramerData;
	private ArrayList<BPDC_TableDataRow> prodActivationData;
	private ArrayList<BPDC_TableDataRow> st1ActivationData;
	private ArrayList<BPDC_TableDataRow> st6ActivationData;
	private ArrayList<BPDC_TableDataRow> f1ActivationData;
	private ArrayList<BPDC_TableDataRow> prodMediationData;
	private ArrayList<BPDC_TableDataRow> st1MediationData;
	private ArrayList<BPDC_TableDataRow> st6MediationData;
	private ArrayList<BPDC_TableDataRow> f1MediationData;
	
	//-----------------------------------------------------------------//
	
	/** Initialize global variables **/
	
	private void initVars() {
		environmentsList = new ArrayList<BPDC_Environment>();
		
		prodVenusData = new ArrayList<BPDC_TableDataRow>();
		st1VenusData = new ArrayList<BPDC_TableDataRow>();
		st6VenusData = new ArrayList<BPDC_TableDataRow>();
		f1VenusData = new ArrayList<BPDC_TableDataRow>();
		prodNimbusData = new ArrayList<BPDC_TableDataRow>();
		st1NimbusData = new ArrayList<BPDC_TableDataRow>();
		st6NimbusData = new ArrayList<BPDC_TableDataRow>();
		f1NimbusData = new ArrayList<BPDC_TableDataRow>();
		prodCramerData = new ArrayList<BPDC_TableDataRow>();
		st1CramerData = new ArrayList<BPDC_TableDataRow>();
		st6CramerData = new ArrayList<BPDC_TableDataRow>();
		f1CramerData = new ArrayList<BPDC_TableDataRow>();
		prodActivationData = new ArrayList<BPDC_TableDataRow>();
		st1ActivationData = new ArrayList<BPDC_TableDataRow>();
		st6ActivationData = new ArrayList<BPDC_TableDataRow>();
		f1ActivationData = new ArrayList<BPDC_TableDataRow>();
		prodMediationData = new ArrayList<BPDC_TableDataRow>();
		st1MediationData = new ArrayList<BPDC_TableDataRow>();
		st6MediationData = new ArrayList<BPDC_TableDataRow>();
		f1MediationData = new ArrayList<BPDC_TableDataRow>();
	}
	
	//-----------------------------------------------------------------//
	
	/** Main method and class declaration/initialization **/
	
	public static void main(String[] args) {
		new BPDC_Main().start();
	}
	
	//-----------------------------------------------------------------//
	
	/** Abstract methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Implemented methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Protected methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Private methods **/
	
	private void start() {
		initVars();
		generateEnvironmentsList();
		getTableData(prodString, venusString);
		getTableData(f1String, venusString);
		getTableData(st1String, venusString);
		getTableData(st6String, venusString);
		getTableData(prodString, nimbusString);
		getTableData(f1String, nimbusString);
		getTableData(st1String, nimbusString);
		getTableData(st6String, nimbusString);
		getTableData(prodString, cramerString);
		getTableData(f1String, cramerString);
		getTableData(st1String, cramerString);
		getTableData(st6String, cramerString);
		getTableData(prodString, activationString);
		getTableData(f1String, activationString);
		getTableData(st1String, activationString);
		getTableData(st6String, activationString);
		getTableData(prodString, mediationString);
		getTableData(f1String, mediationString);
		getTableData(st1String, mediationString);
		getTableData(st6String, mediationString);
		
		ArrayList<ArrayList<BPDC_TableDataRow>> venusDataList = new ArrayList<ArrayList<BPDC_TableDataRow>>();
		venusDataList.add(prodVenusData);
		venusDataList.add(f1VenusData);
		venusDataList.add(st1VenusData);
		venusDataList.add(st6VenusData);
		ArrayList<ArrayList<BPDC_TableDataRow>> nimbusDataList = new ArrayList<ArrayList<BPDC_TableDataRow>>();
		nimbusDataList.add(prodNimbusData);
		nimbusDataList.add(f1NimbusData);
		nimbusDataList.add(st1NimbusData);
		nimbusDataList.add(st6NimbusData);
		ArrayList<ArrayList<BPDC_TableDataRow>> cramerDataList = new ArrayList<ArrayList<BPDC_TableDataRow>>();
		cramerDataList.add(prodCramerData);
		cramerDataList.add(f1CramerData);
		cramerDataList.add(st1CramerData);
		cramerDataList.add(st6CramerData);
		ArrayList<ArrayList<BPDC_TableDataRow>> activationDataList = new ArrayList<ArrayList<BPDC_TableDataRow>>();
		activationDataList.add(prodActivationData);
		activationDataList.add(f1ActivationData);
		activationDataList.add(st1ActivationData);
		activationDataList.add(st6ActivationData);
		ArrayList<ArrayList<BPDC_TableDataRow>> mediationDataList = new ArrayList<ArrayList<BPDC_TableDataRow>>();
		mediationDataList.add(prodMediationData);
		mediationDataList.add(f1MediationData);
		mediationDataList.add(st1MediationData);
		mediationDataList.add(st6MediationData);
		
		BPDC_XLSXWriter excelWriter = new BPDC_XLSXWriter(venusDataList, nimbusDataList, cramerDataList, activationDataList, mediationDataList, desiredTableComparisonLimit);
		excelWriter.buildXLSFile();
	}
	
	private String[] parseConfigFileLine(String inc_line) {
		inc_line = inc_line.trim();
		String[] tokens = inc_line.split("<|>");
		return tokens;
	}
	
	private String[] parseTableDataRowLine(String inc_line) {
		inc_line = inc_line.trim();
		String[] tokens = inc_line.split("\\s+");		// Match all white space characters
		return tokens;
	}
	
	private void generateEnvironmentsList() {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		BufferedReader inStream = null;
		try {
			inStream = new BufferedReader(new FileReader(dbConfigFile));
			String nextLine = inStream.readLine();
			while (nextLine != null) {
				lines.add(parseConfigFileLine(nextLine));
				nextLine = inStream.readLine();
			}
		}
		catch (Exception e) {
			errorDialog.setExceptionDialog(null, e);
		}
		finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					errorDialog.setExceptionDialog(null, e);
				}
			}
		}
		
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i)[1].equals("environment")) {
				environmentsList.add(new BPDC_Environment(lines.get(i + 1)[2], lines.get(i + 3)[2], lines.get(i + 4)[2], lines.get(i + 5)[2], lines.get(i + 8)[2], 
														  lines.get(i + 9)[2], lines.get(i + 10)[2], lines.get(i + 13)[2], lines.get(i + 14)[2], lines.get(i + 15)[2], 
														  lines.get(i + 18)[2], lines.get(i + 19)[2], lines.get(i + 20)[2], lines.get(i + 23)[2], lines.get(i + 24)[2], 
														  lines.get(i + 25)[2]));
				i += 25;
			}
		}
	}
	
	private void getTableData(String inc_environmentName, String inc_schema) {
		ArrayList<BPDC_TableDataRow> tableData = new ArrayList<BPDC_TableDataRow>();
		
		// Special case for PROD
		if (inc_environmentName.equals(prodString)) {
			String file = "";
			if (inc_schema.equals(venusString)) {
				file = prodVenusFile;
			}
			else if (inc_schema.equals(nimbusString)) {
				file = prodNimbusFile;
			}
			else if (inc_schema.equals(cramerString)) {
				file = prodCramerFile;
			}
			else if (inc_schema.equals(activationString)) {
				file = prodActivationFile;
			}
			else if (inc_schema.equals(mediationString)) {
				file = prodMediationFile;
			}
			BufferedReader inStream = null;
			String[] tokens;
			int rank = 1;
			try {
				inStream = new BufferedReader(new FileReader(file));
				String nextLine = inStream.readLine();
				while (nextLine != null) {
					tokens = parseTableDataRowLine(nextLine);
					tableData.add(new BPDC_TableDataRow(tokens[0], tokens[1], Integer.toString(rank), tokens[2], tokens[3]));
					rank++;
					nextLine = inStream.readLine();
				}
			}
			catch (Exception e) {
				errorDialog.setExceptionDialog(null, e);
			}
			finally {
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						errorDialog.setExceptionDialog(null, e);
					}
				}
			}
		}
		
		else {
			String connectionString = "";
			String username = "";
			String password = "";
			String queryText = queryFront + inc_schema + queryBack;
			ArrayList<ArrayList<String>> result;
			
			for (int i = 0; i < environmentsList.size(); i++) {
				if (environmentsList.get(i).getName().equals(inc_environmentName)) {
					if (inc_schema.equals("VENUS")) {
						connectionString = environmentsList.get(i).getVenusConnectionUrl();
						username = environmentsList.get(i).getVenusUsername();
						password = environmentsList.get(i).getVenusPassword();
					}
					else if (inc_schema.equals("NIMBUS")) {
						connectionString = environmentsList.get(i).getNimbusConnectionUrl();
						username = environmentsList.get(i).getNimbusUsername();
						password = environmentsList.get(i).getNimbusPassword();
					}
					else if (inc_schema.equals("CRAMER")) {
						connectionString = environmentsList.get(i).getCramerConnectionUrl();
						username = environmentsList.get(i).getCramerUsername();
						password = environmentsList.get(i).getCramerPassword();
					}
					else if (inc_schema.equals("ACTIVATION")) {
						connectionString = environmentsList.get(i).getActivationConnectionUrl();
						username = environmentsList.get(i).getActivationUsername();
						password = environmentsList.get(i).getActivationPassword();
					}
					else if (inc_schema.equals("MEDIATION")) {
						connectionString = environmentsList.get(i).getMediationConnectionUrl();
						username = environmentsList.get(i).getMediationUsername();
						password = environmentsList.get(i).getMediationPassword();
					}
					break;
				}
			}
			
			result = new ArrayList<ArrayList<String>>();
			BPDC_Dialog_Progress_DataFetch dataFetchDialog = new BPDC_Dialog_Progress_DataFetch();
			dataFetchDialog.setQueryParameters(connectionString, username, password, queryText);
			dataFetchDialog.showMessageDialog(null);
			if (dataFetchDialog.getTaskStatus() == 1) {
				result = dataFetchDialog.getQueryResults();
			}
			else {
				System.exit(0);
			}
			
			ArrayList<String> queryResultRow;
			for (int i = 0; i < result.size(); i++) {
				queryResultRow = result.get(i);
				tableData.add(new BPDC_TableDataRow(queryResultRow.get(0), queryResultRow.get(1), Integer.toString(i + 1), queryResultRow.get(2), queryResultRow.get(3)));
			}
			tableData = serializeData(inc_schema, tableData);
		}
		
		setTableData(inc_environmentName, inc_schema, tableData);
	}
	
	private void setTableData(String inc_environmentName, String inc_schema, ArrayList<BPDC_TableDataRow> inc_tableData) {
		if (inc_environmentName.equals(prodString)) {
			if (inc_schema.equals(venusString)) {
				prodVenusData = inc_tableData;
			}
			else if (inc_schema.equals(nimbusString)) {
				prodNimbusData = inc_tableData;
			}
			else if (inc_schema.equals(cramerString)) {
				prodCramerData = inc_tableData;
			}
			else if (inc_schema.equals(activationString)) {
				prodActivationData = inc_tableData;
			}
			else if (inc_schema.equals(mediationString)) {
				prodMediationData = inc_tableData;
			}
		}
		else if (inc_environmentName.equals(f1String)) {
			if (inc_schema.equals(venusString)) {
				f1VenusData = inc_tableData;
			}
			else if (inc_schema.equals(nimbusString)) {
				f1NimbusData = inc_tableData;
			}
			else if (inc_schema.equals(cramerString)) {
				f1CramerData = inc_tableData;
			}
			else if (inc_schema.equals(activationString)) {
				f1ActivationData = inc_tableData;
			}
			else if (inc_schema.equals(mediationString)) {
				f1MediationData = inc_tableData;
			}
		}
		else if (inc_environmentName.equals(st1String)) {
			if (inc_schema.equals(venusString)) {
				st1VenusData = inc_tableData;
			}
			else if (inc_schema.equals(nimbusString)) {
				st1NimbusData = inc_tableData;
			}
			else if (inc_schema.equals(cramerString)) {
				st1CramerData = inc_tableData;
			}
			else if (inc_schema.equals(activationString)) {
				st1ActivationData = inc_tableData;
			}
			else if (inc_schema.equals(mediationString)) {
				st1MediationData = inc_tableData;
			}
		}
		else if (inc_environmentName.equals(st6String)) {
			if (inc_schema.equals(venusString)) {
				st6VenusData = inc_tableData;
			}
			else if (inc_schema.equals(nimbusString)) {
				st6NimbusData = inc_tableData;
			}
			else if (inc_schema.equals(cramerString)) {
				st6CramerData = inc_tableData;
			}
			else if (inc_schema.equals(activationString)) {
				st6ActivationData = inc_tableData;
			}
			else if (inc_schema.equals(mediationString)) {
				st6MediationData = inc_tableData;
			}
		}
	}
	
	private ArrayList<BPDC_TableDataRow> serializeData(String inc_schema, ArrayList<BPDC_TableDataRow> inc_tableData) {
		ArrayList<BPDC_TableDataRow> orderedTableData = new ArrayList<BPDC_TableDataRow>();
		ArrayList<BPDC_TableDataRow> prodTableData = new ArrayList<BPDC_TableDataRow>();
		
		if (inc_schema.equals(venusString)) {
			prodTableData = prodVenusData;
		}
		else if (inc_schema.equals(nimbusString)) {
			prodTableData = prodNimbusData;
		}
		else if (inc_schema.equals(cramerString)) {
			prodTableData = prodCramerData;
		}
		else if (inc_schema.equals(activationString)) {
			prodTableData = prodActivationData;
		}
		else if (inc_schema.equals(mediationString)) {
			prodTableData = prodMediationData;
		}
		
		int actualTableComparisonLimit = Math.min(Math.min(desiredTableComparisonLimit, inc_tableData.size()), prodTableData.size());
		
		for (int i = 0; i < actualTableComparisonLimit; i++) {
			for (int j = 0; j < inc_tableData.size(); j++) {
				if (inc_tableData.get(j).getTableName().equals(prodTableData.get(i).getTableName())) {
					orderedTableData.add(inc_tableData.get(j));
					break;
				}
				if (j == inc_tableData.size() - 1) {
					// PROD table not found in lab DB
					orderedTableData.add(new BPDC_TableDataRow(prodTableData.get(i).getOwner(), prodTableData.get(i).getTableName(), "DNE", "0", "0"));
				}
			}
		}
		return orderedTableData;
	}
	
	//-----------------------------------------------------------------//
	
}
