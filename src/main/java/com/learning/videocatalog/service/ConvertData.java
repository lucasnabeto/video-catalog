package com.learning.videocatalog.service;

public interface ConvertData {
   public <T> T getData(String json, Class<T> tClass);
}
