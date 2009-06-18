/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sling.launchpad.webapp.integrationtest.servlets.resolution;

/** Test the ExtensionServlet */
public class ExtensionServletTest extends ResolutionTestBase {
  
  public void testExtensionOne() throws Exception {
    assertServlet(
        getContent(testNodeNORT.nodeUrl + ".TEST_EXT_1", CONTENT_TYPE_PLAIN),
        EXT_SERVLET_SUFFIX);
  }
  
  public void testExtensionTwo() throws Exception {
    assertServlet(
        getContent(testNodeNORT.nodeUrl + ".TEST_EXT_2", CONTENT_TYPE_PLAIN), 
        EXT_SERVLET_SUFFIX);
  }
  
  public void testExtensionOther() throws Exception {
    assertNotTestServlet(
        getContent(testNodeNORT.nodeUrl + ".txt", CONTENT_TYPE_PLAIN));
  }
}
