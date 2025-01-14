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
            mentors: mentors,
            sortMethod: sortMethod
        });
        return response.data;
    } catch (error) {
        console.error('Error sorting mentors:', error);
        throw error;
    }
};