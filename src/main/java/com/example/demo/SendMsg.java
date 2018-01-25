package com.example.demo;

import com.example.demo.smswake.CMPPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 曾晓阳
 * Date: 2018/1/24
 * Time: 14:45
 */
@RestController
public class SendMsg {

    @Autowired
    CMPPClient cmppClient;

    @RequestMapping("/sendTboxMsg/{number}")
    public String send(@PathVariable String number){
        try {
            cmppClient.sendNotifySms("111111", number);
            return "yes";
        }catch (Exception e){
            return "no";
        }
    }

}
