package org.kee.spring.test.bean;

import org.kee.spring.beans.factory.annotation.Autowired;
import org.kee.spring.beans.factory.annotation.Qualifier;
import org.kee.spring.context.annotation.Component;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/7 22:52
 */
@Component
public class Wife {

    @Autowired
    private Husband husband;

    @Autowired
    @Qualifier("husbandMother")
    private IMother mother;

    public String queryHusband() {
        return "Wife.husband/ Mother.callMother:" + mother.callMother();
    }


    public Husband getHusband() {
        return husband;
    }

    public void setHusband(Husband husband) {
        this.husband = husband;
    }

    public IMother getMother() {
        return mother;
    }

    public void setMother(IMother mother) {
        this.mother = mother;
    }
}
