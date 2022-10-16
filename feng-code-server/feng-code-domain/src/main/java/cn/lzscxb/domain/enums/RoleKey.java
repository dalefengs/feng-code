package cn.lzscxb.domain.enums;

import cn.lzscxb.domain.utils.StringUtils;

public enum RoleKey {
    ADMIN, STUDENT, TEACHER;

    public String getLowerCaseName() {
        return StringUtils.toRootLowerCase(name());
    }
}
