package org.minima.system.params;

public class GeneralParams {

	/**
	 * Is this a private network - don;t connect to any users..
	 */
	public static boolean PRIVATE_NETWORK = false;
	
	/**
	 * Are we automining a TxPoW every block
	 */
	public static boolean AUTOMINE = false;
	
	/**
	 * Are we creating the genesis block 
	 */
	public static boolean GENESIS = false;
	
	/**
	 * Are we wiping previous data 
	 */
	public static boolean CLEAN = false;
	
	/**
	 * Where are the configuration and database files stored. This is set at startup.
	 */
	public static String CONFIGURATION_FOLDER = "";
	
	/**
	 * The main Minima port
	 */
	public static int MINIMA_PORT = 9001;
	
	/**
	 * The Minima RPC port
	 */
	public static int RPC_PORT = 9002;
	
	/**
	 * Test Params or Main Params
	 */
	public static boolean TEST_PARAMS = false;
	
	/**
	 * Is ther P2P System Enabled
	 */
	public static boolean P2P_ENABLED = true;
	
	/**
	 * Host and IP of the first P2P node..
	 */
	public static String P2P_ROOTNODE = "";
	
	/**
	 * Manual list of Minima nodes to connect to
	 */
	public static String CONNECT_LIST = "";
}
