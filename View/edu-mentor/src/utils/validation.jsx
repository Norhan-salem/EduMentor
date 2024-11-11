
export const validateUsername = (username) => {
    if (!username) {
      return 'Username is required';
    }
    return '';
  };
  
  export const validateEmail = (email) => {
    if (!email) {
      return 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(email)) {
      return 'Email address is invalid';
    }
    return '';
  };
  
  export const validatePassword = (password) => {
    if (!password) {
      return 'Password is required';
    } else if (password.length < 6) {
      return 'Password must be at least 6 characters long';
    }
    return '';
  };
  
  export const validateConfirmPassword = (password, confirmPassword) => {
    if (!confirmPassword) {
      return 'Confirm Password is required';
    } else if (password !== confirmPassword) {
      return 'Passwords do not match';
    }
    return '';
  };
  
  export const validateRole = (role) => {
    if (!role) {
      return 'Role is required';
    }
    return '';
  };
  
  export const validateInterests = (interests) => {
    if (interests.length === 0) {
      return 'At least one interest must be selected';
    } else if (interests.length > 3) {
      return 'You can select up to 3 interests';
    }
    return '';
  };
  
  export const validateForm = (firstName, lastName, email, password, confirmPassword, role, interests, agreedToTerms) => {
    const errors = {};

    if (!firstName) {
      errors.firstName = 'First name is required';
    }

    if (!lastName) {
      errors.lastName = 'Last name is required';
    }

    if (!email) {
      errors.email = 'Email is required';
    }

    if (!password) {
      errors.password = 'Password is required';
    }

    if (password !== confirmPassword) {
      errors.confirmPassword = 'Passwords do not match';
    }

    if (!role) {
      errors.role = 'Role is required';
    }

    if (interests.length === 0) {
      errors.interests = 'At least one interest must be selected';
    } else if (interests.length > 3) {
      errors.interests = 'You can select up to 3 interests';
    }

    if (!agreedToTerms) {
      errors.agreedToTerms = 'You must agree to the terms and conditions';
    }

    return errors;
};

  