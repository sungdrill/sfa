package com.cellbiotech.exception;

import com.cellbiotech.dao.sfa.ErrorLogListRepository;
import com.cellbiotech.model.sfa.ErrorLogList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by user on 2017-08-29.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ErrorLogListRepository errorLogListRepository;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {

        HttpStatus status = getStatus(request);

        ErrorLogList errorLogList = new ErrorLogList();

        String interfaceName = "eth0";
        NetworkInterface networkInterface = null;
        String ip = "UNKNOWN";
        try {
            networkInterface = NetworkInterface.getByName(interfaceName);

            Enumeration<InetAddress> inetAddress = networkInterface.getInetAddresses();
            InetAddress currentAddress;
            currentAddress = inetAddress.nextElement();
            while(inetAddress.hasMoreElements())
            {
                currentAddress = inetAddress.nextElement();
                if(currentAddress instanceof Inet4Address && !currentAddress.isLoopbackAddress())
                {
                    ip = currentAddress.toString();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(":::::::::::::::::"+e.getMessage());

            InetAddress local = null;
            try {
                local = InetAddress.getLocalHost();
            } catch (UnknownHostException e1) {
                System.out.println("::::::::"+e1.getMessage());
            }

            ip = local.getHostAddress();
        }

        errorLogList.setErrorIp(ip);
        errorLogList.setErrorCode(status.toString());
        errorLogList.setErrorContent(ex.getMessage());
        errorLogList.setErrorDate(new Date());

        errorLogListRepository.save(errorLogList);

        return new ResponseEntity(new CustomError(status.toString(), ex.getMessage()), status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
