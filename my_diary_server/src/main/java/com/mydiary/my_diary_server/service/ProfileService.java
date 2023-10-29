package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.data.entity.Profile;
import com.mydiary.my_diary_server.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // 프로필 생성
    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    // 프로필 조회 by ID
    public Profile getProfileById(Long id) {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        return profileOptional.orElse(null);
    }

    // 프로필 업데이트 by ID
    public Profile updateProfile(Long id, Profile updatedProfile) {
        Optional<Profile> profileOptional = profileRepository.findById(id);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            profile.setFavoriteMusic(updatedProfile.getFavoriteMusic());
            profile.setHobby(updatedProfile.getHobby());
            profile.setMbti(updatedProfile.getMbti());
            return profileRepository.save(profile);
        }
        return null;
    }

    // 프로필 삭제 by ID
    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
