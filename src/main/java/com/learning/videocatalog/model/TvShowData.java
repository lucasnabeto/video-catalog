package com.learning.videocatalog.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TvShowData(@JsonAlias("Title") String title,
                         @JsonAlias("totalSeasons") Integer totalSeasons,
                         @JsonAlias("imdbRating") String rating) {

    /* JsonAlias VS JsonProperty
     @JsonAlias e @JsonProperty são anotações em Jackson, uma biblioteca Java para processar JSON, que ajudam a mapear
     propriedades de classe para campos JSON.

     JsonAlias é usado apenas para mapear múltiplos nomes de propriedades JSON para um único campo Java, enquanto JsonProperty
     é usado para mapear um único nome de propriedade JSON para um campo Java e vice-versa.

     Exemplo:
        @JsonAlias({"Title", "title", "movieTitle"}) String titleWithAlias;
        @JsonProperty("Title") String titleWithProperty;

     No caso acima, JsonAlias permite que o campo title seja populado a partir de qualquer um dos três nomes fornecidos
     no JSON. JsonProperty, por outro lado, só permitirá que o campo title seja populado se o JSON tiver exatamente a
     propriedade "Title".
    */
}
