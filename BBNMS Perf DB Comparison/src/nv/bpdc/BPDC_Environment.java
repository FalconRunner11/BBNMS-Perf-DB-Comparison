/**
 * This object class contains all of the connection information for all of a given environment's databases/schemas.
 */

package nv.bpdc;

public class BPDC_Environment {
	
	//-----------------------------------------------------------------//
	
	/** Declare and initialize final variables **/
	
	private final String jdbcUrlPrefix = "jdbc:oracle:thin:@";
	
	//-----------------------------------------------------------------//
	
	/** Declare fields **/
	
	private String name;
	private String venusConnectionUrl;
	private String venusUsername;
	private String venusPassword;
	private String nimbusConnectionUrl;
	private String nimbusUsername;
	private String nimbusPassword;
	private String cramerConnectionUrl;
	private String cramerUsername;
	private String cramerPassword;
	private String activationConnectionUrl;
	private String activationUsername;
	private String activationPassword;
	private String mediationConnectionUrl;
	private String mediationUsername;
	private String mediationPassword;
	
	//-----------------------------------------------------------------//
	
	/** Constructors **/
	
	protected BPDC_Environment(String inc_name, String inc_venusConnectionUrl, String inc_venusUsername, String inc_venusPassword, String inc_nimbusConnectionUrl, 
							   String inc_nimbusUsername, String inc_nimbusPassword, String inc_cramerConnectionUrl, String inc_cramerUsername, String inc_cramerPassword, 
							   String inc_activationConnectionUrl, String inc_activationUsername, String inc_activationPassword, String inc_mediationConnectionUrl, 
							   String inc_mediationUsername, String inc_mediationPassword) {
		name = inc_name;
		venusConnectionUrl = jdbcUrlPrefix + inc_venusConnectionUrl;
		venusUsername = inc_venusUsername;
		venusPassword = inc_venusPassword;
		nimbusConnectionUrl = jdbcUrlPrefix + inc_nimbusConnectionUrl;
		nimbusUsername = inc_nimbusUsername;
		nimbusPassword = inc_nimbusPassword;
		cramerConnectionUrl = jdbcUrlPrefix + inc_cramerConnectionUrl;
		cramerUsername = inc_cramerUsername;
		cramerPassword = inc_cramerPassword;
		activationConnectionUrl = jdbcUrlPrefix + inc_activationConnectionUrl;
		activationUsername = inc_activationUsername;
		activationPassword = inc_activationPassword;
		mediationConnectionUrl = jdbcUrlPrefix + inc_mediationConnectionUrl;
		mediationUsername = inc_mediationUsername;
		mediationPassword = inc_mediationPassword;
	}
	
	//-----------------------------------------------------------------//
	
	/** Abstract methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Implemented methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Accessor methods **/
	
	protected String getName() {
		return name;
	}
	
	protected String getVenusConnectionUrl() {
		return venusConnectionUrl;
	}
	
	protected String getVenusUsername() {
		return venusUsername;
	}
	
	protected String getVenusPassword() {
		return venusPassword;
	}
	
	protected String getNimbusConnectionUrl() {
		return nimbusConnectionUrl;
	}
	
	protected String getNimbusUsername() {
		return nimbusUsername;
	}
	
	protected String getNimbusPassword() {
		return nimbusPassword;
	}
	
	protected String getCramerConnectionUrl() {
		return cramerConnectionUrl;
	}
	
	protected String getCramerUsername() {
		return cramerUsername;
	}
	
	protected String getCramerPassword() {
		return cramerPassword;
	}
	
	protected String getActivationConnectionUrl() {
		return activationConnectionUrl;
	}
	
	protected String getActivationUsername() {
		return activationUsername;
	}
	
	protected String getActivationPassword() {
		return activationPassword;
	}
	
	protected String getMediationConnectionUrl() {
		return mediationConnectionUrl;
	}
	
	protected String getMediationUsername() {
		return mediationUsername;
	}
	
	protected String getMediationPassword() {
		return mediationPassword;
	}
	
	//-----------------------------------------------------------------//
	
	/** Mutator methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Protected methods **/
	
	
	
	//-----------------------------------------------------------------//
	
	/** Private methods **/
	
	
	
	//-----------------------------------------------------------------//
	
}
