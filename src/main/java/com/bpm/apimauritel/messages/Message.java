package com.bpm.apimauritel.messages;

public class Message {
		
		//VALIDATION  
	
		public static String INVALID_PHONE_NUMBER="Invalid Phone number";
		public static String INVALID_IP_ADDRESS="Invalid IP Address";
		public static String INVALID_AMOUNT_VALUE="Amount has to be digit and  superior to zero";
		
		//SWAGGER
		
		public static final String DOC_API_DESCRIPTION="This API allows users to Buy credit or mauritel packages using Bankily's APP";
		public static final String DOC_API_NAME="MAURITEL API";
		public static final String SECURITY_SCHEME="my  security";
		public static final String SCHEME="bearer";
		public static final String VERSION="version";
		
		//API-KEY (This API is not actually used)
		
		public static final String API_KEY="$2a$10$Ogcwr1/qwBQldu2ac290xuzl.S845.TXet8c58wSMwSsseEi5h/gG";

		//HTTP
		
		public static final String AUTHORIZATION="Authorization";
		public static final String PERMISSION_DENIED="Permission denied";
		public static final String IP_ACCES_DENIED="IP address is not allowed";
		public static final String DOWN_SERVICE="THE SERVICE IS DOWN";
		public static final String ILLEGAL_IP="ILLEGAL IP ADDRESS";
		public static final String THIRD_PARTY_API_IS_DOWN="CONNECTION TO MAURITEL IS NOT WORKING";
		public static final String ILLEGAL_AUTHORIZATION="Illegal Authorization";
		
		//MAURITEL MESSAGE
		
		public static final String ERROR_UNKNOWN_SERVICE="Ce service Mauritel n'existe pas ou n'est pas disponible !!!";
		
		public static final String MAURITEL_SERVER_UP="1";
		
		public static final String MAURITEL_SERVER_DOWN="0";
		

		public static final String MESSAGE_MAURITEL_SERVER_DOWN="MAURITEL SERVICE IS DOWN";
		
		
		
		public static final String EXECPTION_CALL_MAURITEL_SERVER="EXCEPTION WHEN CALLING MAURITEL API";
		
		
		
		
		
		
}
