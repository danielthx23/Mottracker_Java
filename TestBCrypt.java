import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String password = "123456";
        String hash = "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi";
        
        System.out.println("Password: " + password);
        System.out.println("Hash: " + hash);
        System.out.println("Matches: " + encoder.matches(password, hash));
        
        // Testar com hash correto para "123456"
        String correctHash = encoder.encode(password);
        System.out.println("Correct hash for '123456': " + correctHash);
        System.out.println("Correct hash matches: " + encoder.matches(password, correctHash));
    }
}
