package com.example.backend.auth.controller;

import com.example.common.dto.AuthDTO;
import com.example.backend.auth.service.AuthService;
import com.example.common.dto.JwtResponseDTO;
import com.example.common.dto.UserDTO;
import com.example.common.util.JwtUtils;
import com.example.security.auth.CustomUserDetails;
import com.example.security.oauth.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtils jwtUtils;
    private final AuthService authService;

    @GetMapping("")
    public ResponseEntity<?> login() {
        return ResponseEntity.ok("Hello");
    }

    @GetMapping("/signup")
    public ResponseEntity<?> signup() {
        return ResponseEntity.ok("signup");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthDTO reqDTO) {
        log.info("AuthController /signup");
        log.info("original reqDTO : {}", reqDTO.toString());

        String provider = reqDTO.getProvider();
        String password = reqDTO.getPassword();
        String roles = reqDTO.getRoles();

        // roles는 null 또는 empty String여야 함
        boolean isRolesValid = (roles == null || roles.isEmpty());
        // provider가 null 또는 empty String -> password가 제공되어야 함, 아닌 경우 -> password가 제공되면 안됨
        boolean isProviderEmpty = (provider == null || provider.isEmpty());
        boolean isPasswordEmpty = (password == null || password.isEmpty());
        boolean isPasswordValid = (isProviderEmpty && !isPasswordEmpty) || (!isProviderEmpty && isPasswordEmpty);

        if (isRolesValid && isPasswordValid) {
            reqDTO.setRoles("USER");
            if (isProviderEmpty) {
                reqDTO.setProvider("infinite");
            }
            log.info("modified reqDTO : {}", reqDTO.toString());

            int result = this.authService.register(reqDTO);
            if (result > 0) {
                return ResponseEntity.ok(reqDTO);
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/signout")
    public ResponseEntity<?> signOut() {
        return ResponseEntity.ok().body("fuck");
    }

    @PostMapping("/issue")
    public ResponseEntity<?> issue(@AuthenticationPrincipal Object principal) {

        log.info("principal toString : {}", principal.toString());
        log.info("principal getClass : {}", principal.getClass());
        boolean isCustomUserDetails = principal instanceof CustomUserDetails;
        if (!isCustomUserDetails) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid principal, UNAUTHORIZED");
        } else {
            CustomUserDetails customUserDetails = (CustomUserDetails) principal;
            String email = customUserDetails.getUsername();
            String provider = customUserDetails.getProvider();
            String roles = createRoles(customUserDetails.getAuthorities());

            long cur = System.currentTimeMillis();
            String accessToken = jwtUtils.createAccessToken(email, roles, provider, cur);
            String refreshToken = jwtUtils.createRefreshToken(cur);
            Date accessTokenExpiresAt = jwtUtils.getAccessTokenExpiresAt(accessToken);
            Date refreshTokenExpiresAt = jwtUtils.getRefreshTokenExpiresAt(refreshToken);

            JwtResponseDTO jwtResponseDTO = new JwtResponseDTO(accessToken, refreshToken, accessTokenExpiresAt, refreshTokenExpiresAt);

            return ResponseEntity.ok(jwtResponseDTO);
        }
    }

    public String createRoles(Collection<? extends GrantedAuthority> authorities) {
        StringBuilder sb = new StringBuilder();

        for (GrantedAuthority grantedAuthority : authorities) {
            SimpleGrantedAuthority authority = (SimpleGrantedAuthority) grantedAuthority;
            String role = authority.getAuthority();
            if (role.startsWith("ROLE_")) {
                sb.append(role.substring(5)).append(",");
            } else {
                sb.append(role).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
