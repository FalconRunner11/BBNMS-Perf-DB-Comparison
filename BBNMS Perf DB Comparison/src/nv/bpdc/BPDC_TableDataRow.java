/**
 * This object class contains the table data from the dba_tables table for a single schema in a given environment's DB.
 */

package nv.bpdc;

public class BPDC_TableDataRow {
	
	//-----------------------------------------------------------------//
	
	/** Declare and initialize final variables **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Declare fields **/
	
	private String owner;
	private String tableName;
	private String rank;
	private long numRows;
	private long size;
	
	//-----------------------------------------------------------------//
	
	/** Constructors **/
	
	protected BPDC_TableDataRow(String inc_owner, String inc_tableName, String inc_rank, String inc_numRows, String inc_size) {
		owner = inc_owner;
		tableName = inc_tableName;
		rank = inc_rank;
		numRows = Long.parseLong(inc_numRows.replace(",", ""));
		size = Long.parseLong(inc_size.replace(",", ""));
	}
	
	//-----------------------------------------------------------------//
	
	/** Abstract methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Implemented methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Accessor methods **/
	
	protected String getOwner() {
		return owner;
	}
	
	protected String getTableName() {
		return tableName;
	}
	
	protected String getRank() {
		return rank;
	}
	
	protected long getNumRows() {
		return numRows;
	}
	
	protected long getSize() {
		return size;
	}
	
	//-----------------------------------------------------------------//
	
	/** Mutator methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Protected methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Private methods **/
	
	
	
	//-----------------------------------------------------------------//
	
}
