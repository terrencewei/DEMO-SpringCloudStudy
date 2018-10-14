package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.controller.InventoryController;
import com.aaxis.microservice.training.demo1.domain.Inventory;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by terrence on 2018/10/14.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class ContractVerifierBase {

    @Mock
    private InventoryService mInventoryService;

    @InjectMocks
    private InventoryController mInventoryController;



    @Before
    public void setup() {
        given(mInventoryService.findInventoryById(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument != null && "test1".equals(argument);
            }
        }))).willReturn(generate());
        RestAssuredMockMvc.standaloneSetup(mInventoryController);
    }



    private Inventory generate() {
        Inventory inventory = new Inventory();
        inventory.setId("test1");
        inventory.setStock(333);
        return inventory;
    }
}