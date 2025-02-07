package com.klinik.rest.login;

import com.klinik.request.UserRequest;
import com.klinik.response.AuthResponse;
import com.klinik.response.BaseResponseError;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ApiResponses(value = {
        @ApiResponse( responseCode = "200" , description = "Authentication success", content = { @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = AuthResponse.class ))) }),
        @ApiResponse( responseCode = "400", description = "Bad Request",             content = { @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) }),
        @ApiResponse( responseCode = "500", description = "System malfunction",      content = { @Content(mediaType = MediaType.APPLICATION_JSON, array = @ArraySchema(schema = @Schema( implementation = BaseResponseError.class ))) })
})
public interface IAuthentication {

    @GetMapping(value = "code", produces = MediaType.APPLICATION_JSON)
    public String code(HttpServletRequest request);

    @GetMapping(value = "login", produces = MediaType.APPLICATION_JSON)
    public String login();

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes redirectAttributes);

    @GetMapping(value = "index", produces = MediaType.APPLICATION_JSON)
    public String index();

    @GetMapping(value = "change-password", produces = MediaType.APPLICATION_JSON)
    public String changePassword();

    @GetMapping(value = "register", produces = MediaType.APPLICATION_JSON)
    public String register();

    @GetMapping(value = "image-access-qr")
    public void getQrImage( @RequestParam("login") String login, HttpServletRequest request, HttpServletResponse response );

    @PostMapping(value = "register")
    public String registerGetQr( @ModelAttribute UserRequest userRequest, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response );

    @PostMapping(value = "change-password")
    public String requestPasswordChange( @RequestParam("user") String user, HttpServletRequest request, RedirectAttributes redirectAttributes );
    
    @PostMapping(value = "clear-error-message", produces = MediaType.APPLICATION_JSON)
    public String clearErrorMessage(HttpServletRequest request);
}
