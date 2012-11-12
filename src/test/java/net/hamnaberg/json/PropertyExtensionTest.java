package net.hamnaberg.json;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import net.hamnaberg.json.extension.Extension;
import net.hamnaberg.json.extension.Tuple2;
import net.hamnaberg.json.generator.CollectionGenerator;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PropertyExtensionTest {

  protected final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

  @Test
  public void should_add_required_extension() throws URISyntaxException, IOException {

    Property prop = Property.value("prop", Optional.of("test Prop"), Optional.<Value>absent())
            .apply(true, requiredExtenstion());

    Template template = new Template(Lists.newArrayList(prop));
    Collection collection = DefaultCollection.builder(new URI("test")).withTemplate(template).build();
    JsonNode node = new CollectionGenerator().toNode(collection);
    JsonNode property = node.get("collection").get("template").get("data").get(0);

    assertThat(property.get("required").asBoolean(), is(true));

    //ObjectMapper mapper = new ObjectMapper();
    //mapper.writeValue(System.out, node);
  }
  
  
  private Extension<Boolean> requiredExtenstion() {
    return new Extension<Boolean>() {
      @Override
      public Boolean extract(ObjectNode node) {
        return node.get("required").getBooleanValue();
      }
      
      @Override
      public Map<String, JsonNode> apply(Boolean value) {
        JsonNode node = nodeFactory.booleanNode(value);
        Map<String, JsonNode> map = new HashMap<String, JsonNode>();
        map.put("required", node);
        return map;
      }
    };
  }
}
