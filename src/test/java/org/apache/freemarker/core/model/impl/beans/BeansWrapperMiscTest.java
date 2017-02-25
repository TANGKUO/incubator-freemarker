/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.freemarker.core.model.impl.beans;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.apache.freemarker.core.Configuration;
import org.apache.freemarker.core.model.TemplateBooleanModel;
import org.apache.freemarker.core.model.TemplateHashModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BeansWrapperMiscTest {

    @Test
    public void booleans() throws Exception {
        final BeansWrapper bw = new BeansWrapper(Configuration.VERSION_3_0_0);

        assertTrue(((TemplateBooleanModel) bw.wrap(Boolean.TRUE)).getAsBoolean());
        assertFalse(((TemplateBooleanModel) bw.wrap(Boolean.FALSE)).getAsBoolean());
        
        TemplateHashModel tm = (TemplateHashModel) bw.wrap(Boolean.TRUE);
        assertNotNull(tm.get("hashCode"));
        assertNotNull(tm.get("class"));
        bw.setExposureLevel(BeansWrapper.EXPOSE_PROPERTIES_ONLY);
        assertNull(tm.get("hashCode"));
        assertNotNull(tm.get("class"));
        bw.setExposureLevel(BeansWrapper.EXPOSE_NOTHING);
        assertNull(tm.get("hashCode"));
        assertNull(tm.get("class"));
        bw.setExposureLevel(BeansWrapper.EXPOSE_ALL);
        assertNotNull(tm.get("hashCode"));
        assertNotNull(tm.get("class"));
        
        assertSame(tm, bw.wrap(Boolean.TRUE));
    }
    
}