package com.example.baemin.common.argument_resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class JwtAuthHandlerArgumentResolver implements HandlerMethodArgumentResolver {

    // ArgumentResolver가 해당 파라미터를 지원하는지 먼저 체크
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("[JwtAuthHandlerArgumentResolver.supportsParameter]");
        boolean hasAnnotation = parameter.hasParameterAnnotation(PreAuthorize.class);
        boolean hasType = long.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasType;
    }

    // 지원되는 파라미터라면 컨트롤러가 호출되기 직전에 해당 메서드 호출
    // -> 필요한 파라미터 정보를 생성해서 넣어준다.
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        log.info("[JwtAuthHandlerArgumentResolver.resolveArgument]");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        return request.getAttribute("userId");
    }

}