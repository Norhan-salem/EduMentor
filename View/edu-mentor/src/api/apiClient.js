import proxyClient from './proxyClient';

/**
 * Search for mentors based on the provided search term.
 * @param {string} searchTerm - The search term for filtering mentors.
 * @returns {Promise} - Resolves with the list of mentors.
 */
export const searchMentors = async (searchTerm) => {
  try {
      const response = await proxyClient.requestWithRetries('/api/search/mentors', 3, 'get', { search: searchTerm });
      return response;
  } catch (error) {
      console.error('Error fetching mentors:', error);
      throw error;
  }
};

/**
* Search for sessions based on the provided search term.
* @param {string} searchTerm - The search term for filtering sessions.
* @returns {Promise} - Resolves with the list of sessions.
*/
export const searchSessions = async (searchTerm) => {
  try {
      const response = await proxyClient.requestWithRetries('/api/search/sessions', 3, 'get', { search: searchTerm });
      return response;
  } catch (error) {
      console.error('Error fetching sessions:', error);
      throw error;
  }
};

/**
* Sort the list of mentors based on the specified sort method.
* @param {Array} mentors - The list of mentors to sort.
* @param {string} sortMethod - The sorting method (e.g., 'alphabetical').
* @returns {Promise} - Resolves with the sorted list of mentors.
*/
export const sortMentors = async (mentors, sortMethod) => {
  try {
      const response = await proxyClient.requestWithRetries('/api/search/sort', 3, 'post', { mentors, sortMethod });
      return response;
  } catch (error) {
      console.error('Error sorting mentors:', error);
      throw error;
  }
};

/**
* Create a new session.
* @param {Object} session - The session data to create.
* @returns {Promise} - Resolves with the created session.
*/
export const createSession = async (session) => {
  try {
      const response = await proxyClient.requestWithRetries('/api/session/create', 3, 'post', session);
      return response;
  } catch (error) {
      console.error('Error creating session:', error);
      throw error;
  }
};

/**
* Assign a mentor to a session.
* @param {Object} session - The session data.
* @param {Object} mentor - The mentor to assign.
* @returns {Promise} - Resolves with the response of the assignment.
*/
export const assignMentor = async (session, mentor) => {
  try {
      const response = await proxyClient.requestWithRetries('/api/session/registerMentor', 3, 'post', { session, mentor });
      return response;
  } catch (error) {
      console.error('Error assigning mentor:', error);
      throw error;
  }
};

/**
 * Registers a mentee to a session.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} session - The session details to register the mentee to.
 * @param {Object} mentee - The mentee details to register.
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error registering the mentee.
 */
export const registerMentee = async (session, mentee) => {
    try {
        const response = await proxyClient.requestWithRetries('/api/session/registerMentee', 3, 'post', { session, mentee });
        return response;
    } catch (error) {
        console.error('Error registering mentee:', error);
        throw error;
    }
};

/**
 * Cancels an existing session.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} session - The session details to cancel.
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error canceling the session.
 */
export const cancelSession = async (session) => {
    try {
        const response = await proxyClient.requestWithRetries('/api/session/cancelSession', 3, 'post', session);
        return response;
    } catch (error) {
        console.error('Error canceling session:', error);
        throw error;
    }
};

/**
 * Retrieves the mentors assigned to a session.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} session - The session details to fetch mentors for.
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error fetching the session's mentors.
 */
export const getSessionMentors = async (session) => {
    try {
        const response = await proxyClient.requestWithRetries('/api/session/getSessionMentors', 3, 'post', session);
        return response;
    } catch (error) {
        console.error('Error fetching session mentors:', error);
        throw error;
    }
};

/**
 * Retrieves the mentees assigned to a session.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} session - The session details to fetch mentees for.
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error fetching the session's mentees.
 */
export const getSessionMentees = async (session) => {
    try {
        const response = await proxyClient.requestWithRetries('/api/session/getSessionMentees', 3, 'post', session);
        return response;
    } catch (error) {
        console.error('Error fetching session mentees:', error);
        throw error;
    }
};

/**
 * Retrieves all sessions for a user.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} user - The user details to fetch sessions for.
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error fetching the user's sessions.
 */
