/*
*Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.carbon.esb.mediator.test.enrich;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpResponse;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.esb.integration.common.utils.ESBIntegrationTest;
import org.wso2.esb.integration.common.utils.clients.SimpleHttpClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class EnrichJSONTest extends ESBIntegrationTest {

    private SimpleHttpClient httpClient;
    private String apiName = "EnrichJSON";

    @BeforeClass(alwaysRun = true)
    public void setEnvironment() throws Exception {
        super.init();
        httpClient = new SimpleHttpClient();
        verifyAPIExistence(apiName);

    }

    @Test(groups = {"wso2.esb"}, description = "Enrichment of a JSON message")
    public void enrichMediatorTest() throws IOException {
        String contentType = "application/json";//Content-Type
        Map<String, String> headers = new HashMap<String, String>();//For HTTP Headers
        headers.put("Content-Type", contentType);
        HttpResponse response = httpClient.doPost(getApiInvocationURL(apiName), headers,
                                                  "{\"token\": " +
                                                          "\"d8ccf265-6651-468f-8d1f-d935c3c7d857\", \"partyId\": " +
                                                          "\"2920394\"}",
                                                  contentType);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        response.getEntity().writeTo(outputStream);

        String payload = new String(outputStream.toByteArray());

        assertEquals(payload, "{ \"a\" : {\"token\":\"d8ccf265-6651-468f-8d1f-d935c3c7d857\",\"partyId\":2920394," +
                "\"Value\":\"PartyId\"} , \"b\":\"thampi\"}");
    }

    @AfterClass(alwaysRun = true)
    public void closeTestArtifacts() throws Exception {
        super.cleanup();
    }


}
