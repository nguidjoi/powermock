package com.powermock.poc.helper.helper;

import com.powermock.poc.utils.CommonPowerMockUtils;
import org.springframework.stereotype.Component;

/**
 * Created by sogla on 07/09/2016.
 */
@Component
public class MockHelper {
    /**
     * Get user firstname.
     * @return firstname
     */
    public static String getName(){
        return CommonPowerMockUtils.USER_LASTNAME;
    }

    /**
     * Get user lastname.
     * @return lastname
     */
    public final String getSurname(){
        return CommonPowerMockUtils.USER_FIRSTNAME;
    }

    /**
     * Get age.
     * @param age
     * @return
     */
    private String getAge(String age){
        return age;
    }
}