export const getUserSessions = async (user) => {
    try {
        const response = await proxyClient.requestWithRetries('/api/session/getUserSessions', 3, 'post', user);
        return response;
    } catch (error) {
        console.error('Error fetching user sessions:', error);
        throw error;
    }
};

/**
 * Retrieves feedback for a session.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} session - The session details to fetch feedback for.
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error fetching the session's feedback.
 */
export const getSessionFeedback = async (session) => {
    try {
        const response = await proxyClient.requestWithRetries('/api/session/getSessionFeedback', 3, 'post', session);
        return response;
    } catch (error) {
        console.error('Error fetching session feedback:', error);
        throw error;
    }
};

/**
 * Retrieves all available sessions.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error fetching all sessions.
 */
export const getSessions = async () => {
    try {
        const response = await proxyClient.requestWithRetries('/api/session/getSessions', 3, 'get');
        return response;
    } catch (error) {
        console.error('Error fetching sessions:', error);
        throw error;
    }
};

/**
 * Retrieves attended hours for a specific mentee.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {number} menteeId - The ID of the mentee to retrieve attended hours for.
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error fetching the attended hours for the mentee.
 */
export const getMenteeAttendedHours = async (menteeId) => {
    try {
        const response = await proxyClient.requestWithRetries(`/api/mentee/${menteeId}/attendedHours`, 3, 'get');
        return response;
    } catch (error) {
        console.error(`Error fetching attended hours for mentee with ID ${menteeId}:`, error);
        throw error;
    }
};

/**
 * Adds topics to a user's profile.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} userTopicsRequest - The request object containing topics to add to the user.
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error adding topics to the user's profile.
 */
export const addTopicsToUser = async (userTopicsRequest) => {
    try {
        const response = await proxyClient.requestWithRetries('/api/topics/addTopicsToUser', 3, 'post', userTopicsRequest);
        return response;
    } catch (error) {
        console.error('Error adding topics to user:', error);
        throw error;
    }
};

/**
 * Deletes topics from a user's profile.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} userTopicsRequest - The request object containing topics to delete from the user.
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error deleting topics from the user's profile.
 */
export const deleteTopicsFromUser = async (userTopicsRequest) => {
    try {
        const response = await proxyClient.requestWithRetries('/api/topics/deleteTopicsFromUser', 3, 'delete', userTopicsRequest);
        return response;
    } catch (error) {
        console.error('Error deleting topics from user:', error);
        throw error;
    }
};

/**
 * Retrieves all topics assigned to a user.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} user - The user details to fetch topics for.
 * @returns {Object} - The response data from the API.
 * @throws {Error} - If there is an error fetching the user's topics.
 */
export const getUserTopics = async (user) => {
    try {
        const response = await proxyClient.requestWithRetries('/api/topics/getUserTopics', 3, 'post', user);
        return response;
    } catch (error) {
        console.error('Error fetching user topics:', error);
        throw error;
    }
};

/**
 * Fetches the availability of a mentor.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} mentor - The mentor object containing necessary details.
 * @returns {Object} - The response data containing mentor availability.
 * @throws {Error} - If there is an error fetching mentor availability.
 */
export const getMentorAvailability = async (mentor) => {
  try {
    const response = await proxyClient.requestWithRetries('/api/mentor/getMentorAvailability', 3, 'post', mentor);
    return response;
  } catch (error) {
    console.error('Error fetching mentor availability:', error);
    throw error;
  }
};

/**
 * Adds availability for a mentor.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} mentorAvailabilityRequest - The availability request object.
 * @returns {Object} - The response data after adding the mentor's availability.
 * @throws {Error} - If there is an error adding mentor availability.
 */
export const addMentorAvailability = async (mentorAvailabilityRequest) => {
  try {
    const response = await proxyClient.requestWithRetries('/api/mentor/addMentorAvailability', 3, 'post', mentorAvailabilityRequest);
    return response;
  } catch (error) {
    console.error('Error adding mentor availability:', error);
    throw error;
  }
};

/**
 * Deletes a mentor's availability.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} mentorAvailabilityRequest - The availability request object.
 * @returns {Object} - The response data after deleting the mentor's availability.
 * @throws {Error} - If there is an error deleting mentor availability.
 */
