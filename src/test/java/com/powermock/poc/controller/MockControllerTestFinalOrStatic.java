package com.powermock.poc.controller;

import com.powermock.poc.helper.helper.MockHelper;
import com.powermock.poc.utils.ModelPowerMockAttributUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

/**
 * Sample test case for showing use of powerMock.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( {MockHelper.class})
        public class MockControllerTestFinalOrStatic {

    /** Controller to test .*/
    MockController controller ;

    /** The helper class use by controller MockController.*/
    MockHelper mockHelper;

    /** the locale, parameter of certain method to test.*/
    Locale locale;

    /** the model interface use by certain tested method .*/
    Model model;

    @Before
    public void setUp() throws Exception {

    }

    /**
     * Test the getRequest method of MockController.
     * @throws Exception
     */
    @Test
    public void testGetRequest() throws Exception {

        // Init data that that the helper class method use as parameter.
        final String surname = getSurname("Alain");
        final String  name = getName("Bell");

        // Init dataBefore test
        initDataBeforeTest();

        // Define and save the expected method calling by tested method invocation.
        defineMethodBehaviour( mockHelper, surname, name);

        // Save the expected method calling by tested method invocation
        replayMock(mockHelper);

        // Calling the method to test.
        ModelAndView modelAndView = controller.getRequest(locale,model);

        // Verify data after test
        verifyModelAndView( modelAndView, surname, name);

    }

    /**
     * Method to  define and save in mock the behaviour of method called by the method to test.
     * @param mockHelper
     * @param surname
     * @param name
     */
    private void defineMethodBehaviour(MockHelper mockHelper, final String surname, final String name){
        // Expectation
        EasyMock.expect(MockHelper.getName()).andReturn(name);
        EasyMock.expect(mockHelper.getSurname()).andReturn(surname);
    }

    /**
     * Method to replay mock.
     * @param mockHelper
     */
    private void replayMock(MockHelper mockHelper) {
        // Note how we replay the class, not the instance!
        PowerMock.replay(MockHelper.class);
        PowerMock.replay(mockHelper);
    }

    /**
     * Method to initialize the data before test.
     */
    private void initDataBeforeTest(){

        //This will mock the MockHelper class containing static method.
        PowerMock.mockStatic(MockHelper.class);

        // This will mock the Model interface
        model  = PowerMock.createMock(Model.class);

        // This will mock the helper class containing the final method
        mockHelper =  PowerMock.createMock(MockHelper.class);

        // init controller that we have to test.
        controller = new MockController();

        //may be ReflectionTestUtils.setField(controller, "mockHelper", mockHelper)
        Whitebox.setInternalState(controller, "mockHelper", mockHelper);

        // init the locale parameter
        locale = Locale.getDefault();

    }

    /**
     * Method to verify the state of object after call of method.
     * @param modelAndView
     * @param surname
     * @param name
     */
    private void verifyModelAndView(ModelAndView modelAndView, String surname, String name){
        // Assert that the modelAndView is not null
        Assert.assertNotNull(modelAndView);

        // Assert that the Model in model and view is not null
        Assert.assertNotNull(modelAndView.getModel());

        // verify that the object in the defined key is in Model
        Assert.assertNotNull(modelAndView.getModel().containsKey(ModelPowerMockAttributUtils.NAME_ATTRIBUT));
        Assert.assertNotNull(modelAndView.getModel().containsKey(ModelPowerMockAttributUtils.SURNAME_ATTRIBUT));

        // verify that the object in model is the one that we have inserted by the tested method call
        Assert.assertEquals((String)modelAndView.getModel().get(ModelPowerMockAttributUtils.SURNAME_ATTRIBUT),surname);
        Assert.assertEquals((String)modelAndView.getModel().get(ModelPowerMockAttributUtils.NAME_ATTRIBUT),name);

        // Note how we verify the class, not the instance!
        PowerMock.verify(MockHelper.class);
        PowerMock.verify(mockHelper);
    }

    /**
     * Method to get the name.
     * @param name
     * @return
     */
    private final String getName(String name){
        return name;
    }

    /**
     * Method to get surname.
     * @return
     */
    private final String getSurname(String surname){
        return surname;
    }
}
