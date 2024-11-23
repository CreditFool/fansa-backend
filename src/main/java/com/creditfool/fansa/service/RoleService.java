package com.creditfool.fansa.service;

import com.creditfool.fansa.constant.ERole;
import com.creditfool.fansa.entity.Role;

public interface RoleService {
    Role getOrSaveRole(ERole roleType);
}
