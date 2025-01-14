import { createContext, useEffect, useReducer, useCallback, useMemo } from 'react';
import axiosInstance from '../utils/axios';
import localStorageAvailable from '../utils/localStorageAvailable';

// Initial state of the authentication context
const initialState = {
  isInitialized: false,
  isAuthenticated: false,
  user: null,
};

// Reducer function to handle state updates
const reducer = (state, action) => {
  switch (action.type) {
    case 'INITIAL':
      return {
        isInitialized: true,
        isAuthenticated: action.payload.isAuthenticated,
        user: action.payload.user,
      };
    case 'LOGIN':
      return {
        ...state,
        isAuthenticated: true,
        user: action.payload.user,
      };
    case 'LOGOUT':
      return {
        ...state,
        isAuthenticated: false,
        user: null,
      };
    default:
      return state;
  }
};

// Create the authentication context
export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [state, dispatch] = useReducer(reducer, initialState);

  // Initialize the user state by checking localStorage
  const initialize = useCallback(() => {
    try {
      const savedUser = localStorageAvailable() ? localStorage.getItem('user') : null;

      if (savedUser) {
        const user = JSON.parse(savedUser);
        dispatch({
          type: 'INITIAL',
          payload: {
            isAuthenticated: true,
            user,
          },
        });
      } else {
        dispatch({
          type: 'INITIAL',
          payload: {
            isAuthenticated: false,
            user: null,
          },
        });
      }
    } catch (error) {
      console.error('Error initializing user:', error);
      dispatch({
        type: 'INITIAL',
        payload: {
          isAuthenticated: false,
          user: null,
        },
      });
    }
  }, []);

  // Run the initialize function on component mount
  useEffect(() => {
    initialize();
  }, [initialize]);

  // Login a user
  const login = useCallback(async (email, password) => {
    try {
      const response = await axiosInstance.post('/api/auth/login', { email, password });
      const { success, user, message } = response.data;

      if (success && user) {
        // Save the user data to localStorage
        if (localStorageAvailable()) {
          localStorage.setItem('user', JSON.stringify(user));
        }
        // Update the state
        dispatch({
          type: 'LOGIN',
          payload: { user },
        });
      } else {
        throw new Error(message || 'Invalid email or password.');
      }
    } catch (error) {
      console.error('Error during login:', error);
      throw error;
    }
  }, []);

  // Register a new user
  const register = useCallback(async (firstName, lastName, email, password, userType) => {
    console.log('registering user');
    try {
      const payload = {
        firstName,
        lastName,
        credentials: {
          email,
          password,
        },
        userType,
      };
      const response = await axiosInstance.post('/api/auth/signup', payload);
      const { success, user, message } = response.data;

      if (success && user) {
        // Save the user data to localStorage
        if (localStorageAvailable()) {
          localStorage.setItem('user', JSON.stringify(user));
        }
        return user;
      } else {
        throw new Error(message || 'Registration failed. Please try again.');
      }
    } catch (error) {
      console.error('Error during registration:', error);
      throw error;
    }
  }, []);

  // Log out the user
  const logout = useCallback(() => {
    if (localStorageAvailable()) {
      localStorage.removeItem('user');
    }
    dispatch({ type: 'LOGOUT' });
  }, []);

  const memoizedValue = useMemo(
    () => ({
      isInitialized: state.isInitialized,
      isAuthenticated: state.isAuthenticated,
      user: state.user,
      login,
      register,
      logout,
    }),
    [state.isInitialized, state.isAuthenticated, state.user, login, register, logout]
  );

  return <AuthContext.Provider value={memoizedValue}>{children}</AuthContext.Provider>;
}
