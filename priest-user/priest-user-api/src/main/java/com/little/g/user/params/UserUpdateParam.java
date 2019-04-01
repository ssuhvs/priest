package com.little.g.user.params;

import com.little.g.common.validate.annatations.Gender;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by lengligang on 2019/3/31.
 */
public class UserUpdateParam implements Serializable {
    private Long uid;
    @Size(min = 1,max = 200)
    private String avatar;
    @Size(min = 1,max = 200)
    private String name;
    @Gender
    private Byte gender;

    private Long birthday;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }
}
