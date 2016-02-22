# JsonHelper
This is a helper class for Javas GSON based JSON class.

## Introduction
Dealing with JSON on Java is often a very complicated task. The google project GSON comes in very handy but needs a lot of additional validation checking when dealing with remote content.

For example:

```JSON
{
  "dogs": [
    {
      "name": "Rex",
      "breed": "German shepherd"
    },
    {
      "name": "Fifi",
      "breed": "Boxer"
    }
  ]
}
```

Lets print all dog names with their breeds:

```Java
try {
  JsonElement json = new JsonParser().parse(jsonContent);
  
  if (json.isJsonObject() && json.getAsJsonObject().has("dogs") && json.getAsJsonObject().get("dogs").isJsonArray()) {
    JsonArray dogs = json.getAsJsonObject().getAsJsonArray("dogs");
    for (final JsonElement dog : dogs) {
      String name;
      String breed;
      
      if (dog.isJsonObject() && dog.getAsJsonObject().has("name") && dog.getAsJsonObject().get("name").isJsonPrimitive()  && dog.getAsJsonObject().getAsJsonPrimitive("name").isString()) {
        name = dog.getAsJsonObject().getAsJsonPrimitive("name").getAsString();
      }
      
      if (dog.isJsonObject() && dog.getAsJsonObject().has("breed") && dog.getAsJsonObject().get("breed").isJsonPrimitive()  && dog.getAsJsonObject().getAsJsonPrimitive("breed").isString()) {
        breed = dog.getAsJsonObject().getAsJsonPrimitive("breed").getAsString();
      }
      
      if (name != null && breed != null) {
        System.out.println("Name: " + name + " Breed: " + breed);
      }
    }
  }
}
catch (Exception e) {
  // handle exeptions...
}
```

As you can can see this is very complicated and not very readable. With my JsonHelper class the same code can be executed in a way more elegant way:

```Java
JsonHelper json = new JsonHelper(jsonContent);

for (int i = 0; i < json.get("dogs").size(); i++) {
  String name = json.get("dogs").at(i).get("name").stringValue();
  String breed = json.get("dogs").at(i).get("breed").stringValue();
  
  if (name != null && breed != null) {
    System.out.println("Name: " + name + " Breed: " + breed);
  }
}
```
