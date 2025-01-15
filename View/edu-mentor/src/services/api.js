import axiosInstance from '../utils/axios';

export const searchMentors = async (searchTerm) => {
    try {
        const response = await axiosInstance.get('/api/search/mentors', {
            params: {
                search: searchTerm
            }
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching mentors:', error);
        throw error;
    }
};

export const searchSessions = async (searchTerm) => {
    try {
        const response = await axiosInstance.get('/api/search/sessions', {
            params: {
                search: searchTerm
            }
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching sessions:', error);
        throw error;
    }
};

export const sortMentors = async (mentors, sortMethod) => {
    try {
        const response = await axiosInstance.post('/api/search/sort', {
            mentors,
            sortMethod
        });
        return response.data;
    } catch (error) {
        console.error('Error sorting mentors:', error);
        throw error;
    }
};

// Create session done
export const createSession = async (session) => {
    try {
        const response = await axiosInstance.post('/api/session/create', session);
        return response.data;
    } catch (error) {
        console.error('Error creating session:', error);
        throw error;
    }
};

// Assign mentor to a session de ya htt8yyr ya m7taga tt8yyr fa hsebha :(
export const assignMentor = async (session, mentor) => {
    try {
        const response = await axiosInstance.post('/api/session/registerMentor', { session, mentor });
        return response.data;
    } catch (error) {
        console.error('Error assigning mentor:', error);
        throw error;
    }
};

// Register mentee to a session done
export const registerMentee = async (session, mentee) => {
    try {
        const response = await axiosInstance.post('/api/session/registerMentee', { session, mentee });
        return response.data;
    } catch (error) {
        console.error('Error registering mentee:', error);
        throw error;
    }
};

// Cancel session done
export const cancelSession = async (session) => {
    try {
        const response = await axiosInstance.post('/api/session/cancelSession', session);
        return response.data;
    } catch (error) {
        console.error('Error canceling session:', error);
        throw error;
    }
};

// Get mentors of a session
export const getSessionMentors = async (session) => {
    try {
        const response = await axiosInstance.post('/api/session/getSessionMentors', session);
        return response.data;
    } catch (error) {
        console.error('Error fetching session mentors:', error);
        throw error;
    }
};

// Get mentees of a session
export const getSessionMentees = async (session) => {
    try {
        const response = await axiosInstance.post('/api/session/getSessionMentees', session);
        return response.data;
    } catch (error) {
        console.error('Error fetching session mentees:', error);
        throw error;
    }
};

// Get sessions for a user lessa el mentor dashboard :(
export const getUserSessions = async (user) => {
    try {
        const response = await axiosInstance.post('/api/session/getUserSessions', user);
        return response.data;
    } catch (error) {
        console.error('Error fetching user sessions:', error);
        throw error;
    }
};

export const getSessionFeedback = async (session) => {
    try {
      const response = await axiosInstance.post('/api/session/getSessionFeedback', session);
      return response.data; 
    } catch (error) {
      console.error('Error fetching session feedback:', error);
      throw error;
    }
  };

export const getSessions = async () =>{
    try {
        const response = await axiosInstance.get('/api/session/getSessions');
        return response.data;
    } catch (error) {
        console.error('Error fetching sessions:', error);
        throw error;
    }
};

export const getMenteeAttendedHours = async (menteeId) => {
    try {
      const response = await axiosInstance.get(`/api/mentee/${menteeId}/attendedHours`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching attended hours for mentee with ID ${menteeId}:`, error);
      throw error;
    }
};

export const addTopicsToUser = async (userTopicsRequest) => {
    try {
      const response = await axiosInstance.post('/api/topics/addTopicsToUser', userTopicsRequest);
      return response.data;
    } catch (error) {
      console.error('Error adding topics to user:', error);
      throw error;
    }
};

export const deleteTopicsFromUser = async (userTopicsRequest) => {
    try {
      const response = await axiosInstance.delete('/api/topics/deleteTopicsFromUser', {
        data: userTopicsRequest,
      });
      return response.data;
    } catch (error) {
      console.error('Error deleting topics from user:', error);
      throw error;
    }
};

export const getUserTopics = async (user) => {
    try {
      const response = await axiosInstance.post('/api/topics/getUserTopics', user);
      return response.data;
    } catch (error) {
      console.error('Error fetching user topics:', error);
      throw error;
    }
};

export const getMentorAvailability = async (mentor) => {
    try {
      const response = await axiosInstance.post('/api/mentor/getMentorAvailability', mentor);
      return response.data;
    } catch (error) {
      console.error('Error fetching mentor availability:', error);
      throw error;
    }
  };
  
  export const addMentorAvailability = async (mentorAvailabilityRequest) => {
    try {
      const response = await axiosInstance.post('/api/mentor/addMentorAvailability', mentorAvailabilityRequest);
      return response.data;
    } catch (error) {
      console.error('Error adding mentor availability:', error);
      throw error;
    }
  };

  export const deleteMentorAvailability = async (mentorAvailabilityRequest) => {
    try {
      const response = await axiosInstance.post('/api/mentor/deleteMentorAvailability', mentorAvailabilityRequest);
      return response.data;
    } catch (error) {
      console.error('Error deleting mentor availability:', error);
      throw error;
    }
  };
  
  export const getMentoringHours = async (mentor) => {
    try {
      const response = await axiosInstance.post('/api/mentor/getMentoringHours', mentor);
      return response.data;
    } catch (error) {
      console.error('Error fetching mentoring hours:', error);
      throw error;
    }
  };