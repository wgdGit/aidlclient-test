package stb.iptv.outward.aidl;

interface IPTVOutWardAidl {    
 	String getParams(String keyName);
 	String getMultiParams(String strkeyNames); 	 	
 	String getIPTVPlayUrl(String jsonStr);
}