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

package net.hamnaberg.json;

public final class Render {
    public static final Render Link = new Render("link");
    public static final Render Image = new Render("image");

    private final String name;

    private Render(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public static Render valueOf(String name) {
        if (Link.getName().equalsIgnoreCase(name)) {
            return Link;
        }
        else if (Image.getName().equalsIgnoreCase(name)) {
            return Image;
        }
        else {
            return new Render(name);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Render render = (Render) o;

        if (name != null ? !name.equals(render.name) : render.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
