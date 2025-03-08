package com.example.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.domain.ProfilePhoto;

@Repository
public interface ProfilePhotoRepo extends JpaRepository<ProfilePhoto, Integer>
{

}
