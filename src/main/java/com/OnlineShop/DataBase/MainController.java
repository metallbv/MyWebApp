package com.OnlineShop.DataBase;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by v.babiak on 05.06.2016.
 */
@Controller
@RequestMapping(value = "/myservice")
public class MainController {

    // этот метод будетпринимать ArrayList WebData, методом GET
    // отвечать клиенту
    @RequestMapping(value= "/{time}", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<WebData> getMyData(@PathVariable long time) {
        ArrayList<WebData> arrayWebData = new ArrayList<>();
        return arrayWebData;
    }

    // этот метод будет методом POST, формировать ArrayList WebData
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ArrayList<WebData> postMyData() {
        ArrayList<WebData> arrayWebData = new ArrayList<>();
        return arrayWebData;
    }

}
