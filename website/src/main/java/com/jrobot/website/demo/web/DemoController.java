package com.jrobot.website.demo.web;

import com.jrobot.website.demo.service.DemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.Map;


@Controller
public class DemoController {

    @Inject
    private DemoService demoService;

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demo(Map<String, Object> map) {
        map.put("demo", demoService.demo());
        return "demo";
    }

    @RequestMapping(value = "/demo_post", method = RequestMethod.GET)
    @ResponseBody
    public String restDemo(Map<String, Object> map) {
        return demoService.demo();
    }

}
