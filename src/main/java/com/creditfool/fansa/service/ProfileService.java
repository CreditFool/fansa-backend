package com.creditfool.fansa.service;

import com.creditfool.fansa.entity.Profile;

public interface ProfileService {
    Profile createProfile(Profile profile);

    Profile updateProfile(Profile updatedProfile, String uid);

    Profile getProfile(Long id);

    Profile getProfileWithUID(String uid);

    Profile getProfileWithUsername(String username);

}
