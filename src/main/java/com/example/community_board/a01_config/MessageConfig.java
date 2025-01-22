package com.example.community_board.a01_config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MessageConfig implements WebMvcConfigurer  {
  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource src = new ReloadableResourceBundleMessageSource();
    src.setBasename("classpath:/message/msg");
    src.setDefaultEncoding("UTF-8");
    src.setCacheSeconds(60);
    src.setUseCodeAsDefaultMessage(true);
    return src;
  }

   /*
    1. 애플리케이션에서 사용할 메세지 소스의 설정을 처리하는 위치를 지정하는 기능 메서드
    2. 다국어 지원을 위한 번역 메세지를 읽어오는 역할을 한다.
    3. ReloadableResourceBundleMessageSource
      1) 프로퍼티 파일에서 번역 메세지를 읽어온다.
      2) msg 라는 기본 이름의 메세지 파일을 지정한다.(classpath:/message/msg)
      3) 예를 들어, 다국어 메세지를 msg_ko.properties, msg_en.properties 와 같은 파일에
      	저장할 수 있다.
    4. 주요 설정..
       setBeanname("classpath:/message/msg"); 메세지 파일의 경로와 기본 이름을 설정
       setDefaultEncoding("utf-8"); 메시지 파일의 인코딩 방식을 utf-8로 설정
    */

  @Bean
  public SessionLocaleResolver localeResolver() {

    return new SessionLocaleResolver();
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    interceptor.setParamName("lang");
    return interceptor;
  }
	/*
	1. 요청 매개변수를 통해 로케일(언어)를 변경 할 수 있도록 처리한다.
	2. 예를 들어 ,?lang=ko 와 같은 쿼리 매개변수를 사용하여 언어를 변경할 수 있다.
	3. LocaleChangeInterceptor
	   언어 변경 요청을 가로채서 SessionLocaleResolver 에 새로운 로케일(언어/위치) 정보를 설정
	   setParamName("lang")을 통해 언어 변경에 사용할 파라미터 이름을 lang 으로 지정한다.
	   ex) ?lang=ko 의 경우 ko(한국)으로 지정이 된다..
	 */

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }
    /*
     1. 애플리케이션에서 사용할 인터셉터(가로채기를 해서 어떤 언어로 처리할지 지정)을 등록한다.
     2. 위에서 정의한 localeChangeInterCeptor 를 Spring MVC 인터셉터로 추가하여 모든 요청을 가로 챈다.
        - 다시 말해, 코드를 해당 언어로 변환하여 처리한다는 말이다.
     3. registry.addInterceptor(localeChangeInterceptor()); 로케인 변경 인터셉터를 요청
     	체인에 추가한다. - 모든 요청에 대해 lang 파라미터를 확인하고, 요청에 지정된 언어로 로케일을
     	변경한다.
     */
}
