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

import com.google.common.collect.ImmutableMap;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import java.util.*;

public abstract class Extension<A> {
    public abstract A extract(ObjectNode node);
    public abstract Map<String, JsonNode> apply(A value);

    public static <A, B> Extension<Tuple2<A, B>> combine(Extension<A> a, Extension<B> b) {
        return new Tuple2Extension<A, B>(Tuple2.of(a, b));
    }

    public static <A, B, C> Extension<Tuple3<A, B, C>> combine3(Extension<A> a, Extension<B> b, Extension<C> c) {
        return new Tuple3Extension<A, B, C>(Tuple3.of(a, b, c));
    }

    private static class Tuple2Extension<A, B> extends Extension<Tuple2<A, B>> {
        private Tuple2<Extension<A>, Extension<B>> delegate;

        private Tuple2Extension(Tuple2<Extension<A>, Extension<B>> delegate) {
            this.delegate = delegate;
        }

        @Override
        public Tuple2<A, B> extract(ObjectNode node) {
            return new Tuple2<A, B>(delegate._1.extract(node), delegate._2.extract(node));
        }

        @Override
        public Map<String, JsonNode> apply(Tuple2<A, B> value) {
            ImmutableMap.Builder<String, JsonNode> builder = ImmutableMap.builder();
            builder.putAll(delegate._1.apply(value._1));
            builder.putAll(delegate._2.apply(value._2));
            return builder.build();
        }
    }

    private static class Tuple3Extension<A, B, C> extends Extension<Tuple3<A, B, C>> {
        private Tuple3<Extension<A>, Extension<B>, Extension<C>> delegate;

        private Tuple3Extension(Tuple3<Extension<A>, Extension<B>, Extension<C>> delegate) {
            this.delegate = delegate;
        }

        @Override
        public Tuple3<A, B, C> extract(ObjectNode node) {
            return Tuple3.of(delegate._1.extract(node), delegate._2.extract(node), delegate._3.extract(node));
        }

        @Override
        public Map<String, JsonNode> apply(Tuple3<A, B, C> value) {
            ImmutableMap.Builder<String, JsonNode> builder = ImmutableMap.builder();
            builder.putAll(delegate._1.apply(value._1));
            builder.putAll(delegate._2.apply(value._2));
            builder.putAll(delegate._3.apply(value._3));
            return builder.build();
        }
    }
}
