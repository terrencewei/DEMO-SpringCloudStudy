package com.aaxis.microservice.training.demo1.service;

import com.aaxis.microservice.training.demo1.controller.PriceController;
import com.aaxis.microservice.training.demo1.domain.ItemPrice;
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
    private ItemPriceService mItemPriceService;

    @InjectMocks
    private PriceController mPriceController;



    @Before
    public void setup() {
        given(mItemPriceService.findItemPriceById(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument != null && "A_1".equals(argument);
            }
        }))).willReturn(generate());
        RestAssuredMockMvc.standaloneSetup(mPriceController);
    }



    private ItemPrice generate() {
        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setId("A_1");
        itemPrice.setPrice(338.33);
        return itemPrice;
    }
}