import { useState, useEffect } from "react";
import { Link,useHistory } from 'react-router-dom';




const useLogin = (props) => {
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  
  const intialValues = { username: "", password: "" };
  const [formValues, setFormValues] = useState(intialValues);
  const [formErrors, setFormErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  
  const submitForm = () => {
    console.log(formValues);
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    setFormErrors(validate(formValues));
    setIsSubmitting(true);
    
  
  };

  const validate = (values) => {
    let errors = {};
    if (!values.username.trim()) {
      errors.username = 'Username is required';
    }

    if (!values.password) {
      errors.password = 'Password is required';
    }

    return errors;
  };
  useEffect(() => {
    if (Object.keys(formErrors).length === 0 && isSubmitting) {
      submitForm();
    }
  }, [formErrors]);

  return { handleChange, intialValues, formErrors, formValues, isSubmitting, handleSubmit };

};
export default useLogin;