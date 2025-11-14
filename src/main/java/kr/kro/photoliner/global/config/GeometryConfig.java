package kr.kro.photoliner.global.config;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeometryConfig {

  @Bean
  public GeometryFactory geometryFactory() {
    return new GeometryFactory(new PrecisionModel(), 4326); // 4326 : 글로벌 표준 지리좌표계 코드
  }
}
