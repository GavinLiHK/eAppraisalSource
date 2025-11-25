package com.hkha.ea.helper;

import java.util.HashMap;
import java.util.Map;

import com.hkha.ea.common.Constants;

public class EaUtililty {

	public static String getFullStatus(String status){
		if (status.equals(Constants.STATUS_CD)){
			return Constants.STATUS_CD_FULL;
		}else if (status.equals(Constants.STATUS_AO)){
			return Constants.STATUS_AO_FULL;
		}else if (status.equals(Constants.STATUS_AP)){
			return Constants.STATUS_AP_FULL;
		}else if (status.equals(Constants.STATUS_CO)){
			return Constants.STATUS_CO_FULL;
		}else if (status.equals(Constants.STATUS_IO)){
			return Constants.STATUS_IO_FULL;
		}else if (status.equals(Constants.STATUS_EO)){
			return Constants.STATUS_EO_FULL;
		}else if (status.equals(Constants.STATUS_RO)){
			return Constants.STATUS_RO_FULL;
		}else if (status.equals(Constants.STATUS_GM)){
			return Constants.STATUS_GM_FULL;
		}
		
		return "";
	}
}
