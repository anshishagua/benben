package com.anshishagua.examples;

import com.anshishagua.annotations.Autowired;
import com.anshishagua.annotations.Controller;
import com.anshishagua.annotations.RequestParam;
import com.anshishagua.annotations.UrlMapping;
import com.anshishagua.annotations.Value;
import com.anshishagua.examples.mybatis.GlobalParam;
import com.anshishagua.examples.mybatis.GlobalParamMapper;
import com.anshishagua.examples.mybatis.TypeDefMapper;
import com.anshishagua.object.ModelAndView;

import java.time.LocalDate;

/**
 * User: lixiao
 * Date: 2018/4/12
 * Time: 下午11:00
 */

@Controller
@UrlMapping("/test")
public class TestController {
    @Autowired
    private String name;

    @Autowired
    private UserService userService;

    @Value(property = "a.person")
    private Person person;

    @Value(property = "requiredDate")
    private LocalDate requiredDate;

    @Autowired
    private GlobalParamMapper globalParamMapper;

    @Autowired
    private TypeDefMapper typeDefMapper;

    @UrlMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @UrlMapping(value = "/abc")
    public ModelAndView abc(@RequestParam(name = "age", required = false, defaultValue = "111") int age) {
        //GlobalParam globalParam = globalParamMapper.getByName("WEB_DATE");

        //System.out.println(globalParam);

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("hello");

        System.out.println(person);

        System.out.println(requiredDate);

        System.out.println(userService.getName());

        //System.out.println(typeDefMapper.getAll());
        /*
        if (age > 10) {
            name = "benben is older than " + 10;
        } else {
            name = "benben is younger than " + 10;
        }
        */

        modelAndView.addObject("name", name);
        GlobalParam globalParam = new GlobalParam();

        globalParam.setId(1L);
        globalParam.setName("JAVA_HOME");
        globalParam.setValue("AAA");
        globalParam.setTypeCode("STRING");

        modelAndView.addObject("globalParam", globalParam);

        return modelAndView;
    }
}