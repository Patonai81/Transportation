package hu.webuni.transportation.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ErrorConstants{

    private static Map<ErrorCodes,String> errorCodesStringMap = new HashMap<>();
    static {
        errorCodesStringMap.put(ErrorCodes.BODY_NULL,"Request body cannot be null");
        errorCodesStringMap.put(ErrorCodes.BODY_ID_NOT_NULL,"Request entity ID cannot be set");
        errorCodesStringMap.put(ErrorCodes.BODY_FIELD_NULL,"Field value cannot be null");
    }

    public static String getErrorMessage(String errorCode){
        if (ErrorCodes.valueOf(errorCode) == null) {
            log.error("Givan key is not defined in errorCodes: "+errorCode);
            return null;
        }
        return errorCodesStringMap.get(ErrorCodes.valueOf(errorCode));
    }

}

 enum ErrorCodes {BODY_NULL, BODY_ID_NOT_NULL, BODY_FIELD_NULL;}
