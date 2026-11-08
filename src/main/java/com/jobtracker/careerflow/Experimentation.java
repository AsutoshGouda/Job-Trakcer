package com.jobtracker.careerflow;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Experimentation {

    public static void main(String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "CareerFlow";
        String hashedPassword = encoder.encode(password);
        System.out.println(hashedPassword);
        String storedHash = "$2a$10$TI.hBJTJv6tIMHWPSBk6We78hti7A3QGAH0iLr/V9fG1Dt6opNLpq";
        System.out.println(
                encoder.matches(password, storedHash)
        );
    }

}
