package ru.kolumarket.apigatewayservice;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

@Component
public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println(RequestContext.getCurrentContext().getRequest());
        RequestContext.getCurrentContext().addZuulResponseHeader("hi", "hi");
        return RequestContext.getCurrentContext();
    }
}
