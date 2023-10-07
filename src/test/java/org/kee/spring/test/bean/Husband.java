package org.kee.spring.test.bean;

import org.kee.spring.beans.factory.annotation.Autowired;
import org.kee.spring.context.annotation.Component;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/7 22:52
 */
@Component
public class Husband {

    @Autowired
    private Wife wife;

    public String queryWife() {
        return "Husband.wife";
    }

    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }
}
