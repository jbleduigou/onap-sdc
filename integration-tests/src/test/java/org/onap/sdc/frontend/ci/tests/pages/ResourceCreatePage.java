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

package org.onap.sdc.frontend.ci.tests.pages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.core.Is.is;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.onap.sdc.frontend.ci.tests.datatypes.LifeCycleStateEnum;
import org.onap.sdc.frontend.ci.tests.datatypes.ResourceCreateData;
import org.onap.sdc.frontend.ci.tests.utilities.LoaderHelper;
import org.onap.sdc.frontend.ci.tests.utilities.NotificationComponent;
import org.onap.sdc.frontend.ci.tests.utilities.NotificationComponent.NotificationType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Handles the Resource Create Page UI actions
 */
public class ResourceCreatePage extends AbstractPageObject {

    private final LoaderHelper loaderHelper;
    private final NotificationComponent notificationComponent;
    private final ResourceWorkspaceTopBarComponent topBarComponent;

    public ResourceCreatePage(final WebDriver webDriver) {
        super(webDriver);
        loaderHelper = new LoaderHelper(webDriver);
        notificationComponent = new NotificationComponent(webDriver);
        topBarComponent = new ResourceWorkspaceTopBarComponent(webDriver);
        timeoutInSeconds = 5;
    }

    @Override
    public void isLoaded() {
        topBarComponent.isLoaded();
        final String lifeCycleState = topBarComponent.getLifecycleState();
        assertThat("Life cycle state should be as expected",
            lifeCycleState, is(equalToIgnoringCase(LifeCycleStateEnum.IN_DESIGN.getValue())));
    }

    /**
     * Creates the resource and wait for success notification.
     */
    public void clickOnCreate() {
        topBarComponent.clickOnCreate();
        loaderHelper.waitForLoader(20);
        notificationComponent.waitForNotification(NotificationType.SUCCESS, 20);
    }

    public void fillForm(final ResourceCreateData resourceCreateData) {
        fillName(resourceCreateData.getName());
        setCategory(resourceCreateData.getCategory());
        fillDescription(resourceCreateData.getDescription());
        fillContactId(resourceCreateData.getContactId());
        fillVendorName(resourceCreateData.getVendorName());
        fillVendorRelease(resourceCreateData.getVendorRelease());
        fillVendorModelNumber(resourceCreateData.getVendorModelNumber());
    }

    public void fillName(final String name) {
        setInputField(By.xpath(XpathSelector.NAME_INPUT.getXpath()), name);
    }

    public void setCategory(final String category) {
        setSelectField(By.xpath(XpathSelector.CATEGORY_SELECT.getXpath()), category);
    }

    public void fillDescription(final String description) {
        setTextAreaField(By.xpath(XpathSelector.DESCRIPTION_TEXT_AREA.getXpath()), description);
    }

    public void fillContactId(final String contactId) {
        setInputField(By.xpath(XpathSelector.CONTACT_ID_INPUT.getXpath()), contactId);
    }

    public void fillVendorName(final String vendorName) {
        setInputField(By.xpath(XpathSelector.VENDOR_NAME_INPUT.getXpath()), vendorName);
    }

    public void fillVendorRelease(final String vendorRelease) {
        setInputField(By.xpath(XpathSelector.VENDOR_RELEASE_INPUT.getXpath()), vendorRelease);
    }

    public void fillVendorModelNumber(final String vendorModelNumber) {
        setInputField(By.xpath(XpathSelector.VENDOR_MODEL_NUMBER_INPUT.getXpath()), vendorModelNumber);
    }

    private void setSelectField(final By locator, final String value) {
        if (value == null) {
            return;
        }
        new Select(findElement(locator)).selectByVisibleText(value);
    }

    private void setInputField(final By locator, final String value) {
        if (value == null) {
            return;
        }
        findElement(locator).sendKeys(value);
    }

    private void setTextAreaField(final By locator, final String value) {
        setInputField(locator, value);
    }

    /**
     * Enum that contains identifiers and xpath expressions to elements related to the enclosing page object.
     */
    @AllArgsConstructor
    private enum XpathSelector {
        NAME_INPUT("name", "//input[@data-tests-id='%s']"),
        CATEGORY_SELECT("selectGeneralCategory", "//select[@data-tests-id='%s']"),
        DESCRIPTION_TEXT_AREA("description", "//textarea[@data-tests-id='%s']"),
        CONTACT_ID_INPUT("contactId", "//input[@data-tests-id='%s']"),
        VENDOR_NAME_INPUT("vendorName", "//input[@data-tests-id='%s']"),
        VENDOR_RELEASE_INPUT("vendorRelease", "//input[@data-tests-id='%s']"),
        VENDOR_MODEL_NUMBER_INPUT("resourceVendorModelNumber", "//input[@data-tests-id='%s']");

        @Getter
        private final String id;
        private final String xpathFormat;

        public String getXpath() {
            return String.format(xpathFormat, id);
        }
    }

}
