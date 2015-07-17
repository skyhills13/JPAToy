package io.lilo.web;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import io.lilo.config.WebConfig;

public class MvcTestUtil {

    static WebConfig webConfig = new WebConfig();

    public static MockMvc getMockMvc(Object controller) {
        return getStandAloneMockMvcWithConfigurationSetting(controller).build();
    }

    private static StandaloneMockMvcBuilder getStandAloneMockMvcWithConfigurationSetting(Object controller) {
        return MockMvcBuilders.standaloneSetup(controller).setViewResolvers(webConfig.internalResourceViewResolver()).addFilter(new HiddenHttpMethodFilter());
    }
}