export const deleteMentorAvailability = async (mentorAvailabilityRequest) => {
  try {
    const response = await proxyClient.requestWithRetries('/api/mentor/deleteMentorAvailability', 3, 'post', mentorAvailabilityRequest);
    return response;
  } catch (error) {
    console.error('Error deleting mentor availability:', error);
    throw error;
  }
};

/**
 * Fetches the total mentoring hours of a mentor.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} mentor - The mentor object containing necessary details.
 * @returns {Object} - The response data containing total mentoring hours.
 * @throws {Error} - If there is an error fetching mentoring hours.
 */
export const getMentoringHours = async (mentor) => {
  try {
    const response = await proxyClient.requestWithRetries('/api/mentor/getMentoringHours', 3, 'post', mentor);
    return response;
  } catch (error) {
    console.error('Error fetching mentoring hours:', error);
    throw error;
  }
};

/**
 * Fetches available mentors for a specific session.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} session - The session object containing necessary details.
 * @returns {Object} - The response data containing available mentors.
 * @throws {Error} - If there is an error fetching available mentors.
 */
export const getAvailableMentors = async (session) => {
  try {
    const response = await proxyClient.requestWithRetries('/api/mentor/getAvailableMentors', 3, 'post', session);
    return response;
  } catch (error) {
    console.error('Error fetching available mentors:', error);
    throw error;
  }
};

/**
 * Fetches all topics available in the system.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @returns {Object} - The response data containing all topics.
 * @throws {Error} - If there is an error fetching all topics.
 */
export const getAllTopics = async () => {
  try {
    const response = await proxyClient.requestWithRetries('/api/topics/getAllTopics', 3, 'get');
    return response;
  } catch (error) {
    console.error('Error fetching all topics:', error);
    throw error;
  }
};

/**
 * Makes a donation using the provided payload.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} donationPayload - The donation data.
 * @returns {Object} - The response data after making the donation.
 * @throws {Error} - If there is an error making the donation.
 */
export const makeDonation = async (donationPayload) => {
  try {
    const response = await proxyClient.requestWithRetries('/api/donor/api/makeDonation', 3, 'post', donationPayload);
    return response;
  } catch (error) {
    console.error('Error making donation:', error);
    throw error;
  }
};

/**
 * Updates the email of the user.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} user - The user object containing user details.
 * @param {string} newEmail - The new email address.
 * @returns {Object} - The response data after updating the email.
 * @throws {Error} - If there is an error updating the user's email.
 */
export const updateUserEmail = async (user, newEmail) => {
  try {
    const emailUpdateRequest = {
      email: newEmail,
      userDTO: user,
    };
    const response = await proxyClient.requestWithRetries('/api/users/updateEmail', 3, 'post', emailUpdateRequest);
    return response;
  } catch (error) {
    console.error('Error updating user email:', error);
    throw error;
  }
};

/**
 * Updates the user's name.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} user - The user object containing user details.
 * @param {string} firstName - The new first name.
 * @param {string} lastName - The new last name.
 * @returns {Object} - The response data after updating the name.
 * @throws {Error} - If there is an error updating the user's name.
 */
export const updateUserName = async (user, firstName, lastName) => {
  try {
    const nameUpdateRequest = {
      firstName: firstName,
      lastName: lastName,
      userDTO: user,
    };
    const response = await proxyClient.requestWithRetries('/api/users/updateName', 3, 'post', nameUpdateRequest);
    return response;
  } catch (error) {
    console.error('Error updating user name:', error);
    throw error;
  }
};

/**
 * Fetches the invoice details for a given donation.
 * Uses the proxyClient to handle retries and caching (if applicable).
 * @param {Object} params - The parameters containing donation, targetCurrency, and includeTax.
 * @returns {Object} - The response data containing the invoice details.
 * @throws {Error} - If there is an error fetching invoice details.
 */
export const getInvoiceDetails = async ({ donation, targetCurrency, includeTax = true }) => {
  try {
    const params = {
      donation: JSON.stringify(donation),
      targetCurrency,
      includeTax,
    };
    const response = await proxyClient.requestWithRetries('/api/invoice/getInvoiceDetails', 3, 'get', params);
    return response;
  } catch (error) {
    console.error('Error fetching invoice details:', error);
    throw error;
  }
};
