import { createContext, useEffect, useReducer, useCallback, useMemo } from 'react';
import axiosInstance from '../utils/axios';
import localStorageAvailable from '../utils/localStorageAvailable';

// Initial state for the AuthContext
const initialState = {
  isInitialized: false,
  isAuthenticated: false,
  user: null,
};

// Reducer function to handle authentication actions
const reducer = (state, action) => {
  switch (action.type) {
    case 'INITIALIZE':
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
        isInitialized: true,
        isAuthenticated: false,
        user: null,
      };
    default:
      return state;
  }
};

// Create the AuthContext
export const AuthContext = createContext(null);

// AuthProvider component to wrap the app and provide context
export const AuthProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  // Initialize user state from localStorage
  const initialize = useCallback(() => {
    try {
      const savedUser = localStorageAvailable() ? localStorage.getItem('user') : null;
      if (savedUser) {
        const user = JSON.parse(savedUser);
        dispatch({
          type: 'INITIALIZE',
          payload: { isAuthenticated: true, user },
        });
      } else {
        dispatch({
          type: 'INITIALIZE',
          payload: { isAuthenticated: false, user: null },
        });
      }
    } catch (error) {
      console.error('Error initializing user:', error);
      dispatch({
        type: 'INITIALIZE',
        payload: { isAuthenticated: false, user: null },
      });
    }
  }, []);

  // Effect to run initialization on mount
  useEffect(() => {
    initialize();
  }, [initialize]);

  // Login function
  const login = useCallback(async (email, password) => {
    try {
      const response = await axiosInstance.post('/api/auth/login', { email, password });
      const { success, user, message } = response.data;

      if (success && user) {
        if (localStorageAvailable()) {
          localStorage.setItem('user', JSON.stringify(user));
        }
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

  // Register function
  const register = useCallback(async (firstName, lastName, email, password, userType) => {
    try {
      const payload = {
        firstName,
        lastName,
        credentials: { email, password },
        userType,
      };
      const response = await axiosInstance.post('/api/auth/signup', payload);
      const { success, user, message } = response.data;

      if (success && user) {
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

  // Logout function
  const logout = useCallback(() => {
    if (localStorageAvailable()) {
      localStorage.removeItem('user');
    }
    dispatch({ type: 'LOGOUT' });
  }, []);

  // Memoized context value
  const contextValue = useMemo(
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

  if (!state.isInitialized) {
    return null;
  }

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};
