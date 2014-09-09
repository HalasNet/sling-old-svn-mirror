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
package org.apache.sling.testing.resourceresolver;

import java.util.Map;

import org.apache.sling.api.resource.AbstractResource;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ModifiableValueMapDecorator;
import org.apache.sling.api.wrappers.ValueMapDecorator;

public class MockResource extends AbstractResource {

    private final String path;

    private final ValueMap props;

    private final ResourceMetadata rm = new ResourceMetadata();

    private final ResourceResolver resolver;

    static final String JCR_PRIMARYTYPE = "jcr:primaryType";
    
    public MockResource(final String path,
            final Map<String, Object> props,
            final ResourceResolver resolver) {
        this.path = path;
        this.props = (props instanceof MockValueMap) ? (MockValueMap)props : new MockValueMap(props);
        this.resolver = resolver;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String getResourceType() {
        String resourceType = this.props.get(ResourceResolver.PROPERTY_RESOURCE_TYPE, String.class);
        if (resourceType == null) {
            // fallback to jcr:primaryType if not resouce type exists (to mimick JCR resource behavior)
            resourceType = this.props.get(JCR_PRIMARYTYPE, String.class);
        }
        return resourceType;
    }

    @Override
    public String getResourceSuperType() {
        return this.props.get("sling:resourceSuperType", String.class);
    }

    @Override
    public ResourceMetadata getResourceMetadata() {
        return rm;
    }

    @Override
    public ResourceResolver getResourceResolver() {
        return this.resolver;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <AdapterType> AdapterType adaptTo(final Class<AdapterType> type) {
        if ( type == ValueMap.class ) {
            return (AdapterType)new ValueMapDecorator(this.props);
        } else if ( type == ModifiableValueMap.class ) {
            ((MockResourceResolver)this.resolver).addChanged(this.path, this.props);
            return (AdapterType)new ModifiableValueMapDecorator(this.props);
        }
        return super.adaptTo(type);
    }

    @Override
    public ValueMap getValueMap() {
        return this.props;
    }

    @Override
    public String toString() {
        return "MockResource [path=" + path + ", props=" + props + "]";
    }

}
