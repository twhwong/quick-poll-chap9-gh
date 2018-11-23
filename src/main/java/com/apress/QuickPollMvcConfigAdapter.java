package com.apress;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 26-10-18
 *
 * @author Tom
 */

@Configuration
public class QuickPollMvcConfigAdapter implements WebMvcConfigurer {

    static final Pageable DEFAULT_PAGE_REQUEST = PageRequest.of(0, 5);

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver();
        // set the default page size to 5 (instead of 20)
        pageableHandlerMethodArgumentResolver.setFallbackPageable(DEFAULT_PAGE_REQUEST);
        resolvers.add(pageableHandlerMethodArgumentResolver);

    }
}
