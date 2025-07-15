package org.demo.common;

import java.util.function.Function;

public enum AppEnum {

  ENV("app.env", "prod", String::toLowerCase);

  private final String name;
  private final String defaultValue;
  private final Function<String, String> format;

  AppEnum(String name, String defaultValue, Function<String,String> format) {
    this.name = name;
    this.defaultValue = defaultValue;
    this.format = format;
  }

  public String getName() {
    return name;
  }

  public String getValue(){
    return format!=null ? format.apply(System.getProperty(this.getName(), defaultValue)) : System.getProperty(this.getName(), defaultValue);
  }



}
