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
package org.apache.freemarker.core;

import org.apache.freemarker.core.util._CollectionUtil;

/**
 * Holds an buffer (array) of {@link _ASTElement}-s with the count of the utilized items in it. The un-utilized tail
 * of the array must only contain {@code null}-s.
 * 
 * @since 2.3.24
 */
class TemplateElements {
    
    static final TemplateElements EMPTY = new TemplateElements(null, 0);

    private final _ASTElement[] buffer;
    private final int count;

    /**
     * @param buffer
     *            The buffer; {@code null} exactly if {@code count} is 0.
     * @param count
     *            The number of utilized buffer elements; if 0, then {@code null} must be {@code null}.
     */
    TemplateElements(_ASTElement[] buffer, int count) {
        /*
        // Assertion:
        if (count == 0 && buffer != null) {
            throw new IllegalArgumentException(); 
        }
        */
        
        this.buffer = buffer;
        this.count = count;
    }

    _ASTElement[] getBuffer() {
        return buffer;
    }

    int getCount() {
        return count;
    }

    _ASTElement getFirst() {
        return buffer != null ? buffer[0] : null;
    }
    
    _ASTElement getLast() {
        return buffer != null ? buffer[count - 1] : null;
    }
    
    /**
     * Used for some backward compatibility hacks.
     */
    _ASTElement asSingleElement() {
        if (count == 0) {
            return new ASTStaticText(_CollectionUtil.EMPTY_CHAR_ARRAY, false);
        } else {
            _ASTElement first = buffer[0];
            if (count == 1) {
                return first;
            } else {
                ASTImplicitParent mixedContent = new ASTImplicitParent();
                mixedContent.setChildren(this);
                mixedContent.setLocation(first.getTemplate(), first, getLast());
                return mixedContent;
            }
        }
    }
    
    /**
     * Used for some backward compatibility hacks.
     */
    ASTImplicitParent asMixedContent() {
        ASTImplicitParent mixedContent = new ASTImplicitParent();
        if (count != 0) {
            _ASTElement first = buffer[0];
            mixedContent.setChildren(this);
            mixedContent.setLocation(first.getTemplate(), first, getLast());
        }
        return mixedContent;
    }

}