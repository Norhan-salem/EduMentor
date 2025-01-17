package com.asu.EduMentor.model.profile.template;

import java.util.Map;

/**
 * Interface defining the contract for profile data management
 */
public interface IProfileData {
    /**
     * Get all profile data
     * @return Map of profile data
     */
    Map<String, Object> getData();

    /**
     * Update profile data with new information
     * @param newData Map containing new/updated data
     */
    void updateData(Map<String, Object> newData);

    /**
     * Validate the profile data
     * @return true if data is valid, false otherwise
     */
    boolean validateData();
}
