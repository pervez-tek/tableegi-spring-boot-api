package com.tpty.tableegi.jamath.components;

import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.entity.AdminUsersLoginTrackingEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.AdminLoginService;
import com.tpty.tableegi.jamath.service.AdminLoginTrackingService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class SingleSessionFilter extends OncePerRequestFilter {

    @Autowired
    private ActiveUsersStore activeUsersStore;

    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private AdminLoginTrackingService adminLoginTrackingService;

    @Value("${front.end.react.api.url}")
    private String FRONT_END_REACT_API_URL;

    @Value("${spring.auto.expire.session.id.token.valid.mins}")
    private String SPRING_AUTO_EXPIRE_JOB_SESSION_TOKEN_VALID_MINS;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("Anonymous request to " + request.getRequestURI());

        List<String> urls = List.of("/api/tableegi/addMasjid", "/api/tableegi/updateMasjid",
                "/api/tableegi/deleteMasjid", "/api/tableegi/getAllMasjids",
                "/api/tableegi/betweenJamathDates", "/api/tableegi/broadCastMessage");

        if (urls.contains(request.getRequestURI())) {

            String sessioid = request.getHeader("sessionid");
            String id = request.getHeader("id"); // or from cookie/localStorage
            String username = request.getHeader("username"); // or from cookie/localStorage
            String usrAdminId = request.getHeader("usrAdminId"); // or from cookie/localStorage

            if (sessioid == null || id == null || username == null || usrAdminId == null) {
                filterChain.doFilter(request, response);
            } else {
                UUID sessionId = UUID.fromString(sessioid); // or from cookie/localStorage
                AdminLoginRequestDto adminDto = AdminLoginRequestDto.builder()
                        .usrAdminId(usrAdminId)
                        .id(id)
                        .sessionid(sessionId)
                        .username(adminLoginService.findById(UUID.fromString(usrAdminId)).getUsername())
                        .build();

                log.info("OncePerRequestFilter" + adminDto);
                if (username.equalsIgnoreCase(adminDto.getUsername()))
                    try {

                        LocalDateTime expiry = LocalDateTime.now().minusMinutes(Integer.parseInt(SPRING_AUTO_EXPIRE_JOB_SESSION_TOKEN_VALID_MINS));
                        AdminUsersLoginTrackingEntity expiredAdmin = adminLoginTrackingService.
                                findExpiredAdmin(expiry, UUID.fromString(adminDto.getId()),
                                        adminDto.getSessionid(),
                                        UUID.fromString(adminDto.getUsrAdminId()));

                        if (expiredAdmin == null) {
                            filterChain.doFilter(request, response);
                        } else {
                            AdminUsersLoginTrackingEntity adminLoginEntity = adminLoginTrackingService.signOut(adminDto);
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"SESSION_EXPIRED: Auto Log off\"}");
                            //response.sendRedirect(FRONT_END_REACT_API_URL);

                        }

                    } catch (InvalidDataException e) {
                        throw new RuntimeException(e);
                    }
            }
        } else {
            filterChain.doFilter(request, response);
        }

//        // Get authentication from Spring Security context
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            username = authentication.getName(); // usually the principal's usernam
//            System.out.println("Request by user: " + username + " to " + request.getRequestURI());
//        }
    }
}
