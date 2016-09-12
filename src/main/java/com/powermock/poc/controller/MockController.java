package com.powermock.poc.controller;

import com.powermock.poc.helper.helper.MockHelper;
import com.powermock.poc.utils.ModelPowerMockAttributUtils;
import com.powermock.poc.utils.UrlPowerMockUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
@RequestMapping(UrlPowerMockUtils.HOME)
public class MockController {

    @Autowired
    MockHelper mockHelper;

    @RequestMapping( method = RequestMethod.GET)
    public final ModelAndView getRequest(Locale locale, Model model) {
        // initializing view.
        final ModelAndView mav = new ModelAndView();

        mav.addObject(ModelPowerMockAttributUtils.NAME_ATTRIBUT, MockHelper.getName());
        mav.addObject(ModelPowerMockAttributUtils.SURNAME_ATTRIBUT, mockHelper.getSurname());
       return mav;
    }


}
