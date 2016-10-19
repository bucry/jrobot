package com.jrobot.website.demo.service;

import com.jrobot.website.demo.repository.DemoRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by twcn on 10/19/16.
 */

@Service
public class DemoService {

    @Inject
    private DemoRepository demoRepository;

    public String demo() {
        return demoRepository.demo();
    }
}
