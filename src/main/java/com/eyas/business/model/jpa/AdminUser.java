package com.eyas.business.model.jpa;

import javax.persistence.*;

/**
 * @Auther: 王龙龙
 * @Date: 2019/2/7 19:32
 * @Description:
 */
@Entity
@Table(name = "adminuser")
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100)
    private String loginname;
    @Column(length = 100)
    private String password;
    @Column
    private boolean validflag;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidflag() {
        return validflag;
    }

    public void setValidflag(boolean validflag) {
        this.validflag = validflag;
    }
}
