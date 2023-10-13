package org.kee.spring.test.bean;

import org.kee.spring.beans.factory.annotation.Autowired;
import org.kee.spring.beans.factory.annotation.Value;
import org.kee.spring.context.annotation.Component;

import java.time.LocalDate;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/7 22:52
 */
@Component
public class Husband {

    private String wifeName;

    private LocalDate marriageDate;

    public String getWifeName() {
        return wifeName;
    }

    public void setWifeName(String wifeName) {
        this.wifeName = wifeName;
    }

    public LocalDate getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
        this.marriageDate = marriageDate;
    }

    @Override
    public String toString() {
        return "Husband{" +
                "wifeName='" + wifeName + '\'' +
                ", marriageDate=" + marriageDate +
                '}';
    }
}
