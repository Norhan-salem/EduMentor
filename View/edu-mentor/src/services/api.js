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
      const response = await axiosInstance.post('/api/getSessionFeedback', session);
      return response.data; 
    } catch (error) {
      console.error('Error fetching session feedback:', error);
      throw error;
    }
  };

export const getSessions = async () =>{};
