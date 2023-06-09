package club.p6e.coat.gateway.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 密码编码器对象
 *
 * @author lidashuang
 * @version 1.0
 */
@Component
@ConditionalOnMissingBean(
        value = PasswordEncoder.class,
        ignored = AuthPasswordEncoder.class
)
public class AuthPasswordEncoder implements PasswordEncoder {

    private static final int M = 16;
    private static final String RS = "bng#fhny2f26yba7p66dz2qzdu@vsyb3";

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        } else {
            final String content = DigestUtils.md5DigestAsHex(
                    (RS + rawPassword).getBytes(StandardCharsets.UTF_8));
            final int mData = ((int) content.charAt(content.length() - 1)) % M;
            return DigestUtils.md5DigestAsHex(content.substring(0, mData).getBytes(StandardCharsets.UTF_8)) + content.substring(mData);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        if (encodedPassword == null) {
            throw new IllegalArgumentException("encodedPassword cannot be null");
        }
        return encodedPassword.length() != 0 && encode(rawPassword).equals(encodedPassword);
    }

    public static void main(String[] args) {
        System.out.println(
                new AuthPasswordEncoder().encode("123456")
        );;
    }

}
