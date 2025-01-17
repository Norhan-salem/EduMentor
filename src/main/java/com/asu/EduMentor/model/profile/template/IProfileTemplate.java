package com.asu.EduMentor.model.profile.template;

import com.asu.EduMentor.model.User;

import java.util.List;
import java.util.Map;

/**
 * Interface defining the contract for profile templates
 */
public interface IProfileTemplate {
    /**
     * Set up a profile for a given user ID
     * @param id The user ID
     * @return Map containing the profile data
     */
    Map<String, Object> setupProfile(int id);

    /**
     * Add new information to a user's profile
     * @param information Map containing new information
     * @param user The user object
     * @return true if information was added successfully
     */
    boolean addInformation(Map<String, Object> information, User user);

    /**
     * Update an existing profile
     * @param id The user ID
     * @param information Map containing updated information
     * @return true if profile was updated successfully
     */
    boolean updateProfile(int id, Map<String, Object> information);

    /**
     * Delete a specific item from a profile
     * @param id The user ID
     * @param key The key to delete
     * @return true if item was deleted successfully
     */
    boolean deleteProfileItem(int id, String key);

    /**
     * Get list of required fields for this profile type
     * @return List of required field names
     */
    List<String> getRequiredFields();

    /**
     * Validate profile data
     * @param profileData Map containing profile data to validate
     * @return true if profile data is valid
     */
    boolean validateProfile(Map<String, Object> profileData);
}
