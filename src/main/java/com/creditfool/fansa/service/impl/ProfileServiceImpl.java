package com.creditfool.fansa.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.creditfool.fansa.constant.Message;
import com.creditfool.fansa.entity.Profile;
import com.creditfool.fansa.repository.ProfileRepository;
import com.creditfool.fansa.service.ProfileService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Override
    public Profile createProfile(Profile profile) {
        log.info("create profile: {}", profile.getUser().getUid());
        return profileRepository.save(profile);
    }

    @Override
    public Profile getProfile(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(Message.PROFILE_NOT_FOUND));
    }

    @Override
    public Profile getProfileWithUsername(String username) {
        return profileRepository.findByUserUsernameAndDeletedAtIsNull(username)
                .orElseThrow(
                        () -> new EntityNotFoundException(Message.PROFILE_NOT_FOUND));
    }

    @Override
    public Profile getProfileWithUID(String uid) {
        try {
            UUID uuid = UUID.fromString(uid);
            return profileRepository.findByUserUid(uuid)
                    .orElseThrow(
                            () -> new EntityNotFoundException(Message.PROFILE_NOT_FOUND));

        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException(Message.PROFILE_NOT_FOUND);
        }
    }

    @Override
    public Profile updateProfile(Profile updatedProfile, String uid) {
        // TODO Auto-generated method stub
        return null;
    }

}
