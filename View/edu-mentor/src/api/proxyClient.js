import axiosInstance from './axiosInstance';

const Cache = new Map();

/**
 * ProxyClient: Handles API requests with retries and caching.
 */
const proxyClient = {
  
  /**
   * Makes a GET request with caching, retries, and logging.
   * @param {string} url - The URL for the GET request.
   * @param {object} options - Optional Axios request configuration.
   * @returns {Promise} - Resolves with the response data.
   */
  async get(url, options = {}) {
    if (Cache.has(url)) {
      console.log(`Cache hit for ${url}`);
      return Cache.get(url);
    }

    try {
      const response = await axiosInstance.get(url, options);
      Cache.set(url, response.data);
      return response.data;
    } catch (error) {
      console.error(`Error on GET request to ${url}:`, error);
      throw error;
    }
  },

  /**
   * Makes a POST request with retries and logging.
   * @param {string} url - The URL for the POST request.
   * @param {object} data - The payload to send in the POST request.
   * @param {object} options - Optional Axios request configuration.
   * @returns {Promise} - Resolves with the response data.
   */
  async post(url, data, options = {}) {
    try {
      const response = await axiosInstance.post(url, data, options);
      return response.data;
    } catch (error) {
      console.error(`Error on POST request to ${url}:`, error);
      throw error;
    }
  },

  /**
   * Makes a request with retries for both GET and POST methods.
   * This handles network failures, server errors, and retries.
   * @param {string} url - The URL for the API request.
   * @param {number} retries - The number of retry attempts (default is 3).
   * @param {string} method - The HTTP method to use ('get' or 'post').
   * @param {object} data - The payload for POST requests (default is {}).
   * @param {object} options - Optional Axios request configuration.
   * @returns {Promise} - Resolves with the response data.
   */
  async requestWithRetries(url, retries = 3, method = 'get', data = {}, options = {}) {
    let attempt = 0;

    while (attempt < retries) {
      try {
        // Attempt the request
        const response = await this[method](url, data, options);
        
        // If successful, return the response data
        return response;
      } catch (error) {
        attempt += 1;
        console.log(`Attempt ${attempt} failed for ${url}`);

        // Handle network errors
        if (error.code === 'ECONNABORTED' || error.message === 'Network Error') {
          console.log(`Network error occurred. Retrying (${attempt}/${retries})...`);
        } else if (error.response) {
          // Retry on server errors (e.g., 500, 503)
          if (error.response.status >= 500) {
            console.log(`Server error occurred. Retrying (${attempt}/${retries})...`);
          } else {
            // For client errors (4xx), don't retry
            console.error(`Non-retriable error: ${error.response.status}.`);
            throw error;
          }
        } else {
          // Unknown error, retry
          console.log(`Unknown error occurred. Retrying (${attempt}/${retries})...`);
        }

        if (attempt >= retries) {
          console.error(`Max retries reached for ${url}.`);
          throw error;
        }
      }
    }
  },
};

export default proxyClient;
