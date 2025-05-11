package jar.controller;

import jar.dto.SignUpRequest;
import jar.dto.LoginRequest;
import jar.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Allow frontend requests from React dev server
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest request) {
        System.out.println("Received signup request for user: " + request.getUsername());
        try {
        	String response = authService.signUp(request.getUsername(), request.getEmail(), request.getPassword());
        	return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error during signup: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Received login request for user: " + loginRequest.getUsername());
        try {
            String jwtToken = (String) authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(jwtToken); // Return JWT token to the frontend
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error during login: " + e.getMessage());
        }
    }
}
