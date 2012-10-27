/*
 * Copyright 2012 Erlend Hamnaberg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.hamnaberg.json.extension;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

public abstract class Extended<T> {
    protected final ObjectNode delegate;

    public Extended(ObjectNode delegate) {
        this.delegate = delegate;
    }

    protected abstract T copy(ObjectNode value);

    public <A> A getExtension(Extension<A> extension) {
        return extension.extract(delegate);
    }

    public <A> T apply(A value, Extension<A> extension) {
        ObjectNode copied = copyDelegate();
        copied.putAll(extension.apply(value));
        return copy(copied);
    }

    private ObjectNode copyDelegate() {
        ObjectNode copied = JsonNodeFactory.instance.objectNode();
        copied.putAll(delegate);
        return copied;
    }

    public ObjectNode asJson() {
        return copyDelegate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Extended extended = (Extended) o;

        if (delegate != null ? !delegate.equals(extended.delegate) : extended.delegate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return delegate != null ? delegate.hashCode() : 0;
    }
}
