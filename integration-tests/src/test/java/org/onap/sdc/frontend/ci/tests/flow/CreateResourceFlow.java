/*
 * ============LICENSE_START=======================================================
 *  Copyright (C) 2019 Nordix Foundation
 *  ================================================================================
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  SPDX-License-Identifier: Apache-2.0
 *  ============LICENSE_END=========================================================
 */

package org.onap.sdc.frontend.ci.tests.flow;

import com.aventstack.extentreports.Status;
import java.util.Optional;
import org.onap.sdc.frontend.ci.tests.datatypes.ResourceCreateData;
import org.onap.sdc.frontend.ci.tests.execute.setup.ExtentTestActions;
import org.onap.sdc.frontend.ci.tests.pages.PageObject;
import org.onap.sdc.frontend.ci.tests.pages.ResourceCreatePage;
import org.onap.sdc.frontend.ci.tests.pages.home.HomePage;
import org.openqa.selenium.WebDriver;

/**
 * UI Flow for Resource creation
 */
public class CreateResourceFlow extends AbstractUiTestFlow {

    private final ResourceCreateData resourceCreateData;
    private ResourceCreatePage resourceCreatePage;

    public CreateResourceFlow(final WebDriver webDriver, final ResourceCreateData resourceCreateData) {
        super(webDriver);
        this.resourceCreateData = resourceCreateData;
    }

    @Override
    public Optional<ResourceCreatePage> run(final PageObject... pageObjects) {
        extendTest.log(Status.INFO, String.format("Creating the Resource '%s' from home page", resourceCreateData.getName()));
        final HomePage homePage = findParameter(pageObjects, HomePage.class);
        homePage.isLoaded();
        resourceCreatePage = homePage.clickOnAddVf();
        resourceCreatePage.fillForm(resourceCreateData);
        resourceCreatePage.clickOnCreate();
        ExtentTestActions.takeScreenshot(Status.INFO, "resource-created",
            String.format("Resource '%s' was created", resourceCreateData.getName()));
        return Optional.ofNullable(this.resourceCreatePage);
    }

    @Override
    public Optional<ResourceCreatePage> getLandedPage() {
        return Optional.ofNullable(resourceCreatePage);
    }

}
