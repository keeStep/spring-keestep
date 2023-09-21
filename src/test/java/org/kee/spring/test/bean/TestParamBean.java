package org.kee.spring.test.bean;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/8/8 23:28
 */
public class TestParamBean {

    private String name;

    public TestParamBean(String name) {
        this.name = name;
    }

    public void printSomething() {
        System.out.println("奉天承运，特命【" + name + "】为: 皓翎国-大王姬！");
    }
}
