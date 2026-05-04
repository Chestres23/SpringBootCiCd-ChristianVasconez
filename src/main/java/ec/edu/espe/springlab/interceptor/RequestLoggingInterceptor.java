package ec.edu.espe.springlab.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    private static final AtomicLong REQUEST_COUNT = new AtomicLong(0);

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
            throws Exception {
        long requestNumber = REQUEST_COUNT.incrementAndGet();
        req.setAttribute("t0", System.currentTimeMillis());
        req.setAttribute("requestNumber", requestNumber);
        resp.setHeader("X-Request-Count", String.valueOf(requestNumber));
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) {
        Long t0 = (Long) req.getAttribute("t0");
        Long requestNumber = (Long) req.getAttribute("requestNumber");
        long elapsed = (t0 == null) ? -1 : (System.currentTimeMillis() - t0);
        long currentRequest = requestNumber == null ? -1 : requestNumber;
        System.out.println("Request #" + currentRequest + " -> " + req.getMethod() + " " + req.getRequestURI() + " " + elapsed + "ms");
    }
}
