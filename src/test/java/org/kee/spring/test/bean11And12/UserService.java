package org.kee.spring.test.bean11And12;

import org.kee.spring.beans.factory.annotation.Value;
import org.kee.spring.context.annotation.Component;

import java.util.Random;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/9/10 22:52
 */
@Component
public class UserService implements IUserService {

    @Value("${token}")
    private String token;

    @Override
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "羽子少年，JAVA，32，本9";
    }

    @Override
    public String register(String username) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Yeah！成功注册用户：" + username;
    }


    @Override
    public String toString() {
        return "UserService.token = " + token;
    }
}
