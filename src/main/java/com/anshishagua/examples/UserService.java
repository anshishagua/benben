package com.anshishagua.examples;

import com.anshishagua.annotations.Autowired;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午2:40
 */

public class UserService {
    @Autowired
    private String name;

    public String getName() {
        return "aaaaaaa";
    }
}